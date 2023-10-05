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

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

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


	public static void main(String[] args) {
		String time=todaysDate;
		System.out.println("Date:"+new Date().toLocaleString());
		System.out.println("Date1:"+time);
	}
}
