package com.navatar.scripts;

import static com.navatar.generic.AppListeners.appLog;
import static com.navatar.generic.BaseLib.phase1DataSheetFilePath;
import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.FS_Object6;
import static com.navatar.generic.CommonVariables.SMOKDeal1InvestmentSize;
import static com.navatar.generic.CommonVariables.SMOKDeal2InvestmentSize;
import static com.navatar.generic.ExcelUtils.readAllDataForAColumn;
import static com.navatar.generic.SmokeCommonVariables.crmUser1FirstName;
import static com.navatar.generic.SmokeCommonVariables.todaysDate;
import static com.navatar.pageObjects.NavigationPageBusineesLayer.navigationParentLabelWithOrder;
import static com.navatar.pageObjects.NavigationPageBusineesLayer.navigationParentLabelWithChildAndOrder;
import static com.navatar.pageObjects.NavigationPageBusineesLayer.navigationParentLabelWithChildSorted;
import static com.navatar.pageObjects.NavigationPageBusineesLayer.sortByValue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

import org.openqa.selenium.WebElement;

import com.navatar.generic.BaseLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.NavigationPageBusineesLayer;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.relevantcodes.extentreports.LogStatus;

public class Demo extends BaseLib {
	Scanner scn = new Scanner(System.in);
	static String breakSP = "<break>";
	String columnSP = "<column>";
	static String commaSP = ",";
	String colonSP = ":";
	String emptyString="";
	String navigationMenuName="Navigation Menu";
	public static  String NavigationMenuTestData_PEExcel = System.getProperty("user.dir")+"\\UploadFiles\\Module 3\\UploadCSV\\NavigationMenuTestData_PE - AllNew.csv";
	public static  String NavigationMenuTestData_PESheet = "asd";
	public static Scanner x;

	public static void main(String[] args) throws ParseException {
		String[] toggles=null;
		String SmokeACDContact1FName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKEACDCON1", excelLabel.Contact_FirstName);
		String SmokeACDContact1LName=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKEACDCON1", excelLabel.Contact_LastName);
		String SmokeACDContact1Inst=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKEACDCON1", excelLabel.Institutions_Name);;
		String SmokeACDContact1EmailID=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKEACDCON1", excelLabel.Contact_EmailId);
		String SmokeACDContact1Title=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKEACDCON1", excelLabel.Title);
		String SmokeACDContact1Phone=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "SMOKEACDCON1", excelLabel.Phone);
		System.out.println(SmokeACDContact1FName);
		System.out.println(SmokeACDContact1LName);
		System.out.println(SmokeACDContact1Inst);
		System.out.println(SmokeACDContact1EmailID);
		System.out.println(SmokeACDContact1Title);
		System.out.println(SmokeACDContact1Phone);
	}

	public  void testDemo(){
		config(ExcelUtils.readDataFromPropertyFile("Browser"));
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		String passwordResetLink=null;
		try {
			passwordResetLink = new EmailLib().getResetPasswordLink("passwordreset",
					ExcelUtils.readDataFromPropertyFile("gmailUserName"),
					ExcelUtils.readDataFromPropertyFile("gmailPassword"));
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}
		appLog.info("ResetLinkIs: " + passwordResetLink);
		driver.get(passwordResetLink);
		if (lp.setNewPassword()) {
			appLog.info("Password is set successfully for CRM User1: " + crmUser1FirstName + " "   );
		} else {
			appLog.info("Password is not set for CRM User1: " + crmUser1FirstName + " " );
			sa.assertTrue(false, "Password is not set for CRM User1: " + crmUser1FirstName + " "  );
			log(LogStatus.ERROR, "Password is not set for CRM User1: " + crmUser1FirstName + " "  ,
					YesNo.Yes);
		}

	}
	public static String getDaysDifferenceOfTwoDates(String startDate, String endDate,String format)  {   
		long days_difference = 0;
		long time_difference =0;
        SimpleDateFormat obj = new SimpleDateFormat(format); 
        obj.format(startDate);
        obj.format(endDate);
        try {   
            Date date1 = obj.parse(startDate);   
            Date date2 = obj.parse(endDate);   
             time_difference = date2.getTime() - date1.getTime();  
             days_difference = (time_difference / (1000*60*60*24)) % 365;   
 
        }catch(ParseException excep){
        	  excep.printStackTrace(); 
        	
        }
        
        return String.valueOf(days_difference);
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
