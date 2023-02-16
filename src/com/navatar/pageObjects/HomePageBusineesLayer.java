package com.navatar.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.Test;

import com.navatar.generic.EnumConstants.AddProspectsTab;
import com.navatar.generic.EnumConstants.CommitmentType;
import com.navatar.generic.EnumConstants.CreateCommitmentPageFieldLabelText;
import com.navatar.generic.EnumConstants.CreatedOrNot;
import com.navatar.generic.EnumConstants.FundraisingContactPageTab;
import com.navatar.generic.EnumConstants.GlobalActionItem;
import com.navatar.generic.EnumConstants.HTMLTAG;
import com.navatar.generic.EnumConstants.IndiviualInvestorFieldLabel;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.PopUpName;
import com.navatar.generic.EnumConstants.SDGCreationLabel;
import com.navatar.generic.EnumConstants.SDGGridName;
import com.navatar.generic.EnumConstants.SearchBasedOnExistingFundsOptions;
import com.navatar.generic.EnumConstants.SortOrder;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.TopOrBottom;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.fundraisingContactActions;
import com.navatar.generic.AppListeners;
import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.NavatarQuickLink;
import com.navatar.generic.EnumConstants.NavigationMenuItems;
import com.navatar.generic.EnumConstants.NewInteractions_DefaultValues;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.searchContactInEmailProspectGrid;
import com.navatar.generic.SoftAssert;
import com.relevantcodes.extentreports.LogStatus;
import static com.navatar.generic.EnumConstants.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.EmailTemplate1_FolderName;
import static com.navatar.generic.CommonVariables.EmailTemplate1_TemplateName;
import static com.navatar.generic.CommonVariables.SmokeReportFolderName;
import static com.navatar.generic.CommonVariables.SmokeReportName;
import static com.navatar.generic.CommonVariables.environment;
import static com.navatar.generic.CommonVariables.mode;
import static com.navatar.generic.AppListeners.*;

public class HomePageBusineesLayer extends HomePage {

	public HomePageBusineesLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/***********************************
	 * Activity Association
	 *********************************/
	/**
	 * @author Azhar Alam
	 * @param environment
	 * @param mode
	 * @return true/false
	 * @description this method is used to click on setup link on home page
	 */
	public boolean clickOnSetUpLink() {
		boolean flag = false;
		if (click(driver, getSettingLink_Lighting(20), "setting icon", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "setting icon", YesNo.No);

		} else {
			log(LogStatus.ERROR, "clicked on setting icon", YesNo.Yes);
			return flag;
		}
		if (click(driver, getUserMenuSetupLink(20), "setup link", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "clicked on setup link", YesNo.No);
			flag = true;
		} else {
			log(LogStatus.ERROR, "user setup link", YesNo.Yes);
		}
		return flag;
	}

	public List<String> verifyContactNameInReviewInvestorList(List<String> contactName, List<String> accountName) {
		List<String> result = new ArrayList<String>();
		HashMap<String, String> uniqueAccountName = new HashMap<String, String>();
		int count = 0;
		if (!contactName.isEmpty() && !accountName.isEmpty()) {
			WebElement ele = null;
			for (int i = 0; i < accountName.size(); i++) {
				count = 0;
				for (int j = 0; j < accountName.size(); j++) {
					if (accountName.get(i).equalsIgnoreCase(accountName.get(j))) {
						count++;
						uniqueAccountName.put(accountName.get(i), String.valueOf(count));
					}
				}
			}
			Set<String> ACName = uniqueAccountName.keySet();
			Iterator<String> itr = ACName.iterator();
			while (itr.hasNext()) {
				String gotAccountName = itr.next();
				String CountNumber = uniqueAccountName.get(gotAccountName);
				String xpath = "//span[@id='Institution_of_Selected_Contacts-view-box-middle']//span/a[text()='"
						+ gotAccountName + "']/../following-sibling::span/a[text()='" + CountNumber + " Contact(s)']";
				ele = isDisplayed(driver, FindElement(driver, xpath, "", action.BOOLEAN, 10), "visibility", 10,
						"Selected AccountName" + gotAccountName);
				if (ele != null) {
					log(LogStatus.INFO, gotAccountName + " Account Name and it's contact count number " + CountNumber
							+ " is matched in review investor list", YesNo.No);
				} else {
					log(LogStatus.ERROR, gotAccountName + " Account Name and it's contact count number " + CountNumber
							+ " is not matched in review investor list", YesNo.Yes);
					result.add(gotAccountName + " Account Name and it's contact count number " + CountNumber
							+ " is not matched in review investor list");
				}
			}
		} else {
			log(LogStatus.ERROR,
					"Contact Name and Account Name list is empty so cannot verify contact Name in review investor list",
					YesNo.Yes);
			result.add(
					"Contact Name and Account Name list is empty so cannot verify contact Name in review investor list");
		}
		return result;

	}

	public double getTotalCommitmentAmount() {
		ThreadSleep(5000);
		WebElement ele = getTotalCommitmentAmount(30);
		if (ele != null) {
			return Double.parseDouble(ele.getText().trim().substring(1).replace(",", ""));
		}
		return 0.00;
	}

	/**
	 * @author Ankit Jaiswal
	 * @param excelpath
	 * @param variableName
	 * @param sheetName
	 * @return
	 */
	public boolean writeTotalAmountInExcelSheet(String excelpath, String variableName, String sheetName) {
		if (getTotalCommitmentAmount() != 0.00) {
			return ExcelUtils.writeData(excelpath,
					Double.parseDouble(ExcelUtils.readData(excelpath, sheetName, excelLabel.Variable_Name, variableName,
							excelLabel.Total_Commitments)) + getTotalCommitmentAmount(),
					sheetName, excelLabel.Variable_Name, variableName, excelLabel.Total_Commitments);
		} else {
			return false;
		}
	}

