package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.ExcelUtils.readAllDataForAColumn;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;
import static com.navatar.generic.SmokeCommonVariables.crmUser1EmailID;
import static com.navatar.generic.SmokeCommonVariables.crmUser3FirstName;
import static com.navatar.generic.SmokeCommonVariables.crmUser3LastName;
import static com.navatar.generic.SmokeCommonVariables.crmUser3Lience;
import static com.navatar.generic.SmokeCommonVariables.crmUser3Profile;
import static com.navatar.generic.SmokeCommonVariables.superAdminUserName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.EnumConstants.AppSetting;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.NavigationMenuItems;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.PermissionType;
import com.navatar.generic.EnumConstants.ProgressType;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;
import com.navatar.generic.EnumConstants.recordTypeLabel;
import com.navatar.generic.ExcelUtils;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.ClipPageBusinessLayer;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.DealPageBusinessLayer;
import com.navatar.pageObjects.FundRaisingPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.NavigationPageBusineesLayer;
import com.navatar.pageObjects.ResearchPageBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.navatar.pageObjects.TaskPageBusinessLayer;
import com.navatar.pageObjects.ThemePageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;

public class AcuityResearchAdvanced extends BaseLib{
	
	String navigationMenuName=NavigationMenuItems.Research.toString().replace("_", " ");
	
