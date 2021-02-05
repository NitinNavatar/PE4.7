package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.ExcelUtils.readAllDataForAColumn;
import static com.navatar.generic.SmokeCommonVariables.todaysDate;
import static com.navatar.pageObjects.NavigationPageBusineesLayer.navigationParentLabelWithOrder;
import static com.navatar.pageObjects.NavigationPageBusineesLayer.navigationParentLabelWithChildAndOrder;
import static com.navatar.pageObjects.NavigationPageBusineesLayer.navigationParentLabelWithChildSorted;
import static com.navatar.pageObjects.NavigationPageBusineesLayer.sortByValue;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;

import com.navatar.generic.ExcelUtils;
import com.navatar.pageObjects.NavigationPageBusineesLayer;

public class Demo {
	Scanner scn = new Scanner(System.in);
	static String breakSP = "<break>";
	String columnSP = "<column>";
	static String commaSP = ",";
	String colonSP = ":";
	String emptyString="";
	String navigationMenuName="Navigation Menu";
	public static  String NavigationMenuTestData_PEExcel = System.getProperty("user.dir")+"\\UploadFiles\\Module 3\\UploadCSV\\NavigationMenuTestData_PE - AllNew.csv";
	public static  String NavigationMenuTestData_PESheet = "asd";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Demo  dm = new Demo();
		Map<String, Integer> map = new LinkedHashMap<String, Integer>();  
		Map<String, String> childMap = new LinkedHashMap<String, String>();  
		System.err.println(NavigationMenuTestData_PEExcel+" >>>>>>>>>>>>><<<<<<<<<<<< "+NavigationMenuTestData_PESheet);
		List<String> csvRecords = ExcelUtils.readAllDataFromCSVFileIntoList(NavigationMenuTestData_PEExcel, false);
		
		for (String string : csvRecords) {
			System.err.println(string);	
		}
		Map<String, Integer> navigationParentLabelWithOrder = navigationParentLabelWithOrder(csvRecords);
		Set<String> abc = navigationParentLabelWithOrder.keySet();
		for (String string : abc) {
			System.err.println("Set : "+string);
		}
//		for (int i = 0; i < abc.size(); i++) {
//			//System.err.println("Set : "+abc);
//		}
		for ( String key : navigationParentLabelWithOrder.keySet() ) {
		    System.out.println(">>>> "+ key );
		    System.out.println(">>>>value "+ navigationParentLabelWithOrder.get(key) );
		}
		System.err.println(">>>>>>> : "+navigationParentLabelWithOrder.keySet());
		System.out.println(navigationParentLabelWithOrder);
		System.err.println(sortByValue(true, navigationParentLabelWithOrder));
		Map<String, String> navigationParentLabelWithChildAndOrder = navigationParentLabelWithChildAndOrder(csvRecords);
		System.out.println(navigationParentLabelWithChildAndOrder);
		Map<String, String> navigationParentLabelWithChildSorted = navigationParentLabelWithChildSorted(navigationParentLabelWithChildAndOrder);
		System.err.println(navigationParentLabelWithChildSorted);

	}


	//method to add elements in the HashMap  

	//sort elements by values  
	public static	Map<String, Integer> sortByValue(boolean order,Map<String, Integer> map)   
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
	//method for printing the elements  
	public void printMap(Map<String, Integer> map)   
	{  
		System.out.println("label\t order ");  
		for (Entry<String, Integer> entry : map.entrySet())   
		{  
			System.out.println(entry.getKey() +"\t"+entry.getValue());  
		}  
		System.out.println("\n");  
	}  
	
	public void printMap1(Map<Integer, String> map)   
	{  
		System.out.println("label\t order ");  
		for (Entry<Integer, String> entry : map.entrySet())   
		{  
			System.out.println(entry.getKey() +"\t"+entry.getValue());  
		}  
		System.out.println("\n");  
	} 

}
