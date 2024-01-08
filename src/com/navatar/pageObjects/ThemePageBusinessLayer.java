package com.navatar.pageObjects;

import static com.navatar.generic.BaseLib.sa;
import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.sql.DriverAction;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
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

	public List<String> verifyTableOnCallListTab(List<String> expectedListOfTabs) {

		List<String> negativeResult = new ArrayList<String>();

		if (!expectedListOfTabs.isEmpty()) {

			if (expectedListOfTabs.size() != 1 && !expectedListOfTabs.get(0).equals("")) {
				List<String> tabsInPage = getMyCallListTableHeader().stream().map(x -> x.getText().trim())
						.collect(Collectors.toList());

				if (!getMyCallListTableHeader().isEmpty()) {
					log(LogStatus.INFO, "No. of Headers Present on Page are: " + getMyCallListTableHeader().size(), YesNo.No);

					int i = 0;
					if (getMyCallListTableHeader().size() == expectedListOfTabs.size()) {
						log(LogStatus.INFO, "No. of Actual and Expected Headers on Page are same, So Continue the Process",
								YesNo.No);

						for (String tab : tabsInPage) {
							if (tab.equals(expectedListOfTabs.get(i))) {
								log(LogStatus.INFO, "----Header Matched, Expected: " + expectedListOfTabs.get(i)
										+ " & Actual: " + tab + " on this Page----", YesNo.No);
							} else {

								log(LogStatus.ERROR, "----Header Not Matched, Expected: " + expectedListOfTabs.get(i)
										+ " but Actual: " + tab + " on this Page----", YesNo.No);
								negativeResult.add("----Header Not Matched, Expected: " + expectedListOfTabs.get(i)
										+ " but Actual: " + tab + " on this Page----");

							}

							i++;
						}
					} else {
						log(LogStatus.ERROR,
								"No. of Expected and Actual Headers on Page not matched, So not able to continue, Expected: "
										+ expectedListOfTabs + " & Actual: " + tabsInPage,
								YesNo.Yes);
						negativeResult.add(
								"No. of Expected and Actual Headers on Page not matched, So not able to continue, Expected: "
										+ expectedListOfTabs + " & Actual: " + tabsInPage);
					}

				}

				else {
					log(LogStatus.ERROR, "No Headers Are Present on this Page", YesNo.Yes);
					negativeResult.add("No Headers Are Present on this Page");
				}
			} else

			{
				log(LogStatus.ERROR, "No Expected Headers to verify on Page Mentioned", YesNo.No);
				negativeResult.add("No Expected Headers to verify On Page Mentioned");
			}
		} else

		{
			log(LogStatus.ERROR, "No Expected Headers to verify on Page Mentioned", YesNo.No);
			negativeResult.add("No Expected Headers to verify On Page Mentioned");
		}

		return negativeResult;
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

	public List<String> recordOnImportContactTable(String[] FirmsWithRecordTypes, int timeOut) {
		List<String> result = new ArrayList<String>();
		for (String FirmWithRecordType : FirmsWithRecordTypes) {
			String[] Firms = FirmWithRecordType.split("<break>");
			String Firm = Firms[0];
			String RecordType = Firms[1];
			
			if (FirmInImportContacts(Firm,timeOut).isDisplayed()) {
				result.add(Firm + " is visible");
					if(RecordTypeInImportContacts(RecordType,timeOut).isDisplayed()) {
						result.add(RecordType + " is visible");
					}
			}
		}
		return result;
	}
	

	public class ResearchDataContainer {

		public String value;
		public String tabNames;
		public String tabValue;
		public String field;
		public String operator;
		public String values;
		public action action;
		public int timeout;

		public ResearchDataContainer(String value, String tabNames, String tabValue, String field, String operator,
				String values, action action, int timeout) {
			this.value = value;
			this.tabNames = tabNames;
			this.tabValue = tabValue;
			this.field = field;

			this.operator = operator;
			this.values = values;
			this.action = action;
			this.timeout = timeout;
		}

		// Getters and setters for the fields
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

		if (addToThemeObjectSelection == null && "".equals(addToThemeObjectSelection)) {
//			if (addToThemeObjectSelection != null && !"".equals(addToThemeObjectSelection)) {

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

		if (!clickonPlusIconOrNot && addToThemeFromActionButton && reasearchRecordFromAdvanced == null) {
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

		} else if (clickonPlusIconOrNot && !addToThemeFromActionButton && reasearchRecordFromAdvanced == null) {
			ThreadSleep(2000);
			CommonLib.clickUsingJavaScript(driver, addToThemePopUpSearchBox2(20),
					"Theme Search Box" , action.SCROLLANDBOOLEAN);
			ThreadSleep(2000);
			if (sendKeys(driver, addToThemePopUpSearchBox2(20), recordName, "Theme Search Box", action.BOOLEAN)) {
				log(LogStatus.INFO, "Able to Pass the Value:  " + recordName, YesNo.No);

				if (CommonLib.click(driver, addToThemePopUpSearchBoxDropDownValue2(recordName, 20),
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
//						if (CommonLib.click(driver, advancedCollapsedExpandButtonInAddToTheme(10),
//								"advancedCollapsedExpandButtonInAddToTheme", action.SCROLLANDBOOLEAN)) {
//							log(LogStatus.INFO, "Click on Advanced Button", YesNo.No);

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

	//						}

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

}
