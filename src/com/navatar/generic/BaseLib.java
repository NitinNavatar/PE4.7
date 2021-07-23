package com.navatar.generic;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumDriverService;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;


import static com.navatar.generic.CommonLib.*;
import com.relevantcodes.extentreports.ExtentReports;

/**
 * 
 * @author Ankur Rana
 * @description Browser launch is controlled by this class.
 */

public class BaseLib extends AppListeners {

	public static WebDriver edriver;
	public static EventFiringWebDriver driver;
	public AppListeners testListner;
	public static SoftAssert sa=new SoftAssert();
	public static boolean PublicFlag = true;
	public CommonVariables cv = null;//common variable reference
	public SmokeCommonVariables scv = null;//common variable reference
	public static String downloadedFilePath=System.getProperty("user.dir")+"\\DownloadedFiles";
	public static String testCasesFilePath = System.getProperty("user.dir")+"/TestCases.xlsx";
	public static String taskWatchlistFilePath = System.getProperty("user.dir")+"/TaskWatchlist.xlsx";
	public static String phase1DataSheetFilePath = System.getProperty("user.dir")+"/Phase1DataSheet.xlsx";
	public static WiniumDriver dDriver = null;
	public static	String breakSP = "<break>";
	public static	String columnSP = "<column>";
	public static String commaSP = ",";
	public static	String colonSP = ":";
	public static String emptyString="";
	
	@BeforeSuite
	public void reportConfig(){
		DateFormat dateFormat = new SimpleDateFormat("yy_MM_dd_hh_mm_ss");
		Date date = new Date();
		extentReport = new ExtentReports(
				System.getProperty("user.dir") + "/Reports/ExtentReports/ExtentLog" + dateFormat.format(date) + ".html",
				true);
		try {
			extentReport.addSystemInfo("Host Name", InetAddress.getLocalHost().getHostName());
			extentReport.addSystemInfo("User Name", System.getProperty("user.name"));
			extentReport.addSystemInfo("Environment", ExcelUtils.readDataFromPropertyFile("Environment"));
			extentReport.addSystemInfo("Mode", ExcelUtils.readDataFromPropertyFile("Mode"));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		extentReport.loadConfig(new File(System.getProperty("user.dir") + "\\ConfigFiles\\extent-config.xml"));
	}
	
	@Parameters(value = "browser")
	@BeforeClass
	public void config(String browserName){
		if (browserName.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\exefiles\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			String OsName= System.getProperty("os.name");
			options.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
			options.addArguments("disable-infobars");
			options.addArguments("--disable-notifications");
			options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			options.setExperimentalOption("useAutomationExtension", false);
			options.addArguments("start-maximized");
			
			System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
		    Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.default_content_settings.popups", 0);
			prefs.put("download.default_directory", downloadedFilePath);
			prefs.put("profile.password_manager_enabled", false);
			options.setExperimentalOption("prefs", prefs);
//			DesiredCapabilities dp = new DesiredCapabilities();
//			dp.setCapability(ChromeOptions.CAPABILITY, options);
//			dp.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			edriver = new ChromeDriver(options);
			
			
		} else if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "\\exefiles\\geckodriver.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			FirefoxOptions options = new FirefoxOptions();
			options.addPreference("log", "{level: trace}");
			capabilities.setCapability("marionette", true);
			capabilities.setCapability("moz:firefoxOptions", options);
			edriver = new FirefoxDriver(capabilities);
		} else if (browserName.equalsIgnoreCase("IE Edge")) {
			 System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") +"\\exefiles\\MicrosoftWebDriver.exe");
			 edriver = new EdgeDriver();
		}
		else {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\exefiles\\chromedriver.exe");
			edriver = new ChromeDriver();
		}
		testListner = new AppListeners();
		driver = new EventFiringWebDriver(edriver);
		driver.register(testListner);
	}
	
	
	@BeforeMethod
	public void settingsBeforeTests() {
		
		//driver.get(ExcelUtils.readDataFromPropertyFile("URL"));
		cv=new CommonVariables(this);
		scv=new SmokeCommonVariables(this);
		//		sa=new SoftAssert();
		// driver.manage().window().maximize();
	}

	@AfterMethod
	public void settingsAfterTests(ITestResult result) {
		sa=new SoftAssert();
		if(result.getStatus()==2){
			if(ExcelUtils.readData("TestCases", excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Priority).equalsIgnoreCase("High")){
				appLog.fatal("Priority and dependency of this test case is high, So will not be able to continue with the execution.");
				String toValue = ExcelUtils.readDataFromPropertyFile("EmailIdForStatusMail");
				String[] attachment = {};
				String from = ExcelUtils.readDataFromPropertyFile("gmailUserName");
				String Password = ExcelUtils.readDataFromPropertyFile("gmailPassword");
				String userName = System.getProperty("user.name");
				String[] to = {from};
				if(!toValue.isEmpty()){
					to = new String[toValue.split(",").length];
					for(int i = 0; i < toValue.split(",").length; i++){
						to[i]=toValue.split(",")[i];
					}
					try {
						EmailLib.sendMail(from, Password, to, currentlyExecutingTC+" failed", "Dear "+userName+",\n\n"+currentlyExecutingTC+" is failed due to below reason: \n\n"+result.getThrowable().getMessage()+"\n\n\bNote: Priority and dependency of this test case is high, So will not be able to continue with the execution.", attachment);
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} 
				CommonLib.exit("Priority and dependency of "+currentlyExecutingTC+" testcase was high so cannot continue with the execution.");
			}
		}
	}
	
	@AfterClass
	public void closeBrowser(){
		driver.close();
		try {
			Process process = Runtime.getRuntime().exec(System.getProperty("user.dir")+"/killbrowser.bat");
			process.waitFor();
			CommonLib.ThreadSleep(1000);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@AfterSuite
	public void reportConfigEnd(){
		extentReport.flush();
		extentReport.close();
	}

}