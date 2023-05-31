package com.navatar.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.navatar.generic.CommonLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.NavigationMenuItems;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.relevantcodes.extentreports.LogStatus;
import static com.navatar.generic.CommonLib.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ResearchPageBusinessLayer extends ResearchPage {

	String navigationMenuName=NavigationMenuItems.Research.toString().replace("_", " ");
	
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
		
		if (clickUsingJavaScript(driver, clickOnRecordUsingGridName(headerName, 10),
				"Grid Name: " + headerName, action.BOOLEAN)) {
			log(LogStatus.PASS, "Clicked on Grid Name: " + headerName, YesNo.No);
			try {
				String parentWindowId = CommonLib.switchOnWindow(driver);
				if (!parentWindowId.isEmpty()) {
					log(LogStatus.PASS, "New Window Open after click on Grid Link: " + headerName,
							YesNo.No);
					flag = true;
					refresh(driver);
					if (RecordPagesHeader(recordName, 10) != null) {//need to update according to Research
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
				ThreadSleep(2000);
				if(clickUsingJavaScript(driver, getViewMoreOptionUsingHeaderName(headerName, 20), "View More"))
				{
					log(LogStatus.INFO,"Count is greater than 5 " + headerName ,YesNo.No);
					ThreadSleep(2000);
					int TotalNumberOfRecords = getAllRecordsUsingHeaderName(headerName,20).size();
					log(LogStatus.INFO,"Total Number Of Records are : " + TotalNumberOfRecords,YesNo.No);
					ThreadSleep(2000);
					if(TotalNumberOfRecords == count) {
						log(LogStatus.INFO,"Total number of records are matched with given count",YesNo.No);
						refresh(driver);
						ThreadSleep(2000);
						refresh(driver);
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
		HashMap<String, ArrayList<String>> headersAndValues;
		
		if(variableName.contains("ACR_")) {
			headersAndValues = ExcelUtils.dataRead(ResearchDataSheetFilePath,"SearchData",excelLabel.Variable_Name, variableName);
		}
		else if(variableName.contains("ARURT_")) {
			headersAndValues = ExcelUtils.dataRead(ResearchDataSheetFilePath,"UpdatedRecordType",excelLabel.Variable_Name, variableName);
		}
		else if(variableName.contains("ARCR_")) {
			headersAndValues = ExcelUtils.dataReadForCurrentRecord(ResearchDataSheetFilePath,"CurrentRecord",excelLabel.Variable_Name, variableName);
		}
		else {
			headersAndValues = ExcelUtils.dataRead(ResearchDataSheetFilePath,"UpdatedData",excelLabel.Variable_Name, variableName);
		}
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

	public ArrayList<String> verifyFieldonResearchPage(String environment, String mode, String[][] Data) {

		String tableData = null;
		ArrayList<String> verifyData = new ArrayList<String>();
		int row = Data.length;
		ArrayList<String> DataFromExcel = new ArrayList<String>();

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < Data[0].length; j++) {
				DataFromExcel.add(Data[i][j]);
			}
		}

		String xpath = "//div[contains(@class,'active')]//a";
		List<WebElement> ele = CommonLib.FindElements(driver, xpath, "Data");
		ArrayList<String> DataFromOrg = new ArrayList<String>();
		for (int i = 0; i < ele.size(); i++) {
			try {
				tableData = CommonLib.getText(driver, ele.get(i), ele.get(i) + " from Org", action.SCROLLANDBOOLEAN);
				ThreadSleep(2000);
				click(driver, getFieldName(tableData, 10), xpath, action.BOOLEAN);
				ThreadSleep(2000);
				if (tableData != "") {
					DataFromOrg.add(tableData);
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				log(LogStatus.ERROR, "Could not get the " + ele.get(i) + " Data from File", YesNo.Yes);
				verifyData.add("Could not get the " + ele.get(i) + " from File");

			}
		}

		for (int i = 0; i < DataFromExcel.size(); i++) {
			if ((DataFromOrg.get(i).equals(DataFromExcel.get(i)) || (DataFromOrg.get(i) == "" && DataFromExcel.get(i) == null))) {
				log(LogStatus.INFO, "Data from Excel : " + DataFromExcel.get(i)
						+ " has been matched with the Org Data : " + DataFromOrg.get(i), YesNo.No);
			} else {
				log(LogStatus.ERROR, "Data from Excel : " + DataFromExcel.get(i)
						+ " is not matched with the Org Data : " + DataFromOrg.get(i), YesNo.Yes);
				verifyData.add(DataFromExcel.get(i));

			}
		}

		return verifyData;
	}


	public boolean openResearchForCurrentRecord(String projectName, String objectName, String recordName, int timeout) { 
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		String name = recordName.replace("_", " ");
		String ObjectName = objectName.replace("_", " ");
		Actions act = new Actions(driver);
		WebElement wait;
		boolean flag = false;
		
		  if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, timeout)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				act.moveToElement(getResearchButton(timeout)).perform();
//				ThreadSleep(2000);
				try {
					wait = new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfElementLocated(
							By.xpath("//div[contains(@class,'slds-combobox_object-switcher')]//button[contains(@class,'slds-input_faux')]")));
					log(LogStatus.INFO, "Progress Dropdown in Research Popup has been found", YesNo.No);
				} catch (Exception ex) {
					ex.printStackTrace();
					log(LogStatus.ERROR, "Could not get the Progress Dropdown in Research Popup", YesNo.No);
					return false;
				}
//				ThreadSleep(3000);
				try {
					act.moveToElement(getProgressDropdown(timeout)).perform();
					log(LogStatus.INFO, "Element has been moved to Progress Dropdown in Research Popup", YesNo.No);
				} catch (Exception ex) {
					ex.printStackTrace();
					log(LogStatus.ERROR, "not able to move to Progress Dropdown in Research Popup", YesNo.No);
				}
//				ThreadSleep(3000);
				if(clickUsingJavaScript(driver, getProgressDropdown(timeout),"Research Progess Dropdown", action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to Click on " + navigationMenuName, YesNo.No);
					if(clickUsingJavaScript(driver, getClickOnProgress(ObjectName,timeout),"", action.BOOLEAN)) {
						log(LogStatus.INFO, "Able to Select " + navigationMenuName, YesNo.No);
					if(sendKeys(driver, getTextAreaResearch(timeout),name, "Research Input Field", action.BOOLEAN)){
						log(LogStatus.INFO, "Send value : " + name + " to research for search", YesNo.No);
						clickUsingJavaScript(driver, getResearchButton(timeout),"Research Button", action.BOOLEAN);
//						ThreadSleep(5000);
						clickUsingJavaScript(driver, getResearchMinimize(timeout),"Research Minimum Button", action.BOOLEAN);
						flag = true;
//						ThreadSleep(2000);
					} else {
					    log(LogStatus.ERROR, "Not able to send " + name, YesNo.Yes);
					} } else {
						log(LogStatus.ERROR, "Not able to select " + ObjectName, YesNo.Yes);
				  } } else {
						log(LogStatus.ERROR, "Not able to click on Research Progress Dropdown", YesNo.Yes);
				} } else {
			    log(LogStatus.ERROR, "Not able to click on Research", YesNo.Yes);
			}
	return flag;
	}
	
}