	public SoftAssert verifyCreateCommitmentGenralOrFundraisingInfo(String environment, String mode,
			String[][] labelAndValue) {
		SoftAssert saa = new SoftAssert();
		WebElement ele = null;
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			switchToFrame(driver, 30, getCreateCommitmentFrame_Lightning(20));
		}
		List<WebElement> companyName = new ArrayList<WebElement>();
		for (String[] splitedlabelAndvalue : labelAndValue) {
			String label = splitedlabelAndvalue[0].replace("_", " ");
			String xpath = "//label[text()='" + label + "']/../following-sibling::td/span";
			if (label.equalsIgnoreCase(CreateCommitmentPageFieldLabelText.Company.toString())) {
				companyName = FindElements(driver, xpath, "company name list");
				if (!companyName.isEmpty()) {
					for (int i = 0; i < companyName.size(); i++) {
						String aa = companyName.get(i).getText().trim();
						if (splitedlabelAndvalue[1].isEmpty()) {
							if (aa.contains(splitedlabelAndvalue[1])) {
								log(LogStatus.INFO, "Company name is balnk. ", YesNo.No);

							} else {
								if (i == companyName.size() - 1) {
									log(LogStatus.ERROR, "Company name is not balnk.  \t Actual : " + aa, YesNo.Yes);
									saa.assertTrue(false, "Company name is not balnk.  \t Actual :" + aa);
								}
							}
						} else {
							if (aa.contains(splitedlabelAndvalue[1])) {
								log(LogStatus.INFO, "Company name is matched. " + splitedlabelAndvalue[1], YesNo.No);
								break;
							} else {
								if (i == companyName.size() - 1) {
									log(LogStatus.ERROR, "Company name is not matched. Expected:  "
											+ splitedlabelAndvalue[1] + "\t Actual : " + aa, YesNo.Yes);
									saa.assertTrue(false, "Company name is not matched. Expected:  "
											+ splitedlabelAndvalue[1] + "\t Actual : " + aa);
								}
							}
						}
					}
				} else {
					log(LogStatus.ERROR,
							"Company name list is not visible so cannot verify company name " + splitedlabelAndvalue[1],
							YesNo.Yes);
					saa.assertTrue(false, "Company name list is not visible so cannot verify company name "
							+ splitedlabelAndvalue[1]);
				}

			} else {
				ele = FindElement(driver, xpath, splitedlabelAndvalue[0] + " label text", action.SCROLLANDBOOLEAN, 30);
				if (ele != null) {
					String aa = ele.getText().trim();
					if (splitedlabelAndvalue[1].contains(aa)) {
						log(LogStatus.INFO, splitedlabelAndvalue[0] + " Field value " + splitedlabelAndvalue[1]
								+ " is displaying on create commitment page", YesNo.No);
					} else {
						log(LogStatus.ERROR, splitedlabelAndvalue[1] + " Field value " + splitedlabelAndvalue[1]
								+ " is not displaying on create commitment page", YesNo.Yes);
						saa.assertTrue(false, splitedlabelAndvalue[1] + " Field value " + splitedlabelAndvalue[1]
								+ " is not displaying on create commitment page");
					}
				} else {
					log(LogStatus.ERROR, splitedlabelAndvalue[0] + " label is not visible so cannot verify "
							+ splitedlabelAndvalue[1] + " value on create commitment page", YesNo.Yes);
					saa.assertTrue(false, splitedlabelAndvalue[0] + " label is not visible so cannot verify "
							+ splitedlabelAndvalue[1] + " value on create commitment page");
				}
			}
		}
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			switchToDefaultContent(driver);
		}
		return saa;

	}

	public SoftAssert deleteRowsFromCreateCommitmentGenralOrFundraisingInfo(String environment, String mode) {
		SoftAssert saa = new SoftAssert();
		List<WebElement> ele = FindElements(driver,
				"//h2[text()='Commitment Information']/..//table//a[@title='Delete'][contains(@style,'display:inline;')]",
				"delete icon");
		if (!ele.isEmpty()) {
			for (int i = 0; i < ele.size(); i++) {
				if (click(driver, ele.get(i), "delete icon", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on delete icon", YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Not able to click on delete icon so cannot delete row from CreateCommitmentGenralOrFundraisingInfo",
							YesNo.Yes);
					saa.assertTrue(false,
							"Not able to click on delete icon so cannot delete row from CreateCommitmentGenralOrFundraisingInfo");
				}
			}
		} else {
			log(LogStatus.ERROR, "Delete Icon is not visible so cannot click on delete icons", YesNo.Yes);
			saa.assertTrue(false, "Delete Icon is not visible so cannot click on delete icons");
		}
		return saa;
	}

	public boolean createNavigationItem(String environment, String mode, String[][] labelWithValue, int timeOut) {
		String navigationTab = "Navigation";
		boolean flag = false;
		if (clickOnTab(environment, navigationTab)) {
			log(LogStatus.INFO, "Click on Tab : " + navigationTab, YesNo.No);
			ThreadSleep(15000);
			if (clickUsingJavaScript(driver, getNewButton(environment, mode, 10), "new button")) {
				log(LogStatus.INFO, "Click on new button going to create ", YesNo.No);
				enteringValueForNavigation(labelWithValue, action.BOOLEAN, timeOut);
				if (click(driver, getNavigationTabSaveBtn(environment, 10), "save button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.ERROR, "Click on save Button ", YesNo.No);
					ThreadSleep(5000);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Not Able to Click on save Button ", YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Not Able to Click on save Button ");
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on new button so cannot create", YesNo.Yes);
				BaseLib.sa.assertTrue(false, "Not Able to Click on new button so cannot create");

			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on Tab : " + navigationTab, YesNo.Yes);
			BaseLib.sa.assertTrue(false, "Not Able to Click on Tab : " + navigationTab);
		}
		return flag;
	}

	public void enteringValueForNavigation(String[][] navigationFieldWithValues, action action, int timeOut) {
		String navigationField;
		String navigationvalue;
		WebElement ele;
		for (String[] navigationFieldAndvalue : navigationFieldWithValues) {
			navigationField = navigationFieldAndvalue[0];
			navigationvalue = navigationFieldAndvalue[1];
			ele = getNavigationField(navigationField, action, 20);

			if (navigationField.equalsIgnoreCase("Navigation Type")) {

				if (click(driver, getNavigationTypeLabel(timeOut), navigationField, action.SCROLLANDBOOLEAN)) {
					ThreadSleep(2000);
					appLog.error("Clicked on Deal stage");

					String xpath = "//span[@title='" + navigationvalue + "']";
					ele = FindElement(driver, xpath, navigationvalue, action.SCROLLANDBOOLEAN, timeOut);
					ThreadSleep(2000);
					if (click(driver, ele, navigationvalue, action.SCROLLANDBOOLEAN)) {
						appLog.info("Selected navigation type : " + navigationvalue);
					} else {
						appLog.error("Not able to Select on navigation type : " + navigationvalue);
					}

				} else {
					appLog.error("Not able to Click on Deal stage : ");
				}
			} else {
				if (sendKeys(driver, ele, navigationvalue, navigationField, action)) {
					log(LogStatus.INFO, "Able to enter " + navigationField, YesNo.No);

					if (navigationField.equalsIgnoreCase("Parent")) {
						ThreadSleep(10000);
						if (click(driver, getItemInList(navigationvalue, action.BOOLEAN, 20),
								navigationvalue + "   :  Parent Name", action.BOOLEAN)) {
							log(LogStatus.INFO, navigationvalue + " is available", YesNo.No);
						} else {
							log(LogStatus.ERROR, navigationvalue + " is not available", YesNo.Yes);
							BaseLib.sa.assertTrue(false, navigationvalue + " is not available");

						}
					}

				} else {
					log(LogStatus.ERROR, "Not Able to enter " + navigationField, YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Not Able to enter " + navigationField);
				}
			}

		}

	}

	public WebElement getItemInList(String itemName, action action, int timeOut) {
		String xpath = "//*[@title='" + itemName + "']//strong[text()='" + itemName.split(" ")[0] + "']";
		WebElement ele = FindElement(driver, xpath, itemName, action, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, itemName);
	}

	public WebElement getNavigationField(String navigationField, action action, int timeOut) {
		navigationField = navigationField.replace("_", " ");
		if (navigationField.equalsIgnoreCase("Parent")) {
			String xpath = "//*[text()='Parent']/following-sibling::*//*//button";
			WebElement ele = FindElement(driver, xpath, navigationField, action, 5);
			click(driver, ele, "PARENT Cross icon", action.BOOLEAN);
		}
		String xpath = "//*[text()='" + navigationField + "']/following-sibling::div//input";
		WebElement ele = FindElement(driver, xpath, navigationField, action, timeOut);
		scrollDownThroughWebelement(driver, ele, navigationField);
		return isDisplayed(driver, ele, "Visibility", timeOut, navigationField);
	}

	@FindBy(xpath = "//*[text()='Navigation Type']/..//div//button")
	private WebElement navigationTypeLabel;

	/**
	 * @return the navigationTypeLabel
	 */
	public WebElement getNavigationTypeLabel(int timeOut) {

		return isDisplayed(driver, navigationTypeLabel, "Visibility", timeOut, "Navigation TYpe Label");

	}

	/**
	 * @author Ankit Jaiswal
	 * @param accountName
	 * @return
	 */
	public boolean clickOnReviewInvestorListFundraisingContactLink(String accountName) {
		WebElement ele = null;
		String xpath = "//span[contains(@id,'Institution_of_Selected_Contacts-cell')]/a[text()='" + accountName
				+ "']/../following-sibling::span/a";
		ele = isDisplayed(
				driver, FindElement(driver, xpath, accountName + " account fundraising contact link",
						action.SCROLLANDBOOLEAN, 10),
				"visibility", 10, accountName + " account fundraising contact link");
		if (ele != null) {
			if (click(driver, ele, accountName + " account fundraising contact link", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on " + accountName + " account fundraising contact link", YesNo.No);
				return true;
			} else {
				log(LogStatus.ERROR, "Not able to click on " + accountName + " account fundraising contact link",
						YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, accountName + " account fundraising contact link is not visible so cannot click on it",
					YesNo.Yes);
		}
		return false;
	}

	/**
	 * @author Ankit Jaiswal
	 * @param fundRaisingName
	 * @param stage
	 * @param investmentLikelyAmount
	 * @param role
	 * @param primary
	 * @param date
	 * @return
	 */
	public SoftAssert VerifyPastFundraisingData(String fundRaisingName, String stage, String investmentLikelyAmount,
			String role, String primary, String date) {
		SoftAssert result = new SoftAssert();
		WebElement ele = null;
		List<WebElement> fundraisingNameList = getFundraisingNameList();
		List<WebElement> stageList = getStageList();
		List<WebElement> investmentList = getInvestmentLikelyAmountList();
		List<WebElement> roleList = getRoleList();
//		List<WebElement> primaryList= getPrimaryList();
		List<WebElement> createdDateList = getCreatedDateList();
		String[] splFRName = fundRaisingName.split("<break>");
		String[] splStage = stage.split("<break>");
		String[] splInvesmentLikely = investmentLikelyAmount.split("<break>");
		String[] splRole = role.split("<break>");
		String[] splPrimary = primary.split("<break>");
		String[] splDate = date.split("<break>");
		if (!fundraisingNameList.isEmpty()) {
			for (int i = 0; i < fundraisingNameList.size(); i++) {
				for (int j = 0; j < splFRName.length; j++) {
					if (fundraisingNameList.get(i).getText().trim().contains(splFRName[j])
							&& stageList.get(i).getText().trim().contains(splStage[j])
							&& investmentList.get(i).getText().trim()
									.contains(convertNumberAccordingToFormatWithoutCurrencySymbol(splInvesmentLikely[j],
											"0,000.00"))
							&& roleList.get(i).getText().trim().contains(splRole[j])
							&& verifyDate(createdDateList.get(i).getText().trim(), null,
									"created date in past fundraising pop up")) {
						log(LogStatus.INFO, splFRName[j] + " " + splStage[j] + " " + splInvesmentLikely[j] + " "
								+ splRole[j] + " " + splDate[j] + "  is verified in past fundraising grid", YesNo.No);
						break;
					} else {
						if (i == fundraisingNameList.size() - 1) {
							log(LogStatus.ERROR,
									splFRName[j] + " " + splStage[j] + " " + splInvesmentLikely[j] + " " + splRole[j]
											+ " " + splDate[j] + "  is not verified in past fundraising grid",
									YesNo.Yes);
							result.assertTrue(false,
									splFRName[j] + " " + splStage[j] + " " + splInvesmentLikely[j] + " " + splRole[j]
											+ " " + splDate[j] + "  is not verified in past fundraising grid");
						}
					}
				}
			}
			for (int j = 0; j < splFRName.length; j++) {
				String xpath = "//span[contains(@id,'Past_FundraisingsContact-cell-0')]/a[text()='" + splFRName[j]
						+ "']/../following-sibling::span[4]/img";
				ele = isDisplayed(driver, FindElement(driver, xpath, "", action.BOOLEAN, 3), "visibility", 3,
						splFRName[j] + " primary data");
				if (!splPrimary[j].isEmpty() && splPrimary[j].contains("Checked")) {
					if (ele != null) {
						log(LogStatus.INFO, "Primary is checked for fundraising " + splFRName[j], YesNo.No);
					} else {
						log(LogStatus.ERROR, "Primary is not checked for fundraising " + splFRName[j], YesNo.Yes);
						result.assertTrue(false, "Primary is not checked for fundraising " + splFRName[j]);
					}

				} else if (splPrimary[j].isEmpty() || splPrimary[j].contains("Not Checked")) {
					if (ele == null) {
						log(LogStatus.INFO, "Primary is not checked for fundraising " + splFRName[j], YesNo.No);
					} else {
						log(LogStatus.ERROR, "Primary is checked for fundraising " + splFRName[j], YesNo.Yes);
						result.assertTrue(false, "Primary is checked for fundraising " + splFRName[j]);
					}
				}
			}
		} else {
			log(LogStatus.ERROR,
					"Fundraising Name is not visible on past fundraising pop up so cannot verify data on it",
					YesNo.Yes);
			result.assertTrue(false,
					"Fundraising Name is not visible on past fundraising pop up so cannot verify data on it");
		}
		return result;

	}

	/**
	 * @author Ankit Jaiswal
	 * @param ContactName
	 * @param AccountName
	 * @return String xpath
	 */
	public static String xpathOfSelectInvestorsInfoIcon(String ContactName, String AccountName) {
		String xpath = "//span[@id='Select_from_Search_Results-view-box-middle']//span/div/a[text()='" + AccountName
				+ "']/../../preceding-sibling::span//a";
		String xpath1 = "/following-sibling::a//img";
		for (int i = 0; i < ContactName.split(" ").length; i++) {
			xpath = xpath + "[contains(text(),'" + ContactName.split(" ")[i] + "')]";
		}
		return xpath + xpath1;
	}

	/**
	 * @author Ankit Jaiswal
	 * @param ContactName
	 * @param AccountName
	 * @return String xpath
	 */
	public static String xpathOfSelectInvestorsContactName(String ContactName, String AccountName) {
		String xpath = "//span[@id='Select_from_Search_Results-view-box-middle']//span/div/a[text()='" + AccountName
				+ "']/../../preceding-sibling::span//a";
		for (int i = 0; i < ContactName.split(" ").length; i++) {
			xpath = xpath + "[contains(text(),'" + ContactName.split(" ")[i] + "')]";
		}
		return xpath;
	}

	/**
	 * @author Ankit Jaiswal
	 * @param contactName
	 * @param accountName
	 * @return
	 */
	public boolean clickOnInfoIcon(String contactName, String accountName) {
		WebElement ele = null;
		ele = isDisplayed(driver, FindElement(driver, xpathOfSelectInvestorsContactName(contactName, accountName), "",
				action.BOOLEAN, 10), "visibility", 10, contactName + " text ");
		if (ele != null) {
			if (mouseOverOperation(driver, ele)) {
				log(LogStatus.INFO, "mouse over on contact name " + contactName, YesNo.No);
				ele = isDisplayed(driver, FindElement(driver, xpathOfSelectInvestorsInfoIcon(contactName, accountName),
						"", action.BOOLEAN, 10), "visibility", 10, contactName + " info icon");
				if (ele != null) {
					if (click(driver, ele, contactName + " info icon", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on contact name " + contactName, YesNo.No);
						return true;
					} else {
						log(LogStatus.ERROR, "Not able to click on contact name " + contactName + "  info icon",
								YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on contact " + contactName
							+ " info icon so cannot verify past fundraising data", YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Not able to mouse over on contact name " + contactName
						+ " so cannot click on contact Name info icon", YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR,
					contactName + " contact name is not visible so cannot mouse over on contact name " + contactName,
					YesNo.Yes);
		}
		return false;

	}

	/**
	 * @author Ankit Jaiswal
	 * @param fieldsAndvalue
	 * @return
	 */
	public boolean selectDefaultFundraisingValue(List<String> fieldsAndvalue) {
		String[] splfield = fieldsAndvalue.get(0).split("<break>");
		String[] splvalue = fieldsAndvalue.get(0).split("<break>");
		WebElement ele = null;
//		for (int i = 0; i < splfield.length; i++) {
		String xpath = "//select[@id='a51aa']";
		ele = isDisplayed(driver, FindElement(driver, xpath, "", action.BOOLEAN, 10), "visibility", 10,
				"field drop down list");
		if (selectVisibleTextFromDropDown(driver, ele, "field drop down list", splvalue[0])) {
			log(LogStatus.INFO, "select field " + splfield[0], YesNo.No);
			String xpath1 = "//input[@id='criteriatextbox51']";
			ele = isDisplayed(driver, FindElement(driver, xpath1, "", action.BOOLEAN, 10), "visibility", 10,
					"Value text box");
			if (sendKeys(driver, ele, splvalue[1], "Value Text Box", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Passed Value " + splvalue[1], YesNo.No);
				return true;
			}
		} else {
			log(LogStatus.ERROR,
					"Not able to select field " + splfield[0] + " so cannot select default fundraising value",
					YesNo.Yes);
		}
//		}
		return false;
	}

	/**
	 * @author Ankit Jaiswal
	 * @param contactNamelist
	 * @param accountNamelist
	 * @param ContactFullNameAndItsAction
	 * @return
	 */
	public List<String> verifyFundRaisingContactAndSetRolePrimaryContact(List<String> contactNamelist,
			List<String> accountNamelist, List<String> ContactFullNameAndItsAction) {
		List<String> result = new ArrayList<String>();
		WebElement ele = null;
		String xpath = "";
		String roleXpath = "";
		String roleDropDownSign = "";
		if (!contactNamelist.isEmpty() && !accountNamelist.isEmpty()) {
			for (int i = 0; i < contactNamelist.size(); i++) {
				String contactXpath = "//span[contains(@id,'gridSelectedContact-row-')]/span[text()='"
						+ contactNamelist.get(i) + "']/following-sibling::span[text()='" + accountNamelist.get(i)
						+ "']";
				ele = isDisplayed(driver, FindElement(driver, contactXpath, contactNamelist.get(i) + " text",
						action.SCROLLANDBOOLEAN, 10), "visibility", 10, contactNamelist.get(i) + " text");
				if (ele != null) {
					log(LogStatus.INFO, accountNamelist.get(i) + " account name contact name " + contactNamelist.get(i)
							+ " is displaying", YesNo.No);
				} else {
					log(LogStatus.ERROR, accountNamelist.get(i) + " account name contact name " + contactNamelist.get(i)
							+ " is not displaying", YesNo.Yes);
					result.add(accountNamelist.get(i) + " account name contact name " + contactNamelist.get(i)
							+ " is not displaying");
				}
			}
			for (int i = 0; i < ContactFullNameAndItsAction.size(); i++) {
				xpath = "";
				String contactName = ContactFullNameAndItsAction.get(i).split("<break>")[0];
				String actions = ContactFullNameAndItsAction.get(i).split("<break>")[1];
				System.err.println(contactName);
				System.err.println(actions);
				if (actions.equalsIgnoreCase(fundraisingContactActions.Remove.toString())) {
					xpath = "//span[contains(@id,'gridSelectedContact-row-')]/span[text()='" + contactName
							+ "']/../span/div/img";
				} else if (actions.equalsIgnoreCase(fundraisingContactActions.PrimaryContact.toString())) {
					xpath = "//span[contains(@id,'gridSelectedContact-row-')]/span[text()='" + contactName
							+ "']/following-sibling::span/span/span[contains(@class,'aw-item-marker')]/../..";
				} else {
					roleDropDownSign = "//span[contains(@id,'gridSelectedContact-row-')]/span[text()='" + contactName
							+ "']/following-sibling::span/span/table//tr[2]";
					roleXpath = "//span[contains(@id,'gridSelectedContact-popup-4-0-item-')]/span/span[text()='"
							+ actions + "']";
				}
				if (!xpath.isEmpty()) {
					ele = isDisplayed(driver,
							FindElement(driver, xpath, contactName + " " + actions + " element",
									action.SCROLLANDBOOLEAN, 10),
							"visibility", 10, contactName + " " + actions + " element");
					if (click(driver, ele, contactName + " " + actions + " " + " element", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on contact name " + contactName + " " + actions + " action",
								YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"Not able to click on Contact Name " + contactName + " " + actions + " action",
								YesNo.Yes);
						result.add("Not able to click on Contact Name " + contactName + " " + actions + " action");
					}
				} else {
					if (actions
							.equalsIgnoreCase(fundraisingContactActions.AddNewContactInFundraisingContact.toString())) {
						xpath = "//div[contains(@class,'ContactAccess_fancybox')]//div[@id='displayFilterText1']//img";
						ele = isDisplayed(driver,
								FindElement(driver, xpath, "expand icon", action.SCROLLANDBOOLEAN, 60), "visibility",
								60, "expand icon");
						if (ele != null) {
							if (click(driver, ele, "expand icon", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.ERROR, "clicked on expand icon ", YesNo.No);
								xpath = "//input[@id='searchcon_grid_select_contact']";
								ele = isDisplayed(driver,
										FindElement(driver, xpath, "expand icon", action.SCROLLANDBOOLEAN, 5),
										"visibility", 5, "expand icon");
								if (ele != null) {
									if (sendKeys(driver, ele, contactName, contactName + " search text box",
											action.SCROLLANDBOOLEAN)) {
										log(LogStatus.ERROR, "passed value in serach text box " + contactName,
												YesNo.No);
										if (click(driver, getFundraisingContactSearchTextSearchIcon(5), "search icon",
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.ERROR, "clicked on Search icon", YesNo.No);
											xpath = "//a[text()='" + contactName
													+ "']/../preceding-sibling::span/span/span[1]";
											ele = /* isDisplayed(driver, */ FindElement(driver, xpath, "expand icon",
													action.SCROLLANDBOOLEAN, 60)/* , "visibility",60,"expand icon") */;
											if (ele != null) {
												if (click(driver, ele, contactName + " check box",
														action.SCROLLANDBOOLEAN)) {
													log(LogStatus.ERROR,
															"clicked on contact name check box " + contactName,
															YesNo.No);
													xpath = "//button[@id='addSelConBtn']";
													ele = isDisplayed(driver,
															FindElement(driver, xpath, "expand icon",
																	action.SCROLLANDBOOLEAN, 60),
															"visibility", 60, "expand icon");
													if (click(driver, ele, "add selected contacts button",
															action.SCROLLANDBOOLEAN)) {
														log(LogStatus.ERROR, "clicked on add selected contacts button",
																YesNo.No);

													} else {
														log(LogStatus.ERROR,
																"Not able to click on add selected contacts button",
																YesNo.Yes);
														result.add("Not able to click on add selected contacts button");
													}

												} else {
													log(LogStatus.ERROR, "Not able to select contact Name "
															+ contactName + " in fundraising contact grid", YesNo.Yes);
													result.add("Not able to select contact Name " + contactName
															+ " in fundraising contact grid");
												}

											} else {
												log(LogStatus.ERROR,
														"Search text Box is not visible so cannot add contact "
																+ contactName + " in fundraising contact grid",
														YesNo.Yes);
												result.add("Search text Box is not visible so cannot add contact "
														+ contactName + " in fundraising contact grid");
											}
										} else {
											log(LogStatus.ERROR,
													"Not able to click on Search icon so cannot add contact "
															+ contactName + " in fundraising contact grid",
													YesNo.Yes);
											result.add("Not able to click on Search icon so cannot add contact "
													+ contactName + " in fundraising contact grid");
										}
									} else {
										log(LogStatus.ERROR,
												"Not abel to pass value in serach text box so cannot add contact "
														+ contactName + " in fundraising contact grid",
												YesNo.Yes);
										result.add("Not abel to pass value in serach text box so cannot add contact "
												+ contactName + " in fundraising contact grid");
									}

								} else {
									log(LogStatus.ERROR, "Search text Box is not visible so cannot add contact "
											+ contactName + " in fundraising contact grid", YesNo.Yes);
									result.add("Search text Box is not visible so cannot add contact " + contactName
											+ " in fundraising contact grid");
								}

							} else {
								log(LogStatus.ERROR, "Not able to click on expand icon so cannot add contact "
										+ contactName + " in fundraising contact grid", YesNo.Yes);
								result.add("Not able to click on expand icon so cannot add contact " + contactName
										+ " in fundraising contact grid");
							}

						}
					} else {
						ele = isDisplayed(driver,
								FindElement(driver, roleDropDownSign, contactName + " role drop down Sign",
										action.SCROLLANDBOOLEAN, 10),
								"visibility", 10, contactName + " role drop down sign");
						ThreadSleep(3000);
						if (click(driver, ele, contactName + " role drop down Sign", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on contact name " + contactName + " role drop down Sign",
									YesNo.No);
							ThreadSleep(3000);
							ele = isDisplayed(driver,
									FindElement(driver, roleXpath, actions + " role drop down Value",
											action.SCROLLANDBOOLEAN, 10),
									"visibility", 10, actions + " role drop down Value");
							if (clickUsingJavaScript(driver, ele, "click on dropdown list")) {
								ThreadSleep(3000);
								log(LogStatus.INFO,
										"clicked on contact name " + contactName + " role drop down Value: " + actions,
										YesNo.No);
							} else {
								log(LogStatus.ERROR,
										"Not able to click on contact name " + contactName
												+ " role drop down Sign so cannot select " + actions + " value",
										YesNo.Yes);
								result.add("Not able to click on contact name " + contactName
										+ " role drop down Sign so cannot select " + actions + " value");
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on contact name " + contactName
									+ " role drop down Sign so cannot select " + actions + " value", YesNo.Yes);
							result.add("Not able to click on contact name " + contactName
									+ " role drop down Sign so cannot select " + actions + " value");
						}
					}
				}

			}
		} else {
			log(LogStatus.ERROR, "ContactName and AccountName list is empty so cannot verify contact and account name.",
					YesNo.Yes);
			result.add("ContactName and AccountName list is empty so cannot verify contact and account name.");
		}
		return result;
	}

	public SoftAssert deleteRowsFromCreateFundraisingDefaultFundraisingValues(String environment, String mode) {
		SoftAssert saa = new SoftAssert();
		List<WebElement> ele = FindElements(driver, "//div[@id='AddRemoveSection']//a[@title='Remove Row']",
				"delete icon");
		if (!ele.isEmpty()) {
			for (int i = 0; i < ele.size(); i++) {
				if (click(driver, ele.get(i), "delete icon", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on delete icon", YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Not able to click on delete icon so cannot delete row from Create Fundraising Default Fundraising Values",
							YesNo.Yes);
					saa.assertTrue(false,
							"Not able to click on delete icon so cannot delete row from Create Fundraising Default Fundraising Values");
				}
			}
		} else {
			log(LogStatus.ERROR, "Delete Icon is not visible so cannot click on delete icons", YesNo.Yes);
			saa.assertTrue(false, "Delete Icon is not visible so cannot click on delete icons");
		}
		return saa;
	}

	/**
	 * @param projectName
	 * @param searchValue
	 * @param action
	 * @param timeOut
	 * @return true if able to click on Navatar Edge
	 */
	public boolean clickOnNavatarEdgeLinkHomePage(String searchValue, action action, int timeOut) {
		boolean flag = false;
		String xpath = "//div[@class='flexipagePage']//span[text()='" + searchValue + "']";
		WebElement ele = FindElement(driver, xpath, searchValue, action, timeOut);
		WebElement ele1 = null;
		if (click(driver, ele, searchValue, action)) {
			log(LogStatus.INFO, "able to click on " + searchValue, YesNo.No);
			flag = true;
			xpath = "//div[@class='flexipagePage']//h2[text()='" + searchValue + "']";
			ele = FindElement(driver, xpath, searchValue + " header", action, timeOut);
			ele = isDisplayed(driver, ele, "Visibility", timeOut, searchValue + " Header");

			xpath = "//div[@class='flexipagePage']//h2[text()='" + searchValue + "']/..//preceding-sibling::div";
			ele1 = FindElement(driver, xpath, searchValue + " image", action, timeOut);
			ele1 = isDisplayed(driver, ele, "Visibility", timeOut, searchValue + " Image");

			if (ele != null && ele1 != null && getNavatarQuickLinkMinimize_Lighting("", timeOut) != null) {
				log(LogStatus.INFO, "Image , Header and minimize icon Verified after click on " + searchValue,
						YesNo.No);
				flag = true;
			} else {
				log(LogStatus.ERROR, "Image , Header and minimize icon Not Verified after click on " + searchValue,
						YesNo.No);
				BaseLib.sa.assertTrue(false,
						"Image , Header and minimize icon Not Verified after click on " + searchValue);
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + searchValue, YesNo.No);
		}
		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param navigationLabel
	 * @param action
	 * @param timeOut
	 * @return getNavigationLabel
	 */
	public WebElement getNavigationLabel(String navigationLabel, action action, int timeOut) {

		int i = 0;
		String[] nb = navigationLabel.split("/");
		String xpath = "";
		WebElement ele = null;
		for (i = 0; i < nb.length; i++) {
			if (i == 0) {
				xpath = "//div[contains(@id,'treeview')]//*//*[text()='" + nb[i] + "']";
				ele = FindElement(driver, xpath, nb[i], action, timeOut);

			} else {
				xpath = xpath + "/../following-sibling::*/*[text()='" + nb[i] + "']";
			}
			if (nb.length > 1 && i == nb.length - 1) {
				click(driver, ele, nb[i], action);
			}
		}
		ele = FindElement(driver, xpath, navigationLabel, action, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, navigationLabel);

	}

	public boolean ClickOnItemOnNavatarEdge(String searchValue, String bulkActionNavigationLink, action action,
			int timeOut) {
		boolean flag = false;
		WebElement ele;
		if (clickOnNavatarEdgeLinkHomePage(searchValue, action, timeOut)) {
			ele = getNavigationLabel(bulkActionNavigationLink, action.BOOLEAN, 10);
			if (ele != null && click(driver, ele, bulkActionNavigationLink, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on " + bulkActionNavigationLink, YesNo.No);
				flag = true;

			} else {
				log(LogStatus.ERROR, "Not Able to Click on " + bulkActionNavigationLink, YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR,
					"Not Able to Click on " + searchValue + " so cannot click on : " + bulkActionNavigationLink,
					YesNo.Yes);
		}
		return flag;
	}

	/**
	 * @author Akul Bhutani >>>>>>> 02b36c58412349b81abef971feaa2422cf7f8edf
	 * @param environment
	 * @param mode
	 * @return true/false
	 * @description this method is used to click on click on setup link either
	 *              classic or lightning
	 */
	public boolean clickOnSetUpLink(String environment, String mode) {
		boolean flag = false;
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			if (click(driver, getUserMenuTab(20), "user menu tab", action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on user menu tab");
				log(LogStatus.INFO, "user menu tab", YesNo.No);

			} else {
				log(LogStatus.ERROR, "user menu tab", YesNo.Yes);
				return flag;
			}
		} else {
			if (click(driver, getSettingLink_Lighting(20), "setting icon", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "setting icon", YesNo.No);

			} else {
				log(LogStatus.ERROR, "setting icon", YesNo.Yes);
				return flag;
			}
		}
		if (click(driver, getUserMenuSetupLink(mode, 20), "setup link", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "setup link", YesNo.No);
			flag = true;
		} else {
			log(LogStatus.ERROR, "user setup link", YesNo.Yes);
		}
		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @return true if able to click on Edit Page Link
	 */
	public boolean clickOnEditPageLinkOnSetUpLink() {
		boolean flag = false;
		if (click(driver, getSettingLink_Lighting(20), "setting icon", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "setting icon", YesNo.No);

		} else {
			log(LogStatus.ERROR, "setting icon", YesNo.Yes);
			return flag;
		}
		if (click(driver, getEditPageOnSetUp(20), "Edit Page", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Edit Page", YesNo.No);
			flag = true;
		} else {
			log(LogStatus.ERROR, "Edit Page", YesNo.Yes);
		}
		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @param environment
	 * @param mode
	 * @param navatarQuickLink
	 * @return true if successfully click on Navatar Quick Link
	 */
	public boolean clickOnLinkFromNavatarQuickLink(String environment, String mode, NavatarQuickLink navatarQuickLink) {
		boolean flag = false;
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {

			if (clickUsingJavaScript(driver, getNavatarQuickLink_Lighting(environment, 20), "Navatar Quik Link")) {
				ThreadSleep(1000);
				switchToFrame(driver, 10, getNavatarQuickLinkFrame_Lighting(environment, 10));
			}
		} else {
			if (getCloseSideBar(5) == null) {
				if (click(driver, getOpenSideBar(30), "Open sied bar", action.BOOLEAN)) {
					log(LogStatus.INFO, "Opened the side bar.", YesNo.No);
				} else {
//					BaseLib.sa.assertTrue(false, "cannot open the side bar, So cannot check the navatar quick link.");
//					log(LogStatus.ERROR, "cannot open the side bar, So cannot check the navatar quick link.", YesNo.Yes);
				}
			}
			ThreadSleep(1000);
			appLog.info("Inside Classic Frame");
			switchToFrame(driver, 10, getNavatarQuickLinkFrame_Classic(environment, 10));
		}

		WebElement quickLink = FindElement(driver, "//a[contains(text(),'" + navatarQuickLink + "')]",
				"Navatar Quick Link : " + navatarQuickLink, action.SCROLLANDBOOLEAN, 20);
		if (click(driver, quickLink, "Navatar Quick Link : " + navatarQuickLink, action.SCROLLANDBOOLEAN)) {
			flag = true;
		}

		switchToDefaultContent(driver);
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			if (click(driver, getNavatarQuickLinkMinimize_Lighting(environment, 20), "Navatar Quik Link Minimize Icon",
					action.SCROLLANDBOOLEAN)) {
				ThreadSleep(1000);
			}
		}

		return flag;

	}

	public String taskCreatesMsg(String projectName, String taskName) {
		return "Task \"" + taskName + "\" was created" + "Task \"" + taskName + "\" was created." + "Task " + taskName
				+ " was created" + "Task " + taskName + " was created.";

	}

	/**
	 * @param projectName
	 * @param globalActionItem
	 * @param labelsWithValue
	 * @return true if able to click on Global action and enter value
	 */
	public boolean clickOnNvaigationMenuAndEnterValueOnTask(String projectName, NavigationMenuItems navigationMenuName,
			NewInteractions_DefaultValues navigationMenuItems, String subject, String dueDate, String contactNAme,
			String[][] dropDownLabelWithValues) {
		boolean flag = false;
		if (ClickOnItemOnNavatarEdge(navigationMenuItems.toString(), navigationMenuName.toString(), action.BOOLEAN,
				20)) {
			log(LogStatus.INFO, "clicked on " + navigationMenuName.toString() + " link", YesNo.No);
			WebElement ele = getNavigationLabel(navigationMenuItems.toString(), action.BOOLEAN, 10);
			if (ele != null && click(driver, ele, navigationMenuItems.toString(), action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on " + navigationMenuItems + " so going for creation", YesNo.No);

				if (enteringSubjectAndSelectDropDownValuesonTaskPopUp(projectName, PageName.TaskPage, subject,
						dropDownLabelWithValues, action.SCROLLANDBOOLEAN, 10)) {
					log(LogStatus.INFO, "Entered value to Subject Text Box ", YesNo.No);
					ThreadSleep(1000);

					if (navigationMenuName.toString().equalsIgnoreCase("Task")
							|| navigationMenuName.toString().equalsIgnoreCase("Call")) {
						if (sendKeys(driver, getdueDateTextBoxInNewTask(projectName, 20), dueDate,
								PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Entered value to Due Date Text Box", YesNo.No);
							ThreadSleep(1000);
						} else {
							log(LogStatus.ERROR, "Not able to enter value on duedate textbox " + dueDate, YesNo.Yes);
							sa.assertTrue(false, "Not able to enter value on duedate textbox " + dueDate);
						}
					} else {
						if (sendKeys(driver, getdueDateTextBoxInNewTask(projectName, 20), dueDate,
								PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Entered value to Due Date Text Box", YesNo.No);
							ThreadSleep(1000);
						} else {
							log(LogStatus.ERROR, "Not able to enter value on duedate textbox " + dueDate, YesNo.Yes);
							sa.assertTrue(false, "Not able to enter value on duedate textbox " + dueDate);
						}
					}

					flag = selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName,
							PageName.TaskPage, PageLabel.Name.toString(), TabName.TaskTab, contactNAme,
							action.SCROLLANDBOOLEAN, 10);
					if (flag) {
						ele = getCrossButtonForAlreadySelectedItem(projectName, PageName.TaskPage,
								PageLabel.Name.toString(), true, contactNAme, action.SCROLLANDBOOLEAN, 5);
						if (ele != null) {
							log(LogStatus.INFO, contactNAme + " Found For Label " + PageLabel.Name.toString() + " at "
									+ navigationMenuItems, YesNo.No);
						} else {
							sa.assertTrue(false, contactNAme + " not Found For Label " + PageLabel.Name.toString()
									+ " at " + navigationMenuItems);
							log(LogStatus.ERROR, contactNAme + " not Found For Label " + PageLabel.Name.toString()
									+ " at " + navigationMenuItems, YesNo.Yes);
						}
					} else {
						sa.assertTrue(false, "Not Able to Select " + contactNAme + " For Label " + PageLabel.Name);
						log(LogStatus.SKIP, "Not Able to Select " + contactNAme + " For Label " + PageLabel.Name,
								YesNo.Yes);

					}
					if (clickUsingJavaScript(driver, getCustomTabSaveBtn(projectName, 20), "save",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "successfully created : " + subject + " for " + navigationMenuItems,
								YesNo.No);
						// ExcelUtils.writeData(phase1DataSheetFilePath,dueDate, "Task1",
						// excelLabel.Variable_Name, "M3CALL1", excelLabel.Due_Date);
						ele = getCreatedConfirmationMsg(projectName, 15);
						if (ele != null) {
							String actualValue = ele.getText().trim();
							String expectedValue = taskCreatesMsg(projectName, subject);
							if (expectedValue.contains(actualValue)) {
								log(LogStatus.INFO, expectedValue + " matched FOR Confirmation Msg", YesNo.No);
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
						log(LogStatus.ERROR, "Save Button is not visible so could not be create " + navigationMenuItems,
								YesNo.Yes);
						sa.assertTrue(false,
								"Save Button is not visible so could not be create " + navigationMenuItems);
					}

				} else {
					log(LogStatus.ERROR, "Subject textbox is not visible so could not be create " + navigationMenuItems,
							YesNo.Yes);
					sa.assertTrue(false,
							"Subject textbox is not visible so could not be create " + navigationMenuItems);
				}

			} else {
				log(LogStatus.ERROR,
						"Not Able to Click on " + navigationMenuItems + " so cannot create data related to this ",
						YesNo.Yes);
				sa.assertTrue(false,
						"Not Able to Click on " + navigationMenuItems + " so cannot create data related to this ");

			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Global Action Related item");
			log(LogStatus.SKIP, "Not Able to Click on Global Action Related item", YesNo.Yes);
		}

		return true;
	}

	/**
	 * @author Azhar Alam
	 * @param environment
	 * @param mode
	 * @param navatarQuickLink
	 * @return true if successfully verify landing page after click on Navatar Setup
	 *         Page
	 */
	public boolean verifyLandingPageAfterClickingOnNavatarSetUpPage(String environment, String mode,
			NavatarQuickLink navatarQuickLink) {

		String landingPage = null;
		WebElement ele;
		switch (navatarQuickLink) {
		case CreateDeal:
			landingPage = "Deal Creation";
			break;
		case BulkEmail:
			landingPage = "Bulk E-mail";
			break;
		default:
			return false;
		}
		ThreadSleep(2000);
		System.err.println("Passed switch statement");

		ele = isDisplayed(driver,
				FindElement(driver, "//p[text()='" + landingPage + "']", landingPage, action.SCROLLANDBOOLEAN, 10),
				"visibility", 10, landingPage);
		if (ele != null) {
			return true;
		}
		return false;
	}

	public boolean clickOnTemplateForReportOnBulkEmail(String environment, String mode, String reportName,
			String templateName) {
		WebElement ele;
		String xpath = "//span[text()='" + templateName + "']/ancestor::ul//span[contains(@id,'extd')][text()='"
				+ reportName + "']";
		ele = FindElement(driver, xpath, reportName + " : " + templateName, action.SCROLLANDBOOLEAN, 10);
		ThreadSleep(2000);
		if (clickUsingJavaScript(driver, ele, reportName + " : " + templateName, action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on >>>   " + reportName + " : " + templateName, YesNo.No);
			return true;
		} else {
			log(LogStatus.ERROR, "Not Able to Click on >>>   " + reportName + " : " + templateName, YesNo.Yes);
		}
		return false;
	}

	public List<String> selectContactAndVerifyInBulkEmail(String environment, String mode, String fname, String lname,
			String contactSearchValue, searchContactInEmailProspectGrid searchContactInEmailProspectGrid, int timeOut) {
		List<String> result = new ArrayList<String>();
		WebElement ele = null;

		if (searchContactInEmailProspectGrid.toString()
				.equalsIgnoreCase(searchContactInEmailProspectGrid.Yes.toString())) {
			if (sendKeys(driver, getSearchForAContactTextBox(environment, mode, timeOut), contactSearchValue,
					"search text box", action.SCROLLANDBOOLEAN)) {
				appLog.info("Passed Value in Search Text box: " + contactSearchValue);
				ThreadSleep(2000);
				if (click(driver, getSearchIconForAContactTextBox(environment, mode, 10), "Search Icon",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked On Search Icon", YesNo.No);
					ThreadSleep(2000);
					ele = getRecordsOnBulkEmail(environment, mode, timeOut);
					if (ele != null) {
						int RecordCount = Integer.parseInt(ele.getText().trim().split(":")[1]);
						if (RecordCount == 1) {
							appLog.info("Bulk Email Record Count is matched Successfully.");
						} else {
							appLog.error("Bulk Email Record Count is not Matched. Expected: 1" + "\t Actual :"
									+ RecordCount);
							result.add("Bulk Email Record Count is not Matched. Expected: 1" + "\t Actual :"
									+ RecordCount);
						}
					} else {
						appLog.error("Email Prospect Record Count is not visible so cannot verify record Count");
						result.add("Email Prospect Record Count is not visible so cannot verify record Count");
					}
				} else {
					log(LogStatus.SKIP, "Not Able to Click On Search Icon", YesNo.Yes);
				}

			} else {
				appLog.error("Not able to pass value " + contactSearchValue
						+ " in search text box so cannot search contact: " + contactSearchValue);
				result.add("Not able to pass value " + contactSearchValue
						+ " in search text box so cannot search contact: " + contactSearchValue);
			}

			if (ScrollAndClickOnContactNameCheckBoxInBulkEmail(fname, lname, 10)) {
				appLog.info("clicked on Contact Name Check Box: " + fname + " " + lname);
			} else {
				appLog.error("Not able to click on Contact Name :" + fname + " " + lname
						+ " check box so cannot add contact in review prospect list");
				result.add("Not able to click on Contact Name :" + fname + " " + lname
						+ " check box so cannot add contact in review prospect list");
			}
		}
		return result;
	}

	public boolean ScrollAndClickOnContactNameCheckBoxInBulkEmail(String fname, String lname, int timeout) {
		int j = 0;
		WebElement ele = null;
		String XpathelementTOSearch = "";
		XpathelementTOSearch = "//span[contains(@class,'aw-hpanel-middle')]//span[contains(@class,'aw-grid-row')]//a[text()='"
				+ lname + "']/../preceding-sibling::span//a[text()='" + fname
				+ "']/../preceding-sibling::span/span/span[1]";
		By byelementToSearch = By.xpath(XpathelementTOSearch);
		int widgetTotalScrollingHeight = Integer.parseInt(String.valueOf(((JavascriptExecutor) driver)
				.executeScript("return arguments[0].scrollHeight", getEmailProspectSelectProspectsGridScrollBox(10))));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(0,0)",
				getEmailProspectSelectProspectsGridScrollBox(10));
		for (int i = 0; i <= widgetTotalScrollingHeight / 25; i++) {
			if (!driver.findElements(byelementToSearch).isEmpty()
					&& driver.findElement(byelementToSearch).isDisplayed()) {
				appLog.info("Element Successfully Found and displayed");
				ThreadSleep(500);
				ele = FindElement(driver, XpathelementTOSearch, "", action.BOOLEAN, timeout);
				if (ele != null) {
					if (click(driver, ele, "", action.BOOLEAN)) {
						appLog.info("clicked on Contact Name : " + fname + " " + lname);
					} else {
						appLog.error("Not able to clicke on Contact Name: " + fname + " " + lname);
						return false;
					}
				}
				break;
			} else {
				System.out.println("Not FOund: " + byelementToSearch.toString());
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(" + j + "," + (j = j + 45) + ")",
						getEmailProspectSelectProspectsGridScrollBox(10));
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

	public boolean selectEmailTemplateFromEmailProspect(String folderName, String emailTemplateName) {
		int j = 0;
		WebElement ele = null;
		String XpathelementTOSearch = "//span[text()='" + emailTemplateName + "']/preceding-sibling::span/input";
		if (folderName != null) {
			if (selectVisibleTextFromDropDown(driver, getEmailProspectFolderDropDownList(20), "folder drop downlist",
					folderName)) {
				appLog.info("Folder Name is selected from folder drop down list : " + folderName);
			} else {
				appLog.error("Not able to select email prospects email template folder: " + folderName);
				return false;
			}
		}
		By byelementToSearch = By.xpath(XpathelementTOSearch);
		int widgetTotalScrollingHeight = Integer
				.parseInt(String.valueOf(((JavascriptExecutor) driver).executeScript("return arguments[0].scrollHeight",
						getEmailProspectStep2CustomEmailtemplateScrollBox(10))));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(0,0)",
				getEmailProspectStep2CustomEmailtemplateScrollBox(10));
		for (int i = 0; i <= widgetTotalScrollingHeight / 25; i++) {
			if (!driver.findElements(byelementToSearch).isEmpty()
					&& driver.findElement(byelementToSearch).isDisplayed()) {
				appLog.info("Element Successfully Found and displayed");
				ThreadSleep(500);
				ele = FindElement(driver, XpathelementTOSearch, "", action.BOOLEAN, 10);
				if (ele != null) {
					if (click(driver, ele, "", action.BOOLEAN)) {
						appLog.info("clicked on Custom email template radio button : " + emailTemplateName);

					} else {
						appLog.error("Not able to clicked on email template radio button: " + emailTemplateName);
						return false;
					}
				}
				break;
			} else {
				System.out.println("Not FOund: " + byelementToSearch.toString());
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(" + j + "," + (j = j + 45) + ")",
						getEmailProspectStep2CustomEmailtemplateScrollBox(10));
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

	public boolean VerifyBulkEmailFunctionality(String environment, String mode, String reportName, String templateName,
			String fname, String lname, String contactSearchValue,
			searchContactInEmailProspectGrid searchContactInEmailProspectGrid, String folderName,
			String emailTemplateName) {
		boolean flag = false;
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		switchToFrame(driver, 30, getCreateFundraisingsFrame_Lighting(20));
		String SmokeReportFolderName = reportName;
		String SmokeReportName = templateName;
		if (hp.clickOnTemplateForReportOnBulkEmail(environment, mode, SmokeReportFolderName, SmokeReportName)) {
			log(LogStatus.INFO, "Clicked On " + SmokeReportFolderName + " : " + SmokeReportName, YesNo.No);
			List<String> result = hp.selectContactAndVerifyInBulkEmail(environment, mode, fname, lname,
					contactSearchValue, searchContactInEmailProspectGrid, 10);
			if (result.isEmpty()) {
				log(LogStatus.PASS, "Able to Search/Check Contact : " + fname + " " + lname, YesNo.No);
				if (click(driver, bp.getEmailProspectStep1NextBtn(20), "step 1 next button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on Steps 1 Next button", YesNo.No);
					String EmailTemplate1_FolderName = folderName;
					String EmailTemplate1_TemplateName = emailTemplateName;
					if (hp.selectEmailTemplateFromEmailProspect(EmailTemplate1_FolderName,
							EmailTemplate1_TemplateName)) {
						log(LogStatus.INFO, "PE Test Custom Email Template is selected successfully", YesNo.No);
						if (click(driver, bp.getEmailProspectStep2NextBtn(30), "step 2 next button",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on step 2 next button", YesNo.No);
							if (click(driver, bp.getEmailProspectSendBtn(TopOrBottom.TOP, 30), "send button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on send button", YesNo.No);
								WebElement ele = hp.step4_BulkEmailPage(environment, mode, 10);

								if (ele != null) {
									String msg = ele.getText().trim();
									if (HomePageErrorMessage.step4_YourEmail.equals(msg)) {
										log(LogStatus.PASS, "Step4 Page Verified : " + msg, YesNo.No);
									} else {
										sa.assertTrue(false, "Step4 Page Verified Not Verified Actual : " + msg
												+ " \t Expected :" + HomePageErrorMessage.step4_YourEmail);
										log(LogStatus.FAIL, "Step4 Page Verified Not Verified Actual : " + msg
												+ " \t Expected :" + HomePageErrorMessage.step4_YourEmail, YesNo.Yes);
									}
								} else {
									sa.assertTrue(false, "Step4 Page Element is null");
									log(LogStatus.FAIL, "Step4 Page Element is null", YesNo.Yes);

								}

								if (click(driver, bp.getEmailProspectFinishBtn(30), "finish button", action.BOOLEAN)) {
									log(LogStatus.INFO, "Clicked on finish button", YesNo.No);
									flag = true;
								} else {
									sa.assertTrue(false, "Not able to click on finish button");
									log(LogStatus.ERROR, "Not able to click on finish button", YesNo.Yes);
								}
							} else {
								sa.assertTrue(false,
										"Not able to click on send button so cannot send email to contact: " + fname
												+ " " + lname);
								log(LogStatus.ERROR,
										"Not able to click on send button so cannot send email to contact: " + fname
												+ " " + lname,
										YesNo.Yes);
							}

						} else {
							sa.assertTrue(false,
									"Not able to click on steps 2 nect button so cannot send email to contact : "
											+ fname + " " + lname);
							log(LogStatus.ERROR,
									"Not able to click on steps 2 nect button so cannot send email to contact : "
											+ fname + " " + lname,
									YesNo.Yes);
						}

					} else {
						sa.assertTrue(false,
								"Not able to select Email Template from Bulk Email so cannot send email to contact "
										+ fname + " " + lname);
						log(LogStatus.ERROR,
								"Not able to select Email Template from Bulk Email so cannot send email to contact "
										+ fname + " " + lname,
								YesNo.Yes);
					}

				} else {
					sa.assertTrue(false,
							"Not able to click on Steps 1 Next button so cannot select email template and send email to contact "
									+ fname + " " + lname);
					log(LogStatus.ERROR,
							"Not able to click on Steps 1 Next button so cannot select email template and send email to contact "
									+ fname + " " + lname,
							YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Search/Check Contact : " + fname + " " + lname);
				log(LogStatus.FAIL, "Not Able to Search/Check Contact : " + fname + " " + lname, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click On " + SmokeReportFolderName + " : " + SmokeReportName);
			log(LogStatus.SKIP, "Not Able to Click On " + SmokeReportFolderName + " : " + SmokeReportName, YesNo.Yes);
		}
		return flag;
	}

	public boolean selectFundNameOrCompanyNameOnCreateFundraisings(String environment, String mode,
			PopUpName selectFundOrCompanyNamePopName, String fundName, String companyName) {
		boolean flag = false;
		if (selectFundOrCompanyNamePopName.toString().equalsIgnoreCase(PopUpName.selectFundPopUp.toString())) {
			if (click(driver, getSelectFundNameFromSelectFundPopUpLookUpIcon(60), "fund name look up icon",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "clicked on fund name look up icon", YesNo.No);
				if (selectValueFromLookUpWindow(fundName)) {
					log(LogStatus.INFO, fundName + " fund Name is select successfully", YesNo.No);
					if (companyName != null) {
						if (fundName != null) {
							if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
								switchToFrame(driver, 30, getCreateFundraisingsFrame_Lighting(20));
							}
						}
						if (click(driver, getSelectCompanyNameFromSelectFundPopUpLookUpIcon(30),
								"company name look up icon", action.BOOLEAN)) {
							if (selectValueFromLookUpWindow(companyName)) {
								log(LogStatus.INFO, companyName + " company Name is select successfully", YesNo.No);
								flag = true;
							} else {
								log(LogStatus.ERROR,
										"Not able to select company Name " + companyName + " from look up pop up",
										YesNo.Yes);
							}
						} else {
							log(LogStatus.ERROR,
									"Not able to click on company name look up icon so cannot select compnay name : "
											+ companyName,
									YesNo.Yes);
						}
					} else {
						flag = true;
					}
				} else {
					log(LogStatus.ERROR, "Not able to select fund Name " + fundName + " from look up pop up",
							YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR,
						"Not able to click on fund name look up icon so cannot select fund name: " + fundName,
						YesNo.Yes);
			}
		} else if (selectFundOrCompanyNamePopName.toString()
				.equalsIgnoreCase(PopUpName.SelectFundPopUpFromCompmayPage.toString())) {
			if (selectVisibleTextFromDropDown(driver, getSelectFundNameDropDownListInSelectFundPopUp(30),
					"select fund name drop down list in select fund pop up", fundName)) {
				log(LogStatus.INFO, "fund Name " + fundName + " is selected from fund name drop down list", YesNo.No);
				flag = true;
			} else {
				log(LogStatus.ERROR, "Not able to select fund name " + fundName + " from select fund pop up",
						YesNo.Yes);
			}
		} else {
			if (click(driver, getSelectCompanyNameWarningPopUpLookUpIcon(30), "company name look up icon",
					action.BOOLEAN)) {
				if (selectValueFromLookUpWindow(companyName)) {
					log(LogStatus.INFO, companyName + " company Name is select successfully", YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Not able to select company Name " + companyName + " from look up pop up",
							YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR,
						"Not able to click on company name look up icon so cannot select compnay name : " + companyName,
						YesNo.Yes);
			}
		}
		return flag;
	}

	/**
	 * @author Ankit Jaiswal
	 * @param tabName
	 * @param environment    TODO
	 * @param mode           TODO
	 * @param fundName
	 * @param fieldName
	 * @param operator
	 * @param Value
	 * @param addFilterLogic TODO
	 * @return
	 */
	public boolean applyFilterOnSearchBasedOnAccountsandContacts(FundraisingContactPageTab tabName,
			SearchBasedOnExistingFundsOptions searchBasedOnExistingFundsOptions, String environment, String mode,
			String fundName, String fieldName, String operator, String Value, String addFilterLogic) {
		List<String> splitedValue = new ArrayList<String>();
		if (searchBasedOnExistingFundsOptions.toString()
				.equalsIgnoreCase(SearchBasedOnExistingFundsOptions.OnlyFundraisingContacts.toString())) {
			ThreadSleep(2000);
			if (mouseOverOperation(driver, getSearchBasedOnExistingFundsDownArrow(20))) {
				ThreadSleep(2000);
				if (clickUsingJavaScript(driver, getOnlyFundraisingContactOptionOnSearchBasedOnExistingFunds(20),
						"Only Fundraising Contact text")) {
					log(LogStatus.INFO, "clicked on Only Fundraising Contact text", YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Not able to click on Only Fundraising Contact option so cannot applied filter logic",
							YesNo.Yes);
					return false;
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Search Based On Existing Funds Down Arrow", YesNo.Yes);
				return false;
			}
		}
		if (tabName.toString().equalsIgnoreCase(FundraisingContactPageTab.SearchBasedOnExistingFunds.toString())) {
			if (fundName != null) {
				if (click(driver, getSelectFundNameFromSearchBasedOnExistingFundLookUpIcon(20),
						"Select FundName From Search Based On Existing Fund LookUp Icon", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on fund name look up icon", YesNo.No);
					if (selectValueFromLookUpWindow(fundName)) {
						log(LogStatus.INFO, fundName + " fund Name is select successfully", YesNo.No);
						if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
							switchToFrame(driver, 30, getCreateFundraisingsFrame_Lighting(20));
						}
					} else {
						log(LogStatus.ERROR, "Not able to select fund Name " + fundName + " from look up pop up",
								YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on fund Name look up icon so cannot select fund Name",
							YesNo.Yes);
					return false;
				}
			}
		}
		if (fieldName != null && operator != null && !fieldName.isEmpty() && !operator.isEmpty()) {
			String[] splitedFieldName = fieldName.split("<break>");
			String[] splitedOperator = operator.split("<break>");
			if (Value != null) {
				for (int i = 0; i < Value.split("<break>").length; i++) {
					splitedValue.add(Value.split("<break>")[i]);
				}
			}
			WebElement ele = null;
			for (int i = 0; i < splitedFieldName.length; i++) {
				if (i < splitedFieldName.length - 1) {
					if (click(driver, getAddRowsLink(30), "add rows link", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on add rows link", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to click on add rows link so cannot add filter logic",
								YesNo.Yes);
						return false;
					}
				}
				ele = isDisplayed(driver, FindElement(driver, "//input[@id='a" + (i + 1) + "aa']", "field text box",
						action.SCROLLANDBOOLEAN, 10), "Visibility", 10, "field Text Box");
				if (ele != null) {
					if (sendKeys(driver, ele, splitedFieldName[i], "field text box", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Passed Value in field Name Text box " + splitedFieldName[i], YesNo.No);
						ThreadSleep(2000);
						ele = isDisplayed(driver,
								FindElement(driver,
										"//ul[contains(@style,'block;')]//li//a[text()='" + splitedFieldName[i] + "']",
										"field text box", action.SCROLLANDBOOLEAN, 10),
								"Visibility", 10, "field Text Box");
						ThreadSleep(500);
						if (click(driver, ele, splitedFieldName[i] + " autocomplete text box", action.BOOLEAN)) {
							log(LogStatus.INFO,
									"Clicked on field Name " + splitedFieldName[i] + " from autocomplete text box",
									YesNo.No);
							ele = isDisplayed(
									driver, FindElement(driver, "//select[@id='opt" + (i + 1) + "']",
											"operator drop down list", action.SCROLLANDBOOLEAN, 30),
									"Visibility", 10, "operator drop down list");
							if (selectVisibleTextFromDropDown(driver, ele, "operator drop down list",
									splitedOperator[i])) {
								log(LogStatus.INFO, "select Operator : " + splitedOperator[i], YesNo.No);
								if (Value != null) {
									ele = isDisplayed(driver,
											FindElement(driver, "//input[@id='criteriatextbox" + (i + 1) + "']",
													"Value text box", action.SCROLLANDBOOLEAN, 10),
											"Visibility", 10, "Value Text Box");
									if (ele != null) {
										if (sendKeys(driver, ele, splitedValue.get(i), "Value Text Box",
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO,
													"Passed Value " + splitedValue.get(i) + " in value text box",
													YesNo.No);

										} else {
											log(LogStatus.ERROR, "Not able to pass Value " + splitedValue.get(i)
													+ " in Text box so cannot apply filter logic", YesNo.Yes);
											return false;
										}
									} else {
										log(LogStatus.ERROR, "Value Text is not visible so cannot apply filter logic",
												YesNo.Yes);
										return false;
									}
								}
							} else {
								log(LogStatus.ERROR, "Not able to select Operator " + splitedOperator[i]
										+ " from Drop Down List so cannot apply filter logic", YesNo.Yes);
								return false;
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on field Name " + splitedFieldName[i]
									+ " form autocomplete text box so cannot apply filter", YesNo.Yes);
							return false;
						}
					} else {
						log(LogStatus.ERROR, "Not able to pass value in Field Name text Box : " + splitedFieldName[i]
								+ " so cannot apply filter logic", YesNo.Yes);
						return false;
					}
				} else {
					log(LogStatus.ERROR, "Field Text Box is not visible so cannot apply filter logic", YesNo.Yes);
					return false;
				}
			}
		}
		if (addFilterLogic != null) {
			WebElement ele = getAddFilterLogicLinkOnCreateFundraising(10);
			if (ele != null) {
				if (click(driver, ele, "add row button", action.BOOLEAN)) {
					appLog.info("clicked on add row link");
					ThreadSleep(2000);
					ele = isDisplayed(driver,
							FindElement(driver, "//input[@id='j_id0:CreateFundraisingFormId:textfilt']",
									"Add filter logic text box", action.BOOLEAN, 10),
							"Visibility", 10, "Add filter logic text box");
					if (ele != null) {
						if (sendKeys(driver, ele, addFilterLogic, "add filter logic text box",
								action.SCROLLANDBOOLEAN)) {
							appLog.info("pass value in filter logic text box : " + addFilterLogic);

						} else {
							appLog.error(
									"Not able to pass value on add filter logic text box so cannot add filter logic "
											+ addFilterLogic);
							return false;
						}
					} else {
						appLog.error(
								"Not able find add filter logic text box so cannot add filter logic " + addFilterLogic);
						return false;
					}

				} else {
					appLog.error("Not able to click on add filter logic so cannot add filter logic " + addFilterLogic);
					return false;
				}
			} else {
				appLog.error("Not able find add filter logic link so cannot add filter logic " + addFilterLogic);
				return false;
			}
		}
		if (click(driver, getSearchBasedOnAccountsAndContactsSearchBtn(30), "search button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "clicked on Search Button", YesNo.No);
			return true;
		} else {
			log(LogStatus.ERROR, "Not able to click on Search Button so cannot apply filter", YesNo.Yes);
		}
		return false;
	}

	public List<String> selectInvestorsContactFromCreateFundRaising(List<String> contactName,
			List<String> accountName) {
		List<String> result = new ArrayList<String>();
		if (!contactName.isEmpty() && !accountName.isEmpty()) {
			for (int i = 0; i < contactName.size(); i++) {
				if (ScrollAndClickOnContactNameCheckBoxAddInvestor(contactName.get(i), accountName.get(i))) {
					log(LogStatus.INFO, "clicked on Contact Name Check Box: " + contactName.get(i), YesNo.No);
				} else {
					log(LogStatus.ERROR, "Not able to click on Contact Name :" + contactName.get(i)
							+ " check box so cannot add contact in review investor list", YesNo.Yes);
					result.add("Not able to click on Contact Name :" + contactName.get(i)
							+ " check box so cannot add contact in review investor list");
				}
			}
		} else {
			log(LogStatus.ERROR,
					"Contact Name and Account Name list is empty so cannot select contact Name in select investor grid",
					YesNo.Yes);
			result.add(
					"Contact Name and Account Name list is empty so cannot select contact Name in select investor grid");

		}
		return result;
	}

	/**
	 * @author Ankit Jaiswal
	 * @param contactName
	 * @param accountName
	 * @param timeout
	 * @return
	 */
	public boolean ScrollAndClickOnContactNameCheckBoxAddInvestor(String contactName, String accountName) {
		int j = 0;
		WebElement ele = null;
		String XpathelementTOSearch = "";
		int widgetTotalScrollingHeight = 0;
		XpathelementTOSearch = xpathOfSelectInvestorsCheckBox(contactName, accountName);
//	System.err.println("XpathelementTOSearch "+XpathelementTOSearch);
		By byelementToSearch = By.xpath(XpathelementTOSearch);
//	System.err.println("byelementToSearch "+byelementToSearch);
		widgetTotalScrollingHeight = Integer.parseInt(String.valueOf(((JavascriptExecutor) driver)
				.executeScript("return arguments[0].scrollHeight", getSelectInvestorGridScrollBox(30))));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(0,0)", getSelectInvestorGridScrollBox(30));
//	System.err.println("Height :"+widgetTotalScrollingHeight);
		for (int i = 0; i <= widgetTotalScrollingHeight / 25; i++) {
			try {
				if (!driver.findElements(byelementToSearch).isEmpty()
						&& driver.findElement(byelementToSearch).isDisplayed()) {
					appLog.info("Element Successfully Found and displayed");
					ThreadSleep(500);
					ele = FindElement(driver, XpathelementTOSearch, "", action.BOOLEAN, 30);
					if (ele != null) {
						if (click(driver, ele, "", action.BOOLEAN)) {
							appLog.info("clicked on Contact Name : " + contactName);
						} else {
							appLog.error("Not able to clicke on Contact Name: " + contactName);
							return false;
						}
					}
					break;
				} else {
					System.out.println("Not FOund: " + byelementToSearch.toString());
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(" + j + "," + (j = j + 45) + ")",
							getSelectInvestorGridScrollBox(20));
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
			} catch (Exception e) {
				// TODO Auto-generated catch block
//			e.printStackTrace();
				System.err.println("Inside : " + i);
			}
		}
		return true;
	}

	/**
	 * @author Ankit Jaiswal
	 * @param ContactName
	 * @param AccountName
	 * @return String xpath
	 */
	public static String xpathOfSelectInvestorsCheckBox(String ContactName, String AccountName) {
		String xpath = "//span[@id='Select_from_Search_Results-view-box-middle']//span/div/a";
		String xpath1 = "/../../following-sibling::span/div/a";
		for (int i = 0; i < ContactName.split(" ").length; i++) {
			xpath = xpath + "[contains(text(),'" + ContactName.split(" ")[i] + "')]";
		}
		for (int i = 0; i < AccountName.split(" ").length; i++) {
			xpath1 = xpath1 + "[contains(text(),'" + AccountName.split(" ")[i] + "')]";
		}
		return xpath + xpath1 + "/../../preceding-sibling::span/span/span[1]";
	}

	/**
	 * @author Ankit Jaiswal
	 * @param environment
	 * @param mode
	 * @param fundraisingName
	 * @param fundName
	 * @param companyName
	 * @param legalName
	 * @param commitmentType
	 * @return true/false
	 */
	public boolean selectFundraisingNameOrCommitmentType(String environment, String mode, String fundraisingName,
			String fundName, String companyName, String legalName, CommitmentType commitmentType) {
		WebElement ele = null;
		boolean flag = false;
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			switchToFrame(driver, 30, getCreateCommitmentFrame_Lightning(60));

		}
		ThreadSleep(5000);
		if (fundraisingName != null) {
			if (click(driver, getFundRaisingNameLookUpIcon(120), "fundraising name look up icon",
					action.SCROLLANDBOOLEAN)) {
				if (selectValueFromLookUpWindow(fundraisingName)) {
					log(LogStatus.INFO, fundraisingName + " fundraising name is selected", YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR,
							fundraisingName + " fundraising name is not selected from fundraising name look up",
							YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR,
						"Not able to click on fundraising name look up icon so cannot select fundraising name "
								+ fundraisingName,
						YesNo.Yes);
			}
		} else {
			if (commitmentType.toString().equalsIgnoreCase(CommitmentType.fund.toString())) {
				if (click(driver, getFundTypeCommitment(120), "fund type text", action.SCROLLANDBOOLEAN)) {
					if (click(driver, getFundNameLookUpIconOnCreateCommitmentPopUp(20), "fund name look up icon",
							action.SCROLLANDBOOLEAN)) {
						if (selectValueFromLookUpWindow(fundName)) {
							log(LogStatus.INFO, "fund name is selected " + fundName, YesNo.No);
							if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
								switchToFrame(driver, 30, getCreateCommitmentFrame_Lightning(20));

							}
							if (click(driver, getLegalNameLookUpIcon(PageName.CreateCommitmentFundType, 20),
									"legal name look up icon", action.SCROLLANDBOOLEAN)) {
								if (selectValueFromLookUpWindow(legalName)) {
									log(LogStatus.INFO, "legal name is selected " + legalName, YesNo.No);
									flag = true;
								} else {
									log(LogStatus.ERROR, "Not able to select institutionName " + legalName, YesNo.Yes);

								}
							} else {
								log(LogStatus.ERROR, "Not able to select institutionName " + legalName, YesNo.Yes);
							}
						} else {
							log(LogStatus.ERROR, "Not able to select Fund Name " + fundName
									+ " so cannot select institutionName " + legalName, YesNo.Yes);
						}

					} else {
						log(LogStatus.ERROR, "Not click on fund Name look up icon so cannot select fund Name "
								+ fundName + " so cannot select institutionName " + legalName, YesNo.Yes);
					}

				} else {
					log(LogStatus.ERROR,
							"Not able to click on fund type on create commitment text so cannot select fundName "
									+ fundName + " institutionName " + legalName,
							YesNo.Yes);
				}
			} else {
				if (click(driver, getCoInvesmentTypeCommitment(120), "co investment type text",
						action.SCROLLANDBOOLEAN)) {
					if (companyName != null) {
						if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
							switchToFrame(driver, 30, getCreateCommitmentFrame_Lightning(20));

						}
						if (click(driver, getSelectCompanyNameLookUpIconOnCreateCommitmentPopUp(20),
								"company name look up icon", action.SCROLLANDBOOLEAN)) {
							if (selectValueFromLookUpWindow(companyName)) {
								log(LogStatus.INFO, "Company name is selected " + fundName, YesNo.No);
								flag = true;

							} else {
								log(LogStatus.ERROR, "Not able to select Company Name " + companyName
										+ " so cannot select institutionName " + legalName, YesNo.Yes);
							}

						} else {
							log(LogStatus.ERROR,
									"Not able to click on company Name look up icon so cannot select Company Name "
											+ companyName + " so cannot select institutionName " + legalName,
									YesNo.Yes);
						}
					} else {
						flag = true;
					}
					if (flag) {
						if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
							switchToFrame(driver, 30, getCreateCommitmentFrame_Lightning(20));

						}
						if (click(driver, getLegalNameLookUpIcon(PageName.CreateCommitmentCoInvestmentType, 20),
								"legal name look up icon", action.SCROLLANDBOOLEAN)) {
							if (selectValueFromLookUpWindow(legalName)) {
								log(LogStatus.INFO, "legal name is selected " + legalName, YesNo.No);
								flag = true;
							} else {
								log(LogStatus.ERROR, "Not able to select institutionName " + legalName, YesNo.Yes);
								flag = false;

							}
						} else {
							log(LogStatus.ERROR, "Not able to select institutionName " + legalName, YesNo.Yes);
							flag = false;
						}
					}

					log(LogStatus.ERROR,
							"Not able to click on co investment type on create commitment text so cannot select Company Name "
									+ companyName + " institutionName " + legalName,
							YesNo.Yes);
				}
			}
		}
		if (flag) {
			if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
				switchToFrame(driver, 60, getCreateCommitmentFrame_Lightning(120));

			}
			WebElement element = null;
			List<WebElement> ele1 = getCommitmentCreationContinueBtn(2);
			for (int i = 0; i < ele1.size(); i++) {
				element = isDisplayed(driver, ele1.get(i), "Visibility", 10, "");
				if (element != null) {
					if (click(driver, element, "continue button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on continue button", YesNo.No);
						break;
					} else {
						if (i == ele1.size() - 1) {
							log(LogStatus.ERROR, "Not able to click on create commitment pop up continue button",
									YesNo.Yes);
							flag = false;
						}
					}
				}
			}
		}
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			switchToDefaultContent(driver);

		}
		return flag;
	}

	/**
	 * @author Ankit Jaiswal
	 * @param environment
	 * @param mode
	 * @param commitmentInformation
	 * @param partnerType
	 * @param taxForms
	 * @param PlacementFee
	 * @return
	 */
	public boolean commitmentInfoAndAdditionalInfo(String environment, String mode, String[][] commitmentInformation,
			String partnerType, String taxForms, String PlacementFee) {
		boolean flag = true;
		List<WebElement> ele = new ArrayList<WebElement>();
		WebElement ele1 = null;
		String xpath = "", xpath1 = "";
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			switchToFrame(driver, 30, getCreateCommitmentFrame_Lightning(20));

		}
		int i = 0;
		for (String[] a : commitmentInformation) {

			if (i > 0 && i <= commitmentInformation.length - 1) {
				if (click(driver, getLogMultipleCommitmentsLink(20), "Log Multiple Commitments Link",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on Log Multiple Commitments Link", YesNo.No);
				} else {
					log(LogStatus.ERROR, "Not able to click on Log Multiple Commitments Link so cannot add more rows",
							YesNo.Yes);
					flag = false;
				}
			}

			xpath = "//input[contains(@name,'mypage:CommitmentCreationFormId') and contains(@name,':" + i + ":')]";
			ele = FindElements(driver, xpath, "LP, CommitmentAmount,partnership,FinalCommitmentDate Text Box list");
			if (!ele.isEmpty()) {
				for (int j = 0; j < a.length; j++) {
					String[] aa = a[j].split("<break>");
					String value = null;
					if (aa.length > 1) {
						if (aa[1].equalsIgnoreCase(CreatedOrNot.AlreadyCreated.toString())) {
							value = aa[0];
						} else {
							value = aa[0] + "\t";
						}
					} else {
						value = aa[0];
					}
					if (sendKeys(driver, ele.get(j), value, "commitment information text box",
							action.SCROLLANDBOOLEAN)) {

						ThreadSleep(3000);
						if (j == 0 || j == 2) {
							if (aa.length == 2) {
								if (aa[1].equalsIgnoreCase(CreatedOrNot.AlreadyCreated.toString())) {
									xpath1 = "//ul[@class='ui-menu ui-widget ui-widget-content ui-autocomplete ui-front'][contains(@style,'display: block;')]//div[text()='"
											+ aa[0] + "']";
									ele1 = FindElement(driver, xpath1, "auto complete drop downlist",
											action.SCROLLANDBOOLEAN, 5);
									if (ele1 != null) {
										if (click(driver, ele1, "auto complete drop down list",
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on " + aa[0] + "name text", YesNo.No);
										} else {
											log(LogStatus.ERROR, "Not able to click on " + aa[0] + "name text",
													YesNo.Yes);
											flag = false;
										}
									} else {
										log(LogStatus.ERROR, "auto complete text is not visible for " + aa[0] + " text",
												YesNo.Yes);
										flag = false;
									}
								}
							} else {
								if (aa.length >= 3) {
									for (int k = 2; k < aa.length; k++) {
										String[] labelsWithValue = aa[k].split(":");
										labelsWithValue[0] = labelsWithValue[0].replace("_", " ");
										xpath = "//label[text()='" + labelsWithValue[0]
												+ "']/../following-sibling::td//label/";
										String tag = getTagElementForGivenXpath(xpath + "*[2]");
										if (tag != null) {
											appLog.info("Tag Element for : " + labelsWithValue[0] + " >>>> " + tag);
											log(LogStatus.INFO,
													"Tag Element for : " + labelsWithValue[0] + " >>>> " + tag,
													YesNo.No);
											if (labelsWithValue.length != 1) {
												String fullXpath = xpath + tag;
												if (enteringValuesOnTheBasisOfTag(fullXpath, labelsWithValue[0],
														labelsWithValue[1], tag)) {
													appLog.info(" value added to " + labelsWithValue[0] + "  : "
															+ labelsWithValue[1]);

												} else {
													log(LogStatus.ERROR,
															"Not able to enter value in " + labelsWithValue[0],
															YesNo.Yes);
													flag = false;
												}
											}

										} else {
											log(LogStatus.ERROR, "Tag Element is null for : " + labelsWithValue[0],
													YesNo.Yes);
											flag = false;
										}

									}
									if (j == 0) {
										if (click(driver, getNewLimitedPartnerAddBtnInCreateCommitment(10),
												"new limited partner add button", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on add button in limited partner pop up",
													YesNo.No);
											String pencilIcon = "//div[@id='LPEdit" + i + "']";
											WebElement pencilIconElement = FindElement(driver, pencilIcon,
													"lp pencil icon", action.SCROLLANDBOOLEAN, 5);
											if (pencilIconElement != null) {
												if (click(driver, pencilIconElement, "LP pencil icon",
														action.SCROLLANDBOOLEAN)) {
													log(LogStatus.INFO, "clicked on LP pencil icon", YesNo.No);
													ThreadSleep(3000);
													for (int k = 2; k < aa.length; k++) {
														String[] labelsWithValue = aa[k].split(":");
														labelsWithValue[0] = labelsWithValue[0].replace("_", " ");
														xpath = "//label[text()='" + labelsWithValue[0]
																+ "']/../following-sibling::td//label/";
														String tag = getTagElementForGivenXpath(xpath + "*[2]");
														if (tag != null) {
															appLog.info("Tag Element for : " + labelsWithValue[0]
																	+ " >>>> " + tag);
															log(LogStatus.INFO, "Tag Element for : "
																	+ labelsWithValue[0] + " >>>> " + tag, YesNo.No);
															if (labelsWithValue.length != 1) {
																String fullXpath = xpath + tag;
																if (verifyValuesOnTheBasisOfTag(fullXpath,
																		labelsWithValue[0], labelsWithValue[1], tag)) {
																	appLog.info("value is verified for "
																			+ labelsWithValue[0] + " with value : "
																			+ labelsWithValue[1]);

																} else {
																	log(LogStatus.ERROR, "value is not verified for "
																			+ labelsWithValue[0] + " with value : "
																			+ labelsWithValue[1], YesNo.Yes);
																	flag = false;
																}
															}
														} else {
															log(LogStatus.ERROR, "Tag Element is null for label : "
																	+ labelsWithValue[0] + " so cannot verify value",
																	YesNo.Yes);
															flag = false;
														}

													}
													if (click(driver,
															getNewLimitedPartnerCancelBtnInCreateCommitment(10),
															"cancel button", action.SCROLLANDBOOLEAN)) {
														log(LogStatus.INFO,
																"clicked on new limited partner pop up cancel button",
																YesNo.No);
													} else {
														log(LogStatus.INFO,
																"Not able to click on new limited partner pop up cancel button",
																YesNo.Yes);
														flag = false;
													}
												} else {
													log(LogStatus.ERROR,
															"Not able to click on Lp pencil icon so cannot click on it and verify filled data",
															YesNo.Yes);
													flag = false;
												}
											} else {
												log(LogStatus.ERROR,
														"Lp pencil icon is not visible so cannot click on it and verify filled data",
														YesNo.Yes);
												flag = false;
											}
										} else {
											log(LogStatus.INFO,
													"Not able to click on add button in limited partner pop up",
													YesNo.Yes);
											flag = false;
										}
									} else if (j == 2) {
										if (click(driver, getNewPartnerShipAddbtnInCreateCommitment(10),
												"new partnership add button", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on add button in new partnership pop up",
													YesNo.No);
											String pencilIcon = "//div[@id='PTEdit" + i + "']";
											WebElement pencilIconElement = FindElement(driver, pencilIcon,
													"partnership pencil icon", action.SCROLLANDBOOLEAN, 5);
											if (pencilIconElement != null) {
												if (click(driver, pencilIconElement, "partnership pencil icon",
														action.SCROLLANDBOOLEAN)) {
													log(LogStatus.INFO, "clicked on partnership pencil icon", YesNo.No);

													for (int k = 2; k < aa.length; k++) {
														String[] labelsWithValue = aa[k].split(":");
														labelsWithValue[0] = labelsWithValue[0].replace("_", " ");
														xpath = "//label[text()='" + labelsWithValue[0]
																+ "']/../following-sibling::td//label/";
														String tag = getTagElementForGivenXpath(xpath + "*[2]");
														if (tag != null) {
															appLog.info("Tag Element for : " + labelsWithValue[0]
																	+ " >>>> " + tag);
															log(LogStatus.INFO, "Tag Element for : "
																	+ labelsWithValue[0] + " >>>> " + tag, YesNo.No);
															if (labelsWithValue.length != 1) {
																String fullXpath = xpath + tag;
																if (verifyValuesOnTheBasisOfTag(fullXpath,
																		labelsWithValue[0], labelsWithValue[1], tag)) {
																	appLog.info("value is verified for "
																			+ labelsWithValue[0] + " with value : "
																			+ labelsWithValue[1]);

																} else {
																	log(LogStatus.ERROR, "value is not verified for "
																			+ labelsWithValue[0] + " with value : "
																			+ labelsWithValue[1], YesNo.Yes);
																	flag = false;
																}
															}
														} else {
															log(LogStatus.ERROR, "Tag Element is null for label : "
																	+ labelsWithValue[0] + " so cannot verify value",
																	YesNo.Yes);
															flag = false;
														}

													}
													if (click(driver, getNewPartnerShipCancelbtnInCreateCommitment(10),
															"cancel button", action.SCROLLANDBOOLEAN)) {
														log(LogStatus.INFO,
																"clicked on new partnership pop up cancel button",
																YesNo.No);
													} else {
														log(LogStatus.INFO,
																"Not able to click on new partnership pop up cancel button",
																YesNo.Yes);
														flag = false;
													}
												} else {
													log(LogStatus.ERROR,
															"Not able to click on partnership pencil icon so cannot click on it and verify filled data",
															YesNo.Yes);
													flag = false;
												}
											} else {
												log(LogStatus.ERROR,
														"partnership pencil icon is not visible so cannot click on it and verify filled data",
														YesNo.Yes);
												flag = false;
											}

										} else {
											log(LogStatus.INFO,
													"Not able to click on add button in new partnership pop up",
													YesNo.Yes);
											flag = false;
										}
									}
								}
							}
						}
						log(LogStatus.INFO, "passed value in text box " + aa[0], YesNo.Yes);

					} else {
						log(LogStatus.ERROR,
								"Not able to pass value in text box " + aa[0] + " so cannot create commitment",
								YesNo.Yes);
						flag = false;
					}
				}
			} else {
				log(LogStatus.ERROR, "Limited partner text box is not visible so cannot create cimmitments", YesNo.Yes);
				flag = false;
			}
			i++;
		}
		if (partnerType != null) {
			if (selectVisibleTextFromDropDown(driver, getPartnerTypeDropDownList(20), "partner type drop down list",
					partnerType)) {
				log(LogStatus.INFO, partnerType + " is selected in partner type drop down list", YesNo.No);
			} else {
				log(LogStatus.ERROR, partnerType + " is not selected in partner type drop down list", YesNo.Yes);
				flag = false;
			}
		}
		if (taxForms != null) {
			if (selectVisibleTextFromDropDown(driver, getTaxformsDropDownList(20), "tax forms drop down list",
					taxForms)) {
				log(LogStatus.INFO, taxForms + " is selected intax forms drop down list", YesNo.No);
			} else {
				log(LogStatus.ERROR, taxForms + " is not selected in tax forms drop down list", YesNo.Yes);
				flag = false;
			}
		}
		if (PlacementFee != null) {
			if (sendKeys(driver, getPlacementFeeTextBox(10), PlacementFee, "placement fee text box",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "pass value in placement fee text box : " + PlacementFee, YesNo.No);
			} else {
				log(LogStatus.ERROR, "Not able to pass value in placement fee text box : " + PlacementFee, YesNo.Yes);
				flag = false;
			}
		}
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			switchToDefaultContent(driver);

		}
		return flag;
	}

	public String getTagElementForGivenXpath(String xpath) {
		WebElement ele = FindElement(driver, xpath, "label xpath", action.SCROLLANDBOOLEAN, 30);
		String tagName = ele.getTagName();
		return tagName;
	}

	public boolean enteringValuesOnTheBasisOfTag(String xpath, String label, String value, String tag) {
		WebElement ele;
		ele = FindElement(driver, xpath, xpath, action.SCROLLANDBOOLEAN, 10);
		ThreadSleep(1000);
		if (tag.equalsIgnoreCase(HTMLTAG.select.toString())) {

			if (selectVisibleTextFromDropDown(driver, ele, label + " Drop Down List", value)) {
				appLog.info("Selected value from " + label + " Drop down List : " + value);
				ThreadSleep(2000);
				return true;
			} else {
				BaseLib.sa.assertTrue(false, "Not Able to Select value from " + label + " Drop down List : " + value);
				appLog.error("Not Able to Select value from " + label + " Drop down List : " + value);
			}

		} else if (tag.equalsIgnoreCase(HTMLTAG.input.toString())) {

			if (sendKeys(driver, ele, value, label + " : " + value, action.BOOLEAN)) {
				appLog.info("Entered Value on " + label + " Text Box : " + value);
				ThreadSleep(2000);
				return true;
			} else {
				BaseLib.sa.assertTrue(false, "Not Able to entered value on " + label + " Text Box : " + value);
				appLog.error("Not Able to entered value on " + label + " Text Box : " + value);
			}

		} else {
			appLog.info("Tag Does not Exist on HTMLTag Enum : " + tag);
		}

		return false;

	}

	public boolean verifyValuesOnTheBasisOfTag(String xpath, String label, String value, String tag) {
		WebElement ele;
		ele = FindElement(driver, xpath, xpath, action.SCROLLANDBOOLEAN, 10);
		ThreadSleep(1000);
		String s = null;
		if (tag.equalsIgnoreCase(HTMLTAG.select.toString())) {

			s = getSelectedOptionOfDropDown(driver, ele, "drop down " + label, "text");

		} else if (tag.equalsIgnoreCase(HTMLTAG.input.toString())) {
			s = ele.getAttribute("value").trim();

		} else {
			appLog.info("Tag Does not Exist on HTMLTag Enum : " + tag);
			return false;
		}
		if (value.equalsIgnoreCase(s)) {
			appLog.info("value from " + label + " is : " + value + " is matched");
			return true;
		} else {
			BaseLib.sa.assertTrue(false, "value from " + label + " is : " + value + " is not matched. Expected: "
					+ value + "\t Actual: " + s);
			appLog.error("value from " + label + " is : " + value + " is not matched. Expected: " + value
					+ "\t Actual: " + s);
		}
		return false;

	}

	public boolean clickOnCreateDealButtonAndVerifyingLandingPage(String environment, String mode, String companyName) {
		boolean flag = false;
		String monthAndYear = getSystemDate("MMM") + " " + getSystemDate("yyyy");
		String expectedPipeLineName = companyName + " " + "-" + " " + monthAndYear;

		if (click(driver, getCreateDealBtn(environment, mode, 10), "Create Deal Button", action.BOOLEAN)) {
			appLog.info("Clicked on Create Deal Button");

			if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
				switchToDefaultContent(driver);
			}

			ThreadSleep(3000);
			flag = true;
			if (getPipelineNameInViewMode(environment, mode, 60) != null) {
				String pipeLineNameViewMode = getText(driver, getPipelineNameInViewMode(environment, mode, 60),
						"PipeLine Name", action.BOOLEAN);
				if (expectedPipeLineName.equalsIgnoreCase(pipeLineNameViewMode)) {
					appLog.info("PipeLine created successfully.:" + pipeLineNameViewMode);

				} else {
					BaseLib.sa.assertTrue(false, "PipeLine Created But not Not Verified - Actual : "
							+ pipeLineNameViewMode + "  Expected  : " + expectedPipeLineName);
					appLog.error("PipeLine Created But not Not Verified - Actual : " + pipeLineNameViewMode
							+ "  Expected  : " + expectedPipeLineName);
				}
			} else {
				BaseLib.sa.assertTrue(false, "Not able to find PipeLine Name in View Mode");
				appLog.error("Not able to find PipeLine Name in View Mode");
			}

		} else {
			BaseLib.sa.assertTrue(false, "Not Able to Click on Create Deal Button");
			appLog.error("Not Able to Click on Create Deal Button");
		}

		return flag;

	}

	public boolean createNewDealPipeLine(String environment, String mode, String companyName, String stage,
			String source) {

		boolean flag = true;

		switchToFrame(driver, 600,
				new NavatarSetupPageBusinessLayer(driver).getnavatarSetUpTabFrame_Lighting(environment, 120));

		WebElement ele = FindElement(driver, "//div[@class='ContentStart']//p[text()='Deal Creation']",
				"Deal Creation Page", action.SCROLLANDBOOLEAN, 60);
		if (ele != null) {
			appLog.info("Deal Creation Page is open");

			if (sendKeys(driver, getCompanyNameTextBox(environment, mode, 10), companyName,
					"Company Name : " + companyName, action.BOOLEAN)) {
				appLog.info("Entered Value on Company Name Text Box : " + companyName);
				ThreadSleep(1000);

				if (selectVisibleTextFromDropDown(driver, getStageDropDownList(environment, mode, 10),
						"Stage Drop Down List", stage)) {
					appLog.info("Selected value from Stage Deop down List : " + stage);

				} else {
					flag = false;
					BaseLib.sa.assertTrue(false, "Not Able to Select value from Stage Deop down List : " + stage);
					appLog.error("Not Able to Select value from Stage Deop down List : " + stage);
				}

				ThreadSleep(1000);
				String actualPipeLineName = getValueFromElementUsingJavaScript(driver,
						getDealNameTextBox(environment, mode, 10), "PipeLine Value");
				String monthAndYear = getSystemDate("MMM") + " " + getSystemDate("yyyy");
				String expectedPipeLineName = companyName + " " + "-" + " " + monthAndYear;

				if ((expectedPipeLineName).equals(actualPipeLineName)) {
					appLog.info("PipeLine Name Value Verified : " + actualPipeLineName);
				} else {
					flag = false;
					BaseLib.sa.assertTrue(false, "PipeLine Name Value Not Verified - Actual : " + actualPipeLineName
							+ "     Expected : " + expectedPipeLineName);
					appLog.error("PipeLine Name Value Not Verified - Actual : " + actualPipeLineName
							+ "     Expected : " + expectedPipeLineName);
				}

			} else {
				flag = false;
				BaseLib.sa.assertTrue(false, "Not Able to entered value on Company Name Text Box : " + companyName);
				appLog.error("Not Able to entered value on Company Name Text Box : " + companyName);
			}

		} else {
			flag = false;
			BaseLib.sa.assertTrue(false, "Deal Creation Page is not open");
			appLog.error("Deal Creation Page is not open");
		}

		return flag;

	}

	public boolean createIndiviualInvestor(String environment, String mode, String[][] labelNamesAndValue,
			String clickOnCopyAddress, TopOrBottom topOrBottom) {
		boolean flag = false;
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		switchToFrame(driver, 10, getCreateCommitmentFrame_Lighting(20));

		ThreadSleep(10000);
		if (enterValuesInCreateIndiviualInvestor(environment, mode, labelNamesAndValue)) {
			log(LogStatus.INFO, "Enter Value Successfully in passed labels", YesNo.No);
			ThreadSleep(500);
			if (clickOnCopyAddress != null && clickOnCopyAddress.equalsIgnoreCase("Yes")) {
				click(driver, getCoptyMailingAddressToOther(30), "Copy link to mailing address link",
						action.SCROLLANDBOOLEAN);
			}
			if (click(driver, getCreateIndiviualInvestorBtn(environment, mode, topOrBottom, 20),
					"create indiviual investor button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on create indiviual investor button", YesNo.No);
				flag = true;
			} else {
				log(LogStatus.ERROR, "Not able to click on create indiviual investor button", YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Not able to pass value in Label so cannot create indiviual investor", YesNo.Yes);
		}

		switchToDefaultContent(driver);

		return flag;
	}

	public boolean enterValuesInCreateIndiviualInvestor(String environment, String mode,
			String[][] labelNamesAndValue) {
		boolean flag = true;
		String xpath = "";
		String label = "";
		WebElement ele = null;
		for (String[] labelAndValue : labelNamesAndValue) {
			if (labelAndValue[0].equalsIgnoreCase(IndiviualInvestorFieldLabel.Fund_Preferences.toString())
					|| labelAndValue[0].equalsIgnoreCase(IndiviualInvestorFieldLabel.Industry_Preferences.toString())
					|| labelAndValue[0]
							.equalsIgnoreCase(IndiviualInvestorFieldLabel.Preferred_Mode_of_Contact.toString())) {
				label = labelAndValue[0].replace("_", " ");
				if (labelAndValue[0]
						.equalsIgnoreCase(IndiviualInvestorFieldLabel.Preferred_Mode_of_Contact.toString())) {
					xpath = "//label[contains(text(),'" + label + "')]/../following-sibling::td//select";
				} else {
					xpath = "//label[contains(text(),'" + label + "')]/../following-sibling::td//select[@title='"
							+ label + " - Available']";
				}
				ele = FindElement(driver, xpath, label + " text box", action.SCROLLANDBOOLEAN, 20);
				if (ele != null) {
					if (selectVisibleTextFromDropDown(driver, ele, label + " multiselect drop down",
							labelAndValue[1])) {
						log(LogStatus.INFO, "select value : " + labelAndValue[1] + " in " + label, YesNo.No);
						if (!labelAndValue[0]
								.equalsIgnoreCase(IndiviualInvestorFieldLabel.Preferred_Mode_of_Contact.toString())) {
							xpath = "//select[@title='" + label
									+ " - Available']/../following-sibling::td/a[@title='Add']";
							ele = FindElement(driver, xpath, label + " text box", action.SCROLLANDBOOLEAN, 20);
							if (ele != null) {
								if (click(driver, ele, label + " add button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on add button label : " + label, YesNo.No);

								} else {
									log(LogStatus.ERROR, "Not able to click on  " + label
											+ " add button so cannot  move value : " + labelAndValue[1], YesNo.Yes);
									flag = false;
								}
							} else {
								log(LogStatus.ERROR, label + " : label add button is not visible so cannot move value :"
										+ labelAndValue[1], YesNo.Yes);
								flag = false;
							}
						}
					} else {
						log(LogStatus.ERROR,
								"Not able to select value " + labelAndValue[1] + " in multiselect : " + label,
								YesNo.Yes);
						flag = false;
					}
				} else {
					log(LogStatus.ERROR, label + " : label is not visible so cannot select value : " + labelAndValue[1],
							YesNo.Yes);
					flag = false;
				}

			} else {
				if (labelAndValue[0].equalsIgnoreCase(IndiviualInvestorFieldLabel.Contact_Description.toString())
						|| labelAndValue[0].equalsIgnoreCase(IndiviualInvestorFieldLabel.Mailing_Street.toString())
						|| labelAndValue[0].equalsIgnoreCase(IndiviualInvestorFieldLabel.Other_Street.toString())) {
					label = labelAndValue[0].replace("_", " ");
					xpath = "//label[contains(text(),'" + label + "')]/../following-sibling::td//textarea";
				} else {
					label = labelAndValue[0].replace("_", " ");
					xpath = "//label[contains(text(),'" + label + "')]/../following-sibling::td//input";
				}

				ele = FindElement(driver, xpath, label + " text box", action.SCROLLANDBOOLEAN, 120);
				if (ele != null) {
					if (sendKeys(driver, ele, labelAndValue[1], label + " text box", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "passed value : " + labelAndValue[1] + " in " + label, YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to pass value " + labelAndValue[1] + " in label : " + label,
								YesNo.Yes);
						flag = false;
					}
				} else {
					log(LogStatus.ERROR, label + " : label is not visible so cannot enter value : " + labelAndValue[1],
							YesNo.Yes);
					flag = false;
				}
			}
		}

		return flag;

	}

	public boolean verifyAddedTabsInHomepage(String tabs, String mode) {
		List<String> result;
		List<WebElement> allTabs = getAllAddedTabList(mode);

		ThreadSleep(2000);
		if (!allTabs.isEmpty()) {
			ThreadSleep(3000);

			// result=compareMultipleList(driver, tabs, allTabs);
			result = compareMultipleListOnBasisOfTitle(driver, tabs, allTabs);

			if (result.isEmpty()) {

				log(LogStatus.PASS, "all homepage added tab successfully verified", YesNo.No);
				return true;
			} else {

				log(LogStatus.FAIL, "all homepage added tab is not verified rsult:" + result, YesNo.No);
				return false;
			}

		} else {
			log(LogStatus.FAIL, "Not abel to get the list of added tabs size:" + allTabs.size(), YesNo.No);
			return false;
		}

	}

	public boolean verifyHomepageAppLogo(String AppName) {

		if (getAppLogoHeaer(30).isDisplayed()) {
			log(LogStatus.FAIL, "App logo is  visible on homepae", YesNo.No);
			ThreadSleep(2000);
			String text = getAppLogoHeaer(30).getText().trim();
			if (text.equalsIgnoreCase(AppName.trim())) {
				log(LogStatus.PASS, "App logo is successfuly verified on homepage", YesNo.No);
				return true;
			} else {
				log(LogStatus.FAIL, "App logo is not verifid on homepae", YesNo.No);
				return false;
			}

		} else {
			log(LogStatus.FAIL, "App logo is not displayed on homepae", YesNo.No);
			return false;

		}

	}

	public boolean verifyTodaayTaskandTodayEventSectionVisibleOnHomepage(int timeOut) {

		boolean status = getTodayTaskSection(timeOut).isDisplayed();
		boolean status1 = getTodayEventSection(timeOut).isDisplayed();

		if (status && status1) {

			log(LogStatus.PASS, "Today task and  today event section is visibile on homepage", YesNo.No);
			return true;
		} else {
			log(LogStatus.FAIL, "Today task and today event section is not visibile on homepage", YesNo.Yes);
			return false;
		}
	}

	/**
	 * @author Ankur Huria
	 * @param Title
	 * @param fieldsInComponent
	 */

	public boolean verifyColumnsOfSDG(String Title, List<String> fieldsInComponent) {
		boolean flag = false;
		WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + Title + "']",
				"Component Title ", action.SCROLLANDBOOLEAN, 10);
		if (alreadyAddedComponentToHomePage != null) {

			log(LogStatus.INFO, "Component Title Matched to Home Page " + Title, YesNo.Yes);

			if (!verifySDGExpandByDefault(Title)) {
				log(LogStatus.INFO, "Not Expanded By Default SDG: " + Title, YesNo.No);
				log(LogStatus.INFO, "Now Expanding  SDG: " + Title, YesNo.No);

				WebElement TooltipElement = FindElement(driver,
						"//a[text()='" + Title + "']/ancestor::article/preceding-sibling::lightning-icon", "Tooltip",
						action.SCROLLANDBOOLEAN, 20);
				if (click(driver, TooltipElement, "Collapse/Expand Element", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on Collapse/Expand");
					flag = true;
				} else {
					log(LogStatus.ERROR, "Not Able to click on Expand Button of SDG :" + Title, YesNo.No);

				}

			}

			else {
				log(LogStatus.INFO, "Expanded By Default SDG :" + Title, YesNo.No);
				flag = true;

			}
			if (flag) {
				List<WebElement> columns = FindElements(driver,
						"//a[text()='" + Title + "']/ancestor::article//thead//th[contains(@class,'navpeI')]//span",
						"Records");
				List<String> columnsText = new ArrayList<String>();
				for (WebElement column : columns) {
					columnsText.add(column.getText());
				}
				System.out.println(columnsText);
				if (CommonLib.compareList(columnsText, fieldsInComponent)) {
					log(LogStatus.INFO, "All Fields are Matched ", YesNo.No);
					flag = true;

				} else {
					log(LogStatus.ERROR, "All Fields are not Matched", YesNo.No);

				}

			} else {
				log(LogStatus.ERROR, "Component Title Not Matched to Home Page :" + Title, YesNo.No);

			}
		}
		return flag;

	}

	/**
	 * @author Ankur Huria
	 * @param Title
	 */

	public boolean verifySDGExpandByDefault(String Title) {
		boolean flag = false;
		WebElement expandElement = FindElement(driver,
				"//a[text()='" + Title + "']/ancestor::article//div[@class='slds-hide']/following-sibling::div",
				"Expand Element of SDG: " + Title, action.SCROLLANDBOOLEAN, 10);
		if (expandElement != null) {

			log(LogStatus.INFO, "Expand Element Found of SDG: " + Title, YesNo.Yes);
			CommonLib.ThreadSleep(7000);
			String display = CommonLib.getAttribute(driver, expandElement, "Expand Element of SDG: " + Title, "style");

			if (display.contains("block")) {
				log(LogStatus.INFO, "-------------SDG of Title:  " + Title + " is Expanded------------", YesNo.No);
				flag = true;

			} else {
				log(LogStatus.ERROR, "-------------SDG of Title:  " + Title + " is not Expanded------------", YesNo.No);

			}

		} else {
			log(LogStatus.ERROR, "Expand Element Not Found of SDG:  " + Title, YesNo.No);

		}
		return flag;

	}

	/**
	 * @author Ankur Huria
	 * @param Title
	 * @param rowNumber
	 */
	public boolean verifySDGTooltipForARecord(String Title, int rowNumber) {
		boolean flag = false;
		List<WebElement> TooltipElements = FindElements(driver, "//a[text()='" + Title
				+ "']/ancestor::article//tbody/tr[" + rowNumber + "]/td//lightning-formatted-url", "Tooltip");
		List<WebElement> TooltipAnchorElements = FindElements(driver, "//a[text()='" + Title
				+ "']/ancestor::article//tbody/tr[" + rowNumber + "]/td//lightning-formatted-url//a", "Tooltip Anchor");

		if (TooltipElements.size() != 0) {

			log(LogStatus.INFO, "1st Tooltip Element Found of SDG: " + Title, YesNo.Yes);
			int i = 0;
			for (WebElement ele : TooltipElements) {
				if (CommonLib.getAttribute(driver, ele, "", "title").equals(TooltipAnchorElements.get(i).getText())) {
					appLog.info("Toototip Verified : " + getAttribute(driver, ele, "", "title"));
					log(LogStatus.INFO, "Toototip Verified : " + getAttribute(driver, ele, "", "title"), YesNo.No);
					flag = true;
				} else {
					appLog.error("Toototip Not Verified : " + getAttribute(driver, ele, "", "title"));
					log(LogStatus.ERROR, "Toototip Not Verified : " + getAttribute(driver, ele, "", "title"), YesNo.No);
				}
				i++;
			}

		} else {
			appLog.error("1st type of Tooltip Elements Not Found of SDG: " + Title);
			log(LogStatus.ERROR, "1st type of Tooltip Elements Not Found of SDG: " + Title, YesNo.No);

		}

		List<WebElement> TooltipElements2 = FindElements(driver, "//a[text()='" + Title
				+ "']/ancestor::article//tbody/tr[" + rowNumber + "]/td//lightning-formatted-text", "Tooltip");

		if (TooltipElements2.size() != 0) {
			flag = false;
			log(LogStatus.INFO, "1st Tooltip Element Found of SDG: " + Title, YesNo.Yes);
			int i = 0;
			for (WebElement ele : TooltipElements2) {
				if (CommonLib.getAttribute(driver, ele, "", "title").equals(ele.getText())) {
					appLog.info("Toototip Verified : " + getAttribute(driver, ele, "", "title"));
					log(LogStatus.INFO, "Toototip Verified : " + getAttribute(driver, ele, "", "title"), YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Toototip Not Verified : " + getAttribute(driver, ele, "", "title"), YesNo.No);
					appLog.error("Toototip Not Verified : " + getAttribute(driver, ele, "", "title"));
				}
				i++;
			}

		} else {
			appLog.error("2nd type of Tooltip Elements Not Found of SDG: " + Title);
			log(LogStatus.ERROR, "2nd type of Tooltip Elements Not Found of SDG: " + Title, YesNo.No);

		}

		List<WebElement> TooltipElements3 = FindElements(driver,
				"//a[text()='" + Title + "']/ancestor::article//tbody/tr[" + rowNumber + "]/td//a[text()='0']",
				"Tooltip");

		if (TooltipElements3.size() != 0) {
			flag = false;
			log(LogStatus.INFO, "1st Tooltip Element Found of SDG: " + Title, YesNo.Yes);

			for (WebElement ele : TooltipElements3) {
				if (CommonLib.getAttribute(driver, ele, "", "title").equals(ele.getText())) {
					appLog.info("Toototip Verified : " + getAttribute(driver, ele, "", "title"));
					log(LogStatus.INFO, "Toototip Verified : " + getAttribute(driver, ele, "", "title"), YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Toototip Not Verified : " + getAttribute(driver, ele, "", "title"), YesNo.No);
					appLog.error("Toototip Not Verified : " + getAttribute(driver, ele, "", "title"));
				}

			}

		} else {
			appLog.error("3rd type of Tooltip Elements Not Found of SDG: " + Title);
			log(LogStatus.ERROR, "3rd type of Tooltip Elements Not Found of SDG: " + Title, YesNo.No);

		}
		return flag;

	}

	/**
	 * @author Ankur Huria
	 * @param Title
	 */
	public boolean verifySDGTooltipForExpandAndCollapse(String Title) {
		boolean flag = false;

		if (TooltipElement(Title) != null) {

			log(LogStatus.INFO, "Collapse/Expand Tooltip Element Found of SDG: " + Title, YesNo.Yes);
			if (CommonLib.getAttribute(driver, TooltipElement(Title), "", "title").equalsIgnoreCase("Collapse")) {
				appLog.info("Toototip Verified : " + getAttribute(driver, TooltipElement(Title), "", "title"));
				log(LogStatus.INFO, "Toototip Verified : " + getAttribute(driver, TooltipElement(Title), "", "title"),
						YesNo.No);
				if (click(driver, TooltipElement(Title), "Collapse/Expand Element", action.SCROLLANDBOOLEAN))
					appLog.info("clicked on Collapse/Expand");
				WebElement expandElement = FindElement(driver,
						"//a[text()='" + Title + "']/ancestor::article//div[@class='slds-hide']/following-sibling::div",
						"Expand Element of SDG: " + Title, action.SCROLLANDBOOLEAN, 10);
				if (expandElement != null) {
					String display = CommonLib.getAttribute(driver, expandElement, "Expand Element of SDG: " + Title,
							"style");

					if (display.contains("none")) {

						appLog.info("----SDG gets Collapsed----");
						log(LogStatus.INFO, "----SDG gets Collapsed-----", YesNo.No);
						flag = true;
					} else {
						appLog.error("----SDG not gets Collapsed----");
						log(LogStatus.ERROR, "----SDG not gets Collapsed-----", YesNo.No);

					}
				}
			} else if (CommonLib.getAttribute(driver, TooltipElement(Title), "", "title").equalsIgnoreCase("Expand")) {
				flag = false;
				if (click(driver, TooltipElement(Title), "Collapse/Expand Element", action.SCROLLANDBOOLEAN))
					appLog.info("clicked on Collapse/Expand");
				WebElement expandElement = FindElement(driver,
						"//a[text()='" + Title + "']/ancestor::article//div[@class='slds-hide']/following-sibling::div",
						"Expand Element of SDG: " + Title, action.SCROLLANDBOOLEAN, 10);
				if (expandElement != null) {
					String display = CommonLib.getAttribute(driver, expandElement, "Expand Element of SDG: " + Title,
							"style");

					if (display.contains("block")) {

						appLog.info("----SDG gets Expanded----");
						log(LogStatus.INFO, "----SDG gets Expanded-----", YesNo.No);
						flag = true;
					} else {
						appLog.error("----SDG not gets Expanded----");
						log(LogStatus.ERROR, "----SDG not gets Expanded-----", YesNo.No);

					}
				}
			} else {
				flag = false;
				if (click(driver, TooltipElement(Title), "Collapse/Expand Element", action.SCROLLANDBOOLEAN))
					appLog.info("clicked on Collapse/Expand");
				WebElement expandElement = FindElement(driver,
						"//a[text()='" + Title + "']/ancestor::article//div[@class='slds-hide']/following-sibling::div",
						"Expand Element of SDG: " + Title, action.SCROLLANDBOOLEAN, 10);
				if (expandElement != null) {
					String display = CommonLib.getAttribute(driver, expandElement, "Expand Element of SDG: " + Title,
							"style");

					if (display.contains("none")) {

						appLog.info("----SDG gets Collapsed----");
						log(LogStatus.INFO, "----SDG gets Collapsed-----", YesNo.No);
						flag = true;
					} else {
						appLog.error("----SDG not gets Collapsed----");
						log(LogStatus.ERROR, "----SDG not gets Collapsed-----", YesNo.No);

					}

				}
			}

		}

		else {
			appLog.error("Collapse/Expand Tooltip Element Not Found of SDG: " + Title);
			log(LogStatus.ERROR, "Collapse/Expand Tooltip Element Not Found of SDG: " + Title, YesNo.No);

		}
		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param Title
	 */
	public boolean verifyCollapseTooltipAFterGoingToInstitutionPageAndComingBack(String Title) {
		boolean flag = false;

		if (TooltipElement(Title) != null) {

			log(LogStatus.INFO, "Collapse/Expand Tooltip Element Found of SDG: " + Title, YesNo.Yes);
			if (CommonLib.getAttribute(driver, TooltipElement(Title), "", "title").equalsIgnoreCase("Collapse")) {
				appLog.info("Toototip Verified : " + getAttribute(driver, TooltipElement(Title), "", "title"));
				log(LogStatus.INFO, "Toototip Verified : " + getAttribute(driver, TooltipElement(Title), "", "title"),
						YesNo.No);
				flag = true;
			} else {
				log(LogStatus.ERROR, "Toototip is Not Collapsed ", YesNo.No);
				appLog.error("Toototip is Not Collapsed : ");
			}
		}

		else {
			appLog.error("Collapse/Expand Tooltip Element Not Found of SDG: " + Title);
			log(LogStatus.ERROR, "Collapse/Expand Tooltip Element Not Found of SDG: " + Title, YesNo.No);

		}
		return flag;

	}

	/**
	 * @author Ankur Huria
	 * @param Title
	 */
	public boolean verifyGearIconPresentAndVerifyTooltip(String Title) {
		boolean flag = false;

		if (gearIcon(Title) != null) {

			log(LogStatus.INFO, "Gear Icon Element Found of SDG: " + Title, YesNo.Yes);
			if (CommonLib.getAttribute(driver, gearIcon(Title), "", "title").equalsIgnoreCase("Open SDG record.")) {
				appLog.info("Toototip Verified : " + getAttribute(driver, gearIcon(Title), "", "title"));
				log(LogStatus.INFO, "Toototip Verified : " + getAttribute(driver, gearIcon(Title), "", "title"),
						YesNo.No);
				flag = true;
			} else {
				log(LogStatus.ERROR, "Toototip is Not Verified " + getAttribute(driver, gearIcon(Title), "", "title"),
						YesNo.No);
				appLog.error("Toototip is Not Verified : " + getAttribute(driver, gearIcon(Title), "", "title"));
			}
		}

		else {
			appLog.error("Gear Icon Element Not Found of SDG: " + Title);
			log(LogStatus.ERROR, "Gear Icon Element Not Found of SDG: " + Title, YesNo.No);

		}
		return flag;

	}

	/**
	 * @author Ankur Huria
	 * @param SDGGridName
	 */

	public void verifyColumnAscendingDescendingOrder(SDGGridName sdgGridName, List<String> columnNames,
			List<String> dateColumns) {

		List<WebElement> headerList = sdgGridAllHeadersLabelNameList(sdgGridName);
		List<String> columnDataText = headerList.stream().map(s -> s.getText()).collect(Collectors.toList()).stream()
				.map(t -> t.trim()).collect(Collectors.toList());
		if (!headerList.isEmpty()) {
			int i = 0;
			for (String columnName : columnNames) {
				int columnIndex = columnDataText.indexOf(columnName);
				if (i == 0) {
					if (CommonLib.checkSorting(driver, SortOrder.Decending,
							sdgGridColumnsDataList(sdgGridName.toString(), columnIndex + 1))) {
						log(LogStatus.PASS, SortOrder.Decending + "Check Sorting on " + columnName + " Columns ",
								YesNo.No);
					} else {
						log(LogStatus.FAIL,
								"Not Checked " + SortOrder.Decending + "Sorting on " + columnName + " Columns ",
								YesNo.No);
						sa.assertTrue(false,
								"Not Checked " + SortOrder.Decending + "Sorting on " + columnName + " Columns ");
					}
				} else {
					if (clickUsingJavaScript(driver, headerList.get(columnIndex),
							sdgGridName.toString() + " SDG Grid header column " + columnName,
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "Clicked on Header" + columnName + " Clomun " + (columnIndex + 1) + " for "
								+ SortOrder.Assecending, YesNo.No);
						ThreadSleep(35000);

						if (!dateColumns.contains(columnName)) {

							if (CommonLib.checkSorting(driver, SortOrder.Assecending,
									sdgGridColumnsDataList(sdgGridName.toString(), columnIndex + 1))) {
								log(LogStatus.PASS, SortOrder.Assecending + " Check Sorting on "
										+ sdgGridName.toString() + " Columns " + columnName, YesNo.No);
							} else {
								log(LogStatus.FAIL, SortOrder.Assecending + " Not Checked Sorting on "
										+ sdgGridName.toString() + " Columns " + columnName, YesNo.No);
								sa.assertTrue(false, SortOrder.Assecending + " Not Checked Sorting on "
										+ sdgGridName.toString() + " Columns " + columnName);
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
								log(LogStatus.PASS, SortOrder.Assecending + " Check Sorting on "
										+ sdgGridName.toString() + " Columns " + columnName, YesNo.No);
							} else {
								log(LogStatus.FAIL, SortOrder.Assecending + " Not Checked Sorting on "
										+ sdgGridName.toString() + " Columns " + columnName, YesNo.No);
								sa.assertTrue(false, SortOrder.Assecending + " Not Checked Sorting on "
										+ sdgGridName.toString() + " Columns " + columnName);
							}

						}

					} else {
						log(LogStatus.PASS, "Not able to click on " + sdgGridName.toString() + " SDG Grid header "
								+ columnName + " so cannot check Sorting " + SortOrder.Assecending, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on " + sdgGridName.toString() + " SDG Grid header "
								+ columnName + " so cannot check Sorting " + SortOrder.Assecending);
					}
					headerList = sdgGridAllHeadersLabelNameList(sdgGridName);
				}
				if (i == 0) {
					if (clickUsingJavaScript(driver, headerList.get(columnIndex),
							sdgGridName.toString() + " SDG Grid header column " + columnName,
							action.SCROLLANDBOOLEAN)) {
						ThreadSleep(35000);
						log(LogStatus.PASS,
								"Clicked on Header" + columnName + " Clomun " + (i + 1) + SortOrder.Assecending,
								YesNo.No);
						if (CommonLib.checkSorting(driver, SortOrder.Assecending,
								sdgGridColumnsDataList(sdgGridName.toString(), columnIndex + 1))) {
							log(LogStatus.PASS, SortOrder.Assecending + " Check Sorting on " + columnName
									+ " Column on " + sdgGridName.toString() + " SDG Grid", YesNo.No);
						} else {
							log(LogStatus.FAIL, "Not Checked " + SortOrder.Assecending + " Sorting on "
									+ sdgGridName.toString() + " Columns " + columnName, YesNo.No);
							sa.assertTrue(false, "Not Checked " + SortOrder.Assecending + " Sorting on "
									+ sdgGridName.toString() + " Columns " + columnName);
						}
					} else {
						log(LogStatus.PASS, "Not able to click on " + sdgGridName.toString() + " SDG Grid header "
								+ columnName + " so cannot check Sorting " + SortOrder.Decending, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on " + sdgGridName.toString() + " SDG Grid header "
								+ columnName + " so cannot check Sorting " + SortOrder.Decending);
					}
				} else {
					if (clickUsingJavaScript(driver, headerList.get(columnIndex),
							sdgGridName.toString() + " SDG Grid header column", action.SCROLLANDBOOLEAN)) {
						ThreadSleep(35000);
						log(LogStatus.PASS, "Clicked on Header " + columnName + " Clomun " + (columnIndex + 1)
								+ SortOrder.Decending, YesNo.No);

						if (!dateColumns.contains(columnName)) {
							if (CommonLib.checkSorting(driver, SortOrder.Decending,
									sdgGridColumnsDataList(sdgGridName.toString(), columnIndex + 1))) {
								log(LogStatus.PASS, SortOrder.Decending + " Check Sorting on " + columnName
										+ " Columns on SDG Grid " + sdgGridName.toString(), YesNo.No);
							} else {
								log(LogStatus.FAIL, "Not Checked " + SortOrder.Decending + " Sorting on "
										+ sdgGridName.toString() + " Columns " + columnName, YesNo.No);
								sa.assertTrue(false, "Not Checked " + SortOrder.Decending + " Sorting on "
										+ sdgGridName.toString() + " Columns " + columnName);
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
								log(LogStatus.PASS, SortOrder.Decending + " Check Sorting on " + columnName
										+ " Columns on SDG Grid " + sdgGridName.toString(), YesNo.No);
							} else {
								log(LogStatus.FAIL, "Not Checked " + SortOrder.Decending + " Sorting on "
										+ sdgGridName.toString() + " Columns " + columnName, YesNo.No);
								sa.assertTrue(false, "Not Checked " + SortOrder.Decending + " Sorting on "
										+ sdgGridName.toString() + " Columns " + columnName);
							}

						}
					} else {
						log(LogStatus.PASS, "Not able to click on " + sdgGridName.toString() + " SDG Grid header "
								+ columnName + " so cannot check Sorting " + SortOrder.Assecending, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on " + sdgGridName.toString() + " SDG Grid header "
								+ columnName + " so cannot check Sorting " + SortOrder.Assecending);
					}
				}
				headerList = sdgGridAllHeadersLabelNameList(sdgGridName);
				i++;
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
	 * @param Title
	 */
	public boolean sdgGridExpandedByDefaultIfNotThenExpand(String Title) {
		boolean flag = false;
		CommonLib.ThreadSleep(8000);
		WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + Title + "']",
				"Component Title ", action.SCROLLANDBOOLEAN, 10);
		if (alreadyAddedComponentToHomePage != null) {

			log(LogStatus.INFO, "Component Title Matched to Home Page " + Title, YesNo.Yes);

			if (!verifySDGExpandByDefault(Title)) {
				log(LogStatus.INFO, "Not Expanded By Default SDG: " + Title, YesNo.No);
				log(LogStatus.INFO, "Now Expanding  SDG: " + Title, YesNo.No);

				WebElement TooltipElement = FindElement(driver,
						"//a[text()='" + Title + "']/ancestor::article/preceding-sibling::lightning-icon", "Tooltip",
						action.SCROLLANDBOOLEAN, 20);
				if (click(driver, TooltipElement, "Collapse/Expand Element", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on Collapse/Expand");
					flag = true;
				} else {
					log(LogStatus.ERROR, "Not Able to click on Expand Button of SDG :" + Title, YesNo.No);

				}
			}

			else {
				log(LogStatus.INFO, "Expanded By Default SDG :" + Title, YesNo.No);
				flag = true;

			}
		} else {
			log(LogStatus.ERROR, "Component Title Not Matched to Home Page :" + Title, YesNo.No);

		}

		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param Title
	 * @param PageSize
	 */

	public boolean pageSizeSelect(String Title, String pageSize) {

		boolean flag = false;
		WebElement pageSizeSelect = FindElement(driver,
				"//a[text()='" + Title + "']/ancestor::article//span[text()='Page Size']/../parent::div//select",
				"Page Size Select ", action.SCROLLANDBOOLEAN, 10);
		if (pageSizeSelect != null) {
			if (CommonLib.selectVisibleTextFromDropDown(driver, pageSizeSelect, "Page Size Select", pageSize)) {
				log(LogStatus.INFO, "Selected the Page Size", YesNo.No);
				CommonLib.ThreadSleep(25000);
				flag = true;
			} else {
				log(LogStatus.ERROR, "Not Able To Select Page Size ", YesNo.No);
				return flag;

			}
		} else {
			log(LogStatus.ERROR, "Not Able To Select Page Size As Element not Found", YesNo.No);
			flag = true;

		}

		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param Title
	 * @param NoOfRecordsLessThanEqualHundred
	 */
	public boolean numberOfRecordsMatch(String Title, int NoOfRecordsLessThanEqualHundred) {
		boolean flag = false;
		List<WebElement> records = FindElements(driver,
				"//a[text()=\"" + Title
						+ "\"]/ancestor::article//tbody/tr//span[not(text()=\"No data returned\")]/ancestor::tr",
				"Records");
		System.out.println("No. of Records Present: " + records.size());
		if (records.size() == NoOfRecordsLessThanEqualHundred) {
			log(LogStatus.INFO, "No. of Records Matched: " + NoOfRecordsLessThanEqualHundred, YesNo.No);
			flag = true;

		} else {
			log(LogStatus.ERROR, "No. of Records not Matched, Expected:" + NoOfRecordsLessThanEqualHundred
					+ " , Actual: " + records.size(), YesNo.No);

			sa.assertTrue(false, "-----------No. of Records not Matched-----> " + "Actual: " + records.size()
					+ "but Expected: " + NoOfRecordsLessThanEqualHundred + "--------------");

		}
		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param Title
	 * @param FundName
	 */
	public boolean deleteSDGRecord(String Title, String FundName) {
		boolean flag = false;

		if (fundNameElement(Title, FundName) != null) {
			log(LogStatus.INFO, "Record Found " + FundName, YesNo.No);
			if (click(driver, deleteRecordBtn(Title, FundName), "Delete Button: " + FundName,
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Delete button of the record " + FundName, YesNo.No);

				if (click(driver, deleteRecordConfirmBtn(Title), "Delete Confirm Button", action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Delete Confirm Button", YesNo.No);
					flag = true;
					String msg = getText(dDriver, deleteRecordMsg(10), "", action.BOOLEAN).trim();
					if (msg != null && !msg.equalsIgnoreCase("") && !msg.isEmpty()) {

						if (msg.contains("Successfully")) {
							log(LogStatus.INFO, "Message Verified: " + msg, YesNo.No);
							CommonLib.ThreadSleep(30000);
							flag = true;
						} else {
							log(LogStatus.FAIL, "Not Able to Delete, Message : " + msg, YesNo.No);
							sa.assertTrue(false, "-----------Msg Showing: " + msg + "------------");
							CommonLib.ThreadSleep(10000);
						}

					}

					else {
						log(LogStatus.ERROR, "Message Not Verified: Succesfully Delete Record", YesNo.No);
					}

				}

				else {
					log(LogStatus.ERROR, "Not Able to click on Delete Confirm Button", YesNo.No);
				}

			}

			else {
				log(LogStatus.ERROR, "Not able to Click on Delete Button of: " + FundName, YesNo.No);

			}

		}

		else {
			log(LogStatus.ERROR, "Record not Found: " + FundName, YesNo.No);

		}

		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param Title
	 */
	public int numberOfRecordsInComponent(String Title) {

		List<WebElement> records = FindElements(driver,
				"//a[text()=\"" + Title
						+ "\"]/ancestor::article//tbody/tr//span[not(text()=\"No data returned\")]/ancestor::tr",
				"Records");
		System.out.println("No. of Records Present: " + records.size());
		if (records.size() != 0) {
			log(LogStatus.INFO, "No. of Records: " + records.size(), YesNo.No);

		} else {
			log(LogStatus.ERROR, "No Records Found ", YesNo.No);

			sa.assertTrue(false, "-----------No Records Found --------- ");

		}
		return records.size();
	}

	/**
	 * @author Ankur Huria
	 * @param sdgGridName
	 * @param columnNames
	 */
	public boolean verifyColumnRecordsRedirecting(SDGGridName sdgGridName, List<String> columnNames) {

		List<WebElement> headerList = sdgGridAllHeadersNameList(sdgGridName);
		List<String> columnDataText = headerList.stream().map(s -> s.getText()).collect(Collectors.toList()).stream()
				.map(t -> t.trim()).collect(Collectors.toList());
		boolean flag = false;
		if (!headerList.isEmpty()) {
			for (String columnName : columnNames) {
				int columnIndex = columnDataText.indexOf(columnName);
				if (sdgGridColumnsDataListAnchorTag(sdgGridName.toString(), columnIndex + 1).size() != 0) {
					log(LogStatus.PASS, "------Column : " + columnName + " contains Redirect URL Data--------",
							YesNo.No);
					for (WebElement columnData : sdgGridColumnsDataListAnchorTag(sdgGridName.toString(),
							columnIndex + 1)) {

						if (CommonLib.getAttribute(driver, columnData, columnData.getText(), "target")
								.equals("_blank")) {
							log(LogStatus.PASS, "Column Data: " + columnData.getText() + " will Redirect to New Tab",
									YesNo.No);
							flag = true;
						} else {
							log(LogStatus.FAIL,
									"Column Data: " + columnData.getText() + " will Not Redirect to New Tab", YesNo.No);
							sa.assertTrue(false,
									"Column Data: " + columnData.getText() + " will Not Redirect to New Tab");

						}

					}
				} else {
					log(LogStatus.INFO, "-------Column : " + columnName + " not contains any Redirect URL Data--------",
							YesNo.No);
				}
			}
		} else {
			log(LogStatus.ERROR, "-----No Column Present for SDG: " + sdgGridName + " -----", YesNo.No);
			sa.assertTrue(false, "-----No Column Present for SDG: " + sdgGridName + " -----");
		}

		return flag;

	}

	/**
	 * @author Ankur Huria
	 * @param sdgGridName
	 * @param columnNames
	 * @param columnRecordToVerify
	 */
	public String verifyColumnRecordRedirectAndReturnNewWindowId(SDGGridName sdgGridName, String columnName,
			List<String> columnRecordToVerify, int indexColumnDataFromList) {

		List<WebElement> headerList = sdgGridAllHeadersLabelNameList(sdgGridName);
		List<String> columnDataText = headerList.stream().map(s -> s.getText()).collect(Collectors.toList()).stream()
				.map(t -> t.trim()).collect(Collectors.toList());
		String parentWindowId = null;
		if (!headerList.isEmpty()) {

			int columnIndex = columnDataText.indexOf(columnName);
			if (sdgGridColumnsDataListAnchorTag(sdgGridName.toString(), columnIndex + 1).size() != 0) {
				log(LogStatus.PASS, "------Column : " + columnName + " contains Redirect URL Data--------", YesNo.No);
				for (WebElement columnData : sdgGridColumnsDataListAnchorTag(sdgGridName.toString(), columnIndex + 1)) {

					if (columnData.getText().equals(columnRecordToVerify.get(indexColumnDataFromList))) {
						if (CommonLib.getAttribute(driver, columnData, columnData.getText(), "target")
								.equals("_blank")) {
							log(LogStatus.PASS, "Column Data: " + columnData.getText() + " will Redirect to New Tab",
									YesNo.No);
							ThreadSleep(2000);
							if (clickUsingJavaScript(driver, columnData, columnData.getText(),
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on :" + columnData.getText(), YesNo.No);
								parentWindowId = CommonLib.switchOnWindow(driver);
								if (parentWindowId != null) {
									log(LogStatus.INFO, "Switched to New Tab", YesNo.No);
									return parentWindowId;
								} else {
									log(LogStatus.ERROR, "New Tab not Open After click on: " + columnData.getText(),
											YesNo.No);
								}

							} else {
								log(LogStatus.ERROR, "Not Able to Click on :" + columnData.getText(), YesNo.No);
							}

						} else {
							log(LogStatus.FAIL,
									"Column Data: " + columnData.getText() + " will Not Redirect to New Tab", YesNo.No);
							sa.assertTrue(false,
									"Column Data: " + columnData.getText() + " will Not Redirect to New Tab");

						}
					}

				}
			} else {
				log(LogStatus.INFO, "-------Column : " + columnName + " not contains any Redirect URL Data--------",
						YesNo.No);
			}

		} else {
			log(LogStatus.ERROR, "-----No Column Present for SDG: " + sdgGridName + " -----", YesNo.No);
			sa.assertTrue(false, "-----No Column Present for SDG: " + sdgGridName + " -----");
		}

		return null;

	}

	/**
	 * @author Ankur Huria
	 * @param SDGName
	 * @param Filter
	 */
	public boolean verifyFilterNotAvailable(String SDGName, String Filter) {

		boolean flag = false;
		if (click(driver, gtFilterButton(SDGName, 20), "Filter Button on SDG: " + SDGName, action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Filter Button on SDG: " + SDGName, YesNo.No);
			List<WebElement> filterLabelsList = getLabelsForFilters(SDGName);
			List<String> filterLabelsListText = filterLabelsList.stream().map(s -> s.getText())
					.collect(Collectors.toList()).stream().map(t -> t.trim()).collect(Collectors.toList());
			if (!filterLabelsListText.contains(Filter)) {
				log(LogStatus.INFO, "List Of Filters " + filterLabelsListText, YesNo.No);
				log(LogStatus.INFO, "Filter: " + Filter + " is not avaialble on Filter Grid of SDG: " + SDGName,
						YesNo.No);
				flag = true;
			} else {
				log(LogStatus.ERROR, "Filter: " + Filter + " is avaialble on Filter Grid of SDG: " + SDGName,
						YesNo.Yes);
				sa.assertTrue(false, "Filter: " + Filter + " is avaialble on Filter Grid of SDG: " + SDGName);
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on Filter Button on SDG: " + SDGName, YesNo.Yes);
			sa.assertTrue(false, "Not able to click on Filter Button on SDG: " + SDGName);
		}

		return flag;

	}

	/**
	 * @author Ankur Huria
	 * @param SDGName
	 * @param datas
	 */
	public boolean VerifyMultipicklistFilterInSDG(String SDGName, String[][] datas,
			List<String> expectedPickListOptionValues, List<String> expectedDefaultPickListOptionSelected) {

		boolean flag = false;
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		String pageSize = "100";

		List<WebElement> filterLabelsList = getLabelsForFilters(SDGName);
		List<String> filterLabelsListText = filterLabelsList.stream().map(s -> s.getText()).collect(Collectors.toList())
				.stream().map(t -> t.trim()).collect(Collectors.toList());
		if (filterLabelsListText.contains("Custom Mpick_list")) {
			log(LogStatus.INFO, "--------Filter available: Custom Mpick_list --------", YesNo.No);

			List<String> actualPickListOptionValues = optionsOfCustomMPicklist().stream().map(s -> s.getText())
					.collect(Collectors.toList());

			if (actualPickListOptionValues.equals(expectedPickListOptionValues)) {
				log(LogStatus.INFO,
						"--------Custom MPickList Contains options: " + actualPickListOptionValues + "--------",
						YesNo.No);
				flag = true;
			} else {
				log(LogStatus.ERROR,
						"--------Custom MPickList not Contains options: " + actualPickListOptionValues + "--------",
						YesNo.Yes);
				sa.assertTrue(false,
						"--------Custom MPickList not Contains options: " + actualPickListOptionValues + "--------");
			}

			List<String> actualSelectedOptionsOfCustomMPicklist = selectedOptionsOfCustomMPicklist().stream()
					.map(s -> s.getText()).collect(Collectors.toList());

			if (actualSelectedOptionsOfCustomMPicklist.equals(expectedDefaultPickListOptionSelected)) {
				log(LogStatus.INFO, "--------Custom MPickList Default Value: " + actualSelectedOptionsOfCustomMPicklist
						+ "--------", YesNo.No);
				flag = true;
			} else {
				log(LogStatus.ERROR,
						"--------Custom MPickList Default Value not Matched, Actual: "
								+ actualSelectedOptionsOfCustomMPicklist + ", Expected: "
								+ expectedDefaultPickListOptionSelected + "--------",
						YesNo.Yes);
				sa.assertTrue(false,
						"--------Custom MPickList Default Value not Matched, Actual: "
								+ actualSelectedOptionsOfCustomMPicklist + ", Expected: "
								+ expectedDefaultPickListOptionSelected + "--------");
			}

			for (String[] data : datas) {
				if (CommonLib.selectVisibleTextFromDropDown(driver, selectTagCustomMPicklist(25),
						"Custom M PickList Select DropDown", data[2])) {
					log(LogStatus.INFO, "Select the Value From DropDown: " + data[2], YesNo.No);
					CommonLib.ThreadSleep(20000);
					if (sendKeysAndPressEnter(driver, inputBoxCustomMPickList(20), data[0],
							"Custom MPicklist Input Box", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Pass the Value to Input Box: " + data[0], YesNo.No);
						CommonLib.ThreadSleep(30000);
						int numberOfRecords = numberOfRecords(SDGName, pageSize);
						if (numberOfRecords == Integer.parseInt(data[1])) {
							log(LogStatus.INFO, "--------Number of Records Matched for " + data[0]
									+ " in CustomMPicklist: " + data[1] + " for Operator: " + data[2] + "--------",
									YesNo.No);
							flag = true;
						} else {
							log(LogStatus.ERROR,
									"--------Number of Records Not Matched for " + data[0]
											+ " in CustomMPicklist, Expected: " + data[1] + " ,but Actual: "
											+ numberOfRecords + " for Operator: " + data[2] + "--------",
									YesNo.Yes);
							sa.assertTrue(false,
									"--------Number of Records Not Matched for " + data[0]
											+ " in CustomMPicklist, Expected: " + data[1] + " ,but Actual: "
											+ numberOfRecords + " for Operator: " + data[2] + "--------");
						}

					} else {
						log(LogStatus.ERROR, "--------Not able to Pass the Value to Input Box: " + data[0] + "--------",
								YesNo.Yes);
						sa.assertTrue(false,
								"--------Not able to Pass the Value to Input Box: " + data[0] + "--------");
					}
				} else {
					log(LogStatus.ERROR, "--------Not able to Select the Value From DropDown: " + data[2] + "--------",
							YesNo.Yes);
					sa.assertTrue(false, "--------Not able to Select the Value From DropDown: " + data[2] + "--------");
				}

			}

		} else {
			log(LogStatus.ERROR, "--------Filter Not available: Custom Mpick_list--------", YesNo.Yes);
			sa.assertTrue(false, "--------Filter Not available: Custom Mpick_list--------");
		}

		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param SDGName
	 * @param datas
	 */
	public boolean VerifySDGFilterSelectAllValues(String SDGName, String FilterLabel,
			List<String> expectedPickListOptionValues) {

		boolean flag = false;

		if (CommonLib.isElementPresent(SDGFilterSelectElement(FilterLabel, 20))) {
			log(LogStatus.INFO, "SDG Select Filter Available: " + FilterLabel, YesNo.No);
			if (click(driver, gtFilterButton(SDGName, 20), "Filter Button on SDG: " + SDGName,
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Filter Button on SDG: " + SDGName, YesNo.No);
				if (CommonLib.isElementPresent(SDGFilterSelectElement(FilterLabel, 20))) {
					log(LogStatus.INFO, "SDG Select Filter Available: " + FilterLabel, YesNo.No);

					List<WebElement> filterLabelsList = getLabelsForFilters(SDGName);
					List<String> filterLabelsListText = filterLabelsList.stream().map(s -> s.getText())
							.collect(Collectors.toList()).stream().map(t -> t.trim()).collect(Collectors.toList());
					if (filterLabelsListText.contains("Custom Mpick_list")) {
						log(LogStatus.INFO, "--------Filter available: Custom Mpick_list --------", YesNo.No);

						List<String> actualPickListOptionValues = optionsOfCustomMPicklist().stream()
								.map(s -> s.getText()).collect(Collectors.toList());

						if (actualPickListOptionValues.equals(expectedPickListOptionValues)) {
							log(LogStatus.INFO, "--------Custom MPickList Contains options: "
									+ actualPickListOptionValues + "--------", YesNo.No);
							flag = true;
						} else {
							log(LogStatus.ERROR, "--------Custom MPickList not Contains options: "
									+ actualPickListOptionValues + "--------", YesNo.Yes);
							sa.assertTrue(false, "--------Custom MPickList not Contains options: "
									+ actualPickListOptionValues + "--------");
						}

					} else {
						log(LogStatus.ERROR, "--------Filter Not available: Custom Mpick_list--------", YesNo.Yes);
						sa.assertTrue(false, "--------Filter Not available: Custom Mpick_list--------");
					}

				} else {
					log(LogStatus.ERROR, "SDG Select Not Filter Available: " + FilterLabel, YesNo.Yes);
					sa.assertTrue(false, "SDG Select Not Filter Available: " + FilterLabel);
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on Filter Button on SDG: " + SDGName, YesNo.Yes);
				sa.assertTrue(false, "Not able to click on Filter Button on SDG: " + SDGName);
			}
		} else {
			log(LogStatus.ERROR, "SDG Select Not Filter Available: " + FilterLabel, YesNo.Yes);
		}

		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param SDGName
	 * @param datas
	 */
	@SuppressWarnings("unlikely-arg-type")
	public boolean VerifySDGFilterSelectDefaultSelectedValue(String SDGName, String FilterLabel,
			List<String> expectedDefaultPickListOptionSelected) {

		boolean flag = false;

		List<WebElement> filterLabelsList = getLabelsForFilters(SDGName);
		List<String> filterLabelsListText = filterLabelsList.stream().map(s -> s.getText()).collect(Collectors.toList())
				.stream().map(t -> t.trim()).collect(Collectors.toList());
		if (filterLabelsListText.contains(FilterLabel)) {
			log(LogStatus.INFO, "--------Filter available: " + FilterLabel + " --------", YesNo.No);

			String actualSelectedOption = CommonLib.getSelectedOptionOfDropDown(driver,
					selectTagOfFilterInSDG(FilterLabel, 30), "selectTagOfFilterInSDG", "text");
			if (actualSelectedOption.equals(expectedDefaultPickListOptionSelected.get(0))) {
				log(LogStatus.INFO, "--------" + FilterLabel + " Default Value: " + actualSelectedOption + "--------",
						YesNo.No);
				flag = true;
			} else {
				log(LogStatus.ERROR, "--------" + FilterLabel + " Default Value not Matched, Actual: "
						+ actualSelectedOption + ", Expected: " + expectedDefaultPickListOptionSelected + "--------",
						YesNo.Yes);
				sa.assertTrue(false, "--------" + FilterLabel + " Default Valuenot Matched, Actual: "
						+ actualSelectedOption + ", Expected: " + expectedDefaultPickListOptionSelected + "--------");
			}
		} else {
			log(LogStatus.ERROR, "--------Filter Not available: " + FilterLabel + "--------", YesNo.Yes);
			sa.assertTrue(false, "--------Filter Not available: " + FilterLabel + "--------");
		}

		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param SDGName
	 * @param datas
	 */
	public boolean VerifySelectOptionInFilter(String SDGName, String FilterLabel, String FilterValue) {

		boolean flag = false;

		if (CommonLib.selectVisibleTextFromDropDown(driver, SDGFilterSelectElement(FilterLabel, 20),
				"Select DropDown Value: " + FilterLabel, FilterValue)) {
			log(LogStatus.INFO, "Select the Value From DropDown: " + FilterValue, YesNo.No);
			flag = true;
			CommonLib.ThreadSleep(20000);
		} else {
			log(LogStatus.ERROR, "--------Not able to Select the Value From DropDown: " + FilterValue + "--------",
					YesNo.Yes);
			sa.assertTrue(false, "--------Not able to Select the Value From DropDown: " + FilterValue + "--------");
		}

		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param SDGName
	 * @param datas
	 */
	public boolean VerifyMultipicklistFilterSelectAndValues(String SDGName, String[][] datas) {

		boolean flag = false;
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		String pageSize = "100";

		List<WebElement> filterLabelsList = getLabelsForFilters(SDGName);
		List<String> filterLabelsListText = filterLabelsList.stream().map(s -> s.getText()).collect(Collectors.toList())
				.stream().map(t -> t.trim()).collect(Collectors.toList());
		if (filterLabelsListText.contains("Custom Mpick_list")) {
			log(LogStatus.INFO, "--------Filter available: Custom Mpick_list --------", YesNo.No);

			for (String[] data : datas) {
				if (CommonLib.selectVisibleTextFromDropDown(driver, selectTagCustomMPicklist(25),
						"Custom M PickList Select DropDown", data[2])) {
					log(LogStatus.INFO, "Select the Value From DropDown: " + data[2], YesNo.No);
					CommonLib.ThreadSleep(20000);
					if (sendKeysAndPressEnter(driver, inputBoxCustomMPickList(20), data[0],
							"Custom MPicklist Input Box", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Pass the Value to Input Box: " + data[0], YesNo.No);
						CommonLib.ThreadSleep(30000);
						int numberOfRecords = numberOfRecords(SDGName, pageSize);
						if (numberOfRecords == Integer.parseInt(data[1])) {
							log(LogStatus.INFO, "--------Number of Records Matched for " + data[0]
									+ " in CustomMPicklist: " + data[1] + " for Operator: " + data[2] + "--------",
									YesNo.No);
							flag = true;
						} else {
							log(LogStatus.ERROR,
									"--------Number of Records Not Matched for " + data[0]
											+ " in CustomMPicklist, Expected: " + data[1] + " ,but Actual: "
											+ numberOfRecords + " for Operator: " + data[2] + "--------",
									YesNo.Yes);
							sa.assertTrue(false,
									"--------Number of Records Not Matched for " + data[0]
											+ " in CustomMPicklist, Expected: " + data[1] + " ,but Actual: "
											+ numberOfRecords + " for Operator: " + data[2] + "--------");
						}

					} else {
						log(LogStatus.ERROR, "--------Not able to Pass the Value to Input Box: " + data[0] + "--------",
								YesNo.Yes);
						sa.assertTrue(false,
								"--------Not able to Pass the Value to Input Box: " + data[0] + "--------");
					}
				} else {
					log(LogStatus.ERROR, "--------Not able to Select the Value From DropDown: " + data[2] + "--------",
							YesNo.Yes);
					sa.assertTrue(false, "--------Not able to Select the Value From DropDown: " + data[2] + "--------");
				}

			}

		} else {
			log(LogStatus.ERROR, "--------Filter Not available: Custom Mpick_list--------", YesNo.Yes);
			sa.assertTrue(false, "--------Filter Not available: Custom Mpick_list--------");
		}

		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param SDGName
	 * @param datas
	 */
	public String ClickOnOpenSDGRecordAndSwitchToNewWindow(String SDGName) {

		String parentid = null;
		if (gearIcon(SDGName) != null) {
			log(LogStatus.INFO, "SDG Gear Icon Present on SDG: " + SDGName, YesNo.No);
			if (click(driver, gearIcon(SDGName), SDGName + " open sdg record ", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.PASS, "Clicked on SDG Gear Icon of SDG: " + SDGName, YesNo.No);
				parentid = switchOnWindow(driver);
				if (parentid != null) {
					ThreadSleep(5000);
					WebElement sdgHeader = FindElement(driver, "//h1//*[text()='" + SDGName + "']",
							SDGName + " tag name xpath", action.BOOLEAN, 10);
					if (sdgHeader != null) {
						log(LogStatus.PASS, SDGName + " tag name is displaying ", YesNo.No);
						return parentid;

					} else {
						log(LogStatus.FAIL, SDGName + " tag name is not displaying ", YesNo.No);
						sa.assertTrue(false, SDGName + " tag name is not displaying ");
					}
				} else {
					log(LogStatus.FAIL, "Not able to switch on open sdg record window of " + SDGName, YesNo.Yes);
					sa.assertTrue(false, "Not able to switch on open sdg record window of " + SDGName);
				}
			} else {
				log(LogStatus.FAIL, "Not Able to Click on SDG Gear Icon of SDG: " + SDGName, YesNo.Yes);
				sa.assertTrue(false, "Not Able to Click on SDG Gear Icon of SDG: " + SDGName);
			}
		} else {
			log(LogStatus.FAIL, "SDG Gear Icon not Present on SDG: " + SDGName, YesNo.Yes);
			sa.assertTrue(false, "SDG Gear Icon not Present on SDG: " + SDGName);
		}

		return parentid;
	}

	/**
	 * @author Ankur Huria
	 * @param SDGGridName
	 */

	public boolean verifyColumnAscendingDescendingOrderShouldNotWork(String sdgGridName, List<String> columnNames) {

		List<WebElement> headerList = sdgGridAllHeadersLabelNameList(sdgGridName);
		List<String> columnDataText = headerList.stream().map(s -> s.getText()).collect(Collectors.toList()).stream()
				.map(t -> t.trim()).collect(Collectors.toList());
		boolean flag = false;
		if (!headerList.isEmpty()) {
			for (String columnName : columnNames) {
				int columnIndex = columnDataText.indexOf(columnName);

				List<WebElement> ListOfDataBeforeHeaderClick = sdgGridColumnsDataList(sdgGridName, columnIndex + 1);

				if (clickUsingJavaScript(driver, headerList.get(columnIndex),
						sdgGridName.toString() + " SDG Grid header column " + columnName, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.PASS,
							"Clicked on Header" + columnName + " Column " + (columnIndex + 1) + " for Sorting Check",
							YesNo.No);
					ThreadSleep(15000);
					List<WebElement> ListOfDataAfterHeaderClick = sdgGridColumnsDataList(sdgGridName, columnIndex + 1);
					if (ListOfDataBeforeHeaderClick.equals(ListOfDataAfterHeaderClick)) {
						log(LogStatus.PASS, "Sorting of Column is not Working for Column: " + columnName, YesNo.No);
						flag = true;
					} else {
						log(LogStatus.FAIL, "Sorting of Column is Working for Column: " + columnName, YesNo.No);
						sa.assertTrue(false, "Sorting of Column is Working for Column: " + columnName);
					}
				} else {
					log(LogStatus.PASS, "Not able to click on " + sdgGridName.toString() + " SDG Grid header "
							+ columnName + " so cannot check Sorting ", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + sdgGridName.toString() + " SDG Grid header "
							+ columnName + " so cannot check Sorting ");
				}

			}

		} else {
			log(LogStatus.PASS,
					sdgGridName.toString() + " SDG Grid header cloumns list is not visible so cannot check Sorting ",
					YesNo.Yes);
			sa.assertTrue(false,
					sdgGridName.toString() + " SDG Grid header cloumns list is not visible so cannot check Sorting ");
		}
		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param SDGName
	 * @param datas
	 */
	public boolean VerifyMultipicklistFilterSelectAndCommaValuesError(String SDGName, String[][] datas,
			String FilterLabelName) {

		boolean flag = false;

		List<WebElement> filterLabelsList = getLabelsForFilters(SDGName);
		List<String> filterLabelsListText = filterLabelsList.stream().map(s -> s.getText()).collect(Collectors.toList())
				.stream().map(t -> t.trim()).collect(Collectors.toList());
		if (filterLabelsListText.contains(FilterLabelName)) {
			log(LogStatus.INFO, "--------Filter available: Custom Mpick_list --------", YesNo.No);

			for (String[] data : datas) {
				if (CommonLib.selectVisibleTextFromDropDown(driver, selectTagCustomMPicklist(25),
						"Custom M PickList Select DropDown", data[2])) {
					log(LogStatus.INFO, "Select the Value From DropDown: " + data[2], YesNo.No);
					CommonLib.ThreadSleep(20000);
					if (sendKeysAndPressEnter(driver, inputBoxCustomMPickList(20), data[0],
							"Custom MPicklist Input Box", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Pass the Value to Input Box: " + data[0], YesNo.No);
						CommonLib.ThreadSleep(2000);
						if (SDGFilterValueErrorElement(FilterLabelName, 25).getText().equals(data[1])) {
							log(LogStatus.INFO,
									"--------Error Message Found for Values:  " + data[0] + " in " + FilterLabelName
											+ " for Operator: " + data[2] + " ,ErrorMessage: " + data[1] + "--------",
									YesNo.No);
							flag = true;
						} else {
							log(LogStatus.ERROR,
									"--------Error Message Not Found for Values:  " + data[0] + " in " + FilterLabelName
											+ " for Operator: " + data[2] + " ,ErrorMessage: " + data[1] + "--------",
									YesNo.Yes);
							sa.assertTrue(false,
									"--------Error Message Not Found for Values:  " + data[0] + " in " + FilterLabelName
											+ " for Operator: " + data[2] + " ,ErrorMessage: " + data[1] + "--------");
						}

					} else {
						log(LogStatus.ERROR, "--------Not able to Pass the Value to Input Box: " + data[0] + "--------",
								YesNo.Yes);
						sa.assertTrue(false,
								"--------Not able to Pass the Value to Input Box: " + data[0] + "--------");
					}
				} else {
					log(LogStatus.ERROR, "--------Not able to Select the Value From DropDown: " + data[2] + "--------",
							YesNo.Yes);
					sa.assertTrue(false, "--------Not able to Select the Value From DropDown: " + data[2] + "--------");
				}

			}

		} else {
			log(LogStatus.ERROR, "--------Filter Not available: Custom Mpick_list--------", YesNo.Yes);
			sa.assertTrue(false, "--------Filter Not available: Custom Mpick_list--------");
		}

		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param SDGName
	 * @param datas
	 */
	public boolean ClickOnOpenSDGRecord(String SDGName) {

		boolean flag = false;
		if (gearIcon(SDGName) != null) {
			log(LogStatus.INFO, "SDG Gear Icon Present on SDG: " + SDGName, YesNo.No);
			if (click(driver, gearIcon(SDGName), SDGName + " open sdg record ", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.PASS, "Clicked on SDG Gear Icon of SDG: " + SDGName, YesNo.No);

				flag = true;
			} else {
				log(LogStatus.FAIL, "Not Able to Click on SDG Gear Icon of SDG: " + SDGName, YesNo.Yes);
				sa.assertTrue(false, "Not Able to Click on SDG Gear Icon of SDG: " + SDGName);
			}
		} else {
			log(LogStatus.FAIL, "SDG Gear Icon not Present on SDG: " + SDGName, YesNo.Yes);
			sa.assertTrue(false, "SDG Gear Icon not Present on SDG: " + SDGName);
		}

		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param SDGName
	 * @param datas
	 */
	public String SwitchToSDGWindow(String SDGName) {

		String parentid = null;
		parentid = switchOnWindow(driver);
		if (parentid != null) {
			ThreadSleep(5000);
			WebElement sdgHeader = FindElement(driver, "//h1//*[text()='" + SDGName + "']", SDGName + " tag name xpath",
					action.BOOLEAN, 10);
			if (sdgHeader != null) {
				log(LogStatus.PASS, SDGName + " tag name is displaying ", YesNo.No);
				return parentid;

			} else {
				log(LogStatus.FAIL, SDGName + " tag name is not displaying ", YesNo.No);
				sa.assertTrue(false, SDGName + " tag name is not displaying ");
			}
		} else {
			log(LogStatus.FAIL, "Not able to switch on open sdg record window of " + SDGName, YesNo.Yes);
			sa.assertTrue(false, "Not able to switch on open sdg record window of " + SDGName);
		}

		return parentid;
	}

	/**
	 * @author Ankur Huria
	 * @param SDGName
	 * @param datas
	 */
	public String SwitchToWindow() {

		String parentid = null;
		parentid = switchOnWindow(driver);
		if (parentid != null) {

			log(LogStatus.PASS, "Successfully Switched to Child Window", YesNo.No);
			return parentid;

		} else {
			log(LogStatus.FAIL, "Not Able to Switch to Child Window", YesNo.Yes);
			sa.assertTrue(false, "Not Able to Switch to Child Window");
		}

		return parentid;
	}

	/**
	 * @author Ankur Huria
	 * @param SDGName
	 * @param datas
	 */
	public boolean actionButtonAlreadyAddedOrNotOnSDG(String sdgName, String[] actions, String[] values, int timeOut,
			String pageSize) {
		LightningAppBuilderPageBusinessLayer AppBuilder = new LightningAppBuilderPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		List<String> actionsList = Arrays.asList(actions);
		List<String> valuesList = Arrays.asList(values);
		boolean nameFlag = false;
		int loopCount = 0;
		int status = 0;
		if (actionsList.size() == valuesList.size()) {
			log(LogStatus.INFO,
					"------------No. of times Actions and Values Size Matched, So Going for Further Process----------------",
					YesNo.No);
			int i = 0;

			for (String actionList : actionsList)

			{
				String[] labels = actionList.split("<Break>");
				String[] value = valuesList.get(i).split("<Break>");
				List<String> labelsList = Arrays.asList(labels);
				List<String> valueList = Arrays.asList(value);
				if (labelsList.size() == valueList.size()) {
					log(LogStatus.INFO,
							"------------Actions and Values Size Matched, So Going for Further Process----------------",
							YesNo.No);

					int k = 0;
					for (String label : labels) {
						if (label.equalsIgnoreCase("Action_Type")) {

							break;

						}
						k++;
					}
					int j = 0;
					for (String label : labels) {
						if (label.equalsIgnoreCase("Name")) {
							nameFlag = true;
							break;

						}
						j++;
					}
					if (nameFlag) {

						if (value[k].equalsIgnoreCase("List Button")) {
							if (listButtonOnSDG(sdgName, value[j], timeOut) != null) {
								log(LogStatus.ERROR, "------------Action List Button: " + value[j]
										+ " Already Added to SDG Grid " + sdgName + "----------------", YesNo.Yes);
								sa.assertTrue(false, "------------Action List Button: " + value[j]
										+ " Already Added to SDG Grid " + sdgName + "----------------");
								status++;

							} else {
								log(LogStatus.INFO,
										"------------Action List Button: " + value[j]
												+ " Not Already Added to SDG Grid " + sdgName + "----------------",
										YesNo.No);
								appLog.info("------------Action List Button: " + value[j]
										+ " Not Already Added to SDG Grid " + sdgName + "----------------");

							}
						}

						else if (value[k].equalsIgnoreCase("Row Button")) {
							int rowCountAfterFilter = numberOfRecords(sdgName, pageSize);
							if (home.rowButtonsInSDGGrid(sdgName, value[j]).size() == rowCountAfterFilter) {
								log(LogStatus.ERROR,
										"------------Action Row Buttons: " + value[j]
												+ " are already added to SDG Grid " + sdgName
												+ " So, Test Case Going to fail----------------",
										YesNo.Yes);
								sa.assertTrue(false,
										"------------Action Row Buttons: " + value[j]
												+ " are already added to SDG Grid " + sdgName
												+ " So, Test Case Going to fail----------------");
								status++;

							} else {
								log(LogStatus.INFO,
										"------------Action Row Buttons: " + value[j] + " are not showing to SDG Grid "
												+ sdgName + "Expected: " + rowCountAfterFilter + " ,but Actual: "
												+ home.rowButtonsInSDGGrid(sdgName, value[j]).size()
												+ "----------------",
										YesNo.No);
								appLog.info("------------Action Row Buttons: " + value[j]
										+ " are not showing to SDG Grid " + sdgName + "Expected: " + rowCountAfterFilter
										+ " ,but Actual: " + home.rowButtonsInSDGGrid(sdgName, value[j]).size()
										+ "----------------");
								;

							}
						}

					} else {
						log(LogStatus.ERROR,
								"------------Name Label not added in Data, So Test Case Going to fail----------------",
								YesNo.No);
						sa.assertTrue(false,
								"------------Name Label not added in Data, So Test Case Going to fail----------------");
					}
					i++;
				} else {
					log(LogStatus.ERROR,
							"------------Actions and Values Size not Matched, So Test Case Going to Fail----------------",
							YesNo.No);
					sa.assertTrue(false,
							"------------Actions and Values Size not Matched, So Test Case Going to Fail---------------");

				}
				loopCount++;
			}

		}

		else {
			log(LogStatus.ERROR,
					"------------No. of times Actions and Values Size not Matched, So Test Case Going to Fail----------------",
					YesNo.No);
			sa.assertTrue(false,
					"------------No. of Times Actions and Values Size not Matched, So Test Case Going to Fail---------------");

		}

		if (loopCount == status)
			return false;
		else
			return true;
	}

	/**
	 * @author Ankur Huria
	 * @param SDGName
	 * @param datas
	 */
	public boolean createFundThroughSDG(String projectName, String sdgName, String actionButtonName,
			String[][] sdgLabels, int timeOut) {
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);

		boolean flag = false;
		if (click(driver, listButtonOnSDG(sdgName, actionButtonName, timeOut), "Action Button " + actionButtonName,
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Button: " + actionButtonName + " on SDG: " + sdgName, YesNo.No);
			if (enterValueAndDropDownForSDGForm(projectName, sdgLabels, action.SCROLLANDBOOLEAN, timeOut)) {
				log(LogStatus.INFO, "Successfully Enter values on " + actionButtonName + " of SDG: " + sdgName,
						YesNo.Yes);

				if (click(driver, getRecordPageSettingSave(60), "Save Button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Click on Save Button  " + sdgName, YesNo.No);
					ThreadSleep(5000);
					flag = true;
				} else {
					sa.assertTrue(false,
							"Not Able to Click on Save Button Value so cannot create Fund on SDG:  " + sdgName);
					log(LogStatus.SKIP,
							"Not Able to Click on Save Button Value so cannot create Fund on SDG:  " + sdgName,
							YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Enter values on " + actionButtonName + " of SDG: " + sdgName,
						YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on Button: " + actionButtonName + " on SDG: " + sdgName, YesNo.Yes);

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
	public boolean enterValueAndDropDownForSDGForm(String projectName, String[][] labelWithValues, action action,
			int timeOut) {
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		boolean flag = false;
		String label = "";
		String value = "";
		int status = 0;
		WebElement ele = null;
		int loopCount = 0;
		for (String[] labelValues : labelWithValues) {
			label = labelValues[0].replace("_", " ");
			value = labelValues[1];
			String xpath = "//lightning-base-combobox-item/span/span";
			if (label.equalsIgnoreCase("Fund Type") || label.equalsIgnoreCase("Investment Category")) {
				CommonLib.ThreadSleep(3000);
				if (dropDownHandle(driver, sdg.sdgActionAndFieldDropDownButton(label, 20), xpath, "DropDown " + label,
						value)) {
					log(LogStatus.INFO, "Successfully Select the value:  " + value + " from " + label, YesNo.Yes);
					status++;

				} else {
					log(LogStatus.SKIP, "Not Successfully Select the value:  " + value + " from " + label, YesNo.Yes);
					sa.assertTrue(false, "Not Successfully Select the value:  " + value + " from " + label);
				}

			} else {
				ele = getLabelTextBox(projectName, PageName.HomePage.toString(), label, 10);
				if (ele != null) {
					if (sendKeys(driver, ele, value, label, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "successfully entered " + value + " in " + label, YesNo.Yes);
						status++;

					} else {
						log(LogStatus.SKIP, "could not enter " + value + " in " + label, YesNo.Yes);
						sa.assertTrue(false, "could not enter " + value + " in " + label);

					}
				} else {
					ele = sdg.sdgActionAndFieldTextArea(label, 10);
					if (sendKeys(driver, ele, value, label, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "successfully entered " + value + " in " + label, YesNo.Yes);
						status++;

					} else {
						log(LogStatus.SKIP, "could not enter " + value + " in " + label, YesNo.Yes);
						sa.assertTrue(false, "could not enter " + value + " in " + label);

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
	 * @param SDGName
	 * @param datas
	 */
	public boolean editFundThroughSDG(String projectName, String sdgName, String actionButtonName, String[][] sdgLabels,
			int timeOut, String beforeUpdateFundName, String pageSize) {
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);

		boolean flag = false;
		if (pageSizeSelect(sdgName, pageSize)) {
			log(LogStatus.PASS, "-----------Page Size has selected to" + pageSize + " --------------", YesNo.No);
			if (rowButtonCorrespondingToSDG(sdgName, beforeUpdateFundName, actionButtonName, timeOut) != null) {
				log(LogStatus.INFO, "Record Found: " + beforeUpdateFundName + " in SDG: " + sdgName, YesNo.Yes);
				if (click(driver, rowButtonCorrespondingToSDG(sdgName, beforeUpdateFundName, actionButtonName, timeOut),
						"Action Button " + actionButtonName, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Button: " + actionButtonName + " on SDG: " + sdgName, YesNo.No);
					if (enterValueAndDropDownForSDGForm(projectName, sdgLabels, action.SCROLLANDBOOLEAN, timeOut)) {
						log(LogStatus.INFO, "Successfully Enter values on " + actionButtonName + " of SDG: " + sdgName,
								YesNo.Yes);

						if (click(driver, getRecordPageSettingSave(60), "Save Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Click on Save Button  " + sdgName, YesNo.No);
							ThreadSleep(5000);
							flag = true;
						} else {
							sa.assertTrue(false,
									"Not Able to Click on Save Button Value so cannot create Fund on SDG:  " + sdgName);
							log(LogStatus.SKIP,
									"Not Able to Click on Save Button Value so cannot create Fund on SDG:  " + sdgName,
									YesNo.Yes);
						}
					} else {
						log(LogStatus.ERROR, "Not Able to Enter values on " + actionButtonName + " of SDG: " + sdgName,
								YesNo.Yes);
						sa.assertTrue(false, "Not Able to Enter values on " + actionButtonName + " of SDG: " + sdgName);
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on Button: " + actionButtonName + " on SDG: " + sdgName,
							YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Button: " + actionButtonName + " on SDG: " + sdgName);

				}

			} else {
				log(LogStatus.ERROR, "Record Not Found: " + beforeUpdateFundName + " in SDG: " + sdgName, YesNo.Yes);
				sa.assertTrue(false, "Record Not Found: " + beforeUpdateFundName + " in SDG: " + sdgName);

			}
		} else {
			log(LogStatus.FAIL, "-----------Not able to Select Page Size: " + pageSize + "--------------", YesNo.No);
			sa.assertTrue(false, "-----------Not able to Select Page Size: " + pageSize + " --------------");

		}
		return flag;

	}

	/**
	 * @author Ankur Huria
	 * @param SDGName
	 * @param datas
	 */
	public boolean deleteFundThroughSDG(String projectName, String sdgName, String actionButtonName, int timeOut,
			String afterUpdateFundName, String pageSize) {
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);

		boolean flag = false;
		if (pageSizeSelect(sdgName, pageSize)) {
			log(LogStatus.PASS, "-----------Page Size has selected to" + pageSize + " --------------", YesNo.No);
			if (rowButtonCorrespondingToSDG(sdgName, afterUpdateFundName, actionButtonName, timeOut) != null) {
				log(LogStatus.INFO, "Record Found: " + afterUpdateFundName + " in SDG: " + sdgName, YesNo.Yes);
				if (click(driver, rowButtonCorrespondingToSDG(sdgName, afterUpdateFundName, actionButtonName, timeOut),
						"Action Button " + actionButtonName, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Button: " + actionButtonName + " on SDG: " + sdgName, YesNo.No);

					if (click(driver, deleteRecordConfirmBtn(sdgName),
							"Delete Confirm Button for " + afterUpdateFundName, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Delete Confirm Button for " + afterUpdateFundName, YesNo.No);
						ThreadSleep(5000);
						flag = true;
					} else {
						sa.assertTrue(false, "Not Able to Click on Delete Confirm Button for " + afterUpdateFundName);
						log(LogStatus.SKIP, "Not Able to Click on Delete Confirm Button for " + afterUpdateFundName,
								YesNo.Yes);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Button: " + actionButtonName + " on SDG: " + sdgName,
							YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Button: " + actionButtonName + " on SDG: " + sdgName);

				}

			} else {
				log(LogStatus.ERROR, "Record Not Found: " + afterUpdateFundName + " in SDG: " + sdgName, YesNo.Yes);
				sa.assertTrue(false, "Record Not Found: " + afterUpdateFundName + " in SDG: " + sdgName);

			}
		} else {
			log(LogStatus.FAIL, "-----------Not able to Select Page Size: " + pageSize + "--------------", YesNo.No);
			sa.assertTrue(false, "-----------Not able to Select Page Size: " + pageSize + " --------------");

		}
		return flag;

	}

	/**
	 * @author Ankur Huria
	 * @param SDGName
	 * @param datas
	 */
	public boolean sdgFilterSendDataAndDropDownHandleAndVerifyErrorMsg(String SDGName, String[][] datas) {

		boolean flag = false;

		List<WebElement> filterLabelsList = getLabelsForFilters(SDGName);
		List<String> filterLabelsListText = filterLabelsList.stream().map(s -> s.getText()).collect(Collectors.toList())
				.stream().map(t -> t.trim()).collect(Collectors.toList());

		for (String[] data : datas) {
			if (filterLabelsListText.contains(data[3])) {
				log(LogStatus.INFO, "--------Filter available: " + data[3] + " --------", YesNo.No);
				if (CommonLib.selectVisibleTextFromDropDown(driver, selectTagForSDGFilterName(data[3], 25),
						data[3] + " Select DropDown", data[2])) {
					log(LogStatus.INFO, "Select the Value From DropDown: " + data[2], YesNo.No);
					CommonLib.ThreadSleep(20000);
					if (sendKeysAndPressEnter(driver, inputBoxForSDGFilterName(data[3], 20), data[0],
							data[3] + " Input Box", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Pass the Value to Input Box: " + data[0], YesNo.No);
						CommonLib.ThreadSleep(2000);

						if (SDGErrorHeader(SDGName, 30).getText().equals(data[1])) {
							log(LogStatus.PASS,
									"-----------Error Msg Verified of SDG: " + SDGName + "Expected: " + data[1]
											+ " , Actual: " + SDGErrorHeader(SDGName, 30).getText() + "--------------",
									YesNo.No);
							flag = true;
						}

						else {
							log(LogStatus.FAIL,
									"-----------Error Msg Not Verified of SDG: " + SDGName + "Expected: " + data[1]
											+ " , Actual: " + SDGErrorHeader(SDGName, 30).getText() + "--------------",
									YesNo.No);
							sa.assertTrue(false,
									"-----------Error Msg Not Verified of SDG: " + SDGName + "Expected: " + data[1]
											+ " , Actual: " + SDGErrorHeader(SDGName, 30).getText() + "--------------");

						}

					} else {
						log(LogStatus.ERROR, "--------Not able to Pass the Value to Input Box: " + data[0] + "--------",
								YesNo.Yes);
						sa.assertTrue(false,
								"--------Not able to Pass the Value to Input Box: " + data[0] + "--------");
					}
				} else {
					log(LogStatus.ERROR, "--------Not able to Select the Value From DropDown: " + data[2] + "--------",
							YesNo.Yes);
					sa.assertTrue(false, "--------Not able to Select the Value From DropDown: " + data[2] + "--------");
				}
			} else {
				log(LogStatus.ERROR, "--------Filter Not available: " + data[3] + "--------", YesNo.Yes);
				sa.assertTrue(false, "--------Filter Not available: " + data[3] + "--------");
			}

		}

		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param SDGName
	 * @param datas
	 */
	public boolean sdgFilterSendDataAndDropDownHandle(String SDGName, String[][] datas) {

		boolean flag = false;

		List<WebElement> filterLabelsList = getLabelsForFilters(SDGName);
		List<String> filterLabelsListText = filterLabelsList.stream().map(s -> s.getText()).collect(Collectors.toList())
				.stream().map(t -> t.trim()).collect(Collectors.toList());

		for (String[] data : datas) {
			if (filterLabelsListText.contains(data[3])) {
				log(LogStatus.INFO, "--------Filter available: " + data[3] + " --------", YesNo.No);
				if (CommonLib.selectVisibleTextFromDropDown(driver, selectTagForSDGFilterName(data[3], 25),
						data[3] + " Select DropDown", data[2])) {
					log(LogStatus.INFO, "Select the Value From DropDown: " + data[2], YesNo.No);
					CommonLib.ThreadSleep(20000);
					if (sendKeysAndPressEnter(driver, inputBoxForSDGFilterName(data[3], 20), data[0],
							data[3] + " Input Box", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Pass the Value to Input Box: " + data[0], YesNo.No);
						CommonLib.ThreadSleep(12000);

						flag = true;

					} else {
						log(LogStatus.ERROR, "--------Not able to Pass the Value to Input Box: " + data[0] + "--------",
								YesNo.Yes);
						sa.assertTrue(false,
								"--------Not able to Pass the Value to Input Box: " + data[0] + "--------");
					}
				} else {
					log(LogStatus.ERROR, "--------Not able to Select the Value From DropDown: " + data[2] + "--------",
							YesNo.Yes);
					sa.assertTrue(false, "--------Not able to Select the Value From DropDown: " + data[2] + "--------");
				}
			} else {
				log(LogStatus.ERROR, "--------Filter Not available: " + data[3] + "--------", YesNo.Yes);
				sa.assertTrue(false, "--------Filter Not available: " + data[3] + "--------");
			}

		}

		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param SDGName
	 * @param datas
	 */
	public boolean verifyBlankDataCorrespondingToBlankData(String SDGName, int Column1Index, int Column2Index) {

		int status = 0;
		int i = 0;
		List<WebElement> column1Data = columnDataOfSDG(SDGName, Column1Index);
		List<WebElement> column2Data = columnDataOfSDG(SDGName, Column2Index);
		List<String> column1DataText = column1Data.stream().map(s -> s.getText()).collect(Collectors.toList()).stream()
				.map(t -> t.trim()).collect(Collectors.toList());
		List<String> column2DataText = column2Data.stream().map(s -> s.getText()).collect(Collectors.toList()).stream()
				.map(t -> t.trim()).collect(Collectors.toList());

		if (column1DataText.size() == column2DataText.size()) {
			log(LogStatus.INFO, "--------Both Column Data Size Matched --------", YesNo.No);
			if (column1DataText.size() != 0 && column2DataText.size() != 0) {
				log(LogStatus.INFO, "No. of Records in both Columns are more than 0", YesNo.No);

				for (String col1DataText : column1DataText) {
					if (col1DataText.equals("")) {
						if (column2DataText.get(i).equals("")) {
							status++;
						} else {
							log(LogStatus.ERROR,
									"--------Record of Column 1 Blank, but Record of Column 2 is not Blank, Record1: "
											+ col1DataText + " ,Record2: " + column2DataText.get(i) + "--------",
									YesNo.Yes);
							sa.assertTrue(false,
									"--------Record of Column 1 Blank, but Record of Column 2 is not Blank, Record1: "
											+ col1DataText + " ,Record2: " + column2DataText.get(i) + "--------");
						}
					} else {

						if (!column2DataText.get(i).equals("")) {
							status++;
						} else {
							log(LogStatus.ERROR,
									"--------Record of Column 1 not Blank , but Record of Column 2 is Blank, Record1: "
											+ col1DataText + " ,Record2: " + column2DataText.get(i) + "--------",
									YesNo.Yes);
							sa.assertTrue(false,
									"--------Record of Column 1 not Blank, but Record of Column 2 is Blank, Record1: "
											+ col1DataText + " ,Record2: " + column2DataText.get(i) + "--------");
						}
					}

					i++;
				}

			} else {
				log(LogStatus.ERROR,
						"--------No. of Records in both Columns are not more than 0, So Cannot proceed further--------",
						YesNo.Yes);
				sa.assertTrue(false,
						"--------No. of Records in both Columns are not more than 0, So Cannot proceed further--------");
			}
		} else {
			log(LogStatus.ERROR, "--------Both Column Data Size Not Matched --------", YesNo.Yes);
			sa.assertTrue(false, "--------Both Column Data Size Not Matched --------");
		}

		if (status == i)
			return true;
		else
			return false;

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

	public boolean verifyPECloudOnHomePage() {
		boolean flag = false;
		if (checkElementVisibility(driver, getPECloudOnHomePage(50), "PE Cloud Text", 50)) {
			String text = CommonLib.getText(driver, getPECloudOnHomePage(50), "PE Cloud", action.BOOLEAN);
			if (text.equals("PE Cloud")) {
				log(LogStatus.INFO, "PE Cloud has been verified in the home page", YesNo.No);
				flag = true;
			} else {
				log(LogStatus.ERROR, "PE Cloud is not availabel in the home page", YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "PE Cloud is not visible in the home page", YesNo.Yes);
		}
		return flag;

	}

	public boolean VerifyOnlyPECloudOnLauncher() {
		String xPath = "";
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		boolean flag = false;
		if (click(driver, lp.getAppLuncherXpath(50), "App launcher icon", action.BOOLEAN)) {
			AppListeners.appLog.info(" click on app launcher icon");
			ThreadSleep(5000);
			xPath = "//div[@aria-label='Apps']//p";
			List<WebElement> AppListOnLauncher = CommonLib.FindElements(driver, xPath, "App list on Launcher");

			int size = AppListOnLauncher.size();
			String text = CommonLib.getText(driver, getPECloudOnLauncher(50), "Apps on App Launcher", action.BOOLEAN);

			if (size == 1 && text.equals("PE Cloud")) {
				log(LogStatus.INFO,
						"PE Could is appearing on the App Launcher page and No other Apps is Appearing on the App Launcher",
						YesNo.No);
				flag = true;
			} else {
				log(LogStatus.ERROR,
						"Either PE Could is not appearing on the App Launcher page Or other Apps are Appearing on the App Launcher",
						YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Could not click on the App Launcher Page", YesNo.Yes);

		}

		return flag;

	}

	public boolean VerifyOnlyPECloudOnViewAll() {
		String xPath = "";
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		boolean flag = false;
		if (click(driver, lp.getAppLuncherXpath(50), "App launcher icon", action.BOOLEAN)) {

			if (click(driver, getViewAllBtn(50), "View all button", action.BOOLEAN)) {
				AppListeners.appLog.info(" click on app launcher icon");
				ThreadSleep(5000);
				xPath = "//div[@class='slds-accordion__content']//one-app-launcher-app-tile//p[@class='slds-truncate']";
				List<WebElement> AppListOnLauncher = CommonLib.FindElements(driver, xPath, "App list on Launcher");

				int size = AppListOnLauncher.size();
				String text = CommonLib.getText(driver, AppListOnLauncher.get(0), "Apps on View All", action.BOOLEAN);

				if (size == 1 && text.equals("PE Cloud")) {
					log(LogStatus.INFO,
							"PE Could is appearing on the view all of App Launcher page and No other Apps are Appearing on the View All of App Launcher",
							YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR,
							"Either PE Could is not appearing on the View All of App Launcher page Or other Apps are Appearing on View All of App Launcher",
							YesNo.Yes);

				}
			} else {
				log(LogStatus.ERROR, "Could not click on the view all button", YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Could not click on the App Launcher Page", YesNo.Yes);

		}
		return flag;
	}

	public ArrayList<String> verifyHomePageTabs(ArrayList<String> Tabs) {

		ArrayList<String> homePageTabs = new ArrayList<String>();
		ArrayList<String> result = new ArrayList<String>();
		CommonLib.ThreadSleep(2000);

		for (int i = 0; i < getHomePageTabs().size(); i++) {
			String text = CommonLib.getText(driver, getHomePageTabs().get(i), "Homepage Tabs", action.BOOLEAN);
			homePageTabs.add(text);
		}

		for (int i = 0; i < Tabs.size(); i++) {
			if (Tabs.get(i).equals(homePageTabs.get(i))) {
				log(LogStatus.INFO, Tabs.get(i) + " has been matched in Home page tab with " + homePageTabs.get(i),
						YesNo.No);
			} else {
				log(LogStatus.ERROR, Tabs.get(i) + " is not matched in Home page tab with " + homePageTabs.get(i),
						YesNo.No);
				result.add(Tabs.get(i) + " is not matched int Home page tab with " + homePageTabs.get(i));
			}
		}

		return result;
	}

	/**
	 * @author Ankur Huria
	 * @param Title
	 */
	public boolean sdgGridExpandedByDefaultIfNotThenExpandByTooltip(String Title) {
		boolean flag = false;
		CommonLib.ThreadSleep(8000);
		WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + Title + "']",
				"Component Title ", action.SCROLLANDBOOLEAN, 10);
		if (alreadyAddedComponentToHomePage != null) {

			log(LogStatus.INFO, "Component Title Matched to Home Page " + Title, YesNo.Yes);

			WebElement TooltipElement = FindElement(driver,
					"//a[text()='" + Title + "']/ancestor::article/preceding-sibling::lightning-icon", "Tooltip",
					action.SCROLLANDBOOLEAN, 20);
			if (CommonLib.getAttribute(driver, TooltipElement, "Collapse/Expand Element", "title")
					.equalsIgnoreCase("Expand")) {
				log(LogStatus.INFO, "Not Expanded By Default SDG: " + Title, YesNo.No);
				log(LogStatus.INFO, "Now Expanding  SDG: " + Title, YesNo.No);

				if (click(driver, TooltipElement, "Collapse/Expand Element", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on Collapse/Expand");
					flag = true;
				} else {
					log(LogStatus.ERROR, "Not Able to click on Expand Button of SDG :" + Title, YesNo.No);

				}
			}

			else {
				log(LogStatus.INFO, "Expanded By Default SDG :" + Title, YesNo.No);
				flag = true;

			}
		} else {
			log(LogStatus.ERROR, "Component Title Not Matched to Home Page :" + Title, YesNo.No);

		}

		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param sdgName
	 * @param pazeSize
	 * @return boolean
	 */

	public int numberOfRecords(String Title, String pageSize) {
		boolean flag = false;
		int size = 0;
		if (sdgGridExpandedByDefaultIfNotThenExpandByTooltip(Title)) {
			log(LogStatus.INFO, "SDG data has been expended", YesNo.No);

			if (pageSizeSelect(Title, pageSize)) {
				log(LogStatus.INFO, "Page size " + pageSize + " has been selected", YesNo.No);
				CommonLib.ThreadSleep(30000);
				List<WebElement> records = FindElements(driver, "//a[text()=\"" + Title
						+ "\"]/ancestor::article//tbody/tr//span[not(text()=\"No data returned\")]/ancestor::tr",
						"Records");
				System.out.println("No. of Records Present: " + records.size());
				size = records.size();
				flag = true;
			} else {
				log(LogStatus.ERROR, "Could not select the Pagesize", YesNo.No);
				flag = false;
			}
		} else {
			log(LogStatus.ERROR, "Could not expend the SDG", YesNo.No);
			flag = false;
		}
		return size;

	}

	/*	*//**
			 * @author Ankur Huria
			 * @param SDGGridName
			 *//*
				 * 
				 * public void verifyColumnAscendingDescendingOrder(String sdgGridName,
				 * List<String> columnNames, List<String> dateColumns) {
				 * 
				 * List<WebElement> headerList = sdgGridAllHeadersLabelNameList(sdgGridName);
				 * List<String> columnDataText = headerList.stream().map(s ->
				 * s.getText()).collect(Collectors.toList()).stream() .map(t ->
				 * t.trim()).collect(Collectors.toList()); if (!headerList.isEmpty()) { int i =
				 * 0; for (String columnName : columnNames) { int columnIndex =
				 * columnDataText.indexOf(columnName); if (i == 0) { if
				 * (CommonLib.checkSorting(driver, SortOrder.Decending,
				 * sdgGridColumnsDataList(sdgGridName.toString(), columnIndex + 1))) {
				 * log(LogStatus.PASS, SortOrder.Decending + "Check Sorting on " + columnName +
				 * " Columns ", YesNo.No); } else { log(LogStatus.FAIL, "Not Checked " +
				 * SortOrder.Decending + "Sorting on " + columnName + " Columns ", YesNo.No);
				 * sa.assertTrue(false, "Not Checked " + SortOrder.Decending + "Sorting on " +
				 * columnName + " Columns "); } } else { if (clickUsingJavaScript(driver,
				 * headerList.get(columnIndex), sdgGridName.toString() +
				 * " SDG Grid header column " + columnName, action.SCROLLANDBOOLEAN)) {
				 * log(LogStatus.PASS, "Clicked on Header" + columnName + " Clomun " +
				 * (columnIndex + 1) + " for " + SortOrder.Assecending, YesNo.No);
				 * ThreadSleep(35000);
				 * 
				 * if (!dateColumns.contains(columnName)) {
				 * 
				 * if (CommonLib.checkSorting(driver, SortOrder.Assecending,
				 * sdgGridColumnsDataList(sdgGridName.toString(), columnIndex + 1))) {
				 * log(LogStatus.PASS, SortOrder.Assecending + " Check Sorting on " +
				 * sdgGridName.toString() + " Columns " + columnName, YesNo.No); } else {
				 * log(LogStatus.FAIL, SortOrder.Assecending + " Not Checked Sorting on " +
				 * sdgGridName.toString() + " Columns " + columnName, YesNo.No);
				 * sa.assertTrue(false, SortOrder.Assecending + " Not Checked Sorting on " +
				 * sdgGridName.toString() + " Columns " + columnName); }
				 * 
				 * }
				 * 
				 * else { List<String> expectedDateListText = new ArrayList<String>();
				 * List<String> actualDateListText = new ArrayList<String>(); List<WebElement>
				 * actualDateListWebElement = sdgGridColumnsDataList(sdgGridName.toString(),
				 * columnIndex + 1); actualDateListText =
				 * actualDateListWebElement.stream().map(date -> date.getText())
				 * .collect(Collectors.toList()).stream().filter(x -> !x.equals(""))
				 * .collect(Collectors.toList()); expectedDateListText =
				 * dateToAscendingOrder(actualDateListWebElement);
				 * 
				 * if (actualDateListText.equals(expectedDateListText)) { log(LogStatus.PASS,
				 * SortOrder.Assecending + " Check Sorting on " + sdgGridName.toString() +
				 * " Columns " + columnName, YesNo.No); } else { log(LogStatus.FAIL,
				 * SortOrder.Assecending + " Not Checked Sorting on " + sdgGridName.toString() +
				 * " Columns " + columnName, YesNo.No); sa.assertTrue(false,
				 * SortOrder.Assecending + " Not Checked Sorting on " + sdgGridName.toString() +
				 * " Columns " + columnName); }
				 * 
				 * }
				 * 
				 * } else { log(LogStatus.PASS, "Not able to click on " + sdgGridName.toString()
				 * + " SDG Grid header " + columnName + " so cannot check Sorting " +
				 * SortOrder.Assecending, YesNo.Yes); sa.assertTrue(false,
				 * "Not able to click on " + sdgGridName.toString() + " SDG Grid header " +
				 * columnName + " so cannot check Sorting " + SortOrder.Assecending); }
				 * headerList = sdgGridAllHeadersLabelNameList(sdgGridName); } if (i == 0) { if
				 * (clickUsingJavaScript(driver, headerList.get(columnIndex),
				 * sdgGridName.toString() + " SDG Grid header column " + columnName,
				 * action.SCROLLANDBOOLEAN)) { ThreadSleep(35000); log(LogStatus.PASS,
				 * "Clicked on Header" + columnName + " Clomun " + (i + 1) +
				 * SortOrder.Assecending, YesNo.No); if (CommonLib.checkSorting(driver,
				 * SortOrder.Assecending, sdgGridColumnsDataList(sdgGridName.toString(),
				 * columnIndex + 1))) { log(LogStatus.PASS, SortOrder.Assecending +
				 * " Check Sorting on " + columnName + " Column on " + sdgGridName.toString() +
				 * " SDG Grid", YesNo.No); } else { log(LogStatus.FAIL, "Not Checked " +
				 * SortOrder.Assecending + " Sorting on " + sdgGridName.toString() + " Columns "
				 * + columnName, YesNo.No); sa.assertTrue(false, "Not Checked " +
				 * SortOrder.Assecending + " Sorting on " + sdgGridName.toString() + " Columns "
				 * + columnName); } } else { log(LogStatus.PASS, "Not able to click on " +
				 * sdgGridName.toString() + " SDG Grid header " + columnName +
				 * " so cannot check Sorting " + SortOrder.Decending, YesNo.Yes);
				 * sa.assertTrue(false, "Not able to click on " + sdgGridName.toString() +
				 * " SDG Grid header " + columnName + " so cannot check Sorting " +
				 * SortOrder.Decending); } } else { if (clickUsingJavaScript(driver,
				 * headerList.get(columnIndex), sdgGridName.toString() +
				 * " SDG Grid header column", action.SCROLLANDBOOLEAN)) { ThreadSleep(35000);
				 * log(LogStatus.PASS, "Clicked on Header " + columnName + " Clomun " +
				 * (columnIndex + 1) + SortOrder.Decending, YesNo.No);
				 * 
				 * if (!dateColumns.contains(columnName)) { if (CommonLib.checkSorting(driver,
				 * SortOrder.Decending, sdgGridColumnsDataList(sdgGridName.toString(),
				 * columnIndex + 1))) { log(LogStatus.PASS, SortOrder.Decending +
				 * " Check Sorting on " + columnName + " Columns on SDG Grid " +
				 * sdgGridName.toString(), YesNo.No); } else { log(LogStatus.FAIL,
				 * "Not Checked " + SortOrder.Decending + " Sorting on " +
				 * sdgGridName.toString() + " Columns " + columnName, YesNo.No);
				 * sa.assertTrue(false, "Not Checked " + SortOrder.Decending + " Sorting on " +
				 * sdgGridName.toString() + " Columns " + columnName); }
				 * 
				 * }
				 * 
				 * else { List<String> expectedDateListText = new ArrayList<String>();
				 * List<String> actualDateListText = new ArrayList<String>(); List<WebElement>
				 * actualDateListWebElement = sdgGridColumnsDataList(sdgGridName.toString(),
				 * columnIndex + 1); actualDateListText =
				 * actualDateListWebElement.stream().map(date -> date.getText())
				 * .collect(Collectors.toList()).stream().filter(x -> !x.equals(""))
				 * .collect(Collectors.toList()); expectedDateListText =
				 * dateToDescendingOrder(actualDateListWebElement);
				 * 
				 * if (actualDateListText.equals(expectedDateListText)) { log(LogStatus.PASS,
				 * SortOrder.Decending + " Check Sorting on " + columnName +
				 * " Columns on SDG Grid " + sdgGridName.toString(), YesNo.No); } else {
				 * log(LogStatus.FAIL, "Not Checked " + SortOrder.Decending + " Sorting on " +
				 * sdgGridName.toString() + " Columns " + columnName, YesNo.No);
				 * sa.assertTrue(false, "Not Checked " + SortOrder.Decending + " Sorting on " +
				 * sdgGridName.toString() + " Columns " + columnName); }
				 * 
				 * } } else { log(LogStatus.PASS, "Not able to click on " +
				 * sdgGridName.toString() + " SDG Grid header " + columnName +
				 * " so cannot check Sorting " + SortOrder.Assecending, YesNo.Yes);
				 * sa.assertTrue(false, "Not able to click on " + sdgGridName.toString() +
				 * " SDG Grid header " + columnName + " so cannot check Sorting " +
				 * SortOrder.Assecending); } } headerList =
				 * sdgGridAllHeadersLabelNameList(sdgGridName); i++; } } else {
				 * log(LogStatus.PASS, sdgGridName.toString() +
				 * " SDG Grid header cloumns list is not visible so cannot check Sorting ",
				 * YesNo.Yes); sa.assertTrue(false, sdgGridName.toString() +
				 * " SDG Grid header cloumns list is not visible so cannot check Sorting "); } }
				 */

	/**
	 * @author Ankur Huria
	 * @param SDGGridName
	 */

	public boolean verifySDGColumnDefaultAscendingOrDescending(String sdgGridName, String columnName, String dateColumn,
			String AscendingOrDescending) {

		boolean flag = false;
		List<WebElement> headerList = sdgGridAllHeadersLabelNameList(sdgGridName);
		List<String> columnDataText = headerList.stream().map(s -> s.getText()).collect(Collectors.toList()).stream()
				.map(t -> t.trim()).collect(Collectors.toList());
		if (!headerList.isEmpty()) {
			int columnIndex = 0;
			if (!"".equals(dateColumn) || dateColumn != null) {
				columnIndex = columnDataText.indexOf(dateColumn);
			} else if (!"".equals(columnName) || columnName != null) {
				columnIndex = columnDataText.indexOf(columnName);
			}

			else {
				return false;
			}

			if (AscendingOrDescending.equalsIgnoreCase("Descending")) {

				if ("".equals(dateColumn) || dateColumn == null) {

					if (!"".equals(columnName) || columnName != null) {
						if (columnIndex >= 0) {
							if (CommonLib.checkSorting(driver, SortOrder.Decending,
									sdgGridColumnsDataList(sdgGridName.toString(), columnIndex + 1))) {
								log(LogStatus.PASS,
										SortOrder.Decending + "Check Sorting on " + columnName + " Columns ", YesNo.No);
								flag = true;
							} else {
								log(LogStatus.FAIL,
										"Not Checked " + SortOrder.Decending + "Sorting on " + columnName + " Columns ",
										YesNo.No);
								sa.assertTrue(false, "Not Checked " + SortOrder.Decending + "Sorting on " + columnName
										+ " Columns ");
							}
						} else {
							log(LogStatus.FAIL, "Column: " + columnName + " is not Present in SDG: " + sdgGridName
									+ " So, Not able to check Default Sorting", YesNo.No);
							sa.assertTrue(false, "Column: " + columnName + " is not Present in SDG: " + sdgGridName
									+ " So, Not able to check Default Sorting");
						}
					}

					else {
						log(LogStatus.FAIL,
								"Both Column Name and DateColumn not Provided, Please Provid either of them", YesNo.No);
						sa.assertTrue(false,
								"Both Column Name and DateColumn not Provided, Please Provid either of them");
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
						log(LogStatus.PASS, SortOrder.Decending + " Check Sorting on " + columnName
								+ " Columns on SDG Grid " + sdgGridName.toString(), YesNo.No);
						flag = true;
					} else {
						log(LogStatus.FAIL, "Not Checked " + SortOrder.Decending + " Sorting on "
								+ sdgGridName.toString() + " Columns " + columnName, YesNo.No);
						sa.assertTrue(false, "Not Checked " + SortOrder.Decending + " Sorting on "
								+ sdgGridName.toString() + " Columns " + columnName);
					}

				}

			}

			else if (AscendingOrDescending.equalsIgnoreCase("Ascending"))

			{

				if ("".equals(dateColumn) || dateColumn == null) {

					if (!"".equals(columnName) || columnName != null) {
						if (columnIndex >= 0) {
							if (CommonLib.checkSorting(driver, SortOrder.Assecending,
									sdgGridColumnsDataList(sdgGridName.toString(), columnIndex + 1))) {
								log(LogStatus.PASS,
										SortOrder.Assecending + "Check Sorting on " + columnName + " Columns ",
										YesNo.No);
								flag = true;
							} else {
								log(LogStatus.FAIL, "Not Checked " + SortOrder.Assecending + "Sorting on " + columnName
										+ " Columns ", YesNo.No);
								sa.assertTrue(false, "Not Checked " + SortOrder.Assecending + "Sorting on " + columnName
										+ " Columns ");
							}
						} else {
							log(LogStatus.FAIL, "Column: " + columnName + " is not Present in SDG: " + sdgGridName
									+ " So, Not able to check Default Sorting", YesNo.No);
							sa.assertTrue(false, "Column: " + columnName + " is not Present in SDG: " + sdgGridName
									+ " So, Not able to check Default Sorting");
						}
					}

					else {
						log(LogStatus.FAIL,
								"Both Column Name and DateColumn not Provided, Please Provid either of them", YesNo.No);
						sa.assertTrue(false,
								"Both Column Name and DateColumn not Provided, Please Provid either of them");
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
						log(LogStatus.PASS, SortOrder.Assecending + " Check Sorting on " + columnName
								+ " Columns on SDG Grid " + sdgGridName.toString(), YesNo.No);
						flag = true;
					} else {
						log(LogStatus.FAIL, "Not Checked " + SortOrder.Assecending + " Sorting on "
								+ sdgGridName.toString() + " Columns " + columnName, YesNo.No);
						sa.assertTrue(false, "Not Checked " + SortOrder.Assecending + " Sorting on "
								+ sdgGridName.toString() + " Columns " + columnName);
					}

				}

			}

			else {
				log(LogStatus.FAIL, "Please Provide Either 'Ascending' or 'Descending' in Parameter", YesNo.No);
				sa.assertTrue(false, "Please Provide Either 'Ascending' or 'Descending' in Parameter");
			}
		} else {

			log(LogStatus.FAIL, "No Columns Find for SDG: " + sdgGridName, YesNo.No);
			sa.assertTrue(false, "No Columns Find for SDG: " + sdgGridName);

		}
		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param SDGGridName
	 */

	public void verifyColumnAscendingDescendingOrder(String sdgGridName, List<String> columnNames,
			List<String> dateColumns, String FirstColumnAscYesOrNoByDefault) {

		List<WebElement> headerList = sdgGridAllHeadersLabelNameList(sdgGridName);
		List<String> columnDataText = headerList.stream().map(s -> s.getText()).collect(Collectors.toList()).stream()
				.map(t -> t.trim()).collect(Collectors.toList());
		if (!headerList.isEmpty()) {
			int i = 0;

			if (FirstColumnAscYesOrNoByDefault.equalsIgnoreCase("Yes")) {
				if (clickUsingJavaScript(driver, headerList.get(2), sdgGridName + " SDG Grid header column",
						action.SCROLLANDBOOLEAN)) {
					ThreadSleep(1000);
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
					ThreadSleep(1000);

					if (!dateColumns.contains(columnName)) {

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
					ThreadSleep(1000);
					log(LogStatus.PASS,
							"Clicked on Header " + columnName + " Clomun " + (columnIndex + 1) + SortOrder.Decending,
							YesNo.No);

					if (!dateColumns.contains(columnName)) {
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
	 */
	public void notificationPopUpClose() {

		if (notificationPopUpCloseButton(2) != null) {
			if (clickUsingJavaScript(driver, notificationPopUpCloseButton(10), "Notification PopUp Close Button",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.PASS, "Clicked on Notification Popup Close Button", YesNo.No);
				if (notificationPopUpCloseButton(2) == null) {
					log(LogStatus.INFO, "Notification Popup has been Closed", YesNo.No);
				}

				else {
					log(LogStatus.FAIL, "Notification Popup has not been Closed", YesNo.Yes);
					sa.assertTrue(false, "Notification Popup has not been Closed");
				}
			} else {
				log(LogStatus.FAIL, "Not Able Click on Notification Popup Close Button", YesNo.Yes);
				sa.assertTrue(false, "Not Able Click on Notification Popup Close Button");
			}
		} else {
			log(LogStatus.INFO, "Notification Popup not showing, so not able to close it", YesNo.No);
		}

	}

	public List<String> verifyNotificationOptions(String... eventName) {

		List<WebElement> notificationOptionsList = getNotificationOptions();
		List<WebElement> notificationButtonsList = getnotificationButtons();
		boolean flag = false;
		List<String> negativeResults = new ArrayList<String>();

		if (notificationHeader(5) != null) {
			log(LogStatus.PASS, "Notification Header is present there: " + notificationHeader(5).getText(), YesNo.No);

		} else {
			log(LogStatus.FAIL, "Notification Header is not present there", YesNo.No);
			negativeResults.add("Notification Header is not present there");
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

				log(LogStatus.FAIL, "Event: " + eventName[i] + " is not present there in Notification Pane", YesNo.No);
				negativeResults.add("Event: " + eventName[i] + " is not present there in Notification Pane");
			}
		}

		return negativeResults;

	}

	public List<String> verifyNotificationUIOnHomePage() {

		List<String> negativeResults = new ArrayList<String>();

		List<String> expectedNotificationFooterButtons = Arrays.asList(new String[] { "Snooze", "Close" });
		if (notificationHeader(5) != null) {
			log(LogStatus.PASS, "Notification Header is present there: " + notificationHeader(5).getText(), YesNo.No);

			if (notificationPopUpCloseButton(10) != null) {
				log(LogStatus.PASS, "Notification Close Icon is there", YesNo.No);
			} else {
				log(LogStatus.ERROR, "Notification Close Icon is not there", YesNo.No);
				negativeResults.add("Notification Close Icon is not there");
			}

			if (notificationFooterButtons().containsAll(expectedNotificationFooterButtons)) {
				log(LogStatus.PASS, "Notification Footer Buttons present: " + expectedNotificationFooterButtons,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Notification Footer Buttons not present: " + expectedNotificationFooterButtons,
						YesNo.No);
				negativeResults.add("Notification Footer Buttons not present: " + expectedNotificationFooterButtons);
			}

		} else {
			log(LogStatus.FAIL, "Notification Header is not present there", YesNo.No);
			negativeResults.add("Notification Header is not present there");
		}

		return negativeResults;
	}

	public List<String> verifyNotificationOptionsNotContains(String... eventName) {

		List<WebElement> notificationOptionsList = getNotificationOptions();

		List<String> negativeResults = new ArrayList<String>();

		if (notificationHeader(5) != null) {
			log(LogStatus.PASS,
					"Notification Header is present there: " + notificationHeader(5).getText() + " in Home Page",
					YesNo.No);

		} else {
			log(LogStatus.FAIL, "Notification Header is not present there in Home Page", YesNo.No);

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

	public boolean globalSearchAndNavigate(String recordName, String sideNavOption, boolean noResultMsg) {
		boolean flag = false;
		if (clickUsingJavaScript(driver, globalSearchButton(20), "globalSearchButton", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on globalSearchButton", YesNo.No);
			if (sendKeysAndPressEnter(driver, globalSearchInput(20), recordName, "globalSearchInput",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Pass the Value to Input Box: " + recordName, YesNo.No);

				if ("".equals(sideNavOption) || sideNavOption == null) {
					if (noResultMsg) {
						if (globalSearchNoResultMsg(7) != null) {

							log(LogStatus.INFO, "No Result Msg showing for record: " + recordName, YesNo.No);

							flag = true;

						}

						else {
							log(LogStatus.ERROR, "No Result Msg nt showing for record: " + recordName, YesNo.Yes);
							sa.assertTrue(false, "No Result Msg nt showing for record: " + recordName);

						}
					} else {

						if (globalSearchRecord(recordName, 7) != null) {
							log(LogStatus.INFO, "Record found named: " + recordName, YesNo.No);
							if (click(driver, globalSearchRecord(recordName, 7), "globalSearchRecord: " + recordName,
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Record: " + recordName, YesNo.No);

								if (sideNavOption.equalsIgnoreCase("Tasks")) {
									CommonLib.ThreadSleep(4000);
									String parentID = CommonLib.switchOnWindow(driver);
									CommonLib.ThreadSleep(4000);
									if (parentID != null) {

										log(LogStatus.INFO, "Subject: " + recordName + " found on All Interaction Page",
												YesNo.No);

										flag = true;

									}

									else {
										log(LogStatus.ERROR,
												"No New Window Open after click on View All Button for Subject: "
														+ recordName,
												YesNo.Yes);
									}
								} else {
									flag = true;
								}

							} else {
								log(LogStatus.ERROR, "Not able to Click on Record: " + recordName, YesNo.Yes);
								sa.assertTrue(false, "Not able to Click on Record: " + recordName);

							}

						} else {
							log(LogStatus.ERROR, "No Record found in Top Results for record: " + recordName, YesNo.Yes);
							sa.assertTrue(false, "No Record found in Top Results for record: " + recordName);
						}

					}
				} else {
					if (click(driver, globalSearchSideNavShowMoreButton(15), "globalSearchSideNavShowMoreButton",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on globalSearchSideNavShowMoreButton", YesNo.No);

						if (globalSearchSideNavOptionLink(sideNavOption, 7) != null) {
							log(LogStatus.INFO, "Option found named: " + sideNavOption, YesNo.No);

							if (click(driver, globalSearchSideNavOptionLink(sideNavOption, 7),
									"globalSearchSideNavOptionLink", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on globalSearchSideNavOptionLink", YesNo.No);

								if (noResultMsg) {
									if (globalSearchNoResultMsg(7) != null) {

										log(LogStatus.INFO, "No Result Msg showing for record: " + recordName,
												YesNo.No);

										flag = true;

									}

									else {
										log(LogStatus.ERROR, "No Result Msg nt showing for record: " + recordName,
												YesNo.Yes);
										sa.assertTrue(false, "No Result Msg nt showing for record: " + recordName);

									}
								} else {

									if (globalSearchRecord(recordName, 7) != null) {
										log(LogStatus.INFO, "Record found named: " + recordName, YesNo.No);
										if (click(driver, globalSearchRecord(recordName, 7),
												"globalSearchRecord: " + recordName, action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Clicked on Record: " + recordName, YesNo.No);
											flag = true;
										} else {
											log(LogStatus.ERROR, "Not able to Click on Record: " + recordName,
													YesNo.Yes);
											sa.assertTrue(false, "Not able to Click on Record: " + recordName);

										}

									} else {
										log(LogStatus.ERROR, "No Record found in side nav: " + sideNavOption
												+ " for record: " + recordName, YesNo.Yes);
										sa.assertTrue(false, "No Record found in side nav: " + sideNavOption
												+ " for record: " + recordName);
									}

								}

							} else {
								log(LogStatus.ERROR, "Not able to click on globalSearchSideNavOptionLink", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on globalSearchSideNavOptionLink");
							}

						} else {
							log(LogStatus.ERROR, "No Option found named: " + sideNavOption, YesNo.Yes);
							sa.assertTrue(false, "No Option found named: " + sideNavOption);

						}

					} else {
						log(LogStatus.ERROR, "Not able to click on globalSearchSideNavShowMoreButton", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on globalSearchSideNavShowMoreButton");
					}

				}

			} else {
				log(LogStatus.ERROR, "Not able to Pass the Value to Input Box: " + recordName, YesNo.Yes);
				sa.assertTrue(false, "Not able to Pass the Value to Input Box: " + recordName);
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on globalSearchButton", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on globalSearchButton");

		}
		return flag;
	}

}