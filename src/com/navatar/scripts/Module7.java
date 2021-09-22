package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.navatar.generic.BaseLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.GlobalActionPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.navatar.pageObjects.TaskPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;

public class Module7 extends BaseLib {
	String meetingNone = "--None--";
	String statusCompleted = "Completed";
	String statusInProgess = "In Progress";
	String statusInStarted = "Not Started";
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc001_CreateEntityAndContact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		String value="";
		String type="";
		String[][] EntityOrAccounts = {{ M7Ins1, M7Ins1RecordType ,null}};

		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, tabObj1)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
				value = accounts[0];
				type = accounts[1];
				if (ip.createEntityOrAccount(projectName, value, type, null, 20)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj1);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj1,YesNo.Yes);
			}
		}
		
		// contact
		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			M7Contact1EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, M7Contact1EmailID, "Contacts", excelLabel.Variable_Name, "M7CON1",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, M7Contact1FName, M7Contact1LName, M7Ins1, M7Contact1EmailID,M7Contact1RecordType, null, null, CreationPage.ContactPage, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+M7Contact1FName+" "+M7Contact1LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+M7Contact1FName+" "+M7Contact1LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+M7Contact1FName+" "+M7Contact1LName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
		
		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			M7Contact2EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, M7Contact2EmailID, "Contacts", excelLabel.Variable_Name, "M7CON2",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, M7Contact2FName, M7Contact2LName, M7Ins1, M7Contact2EmailID,M7Contact2RecordType, null, null, CreationPage.ContactPage, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+M7Contact2FName+" "+M7Contact2LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+M7Contact2FName+" "+M7Contact2LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+M7Contact2FName+" "+M7Contact2LName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
		
		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			M7Contact3EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, M7Contact3EmailID, "Contacts", excelLabel.Variable_Name, "M7CON3",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, M7Contact3FName, M7Contact3LName, M7Ins1, M7Contact3EmailID,M7Contact3RecordType, null, null, CreationPage.ContactPage, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+M7Contact3FName+" "+M7Contact3LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+M7Contact3FName+" "+M7Contact3LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+M7Contact3FName+" "+M7Contact3LName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
		
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M7Tc002_CreateStandardTaskForTomLathamAndVerifyLastTouchpointOnContactDetailPage(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String contactName=M7Contact2FName+" "+M7Contact2LName;
		M7Task1dueDate=yesterdaysDate;
		String task = M7Task1Subject;
		String[][] event1 = {{PageLabel.Subject.toString(),task},
				{PageLabel.Due_Date.toString(),M7Task1dueDate},
				{PageLabel.Name.toString(),contactName},
				{PageLabel.Meeting_Type.toString(),meetingNone},
				{PageLabel.Status.toString(),M7Task1Status}};

		if (gp.clickOnGlobalActionAndEnterValue(projectName, GlobalActionItem.New_Task, event1)) {
			log(LogStatus.INFO,"Able to Enter Value for : "+task,YesNo.No);
			ExcelUtils.writeData(phase1DataSheetFilePath,M7Task1dueDate, "Task1", excelLabel.Variable_Name, "M7Task1", excelLabel.Due_Date);
			if (click(driver, gp.getSaveButtonForEvent(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);		
				flag=true;
				
			}else {
				sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
			}
		} else {
			sa.assertTrue(false,"Not Able to Click/Enter Value : "+task);
			log(LogStatus.SKIP,"Not Able to Click/Enter Value : "+task,YesNo.Yes);	
		}
		if (flag) {
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					 ele=cp.getlastTouchPointValue(projectName, 10);
					String value="";
					if (ele!=null) {
						value=ele.getText().trim();
						if (value.isEmpty() || value.equals("")) {
							log(LogStatus.INFO,"Last Touch Point is Blank for "+contactName, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "Last Touch Point should be Blank for "+contactName, YesNo.Yes);
							sa.assertTrue(false,"Last Touch Point should be Blank for "+contactName );
						}
					}else {
						log(LogStatus.ERROR, "last touch point value is not visible For : "+contactName, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not visible For : "+contactName );
					}
				} else {
					sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
			}	
		} else {
			sa.assertTrue(false,"Task is not created so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not created so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M7Tc003_CreateMultiTaggedTaskforContactJamesRoseAndVerifyLastTouchPoint(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact3FName+" "+M7Contact3LName;
		String secondaryContact=M7Contact1FName+" "+M7Contact1LName;
		M7Task2dueDate=previousOrForwardDate(2, "M/d/YYYY");;
		String task = M7Task2Subject;
		String[][] event1 = {
				{PageLabel.Name.toString(),secondaryContact},
				{PageLabel.Subject.toString(),task},
				{PageLabel.Due_Date.toString(),M7Task2dueDate},
				{PageLabel.Meeting_Type.toString(),meetingNone},
				{PageLabel.Status.toString(),M7Task2Status}};

		
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					ExcelUtils.writeData(phase1DataSheetFilePath,M7Task2dueDate, "Task1", excelLabel.Variable_Name, "M7Task2", excelLabel.Due_Date);
					///////////////////////
					ele = lp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.New_Task, 10);
					if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task.toString(), action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.New_Task.toString(),  YesNo.Yes);
						ThreadSleep(1000);
						
						ele = lp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), true, primaryContact, action.SCROLLANDBOOLEAN, 10);
						if (ele!=null) {
							log(LogStatus.INFO, "successfully verified presence of "+primaryContact+" in name field",YesNo.No);
						} else {
							sa.assertTrue(false,"not found "+ primaryContact+" For Label "+PageLabel.Name);
							log(LogStatus.SKIP,"not found "+ primaryContact+" For Label "+PageLabel.Name,YesNo.Yes);

						}
						
						gp.enterValueForTask(projectName, PageName.Object2Page, event1, 20);
						String relatedValue=M7Ins1;
						flag = lp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), TabName.Object1Tab, relatedValue, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.INFO,"Selected "+relatedValue+" For Label "+PageLabel.Related_Associations,YesNo.No);

						} else {
							sa.assertTrue(false,"Not Able to Select "+relatedValue+" For Label "+PageLabel.Related_Associations);
							log(LogStatus.SKIP,"Not Able to Select "+relatedValue+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

						}
						
						if (clickUsingJavaScript(driver, gp.getCustomTabSaveBtn(projectName, 2), "Save Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
							ThreadSleep(5000);
							flag=true;
							
						}else {
							sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
							log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
						}
						
					}
					else {
						log(LogStatus.ERROR, "not able to click on "+ActivityTimeLineItem.New_Task.toString(), YesNo.Yes);
						sa.assertTrue(false,"not able to click on "+ActivityTimeLineItem.New_Task.toString() );
					}
					//////////////////////
					//refresh(driver);
					ele = lp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Refresh, 10);
					click(driver, ele, ActivityTimeLineItem.Refresh.toString(), action.BOOLEAN);
					ThreadSleep(3000);
					ele=cp.getlastTouchPointValue(projectName, 10);
					String value="";
					if (ele!=null) {
						value=ele.getText().trim();
						if (value.isEmpty() || value.equals("")) {
							log(LogStatus.INFO,"Last Touch Point is Blank for "+primaryContact, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "Last Touch Point should be Blank for "+primaryContact, YesNo.Yes);
							sa.assertTrue(false,"Last Touch Point should be Blank for "+primaryContact );
						}
					}else {
						log(LogStatus.ERROR, "last touch point value is not visible For : "+primaryContact, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not visible For : "+primaryContact );
					}
				} else {
					sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
			}	
			
			
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+secondaryContact,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, secondaryContact, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+secondaryContact+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					
					ele=cp.getlastTouchPointValue(projectName, 10);
					String value="";
					if (ele!=null) {
						value=ele.getText().trim();
						if (value.isEmpty() || value.equals("")) {
							log(LogStatus.INFO,"Last Touch Point is Blank for "+secondaryContact, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "Last Touch Point should be Blank for "+secondaryContact, YesNo.Yes);
							sa.assertTrue(false,"Last Touch Point should be Blank for "+secondaryContact );
						}
					}else {
						log(LogStatus.ERROR, "last touch point value is not visible For : "+secondaryContact, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not visible For : "+secondaryContact );
					}
				} else {
					sa.assertTrue(false,"Item Not Found : "+secondaryContact+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+secondaryContact+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+secondaryContact);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+secondaryContact,YesNo.Yes);
			}	
			
			
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M7Tc004_UpdateStatusToCompletedInStandardTask(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
		boolean flag=false;
		TabName[] tabNames = {TabName.TaskTab};
		String[] taskNames = {M7Task1Subject};
		PageName[] pageNames = {PageName.TaskPage};
		
		TabName tabName ;
		int i=0;
		for (String task : taskNames) {
			task=taskNames[i];
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+task,YesNo.No);
				ele=tp.getTaskNameLinkInSideMMenu(projectName, task, 30);
				if (click(driver, ele, task, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on "+task+" on Task SideMenu", YesNo.No);	
					if (click(driver, tp.getEditButton(projectName, 30), task, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button For : "+task, YesNo.No);	
						if (tp.selectDropDownValueonTaskPopUp(projectName, pageNames[i], PageLabel.Status.toString(), statusCompleted, action.BOOLEAN, 10)) {
							log(LogStatus.INFO, "Selected : "+statusCompleted+" For Label : "+PageLabel.Status.toString(), YesNo.No);	
							if (tp.selectDropDownValueonTaskPopUp(projectName, pageNames[i], PageLabel.Meeting_Type.toString(), M7Task1MeetingType, action.BOOLEAN, 10)) {
								log(LogStatus.INFO, "Selected : "+M7Task1MeetingType+" For Label : "+PageLabel.Meeting_Type.toString(), YesNo.No);	
								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"successfully Updated task : "+task,  YesNo.No);
									ThreadSleep(5000);
									flag=true;
									String[][] fieldsWithValues= {{PageLabel.Status.toString(),statusCompleted},
											{PageLabel.Meeting_Type.toString(),M7Task1MeetingType}};
									tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 30);	
								}else {
									log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+task, YesNo.Yes);
									sa.assertTrue(false,"save button is not clickable so task not Updated : "+task );
								}
							}else {
								log(LogStatus.ERROR, "Not Able to Select : "+M7Task1MeetingType+" For Label : "+PageLabel.Meeting_Type.toString(), YesNo.Yes);	
								BaseLib.sa.assertTrue(false, "Not Able to Select : "+M7Task1MeetingType+" For Label : "+PageLabel.Meeting_Type.toString());	
							}
						}else {
							log(LogStatus.ERROR, "Not Able to Select : "+statusCompleted+" For Label : "+PageLabel.Status.toString(), YesNo.Yes);	
							BaseLib.sa.assertTrue(false, "Not Able to Select : "+statusCompleted+" For Label : "+PageLabel.Status.toString());	
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Edit Button For : "+task, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Edit Button For : "+task);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+task+" on Task SideMenu", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+task+" on Task SideMenu");
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+task,YesNo.Yes);
			}
			i++;
			switchToDefaultContent(driver);
			ThreadSleep(5000);
		}
		String contactName=M7Contact2FName+" "+M7Contact2LName;
		if (flag) {
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					 ele=cp.getlastTouchPointValue(projectName, 10);
					if (ele!=null) {
						String value=ele.getText().trim();
						if (cp.verifyDate(M7Task1dueDate, value)) {
							log(LogStatus.INFO,M7Task1dueDate+" successfully verified last touch point date For : "+contactName, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, M7Task1dueDate+" last touch point value is not matched For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,M7Task1dueDate+" last touch point value is not matched For : "+contactName );
						}
					}else {
						log(LogStatus.ERROR, "last touch point value is not visible For : "+contactName, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not visible For : "+contactName );
					}
				} else {
					sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
			}	
		} else {
			sa.assertTrue(false,"Task is not been updated so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc005_UpdateStatusToCompletedInMultiTaggedTask(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
		boolean flag=false;
		TabName[] tabNames = {TabName.TaskTab};
		String[] taskNames = {M7Task2Subject};
		PageName[] pageNames = {PageName.Object2Page};
		
		TabName tabName ;
		int i=0;
		for (String task : taskNames) {
			task=taskNames[i];
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+task,YesNo.No);
				ele=tp.getTaskNameLinkInSideMMenu(projectName, task, 30);
				if (click(driver, ele, task, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on "+task+" on Task SideMenu", YesNo.No);	
					if (click(driver, tp.getEditButton(projectName, 30), task, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button For : "+task, YesNo.No);	
						if (tp.selectDropDownValueonTaskPopUp(projectName, pageNames[i], PageLabel.Status.toString(), statusCompleted, action.BOOLEAN, 10)) {
							log(LogStatus.INFO, "Selected : "+statusCompleted+" For Label : "+PageLabel.Status.toString(), YesNo.No);	
							if (tp.selectDropDownValueonTaskPopUp(projectName, pageNames[i], PageLabel.Meeting_Type.toString(), M7Task2MeetingType, action.BOOLEAN, 10)) {
								log(LogStatus.INFO, "Selected : "+M7Task2MeetingType+" For Label : "+PageLabel.Meeting_Type.toString(), YesNo.No);	
								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"successfully Updated task : "+task,  YesNo.No);
									ThreadSleep(5000);
									flag=true;
									String[][] fieldsWithValues= {{PageLabel.Status.toString(),statusCompleted},
											{PageLabel.Meeting_Type.toString(),M7Task2MeetingType}};
									tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 30);	
								}else {
									log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+task, YesNo.Yes);
									sa.assertTrue(false,"save button is not clickable so task not Updated : "+task );
								}
							}else {
								log(LogStatus.ERROR, "Not Able to Select : "+M7Task2MeetingType+" For Label : "+PageLabel.Meeting_Type.toString(), YesNo.Yes);	
								BaseLib.sa.assertTrue(false, "Not Able to Select : "+M7Task2MeetingType+" For Label : "+PageLabel.Meeting_Type.toString());	
							}
						}else {
							log(LogStatus.ERROR, "Not Able to Select : "+statusCompleted+" For Label : "+PageLabel.Status.toString(), YesNo.Yes);	
							BaseLib.sa.assertTrue(false, "Not Able to Select : "+statusCompleted+" For Label : "+PageLabel.Status.toString());	
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Edit Button For : "+task, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Edit Button For : "+task);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+task+" on Task SideMenu", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+task+" on Task SideMenu");
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+task,YesNo.Yes);
			}
			i++;
			switchToDefaultContent(driver);
			ThreadSleep(5000);
		}
		String[] contactNames={M7Contact1FName+" "+M7Contact1LName,M7Contact3FName+" "+M7Contact3LName};
		String contactName=null;
		if (flag) {
			for (int j = 0; j < contactNames.length; j++) {
				contactName=contactNames[j];
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						 ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							String value=ele.getText().trim();
							if (cp.verifyDate(M7Task2dueDate, value)) {
								log(LogStatus.INFO,M7Task2dueDate+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, M7Task2dueDate+" last touch point value is not matched For : "+contactName, YesNo.Yes);
								sa.assertTrue(false,M7Task2dueDate+" last touch point value is not matched For : "+contactName );
							}
						}else {
							log(LogStatus.ERROR, "last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,"last touch point value is not visible For : "+contactName );
						}
					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			}
			
		} else {
			sa.assertTrue(false,"Task is not been updated so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc006_CreateMultiTaggedTaskforContactTomLathamAndVerifyLastTouchPoint(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact2FName+" "+M7Contact2LName;
		String secondaryContact=M7Contact1FName+" "+M7Contact1LName;
		M7Task3dueDate=previousOrForwardDate(-5, "M/d/YYYY");;
		String task = M7Task3Subject;
		String actualValue=null;
		String expectedValue = null;
		String[][] event1 = {
				{PageLabel.Name.toString(),secondaryContact},
				{PageLabel.Subject.toString(),task},
				{PageLabel.Due_Date.toString(),M7Task3dueDate},
				{PageLabel.Meeting_Type.toString(),M7Task3MeetingType},
				{PageLabel.Status.toString(),M7Task3Status}};


		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				ThreadSleep(2000);
				ExcelUtils.writeData(phase1DataSheetFilePath,M7Task3dueDate, "Task1", excelLabel.Variable_Name, "M7Task3", excelLabel.Due_Date);
				///////////////////////
				ele = lp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.New_Task, 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task.toString(), action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.New_Task.toString(),  YesNo.Yes);
					ThreadSleep(1000);
					ele = lp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), true, primaryContact, action.SCROLLANDBOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO, "successfully verified presence of "+primaryContact+" in name field",YesNo.No);
					} else {
						sa.assertTrue(false,"not found "+ primaryContact+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"not found "+ primaryContact+" For Label "+PageLabel.Name,YesNo.Yes);

					}
					gp.enterValueForTask(projectName, PageName.Object2Page, event1, 20);
					if (clickUsingJavaScript(driver, gp.getCustomTabSaveBtn(projectName, 2), "Save Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
						ThreadSleep(5000);
						flag=true;

					}else {
						sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
						log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
					}

				}
				else {
					log(LogStatus.ERROR, "not able to click on "+ActivityTimeLineItem.New_Task.toString(), YesNo.Yes);
					sa.assertTrue(false,"not able to click on "+ActivityTimeLineItem.New_Task.toString() );
				}
			
				ele = lp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Refresh, 10);
				click(driver, ele, ActivityTimeLineItem.Refresh.toString(), action.BOOLEAN);
				ThreadSleep(3000);
				ele=cp.getlastTouchPointValue(projectName, 10);
				expectedValue = M7Task1dueDate;
				if (ele!=null) {
					actualValue=ele.getText().trim();
					if (cp.verifyDate(expectedValue, actualValue)) {
						log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+primaryContact, YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "Last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue );
					}
				}else {
					log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+primaryContact, YesNo.Yes);
					sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+primaryContact );
				}
			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	


		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+secondaryContact,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, secondaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+secondaryContact+" For : "+tabObj2,YesNo.No);
				ThreadSleep(2000);
				ele=cp.getlastTouchPointValue(projectName, 10);
				expectedValue = M7Task2dueDate;
				if (ele!=null) {
					actualValue=ele.getText().trim();
					if (cp.verifyDate(expectedValue, actualValue)) {
						log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+secondaryContact, YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "Last touch point value is not matched For : "+secondaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not matched For : "+secondaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue );
					}
				}else {
					log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+secondaryContact, YesNo.Yes);
					sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+secondaryContact );
				}
			} else {
				sa.assertTrue(false,"Item Not Found : "+secondaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+secondaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+secondaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+secondaryContact,YesNo.Yes);
		}	
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc007_ChangeTheStausFromCompletedtoInProgressAndVerifyTheImpactOnTheLastTouchPointDate(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact3FName+" "+M7Contact3LName;
		String task = M7Task2Subject;

		PageName pageName = PageName.Object2Page;

		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					if (click(driver, tp.getEditButton(projectName, 30), task, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button For : "+task, YesNo.No);	
						if (tp.selectDropDownValueonTaskPopUp(projectName, pageName, PageLabel.Status.toString(), statusInProgess, action.BOOLEAN, 10)) {
							log(LogStatus.INFO, "Selected : "+statusInProgess+" For Label : "+PageLabel.Status.toString(), YesNo.No);	
							if (clickUsingJavaScript(driver, lp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"successfully Updated task : "+task,  YesNo.No);
								ThreadSleep(5000);
								flag=true;
								String[][] fieldsWithValues= {{PageLabel.Status.toString(),statusInProgess}};
								tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 30);	
							}else {
								log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+task, YesNo.Yes);
								sa.assertTrue(false,"save button is not clickable so task not Updated : "+task );
							}

						}else {
							log(LogStatus.ERROR, "Not Able to Select : "+statusInProgess+" For Label : "+PageLabel.Status.toString(), YesNo.Yes);	
							BaseLib.sa.assertTrue(false, "Not Able to Select : "+statusInProgess+" For Label : "+PageLabel.Status.toString());	
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Edit Button For : "+task, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Edit Button For : "+task);
					}

				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	
		
		String actualValue=null;
		String expectedValue = null;
		String[] contactNames={M7Contact1FName+" "+M7Contact1LName,M7Contact2FName+" "+M7Contact2LName};
		String contactName=null;
		if (flag) {
			for (int j = 0; j < contactNames.length; j++) {
				contactName=contactNames[j];
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						if (j==0) {
							expectedValue = M7Task3dueDate;
						} else {
							expectedValue = M7Task1dueDate;
						}
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}
						
					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			}
			
		} else {
			sa.assertTrue(false,"Task is not been updated so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc008_DeleteMATouchpointTask_1AndVerifyImpactOnLastTouchPointInContactPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact2FName+" "+M7Contact2LName;
		String task = M7Task1Subject;
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					if (click(driver, cp.getDeleteButton(projectName, 20), "Delete Button", action.BOOLEAN)) {
						log(LogStatus.INFO,"Able to Click on Delete button : "+task,YesNo.No);
						ThreadSleep(2000);
						if (click(driver, cp.getDeleteButtonPopUp(projectName, 20), "Delete Button", action.BOOLEAN)) {
							log(LogStatus.INFO,"Able to Click on Delete button on PopUp : "+task,YesNo.No);
							ThreadSleep(2000);
							flag=true;
						} else {
							sa.assertTrue(false,"Able to Click on Delete button on PopUp : "+task);
							log(LogStatus.SKIP,"Able to Click on Delete button on PopUp : "+task,YesNo.Yes);
						}
					} else {
						sa.assertTrue(false,"Not Able to Click on Delete button : "+task);
						log(LogStatus.SKIP,"Not Able to Click on Delete button : "+task,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	

		String actualValue=null;
		String expectedValue = null;
		String[] contactNames={primaryContact};
		String contactName=null;
		if (flag) {
			for (int j = 0; j < contactNames.length; j++) {
				contactName=contactNames[j];
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						expectedValue=M7Task3dueDate;
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			}

		} else {
			sa.assertTrue(false,"Task is not been deleted so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been deleted so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc009_UpdateTheMATouchpointTask_2TaskStatusAndVerifyTheImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact3FName+" "+M7Contact3LName;
		String task = M7Task2Subject;

		PageName pageName = PageName.Object2Page;

		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					if (click(driver, tp.getEditButton(projectName, 30), task, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button For : "+task, YesNo.No);	
						if (tp.selectDropDownValueonTaskPopUp(projectName, pageName, PageLabel.Status.toString(), statusCompleted, action.BOOLEAN, 10)) {
							log(LogStatus.INFO, "Selected : "+statusCompleted+" For Label : "+PageLabel.Status.toString(), YesNo.No);	
							if (clickUsingJavaScript(driver, lp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"successfully Updated task : "+task,  YesNo.No);
								ThreadSleep(5000);
								flag=true;
								String[][] fieldsWithValues= {{PageLabel.Status.toString(),statusCompleted}};
								tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 30);	
							}else {
								log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+task, YesNo.Yes);
								sa.assertTrue(false,"save button is not clickable so task not Updated : "+task );
							}

						}else {
							log(LogStatus.ERROR, "Not Able to Select : "+statusCompleted+" For Label : "+PageLabel.Status.toString(), YesNo.Yes);	
							BaseLib.sa.assertTrue(false, "Not Able to Select : "+statusCompleted+" For Label : "+PageLabel.Status.toString());	
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Edit Button For : "+task, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Edit Button For : "+task);
					}

				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	
		
		String actualValue=null;
		String expectedValue = null;
		String[] contactNames={M7Contact1FName+" "+M7Contact1LName,M7Contact3FName+" "+M7Contact3LName};
		String contactName=null;
		if (flag) {
			for (int j = 0; j < contactNames.length; j++) {
				contactName=contactNames[j];
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						expectedValue = M7Task2dueDate;
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}
						
					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			}
			
		} else {
			sa.assertTrue(false,"Task is not been updated so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc010_DeleteNewlyUpdatedTaskAndVerifyTheImpactOnLastTouchPointInContactPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact3FName+" "+M7Contact3LName;
		String task = M7Task2Subject;
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					if (click(driver, cp.getDeleteButton(projectName, 20), "Delete Button", action.BOOLEAN)) {
						log(LogStatus.INFO,"Able to Click on Delete button : "+task,YesNo.No);
						ThreadSleep(2000);
						if (click(driver, cp.getDeleteButtonPopUp(projectName, 20), "Delete Button", action.BOOLEAN)) {
							log(LogStatus.INFO,"Able to Click on Delete button on PopUp : "+task,YesNo.No);
							ThreadSleep(2000);
							flag=true;
						} else {
							sa.assertTrue(false,"Able to Click on Delete button on PopUp : "+task);
							log(LogStatus.SKIP,"Able to Click on Delete button on PopUp : "+task,YesNo.Yes);
						}
					} else {
						sa.assertTrue(false,"Not Able to Click on Delete button : "+task);
						log(LogStatus.SKIP,"Not Able to Click on Delete button : "+task,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	

		String actualValue=null;
		String expectedValue = null;
		String[] contactNames={primaryContact,M7Contact1FName+" "+M7Contact1LName};
		String contactName=null;
		if (flag) {
			for (int j = 0; j < contactNames.length; j++) {
				contactName=contactNames[j];
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (j==0) {
								expectedValue="";
								if (actualValue.isEmpty() || actualValue.equals("")) {
									log(LogStatus.INFO,"Last Touch Point is Blank for "+contactName, YesNo.No);
								}
								else {
									log(LogStatus.ERROR, "Last Touch Point should be Blank for "+contactName+" Actual Last Touch Point Date : "+actualValue, YesNo.Yes);
									sa.assertTrue(false,"Last Touch Point should be Blank for "+contactName+" Actual Last Touch Point Date : "+actualValue );
								}
							} else {
								expectedValue=M7Task3dueDate;
								if (cp.verifyDate(expectedValue, actualValue)) {
									log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
								}
								else {
									log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
									sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
								}
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			}

		} else {
			sa.assertTrue(false,"Task is not been deleted so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been deleted so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc011_RestoreTheDeletedTaskMATouchpointTask_1AndVerifyTheImpactOnLastTouchPointInContactPage(String projectName) {
	
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele;
		boolean flag=false;
		String restoreItem = M7Task1Subject;
		if (lp.restoreValueFromRecycleBin(projectName, restoreItem)) {
			log(LogStatus.INFO,"Able to restore item from Recycle Bin "+restoreItem,YesNo.Yes);
			flag=true;
		} else {
			sa.assertTrue(false,"Not Able to restore item from Recycle Bin "+restoreItem);
			log(LogStatus.SKIP,"Not Able to restore item from Recycle Bin "+restoreItem,YesNo.Yes);
	
		}
		String actualValue=null;
		String expectedValue = null;
		String contactName=M7Contact2FName+" "+M7Contact2LName;
		if (flag) {
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						expectedValue=M7Task1dueDate;
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			

		} else {
			sa.assertTrue(false,"Task is not been restored so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been restored so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc012_UpdateDueDateOfMATouchpointTask_1AndVerifyImpactOnLastTouchPoint(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
		boolean flag=false;
		String task=M7Task1Subject;
		M7Task1dueDate=todaysDate1;
		TabName tabName=TabName.TaskTab;
		
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+task,YesNo.No);
				ele=tp.getTaskNameLinkInSideMMenu(projectName, task, 30);
				if (click(driver, ele, task, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on "+task+" on Task SideMenu", YesNo.No);	
					String date = M7Task1dueDate;
					if (tp.EditEnterDueDateAndSave(projectName, task, date)) {
						log(LogStatus.INFO, "Value Entered & saved to Due Date "+date, YesNo.No);	
						ThreadSleep(2000);	
						ExcelUtils.writeData(phase1DataSheetFilePath,date, "Task1", excelLabel.Variable_Name, "M7Task1", excelLabel.Due_Date);
						flag=true;
					} else {
						log(LogStatus.ERROR, "Not Able to Entered & save Value to Due Date "+date, YesNo.Yes);	
						sa.assertTrue(false, "Not Able to Entered Value to Due Date "+date);
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+task+" on Task SideMenu", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+task+" on Task SideMenu");
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+task,YesNo.Yes);
			}
			
			switchToDefaultContent(driver);
			ThreadSleep(5000);
		
		String contactName=M7Contact2FName+" "+M7Contact2LName;
		if (flag) {
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					 ele=cp.getlastTouchPointValue(projectName, 10);
					if (ele!=null) {
						String expectedValue = M7Task1dueDate; 
						String actualValue = ele.getText().trim();
						if (cp.verifyDate(expectedValue, actualValue)) {
							log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
							sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
						}
					}else {
						log(LogStatus.ERROR, "last touch point value is not visible For : "+contactName, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not visible For : "+contactName );
					}
				} else {
					sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
			}	
		} else {
			sa.assertTrue(false,"Task is not been updated so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc013_UpdateTheStatusOfMATouchPointTask_1AndVerifyImpactOnLastTouchPoint(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
		boolean flag=false;
		
			String task=M7Task1Subject;
			TabName tabName=TabName.TaskTab;
			PageName pageName = PageName.TaskPage;
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+task,YesNo.No);
				ele=tp.getTaskNameLinkInSideMMenu(projectName, task, 30);
				if (click(driver, ele, task, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on "+task+" on Task SideMenu", YesNo.No);	
					if (click(driver, tp.getEditButton(projectName, 30), task, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button For : "+task, YesNo.No);	
						if (tp.selectDropDownValueonTaskPopUp(projectName, pageName, PageLabel.Status.toString(), statusInStarted, action.BOOLEAN, 10)) {
							log(LogStatus.INFO, "Selected : "+statusInStarted+" For Label : "+PageLabel.Status.toString(), YesNo.No);	
								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"successfully Updated task : "+task,  YesNo.No);
									ThreadSleep(5000);
									flag=true;
									String[][] fieldsWithValues= {{PageLabel.Status.toString(),statusInStarted}};
									tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 30);	
								}else {
									log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+task, YesNo.Yes);
									sa.assertTrue(false,"save button is not clickable so task not Updated : "+task );
								}
						
						}else {
							log(LogStatus.ERROR, "Not Able to Select : "+statusInStarted+" For Label : "+PageLabel.Status.toString(), YesNo.Yes);	
							BaseLib.sa.assertTrue(false, "Not Able to Select : "+statusInStarted+" For Label : "+PageLabel.Status.toString());	
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Edit Button For : "+task, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Edit Button For : "+task);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+task+" on Task SideMenu", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+task+" on Task SideMenu");
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+task,YesNo.Yes);
			}
		
			switchToDefaultContent(driver);
			ThreadSleep(5000);
		
			String contactName=M7Contact2FName+" "+M7Contact2LName;
			if (flag) {
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						 ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							String expectedValue = M7Task3dueDate; 
							String actualValue = ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, "last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,"last touch point value is not visible For : "+contactName );
						}
					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			} else {
				sa.assertTrue(false,"Task is not been updated so cannot check Last Touch Point for "+contactName);
				log(LogStatus.SKIP,"Task is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
			}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc015_CreateNewTask(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact2FName+" "+M7Contact2LName;
		String secondaryContact=M7Contact1FName+" "+M7Contact1LName;
		M7Task4dueDate=previousOrForwardDate(0, "M/d/YYYY");;
		String task = M7Task4Subject;
		String actualValue=null;
		String expectedValue = null;
		String[][] event1 = {
				{PageLabel.Name.toString(),secondaryContact},
				{PageLabel.Subject.toString(),task},
				{PageLabel.Due_Date.toString(),M7Task4dueDate},
				{PageLabel.Meeting_Type.toString(),M7Task4MeetingType},
				{PageLabel.Status.toString(),M7Task4Status}};


		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				ThreadSleep(2000);
				ExcelUtils.writeData(phase1DataSheetFilePath,M7Task4dueDate, "Task1", excelLabel.Variable_Name, "M7Task4", excelLabel.Due_Date);
				///////////////////////
				ele = lp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.New_Task, 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task.toString(), action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.New_Task.toString(),  YesNo.Yes);
					ThreadSleep(1000);
					ele = lp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), true, primaryContact, action.SCROLLANDBOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO, "successfully verified presence of "+primaryContact+" in name field",YesNo.No);
					} else {
						sa.assertTrue(false,"not found "+ primaryContact+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"not found "+ primaryContact+" For Label "+PageLabel.Name,YesNo.Yes);

					}
					gp.enterValueForTask(projectName, PageName.Object2Page, event1, 20);
					if (clickUsingJavaScript(driver, gp.getCustomTabSaveBtn(projectName, 2), "Save Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
						ThreadSleep(5000);
						flag=true;
						refresh(driver);

					}else {
						sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
						log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
					}

				}
				else {
					log(LogStatus.ERROR, "not able to click on "+ActivityTimeLineItem.New_Task.toString(), YesNo.Yes);
					sa.assertTrue(false,"not able to click on "+ActivityTimeLineItem.New_Task.toString() );
				}
			
				ele = lp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Refresh, 10);
				click(driver, ele, ActivityTimeLineItem.Refresh.toString(), action.BOOLEAN);
				ThreadSleep(3000);
				ele=cp.getlastTouchPointValue(projectName, 10);
				expectedValue = M7Task4dueDate;
				if (ele!=null) {
					actualValue=ele.getText().trim();
					if (cp.verifyDate(expectedValue, actualValue)) {
						log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+primaryContact, YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "Last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue );
					}
				}else {
					log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+primaryContact, YesNo.Yes);
					sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+primaryContact );
				}
			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	


		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+secondaryContact,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, secondaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+secondaryContact+" For : "+tabObj2,YesNo.No);
				ThreadSleep(2000);
				ele=cp.getlastTouchPointValue(projectName, 10);
				expectedValue = M7Task4dueDate;
				if (ele!=null) {
					actualValue=ele.getText().trim();
					if (cp.verifyDate(expectedValue, actualValue)) {
						log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+secondaryContact, YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "Last touch point value is not matched For : "+secondaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not matched For : "+secondaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue );
					}
				}else {
					log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+secondaryContact, YesNo.Yes);
					sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+secondaryContact );
				}
			} else {
				sa.assertTrue(false,"Item Not Found : "+secondaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+secondaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+secondaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+secondaryContact,YesNo.Yes);
		}	
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc016_UpdateTheDueDateWithFutureDateAndVerifyLastTouchPointDate(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
		boolean flag=false;
		String task=M7Task4Subject;
		M7Task4dueDate=previousOrForwardDate(7, "M/d/YYYY");;
		String primaryContact=M7Contact2FName+" "+M7Contact2LName;
		String secondaryContact=M7Contact1FName+" "+M7Contact1LName;

		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					String date = M7Task4dueDate;
					if (tp.EditEnterDueDateAndSave(projectName, task, date)) {
						log(LogStatus.INFO, "Value Entered & saved to Due Date "+date, YesNo.No);	
						ThreadSleep(2000);	
						ExcelUtils.writeData(phase1DataSheetFilePath,date, "Task1", excelLabel.Variable_Name, "M7Task4", excelLabel.Due_Date);
						flag=true;
					} else {
						log(LogStatus.ERROR, "Not Able to Entered & save Value to Due Date "+date, YesNo.Yes);	
						sa.assertTrue(false, "Not Able to Entered Value to Due Date "+date);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	

		String actualValue=null;
		String expectedValue = null;
		String[] contactNames={primaryContact,secondaryContact};
		String contactName=null;
		if (flag) {
			for (int j = 0; j < contactNames.length; j++) {
				contactName=contactNames[j];
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						expectedValue = M7Task4dueDate;
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			}

		} else {
			sa.assertTrue(false,"Task is not been updated so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}

		/////////////////////////

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc017_UpdateTheDueDateWithPastDateAndVerifyLastTouchPointDate(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
		boolean flag=false;
		String task=M7Task4Subject;
		M7Task4dueDate=previousOrForwardDate(-7, "M/d/YYYY");;
		String primaryContact=M7Contact2FName+" "+M7Contact2LName;
		String secondaryContact=M7Contact1FName+" "+M7Contact1LName;

		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					String date = M7Task4dueDate;
					if (tp.EditEnterDueDateAndSave(projectName, task, date)) {
						log(LogStatus.INFO, "Value Entered & saved to Due Date "+date, YesNo.No);	
						ThreadSleep(2000);	
						ExcelUtils.writeData(phase1DataSheetFilePath,date, "Task1", excelLabel.Variable_Name, "M7Task4", excelLabel.Due_Date);
						flag=true;
					} else {
						log(LogStatus.ERROR, "Not Able to Entered & save Value to Due Date "+date, YesNo.Yes);	
						sa.assertTrue(false, "Not Able to Entered Value to Due Date "+date);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	

		String actualValue=null;
		String expectedValue = null;
		String[] contactNames={primaryContact,secondaryContact};
		String contactName=null;
		if (flag) {
			for (int j = 0; j < contactNames.length; j++) {
				contactName=contactNames[j];
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						expectedValue = M7Task3dueDate;
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			}

		} else {
			sa.assertTrue(false,"Task is not been updated so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}

		/////////////////////////

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc018_CreateThreeNewContactToCheckTheLastTouchPointInCall(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		// contact
		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			M7Contact4EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, M7Contact4EmailID, "Contacts", excelLabel.Variable_Name, "M7CON4",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, M7Contact4FName, M7Contact4LName, M7Ins1, M7Contact4EmailID,M7Contact4RecordType, null, null, CreationPage.ContactPage, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+M7Contact4FName+" "+M7Contact4LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+M7Contact4FName+" "+M7Contact4LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+M7Contact4FName+" "+M7Contact4LName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
		
		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			M7Contact5EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, M7Contact5EmailID, "Contacts", excelLabel.Variable_Name, "M7CON5",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, M7Contact5FName, M7Contact5LName, M7Ins1, M7Contact5EmailID,M7Contact5RecordType, null, null, CreationPage.ContactPage, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+M7Contact5FName+" "+M7Contact5LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+M7Contact5FName+" "+M7Contact5LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+M7Contact5FName+" "+M7Contact5LName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
		
		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			M7Contact6EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, M7Contact6EmailID, "Contacts", excelLabel.Variable_Name, "M7CON6",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, M7Contact6FName, M7Contact6LName, M7Ins1, M7Contact6EmailID,M7Contact6RecordType, null, null, CreationPage.ContactPage, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+M7Contact6FName+" "+M7Contact6LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+M7Contact6FName+" "+M7Contact6LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+M7Contact6FName+" "+M7Contact6LName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
		
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc019_CreatLogACallForContactJhonAleaxVerifyLastTouchpointOnContactDetailPage(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String contactName=M7Contact4FName+" "+M7Contact4LName;
		M7Task5dueDate=yesterdaysDate;
		String task = M7Task5Subject;
		String[][] event1 = {{PageLabel.Subject.toString(),task},
				{PageLabel.Due_Date.toString(),M7Task5dueDate},
				{PageLabel.Name.toString(),contactName},
				{PageLabel.Status.toString(),M7Task5Status}};

		if (gp.clickOnGlobalActionAndEnterValue(projectName, GlobalActionItem.Log_a_Call, event1)) {
			log(LogStatus.INFO,"Able to Enter Value for : "+task,YesNo.No);
			ExcelUtils.writeData(phase1DataSheetFilePath,M7Task5dueDate, "Task1", excelLabel.Variable_Name, "M7Task5", excelLabel.Due_Date);
			
			if (click(driver, gp.getSaveButtonForEvent(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);		
				flag=true;
				
			}else {
				sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
			}
		} else {
			sa.assertTrue(false,"Not Able to Click/Enter Value : "+task);
			log(LogStatus.SKIP,"Not Able to Click/Enter Value : "+task,YesNo.Yes);	
		}
		if (flag) {
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					 ele=cp.getlastTouchPointValue(projectName, 10);
					String value="";
					if (ele!=null) {
						value=ele.getText().trim();
						if (value.isEmpty() || value.equals("")) {
							log(LogStatus.INFO,"Last Touch Point is Blank for "+contactName, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "Last Touch Point should be Blank for "+contactName+" Actual Last Touch Point Date : "+value, YesNo.Yes);
							sa.assertTrue(false,"Last Touch Point should be Blank for "+contactName+" Actual Last Touch Point Date : "+value );
						}
					}else {
						log(LogStatus.ERROR, "last touch point value is not visible For : "+contactName, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not visible For : "+contactName );
					}
				} else {
					sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
			}	
		} else {
			sa.assertTrue(false,"Call is not created so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Call is not created so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc020_CreateMultiTaggedCallforContactSamanthaRaoAndVerifyLastTouchPoint(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact5FName+" "+M7Contact5LName;
		String secondaryContact=M7Contact6FName+" "+M7Contact6LName;
		M7Task6dueDate=todaysDate1;
		String task = M7Task6Subject;
		String[][] event1 = {
				{PageLabel.Name.toString(),secondaryContact},
				{PageLabel.Subject.toString(),task},
				{PageLabel.Due_Date.toString(),M7Task6dueDate},
				{PageLabel.Status.toString(),M7Task6Status}};

		
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					ExcelUtils.writeData(phase1DataSheetFilePath,M7Task6dueDate, "Task1", excelLabel.Variable_Name, "M7Task6", excelLabel.Due_Date);
					///////////////////////
					ele = lp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.New_Call, 10);
					if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Call.toString(), action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.New_Call.toString(),  YesNo.Yes);
						ThreadSleep(1000);
						
						ele = lp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), true, primaryContact, action.SCROLLANDBOOLEAN, 10);
						if (ele!=null) {
							log(LogStatus.INFO, "successfully verified presence of "+primaryContact+" in name field",YesNo.No);
						} else {
							sa.assertTrue(false,"not found "+ primaryContact+" For Label "+PageLabel.Name);
							log(LogStatus.SKIP,"not found "+ primaryContact+" For Label "+PageLabel.Name,YesNo.Yes);

						}
						
						gp.enterValueForTask(projectName, PageName.Object2Page, event1, 20);
						String relatedValue=M7Ins1;
						flag = lp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), TabName.Object1Tab, relatedValue, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.INFO,"Selected "+relatedValue+" For Label "+PageLabel.Related_Associations,YesNo.No);

						} else {
							sa.assertTrue(false,"Not Able to Select "+relatedValue+" For Label "+PageLabel.Related_Associations);
							log(LogStatus.SKIP,"Not Able to Select "+relatedValue+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

						}
						
						if (clickUsingJavaScript(driver, gp.getCustomTabSaveBtn(projectName, 2), "Save Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
							ThreadSleep(5000);
							flag=true;
							
						}else {
							sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
							log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
						}
						
					}
					else {
						log(LogStatus.ERROR, "not able to click on "+ActivityTimeLineItem.New_Call.toString(), YesNo.Yes);
						sa.assertTrue(false,"not able to click on "+ActivityTimeLineItem.New_Call.toString() );
					}
					//////////////////////
					refresh(driver);
					ThreadSleep(3000);
					ele=cp.getlastTouchPointValue(projectName, 10);
					String value="";
					if (ele!=null) {
						value=ele.getText().trim();
						if (value.isEmpty() || value.equals("")) {
							log(LogStatus.INFO,"Last Touch Point is Blank for "+primaryContact, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "Last Touch Point should be Blank for "+primaryContact+" Actual Last Touch Point Date : "+value, YesNo.Yes);
							sa.assertTrue(false,"Last Touch Point should be Blank for "+primaryContact+" Actual Last Touch Point Date : "+value );
						}
					}else {
						log(LogStatus.ERROR, "last touch point value is not visible For : "+primaryContact, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not visible For : "+primaryContact );
					}
				} else {
					sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
			}	
			
			
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+secondaryContact,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, secondaryContact, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+secondaryContact+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					
					ele=cp.getlastTouchPointValue(projectName, 10);
					String value="";
					if (ele!=null) {
						value=ele.getText().trim();
						if (value.isEmpty() || value.equals("")) {
							log(LogStatus.INFO,"Last Touch Point is Blank for "+secondaryContact, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "Last Touch Point should be Blank for "+secondaryContact+" Actual Last Touch Point Date : "+value, YesNo.Yes);
							sa.assertTrue(false,"Last Touch Point should be Blank for "+secondaryContact+" Actual Last Touch Point Date : "+value );
						}
					}else {
						log(LogStatus.ERROR, "last touch point value is not visible For : "+secondaryContact, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not visible For : "+secondaryContact );
					}
				} else {
					sa.assertTrue(false,"Item Not Found : "+secondaryContact+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+secondaryContact+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+secondaryContact);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+secondaryContact,YesNo.Yes);
			}	
			
			
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M7Tc021_UpdateStatusToCompletedInStandardCall(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
		boolean flag=false;
		TabName[] tabNames = {TabName.TaskTab};
		String[] taskNames = {M7Task5Subject};
		PageName[] pageNames = {PageName.TaskPage};
		
		TabName tabName ;
		int i=0;
		for (String task : taskNames) {
			task=taskNames[i];
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+task,YesNo.No);
				ele=tp.getTaskNameLinkInSideMMenu(projectName, task, 30);
				if (click(driver, ele, task, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on "+task+" on Task SideMenu", YesNo.No);	
					if (click(driver, tp.getEditButton(projectName, 30), task, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button For : "+task, YesNo.No);	
						if (tp.selectDropDownValueonTaskPopUp(projectName, pageNames[i], PageLabel.Status.toString(), statusCompleted, action.BOOLEAN, 10)) {
							log(LogStatus.INFO, "Selected : "+statusCompleted+" For Label : "+PageLabel.Status.toString(), YesNo.No);	
								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"successfully Updated task : "+task,  YesNo.No);
									ThreadSleep(5000);
									flag=true;
									String[][] fieldsWithValues= {{PageLabel.Status.toString(),statusCompleted}};
									tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 30);	
								}else {
									log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+task, YesNo.Yes);
									sa.assertTrue(false,"save button is not clickable so task not Updated : "+task );
								}
						
						}else {
							log(LogStatus.ERROR, "Not Able to Select : "+statusCompleted+" For Label : "+PageLabel.Status.toString(), YesNo.Yes);	
							BaseLib.sa.assertTrue(false, "Not Able to Select : "+statusCompleted+" For Label : "+PageLabel.Status.toString());	
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Edit Button For : "+task, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Edit Button For : "+task);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+task+" on Task SideMenu", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+task+" on Task SideMenu");
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+task,YesNo.Yes);
			}
			i++;
			switchToDefaultContent(driver);
			ThreadSleep(5000);
		}
		String contactName=M7Contact4FName+" "+M7Contact4LName;
		if (flag) {
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					 ele=cp.getlastTouchPointValue(projectName, 10);
					if (ele!=null) {
						String expectedValue = M7Task5dueDate; 
						String actualValue = ele.getText().trim();
						if (cp.verifyDate(expectedValue, actualValue)) {
							log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
							sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
						}
					}else {
						log(LogStatus.ERROR, "last touch point value is not visible For : "+contactName, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not visible For : "+contactName );
					}
				} else {
					sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
			}	
		} else {
			sa.assertTrue(false,"Task is not been updated so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc022_UpdateStatusToCompletedInMultiTaggedCall(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
		boolean flag=false;
		TabName[] tabNames = {TabName.TaskTab};
		String[] taskNames = {M7Task6Subject};
		PageName[] pageNames = {PageName.Object2Page};
		
		TabName tabName ;
		int i=0;
		for (String task : taskNames) {
			task=taskNames[i];
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+task,YesNo.No);
				ele=tp.getTaskNameLinkInSideMMenu(projectName, task, 30);
				if (click(driver, ele, task, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on "+task+" on Task SideMenu", YesNo.No);	
					if (click(driver, tp.getEditButton(projectName, 30), task, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button For : "+task, YesNo.No);	
						if (tp.selectDropDownValueonTaskPopUp(projectName, pageNames[i], PageLabel.Status.toString(), statusCompleted, action.BOOLEAN, 10)) {
							log(LogStatus.INFO, "Selected : "+statusCompleted+" For Label : "+PageLabel.Status.toString(), YesNo.No);	
								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"successfully Updated task : "+task,  YesNo.No);
									ThreadSleep(5000);
									flag=true;
									String[][] fieldsWithValues= {{PageLabel.Status.toString(),statusCompleted}};
									tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 30);	
								}else {
									log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+task, YesNo.Yes);
									sa.assertTrue(false,"save button is not clickable so task not Updated : "+task );
								}
							
						}else {
							log(LogStatus.ERROR, "Not Able to Select : "+statusCompleted+" For Label : "+PageLabel.Status.toString(), YesNo.Yes);	
							BaseLib.sa.assertTrue(false, "Not Able to Select : "+statusCompleted+" For Label : "+PageLabel.Status.toString());	
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Edit Button For : "+task, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Edit Button For : "+task);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+task+" on Task SideMenu", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+task+" on Task SideMenu");
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+task,YesNo.Yes);
			}
			i++;
			switchToDefaultContent(driver);
			ThreadSleep(5000);
		}
		String[] contactNames={M7Contact5FName+" "+M7Contact5LName,M7Contact6FName+" "+M7Contact6LName};
		String contactName=null;
		if (flag) {
			for (int j = 0; j < contactNames.length; j++) {
				contactName=contactNames[j];
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						 ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							String expectedValue = M7Task6dueDate; 
							String actualValue = ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, "last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,"last touch point value is not visible For : "+contactName );
						}
					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			}
			
		} else {
			sa.assertTrue(false,"Task is not been updated so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc023_CreateMultiTaggedCallforContactJohnAlexaAndVerifyLastTouchPoint(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact4FName+" "+M7Contact4LName;
		String secondaryContact=M7Contact6FName+" "+M7Contact6LName;
		M7Task7dueDate=previousOrForwardDate(-5, "M/d/YYYY");
		String task = M7Task7Subject;
		String[][] event1 = {
				{PageLabel.Name.toString(),secondaryContact},
				{PageLabel.Subject.toString(),task},
				{PageLabel.Due_Date.toString(),M7Task7dueDate},
				{PageLabel.Status.toString(),M7Task7Status}};

		
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					ExcelUtils.writeData(phase1DataSheetFilePath,M7Task7dueDate, "Task1", excelLabel.Variable_Name, "M7Task7", excelLabel.Due_Date);
					///////////////////////
					ele = lp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.New_Call, 10);
					if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Call.toString(), action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.New_Call.toString(),  YesNo.Yes);
						ThreadSleep(1000);
						
						ele = lp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), true, primaryContact, action.SCROLLANDBOOLEAN, 10);
						if (ele!=null) {
							log(LogStatus.INFO, "successfully verified presence of "+primaryContact+" in name field",YesNo.No);
						} else {
							sa.assertTrue(false,"not found "+ primaryContact+" For Label "+PageLabel.Name);
							log(LogStatus.SKIP,"not found "+ primaryContact+" For Label "+PageLabel.Name,YesNo.Yes);

						}
						
						gp.enterValueForTask(projectName, PageName.Object2Page, event1, 20);
						
						if (clickUsingJavaScript(driver, gp.getCustomTabSaveBtn(projectName, 2), "Save Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
							ThreadSleep(5000);
							flag=true;
							
						}else {
							sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
							log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
						}
						
					}
					else {
						log(LogStatus.ERROR, "not able to click on "+ActivityTimeLineItem.New_Call.toString(), YesNo.Yes);
						sa.assertTrue(false,"not able to click on "+ActivityTimeLineItem.New_Call.toString() );
					}
					//////////////////////
					refresh(driver);
					ThreadSleep(3000);
					ele=cp.getlastTouchPointValue(projectName, 10);
					if (ele!=null) {
						String expectedValue = M7Task5dueDate; 
						String actualValue = ele.getText().trim();
						if (cp.verifyDate(expectedValue, actualValue)) {
							log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+primaryContact, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "Last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
							sa.assertTrue(false,"last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue );
						}
					}else {
						log(LogStatus.ERROR, "last touch point value is not visible For : "+primaryContact, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not visible For : "+primaryContact );
					}
				} else {
					sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
			}	
			
			
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+secondaryContact,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, secondaryContact, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+secondaryContact+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					ele=cp.getlastTouchPointValue(projectName, 10);
					if (ele!=null) {
						String expectedValue = M7Task6dueDate; 
						String actualValue = ele.getText().trim();
						if (cp.verifyDate(expectedValue, actualValue)) {
							log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+secondaryContact, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "Last touch point value is not matched For : "+secondaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
							sa.assertTrue(false,"last touch point value is not matched For : "+secondaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue );
						}
					}else {
						log(LogStatus.ERROR, "last touch point value is not visible For : "+secondaryContact, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not visible For : "+secondaryContact );
					}
				} else {
					sa.assertTrue(false,"Item Not Found : "+secondaryContact+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+secondaryContact+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+secondaryContact);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+secondaryContact,YesNo.Yes);
			}	
			
			
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M7Tc024_ChangeTheStausFromCompletedtoInProgressAndVerifyTheImpactOnTheLastTouchPointDate(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact4FName+" "+M7Contact4LName;
		String secondaryContact=M7Contact6FName+" "+M7Contact6LName;
		String task = M7Task7Subject;

		PageName pageName = PageName.TaskPage;

		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					if (click(driver, tp.getEditButton(projectName, 30), task, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button For : "+task, YesNo.No);	
						if (tp.selectDropDownValueonTaskPopUp(projectName, pageName, PageLabel.Status.toString(), statusInProgess, action.BOOLEAN, 10)) {
							log(LogStatus.INFO, "Selected : "+statusInProgess+" For Label : "+PageLabel.Status.toString(), YesNo.No);	
							if (clickUsingJavaScript(driver, lp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"successfully Updated task : "+task,  YesNo.No);
								ThreadSleep(5000);
								flag=true;
								String[][] fieldsWithValues= {{PageLabel.Status.toString(),statusInProgess}};
								tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 30);	
							}else {
								log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+task, YesNo.Yes);
								sa.assertTrue(false,"save button is not clickable so task not Updated : "+task );
							}

						}else {
							log(LogStatus.ERROR, "Not Able to Select : "+statusInProgess+" For Label : "+PageLabel.Status.toString(), YesNo.Yes);	
							BaseLib.sa.assertTrue(false, "Not Able to Select : "+statusInProgess+" For Label : "+PageLabel.Status.toString());	
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Edit Button For : "+task, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Edit Button For : "+task);
					}

				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	
		
		String actualValue=null;
		String expectedValue = null;
		String[] contactNames={primaryContact,secondaryContact};
		String contactName=null;
		if (flag) {
			for (int j = 0; j < contactNames.length; j++) {
				contactName=contactNames[j];
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						if (j==0) {
							expectedValue = M7Task5dueDate;
						} else {
							expectedValue = M7Task6dueDate;
						}
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}
						
					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			}
			
		} else {
			sa.assertTrue(false,"Task is not been updated so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc025_DeleteMATouchpointCall_1AndVerifyImpactOnLastTouchPointInContactPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact4FName+" "+M7Contact4LName;
		String task = M7Task5Subject;
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					if (click(driver, cp.getDeleteButton(projectName, 20), "Delete Button", action.BOOLEAN)) {
						log(LogStatus.INFO,"Able to Click on Delete button : "+task,YesNo.No);
						ThreadSleep(2000);
						if (click(driver, cp.getDeleteButtonPopUp(projectName, 20), "Delete Button", action.BOOLEAN)) {
							log(LogStatus.INFO,"Able to Click on Delete button on PopUp : "+task,YesNo.No);
							ThreadSleep(2000);
							flag=true;
						} else {
							sa.assertTrue(false,"Able to Click on Delete button on PopUp : "+task);
							log(LogStatus.SKIP,"Able to Click on Delete button on PopUp : "+task,YesNo.Yes);
						}
					} else {
						sa.assertTrue(false,"Not Able to Click on Delete button : "+task);
						log(LogStatus.SKIP,"Not Able to Click on Delete button : "+task,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	

		String actualValue=null;
		String expectedValue = "";
		String contactName=primaryContact;
		if (flag) {
				contactName=primaryContact;
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (actualValue.isEmpty() || actualValue.equals("")) {
								log(LogStatus.INFO,"Last Touch Point is Blank for "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last Touch Point should be Blank for "+contactName+" Actual Last Touch Point Date : "+actualValue, YesNo.Yes);
								sa.assertTrue(false,"Last Touch Point should be Blank for "+contactName+" Actual Last Touch Point Date : "+actualValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			

		} else {
			sa.assertTrue(false,"Task is not been deleted so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been deleted so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc026_AgainChangeTheStausFromInProgressToCompletedAndVerifyTheImpactOnTheLastTouchPointDate(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact4FName+" "+M7Contact4LName;
		String secondaryContact=M7Contact6FName+" "+M7Contact6LName;
		String task = M7Task7Subject;

		PageName pageName = PageName.TaskPage;

		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					if (click(driver, tp.getEditButton(projectName, 30), task, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button For : "+task, YesNo.No);	
						if (tp.selectDropDownValueonTaskPopUp(projectName, pageName, PageLabel.Status.toString(), statusCompleted, action.BOOLEAN, 10)) {
							log(LogStatus.INFO, "Selected : "+statusCompleted+" For Label : "+PageLabel.Status.toString(), YesNo.No);	
							if (clickUsingJavaScript(driver, lp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"successfully Updated task : "+task,  YesNo.No);
								ThreadSleep(5000);
								flag=true;
								String[][] fieldsWithValues= {{PageLabel.Status.toString(),statusCompleted}};
								tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 30);	
							}else {
								log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+task, YesNo.Yes);
								sa.assertTrue(false,"save button is not clickable so task not Updated : "+task );
							}

						}else {
							log(LogStatus.ERROR, "Not Able to Select : "+statusCompleted+" For Label : "+PageLabel.Status.toString(), YesNo.Yes);	
							BaseLib.sa.assertTrue(false, "Not Able to Select : "+statusCompleted+" For Label : "+PageLabel.Status.toString());	
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Edit Button For : "+task, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Edit Button For : "+task);
					}

				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	
		
		String actualValue=null;
		String expectedValue = null;
		String[] contactNames={primaryContact,secondaryContact};
		String contactName=null;
		if (flag) {
			for (int j = 0; j < contactNames.length; j++) {
				contactName=contactNames[j];
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						if (j==0) {
							expectedValue = M7Task7dueDate;
						} else {
							expectedValue = M7Task6dueDate;
						}
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}
						
					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			}
			
		} else {
			sa.assertTrue(false,"Task is not been updated so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc027_DeleteNewlyUpdatedTaskAndVerifyTheImpactOnLastTouchPointInContactPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact4FName+" "+M7Contact4LName;
		String secondaryContact=M7Contact6FName+" "+M7Contact6LName;
		String task = M7Task7Subject;
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					if (click(driver, cp.getDeleteButton(projectName, 20), "Delete Button", action.BOOLEAN)) {
						log(LogStatus.INFO,"Able to Click on Delete button : "+task,YesNo.No);
						ThreadSleep(2000);
						if (click(driver, cp.getDeleteButtonPopUp(projectName, 20), "Delete Button", action.BOOLEAN)) {
							log(LogStatus.INFO,"Able to Click on Delete button on PopUp : "+task,YesNo.No);
							ThreadSleep(2000);
							flag=true;
						} else {
							sa.assertTrue(false,"Able to Click on Delete button on PopUp : "+task);
							log(LogStatus.SKIP,"Able to Click on Delete button on PopUp : "+task,YesNo.Yes);
						}
					} else {
						sa.assertTrue(false,"Not Able to Click on Delete button : "+task);
						log(LogStatus.SKIP,"Not Able to Click on Delete button : "+task,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	

		String actualValue=null;
		String expectedValue = null;
		String[] contactNames={primaryContact,secondaryContact};
		String contactName=null;
		if (flag) {
			for (int j = 0; j < contactNames.length; j++) {
				contactName=contactNames[j];
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (j==0) {
								expectedValue="";
								if (actualValue.isEmpty() || actualValue.equals("")) {
									log(LogStatus.INFO,"Last Touch Point is Blank for "+contactName, YesNo.No);
								}
								else {
									log(LogStatus.ERROR, "Last Touch Point should be Blank for "+contactName+" Actual Last Touch Point Date : "+actualValue, YesNo.Yes);
									sa.assertTrue(false,"Last Touch Point should be Blank for "+contactName+" Actual Last Touch Point Date : "+actualValue );
								}
							} else {
								expectedValue=M7Task6dueDate;
								if (cp.verifyDate(expectedValue, actualValue)) {
									log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
								}
								else {
									log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
									sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
								}
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			}

		} else {
			sa.assertTrue(false,"Task is not been deleted so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been deleted so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc028_RestoreTheDeletedTaskMATouchpointCall_1AndVerifyTheImpactOnLastTouchPointInContactPage(String projectName) {
	
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele;
		boolean flag=false;
		String restoreItem = M7Task5Subject;
		if (lp.restoreValueFromRecycleBin(projectName, restoreItem)) {
			log(LogStatus.INFO,"Able to restore item from Recycle Bin "+restoreItem,YesNo.Yes);
			flag=true;
		} else {
			sa.assertTrue(false,"Not Able to restore item from Recycle Bin "+restoreItem);
			log(LogStatus.SKIP,"Not Able to restore item from Recycle Bin "+restoreItem,YesNo.Yes);
	
		}
		String actualValue=null;
		String expectedValue = null;
		String contactName=M7Contact4FName+" "+M7Contact4LName;
		if (flag) {
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						expectedValue=M7Task5dueDate;
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			

		} else {
			sa.assertTrue(false,"Task is not been restored so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been restored so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc029_UpdateDueDateOfMATouchpointCall_1AndVerifyImpactOnLastTouchPoint(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
		boolean flag=false;
		String task=M7Task5Subject;
		M7Task5dueDate=todaysDate1;
		TabName tabName=TabName.TaskTab;
		
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+task,YesNo.No);
				ele=tp.getTaskNameLinkInSideMMenu(projectName, task, 30);
				if (click(driver, ele, task, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on "+task+" on Task SideMenu", YesNo.No);	
					String date = M7Task5dueDate;
					if (tp.EditEnterDueDateAndSave(projectName, task, date)) {
						log(LogStatus.INFO, "Value Entered & saved to Due Date "+date, YesNo.No);	
						ThreadSleep(2000);	
						ExcelUtils.writeData(phase1DataSheetFilePath,date, "Task1", excelLabel.Variable_Name, "M7Task5", excelLabel.Due_Date);
						flag=true;
					} else {
						log(LogStatus.ERROR, "Not Able to Entered & save Value to Due Date "+date, YesNo.Yes);	
						sa.assertTrue(false, "Not Able to Entered Value to Due Date "+date);
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+task+" on Task SideMenu", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+task+" on Task SideMenu");
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+task,YesNo.Yes);
			}
			
			switchToDefaultContent(driver);
			ThreadSleep(5000);
		
		String contactName=M7Contact4FName+" "+M7Contact4LName;
		if (flag) {
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					 ele=cp.getlastTouchPointValue(projectName, 10);
					if (ele!=null) {
						String expectedValue = M7Task5dueDate; 
						String actualValue = ele.getText().trim();
						if (cp.verifyDate(expectedValue, actualValue)) {
							log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
							sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
						}
					}else {
						log(LogStatus.ERROR, "last touch point value is not visible For : "+contactName, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not visible For : "+contactName );
					}
				} else {
					sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
			}	
		} else {
			sa.assertTrue(false,"Task is not been updated so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc030_UpdateTheStatusOfMATouchPointCall_1AndVerifyImpactOnLastTouchPoint(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
		boolean flag=false;
		
			String task=M7Task5Subject;
			TabName tabName=TabName.TaskTab;
			PageName pageName = PageName.TaskPage;
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+task,YesNo.No);
				ele=tp.getTaskNameLinkInSideMMenu(projectName, task, 30);
				if (click(driver, ele, task, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on "+task+" on Task SideMenu", YesNo.No);	
					if (click(driver, tp.getEditButton(projectName, 30), task, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button For : "+task, YesNo.No);	
						if (tp.selectDropDownValueonTaskPopUp(projectName, pageName, PageLabel.Status.toString(), statusInStarted, action.BOOLEAN, 10)) {
							log(LogStatus.INFO, "Selected : "+statusInStarted+" For Label : "+PageLabel.Status.toString(), YesNo.No);	
								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"successfully Updated task : "+task,  YesNo.No);
									ThreadSleep(5000);
									flag=true;
									String[][] fieldsWithValues= {{PageLabel.Status.toString(),statusInStarted}};
									tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 30);	
								}else {
									log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+task, YesNo.Yes);
									sa.assertTrue(false,"save button is not clickable so task not Updated : "+task );
								}
						
						}else {
							log(LogStatus.ERROR, "Not Able to Select : "+statusInStarted+" For Label : "+PageLabel.Status.toString(), YesNo.Yes);	
							BaseLib.sa.assertTrue(false, "Not Able to Select : "+statusInStarted+" For Label : "+PageLabel.Status.toString());	
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Edit Button For : "+task, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Edit Button For : "+task);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+task+" on Task SideMenu", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+task+" on Task SideMenu");
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+task,YesNo.Yes);
			}
		
			switchToDefaultContent(driver);
			ThreadSleep(5000);
		
			String contactName=M7Contact4FName+" "+M7Contact4LName;
			if (flag) {
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						String expectedValue = ""; 
						 ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							String actualValue = ele.getText().trim();
							if (actualValue.isEmpty() || actualValue.equals("")) {
								log(LogStatus.INFO,"Last Touch Point is Blank for "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last Touch Point should be Blank for "+contactName+" Actual Last Touch Point Date : "+actualValue, YesNo.Yes);
								sa.assertTrue(false,"Last Touch Point should be Blank for "+contactName+" Actual Last Touch Point Date : "+actualValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}
					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			} else {
				sa.assertTrue(false,"Task is not been updated so cannot check Last Touch Point for "+contactName);
				log(LogStatus.SKIP,"Task is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
			}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc031_CreateNewCall(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact4FName+" "+M7Contact4LName;
		String secondaryContact=M7Contact6FName+" "+M7Contact6LName;
		M7Task8dueDate=previousOrForwardDate(0, "M/d/YYYY");;
		String task = M7Task8Subject;
		String actualValue=null;
		String expectedValue = null;
		String[][] event1 = {
				{PageLabel.Name.toString(),secondaryContact},
				{PageLabel.Subject.toString(),task},
				{PageLabel.Due_Date.toString(),M7Task8dueDate},
				{PageLabel.Status.toString(),M7Task8Status}};


		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				ThreadSleep(2000);
				ExcelUtils.writeData(phase1DataSheetFilePath,M7Task8dueDate, "Task1", excelLabel.Variable_Name, "M7Task8", excelLabel.Due_Date);
				///////////////////////
				ele = lp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.New_Call, 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Call.toString(), action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.New_Call.toString(),  YesNo.Yes);
					ThreadSleep(1000);
					ele = lp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), true, primaryContact, action.SCROLLANDBOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO, "successfully verified presence of "+primaryContact+" in name field",YesNo.No);
					} else {
						sa.assertTrue(false,"not found "+ primaryContact+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"not found "+ primaryContact+" For Label "+PageLabel.Name,YesNo.Yes);

					}
					gp.enterValueForTask(projectName, PageName.Object2Page, event1, 20);
					if (clickUsingJavaScript(driver, gp.getCustomTabSaveBtn(projectName, 2), "Save Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
						ThreadSleep(5000);
						flag=true;
						refresh(driver);

					}else {
						sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
						log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
					}

				}
				else {
					log(LogStatus.ERROR, "not able to click on "+ActivityTimeLineItem.New_Call.toString(), YesNo.Yes);
					sa.assertTrue(false,"not able to click on "+ActivityTimeLineItem.New_Call.toString() );
				}
			
				ele = lp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Refresh, 10);
				click(driver, ele, ActivityTimeLineItem.Refresh.toString(), action.BOOLEAN);
				ThreadSleep(3000);
				ele=cp.getlastTouchPointValue(projectName, 10);
				expectedValue = M7Task8dueDate;
				if (ele!=null) {
					actualValue=ele.getText().trim();
					if (cp.verifyDate(expectedValue, actualValue)) {
						log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+primaryContact, YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "Last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue );
					}
				}else {
					log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+primaryContact, YesNo.Yes);
					sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+primaryContact );
				}
			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	


		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+secondaryContact,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, secondaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+secondaryContact+" For : "+tabObj2,YesNo.No);
				ThreadSleep(2000);
				ele=cp.getlastTouchPointValue(projectName, 10);
				expectedValue = M7Task8dueDate;
				if (ele!=null) {
					actualValue=ele.getText().trim();
					if (cp.verifyDate(expectedValue, actualValue)) {
						log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+secondaryContact, YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "Last touch point value is not matched For : "+secondaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not matched For : "+secondaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue );
					}
				}else {
					log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+secondaryContact, YesNo.Yes);
					sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+secondaryContact );
				}
			} else {
				sa.assertTrue(false,"Item Not Found : "+secondaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+secondaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+secondaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+secondaryContact,YesNo.Yes);
		}	
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc032_UpdateTheDueDateWithFutureDateAndVerifyLastTouchPointDate(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
		boolean flag=false;
		String task=M7Task8Subject;
		M7Task8dueDate=previousOrForwardDate(7, "M/d/YYYY");;
		String primaryContact=M7Contact4FName+" "+M7Contact4LName;
		String secondaryContact=M7Contact6FName+" "+M7Contact6LName;

		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					if (tp.EditEnterDueDateAndSave(projectName, task, M7Task8dueDate)) {
						log(LogStatus.INFO, "Value Entered & saved to Due Date "+M7Task8dueDate, YesNo.No);	
						ThreadSleep(2000);	
						ExcelUtils.writeData(phase1DataSheetFilePath,M7Task8dueDate, "Task1", excelLabel.Variable_Name, "M7Task8", excelLabel.Due_Date);
						flag=true;
					} else {
						log(LogStatus.ERROR, "Not Able to Entered & save Value to Due Date "+M7Task8dueDate, YesNo.Yes);	
						sa.assertTrue(false, "Not Able to Entered Value to Due Date "+M7Task8dueDate);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	

		String actualValue=null;
		String expectedValue = null;
		String[] contactNames={primaryContact,secondaryContact};
		String contactName=null;
		if (flag) {
			for (int j = 0; j < contactNames.length; j++) {
				contactName=contactNames[j];
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						expectedValue = M7Task8dueDate;
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			}

		} else {
			sa.assertTrue(false,"Task is not been updated so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}

		/////////////////////////

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc033_UpdateTheDueDateWithPastDateAndVerifyLastTouchPointDate(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
		boolean flag=false;
		String task=M7Task8Subject;
		M7Task8dueDate=previousOrForwardDate(-7, "M/d/YYYY");;
		String primaryContact=M7Contact4FName+" "+M7Contact4LName;
		String secondaryContact=M7Contact6FName+" "+M7Contact6LName;

		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					String date = M7Task8dueDate;
					if (tp.EditEnterDueDateAndSave(projectName, task, date)) {
						log(LogStatus.INFO, "Value Entered & saved to Due Date "+date, YesNo.No);	
						ThreadSleep(2000);	
						ExcelUtils.writeData(phase1DataSheetFilePath,date, "Task1", excelLabel.Variable_Name, "M7Task8", excelLabel.Due_Date);
						flag=true;
					} else {
						log(LogStatus.ERROR, "Not Able to Entered & save Value to Due Date "+date, YesNo.Yes);	
						sa.assertTrue(false, "Not Able to Entered Value to Due Date "+date);
					}
			} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	

		String actualValue=null;
		String expectedValue = null;
		String[] contactNames={primaryContact,secondaryContact};
		String contactName=null;
		if (flag) {
			for (int j = 0; j < contactNames.length; j++) {
				contactName=contactNames[j];
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						if (j==0) {
							expectedValue = M7Task8dueDate;	
						} else {
							expectedValue = M7Task6dueDate;
						}
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			}

		} else {
			sa.assertTrue(false,"Task is not been updated so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}

		/////////////////////////

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc034_CreateTaskAndLogAcallOnTheSameContactWithDifferentDueDate(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		// contact
		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			M7Contact7EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, M7Contact7EmailID, "Contacts", excelLabel.Variable_Name, "M7CON7",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, M7Contact7FName, M7Contact7LName, M7Ins1, M7Contact7EmailID,M7Contact7RecordType, null, null, CreationPage.ContactPage, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+M7Contact7FName+" "+M7Contact7LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+M7Contact7FName+" "+M7Contact7LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+M7Contact7FName+" "+M7Contact7LName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
		
		
		WebElement ele = null ;
		String primaryContact=M7Contact7FName+" "+M7Contact7LName;
		M7Task9dueDate=previousOrForwardDate(-2, "M/d/YYYY");;
		String task = M7Task9Subject;
		String actualValue=null;
		String expectedValue = null;
		String[][] event1 = {
				{PageLabel.Subject.toString(),task},
				{PageLabel.Due_Date.toString(),M7Task9dueDate},
				{PageLabel.Meeting_Type.toString(),M7Task9MeetingType},
				{PageLabel.Status.toString(),M7Task9Status}};


		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				ThreadSleep(2000);
				ExcelUtils.writeData(phase1DataSheetFilePath,M7Task9dueDate, "Task1", excelLabel.Variable_Name, "M7Task9", excelLabel.Due_Date);
				///////////////////////
				ele = lp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.New_Task, 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task.toString(), action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.New_Task.toString(),  YesNo.Yes);
					ThreadSleep(1000);
					ele = lp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), true, primaryContact, action.SCROLLANDBOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO, "successfully verified presence of "+primaryContact+" in name field",YesNo.No);
					} else {
						sa.assertTrue(false,"not found "+ primaryContact+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"not found "+ primaryContact+" For Label "+PageLabel.Name,YesNo.Yes);

					}
					gp.enterValueForTask(projectName, PageName.Object2Page, event1, 20);
					if (clickUsingJavaScript(driver, lp.getCustomTabSaveBtn(projectName, 2), "Save Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
						ThreadSleep(5000);
						refresh(driver);

					}else {
						sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
						log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
					}

				}
				else {
					log(LogStatus.ERROR, "not able to click on "+ActivityTimeLineItem.New_Task.toString(), YesNo.Yes);
					sa.assertTrue(false,"not able to click on "+ActivityTimeLineItem.New_Task.toString() );
				}
			
				ele = lp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Refresh, 10);
				click(driver, ele, ActivityTimeLineItem.Refresh.toString(), action.BOOLEAN);
				ThreadSleep(3000);
				ele=cp.getlastTouchPointValue(projectName, 10);
				expectedValue = M7Task9dueDate;
				if (ele!=null) {
					actualValue=ele.getText().trim();
					if (cp.verifyDate(expectedValue, actualValue)) {
						log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+primaryContact, YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "Last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue );
					}
				}else {
					log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+primaryContact, YesNo.Yes);
					sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+primaryContact );
				}
			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}
		
		
		M7Task10dueDate=previousOrForwardDate(-3, "M/d/YYYY");;
		 task = M7Task10Subject;
		String[][] event11 = {
				{PageLabel.Subject.toString(),task},
				{PageLabel.Due_Date.toString(),M7Task10dueDate}};


		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				ThreadSleep(2000);
				ExcelUtils.writeData(phase1DataSheetFilePath,M7Task10dueDate, "Task1", excelLabel.Variable_Name, "M7Task10", excelLabel.Due_Date);
				///////////////////////
				ele = lp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.New_Call, 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Call.toString(), action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.New_Call.toString(),  YesNo.Yes);
					ThreadSleep(1000);
					ele = lp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), true, primaryContact, action.SCROLLANDBOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO, "successfully verified presence of "+primaryContact+" in name field",YesNo.No);
					} else {
						sa.assertTrue(false,"not found "+ primaryContact+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"not found "+ primaryContact+" For Label "+PageLabel.Name,YesNo.Yes);

					}
					gp.enterValueForTask(projectName, PageName.Object2Page, event11, 20);
					if (clickUsingJavaScript(driver, gp.getCustomTabSaveBtn(projectName, 2), "Save Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
						ThreadSleep(5000);
						refresh(driver);

					}else {
						sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
						log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
					}

				}
				else {
					log(LogStatus.ERROR, "not able to click on "+ActivityTimeLineItem.New_Call.toString(), YesNo.Yes);
					sa.assertTrue(false,"not able to click on "+ActivityTimeLineItem.New_Call.toString() );
				}
			
				ele = lp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Refresh, 10);
				click(driver, ele, ActivityTimeLineItem.Refresh.toString(), action.BOOLEAN);
				ThreadSleep(3000);
				ele=cp.getlastTouchPointValue(projectName, 10);
				expectedValue = M7Task9dueDate;
				if (ele!=null) {
					actualValue=ele.getText().trim();
					if (cp.verifyDate(expectedValue, actualValue)) {
						log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+primaryContact, YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "Last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue );
					}
				}else {
					log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+primaryContact, YesNo.Yes);
					sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+primaryContact );
				}
			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	
		
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc035_DeleteTheCreatedTaskAndVerifyTheLastTouchPointOnTheContact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact7FName+" "+M7Contact7LName;
		String task = M7Task9Subject;
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					if (click(driver, cp.getDeleteButton(projectName, 20), "Delete Button", action.BOOLEAN)) {
						log(LogStatus.INFO,"Able to Click on Delete button : "+task,YesNo.No);
						ThreadSleep(2000);
						if (click(driver, cp.getDeleteButtonPopUp(projectName, 20), "Delete Button", action.BOOLEAN)) {
							log(LogStatus.INFO,"Able to Click on Delete button on PopUp : "+task,YesNo.No);
							ThreadSleep(2000);
							flag=true;
						} else {
							sa.assertTrue(false,"Able to Click on Delete button on PopUp : "+task);
							log(LogStatus.SKIP,"Able to Click on Delete button on PopUp : "+task,YesNo.Yes);
						}
					} else {
						sa.assertTrue(false,"Not Able to Click on Delete button : "+task);
						log(LogStatus.SKIP,"Not Able to Click on Delete button : "+task,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	

		String actualValue=null;
		String expectedValue = "";
		String contactName=primaryContact;
		if (flag) {
				contactName=primaryContact;
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						ele=cp.getlastTouchPointValue(projectName, 10);
						expectedValue = M7Task10dueDate;
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+primaryContact, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+primaryContact, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+primaryContact );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			

		} else {
			sa.assertTrue(false,"Task is not been deleted so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been deleted so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc036_DeleteTheCreatedCallAndVerifyTheLastTouchPointOnTheContact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact7FName+" "+M7Contact7LName;
		String task = M7Task10Subject;
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					if (click(driver, cp.getDeleteButton(projectName, 20), "Delete Button", action.BOOLEAN)) {
						log(LogStatus.INFO,"Able to Click on Delete button : "+task,YesNo.No);
						ThreadSleep(2000);
						if (click(driver, cp.getDeleteButtonPopUp(projectName, 20), "Delete Button", action.BOOLEAN)) {
							log(LogStatus.INFO,"Able to Click on Delete button on PopUp : "+task,YesNo.No);
							ThreadSleep(2000);
							flag=true;
						} else {
							sa.assertTrue(false,"Able to Click on Delete button on PopUp : "+task);
							log(LogStatus.SKIP,"Able to Click on Delete button on PopUp : "+task,YesNo.Yes);
						}
					} else {
						sa.assertTrue(false,"Not Able to Click on Delete button : "+task);
						log(LogStatus.SKIP,"Not Able to Click on Delete button : "+task,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	

		String actualValue=null;
		String expectedValue = "";
		String contactName=primaryContact;
		if (flag) {
				contactName=primaryContact;
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (actualValue.isEmpty() || actualValue.equals("")) {
								log(LogStatus.INFO,"Last Touch Point is Blank for "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last Touch Point should be Blank for "+contactName+" Actual Last Touch Point Date : "+actualValue, YesNo.Yes);
								sa.assertTrue(false,"Last Touch Point should be Blank for "+contactName+" Actual Last Touch Point Date : "+actualValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			

		} else {
			sa.assertTrue(false,"Task is not been deleted so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been deleted so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc037_CreateEntityAndContact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		// contact
		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			M7Contact8EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, M7Contact8EmailID, "Contacts", excelLabel.Variable_Name, "M7CON8",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, M7Contact8FName, M7Contact8LName, M7Ins1, M7Contact8EmailID,M7Contact8RecordType, null, null, CreationPage.ContactPage, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+M7Contact8FName+" "+M7Contact8LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+M7Contact8FName+" "+M7Contact8LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+M7Contact8FName+" "+M7Contact8LName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
		
		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			M7Contact9EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, M7Contact9EmailID, "Contacts", excelLabel.Variable_Name, "M7CON9",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, M7Contact9FName, M7Contact9LName, M7Ins1, M7Contact9EmailID,M7Contact9RecordType, null, null, CreationPage.ContactPage, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+M7Contact9FName+" "+M7Contact9LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+M7Contact9FName+" "+M7Contact9LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+M7Contact9FName+" "+M7Contact9LName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
		
		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			M7Contact10EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, M7Contact10EmailID, "Contacts", excelLabel.Variable_Name, "M7CON10",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, M7Contact10FName, M7Contact10LName, M7Ins1, M7Contact10EmailID,M7Contact10RecordType, null, null, CreationPage.ContactPage, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+M7Contact10FName+" "+M7Contact10LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+M7Contact10FName+" "+M7Contact10LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+M7Contact10FName+" "+M7Contact10LName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
		
		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			M7Contact11EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, M7Contact11EmailID, "Contacts", excelLabel.Variable_Name, "M7CON11",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, M7Contact11FName, M7Contact11LName, M7Ins1, M7Contact11EmailID,M7Contact11RecordType, null, null, CreationPage.ContactPage, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+M7Contact11FName+" "+M7Contact11LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+M7Contact11FName+" "+M7Contact11LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+M7Contact11FName+" "+M7Contact11LName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
		
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc038_CreateEventForEllieVokesAndVerifyLastTouchpointOnContactDetailPage(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact8FName+" "+M7Contact8LName;
		M7Event1StartDate=yesterdaysDate;
		M7Event1EndDate=todaysDate1;
		String task = M7Event1Subject;
		String[][] event1 = {{PageLabel.Subject.toString(),task},
				{PageLabel.Name.toString(),primaryContact},
				{PageLabel.Start_Date.toString(),M7Event1StartDate},
				{PageLabel.Start_Time.toString(),M7Event1StartTime},
				{PageLabel.End_Date.toString(),M7Event1EndDate},
				{PageLabel.End_Time.toString(),M7Event1EndTime}};

		if (gp.clickOnGlobalActionAndEnterValue(projectName, GlobalActionItem.New_Event, event1)) {
			log(LogStatus.INFO,"Able to Enter Value for : "+task,YesNo.No);
			ExcelUtils.writeData(phase1DataSheetFilePath,M7Event1StartDate, "Events", excelLabel.Variable_Name, "M7Event1", excelLabel.Start_Date);
			ExcelUtils.writeData(phase1DataSheetFilePath,M7Event1EndDate, "Events", excelLabel.Variable_Name, "M7Event1", excelLabel.End_Date);
			
			if (click(driver, gp.getSaveButtonForEvent(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);		
				flag=true;
				
			}else {
				sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
			}
		} else {
			sa.assertTrue(false,"Not Able to Click/Enter Value : "+task);
			log(LogStatus.SKIP,"Not Able to Click/Enter Value : "+task,YesNo.Yes);	
		}
		if (flag) {
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
						ele=cp.getlastTouchPointValue(projectName, 10);
						String expectedValue = M7Event1EndDate;
						if (ele!=null) {
							String actualValue = ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+primaryContact, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+primaryContact, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+primaryContact );
						}
				} else {
					sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
			}	
		} else {
			sa.assertTrue(false,"Event is not created so cannot check Last Touch Point for "+primaryContact);
			log(LogStatus.SKIP,"Event is not created so cannot check Last Touch Point for "+primaryContact,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M7Tc039_CreateEventfromCalendarforContactJohrYathamAndVerifyLastTouchPoint(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact9FName+" "+M7Contact9LName;
		String secondaryContact=M7Contact10FName+" "+M7Contact10LName;
		M7Event2StartDate=previousOrForwardDate(-5, "M/d/YYYY");;
		M7Event2EndDate=tomorrowsDate;
		String task = M7Event2Subject;
		String[][] Event2 = {{PageLabel.Subject.toString(),task},
				{PageLabel.Name.toString(),primaryContact},
				{PageLabel.Name.toString(),secondaryContact},
				{PageLabel.Start_Date.toString(),M7Event2StartDate},
				{PageLabel.Start_Time.toString(),M7Event2StartTime},
				{PageLabel.End_Date.toString(),M7Event2EndDate},
				{PageLabel.End_Time.toString(),M7Event2EndTime}};
		
		if (lp.clickAnyCellonCalender(projectName)) {
			log(LogStatus.INFO,"Able to click on Calendar/Event Link",YesNo.No);
			gp.enterValueForNewEvent(projectName, GlobalActionItem.New_Event, Event2, 10);
			if (clickUsingJavaScript(driver, gp.getCustomTabSaveBtn(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
				ExcelUtils.writeData(phase1DataSheetFilePath,M7Event2StartDate, "Events", excelLabel.Variable_Name, "M7Event2", excelLabel.Start_Date);
				ExcelUtils.writeData(phase1DataSheetFilePath,M7Event2EndDate, "Events", excelLabel.Variable_Name, "M7Event2", excelLabel.End_Date);
				flag=true;
				
			}else {
				sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Calendar/Event Link");
			log(LogStatus.SKIP,"Not Able to Click on Calendar/Event Link",YesNo.Yes);	
		}
		String actualValue=null;
		String expectedValue = null;
		String[] contactNames={primaryContact,secondaryContact};
		String contactName=null;
		if (flag) {
			for (int j = 0; j < contactNames.length; j++) {
				contactName=contactNames[j];
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						expectedValue = M7Event2EndDate;
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			}

		} else {
			sa.assertTrue(false,"Task is not been created so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been created so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc040_CreateEventfromCalendarforContactEllieVokesAndVerifyLastTouchPoint(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact8FName+" "+M7Contact8LName;
		String secondaryContact=M7Contact9FName+" "+M7Contact9LName;
		M7Event3StartDate=previousOrForwardDate(-5, "M/d/YYYY");;
		M7Event3EndDate=previousOrForwardDate(-3, "M/d/YYYY");;
		String task = M7Event3Subject;
		String[][] Event3 = {{PageLabel.Subject.toString(),task},
				{PageLabel.Start_Date.toString(),M7Event3StartDate},
				{PageLabel.End_Date.toString(),M7Event3EndDate},
				{PageLabel.Name.toString(),primaryContact},
				{PageLabel.Name.toString(),secondaryContact}};
		
		if (lp.clickAnyCellonCalender(projectName)) {
			log(LogStatus.INFO,"Able to click on Calendar/Event Link",YesNo.No);
			gp.enterValueForNewEvent(projectName, GlobalActionItem.New_Event, Event3, 10);
			if (clickUsingJavaScript(driver, gp.getCustomTabSaveBtn(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
				ExcelUtils.writeData(phase1DataSheetFilePath,M7Event3StartDate, "Events", excelLabel.Variable_Name, "M7Event3", excelLabel.Start_Date);
				ExcelUtils.writeData(phase1DataSheetFilePath,M7Event3EndDate, "Events", excelLabel.Variable_Name, "M7Event3", excelLabel.End_Date);
				flag=true;
				
			}else {
				sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Calendar/Event Link");
			log(LogStatus.SKIP,"Not Able to Click on Calendar/Event Link",YesNo.Yes);	
		}
		String actualValue=null;
		String expectedValue = null;
		String[] contactNames={primaryContact,secondaryContact};
		String contactName=null;
		if (flag) {
			for (int j = 0; j < contactNames.length; j++) {
				contactName=contactNames[j];
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						if (j==0) {
							expectedValue = M7Event1EndDate;	
						} else {
							expectedValue = M7Event2EndDate;
						}
						
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			}

		} else {
			sa.assertTrue(false,"Task is not been created so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been created so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc041_ModifyTheEndDateAndVerifyTheImpactOnTheLastTouchPointDate(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact8FName+" "+M7Contact8LName;
		String secondaryContact=M7Contact9FName+" "+M7Contact9LName;;
		M7Event3EndDate=previousOrForwardDate(2, "M/d/YYYY");;
		String task = M7Event3Subject;
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					if (click(driver, lp.getEditButton(projectName, 30), task, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button For : "+task, YesNo.No);	
						if (sendKeys(driver, gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Event, PageLabel.End_Date.toString(),20), M7Event3EndDate, "Due Date", action.BOOLEAN)) {
							log(LogStatus.INFO, "Value Entered to Due Date "+M7Event3EndDate, YesNo.No);	
							ele=gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Event, PageLabel.Subject.toString(),20);
							click(driver, ele, "", action.BOOLEAN);
							ThreadSleep(2000);
							if (clickUsingJavaScript(driver, lp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"successfully Updated task : "+task,  YesNo.No);
								ThreadSleep(5000);
								ExcelUtils.writeData(phase1DataSheetFilePath,M7Event3EndDate, "Events", excelLabel.Variable_Name, "M7Event3", excelLabel.End_Date);
								flag=true;
								String[][] fieldsWithValues= {{PageLabel.End.toString(),M7Event3EndDate}};
								tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 30);	
							}else {
								log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+task, YesNo.Yes);
								sa.assertTrue(false,"save button is not clickable so task not Updated : "+task );
							}

						}else {
							log(LogStatus.ERROR, "Not Able to Entered Value to Due Date "+M7Event3EndDate, YesNo.Yes);	
							BaseLib.sa.assertTrue(false, "Not Able to Entered Value to Due Date "+M7Event3EndDate);	
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Edit Button For : "+task, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Edit Button For : "+task);
					}

				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	
		
		String actualValue=null;
		String expectedValue = null;
		String[] contactNames={primaryContact,secondaryContact};
		String contactName=null;
		if (flag) {
			for (int j = 0; j < contactNames.length; j++) {
				contactName=contactNames[j];
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						expectedValue = M7Event3EndDate;
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			}

		} else {
			sa.assertTrue(false,"Event is not been updated so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Event is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc042_DeleteMATouchpointEvent_1AndVerifyImpactOnLastTouchPointOnContactPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact8FName+" "+M7Contact8LName;
		String task = M7Event1Subject;
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					if (click(driver, cp.getDeleteButton(projectName, 20), "Delete Button", action.BOOLEAN)) {
						log(LogStatus.INFO,"Able to Click on Delete button : "+task,YesNo.No);
						ThreadSleep(2000);
						if (click(driver, cp.getDeleteButtonPopUp(projectName, 20), "Delete Button", action.BOOLEAN)) {
							log(LogStatus.INFO,"Able to Click on Delete button on PopUp : "+task,YesNo.No);
							ThreadSleep(2000);
							flag=true;
						} else {
							sa.assertTrue(false,"Able to Click on Delete button on PopUp : "+task);
							log(LogStatus.SKIP,"Able to Click on Delete button on PopUp : "+task,YesNo.Yes);
						}
					} else {
						sa.assertTrue(false,"Not Able to Click on Delete button : "+task);
						log(LogStatus.SKIP,"Not Able to Click on Delete button : "+task,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	

		String actualValue=null;
		String expectedValue = "";
		String contactName=primaryContact;
		if (flag) {
				contactName=primaryContact;
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						ele=cp.getlastTouchPointValue(projectName, 10);
						expectedValue = M7Event3EndDate;
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+primaryContact, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+primaryContact, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+primaryContact );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			

		} else {
			sa.assertTrue(false,"Event is not been deleted so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Event is not been deleted so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc043_AgainModifyTheEndDateAndVerifyTheImpactOnTheLastTouchPointDate(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact8FName+" "+M7Contact8LName;
		String secondaryContact=M7Contact9FName+" "+M7Contact9LName;;
		M7Event3EndDate=previousOrForwardDate(-3, "M/d/YYYY");;
		String task = M7Event3Subject;
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					if (click(driver, lp.getEditButton(projectName, 30), task, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button For : "+task, YesNo.No);	
						if (sendKeys(driver, gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Event, PageLabel.End_Date.toString(),20), M7Event3EndDate, "Due Date", action.BOOLEAN)) {
							log(LogStatus.INFO, "Value Entered to Due Date "+M7Event3EndDate, YesNo.No);	
							ele=gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Event, PageLabel.Subject.toString(),20);
							click(driver, ele, "", action.BOOLEAN);
							ThreadSleep(2000);
							if (clickUsingJavaScript(driver, lp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"successfully Updated task : "+task,  YesNo.No);
								ThreadSleep(5000);
								ExcelUtils.writeData(phase1DataSheetFilePath,M7Event3EndDate, "Events", excelLabel.Variable_Name, "M7Event3", excelLabel.End_Date);
								flag=true;
								String[][] fieldsWithValues= {{PageLabel.End.toString(),M7Event3EndDate}};
								tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 30);	
							}else {
								log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+task, YesNo.Yes);
								sa.assertTrue(false,"save button is not clickable so task not Updated : "+task );
							}

						}else {
							log(LogStatus.ERROR, "Not Able to Entered Value to Due Date "+M7Event3EndDate, YesNo.Yes);	
							BaseLib.sa.assertTrue(false, "Not Able to Entered Value to Due Date "+M7Event3EndDate);	
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Edit Button For : "+task, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Edit Button For : "+task);
					}

				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	
		
		String actualValue=null;
		String expectedValue = null;
		String[] contactNames={primaryContact,secondaryContact};
		String contactName=null;
		if (flag) {
			for (int j = 0; j < contactNames.length; j++) {
				contactName=contactNames[j];
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						if (j==0) {
							expectedValue = M7Event3EndDate;
						} else {
							expectedValue = M7Event2EndDate;
						}
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			}

		} else {
			sa.assertTrue(false,"Event is not been updated so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Event is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc044_DeleteMATouchpointEvent_3AndVerifyImpactOnLastTouchPointOnContactPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact8FName+" "+M7Contact8LName;
		String secondaryContact=M7Contact9FName+" "+M7Contact9LName;
		String task = M7Event3Subject;
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					if (click(driver, cp.getDeleteButton(projectName, 20), "Delete Button", action.BOOLEAN)) {
						log(LogStatus.INFO,"Able to Click on Delete button : "+task,YesNo.No);
						ThreadSleep(2000);
						if (click(driver, cp.getDeleteButtonPopUp(projectName, 20), "Delete Button", action.BOOLEAN)) {
							log(LogStatus.INFO,"Able to Click on Delete button on PopUp : "+task,YesNo.No);
							ThreadSleep(2000);
							flag=true;
						} else {
							sa.assertTrue(false,"Able to Click on Delete button on PopUp : "+task);
							log(LogStatus.SKIP,"Able to Click on Delete button on PopUp : "+task,YesNo.Yes);
						}
					} else {
						sa.assertTrue(false,"Not Able to Click on Delete button : "+task);
						log(LogStatus.SKIP,"Not Able to Click on Delete button : "+task,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	

		String actualValue=null;
		String expectedValue = null;
		String[] contactNames={primaryContact,secondaryContact};
		String contactName=null;
		if (flag) {
			for (int j = 0; j < contactNames.length; j++) {
				contactName=contactNames[j];
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (j==0) {
								expectedValue="";
								if (actualValue.isEmpty() || actualValue.equals("")) {
									log(LogStatus.INFO,"Last Touch Point is Blank for "+contactName, YesNo.No);
								}
								else {
									log(LogStatus.ERROR, "Last Touch Point should be Blank for "+contactName+" Actual Last Touch Point Date : "+actualValue, YesNo.Yes);
									sa.assertTrue(false,"Last Touch Point should be Blank for "+contactName+" Actual Last Touch Point Date : "+actualValue );
								}
							} else {
								expectedValue=M7Event2EndDate;
								if (cp.verifyDate(expectedValue, actualValue)) {
									log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
								}
								else {
									log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
									sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
								}
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			}

		} else {
			sa.assertTrue(false,"Event is not been deleted so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Event is not been deleted so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc045_RestoreTheDeletedTaskMATouchpointEvent_1AndVerifyTheImpactOnLastTouchPointInContactPage(String projectName) {
	
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele;
		boolean flag=false;
		String restoreItem = M7Event1Subject;
		if (lp.restoreValueFromRecycleBin(projectName, restoreItem)) {
			log(LogStatus.INFO,"Able to restore item from Recycle Bin "+restoreItem,YesNo.Yes);
			flag=true;
		} else {
			sa.assertTrue(false,"Not Able to restore item from Recycle Bin "+restoreItem);
			log(LogStatus.SKIP,"Not Able to restore item from Recycle Bin "+restoreItem,YesNo.Yes);
	
		}
		String actualValue=null;
		String expectedValue = null;
		String contactName=M7Contact8FName+" "+M7Contact8LName;
		if (flag) {
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						expectedValue=M7Event1EndDate;
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			

		} else {
			sa.assertTrue(false,"Event is not been restored so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Event is not been restored so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc046_UpdatedTheEndDateAndVerifyTheImpactOnTheLastTouchPointDate(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact8FName+" "+M7Contact8LName;
		M7Event1EndDate=previousOrForwardDate(0, "M/d/YYYY");;
		String task = M7Event1Subject;
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					if (click(driver, lp.getEditButton(projectName, 30), task, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button For : "+task, YesNo.No);	
						sendKeys(driver, gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Event, PageLabel.End_Time.toString(),20), "11:30 PM", "End Time", action.BOOLEAN);
						if (sendKeys(driver, gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Event, PageLabel.End_Date.toString(),20), M7Event1EndDate, "End Date", action.BOOLEAN)) {
							log(LogStatus.INFO, "Value Entered to Due Date "+M7Event1EndDate, YesNo.No);	
							ele=gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Event, PageLabel.Subject.toString(),20);
							click(driver, ele, "", action.BOOLEAN);
							ThreadSleep(2000);
							if (clickUsingJavaScript(driver, lp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"successfully Updated task : "+task,  YesNo.No);
								ThreadSleep(5000);
								ExcelUtils.writeData(phase1DataSheetFilePath,M7Event1EndDate, "Events", excelLabel.Variable_Name, "M7Event1", excelLabel.End_Date);
								flag=true;
								String[][] fieldsWithValues= {{PageLabel.End.toString(),M7Event1EndDate}};
								tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 30);	
							}else {
								log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+task, YesNo.Yes);
								sa.assertTrue(false,"save button is not clickable so task not Updated : "+task );
							}

						}else {
							log(LogStatus.ERROR, "Not Able to Entered Value to Due Date "+M7Event1EndDate, YesNo.Yes);	
							BaseLib.sa.assertTrue(false, "Not Able to Entered Value to Due Date "+M7Event1EndDate);	
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Edit Button For : "+task, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Edit Button For : "+task);
					}

				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	
		
		String contactName=primaryContact;
		if (flag) {
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						 ele=cp.getlastTouchPointValue(projectName, 10);
						String actualValue="";
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (actualValue.isEmpty() || actualValue.equals("")) {
								log(LogStatus.INFO,"Last Touch Point is Blank for "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last Touch Point should be Blank for "+contactName+" Actual Last Touch Point Date : "+actualValue, YesNo.Yes);
								sa.assertTrue(false,"Last Touch Point should be Blank for "+contactName+" Actual Last Touch Point Date : "+actualValue );
							
							}
						}else {
							log(LogStatus.ERROR, "last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,"last touch point value is not visible For : "+contactName );
						}
					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
		
		} else {
			sa.assertTrue(false,"Event is not been updated so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Event is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc047_CreateNewEventToVerifyTheFutureDateInLastTouchPointDate(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact8FName+" "+M7Contact8LName;
		String secondaryContact=M7Contact9FName+" "+M7Contact9LName;
		M7Event4StartDate=todaysDate1;
		M7Event4EndDate=previousOrForwardDate(7, "M/d/YYYY");
		String task = M7Event4Subject;
		String[][] Event4 = {{PageLabel.Subject.toString(),task},
				{PageLabel.Start_Date.toString(),M7Event4StartDate},
				{PageLabel.End_Date.toString(),M7Event4EndDate},
				{PageLabel.Name.toString(),primaryContact},
				{PageLabel.Name.toString(),secondaryContact}};

		if (gp.clickOnGlobalActionAndEnterValue(projectName, GlobalActionItem.New_Event, Event4)) {
			log(LogStatus.INFO,"Able to Enter Value for : "+task,YesNo.No);
			ExcelUtils.writeData(phase1DataSheetFilePath,M7Event4StartDate, "Events", excelLabel.Variable_Name, "M7Event4", excelLabel.Start_Date);
			ExcelUtils.writeData(phase1DataSheetFilePath,M7Event4EndDate, "Events", excelLabel.Variable_Name, "M7Event4", excelLabel.End_Date);
			if (click(driver, gp.getSaveButtonForEvent(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);		
				flag=true;
				
			}else {
				sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
			}
		} else {
			sa.assertTrue(false,"Not Able to Click/Enter Value : "+task);
			log(LogStatus.SKIP,"Not Able to Click/Enter Value : "+task,YesNo.Yes);	
		}
		
		String actualValue=null;
		String expectedValue = null;
		String[] contactNames={primaryContact,secondaryContact};
		String contactName=null;
		if (flag) {
			for (int j = 0; j < contactNames.length; j++) {
				contactName=contactNames[j];
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						expectedValue = M7Event4EndDate;
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			}

		} else {
			sa.assertTrue(false,"Event is not been updated so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Event is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M7Tc048_CreateNewEventToVerifyAllDayEvent(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact7FName+" "+M7Contact7LName;
		M7Event5StartDate=todaysDate1;
		String task = M7Event5Subject;
		String[][] Event5 = {{PageLabel.Subject.toString(),task},
				{PageLabel.Name.toString(),primaryContact},
				{PageLabel.Start_Date.toString(),M7Event5StartDate},
				{PageLabel.Related_To.toString(),M7Ins1},
				{PageLabel.All_Day_Event.toString(),"true"}};

		if (gp.clickOnGlobalActionAndEnterValue(projectName, GlobalActionItem.New_Event, Event5)) {
			log(LogStatus.INFO,"Able to Enter Value for : "+task,YesNo.No);
			ExcelUtils.writeData(phase1DataSheetFilePath,M7Event5StartDate, "Events", excelLabel.Variable_Name, "M7Event5", excelLabel.Start_Date);
			if (click(driver, gp.getSaveButtonForEvent(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);		
				flag=true;
				
			}else {
				sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
			}
		} else {
			sa.assertTrue(false,"Not Able to Click/Enter Value : "+task);
			log(LogStatus.SKIP,"Not Able to Click/Enter Value : "+task,YesNo.Yes);	
		}
		if (flag) {
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
						ele=cp.getlastTouchPointValue(projectName, 10);
						String expectedValue = M7Event5StartDate;
						if (ele!=null) {
							String actualValue = ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+primaryContact, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+primaryContact, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+primaryContact );
						}
				} else {
					sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
			}	
		} else {
			sa.assertTrue(false,"Event is not created so cannot check Last Touch Point for "+primaryContact);
			log(LogStatus.SKIP,"Event is not created so cannot check Last Touch Point for "+primaryContact,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc049_1_VerifytheLasttouchpointOnContactWhenCallIsCreated(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact11FName+" "+M7Contact11LName;
		M7Task11dueDate=yesterdaysDate;
		String task = M7Task11Subject;
		String[][] event1 = {
				{PageLabel.Subject.toString(),task},
				{PageLabel.Due_Date.toString(),M7Task11dueDate},
				{PageLabel.Status.toString(),M7Task11Status}};

		
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					ExcelUtils.writeData(phase1DataSheetFilePath,M7Task11dueDate, "Task1", excelLabel.Variable_Name, "M7Task11", excelLabel.Due_Date);
					///////////////////////
					ele = lp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.New_Call, 10);
					if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Call.toString(), action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.New_Call.toString(),  YesNo.Yes);
						ThreadSleep(1000);
						
						ele = lp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), true, primaryContact, action.SCROLLANDBOOLEAN, 10);
						if (ele!=null) {
							log(LogStatus.INFO, "successfully verified presence of "+primaryContact+" in name field",YesNo.No);
						} else {
							sa.assertTrue(false,"not found "+ primaryContact+" For Label "+PageLabel.Name);
							log(LogStatus.SKIP,"not found "+ primaryContact+" For Label "+PageLabel.Name,YesNo.Yes);

						}
						gp.enterValueForTask(projectName, PageName.Object2Page, event1, 20);
						if (clickUsingJavaScript(driver, gp.getCustomTabSaveBtn(projectName, 2), "Save Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
							ThreadSleep(5000);
							flag=true;
							
						}else {
							sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
							log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
						}
						
					}
					else {
						log(LogStatus.ERROR, "not able to click on "+ActivityTimeLineItem.New_Call.toString(), YesNo.Yes);
						sa.assertTrue(false,"not able to click on "+ActivityTimeLineItem.New_Call.toString() );
					}
					//////////////////////
					refresh(driver);
					ThreadSleep(3000);
					ele=cp.getlastTouchPointValue(projectName, 10);
					String expectedValue = M7Task11dueDate;
					if (ele!=null) {
						String actualValue = ele.getText().trim();
						if (cp.verifyDate(expectedValue, actualValue)) {
							log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+primaryContact, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "Last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
							sa.assertTrue(false,"last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue );
						}
					}else {
						log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+primaryContact, YesNo.Yes);
						sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+primaryContact );
					}
				} else {
					sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
			}	
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M7Tc049_2_VerifytheLasttouchpointOnContactWhenEventIsCreated(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact11FName+" "+M7Contact11LName;
		M7Event6StartDate=previousOrForwardDate(-10, "M/d/YYYY");;
		M7Event6EndDate=previousOrForwardDate(-7, "M/d/YYYY");;
		String task = M7Event6Subject;
		String[][] Event6 = {{PageLabel.Subject.toString(),task},
				{PageLabel.Start_Date.toString(),M7Event6StartDate},
				{PageLabel.End_Date.toString(),M7Event6EndDate},
				{PageLabel.Name.toString(),primaryContact}};

		if (gp.clickOnGlobalActionAndEnterValue(projectName, GlobalActionItem.New_Event, Event6)) {
			log(LogStatus.INFO,"Able to Enter Value for : "+task,YesNo.No);
			ExcelUtils.writeData(phase1DataSheetFilePath,M7Event6StartDate, "Events", excelLabel.Variable_Name, "M7Event6", excelLabel.Start_Date);
			ExcelUtils.writeData(phase1DataSheetFilePath,M7Event6EndDate, "Events", excelLabel.Variable_Name, "M7Event6", excelLabel.End_Date);
			
			if (click(driver, gp.getSaveButtonForEvent(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);		
				flag=true;
				
			}else {
				sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
			}
		} else {
			sa.assertTrue(false,"Not Able to Click/Enter Value : "+task);
			log(LogStatus.SKIP,"Not Able to Click/Enter Value : "+task,YesNo.Yes);	
		}
		if (flag) {
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
						ele=cp.getlastTouchPointValue(projectName, 10);
						String expectedValue = M7Task11dueDate;
						if (ele!=null) {
							String actualValue = ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+primaryContact, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+primaryContact, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+primaryContact );
						}
				} else {
					sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
			}	
		} else {
			sa.assertTrue(false,"Event is not created so cannot check Last Touch Point for "+primaryContact);
			log(LogStatus.SKIP,"Event is not created so cannot check Last Touch Point for "+primaryContact,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc050_DeleteTheCreatedCallAndVerifyTheImpactOnLastTouchPointInContactPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact11FName+" "+M7Contact11LName;
		String task = M7Task11Subject;
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					if (click(driver, cp.getDeleteButton(projectName, 20), "Delete Button", action.BOOLEAN)) {
						log(LogStatus.INFO,"Able to Click on Delete button : "+task,YesNo.No);
						ThreadSleep(2000);
						if (click(driver, cp.getDeleteButtonPopUp(projectName, 20), "Delete Button", action.BOOLEAN)) {
							log(LogStatus.INFO,"Able to Click on Delete button on PopUp : "+task,YesNo.No);
							ThreadSleep(2000);
							flag=true;
						} else {
							sa.assertTrue(false,"Able to Click on Delete button on PopUp : "+task);
							log(LogStatus.SKIP,"Able to Click on Delete button on PopUp : "+task,YesNo.Yes);
						}
					} else {
						sa.assertTrue(false,"Not Able to Click on Delete button : "+task);
						log(LogStatus.SKIP,"Not Able to Click on Delete button : "+task,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	

		String actualValue=null;
		String expectedValue = null;
		String contactName=null;
		if (flag) {
				contactName=primaryContact;
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							expectedValue=M7Event6EndDate;
								if (cp.verifyDate(expectedValue, actualValue)) {
									log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
								}
								else {
									log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
									sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
								}
							
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			

		} else {
			sa.assertTrue(false,"Call is not been deleted so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Call is not been deleted so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc051_1_VerifytheLasttouchpointOnContactWhenCallIsCreated(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact11FName+" "+M7Contact11LName;
		M7Task12dueDate=previousOrForwardDate(-3, "M/d/YYYY");;
		String task = M7Task12Subject;
		String[][] event1 = {
				{PageLabel.Subject.toString(),task},
				{PageLabel.Due_Date.toString(),M7Task12dueDate},
				{PageLabel.Status.toString(),M7Task12Status}};

		
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					ExcelUtils.writeData(phase1DataSheetFilePath,M7Task12dueDate, "Task1", excelLabel.Variable_Name, "M7Task12", excelLabel.Due_Date);
					///////////////////////
					ele = lp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.New_Call, 10);
					if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Call.toString(), action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.New_Call.toString(),  YesNo.Yes);
						ThreadSleep(1000);
						
						ele = lp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), true, primaryContact, action.SCROLLANDBOOLEAN, 10);
						if (ele!=null) {
							log(LogStatus.INFO, "successfully verified presence of "+primaryContact+" in name field",YesNo.No);
						} else {
							sa.assertTrue(false,"not found "+ primaryContact+" For Label "+PageLabel.Name);
							log(LogStatus.SKIP,"not found "+ primaryContact+" For Label "+PageLabel.Name,YesNo.Yes);

						}
						gp.enterValueForTask(projectName, PageName.Object2Page, event1, 20);
						if (clickUsingJavaScript(driver, gp.getCustomTabSaveBtn(projectName, 2), "Save Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
							ThreadSleep(5000);
							flag=true;
							
						}else {
							sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
							log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
						}
						
					}
					else {
						log(LogStatus.ERROR, "not able to click on "+ActivityTimeLineItem.New_Call.toString(), YesNo.Yes);
						sa.assertTrue(false,"not able to click on "+ActivityTimeLineItem.New_Call.toString() );
					}
					//////////////////////
					refresh(driver);
					ThreadSleep(3000);
					ele=cp.getlastTouchPointValue(projectName, 10);
					String expectedValue = M7Task12dueDate;
					if (ele!=null) {
						String actualValue = ele.getText().trim();
						if (cp.verifyDate(expectedValue, actualValue)) {
							log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+primaryContact, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "Last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
							sa.assertTrue(false,"last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue );
						}
					}else {
						log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+primaryContact, YesNo.Yes);
						sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+primaryContact );
					}
				} else {
					sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
			}	
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M7Tc051_2_VerifytheLasttouchpointOnContactWhenEventIsCreated(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact11FName+" "+M7Contact11LName;
		M7Event7StartDate=previousOrForwardDate(-3, "M/d/YYYY");;
		M7Event7EndDate=previousOrForwardDate(0, "M/d/YYYY");;
		String task = M7Event7Subject;
		String[][] Event7 = {{PageLabel.Subject.toString(),task},
				{PageLabel.Start_Date.toString(),M7Event7StartDate},
				{PageLabel.End_Date.toString(),M7Event7EndDate},
				{PageLabel.Name.toString(),primaryContact}};

		if (gp.clickOnGlobalActionAndEnterValue(projectName, GlobalActionItem.New_Event, Event7)) {
			log(LogStatus.INFO,"Able to Enter Value for : "+task,YesNo.No);
			ExcelUtils.writeData(phase1DataSheetFilePath,M7Event7StartDate, "Events", excelLabel.Variable_Name, "M7Event7", excelLabel.Start_Date);
			ExcelUtils.writeData(phase1DataSheetFilePath,M7Event7EndDate, "Events", excelLabel.Variable_Name, "M7Event7", excelLabel.End_Date);
			
			if (click(driver, gp.getSaveButtonForEvent(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);		
				flag=true;
				
			}else {
				sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
			}
		} else {
			sa.assertTrue(false,"Not Able to Click/Enter Value : "+task);
			log(LogStatus.SKIP,"Not Able to Click/Enter Value : "+task,YesNo.Yes);	
		}
		if (flag) {
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
						ele=cp.getlastTouchPointValue(projectName, 10);
						String expectedValue = M7Event7EndDate;
						if (ele!=null) {
							String actualValue = ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+primaryContact, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+primaryContact, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+primaryContact );
						}
				} else {
					sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
			}	
		} else {
			sa.assertTrue(false,"Event is not created so cannot check Last Touch Point for "+primaryContact);
			log(LogStatus.SKIP,"Event is not created so cannot check Last Touch Point for "+primaryContact,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc052_DeleteTheCreatedEventAndVerifyTheImpactOnLastTouchPointInContactPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact11FName+" "+M7Contact11LName;
		String task = M7Event7Subject;
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					if (click(driver, cp.getDeleteButton(projectName, 20), "Delete Button", action.BOOLEAN)) {
						log(LogStatus.INFO,"Able to Click on Delete button : "+task,YesNo.No);
						ThreadSleep(2000);
						if (click(driver, cp.getDeleteButtonPopUp(projectName, 20), "Delete Button", action.BOOLEAN)) {
							log(LogStatus.INFO,"Able to Click on Delete button on PopUp : "+task,YesNo.No);
							ThreadSleep(2000);
							flag=true;
						} else {
							sa.assertTrue(false,"Able to Click on Delete button on PopUp : "+task);
							log(LogStatus.SKIP,"Able to Click on Delete button on PopUp : "+task,YesNo.Yes);
						}
					} else {
						sa.assertTrue(false,"Not Able to Click on Delete button : "+task);
						log(LogStatus.SKIP,"Not Able to Click on Delete button : "+task,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	

		String actualValue=null;
		String expectedValue = null;
		String contactName=null;
		if (flag) {
				contactName=primaryContact;
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							expectedValue=M7Task12dueDate;
								if (cp.verifyDate(expectedValue, actualValue)) {
									log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
								}
								else {
									log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
									sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
								}
							
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			

		} else {
			sa.assertTrue(false,"Call is not been deleted so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Call is not been deleted so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc053_CreateEntityAndContact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		// contact
		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			M7Contact12EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, M7Contact12EmailID, "Contacts", excelLabel.Variable_Name, "M7CON12",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, M7Contact12FName, M7Contact12LName, M7Ins1, M7Contact12EmailID,M7Contact12RecordType, null, null, CreationPage.ContactPage, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+M7Contact12FName+" "+M7Contact12LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+M7Contact12FName+" "+M7Contact12LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+M7Contact12FName+" "+M7Contact12LName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
		
		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			M7Contact13EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, M7Contact13EmailID, "Contacts", excelLabel.Variable_Name, "M7CON13",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, M7Contact13FName, M7Contact13LName, M7Ins1, M7Contact13EmailID,M7Contact13RecordType, null, null, CreationPage.ContactPage, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+M7Contact13FName+" "+M7Contact13LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+M7Contact13FName+" "+M7Contact13LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+M7Contact13FName+" "+M7Contact13LName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
		
		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			M7Contact14EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, M7Contact14EmailID, "Contacts", excelLabel.Variable_Name, "M7CON14",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, M7Contact14FName, M7Contact14LName, M7Ins1, M7Contact14EmailID,M7Contact14RecordType, null, null, CreationPage.ContactPage, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+M7Contact14FName+" "+M7Contact14LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+M7Contact14FName+" "+M7Contact14LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+M7Contact14FName+" "+M7Contact14LName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
		
		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			M7Contact15EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, M7Contact15EmailID, "Contacts", excelLabel.Variable_Name, "M7CON15",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, M7Contact15FName, M7Contact15LName, M7Ins1, M7Contact15EmailID,M7Contact15RecordType, null, null, CreationPage.ContactPage, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+M7Contact15FName+" "+M7Contact15LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+M7Contact15FName+" "+M7Contact15LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+M7Contact15FName+" "+M7Contact15LName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
		
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc054_CreateMeetingForContactMindyKallingAndverifyLastTouchpointonContactDetailPage(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact12FName+" "+M7Contact12LName;
		M7Task13dueDate=yesterdaysDate;
		String task = M7Task13Subject;
		String[][] event1 = {
				{PageLabel.Subject.toString(),task},
				{PageLabel.Due_Date.toString(),M7Task13dueDate},
				{PageLabel.Status.toString(),M7Task13Status}};

		
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					ExcelUtils.writeData(phase1DataSheetFilePath,M7Task13dueDate, "Task1", excelLabel.Variable_Name, "M7Task13", excelLabel.Due_Date);
					///////////////////////
					ele = lp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.New_Meeting, 10);
					if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Meeting.toString(), action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.New_Meeting.toString(),  YesNo.Yes);
						ThreadSleep(1000);
						
						ele = lp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), true, primaryContact, action.SCROLLANDBOOLEAN, 10);
						if (ele!=null) {
							log(LogStatus.INFO, "successfully verified presence of "+primaryContact+" in name field",YesNo.No);
						} else {
							sa.assertTrue(false,"not found "+ primaryContact+" For Label "+PageLabel.Name);
							log(LogStatus.SKIP,"not found "+ primaryContact+" For Label "+PageLabel.Name,YesNo.Yes);

						}
						gp.enterValueForTask(projectName, PageName.Object2Page, event1, 20);
						if (clickUsingJavaScript(driver, gp.getCustomTabSaveBtn(projectName, 2), "Save Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
							ThreadSleep(5000);
							flag=true;
							
						}else {
							sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
							log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
						}
						
					}
					else {
						log(LogStatus.ERROR, "not able to click on "+ActivityTimeLineItem.New_Meeting.toString(), YesNo.Yes);
						sa.assertTrue(false,"not able to click on "+ActivityTimeLineItem.New_Meeting.toString() );
					}
					//////////////////////
					refresh(driver);
					ThreadSleep(3000);
					ele=cp.getlastTouchPointValue(projectName, 10);
					String expectedValue = M7Task13dueDate;
					if (ele!=null) {
						String actualValue=ele.getText().trim();
						if (actualValue.isEmpty() || actualValue.equals("")) {
							log(LogStatus.INFO,"Last Touch Point is Blank for "+primaryContact, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "Last Touch Point should be Blank for "+primaryContact+" Actual Last Touch Point Date : "+actualValue, YesNo.Yes);
							sa.assertTrue(false,"Last Touch Point should be Blank for "+primaryContact+" Actual Last Touch Point Date : "+actualValue );
						}
					}else {
						log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+primaryContact, YesNo.Yes);
						sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+primaryContact );
					}

				} else {
					sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
			}	
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc055_CreateMultiTaggedMeetingforContactParkerHarrisAndVerifyLastTouchPoint(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact13FName+" "+M7Contact13LName;
		String secondaryContact=M7Contact14FName+" "+M7Contact14LName;
		M7Task14dueDate=previousOrForwardDate(2, "M/d/YYYY");;
		String task = M7Task14Subject;
		String[][] event1 = {
				{PageLabel.Name.toString(),secondaryContact},
				{PageLabel.Subject.toString(),task},
				{PageLabel.Due_Date.toString(),M7Task14dueDate},
				{PageLabel.Meeting_Type.toString(),meetingNone},
				{PageLabel.Status.toString(),M7Task14Status}};

		
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					ExcelUtils.writeData(phase1DataSheetFilePath,M7Task14dueDate, "Task1", excelLabel.Variable_Name, "M7Task14", excelLabel.Due_Date);
					///////////////////////
					ele = lp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.New_Meeting, 10);
					if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Meeting.toString(), action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.New_Meeting.toString(),  YesNo.Yes);
						ThreadSleep(1000);
						
						ele = lp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), true, primaryContact, action.SCROLLANDBOOLEAN, 10);
						if (ele!=null) {
							log(LogStatus.INFO, "successfully verified presence of "+primaryContact+" in name field",YesNo.No);
						} else {
							sa.assertTrue(false,"not found "+ primaryContact+" For Label "+PageLabel.Name);
							log(LogStatus.SKIP,"not found "+ primaryContact+" For Label "+PageLabel.Name,YesNo.Yes);

						}
						
						gp.enterValueForTask(projectName, PageName.Object2Page, event1, 20);
						String relatedValue=M7Ins1;
						flag = lp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object2Page, PageLabel.Related_Associations.toString(), TabName.Object1Tab, relatedValue, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.INFO,"Selected "+relatedValue+" For Label "+PageLabel.Related_Associations,YesNo.No);

						} else {
							sa.assertTrue(false,"Not Able to Select "+relatedValue+" For Label "+PageLabel.Related_Associations);
							log(LogStatus.SKIP,"Not Able to Select "+relatedValue+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

						}
						
						if (clickUsingJavaScript(driver, gp.getCustomTabSaveBtn(projectName, 2), "Save Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
							ThreadSleep(5000);
							flag=true;
							
						}else {
							sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
							log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
						}
						
					}
					else {
						log(LogStatus.ERROR, "not able to click on "+ActivityTimeLineItem.New_Meeting.toString(), YesNo.Yes);
						sa.assertTrue(false,"not able to click on "+ActivityTimeLineItem.New_Meeting.toString() );
					}
					//////////////////////
					//refresh(driver);
					ele = lp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Refresh, 10);
					click(driver, ele, ActivityTimeLineItem.Refresh.toString(), action.BOOLEAN);
					ThreadSleep(3000);
					ele=cp.getlastTouchPointValue(projectName, 10);
					String value="";
					if (ele!=null) {
						value=ele.getText().trim();
						if (value.isEmpty() || value.equals("")) {
							log(LogStatus.INFO,"Last Touch Point is Blank for "+primaryContact, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "Last Touch Point should be Blank for "+primaryContact, YesNo.Yes);
							sa.assertTrue(false,"Last Touch Point should be Blank for "+primaryContact );
						}
					}else {
						log(LogStatus.ERROR, "last touch point value is not visible For : "+primaryContact, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not visible For : "+primaryContact );
					}
				} else {
					sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
			}	
			
			
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+secondaryContact,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, secondaryContact, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+secondaryContact+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					
					ele=cp.getlastTouchPointValue(projectName, 10);
					String value="";
					if (ele!=null) {
						value=ele.getText().trim();
						if (value.isEmpty() || value.equals("")) {
							log(LogStatus.INFO,"Last Touch Point is Blank for "+secondaryContact, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "Last Touch Point should be Blank for "+secondaryContact, YesNo.Yes);
							sa.assertTrue(false,"Last Touch Point should be Blank for "+secondaryContact );
						}
					}else {
						log(LogStatus.ERROR, "last touch point value is not visible For : "+secondaryContact, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not visible For : "+secondaryContact );
					}
				} else {
					sa.assertTrue(false,"Item Not Found : "+secondaryContact+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+secondaryContact+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+secondaryContact);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+secondaryContact,YesNo.Yes);
			}	
			
			
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc056_UpdateMeetingTypeOfMeeting1InStandardTask(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
		boolean flag=false;
		TabName[] tabNames = {TabName.TaskTab};
		String[] taskNames = {M7Task13Subject};
		PageName[] pageNames = {PageName.TaskPage};
		
		TabName tabName ;
		int i=0;
		for (String task : taskNames) {
			task=taskNames[i];
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+task,YesNo.No);
				ele=tp.getTaskNameLinkInSideMMenu(projectName, task, 30);
				if (click(driver, ele, task, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on "+task+" on Task SideMenu", YesNo.No);	
					if (click(driver, tp.getEditButton(projectName, 30), task, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button For : "+task, YesNo.No);	
							if (tp.selectDropDownValueonTaskPopUp(projectName, pageNames[i], PageLabel.Meeting_Type.toString(), M7Task13MeetingType, action.BOOLEAN, 10)) {
								log(LogStatus.INFO, "Selected : "+M7Task13MeetingType+" For Label : "+PageLabel.Meeting_Type.toString(), YesNo.No);	
								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"successfully Updated task : "+task,  YesNo.No);
									ThreadSleep(5000);
									flag=true;
									String[][] fieldsWithValues= {{PageLabel.Meeting_Type.toString(),M7Task13MeetingType}};
									tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 30);	
								}else {
									log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+task, YesNo.Yes);
									sa.assertTrue(false,"save button is not clickable so task not Updated : "+task );
								}
							}else {
								log(LogStatus.ERROR, "Not Able to Select : "+M7Task13MeetingType+" For Label : "+PageLabel.Meeting_Type.toString(), YesNo.Yes);	
								BaseLib.sa.assertTrue(false, "Not Able to Select : "+M7Task13MeetingType+" For Label : "+PageLabel.Meeting_Type.toString());	
							}
					} else {
						log(LogStatus.ERROR, "Not Able to Click on Edit Button For : "+task, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Edit Button For : "+task);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+task+" on Task SideMenu", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+task+" on Task SideMenu");
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+task,YesNo.Yes);
			}
			i++;
			switchToDefaultContent(driver);
			ThreadSleep(5000);
		}
		String contactName=M7Contact12FName+" "+M7Contact12LName;
		if (flag) {
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					 ele=cp.getlastTouchPointValue(projectName, 10);
					if (ele!=null) {
						String value=ele.getText().trim();
						if (cp.verifyDate(M7Task13dueDate, value)) {
							log(LogStatus.INFO,M7Task13dueDate+" successfully verified last touch point date For : "+contactName, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, M7Task13dueDate+" last touch point value is not matched For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,M7Task13dueDate+" last touch point value is not matched For : "+contactName );
						}
					}else {
						log(LogStatus.ERROR, "last touch point value is not visible For : "+contactName, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not visible For : "+contactName );
					}
				} else {
					sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
			}	
		} else {
			sa.assertTrue(false,"Task is not been updated so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc057_UpdateMeetingTypeOfMeeting2InStandardTask(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
		boolean flag=false;
		TabName[] tabNames = {TabName.TaskTab};
		String[] taskNames = {M7Task14Subject};
		PageName[] pageNames = {PageName.Object2Page};
		
		TabName tabName ;
		int i=0;
		for (String task : taskNames) {
			task=taskNames[i];
			tabName=tabNames[i];
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+task,YesNo.No);
				ele=tp.getTaskNameLinkInSideMMenu(projectName, task, 30);
				if (click(driver, ele, task, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on "+task+" on Task SideMenu", YesNo.No);	
					if (click(driver, tp.getEditButton(projectName, 30), task, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button For : "+task, YesNo.No);	
						if (tp.selectDropDownValueonTaskPopUp(projectName, pageNames[i], PageLabel.Status.toString(), statusCompleted, action.BOOLEAN, 10)) {
							log(LogStatus.INFO, "Selected : "+statusCompleted+" For Label : "+PageLabel.Status.toString(), YesNo.No);	
							if (tp.selectDropDownValueonTaskPopUp(projectName, pageNames[i], PageLabel.Meeting_Type.toString(), M7Task14MeetingType, action.BOOLEAN, 10)) {
								log(LogStatus.INFO, "Selected : "+M7Task14MeetingType+" For Label : "+PageLabel.Meeting_Type.toString(), YesNo.No);	
								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"successfully Updated task : "+task,  YesNo.No);
									ThreadSleep(5000);
									flag=true;
									String[][] fieldsWithValues= {{PageLabel.Status.toString(),statusCompleted},{PageLabel.Meeting_Type.toString(),M7Task14MeetingType}};
									tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 30);	
								}else {
									log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+task, YesNo.Yes);
									sa.assertTrue(false,"save button is not clickable so task not Updated : "+task );
								}
							}else {
								log(LogStatus.ERROR, "Not Able to Select : "+M7Task14MeetingType+" For Label : "+PageLabel.Meeting_Type.toString(), YesNo.Yes);	
								BaseLib.sa.assertTrue(false, "Not Able to Select : "+M7Task14MeetingType+" For Label : "+PageLabel.Meeting_Type.toString());	
							}
						}else {
							log(LogStatus.ERROR, "Not Able to Select : "+statusCompleted+" For Label : "+PageLabel.Status.toString(), YesNo.Yes);	
							BaseLib.sa.assertTrue(false, "Not Able to Select : "+statusCompleted+" For Label : "+PageLabel.Status.toString());	
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Edit Button For : "+task, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Edit Button For : "+task);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+task+" on Task SideMenu", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+task+" on Task SideMenu");
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+task,YesNo.Yes);
			}
			i++;
			switchToDefaultContent(driver);
			ThreadSleep(5000);
		}
		String primaryContact=M7Contact13FName+" "+M7Contact13LName;
		String secondaryContact=M7Contact14FName+" "+M7Contact14LName;
		String actualValue=null;
		String expectedValue = null;
		String[] contactNames={primaryContact,secondaryContact};
		String contactName=null;
		if (flag) {
			for (int j = 0; j < contactNames.length; j++) {
				contactName=contactNames[j];
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						expectedValue = M7Task14dueDate;
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			}

		} else {
			sa.assertTrue(false,"Meeting is not been updated so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Meeting is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc058_CreateMeetingforContactMindyKallingAndVerifyLastTouchPoint(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact12FName+" "+M7Contact12LName;
		String secondaryContact=M7Contact14FName+" "+M7Contact14LName;
		M7Task15dueDate=previousOrForwardDate(-5, "M/d/YYYY");;
		String task = M7Task15Subject;
		String[][] event1 = {
				{PageLabel.Name.toString(),secondaryContact},
				{PageLabel.Subject.toString(),task},
				{PageLabel.Due_Date.toString(),M7Task15dueDate},
				{PageLabel.Meeting_Type.toString(),meetingNone},
				{PageLabel.Status.toString(),M7Task15Status}};

		
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					ExcelUtils.writeData(phase1DataSheetFilePath,M7Task15dueDate, "Task1", excelLabel.Variable_Name, "M7Task15", excelLabel.Due_Date);
					///////////////////////
					ele = lp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.New_Meeting, 10);
					if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Meeting.toString(), action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.New_Meeting.toString(),  YesNo.Yes);
						ThreadSleep(1000);
						
						ele = lp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), true, primaryContact, action.SCROLLANDBOOLEAN, 10);
						if (ele!=null) {
							log(LogStatus.INFO, "successfully verified presence of "+primaryContact+" in name field",YesNo.No);
						} else {
							sa.assertTrue(false,"not found "+ primaryContact+" For Label "+PageLabel.Name);
							log(LogStatus.SKIP,"not found "+ primaryContact+" For Label "+PageLabel.Name,YesNo.Yes);

						}
						
						gp.enterValueForTask(projectName, PageName.Object2Page, event1, 20);
						
						if (clickUsingJavaScript(driver, gp.getCustomTabSaveBtn(projectName, 2), "Save Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
							ThreadSleep(5000);
							flag=true;
							
						}else {
							sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
							log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
						}
						
					}
					else {
						log(LogStatus.ERROR, "not able to click on "+ActivityTimeLineItem.New_Meeting.toString(), YesNo.Yes);
						sa.assertTrue(false,"not able to click on "+ActivityTimeLineItem.New_Meeting.toString() );
					}
					//////////////////////
					//refresh(driver);
					ele = lp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Refresh, 10);
					click(driver, ele, ActivityTimeLineItem.Refresh.toString(), action.BOOLEAN);
					ThreadSleep(3000);
					ele=cp.getlastTouchPointValue(projectName, 10);
					String expectedValue = M7Task13dueDate;
					ele=cp.getlastTouchPointValue(projectName, 10);
					if (ele!=null) {
						String actualValue=ele.getText().trim();
						if (cp.verifyDate(expectedValue, actualValue)) {
							log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+primaryContact, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "Last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
							sa.assertTrue(false,"last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue );
						}
					}else {
						log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+primaryContact, YesNo.Yes);
						sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+primaryContact );
					}
				} else {
					sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
			}	
			
			
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+secondaryContact,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, secondaryContact, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+secondaryContact+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					String expectedValue = M7Task14dueDate;
					ele=cp.getlastTouchPointValue(projectName, 10);
					if (ele!=null) {
						String actualValue = ele.getText().trim();
						if (cp.verifyDate(expectedValue, actualValue)) {
							log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+secondaryContact, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "Last touch point value is not matched For : "+secondaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
							sa.assertTrue(false,"last touch point value is not matched For : "+secondaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue );
						}
					}else {
						log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+secondaryContact, YesNo.Yes);
						sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+secondaryContact );
					}
				} else {
					sa.assertTrue(false,"Item Not Found : "+secondaryContact+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+secondaryContact+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+secondaryContact);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+secondaryContact,YesNo.Yes);
			}	
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc059_UpdateStatusToProgressOfMeeting2AndVerifyLastTouchPoint(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact13FName+" "+M7Contact13LName;
		String secondaryContact=M7Contact14FName+" "+M7Contact14LName;
		String task = M7Task14Subject;

		PageName pageName = PageName.Object2Page;

		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					if (click(driver, tp.getEditButton(projectName, 30), task, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button For : "+task, YesNo.No);	
						if (tp.selectDropDownValueonTaskPopUp(projectName, pageName, PageLabel.Status.toString(), statusInProgess, action.BOOLEAN, 10)) {
							log(LogStatus.INFO, "Selected : "+statusInProgess+" For Label : "+PageLabel.Status.toString(), YesNo.No);	
							if (clickUsingJavaScript(driver, lp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"successfully Updated task : "+task,  YesNo.No);
								ThreadSleep(5000);
								flag=true;
								String[][] fieldsWithValues= {{PageLabel.Status.toString(),statusInProgess}};
								tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 30);	
							}else {
								log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+task, YesNo.Yes);
								sa.assertTrue(false,"save button is not clickable so task not Updated : "+task );
							}

						}else {
							log(LogStatus.ERROR, "Not Able to Select : "+statusInProgess+" For Label : "+PageLabel.Status.toString(), YesNo.Yes);	
							BaseLib.sa.assertTrue(false, "Not Able to Select : "+statusInProgess+" For Label : "+PageLabel.Status.toString());	
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Edit Button For : "+task, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Edit Button For : "+task);
					}

				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	
		
		String actualValue=null;
		String expectedValue = null;
		String[] contactNames={primaryContact,secondaryContact};
		String contactName=null;
		if (flag) {
			for (int j = 0; j < contactNames.length; j++) {
				contactName=contactNames[j];
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (j==0) {
								if (actualValue.isEmpty() || actualValue.equals("")) {
									log(LogStatus.INFO,"Last Touch Point is Blank for "+contactName, YesNo.No);
								}
								else {
									log(LogStatus.ERROR, "Last Touch Point should be Blank for "+contactName+" Actual Last Touch Point Date : "+actualValue, YesNo.Yes);
									sa.assertTrue(false,"Last Touch Point should be Blank for "+contactName+" Actual Last Touch Point Date : "+actualValue );
								}
							} else {
								expectedValue = M7Task15dueDate;
								if (cp.verifyDate(expectedValue, actualValue)) {
									log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
								}
								else {
									log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
									sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
								}
							}
							
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}
						
					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			}
			
		} else {
			sa.assertTrue(false,"Task is not been updated so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc060_DeleteMATouchpointMeeting_1AndVerifyImpactOnLastTouchPointInContactPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact12FName+" "+M7Contact12LName;
		String task = M7Task13Subject;
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					if (click(driver, cp.getDeleteButton(projectName, 20), "Delete Button", action.BOOLEAN)) {
						log(LogStatus.INFO,"Able to Click on Delete button : "+task,YesNo.No);
						ThreadSleep(2000);
						if (click(driver, cp.getDeleteButtonPopUp(projectName, 20), "Delete Button", action.BOOLEAN)) {
							log(LogStatus.INFO,"Able to Click on Delete button on PopUp : "+task,YesNo.No);
							ThreadSleep(2000);
							flag=true;
						} else {
							sa.assertTrue(false,"Able to Click on Delete button on PopUp : "+task);
							log(LogStatus.SKIP,"Able to Click on Delete button on PopUp : "+task,YesNo.Yes);
						}
					} else {
						sa.assertTrue(false,"Not Able to Click on Delete button : "+task);
						log(LogStatus.SKIP,"Not Able to Click on Delete button : "+task,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	

		String actualValue=null;
		String expectedValue = "";
		String contactName=primaryContact;
		if (flag) {
				contactName=primaryContact;
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							expectedValue = M7Task15dueDate;
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			

		} else {
			sa.assertTrue(false,"Task is not been deleted so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been deleted so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc061_AgainChangeTheStausFromInProgressToCompletedAndVerifyTheImpactOnTheLastTouchPointDate(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact13FName+" "+M7Contact13LName;
		String secondaryContact=M7Contact14FName+" "+M7Contact14LName;
		String task = M7Task14Subject;

		PageName pageName = PageName.Object2Page;

		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					if (click(driver, tp.getEditButton(projectName, 30), task, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button For : "+task, YesNo.No);	
						if (tp.selectDropDownValueonTaskPopUp(projectName, pageName, PageLabel.Status.toString(), statusCompleted, action.BOOLEAN, 10)) {
							log(LogStatus.INFO, "Selected : "+statusCompleted+" For Label : "+PageLabel.Status.toString(), YesNo.No);	
							if (clickUsingJavaScript(driver, lp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"successfully Updated task : "+task,  YesNo.No);
								ThreadSleep(5000);
								flag=true;
								String[][] fieldsWithValues= {{PageLabel.Status.toString(),statusCompleted}};
								tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 30);	
							}else {
								log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+task, YesNo.Yes);
								sa.assertTrue(false,"save button is not clickable so task not Updated : "+task );
							}

						}else {
							log(LogStatus.ERROR, "Not Able to Select : "+statusCompleted+" For Label : "+PageLabel.Status.toString(), YesNo.Yes);	
							BaseLib.sa.assertTrue(false, "Not Able to Select : "+statusCompleted+" For Label : "+PageLabel.Status.toString());	
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Edit Button For : "+task, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Edit Button For : "+task);
					}

				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	
		
		String actualValue=null;
		String expectedValue = null;
		String[] contactNames={primaryContact,secondaryContact};
		String contactName=null;
		if (flag) {
			for (int j = 0; j < contactNames.length; j++) {
				contactName=contactNames[j];
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						expectedValue = M7Task14dueDate;
						
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}
						
					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			}
			
		} else {
			sa.assertTrue(false,"Task is not been updated so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc062_DeleteNewlyUpdatedTaskAndVerifyTheImpactOnLastTouchPointInContactPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact13FName+" "+M7Contact13LName;
		String secondaryContact=M7Contact14FName+" "+M7Contact14LName;
		String task = M7Task14Subject;
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					if (click(driver, cp.getDeleteButton(projectName, 20), "Delete Button", action.BOOLEAN)) {
						log(LogStatus.INFO,"Able to Click on Delete button : "+task,YesNo.No);
						ThreadSleep(2000);
						if (click(driver, cp.getDeleteButtonPopUp(projectName, 20), "Delete Button", action.BOOLEAN)) {
							log(LogStatus.INFO,"Able to Click on Delete button on PopUp : "+task,YesNo.No);
							ThreadSleep(2000);
							flag=true;
						} else {
							sa.assertTrue(false,"Able to Click on Delete button on PopUp : "+task);
							log(LogStatus.SKIP,"Able to Click on Delete button on PopUp : "+task,YesNo.Yes);
						}
					} else {
						sa.assertTrue(false,"Not Able to Click on Delete button : "+task);
						log(LogStatus.SKIP,"Not Able to Click on Delete button : "+task,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	

		String actualValue=null;
		String expectedValue = null;
		String[] contactNames={primaryContact,secondaryContact};
		String contactName=null;
		if (flag) {
			for (int j = 0; j < contactNames.length; j++) {
				contactName=contactNames[j];
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (j==0) {
								expectedValue="";
								if (actualValue.isEmpty() || actualValue.equals("")) {
									log(LogStatus.INFO,"Last Touch Point is Blank for "+contactName, YesNo.No);
								}
								else {
									log(LogStatus.ERROR, "Last Touch Point should be Blank for "+contactName+" Actual Last Touch Point Date : "+actualValue, YesNo.Yes);
									sa.assertTrue(false,"Last Touch Point should be Blank for "+contactName+" Actual Last Touch Point Date : "+actualValue );
								}
							} else {
								expectedValue=M7Task15dueDate;
								if (cp.verifyDate(expectedValue, actualValue)) {
									log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
								}
								else {
									log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
									sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
								}
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			}

		} else {
			sa.assertTrue(false,"Task is not been deleted so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been deleted so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc063_RestoreTheDeletedTaskMATouchpointMeeting_1AndVerifyTheImpactOnLastTouchPointInContactPage(String projectName) {
	
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele;
		boolean flag=false;
		String restoreItem = M7Task13Subject;
		if (lp.restoreValueFromRecycleBin(projectName, restoreItem)) {
			log(LogStatus.INFO,"Able to restore item from Recycle Bin "+restoreItem,YesNo.Yes);
			flag=true;
		} else {
			sa.assertTrue(false,"Not Able to restore item from Recycle Bin "+restoreItem);
			log(LogStatus.SKIP,"Not Able to restore item from Recycle Bin "+restoreItem,YesNo.Yes);
	
		}
		String actualValue=null;
		String expectedValue = null;
		String contactName=M7Contact12FName+" "+M7Contact12LName;
		if (flag) {
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						expectedValue=M7Task13dueDate;
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			

		} else {
			sa.assertTrue(false,"Task is not been restored so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been restored so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc064_UpdateDueDateOfMATouchpointMeeting_1AndVerifyImpactOnLastTouchPoint(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
		boolean flag=false;
		String task=M7Task13Subject;
		M7Task13dueDate=todaysDate1;
		TabName tabName=TabName.TaskTab;
		
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+task,YesNo.No);
				ele=tp.getTaskNameLinkInSideMMenu(projectName, task, 30);
				if (click(driver, ele, task, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on "+task+" on Task SideMenu", YesNo.No);	
					String date = M7Task13dueDate;
					if (tp.EditEnterDueDateAndSave(projectName, task, date)) {
						log(LogStatus.INFO, "Value Entered & saved to Due Date "+date, YesNo.No);	
						ThreadSleep(2000);	
						ExcelUtils.writeData(phase1DataSheetFilePath,date, "Task1", excelLabel.Variable_Name, "M7Task13", excelLabel.Due_Date);
						flag=true;
					} else {
						log(LogStatus.ERROR, "Not Able to Entered & save Value to Due Date "+date, YesNo.Yes);	
						sa.assertTrue(false, "Not Able to Entered Value to Due Date "+date);
					}
					
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+task+" on Task SideMenu", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+task+" on Task SideMenu");
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+task,YesNo.Yes);
			}
			
			switchToDefaultContent(driver);
			ThreadSleep(5000);
		
		String contactName=M7Contact12FName+" "+M7Contact12LName;
		if (flag) {
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					 ele=cp.getlastTouchPointValue(projectName, 10);
					if (ele!=null) {
						String expectedValue = M7Task13dueDate; 
						String actualValue = ele.getText().trim();
						if (cp.verifyDate(expectedValue, actualValue)) {
							log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
							sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
						}
					}else {
						log(LogStatus.ERROR, "last touch point value is not visible For : "+contactName, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not visible For : "+contactName );
					}
				} else {
					sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
			}	
		} else {
			sa.assertTrue(false,"Task is not been updated so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc065_UpdateTheStatusOfMATouchPointMeeting_1AndVerifyImpactOnLastTouchPoint(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
		boolean flag=false;
		
			String task=M7Task13Subject;
			TabName tabName=TabName.TaskTab;
			PageName pageName = PageName.TaskPage;
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+task,YesNo.No);
				ele=tp.getTaskNameLinkInSideMMenu(projectName, task, 30);
				if (click(driver, ele, task, action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on "+task+" on Task SideMenu", YesNo.No);	
					if (click(driver, tp.getEditButton(projectName, 30), task, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button For : "+task, YesNo.No);	
						if (tp.selectDropDownValueonTaskPopUp(projectName, pageName, PageLabel.Status.toString(), statusInStarted, action.BOOLEAN, 10)) {
							log(LogStatus.INFO, "Selected : "+statusInStarted+" For Label : "+PageLabel.Status.toString(), YesNo.No);	
								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"successfully Updated task : "+task,  YesNo.No);
									ThreadSleep(5000);
									flag=true;
									String[][] fieldsWithValues= {{PageLabel.Status.toString(),statusInStarted}};
									tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 30);	
								}else {
									log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+task, YesNo.Yes);
									sa.assertTrue(false,"save button is not clickable so task not Updated : "+task );
								}
						
						}else {
							log(LogStatus.ERROR, "Not Able to Select : "+statusInStarted+" For Label : "+PageLabel.Status.toString(), YesNo.Yes);	
							BaseLib.sa.assertTrue(false, "Not Able to Select : "+statusInStarted+" For Label : "+PageLabel.Status.toString());	
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Edit Button For : "+task, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Edit Button For : "+task);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+task+" on Task SideMenu", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+task+" on Task SideMenu");
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+task,YesNo.Yes);
			}
		
			switchToDefaultContent(driver);
			ThreadSleep(5000);
		
			String contactName=M7Contact12FName+" "+M7Contact12LName;
			if (flag) {
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						String expectedValue = M7Task15dueDate;
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							String actualValue = ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}
					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			} else {
				sa.assertTrue(false,"Task is not been updated so cannot check Last Touch Point for "+contactName);
				log(LogStatus.SKIP,"Task is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
			}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc066_CreateMultiTaggedMeetingforContactParkerHarrisAndVerifyLastTouchPoint(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact12FName+" "+M7Contact12LName;
		String secondaryContact=M7Contact14FName+" "+M7Contact14LName;
		M7Task16dueDate=previousOrForwardDate(0, "M/d/YYYY");;
		String task = M7Task16Subject;
		String[][] event1 = {
				{PageLabel.Name.toString(),secondaryContact},
				{PageLabel.Subject.toString(),task},
				{PageLabel.Due_Date.toString(),M7Task16dueDate},
				{PageLabel.Meeting_Type.toString(),M7Task16MeetingType},
				{PageLabel.Status.toString(),M7Task16Status}};

		
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					ExcelUtils.writeData(phase1DataSheetFilePath,M7Task16dueDate, "Task1", excelLabel.Variable_Name, "M7Task16", excelLabel.Due_Date);
					///////////////////////
					ele = lp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.New_Meeting, 10);
					if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Meeting.toString(), action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.New_Meeting.toString(),  YesNo.Yes);
						ThreadSleep(1000);
						
						ele = lp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), true, primaryContact, action.SCROLLANDBOOLEAN, 10);
						if (ele!=null) {
							log(LogStatus.INFO, "successfully verified presence of "+primaryContact+" in name field",YesNo.No);
						} else {
							sa.assertTrue(false,"not found "+ primaryContact+" For Label "+PageLabel.Name);
							log(LogStatus.SKIP,"not found "+ primaryContact+" For Label "+PageLabel.Name,YesNo.Yes);

						}
						gp.enterValueForTask(projectName, PageName.Object2Page, event1, 20);
						if (clickUsingJavaScript(driver, gp.getCustomTabSaveBtn(projectName, 2), "Save Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
							ThreadSleep(5000);
							flag=true;
							
						}else {
							sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
							log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
						}
						
					}
					else {
						log(LogStatus.ERROR, "not able to click on "+ActivityTimeLineItem.New_Meeting.toString(), YesNo.Yes);
						sa.assertTrue(false,"not able to click on "+ActivityTimeLineItem.New_Meeting.toString() );
					}
					//////////////////////
					//refresh(driver);
					ele = lp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Refresh, 10);
					click(driver, ele, ActivityTimeLineItem.Refresh.toString(), action.BOOLEAN);
					ThreadSleep(3000);
					String expectedValue = M7Task16dueDate;
					ele=cp.getlastTouchPointValue(projectName, 10);
					if (ele!=null) {
						String actualValue = ele.getText().trim();
						if (cp.verifyDate(expectedValue, actualValue)) {
							log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+primaryContact, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "Last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
							sa.assertTrue(false,"last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue );
						}
					}else {
						log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+primaryContact, YesNo.Yes);
						sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+primaryContact );
					}
				} else {
					sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
			}	
			
			
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+secondaryContact,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, secondaryContact, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+secondaryContact+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					String expectedValue = M7Task16dueDate;
					ele=cp.getlastTouchPointValue(projectName, 10);
					if (ele!=null) {
						String actualValue = ele.getText().trim();
						if (cp.verifyDate(expectedValue, actualValue)) {
							log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+secondaryContact, YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "Last touch point value is not matched For : "+secondaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
							sa.assertTrue(false,"last touch point value is not matched For : "+secondaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue );
						}
					}else {
						log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+secondaryContact, YesNo.Yes);
						sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+secondaryContact );
					}
				} else {
					sa.assertTrue(false,"Item Not Found : "+secondaryContact+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+secondaryContact+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+secondaryContact);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+secondaryContact,YesNo.Yes);
			}	
			
			
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc067_UpdateTheDueDateWithFutureDateAndVerifyLastTouchPointDate(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
		boolean flag=false;
		String task=M7Task16Subject;
		M7Task16dueDate=previousOrForwardDate(7, "M/d/YYYY");;
		String primaryContact=M7Contact12FName+" "+M7Contact12LName;
		String secondaryContact=M7Contact14FName+" "+M7Contact14LName;

		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					String date = M7Task16dueDate;
					if (tp.EditEnterDueDateAndSave(projectName, task, date)) {
						log(LogStatus.INFO, "Value Entered & saved to Due Date "+date, YesNo.No);	
						ThreadSleep(2000);	
						ExcelUtils.writeData(phase1DataSheetFilePath,date, "Task1", excelLabel.Variable_Name, "M7Task16", excelLabel.Due_Date);
						flag=true;
					} else {
						log(LogStatus.ERROR, "Not Able to Entered & save Value to Due Date "+date, YesNo.Yes);	
						sa.assertTrue(false, "Not Able to Entered Value to Due Date "+date);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	

		String actualValue=null;
		String expectedValue = null;
		String[] contactNames={primaryContact,secondaryContact};
		String contactName=null;
		if (flag) {
			for (int j = 0; j < contactNames.length; j++) {
				contactName=contactNames[j];
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						expectedValue = M7Task16dueDate;
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			}

		} else {
			sa.assertTrue(false,"Task is not been updated so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}

		/////////////////////////

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc068_UpdateTheDueDateWithPastDateAndVerifyLastTouchPointDate(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
		boolean flag=false;
		String task=M7Task16Subject;
		M7Task16dueDate=previousOrForwardDate(-7, "M/d/YYYY");;
		String primaryContact=M7Contact12FName+" "+M7Contact12LName;
		String secondaryContact=M7Contact14FName+" "+M7Contact14LName;

		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					String date = M7Task16dueDate;
					if (tp.EditEnterDueDateAndSave(projectName, task, date)) {
						log(LogStatus.INFO, "Value Entered & saved to Due Date "+date, YesNo.No);	
						ThreadSleep(2000);	
						ExcelUtils.writeData(phase1DataSheetFilePath,date, "Task1", excelLabel.Variable_Name, "M7Task16", excelLabel.Due_Date);
						flag=true;
					} else {
						log(LogStatus.ERROR, "Not Able to Entered & save Value to Due Date "+date, YesNo.Yes);	
						sa.assertTrue(false, "Not Able to Entered Value to Due Date "+date);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	

		String actualValue=null;
		String expectedValue = null;
		String[] contactNames={primaryContact,secondaryContact};
		String contactName=null;
		if (flag) {
			for (int j = 0; j < contactNames.length; j++) {
				contactName=contactNames[j];
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						expectedValue = M7Task16dueDate;
						ele=cp.getlastTouchPointValue(projectName, 10);
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+contactName+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+contactName, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+contactName );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			}

		} else {
			sa.assertTrue(false,"Task is not been updated so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Task is not been updated so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}

		/////////////////////////

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc069_CreateTaskAndMeetingCreatedOnTheSameContactWithDifferentDueDate(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		
		WebElement ele = null ;
		String primaryContact=M7Contact15FName+" "+M7Contact15LName;
		M7Task17dueDate=previousOrForwardDate(-3, "M/d/YYYY");;
		String task = M7Task17Subject;
		String actualValue=null;
		String expectedValue = null;
		String[][] event1 = {
				{PageLabel.Subject.toString(),task},
				{PageLabel.Due_Date.toString(),M7Task17dueDate},
				{PageLabel.Status.toString(),M7Task17Status},
				{PageLabel.Meeting_Type.toString(),M7Task17MeetingType}};


		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				ThreadSleep(2000);
				ExcelUtils.writeData(phase1DataSheetFilePath,M7Task17dueDate, "Task1", excelLabel.Variable_Name, "M7Task17", excelLabel.Due_Date);
				///////////////////////
				ele = lp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.New_Task, 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task.toString(), action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.New_Task.toString(),  YesNo.Yes);
					ThreadSleep(1000);
					ele = lp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), true, primaryContact, action.SCROLLANDBOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO, "successfully verified presence of "+primaryContact+" in name field",YesNo.No);
					} else {
						sa.assertTrue(false,"not found "+ primaryContact+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"not found "+ primaryContact+" For Label "+PageLabel.Name,YesNo.Yes);

					}
					gp.enterValueForTask(projectName, PageName.Object2Page, event1, 20);
					if (clickUsingJavaScript(driver, lp.getCustomTabSaveBtn(projectName, 2), "Save Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
						ThreadSleep(5000);
						refresh(driver);

					}else {
						sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
						log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
					}

				}
				else {
					log(LogStatus.ERROR, "not able to click on "+ActivityTimeLineItem.New_Task.toString(), YesNo.Yes);
					sa.assertTrue(false,"not able to click on "+ActivityTimeLineItem.New_Task.toString() );
				}
			
				ele = lp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Refresh, 10);
				click(driver, ele, ActivityTimeLineItem.Refresh.toString(), action.BOOLEAN);
				ThreadSleep(3000);
				ele=cp.getlastTouchPointValue(projectName, 10);
				expectedValue = M7Task17dueDate;
				if (ele!=null) {
					actualValue=ele.getText().trim();
					if (cp.verifyDate(expectedValue, actualValue)) {
						log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+primaryContact, YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "Last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue );
					}
				}else {
					log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+primaryContact, YesNo.Yes);
					sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+primaryContact );
				}
			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}
		
		
		M7Task18dueDate=previousOrForwardDate(0, "M/d/YYYY");;
		 task = M7Task18Subject;
		String[][] event11 = {
				{PageLabel.Subject.toString(),task},
				{PageLabel.Due_Date.toString(),M7Task18dueDate},
				{PageLabel.Status.toString(),M7Task18Status},
				{PageLabel.Meeting_Type.toString(),M7Task18MeetingType}};


		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				ThreadSleep(2000);
				ExcelUtils.writeData(phase1DataSheetFilePath,M7Task18dueDate, "Task1", excelLabel.Variable_Name, "M7Task18", excelLabel.Due_Date);
				///////////////////////
				ele = lp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.New_Meeting, 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Meeting.toString(), action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.New_Meeting.toString(),  YesNo.Yes);
					ThreadSleep(1000);
					ele = lp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), true, primaryContact, action.SCROLLANDBOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO, "successfully verified presence of "+primaryContact+" in name field",YesNo.No);
					} else {
						sa.assertTrue(false,"not found "+ primaryContact+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"not found "+ primaryContact+" For Label "+PageLabel.Name,YesNo.Yes);

					}
					gp.enterValueForTask(projectName, PageName.Object2Page, event11, 20);
					if (clickUsingJavaScript(driver, gp.getCustomTabSaveBtn(projectName, 2), "Save Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
						ThreadSleep(5000);
						refresh(driver);

					}else {
						sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
						log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
					}

				}
				else {
					log(LogStatus.ERROR, "not able to click on "+ActivityTimeLineItem.New_Meeting.toString(), YesNo.Yes);
					sa.assertTrue(false,"not able to click on "+ActivityTimeLineItem.New_Meeting.toString() );
				}
			
				ele = lp.getActivityTimeLineItem2(projectName, PageName.Object3Page, ActivityTimeLineItem.Refresh, 10);
				click(driver, ele, ActivityTimeLineItem.Refresh.toString(), action.BOOLEAN);
				ThreadSleep(3000);
				ele=cp.getlastTouchPointValue(projectName, 10);
				expectedValue = M7Task18dueDate;
				if (ele!=null) {
					actualValue=ele.getText().trim();
					if (cp.verifyDate(expectedValue, actualValue)) {
						log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+primaryContact, YesNo.No);
					}
					else {
						log(LogStatus.ERROR, "Last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
						sa.assertTrue(false,"last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue );
					}
				}else {
					log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+primaryContact, YesNo.Yes);
					sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+primaryContact );
				}
			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	
		
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M7Tc070_DeleteTheCreatedMeetingAndVerifyTheLastTouchPointOnTheContact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact15FName+" "+M7Contact15LName;
		String task = M7Task18Subject;
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
					if (click(driver, cp.getDeleteButton(projectName, 20), "Delete Button", action.BOOLEAN)) {
						log(LogStatus.INFO,"Able to Click on Delete button : "+task,YesNo.No);
						ThreadSleep(2000);
						if (click(driver, cp.getDeleteButtonPopUp(projectName, 20), "Delete Button", action.BOOLEAN)) {
							log(LogStatus.INFO,"Able to Click on Delete button on PopUp : "+task,YesNo.No);
							ThreadSleep(2000);
							flag=true;
						} else {
							sa.assertTrue(false,"Able to Click on Delete button on PopUp : "+task);
							log(LogStatus.SKIP,"Able to Click on Delete button on PopUp : "+task,YesNo.Yes);
						}
					} else {
						sa.assertTrue(false,"Not Able to Click on Delete button : "+task);
						log(LogStatus.SKIP,"Not Able to Click on Delete button : "+task,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Task : "+task);
					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
		}	

		String actualValue=null;
		String expectedValue = "";
		String contactName=primaryContact;
		if (flag) {
				contactName=primaryContact;
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						ele=cp.getlastTouchPointValue(projectName, 10);
						expectedValue = M7Task17dueDate;
						if (ele!=null) {
							actualValue=ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+primaryContact, YesNo.No);
							}
							else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
								sa.assertTrue(false,"last touch point value is not matched For : "+primaryContact+" Actual : "+actualValue+" /t Expected : "+expectedValue );
							}
						}else {
							log(LogStatus.ERROR, expectedValue+" last touch point value is not visible For : "+primaryContact, YesNo.Yes);
							sa.assertTrue(false,expectedValue+" last touch point value is not visible For : "+primaryContact );
						}

					} else {
						sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
						log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
					}
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
				}	
			

		} else {
			sa.assertTrue(false,"Meeting is not been deleted so cannot check Last Touch Point for "+contactName);
			log(LogStatus.SKIP,"Meeting is not been deleted so cannot check Last Touch Point for "+contactName,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
}