package com.navatar.scripts;

import static com.navatar.generic.CommonLib.ThreadSleep;
import static com.navatar.generic.CommonLib.log;
import static com.navatar.generic.CommonLib.switchOnWindow;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.CommonVariables.mode;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;
import static com.navatar.generic.SmokeCommonVariables.crmUser1EmailID;

import java.util.List;

import org.openqa.selenium.remote.server.handler.SwitchToWindow;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.AppListeners;
import com.navatar.generic.BaseLib;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.navatar.generic.EnumConstants.ObjectName;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.object;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;

public class FSTG extends BaseLib {

	
	@Parameters("projectName")
	@Test
	public void fstg001_verifyContactTabFieldLabel(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp= new BasePageBusinessLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		//String a=SmokeDeal2Stage;
		if(lp.clickOnTab(projectName,mode,TabName.ContactTab)){
			log(LogStatus.PASS,	"click on tab:"+TabName.ContactTab.toString(), YesNo.No);
			
			if(bp.clickOnAlreadyCreatedItem(projectName,FSTG1RecordName , 30)) {
				
				log(LogStatus.PASS,	"click on creted item:"+FSTG1RecordName, YesNo.No);
				ThreadSleep(5000);
				List<String> result=bp.verifyDetailsTabFieldLabel(projectName, TabName.ContactTab, FSTG1RecordName, FSTG1SectionAndField, 30);
				if(result.isEmpty()) {
					log(LogStatus.FAIL,	"field label on obejct:"+TabName.ContactTab.toString()+" is verified", YesNo.Yes);
					
				}else {
					log(LogStatus.FAIL,	"field label:"+result+" on obejct:"+TabName.ContactTab.toString()+" is not verified", YesNo.Yes);
					sa.assertTrue(false,"field label:"+result+" on obejct:"+TabName.ContactTab.toString()+"  is not verified");
					
				}
			}else {
				
				AppListeners.appLog.info("Not able to open created item:"+FSTG1RecordName);
			}
		}else {
			AppListeners.appLog.info("Not able to click on tab:");
		}

		
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void fstg002_verifyInstituionTabFieldLabel(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp= new BasePageBusinessLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		//String a=SmokeDeal2Stage;
		if(lp.clickOnTab(projectName,mode,TabName.InstituitonsTab)){
			log(LogStatus.PASS,	"click on tab:"+TabName.InstituitonsTab.toString(), YesNo.No);
			
			if(bp.clickOnAlreadyCreatedItem(projectName, FSTG2RecordName, 30)) {
				
				log(LogStatus.PASS,	"click on creted item:"+FSTG2RecordName, YesNo.No);
				ThreadSleep(5000);
				List<String> result=bp.verifyDetailsTabFieldLabel(projectName, TabName.InstituitonsTab, FSTG2RecordName, FSTG2SectionAndField, 30);
				if(result.isEmpty()) {
					log(LogStatus.FAIL,	"field label on obejct: is verified", YesNo.Yes);
					
				}else {
					log(LogStatus.FAIL,	"field label:"+result+" on obejct:"+TabName.InstituitonsTab.toString()+" is not verified", YesNo.Yes);
					sa.assertTrue(false,"field label:"+result+" on obejct:"+TabName.InstituitonsTab.toString()+"  is not verified");
					
				}
			}else {
				
				AppListeners.appLog.info("Not able to open created item:"+FSTG2RecordName);
			}
		}else {
			AppListeners.appLog.info("Not able to click on tab:"+TabName.InstituitonsTab);
		}

		
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void fstg003_verifyDealTabFieldLabel(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp= new BasePageBusinessLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		//String a=SmokeDeal2Stage;
		if(lp.clickOnTab(projectName,mode,TabName.DealTab)){
			log(LogStatus.PASS,	"click on tab:"+TabName.DealTab.toString(), YesNo.No);
			
			if(bp.clickOnAlreadyCreatedItem(projectName, FSTG3RecordName, 30)) {
				
				log(LogStatus.PASS,	"click on creted item:"+FSTG3RecordName, YesNo.No);
				ThreadSleep(5000);
				List<String> result=bp.verifyDetailsTabFieldLabel(projectName, TabName.DealTab, FSTG3RecordName, FSTG3SectionAndField, 30);
				if(result.isEmpty()) {
					log(LogStatus.FAIL,	"field label on obejct: is verified", YesNo.Yes);
					
				}else {
					log(LogStatus.FAIL,	"field label:"+result+" on obejct:"+TabName.DealTab.toString()+" is not verified", YesNo.Yes);
					sa.assertTrue(false,"field label:"+result+" on obejct:"+TabName.DealTab.toString()+"  is not verified");
					
				}
			}else {
				
				AppListeners.appLog.info("Not able to open created item:"+FSTG3RecordName);
			}
		}else {
			AppListeners.appLog.info("Not able to click on tab:"+TabName.DealTab);
		}

		
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void fstg004_verifyFundFieldLabel(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp= new BasePageBusinessLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		//String a=SmokeDeal2Stage;
		if(lp.clickOnTab(projectName,mode,TabName.FundsTab)){
			log(LogStatus.PASS,	"click on tab:"+TabName.FundsTab.toString(), YesNo.No);
			
			if(bp.clickOnAlreadyCreatedItem(projectName, FSTG4RecordName, 30)) {
				
				log(LogStatus.PASS,	"click on creted item:"+FSTG4RecordName, YesNo.No);
				ThreadSleep(5000);
				List<String> result=bp.verifyDetailsTabFieldLabel(projectName, TabName.FundsTab, FSTG4RecordName, FSTG4SectionAndField, 30);
				if(result.isEmpty()) {
					log(LogStatus.FAIL,	"field label on obejct: is verified", YesNo.Yes);
					
				}else {
					log(LogStatus.FAIL,	"field label:"+result+" on obejct:"+TabName.FundsTab.toString()+" is not verified", YesNo.Yes);
					sa.assertTrue(false,"field label:"+result+" on obejct:"+TabName.FundsTab.toString()+"  is not verified");
					
				}
			}else {
				
				AppListeners.appLog.info("Not able to open created item:"+FSTG4RecordName);
			}
		}else {
			AppListeners.appLog.info("Not able to click on tab:"+TabName.FundsTab);
		}

		
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters("projectName")
	@Test
	public void fstg005_verifyListViewAndFilter(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp= new BasePageBusinessLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		
		
		//String a=SmokeDeal2Stage;
		if(bp.verifyObjectListViewAndFilterCondition(projectName,mode, FSTGListView2ObjectName, FSTGListView2ItemName, FSTGListView2FilterValue, 30).isEmpty()) {
			
			log(LogStatus.PASS, "Successfully veriefied list view and filter of object:"+FSTGListView2ObjectName, YesNo.No);
			
		}else {
			log(LogStatus.PASS, "not veriefied list view and filter of object:"+FSTGListView2ObjectName, YesNo.No);

			sa.assertTrue(false, "not veriefied list view and filter of object:"+FSTGListView2ObjectName);
		}
		
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void fstg006_verifyInstituionObjectPageLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID=null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Institution;

		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode, tab, ObjectFeatureName.pageLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnPageLayout(FSTG_PLField1PageLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All fields are verified of object "+tab.toString(),YesNo.No);

						
					}else {
						
						log(LogStatus.FAIL, "field are not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"field are not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
		}
		
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}
		
	@Parameters("projectName")
	@Test
	public void fstg007_verifyMarketingEventPageLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Marketing_Event;
		
		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode,  tab, ObjectFeatureName.pageLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnPageLayout(FSTG_PLField2PageLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All fields are verified of object: "+tab.toString(),YesNo.No);
						
						
					}else {
						
						log(LogStatus.FAIL, "field are not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"field are not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
			
		}
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters("projectName")
	@Test
	public void fstg008_verifyAffiliationPageLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Affiliation;
		
		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode,  tab, ObjectFeatureName.pageLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnPageLayout(FSTG_PLField3PageLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All fields are verified of object: "+tab.toString(),YesNo.No);
						
						
					}else {
						
						log(LogStatus.FAIL, "field are not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"field are not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
			
		}
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void fstg009_verifyAttendeePageLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Attendee;
		
		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode,  tab, ObjectFeatureName.pageLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnPageLayout(FSTG_PLField4PageLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All fields are verified of object: "+tab.toString(),YesNo.No);
						
						
					}else {
						
						log(LogStatus.FAIL, "field are not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"field are not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
			
		}
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters("projectName")
	@Test
	public void fstg010_verifyContactPageLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Contact;
		
		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode,  tab, ObjectFeatureName.pageLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnPageLayout(FSTG_PLField5PageLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All fields are verified of object: "+tab.toString(),YesNo.No);
						
						
					}else {
						
						log(LogStatus.FAIL, "field are not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"field are not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
			
		}
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void fstg011_verifyDealExpertPageLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Deal_Expert;
		
		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode,  tab, ObjectFeatureName.pageLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnPageLayout(FSTG_PLField6PageLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All fields are verified of object: "+tab.toString(),YesNo.No);
						
						
					}else {
						
						log(LogStatus.FAIL, "field are not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"field are not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
			
		}
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void fstg012_verifyDealTeamPageLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Deal_Team;
		
		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode,  tab, ObjectFeatureName.pageLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnPageLayout(FSTG_PLField7PageLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All fields are verified of object: "+tab.toString(),YesNo.No);
						
						
					}else {
						
						log(LogStatus.FAIL, "field are not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"field are not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
			
		}
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void fstg013_verifyFinancingPageLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Financing;
		
		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode,  tab, ObjectFeatureName.pageLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnPageLayout(FSTG_PLField8PageLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All fields are verified of object: "+tab.toString(),YesNo.No);
						
						
					}else {
						
						log(LogStatus.FAIL, "field are not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"field are not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
			
		}
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void fstg014_verifyFundraisingPageLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Fundraising;
		
		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode,  tab, ObjectFeatureName.pageLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnPageLayout(FSTG_PLField9PageLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All fields are verified of object: "+tab.toString(),YesNo.No);
						
						
					}else {
						
						log(LogStatus.FAIL, "field are not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"field are not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
			
		}
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters("projectName")
	@Test
	public void fstg015_verifyFundPageLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Fund;
		
		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode,  tab, ObjectFeatureName.pageLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnPageLayout(FSTG_PLField10PageLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All fields are verified of object: "+tab.toString(),YesNo.No);
						
						
					}else {
						
						log(LogStatus.FAIL, "field are not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"field are not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
			
		}
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters("projectName")
	@Test
	public void fstg016_verifyFundraisingContactPageLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Fundraising_Contact;
		
		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode,  tab, ObjectFeatureName.pageLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnPageLayout(FSTG_PLField11PageLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All fields are verified of object: "+tab.toString(),YesNo.No);
						
						
					}else {
						
						log(LogStatus.FAIL, "field are not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"field are not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
			
		}
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters("projectName")
	@Test
	public void fstg017_verifyNavigationPageLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Navigation;
		
		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode,  tab, ObjectFeatureName.pageLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnPageLayout(FSTG_PLField12PageLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All fields are verified of object: "+tab.toString(),YesNo.No);
						
						
					}else {
						
						log(LogStatus.FAIL, "field are not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"field are not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
			
		}
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters("projectName")
	@Test
	public void fstg018_verifyDealPageLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Pipeline;
		
		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode,  tab, ObjectFeatureName.pageLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnPageLayout(FSTG_PLField13PageLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All fields are verified of object: "+tab.toString(),YesNo.No);
						
						
					}else {
						
						log(LogStatus.FAIL, "field are not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"field are not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
			
		}
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void fstg019_verifyPipelineSnapshotPageLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Pipeline_Snapshot;
		
		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode,  tab, ObjectFeatureName.pageLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnPageLayout(FSTG_PLField14PageLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All fields are verified of object: "+tab.toString(),YesNo.No);
						
						
					}else {
						
						log(LogStatus.FAIL, "field are not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"field are not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
			
		}
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void fstg020_verifyRequestTrackerLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Request_Tracker;
		
		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode,  tab, ObjectFeatureName.pageLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnPageLayout(FSTG_PLField15PageLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All fields are verified of object: "+tab.toString(),YesNo.No);
						
						
					}else {
						
						log(LogStatus.FAIL, "field are not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"field are not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
			
		}
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void fstg021_verifyReviewPageLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Review;
		
		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode,  tab, ObjectFeatureName.pageLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnPageLayout(FSTG_PLField16PageLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All fields are verified of object: "+tab.toString(),YesNo.No);
						
						
					}else {
						
						log(LogStatus.FAIL, "field are not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"field are not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
			
		}
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void fstg022_verifySortableDataGridPageLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Sortable_Data_Grid;
		
		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode,  tab, ObjectFeatureName.pageLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnPageLayout(FSTG_PLField17PageLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All fields are verified of object: "+tab.toString(),YesNo.No);
						
						
					}else {
						
						log(LogStatus.FAIL, "field are not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"field are not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
			
		}
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void fstg023_verifySortableDataGridActionPageLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Sortable_Data_Grid_Action;
		
		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode,  tab, ObjectFeatureName.pageLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnPageLayout(FSTG_PLField18PageLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All fields are verified of object: "+tab.toString(),YesNo.No);
						
						
					}else {
						
						log(LogStatus.FAIL, "field are not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"field are not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
			
		}
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void fstg024_verifySortableDataGridFieldPageLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Sortable_Data_Grid_Field;
		
		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode,  tab, ObjectFeatureName.pageLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnPageLayout(FSTG_PLField19PageLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All fields are verified of object: "+tab.toString(),YesNo.No);
						
						
					}else {
						
						log(LogStatus.FAIL, "field are not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"field are not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
			
		}
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void fstg025_verifySortableDataGridPreferencePageLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Sortable_Data_Grid_Preference;
		
		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode,  tab, ObjectFeatureName.pageLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnPageLayout(FSTG_PLField20PageLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All fields are verified of object: "+tab.toString(),YesNo.No);
						
						
					}else {
						
						log(LogStatus.FAIL, "field are not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"field are not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
			
		}
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void fstg026_verifyTimeLogPageLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Time_Log;
		
		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode,  tab, ObjectFeatureName.pageLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnPageLayout(FSTG_PLField21PageLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All fields are verified of object: "+tab.toString(),YesNo.No);
						
						
					}else {
						
						log(LogStatus.FAIL, "field are not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"field are not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
			
		}
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void fstg027_verifyValuationPageLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Valuation;
		
		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode,  tab, ObjectFeatureName.pageLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnPageLayout(FSTG_PLField22PageLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All fields are verified of object: "+tab.toString(),YesNo.No);
						
						
					}else {
						
						log(LogStatus.FAIL, "field are not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"field are not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
			
		}
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void fstg028_verifyInstitutionCompactLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Institution;
		
		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode,  tab, ObjectFeatureName.compactLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnCompactLayout(FSTG_CLField1CompactLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All compact layout fields are verified of object: "+tab.toString(),YesNo.No);
						
						
					}else {
						
						log(LogStatus.FAIL, "All compact layout field  not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"All compact layout field  not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
			
		}
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters("projectName")
	@Test
	public void fstg029_verifyContactCompactLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Contact;
		
		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode,  tab, ObjectFeatureName.compactLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnCompactLayout(FSTG_CLField2CompactLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All compact layout fields are verified of object: "+tab.toString(),YesNo.No);
						
						
					}else {
						
						log(LogStatus.FAIL, "All compact layout field  not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"All compact layout field  not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
			
		}
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters("projectName")
	@Test
	public void fstg030_verifyDealCompactLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Deal;
		
		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode,  tab, ObjectFeatureName.compactLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnCompactLayout(FSTG_CLField3CompactLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All compact layout fields are verified of object: "+tab.toString(),YesNo.No);
						
						
					}else {
						
						log(LogStatus.FAIL, "All compact layout field  not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"All compact layout field  not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
			
		}
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters("projectName")
	@Test
	public void fstg031_verifyFundCompactLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Fund;
		
		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode,  tab, ObjectFeatureName.compactLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnCompactLayout(FSTG_CLField4CompactLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All compact layout fields are verified of object: "+tab.toString(),YesNo.No);
						
						
					}else {
						
						log(LogStatus.FAIL, "All compact layout field  not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"All compact layout field  not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
			
		}
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters("projectName")
	@Test
	public void fstg032_verifyFundraisingCompactLayoutField(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword);
		object tab = object.Fundraising;
		
		if(home.clickOnSetUpLink()) {
			log(LogStatus.PASS, "Click on setup link", YesNo.No);

			 parentID = switchOnWindow(driver);
			if (parentID!=null) {
			
			if(setup.searchStandardOrCustomObject(environment, mode, tab)) {
				log(LogStatus.PASS, "Successfully search object: "+tab.toString(), YesNo.No);
				
				if(setup.clickOnObjectFeature(environment, mode,  tab, ObjectFeatureName.compactLayouts)) {
					
					log(LogStatus.PASS, "click on page layout link of objet: "+tab.toString(), YesNo.No);
					List<String> result= setup.verifyFieldsAvailabilityOnCompactLayout(FSTG_CLField5CompactLayoutFields, 60);
					
					if(result.isEmpty()) {
						log(LogStatus.PASS, "All compact layout fields are verified of object: "+tab.toString(),YesNo.No);
						
						
					}else {
						
						log(LogStatus.FAIL, "All compact layout field  not verified :"+ result, YesNo.No);
						sa.assertTrue(false,"All compact layout field  not verified :"+result);
					}

				}else {
										
					log(LogStatus.FAIL, "not able to click on page layout link of objet :"+tab.toString(), YesNo.No);
					sa.assertTrue(false,"not able to click on page layout link of objet :"+tab.toString());
				}
				
			}else {
				
				log(LogStatus.FAIL, "Unable to search object: "+tab.toString(), YesNo.No);
				sa.assertTrue(false,"Unable to search object: "+tab.toString());
			}
			
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			
			log(LogStatus.PASS, "Not able to Click on setup link", YesNo.No);
			sa.assertTrue(false,"Not able to Click on setup link");
			
		}
		driver.close();
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		sa.assertAll();
	}

	
}
