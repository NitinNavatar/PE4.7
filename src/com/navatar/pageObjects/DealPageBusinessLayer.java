package com.navatar.pageObjects;

import static com.navatar.generic.AppListeners.appLog;
import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.ToggleDeal1;
import static com.navatar.generic.CommonVariables.tabObj4;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EnumConstants.ContactPagePhotoActions;
import com.navatar.generic.EnumConstants.InstitutionPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.relevantcodes.extentreports.LogStatus;
import static com.navatar.generic.CommonVariables.*;

public class DealPageBusinessLayer extends DealPage implements DealPageErrorMessage {

	public DealPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param requestID
	 * @param dateRequested
	 * @param request
	 * @param status
	 * @param timeOut
	 * @return true if successfully verified open request
	 */
	public boolean verifyingOpenRequest(String projectName, String requestID, String dateRequested, String request,
			String status, int timeOut) {

		log(LogStatus.INFO, "Going to verify Open Request with information as: " + requestID + " >> " + dateRequested
				+ " >> " + request + " >> " + status, YesNo.No);
		boolean flag = false;

		String requestIdXpath = "//*[contains(text(),'" + requestID + "')]/../../../..";
		String dateRequestedXpath = "/following-sibling::td//*[text()='" + dateRequested + "']/../../..";
		String requestXpath = "/following-sibling::td//*[text()='" + request + "']/../../..";
		String statusXpath = "/following-sibling::td//*[text()='" + status + "']";
		String fullXpath = requestIdXpath + dateRequestedXpath + requestXpath + statusXpath;

		WebElement ele = FindElement(driver, fullXpath, "Open Request", action.BOOLEAN, timeOut);
		if (ele != null) {
			ele = isDisplayed(driver, ele, "Visibility", timeOut, "Open Request");
			log(LogStatus.INFO, "Verified Open Request with information as: " + requestID + " >> " + dateRequested
					+ " >> " + request + " >> " + status, YesNo.No);
			flag = true;
			return flag;
		}
		log(LogStatus.INFO, "Not Verified Open Request with information as: " + requestID + " >> " + dateRequested
				+ " >> " + request + " >> " + status, YesNo.No);
		BaseLib.sa.assertTrue(false, "Not Verified Open Request with information as: " + requestID + " >> "
				+ dateRequested + " >> " + request + " >> " + status);
		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param requestID
	 * @param dateRequested
	 * @param request
	 * @param timeOut
	 * @return true if successfully verified closed request
	 */
	public boolean verifyingClosedRequest(String projectName, String requestID, String dateRequested, String request,
			int timeOut) {

		log(LogStatus.INFO, "Going to verify Closed Request with information as: " + requestID + " >> " + dateRequested
				+ " >> " + request, YesNo.No);
		boolean flag = false;

		String requestIdXpath = "//*[contains(text(),'" + requestID + "')]/../../../..";
		String dateRequestedXpath = "/following-sibling::td//*[text()='" + dateRequested + "']/../../..";
		String requestXpath = "/following-sibling::td//*[text()='" + request + "']";
		String fullXpath = requestIdXpath + dateRequestedXpath + requestXpath;

		WebElement ele = FindElement(driver, fullXpath, "Closed Request", action.BOOLEAN, timeOut);
		if (ele != null) {
			ele = isDisplayed(driver, ele, "Visibility", timeOut, "Closed Request");
			log(LogStatus.INFO, "Verified Closed Request with information as: " + requestID + " >> " + dateRequested
					+ " >> " + request, YesNo.No);
			flag = true;
			return flag;
		}
		log(LogStatus.INFO, "Not Verified Closed Request with information as: " + requestID + " >> " + dateRequested
				+ " >> " + request, YesNo.No);
		BaseLib.sa.assertTrue(false, "Not Verified Closed Request with information as: " + requestID + " >> "
				+ dateRequested + " >> " + request);
		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param pageName
	 * @param date
	 * @param action
	 * @param timeOut
	 * @return date webElement at Toggle
	 */
	public WebElement getDateAtToggle(String projectName, PageName pageName, String date, action action, int timeOut) {
		String xpath = "//*[@data-label='Date Requested: ']//*[text()='" + date + "']";
		WebElement ele = FindElement(driver, xpath, date, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "DATE : " + date);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "DATE : " + date);
		return ele;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param pageName
	 * @param date
	 * @param action
	 * @param timeOut
	 * @return date tool tip
	 */
	public WebElement getDateAtToggleToolTip(String projectName, PageName pageName, String date, action action,
			int timeOut) {
		String xpath = "//*[@data-label='Date Requested: ']//*[text()='" + date + "']/..";
		WebElement ele = FindElement(driver, xpath, date, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "DATE : " + date);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "DATE : " + date);
		return ele;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param pageName
	 * @param name
	 * @param action
	 * @param timeOut
	 * @return inLine
	 */
	public WebElement getInlineOrLockedAtToggle(String projectName, PageName pageName, String name, action action,
			int timeOut) {
		String xpath = "//a[text()='Fund Investments']/../../../../../..//*[@title='" + name
				+ "']/../following-sibling::span/button";
		WebElement ele = FindElement(driver, xpath, name, action, timeOut);
		scrollDownThroughWebelement(driver, ele, name);
		return ele;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param dealName
	 * @param requestInfo
	 * @param timeOut
	 * @return true if new request created successfully
	 */
	public boolean createNewRequest(String projectName, String dealName, String[][] requestInfo, int timeOut) {
		boolean flag = false;
		String label;
		String value;
		String xpath = "";
		WebElement ele;
		if (clickUsingJavaScript(driver, getNewRequestTrackerBtn(projectName, timeOut), "New Request Tracker button")) {
			appLog.info("clicked on New Request Tracker button");
			log(LogStatus.INFO, "click on New Request Tracker Button", YesNo.Yes);

			for (String[] reuestData : requestInfo) {
				label = reuestData[0].replace("_", " ");
				value = reuestData[1];

				if (PageLabel.Request.toString().equals(reuestData[0])) {
					xpath = "//*[text()='Request']//following-sibling::div//textarea";
					ele = FindElement(driver, xpath, label, action.BOOLEAN, timeOut);
					if (sendKeys(driver, ele, value, label + " : " + value, action.BOOLEAN)) {
						log(LogStatus.INFO, "Send " + value + " to label : " + label, YesNo.No);
					} else {
						sa.assertTrue(false, "Not Able to Send " + value + " to label : " + label);
						log(LogStatus.SKIP, "Not Able to Send " + value + " to label : " + label, YesNo.Yes);
					}

				} else if (PageLabel.Date_Requested.toString().equals(reuestData[0])) {
					xpath = "//*[text()='Date Requested']//following-sibling::div//input";
					ele = FindElement(driver, xpath, label, action.BOOLEAN, timeOut);
					if (sendKeys(driver, ele, value, label + " : " + value, action.BOOLEAN)) {
						log(LogStatus.INFO, "Send " + value + " to label : " + label, YesNo.No);
					} else {
						sa.assertTrue(false, "Not Able to Send " + value + " to label : " + label);
						log(LogStatus.SKIP, "Not Able to Send " + value + " to label : " + label, YesNo.Yes);
					}
				} else if (PageLabel.Status.toString().equals(reuestData[0])) {

					if (click(driver, getStatus(projectName, timeOut), label, action.SCROLLANDBOOLEAN)) {
						ThreadSleep(2000);
						log(LogStatus.INFO, "Able to Click on " + label, YesNo.No);

						xpath = "//span[@title='" + value + "']";
						ele = FindElement(driver, xpath, value, action.SCROLLANDBOOLEAN, timeOut);
						ThreadSleep(2000);
						if (click(driver, ele, value, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Able to select " + value + " to label : " + label, YesNo.No);
						} else {
							sa.assertTrue(false, "Not Able to select " + value + " to label : " + label);
							log(LogStatus.SKIP, "Not Able to select " + value + " to label : " + label, YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Not Able to Click on " + label);
						log(LogStatus.SKIP, "Not Able to Click on " + label, YesNo.Yes);
					}

				}
			}

			xpath = "//*[text()='Deal']/following-sibling::div//input[@placeholder='" + dealName + "']";
			ele = FindElement(driver, xpath, dealName, action.BOOLEAN, timeOut);
			if (ele != null) {
				log(LogStatus.INFO, "Deal Label prefilled with value : " + dealName, YesNo.No);
			} else {
				sa.assertTrue(false, "Deal Label not prefilled with value : " + dealName);
				log(LogStatus.SKIP, "Deal Label not prefilled with value : " + dealName, YesNo.Yes);
			}

			if (click(driver, getCustomTabSaveBtn(projectName, timeOut), "save button", action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on save button");
				ThreadSleep(3000);

			} else {
				sa.assertTrue(false, "Not Able to Click on save button so cannot create request");
				log(LogStatus.SKIP, "Not Able to Click on save button so cannot create request", YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not able to click on New Request Tracker Button so cannot create request");
			log(LogStatus.SKIP, "Not able to click on New Request Tracker Button so cannot create request", YesNo.Yes);

		}
		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param attachmentPath
	 * @return image id of uploaded photo
	 */
	public String updatePhotoInUserPage(String projectName, String attachmentPath) {
		String imgId = null;
		ContactsPage cp = new ContactsPage(driver);
		Actions actions = new Actions(driver);
		scrollDownThroughWebelement(driver, getimgIcon(projectName, 10), "img");
		click(driver, getimgIcon(projectName, 10), "img icon", action.SCROLLANDBOOLEAN);
		ThreadSleep(2000);
		log(LogStatus.INFO, "click on img link", YesNo.No);
		if (click(driver, cp.getupdatePhotoLink(projectName, ContactPagePhotoActions.Update_Photo, 10),
				ContactPagePhotoActions.Update_Photo.toString(), action.SCROLLANDBOOLEAN)) {
			if (sendKeys(driver, getuploadPhotoButton(projectName, 10), attachmentPath, "upload photo button",
					action.SCROLLANDBOOLEAN)) {
				ThreadSleep(5000);
				if (click(driver, getSaveButton(10), "save", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "successfully uploaded photo", YesNo.No);
					ThreadSleep(4000);
					imgId = getimgIconForPath(projectName, 10).getAttribute("src");
					if (imgId != null) {
						log(LogStatus.INFO, "found id of img uploaded: " + imgId, YesNo.Yes);

						return imgId;
					} else {
						log(LogStatus.ERROR, "could not find id of img uploaded", YesNo.Yes);
						sa.assertTrue(false, "could not find id of img uploaded");

					}
				} else {
					log(LogStatus.ERROR, "save button is not clickable", YesNo.Yes);
					sa.assertTrue(false, "save button is not clickable");
				}
			} else {
				log(LogStatus.ERROR, "could not pass attachment path to image", YesNo.Yes);
				sa.assertTrue(false, "could not pass attachment path to image");
			}
		} else {
			log(LogStatus.ERROR, "update photo button is not clickable", YesNo.Yes);
			sa.assertTrue(false, "update photo button is not clickable");
		}
		return null;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param pageName
	 * @param request
	 * @param action
	 * @param timeOut
	 * @return request webElement
	 */
	public WebElement getRequestAtToggle(String projectName, PageName pageName, String request, action action,
			int timeOut) {
		String xpath = "//*[@data-label='Request: ']//*[text()='" + request + "']";
		WebElement ele = FindElement(driver, xpath, request, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "request : " + request);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "request : " + request);
		return ele;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param pageName
	 * @param request
	 * @param action
	 * @param timeOut
	 * @return true if reuest present
	 */
	public boolean isRequestAtToggleToolTip(String projectName, PageName pageName, String request, action action,
			int timeOut) {
		boolean flag = false;
		String xpath = "//*[@data-label='Request: ']//*[text()='" + request + "']";
		WebElement ele = FindElement(driver, xpath, request, action, timeOut);
		scrollDownThroughWebelement(driver, ele, "request : " + request);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "request : " + request);
		if (ele != null) {
			flag = ele.getAttribute("title").equalsIgnoreCase(request);
			return flag;
		}
		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param itemValue
	 * @param action
	 * @param timeOut
	 * @return edit btn webElemnt
	 */
	public WebElement getEditBtn(String projectName, String itemValue, action action, int timeOut) {
		String xpath = "//*[text()='" + itemValue + "']/../following-sibling::*//*[@title='Edit']";
		WebElement ele = FindElement(driver, xpath, itemValue, action, timeOut);
		return ele;
	}

	/**
	 * @param projectName
	 * @param stage
	 * @return WebElement
	 */
	public WebElement findDeactivateLink(String projectName, String stage) {

		String xpath = "//th[text()='" + stage + "']/preceding-sibling::td//a[contains(@title,'Deactivate')]";
		WebElement ele = FindElement(driver, xpath, "deactivate", action.SCROLLANDBOOLEAN, 10);
		scrollDownThroughWebelement(driver, ele, "deactivate link for " + stage);
		return isDisplayed(driver, ele, "Visibility", 10, "deactivate " + stage);

	}

	/**
	 * @param projectName
	 * @param stage
	 * @return WebElement
	 */
	public WebElement findDeleteLink(String projectName, String stage) {

		String xpath = "//th[text()='" + stage + "']/preceding-sibling::td//a[contains(@title,'Del')]";
		WebElement ele = FindElement(driver, xpath, "delete", action.SCROLLANDBOOLEAN, 10);
		scrollDownThroughWebelement(driver, ele, "delete link for " + stage);
		return isDisplayed(driver, ele, "Visibility", 10, "delete " + stage);

	}

	/**
	 * @param projectName
	 * @param stage
	 * @return WebElement
	 */
	public WebElement findActivateLink(String projectName, String stage) {

		String xpath = "//th[text()='" + stage + "']/preceding-sibling::td//a[contains(@title,'Activate')]";
		WebElement ele = FindElement(driver, xpath, "Activate", action.SCROLLANDBOOLEAN, 10);
		scrollDownThroughWebelement(driver, ele, "Activate link for " + stage);
		return isDisplayed(driver, ele, "Visibility", 10, "Activate " + stage);

	}

	/**
	 * @param company
	 * @return String
	 */
	public String convertToPortfolioBeforeNextPart1() {
		return "Please click 'Next' to convert ";
	}

	public String convertToPortfolioBeforeNextPart2() {
		return "to a Portfolio Company.";
	}

	/**
	 * @param company
	 * @return String
	 */
	public String convertToPortfolioRepeat(String company) {
		return company + " is already a Portfolio Company.";
	}

	/**
	 * @param company
	 * @return String
	 */
	public String convertToPortfolioAfterNext(String company) {
		return "has been converted to Portfolio Company successfully.";
	}

	/**
	 * @param timeOut
	 * @return WebElement
	 */
	public WebElement getCompanyNameOnconvertToPortfolioMessage(int timeOut) {

		String xpath = "//h2[text()='Convert to Portfolio']/../following-sibling::*//article//p";
		WebElement ele = FindElement(driver, xpath, "company name convert to portfolio", action.SCROLLANDBOOLEAN, 10);

		return isDisplayed(driver, ele, "Visibility", timeOut, "company name convert to portfolio");

	}

	/**
	 * @param company
	 * @param timeOut
	 * @return WebElement
	 */
	public WebElement getconvertToPortfolioMessage(String company, int timeOut) {
		String xpath = "//h2[text()='Convert to Portfolio']/../following-sibling::*//article//span//span/../span[text()='"
				+ company + " ']/../span[text()='" + convertToPortfolioBeforeNextPart2() + "']";
		WebElement ele = FindElement(driver, xpath, "convert to portfolio", action.SCROLLANDBOOLEAN, 10);
		return isDisplayed(driver, ele, "Visibility", timeOut, "convertToPortfolio");

	}

	/**
	 * @param timeOut
	 * @return WebElement
	 */
	public WebElement getconvertToPortfolioMessageAfterNext(int timeOut) {

		String xpath = "//h2[text()='Convert to Portfolio']/../following-sibling::*//article//span[contains(text(),'successfully')]/..";

		List<WebElement> listOfElements = FindElements(driver, xpath, "convert to portfolio").stream()
				.filter(x -> x.isDisplayed()).collect(Collectors.toList());

		if (listOfElements.size() > 0) {
			return listOfElements.get(0);
		} else {
			return null;

		}

	}

	/**
	 * @param head
	 * @param timeOut
	 * @return WebElement
	 */
	public WebElement getconvertToPortfolioMessageAfterNext(String head, int timeOut) {
		String xpath = "//h2[text()='" + head
				+ "']/../following-sibling::*//article//span[contains(text(),'successfully')]/..";
		WebElement ele = FindElement(driver, xpath, "convert to portfolio", action.SCROLLANDBOOLEAN, 10);
		return isDisplayed(driver, ele, "Visibility", timeOut, "convertToPortfolio");

	}

	/**
	 * @param timeOut
	 * @return WebElement
	 */
	public WebElement getconvertToPortfolioMessageRepeat(int timeOut) {
		String xpath = "//h2[text()='Convert to Portfolio']/../following-sibling::*//article//span[contains(text(),'already')]/..";
		WebElement ele = FindElement(driver, xpath, "convert to portfolio", action.SCROLLANDBOOLEAN, 10);
		return isDisplayed(driver, ele, "Visibility", timeOut, "convertToPortfolio");

	}

	/**
	 * @param head
	 * @param timeOut
	 * @return WebElement
	 */
	public WebElement getconvertToPortfolioMessageRepeat(String head, int timeOut) {
		String xpath = "//h2[text()='" + head
				+ "']/../following-sibling::*//article//span[contains(text(),'already')]/..";
		WebElement ele = FindElement(driver, xpath, "convert to portfolio", action.SCROLLANDBOOLEAN, 10);
		return isDisplayed(driver, ele, "Visibility", timeOut, "convertToPortfolio");

	}

	/**
	 * @param company
	 * @param timeOut
	 * @return WebElement
	 */
	public WebElement crossIconForCompanyName(String company, int timeOut) {
		String xpath = "//label[text()='Company']/..//input[@placeholder='" + company
				+ "']//following-sibling::*//button";
		WebElement ele = FindElement(driver, xpath, "cross icon for company", action.SCROLLANDBOOLEAN, 10);
		return isDisplayed(driver, ele, "Visibility", timeOut, "cross icon for company");

	}

	/**
	 * @param timeOut
	 * @return WebElement
	 */
	public WebElement getconvertToPortfolioMessageUnhandledFlow(int timeOut) {

		String xpath = "//h2[text()='Convert to Portfolio']/../following-sibling::*//article//*[contains(text(),'processing')]/..";
		WebElement ele = FindElement(driver, xpath, "unhandled flow message", action.SCROLLANDBOOLEAN, 10);

		return isDisplayed(driver, ele, "Visibility", timeOut, "unhandled flow message");

	}

	/**
	 * @param timeOut
	 * @return WebElement
	 */
	public WebElement getconvertToPortfolioMessageRecordTypeInvalid(int timeOut) {

		String xpath = "//h2[text()='Convert to Portfolio']/../following-sibling::*//article//p";
		WebElement ele = FindElement(driver, xpath, "RT invalid", action.SCROLLANDBOOLEAN, 10);

		return isDisplayed(driver, ele, "Visibility", timeOut, "RT invalid");

	}

	/**
	 * @param expected
	 * @param timeOut
	 * @return boolean
	 */

	public boolean checkValueOfPathComponentValueOfStage(String expected, int timeOut) {
		String xpath = "//span[@class='current slds-path__stage']/following-sibling::span";
		WebElement ele = FindElement(driver, xpath, "path component", action.SCROLLANDBOOLEAN, 10);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "path component");
		if (ele != null) {
			String stage = ele.getText();

			if (stage.equalsIgnoreCase(expected)) {
				log(LogStatus.INFO, "successfully verified stage " + stage, YesNo.No);
				return true;
			} else {
				log(LogStatus.INFO,
						"could not verify stage on path component\nExpected: " + expected + "\nactual: " + stage,
						YesNo.No);
			}
		} else {
			log(LogStatus.INFO, "could not find path component", YesNo.No);
		}
		return false;
	}

	/**
	 * @param projectName
	 * @param stage
	 * @return WebElement
	 */
	public WebElement selectPathComponent(String projectName, String stage) {
		String xpath = "//span[@class='title slds-path__title'][text()='" + stage + "']";
		WebElement ele = FindElement(driver, xpath, "path component", action.SCROLLANDBOOLEAN, 10);
		return isDisplayed(driver, ele, "Visibility", 10, "path component");

	}

	/**
	 * @param projectName
	 * @param stage
	 * @return WebElement
	 */
	public WebElement getStagePath(String stage, String stageVale) {
		String xpath = "";
		stageVale = stageVale.replace("_", " ");
		if (DealStage.Completed.toString().equals(stage)) {
			xpath = "//*[@class='complete slds-path__stage']/following-sibling::span[text()='" + stageVale + "']";
		} else if (DealStage.Current.toString().equals(stage)) {
			xpath = "//*[@class='current slds-path__stage']/following-sibling::span[text()='" + stageVale + "']";
		} else if (DealStage.Ahead.toString().equals(stage)) {
			xpath = "//*[@class='ahead slds-path__stage']/following-sibling::span[text()='" + stageVale + "']";
		}

		WebElement ele = FindElement(driver, xpath, stage + " : " + stageVale, action.SCROLLANDBOOLEAN, 60);
		return isDisplayed(driver, ele, "Visibility", 30, stage + " : " + stageVale);

	}

	public WebElement getconvertToPortfolioMessage1(String msg, int timeOut) {
		msg = convertingPortfoliaMsg;
		String xpath = "//h2[text()='Convert to Portfolio']/../following-sibling::*//*[text()='" + msg + "']";
		WebElement ele = FindElement(driver, xpath, msg, action.SCROLLANDBOOLEAN, 10);

		return isDisplayed(driver, ele, "Visibility", timeOut, msg);

	}

	public boolean createDeal(String environment, String dealName, String companyName, String stage) {
		// TODO Auto-generated method stub
		WebElement ele;
		boolean flag = false;
		if (CommonLib.click(driver, getDealNewButton(30), tabObj4 + " new button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on the new button", YesNo.No);

			if (!dealName.isEmpty() || !dealName.equals("") || dealName != null) {

				if (CommonLib.sendKeys(driver, getNewDealPopupDealNameInput(30), dealName, "Deal name",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, dealName + " value has been passed in Deal Name", YesNo.No);
				} else {
					log(LogStatus.ERROR, dealName + " value is not passed in Deal Name", YesNo.No);
					return false;
				}
			}
			if (companyName != null) {

				if (sendKeys(driver, getCompanyName(60), companyName, "Company Name", action.SCROLLANDBOOLEAN)) {
					ThreadSleep(3000);
					if (click(driver,
							FindElement(driver, "//*[text()='Company']/..//*[@title='" + companyName + "']",
									"Legal Name List", action.THROWEXCEPTION, 30),
							companyName + "   :   Company Name", action.BOOLEAN)) {
						appLog.info(companyName + "  is present in list.");
					} else {
						appLog.error(companyName + "  is not present in the list.");
						return false;
					}

				} else {
					appLog.error("Not able to enter legal name");
					return false;
				}

			}
			if (!stage.isEmpty() || !stage.equals("") || stage != null) {

				if (CommonLib.dropDownHandle(driver, getStageField(40),
						"//label[text()='Stage']/following-sibling::div//span[@class='slds-truncate']", "Stage field",
						stage)) {
					log(LogStatus.INFO, stage + " value has been selected from stage field", YesNo.No);

				} else {
					log(LogStatus.ERROR, stage + " value is not selected from stage field", YesNo.No);
					return false;
				}
			}

			if (CommonLib.click(driver, getSaveButton(30), tabObj4 + " save button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on save button", YesNo.No);

				String xPath = "//lightning-formatted-text[contains(text(),'" + dealName + "')]";
				ele = CommonLib.FindElement(driver, xPath, dealName, action.SCROLLANDBOOLEAN, 40);
				if (ele != null) {
					log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);
					sa.assertTrue(true, dealName + " deal has been created");
					flag = true;

				} else {
					log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);

				}
			} else {
				log(LogStatus.ERROR, "Not able to click on save button", YesNo.No);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on the new button", YesNo.No);
		}

		return flag;

	}

	public boolean createDeal(String recordType, String dealName, String companyName, String stage, String otherLabels,
			String otherLabelValues) {
		// TODO Auto-generated method stub
		WebElement ele;
		boolean flag = false;
		String[] labelNames = null;
		String[] labelValue = null;
		if (otherLabels != null && !"".equalsIgnoreCase(otherLabels)) {
			labelNames = otherLabels.split("<Break>", -1);
			labelValue = otherLabelValues.split("<Break>", -1);
		}

		if (CommonLib.click(driver, getDealNewButton(30), tabObj4 + " new button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on the new button", YesNo.No);

			if (recordType != null && !"".equals(recordType)) {
				if (CommonLib.click(driver, dealRecordTypeRadioButton(recordType, 20), "Radio button: " + recordType,
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on the radio button: " + recordType, YesNo.No);

					if (CommonLib.click(driver, nextButtonOnForm(20), "Next button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on the Next button", YesNo.No);

					} else {
						log(LogStatus.ERROR, "Not able to click on the Next button", YesNo.No);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on the Radio button: " + recordType, YesNo.No);
				}
			}

			if (!dealName.isEmpty() && dealName != null) {

				if (CommonLib.sendKeys(driver, getNewDealPopupDealNameInput(30), dealName, "Deal name",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, dealName + " value has been passed in Deal Name", YesNo.No);
				} else {
					log(LogStatus.ERROR, dealName + " value is not passed in Deal Name", YesNo.No);
					return false;
				}
			} else {
				log(LogStatus.ERROR, "Deal Name not Provided", YesNo.No);
				return false;
			}
			if (!companyName.isEmpty() && companyName != null) {

				if (sendKeys(driver, getCompanyName(60), companyName, "Company Name", action.SCROLLANDBOOLEAN)) {
					ThreadSleep(3000);
					if (click(driver,
							FindElement(driver, "//*[text()='Company']/..//*[@title='" + companyName + "']",
									"Legal Name List", action.THROWEXCEPTION, 30),
							companyName + "   :   Company Name", action.BOOLEAN)) {
						appLog.info(companyName + "  is present in list.");
					} else {
						appLog.error(companyName + "  is not present in the list.");
						return false;
					}

				} else {
					appLog.error("Not able to enter legal name");
					return false;
				}

			}

			else {
				log(LogStatus.ERROR, "Company Name not Provided", YesNo.No);
				return false;
			}
			if (!stage.isEmpty() && stage != null) {

				if (CommonLib.dropDownHandle(driver, getStageField(40),
						"//label[text()='Stage']/following-sibling::div//span[@class='slds-truncate']", "Stage field",
						stage)) {
					log(LogStatus.INFO, stage + " value has been selected from stage field", YesNo.No);

				} else {
					log(LogStatus.ERROR, stage + " value is not selected from stage field", YesNo.No);
					return false;
				}
			}

			else {
				log(LogStatus.ERROR, "Stage not Provided", YesNo.No);
				return false;
			}

			int loopCount = 0;
			int status = 0;
			if (labelNames != null && labelValue != null) {
				for (int i = 0; i < labelNames.length; i++) {

					if (labelNames[i].equalsIgnoreCase("Our Role")
							|| labelNames[i].equalsIgnoreCase("Reason for Decline")
							|| labelNames[i].equalsIgnoreCase("Reason to Park")
							|| labelNames[i].equalsIgnoreCase("Deal Type")
							|| labelNames[i].equalsIgnoreCase("Management Meeting")) {

						String firmDropDownElements = "//*[text()='" + labelNames[i]
								+ "']/..//button/../following-sibling::div//lightning-base-combobox-item";
						if (CommonLib.dropDownHandle(driver, dropDownWithLabelName(labelNames[i], 20),
								firmDropDownElements, labelNames[i] + "DropDown", labelValue[i])) {
							log(LogStatus.INFO, labelNames[i] + " has been Selected to:  " + labelValue[i], YesNo.No);
							status++;

						} else {
							sa.assertTrue(false, labelNames[i] + " has not been Selected to:  " + labelValue[i]);
							log(LogStatus.ERROR, labelNames[i] + " has not been Selected to:  " + labelValue[i],
									YesNo.Yes);

						}

					}

					else if (labelNames[i].equalsIgnoreCase("Platform Company")
							|| labelNames[i].equalsIgnoreCase("Source Firm")
							|| labelNames[i].equalsIgnoreCase("Source Contact")) {
						if (click(driver, FindElement(driver, "//*[text()='" + labelNames[i]
								+ "']/following-sibling::div[@class='slds-form-element__control']//input[@type='text']",
								"picklist " + labelNames[i], action.SCROLLANDBOOLEAN, 10), "picklist " + labelNames[i],
								action.SCROLLANDBOOLEAN)) {
							if (sendKeys(driver, FindElement(driver, "//*[text()='" + labelNames[i]
									+ "']/following-sibling::div[@class='slds-form-element__control']//input[@type='text']",
									"picklist " + labelNames[i], action.SCROLLANDBOOLEAN, 10), labelValue[i],
									"Label Names", action.SCROLLANDBOOLEAN)) {
								appLog.info(labelNames[i] + "  is present in list.");
							}
							ThreadSleep(3000);

							if (click(driver, FindElement(driver,
//											"//span[text()='" + labelValue[i]
//													+ "']/ancestor::lightning-base-combobox-item",
									"//*[text()='" + labelNames[i] + "']/..//*[@title='" + labelValue[i] + "']",
									"Legal Name List", action.THROWEXCEPTION, 30),
									labelNames[i] + "   :   Account Name", action.BOOLEAN)) {
								appLog.info(labelNames[i] + "  is present in list.");
								status++;

							} else {
								appLog.error("Not able to select " + labelValue[i] + " in " + labelNames[i] + " field");
								BaseLib.sa.assertTrue(false,
										"Not able to select " + labelValue[i] + " in " + labelNames[i] + " field");

							}
						} else {
							appLog.error("Not able to select " + labelValue[i] + " in " + labelNames[i] + " field");
							BaseLib.sa.assertTrue(false,
									"Not able to select " + labelValue[i] + " in " + labelNames[i] + " field");

						}
					}

					else if (labelNames[i].equalsIgnoreCase("Date Received")
							|| labelNames[i].equalsIgnoreCase("LOI Due Date")
							|| labelNames[i].equalsIgnoreCase("NDA Signed Date")
							|| labelNames[i].equalsIgnoreCase("Management Meeting Date")
							|| labelNames[i].equalsIgnoreCase("Pipeline Data Date")
							|| labelNames[i].equalsIgnoreCase("Last Stage Change Date")) {

						String[] date = CommonLib
								.convertDateFromOneFormatToAnother(labelValue[i], "MM/dd/yyyy", "dd/MMM/yyyy")
								.split("/");

						if (click(driver, calendarInputBox(labelNames[i], 30), labelNames[i] + " Input Box",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Click on " + labelNames[i] + " Calendar Input Box", YesNo.No);
							if (CommonLib.datePickerHandle(driver, monthInDatePicker(30),
									previousMonthButtonInDatePicker(30), labelNames[i] + " Picker", date[2], date[1],
									date[0])) {
								log(LogStatus.INFO, "Date has been Selected  " + labelValue[i], YesNo.No);
								status++;
							} else {
								sa.assertTrue(false, "Date has not been Selected  " + labelValue[i]);
								log(LogStatus.ERROR, "Date has not been Selected  " + labelValue[i], YesNo.Yes);
							}
						} else {
							sa.assertTrue(false, "Not Able to Click on " + labelNames[i] + " Calendar input Box");
							log(LogStatus.ERROR, "Not Able to Click on " + labelNames[i] + " Calendar input Box",
									YesNo.Yes);
						}

					}

					else if (labelNames[i].equalsIgnoreCase("Deal Description")
							|| labelNames[i].equalsIgnoreCase("Pipeline Comments")) {

						if (CommonLib.sendKeys(driver, textAreaBoxBasedOnLabelName(labelNames[i], 10), labelValue[i],
								"textBoxBasedOnLabelName: " + labelNames[i], action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, labelValue[i] + " value has been passed in " + labelNames[i], YesNo.No);
							status++;
						} else {
							log(LogStatus.ERROR, labelValue[i] + " value is not passed in " + labelNames[i], YesNo.No);
							sa.assertTrue(false, labelValue[i] + " value is not passed in " + labelNames[i]);

						}
					}

					else {

						if (CommonLib.sendKeys(driver, textBoxBasedOnLabelName(labelNames[i], 10), labelValue[i],
								"textBoxBasedOnLabelName: " + labelNames[i], action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, labelValue[i] + " value has been passed in " + labelNames[i], YesNo.No);
							status++;
						} else {
							log(LogStatus.ERROR, labelValue[i] + " value is not passed in " + labelNames[i], YesNo.No);
							sa.assertTrue(false, labelValue[i] + " value is not passed in " + labelNames[i]);

						}
					}

					loopCount++;
				}

			}

			if (status == loopCount) {
				if (CommonLib.click(driver, getSaveButton(30), tabObj4 + " save button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on save button", YesNo.No);

					String xPath = "//lightning-formatted-text[contains(text(),'" + dealName + "')]";
					ele = CommonLib.FindElement(driver, xPath, dealName, action.SCROLLANDBOOLEAN, 40);
					if (ele != null) {
						log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);

						flag = true;

					} else {
						log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);

					}
				} else {
					log(LogStatus.ERROR, "Not able to click on save button", YesNo.No);

				}

//			} else {
//				CommonLib.click(driver, getCancelButton(30), " Cancel button", action.SCROLLANDBOOLEAN);
//				log(LogStatus.ERROR, "Clicked on Cancel Button", YesNo.No);
//				return false;
//			}

			} else {
				log(LogStatus.ERROR, "Not able to click on the new button", YesNo.No);
			}
		}
		return flag;

	}

	/**
	 * @author sahil bansal
	 * @param projectName
	 * @param companyname
	 * @param timeOut
	 * @return true if successfully change stage
	 */
	public boolean UpdateOtherLable(String projectName, String otherLabels, String otherLabelValues, int timeOut) {
		boolean flag = true;
		WebElement ele;
		String[] labelNames = null;
		String[] labelValue = null;
		if (otherLabels != null && !"".equalsIgnoreCase(otherLabels)) {
			labelNames = otherLabels.replaceAll("_", " ").split("<Break>", -1);
			labelValue = otherLabelValues.split("<Break>", -1);
		}
		ThreadSleep(2000);
		if (clickOnShowMoreActionDownArrow(projectName, PageName.Object4Page, ShowMoreActionDropDownList.Edit, 10)) {
			ThreadSleep(2000);
			if (click(driver, getSourceFirmCrossIcon(projectName, 60), "Company Cross Icon", action.SCROLLANDBOOLEAN)) {
				appLog.info("Clicked on Legal Cross icon");
				ThreadSleep(3000);
			} else {
				appLog.info("Not able to click on Cross Icon button");
				log(LogStatus.INFO, "Not able to clicked on edit button so cannot Account Name ", YesNo.Yes);
				BaseLib.sa.assertTrue(false, "Not able to clicked on edit button so cannot Account Name ");
			}
			int loopCount = 0;
			int status = 0;
			if (labelNames != null && labelValue != null) {
				for (int i = 0; i < labelNames.length; i++) {
					if (labelNames[i].equalsIgnoreCase("Platform Company")
							|| labelNames[i].equalsIgnoreCase("Source Firm")
							|| labelNames[i].equalsIgnoreCase("Source Contact")) {
						if (click(driver, FindElement(driver, "//*[text()='" + labelNames[i]
								+ "']/following-sibling::div[@class='slds-form-element__control']//input[@type='text']",
								"picklist " + labelNames[i], action.SCROLLANDBOOLEAN, 10), "picklist " + labelNames[i],
								action.SCROLLANDBOOLEAN)) {

							if (click(driver, FindElement(driver,
//												"//span[text()='" + labelValue[i]
//														+ "']/ancestor::lightning-base-combobox-item",
									"//*[text()='" + labelNames[i] + "']/..//*[@title='" + labelValue[i] + "']",
									"Legal Name List", action.THROWEXCEPTION, 30),
									labelNames[i] + "   :   labelNames[i]", action.BOOLEAN)) {
								appLog.info(labelNames[i] + "  is present in list.");
								status++;

							} else {
								appLog.error("Not able to select " + labelValue[i] + " in " + labelNames[i] + " field");
								BaseLib.sa.assertTrue(false,
										"Not able to select " + labelValue[i] + " in " + labelNames[i] + " field");

							}
						} else {
							appLog.error("Not able to select " + labelValue[i] + " in " + labelNames[i] + " field");
							BaseLib.sa.assertTrue(false,
									"Not able to select " + labelValue[i] + " in " + labelNames[i] + " field");

						}
					}

					else if (labelNames[i].equalsIgnoreCase("Date Received")
							|| labelNames[i].equalsIgnoreCase("LOI Due Date")
							|| labelNames[i].equalsIgnoreCase("NDA Signed Date")
							|| labelNames[i].equalsIgnoreCase("Management Meeting Date")
							|| labelNames[i].equalsIgnoreCase("Pipeline Data Date")
							|| labelNames[i].equalsIgnoreCase("Last Stage Change Date")) {

						String[] date = CommonLib
								.convertDateFromOneFormatToAnother(labelValue[i], "MM/dd/yyyy", "dd/MMM/yyyy")
								.split("/");

						if (click(driver, calendarInputBox(labelNames[i], 30), labelNames[i] + " Input Box",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Click on " + labelNames[i] + " Calendar Input Box", YesNo.No);
							if (CommonLib.datePickerHandle(driver, monthInDatePicker(30),
									previousMonthButtonInDatePicker(30), labelNames[i] + " Picker", date[2], date[1],
									date[0])) {
								log(LogStatus.INFO, "Date has been Selected  " + labelValue[i], YesNo.No);
								status++;
							} else {
								sa.assertTrue(false, "Date has not been Selected  " + labelValue[i]);
								log(LogStatus.ERROR, "Date has not been Selected  " + labelValue[i], YesNo.Yes);
							}
						} else {
							sa.assertTrue(false, "Not Able to Click on " + labelNames[i] + " Calendar input Box");
							log(LogStatus.ERROR, "Not Able to Click on " + labelNames[i] + " Calendar input Box",
									YesNo.Yes);
						}

					} else {

						if (CommonLib.sendKeys(driver, textBoxBasedOnLabelName(labelNames[i], 10), labelValue[i],
								"textBoxBasedOnLabelName: " + labelNames[i], action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, labelValue[i] + " value has been passed in " + labelNames[i], YesNo.No);
							status++;
						} else {
							log(LogStatus.ERROR, labelValue[i] + " value is not passed in " + labelNames[i], YesNo.No);
							sa.assertTrue(false, labelValue[i] + " value is not passed in " + labelNames[i]);

						}
					}

					loopCount++;
				}

			}

			if (status == loopCount) {
				if (CommonLib.click(driver, getSaveButton(30), tabObj4 + " save button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on save button", YesNo.No);

				} else {
					log(LogStatus.ERROR, "Not able to click on save button", YesNo.No);

				}
			} else {
				log(LogStatus.ERROR, "Not able to click on the new button", YesNo.No);
			}

			return flag;

		}
		return flag;

	}

	/**
	 * @author sahil bansal
	 * @param projectName
	 * @param companyname
	 * @param timeOut
	 * @return true if successfully change stage
	 */
	public boolean UpdateOtherLabledaterecieved(String projectName, String otherLabels, String otherLabelValues,
			int timeOut) {
		boolean flag = true;
		WebElement ele;
		String[] labelNames = null;
		String[] labelValue = null;
		if (otherLabels != null && !"".equalsIgnoreCase(otherLabels)) {
			labelNames = otherLabels.split("<Break>", -1);
			labelValue = otherLabelValues.split("<Break>", -1);
			if (clickOnShowMoreActionDownArrow(projectName, PageName.Object4Page, ShowMoreActionDropDownList.Edit,
					10)) {
				ThreadSleep(2000);
			}
		}
		ThreadSleep(2000);
		int loopCount = 0;
		int status = 0;
		if (labelNames != null && labelValue != null) {
			for (int i = 0; i < labelNames.length; i++) {
				if (labelNames[i].equalsIgnoreCase("Date Received") || labelNames[i].equalsIgnoreCase("LOI Due Date")
						|| labelNames[i].equalsIgnoreCase("NDA Signed Date")
						|| labelNames[i].equalsIgnoreCase("Management Meeting Date")
						|| labelNames[i].equalsIgnoreCase("Pipeline Data Date")
						|| labelNames[i].equalsIgnoreCase("Last Stage Change Date")) {

					String[] date = CommonLib
							.convertDateFromOneFormatToAnother(labelValue[i], "MM/dd/yyyy", "dd/MMM/yyyy").split("/");

					if (click(driver, calendarInputBox(labelNames[i], 30), labelNames[i] + " Input Box",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Click on " + labelNames[i] + " Calendar Input Box", YesNo.No);
						if (CommonLib.datePickerHandle(driver, monthInDatePicker(30),
								previousMonthButtonInDatePicker(30), labelNames[i] + " Picker", date[2], date[1],
								date[0])) {
							log(LogStatus.INFO, "Date has been Selected  " + labelValue[i], YesNo.No);
							status++;
						} else {
							sa.assertTrue(false, "Date has not been Selected  " + labelValue[i]);
							log(LogStatus.ERROR, "Date has not been Selected  " + labelValue[i], YesNo.Yes);
						}
					} else {
						sa.assertTrue(false, "Not Able to Click on " + labelNames[i] + " Calendar input Box");
						log(LogStatus.ERROR, "Not Able to Click on " + labelNames[i] + " Calendar input Box",
								YesNo.Yes);
					}

				} else {

					if (CommonLib.sendKeys(driver, textBoxBasedOnLabelName(labelNames[i], 10), labelValue[i],
							"textBoxBasedOnLabelName: " + labelNames[i], action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, labelValue[i] + " value has been passed in " + labelNames[i], YesNo.No);
						status++;
					} else {
						log(LogStatus.ERROR, labelValue[i] + " value is not passed in " + labelNames[i], YesNo.No);
						sa.assertTrue(false, labelValue[i] + " value is not passed in " + labelNames[i]);

					}
				}

				loopCount++;
			}

		}

		if (status == loopCount) {
			if (CommonLib.click(driver, getSaveButton(30), tabObj4 + " save button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on save button", YesNo.No);

			} else {
				log(LogStatus.ERROR, "Not able to click on save button", YesNo.No);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on the new button", YesNo.No);
		}

		return flag;

	}

	/**
	 * @param company
	 * @param timeOut
	 * @return WebElement
	 */
	public String dealbottomcount(int timeOut) {
		String xpath = "//span[@title='Deals']/ancestor::div[contains(@class,'left_x-small')]//table";
		WebElement dealElement = FindElement(driver, xpath, "dealbottomcount", action.BOOLEAN, 10);
		return isDisplayed(driver, dealElement, xpath, 10, "").getAttribute("data-num-rows");
	}

	public String dealbottomname(String dealbottomcount, int timeOut) {
		String xpath = "//span[@title='Deals']/ancestor::div[contains(@class,'left_x-small')]//table//tr[@data-row-number='"
				+ dealbottomcount + "']//a";
		WebElement dealElement = FindElement(driver, xpath, "dealbottomname", action.BOOLEAN, 10);
		return isDisplayed(driver, dealElement, xpath, 10, "").getAttribute("title");
	}

	public String dealtopname(String dealbottomcount, int timeOut) {
		String xpath = "//span[@title='Deals']/ancestor::div[contains(@class,'left_x-small')]//table//tr[@data-row-number='1']//a";
		WebElement dealElement = FindElement(driver, xpath, "dealtopname", action.BOOLEAN, 10);
		return isDisplayed(driver, dealElement, xpath, 10, "").getAttribute("title");
	}

	public String UserName(String teamMemberName, int timeOut) {
		String xpath = "(//div//a[@title='CRM2 Cred'])[2]";
		WebElement userElement = FindElement(driver, xpath, "username", action.BOOLEAN, 10);
		return isDisplayed(driver, userElement, xpath, 10, "").getAttribute("title");
	}

	public List<WebElement> listOfDealNames(int timeout) {
		return FindElements(driver,
				"//span[@title='Deals']/ancestor::div[@class='slds-col slds-size_6-of-12 slds-p-left_x-small']//table[contains(@class,'slds-table slds-table_header-fixed')]");
	}

	public String EmailcountonPopup(int timeOut) {
		String xpath = "//table";
		WebElement emailElement = FindElement(driver, xpath, "EmailcountonPopup", action.BOOLEAN, 10);
		return isDisplayed(driver, emailElement, xpath, 10, "").getAttribute("data-last-rendered-row");
	}

	public boolean createDealfromIcon(String recordType, String dealName, String stage, String otherLabels,
			String otherLabelValues) {
		// TODO Auto-generated method stub
		WebElement ele;
		boolean flag = false;
		String[] labelNames = null;
		String[] labelValue = null;
		if (otherLabels != null && !"".equalsIgnoreCase(otherLabels)) {
			labelNames = otherLabels.split("<Break>", -1);
			labelValue = otherLabelValues.split("<Break>", -1);
		}

//		if (CommonLib.click(driver, NewDealIcon(30), tabObj4 + "New Deal Icon", action.SCROLLANDBOOLEAN)) {
//			log(LogStatus.INFO, "Clicked on the new Deal Icon", YesNo.No);

//			if (recordType != null && !"".equals(recordType)) {
//				if (CommonLib.click(driver, dealRecordTypeRadioButton(recordType, 20), "Radio button: " + recordType,
//						action.SCROLLANDBOOLEAN)) {
//					log(LogStatus.INFO, "Clicked on the radio button: " + recordType, YesNo.No);
//
//					if (CommonLib.click(driver, nextButtonOnForm(20), "Next button", action.SCROLLANDBOOLEAN)) {
//						log(LogStatus.INFO, "Clicked on the Next button", YesNo.No);
//
//					} else {
//						log(LogStatus.ERROR, "Not able to click on the Next button", YesNo.No);
//					}
//
//				} else {
//					log(LogStatus.ERROR, "Not able to click on the Radio button: " + recordType, YesNo.No);
//				}
//			}

		if (!dealName.isEmpty() && dealName != null) {

			if (CommonLib.sendKeys(driver, getPopupdealNameInput(30), dealName, "Deal name", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, dealName + " value has been passed in Deal Name", YesNo.No);
			} else {
				log(LogStatus.ERROR, dealName + " value is not passed in Deal Name", YesNo.No);
				return false;
			}
		} else {
			log(LogStatus.ERROR, "Deal Name not Provided", YesNo.No);
			return false;
		}

//			if (!companyName.isEmpty() && companyName != null) {
//
//				if (sendKeys(driver, getCompanyName(60), companyName, "Company Name", action.SCROLLANDBOOLEAN)) {
//					ThreadSleep(3000);
//					if (click(driver,
//							FindElement(driver, "//*[text()='Company']/..//*[@title='" + companyName + "']",
//									"Legal Name List", action.THROWEXCEPTION, 30),
//							companyName + "   :   Company Name", action.BOOLEAN)) {
//						appLog.info(companyName + "  is present in list.");
//					} else {
//						appLog.error(companyName + "  is not present in the list.");
//						return false;
//					}
//
//				} else {
//					appLog.error("Not able to enter legal name");
//					return false;
//				}
//
//			}
//
//			else {
//				log(LogStatus.ERROR, "Company Name not Provided", YesNo.No);
//				return false;
//			}
		if (!stage.isEmpty() && stage != null) {

			if (CommonLib.dropDownHandle(driver, getpopupstageField(40), "//div[@class=\"select-options\"]//li/a",
					"Stage field", stage)) {
				log(LogStatus.INFO, stage + " value has been selected from stage field", YesNo.No);

			} else {
				log(LogStatus.ERROR, stage + " value is not selected from stage field", YesNo.No);
				return false;
			}
		}

		else {
			log(LogStatus.ERROR, "Stage not Provided", YesNo.No);
			return false;
		}

		int loopCount = 0;
		int status = 0;
		if (labelNames != null && labelValue != null) {
			for (int i = 0; i < labelNames.length; i++) {

				if (labelNames[i].equalsIgnoreCase("Our Role") || labelNames[i].equalsIgnoreCase("Reason for Decline")
						|| labelNames[i].equalsIgnoreCase("Reason to Park")
						|| labelNames[i].equalsIgnoreCase("Deal Type")
						|| labelNames[i].equalsIgnoreCase("Management Meeting")) {

					String firmDropDownElements = "//*[text()='" + labelNames[i]
							+ "']/..//button/../following-sibling::div//lightning-base-combobox-item";
					if (CommonLib.dropDownHandle(driver, dropDownWithLabelName(labelNames[i], 20), firmDropDownElements,
							labelNames[i] + "DropDown", labelValue[i])) {
						log(LogStatus.INFO, labelNames[i] + " has been Selected to:  " + labelValue[i], YesNo.No);
						status++;

					} else {
						sa.assertTrue(false, labelNames[i] + " has not been Selected to:  " + labelValue[i]);
						log(LogStatus.ERROR, labelNames[i] + " has not been Selected to:  " + labelValue[i], YesNo.Yes);

					}

				}

				else if (labelNames[i].equalsIgnoreCase("Platform Company") || labelNames[i].equalsIgnoreCase("	")
						|| labelNames[i].equalsIgnoreCase("Source Contact")) {
					if (click(driver,
							FindElement(driver, "//span[text()='" + labelNames[i] + "']/../..//input[@type='text']",
									"picklist " + labelNames[i], action.SCROLLANDBOOLEAN, 10),
							"picklist " + labelNames[i], action.SCROLLANDBOOLEAN)) {
						if (sendKeys(driver,
								FindElement(driver, "//span[text()='" + labelNames[i] + "']/../..//input[@type='text']",
										"picklist " + labelNames[i], action.SCROLLANDBOOLEAN, 10),
								labelValue[i], "Label Names", action.SCROLLANDBOOLEAN)) {
							appLog.info(labelNames[i] + "  is present in list.");
						}
						ThreadSleep(3000);

						if (click(driver, FindElement(driver,
//											"//span[text()='" + labelValue[i]
//													+ "']/ancestor::lightning-base-combobox-item",
								"//*[text()='" + labelNames[i] + "']/..//*[@title='" + labelValue[i] + "']",
								"Legal Name List", action.THROWEXCEPTION, 30), labelNames[i] + "   :   Account Name",
								action.BOOLEAN)) {
							appLog.info(labelNames[i] + "  is present in list.");
							status++;

						} else {
							appLog.error("Not able to select " + labelValue[i] + " in " + labelNames[i] + " field");
							BaseLib.sa.assertTrue(false,
									"Not able to select " + labelValue[i] + " in " + labelNames[i] + " field");

						}
					} else {
						appLog.error("Not able to select " + labelValue[i] + " in " + labelNames[i] + " field");
						BaseLib.sa.assertTrue(false,
								"Not able to select " + labelValue[i] + " in " + labelNames[i] + " field");

					}
				}

				else if (labelNames[i].equalsIgnoreCase("Date Received")
						|| labelNames[i].equalsIgnoreCase("LOI Due Date")
						|| labelNames[i].equalsIgnoreCase("NDA Signed Date")
						|| labelNames[i].equalsIgnoreCase("Management Meeting Date")
						|| labelNames[i].equalsIgnoreCase("Pipeline Data Date")
						|| labelNames[i].equalsIgnoreCase("Last Stage Change Date")) {

					if (CommonLib.sendKeys(driver, popupcalendarInputBox(labelNames[i], 30), labelValue[i],
							labelValue[i] + " Input Box", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Date has been Selected  " + labelValue[i], YesNo.No);
						status++;
					} else {
						sa.assertTrue(false, "Date has not been Selected  " + labelValue[i]);
						log(LogStatus.ERROR, "Date has not been Selected  " + labelValue[i], YesNo.Yes);
						return false;
					}

				}

				else if (labelNames[i].equalsIgnoreCase("Deal Description")
						|| labelNames[i].equalsIgnoreCase("Pipeline Comments")) {

					if (CommonLib.sendKeys(driver, textAreaBoxBasedOnLabelName(labelNames[i], 10), labelValue[i],
							"textBoxBasedOnLabelName: " + labelNames[i], action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, labelValue[i] + " value has been passed in " + labelNames[i], YesNo.No);
						status++;
					} else {
						log(LogStatus.ERROR, labelValue[i] + " value is not passed in " + labelNames[i], YesNo.No);
						sa.assertTrue(false, labelValue[i] + " value is not passed in " + labelNames[i]);

					}
				}

				else {

					if (CommonLib.sendKeys(driver, textBoxBasedOnLabelName(labelNames[i], 10), labelValue[i],
							"textBoxBasedOnLabelName: " + labelNames[i], action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, labelValue[i] + " value has been passed in " + labelNames[i], YesNo.No);
						status++;
					} else {
						log(LogStatus.ERROR, labelValue[i] + " value is not passed in " + labelNames[i], YesNo.No);
						sa.assertTrue(false, labelValue[i] + " value is not passed in " + labelNames[i]);

					}
				}

				loopCount++;
			}

		}

		if (status == loopCount) {
			if (CommonLib.click(driver, getpopupsaveButton(30), tabObj4 + " save button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on save button", YesNo.No);
//
//					String xPath = "//lightning-formatted-text[contains(text(),'" + dealName + "')]";
//					ele = CommonLib.FindElement(driver, xPath, dealName, action.SCROLLANDBOOLEAN, 40);
//					if (ele != null) {
//						log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);
//
//						flag = true;
//
//					} else {
//						log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
//
//					}
			} else {
				log(LogStatus.ERROR, "Not able to click on save button", YesNo.No);

			}

		} else {
			CommonLib.click(driver, getNewFinancingPopupCancelIcon(30), " Cancel button", action.SCROLLANDBOOLEAN);
			log(LogStatus.ERROR, "Clicked on Cancel Button", YesNo.No);
			return false;
		}

//		} else {
//			log(LogStatus.ERROR, "Not able to click on the new button", YesNo.No);
//		}

		return flag;

	}

	
	public boolean createDealFromNavigation(String dealName, String companyName)
	{
		if(sendKeys(driver, getDealNameInputFromPopup(20), dealName, "deal name", action.SCROLLANDBOOLEAN))
		{
			log(LogStatus.INFO, "Deal Name : "+dealName+"  has been passed", YesNo.No);

			if(click(driver, getnextButton(20), "Next button", action.SCROLLANDBOOLEAN))
			{
				log(LogStatus.INFO, "Clicked on Next button", YesNo.No);

				if(click(driver, getYesButton(20), "Yes button", action.SCROLLANDBOOLEAN))
				{
					log(LogStatus.INFO, "Clicked on Yes button", YesNo.No);	

					if(sendKeys(driver, getCompanyNameSearch(20), companyName, "company name", action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "Company name : "+companyName+" has been passed", YesNo.No);
						ThreadSleep(3000);
						if(clickUsingJavaScript(driver, getCompanyNameSearch(companyName,30), "Company name"))
						{
							log(LogStatus.INFO, "Company name "+companyName+" has been selected", YesNo.No);

							if(click(driver, getDealPopupSaveBtn(20), "save button", action.SCROLLANDBOOLEAN))
							{
								log(LogStatus.INFO, "Clicked on save button", YesNo.No);
								if(getCreatedConfirmationMsg("",30)!=null)
								{
									log(LogStatus.INFO, "Deal : "+dealName+" has been created", YesNo.No);
									return true;
								}
								else
								{
									log(LogStatus.ERROR, "Deal : "+dealName+" is not created", YesNo.No);
								}
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on save button", YesNo.No);
							}

						}
						else
						{
							log(LogStatus.ERROR, "Company name "+companyName+" is not selected", YesNo.No);
						}
					}

					else
					{
						log(LogStatus.ERROR, "Not able to pass the Company name : "+companyName, YesNo.No);
					}
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Yes button", YesNo.No);	
				}
			}

			else
			{
				log(LogStatus.ERROR, "Not able to click on Next button", YesNo.No);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to pass Deal Name : "+dealName, YesNo.No);
		}
		return false;
	}


}
