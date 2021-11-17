package com.navatar.scripts;

import static com.navatar.generic.CommonLib.ThreadSleep;
import static com.navatar.generic.CommonLib.log;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.CommonVariables.mode;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;
import static com.navatar.generic.SmokeCommonVariables.crmUser1EmailID;

import java.util.List;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.AppListeners;
import com.navatar.generic.BaseLib;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
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

	
}