	@Parameters({ "projectName"})
	@Test
	public void ARATc001_VerifyTheNavigationMenuItems(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		lp.CRMLogin(glUser1EmailID, adminPassword,appName);
		String ele;
		String[] searchValues = {AR_Firm3};
		
		// Verification on navigation menu
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(mouseOverGetTextOperation(driver, rp.getAdvancedResearch(10)) != null){
				log(LogStatus.INFO, "items verified "+bp.filesName+" on "+navigationMenuName, YesNo.No);
			} else {
				log(LogStatus.ERROR, "items not verified "+bp.filesName+" on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"items not verified "+bp.filesName+" on "+navigationMenuName);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify list : "+bp.filesName, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify list : "+bp.filesName);
		}
		refresh(driver);
		
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(clickUsingJavaScript(driver, rp.getAdvancedResearch(10),"Advanced Button", action.BOOLEAN)) {
				log(LogStatus.INFO, "items verified "+bp.filesName+" on "+navigationMenuName, YesNo.No);
			} else {
				log(LogStatus.ERROR, "items not verified "+bp.filesName+" on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"items not verified "+bp.filesName+" on "+navigationMenuName);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify list : "+bp.filesName, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify list : "+bp.filesName);
		}
		switchToDefaultContent(driver);
	
	for(String searchValue : searchValues) {
		
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"AdvancedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
		log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if (mouseOverGetTextOperation(driver, rp.getAdvancedResearch(5)) != null) {
			log(LogStatus.INFO,"--------- Advanced Link is present in Research Popup ---------",YesNo.No);
		} else {
			log(LogStatus.FAIL,"--------- Advanced Link is not present in Research Popup ---------",YesNo.No);
			sa.assertTrue(false,"--------- Advanced Link is not present in Research Popup ---------");
		}
		if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getResearchButton(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			ele = rp.getResearchFindingsValue(10).getText();
			if (ele.equals(searchValue)) {
			log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
				}
			} else {
			log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to send value "+searchValue);
			}
		}
		log(LogStatus.INFO,
				"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
						+ searchValue + "---------",
				YesNo.No);
		if(rp.getNoResult(5) != null){
			log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
		} else 
			if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
				log(LogStatus.INFO,
						"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
			ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
				if(list.isEmpty()) {
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Firm4 + "||" + "list : "+list, YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
				}
	
			} else {
				log(LogStatus.FAIL,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
				sa.assertTrue(false,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------");	
				}
			}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();	
	}
	
@Parameters({ "projectName"})
@Test
	public void ARATc002_VerifyTheResearchFunctionality(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	
	String xpath,ele;
	int i = 1;
	String searchValues[] = {"","a","zz","1234567890~!@#$%^&*()_+-=[]{}?|;':,.<>/"};
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
	for(String searchValue : searchValues) {
		log(LogStatus.PASS, "WOrking for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getAdvancedResearch(10), "Research Button");
			ThreadSleep(8000);
			ele = rp.getResearchFindingsValue(10).getText();
			if (ele.equals(searchValue) || searchValue == null || searchValue == "") {
				log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
			}
				ele = rp.getResearchFindings(10).getText();
				if (ele!=null && ele.equalsIgnoreCase("Search Results")) {
					log(LogStatus.PASS, ele +" is visible", YesNo.Yes);
				}
					ele = rp.getNoResult(10).getText();
					if(ele.contains(bp.errorName1)){
						log(LogStatus.PASS, ele +" has been Matched with " +bp.errorName1, YesNo.No);
					} else {
						log(LogStatus.ERROR, ele +" is not Matched with " +bp.errorName1, YesNo.Yes);
						sa.assertTrue(false, ele +" is not Matched with " +bp.errorName1);
					}
				} else {
					log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to send value "+searchValue);
				}
			}
			i++;
			refresh(driver);
			}
			switchToDefaultContent(driver);
			lp.CRMlogout();
			sa.assertAll();
		}
	
	@Parameters({ "projectName"})
	@Test
	public void ARTc003_VerifyResearchFunctionalityForValidData(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(glUser1EmailID, adminPassword, appName);
	ThreadSleep(2000);
	String ele;
	String headerName;
	
	String[] searchValues = {AR_Firm3};
	
	for(String searchValue : searchValues) {
		
		String varibale =ExcelUtils.readData(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Name, searchValue, excelLabel.Variable_Name);
		log(LogStatus.PASS, "Working for " + searchValue, YesNo.Yes);
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			ThreadSleep(2000);
			clickUsingJavaScript(driver, rp.getAdvancedResearch(10),"Research Button", action.BOOLEAN);
			ThreadSleep(8000);
			if(sendKeys(driver, rp.getTextAreaResearch(10),searchValue, "Research Input Field", action.BOOLEAN)){
				ThreadSleep(2000);
				if(isDisplayed(driver, rp.getResearchButtonOnAdvanced(10), "Visibility", 10, "Search By Keyword(s)") != null) {
					
				} else {
					log(LogStatus.ERROR, "Advanced Research option is not visible", YesNo.Yes);
					sa.assertTrue(false,"Advanced Research option is not visible");
				}
			} else {
				log(LogStatus.ERROR, "Not Able to send value "+searchValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to send value "+searchValue);
			}
			ele = rp.getResearchFindingsValue(10).getText();
			if (ele.equals(searchValue)) {
			log(LogStatus.PASS, ele +" is matched with " +searchValue, YesNo.Yes);
			}
		}
		log(LogStatus.INFO,
				"---------Going to Verify the Result Count for Each Category from the Research Findings side menu: "
						+ searchValue + "---------",
				YesNo.No);
		if(rp.getNoResult(5) != null){
			log(LogStatus.PASS, "There is no data retaled to " + searchValue, YesNo.No);
		} else 
			if (bp.searchAnItemInResearchAndVerifyItsLeftCountAndGridCount(projectName, searchValue)) {
				log(LogStatus.INFO,
						"---------Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
			ArrayList<String> list = rp.VerifyNameAndCountForResearchLeftPanel(varibale, action.SCROLLANDBOOLEAN, 10);
				if(list.isEmpty()) {
					
					log(LogStatus.INFO,"---------Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue + "||" + "list : "+list, YesNo.No);
				} else {
					log(LogStatus.ERROR,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ AR_Firm4 + "||" + "list : "+list, YesNo.No);
					sa.assertTrue(false,"---------Not Verify the Result Count from Left Navigation Panel and Excel Data---------Keyword: "+ searchValue+ "||" + "list : "+list);
				}
	
			} else {
				log(LogStatus.FAIL,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------",
						YesNo.No);
				sa.assertTrue(false,
						"---------Not Verify the Result Count for Each Category from the Research Findings side menu for the record: "
								+ searchValue + "---------");
				
		}
			if (rp.mouseHoverOnNavigationAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			
			if (rp.mouseHoverOnGridAndGetText()) {
				log(LogStatus.INFO,"--------- Records are present in Navigation Menu ---------",YesNo.No);
			} else {
				log(LogStatus.FAIL,"--------- Some records are not present in Navigation Menu ---------",YesNo.No);
				sa.assertTrue(false,"--------- Some records are not present in Navigation Menu ---------");
			}
			int gridSize = rp.getElementsFromGrid().size();
			log(LogStatus.FAIL,"--------- Total count of elements is : " + gridSize,YesNo.No);
			for(int i=0; i<gridSize; i++)
			{		
				headerName = rp.getElementsFromGrid().get(i).getText();
				String recordName = rp.clickOnRecordUsingGridName(headerName, 10).getText();
				
				if (rp.clickOperationOnRecordForGrid(headerName,recordName)) {
					log(LogStatus.INFO,"--------- Click on Records For Grid ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- not able click on Records For Grid ---------",YesNo.No);
					sa.assertTrue(false,"--------- not able click on Records For Grid ---------");
				}
				if (rp.VerifyViewMoreOption(headerName)) {
					log(LogStatus.INFO,"--------- Able to click on view more option for" + headerName + " ---------",YesNo.No);
				} else {
					log(LogStatus.FAIL,"--------- Not able to click on view more option for" + headerName + " ---------",YesNo.No);
				}
			}
		}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();	
	}

}
