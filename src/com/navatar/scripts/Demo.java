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

import io.github.bonigarcia.wdm.WebDriverManager;

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

	
	public static void test(String[] args) {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver= new ChromeDriver();
		driver.get("https://outlook.office365.com/");
			ThreadSleep(5000);		//marksmith@blackfordcapital-ng.com
		  //driver.findElement(By.xpath("//input[@name='loginfmt']")).sendKeys("atyaginavatar123@outlook.com");
		  driver.findElement(By.xpath("//input[@name='loginfmt']")).sendKeys("christinewilkinson@blackfordcapital-ng.com");

//		  driver.findElement(By.xpath("//input[@id='username']")).sendKeys("jaugust@railworks-ng.com");

		  ThreadSleep(2000);
		  WebElement submitButton= driver.findElement(By.xpath("//input[@id='idSIButton9']"));
				  submitButton .click();
			ThreadSleep(5000); //navatar@123
		 // driver.findElement(By.xpath("//input[@id='password']")).sendKeys("1Navatar11");
		  
		  driver.findElement(By.xpath("//input[@name='passwd']")).sendKeys("Navatar008");
		 // driver.findElement(By.xpath("//input[@name='passwd']")).sendKeys("Navatar@098");

		  ThreadSleep(2000);
		  //driver.findElement(By.xpath("//button[@id='submitBtn']")).click();
		  driver.findElement(By.xpath("//input[@id='idSIButton9']")).click();
		  ThreadSleep(2000);
		  submitButton= driver.findElement(By.xpath("//input[@id='idSIButton9']"));
		  if(submitButton.isDisplayed()) {
			  submitButton .click();
		  }
		  ThreadSleep(2000);
		  driver.findElement(By.xpath("//button[@aria-label='Calendar']")).click();

		  ThreadSleep(5000);
		  driver.findElement(By.xpath("//button[@id='NewEventButtonAnchor']")).click();
		  driver.findElement(By.xpath("//button[@id='NewEventButtonAnchor']")).click();
		  ThreadSleep(2000);
		  
		  // revenue grid button CRM : //button[contains(@aria-label,'Revenue Grid for Salesforce CRM')]
		  // open revenue grid : //button[@name='Open Revenue Grid']
		  // three line setting menu : //button[@title='Open the main menu'][@data-name='setting-button']
		  // Sync setting menu option : //div[@title='Open sync settings in browser']
		  //force sync button : //button[@title='Force Sync']
		  
		  // error/success message : //div[@class='ajs-message ajs-error ajs-visible']  need to check text with contains (Retry in 5 Minutes.)
		  //last session time :    //h3[@title='Last Session']/..//span
		  
		  
		  							
	}
	
	public static void main(String[] args) {
		String time=todaysDate;
		System.out.println("Date:"+new Date().toLocaleString());
		System.out.println("Date1:"+time);
	}
}
