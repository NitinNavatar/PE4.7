package com.navatar.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import com.navatar.generic.CommonLib;
import com.navatar.generic.EnumConstants.ContactPagePhotoActions;
import com.navatar.generic.EnumConstants.LookUpIcon;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PopUpName;
import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.object;
import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.navatar.generic.AppListeners.*;
public class SetupPageBusinessLayer extends SetupPage {

	public SetupPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author Akul Bhutani
	 * @param environment
	 * @param mode
	 * @param objectName
	 * @return true/false
	 * @description this method is used to search object on setup page by entering on textbox and click
	 */
	public boolean searchStandardOrCustomObject(String environment, String mode, object objectName) {
		String index="[1]";
		String o=objectName.toString().replace("_", " ");
		if (objectName==object.Global_Actions || objectName==object.Activity_Setting || objectName==object.App_Manager
				|| objectName==object.Lightning_App_Builder || objectName==object.Profiles || objectName==object.Tabs ||  objectName==object.Users||  objectName==object.Sharing_Settings) {
			if (objectName==object.Global_Actions || objectName==object.Tabs ||  objectName==object.Users) {
				index="[2]";	
			}
			ThreadSleep(3000);
			if (sendKeys(driver, getQucikSearchInSetupPage(10), o,o, action.BOOLEAN)) {
				
				ThreadSleep(2000);
				if (click(driver, FindElement(driver, "(//mark[text()='"+o+"'])"+index,objectName.toString(), 
						action.BOOLEAN, 10), objectName.toString(), action.BOOLEAN)) {
					return true;
				}
				else {
					log(LogStatus.ERROR, "could not click on "+objectName,YesNo.Yes);
					
				}
			}
			else {
				log(LogStatus.ERROR, "quick search textbox not visible", YesNo.Yes);
			}
			return false;
		}
		if(mode.equalsIgnoreCase(Mode.Classic.toString())) {
			if(sendKeys(driver,getQucikSearchInSetupPage(30),objectName.toString(),"quick search text box in setup page", action.SCROLLANDBOOLEAN)) {
				appLog.info("passed value in serach text box: "+objectName);
				return true;
			}else {
				appLog.error("Not able to search object in classic : "+objectName);
			}
		}else {
			if (objectName==object.Create) {
				String xpath = "//a[@title='Create Menu']/span[text()='"+objectName.toString()+"']";
				if (click(driver, FindElement(driver, xpath,objectName.toString(), action.BOOLEAN, 10), "create Custom Object", action.BOOLEAN)) {
					appLog.info("clicked on "+objectName);
					ThreadSleep(2000);
					xpath = "//a[@title='Custom Object']//span[text()='Custom Object']";
					if (click(driver, FindElement(driver, xpath,"create Custom Object", action.BOOLEAN, 10), "create Custom Object", action.BOOLEAN)) {
						appLog.info("clicked on custom object");
						return true;
					} else {
						appLog.error("Not able to click on custom object link");
					}
					
				} else {
					appLog.error("Not able to click on create icon");
				}
			}else if (clickUsingJavaScript(driver, getObjectManager_Lighting(30), "object manager tab", action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on object manager tab");
				if (objectName==object.Custom_Object) {
					if(sendKeys(driver, getQuickSearchInObjectManager_Lighting(30), tabCustomObj, "quick search text box in lighting", action.SCROLLANDBOOLEAN)) {
						appLog.info("passed value in quick search text box: "+ tabCustomObj);
						return true;
					}else {
						appLog.error("Not able to search object in lighting : "+tabCustomObj);
					}
				}
				if(sendKeys(driver, getQuickSearchInObjectManager_Lighting(30), objectName.toString(), "quick search text box in lighting", action.SCROLLANDBOOLEAN)) {
					appLog.info("passed value in quick search text box: "+ objectName);
					return true;
				}else {
					appLog.error("Not able to search object in lighting : "+objectName);
				}
			} else {
				appLog.error("Not able to click on object manager tab so cannot search object: "+objectName);
			}
	}
		return false;
}

	/**
	 * @author Akul Bhutani
	 * @param environment
	 * @param mode
	 * @param object
	 * @param objectfeatureName
	 * @return true/false
	 * @description this method is used to click on object name on setup page
	 */
	public boolean clickOnObjectFeature(String environment, String mode,object object,ObjectFeatureName objectFeatureName ) {
		WebElement ele=null;
		if (object==object.Global_Actions) {
			return true;
		}
	
		if(mode.equalsIgnoreCase(Mode.Classic.toString())) {
			ele=isDisplayed(driver, FindElement(driver, "//a[text()='"+object+"']/../div/div/a[text()='"+objectFeatureName+"']", "", action.BOOLEAN,20), "visibility",20,"page layout link");
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
			ele=isDisplayed(driver, FindElement(driver, "//table[@data-aura-class='uiVirtualDataGrid--default uiVirtualDataGrid']//a[text()='"+object+"']", "", action.BOOLEAN,20), "visibility",20,"page layout link");
			if(ele!=null) {
				if(click(driver, ele, object+" object link", action.SCROLLANDBOOLEAN)) {
					appLog.info("click on object link : "+object);
					ele=isDisplayed(driver, FindElement(driver, "//a[contains(text(),'"+objectFeatureName+"')]", "", action.BOOLEAN,20), "visibility",20,objectFeatureName+" feature link");
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
				ele=isDisplayed(driver, FindElement(driver, "//a[contains(text(),'"+objectFeatureName+"')]", "", action.BOOLEAN,20), "visibility",20,objectFeatureName+" feature link");
				if(ele!=null) {
					if(click(driver, ele, objectFeatureName+" object feature link", action.SCROLLANDBOOLEAN)) {
						return true;
					}else {
						appLog.error("Not able to click on object "+object+" feature "+objectFeatureName);
					}
				}else {
					appLog.error(object+" object feature "+objectFeatureName+" is not visible so cannot click on it");
				}
			}
		}
		return false;
		
	}

	/**
	 * @author Azhar Alam
	 * @param environment
	 * @param mode
	 * @param object
	 * @param objectFeatureName
	 * @param layoutName
	 * @param sourceANDDestination
	 * @return List<String>
	 * @description this method is used to drag and drop fields on page layout page
	 */
	public List<String> DragNDrop(String environment, String mode, object obj, ObjectFeatureName objectFeatureName,List<String> layoutName,HashMap<String, String> sourceANDDestination) {
		WebElement ele= null;
		List<String> result = new ArrayList<String>();
		boolean flag = false;
		if(searchStandardOrCustomObject(environment, mode, obj)) {
			if(clickOnObjectFeature(environment, mode, obj, objectFeatureName)) {
				for(int i=0; i<layoutName.size(); i++) {
					if (obj==object.Global_Actions) {
						switchToFrame(driver, 10, getEditPageLayoutFrame_Lighting(20));
						ele=isDisplayed(driver, FindElement(driver, "//a[text()='"+layoutName.get(i)+"']/../preceding-sibling::td//a[contains(@title,'Layout')]", "", action.BOOLEAN,20), "visibility",20,obj+" page layout link");
					}
					else {
						if(mode.equalsIgnoreCase(Mode.Classic.toString())) {
							ele=isDisplayed(driver, FindElement(driver, "//div[@id='LayoutList_body']//tr/th[text()='"+layoutName.get(i)+"']/../td/a[contains(@title,'Edit')]", "", action.BOOLEAN,20), "visibility",20,layoutName.get(i)+" page layout link");
						}else {
							ele=isDisplayed(driver, FindElement(driver, "//span[contains(text(),'"+layoutName.get(i)+"')]", "", action.BOOLEAN,20), "visibility",20,layoutName.get(i)+" page layout link");
						}
					}
					if (ele != null) {
						if (click(driver, ele, layoutName.get(i) + " layout name edit icon", action.BOOLEAN)) {
							appLog.info("click on pagelayout " + layoutName.get(i) + " Edit Icon");
							ThreadSleep(20000);
							if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
								switchToFrame(driver, 20, getEditPageLayoutFrame_Lighting(20));
							}
							
							Set<String> Sources = sourceANDDestination.keySet();
							Iterator<String> itr = Sources.iterator();
							while (itr.hasNext()) {
								String src = itr.next();
								String trgt=sourceANDDestination.get(src);
								if (PageLabel.Is_Touchpoint.toString().equalsIgnoreCase(src)) {
									
								}else {
									src=src.replace("_", " ");
								}
								
								if (PageLabel.Is_Touchpoint.toString().equalsIgnoreCase(trgt)) {
									
								}else {
									trgt=trgt.replace("_", " ");
								}

//								src=src.replace("_", " ");
//								trgt=trgt.replace("_", " ");
								
								WebElement targetElement = null;
								if(src.split("<break>")[0].contains("Related List")){
									if(click(driver, FindElement(driver, "//div[text()='Related Lists']", "", action.SCROLLANDBOOLEAN, 30), "", action.SCROLLANDBOOLEAN)){
										if(trgt.split("<break>")[0].equalsIgnoreCase("Above")){
											trgt = trgt.split("<break>")[trgt.split("<break>").length-1];
											targetElement = FindElement(driver, "//h3[text()='"+trgt+"']/../../../../../../../../preceding-sibling::div[1]", "", action.BOOLEAN,20);
										} else {
											trgt = trgt.split("<break>")[trgt.split("<break>").length-1];
											targetElement = FindElement(driver, "//h3[text()='"+trgt+"']/../../../../../../../../following-sibling::div[1]", "", action.BOOLEAN,20);
										}
										src = src.split("<break>")[src.split("<break>").length-1];
									} else {
										appLog.error(src+" is not visible so cannot dragNdrop "+src);
										result.add(src+" is not visible so cannot dragNdrop "+src);
									}
									flag = true;
								} else {
									sendKeys(driver, getquickFindSearch(10), src, src, action.BOOLEAN);
									targetElement = FindElement(driver, "//span[@class='labelText'][text()='"+trgt+"']", "", action.BOOLEAN,20);
								}
								ele = isDisplayed(driver, FindElement(driver, " //span[text()='"+src+"']", "", action.BOOLEAN,20), "visibility",20,src+" field");
								if(ele!=null) {
									WebElement ele1 = isDisplayed(driver, targetElement, "visibility",20,trgt+" field");
									
									ThreadSleep(5000);
									if(ele1!=null) {
										if(dragNDropField(driver, ele, ele1)) {
											ThreadSleep(5000);
											appLog.info("Successfully dragNDrop "+src+" at "+trgt+" location");
											if (FindElement(driver, "//span[@class='labelText'][text()='"+src+"']", "", action.BOOLEAN,20)!=null) {
												appLog.info("successfully verified drag and drop of "+src);
											}
											else {
												appLog.error("Not able to dragNDrop "+src+" at "+trgt+" location");
												result.add("Not able to dragNDrop "+src+" at "+trgt+" location");
											}
											appLog.info("Successfully dragNDrop "+src+" at "+trgt+" location");
										}else {
											appLog.error("Not able to dragNDrop "+src+" at "+trgt+" location");
											result.add("Not able to dragNDrop "+src+" at "+trgt+" location");
										}
									}else {
										appLog.error(trgt+" location is not visible so cannot dragNDrop "+src+" at location "+trgt);
										result.add(trgt+" location is not visible so cannot dragNDrop "+src+" at location "+trgt);
									}
								}else {
									appLog.error(src+" is not visible so cannot dragNdrop "+src);
									result.add(src+" is not visible so cannot dragNdrop "+src);
								}
								
							}
							if(click(driver, getPageLayoutSaveBtn(obj,30), "page layouts save button", action.SCROLLANDBOOLEAN)) {
								appLog.info("clicked on save button");
								if(flag && obj!=object.Global_Actions){
									click(driver, FindElement(driver, "//button[text()='Yes']", "Yes Button", action.BOOLEAN, 30), "", action.SCROLLANDBOOLEAN);
								}
							}else {
								appLog.error("Not able to click on Save button cannot save pagelayout dragged object or section");
								result.add("Not able to click on Save button cannot save pagelayout dragged object or section");
							}
						} else {
							appLog.error("Not able to click on " + layoutName.get(i)+ "layout edit icon so cannot dargNdrop.");
							result.add("Not able to click on " + layoutName.get(i)+ "layout edit icon so cannot dargNdrop.");
						}
					} else {
						appLog.error(layoutName.get(i) + " Layout name is not visible so cannot click on edit icon");
						result.add(layoutName.get(i) + " Layout name is not visible so cannot click on edit icon");
					}
					if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						switchToDefaultContent(driver);
					}
				}
			}else {
				appLog.error("Not able to click on Object feature: "+objectFeatureName+" so cannot dragNdrop source.");
				result.add("Not able to click on Object feature: "+objectFeatureName+" so cannot dragNdrop source.");
			}
		}else {
			appLog.error("Not able to search Object: "+obj+" so cannot dragNdrop source.");
			result.add("Not able to search Object: "+obj+" so cannot dragNdrop source.");
		}
		return result;
		
	}

	
	
