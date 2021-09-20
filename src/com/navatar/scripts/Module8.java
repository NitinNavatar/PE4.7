package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;

import static com.navatar.generic.CommonVariables.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonVariables;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.SmokeCommonVariables;
import com.navatar.generic.EnumConstants.EditPageLabel;
import com.navatar.generic.EnumConstants.GlobalActionItem;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.PermissionType;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.customObjectLabel;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;

import static com.navatar.generic.EnumConstants.*;
import static com.navatar.generic.SmokeCommonVariables.AdminUserFirstName;
import static com.navatar.generic.SmokeCommonVariables.AdminUserLastName;
import static com.navatar.generic.SmokeCommonVariables.Smoke_TaskFund1Name;
import static com.navatar.generic.SmokeCommonVariables.Smoke_TaskINS4Name;
import static com.navatar.generic.SmokeCommonVariables.Smoke_TaskSTD1Comment;
import static com.navatar.generic.SmokeCommonVariables.Smoke_TaskSTD1Subject;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;
import static com.navatar.generic.SmokeCommonVariables.crmUser1EmailID;
import static com.navatar.generic.SmokeCommonVariables.crmUser1FirstName;
import static com.navatar.generic.SmokeCommonVariables.crmUser1LastName;
import static com.navatar.generic.SmokeCommonVariables.dayAfterTomorrowsDate;
import static com.navatar.generic.SmokeCommonVariables.meetingCustomObj1Name;
import static com.navatar.generic.SmokeCommonVariables.superAdminUserName;
import static com.navatar.generic.SmokeCommonVariables.taskCustomObj1Name;
import static com.navatar.generic.SmokeCommonVariables.todaysDate;
import static com.navatar.generic.SmokeCommonVariables.todaysDateSingleDateSingleMonth;
import static com.navatar.generic.SmokeCommonVariables.tomorrowsDate;
//import static com.navatar.generic.SmokeCommonVariables.todaysDate;
import static com.navatar.generic.SmokeCommonVariables.yesterdaysDate;
import static com.navatar.generic.SmokeCommonVariables.dayBeforeYesterdaysDate;

import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.BasePageErrorMessage;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.CustomObjPageBusinessLayer;
import com.navatar.pageObjects.EditPageBusinessLayer;
import com.navatar.pageObjects.GlobalActionPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.HomePageErrorMessage;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.NavigationPageBusineesLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.navatar.pageObjects.TaskPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;

