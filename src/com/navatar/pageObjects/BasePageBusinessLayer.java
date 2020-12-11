/**
 * 
 */
package com.navatar.pageObjects;

import org.apache.poi.hssf.view.brush.PendingPaintings;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.sikuli.script.App;
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
	if (projectName.equalsIgnoreCase(ProjectName.PE.toString()))
		xpath="//li[@title='"+related+"']//a";
	else
	xpath = "//li//a[@title='"+related+"' or text()='"+related+"']";
	ele = isDisplayed(driver, FindElement(driver, xpath, relatedTab.toString(), action.SCROLLANDBOOLEAN, timeOut)
			, "visiblity", 30, relatedTab.toString());
	if (ele!=null) {
	appLog.info("Element Found : "+related);	
	}else {
		appLog.error("Element Not Found : "+related);	
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
	case TaskTab:
		tabName = "Tasks";
		break;
	case RecycleBinTab:
		tabName = "Recycle Bin";
		break;
	default:
		return null;
	}
	return tabName;
}


/**
 * @param projectName
 * @param tabName
 * @param labelName
 * @param labelValue
 * @return true if able to match field/Value on Page
 */
public boolean FieldValueVerificationOnAllPages(String projectName, TabName tabName,
		String labelName,String labelValue) {
	String xpath = "";
	WebElement ele = null;
		xpath = "//span[@class='test-id__field-label'][contains(text(),'" + labelName
				+ "')]/../following-sibling::div/span/*//a";
	
	ele = isDisplayed(driver,
			FindElement(driver, xpath, labelName + " label text in " + projectName, action.SCROLLANDBOOLEAN, 60),
			"Visibility", 30, labelName + " label text in " + projectName);
	if (ele != null) {
		String aa = ele.getText().trim();
		appLog.info("Lable Value is: "+aa);
		if(aa.contains(labelValue)) {
			appLog.info(labelValue + " Value is matched successfully.");
			return true;
			
		}else {
			appLog.info(labelValue + " Value is not matched. Expected: "+labelValue+" /t Actual : "+aa);
		}
	} else {
		appLog.error(labelName + " Value is not visible so cannot matched  label Value "+labelValue);
	}
	return false;

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
	String xpath="//span[text()='"+labelTextBox+"']/../following-sibling::input";
	if (pageName.equalsIgnoreCase(PageName.NewTaskPage.toString()) || pageName.equalsIgnoreCase(PageName.TaskPage.toString()))
		xpath="//label[text()='"+labelTextBox+"']/..//input";
	else if(pageName.equalsIgnoreCase(PageName.FundsPage.toString()))
		xpath="//label[text()='"+labelTextBox+"']/following-sibling::div//input";
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
public boolean clickOnAlreadyCreatedItem(String projectName, TabName tabName,
		String alreadyCreated, int timeout) {
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
	case NavatarSetup:
		viewList = "All";
		break;
	case RecycleBinTab:
		viewList = "Org Recycle Bin";
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
 * @author Azhar Alam
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
 * @param relatedTab
 * @param textLink
 * @param action
 * @param timeOut
 * @return link webelement on Sub Tab
 */
public WebElement getAllLinksOnSubTab(String projectName,PageName pageName,RelatedTab relatedTab,String textLink,action action,int timeOut){
	String xpath="";
	WebElement ele;
	if (PageName.Object2Page.toString().equals(pageName.toString()) && RelatedTab.Meetings.toString().equals(relatedTab.toString())) {
		xpath="//span[@id='Specify_the_recipients_to_include-headers']/../..//a[text()='"+textLink+"']";
	} else {
		xpath="//span[@id='Specify_the_recipients_to_include-headers']/../..//a[text()='"+textLink+"']";
	}
	ele=FindElementSingleQuotes(driver, xpath, "Link : "+textLink, action, timeOut);
	ele = isDisplayed(driver, ele, "Visibility", timeOut, "Link : "+textLink);
	return ele;
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
 * @param object
 * @param field
 * @return true if able to select item on Related Assocation Field on Task/Meeting PopUp
 */
public boolean selectElementInRelatedAssociationDropdown(String projectName, String object, String field) {
	boolean flag=true;
	String[] fields=field.split(",");
	if (click(driver, getrelatedAssociationsdropdownButton(projectName, 10), "RA dropdown", action.BOOLEAN)) {
		WebElement ele=isDisplayed(driver,  FindElement(driver, "//div[contains(@class,'slds-dropdown-trigger')]//div//ul//li//a[@title='"+object+"']"
				, "related assoc object", action.BOOLEAN, 5), "visibility", 10, "related assoc object");
		if (click(driver,ele, "related assoc object", action.BOOLEAN)) {
			for (String f:fields) {
				if (sendKeys(driver,getrelatedAssociationsTextbox(projectName, 20), f, 
						"related associations text box", action.BOOLEAN)) {
					ThreadSleep(3000);
					if (click(driver, FindElement(driver, "//li//span[text()='" +f +  "']/..",
							"name in dropdown", action.BOOLEAN, 10), "name in dropdown", action.BOOLEAN)) {

					}
					else {
						log(LogStatus.ERROR, "not able to click on contact name in name list", YesNo.Yes);
						flag=false;
					}
				}
				else {
					log(LogStatus.ERROR, "not able to enter field name in related associations textbox", YesNo.Yes);
					flag=false;
				}
			}
		}
		else {
			log(LogStatus.ERROR, "object name not found in related assoc dropdown", YesNo.Yes);
			flag=false;
		}
	}
	else {
		log(LogStatus.ERROR, "dropdown button not clickable", YesNo.Yes);
		flag=false;
	}
	return flag;
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
 * @param createdRecords
 * @return true if verify all records on Related Column PoPuP
 */
public boolean verifyAllRecordsOnCreatedRelAssoc(String projectName, PageName pageName,String createdRecords) {
	String[] rec=createdRecords.split(",");
	boolean flag=true;
	for (String r:rec) {
		String xpath="//div[@id='RelatedAsspopupID']//a[text()='"+r+"']";

		if (click(driver, FindElement(driver, xpath, "record name", action.SCROLLANDBOOLEAN, 20)
				, "record name", action.SCROLLANDBOOLEAN)) {
			String parentID=switchOnWindow(driver);
			if (parentID!=null) {
				WebElement ele = getHeaderTextForPage(projectName,pageName, r, action.BOOLEAN, 10);
				if (ele!=null) {
					log(LogStatus.INFO,"Landing Page Verified for : "+r,YesNo.No);	
				} else {
					log(LogStatus.ERROR,"Landing Page Not Verified for : "+r,YesNo.Yes);
					flag=false;
				}
				driver.close();
				driver.switchTo().window(parentID);
				switchToFrame(driver, 30,getmeetingOrActivitiesFrame(projectName, 30));
			}
			else {
				log(LogStatus.ERROR, "could not find new window", YesNo.Yes);
				flag=false;
			}
		}
		else {
			log(LogStatus.ERROR, "record name is not clickable "+r, YesNo.Yes);
			flag=false;
		}
	}
	return flag;
}

public WebElement getCrossButtonForAlreadySelectedItem(String projectName,PageName pageName,String label,boolean isMultipleAssociation,String name,action action,int timeOut) {
	String xpath="";
	WebElement ele;
	String fieldlabel=label.replace("_", " ");
	appLog.info(" >>>>>>>>>>>>>>>>   label:"+label);
	if (label.equalsIgnoreCase(PageLabel.Name.toString()))
		isMultipleAssociation=true;
	if (PageLabel.Name.toString().equalsIgnoreCase(label) && PageName.TaskPage.toString().equalsIgnoreCase(pageName.toString()) && isMultipleAssociation) {
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
 * @param PlusNewButton
 * @param label
 * @param action
 * @param timeOut
 * @return new Button webelement on TaskPopup
 */
public WebElement getNewButtonElementFromTask(String projectName,PageName pageName,PlusNewButton PlusNewButton,String label,action action,int timeOut) {
	String xpath="";
	label=label.replace("_", " ");
	String newButton = getNewButtonFromTask(projectName, PlusNewButton);
	xpath ="//label[text()='"+label+"']/..//div[@class='slds-lookup__item-action--label cursorPointer newWindowCss']//span[@title='"+newButton+"']";
	WebElement ele = FindElement(driver, xpath, newButton+" for "+label, action, timeOut);
	return ele;
	
}


/**
 * @param projectName
 * @param PlusNewButton
 * @return String for New Button
 */
public String getNewButtonFromTask(String projectName,PlusNewButton PlusNewButton) {
	String newButton = null;
	switch (PlusNewButton) {
	case ContactNewButton:
		newButton = "New "+tabObj2;
		break;
	case EntityOrAccountNewButton:
		newButton= "New "+tabObj1;
		break;
	case FundOrDealNewButton:
		newButton="New "+tabObj3; 
		break;
	case TestCustomObjectNewButton:
		newButton = "New "+tabCustomObj;
		break;
	default:
		return null;
	}
	return newButton;
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
			else
			xpath="//div[@role='menu']//span[text()='"+actionDropDown+"']";
			 ThreadSleep(3000);
			 ele=FindElement(driver, xpath, "show more action down arrow : "+actionDropDown, action.BOOLEAN, 10);
			// mouseOverOperation(driver, ele);
			 if(clickUsingJavaScript(driver, ele, "show more action on "+pageName.toString(), action.BOOLEAN)) {
					log(LogStatus.INFO, "clicked on "+actionDropDown+" link", YesNo.No);
					flag=true;
			 }else {
					log(LogStatus.ERROR, "Not able to click on "+actionDropDown+" link", YesNo.Yes);
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
 * @param labelFieldTextBox
 * @param name
 * @param inst
 * @param action
 * @param timeOut
 * @return  ContactNameWithInst Or Related Association Name On Task WebElement
 */
public WebElement getContactNameWithInstOrRelatedAssociationNameOnTask(String projectName,PageName pageName,String labelFieldTextBox,String name,String inst,action action,int timeOut) {
	
	WebElement ele=null;
	String xpath="";
	labelFieldTextBox = labelFieldTextBox.replace("_", " ");
	if (PageName.TaskPage.toString().equals(pageName.toString()) || PageName.NewEventPopUp.toString().equals(pageName.toString())) {
		xpath = "//span[text()='"+labelFieldTextBox+"']/..//following-sibling::div//input//following-sibling::div//ul//li/a//div[@title='"+name+"']";
	} else {
		//Need to write ofr copy if same
		xpath="//label[text()='"+labelFieldTextBox+"']/..//following-sibling::ul//li//span[@title='"+inst+"']/preceding-sibling::span[@title='"+name+"']";
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
 * @param tabName
 * @param label
 * @param action
 * @param timeOut
 * @return get Related Assciation Default Selected DropDown WebElement
 */
public WebElement getRelatedAssciationDefaultSelectedDropDown(String projectName,TabName tabName,String label,action action,int timeOut) {
	
	WebElement ele;
	String xpath="";
	label=label.replace("_", " ");
	String tab= getTabName(projectName, tabName);
	xpath = "//label[text()='"+label+"']/..//*[@title='"+tab+"']";
	ele = FindElement(driver, xpath, label+" : "+tab, action, timeOut);
	scrollDownThroughWebelement(driver, ele, "");
	return isDisplayed(driver, ele, "Visibility", timeOut, label+" : "+tab);
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
 * @param projectName
 * @param pageName
 * @param label
 * @param isMultipleAssociation
 * @param name
 * @param action
 * @param timeOut
 * @return true if click on cross Button for already selected item on Meeting/Task PoPuP 
 */
public boolean ClickOnCrossButtonForAlreadySelectedItem(String projectName,PageName pageName,String label,boolean isMultipleAssociation,String name,action action,int timeOut) {
	
	WebElement ele = getCrossButtonForAlreadySelectedItem(projectName, pageName, label, isMultipleAssociation, name, action, timeOut);
	boolean flag = clickUsingJavaScript(driver, ele,"Cross Button against : "+name+" For Label : "+label, action);
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
	
	String label;
	String value;
	boolean flag=false;
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
 * @author Azhar Alam
 * @param projectName
 * @param pageName
 * @param relatedTab
 * @param action
 * @param timeOut
 * @return refresh webElement
 */
public WebElement getRefreshIconOnSearchResultForMeeting(String projectName,PageName pageName,RelatedTab relatedTab,action action,int timeOut) {

WebElement ele;
String xpath="";

xpath ="//*[text()='Search Results']/..//a[@title='Refresh']";
ele = FindElement(driver, xpath, "Refresh", action, timeOut);
ele = isDisplayed(driver, ele, "Visibility", timeOut, "Refresh Button");
return ele;

	
	
}

/**
 * @author Azhar Alam
 * @param projectName
 * @param pageName
 * @param relatedTab
 * @param labelWithComma
 * @param timeOut
 * @description verify Error Message on Task POpUp
 */
public void  pageErrorOnTaskPopUp(String projectName,PageName pageName,RelatedTab relatedTab,String labelWithComma,int timeOut) {
	WebElement ele=null;
	String xpath="";
	String actualErrorMsg="";
	String ExpectedMsg="";
	
	xpath ="//div[@class='pageLevelErrors']//span[text()='"+ReviewTheErrorMsg+"']"	;
	ele = FindElement(driver, xpath, ReviewTheErrorMsg, action.SCROLLANDBOOLEAN, timeOut);
	scrollDownThroughWebelement(driver, ele, "");
	if (ele!=null) {
		log(LogStatus.INFO, ReviewTheErrorMsg+" : Msg Verified", YesNo.No);
	} else {
		BaseLib.sa.assertTrue(false, ReviewTheErrorMsg+" : Msg not Verified");
	}
	
	
	xpath="//div[@class='pageLevelErrors']//ul/li[contains(text(),'"+RequiredFieldMustBeCompletedMsg+"')]";
	ele = FindElement(driver, xpath, RequiredFieldMustBeCompletedMsg, action.SCROLLANDBOOLEAN, timeOut);
	
	if (ele!=null) {
		log(LogStatus.INFO, RequiredFieldMustBeCompletedMsg+" : Msg Verified", YesNo.No);
		actualErrorMsg=ele.getText().trim();
		ExpectedMsg = RequiredFieldMustBeCompletedMsg;
		System.err.println(">>>>>>>>>>>>>>>>>..  "+ele.getText().trim());
		
		if (actualErrorMsg.contains(ExpectedMsg)) {
			log(LogStatus.INFO, ExpectedMsg+" : Msg Verified", YesNo.No);	
		} else {
			BaseLib.sa.assertTrue(false, "Actual:    "+actualErrorMsg+"\nExpected: "+ExpectedMsg+" : Msg not Verified");
		}
	} else {
		BaseLib.sa.assertTrue(false, RequiredFieldMustBeCompletedMsg+" : Msg not Verified");
	}

	
	String[] labels = labelWithComma.split(",");
	String a;
	for (String s : labels) {
		appLog.info("Label : "+s);
		s=s.trim();
		a=s.replace("-", " ");
		if (PageLabel.Assigned_To.toString().equals(s)) {
			xpath="//div[@id='Error@OwnerId']";
		} else {
			xpath = "Error@"+a;
			xpath="//div[@id='"+xpath+"']";
		}
	
		xpath = xpath+"[text()='"+CompleteThisField+"']";
		ele = FindElement(driver, xpath, CompleteThisField, action.SCROLLANDBOOLEAN, timeOut);
		if (ele!=null) {
			log(LogStatus.INFO, CompleteThisField+" : Msg Verified For Label : "+s, YesNo.No);
		} else {
			BaseLib.sa.assertTrue(false, CompleteThisField+" : Msg not Verified For Label : "+s);
		}
		a=a.replace("_"," ");
		if (actualErrorMsg.contains(a)) {
			log(LogStatus.INFO, "successfully verified "+a + " on top message", YesNo.No);
		}
		else {
			log(LogStatus.ERROR, "could not verify presence of "+a+" on top error message", YesNo.Yes);
			BaseLib.sa.assertTrue(false,"could not verify presence of "+a+" on top error message");
		}
	}
	
}

/**
 * @author Akul Bhutani
 * @param projectName
 * @param columns
 * @return true if verify all column at Task
 */
public boolean verifyAllColumnsInTask(String projectName, String columns) {
	List<WebElement> a=FindElements(driver, "//span[contains(@id,'Specify_the_recipients_to_include-header-') and contains(@id,'box')]/following-sibling::span[2]"
			, "column names");
	
	List<String> s=compareMultipleList(driver, columns, a);
	if (s.isEmpty())
		return true;
	else {
		for (String i:s) {
			log(LogStatus.ERROR, i+" column is not found", YesNo.Yes);
		}
		return false;
	}
}

/**
 * @author Akul Bhutani
 * @param project
 * @param pageName
 * @param column
 * @param timeOut
 * @return true if selected column verified
 */
public boolean verifySelectedColumnInColumnsToDisplayGrid(String project,PageName pageName, String column, int timeOut) {
	WebElement ele=FindElement(driver, "//span[@id='sellistActivitySearchpage']/span/span/span//span[text()='"+column+"']", column+" in available grid", action.SCROLLANDBOOLEAN, timeOut);
	if (ele!=null) {
	//ele = isDisplayed(driver, ele, "visibility", timeOut, "column in available grid");
	scrollDownThroughWebelement(driver, ele, column);
	Actions actions = new Actions(driver);
	actions.clickAndHold(ele).build().perform();
	ThreadSleep(3000);
	actions.release().build().perform();
	
	if (click(driver, ele, column + " in selected grid",action.SCROLLANDBOOLEAN)) {
		log(LogStatus.INFO, "clicked on "+column, YesNo.No);
			return true;
		}
	else {
		log(LogStatus.INFO, "could not click on "+column, YesNo.No);
		
	}
	}
	else {
		log(LogStatus.INFO, "not found "+column, YesNo.No);
	}
	return false;

}

public boolean clickOnSelectedColumn(String project,PageName pageName, String column, int timeOut ) {
	WebElement ele=FindElement(driver, "//span[@id='sellistActivitySearchpage']//span[text()='"+column+"']", column+" in available grid", action.SCROLLANDBOOLEAN, timeOut);
	if (ele!=null) {
		scrollDownThroughWebelement(driver, ele, column);
		ele = isDisplayed(driver, ele, "visibility", timeOut, "column in available grid");
		if (click(driver, ele, column + " in selected grid",action.SCROLLANDBOOLEAN)) {
		}
		return true;
	}
	return false;
}


/**
 * @author Azhar Alam
 * @param project
 * @param pageName
 * @param column
 * @param timeOut
 * @return true if all available columnn verify
 */
public boolean verifyAvailableColumnInColumnsToDisplayGrid(String project,PageName pageName, String column, int timeOut) {
	
	WebElement ele = isDisplayed(driver,FindElement(driver, "//span[@id='avllistActivitySearchpage']//span[text()='"+column+"']", column+" in available grid", action.SCROLLANDBOOLEAN, timeOut/2) , "visibility", timeOut/2, "column in available grid");
	if (ele!=null) {
		Actions actions = new Actions(driver);
		actions.clickAndHold(ele).build().perform();
		ThreadSleep(3000);
		actions.release().build().perform();
		if (click(driver, ele, column + " in available grid",action.BOOLEAN)) {
			return true;
		}
	}
	return false;

}

/**
 * @author Azhar Alam
 * @param projectName
 * @param field
 * @param timeOut
 * @return Advance Filter DropDown WebElement
 */
public WebElement getAdvancedFilteDropdowns(String projectName, String field, int timeOut) {
	
	String xpath="";
	if (field.equalsIgnoreCase(PageLabel.Subject.toString()))
		xpath="//label[text()='"+field+"']/..//following-sibling::td//input";
	else xpath="//label[text()='"+field+"']/..//following-sibling::td//select";
	WebElement ele=isDisplayed(driver, FindElement(driver, xpath, field+" dropdown", action.SCROLLANDBOOLEAN, 10), 
			"visibility", 10, field+" dropdown");
	return ele;
}

/**
 * @author Azhar Alam
 * @param projectName
 * @param field
 * @param timeOut
 * @return Error Message WebElement for Field
 */
public WebElement returnErrorMessageBelowField(String projectName,ActivityRelatedLabel field, int timeOut) {
	String f=field.toString(),xpath="";
	if (field==ActivityRelatedLabel.Assigned_To || field==ActivityRelatedLabel.Subject)
		xpath="//label[text()='"+f+"']/../../following-sibling::div[contains(@id,'Error')]";
	else if (field==ActivityRelatedLabel.Status || field==ActivityRelatedLabel.Priority)
		xpath="//label[text()='"+f+"']/../../../following-sibling::div[contains(@id,'Error')]";
	WebElement ele=isDisplayed(driver, FindElement(driver, xpath, field+" error label", action.SCROLLANDBOOLEAN, 10), 
			"visibility", 10, field+" error label");
	return ele;
}



/**
 * @author Azhar Alam
 * @param projectName
 * @param pageName
 * @param relatedTab
 * @param date
 * @param subjectName
 * @param otherGridValue
 * @param action
 * @param timeOut
 * @description verify all fields on Related Tab
 */
public void verifyingRelatedTabData2(String projectName,PageName pageName,RelatedTab relatedTab,String date, String subjectName,List<String> otherGridValue,action action,int timeOut) {
	//	String[] dateArr=date.split(",");
	ThreadSleep(5000);
//	scrollDownThroughWebelement(driver, getsearchTextboxActivities(projectName, timeOut),"search textbox");
	WebElement ele;
	boolean flag=false;;
	
	String parentXpath="//span[@id='Specify_the_recipients_to_include-rows']";
	String dateXpath="//span/a[text()='"+subjectName+"']/../preceding-sibling::span)[2]";
	ele = FindElement(driver, "("+parentXpath+dateXpath, "Date", action, timeOut);
	String value;
	if (ele!=null) {
		value=ele.getText().trim();
		appLog.info(value);
		if (!date.isEmpty() && !date.equals("")) {
			String[] dates = date.split("/");
			String[] values = value.split("/");
			appLog.info("Excel Date : "+date);
			appLog.info("Page Date : "+value);
			if (dates[0].contains(values[0]) && dates[1].contains(values[1]) && dates[2].contains(values[2])) {
				log(LogStatus.INFO, "Value  matched "+date+" For Grid Data : "+subjectName, YesNo.No);
			} else {
				BaseLib.sa.assertTrue(false, "Value not matched Actual: "+value+" Expected : "+date+" For Grid Data : "+subjectName);
			}
			
		}else if (date.equals(value)) {
			log(LogStatus.INFO, "Value  matched "+date+" For Grid Data : "+subjectName, YesNo.No);
		} else {
			BaseLib.sa.assertTrue(false, "Value not matched Actual: "+value+" Expected : "+date+" For Grid Data : "+subjectName);
		}
		
		if (otherGridValue!=null) {
			String otherXpath="//span/a[text()='"+subjectName+"']/../following-sibling::span";
			List<WebElement> eleList = FindElements(driver, parentXpath+otherXpath, "Other Grid Value After Subject");

			if (!eleList.isEmpty()) {
				List<String> listValue = new LinkedList<String>();
				for (int i=0;i<eleList.size()-1;i++) {
					listValue.add(eleList.get(i).getText().trim());
				}
				
				log(LogStatus.INFO, "Actual   Data :   "+listValue+" For Grid Data : "+subjectName, YesNo.No);	
				log(LogStatus.INFO, "Expected Data :   "+otherGridValue+" For Grid Data : "+subjectName, YesNo.No);
				if (listValue.equals(otherGridValue)) {
					log(LogStatus.INFO, "Value matched "+listValue+" For Grid Data : "+subjectName, YesNo.No);	
				} else {
					BaseLib.sa.assertTrue(false, "Value not matched \nActual:      "+listValue+"\nExpected :   "+otherGridValue+" For Grid Data : "+subjectName);
				}
			} else {
				log(LogStatus.ERROR, "list is not found on related tab" +relatedTab, YesNo.Yes);
			}
		}
		
		
	}else{
		BaseLib.sa.assertTrue(false, "Grid Data Related to : "+subjectName+"not Found");	
	}
	
}


/**
 * @param projectName
 * @param relatedTab
 * @param headers
 * @return true if able to verify Header for Related Tab
 */
public boolean verifyHeadersForRelatedTab(String projectName,RelatedTab relatedTab, List<String> headers) {
	boolean flag=false;
	List<WebElement> eleList=FindElements(driver, "//span[contains(@id,'Specify_the_recipients_to_include-header-') and contains(@id,'box')]/following-sibling::span[2]"
			, "column names");
	
	if (!eleList.isEmpty()) {
		List<String> listValue = new LinkedList<String>();
		for (int i=0;i<eleList.size();i++) {
			listValue.add(eleList.get(i).getText().trim());
		}
		log(LogStatus.INFO, "Actual   Header Data :   "+listValue, YesNo.No);	
		log(LogStatus.INFO, "Expected Header Data :   "+headers, YesNo.No);
		if (listValue.equals(headers)) {
			log(LogStatus.INFO, "Headers matched "+listValue, YesNo.No);	
			flag=true;
		} else {
			log(LogStatus.ERROR, "Headers not matched Actual:   "+listValue+"\n Expected : "+headers, YesNo.Yes);
			
		}
	} else {
		log(LogStatus.ERROR, "Headers List is Empty", YesNo.Yes);
	}
	return flag;
}



/**
 * @param projectName
 * @param timeOut
 * @return list of webelement for Header
 */
public List<WebElement> getHeadersInTaskGrid(String projectName, int timeOut) {
	return FindElements(driver, "//span[contains(@id,'Specify_the_recipients_to_include-header') and contains(@id,'box')]/span[3]", "headers");
}

/**
 * @param projectName
 * @param timeOut
 * @returnn list of webelement for Field
 */
public List<WebElement> getSelectedFieldsList(String projectName, int timeOut) {
	return FindElements(driver, "//span[@id='sellistActivitySearchpage']//span[contains(@id,'box-text')]", "selected columns");
}


/**
 * @author Azhar Alam
 * @param projectName
 * @param pageName
 * @param relatedtab
 * @param fieldValue
 * @param forFilterRowNumber
 * @return true if able to set Field value
 */
public boolean setFieldValue(String projectName,PageName pageName,RelatedTab relatedtab,String fieldValue, int forFilterRowNumber) {
		boolean flag=false;	
		String Xpath = "//input[@id='a" + forFilterRowNumber + "aa']";
		WebElement ele = FindElement(driver, Xpath, "Field value Drop Down", action.BOOLEAN, 5);
		scrollDownThroughWebelement(driver, ele, "Field value drop down");
		ele=isDisplayed(driver,FindElement(driver,Xpath , "add Prospect Field Auto Complete TextBox", action.BOOLEAN,5), "Visibility", 5, "add Prospect Field Auto Complete TextBox");
		if(sendKeys(driver,ele, fieldValue, "field text box", action.BOOLEAN)) {
			ele=null;
			ThreadSleep(2000);
			Xpath="//a[text()='"+fieldValue+"']";
			ele=FindElement(driver, Xpath, "field auto complete text", action.BOOLEAN,5);
			if(ele!=null) {
				ThreadSleep(2000);
				if(click(driver, ele, fieldValue+" text", action.BOOLEAN)) {
					appLog.info("clicked on field name "+fieldValue+" text box");
					
				}else {
					appLog.error("Not able to click on field "+fieldValue+" so cannot apply filter");
					return false;
				}
			}else {
				appLog.error(fieldValue+" is not visible in field auto complete text box so cannot apply filter.");
				return false;
			}
		}else {
			appLog.error("Not able to pass value in field auto complete text box : "+fieldValue+" so cannot apply filter");
			return false;
		}
		
		
		return true;
		
}


/**
 * @author Azhar Alam
 * @param projectName
 * @param pageName
 * @param relatedtab
 * @param operator
 * @param forFilterRowNumber
 * @return true if able to set operator value
 */
public boolean setOperatorValue(String projectName,PageName pageName,RelatedTab relatedtab,String operator, int forFilterRowNumber) {
		String xpath = "//select[@id='opt" + forFilterRowNumber + "']";
		WebElement ele = FindElement(driver, xpath, "Operator Drop Down", action.BOOLEAN, 5);
		scrollDownThroughWebelement(driver, ele, "Operator Drop Down");
		boolean flag=false;
		if (ele != null) {
			if (selectVisibleTextFromDropDown(driver, ele, "Field Value Drop Down", operator)) {
				appLog.info("Operator value " + operator + " is present in the drop down");
				flag= true;
			} else {
				appLog.info("Operator value " + operator + " is not present in the drop down");
			
			}
		} else {
			appLog.info("Operator Drop Down number " + forFilterRowNumber + " is not present");
			
		}
		return flag;
	}



/**
 * @author Azhar Alam
 * @param projectName
 * @param pageName
 * @param relatedtab
 * @param valueType
 * @param value
 * @param forFilterRowNumber
 * @return true if able to set criteria value
 */
public boolean setCriterionValue(String projectName,PageName pageName,RelatedTab relatedtab,String valueType, String value, int forFilterRowNumber) {
		String xpath = "//input[@id='criteriatextbox" + forFilterRowNumber + "']";
		WebElement ele;
		boolean flag=false;
		ele = FindElement(driver, xpath, "Value Box For Row : "+forFilterRowNumber, action.SCROLLANDBOOLEAN, 5);
		if (ele != null) {
			
				if (sendKeys(driver, ele, value, "Criterion Box", action.SCROLLANDBOOLEAN)) {
					appLog.info("Entered Value on Criterion box For Row " + forFilterRowNumber);
					flag=true;
				} else {
					appLog.info("Not Able to Enter Value on Criterion box For Row " + forFilterRowNumber);
				}
			
			
		} else {
			appLog.info("Criterion box For Row " + forFilterRowNumber + " is not present");

		}
		return flag;
	}

/**
 * @author Akul Bhutani
 * @param projectName
 * @param timOut
 * @return Add Row Link webElement
 */
public WebElement getAddRowLink(String projectName,int timOut) {
	String xpath="//a[text()='Add Row']";
	WebElement ele=FindElement(driver, xpath, "Add row button", action.BOOLEAN,10);
	return isDisplayed(driver,ele, "Visibility", 10, "Add row button");
}

/**
 * @author Akul Bhutani
 * @param projectName
 * @param pageName
 * @param subject
 * @return view webElement for particular subject on Related Tab
 */
public WebElement viewLinkWRTsubjectRelatedTab(String projectName, PageName pageName, String subject) {
	String xpath="//span[@id='Specify_the_recipients_to_include-rows']//a[text()='"+subject+"']/../following-sibling::span//a[text()='View']";
	return isDisplayed(driver, FindElement(driver, xpath, "view link", action.SCROLLANDBOOLEAN,10), "visibility", 10, "view link");
}

/**
 * @author Akul Bhutani
 * @param projectName
 * @param pageName
 * @param createdRecords
 * @return true if able to verify all record on Contact Page
 */
public boolean verifyAllRecordsOnCreatedContactName(String projectName, PageName pageName,String createdRecords) {
	String[] rec=createdRecords.split(",");
	boolean flag=true;
	for (String r:rec) {
		String xpath="//div[@id='conDIVid']//a[text()='"+r+"']";

		if (click(driver, FindElement(driver, xpath, "Contact name : "+r, action.SCROLLANDBOOLEAN, 20)
				, "record name", action.SCROLLANDBOOLEAN)) {
			String parentID=switchOnWindow(driver);
			if (parentID!=null) {
				WebElement ele = getHeaderTextForPage(projectName,pageName, r, action.BOOLEAN, 10);
				if (ele!=null) {
					log(LogStatus.INFO,"Landing Page Verified for : "+r,YesNo.No);	
				} else {
					log(LogStatus.ERROR,"Landing Page Not Verified for : "+r,YesNo.Yes);
					flag=false;
				}
				driver.close();
				driver.switchTo().window(parentID);
				switchToFrame(driver, 30,getmeetingOrActivitiesFrame(projectName, 30));
			}
			else {
				log(LogStatus.ERROR, "could not find new window", YesNo.Yes);
				flag=false;
			}
		}
		else {
			log(LogStatus.ERROR, "Contact name is not clickable "+r, YesNo.Yes);
			flag=false;
		}
	}
	return flag;
}


/**
 * @param projectName
 * @param pageName
 * @param relatedTab
 * @param subjectNaame
 * @param cName
 * @param count
 * @param action
 * @param timeOut
 * @return WebElement Link for Sub Tab on Tab
 */
public WebElement getLinkOnSubTab(String projectName,PageName pageName,RelatedTab relatedTab,String subjectNaame,String cName,String count,action action,int timeOut){
	String xpath="";
	WebElement ele;
	
	 	
		xpath="//span[@id='Specify_the_recipients_to_include-rows']//span/a[contains(text(),'"+subjectNaame+"')]/../following-sibling::span//a[text()='"+cName+"']";
		if (count!=null) {
		xpath = xpath +"/following-sibling::a[text()='"+count+"']";
		}
	
	ele=FindElement(driver, xpath, "Link : For "+subjectNaame+" with "+cName, action, timeOut);
	//ele = isDisplayed(driver, ele, "Visibility", timeOut, "Link : For "+subjectNaame+" with "+cName);
	return ele;
}




/**
 * @param projectName
 * @param pageName
 * @return  true if able to click o Show more action Icon
 */
public boolean clickOnShowMoreDropdownOnly(String projectName,PageName pageName) {
	String xpath = "";int i =1;
	WebElement ele=null;
	boolean flag = true;
	refresh(driver);
	ThreadSleep(2000);
	xpath="(//span[contains(text(),'more actions')])[1]/..";
	if (PageName.TestCustomObjectPage.equals(pageName) || PageName.Object3Page.equals(pageName)) {
		xpath="(//span[contains(text(),'more actions')])[1]/..";
	}
	else if(PageName.TaskPage.equals(pageName)) {
		xpath="//a[@title='Show one more action']";
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
 * @param environment
 * @param mode
 * @param TabName
 * @return true/false
 * @description this method is used to click on tab either classic or lightning
 */
public boolean clickOnTab(String projectName,String environment, String mode, TabName TabName) {
	String tabName = null;
	String suffix = " Tab";
	boolean flag = false;
	WebElement ele;
	tabName=getTabName(projectName, TabName);
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
								"//a[contains(@href,'lightning')]/span[@class='sldstruncate']/span[contains(text(),'"
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
	
	
	return flag;
}

/**@author Akul Bhutani
 * @param projectName
 * @param timeOut
 * @return List of WebElement
 * @description this is used to return all elements present on date column on activities grid
 */
public List<WebElement> listOfDatesOnActivitiesRelatedTab(String projectName, int timeOut) {
	return FindElements(driver, "//span[contains(@id,'Specify_the_recipients_to_include-cell-0')]", "dates");
}


/**
 * @param projectName
 * @param pageName
 * @param reatedTab
 * @return SoftAssert
 */
public SoftAssert performSortingCheckOnRelatedTab(String projectName,PageName pageName,RelatedTab reatedTab) {

	SoftAssert sa = new SoftAssert();
	List<WebElement> eleValue = null;
	WebElement columnEle=null;
	String xpath="",xpath1="";

	System.err.println("**********inside no sorting*********");
	eleValue = getDateColumnValue();

	try {
		if (checkSorting(SortOrder.Decending, eleValue)) {
			appLog.info(" By Default Sorting order " + SortOrder.Decending.toString()+" Verified on Date Column");
		} else {
			appLog.error(" By Default Sorting order " + SortOrder.Decending.toString()+" Not Verified on Date Column");
			sa.assertTrue(false, " By Default Sorting order " + SortOrder.Decending.toString()+" Not Verified  on Date Column");
		}
	} catch (Exception e1) {
		appLog.error(" Exception On Default Sorting order " + SortOrder.Decending.toString()+" Not Verified on Date Column");
		sa.assertTrue(false, " Exception On Default Sorting order " + SortOrder.Decending.toString()+" Not Verified  on Date Column");

	}
	
	String[] columns = {"Date","Name","Status","Owner","Meeting Type","Activity"};
	
	for (int i = 0; i < columns.length; i++) {
		
		for (int j = 1; j <= 2; j++) {
		
			try {
				xpath = "//span[@id='Specify_the_recipients_to_include-headers']/span[contains(@id,'Specify_the_recipients_to_include-header-')]/span//span[text()='"+columns[i]+"']/..";
				columnEle = FindElement(driver, xpath, columns[i], action.BOOLEAN, 10);
				ThreadSleep(3000);
				Actions actions = new Actions(edriver);
				actions.moveToElement(columnEle).click(columnEle).perform();
				
				ThreadSleep(1000);
				System.err.println("*****inside All sorting**********");
				appLog.info("Going to Check Sorting for Column Value : "+columns[i]);
				ThreadSleep(1000);

		//		xpath1="//div[@title='New Task with Multiple Associations']/..";	
//				xpath1="//*[contains(text(),'Search Activities and Attachments')]/..";
//				WebElement searchWebEle = FindElement(driver, xpath1, columns[i], action.BOOLEAN, 10);
//				scrollDownThroughWebelement(driver, searchWebEle, "");
				appLog.info("Click on Column : " + columns[i]);
				if (i == 0) {
					eleValue = getDateColumnValue();
				} else if (i == 1) {
					eleValue = getNameColumnValue();
				} else if (i == 2) {
					eleValue = getStatusColumnValue();
				} else if(i==3) {
					eleValue = getOwnerColumnValue();
				}else if(i==4) {
					eleValue = getMeetingTypeColumnValue();
				}else if(i==5) {
					eleValue = getActivityColumnValue();
				}
				
				if (j==1) {
					
					if (checkSorting(SortOrder.Assecending, eleValue)) {
						appLog.info(" Sorting Verified on : " + columns[i] + " for " + SortOrder.Assecending.toString());
					} else {
						appLog.error(" Sorting Not Verified on : " + columns[i] + " for "+ SortOrder.Assecending.toString());
						sa.assertTrue(false, " Sorting Not Verified on : " + columns[i] + " for "+ SortOrder.Assecending.toString());
					}
					
				} else {


					if (checkSorting(SortOrder.Decending, eleValue)) {
						appLog.info(" Sorting Verified on : " + columns[i] + " for " + SortOrder.Decending.toString());
					} else {
						appLog.error(" Sorting Not Verified on : " + columns[i] + " for "+ SortOrder.Decending.toString());
						sa.assertTrue(false, " Sorting Not Verified on : " + columns[i] + " for "+ SortOrder.Decending.toString());
					}
					
				}
				ThreadSleep(2000);
			} catch (Exception e) {
				appLog.error(" Exception For : " + columns[i]);
				sa.assertTrue(false, " Exception For : " + columns[i]);
			}
		}
	
	}
	
	return sa;
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
	} else if(ProjectName.PE.toString().equalsIgnoreCase(projectName) && PageLabel.Account_Name.equals(pageLabel)){
		label="Legal Name";
	} else if(ProjectName.PEEdge.toString().equalsIgnoreCase(projectName) && PageLabel.Account_Name.equals(pageLabel)){
		label="Firm";
	} 
	xpath = "//span[text()='"+label+"']/../following-sibling::div//*[text()='"+labelValue+"']";
	
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



public WebElement getInfoImgIcon(String projectName,PageName pageName,RelatedTab relatedTab,String subject,String imgName){
	String xpath="";
	WebElement ele;
	imgName=imgName+"s";
	xpath="//span[@id='Specify_the_recipients_to_include-headers']/../..//a[text()='"+subject+"']/../following-sibling::span//img[@title='"+imgName+"']";
	
	ele=FindElementSingleQuotes(driver, xpath, "Image Icon For : "+subject, action.BOOLEAN, 5);
	ele = isDisplayed(driver, ele, "Visibility", 5, "Image Icon For : "+subject);
	return ele;
}


public WebElement getAnyElement(String value){
	String xpath="";
	WebElement ele;
	xpath="//*[contains(text(),'"+value+"') or contains(@title,'"+value+"')]";
	
	ele=FindElementSingleQuotes(driver, xpath, value, action.SCROLLANDBOOLEAN, 10);
	ele = isDisplayed(driver, ele, "Visibility", 5, value);
	return ele;
}


public WebElement getActivityTimeLineItem(String projectName,PageName pageName,ActivityTimeLineItem activityTimeLineItem,int timeOut){
	clickUsingJavaScript(driver, getactivityLineItemsDropdown(projectName, 10), "dropdown", action.BOOLEAN);

	String xpath="";
	//WebElement ele;
	String activity = activityTimeLineItem.toString().replace("_", " ");
	if (projectName.equalsIgnoreCase(ProjectName.PE.toString()))
		xpath="//div[@id='completeDiv' and @class='cActivityTimeline']/..//*[text()='"+activity+"']";
	else
		xpath="//div[@id='completeDiv' and @class='cActivityTimeline']/..//*[text()='"+activity+"']";
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

public boolean clickOnLoadMorePastActivitiesMoreDropdown(String projectName, PageName pageName, String noa,int timeOut) {
	if (click(driver, dropdownBtnMorePastAct(projectName, timeOut), "dropdown button", action.SCROLLANDBOOLEAN)) {
		String xpath="";
		String n=noa.toString().replace("_", " ");
		if (noa.contains("View"))
		xpath="//li[@title='Show all past activities in new tab']//a";
		else
			xpath = "//li[@title='"+noa+" Activities']//a";
		WebElement ele=FindElement(driver, xpath, "button to "+noa+" activities", action.SCROLLANDBOOLEAN, 10);
		ele=isDisplayed(driver, ele, "visibility",5, "button to load all activities");
		if (click(driver, ele, "button to load activities", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "successfully clicked on "+noa+" activities", YesNo.No);
			return true;
		}
		else {
			log(LogStatus.ERROR, "could not click on "+noa+" activities", YesNo.Yes);
		}
	}else {
		log(LogStatus.ERROR, "could not click on dropdown button, so cannot verify activities", YesNo.Yes);
	}
	return false;
}

public WebElement getActivityTimeLineItem2(String projectName,PageName pageName,ActivityTimeLineItem activityTimeLineItem,int timeOut){
	
	String xpath="";
	WebElement ele;
	String activity = activityTimeLineItem.toString().replace("_", " ");
	if (projectName.equalsIgnoreCase(ProjectName.PE.toString()))
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



public void verifyActivityAtNextStep(String projectName,PageName pageName,String createdItemValue,String subject,String assignedToMsg,DueDate dueDate,boolean isMeetingType,String meetingTypeValue, boolean isDescription, String descriptionValue, int timeOut){

	WebElement ele;

	String actualValue="";

	String nextStepsXpath = "//div[@class='Next_steps']/following-sibling::div[@class='activity-timeline'][1]";
	String subjectXpath = nextStepsXpath+"//*[@title='"+subject+"']";

	String dateXpath = subjectXpath+"/..//following-sibling::*//*[text()='"+dueDate+"']";
	ele = FindElement(driver, dateXpath, dueDate.toString(), action.SCROLLANDBOOLEAN, 20);

	// Due DATE

	if (ele!=null) {
		log(LogStatus.INFO,dueDate+" verified for subject : "+subject+" For item : "+createdItemValue,YesNo.No);
	}else {
		sa.assertTrue(false,dueDate+" not verified for subject : "+subject+" For item : "+createdItemValue);
		log(LogStatus.SKIP,dueDate+" not verified for subject : "+subject+" For item : "+createdItemValue,YesNo.Yes);
	}


		// Assigned To 
		String assignedToxpath = subjectXpath+"/../..//following-sibling::p";
		ele = FindElement(driver, assignedToxpath, "Asigned To ", action.SCROLLANDBOOLEAN, 5);
		if (ele!=null) {
			log(LogStatus.INFO,"Asigned To verified for subject : "+subject+" For item : "+createdItemValue,YesNo.No);	

			System.err.println(">>>>>>>>>>>>   "+ele.getText().trim());
			actualValue=ele.getText().trim().replace("\n", " ");
			System.err.println(">>>>>>>>>>>>   "+actualValue.replace("\n", " "));
			if (assignedToMsg.equals(actualValue)) {
				log(LogStatus.INFO,assignedToMsg+" Verified for subject : "+subject+" For item : "+createdItemValue,YesNo.No);

			}else {
				sa.assertTrue(false,assignedToMsg+" not Verified for subject : "+subject+" For item : "+createdItemValue+"\nActual  :  "+actualValue+"\nExpected : "+assignedToMsg);
				log(LogStatus.SKIP,assignedToMsg+" not Verified for subject : "+subject+" For item : "+createdItemValue+"\nActual  :  "+actualValue+"\nExpected : "+assignedToMsg,YesNo.Yes);
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
		


	


}

public void verifyActivityAtPastStep(String projectName,PageName pageName,String createdItemValue,String subject,String assignedToMsg,DueDate dueDate,boolean isMeetingType,String meetingTypeValue, boolean isDescription, String descriptionValue, int timeOut){

	WebElement ele;

	String actualValue="";

	String nextStepsXpath = "//div[@class='slds-section__title  past_activity']/following-sibling::div[@class='activity-timeline'][1]";
	String subjectXpath = nextStepsXpath+"//*[@title='"+subject+"']";

	String dateXpath = subjectXpath+"/..//following-sibling::*//*[text()='"+dueDate+"']";
	ele = FindElement(driver, dateXpath, dueDate.toString(), action.SCROLLANDBOOLEAN, 20);

	// Due DATE

	if (ele!=null) {
		log(LogStatus.INFO,dueDate+" verified for subject : "+subject+" For item : "+createdItemValue,YesNo.No);	
	}else {
		sa.assertTrue(false,dueDate+" not verified for subject : "+subject+" For item : "+createdItemValue);
		log(LogStatus.SKIP,dueDate+" not verified for subject : "+subject+" For item : "+createdItemValue,YesNo.Yes);
	}


		// Assigned To 
		String assignedToxpath = subjectXpath+"/../..//following-sibling::p";
		ele = FindElement(driver, assignedToxpath, "Asigned To ", action.SCROLLANDBOOLEAN, 5);
		if (ele!=null) {
			log(LogStatus.INFO,"Asigned To verified for subject : "+subject+" For item : "+createdItemValue,YesNo.No);	

			System.err.println(">>>>>>>>>>>>   "+ele.getText().trim());
			actualValue=ele.getText().trim().replace("\n", " ");
			System.err.println(">>>>>>>>>>>>   "+actualValue.replace("\n", " "));
			if (assignedToMsg.equals(actualValue)) {
				log(LogStatus.INFO,assignedToMsg+" Verified for subject : "+subject+" For item : "+createdItemValue,YesNo.No);

			}else {
				sa.assertTrue(false,assignedToMsg+" not Verified for subject : "+subject+" For item : "+createdItemValue+"\nActual  :  "+actualValue+"\nExpected : "+assignedToMsg);
				log(LogStatus.SKIP,assignedToMsg+" not Verified for subject : "+subject+" For item : "+createdItemValue+"\nActual  :  "+actualValue+"\nExpected : "+assignedToMsg,YesNo.Yes);
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
		


	


}


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

public boolean clickOnRecordOnNextStepsOrPastActivities(String projectName, String record, String taskName) {
	String xpath="//h3[@title='"+taskName+"']/../../following-sibling::p//a[text()='"+record+"']";
	WebElement ele=FindElement(driver, xpath, "record name", action.BOOLEAN, 10);
	ele=isDisplayed(driver, ele,"visibility", 10, record);
	if (ele!=null) {
		if (click(driver, ele, record, action.SCROLLANDBOOLEAN)) {
			return true;
		}
		else {
			log(LogStatus.ERROR, "could not click on "+record, YesNo.Yes);
		}
	}
	else {
		log(LogStatus.ERROR, record+" not displayed", YesNo.Yes);
	}
	return false;
}

public WebElement selectShowMoreActionForTaskInNextSteps(String projectName, PageName pageName, String taskSubject, ShowMoreAction sma) {
	String xpath="",show="";
	if (clickOnShowMoreActionForTaskOnly(projectName, pageName, taskSubject)) {
		show=sma.toString().replace("_", " ");
		xpath="//h3[@title='"+taskSubject+"']/../following-sibling::div//button[@title='Show 4 more actions']/following-sibling::div//li[@title='"+show+"']//a";
		WebElement ele=FindElementSingleQuotes(driver, xpath, sma.toString(), action.SCROLLANDBOOLEAN, 40);
		//ele=isDisplayed(driver, ele, "visibility", 10, sma.toString());
		if (ele!=null) {
			return ele;
		}
		else {
			log(LogStatus.ERROR, sma.toString()+" could not be found on show more actions", YesNo.Yes);
		}
	}else {
		log(LogStatus.ERROR, "show more actions for task could not be clickable", YesNo.Yes);
	}
	return null;
}

public WebElement findActionDropdownElement(String projectName, PageName pageName, String taskSubject, ShowMoreAction sma) {
	String show=sma.toString().replace("_", " ");
	String xpath="//h3[@title='"+taskSubject+"']/../following-sibling::div//button[@title='Show 4 more actions']/following-sibling::div//li[@title='"+show+"']//a";
	WebElement ele=FindElement(driver, xpath, sma.toString(), action.SCROLLANDBOOLEAN, 40);
	ele=isDisplayed(driver, ele, "visibility", 10, sma.toString());
	if (ele!=null) {
		return ele;
	}
	else {
		log(LogStatus.ERROR, sma.toString()+" could not be found on show more actions", YesNo.Yes);
	}
	return null;
}

public boolean clickOnShowMoreActionForTaskOnly(String projectName, PageName pageName, String taskSubject) {
	String xpath="";
	xpath="//h3[@title='"+taskSubject+"']/../following-sibling::div//button[@title='Show 4 more actions']";
	WebElement ele=FindElement(driver, xpath, "show more icon", action.SCROLLANDBOOLEAN, 10);
	ele=isDisplayed(driver, ele, "visibility", 10, "show more icon");
	if (ele!=null) {
		if (clickUsingJavaScript(driver, ele, "show more icon", action.SCROLLANDBOOLEAN)) {
			return true;
		}else {
			log(LogStatus.ERROR, "show more actions could not be clickable", YesNo.Yes);
		}
	}else {
		log(LogStatus.ERROR, "show more actions not found", YesNo.Yes);
	}
	return false;
}

public void verifyActivityAtNextStep1(String projectName,PageName pageName,String createdItemValue,String subject,String assignedToMsg,String dueDate,boolean isMeetingType,String meetingTypeValue, boolean isDescription, String descriptionValue, int timeOut){

	WebElement ele;

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

			System.err.println(">>>>>>>>>>>>   "+ele.getText().trim());
			actualValue=ele.getText().trim().replace("\n", " ");
			System.err.println(">>>>>>>>>>>>   "+actualValue.replace("\n", " "));
			if (assignedToMsg.equals(actualValue)) {
				log(LogStatus.INFO,assignedToMsg+" Verified for subject : "+subject+" For item : "+createdItemValue,YesNo.No);

			}else {
				sa.assertTrue(false,assignedToMsg+" not Verified for subject : "+subject+" For item : "+createdItemValue+"\nActual  :  "+actualValue+"\nExpected : "+assignedToMsg);
				log(LogStatus.SKIP,assignedToMsg+" not Verified for subject : "+subject+" For item : "+createdItemValue+"\nActual  :  "+actualValue+"\nExpected : "+assignedToMsg,YesNo.Yes);
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

public void verifyActivityAtPastStep1(String projectName,PageName pageName,String createdItemValue,String subject,String assignedToMsg,String dueDate,boolean isMeetingType,String meetingTypeValue, boolean isDescription, String descriptionValue, int timeOut){

	WebElement ele;

	String actualValue="";

	String nextStepsXpath = "//div[@class='slds-section__title  past_activity']/following-sibling::div[@class='activity-timeline'][1]";
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

			System.err.println(">>>>>>>>>>>>   "+ele.getText().trim());
			actualValue=ele.getText().trim().replace("\n", " ");
			System.err.println(">>>>>>>>>>>>   "+actualValue.replace("\n", " "));
			if (assignedToMsg.equals(actualValue)) {
				log(LogStatus.INFO,assignedToMsg+" Verified for subject : "+subject+" For item : "+createdItemValue,YesNo.No);

			}else {
				sa.assertTrue(false,assignedToMsg+" not Verified for subject : "+subject+" For item : "+createdItemValue+"\nActual  :  "+actualValue+"\nExpected : "+assignedToMsg);
				log(LogStatus.SKIP,assignedToMsg+" not Verified for subject : "+subject+" For item : "+createdItemValue+"\nActual  :  "+actualValue+"\nExpected : "+assignedToMsg,YesNo.Yes);
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

public boolean verifyUIOfActivityTimeLineFilter(String projectName) {
	boolean flag=true;
	
	String xpath="";
	List<WebElement> eleList=null;
	WebElement ele;
	String values;
	List<String> s;
	
	
	xpath="//div[@class='container']/div//*[@class='leftAllignedCheckboxes']//legend";
	eleList = FindElements(driver, xpath, "");
	values="DATE RANGE,Activities TO SHOW,Activity TYPE";
 s=	compareMultipleList(driver, values, eleList);
	if (s.isEmpty()) {
		log(LogStatus.INFO, values+" verified ", YesNo.No);
	}
	else {
		for (String i:s) {
			log(LogStatus.ERROR, i+" column is not found", YesNo.Yes);
			BaseLib.sa.assertTrue(false, i+" column is not found");
		}
		flag= false;
	}

	values="All time,Last 7 Days,Next 7 Days,Last 30 Days";
	String dateRangeXpath="//div[@class='container']/div//*[@class='leftAllignedCheckboxes']//legend[text()='DATE RANGE']/following-sibling::div//label";
	eleList = FindElements(driver, dateRangeXpath, values+" for Date Range");
	 s=	compareMultipleListOnBasisOfTitle(driver, values, eleList);
	if (s.isEmpty()) {
		log(LogStatus.INFO, values+" for Date Range Verified ", YesNo.No);
	}
	else {
		for (String i:s) {
			log(LogStatus.ERROR, i+" not found on Date Range ", YesNo.Yes);
			BaseLib.sa.assertTrue(false, i+" not found on Date Range ");
		}
		flag= false;
	}
	
	dateRangeXpath="//div[@class='container']/div//*[@class='leftAllignedCheckboxes']//legend[text()='DATE RANGE']/following-sibling::div//label[@title='All time']/input[@name='dateFilters']";
	
	ele=FindElement(driver, dateRangeXpath, "DATE RANGE : All time", action.BOOLEAN, 10);
	ele=isDisplayed(driver, ele, "visibility", 10, "DATE RANGE : All time");
	
	if (isSelected(driver, ele, "DATE RANGE : All time")) {
		log(LogStatus.INFO, "DATE RANGE : All time is selected by default", YesNo.No);	
	} else {
		log(LogStatus.ERROR, "DATE RANGE : All time is not selected by default", YesNo.Yes);
		BaseLib.sa.assertTrue(false, "DATE RANGE : All time is not selected by default");
	}

	////////////////////////////////////////////////////////////////
	
	
	values="All Activities,My Activities";
	String activitiesToShowXpath="//div[@class='container']/div//*[@class='leftAllignedCheckboxes']//legend[text()='Activities']/following-sibling::div//label";
	eleList = FindElements(driver, activitiesToShowXpath, values+" for Date Range");
	 s=	compareMultipleListOnBasisOfTitle(driver, values, eleList);
	if (s.isEmpty()) {
		log(LogStatus.INFO, values+" for Activities TO SHOW Verified ", YesNo.No);
	}
	else {
		for (String i:s) {
			log(LogStatus.ERROR, i+" not found on Activities TO SHOW ", YesNo.Yes);
			BaseLib.sa.assertTrue(false, i+" not found on Activities TO SHOW ");
		}
		flag= false;
	}
	
	activitiesToShowXpath="//div[@class='container']/div//*[@class='leftAllignedCheckboxes']//legend[text()='Activities']/following-sibling::div//label[@title='All Activities']/input[@name='ownerFilter']";
	
	ele=FindElement(driver, activitiesToShowXpath, "Activities TO SHOW : All Activities", action.BOOLEAN, 10);
	ele=isDisplayed(driver, ele, "visibility", 10, "Activities TO SHOW : All Activities");
	
	if (isSelected(driver, ele, "Activities TO SHOW : All Activities")) {
		log(LogStatus.INFO, "Activities TO SHOW : All Activities is selected by default", YesNo.No);	
	} else {
		log(LogStatus.ERROR, "Activities TO SHOW : All Activities is not selected by default", YesNo.Yes);
		BaseLib.sa.assertTrue(false, "Activities TO SHOW : All Activities is not selected by default");
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	
	values="All Types,List Email,Email,Logged Calls,Events,Tasks";
	String activityXpath="//div[@class='container']/div//*[@class='leftAllignedCheckboxes']//legend[text()='Activity']/following-sibling::div//label";
	eleList = FindElements(driver, activityXpath, values+" for Activity TYPE");
	 s=	compareMultipleListOnBasisOfTitle(driver, values, eleList);
	if (s.isEmpty()) {
		log(LogStatus.INFO, values+" for Activity TYPE Verified ", YesNo.No);
	}
	else {
		for (String i:s) {
			log(LogStatus.ERROR, i+" not found on Activity TYPE ", YesNo.Yes);
			BaseLib.sa.assertTrue(false, i+" not found on Activity TYPE ");
		}
		flag= false;
	}
	
	activityXpath="//div[@class='container']/div//*[@class='leftAllignedCheckboxes']//legend[text()='Activity']/following-sibling::div//label[@title='All Types']/input";
	
	ele=FindElement(driver, activityXpath, "Activity TYPE : All Types", action.BOOLEAN, 10);
	ele=isDisplayed(driver, ele, "visibility", 10, "Activity TYPE : All Types");
	
	if (isSelected(driver, ele, "Activity TYPE : All Types")) {
		log(LogStatus.INFO, "Activity TYPE : All Types is selected by default", YesNo.No);	
	} else {
		log(LogStatus.ERROR, "Activity TYPE : All Types is not selected by default", YesNo.Yes);
		BaseLib.sa.assertTrue(false, "Activity TYPE : All Types is not selected by default");
	}
	
	
	if (getCancelBtnAtActivityTimeLineFilterPopuP(projectName, 10)!=null)
		log(LogStatus.INFO, "successfully verified cancel button", YesNo.No);
	else {
		log(LogStatus.ERROR, "could not verify cancel button", YesNo.No);
		BaseLib.sa.assertTrue(false, "could not verify cancel button");
		flag=false;

	}
	
	if (getApplyBtnAtActivityTimeLineFilterPopuP(projectName, 10)!=null)
		log(LogStatus.INFO, "successfully verified Apply button", YesNo.No);
	else {
		log(LogStatus.ERROR, "could not verify Apply button", YesNo.No);
		BaseLib.sa.assertTrue(false, "could not verify Apply button");
		flag=false;

	}
	
	return flag;
}

public boolean clickonRadioCheckBoxOnActivityTimeLineFilter(String projectName,DateRange dateRange,ActivitiesToShow activitiesToShow,ActivityTypes[] activityTypes) {
	boolean flag=true;
	
	String xpath="";
	List<WebElement> eleList=null;
	WebElement ele;
	String values;
	List<String> s;
	
	
	String val=dateRange.toString().replace("_", " ");
	String dateRangeXpath = "//div[@class='container']/div//*[@class='leftAllignedCheckboxes']//legend[text()='DATE RANGE']/following-sibling::div//label[@title='"+val+"']/input[@name='dateFilters']";

	ele=FindElement(driver, dateRangeXpath, "DATE RANGE : "+val, action.SCROLLANDBOOLEAN, 10);
	ele=isDisplayed(driver, ele, "visibility", 10, "DATE RANGE : "+val);
	
	if (click(driver, ele, "DATE RANGE : "+val, action.BOOLEAN)) {
		log(LogStatus.INFO, "click on DATE RANGE : "+val, YesNo.No);	
	} else {
		flag=false;
		log(LogStatus.ERROR, "Not Able to click on DATE RANGE : "+val, YesNo.Yes);
		BaseLib.sa.assertTrue(false, "Not Able to click on DATE RANGE : "+val);
	}

	////////////////////////////////////////////////////////////////
	ThreadSleep(500);
	
	
	 val=activitiesToShow.toString().replace("_", " ");
	String activitiesToShowXpath = "//div[@class='container']/div//*[@class='leftAllignedCheckboxes']//legend[text()='Activities']/following-sibling::div//label[@title='"+val+"']/input[@name='ownerFilter']";
	
	ele=FindElement(driver, activitiesToShowXpath, "Activities TO SHOW : "+val, action.SCROLLANDBOOLEAN, 10);
	ele=isDisplayed(driver, ele, "visibility", 10, "Activities TO SHOW : "+val);
	
	if (click(driver, ele, "Activities TO SHOW : "+val, action.BOOLEAN)) {
		log(LogStatus.INFO, "click on Activities TO SHOW : "+val, YesNo.No);	
	} else {
		flag=false;
		log(LogStatus.ERROR, "Not Able to click on Activities TO SHOW : "+val, YesNo.Yes);
		BaseLib.sa.assertTrue(false, "Not Able to click on Activities TO SHOW : "+val);
	}
	ThreadSleep(500);
	/////////////////////////////////////////////////////////////////////////////////
	

	String activityXpath;
	
	for (int i = 0; i < activityTypes.length; i++) {
		val = activityTypes[i].toString().replace("_", " ");
		
		activityXpath = "//div[@class='container']/div//*[@class='leftAllignedCheckboxes']//legend[text()='Activity']/following-sibling::div//label[@title='"+val+"']/input";
		ele=FindElement(driver, activityXpath, "Activity TYPE : All Types", action.SCROLLANDBOOLEAN, 10);
		ele=isDisplayed(driver, ele, "visibility", 10, "Activity TYPE : "+val);
		
		if (click(driver, ele, "Activity TYPE : "+val, action.BOOLEAN)) {
			log(LogStatus.INFO, "click on Activity TYPE : "+val, YesNo.No);	
		} else {
			flag=false;
			log(LogStatus.ERROR, "Not Able to click on Activity TYPE : "+val, YesNo.Yes);
			BaseLib.sa.assertTrue(false, "Not Able to click on Activity TYPE : "+val);
		}
		ThreadSleep(500);
	}

	
	return flag;
}

public boolean mouseHoverOnRecordsOnTask(String projectName, String record, String taskName) {
	String xpath="//h3[@title='"+taskName+"']/../../following-sibling::p//a[text()='"+record+"']";
	WebElement ele=FindElement(driver, xpath, "record name", action.BOOLEAN, 10);
	ele=isDisplayed(driver, ele,"visibility", 10, record);
	scrollDownThroughWebelement(driver, ele, record);
	if (ele!=null) {
		if (mouseHoverJScript(driver, ele, record, 10)) {
			log(LogStatus.INFO, "mouse over successul on "+record, YesNo.No);
			return true;
		} else {
			log(LogStatus.ERROR, "mouse over could not be done on "+record, YesNo.Yes);
		}
		
	}else {
		log(LogStatus.ERROR, "record not found to mouse over: "+record, YesNo.Yes);
	}
	
	return false;
}

public boolean FieldValueVerificationOnMouseOverPopup(String projectName, String record,
		String labelName,String labelValue) {
	String xpath = "";
	WebElement ele = null;
	String finalLabelName=labelName.replace("_", " ");
	if (labelName.equalsIgnoreCase(ActivityPopupFields.Active.toString()))
		xpath="//section//div//a[@title='"+record+"']/../../following-sibling::ul//li//span[@title='"+finalLabelName+"']/following-sibling::div//img[@class='"+labelValue+"']";
	else if(labelName.equalsIgnoreCase(ActivityPopupFields.Legal_Name.toString()) || labelName.equalsIgnoreCase(ActivityPopupFields.Email.toString())
			|| labelName.equalsIgnoreCase(ActivityPopupFields.Institution_Owner.toString())|| labelName.equalsIgnoreCase(ActivityPopupFields.Website.toString())
			|| labelName.equalsIgnoreCase(ActivityPopupFields.Account_Name.toString())|| labelName.equalsIgnoreCase(ActivityPopupFields.Primary_Coverage_Officer.toString()) || labelName.equalsIgnoreCase(ActivityPopupFields.Entity_Owner.toString()))
		xpath="//section//div//a[@title='"+record+"']/../../following-sibling::ul//li//span[@title='"+finalLabelName+"']/following-sibling::span//a[@title='"+labelValue+"']";
	else
		xpath = "//section//div//a[@title='"+record+"']/../../following-sibling::ul//li//span[@title='"+finalLabelName+"']/following-sibling::span[contains(text(),'"+labelValue+"')]";
	
	if (labelValue.equals("") && ProjectName.PEEdge.toString().equalsIgnoreCase(projectName)) {
		xpath="//section//div//a[@title='"+record+"']/../../following-sibling::ul//li//span[contains(@title,'"+finalLabelName+"')]";
	}
	else if (labelValue.equals("")) {
		xpath="//section//div//a[@title='"+record+"']/../../following-sibling::ul//li//span[@title='"+finalLabelName+"']";
	}
	
	if (labelName.equalsIgnoreCase(ActivityPopupFields.Firm.toString()) && ProjectName.PEEdge.toString().equalsIgnoreCase(projectName)) {
		xpath = "//section//div//a[@title='"+record+"']/../../following-sibling::ul//li//span[@title='"+finalLabelName+"']/following-sibling::span/a[contains(text(),'"+labelValue+"')]";
	}
	
	ele = isDisplayed(driver,
			FindElement(driver, xpath, labelName + " label text in " + projectName, action.SCROLLANDBOOLEAN, 20),
			"Visibility", 10, labelName + " label text in " + projectName);
	if (ele != null) {
		appLog.info("Lable successfully verified : "+finalLabelName+" with value "+labelValue);
		return true;
	} else {
		appLog.error(finalLabelName + " Value is not visible so cannot matched  label Value "+labelValue);
	}
	return false;

}

public WebElement crossIconForActivityMouseoverPopup(String projectName, String record) {
	String xpath="//section//div//a[@title='"+record+"']/../../preceding-sibling::button[@title='Close']";
	
	WebElement ele = isDisplayed(driver,
			FindElement(driver, xpath,  " label text in " + projectName, action.SCROLLANDBOOLEAN, 60),
			"Visibility", 30,  " label text in " + projectName);
	scrollDownThroughWebelement(driver, ele, "cross icon for "+record);
	if (ele!=null) {
		log(LogStatus.INFO, "successfully found cross icon", YesNo.No);
		return ele;
	}
	else {
		log(LogStatus.ERROR, "could not find cross icon", YesNo.Yes);
	}
	return null;
}

public boolean verifyStartandEndTimeOnActivityTimeline(String projectName, PageName pageName,String taskname, ActivityType at,String start, String end) {
	String type="";
	boolean flag=true;
	WebElement ele=null;
	if (at==ActivityType.Next) {
		type="Next_steps";
	} else {
		type="slds-section__title  past_activity";
	}
	String xpath="//div[@class='"+type+"']/following-sibling::div[@class='activity-timeline'][1]//*[@title='"+taskname+"']/../..//following-sibling::div//article//ul//li";
	String startXpath=xpath+"//span[text()='Start']";
	if (start!=null) {
		startXpath+="/following-sibling::span/span";
	}
	ele=isDisplayed(driver, FindElement(driver, startXpath, "start", action.SCROLLANDBOOLEAN, 10), "visibility",10, "start");
	if (ele!=null) {
		String text=ele.getText().trim();
		if (start!=null) {
			if (text.equalsIgnoreCase(start)) {
				flag=true;
				log(LogStatus.INFO, "successfully verified start value with Actual: "+text, YesNo.No);
			}
			else {
				log(LogStatus.ERROR, "could not verify start value with Actual: "+text+" Expected: "+start, YesNo.Yes);
				BaseLib.sa.assertTrue(false,"could not verify start value with Actual: "+text+" Expected: "+start );
				flag=false;
			}
		}
		else {
			log(LogStatus.INFO, "successfully verified presence of text 'start'", YesNo.No);
			flag=true;
		}
	}else {
		log(LogStatus.ERROR, "start value is not displayed", YesNo.No);
		BaseLib.sa.assertTrue(false,"start value is not displayed" );
		flag=false;

	}

	String endXpath=xpath+"//span[text()='End']";
	if (end!=null) {
		endXpath+="/following-sibling::span/span";
	}
	ele=isDisplayed(driver, FindElement(driver, endXpath, "end", action.SCROLLANDBOOLEAN, 10), "visibility",10, "end");
	if (ele!=null) {
		String text=ele.getText().trim();
		if (end!=null) {
			if (text.equalsIgnoreCase(end)) {
				flag=true;
				log(LogStatus.INFO, "successfully verified end value with Actual: "+text, YesNo.No);
			}
			else {
				log(LogStatus.ERROR, "could not verify end value with Actual: "+text+" Expected: "+end, YesNo.Yes);
				BaseLib.sa.assertTrue(false,"could not verify end value with Actual: "+text+" Expected: "+end );
				flag=false;
			}
		}
		else {
			log(LogStatus.INFO, "successfully verified presence of text 'end'", YesNo.No);
			flag=true;
		}
	}else {
		log(LogStatus.ERROR, "end value is not displayed", YesNo.No);
		BaseLib.sa.assertTrue(false,"end value is not displayed" );
		flag=false;

	}

	return flag;
}

public boolean verifyFromAddressToAddressTextBodyOnActivityTimeline(String projectName, PageName pageName,String taskname, ActivityType at,String from, String to,String emailBody,boolean clickHereToRespond) {
	String type="";
	boolean flag=true;
	WebElement ele=null;
	if (at==ActivityType.Next) {
		type="Next_steps";
	} else {
		type="slds-section__title  past_activity";
	}
	String xpath="//div[@class='"+type+"']/following-sibling::div[@class='activity-timeline'][1]//*[@title='"+taskname+"']/../..//following-sibling::div//article//ul//li";
	String fromXpath=xpath+"//span[text()='From Address']";
	if (from!=null) {
		fromXpath+="/following-sibling::span//a";
	}
	ele=isDisplayed(driver, FindElement(driver, fromXpath, "from", action.SCROLLANDBOOLEAN, 10), "visibility",10, "from");
	if (ele!=null) {
		String text=ele.getText().trim();
		if (from!=null) {
			if (text.equalsIgnoreCase(from)) {
				flag=true;
				log(LogStatus.INFO, "successfully verified from address value with Actual: "+text, YesNo.No);
			}
			else {
				log(LogStatus.ERROR, "could not verify from address value with Actual: "+text+" Expected: "+from, YesNo.Yes);
				BaseLib.sa.assertTrue(false,"could not verify from address value with Actual: "+text+" Expected: "+from );
				flag=false;
			}
		}
		else {
			log(LogStatus.INFO, "successfully verified presence of text 'from address'", YesNo.No);
			flag=true;
		}
	}else {
		log(LogStatus.ERROR, "from address value is not displayed", YesNo.No);
		BaseLib.sa.assertTrue(false,"from address value is not displayed" );
		flag=false;

	}

	String toXpath=xpath+"//span[text()='To Address']";
	if (to!=null) {
		toXpath+="/following-sibling::span//a";
	}
	ele=isDisplayed(driver, FindElement(driver, toXpath, "to address", action.SCROLLANDBOOLEAN, 10), "visibility",10, "to address");
	if (ele!=null) {
		String text=ele.getText().trim();
		if (to!=null) {
			if (text.equalsIgnoreCase(to)) {
				flag=true;
				log(LogStatus.INFO, "successfully verified to address value with Actual: "+text, YesNo.No);
			}
			else {
				log(LogStatus.ERROR, "could not verify to address value with Actual: "+text+" Expected: "+to, YesNo.Yes);
				BaseLib.sa.assertTrue(false,"could not verify to address value with Actual: "+text+" Expected: "+to );
				flag=false;
			}
		}
		else {
			log(LogStatus.INFO, "successfully verified presence of text 'to address'", YesNo.No);
			flag=true;
		}
	}else {
		log(LogStatus.ERROR, "to address value is not displayed", YesNo.No);
		BaseLib.sa.assertTrue(false,"to address value is not displayed" );
		flag=false;

	}
	
	String articleXpath="//div[@class='"+type+"']/following-sibling::div[@class='activity-timeline'][1]//*[@title='"+taskname+"']/../..//following-sibling::div//article";
	String textBodyXpath=articleXpath+"//span[text()='Text Body']";
	if (emailBody!=null) {
		textBodyXpath+="/following-sibling::*[text()='"+emailBody+"']";
	}
	ele=isDisplayed(driver, FindElement(driver, textBodyXpath, "email body", action.SCROLLANDBOOLEAN, 10), "visibility",10, "email body");
	if (ele!=null) {
		flag=true;
		log(LogStatus.INFO, "successfully verified email body value with Actual: ", YesNo.No);
	}else {
		log(LogStatus.ERROR, "email body value is not displayed", YesNo.No);
		BaseLib.sa.assertTrue(false,"email body value is not displayed" );
		flag=false;

	}
	if (clickHereToRespond) {
		ele=clickHereToRespondLink(projectName, at, taskname);
		if (ele!=null) {
			flag=true;
			log(LogStatus.INFO, "successfully verified click here to respond", YesNo.No);
		}else {
			log(LogStatus.ERROR, "click here to respond is not displayed", YesNo.No);
			BaseLib.sa.assertTrue(false,"click here to respond is not displayed" );
			flag=false;

		}
	}
	

	return flag;
}

public WebElement clickHereToRespondLink(String projectName, ActivityType at,String taskname) {
	String type="";
	boolean flag=true;
	WebElement ele=null;
	if (at==ActivityType.Next) {
		type="Next_steps";
	} else {
		type="slds-section__title  past_activity";
	}
	String articleXpath="//div[@class='"+type+"']/following-sibling::div[@class='activity-timeline'][1]//*[@title='"+taskname+"']/../..//following-sibling::div//article";
	String x=articleXpath+"//div[contains(text(),'To Respond')]//a[contains(text(),'Click Here')]";
	ele=isDisplayed(driver, FindElement(driver, x, "click here to respond", action.SCROLLANDBOOLEAN, 10), "visibility",10, "click here to respond");
	if (ele!=null) {

		log(LogStatus.INFO, "successfully verified click here to respond", YesNo.No);
		return ele;
	}else {
		log(LogStatus.INFO, "click here to respond is not displayed", YesNo.No);
		BaseLib.sa.assertTrue(false,"click here to respond is not displayed" );
	}
	return null;
}

public WebElement returnEmailHeadingOnPage(String projectName, String subject) {
	String xpath="//h1[text()='"+subject+"']";
	WebElement ele=isDisplayed(driver, FindElement(driver, xpath, "email heading", action.SCROLLANDBOOLEAN, 10), "visibility",10, "email heading");
	return ele;
}

public WebElement linkOnArticleSectionOnActivity(String projectName, String subject,String record) {
	String xpath="//h3[@title='"+subject+"']/../../..//article//a[text()='"+record+"']";
	WebElement ele=isDisplayed(driver, FindElement(driver, xpath, record, action.SCROLLANDBOOLEAN, 10), "visibility",10, record);
	return ele;
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
public List<WebElement> DropDownValueList(String projectName,PageName pageName,String label,action action,int timeOut) {
	boolean flag=false;
	WebElement ele;
	String xpath="";
	label=label.replace("_", " ");
	if (PageName.TaskPage.toString().equals(pageName.toString()) || PageName.NewEventPopUp.toString().equals(pageName.toString())) {
	//	xpath = "//span[text()='"+label+"']/../following-sibling::div";
		xpath = "//ul/li/a";
	} else {
		xpath = "//label[text()='"+label+"']/..//div//span/span";
	}
	
	List<WebElement> eleList = FindElements(driver, xpath, "Drop Down : "+label);
	return eleList;
}



public boolean checkForActivitiesCount(String projectName,PageName pageName,String activityCount,boolean isChecked,boolean isDisabled,action action,int timeOut) {
	boolean flag=true;;
	WebElement ele;
	String xpath="";
	String value="";
	click(driver, dropdownBtnMorePastAct(projectName, timeOut), "dropdown button", action.SCROLLANDBOOLEAN);
	ThreadSleep(3000);
	xpath = "//li[@title='"+activityCount+" Activities']//a";
	ele=FindElement(driver, xpath, activityCount+" activities", action.SCROLLANDBOOLEAN, 10);
	ele=isDisplayed(driver, ele, "visibility",5, activityCount+" activities");
	
	if (ele!=null) {
		
		try {
			value = ele.getAttribute("aria-checked");
			System.err.println("aria-checked : "+value);
			Boolean.parseBoolean(value);
			
			if (Boolean.parseBoolean(value)==isChecked) {
				
			} else {
				flag=false;
				log(LogStatus.ERROR, "Checked not verified for activity no: "+activityCount, YesNo.Yes);
			}
			
			
			value = ele.getAttribute("aria-disabled");
			System.err.println("aria-disabled : "+value);
			Boolean.parseBoolean(value);
			
			if (Boolean.parseBoolean(value)==isDisabled) {
				
			} else {
				flag=false;
				log(LogStatus.ERROR, "Disabled not verified for activity no: "+activityCount, YesNo.Yes);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag=false;
			log(LogStatus.ERROR, "Exception for activity no: "+activityCount, YesNo.Yes);
		}
		
		
	} else {
		flag=false;
		log(LogStatus.ERROR, "Element not found for activity no: "+activityCount, YesNo.Yes);
	}
	
	click(driver, dropdownBtnMorePastAct(projectName, timeOut), "dropdown button", action.SCROLLANDBOOLEAN);
	return flag;
	
}

public boolean verifyListEmailActivityTimeline(String projectName, PageName pageName,String taskname, ActivityType at,String scheduledDate, int total, String Status,String emailBody) {
	String type="";
	boolean flag=true;
	WebElement ele=null;
	if (at==ActivityType.Next) {
		type="Next_steps";
	} else {
		type="slds-section__title  past_activity";
	}
	String t=String.valueOf(total);
	String values[]= {scheduledDate,t,Status};
	String labels[]= {"Scheduled Date","Total Sent","Status"};
	String xpath="//div[@class='"+type+"']/following-sibling::div[@class='activity-timeline'][1]//*[@title='"+taskname+"']/../..//following-sibling::div//article//ul//li";
	int i=0;
	for (String f:values) {
		String fieldXpath=xpath+"//span[text()='"+labels[i]+"']/following-sibling::span/span";
		ele=isDisplayed(driver, FindElement(driver, fieldXpath, labels[i], action.SCROLLANDBOOLEAN, 10), "visibility",10, labels[i]);
		if (ele!=null) {
			String text=ele.getText().trim();
			if (i==0) {
				if (text.contains(f)) {
					flag=true;
				}
			}
			else {
				if (text.equalsIgnoreCase(f)) {
					flag=true;
				}
			}
			if (flag) {
				log(LogStatus.INFO, "successfully verified "+labels[i]+" value with Actual: "+text, YesNo.No);
			}
			else {
				log(LogStatus.ERROR, "could not verify "+labels[i]+" value with Actual: "+text+" Expected: "+f, YesNo.Yes);
				BaseLib.sa.assertTrue(false,"could not verify "+labels[i]+" value with Actual: "+text+" Expected: "+f );
				flag=false;
			}

		}else {
			log(LogStatus.ERROR, labels[i]+" value is not displayed", YesNo.No);
			BaseLib.sa.assertTrue(false,labels[i]+" value is not displayed" );
			flag=false;

		}
		i++;
	}
	String articleXpath="//div[@class='"+type+"']/following-sibling::div[@class='activity-timeline'][1]//*[@title='"+taskname+"']/../..//following-sibling::div//article";
	String textBodyXpath=articleXpath+"//span[text()='Text Body']";
	if (emailBody!=null) {
		textBodyXpath+="/following-sibling::*[text()='"+emailBody+"']";
	}
	ele=isDisplayed(driver, FindElement(driver, textBodyXpath, "email body", action.SCROLLANDBOOLEAN, 10), "visibility",10, "email body");
	if (ele!=null) {
		flag=true;
		log(LogStatus.INFO, "successfully verified email body value with Actual: ", YesNo.No);
	}else {
		log(LogStatus.ERROR, "email body value is not displayed", YesNo.No);
		BaseLib.sa.assertTrue(false,"email body value is not displayed" );
		flag=false;

	}

	return flag;
}

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

public void verifyEventAtActivityTimeLine(String projectName,PageName pageName,String createdItemValue,String subject,String assignedToMsg,String dueDate,boolean isMeetingType,String meetingTypeValue, boolean isDescription, String descriptionValue, int timeOut) {
	try {
		String dateValue = getDateValueAccording(dueDate, AmericaLosAngelesTimeZone);
		ActivityType activityType = isPastDate(dueDate, AmericaLosAngelesTimeZone);
		if (ActivityType.Past.equals(activityType)) {
			verifyActivityAtPastStep1(projectName, pageName, createdItemValue, subject, assignedToMsg, dateValue, isMeetingType, meetingTypeValue, isDescription, descriptionValue, timeOut);
		} else if(ActivityType.Next.equals(activityType)){
			verifyActivityAtNextStep1(projectName, pageName, createdItemValue, subject, assignedToMsg, dateValue, isMeetingType, meetingTypeValue, isDescription, descriptionValue, timeOut);
		}else {
			log(LogStatus.ERROR, "Not Able to verify Activity : "+createdItemValue+" For type : "+activityType, YesNo.Yes);
			BaseLib.sa.assertTrue(false, "Not Able to verify Activity : "+createdItemValue+" For type : "+activityType);
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		log(LogStatus.ERROR, "Not Able to verify Activity : "+createdItemValue+" as exception occurs", YesNo.Yes);
		BaseLib.sa.assertTrue(false, "Not Able to verify Activity : "+createdItemValue+"  as exception occurs");

	}
}

public void verifyActivityAtPastStep2(String projectName,PageName pageName,String createdItemValue,String subject,String assignedToMsg,String dueDate,boolean isMeetingType,String meetingTypeValue, boolean isDescription, String descriptionValue, int timeOut){

	WebElement ele;
	dueDate = getDateValueAccording(dueDate, AmericaLosAngelesTimeZone);
	String actualValue="";

	String nextStepsXpath = "//div[@class='slds-section__title  past_activity']/following-sibling::div[@class='activity-timeline'][1]";
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

			System.err.println(">>>>>>>>>>>>   "+ele.getText().trim());
			actualValue=ele.getText().trim().replace("\n", " ");
			System.err.println(">>>>>>>>>>>>   "+actualValue.replace("\n", " "));
			if (assignedToMsg.equals(actualValue)) {
				log(LogStatus.INFO,assignedToMsg+" Verified for subject : "+subject+" For item : "+createdItemValue,YesNo.No);

			}else {
				sa.assertTrue(false,assignedToMsg+" not Verified for subject : "+subject+" For item : "+createdItemValue+"\nActual  :  "+actualValue+"\nExpected : "+assignedToMsg);
				log(LogStatus.SKIP,assignedToMsg+" not Verified for subject : "+subject+" For item : "+createdItemValue+"\nActual  :  "+actualValue+"\nExpected : "+assignedToMsg,YesNo.Yes);
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

			System.err.println(">>>>>>>>>>>>   "+ele.getText().trim());
			actualValue=ele.getText().trim().replace("\n", " ");
			System.err.println(">>>>>>>>>>>>   "+actualValue.replace("\n", " "));
			if (assignedToMsg.equals(actualValue)) {
				log(LogStatus.INFO,assignedToMsg+" Verified for subject : "+subject+" For item : "+createdItemValue,YesNo.No);

			}else {
				sa.assertTrue(false,assignedToMsg+" not Verified for subject : "+subject+" For item : "+createdItemValue+"\nActual  :  "+actualValue+"\nExpected : "+assignedToMsg);
				log(LogStatus.SKIP,assignedToMsg+" not Verified for subject : "+subject+" For item : "+createdItemValue+"\nActual  :  "+actualValue+"\nExpected : "+assignedToMsg,YesNo.Yes);
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





}
