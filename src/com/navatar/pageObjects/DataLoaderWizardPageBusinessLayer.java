/**
 * 
 */
package com.navatar.pageObjects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.BaseLib;

import static com.navatar.generic.AppListeners.*;
import static com.navatar.generic.CommonLib.*;
import java.util.List;

/**
 * @author ANKIT JAISWAL
 *
 */
public class DataLoaderWizardPageBusinessLayer extends DataLoaderWizardPage {

	/**
	 * @param driver
	 */
	public DataLoaderWizardPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * @author Ankit Jaiswal
	 * @param objectName
	 * @param objectType
	 * @param dataImportFilePath
	 * @param dataImportType
	 * @param numberOfRecordImportCount
	 * @return true if succesfully import data through Data wizard
	 */
	public boolean dataImportWizard(ObjectName objectName,ObjectType objectType,String dataImportFilePath,DataImportType dataImportType, String numberOfRecordImportCount) {
		boolean flag = false;
		BaseLib.PublicFlag = false;
		if(click(driver, getUserMenuTab(30), "user menu tab", action.SCROLLANDBOOLEAN)) {
			appLog.info("clicked on user menu tab ");
			ThreadSleep(2000);
			if(click(driver, getSetUpLink(30), "setupLink", action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on setup link");
				ThreadSleep(2000);
				if(searchStandardOrCustomObject(Environment.Testing.toString(), Mode.Classic.toString(), SearchItemName.Data_Import_Wizard)) {
					appLog.info("searched item  : "+SearchItemName.Data_Import_Wizard.toString());
					if(clickOnObjectFeature(Environment.Testing.toString(), Mode.Classic.toString(), SearchItemcategory.Import, SearchItemName.Data_Import_Wizard)) {
						appLog.info("clicked on Object Name : "+SearchItemName.Data_Import_Wizard.toString());
						ThreadSleep(5000);
						if(clickUsingJavaScript(driver, getLunchWizardButton(30), "lunch wizard button", action.SCROLLANDBOOLEAN)) {
							appLog.info("Clicked on Lunch Wizard button");
							ThreadSleep(10000);
							if(objectType.toString().equalsIgnoreCase(ObjectType.Custom.toString())) {
								if(clickUsingJavaScript(driver, getCustomObjectHeaderTab(30), "custom object header", action.SCROLLANDBOOLEAN)) {
									appLog.info("clicked on Custom Object Header");
								}else {
									appLog.error("Not able to click on custom header object so cannot upload data in object : "+objectName.toString());
									QuitDataImportWizard();
									BaseLib.PublicFlag = true;
									return false;
								}
							}
							if(clickUsingJavaScript(driver, getStandardOrCustomObjectNameLink(objectName.toString(),30), "Object Name text link", action.BOOLEAN)) {
								appLog.info("clicked on Object Name Link : "+objectName.toString());
								ThreadSleep(3000);
								if(clickUsingJavaScript(driver, getDataImportChoiceActivity(dataImportType.toString(), 30), "add new records link ", action.BOOLEAN)) {
									appLog.info("Clicked on Add new records link ");
									ThreadSleep(5000);
									if(objectName.toString().equalsIgnoreCase(ObjectName.InstitutionAndContacts.toString())) {
										if(selectVisibleTextFromDropDown(driver, getAccountMatchByDropDownList(20), "Account Name drop down list","Name & Site")) {
											appLog.info("select Account Name from drop down ");
										}else {
											appLog.error("Not able to select Account Name from drop down list so cannot import data in object : "+objectName.toString());
											QuitDataImportWizard();
											BaseLib.PublicFlag = true;
											return false;
										}
									}else if (objectName.toString().equalsIgnoreCase(ObjectName.Fundraisings.toString())) {
										if(selectVisibleTextFromDropDown(driver, getFundNameLookUpFieldDropDownInFundRaising(20), "fund Name drop down list","Fund Name")) {
											appLog.info("select fund Name from drop down ");
											if(selectVisibleTextFromDropDown(driver, getLegalNameDropDownListInFundRaising(20), "legal Name drop down list","Institution Name")) {
												appLog.info("select legal Name from drop down ");
												
											}else {
												appLog.error("Not able to select legal Name from drop down list so cannot import data in object : "+objectName.toString());
												QuitDataImportWizard();
												BaseLib.PublicFlag = true;
												return false;
											}
										}else {
											appLog.error("Not able to select fund Name from drop down list so cannot import data in object : "+objectName.toString());
											QuitDataImportWizard();
											BaseLib.PublicFlag = true;
											return false;
										}
									}else if (objectName.toString().equalsIgnoreCase(ObjectName.Partnerships.toString())) {
										if(selectVisibleTextFromDropDown(driver, getFundNameLookUpFieldDropDownInFundRaising(20), "fund Name drop down list","Fund Name")) {
											appLog.info("select fund Name from drop down ");
											
										}else {
											appLog.error("Not able to select fund Name from drop down list so cannot import data in object : "+objectName.toString());
											QuitDataImportWizard();
											BaseLib.PublicFlag = true;
											return false;
										}
									}else if (objectName.toString().equalsIgnoreCase(ObjectName.Navigation.toString())) {
										if(selectVisibleTextFromDropDown(driver, getFundNameLookUpFieldDropDownInFundRaising(20), "Navigation Field Name drop down list","Navigation Name")) {
											appLog.info("select Navigation Field Name from drop down ");
											
										}else {
											appLog.error("Not able to select Navigation Field Name from drop down list so cannot import data in object : "+objectName.toString());
											QuitDataImportWizard();
											BaseLib.PublicFlag = true;
											return false;
										}
									}
									if(clickUsingJavaScript(driver, getUploadCSVFileText(30), "upload CSV file link ", action.BOOLEAN)) {
										appLog.info("clicked on upload CSV file link ");
										ThreadSleep(5000);
										if(sendKeys(driver,getChooseCSVFile(30) , System.getProperty("user.dir")+dataImportFilePath, "choose csv file input box", action.BOOLEAN)) {
											appLog.info("Passed Data Import CSV File Path  : "+System.getProperty("user.dir")+dataImportFilePath);
											if(click(driver, getDataImportNextButton(120), "next button", action.SCROLLANDBOOLEAN)) {
												appLog.info("Clicked on Next button.");
												ThreadSleep(3000);
												List<WebElement> mappedObjectList=getMappedSalesforceObjectList();
												List<WebElement> csvHeaderList=getCSVHeaderList();
												if(!mappedObjectList.isEmpty() && !csvHeaderList.isEmpty()) {
													for(int i=0; i<mappedObjectList.size(); i++) {
														String mappedObjectValue=mappedObjectList.get(i).getText().trim();
														String csvHeaderValue=csvHeaderList.get(i).getText().trim();
														if(mappedObjectValue.equalsIgnoreCase("Unmapped")) {
															appLog.error("Import Data Header is not Mapped with salesforce object please check import data CSV header Name :"+csvHeaderValue);
															BaseLib.PublicFlag = true;
															return false;
														}else {
															appLog.info("header is mapped with salesforce object : "+csvHeaderValue);
														}
													}
												}else {
													appLog.error("Not able to get Salesforce mapped object and csv header list so cannot import data in object : "+objectName.toString());
													QuitDataImportWizard();
													BaseLib.PublicFlag = true;
													return false;
												}
												if(click(driver, getDataImportNextButton(120), "Next button", action.SCROLLANDBOOLEAN)) {
													appLog.info("clicked on edit mapping page Next button");
													ThreadSleep(3000);
													if(getUnmappedFieldText(120)!=null) {
														appLog.info("All fields are mapped Successfully .");
														if(click(driver, getStartImportButton(120), "start import button", action.SCROLLANDBOOLEAN)) {
															appLog.info("clicked on satart import button");
															ThreadSleep(2000);
															if(click(driver, getDataImportOKBtn(120), "Ok button", action.SCROLLANDBOOLEAN)) {
																appLog.info("clicked on Congratulations PopUp Ok button");
																ThreadSleep(5000);
																for(int i=0; i<50; i++) {
																	if(getJobStatusClosedText(2)!=null) {
																		appLog.info("Job status is closed");
																		if(getRecordsProcessedCount(2)!=null){
																			String Datacount =getRecordsProcessedCount(2).getText().trim();
																			appLog.info("Datacount>>>>>>>>>>>>> "+Datacount);
																			if(!Datacount.equalsIgnoreCase("0")) {
																				String failedDataCount=getRecordsFailedCount(2).getText().trim();
																				appLog.info("failedDataCount>>>>>>>>>>>>> "+failedDataCount);
																				if(Datacount.equalsIgnoreCase(numberOfRecordImportCount) && failedDataCount.equalsIgnoreCase("0")) {
																					appLog.info("All Data is Imported Successfully in object : "+objectName.toString());
																					flag = true;
																				}else {
																					appLog.error("All data is not imported in object : "+objectName.toString()+". Failed Data import records count is : "+failedDataCount);
																				}
																				break;
																			}
																		}
																		if(click(driver, getReloadButton(2), "reload button", action.SCROLLANDBOOLEAN)) {
																			appLog.info("clicked on reload button");
																		}else {
																			if(i==49) {
																				appLog.error("Data is not imported in object : "+objectName.toString());
																			}
																		}
																	}else {
																		if(click(driver, getReloadButton(10), "reload button", action.SCROLLANDBOOLEAN)) {
																			appLog.info("clicked on reload button");
																		}else {
																			if(i==49) {
																				appLog.error("Data is not imported in object : "+objectName.toString());
																			}
																		}
																	}
																}
															}else {
																appLog.error("Not able to click on Congratulations PopUp Ok button so cannot import data in object : "+objectName.toString());
																QuitDataImportWizard();
															}
														}else {
															appLog.error("Not able to click on start import button so cannot import data in object : "+objectName.toString());
															QuitDataImportWizard();
														}
													}else {
														appLog.error("All Fields are not mapped so cannot import data in object : "+objectName.toString());
														QuitDataImportWizard();
													}
												}else {
													appLog.error("Not able to click on edit mapping page next button so cannot import data in object : "+objectName.toString());
													QuitDataImportWizard();
												}
											}else {
												appLog.error("Not able to click on Next button so cannot import data in object : "+objectName.toString());
												QuitDataImportWizard();
											}
										}else {
											appLog.error("CSV File Path : "+System.getProperty("user.dir")+dataImportFilePath);
											appLog.error("Not able to pass data import csv file path so cannot import data in object :  "+objectName.toString());
											QuitDataImportWizard();
										}
									}else {
										appLog.error("Not able to click on upload CSV file link so cannot import data in object : "+objectName.toString());
										QuitDataImportWizard();
									}
								}else {
									appLog.error("Not able to click on add new records link so cannot  import data in object : "+objectName.toString());
									QuitDataImportWizard();
								}
							}else {
								appLog.error("Not able to click on object name : "+objectName.toString()+" so cannot import data in object : "+objectName.toString());
								QuitDataImportWizard();
							}
						}else {
							appLog.error("Not able to click on lunch wizard button so cannot import data in object : "+objectName.toString());
						}
					}else {
						appLog.error("Not able to click on "+SearchItemName.Data_Import_Wizard.toString()+" so cannot import data in object : "+objectName.toString());
					}
				}else {
					appLog.error("Not able to search "+SearchItemName.Data_Import_Wizard.toString()+" so cannot import data in object : "+objectName);
				}
			}else {
				appLog.error("Not able to click on setup link so cannot upload data in object : "+objectName.toString());
			}
		}else {
			appLog.error("Not able to click on user menu tab so cannot upload data in object :  "+objectName.toString());
		}
		BaseLib.PublicFlag = true;
		return flag;
	}
	
	
	
	/**
	 * @author Ankit Jaiswal
	 * @param environment
	 * @param mode
	 * @param searchitemName
	 * @return true if successfully search object on Setup
	 */
	public boolean searchStandardOrCustomObject(String environment, String mode, SearchItemName searchitemName) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())) {
			if(sendKeys(driver,getQucikSearchInSetupPage(environment, mode, 30),searchitemName.toString().replace("_", " "),"quick search text box in setup page", action.SCROLLANDBOOLEAN)) {
				appLog.info("passed value in serach text box: "+searchitemName);
				return true;
			}else {
				appLog.error("Not able to search object in classic : "+searchitemName);
			}
		}else {
			if (click(driver, getObjectManager_Lighting(30), "object manager tab", action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on object manager tab");
				if(sendKeys(driver, getQuickSearchInObjectManager_Lighting(30), searchitemName.toString().replace("_", " "), "quick search text box in lighting", action.SCROLLANDBOOLEAN)) {
					appLog.info("passed value in quick search text box: "+ searchitemName);
					return true;
				}else {
					appLog.error("Not able to search object in lighting : "+searchitemName);
				}
			} else {
				appLog.error("Not able to click on object manager tab so cannot search object: "+searchitemName);
			}
		}
		return false;
	}
	
	
	
	/**
	 * @author Ankit Jaiswal
	 * @param environment
	 * @param mode
	 * @param object
	 * @param objectFeatureName
	 * @return true if able to click on Object on setup page
	 */
	public boolean clickOnObjectFeature(String environment, String mode,SearchItemcategory object,SearchItemName objectFeatureName ) {
		WebElement ele=null;
		if(mode.equalsIgnoreCase(Mode.Classic.toString())) {
			ele=isDisplayed(driver, FindElement(driver, "//a[text()='"+object+"']/../div/div/a[text()='"+objectFeatureName.toString().replace("_", " ")+"']", "", action.BOOLEAN,20), "visibility",20,"page layout link");
			if(ele!=null) {
				if(click(driver, ele, objectFeatureName+" link", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on "+object+" object feature : "+objectFeatureName);
					return true;
				}else {
					appLog.error("Not able to click on "+object+" object feature: "+objectFeatureName);
				}
			}else {
				appLog.error(object+" object "+objectFeatureName+" feature is not visible so cannot click on it");
			}
		}else {
			ele=isDisplayed(driver, FindElement(driver, "//table[@data-aura-class='uiVirtualDataGrid--default uiVirtualDataGrid']//a[contains(text(),'"+object+"')]", "", action.BOOLEAN,20), "visibility",20,"page layout link");
			if(ele!=null) {
				if(click(driver, ele, object+" object link", action.SCROLLANDBOOLEAN)) {
					appLog.info("click on object link : "+object);
					ele=isDisplayed(driver, FindElement(driver, "//a[contains(text(),'"+objectFeatureName.toString().replace("_", " ")+"')]", "", action.BOOLEAN,20), "visibility",20,objectFeatureName+" feature link");
					if(ele!=null) {
						if(click(driver, ele, objectFeatureName+" object feature link", action.SCROLLANDBOOLEAN)) {
							return true;
						}else {
							appLog.error("Not able to click on object "+object+" feature "+objectFeatureName);
						}
					}else {
						appLog.error(object+" object feature "+objectFeatureName+" is not visible so cannot click on it");
					}
				}else {
					appLog.error("Not able to click on object link : "+object+" so cannot click on it's feature: "+objectFeatureName);
				}
			}else {
				appLog.error(object+" object link is not visible so cannot click on it's feature : "+objectFeatureName);
			}
		}
		return false;
	}
	
	
	/**
	 * @author Ankit Jaiswal
	 * @return true if quit data import wizard successfully
	 */
	public boolean QuitDataImportWizard() {
		if(click(driver, getCancelYourImportBtn(30), "cancel your import button", action.BOOLEAN)) {
			appLog.info("clicked on cancel your import button");
			ThreadSleep(1000);
			if(click(driver,getQuiteDataImportBtn(30),"quite data import button", action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on quite button");
				return true;
			}else {
				appLog.error("Not able to click on quite button");
			}
			
		}else {
			appLog.error("Not able to click on cancel your import button");
		}
		return false;
	}

}
