package com.navatar.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.navatar.generic.EnumConstants.AddProspectsTab;
import com.navatar.generic.EnumConstants.AppSetting;
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

	public static Map<String,Integer> navigationParentLabelWithOrder(List<String> csvRecords){
		Map<String, Integer> map = new LinkedHashMap<String, Integer>(); 
		String[] csvRecords2=null;
		String navigationLabel2=null;
		String parent2="";
		String order2="";

		for (String abc2 : csvRecords) {
			csvRecords2=abc2.split(commaSP);
			navigationLabel2=csvRecords2[0].trim();
			parent2=csvRecords2[2].trim();
			order2=csvRecords2[1].trim();
			int i=0;

			if (parent2.isEmpty() || parent2.equals(" ") ||parent2.equals("")) {
				//System.err.println("inside parent blank");	
				//System.out.println("ye wala "+navigationLabel2+" "+order2+" "+parent2);
				if (order2.isEmpty() || order2.equals(" ") ||order2.equals("")) {
					i=1000;
				}else {
					i=Integer.parseInt(order2);

				}
				//System.err.println(navigationLabel2+"<><>"+i+" >>> "+parent2);
				//	System.err.println("outside parent blank");	
				map.put(navigationLabel2, i);

			}


		}
		return map;

	}

	public static Map<String,String> navigationParentLabelWithChildAndOrder(List<String> csvRecords){
		Map<String,String> childMap = new LinkedHashMap<String,String>(); 
		String[] csvRecords2=null;
		String navigationLabel2=null;
		String parent2="";
		String order2="";

		for (String abc2 : csvRecords) {
			csvRecords2=abc2.split(commaSP);
			navigationLabel2=csvRecords2[0].trim();
			parent2=csvRecords2[2].trim();
			order2=csvRecords2[1].trim();
			int i=0;

			if (parent2.isEmpty() || parent2.equals(" ") ||parent2.equals("")) {


			}else {

				//	System.out.println("ye wala11 "+navigationLabel2+" "+order2+" "+parent2);
				if (order2.isEmpty() || order2.equals(" ") ||order2.equals("")) {
					i=1000;
				}else {
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
		return childMap;

	}

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
		return sortedMap;
	}  

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

		return parentChild;

	}

	/**
	 * @return the editPageSeachTextBox
	 */
	public boolean clickOnNavatarEdgeLinkHomePage(String projectName,String searchValue,action action,int timeOut) {
		boolean flag=false;;
		verifyingNavigationMenuLink(projectName, null, null, action, timeOut);
		String xpath = "//div[@class='flexipagePage']//span[text()='"+searchValue+"']";
		WebElement ele = FindElement(driver, xpath, searchValue, action, timeOut);
		if (click(driver, ele, searchValue, action)) {
			log(LogStatus.INFO, "able to click on "+searchValue, YesNo.No);	
			xpath = "//div[@class='flexipagePage']//h2[text()='"+searchValue+"']";
			ele = FindElement(driver, xpath, searchValue, action, timeOut);
			ele = isDisplayed(driver, ele, "Visibility", timeOut, searchValue+" Header");
			if (ele!=null && getNavatarQuickLinkMinimize_Lighting(projectName, timeOut)!=null) {
				log(LogStatus.INFO, "Header and minimize icon Verified after click on "+searchValue, YesNo.No);	
				flag=true;
			} else {
				log(LogStatus.ERROR, "Header and minimize icon Not Verified after click on "+searchValue, YesNo.No);	
			}
			
		} else {
			log(LogStatus.ERROR, "Not able to click on "+searchValue, YesNo.No);
		}
		return flag;
	}

	
	/**
	 * @return the editPageSeachTextBox
	 */
	public void verifyingNavigationMenuLink(String projectName,Map<String, Integer> onlyParent,Map<String, String> parentwithChild,action action,int timeOut) {
		boolean flag=false;
		WebElement ele ;
		int k=0;
		String xpath = "";
		String[] childs = null;

		if (onlyParent!=null) {
			Set<String> navigationParentLabel = onlyParent.keySet();
			System.err.println("navigationParentLabel>>>>>>>>> : "+navigationParentLabel);
			for (Iterator iterator = navigationParentLabel.iterator(); iterator.hasNext();) {
				String parentLabel = (String) iterator.next();
				System.err.println("parentLabel>>>>>>>>> : "+parentLabel);
				if (k==0) {
					xpath = "//div[@id='treeview12']//*//*[text()='"+parentLabel+"']";
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
					xpath = "//div[@id='treeview12']//*//*[text()='"+childs[i]+"']";
					ele = FindElement(driver, xpath, childs[i], action, timeOut);
					ele = isDisplayed(driver, ele, "Visibility", timeOut, childs[i]);
					if (ele==null) {
						log(LogStatus.INFO, "Navigation Link not visible "+childs[i], YesNo.No);
					} else {
						log(LogStatus.ERROR, "Navigation Link found "+childs[i]+" hence it is not under parent : "+parent, YesNo.Yes);
						sa.assertTrue(false,"Navigation Link found "+childs[i]+" hence it is not under parent : "+parent);;
					}
				}
				xpath = "//div[@id='treeview12']//*//*[text()='"+parent+"']";
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
	
	public WebElement getNavigationLabel(String projectName,String navigationLabel,action action,int timeOut) {
		String xpath = "//div[@id='treeview12']//*//*[text()='"+navigationLabel+"']";
		WebElement ele = FindElement(driver, xpath, navigationLabel, action, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, navigationLabel);
	}
	
	public WebElement getCrossButtonForNavigationLabelPopuP(String projectName,String navigationLabel,action action,int timeOut) {
		String xpath = "//h2[contains(text(),'New') and contains(text(),'"+navigationLabel+"')]/ancestor::div//following-sibling::button[@title='Close this window']";
		WebElement ele = FindElement(driver, xpath, navigationLabel, action, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, navigationLabel);
	}
	
	
	public WebElement getNavigationField(String projectName,String navigationField,action action,int timeOut) {
		navigationField=navigationField.replace("_", " ");
		String xpath = "//*[text()='"+navigationField+"']/following-sibling::div//input";
		WebElement ele = FindElement(driver, xpath, navigationField, action, timeOut);
		scrollDownThroughWebelement(driver, ele, navigationField);
		return isDisplayed(driver, ele, "Visibility", timeOut, navigationField);
	}
	
	
	public WebElement getAll(String projectName,String allReportorDashboard,action action,int timeOut) {
		String all ="All "+allReportorDashboard;
		String xpath = "//h2[text()='"+allReportorDashboard+"']/following-sibling::*//*[text()='"+all+"']";
		WebElement ele = FindElement(driver, xpath, allReportorDashboard, action, timeOut);
		scrollDownThroughWebelement(driver, ele, allReportorDashboard);
		return isDisplayed(driver, ele, "Visibility", timeOut, allReportorDashboard);
	}
	
	public WebElement actionDropdownElement(String projectName, ShowMoreActionDropDownList smaddl, int timeOut) {
		String actionDropDown = smaddl.toString().replace("_", " ");
		String xpath ="//span[text()='"+actionDropDown+"']";
		xpath="//*[@name='"+actionDropDown+"' or text()='"+actionDropDown+"']";
		return isDisplayed(driver, FindElement(driver, xpath, "show more action down arrow", action.SCROLLANDBOOLEAN, 10), "visibility", timeOut, actionDropDown);
	}
	
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
}
