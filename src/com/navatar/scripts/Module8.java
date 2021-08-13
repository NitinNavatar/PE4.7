package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;

import static com.navatar.generic.CommonVariables.*;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;

import static com.navatar.generic.EnumConstants.*;

import com.navatar.pageObjects.EditPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
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
			if(home.SearchDealFilterDataOnHomePage(SDGGridName.Deals,"Deal",M8DealName1, Operator.Equals)) {
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
			if(home.SearchDealFilterDataOnHomePage(SDGGridName.Fundraising,"Fundraising", M8FRName1, Operator.Equals)) {
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
			if(home.SearchDealFilterDataOnHomePage(SDGGridName.My_Call_List,"Name", M8CON1FName+" "+M8CON1LName, Operator.Equals)) {
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
				if(home.SearchDealFilterDataOnHomePage(sdgGridName[i],searchHeaderName[i],DataList[i], operator[i])) {
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
}