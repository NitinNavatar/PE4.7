package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.CommonLib;
import com.navatar.generic.CommonVariables;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.AppListeners.appLog;
import static com.navatar.generic.CommonLib.*;

import java.util.ArrayList;
import java.util.List;

public class PartnershipsPageBusinessLayer extends PartnershipsPage {

	public PartnershipsPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author Ankit Jaiswal
	 * @param projectName
	 * @param environment
	 * @param mode
	 * @param partnershipLegalName
	 * @param fund
	 * @return true if able to create partnership
	 */
	public boolean createPartnership(String projectName, String environment, String mode, String partnershipLegalName,
			String fund) {
		ThreadSleep(5000);
		if (click(driver, getNewButton(environment, mode, 60), "New Button", action.BOOLEAN)) {
			if (sendKeys(driver, getPartnershipLegalName(environment, mode, 60), partnershipLegalName,
					"Partnership Legal Name", action.BOOLEAN)) {
				if (sendKeys(driver, getFundTextBox(environment, mode, 60), fund, "Fund Text Box", action.BOOLEAN)) {
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						ThreadSleep(1000);
						if (click(driver,
								FindElement(driver,
										"//*[contains(@class,'slds-listbox__option-text')]/*[@title='" + fund + "']",
										"fund Name List", action.THROWEXCEPTION, 30),
								fund + "   :   fund Name", action.BOOLEAN)) {
							appLog.info(fund + "  is present in list.");
						} else {
							appLog.info(fund + "  is not present in the list.");
						}
					}
					if (click(driver, getCustomTabSaveBtn(projectName, 60), "Save Button", action.BOOLEAN)) {
						if (getPartnershipNameInViewMode(environment, mode, 60, partnershipLegalName) != null) {
							String partnershipName = getText(driver,
									getPartnershipNameInViewMode(environment, mode, 60, partnershipLegalName),
									"Partnership name in view mode", action.BOOLEAN);
							if (partnershipName.equalsIgnoreCase(partnershipLegalName)) {
								appLog.info("Partnership created successfully.:" + partnershipLegalName);
								return true;
							} else {
								appLog.error("Partnership is not created successfully." + partnershipLegalName);
							}
						} else {
							appLog.error("Partnership name is not displaying");
						}
					} else {
						appLog.error("Not able to click on save button");
					}
				} else {
					appLog.error("Not able to enter value in fund text box");
				}
			} else {
				appLog.error("Not able to enter value in partnershp legal name text box");
			}
		} else {
			appLog.error("Not able to click on new button so we cannot create partnership");
		}
		return false;
	}

	public String getCommitmentID(String mode, String LPName) {
		List<WebElement> ele = new ArrayList<WebElement>();
		String id = "";
		String xpath = "";
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			xpath = "//td//*[text()='" + LPName
					+ "']/ancestor::*/preceding-sibling::th[@data-label='Commitment ID']//a//span";
		} else {
			xpath = "//th[text()='Commitment ID']/../../tr//td/a[text()='" + LPName + "']/../preceding-sibling::th/a";
		}
		ele = FindElements(driver, xpath, "commitment id list");
		if (!ele.isEmpty()) {
			id = ele.get(ele.size() - 1).getText().trim();
			log(LogStatus.INFO, LPName + " commitment id is: " + id, YesNo.No);
		} else {
			log(LogStatus.ERROR, "Not able to get commitment of " + LPName + " LP from Partnership page", YesNo.Yes);
		}
		return id;
	}

	public boolean verifyPartnerShipRelatedList(String environment, String mode, PageName pageName, String commitmentId,
			String LPNameOrPartnerShipName, String companyName, String commitmentAmount) {
		WebElement ele = null;
		String baseXapth = "", xpath = "";
		boolean flag = true;
		CommonLib.refresh(driver);
		if (commitmentAmount != null) {
			commitmentAmount = convertNumberAccordingToFormatWithCurrencySymbol(commitmentAmount, "0,000.0");
		}
		if (pageName.toString().equalsIgnoreCase(PageName.PartnershipsPage.toString())
				|| pageName.toString().equalsIgnoreCase(PageName.LimitedPartnerPage.toString())
				|| pageName.toString().equalsIgnoreCase(PageName.CompanyPage.toString())) {
			if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
				baseXapth = "//h3[text()='Commitments']/ancestor::div[contains(@class,'bRelatedList')]//div[@class='pbBody']//tr//th/a[contains(text(),'"
						+ commitmentId + "')]/../following-sibling::td/a[text()='" + LPNameOrPartnerShipName + "']";

				if (companyName != null) {
					if (pageName.toString().equalsIgnoreCase(PageName.LimitedPartnerPage.toString())) {
						xpath = baseXapth + "/../preceding-sibling::td/a[text()='" + companyName + "']/..";
					} else {
						xpath = baseXapth + "/../following-sibling::td/a[text()='" + companyName + "']/..";
					}

				} else {
					if (pageName.toString().equalsIgnoreCase(PageName.LimitedPartnerPage.toString())) {
						xpath = baseXapth + "/../preceding-sibling::td[1]";
					} else {
						xpath = baseXapth + "/../following-sibling::td[1]";
					}
					ele = FindElement(driver, xpath, "company name", action.SCROLLANDBOOLEAN, 10);
					if (ele != null) {
						String aa = ele.getText().trim();
						if (aa.isEmpty()) {
							log(LogStatus.INFO, "Company name is empty", YesNo.No);
						} else {
							log(LogStatus.ERROR, "Company is not an empty. Actual Result: " + aa, YesNo.Yes);
							flag = false;
						}
					}
				}
				if (commitmentAmount != null) {
					xpath = xpath + "/following-sibling::td[text()='" + commitmentAmount + "']";
				} else {
					xpath = xpath + "/following-sibling::td";
					ele = FindElement(driver, xpath, "commitment amount", action.SCROLLANDBOOLEAN, 10);
					if (ele != null) {
						String aa = ele.getText().trim();
						if (aa.isEmpty()) {
							log(LogStatus.INFO, "Commitment amount is empty", YesNo.No);
						} else {
							log(LogStatus.ERROR, "Commitment amount is not an empty Actual Result: " + aa, YesNo.Yes);
							flag = false;
						}
					}
				}

			} else {
				baseXapth = "//*[text()='" + commitmentId
						+ "']/ancestor::*[@data-label='Commitment ID']/following-sibling::td//*[text()='"
						+ LPNameOrPartnerShipName + "']";
				if (companyName != null) {

					if (pageName.toString().equalsIgnoreCase(PageName.LimitedPartnerPage.toString())) {
						xpath = baseXapth + "/../../preceding-sibling::td/span/a[text()='" + companyName + "']";
					} else {
						xpath = baseXapth + "/ancestor::*/following-sibling::*[@data-label='Company']//slot[text()='"
								+ companyName + "']";
					}
				} else {
					if (pageName.toString().equalsIgnoreCase(PageName.LimitedPartnerPage.toString())) {
						xpath = baseXapth + "/../../preceding-sibling::td[1]/span/span";
					} else {
						xpath = baseXapth + "/ancestor::*/following-sibling::*[@data-label='Company']//slot";
					}
					ele = FindElement(driver, xpath, "company name", action.SCROLLANDBOOLEAN, 10);
					if (ele != null) {
						String aa = ele.getText().trim();
						if (aa.isEmpty()) {
							log(LogStatus.INFO, "Company name is empty", YesNo.No);
						} else {
							log(LogStatus.ERROR, "Company is not an empty. Actual Result: " + aa, YesNo.Yes);
							flag = false;
						}
					}
				}
				if (commitmentAmount != null) {
					xpath = xpath + "/ancestor::*/following-sibling::*[@data-label='Commitment Amount']//*[text()='"
							+ commitmentAmount + "']";
				} else {
					xpath = xpath
							+ "/ancestor::*/following-sibling::*[@data-label='Commitment Amount']//lst-formatted-text";
					ele = FindElement(driver, xpath, "commitment amount", action.SCROLLANDBOOLEAN, 10);
					if (ele != null) {
						String aa = ele.getText().trim();
						if (aa.isEmpty()) {
							log(LogStatus.INFO, "Commitment amount is empty", YesNo.No);
						} else {
							log(LogStatus.ERROR, "Commitment amount is not an empty Actual Result: " + aa, YesNo.Yes);
							flag = false;
						}
					}
				}
			}
		}
		ele = FindElement(driver, xpath, "grid row data", action.SCROLLANDBOOLEAN, 10);
		if (ele != null) {
			log(LogStatus.INFO, "Grid Row data is found successfully in " + pageName.toString(), YesNo.No);
		} else {
			log(LogStatus.INFO, "Grid Row data is not found in " + pageName.toString(), YesNo.Yes);
			flag = false;
		}
		return flag;

	}

	public boolean clickOnCreatedPartnership(String environment, String mode, String partnershipLegalName) {
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			if (click(driver, getGoButton(60), "Go Button", action.BOOLEAN)) {
				WebElement partnershipName = FindElement(driver,
						"//div[@class='x-panel-bwrap']//span[text()='" + partnershipLegalName + "']",
						"Partnership Legal Name", action.BOOLEAN, 60);
				if (partnershipName != null) {
					if (click(driver, partnershipName, "Partnership Name", action.SCROLLANDBOOLEAN)) {
						appLog.info("Clicked on partnership name" + partnershipLegalName + "successfully.");
						return true;
					} else {
						appLog.error("Not able to click on partnership name");
					}
				} else {
					appLog.error("Partnership name is not displaying");
				}
			} else {
				appLog.error("Not able to click on go button so cannot click on created partnership");
			}
		} else {
			if (clickOnAlreadyCreated_Lighting(environment, mode, TabName.PartnershipsTab, partnershipLegalName, 30)) {
				appLog.info("Clicked on partnership name" + partnershipLegalName + "successfully.");
				return true;
			} else {
				appLog.error("Not able to click on partnership name : " + partnershipLegalName);
			}
		}
		return false;
	}

}
