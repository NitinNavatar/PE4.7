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
			//String relatedValue="";
			//gp.enterValueOnRelatedTo(projectName, PageName.GlobalActtion_TaskPOpUp, TabName.InstituitonsTab, relatedValue);
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
						
						if (click(driver, gp.getCustomTabSaveBtn(projectName, 2), "Save Button", action.SCROLLANDBOOLEAN)) {
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
					if (click(driver, gp.getCustomTabSaveBtn(projectName, 2), "Save Button", action.SCROLLANDBOOLEAN)) {
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
					if (click(driver, tp.getEditButton(projectName, 30), task, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button For : "+task, YesNo.No);	
							if (sendKeys(driver, tp.getdueDateTextBoxInNewTask(projectName, 20), M7Task1dueDate, "Due Date", action.BOOLEAN)) {
								log(LogStatus.INFO, "Value Entered to Due Date "+M7Task1dueDate, YesNo.No);	
								ThreadSleep(2000);
								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
									ExcelUtils.writeData(phase1DataSheetFilePath,M7Task1dueDate, "Task1", excelLabel.Variable_Name, "M7Task1", excelLabel.Due_Date);
									log(LogStatus.INFO,"successfully Updated task : "+task,  YesNo.No);
									ThreadSleep(5000);
									flag=true;
									String[][] fieldsWithValues= {{PageLabel.Due_Date.toString(),M7Task1dueDate}};
									tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 30);	
								}else {
									log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+task, YesNo.Yes);
									sa.assertTrue(false,"save button is not clickable so task not Updated : "+task );
								}
							}else {
								log(LogStatus.ERROR, "Not Able to Entered Value to Due Date "+M7Task1dueDate, YesNo.Yes);	
								BaseLib.sa.assertTrue(false, "Not Able to Entered Value to Due Date "+M7Task1dueDate);	
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
	public void M7Tc014_DeleteNewlyUpdatedTaskAndVerifyTheImpactOnLastTouchPointInContactPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		boolean flag=false;
		WebElement ele = null ;
		String primaryContact=M7Contact1FName+" "+M7Contact1LName;
		String task = M7Task3Subject;
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
							ThreadSleep(5000);
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
		String[] contactNames={M7Contact2FName+" "+M7Contact2LName,M7Contact1FName+" "+M7Contact1LName};
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
							expectedValue="";
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
					if (click(driver, gp.getCustomTabSaveBtn(projectName, 2), "Save Button", action.SCROLLANDBOOLEAN)) {
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
					if (click(driver, tp.getEditButton(projectName, 30), task, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button For : "+task, YesNo.No);	
						if (sendKeys(driver, tp.getdueDateTextBoxInNewTask(projectName, 20), M7Task4dueDate, "Due Date", action.BOOLEAN)) {
							log(LogStatus.INFO, "Value Entered to Due Date "+M7Task4dueDate, YesNo.No);	
							ThreadSleep(2000);
							if (clickUsingJavaScript(driver, lp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"successfully Updated task : "+task,  YesNo.No);
								ThreadSleep(5000);
								ExcelUtils.writeData(phase1DataSheetFilePath,M7Task4dueDate, "Task1", excelLabel.Variable_Name, "M7Task4", excelLabel.Due_Date);
								flag=true;
								String[][] fieldsWithValues= {{PageLabel.Due_Date.toString(),M7Task4dueDate}};
								tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 30);	
							}else {
								log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+task, YesNo.Yes);
								sa.assertTrue(false,"save button is not clickable so task not Updated : "+task );
							}

						}else {
							log(LogStatus.ERROR, "Not Able to Entered Value to Due Date "+M7Task4dueDate, YesNo.Yes);	
							BaseLib.sa.assertTrue(false, "Not Able to Entered Value to Due Date "+M7Task4dueDate);	
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
					if (click(driver, tp.getEditButton(projectName, 30), task, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button For : "+task, YesNo.No);	
						if (sendKeys(driver, tp.getdueDateTextBoxInNewTask(projectName, 20), M7Task4dueDate, "Due Date", action.BOOLEAN)) {
							log(LogStatus.INFO, "Value Entered to Due Date "+M7Task4dueDate, YesNo.No);	
							ThreadSleep(2000);
							if (clickUsingJavaScript(driver, lp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"successfully Updated task : "+task,  YesNo.No);
								ThreadSleep(5000);
								ExcelUtils.writeData(phase1DataSheetFilePath,M7Task4dueDate, "Task1", excelLabel.Variable_Name, "M7Task4", excelLabel.Due_Date);
								flag=true;
								String[][] fieldsWithValues= {{PageLabel.Due_Date.toString(),M7Task4dueDate}};
								tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 30);	
							}else {
								log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+task, YesNo.Yes);
								sa.assertTrue(false,"save button is not clickable so task not Updated : "+task );
							}

						}else {
							log(LogStatus.ERROR, "Not Able to Entered Value to Due Date "+M7Task4dueDate, YesNo.Yes);	
							BaseLib.sa.assertTrue(false, "Not Able to Entered Value to Due Date "+M7Task4dueDate);	
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
	
	
	
	
}