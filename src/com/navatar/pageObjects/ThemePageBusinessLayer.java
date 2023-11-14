package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.FindElement;
import static com.navatar.generic.CommonLib.ThreadSleep;
import static com.navatar.generic.CommonLib.click;
import static com.navatar.generic.CommonLib.clickUsingJavaScript;
import static com.navatar.generic.CommonLib.isDisplayed;
import static com.navatar.generic.CommonLib.log;
import static com.navatar.generic.CommonLib.refresh;
import static com.navatar.generic.CommonLib.sendKeys;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.scripts.AcuityTheme.ResearchDataContainer;
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
	public boolean navigateToTheme(String projectName, String tabName, String themeNameToNavigate,
			boolean themeNewWindowCloseOrNotThenSwitchParentWindow) {

		String parentId = null;
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		if (lp.clickOnTab(projectName, tabName)) {
			log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);

			if (CommonLib.sendKeysAndPressEnter(driver, themeSearchBox(20), themeNameToNavigate, "Theme Search Box ",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box", YesNo.No);

				if (recordInTableOfTheme(themeNameToNavigate, 5) != null) {
					log(LogStatus.INFO,
							"Verified Theme " + themeNameToNavigate + " is present there in tab: " + tabName, YesNo.No);

					if (CommonLib.clickUsingJavaScript(driver, recordInTableOfTheme(themeNameToNavigate, 5),
							"Theme Name: " + themeNameToNavigate, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on the Theme: " + themeNameToNavigate, YesNo.No);
						parentId = CommonLib.switchOnWindow(driver);
						if (parentId != null) {
							log(LogStatus.INFO, "Switched to New Window", YesNo.No);

						} else {
							log(LogStatus.ERROR, "Not Able to Switch to New Window", YesNo.No);
							return false;
						}

					} else {
						log(LogStatus.ERROR, "Not able to Click on the Theme: " + themeNameToNavigate, YesNo.No);
						return false;

					}

				} else {
					log(LogStatus.ERROR, "Theme " + themeNameToNavigate + " is not present there", YesNo.No);
					return false;

				}

			} else {
				log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box", YesNo.No);
				return false;

			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabName + " Tab", YesNo.No);
			return false;

		}

		if (themeNewWindowCloseOrNotThenSwitchParentWindow) {

			if (parentId != null) {
				driver.close();
				driver.switchTo().window(parentId);
			}
		}
		return true;

	}
	
	public boolean createAddToTheme(boolean navigateToRecordOrNot, boolean clickonPlusIconOrNot,
			boolean addToThemeFromActionButton, boolean SaveOrCancel,
			boolean themeNewWindowCloseOrNotThenSwitchParentWindow, PageName pageName, String projectName,
			String tabName, String themeName, String sectionName, String addToThemeObjectSelection, String recordName,
			ResearchDataContainer reasearchRecordFromAdvanced, String[] categoriesToSelectFromAdvanced,
			boolean includeAllContactsCheckbox, boolean SaveOrCancelFromAdvanced, boolean advanceWindowClosed,
			String errorMsgInReasearchAddToTheme) {
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		boolean flag = false;
		String parentId = null;
		String researchParentId = null;

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		if (navigateToRecordOrNot) {
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);

				if (CommonLib.sendKeysAndPressEnter(driver, themeSearchBox(20), themeName, "Theme Search Box ",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, themeName + " value has been passed in Theme Search Box", YesNo.No);

					if (recordInTableOfTheme(themeName, 5) != null) {
						log(LogStatus.INFO, "Verified Theme " + themeName + " Has Been Created", YesNo.No);

						if (CommonLib.clickUsingJavaScript(driver, recordInTableOfTheme(themeName, 5),
								"Theme Name: " + themeName, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on the Theme: " + themeName, YesNo.No);
							parentId = CommonLib.switchOnWindow(driver);
							if (parentId != null) {
								log(LogStatus.INFO, "Switched to New Window", YesNo.No);

							} else {
								log(LogStatus.ERROR, "Not Able to Switch to New Window", YesNo.No);
								return false;
							}

						} else {
							log(LogStatus.ERROR, "Not able to Click on the Theme: " + themeName, YesNo.No);
							return false;

						}

					} else {
						log(LogStatus.ERROR, "Theme " + themeName + " is present there", YesNo.No);
						return false;

					}

				} else {
					log(LogStatus.ERROR, themeName + " value is not passed in Theme Search Box", YesNo.No);
					return false;

				}

			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabName + " Tab", YesNo.No);
				return false;

			}

		}

		if (addToThemeFromActionButton && !clickonPlusIconOrNot) {

			if (BP.clickOnShowMoreActionDownArrow(projectName, pageName, ShowMoreActionDropDownList.Add_to_Theme, 10)) {
				log(LogStatus.INFO, "Clicked on Add To Theme Action Button", YesNo.No);
			} else {

				if (BP.clickOnShowMoreActionDownArrow(projectName, pageName, ShowMoreActionDropDownList.Add_to_Theme,
						20)) {
					log(LogStatus.INFO, "Clicked on Add To Theme Action Button", YesNo.No);
				}

				else {
					log(LogStatus.ERROR, "Not ABle to Click on Add To Theme Action Button", YesNo.No);
					return false;

				}

			}
		}

		if (clickonPlusIconOrNot && !addToThemeFromActionButton)

		{
			if (CommonLib.click(driver, themeGridsAddToTheme(sectionName, 10), "Theme Name: " + themeName,
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Plus Icon Button for Section: " + sectionName, YesNo.No);
			} else {
				log(LogStatus.ERROR, "Not Able to Click on Plus Icon Button for Account: " + sectionName, YesNo.No);
				return false;

			}

		}

		if (addToThemeObjectSelection != null && !"".equals(addToThemeObjectSelection)) {

			if (CommonLib.click(driver, addToThemeObjectSelectionButton(10), "addToThemeObjectSelectionButton",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Object Selector Button", YesNo.No);

				if (CommonLib.click(driver, addToThemeObjectSelection(addToThemeObjectSelection, 10),
						"addToThemeObjectSelection: " + addToThemeObjectSelection, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Object Selector Option: " + addToThemeObjectSelection, YesNo.No);
				} else {
					log(LogStatus.ERROR, "Not Able to Click on Object Selector Option: " + addToThemeObjectSelection,
							YesNo.No);
					return false;

				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on Object Selector Button", YesNo.No);
				return false;
			}

		}

		if (clickonPlusIconOrNot && !addToThemeFromActionButton && reasearchRecordFromAdvanced == null) {
			if (sendKeys(driver, addToThemePopUpSearchBox(20), recordName, "Theme Search Box", action.BOOLEAN)) {
				log(LogStatus.INFO, "Able to Pass the Value:  " + recordName, YesNo.No);

				if (CommonLib.clickUsingJavaScript(driver, addToThemePopUpSearchBoxDropDownValue(recordName, 20),
						"Dropdown Value: " + recordName, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Dropdown Value: " + recordName, YesNo.No);

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Dropdown Value: " + recordName, YesNo.No);
					return false;

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Pass the Value: " + recordName, YesNo.No);
				return false;
			}

		} else if (!clickonPlusIconOrNot && addToThemeFromActionButton && reasearchRecordFromAdvanced == null) {

			if (sendKeys(driver, addToThemePopUpSearchBox2(20), recordName, "Theme Search Box", action.BOOLEAN)) {
				log(LogStatus.INFO, "Able to Pass the Value:  " + recordName, YesNo.No);

				if (CommonLib.clickUsingJavaScript(driver, addToThemePopUpSearchBoxDropDownValue2(recordName, 20),
						"Dropdown Value: " + recordName, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Dropdown Value: " + recordName, YesNo.No);

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Dropdown Value: " + recordName, YesNo.No);
					return false;

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Pass the Value: " + recordName, YesNo.No);
				return false;
			}

		}

		else if (reasearchRecordFromAdvanced != null && clickonPlusIconOrNot && !addToThemeFromActionButton) {
			if (CommonLib.click(driver, advancedButtonOnTheme(10), "advancedButtonOnTheme", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Click on Advanced Button", YesNo.No);

				researchParentId = CommonLib.switchToWindowOpenNextToParentWindow(driver);

				if (AddKeywordsForAdvancedResearch(reasearchRecordFromAdvanced.value,
						reasearchRecordFromAdvanced.tabNames, reasearchRecordFromAdvanced.tabValue,
						reasearchRecordFromAdvanced.field, reasearchRecordFromAdvanced.operator,
						reasearchRecordFromAdvanced.values, reasearchRecordFromAdvanced.action,
						reasearchRecordFromAdvanced.timeout)) {
					log(LogStatus.INFO, "Advance Search has been applied", YesNo.No);

					CommonLib.ThreadSleep(3000);
					if (BP.clickOnShowMoreActionDownArrow(projectName, pageName,
							ShowMoreActionDropDownList.Add_to_Theme, 10)) {
						log(LogStatus.INFO, "Clicked on Add To Theme Action Button", YesNo.No);

						CommonLib.ThreadSleep(5000);
						String actualExpectedThemeName = CommonLib.getText(driver,
								existingThemeNameVerifyInAddToTheme(20), "Existing Theme Verify", action.BOOLEAN);
						if (actualExpectedThemeName != null) {
							if (themeName.equals(actualExpectedThemeName)) {

								log(LogStatus.INFO, "-----Existing Theme Name has been verifed and i.e.: "
										+ actualExpectedThemeName + "-----", YesNo.No);
								flag = true;
							} else {
								log(LogStatus.ERROR,
										"-----Existing Theme Name has not been verifed, Actual: "
												+ actualExpectedThemeName + " ,but Expected: " + themeName + "-----",
										YesNo.No);
								BaseLib.sa.assertTrue(false, "-----Existing Theme Name has not been verifed, Actual: "
										+ actualExpectedThemeName + " ,but Expected: " + themeName + "-----");
							}

						} else {
							log(LogStatus.ERROR, "Either Locator Change or Theme Popup is Not Open", YesNo.No);
							return false;

						}
						CommonLib.ThreadSleep(3000);
						if (errorMsgInReasearchAddToTheme != null) {
							if (CommonLib.click(driver, addToThemeFooterButton2("Save", 7),
									"addToThemeFooterButton2: Save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Click on Save Button", YesNo.No);

								String actualErrorMsg = CommonLib.getText(driver, addToThemeInResearchErrorMsg(10),
										"addToThemeInResearchErrorMsg", action.BOOLEAN);
								if (errorMsgInReasearchAddToTheme.equals(actualErrorMsg)) {
									log(LogStatus.INFO, "Error Msg has been verified: " + actualErrorMsg, YesNo.No);
									flag = true;
								} else {
									log(LogStatus.ERROR,
											"Error Msg has not Been Verified, Expected: "
													+ errorMsgInReasearchAddToTheme + " but Actual: " + actualErrorMsg,
											YesNo.No);
									BaseLib.sa.assertTrue(false, "Error Msg has not Been Verified, Expected: "
											+ errorMsgInReasearchAddToTheme + " but Actual: " + actualErrorMsg);
								}

							} else {
								log(LogStatus.ERROR, "Not ABle to Click on Save Button", YesNo.No);
								return false;

							}
						}
						CommonLib.ThreadSleep(3000);
						if (CommonLib.click(driver, advancedCollapsedExpandButtonInAddToTheme(10),
								"advancedCollapsedExpandButtonInAddToTheme", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Click on Advanced Button", YesNo.No);

							if (categoriesToSelectFromAdvanced != null) {

								if (categoriesToSelectFromAdvanced[0].equalsIgnoreCase("All Categories")) {

									CommonLib.ThreadSleep(2000);
									if (CommonLib.click(driver, allCategoriesCheckBoxOfAddToTheme(10),
											"allCategoriesCheckBoxOfAddToTheme", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Click on All Categories Checkbox", YesNo.No);

									} else {
										log(LogStatus.ERROR, "Not Able to Click on All Categories Checkbox", YesNo.No);
										return false;

									}

								} else {
									for (String categoryToSelectFromAdvanced : categoriesToSelectFromAdvanced) {

										CommonLib.ThreadSleep(1000);
										if (CommonLib.click(driver,
												objectOrRecordTypeCheckBoxInAddToTheme(categoryToSelectFromAdvanced,
														10),
												"objectOrRecordTypeCheckBoxInAddToTheme: "
														+ categoryToSelectFromAdvanced,
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Click on CheckBox of: " + categoryToSelectFromAdvanced,
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"Not ABle to Click on CheckBox of: " + categoryToSelectFromAdvanced,
													YesNo.No);
											BaseLib.sa.assertTrue(false, "Not ABle to Click on CheckBox of: "
													+ categoryToSelectFromAdvanced);
											return false;

										}
									}
								}

							}

							CommonLib.ThreadSleep(1000);
							if (includeAllContactsCheckbox) {

								if (CommonLib.click(driver, includeAllContactsCheckBox(10),
										"includeAllContactsCheckBox: ", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Click on CheckBox includeAllContactsCheckBox", YesNo.No);

								} else {
									log(LogStatus.ERROR, "Not Able to Click on CheckBox includeAllContactsCheckBox",
											YesNo.No);
									return false;

								}

							}

							CommonLib.ThreadSleep(1000);
							if (SaveOrCancelFromAdvanced) {

								if (CommonLib.click(driver, addToThemeFooterButton2("Save", 7),
										"addToThemeFooterButton2: Save", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Click on Save Button", YesNo.No);
									if (addToThemeInResearchSuccessMsg(10) != null) {
										log(LogStatus.INFO, "Success Msg Verified", YesNo.No);
										flag = true;
									} else {
										log(LogStatus.ERROR, "Success Msg Not Verified", YesNo.No);
										return false;

									}

								} else {
									log(LogStatus.ERROR, "Not ABle to Click on Save Button", YesNo.No);
									return false;

								}
							} else {
								if (CommonLib.click(driver, addToThemeFooterButton2("Cancel", 7),
										"addToThemeFooterButton2: Cancel", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Click on Cancel Button", YesNo.No);

									flag = true;

								} else {
									log(LogStatus.ERROR, "Not ABle to Click on Cancel Button", YesNo.No);
									return false;

								}
							}

						} else {
							log(LogStatus.ERROR, "Not ABle to Click on Advanced Button", YesNo.No);
							return false;

						}

					} else {
						log(LogStatus.ERROR, "Not ABle to Click on Add To Theme Action Button", YesNo.No);
						return false;

					}
				} else {
					log(LogStatus.ERROR, "Advance Search has not been applied", YesNo.No);
					return false;
				}

				if (researchParentId != null) {

					driver.close();
					driver.switchTo().window(researchParentId);
				}

			}

			else {
				log(LogStatus.ERROR, "Not able to Click on Advanced Button", YesNo.No);
				return false;

			}

		} else

		{

			log(LogStatus.ERROR, "Please Pass data for either Action Button True or Plus Icon Button True", YesNo.No);
			return false;
		}

		if (reasearchRecordFromAdvanced == null) {
			if (SaveOrCancel) {

				if (CommonLib.clickUsingJavaScript(driver, addToThemePopUpSaveButton(10),
						"Dropdown Value: " + recordName, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Save Button", YesNo.No);

					if (successMsg(10) != null) {
						log(LogStatus.INFO, "Success Msg is showing, So Add to Theme Created for record: " + recordName,
								YesNo.No);
						flag = true;
					} else {
						log(LogStatus.ERROR,
								"Success Msg is not showing, So Add to Theme is not Created for record: " + recordName,
								YesNo.No);
						return false;
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Save Button", YesNo.No);
					return false;

				}

			} else

			{
				if (CommonLib.clickUsingJavaScript(driver, addToThemeFooterCancelButton(10),
						"addToThemeFooterCancelButton", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Cancel Button", YesNo.No);

					CommonLib.ThreadSleep(3000);
					if (addToThemeHeader(5) == null) {
						log(LogStatus.INFO, "Add To Theme Header Popup is removed after click on Cancel Button",
								YesNo.No);
						flag = true;
					} else {
						log(LogStatus.ERROR, "Add To Theme Header Popup is still showing after click on Cancel Button",
								YesNo.No);
						return false;

					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Cancel Button", YesNo.No);
					return false;

				}
			}

		}

		if (themeNewWindowCloseOrNotThenSwitchParentWindow)

		{

			if (parentId != null) {
				driver.close();
				driver.switchTo().window(parentId);
			} else {
				driver.close();
				driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
			}
		}

		return flag;
	}
	
	public boolean AddKeywordsForAdvancedResearch(String value, String tabNames, String tabValue, String field,
			String operator, String values, action act, int timeout) {

		ResearchPageBusinessLayer research = new ResearchPageBusinessLayer(driver);

		String[] tabName = tabNames.split("<break>");
		String[] tabValues = tabValue.split("<break>");
		int tabCount = tabName.length;
		String[] fields = field.split("<break>");
		String[] operators = operator.split("<break>");
		String[] valueField = values.split("<break>");
		int fieldCount = fields.length;

		sendKeys(driver, research.getSearchByKeywordTextbox(timeout), value, "Search For Specific Textbox", act);
		log(LogStatus.INFO, "Able to send " + value + " to Search For Specific Textbox", YesNo.No);

		if (tabNames != null && !"".equals(tabNames)) {
			for (int k = 0; k < tabCount; k++) {
				if (k > 0) {
					click(driver, research.getSearchForSpecificAddOption(timeout), "Search For Specific For Add Option",
							act);
					log(LogStatus.INFO, "Able to Click on Add Option", YesNo.No);
				}
				int count = k + 1;

				click(driver, getSearchForSpecificDropdownButton(count, timeout), "Search For Specific Dropdown Button",
						act);
				ThreadSleep(2000);
				if (click(driver, getSearchForSpecificDropdown(tabName[k], timeout), "Search For Specific Dropdown",
						act)) {
					log(LogStatus.INFO, "Able to select " + tabName[k] + " to Search For Specific Dropdown", YesNo.No);
					ThreadSleep(2000);
					if (sendKeys(driver, getSearchForSpecificSearch(timeout), tabValues[k],
							"Search For Specific Textbox", act)) {

						log(LogStatus.INFO, "Able to send " + tabValues[k] + " to Search For Specific Textbox",
								YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not Able to send " + tabValues[k] + " to Search For Specific Textbox",
								YesNo.Yes);

						return false;
					}

					ThreadSleep(2000);
					if (click(driver, research.getValueForSpecificRecord(tabValues[k], timeout),
							"Select Value for Specific Record", act)) {
						log(LogStatus.INFO, "Able to Select Value: " + tabValues[k] + "to Search For Specific Textbox",
								YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"Not Able to Select Value: " + tabValues[k] + "to Search For Specific Textbox",
								YesNo.Yes);

						return false;
					}
				} else {
					log(LogStatus.ERROR, "Not able to select " + tabName[k] + " to Search For Specific Dropdown",
							YesNo.Yes);

					return false;
				}
			}
		}
		if (field != null && !"".equals(field)) {
			for (int j = 0; j < fieldCount; j++) {
				Integer position = j + 1;
				if (j >= 1) {
					ThreadSleep(2000);
					click(driver, getSearchForSpecificAddOption(timeout), "Search For Specific For Add Option", act);
					log(LogStatus.INFO, "Able to Click on Add Option", YesNo.No);
				}
				ThreadSleep(2000);
				if (sendKeys(driver, getSearchByFieldForFieldOption(position, timeout), fields[j],
						"Search By Field For Field Option", act)) {
					ThreadSleep(2000);
					click(driver, getValueForFieldParameter(fields[j], timeout), "Select Value for Field Parameter",
							act);
					log(LogStatus.INFO, "Able to select " + fields[j] + " to Search By Field For Field Option",
							YesNo.No);
					ThreadSleep(5000);

					if (click(driver, getSearchByFieldForOperatorOption(position, timeout),
							"Select Value for Field Parameter", act)) {
						log(LogStatus.INFO, "Able to Click on Operator Button", YesNo.No);
						ThreadSleep(2000);
						if (click(driver, getSearchByFieldForOperatorOptionValue(operators[j], timeout),
								"Select Value for Field Parameter", act)) {

							log(LogStatus.INFO,
									"Able to Select " + operators[j] + " to Search By Field For Operator Option",
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Not Able to Select " + operators[j] + " to Search By Field For Operator Option",
									YesNo.Yes);
							return false;
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Operator Button", YesNo.Yes);
						return false;
					}

					CommonLib.ThreadSleep(5000);
					if (sendKeys(driver, searchByFieldForValueOption(position, timeout), valueField[j],
							"Search By Field For Value Option", act)) {
						log(LogStatus.INFO, "Able to send " + valueField[j] + " to Search By Field For Value Option",
								YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"Not Able to send " + valueField[j] + " to Search By Field For Value Option",
								YesNo.Yes);
						return false;
					}

				} else {
					log(LogStatus.ERROR, "Not able to select " + fields[j] + " to Search For Specific Dropdown",
							YesNo.Yes);
					BaseLib.sa.assertTrue(false,
							"Not able to select " + fields[j] + " to Search For Specific Dropdown");
					return false;
				}
			}
		}
		ThreadSleep(5000);
		if (click(driver, research.getResearchButtonForAdvanced(timeout), "Research Button For Advanced", act)) {
			log(LogStatus.INFO, "Able to Click on Research Button For Advanced", YesNo.No);
			ThreadSleep(5000);

		} else {
			log(LogStatus.ERROR, "Not Able to click on Research Button For Advanced", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "Not Able to click on Research Button For Advanced");
			return false;
		}

		return true;
	}
	
	public List<String> verifyContactNameInThemeEmailList(String  contactName, String title,String  accountName
			,String  Firmtype,String  email) {
		List<String> result = new ArrayList<String>();
		WebElement ele = null;
		String xpath = "";
		if (!contactName.isEmpty() && !title.isEmpty() && !accountName.isEmpty()&& !Firmtype.isEmpty()&& !email.isEmpty()) {
			
				xpath = "//div[contains(@class,'dt-outer-container')]//th[@data-label='Name']//*[text()='" + contactName + "']/ancestor::tr//td[@data-label='Title']//*[text()='" + title + "']/ancestor::tr//td[@data-label='Firm']//*[text()='" + accountName + "']/ancestor::tr//td[@data-label='Firm Type']//*[text()='" + Firmtype + "']/ancestor::tr//td[@data-label='Email']//*[contains(text(),'" + email + "')]";
				ele = FindElement(driver, xpath, contactName + "Contact Name ", action.SCROLLANDBOOLEAN, 10);
				if (ele != null) {
					log(LogStatus.INFO, contactName + " : Contact is displaying in review target list",
							YesNo.No);
				} else {
					log(LogStatus.ERROR, contactName + " : Contact is not displaying in review target list",
							YesNo.Yes);
					result.add(contactName + " : Contact is not displaying in review target list");
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
	
	public boolean SearchforEmailProspects(String environment, String mode,PageName pageName,String fieldName, String operator, String value,String button) {
		WebElement ele= null;
		String xpath="";
		String[] splitedFieldName=fieldName.split(",");
		String [] splitedOperator=operator.split(",");
		String[] Splitedvalue=value.split(",");
		for(int i=0; i<splitedFieldName.length; i++) {
			if(fieldName!=null && operator!=null && !fieldName.isEmpty() && !operator.isEmpty()) {
				if(i<splitedFieldName.length-1) {
					ele=isDisplayed(driver,FindElement(driver, "//*[@title='Add Row']", "Add row button", action.BOOLEAN,10), "Visibility", 10, "Add row button");
					if(ele!=null) {
						if(click(driver, ele, "add row button", action.BOOLEAN)) {
							appLog.info("clicked on add row link");
						}else {
							appLog.error("Not able to click on add row button so cannot add new row and enter filter data "+splitedFieldName[i]+" "+splitedOperator[i]+" "+Splitedvalue[i]);
							return false;
						}
					}else {
						appLog.error("Not able find all row link so cannot add new row and enter filter data "+splitedFieldName[i]+" "+splitedOperator[i]+" "+Splitedvalue[i]);
						return false;
					}
				}
				ThreadSleep(2000);
				ele = FindElement(driver,"(//input[@name='name' and @placeholder='Select Field'])["+(i+1)+"]", "field text box", action.BOOLEAN,10);
				if(sendKeys(driver, ele, splitedFieldName[i], "field text box", action.BOOLEAN)) {
					ThreadSleep(5000);
					xpath="//span[@title='" + splitedFieldName[i] + "']";
					ele=FindElement(driver, xpath, "field auto complete text", action.BOOLEAN,30);
					if(ele!=null) {
						if(click(driver, ele, splitedFieldName[i]+" text", action.BOOLEAN)) {
							appLog.info("clicked on field name "+splitedFieldName[i]+" text box");
							ThreadSleep(2000);
							ele = FindElement(driver, "(//input[@name='name' and @placeholder='Select Field'])["+(i+1)+"]//ancestor::div[@class='headers']/following-sibling::div//button", "operator drop down button", action.BOOLEAN,5);
							if(click(driver, ele, "Drop down button", action.BOOLEAN)) {
								appLog.info("clicked on Drop down button");
								ThreadSleep(2000);
								ele=FindElement(driver, "(//input[@name='name' and @placeholder='Select Field'])["+(i+1)+"]//ancestor::div[@class='headers']/following-sibling::div//button/../following-sibling::div//span[@title='" + splitedOperator[i] +"']", "operator value", action.BOOLEAN,30);
								if(ele!=null) {
									if(click(driver, ele, "operator value", action.BOOLEAN)) {
										appLog.info("select operator value");
										ThreadSleep(2000);
								if(Splitedvalue[i]!=null && !Splitedvalue[i].isEmpty()) {
									ele = FindElement(driver, "(//input[@name='name' and @placeholder='Select Field'])["+(i+1)+"]//ancestor::div[@class='headers']/following-sibling::div//input", "Value text box", action.BOOLEAN,5);
									if(sendKeys(driver, ele, Splitedvalue[i], "Value text box", action.BOOLEAN)) {
										appLog.info("passed value in Value Text Box: "+Splitedvalue[i]);
									}else {
										appLog.error("Not able to pass value in Value text box: "+Splitedvalue[i]+" so cannot apply filter");
										return false;
									}
								}
							}else {
								appLog.error("Not able to select operator: "+splitedOperator[i]+" so cannot apply filter");
								return false;
							}
						}else {
							appLog.error("Not able to click on field "+splitedFieldName[i]+" so cannot apply filter");
							return false;
						}
					}else {
						appLog.error(splitedFieldName[i]+" is not visible in field auto complete text box so cannot apply filter.");
						return false;
					}
				}else {
					appLog.error("Not able to pass value in field auto complete text box : "+splitedFieldName[i]+" so cannot apply filter");
					return false;
				}
			}else {
				appLog.error("Field Name and Operator cannot be empty or null so cannot apply filter please pass value in field and operator");
				return false;
			}
				}
				}
		}
//		if(addFilterLogic!=null) {
//			ele=isDisplayed(driver,FindElement(driver, "//a[@id='pageid:frm:cmdlink']", "Add filter logic link", action.BOOLEAN,10), "Visibility", 10, "Add filter logic link");
//			if(ele!=null) {
//				if(click(driver, ele, "add row button", action.BOOLEAN)) {
//					appLog.info("clicked on add row link");
//					ThreadSleep(2000);
//					ele=isDisplayed(driver,FindElement(driver, "//input[@id='pageid:frm:textfilt']", "Add filter logic text box", action.BOOLEAN,10), "Visibility", 10, "Add filter logic text box");
//					if(ele!=null) {
//						if(sendKeys(driver, ele,addFilterLogic, "add filter logic text box", action.SCROLLANDBOOLEAN)) {
//							appLog.info("pass value in filter logic text box : "+addFilterLogic);
//							
//						}else {
//							appLog.error("Not able to pass value on add filter logic text box so cannot add filter logic "+addFilterLogic);
//							return false;
//						}
//					}else {
//						appLog.error("Not able find add filter logic text box so cannot add filter logic "+addFilterLogic);
//						return false;
//					}
//					
//				}else {
//					appLog.error("Not able to click on add filter logic so cannot add filter logic "+addFilterLogic);
//					return false;
//				}
//			}else {
//				appLog.error("Not able find add filter logic link so cannot add filter logic "+addFilterLogic);
//				return false;
//			}
//		}
		if(click(driver, themeEmailClearandApplyBtn(button,30), "search button", action.BOOLEAN)) {
			appLog.info("clicked on Search button successfully");
			return true;
		}else {
			appLog.error("Not able to click on search button so cannot apply filter");
		}
			
		
		return false;
	}
	
	public boolean selectEmailTemplateFromEmailContact(String folderName, String emailTemplateName) {
		WebElement ele=null;
		ele = FindElement(driver, "//button[@name='Folder']", "folder name drop down button", action.BOOLEAN,5);
		if(click(driver, ele, "Drop down button", action.BOOLEAN)) {
			appLog.info("clicked on Drop down button");
			ThreadSleep(2000);
			ele=FindElement(driver, "//span[@title='" + folderName + "'] ", "folder name value", action.BOOLEAN,30);
			if(ele!=null) {
				if(click(driver, ele, "folder name value", action.BOOLEAN)) {
					appLog.info("select folder name value");
					ThreadSleep(2000);
					ele=FindElement(driver, "//*[text()='" + emailTemplateName + "']/ancestor::tr//label ", "Email name radio button", action.BOOLEAN,30);
					if(ele!=null) {
						if(click(driver, ele, "Email name radio button", action.BOOLEAN)) {
							appLog.info("select Email name radio button");
							ThreadSleep(2000);
							if (CommonLib.click(driver, themeEmailNextbtn2(30), "theme Email Next btn2", action.BOOLEAN)) {
								log(LogStatus.INFO, "Clicked on theme Email Next btn2", YesNo.No);
							}else {
								appLog.error("Not able to Clicked on theme Email Next btn2");
								return false;
							}
						}else {
							appLog.error("Not able to select Email name radio button");
							return false;
						}
					}
					}else {
						appLog.error("Not able to select select folder name value");
						return false;
					}
			}
				}else {
					appLog.error("Not able to clicked on Drop down button");
					return false;
				}
		return true;
		
	}
	
	public boolean selectthemeEmailTemplateFromEmailContact(String folderName, String emailTemplateName) {
		WebElement ele=null;
		ele = FindElement(driver, "//button[@name='Folder']", "folder name drop down button", action.BOOLEAN,5);
		if(click(driver, ele, "Drop down button", action.BOOLEAN)) {
			appLog.info("clicked on Drop down button");
			ThreadSleep(2000);
			ele=FindElement(driver, "//span[@title='" + folderName + "'] ", "folder name value", action.BOOLEAN,30);
			if(ele!=null) {
				if(click(driver, ele, "folder name value", action.BOOLEAN)) {
					appLog.info("select folder name value");
					ThreadSleep(2000);
					ele=FindElement(driver, "//*[text()='" + emailTemplateName + "']/ancestor::tr//label ", "Email name radio button", action.BOOLEAN,30);
					if(ele==null) {
						appLog.info("email name is not present");
					}else {
						appLog.error("email name is present");
						return false;
					}
					}else {
						appLog.error("Not able to select select folder name value");
						return false;
					}
			}
				}else {
					appLog.error("Not able to clicked on Drop down button");
					return false;
				}
		return true;
		
	}
}
