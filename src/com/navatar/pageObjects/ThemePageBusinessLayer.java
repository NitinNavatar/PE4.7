package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.FindElement;
import static com.navatar.generic.CommonLib.ThreadSleep;
import static com.navatar.generic.CommonLib.click;
import static com.navatar.generic.CommonLib.clickUsingJavaScript;
import static com.navatar.generic.CommonLib.isDisplayed;
import static com.navatar.generic.CommonLib.log;
import static com.navatar.generic.CommonLib.refresh;
import static com.navatar.generic.CommonLib.sendKeys;
import static com.navatar.generic.CommonLib.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.TaggedName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.teamMemberNavigation;
import com.navatar.generic.EnumConstants.updateThemeButton;
import com.navatar.scripts.AcuityTheme.DataContainer;
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

											if (successMsg1(10) != null) {
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

			if (BP.clickOnShowMoreActionDownArrow(projectName, pageName, ShowMoreActionDropDownList.Add_To_Theme, 10)) {
				log(LogStatus.INFO, "Clicked on Add To Theme Action Button", YesNo.No);
			} else {

				if (BP.clickOnShowMoreActionDownArrow(projectName, pageName, ShowMoreActionDropDownList.Add_To_Theme,
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
//					if (BP.clickOnShowMoreActionDownArrow(projectName, pageName,
//							ShowMoreActionDropDownList.Add_To_Theme, 10)) {
//						log(LogStatus.INFO, "Clicked on Add To Theme Action Button", YesNo.No);
					
					if (CommonLib.click(driver, getAddtoThemeButton(10), "AddtoThemeButton", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Click on Add to Theme Button", YesNo.No);

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
						log(LogStatus.ERROR, "Not ABle to Click on Add to theme buttton", YesNo.No);
						return false;

					}
					
//					} else {
//						log(LogStatus.ERROR, "Not ABle to Click on Add To Theme Action Button", YesNo.No);
//						return false;
//
//					}
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

					if (successMsg1(10) != null) {
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
	
	/**
	 * @author Ankur Huria
	 * @param ExpectedButtonsOnPage
	 * @param ExpectedButtonsInDownArrowButton
	 * @return negativeResult
	 */
	public List<String> verifyButtonsOnAPageAndInDownArrowButtonInTheme(boolean navigateToRecordOrNot,
			String projectName, String tabName, String themeNameToNavigate, List<String> ExpectedButtonsOnPage,
			List<String> ExpectedButtonsInDownArrowButton, boolean themeNewWindowCloseOrNotThenSwitchFirstWindow,
			boolean themeNewWindowCloseOrNotThenSwitchParentWindow) {
		String parentId = null;

		List<String> negativeResult = new ArrayList<String>();

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		if (navigateToRecordOrNot) {
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);

				if (CommonLib.sendKeysAndPressEnter(driver, themeSearchBox(20), themeNameToNavigate,
						"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box", YesNo.No);

					if (recordInTableOfTheme(themeNameToNavigate, 5) != null) {
						log(LogStatus.INFO, "Verified Theme " + themeNameToNavigate + " Has Been Created", YesNo.No);

						if (CommonLib.clickUsingJavaScript(driver, recordInTableOfTheme(themeNameToNavigate, 5),
								"Theme Name: " + themeNameToNavigate, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on the Theme: " + themeNameToNavigate, YesNo.No);
							parentId = CommonLib.switchOnWindow(driver);
							if (parentId != null) {
								log(LogStatus.INFO, "Switched to New Window", YesNo.No);

							} else {
								log(LogStatus.ERROR, "Not Able to Switch to New Window", YesNo.No);
								negativeResult.add("Not Able to Switch to New Window");
							}

						} else {
							log(LogStatus.ERROR, "Not able to Click on the Theme: " + themeNameToNavigate, YesNo.No);
							negativeResult.add("Not able to Click on the Theme: " + themeNameToNavigate);

						}

					} else {
						log(LogStatus.ERROR, "Theme " + themeNameToNavigate + " is present there", YesNo.No);
						negativeResult.add("Theme " + themeNameToNavigate + " is present there");

					}

				} else {
					log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box", YesNo.No);
					negativeResult.add(themeNameToNavigate + " value is not passed in Theme Search Box");

				}

			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabName + " Tab", YesNo.No);
				negativeResult.add("Not able to click on " + tabName + " Tab");
			}

		}

		if (!ExpectedButtonsOnPage.isEmpty()) {

			if (ExpectedButtonsOnPage.size() != 1 && !ExpectedButtonsOnPage.get(0).equals("")) {
				List<String> themeOuterButtons = themeOuterButtons().stream().map(x -> x.getText().trim())
						.collect(Collectors.toList());

				if (!themeOuterButtons.isEmpty()) {
					log(LogStatus.INFO, "No. of Buttons Present on Page are: " + themeOuterButtons.size(), YesNo.No);

					int i = 0;
					if (themeOuterButtons.size() == ExpectedButtonsOnPage.size()) {
						log(LogStatus.INFO,
								"No. of Actual and Expected Buttons on Page are same, So Continue the Process",
								YesNo.No);

						for (String button : themeOuterButtons) {
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
										+ ExpectedButtonsOnPage + " & Actual: " + themeOuterButtons,
								YesNo.Yes);
						negativeResult.add(
								"No. of Expected and Actual Buttons on Page not matched, So not able to continue, Expected: "
										+ ExpectedButtonsOnPage + " & Actual: " + themeOuterButtons);
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
				if (downArrowButtonInTheme(20) != null) {
					log(LogStatus.INFO, "Down Arrow Button is Present", YesNo.No);
					if (click(driver, downArrowButtonInTheme(20), "DownArrowButton", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);
						CommonLib.ThreadSleep(2000);

						List<String> themeInnerButtons = themeInnerButtons().stream().map(x -> x.getText().trim())
								.collect(Collectors.toList());

						if (!themeInnerButtons.isEmpty()) {
							log(LogStatus.INFO,
									"No. of Buttons Present on DownArrow Button are: " + themeInnerButtons.size(),
									YesNo.No);

							int i = 0;
							if (themeInnerButtons.size() == ExpectedButtonsInDownArrowButton.size()) {
								log(LogStatus.INFO,
										"No. of Actual and Expected Buttons on DownArrowButton are same, So Continue the Process",
										YesNo.No);

								for (String button : themeInnerButtons) {
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
												+ ExpectedButtonsInDownArrowButton + " & Actual: " + themeInnerButtons,
										YesNo.Yes);
								negativeResult.add(
										"No. of Expected and Actual Buttons in DownArrow Button not matched, So not able to continue, Expected: "
												+ ExpectedButtonsInDownArrowButton + " & Actual: " + themeInnerButtons);
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

		if (themeNewWindowCloseOrNotThenSwitchFirstWindow) {
			driver.close();
			driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
		}

		if (themeNewWindowCloseOrNotThenSwitchParentWindow) {

			if (parentId != null) {
				driver.close();
				driver.switchTo().window(parentId);
			} else {
				driver.close();
				driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
			}
		}

		return negativeResult;
	}
	
	public boolean createCopyTheme(boolean copyAllInteractions, boolean navigateToRecordOrNot, boolean SaveOrCancel,
			boolean themeNewWindowCloseOrNotThenSwitchParentWindow, boolean copyThemeFromActionButton,
			boolean existingThemeRemove, PageName pageName, String projectName, String tabName,
			String themeNameToNavigate, String existingThemeVerifyRecordName, String existingThemeRecordName,
			String newNameOfThemeForCopy, String description, String errorMsg) {
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		boolean flag = false;
		String parentId = null;

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		if (navigateToRecordOrNot) {
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);

				if (CommonLib.sendKeysAndPressEnter(driver, themeSearchBox(20), themeNameToNavigate,
						"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box", YesNo.No);

					if (recordInTableOfTheme(themeNameToNavigate, 5) != null) {
						log(LogStatus.INFO, "Verified Theme " + themeNameToNavigate + " Has Been Created", YesNo.No);

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
						log(LogStatus.ERROR, "Theme " + themeNameToNavigate + " is present there", YesNo.No);
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

		}

		if (copyThemeFromActionButton) {

			if (BP.clickOnShowMoreActionDownArrow(projectName, pageName, ShowMoreActionDropDownList.Copy_Theme, 10))
				log(LogStatus.INFO, "Clicked on Add To Theme Action Button", YesNo.No);
		} else {
			log(LogStatus.ERROR, "Not ABle to Click on Add To Theme Action Button", YesNo.No);
			return false;

		}

		if (existingThemeVerifyRecordName != null) {
			String actualExpectedThemeName = CommonLib.getText(driver, existingThemeNameVerify(20),
					"Existing Theme Verify", action.BOOLEAN);
			if (actualExpectedThemeName != null) {
				if (existingThemeVerifyRecordName.equals(actualExpectedThemeName)) {

					log(LogStatus.INFO,
							"-----Existing Theme Name has been verifed and i.e.: " + actualExpectedThemeName + "-----",
							YesNo.No);

				} else {
					log(LogStatus.ERROR, "-----Existing Theme Name has not been verifed, Actual: "
							+ actualExpectedThemeName + " ,but Expected: " + existingThemeVerifyRecordName + "-----",
							YesNo.No);
					return false;
				}

			} else {
				log(LogStatus.ERROR, "Either Locator Change or Theme Popup is Not Open", YesNo.No);
				return false;
			}

		}

		if (existingThemeRemove) {
			if (CommonLib.click(driver, existingThemeRemoveButtton(10), "existingThemeRemoveButtton",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Existing Theme Remove Button", YesNo.No);
				CommonLib.ThreadSleep(2000);

				String actualExpectedThemeName = CommonLib.getAttribute(driver, existingThemeNameInput(20),
						"Existing Theme Verify", "value");
				if (actualExpectedThemeName != null) {
					if ("".equals(actualExpectedThemeName)) {

						log(LogStatus.INFO, "-----Existing Theme Name has been verifed and i.e.: "
								+ actualExpectedThemeName + "-----", YesNo.No);

					} else {
						log(LogStatus.ERROR, "-----Existing Theme Name has not been verifed, Actual: "
								+ actualExpectedThemeName + " ,but Expected: " + "" + "-----", YesNo.No);
						return false;
					}

				} else {
					log(LogStatus.ERROR, "Either Locator Change or Theme Popup is Not Open", YesNo.No);
					return false;
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on Existing Theme Remove Button", YesNo.No);
				return false;
			}
		}

		if (existingThemeRecordName != null) {

			if (sendKeys(driver, existingThemeNameInput(20), existingThemeRecordName, "Existing Theme Name",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Able to Pass the Value:  " + existingThemeRecordName, YesNo.No);

				if (CommonLib.clickUsingJavaScript(driver, existingThemeNameDropDown(existingThemeRecordName, 20),
						"Dropdown Value: " + existingThemeRecordName, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Dropdown Value: " + existingThemeRecordName, YesNo.No);

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Dropdown Value: " + existingThemeRecordName, YesNo.No);
					return false;

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Pass the Value: " + existingThemeRecordName, YesNo.No);
				return false;
			}
		}

		if (newNameOfThemeForCopy != null) {

			if (sendKeys(driver, themeNameInputBox(20), newNameOfThemeForCopy, "Theme Name", action.BOOLEAN)) {
				log(LogStatus.INFO, "Able to Pass the Value:  " + newNameOfThemeForCopy, YesNo.No);

			} else {
				log(LogStatus.ERROR, "Not Able to Pass the Value: " + newNameOfThemeForCopy, YesNo.No);
				return false;
			}
		}

		if (description != null) {

			if (sendKeys(driver, themeDescription(20), description, "Description", action.BOOLEAN)) {
				log(LogStatus.INFO, "Able to Pass the Value:  " + description, YesNo.No);

			} else {
				log(LogStatus.ERROR, "Not Able to Pass the Value: " + description, YesNo.No);
				return false;
			}
		}

		if (copyAllInteractions) {
			if (!CommonLib.isSelected(driver, copyAllInteractionCheckBox(5), "copyAllInteractionCheckBox")) {
				if (CommonLib.click(driver, copyAllInteractionCheckBox(10), "copyAllInteractionCheckBox",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Copy All Interaction CheckBox, It gets Checked", YesNo.No);
				} else {
					log(LogStatus.ERROR, "Not ABle to Click on Copy All Interaction CheckBox, So It not gets Checked",
							YesNo.No);
					return false;

				}
			}

		} else {

			if (CommonLib.isSelected(driver, copyAllInteractionCheckBox(5), "copyAllInteractionCheckBox")) {
				if (CommonLib.click(driver, copyAllInteractionCheckBox(10), "copyAllInteractionCheckBox",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Copy All Interaction CheckBox, It gets UnChecked", YesNo.No);
				} else {
					log(LogStatus.ERROR, "Not ABle to Click on Copy All Interaction CheckBox, So It not Ungets Checked",
							YesNo.No);
					return false;

				}
			}

		}

		if (SaveOrCancel && errorMsg == null) {

			if (CommonLib.click(driver, copyThemeSaveOrCancelButton("Save", 10),
					"copyThemeSaveOrCancelButton: " + "Save", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Save Button", YesNo.No);

				if (successMsg(10) != null) {
					log(LogStatus.INFO,
							"Success Msg is showing, So Copy Theme Created for record: " + newNameOfThemeForCopy,
							YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Success Msg is not showing, So Copy Theme is not Created for record: "
							+ newNameOfThemeForCopy, YesNo.No);
					return false;
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on Save Button", YesNo.No);
				return false;

			}

		} else if (SaveOrCancel && errorMsg != null) {

			if (CommonLib.click(driver, copyThemeSaveOrCancelButton("Save", 10),
					"copyThemeSaveOrCancelButton: " + "Save", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Save Button", YesNo.No);

			} else {
				log(LogStatus.ERROR, "Not Able to Click on Save Button", YesNo.No);
				return false;

			}

		}

		else

		{
			if (CommonLib.click(driver, copyThemeSaveOrCancelButton("Cancel", 10),
					"copyThemeSaveOrCancelButton: " + "Cancel", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Cancel Button", YesNo.No);

				CommonLib.ThreadSleep(3000);
				if (copyThemeHeader(5) == null) {
					log(LogStatus.INFO, "Copy Theme Header Popup is removed after click on Cancel Button", YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Copy Theme Header Popup is still showing after click on Cancel Button",
							YesNo.No);
					return false;

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on Cancel Button", YesNo.No);
				return false;

			}
		}

		if (errorMsg != null) {

			String actualErrorMsg = CommonLib.getText(driver, copyThemeErrorMsg(6), "Error Msg", action.BOOLEAN);
			if (actualErrorMsg.equals(errorMsg)) {
				log(LogStatus.INFO, "Error Msg Verified: " + errorMsg, YesNo.No);
				CommonLib.click(driver, copyThemeSaveOrCancelButton("Cancel", 10),
						"copyThemeSaveOrCancelButton: " + "Cancel", action.SCROLLANDBOOLEAN);
				log(LogStatus.INFO, "Clicked on Cancel Button", YesNo.No);
				flag = true;

			} else {
				log(LogStatus.ERROR, "Error Msg not Verified, Expected: " + errorMsg + " but Actual: " + actualErrorMsg,
						YesNo.No);
				return false;
			}
		}

		if (themeNewWindowCloseOrNotThenSwitchParentWindow) {

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
	
	public boolean UpdateTheme(boolean navigateToRecordOrNot, String tabName, String themeNameToNavigate,
			boolean clickonEditButton, String projectName, String themeName, String[][] labelAndValues,
			boolean themeNewWindowCloseOrNotThenSwitchParentWindow, int timeOut, updateThemeButton UpdateThemeButton) {
		boolean flag = false;

		String parentId = null;
		WebElement ele;
		ThreadSleep(2000);
		String label = "";
		String value = "";

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		if (navigateToRecordOrNot) {
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);

				if (CommonLib.sendKeysAndPressEnter(driver, themeSearchBox(20), themeNameToNavigate,
						"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box", YesNo.No);

					if (recordInTableOfTheme(themeNameToNavigate, 5) != null) {
						log(LogStatus.INFO, "Verified Theme " + themeNameToNavigate + " Has Been Created", YesNo.No);

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
						log(LogStatus.ERROR, "Theme " + themeNameToNavigate + " is present there", YesNo.No);
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

		}

		if (clickonEditButton) {
			if (clickOnShowMoreActionDownArrow(projectName, PageName.ThemesPage, ShowMoreActionDropDownList.Edit, 10)) {
				ThreadSleep(2000);
			} else {
				appLog.error("Not Able to Click on edit Button");
				return false;
			}
		}
		if (themeName != null) {
			ele = getLabelTextBox(projectName, PageName.ThemesPage.toString(), PageLabel.Theme_Name.toString(),
					timeOut);
			if (sendKeys(driver, ele, themeName, "Theme Name", action.BOOLEAN)) {
				appLog.info("Successfully Entered value on Theme Name TextBox : " + themeName);
			} else {
				appLog.error("Not Able to Entered value on Theme Name TextBox : " + themeName);
				return false;
			}
		}

		if (labelAndValues != null) {

			for (String[] labelAndValue : labelAndValues) {
				label = labelAndValue[0].replace("_", " ");
				value = labelAndValue[1];

				if (label.equals("Custom Theme Picklist")) {

					ele = getLabelTextBox(projectName, PageName.ThemesPage.toString(), label, timeOut);
					if (CommonLib.clickUsingJavaScript(driver, customThemePicklistComboxBox(10),
							"customThemePicklistComboxBox: ", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on " + label + "  combo box", YesNo.No);

						if (CommonLib.clickUsingJavaScript(driver, customThemePicklistComboxBoxDropDownValue(value, 10),
								"customThemePicklistComboxBoxDropDownValue: " + value, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Value: " + value, YesNo.No);
						} else {
							appLog.error("Not Able to Click on Value: " + value);
							return false;
						}
					} else {
						appLog.error("Not Able to Click on " + label + "  combo box");
						return false;
					}
				} else if (label.equals("Custom Theme TA") || label.equals("Custom Theme Text Area")
						|| label.equals("Description") || label.equals("Custom Theme LTA")
						|| label.equals("Custom Theme RTA") || label.equals("Custom Theme Description")) {
					ele = themeFieldOfTextArea(label, timeOut);
					if (sendKeys(driver, ele, value, "Label: " + label + " & Value: " + value, action.BOOLEAN)) {
						appLog.info("Successfully Entered value: " + value + " on label: " + label);
					} else {
						appLog.error("Not Successfully Entered value: " + value + " on label: " + label);
						return false;
					}
				}

				else {
					ele = getLabelTextBox(projectName, PageName.ThemesPage.toString(), label, timeOut);
					if (sendKeys(driver, ele, value, "Label: " + label + " & Value: " + value, action.BOOLEAN)) {
						appLog.info("Successfully Entered value: " + value + " on label: " + label);
					} else {
						appLog.error("Not Successfully Entered value: " + value + " on label: " + label);
						return false;
					}
				}

			}

		}

		if (UpdateThemeButton.equals(updateThemeButton.Save)) {
			ThreadSleep(2000);
			if (click(driver, updateThemeFooterButton(UpdateThemeButton.toString(), 5), "Button: " + UpdateThemeButton,
					action.SCROLLANDBOOLEAN)) {
				appLog.info("Click on " + UpdateThemeButton + " Button");
				flag = true;
				ThreadSleep(2000);
			} else {
				appLog.error("Not Able to Click on " + UpdateThemeButton + " Button");
				return false;
			}
		} else if (UpdateThemeButton.equals(updateThemeButton.Save_And_New)) {
			ThreadSleep(2000);
			if (click(driver, updateThemeFooterButton(UpdateThemeButton.toString(), 5), "Button: " + UpdateThemeButton,
					action.SCROLLANDBOOLEAN)) {
				appLog.info("Click on " + UpdateThemeButton + " Button");
				if (newThemeHeader(20) != null) {
					appLog.info("-----New Theme Popup is Open after click on " + UpdateThemeButton + "-----");
					if (click(driver, newThemeCloseIcon(5), "newThemeCloseIcon", action.SCROLLANDBOOLEAN)) {
						appLog.info("Click on Close Icon Of New Theme Popup");
						flag = true;
					} else {
						appLog.error("Not Able to Click on Close Icon Of New Theme Popup");
						return false;
					}

				} else {
					appLog.error("-----New Theme Popup is Not Open after click on " + UpdateThemeButton + "-----");
					return false;
				}
			} else {
				appLog.error("Not Able to Click on " + UpdateThemeButton + " Button");
				return false;
			}
		} else if (UpdateThemeButton.equals(updateThemeButton.Cancel)) {
			ThreadSleep(2000);
			if (click(driver, updateThemeFooterButton(UpdateThemeButton.toString(), 5), "Button: " + UpdateThemeButton,
					action.SCROLLANDBOOLEAN)) {
				appLog.info("Click on " + UpdateThemeButton + " Button");
				flag = true;
			} else {
				appLog.error("Not Able to Click on " + UpdateThemeButton + " Button");
				return false;
			}
		}

		if (themeNewWindowCloseOrNotThenSwitchParentWindow) {

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
	
	public boolean checkDescriptionAndTeamMember(String description, String teamMember,
			boolean themeNewWindowCloseOrNotThenSwitchFirstWindow) {
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String subTabName = RelatedTab.Acuity.toString();
		CommonLib.refresh(driver);
		if (BP.clicktabOnPage(subTabName)) {
			log(LogStatus.PASS, "Clicked on SubTab: " + subTabName, YesNo.No);

		} else {

			log(LogStatus.ERROR, "Not able to click on " + subTabName + " tab", YesNo.Yes);
			return false;
		}
		if (description != null) {
			if (themeDescriptionText(10) != null) {

				if (descriptionShowMoreLink(5) != null) {
					if (CommonLib.click(driver, descriptionShowMoreLink(5), "descriptionShowMoreLink: ",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Click on Show More Link of Desciption", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not Able to Click on Show More Link of Desciption", YesNo.Yes);
						return false;
					}
				}

				String actualDesc = CommonLib.getText(driver, themeDescriptionText(5), "themeDescriptionText",
						action.BOOLEAN);
				if (description.equals(actualDesc)) {
					log(LogStatus.INFO, "-----Description is matched, i.e.: " + actualDesc + "-----", YesNo.No);

				} else {
					log(LogStatus.ERROR, "-----Description is not matched, Expected: " + description + " but Actual: "
							+ actualDesc + "-----", YesNo.Yes);
					return false;
				}
			} else {
				log(LogStatus.ERROR, "Either Element not Found or Locator has been changed", YesNo.Yes);
				return false;

			}
		}

		if (teamMember != null) {
			if (themeTeamText(10) != null) {
				String teamText = CommonLib.getText(driver, themeTeamText(5), "themeTeamText", action.BOOLEAN);
				if (teamText.contains(teamMember)) {
					log(LogStatus.INFO, "-----Team Member is matched, i.e.: " + teamText + "-----", YesNo.No);

				} else {
					log(LogStatus.ERROR, "-----Team Member is not matched, Expected: " + teamMember + " but Actual: "
							+ teamText + "-----", YesNo.Yes);
					return false;
				}
			} else {
				log(LogStatus.ERROR, "Either Element not Found or Locator has been changed", YesNo.Yes);
				return false;

			}
		}

		if (themeNewWindowCloseOrNotThenSwitchFirstWindow) {
			driver.close();
			driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
		}

		return true;
	}
	
	public boolean createTeamMember(boolean navigateToRecordOrNot, String tabName, String themeNameToNavigate,
			String projectName, boolean themeNewWindowCloseOrNotThenSwitchParentWindow, String existingThemeName,
			String member, String role, String title, boolean SaveOrCancel, teamMemberNavigation TeamMemberNavigation,
			boolean dataVerify, boolean errorMsg, String expectedErrorMsg) {

		String parentId = null;

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		try {

			if (navigateToRecordOrNot) {
				if (lp.clickOnTab(projectName, tabName)) {
					log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);

					if (CommonLib.sendKeysAndPressEnter(driver, themeSearchBox(20), themeNameToNavigate,
							"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box",
								YesNo.No);

						if (recordInTableOfTheme(themeNameToNavigate, 5) != null) {
							log(LogStatus.INFO, "Verified Theme " + themeNameToNavigate + " Has Been Created",
									YesNo.No);

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
								log(LogStatus.ERROR, "Not able to Click on the Theme: " + themeNameToNavigate,
										YesNo.No);
								return false;

							}

						} else {
							log(LogStatus.ERROR, "Theme " + themeNameToNavigate + " is present there", YesNo.No);
							return false;

						}

					} else {
						log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
								YesNo.No);
						return false;

					}

				} else {
					log(LogStatus.ERROR, "Not able to click on " + tabName + " Tab", YesNo.No);
					return false;

				}

			}

			if (TeamMemberNavigation.equals(teamMemberNavigation.Action_Button)) {
				if (clickOnShowMoreActionDownArrow(projectName, PageName.ThemesPage,
						ShowMoreActionDropDownList.New_Team_Member, 10)) {

				} else {
					log(LogStatus.ERROR, "Not Able to Click on edit Button", YesNo.No);
					return false;
				}
			} else if (TeamMemberNavigation.equals(teamMemberNavigation.Sub_Tab)) {

				String subTabName = RelatedTab.Team.toString();
				if (BP.clicktabOnPage(subTabName)) {
					log(LogStatus.PASS, "Clicked on SubTab: " + subTabName, YesNo.No);
					if (click(driver, addTeamMemberPlusIconButton(10), "addTeamMemberPlusIconButton",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "click on addTeamMemberPlusIconButton", YesNo.No);

					} else {

						log(LogStatus.ERROR, "not able to click on addTeamMemberPlusIconButton", YesNo.Yes);
						return false;
					}

				} else {

					log(LogStatus.ERROR, "not able to click on " + subTabName + " tab", YesNo.Yes);
					return false;
				}
			}

			if (existingThemeName != null) {
				String actualThemeName = CommonLib.getText(driver, teamMemberThemeName(10), "ThemeName in TeamMember",
						action.BOOLEAN);
				if (existingThemeName.equals(actualThemeName)) {

					log(LogStatus.INFO, "Theme name is verified and i.e. " + actualThemeName, YesNo.No);
				} else {
					log(LogStatus.ERROR, "Theme name is not verify, Expected: " + existingThemeName + " bt Actual: "
							+ actualThemeName, YesNo.No);
					return false;
				}

			}

			if (member != null) {

				if (CommonLib.sendKeys(driver, teamMemberThemeMemberInputBox(10), member, "Theme Name: " + member,
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, member + " value has been passed in Theme Member", YesNo.No);

					if (CommonLib.click(driver, teamMemberThemeMemberSelection(member, 10),
							"teamMemberThemeMemberSelection: " + member, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Theme Member Selector Option: " + member, YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not Able to Click on Theme Member Selector Option: " + member, YesNo.No);
						return false;

					}
				} else {
					log(LogStatus.ERROR, member + " value is not passed in Theme Member", YesNo.No);
					return false;
				}

			}

			if (role != null) {

				if (CommonLib.click(driver, teamMemberRoleButton(10), "teamMemberRoleButton: ",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Theme Role Input Button", YesNo.No);

					if (CommonLib.click(driver, teamMemberRoleSelection(role, 10),
							"teamMemberThemeMemberSelection: " + role, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Theme Role Selector Option: " + role, YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not Able to Click on Theme Role Selector Option: " + role, YesNo.No);
						return false;

					}
				} else {
					log(LogStatus.ERROR, "Not ABle to Click on Theme Role Input Button", YesNo.No);
					return false;
				}

			}

			if (SaveOrCancel && !errorMsg) {
				if (CommonLib.click(driver, teamMemberButtonName("Save", 10), "teamMemberButtonName: " + "Save",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Save Button", YesNo.No);

					if (addTeamMemberSuccessMsg(10) != null) {
						log(LogStatus.INFO, "Success Msg is showing it means, Team Member Created", YesNo.No);

					} else {
						log(LogStatus.ERROR, "Success Msg is not showing it means, Team Member not Created", YesNo.No);
						return false;

					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Save Button", YesNo.No);
					return false;

				}
			} else if (SaveOrCancel && errorMsg) {
				if (CommonLib.click(driver, teamMemberButtonName("Save", 10), "teamMemberButtonName: " + "Save",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Save Button", YesNo.No);

					String actualErrorMsg = CommonLib.getText(driver, addTeamMemberErrorMsg(10),
							"Team Member Error Msg After Save", action.BOOLEAN);
					if (expectedErrorMsg.equals(actualErrorMsg)) {
						log(LogStatus.INFO, "Error Msg is verified and i.e.: " + actualErrorMsg, YesNo.No);
						CommonLib.click(driver, teamMemberButtonName("Cancel", 10), "teamMemberButtonName: " + "Cancel",
								action.SCROLLANDBOOLEAN);
						log(LogStatus.INFO, "Clicked on Cancel Button", YesNo.No);

					} else {
						log(LogStatus.ERROR, "Error Msg is not verified Actual: " + actualErrorMsg + " but Expected: "
								+ expectedErrorMsg, YesNo.No);

						CommonLib.click(driver, teamMemberButtonName("Cancel", 10), "teamMemberButtonName: " + "Cancel",
								action.SCROLLANDBOOLEAN);
						log(LogStatus.INFO, "Clicked on Cancel Button", YesNo.No);
						return false;

					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Save Button", YesNo.No);
					return false;

				}
			}

			else {
				if (CommonLib.click(driver, teamMemberButtonName("Cancel", 10), "teamMemberButtonName: " + "Cancel",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Cancel Button", YesNo.No);

					CommonLib.ThreadSleep(3000);
					if (teamMemberButtonName("Cancel", 3) == null) {
						log(LogStatus.INFO, "Pop of Theme Team Member i close after click on Cancel Button", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Pop of Theme Team Member is not close after click on Cancel Button",
								YesNo.No);
						return false;

					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on Cancel Button", YesNo.No);
					return false;

				}
			}

			if (dataVerify) {

				String subTabName = RelatedTab.Team.toString();
				if (BP.clicktabOnPage(subTabName)) {
					log(LogStatus.PASS, "Clicked on SubTab: " + subTabName, YesNo.No);
				} else {

					log(LogStatus.ERROR, "not able to click on " + subTabName + " tab", YesNo.Yes);
					return false;
				}

				ArrayList<Integer> tempNamesIndex = new ArrayList<Integer>();
				HashMap<String, Integer> tempRoles = new HashMap<String, Integer>();
				Integer loopCount = 0;
				if (AddTeamMemberNameData().contains(member)) {

					for (String AddTeamMemberNameData : AddTeamMemberNameData()) {

						if (AddTeamMemberNameData.equals(member)) {
							tempNamesIndex.add(loopCount);
						}

						loopCount++;
					}

				}

				if (tempNamesIndex.size() > 0 && !loopCount.equals(0)) {

					log(LogStatus.INFO, "Team Member Name Found: " + member, YesNo.No);

					for (Integer Index : tempNamesIndex) {
						Index = Index + 1;
						if (AddTeamMemberDataAsPerIndex(Index).get(3).contains(role)) {
							tempRoles.put(role, Index);
						}

					}

					if (tempRoles.size() == 1) {
						log(LogStatus.INFO, "Role Found: " + role + " of Team Member: " + member, YesNo.No);

						if (title != null) {
							if (AddTeamMemberDataAsPerIndex(tempRoles.get(role)).get(2).equals(title)) {
								log(LogStatus.INFO, "Title Found: " + title + " of Team Member: " + member, YesNo.No);

							} else {

								log(LogStatus.ERROR, "No Title Found: " + role + " of Team Member: " + member,
										YesNo.No);
								return false;
							}
						}

					} else {

						log(LogStatus.ERROR, "No Role Found: " + role + " of Team Member: " + member, YesNo.No);
						return false;

					}
				}

				else {
					log(LogStatus.ERROR, "Team Member Name not Found: " + member, YesNo.No);
					return false;
				}
			}

			if (themeNewWindowCloseOrNotThenSwitchParentWindow) {

				if (parentId != null) {
					driver.close();
					driver.switchTo().window(parentId);
				} else {
					driver.close();
					driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
				}
			}

		} catch (Exception e) {
			log(LogStatus.ERROR, "Exception: " + e.getMessage(), YesNo.Yes);
			return false;
		}

		return true;
	}
	
	public List<String> teamMemberDataVerify(boolean navigateToRecordOrNot, String projectName, String tabName,
			String themeNameToNavigate, String member, String title, String role,
			boolean themeNewWindowCloseOrNotThenSwitchParentWindow) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		ArrayList<Integer> tempNamesIndex = new ArrayList<Integer>();
		List<String> negativeResult = new ArrayList<String>();
		HashMap<String, Integer> tempRoles = new HashMap<String, Integer>();
		String parentId = null;

		if (navigateToRecordOrNot) {
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);

				if (CommonLib.sendKeysAndPressEnter(driver, themeSearchBox(20), themeNameToNavigate,
						"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box", YesNo.No);

					if (recordInTableOfTheme(themeNameToNavigate, 5) != null) {
						log(LogStatus.INFO, "Verified Theme " + themeNameToNavigate + " Has Been Created", YesNo.No);

						if (CommonLib.clickUsingJavaScript(driver, recordInTableOfTheme(themeNameToNavigate, 5),
								"Theme Name: " + themeNameToNavigate, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on the Theme: " + themeNameToNavigate, YesNo.No);
							parentId = CommonLib.switchOnWindow(driver);
							if (parentId != null) {
								log(LogStatus.INFO, "Switched to New Window", YesNo.No);

							} else {
								log(LogStatus.ERROR, "Not Able to Switch to New Window", YesNo.No);
								negativeResult.add("Not Able to Switch to New Window");
							}

						} else {
							log(LogStatus.ERROR, "Not able to Click on the Theme: " + themeNameToNavigate, YesNo.No);
							negativeResult.add("Not able to Click on the Theme: " + themeNameToNavigate);

						}

					} else {
						log(LogStatus.ERROR, "Theme " + themeNameToNavigate + " is present there", YesNo.No);
						negativeResult.add("Theme " + themeNameToNavigate + " is present there");

					}

				} else {
					log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box", YesNo.No);
					negativeResult.add(themeNameToNavigate + " value is not passed in Theme Search Box");

				}

			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabName + " Tab", YesNo.No);
				negativeResult.add("Not able to click on " + tabName + " Tab");
			}

		}

		String subTabName = RelatedTab.Team.toString();
		if (BP.clicktabOnPage(subTabName)) {
			log(LogStatus.PASS, "Clicked on SubTab: " + subTabName, YesNo.No);

		} else {

			log(LogStatus.ERROR, "Not able to click on " + subTabName + " tab", YesNo.Yes);
			negativeResult.add("Not able to click on " + subTabName + " tab");
		}

		Integer loopCount = 0;
		if (AddTeamMemberNameData().contains(member)) {

			for (String AddTeamMemberNameData : AddTeamMemberNameData()) {

				if (AddTeamMemberNameData.equals(member)) {
					tempNamesIndex.add(loopCount);
				}

				loopCount++;
			}

		}

		if (tempNamesIndex.size() > 0 && !loopCount.equals(0)) {

			log(LogStatus.INFO, "Team Member Name Found: " + member, YesNo.No);

			for (Integer Index : tempNamesIndex) {
				Index = Index + 1;
				if (AddTeamMemberDataAsPerIndex(Index).get(3).contains(role)) {
					tempRoles.put(role, Index);
				}

			}

			if (tempRoles.size() == 1) {
				log(LogStatus.INFO, "Role Found: " + role + " of Team Member: " + member, YesNo.No);

				if (title != null) {
					if (AddTeamMemberDataAsPerIndex(tempRoles.get(role)).get(2).equals(title)) {
						log(LogStatus.INFO, "Title Found: " + title + " of Team Member: " + member, YesNo.No);

					} else {

						log(LogStatus.ERROR, "No Title Found: " + role + " of Team Member: " + member, YesNo.No);
						negativeResult.add("No Title Found: " + role + " of Team Member: " + member);
					}
				}

			} else {

				log(LogStatus.ERROR, "No Role Found: " + role + " of Team Member: " + member, YesNo.No);
				negativeResult.add("No Role Found: " + role + " of Team Member: " + member);

			}
		}

		else {
			log(LogStatus.ERROR, "Team Member Name not Found: " + member, YesNo.No);
			negativeResult.add("Team Member Name not Found: " + member);
		}

		if (themeNewWindowCloseOrNotThenSwitchParentWindow) {

			if (parentId != null) {
				driver.close();
				driver.switchTo().window(parentId);
			} else {
				driver.close();
				driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
			}
		}

		return negativeResult;
	}
	
	public boolean deleteTheme(boolean navigateToRecordOrNot, String tabName, String themeNameToNavigate,
			String projectName, boolean themeNewWindowCloseOrNotThenSwitchParentWindow, int timeOut) {
		boolean flag = false;

		String parentId = null;
		ThreadSleep(2000);

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		if (navigateToRecordOrNot) {
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);

				if (CommonLib.sendKeysAndPressEnter(driver, themeSearchBox(20), themeNameToNavigate,
						"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box", YesNo.No);

					if (recordInTableOfTheme(themeNameToNavigate, 5) != null) {
						log(LogStatus.INFO, "Verified Theme " + themeNameToNavigate + " Has Been Created", YesNo.No);

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
						log(LogStatus.ERROR, "Theme " + themeNameToNavigate + " is present there", YesNo.No);
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

		}

		ThreadSleep(2000);
		if (clickOnShowMoreActionDownArrow(projectName, PageName.ThemesPage, ShowMoreActionDropDownList.Delete, 10)) {

			ThreadSleep(2000);
			if (click(driver, themeDeleteConfirmButton(30), "Delete Confirm Button", action.SCROLLANDBOOLEAN)) {
				appLog.info("Click on Delete Confirm Button");
				if (themeDeleteTastMsg(10) != null) {
					log(LogStatus.INFO, "Delete Theme Success Msg is display: " + themeNameToNavigate, YesNo.No);

				} else {
					appLog.error("Delete Theme Success Msg not display: " + themeNameToNavigate);
					return false;
				}

				flag = true;
				ThreadSleep(2000);
			} else {
				appLog.error("Not Able to Click on Delete Confirm Button");
				return false;
			}
		} else {
			appLog.error("Not Able to Click on Delete Button");
			return false;
		}

		if (themeNewWindowCloseOrNotThenSwitchParentWindow) {

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
	
	public boolean verifyThemePresence(boolean navigateToThemeTab, boolean themePresence, String themeName,
			String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		boolean flag = false;
		CommonLib.refresh(driver);
		if (navigateToThemeTab) {
			if (lp.clickOnTab(projectName, TabName.Themes)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Themes, YesNo.No);
			} else {
				log(LogStatus.ERROR, "Not able to click on " + TabName.Themes + " Tab", YesNo.No);
				return false;

			}
		}

		if (themePresence) {

			if (CommonLib.sendKeysAndPressEnter(driver, themeSearchBox(20), themeName, "Theme Search Box ",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, themeName + " value has been passed in Theme Search Box", YesNo.No);

				if (recordInTableOfTheme(themeName, 5) != null) {
					log(LogStatus.INFO, "Verified Theme " + themeName + " Has Been present", YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Theme " + themeName + " is not present there", YesNo.No);
					return false;

				}

			} else {
				log(LogStatus.ERROR, themeName + " value is not passed in Theme Search Box", YesNo.No);
				return false;

			}

		} else {

			if (CommonLib.sendKeysAndPressEnter(driver, themeSearchBox(20), themeName, "Theme Search Box ",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, themeName + " value has been passed in Theme Search Box", YesNo.No);

				if (recordInTableOfTheme(themeName, 5) == null) {
					log(LogStatus.INFO, "Verified Theme " + themeName + " Has not Been present", YesNo.No);

					flag = true;
				} else {
					log(LogStatus.ERROR, "Theme " + themeName + " is present there", YesNo.No);
					return false;

				}

			} else {
				log(LogStatus.ERROR, themeName + " value is not passed in Theme Search Box", YesNo.No);
				return false;

			}

		}

		return flag;

	}
	
	public boolean removeTeamMember(boolean navigateToRecordOrNot, String tabName, String themeNameToNavigate,
			String projectName, boolean themeNewWindowCloseOrNotThenSwitchParentWindow, String member, String role,
			boolean RemoveOrCancel, boolean removeAllRecord, String errorMsg) {

		String parentId = null;

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		try {

			if (navigateToRecordOrNot) {
				if (lp.clickOnTab(projectName, tabName)) {
					log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);

					if (CommonLib.sendKeysAndPressEnter(driver, themeSearchBox(20), themeNameToNavigate,
							"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box",
								YesNo.No);

						if (recordInTableOfTheme(themeNameToNavigate, 5) != null) {
							log(LogStatus.INFO, "Verified Theme " + themeNameToNavigate + " Has Been Created",
									YesNo.No);

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
								log(LogStatus.ERROR, "Not able to Click on the Theme: " + themeNameToNavigate,
										YesNo.No);
								return false;

							}

						} else {
							log(LogStatus.ERROR, "Theme " + themeNameToNavigate + " is present there", YesNo.No);
							return false;

						}

					} else {
						log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
								YesNo.No);
						return false;

					}

				} else {
					log(LogStatus.ERROR, "Not able to click on " + tabName + " Tab", YesNo.No);
					return false;

				}

			}

			String subTabName = RelatedTab.Team.toString();
			if (BP.clicktabOnPage(subTabName)) {
				log(LogStatus.PASS, "Clicked on SubTab: " + subTabName, YesNo.No);
				log(LogStatus.INFO, "click on " + subTabName + " tab", YesNo.No);
				if (click(driver, removeTeamMemberMinusIconButton(10), "removeTeamMemberMinusIconButton",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "click on removeTeamMemberMinusIconButton", YesNo.No);

				} else {

					log(LogStatus.ERROR, "not able to click on removeTeamMemberMinusIconButton", YesNo.Yes);
					return false;
				}

			} else {

				log(LogStatus.ERROR, "not able to click on " + subTabName + " tab", YesNo.Yes);
				return false;
			}

			if (removeAllRecord) {

				if (CommonLib.click(driver, teamMemberAllCheckBoxInput(10), "checBoxOfTeamMembers: ",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on All CHeckBox Button", YesNo.No);

				} else {
					log(LogStatus.ERROR, "Not ABle to Clicked on All CHeckBox Button", YesNo.No);
					return false;
				}

			} else if (member != null) {
				String[] members = member.split("<break>");
				String[] roles = role.split("<break>");

				Integer i = 0;
				for (String teamMember : members) {
					if (CommonLib.click(driver, checBoxOfTeamMembers(teamMember, roles[i], 10),
							"checBoxOfTeamMembers: ", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Theme Role Input Button", YesNo.No);

					} else {
						log(LogStatus.ERROR, "Not ABle to Click on Theme Role Input Button", YesNo.No);
						return false;
					}
					i++;
				}

			}

			if (RemoveOrCancel && errorMsg == null) {
				if (CommonLib.click(driver, teamMemberButton("Remove", 10), "teamMemberButton: " + "Remove",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Remove Button", YesNo.No);

					CommonLib.ThreadSleep(5000);

					if (removeAllRecord) {

						if (noRecordFoundThemeTeam(5) != null) {
							log(LogStatus.INFO, "No Record Found After Remove All records", YesNo.No);

						} else {
							log(LogStatus.ERROR, "Record(s) Found After Remove All records", YesNo.No);
							return false;
						}

					}

					else if (member != null) {
						String[] members = member.split("<break>");
						String[] roles = role.split("<break>");

						Integer i = 0;
						for (String teamMember : members) {
							if (checBoxOfTeamMembers(teamMember, roles[i], 2) == null) {
								log(LogStatus.INFO,
										"Team Member: " + teamMember + " with role :" + roles[i] + " has been removed",
										YesNo.No);

							} else {
								log(LogStatus.ERROR, "Team Member: " + teamMember + " with role :" + roles[i]
										+ " has not been removed", YesNo.No);
								return false;
							}
							i++;
						}

					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Remove Button", YesNo.No);
					return false;

				}
			} else if (RemoveOrCancel && errorMsg != null) {

				if (CommonLib.click(driver, teamMemberButton("Remove", 10), "teamMemberButton: " + "Remove",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Remove Button", YesNo.No);
					String actualErrorMsg = CommonLib.getText(driver, themeTeamErrorMsg(10), "themeTeamErrorMsg",
							action.BOOLEAN);
					if (errorMsg.equals(actualErrorMsg)) {
						log(LogStatus.INFO, "Error Msg has been verified: " + actualErrorMsg, YesNo.No);
						if (CommonLib.click(driver, teamMemberButton("Cancel", 10), "teamMemberButton: " + "Cancel",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Cancel Button", YesNo.No);

						} else {
							log(LogStatus.ERROR, "Not Able to Click on Cancel Button", YesNo.No);
							return false;

						}
					} else {
						log(LogStatus.ERROR, "Error Msg has not Been Verified, Expected: " + errorMsg + " but Actual: "
								+ actualErrorMsg, YesNo.No);
						BaseLib.sa.assertTrue(false, "Error Msg has not Been Verified, Expected: " + errorMsg
								+ " but Actual: " + actualErrorMsg);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Remove Button", YesNo.No);
					return false;

				}

			}

			else {
				if (CommonLib.click(driver, teamMemberButton("Cancel", 10), "teamMemberButton: " + "Cancel",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Cancel Button", YesNo.No);

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Cancel Button", YesNo.No);
					return false;

				}
			}

			if (themeNewWindowCloseOrNotThenSwitchParentWindow) {

				if (parentId != null) {
					driver.close();
					driver.switchTo().window(parentId);
				} else {
					driver.close();
					driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
				}
			}

		} catch (Exception e) {
			log(LogStatus.ERROR, "Exception: " + e.getMessage(), YesNo.Yes);
			return false;
		}

		return true;
	}
	
	public boolean verifyItsLeftCountAndGridCountSepratelyAndTotalSumWithAllCount(boolean navigateToRecordOrNot,
			String tabName, String themeNameToNavigate, String projectName,
			boolean themeNewWindowCloseOrNotThenSwitchParentWindow,
			HashMap<String, Integer> expectedSectionNameAndCount, boolean totalCount, boolean sectionWiseCount,
			boolean sectionCuntAndGridCountEqualOrNot, boolean clickSectionWiseAndCheck) {

		HashMap<String, Integer> gridNameAndCount = new HashMap<String, Integer>();
		HashMap<String, Integer> sideNavNameAndCount = new HashMap<String, Integer>();
		int status = 0;
		int status2 = 0;
		int status3 = 0;
		int status4 = 0;
		int loopCount = 0;
		int loopCount2 = 0;
		int loopCount3 = 0;
		int loopCount4 = 0;
		String parentId = null;
		try {

			LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

			if (navigateToRecordOrNot) {
				if (lp.clickOnTab(projectName, tabName)) {
					log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);

					if (CommonLib.sendKeysAndPressEnter(driver, themeSearchBox(20), themeNameToNavigate,
							"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box",
								YesNo.No);

						if (recordInTableOfTheme(themeNameToNavigate, 5) != null) {
							log(LogStatus.INFO, "Verified Theme " + themeNameToNavigate + " Has Been Created",
									YesNo.No);

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
								log(LogStatus.ERROR, "Not able to Click on the Theme: " + themeNameToNavigate,
										YesNo.No);
								return false;

							}

						} else {
							log(LogStatus.ERROR, "Theme " + themeNameToNavigate + " is present there", YesNo.No);
							return false;

						}

					} else {
						log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
								YesNo.No);
						return false;

					}

				} else {
					log(LogStatus.ERROR, "Not able to click on " + tabName + " Tab", YesNo.No);
					return false;

				}

			}

			HashMap<String, Integer> actualSideNavNameAndCount = new HashMap<String, Integer>();
			HashMap<String, Integer> actualGridNameAndCount = new HashMap<String, Integer>();
			List<String> sideNavCountExceptAllCategories = researchSideNavCountResultsExceptAllCategories().stream()
					.map(x -> x.getText().trim().replace("New Items", "").replace(":", "")
							.replaceAll("[\\t\\n\\r]+", "").trim())
					.collect(Collectors.toList());

			List<String> sideNavNamesWhichHasCountExceptAllCategories = researchSideNavLabelsWhichHasCountResultsExceptAllCategories()
					.stream().map(x -> x.getText().replace("New Items", "").replace(":", "")
							.replaceAll("[\\t\\n\\r]+", "").replaceAll("\\d", ""))
					.collect(Collectors.toList());

			List<String> researchResultsGridCountsInTheme = researchResultsGridCountsInTheme().stream()
					.map(x -> x.getText().trim()).collect(Collectors.toList());
			List<String> researchResultsGridNames = researchResultsGridCountsInTheme().stream()
					.map(x -> x.getText().trim().substring(0, x.getText().trim().indexOf(" (")))
					.collect(Collectors.toList());

			if (Integer.valueOf(sideNavCountExceptAllCategories.size())
					.equals(sideNavNamesWhichHasCountExceptAllCategories.size())) {
				Integer index = 0;
				for (String sideNavNameWhichHasCountExceptAllCategories : sideNavNamesWhichHasCountExceptAllCategories) {
					actualSideNavNameAndCount.put(sideNavNameWhichHasCountExceptAllCategories,
							Integer.valueOf(sideNavCountExceptAllCategories.get(index)));
					index++;
				}
			} else {
				log(LogStatus.ERROR, "Locator is Not Correct", YesNo.No);

				return false;
			}

			if (Integer.valueOf(researchResultsGridCountsInTheme.size()).equals(researchResultsGridNames.size())) {
				Integer index = 0;
				for (String researchResultsGridName : researchResultsGridNames) {
					actualGridNameAndCount.put(researchResultsGridName,
							Integer.parseInt(researchResultsGridCountsInTheme.get(index).substring(
									researchResultsGridCountsInTheme.get(index).indexOf("(") + 1,
									researchResultsGridCountsInTheme.get(index).indexOf(")"))));
					index++;
				}
			} else {
				log(LogStatus.ERROR, "Locator is Not Correct", YesNo.No);

				return false;
			}

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
			for (String gridCounts : researchResultsGridCountsInTheme) {
				gridTotalCount = Integer.parseInt(
						gridCounts.substring(gridCounts.indexOf("(") + 1, gridCounts.indexOf(")"))) + gridTotalCount;
				gridCountsList.add(
						Integer.parseInt(gridCounts.substring(gridCounts.indexOf("(") + 1, gridCounts.indexOf(")"))));

			}

			if (totalCount) {
				if (sideNavTotalCount == gridTotalCount) {
					log(LogStatus.INFO,
							"----Total Count of Grids and Side Nav are Same and i.e. " + gridTotalCount + "----",
							YesNo.No);
					log(LogStatus.INFO, "----Now Going to Verify with All----", YesNo.No);

					if (researchAllCategoriesCount == gridTotalCount) {
						log(LogStatus.INFO,
								"----Total Count of Grids and Side Nav and All Categories are Same and i.e. "
										+ researchAllCategoriesCount + "----",
								YesNo.No);
						status++;

					} else {

						log(LogStatus.ERROR,
								"----Total Count of Grids and Side Nav and All Categories are not Same, Total grid Count: "
										+ gridTotalCount + " and Total Nav Count: " + sideNavTotalCount
										+ "and Total All Categories Count " + researchAllCategoriesCount + "----",
								YesNo.Yes);

					}

				} else {

					log(LogStatus.ERROR, "----Total Count of Grids and Side Nav are not Same, Total grid Count: "
							+ gridTotalCount + " and Total Nav Count: " + sideNavTotalCount + "----", YesNo.Yes);

				}

			}

			if (sectionWiseCount) {

				for (String expectedSectionName : expectedSectionNameAndCount.keySet()) {

					if (actualSideNavNameAndCount.keySet().contains(expectedSectionName)) {

						if (actualSideNavNameAndCount.get(expectedSectionName)
								.equals(expectedSectionNameAndCount.get(expectedSectionName))) {
							log(LogStatus.INFO, "----Count of Side Nav is matched and i.e. "
									+ actualSideNavNameAndCount.get(expectedSectionName) + "----", YesNo.No);
							status2++;

						} else {
							log(LogStatus.ERROR,
									"----Count of Side Nav is not matched for Section: " + expectedSectionName
											+ ", Expected " + expectedSectionNameAndCount.get(expectedSectionName)
											+ ", Actual: " + actualSideNavNameAndCount.get(expectedSectionName)
											+ "----",
									YesNo.Yes);
						}
					} else {
						log(LogStatus.ERROR, "----Either No Section with Name: " + expectedSectionName
								+ " is present or No Count is showing ----", YesNo.Yes);

					}

					if (actualGridNameAndCount.keySet().contains(expectedSectionName)) {

						if (actualGridNameAndCount.get(expectedSectionName)
								.equals(expectedSectionNameAndCount.get(expectedSectionName))) {
							log(LogStatus.INFO, "----Count of Grid is matched and i.e. "
									+ actualGridNameAndCount.get(expectedSectionName) + "----", YesNo.No);
							status2++;
						} else {
							log(LogStatus.ERROR,
									"----Count of Grid is not matched for Section: " + expectedSectionName
											+ ", Expected " + expectedSectionNameAndCount.get(expectedSectionName)
											+ ", Actual: " + actualGridNameAndCount.get(expectedSectionName) + "----",
									YesNo.Yes);
						}
					} else {
						log(LogStatus.ERROR, "----Either No Section with Name: " + expectedSectionName
								+ " is present or No Count is showing ----", YesNo.Yes);

					}

					loopCount2++;

				}

			}

			if (sectionCuntAndGridCountEqualOrNot) {
				try {

					if (sideNavNamesWhichHasCountExceptAllCategories.size() == sideNavCountExceptAllCategoriesList
							.size() && sideNavCountExceptAllCategoriesList.size() == researchResultsGridNames.size()
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

							if (gridNameAndCount.get(gridName).equals(sideNavNameAndCount.get(navName))) {
								log(LogStatus.INFO, "Counts of Section and Grid for " + gridName + " matched and i.e.: "
										+ gridNameAndCount.get(gridName), YesNo.No);
								status3++;
							} else {

								log(LogStatus.ERROR,
										"Counts for " + gridName + " doesn't matched, GridCount: "
												+ gridNameAndCount.get(gridName) + " and SideNavCount: "
												+ sideNavNameAndCount.get(gridName),
										YesNo.Yes);

							}
							loopCount3++;
						}

					} else {

						log(LogStatus.ERROR,
								"Either of List Counts not match for Grids or From SideNav, So not able to validate the result count side by side of Grid and Side Nav",
								YesNo.Yes);

					}

				} catch (Exception e) {

					log(LogStatus.ERROR, "Exception occured: " + e.getMessage(), YesNo.No);
					sa.assertTrue(false, "Exception occured: " + e.getMessage());
					return false;
				}
			}

			if (clickSectionWiseAndCheck) {

				for (String expectedSectionName : expectedSectionNameAndCount.keySet()) {
					if (sideNavNamesWhichHasCountExceptAllCategories.contains(expectedSectionName)) {
						Integer Index = 0;
						Index = sideNavNamesWhichHasCountExceptAllCategories.indexOf(expectedSectionName);

						CommonLib.ThreadSleep(1000);
						if (clickUsingJavaScript(driver,
								researchSideNavLabelsWhichHasCountResultsExceptAllCategories().get(Index),
								"researchSideNavLabelsWhichHasCountResultsExceptAllCategories",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "click on Section: " + expectedSectionName, YesNo.No);

							List<String> researchResultsGridNamesAfterClick = researchResultsGridCountsInTheme()
									.stream()
									.map(x -> x.getText().trim().substring(0, x.getText().trim().indexOf(" (")))
									.collect(Collectors.toList());

							if (researchResultsGridNamesAfterClick.size() == 1
									&& researchResultsGridNamesAfterClick.contains(expectedSectionName)) {
								log(LogStatus.INFO, "-----Verified After Click on Section: " + expectedSectionName
										+ " , Grids coming: " + researchResultsGridNamesAfterClick + " only-----",
										YesNo.No);

								status4++;
							} else {

								log(LogStatus.ERROR,
										"-----After Click on Section: " + expectedSectionName + " , Grids coming: "
												+ researchResultsGridNamesAfterClick + " instaed of "
												+ expectedSectionName + " only-----",
										YesNo.Yes);

							}

						} else {

							log(LogStatus.ERROR, "Not Able to click on Section: " + expectedSectionName, YesNo.Yes);

						}

					} else {
						log(LogStatus.ERROR, "No Section Found named: " + expectedSectionName + " with Count",
								YesNo.Yes);

					}

					loopCount4++;
				}

			}

			if (themeNewWindowCloseOrNotThenSwitchParentWindow) {

				if (parentId != null) {
					driver.close();
					driver.switchTo().window(parentId);
				} else {
					driver.close();
					driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
				}
			}

			if (status > 0) {
				loopCount = loopCount + 1;
			}

			if (status == loopCount && loopCount2 * 2 == status2 && status3 == loopCount3 && status4 == loopCount4)
				return true;
			else
				return false;

		} catch (Exception e) {
			log(LogStatus.ERROR, "Exception: " + e.getMessage(), YesNo.Yes);
			return false;
		}
	}
	
	public ArrayList<String> verifyRedirectionOnClickOnThemeAndSortingInTaggedSection(
			boolean allRecordsRedirectionCheck, TaggedName taggedName, String themeTabNameOfPropertyFile,
			List<String> expectedThemeRecordsToCheckRedirection, boolean sortingCheck) {

		ArrayList<String> result = new ArrayList<String>();

		if (click(driver, getTaggedRecordName(taggedName.toString(), 30), taggedName.toString() + " tab",
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on " + taggedName + " tab name", YesNo.No);
			ThreadSleep(5000);

			if (allRecordsRedirectionCheck) {

				Integer loopCount = 0;
				List<String> actualThemeRecordsText = recordsNamesOnTaggedSection(taggedName.toString(), 30).stream()
						.map(x -> x.getText()).collect(Collectors.toList());
				for (WebElement themeRecord : recordsNamesOnTaggedSection(taggedName.toString(), 30)) {
					if (CommonLib.clickUsingJavaScript(driver, themeRecord, "Records on " + taggedName + " Tagged",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on record on " + taggedName + " tab", YesNo.No);

						String id = switchOnWindow(driver);
						if (id != null) {
							if (themeRecordPageName(themeTabNameOfPropertyFile, actualThemeRecordsText.get(loopCount),
									20) != null) {
								log(LogStatus.INFO,
										"The page is redirecting to " + actualThemeRecordsText.get(loopCount)
												+ " tab after click on Entity type of " + taggedName,
										YesNo.No);
							} else {
								log(LogStatus.ERROR,
										"The page is not redirecting to " + actualThemeRecordsText.get(loopCount)
												+ " tab after click on Entity type of " + taggedName,
										YesNo.No);
								result.add("The page is not redirecting to " + actualThemeRecordsText.get(loopCount)
										+ " tab after click on Entity type of " + taggedName);
							}
							driver.close();
							driver.switchTo().window(id);
						} else {
							log(LogStatus.ERROR,
									"The new tab is not opening after clicking on entity type of " + taggedName,
									YesNo.No);
							result.add("The new tab is not opening after clicking on entity type of " + taggedName);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on record on " + taggedName + " tab", YesNo.No);
						result.add("Not able to click on record on " + taggedName + " tab");
					}
					loopCount++;
				}

			} else if (!expectedThemeRecordsToCheckRedirection.isEmpty()) {
				for (String expectedThemeRecordToCheckRedirection : expectedThemeRecordsToCheckRedirection) {

					if (CommonLib.clickUsingJavaScript(
							driver, recordsNameOnTaggedSection(taggedName.toString(),
									expectedThemeRecordToCheckRedirection, 30),
							"Records on " + taggedName + " Tagged", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on record on " + taggedName + " tab", YesNo.No);

						String id = switchOnWindow(driver);
						if (id != null) {
							if (themeRecordPageName(themeTabNameOfPropertyFile, expectedThemeRecordToCheckRedirection,
									20) != null) {
								log(LogStatus.INFO,
										"The page is redirecting to " + expectedThemeRecordToCheckRedirection
												+ " tab after click on Entity type of " + taggedName,
										YesNo.No);
							} else {
								log(LogStatus.ERROR,
										"The page is not redirecting to " + expectedThemeRecordToCheckRedirection
												+ " tab after click on Entity type of " + taggedName,
										YesNo.No);
								result.add("The page is not redirecting to " + expectedThemeRecordToCheckRedirection
										+ " tab after click on Entity type of " + taggedName);
							}
							driver.close();
							driver.switchTo().window(id);
						} else {
							log(LogStatus.ERROR,
									"The new tab is not opening after clicking on entity type of " + taggedName,
									YesNo.No);
							result.add("The new tab is not opening after clicking on entity type of " + taggedName);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on record on " + taggedName + " tab", YesNo.No);
						result.add("Not able to click on record on " + taggedName + " tab");
					}

				}

			} else {
				log(LogStatus.ERROR,
						"Either Provide data or set true to the function to check redirection for all record",
						YesNo.No);
				result.add("Either Provide data or set true to the function to check redirection for all record");
			}

			if (sortingCheck) {
				List<String> actualThemeRecordsText = recordsNamesOnTaggedSection(taggedName.toString(), 30).stream()
						.map(x -> x.getText()).collect(Collectors.toList());
				if (actualThemeRecordsText.size() > 0) {

					List<String> expectedThemeRecordsText = new ArrayList<String>(actualThemeRecordsText);

					Collections.sort(expectedThemeRecordsText);
					Integer loopCount = 0;
					for (String expectedThemeRecordText : expectedThemeRecordsText) {

						if (expectedThemeRecordText.equals(actualThemeRecordsText.get(loopCount))) {
							log(LogStatus.INFO, "---Sorting in Ascending Matched and i.e.: "
									+ actualThemeRecordsText.get(loopCount) + "---", YesNo.No);
						}

						else {
							log(LogStatus.ERROR,
									"---Sorting in Ascending not Matched, Expected Data: " + expectedThemeRecordText
											+ " but Actual: " + actualThemeRecordsText.get(loopCount) + "---",
									YesNo.No);
							result.add("---Sorting in Ascending not Matched, Expected Data: " + expectedThemeRecordText
									+ " but Actual: " + actualThemeRecordsText.get(loopCount) + "---");
						}

						loopCount++;
					}

				} else {
					log(LogStatus.ERROR, "Either Locator Changed or No data Present under " + taggedName
							+ " of Tagged Section, So Not Able to Check Sorting", YesNo.No);
					result.add("Either Locator Changed or No data Present under " + taggedName
							+ " of Tagged Section, So Not Able to Check Sorting");
				}

			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + taggedName + " tab name", YesNo.No);
			result.add("Not able to click on " + taggedName + " tab name");
		}

		return result;
	}
	
	public void verifyColumnAscendingDescendingOrderInThemeGrids(String gridName, List<String> columnNames,
			List<String> dateColumns, List<String> amountColumns, List<String> pickListColumnAndValues,
			String FirstColumnAscYesOrNoByDefault) {

		List<WebElement> headerList = themeGridsHeaders(gridName);
		List<String> columnDataText = themeGridsHeadersText(gridName).stream().filter(x -> !x.equals(""))
				.collect(Collectors.toList());
		if (!headerList.isEmpty()) {

			if (themeGridsViewAllButton(gridName, 5) != null) {
				if (clickUsingJavaScript(driver, themeGridsViewAllButton(gridName, 5),
						gridName.toString() + " Grid header column", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.PASS, "Clicked on View All Button of Grid: " + gridName, YesNo.No);

				} else {
					log(LogStatus.PASS, "Not Able to Click on View All Button of Grid: " + gridName, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on View All Button of Grid: " + gridName);
				}
			}

			if (FirstColumnAscYesOrNoByDefault.equalsIgnoreCase("Yes")) {
				if (clickUsingJavaScript(driver, headerList.get(2), gridName + "  Grid header column",
						action.SCROLLANDBOOLEAN)) {
					ThreadSleep(6000);
				} else {
					log(LogStatus.PASS, "Not able to click on First Column of : " + gridName, YesNo.Yes);
					sa.assertTrue(false, "Not able to click on First Column of : " + gridName);
				}

			}

			for (String columnName : columnNames) {
				int columnIndex = columnDataText.indexOf(columnName) + 1;

				if (clickUsingJavaScript(driver, themeGridsSortingButton(gridName, 8),
						gridName.toString() + "  Grid header column", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.PASS, "Clicked on Sorted By Link of Grid: " + gridName, YesNo.No);
					
					if (clickUsingJavaScript(driver, themeGridsSortingButtonOptionsValue(gridName, columnName, 10),
							gridName.toString() + "  Grid header column", action.SCROLLANDBOOLEAN)) {
						ThreadSleep(6000);
						log(LogStatus.PASS, "Clicked on Header " + columnName + " Clomun " + (columnIndex)
								+ SortOrder.Decending, YesNo.No);

						if (!dateColumns.contains(columnName)) {
							if (!amountColumns.contains(columnName)) {

								if (pickListColumnAndValues.stream().filter(x -> x.contains(columnName))
										.collect(Collectors.toList()).size() == 0) {

									if (sdgGridColumnsDataList(gridName.toString(), columnIndex + 1).size() > 0) {
										if (CommonLib.checkSorting(driver, SortOrder.Decending,
												sdgGridColumnsDataList(gridName.toString(), columnIndex + 1))) {
											log(LogStatus.PASS,
													"Verified " + SortOrder.Decending + " Sorting on : "
															+ gridName.toString() + " for Column " + columnName,
													YesNo.No);
										} else {
											log(LogStatus.FAIL,
													SortOrder.Decending + " Sorting not working on : "
															+ gridName.toString() + " for Column " + columnName,
													YesNo.No);
											sa.assertTrue(false, SortOrder.Decending + " Sorting not working on : "
													+ gridName.toString() + " for Column " + columnName);
										}
									} else {
										log(LogStatus.FAIL,
												"Either Locator changed for Data of Column: " + columnName
														+ " or Column Not Present there named: " + columnName,
												YesNo.No);
										sa.assertTrue(false, "Either Locator changed for Data of Column: " + columnName
												+ " or Column Not Present there named: " + columnName);
									}

								} else {

									for (String pickListColumnAndValue : pickListColumnAndValues) {
										String[] pickListColumnAndValueArray = pickListColumnAndValue
												.split("<Section>");

										String[] values = pickListColumnAndValueArray[1].split("<break>");

										List<String> expectedPicklistColumnData = new ArrayList<String>();
										List<String> actualPicklistColumnData = new ArrayList<String>();
										List<String> customOrderOfPicklistColumnData = new ArrayList<String>();

										List<WebElement> actualPicklistColumnDataWebElements = sdgGridColumnsDataList(
												gridName.toString(), columnIndex);
										actualPicklistColumnData = actualPicklistColumnDataWebElements.stream()
												.map(pickList -> pickList.getText()).collect(Collectors.toList());

										expectedPicklistColumnData = actualPicklistColumnData;
										customOrderOfPicklistColumnData = Arrays.asList(values);
										Collections.reverse(customOrderOfPicklistColumnData);
										Collections.sort(expectedPicklistColumnData,
												Comparator.comparingInt(customOrderOfPicklistColumnData::indexOf));

										if (actualPicklistColumnData.size() > 0)

										{
											if (expectedPicklistColumnData.equals(actualPicklistColumnData)) {
												log(LogStatus.PASS,
														"Verified " + SortOrder.Decending + " Sorting on : "
																+ gridName.toString() + " for Column " + columnName
																+ " & i.e.: " + actualPicklistColumnData,
														YesNo.No);
											} else {
												log(LogStatus.FAIL,
														SortOrder.Decending + " Sorting not working on : "
																+ gridName.toString() + " for Column " + columnName
																+ ", Expected: " + expectedPicklistColumnData
																+ " & Actual: " + actualPicklistColumnData,
														YesNo.No);
												sa.assertTrue(false,
														SortOrder.Decending + " Sorting not working on : "
																+ gridName.toString() + " for Column " + columnName
																+ ", Expected: " + expectedPicklistColumnData
																+ " & Actual: " + actualPicklistColumnData);
											}
										} else {

											log(LogStatus.FAIL,
													"Not Able to Check Sorting of type: " + SortOrder.Decending
															+ " on : " + gridName.toString() + " for Column "
															+ columnName
															+ " as either there is no data or locator has been changed",
													YesNo.No);
											sa.assertTrue(false, "Not Able to Check Sorting of type: "
													+ SortOrder.Decending + " on : " + gridName.toString()
													+ " for Column " + columnName
													+ " as either there is no data or locator has been changed");
										}

									}
								}

							} else {

								List<Integer> expectedAmount = new ArrayList<Integer>();
								List<String> actualAmount = new ArrayList<String>();
								List<Integer> sortedActualAmount = new ArrayList<Integer>();
								List<WebElement> actualDateListWebElement = sdgGridColumnsDataList(gridName.toString(),
										columnIndex);
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
									log(LogStatus.PASS,
											"Verified " + SortOrder.Decending + " Sorting on : " + gridName.toString()
													+ " for Column " + columnName + columnName + " & i.e.: "
													+ sortedActualAmount,
											YesNo.No);
								} else {
									log(LogStatus.FAIL,
											SortOrder.Decending + " Sorting not working on : " + gridName.toString()
													+ " for Column " + columnName + " , Expected: " + expectedAmount
													+ " but Actual: " + sortedActualAmount,
											YesNo.No);
									sa.assertTrue(false,
											SortOrder.Decending + " Sorting not working on : " + gridName.toString()
													+ " for Column " + columnName + " , Expected: " + expectedAmount
													+ " but Actual: " + sortedActualAmount);
								}

							}

						}

						else {
							List<String> expectedDateListText = new ArrayList<String>();
							List<String> actualDateListText = new ArrayList<String>();
							List<WebElement> actualDateListWebElement = sdgGridColumnsDataList(gridName.toString(),
									columnIndex);
							actualDateListText = actualDateListWebElement.stream().map(date -> date.getText())
									.collect(Collectors.toList()).stream().filter(x -> !x.equals(""))
									.collect(Collectors.toList());
							if (actualDateListText.size() > 0) {
								expectedDateListText = dateToDescendingOrder(actualDateListWebElement);

								if (actualDateListText.equals(expectedDateListText)) {
									log(LogStatus.PASS,
											"Verified " + SortOrder.Decending + " Sorting on : " + gridName.toString()
													+ " for Column " + columnName + " & i.e.: " + actualDateListText,
											YesNo.No);
								} else {
									log(LogStatus.FAIL,
											SortOrder.Decending + " Sorting not working on : " + gridName.toString()
													+ " for Column " + columnName + " , Expected: "
													+ expectedDateListText + " but Actual: " + actualDateListText,
											YesNo.No);
									sa.assertTrue(false,
											SortOrder.Decending + " Sorting not working on : " + gridName.toString()
													+ " for Column " + columnName + " , Expected: "
													+ expectedDateListText + " but Actual: " + actualDateListText);
								}

							}

							else {
								log(LogStatus.FAIL,
										SortOrder.Decending + " Sorting not checked on : " + gridName.toString()
												+ " for Column " + columnName
												+ ", Either No Data Present there or No Column Present there",
										YesNo.No);
								sa.assertTrue(false,
										SortOrder.Decending + " Sorting not checked on : " + gridName.toString()
												+ " for Column " + columnName
												+ ", Either No Data Present there or No Column Present there");
							}

						}
					} else {
						log(LogStatus.PASS, "Not able to click on " + gridName.toString() + "  Grid header "
								+ columnName + " so cannot check Sorting " + SortOrder.Decending, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on " + gridName.toString() + "  Grid header "
								+ columnName + " so cannot check Sorting " + SortOrder.Decending);
					}
				} else {
					log(LogStatus.PASS, "Not Able to Click on Sorted By Link of Grid: " + gridName
							+ " so cannot check Sorting " + SortOrder.Decending, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Sorted By Link of Grid: " + gridName
							+ " so cannot check Sorting " + SortOrder.Decending);
				}

				if (clickUsingJavaScript(driver, themeGridsSortingButton(gridName, 8),
						gridName.toString() + "  Grid header column", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.PASS, "Clicked on Sorted By Link of Grid: " + gridName, YesNo.No);

					if (clickUsingJavaScript(driver, themeGridsSortingButtonOptionsValue(gridName, columnName, 10),
							gridName.toString() + "  Grid header column " + columnName, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "Clicked on Header " + columnName + " Column no. " + (columnIndex)
								+ " for " + SortOrder.Assecending, YesNo.No);
						ThreadSleep(6000);

						if (!dateColumns.contains(columnName)) {

							if (!amountColumns.contains(columnName)) {
								if (pickListColumnAndValues.stream().filter(x -> x.contains(columnName))
										.collect(Collectors.toList()).size() == 0) {
									if (sdgGridColumnsDataList(gridName.toString(), columnIndex).size() > 0) {
										if (CommonLib.checkSorting(driver, SortOrder.Assecending,
												sdgGridColumnsDataList(gridName.toString(), columnIndex))) {
											log(LogStatus.PASS,
													"Verified " + SortOrder.Assecending + " Sorting on : "
															+ gridName.toString() + " for Column " + columnName,
													YesNo.No);
										} else {
											log(LogStatus.FAIL,
													SortOrder.Assecending + " Sorting not working on : "
															+ gridName.toString() + " for Column " + columnName,
													YesNo.No);
											sa.assertTrue(false, SortOrder.Assecending + " Sorting not working on : "
													+ gridName.toString() + " for Column " + columnName);
										}

									} else {
										log(LogStatus.FAIL,
												"Either Locator changed for Data of Column: " + columnName
														+ " or Column Not Present there named: " + columnName,
												YesNo.No);
										sa.assertTrue(false, "Either Locator changed for Data of Column: " + columnName
												+ " or Column Not Present there named: " + columnName);
									}
								} else {

									for (String pickListColumnAndValue : pickListColumnAndValues) {
										String[] pickListColumnAndValueArray = pickListColumnAndValue
												.split("<Section>");

										String[] values = pickListColumnAndValueArray[1].split("<break>");

										List<String> expectedPicklistColumnData = new ArrayList<String>();
										List<String> actualPicklistColumnData = new ArrayList<String>();
										List<String> customOrderOfPicklistColumnData = new ArrayList<String>();

										List<WebElement> actualPicklistColumnDataWebElements = sdgGridColumnsDataList(
												gridName.toString(), columnIndex);
										actualPicklistColumnData = actualPicklistColumnDataWebElements.stream()
												.map(pickList -> pickList.getText()).collect(Collectors.toList());
										expectedPicklistColumnData = actualPicklistColumnData;

										customOrderOfPicklistColumnData = Arrays.asList(values);
										Collections.sort(expectedPicklistColumnData,
												Comparator.comparingInt(customOrderOfPicklistColumnData::indexOf));

										if (actualPicklistColumnData.size() > 0)

										{
											if (expectedPicklistColumnData.equals(actualPicklistColumnData)) {
												log(LogStatus.PASS,
														"Verified " + SortOrder.Assecending + " Sorting on : "
																+ gridName.toString() + " for Column " + columnName
																+ " & i.e.: " + actualPicklistColumnData,
														YesNo.No);
											} else {
												log(LogStatus.FAIL, SortOrder.Assecending + " Sorting not working on : "
														+ gridName.toString() + " for Column " + columnName

														, YesNo.No);
												sa.assertTrue(false,
														SortOrder.Assecending + " Sorting not working on : "
																+ gridName.toString() + " for Column " + columnName
																+ ", Expected: " + expectedPicklistColumnData
																+ " & Actual: " + actualPicklistColumnData);
											}
										} else {

											log(LogStatus.FAIL,
													"Not Able to Check Sorting of type: " + SortOrder.Assecending
															+ " on : " + gridName.toString() + " for Column "
															+ columnName
															+ " as either there is no data or locator has been changed",
													YesNo.No);
											sa.assertTrue(false, "Not Able to Check Sorting of type: "
													+ SortOrder.Assecending + " on : " + gridName.toString()
													+ " for Column " + columnName
													+ " as either there is no data or locator has been changed");
										}

									}
								}

							} else {

								List<Integer> expectedAmount = new ArrayList<Integer>();
								List<String> actualAmount = new ArrayList<String>();
								List<Integer> sortedActualAmount = new ArrayList<Integer>();
								List<WebElement> actualDateListWebElement = sdgGridColumnsDataList(gridName.toString(),
										columnIndex);
								actualAmount = actualDateListWebElement.stream().map(date -> date.getText())
										.collect(Collectors.toList()).stream().filter(x -> !x.equals(""))
										.collect(Collectors.toList());

								for (String val : actualAmount) {
									String[] splitedAmount = val.split("[.]", 0);
									String amount = splitedAmount[0].replace("$", "").replace(",", "");
									int amou = Integer.parseInt(amount);
									sortedActualAmount.add(amou);
								}

								if (actualDateListWebElement.size() > 0) {
									expectedAmount = amountToAscendingOrder(actualDateListWebElement);

									if (sortedActualAmount.equals(expectedAmount)) {
										log(LogStatus.PASS,
												"Verified " + SortOrder.Assecending + " Sorting on : "
														+ gridName.toString() + " for Column " + columnName
														+ " & i.e.: " + sortedActualAmount,
												YesNo.No);
									} else {
										log(LogStatus.FAIL, SortOrder.Assecending + " Sorting not working on : "
												+ gridName.toString() + " for Column " + columnName + " , Expected: "
												+ expectedAmount + " but Actual: " + sortedActualAmount, YesNo.No);
										sa.assertTrue(false, SortOrder.Assecending + " Sorting not working on : "
												+ gridName.toString() + " for Column " + columnName + " , Expected: "
												+ expectedAmount + " but Actual: " + sortedActualAmount);
									}

								} else {
									log(LogStatus.FAIL,
											SortOrder.Assecending + " Sorting not checked on : " + gridName.toString()
													+ " for Column " + columnName
													+ ", Either No Data Present there or No Column Present there",
											YesNo.No);
									sa.assertTrue(false,
											SortOrder.Assecending + " Sorting not checked on : " + gridName.toString()
													+ " for Column " + columnName
													+ ", Either No Data Present there or No Column Present there");
								}

							}

						}

						else {
							List<String> expectedDateListText = new ArrayList<String>();
							List<String> actualDateListText = new ArrayList<String>();
							List<WebElement> actualDateListWebElement = sdgGridColumnsDataList(gridName.toString(),
									columnIndex);
							actualDateListText = actualDateListWebElement.stream().map(date -> date.getText())
									.collect(Collectors.toList()).stream().filter(x -> !x.equals(""))
									.collect(Collectors.toList());

							if (actualDateListText.size() > 0) {
								expectedDateListText = dateToAscendingOrder(actualDateListWebElement);

								if (actualDateListText.equals(expectedDateListText)) {
									log(LogStatus.PASS,
											"Verified " + SortOrder.Assecending + " Sorting on : " + gridName.toString()
													+ " for Column " + columnName + " & i.e.: " + actualDateListText,
											YesNo.No);
								} else {
									log(LogStatus.FAIL,
											SortOrder.Assecending + " Sorting not working on : " + gridName.toString()
													+ " for Column " + columnName + " , Expected: "
													+ expectedDateListText + " but Actual: " + actualDateListText,
											YesNo.No);
									sa.assertTrue(false,
											SortOrder.Assecending + " Sorting not working on : " + gridName.toString()
													+ " for Column " + columnName + " , Expected: "
													+ expectedDateListText + " but Actual: " + actualDateListText);
								}

							}

							else {
								log(LogStatus.FAIL,
										SortOrder.Assecending + " Sorting not checked on : " + gridName.toString()
												+ " for Column " + columnName
												+ ", Either No Data Present there or No Column Present there",
										YesNo.No);
								sa.assertTrue(false,
										SortOrder.Assecending + " Sorting not checked on : " + gridName.toString()
												+ " for Column " + columnName
												+ ", Either No Data Present there or No Column Present there");
							}

						}

					} else {
						log(LogStatus.PASS, "Not able to click on " + gridName.toString() + "  Grid header "
								+ columnName + " so cannot check Sorting " + SortOrder.Assecending, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on " + gridName.toString() + "  Grid header "
								+ columnName + " so cannot check Sorting " + SortOrder.Assecending);
					}

				} else {
					log(LogStatus.PASS, "Not Able to Click on Sorted By Link of Grid: " + gridName
							+ " so cannot check Sorting " + SortOrder.Assecending, YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Sorted By Link of Grid: " + gridName
							+ " so cannot check Sorting " + SortOrder.Assecending);
				}

			}
		} else {
			log(LogStatus.PASS,
					gridName.toString() + "  Grid header cloumns list is not visible so cannot check Sorting ",
					YesNo.Yes);
			sa.assertTrue(false,
					gridName.toString() + "  Grid header cloumns list is not visible so cannot check Sorting ");
		}
	}
	
	public List<String> themeGridHeadersVerification(boolean navigateToRecordOrNot, String projectName, String tabName,
			String themeNameToNavigate, HashMap<String, List<String>> gridAndColumns, boolean exactMatchForColumn,
			boolean themeNewWindowCloseOrNotThenSwitchParentWindow) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		List<String> negativeResult = new ArrayList<String>();

		String parentId = null;

		if (navigateToRecordOrNot) {
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);

				if (CommonLib.sendKeysAndPressEnter(driver, themeSearchBox(20), themeNameToNavigate,
						"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box", YesNo.No);

					if (recordInTableOfTheme(themeNameToNavigate, 5) != null) {
						log(LogStatus.INFO, "Verified Theme " + themeNameToNavigate + " Has Been Created", YesNo.No);

						if (CommonLib.clickUsingJavaScript(driver, recordInTableOfTheme(themeNameToNavigate, 5),
								"Theme Name: " + themeNameToNavigate, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on the Theme: " + themeNameToNavigate, YesNo.No);
							parentId = CommonLib.switchOnWindow(driver);
							if (parentId != null) {
								log(LogStatus.INFO, "Switched to New Window", YesNo.No);

							} else {
								log(LogStatus.ERROR, "Not Able to Switch to New Window", YesNo.No);
								negativeResult.add("Not Able to Switch to New Window");
							}

						} else {
							log(LogStatus.ERROR, "Not able to Click on the Theme: " + themeNameToNavigate, YesNo.No);
							negativeResult.add("Not able to Click on the Theme: " + themeNameToNavigate);

						}

					} else {
						log(LogStatus.ERROR, "Theme " + themeNameToNavigate + " is present there", YesNo.No);
						negativeResult.add("Theme " + themeNameToNavigate + " is present there");

					}

				} else {
					log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box", YesNo.No);
					negativeResult.add(themeNameToNavigate + " value is not passed in Theme Search Box");

				}

			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabName + " Tab", YesNo.No);
				negativeResult.add("Not able to click on " + tabName + " Tab");
			}

		}

		String subTabName = RelatedTab.Acuity.toString();
		if (BP.clicktabOnPage(subTabName)) {
			log(LogStatus.PASS, "Clicked on SubTab: " + subTabName, YesNo.No);

		} else {

			log(LogStatus.ERROR, "Not able to click on " + subTabName + " tab", YesNo.Yes);
			negativeResult.add("Not able to click on " + subTabName + " tab");
		}

		if (gridAndColumns != null) {

			for (String gridColumn : gridAndColumns.keySet()) {

				List<String> actualHeaders = themeGridsHeadersText(gridColumn);
				Integer loopCount = 0;

				if (actualHeaders.size() > 0) {

					if (exactMatchForColumn) {
						for (String column : gridAndColumns.get(gridColumn)) {

							if (column.equals(actualHeaders.get(loopCount))) {
								log(LogStatus.PASS, "Column matched and i.e.: " + column + " for grid: " + gridColumn,
										YesNo.No);
							} else {
								log(LogStatus.ERROR, "Column not matched, Expected: " + column + " but Actual: "
										+ actualHeaders.get(loopCount) + " for grid: " + gridColumn, YesNo.Yes);
								negativeResult.add("Column not matched, Expected: " + column + " but Actual: "
										+ actualHeaders.get(loopCount) + " for grid: " + gridColumn);
							}

							loopCount++;
						}
					} else {
						for (String column : gridAndColumns.get(gridColumn)) {

							if (actualHeaders.contains(column)) {
								log(LogStatus.PASS, "Column contains and i.e.: " + column + " for grid: " + gridColumn,
										YesNo.No);
							} else {
								log(LogStatus.ERROR, "Column not contains, Expected: " + column + " but Actual: "
										+ actualHeaders + " for grid: " + gridColumn, YesNo.Yes);
								negativeResult.add("Column not matched, Expected: " + column + " but Actual: "
										+ actualHeaders + " for grid: " + gridColumn);
							}

						}
					}
				} else {
					log(LogStatus.ERROR,
							"Either No Headers Present there for Grid: " + gridColumn + " or Locator has been Changed",
							YesNo.Yes);
					negativeResult.add(
							"Either No Headers Present there for Grid: " + gridColumn + " or Locator has been Changed");
				}

			}

		}

		if (themeNewWindowCloseOrNotThenSwitchParentWindow) {

			if (parentId != null) {
				driver.close();
				driver.switchTo().window(parentId);
			} else {
				driver.close();
				driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
			}
		}

		return negativeResult;
	}
	
	public ArrayList<String> verifyRecordsonInteractionsViewAllPopup(boolean navigateToRecordOrNot, String projectName,
			String tabName, String themeNameToNavigate, Integer taskCount, Integer callCount, Integer EventCount,
			boolean themeNewWindowCloseOrNotThenSwitchParentWindow) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		ArrayList<String> negativeResult = new ArrayList<String>();
		String mainParentId = null;

		if (navigateToRecordOrNot) {
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);

				if (CommonLib.sendKeysAndPressEnter(driver, themeSearchBox(20), themeNameToNavigate,
						"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box", YesNo.No);

					if (recordInTableOfTheme(themeNameToNavigate, 5) != null) {
						log(LogStatus.INFO, "Verified Theme " + themeNameToNavigate + " Has Been Created", YesNo.No);

						if (CommonLib.clickUsingJavaScript(driver, recordInTableOfTheme(themeNameToNavigate, 5),
								"Theme Name: " + themeNameToNavigate, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on the Theme: " + themeNameToNavigate, YesNo.No);
							mainParentId = CommonLib.switchOnWindow(driver);
							if (mainParentId != null) {
								log(LogStatus.INFO, "Switched to New Window", YesNo.No);

							} else {
								log(LogStatus.ERROR, "Not Able to Switch to New Window", YesNo.No);
								negativeResult.add("Not Able to Switch to New Window");
							}

						} else {
							log(LogStatus.ERROR, "Not able to Click on the Theme: " + themeNameToNavigate, YesNo.No);
							negativeResult.add("Not able to Click on the Theme: " + themeNameToNavigate);

						}

					} else {
						log(LogStatus.ERROR, "Theme " + themeNameToNavigate + " is present there", YesNo.No);
						negativeResult.add("Theme " + themeNameToNavigate + " is present there");

					}

				} else {
					log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box", YesNo.No);
					negativeResult.add(themeNameToNavigate + " value is not passed in Theme Search Box");

				}

			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabName + " Tab", YesNo.No);
				negativeResult.add("Not able to click on " + tabName + " Tab");
			}

		}

		String subTabName = RelatedTab.Acuity.toString();
		if (bp.clicktabOnPage(subTabName)) {
			log(LogStatus.PASS, "Clicked on SubTab: " + subTabName, YesNo.No);

		} else {

			log(LogStatus.ERROR, "Not able to click on " + subTabName + " tab", YesNo.Yes);
			negativeResult.add("Not able to click on " + subTabName + " tab");
		}

		if (CommonLib.clickUsingJavaScript(driver, bp.getViewAllBtnOnIntration(20), "View All button")) {
			log(LogStatus.INFO, "Clicked on View All button of Interaction section", YesNo.No);
			String parentId = switchToWindowOpenNextToParentWindow(driver);
			if (parentId != null) {

				if (interactionAllIcons().size() > 0) {
					if (taskCount != null) {

						List<String> actualData = interactionAllIcons().stream().map(x -> x.getAttribute("icon-name"))
								.collect(Collectors.toList()).stream().filter(x -> x.contains("task"))
								.collect(Collectors.toList());
						if (taskCount.equals(actualData.size())) {
							log(LogStatus.PASS, "-----Counts Matched for Tasks and i.e.: " + taskCount + "-----",
									YesNo.No);
						} else {
							log(LogStatus.ERROR, "-----Counts not Matched for Tasks, Expected: " + taskCount
									+ " but Actual: " + actualData.size() + "-----", YesNo.No);
							negativeResult.add("-----Counts not Matched for Tasks, Expected: " + taskCount
									+ " but Actual: " + actualData.size() + "-----");
						}

					}

					if (callCount != null) {

						List<String> actualData = interactionAllIcons().stream().map(x -> x.getAttribute("icon-name"))
								.collect(Collectors.toList()).stream().filter(x -> x.contains("log_a_call"))
								.collect(Collectors.toList());
						if (callCount.equals(actualData.size())) {
							log(LogStatus.PASS, "-----Counts Matched for Call and i.e.: " + callCount + "-----",
									YesNo.No);
						} else {
							log(LogStatus.ERROR, "-----Counts not Matched for Call, Expected: " + callCount
									+ " but Actual: " + actualData.size() + "-----", YesNo.No);
							negativeResult.add("-----Counts not Matched for Call, Expected: " + callCount
									+ " but Actual: " + actualData.size() + "-----");
						}

					}

					if (EventCount != null) {

						List<String> actualData = interactionAllIcons().stream().map(x -> x.getAttribute("icon-name"))
								.collect(Collectors.toList()).stream().filter(x -> x.contains("event"))
								.collect(Collectors.toList());
						if (EventCount.equals(actualData.size())) {
							log(LogStatus.PASS, "-----Counts Matched for Events and i.e.: " + EventCount + "-----",
									YesNo.No);
						} else {
							log(LogStatus.ERROR, "-----Counts not Matched for Events, Expected: " + EventCount
									+ " but Actual: " + actualData.size() + "-----", YesNo.No);
							negativeResult.add("-----Counts not Matched for Events, Expected: " + EventCount
									+ " but Actual: " + actualData.size() + "-----");
						}

					}
				}

				else {
					log(LogStatus.ERROR, "Either Locator changed or No Dat is present", YesNo.No);
					negativeResult.add("Either Locator changed or No Dat is present");
				}

				driver.close();
				driver.switchTo().window(parentId);

			} else {
				log(LogStatus.ERROR, "New tab did not open after click", YesNo.No);
				negativeResult.add("New tab did not open after click");
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on View All button of Interaction section", YesNo.No);
			negativeResult.add("Not able to click on View All button of Interaction section");
		}

		if (themeNewWindowCloseOrNotThenSwitchParentWindow) {

			if (mainParentId != null) {
				driver.close();
				driver.switchTo().window(mainParentId);
			} else {
				driver.close();
				driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
			}
		}

		return negativeResult;
	}
	
	public List<String> themeGridHyperAndNonHyperLinksNavigationOfFirstRow(boolean navigateToRecordOrNot,
			String projectName, String tabName, String themeNameToNavigate, String gridName,
			List<String> hyperLinkColumns, List<String> nonHyperLinkColumns, List<String> hyperLinkColumnsWithPopups,
			boolean themeNewWindowCloseOrNotThenSwitchParentWindow) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		List<String> negativeResult = new ArrayList<String>();

		String parentId = null;

		if (navigateToRecordOrNot) {
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);

				if (CommonLib.sendKeysAndPressEnter(driver, themeSearchBox(20), themeNameToNavigate,
						"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box", YesNo.No);

					if (recordInTableOfTheme(themeNameToNavigate, 5) != null) {
						log(LogStatus.INFO, "Verified Theme " + themeNameToNavigate + " Has Been Created", YesNo.No);

						if (CommonLib.clickUsingJavaScript(driver, recordInTableOfTheme(themeNameToNavigate, 5),
								"Theme Name: " + themeNameToNavigate, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on the Theme: " + themeNameToNavigate, YesNo.No);
							parentId = CommonLib.switchOnWindow(driver);
							if (parentId != null) {
								log(LogStatus.INFO, "Switched to New Window", YesNo.No);

							} else {
								log(LogStatus.ERROR, "Not Able to Switch to New Window", YesNo.No);
								negativeResult.add("Not Able to Switch to New Window");
							}

						} else {
							log(LogStatus.ERROR, "Not able to Click on the Theme: " + themeNameToNavigate, YesNo.No);
							negativeResult.add("Not able to Click on the Theme: " + themeNameToNavigate);

						}

					} else {
						log(LogStatus.ERROR, "Theme " + themeNameToNavigate + " is present there", YesNo.No);
						negativeResult.add("Theme " + themeNameToNavigate + " is present there");

					}

				} else {
					log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box", YesNo.No);
					negativeResult.add(themeNameToNavigate + " value is not passed in Theme Search Box");

				}

			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabName + " Tab", YesNo.No);
				negativeResult.add("Not able to click on " + tabName + " Tab");
			}

		}

		String subTabName = RelatedTab.Acuity.toString();
		if (BP.clicktabOnPage(subTabName)) {
			log(LogStatus.PASS, "Clicked on SubTab: " + subTabName, YesNo.No);

		} else {

			log(LogStatus.ERROR, "Not able to click on " + subTabName + " tab", YesNo.Yes);
			negativeResult.add("Not able to click on " + subTabName + " tab");
		}

		if (hyperLinkColumns != null) {

			for (String hyperLinkColumn : hyperLinkColumns) {

				if (themeGridsHeadersText(gridName).contains(hyperLinkColumn)) {
					log(LogStatus.PASS, "Column with Name: " + hyperLinkColumn + " present there in Grid: " + gridName,
							YesNo.No);
					if (CommonLib.clickUsingJavaScript(driver,
							themeGridsclumnAndRowWiseData(gridName,
									themeGridsHeadersText(gridName).indexOf(hyperLinkColumn) + 1, 1, 5),
							"themeGridsclumnAndRowWiseData", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS,
								"Clicked Column with Name: " + hyperLinkColumn + " present there in Grid: " + gridName,
								YesNo.No);
						String hyperLinkParentId = switchToWindowOpenNextToParentWindow(driver);
						if (hyperLinkParentId != null) {
							log(LogStatus.PASS, "New Tab open after Clicked Column with Name: " + hyperLinkColumn
									+ " present there in Grid: " + gridName, YesNo.No);
							driver.close();
							driver.switchTo().window(hyperLinkParentId);

						} else {
							log(LogStatus.ERROR, "No new Tab open after Clicked Column with Name: " + hyperLinkColumn
									+ " present there in Grid: " + gridName, YesNo.Yes);
							negativeResult.add("No new Tab open after Clicked Column with Name: " + hyperLinkColumn
									+ " present there in Grid: " + gridName);
						}

					} else {
						log(LogStatus.ERROR,
								"No Column with Name: " + hyperLinkColumn + " present there in Grid: " + gridName,
								YesNo.Yes);
						negativeResult
								.add("No Column with Name: " + hyperLinkColumn + " present there in Grid: " + gridName);
					}

				} else {
					log(LogStatus.ERROR,
							"No Column with Name: " + hyperLinkColumn + " present there in Grid: " + gridName,
							YesNo.Yes);
					negativeResult
							.add("No Column with Name: " + hyperLinkColumn + " present there in Grid: " + gridName);
				}
			}
		}

		if (nonHyperLinkColumns != null) {

			for (String nonHyperLinkColumn : nonHyperLinkColumns) {

				if (themeGridsHeadersText(gridName).contains(nonHyperLinkColumn)) {
					log(LogStatus.PASS,
							"Column with Name: " + nonHyperLinkColumn + " present there in Grid: " + gridName,
							YesNo.No);
					if (CommonLib.clickUsingJavaScript(driver,
							themeGridsclumnAndRowWiseData(gridName,
									themeGridsHeadersText(gridName).indexOf(nonHyperLinkColumn) + 1, 1, 5),
							"themeGridsclumnAndRowWiseData", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "Clicked Column with Name: " + nonHyperLinkColumn
								+ " present there in Grid: " + gridName, YesNo.No);
						String hyperLinkParentId = switchToWindowOpenNextToParentWindow(driver);
						if (hyperLinkParentId == null) {
							log(LogStatus.PASS, "No New Tab open after Clicked Column with Name: " + nonHyperLinkColumn
									+ " present there in Grid: " + gridName, YesNo.No);

						} else {
							log(LogStatus.ERROR, "New Tab open after Clicked Column with Name: " + nonHyperLinkColumn
									+ " present there in Grid: " + gridName, YesNo.Yes);
							negativeResult.add("New Tab open after Clicked Column with Name: " + nonHyperLinkColumn
									+ " present there in Grid: " + gridName);

							driver.close();
							driver.switchTo().window(hyperLinkParentId);
						}

					} else {
						log(LogStatus.ERROR,
								"No Column with Name: " + nonHyperLinkColumn + " present there in Grid: " + gridName,
								YesNo.Yes);
						negativeResult.add(
								"No Column with Name: " + nonHyperLinkColumn + " present there in Grid: " + gridName);
					}

				} else {
					log(LogStatus.ERROR,
							"No Column with Name: " + nonHyperLinkColumn + " present there in Grid: " + gridName,
							YesNo.Yes);
					negativeResult
							.add("No Column with Name: " + nonHyperLinkColumn + " present there in Grid: " + gridName);
				}
			}
		}

		if (hyperLinkColumnsWithPopups != null) {

			for (String hyperLinkColumn : hyperLinkColumnsWithPopups) {

				if (themeGridsHeadersText(gridName).contains(hyperLinkColumn)) {
					log(LogStatus.PASS, "Column with Name: " + hyperLinkColumn + " present there in Grid: " + gridName,
							YesNo.No);
					if (CommonLib.clickUsingJavaScript(driver,
							themeGridsclumnAndRowWiseData(gridName,
									themeGridsHeadersText(gridName).indexOf(hyperLinkColumn) + 1, 1, 5),
							"themeGridsclumnAndRowWiseData", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS,
								"Clicked Column with Name: " + hyperLinkColumn + " present there in Grid: " + gridName,
								YesNo.No);

						if (popUpOfIntractionPage(15) != null) {
							log(LogStatus.PASS, "Popup Open after click on Record of column: " + hyperLinkColumn
									+ " of Grid: " + gridName, YesNo.No);

							if (click(driver, crossButtonOfSubjectLinkPopUpInInteractionSection(8),
									"crossButtonOfSubjectLinkPopUpInInteractionSection", action.BOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Cross Button of Subject Link Pop up ", YesNo.No);

								if (activitySubjetLinkPopupHeaderOnInteraction(5) == null) {

									log(LogStatus.INFO,
											"Verified Subject Link Pop up has been closed after click on Cross button",
											YesNo.No);
								} else {
									log(LogStatus.ERROR,
											"Verified Subject Link Pop up has been closed after click on Cross button",
											YesNo.No);
									negativeResult.add(
											"Verified Subject Link Pop up has been closed after click on Cross button");
								}

							} else {
								log(LogStatus.ERROR, "Not able to Click on Cross Button of Subject Link Pop up",
										YesNo.No);
								negativeResult.add("Not able to Click on Cross Button of Subject Link Pop up");
							}
						} else {
							log(LogStatus.ERROR, "New Popup open after Clicked Column with Name: " + hyperLinkColumn
									+ " present there in Grid: " + gridName, YesNo.Yes);
							negativeResult.add("New Popup open after Clicked Column with Name: " + hyperLinkColumn
									+ " present there in Grid: " + gridName);
						}

						String hyperLinkParentId = switchToWindowOpenNextToParentWindow(driver);
						if (hyperLinkParentId == null) {
							log(LogStatus.PASS, "No New Tab open after Clicked Column with Name: " + hyperLinkColumn
									+ " present there in Grid: " + gridName, YesNo.No);

						} else {
							log(LogStatus.ERROR, "New Tab open after Clicked Column with Name: " + hyperLinkColumn
									+ " present there in Grid: " + gridName, YesNo.Yes);
							negativeResult.add("New Tab open after Clicked Column with Name: " + hyperLinkColumn
									+ " present there in Grid: " + gridName);
							driver.close();
							driver.switchTo().window(hyperLinkParentId);
						}

					} else {
						log(LogStatus.ERROR,
								"No Column with Name: " + hyperLinkColumn + " present there in Grid: " + gridName,
								YesNo.Yes);
						negativeResult
								.add("No Column with Name: " + hyperLinkColumn + " present there in Grid: " + gridName);
					}

				} else {
					log(LogStatus.ERROR,
							"No Column with Name: " + hyperLinkColumn + " present there in Grid: " + gridName,
							YesNo.Yes);
					negativeResult
							.add("No Column with Name: " + hyperLinkColumn + " present there in Grid: " + gridName);
				}
			}
		}

		if (themeNewWindowCloseOrNotThenSwitchParentWindow) {

			if (parentId != null) {
				driver.close();
				driver.switchTo().window(parentId);
			} else {
				driver.close();
				driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
			}
		}

		return negativeResult;
	}
	
	public ArrayList<String> verifyViewNotePopupAfterClick(List<DataContainer> datas) {

		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		ArrayList<String> subjectLinkPopUpNegativeResult = new ArrayList<String>();

		if (datas != null) {

			for (DataContainer data : datas) {

				for (String column : data.columns.split("<break>", -1)) {

					if (themeGridsHeadersText(data.gridName).contains(column)) {
						log(LogStatus.PASS, "Column with Name: " + column + " present there in Grid: " + data.gridName,
								YesNo.No);
						if (CommonLib.clickUsingJavaScript(driver, themeGridsclumnAndRowWiseData(data.gridName,
								themeGridsHeadersText(data.gridName).indexOf(column) + 1,
								themeGridFirstColumnText(data.gridName).indexOf(data.recordNameBasedOnColumnSelect) + 1,
								5), "themeGridsclumnAndRowWiseData", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS,
									"Clicked Column with Name: " + column + " present there in Grid: " + data.gridName,
									YesNo.No);

							ArrayList<String> tempNegativeResult = BP.verifySubjectLinkPopUpOnIntraction(driver,
									data.popUpHeader, data.basicSection, data.advanceSection, data.iconType,
									data.pageName);
							if (!tempNegativeResult.isEmpty()) {
								subjectLinkPopUpNegativeResult.addAll(tempNegativeResult);
							}

						} else {
							log(LogStatus.ERROR,
									"No Column with Name: " + column + " present there in Grid: " + data.gridName,
									YesNo.Yes);
							subjectLinkPopUpNegativeResult
									.add("No Column with Name: " + column + " present there in Grid: " + data.gridName);
						}

					} else {
						log(LogStatus.ERROR,
								"No Column with Name: " + column + " present there in Grid: " + data.gridName,
								YesNo.Yes);
						subjectLinkPopUpNegativeResult
								.add("No Column with Name: " + column + " present there in Grid: " + data.gridName);
					}

				}

			}

		}

		return subjectLinkPopUpNegativeResult;

	}
	
	public boolean verifyRecordInGrid(String sectionName, String columnName, String[] recordNames) {

		Integer status = 0;
		Integer loopCount = 0;
		if (themeGridsViewAllButton(sectionName, 10) != null) {
			if (CommonLib.click(driver, themeGridsViewAllButton(sectionName, 10),
					"themeGridsViewAllButton: " + sectionName, action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Click on View All Button of Grid: " + sectionName, YesNo.No);
			} else {
				log(LogStatus.ERROR, "Not Able to Click on View All Button of Grid: " + sectionName, YesNo.Yes);
				return false;
			}
		}

		for (String recordName : recordNames) {
			if (dataOfSectionBasedOnColumn(sectionName, recordName, columnName, 7) != null) {
				log(LogStatus.INFO,
						"Record Found: " + recordName + " under Column: " + columnName + " under Grid: " + columnName,
						YesNo.No);

				status++;
			} else {
				log(LogStatus.ERROR, "Record Not Found: " + recordName + " under Column: " + columnName
						+ " under Grid: " + columnName, YesNo.Yes);

			}
			loopCount++;
		}

		CommonLib.refresh(driver);
		if (loopCount.equals(status) && !status.equals(0))
			return true;
		else
			return false;

	}
	
	public boolean removeFromThemeBasedOnRecordOfColumnNumber(boolean navigateToRecordOrNot, String tabName,
			String themeNameToNavigate, String projectName, boolean themeNewWindowCloseOrNotThenSwitchParentWindow,
			String gridName, String recordNames, Integer ColumnNumber, boolean removeAllRecord,
			boolean RemoveOrCancel) {

		String parentId = null;

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		try {

			if (navigateToRecordOrNot) {
				if (lp.clickOnTab(projectName, tabName)) {
					log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);

					if (CommonLib.sendKeysAndPressEnter(driver, themeSearchBox(20), themeNameToNavigate,
							"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box",
								YesNo.No);

						if (recordInTableOfTheme(themeNameToNavigate, 5) != null) {
							log(LogStatus.INFO, "Verified Theme " + themeNameToNavigate + " Has Been Created",
									YesNo.No);

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
								log(LogStatus.ERROR, "Not able to Click on the Theme: " + themeNameToNavigate,
										YesNo.No);
								return false;

							}

						} else {
							log(LogStatus.ERROR, "Theme " + themeNameToNavigate + " is present there", YesNo.No);
							return false;

						}

					} else {
						log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box",
								YesNo.No);
						return false;

					}

				} else {
					log(LogStatus.ERROR, "Not able to click on " + tabName + " Tab", YesNo.No);
					return false;

				}

			}

			String subTabName = RelatedTab.Acuity.toString();
			if (BP.clicktabOnPage(subTabName)) {
				log(LogStatus.PASS, "Clicked on SubTab: " + subTabName, YesNo.No);
				log(LogStatus.INFO, "click on " + subTabName + " tab", YesNo.No);
				if (click(driver, themeGridsRemoveIcon(gridName, 15), "themeGridsRemoveIcon: " + gridName,
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "click on themeGridsRemoveIcon " + gridName, YesNo.No);

				} else {

					log(LogStatus.ERROR, "not able to click on themeGridsRemoveIcon " + gridName, YesNo.Yes);
					return false;
				}

			} else {

				log(LogStatus.ERROR, "not able to click on " + subTabName + " tab", YesNo.Yes);
				return false;
			}

			if (recordNames != null && !removeAllRecord) {
				String[] members = recordNames.split("<break>");

				for (String teamMember : members) {
					if (CommonLib.click(driver, checkBoxOfRecordInAGrid(gridName, teamMember, ColumnNumber, 10),
							"checkBoxOfRecordInAGrid: ", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Checkbox of Record: " + teamMember + " of Grid: " + gridName,
								YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"Not ABle to Click on Checkbox of Record: " + teamMember + " of Grid: " + gridName,
								YesNo.No);
						return false;
					}

				}

			} else if (removeAllRecord && ("".equals(recordNames) || recordNames == null)) {

				if (CommonLib.click(driver, checkBoxOfAllRecordInAGrid(gridName, 10), "checkBoxOfRecordInAGrid: ",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Checkbox of All Record of Grid: " + gridName, YesNo.No);

				} else {
					log(LogStatus.ERROR, "Not Able to Clicked on Checkbox of All Record of Grid: " + gridName,
							YesNo.No);
					return false;
				}
			}

			if (RemoveOrCancel) {
				if (CommonLib.click(driver, footerButtonOfAGrid(gridName, "Remove", 10),
						"footerButtonOfAGrid: " + "Remove", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Remove Button", YesNo.No);

					CommonLib.ThreadSleep(5000);

					if (themeGridsRemoveIcon(gridName, 15) != null) {
						if (click(driver, themeGridsRemoveIcon(gridName, 15), "themeGridsRemoveIcon: " + gridName,
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "click on themeGridsRemoveIcon " + gridName, YesNo.No);

							if (recordNames != null && !removeAllRecord) {
								String[] members = recordNames.split("<break>");

								for (String teamMember : members) {
									if (checkBoxOfRecordInAGrid(gridName, teamMember, ColumnNumber, 5) == null) {
										log(LogStatus.INFO,
												"Record: " + teamMember + " of Grid: " + gridName + " has been removed",
												YesNo.No);

									} else {
										log(LogStatus.ERROR, "Record: " + teamMember + " of Grid: " + gridName
												+ " has not been removed", YesNo.No);
										return false;
									}

								}

							}

							else if (removeAllRecord && ("".equals(recordNames) || recordNames == null)) {

								if (checkBoxOfAllRecordInAGrid(gridName, 10) == null) {
									log(LogStatus.INFO, "All Records of Grid : " + gridName + " has been removed",
											YesNo.No);

								} else {
									log(LogStatus.ERROR, "All Records of Grid : " + gridName + " has not been removed",
											YesNo.No);
									return false;
								}
							}

							if (CommonLib.click(driver, footerButtonOfAGrid(gridName, "Cancel", 10),
									"footerButtonOfAGrid: " + "Cancel", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Cancel Button", YesNo.No);

							} else {
								log(LogStatus.ERROR, "Not Able to Click on Cancel Button", YesNo.No);
								return false;

							}

						} else {

							log(LogStatus.ERROR, "not able to click on themeGridsRemoveIcon " + gridName, YesNo.Yes);
							return false;
						}

					} else {

						log(LogStatus.INFO, "Records Has been Removed for Grid: " + gridName, YesNo.Yes);

					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Remove Button", YesNo.No);
					return false;

				}
			}

			else {
				if (CommonLib.click(driver, footerButtonOfAGrid(gridName, "Cancel", 10),
						"footerButtonOfAGrid: " + "Cancel", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Cancel Button", YesNo.No);

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Cancel Button", YesNo.No);
					return false;

				}
			}

			if (themeNewWindowCloseOrNotThenSwitchParentWindow) {

				if (parentId != null) {
					driver.close();
					driver.switchTo().window(parentId);
				} else {
					driver.close();
					driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
				}
			}

		} catch (Exception e) {
			log(LogStatus.ERROR, "Exception: " + e.getMessage(), YesNo.Yes);
			return false;
		}

		return true;
	}
	
	public List<String> createLogANoteFromSectionOfThemeAndVerification(boolean navigateToRecordOrNot, String tabName,
			String themeNameToNavigate, String callSubjectName, String projectName, String[][] basicSection,
			String[][] advanceSection, String[][] taskSection, String[] suggestedTags, String[] removeTagName,
			boolean errorMsg, String validationRuleMessage, String validationRuleErrorMsgLocation,
			String[][] createNewRecordPopUp, String[][] addContactsToDealTeamPopUp,
			String[][] addContactsToFundraisingPopUp, String[][][] detailsSectionUnderSuggestedPopup,
			String sectionNameFromWhichLogANoteButtonClick, String recordNameFromWhichLogANoteButtonClick,
			boolean themeNewWindowCloseOrNotThenSwitchParentWindow) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		List<String> negativeResult = new ArrayList<String>();

		String parentId = null;

		if (navigateToRecordOrNot) {
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);

				if (CommonLib.sendKeysAndPressEnter(driver, themeSearchBox(20), themeNameToNavigate,
						"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, themeNameToNavigate + " value has been passed in Theme Search Box", YesNo.No);

					if (recordInTableOfTheme(themeNameToNavigate, 5) != null) {
						log(LogStatus.INFO, "Verified Theme " + themeNameToNavigate + " Has Been Created", YesNo.No);

						if (CommonLib.clickUsingJavaScript(driver, recordInTableOfTheme(themeNameToNavigate, 5),
								"Theme Name: " + themeNameToNavigate, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on the Theme: " + themeNameToNavigate, YesNo.No);
							parentId = CommonLib.switchOnWindow(driver);
							if (parentId != null) {
								log(LogStatus.INFO, "Switched to New Window", YesNo.No);

							} else {
								log(LogStatus.ERROR, "Not Able to Switch to New Window", YesNo.No);
								negativeResult.add("Not Able to Switch to New Window");
							}

						} else {
							log(LogStatus.ERROR, "Not able to Click on the Theme: " + themeNameToNavigate, YesNo.No);
							negativeResult.add("Not able to Click on the Theme: " + themeNameToNavigate);

						}

					} else {
						log(LogStatus.ERROR, "Theme " + themeNameToNavigate + " is present there", YesNo.No);
						negativeResult.add("Theme " + themeNameToNavigate + " is present there");

					}

				} else {
					log(LogStatus.ERROR, themeNameToNavigate + " value is not passed in Theme Search Box", YesNo.No);
					negativeResult.add(themeNameToNavigate + " value is not passed in Theme Search Box");

				}

			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabName + " Tab", YesNo.No);
				negativeResult.add("Not able to click on " + tabName + " Tab");
			}

		}

		if (CommonLib.click(driver,
				logANoteButtonBasedOnSection(sectionNameFromWhichLogANoteButtonClick,
						recordNameFromWhichLogANoteButtonClick, 10),
				"Log a Note Button of Theme: " + themeNameToNavigate + ", Section "
						+ sectionNameFromWhichLogANoteButtonClick + " and Record: "
						+ recordNameFromWhichLogANoteButtonClick,
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Log a Note Button for " + sectionNameFromWhichLogANoteButtonClick + ": "
					+ recordNameFromWhichLogANoteButtonClick, YesNo.No);

			if (BP.updateActivityTimelineRecord(projectName, basicSection, advanceSection, taskSection, suggestedTags,
					removeTagName, errorMsg, validationRuleMessage, validationRuleErrorMsgLocation,
					createNewRecordPopUp, addContactsToDealTeamPopUp, addContactsToFundraisingPopUp,
					detailsSectionUnderSuggestedPopup)) {
				log(LogStatus.PASS,
						"-----Activity timeline record has been Created for Subject: " + callSubjectName + "-----",
						YesNo.No);

			} else {
				log(LogStatus.FAIL,
						"-----Activity timeline record is not Created for Subject: " + callSubjectName + "-----",
						YesNo.No);
				negativeResult
						.add("-----Activity timeline record is not Created for Subject: " + callSubjectName + "-----");
			}

			CommonLib.refresh(driver);

		} else {
			log(LogStatus.ERROR, "Not Able to Click on Log a Note Button for " + sectionNameFromWhichLogANoteButtonClick
					+ ": " + recordNameFromWhichLogANoteButtonClick, YesNo.No);
			negativeResult.add("Not Able to Click on Log a Note Button for " + sectionNameFromWhichLogANoteButtonClick
					+ ": " + recordNameFromWhichLogANoteButtonClick);

		}

		if (themeNewWindowCloseOrNotThenSwitchParentWindow) {

			if (parentId != null) {
				driver.close();
				driver.switchTo().window(parentId);
			} else {
				driver.close();
				driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
			}
		}

		return negativeResult;
	}
	
	public boolean createTheme(String projectName, String tabName, boolean copyExistingTheme, String newThemeName,
			String newThemeDescription, String existingThemeNameInCaseCopyTheme,
			boolean copyAllInteractionsInCaseCopyTheme, String errorMsg, boolean SaveOrCancel) {

		boolean flag = false;

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		if (lp.clickOnTab(projectName, tabName)) {
			log(LogStatus.INFO, "Click on Tab : " + tabName, YesNo.No);
			if (CommonLib.click(driver, newThemeButton(30), "newThemeButton", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on the New theme Button", YesNo.No);

				if (copyExistingTheme) {

					if (CommonLib.click(driver, yesButton(30), "yesButton", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on the Yes Button", YesNo.No);

						if (errorMsg != null) {
							if (CommonLib.click(driver, copyThemeSaveOrCancelButton("Save", 10),
									"copyThemeSaveOrCancelButton: " + "Save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Save Button", YesNo.No);
								if (newThemeErrorMsg(7) != null) {

									String actualErrorMsg = CommonLib.getText(driver, newThemeErrorMsg(7),
											"newThemeErrorMsg", action.BOOLEAN);

									if (errorMsg.equals(actualErrorMsg)) {
										log(LogStatus.INFO,
												"-----Error Msg has been Verified: " + actualErrorMsg + "-----",
												YesNo.No);
									} else {
										log(LogStatus.ERROR, "-----Error Msg has not been Verified, Actual: "
												+ actualErrorMsg + " but Expected: " + errorMsg + "-----", YesNo.No);
										BaseLib.sa.assertTrue(false, "-----Error Msg has not been Verified, Actual: "
												+ actualErrorMsg + " but Expected: " + errorMsg + "-----");

									}
								}

								else {
									log(LogStatus.ERROR, "Either No Error Msg Found or Locator Changed", YesNo.No);
									BaseLib.sa.assertTrue(false, "Either No Error Msg Found or Locator Changed");

								}

							} else {
								log(LogStatus.ERROR, "Not Able to Click on Save Button", YesNo.No);
								return false;

							}
						}

						if (sendKeys(driver, existingThemeNameInput(20), existingThemeNameInCaseCopyTheme,
								"Existing Theme Name", action.BOOLEAN)) {
							log(LogStatus.INFO, "Able to Pass the Value:  " + existingThemeNameInCaseCopyTheme,
									YesNo.No);

							if (CommonLib.clickUsingJavaScript(driver,
									existingThemeNameDropDown(existingThemeNameInCaseCopyTheme, 20),
									"Dropdown Value: " + existingThemeNameInCaseCopyTheme, action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Dropdown Value: " + existingThemeNameInCaseCopyTheme,
										YesNo.No);

							} else {
								log(LogStatus.ERROR,
										"Not Able to Click on Dropdown Value: " + existingThemeNameInCaseCopyTheme,
										YesNo.No);
								return false;

							}

						} else {
							log(LogStatus.ERROR, "Not Able to Pass the Value: " + existingThemeNameInCaseCopyTheme,
									YesNo.No);
							return false;
						}

						if (newThemeName != null) {

							if (sendKeys(driver, themeNameInputBox(20), newThemeName, "Theme Name", action.BOOLEAN)) {
								log(LogStatus.INFO, "Able to Pass the Value:  " + newThemeName, YesNo.No);

							} else {
								log(LogStatus.ERROR, "Not Able to Pass the Value: " + newThemeName, YesNo.No);
								return false;
							}
						}

						if (newThemeDescription != null) {

							if (sendKeys(driver, themeDescription(20), newThemeDescription, "Description",
									action.BOOLEAN)) {
								log(LogStatus.INFO, "Able to Pass the Value:  " + newThemeDescription, YesNo.No);

							} else {
								log(LogStatus.ERROR, "Not Able to Pass the Value: " + newThemeDescription, YesNo.No);
								return false;
							}
						}

						if (copyAllInteractionsInCaseCopyTheme) {
							if (!CommonLib.isSelected(driver, copyAllInteractionCheckBox(5),
									"copyAllInteractionCheckBox")) {
								if (CommonLib.click(driver, copyAllInteractionCheckBox(10),
										"copyAllInteractionCheckBox", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Copy All Interaction CheckBox, It gets Checked",
											YesNo.No);
								} else {
									log(LogStatus.ERROR,
											"Not ABle to Click on Copy All Interaction CheckBox, So It not gets Checked",
											YesNo.No);
									return false;

								}
							}

						} else {

							if (CommonLib.isSelected(driver, copyAllInteractionCheckBox(5),
									"copyAllInteractionCheckBox")) {
								if (CommonLib.click(driver, copyAllInteractionCheckBox(10),
										"copyAllInteractionCheckBox", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Copy All Interaction CheckBox, It gets UnChecked",
											YesNo.No);
								} else {
									log(LogStatus.ERROR,
											"Not ABle to Click on Copy All Interaction CheckBox, So It not Ungets Checked",
											YesNo.No);
									return false;

								}
							}

						}

						if (SaveOrCancel) {

							if (CommonLib.click(driver, copyThemeSaveOrCancelButton("Save", 10),
									"copyThemeSaveOrCancelButton: " + "Save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Save Button", YesNo.No);

								if (successMsg(10) != null) {
									log(LogStatus.INFO,
											"Success Msg is showing, So Copy Theme Created for record: " + newThemeName,
											YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"Success Msg is not showing, So Copy Theme is not Created for record: "
													+ newThemeName,
											YesNo.No);

								}

								CommonLib.ThreadSleep(2000);
								CommonLib.refresh(driver);
								CommonLib.ThreadSleep(2000);
								if (CommonLib.sendKeysAndPressEnter(driver, themeSearchBox(20), newThemeName,
										"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, newThemeName + " value has been passed in Theme Search Box",
											YesNo.No);

									if (recordInTableOfTheme(newThemeName, 15) != null) {
										log(LogStatus.INFO, "Verified Theme " + newThemeName + " Has Been Created",
												YesNo.No);

										flag = true;

									} else {
										log(LogStatus.ERROR, "Theme " + newThemeName + " is not created", YesNo.No);

									}

								} else {
									log(LogStatus.ERROR, newThemeName + " value is not passed in Theme Search Box",
											YesNo.No);

								}

							} else {
								log(LogStatus.ERROR, "Not Able to Click on Save Button", YesNo.No);
								return false;

							}

						} else {
							if (CommonLib.click(driver, copyThemeSaveOrCancelButton("Cancel", 10),
									"copyThemeSaveOrCancelButton: " + "Cancel", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Cancel Button", YesNo.No);
								CommonLib.ThreadSleep(5000);
								if (themeNameInputBox(6) == null) {
									log(LogStatus.INFO, "---Verified Popup Has Been Closed----", YesNo.No);
									flag = true;
								} else {
									log(LogStatus.ERROR, "---Not Verified Popup Has Been Closed----", YesNo.No);
									return false;
								}

							} else {
								log(LogStatus.ERROR, "Not Able to Click on Cancel Button", YesNo.No);
								return false;

							}
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on the Yes Button", YesNo.No);
					}

				} else {

					if (CommonLib.click(driver, noButton(30), "noButton", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on the No Button", YesNo.No);

						if (errorMsg != null) {
							if (CommonLib.click(driver, copyThemeSaveOrCancelButton("Save", 10),
									"copyThemeSaveOrCancelButton: " + "Save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Save Button", YesNo.No);
								if (newThemeErrorMsg(7) != null) {

									String actualErrorMsg = CommonLib.getText(driver, newThemeErrorMsg(7),
											"newThemeErrorMsg", action.BOOLEAN);

									if (errorMsg.equals(actualErrorMsg)) {
										log(LogStatus.INFO,
												"-----Error Msg has been Verified: " + actualErrorMsg + "-----",
												YesNo.No);
									} else {
										log(LogStatus.ERROR, "-----Error Msg has not been Verified, Actual: "
												+ actualErrorMsg + " but Expected: " + errorMsg + "-----", YesNo.No);
										BaseLib.sa.assertTrue(false, "-----Error Msg has not been Verified, Actual: "
												+ actualErrorMsg + " but Expected: " + errorMsg + "-----");

									}
								}

								else {
									log(LogStatus.ERROR, "Either No Error Msg Found or Locator Changed", YesNo.No);
									BaseLib.sa.assertTrue(false, "Either No Error Msg Found or Locator Changed");

								}

							} else {
								log(LogStatus.ERROR, "Not Able to Click on Save Button", YesNo.No);
								return false;

							}
						}

						if (!newThemeName.isEmpty() || !newThemeName.equals("") || newThemeName != null) {

							if (CommonLib.sendKeys(driver, themeNameInputBox(30), newThemeName,
									"Theme Name: " + newThemeName, action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, newThemeName + " value has been passed in Theme Name", YesNo.No);
							} else {
								log(LogStatus.ERROR, newThemeName + " value is not passed in Theme Name", YesNo.No);
								return false;
							}
						}
						if (!newThemeDescription.isEmpty() || !newThemeDescription.equals("")
								|| newThemeDescription != null) {

							if (sendKeys(driver, themeDescription(10), newThemeDescription,
									"newThemeDescription: " + newThemeDescription, action.SCROLLANDBOOLEAN)) {

								log(LogStatus.INFO, newThemeDescription + " value has been passed in Theme Description",
										YesNo.No);

							} else {
								log(LogStatus.ERROR, newThemeDescription + " value is not passed in Theme Description",
										YesNo.No);
								return false;
							}

						}

						if (SaveOrCancel) {
							if (CommonLib.click(driver, saveButton(30), "Theme save button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on save button", YesNo.No);

								CommonLib.ThreadSleep(2000);
								CommonLib.refresh(driver);
								CommonLib.ThreadSleep(2000);
								if (CommonLib.sendKeysAndPressEnter(driver, themeSearchBox(20), newThemeName,
										"Theme Search Box ", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, newThemeName + " value has been passed in Theme Search Box",
											YesNo.No);

									if (recordInTableOfTheme(newThemeName, 15) != null) {
										log(LogStatus.INFO, "Verified Theme " + newThemeName + " Has Been Created",
												YesNo.No);

										flag = true;

									} else {
										log(LogStatus.ERROR, "Theme " + newThemeName + " is not created", YesNo.No);

									}

								} else {
									log(LogStatus.ERROR, newThemeName + " value is not passed in Theme Search Box",
											YesNo.No);

								}

							} else {
								log(LogStatus.ERROR, "Not able to click on save button", YesNo.No);

							}

						}

						else {
							if (CommonLib.click(driver, copyThemeSaveOrCancelButton("Cancel", 10),
									"copyThemeSaveOrCancelButton: " + "Cancel", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Cancel Button", YesNo.No);
								CommonLib.ThreadSleep(5000);
								if (themeNameInputBox(6) == null) {
									log(LogStatus.INFO, "---Verified Popup Has Been Closed----", YesNo.No);
									flag = true;
								} else {
									log(LogStatus.ERROR, "---Not Verified Popup Has Been Closed----", YesNo.No);
									return false;
								}

							} else {
								log(LogStatus.ERROR, "Not Able to Click on Cancel Button", YesNo.No);
								return false;

							}
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on the No Button", YesNo.No);
					}

				}

			} else {
				log(LogStatus.ERROR, "Not able to click on the New Theme Button", YesNo.No);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabName + " Tab", YesNo.No);

		}

		return flag;

	}
	
	public ArrayList<String> verifyRedirectionOnClickOnFieldRelatedToThemeAndSortingInTaggedSection(
			boolean allRecordsRedirectionCheck, TaggedName taggedName, String themeTabNameOfPropertyFile,
			List<String> expectedThemeRelatedFieldToCheckRedirection, List<String> expectedThemeRecordsToCheckPageName,
			boolean sortingCheck) {

		ArrayList<String> result = new ArrayList<String>();

		if (click(driver, getTaggedRecordName(taggedName.toString(), 30), taggedName.toString() + " tab",
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on " + taggedName + " tab name", YesNo.No);
			ThreadSleep(5000);

			if (allRecordsRedirectionCheck) {

				Integer loopCount = 0;
				List<String> actualThemeRecordsText = recordsNamesOnTaggedSection(taggedName.toString(), 30).stream()
						.map(x -> x.getText()).collect(Collectors.toList());
				for (WebElement themeRecord : recordsNamesOnTaggedSection(taggedName.toString(), 30)) {
					if (CommonLib.clickUsingJavaScript(driver, themeRecord, "Records on " + taggedName + " Tagged",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on record on " + taggedName + " tab", YesNo.No);

						String id = switchOnWindow(driver);
						if (id != null) {
							if (themeRecordPageName(themeTabNameOfPropertyFile, actualThemeRecordsText.get(loopCount),
									20) != null) {
								log(LogStatus.INFO,
										"The page is redirecting to " + actualThemeRecordsText.get(loopCount)
												+ " tab after click on Entity type of " + taggedName,
										YesNo.No);
							} else {
								log(LogStatus.ERROR,
										"The page is not redirecting to " + actualThemeRecordsText.get(loopCount)
												+ " tab after click on Entity type of " + taggedName,
										YesNo.No);
								result.add("The page is not redirecting to " + actualThemeRecordsText.get(loopCount)
										+ " tab after click on Entity type of " + taggedName);
							}
							driver.close();
							driver.switchTo().window(id);
						} else {
							log(LogStatus.ERROR,
									"The new tab is not opening after clicking on entity type of " + taggedName,
									YesNo.No);
							result.add("The new tab is not opening after clicking on entity type of " + taggedName);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on record on " + taggedName + " tab", YesNo.No);
						result.add("Not able to click on record on " + taggedName + " tab");
					}
					loopCount++;
				}

			} else if (!expectedThemeRelatedFieldToCheckRedirection.isEmpty()) {
				Integer loopCount = 0;
				for (String expectedThemeRelatedFieldRecordToCheckRedirection : expectedThemeRelatedFieldToCheckRedirection) {

					if (CommonLib.clickUsingJavaScript(driver,
							recordsNameOnTaggedSection(taggedName.toString(),
									expectedThemeRelatedFieldRecordToCheckRedirection, 30),
							"Records on " + taggedName + " Tagged", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on record on " + taggedName + " tab", YesNo.No);

						String id = switchOnWindow(driver);
						if (id != null) {
							if (themeRecordPageName(themeTabNameOfPropertyFile,
									expectedThemeRecordsToCheckPageName.get(loopCount), 20) != null) {
								log(LogStatus.INFO,
										"The page is redirecting to "
												+ expectedThemeRecordsToCheckPageName.get(loopCount)
												+ " tab after click on Entity type of "
												+ expectedThemeRelatedFieldRecordToCheckRedirection,
										YesNo.No);
							} else {
								log(LogStatus.ERROR,
										"The page is not redirecting to "
												+ expectedThemeRecordsToCheckPageName.get(loopCount)
												+ " tab after click on Entity type of "
												+ expectedThemeRelatedFieldRecordToCheckRedirection,
										YesNo.No);
								result.add("The page is not redirecting to "
										+ expectedThemeRecordsToCheckPageName.get(loopCount)
										+ " tab after click on Entity type of "
										+ expectedThemeRelatedFieldRecordToCheckRedirection);
							}
							driver.close();
							driver.switchTo().window(id);
						} else {
							log(LogStatus.ERROR,
									"The new tab is not opening after clicking on entity type of " + taggedName,
									YesNo.No);
							result.add("The new tab is not opening after clicking on entity type of " + taggedName);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on record on " + taggedName + " tab", YesNo.No);
						result.add("Not able to click on record on " + taggedName + " tab");
					}

					loopCount++;

				}

			} else {
				log(LogStatus.ERROR,
						"Either Provide data or set true to the function to check redirection for all record",
						YesNo.No);
				result.add("Either Provide data or set true to the function to check redirection for all record");
			}

			if (sortingCheck) {
				List<String> actualThemeRecordsText = recordsNamesOnTaggedSection(taggedName.toString(), 30).stream()
						.map(x -> x.getText()).collect(Collectors.toList());
				if (actualThemeRecordsText.size() > 0) {

					List<String> expectedThemeRecordsText = new ArrayList<String>(actualThemeRecordsText);

					Collections.sort(expectedThemeRecordsText);
					Integer loopCount = 0;
					for (String expectedThemeRecordText : expectedThemeRecordsText) {

						if (expectedThemeRecordText.equals(actualThemeRecordsText.get(loopCount))) {
							log(LogStatus.INFO, "---Sorting in Ascending Matched and i.e.: "
									+ actualThemeRecordsText.get(loopCount) + "---", YesNo.No);
						}

						else {
							log(LogStatus.ERROR,
									"---Sorting in Ascending not Matched, Expected Data: " + expectedThemeRecordText
											+ " but Actual: " + actualThemeRecordsText.get(loopCount) + "---",
									YesNo.No);
							result.add("---Sorting in Ascending not Matched, Expected Data: " + expectedThemeRecordText
									+ " but Actual: " + actualThemeRecordsText.get(loopCount) + "---");
						}

						loopCount++;
					}

				} else {
					log(LogStatus.ERROR, "Either Locator Changed or No data Present under " + taggedName
							+ " of Tagged Section, So Not Able to Check Sorting", YesNo.No);
					result.add("Either Locator Changed or No data Present under " + taggedName
							+ " of Tagged Section, So Not Able to Check Sorting");
				}

			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + taggedName + " tab name", YesNo.No);
			result.add("Not able to click on " + taggedName + " tab name");
		}

		return result;
	}
}
