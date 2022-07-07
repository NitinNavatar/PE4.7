/**
 * 
 */
package com.navatar.pageObjects;

import org.apache.poi.hssf.view.brush.PendingPaintings;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
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
import com.navatar.generic.CommonVariables;

import static com.navatar.generic.AppListeners.*;
import static com.navatar.generic.BaseLib.sa;
import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.environment;
import static com.navatar.generic.CommonVariables.mode;

import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;
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
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
			viewList = "All Institutions";
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
		if (fieldlabel.equalsIgnoreCase("Assigned To")
				&& PageName.TaskPage.toString().equalsIgnoreCase(pageName.toString())) {

			xpath = "//span[text()='" + fieldlabel + "']/../following-sibling::div//li/a/span[text()='" + name
					+ "']/following-sibling::a";
			ele = FindElement(driver, xpath, "Cross Button For  : " + name + " For Label : " + fieldlabel, action,
					timeOut);
			return ele;
		}
		if (label.equalsIgnoreCase(PageLabel.Name.toString()) || label.equalsIgnoreCase("Assigned To"))
			isMultipleAssociation = true;
		if (PageName.CallPopUp.toString().equalsIgnoreCase(pageName.toString())
				|| (PageLabel.Name.toString().equalsIgnoreCase(label)
						&& PageName.TaskPage.toString().equalsIgnoreCase(pageName.toString())
						&& isMultipleAssociation)) {
			xpath = "//span[text()='" + fieldlabel + "']/../following-sibling::div//li/a/span[text()='" + name
					+ "']/following-sibling::a";
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
			xpath = "//label[text()='" + label + "']/..//span[@title='" + value + "']";
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
		getLabelTextBox(projectName, PageName.TaskPage.toString(), PageLabel.Subject.toString(), timeOut).clear();
		ThreadSleep(3000);
		if (sendKeys(driver,
				getLabelTextBox(projectName, PageName.TaskPage.toString(), PageLabel.Subject.toString(), timeOut),
				subjectText, "Subject", action)) {
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
		xpath = "//div[@class='slds-card-wrapper activityPanel']";
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
			type = "upcoming";
		} else if (activityType == ActivityType.Past) {
			type = "past";
		}

		String nextStepsXpath = "//div[contains(@id,'" + type + "-activities-section')]";

		String subjectXpath = nextStepsXpath + "//a[@title='" + subject + "']";

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
			String assignedToxpath = subjectXpath + "/../..//following-sibling::p";
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
		} else if (TabName.contains("Funds")) {
			tabName = "Funds";
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
			xpath = "//li[@title='" + related + "']//a";
		else
			xpath = "//li//*[@title='" + related + "' or text()='" + related + "']";
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

			xpath = "//a/span[text()='" + related + "']";
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
			xpath = "//header//a[text()='" + toggleTab + "' or @title='" + toggleTab + "']";
		} else {
			xpath = "//header//a[text()='" + toggleTab + "' or @title='" + toggleTab + "']";
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
		xpath = "//*[contains(text(),'" + head + "')]/..//*[text()='" + itemName + "']";
		ele = FindElement(driver, xpath, "Header : " + itemName, action.BOOLEAN, 30);
		ele = isDisplayed(driver, ele, "Visibility", 10, head + " : " + itemName);
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
		String xpath = "//*[text()='" + labelName + "']//following-sibling::div//input";
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
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-7:00"));
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
		String xpath = "//a[text()='" + taskName + "']";
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
			if (click(driver, getCalenderCellIcon(30), "View Calender Icon", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Click on Calender Cell Icon", YesNo.No);
				ThreadSleep(5000);
				flag = true;
			} else {
				log(LogStatus.SKIP, "Not Able to Click on Calender Cell Icon", YesNo.Yes);
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
				xpath = "//div[contains(@id,'upcoming-activities')]//a[text()='" + subject + "']";
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
				xpath = "//div[contains(@id,'upcoming-activities')]//a[text()='" + subject + "']";
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
		if (tabName.toString().equalsIgnoreCase(TabName.ContactTab.toString())) {
			if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
				xpath = "//h3[text()='Activity History']/ancestor::div[@class='bRelatedList']//div[@class='pbBody']//tr//th/a[contains(text(),'"
						+ subject + "')]/../following-sibling::td/a[text()='" + relatedTo + "']";
				ele = FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, 10);
			} else {
				xpath = "//div[contains(@class,'past-activity')]//a[text()='" + subject + "']";
				ele = FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, 10);
			}
		} else if (tabName.toString().equalsIgnoreCase(TabName.InstituitonsTab.toString())) {
			if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
				xpath = "//h3[text()='Activity History']/ancestor::div[@class='bRelatedList']//div[@class='pbBody']//tr//th/a[text()='"
						+ subject + "']/../following-sibling::td/a[text()='" + contactName
						+ "']/../following-sibling::td/a[text()='" + relatedTo + "']";
				ele = FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, 10);
			} else {
				xpath = "//div[contains(@class,'past-activity')]//a[text()='" + subject + "']";
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
				if (click(driver, getdoneButtonListView(projectName, timeOut), "done", action.BOOLEAN)) {
					log(LogStatus.INFO, "successfully click on done buton", YesNo.No);

					String[] filters = listViewDataRowWise[5].split("<Break>");
					String[] operators = listViewDataRowWise[6].split("<Break>");
					String[] filtervalues = listViewDataRowWise[7].split("<Break>");

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

									if (filter.trim().equalsIgnoreCase("Vintage Year")
											|| filter.trim().equalsIgnoreCase("1st Closing Date")
											|| filter.equals("Fund Name") || filter.equals("Record Name")) {
										if (sendKeys(driver, getfilterValuefield(projectName, timeOut), filtervalues[i],
												"value", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "value has been entered", YesNo.No);

										} else {
											log(LogStatus.ERROR, "Value is not Entered", YesNo.No);
											sa.assertTrue(false, "Value is not Entered");

										}

									}

									else if (filter.equals("Fund Type") || filter.equals("Investment Category")
											|| filter.equals("Stage")) {
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
			ThreadSleep(1000);
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
			if (click(driver, getallCheckboxForFilter(projectName, timeOut), "all filters", action.BOOLEAN)) {
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

}