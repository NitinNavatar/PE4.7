package com.navatar.pageObjects;

import static com.navatar.generic.AppListeners.appLog;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.CommonLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.ReportDashboardFolderType;
import com.navatar.generic.EnumConstants.ReportField;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;
import com.relevantcodes.extentreports.LogStatus;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import static com.navatar.generic.CommonLib.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ResearchPageBusinessLayer extends ResearchPage {

	public ResearchPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public boolean mouseHoverOnNavigationAndGetText() { 
		int navSize = getElementsFromNavigation().size();
		try {
		for(int i=0; i<navSize; i++)
		{
			String getNavText = mouseOverGetTextOperation(driver, getElementsFromNavigation().get(i));
			if(getNavText != null){
				log(LogStatus.PASS, getNavText + " is the text in Nav at position " + i, YesNo.Yes);
				sa.assertTrue(true, getNavText + " is the text in Nav at position " + i);
				return true;
			}else {
				log(LogStatus.ERROR,"No text in present in Nav at position " + i, YesNo.No);
				sa.assertTrue(false, "No text in present in Nav at position " + i);
				return false;
			}
		}
			return true;
	}catch (Exception e) {
		log(LogStatus.ERROR, "Exception: " + e.getMessage(), YesNo.Yes);
		return false;
	}
	}
	
	
	public boolean clickOperationOnRecordForGrid(String headerName, String recordName) { 

		boolean flag = false; 
		
		if (clickUsingJavaScript(driver, clickOnRecordUsingGridName(headerName, 30),
				"Grid Name: " + headerName, action.BOOLEAN)) {
			log(LogStatus.PASS, "Clicked on Grid Name: " + headerName, YesNo.No);
			try {
				String parentWindowId = CommonLib.switchOnWindow(driver);
				if (!parentWindowId.isEmpty()) {
					log(LogStatus.PASS, "New Window Open after click on Grid Link: " + headerName,
							YesNo.No);
					flag = true;
					if (RecordPagesHeader(recordName, 20) != null) {//need to update according to Research
						log(LogStatus.PASS, "----Detail Page is redirecting for Record: "
								+ recordName + "-----", YesNo.No);
						driver.close();
						driver.switchTo().window(parentWindowId);
						flag = true;
					} else {
						log(LogStatus.FAIL, "----Detail Page is not redirecting for Record: "
								+ recordName + "-----", YesNo.Yes);
						sa.assertTrue(false, "----Detail Page is not showing for Record: "
								+ recordName + "-----");
						flag = false;
						driver.close();
						driver.switchTo().window(parentWindowId);

					}

				} else {
					log(LogStatus.FAIL, "No New Window Open after click on Grid Link: " + headerName,
							YesNo.Yes);
					sa.assertTrue(false, "No New Window Open after click on Grid Link: " + headerName);
					flag = false;
				}
			} catch (Exception e) {
				log(LogStatus.FAIL,
						"Not able to switch to window after click on Grid Link, Msg showing: "
								+ e.getMessage(),
						YesNo.Yes);
				sa.assertTrue(false,
						"Not able to switch to window after click on Grid Link, Msg showing: "
								+ e.getMessage());
				flag = false;
			}
		} else {
			log(LogStatus.FAIL, "Not able to Click on Grid Name: " + headerName, YesNo.Yes);
			sa.assertTrue(false, "Not able to Click on Grid Name: " + headerName);
			flag = false;
		}
		return flag;
	}
	
	
	public boolean mouseHoverOnGridAndGetText() { 
		int gridSize = getElementsFromGrid().size();
		try {
		for(int i=0; i<gridSize; i++){
			String getGridText = mouseOverGetTextOperation(driver, getElementsFromGrid().get(i));
			if(getGridText != null){
				log(LogStatus.PASS, getGridText + " is the text in Nav at position " + i, YesNo.Yes);
				sa.assertTrue(true, getGridText + " is the text in Nav at position " + i);
				return true;
			}else {
				log(LogStatus.ERROR,"No text in present in Nav at position " + i, YesNo.No);
				sa.assertTrue(false, "No text in present in Nav at position " + i);
				return false;
				}
			}
		return true;
		}catch (Exception e) {
			log(LogStatus.ERROR, "Exception: " + e.getMessage(), YesNo.Yes);
			return false;
		}
	}
	
	public boolean VerifyViewMoreOption(String headerName) {
		boolean flag = false;
//		List<String> sideNavCountExceptAllCategories = researchSideNavCountResultsExceptAllCategories()
//				.stream().map(x -> x.getText().trim().replace("New Items", "").replace(":", "")
//						.replaceAll("[\\t\\n\\r]+", "").trim()).collect(Collectors.toList());
		int count=Integer.valueOf(headerName.split("\\(")[1].split("\\)")[0].trim());
		
		
			//log(LogStatus.INFO,"Count is greater than 5, so we can see view more option for " + newCountFromSideNav,YesNo.No);
			if(count > 5) {
				log(LogStatus.INFO,"Count is greater than 5, so we can see view more option for " + headerName ,YesNo.No);
				if(clickUsingJavaScript(driver, getViewMoreOptionUsingHeaderName(headerName, 20), "View More"))
				{
					log(LogStatus.INFO,"Count is greater than 5 " + headerName ,YesNo.No);
					int TotalNumberOfRecords = getAllRecordsUsingHeaderName(headerName,20).size();
					log(LogStatus.INFO,"Total Number Of Records are : " + TotalNumberOfRecords,YesNo.No);
					if(TotalNumberOfRecords == count) {
						log(LogStatus.INFO,"Total number of records are matched with given count",YesNo.No);
						clickUsingJavaScript(driver, getAllCategoriesLink(10), "All Categories");
						flag = true;
					}
					flag = true;
				}
			}else {
				log(LogStatus.ERROR,"Count is less than 5, so we can't check view more option for " + headerName ,YesNo.No);
				flag = false;
//				sa.assertTrue(false,"In " + gridWiseHeading +" Search Keyword" + searchValue + "does not contain in " + gridText);
			}
		
		return flag;
	}
	
	public void VerifyNameAndCountFromNavAndGrid(){
		List<String> sideNavCountExceptAllCategories = researchSideNavCountResultsExceptAllCategories()
				.stream().map(x -> x.getText().trim().replace("New Items", "").replace(":", "")
						.replaceAll("[\\t\\n\\r]+", "").trim())
				.collect(Collectors.toList());
		for(String navWiseHeading : sideNavCountExceptAllCategories) {
			log(LogStatus.INFO,"Values for Navigation Menu is : " + researchSideNavCountResultsExceptAllCategories().stream().map(x -> x.getText()),YesNo.No);
			log(LogStatus.INFO,"Name of Field in the Navigation Menu is : " + navWiseHeading,YesNo.No);
		}
		List<String> researchResultsGridCounts = researchResultsGridCounts().stream()
				.map(x -> x.getText().trim()).collect(Collectors.toList());
		for (String headerName : researchResultsGridCounts) {
		String gridWiseHeading = headerName.substring(0,headerName.indexOf(" "));
		log(LogStatus.INFO,"Name of Field in the grid is : " + gridWiseHeading,YesNo.No);
		
		String xpath = "//span[@title='"+ gridWiseHeading +"']/ancestor::div[@class='slds-grid slds-wrap']/following-sibling::div//tbody/tr";
		List<WebElement> ele = FindElements(driver, xpath,"");
		for(WebElement gridData : ele)
		{
			String gridText = gridData.getText();
			log(LogStatus.INFO,"In " + gridWiseHeading +" Text in Grid is : " + gridText,YesNo.No);
		}
	
		}
	}
	
	
	public ArrayList<String> VerifyNameAndCountForResearchLeftPanel(String variableName,action action, int timeout ) {
		WebElement ele=null;
		ArrayList<String> list = new ArrayList<>();
		HashMap<String, ArrayList<String>> headersAndValues = ExcelUtils.dataRead(AcuityDataSheetFilePath,"SearchData",excelLabel.Variable_Name, variableName);
		
		HashMap<String,String> headerAndValue =   new HashMap<String,String>();
		
		for(int i = 0; i < headersAndValues.get("headers").size(); i++)
		{
			headerAndValue.put(headersAndValues.get("headers").get(i), headersAndValues.get("value").get(i));
		}
				
		for(String header: headersAndValues.get("headers")) {
			
			if(!headerAndValue.get(header).equalsIgnoreCase("NA")) {
				
				log(LogStatus.INFO, "Header :"+header +" is enable in excel so  going to verify in research page", YesNo.No);

				ele=researchFindingsLeftPanelHeadingName(header, action, timeout);
				
				if(ele!=null) {
					log(LogStatus.INFO, "Header :"+header +" is  visible in left panel of research page", YesNo.No);
					String headerText = ele.getText().split(":")[0].trim();
					if(headerText.equalsIgnoreCase(header)) {
						log(LogStatus.INFO, "Header :"+header +" is matched with excel label:"+headerText+" in left panel of research page", YesNo.No);
						
						if(Integer.valueOf(headerAndValue.get(header))!=0) {
							
							log(LogStatus.INFO, "Header Count  :" + headerAndValue.get(header)
							+ " is enable for header :" + header + " so going for verify in left panel of research page",
							YesNo.No);
							ele = researchFindingsLeftPanelHeadingCount(header, action, timeout);
							if (ele != null) {

								log(LogStatus.INFO, "Header Count  :" + headerAndValue.get(header)
										+ " is visible for header :" + header + "in left panel of research page",
										YesNo.No);
								String headerCount = ele.getText().trim().replace("New Items", "").replace(":", "")
										.replaceAll("[\\t\\n\\r]+", "").trim();
								if (headerCount.equalsIgnoreCase(headerAndValue.get(header))) {
									log(LogStatus.INFO,
											"Header Count  :" + headerAndValue.get(header)
													+ " is matched with excel label count:" + headerCount
													+ " for header :" + header + "in left panel of research page",
											YesNo.No);
								} else {
									log(LogStatus.SKIP,
											"Header Count  :" + headerAndValue.get(header)
													+ " is not matched with excel label count:" + headerCount
													+ " for header :" + header + "in left panel of research page",
											YesNo.No);
						
									list.add("Header Count  :" + headerAndValue.get(header)
									+ " is not matched with excel label count:" + headerCount
									+ " for header :" + header + "in left panel of research page");
								}

							} else {
								log(LogStatus.SKIP, "Header Count  :" + headerAndValue.get(header)
										+ " is not visible for header :" + header + "in left panel of research page",
										YesNo.No);
								list.add("Header Count  :" + headerAndValue.get(header)
								+ " is not visible for header :" + header + "in left panel of research page");


							}
						} else {
							
							log(LogStatus.SKIP, "Header Count  :" + headerAndValue.get(header)
							+ " is disbale for header :" + header + "in left panel of research page",
							YesNo.No);
						}
					}else {
						log(LogStatus.SKIP,  "Header :"+header +" is not matched with excel label:"+headerText+" in left panel of research page", YesNo.No);
						list.add("Header :"+header +" is not matched with excel label:"+headerText+" in left panel of research page");
					}
				}else {
					log(LogStatus.SKIP, "Header :"+header +" is not visible in left panel of research page", YesNo.No);
					list.add("Header :"+header +" is not visible in left panel of research page");

				}
				
			}else {
				log(LogStatus.SKIP, "Header :"+header +" is disable in excel so cannot going to verify in research page", YesNo.No);			}
			
			
		}
		
		return list;
	}
	
}
