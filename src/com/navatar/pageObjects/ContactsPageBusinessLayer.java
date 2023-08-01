package com.navatar.pageObjects;

import static com.navatar.generic.AppListeners.appLog;

import java.util.ArrayList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.server.handler.SwitchToFrame;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.SoftAssert;
import com.navatar.generic.EnumConstants.AddressAction;
import com.navatar.generic.EnumConstants.ContactPageFieldLabelText;
import com.navatar.generic.EnumConstants.LimitedPartnerPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RecordType;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.SDGGridName;
import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
import com.navatar.generic.EnumConstants.SortOrder;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.CommonLib.*;

public class ContactsPageBusinessLayer extends ContactsPage implements ContactPageErrorMessage {

	public ContactsPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param tabName
	 * @param labelName
	 * @param labelValue
	 * @return true/false
	 * @description verify all fields present on contact page
	 */
	public boolean fieldValueVerificationOnContactPage(String projectName, TabName tabName, String labelName,
			String labelValue) {
		WebElement ele = null;
		String finalLabelName = "";
		ele = getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
		click(driver, ele, RelatedTab.Details.toString(), action.SCROLLANDBOOLEAN);
		ThreadSleep(2000);
		if (labelName.contains("_")) {
			if (labelName.equalsIgnoreCase(excelLabel.Asst_Phone.toString())) {
				finalLabelName = IndiviualInvestorFieldLabel.Asst_Phone.toString();
			} else {
				finalLabelName = labelName.replace("_", " ");
			}
		} else if (labelName.equalsIgnoreCase("Profile_Image")) {
			finalLabelName = labelName;
		} else {
			finalLabelName = labelName;
		}
		String xpath = "";

		xpath = "//span[@class='test-id__field-label'][text()='" + finalLabelName + "']/../following-sibling::div/span";

		if (finalLabelName.contains("Street") || finalLabelName.contains("City") || finalLabelName.contains("State")
				|| finalLabelName.contains("Postal") || finalLabelName.contains("ZIP") || finalLabelName.contains("Zip")
				|| finalLabelName.contains("Country")) {

			// xpath="//span[text()='Address Information']/../../following-sibling::div";
			if (finalLabelName.contains("Legal Name")) {
				xpath = "(" + xpath + ")[2]";
			} else if (finalLabelName.contains("Other Street") || finalLabelName.contains("Other City")
					|| finalLabelName.contains("Other State") || finalLabelName.contains("Other Zip")
					|| finalLabelName.contains("Other Country")) {
				xpath = "//span[text()='Other Address']/../following-sibling::div";
			} else {
				xpath = "//span[text()='Mailing Address']/../following-sibling::div";
			}

		} else if (finalLabelName.contains(excelLabel.Phone.toString())) {
			xpath = "//*[@class='test-id__field-label'][starts-with(text(),'" + finalLabelName
					+ "')]/../following-sibling::div/*//a";
		}
		/*
		 * if(labelName.equalsIgnoreCase(excelLabel.Region.toString()) ||
		 * labelName.equalsIgnoreCase(excelLabel.Industry.toString())) { xpath =
		 * "//span[@class='test-id__field-label'][text()='" + finalLabelName +
		 * "']/../following-sibling::div/span//a";
		 * 
		 * }
		 */
		if (labelName.equalsIgnoreCase(excelLabel.Phone.toString())) {
			xpath = "//span[@class='test-id__field-label'][contains(text(),'" + finalLabelName
					+ "')]/../following-sibling::div/span//a";

		}
		// span[@class='test-id__field-label'][contains(text(),'Phone')]/../following-sibling::div/span//a
		ele = isDisplayed(driver, FindElement(driver, xpath, finalLabelName + " label text in " + projectName,
				action.SCROLLANDBOOLEAN, 5), "Visibility", 5, finalLabelName + " label text in " + projectName);
		if (ele != null) {
			String aa = ele.getText().trim();
			appLog.info("<<<<<<<<     " + finalLabelName + " : Lable Value is: " + aa + "      >>>>>>>>>>>");

			if (aa.isEmpty()) {
				appLog.error(finalLabelName + " Value is Empty label Value " + labelValue);
				return false;
			}

			if (labelName.equalsIgnoreCase(excelLabel.Phone.toString())
					|| labelName.equalsIgnoreCase(excelLabel.Fax.toString())
					|| labelName.equalsIgnoreCase(ContactPageFieldLabelText.Mobile.toString())
					|| labelName.equalsIgnoreCase(excelLabel.Asst_Phone.toString())) {

				if (aa.contains(labelValue) || aa.contains(changeNumberIntoUSFormat(labelValue))) {
					appLog.info(labelValue + " Value is matched successfully.");
					return true;

				}
			} else if (aa.contains(labelValue)) {
				appLog.info(labelValue + " Value is matched successfully.");
				return true;

			} else {
				appLog.info(labelValue + " Value is not matched. Expected: " + labelValue + " /t Actual : " + aa);
			}
		} else {
			appLog.error(finalLabelName + " Value is not visible so cannot matched  label Value " + labelValue);
		}
		return false;

	}

	public boolean officeLocationInputValueAndSelect_Lighting(String environment, String mode, String searchText,
			String lookUpValues) {
		String[] values = lookUpValues.split(",");
		WebElement ele = null;
		if (sendKeys(driver, getOfficeLocationTextBox_Lighting(environment, mode, 10), searchText,
				"Office Location Input Box", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(2000);
			for (int i = 0; i < values.length; i++) {
				ele = isDisplayed(driver, FindElement(driver, "//*[@title='" + values[i] + "']",
						values[i] + " text value", action.SCROLLANDBOOLEAN, 20), "visibility", 20,
						values[i] + " text value");

				if (ele != null) {
					appLog.info(values[i] + " is visible in look up popup");

					if (i == values.length - 1) {
						ele = isDisplayed(
								driver, FindElement(driver, "//*[@title='" + values[0] + "']",
										values[0] + " text value", action.SCROLLANDBOOLEAN, 20),
								"visibility", 20, values[0] + " text value");
						if (click(driver, ele, values[0] + " text value", action.SCROLLANDBOOLEAN)) {
							appLog.info("clicked on " + values[0] + " in lookup pop up");
							return true;
						}
					}

				} else {
					appLog.error(values[i] + " is not visible in look up popup");
					return false;
				}
			}

		}
		return false;
	}

	public boolean verifyCreatedOpenActivity(String environment, String mode, String activitySubject) {
		WebElement ele;
		String xpath;
		WebElement ele2 = getRelatedTab("", RelatedTab.Communications.toString(), 10);
		click(driver, ele2, RelatedTab.Communications.toString(), action.SCROLLANDBOOLEAN);
		ThreadSleep(2000);
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			xpath = "//h3[text()='Open Activities']/ancestor::div[@class='bRelatedList']//a[text()='" + activitySubject
					+ "']";
			ele = FindElement(driver, xpath, activitySubject, action.SCROLLANDBOOLEAN, 10);
		} else {
			ele = verifyCreatedActivityHistory_Lighting(environment, activitySubject);
		}
		if (ele != null) {
			return true;
		}
		return false;

	}

	public boolean verifyPresenceOfCorrespondenceRelatedList(String mode, String environment, String contactName,
			String LPname, String partnershipName, String commId, int timeOut) {
		WebElement ele = null;
		boolean flag = false;

		String xpath = "//*[text()='Contact']/..//following-sibling::div//*[text()='" + contactName + "']";
		ele = FindElement(driver, xpath, contactName, action.BOOLEAN, 30);
		if (ele != null) {
			xpath = "//*[text()='Limited Partner']/..//following-sibling::div//*[text()='" + LPname + "']";
			ele = FindElement(driver, xpath, LPname, action.BOOLEAN, 1);
			if (ele != null) {
				xpath = "//*[text()='Partnership']/..//following-sibling::div//*[text()='" + partnershipName + "']";
				ele = FindElement(driver, xpath, partnershipName, action.BOOLEAN, 1);

				if (ele != null) {
					xpath = "//*[text()='Commitment']/..//following-sibling::div//*[text()='" + commId + "']";
					ele = FindElement(driver, xpath, commId, action.BOOLEAN, 1);
					if (ele != null) {
						flag = true;
					} else {
						CommonLib.log(LogStatus.ERROR, "Data not verified on Correspondence List : " + contactName
								+ " >>>> " + LPname + " >>>> " + partnershipName + " >>> " + commId, YesNo.Yes);
					}

				} else {
					CommonLib.log(LogStatus.ERROR, "Data not verified on Correspondence List : " + contactName
							+ " >>>> " + LPname + " >>>> " + partnershipName + " >>> " + commId, YesNo.Yes);
				}
			} else {
				CommonLib.log(LogStatus.ERROR, "Data not verified on Correspondence List : " + contactName + " >>>> "
						+ LPname + " >>>> " + partnershipName + " >>> " + commId, YesNo.Yes);
			}
		} else {
			CommonLib.log(LogStatus.ERROR, "Data not verified on Correspondence List : " + contactName + " >>>> "
					+ LPname + " >>>> " + partnershipName + " >>> " + commId, YesNo.Yes);
		}

		return flag;
	}

