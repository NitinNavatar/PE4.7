package com.navatar.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.navatar.generic.EnumConstants.AddProspectsTab;
import com.navatar.generic.EnumConstants.AppSetting;
import com.navatar.generic.EnumConstants.CSVLabel;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.BaseLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.NavatarQuickLink;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.searchContactInEmailProspectGrid;
import com.navatar.generic.SoftAssert;
import com.relevantcodes.extentreports.LogStatus;
import static com.navatar.generic.EnumConstants.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.AppListeners.*;
public class NavigationPageBusineesLayer extends NavigationPage {
	
	public NavigationPageBusineesLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author Azhar Alam
	 * @param csvRecords
	 * @return navigation parent as key and order as value
	 */
	public static Map<String,Integer> navigationParentLabelWithOrder(List<String> csvRecords){
		Map<String, Integer> map = new LinkedHashMap<String, Integer>(); 
		String[] csvRecords2=null;
		String navigationLabel2=null;
		String parent2="";
		String order2="";
		String emptyType="\"\"";
		String emptyType1="\" \"";
		for (String abc2 : csvRecords) {
			csvRecords2=abc2.split(commaSP);
			navigationLabel2=csvRecords2[0].trim().replace("\"", "");
			parent2=csvRecords2[3].trim().replace("\"", "");
			order2=csvRecords2[2].trim().replace("\"", "");
			System.err.println("Parent : "+parent2);
			System.err.println("order2 : "+order2);
			int i=0;

			if (parent2.isEmpty() || parent2.equals(" ") ||parent2.equals("") || parent2.equalsIgnoreCase("") || parent2.equalsIgnoreCase(emptyType)|| parent2.equalsIgnoreCase(emptyType1)) {
				//System.err.println("inside parent blank");	
				//System.out.println("ye wala "+navigationLabel2+" "+order2+" "+parent2);
				if (order2.isEmpty() || order2.equals(" ") || order2.equals("") || order2.equals(emptyType1) || order2.equals(emptyType)) {
					i=1000;
				}else {
					order2=order2.replace("\"", "");
					i=Integer.parseInt(order2);

				}
				//System.err.println(navigationLabel2+"<><>"+i+" >>> "+parent2);
				//	System.err.println("outside parent blank");	
				map.put(navigationLabel2, i);

			}


		}
		System.err.println("navigationParentLabelWithOrder : "+map);
		return map;

	}

	/**
	 * @author Azhar Alam
	 * @param csvRecords
	 * @return csv data as Map<String,String>
	 */
	public static Map<String,String> navigationParentLabelWithChildAndOrder(List<String> csvRecords){
		Map<String,String> childMap = new LinkedHashMap<String,String>(); 
		String[] csvRecords2=null;
		String navigationLabel2=null;
		String parent2="";
		String order2="";
		String emptyType="\"\"";
		String emptyType1="\" \"";

		for (String abc2 : csvRecords) {
			csvRecords2=abc2.split(commaSP);
			navigationLabel2=csvRecords2[0].trim().replace("\"", "");;
			parent2=csvRecords2[3].trim().replace("\"", "");
			order2=csvRecords2[2].trim().replace("\"", "");
			int i=0;

			if (parent2.isEmpty() || parent2.equals(" ") ||parent2.equals("") || parent2.equalsIgnoreCase(emptyType) || parent2.equalsIgnoreCase(emptyType1)) {


			}else {

				//	System.out.println("ye wala11 "+navigationLabel2+" "+order2+" "+parent2);
				if (order2.isEmpty() || order2.equals(" ") ||order2.equals("") || order2.equalsIgnoreCase(emptyType) || order2.equalsIgnoreCase(emptyType1)) {
					i=1000;
				}else {
					order2=order2.replace("\"", "");
					i=Integer.parseInt(order2);

				}
				//	System.err.println(navigationLabel2+"<><>"+i+" >>> "+parent2);
				//	System.err.println("outside parent blank");
				if (childMap.get(parent2)!=null) {
					String a=childMap.get(parent2);
					childMap.put(parent2,a+","+navigationLabel2+breakSP+i);
				} else {
					childMap.put(parent2,navigationLabel2+breakSP+i);
				}
			}


		}

		System.err.println("navigationParentLabelWithChildAndOrder : "+childMap);
		return childMap;

	}

