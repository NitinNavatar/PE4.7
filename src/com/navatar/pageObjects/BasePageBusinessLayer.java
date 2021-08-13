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

import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class BasePageBusinessLayer extends BasePage implements BasePageErrorMessage{

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
		//	scrollDownThroughWebelement(driver, ele.get(i), "");
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
	public boolean removeUnusedTabs(){
		WebElement ele=null;
		List<String> lst=new ArrayList<String>();
		ele=FindElement(driver, "//a[contains(@title,'Reports')]", "Reports tab",
				action.SCROLLANDBOOLEAN, 10);
	 if(ele!=null){
		 lst=addRemoveCustomTab("Reports", customTabActionType.Remove);
		 if(!lst.isEmpty()){
			 for(int i=0; i<lst.size();i++){
				 BaseLib.sa.assertTrue(false, lst.get(i));
			 }
		 }
	 }
	 ThreadSleep(1000);
	 ele=FindElement(driver, "//a[contains(@title,'Dashboards')]", "Dashboards tab",
				action.SCROLLANDBOOLEAN, 10);
	 if(ele!=null){
		 lst= addRemoveCustomTab("Dashboards", customTabActionType.Remove);
		 lst.clear();
		 if(!lst.isEmpty()){
			 for(int i=0; i<lst.size();i++){
				 BaseLib.sa.assertTrue(false, lst.get(i));
			 }
		 }
	 }	
	 ThreadSleep(1000);
	 ele=FindElement(driver, "//a[contains(@title,'Marketing')]", "Marketing Initiatives tab",
				action.SCROLLANDBOOLEAN, 10);
	 if(ele!=null){
		 lst=addRemoveCustomTab("Marketing Initiatives", customTabActionType.Remove);
		 lst.clear();
		 if(!lst.isEmpty()){
			 for(int i=0; i<lst.size();i++){
				 BaseLib.sa.assertTrue(false, lst.get(i));
			 }
		 }
	 }
	 ThreadSleep(1000);
	 ele=FindElement(driver, "//a[contains(@title,'Navatar Setup')]", "Navatar setup tab",
				action.SCROLLANDBOOLEAN, 10);
	 if(ele!=null){
		 lst=addRemoveCustomTab("Navatar Setup", customTabActionType.Remove);
		 lst.clear();
		 if(!lst.isEmpty()){
			 for(int i=0; i<lst.size();i++){
				 BaseLib.sa.assertTrue(false, lst.get(i));
			 }
		 }
	 }
	 ThreadSleep(1000);
	 ele=FindElement(driver, "//a[contains(@title,'Navatar Deal')]", "Navatar Deal connect tab",
				action.SCROLLANDBOOLEAN, 10);
	 if(ele!=null){
		 lst=addRemoveCustomTab("Navatar Deal Connect", customTabActionType.Remove);
		 lst.clear();
		 if(!lst.isEmpty()){
			 for(int i=0; i<lst.size();i++){
				 BaseLib.sa.assertTrue(false, lst.get(i));
			 }
		 }
	 }	 
	 ThreadSleep(1000);
	 ele=FindElement(driver, "//a[contains(@title,'Pipelines')]", "Pipelines tab",
				action.SCROLLANDBOOLEAN, 10);
	 if(ele!=null){
		 lst=addRemoveCustomTab("Pipelines", customTabActionType.Remove);
		 lst.clear();
		 if(!lst.isEmpty()){
			 for(int i=0; i<lst.size();i++){
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
	public boolean verifyDate(String date, String dateFormat, String typeOfDate){
		if(dateFormat==null) {
			if(date.contains(getDateAccToTimeZone("America/New_York", "M/dd/yyyy"))){
				appLog.info(typeOfDate+" date is verified : "+getDateAccToTimeZone("America/New_York", "M/dd/yyyy"));
				return true;
			} else if (date.contains(getDateAccToTimeZone("America/New_York", "MM/dd/yyyy"))) {
				appLog.info(typeOfDate+" date is verified : "+getDateAccToTimeZone("America/New_York", "MM/dd/yyyy"));
				return true;
			} else if (date.contains(getDateAccToTimeZone("America/New_York", "dd/M/yyyy"))) {
				appLog.info(typeOfDate+" date is verified : "+getDateAccToTimeZone("America/New_York", "dd/M/yyyy"));
				return true;
			} else if (date.contains(getDateAccToTimeZone("America/New_York", "dd/MM/yyyy"))) {
				appLog.info(typeOfDate+" date is verified : "+getDateAccToTimeZone("America/New_York", "dd/MM/yyyy"));
				return true;
			}else if (date.contains(getDateAccToTimeZone("America/New_York",  "M/d/yyyy"))) {
				appLog.info(typeOfDate+" date is verified : "+getDateAccToTimeZone("America/New_York", "M/d/yyyy"));
				return true;
			}else if (date.contains(getDateAccToTimeZone("America/New_York",  "d/M/yyyy"))) {
				appLog.info(typeOfDate+" date is verified : "+getDateAccToTimeZone("America/New_York", "d/M/yyyy"));
				return true;
			}else {
				appLog.info(typeOfDate+" date is not verified. found result : "+date);
				appLog.info("Expected Date is : "+getDateAccToTimeZone("America/New_York","M/dd/yyyy")+ " or "+getDateAccToTimeZone("America/New_York", "MM/dd/yyyy")+" or "+getDateAccToTimeZone("America/New_York", "dd/M/yyyy")+" or "+getDateAccToTimeZone("America/New_York", "dd/MM/yyyy")+" or "+getDateAccToTimeZone("America/New_York", "M/d/yyyy"));
				return false;
			}
		}else {
			if(date.contains(getDateAccToTimeZone("America/New_York", dateFormat))){
				appLog.info(typeOfDate+" date is verified : "+getDateAccToTimeZone("America/New_York", dateFormat));
				return true;
			}else {
				appLog.info(typeOfDate+" date is not verified. found result : "+date);
				appLog.info("Expected Date is : "+getDateAccToTimeZone("America/New_York", dateFormat)+ " or "+getDateAccToTimeZone("America/New_York", dateFormat)+" or "+getDateAccToTimeZone("America/New_York", dateFormat)+" or "+date.contains(getDateAccToTimeZone("America/New_York", dateFormat)));
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
				if (click(driver, getSwitchToClassic(30), "sales force switch to classic link",action.SCROLLANDBOOLEAN)) {
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
	public boolean clickOnGridSection_Lightning(String projectName,RelatedList gridSectionName ,int timeOut) {
		WebElement ele = null;
		boolean flag=false;
		String xpath1="//span[@title='"+gridSectionName+"']";
		ele = isDisplayed(driver, FindElement(driver,xpath1, gridSectionName.toString()+ " link", action.SCROLLANDBOOLEAN,timeOut),"visibility", timeOut, gridSectionName.toString()+ " link");
		if(click(driver, ele, gridSectionName.toString()+ " link", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "clicked on "+gridSectionName.toString()+" link", YesNo.No);
			flag=true;
		}else {
			log(LogStatus.ERROR, "Not able to click on "+gridSectionName.toString()+" link so cannot verify error message", YesNo.Yes);
		}
		return flag;
	}

	
	/**
	 * @param tabToBeAdded
	 * @param timeOut
	 * @return true if all Tab added successfully
	 */
	public boolean addTab_Lighting(String tabToBeAdded,int timeOut){

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
						//sendKeys(driver, getsearchTabTextbox( timeOut), tabs[i],"search textbox", action.BOOLEAN);
						xpath ="//h3[text()='"+tabs[i]+"']/..//preceding-sibling::label/div";
						ele = FindElement(driver, xpath, "Tab to be add : "+tabs[i], action.SCROLLANDBOOLEAN, timeOut);
						
						ThreadSleep(1000);
						if (ele!=null) {
							scrollDownThroughWebelement(driver, ele, "TABS : "+tabs[i]);	
							if (click(driver, ele, "Tab to be add : "+tabs[i], action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Tab Added : "+tabs[i], YesNo.No);
							} else {
								flag = false;
								log(LogStatus.INFO, "Not Able to add Tab : "+tabs[i], YesNo.Yes);
							}
							
						} else {
							log(LogStatus.INFO, "Tab Already Added : "+tabs[i], YesNo.No);
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
	public WebElement getRelatedTab(String projectName,PageName pageName,RelatedTab relatedTab,int timeOut){
	String xpath="";
	WebElement ele;
	String related = relatedTab.toString().replace("_", " ");
	if (projectName.contains(ProjectName.PE.toString()))
		xpath="//li[@title='"+related+"']//a";
	else
	xpath = "//li//*[@title='"+related+"' or text()='"+related+"']";
	xpath = "//li//*[@title='"+related+"' or text()='"+related+"']";
	ele = isDisplayed(driver, FindElement(driver, xpath, relatedTab.toString(), action.SCROLLANDBOOLEAN, timeOut)
			, "visiblity", 30, relatedTab.toString());
	if (ele!=null) {
	appLog.info("Element Found : "+related);	
	}else {
		appLog.error("Element Not Found : "+related);	
		appLog.error("Going to check on more "+related);	
		xpath = "//li//button[@title='More Tabs']";
		ele = FindElement(driver, xpath, relatedTab.toString(), action.SCROLLANDBOOLEAN, timeOut);
		click(driver, ele, "More Tab", action.BOOLEAN);
		ThreadSleep(3000);
		
		xpath = "//a/span[text()='"+related+"']";
		ele = isDisplayed(driver, FindElement(driver, xpath, relatedTab.toString(), action.SCROLLANDBOOLEAN, timeOut)
				, "visiblity", 30, relatedTab.toString());
		
		
	}
	return ele;
	
}

	


////////////////////////////////////////////////  Activity Association ///////////////////////////////////////////////////////////////////
	


/**
 * @param projectName
 * @param TabName
 * @return true if able to click on Tab
 */
public boolean clickOnTab(String projectName,TabName TabName) {

	String tabName = null;
	boolean flag = false;
	WebElement ele;
	tabName = getTabName(projectName, TabName);
	System.err.println("Passed switch statement");
	if (tabName!=null) {
		ele = FindElement(driver, "//a[contains(@href,'lightning') and contains(@title,'" + tabName + "')]/span/..",tabName, action.SCROLLANDBOOLEAN,30);
		ele = isDisplayed(driver,ele,"visibility", 30, tabName);
		if (ele != null) {
			appLog.info("Tab Found");
			ThreadSleep(5000);
			if (clickUsingJavaScript(driver, ele, tabName+" :Tab")) {
				CommonLib.log(LogStatus.INFO, "Tab found", YesNo.No);
				appLog.info("Clicked on Tab : "+tabName);
				flag = true;
			} else {
				appLog.error("Not Able to Click on Tab : "+tabName);
			}

		} else {
			CommonLib.log(LogStatus.INFO, "Going to found tab after clicking on More Icon", YesNo.No);
			if (click(driver, getMoreTabIcon(projectName, 10), "More Icon", action.SCROLLANDBOOLEAN)) {
				ele = FindElement(driver,"//a[contains(@href,'lightning')]/span[@class='slds-truncate']/span[contains(text(),'"	+ tabName + "')]",tabName, action.SCROLLANDBOOLEAN, 10);
				ele = isDisplayed(driver,ele,"visibility", 10, tabName);
				if (ele!=null) {
					if (clickUsingJavaScript(driver, ele, tabName+" :Tab")) {
						appLog.info("Clicked on Tab on More Icon: "+tabName);
						CommonLib.log(LogStatus.INFO, "Tab found on More Icon", YesNo.No);
						flag = true;
					}	
				}

			} else {
				appLog.error("Not Able to Clicked on Tab on More Icon: "+tabName);
			}

		}
	}

	return flag;
}

/**
 * @param projectName
 * @param TabName
 * @return String for TabName
 */
public String getTabName(String projectName,TabName TabName) {
	String tabName = null;
	switch (TabName) {
	case HomeTab:
		tabName = "Home";
		break;
	case NavatarSetup:
		tabName = "Navatar Setup";
		break;
	case TestCustomObjectTab:
		tabName = tabCustomObj+"s";
		break;
	case Object1Tab:
		if (tabObj1.equalsIgnoreCase("Entity")){
			tabName="Entities";
		}
		else{
			tabName = tabObj1+"s";
		}
		break;
	case Object2Tab:
		tabName = tabObj2+"s";
		break;
	case Object3Tab:
		tabName = tabObj3+"s";
		break;
	case Object4Tab:
		tabName = tabObj4+"s";
		break;
	case Object5Tab:
		tabName = tabObj5+"s";
		break;
	case Object6Tab:
		tabName = tabObj6+"s";
		break;
	case Object7Tab:
		tabName = tabObj7+"s";
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
public WebElement getLabelTextBox(String projectName,String pageName,String labelFieldTextBox,int timeOut) {
	
	WebElement ele=null;
	String labelTextBox = labelFieldTextBox.replace("_", " ");
	String xpath="//*[text()='"+labelTextBox+"']/following-sibling::div/input";
	if (pageName.equalsIgnoreCase(PageName.NewTaskPage.toString()) || pageName.equalsIgnoreCase(PageName.TaskPage.toString()))
		xpath="//*[text()='"+labelTextBox+"']/..//input";
	else if(pageName.equalsIgnoreCase(PageName.FundsPage.toString()))
		xpath="//*[text()='"+labelTextBox+"']/following-sibling::div//input";
	else if(pageName.equalsIgnoreCase(PageName.MEPageFromCalender.toString()))
		xpath="//*[text()='"+labelTextBox+"']/../following-sibling::div//input";
	ele = FindElement(driver, xpath, labelTextBox, action.SCROLLANDBOOLEAN, timeOut);
	ele =isDisplayed(driver, ele, "Visibility", timeOut, labelTextBox);	
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
public boolean clickOnAlreadyCreatedItem(String projectName, TabName tabName,String alreadyCreated, int timeout) {
	boolean flag=false;
	String xpath="";
	String viewList = null;
	switch (tabName) {
	case InstituitonsTab:

		if (ProjectName.MNA.toString().equals(projectName)) {
			viewList = "All Accounts";	
		} else {
			viewList = "All Institutions";
		}
		break;

	case TestCustomObjectTab:
		viewList = "Automation All";
		break;
	case CompaniesTab:
		viewList = "All Companies";
		break;
	case FundsTab:
		viewList = "All";
		break;
	case DealTab:
		viewList = "All";
		break;
	case Object1Tab:
		viewList = "Automation All";
		break;
	case Object2Tab:
		viewList = "Automation All";
		break;
	case Object3Tab:
		viewList = "Automation All";
		break;
	case Object4Tab:
		viewList = "Automation All";
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
		viewList = "Org Recycle Bin";
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
	if (click(driver, getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
		ThreadSleep(3000);
		xpath="//div[@class='listContent']//li/a/span[text()='" + viewList + "']";
		selectListView = FindElement(driver, xpath,"Select List View : "+viewList, action.SCROLLANDBOOLEAN, 30);
		if (click(driver, selectListView, "select List View : "+viewList, action.SCROLLANDBOOLEAN)) {
			ThreadSleep(3000);
			ThreadSleep(5000);

			if (sendKeys(driver, getSearchIcon_Lighting(20), alreadyCreated+"\n", "Search Icon Text",action.SCROLLANDBOOLEAN)) {
				ThreadSleep(5000);

				xpath = "//table[@data-aura-class='uiVirtualDataTable']//tbody//tr//th//span//*[text()='"+ alreadyCreated + "']";
				ele = FindElement(driver,xpath,alreadyCreated, action.BOOLEAN, 30);
				ThreadSleep(2000);

				if (click(driver, ele, alreadyCreated, action.BOOLEAN)) {
					ThreadSleep(3000);
					click(driver, getPagePopUp(projectName,5), "Page PopUp", action.BOOLEAN);
					flag=true;
				} else {
					appLog.error("Not able to Click on Already Created : " + alreadyCreated);
				}
			} else {
				appLog.error("Not able to enter value on Search Box");
			}
		} else {
			appLog.error("Not able to select on Select View List : "+viewList);
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
 * @return true if able to click on particular item on Particular tab
 */
public boolean clickOnAlreadyCreatedItem(String projectName,String alreadyCreated, int timeout) {
	boolean flag=false;
	String xpath="";
	String viewList = null;
	viewList = "Automation All";
	WebElement ele, selectListView;
	ele = null;

	refresh(driver);
	if (click(driver, getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
		ThreadSleep(3000);
		xpath="//div[@class='listContent']//li/a/span[text()='" + viewList + "']";
		selectListView = FindElement(driver, xpath,"Select List View : "+viewList, action.SCROLLANDBOOLEAN, 30);
		if (click(driver, selectListView, "select List View : "+viewList, action.SCROLLANDBOOLEAN)) {
			ThreadSleep(3000);
			ThreadSleep(5000);

			if (sendKeys(driver, getSearchIcon_Lighting(20), alreadyCreated+"\n", "Search Icon Text",action.SCROLLANDBOOLEAN)) {
				ThreadSleep(5000);

				xpath = "//table[@data-aura-class='uiVirtualDataTable']//tbody//tr//th//span//*[text()='"+ alreadyCreated + "']";
				ele = FindElement(driver,xpath,alreadyCreated, action.BOOLEAN, 30);
				ThreadSleep(2000);

				if (click(driver, ele, alreadyCreated, action.BOOLEAN)) {
					ThreadSleep(3000);
					click(driver, getPagePopUp(projectName,5), "Page PopUp", action.BOOLEAN);
					flag=true;
				} else {
					appLog.error("Not able to Click on Already Created : " + alreadyCreated);
				}
			} else {
				appLog.error("Not able to enter value on Search Box");
			}
		} else {
			appLog.error("Not able to select on Select View List : "+viewList);
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
//subject,meeting type, RA, comment,date,priority,contact name, status
public boolean verifyUIOfCreateNewTaskWindow(String projectName, String userFullName,  String[] subjectMeetingAssociationsCommentsDatePriorityName, boolean isMultiple) {
	boolean flag=true;
	String status=getValueFromElementUsingJavaScript(driver, getstatusDropdownInCreateNewTask(projectName, 20), "status dropdown");
	/*System.out.println("div value "+status);
	if (status.trim().contains(subjectMeetingAssociationsCommentsDatePriorityName[7])) {
		log(LogStatus.INFO, "successfully verfied status dropdown", YesNo.No);
	}
	else {
		log(LogStatus.ERROR, "could not verify status dropdown. Found is "+status, YesNo.Yes);
		flag=false;
	}*/
	String name="";
	WebElement ele=getCrossButtonForAlreadySelectedItem(projectName, PageName.Object1Page, PageLabel.Assigned_To.toString(), false, userFullName, action.SCROLLANDBOOLEAN, 10);
	if (ele!=null) {
		log(LogStatus.INFO, "successfully verified user name on assigned to field", YesNo.No);
	}
	else {
		log(LogStatus.ERROR, "could not verify assigned to user name "+name, YesNo.No);
		flag=false;
	}
	name=getValueFromElementUsingJavaScript(driver, getLabelTextBox(projectName, PageName.TaskPage.toString(),PageLabel.Subject.toString(),20), "subject");
	if (name.contains(subjectMeetingAssociationsCommentsDatePriorityName[0].trim())) {
		log(LogStatus.INFO, "successfully verified subject textbox", YesNo.No);
	}
	else {
		log(LogStatus.ERROR, "could not verify subject textbox, found: "+name, YesNo.No);
		flag=false;
	}
	if (subjectMeetingAssociationsCommentsDatePriorityName[6].equalsIgnoreCase("")) {
		name=getValueFromElementUsingJavaScript(driver, getnameTextBoxInNewTask(projectName, 20), "nameTextBox");
		if (name.contains("")) {
			log(LogStatus.INFO, "successfully verified empty name textbox", YesNo.No);
		}
		else {
			log(LogStatus.ERROR, "could not verify empty name textbox, found: "+name, YesNo.No);
			flag=false;
		}
	}
	else {
		List<WebElement> eleList = getAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(),isMultiple, action.SCROLLANDBOOLEAN, 15);
		if (compareMultipleList(driver,subjectMeetingAssociationsCommentsDatePriorityName[6] , eleList).isEmpty()) {
			log(LogStatus.INFO, "successfully verified name textbox", YesNo.No);
		}else {
			log(LogStatus.ERROR, "could not verify name textbox, found: "+name, YesNo.No);
			flag=false;
		}

	}
	
	if ( getdueDateTextBoxInNewTask(projectName, 20)!=null) {
		//name= getdueDateTextBoxInNewTask(projectName, 20).getText().trim();
		name=getValueFromElementUsingJavaScript(driver, getdueDateTextBoxInNewTask(projectName, 20), "dueDateTextBoxInNewTask");
		if (subjectMeetingAssociationsCommentsDatePriorityName[4].equalsIgnoreCase("")) {
			if (name.equalsIgnoreCase(subjectMeetingAssociationsCommentsDatePriorityName[4])) {
				log(LogStatus.INFO, "successfully verified empty due date textbox", YesNo.No);
			}
			else {
				log(LogStatus.ERROR, "date not matched, actual : "+name+" expected: "+subjectMeetingAssociationsCommentsDatePriorityName[4], YesNo.No);
				flag=false;	
			}
		}
		else {
			if (verifyDate(subjectMeetingAssociationsCommentsDatePriorityName[4], name)) {
				log(LogStatus.INFO, "successfully verified dueDate textbox "+subjectMeetingAssociationsCommentsDatePriorityName[4] + " contains "+name, YesNo.No);
			}
			else {
				log(LogStatus.ERROR, "could not verify dueDate textbox, found: "+name, YesNo.No);
				flag=false;
			}
		}
	}else {
		log(LogStatus.ERROR, "not visible on page dueDate textbox", YesNo.No);
		flag=false;
	}
	if (getmeetingTypeDropdown(projectName, 20)!=null) {
		name=getValueFromElementUsingJavaScript(driver, getmeetingTypeDropdown(projectName, 20), "meetingTypeDropdown");
		if (name.trim().contains(subjectMeetingAssociationsCommentsDatePriorityName[1])) {
			log(LogStatus.INFO, "successfully verified "+subjectMeetingAssociationsCommentsDatePriorityName[1]+" in meeting type dropdown", YesNo.No);
		}
		else {
			log(LogStatus.ERROR, "could not verify "+subjectMeetingAssociationsCommentsDatePriorityName[1]+" in meeting type dropdown. Present: "+name, YesNo.No);
			flag=false;
		}
	}else {
		log(LogStatus.ERROR, "could not find meeting type dropdown", YesNo.No);
		flag=false;
	}
	name=getValueFromElementUsingJavaScript(driver, getPriorityDropdown(projectName, 20), "PriorityDropdown");
	if (name.trim().contains(subjectMeetingAssociationsCommentsDatePriorityName[5])) {
		log(LogStatus.INFO, "successfully verified "+subjectMeetingAssociationsCommentsDatePriorityName[5]+" in PriorityDropdown", YesNo.No);
	}
	else {
		log(LogStatus.ERROR, "could not verify "+subjectMeetingAssociationsCommentsDatePriorityName[5]+" in PriorityDropdown. Present: "+name, YesNo.No);
		flag=false;
	}
	ele=null;
	if (!subjectMeetingAssociationsCommentsDatePriorityName[2].equals("")) {
	String ra[]=subjectMeetingAssociationsCommentsDatePriorityName[2].split(",");
	
	for (int i =0;i<ra.length;i++) {
		log(LogStatus.INFO, "trying to find associations "+ra[i], YesNo.No);
		ele=getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), true, ra[i], action.BOOLEAN, 10);
		if (ele!=null) {
			log(LogStatus.INFO, "successfully found associations "+ra[i], YesNo.No);
		}
		else {
			log(LogStatus.ERROR, "could not find association "+ra[i], YesNo.Yes);
			flag=false;
		}
	}
	}
	/*List<String> s=compareMultipleList(driver, subjectMeetingAssociationsCommentsDatePriorityName[2], els);
	if (s.isEmpty()) {
		log(LogStatus.INFO, "successfully found associations "+subjectMeetingAssociationsCommentsDatePriorityName[2], YesNo.No);
	}
	else {
		for (String print:s) {
			log(LogStatus.ERROR, "could not find association "+print, YesNo.Yes);
			flag=false;
		}
	}*/
	name=getcommentsTextBox(projectName, 20).getText();
	if (name.equals(subjectMeetingAssociationsCommentsDatePriorityName[3])) {
		log(LogStatus.INFO, "successfully verified comments textbox : "+name, YesNo.No);
	}
	else {
		log(LogStatus.ERROR, "could not verify comments textbox, found: "+subjectMeetingAssociationsCommentsDatePriorityName[3], YesNo.No);
		flag=false;
	}
	if (getCustomTabSaveBtn(projectName, 10)!=null)
		log(LogStatus.INFO, "successfully verified save button", YesNo.No);
	else {
		log(LogStatus.ERROR, "could not verify save button", YesNo.No);
		flag=false;

    }
    
    if ( getdueDateTextBoxInNewTask(projectName, 20)!=null) {
        //name= getdueDateTextBoxInNewTask(projectName, 20).getText().trim();
        name=getValueFromElementUsingJavaScript(driver, getdueDateTextBoxInNewTask(projectName, 20), "dueDateTextBoxInNewTask");
        if (subjectMeetingAssociationsCommentsDatePriorityName[4].equalsIgnoreCase("")) {
            if (name.equalsIgnoreCase(subjectMeetingAssociationsCommentsDatePriorityName[4])) {
                log(LogStatus.INFO, "successfully verified empty due date textbox", YesNo.No);
            }
            else {
                log(LogStatus.ERROR, "date not matched, actual : "+name+" expected: "+subjectMeetingAssociationsCommentsDatePriorityName[4], YesNo.No);
                flag=false;    
            }
        }
        else {
            if (verifyDate(subjectMeetingAssociationsCommentsDatePriorityName[4], name)) {
                log(LogStatus.INFO, "successfully verified dueDate textbox "+subjectMeetingAssociationsCommentsDatePriorityName[4] + " contains "+name, YesNo.No);
            }
            else {
                log(LogStatus.ERROR, "could not verify dueDate textbox, found: "+name, YesNo.No);
                flag=false;
            }
        }
    }else {
        log(LogStatus.ERROR, "not visible on page dueDate textbox", YesNo.No);
        flag=false;
    }
    if (getmeetingTypeDropdown(projectName, 20)!=null) {
        name=getValueFromElementUsingJavaScript(driver, getmeetingTypeDropdown(projectName, 20), "meetingTypeDropdown");
        if (name.trim().contains(subjectMeetingAssociationsCommentsDatePriorityName[1])) {
            log(LogStatus.INFO, "successfully verified "+subjectMeetingAssociationsCommentsDatePriorityName[1]+" in meeting type dropdown", YesNo.No);
        }
        else {
            log(LogStatus.ERROR, "could not verify "+subjectMeetingAssociationsCommentsDatePriorityName[1]+" in meeting type dropdown. Present: "+name, YesNo.No);
            flag=false;
        }
    }else {
        log(LogStatus.ERROR, "could not find meeting type dropdown", YesNo.No);
        flag=false;
    }
    name=getValueFromElementUsingJavaScript(driver, getPriorityDropdown(projectName, 20), "PriorityDropdown");
    if (name.trim().contains(subjectMeetingAssociationsCommentsDatePriorityName[5])) {
        log(LogStatus.INFO, "successfully verified "+subjectMeetingAssociationsCommentsDatePriorityName[5]+" in PriorityDropdown", YesNo.No);
    }
    else {
        log(LogStatus.ERROR, "could not verify "+subjectMeetingAssociationsCommentsDatePriorityName[5]+" in PriorityDropdown. Present: "+name, YesNo.No);
        flag=false;
    }
    ele=null;
    if (!subjectMeetingAssociationsCommentsDatePriorityName[2].equals("")) {
    String ra[]=subjectMeetingAssociationsCommentsDatePriorityName[2].split(",");
    
    for (int i =0;i<ra.length;i++) {
        log(LogStatus.INFO, "trying to find associations "+ra[i], YesNo.No);
        ele=getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), true, ra[i], action.BOOLEAN, 10);
        if (ele!=null) {
            log(LogStatus.INFO, "successfully found associations "+ra[i], YesNo.No);
        }
        else {
            log(LogStatus.ERROR, "could not find association "+ra[i], YesNo.Yes);
            flag=false;
        }
    }
    }
    /*List<String> s=compareMultipleList(driver, subjectMeetingAssociationsCommentsDatePriorityName[2], els);
    if (s.isEmpty()) {
        log(LogStatus.INFO, "successfully found associations "+subjectMeetingAssociationsCommentsDatePriorityName[2], YesNo.No);
    }
    else {
        for (String print:s) {
            log(LogStatus.ERROR, "could not find association "+print, YesNo.Yes);
            flag=false;
        }
    }*/
    name=getcommentsTextBox(projectName, 20).getText();
    if (name.equals(subjectMeetingAssociationsCommentsDatePriorityName[3])) {
        log(LogStatus.INFO, "successfully verified comments textbox : "+name, YesNo.No);
    }
    else {
        log(LogStatus.ERROR, "could not verify comments textbox, found: "+subjectMeetingAssociationsCommentsDatePriorityName[3], YesNo.No);
        flag=false;
    }
    if (getCustomTabSaveBtn(projectName, 10)!=null)
        log(LogStatus.INFO, "successfully verified save button", YesNo.No);
    else {
        log(LogStatus.ERROR, "could not verify save button", YesNo.No);
        flag=false;

 

    }
    if (getcancelButton(projectName, 10)!=null)
        log(LogStatus.INFO, "successfully verified cancel button", YesNo.No);
    else {
        log(LogStatus.ERROR, "could not verify cancel button", YesNo.No);
        flag=false;

 

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
public WebElement getHeaderTextForPage(String projectName,PageName pageName,String headerText1,action action,int timeOut){
	String xpath="";
	WebElement ele;
	String headerText=headerText1.replace("_", " ");
	if (PageName.CRMUserPage.toString().equals(pageName.toString())) {
		xpath="//b/span[text()='"+headerText+"']";
	} else if (PageName.NewTaskPopUP.toString().equals(pageName.toString())) {
		xpath="//h2[text()='"+headerText+"']";
	}else if (PageLabel.New_Task.toString().equals(headerText1)) {
		xpath="//h2[contains(text(),'New')]";
		//[contains(text(),'Task')]
		//xpath="//h2[contains(text(),'New ')]";
	}else if(PageName.TaskPage==pageName || PageName.Object2Page==pageName) {
		xpath="//*[text()='"+headerText+"']";
	}else if(PageName.ListEmail==pageName) {
		xpath="//h1[contains(text(),'"+headerText+"')]";
	}else if(PageName.Object1PagePopup==pageName) {
		xpath="//h2[contains(text(),'"+headerText+"')]";
	}
	else {
	//	xpath="//*[text()='"+headerText+"']";
		xpath="//h2[contains(text(),'New ')]";
	}
	
	ele=FindElement(driver, xpath, "Header Text : "+headerText, action, timeOut);
	ele = isDisplayed(driver, ele, "Visibility", timeOut, "Header Text : "+headerText);
	return ele;
}


/**
 * @param projectName
 * @return List<WebElement>
 */
public List<WebElement> listOfObjectsInRelatedAssctions(String projectName) {
	return FindElements(driver, "//div[contains(@class,'slds-dropdown-trigger')]//div//ul//li//a", "list of objects in related associations");
}


/**
 * @param projectName
 * @param record
 * @return remove Button In Related Associations Field Webelement
 */
public WebElement removeButtonInRelatedAssociations(String projectName,String record) {
	return isDisplayed(driver, FindElement(driver, "//div[@id='relatedAssociation']//span[text()='"+record+"']/../following-sibling::button[@title='Remove']"
			, "removeButton", action.BOOLEAN, 10), "visibility", 5, "removeButton");
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
public WebElement verifyingRelatedTabData(String projectName,PageName pageName,RelatedTab relatedTab,String date, String subjectName,String contactName,String relatedTo, String plusCount,String status,String owner,String meetingType,String activity,String commentsLink,action action,int timeOut) {
	//String[] dateArr=date.split(",");
	WebElement ele;
	boolean flag=false;;
	status=status.replace("_", " ");
	String parentXpath="//span[@id='Specify_the_recipients_to_include-rows']";
	String dateXpath="//span[text()='"+date+"']";
	String subjectNameXpath="/following-sibling::span/a[text()='"+subjectName+"']";
	String contactNameNameXpath="/../following-sibling::span/a[text()='"+contactName+"']";
	String relatedToXpath="/..//following-sibling::span/a[contains(text(),'"+relatedTo+"')]";
	String plusCountXpath="/following-sibling::a[contains(text(),'"+plusCount+"')]";
	String statusXpath="/..//following-sibling::span[contains(text(),'"+status+"')]";
	String ownerXpath="//following-sibling::span/a[text()='"+owner+"']";
	String meetingTypeXpath="/..//following-sibling::span[contains(text(),'"+meetingType+"')]";
	String activityXpath="//following-sibling::span[text()='"+activity+"']";
	String commentsLinkXpath="//following-sibling::span/a[text()='"+commentsLink+"']";
	if (relatedTo==null && plusCount==null)
		relatedToXpath="";plusCountXpath="/..";
	String xpath = parentXpath+dateXpath+subjectNameXpath+contactNameNameXpath+relatedToXpath+plusCountXpath+statusXpath+ownerXpath+meetingTypeXpath+activityXpath+commentsLinkXpath;
	
	ele = FindElement(driver, xpath, "Grid Data on  "+pageName.toString()+" for related Tab : "+relatedTab.toString(), action, timeOut);
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
 * @return Cross Button for already selected item for field WebElement in Task popup  
 */
public WebElement getCrossButtonForAlreadySelectedItem(String projectName,PageName pageName,String label,boolean isMultipleAssociation,String name,action action,int timeOut) {
	String xpath="";
	WebElement ele;
	String fieldlabel=label.replace("_", " ");
	appLog.info(" >>>>>>>>>>>>>>>>   label:"+label);
	if (label.equalsIgnoreCase(PageLabel.Name.toString()))
		isMultipleAssociation=true;
	if (PageName.CallPopUp.toString().equalsIgnoreCase(pageName.toString())||(PageLabel.Name.toString().equalsIgnoreCase(label) && PageName.TaskPage.toString().equalsIgnoreCase(pageName.toString()) && isMultipleAssociation)) {
		xpath ="//span[text()='"+fieldlabel+"']/../following-sibling::div//li/a/span[text()='"+name+"']/following-sibling::a";	
	}
	else if (PageLabel.Related_To.toString().equalsIgnoreCase(label) || PageLabel.Related_Associations.toString().equalsIgnoreCase(label) || isMultipleAssociation) {
		xpath ="//label[text()='"+fieldlabel+"']/..//span[contains(@class,'customPill')]//span[text()='"+name+"']//following-sibling::button";	
		//label[text()="Name"]/..//span[contains(@class,"customPill")]/span[text()="Davidson Bendt"]/following-sibling::button
	} else {
		xpath="//label[text()='"+fieldlabel+"']/..//span[contains(@class,'pillSize')]//span[text()='"+name+"']/..//following-sibling::button";
	}
	ele = FindElement(driver, xpath, "Cross Button For  : "+name+" For Label : "+fieldlabel, action, timeOut);
	return ele;
	
}

/**
 * @param projectName
 * @param pageName
 * @param label
 * @param isMultipleAssociation
 * @param action
 * @param timeOut
 * @return List<WebElement> of already selected item for Name/Related Association on Task/Meeting Popup
 */
public List<WebElement> getAlreadySelectedItem(String projectName,PageName pageName,String label,boolean isMultipleAssociation,action action,int timeOut) {
	String xpath="";
	List<WebElement> eleList;
	if (label.equalsIgnoreCase(PageLabel.Name.toString()))
		isMultipleAssociation=true;
	String fieldlabel=label.replace("_", " ");
	appLog.info(" >>>>>>>>>>>>>>>>   label:"+label);
	if (PageLabel.Related_To.toString().equalsIgnoreCase(label) || PageLabel.Related_Associations.toString().equalsIgnoreCase(label) || isMultipleAssociation) {
		xpath ="//label[text()='"+fieldlabel+"']/..//span[contains(@class,'customPill')]//button/..//span[2]";	
		//label[text()="Name"]/..//span[contains(@class,"customPill")]//button
	} else {
		xpath="//label[text()='"+fieldlabel+"']/..//span[contains(@class,'pillSize')]//button/..//span[2]";
	}
	
	
	WebElement ele = FindElement(driver, xpath, "Already Selected item for Label : "+fieldlabel, action, timeOut);
	scrollDownThroughWebelement(driver, ele, "");
	eleList = FindElements(driver, xpath, "Already Selected item for Label : "+fieldlabel);
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
public WebElement getLabelTextBoxForNameOrRelatedAssociationOnTask(String projectName,PageName pageName,String labelFieldTextBox,action action,int timeOut) {
	
	WebElement ele=null;
	String xpath="";
	String labelTextBox = labelFieldTextBox.replace("_", " ");
	if (PageName.TaskPage.toString().equals(pageName.toString()) || PageName.NewEventPopUp.toString().equals(pageName.toString())) {
		if (labelFieldTextBox.equalsIgnoreCase(PageLabel.Name.toString()))
			xpath="//span[text()='"+labelTextBox+"']/..//following-sibling::div//input[@title='Search Contacts']";
		else
			xpath = "//span[text()='"+labelTextBox+"']/..//following-sibling::div//input";
	} else {
		xpath="//label[text()='"+labelTextBox+"']/..//span//input";
	}
	
	ele = FindElement(driver, xpath, labelTextBox, action, timeOut);
	ele =isDisplayed(driver, ele, "Visibility", timeOut, labelTextBox);	
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
public WebElement getrelatedAssociationsdropdownButton(String projectName,PageName pageName,String label,action action,int timeOut) {

	String xpath="";
	label=label.replace("_", " ");
	if (PageName.TaskPage.toString().equals(pageName.toString()) || PageName.NewEventPopUp.toString().equals(pageName.toString())) {
		xpath ="//span[text()='"+label+"']/../following-sibling::div//div[@class='uiPopupTrigger']//a";
	} else {
		xpath ="//label[text()='"+label+"']/..//div[contains(@class,'dropdownButton')]";
	}
	
	WebElement ele = FindElement(driver, xpath, "Drop Down For Label : "+label, action, timeOut);
	ele = FindElement(driver, xpath, "Drop Down For Label : "+label, action, timeOut);
	return isDisplayed(driver, ele, "Visibility", timeOut, "Drop Down For Label : "+label);
}

/**
 * @param projectName
 * @param pageName
 * @param label
 * @param tabName
 * @param action
 * @param timeOut
 * @return true if able to select Dopdown item on Related Association Field For Meeting/Task
 */
public boolean SelectRelatedAssociationsdropdownButton(String projectName,PageName pageName,String label,TabName tabName,action action,int timeOut) {
	boolean flag=false;
	WebElement ele;
	String xpath="";
	label=label.replace("_", " ");
	String tab= getTabName(projectName, tabName);
	for(int i=0;i<2;i++) {
		
		ele=getrelatedAssociationsdropdownButton(projectName, pageName, label, action, 5);
		if (click(driver, ele, "Drop Down Icon For Label : "+label, action)) {
			appLog.error("Clicked on  Drown Down Icon for LABEL : "+label);	
			ThreadSleep(2000);
			if (PageName.TaskPage.toString().equals(pageName.toString()) || PageName.NewEventPopUp.toString().equals(pageName.toString())) {
				xpath ="//li//a[@title='"+tab+"']";
			} else {
				xpath ="//label[text()='"+label+"']/..//div[contains(@class,'slds-dropdown-trigger')]//div//ul//li//a[text()='"+tab+"']";
			}
		
			ele = FindElement(driver, xpath, "Drop Down For Value : "+tab, action, 5);
			if (clickUsingJavaScript(driver, ele, "Drop Down Value  : "+tab, action)) {
				appLog.info("Select Drown Down Value : "+tab+" for LABEL : "+label);
				return true;
			} else {
				appLog.error("Not Able to Select Drown Down Value : "+tab+" for LABEL : "+label);
			}
			
		} else {
			appLog.error("Not Able to Click on Drop Down Icon for LABEL : "+label);	
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
public boolean SelectDropDownValue(String projectName,PageName pageName,String label,String value,action action,int timeOut) {
	boolean flag=false;
	WebElement ele;
	String xpath="";
	label=label.replace("_", " ");
	if (PageName.TaskPage.toString().equals(pageName.toString()) || PageName.NewEventPopUp.toString().equals(pageName.toString())) {
	//	xpath = "//span[text()='"+label+"']/../following-sibling::div";
		xpath = "//ul/li/a[@title='"+value+"']";
	} else {
		xpath = "//label[text()='"+label+"']/..//span[@title='"+value+"']";
	}
	

	ele=FindElement(driver, xpath, "Drop Down : "+label+" value : "+value, action, timeOut);
	if (clickUsingJavaScript(driver, ele,"Drop Down Value : "+value, action)) {
		appLog.info("Selected "+value+" For : "+label);	
		flag=true;
	} else {
		appLog.error("Not Able to Select "+value+" For : "+label);	
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
public boolean clickOnShowMoreActionDownArrow(String projectName,PageName pageName, ShowMoreActionDropDownList showMoreActionDropDownList, int timeOut) {
	int i=1;
	String xpath="";
	WebElement ele;
	boolean flag =false;
	String actionDropDown = showMoreActionDropDownList.toString().replace("_", " ");
//		if(pageName.toString().equalsIgnoreCase(PageName.Object2Page.toString())) {
//			i=2;
//			xpath="(//a[contains(@title,'more action')])["+i+"]";
//		}
//		else
		
		if(clickOnShowMoreDropdownOnly(projectName, pageName)) {
			ThreadSleep(3000);
			log(LogStatus.INFO, "clicked on show more actions icon", YesNo.No);
			if (pageName.equals(PageName.TaskPage)) 
				xpath="//div[@role='menu']//li/a[@title='"+actionDropDown+"']";
			else if(pageName.equals(PageName.Object1Page) || pageName.equals(PageName.SDGPage))
				xpath="//*[@role='menu']//span[text()='"+actionDropDown+"']";
			else
			xpath="//div[@role='menu']//span[text()='"+actionDropDown+"']";
			 ThreadSleep(3000);
			 ele=FindElement(driver, xpath, "show more action down arrow : "+actionDropDown, action.BOOLEAN, 10);
			// mouseOverOperation(driver, ele);
			 if(clickUsingJavaScript(driver, ele, "show more action on "+pageName.toString(), action.BOOLEAN)) {
					log(LogStatus.INFO, "clicked on "+actionDropDown+" link", YesNo.No);
					flag=true;
			 }else {
					//log(LogStatus.ERROR, "Not able to click on "+actionDropDown+" link", YesNo.Yes);
					xpath = "//button[@name='"+actionDropDown+"']";
					ele=FindElement(driver, xpath, actionDropDown, action.BOOLEAN,10);
					if (click(driver, ele, actionDropDown, action.SCROLLANDBOOLEAN)) {
						flag=true;
					}
					else {
						log(LogStatus.ERROR, "Not able to click on "+actionDropDown+" link", YesNo.Yes);
					}
					
				}
		}else {
			log(LogStatus.ERROR, "Not able to click on show more action down arrow", YesNo.Yes);
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
public WebElement getContactNameOrRelatedAssociationNameOnTask(String projectName,PageName pageName,String labelFieldTextBox,String name,action action,int timeOut) {
	
	WebElement ele=null;
	String xpath="";
	labelFieldTextBox = labelFieldTextBox.replace("_", " ");
	if (PageName.TaskPage.toString().equals(pageName.toString()) || PageName.NewEventPopUp.toString().equals(pageName.toString())) {
		xpath = "//span[text()='"+labelFieldTextBox+"']/..//following-sibling::div//input//following-sibling::div//ul//li/a//div[@title='"+name+"']";
	} else {
		//Need to write ofr copy if same
		xpath="//label[text()='"+labelFieldTextBox+"']/..//following-sibling::ul//li//span[@title='"+name+"']";
	}
	
	ele = FindElement(driver, xpath, labelFieldTextBox, action, timeOut);
	ele =isDisplayed(driver, ele, "Visibility", timeOut, labelFieldTextBox);	
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
 * @return true if able to select value by searching on Related Association/Name/Assigned To/Name
 */
public boolean selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(String projectName,PageName pageName,String label,TabName tabName,String textValue,action action,int timOut) {
	boolean flag=false;
	WebElement ele;
	
	if (PageLabel.Related_Associations.toString().equals(label)  || ((PageName.NewEventPopUp.toString().equals(pageName.toString()) || PageName.TaskPage.toString().equals(pageName.toString()) ) && PageLabel.Related_To.toString().equals(label) )) {
		
		if (SelectRelatedAssociationsdropdownButton(projectName, pageName, label, tabName, action, timOut)) {
			log(LogStatus.INFO,"Able to Select Drown Down Value : "+getTabName(projectName, tabName)+" For Label "+label,YesNo.No);
			ThreadSleep(2000);	
		}else {
			//sa.assertTrue(false,"Not Able to Select Drown Down Value : "+getTabName(projectName, tabName)+" For Label "+label);
			log(LogStatus.SKIP,"Not Able to Select Drown Down Value : "+getTabName(projectName, tabName)+" For Label "+label,YesNo.Yes);
		return flag;
		}
	}
	
		ele= getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, pageName, label, action,timOut);
		ThreadSleep(2000);
		if (sendKeys(driver, ele,textValue, "Related To Text Label", action)) {
			log(LogStatus.INFO,"Enter Value to Related To Text Box : "+textValue,YesNo.No);	
			ThreadSleep(1000);

			ele =  getContactNameOrRelatedAssociationNameOnTask(projectName, pageName, label,textValue, action,timOut);
			if (clickUsingJavaScript(driver, ele, "Selected "+ textValue +" From Label : "+label, action)) {
				log(LogStatus.INFO,"Clicked on : "+textValue,YesNo.No);
				ThreadSleep(2000);
				flag = true;
			} else {
			//	sa.assertTrue(false,"Not Able to Click on : "+textValue);
				log(LogStatus.SKIP,"Not Able to Click on : "+textValue,YesNo.Yes);	
			}


		}else {
		//	sa.assertTrue(false,"Not Able to Enter Value to Related To Text Box : "+textValue);
			log(LogStatus.SKIP,"Not Able to Enter Value to Related To Text Box : "+textValue,YesNo.Yes);	
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
public boolean selectDropDownValueonTaskPopUp(String projectName,PageName pageName,String label,String value,action action,int timeOut) {
	boolean flag=false;
	WebElement ele=null;
	
	ele = getDropdownOnTaskPopUp(projectName, pageName, label, action, timeOut);
	
	if (ele!=null) {
		log(LogStatus.INFO, "Drop Down Value Label Found : "+label, YesNo.No);
		if (clickUsingJavaScript(driver, ele, label, action.BOOLEAN)) {
			log(LogStatus.INFO, "Click on Drop Down Label : "+label, YesNo.No);
			flag=SelectDropDownValue(projectName, pageName, label, value, action, timeOut);
			if (flag) {
				log(LogStatus.ERROR, "Selected "+value+" on Drop Down Label : "+label, YesNo.Yes);	
			} else {
				log(LogStatus.ERROR, "Not ABle to Select"+value+" on Drop Down Label : "+label, YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Not ABle to Click on Drop Down Label : "+label, YesNo.Yes);
		}
	
	} else {
		log(LogStatus.ERROR, "Drop Down Value Label Not Found : "+label, YesNo.Yes);
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
public boolean enteringSubjectAndSelectDropDownValuesonTaskPopUp(String projectName,PageName pageName,String subjectText,String[][] dropDownLabelWithValues,action action,int timeOut) {
	
	WebElement ele = getTaskPopUpHeader(projectName, 10);
	String expecedHeader="New Task";;
	if (ele!=null) {
		log(LogStatus.INFO, "PopUp is open" , YesNo.No);
		String actualHeader = ele.getText().trim();
		if (ele.getText().trim().equals(expecedHeader)) {
			log(LogStatus.INFO, "Header Text verified : "+expecedHeader, YesNo.Yes);
			
		} else {
			log(LogStatus.ERROR, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader, YesNo.Yes);
			sa.assertTrue(false, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader);
		}

	} else {
		log(LogStatus.ERROR, "No PopUp is open so cannot verify Heading "+expecedHeader, YesNo.Yes);
		sa.assertTrue(false, "No PopUp is open so cannot verify Heading "+expecedHeader);
	}
	
	String label;
	String value;
	boolean flag=false;
	getLabelTextBox(projectName, PageName.TaskPage.toString(), PageLabel.Subject.toString(),timeOut).clear();
	if (sendKeys(driver, getLabelTextBox(projectName, PageName.TaskPage.toString(), PageLabel.Subject.toString(),timeOut), subjectText, "Subject", action.SCROLLANDBOOLEAN)) {
		log(LogStatus.INFO, "Entered value to Subject Text Box", YesNo.Yes);
	
		flag=true;

		if (dropDownLabelWithValues!=null) {
			
			for (String[] labelWithValues : dropDownLabelWithValues) {
				label=labelWithValues[0];
				value=labelWithValues[1];
				ThreadSleep(1000);
				if (selectDropDownValueonTaskPopUp(projectName, pageName, label, value, action, timeOut)) {
					log(LogStatus.INFO, "Selected : "+value+" For Label : "+label, YesNo.No);	
					ThreadSleep(1000);	

				}else {
					log(LogStatus.ERROR, "Not Able to Select : "+value+" For Label : "+label, YesNo.Yes);	
					BaseLib.sa.assertTrue(false, "Not Able to Select : "+value+" For Label : "+label);	
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
public boolean clickOnShowMoreDropdownOnly(String projectName,PageName pageName) {
	String xpath = "";int i =1;
	WebElement ele=null;
	boolean flag = true;
	if(pageName!=PageName.SDGPage) {
	refresh(driver);
	}
	ThreadSleep(2000);
	xpath="(//span[contains(text(),'more actions')])[1]/..";
	if (PageName.TestCustomObjectPage.equals(pageName) || PageName.Object3Page.equals(pageName)) {
		xpath="(//span[contains(text(),'more actions')])[1]/..";
	}
	else if(PageName.TaskPage.equals(pageName)) {
		xpath="//a[@title='Show one more action']";
	}
	else if(PageName.SDGPage.equals(pageName)) {
		xpath="(//span[contains(text(),'More options')])[1]/..";
	}
	ele=FindElement(driver, xpath, "show more action down arrow", action.SCROLLANDBOOLEAN, 30);
	if(click(driver, ele, "show more action on "+pageName.toString(), action.SCROLLANDBOOLEAN)) {
		log(LogStatus.INFO, "clicked on show more actions icon", YesNo.No);

    }
    else {
        log(LogStatus.FAIL, "cannot click on show more actions icon", YesNo.Yes);
        flag = false;
    }
    return flag;
}
	
					

/**
 * @param projectName
 * @param pageName
 * @param smaddl
 * @param timeOut
 * @return webelement for show more action item
 */
public WebElement actionDropdownElement(String projectName,PageName pageName, ShowMoreActionDropDownList smaddl, int timeOut) {
	String actionDropDown = smaddl.toString().replace("_", " ");
	String xpath ="//span[text()='"+actionDropDown+"']";
	
	if (PageName.TestCustomObjectPage.equals(pageName)) {
	//	xpath="//a/span[text()='"+actionDropDown+"']";
		xpath="//*[@name='"+actionDropDown+"' or text()='"+actionDropDown+"']";
	}
	
	return isDisplayed(driver, FindElement(driver, xpath, "show more action down arrow", action.SCROLLANDBOOLEAN, 10), "visibility", timeOut, actionDropDown);
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
public boolean fieldValueVerification(String projectName, PageName pageName,PageLabel pageLabel,String labelValue,int timeOut) {
	String xpath="";
	WebElement ele;
	boolean flag=false;
	
	String label = pageLabel.toString().replace("_", " ");
	
	if (ProjectName.MNA.toString().equalsIgnoreCase(projectName) && PageLabel.Account_Name.equals(pageLabel)) {
		label="Account Name";
	} else if(ProjectName.PE.toString().contains(projectName) && PageLabel.Account_Name.equals(pageLabel)){
		label="Legal Name";
	} else if(ProjectName.PEEdge.toString().equalsIgnoreCase(projectName) && PageLabel.Account_Name.equals(pageLabel)){
		label="Firm";
	} 
	xpath = "//span[text()='"+label+"']/following-sibling::*//a[text()='"+labelValue+"']";
	
	ele = FindElement(driver, xpath, label+" with Value "+labelValue, action.SCROLLANDBOOLEAN, 5);
	scrollDownThroughWebelement(driver, ele, label+" with Value "+labelValue);
	
	if (ele!=null) {
		flag=true;
	} else {

	}
	
	return flag;

}

/**
 * @param dateToCheck
 * @param valueOnPage
 * @return true/false
 * @description this method is to verify 2 dates on the basis of values present on page and passed
 */
public boolean verifyDate(String dateToCheck, String valueOnPage) {
	int size1=dateToCheck.split("/").length;
	int size2=valueOnPage.split("/").length;
	if (!dateToCheck.isEmpty() && !dateToCheck.equals("") && size1==3 && size2==3) {
		String[] dates = dateToCheck.split("/");
		String[] values = valueOnPage.split("/");
		appLog.info("Excel Date : "+dateToCheck);
		appLog.info("Page Date : "+valueOnPage);
		if (dates[0].contains(values[0]) && dates[1].contains(values[1]) && dates[2].contains(values[2])) {
			log(LogStatus.INFO, "Value matched "+dateToCheck+" For Grid Data", YesNo.No);
			return true;
		} else {
			log(LogStatus.ERROR,  "Value not matched Actual: "+valueOnPage+" Expected : "+dateToCheck+" For Grid Data : ", YesNo.No);
		}
	}else {
		log(LogStatus.ERROR, "passed date is in wrong format", YesNo.No);
	}

	return false;
}

/**
 * @param projectName
 * @param tabObj
 * @param timeOut
 * @return true/false
 * @description this method is used to add list view to page if automation all is not present
 */
public boolean addAutomationAllListView(String projectName,  String tabObj, int timeOut) {
	String viewList="Automation All",xpath="";
	if (click(driver, getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
		ThreadSleep(3000);
		xpath="//div[@class='listContent']//li/a/span[text()='" + viewList + "']";
		WebElement selectListView = FindElement(driver, xpath,"Select List View : "+viewList, action.SCROLLANDBOOLEAN, 5);
		ThreadSleep(3000);
		if ( selectListView!=null) {
			log(LogStatus.INFO, "automation all is already present", YesNo.No);
			return true;
		}
		else {
			log(LogStatus.ERROR, "not found automation all.. now creating", YesNo.No);
			
		}
	}else {
		log(LogStatus.ERROR, "list dropdown is not clickable, so cannot check presence of Automation All", YesNo.Yes);
		
	}

	if (createListView(projectName, tabObj, timeOut)) {
		if (changeFilterInListView(projectName, tabObj, timeOut)) {
			return true;
		}
		else {
			log(LogStatus.ERROR, "could not change filter to all", YesNo.Yes);
		}
	}
	else {
		log(LogStatus.ERROR, "could not create new list", YesNo.Yes);
	}
	return false;
}

/**@author Akul Bhutani
 * @param projectName
 * @param obj
 * @param timeOut
 * @return true/false
 * @description this method is used to only create new view names Automation All
 */
public boolean createListView(String projectName,  String obj, int timeOut) {
	refresh(driver);ThreadSleep(2000);
	if (click(driver, getlistViewControlsButton(projectName, timeOut), "list view", action.BOOLEAN)) {
		log(LogStatus.INFO, "successfully click on list view", YesNo.No);
		if (click(driver, getnewButtonListView(projectName, timeOut), "new ", action.BOOLEAN)) {
			log(LogStatus.INFO, "successfully click on new buton", YesNo.No);
			if (sendKeys(driver, getlistNameTextBox(projectName,"List Name", timeOut), "Automation All", "list name", action.SCROLLANDBOOLEAN)) {
				if (sendKeysWithoutClearingTextBox(driver, getlistNameTextBox(projectName,"List API Name", timeOut), "", "list name", action.SCROLLANDBOOLEAN)) {
					if (click(driver, getallUsersRB(projectName, timeOut), "all users", action.BOOLEAN)) {
						log(LogStatus.INFO, "successfully click on all users", YesNo.No);
						if (click(driver, getlistViewSaveButton(projectName, timeOut), "save", action.BOOLEAN)) {
							log(LogStatus.INFO, "successfully click on save buton", YesNo.No);
							return true;
						}else {
							log(LogStatus.ERROR, "list view save button is not clickable", YesNo.No);
						}
					}else {
						log(LogStatus.ERROR, "all users radio button is not clickable", YesNo.No);
					}
				}else {
					log(LogStatus.ERROR, "list api textbox is not visible", YesNo.No);
				}
			}else {
				log(LogStatus.ERROR, "list name textbox is not visible", YesNo.No);
			}
		}else {
			log(LogStatus.ERROR, "new button is not clickable", YesNo.No);
		}
	}else {
		log(LogStatus.ERROR, "list view controls button is not clickable", YesNo.No);
	}
	return false;
}

/**@author Akul Bhutani
 * @param projectName
 * @param tabObj
 * @param timeOut
 * @return true/false
 * @description this method is used to change value in filter to all users
 */
public boolean changeFilterInListView(String projectName,String tabObj, int timeOut) {
	    if (tabObj.equalsIgnoreCase("Entity")){
		       tabObj="entities";
	    }
	if (click(driver, getListFilterSection(projectName,tabObj, timeOut), "filter section", action.BOOLEAN)) {
		log(LogStatus.INFO, "successfully click on filter section", YesNo.No);
		if (click(driver, getallCheckboxForFilter(projectName, timeOut), "all filters", action.BOOLEAN)) {
			log(LogStatus.INFO, "successfully click on all radio button", YesNo.No);
			if (click(driver, getdoneButtonListView(projectName, timeOut),"done", action.BOOLEAN)) {
				log(LogStatus.INFO, "successfully click on done buton", YesNo.No);
				if (click(driver, getfilterSave(projectName, timeOut), "save", action.BOOLEAN)) {
					log(LogStatus.INFO, "successfully click on save buton", YesNo.No);
					WebElement ele = getCreatedConfirmationMsg(projectName, 15);
					if (ele!=null) {
						String actualValue = ele.getText().trim();
						String expectedValue=BasePageErrorMessage.listViewUpdated;
						if (actualValue.contains(expectedValue)) {
							log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);
							return true;
						} else {
							log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
							BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
						}
					} else {
						sa.assertTrue(false,"Created Task Msg Ele not Found");
						log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);

					}
				}
				else {
					log(LogStatus.ERROR, "save button is not clickable", YesNo.No);
				}
			}else {
				log(LogStatus.ERROR, "done button is not clickable", YesNo.No);
			}
		}else {
			log(LogStatus.ERROR, "all checkbox is not clickable", YesNo.No);
		}
	}else {
		log(LogStatus.ERROR, "list filter section is not clickable", YesNo.No);
	}
	return false;
}

/**
 * @param projectName
 * @param pageName
 * @param activityTimeLineItem
 * @param timeOut
 * @return getActivityTimeLineItem
 */
public WebElement getActivityTimeLineItem(String projectName,PageName pageName,ActivityTimeLineItem activityTimeLineItem,int timeOut){
	clickUsingJavaScript(driver, getactivityLineItemsDropdown(projectName, 10), "dropdown", action.BOOLEAN);

	String xpath="";
	//WebElement ele;
	String activity = activityTimeLineItem.toString().replace("_", " ");
	
	if (ActivityTimeLineItem.New_Meeting.equals(activityTimeLineItem) || 
			ActivityTimeLineItem.New_Task.equals(activityTimeLineItem) ||
				ActivityTimeLineItem.New_Call.equals(activityTimeLineItem)) {
		xpath="//div[contains(@class,'slds-grid primaryFieldRow')]//*[text()='"+activity+"']";
	}else {
		if (projectName.equalsIgnoreCase(ProjectName.PE.toString()))
			xpath="//div[@id='completeDiv' and @class='cActivityTimeline']/..//*[text()='"+activity+"']";
		else
			xpath="//div[@id='completeDiv' and @class='cActivityTimeline']/..//*[text()='"+activity+"']";	
	}

	List<WebElement> li=FindElements(driver, xpath, activityTimeLineItem.toString());
	int i=0;
	for (WebElement ele:li) {
		ele = isDisplayed(driver, ele  , "visiblity", 10, activityTimeLineItem.toString());
		if (ele!=null) {
			appLog.info("Element Found : "+activity);
			return ele;
		}
		appLog.error("Element Not Found, attempt : "+(i+1)); 
		i++;
	}
	appLog.error("Element Not Found : "+activity); 
	return null;

}

/**
 * @param projectName
 * @param pageName
 * @param activityTimeLineItem
 * @param timeOut
 * @return getActivityTimeLineItem2
 */
public WebElement getActivityTimeLineItem2(String projectName,PageName pageName,ActivityTimeLineItem activityTimeLineItem,int timeOut){
	
	String xpath="";
	WebElement ele;
	String activity = activityTimeLineItem.toString().replace("_", " ");
	if (projectName.contains(ProjectName.PE.toString()))
		xpath="//div[@id='completeDiv']/..//*[@title='"+activity+"']";
	else
		xpath="//div[@id='completeDiv']/..//*[@title='"+activity+"']";
	ele = isDisplayed(driver, FindElement(driver, xpath, activityTimeLineItem.toString(), action.SCROLLANDBOOLEAN, timeOut)
			, "visiblity", 30, activityTimeLineItem.toString());
	if (ele!=null) {
	appLog.info("Element Found : "+activity);	
	}else {
		appLog.error("Element Not Found : "+activity);	
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
public WebElement getElementForActivityTimeLineTask(String projectName,PageName pageName,ActivityType activityType,String subject,SubjectElement subjectElement,int timeOut){

	WebElement ele;
	String type="";
	if (activityType==ActivityType.Next) {
	type="Next_steps";
	} else {
		type="slds-section__title  past_activity";
	}
	
	String nextStepsXpath = "//div[@class='"+type+"']/following-sibling::div[@class='activity-timeline'][1]";
	String subjectXpath = nextStepsXpath+"//*[@title='"+subject+"']";

	String eleXpath = "";
	
	if (subjectElement==SubjectElement.CheckBox) {
		eleXpath=subjectXpath+"/preceding-sibling::span//input";
	} else if(subjectElement==SubjectElement.RedFlag) {
		eleXpath=subjectXpath+"/following-sibling::div//*[@title='High-Priority Task']";
	} else if(subjectElement==SubjectElement.ExpandIcon) {
		eleXpath=subjectXpath+"/../../../..//div[contains(@id,'expandIcon')]";
	} else if(subjectElement==SubjectElement.CollapseIcon) {
		eleXpath=subjectXpath+"/../../../..//div[contains(@id,'collapseIcon')]";
	}else if(subjectElement==SubjectElement.Attachment) {
		eleXpath=subjectXpath+"/following-sibling::div//*[@title='attachment']";
	}else if(subjectElement==SubjectElement.StrikedText) {
		eleXpath=subjectXpath;
	}
	else {
		eleXpath=subjectXpath+"//a";
	}
	ele = FindElement(driver, eleXpath, subjectElement+" For : "+subject, action.SCROLLANDBOOLEAN, timeOut);
	ele = isDisplayed(driver, ele, "Visibility", timeOut, subjectElement+" For : "+subject);
	// Due DATE

	if (ele!=null) {
		log(LogStatus.INFO,subjectElement+" Found For : "+subject,YesNo.No);	

	}else {
		log(LogStatus.SKIP,subjectElement+" Not Found For : "+subject,YesNo.Yes);
	}

	return ele;
}

/**
 * @author Azhar Alam
 * @param passedDate
 * @param zone
 * @return date according to time zone
 */
public static String getDateValueAccording(String passedDate,String zone) {

	appLog.info("Passed date : "+passedDate);
	appLog.info("Today date : "+previousOrForwardDateAccordingToTimeZone(0,"MM/dd/YYYY",zone));
	appLog.info("Yesterday date : "+previousOrForwardDateAccordingToTimeZone(-1,"MM/dd/YYYY",zone));
	appLog.info("Tomorrow date : "+previousOrForwardDateAccordingToTimeZone(1,"MM/dd/YYYY",zone));
	String value = "";
	
	if (passedDate.equalsIgnoreCase(previousOrForwardDateAccordingToTimeZone(0,"MM/dd/YYYY",zone)) || 
			passedDate.equalsIgnoreCase(previousOrForwardDateAccordingToTimeZone(0,"M/d/YYYY",zone))) {
		value="Today";
	}else if(passedDate.equalsIgnoreCase(previousOrForwardDateAccordingToTimeZone(-1,"MM/dd/YYYY",zone)) || 
			passedDate.equalsIgnoreCase(previousOrForwardDateAccordingToTimeZone(-1,"M/d/YYYY",zone))) {
		value="Yesterday";
	}else if(passedDate.equalsIgnoreCase(previousOrForwardDateAccordingToTimeZone(1,"MM/dd/YYYY",zone)) || 
			passedDate.equalsIgnoreCase(previousOrForwardDateAccordingToTimeZone(1,"M/d/YYYY",zone))){
		value="Tomorrow";
	} else if(passedDate.equalsIgnoreCase(DueDate.No_due_date.toString())) {
		value=passedDate;
	}
	else {
		value=findThreeLetterMonthName(passedDate);
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
public void verifyActivityAtNextStep2(String projectName,PageName pageName,String createdItemValue,String subject,String assignedToMsg,String dueDate,boolean isMeetingType,String meetingTypeValue, boolean isDescription, String descriptionValue, int timeOut){

	WebElement ele;
	dueDate = getDateValueAccording(dueDate, AmericaLosAngelesTimeZone);
	String actualValue="";

	String nextStepsXpath = "//div[@class='Next_steps']/following-sibling::div[@class='activity-timeline'][1]";
	String subjectXpath = nextStepsXpath+"//*[@title='"+subject+"']";

	String dateXpath = subjectXpath+"/..//following-sibling::*//*[contains(text(),'"+dueDate+"')]";
	ele = FindElement(driver, dateXpath, dueDate.toString(), action.SCROLLANDBOOLEAN, 20);

	// Due DATE

	if (ele!=null) {
		log(LogStatus.INFO,dueDate+" verified for subject : "+subject+" For item : "+createdItemValue,YesNo.No);	


		// Assigned To 
		String assignedToxpath = subjectXpath+"/../..//following-sibling::p";
		ele = FindElement(driver, assignedToxpath, "Asigned To ", action.SCROLLANDBOOLEAN, 5);
		if (ele!=null) {
			log(LogStatus.INFO,"Asigned To verified for subject : "+subject+" For item : "+createdItemValue,YesNo.No);	
			if (assignedToMsg!=null) {
				System.err.println(">>>>>>>>>>>>   "+ele.getText().trim());
				actualValue=ele.getText().trim().replace("\n", " ");
				System.err.println(">>>>>>>>>>>>   "+actualValue.replace("\n", " "));
				if (assignedToMsg.equals(actualValue)) {
					log(LogStatus.INFO,assignedToMsg+" Verified for subject : "+subject+" For item : "+createdItemValue,YesNo.No);

				}else {
					sa.assertTrue(false,assignedToMsg+" not Verified for subject : "+subject+" For item : "+createdItemValue+"\nActual  :  "+actualValue+"\nExpected : "+assignedToMsg);
					log(LogStatus.SKIP,assignedToMsg+" not Verified for subject : "+subject+" For item : "+createdItemValue+"\nActual  :  "+actualValue+"\nExpected : "+assignedToMsg,YesNo.Yes);
				}
			}
			

		}else {
			sa.assertTrue(false,"Asigned To not verified for subject : "+subject+" For item : "+createdItemValue);
			log(LogStatus.SKIP,"Asigned To not verified for subject : "+subject+" For item : "+createdItemValue,YesNo.Yes);
		}


		// Meeting Type 
		if (isMeetingType) {
			
			String meetingTypeXpath = subjectXpath+"/../..//following-sibling::div//article//ul//li/span[text()='Meeting Type']";
			ele = FindElement(driver, meetingTypeXpath, "Meeting Type", action.SCROLLANDBOOLEAN, 5);
			if (ele!=null) {
				log(LogStatus.INFO,"Meeting Type verified for subject : "+subject,YesNo.No);

				String meetingTypeValueXpath = meetingTypeXpath+"/following-sibling::span";
				ele = FindElement(driver, meetingTypeValueXpath, meetingTypeValue, action.SCROLLANDBOOLEAN, 5);
				if (ele!=null) {
					log(LogStatus.INFO,meetingTypeValue+" Element is Present for subject : "+subject+" For item : "+createdItemValue,YesNo.No);	
					actualValue =  ele.getText().trim();
					if (meetingTypeValue.equals(actualValue)) {
						log(LogStatus.INFO,meetingTypeValue+" Verified for subject : "+subject+" For item : "+createdItemValue,YesNo.No);

					}else {
						sa.assertTrue(false,meetingTypeValue+" not Verified for subject : "+subject+" For item : "+createdItemValue+" Actual : "+actualValue+" \t Expected : "+meetingTypeValue);
						log(LogStatus.SKIP,meetingTypeValue+" not Verified for subject : "+subject+" For item : "+createdItemValue+" Actual : "+actualValue+" \t Expected : "+meetingTypeValue,YesNo.Yes);
					}

				}else {
					sa.assertTrue(false,meetingTypeValue+" Element is not Present for subject : "+subject+" For item : "+createdItemValue);
					log(LogStatus.SKIP,meetingTypeValue+" Element is not Present for subject : "+subject+" For item : "+createdItemValue,YesNo.Yes);
				}


			}else {
				sa.assertTrue(false,"Meeting Type not verified for subject : "+subject+" For item : "+createdItemValue);
				log(LogStatus.SKIP,"Meeting Type not verified for subject : "+subject+" For item : "+createdItemValue,YesNo.Yes);
			}
		}
		



		// Description 
		
		if (isDescription) {
			
			String descriptionXpath = subjectXpath+"/../..//following-sibling::div//article//div[1][text()='Description']";
			ele = FindElement(driver, descriptionXpath, "Description", action.SCROLLANDBOOLEAN, 5);
			if (ele!=null) {
				log(LogStatus.INFO,"Description Text verified for subject : "+subject+" For item : "+createdItemValue,YesNo.No);	

				String descriptionValueXpath = descriptionXpath+"/following-sibling::div";
				ele = FindElement(driver, descriptionValueXpath, descriptionValue, action.SCROLLANDBOOLEAN, 5);
				if (ele!=null) {
					log(LogStatus.INFO,descriptionValue+" Element is Present for subject : "+subject+" For item : "+createdItemValue,YesNo.No);	
					actualValue =  ele.getText().trim();
					if (descriptionValue.equals(actualValue)) {
						log(LogStatus.INFO,descriptionValue+" Verified for subject : "+subject+" For item : "+createdItemValue,YesNo.No);

					}else {
						sa.assertTrue(false,descriptionValue+" not Verified for subject : "+subject+" For item : "+createdItemValue+" Actual : "+actualValue+" \t Expected : "+descriptionValue);
						log(LogStatus.SKIP,descriptionValue+" not Verified for subject : "+subject+" For item : "+createdItemValue+" Actual : "+actualValue+" \t Expected : "+descriptionValue,YesNo.Yes);
					}

				}else {
					sa.assertTrue(false,descriptionValue+" Element is not Present for subject : "+subject+" For item : "+createdItemValue);
					log(LogStatus.SKIP,descriptionValue+" Element is not Present for subject : "+subject+" For item : "+createdItemValue,YesNo.Yes);
				}


			}else {
				sa.assertTrue(false,"Description Text not verified for subject : "+subject+" For item : "+createdItemValue);
				log(LogStatus.SKIP,"Description Text not verified for subject : "+subject+" For item : "+createdItemValue,YesNo.Yes);
			}
			
		}
		


	}else {
		sa.assertTrue(false,dueDate+" not verified for subject : "+subject+" For item : "+createdItemValue);
		log(LogStatus.SKIP,dueDate+" not verified for subject : "+subject+" For item : "+createdItemValue,YesNo.Yes);
	}


}


/**
 * @param projectName
 * @param TabName
 * @return true if able to click on Tab
 */
public boolean clickOnTab(String projectName,String TabName) {

	String tabName = null;
	boolean flag = false;
	WebElement ele;
	if (TabName.contains("Entit")) {
		tabName ="Entities";
	}else if (TabName.contains("Inst")) {
		tabName ="Institutions";
	}else {
		tabName = TabName;
	}
	System.err.println("Passed switch statement");
	if (tabName!=null) {
		ele = FindElement(driver, "//a[contains(@href,'lightning') and contains(@title,'" + tabName + "')]/span/..",tabName, action.SCROLLANDBOOLEAN,30);
		ele = isDisplayed(driver,ele,"visibility", 30, tabName);
		if (ele != null) {
			appLog.info("Tab Found");
			ThreadSleep(5000);
			if (clickUsingJavaScript(driver, ele, tabName+" :Tab")) {
				CommonLib.log(LogStatus.INFO, "Tab found", YesNo.No);
				appLog.info("Clicked on Tab : "+tabName);
				flag = true;
			} else {
				appLog.error("Not Able to Click on Tab : "+tabName);
			}

		} else {
			CommonLib.log(LogStatus.INFO, "Going to found tab after clicking on More Icon", YesNo.No);
			if (click(driver, getMoreTabIcon(projectName, 10), "More Icon", action.SCROLLANDBOOLEAN)) {
				ele = FindElement(driver,"//a[contains(@href,'lightning')]/span[@class='slds-truncate']/span[contains(text(),'"	+ tabName + "')]",tabName, action.SCROLLANDBOOLEAN, 10);
				ele = isDisplayed(driver,ele,"visibility", 10, tabName);
				if (ele!=null) {
					if (clickUsingJavaScript(driver, ele, tabName+" :Tab")) {
						appLog.info("Clicked on Tab on More Icon: "+tabName);
						CommonLib.log(LogStatus.INFO, "Tab found on More Icon", YesNo.No);
						flag = true;
					}	
				}

			} else {
				appLog.error("Not Able to Clicked on Tab on More Icon: "+tabName);
			}

		}
	}

	return flag;
}


/**
 * @author Azhar Alam
 * @param projectName
 * @param alreadyCreated
 * @param isClick TODO
 * @param timeout
 * @param tabName
 * @return true if able to click on particular item on Particular tab
 */
public boolean clickOnAlreadyCreatedItem(String projectName,String alreadyCreated, boolean isClick, int timeout) {
	boolean flag=false;
	String xpath="";
	String viewList = null;
	viewList = "Automation All";
	WebElement ele, selectListView;
	ele = null;
	ThreadSleep(3000);
	refresh(driver);
	if (click(driver, getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
		ThreadSleep(3000);
		xpath="//div[@class='listContent']//li/a/span[text()='" + viewList + "']";
		selectListView = FindElement(driver, xpath,"Select List View : "+viewList, action.SCROLLANDBOOLEAN, 30);
		if (click(driver, selectListView, "select List View : "+viewList, action.SCROLLANDBOOLEAN)) {
			ThreadSleep(3000);
			ThreadSleep(5000);

			if (sendKeys(driver, getSearchIcon_Lighting(20), alreadyCreated+"\n", "Search Icon Text",action.SCROLLANDBOOLEAN)) {
				ThreadSleep(5000);

				xpath = "//table[@data-aura-class='uiVirtualDataTable']//tbody//tr//th//span//*[text()='"+ alreadyCreated + "']";
				ele = FindElement(driver,xpath,alreadyCreated, action.BOOLEAN, 30);
				ThreadSleep(2000);
				if (isClick) {
					if (click(driver, ele, alreadyCreated, action.BOOLEAN)) {
						ThreadSleep(3000);
						click(driver, getPagePopUp(projectName,5), "Page PopUp", action.BOOLEAN);
						flag=true;
					} else {
					
					}
				} else {
					if (ele!=null) {
						appLog.info("Item Found : " + alreadyCreated);
						flag=true;
					} else {
						appLog.error("Item not Found : " + alreadyCreated);
					}
				}
				
			} else {
				appLog.error("Not able to enter value on Search Box");
			}
		} else {
			appLog.error("Not able to select on Select View List : "+viewList);
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
public WebElement getRelatedTab(String projectName,String relatedTab,int timeOut){
String xpath="";
WebElement ele;
String related = relatedTab.toString().replace("_", " ");
if (projectName.contains(ProjectName.PE.toString()))
	xpath="//li[@title='"+related+"']//a";
else
xpath = "//li//*[@title='"+related+"' or text()='"+related+"']";
xpath = "//li//*[@title='"+related+"' or text()='"+related+"']";
ele = isDisplayed(driver, FindElement(driver, xpath, relatedTab.toString(), action.SCROLLANDBOOLEAN, timeOut)
		, "visiblity", 30, relatedTab.toString());
if (ele!=null) {
appLog.info("Element Found : "+related);	
}else {
	appLog.error("Element Not Found : "+related);	
	appLog.error("Going to check on more "+related);	
	xpath = "//li//button[@title='More Tabs']";
	ele = FindElement(driver, xpath, relatedTab.toString(), action.SCROLLANDBOOLEAN, timeOut);
	click(driver, ele, "More Tab", action.BOOLEAN);
	ThreadSleep(3000);
	
	xpath = "//a/span[text()='"+related+"']";
	ele = isDisplayed(driver, FindElement(driver, xpath, relatedTab.toString(), action.SCROLLANDBOOLEAN, timeOut)
			, "visiblity", 30, relatedTab.toString());
	
	
}
return ele;

}


/**
 * @author Azhar Alam
 * @param projectName
 * @param toggleTab
 * @param btnName
 * @param action
 * @param timeOut
 * @return toggle SDG Button webElement
 */
public WebElement toggleSDGButtons(String projectName,String toggleTab,ToggleButtonGroup btnName,action action,int timeOut) {
	String btname = btnName.toString();
	String xpath = "//*[contains(text(),'"+toggleTab+"')]/../../..//following-sibling::div//button[@title='"+btname+"']";
	WebElement ele = FindElement(driver, xpath,toggleTab+" >> "+btname, action, timeOut);
	scrollDownThroughWebelement(driver, ele, "Toggle Button : "+btname);
	ele = isDisplayed(driver, ele, "Visibility", timeOut, "Toggle Button : "+btname);
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
public WebElement toggleButton(String projectName,String btnName,action action,int timeOut) {
	String xpath = "//button[contains(@title,'"+btnName+"')]";
	WebElement ele = FindElement(driver, xpath,"Toggle Button : "+btnName, action, timeOut);
	scrollDownThroughWebelement(driver, ele, "Toggle Button : "+btnName);
	ele = isDisplayed(driver, ele, "Visibility", timeOut, "Toggle Button : "+btnName);
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
public WebElement toggleButtonColumnNames(String projectName,String btnName,String columnName,action action,int timeOut) {
	String xpath = "//a[text()='"+btnName+"']//ancestor::article//th//div/span[contains(text(),'"+columnName+"')]";
	WebElement ele = FindElement(driver, xpath,"Toggle Button : "+btnName+" >> column Name : "+columnName, action, timeOut);
	scrollDownThroughWebelement(driver, ele, "Toggle Button : "+btnName+" >> column Name : "+columnName);
	ele = isDisplayed(driver, ele, "Visibility", timeOut, "Toggle Button : "+btnName+" >> column Name : "+columnName);
	return ele;
}


/**
 * @author Akul Bhutani
 * @param number
 * @param format
 * @return string with specific format with currency symbol
 */
public static String convertNumberAccordingToFormatWithCurrencySymbol(String number,String format){

	double d = Double.parseDouble(number);
	DecimalFormat myFormatter = new DecimalFormat(format);
	String output = new DecimalFormatSymbols(Locale.US).getCurrencySymbol()+myFormatter.format(d);
	System.err.println(" outpurttt >>>> "+output);
	return output;

}

/**
 * @author Azhar Alam
 * @param header
 * @param itemName
 * @return webElement for created item on Page 
 */
public WebElement verifyCreatedItemOnPage(Header header,String itemName)
{
	WebElement ele;
	String xpath ="";
	String head =header.toString().replace("_", " ");
	ThreadSleep(3000);
	xpath="//*[contains(text(),'"+head+"')]/..//*[text()='"+itemName+"']";
	 ele = FindElement(driver, xpath, "Header : "+itemName, action.BOOLEAN, 30);
	 ele = isDisplayed(driver, ele, "Visibility", 10, head+" : "+itemName);
	return ele;
}

/**
 * @author Akul Bhutani
 * @param number
 * @return string after converting a number in to million format
 */
public static String convertNumberIntoMillions(String number){
	double d = Double.parseDouble(number);
	double aa = d/1000000;
	String output = new DecimalFormatSymbols(Locale.US).getCurrencySymbol()+aa;
	System.err.println("convertNumberIntoMillions  outpurttt >>>> "+output);
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
public WebElement customToggleButton(String projectName,String btnName,action action,int timeOut) {
	String xpath = "//*[text()='"+btnName+"']";
	WebElement ele = FindElement(driver, xpath,"Toggle Button : "+btnName, action, timeOut);
	scrollDownThroughWebelement(driver, ele, "Toggle Button : "+btnName);
	ele = isDisplayed(driver, ele, "Visibility", timeOut, "Toggle Button : "+btnName);
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
public WebElement commonInputElement(String projectName,String labelName,action action,int timeOut) {
	labelName=labelName.replace("_", " ");
	String xpath = "//*[text()='"+labelName+"']//following-sibling::div//input";
	WebElement ele = FindElement(driver, xpath,labelName+" TextBox", action, timeOut);
	ele = isDisplayed(driver, ele, "Visibility",timeOut, labelName+" TextBox");
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
public WebElement getMenuTab(String projectName,String labelName,action action,int timeOut) {
	String xpath = "//div[@class='flexipageComponent']//span[text()='"+labelName+"']";
	WebElement ele = FindElement(driver, xpath,labelName+" TextBox", action, timeOut);
	ele = isDisplayed(driver, ele, "Visibility",timeOut, labelName);
	return ele;
}

/**
 * @author Akul Bhutani
 * @param projectName
 * @param timeOut
 * @return true if automation All is present in View List
 */
public boolean isAutomationAllListViewAdded(String projectName, int timeOut) {
	String viewList="Automation All",xpath="";
	if (click(driver, getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
		ThreadSleep(3000);
		xpath="//span[text()='" + viewList + "']";
		WebElement selectListView = FindElement(driver, xpath,"Select List View : "+viewList, action.SCROLLANDBOOLEAN, 5);
		ThreadSleep(3000);
		if ( selectListView!=null) {
			log(LogStatus.INFO, "automation all is already present", YesNo.No);
			return true;
		}
		else {
			log(LogStatus.ERROR, "not found automation all.. now creating", YesNo.No);
			
		}
	}else {
		log(LogStatus.ERROR, "list dropdown is not clickable, so cannot check presence of Automation All", YesNo.Yes);
		
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
public boolean verifyAccordion(String projectName,String recordName,String[] fieldValues, int timeOut) {
	String field="";
	String value="";
	boolean flag=true;
	String finalx="",xpath = "//article[contains(@class,'RelatedListAccordion')]//a[text()='"+recordName+"']";
	WebElement ele=FindElement(driver, xpath, recordName, action.SCROLLANDBOOLEAN,10);
	if (isDisplayed(driver, ele, "visibility", timeOut, recordName+" in accordion")!=null) {
		xpath = "//article[contains(@class,'RelatedListAccordion')]//a[text()='"+recordName+"']/following-sibling::ul";
		if (fieldValues!=null) {
			for (String fieldValue:fieldValues) {
				field=fieldValue.split(breakSP)[0];
				value=fieldValue.split(breakSP)[1];
				field = field.replace("_", " ");
				finalx=xpath+"//li//div[@title='"+field+"']/following-sibling::div[@title='"+value+"']";
				ele=FindElement(driver, finalx, field+" and "+value, action.SCROLLANDBOOLEAN, 10);
				ele=isDisplayed(driver, ele, "visibility", 10, field+" and "+value);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of "+field+" and "+value, YesNo.No);

				}
				else {
					log(LogStatus.ERROR, "could not verify "+field+" and "+value, YesNo.Yes);
					flag=false;
				}
			}
		}
	}else {
		log(LogStatus.ERROR, "could not verify presence of "+recordName+" in accordion ", YesNo.Yes);
		flag=false;
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
	boolean flag=true;
	String xpath = "//article[contains(@class,'RelatedListAccordion')]//a[text()='"+record+"']";
	String finalx=xpath +"/../preceding-sibling::div//img";
	WebElement ele=FindElement(driver, xpath, "accordion record", action.SCROLLANDBOOLEAN, 10);
	ele=isDisplayed(driver, ele, "visibility", 10, "accordion record profile image");
	if (ele!=null) {
		ele=FindElement(driver, finalx, "img in contact accordion", action.BOOLEAN, 10);
		ele=isDisplayed(driver, ele, "visibility", 10, "accordion record profile image");
		String id = ele.getAttribute("src");
		if (id.contains(imgId)) {
			log(LogStatus.INFO, "successfully verified img id\n"+id+"\nand\n"+imgId, YesNo.No);
		}
		else {
			log(LogStatus.ERROR, "could not verify id. found:\n"+id+"\nexpected:\n"+imgId, YesNo.Yes);
			flag=false;
		}
	}else {
		log(LogStatus.ERROR, "could not find accordion", YesNo.Yes);
		flag=false;
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
public boolean updatePhoto(String projectName,String pageName,String uploadImagePath,boolean errorMsgCheck) {
	String imgId=null;
	boolean flag = false;
	WebElement ele=getUpdatePhotoCameraIcon(10);
	if(ele!=null) {
		ThreadSleep(500);
		if(click(driver, ele,"update photo camera icon", action.BOOLEAN)) {
			log(LogStatus.INFO, "clicked on update photo icon", YesNo.No);
			if(click(driver, updateAndDeletePhotoXpath(IconType.updatePhoto, 10), "update photo button", action.BOOLEAN)) {
				log(LogStatus.INFO, "clicked on update photo button", YesNo.No);
				ThreadSleep(1000);
				String path=System.getProperty("user.dir")+uploadImagePath;
				System.err.println("Path : "+path);
				if (sendKeys(driver, getUploadImageXpath(10), path, "upload photo button", action.SCROLLANDBOOLEAN) ) {
					log(LogStatus.PASS, "clicked on upload image button on "+pageName, YesNo.No);
					ThreadSleep(500);
						ThreadSleep(1000);
						if(!errorMsgCheck) {
							if(click(driver, getRecordPageSettingSave(10),"Save button", action.BOOLEAN)) {
								log(LogStatus.PASS, "clicked on save button and image is updtaed "+path +" on "+pageName, YesNo.No);
								ThreadSleep(4000);
								imgId=getimgLink(projectName, 10).getAttribute("src");
								if (imgId!=null){
									log(LogStatus.INFO, "found id of img uploaded: "+imgId, YesNo.Yes);
									flag= true;
								}else {
									log(LogStatus.ERROR, "could not find id of img uploaded", YesNo.Yes);
								}
							}else {
								log(LogStatus.PASS, "Not able to click on save button and so cannot updtaed image from path "+path +" on "+pageName, YesNo.No);
							}
						}else {
							if(getInvalidImageErrorMsg(10)!=null) {
								String ss = getInvalidImageErrorMsg(10).getText().trim();
								if(ss.equalsIgnoreCase(BasePageErrorMessage.invalidImageErrorMsg)) {
									log(LogStatus.PASS, "Error Message is verified for "+uploadImagePath, YesNo.No);
									flag= true;
								}else {
									log(LogStatus.ERROR, "Error Message is not verified : "+uploadImagePath, YesNo.Yes);
								}
							}else {
								log(LogStatus.ERROR, "Not able to find the error meesage after upload invalid image : "+uploadImagePath, YesNo.Yes);
							}
							if(click(driver, getCancelBtn(10), "cancel button", action.BOOLEAN)) {
								log(LogStatus.PASS, "Clicked on upload image popoup cancel button", YesNo.No);
							}else {
								log(LogStatus.ERROR, "Not able to click on upload image cancel button so cannot close popup", YesNo.Yes);
							}
						}
				}else {
					log(LogStatus.ERROR, "Not able to click on upload image on "+pageName+" so cannot update image from Path : "+path, YesNo.Yes);
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on update photo button so cannot update photo on "+pageName, YesNo.Yes);
			}
			
		}else {
			log(LogStatus.ERROR, "Not able to click on update photo icon so cannot upload photo on "+pageName, YesNo.Yes);
		}
	}else {
		log(LogStatus.ERROR, "camera icon is not displaying on "+pageName+" so cannot upload photo", YesNo.Yes);
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
	String xpath = "//article[contains(@class,'RelatedListAccordion')]//a[text()='"+object+"']";
	WebElement ele=FindElement(driver, xpath, object + "accordion", action.SCROLLANDBOOLEAN, 10);
	return isDisplayed(driver, ele, "visibility", 10, object + "accordion");
	
}

/**
 * @author Akul Bhutani
 * @param projectName
 * @param object
 * @return return Accordion View Details Link webElement
 */
public WebElement returnAccordionViewDetailsLink(String projectName, String object) {
	String xpath = "//article[contains(@class,'RelatedListAccordion')]//a[text()='"+object+"']/../../../following-sibling::footer//a[contains(text(),'View')][contains(text(),'Details')]";
	WebElement ele=FindElement(driver, xpath, object + "accordion", action.SCROLLANDBOOLEAN, 10);
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
	String xpath="//div[contains(@id,'modal')]//*[@title='"+ec.toString()+"']";
	WebElement ele=FindElement(driver, xpath, ec + " accordion", action.SCROLLANDBOOLEAN, timeOut);
	return isDisplayed(driver, ele, "visibility", timeOut, ec + " accordion");

}

/**
 * @author Akul Bhutani
 * @param projectName
 * @param object
 * @return accordion Modal Window Close webElement
 */
public WebElement accordionModalWindowClose(String projectName, String object) {
	String xpath = "//h2[text()='"+object+"']/preceding-sibling::button[@title='close']";
	WebElement ele=FindElement(driver, xpath, object + "accordion", action.SCROLLANDBOOLEAN, 10);
	return isDisplayed(driver, ele, "visibility", 10, object + "accordion");
	
}

/**
 * @author Akul Bhutani
 * @param projectName
 * @param ObjectName
 * @param timeOut
 * @return true if Automation All view list is added
 */
public boolean isAutomationAllListViewForObject(String projectName,String ObjectName, int timeOut) {
	String viewList="Automation All",xpath="";
		ThreadSleep(3000);
		xpath="//span[text()='"+ObjectName+"']/../../../following-sibling::div//span[text()='"+viewList+"']";
		WebElement selectListView = FindElement(driver, xpath,"Select List View : "+viewList+" for "+ObjectName, action.SCROLLANDBOOLEAN, 5);
		ThreadSleep(3000);
		if ( selectListView!=null) {
			log(LogStatus.INFO, viewList+" for "+ObjectName+" available", YesNo.No);
			return true;
		}
		else {
			log(LogStatus.ERROR,  viewList+" for "+ObjectName+" is not available", YesNo.No);
			
		}
	

	return false;
}

/**
 * @author Azhar Alam
 * @param projectName
 * @param smaddl
 * @param timeOut
 * @return  action Drop down Element
 */
public WebElement actionDropdownElement(String projectName, ShowMoreActionDropDownList smaddl, int timeOut) {
	String actionDropDown = smaddl.toString().replace("_", " ");
	String xpath ="//span[text()='"+actionDropDown+"']";
	xpath="//*[@name='"+actionDropDown+"' or text()='"+actionDropDown+"']";
	return isDisplayed(driver, FindElement(driver, xpath, "show more action down arrow", action.SCROLLANDBOOLEAN, 10), "visibility", timeOut, actionDropDown);
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
public WebElement accordionSDGButtons(String projectName,String toggleTab,ToggleButtonGroup btnName,action action,int timeOut) {
	String btname = btnName.toString();
	String xpath = "//h2[contains(text(),'"+toggleTab+"')]/../../..//following-sibling::div//button[@title='"+btname+"']";
	WebElement ele = FindElement(driver, xpath,toggleTab+" >> "+btname, action, timeOut);
	scrollDownThroughWebelement(driver, ele, "Toggle Button : "+btname);
	ele = isDisplayed(driver, ele, "Visibility", timeOut, "Toggle Button : "+btname);
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
public WebElement accordionSDGContactCheckbox(String projectName,String contact,String field,action action,int timeOut) {
	String xpath ="//*[text()='"+contact+"']/../../../../following-sibling::td[contains(@data-label,'"+field+"')]/../../..//th//input";
	WebElement ele = FindElement(driver, xpath,"checkbox for "+contact, action, timeOut);
	scrollDownThroughWebelement(driver, ele, "checkbox for "+contact);
	ele = isDisplayed(driver, ele, "Visibility", timeOut, "checkbox for "+contact);
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
public WebElement accordionSDGActionButtons(String projectName,String toggleTab,String btnName,action action,int timeOut) {
	String btname = btnName.replace("_", " ");
	String xpath = "//*[contains(text(),'"+toggleTab+"')]/../../..//following-sibling::div//button[text()='"+btname+"']";
	WebElement ele = FindElement(driver, xpath,toggleTab+" >> "+btname, action, timeOut);
	scrollDownThroughWebelement(driver, ele, "Toggle Button : "+btname);
	ele = isDisplayed(driver, ele, "Visibility", timeOut, "Toggle Button : "+btname);
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
public WebElement sdgButtons(String projectName, String field,String new1, int timeOut) {
	String xpath ="//span//*[text()='"+field+"']/../../../following-sibling::*//button[text()='"+new1+"']";
	WebElement ele = FindElement(driver, xpath,"sdg buttons", action.BOOLEAN, timeOut);
	return isDisplayed(driver, ele, "visibility", timeOut, "sdg button");
}

/**
 * @author Akul Bhutani
 * @param projectName
 * @param contact
 * @return sdg contact image xpath
 */
public String sdgContactImageXpath(String projectName, String contact) {
	return "//*[@title='"+contact+"']/preceding-sibling::img";
}

/**
 * @author Akul Bhutani
 * @param projectName
 * @param contact
 * @param timeOut
 * @return sdg contact image webElement
 */
public WebElement sdgContactImage(String projectName, String contact, int timeOut) {
	String xpath =sdgContactImageXpath(projectName, contact);
	WebElement ele = FindElement(driver, xpath,"contact image on sdg", action.BOOLEAN, timeOut);
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
public boolean clickOnEditButtonOnSDG(String projectName, String contact, String field,int timeOut) {
	String xpath ="//*[text()='"+contact+"']/../../../../following-sibling::td[contains(@data-label,'"+field+"')]//button";
	WebElement ele = FindElement(driver, xpath,"edit button for "+field, action.BOOLEAN, timeOut);
	JavascriptExecutor js = (JavascriptExecutor)driver;
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
	String xpath ="//input[@name='"+field+"']";
	WebElement ele = FindElement(driver, xpath,"input textbox "+field, action.BOOLEAN, timeOut);
	return isDisplayed(driver, ele, "visibility", timeOut, "input textbox "+field);
}

/**
 * @author Akul Bhutani
 * @param projectName
 * @param fileName
 * @param timeOut
 * @return webElement for document Name
 */
public WebElement documentNameOnFilesApp(String projectName, String fileName, int timeOut) {
	String xpath ="//span[@title='"+fileName+"']";
	WebElement ele = FindElement(driver, xpath,fileName, action.BOOLEAN, timeOut);
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
	String xpath ="//img[@alt='"+fileName+"']";
	WebElement ele = FindElement(driver, xpath,fileName, action.BOOLEAN, timeOut);
	ele=isDisplayed(driver, ele, "visibility", timeOut, fileName);
	Actions actions = new Actions(driver);
	actions.contextClick(ele).build().perform();
	String a="";
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
		a=getClipBoardData();
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
	String imgId=null;
	DealPage dp = new DealPage(driver);
	ContactsPage cp = new ContactsPage(driver);
	Actions actions = new Actions(driver);
	scrollDownThroughWebelement(driver,dp.getimgIcon(projectName, 10) , "img");
	click(driver, dp.getimgIcon(projectName, 10), "img icon", action.SCROLLANDBOOLEAN);
	ThreadSleep(2000);
	log(LogStatus.INFO, "click on img link", YesNo.No);
	if (click(driver,cp. getupdatePhotoLink(projectName,ContactPagePhotoActions.Delete_Photo, 10), ContactPagePhotoActions.Delete_Photo.toString(), action.SCROLLANDBOOLEAN)) {
		if (click(driver, getdeletePhotoButton(projectName, 10), "delete button", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(5000);
			log(LogStatus.INFO, "successfully uploaded photo", YesNo.No);
			if (dp.getimgIconForPath(projectName, 5)==null) {
				log(LogStatus.INFO, "successfully deleted img"+imgId, YesNo.Yes);

				return true;
			}
			else {
				log(LogStatus.ERROR, "could not delete user img for "+user, YesNo.Yes);
				sa.assertTrue(false, "could not delete user img for "+user);

			}
		}else {
			log(LogStatus.ERROR, "delete photo button on popup is not clickable", YesNo.Yes);
			sa.assertTrue(false, "delete photo button on popup is not clickable");
		}
	}else {
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
public WebElement toggleEditSaveButton(String projectName,String btnName,action action,int timeOut) {
	String xpath = "//button[contains(text(),'"+btnName+"')]/../../../../../../../..//button[text()='Save']";
	WebElement ele = FindElement(driver, xpath,"Toggle Button : "+btnName, action, timeOut);
	ele = isDisplayed(driver, ele, "Visibility", timeOut, "Toggle Button : "+btnName);
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
public WebElement toggleEditCancelButton(String projectName,String btnName,action action,int timeOut) {
	String xpath = "//div[contains(@class,'sdgborder')]//button[@title='Cancel' or text()='Cancel']";
	//String xpath = "//button[contains(text(),'"+btnName+"')]/../../../../../../../..//button[text()='Cancel']";
	WebElement ele = FindElement(driver, xpath,"Toggle Button : "+btnName, action, timeOut);
	ele = isDisplayed(driver, ele, "Visibility", timeOut, "Toggle Button : "+btnName);
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
public WebElement crossIconForEventField(String projectName, String field, String name,int timeOut) {
	String xpath = "//label[text()='"+field+"']/following-sibling::div//input[@placeholder='"+name+"']/following-sibling::div/button";
	
	WebElement ele = FindElement(driver, xpath,"cross Button : "+field, action.BOOLEAN, timeOut);
	ele = isDisplayed(driver, ele, "Visibility", timeOut, "cross Button : "+field);
	return ele;
}

public boolean editButtonToggleSDG(String projectName,PageName pageName, String record, int toggleOneOrTwo, int timeOut) {
	String xpath="";
	if(pageName == PageName.Object1Page)
	xpath="(//*[text()='"+record+"']/../../following-sibling::span//button[@title='Edit'])["+toggleOneOrTwo+"]";
	else if(pageName == PageName.Object4Page)
		xpath = "(//*[text()='"+record+"']/../following-sibling::span//button[@title='Edit'])["+toggleOneOrTwo+"]";
	WebElement ele = FindElement(driver, xpath,"edit Button : "+record, action.BOOLEAN, timeOut);
	JavascriptExecutor js = (JavascriptExecutor)driver;
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
public boolean dragNDropUsingScreen(String projectName,String sourceImg,String targetImg,int timeOut) {
	boolean flag=false;
	Screen screen = new Screen();
	try {
		screen.dragDrop(sourceImg, targetImg);
		flag=true;
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
public boolean isAutomationAllListViewForObject(String projectName,String ObjectName,String viewList, int timeOut) {
	String xpath="";
		ThreadSleep(3000);
		xpath="//*[text()='"+ObjectName+"']/../../../../following-sibling::*//*[text()='"+viewList+"']";
		WebElement selectListView = FindElement(driver, xpath,"Select List View : "+viewList+" for "+ObjectName, action.SCROLLANDBOOLEAN, 5);
		ThreadSleep(3000);
		if ( selectListView!=null) {
			log(LogStatus.INFO, viewList+" for "+ObjectName+" available", YesNo.No);
			return true;
		}
		else {
			log(LogStatus.ERROR,  viewList+" for "+ObjectName+" is not available", YesNo.No);
			
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
public WebElement SDGNewButton(String projectName, String field, String name,int timeOut) {
	String xpath = "//label[text()='"+field+"']/following-sibling::div//input[@placeholder='"+name+"']/following-sibling::div/button";
	
	WebElement ele = FindElement(driver, xpath,"cross Button : "+field, action.BOOLEAN, timeOut);
	ele = isDisplayed(driver, ele, "Visibility", timeOut, "cross Button : "+field);
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
	WebElement ele=null;
	String finalLabelName="";
	String labelName="",value="";
	for (String field:fieldValues) {
		labelName=field.split(breakSP)[0];
		value=field.split(breakSP)[1];
		finalLabelName=labelName.replace("_", " ");
		xpath = "//*[text()='"+finalLabelName+"']/following-sibling::div//input";
		if (labelName.equalsIgnoreCase(SDGActionsCreationLabel.Event_Payload.toString()))
			xpath = "//*[text()='"+finalLabelName+"']/following-sibling::div//textarea";
		ele= FindElement(driver, xpath,finalLabelName, action.BOOLEAN, 10);
		ele = isDisplayed(driver, ele, "Visibility", 10, finalLabelName);
		if (labelName.equalsIgnoreCase(SDGActionsCreationLabel.Action_Type.toString())) {
			if (clickUsingJavaScript(driver, ele, finalLabelName)) {
				xpath="//*[text()='Action Type']/following-sibling::div//*[@title='"+value+"']";
				ele= FindElement(driver, xpath,finalLabelName, action.BOOLEAN, 10);
				ele = isDisplayed(driver, ele, "Visibility", 10, finalLabelName);
				if (clickUsingJavaScript(driver, ele, value)) {
					log(LogStatus.INFO,value+" dropdown element successfully selected",YesNo.Yes);
					
				}else {
					log(LogStatus.SKIP,value+" dropdown element is not visible",YesNo.Yes);
					BaseLib.sa.assertTrue(false, value+" dropdown element is not visible");
				}
			}else {
				log(LogStatus.SKIP,finalLabelName+" dropdown is not visible",YesNo.Yes);
				BaseLib.sa.assertTrue(false,finalLabelName+" dropdown is not visible");
			}
		}
		else {
			if (sendKeys(driver, ele, value, finalLabelName, action.SCROLLANDBOOLEAN)) {
				
			}else {
				log(LogStatus.SKIP,finalLabelName+" is not visible",YesNo.Yes);
				BaseLib.sa.assertTrue(false, finalLabelName+" is not visible");
			}
		}

	}
	if (click(driver, getNavigationTabSaveBtn(projectName, 10), "save", action.SCROLLANDBOOLEAN)) {
		
	}else {
		log(LogStatus.SKIP,"save button is not clickable",YesNo.Yes);
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
public WebElement getItemInList(String projectName,String itemName,action action,int timeOut) {
	String xpath = "//*[@title='"+itemName+"']//strong[text()='"+itemName.split(" ")[0]+"']";
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
		tabName = "Institutions";
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
	case FundraisingContacts:
		tabName = "Fundraising Contacts";
		break;
	case ReportsTab:
		tabName = "Reports";
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
				FindElement(driver, "//a[contains(@href,'lightning') and contains(@title,'" + tabName + "')]/span/..",
						tabName, action.SCROLLANDBOOLEAN,30),
				"visibility", 30, tabName);
		if (ele != null) {
			appLog.info("Tab Found");
			ThreadSleep(5000);
			if (clickUsingJavaScript(driver, ele, tabName+" :Tab")) {
				CommonLib.log(LogStatus.INFO, "Tab found", YesNo.No);
				appLog.info("Clicked on Tab : "+tabName);
				flag = true;
			} else {
				appLog.error("Not Able to Click on Tab : "+tabName);
			}

		} else {
			CommonLib.log(LogStatus.INFO, "Going to found tab after clicking on More Icon", YesNo.No);
			if (click(driver, getMoreTabIcon(environment, mode, 10), "More Icon", action.SCROLLANDBOOLEAN)) {
				ele = isDisplayed(driver,
						FindElement(driver,
								"//a[contains(@href,'lightning')]/span[@class='slds-truncate']/span[contains(text(),'"
										+ tabName + "')]",
								tabName, action.SCROLLANDBOOLEAN, 10),
						"visibility", 10, tabName);
				if (ele!=null) {
					if (clickUsingJavaScript(driver, ele, tabName+" :Tab")) {
						appLog.info("Clicked on Tab on More Icon: "+tabName);
						CommonLib.log(LogStatus.INFO, "Tab found on More Icon", YesNo.No);
						flag = true;
					}	
				}
				
			} else {
				appLog.error("Not Able to Clicked on Tab on More Icon: "+tabName);
			}

		}
	}
	
	if (TabName.NavatarSetup.toString().equalsIgnoreCase(TabName.toString())) {
		NavatarSetupPageBusinessLayer np = new NavatarSetupPageBusinessLayer(driver);
		
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
		switchToFrame(driver, 10, np.getnavatarSetUpTabFrame_Lighting(environment, 10));
	}
		if(FindElement(driver, "(//p[contains(text(),'Deal Creation')])[1]", "Deal Creation", action.BOOLEAN, 60)!=null){
			
			appLog.info("Landing Page Verified : " + "Deal Creation");
			
			
			flag = true;
			
		}else{
			appLog.error("Landing Page Not Verified : Deal Creation");
			flag = false;
		}
		switchToDefaultContent(driver);
	}
	return flag;
}

public boolean selectValueFromLookUpWindow(String searchText) {
	String parentWindow=null;
	WebElement ele=null;
	parentWindow=switchOnWindow(driver);
	if(parentWindow!=null) {
		switchToFrame(driver, 20, getLookUpSearchFrame(10));
		if(sendKeys(driver, getLookUpSearchTextBox(30), searchText, "search text box", action.SCROLLANDBOOLEAN)) {
			if(click(driver, getLookUpSearchGoBtn(20), "go button", action.SCROLLANDBOOLEAN)) {
				switchToDefaultContent(driver);
				switchToFrame(driver, 20, getLookUpResultFrame(10));
				ele=isDisplayed(driver, FindElement(driver, "//a[text()='"+searchText+"']",searchText+" text value", action.SCROLLANDBOOLEAN, 20),"visibility", 20,searchText+" text value");
				if(ele!=null) {
					if(!click(driver, ele, searchText+" text value", action.SCROLLANDBOOLEAN)) {
						appLog.info("clicked on "+searchText+" in lookup pop up");
					}
					driver.switchTo().window(parentWindow);
					return true;
				}else {
					appLog.error(searchText+" is not visible in look up popup so cannot select it");
					driver.close();
					driver.switchTo().window(parentWindow);
				}
			}else {
				appLog.error("Not able to click on go button so cannot select "+searchText);
				driver.close();
				driver.switchTo().window(parentWindow);
			}
		}else {
			appLog.error("Not able to pass value in search text box : "+searchText+" so cannot select value "+searchText+" from look up");
			driver.close();
			driver.switchTo().window(parentWindow);
		}
	}else {
		appLog.error("No new window is open so cannot select value "+searchText+" from look up");
	}
	return false;
}

/**
 * @param projectName
 * @param TabName
 * @return true if Tab is already selected
 */
public boolean getSelectedTab(String projectName,String TabName) {
	String tabName = null;
	boolean flag = false;
	WebElement ele;
	if (TabName.contains("Entit")) {
		tabName ="Entities";
	}else {
		tabName = TabName;
	}
	System.err.println("Passed switch statement");
	if (tabName!=null) {
		ele = FindElement(driver, "//a[@title='"+tabName+"']/parent::*[@class='navItem slds-context-bar__item slds-shrink-none slds-is-active']",tabName, action.SCROLLANDBOOLEAN,30);
		ele = isDisplayed(driver,ele,"visibility", 30, tabName);
		if (ele != null) {
			appLog.info("Tab is Already Selected : "+tabName);
			flag=true;
		}
	}
	return flag;
}

/**
 * @author Azhar Alam
 * @param projectName
 * @param alreadyCreated
 * @param isClick TODO
 * @param timeout
 * @param tabName
 * @return true if able to click on particular item on Particular tab
 */
public boolean CheckAlreadyCreatedItem(String projectName,String alreadyCreated, boolean isClick, int timeout) {
	boolean flag=false;
	String xpath="";
	String viewList = null;
	viewList = "Automation All";
	WebElement ele, selectListView;
	ele = null;
	ThreadSleep(3000);
	refresh(driver);
	if (click(driver, getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
		ThreadSleep(3000);
		xpath="//div[@class='listContent']//li/a/span[text()='" + viewList + "']";
		selectListView = FindElement(driver, xpath,"Select List View : "+viewList, action.SCROLLANDBOOLEAN, 30);
		if (click(driver, selectListView, "select List View : "+viewList, action.SCROLLANDBOOLEAN)) {
			ThreadSleep(3000);
			ThreadSleep(5000);
		} else {
			appLog.error("Not able to enter value on Search Box");
		}
	} else {
		appLog.error("Not able to select on Select View List : "+viewList);
	}

	if (sendKeys(driver, getSearchIcon_Lighting(20), alreadyCreated+"\n", "Search Icon Text",action.SCROLLANDBOOLEAN)) {
		ThreadSleep(2000);
		xpath = "//table[@data-aura-class='uiVirtualDataTable']//tbody//tr//th//span//*[text()='"+ alreadyCreated + "']";
		ele=FindElement(driver, xpath,alreadyCreated, action.BOOLEAN, 30);
		if (ele!=null) {
			appLog.info("Item Found : " + alreadyCreated);
			flag=true;
		} else {
			appLog.error("Item not Found : " + alreadyCreated);
		}
	} else {
		appLog.error("Not able to click on Select List Icon");
	}
	return flag;
}



}