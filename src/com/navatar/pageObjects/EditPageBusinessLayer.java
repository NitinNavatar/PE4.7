package com.navatar.pageObjects;

import static com.navatar.generic.AppListeners.appLog;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

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
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.SmokeFSIns1;

public class EditPageBusinessLayer extends EditPage implements EditPageErrorMessage {

	public EditPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the editPageSeachTextBox
	 */
	public WebElement getEditPageSeachValueLink(String projectName, String searchValue, int timeOut) {
		String xpath = "//span[text()='" + searchValue + "']";
		WebElement ele = FindElement(driver, xpath, "Search Value : " + searchValue, action.BOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "Search Value : " + searchValue);
	}

	public boolean addingSubTab(String subTab, String searchText, String sourceImage, String targetImage) {
		boolean flag = false;
		String xpath = "";
		WebElement ele = null;
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		if (hp.clickOnEditPageLinkOnSetUpLink()) {
			log(LogStatus.INFO, "click on Edit Page SetUp Link", YesNo.No);
			// scn.nextLine();
			switchToDefaultContent(driver);
			switchToFrame(driver, 60, getEditPageFrame("", 120));
			// relatedTab=relatedTabs[i];
			ele = ip.getRelatedTab("", subTab, 5);
			if (ele != null) {
				log(LogStatus.INFO, "Sub Tab is Already Added : " + subTab, YesNo.No);
			} else {
				String relatedTab = "Details";
				if (clickUsingJavaScript(driver, ip.getRelatedTab("", relatedTab, 30), relatedTab.toString(),
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on Sub Tab : " + relatedTab, YesNo.No);
					ThreadSleep(2000);
					switchToDefaultContent(driver);
					if (click(driver, getAddTab(10), "Add TAB", action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on Add Tab Link", YesNo.No);
						if (click(driver, getPageTabEle(relatedTab, 20), relatedTab, action.BOOLEAN)) {
							log(LogStatus.INFO, "Click on " + relatedTab, YesNo.No);
							List<WebElement> eleList = allOptionsInDropDrop(driver, getSubTabDropdownListt(20), "");
							for (WebElement webElement : eleList) {
								System.err.println(">>>>>>>> " + webElement.getText().trim());
							}
							if (selectVisibleTextFromDropDown(driver, getSubTabDropdownListt(20),
									"Sub tab drop down list", subTab)) {
								log(LogStatus.INFO, "Able to on select  " + subTab, YesNo.No);

							} else {
								BaseLib.sa.assertTrue(false, "Not Able to on select  " + subTab);
								log(LogStatus.SKIP, "Not Able to on select  " + subTab, YesNo.Yes);
							}
							if (click(driver, getdoneButton(20), "Done Button", action.BOOLEAN)) {
								log(LogStatus.INFO, "Click on Done Button", YesNo.No);
								switchToDefaultContent(driver);
								switchToFrame(driver, 60, getEditPageFrame("", 120));
								relatedTab = subTab;
								if (clickUsingJavaScript(driver, ip.getRelatedTab("", relatedTab, 120),
										relatedTab.toString(), action.BOOLEAN)) {
									log(LogStatus.INFO, "Click on Sub Tab : " + relatedTab, YesNo.No);
									if (searchAndDragDropinEditPage(searchText, sourceImage, targetImage)) {
										log(LogStatus.INFO, "Able to DragNDrop : " + searchText, YesNo.No);
										flag = true;

									} else {
										BaseLib.sa.assertTrue(false, "Not Able to DragNDrop : " + searchText);
										log(LogStatus.FAIL, "Not Able to DragNDrop : " + searchText, YesNo.Yes);
									}

								} else {
									BaseLib.sa.assertTrue(false, "Not Able to Click on Sub Tab : " + relatedTab);
									log(LogStatus.SKIP, "Not Able to Click on Sub Tab : " + relatedTab, YesNo.Yes);
								}
							} else {
								BaseLib.sa.assertTrue(false, "Not Able to on Click on Done Button");
								log(LogStatus.SKIP, "Not Able to on Click on Done Button", YesNo.Yes);
							}
						} else {
							BaseLib.sa.assertTrue(false, "Not Able to on Click on " + relatedTab);
							log(LogStatus.SKIP, "Not Able to on Click on " + relatedTab, YesNo.Yes);
						}

					} else {
						BaseLib.sa.assertTrue(false, "Not Able to on Click on Add Tab Link");
						log(LogStatus.SKIP, "Not Able to on Click on Add Tab Link", YesNo.Yes);
					}
				} else {
					BaseLib.sa.assertTrue(false, "Not Able to Click on Sub Tab : " + relatedTab);
					log(LogStatus.SKIP, "Not Able to Click on Sub Tab : " + relatedTab, YesNo.Yes);
				}
			}
		} else {
			log(LogStatus.ERROR, "Not Able to click on Edit Page SetUp Link", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "Not Able to click on Edit Page SetUp Link");
		}

		return flag;
	}

	public boolean searchAndDragDropinEditPage(String searchText, String sourceImage, String targetImage) {
		boolean flag = false;
		switchToDefaultContent(driver);
		;
		String sValue = searchText;
		if (sendKeys(driver, getEditPageSeachTextBox("", 10), sValue, "Search TextBox", action.BOOLEAN)) {
			ThreadSleep(2000);
			log(LogStatus.INFO, "send value to Search TextBox : " + sValue, YesNo.No);
			if (dragNDropUsingScreen("", sourceImage, targetImage, 20)) {
				log(LogStatus.INFO, "Able to DragNDrop : " + sValue, YesNo.No);
				ThreadSleep(2000);
				if (click(driver, getEditPageSaveButton("", 10), "Edit Page Save Button", action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on Edit Page Save Button", YesNo.No);
					flag = true;
					ThreadSleep(10000);

				} else {
					BaseLib.sa.assertTrue(false, "Not Able to Click on Edit Page Save Button");
					log(LogStatus.FAIL, "Not Able to Click on Edit Page Save Button", YesNo.Yes);
				}

			} else {
				BaseLib.sa.assertTrue(false, "Not Able to DragNDrop : " + sValue);
				log(LogStatus.FAIL, "Not Able to DragNDrop : " + sValue, YesNo.Yes);
			}

		} else {
			BaseLib.sa.assertTrue(false, "Not Able to send value to Search TextBox : " + sValue);
			log(LogStatus.FAIL, "Not Able to send value to Search TextBox : " + sValue, YesNo.Yes);
		}

		return flag;
	}

	/**
	 * @author Ravi Kumar
	 * @param tabToBeAdded
	 * @param timeOut
	 * @return true if all Tab added successfully
	 */
	public List<String> addRelatedTabOnEditPage_Lighting(String projectName, String tabToBeAdded, String typeoftab,
			int timeOut) {

		switchToFrame(driver, 20, getEditPageFrame(projectName, timeOut));
		List<String> result = new ArrayList<String>();
		String splitedTabs = typeoftab;
		ThreadSleep(5000);
		if (clickUsingJavaScript(driver, getRelatedTabBar(timeOut), "All Tab Button", action.SCROLLANDBOOLEAN)) {
			appLog.info("clicked on all tabs icon");
			ThreadSleep(3000);
			switchToDefaultContent(driver);
			if (click(driver, getAddTabEditPageLink(timeOut), "Add a Tab Link", action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on add a tab link");
				List<WebElement> element = getAlreadyAddedTabListEditPage(timeOut);
				if (click(driver, getAlreadyAddedTabListEditPage(timeOut).get(element.size() - 1), "Add a Tab Link",
						action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on added tab link");
					ThreadSleep(3000);
					if (selectVisibleTextFromDropDown(driver, getTabLabelDropdownOnEditPage(timeOut),
							"Available Tab List", splitedTabs)) {
						appLog.info(splitedTabs + " is selected successfully in available tabs");

						if (splitedTabs.equalsIgnoreCase("Custom")) {
							ThreadSleep(2000);
							if (sendKeysAndPressEnter(driver, getTabLabelInputOnEditPage(timeOut), tabToBeAdded,
									"tab label input box", action.BOOLEAN)) {
								appLog.error("enter tab label name:" + tabToBeAdded);
								ThreadSleep(2000);
								if (clickUsingJavaScript(driver, getTabLabelDoneButtonOnEditPage(timeOut),
										"tab label done button", action.SCROLLANDBOOLEAN)) {
									appLog.error("clicked on tab label done button");
								} else {
									result.add("Not able to clicked on tab label done button");
									appLog.error("Not able to clicked on tab label done button");
								}
							} else {
								result.add("Not able enter tab label name:" + tabToBeAdded);
								appLog.error("Not able enter tab label name:" + tabToBeAdded);
							}
						} else {
							ThreadSleep(5000);
							if (clickUsingJavaScript(driver, getTabLabelDoneButtonOnEditPage(timeOut),
									"tab label done button", action.SCROLLANDBOOLEAN)) {
								appLog.error("clicked on tab label done button");
							} else {
								result.add("Not able to clicked on tab label done button");
								appLog.error("Not able to clicked on tab label done button");
							}
						}

					} else {
						result.add(splitedTabs + " is not selected  in available tabs");
						appLog.error(splitedTabs + " is not selected  in available tabs");
					}
					ThreadSleep(5000);
					if (click(driver, getSaveButtonOnEditPage(60), "Custom Tab Save Button", action.SCROLLANDBOOLEAN)) {
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
	 * @author Azhar Alam
	 * @param projectName
	 * @param searchValue
	 * @param timeOut
	 * @return true if able to click on data provider text box search box icon
	 */
	public boolean clickOnELGSeachValueLink(String projectName, String searchValue, int timeOut) {
		click(driver, getElgDataProviderTextBoxSearchIcon(projectName, 30), searchValue, action.BOOLEAN);
		String xpath = "//div[contains(@id,'dropdown-element')]//*[@title='" + searchValue + "']";
		WebElement ele = FindElement(driver, xpath, "Search Value : " + searchValue, action.BOOLEAN, timeOut);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, "Search Value : " + searchValue);
		return click(driver, ele, searchValue, action.BOOLEAN);
	}

	/**
	 * @author Azhar Alam
	 * @return true if able to click on Edit Link
	 */
	public boolean clickOnEditPageLink() {
		boolean flag = false;
		if (click(driver, getSettingLink_Lighting(20), "setting icon", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "clicked on setting icon", YesNo.No);
			ThreadSleep(1000);
			if (click(driver, getEditPageLink_Lighting(20), "edit page link", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on edit page link", YesNo.No);
				flag = true;
			} else {
				log(LogStatus.ERROR, "Not able to click on edit page link", YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on setting icon", YesNo.Yes);
		}
		return flag;
	}

	/**
	 * @author Ankit Jaiswal
	 * @param projectName
	 * @param pageName
	 * @param relatedTab
	 * @param DropComponentName
	 * @param fieldSetApiName
	 * @return true if successfully drag N drop layout on Edit Page
	 */
	public boolean dragAndDropLayOutFromEditPage(String projectName, PageName pageName, RelatedTab relatedTab,
			String DropComponentName, String fieldSetApiName, String imageFieldName) {
		boolean flag = false;
		WebElement ele = null, dropComponentXpath = null, dropLocation = null;
		if (switchToFrame(driver, 30, getEditPageFrame(projectName, 30))) {

			if (clickUsingJavaScript(driver, getAddComponent("",20), "Add component button", action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on add component button", YesNo.No);
				switchToDefaultContent(driver);
				if (sendKeys(driver, getEditPageSeachTextBox(projectName, 10), DropComponentName,
						DropComponentName + " component xpath", action.BOOLEAN)) {
					log(LogStatus.INFO, "Enter component name in search box : " + DropComponentName, YesNo.No);
					String xpath = "//span[@title='" + DropComponentName + "' or text()='" + DropComponentName
							+ "']";
					ele=FindElement(driver, xpath, DropComponentName+"  component", action.SCROLLANDBOOLEAN, 30);
					if(clickUsingJavaScript(driver, ele, DropComponentName))
					{
						log(LogStatus.INFO, "Clicked on component : "+DropComponentName, YesNo.No);
					ThreadSleep(500);
					if (sendKeys(driver, getFieldSetNameTextBox(10), fieldSetApiName,
							"field set name text box", action.BOOLEAN)) {
						log(LogStatus.INFO, "field set name : " + fieldSetApiName, YesNo.No);

						if (imageFieldName != null) {
							if (sendKeys(driver, getImageFieldNameTextBox(10), imageFieldName,
									"image field name text box", action.BOOLEAN)) {
								log(LogStatus.INFO, "image field set name : " + imageFieldName,
										YesNo.No);

							} else {
								log(LogStatus.ERROR, "Not able to enter image field set name : "
										+ imageFieldName + " so cannot add field set", YesNo.Yes);
								clickUsingJavaScript(driver, getBackButton(10), "back button",
										action.BOOLEAN);
								return false;
							}
						}
						if (click(driver, getCustomTabSaveBtn(projectName, 10), "save button",
								action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on save button", YesNo.No);
							ThreadSleep(7000);
							if (clickUsingJavaScript(driver, getBackButton(10), "back button",
									action.BOOLEAN)) {
								log(LogStatus.PASS, "clicked on back button", YesNo.No);
								flag = true;
							} else {
								log(LogStatus.ERROR,
										"Not able to click on back button so cannot back on page ",
										YesNo.Yes);
							}
						} else {
							log(LogStatus.ERROR,
									"Not able to click on save button so cannot add field set : "
											+ fieldSetApiName,
											YesNo.No);
						}
					} else {
						log(LogStatus.ERROR, "Not able to enter field set name : " + fieldSetApiName
								+ " so cannot add field set", YesNo.Yes);
					}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on component "+DropComponentName, YesNo.Yes);
					}

				} else {
					log(LogStatus.ERROR, "Not able to search on component so cannot drag and drop component "
							+ DropComponentName + " in " + relatedTab.toString(), YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Cannot switch in edit page iframe cannot drag and drop component " + DropComponentName
						+ " in " + relatedTab.toString(), YesNo.Yes);
			}
		}
			switchToDefaultContent(driver);
			return flag;

		}

	/**
	 * @return the editPageSeachTextBox
	 */
	public WebElement getElementWithText(String projectName, String searchValue, int timeOut) {
		String xpath = "//*[text()='" + searchValue + "']/ancestor::div[@role='tablist']/..";
		WebElement ele = FindElement(driver, xpath, "Search Value : " + searchValue, action.BOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "Search Value : " + searchValue);
	}

	/**
	 * @return the dragNDropUsingScreen
	 */
	public boolean dragNDropUsingScreen(String projectName, WebElement source, String imagePath, int timeOut) {
		boolean flag = false;
		Actions act = new Actions(driver);
		// WebElement source=getEditPageSeachValueLink(projectName, sValue, 10);
		act.clickAndHold(source).build().perform();
		ThreadSleep(1000);
		Screen screen = new Screen();
		try {
			screen.dropAt(imagePath);
			Robot robot = new Robot();
			ThreadSleep(1000);
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK); // press left click
			ThreadSleep(1000);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			flag = true;
		} catch (FindFailed | AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * @return the dragNDropUsingScreen
	 */
	public boolean dragNDropUsingJscript(WebDriver driver, WebElement source, WebElement target) {
		boolean flag = false;
		try {
			final String java_script = "var src=arguments[0],tgt=arguments[1];var dataTransfer={dropEffe"
					+ "ct:'',effectAllowed:'all',files:[],items:{},types:[],setData:fun"
					+ "ction(format,data){this.items[format]=data;this.types.append(for"
					+ "mat);},getData:function(format){return this.items[format];},clea"
					+ "rData:function(format){}};var emit=function(event,target){var ev"
					+ "t=document.createEvent('Event');evt.initEvent(event,true,false);"
					+ "evt.dataTransfer=dataTransfer;target.dispatchEvent(evt);};emit('"
					+ "dragstart',src);emit('dragenter',tgt);emit('dragover',tgt);emit("
					+ "'drop',tgt);emit('dragend',src);";
			((JavascriptExecutor) driver).executeScript(java_script, source, target);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * @return the editPageSeachTextBox
	 */
	public WebElement getElementWithText1(String projectName, String searchValue, int timeOut) {
		String xpath = "//*[text()='" + searchValue + "']/ancestor::article";
		WebElement ele = FindElement(driver, xpath, "Search Value : " + searchValue, action.BOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "Search Value : " + searchValue);
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param pageName
	 * @param DropComponentName
	 * @param fieldValues
	 * @param source
	 * @param target
	 * @return true if drap N drop successfully
	 */
	public boolean dragAndDropAccordian(String projectName, PageName pageName, String DropComponentName,
			String[] fieldValues, String source, String target) {
		boolean flag = true;
		Actions actions = new Actions(driver);
		WebElement ele = null, dropComponentXpath = null, dropLocation = null;
		if (switchToFrame(driver, 30, getEditPageFrame(projectName, 30))) {
			log(LogStatus.INFO, "Click on Sub Tab : " + RelatedTab.Investment, YesNo.No);
			ThreadSleep(2000);
			switchToDefaultContent(driver);
			if (sendKeys(driver, getEditPageSeachTextBox(projectName, 10), DropComponentName,
					DropComponentName + " component xpath", action.BOOLEAN)) {
				log(LogStatus.INFO, "Enter component name in search box : " + DropComponentName, YesNo.No);
				ThreadSleep(10000);
				if (dragNDropUsingScreen(projectName, source, target, 10)) {

					String value = "", field = "";
					for (String fieldValue : fieldValues) {
						value = fieldValue.split("<break>")[1];
						field = fieldValue.split("<break>")[0];
						if (sendKeys(driver, getFieldTextbox(projectName, field, 10), value, field, action.BOOLEAN)) {
							log(LogStatus.INFO, "field : " + field + ", value: " + value, YesNo.No);
						} else {
							log(LogStatus.ERROR, "Not able to enter : " + value + " on" + field, YesNo.Yes);
							flag = false;
						}
					}
					if (!isSelected(driver, getexpandedCheckbox(projectName, 10), "expanded")) {
						if (click(driver, getexpandedCheckbox(projectName, 10), "expanded", action.BOOLEAN)) {

							log(LogStatus.INFO, "expanded checkbox is now selected", YesNo.No);
						} else {
							log(LogStatus.ERROR, "could not click on expanded", YesNo.No);
							flag = false;
						}

					} else {
						log(LogStatus.INFO, "expanded checkbox is already selected", YesNo.No);

					}
					if (click(driver, getCustomTabSaveBtn(projectName, 10), "save button", action.BOOLEAN)) {
						log(LogStatus.INFO, "clicked on save button", YesNo.No);
						ThreadSleep(2000);
						actions.moveToElement(getBackButton(10)).build().perform();
						ThreadSleep(2000);
						if (clickUsingJavaScript(driver, getBackButton(10), "back button", action.BOOLEAN)) {
							log(LogStatus.PASS, "clicked on back button", YesNo.No);
						} else {
							log(LogStatus.ERROR, "Not able to click on back button so cannot back on page ", YesNo.Yes);
							flag = false;
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on save button so cannot create accordion : ",
								YesNo.No);
					}
				} else {
					log(LogStatus.ERROR, "Drop location is not visible in list so cannot drag and drop component "
							+ DropComponentName, YesNo.Yes);
					flag = false;
				}
			} else {
				log(LogStatus.ERROR,
						"Not able to search on component so cannot drag and drop component " + DropComponentName,
						YesNo.Yes);
				flag = false;
			}
		} else {
			log(LogStatus.ERROR,
					"Cannot switch in edit page iframe cannot drag and drop component " + DropComponentName, YesNo.Yes);
			flag = false;
		}
		switchToDefaultContent(driver);
		return flag;

	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param tabName
	 * @return
	 */
	public WebElement clickOnAccordion(String projectName, TabName tabName) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String xpath = "//div[contains(@class,'RelatedListAccordion')]/following-sibling::article//header//a[contains(text(),'"
				+ bp.getTabName(projectName, tabName) + "')]/ancestor::article/../following-sibling::*";
		WebElement ele = FindElement(driver, xpath, "accordian", action.BOOLEAN, 10);
		return isDisplayed(driver, ele, "visiblity", 10, "acordion");
	}

	/**
	 * @author Akul Bhutani
	 * @param fieldName
	 * @return Contact SDG Query
	 */
	public String ContactSDGQuery(String fieldName) {
		return "SELECT Id, Name, Title," + fieldName
				+ " navpeII__Sector__c, navpeII__Region__c,navpeII__Profile_Image__c FROM Contact WHERE (AccountId = '<<recordId>>') ORDER BY Name ASC";
	}

	/**
	 * @author Akul Bhutani
	 * @param fieldName
	 * @return Contact SDG Query
	 */
	public String InstitutionSDGQuery(String fieldName) {
		return "SELECT Id, Name, Title, Email, Phone" + fieldName
				+ " FROM Contact WHERE (AccountId = '<<recordId>>') ORDER BY Name ASC";
	}

	/**
	 * @author Akul Bhutani
	 * @param fieldName
	 * @return Deal Team SDG Query
	 */
	public String DealTeamSDGQuery(String fieldName) {
		return "SELECT navpeII__Team_Member__c,navpeII__Team_Member__r.Name,navpeII__Team_Member__r.Title,navpeII__Deal_Contact_Type__c,"
				+ fieldName
				+ "navpeII__Team_Member_Role__c,navpeII__Team_Member__r.MediumPhotoUrl FROM navpeII__Deal_Team__c WHERE(navpeII__Deal__c = '<<recordId>>' AND (navpeII__Team_Member__c <> null)) ORDER BY Id ASC";
		// return "SELECT navpeII__Team_Member__c,
		// navpeII__Team_Member__r.Name,navpeII__Team_Member__r.Title,"+fieldName+"navpeII__Team_Member_Role__c,navpeII__Deal_Contact_Type__c,navpeII__Team_Member__r.MediumPhotoUrl
		// FROM navpeII__Deal_Team__c WHERE ( Name = '<<recordId>>' AND
		// (navpeII__Team_Member__c <> null)) ORDER BY Id ASC";
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param source
	 * @param target
	 * @param pageName
	 * @param relatedTab
	 * @param DropComponentName
	 * @param fieldValues
	 * @return true if successfully drag And Drop Calender
	 */
	public boolean dragAndDropCalender(String projectName, String source, String target, PageName pageName,
			RelatedTab relatedTab, String DropComponentName, String[] fieldValues) {
		boolean flag = false;
		Actions actions = new Actions(driver);
		String value = "", field = "";
		appLog.info(">>>>");
		ThreadSleep(3000);
		dragNDropUsingScreen(projectName, source, target, 10);
		ThreadSleep(3000);
		for (String fieldValue : fieldValues) {
			value = fieldValue.split("<break>")[1];
			field = fieldValue.split("<break>")[0];
			field = field.replace("_", " ");
			if (sendKeys(driver, getFieldTextbox(projectName, field, 10), value, field, action.BOOLEAN)) {
				log(LogStatus.INFO, "field : " + field + ", value: " + value, YesNo.No);
			} else {
				log(LogStatus.ERROR, "Not able to enter : " + value + " on" + field, YesNo.Yes);
				flag = false;
			}
		}
		if (click(driver, getCustomTabSaveBtn(projectName, 10), "save button", action.BOOLEAN)) {
			log(LogStatus.INFO, "clicked on save button", YesNo.No);
			ThreadSleep(2000);
			actions.moveToElement(getBackButton(10)).build().perform();
			ThreadSleep(2000);
			if (clickUsingJavaScript(driver, getBackButton(10), "back button", action.BOOLEAN)) {
				log(LogStatus.PASS, "clicked on back button", YesNo.No);
				flag = true;
			} else {
				log(LogStatus.ERROR, "Not able to click on back button so cannot back on page ", YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on save button so cannot create accordion : ", YesNo.No);
		}
		return flag;
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @return true if click on calender Edit Page
	 */
	public boolean clickOnCalenderEditPage(String projectName) {
		String xpath = "//button[text()='Their Events']/../../../../../preceding-sibling::div";
		WebElement ele = FindElement(driver, xpath, "accordian", action.BOOLEAN, 10);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(
				"return arguments[0].setAttribute('class','flexipageEditorNode flexipageEditorElement flexipageEditorComponent sf-interactions-liveSelected sf-interactions-proxyToolboxVisible')",
				ele);
		clickUsingJavaScript(driver, ele, "calender move component");

		return true;
	}

	/**
	 * @author Ankur Huria
	 * @param projectName
	 * @param source
	 * @param target
	 * @param pageName
	 * @param relatedTab
	 * @param DropComponentName
	 * @param fieldValues
	 */

	/* NOTE: SDG Component will added to above of the Deal Component in Home Page */

	public boolean addSDGComponentToRefrencedComponentAndVerifyColumnsAndNoOfRecords(String projectName,
			List<String> fieldsInComponent, String ComponentName, String Title, String DataProviderNameAfterColon,
			int NoOfRecordsLessThanEqualHundred) {
		boolean flag = false;
		int status = 0;
		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CommonLib.switchToFrame(driver, 50, getAppBuilderIframe(50));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		CommonLib.clickUsingJavaScript(driver, dealHeader(20), "Deal Element");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		WebElement addComp = new WebDriverWait(driver,Duration.ofSeconds(25)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(
				"//div[@class='sf-interactions-proxy sf-interactions-proxyAddComponent sf-interactions-proxyAddComponentBefore']")));
		js.executeScript("arguments[0].setAttribute('style.display', 'block')", addComp);
		CommonLib.clickUsingJavaScript(driver, driver.findElement(By.xpath(
				"//div[@class='sf-interactions-proxy sf-interactions-proxyAddComponent sf-interactions-proxyAddComponentBefore']/a")),
				"Add Link");

		CommonLib.switchToDefaultContent(driver);
		if (CommonLib.sendKeys(driver, getComponentSearchBox(50), ComponentName, "SearchBox",
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, ComponentName + "has been Search", YesNo.No);

			if (CommonLib.click(driver, getNavatarSDGBtn(50), "Navatar SDG Button", action.SCROLLANDBOOLEAN)) {

				log(LogStatus.INFO, "Navatar SDG Button has been clicked", YesNo.No);
				if (CommonLib.switchToFrame(driver, 50, getAppBuilderIframe(50))) {
					if (CommonLib.checkElementVisibility(driver, afterAddComponentMsg(50), "Component Msg", 20)) {
						log(LogStatus.INFO, "Component Msg is Visible, So Component Added to Page", YesNo.No);

						CommonLib.switchToDefaultContent(driver);

						if (CommonLib.sendKeys(driver, getTitle(50), Title, "Title", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Title has been Entered", YesNo.No);

							if (CommonLib.getSelectedOptionOfDropDown(driver, getDataProvider(50),
									getDataProviderDropDownList(30), "Data Provider",
									"CustomObject:" + DataProviderNameAfterColon)) {
								log(LogStatus.INFO, "SDG Data Provider has been searched: CustomObject:"
										+ DataProviderNameAfterColon, YesNo.No);

								if (CommonLib.click(driver, getSaveButton(50), " Save Button",
										action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, " save button has been clicked", YesNo.No);

									if (changesSavedMsg(50) != null) {
										log(LogStatus.INFO, "Changes Saved Msg Displayed", YesNo.No);
										status++;

									} else {
										log(LogStatus.ERROR, "Changes Saved Msg Not Displayed", YesNo.Yes);

									}

								}

								else {
									log(LogStatus.ERROR, "Could not be click on save button", YesNo.Yes);
									sa.assertTrue(false, "-----------Not Able to Add SDG: " + Title + " ------------");

								}

							} else {
								log(LogStatus.ERROR, "Could not be Search the SDG Data Provider CustomObject:"
										+ DataProviderNameAfterColon, YesNo.Yes);
							}
						} else {
							log(LogStatus.ERROR, "Could not be entered the Title: " + Title, YesNo.Yes);
						}
					} else {
						log(LogStatus.ERROR, "Could not be click on the Add to component button", YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR, "Component not Added to Referenced Component", YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "Could not click on the Navatar SDG", YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "Could not be Search the item" + ComponentName, YesNo.Yes);

		}

		if (status > 0) {

			if (CommonLib.click(driver, getEditPageBackButton(projectName, 50), "Edit Page Back Button",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Edit Page Back Button", YesNo.No);
				WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + Title + "']",
						"Component Title ", action.SCROLLANDBOOLEAN, 10);
				if (alreadyAddedComponentToHomePage != null) {

					log(LogStatus.INFO, "Component Title Matched to Home Page " + Title, YesNo.Yes);

					String xPath = "//a[text()=\"" + Title + "\"]/ancestor::article/preceding-sibling::lightning-icon";
					WebElement ele = FindElement(driver, xPath, "Expend or Collaps icon", action.BOOLEAN, 20);
					String text = getAttribute(driver, ele, "Expend or Collaps icon", "title");
					if (text.equals("Expand")) {
						clickUsingJavaScript(driver, ele, "Expend icon");
					}

					WebElement pageSizeSelect = FindElement(driver,
							"//a[text()='" + Title
									+ "']/ancestor::article//span[text()='Page Size']/../parent::div//select",
							"Page Size Select ", action.SCROLLANDBOOLEAN, 10);
					if (CommonLib.selectVisibleTextFromDropDown(driver, pageSizeSelect, "Page Size Select", "100")) {
						log(LogStatus.INFO, "Selected the Page Size", YesNo.No);
						try {
							Thread.sleep(30000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						List<WebElement> records = FindElements(driver,
								"//a[text()='" + Title + "']/ancestor::article//tbody/tr", "Records");
						System.out.println("No. of Records Present: " + records.size());
						if (records.size() == NoOfRecordsLessThanEqualHundred) {
							log(LogStatus.INFO, "No. of Records Matched: " + NoOfRecordsLessThanEqualHundred, YesNo.No);
							status++;

						} else {
							log(LogStatus.ERROR, "No. of Records not Matched: " + NoOfRecordsLessThanEqualHundred,
									YesNo.No);
							sa.assertTrue(false, "-----------No. of Records not Matched: "
									+ NoOfRecordsLessThanEqualHundred + "--------------");

						}
					} else {
						log(LogStatus.ERROR, "Not Able To Select Page Size ", YesNo.No);

					}

					List<WebElement> columns = FindElements(driver, "//a[text()=\"" + Title
							+ "\"]/ancestor::article//thead//th[contains(@class,\"navpeI\")]//span[contains(@class,\"slds-truncate\")]",
							"Records");
					List<String> columnsText = new ArrayList<String>();
					for (WebElement column : columns) {
						columnsText.add(column.getText());
					}
					
					if (CommonLib.compareList(columnsText, fieldsInComponent)) {
						log(LogStatus.INFO, "All Fields are Matched ", YesNo.No);

						status++;

					} else {
						log(LogStatus.ERROR, "All Fields are not Matched", YesNo.No);
						sa.assertTrue(false, "-----------All Fields are not Matched------------");

					}

				}

				else {
					log(LogStatus.ERROR, "Component Title Not Matched to Home Page :" + Title, YesNo.No);

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on Edit Page Back Button", YesNo.No);

			}

		}

		if (status == 3)
			return flag = true;

		else
			return flag = false;

	}

	/**
	 * @author Ankur Huria
	 * @param Title
	 * @param fieldsInComponent
	 */

	public boolean verifyColumnsOfSDG(String Title, List<String> fieldsInComponent) {
		boolean flag = false;
		boolean resultFlag = false;
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + Title + "']",
				"Component Title ", action.SCROLLANDBOOLEAN, 10);
		if (alreadyAddedComponentToHomePage != null) {

			log(LogStatus.INFO, "Component Title Matched to Home Page " + Title, YesNo.Yes);

			if (!home.sdgGridExpandedByDefaultIfNotThenExpandByTooltip(Title)) {
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
				List<WebElement> columns = FindElements(driver, "//a[text()=\"" + Title
						+ "\"]/ancestor::article//thead//th[contains(@class,\"navpeI\")]//span[contains(@class,\"slds-truncate\")]",
						"Records");
				List<String> columnsText = new ArrayList<String>();
				for (WebElement column : columns) {
					columnsText.add(column.getText().toLowerCase());
				}
				
				fieldsInComponent = fieldsInComponent.stream().map(x -> x.toLowerCase()).collect(Collectors.toList());
				if (CommonLib.compareList(columnsText, fieldsInComponent)) {
					log(LogStatus.INFO, "All Fields are Matched ", YesNo.No);
					resultFlag = true;

				} else {
					log(LogStatus.ERROR, "All Fields are not Matched", YesNo.No);

				}

			} else {
				log(LogStatus.ERROR, "Component Title Not Matched to Home Page :" + Title, YesNo.No);

			}
		}
		return resultFlag;

	}

	public boolean verifySDGExpandByDefault(String Title) {
		boolean flag = false;
		WebElement expandElement = FindElement(driver,
				"//a[text()='" + Title + "']/ancestor::article//div[@class='slds-hide']/following-sibling::div",
				"Expand Element of SDG: " + Title, action.SCROLLANDBOOLEAN, 10);
		if (expandElement != null) {

			log(LogStatus.INFO, "Expand Element Found of SDG: " + Title, YesNo.Yes);

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

	public boolean editPageAndAddSDG(String labelName, String tableName, String dataProviderName) {
		WebElement ele;

		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		if (clickOnEditPageLink()) {
			ThreadSleep(4000);
			CommonLib.switchToFrame(driver, 50, getAppBuilderIframe(90));
			ThreadSleep(10000);

			if (AddComponentLinkButton(30) != null) {
				if (CommonLib.clickUsingJavaScript(driver, AddComponentLinkButton(50), "Add to component",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Add to component button has been clicked", YesNo.No);
				} else {
					log(LogStatus.ERROR, "Could not be click on the Add to component button", YesNo.Yes);
					return false;
				}
			} else {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				CommonLib.clickUsingJavaScript(driver, getsldHeader(50), "Deal Element");
				WebElement addComp = new WebDriverWait(driver,Duration.ofSeconds(25)).until(ExpectedConditions.presenceOfElementLocated(By
						.xpath("//div[@class='sf-interactions-proxy sf-interactions-proxyAddComponent sf-interactions-proxyAddComponentBefore']")));

				js.executeScript("arguments[0].setAttribute('style.display', 'block')", addComp);
				CommonLib.clickUsingJavaScript(driver, driver.findElement(By.xpath(
						"//div[@class='sf-interactions-proxy sf-interactions-proxyAddComponent sf-interactions-proxyAddComponentBefore']/a")),
						"Add Link");

			}
			ThreadSleep(2000);
			CommonLib.switchToDefaultContent(driver);
			if (CommonLib.sendKeys(driver, getSearchonAppBuilder(50), "Navatar SDG", "SearchBox",
					action.SCROLLANDBOOLEAN)) {
				ThreadSleep(2000);
				log(LogStatus.INFO, "Navatar SDG has been Search", YesNo.No);
				if (CommonLib.click(driver, getNavatarSDGBtn(50), "Navatar SDG Button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Navatar SDG Button has been clicked", YesNo.No);
					ThreadSleep(2000);
					if (CommonLib.sendKeys(driver, getTitle(50), tableName, "Title", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Title has been Entered", YesNo.No);
						ThreadSleep(2000);
						if (CommonLib.getSelectedOptionOfDropDown(driver, getDataProvider(50),
								getDataProviderDropDownList(30), "Data Provider", dataProviderName)) {
							log(LogStatus.INFO, "SDG Data Provider has been searched", YesNo.No);
							ThreadSleep(2000);
							if (CommonLib.click(driver, getEditAppSaveButton(50), "App builder Save Button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "App Builder save button has been clicked", YesNo.No);
								if (CommonLib.checkElementVisibility(driver, getsaveConfirmationMessage(90),
										"Save Button", 90)) {
									log(LogStatus.INFO, "SDG has been saved", YesNo.No);
									if (CommonLib.click(driver, getbBackIcon(50), "", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Back icon has been clicked", YesNo.No);
										CommonLib.ThreadSleep(9000);
										try {
											ele = new WebDriverWait(driver, Duration.ofSeconds(50))
													.until(ExpectedConditions.presenceOfElementLocated(
															By.xpath("//a[text()='" + tableName + "']")));
										} catch (Exception ex) {
											ex.printStackTrace();
											log(LogStatus.ERROR, "Could not found the " + tableName + " Element",
													YesNo.Yes);
											return false;
										}

										if (CommonLib.isElementPresent(ele)) {
											log(LogStatus.INFO, "SDG has been added", YesNo.No);
											return true;
										} else {
											log(LogStatus.ERROR, "SDG is not added", YesNo.Yes);
											return false;
										}
									} else {
										log(LogStatus.ERROR, "Could not click on back icon", YesNo.Yes);
										return false;
									}
								} else {
									log(LogStatus.ERROR, "Could not click on save button", YesNo.Yes);
									return false;
								}
							}

							else {
								log(LogStatus.ERROR, "Could not be click on save button", YesNo.Yes);
								return false;
							}

						} else {
							log(LogStatus.ERROR, "Could not be Search the SDG Data Provider", YesNo.Yes);
							return false;
						}
					} else {
						log(LogStatus.ERROR, "Could not be entered the Title", YesNo.Yes);
						return false;
					}
				} else {
					log(LogStatus.ERROR, "Could not click on the Navatar SDG", YesNo.Yes);
					return false;
				}
			} else {
				log(LogStatus.ERROR, "Could not be Search the item", YesNo.Yes);
				return false;

			}

		}

		else {
			log(LogStatus.ERROR, "Could not click on the Edit Page", YesNo.Yes);
			return false;
		}
	}

	public boolean editMyRecordCheckboxOnAppPage(Condition condition) {
		boolean flag = false;
		if (clickOnEditPageLink()) {
			CommonLib.switchToFrame(driver, 50, getAppBuilderIframe(90));
			ThreadSleep(30000);

			if (CommonLib.clickUsingJavaScript(driver, getcustomFilterComponent(50), "Custom filter component",
					action.BOOLEAN)) {
				ThreadSleep(8000);
				log(LogStatus.INFO, "Custom filter component has been created", YesNo.No);
				CommonLib.switchToDefaultContent(driver);
				if (condition.toString().equals("SelectCheckbox")) {
					if (!CommonLib.isSelected(driver, getmyRecordFilterCheckbox(50), "My Record Checkbox")) {

						if (CommonLib.click(driver, getmyRecordFilterCheckbox(50), "My Record Checkbox",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "My Record Checkbox has been clicked", YesNo.No);

							if (CommonLib.click(driver, getEditAppSaveButton(50), "App builder Save Button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "App Builder save button has been clicked", YesNo.No);
								if (CommonLib.checkElementVisibility(driver, getsaveConfirmationMessage(90),
										"Save Button", 90)) {
									log(LogStatus.INFO, "SDG has been saved", YesNo.No);
									if (CommonLib.click(driver, getbBackIcon(50), "", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Back icon has been clicked", YesNo.No);
										CommonLib.ThreadSleep(9000);
										flag = true;
									} else {
										log(LogStatus.ERROR, "Could not click on the Back icon", YesNo.Yes);
									}
								} else {
									log(LogStatus.ERROR, "save confirmation message is not visible", YesNo.Yes);

								}
							}

							else {
								log(LogStatus.ERROR, "Could not click on save button", YesNo.Yes);

							}
						} else {
							log(LogStatus.ERROR, "Could not click on the My Record Checkbox button", YesNo.No);
							return false;
						}
					} else {
						log(LogStatus.INFO, "My Record Checkbox is already selected", YesNo.No);
						if (CommonLib.click(driver, getbBackIcon(50), "", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Back icon has been clicked", YesNo.No);
							CommonLib.ThreadSleep(9000);
							flag = true;
						} else {
							log(LogStatus.ERROR, "Could not click on the Back icon", YesNo.Yes);
						}
					}
				}
				if (condition.toString().equals("UnSelectCheckbox")) {
					if (CommonLib.isSelected(driver, getmyRecordFilterCheckbox(50), "My Record Checkbox")) {
						if (CommonLib.click(driver, getmyRecordFilterCheckbox(50), "My Record Checkbox",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "My Record Checkbox has been clicked", YesNo.No);
							if (CommonLib.click(driver, getEditAppSaveButton(50), "App builder Save Button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "App Builder save button has been clicked", YesNo.No);
								if (CommonLib.checkElementVisibility(driver, getsaveConfirmationMessage(90),
										"Save Button", 90)) {
									log(LogStatus.INFO, "SDG has been saved", YesNo.No);
									if (CommonLib.click(driver, getbBackIcon(50), "", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Back icon has been clicked", YesNo.No);
										CommonLib.ThreadSleep(9000);
										flag = true;
									} else {
										log(LogStatus.ERROR, "Could not click on the Back icon", YesNo.Yes);
									}
								} else {
									log(LogStatus.ERROR, "save confirmation message is not visible", YesNo.Yes);

								}
							}

							else {
								log(LogStatus.ERROR, "Could not click on save button", YesNo.Yes);
							}
						} else {
							log(LogStatus.ERROR, "Could not click on the My Record Checkbox button", YesNo.No);
							return false;
						}
					} else {
						log(LogStatus.INFO, "My Record Checkbox is already unselected", YesNo.No);

						if (CommonLib.click(driver, getbBackIcon(50), "", action.SCROLLANDBOOLEAN)) {

							log(LogStatus.INFO, "Back icon has been clicked", YesNo.No);
							CommonLib.ThreadSleep(9000);
							flag = true;
						} else {
							log(LogStatus.ERROR, "Could not click on the Back icon", YesNo.Yes);
						}
					}
				}
			} else {
				log(LogStatus.ERROR, "Could not click on custom filter component", YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Could not open the edit page link", YesNo.Yes);
		}
		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param projectName
	 * @param source
	 * @param target
	 * @param pageName
	 * @param relatedTab
	 * @param DropComponentName
	 * @param fieldValues
	 */

	public boolean addSDGComponentToRefrencedComponent(String projectName, String ComponentName, String Title,
			String DataProviderNameAfterColon, String referencedComponentHeading) {
		boolean flag = false;
		int status = 0;

		if (clickOnEditPageLink()) {
			CommonLib.ThreadSleep(50000);

			CommonLib.switchToFrame(driver, 50, getAppBuilderIframe(50));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			CommonLib.clickUsingJavaScript(driver, sdgHeaderElement(referencedComponentHeading, 40),
					referencedComponentHeading);
			CommonLib.ThreadSleep(2000);
			WebElement addComp = new WebDriverWait(driver, Duration.ofSeconds(25)).until(ExpectedConditions.presenceOfElementLocated(By
					.xpath("//div[@class='sf-interactions-proxy sf-interactions-proxyAddComponent sf-interactions-proxyAddComponentBefore']")));
			js.executeScript("arguments[0].setAttribute('style.display', 'block')", addComp);
			CommonLib.clickUsingJavaScript(driver, driver.findElement(By.xpath(
					"//div[@class='sf-interactions-proxy sf-interactions-proxyAddComponent sf-interactions-proxyAddComponentBefore']/a")),
					"Add Link");

			CommonLib.switchToDefaultContent(driver);
			if (CommonLib.sendKeys(driver, getComponentSearchBox(50), ComponentName, "SearchBox",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, ComponentName + "has been Search", YesNo.No);

				if (CommonLib.click(driver, getNavatarSDGBtn(50), "Navatar SDG Button", action.SCROLLANDBOOLEAN)) {

					log(LogStatus.INFO, "Navatar SDG Button has been clicked", YesNo.No);
					if (CommonLib.switchToFrame(driver, 50, getAppBuilderIframe(50))) {
						if (CommonLib.checkElementVisibility(driver, afterAddComponentMsg(50), "Component Msg", 20)) {
							log(LogStatus.INFO, "Component Msg is Visible, So Component Added to Page", YesNo.No);
							ThreadSleep(2000);
							CommonLib.switchToDefaultContent(driver);
							ThreadSleep(2000);
							if (CommonLib.sendKeys(driver, getTitle(50), Title, "Title", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Title has been Entered", YesNo.No);

								if (CommonLib.getSelectedOptionOfDropDown(driver, getDataProvider(50),
										getDataProviderDropDownList(30), "Data Provider",
										"CustomObject:" + DataProviderNameAfterColon)) {
									log(LogStatus.INFO, "SDG Data Provider has been searched: CustomObject:"
											+ DataProviderNameAfterColon, YesNo.No);

									if (CommonLib.click(driver, getSaveButton(50), " Save Button",
											action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, " save button has been clicked", YesNo.No);
										CommonLib.ThreadSleep(3000);
										status++;

									}

									else {
										log(LogStatus.ERROR, "Could not be click on save button", YesNo.Yes);
										sa.assertTrue(false,
												"-----------Not Able to Add SDG: " + Title + " ------------");

									}

								} else {
									log(LogStatus.ERROR, "Could not be Search the SDG Data Provider CustomObject:"
											+ DataProviderNameAfterColon, YesNo.Yes);
								}
							} else {
								log(LogStatus.ERROR, "Could not be entered the Title: " + Title, YesNo.Yes);
							}
						} else {
							log(LogStatus.ERROR, "Could not be click on the Add to component button", YesNo.Yes);
						}
					} else {
						log(LogStatus.ERROR, "Component not Added to Referenced Component", YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR, "Could not click on the Navatar SDG", YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Could not be Search the item" + ComponentName, YesNo.Yes);

			}
		}

		else {
			log(LogStatus.ERROR, "Could not click on the Edit Page", YesNo.Yes);
			return false;
		}

		if (status > 0) {

			if (CommonLib.click(driver, getEditPageBackButton(projectName, 50), "Edit Page Back Button",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Edit Page Back Button", YesNo.No);
				WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + Title + "']",
						"Component Title ", action.SCROLLANDBOOLEAN, 10);
				if (alreadyAddedComponentToHomePage != null) {

					log(LogStatus.INFO, "Component Title Matched to Home Page " + Title, YesNo.Yes);
					status++;

				}

				else {
					log(LogStatus.ERROR, "Component Title Not Matched to Home Page :" + Title, YesNo.No);

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on Edit Page Back Button", YesNo.No);

			}

		}

		if (status == 2)
			return flag = true;

		else
			return flag = false;

	}

	/**
	 * @author Ankur Huria
	 * @param projectName
	 * @param source
	 * @param target
	 * @param pageName
	 * @param relatedTab
	 * @param DropComponentName
	 * @param fieldValues
	 */

	public boolean addTabAndSDGComponentIntoThatTab(String projectName, String ComponentName, String Title,
			String DataProviderNameAfterColon, String referencedTabName, String tabLabel, String tabName,
			String dropTabTo) {
		int status = 0;
		boolean addComponentLinkFlag = false;

		if (clickOnEditPageLink()) {
			CommonLib.ThreadSleep(50000);

			CommonLib.switchToFrame(driver, 50, getAppBuilderIframe(50));
			CommonLib.clickUsingJavaScript(driver, tabNameElementInEditPageComponent(referencedTabName, 40),
					referencedTabName);
			CommonLib.ThreadSleep(2000);

			CommonLib.switchToDefaultContent(driver);
			if (CommonLib.click(driver, addTabButtonInEditPage(50), "Add Tab Button", action.SCROLLANDBOOLEAN)) {

				log(LogStatus.INFO, "Add Tab Button has been Clicked", YesNo.No);

				if (CommonLib.click(driver, detailTabCreatedAfterAddTab(50), "detailTabCreatedAfterAddTab",
						action.SCROLLANDBOOLEAN)) {

					log(LogStatus.INFO, "Detail Tab Link has been Clicked", YesNo.No);
					if (CommonLib.selectVisibleTextFromDropDown(driver, tabLabelSelectElement(30),
							"tabLabelSelectElement", tabLabel)) {
						if (CommonLib.sendKeys(driver, customLabelInputBox(50), tabName, "Custom Label Input Box",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Entered the Value: " + tabName + " in Custom Label Input Box",
									YesNo.No);
							CommonLib.ThreadSleep(2000);
							if (CommonLib.click(driver, doneButtonDivInTabLabel(50), "OutSide of Done Button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on OutSide of Done Button", YesNo.No);

								if (CommonLib.click(driver, doneButtonInTabLabel(50), "Done Button",
										action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Done Button", YesNo.No);

									CommonLib.ThreadSleep(4000);
									if (CommonLib.dragNDropField(driver, tabNameElementInEditPage(tabName, 30),
											tabNameElementInEditPage(dropTabTo, 30))) {
										log(LogStatus.INFO, "Successfully Drag the Tab: " + tabName + " And Drop it to "
												+ dropTabTo, YesNo.No);
										CommonLib.ThreadSleep(3000);

										if (CommonLib.switchToFrame(driver, 50, getAppBuilderIframe(50))) {
											log(LogStatus.INFO, "Successfully Switched to the Frame", YesNo.No);
											if (CommonLib.clickUsingJavaScript(driver,
													tabNameElementInEditPageComponent(tabName, 50), "Tab: " + tabName,
													action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, "Clicked on Tab: " + tabName + " Inside Page",
														YesNo.No);

												CommonLib.ThreadSleep(4000);
												if (CommonLib.clickUsingJavaScript(driver,
														addComponentLinkInTabSection(50),
														"Add Component Here Link in Tab: " + tabName,
														action.SCROLLANDBOOLEAN)) {
													log(LogStatus.INFO,
															"Clicked on Add Component Here Link in Tab: " + tabName,
															YesNo.No);
													CommonLib.ThreadSleep(5000);
													addComponentLinkFlag = true;
												}

												else {
													log(LogStatus.ERROR,
															"Not Able to Click on Add Component Here Link in Tab: "
																	+ tabName,
															YesNo.Yes);
													sa.assertTrue(false,
															"Not Able to Click on Add Component Here Link in Tab: "
																	+ tabName);

												}
											}

											else {
												log(LogStatus.ERROR,
														"Not Able to Click on Tab: " + tabName + " Inside Page",
														YesNo.Yes);
												sa.assertTrue(false,
														"Not Able to Click on Tab: " + tabName + " Inside Page");

											}
										}

										else {
											log(LogStatus.ERROR, "Not Successfully Switch to the Frame", YesNo.Yes);
											sa.assertTrue(false, "Not Successfully Switch to the Frame");

										}
									}

									else {
										log(LogStatus.ERROR, "Not Successfully Drag the Tab: " + tabName
												+ " And Drop it to " + dropTabTo, YesNo.Yes);
										sa.assertTrue(false, "Not Successfully Drag the Tab: " + tabName
												+ " And Drop it to " + dropTabTo);

									}

								}

								else {
									log(LogStatus.ERROR, "Not Able to Click on Done Button", YesNo.Yes);
									sa.assertTrue(false, "Not Able to Click on Done Button");

								}

							}

							else {
								log(LogStatus.ERROR, "Not Able to Click on OutSide of Done Button", YesNo.Yes);
								sa.assertTrue(false, "Not Able to Click on OutSide of Done Button");

							}

						} else {
							log(LogStatus.ERROR,
									"Not Able to Entered the Value: " + tabName + " in Custom Label Input Box",
									YesNo.Yes);

						}

						log(LogStatus.INFO, tabLabel + " Value has been Selected from DropDown", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Could not Select the Value: " + tabLabel + " from DropDown", YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR, "Could not click on the Detail Tab Link", YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "Could not click on the Add Tab Button", YesNo.Yes);
			}

			CommonLib.switchToDefaultContent(driver);
			if (addComponentLinkFlag) {
				if (CommonLib.sendKeys(driver, getComponentSearchBox(50), ComponentName, "SearchBox",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, ComponentName + "has been Search", YesNo.No);

					if (CommonLib.click(driver, getNavatarSDGBtn(50), "Navatar SDG Button", action.SCROLLANDBOOLEAN)) {

						log(LogStatus.INFO, "Navatar SDG Button has been clicked", YesNo.No);
						if (CommonLib.switchToFrame(driver, 50, getAppBuilderIframe(50))) {
							if (CommonLib.checkElementVisibility(driver, afterAddComponentMsg(50), "Component Msg",
									20)) {
								log(LogStatus.INFO, "Component Msg is Visible, So Component Added to Page", YesNo.No);

								CommonLib.switchToDefaultContent(driver);

								if (CommonLib.sendKeys(driver, getTitle(50), Title, "Title", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Title has been Entered", YesNo.No);

									if (CommonLib.getSelectedOptionOfDropDown(driver, getDataProvider(50),
											getDataProviderDropDownList(30), "Data Provider",
											"CustomObject:" + DataProviderNameAfterColon)) {
										log(LogStatus.INFO, "SDG Data Provider has been searched: CustomObject:"
												+ DataProviderNameAfterColon, YesNo.No);

										if (CommonLib.click(driver, getSaveButton(50), " Save Button",
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, " save button has been clicked", YesNo.No);
											if (changesSavedMsg(50) != null) {
												log(LogStatus.INFO, "Changes Saved Msg Displayed", YesNo.No);
												status++;
											} else {
												log(LogStatus.ERROR, "Changes Saved Msg Not Displayed", YesNo.Yes);
												sa.assertTrue(false, "Changes Saved Msg Not Displayed");

											}

										}

										else {
											log(LogStatus.ERROR, "Could not be click on save button", YesNo.Yes);
											sa.assertTrue(false,
													"-----------Not Able to Add SDG: " + Title + " ------------");

										}

									} else {
										log(LogStatus.ERROR, "Could not be Search the SDG Data Provider CustomObject:"
												+ DataProviderNameAfterColon, YesNo.Yes);
									}
								} else {
									log(LogStatus.ERROR, "Could not be entered the Title: " + Title, YesNo.Yes);
								}
							} else {
								log(LogStatus.ERROR, "Could not be click on the Add to component button", YesNo.Yes);
							}
						} else {
							log(LogStatus.ERROR, "Component not Added to Referenced Component", YesNo.Yes);
						}
					} else {
						log(LogStatus.ERROR, "Could not click on the Navatar SDG", YesNo.Yes);
					}

				} else {
					log(LogStatus.ERROR, "Could not be Search the item" + ComponentName, YesNo.Yes);

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Clicked on Add Component Button, So Not Able to Continue", YesNo.Yes);

			}
		}

		else {
			log(LogStatus.ERROR, "Could not click on the Edit Page", YesNo.Yes);
			return false;
		}

		if (status > 0) {

			if (CommonLib.click(driver, getEditPageBackButton(projectName, 50), "Edit Page Back Button",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Edit Page Back Button", YesNo.No);
				WebElement ele1 = getRelatedTab(projectName, RelatedTab.SDG_Tab.toString().replace("_", " "), 10);
				click(driver, ele1, RelatedTab.SDG_Tab.toString().replace("_", " "), action.BOOLEAN);
				ThreadSleep(2000);
				WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + Title + "']",
						"Component Title ", action.SCROLLANDBOOLEAN, 10);
				if (alreadyAddedComponentToHomePage != null) {

					log(LogStatus.INFO, "Component Title Matched to Home Page " + Title, YesNo.Yes);
					status++;

				}

				else {
					log(LogStatus.ERROR, "Component Title Not Matched to Home Page :" + Title, YesNo.No);

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on Edit Page Back Button", YesNo.No);

			}

		}

		if (status == 2)
			return true;

		else
			return false;

	}

	public boolean editPageAndAddFilter(String label1, String query1, String label2, String query2, String label3,
			String query3, Condition myRecordCheckbox) {
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		CommonLib.refresh(driver);
		if (clickOnEditPageLink()) {
			CommonLib.switchToFrame(driver, 50, getAppBuilderIframe(90));
			ThreadSleep(20000);

			JavascriptExecutor js = (JavascriptExecutor) driver;
			CommonLib.clickUsingJavaScript(driver, getsldHeader(50), "SDG Header Element", action.SCROLLANDBOOLEAN);
			WebElement addComp = new WebDriverWait(driver, Duration.ofSeconds(25)).until(ExpectedConditions.presenceOfElementLocated(By
					.xpath("//div[@class='sf-interactions-proxy sf-interactions-proxyAddComponent sf-interactions-proxyAddComponentBefore']")));
			js.executeScript("arguments[0].setAttribute('style.display', 'block')", addComp);
			if (CommonLib.clickUsingJavaScript(driver, driver.findElement(By.xpath(
					"//div[@class='sf-interactions-proxy sf-interactions-proxyAddComponent sf-interactions-proxyAddComponentBefore']/a")),
					"Add Link")) {
				log(LogStatus.INFO, "Add component plus icon has been clicked", YesNo.No);
				CommonLib.switchToDefaultContent(driver);
				if (CommonLib.sendKeys(driver, getSearchonAppBuilder(50), "Navatar Custom Filter For SDG", "SearchBox",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Navatar SDG has been Search", YesNo.No);
					if (CommonLib.click(driver, getcustomFilterForSDGButton(50), "Navatar cuntom filter Button",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Navatar SDG Button has been clicked", YesNo.No);
						if (CommonLib.sendKeys(driver, getlabel1(50), label1, "label 1", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "label1 has been Entered", YesNo.No);
							if (CommonLib.sendKeys(driver, getquery1(50), query1, "query 1", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "query1 has been Entered", YesNo.No);
								if (CommonLib.sendKeys(driver, getlabel2(50), label2, "Label 2",
										action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "label2 has been Entered", YesNo.No);
									if (CommonLib.sendKeys(driver, getquery2(50), query2, "Query 2",
											action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "query2 has been Entered", YesNo.No);
										if (CommonLib.sendKeys(driver, getlabel3(50), label3, "Label 3",
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "label3 has been Entered", YesNo.No);
											if (CommonLib.sendKeys(driver, getquery3(50), query3, "Query 3",
													action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, "query3 has been Entered", YesNo.No);

												if (myRecordCheckbox.toString().equals("SelectCheckbox")) {
													if (!CommonLib.isSelected(driver, getmyRecordFilterCheckbox(50),
															"My Record Checkbox")) {
														if (CommonLib.click(driver, getmyRecordFilterCheckbox(50),
																"My Record Checkbox", action.SCROLLANDBOOLEAN)) {
															log(LogStatus.INFO, "My Record Checkbox has been clicked",
																	YesNo.No);
														} else {
															log(LogStatus.ERROR,
																	"Not able to click on the My Record checkbox button",
																	YesNo.No);
															return false;
														}
													} else {
														log(LogStatus.INFO, "My Record Checkbox is already Selected",
																YesNo.No);
													}

												}
												if (myRecordCheckbox.toString().equals("UnSelectCheckbox")) {
													if (CommonLib.isSelected(driver, getmyRecordFilterCheckbox(50),
															"My Record Checkbox")) {
														if (CommonLib.click(driver, getmyRecordFilterCheckbox(50),
																"My Record Checkbox", action.SCROLLANDBOOLEAN)) {
															log(LogStatus.INFO, "My Record Checkbox has been clicked",
																	YesNo.No);
														} else {
															log(LogStatus.ERROR,
																	"Not able to click on the My Record checkbox button",
																	YesNo.No);
															return false;
														}
													} else {
														log(LogStatus.INFO, "My Record Checkbox is already Selected",
																YesNo.No);
													}
												}

												if (CommonLib.click(driver, getEditAppSaveButton(50),
														"App builder Save Button", action.SCROLLANDBOOLEAN)) {
													log(LogStatus.INFO, "App Builder save button has been clicked",
															YesNo.No);
													if (CommonLib.checkElementVisibility(driver,
															getsaveConfirmationMessage(90), "Save Button", 90)) {
														log(LogStatus.INFO, "SDG has been saved", YesNo.No);
														if (CommonLib.click(driver, getbBackIcon(50), "",
																action.SCROLLANDBOOLEAN)) {
															log(LogStatus.INFO, "Back icon has been clicked", YesNo.No);

															CommonLib.ThreadSleep(9000);

															if (CommonLib
																	.isElementPresent(getcustomFilterComponent(50))) {
																log(LogStatus.INFO,
																		"Filter has been added in the the SDG",
																		YesNo.No);
																return true;
															} else {
																log(LogStatus.ERROR, "Filter is not added in the SDG",
																		YesNo.Yes);
																return false;
															}
														} else {
															log(LogStatus.ERROR, "Could not click on back icon",
																	YesNo.Yes);
															return false;
														}
													} else {
														log(LogStatus.ERROR, "save confirmation message is not visible",
																YesNo.Yes);
														return false;
													}
												}

												else {
													log(LogStatus.ERROR, "Could not be click on save button",
															YesNo.Yes);
													return false;
												}
											} else {
												log(LogStatus.ERROR, "Could not entered the query 3", YesNo.Yes);
												return false;
											}
										} else {
											log(LogStatus.ERROR, "Could not entered the label 3", YesNo.Yes);
											return false;
										}
									} else {
										log(LogStatus.ERROR, "Could not entered the query 2", YesNo.Yes);
										return false;
									}
								} else {
									log(LogStatus.ERROR, "Could not entered the label 2", YesNo.Yes);
									return false;
								}
							}

							else {
								log(LogStatus.ERROR, "Could not entered the query 1", YesNo.Yes);
								return false;
							}
						} else {
							log(LogStatus.ERROR, "Could not entered the label 1", YesNo.Yes);
							return false;
						}
					} else {
						log(LogStatus.ERROR, "Could not click on the navatar custom filter button", YesNo.Yes);
						return false;
					}
				} else {
					log(LogStatus.ERROR, "Could not be Search the item", YesNo.Yes);
					return false;

				}
			} else {
				log(LogStatus.ERROR, "Could not be click on the Add to component button", YesNo.Yes);
				return false;
			}
		}

		else {
			log(LogStatus.ERROR, "Could not click on the Edit Page", YesNo.Yes);
			return false;
		}
	}


	
	
	
	/**
	 * @author Ankur Huria
	 * @param projectName
	 * @param source
	 * @param target
	 * @param pageName
	 * @param relatedTab
	 * @param DropComponentName
	 * @param fieldValues
	 */

	public List<String> addTabInEditPage(String projectName, String referencedTabName, String tabLabel, String tabName,
			String dropTabTo) {
		List<String> result = new ArrayList<String>();

		if (clickOnEditPageLink()) {
			CommonLib.ThreadSleep(50000);

			if (addCloseButton(5) != null) {
				CommonLib.click(driver, addCloseButton(5), "Close Button", action.SCROLLANDBOOLEAN);
			}
			if (addCloseButton(5) != null) {
				CommonLib.click(driver, addCloseButton(5), "Close Button", action.SCROLLANDBOOLEAN);
			}
			CommonLib.switchToFrame(driver, 50, getAppBuilderIframe(50));
			CommonLib.clickUsingJavaScript(driver, tabNameElementInEditPageComponent(referencedTabName, 40),
					referencedTabName);
			CommonLib.ThreadSleep(2000);

			CommonLib.switchToDefaultContent(driver);
			if (CommonLib.click(driver, addTabButtonInEditPage(50), "Add Tab Button", action.SCROLLANDBOOLEAN)) {

				log(LogStatus.INFO, "Add Tab Button has been Clicked", YesNo.No);

				if (CommonLib.click(driver, detailTabCreatedAfterAddTab(50), "detailTabCreatedAfterAddTab",
						action.SCROLLANDBOOLEAN)) {

					log(LogStatus.INFO, "Detail Tab Link has been Clicked", YesNo.No);
					if (CommonLib.selectVisibleTextFromDropDown(driver, tabLabelSelectElement(30),
							"tabLabelSelectElement", tabLabel)) {
						if (CommonLib.sendKeys(driver, customLabelInputBox(50), tabName, "Custom Label Input Box",
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Entered the Value: " + tabName + " in Custom Label Input Box",
									YesNo.No);
							CommonLib.ThreadSleep(2000);
							if (CommonLib.click(driver, doneButtonDivInTabLabel(50), "OutSide of Done Button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on OutSide of Done Button", YesNo.No);

								if (CommonLib.click(driver, doneButtonInTabLabel(50), "Done Button",
										action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Done Button", YesNo.No);

									CommonLib.ThreadSleep(4000);
									if (CommonLib.dragNDropField(driver, tabNameElementInEditPage(tabName, 30),
											tabNameElementInEditPage(dropTabTo, 30))) {
										log(LogStatus.INFO, "Successfully Drag the Tab: " + tabName + " And Drop it to "
												+ dropTabTo, YesNo.No);
										CommonLib.ThreadSleep(3000);

										if (CommonLib.switchToFrame(driver, 50, getAppBuilderIframe(50))) {
											log(LogStatus.INFO, "Successfully Switched to the Frame", YesNo.No);
											if (CommonLib.clickUsingJavaScript(driver,
													tabNameElementInEditPageComponent(tabName, 50), "Tab: " + tabName,
													action.SCROLLANDBOOLEAN)) {

												CommonLib.switchToDefaultContent(driver);

												if (CommonLib.click(driver, getSaveButton(50), " Save Button",
														action.SCROLLANDBOOLEAN)) {
													log(LogStatus.INFO, " save button has been clicked", YesNo.No);
													if (changesSavedMsg(50) != null) {
														log(LogStatus.INFO, "Changes Saved Msg Displayed", YesNo.No);

														if (CommonLib.click(driver,
																getEditPageBackButton(projectName, 50),
																"Edit Page Back Button", action.SCROLLANDBOOLEAN)) {
															log(LogStatus.INFO, "Clicked on Edit Page Back Button",
																	YesNo.No);

															if (getTabNameOnPage(tabName, 30) != null) {
																log(LogStatus.INFO,
																		"Tab: " + tabName + " Added on Page Verified",
																		YesNo.No);
															} else {
																log(LogStatus.ERROR,
																		"Tab: " + tabName + " Not Added to Page",
																		YesNo.No);

																result.add(
																		"Not Able to Click on Edit Page Back Button");
															}

														} else {
															log(LogStatus.ERROR,
																	"Not Able to Click on Edit Page Back Button",
																	YesNo.No);

															result.add("Not Able to Click on Edit Page Back Button");
														}

													} else {
														log(LogStatus.ERROR, "Changes Saved Msg Not Displayed",
																YesNo.Yes);

													}

												}

												else {
													log(LogStatus.ERROR, "Could not be click on save button",
															YesNo.Yes);

													result.add("Could not be click on save button");

												}

											}

											else {
												log(LogStatus.ERROR,
														"Not Able to Click on Tab: " + tabName + " Inside Page",
														YesNo.Yes);

												result.add("Not Able to Click on Tab: " + tabName + " Inside Page");

											}
										}

										else {
											log(LogStatus.ERROR, "Not Successfully Switch to the Frame", YesNo.Yes);
											result.add("Not Successfully Switch to the Frame");

										}
									}

									else {
										log(LogStatus.ERROR, "Not Successfully Drag the Tab: " + tabName
												+ " And Drop it to " + dropTabTo, YesNo.Yes);
										result.add("Not Successfully Drag the Tab: " + tabName + " And Drop it to "
												+ dropTabTo);

									}

								}

								else {
									log(LogStatus.ERROR, "Not Able to Click on Done Button", YesNo.Yes);
									result.add("Not Able to Click on Done Button");

								}

							}

							else {
								log(LogStatus.ERROR, "Not Able to Click on OutSide of Done Button", YesNo.Yes);
								result.add("Not Able to Click on OutSide of Done Button");

							}

						} else {
							log(LogStatus.ERROR,
									"Not Able to Entered the Value: " + tabName + " in Custom Label Input Box",
									YesNo.Yes);
							result.add("Not Able to Entered the Value: " + tabName + " in Custom Label Input Box");

						}

					} else {
						log(LogStatus.ERROR, "Could not Select the Value: " + tabLabel + " from DropDown", YesNo.Yes);
						result.add("Could not Select the Value: " + tabLabel + " from DropDown");
					}
				} else {
					log(LogStatus.ERROR, "Could not click on the Detail Tab Link", YesNo.Yes);
					result.add("Could not click on the Detail Tab Link");
				}
			} else {
				log(LogStatus.ERROR, "Could not click on the Add Tab Button", YesNo.Yes);
				result.add("Could not click on the Add Tab Button");
			}

			CommonLib.switchToDefaultContent(driver);

		}

		else {
			log(LogStatus.ERROR, "Could not click on the Edit Page", YesNo.Yes);
			result.add("Could not click on the Edit Page");

		}

		CommonLib.switchToDefaultContent(driver);
		return result;
	}

}