	////////////////////////////////////////////////////////////// Activity
	////////////////////////////////////////////////////////////// Association
	////////////////////////////////////////////////////////////// /////////////////////////////////////

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param contactFirstName
	 * @param contactLastName
	 * @param legalName
	 * @param emailID
	 * @param recordType       TODO
	 * @param otherLabelFields
	 * @param otherLabelValues
	 * @param creationPage
	 * @param title            TODO
	 * @param tier             TODO
	 * @return true/false
	 * @description This is used to create new contact with given arguments
	 */
	public boolean createContact(String projectName, String contactFirstName, String contactLastName, String legalName,
			String emailID, String recordType, String otherLabelFields, String otherLabelValues,
			CreationPage creationPage, String title, String tier) {
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		String labelNames[] = null;
		String labelValue[] = null;
		if (otherLabelFields != null && otherLabelValues != null) {
			labelNames = otherLabelFields.split(",");
			labelValue = otherLabelValues.split(",");
		}
		if (creationPage.toString().equalsIgnoreCase(CreationPage.AccountPage.toString())) {

			if (ClickonRelatedTab_Lighting(projectName, RecordType.Contact, RelatedTab.Contacts.toString())) {
				appLog.info("clicked on related list tab");
			} else {
				appLog.error("Not able to click on related list tab so cannot create contact: " + contactFirstName + " "
						+ contactLastName);
				return false;
			}

			if (click(driver, ins.getNewContactBtn(projectName, 30), "new contact button in " + projectName,
					action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on new contact button in institution page");
			} else {
				appLog.error("Not able to click on new button on institution page so cannot create contact: "
						+ contactFirstName + " " + contactLastName);
				return false;
			}
		} else {
			refresh(driver);
			ThreadSleep(5000);
			if (clickUsingJavaScript(driver, getNewButton(projectName, 60), "new button")) {
				appLog.info("clicked on new button");

				if (!recordType.equals("") || !recordType.isEmpty()) {
					ThreadSleep(2000);
					if (click(driver, getRadioButtonforRecordType(recordType, 5), "Radio Button for : " + recordType,
							action.SCROLLANDBOOLEAN)) {
						appLog.info("Clicked on radio Button  for record type : " + recordType);
						if (click(driver, getContinueOrNextButton(projectName, 5), "Continue Button", action.BOOLEAN)) {
							appLog.info("Clicked on Continue or Nxt Button");
							ThreadSleep(1000);
						} else {
							appLog.error("Not Able to Clicked on Next Button");
							return false;
						}
					} else {
						appLog.error("Not Able to Clicked on radio Button for record type : " + recordType);
						return false;
					}

				}

			} else {
				appLog.error("Not able to click on New Button so cannot create Contact: " + contactFirstName + " "
						+ contactLastName);
				return false;
			}
		}
		WebElement ele = null;
		ThreadSleep(2000);
		if (sendKeys(driver, getContactFirstName(projectName, 60), contactFirstName, "Contact first Name",
				action.BOOLEAN)) {
			if (sendKeys(driver, getContactLastName(projectName, 60), contactLastName, "Contact Last Name",
					action.BOOLEAN)) {
				ThreadSleep(2000);
				if (creationPage.toString().equalsIgnoreCase(CreationPage.AccountPage.toString())) {

				} else {
					if (sendKeys(driver, getLegalName(projectName, 60), legalName, "Account Name",
							action.SCROLLANDBOOLEAN)) {
						ThreadSleep(2000);
						if (click(driver,
								FindElement(driver, "//*[text()='Legal Name']/..//*[@title='" + legalName + "']",
										"Legal Name List", action.THROWEXCEPTION, 30),
								legalName + "   :   Account Name", action.BOOLEAN)) {
							appLog.info(legalName + "  is present in list.");
						} else {
							appLog.info(legalName + "  is not present in the list.");
							return false;
						}

					} else {
						appLog.error("Not able to enter legal name");
						return false;
					}
				}
				ThreadSleep(2000);
				ele = getLabelTextBox(projectName, PageName.Object2Page.toString(), PageLabel.Email.toString(), 10);

				if (sendKeys(driver, ele, emailID, "Email ID", action.SCROLLANDBOOLEAN)) {
					if (labelNames != null && labelValue != null) {
						for (int i = 0; i < labelNames.length; i++) {

							if (labelNames[i].equalsIgnoreCase(excelLabel.Contact_Type.toString().replace("_", " "))) {

								String firmDropDownElements = "//*[text()='" + labelNames[i]
										+ "']/..//button/../following-sibling::div//lightning-base-combobox-item";
								if (CommonLib.dropDownHandle(driver, dropDownWithLabelName(labelNames[i], 20),
										firmDropDownElements, labelNames[i] + "DropDown", labelValue[i])) {
									log(LogStatus.INFO, labelNames[i] + " has been Selected to:  " + labelValue[i],
											YesNo.No);
									continue;
								} else {
									sa.assertTrue(false,
											labelNames[i] + " has not been Selected to:  " + labelValue[i]);
									log(LogStatus.ERROR, labelNames[i] + " has not been Selected to:  " + labelValue[i],
											YesNo.Yes);
									continue;
								}

							}

							ele = getContactPageTextBoxOrRichTextBoxWebElement(projectName, labelNames[i].trim(), 30);
							if (sendKeysAndPressEnter(driver, ele, labelValue[i], labelNames[i] + " text box",
									action.SCROLLANDBOOLEAN)) {
								appLog.info("passed value " + labelValue[i] + " in " + labelNames[i] + " field");
							} else {
								appLog.error(
										"Not able to pass value " + labelValue[i] + " in " + labelNames[i] + " field");
								BaseLib.sa.assertTrue(false,
										"Not able to pass value " + labelValue[i] + " in " + labelNames[i] + " field");
							}
							if (labelNames[i].equalsIgnoreCase(excelLabel.Region.toString())
									|| labelNames[i].equalsIgnoreCase(excelLabel.Sector.toString())) {
								if (click(driver, FindElement(driver, "//*[text()='" + labelNames[i]
										+ "']/following-sibling::div[@class='slds-form-element__control']//input[@type='text']",
										"picklist " + labelNames[i], action.SCROLLANDBOOLEAN, 10),
										"picklist " + labelNames[i], action.SCROLLANDBOOLEAN)) {

									if (click(driver,
											FindElement(driver,
													"//div[contains(@class,'listbox')]//*[@data-value='" + labelValue[i]
															+ "']",
													"Legal Name List", action.THROWEXCEPTION, 30),
											labelNames[i] + "   :   Account Name", action.BOOLEAN)) {
										appLog.info(labelNames[i] + "  is present in list.");
									} else {
										appLog.error("Not able to select " + labelValue[i] + " in " + labelNames[i]
												+ " field");
										BaseLib.sa.assertTrue(false, "Not able to select " + labelValue[i] + " in "
												+ labelNames[i] + " field");
									}
								} else {
									appLog.error(
											"Not able to select " + labelValue[i] + " in " + labelNames[i] + " field");
									BaseLib.sa.assertTrue(false,
											"Not able to select " + labelValue[i] + " in " + labelNames[i] + " field");
								}
							}

						}

					}
					if (title != null) {
						if (sendKeys(driver, getcontactTitle(projectName, 10), title, "title",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "passed value " + title + " to title", YesNo.No);
						} else {
							log(LogStatus.ERROR, "could not pass value " + title + " to title", YesNo.No);
							BaseLib.sa.assertTrue(false, "could not pass value " + title + " to title");
						}
					}
					if (tier != null) {

						if (click(driver, getcontactTier(projectName, 30), "contact tier dropdown",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked contact tier dropdown", YesNo.No);
							ThreadSleep(2000);
							String xpath = "//span[@title='" + tier + "']/../..";
							WebElement dropdownValue = FindElement(driver, xpath, "", action.BOOLEAN, 30);
							if (click(driver, dropdownValue, "", action.BOOLEAN)) {
								log(LogStatus.INFO, "Selected tier :" + tier + " value in teir dropdown", YesNo.No);

							} else {
								log(LogStatus.INFO, "Not able to Select tier :" + tier + " value in teir dropdown",
										YesNo.Yes);
								BaseLib.sa.assertTrue(false,
										"Not able to Select tier :" + tier + " value in teir dropdown");
							}

						} else {
							log(LogStatus.INFO, "Not able to clicked contact tier dropdown", YesNo.Yes);
							BaseLib.sa.assertTrue(false, "Not able to clicked contact tier dropdown");

						}
					}
					if (click(driver, getNavigationTabSaveBtn(projectName, 60), "Save Button",
							action.SCROLLANDBOOLEAN)) {
						appLog.info("Clicked on save button");
						ThreadSleep(3000);
						if (getNavigationTabSaveBtn(projectName, 5) != null) {
							click(driver, getNavigationTabSaveBtn(projectName, 60), "save", action.BOOLEAN);
						}
						ThreadSleep(3000);
						if (creationPage.toString().equalsIgnoreCase(CreationPage.AccountPage.toString())) {
							if (clickOnGridSection_Lightning(projectName, RelatedList.Contacts, 30)) {
								ele = isDisplayed(driver,
										FindElement(driver,
												"//*[text()='Contact']/following-sibling::*//*[text()='"
														+ contactFirstName + " " + contactLastName + "']",
												"Contact Name Text", action.SCROLLANDBOOLEAN, 30),
										"visibility", 20, "");
								if (ele != null) {
									String contactFullName = getText(driver, ele, "Contact Name", action.BOOLEAN);
									System.err.println("Contact Name : " + contactFullName);
									if (contactFullName.contains(contactFirstName + " " + contactLastName)) {
										appLog.info("Contact Created Successfully :" + contactFirstName + " "
												+ contactLastName);
										return true;
									} else {
										appLog.error("Contact did not get created successfully :" + contactFirstName
												+ " " + contactLastName);
									}
								} else {
									appLog.error("Not able to find contact name label");
								}
							} else {
								log(LogStatus.ERROR,
										"Not able to click on Contacts related list view all section so cannot verify Created Contact "
												+ contactFirstName + " " + contactLastName,
										YesNo.Yes);
							}

						} else {
							if (projectName.equalsIgnoreCase(Mode.Lightning.toString())) {
								ThreadSleep(2000);
								refresh(driver);
								ThreadSleep(5000);
							}
							CommonLib.refresh(driver);
							ele = getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
							click(driver, ele, RelatedTab.Details.toString(), action.SCROLLANDBOOLEAN);

							String expectedContactFullName = "";
							if (contactFirstName == null || "".equals(contactFirstName))
								expectedContactFullName = contactLastName;
							else
								expectedContactFullName = contactFirstName + " " + contactLastName;

							if (clicktabOnPage("Details")) {
								log(LogStatus.PASS, "Clicked on SubTab: " + "Details", YesNo.No);

								ele = isDisplayed(driver,
										FindElement(driver,
												"//*[text()='Contact']/following-sibling::*//*[text()='"
														+ expectedContactFullName + "']",
												"Contact Name Text", action.SCROLLANDBOOLEAN, 30),
										"visibility", 20, "");

								if (ele != null) {

									String contactFullName = getText(driver, ele, "Contact Name",
											action.SCROLLANDBOOLEAN);
									System.err.println("Contact Name : " + contactFullName);
									if (contactFullName.contains(expectedContactFullName)) {
										appLog.info("Contact Created Successfully :" + contactFirstName + " "
												+ contactLastName);
										ThreadSleep(5000);
										if (labelNames != null && labelValue != null) {
											for (int i = 0; i < labelNames.length; i++) {
												if (fieldValueVerificationOnContactPage(projectName, null,
														labelNames[i].replace("_", " ").trim(), labelValue[i])) {
													appLog.info(labelNames[i] + " label value " + labelValue[i]
															+ " is matched successfully.");
												} else {
													appLog.info(labelNames[i] + " label value " + labelValue[i]
															+ " is not matched successfully.");
													BaseLib.sa.assertTrue(false, labelNames[i] + " label value "
															+ labelValue[i] + " is not matched.");
												}
											}
										}
										return true;
									} else {
										appLog.error("Contact did not get created successfully :" + contactFirstName
												+ " " + contactLastName);
									}
								} else {
									appLog.error("Not able to find contact name label");
								}

							} else {
								log(LogStatus.ERROR, "Not able to click on SubTab: " + "Details", YesNo.No);

							}

						}

					} else {
						appLog.info("Not able to click on save button");
					}

				} else {
					appLog.error("Not able to enter email id");
				}

			} else {
				appLog.error("Not able to enter last name in text box");
			}
		} else {
			appLog.error("Not able to enter first Name in text box");
		}
		return false;
	}

	public boolean createContactAcuity(String projectName, String contactFirstName, String contactLastName,
			String legalName, String emailID, String recordType, String otherLabelFields, String otherLabelValues,
			CreationPage creationPage, String title, String tier) {
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		String labelNames[] = null;
		String labelValue[] = null;
		boolean flag = false;
		if (otherLabelFields != null && otherLabelValues != null) {
			labelNames = otherLabelFields.split(",");
			labelValue = otherLabelValues.split(",");
		}
		if (creationPage.toString().equalsIgnoreCase(CreationPage.AccountPage.toString())) {

			if (ClickonRelatedTab_Lighting(projectName, RecordType.Contact, RelatedTab.Contacts.toString())) {
				appLog.info("clicked on related list tab");
			} else {
				appLog.error("Not able to click on related list tab so cannot create contact: " + contactFirstName + " "
						+ contactLastName);
				return flag;
			}

			if (click(driver, ins.getNewContactBtn(projectName, 30), "new contact button in " + projectName,
					action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on new contact button in institution page");
			} else {
				appLog.error("Not able to click on new button on institution page so cannot create contact: "
						+ contactFirstName + " " + contactLastName);
				return flag;
			}
		} else {
			refresh(driver);
			ThreadSleep(5000);
			if (clickUsingJavaScript(driver, getNewButton(projectName, 60), "new button")) {
				appLog.info("clicked on new button");

				if (!recordType.equals("") || !recordType.isEmpty()) {
					ThreadSleep(2000);
					if (click(driver, getRadioButtonforRecordType(recordType, 5), "Radio Button for : " + recordType,
							action.SCROLLANDBOOLEAN)) {
						appLog.info("Clicked on radio Button  for record type : " + recordType);
						if (click(driver, getContinueOrNextButton(projectName, 5), "Continue Button", action.BOOLEAN)) {
							appLog.info("Clicked on Continue or Nxt Button");
							ThreadSleep(1000);
						} else {
							appLog.error("Not Able to Clicked on Next Button");
							return flag;
						}
					} else {
						appLog.error("Not Able to Clicked on radio Button for record type : " + recordType);
						return flag;
					}

				}

			} else {
				appLog.error("Not able to click on New Button so cannot create Contact: " + contactFirstName + " "
						+ contactLastName);
				return flag;
			}
		}
		WebElement ele = null;
		ThreadSleep(2000);
		if (sendKeys(driver, getContactFirstName(projectName, 60), contactFirstName, "Contact first Name",
				action.BOOLEAN)) {
			if (sendKeys(driver, getContactLastName(projectName, 60), contactLastName, "Contact Last Name",
					action.BOOLEAN)) {
				ThreadSleep(2000);
				if (creationPage.toString().equalsIgnoreCase(CreationPage.AccountPage.toString())) {

				} else {
					if (sendKeys(driver, getLegalName(projectName, 60), legalName, "Account Name",
							action.SCROLLANDBOOLEAN)) {
						ThreadSleep(2000);
						if (click(driver,
								FindElement(driver, "//*[text()='Legal Name']/..//*[@title='" + legalName + "']",
										"Legal Name List", action.THROWEXCEPTION, 30),
								legalName + "   :   Account Name", action.BOOLEAN)) {
							appLog.info(legalName + "  is present in list.");
						} else {
							appLog.info(legalName + "  is not present in the list.");
							return flag;
						}

					} else {
						appLog.error("Not able to enter legal name");
						return flag;
					}
				}
				ThreadSleep(2000);
				ele = getLabelTextBox(projectName, PageName.Object2Page.toString(), PageLabel.Email.toString(), 10);

				if (sendKeys(driver, ele, emailID, "Email ID", action.SCROLLANDBOOLEAN)) {
					if (labelNames != null && labelValue != null) {
						for (int i = 0; i < labelNames.length; i++) {

							if (labelNames[i].equalsIgnoreCase(excelLabel.Contact_Type.toString().replace("_", " "))) {

								String firmDropDownElements = "//*[text()='" + labelNames[i]
										+ "']/..//button/../following-sibling::div//lightning-base-combobox-item";
								if (CommonLib.dropDownHandle(driver, dropDownWithLabelName(labelNames[i], 20),
										firmDropDownElements, labelNames[i] + "DropDown", labelValue[i])) {
									log(LogStatus.INFO, labelNames[i] + " has been Selected to:  " + labelValue[i],
											YesNo.No);
									continue;
								} else {
									sa.assertTrue(false,
											labelNames[i] + " has not been Selected to:  " + labelValue[i]);
									log(LogStatus.ERROR, labelNames[i] + " has not been Selected to:  " + labelValue[i],
											YesNo.Yes);
									continue;
								}

							}

							ele = getContactPageTextBoxOrRichTextBoxWebElement(projectName, labelNames[i].trim(), 30);
							if (sendKeysAndPressEnter(driver, ele, labelValue[i], labelNames[i] + " text box",
									action.SCROLLANDBOOLEAN)) {
								appLog.info("passed value " + labelValue[i] + " in " + labelNames[i] + " field");
							} else {
								appLog.error(
										"Not able to pass value " + labelValue[i] + " in " + labelNames[i] + " field");
								BaseLib.sa.assertTrue(false,
										"Not able to pass value " + labelValue[i] + " in " + labelNames[i] + " field");
							}
							if (labelNames[i].equalsIgnoreCase(excelLabel.Region.toString())
									|| labelNames[i].equalsIgnoreCase(excelLabel.Sector.toString())) {
								if (click(driver, FindElement(driver, "//*[text()='" + labelNames[i]
										+ "']/following-sibling::div[@class='slds-form-element__control']//input[@type='text']",
										"picklist " + labelNames[i], action.SCROLLANDBOOLEAN, 10),
										"picklist " + labelNames[i], action.SCROLLANDBOOLEAN)) {

									if (click(driver,
											FindElement(driver,
													"//div[contains(@class,'listbox')]//*[@data-value='" + labelValue[i]
															+ "']",
													"Legal Name List", action.THROWEXCEPTION, 30),
											labelNames[i] + "   :   Account Name", action.BOOLEAN)) {
										appLog.info(labelNames[i] + "  is present in list.");
									} else {
										appLog.error("Not able to select " + labelValue[i] + " in " + labelNames[i]
												+ " field");
										BaseLib.sa.assertTrue(false, "Not able to select " + labelValue[i] + " in "
												+ labelNames[i] + " field");
									}
								} else {
									appLog.error(
											"Not able to select " + labelValue[i] + " in " + labelNames[i] + " field");
									BaseLib.sa.assertTrue(false,
											"Not able to select " + labelValue[i] + " in " + labelNames[i] + " field");
								}
							}

						}

					}
					if (title != null) {
						if (sendKeys(driver, getcontactTitle(projectName, 10), title, "title",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "passed value " + title + " to title", YesNo.No);
						} else {
							log(LogStatus.ERROR, "could not pass value " + title + " to title", YesNo.No);
							BaseLib.sa.assertTrue(false, "could not pass value " + title + " to title");
						}
					}
					if (tier != null) {

						if (click(driver, getcontactTier(projectName, 30), "contact tier dropdown",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked contact tier dropdown", YesNo.No);
							ThreadSleep(2000);
							String xpath = "//span[@title='" + tier + "']/../..";
							WebElement dropdownValue = FindElement(driver, xpath, "", action.BOOLEAN, 30);
							if (click(driver, dropdownValue, "", action.BOOLEAN)) {
								log(LogStatus.INFO, "Selected tier :" + tier + " value in teir dropdown", YesNo.No);

							} else {
								log(LogStatus.INFO, "Not able to Select tier :" + tier + " value in teir dropdown",
										YesNo.Yes);
								BaseLib.sa.assertTrue(false,
										"Not able to Select tier :" + tier + " value in teir dropdown");
							}

						} else {
							log(LogStatus.INFO, "Not able to clicked contact tier dropdown", YesNo.Yes);
							BaseLib.sa.assertTrue(false, "Not able to clicked contact tier dropdown");

						}
					}
					if (click(driver, getNavigationTabSaveBtn(projectName, 60), "Save Button",
							action.SCROLLANDBOOLEAN)) {
						appLog.info("Clicked on save button");
						ThreadSleep(3000);
						if (getNavigationTabSaveBtn(projectName, 5) != null) {
							click(driver, getNavigationTabSaveBtn(projectName, 60), "save", action.BOOLEAN);
						}
flag= true;
						ThreadSleep(3000);

						if (creationPage.toString().equalsIgnoreCase(CreationPage.AccountPage.toString())) {
//							if (clickOnGridSection_Lightning(projectName, RelatedList.Contacts, 30)) {
//								ele = isDisplayed(driver,
//										FindElement(driver,
//												"//*[text()='Contact']/following-sibling::*//*[text()='"
//														+ contactFirstName + " " + contactLastName + "']",
//												"Contact Name Text", action.SCROLLANDBOOLEAN, 30),
//										"visibility", 20, "");
//								if (ele != null) {
//									String contactFullName = getText(driver, ele, "Contact Name", action.BOOLEAN);
//									System.err.println("Contact Name : " + contactFullName);
//									if (contactFullName.contains(contactFirstName + " " + contactLastName)) {
//										appLog.info("Contact Created Successfully :" + contactFirstName + " "
//												+ contactLastName);
//										flag = true;
//									} else {
//										appLog.error("Contact did not get created successfully :" + contactFirstName
//												+ " " + contactLastName);
//									}
//								} else {
//									appLog.error("Not able to find contact name label");
//								}
//							} else {
//								log(LogStatus.ERROR,
//										"Not able to click on Contacts related list view all section so cannot verify Created Contact "
//												+ contactFirstName + " " + contactLastName,
//										YesNo.Yes);
//							}

						} else {
							if (projectName.equalsIgnoreCase(Mode.Lightning.toString())) {
								ThreadSleep(2000);
								refresh(driver);
								ThreadSleep(5000);
							}
//							ele = getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
//							click(driver, ele, RelatedTab.Details.toString(), action.SCROLLANDBOOLEAN);

							ele = isDisplayed(driver, FindElement(driver,
									"//*[text()='Contact']/following-sibling::*//*[text()='" + contactFirstName + " "
											+ contactLastName + "']",
									"Contact Name Text", action.SCROLLANDBOOLEAN, 30), "visibility", 20, "");

							if (ele != null) {

								String contactFullName = getText(driver, ele, "Contact Name", action.SCROLLANDBOOLEAN);
								System.err.println("Contact Name : " + contactFullName);
								if (contactFullName.contains(contactFirstName + " " + contactLastName)) {
									appLog.info("Contact Created Successfully :" + contactFirstName + " "
											+ contactLastName);
									ThreadSleep(5000);
									if (labelNames != null && labelValue != null) {
										for (int i = 0; i < labelNames.length; i++) {
											if (fieldValueVerificationOnContactPage(projectName, null,
													labelNames[i].replace("_", " ").trim(), labelValue[i])) {
												appLog.info(labelNames[i] + " label value " + labelValue[i]
														+ " is matched successfully.");
											} else {
												appLog.info(labelNames[i] + " label value " + labelValue[i]
														+ " is not matched successfully.");
												BaseLib.sa.assertTrue(false, labelNames[i] + " label value "
														+ labelValue[i] + " is not matched.");
											}
										}
									}
									return true;
								} else {
									appLog.error("Contact did not get created successfully :" + contactFirstName + " "
											+ contactLastName);
								}
							} else {
								appLog.error("Not able to find contact name label");
							}

						}

					} else {
						appLog.info("Not able to click on save button");
					}

				} else {
					appLog.error("Not able to enter email id");
				}

			} else {
				appLog.error("Not able to enter last name in text box");
			}
		} else {
			appLog.error("Not able to enter first Name in text box");
		}
		return false;
	}

	/**
	 * @author Ravi Kumar
	 * @param projectName
	 * @param contactFirstName
	 * @param contactLastName
	 * @description This is used to create new contact with given arguments
	 */
	public boolean UpdateContactTier(String projectName, PageName pageName, String tier) {

		if (clickOnShowMoreActionDownArrow(projectName, pageName, ShowMoreActionDropDownList.Edit, 30)) {
			log(LogStatus.INFO, "clicked on edit button", YesNo.No);

			ThreadSleep(2000);
			if (click(driver, getcontactTier(projectName, 30), "contact tier dropdown", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked contact tier dropdown", YesNo.No);
				ThreadSleep(2000);
				String xpath = "//span[@title='" + tier + "']/../..";
				WebElement dropdownValue = FindElement(driver, xpath, "", action.BOOLEAN, 30);
				if (click(driver, dropdownValue, "", action.BOOLEAN)) {
					log(LogStatus.INFO, "Selected tier :" + tier + " value in teir dropdown", YesNo.No);

				} else {
					log(LogStatus.INFO, "Not able to Select tier :" + tier + " value in teir dropdown", YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Not able to Select tier :" + tier + " value in teir dropdown");
				}

			} else {
				log(LogStatus.INFO, "Not able to clicked contact tier dropdown", YesNo.Yes);
				BaseLib.sa.assertTrue(false, "Not able to clicked contact tier dropdown");

			}

			if (click(driver, getNavigationTabSaveBtn(projectName, 60), "Save Button", action.SCROLLANDBOOLEAN)) {
				appLog.info("Clicked on save button");
				ThreadSleep(3000);
				if (getNavigationTabSaveBtn(projectName, 5) != null) {
					click(driver, getNavigationTabSaveBtn(projectName, 60), "save", action.BOOLEAN);
				}
				return true;
			} else {
				appLog.info("Not able to click on save button");
			}

		} else {

			log(LogStatus.INFO, "Not able to clicked on edit button so cannot update tier ", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "Not able to clicked on edit button so cannot update tier ");
		}

		return false;
	}

	/**
	 * @author Sahil Bansal
	 * @param projectName
	 * @param pageName
	 * @param legalName
	 * @description This is used to update legal name of contact
	 */
	public boolean UpdateLegalName(String projectName, PageName pageName, String legalName) {

		if (clickOnShowMoreActionDownArrow(projectName, pageName, ShowMoreActionDropDownList.Edit, 30)) {
			log(LogStatus.INFO, "clicked on edit button", YesNo.No);
			if (click(driver, getLegalCrossIcon(projectName, 60), "Legal Cross Icon", action.SCROLLANDBOOLEAN)) {
				appLog.info("Clicked on Legal Cross icon");
				ThreadSleep(3000);
			} else {
				appLog.info("Not able to click on Cross Icon button");
				log(LogStatus.INFO, "Not able to clicked on edit button so cannot Account Name ", YesNo.Yes);
				BaseLib.sa.assertTrue(false, "Not able to clicked on edit button so cannot Account Name ");
			}

			if (sendKeys(driver, getLegalName(projectName, 60), legalName, "Account Name", action.SCROLLANDBOOLEAN)) {
				ThreadSleep(2000);
				if (click(driver,
						FindElement(driver, "//*[text()='Legal Name']/..//*[@title='" + legalName + "']",
								"Legal Name List", action.THROWEXCEPTION, 30),
						legalName + "   :   Account Name", action.BOOLEAN)) {
					appLog.info(legalName + "  is present in list.");
				} else {
					appLog.info(legalName + "  is not present in the list.");
					return false;
				}

			} else {
				appLog.error("Not able to enter legal name");
				return false;
			}
		}

		if (click(driver, getNavigationTabSaveBtn(projectName, 60), "Save Button", action.SCROLLANDBOOLEAN)) {
			appLog.info("Clicked on save button");
			ThreadSleep(3000);
			if (getNavigationTabSaveBtn(projectName, 5) != null) {
				click(driver, getNavigationTabSaveBtn(projectName, 60), "save", action.BOOLEAN);
			}
			return true;
		} else {
			appLog.info("Not able to click on save button");
			log(LogStatus.INFO, "Not able to clicked on edit button so cannot Account Name ", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "Not able to clicked on edit button so cannot Account Name ");
		}

		return false;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param RecordType
	 * @return true/false
	 * @description This is used to click on related tab
	 */
	public boolean ClickonRelatedTab_Lighting(String projectName, RecordType RecordType) {

		for (int i = 0; i < 2; i++) {
			refresh(driver);
			ThreadSleep(3000);
			List<WebElement> eleList = FindElements(driver, "//*[text()='Related']", "Related Tab");
			for (WebElement ele : eleList) {
				if (click(driver, ele, RecordType + " related tab", action.BOOLEAN)) {
					log(LogStatus.INFO, "clicked on " + RecordType + " related tab", YesNo.No);
					return true;
				}
			}
		}
		log(LogStatus.ERROR, "Not able to click on related tab " + RecordType, YesNo.Yes);
		return false;
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param contactFirstName
	 * @param contactLastName
	 * @return true/false
	 * @description this method is used to click on already created contact
	 */
	public boolean clickOnCreatedContact(String projectName, String contactFirstName, String contactLastName) {
		int i = 1;
		String concatFullName;
		if (contactFirstName == null) {
			concatFullName = contactLastName;
		} else {
			concatFullName = contactFirstName + " " + contactLastName;
		}
		if (clickOnAlreadyCreatedItem(projectName, concatFullName, TabName.Object2Tab, 20)) {
			appLog.info("Clicked on Contact name : " + concatFullName);
			return true;
		} else {
			appLog.error("Contact Not Available : " + concatFullName);
		}

		return false;
	}

	public WebElement verifyCreatedActivityHistory_Lighting(String environment, String activitySubject) {
		WebElement ele;
		String xpath;
		xpath = "//a[contains(text(),'" + activitySubject + "')]";
		ele = FindElement(driver, xpath, activitySubject, action.SCROLLANDBOOLEAN, 10);
		return ele;

	}

	public boolean verifyCreatedActivityHistory(String environment, String mode, String activitySubject) {
		WebElement ele;
		String xpath;
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			xpath = "//h3[text()='Activity History']/ancestor::div[@class='bRelatedList']//a[contains(text(),'"
					+ activitySubject + "')]";
			ele = FindElement(driver, xpath, activitySubject, action.SCROLLANDBOOLEAN, 10);
		} else {
			ele = verifyCreatedActivityHistory_Lighting(environment, activitySubject);
		}
		if (ele != null) {
			appLog.info("Activity History Grid : " + ele.getText().trim());
			return true;
		}
		return false;

	}

	public boolean verifyContactTransferUIonContactPage(String environment, String mode, String contactName,
			String legalName, int timeOut) {
		WebElement ele;
		boolean flag = true;
		;
		NavatarSetupPageBusinessLayer np = new NavatarSetupPageBusinessLayer(driver);
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			switchToFrame(driver, timeOut, np.getnavatarSetUpTabFrame_Lighting(environment, 10));
		}
		ele = isDisplayed(driver,
				FindElement(driver, "//div[@class='ContentStart']//p[@title='Contact Transfer']",
						"Contact Transfer Page", action.BOOLEAN, timeOut),
				"Visibility", timeOut, "Contact Transfer Page");

		if (ele != null) {
			CommonLib.log(LogStatus.INFO, "Landing Page Verified : Contact Transfer", YesNo.No);
		} else {
			flag = false;
			CommonLib.log(LogStatus.INFO, "Landing Page Not Verified : Contact Transfer", YesNo.Yes);
		}

		ele = isDisplayed(driver,
				FindElement(driver, "//label[text()='Name']/../following-sibling::td[text()='" + contactName + "']",
						"Contact Name : " + contactName, action.BOOLEAN, timeOut),
				"Visibility", timeOut, "Contact Name : " + contactName);

		if (ele != null) {
			CommonLib.log(LogStatus.INFO, "Name Label Verified : " + contactName, YesNo.No);
		} else {
			flag = false;
			CommonLib.log(LogStatus.INFO, "Name Label Not Verified : " + contactName, YesNo.Yes);
		}

		ele = isDisplayed(driver,
				FindElement(driver, "//label[text()='Legal Name']/../following-sibling::td[text()='" + legalName + "']",
						"Legal Name : " + contactName, action.BOOLEAN, timeOut),
				"Visibility", timeOut, "Legal Name : " + contactName);

		if (ele != null) {
			CommonLib.log(LogStatus.INFO, "Legal Name Label Verified : " + contactName, YesNo.No);
		} else {
			flag = false;
			CommonLib.log(LogStatus.INFO, "Legal Name Label Not Verified : " + contactName, YesNo.Yes);
		}
		switchToDefaultContent(driver);
		return flag;

	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param accountName
	 * @param timeOut
	 * @return true if able to enter value for Legal Name on Contact Transfer Page
	 */
	public boolean enteringValueforLegalNameOnContactTransferPage(String projectName, String accountName, int timeOut) {
		WebElement ele;
		boolean flag = true;
		;
		NavatarSetupPageBusinessLayer np = new NavatarSetupPageBusinessLayer(driver);
		switchToFrame(driver, timeOut, np.getnavatarSetUpTabFrame_Lighting(projectName, timeOut));
		String AcctOrLegalName = "";
		if (ProjectName.MNA.toString().equalsIgnoreCase(projectName)) {
			AcctOrLegalName = "Account Name";
		} else if (projectName.contains(ProjectName.PE.toString())) {
			AcctOrLegalName = "Legal Name";
		} else {
			AcctOrLegalName = "Firm";
		}

		ele = isDisplayed(driver,
				FindElement(driver,
						"//label[text()='" + AcctOrLegalName + "']/../following-sibling::td/span/div/span//input",
						"Legal Name ", action.BOOLEAN, timeOut),
				"Visibility", timeOut, "Legal Name ");

		if (sendKeys(driver, ele, accountName, "Input Value : " + accountName, action.BOOLEAN)) {
			CommonLib.log(LogStatus.INFO, "Entered Value: " + accountName, YesNo.No);
			if (click(driver, getTransferButton(projectName, timeOut), "Transfer Button", action.BOOLEAN)) {
				CommonLib.log(LogStatus.INFO, "Clicked on Transfer Button", YesNo.No);

//				ele = getContactTransferConfirmationMsg(projectName, timeOut);
//				if (ele != null) {
//					CommonLib.log(LogStatus.INFO, "Confirmation PopUp Element is Present", YesNo.No);
//					String msg = ele.getText();
//
//					if (ContactPageErrorMessage.TransferConfirmationPopUpMessage.equals(msg)) {
//						CommonLib.log(LogStatus.INFO, "Confirmation Msg Verified : " + msg, YesNo.No);
//					} else {
//						flag = false;
//						CommonLib.log(LogStatus.ERROR, "Confirmation Msg Not Verified Actual : " + msg
//								+ " \t Expected : " + ContactPageErrorMessage.TransferConfirmationPopUpMessage,
//								YesNo.Yes);
//					}
//
//				} else {
//					flag = false;
//					CommonLib.log(LogStatus.ERROR, "Confirmation PopUp Element is null", YesNo.Yes);
//				}

			} else {
				flag = false;
				CommonLib.log(LogStatus.ERROR, "Not Able to Click on Transfer Button", YesNo.Yes);
			}
		} else {
			flag = false;
			CommonLib.log(LogStatus.ERROR, "Not Able to Entered Value: " + accountName, YesNo.Yes);
		}
		switchToDefaultContent(driver);
		return flag;

	}

	public boolean enteringValueforLegalNameOnContactTransferPage(String environment, String mode, String legalName,
			AddressAction addressAction, int timeOut) {
		WebElement ele;
		boolean flag = true;
		;
		NavatarSetupPageBusinessLayer np = new NavatarSetupPageBusinessLayer(driver);
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			switchToFrame(driver, timeOut, np.getnavatarSetUpTabFrame_Lighting(environment, timeOut));
		}

		ele = isDisplayed(driver,
				FindElement(driver, "//label[text()='Legal Name']/../following-sibling::td/span/div/span//input",
						"Legal Name ", action.BOOLEAN, timeOut),
				"Visibility", timeOut, "Legal Name ");

		if (sendKeys(driver, ele, legalName, "Input Value : " + legalName, action.BOOLEAN)) {
			CommonLib.log(LogStatus.INFO, "Entered Value: " + legalName, YesNo.No);
			if (click(driver, getTransferButton(environment, timeOut), "Transfer Button", action.BOOLEAN)) {
				CommonLib.log(LogStatus.INFO, "Clicked on Transfer Button", YesNo.No);

				ele = getContactTransferConfirmationMsg(environment, timeOut);
				if (ele != null) {
					CommonLib.log(LogStatus.INFO, "Confirmation PopUp Element is Present", YesNo.No);
					String msg = ele.getText();

					if (ContactPageErrorMessage.TransferConfirmationPopUpMessage.equals(msg)) {
						CommonLib.log(LogStatus.INFO, "Confirmation Msg Verified : " + msg, YesNo.No);
					} else {
						flag = false;
						CommonLib.log(LogStatus.ERROR, "Confirmation Msg Not Verified Actual : " + msg
								+ " \t Expected : " + ContactPageErrorMessage.TransferConfirmationPopUpMessage,
								YesNo.Yes);
					}

				} else {
					flag = false;
					CommonLib.log(LogStatus.ERROR, "Confirmation PopUp Element is null", YesNo.Yes);
				}

				if (getRetainAddressButton(environment, timeOut) != null) {
					CommonLib.log(LogStatus.INFO, "Retain Address Button  Found", YesNo.No);
				} else {
					flag = false;
					CommonLib.log(LogStatus.ERROR, "Retain Address Button Not Found", YesNo.Yes);
				}

				if (getClearAddressButton(environment, timeOut) != null) {
					CommonLib.log(LogStatus.INFO, "Clear Address Button  Found", YesNo.No);
				} else {
					flag = false;
					CommonLib.log(LogStatus.ERROR, "Clear Address Button Not Found", YesNo.Yes);
				}

				if (addressAction.toString().equalsIgnoreCase(AddressAction.Retain.toString())) {

					if (click(driver, getRetainAddressButton(environment, timeOut), "Retain Address Button",
							action.BOOLEAN)) {
						CommonLib.log(LogStatus.INFO, "Clicked on Retain Address Button", YesNo.No);
					} else {
						flag = false;
						CommonLib.log(LogStatus.ERROR, "Not Able to Click on Retain Address Button", YesNo.Yes);
					}

				} else if (addressAction.toString().equalsIgnoreCase(AddressAction.Clear.toString())) {

					if (click(driver, getClearAddressButton(environment, timeOut), "Clear Address Button",
							action.BOOLEAN)) {
						CommonLib.log(LogStatus.INFO, "Clicked on Clear Address Button", YesNo.No);
					} else {
						flag = false;
						CommonLib.log(LogStatus.ERROR, "Not Able to Click on Clear Address Button", YesNo.Yes);
					}

				} else {
					if (click(driver, getCrossIcononContactTransferPopUp(environment, mode, timeOut), "Cross Icon",
							action.BOOLEAN)) {
						CommonLib.log(LogStatus.INFO, "Clicked on Cross Icon", YesNo.No);
					} else {
						flag = false;
						CommonLib.log(LogStatus.ERROR, "Not Able to Click on Cross Icon", YesNo.Yes);
					}
				}

			} else {
				flag = false;
				CommonLib.log(LogStatus.ERROR, "Not Able to Click on Transfer Button", YesNo.Yes);
			}
		} else {
			flag = false;
			CommonLib.log(LogStatus.ERROR, "Not Able to Entered Value: " + legalName, YesNo.Yes);
		}
		switchToDefaultContent(driver);
		return flag;

	}

	/**
	 * @author Akul Bhutani
	 * @param labelName
	 * @param value
	 * @return true if field set component verify successfully
	 */
	public boolean verifyFieldSetComponent(String labelName, String value) {
		String finalLabelName = "";
		if (labelName.contains("_")) {
			finalLabelName = labelName.replace("_", " ");
		} else {
			finalLabelName = labelName;
		}
		String xpath = "//*[@class='navpeIIDisplayFieldSet']//*[contains(text(),'" + finalLabelName
				+ "')]/following-sibling::div/*";

		WebElement ele = FindElement(driver, xpath, finalLabelName + " label text", action.SCROLLANDBOOLEAN, 5);
		if (ele != null) {
			String aa = ele.getText().trim();
			appLog.info("<<<<<<<<     " + finalLabelName + " : Lable Value is: " + aa + "      >>>>>>>>>>>");

			if (aa.isEmpty()) {
				appLog.error(finalLabelName + " Value is Empty label Value " + value);
				if (value.isEmpty() && aa.isEmpty()) {
					return true;
				} else {
					return false;
				}
			}
			if (labelName.equalsIgnoreCase(excelLabel.Phone.toString())
					|| labelName.equalsIgnoreCase(excelLabel.Fax.toString())
					|| labelName.equalsIgnoreCase(ContactPageFieldLabelText.Mobile.toString())
					|| labelName.equalsIgnoreCase(excelLabel.Asst_Phone.toString())) {

				if (aa.contains(value) || aa.contains(changeNumberIntoUSFormat(value))) {
					appLog.info(value + " Value is matched successfully.");
					return true;

				}
			} else if (aa.contains(value)) {
				appLog.info(value + " Value is matched successfully.");
				return true;

			} else {
				appLog.info(value + " Value is not matched. Expected: " + value + " /t Actual : " + aa);
			}
		} else {
			appLog.error(finalLabelName + " Value is not visible so cannot matched  label Value " + value);
		}
		return false;

	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param attachmentPath
	 * @return image id of updated photo in detail page
	 */
	public String updatePhotoInDetailPage(String projectName, String attachmentPath) {
		String imgId = null;
		Actions actions = new Actions(driver);
		scrollDownThroughWebelement(driver, getimgLink(projectName, 10), "img");
		actions.moveToElement(getimgLink(projectName, 10)).click(getimgLink(projectName, 10)).build().perform();
		ThreadSleep(2000);
		// actions.release().build().perform();
		log(LogStatus.INFO, "click on img link", YesNo.No);
		/*
		 * WebElement
		 * ele=getupdatePhotoLink(projectName,ContactPagePhotoActions.Update_Photo, 10);
		 * actions.moveToElement(ele).click(ele).build().perform(); ThreadSleep(2000);
		 */if (click(driver, getupdatePhotoLink(projectName, ContactPagePhotoActions.Update_Photo, 10),
				ContactPagePhotoActions.Update_Photo.toString(), action.SCROLLANDBOOLEAN)) {
			if (sendKeys(driver, getuploadPhotoButton(projectName, 10), attachmentPath, "upload photo button",
					action.SCROLLANDBOOLEAN)) {
				ThreadSleep(5000);
				/*
				 * if (uploadFileAutoIT(attachmentPath)) { log(LogStatus.INFO,
				 * "successfully uploaded file "+attachmentPath, YesNo.No); ThreadSleep(10000);
				 */ if (click(driver, getRecordPageSettingSave(10), "save", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "successfully uploaded photo", YesNo.No);
					ThreadSleep(4000);
					imgId = getimgLink(projectName, 10).getAttribute("src");
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
				/*
				 * }else { log(LogStatus.ERROR, "could not upload file "+attachmentPath,
				 * YesNo.Yes); sa.assertTrue(false, "could not upload file "+attachmentPath); }
				 */
			} else {
				log(LogStatus.ERROR, "could not pass attachment path to image", YesNo.Yes);
				sa.assertTrue(false, "could not pass attachment path to image");
			}
		} else {
			log(LogStatus.ERROR, "update photo button is not clickable", YesNo.Yes);
			sa.assertTrue(false, "update photo button is not clickable");
		}
		/*
		 * }else { log(LogStatus.ERROR, "photo button on contact page is not clickable",
		 * YesNo.Yes); sa.assertTrue(false,
		 * "photo button on contact page is not clickable"); }
		 */
		return null;
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param recordName
	 * @return true if image deleted successfully
	 */
	public boolean deleteImage(String projectName, String recordName) {
		String imgId = null;
//		Actions actions = new Actions(driver);
//		scrollDownThroughWebelement(driver,getimgLink(projectName, 10) , "img");
//		actions.moveToElement( getimgLink(projectName, 10)).click(getimgLink(projectName, 10)).build().perform();
		WebElement ele = getUpdatePhotoCameraIcon(10);
		if (ele != null) {
			if (click(driver, ele, "update photo camera icon", action.BOOLEAN)) {
				log(LogStatus.INFO, "clicked on update photo icon", YesNo.No);
				ThreadSleep(2000);
				if (click(driver, getupdatePhotoLink(projectName, ContactPagePhotoActions.Delete_Photo, 10),
						ContactPagePhotoActions.Update_Photo.toString(), action.SCROLLANDBOOLEAN)) {

					if (click(driver, getdeletePhotoButton(projectName, 10), "delete photo button",
							action.SCROLLANDBOOLEAN)) {
						ThreadSleep(4000);
						try {
							imgId = getimgLink(projectName, 10).getAttribute("src");
							if (imgId != null) {
								if (imgId.contains(defaultPhotoText)) {
									log(LogStatus.INFO, "successfully delete image", YesNo.Yes);
									return true;
								} else {
									log(LogStatus.ERROR, "not able to delete image", YesNo.Yes);
									sa.assertTrue(false, "not able to delete image");
								}
							} else {
								log(LogStatus.ERROR, "id of image not found so cannot verify delete image", YesNo.Yes);
								sa.assertTrue(false, "id of image not found so cannot verify delete image");
							}
						} catch (Exception e) {
							log(LogStatus.ERROR, "id of image not found so image is deleted", YesNo.Yes);
							return true;
						}
					} else {
						log(LogStatus.ERROR, "delete photo button on popup is not clickable", YesNo.Yes);
						sa.assertTrue(false, "delete photo button on popup is not clickable");
					}
				} else {
					log(LogStatus.ERROR, "delete photo button is not clickable", YesNo.Yes);
					sa.assertTrue(false, "delete photo button is not clickable");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on update photo icon so cannot delete photo on " + recordName,
						YesNo.Yes);
				sa.assertTrue(false, "Not able to click on update photo icon so cannot delete photo on " + recordName);
			}
		} else {
			log(LogStatus.ERROR, "camera photo icon is not displaying on " + recordName + " so cannot delete photo",
					YesNo.Yes);
			sa.assertTrue(false, "camera photo icon is not displaying on " + recordName + " so cannot delete photo");
		}
		return false;
	}

	/**
	 * @param projectName
	 * @param contactFirstName
	 * @param contactLastName
	 * @param legalName
	 * @param emailID
	 * @param recordType
	 * @param otherLabelFields
	 * @param otherLabelValues
	 * @param creationPage
	 * @param title
	 * @return true if able create contact from other Page rather than contact page
	 *         New Button
	 */
	public boolean createContactPopUp(String projectName, String contactFirstName, String contactLastName,
			String legalName, String emailID, String recordType, String otherLabelFields, String otherLabelValues,
			CreationPage creationPage, String title) {
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		String labelNames[] = null;
		String labelValue[] = null;
		if (otherLabelFields != null && otherLabelValues != null) {
			labelNames = otherLabelFields.split(",");
			labelValue = otherLabelValues.split(",");
		}
		if (creationPage.toString().equalsIgnoreCase(CreationPage.AccountPage.toString())) {

			if (ClickonRelatedTab_Lighting(projectName, RecordType.Contact)) {
				appLog.info("clicked on related list tab");
			} else {
				appLog.error("Not able to click on related list tab so cannot create contact: " + contactFirstName + " "
						+ contactLastName);
				return false;
			}

			if (click(driver, ins.getNewContactBtn(projectName, 30), "new contact button in " + projectName,
					action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on new contact button in institution page");
			} else {
				appLog.error("Not able to click on new button on institution page so cannot create contact: "
						+ contactFirstName + " " + contactLastName);
				return false;
			}
		} else {
			// refresh(driver);
			ThreadSleep(3000);
			ThreadSleep(5000);

			clickUsingJavaScript(driver, getNewButton(projectName, 60), "new button");

			if (!recordType.equals("") || !recordType.isEmpty()) {
				ThreadSleep(2000);
				if (click(driver, getRadioButtonforRecordType(recordType, 5), "Radio Button for : " + recordType,
						action.SCROLLANDBOOLEAN)) {
					appLog.info("Clicked on radio Button  for record type : " + recordType);
					if (click(driver, getContinueOrNextButton(projectName, 5), "Continue Button", action.BOOLEAN)) {
						appLog.info("Clicked on Continue or Nxt Button");
						ThreadSleep(1000);
					} else {
						appLog.error("Not Able to Clicked on Next Button");
						return false;
					}
				} else {
					appLog.error("Not Able to Clicked on radio Button for record type : " + recordType);
					return false;
				}

			}

		}
		WebElement ele = null;
		ThreadSleep(2000);
		if (sendKeys(driver, getContactFirstName(projectName, 60), contactFirstName, "Contact first Name",
				action.BOOLEAN)) {
			if (sendKeys(driver, getContactLastName(projectName, 60), contactLastName, "Contact Last Name",
					action.BOOLEAN)) {

				if (creationPage.toString().equalsIgnoreCase(CreationPage.AccountPage.toString())) {

				} else {
					if (sendKeys(driver, getLegalName(projectName, 60), legalName, "Account Name",
							action.SCROLLANDBOOLEAN)) {
						ThreadSleep(1000);
						if (click(driver,
								FindElement(driver, "//*[text()='" + legalName + "']/parent::div", "Legal Name List",
										action.THROWEXCEPTION, 30),
								legalName + "   :   Account Name", action.BOOLEAN)) {
							appLog.info(legalName + "  is present in list.");
						} else {
							appLog.info(legalName + "  is not present in the list.");
							return false;
						}

					} else {
						appLog.error("Not able to enter legal name");
						return false;
					}
				}
				ele = getLabelTextBox(projectName, PageName.Object2Page.toString(), PageLabel.Email.toString(), 10);

				if (sendKeys(driver, ele, emailID, "Email ID", action.SCROLLANDBOOLEAN)) {
					if (labelNames != null && labelValue != null) {
						for (int i = 0; i < labelNames.length; i++) {
							ele = getContactPageTextBoxOrRichTextBoxWebElement(projectName, labelNames[i].trim(), 30);
							if (sendKeysAndPressEnter(driver, ele, labelValue[i], labelNames[i] + " text box",
									action.SCROLLANDBOOLEAN)) {
								appLog.info("passed value " + labelValue[i] + " in " + labelNames[i] + " field");
							} else {
								appLog.error(
										"Not able to pass value " + labelValue[i] + " in " + labelNames[i] + " field");
								BaseLib.sa.assertTrue(false,
										"Not able to pass value " + labelValue[i] + " in " + labelNames[i] + " field");
							}
							if (labelNames[i].equalsIgnoreCase(excelLabel.Region.toString())
									|| labelNames[i].equalsIgnoreCase(excelLabel.Sector.toString())) {
								if (click(driver, FindElement(driver, "//*[text()='" + labelNames[i]
										+ "']/following-sibling::div[@class='slds-form-element__control']//input[@type='text']",
										"picklist " + labelNames[i], action.SCROLLANDBOOLEAN, 10),
										"picklist " + labelNames[i], action.SCROLLANDBOOLEAN)) {

									if (click(driver,
											FindElement(driver,
													"//div[contains(@class,'listbox')]//*[@data-value='" + labelValue[i]
															+ "']",
													"Legal Name List", action.THROWEXCEPTION, 30),
											labelNames[i] + "   :   Account Name", action.BOOLEAN)) {
										appLog.info(labelNames[i] + "  is present in list.");
									} else {
										appLog.error("Not able to select " + labelValue[i] + " in " + labelNames[i]
												+ " field");
										BaseLib.sa.assertTrue(false, "Not able to select " + labelValue[i] + " in "
												+ labelNames[i] + " field");
									}
								} else {
									appLog.error(
											"Not able to select " + labelValue[i] + " in " + labelNames[i] + " field");
									BaseLib.sa.assertTrue(false,
											"Not able to select " + labelValue[i] + " in " + labelNames[i] + " field");
								}
							}
						}

					}
					if (title != null) {
						if (sendKeys(driver, getcontactTitle(projectName, 10), title, "title",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "passed value " + title + " to title", YesNo.No);
						} else {
							log(LogStatus.ERROR, "could not pass value " + title + " to title", YesNo.No);
							BaseLib.sa.assertTrue(false, "could not pass value " + title + " to title");
						}
					}
					if (click(driver, getNavigationTabSaveBtn(projectName, 60), "Save Button",
							action.SCROLLANDBOOLEAN)) {
						appLog.info("Clicked on save button");
						ThreadSleep(3000);
						if (getNavigationTabSaveBtn(projectName, 5) != null) {
							click(driver, getNavigationTabSaveBtn(projectName, 60), "save", action.BOOLEAN);
						}
						if (creationPage.toString().equalsIgnoreCase(CreationPage.AccountPage.toString())) {
							if (clickOnGridSection_Lightning(projectName, RelatedList.Contacts, 30)) {
								ele = isDisplayed(driver,
										FindElement(driver,
												"//*[text()='Contact']/following-sibling::*//*[text()='"
														+ contactFirstName + " " + contactLastName + "']",
												"Contact Name Text", action.SCROLLANDBOOLEAN, 30),
										"visibility", 20, "");
								if (ele != null) {
									String contactFullName = getText(driver, ele, "Contact Name", action.BOOLEAN);
									System.err.println("Contact Name : " + contactFullName);
									if (contactFullName.contains(contactFirstName + " " + contactLastName)) {
										appLog.info("Contact Created Successfully :" + contactFirstName + " "
												+ contactLastName);
										return true;
									} else {
										appLog.error("Contact did not get created successfully :" + contactFirstName
												+ " " + contactLastName);
									}
								} else {
									appLog.error("Not able to find contact name label");
								}
							} else {
								log(LogStatus.ERROR,
										"Not able to click on Contacts related list view all section so cannot verify Created Contact "
												+ contactFirstName + " " + contactLastName,
										YesNo.Yes);
							}

						} else {
							if (projectName.equalsIgnoreCase(Mode.Lightning.toString())) {
								ThreadSleep(2000);
								refresh(driver);
								ThreadSleep(5000);
							}
							ele = getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
							click(driver, ele, RelatedTab.Details.toString(), action.SCROLLANDBOOLEAN);

							ele = isDisplayed(driver, FindElement(driver,
									"//*[text()='Contact']/following-sibling::*//*[text()='" + contactFirstName + " "
											+ contactLastName + "']",
									"Contact Name Text", action.SCROLLANDBOOLEAN, 30), "visibility", 20, "");

							if (ele != null) {

								String contactFullName = getText(driver, ele, "Contact Name", action.SCROLLANDBOOLEAN);
								System.err.println("Contact Name : " + contactFullName);
								if (contactFullName.contains(contactFirstName + " " + contactLastName)) {
									appLog.info("Contact Created Successfully :" + contactFirstName + " "
											+ contactLastName);
									ThreadSleep(5000);
									if (labelNames != null && labelValue != null) {
										for (int i = 0; i < labelNames.length; i++) {
											if (fieldValueVerificationOnContactPage(projectName, null,
													labelNames[i].replace("_", " ").trim(), labelValue[i])) {
												appLog.info(labelNames[i] + " label value " + labelValue[i]
														+ " is matched successfully.");
											} else {
												appLog.info(labelNames[i] + " label value " + labelValue[i]
														+ " is not matched successfully.");
												BaseLib.sa.assertTrue(false, labelNames[i] + " label value "
														+ labelValue[i] + " is not matched.");
											}
										}
									}
									return true;
								} else {
									appLog.error("Contact did not get created successfully :" + contactFirstName + " "
											+ contactLastName);
								}
							} else {
								appLog.error("Not able to find contact name label");
							}

						}

					} else {
						appLog.info("Not able to click on save button");
					}

				} else {
					appLog.error("Not able to enter email id");
				}

			} else {
				appLog.error("Not able to enter last name in text box");
			}
		} else {
			appLog.error("Not able to enter first Name in text box");
		}
		return false;
	}

	public boolean deleteContact(String projectName, String contactFirstName, String contactLastName) {
		boolean flag = false;
		if (click(driver, getcontactPageOnProfileEroButton(projectName, 30), "Ero Button On Contact Page",
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Ero Button for Delete the Contact ", YesNo.No);
			if (click(driver, getcontactPageDeleteButton(projectName, 30), "Delete Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Delete Button", YesNo.No);

				if (click(driver, getconfirmationDeleteButton(projectName, 30), "Confirmation Delete Button",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Confirmation Delete Button", YesNo.No);

					if (checkElementVisibility(driver, getconfirmationDeleteMessage(projectName, 50),
							"Delete Confirmation Message", 50)) {
						log(LogStatus.INFO, "Delete confirmation Message is visible So Email Id has been Deleted",
								YesNo.No);

						CommonLib.ThreadSleep(8000);
						return true;

					} else {
						log(LogStatus.ERROR, "Confirmation Delete Message is not visible", YesNo.No);
						return false;
					}
				} else {
					log(LogStatus.ERROR, "Could not click on the Confirmation Delete button", YesNo.No);
					return false;
				}

			} else {
				log(LogStatus.ERROR, "Could not click on the Delete button", YesNo.No);
				return false;
			}
		} else {
			log(LogStatus.ERROR, "Could not click on the Ero button", YesNo.No);
			return false;
		}

	}

	/**
	 * @author Ankur Huria
	 * @param SDGName
	 * @param datas
	 */
	public boolean createReferralThroughSDG(String projectName, String sdgName, String actionButtonName,
			String[][] sdgLabels, int timeOut) {
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		boolean flag = false;
		if (click(driver, home.listButtonOnSDG(sdgName, actionButtonName, timeOut), "Action Button " + actionButtonName,
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Button: " + actionButtonName + " on SDG: " + sdgName, YesNo.No);

			if (nextButtonOfNewFirm(20) != null) {
				if (click(driver, nextButtonOfNewFirm(30), "nextButtonOfNewFirm ", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Button: Next", YesNo.No);
				} else {
					log(LogStatus.ERROR, "Not Able to Clicked on Button: Next", YesNo.Yes);

				}
			}
			if (home.enterValueAndDropDownForSDGForm(projectName, sdgLabels, action.SCROLLANDBOOLEAN, timeOut)) {
				log(LogStatus.INFO, "Successfully Enter values on " + actionButtonName + " of SDG: " + sdgName,
						YesNo.Yes);

				if (click(driver, getRecordPageSettingSave(60), "Save Button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Click on Save Button  " + sdgName, YesNo.No);
					flag = true;
					ThreadSleep(5000);

				} else {
					sa.assertTrue(false,
							"Not Able to Click on Save Button Value so cannot create Firm on SDG:  " + sdgName);
					log(LogStatus.SKIP,
							"Not Able to Click on Save Button Value so cannot create Firm on SDG:  " + sdgName,
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

	public boolean CreateNewContactFromTab(String projectName, ArrayList<String> labelName, ArrayList<String> value,
			ArrayList<String> inputType) {
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		boolean flag = false;
		CommonLib.refresh(driver);
		CommonLib.ThreadSleep(3000);
		if (CommonLib.clickUsingJavaScript(driver, getContactTab(50), "Contact tab", action.SCROLLANDBOOLEAN)) {
			appLog.info("Clicked on contact tab");

			if (BP.createNewRecordThroughSDG(projectName, "Contacts", "New Contact", labelName, value, inputType, 50)) {
				log(LogStatus.PASS, "New Fundraising Contact has been created", YesNo.No);
				sa.assertTrue(true, "New Fundraising Contact has been created");
				flag = true;
			} else {
				log(LogStatus.FAIL, "New Fundraising Contact is not created", YesNo.No);
				sa.assertTrue(false, "New Fundraising Contact is not created");
			}

		} else {
			appLog.error("Not able to click on fundraising contact tab");
		}
		return flag;

	}

	/**
	 * @author Ankur Huria
	 * @param SDGName
	 * @param datas
	 */
	@SuppressWarnings("unused")
	public boolean createNewAffiliationThroughSDG(String projectName, String sdgName, String actionButtonName,
			String startDate, String endDate, String firmToSelect, String roleToChosen, int timeOut) {
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		boolean flag = false;
		if (click(driver, home.listButtonOnSDG(sdgName, actionButtonName, timeOut), "Action Button " + actionButtonName,
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Button: " + actionButtonName + " on SDG: " + sdgName, YesNo.No);

			String firmDropDownElements = "//ul[@aria-label='Recent Firms']//li//lightning-base-combobox-item/span/following-sibling::span/span[1]/span";
			if (CommonLib.dropDownHandle(driver, home.firmInputBox(30), firmDropDownElements, "Firm Input Box",
					firmToSelect)) {
				log(LogStatus.INFO, "Firm has been Selected  " + firmToSelect, YesNo.No);
			} else {
				sa.assertTrue(false, "Firm has not been Selected  " + firmToSelect);
				log(LogStatus.ERROR, "Firm has not been Selected  " + firmToSelect, YesNo.Yes);
			}

			List<WebElement> rolesData = chosenOrAvailableDataForLabel("Role", "Available");

			for (WebElement roledata : rolesData) {
				if (roledata.getText().equals(roleToChosen)) {
					if (click(driver, roledata, roleToChosen, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Click on " + roleToChosen + " Button of Label Role on SDG:  " + sdgName,
								YesNo.No);
						CommonLib.ThreadSleep(3000);

						if (click(driver, home.moveSectionToChosenOrAvailableButton("Role", "Chosen", 30),
								"MultiPickList Navigation Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,
									"Click on MultipickList Navigation Button of Label Role on SDG:  " + sdgName,
									YesNo.No);

							List<WebElement> chosenRoleDatas = chosenOrAvailableDataForLabel("Role", "Chosen");
							if (!chosenRoleDatas.isEmpty()) {
								List<String> chosenRoleDatasText = chosenRoleDatas.stream().map(x -> x.getText())
										.collect(Collectors.toList());
								if (chosenRoleDatasText.contains(roleToChosen)) {

									log(LogStatus.INFO, "Value: " + roleToChosen + " has been Chosen ", YesNo.Yes);

								} else {
									sa.assertTrue(false, "Value: " + roleToChosen
											+ " has not been Chosen, So Not Able to Cotinue to Create Affiliation Contact in SDG: "
											+ sdgName);
									log(LogStatus.ERROR, "Value: " + roleToChosen
											+ " has not been Chosen, So Not Able to Cotinue to Create Affiliation Contact in SDG: "
											+ sdgName, YesNo.Yes);
								}
							} else {
								sa.assertTrue(false,
										"Not Able to Chose the role " + roleToChosen + " on SDG:  " + sdgName);
								log(LogStatus.ERROR,
										"Not Able to Chose the role " + roleToChosen + " on SDG:  " + sdgName,
										YesNo.Yes);
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
								"Not Able to Click on Button Value so cannot move to Chosen of Label Role on SDG:  "
										+ sdgName);
						log(LogStatus.SKIP,
								"Not Able to Click on " + roleToChosen
										+ " Button Value so cannot move to Chosen of Label Role on SDG:  " + sdgName,
								YesNo.Yes);
					}

				}
			}

			if (startDate != null || startDate != "") {
				String[] date = startDate.split("/");

				if (click(driver, calendarInputBox("Start Date", 30), "Start Date Input Box",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Click on Start Date Calendar Input Box", YesNo.No);
					if (CommonLib.datePickerHandle(driver, monthInDatePicker(30), previousMonthButtonInDatePicker(30),
							"Start Date Picker", date[2], date[1], date[0])) {
						log(LogStatus.INFO, "Date has been Selected  " + startDate, YesNo.No);
					} else {
						sa.assertTrue(false, "Date has not been Selected  " + startDate);
						log(LogStatus.ERROR, "Date has not been Selected  " + startDate, YesNo.Yes);
					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Start Date Calendar input Box");
					log(LogStatus.ERROR, "Not Able to Click on Start Date Calendar input Box", YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "No Start Date Provided:  " + startDate, YesNo.Yes);
			}

			if (endDate != null || endDate != "") {
				String[] date = endDate.split("/");

				if (click(driver, calendarInputBox("End Date", 30), "End Date Input Box", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Click on End Date Calendar Input Box", YesNo.No);
					if (CommonLib.datePickerHandle(driver, monthInDatePicker(30), previousMonthButtonInDatePicker(30),
							"Start Date Picker", date[2], date[1], date[0])) {
						log(LogStatus.INFO, "Date has been Selected  " + endDate, YesNo.No);
					} else {
						sa.assertTrue(false, "Date has not been Selected  " + endDate);
						log(LogStatus.ERROR, "Date has not been Selected  " + endDate, YesNo.Yes);
					}

				} else {
					sa.assertTrue(false, "Not Able to Click on End Date Calendar input Box");
					log(LogStatus.ERROR, "Not Able to Click on End Date Calendar input Box", YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "No End Date Provided:  " + endDate, YesNo.Yes);
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

	public String dateFormatChange(String date) {
		String formattedEng = null;
		String resultedDate = null;
		try {
			DateTimeFormatter dtfInput = DateTimeFormatter.ofPattern("dd/MMM/uuuu", Locale.ENGLISH);

			LocalDate Date = LocalDate.parse(date, dtfInput);
			System.out.println(Date);
			DateTimeFormatter dtfOutputEng = DateTimeFormatter.ofPattern("MM/dd/uuuu", Locale.ENGLISH);
			formattedEng = dtfOutputEng.format(Date);
			System.out.println(formattedEng);

			String[] dateArray = formattedEng.split("/");
			String requiredMonth = dateArray[0];
			int month = Integer.parseInt(dateArray[0]);
			if (month < 10) {
				requiredMonth = dateArray[0].replace("0", "");
				resultedDate = requiredMonth + "/" + dateArray[1] + "/" + dateArray[2];
			}
			if (Integer.parseInt(dateArray[1]) < 10 && dateArray[1].length() == 2) {
				String requiredDay = dateArray[1].replace("0", "");
				resultedDate = requiredMonth + "/" + requiredDay + "/" + dateArray[2];
			}

		} catch (Exception e) {
			log(LogStatus.ERROR, "Expected Date Format is not Correct: " + date + " , Expected format: dd/MMM/yyyy",
					YesNo.Yes);
			sa.assertTrue(false, "Expected Date Format is not Correct: " + date + " , Expected format: dd/MMM/yyyy");
			e.getMessage();
		}

		return resultedDate;
	}

	/**
	 * @author Ankur Huria
	 * @param SDGGridName
	 */

	public void verifyFirstRecordInSDG(String sdgGridName, List<String> columnNames,
			List<String> expectedDataCorresspondToColumns) {

		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		List<WebElement> headerList = getSDGColumns(sdgGridName);
		List<String> columnDataText = headerList.stream().map(s -> s.getText()).collect(Collectors.toList()).stream()
				.map(t -> t.trim()).collect(Collectors.toList());
		if (!headerList.isEmpty()) {
			int i = 0;
			for (String columnName : columnNames) {
				int columnIndex = columnDataText.indexOf(columnName);
				String actualDataCorresspondToColumns = home
						.sdgGridColumnsDataOfFirstRow(sdgGridName.toString(), columnIndex + 2).getText();

				if (Pattern.matches("^\\d{2}\\/[a-zA-Z]{3}\\/\\d{4}$", expectedDataCorresspondToColumns.get(i))) {
					expectedDataCorresspondToColumns.set(i, dateFormatChange(expectedDataCorresspondToColumns.get(i)));
				}

				if (actualDataCorresspondToColumns.equals(expectedDataCorresspondToColumns.get(i))) {
					log(LogStatus.INFO, "Data Verified: " + actualDataCorresspondToColumns, YesNo.No);
				} else {

					log(LogStatus.ERROR, "Data Not Verified, Expected: " + expectedDataCorresspondToColumns.get(i)
							+ ", but Actual: " + actualDataCorresspondToColumns, YesNo.Yes);
					sa.assertTrue(false, "Data Not Verified, Expected: " + expectedDataCorresspondToColumns.get(i)
							+ ", but Actual: " + actualDataCorresspondToColumns);
				}
				i++;
			}
		} else {
			log(LogStatus.PASS,
					sdgGridName.toString() + " SDG Grid header cloumns list is not visible so cannot verify SDG Data",
					YesNo.Yes);
			sa.assertTrue(false,
					sdgGridName.toString() + " SDG Grid header cloumns list is not visible so cannot verify SDG Data");
		}

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

				else if (typesOfFields.get(i).equalsIgnoreCase("MultiPickList")) {
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

				else if (typesOfFields.get(i).equalsIgnoreCase("DatePicker")) {
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
						sa.assertTrue(false, "No " + label + " Provided:  " + values.get(i));
					}

				}

				else if (typesOfFields.get(i).equalsIgnoreCase("DatePickerCurrentDate")) {

					String value = CommonLib.getDateAccToTimeZone("GMT+5:30", "dd/MMM/yyyy");

					String[] date = value.split("/");

					if (click(driver, calendarInputBox(label, 30), label + " Input Box", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Click on " + label + " Calendar Input Box", YesNo.No);
						if (CommonLib.datePickerHandle(driver, monthInDatePicker(30),
								previousMonthButtonInDatePicker(30), label + " Picker", date[2], date[1], date[0])) {
							log(LogStatus.INFO, "Date has been Selected  " + value, YesNo.No);
						} else {
							sa.assertTrue(false, "Date has not been Selected  " + value);
							log(LogStatus.ERROR, "Date has not been Selected  " + value, YesNo.Yes);
						}
					} else {
						sa.assertTrue(false, "Not Able to Click on " + label + " Calendar input Box");
						log(LogStatus.ERROR, "Not Able to Click on " + label + " Calendar input Box", YesNo.Yes);
					}

				}

				else if (typesOfFields.get(i).equalsIgnoreCase("DatePickerFutureDate")) {

					String value = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "dd/MMM/yyyy",
							Integer.parseInt(values.get(i)));

					String[] date = value.split("/");

					if (click(driver, calendarInputBox(label, 30), label + " Input Box", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Click on " + label + " Calendar Input Box", YesNo.No);
						if (CommonLib.datePickerHandle(driver, monthInDatePicker(30),
								previousMonthButtonInDatePicker(30), label + " Picker", date[2], date[1], date[0])) {
							log(LogStatus.INFO, "Date has been Selected  " + value, YesNo.No);
						} else {
							sa.assertTrue(false, "Date has not been Selected  " + value);
							log(LogStatus.ERROR, "Date has not been Selected  " + value, YesNo.Yes);
						}
					} else {
						sa.assertTrue(false, "Not Able to Click on " + label + " Calendar input Box");
						log(LogStatus.ERROR, "Not Able to Click on " + label + " Calendar input Box", YesNo.Yes);
					}

				}

				else if (typesOfFields.get(i).equalsIgnoreCase("DropDown")) {

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

				else if (typesOfFields.get(i).equalsIgnoreCase("TextBox")) {

					if (CommonLib.sendKeys(driver, home.recordTextBox(label, 30), values.get(i), label + " Input Box",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, values.get(i) + " value has been Entered in label: " + label, YesNo.No);
					} else {
						sa.assertTrue(false, values.get(i) + " value has not been Entered in label: " + label);
						log(LogStatus.ERROR, values.get(i) + " value has not been Entered in label: " + label,
								YesNo.Yes);
					}
				} else {
					sa.assertTrue(false, "Type of Element for label: " + label
							+ " is not valid, Please Enter Correct Type Of Element");
					log(LogStatus.ERROR, "Type of Element for label: " + label
							+ " is not valid, Please Enter Correct Type Of Element", YesNo.Yes);

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

	/**
	 * @author Ankur Huria
	 * @param SDGName
	 * @param datas
	 */
	@SuppressWarnings("unused")
	public boolean createNewAffiliationThroughContactPage(String projectName, String actionButtonName, String startDate,
			String endDate, String firmToSelect, String roleToChosen, int timeOut) {
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		boolean flag = false;
		if (click(driver, buttonInMainContactPage(actionButtonName, timeOut), "Action Button " + actionButtonName,
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Button: " + actionButtonName + " on New Affiliation Form Page ", YesNo.No);

			String firmDropDownElements = "//ul[@aria-label='Recent Firms']//li//lightning-base-combobox-item/span/following-sibling::span/span[1]/span";
			if (CommonLib.dropDownHandle(driver, home.firmInputBox(30), firmDropDownElements, "Firm Input Box",
					firmToSelect)) {
				log(LogStatus.INFO, "Firm has been Selected  " + firmToSelect, YesNo.No);
			} else {
				sa.assertTrue(false, "Firm has not been Selected  " + firmToSelect);
				log(LogStatus.ERROR, "Firm has not been Selected  " + firmToSelect, YesNo.Yes);
			}

			List<WebElement> rolesData = chosenOrAvailableDataForLabel("Role", "Available");

			for (WebElement roledata : rolesData) {
				if (roledata.getText().equals(roleToChosen)) {
					if (click(driver, roledata, roleToChosen, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,
								"Click on " + roleToChosen + " Button of Label Role on New Affiliation Form Page ",
								YesNo.No);
						CommonLib.ThreadSleep(3000);

						if (click(driver, home.moveSectionToChosenOrAvailableButton("Role", "Chosen", 30),
								"MultiPickList Navigation Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,
									"Click on MultipickList Navigation Button of Label Role on New Affiliation Form Page",
									YesNo.No);

							List<WebElement> chosenRoleDatas = chosenOrAvailableDataForLabel("Role", "Chosen");
							if (!chosenRoleDatas.isEmpty()) {
								List<String> chosenRoleDatasText = chosenRoleDatas.stream().map(x -> x.getText())
										.collect(Collectors.toList());
								if (chosenRoleDatasText.contains(roleToChosen)) {

									log(LogStatus.INFO, "Value: " + roleToChosen + " has been Chosen ", YesNo.Yes);

								} else {
									sa.assertTrue(false, "Value: " + roleToChosen
											+ " has not been Chosen, So Not Able to Cotinue to Create Affiliation Contact in SDG: ");
									log(LogStatus.ERROR, "Value: " + roleToChosen
											+ " has not been Chosen, So Not Able to Cotinue to Create Affiliation Contact on New Affiliation Form Page",
											YesNo.Yes);
								}
							} else {
								sa.assertTrue(false, "Not Able to Chose the role " + roleToChosen
										+ " on on New Affiliation Form Page");
								log(LogStatus.ERROR,
										"Not Able to Chose the role " + roleToChosen + " on New Affiliation Form Page",
										YesNo.Yes);
							}

						} else {
							sa.assertTrue(false,
									"Not Able to Click on MultipickList Navigation Button of Label Role on New Affiliation Form Page");
							log(LogStatus.ERROR,
									"Not Able to Click on MultipickList Navigation Button of Label Role on New Affiliation Form Page",
									YesNo.Yes);
						}

					} else {
						sa.assertTrue(false,
								"Not Able to Click on Button Value so cannot move to Chosen of Label Role on New Affiliation Form Page");
						log(LogStatus.SKIP, "Not Able to Click on " + roleToChosen
								+ " Button Value so cannot move to Chosen of Label Role on New Affiliation Form Page",
								YesNo.Yes);
					}

				}
			}

			if (startDate != null || startDate != "") {
				String[] date = startDate.split("/");

				if (click(driver, calendarInputBox("Start Date", 30), "Start Date Input Box",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Click on Start Date Calendar Input Box", YesNo.No);
					if (CommonLib.datePickerHandle(driver, monthInDatePicker(30), previousMonthButtonInDatePicker(30),
							"Start Date Picker", date[2], date[1], date[0])) {
						log(LogStatus.INFO, "Date has been Selected  " + startDate, YesNo.No);
					} else {
						sa.assertTrue(false, "Date has not been Selected  " + startDate);
						log(LogStatus.ERROR, "Date has not been Selected  " + startDate, YesNo.Yes);
					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Start Date Calendar input Box");
					log(LogStatus.ERROR, "Not Able to Click on Start Date Calendar input Box", YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "No Start Date Provided:  " + startDate, YesNo.Yes);
			}

			if (endDate != null || endDate != "") {
				String[] date = endDate.split("/");

				if (click(driver, calendarInputBox("End Date", 30), "End Date Input Box", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Click on End Date Calendar Input Box", YesNo.No);
					if (CommonLib.datePickerHandle(driver, monthInDatePicker(30), previousMonthButtonInDatePicker(30),
							"Start Date Picker", date[2], date[1], date[0])) {
						log(LogStatus.INFO, "Date has been Selected  " + endDate, YesNo.No);
					} else {
						sa.assertTrue(false, "Date has not been Selected  " + endDate);
						log(LogStatus.ERROR, "Date has not been Selected  " + endDate, YesNo.Yes);
					}

				} else {
					sa.assertTrue(false, "Not Able to Click on End Date Calendar input Box");
					log(LogStatus.ERROR, "Not Able to Click on End Date Calendar input Box", YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "No End Date Provided:  " + endDate, YesNo.Yes);
			}

			if (click(driver, getRecordPageSettingSave(60), "Save Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Click on Save Button  ", YesNo.No);
				flag = true;
				ThreadSleep(5000);

			} else {
				sa.assertTrue(false,
						"Not Able to Click on Save Button Value so cannot create Firm on New Affiliation Form Page");
				log(LogStatus.SKIP,
						"Not Able to Click on Save Button Value so cannot create Firm on New Affiliation Form Page",
						YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "Not Able to Click on Button: " + actionButtonName + " on New Affiliation Form Page",
					YesNo.Yes);
			sa.assertTrue(false, "Not Able to Click on Button: " + actionButtonName + " on New Affiliation Form Page");

		}
		return flag;

	}

	/**
	 * @author Ankur Huria
	 * @param SDGName
	 * @param datas
	 */
	public boolean createReferralThroughContactPage(String projectName, String sdgName, String actionButtonName,
			String[][] sdgLabels, int timeOut) {
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		boolean flag = false;
		if (click(driver, buttonInMainContactPage(actionButtonName, timeOut), "Action Button " + actionButtonName,
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Button: " + actionButtonName + " on New Referral Form Page", YesNo.No);
			if (click(driver, nextButtonOfNewFirm(30), "nextButtonOfNewFirm ", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Button: Next", YesNo.No);

				if (home.enterValueAndDropDownForSDGForm(projectName, sdgLabels, action.SCROLLANDBOOLEAN, timeOut)) {
					log(LogStatus.INFO,
							"Successfully Enter values on " + actionButtonName + " of New Referral Form Page",
							YesNo.Yes);

					if (click(driver, getRecordPageSettingSave(60), "Save Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Click on Save Button on New Referral Form Page", YesNo.No);
						flag = true;
						ThreadSleep(5000);

					} else {
						sa.assertTrue(false,
								"Not Able to Click on Save Button Value so cannot create Firm on New Referral Form Page");
						log(LogStatus.SKIP,
								"Not Able to Click on Save Button Value so cannot create Firm on New Referral Form Page",
								YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR,
							"Not Able to Enter values on " + actionButtonName + " on New Referral Form Page",
							YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Clicked on Button: Next", YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on Button: " + actionButtonName + " on New Referral Form Page",
					YesNo.Yes);

		}
		return flag;

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

	/**
	 * @author Ankur Huria
	 * @param activityTimeLineTab
	 * @param labelValueAndTypes
	 */

	public void verifySDGRecord(String sdgGridName, String[] columnEqualValue,
			String columnNameBasedOnWhichRecordSearch) {

		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		Map<String, String> map = new HashMap<>();

		for (String pair : columnEqualValue) // iterate over the pairs
		{
			String[] entry = pair.split("="); // split the pairs to get key and value
			map.put(entry[0].trim(), entry[1].trim()); // add them to the hashmap and trim whitespaces
		}
		CommonLib.ThreadSleep(15000);

		List<WebElement> headerList = getSDGColumns(sdgGridName);
		List<String> columnText = headerList.stream().map(s -> s.getText()).collect(Collectors.toList()).stream()
				.map(t -> t.trim()).collect(Collectors.toList());

		if (columnText.contains(columnNameBasedOnWhichRecordSearch)) {

			int indexOfColumnToSearch = columnText.indexOf(columnNameBasedOnWhichRecordSearch);
			if (!sdgGridColumnData(sdgGridName, indexOfColumnToSearch + 2).isEmpty()) {

				List<String> columnDataText = sdgGridColumnData(sdgGridName, indexOfColumnToSearch + 2).stream()
						.map(s -> s.getText()).collect(Collectors.toList()).stream().map(t -> t.trim())
						.collect(Collectors.toList());

				if (columnDataText.contains(map.get(columnNameBasedOnWhichRecordSearch))) {

					int indexOfRowToVerifyRecord = columnDataText.indexOf(map.get(columnNameBasedOnWhichRecordSearch));

					if (!headerList.isEmpty()) {

						for (String columnName : map.keySet()) {

							if (columnText.contains(columnName)) {
								int columnIndex = columnText.indexOf(columnName);
								String actualDataCorresspondToColumns = sdgGridColumnsDataRowWise(
										sdgGridName.toString(), columnIndex + 2, indexOfRowToVerifyRecord + 1)
										.getText();

								if (Pattern.matches("^\\d{2}\\/[a-zA-Z]{3}\\/\\d{4}$", map.get(columnName))) {
									map.replace(columnName, dateFormatChange(map.get(columnName)));
								}

								if (actualDataCorresspondToColumns.equals(map.get(columnName))) {
									log(LogStatus.INFO,
											"-----Data Verified: " + actualDataCorresspondToColumns
													+ " correspond to column " + columnName + " for SDG: " + sdgGridName
													+ "-----",
											YesNo.No);
								} else {

									log(LogStatus.ERROR,
											"-----Data Not Verified, Expected: " + map.get(columnName)
													+ ", but Actual: " + actualDataCorresspondToColumns
													+ " correspond to column " + columnName + " for SDG: " + sdgGridName
													+ "-----",
											YesNo.Yes);
									sa.assertTrue(false,
											"-----Data Not Verified, Expected: " + map.get(columnName)
													+ ", but Actual: " + actualDataCorresspondToColumns
													+ " correspond to column " + columnName + " for SDG: " + sdgGridName
													+ "-----");
								}
							} else {
								log(LogStatus.ERROR, "Column: " + columnName + " not Present in SDG: " + sdgGridName,
										YesNo.Yes);
								sa.assertTrue(false, "Column: " + columnName + " not Present in SDG: " + sdgGridName);
							}

						}
					} else {
						log(LogStatus.PASS, sdgGridName.toString()
								+ " SDG Grid header cloumns list is not visible so cannot verify SDG Data for SDG: "
								+ sdgGridName, YesNo.Yes);
						sa.assertTrue(false, sdgGridName.toString()
								+ " SDG Grid header cloumns list is not visible so cannot verify SDG Data for SDG: "
								+ sdgGridName);
					}

				} else {
					log(LogStatus.ERROR,
							"Data: " + map.get(columnNameBasedOnWhichRecordSearch) + " is not Present in the Column "
									+ columnNameBasedOnWhichRecordSearch + " for SDG: " + sdgGridName,
							YesNo.Yes);
					sa.assertTrue(false,
							"Data: " + map.get(columnNameBasedOnWhichRecordSearch) + " is not Present in the Column "
									+ columnNameBasedOnWhichRecordSearch + " for SDG: " + sdgGridName);
				}

			} else {
				log(LogStatus.PASS, "No Data Present in the Column " + columnNameBasedOnWhichRecordSearch + " & Data: "
						+ map.get(columnNameBasedOnWhichRecordSearch) + " for SDG: " + sdgGridName, YesNo.Yes);
				sa.assertTrue(false, "No Data Present in the Column " + columnNameBasedOnWhichRecordSearch + " & Data: "
						+ map.get(columnNameBasedOnWhichRecordSearch) + " for SDG: " + sdgGridName);
			}

		}

		else {

			log(LogStatus.PASS,
					"Column from which record Search in SDG, does not match to actual Columns, Please Pass the Correct Column Name, Data Passed: "
							+ columnNameBasedOnWhichRecordSearch + " Columns present are " + columnText,
					YesNo.Yes);
			sa.assertTrue(false,
					"Column from which record Search in SDG, does not match to actual Columns, Please Pass the Correct Column Name, Data Passed: "
							+ columnNameBasedOnWhichRecordSearch + " Columns present are " + columnText);
		}

	}

	/**
	 * @author Ankur Huria
	 * @param activityTimeLineTab
	 * @param labelValueAndTypes
	 */
	public boolean verifySDGRecordRedirectToNewWindow(String sdgGridName, String columnNameInWhichRecordPresent,
			String recordNameToRedirect) {

		boolean flag = false;
		List<WebElement> headerList = getSDGColumns(sdgGridName);
		List<String> columnText = headerList.stream().map(s -> s.getText()).collect(Collectors.toList()).stream()
				.map(t -> t.trim()).collect(Collectors.toList());

		if (columnText.contains(columnNameInWhichRecordPresent)) {

			int indexOfColumnToSearch = columnText.indexOf(columnNameInWhichRecordPresent);
			if (!sdgGridColumnData(sdgGridName, indexOfColumnToSearch + 2).isEmpty()) {

				List<String> columnDataText = sdgGridColumnData(sdgGridName, indexOfColumnToSearch + 2).stream()
						.map(s -> s.getText()).collect(Collectors.toList()).stream().map(t -> t.trim())
						.collect(Collectors.toList());

				if (columnDataText.contains(recordNameToRedirect)) {

					int indexOfRowToVerifyRecord = columnDataText.indexOf(recordNameToRedirect);

					if (!headerList.isEmpty()) {

						if (columnText.contains(columnNameInWhichRecordPresent)) {
							int columnIndex = columnText.indexOf(columnNameInWhichRecordPresent);
							WebElement actualDataCorresspondToColumn = sdgGridColumnsDataLinkRowWise(
									sdgGridName.toString(), columnIndex + 2, indexOfRowToVerifyRecord + 1);

							if (CommonLib.getText(driver, actualDataCorresspondToColumn, recordNameToRedirect,
									action.BOOLEAN).equalsIgnoreCase(recordNameToRedirect)) {
								log(LogStatus.INFO, "Record Found: " + recordNameToRedirect, YesNo.No);

								if (CommonLib.clickUsingJavaScript(driver, actualDataCorresspondToColumn,
										"Record: " + recordNameToRedirect, action.SCROLLANDBOOLEAN)) {

									log(LogStatus.INFO, "Clicked on Record: " + recordNameToRedirect, YesNo.No);

									String parentID = CommonLib.switchOnWindow(driver);

									if (!"".equals(parentID) || parentID != null) {
										CommonLib.ThreadSleep(15000);
										CommonLib.refresh(driver);

										String xPath = "//div[contains(@class,'entityNameTitle')]/following-sibling::slot//lightning-formatted-text";
										WebElement ele = CommonLib.FindElement(driver, xPath,
												recordNameToRedirect + " heading", action.SCROLLANDBOOLEAN, 40);

										if (ele.getText().equalsIgnoreCase(recordNameToRedirect)) {

											log(LogStatus.INFO,
													"Header Verified : " + recordNameToRedirect
															+ " corresponding to link Text: " + recordNameToRedirect,
													YesNo.No);
											log(LogStatus.INFO,
													"Redirection is working properly on : " + recordNameToRedirect,
													YesNo.No);
											driver.close();
											driver.switchTo().window(parentID);
											flag = true;

										} else {
											log(LogStatus.ERROR, recordNameToRedirect + " Detail Page not Verified, "
													+ " Created Through SDG: " + sdgGridName, YesNo.Yes);
											sa.assertTrue(false,
													"Redirection is not working properly on: " + recordNameToRedirect
															+ " ,Expected: " + recordNameToRedirect + " but Actual: "
															+ ele.getText());
											log(LogStatus.ERROR,
													"Redirection is not working properly on: " + recordNameToRedirect
															+ " ,Expected: " + recordNameToRedirect + " but Actual: "
															+ ele.getText(),
													YesNo.No);
											driver.close();
											driver.switchTo().window(parentID);
										}

									} else {
										sa.assertTrue(false, "After, Click on Link Text: " + recordNameToRedirect
												+ " of SDG: " + sdgGridName + " , No new Window Open");
										log(LogStatus.ERROR, "After, Click on Link Text: " + recordNameToRedirect
												+ " of SDG: " + sdgGridName + " , No new Window Open", YesNo.Yes);

									}

								} else {
									sa.assertTrue(false,
											"Not Able to click on Record: " + recordNameToRedirect
													+ " So, not able to verify Page will redirect to "
													+ recordNameToRedirect + " detail Page");
									log(LogStatus.ERROR,
											"Not Able to click on Record: " + recordNameToRedirect
													+ " So, not able to verify Page will redirect to "
													+ recordNameToRedirect + " detail Page",
											YesNo.Yes);

								}

							}

							else {
								sa.assertTrue(false,
										"No Record Found: " + recordNameToRedirect
												+ " So, not able to verify Page will redirect to "
												+ recordNameToRedirect + " detail Page");
								log(LogStatus.ERROR,
										"No Record Found: " + recordNameToRedirect
												+ " So, not able to verify Page will redirect to "
												+ recordNameToRedirect + " detail Page",
										YesNo.Yes);
							}

						} else {
							log(LogStatus.ERROR,
									"Column: " + columnNameInWhichRecordPresent + " not Present in SDG: " + sdgGridName,
									YesNo.Yes);
							sa.assertTrue(false, "Column: " + columnNameInWhichRecordPresent + " not Present in SDG: "
									+ sdgGridName);
						}

					} else {
						log(LogStatus.PASS, sdgGridName.toString()
								+ " SDG Grid header cloumns list is not visible so cannot verify SDG Data for SDG: "
								+ sdgGridName, YesNo.Yes);
						sa.assertTrue(false, sdgGridName.toString()
								+ " SDG Grid header cloumns list is not visible so cannot verify SDG Data for SDG: "
								+ sdgGridName);
					}

				} else {
					log(LogStatus.ERROR, "Data: " + recordNameToRedirect + " is not Present in the Column "
							+ columnNameInWhichRecordPresent + " for SDG: " + sdgGridName, YesNo.Yes);
					sa.assertTrue(false, "Data: " + recordNameToRedirect + " is not Present in the Column "
							+ columnNameInWhichRecordPresent + " for SDG: " + sdgGridName);
				}

			} else {
				log(LogStatus.PASS, "No Data Present in the Column " + columnNameInWhichRecordPresent + " & Data: "
						+ recordNameToRedirect + " for SDG: " + sdgGridName, YesNo.Yes);
				sa.assertTrue(false, "No Data Present in the Column " + columnNameInWhichRecordPresent + " & Data: "
						+ recordNameToRedirect + " for SDG: " + sdgGridName);
			}

		}

		else {

			log(LogStatus.PASS,
					"Column from which record Search in SDG, does not match to actual Columns, Please Pass the Correct Column Name, Data Passed: "
							+ columnNameInWhichRecordPresent + " Columns present are " + columnText,
					YesNo.Yes);
			sa.assertTrue(false,
					"Column from which record Search in SDG, does not match to actual Columns, Please Pass the Correct Column Name, Data Passed: "
							+ columnNameInWhichRecordPresent + " Columns present are " + columnText);
		}

		return flag;
	}

	public ArrayList<String> verifyContactTierDetails(ArrayList<String> listViewName) {

		ArrayList<String> result = new ArrayList<String>();
		if (click(driver, getEditTierButton(30), "Edit Tier Button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked in EditTierButton", YesNo.No);
			if (click(driver, getTierDropdown(30), "TierDropdown", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Tier Dropdown", YesNo.No);

				List<String> list = new ArrayList<String>();
				List<WebElement> lists = CommonLib.FindElements(driver,
						"//label[text()='Tier']/parent::lightning-combobox//div[@role='listbox']//lightning-base-combobox-item//span//span",
						"TierList");
				if (lists.size() != 0) {
					for (int i = 0; i < lists.size(); i++) {
						WebElement element = lists.get(i);
						String getdata = CommonLib.getText(driver, element, "list", action.BOOLEAN);
						list.add(getdata);
					}
					Collections.sort(list);

				} else {
					log(LogStatus.ERROR, "Not Able to get the elements of Tier Field, So not able to Continue",
							YesNo.No);
					result.add("Not Able to get the elements of Tier Field, So not able to Continue");
				}
				System.out.println(list.size());
				System.out.println(listViewName.size());
				for (int i = 1; i < lists.size(); i++) {
					if (list.get(i).equals(listViewName.get(i))) {
						log(LogStatus.INFO, "Expected Record: " + list.get(i) + " is matched with Actual Record: "
								+ listViewName.get(i), YesNo.No);

					} else {
						log(LogStatus.ERROR, "Expected Record: " + list.get(i) + " is not matched with Actual Record: "
								+ listViewName.get(i), YesNo.No);
						result.add("Expected Record: " + list.get(i) + " is not matched with Actual Record: "
								+ listViewName.get(i));
					}
				}
			} else {
				log(LogStatus.INFO, "Could not clicked Tier Dropdown", YesNo.No);
				result.add("Could not clicked Tier Dropdown, So not able to coninue");

			}
		} else {
			log(LogStatus.INFO, "Could not clicked on EditTierButton", YesNo.Yes);
			result.add("Could not clicked on EditTierButton, So not able to continue");
		}

		return result;
	}

	/**
	 * @author Sahil Bansal
	 * @param projectName
	 * @param pageName
	 * @param legalName
	 * @description This is used to update legal name of contact
	 */
	public boolean UpdateLastName(String projectName, PageName pageName, String contactLastName) {

		if (clickOnShowMoreActionDownArrow(projectName, pageName, ShowMoreActionDropDownList.Edit, 30)) {
			log(LogStatus.INFO, "clicked on edit button", YesNo.No);

			if (sendKeys(driver, getContactLastName(projectName, 60), contactLastName, "Contact Last Name",
					action.BOOLEAN)) {
				ThreadSleep(2000);

			} else {
				appLog.error("Not able to enter last name");
				return false;
			}
		}

		if (click(driver, getNavigationTabSaveBtn(projectName, 60), "Save Button", action.SCROLLANDBOOLEAN)) {
			appLog.info("Clicked on save button");
			ThreadSleep(3000);
			if (getNavigationTabSaveBtn(projectName, 5) != null) {
				click(driver, getNavigationTabSaveBtn(projectName, 60), "save", action.BOOLEAN);
			}
			return true;
		} else {
			appLog.info("Not able to click on save button");
			log(LogStatus.INFO, "Not able to clicked on edit button so cannot Account Name ", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "Not able to clicked on edit button so cannot Account Name ");
		}

		return false;
	}

	public boolean createContactFromContactSectionOfAcuity(String salutation, String[][] labelAndValue) {
		boolean flag = false;

		if (click(driver, getAddContactIcon(30), "Add contact icon", action.SCROLLANDTHROWEXCEPTION)) {
			log(LogStatus.INFO, "clicked on add contact button", YesNo.Yes);
			if (salutation != null) {

			}
			if (labelAndValue != null) {
				for (String[] val : labelAndValue) {
					String labelName = val[0];
					String value = val[1];
					if (sendKeys(driver, getTextboxWithLabelname(labelName, 20), value, labelName + " textbox",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, value + " have been passed in field " + labelName, YesNo.Yes);
					} else {
						log(LogStatus.ERROR, "Not able to pass the value " + value + " in field " + labelName,
								YesNo.Yes);
						return false;
					}
				}

			}

			if (click(driver, getSaveButtonOnNewContactPopup(20), "save button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on save button", YesNo.Yes);
				if (ActivityTimeLineCreatedMsg(20) != null) {
					log(LogStatus.INFO, "The Contact has been created", YesNo.Yes);
					flag = true;
				} else {
					log(LogStatus.ERROR, "The Contact is not created", YesNo.Yes);
					return false;
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on save button", YesNo.Yes);
				return false;
			}

		} else {
			log(LogStatus.ERROR, "Not able clicked on add contact button", YesNo.Yes);
			return false;
		}

		return flag;
	}

}
