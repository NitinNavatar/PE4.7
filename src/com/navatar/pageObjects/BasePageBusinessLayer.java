
package com.navatar.pageObjects;

import org.apache.poi.hssf.view.brush.PendingPaintings;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.server.handler.SwitchToWindow;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.testng.Assert;

import com.jcraft.jsch.ConfigRepository.Config;
import com.navatar.generic.AppListeners;
import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.ExcelUtils;
import static com.navatar.generic.SmokeCommonVariables.*;
import com.navatar.generic.SoftAssert;
import com.navatar.generic.CommonLib.*;
import com.navatar.generic.EnumConstants.*;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.Log;
import com.navatar.generic.CommonVariables;

import static com.navatar.generic.AppListeners.*;
import static com.navatar.generic.BaseLib.sa;
import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.AR_Research2;
import static com.navatar.generic.CommonVariables.ATE_AdvanceDueDate1;
import static com.navatar.generic.CommonVariables.environment;
import static com.navatar.generic.CommonVariables.mode;
import static com.navatar.generic.CommonVariables.tabObj4;

import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

public class BasePageBusinessLayer extends BasePage implements BasePageErrorMessage {

	/**
	 * @param driver
	 */
	public BasePageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author Ankit Jaiswal
	 * @description- This method is used to set new password for CRM Users
	 */
	public boolean setNewPassword() {
		try {
			Assert.assertTrue(getChnageYourPassword(60).getText().trim().contains("Change Your Password"),
					"Change Your Password text is not verified");
		} catch (Exception e) {
			driver.navigate().refresh();
			e.printStackTrace();
		}
		appLog.info("Password To Be Entered: " + ExcelUtils.readDataFromPropertyFile("password"));
		if (sendKeys(driver, getNewPassword(60), ExcelUtils.readDataFromPropertyFile("password"),
				"New Password Text box", action.SCROLLANDBOOLEAN)) {
			appLog.info("Password Entered: " + getNewPassword(10).getAttribute("value"));
			appLog.info("Confirm Password To Be Entered: " + ExcelUtils.readDataFromPropertyFile("password"));
			ThreadSleep(5000);
			if (sendKeys(driver, getConfimpassword(60), ExcelUtils.readDataFromPropertyFile("password"),
					"Confirm Password text Box", action.SCROLLANDBOOLEAN)) {
				appLog.info("Confirm Password Entered: " + getConfimpassword(60).getAttribute("value"));
				CommonLib.selectVisibleTextFromDropDown(driver, getQuestion(60), "In what city were you born?",
						"Question drop down list");
				sendKeys(driver, getAnswer(60), "New York", "Answer Text Box", action.SCROLLANDBOOLEAN);
				ThreadSleep(5000);
				if (click(driver, getChangePassword(60), "Chnage Password Button", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on change password button");
					appLog.info("CRM User Password is set successfully.");
					return true;
				} else {
					appLog.error("Not able to click on change password button so cannot set user password");
				}

			} else {
				appLog.error("Not able to exter confirm password in text box so cannot set user password");
			}
		} else {
			appLog.error("Not able to exter password in text box so cannot set user password");
		}
		return false;
	}

	public boolean clickOnRelatedList_Classic(String environment, RelatedList RelatedList) {
		String relatedList = null;
		WebElement ele;
		switch (RelatedList) {
		case Fundraising_Contacts:
			relatedList = "Fundraising Contacts";
			break;
		case Office_Locations:
			relatedList = "Office Locations";
			break;
		case Open_Activities:
			relatedList = "Open Activities";
			break;
		case Fundraisings:
			relatedList = "Fundraisings";
			break;
		case FundDrawdown:
			relatedList = "Fund Drawdown";
			break;
		case CapitalCalls:
			relatedList = "Capital Calls";
			break;
		case Affiliations:
			relatedList = "Affiliations";
			break;
		case Activities:
			relatedList = "Activities";
			break;
		case Activity_History:
			relatedList = "Activity History";
			break;
		case Commitments:
			relatedList = "Commitments";
			break;
		case Partnerships:
			relatedList = "Partnerships";
			break;
		case Deals_Sourced:
			relatedList = "Deals Sourced";
			break;
		case Pipeline_Stage_Logs:
			relatedList = "Pipeline Stage Logs";
			break;

		default:
			return false;
		}
		ThreadSleep(2000);
		System.err.println("Passed switch statement");

		ele = isDisplayed(driver, FindElement(driver, "//span[@class='listTitle'][text()='" + relatedList + "']",
				relatedList, action.SCROLLANDBOOLEAN, 10), "visibility", 10, relatedList);
		if (ele != null) {
			if (click(driver, ele, relatedList, action.SCROLLANDBOOLEAN)) {
				CommonLib.log(LogStatus.INFO, "Related List found : " + relatedList, YesNo.No);
				ThreadSleep(2000);
				return true;
			}
		}

		return false;
	}

	public boolean clickOnViewAllRelatedList(String environment, String mode, RelatedList RelatedList) {
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			if (clickOnRelatedList_Classic(environment, RelatedList)) {
				return true;
			}
		} else {
			String relatedList = null;
			WebElement ele;
			switch (RelatedList) {
			case Fundraising_Contacts:
				relatedList = "Fundraising Contacts";
				break;
			case Office_Locations:
				relatedList = "Office Locations";
				break;
			case Affiliations:
				relatedList = "Affiliations";
				break;
			case Activities:
				relatedList = "Activities";
				break;
			case Activity_History:
				relatedList = "Activity History";
				break;
			case Deals_Sourced:
				relatedList = "Deals Sourced";
				break;
			case Partnerships:
				relatedList = "Partnerships";
				break;
			case FundDrawdown:
				relatedList = "Fund Drawdown";
				break;
			case FundDistribution:
				relatedList = "Fund Distribution";
				break;
			case CapitalCalls:
				relatedList = "Capital Calls";
				break;
			case InvestorDistributions:
				relatedList = "Investor Distributions";
				break;
			case Pipeline_Stage_Logs:
				relatedList = "Pipeline Stage Logs";
				break;
			case Correspondence_Lists:
				relatedList = "Correspondence Lists";
				break;
			case Commitments:
				relatedList = "Commitments";
				break;
			default:
				return false;
			}
			ThreadSleep(2000);
			System.err.println("Passed switch statement");

			ele = isDisplayed(driver,
					FindElement(driver,
							"//span[text()='" + relatedList + "']/ancestor::article//span[text()='View All']",
							relatedList, action.SCROLLANDBOOLEAN, 10),
					"visibility", 10, relatedList);
			if (ele != null) {
				if (clickUsingJavaScript(driver, ele, relatedList, action.SCROLLANDBOOLEAN)) {
					CommonLib.log(LogStatus.INFO, "Related List found : " + relatedList, YesNo.No);
					ThreadSleep(2000);
					return true;
				} else if (clickUsingJavaScript(driver, ele, relatedList)) {
					CommonLib.log(LogStatus.INFO, "Related List found : " + relatedList, YesNo.No);
					ThreadSleep(2000);
					return true;
				}
			}

		}

		return false;
	}

	public boolean checkContactOrAccountOrFundraisingPage(String environment, String mode,
			String contactOrAccountOrFRName, PageName pageName, columnName columnName, WebElement scrollBox) {
		String[] splitedContactName = null;
		boolean flag = false;
		int j = 0;
		String XpathelementTOSearch = "";
		if (columnName.toString().equalsIgnoreCase(columnName.contactName.toString())) {
			splitedContactName = contactOrAccountOrFRName.split(" ");
			XpathelementTOSearch = "//span/div/a[contains(text(),'" + splitedContactName[0] + "')][contains(text(),'"
					+ splitedContactName[1] + "')]";
		} else if (columnName.toString().equalsIgnoreCase(columnName.AccountName.toString())) {
			XpathelementTOSearch = "//span/div/a[text()='" + contactOrAccountOrFRName + "']";
		} else if (pageName.toString().equalsIgnoreCase(PageName.pastFundraisingContactPopUp.toString())) {
			XpathelementTOSearch = "//span[contains(@id,'Past_FundraisingsContact-cell-0')]/a[text()='"
					+ contactOrAccountOrFRName + "']";
		} else if (pageName.toString().equalsIgnoreCase(PageName.pastFundraisingAccountPopUp.toString())) {
			XpathelementTOSearch = "//span[contains(@id,'Past_Fundraisings-cell-0-0')]/a[text()='"
					+ contactOrAccountOrFRName + "']";
		} else {
			XpathelementTOSearch = "";
		}
		By byelementToSearch = By.xpath(XpathelementTOSearch);
		int widgetTotalScrollingHeight = Integer.parseInt(String
				.valueOf(((JavascriptExecutor) driver).executeScript("return arguments[0].scrollHeight", scrollBox)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(0,0)", scrollBox);
		for (int i = 0; i <= widgetTotalScrollingHeight / 25; i++) {
			if (!driver.findElements(byelementToSearch).isEmpty()
					&& driver.findElement(byelementToSearch).isDisplayed()) {
				appLog.info("Element Successfully Found and displayed");
				ThreadSleep(500);
				WebElement ele = FindElement(driver, XpathelementTOSearch, "", action.SCROLLANDBOOLEAN, 10);
				if (ele != null) {
					if (click(driver, ele, columnName.toString() + " link", action.BOOLEAN)) {
						String parentId = switchOnWindow(driver);
						if (parentId != null) {
							ThreadSleep(5000);
							if (Mode.Lightning.toString().equalsIgnoreCase(mode)) {
								XpathelementTOSearch = "//h1//*[contains(text(),'" + contactOrAccountOrFRName + "')]";
							} else {
								XpathelementTOSearch = "//h2[contains(text(),'" + contactOrAccountOrFRName + "')]";
							}
							ele = FindElement(driver, XpathelementTOSearch, columnName.toString() + "header text",
									action.SCROLLANDBOOLEAN, 20);
							if (ele != null) {
								appLog.info("Landing Page Verified : " + columnName.toString());
								flag = true;
							} else {
								appLog.error("Landing Page Not Verified : " + columnName.toString());
								sa.assertTrue(false, "Landing Page Not Verified : " + columnName.toString());
							}
							driver.close();
							driver.switchTo().window(parentId);
							switchToDefaultContent(driver);
						} else {
							appLog.error("Not New Window for " + columnName.toString());
							sa.assertTrue(false, "Not New Window for " + columnName.toString());
						}
					} else {
						appLog.error(
								"Not able to click on " + columnName.toString() + " so cannot verify landing page");
						sa.assertTrue(false,
								"Not able to click on " + columnName.toString() + " so cannot verify landing page");
					}
				} else {
					appLog.error("Not able to click on " + columnName.toString() + " so cannot verify landing page");
					sa.assertTrue(false,
							"Not able to click on " + columnName.toString() + " so cannot verify landing page");
				}
				break;
			} else {
				System.out.println("Not FOund: " + byelementToSearch.toString());
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(" + j + "," + (j = j + 45) + ")",
						scrollBox);
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
		return flag;
	}

	public boolean verifyGridErrorMessage1(String environment, String mode, RelatedList gridSectionName,
			String expectedMsg, int timeOut) {
		String xpath = "//*[text()='" + gridSectionName.toString()
				+ "']/ancestor::article//*[text()='No data returned']";
		WebElement ele = isDisplayed(driver,
				FindElement(driver, xpath, gridSectionName.toString(), action.SCROLLANDBOOLEAN, timeOut), "visiblity",
				30, gridSectionName.toString());

		if (ele != null) {
			return true;
		} else {
			return false;
		}
	}

	public WebElement getStep1NextBtn(PageName pageName, TopOrBottom topOrBottom, int timeOut) {

		WebElement ele = null;
		String xpath = null;
		if (PageName.BulkDownload.equals(pageName) || PageName.emailFundraisingContact.equals(pageName)
				|| PageName.emailCapitalCallNotice.equals(pageName)
				|| PageName.Send_Distribution_Notices.equals(pageName) || PageName.BulkEmail.equals(pageName)) {

			if (TopOrBottom.TOP.equals(topOrBottom)) {
				xpath = "(//div[@class='step_1']//a[@title='Next'])[1]";
			} else {
				xpath = "(//div[@class='step_1']//a[@title='Next'])[2]";
			}

		} else {

		}
		ele = FindElement(driver, xpath, "Step1 Nxt Btn : " + topOrBottom, action.SCROLLANDBOOLEAN, timeOut);
		return ele;

	}

	public WebElement getStep2PreviousBtn(PageName pageName, TopOrBottom topOrBottom, int timeOut) {

		WebElement ele = null;
		String xpath = null;
		if (PageName.BulkDownload.equals(pageName) || PageName.emailFundraisingContact.equals(pageName)
				|| PageName.emailCapitalCallNotice.equals(pageName) || PageName.emailCapitalCallNotice.equals(pageName)
				|| PageName.Send_Distribution_Notices.equals(pageName) || PageName.BulkEmail.equals(pageName)) {

			if (TopOrBottom.TOP.equals(topOrBottom)) {
				xpath = "(//div[@class='step_2']//a[@title='Previous'])[1]";
			} else {
				xpath = "(//div[@class='step_2']//a[@title='Previous'])[2]";
			}

		} else {

		}
		ele = FindElement(driver, xpath, "Step 2 Previous Btn : " + topOrBottom, action.SCROLLANDBOOLEAN, timeOut);
		return ele;

	}

	public WebElement getStep1CancelBtn(PageName pageName, TopOrBottom topOrBottom, int timeOut) {

		WebElement ele = null;
		String xpath = null;
		if (PageName.BulkDownload.equals(pageName) || PageName.emailFundraisingContact.equals(pageName)
				|| PageName.emailCapitalCallNotice.equals(pageName)
				|| PageName.Send_Distribution_Notices.equals(pageName)
				|| PageName.emailCapitalCallNotice.equals(pageName) || PageName.BulkEmail.equals(pageName)) {

			if (TopOrBottom.TOP.equals(topOrBottom)) {
				xpath = "(//div[@class='step_1']//a[@title='Cancel'])[1]";
			} else {
				xpath = "(//div[@class='step_1']//a[@title='Cancel'])[2]";
			}

		} else {

		}
		ele = FindElement(driver, xpath, "Step1 Cancel Btn : " + topOrBottom, action.SCROLLANDBOOLEAN, timeOut);
		return ele;

	}

	public WebElement getStep2CancelBtn(PageName pageName, TopOrBottom topOrBottom, int timeOut) {

		WebElement ele = null;
		String xpath = null;
		if (PageName.BulkDownload.equals(pageName) || PageName.emailFundraisingContact.equals(pageName)
				|| PageName.emailCapitalCallNotice.equals(pageName)
				|| PageName.Send_Distribution_Notices.equals(pageName) || PageName.BulkEmail.equals(pageName)) {

			if (TopOrBottom.TOP.equals(topOrBottom)) {
				xpath = "(//div[@class='step_2']//a[@title='Cancel'])[1]";
			} else {
				xpath = "(//div[@class='step_2']//a[@title='Cancel'])[2]";
			}

		} else {

		}
		ele = FindElement(driver, xpath, "Step 2 Cancel Btn : " + topOrBottom, action.SCROLLANDBOOLEAN, timeOut);
		return ele;

	}

	public WebElement getStep2NextBtn(PageName pageName, TopOrBottom topOrBottom, int timeOut) {

		WebElement ele = null;
		String xpath = null;
		if (PageName.BulkDownload.equals(pageName) || PageName.emailFundraisingContact.equals(pageName)
				|| PageName.emailCapitalCallNotice.equals(pageName)
				|| PageName.Send_Distribution_Notices.equals(pageName) || PageName.BulkEmail.equals(pageName)) {

			if (TopOrBottom.TOP.equals(topOrBottom)) {
				xpath = "(//div[@class='step_2']//a[@title='Next'])[1]";
			} else {
				xpath = "(//div[@class='step_2']//a[@title='Next'])[2]";
			}

		} else if (PageName.DealPage.equals(pageName)) {

			if (TopOrBottom.TOP.equals(topOrBottom)) {
				xpath = "(//div[@id='op2']//a[@title='Next'])[1]";
			} else {
				xpath = "(//div[@id='op2']//a[@title='Next'])[2]";
			}

		} else {

		}
		ele = FindElement(driver, xpath, "Step 2 Next Btn : " + topOrBottom, action.SCROLLANDBOOLEAN, timeOut);
		return ele;

	}

	public WebElement getStep3PreviousBtn(PageName pageName, TopOrBottom topOrBottom, int timeOut) {

		WebElement ele = null;
		String xpath = null;
		if (PageName.BulkDownload.equals(pageName) || PageName.Send_Distribution_Notices.equals(pageName)
				|| PageName.BulkEmail.equals(pageName)) {

			if (TopOrBottom.TOP.equals(topOrBottom)) {
				xpath = "(//div[@class='step_3']//a[@title='Previous'])[1]";
			} else {
				xpath = "(//div[@class='step_3']//a[@title='Previous'])[2]";
			}

		} else if (PageName.DealPage.equals(pageName)) {

			if (TopOrBottom.TOP.equals(topOrBottom)) {
				xpath = "(//div[@id='op3']//a[@title='Previous'])[1]";
			} else {
				xpath = "(//div[@id='op3']//a[@title='Previous'])[2]";
			}

		} else if (PageName.emailFundraisingContact.equals(pageName)
				|| PageName.emailCapitalCallNotice.equals(pageName)) {

			if (TopOrBottom.TOP.equals(topOrBottom)) {
				xpath = "(//div[@class='step_3']//a[text()='Previous'])[1]";
			} else {
				xpath = "(//div[@class='step_3']//a[text()='Previous'])[2]";
			}

		}
		ele = FindElement(driver, xpath, "Step3 Previous Btn : " + topOrBottom, action.SCROLLANDBOOLEAN, timeOut);
		return ele;

	}

	public WebElement getStep3CancelBtn(PageName pageName, TopOrBottom topOrBottom, int timeOut) {

		WebElement ele = null;
		String xpath = null;
		if (PageName.BulkDownload.equals(pageName) || PageName.emailFundraisingContact.equals(pageName)
				|| PageName.emailCapitalCallNotice.equals(pageName)
				|| PageName.Send_Distribution_Notices.equals(pageName) || PageName.BulkEmail.equals(pageName)) {

			if (TopOrBottom.TOP.equals(topOrBottom)) {
				xpath = "(//div[@class='step_3']//a[@title='Cancel'])[1]";
			} else {
				xpath = "(//div[@class='step_3']//a[@title='Cancel'])[2]";
			}

		} else {

		}
		ele = FindElement(driver, xpath, "Step 3 Cancel Btn : " + topOrBottom, action.SCROLLANDBOOLEAN, timeOut);
		return ele;

	}

	public WebElement getStep3SendBtn(PageName pageName, TopOrBottom topOrBottom, int timeOut) {

		WebElement ele = null;
		String xpath = null;
		if (PageName.DealPage.equals(pageName) || PageName.Send_Distribution_Notices.equals(pageName)
				|| PageName.BulkEmail.equals(pageName)) {

			if (TopOrBottom.TOP.equals(topOrBottom)) {
				xpath = "(//div[@id='op3']//a[@title='Send'])[1]";
			} else {
				xpath = "(//div[@id='op3']//a[@title='Send'])[2]";
			}

		} else {

		}
		ele = FindElement(driver, xpath, "Step 3 Send Btn : " + topOrBottom, action.SCROLLANDBOOLEAN, timeOut);
		return ele;

	}

	/**
	 * @author Ankit Jaiswal
	 * @param addRemoveTabName
	 * @param customTabActionType
	 * @return list
	 */
	public List<String> addRemoveCustomTab(String addRemoveTabName, customTabActionType customTabActionType) {
		List<String> result = new ArrayList<String>();
		String[] splitedTabs = addRemoveTabName.split(",");
		if (click(driver, getAllTabBtn(60), "All Tab Button", action.SCROLLANDBOOLEAN)) {
			appLog.info("clicked on all tabs icon");
			if (click(driver, getAddTabLink(60), "Add a Tab Link", action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on add a tab link");
				if (customTabActionType.toString().equalsIgnoreCase("Add")) {
					System.err.println("inside Add");
					for (int i = 0; i < splitedTabs.length; i++) {
						if (selectVisibleTextFromDropDown(driver, getAvailableTabList(60), "Available Tab List",
								splitedTabs[i])) {
							appLog.info(splitedTabs[i] + " is selected successfully in available tabs");
							if (click(driver, getCustomTabAddBtn(60), "Custom Tab Add Button",
									action.SCROLLANDBOOLEAN)) {
								appLog.error("clicked on add button");
							} else {
								result.add("Not able to click on add button so cannot add custom tabs");
								appLog.error("Not able to click on add button so cannot add custom tabs");
							}
						} else {
							appLog.error(splitedTabs[i] + " custom tab name is not Available list Tab.");
							result.add(splitedTabs[i] + " custom tab name is not Available list Tab.");
						}
					}
				} else if (customTabActionType.toString().equalsIgnoreCase("Remove")) {
					System.err.println("inside remove");
					for (int i = 0; i < splitedTabs.length; i++) {
						if (selectVisibleTextFromDropDown(driver, getCustomTabSelectedList(60), "Selected Tab List",
								splitedTabs[i])) {
							appLog.info(splitedTabs[i] + " is selected successfully in Selected tabs");
							if (click(driver, getCustomTabRemoveBtn(60), "Remove Button", action.SCROLLANDBOOLEAN)) {
								appLog.error("clicked on remove button");
							} else {
								result.add("Not able to click on add button so cannot add custom tabs");
								appLog.error("Not able to click on add button so cannot add custom tabs");
							}
						} else {
							appLog.error(splitedTabs[i] + " custom tab name is not selected list Tab.");
							result.add(splitedTabs[i] + " custom tab name is not selected list Tab.");
						}
					}
				} else {
					result.add(
							"custom tab action type is not mtached so cannot add or remove custom tab please pass correct arrgument");
					appLog.error(
							"custom tab action type is not mtached so cannot add or remove custom tab please pass correct arrgument");
				}

				if (click(driver, getCustomTabSaveBtn(60), "Custom Tab Save Button", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on save button");

				} else {
					result.add("Not able to click on save button so cannot save custom tabs");
					appLog.error("Not able to click on save button so cannot save custom tabs");
				}

			} else {
				result.add("Not able to click on add a tab link so cannot add custom tabs");
				appLog.error("Not able to click on add a tab link so cannot add custom tabs");
			}
		} else {
			result.add("Not able to click on all tabs icon so cannot add custom tabs");
			appLog.error("Not able to click on all tabs icon so cannot add custom tabs");
		}
		return result;
	}

	/**
	 * @return random 5 digit random number
	 */
	public String generateRandomNumber() {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(99999);
		String RandomNumber = String.valueOf(randomInt);
		return RandomNumber;
	}

	/**
	 * @param sortOrder
	 * @param elements
	 * @return true if sorting is correct
	 */
	public boolean checkSorting(SortOrder sortOrder, List<WebElement> elements) {
		List<String> ts = new ArrayList<String>();
		List<String> actual = new ArrayList<String>();
		CommonLib compare = new CommonLib();
		List<WebElement> ele = elements;
		boolean flag = true;
		int j = 0;
		for (int i = 0; i < ele.size(); i++) {
			// scrollDownThroughWebelement(driver, ele.get(i), "");
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
				BaseLib.sa.assertTrue(false, "coloumn is not sorted in " + sortOrder.toString() + " order"
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

	/**
	 * @return true if able to remove unused tab
	 */
	public boolean removeUnusedTabs() {
		WebElement ele = null;
		List<String> lst = new ArrayList<String>();
		ele = FindElement(driver, "//a[contains(@title,'Reports')]", "Reports tab", action.SCROLLANDBOOLEAN, 10);
		if (ele != null) {
			lst = addRemoveCustomTab("Reports", customTabActionType.Remove);
			if (!lst.isEmpty()) {
				for (int i = 0; i < lst.size(); i++) {
					BaseLib.sa.assertTrue(false, lst.get(i));
				}
			}
		}
		ThreadSleep(1000);
		ele = FindElement(driver, "//a[contains(@title,'Dashboards')]", "Dashboards tab", action.SCROLLANDBOOLEAN, 10);
		if (ele != null) {
			lst = addRemoveCustomTab("Dashboards", customTabActionType.Remove);
			lst.clear();
			if (!lst.isEmpty()) {
				for (int i = 0; i < lst.size(); i++) {
					BaseLib.sa.assertTrue(false, lst.get(i));
				}
			}
		}
		ThreadSleep(1000);
		ele = FindElement(driver, "//a[contains(@title,'Marketing')]", "Marketing Initiatives tab",
				action.SCROLLANDBOOLEAN, 10);
		if (ele != null) {
			lst = addRemoveCustomTab("Marketing Initiatives", customTabActionType.Remove);
			lst.clear();
			if (!lst.isEmpty()) {
				for (int i = 0; i < lst.size(); i++) {
					BaseLib.sa.assertTrue(false, lst.get(i));
				}
			}
		}
		ThreadSleep(1000);
		ele = FindElement(driver, "//a[contains(@title,'Navatar Setup')]", "Navatar setup tab", action.SCROLLANDBOOLEAN,
				10);
		if (ele != null) {
			lst = addRemoveCustomTab("Navatar Setup", customTabActionType.Remove);
			lst.clear();
			if (!lst.isEmpty()) {
				for (int i = 0; i < lst.size(); i++) {
					BaseLib.sa.assertTrue(false, lst.get(i));
				}
			}
		}
		ThreadSleep(1000);
		ele = FindElement(driver, "//a[contains(@title,'Navatar Deal')]", "Navatar Deal connect tab",
				action.SCROLLANDBOOLEAN, 10);
		if (ele != null) {
			lst = addRemoveCustomTab("Navatar Deal Connect", customTabActionType.Remove);
			lst.clear();
			if (!lst.isEmpty()) {
				for (int i = 0; i < lst.size(); i++) {
					BaseLib.sa.assertTrue(false, lst.get(i));
				}
			}
		}
		ThreadSleep(1000);
		ele = FindElement(driver, "//a[contains(@title,'Pipelines')]", "Pipelines tab", action.SCROLLANDBOOLEAN, 10);
		if (ele != null) {
			lst = addRemoveCustomTab("Pipelines", customTabActionType.Remove);
			lst.clear();
			if (!lst.isEmpty()) {
				for (int i = 0; i < lst.size(); i++) {
					BaseLib.sa.assertTrue(false, lst.get(i));
				}
			}
		}
		return true;
	}

	/**
	 * @param date
	 * @param dateFormat
	 * @param typeOfDate
	 * @return true if date matched
	 */
	public boolean verifyDate(String date, String dateFormat, String typeOfDate) {
		if (dateFormat == null) {
			if (date.contains(getDateAccToTimeZone("America/New_York", "M/dd/yyyy"))) {
				appLog.info(
						typeOfDate + " date is verified : " + getDateAccToTimeZone("America/New_York", "M/dd/yyyy"));
				return true;
			} else if (date.contains(getDateAccToTimeZone("America/New_York", "MM/dd/yyyy"))) {
				appLog.info(
						typeOfDate + " date is verified : " + getDateAccToTimeZone("America/New_York", "MM/dd/yyyy"));
				return true;
			} else if (date.contains(getDateAccToTimeZone("America/New_York", "dd/M/yyyy"))) {
				appLog.info(
						typeOfDate + " date is verified : " + getDateAccToTimeZone("America/New_York", "dd/M/yyyy"));
				return true;
			} else if (date.contains(getDateAccToTimeZone("America/New_York", "dd/MM/yyyy"))) {
				appLog.info(
						typeOfDate + " date is verified : " + getDateAccToTimeZone("America/New_York", "dd/MM/yyyy"));
				return true;
			} else if (date.contains(getDateAccToTimeZone("America/New_York", "M/d/yyyy"))) {
				appLog.info(typeOfDate + " date is verified : " + getDateAccToTimeZone("America/New_York", "M/d/yyyy"));
				return true;
			} else if (date.contains(getDateAccToTimeZone("America/New_York", "d/M/yyyy"))) {
				appLog.info(typeOfDate + " date is verified : " + getDateAccToTimeZone("America/New_York", "d/M/yyyy"));
				return true;
			} else {
				appLog.info(typeOfDate + " date is not verified. found result : " + date);
				appLog.info("Expected Date is : " + getDateAccToTimeZone("America/New_York", "M/dd/yyyy") + " or "
						+ getDateAccToTimeZone("America/New_York", "MM/dd/yyyy") + " or "
						+ getDateAccToTimeZone("America/New_York", "dd/M/yyyy") + " or "
						+ getDateAccToTimeZone("America/New_York", "dd/MM/yyyy") + " or "
						+ getDateAccToTimeZone("America/New_York", "M/d/yyyy"));
				return false;
			}
		} else {
			if (date.contains(getDateAccToTimeZone("America/New_York", dateFormat))) {
				appLog.info(typeOfDate + " date is verified : " + getDateAccToTimeZone("America/New_York", dateFormat));
				return true;
			} else {
				appLog.info(typeOfDate + " date is not verified. found result : " + date);
				appLog.info("Expected Date is : " + getDateAccToTimeZone("America/New_York", dateFormat) + " or "
						+ getDateAccToTimeZone("America/New_York", dateFormat) + " or "
						+ getDateAccToTimeZone("America/New_York", dateFormat) + " or "
						+ date.contains(getDateAccToTimeZone("America/New_York", dateFormat)));
				return false;
			}

		}

	}

	/**
	 * @return true if able to switch to Lighting from classic
	 */
	public boolean switchToLighting() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(0,0)");
		ThreadSleep(1000);
		if (getSettingLink_Lighting(10) != null) {
			appLog.info("Sales Force is Already open in Lighting mode.");
			return true;
		} else {
			ThreadSleep(2000);
			if (click(driver, getSwitchToLightingLink(60), "sales force lighting icon", action.SCROLLANDBOOLEAN)) {
				appLog.info("Sales Force is switched in Lighting mode successfully.");
				return true;
			} else {
				appLog.error("Not able to click on Lighting Link");
			}
		}
		return false;

	}

	/**
	 * @return true if able to switch to Classic from Lighting
	 */
	public boolean switchToClassic() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(0,0)");
		ThreadSleep(1000);
		if (getUserMenuTab(10) != null) {
			appLog.info("Sales Force is Already open in classic mode.");
			return true;
		} else {
			ThreadSleep(2000);
			if (click(driver, getSalesForceLightingIcon(30), "sales force lighting icon", action.SCROLLANDBOOLEAN)) {
				ThreadSleep(1000);
				if (click(driver, getSwitchToClassic(30), "sales force switch to classic link",
						action.SCROLLANDBOOLEAN)) {
					appLog.info("Sales Force is switched in classic mode successfully.");
					return true;
				} else {
					appLog.error("Not able to switch Classic.");
				}
			} else {
				appLog.error("Not able to click on Lighting Icon");
			}

		}
		return false;
	}

	/**
	 * 
	 * @return random emailID
	 */
	public String generateRandomEmailId() {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(99999);
		String contactEmail = ExcelUtils.readDataFromPropertyFile("gmailUserName");
		String[] EmailIDContact = contactEmail.split("@");
		String contactEmailID = EmailIDContact[0] + "+" + randomInt + "@gmail.com";
		return contactEmailID;
	}

	/**
	 * @param onlymail
	 * @return random emailID
	 */
	public String generateRandomEmailId(String onlymail) {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(99999);
		String contactEmail = onlymail;
		String[] EmailIDContact = contactEmail.split("@");
		String contactEmailID = EmailIDContact[0] + "+" + randomInt + "@gmail.com";
		return contactEmailID;
	}

	/**
	 * @param projectName
	 * @param gridSectionName
	 * @param timeOut
	 * @return true if able to click on link at Grid Section
	 */
	public boolean clickOnGridSection_Lightning(String projectName, RelatedList gridSectionName, int timeOut) {
		WebElement ele = null;
		boolean flag = false;
		String xpath1 = "//span[@title='" + gridSectionName + "']";
		ele = isDisplayed(driver,
				FindElement(driver, xpath1, gridSectionName.toString() + " link", action.SCROLLANDBOOLEAN, timeOut),
				"visibility", timeOut, gridSectionName.toString() + " link");
		if (click(driver, ele, gridSectionName.toString() + " link", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "clicked on " + gridSectionName.toString() + " link", YesNo.No);
			flag = true;
		} else {
			log(LogStatus.ERROR,
					"Not able to click on " + gridSectionName.toString() + " link so cannot verify error message",
					YesNo.Yes);
		}
		return flag;
	}

	/**
	 * @param tabToBeAdded
	 * @param timeOut
	 * @return true if all Tab added successfully
	 */
	public boolean addTab_Lighting(String tabToBeAdded, int timeOut) {

		String xpath;
		WebElement ele;
		boolean flag = true;
		if (click(driver, getPersonalizePencilIcon(timeOut), "Personalize Pencil Icon", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(2000);
			if (click(driver, getAddMoreItemsLink(timeOut), "Add More items Link", action.SCROLLANDBOOLEAN)) {
				ThreadSleep(2000);
				if (click(driver, getAllAddLink(timeOut), "All Link", action.SCROLLANDBOOLEAN)) {
					ThreadSleep(2000);
					click(driver, getAllAddLink(timeOut), "All Link", action.SCROLLANDBOOLEAN);
					ThreadSleep(2000);
					String[] tabs = tabToBeAdded.split(",");
					for (int i = 0; i < tabs.length; i++) {
						// sendKeys(driver, getsearchTabTextbox( timeOut), tabs[i],"search textbox",
						// action.BOOLEAN);
						xpath = "//h3[text()='" + tabs[i] + "']/..//preceding-sibling::label/div";
						ele = FindElement(driver, xpath, "Tab to be add : " + tabs[i], action.SCROLLANDBOOLEAN,
								timeOut);

						ThreadSleep(1000);
						if (ele != null) {
							scrollDownThroughWebelement(driver, ele, "TABS : " + tabs[i]);
							if (click(driver, ele, "Tab to be add : " + tabs[i], action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Tab Added : " + tabs[i], YesNo.No);
							} else {
								flag = false;
								log(LogStatus.INFO, "Not Able to add Tab : " + tabs[i], YesNo.Yes);
							}

						} else {
							log(LogStatus.INFO, "Tab Already Added : " + tabs[i], YesNo.No);
						}

					}

					if (click(driver, getAddNavButton(timeOut), "Add Nav Button", action.SCROLLANDBOOLEAN)) {
						if (click(driver, getTabSaveButton(timeOut), "Save Button", action.SCROLLANDBOOLEAN)) {
						} else {
							log(LogStatus.FAIL, "Not Able to click on Save Button", YesNo.Yes);
							flag = false;
						}
					} else {
						log(LogStatus.FAIL, "Not Able to click on Add Nav Button", YesNo.Yes);
						flag = false;
					}
				} else {
					log(LogStatus.FAIL, "Not Able to click on All Link", YesNo.Yes);
					flag = false;
				}
			} else {
				log(LogStatus.FAIL, "Not Able to click on Add More items Link", YesNo.Yes);
				flag = false;
			}
		} else {
			log(LogStatus.FAIL, "Not Able to click on personalize Pencil Icon", YesNo.Yes);
			flag = false;
		}
		return flag;
	}

	/**
	 * @param projectName
	 * @param pageName
	 * @param relatedTab
	 * @param timeOut
	 * @return Related Tab WebElement
	 */
	public WebElement getRelatedTab(String projectName, PageName pageName, RelatedTab relatedTab, int timeOut) {
		String xpath = "";
		WebElement ele = null;
		String related = relatedTab.toString().replace("_", " ");
		if (projectName.contains(ProjectName.PE.toString()))
			xpath = "//li[@title='" + related + "']//a";
		else
			xpath = "//li//*[@title='" + related + "' or text()='" + related + "']";
		xpath = "//li//*[@title='" + related + "' or text()='" + related + "']";

		List<WebElement> list = FindElements(driver, xpath, "");

		for (WebElement element : list) {
			ele = isDisplayed(driver, element, "visiblity", 30, relatedTab.toString());
			if (ele != null) {
				ele = element;
				break;
			} else {
				appLog.info("Element not visible going to check in another iteration : ");
			}
		}

		if (ele != null) {
			appLog.info("Element Found : " + related);
		} else {
			appLog.error("Element Not Found : " + related);
			appLog.error("Going to check on more " + related);
			xpath = "//li//button[@title='More Tabs']";
			ele = FindElement(driver, xpath, relatedTab.toString(), action.SCROLLANDBOOLEAN, timeOut);
			click(driver, ele, "More Tab", action.BOOLEAN);
			ThreadSleep(3000);

			xpath = "//a/span[text()='" + related + "']";
			List<WebElement> list2 = FindElements(driver, xpath, "");

			for (WebElement element : list2) {
				ele = isDisplayed(driver, element, "visiblity", 30, relatedTab.toString());
				if (ele != null) {
					ele = element;
					break;
				} else {
					appLog.info("Element not visible going to check in another iteration : ");
				}
			}

		}
		return ele;

	}

	//////////////////////////////////////////////// Activity Association
	//////////////////////////////////////////////// ///////////////////////////////////////////////////////////////////

	public WebElement getComponentNoDataToDisplayMessage(String componentTab, int timeOut) {
		WebElement ele = null;

		String xpath = "//p[text()='No data to display.']/ancestor::div//li[@title='"
				+ componentTab.replaceAll("_", " ") + "']";

		ele = isDisplayed(driver,
				FindElement(driver, xpath, "No data to display message of tab:", action.BOOLEAN, timeOut), "visiblity",
				30, "No data to display message of tab:" + componentTab);

		return ele;
	}

	public WebElement getHyperLinkAtConnectionComponent(String contactName) {
		WebElement ele = null;
		String xpath = "//a[text()='" + contactName
				+ "']//ancestor::div[@class='slds-grid slds-wrap']/*/div[contains(@class,'ColumnFooter')]//a";
		List<WebElement> list = FindElements(driver, xpath, "");

		for (WebElement element : list) {
			ele = isDisplayed(driver, element, "visiblity", 30, "Connection hyperlink" + contactName);
			if (ele != null) {
				ele = element;
				break;
			} else {
				appLog.info("Element not visible going to check in another iteration : ");
			}
		}

		return ele;
	}

	public List<WebElement> getComponentInsAndContactNameLinkList() {
		List<WebElement> ele = new ArrayList<>();
		WebElement ele2 = null;
		String xpath = "//div[@class='slds-is-relative cls_relativePos']//a";
		ThreadSleep(5000);
		List<WebElement> list = FindElements(driver, xpath, "");

		for (WebElement element : list) {
			ele2 = isDisplayed(driver, element, "visiblity", 5, "No data to display message of tab:");
			if (ele != null) {
				ele.add(element);
			} else {
				appLog.info("Element not visible going to check in another iteration : ");
			}
		}

		return ele;
	}

	/**
	 * @param projectName
	 * @param TabName
	 * @return true if able to click on Tab
	 */
	public boolean clickOnTab(String projectName, TabName TabName) {

		String tabName = null;
		boolean flag = false;
		WebElement ele;
		tabName = getTabName(projectName, TabName);
		System.err.println("Passed switch statement");
		if (tabName != null) {
			ele = FindElement(driver, "//a[contains(@href,'lightning') and contains(@title,'" + tabName + "')]/span/..",
					tabName, action.SCROLLANDBOOLEAN, 30);
			ele = isDisplayed(driver, ele, "visibility", 30, tabName);
			if (ele != null) {
				appLog.info("Tab Found");
				ThreadSleep(5000);
				if (clickUsingJavaScript(driver, ele, tabName + " :Tab")) {
					CommonLib.log(LogStatus.INFO, "Tab found", YesNo.No);
					appLog.info("Clicked on Tab : " + tabName);
					flag = true;
				} else {
					appLog.error("Not Able to Click on Tab : " + tabName);
				}

			} else {
				CommonLib.log(LogStatus.INFO, "Going to found tab after clicking on More Icon", YesNo.No);
				if (click(driver, getMoreTabIcon(projectName, 10), "More Icon", action.SCROLLANDBOOLEAN)) {
					ele = FindElement(driver,
							"//a[contains(@href,'lightning')]/span[@class='slds-truncate']/span[contains(text(),'"
									+ tabName + "')]",
							tabName, action.SCROLLANDBOOLEAN, 10);
					ele = isDisplayed(driver, ele, "visibility", 10, tabName);
					if (ele != null) {
						if (clickUsingJavaScript(driver, ele, tabName + " :Tab")) {
							appLog.info("Clicked on Tab on More Icon: " + tabName);
							CommonLib.log(LogStatus.INFO, "Tab found on More Icon", YesNo.No);
							flag = true;
						}
					}

				} else {
					appLog.error("Not Able to Clicked on Tab on More Icon: " + tabName);
				}

			}
		}

		return flag;
	}

	public boolean clickOnAlreadyCreated_Lighting(String environment, String mode, TabName tabName,
			String alreadyCreated, int timeout) {

		String viewList = null;
		switch (tabName) {
		case ContactTab:
			viewList = "All Contacts";
			break;
		case InstituitonsTab:
			viewList = "All Firms";
			break;
		case CompaniesTab:
			viewList = "All Companies";
			break;
		case LimitedPartner:
			viewList = "All Limited Partners";
			break;
		case FundraisingsTab:
			viewList = "All";
			break;
		case FundsTab:
			viewList = "All";
			break;
		case CommitmentsTab:
			viewList = "All";
			break;
		case PartnershipsTab:
			viewList = "All";
			break;
		case FundDistributions:
			viewList = "All";
			break;
		case InvestorDistributions:
			viewList = "All";
			break;
		case MarketingInitiatives:
			viewList = "All";
			break;
		case MarketingProspects:
			viewList = "Marketing Prospects";
			break;
		case Pipelines:
			viewList = "All";
			break;
		case CapitalCalls:
			viewList = "All";
			break;
		case FundDrawdowns:
			viewList = "All";
			break;
		case FundraisingContacts:
			viewList = "All";
			break;
		case OfficeLocations:
			viewList = "All";
			break;
		default:
			return false;
		}
		System.err.println("Passed switch statement");
		WebElement ele, selectListView;
		ele = null;
		if (click(driver, getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(3000);
			selectListView = FindElement(driver, "//div[@class='listContent']//li/a/span[text()='" + viewList + "']",
					"Select List View", action.SCROLLANDBOOLEAN, 30);
			if (click(driver, selectListView, "select List View", action.SCROLLANDBOOLEAN)) {
				ThreadSleep(3000);
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					refresh(driver);
					ThreadSleep(5000);
				}
				if (sendKeys(driver, getSearchIcon_Lighting(20), alreadyCreated + "\n", "Search Icon Text",
						action.SCROLLANDBOOLEAN)) {
					ThreadSleep(5000);
					ele = FindElement(driver,
							"//table[@data-aura-class='uiVirtualDataTable']//tbody//tr//th//span//a[text()='"
									+ alreadyCreated + "']",
							alreadyCreated, action.BOOLEAN, 30);
					ThreadSleep(2000);
					if (click(driver, ele, alreadyCreated, action.BOOLEAN)) {
						ThreadSleep(3000);
						return true;
					} else {
						appLog.error("Not able to Click on Already Created : " + alreadyCreated);
					}
				} else {
					appLog.error("Not able to enter value on Search Box");
				}
			} else {
				appLog.error("Not able to select on Select View List");
			}
		} else {
			appLog.error("Not able to click on Select List Icon");
		}
		return false;
	}

	public static String convertNumberAccordingToFormatWithoutCurrencySymbol(String number, String format) {

		double d = Double.parseDouble(number);
		DecimalFormat myFormatter = new DecimalFormat(format);
		String output = myFormatter.format(d);
		System.err.println(" outpurttt >>>> " + output);
		return output;

	}

	/**
	 * @param projectName
	 * @param TabName
	 * @return String for TabName
	 */
	public String getTabName(String projectName, TabName TabName) {
		String tabName = null;
		switch (TabName) {
		case HomeTab:
			tabName = "Home";
			break;
		case NavatarSetup:
			tabName = "Navatar Setup";
			break;
		case TestCustomObjectTab:
			tabName = tabCustomObj + "s";
			break;
		case Object1Tab:
			if (tabObj1.equalsIgnoreCase("Entity")) {
				tabName = "Entities";
			} else {
				tabName = tabObj1 + "s";
			}
			break;
		case Object2Tab:
			tabName = tabObj2 + "s";
			break;
		case Object3Tab:
			tabName = tabObj3 + "s";
			break;
		case Object4Tab:
			tabName = tabObj4 + "s";
			break;
		case Object5Tab:
			tabName = tabObj5 + "s";
			break;
		case Object6Tab:
			tabName = tabObj6 + "s";
			break;
		case Object7Tab:
			tabName = tabObj7 + "s";
			break;
		case SDGTab:
			tabName = "Sortable Data Grids";
			break;
		case AttendeeTab:
			tabName = "Attendies";
			break;
		case Entities:
			tabName = "Entities";
			break;
		case Deals:
			tabName = "Deals";
			break;
		case Marketing_Events:
			tabName = "Marketing Events";
			break;
		case TaskTab:
			tabName = "Tasks";
			break;
		case Deal_Team:
			tabName = "Deal Team";
			break;
		case RecycleBinTab:
			tabName = "Recycle Bin";
			break;
		case PartnershipsTab:
			tabName = "Partnerships";
			break;
		case CommitmentsTab:
			tabName = "Commitments";
			break;

		case InstituitonsTab:
			tabName = "Firms";
			break;

		case FundraisingsTab:
			tabName = "Fundraisings";
			break;

		default:
			return tabName;
		}
		return tabName;
	}

	/**
	 * @param projectName
	 * @param pageName
	 * @param labelFieldTextBox
	 * @param timeOut
	 * @return Label Text Box WebElemet
	 */
	public WebElement getLabelTextBox(String projectName, String pageName, String labelFieldTextBox, int timeOut) {

		WebElement ele = null;
		String labelTextBox = labelFieldTextBox.replace("_", " ");
		String xpath = "//*[text()='" + labelTextBox + "']/following-sibling::div/input";
		if (pageName.equalsIgnoreCase(PageName.NewTaskPage.toString())
				|| pageName.equalsIgnoreCase(PageName.TaskPage.toString()))
			xpath = "//*[text()='" + labelTextBox + "']/..//input";
		else if (pageName.equalsIgnoreCase(PageName.FundsPage.toString()))
			xpath = "//*[text()='" + labelTextBox + "']/following-sibling::div//input";
		else if (pageName.equalsIgnoreCase(PageName.FundraisingPage.toString()))
			xpath = "//*[text()='" + labelTextBox + "']/following-sibling::div//input";
		else if (pageName.equalsIgnoreCase(PageName.MEPageFromCalender.toString()))
			xpath = "//*[text()='" + labelTextBox + "']/../following-sibling::div//input";
		ele = FindElement(driver, xpath, labelTextBox, action.SCROLLANDBOOLEAN, timeOut);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, labelTextBox);
		return ele;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param tabName
	 * @param alreadyCreated
	 * @param timeout
	 * @return true if able to click on particular item on Particular tab
	 */
	public boolean clickOnAlreadyCreatedItem(String projectName, TabName tabName, String alreadyCreated, int timeout) {
		boolean flag = false;
		String xpath = "";
		String viewList = null;
		switch (tabName) {
		case InstituitonsTab:

			if (ProjectName.MNA.toString().equals(projectName)) {
				viewList = "All Accounts";
			} else {
				viewList = "All Firms";
			}
			break;

		case TestCustomObjectTab:
			viewList = "Automation All";
			break;
		case CompaniesTab:
			viewList = "All Companies";
			break;
		case Navigation:
			viewList = "All";
			break;
		case ContactTab:
			viewList = "All";
			break;
		case FundsTab:
			viewList = "All";
			break;
		case DealTab:
			viewList = "All";
			break;
		case TaskTab:
			viewList = "All";
			break;
		case Object1Tab:
			viewList = "All";
			break;
		case Object2Tab:
			viewList = "All";
			break;
		case Object3Tab:
			viewList = "All";
			break;
		case Object4Tab:
			viewList = "All";
			break;
		case Object5Tab:
			viewList = "All";
			break;
		case Object6Tab:
			viewList = "All";
			break;
		case Object7Tab:
			viewList = "All";
			break;
		case NavatarSetup:
			viewList = "All";
			break;
		case RecycleBinTab:
			viewList = "My Recycle Bin";
			break;
		case SDGTab:
			viewList = "All";
			break;
		case Events:
			viewList = "All";
			break;

		default:
			return false;
		}
		System.err.println("Passed switch statement");
		WebElement ele, selectListView;
		ele = null;

		refresh(driver);
		if (TabName.RecycleBinTab.equals(tabName)) {

		} else {
			if (click(driver, getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
				ThreadSleep(3000);
				xpath = "//div[@class='listContent']//li/a/span[text()='" + viewList + "']";
				selectListView = FindElement(driver, xpath, "Select List View : " + viewList, action.SCROLLANDBOOLEAN,
						30);
				if (click(driver, selectListView, "select List View : " + viewList, action.SCROLLANDBOOLEAN)) {
				} else {
					appLog.error("Not able to select on Select View List : " + viewList);
				}
			} else {
				appLog.error("Not able to click on Select List Icon");
			}
		}

		ThreadSleep(3000);
		ThreadSleep(5000);

		if (sendKeys(driver, getSearchIcon_Lighting(20), alreadyCreated + "\n", "Search Icon Text",
				action.SCROLLANDBOOLEAN)) {
			ThreadSleep(5000);

			if (tabName.toString().contains("Task") || tabName.toString().equalsIgnoreCase("Task")) {
				xpath = "//a[text()='" + alreadyCreated + "']";
			} else {
				xpath = "//table[@data-aura-class='uiVirtualDataTable']//tbody//tr//th//span//*[text()='"
						+ alreadyCreated + "']";
			}

			ele = FindElement(driver, xpath, alreadyCreated, action.BOOLEAN, 30);
			ThreadSleep(2000);

			if (click(driver, ele, alreadyCreated, action.BOOLEAN)) {
				ThreadSleep(3000);
				click(driver, getPagePopUp(projectName, 5), "Page PopUp", action.BOOLEAN);
				flag = true;
			} else {
				appLog.error("Not able to Click on Already Created : " + alreadyCreated);
			}
		} else {
			appLog.error("Not able to enter value on Search Box");
		}

		return flag;
	}

	/**
	 * @param projectName
	 * @param alreadyCreated
	 * @param timeout
	 * @return true if able to click on particular item on Particular tab
	 */
	public boolean clickOnAlreadyCreatedItem(String projectName, String alreadyCreated, int timeout) {
		boolean flag = false;
		String xpath = "";
		String viewList = null;
		viewList = "All";
		WebElement ele, selectListView;
		ele = null;

		refresh(driver);
		if (click(driver, getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(3000);
			xpath = "//div[@class='listContent']//li/a/span[text()='" + viewList + "']";
			selectListView = FindElement(driver, xpath, "Select List View : " + viewList, action.SCROLLANDBOOLEAN, 30);
			if (click(driver, selectListView, "select List View : " + viewList, action.SCROLLANDBOOLEAN)) {
				ThreadSleep(3000);
				ThreadSleep(5000);

				if (sendKeys(driver, getSearchIcon_Lighting(20), alreadyCreated + "\n", "Search Icon Text",
						action.SCROLLANDBOOLEAN)) {
					ThreadSleep(5000);

					xpath = "//table[@data-aura-class='uiVirtualDataTable']//tbody//tr//th//span//*[text()='"
							+ alreadyCreated + "']";
					ele = FindElement(driver, xpath, alreadyCreated, action.BOOLEAN, 30);
					ThreadSleep(2000);

					if (click(driver, ele, alreadyCreated, action.BOOLEAN)) {
						ThreadSleep(3000);
						click(driver, getPagePopUp(projectName, 5), "Page PopUp", action.BOOLEAN);
						flag = true;
					} else {
						appLog.error("Not able to Click on Already Created : " + alreadyCreated);
					}
				} else {
					appLog.error("Not able to enter value on Search Box");
				}
			} else {
				appLog.error("Not able to select on Select View List : " + viewList);
			}
		} else {
			appLog.error("Not able to click on Select List Icon");
		}
		return flag;
	}

	/**
	 * @param projectName
	 * @param alreadyCreated
	 * @param timeout
	 * @return true if able to click on first item on Particular tab
	 */
	public boolean clickOnAlreadyCreatedFirstItem(String projectName, int timeout) {
		boolean flag = false;
		String xpath = "";
		String viewList = null;
		viewList = "All";
		WebElement ele, selectListView;
		ele = null;

		refresh(driver);
		if (click(driver, getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(3000);
			xpath = "//div[@class='listContent']//li/a/span[text()='" + viewList + "']";
			selectListView = FindElement(driver, xpath, "Select List View : " + viewList, action.SCROLLANDBOOLEAN, 30);
			if (click(driver, selectListView, "select List View : " + viewList, action.SCROLLANDBOOLEAN)) {
				ThreadSleep(5000);

				xpath = "//table[@data-aura-class='uiVirtualDataTable']//tbody//tr//th//span//a";
				ele = FindElement(driver, xpath, "First elelment of row", action.BOOLEAN, 30);
				ThreadSleep(2000);

				if (click(driver, ele, "first item", action.BOOLEAN)) {
					ThreadSleep(3000);
					click(driver, getPagePopUp(projectName, 5), "Page PopUp", action.BOOLEAN);
					flag = true;

				} else {
					appLog.error("Not able to click on created first item");
				}
			} else {
				appLog.error("Not able to select on Select View List : " + viewList);
			}
		} else {
			appLog.error("Not able to click on Select List Icon");
		}
		return flag;
	}

	/**
	 * @author Akul bhutani
	 * @param projectName
	 * @param userFullName
	 * @param subjectMeetingAssociationsCommentsDatePriorityName
	 * @param isMultiple
	 * @return true task UI verified
	 */
	// subject,meeting type, RA, comment,date,priority,contact name, status
	public boolean verifyUIOfCreateNewTaskWindow(String projectName, String userFullName,
			String[] subjectMeetingAssociationsCommentsDatePriorityName, boolean isMultiple) {
		boolean flag = true;
		String status = getValueFromElementUsingJavaScript(driver, getstatusDropdownInCreateNewTask(projectName, 20),
				"status dropdown");
		/*
		 * System.out.println("div value "+status); if
		 * (status.trim().contains(subjectMeetingAssociationsCommentsDatePriorityName[7]
		 * )) { log(LogStatus.INFO, "successfully verfied status dropdown", YesNo.No); }
		 * else { log(LogStatus.ERROR,
		 * "could not verify status dropdown. Found is "+status, YesNo.Yes); flag=false;
		 * }
		 */
		String name = "";
		WebElement ele = getCrossButtonForAlreadySelectedItem(projectName, PageName.Object1Page,
				PageLabel.Assigned_To.toString(), false, userFullName, action.SCROLLANDBOOLEAN, 10);
		if (ele != null) {
			log(LogStatus.INFO, "successfully verified user name on assigned to field", YesNo.No);
		} else {
			log(LogStatus.ERROR, "could not verify assigned to user name " + name, YesNo.No);
			flag = false;
		}
		name = getValueFromElementUsingJavaScript(driver,
				getLabelTextBox(projectName, PageName.TaskPage.toString(), PageLabel.Subject.toString(), 20),
				"subject");
		if (name.contains(subjectMeetingAssociationsCommentsDatePriorityName[0].trim())) {
			log(LogStatus.INFO, "successfully verified subject textbox", YesNo.No);
		} else {
			log(LogStatus.ERROR, "could not verify subject textbox, found: " + name, YesNo.No);
			flag = false;
		}
		if (subjectMeetingAssociationsCommentsDatePriorityName[6].equalsIgnoreCase("")) {
			name = getValueFromElementUsingJavaScript(driver, getnameTextBoxInNewTask(projectName, 20), "nameTextBox");
			if (name.contains("")) {
				log(LogStatus.INFO, "successfully verified empty name textbox", YesNo.No);
			} else {
				log(LogStatus.ERROR, "could not verify empty name textbox, found: " + name, YesNo.No);
				flag = false;
			}
		} else {
			List<WebElement> eleList = getAlreadySelectedItem(projectName, PageName.Object2Page,
					PageLabel.Name.toString(), isMultiple, action.SCROLLANDBOOLEAN, 15);
			if (compareMultipleList(driver, subjectMeetingAssociationsCommentsDatePriorityName[6], eleList).isEmpty()) {
				log(LogStatus.INFO, "successfully verified name textbox", YesNo.No);
			} else {
				log(LogStatus.ERROR, "could not verify name textbox, found: " + name, YesNo.No);
				flag = false;
			}

		}

		if (getdueDateTextBoxInNewTask(projectName, 20) != null) {
			// name= getdueDateTextBoxInNewTask(projectName, 20).getText().trim();
			name = getValueFromElementUsingJavaScript(driver, getdueDateTextBoxInNewTask(projectName, 20),
					"dueDateTextBoxInNewTask");
			if (subjectMeetingAssociationsCommentsDatePriorityName[4].equalsIgnoreCase("")) {
				if (name.equalsIgnoreCase(subjectMeetingAssociationsCommentsDatePriorityName[4])) {
					log(LogStatus.INFO, "successfully verified empty due date textbox", YesNo.No);
				} else {
					log(LogStatus.ERROR, "date not matched, actual : " + name + " expected: "
							+ subjectMeetingAssociationsCommentsDatePriorityName[4], YesNo.No);
					flag = false;
				}
			} else {
				if (verifyDate(subjectMeetingAssociationsCommentsDatePriorityName[4], name)) {
					log(LogStatus.INFO,
							"successfully verified dueDate textbox "
									+ subjectMeetingAssociationsCommentsDatePriorityName[4] + " contains " + name,
							YesNo.No);
				} else {
					log(LogStatus.ERROR, "could not verify dueDate textbox, found: " + name, YesNo.No);
					flag = false;
				}
			}
		} else {
			log(LogStatus.ERROR, "not visible on page dueDate textbox", YesNo.No);
			flag = false;
		}
		if (getmeetingTypeDropdown(projectName, 20) != null) {
			name = getValueFromElementUsingJavaScript(driver, getmeetingTypeDropdown(projectName, 20),
					"meetingTypeDropdown");
			if (name.trim().contains(subjectMeetingAssociationsCommentsDatePriorityName[1])) {
				log(LogStatus.INFO, "successfully verified " + subjectMeetingAssociationsCommentsDatePriorityName[1]
						+ " in meeting type dropdown", YesNo.No);
			} else {
				log(LogStatus.ERROR, "could not verify " + subjectMeetingAssociationsCommentsDatePriorityName[1]
						+ " in meeting type dropdown. Present: " + name, YesNo.No);
				flag = false;
			}
		} else {
			log(LogStatus.ERROR, "could not find meeting type dropdown", YesNo.No);
			flag = false;
		}
		name = getValueFromElementUsingJavaScript(driver, getPriorityDropdown(projectName, 20), "PriorityDropdown");
		if (name.trim().contains(subjectMeetingAssociationsCommentsDatePriorityName[5])) {
			log(LogStatus.INFO, "successfully verified " + subjectMeetingAssociationsCommentsDatePriorityName[5]
					+ " in PriorityDropdown", YesNo.No);
		} else {
			log(LogStatus.ERROR, "could not verify " + subjectMeetingAssociationsCommentsDatePriorityName[5]
					+ " in PriorityDropdown. Present: " + name, YesNo.No);
			flag = false;
		}
		ele = null;
		if (!subjectMeetingAssociationsCommentsDatePriorityName[2].equals("")) {
			String ra[] = subjectMeetingAssociationsCommentsDatePriorityName[2].split(",");

			for (int i = 0; i < ra.length; i++) {
				log(LogStatus.INFO, "trying to find associations " + ra[i], YesNo.No);
				ele = getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page,
						PageLabel.Related_Associations.toString(), true, ra[i], action.BOOLEAN, 10);
				if (ele != null) {
					log(LogStatus.INFO, "successfully found associations " + ra[i], YesNo.No);
				} else {
					log(LogStatus.ERROR, "could not find association " + ra[i], YesNo.Yes);
					flag = false;
				}
			}
		}
		/*
		 * List<String> s=compareMultipleList(driver,
		 * subjectMeetingAssociationsCommentsDatePriorityName[2], els); if (s.isEmpty())
		 * { log(LogStatus.INFO, "successfully found associations "
		 * +subjectMeetingAssociationsCommentsDatePriorityName[2], YesNo.No); } else {
		 * for (String print:s) { log(LogStatus.ERROR,
		 * "could not find association "+print, YesNo.Yes); flag=false; } }
		 */
		name = getcommentsTextBox(projectName, 20).getText();
		if (name.equals(subjectMeetingAssociationsCommentsDatePriorityName[3])) {
			log(LogStatus.INFO, "successfully verified comments textbox : " + name, YesNo.No);
		} else {
			log(LogStatus.ERROR, "could not verify comments textbox, found: "
					+ subjectMeetingAssociationsCommentsDatePriorityName[3], YesNo.No);
			flag = false;
		}
		if (getCustomTabSaveBtn(projectName, 10) != null)
			log(LogStatus.INFO, "successfully verified save button", YesNo.No);
		else {
			log(LogStatus.ERROR, "could not verify save button", YesNo.No);
			flag = false;

		}

		if (getdueDateTextBoxInNewTask(projectName, 20) != null) {
			// name= getdueDateTextBoxInNewTask(projectName, 20).getText().trim();
			name = getValueFromElementUsingJavaScript(driver, getdueDateTextBoxInNewTask(projectName, 20),
					"dueDateTextBoxInNewTask");
			if (subjectMeetingAssociationsCommentsDatePriorityName[4].equalsIgnoreCase("")) {
				if (name.equalsIgnoreCase(subjectMeetingAssociationsCommentsDatePriorityName[4])) {
					log(LogStatus.INFO, "successfully verified empty due date textbox", YesNo.No);
				} else {
					log(LogStatus.ERROR, "date not matched, actual : " + name + " expected: "
							+ subjectMeetingAssociationsCommentsDatePriorityName[4], YesNo.No);
					flag = false;
				}
			} else {
				if (verifyDate(subjectMeetingAssociationsCommentsDatePriorityName[4], name)) {
					log(LogStatus.INFO,
							"successfully verified dueDate textbox "
									+ subjectMeetingAssociationsCommentsDatePriorityName[4] + " contains " + name,
							YesNo.No);
				} else {
					log(LogStatus.ERROR, "could not verify dueDate textbox, found: " + name, YesNo.No);
					flag = false;
				}
			}
		} else {
			log(LogStatus.ERROR, "not visible on page dueDate textbox", YesNo.No);
			flag = false;
		}
		if (getmeetingTypeDropdown(projectName, 20) != null) {
			name = getValueFromElementUsingJavaScript(driver, getmeetingTypeDropdown(projectName, 20),
					"meetingTypeDropdown");
			if (name.trim().contains(subjectMeetingAssociationsCommentsDatePriorityName[1])) {
				log(LogStatus.INFO, "successfully verified " + subjectMeetingAssociationsCommentsDatePriorityName[1]
						+ " in meeting type dropdown", YesNo.No);
			} else {
				log(LogStatus.ERROR, "could not verify " + subjectMeetingAssociationsCommentsDatePriorityName[1]
						+ " in meeting type dropdown. Present: " + name, YesNo.No);
				flag = false;
			}
		} else {
			log(LogStatus.ERROR, "could not find meeting type dropdown", YesNo.No);
			flag = false;
		}
		name = getValueFromElementUsingJavaScript(driver, getPriorityDropdown(projectName, 20), "PriorityDropdown");
		if (name.trim().contains(subjectMeetingAssociationsCommentsDatePriorityName[5])) {
			log(LogStatus.INFO, "successfully verified " + subjectMeetingAssociationsCommentsDatePriorityName[5]
					+ " in PriorityDropdown", YesNo.No);
		} else {
			log(LogStatus.ERROR, "could not verify " + subjectMeetingAssociationsCommentsDatePriorityName[5]
					+ " in PriorityDropdown. Present: " + name, YesNo.No);
			flag = false;
		}
		ele = null;
		if (!subjectMeetingAssociationsCommentsDatePriorityName[2].equals("")) {
			String ra[] = subjectMeetingAssociationsCommentsDatePriorityName[2].split(",");

			for (int i = 0; i < ra.length; i++) {
				log(LogStatus.INFO, "trying to find associations " + ra[i], YesNo.No);
				ele = getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page,
						PageLabel.Related_Associations.toString(), true, ra[i], action.BOOLEAN, 10);
				if (ele != null) {
					log(LogStatus.INFO, "successfully found associations " + ra[i], YesNo.No);
				} else {
					log(LogStatus.ERROR, "could not find association " + ra[i], YesNo.Yes);
					flag = false;
				}
			}
		}
		/*
		 * List<String> s=compareMultipleList(driver,
		 * subjectMeetingAssociationsCommentsDatePriorityName[2], els); if (s.isEmpty())
		 * { log(LogStatus.INFO, "successfully found associations "
		 * +subjectMeetingAssociationsCommentsDatePriorityName[2], YesNo.No); } else {
		 * for (String print:s) { log(LogStatus.ERROR,
		 * "could not find association "+print, YesNo.Yes); flag=false; } }
		 */
		name = getcommentsTextBox(projectName, 20).getText();
		if (name.equals(subjectMeetingAssociationsCommentsDatePriorityName[3])) {
			log(LogStatus.INFO, "successfully verified comments textbox : " + name, YesNo.No);
		} else {
			log(LogStatus.ERROR, "could not verify comments textbox, found: "
					+ subjectMeetingAssociationsCommentsDatePriorityName[3], YesNo.No);
			flag = false;
		}
		if (getCustomTabSaveBtn(projectName, 10) != null)
			log(LogStatus.INFO, "successfully verified save button", YesNo.No);
		else {
			log(LogStatus.ERROR, "could not verify save button", YesNo.No);
			flag = false;

		}
		if (getcancelButton(projectName, 10) != null)
			log(LogStatus.INFO, "successfully verified cancel button", YesNo.No);
		else {
			log(LogStatus.ERROR, "could not verify cancel button", YesNo.No);
			flag = false;

		}
		return flag;
	}

	/**
	 * @param projectName
	 * @param pageName
	 * @param headerText
	 * @param action
	 * @param timeOut
	 * @return Header Text WebElement
	 */
	public WebElement getHeaderTextForPage(String projectName, PageName pageName, String headerText1, action action,
			int timeOut) {
		String xpath = "";
		WebElement ele;
		String headerText = headerText1.replace("_", " ");
		if (PageName.CRMUserPage.toString().equals(pageName.toString())) {
			xpath = "//b/span[text()='" + headerText + "']";
		} else if (PageName.NewTaskPopUP.toString().equals(pageName.toString())) {
			xpath = "//h2[text()='" + headerText + "']";
		} else if (PageLabel.New_Task.toString().equals(headerText1)) {
			xpath = "//h2[contains(text(),'New')]";
			// [contains(text(),'Task')]
			// xpath="//h2[contains(text(),'New ')]";
		} else if (PageName.TaskPage == pageName || PageName.Object2Page == pageName) {
			xpath = "//*[text()='" + headerText + "']";
		} else if (PageName.ListEmail == pageName) {
			xpath = "//h1[contains(text(),'" + headerText + "')]";
		} else if (PageName.Object1PagePopup == pageName) {
			xpath = "//h2[contains(text(),'" + headerText + "')]";
		} else {
			// xpath="//*[text()='"+headerText+"']";
			xpath = "//h2[contains(text(),'New ')]";
		}

		ele = FindElement(driver, xpath, "Header Text : " + headerText, action, timeOut);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Header Text : " + headerText);
		return ele;
	}

	/**
	 * @param projectName
	 * @return List<WebElement>
	 */
	public List<WebElement> listOfObjectsInRelatedAssctions(String projectName) {
		return FindElements(driver, "//div[contains(@class,'slds-dropdown-trigger')]//div//ul//li//a",
				"list of objects in related associations");
	}

	/**
	 * @param projectName
	 * @param record
	 * @return remove Button In Related Associations Field Webelement
	 */
	public WebElement removeButtonInRelatedAssociations(String projectName, String record) {
		return isDisplayed(driver, FindElement(driver,
				"//div[@id='relatedAssociation']//span[text()='" + record
						+ "']/../following-sibling::button[@title='Remove']",
				"removeButton", action.BOOLEAN, 10), "visibility", 5, "removeButton");
	}

	/**
	 * @param projectName
	 * @param pageName
	 * @param relatedTab
	 * @param date
	 * @param subjectName
	 * @param contactName
	 * @param relatedTo
	 * @param plusCount
	 * @param status
	 * @param owner
	 * @param meetingType
	 * @param activity
	 * @param commentsLink
	 * @param action
	 * @param timeOut
	 * @return webelemnt for grid row on Related Tab
	 */
	public WebElement verifyingRelatedTabData(String projectName, PageName pageName, RelatedTab relatedTab, String date,
			String subjectName, String contactName, String relatedTo, String plusCount, String status, String owner,
			String meetingType, String activity, String commentsLink, action action, int timeOut) {
		// String[] dateArr=date.split(",");
		WebElement ele;
		boolean flag = false;
		;
		status = status.replace("_", " ");
		String parentXpath = "//span[@id='Specify_the_recipients_to_include-rows']";
		String dateXpath = "//span[text()='" + date + "']";
		String subjectNameXpath = "/following-sibling::span/a[text()='" + subjectName + "']";
		String contactNameNameXpath = "/../following-sibling::span/a[text()='" + contactName + "']";
		String relatedToXpath = "/..//following-sibling::span/a[contains(text(),'" + relatedTo + "')]";
		String plusCountXpath = "/following-sibling::a[contains(text(),'" + plusCount + "')]";
		String statusXpath = "/..//following-sibling::span[contains(text(),'" + status + "')]";
		String ownerXpath = "//following-sibling::span/a[text()='" + owner + "']";
		String meetingTypeXpath = "/..//following-sibling::span[contains(text(),'" + meetingType + "')]";
		String activityXpath = "//following-sibling::span[text()='" + activity + "']";
		String commentsLinkXpath = "//following-sibling::span/a[text()='" + commentsLink + "']";
		if (relatedTo == null && plusCount == null)
			relatedToXpath = "";
		plusCountXpath = "/..";
		String xpath = parentXpath + dateXpath + subjectNameXpath + contactNameNameXpath + relatedToXpath
				+ plusCountXpath + statusXpath + ownerXpath + meetingTypeXpath + activityXpath + commentsLinkXpath;

		ele = FindElement(driver, xpath,
				"Grid Data on  " + pageName.toString() + " for related Tab : " + relatedTab.toString(), action,
				timeOut);
		return ele;
	}

	/**
	 * @param projectName
	 * @param pageName
	 * @param label
	 * @param isMultipleAssociation
	 * @param name
	 * @param action
	 * @param timeOut
	 * @return Cross Button for already selected item for field WebElement in Task
	 *         popup
	 */
	public WebElement getCrossButtonForAlreadySelectedItem(String projectName, PageName pageName, String label,
			boolean isMultipleAssociation, String name, action action, int timeOut) {
		String xpath = "";
		WebElement ele;
		String fieldlabel = label.replace("_", " ");
		appLog.info(" >>>>>>>>>>>>>>>>   label:" + label);
		if (fieldlabel.equalsIgnoreCase("User") && PageName.TaskPage.toString().equalsIgnoreCase(pageName.toString())) {

			xpath = "//span[text()='" + name + "']/..//following-sibling::lightning-button-icon/button";
			ele = FindElement(driver, xpath, "Cross Button For  : " + name + " For Label : " + fieldlabel, action,
					timeOut);
			return ele;
		}
		if (label.equalsIgnoreCase(PageLabel.Name.toString()) || label.equalsIgnoreCase("User"))
			isMultipleAssociation = true;
		if (PageName.CallPopUp.toString().equalsIgnoreCase(pageName.toString())
				|| (PageLabel.Name.toString().equalsIgnoreCase(label)
						&& PageName.TaskPage.toString().equalsIgnoreCase(pageName.toString())
						&& isMultipleAssociation)) {
			xpath = "//span[text()='" + name + "']/..//button[@title='Remove']";
		} else if (PageLabel.Related_To.toString().equalsIgnoreCase(label)
				|| PageLabel.Related_Associations.toString().equalsIgnoreCase(label) || isMultipleAssociation) {
			xpath = "//span[text()='" + fieldlabel + "']/../..//span[text()='" + name
					+ "']//following-sibling::a[@class='deleteAction']";
			// label[text()="Name"]/..//span[contains(@class,"customPill")]/span[text()="Davidson
			// Bendt"]/following-sibling::button

		} else {
			xpath = "//label[text()='" + fieldlabel + "']/..//span[contains(@class,'pillSize')]//span[text()='" + name
					+ "']/..//following-sibling::button";
		}
		ele = FindElement(driver, xpath, "Cross Button For  : " + name + " For Label : " + fieldlabel, action, timeOut);
		return ele;

	}

	/**
	 * @param projectName
	 * @param pageName
	 * @param label
	 * @param isMultipleAssociation
	 * @param action
	 * @param timeOut
	 * @return List<WebElement> of already selected item for Name/Related
	 *         Association on Task/Meeting Popup
	 */
	public List<WebElement> getAlreadySelectedItem(String projectName, PageName pageName, String label,
			boolean isMultipleAssociation, action action, int timeOut) {
		String xpath = "";
		List<WebElement> eleList;
		if (label.equalsIgnoreCase(PageLabel.Name.toString()))
			isMultipleAssociation = true;
		String fieldlabel = label.replace("_", " ");
		appLog.info(" >>>>>>>>>>>>>>>>   label:" + label);
		if (PageLabel.Related_To.toString().equalsIgnoreCase(label)
				|| PageLabel.Related_Associations.toString().equalsIgnoreCase(label) || isMultipleAssociation) {
			xpath = "//label[text()='" + fieldlabel + "']/..//span[contains(@class,'customPill')]//button/..//span[2]";
			// label[text()="Name"]/..//span[contains(@class,"customPill")]//button
		} else {
			xpath = "//label[text()='" + fieldlabel + "']/..//span[contains(@class,'pillSize')]//button/..//span[2]";
		}

		WebElement ele = FindElement(driver, xpath, "Already Selected item for Label : " + fieldlabel, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "");
		eleList = FindElements(driver, xpath, "Already Selected item for Label : " + fieldlabel);
		return eleList;

	}

	/**
	 * @param projectName
	 * @param pageName
	 * @param labelFieldTextBox
	 * @param action
	 * @param timeOut
	 * @return text boxt webelement on Task PoPuP
	 */
	public WebElement getLabelTextBoxForNameOrRelatedAssociationOnTask(String projectName, PageName pageName,
			String labelFieldTextBox, action action, int timeOut) {

		WebElement ele = null;
		String xpath = "";
		String labelTextBox = labelFieldTextBox.replace("_", " ");
		if (PageName.TaskPage.toString().equals(pageName.toString())
				|| PageName.NewEventPopUp.toString().equals(pageName.toString())
				|| PageName.GlobalActtion_TaskPOpUp.toString().equals(pageName.toString())) {
			if (labelFieldTextBox.equalsIgnoreCase(PageLabel.Name.toString()))
				xpath = "//span[text()='" + labelTextBox
						+ "']/..//following-sibling::div//input[@title='Search Contacts']";
			else
				xpath = "//span[text()='" + labelTextBox + "']/..//following-sibling::div//input";
		} else {
			xpath = "//span[text()='" + labelTextBox + "']/ancestor::div//input[@title='Search Contacts']";
		}

		ele = FindElement(driver, xpath, labelTextBox, action, timeOut);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, labelTextBox);
		return ele;
	}

	/**
	 * @param projectName
	 * @param pageName
	 * @param label
	 * @param action
	 * @param timeOut
	 * @return related Associations dropdown Button Webelement
	 */
	public WebElement getrelatedAssociationsdropdownButton(String projectName, PageName pageName, String label,
			action action, int timeOut) {

		String xpath = "";
		label = label.replace("_", " ");
		if (PageName.TaskPage.toString().equals(pageName.toString())
				|| PageName.NewEventPopUp.toString().equals(pageName.toString())
				|| PageName.GlobalActtion_TaskPOpUp.toString().equals(pageName.toString())) {
			xpath = "//span[text()='" + label + "']/../following-sibling::div//div[@class='uiPopupTrigger']//a";
		} else {
			xpath = "//label[text()='" + label + "']/..//div[contains(@class,'dropdownButton')]";
		}

		WebElement ele = FindElement(driver, xpath, "Drop Down For Label : " + label, action, timeOut);
		ele = FindElement(driver, xpath, "Drop Down For Label : " + label, action, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "Drop Down For Label : " + label);
	}

	/**
	 * @param projectName
	 * @param pageName
	 * @param label
	 * @param tabName
	 * @param action
	 * @param timeOut
	 * @return true if able to select Dopdown item on Related Association Field For
	 *         Meeting/Task
	 */
	public boolean SelectRelatedAssociationsdropdownButton(String projectName, PageName pageName, String label,
			TabName tabName, action action, int timeOut) {
		boolean flag = false;
		WebElement ele;
		String xpath = "";
		label = label.replace("_", " ");
		String tab = getTabName(projectName, tabName);
		for (int i = 0; i < 2; i++) {

			ele = getrelatedAssociationsdropdownButton(projectName, pageName, label, action, 5);
			if (click(driver, ele, "Drop Down Icon For Label : " + label, action)) {
				appLog.error("Clicked on  Drown Down Icon for LABEL : " + label);
				ThreadSleep(2000);
				if (PageName.TaskPage.toString().equals(pageName.toString())
						|| PageName.NewEventPopUp.toString().equals(pageName.toString())
						|| PageName.GlobalActtion_TaskPOpUp.toString().equals(pageName.toString())) {
					xpath = "//li//a[@title='" + tab + "']";
				} else {
					xpath = "//label[text()='" + label
							+ "']/..//div[contains(@class,'slds-dropdown-trigger')]//div//ul//li//a[text()='" + tab
							+ "']";
				}

				ele = FindElement(driver, xpath, "Drop Down For Value : " + tab, action, 5);
				if (clickUsingJavaScript(driver, ele, "Drop Down Value  : " + tab, action)) {
					appLog.info("Select Drown Down Value : " + tab + " for LABEL : " + label);
					return true;
				} else {
					appLog.error("Not Able to Select Drown Down Value : " + tab + " for LABEL : " + label);
				}

			} else {
				appLog.error("Not Able to Click on Drop Down Icon for LABEL : " + label);
			}
		}

		return flag;
	}

	/**
	 * @param projectName
	 * @param pageName
	 * @param label
	 * @param value
	 * @param action
	 * @param timeOut
	 * @return true if able to select Value on Drop Down
	 */
	public boolean SelectDropDownValue(String projectName, PageName pageName, String label, String value, action action,
			int timeOut) {
		boolean flag = false;
		WebElement ele;
		String xpath = "";
		label = label.replace("_", " ");
		if (PageName.TaskPage.toString().equals(pageName.toString())
				|| PageName.NewEventPopUp.toString().equals(pageName.toString())) {
			// xpath = "//span[text()='"+label+"']/../following-sibling::div";
			xpath = "//ul/li/a[@title='" + value + "']";
		} else {
			/*
			 * xpath = "//label[text()='" + label + "']/..//span[@title='" + value + "']";
			 */

			xpath = "//*[text()='" + label + "']/../..//a[text()='" + value + "']";
		}

		ele = FindElement(driver, xpath, "Drop Down : " + label + " value : " + value, action, timeOut);
		if (clickUsingJavaScript(driver, ele, "Drop Down Value : " + value, action)) {
			appLog.info("Selected " + value + " For : " + label);
			flag = true;
		} else {
			appLog.error("Not Able to Select " + value + " For : " + label);
		}
		return flag;
	}

	/**
	 * @param projectName
	 * @param pageName
	 * @param showMoreActionDropDownList
	 * @param timeOut
	 * @return true if able to click on Show more Icon
	 */
	public boolean clickOnShowMoreActionDownArrow(String projectName, PageName pageName,
			ShowMoreActionDropDownList showMoreActionDropDownList, int timeOut) {
		int i = 1;
		String xpath = "";
		WebElement ele;
		boolean flag = false;
		String actionDropDown = showMoreActionDropDownList.toString().replace("_", " ");

		if (clickOnShowMoreDropdownOnly(projectName, pageName, actionDropDown)) {
			ThreadSleep(3000);
			log(LogStatus.INFO, "clicked on show more actions icon", YesNo.No);
			if (pageName.equals(PageName.TaskPage))
				xpath = "//div[@role='menu']//li/a[@title='" + actionDropDown + "']";
			else if (pageName.equals(PageName.Object1Page) || pageName.equals(PageName.SDGPage))
				xpath = "//*[@role='menu']//span[text()='" + actionDropDown + "']";
			else
				xpath = "//div[@role='menu']//span[text()='" + actionDropDown + "']";
			ThreadSleep(3000);
			ele = FindElement(driver, xpath, "show more action down arrow : " + actionDropDown, action.BOOLEAN, 10);
			// mouseOverOperation(driver, ele);
			if (clickUsingJavaScript(driver, ele, "show more action on " + pageName.toString(), action.BOOLEAN)) {
				log(LogStatus.INFO, "clicked on " + actionDropDown + " link", YesNo.No);
				flag = true;
			} else {
				// log(LogStatus.ERROR, "Not able to click on "+actionDropDown+" link",
				// YesNo.Yes);
				xpath = "//button[@name='" + actionDropDown + "' or text()='" + actionDropDown + "']";
				ele = FindElement(driver, xpath, actionDropDown, action.BOOLEAN, 10);
				if (click(driver, ele, actionDropDown, action.SCROLLANDBOOLEAN)) {
					flag = true;
				} else {
					log(LogStatus.ERROR, "Not able to click on " + actionDropDown + " link", YesNo.Yes);
				}

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on show more action down arrow", YesNo.Yes);
		}

		return flag;

	}

	public boolean verifyPresenceOfActionButtonOfShowMoreActionDownArrow(String projectName, PageName pageName,
			ShowMoreActionDropDownList showMoreActionDropDownList, int timeOut) {
		int i = 1;
		String xpath = "";
		WebElement ele;
		boolean flag = false;
		String actionDropDown = showMoreActionDropDownList.toString().replace("_", " ");

		if (clickOnShowMoreDropdownOnly(projectName, pageName, actionDropDown)) {
			ThreadSleep(3000);
			log(LogStatus.INFO, "clicked on show more actions icon", YesNo.No);
			if (pageName.equals(PageName.TaskPage))
				xpath = "//div[@role='menu']//li/a[@title='" + actionDropDown + "']";
			else if (pageName.equals(PageName.Object1Page) || pageName.equals(PageName.SDGPage))
				xpath = "//*[@role='menu']//span[text()='" + actionDropDown + "']";
			else
				xpath = "//div[@role='menu']//span[text()='" + actionDropDown + "']";
			ThreadSleep(3000);
			ele = FindElement(driver, xpath, "show more action down arrow : " + actionDropDown, action.BOOLEAN, 10);
			// mouseOverOperation(driver, ele);
			if (ele != null) {
				log(LogStatus.INFO, "button: " + actionDropDown + " present in action dropdown", YesNo.No);
				flag = true;
				clickOnShowMoreDropdownOnly(projectName, pageName, actionDropDown);
			} else {
				// log(LogStatus.ERROR, "Not able to click on "+actionDropDown+" link",
				// YesNo.Yes);
				xpath = "//button[@name='" + actionDropDown + "']";
				ele = FindElement(driver, xpath, actionDropDown, action.BOOLEAN, 10);
				if (ele != null) {
					flag = true;
					clickOnShowMoreDropdownOnly(projectName, pageName, actionDropDown);
				} else {
					log(LogStatus.ERROR, "Not able to click on " + actionDropDown + " link", YesNo.Yes);
				}

			}
		} else {
			log(LogStatus.ERROR, "Not able to find on show more action down arrow", YesNo.Yes);
		}

		return flag;

	}

	/**
	 * @param projectName
	 * @param pageName
	 * @param labelFieldTextBox
	 * @param name
	 * @param action
	 * @param timeOut
	 * @return ContactNameWithInst Or Related Association Name On Task WebElement
	 */
	public WebElement getContactNameOrRelatedAssociationNameOnTask(String projectName, PageName pageName,
			String labelFieldTextBox, String name, action action, int timeOut) {

		WebElement ele = null;
		String xpath = "";
		labelFieldTextBox = labelFieldTextBox.replace("_", " ");
		if (PageName.TaskPage.toString().equals(pageName.toString())
				|| PageName.NewEventPopUp.toString().equals(pageName.toString())) {
			xpath = "//span[text()='" + labelFieldTextBox
					+ "']/..//following-sibling::div//input//following-sibling::div//ul//li/a//div[@title='" + name
					+ "']";
		} else {
			// Need to write ofr copy if same
			xpath = "//span[text()='" + labelFieldTextBox + "']/ancestor::div//*[@title='" + name + "']";
		}

		List<WebElement> list = FindElements(driver, xpath, "");
		for (WebElement element : list) {

			ele = isDisplayed(driver, element, "Visibility", timeOut, labelFieldTextBox);
			if (ele != null) {
				ele = element;
				break;
			}
		}

		return ele;
	}

	/**
	 * @param projectName
	 * @param pageName
	 * @param label
	 * @param tabName
	 * @param textValue
	 * @param action
	 * @param timOut
	 * @return true if able to select value by searching on Related
	 *         Association/Name/Assigned To/Name
	 */
	public boolean selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(String projectName,
			PageName pageName, String label, TabName tabName, String textValue, action action, int timOut) {
		boolean flag = false;
		WebElement ele;

		if (PageLabel.Related_Associations.toString().equals(label)
				|| ((PageName.NewEventPopUp.toString().equals(pageName.toString())
						|| PageName.GlobalActtion_TaskPOpUp.toString().equals(pageName.toString())
						|| PageName.TaskPage.toString().equals(pageName.toString()))
						&& PageLabel.Related_To.toString().equals(label))) {

			if (SelectRelatedAssociationsdropdownButton(projectName, pageName, label, tabName, action, timOut)) {
				log(LogStatus.INFO,
						"Able to Select Drown Down Value : " + getTabName(projectName, tabName) + " For Label " + label,
						YesNo.No);
				ThreadSleep(2000);
			} else {
				// sa.assertTrue(false,"Not Able to Select Drown Down Value :
				// "+getTabName(projectName, tabName)+" For Label "+label);
				log(LogStatus.SKIP, "Not Able to Select Drown Down Value : " + getTabName(projectName, tabName)
						+ " For Label " + label, YesNo.Yes);
				return flag;
			}
		}

		if (label.replaceAll("_", " ").equalsIgnoreCase(PageLabel.Name.toString())) {
			String xpath = "//span[text()='" + label.replaceAll("_", " ")
					+ "']/..//following-sibling::div//input[@title='Search Contacts']";
			doubleClickUsingAction(driver, FindElement(driver, xpath, "", action.BOOLEAN, 30));
			log(LogStatus.INFO, "click on name text box", YesNo.No);

		}
		ele = getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, pageName, label, action, timOut);
		ThreadSleep(2000);
		if (sendKeys(driver, ele, textValue, "Related To Text Label", action)) {
			log(LogStatus.INFO, "Enter Value to Related To Text Box : " + textValue, YesNo.No);
			ThreadSleep(2000);

			ele = getContactNameOrRelatedAssociationNameOnTask(projectName, pageName, label, textValue, action, timOut);
			ThreadSleep(2000);
			if (clickUsingJavaScript(driver, ele, "Selected " + textValue + " From Label : " + label, action)) {
				log(LogStatus.INFO, "Clicked on : " + textValue, YesNo.No);
				ThreadSleep(2000);
				flag = true;
			} else {
				// sa.assertTrue(false,"Not Able to Click on : "+textValue);
				log(LogStatus.SKIP, "Not Able to Click on : " + textValue, YesNo.Yes);
			}

		} else {
			// sa.assertTrue(false,"Not Able to Enter Value to Related To Text Box :
			// "+textValue);
			log(LogStatus.SKIP, "Not Able to Enter Value to Related To Text Box : " + textValue, YesNo.Yes);
		}

		return flag;
	}

	/**
	 * @param projectName
	 * @param pageName
	 * @param label
	 * @param value
	 * @param action
	 * @param timeOut
	 * @return true if able to select dropdown value on task pop up
	 */
	public boolean selectDropDownValueonTaskPopUp(String projectName, PageName pageName, String label, String value,
			action action, int timeOut) {
		boolean flag = false;
		WebElement ele = null;

		ele = getDropdownOnTaskPopUp(projectName, pageName, label, action, timeOut);

		if (ele != null) {
			log(LogStatus.INFO, "Drop Down Value Label Found : " + label, YesNo.No);
			if (clickUsingJavaScript(driver, ele, label, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on Drop Down Label : " + label, YesNo.No);
				flag = SelectDropDownValue(projectName, pageName, label, value, action, timeOut);
				if (flag) {
					log(LogStatus.ERROR, "Selected " + value + " on Drop Down Label : " + label, YesNo.Yes);
				} else {
					log(LogStatus.ERROR, "Not ABle to Select" + value + " on Drop Down Label : " + label, YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "Not ABle to Click on Drop Down Label : " + label, YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "Drop Down Value Label Not Found : " + label, YesNo.Yes);
		}
		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param pageName
	 * @param subjectText
	 * @param dropDownLabelWithValues
	 * @param action
	 * @param timeOut
	 * @return true if able to enter subject and drop down value on TaskPoUP
	 */
	public boolean enteringSubjectAndSelectDropDownValuesonTaskPopUp(String projectName, PageName pageName,
			String subjectText, String[][] dropDownLabelWithValues, action action, int timeOut) {

		WebElement ele = getTaskPopUpHeader(projectName, 10);
		String expecedHeader = "New Task";
		;
		if (ele != null) {
			log(LogStatus.INFO, "PopUp is open", YesNo.No);
			String actualHeader = ele.getText().trim();
			if (ele.getText().trim().equals(expecedHeader) || actualHeader.equalsIgnoreCase("New Event")) {
				log(LogStatus.INFO, "Header Text verified : " + expecedHeader, YesNo.Yes);

			} else {
				log(LogStatus.ERROR,
						"Header Text not verified Actual : " + actualHeader + " \t Expected : " + expecedHeader,
						YesNo.Yes);
				sa.assertTrue(false,
						"Header Text not verified Actual : " + actualHeader + " \t Expected : " + expecedHeader);
			}

		} else {
			log(LogStatus.ERROR, "No PopUp is open so cannot verify Heading " + expecedHeader, YesNo.Yes);
			sa.assertTrue(false, "No PopUp is open so cannot verify Heading " + expecedHeader);
		}

		String label;
		String value;
		boolean flag = false;
		WebElement ele2 = getLabelTextBox(projectName, PageName.TaskPage.toString(), PageLabel.Subject.toString(),
				timeOut);
		ele2.sendKeys(Keys.BACK_SPACE);
		ThreadSleep(1000);
		ele2.clear();
		sendKeys(driver, ele2, subjectText, "Subject", action);
		ele2.sendKeys(Keys.BACK_SPACE);
		ele2.sendKeys(Keys.BACK_SPACE);
		ele2.clear();
		ThreadSleep(3000);
		if (sendKeys(driver, ele2, subjectText, "Subject", action)) {
			log(LogStatus.INFO, "Entered value to Subject Text Box", YesNo.Yes);

			flag = true;

			if (dropDownLabelWithValues != null) {

				for (String[] labelWithValues : dropDownLabelWithValues) {
					label = labelWithValues[0];
					value = labelWithValues[1];
					ThreadSleep(1000);
					if (selectDropDownValueonTaskPopUp(projectName, pageName, label, value, action, timeOut)) {
						log(LogStatus.INFO, "Selected : " + value + " For Label : " + label, YesNo.No);
						ThreadSleep(1000);

					} else {
						log(LogStatus.ERROR, "Not Able to Select : " + value + " For Label : " + label, YesNo.Yes);
						BaseLib.sa.assertTrue(false, "Not Able to Select : " + value + " For Label : " + label);
					}

				}

			}

		}
		return flag;

	}

	/**
	 * @param projectName
	 * @param pageName
	 * @return true if able to click o Show more action Icon
	 */
	public boolean clickOnShowMoreDropdownOnly(String projectName, PageName pageName, String field) {
		String xpath = "";
		int i = 1;
		WebElement ele = null;
		boolean flag = true;
		if (pageName != PageName.SDGPage) {
			refresh(driver);
		}
		ThreadSleep(5000);
		xpath = "//div[contains(@class,'ViewMode-normal')]//*[contains(@class,'actionsContainer')]//span[text()='Show more actions']/ancestor::button";
		if (PageName.TestCustomObjectPage.equals(pageName) || PageName.Object3Page.equals(pageName)) {
			xpath = "(//span[contains(text(),'more actions')])[1]/..";
		} else if (PageName.SDGPage.equals(pageName)) {
			xpath = "(//span[contains(text(),'More options')])[1]/..";
		} else if (PageName.TaskPage.equals(pageName) && field.equalsIgnoreCase("New Task")) {
			xpath = "//a[@title='Show one more action']";

		} else if (PageName.TaskPage.equals(pageName)) {
			xpath = "//div[contains(@class,'ViewMode-normal')]//*[contains(@class,'actionsContainer')]//span[text()='Show more actions']/ancestor::a";
		}

		ele = FindElement(driver, xpath, "show more action down arrow", action.SCROLLANDBOOLEAN, 30);
		if (click(driver, ele, "show more action on " + pageName.toString(), action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "clicked on show more actions icon", YesNo.No);

		} else {
			log(LogStatus.FAIL, "cannot click on show more actions icon", YesNo.Yes);
			flag = false;
		}
		return flag;
	}

	public boolean clickOnShowMoreDropdownOnly(String environment, String mode, PageName pageName) {
		ThreadSleep(10000);
		String xpath = "";
		int i = 1;
		WebElement ele = null;
		boolean flag = true;
		if (mode.toString().equalsIgnoreCase(Mode.Lightning.toString())) {
			xpath = "//li//*[contains(@title,'more actions') or contains(text(),'more actions')]/..";
			if (pageName == PageName.ContactsPage)
				xpath = "//li//*[contains(@title,'more actions') or contains(text(),'more actions')]/..";

			List<WebElement> ele1 = FindElements(driver, xpath, "Show more action Icon");

			for (int j = 0; j < ele1.size(); j++) {
				log(LogStatus.INFO, "Size :  " + ele1.size() + "  >>>>>>>>  " + i, YesNo.No);
				ele = isDisplayed(driver, ele1.get(j), "visibility", 5, "Show more action Icon");
				if (clickUsingJavaScript(driver, ele, "show more action on " + pageName.toString(),
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on show more actions icon ", YesNo.No);
					return flag;
				} else {
					if (j == ele1.size() - 1) {
						log(LogStatus.FAIL, "cannot click on show more actions icon", YesNo.Yes);
						flag = false;
					}

				}
			}

		}
		return flag;

	}

	public WebElement getRelatedTab(String relatedTab, int timeOut) {
		String xpath = "";
		WebElement ele = null;
		String related = relatedTab.toString().replace("_", " ");
		xpath = "//li//*[@title='" + related + "' or text()='" + related + "']";
		ele = isDisplayed(driver, FindElement(driver, xpath, relatedTab.toString(), action.SCROLLANDBOOLEAN, timeOut),
				"visiblity", 30, relatedTab.toString());
		if (ele != null) {
			appLog.info("Element Found : " + related);
		} else {
			appLog.error("Element Not Found : " + related);
			appLog.error("Going to check on more " + related);
			xpath = "//li//button[@title='More Tabs']";
			ele = FindElement(driver, xpath, relatedTab.toString(), action.SCROLLANDBOOLEAN, timeOut);
			click(driver, ele, "More Tab", action.BOOLEAN);
			ThreadSleep(3000);

			xpath = "//a/span[text()='" + related + "']";
			ele = isDisplayed(driver,
					FindElement(driver, xpath, relatedTab.toString(), action.SCROLLANDBOOLEAN, timeOut), "visiblity",
					30, relatedTab.toString());

		}
		return ele;
	}

	/**
	 * @author Ankit Jaiswal
	 * @param environment
	 * @param relatedTab  TODO
	 * @param RecordType
	 * @return true/false
	 */
	public boolean ClickonRelatedTab_Lighting(String environment, RecordType recordType, String relatedTab) {
		String xpath1 = "//*[text()='Related']";
		String xpath2 = "//*[text()='Related']";
		String xpath = "";
		if (relatedTab != null) {
			return click(driver, getRelatedTab(relatedTab, 20), relatedTab, action.BOOLEAN);
		} else {
			if ((recordType == RecordType.Partnerships) || (recordType == RecordType.Fund)
					|| (recordType == RecordType.Fundraising) || (recordType == RecordType.Company)
					|| (recordType == RecordType.IndividualInvestor) || (recordType == RecordType.Institution)
					|| (recordType == RecordType.Contact))
				xpath = xpath1;
			else
				xpath = xpath2;
			for (int i = 0; i < 2; i++) {
				refresh(driver);
				ThreadSleep(3000);

				List<WebElement> eleList = FindElements(driver, xpath, "Related Tab");
				for (WebElement ele : eleList) {
					if (clickUsingJavaScript(driver, ele, recordType + " related tab", action.BOOLEAN)) {
						log(LogStatus.INFO, "clicked on " + recordType + " related tab", YesNo.No);
						return true;
					}
				}
			}
		}

		log(LogStatus.ERROR, "Not able to click on related tab " + recordType, YesNo.Yes);
		return false;
	}

	/**
	 * @param projectName
	 * @param pageName
	 * @param smaddl
	 * @param timeOut
	 * @return webelement for show more action item
	 */
	public WebElement actionDropdownElement(String projectName, PageName pageName, ShowMoreActionDropDownList smaddl,
			int timeOut) {
		String actionDropDown = smaddl.toString().replace("_", " ");
		String xpath = "//span[text()='" + actionDropDown + "']";

		if (PageName.TestCustomObjectPage.equals(pageName)) {
			// xpath="//a/span[text()='"+actionDropDown+"']";
			xpath = "//*[@name='" + actionDropDown + "' or text()='" + actionDropDown + "']";
		}

		return isDisplayed(driver,
				FindElement(driver, xpath, "show more action down arrow", action.SCROLLANDBOOLEAN, 10), "visibility",
				timeOut, actionDropDown);
	}

	public boolean clickOnRelatedList(String environment, String mode, RecordType RecordType, RelatedList relatedList,
			String relatedTab) {

		boolean flag = false;
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			if (RelatedList.Activity_History.equals(relatedList) || RelatedList.Open_Activities.equals(relatedList)) {
				return true;
			} else {
				if (ClickonRelatedTab_Lighting(environment, RecordType, relatedTab)) {
					ThreadSleep(4000);
					scrollThroughOutWindow(driver);
					ThreadSleep(4000);
					flag = true;
				}
			}

		} else {
			flag = true;
			/*
			 * if (clickOnRelatedList_Classic(environment, RelatedList)) { flag = true; }
			 */
		}

		return flag;

	}

	/**
	 * @param projectName
	 * @param pageName
	 * @param pageLabel
	 * @param labelValue
	 * @param timeOut
	 * @return true/false
	 * @description return true if particular field has been checked successfully
	 */
	public boolean fieldValueVerification(String projectName, PageName pageName, PageLabel pageLabel, String labelValue,
			int timeOut) {
		String xpath = "";
		WebElement ele;
		boolean flag = false;

		String label = pageLabel.toString().replace("_", " ");

		if (ProjectName.MNA.toString().equalsIgnoreCase(projectName) && PageLabel.Account_Name.equals(pageLabel)) {
			label = "Account Name";
		} else if (projectName.contains(ProjectName.PE.toString()) && PageLabel.Account_Name.equals(pageLabel)) {
			label = "Legal Name";
		} else if (projectName.contains(ProjectName.PEEdge.toString()) && PageLabel.Account_Name.equals(pageLabel)) {
			label = "Firm";
		}

		WebElement ele1 = getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
		click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
		ThreadSleep(2000);
		xpath = "//span[text()='" + label + "']/../following-sibling::div//*[text()='" + labelValue + "']";

		ele = FindElement(driver, xpath, label + " with Value " + labelValue, action.SCROLLANDBOOLEAN, 5);
		scrollDownThroughWebelement(driver, ele, label + " with Value " + labelValue);

		if (ele != null) {
			flag = true;
		} else {

		}

		return flag;

	}

	/**
	 * @param dateToCheck
	 * @param valueOnPage
	 * @return true/false
	 * @description this method is to verify 2 dates on the basis of values present
	 *              on page and passed
	 */
	public boolean verifyDate(String dateToCheck, String valueOnPage) {
		int size1 = valueOnPage.split("/").length;
		int size2 = 0;
		if (dateToCheck.contains(".")) {
			size2 = dateToCheck.split("[.]").length;
		} else {
			size2 = dateToCheck.split("/").length;

		}
		String[] values = null;
		if (!dateToCheck.isEmpty() && !dateToCheck.equals("") && size1 == 3 && size2 == 3) {
			String[] dates = valueOnPage.split("/");
			if (dateToCheck.contains(".")) {
				values = dateToCheck.split("[.]");
			} else {
				values = dateToCheck.split("/");

			}
			appLog.info("Excel Date : " + dateToCheck);
			appLog.info("Page Date : " + valueOnPage);
			if (dates[0].contains(values[0]) && dates[1].contains(values[1])
					&& (dates[2].contains(values[2]) || values[2].contains(dates[2]))) {
				log(LogStatus.INFO, "Value matched " + dateToCheck + " For Grid Data", YesNo.No);
				return true;
			} else {
				log(LogStatus.ERROR,
						"Value not matched Actual: " + valueOnPage + " Expected : " + dateToCheck + " For Grid Data : ",
						YesNo.No);
			}
		} else {
			log(LogStatus.ERROR, "passed date is in wrong format", YesNo.No);
		}

		return false;
	}

	public String getDaysDifferenceOfTwoDates(String startDate, String endDate, String format) {
		long days_difference = 0;
		long time_difference = 0;
		SimpleDateFormat obj = new SimpleDateFormat(format);
		try {
			Date date1 = obj.parse(startDate);
			Date date2 = obj.parse(endDate);
			time_difference = date2.getTime() - date1.getTime();
			days_difference = (time_difference / (1000 * 60 * 60 * 24)) % 365;

		} catch (ParseException excep) {
			excep.printStackTrace();

		}

		return String.valueOf(days_difference);
	}

	/**
	 * @param projectName
	 * @param tabObj
	 * @param timeOut
	 * @return true/false
	 * @description this method is used to add list view to page if automation all
	 *              is not present
	 */
	public boolean addAutomationAllListView(String projectName, String tabObj, int timeOut) {
		String viewList = "Automation All", xpath = "";
		if (click(driver, getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(3000);
			xpath = "//div[@class='listContent']//li/a/span[text()='" + viewList + "']";
			WebElement selectListView = FindElement(driver, xpath, "Select List View : " + viewList,
					action.SCROLLANDBOOLEAN, 5);
			ThreadSleep(3000);
			if (selectListView != null) {
				log(LogStatus.INFO, "automation all is already present", YesNo.No);
				return true;
			} else {
				log(LogStatus.ERROR, "not found automation all.. now creating", YesNo.No);

			}
		} else {
			log(LogStatus.ERROR, "list dropdown is not clickable, so cannot check presence of Automation All",
					YesNo.Yes);

		}

		if (createListView(projectName, tabObj, timeOut)) {
			if (changeFilterInListView(projectName, tabObj, timeOut)) {
				return true;
			} else {
				log(LogStatus.ERROR, "could not change filter to all", YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "could not create new list", YesNo.Yes);
		}
		return false;
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param obj
	 * @param timeOut
	 * @return true/false
	 * @description this method is used to only create new view names Automation All
	 */
	public boolean createListView(String projectName, String obj, int timeOut) {
		refresh(driver);
		ThreadSleep(2000);
		if (click(driver, getlistViewControlsButton(projectName, timeOut), "list view", action.BOOLEAN)) {
			log(LogStatus.INFO, "successfully click on list view", YesNo.No);
			if (click(driver, getnewButtonListView(projectName, timeOut), "new ", action.BOOLEAN)) {
				log(LogStatus.INFO, "successfully click on new buton", YesNo.No);
				if (sendKeys(driver, getlistNameTextBox(projectName, "List Name", timeOut), "Automation All",
						"list name", action.SCROLLANDBOOLEAN)) {
					if (sendKeysWithoutClearingTextBox(driver,
							getlistNameTextBox(projectName, "List API Name", timeOut), "", "list name",
							action.SCROLLANDBOOLEAN)) {
						if (click(driver, getallUsersRB(projectName, timeOut), "all users", action.BOOLEAN)) {
							log(LogStatus.INFO, "successfully click on all users", YesNo.No);
							if (click(driver, getlistViewSaveButton(projectName, timeOut), "save", action.BOOLEAN)) {
								log(LogStatus.INFO, "successfully click on save buton", YesNo.No);
								return true;
							} else {
								log(LogStatus.ERROR, "list view save button is not clickable", YesNo.No);
							}
						} else {
							log(LogStatus.ERROR, "all users radio button is not clickable", YesNo.No);
						}
					} else {
						log(LogStatus.ERROR, "list api textbox is not visible", YesNo.No);
					}
				} else {
					log(LogStatus.ERROR, "list name textbox is not visible", YesNo.No);
				}
			} else {
				log(LogStatus.ERROR, "new button is not clickable", YesNo.No);
			}
		} else {
			log(LogStatus.ERROR, "list view controls button is not clickable", YesNo.No);
		}
		return false;
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param tabObj
	 * @param timeOut
	 * @return true/false
	 * @description this method is used to change value in filter to all users
	 */
	public boolean changeFilterInListView(String projectName, String tabObj, int timeOut) {
		if (tabObj.equalsIgnoreCase("Entity")) {
			tabObj = "entities";
		}
		if (click(driver, getListFilterSection(projectName, tabObj, timeOut), "filter section", action.BOOLEAN)) {
			log(LogStatus.INFO, "successfully click on filter section", YesNo.No);
			if (click(driver, getallCheckboxForFilter(projectName, timeOut), "all filters", action.BOOLEAN)) {
				log(LogStatus.INFO, "successfully click on all radio button", YesNo.No);
				if (click(driver, getdoneButtonListView(projectName, timeOut), "done", action.BOOLEAN)) {
					log(LogStatus.INFO, "successfully click on done buton", YesNo.No);
					if (click(driver, getfilterSave(projectName, timeOut), "save", action.BOOLEAN)) {
						log(LogStatus.INFO, "successfully click on save buton", YesNo.No);
						WebElement ele = getCreatedConfirmationMsg(projectName, 15);
						if (ele != null) {
							String actualValue = ele.getText().trim();
							String expectedValue = BasePageErrorMessage.listViewUpdated;
							if (actualValue.contains(expectedValue)) {
								log(LogStatus.INFO, expectedValue + " matched FOR Confirmation Msg", YesNo.No);
								return true;
							} else {
								log(LogStatus.ERROR, "Actual : " + actualValue + " Expected : " + expectedValue
										+ " not matched FOR Confirmation Msg", YesNo.Yes);
								BaseLib.sa.assertTrue(false, "Actual : " + actualValue + " Expected : " + expectedValue
										+ " not matched FOR Confirmation Msg");
							}
						} else {
							sa.assertTrue(false, "Created Task Msg Ele not Found");
							log(LogStatus.SKIP, "Created Task Msg Ele not Found", YesNo.Yes);

						}
					} else {
						log(LogStatus.ERROR, "save button is not clickable", YesNo.No);
					}
				} else {
					log(LogStatus.ERROR, "done button is not clickable", YesNo.No);
				}
			} else {
				log(LogStatus.ERROR, "all checkbox is not clickable", YesNo.No);
			}
		} else {
			log(LogStatus.ERROR, "list filter section is not clickable", YesNo.No);
		}
		return false;
	}

	/**
	 * @param projectName
	 * @param pageName
	 * @param activityTimeLineItem
	 * @param timeOut
	 * @return activity timeline box
	 */
	public WebElement getActivityTimeLineBox(int timeOut) {

		String xpath = "";
		// WebElement ele;
		WebElement element = null;
		;
		xpath = "//div[@id='completeDiv' and @class='cActivityTimeline']";
		List<WebElement> eles = FindElements(driver, xpath, "");

		for (WebElement ele : eles) {
			ele = isDisplayed(driver, ele, "visiblity", 10, "activity timeline box");
			if (ele != null) {
				appLog.info("Element Found : activity timeline box");
				element = ele;
			}
		}
		return element;

	}

	/**
	 * @param projectName
	 * @param pageName
	 * @param activity
	 * @param timeOut
	 * @return activity box
	 */
	public WebElement getActivityBox(int timeOut) {

		String xpath = "";
		// WebElement ele;
		WebElement element = null;
		;
		xpath = "//*[@title='Activity' and @data-label='Activity']/../../..";
		List<WebElement> eles = FindElements(driver, xpath, "");

		for (WebElement ele : eles) {
			ele = isDisplayed(driver, ele, "visiblity", 10, "activity  box");
			if (ele != null) {
				appLog.info("Element Found : activity  box");
				element = ele;
			}
		}
		return element;

	}

	/**
	 * @param projectName
	 * @param pageName
	 * @param activity
	 * @param timeOut
	 * @return activity box
	 */
	public WebElement getActivityTimelineGridOnRelatedTab(int timeOut) {

		String xpath = "";
		// WebElement ele;
		WebElement element = null;
		;
		xpath = "//div[@class='cActivityTimeline' and @id='completeDiv']";
		List<WebElement> eles = FindElements(driver, xpath, "");

		for (WebElement ele : eles) {
			ele = isDisplayed(driver, ele, "visiblity", 10, "activity grid on related tab ");
			if (ele != null) {
				appLog.info("Element Found : activity grid on related tab ");
				element = ele;
			}
		}
		return element;

	}

	/**
	 * @param projectName
	 * @param pageName
	 * @param activityTimeLineItem
	 * @param timeOut
	 * @return getActivityTimeLineItem
	 */
	public WebElement getActivityTimeLineItem(String projectName, PageName pageName,
			ActivityTimeLineItem activityTimeLineItem, int timeOut) {
		// clickUsingJavaScript(driver, getactivityLineItemsDropdown(projectName, 10),
		// "dropdown", action.BOOLEAN);

		String xpath = "";
		// WebElement ele;
		String activity = activityTimeLineItem.toString().replace("_", " ");

		if (ActivityTimeLineItem.New_Meeting.equals(activityTimeLineItem)
				|| ActivityTimeLineItem.New_Task.equals(activityTimeLineItem)
				|| ActivityTimeLineItem.New_Call.equals(activityTimeLineItem)) {
			xpath = "//div[contains(@class,'ViewMode-normal')]//div[@class='slds-card-wrapper activityPanel']//*[ text()='"
					+ activity + "']";
		} else {
			if (projectName.equalsIgnoreCase(ProjectName.PE.toString()))
				xpath = "//div[@id='completeDiv' and @class='cActivityTimeline']/..//*[text()='" + activity + "']";
			else
				xpath = "//div[contains(@class,'ViewMode-normal')]//div[@class='slds-card-wrapper activityPanel']//*[ text()='"
						+ activity + "']";
		}

		List<WebElement> li = FindElements(driver, xpath, activityTimeLineItem.toString());
		int i = 0;
		for (WebElement ele : li) {
			ele = isDisplayed(driver, ele, "visiblity", 10, activityTimeLineItem.toString());
			if (ele != null) {
				appLog.info("Element Found : " + activity);
				return ele;
			}
			appLog.error("Element Not Found, attempt : " + (i + 1));
			i++;
		}
		appLog.error("Element Not Found : " + activity);
		return null;

	}

	/**
	 * @param projectName
	 * @param pageName
	 * @param activityTimeLineItem
	 * @param timeOut
	 * @return getActivityTimeLineItem2
	 */
	public WebElement getActivityTimeLineItem2(String projectName, PageName pageName,
			ActivityTimeLineItem activityTimeLineItem, int timeOut) {

		String xpath = "";
		WebElement ele;
		String activity = activityTimeLineItem.toString().replace("_", " ");
		if (projectName.contains(ProjectName.PE.toString()))
			xpath = "//div[@id='completeDiv']/..//*[@title='" + activity + "']";
		else
			xpath = "//div[@id='completeDiv']/..//*[@title='" + activity + "']";
		ele = isDisplayed(driver,
				FindElement(driver, xpath, activityTimeLineItem.toString(), action.SCROLLANDBOOLEAN, timeOut),
				"visiblity", 30, activityTimeLineItem.toString());
		if (ele != null) {
			appLog.info("Element Found : " + activity);
		} else {
			appLog.error("Element Not Found : " + activity);
		}
		return ele;

	}

	/**
	 * @param projectName
	 * @param pageName
	 * @param activityType
	 * @param subject
	 * @param subjectElement
	 * @param timeOut
	 * @return getElementForActivityTimeLineTask
	 */
	public WebElement getElementForActivityTimeLineTask(String projectName, PageName pageName,
			ActivityType activityType, String subject, SubjectElement subjectElement, int timeOut) {

		WebElement ele;
		String type = "";
		if (activityType == ActivityType.Next) {
			type = "Next";
		} else if (activityType == ActivityType.Past) {
			type = "past";
		}

		String nextStepsXpath = "//div[contains(@class,'" + type + "')]";

		String subjectXpath = nextStepsXpath + "/following-sibling::div//*[@title='" + subject + "']";

		String eleXpath = "";

		if (subjectElement == SubjectElement.CheckBox) {
			eleXpath = subjectXpath + "/preceding-sibling::span//input";
		} else if (subjectElement == SubjectElement.RedFlag) {
			eleXpath = subjectXpath + "/following-sibling::div//*[@title='High-Priority Task']";
		} else if (subjectElement == SubjectElement.ExpandIcon) {
			eleXpath = subjectXpath + "/../../../..//div[contains(@id,'expandIcon')]";
		} else if (subjectElement == SubjectElement.CollapseIcon) {
			eleXpath = subjectXpath + "/../../../..//div[contains(@id,'collapseIcon')]";
		} else if (subjectElement == SubjectElement.Attachment) {
			eleXpath = subjectXpath + "/following-sibling::div//*[@title='attachment']";
		} else if (subjectElement == SubjectElement.StrikedText) {
			eleXpath = subjectXpath;
		} else if (subjectElement == SubjectElement.NextGrid) {
			eleXpath = nextStepsXpath;
		} else if (subjectElement == SubjectElement.PastGrid) {
			eleXpath = nextStepsXpath;
		} else {
			eleXpath = subjectXpath;
		}
		ele = FindElement(driver, eleXpath, subjectElement + " For : " + subject, action.SCROLLANDBOOLEAN, timeOut);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, subjectElement + " For : " + subject);
		// Due DATE

		if (ele != null) {
			log(LogStatus.INFO, subjectElement + " Found For : " + subject, YesNo.No);

		} else {
			log(LogStatus.SKIP, subjectElement + " Not Found For : " + subject, YesNo.Yes);
		}

		return ele;
	}

	/**
	 * @author Azhar Alam
	 * @param passedDate
	 * @param zone
	 * @return date according to time zone
	 */
	public static String getDateValueAccording(String passedDate, String zone) {

		appLog.info("Passed date : " + passedDate);
		appLog.info("Today date : " + previousOrForwardDateAccordingToTimeZone(0, "MM/dd/YYYY", zone));
		appLog.info("Yesterday date : " + previousOrForwardDateAccordingToTimeZone(-1, "MM/dd/YYYY", zone));
		appLog.info("Tomorrow date : " + previousOrForwardDateAccordingToTimeZone(1, "MM/dd/YYYY", zone));
		String value = "";

		if (passedDate.equalsIgnoreCase(previousOrForwardDateAccordingToTimeZone(0, "MM/dd/YYYY", zone))
				|| passedDate.equalsIgnoreCase(previousOrForwardDateAccordingToTimeZone(0, "M/d/YYYY", zone))) {
			value = "Today";
		} else if (passedDate.equalsIgnoreCase(previousOrForwardDateAccordingToTimeZone(-1, "MM/dd/YYYY", zone))
				|| passedDate.equalsIgnoreCase(previousOrForwardDateAccordingToTimeZone(-1, "M/d/YYYY", zone))) {
			value = "Yesterday";
		} else if (passedDate.equalsIgnoreCase(previousOrForwardDateAccordingToTimeZone(1, "MM/dd/YYYY", zone))
				|| passedDate.equalsIgnoreCase(previousOrForwardDateAccordingToTimeZone(1, "M/d/YYYY", zone))) {
			value = "Tomorrow";
		} else if (passedDate.equalsIgnoreCase(DueDate.No_due_date.toString())) {
			value = passedDate;
		} else {
			value = findThreeLetterMonthName(passedDate);
		}

		return value;

	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param pageName
	 * @param createdItemValue
	 * @param subject
	 * @param assignedToMsg
	 * @param dueDate
	 * @param isMeetingType
	 * @param meetingTypeValue
	 * @param isDescription
	 * @param descriptionValue
	 * @param timeOut
	 */
	public void verifyActivityAtNextStep2(String projectName, PageName pageName, String createdItemValue,
			String subject, String assignedToMsg, String dueDate, boolean isMeetingType, String meetingTypeValue,
			boolean isDescription, String descriptionValue, int timeOut) {

		WebElement ele;
		dueDate = getDateValueAccording(dueDate, AmericaLosAngelesTimeZone);
		String actualValue = "";

		String nextStepsXpath = "//div[@class='standardTimelineUpcomingActivities']";
		String subjectXpath = nextStepsXpath + "//a[@title='" + subject + "']";

		String dateXpath = subjectXpath + "/ancestor::li//*[contains(text(),'" + dueDate + "')]";
		ele = FindElement(driver, dateXpath, dueDate.toString(), action.SCROLLANDBOOLEAN, 20);

		// Due DATE

		if (ele != null) {
			log(LogStatus.INFO, dueDate + " verified for subject : " + subject + " For item : " + createdItemValue,
					YesNo.No);

			// Assigned To
			String assignedToxpath = subjectXpath + "/ancestor::li//div[contains(@class,'summary')]";
			ele = FindElement(driver, assignedToxpath, "Asigned To ", action.SCROLLANDBOOLEAN, 5);
			if (ele != null) {
				log(LogStatus.INFO, "Asigned To verified for subject : " + subject + " For item : " + createdItemValue,
						YesNo.No);
				if (assignedToMsg != null) {
					System.err.println(">>>>>>>>>>>>   " + ele.getText().trim());
					actualValue = ele.getText().trim().replace("\n", " ");
					System.err.println(">>>>>>>>>>>>   " + actualValue.replace("\n", " "));
					if (assignedToMsg.equals(actualValue)) {
						log(LogStatus.INFO, assignedToMsg + " Verified for subject : " + subject + " For item : "
								+ createdItemValue, YesNo.No);

					} else {
						sa.assertTrue(false, assignedToMsg + " not Verified for subject : " + subject + " For item : "
								+ createdItemValue + "\nActual  :  " + actualValue + "\nExpected : " + assignedToMsg);
						log(LogStatus.SKIP, assignedToMsg + " not Verified for subject : " + subject + " For item : "
								+ createdItemValue + "\nActual  :  " + actualValue + "\nExpected : " + assignedToMsg,
								YesNo.Yes);
					}
				}

			} else {
				sa.assertTrue(false,
						"Asigned To not verified for subject : " + subject + " For item : " + createdItemValue);
				log(LogStatus.SKIP,
						"Asigned To not verified for subject : " + subject + " For item : " + createdItemValue,
						YesNo.Yes);
			}

			// Meeting Type
			if (isMeetingType) {

				String meetingTypeXpath = subjectXpath
						+ "/../..//following-sibling::div//article//ul//li/span[text()='Meeting Type']";
				ele = FindElement(driver, meetingTypeXpath, "Meeting Type", action.SCROLLANDBOOLEAN, 5);
				if (ele != null) {
					log(LogStatus.INFO, "Meeting Type verified for subject : " + subject, YesNo.No);

					String meetingTypeValueXpath = meetingTypeXpath + "/following-sibling::span";
					ele = FindElement(driver, meetingTypeValueXpath, meetingTypeValue, action.SCROLLANDBOOLEAN, 5);
					if (ele != null) {
						log(LogStatus.INFO, meetingTypeValue + " Element is Present for subject : " + subject
								+ " For item : " + createdItemValue, YesNo.No);
						actualValue = ele.getText().trim();
						if (meetingTypeValue.equals(actualValue)) {
							log(LogStatus.INFO, meetingTypeValue + " Verified for subject : " + subject + " For item : "
									+ createdItemValue, YesNo.No);

						} else {
							sa.assertTrue(false,
									meetingTypeValue + " not Verified for subject : " + subject + " For item : "
											+ createdItemValue + " Actual : " + actualValue + " \t Expected : "
											+ meetingTypeValue);
							log(LogStatus.SKIP,
									meetingTypeValue + " not Verified for subject : " + subject + " For item : "
											+ createdItemValue + " Actual : " + actualValue + " \t Expected : "
											+ meetingTypeValue,
									YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, meetingTypeValue + " Element is not Present for subject : " + subject
								+ " For item : " + createdItemValue);
						log(LogStatus.SKIP, meetingTypeValue + " Element is not Present for subject : " + subject
								+ " For item : " + createdItemValue, YesNo.Yes);
					}

				} else {
					sa.assertTrue(false,
							"Meeting Type not verified for subject : " + subject + " For item : " + createdItemValue);
					log(LogStatus.SKIP,
							"Meeting Type not verified for subject : " + subject + " For item : " + createdItemValue,
							YesNo.Yes);
				}
			}

			// Description

			if (isDescription) {

				String descriptionXpath = subjectXpath
						+ "/../..//following-sibling::div//article//div[1][text()='Description']";
				ele = FindElement(driver, descriptionXpath, "Description", action.SCROLLANDBOOLEAN, 5);
				if (ele != null) {
					log(LogStatus.INFO,
							"Description Text verified for subject : " + subject + " For item : " + createdItemValue,
							YesNo.No);

					String descriptionValueXpath = descriptionXpath + "/following-sibling::div";
					ele = FindElement(driver, descriptionValueXpath, descriptionValue, action.SCROLLANDBOOLEAN, 5);
					if (ele != null) {
						log(LogStatus.INFO, descriptionValue + " Element is Present for subject : " + subject
								+ " For item : " + createdItemValue, YesNo.No);
						actualValue = ele.getText().trim();
						if (descriptionValue.equals(actualValue)) {
							log(LogStatus.INFO, descriptionValue + " Verified for subject : " + subject + " For item : "
									+ createdItemValue, YesNo.No);

						} else {
							sa.assertTrue(false,
									descriptionValue + " not Verified for subject : " + subject + " For item : "
											+ createdItemValue + " Actual : " + actualValue + " \t Expected : "
											+ descriptionValue);
							log(LogStatus.SKIP,
									descriptionValue + " not Verified for subject : " + subject + " For item : "
											+ createdItemValue + " Actual : " + actualValue + " \t Expected : "
											+ descriptionValue,
									YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, descriptionValue + " Element is not Present for subject : " + subject
								+ " For item : " + createdItemValue);
						log(LogStatus.SKIP, descriptionValue + " Element is not Present for subject : " + subject
								+ " For item : " + createdItemValue, YesNo.Yes);
					}

				} else {
					sa.assertTrue(false, "Description Text not verified for subject : " + subject + " For item : "
							+ createdItemValue);
					log(LogStatus.SKIP, "Description Text not verified for subject : " + subject + " For item : "
							+ createdItemValue, YesNo.Yes);
				}

			}

		} else {
			sa.assertTrue(false,
					dueDate + " not verified for subject : " + subject + " For item : " + createdItemValue);
			log(LogStatus.SKIP, dueDate + " not verified for subject : " + subject + " For item : " + createdItemValue,
					YesNo.Yes);
		}

	}

	/**
	 * @param projectName
	 * @param TabName
	 * @return true if able to click on Tab
	 */
	public boolean clickOnTab(String projectName, String TabName) {

		String tabName = null;
		boolean flag = false;
		WebElement ele;
		if (TabName.contains("Entit")) {
			tabName = "Entities";
		} else if (TabName.contains("Inst")) {
			tabName = "Institutions";
		} else if (TabName.contains("Fundraising")) {
			tabName = "Fundraisings";
		} else if (TabName.contains("Fund")) {
			tabName = "Funds";
		} else if (TabName.contains("Firm"))

		{
			tabName = "Firms";
		} else if (TabName.contains("Deal"))

		{
			tabName = "Deals";
		} else {
			tabName = TabName;
		}
		System.err.println("Passed switch statement");
		if (tabName != null) {
			ele = FindElement(driver, "//a[contains(@href,'lightning') and contains(@title,'" + tabName + "')]/span/..",
					tabName, action.SCROLLANDBOOLEAN, 30);
			ele = isDisplayed(driver, ele, "visibility", 30, tabName);
			if (ele != null) {
				appLog.info("Tab Found");
				ThreadSleep(5000);
				if (clickUsingJavaScript(driver, ele, tabName + " :Tab")) {
					CommonLib.log(LogStatus.INFO, "Tab found", YesNo.No);
					appLog.info("Clicked on Tab : " + tabName);

					flag = true;
				} else {
					appLog.error("Not Able to Click on Tab : " + tabName);
				}

			} else {
				CommonLib.log(LogStatus.INFO, "Going to found tab after clicking on More Icon", YesNo.No);
				if (click(driver, getMoreTabIcon(projectName, 10), "More Icon", action.SCROLLANDBOOLEAN)) {
					ele = FindElement(driver,
							"//a[contains(@href,'lightning')]/span[@class='slds-truncate']/span[contains(text(),'"
									+ tabName + "')]",
							tabName, action.SCROLLANDBOOLEAN, 10);
					ele = isDisplayed(driver, ele, "visibility", 10, tabName);
					if (ele != null) {
						if (clickUsingJavaScript(driver, ele, tabName + " :Tab")) {
							appLog.info("Clicked on Tab on More Icon: " + tabName);
							CommonLib.log(LogStatus.INFO, "Tab found on More Icon", YesNo.No);
							flag = true;
						}
					}

				} else {
					appLog.error("Not Able to Clicked on Tab on More Icon: " + tabName);
				}

			}
		}

		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param alreadyCreated
	 * @param isClick        TODO
	 * @param timeout
	 * @param tabName
	 * @return true if able to click on particular item on Particular tab
	 */
	public boolean clickOnAlreadyCreatedItem(String projectName, String alreadyCreated, boolean isClick, int timeout) {
		boolean flag = false;
		String xpath = "";
		String viewList = null;
		viewList = "All";
		WebElement ele, selectListView;
		ele = null;
		ThreadSleep(3000);
		refresh(driver);
		if (click(driver, getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(3000);
			xpath = "//div[@class='listContent']//li/a/span[text()='" + viewList + "']";
			selectListView = FindElement(driver, xpath, "Select List View : " + viewList, action.SCROLLANDBOOLEAN, 30);
			if (click(driver, selectListView, "select List View : " + viewList, action.SCROLLANDBOOLEAN)) {
				ThreadSleep(3000);
				ThreadSleep(5000);

				if (sendKeys(driver, getSearchIcon_Lighting(20), alreadyCreated + "\n", "Search Icon Text",
						action.SCROLLANDBOOLEAN)) {
					ThreadSleep(5000);

					xpath = "//table[@data-aura-class='uiVirtualDataTable']//tbody//tr//th//span//*[text()='"
							+ alreadyCreated + "']";
					ele = FindElement(driver, xpath, alreadyCreated, action.BOOLEAN, 30);
					ThreadSleep(2000);
					if (isClick) {
						if (click(driver, ele, alreadyCreated, action.BOOLEAN)) {
							ThreadSleep(3000);
							click(driver, getPagePopUp(projectName, 5), "Page PopUp", action.BOOLEAN);
							flag = true;
						} else {

						}
					} else {
						if (ele != null) {
							appLog.info("Item Found : " + alreadyCreated);
							flag = true;
						} else {
							appLog.error("Item not Found : " + alreadyCreated);
						}
					}

				} else {
					appLog.error("Not able to enter value on Search Box");
				}
			} else {
				appLog.error("Not able to select on Select View List : " + viewList);
			}
		} else {
			appLog.error("Not able to click on Select List Icon");
		}
		return flag;
	}

	/**
	 * @param projectName
	 * @param pageName
	 * @param relatedTab
	 * @param timeOut
	 * @return Related Tab WebElement
	 */
	public WebElement getRelatedTab(String projectName, String relatedTab, int timeOut) {
		ThreadSleep(10000);
		String xpath = "";
		WebElement ele;
		String related = relatedTab.toString().replace("_", " ");
		if (projectName.contains(ProjectName.PE.toString()))
			xpath = "//ul[@role='tablist']//a[text()='" + related + "']";
		else
			xpath = "//li//*[@title='" + related + "' or text()='" + related + "']";

		ele = isDisplayed(driver, FindElement(driver, xpath, relatedTab.toString(), action.SCROLLANDBOOLEAN, timeOut),
				"visiblity", 30, relatedTab.toString());
		if (ele != null) {
			appLog.info("Element Found : " + related);
		} else {
			appLog.error("Element Not Found : " + related);
			appLog.error("Going to check on more " + related);
			// xpath = "//li//button[@title='More Tabs']";
			xpath = "//li//*[contains(text(),'More')]";
			List<WebElement> eleList = FindElements(driver, xpath, "More");
			if (!eleList.isEmpty() && eleList.size() >= 2) {
				ele = eleList.get(1);
			} else {
				ele = FindElement(driver, xpath, relatedTab.toString(), action.SCROLLANDBOOLEAN, timeOut);
			}
			click(driver, ele, "More Tab", action.BOOLEAN);
			ThreadSleep(3000);

			// xpath = "//a/span[text()='" + related + "']";
			xpath = "//a[text()='" + related + "']";
			ele = isDisplayed(driver,
					FindElement(driver, xpath, relatedTab.toString(), action.SCROLLANDBOOLEAN, timeOut), "visiblity",
					30, relatedTab.toString());

		}
		return ele;

	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param toggleTab
	 * @param btnName
	 * @param action
	 * @param isInside    TODO
	 * @param timeOut
	 * @return toggle SDG Button webElement
	 */
	public WebElement toggleSDGButtons(String projectName, String toggleTab, ToggleButtonGroup btnName, action action,
			boolean isInside, int timeOut) {
		String btname = btnName.toString();
		String xpath = "";
		if (isInside) {

			xpath = "//button[@title='" + toggleTab + "']";
		} else {
			xpath = "//button[@title='" + toggleTab + "']";

		}
		WebElement ele = FindElement(driver, xpath, toggleTab + " >> " + btname, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "Toggle Button : " + btname);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Toggle Button : " + btname);
		return ele;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param btnName
	 * @param action
	 * @param timeOut
	 * @return toggle Button webElement
	 */
	public WebElement toggleButton(String projectName, String btnName, action action, int timeOut) {
		String xpath = "//button[contains(@title,'" + btnName + "')]";
		WebElement ele = FindElement(driver, xpath, "Toggle Button : " + btnName, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "Toggle Button : " + btnName);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Toggle Button : " + btnName);
		return ele;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param btnName
	 * @param columnName
	 * @param action
	 * @param timeOut
	 * @return toggle Button Column Names webElement
	 */
	public WebElement toggleButtonColumnNames(String projectName, String btnName, String columnName, action action,
			int timeOut) {
		String xpath = "//a[text()='" + btnName + "']//ancestor::article//th//div/span[contains(text(),'" + columnName
				+ "')]";
		WebElement ele = FindElement(driver, xpath, "Toggle Button : " + btnName + " >> column Name : " + columnName,
				action, timeOut);
		scrollDownThroughWebelement(driver, ele, "Toggle Button : " + btnName + " >> column Name : " + columnName);
		ele = isDisplayed(driver, ele, "Visibility", timeOut,
				"Toggle Button : " + btnName + " >> column Name : " + columnName);
		return ele;
	}

	/**
	 * @author Akul Bhutani
	 * @param number
	 * @param format
	 * @return string with specific format with currency symbol
	 */
	public static String convertNumberAccordingToFormatWithCurrencySymbol(String number, String format) {

		double d = Double.parseDouble(number);
		DecimalFormat myFormatter = new DecimalFormat(format);
		String output = new DecimalFormatSymbols(Locale.US).getCurrencySymbol() + myFormatter.format(d);
		System.err.println(" outpurttt >>>> " + output);
		return output;

	}

	/**
	 * @author Azhar Alam
	 * @param header
	 * @param itemName
	 * @return webElement for created item on Page
	 */
	public WebElement verifyCreatedItemOnPage(Header header, String itemName) {
		WebElement ele;
		String xpath = "";
		String head = header.toString().replace("_", " ");
		ThreadSleep(3000);
		xpath = "//div[contains(@class,'outputName') or contains(@class,'header')]//*[text()='" + itemName + "']";
		ele = FindElement(driver, xpath, "Header : " + itemName, action.BOOLEAN, 30);
		ele = isDisplayed(driver, ele, "Visibility", 10, head + " : " + itemName);
		return ele;
	}

	public WebElement verifydefaultCreatedItemOnPageAcuty(Header header, String TabName) {
		WebElement ele;
		String xpath = "";
		String head = header.toString().replace("_", " ");
		ThreadSleep(3000);
		xpath = "//*[contains(@class,'slds-is-active')  and contains(@title,'" + TabName + "')]";
		ele = FindElement(driver, xpath, "Header : " + TabName, action.BOOLEAN, 30);
		ele = isDisplayed(driver, ele, "Visibility", 10, head + " : " + TabName);
		return ele;
	}

	/**
	 * @author Akul Bhutani
	 * @param number
	 * @return string after converting a number in to million format
	 */
	public static String convertNumberIntoMillions(String number) {
		double d = Double.parseDouble(number);
		double aa = d / 1000000;
		String output = new DecimalFormatSymbols(Locale.US).getCurrencySymbol() + aa;
		System.err.println("convertNumberIntoMillions  outpurttt >>>> " + output);
		return output;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param btnName
	 * @param action
	 * @param timeOut
	 * @return custom Toggle Button webElement
	 */
	public WebElement customToggleButton(String projectName, String btnName, action action, int timeOut) {
		String xpath = "//*[text()='" + btnName + "']";
		WebElement ele = FindElement(driver, xpath, "Toggle Button : " + btnName, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "Toggle Button : " + btnName);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Toggle Button : " + btnName);
		return ele;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param labelName
	 * @param action
	 * @param timeOut
	 * @return common Input Element
	 */
	public WebElement commonInputElement(String projectName, String labelName, action action, int timeOut) {
		labelName = labelName.replace("_", " ");
		String xpath = "//section[contains(@class,'active')]//label[text()='" + labelName
				+ "']//following-sibling::div//input";
		WebElement ele = FindElement(driver, xpath, labelName + " TextBox", action, timeOut);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, labelName + " TextBox");
		return ele;
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param labelName
	 * @param action
	 * @param timeOut
	 * @return menu tab webElement
	 */
	public WebElement getMenuTab(String projectName, String labelName, action action, int timeOut) {
		String xpath = "//div[@class='flexipageComponent']//span[text()='" + labelName + "']";
		WebElement ele = FindElement(driver, xpath, labelName + " TextBox", action, timeOut);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, labelName);
		return ele;
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param timeOut
	 * @return true if automation All is present in View List
	 */
	public boolean isAutomationAllListViewAdded(String projectName, int timeOut) {
		String viewList = "Automation All", xpath = "";
		if (click(driver, getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(3000);
			xpath = "//span[text()='" + viewList + "']";
			WebElement selectListView = FindElement(driver, xpath, "Select List View : " + viewList,
					action.SCROLLANDBOOLEAN, 5);
			ThreadSleep(3000);
			if (selectListView != null) {
				log(LogStatus.INFO, "automation all is already present", YesNo.No);
				return true;
			} else {
				log(LogStatus.ERROR, "not found automation all.. now creating", YesNo.No);

			}
		} else {
			log(LogStatus.ERROR, "list dropdown is not clickable, so cannot check presence of Automation All",
					YesNo.Yes);

		}

		return false;
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param recordName
	 * @param fieldValues
	 * @param timeOut
	 * @return true if verify Accordion
	 */
	public boolean verifyAccordion(String projectName, String recordName, String[] fieldValues, int timeOut) {
		String field = "";
		String value = "";
		boolean flag = true;
		String finalx = "", xpath = "//article[contains(@class,'RelatedListAccordion')]";
		WebElement ele = FindElement(driver, xpath, recordName, action.SCROLLANDBOOLEAN, 10);
		if (isDisplayed(driver, ele, "visibility", timeOut, recordName + " in accordion") != null) {
			xpath = "//article[contains(@class,'RelatedListAccordion')]";
			if (fieldValues != null) {
				for (String fieldValue : fieldValues) {
					field = fieldValue.split(breakSP)[0];
					value = fieldValue.split(breakSP)[1];
					field = field.replace("_", " ");

					if (field.equalsIgnoreCase(PageLabel.Name.toString())) {

						finalx = xpath + "//a[text()='" + value + "']";

					} else {
						finalx = xpath + "//li//div[@title='" + field + "']/following-sibling::div[@title='" + value
								+ "']";

					}
					ele = FindElement(driver, finalx, field + " and " + value, action.SCROLLANDBOOLEAN, 10);
					ele = isDisplayed(driver, ele, "visibility", 10, field + " and " + value);
					if (ele != null) {
						log(LogStatus.INFO, "successfully verified presence of " + field + " and " + value, YesNo.No);

					} else {
						log(LogStatus.ERROR, "could not verify " + field + " and " + value, YesNo.Yes);
						flag = false;
					}
				}
			}
		} else {
			log(LogStatus.ERROR, "could not verify presence of " + recordName + " in accordion ", YesNo.Yes);
			flag = false;
		}
		return flag;
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param record
	 * @param imgId
	 * @return true if verify Accordian Record Image
	 */
	public boolean verifyAccordianRecordImage(String projectName, String record, String imgId) {
		boolean flag = true;
		String xpath = "//article[contains(@class,'RelatedListAccordion')]//a[text()='" + record + "']";
		String finalx = xpath + "/../preceding-sibling::div//img";
		WebElement ele = FindElement(driver, xpath, "accordion record", action.SCROLLANDBOOLEAN, 10);
		ele = isDisplayed(driver, ele, "visibility", 10, "accordion record profile image");
		if (ele != null) {
			ele = FindElement(driver, finalx, "img in contact accordion", action.BOOLEAN, 10);
			ele = isDisplayed(driver, ele, "visibility", 10, "accordion record profile image");
			String id = ele.getAttribute("src");
			if (id.contains(imgId)) {
				log(LogStatus.INFO, "successfully verified img id\n" + id + "\nand\n" + imgId, YesNo.No);
			} else {
				log(LogStatus.ERROR, "could not verify id. found:\n" + id + "\nexpected:\n" + imgId, YesNo.Yes);
				flag = false;
			}
		} else {
			log(LogStatus.ERROR, "could not find accordion", YesNo.Yes);
			flag = false;
		}
		return flag;
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param pageName
	 * @param uploadImagePath
	 * @param errorMsgCheck
	 * @return true if photo update successfully
	 */
	public boolean updatePhoto(String projectName, String pageName, String uploadImagePath, boolean errorMsgCheck) {
		String imgId = null;
		boolean flag = false;
		WebElement ele = getUpdatePhotoCameraIcon(10);
		if (ele != null) {
			ThreadSleep(500);
			if (click(driver, ele, "update photo camera icon", action.BOOLEAN)) {
				log(LogStatus.INFO, "clicked on update photo icon", YesNo.No);
				if (click(driver, updateAndDeletePhotoXpath(IconType.updatePhoto, 10), "update photo button",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "clicked on update photo button", YesNo.No);
					ThreadSleep(1000);
					String path = System.getProperty("user.dir") + uploadImagePath;
					System.err.println("Path : " + path);
					if (sendKeys(driver, getUploadImageXpath(10), path, "upload photo button",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "clicked on upload image button on " + pageName, YesNo.No);
						ThreadSleep(500);
						ThreadSleep(1000);
						if (!errorMsgCheck) {
							if (click(driver, getRecordPageSettingSave(10), "Save button", action.BOOLEAN)) {
								log(LogStatus.PASS,
										"clicked on save button and image is updtaed " + path + " on " + pageName,
										YesNo.No);
								ThreadSleep(4000);
								imgId = getimgLink(projectName, 10).getAttribute("src");
								if (imgId != null) {
									log(LogStatus.INFO, "found id of img uploaded: " + imgId, YesNo.Yes);
									flag = true;
								} else {
									log(LogStatus.ERROR, "could not find id of img uploaded", YesNo.Yes);
								}
							} else {
								log(LogStatus.PASS,
										"Not able to click on save button and so cannot updtaed image from path " + path
												+ " on " + pageName,
										YesNo.No);
							}
						} else {
							if (getInvalidImageErrorMsg(10) != null) {
								String ss = getInvalidImageErrorMsg(10).getText().trim();
								if (ss.equalsIgnoreCase(BasePageErrorMessage.invalidImageErrorMsg)) {
									log(LogStatus.PASS, "Error Message is verified for " + uploadImagePath, YesNo.No);
									flag = true;
								} else {
									log(LogStatus.ERROR, "Error Message is not verified : " + uploadImagePath,
											YesNo.Yes);
								}
							} else {
								log(LogStatus.ERROR, "Not able to find the error meesage after upload invalid image : "
										+ uploadImagePath, YesNo.Yes);
							}
							if (click(driver, getCancelBtn(10), "cancel button", action.BOOLEAN)) {
								log(LogStatus.PASS, "Clicked on upload image popoup cancel button", YesNo.No);
							} else {
								log(LogStatus.ERROR,
										"Not able to click on upload image cancel button so cannot close popup",
										YesNo.Yes);
							}
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on upload image on " + pageName
								+ " so cannot update image from Path : " + path, YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR,
							"Not able to click on update photo button so cannot update photo on " + pageName,
							YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on update photo icon so cannot upload photo on " + pageName,
						YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "camera icon is not displaying on " + pageName + " so cannot upload photo", YesNo.Yes);
		}
		return flag;
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param object
	 * @return return Accordion Link webElement
	 */
	public WebElement returnAccordionLink(String projectName, String object) {
		String xpath = "//article[contains(@class,'RelatedListAccordion')]//a[text()='" + object + "']";
		WebElement ele = FindElement(driver, xpath, object + "accordion", action.SCROLLANDBOOLEAN, 10);
		return isDisplayed(driver, ele, "visibility", 10, object + "accordion");

	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param object
	 * @return return Accordion View Details Link webElement
	 */
	public WebElement returnAccordionViewDetailsLink(String projectName, String object) {
		String xpath = "//article[contains(@class,'RelatedListAccordion')]//a[text()='" + object
				+ "']/../../../following-sibling::footer//a[contains(text(),'View')][contains(text(),'Details')]";
		WebElement ele = FindElement(driver, xpath, object + "accordion", action.SCROLLANDBOOLEAN, 10);
		return isDisplayed(driver, ele, "visibility", 10, object + "accordion");

	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param ec
	 * @param timeOut
	 * @return accordion Expand Collapse webElement
	 */
	public WebElement accordionExpandCollapse(String projectName, ExpandCollapse ec, int timeOut) {
		String xpath = "//div[contains(@id,'modal')]//*[@title='" + ec.toString() + "']";
		WebElement ele = FindElement(driver, xpath, ec + " accordion", action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "visibility", timeOut, ec + " accordion");

	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param object
	 * @return accordion Modal Window Close webElement
	 */
	public WebElement accordionModalWindowClose(String projectName, String object) {
		String xpath = "//h2[text()='" + object + "']/preceding-sibling::button[@title='close']";
		WebElement ele = FindElement(driver, xpath, object + "accordion", action.SCROLLANDBOOLEAN, 10);
		return isDisplayed(driver, ele, "visibility", 10, object + "accordion");

	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param ObjectName
	 * @param timeOut
	 * @return true if Automation All view list is added
	 */
	public boolean isAutomationAllListViewForObject(String projectName, String ObjectName, int timeOut) {
		String viewList = "Automation All", xpath = "";
		ThreadSleep(3000);
		xpath = "//span[text()='" + ObjectName + "']/../../../following-sibling::div//span[text()='" + viewList + "']";
		WebElement selectListView = FindElement(driver, xpath, "Select List View : " + viewList + " for " + ObjectName,
				action.SCROLLANDBOOLEAN, 5);
		ThreadSleep(3000);
		if (selectListView != null) {
			log(LogStatus.INFO, viewList + " for " + ObjectName + " available", YesNo.No);
			return true;
		} else {
			log(LogStatus.ERROR, viewList + " for " + ObjectName + " is not available", YesNo.No);

		}

		return false;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param smaddl
	 * @param timeOut
	 * @return action Drop down Element
	 */
	public WebElement actionDropdownElement(String projectName, ShowMoreActionDropDownList smaddl, int timeOut) {
		String actionDropDown = smaddl.toString().replace("_", " ");
		String xpath = "//span[text()='" + actionDropDown + "']";
		xpath = "//*[@name='" + actionDropDown + "' or text()='" + actionDropDown + "']";
		return isDisplayed(driver,
				FindElement(driver, xpath, "show more action down arrow", action.SCROLLANDBOOLEAN, 10), "visibility",
				timeOut, actionDropDown);
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param toggleTab
	 * @param btnName
	 * @param action
	 * @param timeOut
	 * @return accordion SDGB uttons webelement
	 */
	public WebElement accordionSDGButtons(String projectName, String toggleTab, ToggleButtonGroup btnName,
			action action, int timeOut) {
		String btname = btnName.toString();
		String xpath = "//h2[contains(text(),'" + toggleTab + "')]/../../..//following-sibling::div//button[@title='"
				+ btname + "']";
		WebElement ele = FindElement(driver, xpath, toggleTab + " >> " + btname, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "Toggle Button : " + btname);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Toggle Button : " + btname);
		return ele;
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param contact
	 * @param field
	 * @param action
	 * @param timeOut
	 * @return accordion SDG Contact Checkbox webElement
	 */
	public WebElement accordionSDGContactCheckbox(String projectName, String contact, String field, action action,
			int timeOut) {
		String xpath = "//*[text()='" + contact + "']/../../../../following-sibling::td[contains(@data-label,'" + field
				+ "')]/../../..//th//input";
		WebElement ele = FindElement(driver, xpath, "checkbox for " + contact, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "checkbox for " + contact);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "checkbox for " + contact);
		return ele;
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param toggleTab
	 * @param btnName
	 * @param action
	 * @param timeOut
	 * @return accordion SDG Action Buttons webElement
	 */
	public WebElement accordionSDGActionButtons(String projectName, String toggleTab, String btnName, action action,
			int timeOut) {
		String btname = btnName.replace("_", " ");
		String xpath = "//*[contains(text(),'" + toggleTab + "')]/../../..//following-sibling::div//button[text()='"
				+ btname + "']";
		WebElement ele = FindElement(driver, xpath, toggleTab + " >> " + btname, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "Toggle Button : " + btname);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Toggle Button : " + btname);
		return ele;
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param field
	 * @param new1
	 * @param timeOut
	 * @return sdg button element
	 */
	public WebElement sdgButtons(String projectName, String field, String new1, int timeOut) {
		String xpath = "//span//*[text()='" + field + "']/../../../following-sibling::*//button[text()='" + new1 + "']";
		WebElement ele = FindElement(driver, xpath, "sdg buttons", action.BOOLEAN, timeOut);
		return isDisplayed(driver, ele, "visibility", timeOut, "sdg button");
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param contact
	 * @return sdg contact image xpath
	 */
	public String sdgContactImageXpath(String projectName, String contact) {
		return "//*[@title='" + contact + "']/preceding-sibling::img";
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param contact
	 * @param timeOut
	 * @return sdg contact image webElement
	 */
	public WebElement sdgContactImage(String projectName, String contact, int timeOut) {
		String xpath = sdgContactImageXpath(projectName, contact);
		WebElement ele = FindElement(driver, xpath, "contact image on sdg", action.BOOLEAN, timeOut);
		return isDisplayed(driver, ele, "visibility", timeOut, "contact image on sdg");
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param contact
	 * @param field
	 * @param timeOut
	 * @return true if successfully click on SDG edit button
	 */
	public boolean clickOnEditButtonOnSDG(String projectName, String contact, String field, int timeOut) {
		String xpath = "//*[text()='" + contact + "']/../../../../following-sibling::td[contains(@data-label,'" + field
				+ "')]//button";
		WebElement ele = FindElement(driver, xpath, "edit button for " + field, action.BOOLEAN, timeOut);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("return arguments[0].setAttribute('Styles','display: inline-block;')", ele);
		click(driver, ele, "edit", action.BOOLEAN);
		return true;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param field
	 * @param timeOut
	 * @return SDG input TextBox webElement
	 */
	public WebElement SDGInputTextbox(String projectName, String field, int timeOut) {
		String xpath = "//input[contains(@name,'" + field + "')]";
		WebElement ele = FindElement(driver, xpath, "input textbox " + field, action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "visibility", timeOut, "input textbox " + field);
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param fileName
	 * @param timeOut
	 * @return webElement for document Name
	 */
	public WebElement documentNameOnFilesApp(String projectName, String fileName, int timeOut) {
		String xpath = "//span[@title='" + fileName + "']";
		WebElement ele = FindElement(driver, xpath, fileName, action.BOOLEAN, timeOut);
		return isDisplayed(driver, ele, "visibility", timeOut, fileName);
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param fileName
	 * @param timeOut
	 * @return copied file link using right click
	 */
	public String rightClickOnFileAndCopy(String projectName, String fileName, int timeOut) {
		String xpath = "//img[@alt='" + fileName + "']";
		WebElement ele = FindElement(driver, xpath, fileName, action.BOOLEAN, timeOut);
		ele = isDisplayed(driver, ele, "visibility", timeOut, fileName);
		Actions actions = new Actions(driver);
		actions.contextClick(ele).build().perform();
		String a = "";
		ThreadSleep(1000);
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyRelease(KeyEvent.VK_DOWN);
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyRelease(KeyEvent.VK_DOWN);
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyRelease(KeyEvent.VK_DOWN);
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyRelease(KeyEvent.VK_DOWN);
			ThreadSleep(1000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			a = getClipBoardData();
			ThreadSleep(3000);
			robot.keyPress(KeyEvent.VK_ESCAPE);
			robot.keyRelease(KeyEvent.VK_ESCAPE);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;

	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param user
	 * @return true if able to delete photo
	 */
	public boolean deletePhotoInUserPage(String projectName, String user) {
		String imgId = null;
		DealPage dp = new DealPage(driver);
		ContactsPage cp = new ContactsPage(driver);
		Actions actions = new Actions(driver);
		scrollDownThroughWebelement(driver, dp.getimgIcon(projectName, 10), "img");
		click(driver, dp.getimgIcon(projectName, 10), "img icon", action.SCROLLANDBOOLEAN);
		ThreadSleep(2000);
		log(LogStatus.INFO, "click on img link", YesNo.No);
		if (click(driver, cp.getupdatePhotoLink(projectName, ContactPagePhotoActions.Delete_Photo, 10),
				ContactPagePhotoActions.Delete_Photo.toString(), action.SCROLLANDBOOLEAN)) {
			if (click(driver, getdeletePhotoButton(projectName, 10), "delete button", action.SCROLLANDBOOLEAN)) {
				ThreadSleep(5000);
				log(LogStatus.INFO, "successfully uploaded photo", YesNo.No);
				if (dp.getimgIconForPath(projectName, 5) == null) {
					log(LogStatus.INFO, "successfully deleted img" + imgId, YesNo.Yes);

					return true;
				} else {
					log(LogStatus.ERROR, "could not delete user img for " + user, YesNo.Yes);
					sa.assertTrue(false, "could not delete user img for " + user);

				}
			} else {
				log(LogStatus.ERROR, "delete photo button on popup is not clickable", YesNo.Yes);
				sa.assertTrue(false, "delete photo button on popup is not clickable");
			}
		} else {
			log(LogStatus.ERROR, "delete photo button is not clickable", YesNo.Yes);
			sa.assertTrue(false, "delete photo button is not clickable");
		}
		return false;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param btnName
	 * @param action
	 * @param timeOut
	 * @return toggle edit save Button webElement
	 */
	public WebElement toggleEditSaveButton(String projectName, String btnName, action action, int timeOut) {
		String xpath = "//button[contains(text(),'" + btnName + "')]/../../../../../../../..//button[text()='Save']";
		WebElement ele = FindElement(driver, xpath, "Toggle Button : " + btnName, action, timeOut);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Toggle Button : " + btnName);
		return ele;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param btnName
	 * @param action
	 * @param timeOut
	 * @return toggle edit cancel Button webElement
	 */
	public WebElement toggleEditCancelButton(String projectName, String btnName, action action, int timeOut) {
		String xpath = "//div[contains(@class,'sdgborder')]//button[@title='Cancel' or text()='Cancel']";
		// String xpath =
		// "//button[contains(text(),'"+btnName+"')]/../../../../../../../..//button[text()='Cancel']";
		WebElement ele = FindElement(driver, xpath, "Toggle Button : " + btnName, action, timeOut);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Toggle Button : " + btnName);
		return ele;
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param field
	 * @param name
	 * @param timeOut
	 * @return webElement for cross icon
	 */
	public WebElement crossIconForEventField(String projectName, String field, String name, int timeOut) {
		String xpath = "//label[text()='" + field + "']/following-sibling::div//input[@placeholder='" + name
				+ "']/following-sibling::div/button";

		WebElement ele = FindElement(driver, xpath, "cross Button : " + field, action.BOOLEAN, timeOut);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "cross Button : " + field);
		return ele;
	}

	public boolean editButtonToggleSDG(String projectName, PageName pageName, String record, int toggleOneOrTwo,
			int timeOut) {
		String xpath = "";
		if (pageName == PageName.Object1Page)
			xpath = "(//*[text()='" + record + "']/../../following-sibling::span//button[@title='Edit'])["
					+ toggleOneOrTwo + "]";
		else if (pageName == PageName.Object4Page)
			xpath = "(//*[text()='" + record + "']/../following-sibling::span//button[@title='Edit'])[" + toggleOneOrTwo
					+ "]";
		WebElement ele = FindElement(driver, xpath, "edit Button : " + record, action.BOOLEAN, timeOut);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("return arguments[0].setAttribute('Styles','display: inline-block;')", ele);
		click(driver, ele, "edit", action.BOOLEAN);
		return true;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param sourceImg
	 * @param targetImg
	 * @param timeOut
	 * @return true if able to drag and drop successfully
	 */
	public boolean dragNDropUsingScreen(String projectName, String sourceImg, String targetImg, int timeOut) {
		boolean flag = false;
		Screen screen = new Screen();
		try {
			screen.dragDrop(sourceImg, targetImg);
			flag = true;
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param ObjectName
	 * @param viewList
	 * @param timeOut
	 * @return true if automall all is added to view list
	 */
	public boolean isAutomationAllListViewForObject(String projectName, String ObjectName, String viewList,
			int timeOut) {
		String xpath = "";
		ThreadSleep(3000);
		xpath = "//*[text()='" + ObjectName + "']/../../../following-sibling::*//*[text()='" + viewList + "']";
		WebElement selectListView = FindElement(driver, xpath, "Select List View : " + viewList + " for " + ObjectName,
				action.SCROLLANDBOOLEAN, 5);
		ThreadSleep(3000);
		if (selectListView != null) {
			log(LogStatus.INFO, viewList + " for " + ObjectName + " available", YesNo.No);
			return true;
		} else {
			log(LogStatus.ERROR, viewList + " for " + ObjectName + " is not available", YesNo.No);

		}

		return false;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param field
	 * @param name
	 * @param timeOut
	 * @return webElement
	 */
	public WebElement SDGNewButton(String projectName, String field, String name, int timeOut) {
		String xpath = "//label[text()='" + field + "']/following-sibling::div//input[@placeholder='" + name
				+ "']/following-sibling::div/button";

		WebElement ele = FindElement(driver, xpath, "cross Button : " + field, action.BOOLEAN, timeOut);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "cross Button : " + field);
		return ele;
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param fieldValues
	 * @return true if sdg action created successfully
	 */
	public boolean createSDGAction(String projectName, String[] fieldValues) {
		String xpath = "";
		WebElement ele = null;
		String finalLabelName = "";
		String labelName = "", value = "";
		for (String field : fieldValues) {
			labelName = field.split(breakSP)[0];
			value = field.split(breakSP)[1];
			finalLabelName = labelName.replace("_", " ");
			xpath = "//*[text()='" + finalLabelName + "']/following-sibling::div//input";
			if (labelName.equalsIgnoreCase(SDGActionsCreationLabel.Event_Payload.toString()))
				xpath = "//*[text()='" + finalLabelName + "']/following-sibling::div//textarea";
			ele = FindElement(driver, xpath, finalLabelName, action.BOOLEAN, 10);
			ele = isDisplayed(driver, ele, "Visibility", 10, finalLabelName);
			if (labelName.equalsIgnoreCase(SDGActionsCreationLabel.Action_Type.toString())) {
				if (clickUsingJavaScript(driver, ele, finalLabelName)) {
					xpath = "//*[text()='Action Type']/following-sibling::div//*[@title='" + value + "']";
					ele = FindElement(driver, xpath, finalLabelName, action.BOOLEAN, 10);
					ele = isDisplayed(driver, ele, "Visibility", 10, finalLabelName);
					if (clickUsingJavaScript(driver, ele, value)) {
						log(LogStatus.INFO, value + " dropdown element successfully selected", YesNo.Yes);

					} else {
						log(LogStatus.SKIP, value + " dropdown element is not visible", YesNo.Yes);
						BaseLib.sa.assertTrue(false, value + " dropdown element is not visible");
					}
				} else {
					log(LogStatus.SKIP, finalLabelName + " dropdown is not visible", YesNo.Yes);
					BaseLib.sa.assertTrue(false, finalLabelName + " dropdown is not visible");
				}
			} else {
				if (sendKeys(driver, ele, value, finalLabelName, action.SCROLLANDBOOLEAN)) {

				} else {
					log(LogStatus.SKIP, finalLabelName + " is not visible", YesNo.Yes);
					BaseLib.sa.assertTrue(false, finalLabelName + " is not visible");
				}
			}

		}
		if (click(driver, getNavigationTabSaveBtn(projectName, 10), "save", action.SCROLLANDBOOLEAN)) {

		} else {
			log(LogStatus.SKIP, "save button is not clickable", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "save button is not clickable");
		}
		return true;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param itemName
	 * @param action
	 * @param timeOut
	 * @return getItemInList webelement
	 */
	public WebElement getItemInList(String projectName, String itemName, action action, int timeOut) {
		String xpath = "//*[@title='" + itemName + "']//strong[text()='" + itemName.split(" ")[0] + "']";
		WebElement ele = FindElement(driver, xpath, itemName, action, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, itemName);
	}

	/**
	 * @author Ankit Jaiswal
	 * @param TabName
	 * @return true if click on Tab
	 */
	public boolean clickOnTab(String environment, String mode, TabName TabName) {
		String tabName = null;
		String suffix = " Tab";
		boolean flag = false;
		WebElement ele;
		switch (TabName) {
		case ContactTab:
			tabName = "Contacts";
			break;
		case InstituitonsTab:
			tabName = "Firms";
			break;
		case FundraisingsTab:
			tabName = "Fundraisings";
			break;
		case FundsTab:
			tabName = "Funds";
			break;
		case CommitmentsTab:
			tabName = "Commitments";
			break;
		case PartnershipsTab:
			tabName = "Partnerships";
			break;
		case HomeTab:
			tabName = "Home";
			break;
		case FundDistributions:
			tabName = "Fund Distributions";
			break;
		case InvestorDistributions:
			tabName = "Investor Distributions";
			break;
		case MarketingInitiatives:
			tabName = "Marketing Initiatives";
			break;
		case MarketingProspects:
			tabName = "Marketing Prospects";
			break;
		case NavatarSetup:
			tabName = "Navatar Setup";
			break;
		case Pipelines:
			tabName = "Pipelines";
			break;
		case CapitalCalls:
			tabName = "Capital Calls";
			break;
		case FundDrawdowns:
			tabName = "Fund Drawdowns";
			break;
		case FundInvestments:
			tabName = "Fund Investments";
			break;
		case FundOfFundsTransactions:
			tabName = "Fund Of Funds Transactions";
			break;
		case FundraisingContacts:
			tabName = "Fundraising Contacts";
			break;
		case ReportsTab:
			tabName = "Reports";
			break;
		case DealTab:
			tabName = "Deals";
			break;
		case Correspondence_Lists:
			tabName = "Correspondence Lists";
			break;
		case AgreementsAmendments:
			tabName = "Agreements/Amendments";
			break;
		case TaskTab:
			tabName = "Tasks";
			break;

		case Object1Tab:
			tabName = "Firms";
			break;
		case Object2Tab:
			tabName = "Contacts";
			break;
		case OfficeLocations:
			tabName = "Office Locations";
			break;
		default:
			return flag;
		}
		System.err.println("Passed switch statement");
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			tabName = tabName + suffix;
			ele = isDisplayed(driver, FindElement(driver, "//a[contains(@title,'" + tabName + "')]", tabName,
					action.SCROLLANDBOOLEAN, 10), "visibility", 10, tabName);
			if (ele != null) {
				if (click(driver, ele, tabName, action.SCROLLANDBOOLEAN)) {
					CommonLib.log(LogStatus.PASS, "Tab found", YesNo.No);
					flag = true;
				} else {

				}
			} else {
				CommonLib.log(LogStatus.INFO, "Going to found tab after clicking on More Icon", YesNo.No);
				if (click(driver, getMoreTabIcon(environment, mode, 10), "More Icon", action.SCROLLANDBOOLEAN)) {
					if (click(driver,
							isDisplayed(driver,
									FindElement(driver, "//a[contains(@title,'" + tabName + "')]", tabName,
											action.SCROLLANDBOOLEAN, 10),
									"visibility", 10, tabName),
							tabName, action.SCROLLANDBOOLEAN)) {
						CommonLib.log(LogStatus.INFO, "Tab found on More Icon", YesNo.No);
						flag = true;
					}
				} else {

				}
			}
		} else {
			ele = isDisplayed(driver,
					FindElement(driver,
							"//a[contains(@href,'lightning') and contains(@title,'" + tabName + "')]/span/..", tabName,
							action.SCROLLANDBOOLEAN, 30),
					"visibility", 30, tabName);
			if (ele != null) {
				appLog.info("Tab Found");
				ThreadSleep(5000);
				if (clickUsingJavaScript(driver, ele, tabName + " :Tab")) {
					CommonLib.log(LogStatus.INFO, "Tab found", YesNo.No);
					appLog.info("Clicked on Tab : " + tabName);
					flag = true;
				} else {
					appLog.error("Not Able to Click on Tab : " + tabName);
				}

			} else {
				CommonLib.log(LogStatus.INFO, "Going to found tab after clicking on More Icon", YesNo.No);
				if (click(driver, getMoreTabIcon(environment, mode, 10), "More Icon", action.SCROLLANDBOOLEAN)) {
					ele = isDisplayed(driver, FindElement(driver,
							"//a[contains(@href,'lightning')]/span[@class='slds-truncate']/span[contains(text(),'"
									+ tabName + "')]",
							tabName, action.SCROLLANDBOOLEAN, 10), "visibility", 10, tabName);
					if (ele != null) {
						if (clickUsingJavaScript(driver, ele, tabName + " :Tab")) {
							appLog.info("Clicked on Tab on More Icon: " + tabName);
							CommonLib.log(LogStatus.INFO, "Tab found on More Icon", YesNo.No);
							flag = true;
						}
					}

				} else {
					appLog.error("Not Able to Clicked on Tab on More Icon: " + tabName);
				}

			}
		}

		if (TabName.NavatarSetup.toString().equalsIgnoreCase(TabName.toString())) {
			NavatarSetupPageBusinessLayer np = new NavatarSetupPageBusinessLayer(driver);

			if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
				switchToFrame(driver, 10, np.getnavatarSetUpTabFrame_Lighting(environment, 10));
			}
			if (FindElement(driver, "(//p[contains(text(),'Deal Creation')])[1]", "Deal Creation", action.BOOLEAN,
					60) != null) {

				appLog.info("Landing Page Verified : " + "Deal Creation");

				flag = true;

			} else {
				appLog.error("Landing Page Not Verified : Deal Creation");
				flag = false;
			}
			switchToDefaultContent(driver);
		}
		return flag;
	}

	/**
	 * @param searchText
	 * @return true if able to select value from Lookup window
	 */
	public boolean selectValueFromLookUpWindow(String searchText) {
		String parentWindow = null;
		WebElement ele = null;
		parentWindow = switchOnWindow(driver);
		if (parentWindow != null) {
			switchToFrame(driver, 20, getLookUpSearchFrame(10));
			ThreadSleep(5000);
			if (sendKeys(driver, getLookUpSearchTextBox(30), searchText, "search text box", action.SCROLLANDBOOLEAN)) {
				if (click(driver, getLookUpSearchGoBtn(20), "go button", action.SCROLLANDBOOLEAN)) {
					switchToDefaultContent(driver);
					switchToFrame(driver, 20, getLookUpResultFrame(10));
					ele = isDisplayed(driver, FindElement(driver, "//a[text()='" + searchText + "']",
							searchText + " text value", action.SCROLLANDBOOLEAN, 20), "visibility", 20,
							searchText + " text value");
					if (ele != null) {
						if (!click(driver, ele, searchText + " text value", action.SCROLLANDBOOLEAN)) {
							appLog.info("clicked on " + searchText + " in lookup pop up");
						}
						driver.switchTo().window(parentWindow);
						return true;
					} else {
						appLog.error(searchText + " is not visible in look up popup so cannot select it");
						driver.close();
						driver.switchTo().window(parentWindow);
					}
				} else {
					appLog.error("Not able to click on go button so cannot select " + searchText);
					driver.close();
					driver.switchTo().window(parentWindow);
				}
			} else {
				appLog.error("Not able to pass value in search text box : " + searchText + " so cannot select value "
						+ searchText + " from look up");
				driver.close();
				driver.switchTo().window(parentWindow);
			}
		} else {
			appLog.error("No new window is open so cannot select value " + searchText + " from look up");
		}
		return false;
	}

	/**
	 * @param projectName
	 * @param TabName
	 * @return true if Tab is already selected
	 */
	public boolean getSelectedTab(String projectName, String TabName) {
		String tabName = null;
		boolean flag = false;
		WebElement ele;
		if (TabName.contains("Entit")) {
			tabName = "Entities";
		} else {
			tabName = TabName;
		}
		System.err.println("Passed switch statement");
		if (tabName != null) {
			ele = FindElement(driver,
					"//a[@title='" + tabName
							+ "']/parent::*[@class='navItem slds-context-bar__item slds-shrink-none slds-is-active']",
					tabName, action.SCROLLANDBOOLEAN, 30);
			ele = isDisplayed(driver, ele, "visibility", 30, tabName);
			if (ele != null) {
				appLog.info("Tab is Already Selected : " + tabName);
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param alreadyCreated
	 * @param isClick        TODO
	 * @param timeout
	 * @param tabName
	 * @return true if able to click on particular item on Particular tab
	 */
	public boolean CheckAlreadyCreatedItem(String projectName, String alreadyCreated, boolean isClick, int timeout) {
		boolean flag = false;
		String xpath = "";
		String viewList = null;
		viewList = "All";
		WebElement ele, selectListView;
		ele = null;
		ThreadSleep(3000);
		refresh(driver);
		if (click(driver, getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(3000);
			xpath = "//div[@class='listContent']//li/a/span[text()='" + viewList + "']";
			selectListView = FindElement(driver, xpath, "Select List View : " + viewList, action.SCROLLANDBOOLEAN, 30);
			if (click(driver, selectListView, "select List View : " + viewList, action.SCROLLANDBOOLEAN)) {
				ThreadSleep(3000);
				ThreadSleep(5000);
			} else {
				appLog.error("Not able to enter value on Search Box");
			}
		} else {
			appLog.error("Not able to select on Select View List : " + viewList);
		}

		if (sendKeys(driver, getSearchIcon_Lighting(20), alreadyCreated + "\n", "Search Icon Text",
				action.SCROLLANDBOOLEAN)) {
			ThreadSleep(2000);
			xpath = "//table[@data-aura-class='uiVirtualDataTable']//tbody//tr//th//span//*[text()='" + alreadyCreated
					+ "']";
			ele = FindElement(driver, xpath, alreadyCreated, action.BOOLEAN, 30);
			if (ele != null) {
				appLog.info("Item Found : " + alreadyCreated);
				flag = true;
			} else {
				appLog.error("Item not Found : " + alreadyCreated);
			}
		} else {
			appLog.error("Not able to click on Select List Icon");
		}
		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @param sdgGridName
	 * @param labelName
	 * @param searchDataName
	 * @param operator
	 * @param wantToDataShearch
	 * @return
	 */
	public boolean SearchDealFilterDataOnHomePage(SDGGridName sdgGridName, String labelName, String searchDataName,
			Operator operator, YesNo wantToDataShearch) {
		if (selectVisibleTextFromDropDown(driver, getSDGGridDropDown(sdgGridName, labelName, 10), "deal drop down",
				operator)) {
			log(LogStatus.PASS, "Select Equals From Deal Drop Down in filter", YesNo.No);
			ThreadSleep(1000);
			if (wantToDataShearch.toString().equalsIgnoreCase(YesNo.Yes.toString())) {
				WebElement ele = FindElement(driver,
						"//*[text()='" + labelName + "']/../../../following-sibling::div//input", "text box ",
						action.SCROLLANDBOOLEAN, 10);
				if (ele != null) {
					if (sendKeys(driver, ele, searchDataName + "\n", labelName + " name text box ",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "enter " + labelName + " name :" + searchDataName, YesNo.No);
						return true;
					} else {
						log(LogStatus.FAIL, "Not able to Enter " + labelName + " name :" + searchDataName, YesNo.Yes);
					}
				} else {
					log(LogStatus.FAIL, labelName + " Text box is not visible so cannot enter the " + labelName
							+ " name " + searchDataName, YesNo.No);
				}
			} else {
				return true;
			}
		} else {
			log(LogStatus.FAIL, "Not able to Select Equals From Deal Drop Down in filter", YesNo.No);
		}
		return false;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param pageName
	 * @param label
	 * @param isMultipleAssociation
	 * @param name
	 * @param action
	 * @param timeOut
	 * @return boolean
	 */
	public boolean ClickOnCrossButtonForAlreadySelectedItem(String projectName, PageName pageName, String label,
			boolean isMultipleAssociation, String name, action action, int timeOut) {

		WebElement ele = getCrossButtonForAlreadySelectedItem(projectName, pageName, label, isMultipleAssociation, name,
				action, timeOut);
		boolean flag = clickUsingJavaScript(driver, ele, "Cross Button against : " + name + " For Label : " + label,
				action);
		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param time
	 * @return boolean value
	 */
	public boolean verifyBeforeTimeOrNot(String projectName, String time) {
		Calendar cal = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat("H:mm a");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-8:00"));
		sdf.applyPattern("h:mm a");
		// System.out.println(sdf.format(Calendar.getInstance().getTime()));
		System.out.println(sdf.format(cal.getTime()));
		Date timecurrent = null, lt2 = null;
		try {
			timecurrent = sdf.parse(sdf.format(cal.getTime()));
			lt2 = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("current time is " + sdf.format(cal.getTime()));
		System.out.println("time on page is " + time);
		System.out.println(timecurrent.after(lt2));
		return timecurrent.after(lt2);
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param taskName
	 * @return WebElement
	 */
	public WebElement getTaskLink(String projectName, String taskName) {
		WebElement ele = moreStepsBtn(projectName, EnableDisable.Enable, 10);
		click(driver, ele, "More Steps", action.BOOLEAN);
		ThreadSleep(2000);
		// String xpath = "//a[text()='" + taskName + "']";
		String xpath = "//h3[@title='" + taskName + "']/a";
		ele = FindElement(driver, xpath, taskName, action.SCROLLANDBOOLEAN, 20);
		ele = isDisplayed(driver, ele, "Visibility", 20, taskName);
		return ele;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param restoreItem
	 * @return true if able to restore deleted item from recycle bin
	 */
	public boolean restoreValueFromRecycleBin(String projectName, String restoreItem) {
		boolean flag = false;
		TabName tabName = TabName.RecycleBinTab;
		WebElement ele;
		if (clickOnTab(projectName, tabName)) {
			log(LogStatus.INFO, "Clicked on Tab : " + tabName + " For : " + restoreItem, YesNo.No);
			ThreadSleep(1000);
			clickOnAlreadyCreatedItem(projectName, tabName, restoreItem, 20);
			log(LogStatus.INFO, "Clicked on  : " + restoreItem + " For : " + tabName, YesNo.No);
			ThreadSleep(2000);
			ele = getCheckboxOfRestoreItemOnRecycleBin(projectName, restoreItem, 10);
			if (clickUsingJavaScript(driver, ele, "Check box against : " + restoreItem, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on checkbox for " + restoreItem, YesNo.No);
				ThreadSleep(1000);
				ele = getRestoreButtonOnRecycleBin(projectName, 10);
				if (clickUsingJavaScript(driver, ele, "Restore Button : " + restoreItem, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on Restore Button for " + restoreItem, YesNo.No);
					ThreadSleep(5000);
					flag = true;
				} else {
					log(LogStatus.SKIP, "Not Able to Click on Restore Button for " + restoreItem, YesNo.Yes);
				}

			} else {
				log(LogStatus.SKIP, "Not Able to Click on checkbox for " + restoreItem, YesNo.Yes);
			}

		} else {
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName + " For : " + restoreItem, YesNo.Yes);
		}
		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @return true if able to click on Calender Cell
	 */
	public boolean clickAnyCellonCalender(String projectName) {
		boolean flag = false;
		if (click(driver, getCalenderIcon(30), "View Calender Icon", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Click on View Calender Icon", YesNo.No);
			if (click(driver, getNewEventBtn(30), "View Calender Icon", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Click on Calender new event button", YesNo.No);
				ThreadSleep(5000);
				flag = true;
			} else {
				log(LogStatus.SKIP, "Not Able to Click on Calender new event button", YesNo.Yes);
			}
		} else {
			log(LogStatus.SKIP, "Not Able to Click on View Calender Icon", YesNo.Yes);
		}
		return flag;
	}

	public WebElement getElementAtPage(String projectName, String labelName, String labelValue, action action,
			int timeOut) {
		labelName = labelName.toString().replace("_", " ");
		String xpath = "//*[text()='" + labelName + "']/../following-sibling::div//*[text()='" + labelValue + "']";
		WebElement ele = FindElement(driver, xpath, labelName + " with " + labelValue, action, timeOut);
		scrollDownThroughWebelement(driver, ele, labelName + " with " + labelValue);
		return isDisplayed(driver, ele, "visibility", timeOut, labelName + " with " + labelValue);

	}

	public boolean isRelatedListAvailable(String environment, String mode, TabName tabName, RelatedList RelatedList,
			int timeOut) {
		WebElement ele;
		WebElement relatedList;

		boolean flag = false;
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			ele = FindElement(driver, "//span[@class='listTitle'][text()='" + RelatedList + "']",
					RelatedList.toString(), action.SCROLLANDBOOLEAN, 10);
			relatedList = isDisplayed(driver, ele, "Visibility", timeOut, RelatedList.toString(),
					action.SCROLLANDBOOLEAN);

		} else {
			scrollThroughOutWindow(driver);
			ele = FindElement(driver, "//span[text()='" + RelatedList + "']", RelatedList.toString(),
					action.SCROLLANDBOOLEAN, 10);
			relatedList = isDisplayed(driver, ele, "Visibility", timeOut, RelatedList.toString(),
					action.SCROLLANDBOOLEAN);
		}
		if (relatedList != null) {
			flag = true;
		}

		return flag;
	}

	/**
	 * @param environment
	 * @param timeOut
	 * @return
	 */
	public WebElement getRelatedTab_Lighting(String environment, RecordType RecordType, int timeOut) {

		List<WebElement> eleList = FindElements(driver, "//*[text()='Related']", "Related Tab");
		int i = 0;
		for (WebElement ele : eleList) {
			i++;
			WebElement ele1;
			ele1 = isDisplayed(driver, ele, "Visibility", timeOut, "Related Tab " + i);
			if (ele1 != null) {
				return ele1;
			}

		}

		return null;

	}

	public boolean verifyRelatedListViewAllColumnAndValue(String[][] headersWithValues) {
		String columnXpath = "";
		String valuXpath = "";
		WebElement ele;
		String actual = "";
		String[] headerValues = new String[headersWithValues.length];
		String[] Values = new String[headersWithValues.length];
		boolean flag = true;
		ThreadSleep(5000);
		for (int j = 0; j < headerValues.length; j++) {
			headerValues[j] = headersWithValues[j][0].replace("_", " ");
			Values[j] = headersWithValues[j][1];
		}

		columnXpath = "//*[@title='" + headerValues[0] + "']";
		String columnOrder = headerValues[0];

		for (int j = 1; j < headerValues.length; j++) {
			columnXpath = columnXpath + "//following-sibling::*[@title='" + headerValues[j] + "']";
			columnOrder = columnOrder + "  <>  " + headerValues[j];
		}

		ele = FindElement(driver, columnXpath, "Header ", action.BOOLEAN, 30);

		if (ele != null) {
			appLog.info("Header Column Matched with order : " + columnOrder);
		} else {
			flag = false;
			appLog.error("Header Column Not Matched with order : " + columnOrder);
			BaseLib.sa.assertTrue(false, "Header Column Not Matched with order : " + columnOrder);

		}

		String val = "";
		for (int j = 1; j < Values.length; j++) {
			val = Values[j];
			if (Values[j].isEmpty() || Values[j].equals("")) {
				valuXpath = "//*[contains(@title,'" + Values[0] + "')]/../..//following-sibling::td[" + j
						+ "]//span//*";
			} else {
				valuXpath = "//*[contains(@title,'" + Values[0] + "')]/../..//following-sibling::td[" + j
						+ "]//*[contains(@title,'" + val + "') or contains(text(),'" + val + "')]";
			}

			ele = FindElement(driver, valuXpath, val, action.BOOLEAN, 5);

			if (ele != null) {

				actual = ele.getText().trim();
				if (Values[j].isEmpty() || Values[j].equals("")) {
					if (actual.isEmpty() || actual.equals("")) {
						appLog.info("Header Column " + headerValues[j] + " Matched with Value " + Values[j]);
					} else {
						flag = false;
						appLog.error("Header Column " + headerValues[j] + " Not Matched with Value " + Values[j]);
						BaseLib.sa.assertTrue(false,
								"Header Column " + headerValues[j] + " Not Matched with Value " + Values[j]);

					}
				} else {
					appLog.info("Header Column " + headerValues[j] + " Matched with Value " + Values[j]);
				}

			} else {
				flag = false;
				appLog.error("Header Column " + headerValues[j] + " Not Matched with Value " + Values[j]);
				BaseLib.sa.assertTrue(false,
						"Header Column " + headerValues[j] + " Not Matched with Value " + Values[j]);

			}

		}

		return flag;

	}

	/**
	 * @author Akul Bhutani
	 * @param environment
	 * @param mode
	 * @param rl
	 * @param viewAllOrNew TODO
	 * @return This method is used to scroll to a related list on lightning mode.
	 *         Select true for View all and false for New button
	 */
	public boolean scrollToRelatedListViewAll_Lightning(String environment, String mode, RelatedList rl,
			boolean viewAllOrNew) {
		if (mode.toString().equalsIgnoreCase(Mode.Lightning.toString())) {
			String xpath = "";
			if (viewAllOrNew)
				xpath = "/ancestor::article//span[text()='View All']";
			else
				xpath = "/../../../../../following-sibling::div//*[@title='New']";
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0);");
			int widgetTotalScrollingWidth = Integer
					.parseInt(String.valueOf(((JavascriptExecutor) driver).executeScript("return window.outerHeight")));
			int j = 50;
			int i = 0;
			WebElement el = null;
			while (el == null) {
				el = isDisplayed(driver, FindElement(driver, "//*[text()='" + rl.toString() + "']" + xpath,
						rl.toString(), action.BOOLEAN, 5), "visibility", 5, rl.toString());
				((JavascriptExecutor) driver).executeScript("window.scrollBy( 0 ," + j + ")");
				i += j;
				if (i >= widgetTotalScrollingWidth) {
					return false;
				} else if (el != null)
					return true;
			}
			return false;
		} else
			return true;
	}

	public boolean ClickOnLookUpAndSelectValueFromLookUpWindow(String environment, String mode, LookUpIcon lookUpIcon,
			String searchText, String lookUpValues) {

		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);

		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			appLog.info("Classic : ");
			return clickOnLookUpAndSelectValueFromLookUpWindow_Classic(environment, mode, lookUpIcon, searchText,
					lookUpValues);
		} else {
			appLog.info("Lighting : ");
			return cp.officeLocationInputValueAndSelect_Lighting(environment, mode, searchText, lookUpValues);
		}

	}

	public boolean verifyOpenActivityRelatedList(String environment, String mode, TabName tabName, String subject,
			String relatedTo, String contactName) {
		WebElement ele;
		String xpath;
		click(driver, getRelatedTab(RelatedTab.Communications.toString(), 20), RelatedTab.Communications.toString(),
				action.BOOLEAN);
		ThreadSleep(5000);
		ele = isDisplayed(driver,
				FindElement(driver, "//button[contains(@class,'moresteps')]", "More steps", action.BOOLEAN, 20),
				"visibility", 20, "");

		click(driver, ele, "More steps", action.BOOLEAN);
		log(LogStatus.INFO, "click on more steps button", YesNo.No);

		if (tabName.toString().equalsIgnoreCase(TabName.ContactTab.toString())) {
			if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
				if (relatedTo == null) {
					xpath = "(//h3[text()='Open Activities']/ancestor::div[@class='bRelatedList']//div[@class='pbBody']//tr//th/a[contains(text(),'"
							+ subject + "')]/../following-sibling::td)[1]";
					ele = FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, 10);
					System.err.println(">>>>>ele:");
					if (ele != null) {
						String msg = ele.getText().trim();
						System.err.println(">>>>>msg: " + msg);
						if (msg.isEmpty()) {
							return true;
						} else {
							return false;
						}
					} else {
						return false;
					}
				} else {
					xpath = "//h3[text()='Open Activities']/ancestor::div[@class='bRelatedList']//div[@class='pbBody']//tr//th/a[contains(text(),'"
							+ subject + "')]/../following-sibling::td/a[text()='" + relatedTo + "']";
				}

				ele = FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, 10);
			} else {
				if (relatedTo == null || contactName == null) {
					xpath = "//div[contains(@class,'slds-section__content')]//a[text()='" + subject + "']";
				} else {
					xpath = "//div[contains(@class,'slds-section__content')]//a[text()='" + subject
							+ "']/ancestor::div[@class='slds-media']//a[text()='" + relatedTo + "']";
				}
				xpath = "//div[@class='cActivityTimeline']//a[text()='" + subject + "']";
				ele = FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, 10);
			}
		} else if (tabName.toString().equalsIgnoreCase(TabName.InstituitonsTab.toString())) {
			if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
				if (relatedTo == null) {
					xpath = "(//h3[text()='Open Activities']/ancestor::div[@class='bRelatedList']//div[@class='pbBody']//tr//th/a[contains(text(),'"
							+ subject + "')]/../following-sibling::td/a[text()='" + contactName
							+ "']/../following-sibling::td)[1]";
					ele = FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, 10);
					System.err.println(">>>>>ele:");
					if (ele != null) {
						String msg = ele.getText().trim();
						System.err.println(">>>>>msg: " + msg);
						if (msg.isEmpty()) {
							return true;
						} else {
							return false;
						}
					} else {
						return false;
					}
				} else {
					xpath = "//h3[text()='Open Activities']/ancestor::div[@class='bRelatedList']//div[@class='pbBody']//tr//th/a[contains(text(),'"
							+ subject + "')]/../following-sibling::td/a[text()='" + contactName
							+ "']/../following-sibling::td/a[text()='" + relatedTo + "']";
				}
				ele = FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, 10);
			} else {
				xpath = "//div[@class='cActivityTimeline']//a[text()='" + subject + "']";
				ele = FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, 10);
			}
		} else {
			return false;
		}
		if (ele != null) {
			return true;
		} else {
			return false;
		}

	}

	public boolean verifyActivityHistoryRelatedList(String environment, String mode, TabName tabName, String subject,
			String relatedTo, String contactName) {
		WebElement ele = null;
		String xpath;
		click(driver, getRelatedTab(RelatedTab.Communications.toString(), 20), RelatedTab.Communications.toString(),
				action.BOOLEAN);
		ThreadSleep(5000);
		ele = isDisplayed(driver,
				FindElement(driver, "//button[contains(@class,'moresteps')]", "More steps", action.BOOLEAN, 20),
				"visibility", 20, "");

		click(driver, ele, "More steps", action.BOOLEAN);
		log(LogStatus.INFO, "click on more steps button", YesNo.No);

		if (tabName.toString().equalsIgnoreCase(TabName.ContactTab.toString())) {
			if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
				xpath = "//h3[text()='Activity History']/ancestor::div[@class='bRelatedList']//div[@class='pbBody']//tr//th/a[contains(text(),'"
						+ subject + "')]/../following-sibling::td/a[text()='" + relatedTo + "']";
				ele = FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, 10);
			} else {
				xpath = "//div[@class='cActivityTimeline']//a[text()='" + subject + "']";
				ele = FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, 10);
			}
		} else if (tabName.toString().equalsIgnoreCase(TabName.InstituitonsTab.toString())) {
			if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
				xpath = "//h3[text()='Activity History']/ancestor::div[@class='bRelatedList']//div[@class='pbBody']//tr//th/a[text()='"
						+ subject + "']/../following-sibling::td/a[text()='" + contactName
						+ "']/../following-sibling::td/a[text()='" + relatedTo + "']";
				ele = FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, 10);
			} else {
				xpath = "//div[@class='cActivityTimeline']//a[text()='" + subject + "']";
				ele = FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, 10);
			}
		} else {
			return false;
		}
		if (ele != null) {
			return true;
		} else {
			return false;
		}

	}

	public boolean verifyAffliationRelatedList(String environment, String mode, TabName tabName,
			String institutionName) {
		WebElement ele;
		String xpath;
		click(driver, getRelatedTab(RelatedTab.Affiliations.toString(), 20), RelatedTab.Affiliations.toString(),
				action.BOOLEAN);
		ThreadSleep(2000);
		if (tabName.toString().equalsIgnoreCase(TabName.ContactTab.toString())) {
			if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
				xpath = "//h3[text()='Affiliations']/ancestor::div[@class='bRelatedList']//div[@class='pbBody']//tr//th/a[contains(text(),'AF')]/../following-sibling::td/a[text()='"
						+ institutionName + "']/../following-sibling::td[text()='Former Employee']";
				ele = FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, 10);
			} else {
				xpath = "//div[@class='navpeIISdgBase navpeIISdg']//a[text()='" + institutionName
						+ "']/ancestor::tr//*[text()='Former Employee']";
				ele = FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, 10);
			}
		} else {
			return false;
		}

		if (ele != null) {
			return true;
		} else {
			return false;
		}

	}

	public boolean verifyGridErrorMessage(String environment, String mode, RelatedList gridSectionName,
			String expectedMsg, int timeOut) {
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			return verifyNoDataToDisplayErrorMsg_Lightning(environment, mode, gridSectionName, expectedMsg, timeOut);
		} else {
			return verifyNoDataToDisplayErrorMsg_Classic(environment, mode, gridSectionName, expectedMsg, timeOut);
		}

	}

	public boolean verifyNoDataToDisplayErrorMsg_Classic(String environment, String mode, RelatedList gridSectionName,
			String expectedMsg, int timeOut) {
		String xpath = "//h3[text()='" + gridSectionName
				+ "']/ancestor::div[contains(@class,'bRelatedList')]//div[@class='pbBody']//tr//th[1]";
		WebElement ele = isDisplayed(
				driver, FindElement(driver, xpath, gridSectionName.toString() + " error message",
						action.SCROLLANDBOOLEAN, timeOut),
				"visibility", timeOut, gridSectionName.toString() + " error message");
		String msg;
		boolean flag = false;
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			msg = ele.getText().trim();
			CommonLib.log(LogStatus.INFO, "Grid Message : " + msg, YesNo.No);
			if (expectedMsg.equalsIgnoreCase(msg)) {
				flag = true;
			}
		}
		return flag;

	}

	public boolean clickOnGridSection_Lightning(String environment, String mode, RelatedList gridSectionName,
			int timeOut) {
		WebElement ele = null;
		boolean flag = false;
		String xpath1 = "//span[@title='" + gridSectionName + "']";
		List<WebElement> eleList = FindElements(driver, xpath1, gridSectionName.toString());
		for (WebElement webElement : eleList) {
			ele = isDisplayed(driver, webElement, "visibility", 3, gridSectionName.toString() + " link");
			if (ele != null) {
				if (click(driver, ele, gridSectionName.toString() + " link", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on " + gridSectionName.toString() + " link", YesNo.No);
					flag = true;
					return flag;
				} else {
					log(LogStatus.ERROR, "Not able to click on " + gridSectionName.toString()
							+ " link so cannot verify error message", YesNo.Yes);
				}
			}

		}

		return flag;
	}

	public boolean verifyNoDataToDisplayErrorMsg_Lightning(String environment, String mode, RelatedList gridSectionName,
			String expectedMsg, int timeOut) {
		WebElement ele = null;
		String msg;
		boolean flag = false;
		if (clickOnGridSection_Lightning(environment, mode, gridSectionName, timeOut)) {
			log(LogStatus.INFO, "clicked on " + gridSectionName.toString() + " link", YesNo.No);
			String xpath = "//h1[text()='" + gridSectionName
					+ "']/ancestor::div[contains(@class,'test-listViewManager')]//div[contains(@class,'emptyContentInner')]/p";
			ele = isDisplayed(
					driver, FindElement(driver, xpath, gridSectionName.toString() + " error message",
							action.SCROLLANDBOOLEAN, timeOut),
					"visibility", timeOut, gridSectionName.toString() + " error message");

			if (ele != null) {
				msg = ele.getText().trim();
				CommonLib.log(LogStatus.INFO, "Grid Message : " + msg, YesNo.No);
				if (expectedMsg.equals(msg)) {
					flag = true;
				}
			}
			// String
			// xpath="//h2//a[text()='"+gridSectionName.toString()+"']/ancestor::div//table//tr//td/span[contains(text(),'No
			// data')]";
			// List<WebElement> eleList = FindElements(driver, xpath, expectedMsg);
			// for (WebElement webElement : eleList) {
			// ele = isDisplayed(driver, webElement,"visibility", timeOut,
			// gridSectionName.toString()+ " error message");
			// if (ele!=null) {
			// return true;
			// }
			//
			// }
			// ele = isDisplayed(driver, FindElement(driver,xpath,
			// gridSectionName.toString()+ " error message",
			// action.SCROLLANDBOOLEAN,timeOut),"visibility", timeOut,
			// gridSectionName.toString()+ " error message");
			// msg = ele.getText().trim();
			// CommonLib.log(LogStatus.INFO, "Grid Message : "+msg, YesNo.No);
			// if (expectedMsg.equals(msg)) {
			// flag = true;
			// }
		} else {
			log(LogStatus.ERROR,
					"Not able to click on " + gridSectionName.toString() + " link so cannot verify error message",
					YesNo.Yes);
		}
		return flag;

	}

	public boolean verifyNoDataAtActivitiesSection(String environment, String mode, TabName tabName, int timeOut) {
		WebElement ele = getActivitiesGridNoRecordsToDisplay(environment, mode, timeOut);
		String msg;
		boolean flag = false;
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			msg = ele.getText();
			CommonLib.log(LogStatus.INFO, "Grid Message : " + msg, YesNo.No);
			if (BasePageBusinessLayer.noRecordsToDisplayMsg.equals(msg)) {
				flag = true;
			}
		} else {

			if (ele != null) {
				flag = true;
			}
		}
		return flag;

	}

	public boolean verifyActivitiesRelatedList(String environment, String mode, TabName tabName, String subject,
			String contactName, String relatedTo) {
		WebElement ele;
		String xpath;
		if (tabName.toString().equalsIgnoreCase(TabName.InstituitonsTab.toString())) {
			if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
				if (relatedTo == null) {
					xpath = "(//h3[text()='Activities']/ancestor::div[@class='bRelatedList']//div[@class='pbBody']//th//a[text()='"
							+ subject + "']/../following-sibling::td/a[text()='" + contactName
							+ "']/../following-sibling::td)[1]";
					ele = FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, 10);
					System.err.println(">>>>>ele:");
					if (ele != null) {
						String msg = ele.getText().trim();
						System.err.println(">>>>>msg: " + msg);
						if (msg.isEmpty()) {
							return true;
						} else {
							return false;
						}
					} else {
						return false;
					}
				} else {
					xpath = "//h3[text()='Activities']/ancestor::div[@class='bRelatedList']//div[@class='pbBody']//th//a[text()='"
							+ subject + "']/../following-sibling::td/a[text()='" + contactName
							+ "']/../following-sibling::td/a[text()='" + relatedTo + "']";
				}
				ele = FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, 10);
			} else {
				if (relatedTo == null) {
					xpath = "//table[@data-aura-class='uiVirtualDataTable']/tbody/tr/th/span/a[contains(text(),'"
							+ subject + "')]/../../following-sibling::td/span/a[text()='" + contactName
							+ "']/../../following-sibling::td";
					ele = FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, 10);
					System.err.println(">>>>>ele:");
					if (ele != null) {
						String msg = ele.getText().trim();
						System.err.println(">>>>>msg: " + msg);
						if (msg.isEmpty()) {
							return true;
						} else {
							return false;
						}
					} else {
						return false;
					}
				} else {
					xpath = "//table[@data-aura-class='uiVirtualDataTable']/tbody/tr/th/span/a[contains(text(),'"
							+ subject + "')]/../../following-sibling::td/span/a[text()='" + contactName
							+ "']/../../following-sibling::td/span/a[text()='" + relatedTo + "']";
				}
				ele = FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, 10);
			}
		} else {
			return false;
		}

		if (ele != null) {
			return true;
		} else {
			return false;
		}

	}

	public boolean verifyNoDataAtOpenActivitiesSection(String environment, String mode, TabName tabName, int timeOut) {
		WebElement ele = getOpenActivitiesNoRecordsToDisplay(environment, mode, timeOut);
		String msg;
		boolean flag = false;
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			msg = ele.getText();
			CommonLib.log(LogStatus.INFO, "Grid Message : " + msg, YesNo.No);
			if (BasePageBusinessLayer.noRecordsToDisplayMsg.equals(msg)) {
				flag = true;
			}
		} else {
			msg = ele.getText();
			CommonLib.log(LogStatus.INFO, "Grid Message : " + msg, YesNo.No);
			if (msg.contains(BasePageBusinessLayer.noNextActivityMsg1)
					|| msg.contains(BasePageBusinessLayer.noNextActivityMsg2)) {
				flag = true;
			}
		}
		return flag;

	}

	public boolean verifyNoDataAtActivityHistorySection(String environment, String mode, TabName tabName, int timeOut) {
		WebElement ele = getActivityHistoryNoRecordsToDisplay(environment, mode, timeOut);
		ThreadSleep(2000);
		String msg;
		boolean flag = false;
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			msg = ele.getText();
			CommonLib.log(LogStatus.INFO, "Grid Message : " + msg, YesNo.No);
			if (BasePageBusinessLayer.noRecordsToDisplayMsg.equals(msg)) {
				flag = true;
			}
		} else {
			msg = ele.getText();
			ThreadSleep(2000);
			CommonLib.log(LogStatus.INFO, "Grid Message : " + msg, YesNo.No);
			if (msg.contains(BasePageBusinessLayer.noPastActivityMsg1)
					&& msg.contains(BasePageBusinessLayer.noPastActivityMsg2)) {
				flag = true;
			}
		}
		return flag;

	}

	public boolean clickOnLookUpAndSelectValueFromLookUpWindow_Classic(String environment, String mode,
			LookUpIcon lookUpIcon, String searchText, String lookUpValues) {
		String[] values = lookUpValues.split(",");
		WebElement ele = null;
		String xpath = "";
		if (lookUpIcon.toString().equalsIgnoreCase(LookUpIcon.selectFundFromCreateFundraising.toString())) {
			xpath = "(//img[@title='" + lookUpIcon + "'])[2]";
		} else {
			xpath = "//img[@title='" + lookUpIcon + "']";
		}
		WebElement lookUpIconEle = FindElement(driver, xpath, lookUpIcon.toString(), action.SCROLLANDBOOLEAN, 10);
		if (click(driver, lookUpIconEle, "Look Up Icon", action.SCROLLANDBOOLEAN)) {

			String parentWindow = null;
			parentWindow = switchOnWindow(driver);
			if (parentWindow != null) {
				switchToFrame(driver, 20, getLookUpSearchFrame(10));
				if (sendKeys(driver, getLookUpSearchTextBox(30), searchText, "search text box",
						action.SCROLLANDBOOLEAN)) {
					if (click(driver, getLookUpSearchGoBtn(20), "go button", action.SCROLLANDBOOLEAN)) {
						switchToDefaultContent(driver);
						switchToFrame(driver, 20, getLookUpResultFrame(10));
						for (int i = 0; i < values.length; i++) {
							ele = isDisplayed(
									driver, FindElement(driver, "//a[text()='" + values[i] + "']",
											values[i] + " text value", action.SCROLLANDBOOLEAN, 20),
									"visibility", 20, values[i] + " text value");
							if (ele != null) {
								appLog.info(values[i] + " is visible in look up popup");

								if (i == values.length - 1) {
									ele = isDisplayed(driver,
											FindElement(driver, "//a[text()='" + values[0] + "']",
													values[0] + " text value", action.SCROLLANDBOOLEAN, 20),
											"visibility", 20, values[0] + " text value");
									if (!click(driver, ele, values[0] + " text value", action.SCROLLANDBOOLEAN)) {
										appLog.info("clicked on " + values[0] + " in lookup pop up");
										driver.switchTo().window(parentWindow);
										return true;
									}
								}

							} else {
								appLog.error(values[i] + " is not visible in look up popup");
								driver.close();
								driver.switchTo().window(parentWindow);
								return false;
							}
						}

					} else {
						appLog.error("Not able to click on go button so cannot select " + searchText);
						driver.close();
						driver.switchTo().window(parentWindow);
					}
				} else {
					appLog.error("Not able to pass value in search text box : " + searchText
							+ " so cannot select value " + searchText + " from look up");
					driver.close();
					driver.switchTo().window(parentWindow);
				}
			} else {
				appLog.error("No new window is open so cannot select value " + searchText + " from look up");
			}
		} else {
			appLog.error("Not Able to Click oN Look Up Icon");
		}
		return false;
	}

	public boolean verifyRelatedListWithCount(String environment, String mode, TabName tabName, RelatedList RelatedList,
			int count, int timeOut) {
		WebElement ele;
		WebElement relatedListWithCount;

		boolean flag = false;
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			ele = FindElement(driver,
					"//span[@class='listTitle'][text()='" + RelatedList + "']/span[text()='[" + count + "]']",
					RelatedList.toString() + " : " + count, action.SCROLLANDBOOLEAN, 10);
			relatedListWithCount = isDisplayed(driver, ele, "Visibility", timeOut,
					RelatedList.toString() + " : " + count, action.SCROLLANDBOOLEAN);

		} else {
			ele = FindElement(driver,
					"//span[text()='" + RelatedList + "']/following-sibling::span[@title='(" + count + ")']",
					RelatedList.toString() + " : " + count, action.SCROLLANDBOOLEAN, 10);
			relatedListWithCount = isDisplayed(driver, ele, "Visibility", timeOut,
					RelatedList.toString() + " : " + count, action.SCROLLANDBOOLEAN);
		}
		if (relatedListWithCount != null) {
			flag = true;
		}

		return flag;
	}

	public String getValueInCreateDrawdownsOrDistributionsPage(String field) {
		WebElement ele;
		String a = "";
		ele = isDisplayed(driver,
				FindElement(driver, "//td[text()='" + field + "']/following-sibling::td//div//span",
						"text in front of " + field, action.BOOLEAN, 30),
				"visibility", 30, "text in front of " + field);
		if (ele != null) {
			a = ele.getText().trim();
		} else {
			log(LogStatus.ERROR, "text is null", YesNo.No);
			return null;
		}
		return a;
	}

	public WebElement verifyCreatedItemOnPage(String header, String itemName) {
		WebElement ele;
		String xpath = "";
		String head = header.toString().replace("_", " ");
		ThreadSleep(3000);
		xpath = "(//*[contains(text(),'" + head + "')]/following-sibling::*//*[text()='" + itemName + "'])[1]";
		ele = FindElement(driver, xpath, "Header : " + itemName, action.BOOLEAN, 30);
		// ele = isDisplayed(driver, ele, "Visibility", 10, head+" : "+itemName);
		return ele;
	}

	public WebElement getViewListElement(String viewList) {
		String xpath = "";
		xpath = "//div[@class='listContent']//li/a/span[text()='" + viewList + "']";
		WebElement selectListView = FindElement(driver, xpath, "Select List View : " + viewList,
				action.SCROLLANDBOOLEAN, 5);
		return selectListView;
	}

	public WebElement getRelatedListItem(String relatedList, int i) {
		String xpath = "";
		xpath = "//h" + i;
		xpath = xpath + "//*[contains(text(),'" + relatedList + "')]";
		WebElement selectListView = FindElement(driver, xpath, relatedList, action.SCROLLANDBOOLEAN, 5);
		return selectListView;
	}

	public boolean createItem(String projectName, String navigationTab, String[][] labelWithValue, int timeOut) {
		boolean flag = false;
		if (clickOnTab(projectName, navigationTab)) {
			log(LogStatus.INFO, "Click on Tab : " + navigationTab, YesNo.No);

			String[] viewLists = { "All", "Recently Viewed" };
			click(driver, getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN);
			for (String viewList : viewLists) {
				WebElement ele = getViewListElement(viewList);
				;
				if (ele != null) {
					log(LogStatus.INFO, viewList + " is present on " + navigationTab, YesNo.No);
				} else {
					log(LogStatus.ERROR, viewList + " should be present on " + navigationTab, YesNo.Yes);
					sa.assertTrue(false, viewList + " should be present on " + navigationTab);
				}
			}

			if (clickUsingJavaScript(driver, getNewButton(projectName, 10), "new button")) {
				log(LogStatus.INFO, "Click on new button going to create ", YesNo.No);
				enteringValueForCreationPopUp(projectName, labelWithValue, action.BOOLEAN, timeOut);
				if (click(driver, getNavigationTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.ERROR, "Click on save Button ", YesNo.No);
					ThreadSleep(5000);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Not Able to Click on save Button ", YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on save Button ");
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on new button so cannot create", YesNo.Yes);
				sa.assertTrue(false, "Not Able to Click on new button so cannot create");

			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on Tab : " + navigationTab, YesNo.Yes);
			sa.assertTrue(false, "Not Able to Click on Tab : " + navigationTab);
		}
		return flag;
	}

	public void enteringValueForCreationPopUp(String projectName, String[][] navigationFieldWithValues, action action,
			int timeOut) {
		String navigationField;
		String navigationvalue;
		WebElement ele;
		for (String[] navigationFieldAndvalue : navigationFieldWithValues) {
			navigationField = navigationFieldAndvalue[0];
			navigationvalue = navigationFieldAndvalue[1];
			if (navigationField.equalsIgnoreCase(excelLabel.Role.toString())) {
				ele = getDropdownOnCreationPopUp(projectName, PageName.Financing, navigationField, action.BOOLEAN,
						timeOut);
				if (click(driver, ele, navigationField + " with value " + navigationvalue, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on " + navigationField, YesNo.No);
					if (SelectDropDownValue(projectName, PageName.Financing, navigationField, navigationvalue, action,
							timeOut)) {
						log(LogStatus.INFO, "Selected " + navigationvalue + " for " + navigationField, YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not Able to Select " + navigationvalue + " for " + navigationField,
								YesNo.Yes);
						sa.assertTrue(false, "Not Able to Select " + navigationvalue + " for " + navigationField);

					}

				} else {
					log(LogStatus.ERROR, "Not ABle to Click on " + navigationField, YesNo.Yes);
					sa.assertTrue(false, "Not ABle to Click on " + navigationField);

				}
			} else {
				ele = getCreationLabelField(projectName, navigationField, action, 20);
				if (sendKeys(driver, ele, navigationvalue, navigationField, action)) {
					log(LogStatus.INFO, "Able to enter " + navigationField, YesNo.No);

					if (navigationField.equalsIgnoreCase(excelLabel.Fundraising.toString())
							|| navigationField.equalsIgnoreCase(excelLabel.Contact.toString())) {
						ThreadSleep(10000);
						if (click(driver, getItemInList(projectName, navigationvalue, action.BOOLEAN, 20),
								navigationvalue + "   :  Parent Name", action.BOOLEAN)) {
							log(LogStatus.INFO, navigationvalue + " is available", YesNo.No);
						} else {
							log(LogStatus.ERROR, navigationvalue + " is not available", YesNo.Yes);
							sa.assertTrue(false, navigationvalue + " is not available");

						}
					}

				} else {
					log(LogStatus.ERROR, "Not Able to enter " + navigationField, YesNo.Yes);
					sa.assertTrue(false, "Not Able to enter " + navigationField);
				}
			}

		}

	}

	public WebElement getCreationLabelField(String projectName, String navigationField, action action, int timeOut) {
		navigationField = navigationField.replace("_", " ");
		String xpath = "//*[text()='" + navigationField + "']/following-sibling::div//input";
		WebElement ele = FindElement(driver, xpath, navigationField, action, timeOut);
		scrollDownThroughWebelement(driver, ele, navigationField);
		return isDisplayed(driver, ele, "Visibility", timeOut, navigationField);
	}

	public List<String> verifyDetailsTabFieldLabel(String projectName, TabName tabName, String recordName,
			String fieldNameList, int timeout) {
		List<String> result = new ArrayList<String>();
		List<WebElement> uiFieldNameList = new ArrayList<>();
		List<String> sectionList = new ArrayList<>();
		List<String> fieldList = new ArrayList<>();
		String[] sectionsAndFields = fieldNameList.split("<break>");
		String filesName;

		if (clickUsingJavaScript(driver, getRelatedTab(projectName, RelatedTab.Details.toString(), 10), "",
				action.BOOLEAN)) {

			AppListeners.appLog.info("clicked on details tab of tab: " + tabName);
		} else {
			AppListeners.appLog.info("Not able to clicked on details tab of tab: " + tabName);

		}
		for (String sectionWithField : sectionsAndFields) {

			String[] section = sectionWithField.split("<section>");
			sectionList.add(section[0]);
			fieldList.add(section[1]);

		}

		for (int k = 0; k < sectionList.size(); k++) {

			WebElement element = getHeaderSectionGrid(sectionList.get(k), null, timeout);
			String value = getAttribute(driver, element, "section", "aria-expanded");
			AppListeners.appLog.info("Header section expanded is:" + value);
			if (value.contains("false")) {
				AppListeners.appLog.info("Header section going to expanded  :" + value);
				for (int i = 0; i < 3; i++) {
					click(driver, element, sectionList.get(k), action.SCROLLANDBOOLEAN);
					value = getAttribute(driver, element, "section", "aria-expanded");
					AppListeners.appLog.info("Header section is successffully  expanded  :" + value);
					if (value.contains("true")) {
						break;
					}
				}

			}
			filesName = fieldList.get(k).toString();
			uiFieldNameList = getFieldLabelsOfSection(sectionList.get(k), action.SCROLLANDBOOLEAN, timeout);

			String[] fileName = filesName.split("<f>");

			int countFiles = 0;
			try {
				if (fileName.length != 0) {
					if (!uiFieldNameList.isEmpty()) {
						for (int i = 0; i < fileName.length; i++) {
							for (int j = 0; j < uiFieldNameList.size(); j++) {
								scrollDownThroughWebelement(driver, uiFieldNameList.get(j), "");
								ThreadSleep(500);
								AppListeners.appLog.info("Comparing:>>" + fileName[i] + ">>With:>>"
										+ uiFieldNameList.get(j).getText().trim());

								if (fileName[i].equalsIgnoreCase(uiFieldNameList.get(j).getText().trim())) {
									AppListeners.appLog.info(fileName[i] + " is matched successfully");
									countFiles++;
									break;
								} else if (j == uiFieldNameList.size() - 1) {
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

		}

		return result;
	}

	public boolean openObjectFromAppLauchner(String objectName, int timeOut) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		boolean flag = false;
		if (click(driver, lp.getAppLuncherXpath(timeOut), "App launcher icon", action.BOOLEAN)) {

			AppListeners.appLog.info(" click on app launcher icon");
			ThreadSleep(1000);
			if (sendKeys(driver, lp.getSearchAppTextBoxInAppLuncher(timeOut), objectName, "Search box in app launcher",
					action.BOOLEAN)) {
				AppListeners.appLog.info("entered value in app launcher search box value:" + objectName);
				ThreadSleep(3000);
				if (clickUsingJavaScript(driver, lp.getAppNameLabelTextInAppLuncher(objectName, timeOut),
						objectName + ":app label in app launcher", action.BOOLEAN)) {
					AppListeners.appLog.info("click on label in app launcher" + objectName);

					ThreadSleep(5000);
					String pageName = getPageHeaderName(timeOut).getText();
					if (pageName.equals(objectName)) {
						AppListeners.appLog.info(objectName + " page successfully loaded");
						flag = true;

					} else {

						AppListeners.appLog.info(objectName + " page not loaded");
						return false;
					}

				} else {

					AppListeners.appLog.info("Not able tp click on label in app launcher" + objectName);
					return false;
				}
			} else {

				AppListeners.appLog.info("Not able to entered value in app launcher search box value:" + objectName);
				return false;
			}

		} else {

			AppListeners.appLog.info("Not able to click on app launcher icon");
			return false;
		}
		return flag;
	}

	public List<String> verifyObjectListViewAndFilterCondition(String projectName, String mode, String objectName,
			String selectListLink, String filterList, int timeOut) {
		List<String> result = new ArrayList<>();

		if (openObjectFromAppLauchner(objectName, timeOut)) {
			AppListeners.appLog.info("Object page successfully open:" + objectName);

			if (click(driver, getSelectListIcon(timeOut), "selet list icon", action.BOOLEAN)) {

				AppListeners.appLog.info("Click on select list icon on:" + objectName);
				ThreadSleep(2000);

				if (getSelectListIcon(timeOut).getAttribute("aria-expanded").contains("true")) {

					AppListeners.appLog.info("after clicking select list icon is in expand mode on:" + objectName);

					ThreadSleep(3000);
					List<WebElement> lst = getAllLinkOfSelectListIconOption(mode, objectName, 30);
					if (compareMultipleList(driver, selectListLink, lst).isEmpty()) {
						log(LogStatus.PASS, "", YesNo.No);
						AppListeners.appLog.info("All link of select list icon  is verified on:" + objectName);

						click(driver, getSelectListIcon(timeOut), "selet list icon", action.BOOLEAN);
						ThreadSleep(2000);
						if (getSelectListIcon(timeOut).getAttribute("aria-expanded").contains("false")) {
							AppListeners.appLog.info(
									"after verifying list view and clicking on select list icon is not in expand mode on:"
											+ objectName);

						} else {
							AppListeners.appLog.info(
									"after verifying list view and clicking on select list icon should not be in expand mode on:"
											+ objectName);

						}

					} else {

						AppListeners.appLog.info("All link of select list icon is not verified on " + objectName);
						result.add("All link of select list icon  is not verified on " + objectName);
					}

				} else {

					AppListeners.appLog
							.info("after clicking select list icon is should be in expand mode on:" + objectName);
					result.add("after clicking select list icon is should be in expand mode on:" + objectName);
				}

			} else {

				AppListeners.appLog.info("Not able to click on select list icon on:" + objectName);
				result.add("Not able to click on select list icon on:" + objectName);
			}

			String[] listView = filterList.split("<break>");

			List<String> filterName = new ArrayList<>();

			for (int i = 0; i <= listView.length; i++) {

				String[] list = listView[i].split("<filter>");

				String listViewValue = list[0];
				String[] filtersValue = list[1].split("#");

				if (click(driver, getSelectListIcon(timeOut), "select list icon", action.BOOLEAN)) {

					AppListeners.appLog.info("Click on select list icon on:" + objectName);
					ThreadSleep(2000);

					if (getSelectListIcon(timeOut).getAttribute("aria-expanded").contains("true")) {

						AppListeners.appLog.info("after clicking select list icon is in expand mode on:" + objectName);

						if (click(driver, getSelectListLabelLink(listViewValue, timeOut), listViewValue + ": label",
								action.BOOLEAN)) {
							AppListeners.appLog.info("click on select list label:" + listViewValue);

							if (filterList != null || !filterList.isEmpty() | !filterList.equals("")) {
								AppListeners.appLog.info("going to verify filters");

								if (getFilterButton(timeOut).getAttribute("aria-pressed").contains("false")) {

									if (click(driver, getFilterButton(timeOut), "filter button", action.BOOLEAN)) {
										AppListeners.appLog.info("click on fiter button on" + objectName);
									} else {

										AppListeners.appLog.info("Not able to click on fiter button on" + objectName);
										result.add("Not able to click on fiter button on" + objectName);
									}

								} else {

									AppListeners.appLog.info("filter panel is already open");

								}

								List<WebElement> lst = getListOfFilterPanelValue(timeOut);

								for (WebElement element : lst) {

									String value = element.getText().replaceAll("\\s+", "");
									AppListeners.appLog.info("fetch vlaue is :" + value);
									filterName.add(value);

								}
								System.out.println("Filter Value list:" + filterName);

								for (int j = 0; j < filterName.size(); j++) {

									if (filterName.get(j).equalsIgnoreCase(filtersValue[j].replaceAll("\\s+", ""))) {
										AppListeners.appLog.info(filterName.get(j) + ":Filter value is verified with:"
												+ filtersValue[j]);

									} else {
										AppListeners.appLog.info(filterName.get(j)
												+ ": Filter value is not verified with: " + filtersValue[j]);
										result.add(filterName.get(j) + "Filter value is not verified with:"
												+ filtersValue[j]);

									}
								}
								filterName.clear();
								System.out.println("Filter Value list:" + filterName);

							} else {
								AppListeners.appLog
										.info("Filter value is not present in filter panel size:" + filterName.size());
								result.add("Filter value is not present in filter panel size:" + filterName.size());

							}

						} else {

							AppListeners.appLog.info("Not able to click on select list label:" + listViewValue);
							result.add("Not able to click on select list label:" + listViewValue);
						}

					} else {

						AppListeners.appLog
								.info("after clicking select list icon is should be in expand mode on:" + objectName);
						result.add("after clicking select list icon is should be in expand mode on:" + objectName);
					}

				} else {

					AppListeners.appLog.info("Not able to click on select list icon on:" + objectName);
					result.add("Not able to click on select list icon on:" + objectName);
				}

			}

		} else {

			AppListeners.appLog.info("Unable to open Object page :" + objectName);
			result.add("Unable to open Object page :" + objectName);

		}

		return result;
	}

	public boolean createListView(String projectName, String listViewName, String listAccessibility, int timeOut) {
		String xpath = "";
		refresh(driver);
		ThreadSleep(2000);
		if (click(driver, getlistViewControlsButton(projectName, timeOut), "list view", action.BOOLEAN)) {
			log(LogStatus.INFO, "successfully click on list view", YesNo.No);
			if (click(driver, getnewButtonListView(projectName, timeOut), "new ", action.BOOLEAN)) {
				log(LogStatus.INFO, "successfully click on new buton", YesNo.No);
				if (sendKeys(driver, getlistNameTextBox(projectName, "List Name", timeOut), listViewName, "list name",
						action.SCROLLANDBOOLEAN)) {

					xpath = "//span[contains(text(),'" + listAccessibility.trim() + "')]/../preceding-sibling::input";
					WebElement ele = FindElement(driver, xpath, "Found : " + listAccessibility, action.SCROLLANDBOOLEAN,
							5);

					if (click(driver, ele, listAccessibility, action.BOOLEAN)) {
						log(LogStatus.INFO, "successfully click on " + listAccessibility, YesNo.No);
						if (click(driver, getlistViewSaveButton(projectName, timeOut), "save", action.BOOLEAN)) {
							log(LogStatus.INFO, "successfully click on save buton", YesNo.No);
							return true;
						} else {
							log(LogStatus.ERROR, "list view save button is not clickable", YesNo.No);
						}
					} else {
						log(LogStatus.ERROR, "all users radio button is not clickable", YesNo.No);
					}

				} else {
					log(LogStatus.ERROR, "list name textbox is not visible", YesNo.No);
				}
			} else {
				log(LogStatus.ERROR, "new button is not clickable", YesNo.No);
			}
		} else {
			log(LogStatus.ERROR, "list view controls button is not clickable", YesNo.No);
		}
		return false;
	}

	public boolean addAutomationAllListView(String projectName, String[] listViewDataRowWise, int timeOut) {

		String viewList = listViewDataRowWise[2], xpath = "";
		if (click(driver, getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(3000);
			xpath = "//div[@class='listContent']//li/a/span[text()='" + viewList + "']";
			WebElement selectListView = FindElement(driver, xpath, "Select List View : " + viewList,
					action.SCROLLANDBOOLEAN, 5);
			ThreadSleep(3000);
			if (selectListView != null) {
				log(LogStatus.ERROR, "List View: " + viewList + " is already present", YesNo.No);
				if (click(driver, selectListView, viewList, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on List View: " + viewList, YesNo.No);

					return true;
				} else {
					log(LogStatus.INFO, "Not Able to Click on List View: " + viewList, YesNo.No);
				}
			} else {
				log(LogStatus.INFO, "List View: " + viewList + " is not already present.. now creating", YesNo.No);

			}
		} else {
			log(LogStatus.ERROR, "list dropdown is not clickable, so cannot check presence of " + viewList, YesNo.Yes);

		}

		if (createListView(projectName, listViewDataRowWise[2], listViewDataRowWise[3], timeOut)) {
			if (changeFilterInListView(projectName, listViewDataRowWise, timeOut)) {
				return true;
			} else {
				log(LogStatus.ERROR, "Could not change filter for List View: " + viewList, YesNo.Yes);
				return false;
			}
		} else {
			log(LogStatus.ERROR, "Could not create new List View: " + viewList, YesNo.Yes);
		}
		return false;

	}

	/**
	 * @author Ankur Huria
	 * @param projectName
	 * @param listViewName
	 * @param timeOut
	 */

	public boolean changeFilterInListView(String projectName, String[] listViewDataRowWise, int timeOut) {

		if (click(driver, getfilterByOwnerBtn(projectName, 10), "filter section", action.BOOLEAN)) {
			log(LogStatus.INFO, "successfully click on filter section", YesNo.No);
			if (click(driver, filteryOwnerRadioButton(listViewDataRowWise[4], timeOut), "all filters",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "successfully click on all radio button", YesNo.No);
				ThreadSleep(2000);
				if (click(driver, getdoneButtonListView(projectName, timeOut), "done", action.BOOLEAN)) {
					log(LogStatus.INFO, "successfully click on done buton", YesNo.No);

					String[] filters = listViewDataRowWise[5].split("<Break>");
					String[] operators = listViewDataRowWise[6].split("<Break>");
					String[] filtervalues = listViewDataRowWise[7].split("<Break>");
					String[] textBoxType = listViewDataRowWise[8].split("<Break>");

					int i = 0;
					for (String filter : filters) {

						if (click(driver, getaddFilterBtn(projectName, timeOut), "Add Filter Button", action.BOOLEAN)) {
							log(LogStatus.INFO, "successfully click on Add Filter buton", YesNo.No);
							String xpathgetfilterFielddropdownlist = "//label[text()='Field']/parent::lightning-combobox//span[@class='slds-truncate']";
							if (dropDownHandle(driver, getfilterFielddropdown(projectName, timeOut),
									xpathgetfilterFielddropdownlist, "Field filter", filter)) {
								log(LogStatus.INFO, "successfully Select the Field", YesNo.No);
								String xpathgetfilterOperatordropdownlist = "//label[text()='Operator']/parent::lightning-combobox//span[@class='slds-truncate']";
								if (dropDownHandle(driver, getFilterOperatordropdown(projectName, timeOut),
										xpathgetfilterOperatordropdownlist, "Operator filter", operators[i])) {
									log(LogStatus.INFO, "successfully Select the Operator", YesNo.No);

									if (textBoxType[i].equalsIgnoreCase("Textbox")) {
										if (sendKeys(driver, getfilterValuefield(projectName, timeOut), filtervalues[i],
												"value", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "value has been entered", YesNo.No);

										} else {
											log(LogStatus.ERROR, "Value is not Entered", YesNo.No);
											sa.assertTrue(false, "Value is not Entered");

										}

									}

									else if (textBoxType[i].equalsIgnoreCase("dropdown")) {
										String xpathgetfilterValueDropdownlist = "//div[@role='menu']//li/a";
										if (dropDownHandle(driver, getfilterValueDropDown(projectName, timeOut),
												xpathgetfilterValueDropdownlist, "Value filter list",
												filtervalues[i])) {
											log(LogStatus.INFO, "successfully Select the Operator", YesNo.No);
										} else {
											log(LogStatus.ERROR, "Value is not Selected", YesNo.No);
											sa.assertTrue(false, "Value is not Selected");

										}

									}

									else {
										log(LogStatus.ERROR,
												"TextBox Type not Mention Properly in Test data: " + textBoxType[i],
												YesNo.No);
										sa.assertTrue(false,
												"TextBox Type not Mention Properly in Test data: " + textBoxType[i]);
									}

									if (click(driver, getfilterDoneBtn(projectName, timeOut), "Filter Done Button",
											action.BOOLEAN)) {
										log(LogStatus.INFO, "successfully click on Done buton", YesNo.No);

									} else {
										log(LogStatus.ERROR, "done button is not clickable", YesNo.No);
										sa.assertTrue(false, "done button is not clickable");
									}
								}

								else {
									log(LogStatus.ERROR, "Operator Filter is not Selected", YesNo.No);
									sa.assertTrue(false, "Operator Filter is not Selected");
								}

							} else {
								log(LogStatus.ERROR, "Field Filter is not Selected", YesNo.No);
								sa.assertTrue(false, "Field Filter is not Selected");
							}
						} else {
							log(LogStatus.ERROR, "Add Filter button is not clickable", YesNo.No);
							sa.assertTrue(false, "Add Filter button is not clickable");
						}

						i++;
					}

				}

				else {
					log(LogStatus.ERROR, "Filter Done button is not clicked", YesNo.No);
					sa.assertTrue(false, "Filter Done button is not clicked");
				}

			}

			else {
				log(LogStatus.ERROR, "all checkbox is not clickable", YesNo.No);
				sa.assertTrue(false, "all checkbox is not clickable");
			}
		} else {
			log(LogStatus.ERROR, "list filter section is not clickable", YesNo.No);
			sa.assertTrue(false, "list filter section is not clickable");
		}

		if (click(driver, getfilterSave(projectName, timeOut), "save", action.BOOLEAN)) {
			log(LogStatus.INFO, "successfully click on save buton", YesNo.No);
			WebElement ele = getCreatedConfirmationMsg(projectName, 15);
			if (ele != null) {
				String actualValue = ele.getText().trim();
				String expectedValue = BasePageErrorMessage.listViewUpdated;
				if (actualValue.contains(expectedValue)) {
					log(LogStatus.INFO, expectedValue + " matched FOR Confirmation Msg", YesNo.No);
					return true;
				} else {
					log(LogStatus.ERROR, "Actual : " + actualValue + " Expected : " + expectedValue
							+ " not matched FOR Confirmation Msg", YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Actual : " + actualValue + " Expected : " + expectedValue
							+ " not matched FOR Confirmation Msg");
				}
			} else {
				sa.assertTrue(false, "Created Task Msg Ele not Found");
				log(LogStatus.SKIP, "Created Task Msg Ele not Found", YesNo.Yes);
				sa.assertTrue(false, "Created Task Msg Ele not Found");

			}
		} else {
			log(LogStatus.ERROR, "save button is not clickable", YesNo.No);
			sa.assertTrue(false, "save button is not clickable");
		}

		return false;
	}

	public boolean openAppFromAppLauchner(String objectName, int timeOut) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		boolean flag = false;
		if (click(driver, lp.getAppLuncherXpath(timeOut), "App launcher icon", action.BOOLEAN)) {

			AppListeners.appLog.info(" click on app launcher icon");
			ThreadSleep(3000);
			if (sendKeys(driver, lp.getSearchAppTextBoxInAppLuncher(timeOut), objectName, "Search box in app launcher",
					action.BOOLEAN)) {
				AppListeners.appLog.info("entered value in app launcher search box value:" + objectName);
				ThreadSleep(3000);
				if (clickUsingJavaScript(driver, lp.getAppNameLabelTextInAppLuncher(objectName, timeOut),
						objectName + ":app label in app launcher", action.BOOLEAN)) {
					AppListeners.appLog.info("click on label in app launcher" + objectName);

					ThreadSleep(7000);

					String fullXpath = "//div[contains(@class,'header')]//*[text()='" + objectName + "']";

					WebElement ele = FindElement(driver, fullXpath, " App Name", action.BOOLEAN, timeOut);

					if (ele != null) {
						AppListeners.appLog.info(objectName + " page successfully loaded");
						flag = true;

					} else {

						AppListeners.appLog.info(objectName + " page not loaded");
						return false;
					}

				} else {

					AppListeners.appLog.info("Not able tp click on label in app launcher" + objectName);
					return false;
				}
			} else {

				AppListeners.appLog.info("Not able to entered value in app launcher search box value:" + objectName);
				return false;
			}

		} else {

			AppListeners.appLog.info("Not able to click on app launcher icon");
			return false;
		}
		return flag;
	}

	public boolean openAppFromAppLauchner(int timeOut, String objectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		boolean flag = false;
		if (click(driver, lp.getAppLuncherXpath(timeOut), "App launcher icon", action.BOOLEAN)) {

			AppListeners.appLog.info(" click on app launcher icon");
			ThreadSleep(1000);
			if (sendKeys(driver, lp.getSearchAppTextBoxInAppLuncher(timeOut), objectName, "Search box in app launcher",
					action.BOOLEAN)) {
				AppListeners.appLog.info("entered value in app launcher search box value:" + objectName);
				ThreadSleep(3000);
				if (clickUsingJavaScript(driver, lp.getAppNameLabelTextInAppLuncher(objectName, timeOut),
						objectName + ":app label in app launcher", action.BOOLEAN)) {
					AppListeners.appLog.info("click on label in app launcher" + objectName);

					ThreadSleep(7000);

					String fullXpath = "//span[text()='" + objectName + "']";
					WebElement ele = FindElement(driver, fullXpath, " App Name", action.BOOLEAN, timeOut);

					String pageName = ele.getText();
					if (pageName.equals(objectName)) {
						AppListeners.appLog.info(objectName + " page successfully loaded");
						flag = true;

					} else {

						AppListeners.appLog.info(objectName + " page not loaded");
						return false;
					}

				} else {

					AppListeners.appLog.info("Not able to click on label in app launcher" + objectName);
					return false;
				}
			} else {

				AppListeners.appLog.info("Not able to entered value in app launcher search box value:" + objectName);
				return false;
			}

		} else {

			AppListeners.appLog.info("Not able to click on app launcher icon");
			return false;
		}
		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param projectName
	 * @param listViewName
	 * @param timeOut
	 */
	public boolean deleteListView(String projectName, String listViewName, int timeOut) {
		boolean flag = false;
		String viewList = listViewName, xpath = "";
		if (click(driver, getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(3000);
			xpath = "//div[@class='listContent']//li/a/span[text()='" + viewList + "']";
			WebElement selectListView = FindElement(driver, xpath, "Select List View : " + viewList,
					action.SCROLLANDBOOLEAN, 10);
			ThreadSleep(3000);
			if (selectListView != null) {
				log(LogStatus.INFO, "List View already Present, Now Deleting List View: " + viewList, YesNo.No);
				if (click(driver, selectListView, "List View: " + viewList, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Selected the List View: " + viewList, YesNo.No);
					CommonLib.ThreadSleep(4000);
					if (click(driver, getlistViewControlsButton(projectName, timeOut), "list view", action.BOOLEAN)) {
						log(LogStatus.INFO, "successfully click on list view setting Icon", YesNo.No);

						if (click(driver, getdeleteButtonListView(projectName, timeOut), "Delete Button",
								action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Delete Button", YesNo.No);

							if (click(driver, getdeleteConfirmButtonListView(projectName, timeOut),
									"Delete Confirm Button", action.BOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Delete Confirm Button", YesNo.No);
								CommonLib.ThreadSleep(3000);
								if (click(driver, getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Select List Icon", YesNo.No);
									ThreadSleep(3000);
									WebElement selectListViewAfterDelete = FindElement(driver, xpath,
											"Select List View : " + viewList, action.SCROLLANDBOOLEAN, 10);
									if (selectListViewAfterDelete != null) {
										log(LogStatus.FAIL,
												"List View:" + viewList
														+ " again present after delete, So Test Case is going to fail ",
												YesNo.No);

									} else {
										log(LogStatus.PASS,
												"List View:" + viewList
														+ " not present after delete, So Confirmed delete of List View",
												YesNo.No);
										flag = true;
									}

								} else {
									log(LogStatus.ERROR, "Not Able to click on Select List Icon", YesNo.No);
								}
							}

							else {
								log(LogStatus.ERROR, "Not Able to click on Delete Confirm Button", YesNo.No);
							}

						} else {
							log(LogStatus.ERROR, "Not Able to click on Delete Button", YesNo.No);
						}
					} else {
						log(LogStatus.ERROR, "list view Setting Icon is not clickable", YesNo.No);
					}
				}

				else {
					log(LogStatus.ERROR, "Not Able to Select the List View: " + viewList, YesNo.No);

				}

			} else {
				log(LogStatus.FAIL, "List View not already Present, So cannot Delete List View: " + viewList, YesNo.No);
				sa.assertTrue(false, "List View not already Present, So cannot Delete List View: " + viewList);

			}
		} else {
			log(LogStatus.ERROR, "list dropdown is not clickable, so cannot check presence of Automation All",
					YesNo.Yes);

		}

		return flag;

	}

	/**
	 * @author Ankur Huria
	 * @param projectName
	 * @param listViewName
	 * @param timeOut
	 * @param record
	 */
	public boolean deleteListViewRecord(String projectName, String listViewName, int timeOut, String record) {
		boolean flag = false;
		String viewList = listViewName, xpath = "";
		if (click(driver, getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(3000);
			xpath = "//div[@class='listContent']//li/a/span[text()='" + viewList + "']";
			WebElement selectListView = FindElement(driver, xpath, "Select List View : " + viewList,
					action.SCROLLANDBOOLEAN, 10);
			ThreadSleep(3000);
			if (selectListView != null) {
				log(LogStatus.INFO, "List View already Present, List View: " + viewList, YesNo.No);
				if (click(driver, selectListView, "List View: " + viewList, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Selected the List View: " + viewList, YesNo.No);
					if (sendKeys(driver, listSearchBox(timeOut), record, "search text box", action.SCROLLANDBOOLEAN)) {
						appLog.info("Passed Value in Search Text box: " + record);
						log(LogStatus.INFO, "Passed Value in Search Text box: " + record, YesNo.No);
						ThreadSleep(2000);
						if (getFundNameElement(record, 20) != null) {
							log(LogStatus.INFO, "Record Found " + record, YesNo.No);
							if (click(driver, getSelectEditOfFundName(record, 20), "Edit Button: " + record,
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Edit of the record " + record, YesNo.No);
								if (click(driver, deleteRecordButton(timeOut), "Delete Button", action.BOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Delete button", YesNo.No);

									if (click(driver, getdeleteConfirmButtonListView(projectName, timeOut),
											"Delete Confirm Button", action.BOOLEAN)) {
										log(LogStatus.INFO, "Clicked on Delete Confirm Button", YesNo.No);
										CommonLib.ThreadSleep(3000);
										flag = true;
									}

									else {
										log(LogStatus.ERROR, "Not Able to click on Delete Confirm Button", YesNo.No);
									}

								} else {
									log(LogStatus.ERROR, "Not Able to Click on Delete button", YesNo.No);
								}
							}

							else {
								log(LogStatus.ERROR, "Not able to Click on Edit Record of: " + record, YesNo.No);

							}

						}

						else {
							log(LogStatus.ERROR, "Record not Found: " + record, YesNo.No);

						}
					}

					else {
						log(LogStatus.ERROR, "Passed Value in Search Text box: " + record, YesNo.No);

					}
				} else {
					log(LogStatus.ERROR, "Not Able to Select the List View: " + viewList, YesNo.No);

				}

			} else {
				log(LogStatus.FAIL, "List View not already Present, List View: " + viewList, YesNo.No);
				sa.assertTrue(false, "List View not already Present,  List View: " + viewList);

			}
		} else {
			log(LogStatus.ERROR, "list dropdown is not clickable, so cannot check presence of Automation All",
					YesNo.Yes);

		}

		return flag;

	}

	public boolean clickOnAlreadyCreated(String environment, String mode, TabName tabName, String alreadyCreated,
			int timeout) {

		String viewList = null;
		switch (tabName) {
		case ContactTab:
			viewList = "All Contacts";
			break;
		case InstituitonsTab:
			viewList = "All Firms";
			break;
		case CompaniesTab:
			viewList = "All Companies";
			break;
		case LimitedPartner:
			viewList = "All Limited Partners";
			break;
		case FundraisingsTab:
			viewList = "All";
			break;
		case FundsTab:
			viewList = "All";
			break;
		case CommitmentsTab:
			viewList = "All";
			break;
		case PartnershipsTab:
			viewList = "All";
			break;
		case FundDistributions:
			viewList = "All";
			break;
		case InvestorDistributions:
			viewList = "All";
			break;
		case MarketingInitiatives:
			viewList = "All";
			break;
		case MarketingProspects:
			viewList = "Marketing Prospects";
			break;
		case Pipelines:
			viewList = "All";
			break;
		case CapitalCalls:
			viewList = "All";
			break;
		case FundDrawdowns:
			viewList = "All";
			break;
		case FundraisingContacts:
			viewList = "All";
			break;
		default:
			return false;
		}
		System.err.println("Passed switch statement");
		WebElement ele, selectListView;
		ele = null;
		if (click(driver, getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(3000);
			selectListView = FindElement(driver, "//div[@class='listContent']//li/a/span[text()='" + viewList + "']",
					"Select List View", action.SCROLLANDBOOLEAN, 30);
			if (click(driver, selectListView, "select List View", action.SCROLLANDBOOLEAN)) {
				ThreadSleep(3000);
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					refresh(driver);
					ThreadSleep(5000);
				}
				if (sendKeys(driver, getSearchIcon_Lighting(20), alreadyCreated + "\n", "Search Icon Text",
						action.SCROLLANDBOOLEAN)) {
					ThreadSleep(5000);
					ele = FindElement(driver,
							"//table[@data-aura-class='uiVirtualDataTable']//tbody//tr//th//span//a[text()='"
									+ alreadyCreated + "']",
							alreadyCreated, action.BOOLEAN, 30);
					ThreadSleep(2000);
					if (click(driver, ele, alreadyCreated, action.BOOLEAN)) {
						ThreadSleep(3000);
						return true;
					} else {
						appLog.error("Not able to Click on Already Created : " + alreadyCreated);
					}
				} else {
					appLog.error("Not able to enter value on Search Box");
				}
			} else {
				appLog.error("Not able to select on Select View List");
			}
		} else {
			appLog.error("Not able to click on Select List Icon");
		}
		return false;
	}

	public boolean addListView(String projectName, String[] listViewDataRowWise, int timeOut) {

		boolean flag = false;
		String viewList = listViewDataRowWise[2], xpath = "";

		if (getSelectListIcon(10) == null) {
			log(LogStatus.INFO, "not found " + viewList + ".. now creating", YesNo.No);
			if (createListView(projectName, listViewDataRowWise[2], listViewDataRowWise[3], timeOut)) {
				log(LogStatus.INFO, viewList + " has been created", YesNo.No);
				if (CreateOrChangeFilterInListView(projectName, listViewDataRowWise, timeOut)) {
					flag = true;
				} else {
					log(LogStatus.ERROR, "could not change filter to all", YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "could not create new list", YesNo.Yes);
			}
		} else {

			if (click(driver, getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
				ThreadSleep(3000);
				xpath = "//div[@class='listContent']//li/a/span[text()='" + viewList + "']";
				WebElement selectListView = FindElement(driver, xpath, "Select List View : " + viewList,
						action.SCROLLANDBOOLEAN, 5);
				ThreadSleep(3000);
				if (selectListView != null) {
					log(LogStatus.INFO, "" + viewList + " is already present", YesNo.No);

					if (click(driver, selectListView, "select List View", action.SCROLLANDBOOLEAN)) {
						ThreadSleep(3000);
						log(LogStatus.INFO, "" + viewList + " has been clicked", YesNo.No);

						if (click(driver, getshowFilter(50), "Show filter", action.SCROLLANDBOOLEAN)) {
							ThreadSleep(3000);
							log(LogStatus.INFO, "Clicked on the show filter", YesNo.No);

							if (click(driver, getremoveAll(50), "Show filter", action.SCROLLANDBOOLEAN)) {
								ThreadSleep(3000);
								log(LogStatus.INFO, "Clicked on the remove all button", YesNo.No);

								if (CreateOrChangeFilterInListView(projectName, listViewDataRowWise, timeOut)) {
									flag = true;
								} else {
									log(LogStatus.ERROR, "could not change filter to " + viewList + "", YesNo.Yes);
								}
							} else {
								log(LogStatus.ERROR, "Could not click on remove all button", YesNo.No);
							}
						} else {
							log(LogStatus.ERROR, "Could not click on show filter", YesNo.No);
						}

					} else {
						log(LogStatus.ERROR, "Could not click on " + viewList + "", YesNo.No);
					}

				} else {
					log(LogStatus.INFO, "not found " + viewList + ".. now creating", YesNo.No);
					if (createListView(projectName, listViewDataRowWise[2], listViewDataRowWise[3], timeOut)) {
						log(LogStatus.INFO, viewList + " has been created", YesNo.No);
						if (CreateOrChangeFilterInListView(projectName, listViewDataRowWise, timeOut)) {
							flag = true;
						} else {
							log(LogStatus.ERROR, "could not change filter to all", YesNo.Yes);
						}
					} else {
						log(LogStatus.ERROR, "could not create new list", YesNo.Yes);
					}
				}
			} else {
				log(LogStatus.ERROR, "list dropdown is not clickable, so cannot check presence of Automation All",
						YesNo.Yes);

			}

		}

		return flag;

	}

	public boolean CreateOrChangeFilterInListView(String projectName, String[] listViewDataRowWise, int timeOut) {
		WebElement ele;
		boolean flag = false;
		String xPath = "";
		String[] filters = listViewDataRowWise[5].split("<Break>");
		String[] operators = listViewDataRowWise[6].split("<Break>");
		String[] filtervalues = listViewDataRowWise[7].split("<Break>");
		if (click(driver, getfilterByOwnerBtn(projectName, 10), "filter section", action.BOOLEAN)) {
			log(LogStatus.INFO, "successfully click on filter section", YesNo.No);
			if (click(driver, filteryOwnerRadioButton(listViewDataRowWise[4], timeOut),
					"Filter: " + listViewDataRowWise[4], action.BOOLEAN)) {
				log(LogStatus.INFO, "successfully click on all radio button", YesNo.No);
				if (click(driver, getdoneButtonListView(projectName, timeOut), "done", action.BOOLEAN)) {
					log(LogStatus.INFO, "successfully click on done buton", YesNo.No);

					for (int i = 0; i < filters.length; i++) {

						if (click(driver, getaddFilterBtn(projectName, timeOut), "Add Filter Button", action.BOOLEAN)) {
							log(LogStatus.INFO, "successfully click on Add Filter buton", YesNo.No);

							if (getSelectedOptionOfDropDown(driver, getfilterFielddropdown(projectName, timeOut),
									getfilterFielddropdownlist(projectName, timeOut), "Field filter", filters[i])) {
								log(LogStatus.INFO, "successfully Select the Field", YesNo.No);

								if (getSelectedOptionOfDropDown(driver, getFilterOperatordropdown(projectName, timeOut),
										getfilterOperatordropdownlist(projectName, timeOut), "Operator filter",
										operators[i])) {
									log(LogStatus.INFO, "successfully Select the Operator", YesNo.No);

									if (filters[i].trim().equalsIgnoreCase("Vintage Year")
											|| filters[i].trim().equalsIgnoreCase("1st Closing Date")
											|| filters[i].equals("Fund Name") || filters[i].equals("Record Name")) {
										if (sendKeys(driver, getfilterValuefield(projectName, timeOut), filtervalues[i],
												"value", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "value has been entered", YesNo.No);

										} else {
											log(LogStatus.ERROR, "Value is not Entered", YesNo.No);

										}

									}

									else if (filters[i].equals("Fund Type") || filters[i].equals("Investment Category")
											|| filters[i].equals("Stage")) {
										if (getSelectedOptionOfDropDown(driver,
												getfilterValueDropDown(projectName, timeOut),
												getfilterValueDropdownlist(projectName, timeOut), "Value filter list",
												filtervalues[i])) {
											log(LogStatus.INFO, "successfully Select the Operator", YesNo.No);
										} else {
											log(LogStatus.ERROR, "Value is not Selected", YesNo.No);
										}

									}

									if (click(driver, getfilterDoneBtn(projectName, timeOut), "Filter Done Button",
											action.BOOLEAN)) {
										log(LogStatus.INFO, "successfully click on Done buton", YesNo.No);

										CommonLib.ThreadSleep(3000);
										xPath = "//div[contains(@class,'SecondaryDisplayManager')]//button[text()='Save']";
										ele = FindElement(driver, xPath, "", action.BOOLEAN, 20);
										if (ele == null) {
											log(LogStatus.INFO, "Filter is already created", YesNo.No);
											flag = true;
										} else {
											if (click(driver, getfilterSave(projectName, timeOut), "save",
													action.BOOLEAN)) {
												log(LogStatus.INFO, "successfully click on save buton", YesNo.No);

												String xPath1 = "//div[@class=\"fieldLabel\" and text()='"
														+ filters[i].toString() + "']";
												ele = FindElement(driver, xPath1, filters[i] + " value", action.BOOLEAN,
														50);
												if (ele != null) {
													log(LogStatus.INFO, "Filter has been craeted in the List view",
															YesNo.No);
													flag = true;
												} else {
													log(LogStatus.ERROR, "Filter has been craeted in the List view",
															YesNo.Yes);

												}

											} else {
												log(LogStatus.ERROR, "save button is not clickable", YesNo.No);
											}
										}

									} else {
										log(LogStatus.ERROR, "done button is not clickable", YesNo.No);
									}
								}

								else {
									log(LogStatus.ERROR, "Operator Filter is not Selected", YesNo.No);
								}

							} else {
								log(LogStatus.ERROR, "Field Filter is not Selected", YesNo.No);
							}
						} else {
							log(LogStatus.ERROR, "Add Filter button is not clickable", YesNo.No);
						}

					}

				}

				else {
					log(LogStatus.ERROR, "Filter Done button is not clicked", YesNo.No);
				}

			}

			else {
				log(LogStatus.ERROR, "all checkbox is not clickable", YesNo.No);
			}
		} else {
			log(LogStatus.ERROR, "list filter section is not clickable", YesNo.No);
		}

		return flag;
	}

	public boolean clickOnAlreadyCreatedItem(String projectName, String alreadyCreated, TabName tabName, int timeout) {
		boolean flag = false;
		String xpath = "";
		String viewList = null;
		switch (tabName) {
		case InstituitonsTab:

			if (ProjectName.MNA.toString().equals(projectName)) {
				viewList = "All Accounts";
			} else {
				viewList = "All Firms";
			}
			break;

		case TestCustomObjectTab:
			viewList = "Automation All";
			break;
		case CompaniesTab:
			viewList = "All Companies";
			break;
		case Navigation:
			viewList = "All";
			break;
		case ContactTab:
			viewList = "All";
			break;
		case FundsTab:
			viewList = "All";
			break;
		case DealTab:
			viewList = "All";
			break;
		case Object1Tab:
			viewList = "All";
			break;
		case Object2Tab:
			viewList = "All";
			break;
		case Object3Tab:
			viewList = "All";
			break;
		case Object4Tab:
			viewList = "All";
			break;
		case Object5Tab:
			viewList = "All";
			break;
		case Object6Tab:
			viewList = "All";
			break;
		case Object7Tab:
			viewList = "All";
			break;
		case NavatarSetup:
			viewList = "All";
			break;
		case RecycleBinTab:
			viewList = "My Recycle Bin";
			break;
		case SDGTab:
			viewList = "All";
			break;
		default:
			return false;
		}
		System.err.println("Passed switch statement");
		WebElement ele, selectListView;
		ele = null;

		refresh(driver);
		if (TabName.RecycleBinTab.equals(tabName)) {

		} else {
			if (click(driver, getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
				ThreadSleep(3000);
				xpath = "//div[@class='listContent']//li/a/span[contains(text(),'" + viewList + "')]";
				selectListView = FindElement(driver, xpath, "Select List View : " + viewList, action.SCROLLANDBOOLEAN,
						30);
				if (click(driver, selectListView, "select List View : " + viewList, action.SCROLLANDBOOLEAN)) {
				} else {
					appLog.error("Not able to select on Select View List : " + viewList);
				}
			} else {
				appLog.error("Not able to click on Select List Icon");
			}
		}

		ThreadSleep(3000);
		ThreadSleep(5000);

		if (sendKeys(driver, getSearchIcon_Lighting(20), alreadyCreated + "\n", "Search Icon Text",
				action.SCROLLANDBOOLEAN)) {
			ThreadSleep(5000);

			xpath = "//table[@data-aura-class='uiVirtualDataTable']//tbody//tr//th//span//*[text()='" + alreadyCreated
					+ "']";
			ele = FindElement(driver, xpath, alreadyCreated, action.BOOLEAN, 30);
			ThreadSleep(2000);

			if (click(driver, ele, alreadyCreated, action.BOOLEAN)) {
				ThreadSleep(3000);
				click(driver, getPagePopUp(projectName, 5), "Page PopUp", action.BOOLEAN);
				flag = true;
			} else {
				appLog.error("Not able to Click on Already Created : " + alreadyCreated);
			}
		} else {
			appLog.error("Not able to enter value on Search Box");
		}

		return flag;
	}

	/**
	 * 
	 * @author Ankur Huria
	 * 
	 * @param valueType
	 * 
	 * @param forFilterNumber
	 * 
	 * @param value
	 * 
	 * @param fieldvalue
	 * 
	 * @param timeOut
	 * 
	 * @return true/false
	 * 
	 */

	public SoftAssert checkFilterSingle(String path, String sheetName, int timeOut) {

		String operation;
		String fieldValue;
		String operatorValue;
		String operator;
		String valueType;
		String expectedResult = "";
		for (int i = 1; i > 0; i++) {
			operation = ExcelUtils.readData(path, sheetName, i, 6);
			if (operation == null) {
				appLog.info("Done with the filters");
				break;
			}
			operation.trim();
			fieldValue = ExcelUtils.readData(path, sheetName, i, 0);
			if (fieldValue == null) {
				appLog.info("No Field value is provided in row number " + (i + 1)
						+ ". So Skipping to the next field filter check.");

				/*
				 * ExcelUtils.
				 * writeDataInExcel("Not Checked: No Field value is provided in row number " +
				 * (i + 1) + ". So Skipping to the next field filter check.",
				 * "Filter Logic for single row", i, 7);
				 */

				continue;
			}
			valueType = ExcelUtils.readData(path, sheetName, i, 2);

			String display = CommonLib.getAttribute(driver, firstFilterGrid(20), "Expand The Grid ", "style");

			try {
				if (!display.equals("")) {
					appLog.info("Filter Grid Not Expanded By Default, Now Going to Expand");
					if (CommonLib.click(driver, firstFilterGridBtn(10), "Filter Grid Button", action.BOOLEAN)) {
						appLog.info("Clicked on Filter Grid Button");
					} else {
						appLog.error("Not Able  to Click on Filter Grid Button");
						break;
					}
				} else {
					appLog.info("Filter Grid Expanded By Default");
				}

			} catch (Exception e) {
				e.printStackTrace();
				break;
			}

			if (searchClearButton(3) != null) {
				CommonLib.clickUsingJavaScript(driver, searchClearButton(2), "searchClearButton",
						action.SCROLLANDBOOLEAN);
				appLog.info("Clicked on Filter Search Clear Button");
			}
			CommonLib.ThreadSleep(2000);
			if (setFieldValueOnManageInvestor(fieldValue, 1, timeOut)) {

				if (operation.equalsIgnoreCase("Operator")) {
					List<String> list = operatorCheck(path, i, fieldValue, sheetName);
					if (!list.isEmpty()) {
						for (int j = 0; j < list.size(); j++) {
							sa.assertTrue(false, list.get(j) + " :Operator not found in the drop down for field value: "
									+ fieldValue);
						}
					} else {
						appLog.info("Successfully verified operators.");
					}
				} else if (operation.equalsIgnoreCase("Result")) {
					operator = ExcelUtils.readData(path, sheetName, i, 1);
					operatorValue = ExcelUtils.readData(path, sheetName, i, 3);
					if (setOperatorValueOnManageInvestor(operator, 1, timeOut)) {
						if (setCriterionValueOnManageTarget(valueType, 1, operatorValue, fieldValue, timeOut)) {
							if (searchButton(3) != null) {

								if (click(driver, searchButton(timeOut), "Search Button", action.THROWEXCEPTION)) {
									expectedResult = ExcelUtils.readData(path, sheetName, i, 4);
									List<String> list = new ArrayList<String>();
									list.addAll(checkResultOnManageInvestor(expectedResult, timeOut));
									System.out.println("\n\n\nis list empty: " + list.isEmpty() + "\n\n\nList Size: "
											+ list.size());
									if (!list.isEmpty()) {
										System.out.println("Inside the condition\n\n\n");
										for (int j = 0; j < list.size(); j++) {
											System.out.println("Inside the loop.");
											sa.assertTrue(false,
													list.get(j) + " :Target not found for field value " + fieldValue
															+ " operator " + operator + " and value " + operatorValue);
										}
									}
								} else {
									appLog.error("Not Able to Click on Search Button");
								}

							}

							else {

								if (click(driver, applyButton(timeOut), "Apply Button", action.THROWEXCEPTION)) {
									expectedResult = ExcelUtils.readData(path, sheetName, i, 4);
									List<String> list = new ArrayList<String>();
									list.addAll(checkResultOnManageInvestor(expectedResult, timeOut));
									System.out.println("\n\n\nis list empty: " + list.isEmpty() + "\n\n\nList Size: "
											+ list.size());
									if (!list.isEmpty()) {
										System.out.println("Inside the condition\n\n\n");
										for (int j = 0; j < list.size(); j++) {
											System.out.println("Inside the loop.");
											sa.assertTrue(false,
													list.get(j) + " :Target not found for field value " + fieldValue
															+ " operator " + operator + " and value " + operatorValue);
										}
									}
								}

								else {
									appLog.error("Not Able to Click on Apply Button");
								}
							}

						}
					} else {
						appLog.info("Not able to set operator value. So Skipping to the next filter.");
						continue;
					}
				} else if (operation.equalsIgnoreCase("both")) {
					List<String> list = operatorCheck(path, i, fieldValue, sheetName);
					if (!list.isEmpty()) {
						for (int j = 0; j < list.size(); j++) {
							sa.assertTrue(false, list.get(j) + " :Operator not found in the drop down for field value: "
									+ fieldValue);
						}
					} else {
						appLog.info("Successfully verified operators.");
					}
					operator = ExcelUtils.readData(path, sheetName, i, 1);
					operatorValue = ExcelUtils.readData(path, sheetName, i, 3);
					CommonLib.ThreadSleep(2000);
					if (setOperatorValueOnManageInvestor(operator, 1, timeOut)) {
						if (setCriterionValueOnManageTarget(valueType, 1, operatorValue, fieldValue, timeOut)) {
							if (searchButton(3) != null) {

								if (click(driver, searchButton(timeOut), "Search Button", action.THROWEXCEPTION)) {
									expectedResult = ExcelUtils.readData(path, sheetName, i, 4);
									List<String> listResult = new ArrayList<String>();
									listResult.addAll(checkResultOnManageInvestor(expectedResult, timeOut));
									System.out.println("\n\n\nis list empty: " + listResult.isEmpty()
											+ "\n\n\nList Size: " + listResult.size());
									if (!listResult.isEmpty()) {
										System.out.println("Inside the condition\n\n\n");
										for (int j = 0; j < listResult.size(); j++) {
											System.out.println("Inside the loop.");
											sa.assertTrue(false,
													listResult.get(j) + " :Target not found for field value "
															+ fieldValue + " operator " + operator + " and value "
															+ operatorValue);
										}
									}
								} else {
									appLog.error("Not Able to Click on Search Button");
								}

							}

							else {

								if (click(driver, applyButton(timeOut), "Apply Button", action.THROWEXCEPTION)) {
									expectedResult = ExcelUtils.readData(path, sheetName, i, 4);
									List<String> listResult = new ArrayList<String>();
									listResult.addAll(checkResultOnManageInvestor(expectedResult, timeOut));
									System.out.println("\n\n\nis list empty: " + listResult.isEmpty()
											+ "\n\n\nList Size: " + listResult.size());
									if (!listResult.isEmpty()) {
										System.out.println("Inside the condition\n\n\n");
										for (int j = 0; j < listResult.size(); j++) {
											System.out.println("Inside the loop.");
											sa.assertTrue(false,
													listResult.get(j) + " :Target not found for field value "
															+ fieldValue + " operator " + operator + " and value "
															+ operatorValue);
										}
									}
								}

								else {
									appLog.error("Not Able to Click on Apply Button");
								}
							}

						}
					} else {
						appLog.info("Not able to set operator value. So Skipping to the next filter.");
						continue;
					}
				}
			} else {
				appLog.info("Field Value:" + fieldValue + " is not available in drop down. So skipping this filter");

				continue;
			}

		}
		return sa;
	}

	/**
	 * 
	 * @author Ankur Huria
	 * 
	 * @param valueType
	 * 
	 * @param forFilterNumber
	 * 
	 * @param value
	 * 
	 * @param fieldvalue
	 * 
	 * 
	 * @param timeOut
	 * 
	 * @return true/false
	 * 
	 */
	public boolean setFieldValueOnManageInvestor(String fieldValue, int forFilterNumber, int timeOut) {

		boolean filterFindflag = false;

		if (CommonLib.click(driver, fieldInputBox(30), "fieldInputBox", action.BOOLEAN)) {
			appLog.info("Clicked on Field Input Box");

			List<WebElement> filterList = filterList();
			for (WebElement filter : filterList) {

				if (filter.getText().equalsIgnoreCase(fieldValue)) {

					if (CommonLib.click(driver, filter, fieldValue, action.SCROLLANDBOOLEAN)) {

						appLog.info("Field value " + fieldValue + " is present in the drop down and Selected");

						filterFindflag = true;
						break;

					} else {

						filterFindflag = false;

					}

				}
			}

		} else {
			appLog.error("Not Able to Click on Field Input Box");
			return false;
		}
		if (filterFindflag)
			return true;
		else {
			appLog.error("Field value " + fieldValue + " is not present in the drop down, for filter no.: "
					+ forFilterNumber + " So, Skipping this Filter");
			sa.assertTrue(false, "Field value " + fieldValue + " is not present in the drop down, for filter no.: "
					+ forFilterNumber + " So, Skipping this Filter");
			return false;
		}

	}

	/**
	 * 
	 * @author Ankur Huria
	 * 
	 * @param operator
	 * 
	 * @param forFilterNumber
	 * 
	 * 
	 * @param timeOut
	 * 
	 * @return true/false
	 * 
	 */

	public boolean setOperatorValueOnManageInvestor(String operator, int forFilterNumber, int timeOut) {

		String operatorValues = "//div//lightning-combobox//lightning-base-combobox//button[@name='']//parent::div//following-sibling::div//lightning-base-combobox-item/span/span";
		if (CommonLib.dropDownHandle(driver, operatorInputBox(30), operatorValues, "Operator Dropdown", operator))

		{
			appLog.info("Operator: " + operator + " is present in the drop down and Selected");
			return true;
		} else {
			appLog.error(
					"Operator: " + operator + " is Not present in the drop down for Filter Number: " + forFilterNumber);
			sa.assertTrue(false,
					"Operator: " + operator + " is Not present in the drop down for Filter Number: " + forFilterNumber);
			return false;
		}

	}

	/**
	 * 
	 * /**
	 * 
	 * @author Ankur Huria
	 * 
	 * @param valueType
	 * 
	 * @param forFilterNumber
	 * 
	 * @param value
	 * 
	 * @param fieldvalue
	 * 
	 * 
	 * @param timeOut
	 * 
	 * @return true/false
	 * 
	 */

	public boolean setCriterionValueOnManageTarget(String valueType, int forFilterNumber, String value,
			String fieldvalue, int timeOut) {

		String xpath = "//lightning-input[not(contains(@class,'slds-combobox__input')) and contains(@style,'display')]//input";

		String xpathLookUp = "//lightning-input[not(contains(@class,'slds-combobox__input')) and contains(@style,'display')]/parent::div/following-sibling::div/lightning-icon";

		String xpathcalendar = "//input/ancestor::lightning-datepicker//input";

		String[] valueTypes = valueType.split("-");

		if (valueTypes[0].equalsIgnoreCase("TextBox") || value == null) {

			WebElement ele = FindElement(driver, xpath, "valueInputBox", action.SCROLLANDBOOLEAN, timeOut);
			if (value == null) {

				value = "";

			}

			ele.clear();

			if (sendKeys(driver, ele, value, "Criterion Box", action.SCROLLANDTHROWEXCEPTION)) {
				appLog.info("Successfully send the Value :" + value + " , for Field : " + fieldvalue);
				return true;
			}

			else {
				appLog.error("Not Successfully send the Value :" + value + " , for Field : " + fieldvalue);
				return false;
			}

		} else if (valueTypes[0].equalsIgnoreCase("lookup")) {

			WebElement eleLookUp = FindElement(driver, xpathLookUp, "Look Up icon", action.BOOLEAN, timeOut);

			scrollDownThroughWebelement(driver, eleLookUp, "LookUp Icon");

			if (eleLookUp != null) {

				click(driver, eleLookUp, "LookUp Icon", action.SCROLLANDTHROWEXCEPTION);

				if (valueType.equalsIgnoreCase("lookup-radio")) {
					String val[] = value.split(",");

					for (int i = 0; i < val.length; i++) {
						if (!click(driver,

								FindElement(driver,

										"//tr/th//div//lightning-base-formatted-text[text()='" + val[i]
												+ "']/ancestor::th/preceding-sibling::td/lightning-primitive-cell-checkbox",

										"Radio Button of: " + val[i], action.BOOLEAN, timeOut),

								"Radio Button of: " + val[i], action.SCROLLANDBOOLEAN)) {

							appLog.info(
									val[i] + " is not present in the lookup window., for Field Value: " + fieldvalue);
							sa.assertTrue(false,
									val[i] + " is not present in the lookup window., for Field Value: " + fieldvalue);

							if (click(driver, closePopupButton(20),

									"closePopupButton", action.SCROLLANDBOOLEAN)) {

								appLog.info("Clicked on Close Popup Button");
							} else {
								appLog.error("Not able to Click on Close Popup Button");
							}

							return false;

						}
					}

				} else {

					String val[] = value.split(",");

					for (int i = 0; i < val.length; i++) {

						if (!click(driver,

								FindElement(driver,

										"//lightning-menu-item//span[text()='" + val[i] + "']/parent::a",

										"CheckBox of: " + val[i], action.BOOLEAN, 20),

								"CheckBox of: " + val[i], action.SCROLLANDBOOLEAN)) {

							appLog.error(
									val[i] + " is not present in the lookup window., for Field Value: " + fieldvalue);
							sa.assertTrue(false,
									val[i] + " is not present in the lookup window., for Field Value: " + fieldvalue);
							if (click(driver, closePopupButton(20),

									"closePopupButton", action.SCROLLANDBOOLEAN)) {

								appLog.info("Clicked on Close Popup Button");
							} else {
								appLog.error("Not able to Click on Close Popup Button");
							}

							return false;

						}

					}

				}

				if (click(driver, getInsertSelectedButton(timeOut), "Insert Selected", action.SCROLLANDBOOLEAN)) {

					appLog.info("Successfully selected the value: " + value + " for Field Value: "
							+ "Invalid Date format: " + value + " for Field Value: " + fieldvalue);

					return true;

				}

				System.out.println("going to close the lookup window.");

				appLog.info("Not able to select value " + value + " from the lookup window");

				return false;

			} else {

				appLog.info("Lookup is not displaying for field value: " + fieldvalue);
				sa.assertTrue(false, "Lookup is not displaying for field value: " + fieldvalue);

				return false;

			}

		} else if (valueType.equalsIgnoreCase("Date")) {

			if (!click(driver, FindElement(driver, xpathcalendar, "calendar Icon", action.BOOLEAN, timeOut),

					"Calendar icon", action.SCROLLANDBOOLEAN)) {

				appLog.info("Calendar icon is not present for the current field value.: " + fieldvalue);
				sa.assertTrue(false, "Calendar icon is not present for the current field value.: " + fieldvalue);

				return false;

			}

			String[] val = value.split("/");

			if (datePickerHandle(driver, monthInDatePicker(20), previousMonthButtonInDatePicker(10), "Date Picker",
					val[2], val[1], val[0])) {
				appLog.info("Date has been Selected: " + value + " for Field Value: " + fieldvalue);
				return true;

			} else {

				appLog.error("Invalid Date format: " + value + " for Field Value: " + fieldvalue);
				sa.assertTrue(false, "Invalid Date format: " + value + " for Field Value: " + fieldvalue);

				return false;

			}

		}

		else {

			appLog.info("Criterion box number " + forFilterNumber + " is not present");

			return false;

		}

	}

	/**
	 * @author Ankur Huria
	 * @param expectedResult
	 * @param timeOut
	 * @return List<String>
	 */
	public List<String> checkResultOnManageInvestor(String expectedResult, int timeOut) {
		List<String> notFound = new ArrayList<String>();
		String[] result = expectedResult.split("<:break:>");

		for (int i = 0; i < result.length; i++) {

			List<WebElement> elements = null;
			List<String> elementsText = null;
			if (result[i].toLowerCase().contains("no data to display")) {

				elements = FindElements(driver, "//div/div/b", "Filter Result List: No data to Display");
				elementsText = elements.stream().map(x -> x.getText().trim().toLowerCase())
						.collect(Collectors.toList());

			} else {

				elements = FindElements(driver, "//tbody//tr//th//lightning-formatted-url/a", "Filter Result List");
				elementsText = elements.stream().map(x -> x.getText().trim().toLowerCase())
						.collect(Collectors.toList());

			}

			try {
				if (elements.size() == 0) {
					appLog.error(result[i] + " is not matched.");
					notFound.add(result[i]);
				} else {
					if (elementsText.contains(result[i].toLowerCase())) {
						appLog.info(result[i] + " is matched.");
					} else {
						appLog.error(result[i] + " is not matched.");
						notFound.add(result[i]);
					}
				}

			} catch (Exception e) {
				notFound.add(result[i]);
			}

		}

		return notFound;
	}

	/**
	 * @author Ankur Huria
	 * @param path
	 * @param rowNum
	 * @param fieldValue
	 * @param sheetName
	 * @return List<String>
	 */
	public List<String> operatorCheck(String path, int rowNum, String fieldValue, String sheetName) {
		List<String> notFoundOperators = new ArrayList<String>();
		CommonLib.ThreadSleep(2000);
		if (CommonLib.clickUsingJavaScript(driver, operatorInputBox(30), "Operator DropDown",
				action.SCROLLANDBOOLEAN)) {
			appLog.info("Clicked on Operator DropDown for " + fieldValue);
			CommonLib.ThreadSleep(3000);
			List<WebElement> operatorDropDownValues = operatorValues();

			String operatorValues = ExcelUtils.readData(path, sheetName, rowNum, 5);
			String[] opVal = operatorValues.split(",");
			for (int j = 0; j < opVal.length; j++) {
				for (int k = 0; k < operatorDropDownValues.size(); k++) {
					if (opVal[j].equalsIgnoreCase(operatorDropDownValues.get(k).getText())) {
						appLog.info("Successfully Found The Operator: " + opVal[j]);
						break;
					} else if (k == operatorDropDownValues.size() - 1) {
						appLog.info(opVal[j] + " :Operator not found in the drop down for field value: " + fieldValue);
						notFoundOperators.add(opVal[j]);

					}
				}
			}
		} else {
			appLog.error("Not Able to Click on Operator DropDown, So Not able Check Operators for " + fieldValue);
		}
		CommonLib.clickUsingJavaScript(driver, operatorInputBox(30), "Operator DropDown", action.SCROLLANDBOOLEAN);
		return notFoundOperators;
	}

	public List<String> verifyListView(List<String> listViewName) {
		boolean flag = false;
		ArrayList<String> result = new ArrayList<String>();
		if (CommonLib.click(driver, getClickedOnRecentlyViewed(30), "Recently Viewed", action.SCROLLANDBOOLEAN)) {

			appLog.info("clicked on recently viewed");

			List<String> listView = new ArrayList<String>();

			List<WebElement> lists = CommonLib.FindElements(driver, "//div[@class='scroller']//ul//li",
					"RecentlyViewedList");
			if (lists.size() != 0) {
				for (int i = 0; i < lists.size(); i++) {
					WebElement element = lists.get(i);
					String listName = CommonLib.getText(driver, element, "list view of Firm", action.BOOLEAN);
					listView.add(listName.replace("Selected", "").trim().replace("\n", ""));

				}
			} else {
				log(LogStatus.ERROR, "Could not get the list view name", YesNo.No);
				result.add("Could not get the list view name");
			}

			for (int i = 0; i < listViewName.size(); i++) {
				if (listView.contains(listViewName.get(i))) {

					log(LogStatus.INFO, listViewName.get(i) + " List Name is present", YesNo.No);

				} else {
					log(LogStatus.ERROR, listViewName.get(i) + " List Name is Not present", YesNo.No);
					result.add(listViewName.get(i) + " List Name is Not present");

				}
			}
		} else {
			appLog.error("Not able to click on recently viewed...");
			result.add("Not able to click on recently viewed...");

		}

		if (CommonLib.click(driver, getClickedOnRecentlyViewed(30), "Recently Viewed", action.SCROLLANDBOOLEAN)) {

			appLog.info("clicked on recently viewed");
		} else {
			appLog.error("Not able to click on recently viewed...");
			result.add("Not able to click on recently viewed...");

		}
		return result;
	}

	public ArrayList<String> verifyFilterOnListView(String[] listViewName, String[] filter, String[] field,
			String[] Operator, String[] filterValue, String[] filterCondition) {
		String xPath = "";
		WebElement ele;
		ArrayList<String> result = new ArrayList<String>();

		for (int i = 0; i < filterCondition.length; i++) {
			String[] filterFiled = null;
			String[] fOperator = null;
			String[] FOperand = null;

			if (CommonLib.click(driver, getClickedOnRecentlyViewed(30), "Recently Viewed", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on the recently view button", YesNo.No);

				xPath = "//ul[@class='slds-dropdown__list slds-show']//span[text()='" + listViewName[i] + "']";
				ele = CommonLib.FindElement(driver, xPath, listViewName[i], action.SCROLLANDBOOLEAN, 50);

				if (CommonLib.click(driver, ele, listViewName[i] + " element", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on " + listViewName[i] + " element" + " button", YesNo.No);

					if (filterCondition[i].equals("All Filters")) {

						if (CommonLib.click(driver, getshowFilter(50), "Show filter", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Show filter button", YesNo.No);

							String scopeLabelFilter = CommonLib.getText(driver, getscopeLabelFilter(50),
									"scope label filter", action.SCROLLANDBOOLEAN);
							if (scopeLabelFilter.equals(filter[i])) {
								log(LogStatus.INFO, scopeLabelFilter + " is visible in the Filter by Owner", YesNo.No);
							} else {
								log(LogStatus.ERROR, scopeLabelFilter + " is not visible in the Filter by Owner",
										YesNo.Yes);
								result.add(scopeLabelFilter + " is not visible in the Filter by Owner");
							}

							filterFiled = field[i].split("<FieldBreak>");
							fOperator = Operator[i].split("<OperatorBreak>");
							FOperand = filterValue[i].split("<valueBreak>");

							if (filterFiled.length == 1) {
								String filterFieldLabel = CommonLib.getText(driver, getfilterFieldLabel(50),
										"Filter field label", action.SCROLLANDBOOLEAN);
								String filterOperator = CommonLib.getText(driver, getfilterOperator(50),
										"Filter Operator", action.SCROLLANDBOOLEAN);
								String filterOperand = CommonLib.getText(driver, getfilterOperand(50), "Filter Operand",
										action.SCROLLANDBOOLEAN);

								if (filterFieldLabel.equalsIgnoreCase(field[i])
										&& filterOperator.equalsIgnoreCase(Operator[i])
										&& filterOperand.equalsIgnoreCase(filterValue[i])) {
									log(LogStatus.INFO, filterFieldLabel + ", " + filterOperator + " and "
											+ filterOperand + " have been matched", YesNo.No);
								} else {
									log(LogStatus.ERROR,
											"Either Filter label name : " + filterFieldLabel + " or filter Operator :"
													+ filterOperator + " Or Filter operand :" + filterOperand
													+ " are not matced with Filter label name : " + field
													+ ", filter Operator :" + Operator + ", Filter operand :"
													+ filterValue[i] + "",
											YesNo.No);
									result.add("Either Filter label name : " + filterFieldLabel
											+ " or filter Operator :" + filterOperator + " Or Filter operand :"
											+ filterOperand + " are not matced with Filter label name : " + field
											+ ", filter Operator :" + Operator + ", Filter operand :" + filterValue[i]
											+ "");
								}
							} else {

								for (int j = 0; j < filterFiled.length; j++) {
									xPath = "//div[@id='filterPanelFieldCriterion" + j + "']//div[@class='fieldLabel']";
									ele = CommonLib.FindElement(driver, xPath, "Field Label", action.SCROLLANDBOOLEAN,
											50);
									String filterFieldLabel = CommonLib.getText(driver, ele, "Filter field label",
											action.SCROLLANDBOOLEAN);

									xPath = "//div[@id='filterPanelFieldCriterion" + j
											+ "']//span[@class='test-operatorWrapper']";
									ele = CommonLib.FindElement(driver, xPath, "Field Label", action.SCROLLANDBOOLEAN,
											50);
									String filterOperator = CommonLib.getText(driver, ele, "Filter Operator",
											action.SCROLLANDBOOLEAN);

									xPath = "//div[@id='filterPanelFieldCriterion" + j
											+ "']//span[@class='test-operandsWrapper']";
									ele = CommonLib.FindElement(driver, xPath, "Field Label", action.SCROLLANDBOOLEAN,
											50);
									String filterOperand = CommonLib.getText(driver, ele, "Filter Operand",
											action.SCROLLANDBOOLEAN);

									if (filterFieldLabel.equalsIgnoreCase(filterFiled[j])
											&& filterOperator.equalsIgnoreCase(fOperator[j])
											&& filterOperand.equalsIgnoreCase(FOperand[j])) {
										log(LogStatus.INFO, filterFieldLabel + ", " + filterOperator + " and "
												+ filterOperand + " have been matched", YesNo.No);
									} else {
										log(LogStatus.ERROR, "Either Filter label name : " + filterFieldLabel
												+ " or filter Operator :" + filterOperator + " Or Filter operand :"
												+ filterOperand + " are not matced with Filter label name : "
												+ filterFiled[j] + ", filter Operator :" + fOperator[j]
												+ ", Filter operand :" + FOperand[j] + "", YesNo.Yes);
										result.add("Either Filter label name : " + filterFieldLabel
												+ " or filter Operator :" + filterOperator + " Or Filter operand :"
												+ filterOperand + " are not matced with Filter label name : "
												+ filterFiled[j] + ", filter Operator :" + fOperator[j]
												+ ", Filter operand :" + FOperand[j] + "");
									}

									if (filterLogic(50) != null) {
										log(LogStatus.INFO, "Filter logic is visible", YesNo.No);
									} else {
										log(LogStatus.INFO, "Filter logic is nto visible", YesNo.Yes);
										result.add("Filter logic is not visible");
									}

								}
							}

							CommonLib.refresh(driver);
						} else {
							log(LogStatus.ERROR, "Not able to click on Show filter button", YesNo.Yes);
							result.add("Not able to click on Show filter button");
						}

					}

					else if (filterCondition[i].trim().equalsIgnoreCase("Only Filter_By_Owner")) {

						if (CommonLib.click(driver, getshowFilter(50), "Show filter", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Show filter button", YesNo.No);

							String scopeLabelFilter = CommonLib.getText(driver, getscopeLabelFilter(50),
									"scope label filter", action.SCROLLANDBOOLEAN);
							if (scopeLabelFilter.equals(filter[i])) {
								log(LogStatus.INFO, scopeLabelFilter + " is visible in the Filter by Owner", YesNo.No);
							} else {
								log(LogStatus.ERROR, scopeLabelFilter + " is not visible in the Filter by Owner for "
										+ listViewName[i], YesNo.Yes);
								result.add(scopeLabelFilter + " is not visible in the Filter by Owner for "
										+ listViewName[i]);
							}

							CommonLib.refresh(driver);
						} else {
							log(LogStatus.ERROR, "Not able to click on Show filter button", YesNo.Yes);
							result.add("Not able to click on Show filter button");
						}

					}

					else if (filterCondition[i].trim().equalsIgnoreCase("Only Filter_icon_Availability")) {

						ele = getshowFilter(50);
						if (ele == null) {
							log(LogStatus.INFO, "Filter icon is disable for list view : " + listViewName[i], YesNo.No);
						} else {
							log(LogStatus.ERROR, "Filter icon is not disable for list view : " + listViewName[i],
									YesNo.Yes);
							result.add("Filter icon is not disable for list view : " + listViewName[i]);
						}
						CommonLib.refresh(driver);
					}

					else {
						log(LogStatus.ERROR, "Not able to click on " + listViewName[i] + "", YesNo.Yes);
						result.add("Not able to click on " + listViewName[i] + "");
					}

				} else {
					log(LogStatus.ERROR, listViewName[i] + " element not found", YesNo.Yes);
					result.add(listViewName[i] + " element not found");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on recently view", YesNo.Yes);
				result.add("Not able to click on recently view");
			}
		}
		return result;

	}

	public ArrayList<String> verifyFieldsOnListview(String listViewAndFieldData, int timeOut) {
		String xPath = "";
		WebElement ele;
		List<WebElement> elements;
		ArrayList<String> actualFieldValue = new ArrayList<String>();
		ArrayList<String> expectedFieldValue = new ArrayList<String>();
		ArrayList<String> result = new ArrayList<String>();
		String[] listViewAndFieldName = listViewAndFieldData.split("<break>");

		for (int i = 0; i < listViewAndFieldName.length; i++) {
			String data[] = listViewAndFieldName[i].split("<fieldBreak>");
			String listViewName = data[0];
			String fieldData[] = data[1].split("<f>");

			for (int k = 0; k < fieldData.length; k++) {
				String val = fieldData[k];
				expectedFieldValue.add(val);
			}
			if (CommonLib.click(driver, getClickedOnRecentlyViewed(timeOut), "Recently Viewed ero icon",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on the recently view ero icon", YesNo.No);

				xPath = "//ul[@class='slds-dropdown__list slds-show']//span[text()='" + listViewName + "']";
				ele = CommonLib.FindElement(driver, xPath, listViewName, action.SCROLLANDBOOLEAN, timeOut);

				if (CommonLib.click(driver, ele, listViewName + " element", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on " + listViewName + " element" + " button", YesNo.No);
					ThreadSleep(10000);
					xPath = "//table[@data-aura-class='uiVirtualDataTable']//thead//th//span[@class='slds-truncate' and text()!='']";
					elements = CommonLib.FindElements(driver, xPath, listViewName + "'s field");
					for (int j = 0; j < elements.size(); j++) {
						String text = CommonLib.getText(driver, elements.get(j), listViewName + " field",
								action.SCROLLANDBOOLEAN);
						actualFieldValue.add(text);
					}

					for (int a = 0; a < expectedFieldValue.size(); a++) {
						if (expectedFieldValue.get(a).equals(actualFieldValue.get(a))) {
							log(LogStatus.INFO, "Expected field value : " + expectedFieldValue.get(a)
									+ " has been matched with the Actual field value : " + actualFieldValue.get(a),
									YesNo.No);
						} else {
							log(LogStatus.ERROR, "Expected field value : " + expectedFieldValue.get(a)
									+ " is not matched with the Actual field value : " + actualFieldValue.get(a),
									YesNo.No);
							result.add("Expected field value : " + expectedFieldValue.get(a)
									+ " is not matched with the Actual field value : " + actualFieldValue.get(a));
						}

					}

				} else {
					log(LogStatus.ERROR, "Not able to click on the " + listViewName + " list view name", YesNo.Yes);
					result.add("Not able to click on the " + listViewName + " list view name");
				}
			}

			else {
				log(LogStatus.ERROR, "Not able to click on the recently view ero icon", YesNo.Yes);
				result.add("Not able to click on the recently view ero icon");
			}
			actualFieldValue.removeAll(actualFieldValue);
			expectedFieldValue.removeAll(expectedFieldValue);

		}

		return result;

	}

	/**
	 * @author Ankur Huria
	 * @param ExpectedLabels
	 * @param ExpectedLabelsValues
	 * @return negativeResult
	 */
	public List<String> verifyHighlightLabelValues(List<String> ExpectedLabels, List<String> ExpectedLabelsValues) {
		List<String> highlightedPanelLabels = highlightPanelLabels().stream().map(x -> x.getText().trim())
				.collect(Collectors.toList());
		List<String> highlightedPanelLabelsValues = highlightPanelLabelsValues().stream()
				.map(x -> x.getText().replace("Preview", "").replace("Open", "").trim()).collect(Collectors.toList());
		List<String> negativeResult = new ArrayList<String>();
		if (highlightedPanelLabels.size() == highlightedPanelLabelsValues.size()) {
			log(LogStatus.INFO, "No. of Highlighted Labels and Values Matched", YesNo.No);
			if (ExpectedLabels.size() == ExpectedLabelsValues.size()) {
				log(LogStatus.INFO, "No. of Expected Highlighted Labels and Values Matched", YesNo.No);
				if (ExpectedLabels.size() == highlightedPanelLabels.size()) {
					log(LogStatus.INFO, "No. of Expected Highlighted Labels and Actual Highlighted Labels Matched",
							YesNo.No);
					int i = 0;
					for (String expLabel : ExpectedLabels) {
						if (expLabel.equals(highlightedPanelLabels.get(i))
								&& ExpectedLabelsValues.get(i).equals(highlightedPanelLabelsValues.get(i))) {
							log(LogStatus.INFO, "Label & Values Matched, Expected: " + expLabel + " :: "
									+ ExpectedLabelsValues.get(i) + " & Actual: " + highlightedPanelLabels.get(i)
									+ " :: " + highlightedPanelLabelsValues.get(i), YesNo.No);
						} else {

							log(LogStatus.ERROR, "Label & Values Doesn't Matched, Expected: " + expLabel + " :: "
									+ ExpectedLabelsValues.get(i) + " & Actual: " + highlightedPanelLabels.get(i)
									+ " :: " + highlightedPanelLabelsValues.get(i), YesNo.No);
							negativeResult.add("Label & Values Doesn't Matched, Expected: " + expLabel + " :: "
									+ ExpectedLabelsValues.get(i) + " & Actual: " + highlightedPanelLabels.get(i)
									+ " :: " + highlightedPanelLabelsValues.get(i));

						}

						i++;
					}

				}

				else {
					log(LogStatus.ERROR,
							"No. of Expected Highlighted Labels and Actual Highlighted Labels doesn't Match, So not able to Continue to verify Label and Values, No. of Expected Labels: "
									+ ExpectedLabels.size() + " & No. of Actual Highlighted Labels: "
									+ highlightedPanelLabels.size(),
							YesNo.No);
					negativeResult.add(
							"No. of Expected Highlighted Labels and Actual Highlighted Labels doesn't Match, So not able to Continue to verify Label and Values, No. of Expected Labels: "
									+ ExpectedLabels.size() + " & No. of Actual Highlighted Labels: "
									+ highlightedPanelLabels.size());
				}

			}

			else {
				log(LogStatus.ERROR,
						"No. of Expected Highlighted Labels and Values doesn't Match, So not able to Continue to verify Label and Values, No. of Expected Labels: "
								+ ExpectedLabels.size() + " & No. of Expected Values: " + ExpectedLabelsValues.size(),
						YesNo.No);
				negativeResult.add(
						"No. of Expected Highlighted Labels and Values doesn't Match, So not able to Continue to verify Label and Values, No. of Expected Labels: "
								+ ExpectedLabels.size() + " & No. of Expected Values: " + ExpectedLabelsValues.size());
			}

		}

		else {
			log(LogStatus.ERROR,
					"No. of Actual Highlighted Labels and Values doesn't Match, So not able to Continue to verify Label and Values, No. of Actual Labels: "
							+ highlightedPanelLabels.size() + " & No. of Actual Values: "
							+ highlightedPanelLabelsValues.size(),
					YesNo.Yes);
			negativeResult.add(
					"No. of Actual Highlighted Labels and Values doesn't Match, So not able to Continue to verify Label and Values, No. of Actual Labels: "
							+ highlightedPanelLabels.size() + " & No. of Actual Values: "
							+ highlightedPanelLabelsValues.size());
		}
		return negativeResult;
	}

	/**
	 * @author Ankur Huria
	 * @param ExpectedButtonsOnPage
	 * @param ExpectedButtonsInDownArrowButton
	 * @return negativeResult
	 */
	public List<String> verifyButtonsOnAPageAndInDownArrowButton(List<String> ExpectedButtonsOnPage,
			List<String> ExpectedButtonsInDownArrowButton) {

		List<String> negativeResult = new ArrayList<String>();

		if (!ExpectedButtonsOnPage.isEmpty()) {

			if (ExpectedButtonsOnPage.size() != 1 && !ExpectedButtonsOnPage.get(0).equals("")) {
				List<String> listOfButtons = listOfButtons().stream().map(x -> x.getText().trim())
						.collect(Collectors.toList());

				if (!listOfButtons.isEmpty()) {
					log(LogStatus.INFO, "No. of Buttons Present on Page are: " + listOfButtons.size(), YesNo.No);

					int i = 0;
					if (listOfButtons.size() == ExpectedButtonsOnPage.size()) {
						log(LogStatus.INFO,
								"No. of Actual and Expected Buttons on Page are same, So Continue the Process",
								YesNo.No);

						for (String button : listOfButtons) {
							if (button.equals(ExpectedButtonsOnPage.get(i))) {
								log(LogStatus.INFO, "----Button Matched, Expected: " + ExpectedButtonsOnPage.get(i)
										+ " & Actual: " + button + " on this Page----", YesNo.No);
							} else {

								log(LogStatus.ERROR, "----Button Not Matched, Expected: " + ExpectedButtonsOnPage.get(i)
										+ " but Actual: " + button + " on this Page----", YesNo.No);
								negativeResult.add("----Button Not Matched, Expected: " + ExpectedButtonsOnPage.get(i)
										+ " but Actual: " + button + " on this Page----");

							}

							i++;
						}
					} else {
						log(LogStatus.ERROR,
								"No. of Expected and Actual Buttons on Page not matched, So not able to continue, Expected: "
										+ ExpectedButtonsOnPage + " & Actual: " + listOfButtons,
								YesNo.Yes);
						negativeResult.add(
								"No. of Expected and Actual Buttons on Page not matched, So not able to continue, Expected: "
										+ ExpectedButtonsOnPage + " & Actual: " + listOfButtons);
					}

				}

				else {
					log(LogStatus.ERROR, "No Buttons Are Present on this Page", YesNo.Yes);
					negativeResult.add("No Buttons Are Present on this Page");
				}
			} else

			{
				log(LogStatus.ERROR, "No Expected Buttons to verify on Page Mentioned", YesNo.No);
			}
		} else

		{
			log(LogStatus.ERROR, "No Expected Buttons to verify on Page Mentioned", YesNo.No);
		}

		if (!ExpectedButtonsInDownArrowButton.isEmpty())

		{
			if (ExpectedButtonsInDownArrowButton.size() != 1 && !ExpectedButtonsInDownArrowButton.get(0).equals("")) {
				if (downArrowButton(20) != null) {
					log(LogStatus.INFO, "Down Arrow Button is Present", YesNo.No);
					if (click(driver, downArrowButton(20), "DownArrowButton", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);
						CommonLib.ThreadSleep(2000);

						List<String> dropDownButtonsList = dropDownButtonsList().stream().map(x -> x.getText().trim())
								.collect(Collectors.toList());

						if (!dropDownButtonsList.isEmpty()) {
							log(LogStatus.INFO,
									"No. of Buttons Present on DownArrow Button are: " + dropDownButtonsList.size(),
									YesNo.No);

							int i = 0;
							if (dropDownButtonsList.size() == ExpectedButtonsInDownArrowButton.size()) {
								log(LogStatus.INFO,
										"No. of Actual and Expected Buttons on DownArrowButton are same, So Continue the Process",
										YesNo.No);

								for (String button : dropDownButtonsList) {
									if (button.equals(ExpectedButtonsInDownArrowButton.get(i))) {
										log(LogStatus.INFO,
												"----Button Matched, Expected: "
														+ ExpectedButtonsInDownArrowButton.get(i) + " & Actual: "
														+ button + " in DownArrow Button----",
												YesNo.No);
									} else {

										log(LogStatus.ERROR,
												"----Button Not Matched, Expected: "
														+ ExpectedButtonsInDownArrowButton.get(i) + " but Actual: "
														+ button + " in DownArrow Button----",
												YesNo.No);
										negativeResult.add("----Button Not Matched, Expected: "
												+ ExpectedButtonsInDownArrowButton.get(i) + " but Actual: " + button
												+ " in DownArrow Button----");

									}

									i++;
								}
							} else {
								log(LogStatus.ERROR,
										"No. of Expected and Actual Buttons in DownArrow Button not matched, So not able to continue, Expected: "
												+ ExpectedButtonsInDownArrowButton + " & Actual: "
												+ dropDownButtonsList,
										YesNo.Yes);
								negativeResult.add(
										"No. of Expected and Actual Buttons in DownArrow Button not matched, So not able to continue, Expected: "
												+ ExpectedButtonsInDownArrowButton + " & Actual: "
												+ dropDownButtonsList);
							}

						}

						else {
							log(LogStatus.ERROR, "No Buttons Are Present in DownArrow Button", YesNo.Yes);
							negativeResult.add("No Buttons Are Present in DownArrow Button");
						}

					} else {
						log(LogStatus.INFO, "Not able to Click on Down Arrow Button", YesNo.No);
					}
				}

				else {
					log(LogStatus.ERROR, "Down Arrow Button is not Present on this Page", YesNo.Yes);
					negativeResult.add("Down Arrow Button is not Present on this Page");
				}
			} else

			{
				log(LogStatus.ERROR, "No Expected Buttons to verify in Down Arrow Button Mentioned", YesNo.No);
			}
		} else

		{
			log(LogStatus.ERROR, "No Expected Buttons to verify in Down Arrow Button Mentioned", YesNo.No);
		}

		if (!ExpectedButtonsInDownArrowButton.isEmpty() && !ExpectedButtonsOnPage.isEmpty()) {
			if ((ExpectedButtonsInDownArrowButton.size() == 1 && ExpectedButtonsInDownArrowButton.get(0).equals(""))
					&& ExpectedButtonsOnPage.size() == 1 && ExpectedButtonsOnPage.get(0).equals("")) {
				log(LogStatus.ERROR, "No Expected Buttons to verify in Down Arrow Button and On Page Mentioned",
						YesNo.No);
				negativeResult.add("No Expected Buttons to verify in Down Arrow Button and On Page Mentioned");
			}
		} else {
			log(LogStatus.ERROR, "No Expected Buttons to verify in Down Arrow Button and On Page Mentioned", YesNo.No);
			negativeResult.add("No Expected Buttons to verify in Down Arrow Button and On Page Mentioned");
		}
		return negativeResult;
	}

	/**
	 * @author Ankur Huria
	 * @param expectedListOfTabs
	 * @return negativeResult
	 */
	public List<String> verifyTabsOnAPage(List<String> expectedListOfTabs) {

		List<String> negativeResult = new ArrayList<String>();

		if (!expectedListOfTabs.isEmpty()) {

			if (expectedListOfTabs.size() != 1 && !expectedListOfTabs.get(0).equals("")) {
				List<String> tabsInPage = tabsInPage().stream().map(x -> x.getText().trim())
						.collect(Collectors.toList());

				if (!tabsInPage.isEmpty()) {
					log(LogStatus.INFO, "No. of Tabs Present on Page are: " + tabsInPage.size(), YesNo.No);

					int i = 0;
					if (tabsInPage.size() == expectedListOfTabs.size()) {
						log(LogStatus.INFO, "No. of Actual and Expected Tabs on Page are same, So Continue the Process",
								YesNo.No);

						for (String tab : tabsInPage) {
							if (tab.equals(expectedListOfTabs.get(i))) {
								log(LogStatus.INFO, "----Tab Matched, Expected: " + expectedListOfTabs.get(i)
										+ " & Actual: " + tab + " on this Page----", YesNo.No);
							} else {

								log(LogStatus.ERROR, "----Tab Not Matched, Expected: " + expectedListOfTabs.get(i)
										+ " but Actual: " + tab + " on this Page----", YesNo.No);
								negativeResult.add("----Tab Not Matched, Expected: " + expectedListOfTabs.get(i)
										+ " but Actual: " + tab + " on this Page----");

							}

							i++;
						}
					} else {
						log(LogStatus.ERROR,
								"No. of Expected and Actual Tabs on Page not matched, So not able to continue, Expected: "
										+ expectedListOfTabs + " & Actual: " + tabsInPage,
								YesNo.Yes);
						negativeResult.add(
								"No. of Expected and Actual Tabs on Page not matched, So not able to continue, Expected: "
										+ expectedListOfTabs + " & Actual: " + tabsInPage);
					}

				}

				else {
					log(LogStatus.ERROR, "No Tabs Are Present on this Page", YesNo.Yes);
					negativeResult.add("No Tabs Are Present on this Page");
				}
			} else

			{
				log(LogStatus.ERROR, "No Expected Tabs to verify on Page Mentioned", YesNo.No);
				negativeResult.add("No Expected Tabs to verify On Page Mentioned");
			}
		} else

		{
			log(LogStatus.ERROR, "No Expected Tabs to verify on Page Mentioned", YesNo.No);
			negativeResult.add("No Expected Tabs to verify On Page Mentioned");
		}

		return negativeResult;
	}

	/**
	 * @author Ankur Huria
	 * @param SDGName
	 * @param datas
	 */

	@SuppressWarnings("unused")
	public boolean createNewRecordThroughSDG(String projectName, String sdgName, String actionButtonName,
			List<String> labels, List<String> values, List<String> typesOfFields, int timeOut) {
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		boolean flag = false;
		if (click(driver, home.listButtonOnSDG(sdgName, actionButtonName, timeOut), "Action Button " + actionButtonName,
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Button: " + actionButtonName + " on SDG: " + sdgName, YesNo.No);

			int i = 0;
			for (String label : labels) {

				if (typesOfFields.get(i).equalsIgnoreCase("SearchDropDown")) {
					String DropDownElements = "//label[text()=\"" + label
							+ "\"]/following-sibling::div//div[contains(@class,\"slds-combobox__form-element\")]/input//parent::div/following-sibling::div//li//lightning-base-combobox-item/span/following-sibling::span/span[1]/span";

					if (CommonLib.dropDownHandle(driver, home.searchDropDownBoxThroughSDG(label, 30), DropDownElements,
							"searchDropDownBoxThroughSDG", values.get(i))) {
						log(LogStatus.INFO, values.get(i) + " value has been Selected  from label: " + label, YesNo.No);
					} else {
						sa.assertTrue(false, values.get(i) + " value has not been Selected  from label: " + label);
						log(LogStatus.ERROR, values.get(i) + " value has not been Selected  from label: " + label,
								YesNo.Yes);
					}

				}

				if (typesOfFields.get(i).equalsIgnoreCase("MultiPickList")) {
					List<WebElement> rolesData = chosenOrAvailableDataForLabel(label, "Available");

					for (WebElement roledata : rolesData) {
						if (roledata.getText().equals(values.get(i))) {
							if (click(driver, roledata, values.get(i), action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Click on " + values.get(i) + " Button of Label " + label
										+ " on SDG:  " + sdgName, YesNo.No);
								CommonLib.ThreadSleep(3000);

								if (click(driver, home.moveSectionToChosenOrAvailableButton(label, "Chosen", 30),
										"MultiPickList Navigation Button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Click on MultipickList Navigation Button of Label " + label
											+ " on SDG:  " + sdgName, YesNo.No);

									List<WebElement> chosenRoleDatas = chosenOrAvailableDataForLabel(label, "Chosen");
									if (!chosenRoleDatas.isEmpty()) {
										List<String> chosenRoleDatasText = chosenRoleDatas.stream()
												.map(x -> x.getText()).collect(Collectors.toList());
										if (chosenRoleDatasText.contains(values.get(i))) {

											log(LogStatus.INFO, "Value: " + values.get(i) + " has been Chosen ",
													YesNo.Yes);

										} else {
											sa.assertTrue(false, "Value: " + values.get(i)
													+ " has not been Chosen, So Not Able to Continue to Create Record in SDG: "
													+ sdgName);
											log(LogStatus.ERROR, "Value: " + values.get(i)
													+ " has not been Chosen, So Not Able to Continue to Create Record in SDG: "
													+ sdgName, YesNo.Yes);
										}
									} else {
										sa.assertTrue(false, "Not Able to Chose the " + label + " " + values.get(i)
												+ " on SDG:  " + sdgName);
										log(LogStatus.ERROR, "Not Able to Chose the " + label + " " + values.get(i)
												+ " on SDG:  " + sdgName, YesNo.Yes);
									}

								} else {
									sa.assertTrue(false,
											"Not Able to Click on MultipickList Navigation Button of Label Role on SDG:  "
													+ sdgName);
									log(LogStatus.ERROR,
											"Not Able to Click on MultipickList Navigation Button of Label Role on SDG:  "
													+ sdgName,
											YesNo.Yes);
								}

							} else {
								sa.assertTrue(false,
										"Not Able to Click on Button Value so cannot move to Chosen of Label " + label
												+ " on SDG:  " + sdgName);
								log(LogStatus.SKIP,
										"Not Able to Click on " + values.get(i)
												+ " Button Value so cannot move to Chosen of Label " + label
												+ " on SDG:  " + sdgName,
										YesNo.Yes);
							}

						}
					}
				}

				if (typesOfFields.get(i).equalsIgnoreCase("DatePicker")) {
					if (values.get(i) != null || values.get(i) != "") {
						String[] date = values.get(i).split("/");

						if (click(driver, calendarInputBox(label, 30), label + " Input Box", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Click on " + label + " Calendar Input Box", YesNo.No);
							if (CommonLib.datePickerHandle(driver, monthInDatePicker(30),
									previousMonthButtonInDatePicker(30), label + " Picker", date[2], date[1],
									date[0])) {
								log(LogStatus.INFO, "Date has been Selected  " + values.get(i), YesNo.No);
							} else {
								sa.assertTrue(false, "Date has not been Selected  " + values.get(i));
								log(LogStatus.ERROR, "Date has not been Selected  " + values.get(i), YesNo.Yes);
							}
						} else {
							sa.assertTrue(false, "Not Able to Click on " + label + " Calendar input Box");
							log(LogStatus.ERROR, "Not Able to Click on " + label + " Calendar input Box", YesNo.Yes);
						}

					} else {
						log(LogStatus.ERROR, "No " + label + " Provided:  " + values.get(i), YesNo.Yes);
					}

				}

				if (typesOfFields.get(i).equalsIgnoreCase("DropDown")) {

					String DropDownElements = "//label[text()='" + label
							+ "']/parent::lightning-combobox//div[contains(@class,'slds-listbox_vertical')]//lightning-base-combobox-item//span[@class='slds-media__body']/span";
					if (CommonLib.dropDownHandle(driver, home.recordDropDown(label, 30), DropDownElements,
							label + " Input Box", values.get(i))) {
						log(LogStatus.INFO, values.get(i) + " value has been Selected  from label: " + label, YesNo.No);
					} else {
						sa.assertTrue(false, values.get(i) + " value has not been Selected  from label: " + label);
						log(LogStatus.ERROR, values.get(i) + " value has not been Selected  from label: " + label,
								YesNo.Yes);
					}
				}

				if (typesOfFields.get(i).equalsIgnoreCase("TextBox")) {

					if (CommonLib.sendKeys(driver, home.recordTextBox(label, 30), values.get(i), label + " Input Box",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, values.get(i) + " value has been Entered in label: " + label, YesNo.No);
					} else {
						sa.assertTrue(false, values.get(i) + " value has not been Entered in label: " + label);
						log(LogStatus.ERROR, values.get(i) + " value has not been Entered in label: " + label,
								YesNo.Yes);
					}
				}

				if (typesOfFields.get(i).equalsIgnoreCase("Checkbox")) {
					String xPath = "//span[text()='" + label + "']/../following-sibling::div//input";
					WebElement ele = CommonLib.FindElement(driver, xPath, label + " checkbox", action.SCROLLANDBOOLEAN,
							50);
					if (values.get(i).equalsIgnoreCase("select")) {
						if (!CommonLib.isSelected(driver, ele, label + " checkbox")) {
							if (CommonLib.click(driver, ele, label + " Checkbox", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, values + " checkbox has been clicked", YesNo.No);
							} else {
								sa.assertTrue(false, values + " checkbox is not clicked");
								log(LogStatus.ERROR, values + " checkbox is not clicked", YesNo.Yes);
							}
						} else {
							log(LogStatus.INFO, values + " checkbox is already selected", YesNo.No);
						}

						if (values.get(i).equalsIgnoreCase("unselect")) {
							if (CommonLib.isSelected(driver, ele, label + " checkbox")) {
								if (CommonLib.click(driver, ele, label + " Checkbox", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, values + " checkbox has been unselected", YesNo.No);
								} else {
									sa.assertTrue(false, values + " checkbox is not unselected");
									log(LogStatus.ERROR, values + " checkbox is not unselected", YesNo.Yes);
								}
							} else {
								log(LogStatus.INFO, values + " checkbox is already unselected", YesNo.No);
							}
						}
					}
				}

				i++;
			}

			if (click(driver, getRecordPageSettingSave(60), "Save Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Click on Save Button  " + sdgName, YesNo.No);
				flag = true;
				ThreadSleep(5000);

			} else {
				sa.assertTrue(false,
						"Not Able to Click on Save Button Value so cannot create Firm on SDG:  " + sdgName);
				log(LogStatus.SKIP, "Not Able to Click on Save Button Value so cannot create Firm on SDG:  " + sdgName,
						YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "Not Able to Click on Button: " + actionButtonName + " on SDG: " + sdgName, YesNo.Yes);
			sa.assertTrue(false, "Not Able to Click on Button: " + actionButtonName + " on SDG: " + sdgName);

		}
		return flag;

	}

	public ArrayList<String> verifyEditOrLockIconOnSDgRecord(String sdgName, ArrayList<String> recordName,
			ArrayList<String> iconType) {

		String xPath;
		WebElement ele;
		ArrayList<String> result = new ArrayList<String>();
		if (recordName.size() == iconType.size()) {
			for (int i = 0; i < recordName.size(); i++) {
				xPath = "//a[text()='" + sdgName + "']/ancestor::article//td[contains(@data-label,'" + recordName.get(i)
						+ "')]//span";
				ele = FindElement(driver, xPath, recordName.get(i) + " record", null, 50);
				scrollDownThroughWebelementInCenter(driver, ele, recordName.get(i) + " record");
				ThreadSleep(1500);
				if (mouseOverOperation(driver, ele)) {
					log(LogStatus.INFO, "The mouse has been hover to " + recordName.get(i), YesNo.No);
					if (iconType.get(i).equals("Lock")) {
						xPath = "//a[text()='" + sdgName + "']/ancestor::article//td[contains(@data-label,'"
								+ recordName.get(i) + "')]//button[@title='Locked']";
						ele = CommonLib.FindElement(driver, xPath, recordName.get(i), null, 20);
						if (CommonLib.checkElementVisibility(driver, ele, recordName.get(i) + " record lock icon",
								20)) {
							log(LogStatus.INFO, "Locked icon is visible on " + recordName.get(i), YesNo.No);
						} else {
							log(LogStatus.ERROR, "Locked icon is not visible on " + recordName.get(i), YesNo.No);
							result.add("Locked icon is not visible on " + recordName.get(i));
						}
					} else if (iconType.get(i).equals("Edit")) {
						xPath = "//a[text()='" + sdgName + "']/ancestor::article//td[contains(@data-label,'"
								+ recordName.get(i) + "')]//button[@title='Edit']";
						ele = CommonLib.FindElement(driver, xPath, recordName.get(i), null, 20);
						if (CommonLib.checkElementVisibility(driver, ele, recordName.get(i) + " record edit icon",
								20)) {
							log(LogStatus.INFO, "Edit icon is visible on " + recordName.get(i), YesNo.No);
						} else {
							log(LogStatus.ERROR, "Edit icon is not visible on " + recordName.get(i), YesNo.No);
							result.add("Edit icon is not visible on " + recordName.get(i));
						}
					} else {
						log(LogStatus.ERROR, "Icon type is not correct", YesNo.No);
						result.add("Icon type is not correct");
					}
				} else {
					log(LogStatus.ERROR, "Not abe to hover to " + recordName.get(i), YesNo.No);
					result.add("Not abe to hover to " + recordName.get(i));
				}
			}
		}

		return result;
	}

	/**
	 * @author Ankur Huria
	 * @param activityTimeLineTab
	 * @param labelValueAndTypes
	 */
	public boolean enterValuesForActivityTimeline(String activityTimeLineTab, String[][] labelValueAndTypes) {
		CommonLib.refresh(driver);

		int status = 0;
		int loopCount = 0;
		WebElement ele = getActivityTimelineGridOnRelatedTab(30);
		if (ele != null) {
			log(LogStatus.INFO, "Activity timeline grid is presenton on this page", YesNo.No);

			if (click(driver, activityTimelineTab(activityTimeLineTab, 10), activityTimeLineTab,
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Click on " + activityTimeLineTab + " Tab", YesNo.No);

				if (activityTimeLineTab.equalsIgnoreCase("New Task")) {
					if (click(driver, addTaskButtonInActivityTimeline(10), "New Task", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Click on Add Task Button In Activity Timeline", YesNo.No);

					} else {
						sa.assertTrue(false, "Not Able to Click on Add Task Button In Activity Timeline");
						log(LogStatus.SKIP, "Not Able to Click on Add Task Button In Activity Timeline", YesNo.Yes);
					}
				}

				for (String[] labelValueAndType : labelValueAndTypes) {

					String label = labelValueAndType[0].replace("_", " ");
					String value = labelValueAndType[1];
					String type = labelValueAndType[2];

					if (!"".equals(label) && !"".equals(value) && !"".equals(type)) {

						if (type.equalsIgnoreCase("SearchDropDown")) {

							String DropDownElements = "//label[text()='" + label
									+ "']/ancestor::div//div/lightning-base-combobox-item/span/span/ancestor::lightning-base-combobox-item";

							if (CommonLib.dropDownHandle(driver, activityTimelineSearchDropDown(label, 30),
									DropDownElements, "searchDropDownBoxThroughSDG", value)) {
								log(LogStatus.INFO, value + " value has been Selected  from label: " + label, YesNo.No);
								status++;
							} else {
								sa.assertTrue(false, value + " value has not been Selected  from label: " + label);
								log(LogStatus.ERROR, value + " value has not been Selected  from label: " + label,
										YesNo.Yes);
							}

						}

						else if (type.equalsIgnoreCase("DatePicker")) {
							if (value != "") {
								String[] date = value.split("/");

								if (click(driver, calendarInputBox(label, 30), label + " Input Box",
										action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Click on " + label + " Calendar Input Box", YesNo.No);
									if (CommonLib.datePickerHandle(driver, monthInDatePicker(30),
											previousMonthButtonInDatePicker(30), label + " Picker", date[2], date[1],
											date[0])) {
										log(LogStatus.INFO, "Date has been Selected  " + value, YesNo.No);
										status++;
									} else {
										sa.assertTrue(false, "Date has not been Selected  " + value);
										log(LogStatus.ERROR, "Date has not been Selected  " + value, YesNo.Yes);
									}
								} else {
									sa.assertTrue(false, "Not Able to Click on " + label + " Calendar input Box");
									log(LogStatus.ERROR, "Not Able to Click on " + label + " Calendar input Box",
											YesNo.Yes);
								}

							} else {
								log(LogStatus.ERROR, "No " + label + " Provided:  " + value, YesNo.Yes);
							}

						}

						else if (type.equalsIgnoreCase("DatePickerCurrentDate")) {
							value = CommonLib.getDateAccToTimeZone("GMT+5:30", "dd/MMM/yyyy");

							String[] date = value.split("/");

							if (click(driver, calendarInputBox(label, 30), label + " Input Box",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Click on " + label + " Calendar Input Box", YesNo.No);
								if (CommonLib.datePickerHandle(driver, monthInDatePicker(30),
										previousMonthButtonInDatePicker(30), label + " Picker", date[2], date[1],
										date[0])) {
									log(LogStatus.INFO, "Date has been Selected  " + value, YesNo.No);
									status++;
								} else {
									sa.assertTrue(false, "Date has not been Selected  " + value);
									log(LogStatus.ERROR, "Date has not been Selected  " + value, YesNo.Yes);
								}
							} else {
								sa.assertTrue(false, "Not Able to Click on " + label + " Calendar input Box");
								log(LogStatus.ERROR, "Not Able to Click on " + label + " Calendar input Box",
										YesNo.Yes);
							}

						}

						else if (type.equalsIgnoreCase("DatePickerFutureDate")) {
							if (value != "") {

								value = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MMM/yyyy",
										Integer.parseInt(value));
								String[] date = value.split("/");

								if (click(driver, calendarInputBox(label, 30), label + " Input Box",
										action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Click on " + label + " Calendar Input Box", YesNo.No);
									if (CommonLib.datePickerHandle(driver, monthInDatePicker(30),
											previousMonthButtonInDatePicker(30), label + " Picker", date[2], date[1],
											date[0])) {
										log(LogStatus.INFO, "Date has been Selected  " + value, YesNo.No);
										status++;
									} else {
										sa.assertTrue(false, "Date has not been Selected  " + value);
										log(LogStatus.ERROR, "Date has not been Selected  " + value, YesNo.Yes);
									}
								} else {
									sa.assertTrue(false, "Not Able to Click on " + label + " Calendar input Box");
									log(LogStatus.ERROR, "Not Able to Click on " + label + " Calendar input Box",
											YesNo.Yes);
								}

							} else {
								log(LogStatus.ERROR, "No " + label + " Provided:  " + value, YesNo.Yes);
							}

						}

						else if (type.equalsIgnoreCase("DateTimePicker")) {
							if (value != "") {
								String[] dateTime = value.split("<Split>");
								String[] date = dateTime[0].split("/");
								String time = dateTime[1];

								if (!dateTime[0].equals("")) {
									if (click(driver, activityTimelineDateElement(label, "Date", 30),
											label + " Input Box", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Click on " + label + " Calendar Input Box", YesNo.No);
										if (CommonLib.datePickerHandle(driver, monthInDatePicker(30),
												previousMonthButtonInDatePicker(30), label + " Picker", date[2],
												date[1], date[0])) {
											log(LogStatus.INFO, "Date has been Selected  " + dateTime[0], YesNo.No);
											CommonLib.ThreadSleep(2000);
											if (!dateTime[1].equals("")) {

												String DropDownElements = "//div[contains(@class,'slds-listbox')]/lightning-base-combobox-item/span/span";
												if (CommonLib.dropDownHandle(driver,
														activityTimelineTimeElement(label, "Time", 30),
														DropDownElements, label + " Input Box", time)) {
													log(LogStatus.INFO,
															time + " value has been Selected  from label: " + label,
															YesNo.No);
													status++;
												} else {
													sa.assertTrue(false, time
															+ " value has not been Selected  from label: " + label);
													log(LogStatus.ERROR,
															time + " value has not been Selected  from label: " + label,
															YesNo.Yes);
												}
											}

										} else {
											sa.assertTrue(false, "Date has not been Selected  " + dateTime[0]);
											log(LogStatus.ERROR, "Date has not been Selected  " + dateTime[0],
													YesNo.Yes);
										}
									} else {
										sa.assertTrue(false, "Not Able to Click on " + label + " Calendar input Box");
										log(LogStatus.ERROR, "Not Able to Click on " + label + " Calendar input Box",
												YesNo.Yes);
									}
								}

							} else {
								log(LogStatus.ERROR, "No " + label + " Provided:  " + value, YesNo.Yes);
							}

						}

						else if (type.equalsIgnoreCase("DateTimePickerCurrentDate")) {
							if (value != "") {
								String[] dateTime = value.split("<Split>");

								String currentDate = CommonLib.getDateAccToTimeZone("GMT+5:30", "dd/MMM/yyyy");
								String[] date = currentDate.split("/");
								String time = dateTime[1];

								if (!dateTime[0].equals("")) {
									if (click(driver, activityTimelineDateElement(label, "Date", 30),
											label + " Input Box", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Click on " + label + " Calendar Input Box", YesNo.No);
										if (CommonLib.datePickerHandle(driver, monthInDatePicker(30),
												previousMonthButtonInDatePicker(30), label + " Picker", date[2],
												date[1], date[0])) {
											log(LogStatus.INFO, "Date has been Selected  " + dateTime[0], YesNo.No);
											CommonLib.ThreadSleep(2000);
											if (!dateTime[1].equals("")) {

												String DropDownElements = "//div[contains(@class,'slds-listbox')]/lightning-base-combobox-item/span/span";
												if (CommonLib.dropDownHandle(driver,
														activityTimelineTimeElement(label, "Time", 30),
														DropDownElements, label + " Input Box", time)) {
													log(LogStatus.INFO,
															time + " value has been Selected  from label: " + label,
															YesNo.No);
													status++;
												} else {
													sa.assertTrue(false, time
															+ " value has not been Selected  from label: " + label);
													log(LogStatus.ERROR,
															time + " value has not been Selected  from label: " + label,
															YesNo.Yes);
												}
											}

										} else {
											sa.assertTrue(false, "Date has not been Selected  " + dateTime[0]);
											log(LogStatus.ERROR, "Date has not been Selected  " + dateTime[0],
													YesNo.Yes);
										}
									} else {
										sa.assertTrue(false, "Not Able to Click on " + label + " Calendar input Box");
										log(LogStatus.ERROR, "Not Able to Click on " + label + " Calendar input Box",
												YesNo.Yes);
									}
								}

							} else {
								log(LogStatus.ERROR, "No " + label + " Provided:  " + value, YesNo.Yes);
							}

						}

						else if (type.equalsIgnoreCase("DateTimePickerFutureDate")) {
							if (value != "") {
								String[] dateTime = value.split("<Split>");

								String currentDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MMM/yyyy",
										Integer.parseInt(value));
								String[] date = currentDate.split("/");
								String time = dateTime[1];

								if (!dateTime[0].equals("")) {
									if (click(driver, activityTimelineDateElement(label, "Date", 30),
											label + " Input Box", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Click on " + label + " Calendar Input Box", YesNo.No);
										if (CommonLib.datePickerHandle(driver, monthInDatePicker(30),
												previousMonthButtonInDatePicker(30), label + " Picker", date[2],
												date[1], date[0])) {
											log(LogStatus.INFO, "Date has been Selected  " + dateTime[0], YesNo.No);
											CommonLib.ThreadSleep(2000);
											if (!dateTime[1].equals("")) {

												String DropDownElements = "//div[contains(@class,'slds-listbox')]/lightning-base-combobox-item/span/span";
												if (CommonLib.dropDownHandle(driver,
														activityTimelineTimeElement(label, "Time", 30),
														DropDownElements, label + " Input Box", time)) {
													log(LogStatus.INFO,
															time + " value has been Selected  from label: " + label,
															YesNo.No);
													status++;
												} else {
													sa.assertTrue(false, time
															+ " value has not been Selected  from label: " + label);
													log(LogStatus.ERROR,
															time + " value has not been Selected  from label: " + label,
															YesNo.Yes);
												}
											}

										} else {
											sa.assertTrue(false, "Date has not been Selected  " + dateTime[0]);
											log(LogStatus.ERROR, "Date has not been Selected  " + dateTime[0],
													YesNo.Yes);
										}
									} else {
										sa.assertTrue(false, "Not Able to Click on " + label + " Calendar input Box");
										log(LogStatus.ERROR, "Not Able to Click on " + label + " Calendar input Box",
												YesNo.Yes);
									}
								}

							} else {
								log(LogStatus.ERROR, "No " + label + " Provided:  " + value, YesNo.Yes);
							}

						}

						else if (type.equalsIgnoreCase("DropDown")) {

							String DropDownElements = "//div[@class='select-options']/ul/li[contains(@class,'uiMenuItem')]/a";
							if (CommonLib.dropDownHandle(driver, activityTimelineDropDownElement(label, 30),
									DropDownElements, label + " Input Box", value)) {
								log(LogStatus.INFO, value + " value has been Selected  from label: " + label, YesNo.No);
								status++;
							} else {
								sa.assertTrue(false, value + " value has not been Selected  from label: " + label);
								log(LogStatus.ERROR, value + " value has not been Selected  from label: " + label,
										YesNo.Yes);
							}
						}

						else if (type.equalsIgnoreCase("DoubleDropDown")) {

							String val[] = value.split("<Split>");
							String DropDownElements = "//div[@class='entityMenuList']/ul/li/a/span";
							if (CommonLib.dropDownHandle(driver,
									activityTimelineSmallDropDownInDoubleDropDown(activityTimeLineTab, label, 30),
									DropDownElements, label + " Input Box", val[0])) {
								log(LogStatus.INFO, val[0] + " value has been Selected  from label: " + label,
										YesNo.No);

								CommonLib.ThreadSleep(2000);

								if (activityTimeLineTab.equalsIgnoreCase("Email")) {
									String searchDropDownListInNewRecordFormInCaseOfEmail = "//span[text()='" + label
											+ "']/parent::label/following::div//div[contains(@class,'listContent')]/ul/li[not(contains(@class,'invisible'))]//div[contains(@class,'primaryLabel')]";
									if (CommonLib.dropDownHandle(driver,
											activityTimelineSearchDropDownInDoubleDropDown(activityTimeLineTab, label,
													30),
											searchDropDownListInNewRecordFormInCaseOfEmail, label + " Input Box",
											val[1])) {
										log(LogStatus.INFO, val[1] + " value has been Selected  from label: " + label,
												YesNo.No);
										status++;
										CommonLib.ThreadSleep(2000);

									} else {
										sa.assertTrue(false,
												val[1] + " value has not been Selected  from label: " + label);
										log(LogStatus.ERROR,
												val[1] + " value has not been Selected  from label: " + label,
												YesNo.Yes);
									}
								} else {

									if (CommonLib.dropDownHandle(driver,
											activityTimelineSearchDropDownInDoubleDropDown(activityTimeLineTab, label,
													30),
											searchDropDownListInNewRecordForm(label), label + " Input Box", val[1])) {
										log(LogStatus.INFO, val[1] + " value has been Selected  from label: " + label,
												YesNo.No);
										status++;
										CommonLib.ThreadSleep(2000);

									} else {
										sa.assertTrue(false,
												val[1] + " value has not been Selected  from label: " + label);
										log(LogStatus.ERROR,
												val[1] + " value has not been Selected  from label: " + label,
												YesNo.Yes);
									}
								}

							} else {
								sa.assertTrue(false, val[0] + " value has not been Selected  from label: " + label);
								log(LogStatus.ERROR, val[0] + " value has not been Selected  from label: " + label,
										YesNo.Yes);
							}
						}

						else if (type.equalsIgnoreCase("SearchDropDownTextBox")) {

							activityTimelineSearchDropDown(label, 30)
									.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
							if (CommonLib.sendKeys(driver, activityTimelineSearchDropDown(label, 30), value,
									label + " Input Box", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, value + " value has been Entered in label: " + label, YesNo.No);
								status++;
							} else {
								sa.assertTrue(false, value + " value has not been Entered in label: " + label);
								log(LogStatus.ERROR, value + " value has not been Entered in label: " + label,
										YesNo.Yes);
							}
						}

						else if (type.equalsIgnoreCase("TextBox")) {
							if (label.equals("To")) {
								if (CommonLib.sendKeys(driver, activityTimelineToTextBoxElement(label, 30), value,
										label + " Input Box", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, value + " value has been Entered in label: " + label, YesNo.No);
									status++;
								} else {
									sa.assertTrue(false, value + " value has not been Entered in label: " + label);
									log(LogStatus.ERROR, value + " value has not been Entered in label: " + label,
											YesNo.Yes);
								}
							}

							else if (label.equals("Body")) {
								if (CommonLib.switchToFrame(driver, 50, getckEditorIframe(50))) {

									if (CommonLib.switchToFrame(driver, 50, getemailBodyIframe(50))) {
										log(LogStatus.INFO, "Switched to email body iframe", YesNo.No);
										if (CommonLib.sendKeys(driver, activityTimelineBodyTextBoxElement(label, 30),
												value, label + " Input Box", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, value + " value has been Entered in label: " + label,
													YesNo.No);
											status++;
										} else {
											sa.assertTrue(false,
													value + " value has not been Entered in label: " + label);
											log(LogStatus.ERROR,
													value + " value has not been Entered in label: " + label,
													YesNo.Yes);
										}

									} else {
										sa.assertTrue(false, "Not able to switch to email body Iframe");
										log(LogStatus.ERROR, "Not able to switch to email body Iframe", YesNo.Yes);
									}
								} else {

									sa.assertTrue(false, "Not able to switch to ck editor Iframe");
									log(LogStatus.ERROR, "Not able to switch to ck editor Iframe", YesNo.Yes);

								}
								CommonLib.switchToDefaultContent(driver);
							}

							else {

								if (CommonLib.sendKeys(driver, activityTimelineTextBoxElement(label, 30), value,
										label + " Input Box", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, value + " value has been Entered in label: " + label, YesNo.No);
									status++;
								} else {
									sa.assertTrue(false, value + " value has not been Entered in label: " + label);
									log(LogStatus.ERROR, value + " value has not been Entered in label: " + label,
											YesNo.Yes);
								}
							}
						}

						else {
							sa.assertTrue(false,
									"Please Enter Valid Type of Element, So Not Able to Enter details for It, Label: "
											+ label + " ,Value: " + value + " & Type: " + type);
							log(LogStatus.SKIP,
									"Either One of Them Data is Empty, So Not Able to Enter details for It, Label: "
											+ label + " ,Value: " + value + " & Type: " + type,
									YesNo.Yes);
						}

					}

					else {
						sa.assertTrue(false,
								"Either One of Them Data is Empty, So Not Able to Enter details for It, Label: " + label
										+ " ,Value: " + value + " & Type: " + type);
						log(LogStatus.SKIP,
								"Either One of Them Data is Empty, So Not Able to Enter details for It, Label: " + label
										+ " ,Value: " + value + " & Type: " + type,
								YesNo.Yes);
					}

					loopCount++;
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on " + activityTimeLineTab + " Tab");
				log(LogStatus.SKIP, "Not Able to Click on " + activityTimeLineTab + " Tab", YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Activity timeline grid is not present on this page");
			log(LogStatus.SKIP, "Activity timeline grid is not present on this page", YesNo.Yes);
		}

		if (status == loopCount) {

			if (activityTimeLineTab.equalsIgnoreCase("Email")) {

				if (clickUsingJavaScript(driver, sendButtonInActivityTimeline(10), "Send Button",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Click on Send Button", YesNo.No);

					if (emailSentMsg(30) != null) {

						log(LogStatus.INFO, "Verified Email Sent Msg", YesNo.No);
						return true;
					} else {

						sa.assertTrue(false, "Email Sent Msg not Verified");
						log(LogStatus.SKIP, "Email Sent Msg not Verified", YesNo.Yes);
						return false;
					}

				} else {
					sa.assertTrue(false, "Not Able to Click on Send Button");
					log(LogStatus.SKIP, "Not Able to Click on Send Button", YesNo.Yes);
					return false;
				}

			} else {

				if (clickUsingJavaScript(driver, getCustomTabSaveBtn(5), "Save Button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Click on Save Button", YesNo.No);
					WebElement msg = ActivityTimeLineCreatedMsg(30);
					if (msg != null) {
						if (msg.getText().contains("was created.")) {
							log(LogStatus.INFO, "Created Msg Verified for :" + activityTimeLineTab, YesNo.No);
							return true;
						} else {

							sa.assertTrue(false, "Created Msg not Verified for :" + activityTimeLineTab);
							log(LogStatus.SKIP, "Created Msg not Verified for :" + activityTimeLineTab, YesNo.Yes);
							return false;
						}

					} else {
						sa.assertTrue(false, "Created Msg not Verified for :" + activityTimeLineTab);
						log(LogStatus.SKIP, "Created Msg not Verified for :" + activityTimeLineTab, YesNo.Yes);
						return false;
					}

				} else {
					sa.assertTrue(false, "Not Able to Click on Save Button");
					log(LogStatus.SKIP, "Not Able to Click on Save Button", YesNo.Yes);
					return false;
				}
			}

		}

		else
			return false;
	}

	public boolean verifyActivityRecord(String recordActivityTitle) {
		String xPath;
		List<WebElement> elements;
		boolean flag = false;
		ArrayList<String> expectedUpcomingAndOverdueRecord = new ArrayList<String>();
		ArrayList<String> expectedThisMonthRecord = new ArrayList<String>();
		ArrayList<String> actualUpcomingAndOverdueRecord = new ArrayList<String>();
		ArrayList<String> actualThisMonthRecord = new ArrayList<String>();

		String[] recordAndActivityTitle = recordActivityTitle.split("<Break>");
		for (int i = 0; i < recordAndActivityTitle.length; i++) {
			String activityTitle = recordAndActivityTitle[i].split("<Br>")[1];
			if (activityTitle.equals("UpcomingAndOverdue")) {

				expectedUpcomingAndOverdueRecord.add(recordAndActivityTitle[i].split("<Br>")[0]);
			} else if (activityTitle.equals("ThisMonth")) {
				expectedThisMonthRecord.add(recordAndActivityTitle[i].split("<Br>")[0]);
			} else {
				log(LogStatus.ERROR, "Activity titles are not correct", YesNo.No);
				return false;
			}
		}
		// for Upcoming and Overdue recrd
		xPath = "//button[text()='Upcoming & Overdue']/ancestor::div[contains(@class,'open-activity-group')]//a[@rel='noreferrer']";
		elements = CommonLib.FindElements(driver, xPath, "Upcoming and overdue record");
		for (WebElement ele : elements) {
			actualUpcomingAndOverdueRecord
					.add(CommonLib.getText(driver, ele, "Upcoming and overdue record", action.SCROLLANDBOOLEAN));
		}
		// for This Month
		xPath = "//span[text()='This Month']/ancestor::div[contains(@class,'past-activity-group')]//a[@rel='noreferrer']";
		elements = CommonLib.FindElements(driver, xPath, "This Month record");
		for (WebElement ele : elements) {
			actualThisMonthRecord.add(CommonLib.getText(driver, ele, "This Month record", action.SCROLLANDBOOLEAN));
		}

		if (actualUpcomingAndOverdueRecord.containsAll(actualUpcomingAndOverdueRecord)
				&& actualThisMonthRecord.containsAll(expectedThisMonthRecord)) {
			log(LogStatus.INFO,
					"Record on the Upcoming & Overdue and This month has been verified Upcoming & Overdue : "
							+ actualUpcomingAndOverdueRecord + " This Month : " + actualThisMonthRecord,
					YesNo.No);
			flag = true;
		} else {
			log(LogStatus.ERROR,
					"Record on the Upcoming & Overdue and This month are not verified Upcoming & Overdue : "
							+ actualUpcomingAndOverdueRecord + " This Month : " + actualThisMonthRecord,
					YesNo.No);

		}
		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param SDGGridName
	 */

	public void verifyColumnAscendingDescendingOrder(String sdgGridName, List<String> columnNames,
			List<String> dateColumns, List<String> amountColumns, String FirstColumnAscYesOrNoByDefault) {

		List<WebElement> headerList = sdgGridAllHeadersLabelNameList(sdgGridName);
		List<String> columnDataText = headerList.stream().map(s -> s.getText()).collect(Collectors.toList()).stream()
				.map(t -> t.trim()).collect(Collectors.toList());
		if (!headerList.isEmpty()) {
			int i = 0;

			if (FirstColumnAscYesOrNoByDefault.equalsIgnoreCase("Yes")) {
				if (clickUsingJavaScript(driver, headerList.get(2), sdgGridName + " SDG Grid header column",
						action.SCROLLANDBOOLEAN)) {
					ThreadSleep(6000);
				} else {
					log(LogStatus.PASS, "Not able to click on First Column of SDG: " + sdgGridName, YesNo.Yes);
					sa.assertTrue(false, "Not able to click on First Column of SDG: " + sdgGridName);
				}

			}

			for (String columnName : columnNames) {
				int columnIndex = columnDataText.indexOf(columnName);

				if (clickUsingJavaScript(driver, headerList.get(columnIndex),
						sdgGridName.toString() + " SDG Grid header column " + columnName, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.PASS, "Clicked on Header " + columnName + " Column no. " + (columnIndex + 1) + " for "
							+ SortOrder.Assecending, YesNo.No);
					ThreadSleep(6000);

					if (!dateColumns.contains(columnName)) {

						if (!amountColumns.contains(columnName)) {

							if (CommonLib.checkSorting(driver, SortOrder.Assecending,
									sdgGridColumnsDataList(sdgGridName.toString(), columnIndex + 1))) {
								log(LogStatus.PASS, "Verified " + SortOrder.Assecending + " Sorting on SDG: "
										+ sdgGridName.toString() + " for Column " + columnName, YesNo.No);
							} else {
								log(LogStatus.FAIL, SortOrder.Assecending + " Sorting not working on SDG: "
										+ sdgGridName.toString() + " for Column " + columnName, YesNo.No);
								sa.assertTrue(false, SortOrder.Assecending + " Sorting not working on SDG: "
										+ sdgGridName.toString() + " for Column " + columnName);
							}
						} else {

							List<Integer> expectedAmount = new ArrayList<Integer>();
							List<String> actualAmount = new ArrayList<String>();
							List<Integer> sortedActualAmount = new ArrayList<Integer>();
							List<WebElement> actualDateListWebElement = sdgGridColumnsDataList(sdgGridName.toString(),
									columnIndex + 1);
							actualAmount = actualDateListWebElement.stream().map(date -> date.getText())
									.collect(Collectors.toList()).stream().filter(x -> !x.equals(""))
									.collect(Collectors.toList());

							for (String val : actualAmount) {
								String[] splitedAmount = val.split("[.]", 0);
								String amount = splitedAmount[0].replace("$", "").replace(",", "");
								int amou = Integer.parseInt(amount);
								sortedActualAmount.add(amou);
							}

							expectedAmount = amountToAscendingOrder(actualDateListWebElement);

							if (sortedActualAmount.equals(expectedAmount)) {
								log(LogStatus.PASS, "Verified " + SortOrder.Assecending + " Sorting on SDG: "
										+ sdgGridName.toString() + " for Column " + columnName, YesNo.No);
							} else {
								log(LogStatus.FAIL, SortOrder.Assecending + " Sorting not working on SDG: "
										+ sdgGridName.toString() + " for Column " + columnName, YesNo.No);
								sa.assertTrue(false, SortOrder.Assecending + " Sorting not working on SDG: "
										+ sdgGridName.toString() + " for Column " + columnName);
							}

						}

					}

					else {
						List<String> expectedDateListText = new ArrayList<String>();
						List<String> actualDateListText = new ArrayList<String>();
						List<WebElement> actualDateListWebElement = sdgGridColumnsDataList(sdgGridName.toString(),
								columnIndex + 1);
						actualDateListText = actualDateListWebElement.stream().map(date -> date.getText())
								.collect(Collectors.toList()).stream().filter(x -> !x.equals(""))
								.collect(Collectors.toList());
						expectedDateListText = dateToAscendingOrder(actualDateListWebElement);

						if (actualDateListText.equals(expectedDateListText)) {
							log(LogStatus.PASS, "Verified " + SortOrder.Assecending + " Sorting on SDG: "
									+ sdgGridName.toString() + " for Column " + columnName, YesNo.No);
						} else {
							log(LogStatus.FAIL, SortOrder.Assecending + " Sorting not working on SDG: "
									+ sdgGridName.toString() + " for Column " + columnName, YesNo.No);
							sa.assertTrue(false, SortOrder.Assecending + " Sorting not working on SDG: "
									+ sdgGridName.toString() + " for Column " + columnName);
						}

					}

				} else {
					log(LogStatus.PASS, "Not able to click on " + sdgGridName.toString() + " SDG Grid header "
							+ columnName + " so cannot check Sorting " + SortOrder.Assecending, YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + sdgGridName.toString() + " SDG Grid header "
							+ columnName + " so cannot check Sorting " + SortOrder.Assecending);
				}
				headerList = sdgGridAllHeadersLabelNameList(sdgGridName);

				if (clickUsingJavaScript(driver, headerList.get(columnIndex),
						sdgGridName.toString() + " SDG Grid header column", action.SCROLLANDBOOLEAN)) {
					ThreadSleep(6000);
					log(LogStatus.PASS,
							"Clicked on Header " + columnName + " Clomun " + (columnIndex + 1) + SortOrder.Decending,
							YesNo.No);

					if (!dateColumns.contains(columnName)) {
						if (!amountColumns.contains(columnName)) {

							if (CommonLib.checkSorting(driver, SortOrder.Decending,
									sdgGridColumnsDataList(sdgGridName.toString(), columnIndex + 1))) {
								log(LogStatus.PASS, "Verified " + SortOrder.Decending + " Sorting on SDG: "
										+ sdgGridName.toString() + " for Column " + columnName, YesNo.No);
							} else {
								log(LogStatus.FAIL, SortOrder.Decending + " Sorting not working on SDG: "
										+ sdgGridName.toString() + " for Column " + columnName, YesNo.No);
								sa.assertTrue(false, SortOrder.Decending + " Sorting not working on SDG: "
										+ sdgGridName.toString() + " for Column " + columnName);
							}
						} else {

							List<Integer> expectedAmount = new ArrayList<Integer>();
							List<String> actualAmount = new ArrayList<String>();
							List<Integer> sortedActualAmount = new ArrayList<Integer>();
							List<WebElement> actualDateListWebElement = sdgGridColumnsDataList(sdgGridName.toString(),
									columnIndex + 1);
							actualAmount = actualDateListWebElement.stream().map(date -> date.getText())
									.collect(Collectors.toList()).stream().filter(x -> !x.equals(""))
									.collect(Collectors.toList());

							for (String val : actualAmount) {
								String[] splitedAmount = val.split("[.]", 0);
								String amount = splitedAmount[0].replace("$", "").replace(",", "");
								int amou = Integer.parseInt(amount);
								sortedActualAmount.add(amou);
							}

							expectedAmount = amountToDescendingOrder(actualDateListWebElement);

							if (sortedActualAmount.equals(expectedAmount)) {
								log(LogStatus.PASS, "Verified " + SortOrder.Decending + " Sorting on SDG: "
										+ sdgGridName.toString() + " for Column " + columnName, YesNo.No);
							} else {
								log(LogStatus.FAIL, SortOrder.Decending + " Sorting not working on SDG: "
										+ sdgGridName.toString() + " for Column " + columnName, YesNo.No);
								sa.assertTrue(false, SortOrder.Decending + " Sorting not working on SDG: "
										+ sdgGridName.toString() + " for Column " + columnName);
							}

						}

					}

					else {
						List<String> expectedDateListText = new ArrayList<String>();
						List<String> actualDateListText = new ArrayList<String>();
						List<WebElement> actualDateListWebElement = sdgGridColumnsDataList(sdgGridName.toString(),
								columnIndex + 1);
						actualDateListText = actualDateListWebElement.stream().map(date -> date.getText())
								.collect(Collectors.toList()).stream().filter(x -> !x.equals(""))
								.collect(Collectors.toList());
						expectedDateListText = dateToDescendingOrder(actualDateListWebElement);

						if (actualDateListText.equals(expectedDateListText)) {
							log(LogStatus.PASS, "Verified " + SortOrder.Decending + " Sorting on SDG: "
									+ sdgGridName.toString() + " for Column " + columnName, YesNo.No);
						} else {
							log(LogStatus.FAIL, SortOrder.Decending + " Sorting not working on SDG: "
									+ sdgGridName.toString() + " for Column " + columnName, YesNo.No);
							sa.assertTrue(false, SortOrder.Decending + " Sorting not working on SDG: "
									+ sdgGridName.toString() + " for Column " + columnName);
						}

					}
				} else {
					log(LogStatus.PASS, "Not able to click on " + sdgGridName.toString() + " SDG Grid header "
							+ columnName + " so cannot check Sorting " + SortOrder.Decending, YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + sdgGridName.toString() + " SDG Grid header "
							+ columnName + " so cannot check Sorting " + SortOrder.Decending);
				}

			}
		} else {
			log(LogStatus.PASS,
					sdgGridName.toString() + " SDG Grid header cloumns list is not visible so cannot check Sorting ",
					YesNo.Yes);
			sa.assertTrue(false,
					sdgGridName.toString() + " SDG Grid header cloumns list is not visible so cannot check Sorting ");
		}
	}

	/**
	 * @author Ankur Huria
	 * @param SDGName
	 * @param datas
	 */
	public List<String> dateToAscendingOrder(List<WebElement> datesListElements) {
		List<String> datesList = new ArrayList<String>();
		datesList = datesListElements.stream().map(date -> date.getText()).collect(Collectors.toList());
		datesList = datesList.stream().filter(s -> !s.equals("")).collect(Collectors.toList());
		List<String> dateResolved = new ArrayList<String>();
		List<String> dateStringResult = new ArrayList<String>();
		for (String dates : datesList) {

			String[] dateArray = dates.split("/");
			int monthLength = dateArray[0].length();
			if (monthLength == 1) {
				dates = "0" + dates;
			}

			dateResolved.add(dates);

		}
		/* Sorting the ArrayList using Collections.sort() method */

		List<Date> pureDatesList = dateResolved.stream().filter(s -> !s.equals("")).map(date -> {
			try {

				return new SimpleDateFormat("MM/dd/yyyy").parse(date);

			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

		Collections.sort(pureDatesList, new Comparator<Date>() {
			@Override
			public int compare(Date lhs, Date rhs) {
				if (lhs.getTime() < rhs.getTime())
					return -1;
				else if (lhs.getTime() == rhs.getTime())
					return 0;
				else
					return 1;
			}
		});

		List<String> pureDatesListInFormat = pureDatesList.stream().map(date -> formatter.format(date))
				.collect(Collectors.toList());
		for (String dates : pureDatesListInFormat) {

			String[] dateArray = dates.split("/");
			String[] monthArray = dateArray[0].split("");
			String[] dayArray = dateArray[1].split("");
			String month;
			month = dateArray[0];
			if (monthArray[0].equals("0")) {
				month = dateArray[0].replace("0", "");
				dates = month + "/" + dateArray[1] + "/" + dateArray[2];

			}
			if (dayArray[0].equals("0")) {
				String day = dateArray[1].replace("0", "");
				dates = month + "/" + day + "/" + dateArray[2];
			}

			dateStringResult.add(dates);

		}

		int i = 0;
		for (String expectedDates : datesList) {

			if (expectedDates.equals(dateStringResult.get(i))) {
				appLog.info("Order of column is matched " + "Expected: " + expectedDates + "\tActual: "
						+ dateStringResult.get(i));
			} else {
				appLog.info("Order of column din't match. " + "Expected: " + expectedDates + "\tActual: "
						+ dateStringResult.get(i));
				BaseLib.sa.assertTrue(false, "Order of column din't match. " + "Expected: " + expectedDates
						+ "\tActual: " + dateStringResult.get(i));

			}

			i++;
		}
		return dateStringResult;
	}

	public List<String> dateToDescendingOrder(List<WebElement> datesListElements) {
		List<String> datesList = new ArrayList<String>();
		datesList = datesListElements.stream().map(date -> date.getText()).collect(Collectors.toList());
		datesList = datesList.stream().filter(s -> !s.equals("")).collect(Collectors.toList());
		List<String> dateResolved = new ArrayList<String>();
		List<String> dateStringResult = new ArrayList<String>();
		for (String dates : datesList) {

			String[] dateArray = dates.split("/");
			int monthLength = dateArray[0].length();
			if (monthLength == 1) {
				dates = "0" + dates;
			}

			dateResolved.add(dates);

		}
		/* Sorting the ArrayList using Collections.sort() method */

		List<Date> pureDatesList = dateResolved.stream().filter(s -> !s.equals("")).map(date -> {
			try {

				return new SimpleDateFormat("MM/dd/yyyy").parse(date);

			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

		Collections.sort(pureDatesList, new Comparator<Date>() {
			@Override
			public int compare(Date lhs, Date rhs) {
				if (lhs.getTime() < rhs.getTime())
					return 1;
				else if (lhs.getTime() == rhs.getTime())
					return 0;
				else
					return -1;
			}
		});

		List<String> pureDatesListInFormat = pureDatesList.stream().map(date -> formatter.format(date))
				.collect(Collectors.toList());
		for (String dates : pureDatesListInFormat) {

			String[] dateArray = dates.split("/");
			String[] monthArray = dateArray[0].split("");
			String[] dayArray = dateArray[1].split("");
			String month;
			month = dateArray[0];
			if (monthArray[0].equals("0")) {
				month = dateArray[0].replace("0", "");
				dates = month + "/" + dateArray[1] + "/" + dateArray[2];

			}
			if (dayArray[0].equals("0")) {
				String day = dateArray[1].replace("0", "");
				dates = month + "/" + day + "/" + dateArray[2];
			}

			dateStringResult.add(dates);

		}

		int i = 0;
		for (String expectedDates : datesList) {

			if (expectedDates.equals(dateStringResult.get(i))) {
				appLog.info("Order of column is matched " + "Expected: " + expectedDates + "\tActual: "
						+ dateStringResult.get(i));
			} else {
				appLog.info("Order of column din't match. " + "Expected: " + expectedDates + "\tActual: "
						+ dateStringResult.get(i));
				BaseLib.sa.assertTrue(false, "Order of column din't match. " + "Expected: " + expectedDates
						+ "\tActual: " + dateStringResult.get(i));

			}

			i++;
		}
		return dateStringResult;
	}

	public List<Integer> amountToAscendingOrder(List<WebElement> amountListElements) {

		List<String> expectedAmount = new ArrayList<String>();
		List<Integer> sortedExpectedAmount = new ArrayList<Integer>();

		expectedAmount = amountListElements.stream().map(date -> date.getText()).collect(Collectors.toList()).stream()
				.filter(x -> !x.equals("")).collect(Collectors.toList());

		for (String val : expectedAmount) {
			String[] splitedAmount = val.split("[.]", 0);
			String amount = splitedAmount[0].replace("$", "").replace(",", "");
			int amou = Integer.parseInt(amount);

			sortedExpectedAmount.add(amou);
		}
		Collections.sort(sortedExpectedAmount);
		return sortedExpectedAmount;
	}

	public List<Integer> amountToDescendingOrder(List<WebElement> amountListElements) {

		List<String> expectedAmount = new ArrayList<String>();
		List<Integer> sortedExpectedAmount = new ArrayList<Integer>();
		expectedAmount = amountListElements.stream().map(date -> date.getText()).collect(Collectors.toList()).stream()
				.filter(x -> !x.equals("")).collect(Collectors.toList());

		for (String val : expectedAmount) {
			String[] splitedAmount = val.split("[.]", 0);
			String amount = splitedAmount[0].replace("$", "").replace(",", "");
			int amou = Integer.parseInt(amount);

			sortedExpectedAmount.add(amou);
		}
		Collections.sort(sortedExpectedAmount, Collections.reverseOrder());
		return sortedExpectedAmount;
	}

	/**
	 * @author Ankur Huria
	 * @param expectedListOfSectionHeaders
	 * @return negativeResult
	 */
	public List<String> verifyRecordLayoutSectionHeaderLabels(String expectedListOfSectionHeaderLabels) {

		List<String> negativeResult = new ArrayList<String>();

		if (!expectedListOfSectionHeaderLabels.isEmpty()) {

			String[] expectedListOfSectionHeaderLabel = expectedListOfSectionHeaderLabels.split("<Break>", -1);

			for (int i = 0; i < expectedListOfSectionHeaderLabel.length; i++) {
				String[] expectListOfSectionHeaderLabel = expectedListOfSectionHeaderLabel[i].split("<Header&Labels>",
						-1);
				String expectedHeader = expectListOfSectionHeaderLabel[0];
				ArrayList<String> expectedLabels = new ArrayList<String>();
				for (int k = 1; k < expectListOfSectionHeaderLabel.length; k++) {
					expectedLabels.add(expectListOfSectionHeaderLabel[k]);
				}

				List<String> recordLayoutSectionHeaderLabels = recordLayoutSectionHeaderLabels(expectedHeader).stream()
						.map(x -> x.getText().trim()).collect(Collectors.toList());

				if (!recordLayoutSectionHeaderLabels.isEmpty()) {
					log(LogStatus.INFO, "No. of Section Header Labels of Section " + expectedHeader
							+ " Present on Page are: " + recordLayoutSectionHeaderLabels.size(), YesNo.No);

					int j = 0;
					if (recordLayoutSectionHeaderLabels.size() == expectedLabels.size()) {
						log(LogStatus.INFO, "No. of Actual and Expected Section Labels of section " + expectedHeader
								+ " on Page are same, So Continue the Process", YesNo.No);

						for (String recordLayoutSectionHeaderLabel : recordLayoutSectionHeaderLabels) {
							if (recordLayoutSectionHeaderLabel.equals(expectedLabels.get(j))) {
								log(LogStatus.INFO,
										"----Header's Label Matched, Expected: " + expectedLabels.get(j) + " & Actual: "
												+ recordLayoutSectionHeaderLabel + " for  section: " + expectedHeader
												+ " on this Page----",
										YesNo.No);
							} else {

								log(LogStatus.ERROR,
										"----Header's Label Not Matched, Expected: " + expectedLabels.get(j)
												+ " & Actual: " + recordLayoutSectionHeaderLabel + " for  section: "
												+ expectedHeader + " on this Page----",
										YesNo.No);
								negativeResult.add("----Header's Label Not Matched, Expected: " + expectedLabels.get(j)
										+ " & Actual: " + recordLayoutSectionHeaderLabel + " for  section: "
										+ expectedHeader + " on this Page----");

							}

							j++;
						}
					} else {
						log(LogStatus.ERROR,
								"No. of Expected and Actual Section Header Labels of Section " + expectedHeader
										+ " on Page not matched, So not able to continue, Expected: " + expectedLabels
										+ " & Actual: " + recordLayoutSectionHeaderLabels,
								YesNo.Yes);
						negativeResult.add("No. of Expected and Actual Section Header Labels of Section "
								+ expectedHeader + " on Page not matched, So not able to continue, Expected: "
								+ expectedLabels + " & Actual: " + recordLayoutSectionHeaderLabels);
					}

				}

				else {
					log(LogStatus.ERROR, "No Section Header Labels of Section " + expectedHeader + " Present on Page",
							YesNo.Yes);
					negativeResult.add("No Section Header Labels of Section " + expectedHeader + " Present on Page");
				}
			}

		} else

		{
			log(LogStatus.ERROR, "No Expected Section Headers to verify on Page Mentioned", YesNo.No);
			negativeResult.add("No Expected Section Headers to verify On Page Mentioned");
		}

		return negativeResult;
	}

	/**
	 * @author Ankur Huria
	 * @param tabName
	 * @param sdgName
	 * @return result
	 */
	public boolean verifySDGGridInPageTab(String tabName, String sdgName) {

		CommonLib.refresh(driver);
		CommonLib.ThreadSleep(5000);
		boolean flag = false;
		if (click(driver, getTabInPage(tabName, 40), "Tab Name: " + tabName, action.SCROLLANDBOOLEAN)) {

			log(LogStatus.INFO, "Clicked on Tab: " + tabName, YesNo.No);
			if (getSDGName(sdgName, 40) != null) {
				log(LogStatus.INFO, "SDG Present: " + sdgName, YesNo.No);
				flag = true;
			} else {
				log(LogStatus.ERROR, "SDG Not Present: " + sdgName, YesNo.No);
				sa.assertTrue(false, "SDG Not Present: " + sdgName);
			}

		} else {
			log(LogStatus.ERROR, "Not Able to Click on Tab: " + tabName, YesNo.Yes);
			sa.assertTrue(false, "Not Able to Click on Tab: " + tabName);
		}

		return flag;

	}

	/**
	 * @author Ankur Huria
	 * @param projectName
	 * @param labelWithValues
	 * @param action
	 * @param timeOut
	 * @return true if able to enter value for SDG creation
	 */
	@SuppressWarnings("unused")
	public boolean enterDetailsForRecord(String projectName, String[][] labelValueAndTypeOfElement, action action,
			int timeOut) {
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		boolean flag = false;
		String label = "";
		String value = "";
		String typeOfElement = "";
		int status = 0;
		WebElement ele = null;
		int loopCount = 0;
		for (String[] labelValues : labelValueAndTypeOfElement) {
			label = labelValues[0].replace("_", " ");
			value = labelValues[1];
			typeOfElement = labelValues[2];

			if (typeOfElement.equalsIgnoreCase("DropDown")) {
				CommonLib.ThreadSleep(3000);
				if (dropDownHandle(driver, sdg.dropDownInputInNewRecordForm(label, 20),
						dropDownListInNewRecordForm(label), "DropDown " + label, value)) {
					log(LogStatus.INFO, "Successfully Select the value:  " + value + " from " + label, YesNo.Yes);
					status++;

				} else {
					log(LogStatus.SKIP, "Not Successfully Select the value:  " + value + " from " + label, YesNo.Yes);
					sa.assertTrue(false, "Not Successfully Select the value:  " + value + " from " + label);
				}

			} else if (typeOfElement.equalsIgnoreCase("TextBox")) {
				ele = textBoxInNewRecordForm(label, 10);
				if (ele != null) {
					if (sendKeys(driver, ele, value, label, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "successfully entered " + value + " in " + label, YesNo.Yes);
						status++;

					} else {
						log(LogStatus.SKIP, "could not enter " + value + " in " + label, YesNo.Yes);
						sa.assertTrue(false, "could not enter " + value + " in " + label);

					}

				}
			} else if (typeOfElement.equalsIgnoreCase("TextArea")) {
				ele = sdg.textAreaInNewRecordForm(label, 10);
				if (sendKeys(driver, ele, value, label, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "successfully entered " + value + " in " + label, YesNo.Yes);
					status++;

				} else {
					log(LogStatus.SKIP, "could not enter " + value + " in " + label, YesNo.Yes);
					sa.assertTrue(false, "could not enter " + value + " in " + label);

				}

			}

			else if (typeOfElement.equalsIgnoreCase("SearchDropDown")) {
				ele = sdg.sdgActionAndFieldTextArea(label, 10);
				if (dropDownHandle(driver, sdg.searchDropDownInputInNewRecordForm(label, 20),
						searchDropDownListInNewRecordForm(label), "DropDown " + label, value)) {
					log(LogStatus.INFO, "Successfully Select the value:  " + value + " from " + label, YesNo.Yes);
					status++;

				} else {
					log(LogStatus.SKIP, "Not Successfully Select the value:  " + value + " from " + label, YesNo.Yes);
					sa.assertTrue(false, "Not Successfully Select the value:  " + value + " from " + label);
				}

			}

			else if (typeOfElement.equalsIgnoreCase("DatePicker")) {
				if (value != null || value != "") {
					String[] date = value.split("/");

					if (click(driver, datePickerInputInNewRecordForm(label, 30), label + " Input Box",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Click on " + label + " Calendar Input Box", YesNo.No);
						if (CommonLib.datePickerHandle(driver, label + " Picker", date[2], date[1], date[0])) {
							log(LogStatus.INFO, "Date has been Selected  " + value, YesNo.No);
							status++;
						} else {
							sa.assertTrue(false, "Date has not been Selected  " + value);
							log(LogStatus.ERROR, "Date has not been Selected  " + value, YesNo.Yes);
						}
					} else {
						sa.assertTrue(false, "Not Able to Click on " + label + " Calendar input Box");
						log(LogStatus.ERROR, "Not Able to Click on " + label + " Calendar input Box", YesNo.Yes);
					}

				} else {
					log(LogStatus.ERROR, "No " + label + " Provided:  " + value, YesNo.Yes);
				}

			}

			else if (typeOfElement.equalsIgnoreCase("DatePickerCurrentDate")) {
				value = CommonLib.getDateAccToTimeZone("GMT+5:30", "dd/MMM/yyyy");

				String[] date = value.split("/");

				if (click(driver, datePickerInputInNewRecordForm(label, 30), label + " Input Box",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Click on " + label + " Calendar Input Box", YesNo.No);
					if (CommonLib.datePickerHandle(driver, label + " Picker", date[2], date[1], date[0])) {
						log(LogStatus.INFO, "Date has been Selected  " + value, YesNo.No);
						status++;
					} else {
						sa.assertTrue(false, "Date has not been Selected  " + value);
						log(LogStatus.ERROR, "Date has not been Selected  " + value, YesNo.Yes);
					}
				} else {
					sa.assertTrue(false, "Not Able to Click on " + label + " Calendar input Box");
					log(LogStatus.ERROR, "Not Able to Click on " + label + " Calendar input Box", YesNo.Yes);
				}

			}

			else if (typeOfElement.equalsIgnoreCase("DatePickerFutureDate")) {
				value = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MMM/yyyy", Integer.parseInt(value));
				String[] date = value.split("/");

				if (click(driver, datePickerInputInNewRecordForm(label, 30), label + " Input Box",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Click on " + label + " Calendar Input Box", YesNo.No);
					if (CommonLib.datePickerHandle(driver, label + " Picker", date[2], date[1], date[0])) {
						log(LogStatus.INFO, "Date has been Selected  " + value, YesNo.No);
						status++;
					} else {
						sa.assertTrue(false, "Date has not been Selected  " + value);
						log(LogStatus.ERROR, "Date has not been Selected  " + value, YesNo.Yes);
					}
				} else {
					sa.assertTrue(false, "Not Able to Click on " + label + " Calendar input Box");
					log(LogStatus.ERROR, "Not Able to Click on " + label + " Calendar input Box", YesNo.Yes);
				}

			}

			else if (typeOfElement.equalsIgnoreCase("MultiPickList")) {
				List<WebElement> rolesData = chosenOrAvailableDataForLabel(label, "Available");

				for (WebElement roledata : rolesData) {
					if (roledata.getText().equals(value)) {
						if (click(driver, roledata, value, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Click on " + value + " Button of Label " + label, YesNo.No);
							CommonLib.ThreadSleep(3000);

							if (click(driver, home.moveSectionToChosenOrAvailableButton(label, "Chosen", 30),
									"MultiPickList Navigation Button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Click on MultipickList Navigation Button of Label " + label,
										YesNo.No);

								List<WebElement> chosenRoleDatas = chosenOrAvailableDataForLabel(label, "Chosen");
								if (!chosenRoleDatas.isEmpty()) {
									List<String> chosenRoleDatasText = chosenRoleDatas.stream().map(x -> x.getText())
											.collect(Collectors.toList());
									if (chosenRoleDatasText.contains(value)) {

										log(LogStatus.INFO, "Value: " + value + " has been Chosen ", YesNo.Yes);
										status++;

									} else {
										sa.assertTrue(false, "Value: " + value
												+ " has not been Chosen, So Not Able to Continue to Create Record");
										log(LogStatus.ERROR, "Value: " + value
												+ " has not been Chosen, So Not Able to Continue to Create Record",
												YesNo.Yes);
									}
								} else {
									sa.assertTrue(false, "Not Able to Chose the " + label + " " + value);
									log(LogStatus.ERROR, "Not Able to Chose the " + label + " " + value, YesNo.Yes);
								}

							} else {
								sa.assertTrue(false,
										"Not Able to Click on MultipickList Navigation Button of Label: " + label);
								log(LogStatus.ERROR,
										"Not Able to Click on MultipickList Navigation Button of Label: " + label,
										YesNo.Yes);
							}

						} else {
							sa.assertTrue(false,
									"Not Able to Click on Button Value so cannot move to Chosen of Label " + label);
							log(LogStatus.SKIP, "Not Able to Click on " + value
									+ " Button Value so cannot move to Chosen of Label " + label, YesNo.Yes);
						}

					}
				}
			}

			loopCount++;
		}
		if (status == loopCount)
			return true;
		else
			return false;
	}

	/**
	 * @author Ankur Huria
	 * @param buttonName
	 * @param timeOut
	 * @return true if able to click on button
	 */
	public boolean clickOnRecordPageButtonForNewRecordCreation(String buttonName, int timeOut) {
		boolean flag = false;
		if (buttonInRecordPage(buttonName, timeOut) != null) {
			if (click(driver, buttonInRecordPage(buttonName, timeOut), "Button " + buttonName,
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Button: " + buttonName + " on Record Form Page", YesNo.No);
				flag = true;
			} else {
				log(LogStatus.ERROR, "Not Able to Click on Button: " + buttonName + " on Record Form Page", YesNo.Yes);

			}
		}

		else if (downArrowButton(timeOut) != null) {
			log(LogStatus.INFO, "Down Arrow Button is Present", YesNo.No);
			if (click(driver, downArrowButton(timeOut), "DownArrowButton", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);
				CommonLib.ThreadSleep(2000);

				List<String> dropDownButtonsList = dropDownButtonsList().stream().map(x -> x.getText().trim())
						.collect(Collectors.toList());

				if (!dropDownButtonsList.isEmpty()) {
					log(LogStatus.INFO, "No. of Buttons Present on DownArrow Button are: " + dropDownButtonsList.size(),
							YesNo.No);

					int i = 0;
					for (String button : dropDownButtonsList) {
						if (button.equalsIgnoreCase(buttonName)) {
							log(LogStatus.INFO, "Button Found in Down Arrow Button", YesNo.No);

							if (click(driver, dropDownButtonsList().get(i), "DownArrowButton",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);
								flag = true;
								break;
							}

							else {
								log(LogStatus.ERROR,
										"Not Able to Click on Button: " + buttonName + " on Record Form Page",
										YesNo.Yes);

							}

						}

						i++;

					}

				}

				else {
					log(LogStatus.ERROR, "No Buttons Are Present in DownArrow Button", YesNo.Yes);

				}

			} else {
				log(LogStatus.INFO, "Not able to Click on Down Arrow Button", YesNo.No);
			}
		}

		else {
			log(LogStatus.ERROR, "Down Arrow Button is not Present on this Page, So No button: " + buttonName
					+ " present on this Page", YesNo.Yes);

		}
		return flag;
	}

	public ArrayList<String> VerifyInlineEditingForContactsAndAffiliationsGrid(String record) {
		String xPath = "";
		WebElement ele;
		List<WebElement> elements;
		Random random = new Random();
		ArrayList<String> columnName = new ArrayList<String>();
		ArrayList<String> inputType = new ArrayList<String>();
		ArrayList<String> result = new ArrayList<String>();
		String[] data = record.split("<break>");
		for (int i = 0; i < data.length; i++) {
			String[] gridNameAndCoulmnNameInputType = data[i].split("<gridBreak>");
			String gridName = gridNameAndCoulmnNameInputType[0];
			String[] CoulmnNameAndInputType = gridNameAndCoulmnNameInputType[1].split("<f>");
			for (int j = 0; j < CoulmnNameAndInputType.length; j++) {
				String[] ColumnNameAndDatatype = CoulmnNameAndInputType[j].split("<v>");
				columnName.add(ColumnNameAndDatatype[0]);
				inputType.add(ColumnNameAndDatatype[1]);
			}

			if (columnName.size() == inputType.size()) {
				for (int j = 0; j < columnName.size(); j++) {

					xPath = "//a[text()='" + gridName + "']/ancestor::article//td[contains(@data-label,'"
							+ columnName.get(j) + "')]//span";
					ele = CommonLib.FindElement(driver, xPath, columnName.get(j) + " heading", action.SCROLLANDBOOLEAN,
							50);
					if (ele != null) {
						if (CommonLib.mouseOverOperation(driver, ele)) {
							log(LogStatus.INFO, "Mouse has been hover to " + columnName.get(j), YesNo.Yes);
							xPath = "//a[text()='" + gridName + "']/ancestor::article//td[contains(@data-label,'"
									+ columnName.get(j) + "')]//button[@title='Edit']";
							ele = CommonLib.FindElement(driver, xPath, columnName.get(j) + " record", action.BOOLEAN,
									50);
							if (ele != null) {
								log(LogStatus.INFO, "Edit icon has been located of " + columnName.get(j), YesNo.Yes);
								if (CommonLib.clickUsingJavaScript(driver, ele, columnName.get(j) + " record",
										action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on " + columnName.get(j) + " record edit icon",
											YesNo.No);

									if (inputType.get(j).equals("textbox")) {
										xPath = "//a[text()='" + gridName
												+ "']/ancestor::article//td[contains(@data-label,'" + columnName.get(j)
												+ "')]//input";
										ele = CommonLib.FindElement(driver, xPath, columnName.get(j) + " record",
												action.SCROLLANDBOOLEAN, 50);
										CommonLib.scrollDownThroughWebelementInCenter(driver, ele,
												columnName.get(j) + " record");
										if (ele != null) {
											if (columnName.get(j).equals("Phone")) {
												if (CommonLib.sendKeys(driver, ele, random.nextInt(1000000000) + "",
														columnName.get(j) + " input type", action.BOOLEAN)) {
													log(LogStatus.INFO, "Phone number has been passed", YesNo.No);

												} else {
													log(LogStatus.ERROR, "Not able to enter the Phone numberd",
															YesNo.No);
													result.add("Not able to enter the Phone numberd");
												}
											} else if (columnName.get(j).equals("Email")) {
												if (CommonLib.sendKeys(driver, ele,
														"Automation" + random.nextInt(100000) + "@yopmail.com",
														columnName.get(j) + " input type", action.BOOLEAN)) {
													log(LogStatus.INFO, "Email id has been passed", YesNo.No);

												} else {
													log(LogStatus.ERROR, "Not able to enter the Email id", YesNo.No);
													result.add("Not able to enter the Email id");
												}
											} else {
												if (CommonLib.sendKeys(driver, ele,
														"Automation" + random.nextInt(100000),
														columnName.get(j) + " input type", action.BOOLEAN)) {

													log(LogStatus.INFO, "Value has been passed", YesNo.No);

												} else {
													log(LogStatus.ERROR, "Not able to enter the value", YesNo.No);
													result.add("Not able to enter the value");
												}
											}

										} else {
											log(LogStatus.ERROR,
													"Not able to get the " + columnName.get(j) + " input icon",
													YesNo.Yes);
											result.add("Not able to get the " + columnName.get(j) + " input icon");
										}
									} else if (inputType.get(j).equals("calender")) {

										xPath = "//a[text()='" + gridName
												+ "']/ancestor::article//td[contains(@data-label,'" + columnName.get(j)
												+ "')]//input";
										ele = CommonLib.FindElement(driver, xPath, columnName.get(j) + " record",
												action.SCROLLANDBOOLEAN, 50);
										if (ele != null) {
											if (CommonLib.click(driver, ele, columnName.get(j) + " input",
													action.BOOLEAN)) {
												if (datePickerHandle(driver, monthInDatePicker(50),
														previousMonthButtonInDatePicker(50), "Calender", "2022", "July",
														"22")) {
													log(LogStatus.INFO, "Date has been selected from the calender",
															YesNo.No);

												} else {
													log(LogStatus.ERROR, "Date is not selected from the calender",
															YesNo.No);
													result.add("Date is not selected from the calender");
												}

											} else {
												log(LogStatus.ERROR,
														"Not able to click on " + columnName.get(j) + " input icon",
														YesNo.Yes);
												result.add("Not able to click on " + columnName.get(j) + " input icon");
											}
										} else {
											log(LogStatus.ERROR,
													"Not able to get the " + columnName.get(j) + " input icon",
													YesNo.Yes);
											result.add("Not able to get the " + columnName.get(j) + " input icon");
										}
									}

									else if (inputType.get(j).equals("multipicklist")) {
										xPath = "//span[text()='Available']/parent::div//span[@class='slds-truncate']";
										ele = CommonLib.FindElement(driver, xPath,
												columnName.get(j) + " multipicklist record", action.SCROLLANDBOOLEAN,
												50);
										if (ele != null) {
											if (CommonLib.click(driver, ele, "Availabel picklist", action.BOOLEAN)) {
												log(LogStatus.INFO, "multipicklist element has located", YesNo.Yes);
												xPath = "//button[@title='Move selection to Chosen']";
												ele = CommonLib.FindElement(driver, xPath,
														"Move selection to Chosen icon ", action.SCROLLANDBOOLEAN, 50);
												if (ele != null) {
													if (CommonLib.click(driver, ele, "Move selection to Chosen icon",
															action.BOOLEAN)) {
														log(LogStatus.INFO, "clicked on Move selection to Chosen icon",
																YesNo.No);

													} else {
														log(LogStatus.ERROR,
																"Not able to clicked on Move selection to Chosen icon",
																YesNo.No);
														result.add(
																"Not able to clicked on Move selection to Chosen icon");
													}
												} else {
													log(LogStatus.ERROR,
															"Not able to get the Move selection to Chosen locator",
															YesNo.No);
													result.add("Not able to get the Move selection to Chosen locator");
												}

											} else {
												log(LogStatus.ERROR, "Not able to click on Availabel list", YesNo.No);
												result.add("Not able to click on Availabel list");
											}
										} else {
											log(LogStatus.ERROR, "Not able to locate the multipicklist locator",
													YesNo.No);
											result.add("Not able to locate the multipicklist locator");
										}
									}

									else if (inputType.get(j).equals("searchDropDown")) {
										xPath = "//a[text()='" + gridName
												+ "']/ancestor::article//td[contains(@data-label,'" + columnName.get(j)
												+ "')]//span//lightning-formatted-text";
										ele = CommonLib.FindElement(driver, xPath, columnName.get(j) + " Column Name",
												null, 25);
										String text = CommonLib.getText(driver, ele, columnName.get(j) + " record",
												null);

										xPath = "//a[text()='" + gridName
												+ "']/ancestor::article//td[contains(@data-label,'" + columnName.get(j)
												+ "')]//button[contains(@id,'combobox-button')]";
										ele = CommonLib.FindElement(driver, xPath, columnName.get(j) + " Column Name",
												null, 25);
										if (CommonLib.click(driver, ele, columnName.get(j) + " record", null)) {
											xPath = "//a[text()='" + gridName
													+ "']/ancestor::article//td[contains(@data-label,'"
													+ columnName.get(j)
													+ "')]//lightning-base-combobox-item//span[@class='slds-truncate']";
											elements = CommonLib.FindElements(driver, xPath,
													columnName.get(j) + " record");
											for (int k = 1; k < elements.size(); k++) {
												if (!CommonLib.getText(driver, elements.get(k),
														columnName.get(j) + " record", null).equals(text)) {
													if (CommonLib.clickUsingJavaScript(driver, elements.get(k),
															columnName.get(j) + " record")) {
														log(LogStatus.INFO,
																elements.get(k).getText() + " value has been selected",
																YesNo.No);
														break;
													} else {
														log(LogStatus.ERROR,
																"Not able to select " + elements.get(k).getText()
																		+ " value from dropdown list",
																YesNo.No);
														result.add("Not able to select " + elements.get(k).getText()
																+ " value from dropdown list");
													}
												}

											}
										} else {
											log(LogStatus.ERROR,
													"Not able to click on " + columnName.get(j) + " record", YesNo.No);
											result.add("Not able to click on " + columnName.get(j) + " record");
										}
									}

									else {
										log(LogStatus.ERROR, "Input type of record is incorrect", YesNo.No);
										result.add("Input type of record is incorrect");
									}

								} else {
									log(LogStatus.ERROR,
											"Not able to get the " + columnName.get(i) + " record edit icon", YesNo.No);
									result.add("Not able to get the " + columnName.get(i) + " record edit icon");
								}

							} else {
								log(LogStatus.ERROR, "Not able to get the " + columnName.get(i) + " edit icon",
										YesNo.No);
								result.add("Not able to get the " + columnName.get(i) + " edit icon");
							}
						} else {
							log(LogStatus.ERROR, "Not able to hover the record ", YesNo.No);
							result.add("Not able to hover the record ");
						}
					} else {
						log(LogStatus.ERROR, "Not able to get the locator of " + gridName + " record", YesNo.No);
						result.add("Not able to get the locator of " + gridName + " record");
					}

					xPath = "//a[text()='" + gridName + "']/ancestor::article//td//input[@type='checkbox']/..";
					ele = CommonLib.FindElement(driver, xPath, columnName.get(j) + " checkboxs record",
							action.SCROLLANDBOOLEAN, 50);
					CommonLib.scrollDownThroughWebelementInCenter(driver, ele, " record");
					CommonLib.doubleClickUsingAction(driver, ele);
					CommonLib.ThreadSleep(4000);

				}

			} else {
				log(LogStatus.ERROR,
						"Column Name size and Input type size are not matched. Please provide the correct input",
						YesNo.Yes);
				result.add("Column Name size and Input type size are not matched. Please provide the correct input");
			}

			xPath = "//a[text()='" + gridName + "']/ancestor::article//button[@title='Save']";
			ele = CommonLib.FindElement(driver, xPath, gridName + " ", action.SCROLLANDBOOLEAN, 50);
			if (CommonLib.click(driver, ele, "save button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Save of " + gridName, YesNo.No);
				if (CommonLib.checkElementVisibility(driver, getsaveConfirmationMsg(50), "save confirmation message",
						50)) {
					log(LogStatus.INFO, gridName + " record is has been saved", YesNo.No);
				} else {
					log(LogStatus.ERROR, gridName + " record is not saved", YesNo.No);
					result.add(gridName + " record is not saved");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on save button", YesNo.No);
				result.add("Not able to click on save button");
			}

			inputType.removeAll(inputType);
			columnName.removeAll(columnName);
		}
		return result;
	}

	/**
	 * @author Ankur Huria
	 * @param projectName
	 * @param alreadyCreated
	 * @param viewList
	 * @param timeout
	 * @return true if able to click on List View
	 */
	public boolean clickOnAlreadyCreatedListView(String projectName, String alreadyCreated, String viewList,
			int timeout) {
		boolean flag = false;
		String xpath = "";

		WebElement ele, selectListView;
		ele = null;

		refresh(driver);

		if (click(driver, getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(3000);
			xpath = "//div[@class='listContent']//li/a/span[contains(text(),'" + viewList + "')]";
			selectListView = FindElement(driver, xpath, "Select List View : " + viewList, action.SCROLLANDBOOLEAN, 30);
			if (click(driver, selectListView, "select List View : " + viewList, action.SCROLLANDBOOLEAN)) {
			} else {
				appLog.error("Not able to select on Select View List : " + viewList);
			}
		} else {
			appLog.error("Not able to click on Select List Icon");
		}

		ThreadSleep(3000);
		ThreadSleep(5000);

		if (sendKeys(driver, getSearchIcon_Lighting(20), alreadyCreated + "\n", "Search Icon Text",
				action.SCROLLANDBOOLEAN)) {
			ThreadSleep(5000);

			xpath = "//table[@data-aura-class='uiVirtualDataTable']//tbody//tr//th//span//*[text()='" + alreadyCreated
					+ "']";
			ele = FindElement(driver, xpath, alreadyCreated, action.BOOLEAN, 30);
			ThreadSleep(2000);

			if (click(driver, ele, alreadyCreated, action.BOOLEAN)) {
				ThreadSleep(3000);
				click(driver, getPagePopUp(projectName, 5), "Page PopUp", action.BOOLEAN);
				flag = true;
			} else {
				appLog.error("Not able to Click on Already Created : " + alreadyCreated);
			}
		} else {
			appLog.error("Not able to enter value on Search Box");
		}

		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param columnNameDataTypeAndValue
	 * @return ArrayList<String> of Negative Results
	 */

	public ArrayList<String> VerifyInlineEditing(String columnNameDataTypeAndValue) {
		String xPath = "";
		WebElement ele;
		List<WebElement> elements;
		Random random = new Random();
		ArrayList<String> columnName = new ArrayList<String>();
		ArrayList<String> inputType = new ArrayList<String>();
		ArrayList<String> inputValue = new ArrayList<String>();
		ArrayList<String> result = new ArrayList<String>();
		String[] data = columnNameDataTypeAndValue.split("<break>");
		for (int i = 0; i < data.length; i++) {
			String[] gridNameAndCoulmnNameInputType = data[i].split("<gridBreak>");
			String gridName = gridNameAndCoulmnNameInputType[0];
			String[] CoulmnNameAndInputType = gridNameAndCoulmnNameInputType[1].split("<f>");
			for (int j = 0; j < CoulmnNameAndInputType.length; j++) {
				String[] ColumnNameAndDatatype = CoulmnNameAndInputType[j].split("<v>");
				columnName.add(ColumnNameAndDatatype[0]);
				inputType.add(ColumnNameAndDatatype[1]);
				inputValue.add(ColumnNameAndDatatype[2]);
			}

			if (columnName.size() == inputType.size() && inputValue.size() == inputType.size()) {
				for (int j = 0; j < columnName.size(); j++) {

					xPath = "//a[text()='" + gridName + "']/ancestor::article//td[contains(@data-label,'"
							+ columnName.get(j) + "')]//span";
					ele = CommonLib.FindElement(driver, xPath, columnName.get(j) + " heading", action.SCROLLANDBOOLEAN,
							50);
					if (ele != null) {
						if (CommonLib.mouseOverOperation(driver, ele)) {
							log(LogStatus.INFO, "Mouse has been hover to " + columnName.get(j), YesNo.Yes);
							xPath = "//a[text()='" + gridName + "']/ancestor::article//td[contains(@data-label,'"
									+ columnName.get(j) + "')]//button[@title='Edit']";
							ele = CommonLib.FindElement(driver, xPath, columnName.get(j) + " record", action.BOOLEAN,
									50);
							if (ele != null) {
								log(LogStatus.INFO, "Edit icon has been located of " + columnName.get(j), YesNo.Yes);
								if (CommonLib.clickUsingJavaScript(driver, ele, columnName.get(j) + " record",
										action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on " + columnName.get(j) + " record edit icon",
											YesNo.No);

									if (inputType.get(j).equalsIgnoreCase("textbox")) {
										xPath = "//a[text()='" + gridName
												+ "']/ancestor::article//td[contains(@data-label,'" + columnName.get(j)
												+ "')]//input";
										ele = CommonLib.FindElement(driver, xPath, columnName.get(j) + " record",
												action.SCROLLANDBOOLEAN, 50);
										CommonLib.scrollDownThroughWebelementInCenter(driver, ele,
												columnName.get(j) + " record");
										if (ele != null) {

											if (CommonLib.sendKeys(driver, ele, inputValue.get(j),
													columnName.get(j) + " input type", action.BOOLEAN)) {

												log(LogStatus.INFO, "Value has been passed " + inputValue.get(j),
														YesNo.No);

											} else {
												log(LogStatus.ERROR, "Not able to enter the value" + inputValue.get(j),
														YesNo.No);
												result.add("Not able to enter the value" + inputValue.get(j));
											}

										} else {
											log(LogStatus.ERROR,
													"Not able to get the " + columnName.get(j) + " input icon",
													YesNo.Yes);
											result.add("Not able to get the " + columnName.get(j) + " input icon");
										}
									} else if (inputType.get(j).equalsIgnoreCase("DatePicker")) {

										String[] date = inputValue.get(j).split("/");
										xPath = "//a[text()='" + gridName
												+ "']/ancestor::article//td[contains(@data-label,'" + columnName.get(j)
												+ "')]//input";
										ele = CommonLib.FindElement(driver, xPath, columnName.get(j) + " record",
												action.SCROLLANDBOOLEAN, 50);
										if (ele != null) {
											if (CommonLib.click(driver, ele, columnName.get(j) + " input",
													action.BOOLEAN)) {
												if (datePickerHandle(driver, monthInDatePicker(50),
														previousMonthButtonInDatePicker(50), "Calendar", date[3],
														date[1], date[0])) {
													log(LogStatus.INFO,
															"Date has been selected from the calendar " + date,
															YesNo.No);

												} else {
													log(LogStatus.ERROR,
															"Date is not selected from the calendar " + date, YesNo.No);
													result.add("Date is not selected from the calendar " + date);
												}

											} else {
												log(LogStatus.ERROR,
														"Not able to click on " + columnName.get(j) + " input icon",
														YesNo.Yes);
												result.add("Not able to click on " + columnName.get(j) + " input icon");
											}
										} else {
											log(LogStatus.ERROR,
													"Not able to get the " + columnName.get(j) + " input icon",
													YesNo.Yes);
											result.add("Not able to get the " + columnName.get(j) + " input icon");
										}
									}

									else if (inputType.get(j).equalsIgnoreCase("DatePickerCurrentDate")) {
										String value = CommonLib.getDateAccToTimeZone("GMT+5:30", "dd/MMM/yyyy");
										String[] date = value.split("/");
										xPath = "//a[text()='" + gridName
												+ "']/ancestor::article//td[contains(@data-label,'" + columnName.get(j)
												+ "')]//input";
										ele = CommonLib.FindElement(driver, xPath, columnName.get(j) + " record",
												action.SCROLLANDBOOLEAN, 50);
										if (ele != null) {
											if (CommonLib.click(driver, ele, columnName.get(j) + " input",
													action.BOOLEAN)) {
												if (datePickerHandle(driver, monthInDatePicker(50),
														previousMonthButtonInDatePicker(50), "Calendar", date[3],
														date[1], date[0])) {
													log(LogStatus.INFO,
															"Date has been selected from the calendar " + date,
															YesNo.No);

												} else {
													log(LogStatus.ERROR,
															"Date is not selected from the calendar " + date, YesNo.No);
													result.add("Date is not selected from the calendar " + date);
												}

											} else {
												log(LogStatus.ERROR,
														"Not able to click on " + columnName.get(j) + " input icon",
														YesNo.Yes);
												result.add("Not able to click on " + columnName.get(j) + " input icon");
											}
										} else {
											log(LogStatus.ERROR,
													"Not able to get the " + columnName.get(j) + " input icon",
													YesNo.Yes);
											result.add("Not able to get the " + columnName.get(j) + " input icon");
										}
									}

									else if (inputType.get(j).equalsIgnoreCase("DatePickerFutureDate")) {
										String value = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MMM/yyyy",
												Integer.parseInt(inputValue.get(j)));
										String[] date = value.split("/");
										xPath = "//a[text()='" + gridName
												+ "']/ancestor::article//td[contains(@data-label,'" + columnName.get(j)
												+ "')]//input";
										ele = CommonLib.FindElement(driver, xPath, columnName.get(j) + " record",
												action.SCROLLANDBOOLEAN, 50);
										if (ele != null) {
											if (CommonLib.click(driver, ele, columnName.get(j) + " input",
													action.BOOLEAN)) {
												if (datePickerHandle(driver, monthInDatePicker(50),
														previousMonthButtonInDatePicker(50), "Calendar", date[3],
														date[1], date[0])) {
													log(LogStatus.INFO,
															"Date has been selected from the calendar " + date,
															YesNo.No);

												} else {
													log(LogStatus.ERROR,
															"Date is not selected from the calendar " + date, YesNo.No);
													result.add("Date is not selected from the calendar " + date);
												}

											} else {
												log(LogStatus.ERROR,
														"Not able to click on " + columnName.get(j) + " input icon",
														YesNo.Yes);
												result.add("Not able to click on " + columnName.get(j) + " input icon");
											}
										} else {
											log(LogStatus.ERROR,
													"Not able to get the " + columnName.get(j) + " input icon",
													YesNo.Yes);
											result.add("Not able to get the " + columnName.get(j) + " input icon");
										}
									}

									else if (inputType.get(j).equalsIgnoreCase("multipicklist")) {
										xPath = "//span[text()='Available']/parent::div//span[@class='slds-truncate']";
										ele = CommonLib.FindElement(driver, xPath,
												columnName.get(j) + " multipicklist record", action.SCROLLANDBOOLEAN,
												50);
										if (ele != null) {
											if (CommonLib.click(driver, ele, "Availabel picklist", action.BOOLEAN)) {
												log(LogStatus.INFO, "multipicklist element has located", YesNo.Yes);
												xPath = "//button[@title='Move selection to Chosen']";
												ele = CommonLib.FindElement(driver, xPath,
														"Move selection to Chosen icon ", action.SCROLLANDBOOLEAN, 50);
												if (ele != null) {
													if (CommonLib.click(driver, ele, "Move selection to Chosen icon",
															action.BOOLEAN)) {
														log(LogStatus.INFO, "clicked on Move selection to Chosen icon",
																YesNo.No);

													} else {
														log(LogStatus.ERROR,
																"Not able to clicked on Move selection to Chosen icon",
																YesNo.No);
														result.add(
																"Not able to clicked on Move selection to Chosen icon");
													}
												} else {
													log(LogStatus.ERROR,
															"Not able to get the Move selection to Chosen locator",
															YesNo.No);
													result.add("Not able to get the Move selection to Chosen locator");
												}

											} else {
												log(LogStatus.ERROR, "Not able to click on Availabel list", YesNo.No);
												result.add("Not able to click on Availabel list");
											}
										} else {
											log(LogStatus.ERROR, "Not able to locate the multipicklist locator",
													YesNo.No);
											result.add("Not able to locate the multipicklist locator");
										}
									}

									else if (inputType.get(j).equalsIgnoreCase("searchDropDown")) {
										xPath = "//a[text()='" + gridName
												+ "']/ancestor::article//td[contains(@data-label,'" + columnName.get(j)
												+ "')]//span//lightning-formatted-text";
										ele = CommonLib.FindElement(driver, xPath, columnName.get(j) + " Column Name",
												null, 25);

										xPath = "//a[text()='" + gridName
												+ "']/ancestor::article//td[contains(@data-label,'" + columnName.get(j)
												+ "')]//button[contains(@id,'combobox-button')]";
										ele = CommonLib.FindElement(driver, xPath, columnName.get(j) + " Column Name",
												null, 25);
										if (CommonLib.click(driver, ele, columnName.get(j) + " record", null)) {
											xPath = "//a[text()='" + gridName
													+ "']/ancestor::article//td[contains(@data-label,'"
													+ columnName.get(j)
													+ "')]//lightning-base-combobox-item//span[@class='slds-truncate']";
											elements = CommonLib.FindElements(driver, xPath,
													columnName.get(j) + " record");
											for (int k = 1; k < elements.size(); k++) {
												if (CommonLib.getText(driver, elements.get(k),
														columnName.get(j) + " record", null)
														.equals(inputValue.get(j))) {
													if (CommonLib.clickUsingJavaScript(driver, elements.get(k),
															columnName.get(j) + " record")) {
														log(LogStatus.INFO,
																elements.get(k).getText() + " value has been selected",
																YesNo.No);
														break;
													} else {
														log(LogStatus.ERROR,
																"Not able to select " + elements.get(k).getText()
																		+ " value from dropdown list",
																YesNo.No);
														result.add("Not able to select " + elements.get(k).getText()
																+ " value from dropdown list");
													}
												}

											}
										} else {
											log(LogStatus.ERROR,
													"Not able to click on " + columnName.get(j) + " record", YesNo.No);
											result.add("Not able to click on " + columnName.get(j) + " record");
										}
									}

									else {
										log(LogStatus.ERROR, "Input type of record is incorrect", YesNo.No);
										result.add("Input type of record is incorrect");
									}

								} else {
									log(LogStatus.ERROR,
											"Not able to get the " + columnName.get(i) + " record edit icon", YesNo.No);
									result.add("Not able to get the " + columnName.get(i) + " record edit icon");
								}

							} else {
								log(LogStatus.ERROR, "Not able to get the " + columnName.get(i) + " edit icon",
										YesNo.No);
								result.add("Not able to get the " + columnName.get(i) + " edit icon");
							}
						} else {
							log(LogStatus.ERROR, "Not able to hover the record ", YesNo.No);
							result.add("Not able to hover the record ");
						}
					} else {
						log(LogStatus.ERROR, "Not able to get the locator of " + gridName + " record", YesNo.No);
						result.add("Not able to get the locator of " + gridName + " record");
					}

					xPath = "//a[text()='" + gridName + "']/ancestor::article//td//input[@type='checkbox']/..";
					ele = CommonLib.FindElement(driver, xPath, columnName.get(j) + " checkboxs record",
							action.SCROLLANDBOOLEAN, 50);
					CommonLib.scrollDownThroughWebelementInCenter(driver, ele, " record");
					CommonLib.doubleClickUsingAction(driver, ele);
					CommonLib.ThreadSleep(4000);

				}

			} else {
				log(LogStatus.ERROR,
						"Column Name size and Input type size are not matched. Please provide the correct input",
						YesNo.Yes);
				result.add("Column Name size and Input type size are not matched. Please provide the correct input");
			}

			xPath = "//a[text()='" + gridName + "']/ancestor::article//button[@title='Save']";
			ele = CommonLib.FindElement(driver, xPath, gridName + " ", action.SCROLLANDBOOLEAN, 50);
			if (CommonLib.click(driver, ele, "save button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Save of " + gridName, YesNo.No);
				if (CommonLib.checkElementVisibility(driver, getsaveConfirmationMsg(50), "save confirmation message",
						50)) {
					log(LogStatus.INFO, gridName + " record is has been saved", YesNo.No);
				} else {
					log(LogStatus.ERROR, gridName + " record is not saved", YesNo.No);
					result.add(gridName + " record is not saved");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on save button", YesNo.No);
				result.add("Not able to click on save button");
			}

			inputType.removeAll(inputType);
			columnName.removeAll(columnName);
		}
		return result;
	}

	public boolean uploadFileAndVerify(String fileName, String fileType, String fileSize) {
		boolean flag = false;
		String xPath;
		WebElement ele;

		String text = CommonLib.getText(driver, getUploadedFileCount(30), "Uploaded file count", action.BOOLEAN);
		String size = text.replaceAll("()", "");
		String currentDate = CommonLib.getDateAccToTimeZone("GMT+5:30", "MMM dd, yyyy");

		if (getuploadFileVisible(15) != null) {

			if (CommonLib.sendKeys(driver, getfileUpload(30),
					System.getProperty("user.dir") + "/UploadFiles/PEFSTG/" + fileName, "Upload file",
					action.BOOLEAN)) {
				if (click(driver, getClickedOnDoneButton(40), "Done button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on done button", YesNo.No);

					if (CommonLib.checkElementVisibility(driver, getfileUploadConfirmationMsg(50),
							"File Upload confirmation message", 50)) {
						log(LogStatus.INFO, "File has been Uploaded", YesNo.No);

						CommonLib.ThreadSleep(5000);
						String actualUploadedFileDate = CommonLib.getText(driver, getfileUploadDate(20),
								"Uploaded File Date", action.SCROLLANDBOOLEAN);
						String actualUploadedFileSize = CommonLib.getText(driver, getfileUploadSize(20),
								"Uploaded File Size", action.SCROLLANDBOOLEAN);
						String actualUploadedFileType = CommonLib.getText(driver, getfileUploadType(20),
								"Uploaded File type", action.SCROLLANDBOOLEAN);

						if (actualUploadedFileDate.equals(currentDate) && actualUploadedFileSize.equals(fileSize)
								&& actualUploadedFileType.equals(fileType)) {
							log(LogStatus.INFO,
									"Expected uploaded file date : " + currentDate + ", file size : " + fileSize
											+ ", and file type : " + fileType
											+ " have been matched with the Actual uploaded file date : "
											+ actualUploadedFileDate + ", file size : " + actualUploadedFileSize
											+ ", and file type : " + actualUploadedFileType,
									YesNo.No);
							flag = true;
						} else {
							log(LogStatus.ERROR,
									"Expected uploaded file date : " + currentDate + ", file size : " + fileSize
											+ ", and file type : " + fileType
											+ " are not  matched with the Actual uploaded file date : "
											+ actualUploadedFileDate + ", file size : " + actualUploadedFileSize
											+ ", and file type : " + actualUploadedFileType,
									YesNo.No);

						}
					} else {
						log(LogStatus.ERROR, "Not able to Upload the File", YesNo.No);
					}

				} else {
					log(LogStatus.ERROR, "Could not Clicked on  Done Button", YesNo.No);
				}
			} else {
				log(LogStatus.ERROR, "Not able to upload the file", YesNo.No);
			}
		} else {
			log(LogStatus.ERROR, "Not able to upload the file", YesNo.No);
		}
		return flag;
	}

	public ArrayList<String> verifyFileCountUploadAndAddFileButton() {
		ArrayList<String> result = new ArrayList<String>();
		boolean flag = false;
		if (CommonLib.checkElementVisibility(driver, getfileCountVisible(20), "file Count visiblity", 20)) {
			log(LogStatus.INFO, "File count is visible", YesNo.No);
		} else {
			log(LogStatus.ERROR, "File count is not visible", YesNo.No);
			result.add("File count is not visible");
		}

		if (CommonLib.checkElementVisibility(driver, getuploadFileVisible(20), "Upload file visiblity", 20)) {
			log(LogStatus.INFO, "Upload File button is visible", YesNo.No);
		} else {
			log(LogStatus.ERROR, "Upload File button is not visible", YesNo.No);
			result.add("Upload File button is not visible");
		}

		if (CommonLib.checkElementVisibility(driver, getaddFileVisible(20), "Add file button visiblity", 20)) {
			log(LogStatus.INFO, "Add File button is visible", YesNo.No);
		} else {
			log(LogStatus.ERROR, "Add File button is not visible", YesNo.No);
			result.add("Add File button is not visible");
		}

		if (CommonLib.checkElementVisibility(driver, getdropFileVisible(20), "Or drop file button visiblity", 20)) {
			log(LogStatus.INFO, "Or drop File button is visible", YesNo.No);
		} else {
			log(LogStatus.ERROR, "Or drop File button is not visible", YesNo.No);
			result.add("Or drop File button is not visible");
		}
		return result;
	}

	/**
	 * @author Ankur Huria
	 * @param expectedListOfSectionHeaders
	 * @return negativeResult
	 */
	public List<String> verifyRecordLayoutSectionHeaders(List<String> expectedListOfSectionHeaders) {

		List<String> negativeResult = new ArrayList<String>();

		if (!expectedListOfSectionHeaders.isEmpty()) {

			if (expectedListOfSectionHeaders.size() != 1 && !expectedListOfSectionHeaders.get(0).equals("")) {
				List<String> recordLayoutSectionHeaders = recordLayoutSectionHeaders().stream()
						.map(x -> x.getText().trim()).collect(Collectors.toList());

				if (!recordLayoutSectionHeaders.isEmpty()) {
					log(LogStatus.INFO,
							"No. of Section Headers Present on Page are: " + recordLayoutSectionHeaders.size(),
							YesNo.No);

					int i = 0;
					if (recordLayoutSectionHeaders.size() == expectedListOfSectionHeaders.size()) {
						log(LogStatus.INFO,
								"No. of Actual and Expected Section Headers on Page are same, So Continue the Process",
								YesNo.No);

						for (String recordLayoutSectionHeader : recordLayoutSectionHeaders) {
							if (recordLayoutSectionHeader.equals(expectedListOfSectionHeaders.get(i))) {
								log(LogStatus.INFO,
										"----Section Headers Matched, Expected: " + expectedListOfSectionHeaders.get(i)
												+ " & Actual: " + recordLayoutSectionHeader + " on this Page----",
										YesNo.No);
							} else {

								log(LogStatus.ERROR,
										"----Section Headers Not Matched, Expected: "
												+ expectedListOfSectionHeaders.get(i) + " but Actual: "
												+ recordLayoutSectionHeader + " on this Page----",
										YesNo.No);
								negativeResult.add("----Section Headers Not Matched, Expected: "
										+ expectedListOfSectionHeaders.get(i) + " but Actual: "
										+ recordLayoutSectionHeader + " on this Page----");

							}

							i++;
						}
					} else {
						log(LogStatus.ERROR,
								"No. of Expected and Actual Section Headers on Page not matched, So not able to continue, Expected: "
										+ expectedListOfSectionHeaders + " & Actual: " + recordLayoutSectionHeaders,
								YesNo.Yes);
						negativeResult.add(
								"No. of Expected and Actual Section Headers on Page not matched, So not able to continue, Expected: "
										+ expectedListOfSectionHeaders + " & Actual: " + recordLayoutSectionHeaders);
					}

				}

				else {
					log(LogStatus.ERROR, "No Section Headers Are Present on this Page", YesNo.Yes);
					negativeResult.add("No Section Headers Are Present on this Page");
				}
			} else

			{
				log(LogStatus.ERROR, "No Expected Section Headers to verify on Page Mentioned", YesNo.No);
				negativeResult.add("No Expected Section Headers to verify On Page Mentioned");
			}
		} else

		{
			log(LogStatus.ERROR, "No Expected Section Headers to verify on Page Mentioned", YesNo.No);
			negativeResult.add("No Expected Section Headers to verify On Page Mentioned");
		}

		return negativeResult;
	}

	public boolean clicktabOnPage(String tabName) {
		if (CommonLib.clickUsingJavaScript(driver, getTabNameOnPage(tabName, 30), tabName, action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "clicked on " + tabName + " tab name", YesNo.No);

			String text = CommonLib.getAttribute(driver, getVerificationPartTabNameOnPage(tabName, 20),
					tabName + " tab", "class");
			if (text.contains("slds-is-active")) {
				log(LogStatus.INFO, tabName + " tab has been open", YesNo.No);
				return true;
			} else {
				log(LogStatus.ERROR, "Not able to open tab " + tabName, YesNo.No);
				return false;
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabName + " tab name", YesNo.No);

			return false;
		}

	}

	/**
	 * @author Ankur Huria
	 * @param contactName
	 * @param teamMember
	 * @param title
	 * @param deals
	 * @param meetingAndCalls
	 * @param email
	 * @return return Empty ArrayList if test case is passed
	 */

	public ArrayList<String> verifyRecordOnConnectionsPopUpOfContactInAcuity(String contactName, String teamMember,
			String title, String deals, String meetingAndCalls, String email) {

		ArrayList<String> result = new ArrayList<String>();

		if (contactName != null && contactName != "") {

			if (contactNameUserIconButton(contactName, 30) != null) {

				if (click(driver, contactNameUserIconButton(contactName, 30), "Contact Name: " + contactName,
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Contact: " + contactName, YesNo.No);
					String parentID = switchOnWindow(driver);

					if (connectionPopUpTeamMember(teamMember, 20) != null) {

						if (title != null && title != "") {

							String actualTitle = getText(driver, connectionPopUpTitle(teamMember, title, 30), "Title",
									action.SCROLLANDBOOLEAN);
							if (actualTitle.equalsIgnoreCase(title)) {
								log(LogStatus.INFO, "Actual result " + actualTitle
										+ " of Title has been matched with Expected result : " + title
										+ " of Team Member: " + teamMember + " under Record page: " + contactName,
										YesNo.No);
							} else {
								log(LogStatus.ERROR, "Actual result " + actualTitle
										+ " of Title is not matched with Expected result : " + title
										+ " of Team Member: " + teamMember + " under Record page: " + contactName,
										YesNo.No);
								result.add("Actual result " + actualTitle
										+ " of Title is not matched with Expected result : " + title
										+ " of Team Member: " + teamMember + " under Record page: " + contactName);
							}
						}

						if (deals != null && deals != "") {

							String actualDeal = getText(driver, connectionPopUpDealsCount(teamMember, deals, 30),
									"deal", action.SCROLLANDBOOLEAN);
							if (actualDeal.equalsIgnoreCase(deals)) {
								log(LogStatus.INFO, "Actual result " + actualDeal
										+ " of deal has been matched with Expected result : " + deals
										+ " of Team Member: " + teamMember + " under Record page: " + contactName,
										YesNo.No);
							} else {
								log(LogStatus.ERROR, "Actual result " + actualDeal
										+ " of deal are not matched with Expected resut : " + deals
										+ " of Team Member: " + teamMember + " under Record page: " + contactName,
										YesNo.No);
								result.add("Actual result " + actualDeal
										+ " of deal are not matched with Expected resut : " + deals
										+ " of Team Member: " + teamMember + " under Record page: " + contactName);
							}
						}

						if (meetingAndCalls != null && meetingAndCalls != "") {

							String actualmeetingAndCalls = getText(driver,
									connectionPopUpMeetingCallsCount(teamMember, meetingAndCalls, 30),
									"meeting and call", action.SCROLLANDBOOLEAN);
							if (actualmeetingAndCalls.equalsIgnoreCase(meetingAndCalls)) {
								log(LogStatus.INFO,
										"Actual result " + actualmeetingAndCalls
												+ " of meeting and call has been matched with Expected result : "
												+ meetingAndCalls + " of Team Member: " + teamMember
												+ " under Record page: " + contactName,
										YesNo.No);
							} else {
								log(LogStatus.ERROR,
										"Actual result " + actualmeetingAndCalls
												+ " of meeting and call are not matched with Expected resut : "
												+ meetingAndCalls + " of Team Member: " + teamMember
												+ " under Record page: " + contactName,
										YesNo.No);
								result.add("Actual result " + actualmeetingAndCalls
										+ " of meeting and call are not matched with Expected resut : "
										+ meetingAndCalls + " of Team Member: " + teamMember + " under Record page: "
										+ contactName);
							}
						}
						if (email != null && email != "") {
							String actualEmail = getText(driver, connectionPopUpEmailCount(teamMember, email, 30),
									"email", action.SCROLLANDBOOLEAN);
							if (actualEmail.equalsIgnoreCase(email)) {
								log(LogStatus.INFO, "Actual result " + actualEmail
										+ " of email has been matched with Expected result : " + email
										+ " of Team Member: " + teamMember + " under Record page: " + contactName,
										YesNo.No);
							} else {
								log(LogStatus.ERROR, "Actual result " + actualEmail
										+ " of email are not matched with Expected resut : " + email
										+ " of Team Member: " + teamMember + " under Record page: " + contactName,
										YesNo.No);
								result.add("Actual result " + actualEmail
										+ " of email are not matched with Expected resut : " + email
										+ " of Team Member: " + teamMember + " under Record page: " + contactName);
							}
						}

					} else {
						log(LogStatus.ERROR,
								"No Team Member found of name: " + teamMember + " for contact: " + contactName,
								YesNo.No);
						result.add("No Team Member found of name: " + teamMember + " for contact: " + contactName);
						if (connectionClosePopupButton(15) != null) {
							click(driver, connectionClosePopupButton(15), "Close Button", action.SCROLLANDBOOLEAN);
						}

					}
					driver.close();
					driver.switchTo().window(parentID);

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Contact: " + contactName, YesNo.No);
					result.add("Not Able to Click on Contact: " + contactName);

				}
			} else {
				log(LogStatus.ERROR, contactName + " is not avalable in contact section, So Can not Click on User Icon",
						YesNo.No);
				result.add(contactName + " is not avalable in contact section, So Can not Click on User Icon");

			}
		} else {
			log(LogStatus.ERROR, "Provided Contact Name should not be null in DataSheet", YesNo.No);
			result.add("Provided Contact Name should not be null in DataSheet");

		}
		return result;
	}

	/**
	 * @author Ankur Huria
	 * @param recordNameOfRecordPage
	 * @param teamMember
	 * @param title
	 * @param deals
	 * @param meetingAndCalls
	 * @param email
	 * @return return Empty ArrayList if test case is passed
	 */

	public ArrayList<String> verifyRecordOnConnectionsSectionInAcuity(String recordNameOfRecordPage, String teamMember,
			String title, String deals, String meetingAndCalls, String email) {
		String xPath = "";
		WebElement ele = null;
		ArrayList<String> result = new ArrayList<String>();

		if (connectionTeamMember(teamMember, 20) != null) {

			if (title != null && title != "") {

				String actualTitle = getText(driver, connectionTitle(teamMember, title, 30), "Title",
						action.SCROLLANDBOOLEAN);
				if (actualTitle.equalsIgnoreCase(title)) {
					log(LogStatus.INFO,
							"Actual result " + actualTitle + " of Title has been matched with Expected result : "
									+ title + " of Team Member: " + teamMember + "under Record page of "
									+ recordNameOfRecordPage,
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Actual result " + actualTitle + " of Title is not matched with Expected result : " + title
									+ " of Team Member: " + teamMember + "under Record page of "
									+ recordNameOfRecordPage,
							YesNo.No);
					result.add("Actual result " + actualTitle + " of Title is not matched with Expected result : "
							+ title + " of Team Member: " + teamMember + "under Record page of "
							+ recordNameOfRecordPage);
				}
			}

			if (deals != null && deals != "") {

				String actualDeal = getText(driver, connectionDealsCount(teamMember, deals, 30), "deal",
						action.SCROLLANDBOOLEAN);
				if (actualDeal.equalsIgnoreCase(deals)) {
					log(LogStatus.INFO,
							"Actual result " + actualDeal + " of deal has been matched with Expected resut : " + deals
									+ " of Team Member: " + teamMember + "under Record page of "
									+ recordNameOfRecordPage,
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Actual result " + actualDeal + " of deal are not matched with Expected resut : " + deals
									+ " of Team Member: " + teamMember + "under Record page of "
									+ recordNameOfRecordPage,
							YesNo.No);
					result.add("Actual result " + actualDeal + " of deal are not matched with Expected resut : " + deals
							+ " of Team Member: " + teamMember + "under Record page of " + recordNameOfRecordPage);
				}
			}

			if (meetingAndCalls != null && meetingAndCalls != "") {

				String actualmeetingAndCalls = getText(driver,
						connectionMeetingCallsCount(teamMember, meetingAndCalls, 30), "meeting and call",
						action.SCROLLANDBOOLEAN);
				if (actualmeetingAndCalls.equalsIgnoreCase(meetingAndCalls)) {
					log(LogStatus.INFO, "Actual result " + actualmeetingAndCalls
							+ " of meeting and call has been matched with Expected result : " + meetingAndCalls
							+ " of Team Member: " + teamMember + "under Record page of " + recordNameOfRecordPage,
							YesNo.No);
				} else {
					log(LogStatus.ERROR, "Actual result " + actualmeetingAndCalls
							+ " of meeting and call are not matched with Expected resut : " + meetingAndCalls
							+ " of Team Member: " + teamMember + "under Record page of " + recordNameOfRecordPage,
							YesNo.No);
					result.add("Actual result " + actualmeetingAndCalls
							+ " of meeting and call are not matched with Expected resut : " + meetingAndCalls
							+ " of Team Member: " + teamMember + "under Record page of " + recordNameOfRecordPage);
				}
			}
			if (email != null && email != "") {

				String actualEmail = getText(driver, connectionEmailCount(teamMember, email, 30), "email",
						action.SCROLLANDBOOLEAN);
				if (actualEmail.equalsIgnoreCase(email)) {
					log(LogStatus.INFO,
							"Actual result " + actualEmail + " of email has been matched with Expected result : "
									+ email + " of Team Member: " + teamMember + "under Record page of "
									+ recordNameOfRecordPage,
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Actual result " + actualEmail + " of email are not matched with Expected resut : " + email
									+ " of Team Member: " + teamMember + "under Record page of "
									+ recordNameOfRecordPage,
							YesNo.No);
					result.add("Actual result " + actualEmail + " of email are not matched with Expected resut : "
							+ email + " of Team Member: " + teamMember + "under Record page of "
							+ recordNameOfRecordPage);
				}
			}

		} else {
			log(LogStatus.ERROR,
					"No Team Member found of name: " + teamMember + "under Record page of " + recordNameOfRecordPage,
					YesNo.No);
			result.add(
					"No Team Member found of name: " + teamMember + "under Record page of " + recordNameOfRecordPage);

		}

		return result;
	}

	/**
	 * @author Ankur Huria
	 * @param recordNameOfRecordPage
	 * @param dealName
	 * @param stage
	 * @param highestStageReachedName
	 * @param dateReceived
	 * @return return Empty ArrayList if test case is passed
	 */

	public ArrayList<String> verifyRecordOnDealsSectionInAcuity(String recordNameOfRecordPage, String dealName,
			String stage, String highestStageReachedName, String dateReceived) {

		ArrayList<String> result = new ArrayList<String>();

		if (dealAcuityDealName(dealName, 20) != null) {

			if (highestStageReachedName != null && highestStageReachedName != "") {

				String actualCompany = getText(driver,
						dealAcuityhighestStageReachedName(dealName, highestStageReachedName, 20), "Title",
						action.SCROLLANDBOOLEAN);
				if (actualCompany.equalsIgnoreCase(highestStageReachedName)) {
					log(LogStatus.INFO,
							"Actual result " + actualCompany
									+ " of highestStageReachedName has been matched with Expected result : "
									+ highestStageReachedName + " of Deal Name: " + dealName + " under Record Page of "
									+ recordNameOfRecordPage,
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Actual result " + actualCompany
									+ " of highestStageReachedName is not matched with Expected result : "
									+ highestStageReachedName + " of Deal Name: " + dealName + " under Record Page of "
									+ recordNameOfRecordPage,
							YesNo.No);
					result.add("Actual result " + actualCompany + " of Company is not matched with Expected result : "
							+ highestStageReachedName + " of Deal Name: " + dealName + " under Record Page of "
							+ recordNameOfRecordPage);
				}
			}

			if (stage != null && stage != "") {

				String actualStage = getText(driver, dealAcuityStageName(dealName, stage, 20), "deal",
						action.SCROLLANDBOOLEAN);
				if (actualStage.equalsIgnoreCase(stage)) {
					log(LogStatus.INFO,
							"Actual result " + actualStage + " of Stage has been matched with Expected resut : " + stage
									+ " of Deal Name: " + dealName + " under Record Page of " + recordNameOfRecordPage,
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Actual result " + actualStage + " of Stage are not matched with Expected resut : " + stage
									+ " of Deal Name: " + dealName + " under Record Page of " + recordNameOfRecordPage,
							YesNo.No);
					result.add("Actual result " + actualStage + " of Stage are not matched with Expected resut : "
							+ stage + " of Deal Name: " + dealName + " under Record Page of " + recordNameOfRecordPage);
				}
			}

			if (dateReceived != null && dateReceived != "") {

				String actualDateReceived = getText(driver, dealAcuityDateReceived(dealName, dateReceived, 20),
						"meeting and call", action.SCROLLANDBOOLEAN);
				if (actualDateReceived.equalsIgnoreCase(dateReceived)) {
					log(LogStatus.INFO,
							"Actual result " + actualDateReceived
									+ " of Date Received has been matched with Expected result : " + dateReceived
									+ " of Deal Name: " + dealName + " under Record Page of " + recordNameOfRecordPage,
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Actual result " + actualDateReceived
									+ " of Date Received are not matched with Expected resut : " + dateReceived
									+ " of Deal Name: " + dealName + " under Record Page of " + recordNameOfRecordPage,
							YesNo.No);
					result.add("Actual result " + actualDateReceived
							+ " of Date Received are not matched with Expected resut : " + dateReceived
							+ " of Deal Name: " + dealName + " under Record Page of " + recordNameOfRecordPage);
				}
			}

		} else {
			log(LogStatus.ERROR,
					"No Deal Name found of name: " + dealName + " under Record Page of " + recordNameOfRecordPage,
					YesNo.No);
			result.add("No Deal Name found of name: " + dealName + " under Record Page of " + recordNameOfRecordPage);

		}

		return result;
	}

	public ArrayList<String> verifyDealsSectionBoxInAcuity(String recordNameOfRecordPage, String dealName,
			String highestStageReachedName, String stage, String dateReceived) {

		ArrayList<String> result = new ArrayList<String>();

		if (dealAcuityDealName(dealName, 20) != null) {

			if (highestStageReachedName != null && highestStageReachedName != "") {

				String actualCompany = getText(driver,
						dealAcuityhighestStageReachedName(dealName, highestStageReachedName, 20), "Title",
						action.SCROLLANDBOOLEAN);
				if (actualCompany.equalsIgnoreCase(highestStageReachedName)) {
					log(LogStatus.INFO,
							"Actual result " + actualCompany
									+ " of highestStageReachedName has been matched with Expected result : "
									+ highestStageReachedName + " of Deal Name: " + dealName + " under Record Page of "
									+ recordNameOfRecordPage,
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Actual result " + actualCompany
									+ " of highestStageReachedName is not matched with Expected result : "
									+ highestStageReachedName + " of Deal Name: " + dealName + " under Record Page of "
									+ recordNameOfRecordPage,
							YesNo.No);
					result.add("Actual result " + actualCompany
							+ " of highestStageReachedName is not matched with Expected result : "
							+ highestStageReachedName + " of Deal Name: " + dealName + " under Record Page of "
							+ recordNameOfRecordPage);
				}
			}

			if (stage != null && stage != "") {

				String actualStage = getText(driver, dealAcuityStageName(dealName, stage, 20), "deal",
						action.SCROLLANDBOOLEAN);
				if (actualStage.equalsIgnoreCase(stage)) {
					log(LogStatus.INFO,
							"Actual result " + actualStage + " of Stage has been matched with Expected resut : " + stage
									+ " of Deal Name: " + dealName + " under Record Page of " + recordNameOfRecordPage,
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Actual result " + actualStage + " of Stage are not matched with Expected resut : " + stage
									+ " of Deal Name: " + dealName + " under Record Page of " + recordNameOfRecordPage,
							YesNo.No);
					result.add("Actual result " + actualStage + " of Stage are not matched with Expected resut : "
							+ stage + " of Deal Name: " + dealName + " under Record Page of " + recordNameOfRecordPage);
				}
			}

			if (dateReceived != null && dateReceived != "") {

				String actualDateReceived = getText(driver, dealAcuityDateReceived(dealName, dateReceived, 20),
						"meeting and call", action.SCROLLANDBOOLEAN);
				if (actualDateReceived.equalsIgnoreCase(dateReceived)) {
					log(LogStatus.INFO,
							"Actual result " + actualDateReceived
									+ " of Date Received has been matched with Expected result : " + dateReceived
									+ " of Deal Name: " + dealName + " under Record Page of " + recordNameOfRecordPage,
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Actual result " + actualDateReceived
									+ " of Date Received are not matched with Expected resut : " + dateReceived
									+ " of Deal Name: " + dealName + " under Record Page of " + recordNameOfRecordPage,
							YesNo.No);
					result.add("Actual result " + actualDateReceived
							+ " of Date Received are not matched with Expected resut : " + dateReceived
							+ " of Deal Name: " + dealName + " under Record Page of " + recordNameOfRecordPage);
				}
			}

		} else {
			log(LogStatus.ERROR,
					"No Deal Name found of name: " + dealName + " under Record Page of " + recordNameOfRecordPage,
					YesNo.No);
			result.add("No Deal Name found of name: " + dealName + " under Record Page of " + recordNameOfRecordPage);

		}

		return result;
	}

	/**
	 * @author Ankur Huria
	 * @param recordNameOfRecordPage
	 * @param dealName
	 * @param company
	 * @param stage
	 * @param dateReceived
	 * @return return Empty ArrayList if test case is passed
	 */

	public ArrayList<String> verifyRecordOnDealsPopUpSectionInAcuity(String recordNameOfRecordPage, String dealName,
			String company, String stage, String dateReceived) {

		ArrayList<String> result = new ArrayList<String>();

		if (dealAcuityPopUpDealName(dealName, 20) != null) {

			if (company != null && company != "") {

				String actualCompany = getText(driver, dealAcuityPopUpCompanyName(dealName, company, 20), "Title",
						action.SCROLLANDBOOLEAN);
				if (actualCompany.equalsIgnoreCase(company)) {
					log(LogStatus.INFO,
							"Actual result " + actualCompany + " of Company has been matched with Expected result : "
									+ company + " of Deal Name: " + dealName + " under Record Page of "
									+ recordNameOfRecordPage,
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Actual result " + actualCompany + " of Company is not matched with Expected result : "
									+ company + " of Deal Name: " + dealName + " under Record Page of "
									+ recordNameOfRecordPage,
							YesNo.No);
					result.add("Actual result " + actualCompany + " of Company is not matched with Expected result : "
							+ company + " of Deal Name: " + dealName + " under Record Page of "
							+ recordNameOfRecordPage);
				}
			}

			if (stage != null && stage != "") {

				String actualStage = getText(driver, dealAcuityPopUpStageName(dealName, stage, 20), "deal",
						action.SCROLLANDBOOLEAN);
				if (actualStage.equalsIgnoreCase(stage)) {
					log(LogStatus.INFO,
							"Actual result " + actualStage + " of Stage has been matched with Expected resut : " + stage
									+ " of Deal Name: " + dealName + " under Record Page of " + recordNameOfRecordPage,
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Actual result " + actualStage + " of Stage are not matched with Expected resut : " + stage
									+ " of Deal Name: " + dealName + " under Record Page of " + recordNameOfRecordPage,
							YesNo.No);
					result.add("Actual result " + actualStage + " of Stage are not matched with Expected resut : "
							+ stage + " of Deal Name: " + dealName + " under Record Page of " + recordNameOfRecordPage);
				}
			}

			if (dateReceived != null && dateReceived != "") {

				String actualDateReceived = getText(driver, dealAcuityPopUpDateReceived(dealName, dateReceived, 20),
						"meeting and call", action.SCROLLANDBOOLEAN);
				if (actualDateReceived.equalsIgnoreCase(dateReceived)) {
					log(LogStatus.INFO,
							"Actual result " + actualDateReceived
									+ " of Date Received has been matched with Expected result : " + dateReceived
									+ " of Deal Name: " + dealName + " under Record Page of " + recordNameOfRecordPage,
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Actual result " + actualDateReceived
									+ " of Date Received are not matched with Expected resut : " + dateReceived
									+ " of Deal Name: " + dealName + " under Record Page of " + recordNameOfRecordPage,
							YesNo.No);
					result.add("Actual result " + actualDateReceived
							+ " of Date Received are not matched with Expected resut : " + dateReceived
							+ " of Deal Name: " + dealName + " under Record Page of " + recordNameOfRecordPage);
				}
			}

			click(driver, connectionClosePopupButton(15), "Close Button", action.SCROLLANDBOOLEAN);

		} else {
			log(LogStatus.ERROR,
					"No Deal Name found of name: " + dealName + " under Record Page of " + recordNameOfRecordPage,
					YesNo.No);
			result.add("No Deal Name found of name: " + dealName + " under Record Page of " + recordNameOfRecordPage);

			if (connectionClosePopupButton(15) != null) {
				click(driver, connectionClosePopupButton(15), "Close Button", action.SCROLLANDBOOLEAN);
			}

		}

		return result;
	}

	/**
	 * @author Ankur Huria
	 * @param recordNameOfRecordPage
	 * @param fundraisingName
	 * @param fundName
	 * @param stage
	 * @param targetClosedDate
	 * @return return Empty ArrayList if test case is passed
	 */

	public ArrayList<String> verifyRecordOnFundraisingsSectionInAcuity(String recordNameOfRecordPage,
			String fundraisingName, String fundName, String stage, String targetClosedDate) {

		ArrayList<String> result = new ArrayList<String>();

		if (fundraisingsAcuityFundraisingsName(fundraisingName, 20) != null) {

			if (fundName != null && fundName != "") {

				String actualFund = getText(driver, fundraisingsAcuityFundName(fundraisingName, fundName, 20),
						"Fund: " + fundName, action.SCROLLANDBOOLEAN);
				if (actualFund.equalsIgnoreCase(fundName)) {
					log(LogStatus.INFO,
							"Actual result " + actualFund + " of Fund has been matched with Expected result : "
									+ fundName + " of Fundraising Name: " + fundraisingName + " under Record Page of "
									+ recordNameOfRecordPage,
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Actual result " + actualFund + " of Fund is not matched with Expected result : " + fundName
									+ " of Fundraising Name: " + fundraisingName + " under Record Page of "
									+ recordNameOfRecordPage,
							YesNo.No);
					result.add("Actual result " + actualFund + " of Fund is not matched with Expected result : "
							+ fundName + " of Fundraising Name: " + fundraisingName + " under Record Page of "
							+ recordNameOfRecordPage);
				}
			}

			if (stage != null && stage != "") {

				String actualStage = getText(driver, fundraisingsAcuityStageName(fundraisingName, stage, 20),
						"Stage: " + stage, action.SCROLLANDBOOLEAN);
				if (actualStage.equalsIgnoreCase(stage)) {
					log(LogStatus.INFO,
							"Actual result " + actualStage + " of Stage has been matched with Expected resut : " + stage
									+ " of Fundraising Name: " + fundraisingName + " under Record Page of "
									+ recordNameOfRecordPage,
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Actual result " + actualStage + " of Stage are not matched with Expected resut : " + stage
									+ " of Fundraising Name: " + fundraisingName + " under Record Page of "
									+ recordNameOfRecordPage,
							YesNo.No);
					result.add("Actual result " + actualStage + " of Stage are not matched with Expected resut : "
							+ stage + " of Fundraising Name: " + fundraisingName + " under Record Page of "
							+ recordNameOfRecordPage);
				}
			}

			if (targetClosedDate != null && targetClosedDate != "") {

				String actualTargetClosedDate = getText(driver,
						fundrasingsAcuityTargetClosedDate(fundraisingName, targetClosedDate, 20),
						"Target Closed Date: " + targetClosedDate, action.SCROLLANDBOOLEAN);
				if (actualTargetClosedDate.equalsIgnoreCase(targetClosedDate)) {
					log(LogStatus.INFO,
							"Actual result " + actualTargetClosedDate
									+ " of targetClosedDate has been matched with Expected result : " + targetClosedDate
									+ " of Fundraising Name: " + fundraisingName + " under Record Page of "
									+ recordNameOfRecordPage,
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Actual result " + actualTargetClosedDate
									+ " of targetClosedDate are not matched with Expected resut : " + targetClosedDate
									+ " of Fundraising Name: " + fundraisingName + " under Record Page of "
									+ recordNameOfRecordPage,
							YesNo.No);
					result.add("Actual result " + actualTargetClosedDate
							+ " of targetClosedDate are not matched with Expected resut : " + targetClosedDate
							+ " of Fundraising Name: " + fundraisingName + " under Record Page of "
							+ recordNameOfRecordPage);
				}
			}

		} else {
			log(LogStatus.ERROR, "No Fundraising Name found of name: " + fundraisingName + " under Record Page of "
					+ recordNameOfRecordPage, YesNo.No);
			result.add("No Fundraising Name found of name: " + fundraisingName + " under Record Page of "
					+ recordNameOfRecordPage);

		}

		return result;
	}

	/**
	 * @author Sourabh Saini
	 * @param projectName
	 * @param fromNavigation
	 * @param buttonName
	 * @param basicSection
	 * @param advanceSection
	 * @param taskSection
	 * @param suggestedTags
	 * @return return true if test case is passed
	 */

	public boolean createActivityTimeline(String projectName, boolean fromNavigation, String buttonName,
			String[][] basicSection, String[][] advanceSection, String[][] taskSection, String[] suggestedTags) {
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		String xPath = "";
		WebElement ele;
		boolean flag = false;

		if (fromNavigation == true) {
			if (npbl.createNavPopUpMinimizeButton(5) != null) {
				CommonLib.click(driver, npbl.createNavPopUpMinimizeButton(5), "Minimize", action.BOOLEAN);
			}
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, NavigationMenuItems.Create.toString(), action.BOOLEAN,
					30)) {
				log(LogStatus.INFO, "Able to Click on " + buttonName + " Going to click on : "
						+ NavigationMenuItems.Create.toString() + " for creation ", YesNo.No);
				ele = npbl.getNavigationLabel(projectName, buttonName, action.BOOLEAN, 10);
				if (ele != null && CommonLib.click(driver, ele, buttonName, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on " + buttonName + " so going for creation", YesNo.No);

				} else {
					log(LogStatus.ERROR,
							"Not Able to Click on " + buttonName + " so cannot create data related to this ",
							YesNo.Yes);
					sa.assertTrue(false,
							"Not Able to Click on " + buttonName + " so cannot create data related to this ");
					return false;
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on " + NavigationMenuItems.Create.toString()
						+ " so cannot click on : " + buttonName + " for creation ", YesNo.Yes);
				sa.assertTrue(false, "Not Able to Click on " + NavigationMenuItems.Create.toString()
						+ " so cannot click on : " + buttonName + " for creation ");
				return false;
			}
		} else {
			if (CommonLib.clickUsingJavaScript(driver, activityTimelineButton(buttonName, 30), buttonName + " button",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on " + buttonName + " button name", YesNo.No);
			} else {
				log(LogStatus.ERROR, "Not able to click on " + buttonName, YesNo.No);
				sa.assertTrue(false, "Not able to click on " + buttonName);
				return false;
			}
		}

		String detail = CommonLib.getAttribute(driver, getNotePopUpSectionDetail("Tasks", 30), "Tasks Section",
				"aria-hidden");

		if (detail.equals("true")) {

		} else {

			clickUsingJavaScript(driver, getSectionBtn("Tasks", 30), "Tasks section", action.SCROLLANDBOOLEAN);
		}

		String detail2 = CommonLib.getAttribute(driver, getNotePopUpSectionDetail("Advanced", 30), "Advanced Section",
				"aria-hidden");

		if (detail2.equals("true")) {

		} else {

			clickUsingJavaScript(driver, getSectionBtn("Advanced", 30), "Advanced section", action.SCROLLANDBOOLEAN);
		}
		CommonLib.ThreadSleep(3000);
		if (basicSection != null) {

			for (String[] val : basicSection) {
				String labelName = val[0];
				String value = val[1];

				if (labelName.contains(excelLabel.Subject.toString())) {
					xPath = "//label[text()='" + labelName + "']/..//input[contains(@data-id,'combobox')]";
					ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
					if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
					} else {
						log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
						sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
						return false;

					}
				} else if (labelName.contains(excelLabel.Notes.toString())) {
					xPath = "//div[label[text()='Notes']]//textarea";
					ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
					if (CommonLib.clickUsingJavaScript(driver, ele, labelName + " paragraph")) {
						log(LogStatus.INFO, "Clicked on " + labelName + " paragraph", YesNo.No);
						ThreadSleep(2000);
						xPath = "//div[label[text()='Notes']]//textarea";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (sendKeys(driver, ele, value, labelName + " paragraph", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " has been passed on " + labelName + " paragraph", YesNo.No);

							CommonLib.ThreadSleep(2000);
						} else {
							log(LogStatus.ERROR, value + " is not passed on " + labelName + " paragraph", YesNo.No);
							sa.assertTrue(false, value + " is not passed on " + labelName + " paragraph");
							return false;
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on " + labelName + " paragraph", YesNo.No);
						sa.assertTrue(false, "Not able to click on " + labelName + " paragraph");
						return false;
					}

				} else if (labelName.equalsIgnoreCase(excelLabel.Related_To.toString())) {
					ArrayList<String> tagList = new ArrayList<String>();

					if (value.contains("<break>")) {
						String[] data = value.split("<break>");
						for (int i = 0; i < data.length; i++) {
							tagList.add(data[i]);
						}
					} else {
						tagList.add(value);
					}

					for (int i = 0; i < tagList.size(); i++) {

						if (tagList.get(i).contains("Prefilled")) {
							String val1 = tagList.get(i);
							String[] tgName = tagList.get(i).split("<Prefilled>");
							xPath = "//lightning-pill[@data-id='" + tgName[0] + "']";
							ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN,
									30);
							if (ele != null) {
								log(LogStatus.INFO, tgName[0] + " is prefilled", YesNo.No);
							} else {
								log(LogStatus.ERROR, tgName[0] + " is not prefilled", YesNo.No);
								sa.assertTrue(false, tgName[0] + " is not prefilled");
								return false;
							}

						} else {
							xPath = "//input[@placeholder='Search Firm, People or Deals']";
							ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN,
									2);
							if (ele == null) {
								xPath = "//h2[contains(text(),'" + buttonName + "')]/../..//*[@title='Tag']";
								ele = CommonLib.FindElement(driver, xPath, labelName + " label",
										action.SCROLLANDBOOLEAN, 30);
								if (CommonLib.clickUsingJavaScript(driver, ele, labelName + " paragraph")) {
									log(LogStatus.INFO, "Clicked on Tag button", YesNo.No);
								} else {
									log(LogStatus.ERROR, "Not able to click on Tag button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Tag button");
									return false;
								}

							}

							xPath = "//input[@placeholder='Search Firm, People or Deals']";
							ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN,
									2);
							if (sendKeys(driver, ele, tagList.get(i), "Tag", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,
										tagList.get(i) + " value has been passed in " + labelName + " field", YesNo.No);
								ThreadSleep(3000);
								xPath = "//ul[@class='drop_ul']//li[text()='" + tagList.get(i) + "']";
								ele = CommonLib.FindElement(driver, xPath, labelName + " label",
										action.SCROLLANDBOOLEAN, 30);
								if (click(driver, ele, tagList + " dropdown", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on " + tagList.get(i) + " value", YesNo.No);
								} else {
									log(LogStatus.ERROR, "Not able to click on " + tagList.get(i) + " value", YesNo.No);
									sa.assertTrue(false, "Not able to click on " + tagList.get(i) + " value");
									return false;
								}

							} else {
								log(LogStatus.ERROR, tagList.get(i) + " value is not passed in " + labelName + " field",
										YesNo.No);
								sa.assertTrue(false,
										tagList.get(i) + " value is not passed in " + labelName + " field");
								return false;
							}
						}
					}
				} else {
					log(LogStatus.ERROR, "Label Name is not correct", YesNo.No);
					sa.assertTrue(false, "Label Name is not correct");
					return false;
				}
			}
		}
		if (advanceSection != null) {
			if (clickUsingJavaScript(driver, getSectionBtn("Advanced", 30), "Advanced section",
					action.SCROLLANDBOOLEAN)) {

				log(LogStatus.INFO, "clicked on Advanced section", YesNo.No);
				for (String[] val : advanceSection) {
					String labelName = val[0];
					String value = val[1];
					// String fieldType=val[2];

					if (labelName.contains("Start Date")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='Start Date Time']/..//label[text()='Date']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}
					} else if (labelName.contains("Start Time")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='Start Date Time']/..//label[text()='Time']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					} else if (labelName.contains("End Date")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='End Date Time']/..//label[text()='Date']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					} else if (labelName.contains("End Time")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='End Date Time']/..//label[text()='Time']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					}

					else if (labelName.contains(excelLabel.Location.toString()) || labelName.contains("Due Date")
							|| labelName.contains("Date")) {

						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
								+ labelName + "']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					}

					else if (labelName.contains(excelLabel.Status.toString())
							|| labelName.contains(excelLabel.Priority.toString())) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
								+ labelName + "']/..//button";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
								+ labelName + "']/..//span[@class='slds-truncate']";
						if (CommonLib.dropDownHandle(driver, ele, xPath, labelName + " dropdown", value)) {
							log(LogStatus.INFO, value + " value has been selected from " + labelName + " field",
									YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not selected from " + labelName + " field",
									YesNo.No);
							sa.assertTrue(false, value + " value is not selected from " + labelName + " field");
							return false;
						}
					} else if (labelName.equalsIgnoreCase("All-Day Event")) {

						if (value.equals("true")) {
							xPath = "//span[text()='All-Day Event']/../../input";
							ele = FindElement(driver, xPath, labelName + " name", action.SCROLLANDBOOLEAN, 30);
							if (isSelected(driver, ele, labelName + " checkbox")) {
								log(LogStatus.INFO, labelName + " checkbox is already selected", YesNo.No);

							} else {
								if (click(driver, ele, labelName + " checkbox", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, labelName + " checkbox has been selected", YesNo.No);
								} else {
									log(LogStatus.ERROR, labelName + " checkbox is not selected", YesNo.No);
									sa.assertTrue(false, labelName + " checkbox is not selected");
									return false;
								}
							}

						} else {
							xPath = "//span[text()='All-Day Event']/../../input";
							ele = FindElement(driver, xPath, labelName + " name", action.SCROLLANDBOOLEAN, 30);
							if (!isSelected(driver, ele, labelName + " checkbox")) {
								log(LogStatus.INFO, labelName + " checkbox is not selected", YesNo.No);

							} else {
								if (click(driver, ele, labelName + " checkbox", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, labelName + " checkbox has been unselected", YesNo.No);
								} else {
									log(LogStatus.ERROR, labelName + " checkbox is not unselected", YesNo.No);
									sa.assertTrue(false, labelName + " checkbox is not unselected");
									return false;
								}
							}
						}
					} else if (labelName.contains("User")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
								+ labelName + "']/..//button";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (click(driver, ele, labelName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on cross icon of value of " + labelName, YesNo.No);
							xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
									+ labelName + "']/..//input";
							ele = CommonLib.FindElement(driver, xPath, labelName + " input", action.SCROLLANDBOOLEAN,
									30);
							if (sendKeys(driver, ele, value, labelName + " input", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, value + " has been passed in " + labelName, YesNo.No);
								xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
										+ labelName + "']/../..//ul//li[text()='" + value + "']";
								ele = CommonLib.FindElement(driver, xPath, value + " list", action.SCROLLANDBOOLEAN,
										30);
								if (clickUsingJavaScript(driver, ele, value + " list element")) {
									log(LogStatus.INFO, "click on the list element of " + value, YesNo.No);
								} else {
									log(LogStatus.ERROR, "Not able to click on the list element of " + value, YesNo.No);
									sa.assertTrue(false, "Not able to click on the list element of " + value);
									return false;
								}

							} else {
								log(LogStatus.ERROR, value + " is not passed in " + labelName, YesNo.No);
								sa.assertTrue(false, value + " is not passed in " + labelName);
								return false;
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on cross icon of value of " + labelName, YesNo.No);
							sa.assertTrue(false, "Not able to click on cross icon of value of " + labelName);
							return false;
						}
					}

					else {
						log(LogStatus.ERROR, "label name is not correct", YesNo.No);
						sa.assertTrue(false, "label name is not correct");
						return false;
					}
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Advanced search section", YesNo.No);
				sa.assertTrue(false, "Not able to click on Advanced search section");
				return false;
			}

		}
		if (taskSection != null) {

			xPath = "//span[text()='Advanced']/parent::button[@aria-expanded='true']";
			ele = FindElement(driver, xPath, "Advance section", action.SCROLLANDBOOLEAN, 3);
			if (ele == null) {
				if (clickUsingJavaScript(driver, getSectionBtn("Advanced", 3), "Advanced section",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on Advanced section", YesNo.No);
				} else {
					log(LogStatus.ERROR, "Not able to click on Advanced section", YesNo.No);
					sa.assertTrue(false, "Not able to click on Advanced section");
					return false;
				}
			}

			if (clickUsingJavaScript(driver, getSectionBtn("Tasks", 30), "Tasks section", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on Tasks section", YesNo.No);

				for (String[] val : taskSection) {
					String labelName = val[0];
					String value = val[1];

					if (labelName.contains(excelLabel.Subject.toString()) || labelName.contains("Due Date")
							|| labelName.contains("Date")) {
						xPath = "//span[text()='Tasks']/ancestor::lightning-accordion[1]//label[text()='" + labelName
								+ "']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}
					} else if (labelName.contains(excelLabel.Status.toString())) {
						xPath = "//span[text()='Tasks']/ancestor::lightning-accordion[1]//label[text()='" + labelName
								+ "']/..//button";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						xPath = "//span[text()='Tasks']/ancestor::lightning-accordion[1]//label[text()='" + labelName
								+ "']/..//span[@class='slds-truncate']";
						if (CommonLib.dropDownHandle(driver, ele, xPath, labelName + " dropdown", value)) {
							log(LogStatus.INFO, value + " value has been selected from " + labelName + " field",
									YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not selected from " + labelName + " field",
									YesNo.No);
							sa.assertTrue(false, value + " value is not selected from " + labelName + " field");
							return false;
						}
					} else {
						log(LogStatus.ERROR, "Label name is not correct", YesNo.No);
						sa.assertTrue(false, "Label name is not correct");
						return false;
					}
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on Tasks section", YesNo.No);
				sa.assertTrue(false, "Not able to click on Tasks section");
				return false;
			}

		}

		if (click(driver, getfooterSaveOrCancelButton("Save", 20), "Save button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "clicked on Save button", YesNo.No);

			if (suggestedTags != null) {
				/*
				 * if (getSuccessMsg(30) != null) { log(LogStatus.INFO,
				 * "Activity timeline record has been created", YesNo.No);
				 */
				ThreadSleep(2000);
				for (int i = 0; i < suggestedTags.length; i++) {

					if (!suggestedTags[0].equals("")) {
						xPath = "//lightning-base-formatted-text[text()='" + suggestedTags[i]
								+ "']/ancestor::th[@data-label='Name']/..//td//input";
						ele = CommonLib.FindElement(driver, xPath, suggestedTags[i] + " sugested Tag",
								action.SCROLLANDBOOLEAN, 30);
						if (click(driver, ele, suggestedTags[i] + " suggested tag", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on " + suggestedTags[i] + " suggested tag checkbox button",
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Not able to click on " + suggestedTags[i] + " suggested tag checkbox button",
									YesNo.No);
							sa.assertTrue(false,
									"Not able to click on " + suggestedTags[i] + " suggested tag checkbox button");
							return false;
						}
					}
				}
				if (click(driver, getfooterTagButton(30), "Tag Button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on footer tag button", YesNo.No);
					ThreadSleep(3000);
					if (crossIconButtonInNotePopUp(8) != null) {
						if (clickUsingJavaScript(driver, crossIconButtonInNotePopUp(8), "close button")) {
							log(LogStatus.INFO, "Note popup has been closed", YesNo.No);
							log(LogStatus.INFO, "Activity timeline record has been created", YesNo.No);
							flag = true;
						} else {
							log(LogStatus.ERROR, "Not able to close the Note popup", YesNo.No);
							sa.assertTrue(false, "Not able to close the Note popup");
							log(LogStatus.ERROR, "Activity timeline record is not created", YesNo.No);
							return false;
						}
					} else {
						return true;
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on footer tag button", YesNo.No);
					sa.assertTrue(false, "Not able to click on footer tag button");
					return false;
				}
				/*
				 * } else { log(LogStatus.ERROR, "Activity timeline record is not created",
				 * YesNo.No); sa.assertTrue(false, "Activity timeline record is not created");
				 * return false; }
				 */
			}

			else {
				/*
				 * if (getSuccessMsg(30) != null) { log(LogStatus.INFO,
				 * "Activity timeline record has been created", YesNo.No); ThreadSleep(1000);
				 */
				refresh(driver);
				ThreadSleep(3000);
				if (crossIconButtonInNotePopUp(8) != null) {
					if (clickUsingJavaScript(driver, crossIconButtonInNotePopUp(8), "close button")) {
						log(LogStatus.INFO, "Note popup has been closed", YesNo.No);
						log(LogStatus.INFO, "Activity timeline record has been created", YesNo.No);
						flag = true;
					} else {
						log(LogStatus.ERROR, "Not able to close the Note popup", YesNo.No);
						sa.assertTrue(false, "Not able to close the Note popup");
						log(LogStatus.ERROR, "Activity timeline record is not created", YesNo.No);
						return false;
					}
				} else {
					return true;
				}

				/*
				 * } else { log(LogStatus.ERROR, "Activity timeline record is not created",
				 * YesNo.No); sa.assertTrue(false, "Activity timeline record is not created");
				 * return false; }
				 */
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Save button", YesNo.No);
			sa.assertTrue(false, "Not able to click on Save button");
			return false;
		}

		return flag;
	}

	/**
	 * @author Sourabh Saini
	 * @param intractionSubjectName
	 * @return return true if test case is passed
	 */

	public boolean verifyRecordOnInteractionCard(String intractionSubjectName) {
		boolean flag = false;
		if (click(driver, getInteractionViewAllBtn(30), "Interaction view all button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on view all button of Interaction", YesNo.No);
			if (getIntractionSubjectName(intractionSubjectName, 30) != null) {
				log(LogStatus.INFO, intractionSubjectName + " intraction Record has been created", YesNo.No);
				flag = true;
			} else {
				log(LogStatus.ERROR, intractionSubjectName + " intraction Record is not created", YesNo.No);
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on view all button of Interaction", YesNo.No);
		}
		return flag;

	}

	/**
	 * @author Sourabh Saini
	 * @param companyTag
	 * @param peopleTag
	 * @param dealTag
	 * @return return Empty ArrayList if test case is passed
	 */

	public ArrayList<String> verifyRecordOnTagged(String[] companyTag, String peopleTag[], String dealTag[]) {
		ArrayList<String> result = new ArrayList<String>();
		if (companyTag != null) {

			if (click(driver, getTaggedRecordName("Companies", 30), "Companies tab", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Companies tab name", YesNo.No);
				for (int i = 0; i < companyTag.length; i++) {
					if (getTaggedRecordName("Companies", companyTag[i], 30) != null) {
						log(LogStatus.INFO, companyTag[i] + " record is available on company tab", YesNo.No);
					} else {
						log(LogStatus.ERROR, companyTag[i] + " record is not available on company tab", YesNo.No);
						result.add(companyTag[i] + " record is not available on company tab");
					}
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Companies tab name", YesNo.No);
				result.add("Not able to click on Companies tab name");
			}
		}
		if (peopleTag != null) {

			if (click(driver, getTaggedRecordName("People", 30), "People tab", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on People tab name", YesNo.No);

				for (int i = 0; i < peopleTag.length; i++) {
					if (getTaggedRecordName("People", peopleTag[i], 30) != null) {
						log(LogStatus.INFO, peopleTag[i] + " record is available on people tab", YesNo.No);
					} else {
						log(LogStatus.ERROR, peopleTag[i] + " record is not available on people tab", YesNo.No);
						result.add(peopleTag[i] + " record is not available on people tab");
					}
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on People tab name", YesNo.No);
				result.add("Not able to click on People tab name");
			}
		}
		if (dealTag != null) {

			if (click(driver, getTaggedRecordName("Deals", 30), "Deals tab", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Deals tab name", YesNo.No);

				for (int i = 0; i < dealTag.length; i++) {
					if (getTaggedRecordName("Deals", dealTag[i], 30) != null) {
						log(LogStatus.INFO, dealTag[i] + " record is available on deal tab", YesNo.No);
					} else {
						log(LogStatus.ERROR, dealTag[i] + " record is not available on deal tab", YesNo.No);
						result.add(dealTag[i] + " record is not available on deal tab");
					}
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Deals tab name", YesNo.No);
				result.add("Not able to click on Deals tab name");
			}
		}
		return result;
	}

	/**
	 * @author Sourabh Saini
	 * @param dueDate
	 * @param subjectName
	 * @param notes
	 * @param editNote
	 * @param addNote
	 * @return return Empty ArrayList if test case is passed
	 */

	public ArrayList<String> verifyRecordOnInteractionCard(String dueDate, IconType icon, String subjectName,
			String notes, boolean editNote, boolean addNote, String[] relatedTo, String[] relatedAssociation) {
		String xPath;
		WebElement ele;
		List<WebElement> elements;
		ArrayList<String> result = new ArrayList<String>();
		if (dueDate != "" && dueDate != null) {
			xPath = "//a[text()='" + subjectName + "']/../preceding-sibling::div//lightning-badge";
			ele = CommonLib.FindElement(driver, xPath, "Due Date", action.SCROLLANDBOOLEAN, 30);
			String date = getText(driver, ele, "Due Date", action.SCROLLANDBOOLEAN);
			String actualDate;
			if (date.contains(",")) {
				String[] val = date.split(",");
				actualDate = val[0];
			} else {
				actualDate = date;
			}
			String[] splittedDate = dueDate.split("/");
			char dayMonth = splittedDate[0].charAt(0);
			char day = splittedDate[1].charAt(0);
			String month;
			if (dayMonth == '0') {
				month = splittedDate[0].replaceAll("0", "");
			} else {
				month = splittedDate[0];
			}
			String finalDay;
			if (day == '0') {
				finalDay = splittedDate[1].replaceAll("0", "");
			} else {
				finalDay = splittedDate[1];
			}

			String expectedDate = month + "/" + finalDay + "/" + splittedDate[2];

			if (expectedDate.trim().equals(actualDate.trim())) {
				log(LogStatus.INFO, "Actual result : " + actualDate + " has been matched with expected result : "
						+ expectedDate + " for Due Date", YesNo.No);
			} else {
				log(LogStatus.ERROR, "Actual result : " + actualDate + " is not matched with expected result : "
						+ expectedDate + " for Due Date", YesNo.No);
				result.add("Actual result : " + actualDate + " is not matched with expected result : " + expectedDate
						+ " for Due Date");
			}

		}

		if (icon != null) {

			xPath = "//a[text()='" + subjectName + "']/../preceding-sibling::div//lightning-icon";
			ele = FindElement(driver, xPath, "Activity icon", action.SCROLLANDBOOLEAN, 20);
			String iconval = getAttribute(driver, ele, "Icon", "title");
			if (icon.toString().equalsIgnoreCase(iconval)) {
				log(LogStatus.INFO, "Actual icon type : " + iconval + " has been matched with expected icon type : "
						+ icon.toString(), YesNo.No);
			} else {
				log(LogStatus.ERROR, "Actual icon type : " + iconval + " is not matched with expected icon type : "
						+ icon.toString(), YesNo.No);
				result.add("Actual icon type : " + iconval + " is not matched with expected icon type : "
						+ icon.toString());
			}

		}

		if (subjectName != null) {
			xPath = "//a[@class=\"interaction_sub subject_text\" and text()='" + subjectName + "']";
			ele = CommonLib.FindElement(driver, xPath, "Subject", action.SCROLLANDBOOLEAN, 30);
			String subName = getText(driver, ele, "Subject", action.SCROLLANDBOOLEAN);
			if (subjectName.equals(subName)) {
				log(LogStatus.INFO, "Actual result : " + subName + " has been matched with expected result : "
						+ subjectName + " for Subject", YesNo.No);
			} else {
				log(LogStatus.ERROR, "Actual result : " + subName + " is not matched with expected result : "
						+ subjectName + " for Subject", YesNo.No);
				result.add("Actual result : " + subName + " is not matched with expected result : " + subjectName
						+ " for Subject");
			}

		}
		if (notes != null) {
			xPath = "//a[@class=\"interaction_sub subject_text\" and text()='" + subjectName
					+ "']/../following-sibling::div[contains(@class,'slds-text-title')]";
			ele = CommonLib.FindElement(driver, xPath, "Notes", action.SCROLLANDBOOLEAN, 30);
			String note = getText(driver, ele, "notes", action.SCROLLANDBOOLEAN);
			if (note.equals(notes.trim().replaceAll(" +", " "))) {
				log(LogStatus.INFO,
						"Actual result : " + note + " has been matched with expected result : " + notes + " for notes",
						YesNo.No);
			} else {
				log(LogStatus.ERROR,
						"Actual result : " + note + " is not matched with expected result : " + notes + " for notes",
						YesNo.No);
				result.add(
						"Actual result : " + note + " is not matched with expected result : " + notes + " for notes");
			}

		}

		/*
		 * if (relatedTo != null) { xPath =
		 * "//a[@class='interaction_sub subject_text' and text()='" + subjectName +
		 * "']/../following-sibling::div[contains(@class,'cls_myPill')]//span[@class='slds-pill__label']";
		 * elements = FindElements(driver, xPath, "Related to elements"); String[]
		 * actualRelatedTo = new String[elements.size()]; for (int i = 0; i <
		 * elements.size(); i++) { actualRelatedTo[i] = getText(driver, elements.get(i),
		 * "related to value", action.SCROLLANDBOOLEAN); } for (int i = 0; i <
		 * relatedTo.length; i++) { int status = 0; for (int j = 0; j <
		 * actualRelatedTo.length; j++) { if (relatedTo[i].equals(actualRelatedTo[j])) {
		 * log(LogStatus.INFO, "Actual result : " + actualRelatedTo[j] +
		 * " has been matched with expected result : " + relatedTo[i] +
		 * " for tagged value", YesNo.No); status++; } } if (status == 0) {
		 * log(LogStatus.ERROR, "Expected result : " + relatedTo[i] +
		 * " is not matched with the actual result for tagged value", YesNo.No);
		 * result.add("Expected result : " + relatedTo[i] +
		 * " is not matched with the actual result for tagged value"); } } }
		 */

		if (editNote == true) {
			xPath = "//a[text()='" + subjectName + "']/../preceding-sibling::div//button[@title='Edit Note']";
			ele = CommonLib.FindElement(driver, xPath, "Notes", action.SCROLLANDBOOLEAN, 30);
			if (ele != null) {
				log(LogStatus.INFO, "Edit Notes button is visible", YesNo.No);
			} else {
				log(LogStatus.ERROR, "Edit Notes button is not visible", YesNo.No);
				result.add("Edit Notes button is not visible");
			}
		}
		if (addNote == true) {
			xPath = "//a[text()='" + subjectName + "']/../preceding-sibling::div//button[@title='Add Note']";
			ele = CommonLib.FindElement(driver, xPath, "Notes", action.SCROLLANDBOOLEAN, 30);
			if (ele != null) {
				log(LogStatus.INFO, "Add Notes button is visible", YesNo.No);
			} else {
				log(LogStatus.ERROR, "Add Notes button is not visible", YesNo.No);
				result.add("Add Notes button is not visible");
			}
		}

		/*
		 * if (relatedAssociation != null) { xPath =
		 * "//a[@class=\"interaction_sub subject_text\" and text()='" + subjectName +
		 * "']/../following-sibling::div[contains(@class,'cls_myPill')]//span[@class=\"slds-pill__label\" and starts-with(@title,'+')]"
		 * ; ele = FindElement(driver, xPath, "extra tagged", action.SCROLLANDBOOLEAN,
		 * 20); if (click(driver, ele, "extra tagged", action.SCROLLANDBOOLEAN)) {
		 * log(LogStatus.INFO, "clicked on tagged value", YesNo.No);
		 * 
		 * xPath = "//h2[text()='Tagged']/../following-sibling::div//a"; elements =
		 * FindElements(driver, xPath, "Tagged element"); String[]
		 * actualExtraTaggedRecord = new String[elements.size()]; for (int i = 0; i <
		 * elements.size(); i++) { actualExtraTaggedRecord[i] = getText(driver,
		 * elements.get(i), "Extra Tagged", action.SCROLLANDBOOLEAN); }
		 * 
		 * for (int i = 0; i < relatedAssociation.length; i++) { int status = 0; for
		 * (int j = 0; j < actualExtraTaggedRecord.length; j++) {
		 * 
		 * if (relatedAssociation[i].equals(actualExtraTaggedRecord[j])) {
		 * log(LogStatus.INFO, "Actual result of related association: " +
		 * actualExtraTaggedRecord[j] +
		 * " has been matched with the expected result of related association : " +
		 * relatedAssociation[i], YesNo.No); status++; }
		 * 
		 * } if (status == 0) { log(LogStatus.ERROR, "The result: " +
		 * relatedAssociation[i] + " is not available on related association",
		 * YesNo.No); result.add("The result of tagged: " + relatedAssociation[i] +
		 * " is not available on related association"); } }
		 * 
		 * xPath = "//h2[text()='Tagged']/../button[@title='Close']"; ele =
		 * FindElement(driver, xPath, "close button of Tagged popup",
		 * action.SCROLLANDBOOLEAN, 10); if (click(driver, ele, xPath,
		 * action.SCROLLANDBOOLEAN)) { log(LogStatus.INFO,
		 * "Clicked on close button of tagged", YesNo.No); } else { log(LogStatus.ERROR,
		 * "Not able to click on close button of tagged", YesNo.No);
		 * result.add("Not able to click on close button of tagged"); }
		 * 
		 * } else { log(LogStatus.ERROR, "Not able to click on tagged value", YesNo.No);
		 * result.add("Not able to click on tagged value"); } }
		 */

		return result;
	}

	/**
	 * 
	 * @author Ankur Huria
	 * @param projectName
	 * @param searchItem
	 * @return return true if it found error msg
	 */
	public boolean searchAnItemAndFoundErrorMsgInResearch(String projectName, String searchItem) {

		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		boolean flag = false;
		if (npbl.createNavPopUpMinimizeButton(5) != null) {
			CommonLib.click(driver, npbl.createNavPopUpMinimizeButton(5), "Minimize", action.BOOLEAN);
		}
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, NavigationMenuItems.Research.toString(), action.BOOLEAN,
				30)) {
			log(LogStatus.INFO, "Able to Click on Research Going to click on : "
					+ NavigationMenuItems.Research.toString() + " for Research an Item", YesNo.No);

			if (CommonLib.sendKeys(driver, npbl.researchSearchBox(20), searchItem, "Research Search Box",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Enter Value in Research Search Box: " + searchItem, YesNo.No);

				if (click(driver, npbl.researchButton(20), "Research Button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Research Button", YesNo.No);

					if (CommonLib.getText(driver, npbl.researchErrorMsg(20), "Research Error Msg", action.BOOLEAN)
							.contains("Your search term must have 2 or more characters.")) {
						log(LogStatus.INFO, "Error Msg Found: " + "  Your search term must have 2 or more characters.",
								YesNo.No);
						flag = true;
					} else {
						log(LogStatus.ERROR,
								"Not Able to Find The Error Msg on Research after Text Pass: " + searchItem, YesNo.Yes);
						sa.assertTrue(false,
								"Not Able to Find The Error Msg on Research after Text Pass: " + searchItem);
					}
				}

				else {
					log(LogStatus.ERROR, "Not Able to Click on Research Button", YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Research Button");
				}

			} else {

				log(LogStatus.ERROR, "Not Able to Enter Value in Research Search Box: " + searchItem, YesNo.Yes);
				sa.assertTrue(false, "Not Able to Enter Value in Research Search Box: " + searchItem);

			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on " + NavigationMenuItems.Research.toString()
					+ " so cannot click on it for Research an Item", YesNo.Yes);
			sa.assertTrue(false, "Not Able to Click on " + NavigationMenuItems.Research.toString()
					+ " so cannot click on it for Research an Item");

		}
		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param projectName
	 * @param searchString
	 * @return return true if it found the text you have searched, otherwise false
	 */
	public boolean searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(String projectName, String searchString) {

		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);

		HashMap<String, Integer> gridNameAndCount = new HashMap<>();
		HashMap<String, Integer> sideNavNameAndCount = new HashMap<>();

		ArrayList<String> output = new ArrayList<String>();
		int status = 0;
		int loopCount = 0;
		try {

			if (npbl.createNavPopUpMinimizeButton(5) != null) {
				CommonLib.click(driver, npbl.createNavPopUpMinimizeButton(5), "Minimize", action.BOOLEAN);
			}
//			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName,
//					NavigationMenuItems.Research.toString().replace("_", " "), action.BOOLEAN, 30)) {
//				log(LogStatus.INFO, "Able to Click on Research Going to click on : "
//						+ NavigationMenuItems.Research.toString() + " for Research an Item", YesNo.No);

//				if (CommonLib.sendKeys(driver, npbl.researchSearchBox(20), searchString, "Research Search Box",
//						action.BOOLEAN)) {
//				if (CommonLib.sendKeys(driver, npbl.researchSearchBox(20), searchString, "Research Search Box",
//						action.BOOLEAN)) {

//					log(LogStatus.INFO, "Enter Value in Research Search Box: " + searchString, YesNo.No);
//
//					if (click(driver, npbl.researchButton(20), "Research Button", action.SCROLLANDBOOLEAN)) {
//						log(LogStatus.INFO, "Clicked on Research Button", YesNo.No);

			if (noResultMsgInResearch(3) == null) {
				CommonLib.refresh(driver);
				CommonLib.ThreadSleep(3000);
				List<String> sideNavCountExceptAllCategories = researchSideNavCountResultsExceptAllCategories().stream()
						.map(x -> x.getText().trim().replace("New Items", "").replace(":", "")
								.replaceAll("[\\t\\n\\r]+", "").trim())
						.collect(Collectors.toList());

				List<String> sideNavNamesWhichHasCountExceptAllCategories = researchSideNavLabelsWhichHasCountResultsExceptAllCategories()
						.stream().map(x -> x.getText().replace("New Items", "").replace(":", "")
								.replaceAll("[\\t\\n\\r]+", "").replaceAll("\\d", ""))
						.collect(Collectors.toList());

				List<String> researchResultsGridCounts = researchResultsGridCounts().stream()
						.map(x -> x.getText().trim()).collect(Collectors.toList());
				List<String> researchResultsGridNames = researchResultsGridCounts().stream()
						.map(x -> x.getText().trim().substring(0, x.getText().trim().indexOf(" (")))
						.collect(Collectors.toList());

				int researchAllCategoriesCount = 0;
				if (researchAllCategoriesCount(10) != null) {
					researchAllCategoriesCount = Integer.parseInt(researchAllCategoriesCount(10).getText().trim()
							.replace("New Items", "").replaceAll("[\\t\\n\\r]+", "").replace(":", "").trim());
				}
				int sideNavTotalCount = 0;
				List<Integer> sideNavCountExceptAllCategoriesList = new ArrayList<Integer>();
				for (String sideNavCount : sideNavCountExceptAllCategories) {
					sideNavTotalCount = sideNavTotalCount + Integer.parseInt(sideNavCount);
					sideNavCountExceptAllCategoriesList.add(Integer.parseInt(sideNavCount));
				}

				int gridTotalCount = 0;
				List<Integer> gridCountsList = new ArrayList<Integer>();
				for (String gridCounts : researchResultsGridCounts) {
					gridTotalCount = Integer
							.parseInt(gridCounts.substring(gridCounts.indexOf("(") + 1, gridCounts.indexOf(")")))
							+ gridTotalCount;
					gridCountsList.add(Integer
							.parseInt(gridCounts.substring(gridCounts.indexOf("(") + 1, gridCounts.indexOf(")"))));

				}

				if (sideNavTotalCount == gridTotalCount) {
					log(LogStatus.INFO, "----Total Count of Grids and Side Nav are Same and i.e. " + gridTotalCount
							+ " for Record Search " + searchString + "----", YesNo.No);
					log(LogStatus.INFO,
							"----Now Going to Verify with All Categories for Record Search " + searchString + "----",
							YesNo.No);

					if (researchAllCategoriesCount == gridTotalCount) {
						log(LogStatus.INFO,
								"----Total Count of Grids and Side Nav and All Categories are Same and i.e. "
										+ researchAllCategoriesCount + " for Record Search " + searchString + "----",
								YesNo.No);
						status++;

						try {

							if (sideNavNamesWhichHasCountExceptAllCategories
									.size() == sideNavCountExceptAllCategoriesList.size()
									&& sideNavCountExceptAllCategoriesList.size() == researchResultsGridNames.size()
									&& researchResultsGridNames.size() == gridCountsList.size()) {

								for (int i = 0; i < researchResultsGridNames.size(); i++) {
									gridNameAndCount.put(researchResultsGridNames.get(i), gridCountsList.get(i));
								}

								for (int i = 0; i < sideNavNamesWhichHasCountExceptAllCategories.size(); i++) {
									sideNavNameAndCount.put(sideNavNamesWhichHasCountExceptAllCategories.get(i),
											sideNavCountExceptAllCategoriesList.get(i));
								}

								for (String gridName : gridNameAndCount.keySet()) {

									String navName = gridName;
									if (gridName.equalsIgnoreCase("Contact")) {
										navName = gridName + "s";
									}
									if (gridNameAndCount.get(gridName).equals(sideNavNameAndCount.get(navName))) {
										log(LogStatus.INFO, "Counts for " + gridName + " matched and i.e.: "
												+ gridNameAndCount.get(gridName), YesNo.No);
										status++;
									} else {

										log(LogStatus.ERROR,
												"Counts for " + gridName + " doesn't matched, GridCount: "
														+ gridNameAndCount.get(gridName) + " and SideNavCount: "
														+ sideNavNameAndCount.get(gridName),
												YesNo.Yes);
										sa.assertTrue(false,
												"Counts for " + gridName + " doesn't matched, GridCount: "
														+ gridNameAndCount.get(gridName) + " and SideNavCount: "
														+ sideNavNameAndCount.get(gridName));
									}
									loopCount++;
								}

							} else {

								log(LogStatus.ERROR,
										"Either of List Counts not match for Grids or From SideNav, So not able to validate the result count side by side of Grid and Side Nav",
										YesNo.Yes);
								sa.assertTrue(false,
										"Either of List Counts not match for Grids or From SideNav, So not able to validate the result count side by side of Grid and Side Nav");
							}

						} catch (Exception e) {

							log(LogStatus.ERROR, "Exception occured: " + e.getMessage(), YesNo.No);
							sa.assertTrue(false, "Exception occured: " + e.getMessage());
							return false;
						}

					} else {

						log(LogStatus.ERROR,
								"----Total Count of Grids and Side Nav and All Categories are not Same, Total grid Count: "
										+ gridTotalCount + " and Total Nav Count: " + sideNavTotalCount
										+ "and Total All Categories Count " + researchAllCategoriesCount
										+ " for Record Search " + searchString + "----",
								YesNo.Yes);
						sa.assertTrue(false,
								"----Total Count of Grids and Side Nav and All Categories are not Same, Total grid Count: "
										+ gridTotalCount + " and Total Nav Count: " + sideNavTotalCount
										+ "and Total All Categories Count " + researchAllCategoriesCount
										+ " for Record Search " + searchString + "----");

					}

				} else {

					log(LogStatus.ERROR,
							"----Total Count of Grids and Side Nav are not Same, Total grid Count: " + gridTotalCount
									+ " and Total Nav Count: " + sideNavTotalCount + " for Record Search "
									+ searchString + "----",
							YesNo.Yes);
					sa.assertTrue(false,
							"----Total Count of Grids and Side Nav are not Same, Total grid Count: " + gridTotalCount
									+ " and Total Nav Count: " + sideNavTotalCount + " for Record Search "
									+ searchString + "----");

				}

			} else {
				log(LogStatus.ERROR, "No Result Found for the Text: " + searchString, YesNo.Yes);
				sa.assertTrue(false, "No Result Found for the Text: " + searchString);
			}

//					}
//
//					else {
//						log(LogStatus.ERROR, "Not Able to Click on Research Button", YesNo.Yes);
//						sa.assertTrue(false, "Not Able to Click on Research Button");
//					}

//				} else {
//
//					log(LogStatus.ERROR, "Not Able to Enter Value in Research Search Box: " + searchString, YesNo.Yes);
//					sa.assertTrue(false, "Not Able to Enter Value in Research Search Box: " + searchString);
//
//				}
//			} else {
//				log(LogStatus.ERROR, "Not Able to Click on " + NavigationMenuItems.Research.toString()
//						+ " so cannot click on it for Research an Item", YesNo.Yes);
//				sa.assertTrue(false, "Not Able to Click on " + NavigationMenuItems.Research.toString()
//						+ " so cannot click on it for Research an Item");
//
//			}

			if (status == loopCount + 1)
				return true;
			else
				return false;

		} catch (Exception e) {
			log(LogStatus.ERROR, "Exception: " + e.getMessage(), YesNo.Yes);
			return false;
		}
	}

	/**
	 * @author Ankur Huria
	 * @param projectName
	 * @param searchString
	 * @return return true if it found the text you have searched, otherwise false
	 */
	public boolean searchAnItemInResearchAndVerify(String projectName, String searchString) {

		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		int loopCount = 0;
		int status = 0;
		int resultLoopCount = 0;
		String searchItem = searchString.trim().replaceAll(" +", " ");
		if (npbl.createNavPopUpMinimizeButton(5) != null) {
			CommonLib.click(driver, npbl.createNavPopUpMinimizeButton(5), "Minimize", action.BOOLEAN);
		}
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, NavigationMenuItems.Research.toString(), action.BOOLEAN,
				30)) {
			log(LogStatus.INFO, "Able to Click on Research Going to click on : "
					+ NavigationMenuItems.Research.toString() + " for Research an Item", YesNo.No);

			if (CommonLib.sendKeys(driver, npbl.researchSearchBox(20), searchString, "Research Search Box",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Enter Value in Research Search Box: " + searchString, YesNo.No);

				if (click(driver, npbl.researchButton(20), "Research Button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Research Button", YesNo.No);

					if (noResultMsgInResearch(8) == null) {
						CommonLib.refresh(driver);
						CommonLib.ThreadSleep(5000);
						List<String> sideNavLinkText = researchSideNavLinksWithResults().stream()
								.map(x -> x.getText().trim().replace("New Items", "").replaceAll("[\\t\\n\\r]+", " "))
								.collect(Collectors.toList());
						int navLinkLoop = 0;
						for (WebElement sideNavLink : researchSideNavLinksWithResults())

						{
							String navText = sideNavLinkText.get(navLinkLoop).trim();
							if (clickUsingJavaScript(driver, sideNavLink, "sideNavLink: " + navText,
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on NavLink: " + navText, YesNo.No);
								List<String> searchResultsInTextForm = null;
								searchResultsInTextForm = searchItemsInResearch().stream()
										.map(x -> x.getText().toLowerCase().replace("\n", " "))
										.collect(Collectors.toList());
								if (!searchResultsInTextForm.isEmpty()) {

									String[] searchItemArray;
									if (searchItem.startsWith("\"") && searchItem.endsWith("\"")) {
										searchItemArray = new String[1];
										searchItemArray[0] = searchString;
										searchItem = searchItem.replace("\"", "");

									} else {
										searchItemArray = searchItem.split(" ");
									}
									if (searchItemArray.length > 1) {
										log(LogStatus.INFO, "Total No. of Records of searched Item: " + searchString
												+ " are " + searchResultsInTextForm.size(), YesNo.No);
										int loopReset = 0;
										int recordNumber;
										recordNumber = 0;
										loopCount = 0;
										for (String searchResultInTextForm : searchResultsInTextForm) {

											loopReset = 0;
											recordNumber = loopCount + 1;
											int i = 0;
											for (i = 0; i < searchItemArray.length; i++) {
												if (searchResultInTextForm.contains(searchItemArray[i].toLowerCase())) {
													log(LogStatus.INFO,
															"Text Found: " + searchItemArray[i] + " in Record no. "
																	+ recordNumber + " of NavLink: " + navText
																	+ " of Record: " + searchString,
															YesNo.No);
													status++;
													loopReset++;
													break;
												}

											}
											if (loopReset == 0) {
												log(LogStatus.ERROR,
														"No Text Found: " + searchString + " in Record No. "
																+ recordNumber + " of NavLink: " + navText
																+ ", Actual Row: " + searchResultInTextForm,
														YesNo.Yes);
											}

											loopCount++;
										}
										resultLoopCount = resultLoopCount + loopCount;

									}

									else {

										int recordNumber;
										recordNumber = 0;
										loopCount = 0;
										for (String searchResultInTextForm : searchResultsInTextForm) {
											recordNumber = loopCount + 1;

											if (searchResultInTextForm.contains(searchItem.toLowerCase())) {
												log(LogStatus.INFO,
														"Text Found: " + searchItem + " in Record no. " + recordNumber
																+ " of NavLink: " + navText + " of Record: "
																+ searchItem,
														YesNo.No);
												status++;
											}

											else {
												log(LogStatus.ERROR,
														"No Text Found: " + searchItem + " in Record No. "
																+ recordNumber + " of NavLink: " + navText
																+ ", Actual Row: " + searchResultInTextForm,
														YesNo.Yes);
												sa.assertTrue(false,
														"No Text Found: " + searchItem + " in Record No. "
																+ recordNumber + " of NavLink: " + navText
																+ ", Actual Row: " + searchResultInTextForm);
											}

											loopCount++;
										}
										resultLoopCount = resultLoopCount + loopCount;
									}

								} else {
									log(LogStatus.ERROR,
											"Not Found Any Result on Research after Text Pass: " + searchString,
											YesNo.Yes);
									sa.assertTrue(false,
											"Not Found Any Result on Research after Text Pass: " + searchString);
								}

							} else {

								log(LogStatus.ERROR, "Not Able to Click on NavLink: " + navText, YesNo.Yes);
								sa.assertTrue(false, "Not Able to Click on NavLink: " + navText);

							}

							navLinkLoop++;
						}

					} else {
						log(LogStatus.ERROR, "No Result Found for the Text: " + searchString, YesNo.Yes);
						sa.assertTrue(false, "No Result Found for the Text: " + searchString);
					}

				}

				else {
					log(LogStatus.ERROR, "Not Able to Click on Research Button", YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Research Button");
				}

			} else {

				log(LogStatus.ERROR, "Not Able to Enter Value in Research Search Box: " + searchString, YesNo.Yes);
				sa.assertTrue(false, "Not Able to Enter Value in Research Search Box: " + searchString);

			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on " + NavigationMenuItems.Research.toString()
					+ " so cannot click on it for Research an Item", YesNo.Yes);
			sa.assertTrue(false, "Not Able to Click on " + NavigationMenuItems.Research.toString()
					+ " so cannot click on it for Research an Item");

		}

		if (resultLoopCount == status && status != 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @author Ankur Huria
	 * @param projectName
	 * @param searchString
	 * @return return true if it found the text you have searched, otherwise false
	 */
	public boolean searchAnItemInResearchAndVerifyItsCountInGridNotMoreThan5(String projectName, String searchString) {

		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);

		int loopCount = 0;
		int status = 0;
		try {

			if (npbl.createNavPopUpMinimizeButton(5) != null) {
				CommonLib.click(driver, npbl.createNavPopUpMinimizeButton(5), "Minimize", action.BOOLEAN);
			}
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, NavigationMenuItems.Research.toString(),
					action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on Research Going to click on : "
						+ NavigationMenuItems.Research.toString() + " for Research an Item", YesNo.No);

				if (CommonLib.sendKeys(driver, npbl.researchSearchBox(20), searchString, "Research Search Box",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Enter Value in Research Search Box: " + searchString, YesNo.No);

					if (click(driver, npbl.researchButton(20), "Research Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Research Button", YesNo.No);

						if (noResultMsgInResearch(3) == null) {
							CommonLib.refresh(driver);
							CommonLib.ThreadSleep(3000);

							List<String> researchResultsGridNames = researchResultsGridCounts().stream()
									.map(x -> x.getText().trim()).collect(Collectors.toList());

							for (String gridName : researchResultsGridNames) {
								String gridNameAfterTrim = gridName.substring(0, gridName.indexOf(" ("));

								int gridCount = recordsUnderGridInResearchResults(gridNameAfterTrim).size();

								if (gridCount <= 5 && gridCount >= 1) {
									log(LogStatus.INFO,
											"----Total Count of Grids are less than or equal to 5 and i.e. " + gridCount
													+ " in section: " + gridNameAfterTrim + " for Record Search "
													+ searchString + "----",
											YesNo.No);
									status++;
								} else {

									log(LogStatus.ERROR,
											"----Total Count of Grids are not less than or equal to 5 and i.e. "
													+ gridCount + " in section: " + gridNameAfterTrim
													+ " for Record Search " + searchString + "----",
											YesNo.Yes);
									sa.assertTrue(false,
											"----Total Count of Grids are not less than or equal to 5 and i.e. "
													+ gridCount + " in section: " + gridNameAfterTrim
													+ " for Record Search " + searchString + "----");
								}

								loopCount++;
							}

						} else {
							log(LogStatus.ERROR, "No Result Found for the Text: " + searchString, YesNo.Yes);
							sa.assertTrue(false, "No Result Found for the Text: " + searchString);
						}

					}

					else {
						log(LogStatus.ERROR, "Not Able to Click on Research Button", YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on Research Button");
					}

				} else {

					log(LogStatus.ERROR, "Not Able to Enter Value in Research Search Box: " + searchString, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Enter Value in Research Search Box: " + searchString);

				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on " + NavigationMenuItems.Research.toString()
						+ " so cannot click on it for Research an Item", YesNo.Yes);
				sa.assertTrue(false, "Not Able to Click on " + NavigationMenuItems.Research.toString()
						+ " so cannot click on it for Research an Item");

			}

			if (loopCount == status && status != 0)
				return true;

			else
				return false;

		} catch (Exception e) {
			log(LogStatus.ERROR, "Exception: " + e.getMessage(), YesNo.Yes);
			return false;
		}
	}

	/**
	 * @author Sourabh Saini
	 * @param name
	 * @param title
	 * @param deals
	 * @param meetingAndCalls
	 * @param email
	 * @return return Empty ArrayList if test case is passed
	 */

	public ArrayList<String> verifyRecordOnContactSectionAcuity(String name, String title, String deals,
			String meetingAndCalls, String email) {
		String xPath = "";
		WebElement ele = null;
		ArrayList<String> result = new ArrayList<String>();
		if (name != null && name != "") {

			xPath = "//td[@data-label='Name']//a[text()='" + name + "']";
			ele = CommonLib.FindElement(driver, xPath, "Name", action.SCROLLANDBOOLEAN, 30);
			if (ele != null) {
				log(LogStatus.INFO, name + " is available in contact section", YesNo.No);
				sa.assertTrue(true, name + " is available in contact section");

				if (title != null && title != "") {
					xPath = "//a[text()='" + name
							+ "']/ancestor::td[@data-label='Name']/..//td[@data-label='Title']//span";
					ele = CommonLib.FindElement(driver, xPath, "title", action.SCROLLANDBOOLEAN, 30);
					String actualTitle = getText(driver, ele, "title", action.SCROLLANDBOOLEAN);
					if (title.equalsIgnoreCase(actualTitle)) {
						log(LogStatus.INFO, "Actual result " + actualTitle
								+ " of Title has been matched with Expected result : " + title, YesNo.No);
					} else {
						log(LogStatus.ERROR, "Actual result " + actualTitle
								+ " of Title is not matched with Expected result : " + title, YesNo.No);
						result.add("Actual result " + actualTitle + " of Title is not matched with Expected result : "
								+ title);
					}
				}

				if (deals != null && deals != "") {
					xPath = "//a[text()='" + name
							+ "']/ancestor::td[@data-label='Name']/..//td[@data-label='Deals']//span";
					ele = CommonLib.FindElement(driver, xPath, "deal", action.SCROLLANDBOOLEAN, 30);
					String actualDeal = getText(driver, ele, "deal", action.SCROLLANDBOOLEAN);
					if (deals.equalsIgnoreCase(actualDeal)) {
						log(LogStatus.INFO, "Actual result : " + actualDeal
								+ " of deal has been matched with Expected resut : " + deals, YesNo.No);
					} else {
						log(LogStatus.ERROR, "Actual result : " + actualDeal
								+ " of deal are not matched with Expected result : " + deals, YesNo.No);
						result.add("Actual result : " + actualDeal + " of deal are not matched with Expected result : "
								+ deals);
					}
				}

				if (meetingAndCalls != null && meetingAndCalls != "") {
					xPath = "//a[text()='" + name
							+ "']/ancestor::td[@data-label='Name']/..//td[@data-label='Meetings and Calls']//span";
					ele = CommonLib.FindElement(driver, xPath, "meeting and call", action.SCROLLANDBOOLEAN, 30);
					String actualmeetingAndCalls = getText(driver, ele, "meeting and call", action.SCROLLANDBOOLEAN);
					if (meetingAndCalls.equalsIgnoreCase(actualmeetingAndCalls)) {
						log(LogStatus.INFO, "Actual result " + actualmeetingAndCalls
								+ " of meeting and call has been matched with Expected result : " + meetingAndCalls,
								YesNo.No);
					} else {
						log(LogStatus.ERROR, "Actual result " + actualmeetingAndCalls
								+ " of meeting and call are not matched with Expected result : " + meetingAndCalls,
								YesNo.No);
						result.add("Actual result " + actualmeetingAndCalls
								+ " of meeting and call are not matched with Expected result : " + meetingAndCalls);
					}
				}

				if (email != null && email != "") {
					xPath = "//a[text()='" + name
							+ "']/ancestor::td[@data-label='Name']/..//td[@data-label='Emails']//span";
					ele = CommonLib.FindElement(driver, xPath, "email", action.SCROLLANDBOOLEAN, 30);
					String actualEmail = getText(driver, ele, "email", action.SCROLLANDBOOLEAN);
					if (email.equalsIgnoreCase(actualEmail)) {
						log(LogStatus.INFO, "Actual result " + actualEmail
								+ " of email has been matched with Expected result : " + email, YesNo.No);
					} else {
						log(LogStatus.ERROR, "Actual result " + actualEmail
								+ " of email are not matched with Expected result : " + email, YesNo.No);
						result.add("Actual result " + actualEmail + " of email are not matched with Expected result : "
								+ email);
					}
				}
			} else {
				log(LogStatus.ERROR, name + " is not available in contact section", YesNo.No);
				result.add(name + " is not available in contact section");
				sa.assertTrue(false, name + " is not available in contact section");
			}
		} else {
			log(LogStatus.ERROR, "Please provide the contact name. Contat name should not be null or blank", YesNo.No);
			result.add("Please provide the contact name. Contat name should not be null or blank");
		}
		return result;
	}

	public ArrayList<String> verifyNotesPopupWithPrefilledValueAndOnSameUrl(String url, String subject, String Notes,
			String[] tag) {
		String xPath;
		WebElement ele;
		ArrayList<String> result = new ArrayList<String>();

		String currentUrl = getURL(driver, 10);
		ThreadSleep(4000);

		if (url.equals(currentUrl)) {
			log(LogStatus.INFO, "popun is open in the same page", YesNo.No);
			if (subject != null && subject != "") {
				String actualSubject = getAttribute(driver, getSubjectInput(15), "Subject", "value");

				log(LogStatus.INFO, "Successfully get the value from Subject field", YesNo.No);
				if (subject.equals(actualSubject)) {
					log(LogStatus.INFO, "Subject value has been verify", YesNo.No);
				} else {
					log(LogStatus.ERROR, "Subject value is not verify", YesNo.No);
					result.add("Subject value is not verify");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on subject textbox", YesNo.No);
				result.add("Not able to click on subject textbox");
			}

			if (Notes != null && Notes != "") {
				String actualNotes = getText(driver, getNotesText(20), "Notes", action.SCROLLANDBOOLEAN);
				if (Notes.contains(actualNotes)) {
					log(LogStatus.INFO, "Notes value has been verified", YesNo.No);
				} else {
					log(LogStatus.ERROR, "Notes value is not verified", YesNo.No);
					result.add("Notes value is not verified");
				}
			}

			if (tag != null && tag.length != 0) {
				for (int i = 0; i < tag.length; i++) {
					xPath = "//lightning-pill//span[text()='" + tag[i] + "']";
					ele = FindElement(driver, xPath, tag[i] + " tag", action.SCROLLANDBOOLEAN, 15);
					if (ele != null) {
						log(LogStatus.INFO, tag[i] + " tag has been verified", YesNo.No);
					} else {
						log(LogStatus.ERROR, tag[i] + " tag is not verified", YesNo.No);
						result.add(tag[i] + " tag is not verified");
					}
				}
			}
		} else {
			log(LogStatus.ERROR, "Popup is not open on the same page", YesNo.No);
			result.add(" Popup is not open on the same page");
		}
		return result;
	}

	/**
	 * @author Sourabh Saini
	 * @param projectName
	 * @param basicSection
	 * @param advanceSection
	 * @param taskSection
	 * @param suggestedTags
	 * @param removeTagName
	 * @return return true if test case is passed
	 */

	public boolean updateActivityTimelineRecord(String projectName, String[][] basicSection, String[][] advanceSection,
			String[][] taskSection, String[] suggestedTags, String[] removeTagName) {
		String xPath = "";
		WebElement ele;
		boolean flag = false;
		ThreadSleep(12000);

		if (removeTagName != null && removeTagName.length != 0) {
			for (int i = 0; i < removeTagName.length; i++) {
				ele = crossButtonOfRelatedAssociation(removeTagName[i], 10);
				if (ele != null) {
					if (CommonLib.clickUsingJavaScript(driver, ele, removeTagName[i] + " tag name")) {
						log(LogStatus.INFO, "clicked on cross button of " + removeTagName[i], YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to click on cross button of " + removeTagName[i], YesNo.No);
						sa.assertTrue(false, "Not able to click on cross button of " + removeTagName[i]);
						return false;
					}
				} else {
					log(LogStatus.ERROR, "Not able to get the element of " + removeTagName[i], YesNo.No);
					sa.assertTrue(false, "Not able to get the element of " + removeTagName[i]);
					return false;
				}
			}
		}

		String detail = CommonLib.getAttribute(driver, getNotePopUpSectionDetail("Tasks", 30), "Tasks Section",
				"aria-hidden");

		if (detail.equals("true")) {

		} else {

			clickUsingJavaScript(driver, getSectionBtn("Tasks", 30), "Tasks section", action.SCROLLANDBOOLEAN);
		}

		String detail2 = CommonLib.getAttribute(driver, getNotePopUpSectionDetail("Advanced", 30), "Advanced Section",
				"aria-hidden");

		if (detail2.equals("true")) {

		} else {

			clickUsingJavaScript(driver, getSectionBtn("Advanced", 30), "Advanced section", action.SCROLLANDBOOLEAN);
		}

		if (basicSection != null) {

			for (String[] val : basicSection) {
				String labelName = val[0];
				String value = val[1];

				if (labelName.contains(excelLabel.Subject.toString())) {
					xPath = "//label[text()='" + labelName + "']/..//input[contains(@data-id,'combobox')]";
					ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
					if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
					} else {
						log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
						sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
						return false;

					}
				} else if (labelName.contains(excelLabel.Notes.toString())) {
					xPath = "//div[label[text()='Notes']]//textarea";
					ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.BOOLEAN, 30);
					if (CommonLib.clickUsingJavaScript(driver, ele, labelName + " paragraph")) {
						log(LogStatus.INFO, "Clicked on " + labelName + " paragraph", YesNo.No);
						ThreadSleep(2000);

						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						ele.sendKeys(Keys.CONTROL + "A");
						ThreadSleep(1000);
						ele.sendKeys(Keys.BACK_SPACE);
						CommonLib.ThreadSleep(3000);
						if (sendKeys(driver, ele, value, labelName + " paragraph", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " has been passed on " + labelName + " paragraph", YesNo.No);
							CommonLib.ThreadSleep(2000);
						} else {
							log(LogStatus.ERROR, value + " is not passed on " + labelName + " paragraph", YesNo.No);
							sa.assertTrue(false, value + " is not passed on " + labelName + " paragraph");
							return false;
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on " + labelName + " paragraph", YesNo.No);
						sa.assertTrue(false, "Not able to click on " + labelName + " paragraph");
						return false;
					}

				} else if (labelName.equalsIgnoreCase(excelLabel.Related_To.toString())) {
					ArrayList<String> tagList = new ArrayList<String>();

					if (value.contains("<break>")) {
						String[] data = value.split("<break>");
						for (int i = 0; i < data.length; i++) {
							tagList.add(data[i]);
						}
					} else {
						tagList.add(value);
					}

					for (int i = 0; i < tagList.size(); i++) {

						xPath = "//input[@placeholder='Search Firm, People or Deals']";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 2);
						if (ele == null) {
							xPath = "//lightning-icon[@title=\"Tag\" and contains(@class,'mt15')]//lightning-primitive-icon";
							ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN,
									30);
							if (CommonLib.clickUsingJavaScript(driver, ele, labelName + " paragraph")) {
								log(LogStatus.INFO, "Clicked on Tag button", YesNo.No);
							} else {
								log(LogStatus.ERROR, "Not able to click on Tag button", YesNo.No);
								sa.assertTrue(false, "Not able to click on Tag button");
								return false;
							}

						}

						xPath = "//input[@placeholder='Search Firm, People or Deals']";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 2);
						if (sendKeys(driver, ele, tagList.get(i), "Tag", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, tagList.get(i) + " value has been passed in " + labelName + " field",
									YesNo.No);
							ThreadSleep(3000);
							xPath = "//ul[@class='drop_ul']//li[text()='" + tagList.get(i) + "']";
							ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN,
									30);
							if (click(driver, ele, tagList + " dropdown", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on " + tagList.get(i) + " value", YesNo.No);
							} else {
								log(LogStatus.ERROR, "Not able to click on " + tagList.get(i) + " value", YesNo.No);
								sa.assertTrue(false, "Not able to click on " + tagList.get(i) + " value");
								return false;
							}

						} else {
							log(LogStatus.ERROR, tagList.get(i) + " value is not passed in " + labelName + " field",
									YesNo.No);
							sa.assertTrue(false, tagList.get(i) + " value is not passed in " + labelName + " field");
							return false;
						}
					}
				} else {
					log(LogStatus.ERROR, "Label Name is not correct", YesNo.No);
					sa.assertTrue(false, "Label Name is not correct");
					return false;
				}
			}
		}
		if (advanceSection != null) {
			if (clickUsingJavaScript(driver, getSectionBtn("Advanced", 30), "Advanced section",
					action.SCROLLANDBOOLEAN)) {

				log(LogStatus.INFO, "clicked on Advanced section", YesNo.No);
				for (String[] val : advanceSection) {
					String labelName = val[0];
					String value = val[1];
					// String fieldType=val[2];

					if (labelName.contains("Start Date")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='Start']/..//label[text()='Date']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						ele.sendKeys(Keys.CONTROL + "A");
						ele.sendKeys(Keys.BACK_SPACE);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}
					} else if (labelName.contains("Start Time")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='Start']/..//label[text()='Time']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						ele.sendKeys(Keys.CONTROL + "A");
						ele.sendKeys(Keys.BACK_SPACE);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					} else if (labelName.contains("End Date")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='End']/..//label[text()='Date']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						ele.sendKeys(Keys.CONTROL + "A");
						ele.sendKeys(Keys.BACK_SPACE);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					} else if (labelName.contains("End Time")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='End']/..//label[text()='Time']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						ele.sendKeys(Keys.CONTROL + "A");
						ele.sendKeys(Keys.BACK_SPACE);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					}

					else if (labelName.contains(excelLabel.Location.toString()) || labelName.contains("Due Date")
							|| labelName.contains("Date")) {

						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
								+ labelName + "']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					}

					else if (labelName.contains(excelLabel.Status.toString())
							|| labelName.contains(excelLabel.Priority.toString())) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
								+ labelName + "']/..//button";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
								+ labelName + "']/..//span[@class='slds-truncate']";
						if (CommonLib.dropDownHandle(driver, ele, xPath, labelName + " dropdown", value)) {
							log(LogStatus.INFO, value + " value has been selected from " + labelName + " field",
									YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not selected from " + labelName + " field",
									YesNo.No);
							sa.assertTrue(false, value + " value is not selected from " + labelName + " field");
							return false;
						}
					} else if (labelName.equalsIgnoreCase("All-Day Event")) {

						if (value.equals("true")) {
							xPath = "//span[text()='All-Day Event']/../../input";
							ele = FindElement(driver, xPath, labelName + " name", action.SCROLLANDBOOLEAN, 30);
							if (isSelected(driver, ele, labelName + " checkbox")) {
								log(LogStatus.INFO, labelName + " checkbox is already selected", YesNo.No);

							} else {
								if (click(driver, ele, labelName + " checkbox", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, labelName + " checkbox has been selected", YesNo.No);
								} else {
									log(LogStatus.ERROR, labelName + " checkbox is not selected", YesNo.No);
									sa.assertTrue(false, labelName + " checkbox is not selected");
									return false;
								}
							}

						} else {
							xPath = "//span[text()='All-Day Event']/../../input";
							ele = FindElement(driver, xPath, labelName + " name", action.SCROLLANDBOOLEAN, 30);
							if (!isSelected(driver, ele, labelName + " checkbox")) {
								log(LogStatus.INFO, labelName + " checkbox is not selected", YesNo.No);

							} else {
								if (click(driver, ele, labelName + " checkbox", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, labelName + " checkbox has been unselected", YesNo.No);
								} else {
									log(LogStatus.ERROR, labelName + " checkbox is not unselected", YesNo.No);
									sa.assertTrue(false, labelName + " checkbox is not unselected");
									return false;
								}
							}
						}
					}

					else if (labelName.contains("User")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
								+ labelName + "']/..//button";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (click(driver, ele, labelName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on cross icon of value of " + labelName, YesNo.No);
							xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
									+ labelName + "']/..//input";
							ele = CommonLib.FindElement(driver, xPath, labelName + " input", action.SCROLLANDBOOLEAN,
									30);
							if (sendKeys(driver, ele, value, labelName + " input", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, value + " has been passed in " + labelName, YesNo.No);
								xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
										+ labelName + "']/../..//ul//li[text()='" + value + "']";
								ele = CommonLib.FindElement(driver, xPath, value + " list", action.SCROLLANDBOOLEAN,
										30);
								if (clickUsingJavaScript(driver, ele, value + " list element")) {
									log(LogStatus.INFO, "click on the list element of " + value, YesNo.No);
								} else {
									log(LogStatus.ERROR, "Not able to click on the list element of " + value, YesNo.No);
									sa.assertTrue(false, "Not able to click on the list element of " + value);
									return false;
								}

							} else {
								log(LogStatus.ERROR, value + " is not passed in " + labelName, YesNo.No);
								sa.assertTrue(false, value + " is not passed in " + labelName);
								return false;
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on cross icon of value of " + labelName, YesNo.No);
							sa.assertTrue(false, "Not able to click on cross icon of value of " + labelName);
							return false;
						}
					}

					else {
						log(LogStatus.ERROR, "label name is not correct", YesNo.No);
						sa.assertTrue(false, "label name is not correct");
						return false;
					}
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Advanced search section", YesNo.No);
				sa.assertTrue(false, "Not able to click on Advanced search section");
				return false;
			}
		}
		if (taskSection != null) {

			xPath = "//span[text()='Advanced']/parent::button[@aria-expanded='true']";
			ele = FindElement(driver, xPath, "Advance section", action.SCROLLANDBOOLEAN, 3);
			if (ele == null) {
				if (clickUsingJavaScript(driver, getSectionBtn("Advanced", 3), "Advanced section",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on Advanced section", YesNo.No);
				} else {
					log(LogStatus.ERROR, "Not able to click on Advanced section", YesNo.No);
					sa.assertTrue(false, "Not able to click on Advanced section");
					return false;
				}
			}

			if (clickUsingJavaScript(driver, getSectionBtn("Tasks", 30), "Tasks section", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on Tasks section", YesNo.No);

				for (String[] val : taskSection) {
					String labelName = val[0];
					String value = val[1];

					if (labelName.contains(excelLabel.Subject.toString()) || labelName.contains("Due Date")
							|| labelName.contains("Date")) {
						xPath = "//span[text()='Tasks']/ancestor::lightning-accordion[1]//label[text()='" + labelName
								+ "']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}
					} else if (labelName.contains(excelLabel.Status.toString())) {
						xPath = "//span[text()='Tasks']/ancestor::lightning-accordion[1]//label[text()='" + labelName
								+ "']/..//button";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						xPath = "//span[text()='Tasks']/ancestor::lightning-accordion[1]//label[text()='" + labelName
								+ "']/..//span[@class='slds-truncate']";
						if (CommonLib.dropDownHandle(driver, ele, xPath, labelName + " dropdown", value)) {
							log(LogStatus.INFO, value + " value has been selected from " + labelName + " field",
									YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not selected from " + labelName + " field",
									YesNo.No);
							sa.assertTrue(false, value + " value is not selected from " + labelName + " field");
							return false;
						}
					} else {
						log(LogStatus.ERROR, "Label name is not correct", YesNo.No);
						sa.assertTrue(false, "Label name is not correct");
						return false;
					}
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on Tasks section", YesNo.No);
				sa.assertTrue(false, "Not able to click on Tasks section");
				return false;
			}

		}

		if (click(driver, getfooterSaveOrCancelButton("Save", 20), "Save button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "clicked on Save button", YesNo.No);

			if (suggestedTags != null) {

				log(LogStatus.INFO, "Activity timeline record has been updated", YesNo.No);
				ThreadSleep(2000);
				if (suggestedTags.length > 0) {

					if (suggestedTags[0].equalsIgnoreCase("SuggestedPopUpShouldNotThere")) {
						CommonLib.ThreadSleep(5000);
						if (suggestedTagHeading(10) == null) {
							log(LogStatus.INFO, "------Verified Suggested Popup Not Open-------", YesNo.No);
							return true;

						} else {
							log(LogStatus.ERROR, "------Suggested Popup Opened, which should not be there-------",
									YesNo.Yes);
							sa.assertTrue(false, "------Suggested Popup Opened, which should not be there-------");
							return false;

						}
					}

					if (suggestedTags[0].equalsIgnoreCase("All Records Select")) {
						CommonLib.ThreadSleep(5000);
						if (click(driver, suggestedTagsCheckBoxAllInput(), "suggestedTagsCheckBoxAllInput",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on All Checkbox Input box of Suggested Tags Popup", YesNo.No);

						} else {
							log(LogStatus.ERROR, "Not able to Click on All Checkbox Input box of Suggested Tags Popup",
									YesNo.No);
							sa.assertTrue(false, "Not able to Click on All Checkbox Input box of Suggested Tags Popup");
							return false;
						}

					} else {

						for (int i = 0; i < suggestedTags.length; i++) {

							if (!suggestedTags[0].equals("")) {
								xPath = "//lightning-base-formatted-text[text()='" + suggestedTags[i]
										+ "']/ancestor::th[@data-label='Name']/..//td//input";
								ele = CommonLib.FindElement(driver, xPath, suggestedTags[i] + " sugested Tag",
										action.SCROLLANDBOOLEAN, 30);
								if (click(driver, ele, suggestedTags[i] + " suggested tag", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,
											"clicked on " + suggestedTags[i] + " suggested tag checkbox button",
											YesNo.No);

								} else {
									log(LogStatus.ERROR, "Not able to click on " + suggestedTags[i]
											+ " suggested tag checkbox button", YesNo.No);
									sa.assertTrue(false, "Not able to click on " + suggestedTags[i]
											+ " suggested tag checkbox button");
									return false;
								}

							}

						}
					}
				} else {
					log(LogStatus.ERROR, "Please Provide the Expected Suggested Tag Array non - empty", YesNo.No);
					sa.assertTrue(false, "Please Provide the Expected Suggested Tag Array non - empty");
					return false;
				}
				if (click(driver, getfooterTagButton(30), "Tag Button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on footer tag button", YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Not able to click on footer tag button", YesNo.No);
					sa.assertTrue(false, "Not able to click on footer tag button");
					return false;
				}

			}

			else {

				log(LogStatus.INFO, "Activity timeline record has been updated", YesNo.No);
				ThreadSleep(1000);
				refresh(driver);
				ThreadSleep(3000);
				if (crossIconButtonInNotePopUp(8) != null) {
					if (clickUsingJavaScript(driver, crossIconButtonInNotePopUp(8), "close button")) {
						log(LogStatus.INFO, "Note popup has been closed", YesNo.No);
						flag = true;
					} else {
						log(LogStatus.ERROR, "Not able to close the Note popup", YesNo.No);
						sa.assertTrue(false, "Not able to close the Note popup");
						return false;
					}
				} else {
					return true;
				}

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Save button", YesNo.No);
			sa.assertTrue(false, "Not able to click on Save button");
			return false;
		}
		return flag;
	}

	public boolean verifyViewAllButtonOnIntractionCard(int timeOut) {
		boolean flag = false;
		if (getViewAllBtnOnIntration(timeOut) != null) {
			log(LogStatus.INFO, "View All Button is visible", YesNo.No);
			flag = true;
		} else {
			log(LogStatus.INFO, "View All Button is not visible", YesNo.No);
		}
		return flag;
	}

	public boolean verifyAllIntractionsRecord(ArrayList<String> subjectName) {
		boolean flag = false;
		ArrayList<String> actualSubjectName = new ArrayList<String>();
		String xPath = "//h2[contains(text(),'All Interactions')]/ancestor::section//td[@data-label='Subject']//a";
		List<WebElement> elements = CommonLib.FindElements(driver, xPath, "Subject name on Intraction");
		if (!elements.isEmpty() && elements != null) {
			for (int i = 0; i < elements.size(); i++) {
				String subName = getText(driver, elements.get(i), "Subject Name ", action.SCROLLANDBOOLEAN);
				actualSubjectName.add(subName);
			}
			if (actualSubjectName.size() == subjectName.size()) {
				int status = 0;
				for (int i = 0; i < actualSubjectName.size(); i++) {
					int k = 0;
					for (int j = 0; j < subjectName.size(); j++) {
						if (actualSubjectName.get(i).equals(subjectName.get(j))) {
							log(LogStatus.INFO,
									"Actual result : " + actualSubjectName.get(i)
											+ " has been matched with the expected result : " + subjectName.get(i)
											+ " for subject in intraction view all",
									YesNo.No);
							k++;
							status++;
							break;
						}
					}
					if (k == 0) {
						log(LogStatus.ERROR, actualSubjectName.get(i) + " is not present on the intraction card",
								YesNo.No);
						return false;
					}
				}
				if (subjectName.size() == status) {
					log(LogStatus.INFO,
							"All subject names are present on view all page Intraction card :- " + actualSubjectName,
							YesNo.No);
					flag = true;
				}
			} else {
				log(LogStatus.ERROR, "the size of actual Subject name and expected subject name are not equal",
						YesNo.No);
				return false;
			}
		} else {
			log(LogStatus.ERROR, "Could not get the Elements of All Intractions record", YesNo.No);
			return false;
		}
		return flag;
	}

	public boolean verifyCountofIntractionCard(int IntractionCardCount) {
		String xPath;
		List<WebElement> elements;
		ThreadSleep(4000);
		xPath = "//div[@class='slds-grid allcardheight']//article";
		elements = CommonLib.FindElements(driver, xPath, "Intraction card");
		if (elements.size() == IntractionCardCount) {
			log(LogStatus.INFO, "the count of Intraction card has been verified", YesNo.No);
			return true;
		} else {
			log(LogStatus.ERROR, "the count of Intraction card is not verified", YesNo.No);
			return false;
		}
	}

	/*
	 * public boolean verifySubjectLinkRedirectionOnIntraction(WebDriver driver,
	 * String subjectName) { String xPath; WebElement ele; boolean flag = false;
	 * xPath = "//a[@class=\"interaction_sub subject_text\" and text()='" +
	 * subjectName + "']"; ele = FindElement(driver, xPath, "Subject Name",
	 * action.SCROLLANDBOOLEAN, 15); if (CommonLib.clickUsingJavaScript(driver, ele,
	 * "Subject Name on Intraction")) { log(LogStatus.INFO, "clicked on " +
	 * subjectName, YesNo.No);
	 * 
	 * String windowID = switchOnWindow(driver); if (windowID != null) {
	 * 
	 * if (getPageHeaderTitle(10) != null) { log(LogStatus.INFO, subjectName +
	 * " link is redirecting to Details Page", YesNo.No); driver.close();
	 * driver.switchTo().window(windowID); flag = true; } else {
	 * log(LogStatus.ERROR, subjectName +
	 * " links is not redirecting to Details Page", YesNo.No); } } else {
	 * log(LogStatus.ERROR, subjectName + " url did not open in new tab", YesNo.No);
	 * } } else { log(LogStatus.ERROR, "not able to click on " + subjectName,
	 * YesNo.No); } return flag; }
	 */
	public boolean verifySubjectLinkPopUpOnIntraction(WebDriver driver, String subjectName) {

		boolean flag = false;

		if (CommonLib.click(driver, subjectOfInteractionCard(subjectName, 15), "Subject Name on Intraction",
				action.BOOLEAN)) {
			log(LogStatus.INFO, "clicked on " + subjectName, YesNo.No);

			if (activitySubjetLinkPopupHeaderOnInteraction(5) != null) {

				if (activitySubjetLinkPopupHeaderOnInteraction(10).getText().equals(subjectName)) {
					log(LogStatus.INFO, subjectName + " link Popup open after click on it", YesNo.No);
					CommonLib.refresh(driver);
					flag = true;
				} else {
					log(LogStatus.ERROR, subjectName + " link Popup not open after click on it", YesNo.No);
				}
			} else {
				log(LogStatus.ERROR, subjectName + " url did not open in new tab", YesNo.No);
			}
		} else {
			log(LogStatus.ERROR, "not able to click on " + subjectName, YesNo.No);
		}
		return flag;
	}

	public boolean clickOnCountOfTaggedRecord(String recordName) {
		String xPath;
		WebElement ele;
		boolean flag = false;

		xPath = "//a[text()='" + recordName + "']/ancestor::tr//button[@name='timesRef']";
		ele = FindElement(driver, xPath, recordName + " record", action.SCROLLANDBOOLEAN, 20);
		if (CommonLib.click(driver, ele, recordName + " element", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "clicked on the count of " + recordName, YesNo.No);
			xPath = "//h2[contains(text(),'All Interactions')]";
			ele = FindElement(driver, xPath, "All Intraction popup", action.SCROLLANDBOOLEAN, 20);
			if (ele != null) {
				log(LogStatus.INFO, recordName + " Interaction popup have been open", YesNo.No);
				flag = true;
			} else {
				log(LogStatus.ERROR, recordName + " Interaction popup is not open", YesNo.No);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on the count of " + recordName, YesNo.No);
		}
		return flag;

	}

	public ArrayList<String> clickOnTaggedCountAndVerifyRecord(TaggedName taggedName, String recordName,
			ArrayList<String> subjectNames) {
		ArrayList<String> result = new ArrayList<String>();
		if (taggedName.toString() != null && taggedName.toString() != "") {
			if (taggedName.toString().equals("Companies")) {
				if (click(driver, getTaggedRecordName("Companies", 30), "Companies tab", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Companies tab name", YesNo.No);

					if (clickOnCountOfTaggedRecord(recordName)) {
						log(LogStatus.INFO, "clicked on the count of " + recordName, YesNo.No);
						if (verifyAllIntractionsRecord(subjectNames)) {
							log(LogStatus.INFO, "The records on intraction popup have been verified", YesNo.No);
						} else {
							log(LogStatus.ERROR, "The records on intraction popup is not verify", YesNo.No);
							result.add("The records on intraction popup is not verify");
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on the count of " + recordName, YesNo.No);
						result.add("Not able to click on the count of " + recordName);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Companies tab name", YesNo.No);
					result.add("Not able to click on Companies tab name");
				}
			} else if (taggedName.toString().equals("People")) {
				if (click(driver, getTaggedRecordName("People", 30), "People tab", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on People tab name", YesNo.No);

					if (clickOnCountOfTaggedRecord(recordName)) {
						log(LogStatus.INFO, "clicked on the count of " + recordName, YesNo.No);
						if (verifyAllIntractionsRecord(subjectNames)) {
							log(LogStatus.INFO, "The records on intraction popup have been verified", YesNo.No);
						} else {
							log(LogStatus.ERROR, "The records on intraction popup is not verify", YesNo.No);
							result.add("The records on intraction popup is not verify");
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on the count of " + recordName, YesNo.No);
						result.add("Not able to click on the count of " + recordName);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on People tab name", YesNo.No);
					result.add("Not able to click on People tab name");
				}
			} else if (taggedName.toString().equals("Deals")) {

				if (click(driver, getTaggedRecordName("Deals", 30), "Deals tab", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Deals tab name", YesNo.No);

					if (clickOnCountOfTaggedRecord(recordName)) {
						log(LogStatus.INFO, "clicked on the count of " + recordName, YesNo.No);
						if (verifyAllIntractionsRecord(subjectNames)) {
							log(LogStatus.INFO, "The records on intraction popup have been verified", YesNo.No);
						} else {
							log(LogStatus.ERROR, "The records on intraction popup is not verify", YesNo.No);
							result.add("The records on intraction popup is not verify");
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on the count of " + recordName, YesNo.No);
						result.add("Not able to click on the count of " + recordName);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Deals tab name", YesNo.No);
					result.add("Not able to click on Deals tab name");
				}

			}
		} else {
			log(LogStatus.ERROR, "Either tag name is empty or null", YesNo.No);
			result.add("Either tag name is empty or null");
		}
		return result;
	}

	public ArrayList<String> verifyRecordOnMeetingsAndCallPopUpSectionInAcuity(String icon, String dueDate,
			String subjectName, String details, String assignedTO) {
		ArrayList<String> result = new ArrayList<String>();
		String xPath;
		WebElement ele;

		String parentId = switchToWindowOpenNextToParentWindow(driver);

		/*
		 * if (getMeetingAndCallPopUp(20) != null) {
		 * 
		 */
		ThreadSleep(4000);
		if (parentId != null) {
			if (subjectName != null && subjectName != "") {
				log(LogStatus.INFO, "Meeting and calls popup has been open", YesNo.No);

				if (icon != null) {
					xPath = "//button[text()='" + subjectName
							+ "']/ancestor::td[@data-label='Subject']/../th[@data-label='Type']//lightning-icon";
					ele = FindElement(driver, xPath, "Icon type", action.SCROLLANDBOOLEAN, 20);
					String iconName = getAttribute(driver, ele, "Icon type", "class");
					if (iconName.toString().toLowerCase().contains(icon.toLowerCase())) {
						log(LogStatus.INFO, icon + " icon has been verified against " + subjectName
								+ " on Meetings and Calls popup", YesNo.No);

					} else {
						log(LogStatus.ERROR,
								icon + " icon is not verified against " + subjectName + " on Meetings and Calls popup",
								YesNo.No);
						result.add(
								icon + " icon is not verified against " + subjectName + " on Meetings and Calls popup");
					}
				}

				if (dueDate != null && dueDate != "") {
					xPath = "//button[text()='" + subjectName
							+ "']/ancestor::td[@data-label='Subject']/../td[@data-label='Date']//lightning-base-formatted-text";
					ele = FindElement(driver, xPath, "Date column", action.SCROLLANDBOOLEAN, 30);

					String date = getText(driver, ele, "Date", action.BOOLEAN);
					String actualDate;
					if (date.contains(",")) {
						String[] val = date.split(",");
						actualDate = val[0];
					} else {
						actualDate = date;
					}
					String[] splittedDate = dueDate.split("/");
					char dayMonth = splittedDate[0].charAt(0);
					char day = splittedDate[1].charAt(0);
					String month;
					if (dayMonth == '0') {
						month = splittedDate[0].replaceAll("0", "");
					} else {
						month = splittedDate[0];
					}
					String finalDay;
					if (day == '0') {
						finalDay = splittedDate[1].replaceAll("0", "");
					} else {
						finalDay = splittedDate[1];
					}

					String expectedDate = month + "/" + finalDay + "/" + splittedDate[2];

					if (actualDate.trim().equalsIgnoreCase(expectedDate.trim())) {
						log(LogStatus.INFO, "Expected date: " + expectedDate
								+ " has been matched with the actual date: " + actualDate, YesNo.No);
					} else {
						log(LogStatus.INFO, "Expected date: " + expectedDate + " is not matched with the actual date: "
								+ actualDate, YesNo.No);
						result.add("Expected date: " + expectedDate + " is not matched with the actual date: "
								+ actualDate);
					}

				}

				if (subjectName != null && subjectName != "") {
					xPath = "//td[@data-label='Subject']//button[text()='" + subjectName + "']";
					ele = FindElement(driver, xPath, "Subject column", action.SCROLLANDBOOLEAN, 30);

					String actualSubject = getText(driver, ele, "subject", action.BOOLEAN);
					if (subjectName.equalsIgnoreCase(actualSubject)) {
						log(LogStatus.INFO, "Expected subject: " + subjectName
								+ " has been matched with the actual subject: " + actualSubject, YesNo.No);
					} else {
						log(LogStatus.ERROR, "Expected subject: " + subjectName
								+ " is not matched with the actual subject: " + actualSubject, YesNo.No);
						result.add("Expected subject: " + subjectName + " is not matched with the actual subject : "
								+ actualSubject);
					}

				}

				if (details != null && details != "") {
					xPath = "//button[text()='" + subjectName
							+ "']/ancestor::td[@data-label='Subject']/../td[@data-label='Details']//button";
					ele = FindElement(driver, xPath, "Details column", action.SCROLLANDBOOLEAN, 30);

					String actualDetails = getText(driver, ele, "Details", action.BOOLEAN);
					if (details.equalsIgnoreCase(actualDetails)) {
						log(LogStatus.INFO, "Expected details: " + details
								+ " has been matched with the actual details: " + actualDetails, YesNo.No);
					} else {
						log(LogStatus.ERROR, "Expected details: " + details
								+ " is not matched with the actual details: " + actualDetails, YesNo.No);
						result.add("Expected details: " + details + " is not matched with the actual details: "
								+ actualDetails);
					}
				}
				/*
				 * if (assignedTO != null && assignedTO != "") { xPath = "//a[text()='" +
				 * subjectName +
				 * "']/ancestor::td[@data-label='Subject']/../td[@data-label='Assigned To']//a";
				 * ele = FindElement(driver, xPath, "Assigned To column",
				 * action.SCROLLANDBOOLEAN, 30);
				 * 
				 * String actualAssignedTO = getText(driver, ele, "User", action.BOOLEAN); if
				 * (assignedTO.equalsIgnoreCase(actualAssignedTO)) { log(LogStatus.INFO,
				 * "Expected Assigned to : " + assignedTO +
				 * " has been matched with the actual Assigned to : " + actualAssignedTO,
				 * YesNo.No); } else { log(LogStatus.ERROR, "Expected Assigned to : " +
				 * assignedTO + " is not matched with the actual Assigned to : " +
				 * actualAssignedTO, YesNo.No); result.add("Expected Assigned to : " +
				 * assignedTO + " is not matched with the actual Assigned to : " +
				 * actualAssignedTO); } }
				 */
			} else {
				log(LogStatus.ERROR, "Either subject name is empty or null", YesNo.No);
				result.add("Either subject name is empty or null");
			}

			driver.close();
			driver.switchTo().window(parentId);

		} else {
			log(LogStatus.ERROR, "Meeting and call page did not open", YesNo.No);
			result.add("Meeting and call page did not open");
		}

		/*
		 * xPath =
		 * "//h2[contains(text(),'Meetings and Calls')]/../button//lightning-primitive-icon"
		 * ; ele = FindElement(driver, xPath, "close icon", action.SCROLLANDBOOLEAN,
		 * 30); if (CommonLib.clickUsingJavaScript(driver, ele, "close icon")) {
		 * log(LogStatus.INFO, "Clicked on close icon of popup", YesNo.No); } else {
		 * log(LogStatus.ERROR, "Not able to click on close icon of popup", YesNo.No);
		 * result.add("Not able to click on close icon of popup"); } } else {
		 * log(LogStatus.ERROR, "Meeting and calls popup is not open", YesNo.No);
		 * result.add("Meeting and calls popup is not open"); }
		 */
		return result;
	}

	public ArrayList<String> verifyPopupFromConnectionIcon(String recordPageName, String teamMemberName,
			String[][] meetingAndCallsPopupColumnAndValue, String[][] dealsPopupColumnAndValue) {
		String xPath;
		WebElement ele;
		ArrayList<String> finResult = new ArrayList<String>();
		if (teamMemberName != null && teamMemberName != "") {
			log(LogStatus.INFO, teamMemberName + " is present on popup of connection", YesNo.No);

			if (meetingAndCallsPopupColumnAndValue.length != 0) {
				xPath = "//a[text()='" + teamMemberName
						+ "']/ancestor::th[@data-label='Team Member']/../td[@data-label='Meetings and Calls']//button";
				ele = FindElement(driver, xPath, "Meeting and calls count", action.SCROLLANDBOOLEAN, 30);
				if (click(driver, ele, "Meeting and calls count", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Sucessfully clicked on Meeting and call count", YesNo.No);

					String date = null, subject = null, details = null, assignedTo = null, icon = null;

					for (String[] meetingAndCall : meetingAndCallsPopupColumnAndValue) {
						String val = meetingAndCall[0];
						if (val.equalsIgnoreCase("date")) {
							date = meetingAndCall[1];
						} else if (val.equalsIgnoreCase("subject")) {
							subject = meetingAndCall[1];
						} else if (val.equalsIgnoreCase("details")) {
							details = meetingAndCall[1];
						} else if (val.equalsIgnoreCase("AssignedTo")) {
							assignedTo = meetingAndCall[1];
						} else if (val.equalsIgnoreCase("iconType")) {
							icon = meetingAndCall[1];
						}
					}
					ArrayList<String> result = new ArrayList<String>();
					result = verifyRecordOnMeetingsAndCallPopUpSectionInAcuity(icon, date, subject, details,
							assignedTo);
					if (result.isEmpty()) {
						log(LogStatus.INFO, "The records have been verified on Meeting and call popup", YesNo.No);
					} else {
						log(LogStatus.ERROR, "The records is not verified on Meeting and call popup", YesNo.No);
						finResult.add("The records is not verified on Meeting and call popup");
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Meetings and call popup", YesNo.No);
					finResult.add("Not able to click on Meetings and call popup");
				}

			}

			if (dealsPopupColumnAndValue.length != 0) {
				xPath = "//a[text()='" + teamMemberName
						+ "']/ancestor::th[@data-label='Team Member']/../td[@data-label='Deals']//button";
				ele = FindElement(driver, xPath, "Deals count", action.SCROLLANDBOOLEAN, 30);
				if (click(driver, ele, "Deals count", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Sucessfully clicked on Deals count", YesNo.No);

					String dealName = null, company = null, stage = null, dateReceived = null;
					for (String[] deal : dealsPopupColumnAndValue) {
						String val = deal[0];
						if (val.equalsIgnoreCase("Deal Name")) {
							dealName = deal[1];
						} else if (val.equalsIgnoreCase("Company")) {
							company = deal[1];
						} else if (val.equalsIgnoreCase("Stage")) {
							stage = deal[1];
						} else if (val.equalsIgnoreCase("Date Received")) {
							dateReceived = deal[1];
						}
					}
					ArrayList<String> result1 = new ArrayList<String>();
					result1 = verifyRecordOnDealsPopUpSectionInAcuity(recordPageName, dealName, company, stage,
							dateReceived);
					if (result1.isEmpty()) {
						log(LogStatus.INFO, "The records have been verified on Deals popup", YesNo.No);
					} else {
						log(LogStatus.ERROR, "The records is not verified on Deals popup", YesNo.No);
						finResult.add("The records is not verified on Deals popup");
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Deal count", YesNo.No);
					finResult.add("Not able to click on Deal count");
				}
			}

		} else {
			log(LogStatus.ERROR, "Either Team Member name is null or Empty", YesNo.No);
			finResult.add("Either Team Member name is null or Empty");

		}
		return finResult;

	}

	public boolean verifyExportButtonPopup(String recordName, ArrayList<String> reportName,
			ArrayList<String> reportDescription) {

		boolean flag = false;
		String xPath;
		WebElement ele;
		List<WebElement> elements;
		ArrayList<String> actualReportName = new ArrayList<String>();
		ArrayList<String> actualDescription = new ArrayList<String>();
		if (click(driver, getactionEroBtn(20), "Action Ero button", action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Action ero button", YesNo.No);
			if (CommonLib.click(driver, getExportBtn(20), "Export button", action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on export button", YesNo.No);
				if (getExportPopup(20) != null) {
					log(LogStatus.INFO, "Export popup has been open", YesNo.No);

					xPath = "//lightning-formatted-text[text()='" + recordName + "']";
					ele = FindElement(driver, xPath, recordName + " record name", action.BOOLEAN, 20);

					if (ele != null) {
						log(LogStatus.INFO, "Notes popup is open in the same page", YesNo.No);

						xPath = "//h2[text()='Export']/../..//a[@class='list slds-truncate']";
						elements = FindElements(driver, xPath, "Report Name");
						for (int i = 0; i < elements.size(); i++) {
							actualReportName
									.add(getText(driver, elements.get(i), "Report Name", action.SCROLLANDBOOLEAN));
						}
						int status = 0;
						for (int i = 0; i < reportName.size(); i++) {
							int k = 0;
							for (int j = 0; j < actualReportName.size(); j++) {
								if (reportName.get(i).equals(actualReportName.get(j))) {
									log(LogStatus.INFO, "Actual report name : " + actualReportName.get(j)
											+ " has been matched with Expected report name : " + reportName.get(i),
											YesNo.No);
									k++;
									status++;
								}
							}
							if (k == 0) {
								log(LogStatus.ERROR, "Expected report name : " + reportName.get(i)
										+ " is not available on export popup", YesNo.No);
							}

						}

						if (reportName.size() == status) {
							for (int i = 0; i < reportName.size(); i++) {
								xPath = "//h2[text()='Export']/../..//a[text()='" + reportName.get(i)
										+ "']/../../following-sibling::div//span";
								ele = FindElement(driver, xPath, reportName.get(i) + " report description",
										action.SCROLLANDBOOLEAN, 20);
								if (ele != null) {
									actualDescription.add(getText(driver, ele,
											reportName.get(i) + " report description", action.SCROLLANDBOOLEAN));
								} else {
									log(LogStatus.ERROR, "Not able to get the element of " + reportName.get(i)
											+ " report description", YesNo.No);
								}
							}
							status = 0;
							if (actualDescription.size() == reportDescription.size()) {
								for (int i = 0; i < reportDescription.size(); i++) {
									if (reportDescription.get(i).equals(actualDescription.get(i))) {
										log(LogStatus.INFO,
												"Actual report description : " + actualDescription.get(i)
														+ " has been matched with Expected report description : "
														+ reportDescription.get(i),
												YesNo.No);
										status++;
									} else {
										log(LogStatus.ERROR,
												"Actual report description : " + actualDescription.get(i)
														+ " is not matched with Expected report description : "
														+ reportDescription.get(i),
												YesNo.No);
									}
								}
								if (status == reportDescription.size()) {
									log(LogStatus.INFO,
											"All expected report description has been matched with the actual report description",
											YesNo.No);
									if (click(driver, getexportPopupCloseBtn(20), "popup close btn", action.BOOLEAN)) {
										log(LogStatus.INFO,
												"clicked on the close button of popup. Popup has been closed",
												YesNo.No);
										flag = true;
									} else {
										log(LogStatus.ERROR,
												"Not able to click on the close button of popup. Popup is not closed",
												YesNo.No);
									}
								}
							} else {
								log(LogStatus.ERROR,
										"the size of actual descrption and expected description is not equal",
										YesNo.No);
							}
						} else {
							log(LogStatus.ERROR, "All expected reports are not available on export popup", YesNo.No);
						}
					} else {
						log(LogStatus.ERROR, "Notes popup is not open in the same page", YesNo.No);
					}
				} else {
					log(LogStatus.ERROR, "Export popup is not open", YesNo.No);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on export button", YesNo.No);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Action ero button", YesNo.No);
		}
		return flag;
	}

	public boolean verifyRecordandDescriptionThroughUtility(ArrayList<String> reportName,
			ArrayList<String> descripton) {
		boolean flag = true;
		String xPath;
		List<WebElement> elements;
		ArrayList<String> actualReportName = new ArrayList<String>();
		ArrayList<String> actualDescriptionName = new ArrayList<String>();

		xPath = "//div[@class='slds-utility-panel__body']//a[@class='list slds-truncate']";
		elements = CommonLib.FindElements(driver, xPath, "Report Name ");
		for (WebElement ele : elements) {
			actualReportName.add(getText(driver, ele, "Report Name", action.SCROLLANDBOOLEAN));
		}

		if (reportName.size() == actualReportName.size()) {
			for (int i = 0; i < reportName.size(); i++) {
				int status = 0;
				for (int j = 0; j < actualReportName.size(); j++) {

					if (reportName.get(i).equals(actualReportName.get(j))) {
						log(LogStatus.INFO,
								"Actual report name : " + actualReportName.get(j)
										+ " has been matched with Expected report name :" + reportName.get(i),
								YesNo.No);
						status++;
						break;
					}
				}
				if (status == 0) {
					log(LogStatus.ERROR, "Expected report name : " + reportName.get(i) + " is not matched", YesNo.No);
					flag = false;
				}
			}
		} else {
			log(LogStatus.ERROR, "the size of Actual report Name and Expected report name is not equal", YesNo.No);
			flag = false;
		}

		xPath = "//div[@class='slds-utility-panel__body']//span[@class='text']";
		elements = CommonLib.FindElements(driver, xPath, "Report Description ");
		for (WebElement ele : elements) {
			actualDescriptionName.add(getText(driver, ele, "Report Description", action.SCROLLANDBOOLEAN));
		}

		if (actualDescriptionName.size() == descripton.size()) {
			for (int i = 0; i < descripton.size(); i++) {
				int status = 0;
				for (int j = 0; j < actualDescriptionName.size(); j++) {

					if (descripton.get(i).equals(actualDescriptionName.get(j))) {
						log(LogStatus.INFO,
								"Actual report description : " + actualDescriptionName.get(j)
										+ " has been matched with Expected report description :" + descripton.get(i),
								YesNo.No);
						status++;
						break;
					}
				}
				if (status == 0) {
					log(LogStatus.ERROR, "Expected report description : " + descripton.get(i) + " is not matched",
							YesNo.No);
					flag = false;
				}

			}

		} else {
			log(LogStatus.ERROR, "the size of Actual report description and Expected report description is not equal",
					YesNo.No);
			flag = false;
		}

		return flag;

	}

	public boolean verifySectionsAndTooltipOnAcuityTab(List<String> sectionHeaderName, List<String> toolTipMessage) {
		String xPath;
		List<WebElement> elements;
		List<String> actualSectionHeaderName = new ArrayList<String>();
		List<String> actualTooltipName = new ArrayList<String>();

		xPath = "//slot//span[contains(@class,'slds-page-header__title') and contains(@class,'fit-width-content')]";
		elements = FindElements(driver, xPath, "Acuity section Header Name");
		for (WebElement ele : elements) {
			actualSectionHeaderName.add(getText(driver, ele, "Acuity Section Name", action.SCROLLANDBOOLEAN));
			actualTooltipName.add(getAttribute(driver, ele, "Acuity Section Name", "title"));
		}

		if (sectionHeaderName.containsAll(actualSectionHeaderName)) {
			log(LogStatus.INFO, "Actual Section header name : " + actualSectionHeaderName
					+ " have been matched with the Expected Header Name : " + sectionHeaderName, YesNo.No);

			if (toolTipMessage.containsAll(actualTooltipName)) {
				log(LogStatus.INFO, "Actual Tooltip message : " + actualTooltipName
						+ " have been matched with the Expected Tooltip message : " + toolTipMessage, YesNo.No);
				return true;
			} else {
				log(LogStatus.ERROR, "Actual Tooltip message : " + actualTooltipName
						+ " is not matched with the Expected Tooltip message : " + toolTipMessage, YesNo.No);
				sa.assertTrue(false, "Actual Tooltip message : " + actualTooltipName
						+ " is not matched with the Expected Tooltip message : " + toolTipMessage);

				return false;
			}
		} else {
			log(LogStatus.ERROR, "Actual Section header name : " + actualSectionHeaderName
					+ " are not  matched with the Expected Header Name : " + sectionHeaderName, YesNo.No);
			sa.assertTrue(false, "Actual Section header name : " + actualSectionHeaderName
					+ " are not  matched with the Expected Header Name : " + sectionHeaderName);
			return false;
		}
	}

	public boolean verifyTabsOnTaggedSection(List<String> tabName, String defaultSelectedTab) {
		boolean flag = false;
		String xPath;
		List<WebElement> elements;
		WebElement ele;
		List<String> expectedTabName = new ArrayList<String>();

		xPath = "//slot//span[@title='Tagged']/ancestor::div//span[@class='slds-radio_faux']";
		elements = FindElements(driver, xPath, "Tabs name");
		for (WebElement el : elements) {
			expectedTabName.add(getText(driver, el, "TabsName", action.SCROLLANDBOOLEAN));
		}
		if (expectedTabName.containsAll(expectedTabName)) {
			log(LogStatus.INFO, "Expected tab name : " + expectedTabName
					+ " have been matched with the Actual tab Name : " + tabName + " on Tagged section", YesNo.No);

			xPath = "//span[@class=\"slds-truncate\" and @title='" + defaultSelectedTab + "']";
			ele = FindElement(driver, xPath, "header of selected tag", action.SCROLLANDBOOLEAN, 15);
			if (ele != null) {
				log(LogStatus.INFO, defaultSelectedTab + " tab is default selected on Tagged", YesNo.No);
				flag = true;
			} else {
				log(LogStatus.ERROR, defaultSelectedTab + " tab is not default selected on Tagged", YesNo.No);
			}
		} else {
			log(LogStatus.ERROR,
					"Expected Section header name : " + expectedTabName
							+ " are not  matched with the Actual Header Name : " + tabName + " on Tagged section",
					YesNo.No);
		}
		return flag;
	}

	public ArrayList<String> verifyColumnsAndMessageOnTabsOfTagged(List<String> tabName, String message) {
		ArrayList<String> result = new ArrayList<String>();

		for (String val : tabName) {

			if (click(driver, getTaggedRecordName(val, 25), val + " tab", action.BOOLEAN)) {
				log(LogStatus.INFO, "clicked on " + val + " tab", YesNo.No);

				if (getHeadingNameOfTabOnTaggedSection(val, 15) != null) {
					log(LogStatus.INFO,
							"First column " + val + " has been verified on " + val + " tab of Tagged section",
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"First column " + val + " is not verified verified on " + val + " tab of Tagged section",
							YesNo.No);
					result.add(
							"First column " + val + " is not verified verified on " + val + " tab of Tagged section");
				}

				if (getTimeReferenceIconOnTaggedSection(15) != null) {
					log(LogStatus.INFO,
							"Second column Times Referenced has been verified on " + val + " tab of Tagged section",
							YesNo.No);
				} else {
					log(LogStatus.ERROR, "Second column Times Referenced is not verified verified on " + val
							+ " tab of Tagged section", YesNo.No);
					result.add("Second column Times Referenced is not verified verified on " + val
							+ " tab of Tagged section");
				}

				if (getMessageOnTaggedSection(val, message, 15) != null) {
					log(LogStatus.INFO, message + " message is visible on Tagged section", YesNo.No);
				} else {
					log(LogStatus.ERROR, message + " message is not visible on Tagged section", YesNo.No);
					result.add(message + " message is not visible on Tagged section");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + val + " tab", YesNo.No);
				result.add("Not able to click on " + val + " tab");
			}

		}

		return result;

	}

	public ArrayList<String> verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(
			String InteractionSectionmessage, List<String> contactsSectionHeaderName,
			String contactsSectionHeaderMessage, List<String> dealsSectionHeaderName, String dealsSectionHeaderMessage,
			List<String> connectionsSectionHeaderName, String connectionsSectionHeaderMessage) {
		String xPath;
		WebElement ele;
		List<WebElement> elements;
		ArrayList<String> result = new ArrayList<String>();

		if (InteractionSectionmessage != null && !"".equals(InteractionSectionmessage)) {

			String text = getText(driver, getMessageOnInteractionSection(15), "Interaction section message",
					action.SCROLLANDBOOLEAN);
			if (text.equals(InteractionSectionmessage)) {
				log(LogStatus.INFO, InteractionSectionmessage + " message has been verified on Interaction section",
						YesNo.No);
			} else {
				log(LogStatus.ERROR, InteractionSectionmessage + " message is not verified on Interaction section",
						YesNo.No);
				result.add(InteractionSectionmessage + " message is not verified on Interaction section");
			}
		}
		if (!contactsSectionHeaderName.isEmpty()) {
			ArrayList<String> actualContactsSectionHeaderName = new ArrayList<String>();
			xPath = "//span[@title='Contacts']/ancestor::div[contains(@class,'slds-grid slds-wrap slds-box')]/following-sibling::div//span[@class='slds-truncate' and @title!='']";
			elements = FindElements(driver, xPath, "Contact section headers");
			for (WebElement el : elements) {
				actualContactsSectionHeaderName
						.add(getText(driver, el, "Contact section headers", action.SCROLLANDBOOLEAN));
			}

			xPath = "//span[@title='Contacts']/ancestor::div[contains(@class,'slds-grid slds-wrap slds-box')]/following-sibling::div//lightning-icon";
			elements = FindElements(driver, xPath, "Contact section headers");
			for (WebElement el : elements) {
				actualContactsSectionHeaderName.add(getAttribute(driver, el, "Contact section headers", "title"));
			}

			for (int i = 0; i < contactsSectionHeaderName.size(); i++) {
				int k = 0;
				for (int j = 0; j < actualContactsSectionHeaderName.size(); j++) {
					if (contactsSectionHeaderName.get(i).equalsIgnoreCase(actualContactsSectionHeaderName.get(j))) {
						log(LogStatus.INFO,
								"Expected header name: " + contactsSectionHeaderName.get(i)
										+ " has been matched with Actual header name: "
										+ actualContactsSectionHeaderName.get(j) + " on contact section",
								YesNo.No);
						k++;
					}
				}
				if (k == 0) {
					log(LogStatus.ERROR, "Expected header name: " + contactsSectionHeaderName.get(i)
							+ " is not matched with Actual header name on contact section", YesNo.No);
					result.add("Expected header name: " + contactsSectionHeaderName.get(i)
							+ " is not matched with Actual header name on contact section");
				}

			}

		}
		if (contactsSectionHeaderMessage != null && !"".equals(contactsSectionHeaderMessage)) {
			xPath = "//span[@title='Contacts']/ancestor::div[contains(@class,'slds-grid slds-wrap')]/following-sibling::div//div[text()='"
					+ contactsSectionHeaderMessage + "']";
			ele = FindElement(driver, xPath, "Message on Contact section", action.SCROLLANDBOOLEAN, 15);
			if (ele != null) {
				log(LogStatus.INFO,
						"The meessage : " + contactsSectionHeaderMessage + " has been verified on Contect section",
						YesNo.No);
			} else {
				log(LogStatus.ERROR,
						"The meessage : " + contactsSectionHeaderMessage + " is not verified on Contect section",
						YesNo.No);
				result.add("The meessage : " + contactsSectionHeaderMessage + " is not verified on Contect section");
			}
		}

		if (!dealsSectionHeaderName.isEmpty()) {
			ArrayList<String> actualDealsSectionHeaderName = new ArrayList<String>();
			xPath = "//span[@title='Deals']/../../../..//span[text()=@title]";
//					"//span[@title='Deals']/ancestor::div/following-sibling::div//span[text()='']";
			// span[@title='Deals']/ancestor::div//th[contains(@data-col-key-value,'field')]//span[text()='Deal
			// Name']
			elements = FindElements(driver, xPath, "Deal section headers");
			for (WebElement el : elements) {
				actualDealsSectionHeaderName.add(getText(driver, el, "deal section headers", action.SCROLLANDBOOLEAN));
			}

			for (int i = 0; i < dealsSectionHeaderName.size(); i++) {
				int k = 0;
				for (int j = 0; j < actualDealsSectionHeaderName.size(); j++) {
					if (dealsSectionHeaderName.get(i).equalsIgnoreCase(actualDealsSectionHeaderName.get(j))) {
						log(LogStatus.INFO,
								"Expected header name: " + dealsSectionHeaderName.get(i)
										+ " has been matched with Actual header name: "
										+ actualDealsSectionHeaderName.get(j) + " on deal section",
								YesNo.No);
						k++;
					}
				}
				if (k == 0) {
					log(LogStatus.ERROR, "Expected header name: " + dealsSectionHeaderName.get(i)
							+ " is not matched with Actual header name on deal section", YesNo.No);
					result.add("Expected header name: " + dealsSectionHeaderName.get(i)
							+ " is not matched with Actual header name on deal section");
				}

			}
		}

		if (dealsSectionHeaderMessage != null && !"".equals(dealsSectionHeaderMessage)) {
			xPath = "//span[@title='Deals']/ancestor::div[contains(@class,'slds-grid slds-wrap')]/following-sibling::div//div[text()='"
					+ dealsSectionHeaderMessage + "']";
			ele = FindElement(driver, xPath, "Message on deal section", action.SCROLLANDBOOLEAN, 15);
			if (ele != null) {
				log(LogStatus.INFO, "The meessage : " + dealsSectionHeaderMessage + " has been verified on deal secton",
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "The meessage : " + dealsSectionHeaderMessage + " is not verified on deal secton",
						YesNo.No);
				result.add("The meessage : " + dealsSectionHeaderMessage + " is not verified on deal secton");
			}
		}

		if (!connectionsSectionHeaderName.isEmpty()) {
			ArrayList<String> actualConnectionsSectionHeaderName = new ArrayList<String>();
//			xPath = "//span[@title='Connections']/ancestor::div[@class='slds-m-bottom_xx-small']//span[@class='slds-truncate' and @title!='']";
			xPath = "//span[contains(@title,'Connection')]/ancestor::div//span[@class='slds-truncate' and @title!='']";
			elements = FindElements(driver, xPath, "Connections section headers");
			for (WebElement el : elements) {
				actualConnectionsSectionHeaderName
						.add(getText(driver, el, "Connections section headers", action.SCROLLANDBOOLEAN));
			}

//			xPath = "//span[@title='Connections']/ancestor::div[@class='slds-m-bottom_xx-small']//lightning-icon";
			xPath = "//span[contains(@title,'Connection')]/ancestor::div//div[@class='slds-grid slds-grid_vertical-align-center slds-has-flexi-truncate']//lightning-icon";
			elements = FindElements(driver, xPath, "Connections section headers");
			for (WebElement el : elements) {
				actualConnectionsSectionHeaderName
						.add(getAttribute(driver, el, "Connections section headers", "title"));
			}

			for (int i = 0; i < connectionsSectionHeaderName.size(); i++) {
				int k = 0;
				for (int j = 0; j < actualConnectionsSectionHeaderName.size(); j++) {
					if (connectionsSectionHeaderName.get(i)
							.equalsIgnoreCase(actualConnectionsSectionHeaderName.get(j))) {
						log(LogStatus.INFO,
								"Expected header name: " + connectionsSectionHeaderName.get(i)
										+ " has been matched with Actual header name: "
										+ actualConnectionsSectionHeaderName.get(j) + " on connection section",
								YesNo.No);
						k++;
					}
				}
				if (k == 0) {
					log(LogStatus.ERROR, "Expected header name: " + connectionsSectionHeaderName.get(i)
							+ " is not matched with Actual header name on connection section", YesNo.No);
					result.add("Expected header name: " + connectionsSectionHeaderName.get(i)
							+ " is not matched with Actual header name on connection section");
				}

			}
		}

		if (connectionsSectionHeaderMessage != null && !"".equals(connectionsSectionHeaderMessage)) {
			xPath = "//span[@title='Connections']/ancestor::div[contains(@class,'slds-grid slds-wrap')]/following-sibling::div//p[text()='"
					+ connectionsSectionHeaderMessage + "']";
			ele = FindElement(driver, xPath, "Message on Connections section", action.SCROLLANDBOOLEAN, 15);
			if (ele != null) {
				log(LogStatus.INFO, "The meessage : " + connectionsSectionHeaderMessage
						+ " has been verified on Connection section", YesNo.No);
			} else {
				log(LogStatus.ERROR,
						"The meessage : " + connectionsSectionHeaderMessage + " is not verified on Connection section",
						YesNo.No);
				result.add(
						"The meessage : " + connectionsSectionHeaderMessage + " is not verified on Connection section");
			}
		}
		return result;
	}

	public ArrayList<String> verifyHeaderNameAndMessageOnFundraisigContactsConnectionsAndDealsSection(
			List<String> FundraisingSectionHeaderName, String FundraisngSectionHeaderMessage) {
		String xPath;
		WebElement ele;
		List<WebElement> elements;
		ArrayList<String> result = new ArrayList<String>();

		if (!FundraisingSectionHeaderName.isEmpty()) {
			ArrayList<String> actualFundraisingSectionHeaderName = new ArrayList<String>();
			xPath = "//span[@title='Fundraisings']/ancestor::div[@class='slds-grid slds-wrap']/following-sibling::div//span[@class='slds-truncate' and text()!='']";
			elements = FindElements(driver, xPath, "Fundraising section headers");
			for (WebElement el : elements) {
				actualFundraisingSectionHeaderName
						.add(getText(driver, el, "Fundraising section headers", action.SCROLLANDBOOLEAN));
			}
			if (FundraisingSectionHeaderName.containsAll(FundraisingSectionHeaderName)) {
				log(LogStatus.INFO, "The Headers name of Fundraising section have been verified "
						+ actualFundraisingSectionHeaderName, YesNo.No);
			} else {
				log(LogStatus.ERROR, "The Headers name of Fundraising section are not verified "
						+ actualFundraisingSectionHeaderName, YesNo.No);
				result.add("The Headers name of Fundraising section are not verified "
						+ actualFundraisingSectionHeaderName);
			}
		}

		if (FundraisngSectionHeaderMessage != null && !"".equals(FundraisngSectionHeaderMessage)) {
			xPath = "//span[@title='Fundraisings']/ancestor::div[contains(@class,'slds-grid slds-wrap')]/following-sibling::div//p[text()='"
					+ FundraisngSectionHeaderMessage + "']";
			ele = FindElement(driver, xPath, "Message on Fundraisisng section", action.SCROLLANDBOOLEAN, 15);
			if (ele != null) {
				log(LogStatus.INFO,
						"The meessage : " + FundraisngSectionHeaderMessage + " has been verified on deal secton",
						YesNo.No);
			} else {
				log(LogStatus.ERROR,
						"The meessage : " + FundraisngSectionHeaderMessage + " is not verified on deal secton",
						YesNo.No);
				result.add("The meessage : " + FundraisngSectionHeaderMessage + " is not verified on deal secton");
			}
			return result;
		}
		return result;
	}

	public ArrayList<String> verifyToolTipOnDealsConnctionsAndContactsHeader(List<String> dealSectionHeaderTooltip,
			List<String> contactsSectionHeaderTooltip, List<String> connectionsSectionHeaderTooltip) {
		String xPath;
		List<WebElement> elements;
		ArrayList<String> result = new ArrayList<String>();
		if (!dealSectionHeaderTooltip.isEmpty()) {
			ArrayList<String> actualDealsSectionHeaderTooltip = new ArrayList<String>();
			xPath = "//span[@title='Deals']/ancestor::div[@class='slds-grid slds-wrap']/following-sibling::div//span[@class='slds-truncate' and text()!='']";
			elements = FindElements(driver, xPath, "Deal section headers");
			for (WebElement el : elements) {
				actualDealsSectionHeaderTooltip.add(getAttribute(driver, el, "deal section headers", "title"));
			}
			if (dealSectionHeaderTooltip.containsAll(actualDealsSectionHeaderTooltip)) {
				log(LogStatus.INFO, "The Tooltip on Headers name of deal section have been verified "
						+ actualDealsSectionHeaderTooltip, YesNo.No);
			} else {
				log(LogStatus.ERROR, "The Tooltip on Headers name of deal section are not verified "
						+ actualDealsSectionHeaderTooltip, YesNo.No);
				result.add("The Tooltip on Headers name of deal section are not verified "
						+ actualDealsSectionHeaderTooltip);
			}
		}

		if (!contactsSectionHeaderTooltip.isEmpty()) {
			ArrayList<String> ExpectedTooltip = new ArrayList<String>();

			xPath = "//span[@title='Contacts']/ancestor::div[@class='slds-grid slds-wrap']/following-sibling::div//span[@class='slds-truncate' and @title!='']";
			elements = FindElements(driver, xPath, "Contact section headers");

			for (WebElement el : elements) {
				ExpectedTooltip.add(getAttribute(driver, el, "Contact section headers Tooltip", "title"));
			}

			xPath = "//span[@title='Contacts']/ancestor::div[@class='slds-grid slds-wrap']/following-sibling::div//lightning-icon";
			elements = FindElements(driver, xPath, "Contact section headers");
			for (WebElement el : elements) {
				ExpectedTooltip.add(getAttribute(driver, el, "Contact section headers Tooltip", "title"));
			}

			if (contactsSectionHeaderTooltip.containsAll(ExpectedTooltip)) {
				log(LogStatus.INFO,
						"The Tooltip on Header of contact section have been verified " + contactsSectionHeaderTooltip,
						YesNo.No);
			} else {
				log(LogStatus.ERROR,
						"The Tooltip on Header of contact section are not verified " + contactsSectionHeaderTooltip,
						YesNo.No);
				result.add("The Tooltip on Header of contact section are not verified " + contactsSectionHeaderTooltip);
			}
		}

		if (!connectionsSectionHeaderTooltip.isEmpty()) {
			ArrayList<String> ExpectedTooltip = new ArrayList<String>();

			xPath = "//span[@title='Connections']/ancestor::div[@class='slds-grid slds-wrap']/following-sibling::div//span[@class='slds-truncate' and @title!='']";
			elements = FindElements(driver, xPath, "Connections section headers");

			for (WebElement el : elements) {
				ExpectedTooltip.add(getAttribute(driver, el, "Connections section headers Tooltip", "title"));
			}

			xPath = "//span[@title='Connections']/ancestor::div[@class='slds-grid slds-wrap']/following-sibling::div//lightning-icon";
			elements = FindElements(driver, xPath, "Connections section headers");
			for (WebElement el : elements) {
				ExpectedTooltip.add(getAttribute(driver, el, "Connections section headers Tooltip", "title"));
			}

			if (connectionsSectionHeaderTooltip.containsAll(ExpectedTooltip)) {
				log(LogStatus.INFO, "The Tooltip on Header of Connections section have been verified "
						+ connectionsSectionHeaderTooltip, YesNo.No);
			} else {
				log(LogStatus.ERROR, "The Tooltip on Header of Connections section are not verified "
						+ connectionsSectionHeaderTooltip, YesNo.No);
				result.add("The Tooltip on Header of contact Connections are not verified "
						+ connectionsSectionHeaderTooltip);
			}
		}
		return result;
	}

	public ArrayList<String> verifyRecordAndReferencedTypeOnTagged(String[] firmsTagName, String[] firmTimesReferenced,
			String[] peopleTagName, String[] peopleTimesReferenced, String[] dealTagName, String[] dealTimesReferenced,
			boolean isInstitutionRecordType) {
		ArrayList<String> result = new ArrayList<String>();
		if (firmsTagName != null && firmTimesReferenced != null) {
			if (firmsTagName.length == firmTimesReferenced.length) {
				if (click(driver, getTaggedRecordName("Firms", 30), "Firms tab", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Firms tab name", YesNo.No);

					for (int i = 0; i < firmsTagName.length; i++) {

						if (getTaggedRecordName("Firms", firmsTagName[i], 30) != null) {
							log(LogStatus.INFO, firmsTagName[i] + " record is available on firm tab of Tagged",
									YesNo.No);
							if (getTaggedRecordTimeReference("Firms", firmsTagName[i], firmTimesReferenced[i],
									30) != null) {
								log(LogStatus.INFO, "Time Reference : " + firmTimesReferenced[i]
										+ " is verified against " + firmsTagName[i] + " record on firm tab of Tagged",
										YesNo.No);
							} else {
								log(LogStatus.ERROR,
										"Time Reference : " + firmTimesReferenced[i] + " is not verified against "
												+ firmsTagName[i] + " record on firm tab of Tagged",
										YesNo.No);
								result.add("Time Reference : " + firmTimesReferenced[i] + " is not verified against "
										+ firmsTagName[i] + " record on firm tab of Tagged");
							}

						} else {
							log(LogStatus.ERROR, firmsTagName[i] + " record is not available on firm tab of Tagged",
									YesNo.No);
							result.add(firmsTagName[i] + " record is not available on firm tab of Tagged");
						}

					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Companies tab name", YesNo.No);
					result.add("Not able to click on Companies tab name");
				}
			} else {
				log(LogStatus.ERROR, "The size of tagged firm name and size of tagged time reference is not equal",
						YesNo.No);
				result.add("The size of tagged firm name and size of tagged time reference is not equal");
			}
		}

		if (peopleTagName != null && peopleTimesReferenced != null) {
			if (peopleTagName.length == peopleTimesReferenced.length) {
				if (click(driver, getTaggedRecordName("People", 30), "People tab", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on People tab name", YesNo.No);
					for (int i = 0; i < peopleTagName.length; i++) {
						if (getTaggedRecordName("People", peopleTagName[i], 30) != null) {
							log(LogStatus.INFO, peopleTagName[i] + " record is available on people tab of Tagged",
									YesNo.No);

							if (getTaggedRecordTimeReference("People", peopleTagName[i], peopleTimesReferenced[i],
									30) != null) {
								log(LogStatus.INFO,
										"Time Reference : " + peopleTimesReferenced[i] + " is verified against "
												+ peopleTagName[i] + " record on people tab of Tagged",
										YesNo.No);
							} else {
								log(LogStatus.ERROR,
										"Time Reference : " + peopleTimesReferenced[i] + " is not verified against "
												+ peopleTagName[i] + " record on people tab of Tagged",
										YesNo.No);
								result.add("Time Reference : " + peopleTimesReferenced[i] + " is not verified against "
										+ peopleTagName[i] + " record on people tab of Tagged");
							}

						} else {
							log(LogStatus.ERROR, peopleTagName[i] + " record is not available on people tab", YesNo.No);
							result.add(peopleTagName[i] + " record is not available on people tab");
						}
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on People tab name", YesNo.No);
					result.add("Not able to click on People tab name");
				}

			} else {
				log(LogStatus.ERROR, "The size of tagged people name and size of tagged time reference is not equal",
						YesNo.No);
				result.add("The size of tagged people name and size of tagged time reference is not equal");
			}
		}

		if (isInstitutionRecordType == false) {
			if (dealTagName != null && dealTimesReferenced != null) {
				if (dealTagName.length == dealTimesReferenced.length) {
					if (click(driver, getTaggedRecordName("Deals", 30), "Deals tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Deals tab name", YesNo.No);

						for (int i = 0; i < dealTagName.length; i++) {
							if (getTaggedRecordName("Deals", dealTagName[i], 6) != null) {
								log(LogStatus.INFO, dealTagName[i] + " record is available on deal tab of tagged",
										YesNo.No);

								if (getTaggedRecordTimeReference("Deals", dealTagName[i], dealTimesReferenced[i],
										6) != null) {
									log(LogStatus.INFO,
											"Time Reference : " + dealTimesReferenced[i] + " is verified against "
													+ dealTagName[i] + " record on deals tab of Tagged",
											YesNo.No);
								} else {
									log(LogStatus.ERROR,
											"Time Reference : " + dealTimesReferenced[i] + " is not verified against "
													+ dealTagName[i] + " record on deals tab of Tagged",
											YesNo.No);
									result.add(
											"Time Reference : " + dealTimesReferenced[i] + " is not verified against "
													+ dealTagName[i] + " record on deals tab of Tagged");
								}

							} else {
								log(LogStatus.ERROR, dealTagName[i] + " record is not available on deal tab of tagged",
										YesNo.No);
								result.add(dealTagName[i] + " record is not available on deal tab of tagged");
							}

						}

					} else {
						log(LogStatus.ERROR, "Not able to click on Deals tab name", YesNo.No);
						result.add("Not able to click on Deals tab name");
					}
				}
			}
		}
		return result;
	}

	public ArrayList<String> verifyFieldWithDataonResearchPage(String environment, String mode, String[][] Data) {

		ArrayList<String> verifyData = new ArrayList<String>();
		int row = Data.length;
		ArrayList<String> DataFromExcel = new ArrayList<String>();

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < Data[0].length; j++) {
				DataFromExcel.add(Data[i][j]);

				String xpath = "//div[contains(@class,'active')]//a[text()='" + Data[i][j] + "']";
				WebElement ele = CommonLib.FindElement(driver, xpath, Data[i][j], action.BOOLEAN, 10);
				click(driver, ele, xpath, action.BOOLEAN);
			}
		}
		return verifyData;
	}

	public ArrayList<String> verifyRecordsonInteractionsViewAllPopup(IconType[] icon, String[] date, String[] subject,
			String[] details, String[] assignedTo, String[] correspondenceHeader) {
		String xPath;
		WebElement ele;
		ArrayList<String> result = new ArrayList<String>();
		String parentId = switchOnWindow(driver);
		if (parentId != null) {
			if (correspondenceHeader != null && correspondenceHeader.length != 0) {
				for (int i = 0; i < correspondenceHeader.length; i++) {

					if (icon[i] != null && icon.length != 0 && icon[i].toString() != "") {
						xPath = "//button[text()='" + correspondenceHeader[i]
								+ "']/ancestor::tr//th[@data-label='Type']//lightning-icon";
						ele = FindElement(driver, xPath, "Icon type of " + correspondenceHeader[i],
								action.SCROLLANDBOOLEAN, 20);
						String iconVal = getAttribute(driver, ele, "Icon type", "class");
						if (iconVal.toLowerCase().contains(icon[i].toString().toLowerCase())) {
							log(LogStatus.INFO, "The icon :" + icon[i].toString() + " has been verified against "
									+ correspondenceHeader[i] + " record", YesNo.No);
						} else {
							log(LogStatus.ERROR, "The icon :" + icon[i].toString() + " is not verified against "
									+ correspondenceHeader[i] + " record", YesNo.No);
							result.add("The icon :" + icon[i].toString() + " is not verified against "
									+ correspondenceHeader[i] + " record");
						}
					}

					if (date != null && date.length != 0 && date[i] != "" && date[i] != null) {
						xPath = "//button[text()='" + correspondenceHeader[i]
								+ "']/ancestor::tr//td[@data-label='Date']//lightning-base-formatted-text";
						ele = FindElement(driver, xPath, "date ", action.BOOLEAN, 25);
						String actDate = getText(driver, ele, "date ", action.BOOLEAN);

						String actualDate = null;

						if (actDate.contains(",")) {
							actualDate = actDate.split(",")[0];
						} else {
							actualDate = actDate;
						}

						String dueDate;
						if (date[i].contains(",")) {
							dueDate = date[i].split(",")[0];
						} else {
							dueDate = date[i];
						}

						String[] splittedDate = dueDate.split("/");
						char dayMonth = splittedDate[0].charAt(0);
						char day = splittedDate[1].charAt(0);
						String month;
						if (dayMonth == '0') {
							month = splittedDate[0].replaceAll("0", "");
						} else {
							month = splittedDate[0];
						}
						String finalDay;
						if (day == '0') {
							finalDay = splittedDate[1].replaceAll("0", "");
						} else {
							finalDay = splittedDate[1];
						}

						String expectedDate = month + "/" + finalDay + "/" + splittedDate[2];

						if (actualDate.trim().equalsIgnoreCase(expectedDate.trim())) {
							log(LogStatus.INFO, "actual date : " + actualDate
									+ " has been matched with the Expected date : " + expectedDate, YesNo.No);
						} else {
							log(LogStatus.ERROR, "actual date : " + actualDate
									+ " is not matched with the Expected date : " + expectedDate, YesNo.No);
							result.add("actual date : " + actualDate + " is not matched with the Expected date : "
									+ expectedDate);
						}
					}
					if (subject != null && subject.length != 0 && subject[i] != "" && subject[i] != null) {
						xPath = "//tr//td[@data-label='Subject']//button[text()='" + correspondenceHeader[i] + "']";
						ele = FindElement(driver, xPath, "subject ", action.BOOLEAN, 25);
						String actSubject = getText(driver, ele, "subject ", action.BOOLEAN);
						if (subject[i].equalsIgnoreCase(actSubject)) {
							log(LogStatus.INFO, "actual subject : " + actSubject
									+ " has been matched with the Expected subject : " + subject[i], YesNo.No);
						} else {
							log(LogStatus.ERROR, "actual subject : " + actSubject
									+ " is not matched with the Expected subject : " + subject[i], YesNo.No);
							result.add("actual subject : " + actSubject + " is not matched with the Expected subject : "
									+ subject[i]);
						}
					}
					if (details != null && details.length != 0 && details[i] != "" && details[i] != null) {
						xPath = "//button[text()='" + correspondenceHeader[i]
								+ "']/ancestor::tr//td[@data-label='Details']//button";
						ele = FindElement(driver, xPath, "details ", action.BOOLEAN, 25);
						String actDetails = getText(driver, ele, "details ", action.BOOLEAN);
						if (actDetails.equalsIgnoreCase(details[i])) {
							log(LogStatus.INFO, "actual details : " + actDetails
									+ " has been matched with the Expected details : " + details[i], YesNo.No);
						} else {
							log(LogStatus.ERROR, "actual details : " + actDetails
									+ " is not matched with the Expected details : " + details[i], YesNo.No);
							result.add("actual details : " + actDetails + " is not matched with the Expected details : "
									+ details[i]);
						}
					}
					/*
					 * if (assignedTo != null && assignedTo.length != 0 && assignedTo[i] != "" &&
					 * assignedTo[i] != null) { xPath =
					 * "//h2[contains(text(),'All Interactions')]/..//following-sibling::div//*[text()='"
					 * + correspondenceHeader[i] +
					 * "']/ancestor::tr//td[@data-label='Assigned To']//a"; ele =
					 * FindElement(driver, xPath, "assigned to ", action.BOOLEAN, 25); String
					 * actAssigned = getText(driver, ele, "assigned to ", action.BOOLEAN); if
					 * (actAssigned.equalsIgnoreCase(assignedTo[i])) { log(LogStatus.INFO,
					 * "actual AssignedTo value : " + actAssigned +
					 * " has been matched with the Expected AssignedTo value : " + assignedTo[i],
					 * YesNo.No); } else { log(LogStatus.ERROR, "actual AssignedTo value : " +
					 * actAssigned + " is not matched with the Expected AssignedTo value : " +
					 * assignedTo[i], YesNo.No); result.add("actual AssignedTo value : " +
					 * actAssigned + " is not matched with the Expected AssignedTo value : " +
					 * assignedTo[i]); } }
					 */
				}
			} else {
				log(LogStatus.ERROR,
						"Either correspondence is null or Empty. Please provide data to verify data on interaction popup ",
						YesNo.No);
				result.add(
						"Either correspondence is null or Empty. Please provide data to verify data on interaction popup");
			}
			driver.close();
			driver.switchTo().window(parentId);
		} else {
			log(LogStatus.ERROR, "New tab did not open after click", YesNo.No);
			result.add("New tab did not open after click");
		}

		return result;
	}

	public List<String> verifyNotificationOptionsOnRecordDetailsPage(String... eventName) {

		// company page
		CommonLib.clickUsingJavaScript(driver, getNotificationIcon(), "NotificationIcon"); // click on notification icon

		List<WebElement> notificationOptionsList = getNotificationOptions(); // fetch notification option list
		List<WebElement> notificationButtonsList = getnotificationButtons(); // fetch notification button list
		List<String> negativeResults = new ArrayList<String>(); // creating blank arrayList
		boolean flag = false;

		if (notificationHeaderInRecordDetailsPage(5) != null) {
			log(LogStatus.PASS,
					"Notification Header is present there: " + notificationHeaderInRecordDetailsPage(5).getText(),
					YesNo.No);

		} else {
			log(LogStatus.FAIL, "Notification Header is not present there", YesNo.No);
			negativeResults.add("Notification Header is not present there");
		}

		if (notificationCloseIconInRecordDetailsPage(5) != null) {
			log(LogStatus.PASS, "Notification Close Icon is present there", YesNo.No);

		} else {
			log(LogStatus.FAIL, "Notification Close Icon is not present there", YesNo.No);
			negativeResults.add("Notification Close Icon is not present there");
		}

		List<String> notificationOptionsListInText = notificationOptionsList.stream()
				.map(x -> CommonLib.getText(driver, x, "Event Name", action.BOOLEAN)).collect(Collectors.toList());

		for (int i = 0; i < eventName.length; i++) {
			if (notificationOptionsListInText.contains(eventName[i])) {

				if (notificationOptionsList.get(notificationOptionsListInText.indexOf(eventName[i])).isDisplayed()) {
					flag = false;

					if (notificationButtonsList != null) {
						for (WebElement actualEventButton : notificationButtonsList) {

							if (notificationButtonsList.indexOf(actualEventButton) == notificationOptionsListInText
									.indexOf(eventName[i])) {
								if (actualEventButton.isDisplayed())
									flag = true;
								break;
							} else {
								continue;
							}
						}
					} else {
						log(LogStatus.ERROR, "Add note button list is empty..might locator has mismatched", YesNo.Yes);
					}
					if (flag) {
						log(LogStatus.PASS, "Event name is visible" + eventName[i], YesNo.No);
						log(LogStatus.PASS, "Add note Button is visible for " + eventName[i], YesNo.No);

					} else {
						log(LogStatus.FAIL, "Button for Event: " + eventName[i] + " is not visible", YesNo.No);
						negativeResults.add("Button for Event: " + eventName[i] + " is not visible");
					}

				}
			}

			else {

				log(LogStatus.FAIL,
						"Event: " + eventName[i] + " is not present there in Notification Pane in record detail page",
						YesNo.No);
				negativeResults.add(
						"Event: " + eventName[i] + " is not present there in Notification Pane in record detail page");
			}
		}

		return negativeResults;
	}

	public ArrayList<String> verifyUIOfConnectionPopup(String recordName, ArrayList<String> headingName,
			String message) {
		String xPath;
		WebElement ele;
		List<WebElement> elements;
		ArrayList<String> result = new ArrayList<String>();
		String parentID = CommonLib.switchOnWindow(driver);
		if (getHeadingOfConnectionPage(recordName, 20) != null) {
			log(LogStatus.INFO, "Connection popup has been open and heading of connection popup has been verified",
					YesNo.No);

			if (!headingName.isEmpty()) {
				ArrayList<String> actualHeadingName = new ArrayList<String>();
				xPath = "//span[contains(text(),'Connections')]/ancestor::div//span[@class='slds-th__action']/span[@class='slds-truncate' and @title!='']";
				elements = FindElements(driver, xPath, "Connections section headers");
				for (WebElement el : elements) {
					actualHeadingName.add(getText(driver, el, "Connections section headers", action.SCROLLANDBOOLEAN));
				}

				xPath = "//span[@class='slds-th__action']//lightning-icon[@title!='Close']";
				elements = FindElements(driver, xPath, "Connections section headers");
				for (WebElement el : elements) {
					actualHeadingName.add(getAttribute(driver, el, "Connections section headers", "title"));
				}

				for (int i = 0; i < headingName.size(); i++) {
					int k = 0;
					for (int j = 0; j < actualHeadingName.size(); j++) {
						if (headingName.get(i).trim().equalsIgnoreCase(actualHeadingName.get(j).trim())) {
							log(LogStatus.INFO,
									"Expected heading name " + headingName.get(i)
											+ " have been matched with the Actual heading name "
											+ actualHeadingName.get(j) + " of Connection page",
									YesNo.No);
							k++;
						}
					}
					if (k == 0) {
						log(LogStatus.ERROR,
								"Expected heading name " + headingName.get(i) + " is not matched on Connection page",
								YesNo.No);
						result.add(
								"Expected heading name " + headingName.get(i) + " is not matched on Connection page");
					}
				}
			}

			if (message != null && message != "") {
				xPath = "//span[contains(text(),'Connections of')]/ancestor::div//p[text()='" + message + "']";
				ele = FindElement(driver, xPath, "message on popup", action.SCROLLANDBOOLEAN, 20);
				if (ele != null) {
					log(LogStatus.INFO, message + ": Message is visible on Connection popup", YesNo.No);
				} else {
					log(LogStatus.ERROR, message + ": Message is not visible on Connection popup", YesNo.No);
					result.add(message + ": Message is not visible on Connection popup");
				}
			}
		} else {
			log(LogStatus.ERROR, "Either Connection popup did not open or Connection popup heading is not verified",
					YesNo.No);
			result.add("Either Connection popup did not open or Connection popup heading is not verified");
		}
		driver.close();
		driver.switchTo().window(parentID);

		return result;

	}

	public boolean clickOnAlreadyCreated_Lighting(String environment, String mode, TabName tabName, String recordType,
			String alreadyCreated, int timeout) {

		String viewList = null;
		switch (tabName) {
		case ContactTab:
			viewList = "All Contacts";
			break;
		case InstituitonsTab:
			viewList = "All Firms";
			break;
		case CompaniesTab:
			viewList = "All Companies";
			break;
		case LimitedPartner:
			viewList = "All Limited Partners";
			break;
		case FundraisingsTab:
			viewList = "All";
			break;
		case FundsTab:
			viewList = "All";
			break;
		case CommitmentsTab:
			viewList = "All";
			break;
		case PartnershipsTab:
			viewList = "All";
			break;
		case FundDistributions:
			viewList = "All";
			break;
		case InvestorDistributions:
			viewList = "All";
			break;
		case MarketingInitiatives:
			viewList = "All";
			break;
		case MarketingProspects:
			viewList = "Marketing Prospects";
			break;
		case Pipelines:
			viewList = "All";
			break;
		case CapitalCalls:
			viewList = "All";
			break;
		case FundDrawdowns:
			viewList = "All";
			break;
		case FundraisingContacts:
			viewList = "All";
			break;
		case OfficeLocations:
			viewList = "All";
			break;
		default:
			return false;
		}
		System.err.println("Passed switch statement");
		WebElement ele, selectListView;
		ele = null;
		if (click(driver, getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(3000);
			selectListView = FindElement(driver, "//div[@class='listContent']//li/a/span[text()='" + viewList + "']",
					"Select List View", action.SCROLLANDBOOLEAN, 30);
			if (click(driver, selectListView, "select List View", action.SCROLLANDBOOLEAN)) {
				ThreadSleep(3000);
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					refresh(driver);
					ThreadSleep(5000);
				}
				if (sendKeys(driver, getSearchIcon_Lighting(20), alreadyCreated + "\n", "Search Icon Text",
						action.SCROLLANDBOOLEAN)) {
					ThreadSleep(5000);
					ele = FindElement(driver,
							"//table[@data-aura-class='uiVirtualDataTable']//tbody//tr//td//span[text()='" + recordType
									+ "']/ancestor::td/preceding-sibling::th//span//a[text()='" + alreadyCreated + "']",
							"Record: " + alreadyCreated + " of Record Type: " + recordType, action.BOOLEAN, 30);
					ThreadSleep(2000);
					if (click(driver, ele, "Record: " + alreadyCreated + " of Record Type: " + recordType,
							action.BOOLEAN)) {
						ThreadSleep(3000);
						return true;
					} else {
						appLog.error("Not able to Click on Already Created, Record: " + alreadyCreated
								+ " of Record Type: " + recordType);
					}
				} else {
					appLog.error("Not able to enter value on Search Box");
				}
			} else {
				appLog.error("Not able to select on Select View List");
			}
		} else {
			appLog.error("Not able to click on Select List Icon");
		}
		return false;
	}

	public boolean verifySubjectLinkRedirectionOnIntractionAndAbleToEditCommentsOfTask(WebDriver driver,
			String subjectName, String commentToUpdate) {
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);
		String xPath;
		WebElement ele;
		boolean flag = false;
		xPath = "//a[@class=\"interaction_sub subject_text\" and text()='" + subjectName + "']";
		ele = FindElement(driver, xPath, "Subject Name", action.SCROLLANDBOOLEAN, 30);
		if (CommonLib.clickUsingJavaScript(driver, ele, "Subject Name on Intraction")) {
			log(LogStatus.INFO, "clicked on " + subjectName, YesNo.No);

			String windowID = switchOnWindow(driver);
			if (windowID != null) {

				if (getPageHeaderTitle(20) != null) {
					log(LogStatus.INFO, subjectName + " link is redirecting to Details Page", YesNo.No);

					if (taskBP.editCommentsIntask(subjectName, commentToUpdate)) {
						log(LogStatus.INFO, "Updated Comment of Task: " + subjectName + " verified: " + commentToUpdate,
								YesNo.No);
						driver.close();
						driver.switchTo().window(windowID);
						flag = true;
					} else {
						log(LogStatus.ERROR,
								"Updated Comment of Task: " + subjectName + " is not verified: " + commentToUpdate,
								YesNo.No);
						driver.close();
						driver.switchTo().window(windowID);
					}

				} else {
					log(LogStatus.ERROR, subjectName + " links is not redirecting to Details Page", YesNo.No);
				}
			} else {
				log(LogStatus.ERROR, subjectName + " url did not open in new tab", YesNo.No);
			}
		} else {
			log(LogStatus.ERROR, "not able to click on " + subjectName, YesNo.No);
		}
		return flag;
	}

	public boolean verifyUIOfSuggestedTagSection(String[] suggestedTags) {

		List<String> expectedColumnList = new ArrayList<String>();
		expectedColumnList.add("Name");
		expectedColumnList.add("Type");

		List<String> expectedFooterList = new ArrayList<String>();
		expectedFooterList.add("Tag");
		expectedFooterList.add("Cancel");
		int expectedCount = suggestedTags.length;

		int resultStatus = 0;
		int loopCount = 0;
		int status = 0;

		if (suggestedTagHeading(20) != null) {
			log(LogStatus.INFO, "Suggested Tag Heading Verified", YesNo.No);
			List<String> columnsList = listOfColumnsOfSuggestedTags().stream()
					.map(x -> CommonLib.getText(driver, x, "Column", action.BOOLEAN)).collect(Collectors.toList());
			if (!columnsList.isEmpty()) {
				if (columnsList.equals(expectedColumnList)) {

					log(LogStatus.INFO, "Columns and their Sequence Matched under Suggested Tags: " + columnsList,
							YesNo.No);
					resultStatus++;
				} else {
					log(LogStatus.ERROR, "Columns and their Sequence not Matched under Suggested Tags, Expected: "
							+ expectedColumnList + " but Actual: " + columnsList, YesNo.Yes);
					sa.assertTrue(false, "Columns and their Sequence not Matched under Suggested Tags, Expected: "
							+ expectedColumnList + " but Actual: " + columnsList);
				}

				for (int i = 0; i < suggestedTags.length; i++) {
					String[] column = suggestedTags[i].split("==", -1);
					String xPath = "//lightning-base-formatted-text[text()='" + column[0]
							+ "']/ancestor::th[@data-label='Name']/following-sibling::td//lightning-base-formatted-text[text()='"
							+ column[1] + "']/ancestor::td/preceding-sibling::td//input";
					WebElement ele = CommonLib.FindElement(driver, xPath,
							column[0] + " sugested Tag of Type: " + column[1], action.SCROLLANDBOOLEAN, 30);
					if (click(driver, ele, column[0] + " sugested Tag of Type: " + column[1],
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,
								"clicked on " + column[0] + " sugested Tag of Type: " + column[1] + " checkbox button",
								YesNo.No);
						status++;
						resultStatus++;

					} else {
						log(LogStatus.ERROR, "Not able to click on " + column[0] + " sugested Tag of Type: " + column[1]
								+ " checkbox button", YesNo.No);
						sa.assertTrue(false, "Not able to click on " + column[0] + " sugested Tag of Type: " + column[1]
								+ " checkbox button");

					}
					loopCount++;
				}

				if (loopCount == status) {
					String countText = CommonLib.getText(driver, suggestedTagCountOfCheckBoxes(), "Count: ",
							action.BOOLEAN);
					if (countText.equalsIgnoreCase(expectedCount + " items selected")) {
						log(LogStatus.INFO, "Count is Matched, Expected: " + expectedCount + " items selected"
								+ " , But Actual: " + countText, YesNo.No);
						resultStatus++;
					} else {
						log(LogStatus.ERROR, "Count is not Matched, Expected: " + expectedCount + " items selected"
								+ " , But Actual: " + countText, YesNo.No);
						sa.assertTrue(false, "Count is not Matched, Expected: " + expectedCount + " items selected"
								+ " , But Actual: " + countText);
					}
				} else {

					log(LogStatus.ERROR,
							"Not Able to Click on CheckBox, So no able to Check the Counts of Selected CheckBox",
							YesNo.No);
					sa.assertTrue(false,
							"Not Able to Click on CheckBox, So no able to Check the Counts of Selected CheckBox");

				}

				List<String> actualFooterList = suggestedTagFooterButtons().stream()
						.map(x -> CommonLib.getText(driver, x, "Footer", action.BOOLEAN)).collect(Collectors.toList());

				if (actualFooterList.containsAll(expectedFooterList)) {
					log(LogStatus.INFO, "Footer List Matched: " + expectedFooterList, YesNo.No);
					resultStatus++;

				} else {
					log(LogStatus.ERROR, "Footer List not Matched, Expected: " + expectedFooterList + ", Actual: "
							+ expectedFooterList, YesNo.No);
					sa.assertTrue(false, "Footer List not Matched, Expected: " + expectedFooterList + ", Actual: "
							+ expectedFooterList);

				}

			} else {
				log(LogStatus.ERROR,
						"Either Locator of Columns not correct or No Columns present under Suggested Tags Section",
						YesNo.Yes);
				sa.assertTrue(false,
						"Either Locator of Columns not correct or No Columns present under Suggested Tags Section");
			}

		} else {
			log(LogStatus.ERROR, "Suggested Tag Heading Not Verified", YesNo.Yes);
			sa.assertTrue(false, "Suggested Tag Heading Not Verified");

		}
		if (resultStatus == status + 3)
			return true;
		else
			return false;
	}

	public boolean createActivityTimelineAlsoVerifyUIOfSuggestedTag(String projectName, boolean fromNavigation,
			String buttonName, String[][] basicSection, String[][] advanceSection, String[][] taskSection,
			String[] suggestedTags) {
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		String xPath = "";
		WebElement ele;
		boolean flag = false;

		if (fromNavigation == true) {

			if (npbl.createNavPopUpMinimizeButton(5) != null) {
				CommonLib.click(driver, npbl.createNavPopUpMinimizeButton(5), "Minimize", action.BOOLEAN);
			}
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, NavigationMenuItems.Create.toString(), action.BOOLEAN,
					30)) {
				log(LogStatus.INFO, "Able to Click on " + buttonName + " Going to click on : "
						+ NavigationMenuItems.Create.toString() + " for creation ", YesNo.No);
				ele = npbl.getNavigationLabel(projectName, buttonName, action.BOOLEAN, 10);
				if (ele != null && CommonLib.click(driver, ele, buttonName, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on " + buttonName + " so going for creation", YesNo.No);

				} else {
					log(LogStatus.ERROR,
							"Not Able to Click on " + buttonName + " so cannot create data related to this ",
							YesNo.Yes);
					sa.assertTrue(false,
							"Not Able to Click on " + buttonName + " so cannot create data related to this ");
					return false;
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on " + NavigationMenuItems.Create.toString()
						+ " so cannot click on : " + buttonName + " for creation ", YesNo.Yes);
				sa.assertTrue(false, "Not Able to Click on " + NavigationMenuItems.Create.toString()
						+ " so cannot click on : " + buttonName + " for creation ");
				return false;
			}
		} else {
			if (CommonLib.clickUsingJavaScript(driver, activityTimelineButton(buttonName, 30), buttonName + " button",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on " + buttonName + " button name", YesNo.No);
			} else {
				log(LogStatus.ERROR, "Not able to click on " + buttonName, YesNo.No);
				sa.assertTrue(false, "Not able to click on " + buttonName);
				return false;
			}
		}

		String detail = CommonLib.getAttribute(driver, getNotePopUpSectionDetail("Tasks", 30), "Tasks Section",
				"aria-hidden");

		if (detail.equals("true")) {

		} else {

			clickUsingJavaScript(driver, getSectionBtn("Tasks", 30), "Tasks section", action.SCROLLANDBOOLEAN);
		}

		String detail2 = CommonLib.getAttribute(driver, getNotePopUpSectionDetail("Advanced", 30), "Advanced Section",
				"aria-hidden");

		if (detail2.equals("true")) {

		} else {

			clickUsingJavaScript(driver, getSectionBtn("Advanced", 30), "Advanced section", action.SCROLLANDBOOLEAN);
		}
		if (basicSection != null) {

			for (String[] val : basicSection) {
				String labelName = val[0];
				String value = val[1];

				if (labelName.contains(excelLabel.Subject.toString())) {
					xPath = "//label[text()='" + labelName + "']/..//input[contains(@data-id,'combobox')]";
					ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
					if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
					} else {
						log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
						sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
						return false;

					}
				} else if (labelName.contains(excelLabel.Notes.toString())) {
					xPath = "//div[label[text()='Notes']]//textarea";
					ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
					if (CommonLib.clickUsingJavaScript(driver, ele, labelName + " paragraph")) {
						log(LogStatus.INFO, "Clicked on " + labelName + " paragraph", YesNo.No);
						ThreadSleep(2000);

						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (sendKeys(driver, ele, value, labelName + " paragraph", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " has been passed on " + labelName + " paragraph", YesNo.No);

							CommonLib.ThreadSleep(2000);
						} else {
							log(LogStatus.ERROR, value + " is not passed on " + labelName + " paragraph", YesNo.No);
							sa.assertTrue(false, value + " is not passed on " + labelName + " paragraph");
							return false;
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on " + labelName + " paragraph", YesNo.No);
						sa.assertTrue(false, "Not able to click on " + labelName + " paragraph");
						return false;
					}

				} else if (labelName.equalsIgnoreCase(excelLabel.Related_To.toString())) {
					ArrayList<String> tagList = new ArrayList<String>();

					if (value.contains("<break>")) {
						String[] data = value.split("<break>");
						for (int i = 0; i < data.length; i++) {
							tagList.add(data[i]);
						}
					} else {
						tagList.add(value);
					}

					for (int i = 0; i < tagList.size(); i++) {

						if (tagList.get(i).contains("Prefilled")) {
							String val1 = tagList.get(i);
							String[] tgName = tagList.get(i).split("<Prefilled>");
							xPath = "//lightning-pill[@data-id='" + tgName[0] + "']";
							ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN,
									30);
							if (ele != null) {
								log(LogStatus.INFO, tgName[0] + " is prefilled", YesNo.No);
							} else {
								log(LogStatus.ERROR, tgName[0] + " is not prefilled", YesNo.No);
								sa.assertTrue(false, tgName[0] + " is not prefilled");
								return false;
							}

						} else {
							xPath = "//input[@placeholder='Search Firm, People or Deals']";
							ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN,
									2);
							// h2[contains(@class,'header_text')]/../..//*[@title='Tag']
							if (ele == null) {
								xPath = "//h2[contains(@class,'header_text')]/../..//*[@title='Tag']";
								ele = CommonLib.FindElement(driver, xPath, labelName + " label",
										action.SCROLLANDBOOLEAN, 30);
								if (CommonLib.clickUsingJavaScript(driver, ele, labelName + " paragraph")) {
									log(LogStatus.INFO, "Clicked on Tag button", YesNo.No);
								} else {
									log(LogStatus.ERROR, "Not able to click on Tag button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Tag button");
									return false;
								}

							}

							xPath = "//input[@placeholder='Search Firm, People or Deals']";
							ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN,
									2);
							if (sendKeys(driver, ele, tagList.get(i), "Tag", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,
										tagList.get(i) + " value has been passed in " + labelName + " field", YesNo.No);
								ThreadSleep(3000);
								xPath = "//ul[@class='drop_ul']//li[text()='" + tagList.get(i) + "']";
								ele = CommonLib.FindElement(driver, xPath, labelName + " label",
										action.SCROLLANDBOOLEAN, 30);
								if (click(driver, ele, tagList + " dropdown", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on " + tagList.get(i) + " value", YesNo.No);
								} else {
									log(LogStatus.ERROR, "Not able to click on " + tagList.get(i) + " value", YesNo.No);
									sa.assertTrue(false, "Not able to click on " + tagList.get(i) + " value");
									return false;
								}

							} else {
								log(LogStatus.ERROR, tagList.get(i) + " value is not passed in " + labelName + " field",
										YesNo.No);
								sa.assertTrue(false,
										tagList.get(i) + " value is not passed in " + labelName + " field");
								return false;
							}
						}
					}
				} else {
					log(LogStatus.ERROR, "Label Name is not correct", YesNo.No);
					sa.assertTrue(false, "Label Name is not correct");
					return false;
				}
			}

		}
		if (advanceSection != null) {

			if (clickUsingJavaScript(driver, getSectionBtn("Advanced", 30), "Advanced section",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on Advanced section", YesNo.No);
				for (String[] val : advanceSection) {
					String labelName = val[0];
					String value = val[1];
					// String fieldType=val[2];

					if (labelName.contains("Start Date")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='Start Date Time']/..//label[text()='Date']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}
					} else if (labelName.contains("Start Time")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='Start Date Time']/..//label[text()='Time']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					} else if (labelName.contains("End Date")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='End Date Time']/..//label[text()='Date']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					} else if (labelName.contains("End Time")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='End Date Time']/..//label[text()='Time']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					}

					else if (labelName.contains(excelLabel.Location.toString()) || labelName.contains("Due Date")
							|| labelName.contains("Date")) {

						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
								+ labelName + "']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					}

					else if (labelName.contains(excelLabel.Status.toString())
							|| labelName.contains(excelLabel.Priority.toString())) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
								+ labelName + "']/..//button";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
								+ labelName + "']/..//span[@class='slds-truncate']";
						if (CommonLib.dropDownHandle(driver, ele, xPath, labelName + " dropdown", value)) {
							log(LogStatus.INFO, value + " value has been selected from " + labelName + " field",
									YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not selected from " + labelName + " field",
									YesNo.No);
							sa.assertTrue(false, value + " value is not selected from " + labelName + " field");
							return false;
						}
					} else if (labelName.equalsIgnoreCase("All-Day Event")) {

						if (value.equals("true")) {
							xPath = "//span[text()='All-Day Event']/../../input";
							ele = FindElement(driver, xPath, labelName + " name", action.SCROLLANDBOOLEAN, 30);
							if (isSelected(driver, ele, labelName + " checkbox")) {
								log(LogStatus.INFO, labelName + " checkbox is already selected", YesNo.No);

							} else {
								if (click(driver, ele, labelName + " checkbox", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, labelName + " checkbox has been selected", YesNo.No);
								} else {
									log(LogStatus.ERROR, labelName + " checkbox is not selected", YesNo.No);
									sa.assertTrue(false, labelName + " checkbox is not selected");
									return false;
								}
							}

						} else {
							xPath = "//span[text()='All-Day Event']/../../input";
							ele = FindElement(driver, xPath, labelName + " name", action.SCROLLANDBOOLEAN, 30);
							if (!isSelected(driver, ele, labelName + " checkbox")) {
								log(LogStatus.INFO, labelName + " checkbox is not selected", YesNo.No);

							} else {
								if (click(driver, ele, labelName + " checkbox", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, labelName + " checkbox has been unselected", YesNo.No);
								} else {
									log(LogStatus.ERROR, labelName + " checkbox is not unselected", YesNo.No);
									sa.assertTrue(false, labelName + " checkbox is not unselected");
									return false;
								}
							}
						}
					} else if (labelName.contains("User")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
								+ labelName + "']/..//button";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (click(driver, ele, labelName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on cross icon of value of " + labelName, YesNo.No);
							xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
									+ labelName + "']/..//input";
							ele = CommonLib.FindElement(driver, xPath, labelName + " input", action.SCROLLANDBOOLEAN,
									30);
							if (sendKeys(driver, ele, value, labelName + " input", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, value + " has been passed in " + labelName, YesNo.No);
								xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
										+ labelName + "']/../..//ul//li[text()='" + value + "']";
								ele = CommonLib.FindElement(driver, xPath, value + " list", action.SCROLLANDBOOLEAN,
										30);
								if (clickUsingJavaScript(driver, ele, value + " list element")) {
									log(LogStatus.INFO, "click on the list element of " + value, YesNo.No);
								} else {
									log(LogStatus.ERROR, "Not able to click on the list element of " + value, YesNo.No);
									sa.assertTrue(false, "Not able to click on the list element of " + value);
									return false;
								}

							} else {
								log(LogStatus.ERROR, value + " is not passed in " + labelName, YesNo.No);
								sa.assertTrue(false, value + " is not passed in " + labelName);
								return false;
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on cross icon of value of " + labelName, YesNo.No);
							sa.assertTrue(false, "Not able to click on cross icon of value of " + labelName);
							return false;
						}
					}

					else {
						log(LogStatus.ERROR, "label name is not correct", YesNo.No);
						sa.assertTrue(false, "label name is not correct");
						return false;
					}
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Advanced search section", YesNo.No);
				sa.assertTrue(false, "Not able to click on Advanced search section");
				return false;
			}
		}
		if (taskSection != null) {

			xPath = "//span[text()='Advanced']/parent::button[@aria-expanded='true']";
			ele = FindElement(driver, xPath, "Advance section", action.SCROLLANDBOOLEAN, 3);
			if (ele == null) {
				if (clickUsingJavaScript(driver, getSectionBtn("Advanced", 3), "Advanced section",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on Advanced section", YesNo.No);
				} else {
					log(LogStatus.ERROR, "Not able to click on Advanced section", YesNo.No);
					sa.assertTrue(false, "Not able to click on Advanced section");
					return false;
				}
			}

			if (clickUsingJavaScript(driver, getSectionBtn("Tasks", 30), "Tasks section", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on Tasks section", YesNo.No);

				for (String[] val : taskSection) {
					String labelName = val[0];
					String value = val[1];

					if (labelName.contains(excelLabel.Subject.toString()) || labelName.contains("Due Date")
							|| labelName.contains("Date")) {
						xPath = "//span[text()='Tasks']/ancestor::lightning-accordion[1]//label[text()='" + labelName
								+ "']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}
					} else if (labelName.contains(excelLabel.Status.toString())) {
						xPath = "//span[text()='Tasks']/ancestor::lightning-accordion[1]//label[text()='" + labelName
								+ "']/..//button";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						xPath = "//span[text()='Tasks']/ancestor::lightning-accordion[1]//label[text()='" + labelName
								+ "']/..//span[@class='slds-truncate']";
						if (CommonLib.dropDownHandle(driver, ele, xPath, labelName + " dropdown", value)) {
							log(LogStatus.INFO, value + " value has been selected from " + labelName + " field",
									YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not selected from " + labelName + " field",
									YesNo.No);
							sa.assertTrue(false, value + " value is not selected from " + labelName + " field");
							return false;
						}
					} else {
						log(LogStatus.ERROR, "Label name is not correct", YesNo.No);
						sa.assertTrue(false, "Label name is not correct");
						return false;
					}
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on Tasks section", YesNo.No);
				sa.assertTrue(false, "Not able to click on Tasks section");
				return false;
			}
		}

		if (click(driver, getfooterSaveOrCancelButton("Save", 20), "Save button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "clicked on Save button", YesNo.No);

			if (suggestedTags != null) {

				log(LogStatus.INFO, "Activity timeline record has been created", YesNo.No);
				ThreadSleep(2000);
				boolean suggestedFlag = verifyUIOfSuggestedTagSection(suggestedTags);
				if (suggestedFlag == true) {
				} else {
					return false;
				}
				if (click(driver, getfooterTagButton(30), "Tag Button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on footer tag button", YesNo.No);
					ThreadSleep(3000);
					if (crossIconButtonInNotePopUp(8) != null) {
						if (clickUsingJavaScript(driver, crossIconButtonInNotePopUp(8), "close button")) {
							log(LogStatus.INFO, "Note popup has been closed", YesNo.No);
							flag = true;
						} else {
							log(LogStatus.ERROR, "Not able to close the Note popup", YesNo.No);
							sa.assertTrue(false, "Not able to close the Note popup");
							return false;
						}
					} else {
						return true;
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on footer tag button", YesNo.No);
					sa.assertTrue(false, "Not able to click on footer tag button");
					return false;
				}

			}

			else {

				log(LogStatus.INFO, "Activity timeline record has been created", YesNo.No);
				ThreadSleep(2000);
				refresh(driver);
				ThreadSleep(3000);
				if (crossIconButtonInNotePopUp(8) != null) {
					if (clickUsingJavaScript(driver, crossIconButtonInNotePopUp(8), "close button")) {
						log(LogStatus.INFO, "Note popup has been closed", YesNo.No);
						flag = true;
					} else {
						log(LogStatus.ERROR, "Not able to close the Note popup", YesNo.No);
						sa.assertTrue(false, "Not able to close the Note popup");
						return false;
					}
				} else {
					return true;
				}

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Save button", YesNo.No);
			sa.assertTrue(false, "Not able to click on Save button");
			return false;
		}

		return flag;
	}

	public boolean verifySubjectLinkRedirectionOnIntractionAndAbleToClickOnEditButtonInTaskDetailPage(WebDriver driver,
			String subjectName) {
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);
		String xPath;
		WebElement ele;
		boolean flag = false;
		xPath = "//a[@class=\"interaction_sub subject_text\" and text()='" + subjectName + "']";
		ele = FindElement(driver, xPath, "Subject Name", action.SCROLLANDBOOLEAN, 30);
		if (CommonLib.clickUsingJavaScript(driver, ele, "Subject Name on Intraction")) {
			log(LogStatus.INFO, "clicked on " + subjectName, YesNo.No);

			String windowID = switchOnWindow(driver);
			if (windowID != null) {

				if (getPageHeaderTitle(25) != null) {
					log(LogStatus.INFO, subjectName + " link is redirecting to Details Page", YesNo.No);

					if (click(driver, taskBP.downArrowButton(20), "downArrowButton", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

						if (click(driver, taskBP.buttonInTheDownArrowList("Edit", 20), "Edit Button in downArrowButton",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Edit Button in  Down Arrow Button", YesNo.No);

							flag = true;
						} else {
							log(LogStatus.ERROR, "Not Able Click on Edit button in Down Arrow Button", YesNo.Yes);
							driver.close();
							driver.switchTo().window(windowID);
						}

					} else {
						log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
						driver.close();
						driver.switchTo().window(windowID);
					}

				} else {
					log(LogStatus.ERROR,
							subjectName
									+ " links is not redirecting to Details Page, So Not able to Click on Edit Button",
							YesNo.No);
					driver.close();
					driver.switchTo().window(windowID);
				}
			} else {
				log(LogStatus.ERROR, subjectName + " url did not open in new tab", YesNo.No);
			}
		} else {
			log(LogStatus.ERROR, "not able to click on " + subjectName, YesNo.No);
		}
		return flag;
	}

	public ArrayList<String> verifyNotesPopupWithPrefilledValueAndOnSameUrl(String url,
			String[][] basicSectionVerificationData, String[][] advancedSectionVerificationData,
			String[][] tasksSectionVerificationData) {
		String xPath;
		WebElement ele;
		ArrayList<String> result = new ArrayList<String>();

		String currentUrl = getURL(driver, 10);
		ThreadSleep(4000);

		if (url.equals(currentUrl)) {
			log(LogStatus.INFO, "popup is open in the same page", YesNo.No);

			String detail = CommonLib.getAttribute(driver, getNotePopUpSectionDetail("Tasks", 30), "Tasks Section",
					"aria-hidden");

			if (detail.equals("true")) {

			} else {

				clickUsingJavaScript(driver, getSectionBtn("Tasks", 30), "Tasks section", action.SCROLLANDBOOLEAN);
			}

			String detail2 = CommonLib.getAttribute(driver, getNotePopUpSectionDetail("Advanced", 30),
					"Advanced Section", "aria-hidden");

			if (detail2.equals("true")) {

			} else {

				clickUsingJavaScript(driver, getSectionBtn("Advanced", 30), "Advanced section",
						action.SCROLLANDBOOLEAN);
			}
			if (basicSectionVerificationData != null) {

				for (String[] val : basicSectionVerificationData) {
					String labelName = val[0];
					String value = val[1];

					if (labelName.contains(excelLabel.Subject.toString())) {

						String actualSubject = getAttribute(driver, getSubjectInput(labelName, 15), labelName, "value");

						log(LogStatus.INFO, "Successfully get the value from Subject field", YesNo.No);
						if (value.equals(actualSubject)) {
							log(LogStatus.INFO, "Subject value has been verify and i.e. :" + value, YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"Subject value is not verify, Expected: " + value + " but Actual: " + actualSubject,
									YesNo.No);
							result.add("Subject value is not verify, Expected: " + value + " but Actual: "
									+ actualSubject);
						}
					}

					else if (labelName.contains(excelLabel.Notes.toString())) {
						String actualNotes = getText(driver, getNotesText(20), "Notes", action.SCROLLANDBOOLEAN);
						if (actualNotes.equals(value.trim().replaceAll(" +", " "))) {
							log(LogStatus.INFO, "Notes value has been verified and i.e. :" + value, YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"Notes value is not verified, Expected: " + value + " but Actual: " + actualNotes,
									YesNo.No);
							result.add(
									"Notes value is not verified, Expected: " + value + " but Actual: " + actualNotes);
						}
					}

					else if (labelName.equalsIgnoreCase(excelLabel.Related_To.toString())) {

						if (!value.contains("==")) {

							String[] tag = value.split("<break>", -1);
							List<String> taggedRelatedToListInNotePopUp = taggedRelatedToInNotePopUp().stream()
									.map(x -> x.getText()).collect(Collectors.toList());
							for (int i = 0; i < tag.length; i++) {

								if (taggedRelatedToListInNotePopUp.contains(tag[i])) {
									log(LogStatus.INFO, tag[i] + " tag has been verified in Note PopUp ", YesNo.No);
								} else {
									log(LogStatus.ERROR, tag[i] + " tag has not verified in Note PopUp", YesNo.No);
									result.add(tag[i] + " tag has not verified in Note PopUp");
								}
							}
						} else {

							String[] tag = value.split("<break>", -1);

							for (int i = 0; i < tag.length; i++) {
								String[] relatedAndIcon = tag[i].split("==", -1);
								String relatedAssociation = relatedAndIcon[0];
								String relatedAssociationIcon = relatedAndIcon[1];

								if (relatedAssocitionWithIcon(relatedAssociation, relatedAssociationIcon, 4) != null) {
									log(LogStatus.INFO,
											relatedAssociation + " tag has been verified in Note PopUp with Icon: "
													+ relatedAssociationIcon,
											YesNo.No);
								} else {
									log(LogStatus.ERROR,
											relatedAssociation + " tag has not verified in Note PopUp with Icon: "
													+ relatedAssociationIcon,
											YesNo.No);
									result.add(relatedAssociation + " tag has not verified in Note PopUp with Icon: "
											+ relatedAssociationIcon);
								}
							}

						}

					} else {
						log(LogStatus.ERROR, "Please Provide the Correct Label Name: " + labelName, YesNo.No);
						result.add("Please Provide the Correct Label Name: " + labelName);
					}
				}
			}

			if (advancedSectionVerificationData != null) {

				if (clickUsingJavaScript(driver, getSectionBtn("Advanced", 30), "Advanced section",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on Advanced section", YesNo.No);

					for (String[] val : advancedSectionVerificationData) {
						String labelName = val[0];
						String value = val[1];

						if (labelName.contains("User")) {

							String actualAssignedToId = getText(driver, assignedToVerificationInAdvance(labelName, 10),
									"User", action.SCROLLANDBOOLEAN);

							log(LogStatus.INFO, "Successfully get the value from Assigned To field", YesNo.No);
							if (value.equals(actualAssignedToId)) {
								log(LogStatus.INFO, "Assigned To value has been verify and i.e. :" + value, YesNo.No);
							} else {
								log(LogStatus.ERROR, "Assigned To value is not verify, Expected: " + value
										+ " but Actual: " + actualAssignedToId, YesNo.No);
								result.add("Assigned To value is not verify, Expected: " + value + " but Actual: "
										+ actualAssignedToId);
							}
						}

						else if (labelName.contains(excelLabel.Status.toString())) {
							String actualStatus = getText(driver, statusVerificationInAdvanced(labelName, 10),
									excelLabel.Status.toString(), action.SCROLLANDBOOLEAN);
							if (actualStatus.contains(value)) {
								log(LogStatus.INFO, "Status value has been verified and i.e. :" + value, YesNo.No);
							} else {
								log(LogStatus.ERROR, "STatus value is not verified, Expected: " + value
										+ " but Actual: " + actualStatus, YesNo.No);
								result.add("Status value is not verified, Expected: " + value + " but Actual: "
										+ actualStatus);
							}
						}

						else if (labelName.contains("Due Date") || labelName.contains("Date")) {

							String actualDueDateOnly = getAttribute(driver,
									dueDateOnlyVerificationInAdvanced(labelName, 10), "Due Date", "value");
							String tempvalue = CommonLib.convertDateFromOneFormatToAnother(value, "M/d/yyyy",
									"MMM d, yyyy");
							if (tempvalue != null) {
								value = tempvalue;
							}

							if (value.equals(actualDueDateOnly)) {
								log(LogStatus.INFO, "Due Date value has been verified  and i.e. :" + value, YesNo.No);
							} else {
								log(LogStatus.ERROR, "Due Date value is not verified, Expected: " + value
										+ " but Actual: " + actualDueDateOnly, YesNo.No);
								result.add("Due Date value is not verified, Expected: " + value + " but Actual: "
										+ actualDueDateOnly);
							}
						}

						else if (labelName.contains(excelLabel.Priority.toString())) {

							String actualPriority = getText(driver, priorityVerificationInAdvanced(labelName, 10),
									excelLabel.Priority.toString(), action.SCROLLANDBOOLEAN);

							if (value.contains(actualPriority)) {
								log(LogStatus.INFO, "Priority value has been verified and i.e. :" + value, YesNo.No);
							} else {
								log(LogStatus.ERROR, "Priority value is not verified, Expected: " + value
										+ " but Actual: " + actualPriority, YesNo.No);
								result.add("Priority value is not verified, Expected: " + value + " but Actual: "
										+ actualPriority);
							}
						}

						else if (labelName.contains("Start Date Time") || labelName.contains("End Date Time")) {

							String labels[] = labelName.split("<break>", -1);
							if (labels.length == 2) {
								String actualDataOnly = getAttribute(driver,
										eventNotPopUpAdvanceSectionDateTime(labels[0], labels[1], 10),
										labels.toString(), "value");
								String tempvalue = CommonLib.convertDateFromOneFormatToAnother(value, "M/d/yyyy",
										"MMM d, yyyy");
								if (tempvalue != null) {
									value = tempvalue;
								}

								if (actualDataOnly.contains(value)) {
									log(LogStatus.INFO,
											labels.toString() + " value has been verified  and i.e. :" + value,
											YesNo.No);
								} else {
									log(LogStatus.ERROR, labels.toString() + " value is not verified, Expected: "
											+ value + " but Actual: " + actualDataOnly, YesNo.No);
									result.add(labels.toString() + " value is not verified, Expected: " + value
											+ " but Actual: " + actualDataOnly);
								}

							} else {
								log(LogStatus.ERROR, "Date is not correctely provided, i.e.: " + labelName
										+ " , it should be, for eg.: ParentLabel<break>ChildLabel", YesNo.No);
								result.add("Date is not correctely provided, i.e.: " + labelName
										+ " , it should be, for eg.: ParentLabel<break>ChildLabel");
							}

						}

						else if (labelName.contains(excelLabel.Location.toString())) {

							String actualLocation = getAttribute(driver,
									dueDateOnlyVerificationInAdvanced(labelName, 10), labelName, "value");

							if (value.contains(actualLocation)) {
								log(LogStatus.INFO, labelName + " value has been verified and i.e. :" + value,
										YesNo.No);
							} else {
								log(LogStatus.ERROR, labelName + " value is not verified, Expected: " + value
										+ " but Actual: " + actualLocation, YesNo.No);
								result.add(labelName + " value is not verified, Expected: " + value + " but Actual: "
										+ actualLocation);
							}
						}

						else {
							log(LogStatus.ERROR, "Please Provide the Correct Label Name: " + labelName, YesNo.No);
							result.add("Please Provide the Correct Label Name: " + labelName);
						}
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Advanced search section", YesNo.No);

					result.add("Not able to click on Advanced search section");

				}
			}

			if (tasksSectionVerificationData != null) {

				xPath = "//span[text()='Advanced']/parent::button[@aria-expanded='true']";
				ele = FindElement(driver, xPath, "Advance section", action.SCROLLANDBOOLEAN, 3);
				if (ele == null) {
					if (clickUsingJavaScript(driver, getSectionBtn("Advanced", 3), "Advanced section",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on Advanced section ", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to click on Advanced section", YesNo.No);

						result.add("Not able to click on Advanced section");

					}
				}

				if (clickUsingJavaScript(driver, getSectionBtn("Tasks", 30), "Tasks section",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on Tasks section", YesNo.No);

					for (String[] val : tasksSectionVerificationData) {
						String labelName = val[0];
						String value = val[1];

						if (labelName.contains(excelLabel.Subject.toString())) {

							String actualSubject = getAttribute(driver, subjectVerificationInTasks(labelName, 10),
									"Subject", "value");

							log(LogStatus.INFO, "Successfully get the value from Subject field", YesNo.No);
							if (value.equals(actualSubject)) {
								log(LogStatus.INFO, "Subject value has been verify and i.e. :" + value, YesNo.No);
							} else {
								log(LogStatus.ERROR, "Subject value is not verify, Expected: " + value + " but Actual: "
										+ actualSubject, YesNo.No);
								result.add("Subject value is not verify, Expected: " + value + " but Actual: "
										+ actualSubject);
							}
						}

						else if (labelName.contains("User")) {

							String actualAssignedToId = getText(driver, assignedToVerificationInTasks(labelName, 10),
									"User", action.SCROLLANDBOOLEAN);

							log(LogStatus.INFO, "Successfully get the value from Assigned To field", YesNo.No);
							if (value.equals(actualAssignedToId)) {
								log(LogStatus.INFO, "Assigned To value has been verify and i.e. :" + value, YesNo.No);
							} else {
								log(LogStatus.ERROR, "Assigned To value is not verify, Expected: " + value
										+ " but Actual: " + actualAssignedToId, YesNo.No);
								result.add("Assigned To value is not verify, Expected: " + value + " but Actual: "
										+ actualAssignedToId);
							}
						} else if (labelName.contains(excelLabel.Status.toString())) {
							String actualStatus = getText(driver, statusVerificationInTasks(labelName, 10),
									excelLabel.Status.toString(), action.SCROLLANDBOOLEAN);
							if (actualStatus.contains(value)) {
								log(LogStatus.INFO, "Status value has been verified", YesNo.No);
							} else {
								log(LogStatus.ERROR, "Status value is not verified, Expected: " + value
										+ " but Actual: " + actualStatus, YesNo.No);
								result.add("Status value is not verified, Expected: " + value + " but Actual: "
										+ actualStatus);
							}
						}

						else if (labelName.contains("Due Date") || labelName.contains("Date")) {

							String actualDueDateOnly = getAttribute(driver,
									dueDateOnlyVerificationInTasks(labelName, 10), "Due Date", "value");
							String tempvalue = CommonLib.convertDateFromOneFormatToAnother(value, "M/d/yyyy",
									"MMM d, yyyy");

							if (tempvalue != null) {
								value = tempvalue;
							}

							if (value.equals(actualDueDateOnly)) {
								log(LogStatus.INFO, "Due Date value has been verified and i.e. :" + value, YesNo.No);
							} else {
								log(LogStatus.ERROR, "Due Date value is not verified, Expected: " + value
										+ " but Actual: " + actualDueDateOnly, YesNo.No);
								result.add("Due Date value is not verified, Expected: " + value + " but Actual: "
										+ actualDueDateOnly);
							}
						}

						else {
							log(LogStatus.ERROR, "Please Provide the Correct Label Name: " + labelName, YesNo.No);
							result.add("Please Provide the Correct Label Name: " + labelName);
						}
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Tasks section", YesNo.No);
					result.add("Not able to click on Tasks section");

				}
			}

		} else {
			log(LogStatus.ERROR, "Popup is not open on the same page", YesNo.No);
			result.add(" Popup is not open on the same page");
		}

//		click(driver, crossIconButtonInNotePopUp(20), "Clicked on Cross Icon of PopUp", action.SCROLLANDBOOLEAN);

		return result;
	}

	public ArrayList<String> verifyRedirectionOnClickRecordAndReferencedTypeOnTagged(String[] firmTagName,
			String[] firmTimesReferenced, String[] peopleTagName, String[] peopleTimesReferenced, String[] dealTagName,
			String[] dealTimesReferenced, boolean isInstitutionRecordType) {
		String xPath;
		WebElement ele;
		ArrayList<String> result = new ArrayList<String>();
		if (firmTagName != null && firmTimesReferenced != null) {
			if (firmTagName.length == firmTimesReferenced.length) {
				if (click(driver, getTaggedRecordName("Firms", 30), "Firms tab", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Firms tab name", YesNo.No);

					for (int i = 0; i < firmTagName.length; i++) {
						ThreadSleep(5000);

						if (CommonLib.clickUsingJavaScript(driver, getTaggedRecordName("Firms", firmTagName[i], 30),
								firmTagName[i] + " on Company Tagged", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on " + firmTagName[i] + " record on Firms tab", YesNo.No);

							String id = switchOnWindow(driver);
							xPath = "//lightning-formatted-text[text()='" + firmTagName[i] + "']";
							ele = FindElement(driver, xPath, firmTagName[i] + " record", action.SCROLLANDBOOLEAN, 40);
							if (ele != null) {
								log(LogStatus.INFO, firmTagName[i] + " record is redirecting to new tab", YesNo.No);
							} else {
								log(LogStatus.ERROR, firmTagName[i] + " is not redirecting to new tab", YesNo.No);
								result.add(firmTagName[i] + " is not redirecting to new tab");
							}
							driver.close();
							driver.switchTo().window(id);

						} else {
							log(LogStatus.ERROR, "Not able to click on " + firmTagName[i] + " record on Firms tab",
									YesNo.No);
							result.add("Not able to click on " + firmTagName[i] + " record on Firms tab");
						}

						if (click(driver,
								getTaggedRecordTimeReference("Firms", firmTagName[i], firmTimesReferenced[i], 30),
								firmTagName[i] + " on Company Tagged", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of " + firmTagName[i], YesNo.No);

							String id = switchOnWindow(driver);
							xPath = "//span[@class='slds-page-header__title slds-truncate']";
							ele = FindElement(driver, xPath, firmTagName[i] + " record's count",
									action.SCROLLANDBOOLEAN, 40);
							if (ele != null) {
								log(LogStatus.INFO, firmTagName[i] + " time reference count is redirecting to new tab",
										YesNo.No);
							} else {
								log(LogStatus.ERROR,
										firmTagName[i] + " time reference count is not redirecting to new tab",
										YesNo.No);
								result.add(firmTagName[i] + " time reference count is not redirecting to new tab");
							}
							driver.close();
							driver.switchTo().window(id);

						} else {
							log(LogStatus.ERROR, "Not able to click on Time reference count of " + firmTagName[i],
									YesNo.No);
							result.add("Not able to click on Time reference count of " + firmTagName[i]);
						}
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Firms tab name", YesNo.No);
					result.add("Not able to click on Firms tab name");
				}
			} else {
				log(LogStatus.ERROR, "The size of tagged company name and size of tagged time reference is not equal",
						YesNo.No);
				result.add("The size of tagged company name and size of tagged time reference is not equal");
			}
		}

		if (peopleTagName != null && peopleTimesReferenced != null) {
			if (peopleTagName.length == peopleTimesReferenced.length) {
				if (click(driver, getTaggedRecordName("People", 30), "People tab", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on People tab name", YesNo.No);

					for (int i = 0; i < peopleTagName.length; i++) {
						ThreadSleep(5000);
						if (clickUsingJavaScript(driver, getTaggedRecordName("People", peopleTagName[i], 30),
								peopleTagName[i] + " on People Tagged", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on " + peopleTagName[i] + " record on People tab", YesNo.No);

							String id = switchOnWindow(driver);
							xPath = "//span[text()=\"" + peopleTagName[i] + "\" and contains(@class,\"uiOutputText\")]";
							ele = FindElement(driver, xPath, peopleTagName[i] + " record", action.SCROLLANDBOOLEAN, 40);
							if (ele != null) {
								log(LogStatus.INFO, peopleTagName[i] + " record is redirecting to new tab", YesNo.No);
							} else {
								log(LogStatus.ERROR, peopleTagName[i] + " is not redirecting to new tab", YesNo.No);
								result.add(peopleTagName[i] + " is not redirecting to new tab");
							}
							driver.close();
							driver.switchTo().window(id);

						} else {
							log(LogStatus.ERROR, "Not able to click on " + peopleTagName[i] + " record on People tab",
									YesNo.No);
							result.add("Not able to click on " + peopleTagName[i] + " record on People tab");
						}

						if (click(driver,
								getTaggedRecordTimeReference("People", peopleTagName[i], peopleTimesReferenced[i], 30),
								peopleTagName[i] + " on People Tagged", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of " + peopleTagName[i], YesNo.No);

							String id = switchOnWindow(driver);
							xPath = "//span[@class='slds-page-header__title slds-truncate']";
							ele = FindElement(driver, xPath, firmTagName[i] + " record's count",
									action.SCROLLANDBOOLEAN, 40);
							if (ele != null) {
								log(LogStatus.INFO, firmTagName[i] + " time reference count is redirecting to new tab",
										YesNo.No);
							} else {
								log(LogStatus.ERROR,
										firmTagName[i] + " time reference count is not redirecting to new tab",
										YesNo.No);
								result.add(firmTagName[i] + " time reference count is not redirecting to new tab");
							}
							driver.close();
							driver.switchTo().window(id);
						} else {
							log(LogStatus.ERROR, "Not able to click on Time reference count of " + peopleTagName[i],
									YesNo.No);
							result.add("Not able to click on Time reference count of " + peopleTagName[i]);
						}
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on People tab name", YesNo.No);
					result.add("Not able to click on People tab name");
				}

			} else {
				log(LogStatus.ERROR, "The size of tagged people name and size of tagged time reference is not equal",
						YesNo.No);
				result.add("The size of tagged people name and size of tagged time reference is not equal");
			}
		}
		if (isInstitutionRecordType == false) {
			if (dealTagName != null && dealTimesReferenced != null) {
				if (dealTagName.length == dealTimesReferenced.length) {
					if (click(driver, getTaggedRecordName("Deals", 30), "Deals tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Deals tab name", YesNo.No);

						for (int i = 0; i < dealTagName.length; i++) {
							ThreadSleep(5000);
							if (clickUsingJavaScript(driver, getTaggedRecordName("Deals", dealTagName[i], 30),
									dealTagName[i] + " on Deals Tagged", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on " + dealTagName[i] + " record on Deals tab", YesNo.No);

								String id = switchOnWindow(driver);
								xPath = "//lightning-formatted-text[text()='" + dealTagName[i] + "']";
								ele = FindElement(driver, xPath, dealTagName[i] + " record", action.SCROLLANDBOOLEAN,
										40);
								if (ele != null) {
									log(LogStatus.INFO, dealTagName[i] + " record is redirecting to new tab", YesNo.No);
								} else {
									log(LogStatus.ERROR, dealTagName[i] + " is not redirecting to new tab", YesNo.No);
									result.add(dealTagName[i] + " is not redirecting to new tab");
								}
								driver.close();
								driver.switchTo().window(id);

							} else {
								log(LogStatus.ERROR, "Not able to click on " + dealTagName[i] + " record on Deals tab",
										YesNo.No);
								result.add("Not able to click on " + dealTagName[i] + " record on Deals tab");
							}

							if (click(driver,
									getTaggedRecordTimeReference("Deals", dealTagName[i], dealTimesReferenced[i], 30),
									dealTagName[i] + " on Deals Tagged", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Time reference count of " + dealTagName[i], YesNo.No);

								String id = switchOnWindow(driver);
								xPath = "//span[@class='slds-page-header__title slds-truncate']";
								ele = FindElement(driver, xPath, firmTagName[i] + " record's count",
										action.SCROLLANDBOOLEAN, 40);
								if (ele != null) {
									log(LogStatus.INFO,
											firmTagName[i] + " time reference count is redirecting to new tab",
											YesNo.No);
								} else {
									log(LogStatus.ERROR,
											firmTagName[i] + " time reference count is not redirecting to new tab",
											YesNo.No);
									result.add(firmTagName[i] + " time reference count is not redirecting to new tab");
								}
								driver.close();
								driver.switchTo().window(id);
							} else {
								log(LogStatus.ERROR, "Not able to click on Time reference count of " + dealTagName[i],
										YesNo.No);
								result.add("Not able to click on Time reference count of " + dealTagName[i]);
							}
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Deals tab name", YesNo.No);
						result.add("Not able to click on Deals tab name");
					}
				}
			}
		}
		return result;
	}

	public ArrayList<String> verifyRedirectionOnClickOfTaggedRecord(String subjectName, String[] relatedAssociation,
			String[] relatedAssociationOnTagged) {
		String xPath;
		WebElement ele;
		ArrayList<String> result = new ArrayList<String>();
		if (subjectName != null && subjectName != "") {
			if (relatedAssociation != null && relatedAssociation.length != 0) {
				for (int i = 0; i < relatedAssociation.length; i++) {
					xPath = "//a[text()='" + subjectName + "']/../..//span[@class=\"slds-pill__label\" and text()='"
							+ relatedAssociation[i] + "']";
					ele = FindElement(driver, xPath, relatedAssociation[i] + " related association",
							action.SCROLLANDBOOLEAN, 20);
					if (click(driver, ele, relatedAssociation[i] + " related association", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on " + relatedAssociation[i] + " related association", YesNo.No);
						String id = switchOnWindow(driver);
						xPath = "//*[(contains(@class,'custom-truncate') or @slot='primaryField') and text()='"
								+ relatedAssociation[i] + "']";
						ele = FindElement(driver, xPath, relatedAssociation[i] + " record", action.SCROLLANDBOOLEAN,
								25);
						if (ele != null) {
							log(LogStatus.INFO, relatedAssociation[i] + " record is redirecting to new tab", YesNo.No);
						} else {
							log(LogStatus.ERROR, relatedAssociation[i] + " record is not redirecting to new tab",
									YesNo.No);
							result.add(relatedAssociation[i] + " record is not redirecting to new tab");
						}
						driver.close();
						driver.switchTo().window(id);
					} else {
						log(LogStatus.ERROR, "Not able to click on " + relatedAssociation[i] + " related association",
								YesNo.No);
						result.add("Not able to click on " + relatedAssociation[i] + " related association");
					}
				}
			}
			if (relatedAssociationOnTagged != null && relatedAssociationOnTagged.length != 0) {
				for (int i = 0; i < relatedAssociationOnTagged.length; i++) {
					xPath = "//a[text()='" + subjectName
							+ "']/../..//span[@class=\"slds-pill__label\" and starts-with(text(),'+')]";
					ele = FindElement(driver, xPath, " related association + ", action.SCROLLANDBOOLEAN, 20);
					if (click(driver, ele, "Tagged related association", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on tagged related association", YesNo.No);
						ThreadSleep(2000);
						xPath = "//h2[text()='Tagged']/../following-sibling::div//a[text()='"
								+ relatedAssociationOnTagged[i] + "']";
						ele = FindElement(driver, xPath, relatedAssociationOnTagged[i] + " related association",
								action.SCROLLANDBOOLEAN, 20);
						ThreadSleep(2000);
						if (CommonLib.click(driver, ele, relatedAssociationOnTagged[i] + " related association",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on " + relatedAssociationOnTagged[i] + " related association",
									YesNo.No);
							String id = switchOnWindow(driver);
							xPath = "//*[(contains(@class,'custom-truncate') or @slot='primaryField') and text()='"
									+ relatedAssociationOnTagged[i] + "']";
							ele = FindElement(driver, xPath, relatedAssociationOnTagged[i] + " record",
									action.SCROLLANDBOOLEAN, 25);
							if (ele != null) {
								log(LogStatus.INFO, relatedAssociationOnTagged[i] + " record is redirecting to new tab",
										YesNo.No);
							} else {
								log(LogStatus.ERROR,
										relatedAssociationOnTagged[i] + " record is not redirecting to new tab",
										YesNo.No);
								result.add(relatedAssociationOnTagged[i] + " record is not redirecting to new tab");
							}
							driver.close();
							driver.switchTo().window(id);
							xPath = "//h2[text()='Tagged']/../button[@title='Close']";
							ele = FindElement(driver, xPath, "close button", action.SCROLLANDBOOLEAN, 20);
							if (click(driver, ele, "close button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on close button of tagged", YesNo.No);
							} else {
								log(LogStatus.ERROR, "Not able to click oo close button of tagged", YesNo.No);
								result.add("Not able to click oo close button of tagged");
							}
						} else {
							log(LogStatus.ERROR,
									"Not able to click on " + relatedAssociationOnTagged[i] + " related association",
									YesNo.No);
							result.add(
									"Not able to click on " + relatedAssociationOnTagged[i] + " related association");
						}
					} else {
						log(LogStatus.ERROR, "Not able to clicked on tagged related association", YesNo.No);
						result.add("Not able to clicked on tagged related association");
					}
				}
			}
		} else {
			log(LogStatus.ERROR, "Subject name is null or blank, Please provide subject name", YesNo.No);
			result.add("Subject name is null or blank, Please provide subject name");
		}

		return result;
	}

	public ArrayList<String> verifyUIOfMeetingAndCallsPopup(ArrayList<String> headerName, String message) {
		String xPath;
		WebElement ele;
		List<WebElement> elements;
		String parentID = switchOnWindow(driver);
		ArrayList<String> result = new ArrayList<String>();
		if (!headerName.isEmpty()) {
			ArrayList<String> actualHeaderName = new ArrayList<String>();
			xPath = "//table[contains(@class,'slds-table_header-fixed')]//span[@class='slds-truncate' and @title!='']";
			elements = FindElements(driver, xPath, "Meetings and Calls popup");
			for (WebElement el : elements) {
				actualHeaderName.add(getText(driver, el, "Meetings and Calls popup headers", action.SCROLLANDBOOLEAN));
			}

			for (int i = 0; i < headerName.size(); i++) {
				int k = 0;
				for (int j = 0; j < actualHeaderName.size(); j++) {
					if (headerName.get(i).equalsIgnoreCase(actualHeaderName.get(j))) {
						log(LogStatus.INFO,
								"Expected header name : " + headerName.get(i)
										+ " has been matched with Actual header name : " + actualHeaderName.get(j),
								YesNo.No);
						k++;
					}

				}
				if (k == 0) {
					log(LogStatus.ERROR,
							"Expected header name : " + headerName.get(i) + " is not matched with Actual header name",
							YesNo.No);
					result.add(
							"Expected header name : " + headerName.get(i) + " is not matched with Actual header name");

				}
			}
		}
		if (message != null && !"".equals(message)) {
			xPath = "//p[contains(@class,'nodata-popup') and text()='" + message + "']";
			ele = FindElement(driver, xPath, "Message on Meetings and Calls popup", action.SCROLLANDBOOLEAN, 15);
			if (ele != null) {
				log(LogStatus.INFO, "The meessage : " + message + " has been verified on Meetings and Calls popup",
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "The meessage : " + message + " is not verified on Meetings and Calls popup",
						YesNo.No);
				result.add("The meessage : " + message + " is not verified on Meetings and Calls popup");
			}
		}
		driver.close();
		driver.switchTo().window(parentID);
		return result;

	}

	public boolean createRecordForCustomObject(String projectName, String tabName, String textBoxRecordLabel,
			String textBoxRecordName) {
		boolean flag = false;

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		if (lp.clickOnTab(projectName, tabName)) {
			log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);

			CommonLib.ThreadSleep(4000);
			if (clickUsingJavaScript(driver, testCustomObjectNewButton(20), "New Button ", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on New Button on " + tabName + " Form Page", YesNo.No);

				ThreadSleep(3000);
				WebElement ele = testCustomObjectTextBoxInput(textBoxRecordLabel, 20);
				if (ele != null) {
					if (sendKeys(driver, ele, textBoxRecordName, textBoxRecordLabel, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "successfully entered " + textBoxRecordName + " in " + textBoxRecordLabel,
								YesNo.Yes);

						if (click(driver, testCustomObjectFooterButton("Save", 10), "Save Button ",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Save Button on " + tabName + " Form Page", YesNo.No);
							if (getCreatedConfirmationMsg(projectName, 20) != null) {
								log(LogStatus.INFO, "Success Msg of Record Create showing for: " + textBoxRecordName,
										YesNo.No);
								flag = true;
							} else {

								log(LogStatus.ERROR,
										"Success Msg of Record Create not showing for: " + textBoxRecordName,
										YesNo.Yes);
								sa.assertTrue(false,
										"Success Msg of Record Create not showing for: " + textBoxRecordName);
							}

						}

						else {
							log(LogStatus.ERROR, "Not Able to Click on Save Button on " + tabName + " Form Page",
									YesNo.Yes);
							sa.assertTrue(false, "Not Able to Click on Save Button on " + tabName + " Form Page");
							click(driver, testCustomObjectFooterButton("Cancel", 10), "Save Button ",
									action.SCROLLANDBOOLEAN);

						}

					} else {
						log(LogStatus.SKIP, "could not enter " + textBoxRecordName + " in " + textBoxRecordLabel,
								YesNo.Yes);
						sa.assertTrue(false, "could not enter " + textBoxRecordName + " in " + textBoxRecordLabel);

					}

				} else {
					log(LogStatus.ERROR, "Input box of Label not Found: " + textBoxRecordLabel, YesNo.No);
					sa.assertTrue(false, "Input box of Label not Found: " + textBoxRecordLabel);
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on New Button on " + tabName + " Form Page", YesNo.Yes);
				sa.assertTrue(false, "Not Able to Click on New Button on " + tabName + " Form Page");
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabName + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + tabName + " Tab");
		}

		return flag;

	}

	public boolean createTasksWithVerificationOfFollowUpTaskSubjectNameAfterClickThenAgainUpdateTaskNameandVerifyFollowUpTaskSubjectName(
			String projectName, boolean fromNavigation, String buttonName, String[][] basicSection,
			String[][] advanceSection, String[] suggestedTags, String[][] tasksSectionVerificationData,
			String updatedSubject) {
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);

		String xPath = "";
		WebElement ele;
		boolean flag = false;

		if (fromNavigation == true) {
			if (npbl.createNavPopUpMinimizeButton(5) != null) {
				CommonLib.click(driver, npbl.createNavPopUpMinimizeButton(5), "Minimize", action.BOOLEAN);
			}
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, NavigationMenuItems.Create.toString(), action.BOOLEAN,
					30)) {
				log(LogStatus.INFO, "Able to Click on " + buttonName + " Going to click on : "
						+ NavigationMenuItems.Create.toString() + " for creation ", YesNo.No);
				ele = npbl.getNavigationLabel(projectName, buttonName, action.BOOLEAN, 10);
				if (ele != null && CommonLib.click(driver, ele, buttonName, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on " + buttonName + " so going for creation", YesNo.No);

				} else {
					log(LogStatus.ERROR,
							"Not Able to Click on " + buttonName + " so cannot create data related to this ",
							YesNo.Yes);
					sa.assertTrue(false,
							"Not Able to Click on " + buttonName + " so cannot create data related to this ");
					return false;
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on " + NavigationMenuItems.Create.toString()
						+ " so cannot click on : " + buttonName + " for creation ", YesNo.Yes);
				sa.assertTrue(false, "Not Able to Click on " + NavigationMenuItems.Create.toString()
						+ " so cannot click on : " + buttonName + " for creation ");
				return false;
			}
		} else {
			if (CommonLib.clickUsingJavaScript(driver, activityTimelineButton(buttonName, 30), buttonName + " button",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on " + buttonName + " button name", YesNo.No);
			} else {
				log(LogStatus.ERROR, "Not able to click on " + buttonName, YesNo.No);
				sa.assertTrue(false, "Not able to click on " + buttonName);
				return false;
			}
		}
		CommonLib.ThreadSleep(2000);

		String detail = CommonLib.getAttribute(driver, getNotePopUpSectionDetail("Tasks", 30), "Tasks Section",
				"aria-hidden");

		if (detail.equals("true")) {

		} else {

			clickUsingJavaScript(driver, getSectionBtn("Tasks", 30), "Tasks section", action.SCROLLANDBOOLEAN);
		}

		String detail2 = CommonLib.getAttribute(driver, getNotePopUpSectionDetail("Advanced", 30), "Advanced Section",
				"aria-hidden");

		if (detail2.equals("true")) {

		} else {

			clickUsingJavaScript(driver, getSectionBtn("Advanced", 30), "Advanced section", action.SCROLLANDBOOLEAN);
		}
		if (basicSection != null) {

			for (String[] val : basicSection) {
				String labelName = val[0];
				String value = val[1];

				if (labelName.contains(excelLabel.Subject.toString())) {
					xPath = "//label[text()='" + labelName + "']/..//input[contains(@data-id,'combobox')]";
					ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
					if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
					} else {
						log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
						sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
						return false;

					}
				} else if (labelName.contains(excelLabel.Notes.toString())) {
					xPath = "//div[label[text()='Notes']]//textarea";
					ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
					if (CommonLib.clickUsingJavaScript(driver, ele, labelName + " paragraph")) {
						log(LogStatus.INFO, "Clicked on " + labelName + " paragraph", YesNo.No);
						ThreadSleep(2000);

						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (sendKeys(driver, ele, value, labelName + " paragraph", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " has been passed on " + labelName + " paragraph", YesNo.No);

							CommonLib.ThreadSleep(2000);
						} else {
							log(LogStatus.ERROR, value + " is not passed on " + labelName + " paragraph", YesNo.No);
							sa.assertTrue(false, value + " is not passed on " + labelName + " paragraph");
							return false;
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on " + labelName + " paragraph", YesNo.No);
						sa.assertTrue(false, "Not able to click on " + labelName + " paragraph");
						return false;
					}

				} else if (labelName.equalsIgnoreCase(excelLabel.Related_To.toString())) {
					ArrayList<String> tagList = new ArrayList<String>();

					if (value.contains("<break>")) {
						String[] data = value.split("<break>");
						for (int i = 0; i < data.length; i++) {
							tagList.add(data[i]);
						}
					} else {
						tagList.add(value);
					}

					for (int i = 0; i < tagList.size(); i++) {

						if (tagList.get(i).contains("Prefilled")) {
							String val1 = tagList.get(i);
							String[] tgName = tagList.get(i).split("<Prefilled>");
							xPath = "//lightning-pill[@data-id='" + tgName[0] + "']";
							ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN,
									30);
							if (ele != null) {
								log(LogStatus.INFO, tgName[0] + " is prefilled", YesNo.No);
							} else {
								log(LogStatus.ERROR, tgName[0] + " is not prefilled", YesNo.No);
								sa.assertTrue(false, tgName[0] + " is not prefilled");
								return false;
							}

						} else {
							xPath = "//input[@placeholder='Search Firm, People or Deals']";
							ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN,
									2);
							if (ele == null) {
								xPath = "//h2[contains(@class,'header_text')]/../..//*[@title='Tag']";
								ele = CommonLib.FindElement(driver, xPath, labelName + " label",
										action.SCROLLANDBOOLEAN, 30);
								if (CommonLib.clickUsingJavaScript(driver, ele, labelName + " paragraph")) {
									log(LogStatus.INFO, "Clicked on Tag button", YesNo.No);
								} else {
									log(LogStatus.ERROR, "Not able to click on Tag button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Tag button");
									return false;
								}

							}

							xPath = "//input[@placeholder='Search Firm, People or Deals']";
							ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN,
									2);
							if (sendKeys(driver, ele, tagList.get(i), "Tag", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,
										tagList.get(i) + " value has been passed in " + labelName + " field", YesNo.No);
								ThreadSleep(3000);
								xPath = "//ul[@class='drop_ul']//li[text()='" + tagList.get(i) + "']";
								ele = CommonLib.FindElement(driver, xPath, labelName + " label",
										action.SCROLLANDBOOLEAN, 30);
								if (click(driver, ele, tagList + " dropdown", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on " + tagList.get(i) + " value", YesNo.No);
								} else {
									log(LogStatus.ERROR, "Not able to click on " + tagList.get(i) + " value", YesNo.No);
									sa.assertTrue(false, "Not able to click on " + tagList.get(i) + " value");
									return false;
								}

							} else {
								log(LogStatus.ERROR, tagList.get(i) + " value is not passed in " + labelName + " field",
										YesNo.No);
								sa.assertTrue(false,
										tagList.get(i) + " value is not passed in " + labelName + " field");
								return false;
							}
						}
					}
				} else {
					log(LogStatus.ERROR, "Label Name is not correct", YesNo.No);
					sa.assertTrue(false, "Label Name is not correct");
					return false;
				}
			}

		}
		if (advanceSection != null) {
			if (clickUsingJavaScript(driver, getSectionBtn("Advanced", 30), "Advanced section",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on Advanced section", YesNo.No);
				for (String[] val : advanceSection) {
					String labelName = val[0];
					String value = val[1];
					// String fieldType=val[2];

					if (labelName.contains("Start Date")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='Start Date Time']/..//label[text()='Date']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}
					} else if (labelName.contains("Start Time")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='Start Date Time']/..//label[text()='Time']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					} else if (labelName.contains("End Date")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='End Date Time']/..//label[text()='Date']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					} else if (labelName.contains("End Time")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='End Date Time']/..//label[text()='Time']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					}

					else if (labelName.contains(excelLabel.Location.toString()) || labelName.contains("Due Date")
							|| labelName.contains("Date")) {

						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
								+ labelName + "']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					}

					else if (labelName.contains(excelLabel.Status.toString())
							|| labelName.contains(excelLabel.Priority.toString())) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
								+ labelName + "']/..//button";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
								+ labelName + "']/..//span[@class='slds-truncate']";
						if (CommonLib.dropDownHandle(driver, ele, xPath, labelName + " dropdown", value)) {
							log(LogStatus.INFO, value + " value has been selected from " + labelName + " field",
									YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not selected from " + labelName + " field",
									YesNo.No);
							sa.assertTrue(false, value + " value is not selected from " + labelName + " field");
							return false;
						}
					} else if (labelName.equalsIgnoreCase("All-Day Event")) {

						if (value.equals("true")) {
							xPath = "//span[text()='All-Day Event']/../../input";
							ele = FindElement(driver, xPath, labelName + " name", action.SCROLLANDBOOLEAN, 30);
							if (isSelected(driver, ele, labelName + " checkbox")) {
								log(LogStatus.INFO, labelName + " checkbox is already selected", YesNo.No);

							} else {
								if (click(driver, ele, labelName + " checkbox", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, labelName + " checkbox has been selected", YesNo.No);
								} else {
									log(LogStatus.ERROR, labelName + " checkbox is not selected", YesNo.No);
									sa.assertTrue(false, labelName + " checkbox is not selected");
									return false;
								}
							}

						} else {
							xPath = "//span[text()='All-Day Event']/../../input";
							ele = FindElement(driver, xPath, labelName + " name", action.SCROLLANDBOOLEAN, 30);
							if (!isSelected(driver, ele, labelName + " checkbox")) {
								log(LogStatus.INFO, labelName + " checkbox is not selected", YesNo.No);

							} else {
								if (click(driver, ele, labelName + " checkbox", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, labelName + " checkbox has been unselected", YesNo.No);
								} else {
									log(LogStatus.ERROR, labelName + " checkbox is not unselected", YesNo.No);
									sa.assertTrue(false, labelName + " checkbox is not unselected");
									return false;
								}
							}
						}
					} else if (labelName.contains("User")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
								+ labelName + "']/..//button";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (click(driver, ele, labelName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on cross icon of value of " + labelName, YesNo.No);
							xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
									+ labelName + "']/..//input";
							ele = CommonLib.FindElement(driver, xPath, labelName + " input", action.SCROLLANDBOOLEAN,
									30);
							if (sendKeys(driver, ele, value, labelName + " input", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, value + " has been passed in " + labelName, YesNo.No);
								xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
										+ labelName + "']/../..//ul//li[text()='" + value + "']";
								ele = CommonLib.FindElement(driver, xPath, value + " list", action.SCROLLANDBOOLEAN,
										30);
								if (clickUsingJavaScript(driver, ele, value + " list element")) {
									log(LogStatus.INFO, "click on the list element of " + value, YesNo.No);
								} else {
									log(LogStatus.ERROR, "Not able to click on the list element of " + value, YesNo.No);
									sa.assertTrue(false, "Not able to click on the list element of " + value);
									return false;
								}

							} else {
								log(LogStatus.ERROR, value + " is not passed in " + labelName, YesNo.No);
								sa.assertTrue(false, value + " is not passed in " + labelName);
								return false;
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on cross icon of value of " + labelName, YesNo.No);
							sa.assertTrue(false, "Not able to click on cross icon of value of " + labelName);
							return false;
						}
					}

					else {
						log(LogStatus.ERROR, "label name is not correct", YesNo.No);
						sa.assertTrue(false, "label name is not correct");
						return false;
					}
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Advanced search section", YesNo.No);
				sa.assertTrue(false, "Not able to click on Advanced search section");
				return false;
			}
		}

		if (tasksSectionVerificationData != null) {

			xPath = "//span[text()='Advanced']/parent::button[@aria-expanded='true']";
			ele = FindElement(driver, xPath, "Advance section", action.SCROLLANDBOOLEAN, 3);
			if (ele == null) {
				if (clickUsingJavaScript(driver, getSectionBtn("Advanced", 3), "Advanced section",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on Advanced section ", YesNo.No);
				} else {
					log(LogStatus.ERROR, "Not able to click on Advanced section", YesNo.No);

					sa.assertTrue(false, "Not able to click on Advanced section");

				}
			}

			if (clickUsingJavaScript(driver, getSectionBtn("Tasks", 30), "Tasks section", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on Tasks section", YesNo.No);

				for (String[] val : tasksSectionVerificationData) {
					String labelName = val[0];
					String value = val[1];

					if (labelName.equalsIgnoreCase(excelLabel.Subject.toString())) {

						String actualSubject = getAttribute(driver, subjectVerificationInTasks(labelName, 10),
								"Subject", "value");

						log(LogStatus.INFO, "Successfully get the value from Subject field", YesNo.No);
						if (value.equals(actualSubject)) {
							log(LogStatus.INFO, "Subject value has been verify and i.e. :" + value, YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"Subject value is not verify, Expected: " + value + " but Actual: " + actualSubject,
									YesNo.No);
							sa.assertTrue(false, "Subject value is not verify, Expected: " + value + " but Actual: "
									+ actualSubject);
						}
					}

					else if (labelName.contains("User")) {

						String actualAssignedToId = getText(driver, assignedToVerificationInTasks(labelName, 10),
								"User", action.SCROLLANDBOOLEAN);

						log(LogStatus.INFO, "Successfully get the value from Assigned To field", YesNo.No);
						if (value.equals(actualAssignedToId)) {
							log(LogStatus.INFO, "Assigned To value has been verify and i.e. :" + value, YesNo.No);
						} else {
							log(LogStatus.ERROR, "Assigned To value is not verify, Expected: " + value + " but Actual: "
									+ actualAssignedToId, YesNo.No);
							sa.assertTrue(false, "Assigned To value is not verify, Expected: " + value + " but Actual: "
									+ actualAssignedToId);
						}
					} else if (labelName.contains(excelLabel.Status.toString())) {
						String actualStatus = getText(driver, statusVerificationInTasks(labelName, 10),
								excelLabel.Status.toString(), action.SCROLLANDBOOLEAN);
						if (actualStatus.contains(value)) {
							log(LogStatus.INFO, "Status value has been verified", YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"Status value is not verified, Expected: " + value + " but Actual: " + actualStatus,
									YesNo.No);
							sa.assertTrue(false, "Status value is not verified, Expected: " + value + " but Actual: "
									+ actualStatus);
						}
					}

					else if (labelName.contains("Due Date") || labelName.contains("Date")) {

						String actualDueDateOnly = getAttribute(driver, dueDateOnlyVerificationInTasks(labelName, 10),
								"Due Date", "value");
						String tempvalue = CommonLib.convertDateFromOneFormatToAnother(value, "M/d/yyyy",
								"MMM d, yyyy");

						if (tempvalue != null) {
							value = tempvalue;
						}

						if (value.equals(actualDueDateOnly)) {
							log(LogStatus.INFO, "Due Date value has been verified and i.e. :" + value, YesNo.No);
						} else {
							log(LogStatus.ERROR, "Due Date value is not verified, Expected: " + value + " but Actual: "
									+ actualDueDateOnly, YesNo.No);
							sa.assertTrue(false, "Due Date value is not verified, Expected: " + value + " but Actual: "
									+ actualDueDateOnly);
						}
					}
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on Tasks section", YesNo.No);
				sa.assertTrue(false, "Not able to click on Tasks section");

			}
		}

		CommonLib.ThreadSleep(3000);
		String basicSectionxPath = "//label[text()='Subject']/..//input[contains(@data-id,'combobox')]";
		ele = CommonLib.FindElement(driver, basicSectionxPath, "Subject" + " label", action.SCROLLANDBOOLEAN, 30);
		if (CommonLib.sendKeys(driver, ele, updatedSubject, "Subject" + " label", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, updatedSubject + " value has been passed in " + "Subject" + " field", YesNo.No);

			if (tasksSectionVerificationData != null) {

				xPath = "//span[text()='Advanced']/parent::button[@aria-expanded='true']";
				ele = FindElement(driver, xPath, "Advance section", action.SCROLLANDBOOLEAN, 3);
				if (ele == null) {
					if (clickUsingJavaScript(driver, getSectionBtn("Advanced", 3), "Advanced section",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on Advanced section ", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to click on Advanced section", YesNo.No);

						sa.assertTrue(false, "Not able to click on Advanced section");

					}
				}

				for (String[] val : tasksSectionVerificationData) {
					String labelName = val[0];
					String value = val[1];

					if (labelName.equalsIgnoreCase(excelLabel.Subject.toString())) {

						String actualSubject = getAttribute(driver, subjectVerificationInTasks(labelName, 10),
								"Subject", "value");

						log(LogStatus.INFO, "Successfully get the value from Subject field", YesNo.No);
						if (value.equals(actualSubject)) {
							log(LogStatus.INFO, "Subject value has been verify and i.e. :" + value, YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"Subject value is not verify, Expected: " + value + " but Actual: " + actualSubject,
									YesNo.No);
							sa.assertTrue(false, "Subject value is not verify, Expected: " + value + " but Actual: "
									+ actualSubject);
						}
					}

					else if (labelName.contains("User")) {

						String actualAssignedToId = getText(driver, assignedToVerificationInTasks(labelName, 10),
								"User", action.SCROLLANDBOOLEAN);

						log(LogStatus.INFO, "Successfully get the value from Assigned To field", YesNo.No);
						if (value.equals(actualAssignedToId)) {
							log(LogStatus.INFO, "Assigned To value has been verify and i.e. :" + value, YesNo.No);
						} else {
							log(LogStatus.ERROR, "Assigned To value is not verify, Expected: " + value + " but Actual: "
									+ actualAssignedToId, YesNo.No);
							sa.assertTrue(false, "Assigned To value is not verify, Expected: " + value + " but Actual: "
									+ actualAssignedToId);
						}
					} else if (labelName.equalsIgnoreCase(excelLabel.Status.toString())) {
						String actualStatus = getText(driver, statusVerificationInTasks(labelName, 10),
								excelLabel.Status.toString(), action.SCROLLANDBOOLEAN);
						if (value.contains(actualStatus)) {
							log(LogStatus.INFO, "Status value has been verified", YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"Status value is not verified, Expected: " + value + " but Actual: " + actualStatus,
									YesNo.No);
							sa.assertTrue(false, "Status value is not verified, Expected: " + value + " but Actual: "
									+ actualStatus);
						}
					}

					else if (labelName.contains("Due Date") || labelName.contains("Date")) {

						String actualDueDateOnly = getAttribute(driver, dueDateOnlyVerificationInTasks(labelName, 10),
								"Due Date", "value");
						String tempvalue = CommonLib.convertDateFromOneFormatToAnother(value, "M/d/yyyy",
								"MMM d, yyyy");

						if (tempvalue != null) {
							value = tempvalue;
						}

						if (value.equals(actualDueDateOnly)) {
							log(LogStatus.INFO, "Due Date value has been verified and i.e. :" + value, YesNo.No);
						} else {
							log(LogStatus.ERROR, "Due Date value is not verified, Expected: " + value + " but Actual: "
									+ actualDueDateOnly, YesNo.No);
							sa.assertTrue(false, "Due Date value is not verified, Expected: " + value + " but Actual: "
									+ actualDueDateOnly);
						}
					}
				}

			}

		} else {
			log(LogStatus.ERROR, updatedSubject + " value is not passed in " + "Subject" + " field", YesNo.No);
			sa.assertTrue(false, updatedSubject + " value is not passed in " + "Subject" + " field");
			return false;
		}

		if (click(driver, getfooterSaveOrCancelButton("Save", 20), "Save button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "clicked on Save button", YesNo.No);

			if (suggestedTags != null) {

				log(LogStatus.INFO, "Activity timeline record has been created", YesNo.No);
				ThreadSleep(2000);
				for (int i = 0; i < suggestedTags.length; i++) {
					xPath = "//lightning-base-formatted-text[text()='" + suggestedTags[i]
							+ "']/ancestor::th[@data-label='Name']/..//td//input";
					ele = CommonLib.FindElement(driver, xPath, suggestedTags[i] + " sugested Tag",
							action.SCROLLANDBOOLEAN, 30);
					if (click(driver, ele, suggestedTags[i] + " suggested tag", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on " + suggestedTags[i] + " suggested tag checkbox button",
								YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"Not able to click on " + suggestedTags[i] + " suggested tag checkbox button",
								YesNo.No);
						sa.assertTrue(false,
								"Not able to click on " + suggestedTags[i] + " suggested tag checkbox button");
						return false;
					}
				}
				if (click(driver, getfooterTagButton(30), "Tag Button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on footer tag button", YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Not able to click on footer tag button", YesNo.No);
					sa.assertTrue(false, "Not able to click on footer tag button");
					return false;
				}

			}

			else {

				log(LogStatus.INFO, "Activity timeline record has been created", YesNo.No);
				ThreadSleep(2000);
				refresh(driver);
				flag = true;

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Save button", YesNo.No);
			sa.assertTrue(false, "Not able to click on Save button");
			return false;
		}

		return flag;
	}

	public boolean createActivityTimelineForMultipleFollowUpTasks(String projectName, boolean fromNavigation,
			String buttonName, String[][] basicSection, String[][] advanceSection, String[][][] taskSectionMultiple,
			String[] suggestedTags) {
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		String xPath = "";
		WebElement ele;
		boolean flag = false;

		if (fromNavigation == true) {
			if (npbl.createNavPopUpMinimizeButton(5) != null) {
				CommonLib.click(driver, npbl.createNavPopUpMinimizeButton(5), "Minimize", action.BOOLEAN);
			}
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, NavigationMenuItems.Create.toString(), action.BOOLEAN,
					30)) {
				log(LogStatus.INFO, "Able to Click on " + buttonName + " Going to click on : "
						+ NavigationMenuItems.Create.toString() + " for creation ", YesNo.No);
				ele = npbl.getNavigationLabel(projectName, buttonName, action.BOOLEAN, 10);
				if (ele != null && CommonLib.click(driver, ele, buttonName, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on " + buttonName + " so going for creation", YesNo.No);

				} else {
					log(LogStatus.ERROR,
							"Not Able to Click on " + buttonName + " so cannot create data related to this ",
							YesNo.Yes);
					sa.assertTrue(false,
							"Not Able to Click on " + buttonName + " so cannot create data related to this ");
					return false;
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on " + NavigationMenuItems.Create.toString()
						+ " so cannot click on : " + buttonName + " for creation ", YesNo.Yes);
				sa.assertTrue(false, "Not Able to Click on " + NavigationMenuItems.Create.toString()
						+ " so cannot click on : " + buttonName + " for creation ");
				return false;
			}
		} else {
			if (CommonLib.clickUsingJavaScript(driver, activityTimelineButton(buttonName, 30), buttonName + " button",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on " + buttonName + " button name", YesNo.No);
			} else {
				log(LogStatus.ERROR, "Not able to click on " + buttonName, YesNo.No);
				sa.assertTrue(false, "Not able to click on " + buttonName);
				return false;
			}
		}
		String detail = CommonLib.getAttribute(driver, getNotePopUpSectionDetail("Tasks", 30), "Tasks Section",
				"aria-hidden");

		if (detail.equals("true")) {

		} else {

			clickUsingJavaScript(driver, getSectionBtn("Tasks", 30), "Tasks section", action.SCROLLANDBOOLEAN);
		}

		String detail2 = CommonLib.getAttribute(driver, getNotePopUpSectionDetail("Advanced", 30), "Advanced Section",
				"aria-hidden");

		if (detail2.equals("true")) {

		} else {

			clickUsingJavaScript(driver, getSectionBtn("Advanced", 30), "Advanced section", action.SCROLLANDBOOLEAN);
		}
		if (basicSection != null) {

			for (String[] val : basicSection) {
				String labelName = val[0];
				String value = val[1];

				if (labelName.contains(excelLabel.Subject.toString())) {
					xPath = "//label[text()='" + labelName + "']/..//input[contains(@data-id,'combobox')]";
					ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
					if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
					} else {
						log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
						sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
						return false;

					}
				} else if (labelName.contains(excelLabel.Notes.toString())) {
					xPath = "//div[label[text()='Notes']]//textarea";
					ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
					if (CommonLib.clickUsingJavaScript(driver, ele, labelName + " paragraph")) {
						log(LogStatus.INFO, "Clicked on " + labelName + " paragraph", YesNo.No);
						ThreadSleep(2000);

						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (sendKeys(driver, ele, value, labelName + " paragraph", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " has been passed on " + labelName + " paragraph", YesNo.No);

							CommonLib.ThreadSleep(2000);
						} else {
							log(LogStatus.ERROR, value + " is not passed on " + labelName + " paragraph", YesNo.No);
							sa.assertTrue(false, value + " is not passed on " + labelName + " paragraph");
							return false;
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on " + labelName + " paragraph", YesNo.No);
						sa.assertTrue(false, "Not able to click on " + labelName + " paragraph");
						return false;
					}

				} else if (labelName.equalsIgnoreCase(excelLabel.Related_To.toString())) {
					ArrayList<String> tagList = new ArrayList<String>();

					if (value.contains("<break>")) {
						String[] data = value.split("<break>");
						for (int i = 0; i < data.length; i++) {
							tagList.add(data[i]);
						}
					} else {
						tagList.add(value);
					}

					for (int i = 0; i < tagList.size(); i++) {

						if (tagList.get(i).contains("Prefilled")) {
							String val1 = tagList.get(i);
							String[] tgName = tagList.get(i).split("<Prefilled>");
							xPath = "//lightning-pill[@data-id='" + tgName[0] + "']";
							ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN,
									30);
							if (ele != null) {
								log(LogStatus.INFO, tgName[0] + " is prefilled", YesNo.No);
							} else {
								log(LogStatus.ERROR, tgName[0] + " is not prefilled", YesNo.No);
								sa.assertTrue(false, tgName[0] + " is not prefilled");
								return false;
							}

						} else {
							xPath = "//input[@placeholder='Search Firm, People or Deals']";
							ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN,
									2);
							if (ele == null) {
								xPath = "//h2[contains(@class,'header_text')]/../..//*[@title='Tag']";
								ele = CommonLib.FindElement(driver, xPath, labelName + " label",
										action.SCROLLANDBOOLEAN, 30);
								if (CommonLib.clickUsingJavaScript(driver, ele, labelName + " paragraph")) {
									log(LogStatus.INFO, "Clicked on Tag button", YesNo.No);
								} else {
									log(LogStatus.ERROR, "Not able to click on Tag button", YesNo.No);
									sa.assertTrue(false, "Not able to click on Tag button");
									return false;
								}

							}

							xPath = "//input[@placeholder='Search Firm, People or Deals']";
							ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN,
									2);
							if (sendKeys(driver, ele, tagList.get(i), "Tag", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,
										tagList.get(i) + " value has been passed in " + labelName + " field", YesNo.No);
								ThreadSleep(3000);
								xPath = "//ul[@class='drop_ul']//li[text()='" + tagList.get(i) + "']";
								ele = CommonLib.FindElement(driver, xPath, labelName + " label",
										action.SCROLLANDBOOLEAN, 30);
								if (click(driver, ele, tagList + " dropdown", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on " + tagList.get(i) + " value", YesNo.No);
								} else {
									log(LogStatus.ERROR, "Not able to click on " + tagList.get(i) + " value", YesNo.No);
									sa.assertTrue(false, "Not able to click on " + tagList.get(i) + " value");
									return false;
								}

							} else {
								log(LogStatus.ERROR, tagList.get(i) + " value is not passed in " + labelName + " field",
										YesNo.No);
								sa.assertTrue(false,
										tagList.get(i) + " value is not passed in " + labelName + " field");
								return false;
							}
						}
					}
				} else {
					log(LogStatus.ERROR, "Label Name is not correct", YesNo.No);
					sa.assertTrue(false, "Label Name is not correct");
					return false;
				}
			}

		}
		if (advanceSection != null) {

			if (clickUsingJavaScript(driver, getSectionBtn("Advanced", 30), "Advanced section",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on Advanced section", YesNo.No);
				for (String[] val : advanceSection) {
					String labelName = val[0];
					String value = val[1];
					// String fieldType=val[2];

					if (labelName.contains("Start Date")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='Start Date Time']/..//label[text()='Date']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}
					} else if (labelName.contains("Start Time")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='Start Date Time']/..//label[text()='Time']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					} else if (labelName.contains("End Date")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='End Date Time']/..//label[text()='Date']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					} else if (labelName.contains("End Time")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='End Date Time']/..//label[text()='Time']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					}

					else if (labelName.contains(excelLabel.Location.toString()) || labelName.contains("Due Date")
							|| labelName.contains("Date")) {

						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
								+ labelName + "']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					}

					else if (labelName.contains(excelLabel.Status.toString())
							|| labelName.contains(excelLabel.Priority.toString())) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
								+ labelName + "']/..//button";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
								+ labelName + "']/..//span[@class='slds-truncate']";
						if (CommonLib.dropDownHandle(driver, ele, xPath, labelName + " dropdown", value)) {
							log(LogStatus.INFO, value + " value has been selected from " + labelName + " field",
									YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not selected from " + labelName + " field",
									YesNo.No);
							sa.assertTrue(false, value + " value is not selected from " + labelName + " field");
							return false;
						}
					} else if (labelName.equalsIgnoreCase("All-Day Event")) {

						if (value.equals("true")) {
							xPath = "//span[text()='All-Day Event']/../../input";
							ele = FindElement(driver, xPath, labelName + " name", action.SCROLLANDBOOLEAN, 30);
							if (isSelected(driver, ele, labelName + " checkbox")) {
								log(LogStatus.INFO, labelName + " checkbox is already selected", YesNo.No);

							} else {
								if (click(driver, ele, labelName + " checkbox", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, labelName + " checkbox has been selected", YesNo.No);
								} else {
									log(LogStatus.ERROR, labelName + " checkbox is not selected", YesNo.No);
									sa.assertTrue(false, labelName + " checkbox is not selected");
									return false;
								}
							}

						} else {
							xPath = "//span[text()='All-Day Event']/../../input";
							ele = FindElement(driver, xPath, labelName + " name", action.SCROLLANDBOOLEAN, 30);
							if (!isSelected(driver, ele, labelName + " checkbox")) {
								log(LogStatus.INFO, labelName + " checkbox is not selected", YesNo.No);

							} else {
								if (click(driver, ele, labelName + " checkbox", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, labelName + " checkbox has been unselected", YesNo.No);
								} else {
									log(LogStatus.ERROR, labelName + " checkbox is not unselected", YesNo.No);
									sa.assertTrue(false, labelName + " checkbox is not unselected");
									return false;
								}
							}
						}
					} else if (labelName.contains("User")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
								+ labelName + "']/..//button";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (click(driver, ele, labelName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on cross icon of value of " + labelName, YesNo.No);
							xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
									+ labelName + "']/..//input";
							ele = CommonLib.FindElement(driver, xPath, labelName + " input", action.SCROLLANDBOOLEAN,
									30);
							if (sendKeys(driver, ele, value, labelName + " input", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, value + " has been passed in " + labelName, YesNo.No);
								xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
										+ labelName + "']/../..//ul//li[text()='" + value + "']";
								ele = CommonLib.FindElement(driver, xPath, value + " list", action.SCROLLANDBOOLEAN,
										30);
								if (clickUsingJavaScript(driver, ele, value + " list element")) {
									log(LogStatus.INFO, "click on the list element of " + value, YesNo.No);
								} else {
									log(LogStatus.ERROR, "Not able to click on the list element of " + value, YesNo.No);
									sa.assertTrue(false, "Not able to click on the list element of " + value);
									return false;
								}

							} else {
								log(LogStatus.ERROR, value + " is not passed in " + labelName, YesNo.No);
								sa.assertTrue(false, value + " is not passed in " + labelName);
								return false;
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on cross icon of value of " + labelName, YesNo.No);
							sa.assertTrue(false, "Not able to click on cross icon of value of " + labelName);
							return false;
						}
					}

					else {
						log(LogStatus.ERROR, "label name is not correct", YesNo.No);
						sa.assertTrue(false, "label name is not correct");
						return false;
					}
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Advanced search section", YesNo.No);
				sa.assertTrue(false, "Not able to click on Advanced search section");
				return false;
			}
		}
		if (taskSectionMultiple != null) {

			int taskSectionLoopCount = 0;
			for (String[][] taskSection : taskSectionMultiple) {

				xPath = "//span[text()='Advanced']/parent::button[@aria-expanded='true']";
				ele = FindElement(driver, xPath, "Advance section", action.SCROLLANDBOOLEAN, 3);
				if (ele == null) {
					if (clickUsingJavaScript(driver, getSectionBtn("Advanced", 3), "Advanced section",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on Advanced section", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to click on Advanced section", YesNo.No);
						sa.assertTrue(false, "Not able to click on Advanced section");
						return false;
					}
				}

				if (taskSectonInNotePopUpNotExpanded(3) != null) {

					if (clickUsingJavaScript(driver, getSectionBtn("Tasks", 30), "Tasks section",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on Tasks section", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to click on Tasks section", YesNo.No);
						sa.assertTrue(false, "Not able to click on Tasks section");
						return false;
					}
				}

				if (taskSectionLoopCount > 0) {

					if (clickUsingJavaScript(driver, notePopUpAddMoreButton(10), "notePopUpAddMoreButton",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on Add More Button", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to click on Add More Button", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on Add More Button");
						return false;
					}
				}

				int followUpTaskCount = taskSectionLoopCount + 1;

				for (String[] val : taskSection) {
					String labelName = val[0];
					String value = val[1];

					if (labelName.contains(excelLabel.Subject.toString()) || labelName.contains("Due Date")
							|| labelName.contains("Date")) {
						xPath = "//span[text()='Tasks']/ancestor::lightning-accordion[1]//div[contains(@class,'slds-m-top_x-small')]["
								+ followUpTaskCount + "]//div[@class='slds-grid']//label[text()='" + labelName
								+ "']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}
					} else if (labelName.contains(excelLabel.Status.toString())) {
						xPath = "//span[text()='Tasks']/ancestor::lightning-accordion[1]//div[contains(@class,'slds-m-top_x-small')]["
								+ followUpTaskCount + "]//div[@class='slds-grid']//label[text()='" + labelName
								+ "']/..//button";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);

						if (CommonLib.dropDownHandle(driver, ele, xPath, labelName + " dropdown", value)) {
							log(LogStatus.INFO, value + " value has been selected from " + labelName + " field",
									YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not selected from " + labelName + " field",
									YesNo.No);
							sa.assertTrue(false, value + " value is not selected from " + labelName + " field");
							return false;
						}
					} else {
						log(LogStatus.ERROR, "Label name is not correct", YesNo.No);
						sa.assertTrue(false, "Label name is not correct");
						return false;
					}
				}

				taskSectionLoopCount++;
			}
		}

		if (click(driver, getfooterSaveOrCancelButton("Save", 20), "Save button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "clicked on Save button", YesNo.No);

			if (suggestedTags != null) {

				log(LogStatus.INFO, "Activity timeline record has been created", YesNo.No);
				ThreadSleep(2000);
				for (int i = 0; i < suggestedTags.length; i++) {

					if (!suggestedTags[0].equals("")) {
						xPath = "//lightning-base-formatted-text[text()='" + suggestedTags[i]
								+ "']/ancestor::th[@data-label='Name']/..//td//input";
						ele = CommonLib.FindElement(driver, xPath, suggestedTags[i] + " sugested Tag",
								action.SCROLLANDBOOLEAN, 30);
						if (click(driver, ele, suggestedTags[i] + " suggested tag", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on " + suggestedTags[i] + " suggested tag checkbox button",
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Not able to click on " + suggestedTags[i] + " suggested tag checkbox button",
									YesNo.No);
							sa.assertTrue(false,
									"Not able to click on " + suggestedTags[i] + " suggested tag checkbox button");
							return false;
						}

					}

				}
				if (click(driver, getfooterTagButton(30), "Tag Button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on footer tag button", YesNo.No);
					ThreadSleep(3000);
					if (crossIconButtonInNotePopUp(8) != null) {
						if (clickUsingJavaScript(driver, crossIconButtonInNotePopUp(8), "close button")) {
							log(LogStatus.INFO, "Note popup has been closed", YesNo.No);
							flag = true;
						} else {
							log(LogStatus.ERROR, "Not able to close the Note popup", YesNo.No);
							sa.assertTrue(false, "Not able to close the Note popup");
							return false;
						}
					} else {
						return true;
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on footer tag button", YesNo.No);
					sa.assertTrue(false, "Not able to click on footer tag button");
					return false;
				}

			}

			else {

				log(LogStatus.INFO, "Activity timeline record has been created", YesNo.No);
				ThreadSleep(2000);
				refresh(driver);
				ThreadSleep(3000);
				if (crossIconButtonInNotePopUp(8) != null) {
					if (clickUsingJavaScript(driver, crossIconButtonInNotePopUp(8), "close button")) {
						log(LogStatus.INFO, "Note popup has been closed", YesNo.No);
						flag = true;
					} else {
						log(LogStatus.ERROR, "Not able to close the Note popup", YesNo.No);
						sa.assertTrue(false, "Not able to close the Note popup");
						return false;
					}
				} else {
					return true;
				}

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Save button", YesNo.No);
			sa.assertTrue(false, "Not able to click on Save button");
			return false;
		}

		return flag;
	}

	public boolean updateActivityTimelineRecordForMultipleFollowUpTasks(String projectName, String[][] basicSection,
			String[][] advanceSection, String[][][] taskSectionMultiple, String[] suggestedTags, String[] removeTagName,
			boolean closeEditButtonAfterDetailsFill, boolean closeIconButtonAfterDetailsFill) {
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		String xPath = "";
		WebElement ele;
		boolean flag = false;
		ThreadSleep(9000);

		if (removeTagName != null && removeTagName.length != 0) {
			for (int i = 0; i < removeTagName.length; i++) {
				ele = getCrossButtonForAlreadySelectedItem(projectName, PageName.TaskPage, "User", false,
						removeTagName[i], action.SCROLLANDBOOLEAN, 10);
				if (ele != null) {
					if (CommonLib.clickUsingJavaScript(driver, ele, removeTagName[i] + " tag name")) {
						log(LogStatus.INFO, "clicked on cross button of " + removeTagName[i], YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to click on cross button of " + removeTagName[i], YesNo.No);
						sa.assertTrue(false, "Not able to click on cross button of " + removeTagName[i]);
						return false;
					}
				} else {
					log(LogStatus.ERROR, "Not able to get the element of " + removeTagName[i], YesNo.No);
					sa.assertTrue(false, "Not able to get the element of " + removeTagName[i]);
					return false;
				}
			}
		}
		String detail = CommonLib.getAttribute(driver, getNotePopUpSectionDetail("Tasks", 30), "Tasks Section",
				"aria-hidden");

		if (detail.equals("true")) {

		} else {

			clickUsingJavaScript(driver, getSectionBtn("Tasks", 30), "Tasks section", action.SCROLLANDBOOLEAN);
		}

		String detail2 = CommonLib.getAttribute(driver, getNotePopUpSectionDetail("Advanced", 30), "Advanced Section",
				"aria-hidden");

		if (detail2.equals("true")) {

		} else {

			clickUsingJavaScript(driver, getSectionBtn("Advanced", 30), "Advanced section", action.SCROLLANDBOOLEAN);
		}

		if (basicSection != null) {

			for (String[] val : basicSection) {
				String labelName = val[0];
				String value = val[1];

				if (labelName.contains(excelLabel.Subject.toString())) {
					xPath = "//label[text()='" + labelName + "']/..//input[contains(@data-id,'combobox')]";
					ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
					if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
					} else {
						log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
						sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
						return false;

					}
				} else if (labelName.contains(excelLabel.Notes.toString())) {
					xPath = "//div[label[text()='Notes']]//textarea";
					ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
					if (CommonLib.clickUsingJavaScript(driver, ele, labelName + " paragraph")) {
						log(LogStatus.INFO, "Clicked on " + labelName + " paragraph", YesNo.No);
						ThreadSleep(2000);

						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						ele.sendKeys(Keys.CONTROL + "A");
						ThreadSleep(1000);
						ele.sendKeys(Keys.BACK_SPACE);

						if (sendKeys(driver, ele, value, labelName + " paragraph", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " has been passed on " + labelName + " paragraph", YesNo.No);

							CommonLib.ThreadSleep(2000);
						} else {
							log(LogStatus.ERROR, value + " is not passed on " + labelName + " paragraph", YesNo.No);
							sa.assertTrue(false, value + " is not passed on " + labelName + " paragraph");
							return false;
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on " + labelName + " paragraph", YesNo.No);
						sa.assertTrue(false, "Not able to click on " + labelName + " paragraph");
						return false;
					}

				} else if (labelName.equalsIgnoreCase(excelLabel.Related_To.toString())) {
					ArrayList<String> tagList = new ArrayList<String>();

					if (value.contains("<break>")) {
						String[] data = value.split("<break>");
						for (int i = 0; i < data.length; i++) {
							tagList.add(data[i]);
						}
					} else {
						tagList.add(value);
					}

					for (int i = 0; i < tagList.size(); i++) {

						xPath = "//input[@placeholder='Search Firm, People or Deals']";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 2);
						if (ele == null) {
							xPath = "//h2[contains(@class,'header_text')]/../..//*[@title='Tag']";
							ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN,
									30);
							if (CommonLib.clickUsingJavaScript(driver, ele, labelName + " paragraph")) {
								log(LogStatus.INFO, "Clicked on Tag button", YesNo.No);
							} else {
								log(LogStatus.ERROR, "Not able to click on Tag button", YesNo.No);
								sa.assertTrue(false, "Not able to click on Tag button");
								return false;
							}

						}

						xPath = "//input[@placeholder='Search Firm, People or Deals']";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 2);
						if (sendKeys(driver, ele, tagList.get(i), "Tag", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, tagList.get(i) + " value has been passed in " + labelName + " field",
									YesNo.No);
							ThreadSleep(3000);
							xPath = "//ul[@class='drop_ul']//li[text()='" + tagList.get(i) + "']";
							ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN,
									30);
							if (click(driver, ele, tagList + " dropdown", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on " + tagList.get(i) + " value", YesNo.No);
							} else {
								log(LogStatus.ERROR, "Not able to click on " + tagList.get(i) + " value", YesNo.No);
								sa.assertTrue(false, "Not able to click on " + tagList.get(i) + " value");
								return false;
							}

						} else {
							log(LogStatus.ERROR, tagList.get(i) + " value is not passed in " + labelName + " field",
									YesNo.No);
							sa.assertTrue(false, tagList.get(i) + " value is not passed in " + labelName + " field");
							return false;
						}
					}
				} else {
					log(LogStatus.ERROR, "Label Name is not correct", YesNo.No);
					sa.assertTrue(false, "Label Name is not correct");
					return false;
				}
			}

		}
		if (advanceSection != null) {

			if (clickUsingJavaScript(driver, getSectionBtn("Advanced", 30), "Advanced section",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on Advanced section", YesNo.No);
				for (String[] val : advanceSection) {
					String labelName = val[0];
					String value = val[1];
					// String fieldType=val[2];

					if (labelName.contains("Start Date")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='Start Date Time']/..//label[text()='Date']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}
					} else if (labelName.contains("Start Time")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='Start Date Time']/..//label[text()='Time']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					} else if (labelName.contains("End Date")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='End Date Time']/..//label[text()='Date']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					} else if (labelName.contains("End Time")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='End Date Time']/..//label[text()='Time']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					}

					else if (labelName.contains(excelLabel.Location.toString()) || labelName.contains("Due Date")
							|| labelName.contains("Date")) {

						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
								+ labelName + "']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					}

					else if (labelName.contains(excelLabel.Status.toString())
							|| labelName.contains(excelLabel.Priority.toString())) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
								+ labelName + "']/..//button";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
								+ labelName + "']/..//span[@class='slds-truncate']";
						if (CommonLib.dropDownHandle(driver, ele, xPath, labelName + " dropdown", value)) {
							log(LogStatus.INFO, value + " value has been selected from " + labelName + " field",
									YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not selected from " + labelName + " field",
									YesNo.No);
							sa.assertTrue(false, value + " value is not selected from " + labelName + " field");
							return false;
						}
					} else if (labelName.equalsIgnoreCase("All-Day Event")) {

						if (value.equals("true")) {
							xPath = "//span[text()='All-Day Event']/../../input";
							ele = FindElement(driver, xPath, labelName + " name", action.SCROLLANDBOOLEAN, 30);
							if (isSelected(driver, ele, labelName + " checkbox")) {
								log(LogStatus.INFO, labelName + " checkbox is already selected", YesNo.No);

							} else {
								if (click(driver, ele, labelName + " checkbox", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, labelName + " checkbox has been selected", YesNo.No);
								} else {
									log(LogStatus.ERROR, labelName + " checkbox is not selected", YesNo.No);
									sa.assertTrue(false, labelName + " checkbox is not selected");
									return false;
								}
							}

						} else {
							xPath = "//span[text()='All-Day Event']/../../input";
							ele = FindElement(driver, xPath, labelName + " name", action.SCROLLANDBOOLEAN, 30);
							if (!isSelected(driver, ele, labelName + " checkbox")) {
								log(LogStatus.INFO, labelName + " checkbox is not selected", YesNo.No);

							} else {
								if (click(driver, ele, labelName + " checkbox", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, labelName + " checkbox has been unselected", YesNo.No);
								} else {
									log(LogStatus.ERROR, labelName + " checkbox is not unselected", YesNo.No);
									sa.assertTrue(false, labelName + " checkbox is not unselected");
									return false;
								}
							}
						}
					} else {
						log(LogStatus.ERROR, "label name is not correct", YesNo.No);
						sa.assertTrue(false, "label name is not correct");
						return false;
					}
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Advanced search section", YesNo.No);
				sa.assertTrue(false, "Not able to click on Advanced search section");
				return false;
			}
		}
		if (taskSectionMultiple != null) {

			int taskSectionLoopCount = 0;
			for (String[][] taskSection : taskSectionMultiple) {

				xPath = "//span[text()='Advanced']/parent::button[@aria-expanded='true']";
				ele = FindElement(driver, xPath, "Advance section", action.SCROLLANDBOOLEAN, 3);
				if (ele == null) {
					if (clickUsingJavaScript(driver, getSectionBtn("Advanced", 3), "Advanced section",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on Advanced section", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to click on Advanced section", YesNo.No);
						sa.assertTrue(false, "Not able to click on Advanced section");
						return false;
					}
				}

				if (taskSectonInNotePopUpNotExpanded(3) != null) {

					if (clickUsingJavaScript(driver, getSectionBtn("Tasks", 30), "Tasks section",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on Tasks section", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to click on Tasks section", YesNo.No);
						sa.assertTrue(false, "Not able to click on Tasks section");
						return false;
					}
				}

				if (taskSectionLoopCount > 0) {

					if (clickUsingJavaScript(driver, notePopUpAddMoreButton(10), "notePopUpAddMoreButton",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on Add More Button", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to click on Add More Button", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on Add More Button");
						return false;
					}
				}
				int followUpTaskCount = taskSectionLoopCount + 1;

				for (String[] val : taskSection) {
					String labelName = val[0];
					String value = val[1];

					if (labelName.contains(excelLabel.Subject.toString()) || labelName.contains("Due Date")
							|| labelName.contains("Date")) {
						xPath = "//span[text()='Tasks']/ancestor::lightning-accordion[1]//div[contains(@class,'slds-m-top_x-small')]["
								+ followUpTaskCount + "]//div[@class='slds-grid']//label[text()='" + labelName
								+ "']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}
					} else if (labelName.contains(excelLabel.Status.toString())) {
						xPath = "//span[text()='Tasks']/ancestor::lightning-accordion[1]//div[contains(@class,'slds-m-top_x-small')]["
								+ followUpTaskCount + "]//div[@class='slds-grid']//label[text()='" + labelName
								+ "']/..//button";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);

						if (CommonLib.dropDownHandle(driver, ele, xPath, labelName + " dropdown", value)) {
							log(LogStatus.INFO, value + " value has been selected from " + labelName + " field",
									YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not selected from " + labelName + " field",
									YesNo.No);
							sa.assertTrue(false, value + " value is not selected from " + labelName + " field");
							return false;
						}
					} else {
						log(LogStatus.ERROR, "Label name is not correct", YesNo.No);
						sa.assertTrue(false, "Label name is not correct");
						return false;
					}
				}
				taskSectionLoopCount++;
			}
		}

		if (closeEditButtonAfterDetailsFill) {
			if (click(driver, getfooterSaveOrCancelButton("Cancel", 20), "Cancel button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on Cancel button", YesNo.No);
				flag = true;
			} else {
				log(LogStatus.ERROR, "Not able to click on Cancel button", YesNo.No);
				sa.assertTrue(false, "Not able to click on Cancel button");
				return false;
			}

		}

		else {

			if (closeIconButtonAfterDetailsFill) {
				if (click(driver, crossIconButtonInNotePopUp(20), "Cross Icon button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on Cross Icon button", YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Not able to click on Cross Icon button", YesNo.No);
					sa.assertTrue(false, "Not able to click on Cross Icon button");
					return false;
				}

			} else {

				if (click(driver, getfooterSaveOrCancelButton("Save", 20), "Save button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on Save button", YesNo.No);

					if (suggestedTags != null) {
						/*
						 * for(int i=0; i<suggestedTags.length; i++) {
						 * xPath="//lightning-base-formatted-text[text()='"+suggestedTags[i]
						 * +"']/ancestor::th[@data-label='Reference Found']/..//td//input";
						 * ele=CommonLib.FindElement(driver, xPath, suggestedTags[i]+" sugested Tag",
						 * action.SCROLLANDBOOLEAN, 30); if(click(driver, ele,
						 * suggestedTags[i]+" suggested tag", action.SCROLLANDBOOLEAN)) {
						 * log(LogStatus.INFO,
						 * "clicked on "+suggestedTags[i]+" suggested tag checkbox button", YesNo.No);
						 * 
						 * } else { log(LogStatus.ERROR,
						 * "Not able to click on "+suggestedTags[i]+" suggested tag checkbox button",
						 * YesNo.No); sa.assertTrue(false,
						 * "Not able to click on "+suggestedTags[i]+" suggested tag checkbox button");
						 * return false; } } if(click(driver, getfooterTagButton(30), "Tag Button",
						 * action.SCROLLANDBOOLEAN)) { log(LogStatus.INFO,
						 * "clicked on footer tag button", YesNo.No); ThreadSleep(2000);
						 * refresh(driver); } else { log(LogStatus.ERROR,
						 * "Not able to click on footer tag button", YesNo.No); sa.assertTrue(false,
						 * "Not able to click on footer tag button"); return false; }
						 */

						log(LogStatus.INFO, "Activity timeline record has been updated", YesNo.No);

						if (suggestedTags.length > 0) {
							if (suggestedTags[0].equalsIgnoreCase("All Records Select")) {
								CommonLib.ThreadSleep(5000);
								if (click(driver, suggestedTagsCheckBoxAllInput(), "suggestedTagsCheckBoxAllInput",
										action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on All Checkbox Input box of Suggested Tags Popup",
											YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"Not able to Click on All Checkbox Input box of Suggested Tags Popup",
											YesNo.No);
									sa.assertTrue(false,
											"Not able to Click on All Checkbox Input box of Suggested Tags Popup");
									return false;
								}

							} else {

								for (int i = 0; i < suggestedTags.length; i++) {

									if (!suggestedTags[0].equals("")) {
										xPath = "//lightning-base-formatted-text[text()='" + suggestedTags[i]
												+ "']/ancestor::th[@data-label='Name']/..//td//input";
										ele = CommonLib.FindElement(driver, xPath, suggestedTags[i] + " sugested Tag",
												action.SCROLLANDBOOLEAN, 30);
										if (click(driver, ele, suggestedTags[i] + " suggested tag",
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO,
													"clicked on " + suggestedTags[i] + " suggested tag checkbox button",
													YesNo.No);

										} else {
											log(LogStatus.ERROR, "Not able to click on " + suggestedTags[i]
													+ " suggested tag checkbox button", YesNo.No);
											sa.assertTrue(false, "Not able to click on " + suggestedTags[i]
													+ " suggested tag checkbox button");
											return false;
										}

									}

								}
							}
						} else {
							log(LogStatus.ERROR, "Please Provide the Expected Suggested Tag Array non - empty",
									YesNo.No);
							sa.assertTrue(false, "Please Provide the Expected Suggested Tag Array non - empty");
							return false;
						}
						if (click(driver, getfooterTagButton(30), "Tag Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on footer tag button", YesNo.No);
							ThreadSleep(2000);
							refresh(driver);
							flag = true;
						} else {
							log(LogStatus.ERROR, "Not able to click on footer tag button", YesNo.No);
							sa.assertTrue(false, "Not able to click on footer tag button");
							return false;
						}

					} else {

						log(LogStatus.INFO, "Activity timeline record has been updated", YesNo.No);
						flag = true;

					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Save button", YesNo.No);
					sa.assertTrue(false, "Not able to click on Save button");
					return false;
				}

			}

		}
		return flag;
	}

	public ArrayList<String> verifyRelatedToNotContains(String[][] basicSection) {

		ArrayList<String> negativeResult = new ArrayList<String>();
		for (String[] val : basicSection) {

			String labelName = val[0];
			String value = val[1];
			ArrayList<String> tagList = new ArrayList<String>();

			if (value.contains("<break>")) {
				String[] data = value.split("<break>");
				for (int i = 0; i < data.length; i++) {
					tagList.add(data[i]);
				}
			} else {
				tagList.add(value);
			}

			for (int i = 0; i < tagList.size(); i++) {
				WebElement ele;
				String xPath = "//input[@placeholder='Search Firm, People or Deals']";
				if (i == 0) {

					ele = isDisplayed(driver,
							CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 10),
							"Visibility", 8, "RelatedTo Search Box");
					if (ele == null) {
						xPath = "//h2[contains(@class,'header_text')]/../..//*[@title='Tag']";

						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						CommonLib.ThreadSleep(4000);
						if (CommonLib.click(driver, ele, labelName + " paragraph", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Tag button", YesNo.No);
						} else {
							log(LogStatus.ERROR, "Not able to click on Tag button", YesNo.No);
							negativeResult.add("Not able to click on Tag button");

						}

					}
				}

				xPath = "//input[@placeholder='Search Firm, People or Deals']";
				ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 10);
				if (sendKeys(driver, ele, tagList.get(i), "Tag", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, tagList.get(i) + " value has been passed in " + labelName + " field", YesNo.No);
					ThreadSleep(3000);
					xPath = "//ul[@class='drop_ul']//li[text()='" + tagList.get(i) + "']";
					ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 8);

					if (ele == null) {
						log(LogStatus.INFO, "Verified Related Association Not Found: " + tagList.get(i), YesNo.No);
					} else {

						log(LogStatus.ERROR,
								"Related Association: " + tagList.get(i) + " should not be there in dropdown",
								YesNo.No);
						negativeResult
								.add("Related Association: " + tagList.get(i) + " should not be there in dropdown");
					}

				} else {
					log(LogStatus.ERROR, tagList.get(i) + " value is not passed in " + labelName + " field", YesNo.No);
					negativeResult.add(tagList.get(i) + " value is not passed in " + labelName + " field");

				}

			}
		}

		return negativeResult;
	}

	public boolean updateActivityTimelineRecordForMultipleFollowUpTasksAndVerifyUIOfSuggestedTags(String projectName,
			String[][] basicSection, String[][] advanceSection, String[][][] taskSectionMultiple,
			String[] suggestedTags, String[] removeTagName) {
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		String xPath = "";
		WebElement ele;
		boolean flag = false;
		ThreadSleep(9000);

		if (removeTagName != null && removeTagName.length != 0) {
			for (int i = 0; i < removeTagName.length; i++) {
				ele = getCrossButtonForAlreadySelectedItem(projectName, PageName.TaskPage, "User", false,
						removeTagName[i], action.SCROLLANDBOOLEAN, 10);
				if (ele != null) {
					if (CommonLib.clickUsingJavaScript(driver, ele, removeTagName[i] + " tag name")) {
						log(LogStatus.INFO, "clicked on cross button of " + removeTagName[i], YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to click on cross button of " + removeTagName[i], YesNo.No);
						sa.assertTrue(false, "Not able to click on cross button of " + removeTagName[i]);
						return false;
					}
				} else {
					log(LogStatus.ERROR, "Not able to get the element of " + removeTagName[i], YesNo.No);
					sa.assertTrue(false, "Not able to get the element of " + removeTagName[i]);
					return false;
				}
			}
		}

		String detail = CommonLib.getAttribute(driver, getNotePopUpSectionDetail("Tasks", 30), "Tasks Section",
				"aria-hidden");

		if (detail.equals("true")) {

		} else {

			clickUsingJavaScript(driver, getSectionBtn("Tasks", 30), "Tasks section", action.SCROLLANDBOOLEAN);
		}

		String detail2 = CommonLib.getAttribute(driver, getNotePopUpSectionDetail("Advanced", 30), "Advanced Section",
				"aria-hidden");

		if (detail2.equals("true")) {

		} else {

			clickUsingJavaScript(driver, getSectionBtn("Advanced", 30), "Advanced section", action.SCROLLANDBOOLEAN);
		}

		if (basicSection != null) {

			for (String[] val : basicSection) {
				String labelName = val[0];
				String value = val[1];

				if (labelName.contains(excelLabel.Subject.toString())) {
					xPath = "//label[text()='" + labelName + "']/..//input[contains(@data-id,'combobox')]";
					ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
					if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
					} else {
						log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
						sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
						return false;

					}
				} else if (labelName.contains(excelLabel.Notes.toString())) {
					xPath = "//div[label[text()='Notes']]//textarea";
					ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
					if (CommonLib.clickUsingJavaScript(driver, ele, labelName + " paragraph")) {
						log(LogStatus.INFO, "Clicked on " + labelName + " paragraph", YesNo.No);
						ThreadSleep(2000);

						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						ele.sendKeys(Keys.CONTROL + "A");
						ThreadSleep(1000);
						ele.sendKeys(Keys.BACK_SPACE);

						if (sendKeys(driver, ele, value, labelName + " paragraph", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " has been passed on " + labelName + " paragraph", YesNo.No);

							CommonLib.ThreadSleep(2000);
						} else {
							log(LogStatus.ERROR, value + " is not passed on " + labelName + " paragraph", YesNo.No);
							sa.assertTrue(false, value + " is not passed on " + labelName + " paragraph");
							return false;
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on " + labelName + " paragraph", YesNo.No);
						sa.assertTrue(false, "Not able to click on " + labelName + " paragraph");
						return false;
					}

				} else if (labelName.equalsIgnoreCase(excelLabel.Related_To.toString())) {
					ArrayList<String> tagList = new ArrayList<String>();

					if (value.contains("<break>")) {
						String[] data = value.split("<break>");
						for (int i = 0; i < data.length; i++) {
							tagList.add(data[i]);
						}
					} else {
						tagList.add(value);
					}

					for (int i = 0; i < tagList.size(); i++) {

						xPath = "//input[@placeholder='Search Firm, People or Deals']";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 2);
						if (ele == null) {
							xPath = "//h2[contains(@class,'header_text')]/../..//*[@title='Tag']";
							ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN,
									30);
							if (CommonLib.clickUsingJavaScript(driver, ele, labelName + " paragraph")) {
								log(LogStatus.INFO, "Clicked on Tag button", YesNo.No);
							} else {
								log(LogStatus.ERROR, "Not able to click on Tag button", YesNo.No);
								sa.assertTrue(false, "Not able to click on Tag button");
								return false;
							}

						}

						xPath = "//input[@placeholder='Search Firm, People or Deals']";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 2);
						if (sendKeys(driver, ele, tagList.get(i), "Tag", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, tagList.get(i) + " value has been passed in " + labelName + " field",
									YesNo.No);
							ThreadSleep(3000);
							xPath = "//ul[@class='drop_ul']//li[text()='" + tagList.get(i) + "']";
							ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN,
									30);
							if (click(driver, ele, tagList + " dropdown", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on " + tagList.get(i) + " value", YesNo.No);
							} else {
								log(LogStatus.ERROR, "Not able to click on " + tagList.get(i) + " value", YesNo.No);
								sa.assertTrue(false, "Not able to click on " + tagList.get(i) + " value");
								return false;
							}

						} else {
							log(LogStatus.ERROR, tagList.get(i) + " value is not passed in " + labelName + " field",
									YesNo.No);
							sa.assertTrue(false, tagList.get(i) + " value is not passed in " + labelName + " field");
							return false;
						}
					}
				} else {
					log(LogStatus.ERROR, "Label Name is not correct", YesNo.No);
					sa.assertTrue(false, "Label Name is not correct");
					return false;
				}
			}

		}
		if (advanceSection != null) {

			if (clickUsingJavaScript(driver, getSectionBtn("Advanced", 30), "Advanced section",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on Advanced section", YesNo.No);
				for (String[] val : advanceSection) {
					String labelName = val[0];
					String value = val[1];
					// String fieldType=val[2];

					if (labelName.contains("Start Date")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='Start Date Time']/..//label[text()='Date']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}
					} else if (labelName.contains("Start Time")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='Start Date Time']/..//label[text()='Time']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					} else if (labelName.contains("End Date")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='End Date Time']/..//label[text()='Date']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					} else if (labelName.contains("End Time")) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//legend[text()='End Date Time']/..//label[text()='Time']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					}

					else if (labelName.contains(excelLabel.Location.toString()) || labelName.contains("Due Date")
							|| labelName.contains("Date")) {

						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
								+ labelName + "']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}

					}

					else if (labelName.contains(excelLabel.Status.toString())
							|| labelName.contains(excelLabel.Priority.toString())) {
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
								+ labelName + "']/..//button";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						xPath = "//span[text()='Advanced']/ancestor::section//lightning-layout//label[text()='"
								+ labelName + "']/..//span[@class='slds-truncate']";
						if (CommonLib.dropDownHandle(driver, ele, xPath, labelName + " dropdown", value)) {
							log(LogStatus.INFO, value + " value has been selected from " + labelName + " field",
									YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not selected from " + labelName + " field",
									YesNo.No);
							sa.assertTrue(false, value + " value is not selected from " + labelName + " field");
							return false;
						}
					} else if (labelName.equalsIgnoreCase("All-Day Event")) {

						if (value.equals("true")) {
							xPath = "//span[text()='All-Day Event']/../../input";
							ele = FindElement(driver, xPath, labelName + " name", action.SCROLLANDBOOLEAN, 30);
							if (isSelected(driver, ele, labelName + " checkbox")) {
								log(LogStatus.INFO, labelName + " checkbox is already selected", YesNo.No);

							} else {
								if (click(driver, ele, labelName + " checkbox", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, labelName + " checkbox has been selected", YesNo.No);
								} else {
									log(LogStatus.ERROR, labelName + " checkbox is not selected", YesNo.No);
									sa.assertTrue(false, labelName + " checkbox is not selected");
									return false;
								}
							}

						} else {
							xPath = "//span[text()='All-Day Event']/../../input";
							ele = FindElement(driver, xPath, labelName + " name", action.SCROLLANDBOOLEAN, 30);
							if (!isSelected(driver, ele, labelName + " checkbox")) {
								log(LogStatus.INFO, labelName + " checkbox is not selected", YesNo.No);

							} else {
								if (click(driver, ele, labelName + " checkbox", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, labelName + " checkbox has been unselected", YesNo.No);
								} else {
									log(LogStatus.ERROR, labelName + " checkbox is not unselected", YesNo.No);
									sa.assertTrue(false, labelName + " checkbox is not unselected");
									return false;
								}
							}
						}
					} else {
						log(LogStatus.ERROR, "label name is not correct", YesNo.No);
						sa.assertTrue(false, "label name is not correct");
						return false;
					}
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Advanced search section", YesNo.No);
				sa.assertTrue(false, "Not able to click on Advanced search section");
				return false;
			}
		}
		if (taskSectionMultiple != null) {

			int taskSectionLoopCount = 0;
			for (String[][] taskSection : taskSectionMultiple) {

				xPath = "//span[text()='Advanced']/parent::button[@aria-expanded='true']";
				ele = FindElement(driver, xPath, "Advance section", action.SCROLLANDBOOLEAN, 3);
				if (ele == null) {
					if (clickUsingJavaScript(driver, getSectionBtn("Advanced", 3), "Advanced section",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on Advanced section", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to click on Advanced section", YesNo.No);
						sa.assertTrue(false, "Not able to click on Advanced section");
						return false;
					}
				}

				if (taskSectonInNotePopUpNotExpanded(3) != null) {

					if (clickUsingJavaScript(driver, getSectionBtn("Tasks", 30), "Tasks section",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on Tasks section", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to click on Tasks section", YesNo.No);
						sa.assertTrue(false, "Not able to click on Tasks section");
						return false;
					}
				}

				if (taskSectionLoopCount > 0) {

					if (clickUsingJavaScript(driver, notePopUpAddMoreButton(10), "notePopUpAddMoreButton",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on Add More Button", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to click on Add More Button", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on Add More Button");
						return false;
					}
				}
				int followUpTaskCount = taskSectionLoopCount + 1;

				for (String[] val : taskSection) {
					String labelName = val[0];
					String value = val[1];

					if (labelName.contains(excelLabel.Subject.toString()) || labelName.contains("Due Date")
							|| labelName.contains("Date")) {
						xPath = "//span[text()='Tasks']/ancestor::lightning-accordion[1]//div[contains(@class,'slds-m-top_x-small')]["
								+ followUpTaskCount + "]//div[@class='slds-grid']//label[text()='" + labelName
								+ "']/..//input";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);
						if (CommonLib.sendKeys(driver, ele, value, labelName + " label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, value + " value has been passed in " + labelName + " field", YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not passed in " + labelName + " field", YesNo.No);
							sa.assertTrue(false, value + " value is not passed in " + labelName + " field");
							return false;
						}
					} else if (labelName.contains(excelLabel.Status.toString())) {
						xPath = "//span[text()='Tasks']/ancestor::lightning-accordion[1]//div[contains(@class,'slds-m-top_x-small')]["
								+ followUpTaskCount + "]//div[@class='slds-grid']//label[text()='" + labelName
								+ "']/..//button";
						ele = CommonLib.FindElement(driver, xPath, labelName + " label", action.SCROLLANDBOOLEAN, 30);

						if (CommonLib.dropDownHandle(driver, ele, xPath, labelName + " dropdown", value)) {
							log(LogStatus.INFO, value + " value has been selected from " + labelName + " field",
									YesNo.No);
						} else {
							log(LogStatus.ERROR, value + " value is not selected from " + labelName + " field",
									YesNo.No);
							sa.assertTrue(false, value + " value is not selected from " + labelName + " field");
							return false;
						}
					} else {
						log(LogStatus.ERROR, "Label name is not correct", YesNo.No);
						sa.assertTrue(false, "Label name is not correct");
						return false;
					}
				}
				taskSectionLoopCount++;
			}
		}

		if (click(driver, getfooterSaveOrCancelButton("Save", 20), "Save button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "clicked on Save button", YesNo.No);

			if (suggestedTags != null) {
				/*
				 * for(int i=0; i<suggestedTags.length; i++) {
				 * xPath="//lightning-base-formatted-text[text()='"+suggestedTags[i]
				 * +"']/ancestor::th[@data-label='Reference Found']/..//td//input";
				 * ele=CommonLib.FindElement(driver, xPath, suggestedTags[i]+" sugested Tag",
				 * action.SCROLLANDBOOLEAN, 30); if(click(driver, ele,
				 * suggestedTags[i]+" suggested tag", action.SCROLLANDBOOLEAN)) {
				 * log(LogStatus.INFO,
				 * "clicked on "+suggestedTags[i]+" suggested tag checkbox button", YesNo.No);
				 * 
				 * } else { log(LogStatus.ERROR,
				 * "Not able to click on "+suggestedTags[i]+" suggested tag checkbox button",
				 * YesNo.No); sa.assertTrue(false,
				 * "Not able to click on "+suggestedTags[i]+" suggested tag checkbox button");
				 * return false; } } if(click(driver, getfooterTagButton(30), "Tag Button",
				 * action.SCROLLANDBOOLEAN)) { log(LogStatus.INFO,
				 * "clicked on footer tag button", YesNo.No); ThreadSleep(2000);
				 * refresh(driver); } else { log(LogStatus.ERROR,
				 * "Not able to click on footer tag button", YesNo.No); sa.assertTrue(false,
				 * "Not able to click on footer tag button"); return false; }
				 */

				log(LogStatus.INFO, "Activity timeline record has been updated", YesNo.No);

				if (suggestedTags.length > 0) {

					boolean suggestedFlag = verifyUIOfSuggestedTagSection(suggestedTags);
					if (suggestedFlag == true) {
					} else {
						return false;
					}

				} else {
					log(LogStatus.ERROR, "Please Provide the Expected Suggested Tag Array non - empty", YesNo.No);
					sa.assertTrue(false, "Please Provide the Expected Suggested Tag Array non - empty");
					return false;
				}
				if (click(driver, getfooterTagButton(30), "Tag Button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on footer tag button", YesNo.No);
					ThreadSleep(2000);
					refresh(driver);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Not able to click on footer tag button", YesNo.No);
					sa.assertTrue(false, "Not able to click on footer tag button");
					return false;
				}

			} else {

				log(LogStatus.INFO, "Activity timeline record has been updated", YesNo.No);
				flag = true;

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Save button", YesNo.No);
			sa.assertTrue(false, "Not able to click on Save button");
			return false;
		}
		return flag;
	}

	public ArrayList<String> verifyRecordOnContactSectionAcuity(String[] name, String title[], String deals[],
			String meetingAndCalls[], String email[]) {
		String xPath = "";
		WebElement ele = null;
		ArrayList<String> result = new ArrayList<String>();
		if (name.length != 0) {
			if (name.length == title.length && name.length == deals.length && name.length == meetingAndCalls.length
					&& name.length == email.length) {
				for (int i = 0; i < name.length; i++) {
					if (name[i] != null && name[i] != "") {
						xPath = "//td[@data-label='Name']//a[text()='" + name[i] + "']";
						ele = CommonLib.FindElement(driver, xPath, name[i] + " Contact name", action.SCROLLANDBOOLEAN,
								30);
						String actualName = getText(driver, ele, name[i] + " Contact name", action.SCROLLANDBOOLEAN);
						if (actualName.toLowerCase().trim().equals(name[i].toLowerCase().trim())) {
							log(LogStatus.INFO, "Expected contact name : " + name[i]
									+ " has been match with the Actual Contact Name : " + actualName, YesNo.No);

						} else {
							log(LogStatus.ERROR, "Expected contact name : " + name[i]
									+ " is not match with the Actual Contact Name : " + actualName, YesNo.No);
							result.add("Expected contact name : " + name[i]
									+ " is not match with the Actual Contact Name : " + actualName);
						}

						if (title[i] != null && title[i] != "") {
							xPath = "//a[text()='" + name[i]
									+ "']/ancestor::td[@data-label='Name']/..//td[@data-label='Title']//span";
							ele = CommonLib.FindElement(driver, xPath, "title", action.SCROLLANDBOOLEAN, 30);
							String actualTitle = getText(driver, ele, "title", action.SCROLLANDBOOLEAN);
							if (actualTitle.toLowerCase().trim().equals(title[i].toLowerCase().trim())) {
								log(LogStatus.INFO,
										"Actual result " + actualTitle
												+ " of Title has been matched with Expected result : " + title[i],
										YesNo.No);
							} else {
								log(LogStatus.ERROR,
										"Actual result " + actualTitle
												+ " of Title is not matched with Expected result : " + title[i],
										YesNo.No);
								result.add("Actual result " + actualTitle
										+ " of Title is not matched with Expected result : " + title[i]);
							}
						}

						if (deals[i] != null && deals[i] != "") {
							xPath = "//a[text()='" + name[i]
									+ "']/ancestor::td[@data-label='Name']/..//td[@data-label='Deals']//span";
							ele = CommonLib.FindElement(driver, xPath, "deal", action.SCROLLANDBOOLEAN, 30);
							String actualDeal = getText(driver, ele, "deal", action.SCROLLANDBOOLEAN);
							if (actualDeal.toLowerCase().trim().equals(deals[i].toLowerCase().trim())) {
								log(LogStatus.INFO,
										"Actual result : " + actualDeal
												+ " of deal has been matched with Expected resut : " + deals[i],
										YesNo.No);
							} else {
								log(LogStatus.ERROR,
										"Actual result : " + actualDeal
												+ " of deal are not matched with Expected result : " + deals[i],
										YesNo.No);
								result.add("Actual result : " + actualDeal
										+ " of deal are not matched with Expected result : " + deals[i]);
							}
						}

						if (meetingAndCalls[i] != null && meetingAndCalls[i] != "") {
							xPath = "//a[text()='" + name[i]
									+ "']/ancestor::td[@data-label='Name']/..//td[@data-label='Meetings and Calls']//span";
							ele = CommonLib.FindElement(driver, xPath, "meeting and call", action.SCROLLANDBOOLEAN, 30);
							String actualmeetingAndCalls = getText(driver, ele, "meeting and call",
									action.SCROLLANDBOOLEAN);
							if (actualmeetingAndCalls.toLowerCase().trim()
									.equals(meetingAndCalls[i].toLowerCase().trim())) {
								log(LogStatus.INFO,
										"Actual result " + actualmeetingAndCalls
												+ " of meeting and call has been matched with Expected result : "
												+ meetingAndCalls[i],
										YesNo.No);
							} else {
								log(LogStatus.ERROR,
										"Actual result " + actualmeetingAndCalls
												+ " of meeting and call are not matched with Expected result : "
												+ meetingAndCalls[i],
										YesNo.No);
								result.add("Actual result " + actualmeetingAndCalls
										+ " of meeting and call are not matched with Expected result : "
										+ meetingAndCalls[i]);
							}
						}

						if (email[i] != null && email[i] != "") {
							xPath = "//a[text()='" + name[i]
									+ "']/ancestor::td[@data-label='Name']/..//td[@data-label='Emails']//span";
							ele = CommonLib.FindElement(driver, xPath, "email", action.SCROLLANDBOOLEAN, 30);
							String actualEmail = getText(driver, ele, "email", action.SCROLLANDBOOLEAN);
							if (actualEmail.toLowerCase().trim().equals(email[i].toLowerCase().trim())) {
								log(LogStatus.INFO,
										"Actual result " + actualEmail
												+ " of email has been matched with Expected result : " + email[i],
										YesNo.No);
							} else {
								log(LogStatus.ERROR,
										"Actual result " + actualEmail
												+ " of email are not matched with Expected result : " + email[i],
										YesNo.No);
								result.add("Actual result " + actualEmail
										+ " of email are not matched with Expected result : " + email[i]);
							}
						}

					} else {

						log(LogStatus.ERROR, "Either name is null or blank", YesNo.No);
						result.add("Either name is null or blank");
					}
				}
			} else {
				log(LogStatus.ERROR,
						"The length of Expected Contact Name, Contact title, Contact deal, Contact meeting and call, Contact Email are not equal.",
						YesNo.No);
				result.add(
						"The length of Expected Contact Name, Contact title, Contact deal, Contact meeting and call, Contact Email are not equal.");
			}
		} else {
			log(LogStatus.ERROR, "Please provide the contact name. Contat name should not be blank", YesNo.No);
			result.add("Please provide the contact name. Contat name should not be blank");
		}

		return result;
	}

	public ArrayList<String> verifyRecordsonInteractionsViewAllPopup(String[] icon, String[] date, String[] subject,
			String[] details, String[] assignedTo, String[] correspondenceHeader) {
		String xPath;
		WebElement ele;

		ArrayList<String> result = new ArrayList<String>();
		String parentId = switchOnWindow(driver);
		if (parentId != null) {
			if (correspondenceHeader != null && correspondenceHeader.length != 0) {
				for (int i = 0; i < correspondenceHeader.length; i++) {

					if (icon[i] != null && icon.length != 0 && icon[i] != "" && icon[i] != null) {
						xPath = "//button[text()='" + correspondenceHeader[i]
								+ "']/ancestor::tr//th[@data-label='Type']//lightning-icon";
						ele = FindElement(driver, xPath, "Icon type of " + correspondenceHeader[i],
								action.SCROLLANDBOOLEAN, 20);
						String iconVal = getAttribute(driver, ele, "Icon type", "class");
						if (iconVal.contains(icon[i].toLowerCase())) {
							log(LogStatus.INFO, "The icon :" + icon[i] + " has been verified against "
									+ correspondenceHeader[i] + " record", YesNo.No);
						} else {
							log(LogStatus.ERROR, "The icon :" + icon[i] + " is not verified against "
									+ correspondenceHeader[i] + " record", YesNo.No);
							result.add("The icon :" + icon[i] + " is not verified against " + correspondenceHeader[i]
									+ " record");

						}
					}

					if (date != null && date.length != 0 && date[i] != "" && date[i] != null) {
						xPath = "//button[text()='" + correspondenceHeader[i]
								+ "']/ancestor::tr//td[@data-label='Date']//lightning-base-formatted-text";
						ele = FindElement(driver, xPath, "date ", action.SCROLLANDBOOLEAN, 25);
						String actDate = getText(driver, ele, "date ", action.SCROLLANDBOOLEAN);
						String actualDate = null;

						if (actDate.contains(",")) {
							actualDate = actDate.split(",")[0];
						} else {
							actualDate = actDate;
						}

						String dueDate;
						if (date[i].contains(",")) {
							dueDate = date[i].split(",")[0];
						} else {
							dueDate = date[i];
						}

						String[] splittedDate = dueDate.split("/");
						char dayMonth = splittedDate[0].charAt(0);
						char day = splittedDate[1].charAt(0);
						String month;
						if (dayMonth == '0') {
							month = splittedDate[0].replaceAll("0", "");
						} else {
							month = splittedDate[0];
						}
						String finalDay;
						if (day == '0') {
							finalDay = splittedDate[1].replaceAll("0", "");
						} else {
							finalDay = splittedDate[1];
						}

						String expectedDate = month + "/" + finalDay + "/" + splittedDate[2];

						if (actualDate.trim().equalsIgnoreCase(expectedDate.trim())) {
							log(LogStatus.INFO,
									"actual date : " + actualDate + " has been matched with the Expected date : "
											+ expectedDate + " of subject : " + subject[i],
									YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"actual date : " + actualDate + " is not matched with the Expected date : "
											+ expectedDate + " of subject : " + subject[i],
									YesNo.No);
							result.add("actual date : " + actualDate + " is not matched with the Expected date : "
									+ expectedDate + " of subject : " + subject[i]);
						}
					}
					if (subject != null && subject.length != 0 && subject[i] != "" && subject[i] != null) {
						xPath = "//tr//td[@data-label='Subject']//button[text()='" + correspondenceHeader[i] + "']";
						ele = FindElement(driver, xPath, "subject ", action.SCROLLANDBOOLEAN, 25);
						String actSubject = getText(driver, ele, "subject ", action.SCROLLANDBOOLEAN);
						if (actSubject.equalsIgnoreCase(subject[i])) {
							log(LogStatus.INFO, "actual subject : " + actSubject
									+ " has been matched with the Expected subject : " + subject[i], YesNo.No);
						} else {
							log(LogStatus.ERROR, "actual subject : " + actSubject
									+ " is not matched with the Expected subject : " + subject[i], YesNo.No);
							result.add("actual subject : " + actSubject + " is not matched with the Expected subject : "
									+ subject[i]);
						}
					}
					if (details != null && details.length != 0 && details[i] != "" && details[i] != null) {
						xPath = "//button[text()='" + correspondenceHeader[i]
								+ "']/ancestor::tr//td[@data-label='Details']//button";
						ele = FindElement(driver, xPath, "details ", action.SCROLLANDBOOLEAN, 25);
						String actDetails = getText(driver, ele, "details ", action.SCROLLANDBOOLEAN);
						if (actDetails.trim().equalsIgnoreCase(details[i].trim().replaceAll(" +", " "))) {
							log(LogStatus.INFO,
									"actual details : " + actDetails + " has been matched with the Expected details : "
											+ details[i] + " of subject : " + subject[i],
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"actual details : " + actDetails + " is not matched with the Expected details : "
											+ details[i] + " of subject : " + subject[i],
									YesNo.No);
							result.add("actual details : " + actDetails + " is not matched with the Expected details : "
									+ details[i] + " of subject : " + subject[i]);
						}
					}
					/*
					 * if (assignedTo != null && assignedTo.length != 0 && assignedTo[i] != "" &&
					 * assignedTo[i] != null) { xPath =
					 * "//h2[contains(text(),'All Interactions')]/..//following-sibling::div//*[text()='"
					 * + correspondenceHeader[i] +
					 * "']/ancestor::tr//td[@data-label='Assigned To']//a"; ele =
					 * FindElement(driver, xPath, "assigned to ", action.SCROLLANDBOOLEAN, 25);
					 * String actAssigned = getText(driver, ele, "assigned to ",
					 * action.SCROLLANDBOOLEAN); if (actAssigned.equalsIgnoreCase(assignedTo[i])) {
					 * log(LogStatus.INFO, "actual AssignedTo value : " + actAssigned +
					 * " has been matched with the Expected AssignedTo value : " + assignedTo[i] +
					 * " of subject : " + subject[i], YesNo.No); } else { log(LogStatus.ERROR,
					 * "actual AssignedTo value : " + actAssigned +
					 * " is not matched with the Expected AssignedTo value : " + assignedTo[i] +
					 * " of subject : " + subject[i], YesNo.No);
					 * result.add("actual AssignedTo value : " + actAssigned +
					 * " is not matched with the Expected AssignedTo value : " + assignedTo[i] +
					 * " of subject : " + subject[i]); } }
					 */
				}
			} else {
				log(LogStatus.ERROR,
						"Either correspondence is null or Empty. Please provide data to verify data on interaction popup ",
						YesNo.No);
				result.add(
						"Either correspondence is null or Empty. Please provide data to verify data on interaction popup");
			}
			driver.close();
			driver.switchTo().window(parentId);
		} else {
			log(LogStatus.ERROR, "New tab did not open after click", YesNo.No);
			result.add("New tab did not open after click");
		}
		return result;
	}

	public boolean verifyCountOfRelatedAssociationOnTaggedPopupOnInteractionSctionOfFirstRecord() {
		String xPath;
		WebElement ele;
		List<WebElement> elements;
		boolean flag = false;

		xPath = "(//span[text()='Interactions']/ancestor::div[contains(@class,'slds-p-bottom_none')]//span[@class=\"slds-pill__label\" and starts-with(text(),'+')])[1]";
		ele = FindElement(driver, xPath, "Tagged record", action.SCROLLANDBOOLEAN, 20);
		String InteractionData = getText(driver, ele, "Tagged record", action.SCROLLANDBOOLEAN);
		String replacedVal = InteractionData.replace("+", "");
		int countOnInteraction = Integer.parseInt(replacedVal);
		if (clickUsingJavaScript(driver, ele, "Tagged record", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Tagged record for Tagged popup", YesNo.No);
			ThreadSleep(3000);
			xPath = "//h2[text()='Tagged']/../..//a";
			elements = FindElements(driver, xPath, "Record on Tagged section");
			if (countOnInteraction == elements.size()) {
				log(LogStatus.INFO, "The count has been verified on Tagged popup", YesNo.No);
				if (click(driver, popupCloseButton("Tagged", 20), "Tagged popup close", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on close button of Tagged popup", YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Not able to click on close button of Tagged popup", YesNo.No);
				}
			} else {
				log(LogStatus.ERROR, "The count is not verified on Tagged popup", YesNo.No);
				if (click(driver, popupCloseButton("Tagged", 20), "Tagged popup close", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on close button of Tagged popup", YesNo.No);
				} else {
					log(LogStatus.ERROR, "Not able to click on close button of Tagged popup", YesNo.No);
				}
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tagged record for Tagged popup", YesNo.No);
		}

		return true;

	}

	public ArrayList<String> verifyRecordOnConnectionsPopUpOfContactInAcuity(String contactName, String[] teamMember,
			String[] title, String[] deals, String[] meetingAndCalls, String[] email) {

		ArrayList<String> result = new ArrayList<String>();

		if (contactName != null && contactName != "") {

			if (contactNameUserIconButton(contactName, 30) != null) {

				if (CommonLib.clickUsingJavaScript(driver, contactNameUserIconButton(contactName, 30),
						"Contact Name: " + contactName, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on connection icon of contact : " + contactName, YesNo.No);
					String parentID = switchOnWindow(driver);
					if (teamMember.length == title.length && teamMember.length == deals.length
							&& teamMember.length == meetingAndCalls.length
							&& teamMember.length == meetingAndCalls.length && teamMember.length == email.length) {
						for (int i = 0; i < teamMember.length; i++) {
							if (connectionPopUpTeamMember(teamMember[i], 20) != null) {
								if (title[i] != null && title[i] != "") {
									String actualTitle = getText(driver,
											connectionPopUpTitle(teamMember[i], title[i], 30), "Title",
											action.SCROLLANDBOOLEAN);
									if (actualTitle.equalsIgnoreCase(title[i])) {
										log(LogStatus.INFO, "Actual result " + actualTitle
												+ " of Title has been matched with Expected result : " + title[i]
												+ " of Team Member: " + teamMember[i] + " under Record page: "
												+ contactName + " for user " + teamMember[i], YesNo.No);
									} else {
										log(LogStatus.ERROR,
												"Actual result " + actualTitle
														+ " of Title is not matched with Expected result : " + title[i]
														+ " of Team Member: " + teamMember[i] + " under Record page: "
														+ contactName + " for user " + teamMember[i],
												YesNo.No);
										result.add("Actual result " + actualTitle
												+ " of Title is not matched with Expected result : " + title[i]
												+ " of Team Member: " + teamMember[i] + " under Record page: "
												+ contactName + " for user " + teamMember[i]);
									}
								}

								if (deals[i] != null && deals[i] != "") {

									String actualDeal = getText(driver,
											connectionPopUpDealsCount(teamMember[i], deals[i], 30), "deal",
											action.SCROLLANDBOOLEAN);
									if (actualDeal.equalsIgnoreCase(deals[i])) {
										log(LogStatus.INFO,
												"Actual result " + actualDeal
														+ " of deal has been matched with Expected resut : " + deals[i]
														+ " of Team Member: " + teamMember[i] + " under Record page: "
														+ contactName + " for user " + teamMember[i],
												YesNo.No);
									} else {
										log(LogStatus.ERROR,
												"Actual result " + actualDeal
														+ " of deal are not matched with Expected resut : " + deals[i]
														+ " of Team Member: " + teamMember[i] + " under Record page: "
														+ contactName + " for user " + teamMember[i],
												YesNo.No);
										result.add("Actual result " + actualDeal
												+ " of deal are not matched with Expected resut : " + deals[i]
												+ " of Team Member: " + teamMember[i] + " under Record page: "
												+ contactName + " for user " + teamMember[i]);
									}
								}

								if (meetingAndCalls[i] != null && meetingAndCalls[i] != "") {

									String actualmeetingAndCalls = getText(driver,
											connectionPopUpMeetingCallsCount(teamMember[i], meetingAndCalls[i], 30),
											"meeting and call", action.SCROLLANDBOOLEAN);
									if (actualmeetingAndCalls.equalsIgnoreCase(meetingAndCalls[i])) {
										log(LogStatus.INFO, "Actual result " + actualmeetingAndCalls
												+ " of meeting and call has been matched with Expected result : "
												+ meetingAndCalls[i] + " of Team Member: " + teamMember[i]
												+ " under Record page: " + contactName + " for user " + teamMember[i],
												YesNo.No);
									} else {
										log(LogStatus.ERROR, "Actual result " + actualmeetingAndCalls
												+ " of meeting and call are not matched with Expected resut : "
												+ meetingAndCalls[i] + " of Team Member: " + teamMember[i]
												+ " under Record page: " + contactName + " for user " + teamMember[i],
												YesNo.No);
										result.add("Actual result " + actualmeetingAndCalls
												+ " of meeting and call are not matched with Expected resut : "
												+ meetingAndCalls[i] + " of Team Member: " + teamMember[i]
												+ " under Record page: " + contactName + " for user " + teamMember[i]);
									}
								}
								if (email[i] != null && email[i] != "") {
									String actualEmail = getText(driver,
											connectionPopUpEmailCount(teamMember[i], email[i], 30), "email",
											action.SCROLLANDBOOLEAN);
									if (actualEmail.equalsIgnoreCase(email[i])) {
										log(LogStatus.INFO, "Actual result " + actualEmail
												+ " of email has been matched with Expected result : " + email[i]
												+ " of Team Member: " + teamMember[i] + " under Record page: "
												+ contactName + " for user " + teamMember[i], YesNo.No);
									} else {
										log(LogStatus.ERROR,
												"Actual result " + actualEmail
														+ " of email are not matched with Expected resut : " + email[i]
														+ " of Team Member: " + teamMember[i] + " under Record page: "
														+ contactName + " for user " + teamMember[i],
												YesNo.No);
										result.add("Actual result " + actualEmail
												+ " of email are not matched with Expected resut : " + email[i]
												+ " of Team Member: " + teamMember[i] + " under Record page: "
												+ contactName + " for user " + teamMember[i]);
									}
								}
							} else {
								log(LogStatus.ERROR, "No Team Member found of name: " + teamMember[i] + " for contact: "
										+ contactName, YesNo.No);
								result.add("No Team Member found of name: " + teamMember[i] + " for contact: "
										+ contactName);

							}
						}

					} else {
						log(LogStatus.ERROR,
								"The size of Team member, Title, deal, meeting and call, email are not equal",
								YesNo.No);
						result.add("The size of Team member, Title, deal, meeting and call, email are not equal");
					}

					driver.close();
					driver.switchTo().window(parentID);
				} else {
					log(LogStatus.ERROR, "Not Able to Click on Contact: " + contactName, YesNo.No);
					result.add("Not Able to Click on Contact: " + contactName);

				}
			} else {
				log(LogStatus.ERROR, contactName + " is not avalable in contact section, So Can not Click on User Icon",
						YesNo.No);
				result.add(contactName + " is not avalable in contact section, So Can not Click on User Icon");

			}
		} else {
			log(LogStatus.ERROR, "Provided Contact Name should not be null in DataSheet", YesNo.No);
			result.add("Provided Contact Name should not be null in DataSheet");

		}
		return result;
	}

	public ArrayList<String> verifyRecordOnMeetingsAndCallPopUpSectionInAcuity(String[] icon, String[] date,
			String[] subjectName, String[] details, String[] assignedTO) {
		ArrayList<String> result = new ArrayList<String>();
		String xPath;
		WebElement ele;
		String parentId = switchToWindowOpenNextToParentWindow(driver);
		ThreadSleep(4000);
		if (parentId != null) {
			if (icon.length == date.length && icon.length == subjectName.length && icon.length == details.length
					&& icon.length == assignedTO.length) {
				/*
				 * if (getMeetingAndCallPopUp(20) != null) { log(LogStatus.INFO,
				 * "Meeting and calls popup has been open", YesNo.No);
				 */
				for (int i = 0; i < subjectName.length; i++) {
					if (subjectName[i] != null && subjectName[i] != "") {
						if (icon[i] != null && icon[i] != "") {
							xPath = "//button[text()='" + subjectName[i]
									+ "']/ancestor::td[@data-label='Subject']/../th[@data-label='Type']//lightning-icon";
							ele = FindElement(driver, xPath, "Icon type", action.SCROLLANDBOOLEAN, 20);
							String iconName = getAttribute(driver, ele, "Icon type", "class");
							if (iconName.contains(icon[i].toLowerCase())) {
								log(LogStatus.INFO, icon[i] + " icon has been verified against " + subjectName[i]
										+ " on Meetings and Calls popup", YesNo.No);

							} else {
								log(LogStatus.ERROR, icon + " icon is not verified against " + subjectName[i]
										+ " on Meetings and Calls popup", YesNo.No);
								result.add(icon + " icon is not verified against " + subjectName[i]
										+ " on Meetings and Calls popup");
							}
						}

						if (date[i] != null) {
							xPath = "//button[text()='" + subjectName[i]
									+ "']/ancestor::td[@data-label='Subject']/../td[@data-label='Date']//lightning-base-formatted-text";
							ele = FindElement(driver, xPath, "Date column", action.SCROLLANDBOOLEAN, 30);

							String val = getText(driver, ele, "Date", action.BOOLEAN);

							String actualDate = null;

							if (val.contains(",")) {
								actualDate = val.split(",")[0];
							} else {
								actualDate = val;
							}

							String dueDate;
							if (date[i].contains(",")) {
								dueDate = date[i].split(",")[0];
							} else {
								dueDate = date[i];
							}

							String[] splittedDate = dueDate.split("/");
							char dayMonth = splittedDate[0].charAt(0);
							char day = splittedDate[1].charAt(0);
							String month;
							if (dayMonth == '0') {
								month = splittedDate[0].replaceAll("0", "");
							} else {
								month = splittedDate[0];
							}
							String finalDay;
							if (day == '0') {
								finalDay = splittedDate[1].replaceAll("0", "");
							} else {
								finalDay = splittedDate[1];
							}

							String expectedDate = month + "/" + finalDay + "/" + splittedDate[2];

							if (actualDate.trim().equalsIgnoreCase(expectedDate.trim())) {
								log(LogStatus.INFO,
										"Expected date: " + date[i] + " has been matched with the actual date: "
												+ actualDate + " for subject: " + subjectName[i]
												+ " on meeting and call popup",
										YesNo.No);
							} else {
								log(LogStatus.INFO,
										"Expected date: " + date[i] + " is not matched with the actual date: "
												+ actualDate + " for subject: " + subjectName[i]
												+ " on meeting and call popup",
										YesNo.No);
								result.add("Expected date: " + date[i] + " is not matched with the actual date: "
										+ actualDate + " for subject: " + subjectName[i]
										+ " on meeting and call popup");
							}
						}

						if (subjectName[i] != null && subjectName[i] != "") {
							xPath = "//td[@data-label='Subject']//button[text()='" + subjectName[i] + "']";
							ele = FindElement(driver, xPath, "Subject column", action.SCROLLANDBOOLEAN, 30);

							String actualSubject = getText(driver, ele, "subject", action.BOOLEAN);
							if (actualSubject.equalsIgnoreCase(subjectName[i].trim())) {
								log(LogStatus.INFO,
										"Expected subject: " + subjectName[i]
												+ " has been matched with the actual subject: " + actualSubject,
										YesNo.No);
							} else {
								log(LogStatus.ERROR,
										"Expected subject: " + subjectName[i]
												+ " is not matched with the actual subject: " + actualSubject,
										YesNo.No);
								result.add("Expected subject: " + subjectName[i]
										+ " is not matched with the actual subject : " + actualSubject);
							}
						}

						if (details[i] != null) {
							xPath = "//button[text()='" + subjectName[i]
									+ "']/ancestor::td[@data-label='Subject']/../td[@data-label='Details']//button";
							ele = FindElement(driver, xPath, "Details column", action.SCROLLANDBOOLEAN, 30);

							String actualDetails = getText(driver, ele, "Details", action.BOOLEAN);

							System.out.println(details[i].trim().replaceAll(" +", " "));
							if (actualDetails.equals(details[i].trim().replaceAll(" +", " "))) {
								log(LogStatus.INFO,
										"Expected details: " + details[i]
												+ " has been matched with the actual details: " + actualDetails
												+ " for subject: " + subjectName[i]
												+ " on meeting and call popup. Subject: " + subjectName,
										YesNo.No);
							} else {
								log(LogStatus.ERROR,
										"Expected details: " + details[i] + " is not matched with the actual details: "
												+ actualDetails + " for subject: " + subjectName[i]
												+ " on meeting and call popup",
										YesNo.No);
								result.add("Expected details: " + details[i]
										+ " is not matched with the actual details: " + actualDetails + " for subject: "
										+ subjectName[i] + " on meeting and call popup");
							}
						}

						/*
						 * if (assignedTO[i] != null && assignedTO[i] != "") { xPath = "//a[text()='" +
						 * subjectName[i] +
						 * "']/ancestor::td[@data-label='Subject']/../td[@data-label='Assigned To']//a";
						 * ele = FindElement(driver, xPath, "Assigned To column",
						 * action.SCROLLANDBOOLEAN, 30);
						 * 
						 * String actualAssignedTO = getText(driver, ele, "User", action.BOOLEAN); if
						 * (actualAssignedTO.equalsIgnoreCase(assignedTO[i])) { log(LogStatus.INFO,
						 * "Expected Assigned to : " + assignedTO[i] +
						 * " has been matched with the actual Assigned to : " + actualAssignedTO +
						 * " for subject: " + subjectName[i] + " on meeting and call popup", YesNo.No);
						 * } else { log(LogStatus.ERROR, "Expected Assigned to : " + assignedTO[i] +
						 * " is not matched with the actual Assigned to : " + actualAssignedTO +
						 * " for subject: " + subjectName[i] + " on meeting and call popup", YesNo.No);
						 * result.add("Expected Assigned to : " + assignedTO[i] +
						 * " is not matched with the actual Assigned to : " + actualAssignedTO +
						 * " for subject: " + subjectName[i] + " on meeting and call popup"); } }
						 */
					} else {
						log(LogStatus.ERROR, "Either subject name is empty or null", YesNo.No);
						result.add("Either subject name is empty or null");
					}

				}
				/*
				 * xPath =
				 * "//h2[contains(text(),'Meetings and Calls')]/../button//lightning-primitive-icon"
				 * ; ele = FindElement(driver, xPath, "close icon", action.SCROLLANDBOOLEAN,
				 * 30); if (CommonLib.clickUsingJavaScript(driver, ele, "close icon")) {
				 * log(LogStatus.INFO, "Clicked on close icon of popup", YesNo.No); } else {
				 * log(LogStatus.ERROR, "Not able to click on close icon of popup", YesNo.No);
				 * result.add("Not able to click on close icon of popup"); }
				 * 
				 * } else { log(LogStatus.ERROR, "Meeting and calls popup is not open",
				 * YesNo.No); result.add("Meeting and calls popup is not open"); }
				 */
			} else {
				log(LogStatus.ERROR, "The length of Icon, Date, Subject and Assigned to are not equal.", YesNo.No);
				result.add("The length of Icon, Date, Subject and Assigned to are not equal.");
			}
			driver.close();
			driver.switchTo().window(parentId);
		} else {
			log(LogStatus.ERROR, "Meeting and call page did not open", YesNo.No);
			result.add("Meeting and call page did not open");
		}

		return result;
	}

	public ArrayList<String> verifyDefaultSortingOfReferencedTypeOnTaggedSection(boolean isInstitutionRecord) {
		String xPath;
		List<WebElement> elements;
		ArrayList<String> result = new ArrayList<String>();
		if (click(driver, getTaggedRecordName("Firms", 30), "Firms tab", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Firms tab name", YesNo.No);
			xPath = "//span[text()='Firms']/ancestor::table//button[@name='timesRef']";
			elements = FindElements(driver, xPath, "Time Reference Count");
			if (CommonLib.checkSorting(driver, SortOrder.Decending, elements)) {
				log(LogStatus.INFO, "Default Decending order of Time Referenced count have been verified on Firms tag",
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Default Decending order of Time Referenced count are not verified on Firms tag",
						YesNo.No);
				result.add("Default Decending order of Time Referenced count are not verified on Firms tag");
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on Firms tab name", YesNo.No);
			result.add("Not able to click on Firms tab name");
		}

		if (click(driver, getTaggedRecordName("People", 30), "People tab", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on People tab name", YesNo.No);
			xPath = "//span[text()='People']/ancestor::table//button[@name='timesRef']";
			elements = FindElements(driver, xPath, "Time Reference Count");
			if (CommonLib.checkSorting(driver, SortOrder.Decending, elements)) {
				log(LogStatus.INFO, "Default Decending order of Time Referenced count have been verified on People tag",
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Default Decending order of Time Referenced count are not verified on People tag",
						YesNo.No);
				result.add("Default Decending order of Time Referenced count are not verified on People tag");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on People tab name", YesNo.No);
			result.add("Not able to click on People tab name");
		}
		if (isInstitutionRecord == false) {
			if (click(driver, getTaggedRecordName("Deals", 30), "Deals tab", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Deals tab name", YesNo.No);
				xPath = "//span[text()='Deals']/ancestor::table//button[@name='timesRef']";
				elements = FindElements(driver, xPath, "Time Reference Count");
				if (CommonLib.checkSorting(driver, SortOrder.Decending, elements)) {
					log(LogStatus.INFO,
							"Default Decending order of Time Referenced count have been verified on Deals tag",
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Default Decending order of Time Referenced count are not verified on Deals tag", YesNo.No);
					result.add("Default Decending order of Time Referenced count are not verified on Deals tag");
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on Deals tab name", YesNo.No);
				result.add("Not able to click on Deals tab name");
			}
		}
		return result;
	}

	public ArrayList<String> verifyActivityTimeLineRecordShouldNotVisibleOnViewAllInteractionPopup(
			String[] subjectName) {
		String xPath;
		WebElement ele;
		List<WebElement> elements;
		ArrayList<String> result = new ArrayList<String>();
		ThreadSleep(3000);

		xPath = "//h2[contains(text(),'All Interactions with')]/../following-sibling::div//td[@data-label='Subject']//a";
		elements = FindElements(driver, xPath, "Subject names on Interaction section");
		String[] actualSubject = new String[elements.size()];

		for (int i = 0; i < elements.size(); i++) {
			actualSubject[i] = getText(driver, elements.get(i), "Subject Name ", action.SCROLLANDBOOLEAN);
		}

		for (int i = 0; i < subjectName.length; i++) {
			int k = 0;
			for (int j = 0; j < actualSubject.length; j++) {
				if (subjectName[i].trim().equals(actualSubject[j].trim())) {
					log(LogStatus.ERROR, "Expected subject name : " + subjectName[i]
							+ " has been with actual subject name: " + actualSubject[j], YesNo.No);
					result.add("Expected subject name : " + subjectName[i] + " has been with actual subject name: "
							+ actualSubject[j]);
					k++;
				}
			}
			if (k != 0) {
				log(LogStatus.INFO,
						"Expected subject name : " + subjectName[i] + " is not available on View All Interaction popup",
						YesNo.No);
			}
		}

		return result;

	}

	public boolean clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(String subjectName) {
		boolean flag = false;
		if (subjectOfInteractionCard(subjectName, 15) != null) {

			if (click(driver, subjectOfInteractionCard(subjectName, 15), "Subject Name: " + subjectName,
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on Subject: " + subjectName + " value", YesNo.No);

				String windowID = switchOnWindow(driver);
				if (windowID != null) {

					if (getPageHeaderTitle(20) != null) {
						log(LogStatus.INFO, subjectName + " link is redirecting to Details Page", YesNo.No);

						flag = true;

					} else {
						log(LogStatus.ERROR, subjectName + " links is not redirecting to Details Page", YesNo.No);

					}
				} else {
					log(LogStatus.ERROR, subjectName + " url did not open in new tab", YesNo.No);

				}

			} else {
				log(LogStatus.ERROR, "Not able to click on Subject: " + subjectName + " value", YesNo.Yes);

			}

		} else {

			log(LogStatus.INFO,
					subjectName + " has not found in Interaction Cards, So going to check in View All PopUp", YesNo.No);
			if (getInteractionViewAllBtn(8) != null) {

				if (click(driver, getInteractionViewAllBtn(8), "View All Button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on View All Button", YesNo.No);

					if (getIntractionSubjectName(subjectName, 10) != null) {

						if (clickUsingJavaScript(driver, getIntractionSubjectName(subjectName, 15),
								"Subject Name: " + subjectName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on Subject: " + subjectName + " value", YesNo.No);

							String windowID = switchOnWindow(driver);
							if (windowID != null) {

								if (getPageHeaderTitle(20) != null) {
									log(LogStatus.INFO, subjectName + " link is redirecting to Details Page", YesNo.No);

									flag = true;

								} else {
									log(LogStatus.ERROR, subjectName + " links is not redirecting to Details Page",
											YesNo.No);

								}
							} else {
								log(LogStatus.ERROR, subjectName + " url did not open in new tab", YesNo.No);

							}

						} else {
							log(LogStatus.ERROR, "Not able to click on Subject: " + subjectName + " value", YesNo.Yes);

						}

					} else {
						log(LogStatus.ERROR,
								"Subject: " + subjectName
										+ " also not found in View All popup, So not able to perform click operation",
								YesNo.Yes);
					}

				} else {
					log(LogStatus.ERROR, "Not able to Click on View All Button", YesNo.Yes);

				}

			} else {
				log(LogStatus.ERROR, "View All Button is not present, So not able to Click on Subject: " + subjectName,
						YesNo.Yes);
			}
		}
		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param projectName
	 * @param listViewName
	 * @param timeOut
	 */
	public ArrayList<String> verifyRecordsonInteractionsViewAllPopup(ArrayList<String> icon, ArrayList<String> date,
			ArrayList<String> subject, ArrayList<String> details, ArrayList<String> assignedTo) {

		List<String> actualSubjects = acuityViewAllPopUpSubjects();
		List<String> actualIcons = acuityViewAllPopUpIcons();
		List<String> actualDates = acuityViewAllPopUpDates();
		List<String> actualDetails = acuityViewAllPopUpDetails();
		List<String> actualAssignedTo = acuityViewAllPopUpAssignedTo();

		ArrayList<String> result = new ArrayList<String>();
		if (subject != null && subject.size() != 0) {
			for (int i = 0; i < subject.size(); i++) {

				Integer index = actualSubjects.indexOf(subject.get(i));

				if (!index.equals(-1)) {
					log(LogStatus.INFO, "actual subject : " + actualSubjects.get(index)
							+ " has been matched with the Expected subject : " + subject.get(i), YesNo.No);

					if (icon != null && icon.size() != 0 && icon.get(i) != "") {

						if (actualIcons.get(index).toLowerCase().contains(icon.get(i).toLowerCase())) {
							log(LogStatus.INFO, "The icon :" + icon.get(i) + " has been verified against "
									+ subject.get(i) + " record", YesNo.No);
						} else {
							log(LogStatus.ERROR, "The icon :" + icon.get(i) + " is not verified against "
									+ subject.get(i) + " record", YesNo.No);
							result.add("The icon :" + icon.get(i) + " is not verified against " + subject.get(i)
									+ " record");
						}
					}

					if (date != null && date.size() != 0 && date.get(i) != "") {

						if (actualDates.get(index).equalsIgnoreCase(date.get(i))) {
							log(LogStatus.INFO,
									"actual date : " + actualDates.get(index)
											+ " has been matched with the Expected date : " + date.get(i)
											+ " against Subject: " + subject.get(i),
									YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"actual date : " + actualDates.get(index)
											+ " is not matched with the Expected date : " + date.get(i)
											+ " against Subject: " + subject.get(i),
									YesNo.No);
							result.add("actual date : " + actualDates.get(index)
									+ " is not matched with the Expected date : " + date.get(i) + " against Subject: "
									+ subject.get(i));
						}
					}

					if (details != null && details.size() != 0 && details.get(i) != "") {

						if (actualDetails.get(index).equalsIgnoreCase(details.get(i))) {
							log(LogStatus.INFO,
									"actual details : " + actualDetails.get(index)
											+ " has been matched with the Expected details : " + details.get(i)
											+ " against Subject: " + subject.get(i),
									YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"actual details : " + actualDetails.get(index)
											+ " is not matched with the Expected details : " + details.get(i)
											+ " against Subject: " + subject.get(i),
									YesNo.No);
							result.add("actual details : " + actualDetails.get(index)
									+ " is not matched with the Expected details : " + details.get(i)
									+ " against Subject: " + subject.get(i));
						}
					}
					if (assignedTo != null && assignedTo.size() != 0 && assignedTo.get(i) != "") {

						if (actualAssignedTo.get(index).equalsIgnoreCase(assignedTo.get(i))) {
							log(LogStatus.INFO,
									"actual AssignedTo value : " + actualAssignedTo.get(index)
											+ " has been matched with the Expected AssignedTo value : "
											+ assignedTo.get(i) + " against Subject: " + subject.get(i),
									YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"actual AssignedTo value : " + actualAssignedTo.get(index)
											+ " is not matched with the Expected AssignedTo value : "
											+ assignedTo.get(i) + " against Subject: " + subject.get(i),
									YesNo.No);
							result.add("actual AssignedTo value : " + actualAssignedTo.get(index)
									+ " is not matched with the Expected AssignedTo value : " + assignedTo.get(i)
									+ " against Subject: " + subject.get(i));
						}
					}
				} else {
					log(LogStatus.ERROR, "actual subject : " + actualSubjects.get(index)
							+ " is not matched with the Expected subject : " + subject.get(i), YesNo.No);
					result.add("actual subject : " + actualSubjects.get(index)
							+ " is not matched with the Expected subject : " + subject.get(i));
				}

			}
		} else {
			log(LogStatus.ERROR,
					"Either subject is null or Empty. Please provide data to verify data on interaction popup ",
					YesNo.No);
			result.add("Either subject is null or Empty. Please provide data to verify data on interaction popup");
		}
		return result;
	}

	public List<String> fieldValueVerification(String[] labelAndValueSeprateByBreak) {

		List<String> result = new ArrayList<String>();
		String label = "";
		String value = "";

		CommonLib.ThreadSleep(14000);
		try {
			for (String labelValue : labelAndValueSeprateByBreak) {

				label = labelValue.split("<break>", -1)[0];
				value = labelValue.split("<break>", -1)[1];

				if (valueOfLabelInDetailPage(label, 45) != null) {
					log(LogStatus.INFO, "Label found: " + label, YesNo.No);
					String actualValue = CommonLib
							.getText(driver, valueOfLabelInDetailPage(label, 30), "value of: " + label, action.BOOLEAN)
							.replaceAll("[\\t\\n\\r]+", " ");
					if (actualValue.contains(value)) {
						log(LogStatus.INFO, "Value contains for label: " + label + " ,Expected: " + value
								+ " and Actual: " + actualValue, YesNo.No);

					} else {
						log(LogStatus.ERROR, "Value not contains for label: " + label + " ,Expected: " + value
								+ " but Actual: " + actualValue, YesNo.No);
						result.add("Value not contains for label: " + label + " ,Expected: " + value + " but Actual: "
								+ actualValue);
					}
				} else {
					log(LogStatus.ERROR, "Not found the label: " + label, YesNo.No);
					result.add("Not found the label: " + label);
				}

			}
		} catch (Exception e) {
			log(LogStatus.ERROR, "Exception: " + e.getMessage(), YesNo.No);
			return result;
		}

		return result;
	}

	public ArrayList<String> verifyFilterIconAndFilterRecordsOnInteractionsPopup(String[] filterValue,
			String[] filterIconType) {

		String xPath;
		WebElement ele;
		List<WebElement> elements;
		String parentId = switchOnWindow(driver);

		ArrayList<String> result = new ArrayList<String>();
		if (getFilterIconOnInteractionPopup(20) != null) {
			log(LogStatus.INFO, "Filter Icon is visible on Interaction popup", YesNo.No);

			if (clickUsingJavaScript(driver, getFilterIconOnInteractionPopup(20), "Filter icon on Interaction popup")) {
				log(LogStatus.INFO, "clicked on filter icon", YesNo.No);

				String filterHeading = getText(driver, getheadingOnFilterSectionInteractionPopup(20), "Filter heading",
						action.SCROLLANDBOOLEAN);
				if (filterHeading.trim().equals("Filters")) {
					log(LogStatus.INFO, "Filters heading has been verified on Filter section", YesNo.No);
				} else {
					log(LogStatus.ERROR, "Filters heading is not verified on Filter section", YesNo.No);
					result.add("Filters heading is not verified on Filter section");
				}

				if (getcloseIconOnFilterSectiOnInteractionPopup(20) != null) {
					log(LogStatus.INFO, "Close icon visibles on Filter section", YesNo.No);
				} else {
					log(LogStatus.ERROR, "Close icon is not visible on Filter section", YesNo.No);
					result.add("Close icon is not visible on Filter section");
				}

				xPath = "//section[contains(@class,'myfilter')]//span[@class='slds-form-element__label']";
				elements = FindElements(driver, xPath, "Filter type");
				String[] actulaFilterType = new String[elements.size()];
				for (int i = 0; i < actulaFilterType.length; i++) {
					actulaFilterType[i] = getText(driver, elements.get(i), "Filter value", action.SCROLLANDBOOLEAN);
				}

				for (int i = 0; i < filterValue.length; i++) {
					int k = 0;
					for (int j = 0; j < actulaFilterType.length; j++) {
						if (filterValue[i].trim().equals(actulaFilterType[j].trim())) {
							log(LogStatus.INFO,
									"Expected filter type: " + filterValue[i]
											+ " has been matched with the Actual filter type: " + actulaFilterType[j],
									YesNo.No);
							k++;
						}
					}
					if (k == 0) {
						log(LogStatus.ERROR,
								"Expected filter type: " + filterValue[i] + " did not match in filter section",
								YesNo.No);
						result.add("Expected filter type: " + filterValue[i] + " did not match in filter section");
					}
				}

				xPath = "//section[contains(@class,'myfilter')]//input[@name='All']";
				ele = FindElement(driver, xPath, "All Record checkbox", action.BOOLEAN, 20);
				if (ele != null) {
					if (isSelected(driver, ele, "All types filter")) {
						if (click(driver, ele, "All types checkbox", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,
									"Click on the All types checkbox, so All types checkbox has been unselected",
									YesNo.No);
							for (int i = 1; i < filterValue.length; i++) {
								xPath = "//section[contains(@class,'myfilter')]//span[text()='" + filterValue[i]
										+ "']/../../input";
								ele = FindElement(driver, xPath, filterValue[i] + " checkbox", action.SCROLLANDBOOLEAN,
										20);
								if (click(driver, ele, filterValue[i] + " checkbox", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on the checkbox of " + filterValue[i], YesNo.No);

									xPath = "//p[text()='" + acuityDefaultMessage + "']";
									ele = FindElement(driver, xPath, "No Item ", action.SCROLLANDBOOLEAN, 10);
									if (ele == null) {
										xPath = "//th[@data-label='Type']//lightning-icon";
										elements = FindElements(driver, xPath, "Icon");

										for (int j = 0; j < elements.size(); j++) {
											String iconType = getAttribute(driver, elements.get(j), "Icon class",
													"class");
											if (iconType.toLowerCase().trim()
													.contains(filterIconType[i - 1].toLowerCase().trim())) {
												log(LogStatus.INFO,
														filterValue[i]
																+ " filter has been verfied on interaction popup",
														YesNo.No);
											} else {
												log(LogStatus.ERROR,
														filterValue[i] + " filter are not verfied on interaction popup",
														YesNo.No);
												result.add(filterValue[i]
														+ " filter are not verfied on interaction popup");
											}
										}

										xPath = "//section[contains(@class,'myfilter')]//span[text()='" + filterValue[i]
												+ "']/../../input";
										ele = FindElement(driver, xPath, filterValue[i] + " checkbox",
												action.SCROLLANDBOOLEAN, 20);
										if (click(driver, ele, filterValue[i] + " checkbox", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on the checkbox of " + filterValue[i],
													YesNo.No);
										} else {
											log(LogStatus.ERROR,
													"Not able to click on the checkbox of " + filterValue[i] + ". So "
															+ filterValue[i] + " filter did not unselect",
													YesNo.No);
											result.add("Not able to click on the checkbox of " + filterValue[i]
													+ ". So " + filterValue[i] + " filter did not unselect");
										}

									} else {
										log(LogStatus.INFO,
												"records are not available on " + filterValue[i] + " filter", YesNo.No);
										xPath = "//section[contains(@class,'myfilter')]//span[text()='" + filterValue[i]
												+ "']/../../input";
										ele = FindElement(driver, xPath, filterValue[i] + " checkbox",
												action.SCROLLANDBOOLEAN, 20);
										if (click(driver, ele, filterValue[i] + " checkbox", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on the checkbox of " + filterValue[i],
													YesNo.No);
										} else {
											log(LogStatus.ERROR,
													"Not able to click on the checkbox of " + filterValue[i] + ". So "
															+ filterValue[i] + " filter did not unselect",
													YesNo.No);
											result.add("Not able to click on the checkbox of " + filterValue[i]
													+ ". So " + filterValue[i] + " filter did not unselect");
										}
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on the checkbox of " + filterValue[i],
											YesNo.No);
									result.add("Not able to click on the checkbox of " + filterValue[i]);
								}
							}
						} else {
							log(LogStatus.ERROR,
									"Not able to click on the All types checkbox, so All types checkbox selected",
									YesNo.No);
							result.add("Not able to click on the All types checkbox, so All types checkbox selected");
						}

					} else {
						log(LogStatus.ERROR, "Not able to get the element of All Types filter", YesNo.No);
						result.add("Not able to get the element of All Types filter");
					}
				}

				int filterValueLength = filterValue.length;
				int filterIconTypeLength = filterIconType.length;

				xPath = "//section[contains(@class,'myfilter')]//span[text()='" + filterValue[filterValueLength - 1]
						+ "']/../../input";

				ele = FindElement(driver, xPath, filterValue[filterValueLength - 1] + " checkbox",
						action.SCROLLANDBOOLEAN, 20);
				if (click(driver, ele, filterValue[filterValueLength - 1] + " checkbox", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on the checkbox of " + filterValue[filterValueLength - 1], YesNo.No);
					xPath = "//section[contains(@class,'myfilter')]//span[text()='" + filterValue[filterValueLength - 2]
							+ "']/../../input";
					ele = FindElement(driver, xPath, filterValue[filterValueLength - 2] + " checkbox",
							action.SCROLLANDBOOLEAN, 20);
					if (click(driver, ele, filterValue[filterValueLength - 2] + " checkbox", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on the checkbox of " + filterValue[filterValueLength - 2],
								YesNo.No);

						xPath = "//p[text()='" + acuityDefaultMessage + "']";
						ele = FindElement(driver, xPath, "No Item ", action.SCROLLANDBOOLEAN, 10);
						if (ele == null) {
							xPath = "//th[@data-label='Type']//lightning-icon";
							elements = FindElements(driver, xPath, "Icon");

							for (int j = 0; j < elements.size(); j++) {
								String iconType = getAttribute(driver, elements.get(j), "Icon class", "class");
								if (iconType.toLowerCase().trim()
										.contains(filterIconType[filterIconTypeLength - 1].toLowerCase().trim())
										|| iconType.toLowerCase().trim().contains(
												filterIconType[filterIconTypeLength - 2].toLowerCase().trim())) {
									log(LogStatus.INFO,
											iconType + " filter records has been verfied on interaction popup",
											YesNo.No);
								} else {
									log(LogStatus.ERROR,
											iconType + " filter records are not verfied on interaction popup",
											YesNo.No);
									result.add(iconType + "filter records are not verfied on interaction popup");
								}
							}
						} else {
							log(LogStatus.INFO, "records are not showing after applying the filter", YesNo.No);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on checkbox of " + filterValue[filterValueLength - 2],
								YesNo.No);
						result.add("Not able to click on checkbox of " + filterValue[filterValueLength - 2]);
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on checkbox of " + filterValue[filterValueLength - 1],
							YesNo.No);
					result.add("Not able to click on checkbox of " + filterValue[filterValueLength - 1]);
				}
				driver.close();
				driver.switchTo().window(parentId);

			} else {
				log(LogStatus.ERROR, "Not able to click on filter icon", YesNo.No);
				result.add("Not able to click on filter icon");
			}
		} else {
			log(LogStatus.ERROR, "Filter Icon does not visible on Interaction popup", YesNo.No);
			result.add("Filter Icon does not visible on Interaction popup");
		}

		return result;
	}

	public List<String> verifyNotificationUIWhenNoEventThereOnRecordDetailsPage() {

		List<String> negativeResults = new ArrayList<String>();
		if (CommonLib.clickUsingJavaScript(driver, getNotificationIcon(), "NotificationIcon")) {
			List<WebElement> notificationOptionsList = getNotificationOptions(); // fetch notification option list

			if (notificationOptionsList.isEmpty()) {
				log(LogStatus.PASS, "No Record is present there in Notification Pane", YesNo.No);
				if (notificationMsgInRecordPage(5) != null) {
					log(LogStatus.PASS,
							"Notification Msg is present there: " + notificationMsgInRecordPage(5).getText(), YesNo.No);

				} else {
					log(LogStatus.FAIL, "Notification Msg is not present there", YesNo.No);
					negativeResults.add("Notification Msg is not present there");
				}

				if (notificationHeaderInRecordDetailsPage(5) != null) {
					log(LogStatus.PASS, "Notification Header is present there: "
							+ notificationHeaderInRecordDetailsPage(5).getText(), YesNo.No);

				} else {
					log(LogStatus.FAIL, "Notification Header is not present there", YesNo.No);
					negativeResults.add("Notification Header is not present there");
				}

				if (notificationCloseIconInRecordDetailsPage(5) != null) {
					log(LogStatus.PASS, "Notification Close Icon is present there", YesNo.No);

				} else {
					log(LogStatus.FAIL, "Notification Close Icon is not present there", YesNo.No);
					negativeResults.add("Notification Close Icon is not present there");
				}

			} else {
				log(LogStatus.FAIL, "No Record should be present there in Notification Pane", YesNo.No);
				negativeResults.add("No Record should be present there in Notification Pane");
			}

		} else {
			log(LogStatus.FAIL, "Not able to click on Notification Icon", YesNo.No);
			negativeResults.add("Not able to click on Notification Icon");
		}

		return negativeResults;
	}

	public ArrayList<String> verifyFilterIconAndFilterRecordsOnMeetingAndCallPopup(String[] filterValue,
			String[] filterIconType) {

		String xPath;
		WebElement ele;
		List<WebElement> elements;
		String parentId = switchToWindowOpenNextToParentWindow(driver);
		ArrayList<String> result = new ArrayList<String>();
		if (getfilterIconOnMeetingAndCallPopup(20) != null) {
			log(LogStatus.INFO, "Filter Icon is visible on Meetings and Calls popup", YesNo.No);

			if (clickUsingJavaScript(driver, getfilterIconOnMeetingAndCallPopup(20),
					"Filter icon on Meetings and Calls popup")) {
				log(LogStatus.INFO, "clicked on filter icon", YesNo.No);

				String filterHeading = getText(driver, getheadingOnFilterSectionMeetingAndCallPopup(20),
						"Filter heading", action.SCROLLANDBOOLEAN);
				if (filterHeading.trim().equals("Filters")) {
					log(LogStatus.INFO, "Filters heading has been verified on Filter section", YesNo.No);
				} else {
					log(LogStatus.ERROR, "Filters heading is not verified on Filter section", YesNo.No);
					result.add("Filters heading is not verified on Filter section");
				}

				if (getcloseIconOnFilterSectiOnMeetingAndCallPopup(20) != null) {
					log(LogStatus.INFO, "Close icon visibles on Filter section", YesNo.No);
				} else {
					log(LogStatus.ERROR, "Close icon is not visible on Filter section", YesNo.No);
					result.add("Close icon is not visible on Filter section");
				}

				xPath = "//section//span[@class='slds-form-element__label']";
				elements = FindElements(driver, xPath, "Filter type");
				String[] actulaFilterType = new String[elements.size()];
				for (int i = 0; i < actulaFilterType.length; i++) {
					actulaFilterType[i] = getText(driver, elements.get(i), "Filter value", action.SCROLLANDBOOLEAN);
				}

				for (int i = 0; i < filterValue.length; i++) {
					int k = 0;
					for (int j = 0; j < actulaFilterType.length; j++) {
						if (filterValue[i].trim().equals(actulaFilterType[j].trim())) {
							log(LogStatus.INFO,
									"Expected filter type: " + filterValue[i]
											+ " has been matched with the Actual filter type: " + actulaFilterType[j],
									YesNo.No);
							k++;
						}
					}
					if (k == 0) {
						log(LogStatus.ERROR,
								"Expected filter type: " + filterValue[i] + " did not match in filter section",
								YesNo.No);
						result.add("Expected filter type: " + filterValue[i] + " did not match in filter section");
					}
				}

				xPath = "//section[contains(@class,'myfilter')]//input[@name='All']";
				ele = FindElement(driver, xPath, "All Record checkbox", action.BOOLEAN, 20);
				if (ele != null) {
					if (isSelected(driver, ele, "All types filter")) {
						if (click(driver, ele, "All types checkbox", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,
									"Click on the All types checkbox, so All types checkbox has been unselected",
									YesNo.No);
							for (int i = 1; i < filterValue.length; i++) {
								xPath = "//section[contains(@class,'myfilter')]//span[text()='" + filterValue[i]
										+ "']/../../input";
								ele = FindElement(driver, xPath, filterValue[i] + " checkbox", action.SCROLLANDBOOLEAN,
										20);
								if (click(driver, ele, filterValue[i] + " checkbox", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on the checkbox of " + filterValue[i], YesNo.No);

									xPath = "//p[text()='" + acuityDefaultMessage + "']";
									;
									ele = FindElement(driver, xPath, "No Item ", action.SCROLLANDBOOLEAN, 10);
									if (ele == null) {
										xPath = "//th[@data-label='Type']//lightning-icon";
										elements = FindElements(driver, xPath, "Icon");

										for (int j = 0; j < elements.size(); j++) {
											String iconType = getAttribute(driver, elements.get(j), "Icon class",
													"class");
											if (iconType.toLowerCase().trim()
													.contains(filterIconType[i - 1].toLowerCase().trim())) {
												log(LogStatus.INFO, filterValue[i]
														+ " filter has been verfied on Meetings and Calls popup",
														YesNo.No);
											} else {
												log(LogStatus.ERROR,
														filterValue[i]
																+ " filter are not verfied on Meetings and Calls popup",
														YesNo.No);
												result.add(filterValue[i]
														+ " filter are not verfied on Meetings and Calls popup");
											}
										}

										xPath = "//section[contains(@class,'myfilter')]//span[text()='" + filterValue[i]
												+ "']/../../input";
										ele = FindElement(driver, xPath, filterValue[i] + " checkbox",
												action.SCROLLANDBOOLEAN, 20);
										if (click(driver, ele, filterValue[i] + " checkbox", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on the checkbox of " + filterValue[i],
													YesNo.No);
										} else {
											log(LogStatus.ERROR,
													"Not able to click on the checkbox of " + filterValue[i] + ". So "
															+ filterValue[i] + " filter did not unselect",
													YesNo.No);
											result.add("Not able to click on the checkbox of " + filterValue[i]
													+ ". So " + filterValue[i] + " filter did not unselect");
										}

									} else {
										log(LogStatus.INFO,
												"records are not available on " + filterValue[i] + " filter", YesNo.No);
										xPath = "//section[contains(@class,'myfilter')]//span[text()='" + filterValue[i]
												+ "']/../../input";
										ele = FindElement(driver, xPath, filterValue[i] + " checkbox",
												action.SCROLLANDBOOLEAN, 20);
										if (click(driver, ele, filterValue[i] + " checkbox", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on the checkbox of " + filterValue[i],
													YesNo.No);
										} else {
											log(LogStatus.ERROR,
													"Not able to click on the checkbox of " + filterValue[i] + ". So "
															+ filterValue[i] + " filter did not unselect",
													YesNo.No);
											result.add("Not able to click on the checkbox of " + filterValue[i]
													+ ". So " + filterValue[i] + " filter did not unselect");
										}
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on the checkbox of " + filterValue[i],
											YesNo.No);
									result.add("Not able to click on the checkbox of " + filterValue[i]);
								}
							}
						} else {
							log(LogStatus.ERROR,
									"Not able to click on the All types checkbox, so All types checkbox selected",
									YesNo.No);
							result.add("Not able to click on the All types checkbox, so All types checkbox selected");
						}

					} else {
						log(LogStatus.ERROR, "Not able to get the element of All Types filter", YesNo.No);
						result.add("Not able to get the element of All Types filter");
					}
				}

				driver.close();
				driver.switchTo().window(parentId);

			} else {
				log(LogStatus.ERROR, "Not able to click on filter icon", YesNo.No);
				result.add("Not able to click on filter icon");
			}
		} else {
			log(LogStatus.ERROR, "Filter Icon does not visible on Meetings and Calls popup", YesNo.No);
			result.add("Filter Icon does not visible on Meetings and Calls popup");
		}

		return result;
	}

	public ArrayList<String> verifyRecordShouldNotVisibleOnTagged(String[] companyTag, String peopleTag[],
			String dealTag[], boolean isInstitutionRecord) {
		ArrayList<String> result = new ArrayList<String>();
		if (companyTag != null) {

			if (click(driver, getTaggedRecordName("Firms", 30), "Firms tab", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Firms tab name", YesNo.No);
				for (int i = 0; i < companyTag.length; i++) {
					if (getTaggedRecordName("Firms", companyTag[i], 10) == null) {
						log(LogStatus.INFO, companyTag[i] + " record is not available on company tab", YesNo.No);
					} else {
						log(LogStatus.ERROR, companyTag[i] + " record is available on company tab", YesNo.No);
						result.add(companyTag[i] + " record is available on company tab");
					}
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Firms tab name", YesNo.No);
				result.add("Not able to click on Firms tab name");
			}
		}
		if (peopleTag != null) {

			if (click(driver, getTaggedRecordName("People", 30), "People tab", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on People tab name", YesNo.No);

				for (int i = 0; i < peopleTag.length; i++) {
					if (getTaggedRecordName("People", peopleTag[i], 10) == null) {
						log(LogStatus.INFO, peopleTag[i] + " record is not available on people tab", YesNo.No);
					} else {
						log(LogStatus.ERROR, peopleTag[i] + " record is available on people tab", YesNo.No);
						result.add(peopleTag[i] + " record is available on people tab");
					}
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on People tab name", YesNo.No);
				result.add("Not able to click on People tab name");
			}
		}
		if (isInstitutionRecord == false) {
			if (dealTag != null) {

				if (click(driver, getTaggedRecordName("Deals", 30), "Deals tab", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Deals tab name", YesNo.No);

					for (int i = 0; i < dealTag.length; i++) {
						if (getTaggedRecordName("Deals", dealTag[i], 10) == null) {
							log(LogStatus.INFO, dealTag[i] + " record is not available on deal tab", YesNo.No);
						} else {
							log(LogStatus.ERROR, dealTag[i] + " record is available on deal tab", YesNo.No);
							result.add(dealTag[i] + " record is available on deal tab");
						}
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Deals tab name", YesNo.No);
					result.add("Not able to click on Deals tab name");
				}
			}
		}
		return result;
	}

	public List<String> verifyNotificationOptionsNotContainsInRecordDetailPage(String... eventName) {

		List<WebElement> notificationOptionsList = getNotificationOptions();

		List<String> negativeResults = new ArrayList<String>();

		if (notificationHeaderInRecordDetailsPage(5) != null) {
			log(LogStatus.PASS,
					"Notification Header is present there: " + notificationHeaderInRecordDetailsPage(5).getText(),
					YesNo.No);

		} else {
			log(LogStatus.FAIL, "Notification Header is not present there", YesNo.No);

		}
		List<String> notificationOptionsListInText = notificationOptionsList.stream()
				.map(x -> CommonLib.getText(driver, x, "Event Name", action.BOOLEAN)).collect(Collectors.toList());

		if (notificationOptionsListInText.size() != 0) {
			for (int i = 0; i < eventName.length; i++) {
				if (!notificationOptionsListInText.contains(eventName[i])) {

					log(LogStatus.PASS,
							"Event: " + eventName[i] + " is not present there in Notification Pane of HomePage",
							YesNo.No);
				}

				else {

					log(LogStatus.FAIL, "Event: " + eventName[i] + " is present there in Notification Pane of HomePage",
							YesNo.No);
					negativeResults
							.add("Event: " + eventName[i] + " is present there in Notification Pane of HomePage");
				}
			}
		} else {

			log(LogStatus.FAIL, "Either Notification Pane is not open or might be Locator gets changed", YesNo.No);

		}

		return negativeResults;

	}

	public boolean notificationIconCountAndActualCountMatchedOrNot() {

		boolean flag = false;
		CommonLib.refresh(driver);
		CommonLib.ThreadSleep(5000);
		Integer countIcon = Integer.valueOf(CommonLib.getText(driver, countOfAcuityNotificationInRecordDetailPage(20),
				"Count Notification Icon", action.BOOLEAN));

		// click on notification icon

		if (CommonLib.clickUsingJavaScript(driver, getNotificationIcon(), "NotificationIcon")) {

			log(LogStatus.INFO, "Clicked on Notification Icon", YesNo.No);
			CommonLib.ThreadSleep(4000);
			List<WebElement> notificationOptionsList = getNotificationOptions();
			Integer actualCount = notificationOptionsList.size();
			if (countIcon.equals(actualCount)) {

				log(LogStatus.INFO, "--------Icon Count and Actual Count in Notification Matched, i.e.:  " + countIcon
						+ "----------", YesNo.No);
				flag = true;

			} else {

				log(LogStatus.FAIL, "--------Icon Count and Actual Count in Notification not Matched, Icon Count: "
						+ countIcon + " & Actual Count: " + actualCount + "----------", YesNo.No);
				sa.assertTrue(false, "--------Icon Count and Actual Count in Notification not Matched, Icon Count: "
						+ countIcon + " & Actual Count: " + actualCount + "----------");
			}
		} else {
			log(LogStatus.FAIL, "Not able to click on Notification Icon", YesNo.No);
			sa.assertTrue(false, "Not able to click on Notification Icon");
		}

		return flag;

	}

	public String notificationEventNavigateToDetailPageOrNot(String eventName) {

		CommonLib.refresh(driver);
		CommonLib.ThreadSleep(5000);

		// click on notification icon

		if (CommonLib.clickUsingJavaScript(driver, getNotificationIcon(), "NotificationIcon")) {

			log(LogStatus.INFO, "Clicked on Notification Icon", YesNo.No);

			if (linkOfEventInNotificationOfRecordDetailPage(eventName, 12) != null) {
				log(LogStatus.INFO, "Clicked on Event: " + eventName + " from Record Page Notification", YesNo.No);

				if (click(driver, linkOfEventInNotificationOfRecordDetailPage(eventName, 8), "Event: " + eventName,
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Event: " + eventName + " from Record page Notification", YesNo.No);
					String parentId = CommonLib.switchOnWindow(driver);
					if (parentId != null) {
						log(LogStatus.INFO, "Switched to New Window", YesNo.No);
						if (eventDetailPageHeader(eventName, 15) != null) {
							log(LogStatus.INFO, "Header Found: " + eventName + " ,So Event Detail Page Open", YesNo.No);
							return parentId;
						} else {
							log(LogStatus.FAIL,
									"Header not Found: " + eventName + " ,So Event Detail Page has not Open", YesNo.No);
							sa.assertTrue(false,
									"Header not Found: " + eventName + " ,So Event Detail Page has not Open");
							return null;
						}
					} else {
						log(LogStatus.FAIL, "No New window open after click on Event name: " + eventName, YesNo.No);
						sa.assertTrue(false, "No New window open after click on Event name: " + eventName);
						return null;
					}

				} else {

					log(LogStatus.FAIL, "Not able to Click on Event: " + eventName + " from Record Page Notification",
							YesNo.No);
					sa.assertTrue(false, "Not able to Click on Event: " + eventName + " from Record Page Notification");
					return null;
				}

			} else {

				log(LogStatus.FAIL, "No Link found of Evenet named: " + eventName + " in Notification Pane", YesNo.No);
				sa.assertTrue(false, "No Link found of Evenet named: " + eventName + " in Notification Pane");
				return null;
			}
		} else {
			log(LogStatus.FAIL, "Not able to click on Notification Icon", YesNo.No);
			sa.assertTrue(false, "Not able to click on Notification Icon");
			return null;
		}

	}

	public ArrayList<String> verifyRedirectionOnClickEntityTypeOnTaggedSection(boolean isInstitutionRecord) {

		ArrayList<String> result = new ArrayList<String>();

		if (click(driver, getTaggedRecordName(TaggedName.Firms.toString(), 30), TaggedName.Firms.toString() + " tab",
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Firms tab name", YesNo.No);
			ThreadSleep(5000);
			if (CommonLib.clickUsingJavaScript(driver, recordsNameOnTaggedSection(TaggedName.Firms.toString(), 30),
					"Records on firm Tagged", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on record on firm tab", YesNo.No);

				String id = switchOnWindow(driver);
				if (id != null) {
					if (getTabName("Firm", 20) != null) {
						log(LogStatus.INFO, "The page is redirecting to firm tab after click on Entity type of firm",
								YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"The page is not redirecting to firm tab after click on Entity type of firm", YesNo.No);
						result.add("The page is not redirecting to firm tab after click on Entity type of firm");
					}
					driver.close();
					driver.switchTo().window(id);
				} else {
					log(LogStatus.ERROR, "The new tab is not opening after clicking on entity type of firm", YesNo.No);
					result.add("The new tab is not opening after clicking on entity type of firm");
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on record on firm tab", YesNo.No);
				result.add("Not able to click on record on firm tab");
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on firm tab name", YesNo.No);
			result.add("Not able to click on firm tab name");
		}

		if (click(driver, getTaggedRecordName(TaggedName.People.toString(), 30), TaggedName.People.toString() + " tab",
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on People tab name", YesNo.No);
			ThreadSleep(5000);
			if (CommonLib.clickUsingJavaScript(driver, recordsNameOnTaggedSection(TaggedName.People.toString(), 30),
					"Records on People Tagged", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on record on People tab", YesNo.No);

				String id = switchOnWindow(driver);
				if (id != null) {
					if (getTabName("Contact", 20) != null) {
						log(LogStatus.INFO,
								"The page is redirecting to Contact tab after click on Entity type of People",
								YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"The page is not redirecting to Contact tab after click on Entity type of people",
								YesNo.No);
						result.add("The page is not redirecting to Contact tab after click on Entity type of people");
					}
					driver.close();
					driver.switchTo().window(id);
				} else {
					log(LogStatus.ERROR, "The new tab is not opening after clicking on entity type of People",
							YesNo.No);
					result.add("The new tab is not opening after clicking on entity type of people");
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on record of people tab name", YesNo.No);
				result.add("Not able to click on record of people tab name");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Companies tab name", YesNo.No);
			result.add("Not able to click on Companies tab name");
		}

		if (isInstitutionRecord == false) {
			if (click(driver, getTaggedRecordName(TaggedName.Deals.toString(), 30),
					TaggedName.Deals.toString() + " tab", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Deal tab name", YesNo.No);
				ThreadSleep(5000);
				if (CommonLib.clickUsingJavaScript(driver, recordsNameOnTaggedSection(TaggedName.Deals.toString(), 30),
						"Records on Deal Tagged", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on record on deal tab", YesNo.No);

					String id = switchOnWindow(driver);
					if (id != null) {
						if (getTabName("Deal", 20) != null) {
							log(LogStatus.INFO,
									"The page is redirecting to deal tab after click on Entity type of deal", YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"The page is not redirecting to Deal tab after click on Entity type of deal",
									YesNo.No);
							result.add("The page is not redirecting to Deal tab after click on Entity type of deal");
						}
						driver.close();
						driver.switchTo().window(id);
					} else {
						log(LogStatus.ERROR, "The new tab is not opening after clicking on entity type of Deal",
								YesNo.No);
						result.add("The new tab is not opening after clicking on entity type of Deal");
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on record of deal tab name", YesNo.No);
					result.add("Not able to click on record of deal tab name");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Deal tab name", YesNo.No);
				result.add("Not able to click on Deal tab name");
			}
		}
		return result;
	}

	public ArrayList<String> verifyDescriptionShouldNotVisibleUnderDetailsOnInteractionSection(String[] subjectName,
			String[] detailsMessage) {
		String id = switchOnWindow(driver);
		ArrayList<String> result = new ArrayList<String>();
		if (subjectName.length == detailsMessage.length) {
			for (int i = 0; i < subjectName.length; i++) {
				String actualDetails = getText(driver, getDetailsOnInteractionPage(subjectName[i], 30),
						" details of subject : " + subjectName[i], action.SCROLLANDBOOLEAN);
				if (!actualDetails.contains(detailsMessage[i])) {
					log(LogStatus.INFO, "Details : " + detailsMessage[i]
							+ " is not available on Interaction popup for subject " + subjectName[i], YesNo.No);
				} else {
					log(LogStatus.ERROR, "Details : " + detailsMessage[i]
							+ " is available on Interaction popup for subject " + subjectName[i], YesNo.No);
					result.add("Details : " + detailsMessage[i] + " is available on Interaction popup for subject "
							+ subjectName[i]);
				}

			}

		}
		driver.close();
		driver.switchTo().window(id);
		return result;

	}

	public ArrayList<String> verifyHighlightedCompanyNameOnCompanyTaggedSection(TaggedName tabName,
			String[] highlightedRecord) {
		ArrayList<String> result = new ArrayList<String>();
		if (click(driver, getTaggedRecordName(tabName.toString(), 30), tabName.toString() + " tab",
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on " + tabName.toString() + " tab name", YesNo.No);

			for (int i = 0; i < highlightedRecord.length; i++) {
				if (getHighlightedCompanyName(highlightedRecord[i], 15) != null) {
					log(LogStatus.INFO, highlightedRecord[i] + " is highlighted in " + tabName.toString() + " list",
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							highlightedRecord[i] + " is not highlighted in " + tabName.toString() + " list", YesNo.No);
					result.add(highlightedRecord[i] + " is not highlighted in " + tabName.toString() + " list");
				}
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabName.toString() + " tab name", YesNo.No);
			result.add("Not able to click on " + tabName.toString() + " tab name");
		}
		return result;
	}

	public boolean verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(String subjectName) {
		boolean flag = false;
		if (subjectOfInteractionCard(subjectName, 6) != null) {

			log(LogStatus.INFO, subjectName + " Found on Interaction Card", YesNo.No);

			flag = true;

		} else {

			log(LogStatus.INFO,
					subjectName + " has not found in Interaction Cards, So going to check in View All PopUp", YesNo.No);
			if (getInteractionViewAllBtn(8) != null) {

				if (click(driver, getInteractionViewAllBtn(8), "View All Button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on View All Button", YesNo.No);

					String parentID = CommonLib.switchOnWindow(driver);

					if (parentID != null) {

						if (CommonLib.sendKeysAndPressEnter(driver, allInteractionSearchBox(15), subjectName,
								"Subject Pass: " + subjectName, action.SCROLLANDBOOLEAN)) {

							if (getActivityTimelineSubjectinAllIntearctionPage(subjectName, 7) != null) {
								log(LogStatus.INFO, "Subject: " + subjectName + " found on All Interaction Page",
										YesNo.No);

								flag = true;

							} else {
								log(LogStatus.ERROR, "Subject: " + subjectName + " not found on All Interaction Page",
										YesNo.Yes);

							}

						} else {
							log(LogStatus.ERROR, "Subject not Passed " + subjectName + " to the Search box", YesNo.Yes);
						}

						driver.close();
						driver.switchTo().window(parentID);

					}

					else {
						log(LogStatus.ERROR,
								"No New Window Open after click on View All Button for Subject: " + subjectName,
								YesNo.Yes);
					}

				} else {
					log(LogStatus.ERROR, "Not able to Click on View All Button", YesNo.Yes);

				}

			} else {
				log(LogStatus.ERROR, "View All Button is not present, So not able to Click on Subject: " + subjectName,
						YesNo.Yes);
			}
		}
		return flag;
	}

	public boolean verifyDescriptionShouldNotVisibleOnInteractionCard(String subjectName, String detailsMessage) {
		boolean flag = false;
		String actualDetails = getText(driver, getDetailsOnInteractionCard(subjectName, 30),
				" details of subject : " + subjectName, action.SCROLLANDBOOLEAN);
		if (!actualDetails.contains(detailsMessage)) {
			log(LogStatus.INFO,
					"Details : " + detailsMessage + " is not available on Interaction popup for subject " + subjectName,
					YesNo.No);
			flag = true;

		} else {
			log(LogStatus.ERROR,
					"Details : " + detailsMessage + " is available on Interaction popup for subject " + subjectName,
					YesNo.No);

		}
		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param projectName
	 * @param listViewName
	 * @param timeOut
	 */
	public ArrayList<String> verifySubjectLinkPopUpOnIntraction(WebDriver driver, String subjectName,
			String[][] basicSectionVerificationData, String[][] advancedSectionVerificationData, IconType icon,
			PageName pageName) {
		ArrayList<String> negativeResult = new ArrayList<String>();

		if (pageName.toString().equals("AcuityDetails")) {
			if (CommonLib.click(driver, subjectOfInteractionCard(subjectName, 15), "Subject Name on Intraction",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "clicked on " + subjectName, YesNo.No);

			} else {
				log(LogStatus.ERROR, "not able to click on " + subjectName, YesNo.No);
				negativeResult.add("not able to click on " + subjectName);
			}
		} else if (pageName.toString().equals("Interaction")) {
			if (CommonLib.click(driver, subjectOfInteractionPage(subjectName, 15), "Subject Name on Intraction",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "clicked on " + subjectName, YesNo.No);

			} else {
				log(LogStatus.ERROR, "not able to click on " + subjectName, YesNo.No);
				negativeResult.add("not able to click on " + subjectName);
			}
		}

		if (activitySubjetLinkPopupHeaderOnInteraction(5) != null) {

			if (editButtonOfSubjectLinkPopUpInInteractionSection(5) != null) {

				log(LogStatus.INFO, "Edit Button Verified", YesNo.No);
			} else {
				log(LogStatus.ERROR, "Edit Button not Verified", YesNo.No);
				negativeResult.add("Edit Button not Verified");
			}

			if (icon.equals(IconType.Task)) {

				String type = CommonLib.getText(driver, typeOfSubjectLinkPopUpInInteractionSection(5), "Type",
						action.BOOLEAN);

				if (type.equals(IconType.Task.toString())) {

					log(LogStatus.INFO, "Type has been verified and i.e.: " + type, YesNo.No);
				} else {
					log(LogStatus.ERROR, "Type has not been verified, Expected: " + IconType.Task.toString()
							+ " but Actual: " + type, YesNo.No);
					negativeResult.add("Type has not been verified, Expected: " + IconType.Task.toString()
							+ " but Actual: " + type);
				}

				WebElement iconLocator = iconOfSubjectLinkPopUpInInteractionSection(7);
				if (iconLocator != null) {
					String attribute = iconLocator.getAttribute("data-key");
					if (attribute.equalsIgnoreCase("task")) {

						log(LogStatus.INFO, "Icon verified for Subject link Popup & i.e.: " + attribute, YesNo.No);
					}

					else {
						log(LogStatus.ERROR, "Icon not verified for Subject link Popup Expected: " + icon.toString()
								+ " but Actual: " + attribute, YesNo.No);
						negativeResult.add("Icon not verified for Subject link Popup Expected: " + icon.toString()
								+ " but Actual: " + attribute);
					}
				}

				else {
					log(LogStatus.ERROR, " Locator of Icon is not correct", YesNo.No);
					negativeResult.add(" Locator of Icon is not correct");
				}

			} else if (icon.equals(IconType.Call)) {

				String type = CommonLib.getText(driver, typeOfSubjectLinkPopUpInInteractionSection(5), "Type",
						action.BOOLEAN);

				if (type.equals(IconType.Call.toString())) {

					log(LogStatus.INFO, "Type has been verified and i.e.: " + type, YesNo.No);
				} else {
					log(LogStatus.ERROR, "Type has not been verified, Expected: " + IconType.Call.toString()
							+ " but Actual: " + type, YesNo.No);
					negativeResult.add("Type has not been verified, Expected: " + IconType.Call.toString()
							+ " but Actual: " + type);
				}

				WebElement iconLocator = iconOfSubjectLinkPopUpInInteractionSection(7);
				if (iconLocator != null) {
					String attribute = iconLocator.getAttribute("data-key");
					if (attribute.equalsIgnoreCase("log_a_call")) {

						log(LogStatus.INFO, "Icon verified for Subject link Popup & i.e.: " + attribute, YesNo.No);
					}

					else {
						log(LogStatus.ERROR, "Icon not verified for Subject link Popup Expected: " + icon.toString()
								+ " but Actual: " + attribute, YesNo.No);
						negativeResult.add("Icon not verified for Subject link Popup Expected: " + icon.toString()
								+ " but Actual: " + attribute);
					}
				}

				else {
					log(LogStatus.ERROR, " Locator of Icon is not correct", YesNo.No);
					negativeResult.add(" Locator of Icon is not correct");
				}

			} else if (icon.equals(IconType.Event) || icon.equals(IconType.Meeting)) {

				String type = CommonLib.getText(driver, typeOfSubjectLinkPopUpInInteractionSection(5), "Type",
						action.BOOLEAN);

				if (type.equals(IconType.Meeting.toString())) {

					log(LogStatus.INFO, "Type has been verified and i.e.: " + type, YesNo.No);
				} else {
					log(LogStatus.ERROR, "Type has not been verified, Expected: " + IconType.Meeting.toString()
							+ " but Actual: " + type, YesNo.No);
					negativeResult.add("Type has not been verified, Expected: " + IconType.Meeting.toString()
							+ " but Actual: " + type);
				}

				WebElement iconLocator = iconOfSubjectLinkPopUpInInteractionSection(7);
				if (iconLocator != null) {
					String attribute = iconLocator.getAttribute("data-key");
					if (attribute.equalsIgnoreCase("event")) {

						log(LogStatus.INFO, "Icon verified for Subject link Popup & i.e.: " + attribute, YesNo.No);
					}

					else {
						log(LogStatus.ERROR, "Icon not verified for Subject link Popup Expected: " + icon.toString()
								+ " but Actual: " + attribute, YesNo.No);
						negativeResult.add("Icon not verified for Subject link Popup Expected: " + icon.toString()
								+ " but Actual: " + attribute);
					}
				}

				else {
					log(LogStatus.ERROR, " Locator of Icon is not correct", YesNo.No);
					negativeResult.add(" Locator of Icon is not correct");
				}

			}

			else if (icon.equals(IconType.Email)) {

				String type = CommonLib.getText(driver, typeOfSubjectLinkPopUpInInteractionSection(5), "Type",
						action.BOOLEAN);

				if (type.equals(IconType.Email.toString())) {

					log(LogStatus.INFO, "Type has been verified and i.e.: " + type, YesNo.No);
				} else {
					log(LogStatus.ERROR, "Type has not been verified, Expected: " + IconType.Email.toString()
							+ " but Actual: " + type, YesNo.No);
					negativeResult.add("Type has not been verified, Expected: " + IconType.Email.toString()
							+ " but Actual: " + type);
				}

				WebElement iconLocator = iconOfSubjectLinkPopUpInInteractionSection(7);
				if (iconLocator != null) {
					String attribute = iconLocator.getAttribute("data-key");
					if (attribute.equalsIgnoreCase("email")) {

						log(LogStatus.INFO, "Icon verified for Subject link Popup & i.e.: " + attribute, YesNo.No);
					}

					else {
						log(LogStatus.ERROR, "Icon not verified for Subject link Popup Expected: " + icon.toString()
								+ " but Actual: " + attribute, YesNo.No);
						negativeResult.add("Icon not verified for Subject link Popup Expected: " + icon.toString()
								+ " but Actual: " + attribute);
					}
				}

				else {
					log(LogStatus.ERROR, " Locator of Icon is not correct", YesNo.No);
					negativeResult.add(" Locator of Icon is not correct");
				}

			} else {
				log(LogStatus.ERROR, "Please provide correct Icon Type of Subject Link popup as it is mandatory",
						YesNo.No);
				negativeResult.add("Please provide correct Icon Type of Subject Link popup as it is mandatory");
			}

			if (basicSectionVerificationData != null) {

				for (String[] val : basicSectionVerificationData) {
					String labelName = val[0];
					String value = val[1];

					if (labelName.contains(excelLabel.Notes.toString())
							|| labelName.equalsIgnoreCase(excelLabel.Related_To.toString())
							|| labelName.equalsIgnoreCase("Tags")
							|| labelName.contains(excelLabel.Subject.toString())) {

						if (labelName.equalsIgnoreCase(excelLabel.Related_To.toString())) {
							labelName = "Tags";
						}

						if (labelName.equalsIgnoreCase("Tags")) {

							if (!value.contains("==")) {

								String[] tag = value.split("<break>", -1);
								List<String> taggedRelatedToListInNotePopUp = valueOfTagsInSubjectLinkPopUpInInteractionSection(
										labelName);
								for (int i = 0; i < tag.length; i++) {

									if (taggedRelatedToListInNotePopUp.contains(tag[i])) {
										log(LogStatus.INFO,
												tag[i] + " tag has been verified in Subject Link PopUp of Interaction",
												YesNo.No);
									} else {
										log(LogStatus.ERROR, tag[i]
												+ " tag has not been verified in Subject Link PopUp of Interaction",
												YesNo.No);
										negativeResult.add(tag[i]
												+ " tag has not been verified in Subject Link PopUp of Interaction");
									}
								}
							} else {

								String[] tag = value.split("<break>", -1);

								for (int i = 0; i < tag.length; i++) {
									String[] relatedAndIcon = tag[i].split("==", -1);
									String relatedAssociation = relatedAndIcon[0];
									String relatedAssociationIcon = relatedAndIcon[1];

									if (iconOfTagsInSubjectLinkPopUpInInteractionSection(labelName, relatedAssociation,
											relatedAssociationIcon, 4) != null) {
										log(LogStatus.INFO,

												relatedAssociation
														+ " tag has been verified in Subject Link PopUp of Interaction with Icon: "
														+ relatedAssociationIcon,
												YesNo.No);
									} else {
										log(LogStatus.ERROR, relatedAssociation
												+ " tag has not been verified in Subject Link PopUp of Interaction with Icon: "
												+ relatedAssociationIcon, YesNo.No);
										negativeResult.add(relatedAssociation
												+ " tag has not been verified in Subject Link PopUp of Interaction with Icon: "
												+ relatedAssociationIcon);
									}
								}

							}

						}

						else if (labelName.contains(excelLabel.Subject.toString())) {
							if (activitySubjetLinkPopupHeaderOnInteraction(10).getText().equals(value)) {
								log(LogStatus.INFO, value + " link Popup header has been verified", YesNo.No);

							} else {
								log(LogStatus.ERROR, value + " link Popup header has not been verified", YesNo.No);
								negativeResult.add(value + " link Popup header has not been verified");

							}
						}

						else {
							String actualValue = getText(driver,
									valueOfLabelInSubjectLinkPopUpInInteractionSection(labelName, 7), labelName,
									action.SCROLLANDBOOLEAN);

							log(LogStatus.INFO, "Successfully get the value from " + labelName + " field", YesNo.No);
							if (value.equals(actualValue)) {
								log(LogStatus.INFO, labelName
										+ " label's value has been verify in Subject link Popup and i.e. :" + value,
										YesNo.No);
							} else {
								log(LogStatus.ERROR, labelName + " label's value is not verify, Expected: " + value
										+ " but Actual: " + actualValue, YesNo.No);
								negativeResult.add(labelName + " label's value is not verify, Expected: " + value
										+ " but Actual: " + actualValue);
							}

						}

					} else {
						log(LogStatus.ERROR, "Please Provide the Correct Label Name: " + labelName, YesNo.No);
						negativeResult.add("Please Provide the Correct Label Name: " + labelName);
					}

				}

			}

			if (advancedSectionVerificationData != null) {

				for (String[] val : advancedSectionVerificationData) {
					String labelName = val[0];
					String value = val[1];

					if (labelName.contains("User") || labelName.contains(excelLabel.Status.toString())
							|| labelName.contains("Due Date") || labelName.contains("Date")
							|| labelName.contains(excelLabel.Priority.toString())) {

						if (icon.equals(IconType.Task) && (labelName.contains(excelLabel.Status.toString())
								|| labelName.contains(excelLabel.Priority.toString()))) {

							String actualValue = getText(driver,
									valueOfLabelInSubjectLinkPopUpInInteractionSection(labelName, 7), labelName,
									action.SCROLLANDBOOLEAN);

							log(LogStatus.INFO, "Successfully get the value from " + labelName + " field", YesNo.No);
							if (value.equals(actualValue)) {
								log(LogStatus.INFO, labelName
										+ " label's value has been verify in Subject link Popup and i.e. :" + value,
										YesNo.No);
							} else {
								log(LogStatus.ERROR, labelName + " label's value is not verify, Expected: " + value
										+ " but Actual: " + actualValue, YesNo.No);
								negativeResult.add(labelName + " label's value is not verify, Expected: " + value
										+ " but Actual: " + actualValue);
							}

						}

					}

					else {
						log(LogStatus.ERROR, "Please Provide the Correct Label Name: " + labelName, YesNo.No);
						negativeResult.add("Please Provide the Correct Label Name: " + labelName);
					}
				}

			}

			if (click(driver, crossButtonOfSubjectLinkPopUpInInteractionSection(8),
					"crossButtonOfSubjectLinkPopUpInInteractionSection", action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Cross Button of Subject Link Pop up of Subject: " + subjectName,
						YesNo.No);

				if (activitySubjetLinkPopupHeaderOnInteraction(5) == null) {

					log(LogStatus.INFO,
							"Verified Subject Link Pop up has been closed after click on Cross button for Subject: "
									+ subjectName,
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Subject Link Pop up has not been closed after click on Cross button for Subject: "
									+ subjectName,
							YesNo.No);
					negativeResult
							.add("Subject Link Pop up has not been closed after click on Cross button for Subject: "
									+ subjectName);
				}

			} else {
				log(LogStatus.ERROR,
						"Not able to Click on Cross Button of Subject Link Pop up of Subject: " + subjectName,
						YesNo.No);
				negativeResult
						.add("Not able to Click on Cross Button of Subject Link Pop up of Subject: " + subjectName);
			}

		} else {
			log(LogStatus.ERROR, subjectName + " link Popup has not been Open", YesNo.No);
			negativeResult.add(subjectName + " link Popup has not been Open");
		}

		return negativeResult;
	}

	public boolean navigateToRecordAndClickOnSubTab(String projectName, String tabName, String recordName,
			String subTabName) {
		boolean flag = false;

		if (tabName.equalsIgnoreCase(tabObj1))
			if (clickOnTab(projectName, tabName)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabName, YesNo.No);

				if (clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, recordName, 30)) {
					log(LogStatus.INFO, recordName + " record of Firm has been open", YesNo.No);

					if (!"".equalsIgnoreCase(subTabName) && subTabName != null) {
						if (clicktabOnPage(subTabName)) {
							log(LogStatus.PASS, "Clicked on SubTab: " + subTabName, YesNo.No);
							flag = true;
						} else {
							log(LogStatus.ERROR, "Not able to click on SubTab: " + subTabName, YesNo.No);

						}
					} else {
						flag = true;
					}

				} else {
					log(LogStatus.ERROR, "Not able to open " + recordName + " record of Firm", YesNo.No);

				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabName, YesNo.No);

			}
		else if (tabName.equalsIgnoreCase(tabObj2))
			if (clickOnTab(projectName, tabName)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabName, YesNo.No);

				if (clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
					log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
					if (!"".equalsIgnoreCase(subTabName) && subTabName != null) {
						if (clicktabOnPage(subTabName)) {
							log(LogStatus.PASS, "Clicked on SubTab: " + subTabName, YesNo.No);
							flag = true;
						} else {
							log(LogStatus.ERROR, "Not able to click on SubTab: " + subTabName, YesNo.No);

						}
					} else {
						flag = true;
					}

				} else {
					log(LogStatus.ERROR, "Not able to open " + recordName + " record", YesNo.No);

				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Tab : " + tabName, YesNo.No);

			}

		return flag;
	}

	public void verifyUIOfTaskPopUp(String url, String[][] basicSectionVerificationData,
			String[][] advancedSectionVerificationData, String[][] tasksSectionVerificationData) {
		String expectedHeaderName = "Task";
		List<String> expectedFooterList = new ArrayList<String>();
		expectedFooterList.add("Cancel");
		expectedFooterList.add("Save");
		List<String> expectedSubjectList = new ArrayList<String>();
		expectedSubjectList.add("-None-");
		expectedSubjectList.add("Call");
		expectedSubjectList.add("Send Letter");
		expectedSubjectList.add("Send Quote");
		expectedSubjectList.add("Other");
		if (notePopUpHeading(expectedHeaderName, 15) != null) {
			log(LogStatus.INFO, "PopUp Name has been verified to: " + expectedHeaderName, YesNo.No);
		}

		else {
			log(LogStatus.ERROR, "PopUp Name has been not been verified, Expected: " + expectedHeaderName, YesNo.No);
			sa.assertTrue(false, "PopUp Name has been not been verified, Expected: " + expectedHeaderName);
		}

		if (notePopUpCrossButton(7) != null) {
			log(LogStatus.INFO, "Cross Button is visible in " + expectedHeaderName + " Popup", YesNo.No);
		}

		else {
			log(LogStatus.ERROR, "Cross Button is not visible in " + expectedHeaderName + " Popup", YesNo.No);
			sa.assertTrue(false, "Cross Button is not visible in " + expectedHeaderName + " Popup");
		}

		if (notePopUpAddMoreButton(7) != null) {
			log(LogStatus.INFO, "Add More Button is present in " + expectedHeaderName + " Popup", YesNo.No);
		}

		else {
			log(LogStatus.ERROR, "Add More Button is not present in " + expectedHeaderName + " Popup", YesNo.No);
			sa.assertTrue(false, "Add More Button is not present in " + expectedHeaderName + " Popup");
		}

		List<String> actualFooterList = notePopUpFooterButtons().stream()
				.map(x -> CommonLib.getText(driver, x, "Footer", action.BOOLEAN)).collect(Collectors.toList());

		if (actualFooterList.containsAll(expectedFooterList)) {
			log(LogStatus.INFO, "Footer List Matched: " + expectedFooterList, YesNo.No);

		} else {
			log(LogStatus.ERROR,
					"Footer List not Matched, Expected: " + expectedFooterList + ", Actual: " + expectedFooterList,
					YesNo.No);
			sa.assertTrue(false,
					"Footer List not Matched, Expected: " + expectedFooterList + ", Actual: " + expectedFooterList);
		}

		if (basicSectionVerificationData != null) {

			for (String[] val : basicSectionVerificationData) {
				String labelName = val[0];

				if (labelName.contains(excelLabel.Subject.toString())) {

					if (click(driver, getSubjectInput(labelName, 10), "Subject Input", action.BOOLEAN)) {
						log(LogStatus.INFO, "successfully click on " + labelName, YesNo.No);

						List<String> actualSubjectValues = getAllValuesOfSubjectInTaskPopUp();
						if (actualSubjectValues.containsAll(expectedSubjectList)) {
							log(LogStatus.INFO,
									"All Values comes under Subject has been matched: " + expectedSubjectList,
									YesNo.No);
						} else {
							log(LogStatus.ERROR, "All Values comes under Subject has not been matched, Expected: "
									+ expectedSubjectList + " but Actual: " + actualSubjectValues, YesNo.Yes);
							sa.assertTrue(false, "All Values comes under Subject has not been matched, Expected: "
									+ expectedSubjectList + " but Actual: " + actualSubjectValues);
						}

					} else {
						log(LogStatus.ERROR, "Not successfully click on " + labelName, YesNo.Yes);
						sa.assertTrue(false, "Not successfully click on " + labelName);
					}
				}

			}

		}

		ArrayList<String> NotesPopUpPrefilledNegativeResult = verifyNotesPopupWithPrefilledValueAndOnSameUrl(url,
				basicSectionVerificationData, advancedSectionVerificationData, tasksSectionVerificationData);
		if (NotesPopUpPrefilledNegativeResult.isEmpty()) {
			log(LogStatus.INFO,
					"Notes Popup has been verified and Notes popup is opening in same page with prefilled value",
					YesNo.No);

		} else {
			log(LogStatus.ERROR,
					"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
							+ NotesPopUpPrefilledNegativeResult,
					YesNo.No);
			sa.assertTrue(false,
					"Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value, Reason: "
							+ NotesPopUpPrefilledNegativeResult);
		}
	}

}