	/*******************************************************Activity Association******************************/
	/**
	 * @author Azhar Alam
	 * @param userfirstname
	 * @param userlastname
	 * @param email
	 * @param userLicense
	 * @param userProfile
	 * @return true/false
	 * @description this method is used to create new user in pe or mna
	 */
	public boolean createPEUser(String userfirstname, String userlastname, String email, String userLicense,
			String userProfile) {
			if (click(driver, getExpandUserIcon( 30), "expand User Icon", action.SCROLLANDBOOLEAN)) {
				appLog.info("clicked on user expand icon");
				if (click(driver, getUsersLink(30), "User Link", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on users link");
						switchToFrame(driver, 20, getSetUpPageIframe(20));
					if (click(driver, getNewUserLink(20), "New User Button", action.SCROLLANDBOOLEAN)) {
						appLog.info("clicked on new users button");
							switchToDefaultContent(driver);
							switchToFrame(driver, 20, getSetUpPageIframe(20));
						if (sendKeys(driver, getUserFirstName(60), userfirstname, "User First Name",
								action.SCROLLANDBOOLEAN)) {
							if (sendKeys(driver, getUserLastName(60), userlastname, "User Last Name",
									action.SCROLLANDBOOLEAN)) {
								if (sendKeys(driver, getUserEmailId(60), email, "User Email Id",
										action.SCROLLANDBOOLEAN)) {
									if (selectVisibleTextFromDropDown(driver, getUserUserLicenseDropDownList(60),
											"User License drop down list", userLicense)) {
										appLog.info("select user license from drop downlist: " + userLicense);
										if (selectVisibleTextFromDropDown(driver, getUserProfileDropDownList(60),
												"User profile drop down list", userProfile)) {
											appLog.info("select user profile from drop downlist: " + userProfile);
											if(click(driver, getSalesforceCRMContentUserCheckBox(60), "Salesforce CRM Content User check Box",
													action.SCROLLANDBOOLEAN)){
													if (click(driver, getCreateUserSaveBtn_Lighting(30), "Save Button",
															action.SCROLLANDBOOLEAN)) {
														appLog.info("clicked on save button");
														appLog.info("CRM User is created successfully: " + userfirstname
																+ " " + userlastname);
														return true;
													} else {
														appLog.error(
																"Not able to click on save buttton so cannot create user: "
																		+ userfirstname + " " + userlastname);
													}
												
											}else{
												appLog.info("Not able to click on content user checkbox");
											}
										} else {
											appLog.error("Not able to select profile from drop downlist: "
													+ userProfile + " so cannot create user: " + userfirstname + " "
													+ userlastname);
										}
										
									} else {
										appLog.error("Not able to select user license from drop downlist: "
												+ userLicense + " so cannot create user: " + userfirstname + " "
												+ userlastname);
									}
									
								} else {
									appLog.error("Not able to pass email id in text box: " + email
											+ " so cannot create user: " + userfirstname + " " + userlastname);
								}
								
							} else {
								appLog.error("Not able to pass user last name in text box: " + userlastname
										+ " so cannot create user: " + userfirstname + " " + userlastname);
							}
						} else {
							appLog.error("Not able pass user first name in text box: " + userfirstname
									+ " so cannot create user: " + userfirstname + " " + userlastname);
						}
						
					} else {
						appLog.error("Not able to click on new user button so cannot create user: " + userfirstname
								+ " " + userlastname);
					}
					
				} else {
					appLog.error("Not able to click on users link so cannot create user: " + userfirstname + " "
							+ userlastname);
				}
				
			} else {
				appLog.error("Not able to click on manage user expand icon so cannot create user: " + userfirstname
						+ " " + userlastname);
			}
				switchToDefaultContent(driver);
		return false;
	}
	/**
	 * @author Akul Bhutani
	 * @param firstName
	 * @param lastName
	 * @return true/false
	 * @description this method is used to add PE package to user name in arguments
	 */
	public boolean installedPackages(String firstName, String lastName) {
			if(sendKeys(driver,getQucikSearchInSetupPage(30),"Installed package","quick search text box in setup page", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "passed value in serach text box installed package ", YesNo.No);
				
			}else {
				log(LogStatus.INFO, "Not able to search installed package in search text box so cannot click on installed package link in lightning", YesNo.Yes);
				return false;
			}
		if (click(driver, getInstalledPackageLink(30), "Installed Package link", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO,"clicked on installed package link", YesNo.No);
				switchToFrame(driver, 30, getSetUpPageIframe(30));
			if (click(driver, getManageLicensesLink(60), "Manage licenses link", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO,"clicked on manage licenses link", YesNo.No);
					switchToDefaultContent(driver);
					ThreadSleep(10000);
					switchToFrame(driver, 30, getSetUpPageIframe(30));
					ThreadSleep(5000);
				if (click(driver, getAddUsersbutton(60), "Add Users link", action.BOOLEAN)) {
					log(LogStatus.INFO,"clicked on add users button", YesNo.No);
						switchToDefaultContent(driver);
						switchToFrame(driver, 30, getInstalledPackageParentFrame_Lighting(20));
					if (switchToFrame(driver, 30, getInstalledPackageFrame(20))) {
						for (int i = 0; i < 2; i++) {
							if (click(driver, getActiveUserTab(60), "Active Users Tab",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"Clicked on active user tab ", YesNo.No);
							} else {
								if (i == 1) {
									switchToDefaultContent(driver);
									log(LogStatus.INFO,"Not able to click on active user tab", YesNo.Yes);
								}
							}
						}
						WebElement ele = FindElement(driver,
								"//img[@title='Checked']/../..//span[contains(text(),'" + lastName + ", "
										+ firstName + "')]/../..//input",
										"Activate User Check Box", action.BOOLEAN, 20);
						if (ele != null) {
							for (int i = 0; i < 2; i++) {
								if (click(driver, ele, firstName + " " + lastName + " check box",
										action.BOOLEAN)) {
									ThreadSleep(2000);
									WebElement checkBox = FindElement(driver,
											"//img[@title='Checked']/../..//span[contains(text(),'" + lastName
											+ ", " + firstName + "')]/../..//input",
											"Activate User Check Box", action.BOOLEAN, 20);
									if (isSelected(driver, checkBox,
											firstName + " " + lastName + " check box")) {
										log(LogStatus.INFO,"clicked on user check box: " + firstName + " " + lastName, YesNo.No);
											switchToDefaultContent(driver);
											switchToFrame(driver, 30, getInstalledPackageParentFrame_Lighting(20));
										if (click(driver, getActiveUserAddButton(60), "Active User Add Button",
												action.BOOLEAN)) {
											log(LogStatus.INFO,"clicked on add button", YesNo.No);
											log(LogStatus.INFO,"package is installed successfully: " + firstName + " "
													+ lastName, YesNo.No);
											switchToDefaultContent(driver);
											return true;

										} else {
											switchToDefaultContent(driver);
											log(LogStatus.INFO,"Not able to click on add button so cannot install user package: "
													+ firstName + " " + lastName, YesNo.Yes);
										}
									} else {
										if (i == 1) {
											switchToDefaultContent(driver);
											log(LogStatus.INFO,"username checkbox is not selected in istalled package : "
													+ firstName + " " + lastName, YesNo.Yes);
										}
									}
								} else {
									if (i == 1) {
										switchToDefaultContent(driver);
										log(LogStatus.INFO,"Not able to click on user check box: " + firstName + " "
												+ lastName, YesNo.Yes);
									}
								}
							}
						} else {
							switchToDefaultContent(driver);
							log(LogStatus.INFO,"create user " + firstName + " " + lastName
									+ " is not visible so cannot istall user package: " + firstName + " "
									+ lastName, YesNo.Yes);
						}
					} else {
						switchToDefaultContent(driver);
						log(LogStatus.INFO,"installed package frame is not loaded so cannot install user package: "
								+ firstName + " " + lastName, YesNo.Yes);
					}
				} else {
					log(LogStatus.INFO,"Not able to click on add users button so cannot install user package: "
							+ firstName + " " + lastName, YesNo.Yes);
						switchToDefaultContent(driver);
				}
			} else {
				log(LogStatus.INFO,"Not able to click on manage licenses link so cannot install user package: "
						+ firstName + " " + lastName, YesNo.Yes);
					switchToDefaultContent(driver);
			}
		} else {
			log(LogStatus.INFO,"Not able to click on installed packages link so cannot istall user package: "
					+ firstName + " " + lastName, YesNo.Yes);
		}
			switchToDefaultContent(driver);
		return false;
	}
	
	/**
	 * @author Ankit Jaiswal
	 * @param layoutName
	 * @return true if able to click on already created layout
	 */
	public boolean clickOnAlreadyCreatedLayout(String layoutName) {
		String xpath="//td//a//span[text()='"+layoutName+"']";
		WebElement ele=FindElement(driver, xpath, layoutName, action.SCROLLANDBOOLEAN, 10);
		ele=isDisplayed(driver, ele, "visibility", 10, layoutName);
		if (click(driver, ele, layoutName, action.SCROLLANDBOOLEAN)) {
			return true;
		}
		else {
			log(LogStatus.ERROR, "could not click on layout "+layoutName, YesNo.Yes);
		}
		return false;
	}
	
	/**
	 * @author ANKIT JAISWAL
	 * @param environment
	 * @param mode
	 * @param objectName
	 * @param objectLeftSideActions
	 * @param dataType
	 * @param fieldLabelName
	 * @param formulaReturnType
	 * @param formulaText
	 * @return true/false
	 */
	public boolean addCustomFieldforFormula(String environment, String mode,object objectName, ObjectFeatureName objectLeftSideActions, String dataType,String fieldLabelName,String[][] labelsWithValues,String formulaReturnType,String formulaText) {
		WebElement ele=null;
		 tabCustomObj=tabCustomObj;
		if(searchStandardOrCustomObject(environment, mode, objectName)) {
			log(LogStatus.PASS, "object searched : "+objectName.toString(), YesNo.No);
			ThreadSleep(5000);
			String xpath="//div[@data-aura-class='uiScroller']//a[text()='"+objectName.toString()+"']";
			if (objectName==object.Custom_Object) {
				xpath="//div[@data-aura-class='uiScroller']//a[text()='"+tabCustomObj.toString()+"']";
			}else {
				 xpath="//div[@data-aura-class='uiScroller']//a[text()='"+objectName.toString()+"']";
			}
			
			ele=FindElement(driver, xpath,objectName.toString()+" xpath", action.SCROLLANDBOOLEAN,30);
			if(ele!=null) {
				if(click(driver, ele, objectName.toString()+" xpath ", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.PASS, "clicked on object Name "+objectName, YesNo.No);
					ThreadSleep(5000);
					xpath="//div[@id='setupComponent']/div[@class='setupcontent']//ul/li/a[@data-list='"+objectLeftSideActions+"']";
					ele=FindElement(driver, xpath,objectLeftSideActions+" xpath", action.SCROLLANDBOOLEAN,20);
					if(click(driver, ele, objectLeftSideActions+" xpath ", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "clicked on object side action :  "+objectLeftSideActions, YesNo.No);
						ThreadSleep(5000);
						if(click(driver, getCustomFieldNewButton(30),"new button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "clicked on new button", YesNo.No);
							ThreadSleep(1000);
							if(switchToFrame(driver, 30, getNewCustomFieldFrame(objectName.toString(),30))) {
								if(click(driver, getNewCustomFieldDataTypeOrFormulaReturnType(dataType, 30),"data type radio button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.PASS,dataType+" : data type radio button ",YesNo.No);
									if(click(driver, getCustomFieldNextBtn(30),"next button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.PASS, "Clicked on Step 1 Next button", YesNo.No);
										ThreadSleep(1000);
										if(sendKeys(driver, getFieldLabelTextBox(30), fieldLabelName, "field label name ", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.PASS, "passed value in field label text box : "+fieldLabelName, YesNo.No);

											
												
												if (dataType.equalsIgnoreCase("Picklist")) {
													xpath = "//label[text()='Enter values, with each value separated by a new line']//preceding-sibling::input[@name='picklistType']";
													ele = FindElement(driver, xpath, "", action.BOOLEAN, 5);
													
													if(click(driver, ele,"Radio Button", action.SCROLLANDBOOLEAN)) {
														log(LogStatus.PASS, "Click on radio button", YesNo.No);
														ThreadSleep(2000);
														
														xpath = "//textarea[@id='ptext']";
														ele = FindElement(driver, xpath, "", action.BOOLEAN, 5);
														if(sendKeys(driver, ele, labelsWithValues[0][1],labelsWithValues[0][0]+" "+labelsWithValues[0][1], action.SCROLLANDBOOLEAN)) {
															log(LogStatus.PASS, "Passed Value in "+labelsWithValues[0][0]+" "+labelsWithValues[0][1], YesNo.No);
														}else {
															log(LogStatus.FAIL, "Not able to pass "+labelsWithValues[0][0]+" "+labelsWithValues[0][1],YesNo.Yes);
														}
														
													}else {
														log(LogStatus.FAIL, "Not Able to Click on radio button",YesNo.Yes);
															
													}
												
												} else {
													for (String[] labelWithValue : labelsWithValues) {
														xpath = "//label[contains(text(),'"+labelWithValue[0]+"')]/../following-sibling::td//input";
														ele = FindElement(driver, xpath, labelWithValue[0]+" "+labelWithValue[1], action.BOOLEAN, 5);
														if(sendKeys(driver, ele, labelWithValue[1],labelWithValue[0]+" "+labelWithValue[1], action.SCROLLANDBOOLEAN)) {
															log(LogStatus.PASS, "Passed Value in "+labelWithValue[0]+" "+labelWithValue[1], YesNo.No);
														}else {
															log(LogStatus.FAIL, "Not able to pass "+labelWithValue[0]+" "+labelWithValue[1],YesNo.Yes);
														}
														ThreadSleep(500);

													}
												}
					
												
											
											
										}
											if(click(driver, getCustomFieldNextBtn(30),"next button", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.PASS, "Clicked on Step 2 Next button", YesNo.No);
												ThreadSleep(2000);
//											if(sendKeys(driver, getFormulaTextBox(30), formulaText,"formula text area", action.SCROLLANDBOOLEAN)) {
//													log(LogStatus.PASS, "Passed Value in formula Text Box "+formulaText, YesNo.No);
													if(click(driver, getCustomFieldNextBtn(30),"next button", action.SCROLLANDBOOLEAN)) {
														log(LogStatus.PASS, "Clicked on Step 3 Next button", YesNo.No);
														ThreadSleep(1000);
														if(true/*click(driver, getCustomFieldNextBtn(30),"next button", action.SCROLLANDBOOLEAN)*/) {
															log(LogStatus.PASS, "Clicked on Step 4 Next button", YesNo.No);
															ThreadSleep(1000);
															if(click(driver, getCustomFieldSaveBtn(30), "save button", action.SCROLLANDBOOLEAN)) {
																log(LogStatus.PASS, "clicked on save button", YesNo.No);
																switchToDefaultContent(driver);
																return true;
																
															}else {
																log(LogStatus.FAIL, "Not able to click on save button so cannot create custom field "+objectName, YesNo.Yes);
															}
															
														}else {
															log(LogStatus.FAIL, "Not able to click on Step 4 next button so cannot create custom field : "+objectName,YesNo.Yes);
														}
														
													}else {
														log(LogStatus.FAIL, "Not able to click on Step 3 next button so cannot create custom field : "+objectName,YesNo.Yes);
													}
													
													
												
//											}else {
//												log(LogStatus.FAIL, "Not able to click on Step 2 next button so cannot create custom field : "+objectName,YesNo.Yes);
//											}
										}else {
											log(LogStatus.FAIL, "Not able to enter value in field label text box : "+fieldLabelName+" so cannot create custom field", YesNo.Yes);
										}
									}else {
										log(LogStatus.FAIL, "Not able to click on Step 1 next button so cannot create custom field", YesNo.Yes);
									}
								}else {
									log(LogStatus.FAIL, "Not able to click on data type radio button "+dataType+" so cannot create custom field", YesNo.Yes);
								}
							}else {
								log(LogStatus.FAIL, "Not able to switch in "+objectName+" new object frame so cannot add custom object", YesNo.Yes);
							}
						}else {
							log(LogStatus.FAIL, "Not able to click on New button so cannot add custom field in "+objectName.toString(), YesNo.Yes);
						}
						
					}else {
						log(LogStatus.FAIL, "Not able to click on object side action "+objectLeftSideActions+" so cannot add custom object ", YesNo.Yes);
					}
					
				}else {
					log(LogStatus.FAIL, "Not able to click on object Name "+objectName+" so cannot add custom object ", YesNo.Yes);
				}
			}else {
				log(LogStatus.FAIL, "Not able to found object : "+objectName.toString()+" so cannot add custom object", YesNo.Yes);
			}
		}else {
			log(LogStatus.FAIL, "Not able to search object "+objectName.toString()+" so cannot add custom object", YesNo.Yes);
		}
		switchToDefaultContent(driver);
		return false;
	}
	
	
	
	
	/**
	 * @author ANKIT JAISWAL
	 * @param objectName
	 * @param objectFeatureName
	 * @param fieldSetLabel
	 * @param fieldSetWhereisThisUsed
	 * @return true/false
	 */
	public boolean createFieldSetComponent(object objectName,ObjectFeatureName objectFeatureName,String fieldSetLabel, String fieldSetWhereisThisUsed,String DragComponentName) {
		boolean flag = false;
		WebElement ele=null,sourceElement=null;
		int count=0;
		if(searchStandardOrCustomObject(environment,mode, objectName)) {
			log(LogStatus.INFO, "click on Object : "+objectName, YesNo.No);
			ThreadSleep(2000);
			if(clickOnObjectFeature(environment,mode, objectName, objectFeatureName)) {
				log(LogStatus.INFO, "Clicked on feature : "+objectFeatureName, YesNo.No);
				ThreadSleep(1000);
				if(sendKeys(driver, getQuickSearchInObjectManager_Lighting(10), fieldSetLabel, "search text box", action.BOOLEAN)) {
					String xpath="//span[text()='"+fieldSetLabel+"']";
					ele = isDisplayed(driver, FindElement(driver, xpath, "field set label text", action.BOOLEAN, 3), "visibility", 3, "field set label text");
					if(ele!=null) {
						log(LogStatus.INFO, "Field Set Label "+fieldSetLabel+" is already created ", YesNo.No);
						return true;
					}
				}
				if(click(driver, getObjectFeatureNewButton(objectFeatureName, 10), "new button", action.BOOLEAN)) {
					log(LogStatus.INFO, "clicked on New button", YesNo.No);
					ThreadSleep(2000);
					switchToFrame(driver, 20, getFieldSetComponentFrame(20));
					if(sendKeys(driver, getFieldSetLabelTextBox(10),fieldSetLabel,"field set label text box", action.BOOLEAN)) {
						log(LogStatus.INFO, "Entering value in field set text box : "+fieldSetLabel, YesNo.No);
						if(sendKeys(driver, getFieldSetWhereIsThisUsedTextArea(10),fieldSetWhereisThisUsed,"where is this used text area", action.BOOLEAN)) {
							log(LogStatus.INFO, "Entering value in where is this used text area : "+fieldSetLabel, YesNo.No);
							if(click(driver, getSaveButton(10), "save button", action.BOOLEAN)) {
								log(LogStatus.ERROR, "Clicked on save button and create field set label : "+fieldSetLabel+" successfully", YesNo.Yes);
								ThreadSleep(2000);
								switchToDefaultContent(driver);
								if(DragComponentName!=null) {
									String[] splitedDragComponent= DragComponentName.split("<break>");
									switchToFrame(driver, 20, getEditPageLayoutFrame_Lighting(20));
									for(int i=0; i<splitedDragComponent.length; i++) {
										sendKeys(driver, getQuickFindSearchBox(environment, mode, 10), splitedDragComponent[i], "Search Value : "+splitedDragComponent[i], action.BOOLEAN);
										if(splitedDragComponent[i].equalsIgnoreCase("Highest Stage Reached")) {
											String DragComponent=splitedDragComponent[i].split(" ")[0];
											sourceElement =isDisplayed(driver, FindElement(driver, "//span[starts-with(text(),'"+DragComponent+"')]", "", action.BOOLEAN,10), "visibility",10,splitedDragComponent[i]+" page layout link");
										}else {
											sourceElement =isDisplayed(driver, FindElement(driver, "//span[starts-with(text(),'"+splitedDragComponent[i]+"')]", "", action.BOOLEAN,10), "visibility",10,splitedDragComponent[i]+" page layout link");
										}
										ThreadSleep(2000);
										if(dragNDropOperation(driver, sourceElement, getFieldSetdefaultViewDragAndDropTextLabel(5))) {
											log(LogStatus.INFO, "Dragged Successfully : "+splitedDragComponent[i], YesNo.No);
											count++;
										}else {
											log(LogStatus.ERROR, "Not able to drag and drop field "+splitedDragComponent[i]+" in created field set component "+fieldSetLabel, YesNo.Yes);
										}
									}
									if(count==splitedDragComponent.length) {
										flag=true;
									}
								}else {
									flag=true;
								}
								if(click(driver, getPageLayoutSaveBtn(object.Global_Actions, 10), "page layouts save button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Save button", YesNo.No);
									
								}else {
									log(LogStatus.ERROR, "Not able to click on Save button cannot save pagelayout dragged object or section in field set component "+fieldSetLabel, YesNo.Yes);
									flag=false;
								}
							}else {
								log(LogStatus.ERROR, "Not able to click on save button so cannot create field set label : "+fieldSetLabel, YesNo.Yes);
							}
						}else {
							log(LogStatus.ERROR, "Not able to enter the value in where is this used text area : "+fieldSetLabel+" so cannot create field set component", YesNo.Yes);
						}
					}else {
						log(LogStatus.ERROR, "Not able to enter the value in field set label text box : "+fieldSetLabel+" so cannot create field set component", YesNo.Yes);
					}
				}else {
					log(LogStatus.ERROR, "Not able to click on new button so cannot create field set compnent : "+fieldSetLabel, YesNo.Yes);
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on object feature : "+objectFeatureName, YesNo.Yes);
			}
			
		}else {
			log(LogStatus.ERROR, "Not able to click on Object : "+objectName+" so cannot create field set component", YesNo.Yes);
		}
		switchToDefaultContent(driver);
		return flag;
	}
	
	/**
	 * @author Ankit Jaiswal
	 * @param objectName
	 * @param objectFeatureName
	 * @param permissionType
	 * @param fieldLabel
	 * @param profileName
	 * @return true if able to give/Remove Object Permission From Object Manager
	 */
	public boolean giveAndRemoveObjectPermissionFromObjectManager(object objectName,ObjectFeatureName objectFeatureName,PermissionType permissionType, String fieldLabel, String profileName) {
		boolean flag = false;
		WebElement ele=null;
		if(searchStandardOrCustomObject(environment,mode, objectName)) {
			log(LogStatus.INFO, "click on Object : "+objectName, YesNo.No);
			ThreadSleep(2000);
			if(clickOnObjectFeature(environment,mode, objectName, objectFeatureName)) {
				log(LogStatus.INFO, "Clicked on feature : "+objectFeatureName, YesNo.No);
				ThreadSleep(1000);
				if(sendKeys(driver, getQuickSearchInObjectManager_Lighting(10), fieldLabel, "search text box", action.BOOLEAN)) {
					String xpath="//a[contains(@href,'/Contact/FieldsAndRelationships')]/span[text()='"+fieldLabel+"']";
					ele = isDisplayed(driver, FindElement(driver, xpath, "field set label text", action.BOOLEAN, 3), "visibility", 3, "field set label text");
					if(ele!=null) {
						if(click(driver, ele, "field label text link", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on field label "+fieldLabel, YesNo.No);
							switchToFrame(driver, 20, getFieldAndRelationShipFrame(20));
							ThreadSleep(1000);
							if(click(driver, getObjectEditOrSetFieldSecurityOrViewFieldAccessbilityBtn("View Field Accessibility", 10), "view field accessbility button xpath", action.BOOLEAN)) {
								log(LogStatus.INFO, "clicked on view field accessbility of field label : "+fieldLabel, YesNo.No);
								switchToFrame(driver, 20, getFieldAndRelationShipFrame(20));
								ThreadSleep(1000);
								if(selectVisibleTextFromDropDown(driver, getFieldAccessbilityDropDown(10), "field accessbility drop down",fieldLabel)) {
									log(LogStatus.INFO,"select field label accessbility drop down "+fieldLabel,YesNo.No);
									ThreadSleep(1000);
									if(clickUsingJavaScript(driver, getfieldAccessOptionLink(fieldLabel,profileName,10),"profile link name", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO,"clicked on "+profileName+" link",YesNo.No);
										ThreadSleep(5000);
										switchToFrame(driver, 20, getFieldAndRelationShipFrame(20));
										if(click(driver, getFieldLevelSecurityVisibleCheckBox(10), "check box", action.BOOLEAN)) {
											log(LogStatus.INFO,"Clicked on field level security check box", YesNo.No);
											if(click(driver, getViewAccessbilityDropDownSaveButton(10), "save button", action.BOOLEAN)) {
												log(LogStatus.INFO,"save button",YesNo.No);
												return true;
												
											}else {
												log(LogStatus.ERROR,"Not able to click on save button field accessbility of field label "+fieldLabel+" in object "+objectName+" so cannot "+permissionType,YesNo.Yes);
											}
										}else {
											log(LogStatus.ERROR,"Not able to click on visible field accessbility of field label "+fieldLabel+" in object "+objectName+" so cannot "+permissionType,YesNo.Yes);
										}
									}else {
										log(LogStatus.ERROR," Not able to click on profile link from view field accessbility of field label "+fieldLabel+" in object "+objectName+" so cannot "+permissionType,YesNo.Yes);
									}
								}else {
									log(LogStatus.ERROR,"Not able to select value from view field accessbility of field label "+fieldLabel+" in object "+objectName+" so cannot "+permissionType,YesNo.Yes);
								}
							}else {
								log(LogStatus.ERROR,"Not able to click on view field accessbility of field label "+fieldLabel+" in object "+objectName+" so cannot "+permissionType,YesNo.Yes);
							}
						}else {
							log(LogStatus.ERROR,"Not able to click on field label "+fieldLabel+" in object "+objectName+" so cannot "+permissionType,YesNo.Yes);
						}
					}else {
						log(LogStatus.ERROR,"Search field label "+fieldLabel+" is not found in object "+objectName+" so cannot "+permissionType,YesNo.Yes);	
					}
				}else {
					log(LogStatus.ERROR,"Not able to search field label "+fieldLabel+" in object "+objectName+" so cannot "+permissionType,YesNo.Yes);
				}

			}else {
				log(LogStatus.FAIL, "Not able to found object : "+objectName.toString()+" so cannot "+permissionType, YesNo.Yes);
			}
		}else {
			log(LogStatus.FAIL, "Not able to search object "+objectName.toString()+" so cannot "+permissionType, YesNo.Yes);
		}

		return flag;
	}

	/**
	 * @author Ankit Jaiswal
	 * @param objectName
	 * @param objectFeatureName
	 * @param fieldSetLabel
	 * @param DragComponentName
	 * @param removableObjectName
	 * @param removeSomeFields
	 * @return true if able to change Position Of Field Set Component
	 */
	public boolean changePositionOfFieldSetComponent(object objectName,ObjectFeatureName objectFeatureName,String fieldSetLabel,String DragComponentName,String removableObjectName,YesNo removeSomeFields) {
		boolean flag = false;
		WebElement ele=null,sourceElement=null;
		int count=0;
		if(searchStandardOrCustomObject(environment,mode, objectName)) {
			log(LogStatus.INFO, "click on Object : "+objectName, YesNo.No);
			ThreadSleep(2000);
			if(clickOnObjectFeature(environment,mode, objectName, objectFeatureName)) {
				log(LogStatus.INFO, "Clicked on feature : "+objectFeatureName, YesNo.No);
				ThreadSleep(1000);
				if(sendKeys(driver, getQuickSearchInObjectManager_Lighting(10), fieldSetLabel, "search text box", action.BOOLEAN)) {
					String xpath="//span[text()='"+fieldSetLabel+"']/..";
					ele = isDisplayed(driver, FindElement(driver, xpath, "field set label text", action.BOOLEAN, 3), "visibility", 3, "field set label text");
					if(ele!=null) {
						if(click(driver, ele, "create field set "+fieldSetLabel, action.BOOLEAN)) {
							log(LogStatus.INFO, "Field Set Label "+fieldSetLabel+" is already created ", YesNo.No);
							ThreadSleep(5000);
							switchToFrame(driver, 20, getEditPageLayoutFrame_Lighting(20));
							if(removableObjectName!=null) {
								String[] removeObject= removableObjectName.split("<break>");
								List<WebElement> lst = getDraggedObjectListInCreateFieldSet();
								if(!lst.isEmpty()) {
									for(int i=0; i<removeObject.length; i++) {
										xpath="//div[@id='defaultView']//div[starts-with(text(),'"+removeObject[i]+"')]/ancestor::div[contains(@class,'field-source')]";
										ele=FindElement(driver,xpath,removeObject[i]+" xpath", action.BOOLEAN,10);
										
										String id =ele.getAttribute("id");
										ThreadSleep(1000);
										((JavascriptExecutor) driver).executeScript("document.getElementById('"+id+"').setAttribute('class', 'field-source field-selected field-hover');");
										ThreadSleep(2000);
										if(click(driver,FindElement(driver, xpath+"//div[@class='remove']", "remove Icon", action.BOOLEAN, 10), "remove icon", action.BOOLEAN)) {
											log(LogStatus.INFO,"Clicked on reomve icon of object : "+removeObject[i],YesNo.No);
										}else {
											log(LogStatus.INFO,"Not able to click on reomve icon of object : "+removeObject[i]+" so cannot remove old object and dragged new objects in field set",YesNo.No);
											return false;
										}
									}
								}else {
									log(LogStatus.ERROR, "Object is not present in create field set "+fieldSetLabel+" so cannot remove old object and dragged new objects in field set", YesNo.Yes);
									return false;
								}
								
							}
							if(removeSomeFields.toString().equalsIgnoreCase(YesNo.Yes.toString())) {
								xpath="//div[@id='defaultView']/div";
								List<WebElement> fieldSetList=FindElements(driver, xpath, "all dragged field set list xpath");
								if(!fieldSetList.isEmpty()) {
									for(int i=0; i<fieldSetList.size()-2; i++) {
										ThreadSleep(1000);
										String id =fieldSetList.get(i).getAttribute("id");
										ThreadSleep(1000);
										((JavascriptExecutor) driver).executeScript("document.getElementById('"+id+"').setAttribute('class','field-source');");
										ThreadSleep(1000);
										mouseOverOperation(driver, fieldSetList.get(i));
										ThreadSleep(3000);
										WebElement ele1 =FindElement(driver, "//div[@id='"+id+"']//div[@class='remove']", "remove Icon", action.BOOLEAN, 10);
										mouseOverClickOperation(driver, ele1);
										fieldSetList=FindElements(driver, xpath, "all dragged field set list xpath");
										if(fieldSetList.size()!=2) {
											continue;
										}else {
											break;
										}
									}
									
								}else {
									log(LogStatus.ERROR, "Field Set objects is not available so cannot remove some field set", YesNo.Yes);
								}
							}
							if(DragComponentName!=null) {
								String[] splitedDragComponent= DragComponentName.split("<break>");
								for(int i=0; i<splitedDragComponent.length; i++) {
									sendKeys(driver, getQuickFindSearchBox(environment, mode, 10), splitedDragComponent[i], "Search Value : "+splitedDragComponent[i], action.BOOLEAN);
									if(splitedDragComponent[i].equalsIgnoreCase("Highest Stage Reached") || splitedDragComponent[i].equalsIgnoreCase("Average Deal Quality Score") || splitedDragComponent[i].equalsIgnoreCase("Contact Referral Source") || splitedDragComponent[i].equalsIgnoreCase("Last Stay-in-Touch Request Date") 
											||	splitedDragComponent[i].equalsIgnoreCase( "Total Fund Commitments (mn)") || splitedDragComponent[i].equalsIgnoreCase( "Total Co-investment Commitments (mn)")) {
										String DragComponent=splitedDragComponent[i].split(" ")[0];
										sourceElement =isDisplayed(driver, FindElement(driver, "//span[starts-with(text(),'"+DragComponent+"')]", "", action.BOOLEAN,10), "visibility",10,splitedDragComponent[i]+" page layout link");
									}else {
										sourceElement =isDisplayed(driver, FindElement(driver, "//span[starts-with(text(),'"+splitedDragComponent[i]+"')]", "", action.BOOLEAN,10), "visibility",10,splitedDragComponent[i]+" page layout link");
									}
									ThreadSleep(2000);
									if(dragNDropField(driver, sourceElement, getFieldSetdefaultViewDragAndDropTextLabel(5))) {
										log(LogStatus.INFO, "Dragged Successfully : "+splitedDragComponent[i], YesNo.No);
										count++;
									}else {
										log(LogStatus.ERROR, "Not able to drag and drop field "+splitedDragComponent[i]+" in created field set component "+fieldSetLabel, YesNo.Yes);
									}
								}
								if(count==splitedDragComponent.length) {
									flag=true;
								}
							}else {
								flag=true;
							}
							if(click(driver, getPageLayoutSaveBtn(object.Global_Actions, 10), "page layouts save button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Save button", YesNo.No);
								ThreadSleep(5000);

							}else {
								log(LogStatus.ERROR, "Not able to click on Save button cannot save pagelayout dragged object or section in field set component "+fieldSetLabel, YesNo.Yes);
								flag=false;
							}
						}else {
							log(LogStatus.INFO, "Not able to click on created Field Set Label "+fieldSetLabel+" is not visible so cannot change position of labels", YesNo.Yes);
						}
					}else {
						log(LogStatus.INFO, "created Field Set Label "+fieldSetLabel+" is not visible so cannot change position of labels", YesNo.Yes);
					}
				}else {
					log(LogStatus.INFO, "Not able to search created Field Set Label "+fieldSetLabel, YesNo.Yes);
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on object feature : "+objectFeatureName, YesNo.Yes);
			}
			
		}else {
			log(LogStatus.ERROR, "Not able to click on Object : "+objectName+" so cannot create field set component", YesNo.Yes);
		}
		switchToDefaultContent(driver);
		return flag;
	}
	
	
	/**
	 * @author Ankit Jaiswal
	 * @param objectName
	 * @param objectFeatureName
	 * @param fieldSetLabel
	 * @param removeSomeFields
	 * @return true if able to delete field set component
	 */
	public boolean deleteFieldSetComponent(object objectName,ObjectFeatureName objectFeatureName,String fieldSetLabel,YesNo removeSomeFields) {
		boolean flag = false;
		WebElement ele=null;
		if(searchStandardOrCustomObject(environment,mode, objectName)) {
			log(LogStatus.INFO, "click on Object : "+objectName, YesNo.No);
			ThreadSleep(2000);
			if(clickOnObjectFeature(environment,mode, objectName, objectFeatureName)) {
				log(LogStatus.INFO, "Clicked on feature : "+objectFeatureName, YesNo.No);
				ThreadSleep(1000);
				while(true) {
					if(sendKeys(driver, getQuickSearchInObjectManager_Lighting(10), fieldSetLabel, "search text box", action.BOOLEAN)) {
						String xpath="//span[text()='"+fieldSetLabel+"']/..";
						ele = isDisplayed(driver, FindElement(driver, xpath, "field set label text", action.BOOLEAN, 3), "visibility", 3, "field set label text");
						if(ele!=null) {
							if(click(driver, ele, "create field set "+fieldSetLabel, action.BOOLEAN)) {
								log(LogStatus.INFO, "Field Set Label "+fieldSetLabel+" is already created ", YesNo.No);
								ThreadSleep(5000);
								switchToFrame(driver, 20, getEditPageLayoutFrame_Lighting(20));
								if(removeSomeFields.toString().equalsIgnoreCase(YesNo.Yes.toString())) {
									xpath="//div[@id='defaultView']/div";
									List<WebElement> fieldSetList=FindElements(driver, xpath, "all dragged field set list xpath");
									if(!fieldSetList.isEmpty() && fieldSetList.size() > 2) {
										for(int i=0; i<fieldSetList.size()-2; i++) {
											ThreadSleep(1000);
											String id =fieldSetList.get(i).getAttribute("id");
											ThreadSleep(1000);
											((JavascriptExecutor) driver).executeScript("document.getElementById('"+id+"').setAttribute('class', 'field-source field-selected field-hover');");
											ThreadSleep(2000);
											if(click(driver,FindElement(driver, xpath+"//div[@class='remove']", "remove Icon", action.BOOLEAN, 10), "remove icon", action.BOOLEAN)) {
												log(LogStatus.INFO,"Clicked on reomve icon of object",YesNo.No);
											}else {
												log(LogStatus.INFO,"Not able to click on reomve icon of object so cannot remove object from field set component",YesNo.No);
												return false;
											}
										fieldSetList=FindElements(driver, xpath, "all dragged field set list xpath");
										if(fieldSetList.size()!=2) {
											if(click(driver, getPageLayoutSaveBtn(object.Global_Actions, 10), "page layouts save button", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, "Clicked on Save button", YesNo.No);
												ThreadSleep(5000);
												break;

											}else {
												log(LogStatus.ERROR, "Not able to click on Save button cannot save pagelayout dragged object or section in field set component "+fieldSetLabel, YesNo.Yes);
												return false;
											}
										}else {
											if(click(driver, getPageLayoutSaveBtn(object.Global_Actions, 10), "page layouts save button", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, "Clicked on Save button", YesNo.No);
												ThreadSleep(5000);
												return true;
												
											}else {
												log(LogStatus.ERROR, "Not able to click on Save button cannot save pagelayout dragged object or section in field set component "+fieldSetLabel, YesNo.Yes);
												flag=false;
											}
										}
									}
								}else {
									log(LogStatus.ERROR, "more then 2 fields are not available in field set object", YesNo.Yes);
								}
							}
							}else {
								log(LogStatus.INFO, "Not able to click on created Field Set Label "+fieldSetLabel+" is not visible so cannot change position of labels", YesNo.Yes);
							}
						}else {
							log(LogStatus.INFO, "created Field Set Label "+fieldSetLabel+" is not visible so cannot change position of labels", YesNo.Yes);
						}
					}else {
						log(LogStatus.INFO, "Not able to search created Field Set Label "+fieldSetLabel, YesNo.Yes);
					}
					switchToDefaultContent(driver);
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on object feature : "+objectFeatureName, YesNo.Yes);
			}
			
		}else {
			log(LogStatus.ERROR, "Not able to click on Object : "+objectName+" so cannot create field set component", YesNo.Yes);
		}
		switchToDefaultContent(driver);
		return flag;
	}
	
	

/**
 * @author Akul Bhutani
 * @param projectName
 * @param status
 * @return EditInFrontOfFieldValues 
 */
public WebElement clickOnEditInFrontOfFieldValues(String projectName, String status) {
	status=status.replace("_", " ");
	String xpath="//th[text()='"+status+"']/preceding-sibling::td//a[contains(@title,'Edit')]";
	WebElement ele=isDisplayed(driver, FindElement(driver, xpath, "edit", action.SCROLLANDBOOLEAN, 10), "visibility", 10, "edit");
	return ele;
}

/**
 * @author Azhar Alam
 * @param driver
 * @param appName
 * @param developerName
 * @param description
 * @param timeOut
 * @return true if clcik on edit for App
 */
public boolean clickOnEditForApp(WebDriver driver,String appName,String developerName,String description,int timeOut) {
	boolean flag=false;;
	String xpath="";
	xpath="//*[text()='"+appName+"']/../../following-sibling::*//*[text()='"+developerName+"']/../../following-sibling::*//*[text()='"+description+"']/../../following-sibling::*//*[text()='Show Actions']/..";
	WebElement scrollEle = FindElement(driver, "//div[@class='uiScroller scroller-wrapper scroll-bidirectional native']", "Widget scroll",action.SCROLLANDBOOLEAN, 60);
	scrollActiveWidget(driver,scrollEle,By.xpath(xpath));
	ThreadSleep(2000);
	WebElement ele=isDisplayed(driver, FindElement(driver, xpath, "show more action", action.SCROLLANDBOOLEAN, timeOut), "visibility", timeOut, "show more action");
	if (click(driver, ele, "Show more action against "+appName+" : "+developerName+" "+description, action.BOOLEAN)) {
		log(LogStatus.INFO, "click on Show more action against "+appName+" : "+developerName+" "+description, YesNo.No);
		ThreadSleep(1000);
		xpath="//li/a[@title='Edit']";
		 ele=isDisplayed(driver, FindElement(driver, xpath, "edit", action.SCROLLANDBOOLEAN, timeOut), "visibility", timeOut, "edit");
		 if (click(driver, ele, "Show more action against "+appName+" : "+developerName+" "+description, action.BOOLEAN)) {
			log(LogStatus.INFO, "able to click on edit button against "+appName+" : "+developerName+" "+description, YesNo.No);
			ThreadSleep(1000);
			flag=true;
		} else {
			log(LogStatus.ERROR, "Not able to click on edit button against "+appName+" : "+developerName+" "+description, YesNo.Yes);
		}
		
	} else {
		log(LogStatus.ERROR, "Not able to click on Show more action against "+appName+" : "+developerName+" "+description, YesNo.Yes);
	}
	return flag;
}

/**
 * @param driver
 * @param appSetting
 * @param timeOut
 * @return true if able to click on particular app setting
 */
public boolean clickOnAppSettingList(WebDriver driver,AppSetting appSetting,int timeOut) {
	boolean flag=false;;
	String xpath="";
	xpath="//*[contains(text(),'"+appSetting+"')]";
	WebElement ele=isDisplayed(driver, FindElement(driver, xpath, "show more action", action.SCROLLANDBOOLEAN, timeOut), "visibility", timeOut, "show more action");
	if (click(driver, ele, appSetting.toString(), action.BOOLEAN)) {
		log(LogStatus.INFO, "able to click on "+appSetting, YesNo.No);
		flag=true;
	} else {
		log(LogStatus.ERROR, "Not able to click on "+appSetting, YesNo.Yes);
	}
	return flag;
}

/**
 * @author Azhar Alam
 * @param projectName
 * @param addRemoveTabName
 * @param customTabActionType
 */
public void addRemoveAppSetingData(String projectName,String addRemoveTabName, customTabActionType customTabActionType) {
	String[] splitedTabs = addRemoveTabName.split(",");
	WebElement ele;
	String xpath ;
	int count=0;
	if (customTabActionType.toString().equalsIgnoreCase("Add")) {
		System.err.println("inside Add");
		for (int i = 0; i < splitedTabs.length; i++) {
			//////////////////////////////////////////////////////
			xpath = "//div[contains(text(),'Selected')]/..//following-sibling::*//*[text()='"+splitedTabs[i]+"']";
			ele = FindElement(driver, xpath, "selected item : "+splitedTabs[i], action.BOOLEAN, 10);
			if (ele!=null) {
				log(LogStatus.INFO, splitedTabs[i]+" Already added", YesNo.No);
			} else {
				log(LogStatus.INFO, "going to add "+splitedTabs[i], YesNo.No);
				xpath = "//div[@class='search-form']//input";
				ele = FindElement(driver, xpath, "available item search box", action.BOOLEAN, 10);
				if (sendKeys(driver, ele,splitedTabs[i],"available item search box",action.BOOLEAN)) {
					log(LogStatus.INFO,"send value to available item search box : "+splitedTabs[i],YesNo.No);
					ThreadSleep(500);
					xpath = "//div[contains(text(),'Available')]/..//following-sibling::*//*[text()='"+splitedTabs[i]+"']";
					ele = FindElement(driver, xpath, splitedTabs[i], action.BOOLEAN, 10);
					if (click(driver, ele,splitedTabs[i]+" from available item", action.BOOLEAN)) {
						log(LogStatus.INFO,"Able to select "+splitedTabs[i]+" from available item",YesNo.No);
						ThreadSleep(500);	
						xpath = "//*[@title='Add']";
						ele = FindElement(driver, xpath, "Add icon for : "+splitedTabs[i], action.BOOLEAN, 10);
						if (click(driver, ele,"Add icon for : "+splitedTabs[i], action.BOOLEAN)) {
							log(LogStatus.INFO,"Able to clcik on Add icon for : "+splitedTabs[i],YesNo.No);
							ThreadSleep(500);		
							count++;
						} else {
							sa.assertTrue(false, "Not Able to click on Add icon for : "+splitedTabs[i]);
							log(LogStatus.FAIL,"Not Able to click on Add icon for : "+splitedTabs[i],YesNo.Yes);
						}
					} else {
						sa.assertTrue(false, "Not Able to select "+splitedTabs[i]+" from available item");
						log(LogStatus.FAIL,"Not Able to select "+splitedTabs[i]+" from available item",YesNo.Yes);
					}

				} else {
					sa.assertTrue(false, "Not Able to send value to available item search box : "+splitedTabs[i]);
					log(LogStatus.FAIL,"Not Able to send value to available item search box : "+splitedTabs[i],YesNo.Yes);
				}

			}
		}



	} else if (customTabActionType.toString().equalsIgnoreCase("Remove")) {
		System.err.println("inside remove");
		for (int i = 0; i < splitedTabs.length; i++) {
			//////////////////////////////////////////////////////
			xpath = "//div[contains(text(),'Selected')]/..//following-sibling::*//*[text()='"+splitedTabs[i]+"']";
			ele = FindElement(driver, xpath, "selected item : "+splitedTabs[i], action.BOOLEAN, 10);
			if (ele==null) {
				log(LogStatus.INFO, splitedTabs[i]+" Already removed", YesNo.No);
			} else {
				log(LogStatus.INFO, "going to remove "+splitedTabs[i], YesNo.No);
				xpath = "//*[@title='Remove']";
				ele = FindElement(driver, xpath, "Add icon for : "+splitedTabs[i], action.BOOLEAN, 10);
				if (click(driver, ele,"Add icon for : "+splitedTabs[i], action.BOOLEAN)) {
					log(LogStatus.INFO,"Able to click on remove icon for : "+splitedTabs[i],YesNo.No);
					ThreadSleep(500);	
					count++;
				} else {
					sa.assertTrue(false, "Not Able to click on remove icon for : "+splitedTabs[i]);
					log(LogStatus.FAIL,"Not Able to click on remove icon for : "+splitedTabs[i],YesNo.Yes);
				}


			}
		}
	} 

	if (count>0) {
		if (click(driver, getCustomTabSaveBtn(projectName,60), "Custom Tab Save Button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO,"click on save button",YesNo.No);
			ThreadSleep(5000);
		} else {
			sa.assertTrue(false, "Not Able to click on save button");
			log(LogStatus.FAIL,"Not Able to click on save button",YesNo.Yes);
		}	
	} 
	

}


/**
 * @author Azhar Alam
 * @param driver
 * @param userName
 * @param recordType
 * @param timeOut
 * @return true if Record Type Setting changed
 */
public boolean changeRecordTypeSetting(WebDriver driver,String userName,String recordType,int timeOut) {
	switchToDefaultContent(driver);
	switchToFrame(driver, 20, getSetUpPageIframe(60));
	boolean flag=false;;
	String xpath="";
	xpath="//th//a[text()='"+userName+"']";
	WebElement ele=FindElement(driver, xpath,userName, action.SCROLLANDBOOLEAN, timeOut);
	ele=isDisplayed(driver, ele, "visibility", timeOut, userName);
	if (click(driver, ele, userName.toString(), action.BOOLEAN)) {
		log(LogStatus.INFO, "able to click on "+userName, YesNo.No);
		switchToDefaultContent(driver);
		ThreadSleep(5000);
		switchToFrame(driver, 60, getSetUpPageIframe(20));
		xpath="//*[text()='Accounts']/following-sibling::*//*[text()='Edit']";
		ele=FindElement(driver, xpath, "Edit Button", action.SCROLLANDBOOLEAN, timeOut);
		if (click(driver, ele, "Edit Button", action.BOOLEAN)) {
			log(LogStatus.INFO, "able to click on edit button for record type settiing", YesNo.No);
			switchToDefaultContent(driver);
			ThreadSleep(5000);
			switchToFrame(driver, 20, getSetUpPageIframe(20));
			xpath="//select[@id='p5']";
			ele=FindElement(driver, xpath, "Record dropdown", action.SCROLLANDBOOLEAN, timeOut);
			scrollDownThroughWebelement(driver, ele, "Record dropdown");
			if (selectVisibleTextFromDropDown(driver, ele, recordType, recordType)) {
				log(LogStatus.INFO, "selected default record Type : "+recordType, YesNo.No);

				if (click(driver, getCreateUserSaveBtn_Lighting(30), "Save Button",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "clicked on save button for record type settiing", YesNo.No);
					flag=true;
				} else {
					log(LogStatus.ERROR, "not able to click on save button for record type settiing", YesNo.Yes);
				}

				flag=true;
			} else {
				log(LogStatus.ERROR, "not able to select default record Type : "+recordType, YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "not able to click on edit button for record type settiing", YesNo.Yes);
		}

	} else {
		log(LogStatus.ERROR, "Not able to click on "+userName, YesNo.Yes);
	}
	return flag;
}

/**
 * @author Azhar Alam
 * @param projectName
 * @param recordTypeLabel
 * @param timeOut
 * @return getRecordTypeLabel
 */
public WebElement getRecordTypeLabel(String projectName,String recordTypeLabel,int timeOut) {
	String xpath="//*[text()='"+recordTypeLabel+"']/..//following-sibling::td//input";
	WebElement ele=isDisplayed(driver, FindElement(driver, xpath, recordTypeLabel, action.BOOLEAN, 10), "visibility", 10, recordTypeLabel);
	return ele;
}

/**
 * @author Azhar Alam
 * @param projectName
 * @param labelWithValue
 * @param isMakeAvailable
 * @param isMakeDefault
 * @param layOut
 * @param timeOut
 * @return true if record type is created for Object
 */
public boolean createRecordTypeForObject(String projectName,String[][] labelWithValue,boolean isMakeAvailable,boolean isMakeDefault,String layOut,int timeOut) {
	WebElement ele;
	String label;
	String value;
	boolean flag=false;

	switchToDefaultContent(driver);
	if (click(driver,getRecordTypeNewButton(10), "Record Type New Button", action.SCROLLANDBOOLEAN)) {
		log(LogStatus.INFO, "Click on Record Type New Button", YesNo.No);
		ThreadSleep(5000);
		switchToFrame(driver, 20, getSetUpPageIframe(60));
		for (String[] lv : labelWithValue) {
			label=lv[0];
			value=lv[1];
			ele =  getRecordTypeLabel(projectName, label, 20);
			ThreadSleep(2000);
			if (label.equals(recordTypeLabel.Active.toString())) {

				if (click(driver, ele, "Active CheckBox", action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on Active CheckBox", YesNo.No);	
				} else {
					log(LogStatus.ERROR, "Not Able to Click on Active CheckBox", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on Active CheckBox");
				}
			} else {

				if (sendKeys(driver, ele, value, label, action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to enter "+label, YesNo.No);
					ThreadSleep(2000);
					flag=true;
				} else {
					log(LogStatus.ERROR, "Not Able to enter "+value+" to label "+label, YesNo.Yes);
					sa.assertTrue(false,"Not Able to enter "+value+" to label "+label);
				}

			}

		}
		if (isMakeAvailable) {
			ele=getMakeAvailableCheckBox(10);
			if (click(driver, ele, "make Available CheckBox", action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on make Available CheckBox", YesNo.No);	
				ThreadSleep(1000);



			} else {
				log(LogStatus.ERROR, "Not Able to Click on make Available CheckBox", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on make Available CheckBox");
			}	
		}

		if (isMakeDefault) {
			ele=getMakeDefaultCheckBoxCheckBox(10);
			if (click(driver, ele, "make Default CheckBox", action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on make Default CheckBox", YesNo.No);	
				ThreadSleep(1000);
			} else {
				log(LogStatus.ERROR, "Not Able to Click on make Default CheckBox", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on make Default CheckBox");
			}
		}
		if(click(driver, getCustomFieldNextBtn2(30),"next button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "Clicked on Next button", YesNo.No);
			ThreadSleep(1000);		

			if (layOut!=null) {
				if(selectVisibleTextFromDropDown(driver, getApplyOneLayoutToAllProfiles(10), "Page Layout",layOut)) {
					log(LogStatus.INFO,"Select Existing Page Layout drop down "+layOut,YesNo.No);
					ThreadSleep(1000);	
				}else {
					log(LogStatus.ERROR,"Not able to select value from Existing Page Layout drop down "+layOut,YesNo.Yes);
				}	
			}
			if (click(driver,  getCustomTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.ERROR, "Click on save Button ", YesNo.No);
				ThreadSleep(5000);
				flag=true;
			} else {
				log(LogStatus.ERROR, "Not Able to Click on save Button ", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on save Button ");
			}

		}else {
			log(LogStatus.FAIL, "Not able to click on next button so cannot record Type",YesNo.Yes);
			sa.assertTrue(false,"Not able to click on next button so cannot record Type");
		}




	} else {
		log(LogStatus.ERROR, "Not Able to Click on Record Type New Button", YesNo.Yes);
		sa.assertTrue(false,"Not Able to Click on Record Type New Button");
	}
	return flag;
}

/**
 * @author Azhar Alam
 * @param projectName
 * @param labelWithValue
 * @param timeOut
 * @return true if able to click on edit button for Object
 */
public boolean editRecordTypeForObject(String projectName,String[][] labelWithValue,int timeOut) {
	WebElement ele;
	String label;
	String value;
	boolean flag=false;
	ThreadSleep(5000);
	switchToDefaultContent(driver);
	switchToFrame(driver, 20, getSetUpPageIframe(60));
	if (click(driver, getEditButton(environment,"Classic",10), "edit", action.SCROLLANDBOOLEAN)) {
		log(LogStatus.INFO, "Click on edit Button", YesNo.No);
		ThreadSleep(5000);
		switchToDefaultContent(driver);
		switchToFrame(driver, 20, getSetUpPageIframe(60));
		for (String[] lv : labelWithValue) {
			label=lv[0];
			value=lv[1];
			ele =  getRecordTypeLabel(projectName, label, 20);
			ThreadSleep(2000);
			
			try {
				if (isAlertPresent(driver)) {
					switchToAlertAndAcceptOrDecline(driver, 10, action.ACCEPT);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					switchToAlertAndAcceptOrDecline(driver, 10, action.ACCEPT);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			if (label.equals(recordTypeLabel.Active.toString())) {
				if (click(driver, ele, "Active CheckBox", action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on Active CheckBox", YesNo.No);	
				} else {
					log(LogStatus.ERROR, "Not Able to Click on Active CheckBox", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on Active CheckBox");
				}
			} else {

				if (sendKeys(driver, ele, value, label, action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to enter "+label, YesNo.No);
					ThreadSleep(2000);
					flag=true;
				} else {
					log(LogStatus.ERROR, "Not Able to enter "+value+" to label "+label, YesNo.Yes);
					sa.assertTrue(false,"Not Able to enter "+value+" to label "+label);
				}
				
				

			}

		}
		
		if (click(driver,getCreateUserSaveBtn_Lighting(30), "Save Button",action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "clicked on save button", YesNo.No);
			flag=true;
			ThreadSleep(5000);
			recordTypeVerification(labelWithValue);
		} else {
			log(LogStatus.ERROR, "not able to click on save button", YesNo.Yes);
			sa.assertTrue(false, "not able to click on save button");
		}




	} else {
		log(LogStatus.ERROR, "Not Able to Click on Edit Button", YesNo.Yes);
		sa.assertTrue(false,"Not Able to Click on Edit Button");
	}
	return flag;
}

/**
 * @author Azhar Alam
 * @param labelWithValue
 */
public void recordTypeVerification(String[][] labelWithValue) {
	String xpath = "";
	WebElement ele;
	ThreadSleep(5000);
	switchToDefaultContent(driver);
	switchToFrame(driver, 20, getSetUpPageIframe(60));
	for (String[] labelValue : labelWithValue) {
		xpath ="//*[text()='"+labelValue[0]+"']/..//following-sibling::td[text()='"+labelValue[1]+"']";
		ele=FindElement(driver, xpath, labelValue[0]+" with Value "+labelValue[1], action.BOOLEAN, 10);
		if (ele!=null) {
			log(LogStatus.PASS, labelValue[0]+" with Value "+labelValue[1]+" verified", YesNo.No);	
		} else {
			log(LogStatus.ERROR, labelValue[0]+" with Value "+labelValue[1]+" not verified", YesNo.Yes);
			sa.assertTrue(false, labelValue[0]+" with Value "+labelValue[1]+" not verified");
		}
		
	}
}

/**
 * @author Azhar Alam
 * @param projectName
 * @param field
 * @return API name of String
 */
public String returnAPINameOfField(String projectName, String field) {
	String xpath = "//span[text()='"+field+"']/../../following-sibling::td[1]/span";
	WebElement ele= isDisplayed(driver, FindElement(driver, xpath,field,
			action.SCROLLANDBOOLEAN, 10), "visibility", 10, field);
	if (ele!=null)
		return ele.getText();
	return ""; 

}

/**
 * @author Azhar Alam
 * @param projectName
 * @param label
 * @param timeOut
 * @return label iput box Element
 */
public WebElement getLabelInputBoxwithCommonXpath(String projectName,String label,int timeOut) {
	String xpath="//*[text()='"+label+"']/..//following-sibling::td//input";
	WebElement ele=isDisplayed(driver, FindElement(driver, xpath, label, action.BOOLEAN, 10), "visibility", 10, label);
	return ele;
}

/**
 * @author Azhar Alam
 * @param projectName
 * @param labelWithValue
 * @param timeOut
 * @return true if custom object created successfully
 */
public boolean createCustomObject(String projectName,String[][] labelWithValue,int timeOut) {
	WebElement ele;
	String label;
	String value;
	boolean flag=false;
		switchToDefaultContent(driver);
		switchToFrame(driver, 20, getSetUpPageIframe(60));
		for (String[] lv : labelWithValue) {
			label=lv[0];
			value=lv[1];
			ele =  getLabelInputBoxwithCommonXpath(projectName, label, 20);
			ThreadSleep(2000);
			if (sendKeys(driver, ele, value, label, action.BOOLEAN)) {
				log(LogStatus.INFO, "Able to enter "+label, YesNo.No);
				ThreadSleep(2000);
				flag=true;
			} else {
				log(LogStatus.ERROR, "Not Able to enter "+value+" to label "+label, YesNo.Yes);
				sa.assertTrue(false,"Not Able to enter "+value+" to label "+label);
			}
		}
		flag=false;
		if (click(driver,  getCustomTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.ERROR, "Click on save Button ", YesNo.No);
			ThreadSleep(5000);
			flag=true;
		} else {
			log(LogStatus.ERROR, "Not Able to Click on save Button ", YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on save Button ");
		}
	
	return flag;
}

/**
 * @author Azhar Alam
 * @param environment
 * @param mode
 * @param projectName
 * @param objectName
 * @param ObjecttoAddedOnTab
 * @param styleType
 * @param parentId
 * @return true if object added to Tab
 */
public boolean addObjectToTab(String environment, String mode,String projectName,object objectName, String ObjecttoAddedOnTab,String styleType,String parentId) {
	WebElement ele=null;
	boolean flag=false;
	if(searchStandardOrCustomObject(environment, mode, objectName)) {
		log(LogStatus.PASS, "object searched : "+objectName.toString(), YesNo.No);
		ThreadSleep(5000);
		switchToDefaultContent(driver);
		switchToFrame(driver, 20, getSetUpPageIframe(120));
		if (click(driver, getCustomObjectTabNewBtn(120), "New", action.BOOLEAN)) {
			log(LogStatus.PASS, "clicked on new button ", YesNo.No);
			ThreadSleep(5000);
			switchToDefaultContent(driver);
			switchToFrame(driver, 20, getSetUpPageIframe(120));
			if (selectVisibleTextFromDropDown(driver, getObjectDropDown(120), ObjecttoAddedOnTab, ObjecttoAddedOnTab)) {
				log(LogStatus.INFO, "selected OBJECT : "+ObjecttoAddedOnTab, YesNo.No);

				if (tabStyleSelector(styleType,parentId)) {
					log(LogStatus.INFO, "selected style : "+styleType, YesNo.No);
					ThreadSleep(1000);		
					
					for (String window : driver.getWindowHandles()) {
						if (!window.equals(parentId)) {
							try {
								driver.switchTo().window(window);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
					
					switchToDefaultContent(driver);
					switchToFrame(driver, 20, getSetUpPageIframe(120));
					if(click(driver, getCustomFieldNextBtn2(10),"next button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "Clicked on Next button", YesNo.No);
						ThreadSleep(1000);		
						switchToDefaultContent(driver);
						switchToFrame(driver, 20, getSetUpPageIframe(120));
						if(click(driver, getCustomFieldNextBtn2(120),"next button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "Clicked on Next button", YesNo.No);
							ThreadSleep(1000);			
							switchToDefaultContent(driver);
							switchToFrame(driver, 20, getSetUpPageIframe(120));
							if (click(driver,  getCustomTabSaveBtn(projectName, 120), "save button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.ERROR, "Click on save Button & succeessfully add "+ObjecttoAddedOnTab+" to tab", YesNo.No);
								ThreadSleep(5000);
								flag = true;
							} else {
								log(LogStatus.ERROR, "Not Able to Click on save Button so cannot add add custom object on Tab", YesNo.Yes);
								sa.assertTrue(false,"Not Able to Click on save Button so cannot add add custom object on Tab");
							}

						}else {
							log(LogStatus.FAIL, "Not able to click on next button so cannot add custom object on Tab",YesNo.Yes);
							sa.assertTrue(false,"Not able to click on next button so cannot add custom object on Tab");
						}

					}else {
						log(LogStatus.FAIL, "Not able to click on next button so cannot add custom object on Tab",YesNo.Yes);
						sa.assertTrue(false,"Not able to click on next button so cannot add custom object on Tab");
					}

				} else {
					log(LogStatus.ERROR, "not able to select style : "+styleType, YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "not able to select OBJECT : "+objectName, YesNo.Yes);
			}

		} else {
			log(LogStatus.FAIL, "Not able to click on new button so cannot add custom object on Tab", YesNo.Yes);

		}


	}else {
		log(LogStatus.FAIL, "Not able to search object "+objectName.toString()+" so cannot add custom object on Tab", YesNo.Yes);
	}
	switchToDefaultContent(driver);
	return flag;
}

/**
 * @author Azhar Alam
 * @param styleType
 * @param parentId
 * @return true if able to select style for tab
 */
public boolean tabStyleSelector(String styleType,String parentId) {
	boolean flag = false;
	boolean windowFlag=false;
	if(click(driver,getTabObjectLookUpIcon(30), "tab Style look up icon", action.BOOLEAN)) {
		WebElement ele=null;
		String currentWindow=driver.getWindowHandle();
		log(LogStatus.INFO, "Tab window "+currentWindow,YesNo.No);
		log(LogStatus.INFO, "First window "+parentId,YesNo.No);
		for (String window : driver.getWindowHandles()) {
			if (!window.equals(currentWindow) && !window.equals(parentId)) {
				driver.switchTo().window(window);
				windowFlag=true;
			}
		}
		
		if(windowFlag) {
			try {
				ele= FindElement(driver, "//*[text()='"+styleType+"']",styleType, action.SCROLLANDBOOLEAN, 20);
				ele=isDisplayed(driver,ele,"visibility", 20,styleType);
				if(click(driver, ele, styleType, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "selected "+styleType,YesNo.No);
					flag=true;
				}else {
					log(LogStatus.ERROR, "cannot select "+styleType,YesNo.Yes);
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			flag=true;
			//driver.close();
			try {
				driver.switchTo().window(currentWindow);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			log(LogStatus.ERROR, "No new window is open so cannot select value "+styleType+" from look up",YesNo.Yes);
		}

	}else {
		log(LogStatus.ERROR, "Not able to click on tab Style look up icon",YesNo.Yes);
	}
	return flag;
}


/**
 * @author Azhar Alam
 * @param projectName
 * @param attachmentPath
 * @param imgName
 * @return true if 	upload/update img
 */
public boolean updateEdgeIcon(String projectName,String attachmentPath,String imgName) {
	boolean flag=false;
	attachmentPath=attachmentPath+imgName;
	if (click(driver, getImgClearButton(projectName, 10),"Img Clear button", action.BOOLEAN)) {
		log(LogStatus.INFO,"Click on Img Clear Button",YesNo.No);
		ThreadSleep(5000);		
		
		if (sendKeys(driver, getuploadPhotoButton(projectName, 10),attachmentPath,attachmentPath,action.BOOLEAN)) {
			ThreadSleep(500);
			log(LogStatus.INFO,"send value to "+attachmentPath,YesNo.No);
			if (click(driver, getCustomTabSaveBtn(projectName, 10)," Save Button", action.BOOLEAN)) {
				log(LogStatus.INFO,"Click on Save Button",YesNo.No);
				ThreadSleep(5000);	
				flag=true;
//				String xpath = "//div[@aria-labelledby='appImageLabel']//span[text()='"+imgName+"']";
//				WebElement ele= FindElement(driver, xpath, imgName, action.BOOLEAN, 30);
//				if (ele!=null) {
//					log(LogStatus.INFO,"File uploaded successfully "+imgName,YesNo.No);
//					flag=true;
//				} else {
//					sa.assertTrue(false, "File not uploaded "+imgName);
//					log(LogStatus.FAIL,"File not uploaded "+imgName,YesNo.Yes);
//				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Save Button");
				log(LogStatus.FAIL,"Not Able to Click on Save Button",YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to send value to "+attachmentPath);
			log(LogStatus.FAIL,"Not Able to send value to "+attachmentPath,YesNo.Yes);
		}
		
	} else {
		sa.assertTrue(false, "Not Able to Click on Img Clear Button");
		log(LogStatus.FAIL,"Not Able to Click on Img Clear Button",YesNo.Yes);
	}
	return flag;
	
}


/**
 * @author Azhar Alam
 * @param driver
 * @param userName
 * @param LabelswithCheck
 * @param timeOut
 * @return true if able to change permission for particular object for particular type for particular user
 */
public boolean permissionChangeForUserONObject(WebDriver driver,String userName,String[][] LabelswithCheck,int timeOut) {

	ThreadSleep(10000);
	switchToDefaultContent(driver);
	switchToFrame(driver, 60, getSetUpPageIframe(60));
	boolean flag=false;;
	String xpath="";
	xpath="//th//a[text()='"+userName+"']";
	WebElement ele=FindElement(driver, xpath,userName, action.SCROLLANDBOOLEAN, timeOut);
	ele=isDisplayed(driver, ele, "visibility", timeOut, userName);
	if (click(driver, ele, userName.toString(), action.BOOLEAN)) {
		log(LogStatus.INFO, "able to click on "+userName, YesNo.No);
		ThreadSleep(10000);
		switchToDefaultContent(driver);
		ThreadSleep(5000);
		switchToFrame(driver, 60, getSetUpPageIframe(60));
		xpath="//*[@id='topButtonRow']//input[@name='edit']";
		ele=FindElement(driver, xpath, "Edit Button", action.SCROLLANDBOOLEAN, timeOut);
		ThreadSleep(5000);
		if (click(driver, ele, "Edit Button", action.BOOLEAN)) {
			log(LogStatus.INFO, "able to click on edit button", YesNo.No);
			ThreadSleep(10000);
			switchToDefaultContent(driver);
			ThreadSleep(5000);
			switchToFrame(driver, 60, getSetUpPageIframe(60));
			String OnObject="";
			String permission = "";
			for (String[] strings : LabelswithCheck) {
				OnObject=strings[0];
				permission=strings[1];
				xpath="//*[text()='"+OnObject+"']/following-sibling::*//td/input[contains(@title,'"+permission+"')]";
				ele = FindElement(driver, xpath, OnObject+" with permission "+permission, action.SCROLLANDBOOLEAN, timeOut);
			//	if (!isSelected(driver, ele, OnObject+" with permission "+permission)) {
					if (click(driver, ele, OnObject+" with permission "+permission,action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on checkbox "+permission+" for "+OnObject, YesNo.No);

					} else {
						log(LogStatus.ERROR, "Not Able clicked on checkbox "+permission+" for "+OnObject, YesNo.Yes);
						sa.assertTrue(false, permission+ " permission not change for "+userName+" on object "+OnObject);
						log(LogStatus.FAIL,permission+ " permission not change for "+userName+" on object "+OnObject,YesNo.Yes);

					}
			//	}
			}

			if (click(driver, getCreateUserSaveBtn_Lighting(30), "Save Button",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on save button for record type settiing", YesNo.No);
				ThreadSleep(10000);
				flag=true;
				ThreadSleep(5000);
			} else {
				log(LogStatus.ERROR, "not able to click on save button for record type settiing", YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "not able to click on edit button", YesNo.Yes);
		}

	} else {
		log(LogStatus.ERROR, "Not able to click on "+userName, YesNo.Yes);
	}
	return flag;
}

/**
 * @author Azhar Alam
 * @param driver
 * @param userLastName
 * @param userFirstName
 * @param timeOut
 * @return true if able to click on Edit button for CRM User
 */
public boolean clickOnEditBtnForCRMUser(WebDriver driver,String userLastName,String userFirstName,int timeOut) {
	boolean flag=false;
	switchToDefaultContent(driver);
	ThreadSleep(10000);
	switchToFrame(driver, 60, getSetUpPageIframe(60));
	WebElement ele = FindElement(driver,
			"//a[text()='" + userLastName + "," + " " + userFirstName+ "']",userLastName+", "+userFirstName, action.SCROLLANDBOOLEAN, 10);
	if (ele != null) {
		if (click(driver, ele, userLastName+", "+userFirstName, action.SCROLLANDBOOLEAN)) {
			ThreadSleep(10000);
			switchToFrame(driver, 40, getSetUpPageIframe(40));
			ele=getEditButton("", "Classic", timeOut);
			if (click(driver, ele, "Edit icon", action.SCROLLANDBOOLEAN)) {
				switchToDefaultContent(driver);
				ThreadSleep(10000);
				switchToFrame(driver, 20, getSetUpPageIframe(20));
				flag=true;
			} else {
				appLog.info("Not able to click on edit icon");
			}

		} else {
			appLog.info("Not able to click on "+userLastName+", "+userFirstName);
		}
	} else {
		appLog.info("Element is not present");
	}
	return flag;
}

/**
 * @author Akul Bhutani
 * @param projectName
 * @param object
 * @param at
 * @return drop down object
 */
public WebElement selectObjectDropdownOnSharingSettings(String projectName, String object,accessType at) {
	object=object.replace("_", " ");
	int i=0;
	if (at==accessType.InternalUserAccess)
		i=1;
	else if(at==accessType.ExternalUserAccess)
		i=2;
	String xpath = "(//*[text()='"+object+"']/following-sibling::*//select)["+i+"]";
	WebElement ele=FindElement(driver, xpath,"dropdown", action.SCROLLANDBOOLEAN, 10);
	return isDisplayed(driver, ele, "visibility", 10, "dropdown");
}

/**
 * @author Azhar Alam
 * @param driver
 * @param labelsWithValues
 * @param timeOut
 * @return true if global action created successfully
 */
public boolean createNewAction(WebDriver driver,String[][] labelsWithValues,int timeOut) {
	boolean flag=false;
	switchToDefaultContent(driver);
	ThreadSleep(10000);
	switchToFrame(driver, 60, getSetUpPageIframe(60));
	ThreadSleep(5000);
	if (click(driver, getNewActionBtnNewBtn(timeOut), "New Action Button", action.BOOLEAN)) {
		log(LogStatus.INFO, "click on New Action Button", YesNo.No);
		switchToDefaultContent(driver);
		ThreadSleep(10000);
		switchToFrame(driver, 60, getSetUpPageIframe(60));
		ThreadSleep(5000);
		String label="";
		String value="";
		String xpath="";
		WebElement ele;
		for (String[] labelWithValue : labelsWithValues) {
			label=labelWithValue[0].replace("_", " ");
			value=labelWithValue[1];
			xpath = "//*[text()='"+label+"']/../following-sibling::td//input";
			ele = FindElement(driver, xpath, label, action.BOOLEAN, 10);
			if (ele!=null) {
				log(LogStatus.INFO, "Label not found : "+label, YesNo.No);
				if (sendKeys(driver, ele, value, label, action.BOOLEAN)) {
					log(LogStatus.INFO, "enter value "+value+" to Label : "+label, YesNo.No);
					flag=true;
				} else {
					log(LogStatus.ERROR, "Not Able to enter value "+value+" to Label : "+label, YesNo.Yes);
					sa.assertTrue(false, "Not Able to enter value "+value+" to Label : "+label);
				}
			} else {
				log(LogStatus.ERROR, "Label not found : "+label, YesNo.Yes);
				sa.assertTrue(false, "Label not found : "+label);
			}
		}

		if (click(driver, getSaveButton(20), "Save Button",action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Click on Save Button ", YesNo.No);

		} else {
			log(LogStatus.ERROR, "Not Able to Click on Save Button", YesNo.Yes);
			sa.assertTrue(false, "Not Able to Click on Save Button");
		}


	} else {
		log(LogStatus.ERROR, "Not able to click on New Action Button", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on New Action Button");
	}
	return flag;
}


/**
 * @author Azhar Alam
 * @param driver
 * @param actionName
 * @param labelsWithValues
 * @param timeOut create predefined value for Global Action
 */
public void createPredefinedValueForGlobalAction(WebDriver driver,String actionName,String[][] labelsWithValues,int timeOut) {
	boolean flag=false;
	switchToDefaultContent(driver);
	ThreadSleep(10000);
	switchToFrame(driver, 60, getSetUpPageIframe(60));
	ThreadSleep(5000);
	String xpath="";
	WebElement ele;
	String label="";
	String value="";
	xpath = "//a[text()='"+actionName+"']";
	ele= FindElement(driver, xpath, actionName, action.SCROLLANDBOOLEAN, timeOut);
	if (click(driver, ele, actionName, action.BOOLEAN)) {
		log(LogStatus.INFO, "click on "+actionName, YesNo.No);
		for (String[] labelWithValue : labelsWithValues) {

			switchToDefaultContent(driver);
			ThreadSleep(20000);
			switchToFrame(driver, 60, getSetUpPageIframe(60));
			ThreadSleep(5000);
			label=labelWithValue[0].replace("_", " ");
			value=labelWithValue[1];
			log(LogStatus.INFO, "Going to fill value : "+value+" for label : "+label, YesNo.No);
			if (click(driver, getpredefinedFieldValuesNewButtonn(timeOut), "Predefined Field Values New Button", action.BOOLEAN)) {
				log(LogStatus.INFO, "click on Predefined Field Values New Button", YesNo.No);

				switchToDefaultContent(driver);
				ThreadSleep(20000);
				switchToFrame(driver, 60, getSetUpPageIframe(60));
				ThreadSleep(5000);
				
				if (click(driver, getSelectFieldName(10), label, action.SCROLLANDBOOLEAN)/*selectVisibleTextFromDropDown(driver,getSelectFieldName(10), label,label)*/) {
					log(LogStatus.INFO, "selected visbible text from the Field Name dropdown "+label, YesNo.No);
					ThreadSleep(2000);
					xpath="//select[@id='ColumnEnumOrId']//*[text()='"+label+"']";
					ele = FindElement(driver, xpath, label, action.SCROLLANDBOOLEAN, timeOut);
					click(driver, ele, label, action.SCROLLANDBOOLEAN);
					ThreadSleep(2000);
					if (sendKeys(driver, getFormulaValueTextArea(20), value, "Formula Field : "+value, action.BOOLEAN)) {
						log(LogStatus.INFO, "enter value for "+label, YesNo.Yes);

						if (click(driver, getSaveButton(20), "Save Button",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Click on Save Button for  "+label, YesNo.No);
							
						} else {
							log(LogStatus.ERROR, "Not Able to Click on Save Button for  "+label, YesNo.Yes);
							sa.assertTrue(false, "Not Able to Click on Save Button for  "+label);
						}

					} else {
						log(LogStatus.ERROR, "Not Able to enter value for "+label, YesNo.Yes);
						sa.assertTrue(false, "Not Able to enter value for "+label);
					}



				} else {
					log(LogStatus.ERROR, "Not able to select visbible text from the Field Name dropdown "+label, YesNo.Yes);
					sa.assertTrue(false, "Not able to select visbible text from the Field Name dropdown "+label);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on Predefined Field Values New Button : "+actionName, YesNo.Yes);
				sa.assertTrue(false, "Not able to click on Predefined Field Values New Button : "+actionName);
			}

		}
		


	} else {
		log(LogStatus.ERROR, "Not able to click on "+actionName, YesNo.Yes);
		sa.assertTrue(false, "Not able to click on "+actionName);
	}
}

/**
 * @author Azhar Alam
 * @param projectName
 * @param labelWithValue
 * @param existingPageLayout
 * @param src
 * @param trgt
 * @param timeOut
 * @return true if page layout created successfully
 */
public boolean createPageLayout(String projectName,String[][] labelWithValue,String existingPageLayout,String src,String trgt,int timeOut) {
	WebElement ele;
	String label;
	String value;
	boolean flag=false;
	switchToDefaultContent(driver);
	if (click(driver,getPageLayoutNewButton(10), "Page Layout New Button", action.SCROLLANDBOOLEAN)) {
		log(LogStatus.INFO, "Click on Page Layout New Button", YesNo.No);
		ThreadSleep(5000);
		switchToFrame(driver, 20, getSetUpPageIframe(60));
		for (String[] lv : labelWithValue) {
			label=lv[0];
			value=lv[1];
			ele =  getRecordTypeLabel(projectName, "Page Layout Name", 20);
			if (sendKeys(driver, ele, value, label, action.BOOLEAN)) {
				log(LogStatus.INFO, "Able to enter "+label, YesNo.No);
				ThreadSleep(2000);
				flag=true;
			} else {
				log(LogStatus.ERROR, "Not Able to enter "+value+" to label "+label, YesNo.Yes);
				sa.assertTrue(false,"Not Able to enter "+value+" to label "+label);
			}
		}
		if(selectVisibleTextFromDropDown(driver, getSelectExistingPageLayout(10), "field accessbility drop down",existingPageLayout)) {
			log(LogStatus.INFO,"Select Existing Page Layout drop down "+existingPageLayout,YesNo.No);
			ThreadSleep(1000);

			if (click(driver,  getCustomTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.ERROR, "Click on save Button ", YesNo.No);
				ThreadSleep(5000);
				ThreadSleep(5000);
				switchToFrame(driver, 20, getSetUpPageIframe(60));
				if (dragDropOnPageLayout(src, trgt)) {
					log(LogStatus.INFO, "Able to dragNDrop "+src+" at "+trgt+" location", YesNo.No);
					flag=true;
				} else {
					log(LogStatus.ERROR, "Not able to dragNDrop "+src+" at "+trgt+" location", YesNo.Yes);
					sa.assertTrue(false,"Not able to dragNDrop "+src+" at "+trgt+" location");
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on save Button ", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on save Button ");
			}


		}else {
			log(LogStatus.ERROR,"Not able to select value from Existing Page Layout drop down "+existingPageLayout,YesNo.Yes);
		}

	} else {
		log(LogStatus.ERROR, "Not Able to Click on Page Layout New Button", YesNo.Yes);
		sa.assertTrue(false,"Not Able to Click on Page Layout New Button");
	}
	return flag;
}

/**
 * @author Azhar Alam
 * @param src
 * @param trgt
 * @return true if successfully drag n drop & save layout
 */
public boolean dragDropOnPageLayout(String src,String trgt){
	boolean flag=false;
	sendKeys(driver, getquickFindSearch(10), src, src, action.BOOLEAN);
	WebElement targetElement = FindElement(driver, "//span[@class='labelText'][text()='"+trgt+"']", "", action.BOOLEAN,20);
	WebElement ele = isDisplayed(driver, FindElement(driver, " //span[text()='"+src+"']", "", action.BOOLEAN,20), "visibility",20,src+" field");
	if(ele!=null) {
		WebElement ele1 = isDisplayed(driver, targetElement, "visibility",20,trgt+" field");
		ThreadSleep(5000);
		if(ele1!=null) {
			if(dragNDropField(driver, ele, ele1)) {
				ThreadSleep(5000);
				appLog.info("Successfully dragNDrop "+src+" at "+trgt+" location");
				if (FindElement(driver, "//span[@class='labelText'][text()='"+src+"']", "", action.BOOLEAN,20)!=null) {
					appLog.info("successfully verified drag and drop of "+src);
					if(click(driver, getPageLayoutSaveBtn(object.Apps,30), "page layouts save button", action.SCROLLANDBOOLEAN)) {
						appLog.info("clicked on save button");
						flag=true;
					}else {
						appLog.error("Not able to click on Save button cannot save pagelayout dragged object or section");
					}
				}
				else {
					appLog.error("Not able to dragNDrop "+src+" at "+trgt+" location");
				}
				appLog.info("Successfully dragNDrop "+src+" at "+trgt+" location");
			}else {
				appLog.error("Not able to dragNDrop "+src+" at "+trgt+" location");
			}
		}else {
			appLog.error(trgt+" location is not visible so cannot dragNDrop "+src+" at location "+trgt);
		}
	}else {
		appLog.error(src+" is not visible so cannot dragNdrop "+src);
	}
	return flag;
}

}