	/**
	 * @author Azhar Alam
	 * @param order
	 * @param map
	 * @return Map<String, Integer> as sort map by value
	 */
	public static Map<String, Integer> sortByValue(boolean order,Map<String, Integer> map)   
	{  
		
		//convert HashMap into List   
		List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(map.entrySet());  
		//sorting the list elements  
		Collections.sort(list, new Comparator<Entry<String, Integer>>()   
		{  
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2)   
			{  
				if (order)   
				{  
					//compare two object and return an integer  
					return o1.getValue().compareTo(o2.getValue());}   
				else   
				{  
					return o2.getValue().compareTo(o1.getValue());  
				}  
			}  
		});  
		//prints the sorted HashMap  
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();  
		for (Entry<String, Integer> entry : list)   
		{  
			sortedMap.put(entry.getKey(), entry.getValue());  
		}  
		//printMap(sortedMap);  
		System.err.println("sortByValue : "+sortedMap);
		return sortedMap;
	}  
	
	/**
	 * @author Azhar Alam
	 * @param childMap
	 * @return Map<String,String> with Parent-child Navigation menu iten 
	 */
	public static Map<String,String> navigationParentLabelWithChildSorted(Map<String, String> childMap){
		String[] childWithOrder;
		String[] childOrderSpillter;
		Map<String, Integer> childOrder = new LinkedHashMap<String, Integer>();  
		Map<String, String> parentChild = new LinkedHashMap<String, String>(); 
		for (String name: childMap.keySet())         //iteration over keys  
		{  
			//returns the value to which specified key is mapped  
			String childWithOc=childMap.get(name); 
			//System.out.println("Key: " + name + ", Value: " + childWithOc);   
			childWithOrder=childWithOc.split(commaSP);
			for (int i = 0; i < childWithOrder.length; i++) {
				//System.err.println(childWithOrder[i]);
				childOrderSpillter=childWithOrder[i].split(breakSP);
				//	System.out.println(childOrderSpillter[0]+" "+ childOrderSpillter[1]);
				childOrder.put(childOrderSpillter[0], Integer.parseInt(childOrderSpillter[1]));
			}
			for (String onlyChild: sortByValue(true, childOrder).keySet())  
			{  
				if (parentChild.get(name)!=null) {
					String a=parentChild.get(name);
					parentChild.put(name,a+","+onlyChild);
				} else {
					parentChild.put(name,onlyChild);
				}

			}
			childOrder=new LinkedHashMap<String, Integer>();
		} 

		System.err.println("navigationParentLabelWithChildSorted : "+parentChild);
		return parentChild;

	}

	
	/**
	 * @param projectName
	 * @param searchValue
	 * @param action
	 * @param timeOut
	 * @return true if able to click on Navatar Edge
	 */
	public boolean clickOnNavatarEdgeLinkHomePage(String projectName,String searchValue,action action,int timeOut) {
		boolean flag=false;;
		verifyingNavigationMenuLink(projectName, null, null, action, timeOut);
		String xpath = "//div[@class='flexipagePage']//span[text()='"+searchValue+"']";
		WebElement ele = FindElement(driver, xpath, searchValue, action, timeOut);
		WebElement ele1=null;
		if (click(driver, ele, searchValue, action)) {
			log(LogStatus.INFO, "able to click on "+searchValue, YesNo.No);	
			flag=true;
			xpath = "//div[@class='flexipagePage']//h2[text()='"+searchValue+"']";
			ele = FindElement(driver, xpath, searchValue+" header", action, timeOut);
			ele = isDisplayed(driver, ele, "Visibility", timeOut, searchValue+" Header");
			
			xpath = "//div[@class='flexipagePage']//h2[text()='"+searchValue+"']/..//preceding-sibling::div";
			ele1 = FindElement(driver, xpath, searchValue+" image", action, timeOut);
			ele1 = isDisplayed(driver, ele, "Visibility", timeOut, searchValue+" Image");
			
			if (ele!=null && ele1!=null && getNavatarQuickLinkMinimize_Lighting(projectName, timeOut)!=null) {
				log(LogStatus.INFO, "Image , Header and minimize icon Verified after click on "+searchValue, YesNo.No);	
				flag=true;
			} else {
				log(LogStatus.ERROR, "Image , Header and minimize icon Not Verified after click on "+searchValue, YesNo.No);
				sa.assertTrue(false, "Image , Header and minimize icon Not Verified after click on "+searchValue);
			}
			
		} else {
			log(LogStatus.ERROR, "Not able to click on "+searchValue, YesNo.No);
		}
		return flag;
	}

	
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param onlyParent
	 * @param parentwithChild
	 * @param action
	 * @param timeOut verifying Navigation Menu Link 
	 */
	public void verifyingNavigationMenuLink(String projectName,Map<String, Integer> onlyParent,Map<String, String> parentwithChild,action action,int timeOut) {
		boolean flag=false;
		WebElement ele ;
		int k=0;
		String xpath = "";
		String[] childs = null;
		ThreadSleep(20000);
		if (onlyParent!=null) {
			Set<String> navigationParentLabel = onlyParent.keySet();
			System.err.println("navigationParentLabel>>>>>>>>> : "+navigationParentLabel);
			for (Iterator iterator = navigationParentLabel.iterator(); iterator.hasNext();) {
				String parentLabel = (String) iterator.next();
				System.err.println("parentLabel>>>>>>>>> : "+parentLabel);
				if (k==0) {
					xpath = "//div[contains(@id,'treeview')]//*//*[text()='"+parentLabel+"']";
					ele = FindElement(driver, xpath, parentLabel, action, timeOut);
					if (ele!=null) {
						log(LogStatus.INFO, "1st Navigation Link Find "+parentLabel, YesNo.No);
					} else {
						log(LogStatus.ERROR, "1st Navigation Link not found "+parentLabel+" so cannot verified navigation order : "+navigationParentLabel, YesNo.Yes);
						sa.assertTrue(false,"1st Navigation Link not found "+parentLabel+" so cannot verified navigation order : "+navigationParentLabel);
						break;
					}

				} else {
					xpath = xpath+"/../following-sibling::*//*[text()='"+parentLabel+"']";
					ele = FindElement(driver, xpath, parentLabel, action, timeOut);
					if (ele!=null) {
						log(LogStatus.INFO, "Navigation Link Find "+parentLabel, YesNo.No);
					} else {
						log(LogStatus.ERROR, "Navigation Link not found "+parentLabel+" so cannot verified navigation order : "+navigationParentLabel, YesNo.Yes);
						sa.assertTrue(false,"Navigation Link not found "+parentLabel+" so cannot verified navigation order : "+navigationParentLabel);
						//	break;
					}

				}
				k++;
			}
		}
		if (parentwithChild!=null) {

			for ( String parent : parentwithChild.keySet() ) {
				System.out.println(">>>> "+ parent );
				System.out.println(">>>>value "+ parentwithChild.get(parent) );
				xpath="";
				childs=parentwithChild.get(parent).split(commaSP);
				for (int i = 0; i < childs.length; i++) {
					xpath = "//div[contains(@id,'treeview')]//*//*[text()='"+childs[i]+"']";
					ele = FindElement(driver, xpath, childs[i], action, timeOut);
					ele = isDisplayed(driver, ele, "Visibility", timeOut, childs[i]);
					if (ele==null) {
						log(LogStatus.INFO, "Navigation Link not visible "+childs[i], YesNo.No);
					} else {
						log(LogStatus.ERROR, "Navigation Link found "+childs[i]+" hence it is not under parent : "+parent, YesNo.Yes);
						sa.assertTrue(false,"Navigation Link found "+childs[i]+" hence it is not under parent : "+parent);;
					}
				}
				xpath = "//div[contains(@id,'treeview')]//*//*[text()='"+parent+"']";
				ele = FindElement(driver, xpath, parent, action, timeOut);
				if (click(driver, ele, parent, action)) {
					log(LogStatus.INFO, "Able to Click on Navigation Label : "+parent+" so going to check child label : "+parentwithChild.get(parent), YesNo.No);
					ThreadSleep(2000);
					for (int i = 0; i < childs.length; i++) {
						xpath = xpath+"/../following-sibling::*//*[text()='"+childs[i]+"']";;
						ele = FindElement(driver, xpath, childs[i], action, timeOut);
						ele = isDisplayed(driver, ele, "Visibility", timeOut, childs[i]);
						if (ele!=null) {
							log(LogStatus.INFO, "Navigation Link found & visible "+childs[i]+" and under parent : "+parent, YesNo.No);
						} else {
							log(LogStatus.ERROR, "Navigation Link not found "+childs[i]+" hence it is not under parent : "+parent, YesNo.Yes);
							sa.assertTrue(false,"Navigation Link not found "+childs[i]+" hence it is not under parent : "+parent);
							//	break;
						}
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on Navigation Label : "+parent+" so cannot check child label : "+parentwithChild.get(parent), YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on Navigation Label : "+parent+" so cannot check child label : "+parentwithChild.get(parent));
				}


			}
		}

	}
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param navigationLabel
	 * @param action
	 * @param timeOut
	 * @return getNavigationLabel
	 */
	public WebElement getNavigationLabel(String projectName,String navigationLabel,action action,int timeOut) {
//		if (navigationLabel.contains("/")) {
//			int i=0;
//			String[] nb = navigationLabel.split("/");
//			for (i = 0; i < nb.length-1; i++) {
//				click(driver, getNavigationLabel(projectName, nb[i], action, timeOut), nb[i], action);
//			}
//			String xpath = "//div[@id='treeview12']//*//*[text()='"+nb[nb.length-1]+"']";
//			WebElement ele = FindElement(driver, xpath, nb[nb.length-1], action, timeOut);
//			return isDisplayed(driver, ele, "Visibility", timeOut, nb[nb.length-1]);
//		}else {
//
//			String xpath = "//div[@id='treeview12']//*//*[text()='"+navigationLabel+"']";
//			WebElement ele = FindElement(driver, xpath, navigationLabel, action, timeOut);
//			return isDisplayed(driver, ele, "Visibility", timeOut, navigationLabel);
//		}
		
		
		int i=0;
		String[] nb = navigationLabel.split("/");
		String xpath = "";
		WebElement ele=null;
		for (i = 0; i < nb.length; i++) {
			if (i==0) {
				xpath = "//div[contains(@id,'treeview')]//*//*[text()='"+nb[i]+"']";	
				ele = FindElement(driver, xpath, nb[i], action, timeOut);
				
			} else {
				xpath=xpath+"/../following-sibling::*/*[text()='"+nb[i]+"']";
			}
			if (nb.length>1 && i==nb.length-1) {
				click(driver,ele, nb[i], action);	
			}
		}
		ele = FindElement(driver, xpath, navigationLabel, action, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, navigationLabel);

	}
	
	
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param navigationLabel
	 * @param action
	 * @param timeOut
	 * @return getCrossButtonForNavigationLabelPopuP
	 */
	public WebElement getCrossButtonForNavigationLabelPopuP(String projectName,String navigationLabel,action action,int timeOut) {
		String xpath = "//h2[contains(text(),'New') and contains(text(),'"+navigationLabel+"')]/ancestor::div//following-sibling::button[@title='Close this window']";
		WebElement ele = FindElement(driver, xpath, navigationLabel, action, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, navigationLabel);
	}
	
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param navigationField
	 * @param action
	 * @param timeOut
	 * @return Navigation Field webElement
	 */
	public WebElement getNavigationField(String projectName,String navigationField,action action,int timeOut) {
		navigationField=navigationField.replace("_", " ");
		if (navigationField.equalsIgnoreCase(CSVLabel.Parent.toString())) {
			String xpath = "//*[text()='Parent']/following-sibling::*//*//button";
			WebElement ele = FindElement(driver, xpath, navigationField, action, 5);	
			click(driver, ele, "PARENT Cross icon", action.BOOLEAN);
		}
		String xpath = "//*[text()='"+navigationField+"']/following-sibling::div//input";
		WebElement ele = FindElement(driver, xpath, navigationField, action, timeOut);
		scrollDownThroughWebelement(driver, ele, navigationField);
		return isDisplayed(driver, ele, "Visibility", timeOut, navigationField);
	}
	
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param allReportorDashboard
	 * @param action
	 * @param timeOut
	 * @return All list view with report
	 */
	public WebElement getAll(String projectName,String allReportorDashboard,action action,int timeOut) {
		String all ="All "+allReportorDashboard;
		String xpath = "//h2[text()='"+allReportorDashboard+"']/following-sibling::*//*[text()='"+all+"']";
		WebElement ele = FindElement(driver, xpath, allReportorDashboard, action, timeOut);
		scrollDownThroughWebelement(driver, ele, allReportorDashboard);
		return isDisplayed(driver, ele, "Visibility", timeOut, allReportorDashboard);
	}
	
	
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @return true if able to click on show more drop down value
	 */
	public boolean clickOnShowMoreDropdownOnly(String projectName) {
		String xpath = "";int i =1;
		WebElement ele=null;
		boolean flag = true;
		refresh(driver);
		ThreadSleep(2000);
		xpath="(//span[contains(text(),'more actions')])[1]/..";
		ele=FindElement(driver, xpath, "show more action down arrow", action.SCROLLANDBOOLEAN, 30);
		if(click(driver, ele, "show more action on ", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "clicked on show more actions icon", YesNo.No);
		}
		else {
			log(LogStatus.FAIL, "cannot click on show more actions icon", YesNo.Yes);
			flag = false;
		}
		return flag;
	}
	
	public WebElement getItemInList(String projectName,String itemName,action action,int timeOut) {
		String xpath = "//*[@title='"+itemName+"']//strong[text()='"+itemName.split(" ")[0]+"']";
		WebElement ele = FindElement(driver, xpath, itemName, action, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, itemName);
	}
	
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param appName
	 * @param action
	 * @param timeOut
	 * @return true if able to add App to Page/tab
	 */
	public boolean addingPageToApp(String projectName,String appName,action action,int timeOut) {
		String xpath="";
		boolean flag=false;
		if (click(driver, getLightningExperienceTab(projectName, timeOut),"lightning Experience Tab", action.BOOLEAN)) {
			log(LogStatus.INFO,"Click on lightning Experience Tab",YesNo.No);
			ThreadSleep(5000);
			xpath = "//div//li/a[text()='"+appName+"']";
			List<WebElement> eleList = FindElements(driver, xpath, appName);
			for (WebElement webElement : eleList) {
				
				if (click(driver, webElement,appName, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on : "+appName,YesNo.No);
					xpath="//*[contains(text(),'Add page to app')]";
					List<WebElement> eleList1 = FindElements(driver, xpath, "Add page to app");
					for (WebElement webElement2 : eleList1) {
						webElement2=isDisplayed(driver, webElement2, "Visibility", timeOut, "Add page to app");
						
						if (webElement2!=null) {
							if (click(driver, webElement2,appName, action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"Click on Add page to app : "+appName,YesNo.No);
								flag=true;
								ThreadSleep(4000);
							} else {
								sa.assertTrue(false, "Not Able to Click on Add page to app : "+appName);
								log(LogStatus.FAIL,"Not Able to Click on Add page to app : "+appName,YesNo.Yes);
							}
						} else {

						}
					}
							
				} else {
					sa.assertTrue(false, "Not Able to Click on : "+appName);
					log(LogStatus.FAIL,"Not Able to Click on : "+appName,YesNo.Yes);
				}
			}
			
		} else {
			sa.assertTrue(false, "Not Able to Click on lightning Experience Tab");
			log(LogStatus.FAIL,"Not Able to Click on lightning Experience Tab",YesNo.Yes);
		}
		return flag;
	}
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param navigationField
	 * @param action
	 * @param timeOut
	 * @return Navigation Field webElement
	 */
	public WebElement getNavigationField1(String projectName,String navigationField,action action,int timeOut) {
		navigationField=navigationField.replace("_", " ");
		String xpath = "//*[text()='"+navigationField+"']/following-sibling::div//input";
		WebElement ele = FindElement(driver, xpath, navigationField, action, timeOut);
		scrollDownThroughWebelement(driver, ele, navigationField);
		return isDisplayed(driver, ele, "Visibility", timeOut, navigationField);
	}
	
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param navigationFieldWithValues
	 * @param action
	 * @param timeOut
	 * method ebnter valu on navigation popup
	 */
	public void enteringValueForNavigation(String projectName,String[][] navigationFieldWithValues,action action,int timeOut) {
		String navigationField;
		String navigationvalue;
		WebElement ele;
		for (String[] navigationFieldAndvalue : navigationFieldWithValues) {
			navigationField=navigationFieldAndvalue[0];
			navigationvalue = navigationFieldAndvalue[1];
			ele =getNavigationField(projectName, navigationField, action, 20);

			if (navigationField.equalsIgnoreCase(CSVLabel.Navigation_Type.toString())) {

				if (click(driver, getNavigationTypeLabel(projectName, timeOut), navigationField, action.SCROLLANDBOOLEAN)) {
					ThreadSleep(2000);
					appLog.error("Clicked on Deal stage");

					String xpath = "//span[@title='"+navigationvalue+"']";
					ele = FindElement(driver,xpath, navigationvalue,action.SCROLLANDBOOLEAN, timeOut);
					ThreadSleep(2000);
					if (click(driver, ele, navigationvalue, action.SCROLLANDBOOLEAN)) {
						appLog.info("Selected navigation type : "+navigationvalue);
					} else {
						appLog.error("Not able to Select on navigation type : "+navigationvalue);
					}

				} else {
					appLog.error("Not able to Click on Deal stage : ");
				}	
			}else{
				if (sendKeys(driver, ele, navigationvalue, navigationField, action)) {
					log(LogStatus.INFO, "Able to enter "+navigationField, YesNo.No);

					if (navigationField.equalsIgnoreCase(CSVLabel.Parent.toString())) {
						ThreadSleep(10000);
						if (click(driver,getItemInList(projectName, navigationvalue, action.BOOLEAN, 20),
								navigationvalue + "   :  Parent Name", action.BOOLEAN)) {
							log(LogStatus.INFO, navigationvalue+" is available", YesNo.No);
						} else {
							log(LogStatus.ERROR, navigationvalue+" is not available", YesNo.Yes);
							sa.assertTrue(false, navigationvalue+" is not available");

						}	
					}

				} else {
					log(LogStatus.ERROR, "Not Able to enter "+navigationField, YesNo.Yes);
					sa.assertTrue(false,"Not Able to enter "+navigationField);
				}	
			}

		}

	}
	
	
	/**
	 * @return the radioButtonforNewInstitution
	 */
	public WebElement getRadioButtonforRecordTypeAtAccount(String recordType,int timeOut) {
		String xpath="//span[text()='"+recordType+"']/preceding-sibling::input";
		WebElement ele = null;
		ele=FindElement(driver, xpath, "radio button of record type "+recordType, action.SCROLLANDBOOLEAN,timeOut);
		return isDisplayed(driver,ele,"visibility",timeOut,"radio button of record type "+recordType);
	}
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param labelValue
	 * @param labelWithValue
	 * @param timeOut
	 * @return true if value entered & save on Navigation PopUp
	 */
	public boolean enterValueOnEditPopUpForNavigationTab(String projectName,String labelValue,String[][] labelWithValue,int timeOut) {
		String navigationTab="Navigation";
		WebElement ele;
		String label;
		String value;
		boolean flag=false;
		if (clickOnTab(projectName, navigationTab)) {
			log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);
			if ( clickOnAlreadyCreatedItem(projectName, labelValue, true, 15)) {
				log(LogStatus.INFO,"Item found: "+labelValue+" on Tab : "+navigationTab, YesNo.No);
				clickOnShowMoreDropdownOnly(projectName);
				ele =  actionDropdownElement(projectName, ShowMoreActionDropDownList.Edit, 10);
				if (click(driver, ele, ShowMoreActionDropDownList.Edit.toString(), action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to Click on Edit Button : "+labelValue, YesNo.No);
					for (String[] lv : labelWithValue) {
						label=lv[0];
						value=lv[1];
						ele =  getNavigationField(projectName, label, action.BOOLEAN, 20);
						try {
							ele.clear();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						ThreadSleep(500);
						if (sendKeys(driver, ele, value, label, action.BOOLEAN)) {
							log(LogStatus.INFO, "Able to enter "+label, YesNo.No);
							ThreadSleep(500);
							flag=true;
							
							if (label.equalsIgnoreCase(CSVLabel.Parent.toString()) && !value.isEmpty() && !value.equals("")) {
								ThreadSleep(10000);
								if (click(driver,getItemInList(projectName, value, action.BOOLEAN, 20),
										value + "   :  Parent Name", action.BOOLEAN)) {
									log(LogStatus.INFO, value+" is available", YesNo.No);
								} else {
									log(LogStatus.ERROR, value+" is not available", YesNo.Yes);
									sa.assertTrue(false, value+" is not available");

								}	
							}
							
						} else {
							log(LogStatus.ERROR, "Not Able to enter "+value+" to label "+label, YesNo.Yes);
							sa.assertTrue(false,"Not Able to enter "+value+" to label "+label);
						}
					}
					if (click(driver,  getCustomTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.ERROR, "Click on save Button : "+labelValue, YesNo.No);
						ThreadSleep(5000);
						flag=true;
					} else {
						log(LogStatus.ERROR, "Not Able to Click on save Button : "+labelValue, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on save Button : "+labelValue);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Edit Button : "+labelValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on Edit Button : "+labelValue);
				}
			}else {

				log(LogStatus.ERROR,"Item not found: "+labelValue+" on Tab : "+navigationTab, YesNo.Yes);
				sa.assertTrue(false,"Item not found: "+labelValue+" on Tab : "+navigationTab);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on Tab : "+navigationTab, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on Tab : "+navigationTab);
		}
		return flag;
	}
	
	/**
	 * @author Azhar Alam
	 * @param recordType
	 * @param desc
	 * @param timeOut
	 * @return record Type With Description webElement
	 */
	public WebElement getrecordTypeWithDescription(String recordType,String desc,int timeOut) {
		String xpath ="//*[text()='"+recordType+"']/*[text()='"+desc+"']";
		WebElement ele = FindElement(driver, xpath, recordType+" "+desc, action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, recordType+" "+desc);
	}
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param labelWithValue
	 * @param timeOut
	 * @return true if successfully created navigation item
	 */
	public boolean createNavigationItem(String projectName,String[][] labelWithValue,int timeOut) {
		String navigationTab="Navigation";
		boolean flag=false;
		if (clickOnTab(projectName, navigationTab)) {
			log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);
			if(clickUsingJavaScript(driver, getNewButton(projectName, 10), "new button")) {
				log(LogStatus.INFO, "Click on new button going to create ", YesNo.No);
				enteringValueForNavigation(projectName, labelWithValue, action.BOOLEAN, timeOut);
				if (click(driver,  getNavigationTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.ERROR, "Click on save Button ", YesNo.No);
					ThreadSleep(5000);
					flag=true;
				} else {
					log(LogStatus.ERROR, "Not Able to Click on save Button ", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on save Button ");
				}
				
			}else {
				log(LogStatus.ERROR, "Not Able to Click on new button so cannot create", YesNo.Yes);
				sa.assertTrue(false, "Not Able to Click on new button so cannot create");

			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on Tab : "+navigationTab, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on Tab : "+navigationTab);
		}
		return flag;
	}
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param parentLabel
	 * @param childNavigationLabel
	 * @param action
	 * @param timeOut
	 * @return get Navigation Label
	 */
	public WebElement getNavigationLabel(String projectName,String parentLabel,String childNavigationLabel,action action,int timeOut) {
		click(driver, getNavigationLabel(projectName, parentLabel, action, timeOut), parentLabel, action);
		String xpath = "//div[contains(@id,'treeview')]//*//*[text()='"+parentLabel+"']";
		int i=0;
		String[] nb = childNavigationLabel.split("<break>");
		for (i = 0; i < nb.length; i++) {
			xpath=xpath+"/../following-sibling::*/*[text()='"+nb[i]+"']";
		}
		WebElement ele = FindElement(driver, xpath, parentLabel+" "+childNavigationLabel, action, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, parentLabel+" "+childNavigationLabel);

	}
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param action
	 * @param timeOut
	 * @return PageDoesNotExist msg element
	 */
	public WebElement getPageDoesNotExist(String projectName,action action,int timeOut) {
		String msg = NavatarSetUpPageErrorMessage.PageDoesExist;
		String xpath = "//h2[contains(text(),'"+msg+"')]/ancestor::div//following-sibling::button[@title='Close this window']";
		WebElement ele = FindElement(driver, xpath, msg, action, timeOut);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, msg);
		if (ele!=null) {
			msg = NavatarSetUpPageErrorMessage.EnterAValidURLAndTryAgain;
			 xpath = "//div[contains(text(),'"+msg+"')]/ancestor::div//following-sibling::button[@title='Close this window']";
			 ele = FindElement(driver, xpath, msg, action, timeOut);
			ele = isDisplayed(driver, ele, "Visibility", timeOut, msg);
		} 
		return ele;
	}
	
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param tabName
	 * @param action
	 * @param timeOut
	 * @return coverage item as tab after click on navigation menu item
	 */
	public WebElement getCoverageTabAfterClick(String projectName,String tabName,action action,int timeOut) {
		ThreadSleep(5000);
		String xpath = "//a[contains(@href,'lightning') and contains(@title,'Coverages') or contains(@title,'"+tabName+"')]/span/..";
		WebElement ele = FindElement(driver, xpath,tabName, action.SCROLLANDBOOLEAN,timeOut);
		ele = isDisplayed(driver,ele,"visibility", timeOut, tabName);
		
		String url=getURL(driver, 10);
		String coverage="coverage";
		
		if (url.contains(coverage) || url.contains("Coverage")) {
			log(LogStatus.INFO, "Coverage verified : "+tabName, YesNo.No);
		} else {
			log(LogStatus.ERROR, "Coverage not verified : "+tabName, YesNo.Yes);
			sa.assertTrue(false,"Coverage not verified : "+tabName);
		}
		
		if (getPageDoesNotExist(projectName, action.BOOLEAN, 5)==null) {
			
		} else {
			log(LogStatus.ERROR, "Coverage not verified : "+tabName, YesNo.Yes);
			sa.assertTrue(false,"Coverage not verified : "+tabName);
		}
		return ele;
	}
	
	/**
	 * @param projectName
	 * @param searchValue
	 * @param action
	 * @param timeOut
	 * @return true if able to click on Navatar Edge
	 */
	public boolean clickOnNavatarEdgeLink(String projectName,String searchValue,action action,int timeOut) {
		boolean flag=false;;
		verifyingNavigationMenuLink(projectName, null, null, action, timeOut);
		String xpath = "//div[@class='flexipagePage']//span[text()='"+searchValue+"']";
		WebElement ele = FindElement(driver, xpath, searchValue, action, timeOut);
		if (click(driver, ele, searchValue, action)) {
			log(LogStatus.INFO, "able to click on "+searchValue, YesNo.No);	
			flag=true;
		} else {
			log(LogStatus.ERROR, "Not able to click on "+searchValue, YesNo.No);
		}
		return flag;
	}

	
	
	
}
