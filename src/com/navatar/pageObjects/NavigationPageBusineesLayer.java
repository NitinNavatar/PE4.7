package com.navatar.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.navatar.generic.EnumConstants.AddProspectsTab;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageName;
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

}