public class Module8 extends BaseLib {
	
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc003_verifyHomePage(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		List<WebElement> lst = new ArrayList<WebElement>();
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		
		WebElement[] ele = {home.sdgGridHeaderName(SDGGridName.Deals,5),home.sdgGridHeaderName(SDGGridName.Fundraising,5),home.sdgGridHeaderName(SDGGridName.My_Call_List,5)};
		String[] labelName = {"Deal","FundRaising","My Call List"};
		for (int i=0; i<ele.length; i++) {
			if(ele[i]!=null) {
				log(LogStatus.PASS, "SDG Grid is displaying "+labelName[i], YesNo.No);
			}else {
				log(LogStatus.FAIL,"SDG Grid is not displaying "+labelName[i], YesNo.Yes);
				sa.assertTrue(false, "SDG Grid is not displaying "+labelName[i]);
			}
		}
		WebElement[] ele1 = {home.sdgGridExpandCollpaseIcon(SDGGridName.Deals,CollapseExpandIcon.Expand,5),home.sdgGridExpandCollpaseIcon(SDGGridName.Fundraising,CollapseExpandIcon.Expand,5),home.sdgGridExpandCollpaseIcon(SDGGridName.My_Call_List,CollapseExpandIcon.Expand,5)};
		for (int i=0; i<ele1.length; i++) {
			if(ele1[i]!=null) {
				log(LogStatus.PASS, "SDG Grid is expanded "+labelName[i], YesNo.No);
			}else {
				log(LogStatus.FAIL,"SDG Grid is not expanded "+labelName[i], YesNo.Yes);
				sa.assertTrue(false, "SDG Grid is not expanded "+labelName[i]);
			}
		}
		for (int i=0; i<labelName.length; i++) {
			String headerName="";
			if(i==0) {
				lst = home.sdgGridHeadersLabelNameList(SDGGridName.Deals);
				headerName="Deal,Stage,Source Firm,Notes,Source Firm";
			}else if (i==1) {
				lst = home.sdgGridHeadersLabelNameList(SDGGridName.Fundraising);
				headerName="Fundraising,Stage,Closing,Close Date,Potential Investment(M),Status Notes";
			}else {
				lst = home.sdgGridHeadersLabelNameList(SDGGridName.My_Call_List);
				headerName="Name,Firm,Phone";
			}
			if(!lst.isEmpty()) {
				if(compareMultipleList(driver, headerName, lst).isEmpty()) {
					log(LogStatus.PASS, labelName[i]+" SDG Grid Header Name is verified ", YesNo.No);
				}else {
					log(LogStatus.FAIL,labelName[i]+" SDG Grid Header Name is not verified ", YesNo.Yes);
					sa.assertTrue(false, labelName[i]+" SDG Grid Header Name is not verified ");
				}
			}else {
				log(LogStatus.FAIL,labelName[i]+" SDG Grid Header Name is not visible ", YesNo.Yes);
				sa.assertTrue(false, labelName[i]+" SDG Grid Header Name is not visible ");
			}
		}
		WebElement[] ele2 = {home.sdgGridSideIcons(SDGGridName.Deals,SDGGridSideIcons.Open_SDG_Record,5),home.sdgGridSideIcons(SDGGridName.Fundraising,SDGGridSideIcons.Open_SDG_Record,5),home.sdgGridSideIcons(SDGGridName.My_Call_List,SDGGridSideIcons.Open_SDG_Record,5)};
		String[] tagNames= {"MyHome_ActiveDeals_Baseline","IR - Investors - Fundraising Pipeline - Home","Smart_Caller_MY_Home"};
		String[] defaultSorting= {"Name ASC","Name ASC","Last_Touch_Point__c ASC"};
		for (int i=0; i<ele2.length; i++) {
			if(ele2[i]!=null) {
				if(click(driver, ele2[i], labelName[i]+ " open sdg record ", action.SCROLLANDBOOLEAN)) {
					String parentid = null;
					parentid=switchOnWindow(driver);
					if(parentid!=null) {
						ThreadSleep(5000);
						WebElement ele4 = FindElement(driver, "//h1//*[text()='"+tagNames[i]+"']", labelName[i]+" tag name xpath", action.BOOLEAN, 10);
						if(ele4!=null) {
							log(LogStatus.PASS, labelName[i]+" tag name is displaying ", YesNo.No);
						}else {
							log(LogStatus.PASS, labelName[i]+" tag name is not displaying ", YesNo.No);
							sa.assertTrue(false, labelName[i]+" tag name is not displaying ");
						}
						
						ele4 = FindElement(driver, "//*[text()='Default Sort']/../following-sibling::div//*[text()='"+defaultSorting[i]+"']", labelName[i]+" tag name xpath", action.BOOLEAN, 10);
						if(ele4!=null) {
							log(LogStatus.PASS, labelName[i]+" tag name default name is verified ", YesNo.No);
						}else {
							log(LogStatus.PASS, labelName[i]+" tag name default name is not verified ", YesNo.No);
							sa.assertTrue(false, labelName[i]+" tag name default name is not verified ");
						}
						
						driver.close();
						driver.switchTo().window(parentid);
					}else {
						log(LogStatus.FAIL,"Not able to switch on open sdg record window of "+labelName[i], YesNo.Yes);
						sa.assertTrue(false, "Not able to switch on open sdg record window of "+labelName[i]);
					}
				}
			}else {
				log(LogStatus.FAIL,"SDG Grid is not expanded "+labelName[i], YesNo.Yes);
				sa.assertTrue(false, "SDG Grid is not expanded "+labelName[i]);
			}
		}
		
//		List<WebElement> DealsheaderList = home.sdgGridHeadersLabelNameListForSorting(SDGGridName.Deals);
//		if(!DealsheaderList.isEmpty()) {
//			for(int i=0; i<DealsheaderList.size(); i++) {
//				if(i==0) {
//					if(checkSorting(driver, SortOrder.Assecending, home.sdgGridHeadersDealsGridDealColumnsDataList(i+2))){
//						log(LogStatus.PASS, SortOrder.Assecending+ "Check Sorting on Deal Columns on deals SDG Grid", YesNo.No);
//					}else {
//						log(LogStatus.FAIL, "Not Checked "+SortOrder.Assecending+"Sorting on Deal Columns on deals SDG Grid", YesNo.No);
//						sa.assertTrue(false, "Not Checked "+SortOrder.Assecending+"Sorting on Deal Columns on deals SDG Grid");
//					}
//				}else {
//					if(click(driver, DealsheaderList.get(i), "Deals SDG Grid header column", action.SCROLLANDBOOLEAN)) {
//						log(LogStatus.PASS, "Clicked on Header Clomun "+(i+1)+" for "+SortOrder.Assecending, YesNo.No);
//						ThreadSleep(3000);
//						if(checkSorting(driver, SortOrder.Decending, home.sdgGridHeadersDealsGridDealColumnsDataList(i+2))){
//							log(LogStatus.PASS, SortOrder.Decending+" Check Sorting on Deal Columns on deals SDG Grid", YesNo.No);
//						}else {
//							log(LogStatus.FAIL, SortOrder.Decending+" Not Checked Sorting on Deal Columns on deals SDG Grid", YesNo.No);
//							sa.assertTrue(false, SortOrder.Decending+" Not Checked Sorting on Deal Columns on deals SDG Grid");
//						}
//					}else {
//						log(LogStatus.PASS, "Not able to click on Deals SDG Grid header so cannot check Sorting "+SortOrder.Assecending, YesNo.Yes);
//						sa.assertTrue(false, "Not able to click on Deals SDG Grid header so cannot check Sorting "+SortOrder.Assecending);
//					}
//					DealsheaderList=home.sdgGridHeadersLabelNameListForSorting(SDGGridName.Deals);
//				}
//				if(i==0) {
//					if(click(driver, DealsheaderList.get(i), "Deals SDG Grid header column", action.SCROLLANDBOOLEAN)) {
//						ThreadSleep(3000);
//						log(LogStatus.PASS, "Clicked on Header Clomun "+(i+1)+SortOrder.Decending, YesNo.No);
//						if(checkSorting(driver, SortOrder.Decending, home.sdgGridHeadersDealsGridDealColumnsDataList(i+2))){
//							log(LogStatus.PASS, SortOrder.Decending+" Check Sorting on Deal Columns on deals SDG Grid", YesNo.No);
//						}else {
//							log(LogStatus.FAIL, "Not Checked "+SortOrder.Decending+" Sorting on Deal Columns on deals SDG Grid", YesNo.No);
//							sa.assertTrue(false, "Not Checked "+SortOrder.Decending+" Sorting on Deal Columns on deals SDG Grid");
//						}
//					}else { 
//						log(LogStatus.PASS, "Not able to click on Deals SDG Grid header so cannot check Sorting "+SortOrder.Decending, YesNo.Yes);
//						sa.assertTrue(false, "Not able to click on Deals SDG Grid header so cannot check Sorting "+SortOrder.Decending);
//					}
//				}else {
//					if(click(driver, DealsheaderList.get(i), "Deals SDG Grid header column", action.SCROLLANDBOOLEAN)) {
//						ThreadSleep(3000);
//						log(LogStatus.PASS, "Clicked on Header Clomun "+(i+1)+SortOrder.Assecending, YesNo.No);
//						if(checkSorting(driver, SortOrder.Assecending, home.sdgGridHeadersDealsGridDealColumnsDataList(i+2))){
//							log(LogStatus.PASS, SortOrder.Assecending+" Check Sorting on Deal Columns on deals SDG Grid", YesNo.No);
//						}else {
//							log(LogStatus.FAIL, "Not Checked "+SortOrder.Assecending+" Sorting on Deal Columns on deals SDG Grid", YesNo.No);
//							sa.assertTrue(false, "Not Checked "+SortOrder.Assecending+" Sorting on Deal Columns on deals SDG Grid");
//						}
//					}else { 
//						log(LogStatus.PASS, "Not able to click on Deals SDG Grid header so cannot check Sorting "+SortOrder.Assecending, YesNo.Yes);
//						sa.assertTrue(false, "Not able to click on Deals SDG Grid header so cannot check Sorting "+SortOrder.Assecending);
//					}
//				}
//				DealsheaderList=home.sdgGridHeadersLabelNameListForSorting(SDGGridName.Deals);
//			}
//		}else {
//			log(LogStatus.PASS, "Deals SDG Grid header cloumns list is not visible so cannot check Sorting ", YesNo.Yes);
//			sa.assertTrue(false, "Deals SDG Grid header cloumns list is not visible so cannot check Sorting ");
//		}
//		
//		List<WebElement> FundRaisingsHeaderList = home.sdgGridHeadersLabelNameListForSorting(SDGGridName.Fundraising);
//		if(!FundRaisingsHeaderList.isEmpty()) {
//			for(int i=0; i<FundRaisingsHeaderList.size(); i++) {
//				if(i==0) {
//					if(checkSorting(driver, SortOrder.Assecending, home.sdgGridHeadersFundRaisingsFundraisingColumnsDataList(i+2))){
//						log(LogStatus.PASS, SortOrder.Assecending+ "Check Sorting on Fundraising Columns on deals SDG Grid", YesNo.No);
//					}else {
//						log(LogStatus.FAIL, "Not Checked "+SortOrder.Assecending+"Sorting on Fundraising Columns on deals SDG Grid", YesNo.No);
//						sa.assertTrue(false, "Not Checked "+SortOrder.Assecending+"Sorting on Fundraising Columns on deals SDG Grid");
//					}
//				}else {
//					if(click(driver, FundRaisingsHeaderList.get(i), "FundRaising SDG Grid header column", action.SCROLLANDBOOLEAN)) {
//						log(LogStatus.PASS, "Clicked on Header Clomun "+(i+1)+" for "+SortOrder.Assecending, YesNo.No);
//						ThreadSleep(3000);
//						if(checkSorting(driver, SortOrder.Decending, home.sdgGridHeadersFundRaisingsFundraisingColumnsDataList(i+2))){
//							log(LogStatus.PASS, SortOrder.Decending+" Check Sorting on Fundraising Columns on deals SDG Grid", YesNo.No);
//						}else {
//							log(LogStatus.FAIL, SortOrder.Decending+" Not Checked Sorting on Fundraising Columns on deals SDG Grid", YesNo.No);
//							sa.assertTrue(false, SortOrder.Decending+" Not Checked Sorting on Fundraising Columns on deals SDG Grid");
//						}
//					}else {
//						log(LogStatus.PASS, "Not able to click on FundRaising SDG Grid header so cannot check Sorting "+SortOrder.Assecending, YesNo.Yes);
//						sa.assertTrue(false, "Not able to click on FundRaising SDG Grid header so cannot check Sorting "+SortOrder.Assecending);
//					}
//					FundRaisingsHeaderList=home.sdgGridHeadersLabelNameListForSorting(SDGGridName.Fundraising);
//				}
//				if(i==0) {
//					if(click(driver, FundRaisingsHeaderList.get(i), "Deals SDG Grid header column", action.SCROLLANDBOOLEAN)) {
//						ThreadSleep(3000);
//						log(LogStatus.PASS, "Clicked on Header Clomun "+(i+1)+SortOrder.Decending, YesNo.No);
//						if(checkSorting(driver, SortOrder.Decending, home.sdgGridHeadersFundRaisingsFundraisingColumnsDataList(i+2))){
//							log(LogStatus.PASS, SortOrder.Decending+" Check Sorting on Fundraising Columns on deals SDG Grid", YesNo.No);
//						}else {
//							log(LogStatus.FAIL, "Not Checked "+SortOrder.Decending+" Sorting on Fundraising Columns on deals SDG Grid", YesNo.No);
//							sa.assertTrue(false, "Not Checked "+SortOrder.Decending+" Sorting on Fundraising Columns on deals SDG Grid");
//						}
//					}else { 
//						log(LogStatus.PASS, "Not able to click on FundRaising SDG Grid header so cannot check Sorting "+SortOrder.Decending, YesNo.Yes);
//						sa.assertTrue(false, "Not able to click on FundRaising SDG Grid header so cannot check Sorting "+SortOrder.Decending);
//					}
//				}else {
//					if(click(driver, FundRaisingsHeaderList.get(i), "FundRaising SDG Grid header column", action.SCROLLANDBOOLEAN)) {
//						ThreadSleep(3000);
//						log(LogStatus.PASS, "Clicked on Header Clomun "+(i+1)+SortOrder.Assecending, YesNo.No);
//						if(checkSorting(driver, SortOrder.Assecending, home.sdgGridHeadersFundRaisingsFundraisingColumnsDataList(i+2))){
//							log(LogStatus.PASS, SortOrder.Assecending+" Check Sorting on Fundraising Columns on deals SDG Grid", YesNo.No);
//						}else {
//							log(LogStatus.FAIL, "Not Checked "+SortOrder.Assecending+" Sorting on Fundraising Columns on deals SDG Grid", YesNo.No);
//							sa.assertTrue(false, "Not Checked "+SortOrder.Assecending+" Sorting on Fundraising Columns on deals SDG Grid");
//						}
//					}else { 
//						log(LogStatus.PASS, "Not able to click on FundRaising SDG Grid header so cannot check Sorting "+SortOrder.Assecending, YesNo.Yes);
//						sa.assertTrue(false, "Not able to click on FundRaising SDG Grid header so cannot check Sorting "+SortOrder.Assecending);
//					}
//				}
//				FundRaisingsHeaderList=home.sdgGridHeadersLabelNameListForSorting(SDGGridName.Fundraising);
//			}
//		}else {
//			log(LogStatus.PASS, "FundRaising SDG Grid header cloumns list is not visible so cannot check Sorting ", YesNo.Yes);
//			sa.assertTrue(false, "FundRaising SDG Grid header cloumns list is not visible so cannot check Sorting ");
//		}
//		
//		List<WebElement> MyCallListHeaderList = home.sdgGridHeadersLabelNameListForSorting(SDGGridName.My_Call_List);
//		if(!MyCallListHeaderList.isEmpty()) {
//			for(int i=0; i<MyCallListHeaderList.size(); i++) {
//				if(i==0) {
//					if(checkSorting(driver, SortOrder.Assecending, home.sdgGridHeadersMyCallListNameColumnsDataList(i+2))){
//						log(LogStatus.PASS, SortOrder.Assecending+ "Check Sorting on My Call List Columns on deals SDG Grid", YesNo.No);
//					}else {
//						log(LogStatus.FAIL, "Not Checked "+SortOrder.Assecending+"Sorting on My Call List Columns on deals SDG Grid", YesNo.No);
//						sa.assertTrue(false, "Not Checked "+SortOrder.Assecending+"Sorting on My Call List Columns on deals SDG Grid");
//					}
//				}else {
//					if(click(driver, MyCallListHeaderList.get(i), "My Call List SDG Grid header column", action.SCROLLANDBOOLEAN)) {
//						log(LogStatus.PASS, "Clicked on Header Clomun "+(i+1)+" for "+SortOrder.Assecending, YesNo.No);
//						ThreadSleep(3000);
//						if(checkSorting(driver, SortOrder.Decending, home.sdgGridHeadersMyCallListNameColumnsDataList(i+2))){
//							log(LogStatus.PASS, SortOrder.Decending+" Check Sorting on My Call List Columns on deals SDG Grid", YesNo.No);
//						}else {
//							log(LogStatus.FAIL, SortOrder.Decending+" Not Checked Sorting on My Call List Columns on deals SDG Grid", YesNo.No);
//							sa.assertTrue(false, SortOrder.Decending+" Not Checked Sorting on My Call List Columns on deals SDG Grid");
//						}
//					}else {
//						log(LogStatus.PASS, "Not able to click on My Call List SDG Grid header so cannot check Sorting "+SortOrder.Assecending, YesNo.Yes);
//						sa.assertTrue(false, "Not able to click on My Call List SDG Grid header so cannot check Sorting "+SortOrder.Assecending);
//					}
//					MyCallListHeaderList=home.sdgGridHeadersLabelNameListForSorting(SDGGridName.My_Call_List);
//				}
//				if(i==0) {
//					if(click(driver, MyCallListHeaderList.get(i), "My Call List SDG Grid header column", action.SCROLLANDBOOLEAN)) {
//						ThreadSleep(3000);
//						log(LogStatus.PASS, "Clicked on Header Clomun "+(i+1)+SortOrder.Decending, YesNo.No);
//						if(checkSorting(driver, SortOrder.Decending, home.sdgGridHeadersMyCallListNameColumnsDataList(i+2))){
//							log(LogStatus.PASS, SortOrder.Decending+" Check Sorting on My Call List Columns on deals SDG Grid", YesNo.No);
//						}else {
//							log(LogStatus.FAIL, "Not Checked "+SortOrder.Decending+" Sorting on My Call List Columns on deals SDG Grid", YesNo.No);
//							sa.assertTrue(false, "Not Checked "+SortOrder.Decending+" Sorting on My Call List Columns on deals SDG Grid");
//						}
//					}else { 
//						log(LogStatus.PASS, "Not able to click on My Call List SDG Grid header so cannot check Sorting "+SortOrder.Decending, YesNo.Yes);
//						sa.assertTrue(false, "Not able to click on My Call List SDG Grid header so cannot check Sorting "+SortOrder.Decending);
//					}
//				}else {
//					if(click(driver, MyCallListHeaderList.get(i), "My Call List SDG Grid header column", action.SCROLLANDBOOLEAN)) {
//						ThreadSleep(3000);
//						log(LogStatus.PASS, "Clicked on Header Clomun "+(i+1)+SortOrder.Assecending, YesNo.No);
//						if(checkSorting(driver, SortOrder.Assecending, home.sdgGridHeadersMyCallListNameColumnsDataList(i+2))){
//							log(LogStatus.PASS, SortOrder.Assecending+" Check Sorting on My Call List Columns on deals SDG Grid", YesNo.No);
//						}else {
//							log(LogStatus.FAIL, "Not Checked "+SortOrder.Assecending+" Sorting on My Call List Columns on deals SDG Grid", YesNo.No);
//							sa.assertTrue(false, "Not Checked "+SortOrder.Assecending+" Sorting on My Call List Columns on deals SDG Grid");
//						}
//					}else { 
//						log(LogStatus.PASS, "Not able to click on My Call List SDG Grid header so cannot check Sorting "+SortOrder.Assecending, YesNo.Yes);
//						sa.assertTrue(false, "Not able to click on My Call List SDG Grid header so cannot check Sorting "+SortOrder.Assecending);
//					}
//				}
//				MyCallListHeaderList=home.sdgGridHeadersLabelNameListForSorting(SDGGridName.My_Call_List);
//			}
//		}else {
//			log(LogStatus.PASS, "My Call List SDG Grid header cloumns list is not visible so cannot check Sorting ", YesNo.Yes);
//			sa.assertTrue(false, "My Call List SDG Grid header cloumns list is not visible so cannot check Sorting ");
//		}
		
		if(click(driver, home.sdgGridSideIcons(SDGGridName.Deals, SDGGridSideIcons.Toggle_Filters, 10), "filter icon", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "click on filter icon so cannot search deal name : "+M8DealName1, YesNo.Yes);
			if(home.SearchDealFilterDataOnHomePage(SDGGridName.Deals,"Deal",M8DealName1, Operator.Equals, YesNo.Yes)) {
				log(LogStatus.PASS, "Search Deal Name in filter "+M8DealName1, YesNo.No);
				ThreadSleep(3000);
				if(clickUsingJavaScript(driver, home.sdgGridHeadersDealsNameList().get(0), "deal data", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.PASS, "clicked on deal name "+M8DealName1, YesNo.No);
					String parentid = null;
					parentid=switchOnWindow(driver);
					if(parentid!=null) {
						ThreadSleep(5000);
						WebElement ele4 = FindElement(driver, "//h1//*[text()='"+M8DealName1+"']", M8DealName1+" tag name xpath", action.BOOLEAN, 10);
						if(ele4!=null) {
							log(LogStatus.PASS, M8DealName1+" deal page is open", YesNo.No);
						}else {
							log(LogStatus.PASS, M8DealName1+" deal page is open", YesNo.No);
							sa.assertTrue(false, M8DealName1+" deal page is open");
						}
						driver.close();
						driver.switchTo().window(parentid);
					}else {
						log(LogStatus.FAIL,"new window is not open after click on deal name "+M8DealName1, YesNo.Yes);
						sa.assertTrue(false, "new window is not open after click on deal name "+M8DealName1);
					}
				}else {
					log(LogStatus.PASS, "Not able to click on deal name "+M8DealName1, YesNo.No);
					sa.assertTrue(false, "Not able to click on deal name "+M8DealName1);
				}
			}else {
				log(LogStatus.FAIL, "Not able to Search Deal Name in filter "+M8DealName1, YesNo.No);
				sa.assertTrue(false, "Not able to Search Deal Name in filter "+M8DealName1);
			}
		}else {
			log(LogStatus.FAIL, "Not able to click on filter icon so cannot search deal name : "+M8DealName1, YesNo.Yes);
			sa.assertTrue(false, "Not able to click on filter icon so cannot search deal name : "+M8DealName1);
		}
		
		
		if(click(driver, home.sdgGridSideIcons(SDGGridName.Fundraising, SDGGridSideIcons.Toggle_Filters, 10), "filter icon", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "click on filter icon so cannot search FundRaising name : "+M8FRName1, YesNo.Yes);
			if(home.SearchDealFilterDataOnHomePage(SDGGridName.Fundraising,"Fundraising", M8FRName1, Operator.Equals, YesNo.Yes)) {
				log(LogStatus.PASS, "Search FundRaising Name in filter "+M8FRName1, YesNo.No);
				ThreadSleep(3000);
				if(clickUsingJavaScript(driver, home.sdgGridHeadersFundRaisingsFundraisingNameList().get(0), "FundRaising data", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.PASS, "clicked on FundRaising name "+M8FRName1, YesNo.No);
					String parentid = null;
					parentid=switchOnWindow(driver);
					if(parentid!=null) {
						ThreadSleep(5000);
						WebElement ele4 = FindElement(driver, "//h1//*[text()='"+M8FRName1+"']", M8FRName1+" tag name xpath", action.BOOLEAN, 10);
						if(ele4!=null) {
							log(LogStatus.PASS, M8FRName1+" FundRaising page is open", YesNo.No);
						}else {
							log(LogStatus.PASS, M8FRName1+" FundRaising page is open", YesNo.No);
							sa.assertTrue(false, M8FRName1+" FundRaising page is open");
						}
						driver.close();
						driver.switchTo().window(parentid);
					}else {
						log(LogStatus.FAIL,"new window is not open after click on FundRaising name "+M8FRName1, YesNo.Yes);
						sa.assertTrue(false, "new window is not open after click on FundRaising name "+M8FRName1);
					}
				}else {
					log(LogStatus.PASS, "Not able to click on FundRaising name "+M8FRName1, YesNo.No);
					sa.assertTrue(false, "Not able to click on FundRaising name "+M8FRName1);
				}
			}else {
				log(LogStatus.FAIL, "Not able to Search FundRaising Name in filter "+M8FRName1, YesNo.No);
				sa.assertTrue(false, "Not able to Search FundRaising Name in filter "+M8FRName1);
			}
		}else {
			log(LogStatus.FAIL, "Not able to click on filter icon so cannot search FundRaising name : "+M8FRName1, YesNo.Yes);
			sa.assertTrue(false, "Not able to click on filter icon so cannot search FundRaising name : "+M8FRName1);
		}
		
		if(click(driver, home.sdgGridSideIcons(SDGGridName.My_Call_List, SDGGridSideIcons.Toggle_Filters, 10), "filter icon", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "click on filter icon so cannot search My Call List name : "+M8CON1FName+" "+M8CON1LName, YesNo.Yes);
			if(home.SearchDealFilterDataOnHomePage(SDGGridName.My_Call_List,"Name", M8CON1FName+" "+M8CON1LName, Operator.Equals, YesNo.Yes)) {
				log(LogStatus.PASS, "Search My Call List Name in filter "+M8CON1FName+" "+M8CON1LName, YesNo.No);
				ThreadSleep(3000);
				if(clickUsingJavaScript(driver, home.sdgGridHeadersMyCallListNameList().get(0), "my call list data", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.PASS, "clicked on My Call List name "+M8CON1FName+" "+M8CON1LName, YesNo.No);
					String parentid = null;
					parentid=switchOnWindow(driver);
					if(parentid!=null) {
						ThreadSleep(5000);
						WebElement ele4 = FindElement(driver, "//h1//*[text()='"+M8CON1FName+" "+M8CON1LName+"']", M8CON1FName+" "+M8CON1LName+" tag name xpath", action.BOOLEAN, 10);
						if(ele4!=null) {
							log(LogStatus.PASS, M8CON1FName+" "+M8CON1LName+" My Call List page is open", YesNo.No);
						}else {
							log(LogStatus.PASS, M8CON1FName+" "+M8CON1LName+" My Call List page is open", YesNo.No);
							sa.assertTrue(false, M8CON1FName+" "+M8CON1LName+" My Call List page is open");
						}
						driver.close();
						driver.switchTo().window(parentid);
					}else {
						log(LogStatus.FAIL,"new window is not open after click on My Call List name "+M8CON1FName+" "+M8CON1LName, YesNo.Yes);
						sa.assertTrue(false, "new window is not open after click on My Call List name "+M8CON1FName+" "+M8CON1LName);
					}
				}else {
					log(LogStatus.PASS, "Not able to click on My Call List name "+M8CON1FName+" "+M8CON1LName, YesNo.No);
					sa.assertTrue(false, "Not able to click on My Call List name "+M8CON1FName+" "+M8CON1LName);
				}
			}else {
				log(LogStatus.FAIL, "Not able to Search My Call List Name in filter "+M8CON1FName+" "+M8CON1LName, YesNo.No);
				sa.assertTrue(false, "Not able to Search My Call List Name in filter "+M8CON1FName+" "+M8CON1LName);
			}
		}else {
			log(LogStatus.FAIL, "Not able to click on filter icon so cannot search My Call List name : "+M8CON1FName+" "+M8CON1LName, YesNo.Yes);
			sa.assertTrue(false, "Not able to click on filter icon so cannot search My Call List name : "+M8CON1FName+" "+M8CON1LName);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc004_verifyLogNotesButton(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		List<WebElement> lst = new ArrayList<WebElement>();
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		scrollDownThroughWebelement(driver, home.sdgGridHeaderName(SDGGridName.My_Call_List, 10), "my call list");
		if(click(driver, home.sdgGridHeadersMyCallListNameCallLogList().get(0), "call log button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "clicked on call log button ", YesNo.No);
			ThreadSleep(5000);
			WebElement ele = FindElement(driver, "//h2[contains(text(),'New')]", "new log call", action.BOOLEAN, 10);
			if(ele!=null) {
				log(LogStatus.PASS, "New Log Call Notes pop is open", YesNo.No);
			}else {
				log(LogStatus.FAIL, "New Log Call Notes pop is not opened", YesNo.Yes);
				sa.assertTrue(false, "New Log Call Notes pop is not opened");
			}
			if(click(driver, home.getCustomTabSaveBtn(projectName, 10), "save button", action.BOOLEAN)) {
				log(LogStatus.PASS, "clicked on save button and New Call Log is created ", YesNo.No);
			}else {
				log(LogStatus.FAIL, "Not able to click on save button so cannot create log call ", YesNo.No);
				sa.assertTrue(false, "Not able to click on save button so cannot create log call ");
			}
		}else {
			log(LogStatus.PASS, "Not able to click on call log button ", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on call log button ");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc005_verifyRecordsCount(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		List<WebElement> lst = new ArrayList<WebElement>();
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if(home.sdgGridHeadersDealsGridDealColumnsDataList(2).size()==10) {
			log(LogStatus.PASS, "10 records are displaying in deals grid", YesNo.No);
		}else {
			log(LogStatus.FAIL, "10 records are not displaying in deals grid", YesNo.Yes);
			sa.assertTrue(false, "10 records are not displaying in deals grid");
		}
		if(home.sdgGridHeadersFundRaisingsFundraisingColumnsDataList(2).size()==10) {
			log(LogStatus.PASS, "10 records are displaying in Fundraising grid", YesNo.No);
		}else {
			log(LogStatus.FAIL, "10 records are not displaying in Fundraising grid", YesNo.Yes);
			sa.assertTrue(false, "10 records are not displaying in Fundraising grid");
		}
		if(home.sdgGridHeadersMyCallListNameColumnsDataList(2).size()==10) {
			log(LogStatus.PASS, "10 records are displaying in My Call list grid", YesNo.No);
		}else {
			log(LogStatus.FAIL, "10 records are not displaying in My Call list grid", YesNo.Yes);
			sa.assertTrue(false, "10 records are not displaying in My Call list grid");
		}
		
		if(selectVisibleTextFromDropDown(driver, home.sdgGridPageAndPageSizeofDealFundraisingAndMyCallList(SDGGridName.Deals, ActionType.Page, 10), "deal sdg grid page drop down","2")) {
			log(LogStatus.PASS, "2 page is selected in deal SDG grid", YesNo.No);
		}else {
			log(LogStatus.FAIL, "2 page is not selected in deal SDG grid", YesNo.Yes);
			sa.assertTrue(false, "2 page is not selected in deal SDG grid");
		}
		
		if(selectVisibleTextFromDropDown(driver, home.sdgGridPageAndPageSizeofDealFundraisingAndMyCallList(SDGGridName.Fundraising, ActionType.Page, 10), "Fundraising sdg grid page drop down","2")) {
			log(LogStatus.PASS, "2 page is selected in Fundraising SDG grid", YesNo.No);
		}else {
			log(LogStatus.FAIL, "2 page is not selected in Fundraising SDG grid", YesNo.Yes);
			sa.assertTrue(false, "2 page is not selected in Fundraising SDG grid");
		}
		
		if(selectVisibleTextFromDropDown(driver, home.sdgGridPageAndPageSizeofDealFundraisingAndMyCallList(SDGGridName.My_Call_List, ActionType.Page, 10), "My_Call_List sdg grid page drop down","2")) {
			log(LogStatus.PASS, "2 page is selected in My_Call_List SDG grid", YesNo.No);
		}else {
			log(LogStatus.FAIL, "2 page is not selected in My_Call_List SDG grid", YesNo.Yes);
			sa.assertTrue(false, "2 page is not selected in My_Call_List SDG grid");
		}
		
		if(selectVisibleTextFromDropDown(driver, home.sdgGridPageAndPageSizeofDealFundraisingAndMyCallList(SDGGridName.Deals, ActionType.PageSize, 10), "deal sdg grid page drop down","20")) {
			log(LogStatus.PASS, "20 page size is selected in deal SDG grid", YesNo.No);
		}else {
			log(LogStatus.FAIL, "20 page size is not selected in deal SDG grid", YesNo.Yes);
			sa.assertTrue(false, "20 page size is not selected in deal SDG grid");
		}
		
		if(selectVisibleTextFromDropDown(driver, home.sdgGridPageAndPageSizeofDealFundraisingAndMyCallList(SDGGridName.Fundraising, ActionType.PageSize, 10), "Fundraising sdg grid page drop down","20")) {
			log(LogStatus.PASS, "20 page size is selected in Fundraising SDG grid", YesNo.No);
		}else {
			log(LogStatus.FAIL, "20 page size is not selected in Fundraising SDG grid", YesNo.Yes);
			sa.assertTrue(false, "20 page size is not selected in Fundraising SDG grid");
		}
		
		if(selectVisibleTextFromDropDown(driver, home.sdgGridPageAndPageSizeofDealFundraisingAndMyCallList(SDGGridName.My_Call_List, ActionType.PageSize, 10), "My_Call_List sdg grid page drop down","20")) {
			log(LogStatus.PASS, "20 page size is selected in My_Call_List SDG grid", YesNo.No);
		}else {
			log(LogStatus.FAIL, "20 page size is not selected in My_Call_List SDG grid", YesNo.Yes);
			sa.assertTrue(false, "20 page size is not selected in My_Call_List SDG grid");
		}
		
		SDGGridName[] sdgGridName = {SDGGridName.Deals,SDGGridName.Fundraising,SDGGridName.My_Call_List};
		String[] searchHeaderName = {"Deal","Fundraising","Name"};
		
		String [] DataList = {M8DealName2,M8FRName1,M8CON1FName+" "+M8CON1LName};
		Operator[] operator = {Operator.Equals,Operator.StartWith,Operator.Equals};
		
		
		for(int i=0; i<sdgGridName.length; i++) {
			if(click(driver, home.sdgGridSideIcons(sdgGridName[i], SDGGridSideIcons.Toggle_Filters, 10), "filter icon", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.PASS, "click on filter icon so cannot search My Call List name : "+M8CON1FName+" "+M8CON1LName, YesNo.Yes);
				if(searchHeaderName[i]=="Fundraising") {
					DataList[i]= (M8FRName1.split("-")[0]);
				}
				if(home.SearchDealFilterDataOnHomePage(sdgGridName[i],searchHeaderName[i],DataList[i], operator[i], YesNo.Yes)) {
					log(LogStatus.PASS, "Search My Call List Name in filter "+DataList[i], YesNo.No);
					ThreadSleep(3000);
					
				
					String xpath="//td//span/*[contains(@title,'"+DataList[i]+"')]";
					WebElement ele4 = FindElement(driver, xpath, DataList[i]+" tool tip xpath on "+sdgGridName[i], action.BOOLEAN, 10);
					if(ele4!=null) {
						log(LogStatus.PASS, DataList[i]+" tool tip is displaying on "+sdgGridName[i], YesNo.No);
					}else {
						log(LogStatus.PASS, DataList[i]+" tool tip is not displaying on "+sdgGridName[i], YesNo.Yes);
						sa.assertTrue(false, DataList[i]+" tool tip is not displaying on "+sdgGridName[i]);
					}
					
					
				}else {
					log(LogStatus.FAIL, "Not able to Search "+sdgGridName[i]+" in filter "+DataList[i], YesNo.No);
					sa.assertTrue(false, "Not able to Search "+sdgGridName[i]+" Name in filter "+DataList[i]);
				}
			}else {
				log(LogStatus.FAIL, "Not able to click on filter icon so cannot search "+sdgGridName[i]+" : "+DataList[i], YesNo.Yes);
				sa.assertTrue(false, "Not able to click on filter icon so cannot search "+sdgGridName[i]+" : "+DataList[i]);
			}
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc006_1_verifyDefaultTheme(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		ThreadSleep(5000);
		WebElement[][] ele2 = {{home.sdgGridSideIcons(SDGGridName.Deals,SDGGridSideIcons.Manage_fields,5),home.sdgGridSideIcons(SDGGridName.Deals,SDGGridSideIcons.Open_SDG_Record,5),
			home.sdgGridSideIcons(SDGGridName.Deals,SDGGridSideIcons.Toggle_Filters,5),home.sdgGridSideIcons(SDGGridName.Deals,SDGGridSideIcons.Reload,5)}
		,{home.sdgGridSideIcons(SDGGridName.Fundraising,SDGGridSideIcons.Manage_fields,5),home.sdgGridSideIcons(SDGGridName.Fundraising,SDGGridSideIcons.Open_SDG_Record,5),
			home.sdgGridSideIcons(SDGGridName.Fundraising,SDGGridSideIcons.Toggle_Filters,5),home.sdgGridSideIcons(SDGGridName.Fundraising,SDGGridSideIcons.Reload,5)},
		{home.sdgGridSideIcons(SDGGridName.My_Call_List,SDGGridSideIcons.Manage_fields,5),home.sdgGridSideIcons(SDGGridName.My_Call_List,SDGGridSideIcons.Open_SDG_Record,5),
				home.sdgGridSideIcons(SDGGridName.My_Call_List,SDGGridSideIcons.Toggle_Filters,5),home.sdgGridSideIcons(SDGGridName.My_Call_List,SDGGridSideIcons.Reload,5)}};
		System.err.println(ele2.length);
		System.err.println(ele2[0].length);
		String[] labelName = {"Deal","FundRaising","My Call List"};
		String[] filterIconName= {"Manage_fields","Open_SDG_Record","Toggle_Filters","Reload"};
		int j=0;
		for (WebElement[] webElements : ele2) {
			for(int i=0 ;i<webElements.length; i++) {
				if(webElements[i]!=null) {
					log(LogStatus.PASS, filterIconName[i]+" is displaying and tool tip is available for "+labelName[j], YesNo.No);
				}else {
					log(LogStatus.PASS, filterIconName[i]+" is displaying and tool tip is not  available for "+labelName[j], YesNo.Yes);
					sa.assertTrue(false,  filterIconName[i]+" is displaying and tool tip is not available for "+labelName[j]);
				}
			}
			j++;
		}
		if(edit.clickOnEditPageLink()) {
			log(LogStatus.PASS, "clicked on edit page on home page", YesNo.No);
			ThreadSleep(10000);
			String[] sdgGrid = {"Deal","FundRaising","My Call List"};
			SDGGridName[] sdgNames = {SDGGridName.Deals,SDGGridName.Fundraising,SDGGridName.My_Call_List};
			switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
			for(int i=0; i<sdgNames.length; i++) {
				WebElement ele= home.sdgGridListInEditMode(sdgNames[i],20);
				scrollDownThroughWebelement(driver, ele, "");
				if(click(driver, ele, "sdg grid "+sdgNames[i], action.BOOLEAN)) {
					log(LogStatus.PASS, "clicked on SDG Grid "+(i+1), YesNo.No);
					ThreadSleep(5000);
					switchToDefaultContent(driver);
					if(home.getSelectThemeinputBoxButton(10).getAttribute("placeholder").trim().contains("Standard")) {
						log(LogStatus.PASS, "Standard theme is selected in "+sdgGrid[i]+" SDG Grid", YesNo.No);
					}else {
						log(LogStatus.FAIL, "Standard theme is not selected in "+sdgGrid[i]+" SDG Grid", YesNo.Yes);
						sa.assertTrue(false, "Standard theme is not selected in "+sdgGrid[i]+" SDG Grid");
					}
					click(driver, home.getSelectThemeinputBoxClearButton(10), "clear button", action.SCROLLANDBOOLEAN);
					ThreadSleep(1000);
					String themeList ="Standard,Light,Dark";
					List<WebElement> themelistwebelement=home.sdgGridSelectThemeList();
					if(compareMultipleList(driver, themeList, themelistwebelement).isEmpty()) {
						log(LogStatus.PASS, "Theme list is verified for "+sdgGrid[i], YesNo.No);
					}else {
						log(LogStatus.FAIL, "Theme list is not verified for "+sdgGrid[i], YesNo.Yes);
						sa.assertTrue(false, "Theme list is not verified for "+sdgGrid[i]);
					}

				}else {
					log(LogStatus.PASS, "Not able to click on SDG Grid "+sdgNames[i], YesNo.Yes);
					sa.assertTrue(false, "Not able to click on SDG Grid "+sdgNames[i]);
				}
				if(i!=sdgNames.length-1) {
					switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
				}
			}
			
			ThreadSleep(2000);
			if(clickUsingJavaScript(driver, edit.getBackButton(10), "back button", action.BOOLEAN)) {
				log(LogStatus.PASS, "clicked on back button", YesNo.No);
			}else {
				log(LogStatus.ERROR, "Not able to click on back button so cannot back on page ", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on back button so cannot back on page ");
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on edit page so cannot check deafult theme", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on edit page so cannot check deafult theme");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc006_2_verifyDefaultTheme(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		ThreadSleep(5000);
		WebElement[][] ele2 = {{home.sdgGridSideIcons(SDGGridName.Deals,SDGGridSideIcons.Manage_fields,5),home.sdgGridSideIcons(SDGGridName.Deals,SDGGridSideIcons.Open_SDG_Record,5),
			home.sdgGridSideIcons(SDGGridName.Deals,SDGGridSideIcons.Toggle_Filters,5),home.sdgGridSideIcons(SDGGridName.Deals,SDGGridSideIcons.Reload,5)}
		,{home.sdgGridSideIcons(SDGGridName.Fundraising,SDGGridSideIcons.Manage_fields,5),home.sdgGridSideIcons(SDGGridName.Fundraising,SDGGridSideIcons.Open_SDG_Record,5),
			home.sdgGridSideIcons(SDGGridName.Fundraising,SDGGridSideIcons.Toggle_Filters,5),home.sdgGridSideIcons(SDGGridName.Fundraising,SDGGridSideIcons.Reload,5)},
		{home.sdgGridSideIcons(SDGGridName.My_Call_List,SDGGridSideIcons.Manage_fields,5),home.sdgGridSideIcons(SDGGridName.My_Call_List,SDGGridSideIcons.Open_SDG_Record,5),
				home.sdgGridSideIcons(SDGGridName.My_Call_List,SDGGridSideIcons.Toggle_Filters,5),home.sdgGridSideIcons(SDGGridName.My_Call_List,SDGGridSideIcons.Reload,5)}};
		String[] labelName = {"Deal","FundRaising","My Call List"};
		String[] filterIconName= {"Manage_fields","Open_SDG_Record","Toggle_Filters","Reload"};
		int j=0;
		for (WebElement[] webElements : ele2) {
			for(int i=0 ;i<webElements.length; i++) {
				if(webElements[i]!=null) {
					log(LogStatus.PASS, filterIconName[i]+" is displaying and tool tip is available for "+labelName[j], YesNo.No);
				}else {
					log(LogStatus.PASS, filterIconName[i]+" is displaying and tool tip is not  available for "+labelName[j], YesNo.Yes);
					sa.assertTrue(false,  filterIconName[i]+" is displaying and tool tip is not available for "+labelName[j]);
				}
			}
			j++;
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc007_verifywrenchIconForStandardTheme(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		ThreadSleep(5000);
		WebElement[] ele2 = {home.sdgGridSideIcons(SDGGridName.Deals,SDGGridSideIcons.Manage_fields,5)
				,home.sdgGridSideIcons(SDGGridName.Fundraising,SDGGridSideIcons.Manage_fields,5),
				home.sdgGridSideIcons(SDGGridName.My_Call_List,SDGGridSideIcons.Manage_fields,5)};
		String[] labelName = {"Deal","FundRaising","My Call List"};
		String dealVisibleFieldList ="Deal,Stage,Source Firm,Notes,Source Firm";
		String FundRaisingVisibleFieldList ="Fundraising,Stage,Closing,Close Date,Potential Investment(M),Status Notes";
		String MyCallListVisibleFieldList ="Name,Firm,Phone";
		for(int i=0 ;i<ele2.length; i++) {
			String list="";
			if(ele2[i]!=null) {
				log(LogStatus.PASS, "Manage Field icon is displaying and tool tip is available for "+labelName[i], YesNo.No);
				ThreadSleep(3000);
				if(click(driver, ele2[i], "manage field icon", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.PASS, "clicked on manage field icon of "+labelName[i], YesNo.No);
					List<WebElement> lst = home.sdgGridSelectVisibleFieldsListInManageFieldPopUp();
					if(i==0) {
						list=dealVisibleFieldList;
					}else if (i==1) {
						list= FundRaisingVisibleFieldList;
					}else {
						list=MyCallListVisibleFieldList;
					}
					if(compareMultipleList(driver, list, lst).isEmpty()) {
						log(LogStatus.PASS, "visible list is verfied for "+labelName[i], YesNo.No);
					}else {
						log(LogStatus.FAIL, "visible list is not verfied for "+labelName[i], YesNo.Yes);
						sa.assertTrue(false, "visible list is not verfied for "+labelName[i]);
					}
					if(click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Cancel, 10), "cancel button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "clicked on cancel button for "+labelName[i], YesNo.No);
					}else {
						log(LogStatus.PASS, "Not able to click on cancel button for "+labelName[i], YesNo.No);
						sa.assertTrue(false, "Not able to click on cancel button for "+labelName[i]);
					}
				}else {
					log(LogStatus.PASS, "Not able to click on manage field icon of "+labelName[i], YesNo.No);
					sa.assertTrue(false, "Not able to click on manage field icon of "+labelName[i]);
				}
			}else {
				log(LogStatus.PASS, "Manage field icon is not displaying so cannot check manage field icon for "+labelName[i], YesNo.Yes);
				sa.assertTrue(false,  "Manage field icon is not displaying and cannot check manage field icon for "+labelName[i]);
			}
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M8Tc008_verifySDGFiltersForStandardTheme(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		List<WebElement> lst = new ArrayList<WebElement>();
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		ThreadSleep(5000);
		SDGGridName[] sdgGridName = {SDGGridName.Deals,SDGGridName.Fundraising,SDGGridName.My_Call_List};
		String[] labelName = {"Deal","FundRaising","My Call List"};
		String [] dropDownName = {"Stage","Closing","Firm"};
		String [] searchDataName= {null,null,"Centri"};
		Operator[] operators = {Operator.Deal_Received,Operator.Second_Closing,Operator.StartWith};
		YesNo[] searchData = {YesNo.No,YesNo.No,YesNo.Yes};
		
		for(int i=0; i<sdgGridName.length; i++) {
			if(click(driver, home.sdgGridSideIcons(sdgGridName[i], SDGGridSideIcons.Toggle_Filters, 10), "filter icon", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.PASS, "click on filter icon so cannot search My Call List name : "+M8CON1FName+" "+M8CON1LName, YesNo.Yes);
				if(home.SearchDealFilterDataOnHomePage(sdgGridName[i],dropDownName[i],searchDataName[i], operators[i], searchData[i])) {
					log(LogStatus.PASS, "Search My Call List Name in filter "+labelName[i], YesNo.No);
					ThreadSleep(3000);
					if(i==0) {
						lst =home.sdgGridHeadersDealsGridDealColumnsDataList(3);
						if(!lst.isEmpty()) {
							if(compareMultipleList(driver,operators[i].toString(), lst).isEmpty()) {
								log(LogStatus.PASS, "Deal Received is verfied for "+labelName[i], YesNo.No);
							}else {
								log(LogStatus.FAIL, "Deal Received is not verfied for "+labelName[i], YesNo.Yes);
								sa.assertTrue(false, "Deal Received is not verfied for "+labelName[i]);
							}
						}else {
							log(LogStatus.FAIL, "Stage data list is not found so cannot check stage list after applied filter in deal SDG", YesNo.Yes);
							sa.assertTrue(false, "Stage data list is not found so cannot check stage list after applied filter in deal SDG");
						}
						
					}else if (i==1) {
						lst =home.sdgGridHeadersFundRaisingsFundraisingColumnsDataList(3);
						if(!lst.isEmpty()) {
							if(compareMultipleList(driver,operators[i].toString(), lst).isEmpty()) {
								log(LogStatus.PASS, "2nd closing is verfied for "+labelName[i], YesNo.No);
							}else {
								log(LogStatus.FAIL, "2nd closing is not verfied for "+labelName[i], YesNo.Yes);
								sa.assertTrue(false, "2nd closing is not verfied for "+labelName[i]);
							}
						}else {
							log(LogStatus.FAIL, "2nd closing data list is not found so cannot check 2nd closing list after applied filter in Fundraising SDG", YesNo.Yes);
							sa.assertTrue(false, "2nd closing data data list is not found so cannot check 2nd closing list after applied filter in Fundraising SDG");
						}
					}else {
						lst =home.sdgGridHeadersMyCallListNameColumnsDataList(3);
						if(!lst.isEmpty()) {
							if(compareMultipleList(driver,"Centri Technology", lst).isEmpty()) {
								log(LogStatus.PASS, "Firm filter is verfied for "+labelName[i], YesNo.No);
							}else {
								log(LogStatus.FAIL, "Firm filter is not verfied for "+labelName[i], YesNo.Yes);
								sa.assertTrue(false, "Firm filter is not verfied for "+labelName[i]);
							}
						}else {
							log(LogStatus.FAIL, "Firm filter data list is not found so cannot check Firm list after applied filter in My_Call_List SDG", YesNo.Yes);
							sa.assertTrue(false, "firm filter data list is not found so cannot check Firm list after applied filter in My_Call_List SDG");
						}
					}
					click(driver, home.sdgGridSideIcons(sdgGridName[i], SDGGridSideIcons.Toggle_Filters, 10), "filter icon", action.SCROLLANDBOOLEAN);
				}else {
					log(LogStatus.FAIL, "Not able to Search "+sdgGridName[i]+" in filter "+labelName[i], YesNo.No);
					sa.assertTrue(false, "Not able to Search "+sdgGridName[i]+" Name in filter "+labelName[i]);
				}
			}else {
				log(LogStatus.FAIL, "Not able to click on filter icon so cannot search "+sdgGridName[i]+" : "+labelName[i], YesNo.Yes);
				sa.assertTrue(false, "Not able to click on filter icon so cannot search "+sdgGridName[i]+" : "+labelName[i]);
			}
		}
		refresh(driver);
		lst =home.sdgGridHeadersDealsGridDealColumnsDataList(3);
		if(!lst.isEmpty()) {
			if(!compareMultipleList(driver,operators[0].toString(), lst).isEmpty()) {
				log(LogStatus.PASS, "Deal Received is not visible for "+labelName[0], YesNo.No);
			}else {
				log(LogStatus.FAIL, "Deal Received is visible for "+labelName[0], YesNo.Yes);
				sa.assertTrue(false, "Deal Received is visible for "+labelName[0]);
			}
		}else {
			log(LogStatus.FAIL, "Stage data list is not found so cannot check stage list after refresh in deal SDG", YesNo.Yes);
			sa.assertTrue(false, "Stage data list is not found so cannot check stage list after refresh filter in deal SDG");
		}
		lst = new ArrayList<WebElement>();
		lst =home.sdgGridHeadersFundRaisingsFundraisingColumnsDataList(3);
		if(!lst.isEmpty()) {
			if(!compareMultipleList(driver,operators[1].toString(), lst).isEmpty()) {
				log(LogStatus.PASS, "2nd closing is not visible for "+labelName[1], YesNo.No);
			}else {
				log(LogStatus.FAIL, "2nd closing is visible for "+labelName[1], YesNo.Yes);
				sa.assertTrue(false, "2nd closing is visible for "+labelName[1]);
			}
		}else {
			log(LogStatus.FAIL, "2nd closing data list is not found so cannot check 2nd closing list after refresh in Fundraising SDG", YesNo.Yes);
			sa.assertTrue(false, "2nd closing data data list is not found so cannot check 2nd closing list after refresh in Fundraising SDG");
		}
		lst = new ArrayList<WebElement>();
		lst =home.sdgGridHeadersMyCallListNameColumnsDataList(3);
		if(!lst.isEmpty()) {
			if(compareMultipleList(driver,"Centri Technology", lst).isEmpty()) {
				log(LogStatus.PASS, "Firm filter is not visible for "+labelName[2], YesNo.No);
			}else {
				log(LogStatus.FAIL, "Firm filter is visible for "+labelName[2], YesNo.Yes);
				sa.assertTrue(false, "Firm filter is visible for "+labelName[2]);
			}
		}else {
			log(LogStatus.FAIL, "Firm filter data list is not found so cannot check Firm list after refresh in My_Call_List SDG", YesNo.Yes);
			sa.assertTrue(false, "firm filter data list is not found so cannot check Firm list after refresh in My_Call_List SDG");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M8Tc009_verifySetupIconAndTextForStandardTheme(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		ThreadSleep(5000);
		String[] labelName = {"Deal","FundRaising","My Call List"};
		WebElement[] ele2 = {home.sdgGridSideIcons(SDGGridName.Deals,SDGGridSideIcons.Open_SDG_Record,5),home.sdgGridSideIcons(SDGGridName.Fundraising,SDGGridSideIcons.Open_SDG_Record,5),home.sdgGridSideIcons(SDGGridName.My_Call_List,SDGGridSideIcons.Open_SDG_Record,5)};
		String[] tagNames= {"MyHome_ActiveDeals_Baseline","IR - Investors - Fundraising Pipeline - Home","Smart_Caller_MY_Home"};
		for (int i=0; i<ele2.length; i++) {
			if(ele2[i]!=null) {
				if(click(driver, ele2[i], labelName[i]+ " open sdg record ", action.SCROLLANDBOOLEAN)) {
					String parentid = null;
					parentid=switchOnWindow(driver);
					if(parentid!=null) {
						ThreadSleep(5000);
						WebElement ele4 = FindElement(driver, "//h1//*[text()='"+tagNames[i]+"']", labelName[i]+" tag name xpath", action.BOOLEAN, 10);
						if(ele4!=null) {
							log(LogStatus.PASS, labelName[i]+" tag name is displaying ", YesNo.No);
						}else {
							log(LogStatus.PASS, labelName[i]+" tag name is not displaying ", YesNo.No);
							sa.assertTrue(false, labelName[i]+" tag name is not displaying ");
						}
						driver.close();
						driver.switchTo().window(parentid);
					}else {
						log(LogStatus.FAIL,"Not able to switch on open sdg record window of "+labelName[i], YesNo.Yes);
						sa.assertTrue(false, "Not able to switch on open sdg record window of "+labelName[i]);
					}
				}
			}else {
				log(LogStatus.FAIL,"Open_SDG_Record  is not visible in SDG Grid "+labelName[i], YesNo.Yes);
				sa.assertTrue(false, "SDG Grid is not visible in SDG Grid "+labelName[i]);
			}
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M8Tc010_verifyReloadIconForStandardTheme(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		List<WebElement> lst = new ArrayList<WebElement>();
		ThreadSleep(5000);
		String[] labelName = {"Deal","FundRaising","My Call List"};
		WebElement[] ele2 = {home.sdgGridSideIcons(SDGGridName.Deals,SDGGridSideIcons.Reload,5),home.sdgGridSideIcons(SDGGridName.Fundraising,SDGGridSideIcons.Reload,5),home.sdgGridSideIcons(SDGGridName.My_Call_List,SDGGridSideIcons.Reload,5)};
		EditPageLabel [] editPageLabel= {EditPageLabel.Deal,EditPageLabel.Fundraising,EditPageLabel.Phone};
		EditPageLabel[] textBoxName= {EditPageLabel.Name,EditPageLabel.Name,EditPageLabel.Phone};
		String dealName="";
		String updatedName="";
		String imagePath="//AutoIT//CheckBox.PNG";
		for (int i=0; i<ele2.length; i++) {
			if(ele2[i]!=null) {
				if(i==0) {
					lst = home.sdgGridHeadersDealsNameList();
					dealName=lst.get(0).getText();
					updatedName=dealName+"Update";
				}else if (i==1) {
					lst = home.sdgGridHeadersFundRaisingsFundraisingNameList();
					dealName=lst.get(0).getText();
					updatedName=dealName+"Update";
				}else {
					lst = home.sdgGridHeadersMyCallListPhoneList();
					for(int i1=0; i1<lst.size(); i1++) {
						if(!lst.get(i1).getText().equals("")) {
							dealName=lst.get(i).getText();
							break;
						}
					}
					updatedName=dealName+"12345";
				}
				scrollDownThroughWebelement(driver, ele2[i], "");
				if (home.clickOnEditButtonOnSDGGridOnHomePage(projectName,dealName , editPageLabel[i].toString(), 10)) {
					ThreadSleep(3000);
					log(LogStatus.PASS, "mouse over on Deal Name : "+dealName, YesNo.No);
					
					WebElement ele=home.SDGInputTextbox(projectName, textBoxName[i].toString(), 10);
					sendKeys(driver, ele, updatedName, "title textbox", action.BOOLEAN);
					if (mouseHoveAndClickAction(imagePath, "checkbox")) {
						ThreadSleep(5000);

						log(LogStatus.INFO, "successfully clicked on checkbox button", YesNo.No);

						if (click(driver, home.getsdgSaveButton(projectName,10), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "successfully clicked on save button", YesNo.No);

						}else {
							log(LogStatus.ERROR, "could not click on save button", YesNo.Yes);
							sa.assertTrue(false,"could not click on save button" );
						}
						ThreadSleep(5000);
						
						if (click(driver,ele2[i],"relaod icon "+labelName[i], action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on reload icon "+labelName[i], YesNo.No);
							
							

						}else {
							log(LogStatus.ERROR, "Not able to click on reload icon "+labelName[i], YesNo.Yes);
							sa.assertTrue(false,"Not able to click on reload icon "+labelName[i]);
						}
					}else {
						log(LogStatus.ERROR, "could not click on checkbox sikuli", YesNo.Yes);
						sa.assertTrue(false,"could not click on checkbox sikuli" );
					}
				
				}else {
					log(LogStatus.ERROR, "could not click on edit button : "+dealName, YesNo.Yes);
					sa.assertTrue(false,"could not click on edit button: "+dealName );
				}
				
			}else {
				log(LogStatus.FAIL,"Open_SDG_Record  is not visible in SDG Grid "+labelName[i], YesNo.Yes);
				sa.assertTrue(false, "SDG Grid is not visible in SDG Grid "+labelName[i]);
			}
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc011_1_verifyLightThemeForSDG(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		ThreadSleep(5000);
		if(edit.clickOnEditPageLink()) {
			log(LogStatus.PASS, "clicked on edit page on home page", YesNo.No);
			ThreadSleep(10000);
			String[] sdgGrid = {"Deal","FundRaising","My Call List"};
			SDGGridName[] sdgNames = {SDGGridName.Deals,SDGGridName.Fundraising,SDGGridName.My_Call_List};
			switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
			for(int i=0; i<sdgNames.length; i++) {
				WebElement ele= home.sdgGridListInEditMode(sdgNames[i],20);
				scrollDownThroughWebelement(driver, ele, "");
				if(click(driver, ele, "sdg grid "+sdgNames[i], action.BOOLEAN)) {
					log(LogStatus.PASS, "clicked on SDG Grid "+(i+1), YesNo.No);
					ThreadSleep(5000);
					switchToDefaultContent(driver);
					click(driver, home.getSelectThemeinputBoxClearButton(10), "clear button", action.SCROLLANDBOOLEAN);
					ThreadSleep(1000);
					List<WebElement> themelistwebelement=home.sdgGridSelectThemeList();
					for(int i1=0; i1<themelistwebelement.size(); i1++) {
						if(themelistwebelement.get(i1).getText().equalsIgnoreCase("Light")) {
							if(click(driver, themelistwebelement.get(i1), "Light theme xpath", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "Select Ligth Theme for "+sdgGrid[i], YesNo.No);
								break;
							}
						}else {
								log(LogStatus.PASS, "Not able to select Ligth Theme for "+sdgGrid[i], YesNo.Yes);
								sa.assertTrue(false, "Not able to select Ligth Theme for "+sdgGrid[i]);
							
						}
					}
				}else {
					log(LogStatus.PASS, "Not able to click on SDG Grid "+sdgNames[i], YesNo.Yes);
					sa.assertTrue(false, "Not able to click on SDG Grid "+sdgNames[i]);
				}
				if(i!=sdgNames.length-1) {
					switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
				}
			}
			
			ThreadSleep(2000);
			if(click(driver, home.getCustomTabSaveBtn(projectName, 10), "save button", action.BOOLEAN)) {
        		log(LogStatus.INFO, "clicked on save button", YesNo.No);
        		ThreadSleep(7000);
        		if(clickUsingJavaScript(driver, edit.getBackButton(10), "back button", action.BOOLEAN)) {
        			log(LogStatus.PASS, "clicked on back button", YesNo.No);
        		}else {
					log(LogStatus.ERROR, "Not able to click on back button so cannot back on page ", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on back button so cannot back on page ");
				}
        	}else {
				log(LogStatus.ERROR, "Not able to click on save button so select light theme", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on save button so select light theme");
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on edit page so cannot select light theme", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on edit page so cannot select light theme");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M8Tc011_2_verifyLightThemeForSDG(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		ThreadSleep(5000);
		WebElement[] ele2 = {home.sdgGridSideIconsForLightTheme(SDGGridName.Deals, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10),
				home.sdgGridSideIconsForLightTheme(SDGGridName.Fundraising, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10),
				home.sdgGridSideIconsForLightTheme(SDGGridName.My_Call_List, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10)};
		String[] labelName = {"Deal","FundRaising","My Call List"};
		String[] filterIconName= {"Manage_fields","Setup","Reload"};
		SDGGridName[] sdgGridNames = {SDGGridName.Deals,SDGGridName.Fundraising,SDGGridName.My_Call_List};
		int j=0;
		for (WebElement webElements : ele2) {
			if(webElements!=null) {
				log(LogStatus.PASS, "Drop Down button is displaying for "+labelName[j], YesNo.No);
				if(click(driver, webElements, "drop down  button of "+labelName[j], action.SCROLLANDBOOLEAN)) {
					log(LogStatus.PASS, "Clicked on Drop Down button for "+labelName[j], YesNo.No);
					ThreadSleep(2000);
					int i=0;
					WebElement[] ele3 = {home.sdgGridSideIconsForLightTheme(sdgGridNames[j], SDGGridSideIcons.Manage_fields, 10),
							home.sdgGridSideIconsForLightTheme(sdgGridNames[j], SDGGridSideIcons.Setup, 10),
							home.sdgGridSideIconsForLightTheme(sdgGridNames[j], SDGGridSideIcons.Reload, 10)};
					for (WebElement webElements1 : ele3) {
						if(webElements1!=null) {
							log(LogStatus.PASS,filterIconName[i]+"Drop Down menu is displaying for "+labelName[j], YesNo.No);

						}else {
							log(LogStatus.PASS, filterIconName[i]+" Drop Down menu is not displaying for "+labelName[j], YesNo.Yes);
							sa.assertTrue(false,filterIconName[i]+ "Drop Down menu is not displaying for "+labelName[j]);
						}
						i++;
					}
				}else {
					log(LogStatus.FAIL, "Not able to Click on Drop Down button for "+labelName[j], YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on Drop Down button for "+labelName[j]);
				}

			}else {
				log(LogStatus.PASS, "Drop Down button is not displaying for "+labelName[j], YesNo.Yes);
				sa.assertTrue(false,"Drop Down button is not displaying for "+labelName[j]);
			}
			j++;
		}
		int j1=0;
		WebElement[] ele4 = {home.sdgGridSideIconsForLightTheme(SDGGridName.Deals, SDGGridSideIcons.Toggle_Filters, 10),
				home.sdgGridSideIconsForLightTheme(SDGGridName.Fundraising, SDGGridSideIcons.Toggle_Filters, 10),
				home.sdgGridSideIconsForLightTheme(SDGGridName.My_Call_List, SDGGridSideIcons.Toggle_Filters, 10)};
		for (WebElement webElements : ele4) {
			if(webElements!=null) {
				log(LogStatus.PASS, "toggle filter button is displaying for "+labelName[j1], YesNo.No);
			}else {
				log(LogStatus.FAIL, "toggle filter button is not displaying for "+labelName[j1], YesNo.Yes);
				sa.assertTrue(false,"toggle filter is not displaying for "+labelName[j1]);
			}
			j1++;
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M8Tc012_verifywrenchIconForLightTheme(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		ThreadSleep(5000);
		
		WebElement[] ele1 = {home.sdgGridSideIconsForLightTheme(SDGGridName.Deals, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10),
				home.sdgGridSideIconsForLightTheme(SDGGridName.Fundraising, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10),
				home.sdgGridSideIconsForLightTheme(SDGGridName.My_Call_List, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10)};
		String[] labelName = {"Deal","FundRaising","My Call List"};
		String dealVisibleFieldList ="Deal,Stage,Source Firm,Notes,Source Firm";
		String FundRaisingVisibleFieldList ="Fundraising,Stage,Closing,Close Date,Potential Investment(M),Status Notes";
		String MyCallListVisibleFieldList ="Name,Firm,Phone";
		for(int j=0; j<ele1.length; j++) {
			if(click(driver, ele1[j], "drop down  button of "+labelName[j], action.SCROLLANDBOOLEAN)) {
				log(LogStatus.PASS, "Clicked on Drop Down button for "+labelName[j], YesNo.No);
				WebElement ele2 = null;

				if(j==0) {
					ele2=home.sdgGridSideIconsForLightTheme(SDGGridName.Deals,SDGGridSideIcons.Manage_fields,5);
				}else if (j==1) {
					ele2=home.sdgGridSideIconsForLightTheme(SDGGridName.Fundraising,SDGGridSideIcons.Manage_fields,5);
				}else {
					ele2=home.sdgGridSideIconsForLightTheme(SDGGridName.My_Call_List,SDGGridSideIcons.Manage_fields,5);
				}
				String list="";
				if(ele2!=null) {
					log(LogStatus.PASS, "Manage Field icon is displaying and tool tip is available for "+labelName[j], YesNo.No);
					ThreadSleep(3000);
					if(click(driver, ele2, "manage field icon", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "clicked on manage field icon of "+labelName[j], YesNo.No);
						List<WebElement> lst = home.sdgGridSelectVisibleFieldsListInManageFieldPopUp();
						if(j==0) {
							list=dealVisibleFieldList;
						}else if (j==1) {
							list= FundRaisingVisibleFieldList;
						}else {
							list=MyCallListVisibleFieldList;
						}
						if(compareMultipleList(driver, list, lst).isEmpty()) {
							log(LogStatus.PASS, "visible list is verfied for "+labelName[j], YesNo.No);
						}else {
							log(LogStatus.FAIL, "visible list is not verfied for "+labelName[j], YesNo.Yes);
							sa.assertTrue(false, "visible list is not verfied for "+labelName[j]);
						}
						if(click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Cancel, 10), "cancel button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "clicked on cancel button for "+labelName[j], YesNo.No);
						}else {
							log(LogStatus.PASS, "Not able to click on cancel button for "+labelName[j], YesNo.No);
							sa.assertTrue(false, "Not able to click on cancel button for "+labelName[j]);
						}
					}else {
						log(LogStatus.PASS, "Not able to click on manage field icon of "+labelName[j], YesNo.No);
						sa.assertTrue(false, "Not able to click on manage field icon of "+labelName[j]);
					}
				}else {
					log(LogStatus.PASS, "Manage field icon is not displaying so cannot check manage field icon for "+labelName[j], YesNo.Yes);
					sa.assertTrue(false,  "Manage field icon is not displaying and cannot check manage field icon for "+labelName[j]);
				}

			}else {
				log(LogStatus.FAIL, "Not able to Click on Drop Down button for "+labelName[j], YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Drop Down button for "+labelName[j]);
			}
		
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc013_verifySDGFiltersForLightTheme(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		List<WebElement> lst = new ArrayList<WebElement>();
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		ThreadSleep(5000);
		SDGGridName[] sdgGridName = {SDGGridName.Deals,SDGGridName.Fundraising,SDGGridName.My_Call_List};
		String[] labelName = {"Deal","FundRaising","My Call List"};
		String [] dropDownName = {"Stage","Closing","Firm"};
		String [] searchDataName= {null,null,"Centri"};
		Operator[] operators = {Operator.Deal_Received,Operator.Second_Closing,Operator.StartWith};
		YesNo[] searchData = {YesNo.No,YesNo.No,YesNo.Yes};
		
		for(int i=0; i<sdgGridName.length; i++) {
			if(click(driver, home.sdgGridSideIconsForLightTheme(sdgGridName[i], SDGGridSideIcons.Toggle_Filters, 10), "filter icon", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.PASS, "click on filter icon so cannot search My Call List name : "+M8CON1FName+" "+M8CON1LName, YesNo.Yes);
				if(home.SearchDealFilterDataOnHomePage(sdgGridName[i],dropDownName[i],searchDataName[i], operators[i], searchData[i])) {
					log(LogStatus.PASS, "Search My Call List Name in filter "+labelName[i], YesNo.No);
					ThreadSleep(3000);
					if(i==0) {
						lst =home.sdgGridHeadersDealsGridDealColumnsDataList(3);
						if(!lst.isEmpty()) {
							if(compareMultipleList(driver,operators[i].toString(), lst).isEmpty()) {
								log(LogStatus.PASS, "Deal Received is verfied for "+labelName[i], YesNo.No);
							}else {
								log(LogStatus.FAIL, "Deal Received is not verfied for "+labelName[i], YesNo.Yes);
								sa.assertTrue(false, "Deal Received is not verfied for "+labelName[i]);
							}
						}else {
							log(LogStatus.FAIL, "Stage data list is not found so cannot check stage list after applied filter in deal SDG", YesNo.Yes);
							sa.assertTrue(false, "Stage data list is not found so cannot check stage list after applied filter in deal SDG");
						}
						
					}else if (i==1) {
						lst =home.sdgGridHeadersFundRaisingsFundraisingColumnsDataList(3);
						if(!lst.isEmpty()) {
							if(compareMultipleList(driver,operators[i].toString(), lst).isEmpty()) {
								log(LogStatus.PASS, "2nd closing is verfied for "+labelName[i], YesNo.No);
							}else {
								log(LogStatus.FAIL, "2nd closing is not verfied for "+labelName[i], YesNo.Yes);
								sa.assertTrue(false, "2nd closing is not verfied for "+labelName[i]);
							}
						}else {
							log(LogStatus.FAIL, "2nd closing data list is not found so cannot check 2nd closing list after applied filter in Fundraising SDG", YesNo.Yes);
							sa.assertTrue(false, "2nd closing data data list is not found so cannot check 2nd closing list after applied filter in Fundraising SDG");
						}
					}else {
						lst =home.sdgGridHeadersMyCallListNameColumnsDataList(3);
						if(!lst.isEmpty()) {
							if(compareMultipleList(driver,"Centri Technology", lst).isEmpty()) {
								log(LogStatus.PASS, "Firm filter is verfied for "+labelName[i], YesNo.No);
							}else {
								log(LogStatus.FAIL, "Firm filter is not verfied for "+labelName[i], YesNo.Yes);
								sa.assertTrue(false, "Firm filter is not verfied for "+labelName[i]);
							}
						}else {
							log(LogStatus.FAIL, "Firm filter data list is not found so cannot check Firm list after applied filter in My_Call_List SDG", YesNo.Yes);
							sa.assertTrue(false, "firm filter data list is not found so cannot check Firm list after applied filter in My_Call_List SDG");
						}
					}
					click(driver, home.sdgGridSideIcons(sdgGridName[i], SDGGridSideIcons.Toggle_Filters, 10), "filter icon", action.SCROLLANDBOOLEAN);
				}else {
					log(LogStatus.FAIL, "Not able to Search "+sdgGridName[i]+" in filter "+labelName[i], YesNo.No);
					sa.assertTrue(false, "Not able to Search "+sdgGridName[i]+" Name in filter "+labelName[i]);
				}
			}else {
				log(LogStatus.FAIL, "Not able to click on filter icon so cannot search "+sdgGridName[i]+" : "+labelName[i], YesNo.Yes);
				sa.assertTrue(false, "Not able to click on filter icon so cannot search "+sdgGridName[i]+" : "+labelName[i]);
			}
		}
		refresh(driver);
		lst.removeAll(lst);
		lst =home.sdgGridHeadersDealsGridDealColumnsDataList(3);
		if(!lst.isEmpty()) {
			for(int i=lst.size()-1; i>=1; i--) {
				lst.remove(i);
			}
			if(!compareMultipleList(driver,operators[0].toString(), lst).isEmpty()) {
				log(LogStatus.PASS, "Deal Received is not visible for "+labelName[0], YesNo.No);
			}else {
				log(LogStatus.FAIL, "Deal Received is visible for "+labelName[0], YesNo.Yes);
				sa.assertTrue(false, "Deal Received is visible for "+labelName[0]);
			}
		}else {
			log(LogStatus.FAIL, "Stage data list is not found so cannot check stage list after refresh in deal SDG", YesNo.Yes);
			sa.assertTrue(false, "Stage data list is not found so cannot check stage list after refresh filter in deal SDG");
		}
		lst.removeAll(lst);
		lst =home.sdgGridHeadersFundRaisingsFundraisingColumnsDataList(3);
		if(!lst.isEmpty()) {
			for(int i=lst.size()-1; i>=3; i--) {
				lst.remove(i);
			}
			if(!compareMultipleList(driver,operators[1].toString(), lst).isEmpty()) {
				log(LogStatus.PASS, "2nd closing is not visible for "+labelName[1], YesNo.No);
			}else {
				log(LogStatus.FAIL, "2nd closing is visible for "+labelName[1], YesNo.Yes);
				sa.assertTrue(false, "2nd closing is visible for "+labelName[1]);
			}
		}else {
			log(LogStatus.FAIL, "2nd closing data list is not found so cannot check 2nd closing list after refresh in Fundraising SDG", YesNo.Yes);
			sa.assertTrue(false, "2nd closing data data list is not found so cannot check 2nd closing list after refresh in Fundraising SDG");
		}
		lst.removeAll(lst);
		lst =home.sdgGridHeadersMyCallListNameColumnsDataList(3);
		if(!lst.isEmpty()) {
			for(int i=lst.size()-1; i>=2; i--) {
				lst.remove(i);
			}
			if(!compareMultipleList(driver,"Centri Technology", lst).isEmpty()) {
				log(LogStatus.PASS, "Firm filter is not visible for "+labelName[2], YesNo.No);
			}else {
				log(LogStatus.FAIL, "Firm filter is visible for "+labelName[2], YesNo.Yes);
				sa.assertTrue(false, "Firm filter is visible for "+labelName[2]);
			}
		}else {
			log(LogStatus.FAIL, "Firm filter data list is not found so cannot check Firm list after refresh in My_Call_List SDG", YesNo.Yes);
			sa.assertTrue(false, "firm filter data list is not found so cannot check Firm list after refresh in My_Call_List SDG");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M8Tc014_verifySetupIconAndTextForLightTheme(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		ThreadSleep(5000);
		
		WebElement[] ele1 = {home.sdgGridSideIconsForLightTheme(SDGGridName.Deals, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10),
				home.sdgGridSideIconsForLightTheme(SDGGridName.Fundraising, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10),
				home.sdgGridSideIconsForLightTheme(SDGGridName.My_Call_List, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10)};
		
		String[] labelName = {"Deal","FundRaising","My Call List"};
		String[] tagNames= {"MyHome_ActiveDeals_Baseline","IR - Investors - Fundraising Pipeline - Home","Smart_Caller_MY_Home"};
		for (int i=0; i<ele1.length; i++) {
			if(click(driver, ele1[i], "drop down  button of "+labelName[i], action.SCROLLANDBOOLEAN)) {
				log(LogStatus.PASS, "Clicked on Drop Down button for "+labelName[i], YesNo.No);
				
				WebElement ele2 = null;
				if(i==0) {
					ele2=home.sdgGridSideIconsForLightTheme(SDGGridName.Deals,SDGGridSideIcons.Setup,5);
				}else if (i==1) {
					ele2=home.sdgGridSideIconsForLightTheme(SDGGridName.Fundraising,SDGGridSideIcons.Setup,5);
				}else {
					ele2=home.sdgGridSideIconsForLightTheme(SDGGridName.My_Call_List,SDGGridSideIcons.Setup,5);
				}
				if(ele2!=null) {
					if(click(driver, ele2, labelName[i]+ " open sdg record ", action.SCROLLANDBOOLEAN)) {
						String parentid = null;
						parentid=switchOnWindow(driver);
						if(parentid!=null) {
							ThreadSleep(5000);
							WebElement ele4 = FindElement(driver, "//h1//*[text()='"+tagNames[i]+"']", labelName[i]+" tag name xpath", action.BOOLEAN, 10);
							if(ele4!=null) {
								log(LogStatus.PASS, labelName[i]+" tag name is displaying ", YesNo.No);
							}else {
								log(LogStatus.PASS, labelName[i]+" tag name is not displaying ", YesNo.No);
								sa.assertTrue(false, labelName[i]+" tag name is not displaying ");
							}
							driver.close();
							driver.switchTo().window(parentid);
						}else {
							log(LogStatus.FAIL,"Not able to switch on open sdg record window of "+labelName[i], YesNo.Yes);
							sa.assertTrue(false, "Not able to switch on open sdg record window of "+labelName[i]);
						}
					}
				}else {
					log(LogStatus.FAIL,"Open_SDG_Record  is not visible in SDG Grid "+labelName[i], YesNo.Yes);
					sa.assertTrue(false, "SDG Grid is not visible in SDG Grid "+labelName[i]);
				}
			}else {
				log(LogStatus.FAIL, "Not able to Click on Drop Down button for "+labelName[i], YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Drop Down button for "+labelName[i]);
			}
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc015_verifyReloadIconForLightTheme(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		List<WebElement> lst = new ArrayList<WebElement>();
		ThreadSleep(5000);
		
		WebElement[] ele2 = {home.sdgGridSideIconsForLightTheme(SDGGridName.Deals, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10),
				home.sdgGridSideIconsForLightTheme(SDGGridName.Fundraising, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10),
				home.sdgGridSideIconsForLightTheme(SDGGridName.My_Call_List, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10)};
		
		
		String[] labelName = {"Deal","FundRaising","My Call List"};
		EditPageLabel [] editPageLabel= {EditPageLabel.Deal,EditPageLabel.Fundraising,EditPageLabel.Phone};
		EditPageLabel[] textBoxName= {EditPageLabel.Name,EditPageLabel.Name,EditPageLabel.Phone};
		String dealName="";
		String updatedName="";
		String imagePath="//AutoIT//CheckBox.PNG";
		for (int i=0; i<ele2.length; i++) {
			if(ele2[i]!=null) {
				if(i==0) {
					lst = home.sdgGridHeadersDealsNameList();
					dealName=lst.get(0).getText();
					updatedName=dealName+"Update";
				}else if (i==1) {
					lst = home.sdgGridHeadersFundRaisingsFundraisingNameList();
					dealName=lst.get(0).getText();
					updatedName=dealName+"Update";
				}else {
					lst = home.sdgGridHeadersMyCallListPhoneList();
					for(int i1=0; i1<lst.size(); i1++) {
						if(!lst.get(i1).getText().equals("")) {
							dealName=lst.get(i).getText();
							break;
						}
					}
					updatedName=dealName+"12345";
				}
				scrollDownThroughWebelement(driver, ele2[i], "");
				if (home.clickOnEditButtonOnSDGGridOnHomePage(projectName,dealName , editPageLabel[i].toString(), 10)) {
					ThreadSleep(3000);
					log(LogStatus.PASS, "mouse over on Deal Name : "+dealName, YesNo.No);
					
					WebElement ele=home.SDGInputTextbox(projectName, textBoxName[i].toString(), 10);
					sendKeys(driver, ele, updatedName, "title textbox", action.BOOLEAN);
					if (mouseHoveAndClickAction(imagePath, "checkbox")) {
						ThreadSleep(5000);

						log(LogStatus.INFO, "successfully clicked on checkbox button", YesNo.No);

						if (click(driver, home.getsdgSaveButton(projectName,10), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "successfully clicked on save button", YesNo.No);

						}else {
							log(LogStatus.ERROR, "could not click on save button", YesNo.Yes);
							sa.assertTrue(false,"could not click on save button" );
						}
						ThreadSleep(5000);
						if(click(driver, ele2[i], "drop down  button of "+labelName[i], action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "Clicked on Drop Down button for "+labelName[i], YesNo.No);
							WebElement ele1 = null;
							if(i==0) {
								ele1=home.sdgGridSideIconsForLightTheme(SDGGridName.Deals,SDGGridSideIcons.Reload,5);
							}else if (i==1) {
								ele1=home.sdgGridSideIconsForLightTheme(SDGGridName.Fundraising,SDGGridSideIcons.Reload,5);
							}else {
								ele1=home.sdgGridSideIconsForLightTheme(SDGGridName.My_Call_List,SDGGridSideIcons.Reload,5);
							}
							if (click(driver,ele1,"relaod icon "+labelName[i], action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on reload icon "+labelName[i], YesNo.No);

							}else {
								log(LogStatus.ERROR, "Not able to click on reload icon "+labelName[i], YesNo.Yes);
								sa.assertTrue(false,"Not able to click on reload icon "+labelName[i]);
							}
						}else {
							log(LogStatus.FAIL, "Not able to Click on Drop Down button for "+labelName[i], YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Drop Down button for "+labelName[i]);
						}
					}else {
						log(LogStatus.ERROR, "could not click on checkbox sikuli", YesNo.Yes);
						sa.assertTrue(false,"could not click on checkbox sikuli" );
					}
				
				}else {
					log(LogStatus.ERROR, "could not click on edit button : "+dealName, YesNo.Yes);
					sa.assertTrue(false,"could not click on edit button: "+dealName );
				}
				
			}else {
				log(LogStatus.FAIL,"Open_SDG_Record  is not visible in SDG Grid "+labelName[i], YesNo.Yes);
				sa.assertTrue(false, "SDG Grid is not visible in SDG Grid "+labelName[i]);
			}
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc016_1_verifyDarkThemeForSDG(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		ThreadSleep(5000);
		if(edit.clickOnEditPageLink()) {
			log(LogStatus.PASS, "clicked on edit page on home page", YesNo.No);
			ThreadSleep(10000);
			String[] sdgGrid = {"Deal","FundRaising","My Call List"};
			SDGGridName[] sdgNames = {SDGGridName.Deals,SDGGridName.Fundraising,SDGGridName.My_Call_List};
			switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
			for(int i=0; i<sdgNames.length; i++) {
				WebElement ele= home.sdgGridListInEditMode(sdgNames[i],20);
				scrollDownThroughWebelement(driver, ele, "");
				if(click(driver, ele, "sdg grid "+sdgNames[i], action.BOOLEAN)) {
					log(LogStatus.PASS, "clicked on SDG Grid "+(i+1), YesNo.No);
					ThreadSleep(5000);
					switchToDefaultContent(driver);
					click(driver, home.getSelectThemeinputBoxClearButton(10), "clear button", action.SCROLLANDBOOLEAN);
					ThreadSleep(1000);
					List<WebElement> themelistwebelement=home.sdgGridSelectThemeList();
					for(int i1=0; i1<themelistwebelement.size(); i1++) {
						if(themelistwebelement.get(i1).getText().equalsIgnoreCase("Dark")) {
							if(click(driver, themelistwebelement.get(i1), "Light theme xpath", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "Select Ligth Theme for "+sdgGrid[i], YesNo.No);
								break;
							}
						}else {
							log(LogStatus.PASS, "Not able to select Ligth Theme for "+sdgGrid[i], YesNo.Yes);
							sa.assertTrue(false, "Not able to select Ligth Theme for "+sdgGrid[i]);
						}
						
					}
				}else {
					log(LogStatus.PASS, "Not able to click on SDG Grid "+sdgNames[i], YesNo.Yes);
					sa.assertTrue(false, "Not able to click on SDG Grid "+sdgNames[i]);
				}
				if(i!=sdgNames.length-1) {
					switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
				}
			}
			
			ThreadSleep(2000);
			if(click(driver, home.getCustomTabSaveBtn(projectName, 10), "save button", action.BOOLEAN)) {
        		log(LogStatus.INFO, "clicked on save button", YesNo.No);
        		ThreadSleep(7000);
        		if(clickUsingJavaScript(driver, edit.getBackButton(10), "back button", action.BOOLEAN)) {
        			log(LogStatus.PASS, "clicked on back button", YesNo.No);
        		}else {
					log(LogStatus.ERROR, "Not able to click on back button so cannot back on page ", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on back button so cannot back on page ");
				}
        	}else {
				log(LogStatus.ERROR, "Not able to click on save button so select light theme", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on save button so select light theme");
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on edit page so cannot select light theme", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on edit page so cannot select light theme");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc016_2_verifyDarkThemeForSDG(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		ThreadSleep(5000);
		WebElement[] ele2 = {home.sdgGridSideIconsForLightTheme(SDGGridName.Deals, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10),
				home.sdgGridSideIconsForLightTheme(SDGGridName.Fundraising, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10),
				home.sdgGridSideIconsForLightTheme(SDGGridName.My_Call_List, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10)};
		String[] labelName = {"Deal","FundRaising","My Call List"};
		String[] filterIconName= {"Manage_fields","Setup","Reload"};
		SDGGridName[] sdgGridNames = {SDGGridName.Deals,SDGGridName.Fundraising,SDGGridName.My_Call_List};
		int j=0;
		for (WebElement webElements : ele2) {
			if(webElements!=null) {
				log(LogStatus.PASS, "Drop Down button is displaying for "+labelName[j], YesNo.No);
				if(click(driver, webElements, "drop down  button of "+labelName[j], action.SCROLLANDBOOLEAN)) {
					log(LogStatus.PASS, "Clicked on Drop Down button for "+labelName[j], YesNo.No);
					ThreadSleep(2000);
					int i=0;
					WebElement[] ele3 = {home.sdgGridSideIconsForLightTheme(sdgGridNames[j], SDGGridSideIcons.Manage_fields, 10),
							home.sdgGridSideIconsForLightTheme(sdgGridNames[j], SDGGridSideIcons.Setup, 10),
							home.sdgGridSideIconsForLightTheme(sdgGridNames[j], SDGGridSideIcons.Reload, 10)};
					for (WebElement webElements1 : ele3) {
						if(webElements1!=null) {
							log(LogStatus.PASS,filterIconName[i]+"Drop Down menu is displaying for "+labelName[j], YesNo.No);

						}else {
							log(LogStatus.PASS, filterIconName[i]+" Drop Down menu is not displaying for "+labelName[j], YesNo.Yes);
							sa.assertTrue(false,filterIconName[i]+ "Drop Down menu is not displaying for "+labelName[j]);
						}
						i++;
					}
				}else {
					log(LogStatus.FAIL, "Not able to Click on Drop Down button for "+labelName[j], YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on Drop Down button for "+labelName[j]);
				}

			}else {
				log(LogStatus.PASS, "Drop Down button is not displaying for "+labelName[j], YesNo.Yes);
				sa.assertTrue(false,"Drop Down button is not displaying for "+labelName[j]);
			}
			j++;
		}
		int j1=0;
		WebElement[] ele4 = {home.sdgGridSideIconsForLightTheme(SDGGridName.Deals, SDGGridSideIcons.Toggle_Filters, 10),
				home.sdgGridSideIconsForLightTheme(SDGGridName.Fundraising, SDGGridSideIcons.Toggle_Filters, 10),
				home.sdgGridSideIconsForLightTheme(SDGGridName.My_Call_List, SDGGridSideIcons.Toggle_Filters, 10)};
		for (WebElement webElements : ele4) {
			if(webElements!=null) {
				log(LogStatus.PASS, "toggle filter button is displaying for "+labelName[j1], YesNo.No);
			}else {
				log(LogStatus.FAIL, "toggle filter button is not displaying for "+labelName[j1], YesNo.Yes);
				sa.assertTrue(false,"toggle filter is not displaying for "+labelName[j1]);
			}
			j1++;
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc017_verifywrenchIconForDarkTheme(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		ThreadSleep(5000);
		
		WebElement[] ele1 = {home.sdgGridSideIconsForLightTheme(SDGGridName.Deals, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10),
				home.sdgGridSideIconsForLightTheme(SDGGridName.Fundraising, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10),
				home.sdgGridSideIconsForLightTheme(SDGGridName.My_Call_List, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10)};
		String[] labelName = {"Deal","FundRaising","My Call List"};
		String dealVisibleFieldList ="Deal,Stage,Source Firm,Notes,Source Firm";
		String FundRaisingVisibleFieldList ="Fundraising,Stage,Closing,Close Date,Potential Investment(M),Status Notes";
		String MyCallListVisibleFieldList ="Name,Firm,Phone";
		for(int j=0; j<ele1.length; j++) {
			if(click(driver, ele1[j], "drop down  button of "+labelName[j], action.SCROLLANDBOOLEAN)) {
				log(LogStatus.PASS, "Clicked on Drop Down button for "+labelName[j], YesNo.No);
				WebElement ele2 = null;

				if(j==0) {
					ele2=home.sdgGridSideIconsForLightTheme(SDGGridName.Deals,SDGGridSideIcons.Manage_fields,5);
				}else if (j==1) {
					ele2=home.sdgGridSideIconsForLightTheme(SDGGridName.Fundraising,SDGGridSideIcons.Manage_fields,5);
				}else {
					ele2=home.sdgGridSideIconsForLightTheme(SDGGridName.My_Call_List,SDGGridSideIcons.Manage_fields,5);
				}
				String list="";
				if(ele2!=null) {
					log(LogStatus.PASS, "Manage Field icon is displaying and tool tip is available for "+labelName[j], YesNo.No);
					ThreadSleep(3000);
					if(click(driver, ele2, "manage field icon", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "clicked on manage field icon of "+labelName[j], YesNo.No);
						List<WebElement> lst = home.sdgGridSelectVisibleFieldsListInManageFieldPopUp();
						if(j==0) {
							list=dealVisibleFieldList;
						}else if (j==1) {
							list= FundRaisingVisibleFieldList;
						}else {
							list=MyCallListVisibleFieldList;
						}
						if(compareMultipleList(driver, list, lst).isEmpty()) {
							log(LogStatus.PASS, "visible list is verfied for "+labelName[j], YesNo.No);
						}else {
							log(LogStatus.FAIL, "visible list is not verfied for "+labelName[j], YesNo.Yes);
							sa.assertTrue(false, "visible list is not verfied for "+labelName[j]);
						}
						if(click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Cancel, 10), "cancel button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "clicked on cancel button for "+labelName[j], YesNo.No);
						}else {
							log(LogStatus.PASS, "Not able to click on cancel button for "+labelName[j], YesNo.No);
							sa.assertTrue(false, "Not able to click on cancel button for "+labelName[j]);
						}
					}else {
						log(LogStatus.PASS, "Not able to click on manage field icon of "+labelName[j], YesNo.No);
						sa.assertTrue(false, "Not able to click on manage field icon of "+labelName[j]);
					}
				}else {
					log(LogStatus.PASS, "Manage field icon is not displaying so cannot check manage field icon for "+labelName[j], YesNo.Yes);
					sa.assertTrue(false,  "Manage field icon is not displaying and cannot check manage field icon for "+labelName[j]);
				}

			}else {
				log(LogStatus.FAIL, "Not able to Click on Drop Down button for "+labelName[j], YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Drop Down button for "+labelName[j]);
			}
		
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc018_verifySDGFiltersForDarkTheme(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		List<WebElement> lst = new ArrayList<WebElement>();
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		ThreadSleep(5000);
		SDGGridName[] sdgGridName = {SDGGridName.Deals,SDGGridName.Fundraising,SDGGridName.My_Call_List};
		String[] labelName = {"Deal","FundRaising","My Call List"};
		String [] dropDownName = {"Stage","Closing","Firm"};
		String [] searchDataName= {null,null,"Centri"};
		Operator[] operators = {Operator.Deal_Received,Operator.Second_Closing,Operator.StartWith};
		YesNo[] searchData = {YesNo.No,YesNo.No,YesNo.Yes};
		
		for(int i=0; i<sdgGridName.length; i++) {
			if(click(driver, home.sdgGridSideIconsForLightTheme(sdgGridName[i], SDGGridSideIcons.Toggle_Filters, 10), "filter icon", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.PASS, "click on filter icon so cannot search My Call List name : "+M8CON1FName+" "+M8CON1LName, YesNo.Yes);
				if(home.SearchDealFilterDataOnHomePage(sdgGridName[i],dropDownName[i],searchDataName[i], operators[i], searchData[i])) {
					log(LogStatus.PASS, "Search My Call List Name in filter "+labelName[i], YesNo.No);
					ThreadSleep(3000);
					if(i==0) {
						lst =home.sdgGridHeadersDealsGridDealColumnsDataList(3);
						if(!lst.isEmpty()) {
							if(compareMultipleList(driver,operators[i].toString(), lst).isEmpty()) {
								log(LogStatus.PASS, "Deal Received is verfied for "+labelName[i], YesNo.No);
							}else {
								log(LogStatus.FAIL, "Deal Received is not verfied for "+labelName[i], YesNo.Yes);
								sa.assertTrue(false, "Deal Received is not verfied for "+labelName[i]);
							}
						}else {
							log(LogStatus.FAIL, "Stage data list is not found so cannot check stage list after applied filter in deal SDG", YesNo.Yes);
							sa.assertTrue(false, "Stage data list is not found so cannot check stage list after applied filter in deal SDG");
						}
						
					}else if (i==1) {
						lst =home.sdgGridHeadersFundRaisingsFundraisingColumnsDataList(4);
						if(!lst.isEmpty()) {
							if(compareMultipleList(driver,operators[i].toString(), lst).isEmpty()) {
								log(LogStatus.PASS, "2nd closing is verfied for "+labelName[i], YesNo.No);
							}else {
								log(LogStatus.FAIL, "2nd closing is not verfied for "+labelName[i], YesNo.Yes);
								sa.assertTrue(false, "2nd closing is not verfied for "+labelName[i]);
							}
						}else {
							log(LogStatus.FAIL, "2nd closing data list is not found so cannot check 2nd closing list after applied filter in Fundraising SDG", YesNo.Yes);
							sa.assertTrue(false, "2nd closing data data list is not found so cannot check 2nd closing list after applied filter in Fundraising SDG");
						}
					}else {
						lst =home.sdgGridHeadersMyCallListNameColumnsDataList(3);
						if(!lst.isEmpty()) {
							if(compareMultipleList(driver,"Centri Technology", lst).isEmpty()) {
								log(LogStatus.PASS, "Firm filter is verfied for "+labelName[i], YesNo.No);
							}else {
								log(LogStatus.FAIL, "Firm filter is not verfied for "+labelName[i], YesNo.Yes);
								sa.assertTrue(false, "Firm filter is not verfied for "+labelName[i]);
							}
						}else {
							log(LogStatus.FAIL, "Firm filter data list is not found so cannot check Firm list after applied filter in My_Call_List SDG", YesNo.Yes);
							sa.assertTrue(false, "firm filter data list is not found so cannot check Firm list after applied filter in My_Call_List SDG");
						}
					}
					click(driver, home.sdgGridSideIcons(sdgGridName[i], SDGGridSideIcons.Toggle_Filters, 10), "filter icon", action.SCROLLANDBOOLEAN);
				}else {
					log(LogStatus.FAIL, "Not able to Search "+sdgGridName[i]+" in filter "+labelName[i], YesNo.No);
					sa.assertTrue(false, "Not able to Search "+sdgGridName[i]+" Name in filter "+labelName[i]);
				}
			}else {
				log(LogStatus.FAIL, "Not able to click on filter icon so cannot search "+sdgGridName[i]+" : "+labelName[i], YesNo.Yes);
				sa.assertTrue(false, "Not able to click on filter icon so cannot search "+sdgGridName[i]+" : "+labelName[i]);
			}
		}
		refresh(driver);
		lst.removeAll(lst);
		lst =home.sdgGridHeadersDealsGridDealColumnsDataList(3);
		if(!lst.isEmpty()) {
			for(int i=lst.size()-1; i>=1; i--) {
				lst.remove(i);
			}
			if(!compareMultipleList(driver,operators[0].toString(), lst).isEmpty()) {
				log(LogStatus.PASS, "Deal Received is not visible for "+labelName[0], YesNo.No);
			}else {
				log(LogStatus.FAIL, "Deal Received is visible for "+labelName[0], YesNo.Yes);
				sa.assertTrue(false, "Deal Received is visible for "+labelName[0]);
			}
		}else {
			log(LogStatus.FAIL, "Stage data list is not found so cannot check stage list after refresh in deal SDG", YesNo.Yes);
			sa.assertTrue(false, "Stage data list is not found so cannot check stage list after refresh filter in deal SDG");
		}
		lst.removeAll(lst);
		lst =home.sdgGridHeadersFundRaisingsFundraisingColumnsDataList(4);
		if(!lst.isEmpty()) {
			for(int i=lst.size()-1; i>=3; i--) {
				lst.remove(i);
			}
			if(!compareMultipleList(driver,operators[1].toString(), lst).isEmpty()) {
				log(LogStatus.PASS, "2nd closing is not visible for "+labelName[1], YesNo.No);
			}else {
				log(LogStatus.FAIL, "2nd closing is visible for "+labelName[1], YesNo.Yes);
				sa.assertTrue(false, "2nd closing is visible for "+labelName[1]);
			}
		}else {
			log(LogStatus.FAIL, "2nd closing data list is not found so cannot check 2nd closing list after refresh in Fundraising SDG", YesNo.Yes);
			sa.assertTrue(false, "2nd closing data data list is not found so cannot check 2nd closing list after refresh in Fundraising SDG");
		}
		lst.removeAll(lst);
		lst =home.sdgGridHeadersMyCallListNameColumnsDataList(3);
		if(!lst.isEmpty()) {
			for(int i=lst.size()-1; i>=2; i--) {
				lst.remove(i);
			}
			if(!compareMultipleList(driver,"Centri Technology", lst).isEmpty()) {
				log(LogStatus.PASS, "Firm filter is not visible for "+labelName[2], YesNo.No);
			}else {
				log(LogStatus.FAIL, "Firm filter is visible for "+labelName[2], YesNo.Yes);
				sa.assertTrue(false, "Firm filter is visible for "+labelName[2]);
			}
		}else {
			log(LogStatus.FAIL, "Firm filter data list is not found so cannot check Firm list after refresh in My_Call_List SDG", YesNo.Yes);
			sa.assertTrue(false, "firm filter data list is not found so cannot check Firm list after refresh in My_Call_List SDG");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc019_verifySetupIconAndTextForDarkTheme(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		ThreadSleep(5000);
		
		WebElement[] ele1 = {home.sdgGridSideIconsForLightTheme(SDGGridName.Deals, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10),
				home.sdgGridSideIconsForLightTheme(SDGGridName.Fundraising, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10),
				home.sdgGridSideIconsForLightTheme(SDGGridName.My_Call_List, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10)};
		
		String[] labelName = {"Deal","FundRaising","My Call List"};
		String[] tagNames= {"MyHome_ActiveDeals_Baseline","IR - Investors - Fundraising Pipeline - Home","Smart_Caller_MY_Home"};
		for (int i=0; i<ele1.length; i++) {
			if(click(driver, ele1[i], "drop down  button of "+labelName[i], action.SCROLLANDBOOLEAN)) {
				log(LogStatus.PASS, "Clicked on Drop Down button for "+labelName[i], YesNo.No);
				
				WebElement ele2 = null;
				if(i==0) {
					ele2=home.sdgGridSideIconsForLightTheme(SDGGridName.Deals,SDGGridSideIcons.Setup,5);
				}else if (i==1) {
					ele2=home.sdgGridSideIconsForLightTheme(SDGGridName.Fundraising,SDGGridSideIcons.Setup,5);
				}else {
					ele2=home.sdgGridSideIconsForLightTheme(SDGGridName.My_Call_List,SDGGridSideIcons.Setup,5);
				}
				if(ele2!=null) {
					if(click(driver, ele2, labelName[i]+ " open sdg record ", action.SCROLLANDBOOLEAN)) {
						String parentid = null;
						parentid=switchOnWindow(driver);
						if(parentid!=null) {
							ThreadSleep(5000);
							WebElement ele4 = FindElement(driver, "//h1//*[text()='"+tagNames[i]+"']", labelName[i]+" tag name xpath", action.BOOLEAN, 10);
							if(ele4!=null) {
								log(LogStatus.PASS, labelName[i]+" tag name is displaying ", YesNo.No);
							}else {
								log(LogStatus.PASS, labelName[i]+" tag name is not displaying ", YesNo.No);
								sa.assertTrue(false, labelName[i]+" tag name is not displaying ");
							}
							driver.close();
							driver.switchTo().window(parentid);
						}else {
							log(LogStatus.FAIL,"Not able to switch on open sdg record window of "+labelName[i], YesNo.Yes);
							sa.assertTrue(false, "Not able to switch on open sdg record window of "+labelName[i]);
						}
					}
				}else {
					log(LogStatus.FAIL,"Open_SDG_Record  is not visible in SDG Grid "+labelName[i], YesNo.Yes);
					sa.assertTrue(false, "SDG Grid is not visible in SDG Grid "+labelName[i]);
				}
			}else {
				log(LogStatus.FAIL, "Not able to Click on Drop Down button for "+labelName[i], YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Drop Down button for "+labelName[i]);
			}
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc020_verifyReloadIconForDarkTheme(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		List<WebElement> lst = new ArrayList<WebElement>();
		ThreadSleep(5000);
		
		WebElement[] ele2 = {home.sdgGridSideIconsForLightTheme(SDGGridName.Deals, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10),
				home.sdgGridSideIconsForLightTheme(SDGGridName.Fundraising, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10),
				home.sdgGridSideIconsForLightTheme(SDGGridName.My_Call_List, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10)};
		
		
		String[] labelName = {"Deal","FundRaising","My Call List"};
		EditPageLabel [] editPageLabel= {EditPageLabel.Deal,EditPageLabel.Fundraising,EditPageLabel.Phone};
		EditPageLabel[] textBoxName= {EditPageLabel.Name,EditPageLabel.Name,EditPageLabel.Phone};
		String dealName="";
		String updatedName="";
		String imagePath="//AutoIT//CheckBoxForDarkTheme.PNG";
		for (int i=0; i<ele2.length; i++) {
			if(ele2[i]!=null) {
				if(i==0) {
					lst = home.sdgGridHeadersDealsNameList();
					dealName=lst.get(0).getText();
					updatedName=dealName+"Update";
				}else if (i==1) {
					lst = home.sdgGridHeadersFundRaisingsFundraisingNameList();
					dealName=lst.get(0).getText();
					updatedName=dealName+"Update";
				}else {
					lst = home.sdgGridHeadersMyCallListPhoneList();
					for(int i1=0; i1<lst.size(); i1++) {
						if(!lst.get(i1).getText().equals("")) {
							dealName=lst.get(i).getText();
							break;
						}
					}
					updatedName=dealName+"12345";
				}
				scrollDownThroughWebelement(driver, ele2[i], "");
				if (home.clickOnEditButtonOnSDGGridOnHomePage(projectName,dealName , editPageLabel[i].toString(), 10)) {
					ThreadSleep(3000);
					log(LogStatus.PASS, "mouse over on Deal Name : "+dealName, YesNo.No);
					
					WebElement ele=home.SDGInputTextbox(projectName, textBoxName[i].toString(), 10);
					sendKeys(driver, ele, updatedName, "title textbox", action.BOOLEAN);
					if (mouseHoveAndClickAction(imagePath, "checkbox")) {
						ThreadSleep(5000);

						log(LogStatus.INFO, "successfully clicked on checkbox button", YesNo.No);

						if (click(driver, home.getsdgSaveButton(projectName,10), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "successfully clicked on save button", YesNo.No);

						}else {
							log(LogStatus.ERROR, "could not click on save button", YesNo.Yes);
							sa.assertTrue(false,"could not click on save button" );
						}
						ThreadSleep(5000);
						if(click(driver, ele2[i], "drop down  button of "+labelName[i], action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "Clicked on Drop Down button for "+labelName[i], YesNo.No);
							WebElement ele1 = null;
							if(i==0) {
								ele1=home.sdgGridSideIconsForLightTheme(SDGGridName.Deals,SDGGridSideIcons.Reload,5);
							}else if (i==1) {
								ele1=home.sdgGridSideIconsForLightTheme(SDGGridName.Fundraising,SDGGridSideIcons.Reload,5);
							}else {
								ele1=home.sdgGridSideIconsForLightTheme(SDGGridName.My_Call_List,SDGGridSideIcons.Reload,5);
							}
							if (click(driver,ele1,"relaod icon "+labelName[i], action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on reload icon "+labelName[i], YesNo.No);

							}else {
								log(LogStatus.ERROR, "Not able to click on reload icon "+labelName[i], YesNo.Yes);
								sa.assertTrue(false,"Not able to click on reload icon "+labelName[i]);
							}
						}else {
							log(LogStatus.FAIL, "Not able to Click on Drop Down button for "+labelName[i], YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Drop Down button for "+labelName[i]);
						}
					}else {
						log(LogStatus.ERROR, "could not click on checkbox sikuli", YesNo.Yes);
						sa.assertTrue(false,"could not click on checkbox sikuli" );
					}
				
				}else {
					log(LogStatus.ERROR, "could not click on edit button : "+dealName, YesNo.Yes);
					sa.assertTrue(false,"could not click on edit button: "+dealName );
				}
				
			}else {
				log(LogStatus.FAIL,"Open_SDG_Record  is not visible in SDG Grid "+labelName[i], YesNo.Yes);
				sa.assertTrue(false, "SDG Grid is not visible in SDG Grid "+labelName[i]);
			}
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M8Tc021_1_selectStandardThemeForSDG(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		ThreadSleep(5000);
		if(edit.clickOnEditPageLink()) {
			log(LogStatus.PASS, "clicked on edit page on home page", YesNo.No);
			ThreadSleep(10000);
			String[] sdgGrid = {"Deal","FundRaising","My Call List"};
			SDGGridName[] sdgNames = {SDGGridName.Deals,SDGGridName.Fundraising,SDGGridName.My_Call_List};
			switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
			for(int i=0; i<sdgNames.length; i++) {
				WebElement ele= home.sdgGridListInEditMode(sdgNames[i],20);
				scrollDownThroughWebelement(driver, ele, "");
				if(click(driver, ele, "sdg grid "+sdgNames[i], action.BOOLEAN)) {
					log(LogStatus.PASS, "clicked on SDG Grid "+(i+1), YesNo.No);
					ThreadSleep(5000);
					switchToDefaultContent(driver);
					click(driver, home.getSelectThemeinputBoxClearButton(10), "clear button", action.SCROLLANDBOOLEAN);
					ThreadSleep(1000);
					List<WebElement> themelistwebelement=home.sdgGridSelectThemeList();
					for(int i1=0; i1<themelistwebelement.size(); i1++) {
						if(themelistwebelement.get(i1).getText().equalsIgnoreCase("Standard")) {
							if(click(driver, themelistwebelement.get(i1), "Light theme xpath", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "Select Ligth Theme for "+sdgGrid[i], YesNo.No);
								break;
							}
						}else {
							log(LogStatus.PASS, "Not able to select Ligth Theme for "+sdgGrid[i], YesNo.Yes);
							sa.assertTrue(false, "Not able to select Ligth Theme for "+sdgGrid[i]);
						}
						
					}
				}else {
					log(LogStatus.PASS, "Not able to click on SDG Grid "+sdgNames[i], YesNo.Yes);
					sa.assertTrue(false, "Not able to click on SDG Grid "+sdgNames[i]);
				}
				if(i!=sdgNames.length-1) {
					switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
				}
			}
			
			ThreadSleep(2000);
			if(click(driver, home.getCustomTabSaveBtn(projectName, 10), "save button", action.BOOLEAN)) {
        		log(LogStatus.INFO, "clicked on save button", YesNo.No);
        		ThreadSleep(7000);
        		if(clickUsingJavaScript(driver, edit.getBackButton(10), "back button", action.BOOLEAN)) {
        			log(LogStatus.PASS, "clicked on back button", YesNo.No);
        		}else {
					log(LogStatus.ERROR, "Not able to click on back button so cannot back on page ", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on back button so cannot back on page ");
				}
        	}else {
				log(LogStatus.ERROR, "Not able to click on save button so select light theme", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on save button so select light theme");
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on edit page so cannot select light theme", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on edit page so cannot select light theme");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
		
	@Parameters({ "projectName"})
	@Test
	public void M8Tc021_2_updateDealFundRaisingMyCallListWithMaxChar(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		List<WebElement> lst = new ArrayList<WebElement>();
		ThreadSleep(5000);
		String [] dropDownName = {"Deal","Fundraising"};
		
		Operator[] operators = {Operator.StartWith,Operator.StartWith};
		YesNo[] searchData = {YesNo.Yes,YesNo.Yes,YesNo.Yes};
		String[] labelName = {"Deal","FundRaising"};
		SDGGridName[] sdgGridName = {SDGGridName.Deals,SDGGridName.Fundraising};
		EditPageLabel [] editPageLabel= {EditPageLabel.Deal,EditPageLabel.Fundraising};
		EditPageLabel[] textBoxName= {EditPageLabel.Name,EditPageLabel.Name};
		String dealName="";
		String updatedName="";
		String imagePath="//AutoIT//CheckBox.PNG";
		for (int i=0; i<sdgGridName.length; i++) {

			if(i==0) {
				lst = home.sdgGridHeadersDealsNameList();
				dealName=lst.get(0).getText();
				updatedName=dealName+"#@&*%.()'';:,#@&*%.(";
			}else if (i==1) {
				lst = home.sdgGridHeadersFundRaisingsFundraisingNameList();
				dealName=lst.get(0).getText();
				updatedName=dealName+"#@&*%.()'';:,#@&*%.(";
			}
			scrollDownThroughWebelement(driver, home.sdgGridHeaderName(sdgGridName[i], 10), "");
			if (home.clickOnEditButtonOnSDGGridOnHomePage(projectName,dealName , editPageLabel[i].toString(), 10)) {
				ThreadSleep(3000);
				log(LogStatus.PASS, "mouse over on Deal Name : "+dealName, YesNo.No);

				WebElement ele=home.SDGInputTextbox(projectName, textBoxName[i].toString(), 10);
				sendKeys(driver, ele, updatedName, "title textbox", action.BOOLEAN);
				if (mouseHoveAndClickAction(imagePath, "checkbox")) {
					ThreadSleep(5000);

					log(LogStatus.INFO, "successfully clicked on checkbox button", YesNo.No);

					if (click(driver, home.getsdgSaveButton(projectName,10), "save", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "successfully clicked on save button", YesNo.No);
						ThreadSleep(7000);
						if(click(driver, home.sdgGridSideIcons(sdgGridName[i], SDGGridSideIcons.Toggle_Filters, 10), "filter icon", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "click on filter icon so cannot search My Call List name : "+M8CON1FName+" "+M8CON1LName, YesNo.No);
							if(home.SearchDealFilterDataOnHomePage(sdgGridName[i],dropDownName[i],updatedName, operators[i], searchData[i])) {
								log(LogStatus.PASS, "Search My Call List Name in filter "+labelName[i], YesNo.No);
								ThreadSleep(5000);
								if(i==0) {
									lst = home.sdgGridHeadersDealsNameListForToolTip();
									
								}else if (i==1) {
									lst = home.sdgGridHeadersFundRaisingsFundraisingNameListForToolTip();
									
								}
								if(updatedName.contains(lst.get(0).getAttribute("title").trim())) {
									log(LogStatus.PASS, "Tool Tip is present for data "+updatedName, YesNo.No);
								}else {
									log(LogStatus.PASS, "Tool Tip is not present for data "+updatedName, YesNo.Yes);
									sa.assertTrue(false, "Tool Tip is not present for data "+updatedName);
								}
								click(driver, home.sdgGridSideIcons(sdgGridName[i], SDGGridSideIcons.Toggle_Filters, 10), "filter icon", action.SCROLLANDBOOLEAN);
							}else {
								log(LogStatus.FAIL, "Not able to Search "+sdgGridName[i]+" in filter "+labelName[i], YesNo.No);
								sa.assertTrue(false, "Not able to Search "+sdgGridName[i]+" Name in filter "+labelName[i]);
							}
						}else {
							log(LogStatus.FAIL, "Not able to click on filter icon so cannot search "+sdgGridName[i]+" : "+labelName[i], YesNo.Yes);
							sa.assertTrue(false, "Not able to click on filter icon so cannot search "+sdgGridName[i]+" : "+labelName[i]);
						}

					}else {
						log(LogStatus.ERROR, "could not click on save button", YesNo.Yes);
						sa.assertTrue(false,"could not click on save button" );
					}
					ThreadSleep(5000);

				}else {
					log(LogStatus.ERROR, "could not click on checkbox sikuli", YesNo.Yes);
					sa.assertTrue(false,"could not click on checkbox sikuli" );
				}

			}else {
				log(LogStatus.ERROR, "could not click on edit button : "+dealName, YesNo.Yes);
				sa.assertTrue(false,"could not click on edit button: "+dealName );
			}


		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc022_verifyDefaultInlineEditIconForStandardTheme(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		List<WebElement> lst = new ArrayList<WebElement>();
		ThreadSleep(5000);
		String[] labelName = {"Deal","FundRaising"};
		SDGGridName[] sdgGridName = {SDGGridName.Deals,SDGGridName.Fundraising};
		EditPageLabel [] editPageLabel= {EditPageLabel.Deal,EditPageLabel.Fundraising};
		EditPageLabel[] textBoxName= {EditPageLabel.Name,EditPageLabel.Name};
		String dealName="";
		for (int i=0; i<sdgGridName.length; i++) {
			if(i==0) {
				lst = home.sdgGridHeadersDealsNameList();
				dealName=lst.get(0).getText();
				
			}else if (i==1) {
				lst = home.sdgGridHeadersFundRaisingsFundraisingNameList();
				dealName=lst.get(0).getText();
				
			}else {
				lst = home.sdgGridHeadersMyCallListPhoneList();
				dealName=lst.get(0).getText();
			}
			scrollDownThroughWebelement(driver, home.sdgGridHeaderName(sdgGridName[i], 10), "");
			if(i!=2) {
				if (home.clickOnEditButtonOnSDGGridOnHomePage(projectName,dealName , editPageLabel[i].toString(), 10)) {
					ThreadSleep(3000);
					log(LogStatus.PASS, "mouse over on Deal Name : "+dealName, YesNo.No);
					WebElement ele=home.SDGInputTextbox(projectName, textBoxName[i].toString(), 10);
					if (ele!=null) {
						ThreadSleep(5000);
						log(LogStatus.INFO, "successfully clicked on checkbox button", YesNo.No);
						click(driver, home.getsdgCancelButton(projectName,10), "save", action.SCROLLANDBOOLEAN);
						
						ThreadSleep(5000);
						
					}else {
						log(LogStatus.ERROR, "Pencil Icon is not displaying for "+labelName[i], YesNo.Yes);
						sa.assertTrue(false,"Pencil Icon is not displaying for "+labelName[i]);
					}
					
				}else {
					log(LogStatus.ERROR, "could not click on edit button : "+dealName, YesNo.Yes);
					sa.assertTrue(false,"could not click on edit button: "+dealName );
				}
			}else {
				if (!home.clickOnEditButtonOnSDGGridOnHomePage(projectName,dealName , editPageLabel[i].toString(), 10)) {
					log(LogStatus.ERROR, "clicked on edit button is present : "+dealName, YesNo.Yes);
					WebElement ele=home.SDGInputTextbox(projectName, textBoxName[i].toString(), 10);
					if (ele!=null) {
						log(LogStatus.INFO, "Pencil Icon is displaying for "+labelName[i], YesNo.No);
						sa.assertTrue(false,"Pencil Icon is displaying for "+labelName[i]);
						
					}else {
						log(LogStatus.ERROR, "Pencil Icon is not displaying for "+labelName[i], YesNo.Yes);
						
					}
				}else {
					log(LogStatus.ERROR, "edit button is not present : "+dealName, YesNo.Yes);
				}
			}
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc023_verifyInlineEditInDealGridForStandardTheme(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		List<WebElement> lst = new ArrayList<WebElement>();
		ThreadSleep(5000);
		String[] labelName = {"Deal"};
		SDGGridName[] sdgGridName = {SDGGridName.Deals};
		EditPageLabel [] editPageLabel= {EditPageLabel.Stage,EditPageLabel.Source_Firm};
		for (int i=0; i<sdgGridName.length; i++) {
			lst = home.sdgGridCheckBoxList(sdgGridName[i]);
			List<WebElement> dealNameList= home.sdgGridHeadersDealsNameList();
			if(!lst.isEmpty()) {
				for(int j=0; j<3; j++) {
					String dealNameFromUI=dealNameList.get(j).getText();
					if(clickUsingJavaScript(driver, lst.get(j), "check list", action.BOOLEAN)) {
						log(LogStatus.PASS, "clicked on check box : "+dealNameFromUI, YesNo.No);



					}else {
						log(LogStatus.FAIL, "Not able to click on check box : "+dealNameFromUI, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on check box : "+dealNameFromUI);
					}
				}
			}else {
				log(LogStatus.FAIL, "Deal SDG grid check box list is not visible so cannot update stage column values", YesNo.Yes);
				sa.assertTrue(false, "Deal SDG grid check box list is not visible so cannot update stage column values");
				exit("Deal SDG grid check box list is not visible so cannot update stage column values");
			}
			List<WebElement> stageDataList = home.sdgGridHeadersDealsAndFundRaisingStageColumnList(sdgGridName[i]);
			ThreadSleep(1000);
			if(home.clickOnEditButtonOnSDGGridOnHomePage(projectName, stageDataList.get(i).getText().toString(), editPageLabel[i].toString(), 10)) {
				log(LogStatus.PASS, "clicked on edit icon of "+dealNameList.get(i), YesNo.No);
				ThreadSleep(3000);
				if(clickUsingJavaScript(driver, home.sdgGridSideDealStageColumnDropDownListInEditMode(sdgGridName[i], 10), "", action.BOOLEAN)) {
					log(LogStatus.PASS, "clicked on stage drop down list in edit mode", YesNo.No);
					ThreadSleep(2000);
					WebElement ele = FindElement(driver, "//span[text()='Deal Received']", "", action.BOOLEAN,5);
					if(click(driver, ele,"Deal received value", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "clicked on Deal received value", YesNo.No);
						ThreadSleep(2000);
						if(click(driver, home.sdgGridSideDealStageColumnUpdateSelecteditemsCheckBox(sdgGridName[i],3,3, 10), "Update3SelecteditemsCheckBox", action.BOOLEAN)) {
							log(LogStatus.PASS, "clicked on Update 3 Selected items CheckBox", YesNo.No);
							ThreadSleep(1000);
							if(click(driver, home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Apply, 10), "apply button", action.BOOLEAN)) {
								log(LogStatus.PASS, "clicked on apply button", YesNo.No);
								ThreadSleep(5000);
								stageDataList = home.sdgGridHeadersDealsAndFundRaisingStageColumnList(sdgGridName[i]);

								if(!stageDataList.isEmpty()) {
									for(int k=0; k<stageDataList.size()-7; k++) {
										String s  = stageDataList.get(k).getText().trim();
										if(s.equalsIgnoreCase("Deal Received")) {
											log(LogStatus.PASS, labelName[i]+" SDG Grid Header Name is verified ", YesNo.No);
										}else {
											log(LogStatus.FAIL,labelName[i]+" SDG Grid Header Name is not verified ", YesNo.Yes);
											sa.assertTrue(false, labelName[i]+" SDG Grid Header Name is not verified ");
										}
									}
									
									click(driver, home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Save, 10), "Save button", action.BOOLEAN);
									
								}else {
									log(LogStatus.FAIL, "Stage data list is not found so cannot check stage list after Update value in deal SDG", YesNo.Yes);
									sa.assertTrue(false, "Stage data list is not found so cannot check stage list after update value in deal SDG");
								}

							}else {
								log(LogStatus.PASS, "Not able to click on apply button so cannot update stage value", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on apply button so cannot update stage value");
							}

						}else {
							log(LogStatus.FAIL, "Not able to click on Update 3 Selected items CheckBox", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on Update 3 Selected items CheckBox");
						}
					}else {
						log(LogStatus.FAIL, "Not able to click on Deal received value", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on Deal received value");
					}
				}else {
					log(LogStatus.FAIL, "Not able on stage drop down list in edit mode ", YesNo.Yes);
					sa.assertTrue(false, "Not able on stage drop down list in edit mode ");
				}


			}else {
				log(LogStatus.FAIL, "Not able to click on edit icon of "+dealNameList.get(i), YesNo.Yes);
				sa.assertTrue(false, "Not able to click on edit icon of "+dealNameList.get(i));
			}
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc024_verifyInlineEditInFundRaisingGridForStandardTheme(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		List<WebElement> lst = new ArrayList<WebElement>();
		ThreadSleep(5000);
		String StageData="";
		String[] labelName = {"Fundraising"};
		SDGGridName[] sdgGridName = {SDGGridName.Fundraising};
		EditPageLabel [] editPageLabel= {EditPageLabel.Stage,EditPageLabel.Closing};
		for (int i=0; i<sdgGridName.length; i++) {
			lst = home.sdgGridCheckBoxList(sdgGridName[i]);
			List<WebElement> FundRaisingNameList= home.sdgGridHeadersFundRaisingsFundraisingNameList();
			if(!lst.isEmpty()) {
				for(int j=0; j<5; j++) {
					String dealNameFromUI=FundRaisingNameList.get(j).getText();
					if(clickUsingJavaScript(driver, lst.get(j), "check list", action.BOOLEAN)) {
						log(LogStatus.PASS, "clicked on check box : "+dealNameFromUI, YesNo.No);



					}else {
						log(LogStatus.FAIL, "Not able to click on check box : "+dealNameFromUI, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on check box : "+dealNameFromUI);
					}
				}
			}else {
				log(LogStatus.FAIL, "Fundraising SDG grid check box list is not visible so cannot update stage column values", YesNo.Yes);
				sa.assertTrue(false, "Fundraising SDG grid check box list is not visible so cannot update stage column values");
				exit("Fundraising SDG grid check box list is not visible so cannot update stage column values");
			}
			List<WebElement> stageDataList = home.sdgGridHeadersDealsAndFundRaisingStageColumnList(sdgGridName[i]);
			ThreadSleep(1000);
			
			for(int z=0; z<stageDataList.size(); z++) {
				StageData = stageDataList.get(z).getText().trim();
				if(StageData.isEmpty()) {
					continue;
				}else {
					break;
				}
			}
			if(home.clickOnEditButtonOnSDGGridOnHomePage(projectName, StageData, editPageLabel[i].toString(), 10)) {
				log(LogStatus.PASS, "clicked on edit icon of "+FundRaisingNameList.get(i), YesNo.No);
				ThreadSleep(3000);
				if(clickUsingJavaScript(driver, home.sdgGridSideDealStageColumnDropDownListInEditMode(sdgGridName[i], 10), "", action.BOOLEAN)) {
					log(LogStatus.PASS, "clicked on stage drop down list in edit mode", YesNo.No);
					ThreadSleep(2000);
					WebElement ele = FindElement(driver, "//span[text()='Follow up Diligence']", "", action.BOOLEAN,5);
					if(click(driver, ele,"Deal received value", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "clicked on Deal received value", YesNo.No);
						ThreadSleep(2000);
						if(click(driver, home.sdgGridSideDealStageColumnUpdateSelecteditemsCheckBox(sdgGridName[i],5,3, 10), "Update5SelecteditemsCheckBox", action.BOOLEAN)) {
							log(LogStatus.PASS, "clicked on Update 3 Selected items CheckBox", YesNo.No);
							ThreadSleep(1000);
							if(click(driver, home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Apply, 10), "apply button", action.BOOLEAN)) {
								log(LogStatus.PASS, "clicked on apply button", YesNo.No);
								ThreadSleep(5000);
								stageDataList = home.sdgGridHeadersDealsAndFundRaisingStageColumnList(sdgGridName[i]);

								if(!stageDataList.isEmpty()) {
									for(int k=0; k<stageDataList.size()-5; k++) {
										String s  = stageDataList.get(k).getText().trim();
										if(s.equalsIgnoreCase("Deal Received")) {
											log(LogStatus.PASS, labelName[i]+" SDG Grid Stage value is verified ", YesNo.No);
										}else {
											log(LogStatus.FAIL,labelName[i]+" SDG Grid Stage value is not verified ", YesNo.Yes);
											sa.assertTrue(false, labelName[i]+" SDG Grid Stage value is not verified ");
										}
									}
									
									click(driver, home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Save, 10), "Save button", action.BOOLEAN);
									
								}else {
									log(LogStatus.FAIL, "Stage data list is not found so cannot check stage list after Update value in Fundraising SDG", YesNo.Yes);
									sa.assertTrue(false, "Stage data list is not found so cannot check stage list after update value in Fundraising SDG");
								}

							}else {
								log(LogStatus.PASS, "Not able to click on apply button so cannot update stage value", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on apply button so cannot update stage value");
							}

						}else {
							log(LogStatus.FAIL, "Not able to click on Update 3 Selected items CheckBox", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on Update 3 Selected items CheckBox");
						}
					}else {
						log(LogStatus.FAIL, "Not able to click on Follow up Diligence value", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on Follow up Diligence value");
					}
				}else {
					log(LogStatus.FAIL, "Not able on stage drop down list in edit mode ", YesNo.Yes);
					sa.assertTrue(false, "Not able on stage drop down list in edit mode ");
				}


			}else {
				log(LogStatus.FAIL, "Not able to click on edit icon of "+FundRaisingNameList.get(i), YesNo.Yes);
				sa.assertTrue(false, "Not able to click on edit icon of "+FundRaisingNameList.get(i));
			}
		}
		refresh(driver);
		ThreadSleep(5000);
		for (int i=0; i<sdgGridName.length; i++) {
			lst = home.sdgGridCheckBoxList(sdgGridName[i]);
			List<WebElement> FundRaisingNameList= home.sdgGridHeadersFundRaisingsFundraisingNameList();
			if(!lst.isEmpty()) {
				for(int j=0; j<lst.size(); j++) {
					String dealNameFromUI=FundRaisingNameList.get(j).getText();
					if(clickUsingJavaScript(driver, lst.get(j), "check list", action.BOOLEAN)) {
						log(LogStatus.PASS, "clicked on check box : "+dealNameFromUI, YesNo.No);



					}else {
						log(LogStatus.FAIL, "Not able to click on check box : "+dealNameFromUI, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on check box : "+dealNameFromUI);
					}
				}
			}else {
				log(LogStatus.FAIL, "Fundraising SDG grid check box list is not visible so cannot update Closing column values", YesNo.Yes);
				sa.assertTrue(false, "Fundraising SDG grid check box list is not visible so cannot update Closing column values");
				exit("Fundraising SDG grid check box list is not visible so cannot update Closing column values");
			}
			List<WebElement> stageDataList = home.sdgGridHeadersFundRaisingClosingColumnList(sdgGridName[i]);
			ThreadSleep(1000);
			
			for(int z=0; z<stageDataList.size(); z++) {
				StageData = stageDataList.get(z).getText().trim();
				if(StageData.isEmpty()) {
					continue;
				}else {
					break;
				}
			}
			if(home.clickOnEditButtonOnSDGGridOnHomePage(projectName, StageData, editPageLabel[1].toString(), 10)) {
				log(LogStatus.PASS, "clicked on edit icon of "+FundRaisingNameList.get(i), YesNo.No);
				ThreadSleep(3000);
				if(clickUsingJavaScript(driver, home.sdgGridSideFundRaisingClosingColumnDropDownListInEditMode(sdgGridName[i], 10), "", action.BOOLEAN)) {
					log(LogStatus.PASS, "clicked on Closing drop down list in edit mode", YesNo.No);
					ThreadSleep(2000);
					WebElement ele = FindElement(driver, "//span[text()='1st Closing']", "", action.BOOLEAN,5);
					if(click(driver, ele,"1st Closing value", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "clicked on 1st Closing value", YesNo.No);
						ThreadSleep(2000);
						if(click(driver, home.sdgGridSideDealStageColumnUpdateSelecteditemsCheckBox(sdgGridName[i],10,4, 10), "Update10SelecteditemsCheckBox", action.BOOLEAN)) {
							log(LogStatus.PASS, "clicked on Update 10 Selected items CheckBox", YesNo.No);
							ThreadSleep(1000);
							if(click(driver, home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Apply, 10), "apply button", action.BOOLEAN)) {
								log(LogStatus.PASS, "clicked on apply button", YesNo.No);
								ThreadSleep(5000);
								stageDataList = home.sdgGridHeadersFundRaisingClosingColumnList(sdgGridName[i]);

								if(!stageDataList.isEmpty()) {
									for(int k=0; k<stageDataList.size(); k++) {
										String s  = stageDataList.get(k).getText().trim();
										if(s.equalsIgnoreCase("1st Closing")) {
											log(LogStatus.PASS, labelName[i]+" SDG Grid Closing value is verified ", YesNo.No);
										}else {
											log(LogStatus.FAIL,labelName[i]+" SDG Grid Closing value is not verified ", YesNo.Yes);
											sa.assertTrue(false, labelName[i]+" SDG Grid Closing value is not verified ");
										}
									}
									
									click(driver, home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Save, 10), "Save button", action.BOOLEAN);
									
								}else {
									log(LogStatus.FAIL, "Closing data list is not found so cannot check Closing list after Update value in Fundraising SDG", YesNo.Yes);
									sa.assertTrue(false, "Closing data list is not found so cannot check Closing list after update value in Fundraising SDG");
								}

							}else {
								log(LogStatus.PASS, "Not able to click on apply button so cannot update Closing value", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on apply button so cannot update Closing value");
							}

						}else {
							log(LogStatus.FAIL, "Not able to click on Update 3 Selected items CheckBox", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on Update 3 Selected items CheckBox");
						}
					}else {
						log(LogStatus.FAIL, "Not able to click on 1st Closing value", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on 1st Closing value");
					}
				}else {
					log(LogStatus.FAIL, "Not able on Closing drop down list in edit mode ", YesNo.Yes);
					sa.assertTrue(false, "Not able on Closing drop down list in edit mode ");
				}


			}else {
				log(LogStatus.FAIL, "Not able to click on edit icon of "+FundRaisingNameList.get(i), YesNo.Yes);
				sa.assertTrue(false, "Not able to click on edit icon of "+FundRaisingNameList.get(i));
			}
		}
		
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc025_verifyTaskAndEventSection(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		List<WebElement> lst = new ArrayList<WebElement>();
		ThreadSleep(5000);
		if(home.todayTasksAndTodayEventsLabelText(Task.TodayTasks, 10)!=null) {
			log(LogStatus.PASS, "Today Tasks section is displaying on home page", YesNo.No);
		}else {
			log(LogStatus.FAIL, "Today Tasks section is not displaying on home page", YesNo.Yes);
			sa.assertTrue(false, "Today Tasks section is not displaying on home page");
		}
		
		if(home.todayTasksAndTodayEventsLabelText(Task.TodayEvents, 10)!=null) {
			log(LogStatus.PASS, "Today Events section is displaying on home page", YesNo.No);
		}else {
			log(LogStatus.FAIL, "Today Events section is not displaying on home page", YesNo.Yes);
			sa.assertTrue(false, "Today Events section is not displaying on home page");
		}
		if(click(driver, home.todayTasksDownArrow(Task.TodayTasks, 10), "today task down arrow", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "Clicked on today tasks down arrow ", YesNo.No);
			ThreadSleep(1000);
			TodayTaskDownArrowValues[] value = {TodayTaskDownArrowValues.Today,TodayTaskDownArrowValues.MyTasks,TodayTaskDownArrowValues.AllOverdue,TodayTaskDownArrowValues.CompletedWithinLast7Days,TodayTaskDownArrowValues.DelegatedTasks};
			for(int i=0; i<value.length; i++) {
				if(home.todayTasksDownArrowListValues(value[i], 10)!=null) {
					log(LogStatus.PASS, value[i]+" is displaying in today tasks list", YesNo.No);
				}else {
					log(LogStatus.FAIL, value[i]+" is not displaying in today tasks list", YesNo.Yes);
					sa.assertTrue(false, value[i]+" is not displaying in today tasks list");
				}
			}
		}else {
			log(LogStatus.PASS, "Not able to Click on today tasks down arrow so cannot check all values", YesNo.Yes);
			sa.assertTrue(false,  "Not able to Click on today tasks down arrow so cannot check all values");
		}
		if(home.viewAllAndviewClendarLink(Task.TodayTasks, ViewAllAndViewCalendarLink.viewALl, 10)!=null) {
			log(LogStatus.PASS, "view all link is displaying in today tasks", YesNo.No);
		}else {
			log(LogStatus.FAIL, "view all link is not displaying in today tasks", YesNo.Yes);
			sa.assertTrue(false, "view all link is not displaying in today tasks");
		}
		if(home.viewAllAndviewClendarLink(Task.TodayEvents, ViewAllAndViewCalendarLink.ViewCalendar, 10)!=null) {
			log(LogStatus.PASS, "view calendar link is displaying in today events", YesNo.No);
		}else {
			log(LogStatus.FAIL, "view calendar link is not displaying in today events", YesNo.Yes);
			sa.assertTrue(false, "view calendar link is not displaying in today events");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc026_createTaskAndEvents(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop = new CustomObjPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		WebElement ele = null;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		List<WebElement> lst = new ArrayList<WebElement>();
		ThreadSleep(5000);
		String date="";
		boolean flag=false;
		String owner = crmUser1FirstName+" "+crmUser1LastName;
		for(int i=1; i<15; i++) {
			String status =ExcelUtils.readData(phase1DataSheetFilePath, "Task1", excelLabel.Variable_Name,"M8Task"+(i), excelLabel.Status);
			String subject =ExcelUtils.readData(phase1DataSheetFilePath, "Task1", excelLabel.Variable_Name,"M8Task"+(i), excelLabel.Subject);
			String name =ExcelUtils.readData(phase1DataSheetFilePath, "Task1", excelLabel.Variable_Name,"M8Task"+(i), excelLabel.Name);
			String accountName =ExcelUtils.readData(phase1DataSheetFilePath, "Task1", excelLabel.Variable_Name,"M8Task"+(i), excelLabel.Related_To);
			if(i==11 || i==12) {
				date=yesterdaysDate;
			}else if (i==13 || i==14) {
				date=dayBeforeYesterdaysDate;
			}else {
				date=todaysDate;
			}
			if (click(driver, gp.getGlobalActionIcon(projectName, 15), "Global Action Related item", action.BOOLEAN)) {
				log(LogStatus.INFO,"Clicked on Global Action Related item",YesNo.No);
				if (clickUsingJavaScript(driver, gp.getActionItem(projectName, GlobalActionItem.New_Task, 15), "New_Task Link", action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on log call Link",YesNo.Yes);
					ThreadSleep(2000);	
					// subject
					ele=gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Task, PageLabel.Subject.toString(),10);
					if (sendKeys(driver, ele, subject, "Subject", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Entered value to Subject Text Box : "+subject, YesNo.Yes);
						ThreadSleep(1000);
						
						// Due Date
						if (sendKeys(driver, ip.getdueDateTextBoxInNewTask(projectName, 20), date, PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Entered value to Due Date Text Box : "+date, YesNo.Yes);
							ThreadSleep(1000);
							
							// Name
							ele= cp.getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, PageName.TaskPage, PageLabel.Name.toString(), action.SCROLLANDBOOLEAN,15);
							if (sendKeys(driver, ele, name,"Name Text Label", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"Enter Value to Name Text Box : "+name,YesNo.Yes);	
								ThreadSleep(1000);
								ele =  cp.getContactNameOrRelatedAssociationNameOnTask(projectName, PageName.TaskPage, PageLabel.Name.toString(),name, action.SCROLLANDBOOLEAN,15);
								if (click(driver, ele, "Select Name From Label : "+PageLabel.Name, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"Clicked on : "+name,YesNo.Yes);
								} else {
									sa.assertTrue(false,"Not Able to Click on : "+name);
									log(LogStatus.SKIP,"Not Able to Click on : "+name,YesNo.Yes);	
								}
								
							}else {
								sa.assertTrue(false,"Not Able to Enter Value to Name Text Box : "+name);
								log(LogStatus.SKIP,"Not Able to Enter Value to Name Text Box : "+name,YesNo.Yes);	
							}
							// Related To
							click(driver, ip.getrelatedAssociationsdropdownButton(projectName, PageName.TaskPage,PageLabel.Related_To.toString()
									, action.SCROLLANDBOOLEAN, 10),"dropdown button", action.SCROLLANDBOOLEAN);
							if (cp.SelectRelatedAssociationsdropdownButton(projectName, PageName.TaskPage, PageLabel.Related_To.toString(), TabName.InstituitonsTab, action.SCROLLANDBOOLEAN, 20)) {
								log(LogStatus.INFO,"Able to Select Drown Down Value : "+cp.getTabName(projectName, TabName.InstituitonsTab)+" For Label "+PageLabel.Related_Associations,YesNo.No);
								ThreadSleep(2000);	
								
								ele= cp.getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, PageName.TaskPage, PageLabel.Related_To.toString(), action.SCROLLANDBOOLEAN,15);
								if (sendKeys(driver, ele,accountName, "Related To Text Label", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"Enter Value to Related To Text Box : "+accountName,YesNo.Yes);	
									ThreadSleep(1000);
									
									ele =  cp.getContactNameOrRelatedAssociationNameOnTask(projectName, PageName.TaskPage, PageLabel.Related_To.toString(),accountName, action.SCROLLANDBOOLEAN,15);
									if (click(driver, ele, "Select TestCustomObject From Label : "+PageLabel.Related_To, action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO,"Clicked on : "+accountName,YesNo.Yes);
									} else {
										sa.assertTrue(false,"Not Able to Click on : "+accountName);
										log(LogStatus.SKIP,"Not Able to Click on : "+accountName,YesNo.Yes);	
									}
									
									
								}else {
									sa.assertTrue(false,"Not Able to Enter Value to Related To Text Box : "+accountName);
									log(LogStatus.SKIP,"Not Able to Enter Value to Related To Text Box : "+accountName,YesNo.Yes);	
								}
								
							} else {
								sa.assertTrue(false,"Not Able to Select Drown Down Value : "+cp.getTabName(projectName, TabName.InstituitonsTab)+" For Label "+PageLabel.Related_Associations);
								log(LogStatus.SKIP,"Not Able to Select Drown Down Value : "+cp.getTabName(projectName, TabName.InstituitonsTab)+" For Label "+PageLabel.Related_Associations,YesNo.Yes);
							}
							
							if(i==6 || i==7 || i==8 || i==9 || i==10) {
								if (bp.ClickOnCrossButtonForAlreadySelectedItem(projectName, PageName.TaskPage, PageLabel.Assigned_To.toString(),false, owner, action.SCROLLANDBOOLEAN, 15)) {
									log(LogStatus.INFO, "Clicked on Cross Button against : "+owner+" For Label : "+PageLabel.Assigned_To.toString(),YesNo.No);	

									ThreadSleep(2000);
									// Assigned To
									String AdminName = AdminUserFirstName+" "+AdminUserLastName;
									flag=bp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TaskPage, PageLabel.Assigned_To.toString(), TabName.TaskTab, AdminName, action.BOOLEAN, 10);		
									if (flag) {
										log(LogStatus.INFO,"Selected "+AdminName+" For  Drown Down Value For Label "+PageLabel.Assigned_To,YesNo.No);
										ThreadSleep(1000);
										ExcelUtils.writeData(phase1DataSheetFilePath,AdminName, "Task1", excelLabel.Variable_Name, "M8Task"+(i), excelLabel.Assigned_To);

									} else {
										sa.assertTrue(false,"could not select admin name in "+PageLabel.Assigned_To.toString());
										log(LogStatus.ERROR, "could not select admin name in "+PageLabel.Assigned_To.toString(),YesNo.Yes);

									}
								}else {
									sa.assertTrue(false,"cross button could not be clicked on "+PageLabel.Assigned_To.toString());
									log(LogStatus.ERROR, "cross button could not be clicked on "+PageLabel.Assigned_To.toString(),YesNo.Yes);

								}
							}else {
								ExcelUtils.writeData(phase1DataSheetFilePath,owner, "Task1", excelLabel.Variable_Name, "M8Task"+(i), excelLabel.Assigned_To);
							}
							if(click(driver, home.getCreateTaskStatusDropDown(10), "status drop down list", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.FAIL, "clicked on status drop down", YesNo.No);
								ThreadSleep(1000);
								if(click(driver, home.createTaskDropDownValue(status,10), "status drop down list", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.FAIL, "Select on status : "+status, YesNo.No);
									ThreadSleep(1000);
									
								}else {
									log(LogStatus.FAIL, "Not able to click on status drop down so cannot select "+status, YesNo.Yes);
									sa.assertTrue(false, "Not able to click on status drop down so cannot select "+status);
								}
							}else {
								log(LogStatus.FAIL, "Not able to click on status drop down so cannot select "+status, YesNo.Yes);
								sa.assertTrue(false, "Not able to click on status drop down so cannot select "+status);
							}
							if (clickUsingJavaScript(driver, gp.getSaveButtonForEvent(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"successfully created task : "+subject,  YesNo.Yes);
								ThreadSleep(1000);
								ExcelUtils.writeData(phase1DataSheetFilePath,date, "Task1", excelLabel.Variable_Name, "M8Task"+(i), excelLabel.Due_Date);
							}else {
								log(LogStatus.ERROR, "save button is not clickable so task not created : "+Smoke_TaskSTD1Subject, YesNo.Yes);
								sa.assertTrue(false,"save button is not clickable so task not created : "+Smoke_TaskSTD1Subject );
							}
						}else {
							log(LogStatus.ERROR, "duedate textbox is not visible so task could not be created", YesNo.Yes);
							sa.assertTrue(false,"duedate textbox is not visible so task could not be created" );
						}
					}else {
						log(LogStatus.ERROR, subject+" : Subject textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,subject+" :Subject textbox is not visible so task could not be created" );
					}
					
				} else {
					sa.assertTrue(false,"Not Able to Click on New Task Button for show more action");
					log(LogStatus.SKIP,"Not Able to Click on New Task Button for show more action",YesNo.Yes);
				}
				
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.TaskTab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.TaskTab,YesNo.Yes);
			}
			
		}
//		//Create Events
		String startDate = null;
		String endDate = null;
		String meetingEventSubject = null;
		boolean flag1=false,flag2=false;
		for (int k =1; k < 11; k++) {
			
			if (click(driver, gp.getGlobalActionIcon(projectName, 15), "Global Action Related item", action.BOOLEAN)) {
				log(LogStatus.INFO,"Clicked on Global Action Related item",YesNo.No);
				if (clickUsingJavaScript(driver, gp.getActionItem(projectName, GlobalActionItem.New_Event, 15), "New Event Link", action.BOOLEAN)) {
					log(LogStatus.INFO,"Clicked on New Event Link",YesNo.Yes);
					ThreadSleep(2000);	

					click(driver, gp.getMaximizeIcon(projectName, 15), "Maximize Icon", action.BOOLEAN);
					startDate=todaysDate;
					endDate=dayAfterTomorrowsDate;
					
					meetingEventSubject=ExcelUtils.readData(phase1DataSheetFilePath, "Events", excelLabel.Variable_Name,"M8Event"+(k), excelLabel.Subject);
					
					String Name=ExcelUtils.readData(phase1DataSheetFilePath, "Events", excelLabel.Variable_Name,"M8Event"+(k), excelLabel.Name);
					String accountName=ExcelUtils.readData(phase1DataSheetFilePath, "Events", excelLabel.Variable_Name,"M8Event"+(k), excelLabel.Related_To);
					String location=ExcelUtils.readData(phase1DataSheetFilePath, "Events", excelLabel.Variable_Name,"M8Event"+(k), excelLabel.Location);
					ExcelUtils.writeData(phase1DataSheetFilePath,startDate, "Events", excelLabel.Variable_Name, "M8Event"+(k), excelLabel.Start_Date);
					ExcelUtils.writeData(phase1DataSheetFilePath,endDate, "Events", excelLabel.Variable_Name, "M8Event"+(k), excelLabel.End_Date);
					
					String[][] event1 = {{PageLabel.Subject.toString(),meetingEventSubject},
							{PageLabel.Start_Date.toString(),startDate},
							{PageLabel.End_Date.toString(),endDate},
							{PageLabel.Name.toString(),Name}};
					

					flag1=gp.enterValueForNewEvent(projectName, GlobalActionItem.New_Event, event1, 10);
					
					
					clickUsingJavaScript(driver, lp.getrelatedAssociationsdropdownButton(projectName, PageName.TaskPage,PageLabel.Related_To.toString()
							, action.BOOLEAN, 10),"dropdown button", action.BOOLEAN);
					
					flag2 = gp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.NewEventPopUp, PageLabel.Related_To.toString(), TabName.InstituitonsTab, accountName, action.SCROLLANDBOOLEAN,10);		
					
					appLog.info("using related value : "+accountName);
					appLog.info(">>>>");
				//	 
					if (flag1) {
						if (flag2) {
							log(LogStatus.INFO,"Selected "+accountName+" For Label "+PageLabel.Related_To,YesNo.No);
							
							
							
							if(k==6 || k==7 || k==8 || k==9) {
								if (bp.ClickOnCrossButtonForAlreadySelectedItem(projectName, PageName.TaskPage, PageLabel.Assigned_To.toString(),false, owner, action.SCROLLANDBOOLEAN, 15)) {
									log(LogStatus.INFO, "Clicked on Cross Button against : "+owner+" For Label : "+PageLabel.Assigned_To.toString(),YesNo.No);	

									ThreadSleep(2000);
									// Assigned To
									String AdminName = AdminUserFirstName+" "+AdminUserLastName;
									flag=bp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TaskPage, PageLabel.Assigned_To.toString(), TabName.TaskTab, AdminName, action.BOOLEAN, 10);		
									if (flag) {
										log(LogStatus.INFO,"Selected "+AdminName+" For  Drown Down Value For Label "+PageLabel.Assigned_To,YesNo.No);
										ThreadSleep(1000);
										ExcelUtils.writeData(phase1DataSheetFilePath,AdminName, "Events", excelLabel.Variable_Name, "M8Event"+(k), excelLabel.Assigned_To);

									} else {
										sa.assertTrue(false,"could not select admin name in "+PageLabel.Assigned_To.toString());
										log(LogStatus.ERROR, "could not select admin name in "+PageLabel.Assigned_To.toString(),YesNo.Yes);

									}
								}else {
									sa.assertTrue(false,"cross button could not be clicked on "+PageLabel.Assigned_To.toString());
									log(LogStatus.ERROR, "cross button could not be clicked on "+PageLabel.Assigned_To.toString(),YesNo.Yes);

								}
							}else {
								ExcelUtils.writeData(phase1DataSheetFilePath,owner, "Events", excelLabel.Variable_Name, "M8Event"+(k), excelLabel.Assigned_To);
							}
							
							click(driver, home.getAllDayEventCheckBox(10), "all day event check box", action.SCROLLANDBOOLEAN);
							
							ele=gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Event, PageLabel.Location.toString(),10);
							if (sendKeys(driver, ele, location, "Subject", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "Enter location : "+location, YesNo.No);
							}else {
								log(LogStatus.PASS, "Not able to Enter location : "+location, YesNo.Yes);
								sa.assertTrue(false, "Not able to Enter location : "+location);
							}
							if (click(driver, gp.getSaveButtonForEvent(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"Click on Save Button For Event : "+meetingEventSubject,YesNo.No);		
								ThreadSleep(500);
							}
							else {
								sa.assertTrue(false,"Not Able to Click on Save Button For Event : "+meetingEventSubject);
								log(LogStatus.SKIP,"Not Able to Click on Save Button For Event : "+meetingEventSubject,YesNo.Yes);	
							}

						} else {
							BaseLib.sa.assertTrue(false,"Not Able to Select "+accountName+" For Label "+PageLabel.Related_To);
							log(LogStatus.ERROR,"Not Able to Select "+accountName+" For Label "+PageLabel.Related_To,YesNo.Yes);

						}
					}else {
						BaseLib.sa.assertTrue(false,"Event can not created as Some data is not entered : "+meetingEventSubject);
						log(LogStatus.ERROR,"Event can not created as Some data is not entered : "+meetingEventSubject,YesNo.Yes);
					}


				} else {
					sa.assertTrue(false,"Not Able to Click on New Event Link");
					log(LogStatus.SKIP,"Not Able to Click on New Event Link",YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Global Action Related item");
				log(LogStatus.SKIP,"Not Able to Click on Global Action Related item",YesNo.Yes);
			}
			
		}
		refresh(driver);
		
		if(home.ctreatedTaskListOnHomePage().size()==10) {
			log(LogStatus.PASS, "5 task and 5 events are displaying on home page ", YesNo.No);
		}else {
			log(LogStatus.PASS, "5 task and 5 events are not displaying on home page ", YesNo.Yes);
			sa.assertTrue(false, "5 task and 5 events are not displaying on home page ");
		}
		if(click(driver, home.viewAllAndviewClendarLink(Task.TodayTasks, ViewAllAndViewCalendarLink.viewALl, 10), "view all", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "view all link ",YesNo.No);
			ThreadSleep(1000);
			if(home.getTaskPageRecentView(10)!=null) {
				log(LogStatus.PASS, "all task is displaying",YesNo.No);
			}else {
				log(LogStatus.PASS, "all task is not displaying",YesNo.Yes);
				sa.assertTrue(false, "all task is not displaying");
			}
			home.clickOnTab(projectName, TabName.HomeTab);
			
		}else {
			log(LogStatus.PASS, "view all link is not displaying ",YesNo.Yes);
			sa.assertTrue(false,  "view all link is not displaying ");
			home.clickOnTab(projectName, TabName.HomeTab);
		}
		
		if(click(driver, home.viewAllAndviewClendarLink(Task.TodayEvents, ViewAllAndViewCalendarLink.ViewCalendar, 10), "view calendar", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "view calendar link ",YesNo.No);
			ThreadSleep(1000);
			if(home.CreateEventsListOnViewClendarPage().size()==5) {
				log(LogStatus.PASS, "all 5 event is displaying",YesNo.No);
			}else {
				log(LogStatus.PASS, "all 5 event is not displaying",YesNo.Yes);
				sa.assertTrue(false, "all  5 event is not displaying");
			}
			home.clickOnTab(projectName, TabName.HomeTab);
			
		}else {
			log(LogStatus.PASS, "view calendar link is not displaying ",YesNo.Yes);
			sa.assertTrue(false,  "view calendar link is not displaying ");
			home.clickOnTab(projectName, TabName.HomeTab);
		}
		ThreadSleep(5000);
		refresh(driver);
		ThreadSleep(5000);
		String name = home.ctreatedTaskAndEventListOnHomePage(Task.TodayTasks).get(0).getText();
		if(clickUsingJavaScript(driver, home.ctreatedTaskAndEventListOnHomePage(Task.TodayTasks).get(0),"", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "clicked on created task name "+name, YesNo.No);
			ThreadSleep(10000);
			if(home.getHeaderNameText(10)!=null) {
				log(LogStatus.PASS, "task is displaying", YesNo.No);
			}else {
				log(LogStatus.PASS, "task is not displaying", YesNo.No);
				sa.assertTrue(false, "task is not displaying");
			}
			home.clickOnTab(projectName, TabName.HomeTab);
		}else {
			log(LogStatus.FAIL, "Not able to click on created task name "+name, YesNo.No);
			sa.assertTrue(false, "Not able to click on created task name "+name);
		}
		home.clickOnTab(projectName, TabName.HomeTab);
		ThreadSleep(5000);
		refresh(driver);
		ThreadSleep(5000);
		name = home.ctreatedTaskAndEventListOnHomePage(Task.TodayEvents).get(0).getText();
		if(clickUsingJavaScript(driver, home.ctreatedTaskAndEventListOnHomePage(Task.TodayEvents).get(0),"", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "clicked on created event name "+name, YesNo.No);
			ThreadSleep(5000);
			if(home.getHeaderNameText(10)!=null) {
				log(LogStatus.PASS, "event is displaying", YesNo.No);
			}else {
				log(LogStatus.PASS, "event is not displaying", YesNo.No);
				sa.assertTrue(false, "event is not displaying");
			}
			
		}else {
			log(LogStatus.FAIL, "Not able to click on created event name "+name, YesNo.No);
			sa.assertTrue(false, "Not able to click on created event name "+name);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc027_verifyTodaysSectionUponSelectingDifferentValuesFromDropDown(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		ThreadSleep(5000);
		List<WebElement> eleList=null;
		List<String> expectedList = new ArrayList<String>();
		List<String> actualList = new ArrayList<String>();
		TodayTaskDownArrowValues defaultValue;
		WebElement ele;
		String expectedValue = "selected";
		String[] headers = {"","My Tasks","All Overdue Tasks","Completed Tasks","Delegated Tasks"};
		TodayTaskDownArrowValues[] value = {TodayTaskDownArrowValues.Today,TodayTaskDownArrowValues.MyTasks,TodayTaskDownArrowValues.AllOverdue,TodayTaskDownArrowValues.CompletedWithinLast7Days,TodayTaskDownArrowValues.DelegatedTasks};

		for (int i = 0; i < value.length; i++) {
			defaultValue = value[i];
			ele=home.getArrowIconView(30);
			if (click(driver, ele, "Select a view of your tasks Arrow Icon for "+defaultValue, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on Select a view of your tasks Arrow Icon for "+defaultValue, YesNo.Yes);
			}else{
				log(LogStatus.SKIP, "Not able to Click on Select a view of your tasks Arrow Icon for "+defaultValue, YesNo.Yes);
				sa.assertTrue(false,  "Not able to Click on Select a view of your tasks Arrow Icon for "+defaultValue);	
			}
			if (i==0) {
				//TodayTaskDownArrowValues.Today
				ele = home.todayTasksDownArrowListValues(defaultValue, 10);
				if(ele!=null) {
					log(LogStatus.PASS, defaultValue+" is displaying in today tasks list", YesNo.No);
					String actualValue = ele.getAttribute("class");
					if (expectedValue.equals(actualValue)) {
						log(LogStatus.INFO,defaultValue+" is selected by default", YesNo.No);
					}
					else {
						log(LogStatus.ERROR, defaultValue+" should be selected by default", YesNo.Yes);
						sa.assertTrue(false,defaultValue+" should be selected by default");
					}
					expectedList.add(M8CallSubject);
					expectedList.add(M8SendLetterSubject);
					expectedList.add(M8SendQuoteSubject);
					expectedList.add(M8OtherSubject);
					expectedList.add(M8Einstein5Subject);
					expectedList.add(M8Einstein6Subject);
					expectedList.add(M8Einstein7Subject);
					expectedList.add(M8Einstein8Subject);
					expectedList.add(M8Einstein9Subject);
					expectedList.add(M8Einstein10Subject);
				}else {
					log(LogStatus.FAIL, defaultValue+" is not displaying in today tasks list", YesNo.Yes);
					sa.assertTrue(false, defaultValue+" is not displaying in today tasks list");
				}

			}else if(i==1){
				expectedList.add(M8CallSubject);
				expectedList.add(M8SendLetterSubject);
				expectedList.add(M8SendQuoteSubject);
				expectedList.add(M8OtherSubject);
				expectedList.add(M8Einstein5Subject);
				expectedList.add(M8Einstein11Subject);
				expectedList.add(M8Einstein12Subject);
				expectedList.add(M8Einstein13Subject);
				expectedList.add(M8Einstein14Subject);
			}else if(i==2){
				expectedList.add(M8Einstein11Subject);
				expectedList.add(M8Einstein12Subject);
			}else if(i==3){
				expectedList.add(M8Einstein13Subject);
				expectedList.add(M8Einstein14Subject);
			}else{
				expectedList.add(M8Einstein6Subject);
				expectedList.add(M8Einstein7Subject);
				expectedList.add(M8Einstein8Subject);
				expectedList.add(M8Einstein9Subject);
				expectedList.add(M8Einstein10Subject);
			}

			ele = home.todayTasksDownArrowListValues(defaultValue, 30);
			if (click(driver, ele, defaultValue.toString(), action.BOOLEAN)) {
				log(LogStatus.INFO, "click on "+defaultValue, YesNo.Yes);
				ele=home.taskHeaderDownArrow(headers[i], 30);
				if(ele!=null) {
					log(LogStatus.PASS, headers[i]+" is displaying in section Header", YesNo.No);
				}else {
					log(LogStatus.FAIL, headers[i]+" is not displaying in section Header", YesNo.Yes);
					sa.assertTrue(false, headers[i]+" is not displaying in section Header");
				}
			} else {
				log(LogStatus.ERROR, "Not Able to click on "+defaultValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to click on "+defaultValue);
			}

			eleList = home.ctreatedTaskAndEventListOnHomePage(Task.TodayTasks);
			for (WebElement webElement : eleList) {
				actualList.add(webElement.getText().trim());
			}
			if (expectedList.containsAll(actualList)) {
				log(LogStatus.INFO, defaultValue+" data verified Actual : "+actualList, YesNo.Yes);
			} else {
				log(LogStatus.FAIL, defaultValue+" data not verified Actual : "+actualList+" Expected : "+expectedList, YesNo.Yes);
				sa.assertTrue(false, defaultValue+" data not verified Actual : "+actualList+" Expected : "+expectedList);
			}
			refresh(driver);
			ThreadSleep(5000);
			expectedList.clear();
			actualList.clear();
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc028_updateAndDeleteTaskAndEvents(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		List<WebElement> lst = new ArrayList<WebElement>();
		ThreadSleep(5000);
		String[] taskAndEvent={M8Einstein11Subject,M8EinsteinEvent10Subject};
		String t="";
		String update=" update";
		String upadtedTask="";
		for (int i = 0; i < taskAndEvent.length; i++) {
			t=taskAndEvent[i];
			upadtedTask=t+update;
			if (click(driver, home.createdtaskOnHomePage(t), t, action.BOOLEAN)) {
				log(LogStatus.PASS, "able to Clicked on "+t+" so going to update it to "+update, YesNo.No);
				if (click(driver, tp.getEditButton(projectName, 30), t, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Edit Button For : "+t, YesNo.No);	
					try {
						gp.getLabelTextBoxForTask(projectName, PageName.TaskPage, PageLabel.Subject.toString(),20).clear();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ThreadSleep(2000);
					if (sendKeys(driver, gp.getLabelTextBoxForTask(projectName, PageName.TaskPage, PageLabel.Subject.toString(),20), update, "Subject", action.BOOLEAN)) {
						log(LogStatus.INFO, "Value Entered to Due Date "+upadtedTask, YesNo.No);	
						ThreadSleep(10000);
						if (clickUsingJavaScript(driver, lp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"successfully Updated task : "+t,  YesNo.No);
							ThreadSleep(5000);
							String[][] fieldsWithValues= {{PageLabel.Subject.toString(),upadtedTask}};
							tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 30);
							lp.clickOnTab(projectName, TabName.HomeTab);
							if (click(driver, home.createdtaskOnHomePage(upadtedTask), upadtedTask, action.BOOLEAN)) {
								log(LogStatus.PASS, "Task/Event Updated "+upadtedTask+" so going to delete it ", YesNo.No);
								if (click(driver, cp.getDeleteButton(projectName, 20), "Delete Button", action.BOOLEAN)) {
									log(LogStatus.INFO,"Able to Click on Delete button : "+upadtedTask,YesNo.No);
									ThreadSleep(2000);
									if (click(driver, cp.getDeleteButtonPopUp(projectName, 20), "Delete Button", action.BOOLEAN)) {
										log(LogStatus.INFO,"Able to Click on Delete button on PopUp : "+upadtedTask,YesNo.No);
										ThreadSleep(2000);
										lp.clickOnTab(projectName, TabName.HomeTab);
										if (home.createdtaskOnHomePage(upadtedTask)==null) {
											log(LogStatus.PASS,"Task/Event deleted "+upadtedTask,YesNo.Yes);
										} else {
											sa.assertTrue(false,"Task/Event not deleted "+upadtedTask);
											log(LogStatus.SKIP,"Task/Event not deleted "+upadtedTask,YesNo.Yes);
										}
									} else {
										sa.assertTrue(false,"Not Able to Click on Delete button on PopUp : "+upadtedTask);
										log(LogStatus.SKIP,"Not Able to Click on Delete button on PopUp : "+upadtedTask,YesNo.Yes);
									}
								} else {
									sa.assertTrue(false,"Not Able to Click on Delete button : "+upadtedTask);
									log(LogStatus.SKIP,"Not Able to Click on Delete button : "+upadtedTask,YesNo.Yes);
								}
								
							}else {
								log(LogStatus.FAIL, "Task/Event has not been Updated "+upadtedTask+" so cannot delete it ", YesNo.Yes);
								sa.assertTrue(false, "Task/Event has not been Updated "+upadtedTask+" so cannot delete it ");
							}
						}else {
							log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+t, YesNo.Yes);
							sa.assertTrue(false,"save button is not clickable so task not Updated : "+t );
						}
					}else {
						log(LogStatus.ERROR, "Not Able to Entered Value to Due Date "+M7Task8dueDate, YesNo.Yes);	
						BaseLib.sa.assertTrue(false, "Not Able to Entered Value to Due Date "+M7Task8dueDate);	
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Edit Button For : "+t, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on Edit Button For : "+t);
				}
			}else {
				log(LogStatus.FAIL, "Not able to Clicked on "+t+" so cannot update it to "+upadtedTask, YesNo.Yes);
				sa.assertTrue(false, "Not able to Clicked on "+t+" so cannot update it to "+upadtedTask);
			}
		}
		
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc029_verifyCreateButtonInStandardTheme(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		List<WebElement> lst = new ArrayList<WebElement>();
		ThreadSleep(5000);
		SDGGridName [] sdgName = {SDGGridName.Deals,SDGGridName.Fundraising,SDGGridName.My_Call_List};
		String[] buttonName= {M8CreateDealButtonName,M8CreateFundRaisingButtonName,M8CreateContactButtonName};
		String[] event= {M8DealEvent,M8FundRaisingEvent,M8ContactEvent};
		String[] ActionOrder= {M8DealActionOrder,M8FundRaisingActionOrder,M8ContactActionOrder};
		String[] ActionType= {M8DealActionType,M8FundRaisingActionType,M8ContactActionType};
		String[] payLoad= {M8DealEventPayLoad,M8FundRaisingEventPayLoad,M8ContactEventPayLoad};
		for(int i=0; i<sdgName.length; i++) {
			
			if(click(driver, home.sdgGridSideIcons(sdgName[i], SDGGridSideIcons.Open_SDG_Record, 10), "Open_SDG_Record", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.PASS, "click on Open_SDG_Record icon so cannot search My Call List name : "+M8CON1FName+" "+M8CON1LName, YesNo.Yes);
				ThreadSleep(5000);
				String parentid=switchOnWindow(driver);
				if(parentid!=null) {
					ThreadSleep(5000);
					if(click(driver, lp.getRelatedTab(projectName, RelatedTab.Related.toString(), 30), "Related", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "clicked on related tab", YesNo.No);
						ThreadSleep(1000);
						if(click(driver, home.openSDGRecordOrSettingsPageNewBtnInActions("Actions", "New", 10), "new button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "clicked on new button", YesNo.No);
							ThreadSleep(1000);
							
							
							if(sendKeys(driver, home.openSDGRecordOrSettingsPageActionPopUptextBoxXpath(excelLabel.Name.toString(),10), buttonName[i],"name", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "Enter button name "+buttonName[i], YesNo.No);
							}else {
								log(LogStatus.PASS, "Not able to Enter button name "+buttonName[i], YesNo.No);
								sa.assertTrue(false, "Not able to Enter button name "+buttonName[i]);
							}
							
							if(sendKeys(driver, home.openSDGRecordOrSettingsPageActionPopUptextBoxXpath(excelLabel.Event.toString(),10), event[i],"event", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "Enter event name "+event[i], YesNo.No);
							}else {
								log(LogStatus.PASS, "Not able to Enter event name "+event[i], YesNo.No);
								sa.assertTrue(false, "Not able to Enter event name "+event[i]);
							}
							if(sendKeys(driver, home.openSDGRecordOrSettingsPageActionPopUptextBoxXpath(excelLabel.Action_Order.toString(),10), ActionOrder[i],"ActionOrder", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "Enter ActionOrder name "+ActionOrder[i], YesNo.No);
							}else {
								log(LogStatus.PASS, "Not able to Enter ActionOrder name "+ActionOrder[i], YesNo.No);
								sa.assertTrue(false, "Not able to Enter ActionOrder name "+ActionOrder[i]);
							}
							
							if(click(driver, home.openSDGRecordOrSettingsPageActionPopUptextBoxXpath(excelLabel.Action_Type.toString(),10), "action type", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "clicked on action type drop down", YesNo.No);
								if(click(driver, home.ActionTypeDropDownList(ActionType[i], 10), "", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.PASS, "clicked ActionType name "+ActionType[i], YesNo.No);
								}else {
									log(LogStatus.PASS, "Not able to clicked on  ActionType name "+ActionType[i], YesNo.No);
									sa.assertTrue(false, "Not able to clicked on ActionType name "+ActionType[i]);
								}
							}else {
								log(LogStatus.PASS, "Not able to clicked on action type drop down so cannot select"+ActionType[i], YesNo.No);
								sa.assertTrue(false, "Not able to clicked on action type drop down so cannot select"+ActionType[i]);
							}
							if(sendKeys(driver, home.openSDGRecordOrSettingsPageActionPopUptextBoxXpath(excelLabel.Event_Payload.toString(),10), payLoad[i],"Event_Payload", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "Enter payLoad name "+payLoad[i], YesNo.No);
							}else {
								log(LogStatus.PASS, "Not able to Enter payLoad name "+payLoad[i], YesNo.No);
								sa.assertTrue(false, "Not able to Enter payLoad name "+payLoad[i]);
							}
							if (click(driver, home.getNavigationTabSaveBtn(projectName, 60), "Save Button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "Clicked on save button", YesNo.No);
							}else {
								log(LogStatus.FAIL, "Not able to Clicked on save button", YesNo.Yes);
								sa.assertTrue(false, "Not able to Clicked on save button");
							}
							
						}else {
							log(LogStatus.PASS, "not able to clicked on new button so cannot create ", YesNo.Yes);
							sa.assertTrue(false, "not able to clicked on new button so cannot create ");
						}
					}else {
						log(LogStatus.FAIL, "Not able to click on related tab", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on related tab");
					}
					driver.close();
					driver.switchTo().window(parentid);
				}else {
					log(LogStatus.FAIL,"Not able to switch on open sdg record window", YesNo.Yes);
					sa.assertTrue(false, "Not able to switch on open sdg record window");
				}
				refresh(driver);
				ThreadSleep(5000);
				if(home.openSDGRecordOrSettingsPageNewBtnInActions(sdgName[i].toString(),buttonName[i], 30)!=null) {
					log(LogStatus.PASS, buttonName[i]+" button is displaying on "+sdgName[i], YesNo.No);
				
				}else {
					log(LogStatus.PASS, buttonName[i]+" button is not displaying on "+sdgName[i], YesNo.Yes);
					sa.assertTrue(false, buttonName[i]+" button is not displaying on "+sdgName[i]);
				}
			}else {
				log(LogStatus.FAIL, "Not able to click on Open_SDG_Record icon so cannot check create button ", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on Open_SDG_Record icon so cannot search check create button");
			}
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc030_verifychangeActionTypeInStandardTheme(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		List<WebElement> lst = new ArrayList<WebElement>();
		ThreadSleep(5000);
		SDGGridName [] sdgName = {SDGGridName.Deals,SDGGridName.Fundraising,SDGGridName.My_Call_List};
		String[] buttonName= {M8CreateDealButtonName,M8CreateFundRaisingButtonName,M8CreateContactButtonName};
		for(int i=0; i<sdgName.length; i++) {
			
			if(click(driver, home.sdgGridSideIcons(sdgName[i], SDGGridSideIcons.Open_SDG_Record, 10), "Open_SDG_Record", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.PASS, "click on Open_SDG_Record icon so cannot search My Call List name : "+M8CON1FName+" "+M8CON1LName, YesNo.Yes);
				ThreadSleep(5000);
				String parentid=switchOnWindow(driver);
				if(parentid!=null) {
					ThreadSleep(5000);
					if(click(driver, lp.getRelatedTab(projectName, RelatedTab.Related.toString(), 30), "Related", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "clicked on related tab", YesNo.No);
						ThreadSleep(1000);
						if(click(driver, home.createdButtonEditAndDeleteBtn(buttonName[i], "Edit",30), "edit button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "clicked on new button", YesNo.No);
							ThreadSleep(1000);
							
							if(click(driver, home.openSDGRecordOrSettingsPageActionPopUptextBoxXpath(excelLabel.Action_Type.toString(),10), "action type", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "clicked on action type drop down", YesNo.No);
								if(click(driver, home.ActionTypeDropDownList("List", 10), "", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.PASS, "clicked ActionType name List", YesNo.No);
								}else {
									log(LogStatus.PASS, "Not able to clicked on  ActionType name List", YesNo.Yes);
									sa.assertTrue(false, "Not able to clicked on ActionType name List");
								}
							}else {
								log(LogStatus.PASS, "Not able to clicked on action type drop down so cannot select List", YesNo.Yes);
								sa.assertTrue(false, "Not able to clicked on action type drop down so cannot select List");
							}
							
							if (click(driver, home.getNavigationTabSaveBtn(projectName, 60), "Save Button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "Clicked on save button", YesNo.No);
							}else {
								log(LogStatus.FAIL, "Not able to Clicked on save button", YesNo.Yes);
								sa.assertTrue(false, "Not able to Clicked on save button");
							}
							
						}else {
							log(LogStatus.PASS, "not able to clicked on edit button so cannot change action type ", YesNo.Yes);
							sa.assertTrue(false, "not able to clicked on edit button so cannot change action type");
						}
					}else {
						log(LogStatus.FAIL, "Not able to click on related tab", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on related tab");
					}
					driver.close();
					driver.switchTo().window(parentid);
				}else {
					log(LogStatus.FAIL,"Not able to switch on open sdg record window", YesNo.Yes);
					sa.assertTrue(false, "Not able to switch on open sdg record window");
				}
				refresh(driver);
				ThreadSleep(5000);
				if(click(driver, home.sdgGridSideIconsForLightTheme(sdgName[i], SDGGridSideIcons.Side_DropDOwnButtonforStandardTheme, 10), "drop down  button sdg grid", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.PASS, "Clicked on Drop Down button", YesNo.No);
					ThreadSleep(500);
					if(home.sdgGridSideIconsForLightTheme(sdgName[i], buttonName[i], 10)!=null) {
						log(LogStatus.PASS, "Clicked on Drop Down button", YesNo.No);
						
					}else {
						log(LogStatus.FAIL, "Not able to Click on Drop Down button", YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on Drop Down button");
					}
				}else {
					log(LogStatus.FAIL, "Not able to Click on Drop Down button", YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on Drop Down button");
				}
			}else {
				log(LogStatus.FAIL, "Not able to click on Open_SDG_Record icon so cannot change action type ", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on Open_SDG_Record icon so cannot change action type");
			}
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc031_verifychangeActionTypeRowButtonInStandardTheme(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		List<WebElement> lst = new ArrayList<WebElement>();
		ThreadSleep(5000);
		SDGGridName [] sdgName = {SDGGridName.Deals,SDGGridName.Fundraising,SDGGridName.My_Call_List};
		String[] buttonName= {M8CreateDealButtonName,M8CreateFundRaisingButtonName,M8CreateContactButtonName};
		for(int i=0; i<sdgName.length; i++) {
			
			if(click(driver, home.sdgGridSideIcons(sdgName[i], SDGGridSideIcons.Open_SDG_Record, 10), "Open_SDG_Record", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.PASS, "click on Open_SDG_Record icon so cannot search My Call List name : "+M8CON1FName+" "+M8CON1LName, YesNo.Yes);
				ThreadSleep(5000);
				String parentid=switchOnWindow(driver);
				if(parentid!=null) {
					ThreadSleep(5000);
					if(click(driver, lp.getRelatedTab(projectName, RelatedTab.Related.toString(), 30), "Related", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "clicked on related tab", YesNo.No);
						ThreadSleep(1000);
						if(click(driver, home.createdButtonEditAndDeleteBtn(buttonName[i], "Edit",30), "edit button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "clicked on new button", YesNo.No);
							ThreadSleep(1000);
							
							if(click(driver, home.openSDGRecordOrSettingsPageActionPopUptextBoxXpath(excelLabel.Action_Type.toString(),10), "action type", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "clicked on action type drop down", YesNo.No);
								if(click(driver, home.ActionTypeDropDownList("Row Button", 10), "", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.PASS, "clicked ActionType name Row Button", YesNo.No);
								}else {
									log(LogStatus.PASS, "Not able to clicked on  ActionType name Row Button", YesNo.Yes);
									sa.assertTrue(false, "Not able to clicked on ActionType name Row Button");
								}
							}else {
								log(LogStatus.PASS, "Not able to clicked on action type drop down so cannot select List", YesNo.Yes);
								sa.assertTrue(false, "Not able to clicked on action type drop down so cannot select List");
							}
							
							if (click(driver, home.getNavigationTabSaveBtn(projectName, 60), "Save Button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "Clicked on save button", YesNo.No);
							}else {
								log(LogStatus.FAIL, "Not able to Clicked on save button", YesNo.Yes);
								sa.assertTrue(false, "Not able to Clicked on save button");
							}
							
						}else {
							log(LogStatus.PASS, "not able to clicked on edit button so cannot change action type ", YesNo.Yes);
							sa.assertTrue(false, "not able to clicked on edit button so cannot change action type");
						}
					}else {
						log(LogStatus.FAIL, "Not able to click on related tab", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on related tab");
					}
					driver.close();
					driver.switchTo().window(parentid);
				}else {
					log(LogStatus.FAIL,"Not able to switch on open sdg record window", YesNo.Yes);
					sa.assertTrue(false, "Not able to switch on open sdg record window");
				}
				refresh(driver);
				ThreadSleep(5000);
				if(home.sdgGridHeadersCreateButtonList(sdgName[i], buttonName[i]).size()==10) {
					log(LogStatus.PASS, buttonName[i]+" is displaying on "+sdgName[i], YesNo.No);

				}else {
					log(LogStatus.FAIL, buttonName[i]+" is not displaying on "+sdgName[i], YesNo.Yes);
					sa.assertTrue(false, buttonName[i]+" is not displaying on "+sdgName[i]);
				}
			}else {
				log(LogStatus.FAIL, "Not able to click on Open_SDG_Record icon so cannot change action type ", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on Open_SDG_Record icon so cannot change action type");
			}
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M8Tc032_verifyImapctOnButtonInSDGInLightTheme(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		ThreadSleep(5000);
		if(edit.clickOnEditPageLink()) {
			log(LogStatus.PASS, "clicked on edit page on home page", YesNo.No);
			ThreadSleep(10000);
			String[] sdgGrid = {"Deal","FundRaising","My Call List"};
			SDGGridName[] sdgNames = {SDGGridName.Deals,SDGGridName.Fundraising,SDGGridName.My_Call_List};
			switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
			for(int i=0; i<sdgNames.length; i++) {
				WebElement ele= home.sdgGridListInEditMode(sdgNames[i],20);
				scrollDownThroughWebelement(driver, ele, "");
				if(click(driver, ele, "sdg grid "+sdgNames[i], action.BOOLEAN)) {
					log(LogStatus.PASS, "clicked on SDG Grid "+(i+1), YesNo.No);
					ThreadSleep(5000);
					switchToDefaultContent(driver);
					click(driver, home.getSelectThemeinputBoxClearButton(10), "clear button", action.SCROLLANDBOOLEAN);
					ThreadSleep(1000);
					List<WebElement> themelistwebelement=home.sdgGridSelectThemeList();
					for(int i1=0; i1<themelistwebelement.size(); i1++) {
						if(themelistwebelement.get(i1).getText().equalsIgnoreCase("Light")) {
							if(click(driver, themelistwebelement.get(i1), "Light theme xpath", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "Select Ligth Theme for "+sdgGrid[i], YesNo.No);
								break;
							}
						}else {
								log(LogStatus.PASS, "Not able to select Ligth Theme for "+sdgGrid[i], YesNo.Yes);
								sa.assertTrue(false, "Not able to select Ligth Theme for "+sdgGrid[i]);
							
						}
					}
				}else {
					log(LogStatus.PASS, "Not able to click on SDG Grid "+sdgNames[i], YesNo.Yes);
					sa.assertTrue(false, "Not able to click on SDG Grid "+sdgNames[i]);
				}
				if(i!=sdgNames.length-1) {
					switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
				}
			}
			
			ThreadSleep(2000);
			if(click(driver, home.getCustomTabSaveBtn(projectName, 10), "save button", action.BOOLEAN)) {
        		log(LogStatus.INFO, "clicked on save button", YesNo.No);
        		ThreadSleep(7000);
        		if(clickUsingJavaScript(driver, edit.getBackButton(10), "back button", action.BOOLEAN)) {
        			log(LogStatus.PASS, "clicked on back button", YesNo.No);
        			ThreadSleep(5000);
        			WebElement[] ele2 = {home.sdgGridSideIconsForLightTheme(SDGGridName.Deals, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10),
        					home.sdgGridSideIconsForLightTheme(SDGGridName.Fundraising, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10),
        					home.sdgGridSideIconsForLightTheme(SDGGridName.My_Call_List, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10)};
        			String[] labelName = {"Deal","FundRaising","My Call List"};
        			String[] filterIconName= {"Manage_fields","Setup","Reload"};
        			SDGGridName[] sdgGridNames = {SDGGridName.Deals,SDGGridName.Fundraising,SDGGridName.My_Call_List};
        			int j=0;
        			for (WebElement webElements : ele2) {
        				if(webElements!=null) {
        					log(LogStatus.PASS, "Drop Down button is displaying for "+labelName[j], YesNo.No);
        					if(click(driver, webElements, "drop down  button of "+labelName[j], action.SCROLLANDBOOLEAN)) {
        						log(LogStatus.PASS, "Clicked on Drop Down button for "+labelName[j], YesNo.No);
        						ThreadSleep(2000);
        						int i=0;
        						WebElement[] ele3 = {home.sdgGridSideIconsForLightTheme(sdgGridNames[j], SDGGridSideIcons.Manage_fields, 10),
        								home.sdgGridSideIconsForLightTheme(sdgGridNames[j], SDGGridSideIcons.Setup, 10),
        								home.sdgGridSideIconsForLightTheme(sdgGridNames[j], SDGGridSideIcons.Reload, 10)};
        						for (WebElement webElements1 : ele3) {
        							if(webElements1!=null) {
        								log(LogStatus.PASS,filterIconName[i]+"Drop Down menu is displaying for "+labelName[j], YesNo.No);

        							}else {
        								log(LogStatus.PASS, filterIconName[i]+" Drop Down menu is not displaying for "+labelName[j], YesNo.Yes);
        								sa.assertTrue(false,filterIconName[i]+ "Drop Down menu is not displaying for "+labelName[j]);
        							}
        							i++;
        						}
        					}else {
        						log(LogStatus.FAIL, "Not able to Click on Drop Down button for "+labelName[j], YesNo.Yes);
        						sa.assertTrue(false, "Not able to Click on Drop Down button for "+labelName[j]);
        					}

        				}else {
        					log(LogStatus.PASS, "Drop Down button is not displaying for "+labelName[j], YesNo.Yes);
        					sa.assertTrue(false,"Drop Down button is not displaying for "+labelName[j]);
        				}
        				j++;
        			}
        			int j1=0;
        			WebElement[] ele4 = {home.sdgGridSideIconsForLightTheme(SDGGridName.Deals, SDGGridSideIcons.Toggle_Filters, 10),
        					home.sdgGridSideIconsForLightTheme(SDGGridName.Fundraising, SDGGridSideIcons.Toggle_Filters, 10),
        					home.sdgGridSideIconsForLightTheme(SDGGridName.My_Call_List, SDGGridSideIcons.Toggle_Filters, 10)};
        			for (WebElement webElements : ele4) {
        				if(webElements!=null) {
        					log(LogStatus.PASS, "toggle filter button is displaying for "+labelName[j1], YesNo.No);
        				}else {
        					log(LogStatus.FAIL, "toggle filter button is not displaying for "+labelName[j1], YesNo.Yes);
        					sa.assertTrue(false,"toggle filter is not displaying for "+labelName[j1]);
        				}
        				j1++;
        			}
        			refresh(driver);
    				ThreadSleep(5000);
    				SDGGridName [] sdgName = {SDGGridName.Deals,SDGGridName.Fundraising,SDGGridName.My_Call_List};
    				String[] buttonName= {M8CreateDealButtonName,M8CreateFundRaisingButtonName,M8CreateContactButtonName};
    				for(int i=0; i<sdgName.length; i++) {
    					if(home.sdgGridHeadersCreateButtonList(sdgName[i], buttonName[i]).size()==10) {
        					log(LogStatus.PASS, buttonName[i]+" is displaying on "+sdgName[i], YesNo.No);

        				}else {
        					log(LogStatus.FAIL, buttonName[i]+" is not displaying on "+sdgName[i], YesNo.Yes);
        					sa.assertTrue(false, buttonName[i]+" is not displaying on "+sdgName[i]);
        				}
    				}
        		}else {
					log(LogStatus.ERROR, "Not able to click on back button so cannot back on page ", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on back button so cannot back on page ");
				}
        	}else {
				log(LogStatus.ERROR, "Not able to click on save button so select light theme", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on save button so select light theme");
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on edit page so cannot select light theme", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on edit page so cannot select light theme");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M8Tc033_verifyImapctOnButtonInSDGInDarkTheme(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		ThreadSleep(5000);
		if(edit.clickOnEditPageLink()) {
			log(LogStatus.PASS, "clicked on edit page on home page", YesNo.No);
			ThreadSleep(10000);
			String[] sdgGrid = {"Deal","FundRaising","My Call List"};
			SDGGridName[] sdgNames = {SDGGridName.Deals,SDGGridName.Fundraising,SDGGridName.My_Call_List};
			switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
			for(int i=0; i<sdgNames.length; i++) {
				WebElement ele= home.sdgGridListInEditMode(sdgNames[i],20);
				scrollDownThroughWebelement(driver, ele, "");
				if(click(driver, ele, "sdg grid "+sdgNames[i], action.BOOLEAN)) {
					log(LogStatus.PASS, "clicked on SDG Grid "+(i+1), YesNo.No);
					ThreadSleep(5000);
					switchToDefaultContent(driver);
					click(driver, home.getSelectThemeinputBoxClearButton(10), "clear button", action.SCROLLANDBOOLEAN);
					ThreadSleep(1000);
					List<WebElement> themelistwebelement=home.sdgGridSelectThemeList();
					for(int i1=0; i1<themelistwebelement.size(); i1++) {
						if(themelistwebelement.get(i1).getText().contains("Dark")) {
							if(click(driver, themelistwebelement.get(i1), "Dark theme xpath", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "Select Dark Theme for "+sdgGrid[i], YesNo.No);
								break;
							}
						}else {
							log(LogStatus.PASS, "Not able to select Dark Theme for "+sdgGrid[i], YesNo.Yes);
							sa.assertTrue(false, "Not able to select Dark Theme for "+sdgGrid[i]);
						}
						
					}
				}else {
					log(LogStatus.PASS, "Not able to click on SDG Grid "+sdgNames[i], YesNo.Yes);
					sa.assertTrue(false, "Not able to click on SDG Grid "+sdgNames[i]);
				}
				if(i!=sdgNames.length-1) {
					switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
				}
			}
			
			ThreadSleep(2000);
			if(click(driver, home.getCustomTabSaveBtn(projectName, 10), "save button", action.BOOLEAN)) {
        		log(LogStatus.INFO, "clicked on save button", YesNo.No);
        		ThreadSleep(7000);
        		if(clickUsingJavaScript(driver, edit.getBackButton(10), "back button", action.BOOLEAN)) {
        			log(LogStatus.PASS, "clicked on back button", YesNo.No);
        			
        			ThreadSleep(5000);
        			WebElement[] ele2 = {home.sdgGridSideIconsForLightTheme(SDGGridName.Deals, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10),
        					home.sdgGridSideIconsForLightTheme(SDGGridName.Fundraising, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10),
        					home.sdgGridSideIconsForLightTheme(SDGGridName.My_Call_List, SDGGridSideIcons.Side_DropDOwnButtonforLightTheme, 10)};
        			String[] labelName = {"Deal","FundRaising","My Call List"};
        			String[] filterIconName= {"Manage_fields","Setup","Reload"};
        			SDGGridName[] sdgGridNames = {SDGGridName.Deals,SDGGridName.Fundraising,SDGGridName.My_Call_List};
        			int j=0;
        			for (WebElement webElements : ele2) {
        				if(webElements!=null) {
        					log(LogStatus.PASS, "Drop Down button is displaying for "+labelName[j], YesNo.No);
        					if(click(driver, webElements, "drop down  button of "+labelName[j], action.SCROLLANDBOOLEAN)) {
        						log(LogStatus.PASS, "Clicked on Drop Down button for "+labelName[j], YesNo.No);
        						ThreadSleep(2000);
        						int i=0;
        						WebElement[] ele3 = {home.sdgGridSideIconsForLightTheme(sdgGridNames[j], SDGGridSideIcons.Manage_fields, 10),
        								home.sdgGridSideIconsForLightTheme(sdgGridNames[j], SDGGridSideIcons.Setup, 10),
        								home.sdgGridSideIconsForLightTheme(sdgGridNames[j], SDGGridSideIcons.Reload, 10)};
        						for (WebElement webElements1 : ele3) {
        							if(webElements1!=null) {
        								log(LogStatus.PASS,filterIconName[i]+"Drop Down menu is displaying for "+labelName[j], YesNo.No);

        							}else {
        								log(LogStatus.PASS, filterIconName[i]+" Drop Down menu is not displaying for "+labelName[j], YesNo.Yes);
        								sa.assertTrue(false,filterIconName[i]+ "Drop Down menu is not displaying for "+labelName[j]);
        							}
        							i++;
        						}
        					}else {
        						log(LogStatus.FAIL, "Not able to Click on Drop Down button for "+labelName[j], YesNo.Yes);
        						sa.assertTrue(false, "Not able to Click on Drop Down button for "+labelName[j]);
        					}

        				}else {
        					log(LogStatus.PASS, "Drop Down button is not displaying for "+labelName[j], YesNo.Yes);
        					sa.assertTrue(false,"Drop Down button is not displaying for "+labelName[j]);
        				}
        				j++;
        			}
        			int j1=0;
        			WebElement[] ele4 = {home.sdgGridSideIconsForLightTheme(SDGGridName.Deals, SDGGridSideIcons.Toggle_Filters, 10),
        					home.sdgGridSideIconsForLightTheme(SDGGridName.Fundraising, SDGGridSideIcons.Toggle_Filters, 10),
        					home.sdgGridSideIconsForLightTheme(SDGGridName.My_Call_List, SDGGridSideIcons.Toggle_Filters, 10)};
        			for (WebElement webElements : ele4) {
        				if(webElements!=null) {
        					log(LogStatus.PASS, "toggle filter button is displaying for "+labelName[j1], YesNo.No);
        				}else {
        					log(LogStatus.FAIL, "toggle filter button is not displaying for "+labelName[j1], YesNo.Yes);
        					sa.assertTrue(false,"toggle filter is not displaying for "+labelName[j1]);
        				}
        				j1++;
        			}
        			
        			refresh(driver);
    				ThreadSleep(5000);
    				SDGGridName [] sdgName = {SDGGridName.Deals,SDGGridName.Fundraising,SDGGridName.My_Call_List};
    				String[] buttonName= {M8CreateDealButtonName,M8CreateFundRaisingButtonName,M8CreateContactButtonName};
    				for(int i=0; i<sdgName.length; i++) {
    					if(home.sdgGridHeadersCreateButtonList(sdgName[i], buttonName[i]).size()==10) {
        					log(LogStatus.PASS, buttonName[i]+" is displaying on "+sdgName[i], YesNo.No);

        				}else {
        					log(LogStatus.FAIL, buttonName[i]+" is not displaying on "+sdgName[i], YesNo.Yes);
        					sa.assertTrue(false, buttonName[i]+" is not displaying on "+sdgName[i]);
        				}
    				}
        			
        			
        		}else {
					log(LogStatus.ERROR, "Not able to click on back button so cannot back on page ", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on back button so cannot back on page ");
				}
        	}else {
				log(LogStatus.ERROR, "Not able to click on save button so select light theme", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on save button so select light theme");
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on edit page so cannot select light theme", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on edit page so cannot select light theme");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc034_1_selectStandardThemeForSDG(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		ThreadSleep(5000);
		if(edit.clickOnEditPageLink()) {
			log(LogStatus.PASS, "clicked on edit page on home page", YesNo.No);
			ThreadSleep(10000);
			String[] sdgGrid = {"Deal","FundRaising","My Call List"};
			SDGGridName[] sdgNames = {SDGGridName.Deals,SDGGridName.Fundraising,SDGGridName.My_Call_List};
			switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
			for(int i=0; i<sdgNames.length; i++) {
				WebElement ele= home.sdgGridListInEditMode(sdgNames[i],20);
				scrollDownThroughWebelement(driver, ele, "");
				if(click(driver, ele, "sdg grid "+sdgNames[i], action.BOOLEAN)) {
					log(LogStatus.PASS, "clicked on SDG Grid "+(i+1), YesNo.No);
					ThreadSleep(5000);
					switchToDefaultContent(driver);
					click(driver, home.getSelectThemeinputBoxClearButton(10), "clear button", action.SCROLLANDBOOLEAN);
					ThreadSleep(1000);
					List<WebElement> themelistwebelement=home.sdgGridSelectThemeList();
					for(int i1=0; i1<themelistwebelement.size(); i1++) {
						if(themelistwebelement.get(i1).getText().equalsIgnoreCase("Standard")) {
							if(click(driver, themelistwebelement.get(i1), "Light theme xpath", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "Select Ligth Theme for "+sdgGrid[i], YesNo.No);
								break;
							}
						}else {
							log(LogStatus.PASS, "Not able to select Ligth Theme for "+sdgGrid[i], YesNo.Yes);
							sa.assertTrue(false, "Not able to select Ligth Theme for "+sdgGrid[i]);
						}
						
					}
				}else {
					log(LogStatus.PASS, "Not able to click on SDG Grid "+sdgNames[i], YesNo.Yes);
					sa.assertTrue(false, "Not able to click on SDG Grid "+sdgNames[i]);
				}
				if(i!=sdgNames.length-1) {
					switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
				}
			}
			
			ThreadSleep(2000);
			if(click(driver, home.getCustomTabSaveBtn(projectName, 10), "save button", action.BOOLEAN)) {
        		log(LogStatus.INFO, "clicked on save button", YesNo.No);
        		ThreadSleep(7000);
        		if(clickUsingJavaScript(driver, edit.getBackButton(10), "back button", action.BOOLEAN)) {
        			log(LogStatus.PASS, "clicked on back button", YesNo.No);
        		}else {
					log(LogStatus.ERROR, "Not able to click on back button so cannot back on page ", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on back button so cannot back on page ");
				}
        	}else {
				log(LogStatus.ERROR, "Not able to click on save button so select light theme", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on save button so select light theme");
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on edit page so cannot select light theme", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on edit page so cannot select light theme");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc034_2_verifyGridIconButton(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		List<WebElement> lst = new ArrayList<WebElement>();
		String[][] userAndPassword = {{superAdminUserName,adminPassword},{crmUser1EmailID,adminPassword}};
		for (String[] userPass : userAndPassword) {
		lp.CRMLogin(userPass[0], userPass[1]);
			ThreadSleep(5000);
			WebElement[] ele = {home.sdgGridHeaderName(SDGGridName.Deals,5),home.sdgGridHeaderName(SDGGridName.Fundraising,5),home.sdgGridHeaderName(SDGGridName.My_Call_List,5)};
			String[] labelName = {"Deal","FundRaising","My Call List"};
			for (int i=0; i<ele.length; i++) {
				if(ele[i]!=null) {
					log(LogStatus.PASS, "SDG Grid is displaying "+labelName[i], YesNo.No);
				}else {
					log(LogStatus.FAIL,"SDG Grid is not displaying "+labelName[i], YesNo.Yes);
					sa.assertTrue(false, "SDG Grid is not displaying "+labelName[i]);
				}
			}
			WebElement[] ele1 = {home.sdgGridExpandCollpaseIcon(SDGGridName.Deals,CollapseExpandIcon.Expand,5),home.sdgGridExpandCollpaseIcon(SDGGridName.Fundraising,CollapseExpandIcon.Expand,5),home.sdgGridExpandCollpaseIcon(SDGGridName.My_Call_List,CollapseExpandIcon.Expand,5)};
			for (int i=0; i<ele1.length; i++) {
				if(ele1[i]!=null) {
					log(LogStatus.PASS, "SDG Grid is expanded "+labelName[i], YesNo.No);
				}else {
					log(LogStatus.FAIL,"SDG Grid is not expanded "+labelName[i], YesNo.Yes);
					sa.assertTrue(false, "SDG Grid is not expanded "+labelName[i]);
				}
			}
			for (int i=0; i<labelName.length; i++) {
				String headerName="";
				if(i==0) {
					lst = home.sdgGridHeadersLabelNameList(SDGGridName.Deals);
					headerName="Deal,Stage,Source Firm,Notes,Source Firm";
				}else if (i==1) {
					lst = home.sdgGridHeadersLabelNameList(SDGGridName.Fundraising);
					headerName="Fundraising,Stage,Closing,Close Date,Potential Investment(M),Status Notes";
				}else {
					lst = home.sdgGridHeadersLabelNameList(SDGGridName.My_Call_List);
					headerName="Name,Firm,Phone";
				}
				if(!lst.isEmpty()) {
					if(compareMultipleList(driver, headerName, lst).isEmpty()) {
						log(LogStatus.PASS, labelName[i]+" SDG Grid Header Name is verified ", YesNo.No);
					}else {
						log(LogStatus.FAIL,labelName[i]+" SDG Grid Header Name is not verified ", YesNo.Yes);
						sa.assertTrue(false, labelName[i]+" SDG Grid Header Name is not verified ");
					}
				}else {
					log(LogStatus.FAIL,labelName[i]+" SDG Grid Header Name is not visible ", YesNo.Yes);
					sa.assertTrue(false, labelName[i]+" SDG Grid Header Name is not visible ");
				}
			}
			WebElement[][] ele2 = {{home.sdgGridSideIcons(SDGGridName.Deals,SDGGridSideIcons.Manage_fields,5),home.sdgGridSideIcons(SDGGridName.Deals,SDGGridSideIcons.Open_SDG_Record,5),
				home.sdgGridSideIcons(SDGGridName.Deals,SDGGridSideIcons.Toggle_Filters,5),home.sdgGridSideIcons(SDGGridName.Deals,SDGGridSideIcons.Reload,5)}
			,{home.sdgGridSideIcons(SDGGridName.Fundraising,SDGGridSideIcons.Manage_fields,5),home.sdgGridSideIcons(SDGGridName.Fundraising,SDGGridSideIcons.Open_SDG_Record,5),
				home.sdgGridSideIcons(SDGGridName.Fundraising,SDGGridSideIcons.Toggle_Filters,5),home.sdgGridSideIcons(SDGGridName.Fundraising,SDGGridSideIcons.Reload,5)},
			{home.sdgGridSideIcons(SDGGridName.My_Call_List,SDGGridSideIcons.Manage_fields,5),home.sdgGridSideIcons(SDGGridName.My_Call_List,SDGGridSideIcons.Open_SDG_Record,5),
					home.sdgGridSideIcons(SDGGridName.My_Call_List,SDGGridSideIcons.Toggle_Filters,5),home.sdgGridSideIcons(SDGGridName.My_Call_List,SDGGridSideIcons.Reload,5)}};
			System.err.println(ele2.length);
			System.err.println(ele2[0].length);
			String[] filterIconName= {"Manage_fields","Open_SDG_Record","Toggle_Filters","Reload"};
			int j=0;
			for (WebElement[] webElements : ele2) {
				for(int i=0 ;i<webElements.length; i++) {
					if(webElements[i]!=null) {
						log(LogStatus.PASS, filterIconName[i]+" is displaying and tool tip is available for "+labelName[j], YesNo.No);
					}else {
						log(LogStatus.PASS, filterIconName[i]+" is displaying and tool tip is not  available for "+labelName[j], YesNo.Yes);
						sa.assertTrue(false,  filterIconName[i]+" is displaying and tool tip is not available for "+labelName[j]);
					}
				}
				j++;
			}
			if(home.todayTasksAndTodayEventsLabelText(Task.TodayTasks, 10)!=null) {
				log(LogStatus.PASS, "Today Tasks section is displaying on home page", YesNo.No);
			}else {
				log(LogStatus.FAIL, "Today Tasks section is not displaying on home page", YesNo.Yes);
				sa.assertTrue(false, "Today Tasks section is not displaying on home page");
			}
			
			if(home.todayTasksAndTodayEventsLabelText(Task.TodayEvents, 10)!=null) {
				log(LogStatus.PASS, "Today Events section is displaying on home page", YesNo.No);
			}else {
				log(LogStatus.FAIL, "Today Events section is not displaying on home page", YesNo.Yes);
				sa.assertTrue(false, "Today Events section is not displaying on home page");
			}
			
			lp.CRMlogout();
			closeBrowser();
			config(ExcelUtils.readDataFromPropertyFile("Browser"));
			lp = new LoginPageBusinessLayer(driver);
		}
		sa.assertAll();
		}

	@Parameters({ "projectName"})
	@Test
	public void M8Tc035_verifyUIofWrenchPopUp(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		String[][] userAndPassword = {{superAdminUserName,adminPassword},{crmUser1EmailID,adminPassword}};
		for (String[] userPass : userAndPassword) {
		lp.CRMLogin(userPass[0], userPass[1]);
			ThreadSleep(5000);
		if(click(driver, home.sdgGridSideIcons(SDGGridName.Deals,SDGGridSideIcons.Manage_fields,5), "manage field icon", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "clicked on manage field icon of "+SDGGridName.Deals, YesNo.No);
			List<WebElement> lst = home.sdgGridSelectVisibleFieldsListInManageFieldPopUp();
			
			if(home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Cancel, 10)!=null) {
				log(LogStatus.PASS, "Cancel button is displaying", YesNo.No);
			}else {
				log(LogStatus.PASS, "Cancel button is not displaying", YesNo.No);
				sa.assertTrue(false, "Cancel button is not displaying");
			}
			
			if(home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Save, 10)!=null) {
				log(LogStatus.PASS, "Save button is displaying", YesNo.No);
			}else {
				log(LogStatus.PASS, "Save button is not displaying", YesNo.No);
				sa.assertTrue(false, "Save button is not displaying");
			}
			
			if(home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.close, 10)!=null) {
				log(LogStatus.PASS, " Select field to display and Cross icon is displaying", YesNo.No);
			}else {
				log(LogStatus.PASS, "Select field to display and Cross icon is not displaying", YesNo.No);
				sa.assertTrue(false, "Select field to display and Cross icon is not displaying");
			}
			
			if(home.sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp(10)!=null) {
				log(LogStatus.PASS, "field finder is displaying", YesNo.No);
			}else {
				log(LogStatus.PASS, "field finder is not displaying", YesNo.No);
				sa.assertTrue(false, "field finder is not displaying");
			}
			
			
			if(home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Move_Up, 10)!=null) {
				log(LogStatus.PASS, " Move up button is displaying", YesNo.No);
			}else {
				log(LogStatus.PASS, "Move up button is not displaying", YesNo.No);
				sa.assertTrue(false, "Move up button is not displaying");
			}
			if(home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Move_Down, 10)!=null) {
				log(LogStatus.PASS, " Move down button is displaying", YesNo.No);
			}else {
				log(LogStatus.PASS, "Move down button is not displaying", YesNo.No);
				sa.assertTrue(false, "Move down button is not displaying");
			}
			String dealVisibleFieldList ="Deal,Stage,Source Firm,Notes,Source Firm";
			lst = home.sdgGridSelectVisibleFieldsListInManageFieldPopUp();
			if(compareMultipleList(driver, dealVisibleFieldList, lst).isEmpty()) {
				log(LogStatus.PASS, "visible list is verfied for "+SDGGridName.Deals, YesNo.No);
			}else {
				log(LogStatus.FAIL, "visible list is not verfied for "+SDGGridName.Deals, YesNo.Yes);
				sa.assertTrue(false, "visible list is not verfied for "+SDGGridName.Deals);
			}
			if(click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Cancel, 10), "cancel button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.PASS, "clicked on cancel button for "+SDGGridName.Deals, YesNo.No);
			}else {
				log(LogStatus.PASS, "Not able to click on cancel button for "+SDGGridName.Deals, YesNo.No);
				sa.assertTrue(false, "Not able to click on cancel button for "+SDGGridName.Deals);
			}
		}else {
			log(LogStatus.PASS, "Not able to click on manage field icon of "+SDGGridName.Deals, YesNo.No);
			sa.assertTrue(false, "Not able to click on manage field icon of "+SDGGridName.Deals);
		}
		lp.CRMlogout();
		closeBrowser();
		config(ExcelUtils.readDataFromPropertyFile("Browser"));
		lp = new LoginPageBusinessLayer(driver);
	}
	sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc036_verifytoolTipAndAddFunctionalityOfWrenchIcon(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		String[][] userAndPassword = {{superAdminUserName,adminPassword},{crmUser1EmailID,adminPassword}};
		for (String[] userPass : userAndPassword) {
		lp.CRMLogin(userPass[0], userPass[1]);
			ThreadSleep(5000);
		if(click(driver, home.sdgGridSideIcons(SDGGridName.Deals,SDGGridSideIcons.Manage_fields,5), "manage field icon", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "clicked on manage field icon of "+SDGGridName.Deals, YesNo.No);
			List<WebElement> lst = home.sdgGridSelectVisibleFieldsListInManageFieldPopUp();
			
			if(userPass[0]== superAdminUserName) {
				if(selectVisibleTextFromDropDown(driver, home.sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp(10), "sdgGridSelectFieldToDisplayFieldFinderDropDown", "Deal Description")) {
					log(LogStatus.PASS, "select Deal Description from field finder", YesNo.No);
					ThreadSleep(2000);
					if(click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Add, 10), "Add button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "clicked on Add button for "+SDGGridName.Deals, YesNo.No);
						ThreadSleep(1000);
						
						if(click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Save, 10), "Save button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "clicked on Save button for "+SDGGridName.Deals, YesNo.No);
						}else {
							log(LogStatus.PASS, "Not able to click on Save button for "+SDGGridName.Deals, YesNo.No);
							sa.assertTrue(false, "Not able to click on Save button for "+SDGGridName.Deals);
						}
						
					}else {
						log(LogStatus.PASS, "Not able to click on Add button for "+SDGGridName.Deals, YesNo.No);
						sa.assertTrue(false, "Not able to click on Add button for "+SDGGridName.Deals);
					}
					
				}else {
					log(LogStatus.PASS, "Cannot select Deal Description from field finder", YesNo.No);
					sa.assertTrue(false, "Cannot select Deal Description from field finder");
				}
			}
			lst = home.sdgGridHeadersLabelNameList(SDGGridName.Deals);
			String headerName="Deal,Stage,Source Firm,Notes,Source Firm,Deal Description";
			if(compareMultipleList(driver, headerName, lst).isEmpty()) {
				log(LogStatus.PASS, SDGGridName.Deals+" SDG Grid Header Name is verified ", YesNo.No);
			}else {
				log(LogStatus.FAIL,SDGGridName.Deals+" SDG Grid Header Name is not verified ", YesNo.Yes);
				sa.assertTrue(false, SDGGridName.Deals+" SDG Grid Header Name is not verified ");
			}
		}else {
			log(LogStatus.PASS, "Not able to click on manage field icon of "+SDGGridName.Deals, YesNo.No);
			sa.assertTrue(false, "Not able to click on manage field icon of "+SDGGridName.Deals);
		}
		lp.CRMlogout();
		closeBrowser();
		config(ExcelUtils.readDataFromPropertyFile("Browser"));
		lp = new LoginPageBusinessLayer(driver);
	}
	sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc037_verifySaveCancelButtonFunctionality(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		String[][] userAndPassword = {{superAdminUserName,adminPassword},{crmUser1EmailID,adminPassword}};
		for (String[] userPass : userAndPassword) {
		lp.CRMLogin(userPass[0], userPass[1]);
			ThreadSleep(5000);
		if(click(driver, home.sdgGridSideIcons(SDGGridName.Deals,SDGGridSideIcons.Manage_fields,5), "manage field icon", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "clicked on manage field icon of "+SDGGridName.Deals, YesNo.No);
			List<WebElement> lst = home.sdgGridSelectVisibleFieldsListInManageFieldPopUp();
			
			if(userPass[0]== superAdminUserName) {
				for(int i=0; i<6; i++) {
					if(i==0) {
						if(selectVisibleTextFromDropDown(driver, home.sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp(10), "drop down", "Notes")) {
							log(LogStatus.PASS, "select Notes text from visible field", YesNo.No);
							ThreadSleep(2000);

							if(click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.close, 10), "Add button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "clicked on close button for "+SDGGridName.Deals, YesNo.No);
								ThreadSleep(1000);

								lst = home.sdgGridHeadersLabelNameList(SDGGridName.Deals);
								String headerName="Deal,Stage,Source Firm,Notes,Source Firm,Deal Description";
								if(compareMultipleList(driver, headerName, lst).isEmpty()) {
									log(LogStatus.PASS, SDGGridName.Deals+" SDG Grid Header Name is verified ", YesNo.No);
								}else {
									log(LogStatus.FAIL,SDGGridName.Deals+" SDG Grid Header Name is not verified ", YesNo.Yes);
									sa.assertTrue(false, SDGGridName.Deals+" SDG Grid Header Name is not verified ");
								}


							}else {
								log(LogStatus.PASS, "Not able to click on close button for "+SDGGridName.Deals, YesNo.No);
								sa.assertTrue(false, "Not able to click on close button for "+SDGGridName.Deals);
							}

						}else {
							log(LogStatus.PASS, "Cannot select Deal Description from field finder", YesNo.No);
							sa.assertTrue(false, "Cannot select Deal Description from field finder");
						}	

					}
						
						if(i==1) {

							if(selectVisibleTextFromDropDown(driver, home.sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp(10), "drop down", "Notes")) {
								log(LogStatus.PASS, "select Notes text from visible field", YesNo.No);
								ThreadSleep(2000);

								if(click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Cancel, 10), "Cancel button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.PASS, "clicked on Cancel button for "+SDGGridName.Deals, YesNo.No);
									ThreadSleep(1000);

									lst = home.sdgGridHeadersLabelNameList(SDGGridName.Deals);
									String headerName="Deal,Stage,Source Firm,Notes,Source Firm,Deal Description";
									if(compareMultipleList(driver, headerName, lst).isEmpty()) {
										log(LogStatus.PASS, SDGGridName.Deals+" SDG Grid Header Name is verified ", YesNo.No);
									}else {
										log(LogStatus.FAIL,SDGGridName.Deals+" SDG Grid Header Name is not verified ", YesNo.Yes);
										sa.assertTrue(false, SDGGridName.Deals+" SDG Grid Header Name is not verified ");
									}

								}else {
									log(LogStatus.PASS, "Not able to click on Cancel button for "+SDGGridName.Deals, YesNo.No);
									sa.assertTrue(false, "Not able to click on Cancel button for "+SDGGridName.Deals);
								}

							}else {
								log(LogStatus.PASS, "Cannot select Deal Description from field finder", YesNo.No);
								sa.assertTrue(false, "Cannot select Deal Description from field finder");
							}	

						}
						
						
						if(i==2) {

							if(selectVisibleTextFromDropDown(driver, home.sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp(10), "drop down", "Notes")) {
								log(LogStatus.PASS, "select Notes text from visible field", YesNo.No);
								ThreadSleep(2000);

								if(click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Save, 10), "Save button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.PASS, "clicked on Save button for "+SDGGridName.Deals, YesNo.No);
									ThreadSleep(1000);

									lst = home.sdgGridHeadersLabelNameList(SDGGridName.Deals);
									String headerName="Notes";
									if(!compareMultipleList(driver, headerName, lst).isEmpty()) {
										log(LogStatus.PASS, SDGGridName.Deals+" SDG Grid Header Name is verified ", YesNo.No);
									}else {
										log(LogStatus.FAIL,SDGGridName.Deals+" SDG Grid Header Name is not verified ", YesNo.Yes);
										sa.assertTrue(false, SDGGridName.Deals+" SDG Grid Header Name is not verified ");
									}

								}else {
									log(LogStatus.PASS, "Not able to click on Save button for "+SDGGridName.Deals, YesNo.No);
									sa.assertTrue(false, "Not able to click on Save button for "+SDGGridName.Deals);
								}

							}else {
								log(LogStatus.PASS, "Cannot select Deal Description from field finder", YesNo.No);
								sa.assertTrue(false, "Cannot select Deal Description from field finder");
							}	

						}
						if(i==3) {
							ThreadSleep(2000);
							
							
							if(click(driver, home.sdgGridVisibleFieldTextInManageFieldPopUp("Source Firm", 10), "Source Firm text", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "Remove Source Firm text from visible field", YesNo.No);
								ThreadSleep(2000);

								if(click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Move_Up, 10), "Save button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.PASS, "clicked on Save button for "+SDGGridName.Deals, YesNo.No);
									ThreadSleep(1000);
									
									if(click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.close, 10), "close button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.PASS, "clicked on close button for "+SDGGridName.Deals, YesNo.No);
										ThreadSleep(1000);
										
										lst = home.sdgGridHeadersLabelNameList(SDGGridName.Deals);
										
										List<String> actualResult = new ArrayList<String>();
										
										if(!lst.isEmpty()) {
											for(int i1=0; i1<lst.size(); i1++) {
												actualResult.add(lst.get(i1).getText().trim());
											}
										}
										String headerName="Deal,Stage,Source Firm,Source Firm,Deal Description";
										List<String> expctedResult =createListOutOfString(headerName);
										
										if(compareList( expctedResult, actualResult)) {
											log(LogStatus.PASS, SDGGridName.Deals+" SDG Grid Header Name is verified ", YesNo.No);
										}else {
											log(LogStatus.FAIL,SDGGridName.Deals+" SDG Grid Header Name is not verified ", YesNo.Yes);
											sa.assertTrue(false, SDGGridName.Deals+" SDG Grid Header Name is not verified ");
										}
										
									}else {
										log(LogStatus.PASS, "Not able to click on close button for "+SDGGridName.Deals, YesNo.No);
										sa.assertTrue(false, "Not able to click on close button for "+SDGGridName.Deals);
									}

								}else {
									log(LogStatus.PASS, "Not able to click on move up button for "+SDGGridName.Deals, YesNo.No);
									sa.assertTrue(false, "Not able to click on move up button for "+SDGGridName.Deals);
								}

							}else {
								log(LogStatus.PASS, "Cannot select Source Firm from visible field finder", YesNo.No);
								sa.assertTrue(false, "Cannot select Source Firm from visible field finder");
							}	


						}
						if(i==4) {
							ThreadSleep(2000);
							if(click(driver, home.sdgGridVisibleFieldTextInManageFieldPopUp("Source Firm", 10), "Source Firm text", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "Remove Source Firm text from visible field", YesNo.No);
								ThreadSleep(2000);

								if(click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Move_Down, 10), "Save button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.PASS, "clicked on move down button for "+SDGGridName.Deals, YesNo.No);
									ThreadSleep(1000);
									
									if(click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Cancel, 10), "close button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.PASS, "clicked on cancel button for "+SDGGridName.Deals, YesNo.No);
										ThreadSleep(1000);
										
										lst = home.sdgGridHeadersLabelNameList(SDGGridName.Deals);
										
										List<String> actualResult = new ArrayList<String>();
										
										if(!lst.isEmpty()) {
											for(int i1=0; i1<lst.size(); i1++) {
												actualResult.add(lst.get(i1).getText().trim());
											}
										}
										String headerName="Deal,Stage,Source Firm,Source Firm,Deal Description";
										List<String> expctedResult =createListOutOfString(headerName);
										
										if(compareList( expctedResult, actualResult)) {
											log(LogStatus.PASS, SDGGridName.Deals+" SDG Grid Header Name is verified ", YesNo.No);
										}else {
											log(LogStatus.FAIL,SDGGridName.Deals+" SDG Grid Header Name is not verified ", YesNo.Yes);
											sa.assertTrue(false, SDGGridName.Deals+" SDG Grid Header Name is not verified ");
										}
										
									}else {
										log(LogStatus.PASS, "Not able to click on cancel button for "+SDGGridName.Deals, YesNo.No);
										sa.assertTrue(false, "Not able to click on cancel button for "+SDGGridName.Deals);
									}

								}else {
									log(LogStatus.PASS, "Not able to click on move down button for "+SDGGridName.Deals, YesNo.No);
									sa.assertTrue(false, "Not able to click on move down button for "+SDGGridName.Deals);
								}

							}else {
								log(LogStatus.PASS, "Cannot select Source Firm from visible field finder", YesNo.No);
								sa.assertTrue(false, "Cannot select Source Firm from visible field finder");
							}	


						}
						
						if(i==5) {
							ThreadSleep(2000);
							if(click(driver, home.sdgGridVisibleFieldTextInManageFieldPopUp("Source Firm", 10), "Source Firm text", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "Remove Source Firm text from visible field", YesNo.No);
								ThreadSleep(2000);

								if(click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Move_Up, 10), "Save button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.PASS, "clicked on Save button for "+SDGGridName.Deals, YesNo.No);
									ThreadSleep(1000);
									
									if(click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Save, 10), "close button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.PASS, "clicked on save button for "+SDGGridName.Deals, YesNo.No);
										ThreadSleep(1000);
										
										lst = home.sdgGridHeadersLabelNameList(SDGGridName.Deals);
										
										List<String> actualResult = new ArrayList<String>();
										
										if(!lst.isEmpty()) {
											for(int i1=0; i1<lst.size(); i1++) {
												actualResult.add(lst.get(i1).getText().trim());
											}
										}
										String headerName="Deal,Source Firm,Stage,Source Firm,Deal Description";
										List<String> expctedResult =createListOutOfString(headerName);
										if(compareList( expctedResult, actualResult)) {
											log(LogStatus.PASS, SDGGridName.Deals+" SDG Grid Header Name is verified ", YesNo.No);
										}else {
											log(LogStatus.FAIL,SDGGridName.Deals+" SDG Grid Header Name is not verified ", YesNo.Yes);
											sa.assertTrue(false, SDGGridName.Deals+" SDG Grid Header Name is not verified ");
										}
										
									}else {
										log(LogStatus.PASS, "Not able to click on save button for "+SDGGridName.Deals, YesNo.No);
										sa.assertTrue(false, "Not able to click on save button for "+SDGGridName.Deals);
									}

								}else {
									log(LogStatus.PASS, "Not able to click on move up button for "+SDGGridName.Deals, YesNo.No);
									sa.assertTrue(false, "Not able to click on move up button for "+SDGGridName.Deals);
								}

							}else {
								log(LogStatus.PASS, "Cannot select Source Firm from visible field finder", YesNo.No);
								sa.assertTrue(false, "Cannot select Source Firm from visible field finder");
							}	


						}
						
					
				}
			}else {
				lst = home.sdgGridHeadersLabelNameList(SDGGridName.Deals);
				
				List<String> actualResult = new ArrayList<String>();
				
				if(!lst.isEmpty()) {
					for(int i1=0; i1<lst.size(); i1++) {
						actualResult.add(lst.get(i1).getText().trim());
					}
				}
				String headerName="Deal,Source Firm,Stage,Source Firm,Deal Description";
				List<String> expctedResult =createListOutOfString(headerName);
				if(compareList( expctedResult, actualResult)) {
					log(LogStatus.PASS, SDGGridName.Deals+" SDG Grid Header Name is verified ", YesNo.No);
				}else {
					log(LogStatus.FAIL,SDGGridName.Deals+" SDG Grid Header Name is not verified ", YesNo.Yes);
					sa.assertTrue(false, SDGGridName.Deals+" SDG Grid Header Name is not verified ");
				}
				
				if(click(driver, home.sdgGridVisibleFieldTextInManageFieldPopUp("Source Firm", 10), "Source Firm text", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.PASS, "Remove Source Firm text from visible field", YesNo.No);
					ThreadSleep(2000);

					if(click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Move_Down, 10), "Save button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "clicked on Move_Down button for "+SDGGridName.Deals, YesNo.No);
						ThreadSleep(1000);
						
						if(click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Save, 10), "close button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "clicked on save button for "+SDGGridName.Deals, YesNo.No);
							ThreadSleep(1000);
							
							lst = home.sdgGridHeadersLabelNameList(SDGGridName.Deals);
							
							actualResult = new ArrayList<String>();
							
							if(!lst.isEmpty()) {
								for(int i1=0; i1<lst.size(); i1++) {
									actualResult.add(lst.get(i1).getText().trim());
								}
							}
							headerName="Deal,Stage,Source Firm,Source Firm,Deal Description";
							expctedResult =createListOutOfString(headerName);
							if(compareList( expctedResult, actualResult)) {
								log(LogStatus.PASS, SDGGridName.Deals+" SDG Grid Header Name is verified for user after change the position", YesNo.No);
							}else {
								log(LogStatus.FAIL,SDGGridName.Deals+" SDG Grid Header Name is not verified for user after change the position", YesNo.Yes);
								sa.assertTrue(false, SDGGridName.Deals+" SDG Grid Header Name is not verified for user after change the position");
							}
						}else {
							log(LogStatus.PASS, "Not able to click on save button for "+SDGGridName.Deals, YesNo.No);
							sa.assertTrue(false, "Not able to click on save button for "+SDGGridName.Deals);
						}

					}else {
						log(LogStatus.PASS, "Not able to click on Move_Down button for "+SDGGridName.Deals, YesNo.No);
						sa.assertTrue(false, "Not able to click on Move_Down button for "+SDGGridName.Deals);
					}

				}else {
					log(LogStatus.PASS, "Cannot select Source Firm from visible field finder", YesNo.No);
					sa.assertTrue(false, "Cannot select Source Firm from visible field finder");
				}
			}
		}else {
			log(LogStatus.PASS, "Not able to click on manage field icon of "+SDGGridName.Deals, YesNo.No);
			sa.assertTrue(false, "Not able to click on manage field icon of "+SDGGridName.Deals);
		}
		lp.CRMlogout();
		closeBrowser();
		config(ExcelUtils.readDataFromPropertyFile("Browser"));
		lp = new LoginPageBusinessLayer(driver);
	}
	sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc038_verifyAddButtonFunctionality(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		String[][] userAndPassword = {{superAdminUserName,adminPassword},{crmUser1EmailID,adminPassword}};
		for (String[] userPass : userAndPassword) {
		lp.CRMLogin(userPass[0], userPass[1]);
			ThreadSleep(5000);
		if(click(driver, home.sdgGridSideIcons(SDGGridName.Deals,SDGGridSideIcons.Manage_fields,5), "manage field icon", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "clicked on manage field icon of "+SDGGridName.Deals, YesNo.No);
			List<WebElement> lst = home.sdgGridSelectVisibleFieldsListInManageFieldPopUp();
			
			if(userPass[0]== superAdminUserName) {
				for(int i=0; i<2; i++) {
					if(i==0) {
						if(selectVisibleTextFromDropDown(driver, home.sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp(10), "drop down", "Deal Keywords")) {
							log(LogStatus.PASS, "select Deal Keywords text from visible field", YesNo.No);
							ThreadSleep(2000);
							click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Add, 10), "Add button", action.SCROLLANDBOOLEAN);


							if(selectVisibleTextFromDropDown(driver, home.sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp(10), "drop down", "Deal Quality Score")) {
								log(LogStatus.PASS, "select Deal Quality Score text from visible field", YesNo.No);
								ThreadSleep(2000);

								click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Add, 10), "Add button", action.SCROLLANDBOOLEAN);


								if(selectVisibleTextFromDropDown(driver, home.sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp(10), "drop down", "EBITDA")) {
									log(LogStatus.PASS, "select EBITDA text from visible field", YesNo.No);
									ThreadSleep(2000);
									click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Add, 10), "Add button", action.SCROLLANDBOOLEAN);


									if(click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Save, 10), "Add button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.PASS, "clicked on save button for "+SDGGridName.Deals, YesNo.No);
										ThreadSleep(1000);

										lst = home.sdgGridHeadersLabelNameList(SDGGridName.Deals);
										String headerName="Deal Keywords,Deal Quality Score,EBITDA";
										if(compareMultipleList(driver, headerName, lst).isEmpty()) {
											log(LogStatus.PASS, SDGGridName.Deals+" SDG Grid Header Name is verified ", YesNo.No);
										}else {
											log(LogStatus.FAIL,SDGGridName.Deals+" SDG Grid Header Name is not verified ", YesNo.Yes);
											sa.assertTrue(false, SDGGridName.Deals+" SDG Grid Header Name is not verified ");
										}
									}else {
										log(LogStatus.PASS, "Not able to click on save button for "+SDGGridName.Deals, YesNo.No);
										sa.assertTrue(false, "Not able to click on save button for "+SDGGridName.Deals);
									}

								}else {
									log(LogStatus.PASS, "Cannot select EBITDA from field finder", YesNo.No);
									sa.assertTrue(false, "Cannot select EBITDA from field finder");
								}	

							}else {
								log(LogStatus.PASS, "Cannot select Deal Description from field finder", YesNo.No);
								sa.assertTrue(false, "Cannot select Deal Description from field finder");
							}	
						}else {
							log(LogStatus.PASS, "Cannot select Deal Keywords from field finder", YesNo.No);
							sa.assertTrue(false, "Cannot select Deal Keywords from field finder");
						}	

					}
					
					
					if(i==1) {
						if(selectVisibleTextFromDropDown(driver, home.sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp(10), "drop down", "Company Name-->Institution")) {
							log(LogStatus.PASS, "select Deal Keywords text from visible field", YesNo.No);
							ThreadSleep(2000);
							if(selectVisibleTextFromDropDown(driver, home.sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp(10), "drop down", "Bank Name")) {
								log(LogStatus.PASS, "select Deal Quality Score text from visible field", YesNo.No);
								ThreadSleep(2000);
								if(click(driver,home.sdgGridFieldFinderRemoveSelectedFieldRemoveButtonInManageFieldPopUp("Bank Name",10), "Bank Name remove button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.PASS, "clicked on remove button for "+SDGGridName.Deals, YesNo.No);
									ThreadSleep(1000);
									
									click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.close, 10), "close button", action.SCROLLANDBOOLEAN);
									
								}else {
									log(LogStatus.PASS, "Not able to click on remove button for "+SDGGridName.Deals, YesNo.No);
									sa.assertTrue(false, "Not able to click on remove button for "+SDGGridName.Deals);
								}
							}else {
								log(LogStatus.PASS, "Cannot select Bank Name from field finder", YesNo.No);
								sa.assertTrue(false, "Cannot select Bank Name from field finder");
							}	
						}else {
							log(LogStatus.PASS, "Cannot select Bank Name from field finder", YesNo.No);
							sa.assertTrue(false, "Cannot select Bank Name from field finder");
						}	

					}
					
					if(i==2) {

						if(selectVisibleTextFromDropDown(driver, home.sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp(10), "drop down", "Company Name-->Institution")) {
							log(LogStatus.PASS, "select Deal Keywords text from visible field", YesNo.No);
							ThreadSleep(2000);
							if(selectVisibleTextFromDropDown(driver, home.sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp(10), "drop down", "Bank Name")) {
								log(LogStatus.PASS, "select Deal Quality Score text from visible field", YesNo.No);
								ThreadSleep(2000);

								click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Add, 10), "Add button", action.SCROLLANDBOOLEAN);

								if(click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Save, 10), "Add button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.PASS, "clicked on save button for "+SDGGridName.Deals, YesNo.No);
									ThreadSleep(1000);

									lst = home.sdgGridHeadersLabelNameList(SDGGridName.Deals);
									String headerName="Bank Name";
									if(compareMultipleList(driver, headerName, lst).isEmpty()) {
										log(LogStatus.PASS, SDGGridName.Deals+" SDG Grid Header Name is verified ", YesNo.No);
									}else {
										log(LogStatus.FAIL,SDGGridName.Deals+" SDG Grid Header Name is not verified ", YesNo.Yes);
										sa.assertTrue(false, SDGGridName.Deals+" SDG Grid Header Name is not verified ");
									}
								}else {
									log(LogStatus.PASS, "Not able to click on save button for "+SDGGridName.Deals, YesNo.No);
									sa.assertTrue(false, "Not able to click on save button for "+SDGGridName.Deals);
								}
							}else {
								log(LogStatus.PASS, "Cannot select Deal Description from field finder", YesNo.No);
								sa.assertTrue(false, "Cannot select Deal Description from field finder");
							}	
						}else {
							log(LogStatus.PASS, "Cannot select Deal Keywords from field finder", YesNo.No);
							sa.assertTrue(false, "Cannot select Deal Keywords from field finder");
						}	

					}
				}
			}else {
				
				String headerName="Bank Name";
				if(compareMultipleList(driver, headerName, home.sdgGridHeadersLabelNameList(SDGGridName.Deals)).isEmpty()) {
					log(LogStatus.PASS, SDGGridName.Deals+" SDG Grid Header Name is verified for user", YesNo.No);
				}else {
					log(LogStatus.FAIL,SDGGridName.Deals+" SDG Grid Header Name is not verified for user", YesNo.Yes);
					sa.assertTrue(false, SDGGridName.Deals+" SDG Grid Header Name is not verified for user");
				}
			}
		}else {
			log(LogStatus.PASS, "Not able to click on manage field icon of "+SDGGridName.Deals, YesNo.No);
			sa.assertTrue(false, "Not able to click on manage field icon of "+SDGGridName.Deals);
		}
		lp.CRMlogout();
		closeBrowser();
		config(ExcelUtils.readDataFromPropertyFile("Browser"));
		lp = new LoginPageBusinessLayer(driver);
	}
	sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M1Tc039_1_createFieldsForCustomObject(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create Fields Objects for custom object Marketing Event");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create Fields Objects for custom object Marketing Event",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create Fields Objects for custom object Marketing Event");
			}
			ThreadSleep(3000);
			String[][] labelAndValues= {{M8_Object1FieldName,M8_Object1,null,null},
					{M8_Object2FieldName,M8_Object2,null,null},
					{M8_Object3FieldName,M8_Object3,null,null},
					{M8_Object4FieldName,M8_Object4,null,null}};
			for(String[] objects : labelAndValues) {
				
				String [][] valuesandLabel = {{objects[2],objects[3]}};
				
				
				
				if(setup.addCustomFieldforFormula(environment,mode,object.Deal,ObjectFeatureName.FieldAndRelationShip,objects[0],objects[1], valuesandLabel, null,null)) {
					log(LogStatus.PASS, "Field Object is created for :"+objects[1], YesNo.No);
				}else {
					log(LogStatus.PASS, "Field Object is not created for :"+objects[1], YesNo.Yes);
					sa.assertTrue(false, "Field Object is not created for :"+objects[1]);
				}
			}
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		}else {
			log(LogStatus.ERROR, "Not able to click on setup link so cannot create Fields Objects for custom object Marketing Event", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link so cannot create Fields Objects for custom object Marketing Event");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc039_2_verifyAddCustomFunctionality(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		String[][] userAndPassword = {{superAdminUserName,adminPassword},{crmUser1EmailID,adminPassword}};
		for (String[] userPass : userAndPassword) {
		lp.CRMLogin(userPass[0], userPass[1]);
			ThreadSleep(5000);
		if(click(driver, home.sdgGridSideIcons(SDGGridName.Deals,SDGGridSideIcons.Manage_fields,5), "manage field icon", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "clicked on manage field icon of "+SDGGridName.Deals, YesNo.No);
			List<WebElement> lst = home.sdgGridSelectVisibleFieldsListInManageFieldPopUp();
			
			if(userPass[0]== superAdminUserName) {
				for(int i=0; i<1; i++) {
					if(i==0) {
						if(selectVisibleTextFromDropDown(driver, home.sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp(10), "drop down", M8_Object1)) {
							log(LogStatus.PASS, "select "+M8_Object1+" text from visible field", YesNo.No);
							ThreadSleep(2000);
							click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Add, 10), "Add button", action.SCROLLANDBOOLEAN);
							
							
							if(selectVisibleTextFromDropDown(driver, home.sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp(10), "drop down", M8_Object2)) {
								log(LogStatus.PASS, "select "+M8_Object2+" text from visible field", YesNo.No);
								ThreadSleep(2000);
								
								click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Add, 10), "Add button", action.SCROLLANDBOOLEAN);
								
								
								if(selectVisibleTextFromDropDown(driver, home.sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp(10), "drop down", M8_Object3)) {
									log(LogStatus.PASS, "select "+M8_Object3+" text from visible field", YesNo.No);
									ThreadSleep(2000);
									click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Add, 10), "Add button", action.SCROLLANDBOOLEAN);
									
									
									if(click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Save, 10), "save button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.PASS, "clicked on save button for "+SDGGridName.Deals, YesNo.No);
										ThreadSleep(1000);

										lst = home.sdgGridHeadersLabelNameList(SDGGridName.Deals);
										String headerName=M8_Object1+","+M8_Object2+","+M8_Object3;
										if(compareMultipleList(driver, headerName, lst).isEmpty()) {
											log(LogStatus.PASS, SDGGridName.Deals+" SDG Grid Header Name is verified ", YesNo.No);
										}else {
											log(LogStatus.FAIL,SDGGridName.Deals+" SDG Grid Header Name is not verified ", YesNo.Yes);
											sa.assertTrue(false, SDGGridName.Deals+" SDG Grid Header Name is not verified ");
										}
									}else {
										log(LogStatus.PASS, "Not able to click on save button for "+SDGGridName.Deals, YesNo.No);
										sa.assertTrue(false, "Not able to click on save button for "+SDGGridName.Deals);
									}
									
								}else {
									log(LogStatus.PASS, "Cannot select "+M8_Object3+" from field finder", YesNo.No);
									sa.assertTrue(false, "Cannot select "+M8_Object3+" from field finder");
								}	
								
							}else {
								log(LogStatus.PASS, "Cannot select "+M8_Object2+" from field finder", YesNo.No);
								sa.assertTrue(false, "Cannot select "+M8_Object2+" from field finder");
							}	
						}else {
							log(LogStatus.PASS, "Cannot select "+M8_Object1+" from field finder", YesNo.No);
							sa.assertTrue(false, "Cannot select "+M8_Object1+" from field finder");
						}	

					}
				}
			}else {
				lst = home.sdgGridHeadersLabelNameList(SDGGridName.Deals);
				String headerName=M8_Object1+","+M8_Object2+","+M8_Object3;
				if(compareMultipleList(driver, headerName, lst).isEmpty()) {
					log(LogStatus.PASS, SDGGridName.Deals+" SDG Grid Header Name is verified for user", YesNo.No);
				}else {
					log(LogStatus.FAIL,SDGGridName.Deals+" SDG Grid Header Name is not verified for user", YesNo.Yes);
					sa.assertTrue(false, SDGGridName.Deals+" SDG Grid Header Name is not verified for user");
				}
			}
		}else {
			log(LogStatus.PASS, "Not able to click on manage field icon of "+SDGGridName.Deals, YesNo.No);
			sa.assertTrue(false, "Not able to click on manage field icon of "+SDGGridName.Deals);
		}
		lp.CRMlogout();
		closeBrowser();
		config(ExcelUtils.readDataFromPropertyFile("Browser"));
		lp = new LoginPageBusinessLayer(driver);
	}
	sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc040_1_removeEditPermissionOfPEStandardUser(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		
		/////////////////////////////////////////////////////
		String[] userNames= {"PE Standard User"};
		
		String a ="Sortable Data Grids";
		String b ="Sortable Data Grid Actions";
		String c ="Sortable Data Grid Fields";
		String d ="Sortable Data Grid Preferences";
		
		String permission="Read";
		for (String userName : userNames) {
			switchToDefaultContent(driver);
			if (home.clickOnSetUpLink()) {
				String parentID = switchOnWindow(driver);
				if (parentID!=null) {
					log(LogStatus.INFO, "Able to switch on new window, so going to remove "+permission+" for "+a+" "+b+" "+c+" "+d, YesNo.No);
					ThreadSleep(500);
					if(setup.searchStandardOrCustomObject(environment,mode, object.Profiles)) {
						log(LogStatus.INFO, "click on Object : "+object.Profiles, YesNo.No);
						ThreadSleep(2000);
						if (setup.permissionChangeForUserONObject(driver, userName, new String[][]{{a,permission},{b,permission},{c,permission},{d,permission}}, 20)) {
							log(LogStatus.PASS,permission+ " permission change for "+userName+" on object "+a+" "+b+" "+c+" "+d,YesNo.No);
						} else {
							sa.assertTrue(false, permission+ " permission not change for "+userName+" on object "+a+" "+b+" "+c+" "+d);
							log(LogStatus.FAIL,permission+ " permission not change for "+userName+" on object "+a+" "+b+" "+c+" "+d,YesNo.Yes);
						}
					}else {
						log(LogStatus.ERROR, "Not able to search/click on "+object.Profiles, YesNo.Yes);
						sa.assertTrue(false, "Not able to search/click on "+object.Profiles);
					}
					driver.close();
					driver.switchTo().window(parentID);
				}else {
					log(LogStatus.FAIL, "could not find new window to switch, so cannot to remove "+permission+" for "+a+" "+b+" "+c+" "+d, YesNo.Yes);
					sa.assertTrue(false, "could not find new window to switch, to remove "+permission+" for "+a+" "+b+" "+c+" "+d);
				}

			}else {
				log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link");	
			}
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc040_2_checkErrorMsgAfterRemoveEditPermissionOfPEStandardUser(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID,adminPassword);
		ThreadSleep(5000);
		if(click(driver, home.sdgGridSideIcons(SDGGridName.Deals,SDGGridSideIcons.Manage_fields,5), "manage field icon", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "clicked on manage field icon of "+SDGGridName.Deals, YesNo.No);
			List<WebElement> lst = home.sdgGridSelectVisibleFieldsListInManageFieldPopUp();
			for(int i=0; i<1; i++) {
				if(i==0) {
					if(selectVisibleTextFromDropDown(driver, home.sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp(10), "drop down", "Deal Type")) {
						log(LogStatus.PASS, "select Deal Type text from visible field", YesNo.No);
						ThreadSleep(2000);
						click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Add, 10), "Add button", action.SCROLLANDBOOLEAN);

						if(click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Save, 10), "save button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "clicked on save button for "+SDGGridName.Deals, YesNo.No);
							ThreadSleep(1000);
							WebElement ele = home.getSelectFieldPopUpErrorMsg(10);
							if(ele!=null) {
								if(ele.getText().trim().equalsIgnoreCase(HomePageErrorMessage.selectFieldPopUpErrorMessage)) {
									log(LogStatus.PASS, "Error Message is verified "+HomePageErrorMessage.selectFieldPopUpErrorMessage, YesNo.No);
								}else {
									log(LogStatus.PASS, "Error Message is not verified "+HomePageErrorMessage.selectFieldPopUpErrorMessage, YesNo.Yes);
									sa.assertTrue(false, "Error Message is not verified "+HomePageErrorMessage.selectFieldPopUpErrorMessage);
								}
							}else {
								log(LogStatus.PASS, "error message is not displaying "+SDGGridName.Deals, YesNo.No);
								sa.assertTrue(false, "error message is not displaying "+SDGGridName.Deals);
							}
							click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.close, 10), "close button", action.SCROLLANDBOOLEAN);
						}else {
							log(LogStatus.PASS, "Not able to click on save button for "+SDGGridName.Deals, YesNo.No);
							sa.assertTrue(false, "Not able to click on save button for "+SDGGridName.Deals);
						}
					}else {
						log(LogStatus.PASS, "Cannot select Deal Type from field finder", YesNo.No);
						sa.assertTrue(false, "Cannot select Deal Type from field finder");
					}	
				}
			}
		}else {
			log(LogStatus.PASS, "Not able to click on manage field icon of "+SDGGridName.Deals, YesNo.No);
			sa.assertTrue(false, "Not able to click on manage field icon of "+SDGGridName.Deals);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc041_1_AddEditPermissionOfPEStandardUser(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		String[] userNames= {"PE Standard User"};
		String a ="Sortable Data Grids";
		String b ="Sortable Data Grid Actions";
		String c ="Sortable Data Grid Fields";
		String d ="Sortable Data Grid Preferences";
		
		String permission="Edit";
		for (String userName : userNames) {
			switchToDefaultContent(driver);
			if (home.clickOnSetUpLink()) {
				String parentID = switchOnWindow(driver);
				if (parentID!=null) {
					log(LogStatus.INFO, "Able to switch on new window, so going to add "+permission+" for "+a+" "+b+" "+c+" "+d, YesNo.No);
					ThreadSleep(500);
					if(setup.searchStandardOrCustomObject(environment,mode, object.Profiles)) {
						log(LogStatus.INFO, "click on Object : "+object.Profiles, YesNo.No);
						ThreadSleep(2000);
						if (setup.permissionChangeForUserONObject(driver, userName, new String[][]{{a,permission},{b,permission},{c,permission},{d,permission}}, 20)) {
							log(LogStatus.PASS,permission+ " permission change for "+userName+" on object "+a+" "+b+" "+c+" "+d,YesNo.No);
						} else {
							sa.assertTrue(false, permission+ " permission not change for "+userName+" on object "+a+" "+b+" "+c+" "+d);
							log(LogStatus.FAIL,permission+ " permission not change for "+userName+" on object "+a+" "+b+" "+c+" "+d,YesNo.Yes);
						}
					}else {
						log(LogStatus.ERROR, "Not able to search/click on "+object.Profiles, YesNo.Yes);
						sa.assertTrue(false, "Not able to search/click on "+object.Profiles);
					}
					driver.close();
					driver.switchTo().window(parentID);
				}else {
					log(LogStatus.FAIL, "could not find new window to switch, so cannot to add "+permission+" for "+a+" "+b+" "+c+" "+d, YesNo.Yes);
					sa.assertTrue(false, "could not find new window to switch, to add "+permission+" for "+a+" "+b+" "+c+" "+d);
				}

			}else {
				log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link");	
			}
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc041_2_addFieldAfterAddEditPermissionOfPEStandardUser(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID,adminPassword);
		ThreadSleep(5000);
		if(click(driver, home.sdgGridSideIcons(SDGGridName.Deals,SDGGridSideIcons.Manage_fields,5), "manage field icon", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "clicked on manage field icon of "+SDGGridName.Deals, YesNo.No);
			List<WebElement> lst = home.sdgGridSelectVisibleFieldsListInManageFieldPopUp();
			for(int i=0; i<1; i++) {
				if(i==0) {
					if(selectVisibleTextFromDropDown(driver, home.sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp(10), "drop down", "Deal Type")) {
						log(LogStatus.PASS, "select Deal Type text from visible field", YesNo.No);
						ThreadSleep(2000);
						click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Add, 10), "Add button", action.SCROLLANDBOOLEAN);

						if(click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Save, 10), "save button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "clicked on save button for "+SDGGridName.Deals, YesNo.No);
							ThreadSleep(1000);
							lst = home.sdgGridHeadersLabelNameList(SDGGridName.Deals);
							String headerName="Deal Type";
							if(compareMultipleList(driver, headerName, lst).isEmpty()) {
								log(LogStatus.PASS, SDGGridName.Deals+" SDG Grid Header Name is verified ", YesNo.No);
							}else {
								log(LogStatus.FAIL,SDGGridName.Deals+" SDG Grid Header Name is not verified ", YesNo.Yes);
								sa.assertTrue(false, SDGGridName.Deals+" SDG Grid Header Name is not verified ");
							}
						}else {
							log(LogStatus.PASS, "Not able to click on save button for "+SDGGridName.Deals, YesNo.No);
							sa.assertTrue(false, "Not able to click on save button for "+SDGGridName.Deals);
						}
					}else {
						log(LogStatus.PASS, "Cannot select Deal Type from field finder", YesNo.No);
						sa.assertTrue(false, "Cannot select Deal Type from field finder");
					}	
				}
			}
		}else {
			log(LogStatus.PASS, "Not able to click on manage field icon of "+SDGGridName.Deals, YesNo.No);
			sa.assertTrue(false, "Not able to click on manage field icon of "+SDGGridName.Deals);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc042_1_createFieldsForCustomObjectWithMaxChar(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create Fields Objects for custom object ");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create Fields Objects for custom object ",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create Fields Objects for custom object ");
			}
			ThreadSleep(3000);
			String[][] labelAndValues = {{M8_Object5FieldName,M8_Object5,null,null},
					{M8_Object6FieldName,M8_Object6,"Related To","Institution"}};
			for(String[] objects : labelAndValues) {
				
				String [][] valuesandLabel = {{objects[2],objects[3]}};
				
				
				
				if(setup.addCustomFieldforFormula(environment,mode,object.Deal,ObjectFeatureName.FieldAndRelationShip,objects[0],objects[1], valuesandLabel, null,null)) {
					log(LogStatus.PASS, "Field Object is created for :"+objects[1], YesNo.No);
				}else {
					log(LogStatus.PASS, "Field Object is not created for :"+objects[1], YesNo.Yes);
					sa.assertTrue(false, "Field Object is not created for :"+objects[1]);
				}
			}
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		}else {
			log(LogStatus.ERROR, "Not able to click on setup link so cannot create Fields Objects for custom object", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link so cannot create Fields Objects for custom object ");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc042_2_verifycreatedFieldsForCustomObjectWithMaxChar(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		String[][] userAndPassword = {{superAdminUserName,adminPassword},{crmUser1EmailID,adminPassword}};
		for (String[] userPass : userAndPassword) {
		lp.CRMLogin(userPass[0], userPass[1]);
			ThreadSleep(5000);
		if(click(driver, home.sdgGridSideIcons(SDGGridName.Deals,SDGGridSideIcons.Manage_fields,5), "manage field icon", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "clicked on manage field icon of "+SDGGridName.Deals, YesNo.No);
			List<WebElement> lst = home.sdgGridSelectVisibleFieldsListInManageFieldPopUp();
			
			if(userPass[0]== superAdminUserName) {
				for(int i=0; i<1; i++) {
					if(i==0) {
						if(selectVisibleTextFromDropDown(driver, home.sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp(10), "drop down", "Deal sample Dependent test new field1234-->Institution")) {
							log(LogStatus.PASS, "select Deal sample Dependent test new field1234-->Institution text from visible field", YesNo.No);
							ThreadSleep(2000);
							
							if(selectVisibleTextFromDropDown(driver, home.sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp(10), "drop down", "Bank Name")) {
								log(LogStatus.PASS, "select Bank Name text from visible field", YesNo.No);
								ThreadSleep(2000);
								
								if(home.sdgGridVisibleFieldRemoveInManageFieldPopUp("Bank Name", 10) !=null) {
									log(LogStatus.PASS, "Bank Name is visible in field finder", YesNo.No);
									ThreadSleep(2000);
									
									click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Add, 10), "Add button", action.SCROLLANDBOOLEAN);

									if(click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Save, 10), "Add button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.PASS, "clicked on save button for "+SDGGridName.Deals, YesNo.No);
										ThreadSleep(1000);

										lst = home.sdgGridHeadersLabelNameList(SDGGridName.Deals);
										String headerName="Bank Name";
										if(compareMultipleList(driver, headerName, lst).isEmpty()) {
											log(LogStatus.PASS, SDGGridName.Deals+" SDG Grid Header Name is verified ", YesNo.No);
										}else {
											log(LogStatus.FAIL,SDGGridName.Deals+" SDG Grid Header Name is not verified ", YesNo.Yes);
											sa.assertTrue(false, SDGGridName.Deals+" SDG Grid Header Name is not verified ");
										}
									}else {
										log(LogStatus.PASS, "Not able to click on save button for "+SDGGridName.Deals, YesNo.No);
										sa.assertTrue(false, "Not able to click on save button for "+SDGGridName.Deals);
									}
								}else {
									log(LogStatus.PASS, "Cannot visible Bank Name from field finder", YesNo.No);
									sa.assertTrue(false, "Cannot visible Bank Name from field finder");
								}
							}else {
								log(LogStatus.PASS, "Cannot select Deal sample Dependent test new field1234-->Institution from field finder", YesNo.No);
								sa.assertTrue(false, "Cannot select Deal sample Dependent test new field1234-->Institution from field finder");
							}	
						}else {
							log(LogStatus.PASS, "Cannot select Deal sample Dependent test new field1234-->Institution from field finder", YesNo.No);
							sa.assertTrue(false, "Cannot select Deal sample Dependent test new field1234-->Institution from field finder");
						}	

					}
				}
			}else {
				String headerName="Bank Name";
				lst = home.sdgGridHeadersLabelNameList(SDGGridName.Deals);
				if(compareMultipleList(driver, headerName, lst).isEmpty()) {
				
					log(LogStatus.PASS, SDGGridName.Deals+" SDG Grid Header Name is verified for user", YesNo.No);
				}else {
					log(LogStatus.FAIL,SDGGridName.Deals+" SDG Grid Header Name is not verified for user", YesNo.Yes);
					sa.assertTrue(false, SDGGridName.Deals+" SDG Grid Header Name is not verified for user");
				}
			}
		}else {
			log(LogStatus.PASS, "Not able to click on manage field icon of "+SDGGridName.Deals, YesNo.No);
			sa.assertTrue(false, "Not able to click on manage field icon of "+SDGGridName.Deals);
		}
		lp.CRMlogout();
		closeBrowser();
		config(ExcelUtils.readDataFromPropertyFile("Browser"));
		lp = new LoginPageBusinessLayer(driver);
	}
	sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc043_verifyAddDuplicateFieldAndCheckErrorMsg(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		String[][] userAndPassword = {{superAdminUserName,adminPassword},{crmUser1EmailID,adminPassword}};
		String fieldName ="Deal sample Dependent test new field1234";
		for (String[] userPass : userAndPassword) {
			lp.CRMLogin(userPass[0], userPass[1]);
			ThreadSleep(5000);
			if(click(driver, home.sdgGridSideIcons(SDGGridName.Deals,SDGGridSideIcons.Manage_fields,5), "manage field icon", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.PASS, "clicked on manage field icon of "+SDGGridName.Deals, YesNo.No);
				
				if(selectVisibleTextFromDropDown(driver, home.sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp(10), "drop down", fieldName)) {
					log(LogStatus.PASS, "select "+fieldName+" text from visible field", YesNo.No);
					ThreadSleep(2000);

					click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Add, 10), "Add button", action.SCROLLANDBOOLEAN);
					ThreadSleep(1000);

					if(selectVisibleTextFromDropDown(driver, home.sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp(10), "drop down", fieldName)) {
						log(LogStatus.PASS, "select "+fieldName+" text from visible field", YesNo.No);
						ThreadSleep(2000);

						click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Add, 10), "Add button", action.SCROLLANDBOOLEAN);

						WebElement ele = home.getToastErroMsg(10);
						if(ele!=null) {
							if(ele.getText().trim().equalsIgnoreCase(HomePageErrorMessage.FieldPopUpToastErrorMessage(fieldName))) {
								log(LogStatus.PASS, "Error Message is verified "+HomePageErrorMessage.selectFieldPopUpErrorMessage, YesNo.No);
							}else {
								log(LogStatus.PASS, "Error Message is not verified "+HomePageErrorMessage.selectFieldPopUpErrorMessage, YesNo.Yes);
								sa.assertTrue(false, "Error Message is not verified "+HomePageErrorMessage.selectFieldPopUpErrorMessage);
							}
						}else {
							log(LogStatus.PASS, "error message is not displaying "+SDGGridName.Deals, YesNo.No);
							sa.assertTrue(false, "error message is not displaying "+SDGGridName.Deals);
						}
						click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.close, 10), "close button", action.SCROLLANDBOOLEAN);
					}else {
						log(LogStatus.PASS, "Cannot select again "+fieldName+" from field finder", YesNo.No);
						sa.assertTrue(false, "Cannot select again "+fieldName+" from field finder");
					}	



				}else {
					log(LogStatus.PASS, "Cannot select "+fieldName+" from field finder", YesNo.No);
					sa.assertTrue(false, "Cannot select "+fieldName+" from field finder");
				}	
			}else {
				log(LogStatus.PASS, "Not able to click on manage field icon of "+SDGGridName.Deals, YesNo.No);
				sa.assertTrue(false, "Not able to click on manage field icon of "+SDGGridName.Deals);
			}
			lp.CRMlogout();
			closeBrowser();
			config(ExcelUtils.readDataFromPropertyFile("Browser"));
			lp = new LoginPageBusinessLayer(driver);
		}
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc044_1_renameCreatedStandardObjectAndCheckImpact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot update Fields Objects for custom object ");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot update Fields Objects for custom object ",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot update Fields Objects for custom object ");
			}
			ThreadSleep(3000);
			if(setup.updateCreatedCustomFieldforFormula(environment,mode,object.Deal,ObjectFeatureName.FieldAndRelationShip,"Source Firm","New Source Firm")) {
				log(LogStatus.PASS, "Update Field Object name for created object for : New Source Firm", YesNo.No);
			}else {
				log(LogStatus.PASS, "Source Firm Field Object name is not udated : New Source Firm", YesNo.Yes);
				sa.assertTrue(false, "Source Firm Field Object name is not created for : New Source Firm");
			}
			
			
			ThreadSleep(3000);
			if(setup.updateCreatedCustomFieldforFormula(environment,mode,object.Deal,ObjectFeatureName.FieldAndRelationShip,"Deal sample 1","Deal sample 1 New")) {
				log(LogStatus.PASS, "Update Field Object name for created object for : Deal sample 1 New", YesNo.No);
			}else {
				log(LogStatus.PASS, "Source Firm Field Object name is not udated : Deal sample 1 New", YesNo.Yes);
				sa.assertTrue(false, "Source Firm Field Object name is not created for : Deal sample 1 New");
			}
			
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		}else {
			log(LogStatus.ERROR, "Not able to click on setup link so cannot create Fields Objects for custom object", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link so cannot create Fields Objects for custom object ");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc044_2_verifyRenameObjectInDealSDGGrid(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID,adminPassword);
		ThreadSleep(5000);
		if(click(driver, home.sdgGridSideIcons(SDGGridName.Deals,SDGGridSideIcons.Manage_fields,5), "manage field icon", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "clicked on manage field icon of "+SDGGridName.Deals, YesNo.No);
			List<WebElement> lst = home.sdgGridSelectVisibleFieldsListInManageFieldPopUp();
			for(int i=0; i<1; i++) {
				if(i==0) {
					if(selectVisibleTextFromDropDown(driver, home.sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp(10), "drop down", "Deal sample 1 new")) {
						log(LogStatus.PASS, "select Deal sample 1 new text from visible field", YesNo.No);
						ThreadSleep(2000);
						click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Add, 10), "Add button", action.SCROLLANDBOOLEAN);

						if(click(driver,home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(Buttons.Save, 10), "save button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "clicked on save button for "+SDGGridName.Deals, YesNo.No);
							ThreadSleep(1000);
							
						}else {
							log(LogStatus.PASS, "Not able to click on save button for "+SDGGridName.Deals, YesNo.No);
							sa.assertTrue(false, "Not able to click on save button for "+SDGGridName.Deals);
						}
					}else {
						log(LogStatus.PASS, "Cannot select Deal sample 1 new from field finder", YesNo.No);
						sa.assertTrue(false, "Cannot select Deal sample 1 new from field finder");
					}	
				}
			}
		}else {
			log(LogStatus.PASS, "Not able to click on manage field icon of "+SDGGridName.Deals, YesNo.No);
			sa.assertTrue(false, "Not able to click on manage field icon of "+SDGGridName.Deals);
		}
		
		String headerName="New source firm,Deal sample 1 new";
		List<WebElement> lst = home.sdgGridHeadersLabelNameList(SDGGridName.Deals);
		if(compareMultipleList(driver, headerName, lst).isEmpty()) {
		
			log(LogStatus.PASS, SDGGridName.Deals+" SDG Grid Header Name is verified for user", YesNo.No);
		}else {
			log(LogStatus.FAIL,SDGGridName.Deals+" SDG Grid Header Name is not verified for user", YesNo.Yes);
			sa.assertTrue(false, SDGGridName.Deals+" SDG Grid Header Name is not verified for user");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M1Tc045_1_removeObjectPermissionFromObject(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ContactsPageBusinessLayer con = new ContactsPageBusinessLayer(driver);
		String parentWindow = null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot remove permission of  Field Set Component");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot remove permission of create Field Set Component",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot remove permission of create Field Set Component");
			}
			ThreadSleep(3000);
			if(setup.giveAndRemoveObjectPermissionFromObjectManager(object.Deal,ObjectFeatureName.FieldAndRelationShip,PermissionType.removePermission,"New Source Firm","PE Standard User")) {
				log(LogStatus.PASS,"Remove Permission of New Source Firm from Deal Object", YesNo.No);
				
			}else {
				log(LogStatus.ERROR,"Not able to Remove Permission of New Source Firm from Deal Object", YesNo.Yes);
				sa.assertTrue(false, "Not able to Remove Permission of New Source Firm from Deal Object");
			}
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		}else {
			log(LogStatus.ERROR, "Not able to click on setup link so cannot remove permission on New Source Firm from Deal Object", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link so cannot remove permission on New Source Firm from Deal Object");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc045_2_verifyObjectVisibilityAfterRemoveObjectPermission(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID,adminPassword);
		String headerName="New source firm";
		List<WebElement> lst = home.sdgGridHeadersLabelNameList(SDGGridName.Deals);
		if(!compareMultipleList(driver, headerName, lst).isEmpty()) {
			log(LogStatus.PASS, SDGGridName.Deals+" SDG Grid Header Name is verified for user", YesNo.No);
		}else {
			log(LogStatus.FAIL,SDGGridName.Deals+" SDG Grid Header Name is not verified for user", YesNo.Yes);
			sa.assertTrue(false, SDGGridName.Deals+" SDG Grid Header Name is not verified for user");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M1Tc045_3_giveObjectPermissionFromObject(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ContactsPageBusinessLayer con = new ContactsPageBusinessLayer(driver);
		String parentWindow = null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot remove permission of  Field Set Component");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot remove permission of create Field Set Component",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot remove permission of create Field Set Component");
			}
			ThreadSleep(3000);
			if(setup.giveAndRemoveObjectPermissionFromObjectManager(object.Deal,ObjectFeatureName.FieldAndRelationShip,PermissionType.removePermission,"New Source Firm","PE Standard User")) {
				log(LogStatus.PASS,"Add Permission of New Source Firm from Deal Object", YesNo.No);
				
			}else {
				log(LogStatus.ERROR,"Not able to Add Permission of New Source Firm from Deal Object", YesNo.Yes);
				sa.assertTrue(false, "Not able to Add Permission of New Source Firm from Deal Object");
			}
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		}else {
			log(LogStatus.ERROR, "Not able to click on setup link so cannot add permission on New Source Firm from Deal Object", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link so cannot add permission on New Source Firm from Deal Object");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M8Tc045_4_verifyObjectVisibilityAfterAddingObjectPermission(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID,adminPassword);
		String headerName="New source firm";
		List<WebElement> lst = home.sdgGridHeadersLabelNameList(SDGGridName.Deals);
		if(compareMultipleList(driver, headerName, lst).isEmpty()) {
			log(LogStatus.PASS, SDGGridName.Deals+" SDG Grid Header Name is verified for user", YesNo.No);
		}else {
			log(LogStatus.FAIL,SDGGridName.Deals+" SDG Grid Header Name is not verified for user", YesNo.Yes);
			sa.assertTrue(false, SDGGridName.Deals+" SDG Grid Header Name is not verified for user");
		}
		lp.CRMlogout();
		sa.assertAll();
	}

}
