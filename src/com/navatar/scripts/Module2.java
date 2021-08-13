package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.android.dx.cf.iface.Field;
import com.navatar.generic.BaseLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.SoftAssert;
import com.navatar.generic.EnumConstants.*;
import com.navatar.pageObjects.BasePageErrorMessage;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.DealPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.NavatarSetupPageBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.navatar.pageObjects.TaskPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;

public class Module2 extends BaseLib{
	public static double dealReceivedScore=1.0,NDASignedScore=1.0,managementMeetingScore=3.0,ioiScore=3.0,loiScore=5.0;
	public static double dueDiligenceScore=5.0,closedScore=5.0,declinedDeadScore=5.0,parkedScore=5.0;
	//postcondition:
	//ioi, nda signed revert
	//watch list rename to watchlist

	@Parameters({ "projectName"})
	@Test
	public void M2tc002_1_CreatePreconditionData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String value="";
		String type="";
		String status1=null;
		String[][] EntityOrAccounts = {
				{ Smoke_TWINS1Name, Smoke_TWINS1RecordType ,Smoke_TWINS1Status}
				, {Smoke_TWINS2Name,Smoke_TWINS2RecordType ,Smoke_TWINS2Status},
				{Smoke_TWINS3Name,Smoke_TWINS3RecordType ,Smoke_TWINS3Status},
				{Smoke_TWINS4Name,Smoke_TWINS4RecordType ,Smoke_TWINS4Status}
		};
		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
				value = accounts[0];
				type = accounts[1];
				status1=accounts[2];
				if (ip.createEntityOrAccount(projectName, value, type, new String[][] {{PageLabel.Status.toString(),status1}}, 20)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
			}
		}

		String fname="";
		String lname="";
		String mailID="";
		String ins="";
		String title=null;
		String[][] contactsInfo = { { Smoke_TWContact1FName, Smoke_TWContact1LName, Smoke_TWINS1Name,
			Smoke_TWContact1RecordType,null}
		, {Smoke_TWContact2FName,Smoke_TWContact2LName,Smoke_TWINS1Name,
			Smoke_TWContact2RecordType,null},
		{Smoke_TWContact3FName,Smoke_TWContact3LName,Smoke_TWINS2Name,
				Smoke_TWContact3RecordType,null},
		{Smoke_TWContact4FName,Smoke_TWContact4LName,Smoke_TWINS2Name,
					Smoke_TWContact4RecordType,null},
		{Smoke_TWContact5FName,Smoke_TWContact5LName,Smoke_TWINS3Name,
						Smoke_TWContact5RecordType,null}
		};
		int i=1;
		String recType;
		for (String[] contacts : contactsInfo) {
			if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);	
				fname = contacts[0];
				lname = contacts[1];
				ins=contacts[2];
				recType=contacts[3];
				title=contacts[4];
				mailID=	lp.generateRandomEmailId(gmailUserName);
				ExcelUtils.writeData(phase1DataSheetFilePath, mailID, "Contacts", excelLabel.Variable_Name, "TWCON"+i,excelLabel.Contact_EmailId);

				if (cp.createContact(projectName, fname, lname, ins, mailID,recType, null, null, CreationPage.ContactPage, title)) {
					log(LogStatus.INFO,"successfully Created Contact : "+fname+" "+lname,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Contact : "+fname+" "+lname);
					log(LogStatus.SKIP,"Not Able to Create Contact: "+fname+" "+lname,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
			}
			i++;
		}

		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2tc003_1_AddWatchlistField(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
	
		String parentID=null;
		String mode="Lightning";
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
			List<String> layoutName = new ArrayList<String>();
			layoutName.add("Task Layout");
			HashMap<String, String> sourceANDDestination = new HashMap<String, String>();
			sourceANDDestination.put(PageLabel.Watchlist.toString(), PageLabel.Meeting_Type.toString());
			List<String> abc = setup.DragNDrop("", mode, object.Task, ObjectFeatureName.pageLayouts, layoutName, sourceANDDestination);
			ThreadSleep(10000);
			if (!abc.isEmpty()) {
				log(LogStatus.FAIL, "field not added/already present 1", YesNo.Yes);
			}else{
				log(LogStatus.INFO, "field added/already present 1", YesNo.Yes);
			}
			driver.close();
			driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch, so cannot add field", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch, so cannot add field");
			}
		}
		else {
			log(LogStatus.FAIL, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}
		lp.CRMlogout();
		sa.assertAll();
	
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc004_AddWatchlistContactAndVerifyImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		String tabs=ip.getTabName(projectName, TabName.Object1Tab)+","+ip.getTabName(projectName, TabName.Object3Tab);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TWINS2Name, 20)) {
				scrollThroughOutWindow(driver);
				ThreadSleep(3000);
				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task , 10);
				scrollDownThroughWebelement(driver, ele, "new task");
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task.toString(), action.BOOLEAN)) {
				ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task , 10);
				scrollDownThroughWebelement(driver, ip.relatedAssociations(projectName).get(0), "related associatons");
				if (clickUsingJavaScript(driver, ip.getrelatedAssociationsdropdownButton(projectName, 10), "dropdown button for related associations")) {
					List<String> l=compareMultipleListContainsByTitle(driver, tabs, ip.listOfObjectsInRelatedAssctions(projectName));
					if (l.isEmpty()) {
						log(LogStatus.INFO, "successfully verified presence of all tabs "+tabs, YesNo.No);
					}
					else {
						for (String a:l) {
							log(LogStatus.ERROR,"not found "+a,  YesNo.Yes);
							sa.assertTrue(false, "not found "+a);
						}
					}
					l.clear();
					l=compareMultipleListContainsByTitle(driver,ip.getTabName(projectName, TabName.Object2Tab).toString(), ip.listOfObjectsInRelatedAssctions(projectName));
					if (l.isEmpty()) {
						log(LogStatus.ERROR, "tab "+TabName.Object2Tab.toString()+" is present but should not be", YesNo.Yes);
						sa.assertTrue(false, "tab "+TabName.Object2Tab.toString()+" is present but should not be");
					}
					else {
						for (String a:l) {
							log(LogStatus.INFO,"not found "+a+" as expected",  YesNo.Yes);

						}
					}
					//3
					boolean flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object1Tab, Smoke_TWContact3FName+" "+Smoke_TWContact3LName, action.SCROLLANDBOOLEAN, 10);		
					if (flag) {
						log(LogStatus.SKIP,"Selected "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.No);

					} else {
						sa.assertTrue(false,"Not Able to Select "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.Yes);

					}

					if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), TWTask1Subject, "Subject", action.SCROLLANDBOOLEAN)) {
								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
								}
								else {
									log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
									sa.assertTrue(false,"save button is not clickable so task not created" );
								}
					}
					else {
						log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
					}
					ThreadSleep(2000);
					refresh(driver);
					ThreadSleep(2000);
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object3Page,ActivityType.Next, TWTask1Subject, SubjectElement.SubjectLink, 10);
					if (click(driver, ele, "task name",action.SCROLLANDBOOLEAN)) {
						String[][] fieldsWithValues= {{PageLabel.Subject.toString(),TWTask1Subject},
								{PageLabel.Watchlist.toString(),Watchlist.True.toString()}};

						tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);

					}else {
						log(LogStatus.ERROR,"could not click on task on task page", YesNo.Yes);
						sa.assertTrue(false,"could not click on task on task page" );
					}
				}else {
					log(LogStatus.ERROR, "related association dropdown button is not clickable", YesNo.Yes);
					sa.assertTrue(false,"related association dropdown button is not clickable" );
				}
				}else {
					log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
					sa.assertTrue(false,"could not click on new task button" );
				}
			}else {
				log(LogStatus.ERROR, "could not find "+Smoke_TWINS2Name, YesNo.Yes);
				sa.assertTrue(false,"could not find "+Smoke_TWINS2Name );
			}
			
		}else {
			log(LogStatus.ERROR, "could not click on object 1 tab", YesNo.Yes);
			sa.assertTrue(false,"could not click on object 1 tab" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc005_AddUnderEvalContactAndVerifyImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		String tabs=ip.getTabName(projectName, TabName.Object1Tab)+","+ip.getTabName(projectName, TabName.Object3Tab);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TWINS1Name, 20)) {
				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task , 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task.toString(), action.BOOLEAN)) {
				ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task , 10);
				scrollDownThroughWebelement(driver, ip.relatedAssociations(projectName).get(0), "related associatons");
				if (clickUsingJavaScript(driver, ip.getrelatedAssociationsdropdownButton(projectName, 10), "dropdown button for related associations")) {
					List<String> l=compareMultipleListContainsByTitle(driver, tabs, ip.listOfObjectsInRelatedAssctions(projectName));
					if (l.isEmpty()) {
						log(LogStatus.INFO, "successfully verified presence of all tabs "+tabs, YesNo.No);
					}
					else {
						for (String a:l) {
							log(LogStatus.ERROR,"not found "+a,  YesNo.Yes);
							sa.assertTrue(false, "not found "+a);
						}
					}
					l.clear();
					l=compareMultipleListContainsByTitle(driver,ip.getTabName(projectName, TabName.Object2Tab).toString(), ip.listOfObjectsInRelatedAssctions(projectName));
					if (l.isEmpty()) {
						log(LogStatus.ERROR, "tab "+TabName.Object2Tab.toString()+" is present but should not be", YesNo.Yes);
						sa.assertTrue(false, "tab "+TabName.Object2Tab.toString()+" is present but should not be");
					}
					else {
						for (String a:l) {
							log(LogStatus.INFO,"not found "+a+" as expected",  YesNo.Yes);

						}
					}
					//3
					boolean flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object1Tab, Smoke_TWContact1FName+" "+Smoke_TWContact1LName, action.SCROLLANDBOOLEAN, 10);		
					if (flag) {
						log(LogStatus.SKIP,"Selected "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name,YesNo.No);

					} else {
						sa.assertTrue(false,"Not Able to Select "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name,YesNo.Yes);

					}

					if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), TWTask2Subject, "Subject", action.SCROLLANDBOOLEAN)) {
								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
								}
								else {
									log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
									sa.assertTrue(false,"save button is not clickable so task not created" );
								}
					}
					else {
						log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
					}
					ThreadSleep(2000);
					refresh(driver);
					ThreadSleep(2000);
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object3Page,ActivityType.Next, TWTask2Subject, SubjectElement.SubjectLink, 10);
					if (click(driver, ele, "task name",action.SCROLLANDBOOLEAN)) {
						String[][] fieldsWithValues= {{PageLabel.Subject.toString(),TWTask2Subject},
								{PageLabel.Watchlist.toString(),Watchlist.False.toString()}};

						tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);

					}else {
						log(LogStatus.ERROR,"could not click on task on task page", YesNo.Yes);
						sa.assertTrue(false,"could not click on task on task page" );
					}
				}else {
					log(LogStatus.ERROR, "related association dropdown button is not clickable", YesNo.Yes);
					sa.assertTrue(false,"related association dropdown button is not clickable" );
				}
				}else {
					log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
					sa.assertTrue(false,"could not click on new task button" );
				}
			}else {
				log(LogStatus.ERROR, "could not find "+Smoke_TWINS1Name, YesNo.Yes);
				sa.assertTrue(false,"could not find "+Smoke_TWINS1Name );
			}
			
		}else {
			log(LogStatus.ERROR, "could not click on object 1 tab", YesNo.Yes);
			sa.assertTrue(false,"could not click on object 1 tab" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2tc006_AddWatchlistContactAndVerifyImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		String tabs=ip.getTabName(projectName, TabName.Object1Tab)+","+ip.getTabName(projectName, TabName.Object3Tab);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TWINS2Name, 20)) {
				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Call , 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Call.toString(), action.BOOLEAN)) {
				ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Call , 10);
				scrollDownThroughWebelement(driver, ip.relatedAssociations(projectName).get(0), "related associatons");
					//3
					boolean flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object1Tab, Smoke_TWContact3FName+" "+Smoke_TWContact3LName, action.SCROLLANDBOOLEAN, 10);		
					if (flag) {
						log(LogStatus.SKIP,"Selected "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.No);

					} else {
						sa.assertTrue(false,"Not Able to Select "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.Yes);

					}

					tp.getdueDateTextBoxInNewTask(projectName, 20).clear();						
					if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), TWTask3Subject, "Subject", action.SCROLLANDBOOLEAN)) {
								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
								}
								else {
									log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
									sa.assertTrue(false,"save button is not clickable so task not created" );
								}
					}
					else {
						log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
					}
					ThreadSleep(2000);
					refresh(driver);
					ThreadSleep(2000);
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object3Page,ActivityType.Past, TWTask3Subject, SubjectElement.SubjectLink, 10);
					if (click(driver, ele, "task name",action.SCROLLANDBOOLEAN)) {
						String[][] fieldsWithValues= {{PageLabel.Subject.toString(),TWTask3Subject},
								{PageLabel.Watchlist.toString(),Watchlist.True.toString()}};

						tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);

					}else {
						log(LogStatus.ERROR,"could not click on task on task page", YesNo.Yes);
						sa.assertTrue(false,"could not click on task on task page" );
					}
				}else {
					log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
					sa.assertTrue(false,"could not click on new task button" );
				}
			}else {
				log(LogStatus.ERROR, "could not find "+Smoke_TWINS1Name, YesNo.Yes);
				sa.assertTrue(false,"could not find "+Smoke_TWINS1Name );
			}
			
		}else {
			log(LogStatus.ERROR, "could not click on object 1 tab", YesNo.Yes);
			sa.assertTrue(false,"could not click on object 1 tab" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2tc007_AddUnderEvalContactAndVerifyImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		String tabs=ip.getTabName(projectName, TabName.Object1Tab)+","+ip.getTabName(projectName, TabName.Object3Tab);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TWINS1Name, 20)) {
				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Call , 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Call.toString(), action.BOOLEAN)) {
				ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Call , 10);
				scrollDownThroughWebelement(driver, ip.relatedAssociations(projectName).get(0), "related associatons");
					//3
					boolean flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object1Tab, Smoke_TWContact1FName+" "+Smoke_TWContact1LName, action.SCROLLANDBOOLEAN, 10);		
					if (flag) {
						log(LogStatus.SKIP,"Selected "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name,YesNo.No);

					} else {
						sa.assertTrue(false,"Not Able to Select "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name,YesNo.Yes);

					}

					tp.getdueDateTextBoxInNewTask(projectName, 20).clear();						
					if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), TWTask4Subject, "Subject", action.SCROLLANDBOOLEAN)) {
								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
								}
								else {
									log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
									sa.assertTrue(false,"save button is not clickable so task not created" );
								}
					}
					else {
						log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
					}
					ThreadSleep(2000);
					refresh(driver);
					ThreadSleep(2000);
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object3Page,ActivityType.Past, TWTask4Subject, SubjectElement.SubjectLink, 10);
					if (click(driver, ele, "task name",action.SCROLLANDBOOLEAN)) {
						String[][] fieldsWithValues= {{PageLabel.Subject.toString(),TWTask4Subject},
								{PageLabel.Watchlist.toString(),Watchlist.False.toString()}};

						tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);

					}else {
						appLog.error("could not click on task on task page");
						sa.assertTrue(false,"could not click on task on task page" );
					}
				}else {
					log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
					sa.assertTrue(false,"could not click on new task button" );
				}
			}else {
				log(LogStatus.ERROR, "could not find "+Smoke_TWINS1Name, YesNo.Yes);
				sa.assertTrue(false,"could not find "+Smoke_TWINS1Name );
			}
			
		}else {
			log(LogStatus.ERROR, "could not click on object 1 tab", YesNo.Yes);
			sa.assertTrue(false,"could not click on object 1 tab" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc008_AddMultipleWatchlistContactAndVerifyImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		String tabs=ip.getTabName(projectName, TabName.Object1Tab)+","+ip.getTabName(projectName, TabName.Object3Tab);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TWINS2Name, 20)) {
				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task , 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task.toString(), action.BOOLEAN)) {
				ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task , 10);
				scrollDownThroughWebelement(driver, ip.relatedAssociations(projectName).get(0), "related associatons");
				if (clickUsingJavaScript(driver, ip.getrelatedAssociationsdropdownButton(projectName, 10), "dropdown button for related associations")) {
					List<String> l=compareMultipleListContainsByTitle(driver, tabs, ip.listOfObjectsInRelatedAssctions(projectName));
					if (l.isEmpty()) {
						log(LogStatus.INFO, "successfully verified presence of all tabs "+tabs, YesNo.No);
					}
					else {
						for (String a:l) {
							log(LogStatus.ERROR,"not found "+a,  YesNo.Yes);
							sa.assertTrue(false, "not found "+a);
						}
					}
					l.clear();
					l=compareMultipleListContainsByTitle(driver,ip.getTabName(projectName, TabName.Object2Tab).toString(), ip.listOfObjectsInRelatedAssctions(projectName));
					if (l.isEmpty()) {
						log(LogStatus.ERROR, "tab "+TabName.Object2Tab.toString()+" is present but should not be", YesNo.Yes);
						sa.assertTrue(false, "tab "+TabName.Object2Tab.toString()+" is present but should not be");
					}
					else {
						for (String a:l) {
							log(LogStatus.INFO,"not found "+a+" as expected",  YesNo.Yes);

						}
					}
					//3
					boolean flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object1Tab, Smoke_TWContact3FName+" "+Smoke_TWContact3LName, action.SCROLLANDBOOLEAN, 10);		
					if (flag) {
						log(LogStatus.SKIP,"Selected "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.No);

					} else {
						sa.assertTrue(false,"Not Able to Select "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.Yes);

					}
					flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object1Tab, Smoke_TWContact1FName+" "+Smoke_TWContact1LName, action.SCROLLANDBOOLEAN, 10);		
					if (flag) {
						log(LogStatus.SKIP,"Selected "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name,YesNo.No);

					} else {
						sa.assertTrue(false,"Not Able to Select "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name,YesNo.Yes);

					}
					
					if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), TWTask5Subject, "Subject", action.SCROLLANDBOOLEAN)) {
								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
								}
								else {
									log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
									sa.assertTrue(false,"save button is not clickable so task not created" );
								}
					}
					else {
						log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
					}
					ThreadSleep(2000);
					refresh(driver);
					ThreadSleep(2000);
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object3Page,ActivityType.Next, TWTask5Subject, SubjectElement.SubjectLink, 10);
					if (click(driver, ele, "task name",action.SCROLLANDBOOLEAN)) {
						String[][] fieldsWithValues= {{PageLabel.Subject.toString(),TWTask5Subject},
								{PageLabel.Watchlist.toString(),Watchlist.True.toString()}};

						tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);

					}else {
						appLog.error("could not click on task on task page");
						sa.assertTrue(false,"could not click on task on task page" );
					}
				}else {
					log(LogStatus.ERROR, "related association dropdown button is not clickable", YesNo.Yes);
					sa.assertTrue(false,"related association dropdown button is not clickable" );
				}
				}else {
					log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
					sa.assertTrue(false,"could not click on new task button" );
				}
			}else {
				log(LogStatus.ERROR, "could not find "+Smoke_TWINS2Name, YesNo.Yes);
				sa.assertTrue(false,"could not find "+Smoke_TWINS2Name );
			}
			
		}else {
			log(LogStatus.ERROR, "could not click on object 1 tab", YesNo.Yes);
			sa.assertTrue(false,"could not click on object 1 tab" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc009_AddMultipleWatchlistContactAndVerifyImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		String tabs=ip.getTabName(projectName, TabName.Object1Tab)+","+ip.getTabName(projectName, TabName.Object3Tab);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TWINS2Name, 20)) {
				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Call , 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Call.toString(), action.BOOLEAN)) {
				ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Call , 10);
				scrollDownThroughWebelement(driver, ip.relatedAssociations(projectName).get(0), "related associatons");
				if (clickUsingJavaScript(driver, ip.getrelatedAssociationsdropdownButton(projectName, 10), "dropdown button for related associations")) {
					List<String> l=compareMultipleListContainsByTitle(driver, tabs, ip.listOfObjectsInRelatedAssctions(projectName));
					if (l.isEmpty()) {
						log(LogStatus.INFO, "successfully verified presence of all tabs "+tabs, YesNo.No);
					}
					else {
						for (String a:l) {
							log(LogStatus.ERROR,"not found "+a,  YesNo.Yes);
							sa.assertTrue(false, "not found "+a);
						}
					}
					l.clear();
					l=compareMultipleListContainsByTitle(driver,ip.getTabName(projectName, TabName.Object2Tab).toString(), ip.listOfObjectsInRelatedAssctions(projectName));
					if (l.isEmpty()) {
						log(LogStatus.ERROR, "tab "+TabName.Object2Tab.toString()+" is present but should not be", YesNo.Yes);
						sa.assertTrue(false, "tab "+TabName.Object2Tab.toString()+" is present but should not be");
					}
					else {
						for (String a:l) {
							log(LogStatus.INFO,"not found "+a+" as expected",  YesNo.Yes);

						}
					}
					//3
					boolean flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object1Tab, Smoke_TWContact3FName+" "+Smoke_TWContact3LName, action.SCROLLANDBOOLEAN, 10);		
					if (flag) {
						log(LogStatus.SKIP,"Selected "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.No);

					} else {
						sa.assertTrue(false,"Not Able to Select "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.Yes);

					}
					flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object1Tab, Smoke_TWContact1FName+" "+Smoke_TWContact1LName, action.SCROLLANDBOOLEAN, 10);		
					if (flag) {
						log(LogStatus.SKIP,"Selected "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name,YesNo.No);

					} else {
						sa.assertTrue(false,"Not Able to Select "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name,YesNo.Yes);

					}
					
					tp.getdueDateTextBoxInNewTask(projectName, 20).clear();						
					if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), TWTask6Subject, "Subject", action.SCROLLANDBOOLEAN)) {
								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
								}
								else {
									log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
									sa.assertTrue(false,"save button is not clickable so task not created" );
								}
					}
					else {
						log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
					}
					ThreadSleep(2000);
					refresh(driver);
					ThreadSleep(2000);
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object3Page,ActivityType.Past, TWTask6Subject, SubjectElement.SubjectLink, 10);
					if (click(driver, ele, "task name",action.SCROLLANDBOOLEAN)) {
						String[][] fieldsWithValues= {{PageLabel.Subject.toString(),TWTask6Subject},
								{PageLabel.Watchlist.toString(),Watchlist.True.toString()}};

						tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);

					}else {
						log(LogStatus.ERROR,"could not click on task on task page", YesNo.Yes);
						sa.assertTrue(false,"could not click on task on task page" );
					}
				}else {
					log(LogStatus.ERROR, "related association dropdown button is not clickable", YesNo.Yes);
					sa.assertTrue(false,"related association dropdown button is not clickable" );
				}
				}else {
					log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
					sa.assertTrue(false,"could not click on new task button" );
				}
			}else {
				log(LogStatus.ERROR, "could not find "+Smoke_TWINS2Name, YesNo.Yes);
				sa.assertTrue(false,"could not find "+Smoke_TWINS2Name );
			}
			
		}else {
			log(LogStatus.ERROR, "could not click on object 1 tab", YesNo.Yes);
			sa.assertTrue(false,"could not click on object 1 tab" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc010_RemoveContactFromWatchlistContactAndVerifyImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.TaskTab)) {
			WebElement ele=tp.getTaskNameLinkInSideMMenu(projectName, TWTask5Subject, 15);
		if (click(driver, ele, TWTask5Subject, action.BOOLEAN)) {
			String[][] fieldsWithValues= {{PageLabel.Subject.toString(),TWTask5Subject},
					{PageLabel.Watchlist.toString(),Watchlist.True.toString()}};

			tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);
			ThreadSleep(3000);
			scrollDownThroughWebelement(driver, ip.getEditButton(environment,  mode,10), "edit");
			if (click(driver, ip.getEditButton(environment,  mode,10), "edit", action.SCROLLANDBOOLEAN)) {
				ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(),false, Smoke_TWContact3FName+" "+Smoke_TWContact3LName, action.SCROLLANDBOOLEAN, 15);
				if (click(driver, ele, "cross icon for contact 3", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on cross icon for "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName);
					if  (click(driver, ip.getCustomTabSaveBtn(projectName,10), "save", action.BOOLEAN)) {
						appLog.info("successfully clicked on save button after changing contact");
						
						ThreadSleep(3000);
						ele=ip.getCustomTabSaveBtn(projectName,10);
						if (ele!=null) {
							appLog.error("edit mode opened after clicking on save button");
							sa.assertTrue(false, "edit mode opened after clicking on save button");
							click(driver, ele, "save", action.BOOLEAN);
						}
						if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10)) {

							appLog.info("successfully verifid watchlist as true after chaning cntact");
						}
						else {
							appLog.error("could not verify watchlist as true");
							sa.assertTrue(false, "could not verify watchlist as true");
						}
					}else {
						appLog.error("save button is not clickable so cannot verify watchlist checkbox functionality");
						sa.assertTrue(false, "save button is not clickable so cannot verify watchlist checkbox functionality");
					}
				}else {
					appLog.error("cross button is not clickable so cannot verify watchlist checkbox functionality");
					sa.assertTrue(false, "cross button is not clickable so cannot verify watchlist checkbox functionality");
				}
			}else {
				appLog.error("edit button is not clickable so cannot verify watchlist checkbox functionality");
				sa.assertTrue(false, "edit button is not clickable so cannot verify watchlist checkbox functionality");
			}
		}else {
			appLog.error("task name is not clickable so cannot verify watchlist checkbox functionality");
			sa.assertTrue(false, "task name is not clickable so cannot verify watchlist checkbox functionality");
		}
		}else {
			appLog.error("task tab is not clickable");
			sa.assertTrue(false, "task tab is not clickable");
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2tc011_RemoveContactFromWatchlistContactAndVerifyImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.TaskTab)) {
			WebElement ele=tp.getTaskNameLinkInSideMMenu(projectName, TWTask6Subject, 15);
		if (click(driver, ele, TWTask6Subject, action.BOOLEAN)) {
			String[][] fieldsWithValues= {{PageLabel.Subject.toString(),TWTask6Subject},
					{PageLabel.Watchlist.toString(),Watchlist.True.toString()}};

			tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);
			ThreadSleep(3000);
			scrollDownThroughWebelement(driver, ip.getEditButton(environment,  mode,10), "edit");
			if (click(driver, ip.getEditButton(environment,  mode,10), "edit", action.SCROLLANDBOOLEAN)) {
				ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(),false, Smoke_TWContact3FName+" "+Smoke_TWContact3LName, action.SCROLLANDBOOLEAN, 15);
				if (click(driver, ele, "cross icon for contact 3", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on cross icon for "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName);
					if  (click(driver, ip.getCustomTabSaveBtn(projectName,10), "save", action.BOOLEAN)) {
						appLog.info("successfully clicked on save button after changing contact");
						
						ThreadSleep(3000);
						ele=ip.getCustomTabSaveBtn(projectName,10);
						if (ele!=null) {
							appLog.error("edit mode opened after clicking on save button");
							sa.assertTrue(false, "edit mode opened after clicking on save button");
							click(driver, ele, "save", action.BOOLEAN);
						}
						if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10)) {

							appLog.info("successfully verifid watchlist as true after chaning cntact");
						}
						else {
							appLog.error("could not verify watchlist as true");
							sa.assertTrue(false, "could not verify watchlist as true");
						}
					}else {
						appLog.error("save button is not clickable so cannot verify watchlist checkbox functionality");
						sa.assertTrue(false, "save button is not clickable so cannot verify watchlist checkbox functionality");
					}
				}else {
					appLog.error("cross button is not clickable so cannot verify watchlist checkbox functionality");
					sa.assertTrue(false, "cross button is not clickable so cannot verify watchlist checkbox functionality");
				}
			}else {
				appLog.error("edit button is not clickable so cannot verify watchlist checkbox functionality");
				sa.assertTrue(false, "edit button is not clickable so cannot verify watchlist checkbox functionality");
			}
		}else {
			appLog.error("task name is not clickable so cannot verify watchlist checkbox functionality");
			sa.assertTrue(false, "task name is not clickable so cannot verify watchlist checkbox functionality");
		}
		}else {
			appLog.error("task tab is not clickable");
			sa.assertTrue(false, "task tab is not clickable");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	

	@Parameters({ "projectName"})
	@Test
	public void M2tc012_AddContactFromWatchlistContactAndVerifyImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.TaskTab)) {
			WebElement ele=tp.getTaskNameLinkInSideMMenu(projectName, TWTask5Subject, 15);
		if (click(driver, ele, TWTask5Subject, action.BOOLEAN)) {
			String[][] fieldsWithValues= {{PageLabel.Subject.toString(),TWTask5Subject},
					{PageLabel.Watchlist.toString(),Watchlist.True.toString()}};

			tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);
			ThreadSleep(3000);
			scrollDownThroughWebelement(driver, ip.getEditButton(environment,  mode,10), "edit");
			if (click(driver, ip.getEditButton(environment,  mode,10), "edit", action.SCROLLANDBOOLEAN)) {
				boolean flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object1Tab, Smoke_TWContact3FName+" "+Smoke_TWContact3LName, action.SCROLLANDBOOLEAN, 10);		
				if (flag) {
					log(LogStatus.SKIP,"Selected "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.No);

				} else {
					sa.assertTrue(false,"Not Able to Select "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name);
					log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.Yes);

				}
				if  (click(driver, ip.getCustomTabSaveBtn(projectName,10), "save", action.BOOLEAN)) {
					appLog.info("successfully clicked on save button after changing contact");

					ThreadSleep(3000);
					ele=ip.getCustomTabSaveBtn(projectName,10);
					if (ele!=null) {
						appLog.error("edit mode opened after clicking on save button");
						sa.assertTrue(false, "edit mode opened after clicking on save button");
						click(driver, ele, "save", action.BOOLEAN);
					}
					if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10)) {

						appLog.info("successfully verifid watchlist as true after chaning cntact");
					}
					else {
						appLog.error("could not verify watchlist as true");
						sa.assertTrue(false, "could not verify watchlist as true");
					}
				}else {
					appLog.error("save button is not clickable so cannot verify watchlist checkbox functionality");
					sa.assertTrue(false, "save button is not clickable so cannot verify watchlist checkbox functionality");
				}

			}else {
				appLog.error("edit button is not clickable so cannot verify watchlist checkbox functionality");
				sa.assertTrue(false, "edit button is not clickable so cannot verify watchlist checkbox functionality");
			}
		}else {
			appLog.error("task name is not clickable so cannot verify watchlist checkbox functionality");
			sa.assertTrue(false, "task name is not clickable so cannot verify watchlist checkbox functionality");
		}
		}else {
			appLog.error("task tab is not clickable");
			sa.assertTrue(false, "task tab is not clickable");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc013_AddContactFromWatchlistContactAndVerifyImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.TaskTab)) {
			WebElement ele=tp.getTaskNameLinkInSideMMenu(projectName, TWTask6Subject, 15);
		if (click(driver, ele, TWTask6Subject, action.BOOLEAN)) {
			String[][] fieldsWithValues= {{PageLabel.Subject.toString(),TWTask6Subject},
					{PageLabel.Watchlist.toString(),Watchlist.True.toString()}};

			tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);
			ThreadSleep(3000);
			scrollDownThroughWebelement(driver, ip.getEditButton(environment,  mode,10), "edit");
			if (click(driver, ip.getEditButton(environment,  mode,10), "edit", action.SCROLLANDBOOLEAN)) {
				boolean flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object1Tab, Smoke_TWContact3FName+" "+Smoke_TWContact3LName, action.SCROLLANDBOOLEAN, 10);		
				if (flag) {
					log(LogStatus.SKIP,"Selected "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.No);

				} else {
					sa.assertTrue(false,"Not Able to Select "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name);
					log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.Yes);

				}
				if  (click(driver, ip.getCustomTabSaveBtn(projectName,10), "save", action.BOOLEAN)) {
					appLog.info("successfully clicked on save button after changing contact");

					ThreadSleep(3000);
					ele=ip.getCustomTabSaveBtn(projectName,10);
					if (ele!=null) {
						appLog.error("edit mode opened after clicking on save button");
						sa.assertTrue(false, "edit mode opened after clicking on save button");
						click(driver, ele, "save", action.BOOLEAN);
					}
					if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10)) {

						appLog.info("successfully verifid watchlist as true after chaning cntact");
					}
					else {
						appLog.error("could not verify watchlist as true");
						sa.assertTrue(false, "could not verify watchlist as true");
					}
				}else {
					appLog.error("save button is not clickable so cannot verify watchlist checkbox functionality");
					sa.assertTrue(false, "save button is not clickable so cannot verify watchlist checkbox functionality");
				}

			}else {
				appLog.error("edit button is not clickable so cannot verify watchlist checkbox functionality");
				sa.assertTrue(false, "edit button is not clickable so cannot verify watchlist checkbox functionality");
			}
		}else {
			appLog.error("task name is not clickable so cannot verify watchlist checkbox functionality");
			sa.assertTrue(false, "task name is not clickable so cannot verify watchlist checkbox functionality");
		}
		}else {
			appLog.error("task tab is not clickable");
			sa.assertTrue(false, "task tab is not clickable");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc014_SetStatusAsWatchlistAndVerifyImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TWINS1Name, 20)) {
				if (ip.clickOnShowMoreActionDownArrow(projectName, PageName.Object1Page, ShowMoreActionDropDownList.Edit, 10)) {
				if (click(driver, fp.getDealStatus(projectName, 10), "Status : "+PageLabel.Watchlist.toString(), action.SCROLLANDBOOLEAN)) {
					ThreadSleep(2000);
					appLog.error("Clicked on Deal Status");
					
					String xpath="//span[@title='"+PageLabel.Watchlist.toString()+"']";
					WebElement dealStatusEle = FindElement(driver,xpath, PageLabel.Watchlist.toString(),action.SCROLLANDBOOLEAN, 10);
					ThreadSleep(2000);
					if (click(driver, dealStatusEle, PageLabel.Watchlist.toString(), action.SCROLLANDBOOLEAN)) {
						appLog.info("Selected Status : "+PageLabel.Watchlist.toString());
						ThreadSleep(2000);
					} else {
						log(LogStatus.ERROR,"Not able to Select on Status : "+PageLabel.Watchlist.toString(),YesNo.No);
						sa.assertTrue(false, "Not able to Select on Status : "+PageLabel.Watchlist.toString());
					}
					if (click(driver, ip.getNavigationTabSaveBtn(projectName,10), "save button", action.SCROLLANDBOOLEAN)) {
						appLog.info("clicked on save button");
					}else {
						appLog.error("save button is not clickable so cannot change cmpany to watchlist");
						sa.assertTrue(false, "save button is not clickable so cannot change cmpany to watchlist");
					}
				} else {
					log(LogStatus.ERROR,"Not able to Click on Status : "+PageLabel.Watchlist.toString(),YesNo.Yes);
					sa.assertTrue(false,"Not able to Click on Status : "+PageLabel.Watchlist.toString());
				}
				}else {
					log(LogStatus.ERROR,"edit button is not clickable so cannot change cmpany to watchlist", YesNo.Yes);
					sa.assertTrue(false, "edit button is not clickable so cannot change cmpany to watchlist");
				}
			}else {
				log(LogStatus.ERROR, "could not find "+Smoke_TWINS1Name, YesNo.Yes);
				sa.assertTrue(false,"could not find "+Smoke_TWINS1Name );
			}
		}else {
			log(LogStatus.ERROR,"object 1 tab is not clickable", YesNo.Yes);
			sa.assertTrue(false, "object 1 tab is not clickable");
		}
		
		if (ip.clickOnTab(projectName, TabName.TaskTab)) {
			WebElement ele=tp.getTaskNameLinkInSideMMenu(projectName, TWTask2Subject, 15);
		if (click(driver, ele, TWTask2Subject, action.BOOLEAN)) {
			String[][] fieldsWithValues= {{PageLabel.Subject.toString(),TWTask2Subject},
					{PageLabel.Watchlist.toString(),Watchlist.False.toString()}};

			tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);
			ThreadSleep(3000);
		}else {
			log(LogStatus.ERROR,TWTask2Subject+" task is not clickable", YesNo.Yes);
			sa.assertTrue(false, TWTask2Subject+" task is not clickable");
		}
		}else {
			log(LogStatus.ERROR,"task tab is not clickable", YesNo.Yes);
			sa.assertTrue(false, "task tab is not clickable");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	
	@Parameters({ "projectName"})
	@Test
	public void M2tc015_SaveTaskAgainAndVerifyImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.TaskTab)) {
			WebElement ele=tp.getTaskNameLinkInSideMMenu(projectName, TWTask2Subject, 15);
		if (click(driver, ele, TWTask2Subject, action.BOOLEAN)) {
			String[][] fieldsWithValues= {{PageLabel.Subject.toString(),TWTask2Subject},
					{PageLabel.Watchlist.toString(),Watchlist.False.toString()}};

			if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10)) {

				appLog.info("successfully verifid watchlist as checked before any action");
			}
			else {
				appLog.error("could not verify watchlist as checked");
				sa.assertTrue(false, "could not verify watchlist as checked");
			}
			ThreadSleep(3000);
			scrollDownThroughWebelement(driver, ip.getEditButton(environment,  mode,10), "edit");
			if (click(driver, ip.getEditButton(environment,  mode,10), "edit", action.SCROLLANDBOOLEAN)) {
				if (sendKeys(driver, tp.getdueDateTextBoxInNewTask(projectName, 20), todaysDate, PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Entered value to Due Date Text Box", YesNo.Yes);
					ThreadSleep(1000);
				}else {
					log(LogStatus.ERROR, "duedate textbox is not visible so task could not be created", YesNo.Yes);
					sa.assertTrue(false,"duedate textbox is not visible so task could not be created" );
				}
				if  (click(driver, ip.getCustomTabSaveBtn(projectName,10), "save", action.BOOLEAN)) {
					appLog.info("successfully clicked on save button after changing contact");

					ThreadSleep(3000);
					ele=ip.getCustomTabSaveBtn(projectName,10);
					if (ele!=null) {
						appLog.error("edit mode opened after clicking on save button");
						sa.assertTrue(false, "edit mode opened after clicking on save button");
						click(driver, ele, "save", action.BOOLEAN);
					}
					String[][] fieldsWithValues1= {{PageLabel.Subject.toString(),TWTask2Subject},
							{PageLabel.Watchlist.toString(),Watchlist.True.toString()}};

					if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues1, action.BOOLEAN, 10)) {

						appLog.info("successfully verifid watchlist as unchecked after changing date");
					}
					else {
						appLog.error("could not verify watchlist as unchecked");
						sa.assertTrue(false, "could not verify watchlist as unchecked");
					}
				}else {
					appLog.error("save button is not clickable so cannot verify watchlist checkbox functionality");
					sa.assertTrue(false, "save button is not clickable so cannot verify watchlist checkbox functionality");
				}

			}else {
				appLog.error("edit button is not clickable so cannot verify watchlist checkbox functionality");
				sa.assertTrue(false, "edit button is not clickable so cannot verify watchlist checkbox functionality");
			}
		}else {
			appLog.error("task name is not clickable so cannot verify watchlist checkbox functionality");
			sa.assertTrue(false, "task name is not clickable so cannot verify watchlist checkbox functionality");
		}
		}else {
			appLog.error("task tab is not clickable");
			sa.assertTrue(false, "task tab is not clickable");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	

	@Parameters({ "projectName"})
	@Test
	public void M2tc016_CreateNewTaskWithWatchlistAndVerifyImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.TaskTab)) {

			if (cp.clickOnShowMoreActionDownArrow(projectName, PageName.TaskPage, ShowMoreActionDropDownList.New_Task, 15)) {
				log(LogStatus.INFO,"Clicked on New Task Button for show more action",YesNo.No);
				ThreadSleep(1000);
				if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), TWTask8Subject, "Subject", action.SCROLLANDBOOLEAN)) {
					boolean flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TaskPage, PageLabel.Related_To.toString(), TabName.Object1Tab, Smoke_TWINS2Name, action.SCROLLANDBOOLEAN, 10);		
					if (flag) {
						log(LogStatus.SKIP,"Selected "+Smoke_TWINS2Name+" For Label "+PageLabel.Related_To,YesNo.No);

					} else {
						sa.assertTrue(false,"Not Able to Select "+Smoke_TWINS2Name+" For Label "+PageLabel.Related_To);
						log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWINS2Name+" For Label "+PageLabel.Related_To,YesNo.Yes);
					}

					ThreadSleep(3000);
					if  (click(driver, ip.getCustomTabSaveBtn(projectName,10), "save", action.BOOLEAN)) {
						appLog.info("successfully clicked on save button after changing contact");

						ThreadSleep(3000);
						WebElement ele=ip.getCustomTabSaveBtn(projectName,10);
						if (ele!=null) {
							appLog.error("edit mode opened after clicking on save button");
							sa.assertTrue(false, "edit mode opened after clicking on save button");
							click(driver, ele, "save", action.BOOLEAN);
						}
						String[][] fieldsWithValues1= {{PageLabel.Subject.toString(),TWTask8Subject},
								{PageLabel.Watchlist.toString(),Watchlist.False.toString()}};

						if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues1, action.BOOLEAN, 10)) {

							appLog.info("successfully verifid watchlist as unchecked after changing date");
						}
						else {
							appLog.error("could not verify watchlist as unchecked");
							sa.assertTrue(false, "could not verify watchlist as unchecked");
						}
					}else {
						appLog.error("save button is not clickable so cannot verify watchlist checkbox functionality");
						sa.assertTrue(false, "save button is not clickable so cannot verify watchlist checkbox functionality");
					}

				}else {
					appLog.error("edit button is not clickable so cannot verify watchlist checkbox functionality");
					sa.assertTrue(false, "edit button is not clickable so cannot verify watchlist checkbox functionality");
				}
			}else {
				appLog.error("task name is not clickable so cannot verify watchlist checkbox functionality");
				sa.assertTrue(false, "task name is not clickable so cannot verify watchlist checkbox functionality");
			}
		}else {
			appLog.error("task tab is not clickable");
			sa.assertTrue(false, "task tab is not clickable");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc017_AddWatchlistContactInStandardTask(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.TaskTab)) {
			WebElement ele=tp.getTaskNameLinkInSideMMenu(projectName, TWTask8Subject, 15);
			if (click(driver, ele, TWTask8Subject, action.BOOLEAN)) {
				String[][] fieldsWithValues= {{PageLabel.Subject.toString(),TWTask8Subject},
						{PageLabel.Watchlist.toString(),Watchlist.False.toString()}};

				if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10)) {

					appLog.info("successfully verifid watchlist as unchecked before any action");
				}
				else {
					appLog.error("could not verify watchlist as unchecked");
					sa.assertTrue(false, "could not verify watchlist as unchecked");
				}
				ThreadSleep(3000);
				scrollDownThroughWebelement(driver, ip.getEditButton(environment,  mode,10), "edit");
				if (click(driver, ip.getEditButton(environment,  mode,10), "edit", action.SCROLLANDBOOLEAN)) {
					if (sendKeys(driver, tp.getdueDateTextBoxInNewTask(projectName, 20), todaysDate, PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Entered value to Due Date Text Box", YesNo.Yes);
						ThreadSleep(1000);
					}else {
						log(LogStatus.ERROR, "duedate textbox is not visible so task could not be created", YesNo.Yes);
						sa.assertTrue(false,"duedate textbox is not visible so task could not be created" );
					}

					boolean flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TaskPage, PageLabel.Name.toString(), TabName.Object1Tab, Smoke_TWContact4FName+" "+Smoke_TWContact4LName, action.SCROLLANDBOOLEAN, 10);		
					if (flag) {
						log(LogStatus.SKIP,"Selected "+Smoke_TWContact4FName+" "+Smoke_TWContact4LName+" For Label "+PageLabel.Name,YesNo.No);

					} else {
						sa.assertTrue(false,"Not Able to Select "+Smoke_TWContact4FName+" "+Smoke_TWContact4LName+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWContact4FName+" "+Smoke_TWContact4LName+" For Label "+PageLabel.Name,YesNo.Yes);

					}
					if  (click(driver, ip.getCustomTabSaveBtn(projectName,10), "save", action.BOOLEAN)) {
						appLog.info("successfully clicked on save button after changing contact");
						ThreadSleep(3000);
						ele=ip.getCustomTabSaveBtn(projectName,10);
						if (ele!=null) {
							log(LogStatus.ERROR,"edit mode opened after clicking on save button",YesNo.Yes);
							sa.assertTrue(false, "edit mode opened after clicking on save button");
							click(driver, ele, "save", action.BOOLEAN);
						}

						String[][] fieldsWithValues1= {{PageLabel.Subject.toString(),TWTask8Subject},
								{PageLabel.Watchlist.toString(),Watchlist.True.toString()}};

						if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues1, action.BOOLEAN, 10)) {

							log(LogStatus.INFO,"successfully verifid watchlist as checked after editing action",YesNo.No);
						}
						else {
							log(LogStatus.ERROR,"could not verify watchlist as checked",YesNo.Yes);
							sa.assertTrue(false, "could not verify watchlist as checked");
						}
					}else {
						log(LogStatus.ERROR,"save button is not clickable",YesNo.Yes);
						sa.assertTrue(false, "save button is not clickable");
					}
				}else {
					log(LogStatus.ERROR,"edit button is not clickable",YesNo.Yes);
					sa.assertTrue(false, "edit button is not clickable");
				}
			}else {
				log(LogStatus.ERROR,"task link is not clickable, so cannot verify watchlist checkbox functionality",YesNo.Yes);
				sa.assertTrue(false, "task link is not clickable, so cannot verify watchlist checkbox functionality");
			}
		}else {
			log(LogStatus.ERROR,"task tab is not clickable",YesNo.Yes);
			sa.assertTrue(false, "task tab is not clickable");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	

	@Parameters({ "projectName"})
	@Test
	public void M2tc018_EnableContactTransfer(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavatarSetupPageBusinessLayer np= new NavatarSetupPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		if (ip.clickOnTab(projectName, TabName.NavatarSetup)) {
				if (np.clickOnNavatarSetupSideMenusTab(projectName, NavatarSetupSideMenuTab.ContactTransfer)) {
					log(LogStatus.INFO,"Clicked on Contact Transfer Tab", YesNo.No);
					if (clickUsingJavaScript(driver, np.getEditButtonforNavatarSetUpSideMenuTab(projectName,NavatarSetupSideMenuTab.ContactTransfer, 10), "Edit Button", action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button", YesNo.No);
						ThreadSleep(2000);
						if (!isSelected(driver, np.getEnableCheckBoxforNavatarSetUpSideMenuTab(projectName,NavatarSetupSideMenuTab.ContactTransfer, EditViewMode.Edit, ClickOrCheckEnableDisableCheckBox.Click, 10), "Enabled CheckBox")) {

							log(LogStatus.INFO, "Enable Contact Transfer is Unchecked", YesNo.No);

							if (clickUsingJavaScript(driver,np.getEnableCheckBoxforClickNavatarSetUpSideMenuTab(projectName,NavatarSetupSideMenuTab.ContactTransfer, EditViewMode.Edit, 10),"Contact Trasfer CheckBox", action.BOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Enable Contact Transfer Box Checkbox", YesNo.No);
								ThreadSleep(2000);
								String keepActivitiesDefaultValue = np.keepActivitiesValue(projectName, KeepActivityEnum.OldInstitutionOnly);
								String selectIncludeActivitiesValue = np.includeActivitiesValue(projectName, InculdeActivityEnum.ContactOnly);
								if (selectVisibleTextFromDropDown(driver,np.getKeepActivitiesAtSelectList(projectName, EditViewMode.Edit, 10),keepActivitiesDefaultValue, keepActivitiesDefaultValue)) {
									log(LogStatus.INFO, "Selected Keep Activities related to : " + keepActivitiesDefaultValue,YesNo.No);
									ThreadSleep(1000);
									
									if (selectVisibleTextFromDropDown(driver,np.getIncludeActivitiesSelectList(projectName, EditViewMode.Edit, 10),selectIncludeActivitiesValue, selectIncludeActivitiesValue)) {
										log(LogStatus.INFO, "Selected Include Activities related to : " + keepActivitiesDefaultValue,YesNo.No);
										ThreadSleep(1000);
										
										if (click(driver, np.getSaveButtonforNavatarSetUpSideMenuTab(projectName, NavatarSetupSideMenuTab.ContactTransfer, 10, TopOrBottom.TOP), "Save Button", action.BOOLEAN)) {
											ThreadSleep(5000);
											log(LogStatus.INFO, "Clicked on Save Button", YesNo.No);
											ThreadSleep(10000);
												SoftAssert tsa = np.verifyingContactTransferTab(projectName, EditViewMode.View,CheckBox.Checked, keepActivitiesDefaultValue, selectIncludeActivitiesValue);
												sa.combineAssertions(tsa);
											
										} else {
											sa.assertTrue(false, "Not Able to Click on Save Button");
											log(LogStatus.SKIP, "Not Able to Click on Save Button", YesNo.Yes);
										}
										
									} else {
										sa.assertTrue(false,"Not Able to Select Include Activities related to : " + selectIncludeActivitiesValue);
										log(LogStatus.SKIP,"Not Able to Select Include Activities related to : " + selectIncludeActivitiesValue,YesNo.Yes);
									}
								} else {
									sa.assertTrue(false,"Not Able to Select Keep Activities related to : " + keepActivitiesDefaultValue);
									log(LogStatus.SKIP,"Not Able to Select Keep Activities related to : " + keepActivitiesDefaultValue,YesNo.Yes);
								}
							} else {
								sa.assertTrue(false, "Not Able to Click on Enable Contact Transfer Checkbox");
								log(LogStatus.SKIP, "Not Able to Click on Enable Contact Transfer Checkbox", YesNo.Yes);
							}
						}else {
							log(LogStatus.SKIP, "Contact Transfer Checkbox is already checked", YesNo.Yes);
						}
					}else {
						sa.assertTrue(false, "edit button is not clickable");
						log(LogStatus.SKIP, "edit button is not clickable", YesNo.Yes);
					}
				}else {
					sa.assertTrue(false, "contact transfer side menu tab is not clickable");
					log(LogStatus.SKIP, "contact transfer side menu tab is not clickable", YesNo.Yes);
				}
			}else {
				sa.assertTrue(false, "navatar setup tab is not clickable");
				log(LogStatus.SKIP, "navatar setup tab is not clickable", YesNo.Yes);
			}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
		}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc019_TransferContact_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object2Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, Smoke_TWContact5FName+" "+Smoke_TWContact5LName, 20)) {
				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task , 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task.toString(), action.BOOLEAN)) {
				if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), TWTaskCR1Subject, "Subject", action.SCROLLANDBOOLEAN)) {
					if (sendKeys(driver, tp.getdueDateTextBoxInNewTask(projectName, 20), "", PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
						
					if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
					}
					else {
						log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
						sa.assertTrue(false,"save button is not clickable so task not created" );
					}
					}
					else {
						log(LogStatus.ERROR, "due date textbox is not visible", YesNo.Yes);
						sa.assertTrue(false,"due date textbox is not visible" );
					}
				}else {
					log(LogStatus.ERROR, "subject textbox is not clickable", YesNo.Yes);
					sa.assertTrue(false,"subject textbox is not clickable" );
				}
				}else {
					log(LogStatus.ERROR, "new task button not clickable", YesNo.Yes);
					sa.assertTrue(false,"new task button not clickable" );
				}
				refresh(driver);
				
				if (cp.clickOnShowMoreActionDownArrow(projectName, PageName.Object2Page, ShowMoreActionDropDownList.Contact_Transfer, 10)) {
					log(LogStatus.INFO, "Clicked on Contact Transfer", YesNo.No);	

					if (cp.enteringValueforLegalNameOnContactTransferPage(projectName, Smoke_TWINS4Name, 10)) {
						log(LogStatus.PASS, "Able to Transfer Contact", YesNo.No);
						ThreadSleep(2000);
						refresh(driver);

						if (cp.fieldValueVerification(projectName, PageName.Object2Page, PageLabel.Account_Name, Smoke_TWINS4Name, 5)) {
							log(LogStatus.PASS, "Label Verified after contact Transfer", YesNo.No);	
							ThreadSleep(2000);
						} else {
							sa.assertTrue(false, "Label Not Verified after contact Transfer");
							log(LogStatus.FAIL, "Label Not Verified after contact Transfer", YesNo.Yes);
						}
					}else {
						sa.assertTrue(false, "could not enter new inst vale on contact transfer");
						log(LogStatus.FAIL, "could not enter new inst vale on contact transfer", YesNo.Yes);
					}
				}else {
					sa.assertTrue(false, "new task button is not clickable");
					log(LogStatus.FAIL, "new task button is not clickable", YesNo.Yes);
				}
			}else {
				sa.assertTrue(false, Smoke_TWContact5FName+" "+Smoke_TWContact5LName+" contact is not found on contact tab");
				log(LogStatus.FAIL, Smoke_TWContact5FName+" "+Smoke_TWContact5LName +" contact is not found on contact tab", YesNo.Yes);
			}
		}else {
			sa.assertTrue(false, "contact tab is not clickable");
			log(LogStatus.FAIL, "contact tab is not clickable", YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc019_TransferContact_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TWINS4Name, 20)) {
				WebElement ele=null;
				String msg=BasePageErrorMessage.UpcomingTaskMsg(null, Smoke_TWContact5FName+" "+Smoke_TWContact5LName, 0);
				//msg+= " about "+Smoke_TWINS3Name;
				lp.verifyActivityAtNextStep2(projectName, PageName.Object1Page, null,TWTaskCR1Subject, msg, DueDate.No_due_date.toString(),false, "",false, "", 10);
				ele = tp.getElementForActivityTimeLineTask(projectName, PageName.Object1Page,ActivityType.Next, TWTaskCR1Subject, SubjectElement.SubjectLink, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "successfully verified presence of task",YesNo.No);
					if (click(driver, ele,TWTaskCR1Subject , action.BOOLEAN)){
						String[][] fieldsWithValues= {{PageLabel.Subject.toString(),TWTaskCR1Subject},
								{PageLabel.Watchlist.toString(),Watchlist.True.toString()}};

						if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10)) {

							log(LogStatus.INFO,"successfully verifid watchlist as checked after editing action",YesNo.No);
						}
						else {
							log(LogStatus.ERROR,"could not verify watchlist as checked",YesNo.Yes);
							sa.assertTrue(false, "could not verify watchlist as checked");
						}
					}else {
						log(LogStatus.ERROR,"task link is not clickable",YesNo.Yes);
						sa.assertTrue(false, "task link is not clickable");
					}
				}else {
					log(LogStatus.ERROR,"presence of task is not verified, so cannot verify watchlist checkbox",YesNo.Yes);
					sa.assertTrue(false, "presence of task is not verified, so cannot verify watchlist checkbox");
				}
				
			}else {
				log(LogStatus.ERROR,Smoke_TWINS4Name+" ins is not found on entity tab",YesNo.Yes);
				sa.assertTrue(false, Smoke_TWINS4Name+" ins is not found on entity tab");
			}
		}else {
			log(LogStatus.ERROR,"entity tab is not clickable",YesNo.Yes);
			sa.assertTrue(false, "entity tab is not clickable");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc020_1_UpdateWatchlistLabels_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		String parentID=null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject(environment, mode,object.Activity )) {
					if(sp.clickOnObjectFeature(environment, mode,object.Activity, ObjectFeatureName.FieldAndRelationShip)) {
						if (sp.clickOnAlreadyCreatedLayout(PageLabel.Watchlist.toString())) {
							switchToFrame(driver, 10, sp.getFrame(PageName.ActivityLayoutPage, 10));
							if (click(driver, ip.getEditButton(environment,  Mode.Classic.toString(),10), "edit classic", action.BOOLEAN)) {
								switchToDefaultContent(driver);
								switchToFrame(driver, 10, sp.getFrame(PageName.ActivityLayoutPage, 10));
								ThreadSleep(3000);
								sp.getFieldLabelTextBox(10).sendKeys("Watch list");
								
								
								if (click(driver, fp.getCustomTabSaveBtn(10), "save", action.BOOLEAN)) {
											switchToAlertAndAcceptOrDecline(driver, 10, action.ACCEPT);
											click(driver, fp.getCustomTabSaveBtn(10), "save", action.BOOLEAN);
												
											log(LogStatus.INFO, "successfully changed watchlist label", YesNo.No);
									}else {
										sa.assertTrue(false,"not able to click on save button");
										log(LogStatus.SKIP,"not able to click on save button",YesNo.Yes);
									}
								
								
							}else {
								sa.assertTrue(false,"edit button is not clickable");
								log(LogStatus.SKIP,"edit button is not clickable",YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"watchlist layout is not clickable");
							log(LogStatus.SKIP,"watchlist layout is not clickable",YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"field and relationships is not clickable");
						log(LogStatus.SKIP,"field and relationships is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"activity object is not clickable");
					log(LogStatus.SKIP,"activity object is not clickable",YesNo.Yes);
				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				sa.assertTrue(false,"new window is not found, so cannot change watchlist label");
				log(LogStatus.SKIP,"new window is not found, so cannot change watchlist label",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"setup link is not clickable");
			log(LogStatus.SKIP,"setup link is not clickable",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc020_2_UpdateWatchlistLabels_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TWINS2Name, 20)) {
				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task , 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task_with_Multiple_Associations.toString(), action.BOOLEAN)) {
					ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task , 10);
					if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), TWTaskUpdateLabelSubject, "Subject", action.SCROLLANDBOOLEAN)) {

						boolean flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object2Tab, Smoke_TWContact3FName+" "+Smoke_TWContact3LName, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.SKIP,"Selected "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.No);

						} else {
							sa.assertTrue(false,"Not Able to Select "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name);
							log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.Yes);

						}

						flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object2Tab, Smoke_TWContact1FName+" "+Smoke_TWContact1LName, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							log(LogStatus.SKIP,"Selected "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name,YesNo.No);

						} else {
							sa.assertTrue(false,"Not Able to Select "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name);
							log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name,YesNo.Yes);

						}
						if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,10), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
						}
						else {
							log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
							sa.assertTrue(false,"save button is not clickable so task not created" );
						}
					}else {
						log(LogStatus.ERROR, "subject textbox is not visible, so cannot create task", YesNo.Yes);
						sa.assertTrue(false,"subject textbox is not visible, so cannot create task" );
					}
				}else {
					log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
					sa.assertTrue(false,"could not click on new task button" );
				}
			}else {
				log(LogStatus.ERROR, "could not click on ins "+Smoke_TWINS2Name, YesNo.Yes);
				sa.assertTrue(false,"could not click on ins " +Smoke_TWINS2Name);
			}
		}else {
			log(LogStatus.ERROR, "entity tab is not clickable", YesNo.Yes);
			sa.assertTrue(false,"entity tab is not clickable" );
		}

		if (ip.clickOnTab(projectName, TabName.TaskTab)) {
			WebElement ele=tp.getTaskNameLinkInSideMMenu(projectName, TWTaskUpdateLabelSubject, 15);
			if (click(driver, ele, TWTaskUpdateLabelSubject, action.BOOLEAN)) {
				String[][] fieldsWithValues= {{PageLabel.Subject.toString(),TWTaskUpdateLabelSubject},
						{PageLabel.Watch_list.toString(),Watchlist.True.toString()}};

				tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);
			}else {
				log(LogStatus.ERROR, TWTaskUpdateLabelSubject+" task is not clickable", YesNo.Yes);
				sa.assertTrue(false,TWTaskUpdateLabelSubject+" task is not clickable" );
			}
		}else {
			log(LogStatus.ERROR, "task tab is not clickable", YesNo.Yes);
			sa.assertTrue(false,"task tab is not clickable" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc021_DeleteContactAndVerifyImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		String contact=Smoke_TWContact3FName+" "+Smoke_TWContact3LName;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object2Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab,contact , 20)) {
				WebElement ele=null;

				cp.clickOnShowMoreDropdownOnly(projectName,PageName.Object2Page);
				log(LogStatus.INFO,"Able to Click on Show more Icon : "+TabName.Object2Tab+" For : "+contact,YesNo.No);
				ThreadSleep(500);
				ele = cp.actionDropdownElement(projectName, PageName.Object2Page, ShowMoreActionDropDownList.Delete, 15);
				if (ele==null) {
					ele =cp.getDeleteButton(projectName, 30);
				}
				if (click(driver, ele, "delete", action.BOOLEAN)) {
					if(click(driver,cp.getDeleteButtonPopUp(projectName, 10), "delete", action.BOOLEAN)) {
						log(LogStatus.INFO,"Able to Click on delete button on delete popup : "+TabName.Object2Tab+" For : "+contact,YesNo.No); 
					}else {
						sa.assertTrue(false,"Not Able to Select delete button for "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"Not Able to Select delete button for "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.Yes);

					}

				}else {
					sa.assertTrue(false,"Not Able to Select delete button for "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name);
					log(LogStatus.SKIP,"Not Able to Select delete button for "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.Yes);

				}
			}else {
				sa.assertTrue(false,"Not Able to find contact "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name);
				log(LogStatus.SKIP,"Not Able to find contact "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.Yes);

			}
		}else {
			sa.assertTrue(false,"not able to click on contact tab");
			log(LogStatus.SKIP,"not able to click on contact tab",YesNo.Yes);

		}
		
		if (ip.clickOnTab(projectName, TabName.TaskTab)) {
			WebElement ele=tp.getTaskNameLinkInSideMMenu(projectName, TWTaskUpdateLabelSubject, 15);
			if (click(driver, ele, TWTaskUpdateLabelSubject, action.BOOLEAN)) {
				String[][] fieldsWithValues= {{PageLabel.Subject.toString(),TWTaskUpdateLabelSubject},
						{PageLabel.Watch_list.toString(),Watchlist.True.toString()}};

				if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10)) {

					appLog.info("successfully verifid watchlist as checked before any action");
				}
				else {
					appLog.error("could not verify watchlist as checked");
					sa.assertTrue(false, "could not verify watchlist as checked");
				}
				scrollDownThroughWebelement(driver, ip.getEditButton(environment,  mode,10), "edit");
				if (click(driver, ip.getEditButton(environment,  mode,10), "edit", action.SCROLLANDBOOLEAN)) {
					if (sendKeys(driver, tp.getdueDateTextBoxInNewTask(projectName, 20), todaysDate, PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
						if  (click(driver, ip.getCustomTabSaveBtn(projectName,10), "save", action.BOOLEAN)) {
							appLog.info("successfully clicked on save button after changing contact");
							ThreadSleep(3000);
							ele=ip.getCustomTabSaveBtn(projectName,10);
							if (ele!=null) {
								log(LogStatus.ERROR,"edit mode opened after clicking on save button",YesNo.Yes);
								sa.assertTrue(false, "edit mode opened after clicking on save button");
								click(driver, ele, "save", action.BOOLEAN);
							}


							if (tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10)) {

								log(LogStatus.INFO,"successfully verifid watchlist as checked same as before",YesNo.No);
							}
							else {
								log(LogStatus.ERROR,"could not verify watchlist as checked",YesNo.Yes);
								sa.assertTrue(false, "could not verify watchlist as checked");
							}
						}else {
							log(LogStatus.ERROR,"save button is not clickable",YesNo.Yes);
							sa.assertTrue(false, "save button is not clickable");
						}
					}else {
						log(LogStatus.ERROR,"due date textbox is not visible",YesNo.Yes);
						sa.assertTrue(false, "due date textbox is not visible");
					}
					}else {
						log(LogStatus.ERROR,"edit button is not clickable",YesNo.Yes);
						sa.assertTrue(false, "edit button is not clickable");
					}
				}else {
					log(LogStatus.ERROR,"task link is not clickable, so cannot verify watchlist checkbox functionality",YesNo.Yes);
					sa.assertTrue(false, "task link is not clickable, so cannot verify watchlist checkbox functionality");
				}
		}else {
			log(LogStatus.ERROR,"task tab is not clickable",YesNo.Yes);
			sa.assertTrue(false, "task tab is not clickable");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2ConvDatetc022_1_AddConversionDateField(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
	
		String parentID=null;
		String mode="Lightning";
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
			List<String> layoutName = new ArrayList<String>();
			layoutName.add("Company");
			HashMap<String, String> sourceANDDestination = new HashMap<String, String>();
			sourceANDDestination.put(PageLabel.Deal_Conversion_Date.toString(), PageLabel.Investment_Type.toString());
			List<String> abc = setup.DragNDrop("", mode, object.Institution, ObjectFeatureName.pageLayouts, layoutName, sourceANDDestination);
			ThreadSleep(10000);
			if (!abc.isEmpty()) {
				log(LogStatus.FAIL, "field not added/already present 1", YesNo.Yes);
			}else{
				log(LogStatus.INFO, "field added/already present 1", YesNo.Yes);
			}
			driver.close();
			driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch, so cannot add field", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch, so cannot add field");
			}
		}
		else {
			log(LogStatus.FAIL, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}
		lp.CRMlogout();
		sa.assertAll();
	
	}

	
	@Parameters({ "projectName"})
	@Test
	public void M2ConvDatetc022_2_CreateInsCompany(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.createEntityOrAccount(projectName, Smoke_CDINS1Name, Smoke_CDINS1RecordType, new String[][] {{PageLabel.Status.toString(),Smoke_CDINS1Status}}, 10)) {
				log(LogStatus.INFO,"successfully Created Account/Entity : "+Smoke_CDINS1Name+" of record type : "+Smoke_CDINS1RecordType,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Account/Entity : "+Smoke_CDINS1Name+" of record type : "+Smoke_CDINS1RecordType);
				log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+Smoke_CDINS1Name+" of record type : "+Smoke_CDINS1RecordType,YesNo.Yes);
			}
		}else {
			log(LogStatus.FAIL, "entity tab is not clickable", YesNo.Yes);
			sa.assertTrue(false, "entity tab is not clickable");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2ConvDatetc023_ChangeStatusOfCompanyAndCheck(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		String mode="Lightning";
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String labels[]={PageLabel.Status.toString(),PageLabel.Deal_Conversion_Date.toString()};
		int i=0;
		String values1[]={PageLabel.Under_Evaluation.toString().replace("_", " "),todaysDateSingleDigit};
		String values2[]={PageLabel.Watchlist.toString(),todaysDateSingleDigit};
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
		if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_CDINS1Name, 10)) {
			if (ip.changeStatus(projectName, PageLabel.Under_Evaluation.toString())) {
				for (String s:labels) {
				if (ip.fieldValueVerificationOnInstitutionPage("", mode, TabName.Object1Tab, s, values1[i])) {
					log(LogStatus.INFO,"successfully verified "+s+" : "+values1[i],YesNo.No);	
				}else {
					log(LogStatus.ERROR,"could not verify "+s+" : "+values1[i],YesNo.No);	
					sa.assertTrue(false,"could not verify "+s+" : "+values1[i] );
				}
				i++;
				}
			}else {
				log(LogStatus.ERROR,"could not change status",YesNo.No);	
				sa.assertTrue(false,"could not change status" );
			}
			
			if (ip.changeStatus(projectName, PageLabel.Watchlist.toString())) {
				i=0;
				for (String s:labels) {
				if (ip.fieldValueVerificationOnInstitutionPage("", mode, TabName.Object1Tab, s, values2[i])) {
					log(LogStatus.INFO,"successfully verified "+s+" : "+values2[i],YesNo.No);	
				}else {
					log(LogStatus.ERROR,"could not verify "+s+" : "+values2[i],YesNo.No);	
					sa.assertTrue(false,"could not verify "+s+" : "+values2[i] );
				}
				i++;
				}
			}else {
				log(LogStatus.ERROR,"could not change status",YesNo.No);	
				sa.assertTrue(false,"could not change status" );
			}
		}else {
			log(LogStatus.FAIL, "could not click on entity: "+Smoke_CDINS1Name, YesNo.Yes);
			sa.assertTrue(false, "could not click on entity: "+Smoke_CDINS1Name);
		}
		}else {
			log(LogStatus.FAIL, "entity tab is not clickable", YesNo.Yes);
			sa.assertTrue(false, "entity tab is not clickable");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2ConvDatetc024_CreateNewCompanyUnderEvalThenChangeToPortfolioCompany(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String values1[]={PageLabel.Under_Evaluation.toString(),""};
		String values2[]={PageLabel.Watchlist.toString(),""};
		String values3[]={PageLabel.Under_Evaluation.toString(),todaysDateSingleDigit};
		String values4[]={PageLabel.Portfolio_Company.toString(),todaysDateSingleDigit};
		
		String labels[]={PageLabel.Status.toString(),PageLabel.Deal_Conversion_Date.toString()};
		int i=0;
		
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.createEntityOrAccount(projectName, Smoke_CDINS2Name, Smoke_CDINS2RecordType, new String[][] {{PageLabel.Status.toString(),Smoke_CDINS2Status}}, 10)) {
				log(LogStatus.INFO,"successfully Created Account/Entity : "+Smoke_CDINS1Name+" of record type : "+Smoke_CDINS1RecordType,YesNo.No);	
				for (String s:labels) {
					if (ip.fieldValueVerificationOnInstitutionPage("", mode, TabName.Object1Tab, s, values1[i])) {
						log(LogStatus.INFO,"successfully verified "+s+" : "+values1[i],YesNo.No);	
					}else {
						log(LogStatus.ERROR,"could not verify "+s+" : "+values1[i],YesNo.No);	
						sa.assertTrue(false,"could not verify "+s+" : "+values1[i] );
					}
					i++;
				}
				
				
				if (ip.changeStatus(projectName, PageLabel.Watchlist.toString())) {
					i=0;
					for (String s:labels) {
					if (ip.fieldValueVerificationOnInstitutionPage("", mode, TabName.Object1Tab, s, values2[i])) {
						log(LogStatus.INFO,"successfully verified "+s+" : "+values2[i],YesNo.No);	
					}else {
						log(LogStatus.ERROR,"could not verify "+s+" : "+values2[i],YesNo.No);	
						sa.assertTrue(false,"could not verify "+s+" : "+values2[i] );
					}
					i++;
					}
				}else {
					log(LogStatus.ERROR,"could not change status",YesNo.No);	
					sa.assertTrue(false,"could not change status" );
				}
				
				if (ip.changeStatus(projectName, PageLabel.Under_Evaluation.toString())) {
					i=0;
					for (String s:labels) {
					if (ip.fieldValueVerificationOnInstitutionPage("", mode, TabName.Object1Tab, s, values3[i])) {
						log(LogStatus.INFO,"successfully verified "+s+" : "+values3[i],YesNo.No);	
					}else {
						log(LogStatus.ERROR,"could not verify "+s+" : "+values3[i],YesNo.No);	
						sa.assertTrue(false,"could not verify "+s+" : "+values3[i] );
					}
					i++;
					}
				}else {
					log(LogStatus.ERROR,"could not change status",YesNo.No);	
					sa.assertTrue(false,"could not change status" );
				}
				
				
				if (ip.changeStatus(projectName, PageLabel.Portfolio_Company.toString())) {
					i=0;
					for (String s:labels) {
					if (ip.fieldValueVerificationOnInstitutionPage("", mode, TabName.Object1Tab, s, values4[i])) {
						log(LogStatus.INFO,"successfully verified "+s+" : "+values4[i],YesNo.No);	
					}else {
						log(LogStatus.ERROR,"could not verify "+s+" : "+values4[i],YesNo.No);	
						sa.assertTrue(false,"could not verify "+s+" : "+values4[i] );
					}
					i++;
					}
				}else {
					log(LogStatus.ERROR,"could not change status",YesNo.No);	
					sa.assertTrue(false,"could not change status" );
				}
			} else {
				sa.assertTrue(false,"Not Able to Create Account/Entity : "+Smoke_CDINS2Name+" of record type : "+Smoke_CDINS2RecordType);
				log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+Smoke_CDINS2Name+" of record type : "+Smoke_CDINS2RecordType,YesNo.Yes);
			}
		}else {
			log(LogStatus.FAIL, "entity tab is not clickable", YesNo.Yes);
			sa.assertTrue(false, "entity tab is not clickable");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2ConvDatetc025_RenameStatusValues(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String parentID=null;
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject(environment, mode,object.Institution )) {
					if(sp.clickOnObjectFeature(environment, mode,object.Institution, ObjectFeatureName.FieldAndRelationShip)) {
						if (sendKeys(driver, sp.getsearchTextboxFieldsAndRelationships(10), PageLabel.Status.toString()+Keys.ENTER, "status", action.BOOLEAN)) {
							if (sp.clickOnAlreadyCreatedLayout(PageLabel.Status.toString())) {
								switchToFrame(driver, 10, sp.getFrame(PageName.AccountCustomFieldStatusPage, 10));
								WebElement ele=sp.clickOnEditInFrontOfFieldValues(projectName, PageLabel.Watchlist.toString());
								if (click(driver, ele, "watchlist", action.BOOLEAN)) {
									switchToDefaultContent(driver);
									switchToFrame(driver, 10, sp.getstatusPicklistFrame(10));
									if (sendKeys(driver, sp.getFieldLabelTextBox1(10), PageLabel.RenameWatchlist.toString(), "label", action.BOOLEAN)) {
										
										if (click(driver, fp.getCustomTabSaveBtn(10), "save", action.BOOLEAN)) {
											log(LogStatus.INFO,"successfully saved rename watchlist",YesNo.No);	
											
										}else {
											log(LogStatus.ERROR,"could not click on save button, so cannot change watchlist name",YesNo.No);	
											sa.assertTrue(false,"could not click on save button, so cannot change watchlist name" );
										}
									}else {
										log(LogStatus.ERROR,"field label textbox is not visible",YesNo.No);	
										sa.assertTrue(false,"field label textbox is not visible" );
									}
								}else {
									log(LogStatus.ERROR,"edit link is not clickable for watchlist",YesNo.No);	
									sa.assertTrue(false,"edit link is not clickable for watchlist" );
								}
								switchToDefaultContent(driver);
								switchToFrame(driver, 10, sp.getFrame(PageName.AccountCustomFieldStatusPage, 10));
								ele=sp.clickOnEditInFrontOfFieldValues(projectName, PageLabel.Under_Evaluation.toString());
								if (click(driver, ele, "watchlist", action.BOOLEAN)) {
									switchToDefaultContent(driver);
									switchToFrame(driver, 10, sp.getstatusPicklistFrame(10));
									
									if (sendKeys(driver, sp.getFieldLabelTextBox1(10), PageLabel.RenameUnder_Evaluation.toString().replace("_", " "), "label", action.BOOLEAN)) {
										
										if (click(driver, fp.getCustomTabSaveBtn(10), "save", action.BOOLEAN)) {
											log(LogStatus.INFO,"successfully saved rename watchlist",YesNo.No);	
											
										}else {
											log(LogStatus.ERROR,"could not click on save button, so cannot change Under_Evaluation name",YesNo.No);	
											sa.assertTrue(false,"could not click on save button, so cannot change Under_Evaluation name" );
										}
									}else {
										log(LogStatus.ERROR,"field label textbox is not visible",YesNo.No);	
										sa.assertTrue(false,"field label textbox is not visible" );
									}
								}else {
									log(LogStatus.ERROR,"edit link is not clickable for watchlist",YesNo.No);	
									sa.assertTrue(false,"edit link is not clickable for watchlist" );
								}
								switchToDefaultContent(driver);
								switchToFrame(driver, 10, sp.getFrame(PageName.AccountCustomFieldStatusPage, 10));
								ele=sp.clickOnEditInFrontOfFieldValues(projectName, PageLabel.RenameUnder_Evaluation.toString());
								WebElement ele1=null;
								ele1=sp.clickOnEditInFrontOfFieldValues(projectName, PageLabel.RenameWatchlist.toString());
								if ((ele!=null)&&(ele1!=null)) {
									log(LogStatus.INFO,"successfully verified rename of status values",YesNo.No);	
									
								}else {
									log(LogStatus.ERROR,"status field is not renamed",YesNo.No);	
									sa.assertTrue(false,"status field is not renamed" );
								}
							}else {
								log(LogStatus.ERROR,"status field is not clickable",YesNo.No);	
								sa.assertTrue(false,"status field is not clickable" );
							}
						}else {
							log(LogStatus.ERROR,"fields and relationships search is not visible",YesNo.No);	
							sa.assertTrue(false,"fields and relationships search is not visible" );
						}
					}else {
						log(LogStatus.ERROR,"fields and relationships link is not clickable",YesNo.No);	
						sa.assertTrue(false,"fields and relationships link is not clickable" );
					}
				}else {
					log(LogStatus.ERROR,"entity object is not clickable",YesNo.No);	
					sa.assertTrue(false,"entity object is not clickable" );
				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.ERROR,"could not find new window to switch after clicking setup",YesNo.No);	
				sa.assertTrue(false,"could not find new window to switch after clicking setup" );
			}
		}else {
			log(LogStatus.ERROR,"setup link is not clickable, so cannot rename fields name",YesNo.No);	
			sa.assertTrue(false,"setup link is not clickable, so cannot rename fields name" );
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	
	@Parameters({ "projectName"})
	@Test
	public void M2ConvDatetc026_CreateInsCompany(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		String status=PageLabel.RenameWatchlist.toString();
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String labels[]={PageLabel.Status.toString(),PageLabel.Deal_Conversion_Date.toString()};
		int i=0;
		String values1[]={PageLabel.RenameWatchlist.toString(),""};
		String values2[]={PageLabel.RenameUnder_Evaluation.toString(),todaysDateSingleDigit};
		
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.createEntityOrAccount(projectName, Smoke_CDINS3Name, Smoke_CDINS3RecordType, new String[][] {{PageLabel.Status.toString(),status}}, 10)) {
				log(LogStatus.INFO,"successfully Created Account/Entity : "+Smoke_CDINS3Name+" of record type : "+Smoke_CDINS3RecordType,YesNo.No);	
				for (String s:labels) {
					if (ip.fieldValueVerificationOnInstitutionPage("", mode, TabName.Object1Tab, s, values1[i])) {
						log(LogStatus.INFO,"successfully verified "+s+" : "+values1[i],YesNo.No);	
					}else {
						log(LogStatus.ERROR,"could not verify "+s+" : "+values1[i],YesNo.No);	
						sa.assertTrue(false,"could not verify "+s+" : "+values1[i] );
					}
					i++;
					}
				i=0;
				if (ip.changeStatus(projectName, PageLabel.RenameUnder_Evaluation.toString())) {
					for (String s:labels) {
					if (ip.fieldValueVerificationOnInstitutionPage("", mode, TabName.Object1Tab, s, values2[i])) {
						log(LogStatus.INFO,"successfully verified "+s+" : "+values2[i],YesNo.No);
					}else {
						log(LogStatus.ERROR,"could not verify "+s+" : "+values2[i],YesNo.No);	
						sa.assertTrue(false,"could not verify "+s+" : "+values2[i] );
					}
					i++;
					}
				}else {
					log(LogStatus.ERROR,"could not change status",YesNo.No);	
					sa.assertTrue(false,"could not change status" );
				}
			} else {
				sa.assertTrue(false,"Not Able to Create Account/Entity : "+Smoke_CDINS3Name+" of record type : "+Smoke_CDINS3RecordType);
				log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+Smoke_CDINS3Name+" of record type : "+Smoke_CDINS3RecordType,YesNo.Yes);
			}
		}else {
			log(LogStatus.FAIL, "entity tab is not clickable", YesNo.Yes);
			sa.assertTrue(false, "entity tab is not clickable");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2ConvDatetc027_PostConditionRevertRenameStatusValues(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String parentID=null;
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject(environment, mode,object.Institution )) {
					if(sp.clickOnObjectFeature(environment, mode,object.Institution, ObjectFeatureName.FieldAndRelationShip)) {
						if (sendKeys(driver, sp.getsearchTextboxFieldsAndRelationships(10), PageLabel.Status.toString()+Keys.ENTER, "status", action.BOOLEAN)) {
							if (sp.clickOnAlreadyCreatedLayout(PageLabel.Status.toString())) {
								switchToFrame(driver, 10, sp.getFrame(PageName.AccountCustomFieldStatusPage, 10));
								WebElement ele=sp.clickOnEditInFrontOfFieldValues(projectName, PageLabel.RenameWatchlist.toString());
								if (click(driver, ele, "watchlist", action.BOOLEAN)) {
									switchToDefaultContent(driver);
									switchToFrame(driver, 10, sp.getstatusPicklistFrame(10));
									if (sendKeys(driver, sp.getFieldLabelTextBox1(10), PageLabel.Watchlist.toString(), "label", action.BOOLEAN)) {
										
										if (click(driver, fp.getCustomTabSaveBtn(10), "save", action.BOOLEAN)) {
											log(LogStatus.INFO,"successfully saved rename watchlist",YesNo.No);	
											
										}else {
											log(LogStatus.ERROR,"could not click on save button, so cannot change watchlist name",YesNo.No);	
											sa.assertTrue(false,"could not click on save button, so cannot change watchlist name" );
										}
									}else {
										log(LogStatus.ERROR,"field label textbox is not visible",YesNo.No);	
										sa.assertTrue(false,"field label textbox is not visible" );
									}
								}else {
									log(LogStatus.ERROR,"edit link is not clickable for watchlist",YesNo.No);	
									sa.assertTrue(false,"edit link is not clickable for watchlist" );
								}
								switchToDefaultContent(driver);
								switchToFrame(driver, 10, sp.getFrame(PageName.AccountCustomFieldStatusPage, 10));
								ele=sp.clickOnEditInFrontOfFieldValues(projectName, PageLabel.RenameUnder_Evaluation.toString());
								if (click(driver, ele, "watchlist", action.BOOLEAN)) {
									switchToDefaultContent(driver);
									switchToFrame(driver, 10, sp.getstatusPicklistFrame(10));
									
									if (sendKeys(driver, sp.getFieldLabelTextBox1(10), PageLabel.Under_Evaluation.toString().replace("_", " "), "label", action.BOOLEAN)) {
										
										if (click(driver, fp.getCustomTabSaveBtn(10), "save", action.BOOLEAN)) {
											log(LogStatus.INFO,"successfully saved rename watchlist",YesNo.No);	
											
										}else {
											log(LogStatus.ERROR,"could not click on save button, so cannot change Under_Evaluation name",YesNo.No);	
											sa.assertTrue(false,"could not click on save button, so cannot change Under_Evaluation name" );
										}
									}else {
										log(LogStatus.ERROR,"field label textbox is not visible",YesNo.No);	
										sa.assertTrue(false,"field label textbox is not visible" );
									}
								}else {
									log(LogStatus.ERROR,"edit link is not clickable for watchlist",YesNo.No);	
									sa.assertTrue(false,"edit link is not clickable for watchlist" );
								}
								switchToDefaultContent(driver);
								switchToFrame(driver, 10, sp.getFrame(PageName.AccountCustomFieldStatusPage, 10));
								ele=sp.clickOnEditInFrontOfFieldValues(projectName, PageLabel.Under_Evaluation.toString());
								WebElement ele1=null;
								ele1=sp.clickOnEditInFrontOfFieldValues(projectName, PageLabel.Watchlist.toString());
								if ((ele!=null)&&(ele1!=null)) {
									log(LogStatus.INFO,"successfully verified rename of status values",YesNo.No);	
									
								}else {
									log(LogStatus.ERROR,"status field is not renamed",YesNo.No);	
									sa.assertTrue(false,"status field is not renamed" );
								}
							}else {
								log(LogStatus.ERROR,"status field is not clickable",YesNo.No);	
								sa.assertTrue(false,"status field is not clickable" );
							}
						}else {
							log(LogStatus.ERROR,"fields and relationships search is not visible",YesNo.No);	
							sa.assertTrue(false,"fields and relationships search is not visible" );
						}
					}else {
						log(LogStatus.ERROR,"fields and relationships link is not clickable",YesNo.No);	
						sa.assertTrue(false,"fields and relationships link is not clickable" );
					}
				}else {
					log(LogStatus.ERROR,"entity object is not clickable",YesNo.No);	
					sa.assertTrue(false,"entity object is not clickable" );
				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.ERROR,"could not find new window to switch after clicking setup",YesNo.No);	
				sa.assertTrue(false,"could not find new window to switch after clicking setup" );
			}
		}else {
			log(LogStatus.ERROR,"setup link is not clickable, so cannot rename fields name",YesNo.No);	
			sa.assertTrue(false,"setup link is not clickable, so cannot rename fields name" );
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	
	@Parameters({ "projectName"})
	@Test
	public void M2ConvDatetc028_1_Precondition(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		WebElement ele=null;
		String parentID=null;
		String[] stages={Stage.Deal_Received.toString(),Stage.NDA_Signed.toString(),Stage.IOI.toString(),Stage.Management_Meeting.toString(),Stage.Due_Diligence.toString(),Stage.Parked.toString()};
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject(environment, mode,object.Deal )) {
					if(sp.clickOnObjectFeature(environment, mode,object.Deal, ObjectFeatureName.FieldAndRelationShip)) {
						if (sendKeys(driver, sp.getsearchTextboxFieldsAndRelationships(10), excelLabel.Stage.toString()+Keys.ENTER, "status", action.BOOLEAN)) {
							if (sp.clickOnAlreadyCreatedLayout(excelLabel.Stage.toString())) {
								for (String s:stages) {
									switchToDefaultContent(driver);
									switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
										s=s.replace("_", " ");
									ele=dp.findDeactivateLink(projectName, s);
									if (ele!=null) {
										log(LogStatus.INFO,"successfully found active stage : "+s,YesNo.No);	
									}else {
										log(LogStatus.ERROR,"stage is not active, now activating "+s,YesNo.No);	
										ThreadSleep(5000);
										
										ele=dp.findActivateLink(projectName, s);
										if (click(driver, ele, "activate", action.SCROLLANDBOOLEAN)) {
											switchToDefaultContent(driver);
											switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
											
											ele=dp.findDeactivateLink(projectName, s);
											if (ele!=null) {
												log(LogStatus.INFO,"successfully verified active stage : "+s,YesNo.No);	
												
											}
											else {
												log(LogStatus.ERROR,"could not activate stage : "+s,YesNo.No);	
												sa.assertTrue(false,"could not activate stage : "+s);
											}
										}else {
											log(LogStatus.ERROR,"could not click on activate button for stage : "+s,YesNo.No);	
											sa.assertTrue(false,"could not click on activate button for stage : "+s);
										}
									}
								}
							}else {
								log(LogStatus.ERROR,"stage field is not clickable",YesNo.No);	
								sa.assertTrue(false,"stage field is not clickable");
							}
						}else {
							log(LogStatus.ERROR,"search textbox field is not visible",YesNo.No);	
							sa.assertTrue(false,"search textbox field is not visible");
						}
					}else {
						log(LogStatus.ERROR,"deal object is not clickable",YesNo.No);	
						sa.assertTrue(false,"deal object is not clickable");
					}
				}else {
					log(LogStatus.ERROR,"could not search deal object",YesNo.No);	
					sa.assertTrue(false,"could not search deal object");
				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.ERROR,"could not find new window",YesNo.No);	
				sa.assertTrue(false,"could not find new window");
			}
		}else {
			log(LogStatus.ERROR,"setup link is not clickable",YesNo.No);	
			sa.assertTrue(false,"setup link is not clickable");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
				
	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc028_2_DataCreation(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String ins,rt,fn,ln,mailID;
		for (int i =0;i<5;i++) {
			if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
				ins=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "HSRINS"+(i+1), excelLabel.Institutions_Name);
				rt=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "HSRINS"+(i+1), excelLabel.Record_Type);

				if (ip.createEntityOrAccount(projectName, ins, rt, null, 10)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+ins+" of record type : "+rt,YesNo.No);	
				}else {
					log(LogStatus.ERROR,"could not create account: "+ins,YesNo.No);	
					sa.assertTrue(false,"could not create account: "+ins );
				}
			}else {
				log(LogStatus.ERROR,"could not click on account/entity tab",YesNo.No);	
				sa.assertTrue(false,"could not click on account/entity tab" );
			}
		}
		for (int i=0;i<3;i++) {
			if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);	
				fn=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "HSRCON"+(i+1), excelLabel.Contact_FirstName);
				ln=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "HSRCON"+(i+1), excelLabel.Contact_LastName);
				ins=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "HSRCON"+(i+1), excelLabel.Institutions_Name);

				mailID=	lp.generateRandomEmailId(gmailUserName);
				ExcelUtils.writeData(phase1DataSheetFilePath, mailID, "Contacts", excelLabel.Variable_Name, "HSRCON"+(i+1),excelLabel.Contact_EmailId);

				if (cp.createContact(projectName, fn, ln, ins, mailID,"", null, null, CreationPage.ContactPage, null)) {
					log(LogStatus.INFO,"successfully Created Contact : "+fn+" "+ln,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Contact : "+fn+" "+ln);
					log(LogStatus.SKIP,"Not Able to Create Contact: "+fn+" "+ln,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
			}
		}
		String pipe,company,stage,sf,sc;
		for (int i=0;i<1;i++) {
			if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object4Tab,YesNo.No);	
				pipe= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP"+(i+1), excelLabel.Deal_Name);
				company= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP"+(i+1), excelLabel.Company_Name);
				sf= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP"+(i+1), excelLabel.Source_Firm);
				sc= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP"+(i+1), excelLabel.Source_Contact);
				stage= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP"+(i+1), excelLabel.Stage);
				String[][] otherLabels = {{excelLabel.Source_Contact.toString(),sc},{excelLabel.Source_Firm.toString(),sf}};
				refresh(driver);
				ThreadSleep(3000);
				if (fp.createDeal(projectName,"",pipe, company, stage,otherLabels, 15)) {
					log(LogStatus.INFO,"Created Deal : "+pipe,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Deal  : "+pipe);
					log(LogStatus.SKIP,"Not Able to Create Deal  : "+pipe,YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to click on deal tab");
				log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
			}
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc028_3_DataCreation(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=dealReceivedScore;totalDealsshown=1;averageDealQualityScore=dealQualityScore/totalDealsshown;
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Smoke_HSRPipeline1Stage,Smoke_HSRPipeline1Stage,String.valueOf(dealQualityScore)};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, Smoke_HSRPipeline1Name, 10)){
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
				for (int i =0;i<labelName.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
						log(LogStatus.SKIP,"successfully verified "+labelName[i],YesNo.No);
						
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
					}
				
				}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+Smoke_HSRPipeline1Name);
				log(LogStatus.SKIP,"Not Able to click "+Smoke_HSRPipeline1Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={Smoke_HSRINS1Name,Smoke_HSRContact1FName+" "+Smoke_HSRContact1LName};
		int j=0;
		for (TabName tab:tabName) {
		if (lp.clickOnTab(projectName, tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
				
				for (int i =0;i<labelName1.length;i++) {
					if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
						log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);
						
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+records[j]);
				log(LogStatus.SKIP,"Not Able to click "+records[j],YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		j++;
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc029_ChangeStageToNDASigned_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=NDASignedScore;totalDealsshown=1;averageDealQualityScore=dealQualityScore/totalDealsshown;;
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.NDA_Signed.toString(),Stage.NDA_Signed.toString(),String.valueOf(dealQualityScore)};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, Smoke_HSRPipeline1Name, 10)){
				if (fp.changeStage(projectName, Stage.NDA_Signed.toString(), 10)) {
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele, "details", action.SCROLLANDBOOLEAN);
					log(LogStatus.INFO,"successfully changed stage to "+Stage.NDA_Signed,YesNo.Yes);
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
							log(LogStatus.INFO,"successfully verified "+labelName[i],YesNo.Yes);
						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}
					
					}
					
				}else {
					sa.assertTrue(false,"not able to change stage to "+Stage.NDA_Signed);
					log(LogStatus.SKIP,"not able to change stage to "+Stage.NDA_Signed,YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to find pipeline "+Smoke_HSRPipeline1Name);
				log(LogStatus.SKIP,"not able to find pipeline "+Smoke_HSRPipeline1Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc029_ChangeStageToNDASigned_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=NDASignedScore;totalDealsshown=1;averageDealQualityScore=dealQualityScore/totalDealsshown;
		
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={Smoke_HSRINS1Name,Smoke_HSRContact1FName+" "+Smoke_HSRContact1LName};
		int j=0;
		WebElement ele;
		for (TabName tab:tabName) {
		if (lp.clickOnTab(projectName, tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
				
				for (int i =0;i<labelName1.length;i++) {
					if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
						log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);
						
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+records[j]);
				log(LogStatus.SKIP,"Not Able to click "+records[j],YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on "+tab);
			log(LogStatus.SKIP,"not able to click on "+tab,YesNo.Yes);
		}
		j++;
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc030_ChangeStageToManagementMeeting_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=managementMeetingScore;totalDealsshown=1;averageDealQualityScore=dealQualityScore/totalDealsshown;
		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.Management_Meeting.toString(),Stage.Management_Meeting.toString(),String.valueOf(dealQualityScore)};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, Smoke_HSRPipeline1Name, 10)){
				if (fp.changeStage(projectName, Stage.Management_Meeting.toString(), 10)) {
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele, "details", action.SCROLLANDBOOLEAN);
					log(LogStatus.INFO,"successfully changed stage to "+Stage.Management_Meeting,YesNo.Yes);
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
							log(LogStatus.INFO,"successfully verified "+labelName[i],YesNo.Yes);
						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}
					
					}
					
				}else {
					sa.assertTrue(false,"not able to change stage to "+Stage.Management_Meeting);
					log(LogStatus.SKIP,"not able to change stage to "+Stage.Management_Meeting,YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to find pipeline "+Smoke_HSRPipeline1Name);
				log(LogStatus.SKIP,"not able to find pipeline "+Smoke_HSRPipeline1Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc030_ChangeStageToManagementMeeting_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=managementMeetingScore;totalDealsshown=1;averageDealQualityScore=dealQualityScore/totalDealsshown;
		
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={Smoke_HSRINS1Name,Smoke_HSRContact1FName+" "+Smoke_HSRContact1LName};
		int j=0;
		WebElement ele;
		for (TabName tab:tabName) {
		if (lp.clickOnTab(projectName, tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
				
				for (int i =0;i<labelName1.length;i++) {
					if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
						log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);
						
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+records[j]);
				log(LogStatus.SKIP,"Not Able to click "+records[j],YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on "+tab);
			log(LogStatus.SKIP,"not able to click on "+tab,YesNo.Yes);
		}
		j++;
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}


	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc031_ChangeStageToIOI_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=ioiScore;totalDealsshown=1;averageDealQualityScore=dealQualityScore/totalDealsshown;
		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.IOI.toString(),Stage.IOI.toString(),String.valueOf(dealQualityScore)};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, Smoke_HSRPipeline1Name, 10)){
				if (fp.changeStage(projectName, Stage.IOI.toString(), 10)) {
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele, "details", action.SCROLLANDBOOLEAN);
					log(LogStatus.INFO,"successfully changed stage to "+Stage.IOI,YesNo.Yes);
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
							log(LogStatus.INFO,"successfully verified "+labelName[i],YesNo.Yes);
						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}
					
					}
					
				}else {
					sa.assertTrue(false,"not able to change stage to "+Stage.IOI);
					log(LogStatus.SKIP,"not able to change stage to "+Stage.IOI,YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to find pipeline "+Smoke_HSRPipeline1Name);
				log(LogStatus.SKIP,"not able to find pipeline "+Smoke_HSRPipeline1Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc031_ChangeStageToIOI_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=ioiScore;totalDealsshown=1;averageDealQualityScore=dealQualityScore/totalDealsshown;
		
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={Smoke_HSRINS1Name,Smoke_HSRContact1FName+" "+Smoke_HSRContact1LName};
		int j=0;
		WebElement ele;
		for (TabName tab:tabName) {
		if (lp.clickOnTab(projectName, tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
				
				for (int i =0;i<labelName1.length;i++) {
					if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
						log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);
						
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+records[j]);
				log(LogStatus.SKIP,"Not Able to click "+records[j],YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on "+tab);
			log(LogStatus.SKIP,"not able to click on "+tab,YesNo.Yes);
		}
		j++;
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc032_ChangeStageToLOI_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=loiScore;totalDealsshown=1;averageDealQualityScore=dealQualityScore/totalDealsshown;
		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.LOI.toString(),Stage.LOI.toString(),String.valueOf(dealQualityScore)};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, Smoke_HSRPipeline1Name, 10)){
				if (fp.changeStage(projectName, Stage.LOI.toString(), 10)) {
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele, "details", action.SCROLLANDBOOLEAN);
					log(LogStatus.INFO,"successfully changed stage to "+Stage.LOI,YesNo.Yes);
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
							log(LogStatus.INFO,"successfully verified "+labelName[i],YesNo.Yes);
						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}
					
					}
					
				}else {
					sa.assertTrue(false,"not able to change stage to "+Stage.LOI);
					log(LogStatus.SKIP,"not able to change stage to "+Stage.LOI,YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to find pipeline "+Smoke_HSRPipeline1Name);
				log(LogStatus.SKIP,"not able to find pipeline "+Smoke_HSRPipeline1Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc032_ChangeStageToLOI_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=loiScore;totalDealsshown=1;averageDealQualityScore=dealQualityScore/totalDealsshown;
		
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={Smoke_HSRINS1Name,Smoke_HSRContact1FName+" "+Smoke_HSRContact1LName};
		int j=0;
		WebElement ele;
		for (TabName tab:tabName) {
		if (lp.clickOnTab(projectName, tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
				
				for (int i =0;i<labelName1.length;i++) {
					if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
						log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);
						
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+records[j]);
				log(LogStatus.SKIP,"Not Able to click "+records[j],YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on "+tab);
			log(LogStatus.SKIP,"not able to click on "+tab,YesNo.Yes);
		}
		j++;
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc033_ChangeStageToDueDiligence_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=dueDiligenceScore;totalDealsshown=1;averageDealQualityScore=dealQualityScore/totalDealsshown;
		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.Due_Diligence.toString(),Stage.Due_Diligence.toString(),String.valueOf(dealQualityScore)};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, Smoke_HSRPipeline1Name, 10)){
				if (fp.changeStage(projectName, Stage.Due_Diligence.toString(), 10)) {
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele, "details", action.SCROLLANDBOOLEAN);
					log(LogStatus.INFO,"successfully changed stage to "+Stage.Due_Diligence,YesNo.Yes);
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
							log(LogStatus.INFO,"successfully verified "+labelName[i],YesNo.Yes);
						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}
					
					}
					
				}else {
					sa.assertTrue(false,"not able to change stage to "+Stage.Due_Diligence);
					log(LogStatus.SKIP,"not able to change stage to "+Stage.Due_Diligence,YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to find pipeline "+Smoke_HSRPipeline1Name);
				log(LogStatus.SKIP,"not able to find pipeline "+Smoke_HSRPipeline1Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc033_ChangeStageToDueDiligence_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=dueDiligenceScore;totalDealsshown=1;averageDealQualityScore=dealQualityScore/totalDealsshown;
		
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={Smoke_HSRINS1Name,Smoke_HSRContact1FName+" "+Smoke_HSRContact1LName};
		int j=0;
		WebElement ele;
		for (TabName tab:tabName) {
		if (lp.clickOnTab(projectName, tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
				
				for (int i =0;i<labelName1.length;i++) {
					if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
						log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);
						
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+records[j]);
				log(LogStatus.SKIP,"Not Able to click "+records[j],YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on "+tab);
			log(LogStatus.SKIP,"not able to click on "+tab,YesNo.Yes);
		}
		j++;
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc034_ChangeStageToParked_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=parkedScore;totalDealsshown=1;averageDealQualityScore=dealQualityScore/totalDealsshown;
		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.Due_Diligence.toString(),Stage.Parked.toString(),String.valueOf(dealQualityScore)};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, Smoke_HSRPipeline1Name, 10)){
				if (fp.changeStage(projectName, Stage.Parked.toString(), 10)) {
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele, "details", action.SCROLLANDBOOLEAN);
					log(LogStatus.INFO,"successfully changed stage to "+Stage.Parked,YesNo.Yes);
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
							log(LogStatus.INFO,"successfully verified "+labelName[i],YesNo.Yes);
						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}
					
					}
					
				}else {
					sa.assertTrue(false,"not able to change stage to "+Stage.Parked);
					log(LogStatus.SKIP,"not able to change stage to "+Stage.Parked,YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to find pipeline "+Smoke_HSRPipeline1Name);
				log(LogStatus.SKIP,"not able to find pipeline "+Smoke_HSRPipeline1Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc034_ChangeStageToParked_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=parkedScore;totalDealsshown=1;averageDealQualityScore=dealQualityScore/totalDealsshown;
		
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={Smoke_HSRINS1Name,Smoke_HSRContact1FName+" "+Smoke_HSRContact1LName};
		int j=0;
		WebElement ele;
		for (TabName tab:tabName) {
		if (lp.clickOnTab(projectName, tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
				
				for (int i =0;i<labelName1.length;i++) {
					if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
						log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);
						
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+records[j]);
				log(LogStatus.SKIP,"Not Able to click "+records[j],YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on "+tab);
			log(LogStatus.SKIP,"not able to click on "+tab,YesNo.Yes);
		}
		j++;
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc035_CreateReliancePipelines(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String pipe,company,stage,sf,sc;
		totalDealsshown=2;dealQualityScore=dealReceivedScore;averageDealQualityScore=(parkedScore+dealReceivedScore)/totalDealsshown;
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Smoke_HSRPipeline2Stage,Smoke_HSRPipeline2Stage,String.valueOf(dealQualityScore)};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		
		WebElement ele=null;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object4Tab,YesNo.No);	
			pipe= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP2", excelLabel.Deal_Name);
			company= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP2", excelLabel.Company_Name);
			sf= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP2", excelLabel.Source_Firm);
			sc= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP2", excelLabel.Source_Contact);
			stage= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP2", excelLabel.Stage);
			String[][] otherLabels = {{excelLabel.Source_Contact.toString(),sc},{excelLabel.Source_Firm.toString(),sf}};
			refresh(driver);
			ThreadSleep(3000);
			if (fp.createDeal(projectName,"",pipe, company, stage,otherLabels, 15)) {
				log(LogStatus.INFO,"Created Deal : "+pipe,YesNo.No);
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
							log(LogStatus.SKIP,"successfully verified "+labelName[i],YesNo.No);
							
						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}

					}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Create Deal  : "+pipe);
				log(LogStatus.SKIP,"Not Able to Create Deal  : "+pipe,YesNo.Yes);
			}


		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={Smoke_HSRINS1Name,Smoke_HSRContact1FName+" "+Smoke_HSRContact1LName};
		int j=0;
		for (TabName tab:tabName) {
		if (lp.clickOnTab(projectName, tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
				
				for (int i =0;i<labelName1.length;i++) {
					if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
						log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);
						
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+records[j]);
				log(LogStatus.SKIP,"Not Able to click "+records[j],YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		j++;
		}
		lp.CRMlogout();
		sa.assertAll();
	}


	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc036_ChangeStageToLOI_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		totalDealsshown=2;dealQualityScore=loiScore;averageDealQualityScore=(parkedScore+loiScore)/totalDealsshown;
		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.LOI.toString(),Stage.LOI.toString(),String.valueOf(dealQualityScore)};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, Smoke_HSRPipeline2Name, 10)){
				if (fp.changeStage(projectName, Stage.LOI.toString(), 10)) {
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele, "details", action.SCROLLANDBOOLEAN);
					log(LogStatus.INFO,"successfully changed stage to "+Stage.LOI,YesNo.Yes);
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
							log(LogStatus.INFO,"successfully verified "+labelName[i],YesNo.Yes);
						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}
					
					}
					
				}else {
					sa.assertTrue(false,"not able to change stage to "+Stage.LOI);
					log(LogStatus.SKIP,"not able to change stage to "+Stage.LOI,YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to find pipeline "+Smoke_HSRPipeline2Name);
				log(LogStatus.SKIP,"not able to find pipeline "+Smoke_HSRPipeline2Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc036_ChangeStageToLOI_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		totalDealsshown=2;dealQualityScore=loiScore;averageDealQualityScore=(parkedScore+loiScore)/totalDealsshown;
		
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={Smoke_HSRINS1Name,Smoke_HSRContact1FName+" "+Smoke_HSRContact1LName};
		int j=0;
		WebElement ele;
		for (TabName tab:tabName) {
		if (lp.clickOnTab(projectName, tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
				
				for (int i =0;i<labelName1.length;i++) {
					if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
						log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);
						
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+records[j]);
				log(LogStatus.SKIP,"Not Able to click "+records[j],YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on "+tab);
			log(LogStatus.SKIP,"not able to click on "+tab,YesNo.Yes);
		}
		j++;
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc037_ChangeStageToParked_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		totalDealsshown=2;dealQualityScore=parkedScore;averageDealQualityScore=(parkedScore+parkedScore)/totalDealsshown;
		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.LOI.toString(),Stage.Parked.toString(),String.valueOf(dealQualityScore)};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, Smoke_HSRPipeline2Name, 10)){
				if (fp.changeStage(projectName, Stage.Parked.toString(), 10)) {
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele, "details", action.SCROLLANDBOOLEAN);
					log(LogStatus.INFO,"successfully changed stage to "+Stage.LOI,YesNo.Yes);
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
							log(LogStatus.INFO,"successfully verified "+labelName[i],YesNo.Yes);
						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}
					
					}
					
				}else {
					sa.assertTrue(false,"not able to change stage to "+Stage.LOI);
					log(LogStatus.SKIP,"not able to change stage to "+Stage.LOI,YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to find pipeline "+Smoke_HSRPipeline2Name);
				log(LogStatus.SKIP,"not able to find pipeline "+Smoke_HSRPipeline2Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc037_ChangeStageToParked_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		totalDealsshown=2;dealQualityScore=parkedScore;averageDealQualityScore=(parkedScore+parkedScore)/totalDealsshown;
		
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={Smoke_HSRINS1Name,Smoke_HSRContact1FName+" "+Smoke_HSRContact1LName};
		int j=0;
		WebElement ele;
		for (TabName tab:tabName) {
		if (lp.clickOnTab(projectName, tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
				
				for (int i =0;i<labelName1.length;i++) {
					if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
						log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);
						
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+records[j]);
				log(LogStatus.SKIP,"Not Able to click "+records[j],YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on "+tab);
			log(LogStatus.SKIP,"not able to click on "+tab,YesNo.Yes);
		}
		j++;
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc038_ChangeStageToDeclinedDead_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		totalDealsshown=2;dealQualityScore=declinedDeadScore;averageDealQualityScore=(parkedScore+declinedDeadScore)/totalDealsshown;
		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.LOI.toString(),Stage.DeclinedDead.toString(),String.valueOf(dealQualityScore)};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, Smoke_HSRPipeline2Name, 10)){
				if (fp.changeStage(projectName, Stage.DeclinedDead.toString(), 10)) {
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele, "details", action.SCROLLANDBOOLEAN);
					log(LogStatus.INFO,"successfully changed stage to "+Stage.LOI,YesNo.Yes);
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
							log(LogStatus.INFO,"successfully verified "+labelName[i],YesNo.Yes);
						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}
					
					}
					
				}else {
					sa.assertTrue(false,"not able to change stage to "+Stage.LOI);
					log(LogStatus.SKIP,"not able to change stage to "+Stage.LOI,YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to find pipeline "+Smoke_HSRPipeline2Name);
				log(LogStatus.SKIP,"not able to find pipeline "+Smoke_HSRPipeline2Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc038_ChangeStageToDeclinedDead_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		totalDealsshown=2;dealQualityScore=declinedDeadScore;averageDealQualityScore=(parkedScore+declinedDeadScore)/totalDealsshown;
		
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={Smoke_HSRINS1Name,Smoke_HSRContact1FName+" "+Smoke_HSRContact1LName};
		int j=0;
		WebElement ele;
		for (TabName tab:tabName) {
		if (lp.clickOnTab(projectName, tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
				
				for (int i =0;i<labelName1.length;i++) {
					if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
						log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);
						
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+records[j]);
				log(LogStatus.SKIP,"Not Able to click "+records[j],YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on "+tab);
			log(LogStatus.SKIP,"not able to click on "+tab,YesNo.Yes);
		}
		j++;
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc039_ChangeStageToClosed_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		totalDealsshown=2;dealQualityScore=closedScore;averageDealQualityScore=(parkedScore+closedScore)/totalDealsshown;
		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.Closed.toString(),Stage.Closed.toString(),String.valueOf(dealQualityScore)};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, Smoke_HSRPipeline2Name, 10)){
				if (fp.changeStage(projectName, Stage.Closed.toString(), 10)) {
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele, "details", action.SCROLLANDBOOLEAN);
					log(LogStatus.INFO,"successfully changed stage to "+Stage.Closed,YesNo.Yes);
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
							log(LogStatus.INFO,"successfully verified "+labelName[i],YesNo.Yes);
						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}
					
					}
					
				}else {
					sa.assertTrue(false,"not able to change stage to "+Stage.LOI);
					log(LogStatus.SKIP,"not able to change stage to "+Stage.LOI,YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to find pipeline "+Smoke_HSRPipeline2Name);
				log(LogStatus.SKIP,"not able to find pipeline "+Smoke_HSRPipeline2Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc039_ChangeStageToClosed_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		totalDealsshown=2;dealQualityScore=closedScore;averageDealQualityScore=(parkedScore+closedScore)/totalDealsshown;
		
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={Smoke_HSRINS1Name,Smoke_HSRContact1FName+" "+Smoke_HSRContact1LName};
		int j=0;
		WebElement ele;
		for (TabName tab:tabName) {
		if (lp.clickOnTab(projectName, tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
				
				for (int i =0;i<labelName1.length;i++) {
					if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
						log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);
						
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+records[j]);
				log(LogStatus.SKIP,"Not Able to click "+records[j],YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on "+tab);
			log(LogStatus.SKIP,"not able to click on "+tab,YesNo.Yes);
		}
		j++;
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	
	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc040_CreatePiplelineWithDifferentSourceFirm_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String pipe,company,stage,sf,sc;
		for (int i=0;i<1;i++) {
			if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object4Tab,YesNo.No);	
				pipe= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP3", excelLabel.Deal_Name);
				company= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP3", excelLabel.Company_Name);
				sf= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP3", excelLabel.Source_Firm);
				sc= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP3", excelLabel.Source_Contact);
				stage= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP3", excelLabel.Stage);
				String[][] otherLabels = {{excelLabel.Source_Contact.toString(),sc},{excelLabel.Source_Firm.toString(),sf}};
				refresh(driver);
				ThreadSleep(3000);
				if (fp.createDeal(projectName,"",pipe, company, stage,otherLabels, 15)) {
					log(LogStatus.INFO,"Created Deal : "+pipe,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Deal  : "+pipe);
					log(LogStatus.SKIP,"Not Able to Create Deal  : "+pipe,YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to click on deal tab");
				log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
			}
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc040_CreatePiplelineWithDifferentSourceFirm_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		totalDealsshown=3;dealQualityScore=loiScore;averageDealQualityScore=(parkedScore+closedScore+loiScore)/totalDealsshown;
		String dealsForInst="1";
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Smoke_HSRPipeline3Stage,Smoke_HSRPipeline3Stage,String.valueOf(dealQualityScore)};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		String labelValues1[]={String.valueOf(averageDealQualityScore),dealsForInst};
		String labelValues2[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};

		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, Smoke_HSRPipeline3Name, 10)){
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
							log(LogStatus.SKIP,"successfully verified "+labelName[i],YesNo.No);
							
						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}

					}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+Smoke_HSRPipeline1Name);
				log(LogStatus.SKIP,"Not Able to click "+Smoke_HSRPipeline1Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={Smoke_HSRINS2Name,Smoke_HSRContact1FName+" "+Smoke_HSRContact1LName};
		int j=0;
		String temp[];
		for (TabName tab:tabName) {
			if (lp.clickOnTab(projectName, tab)) {
				if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
						if (j==0)
							temp=labelValues1;
						else
							temp=labelValues2;
						for (int i =0;i<labelName1.length;i++) {
							if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],temp[i])) {
								log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);
								
							}else {
								sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
								log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
							}

						}
					}else {
						sa.assertTrue(false,"not able to click on details tab");
						log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"Not Able to click "+records[j]);
					log(LogStatus.SKIP,"Not Able to click "+records[j],YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to click on deal tab");
				log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
			}
			j++;
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc041_DeletePipeline_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, Smoke_HSRPipeline1Name, 10)) {
				cp.clickOnShowMoreDropdownOnly(projectName, PageName.Object4Page);
				log(LogStatus.INFO,"Able to Click on Show more Icon : "+TabName.Object4Tab+" For : "+Smoke_HSRPipeline1Name,YesNo.No);
				ThreadSleep(500);
				WebElement ele = cp.actionDropdownElement(projectName, PageName.Object4Page, ShowMoreActionDropDownList.Delete, 15);
				 if (ele==null) {
					 ele =cp.getDeleteButton(projectName, 30);
				} 
				
				 if (click(driver, ele, "Delete More Icon", action.BOOLEAN)) {
					log(LogStatus.INFO,"Able to Click on Delete more Icon : "+TabName.Object4Tab+" For : "+Smoke_HSRPipeline1Name,YesNo.No);
					ThreadSleep(1000);
					if (click(driver, cp.getDeleteButtonOnDeletePopUp(projectName, 30), "Delete Button", action.BOOLEAN)) {
						log(LogStatus.INFO,"Able to Click on Delete button on Delete PoPup : "+TabName.Object4Tab+" For : "+Smoke_HSRPipeline1Name,YesNo.No);
						ThreadSleep(10000);
						if (cp.clickOnTab(projectName, TabName.Object4Tab)) {
							log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object4Tab+" For : "+Smoke_HSRPipeline1Name,YesNo.No);
							ThreadSleep(1000);
							if (!cp.clickOnAlreadyCreatedItem(projectName, TabName.Object4Tab, Smoke_HSRPipeline1Name, 10)) {
								log(LogStatus.INFO,"Item has been Deleted after delete operation  : "+Smoke_HSRPipeline1Name+" For : "+TabName.Object4Tab,YesNo.No);

							}else {
								sa.assertTrue(false,"Item has not been Deleted after delete operation  : "+Smoke_HSRPipeline1Name+" For : "+TabName.Object4Tab);
								log(LogStatus.SKIP,"Item has not been Deleted after delete operation  : "+Smoke_HSRPipeline1Name+" For : "+TabName.Object4Tab,YesNo.Yes);
							}

						}else {
							sa.assertTrue(false,"Not Able to Click on Tab after delete : "+TabName.Object4Tab+" For : "+Smoke_HSRPipeline1Name);
							log(LogStatus.SKIP,"Not Able to Click on Tab after delete : "+TabName.Object4Tab+" For : "+Smoke_HSRPipeline1Name,YesNo.Yes);	
						}
					}else {
						log(LogStatus.INFO,"not able to click on delete button, so not deleted : "+TabName.Object4Tab+" For : "+Smoke_HSRPipeline1Name,YesNo.No);
						sa.assertTrue(false,"not able to click on delete button, so not deleted : "+TabName.Object4Tab+" For : "+Smoke_HSRPipeline1Name);
					}
				 }else {
					 log(LogStatus.INFO,"not Able to Click on Delete more Icon : "+TabName.Object4Tab+" For : "+Smoke_HSRPipeline1Name,YesNo.No);
					 sa.assertTrue(false,"not Able to Click on Delete more Icon : "+TabName.Object4Tab+" For : "+Smoke_HSRPipeline1Name);
				 }
			}else {
				 log(LogStatus.INFO,"not Able to Click on "+Smoke_HSRPipeline1Name,YesNo.No);
				 sa.assertTrue(false,"not Able to Click on "+Smoke_HSRPipeline1Name);
			 }
		}else {
			 log(LogStatus.INFO,"not Able to Click on "+TabName.Object4Tab,YesNo.No);
			 sa.assertTrue(false,"not Able to Click on "+TabName.Object4Tab);
		 }
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc041_DeletePipeline_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		totalDealsshown=2;dealQualityScore=closedScore;averageDealQualityScore=(parkedScore+closedScore)/totalDealsshown;
		
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		String labelValues2[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		String labelValues1[]={String.valueOf(averageDealQualityScore),"1"};
		
		String temp[];
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={Smoke_HSRINS1Name,Smoke_HSRContact1FName+" "+Smoke_HSRContact1LName};
		int j=0;
		WebElement ele;
		for (TabName tab:tabName) {
		if (lp.clickOnTab(projectName, tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
				if (j==0)
					temp=labelValues1;
				else
					temp=labelValues2;
				for (int i =0;i<labelName1.length;i++) {
					if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],temp[i])) {
						log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);
						
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+records[j]);
				log(LogStatus.SKIP,"Not Able to click "+records[j],YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on "+tab);
			log(LogStatus.SKIP,"not able to click on "+tab,YesNo.Yes);
		}
		j++;
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc042_RestorePipeline_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		WebElement ele = null ;
	
		
		TabName tabName = TabName.RecycleBinTab;
		String name = Smoke_HSRPipeline1Name;
		
			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
				ThreadSleep(1000);
				cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 20);
					log(LogStatus.INFO,"Clicked on  : "+name+" For : "+tabName,YesNo.No);
					ThreadSleep(2000);
					
					 ele=cp.getCheckboxOfRestoreItemOnRecycleBin(projectName, name, 10);
					 if (clickUsingJavaScript(driver, ele, "Check box against : "+name, action.BOOLEAN)) {
						 log(LogStatus.INFO,"Click on checkbox for "+name,YesNo.No);
						 
						 ThreadSleep(1000);
						 ele=cp.getRestoreButtonOnRecycleBin(projectName, 10);
						 if (clickUsingJavaScript(driver, ele, "Restore Button : "+name, action.BOOLEAN)) {
							 log(LogStatus.INFO,"Click on Restore Button for "+name,YesNo.No);
							 ThreadSleep(1000);
						} else {
							sa.assertTrue(false,"Not Able to Click on Restore Button for "+name);
							log(LogStatus.SKIP,"Not Able to Click on Restore Button for "+name,YesNo.Yes);
						}
						 
					} else {
						sa.assertTrue(false,"Not Able to Click on checkbox for "+name);
						log(LogStatus.SKIP,"Not Able to Click on checkbox for "+name,YesNo.Yes);
					}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
			}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc042_RestorePipeline_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		totalDealsshown=3;dealQualityScore=closedScore;averageDealQualityScore=(parkedScore+loiScore+closedScore)/totalDealsshown;
		String instDealCOunt="2";
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		String labelValues2[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		String labelValues1[]={String.valueOf(averageDealQualityScore),instDealCOunt};
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.Due_Diligence.toString(),Stage.Parked.toString(),String.valueOf(dealQualityScore)};
		
		String temp[];
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={Smoke_HSRINS1Name,Smoke_HSRContact1FName+" "+Smoke_HSRContact1LName};
		int j=0;
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, Smoke_HSRPipeline1Name, 10)){
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
							log(LogStatus.SKIP,"successfully verified "+labelName[i],YesNo.No);
							
						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}

					}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+Smoke_HSRPipeline1Name);
				log(LogStatus.SKIP,"Not Able to click "+Smoke_HSRPipeline1Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		for (TabName tab:tabName) {
			if (lp.clickOnTab(projectName, tab)) {
				if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
						if (j==0)
							temp=labelValues1;
						else
							temp=labelValues2;
						for (int i =0;i<labelName1.length;i++) {
							if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],temp[i])) {
								log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);

							}else {
								sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
								log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
							}
						}
					}else {
						sa.assertTrue(false,"not able to click on details tab");
						log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"Not Able to click "+records[j]);
					log(LogStatus.SKIP,"Not Able to click "+records[j],YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to click on "+tab);
				log(LogStatus.SKIP,"not able to click on "+tab,YesNo.Yes);
			}
			j++;
		}

		lp.CRMlogout();
		sa.assertAll();
	}
	

	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc043_UpdateStageNames(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		String parentID=null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String[][] newAndOldStage= {{Stage.NDA_Signed.toString(),Stage.NonDisclosureAgreement.toString()},
				{Stage.IOI.toString(),Stage.IndicationOfInterest.toString()}};
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject(environment, mode,object.Deal )) {
					if(sp.clickOnObjectFeature(environment, mode,object.Deal, ObjectFeatureName.FieldAndRelationShip)) {
						if (sendKeys(driver, sp.getsearchTextboxFieldsAndRelationships(10), excelLabel.Stage.toString()+Keys.ENTER, "status", action.BOOLEAN)) {

							if (sp.clickOnAlreadyCreatedLayout(excelLabel.Stage.toString())) {
								for (int i = 0;i<newAndOldStage.length;i++) {
									switchToDefaultContent(driver);
									switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
									WebElement ele=sp.clickOnEditInFrontOfFieldValues(projectName, newAndOldStage[i][0]);
									if (click(driver, ele, "watchlist", action.BOOLEAN)) {
										switchToDefaultContent(driver);
										ThreadSleep(3000);
										switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
										sendKeys(driver, sp.getFieldLabelTextBox1(10), newAndOldStage[i][1], "label", action.BOOLEAN);
											

										if (click(driver, fp.getCustomTabSaveBtn(10), "save", action.BOOLEAN)) {

											log(LogStatus.INFO, "successfully changed watchlist label", YesNo.No);
										}else {
											sa.assertTrue(false,"not able to click on save button");
											log(LogStatus.SKIP,"not able to click on save button",YesNo.Yes);
										}

									}else {
										sa.assertTrue(false,"edit button is not clickable");
										log(LogStatus.SKIP,"edit button is not clickable",YesNo.Yes);
									}
								}
								ThreadSleep(3000);
								switchToDefaultContent(driver);
								switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
								WebElement ele=sp.clickOnEditInFrontOfFieldValues(projectName, newAndOldStage[0][1]);
								WebElement ele1=null;
								ele1=sp.clickOnEditInFrontOfFieldValues(projectName, newAndOldStage[1][1]);
								if ((ele!=null)&&(ele1!=null)) {
									log(LogStatus.INFO,"successfully verified rename of stage values",YesNo.No);	
									
								}else {
									log(LogStatus.ERROR,"stage field is not renamed",YesNo.No);	
									sa.assertTrue(false,"stage field is not renamed" );
								}
							}else {
								sa.assertTrue(false,"stage field is not clickable");
								log(LogStatus.SKIP,"stage field is not clickable",YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"field textbox is not visible");
							log(LogStatus.SKIP,"field textbox is not visible",YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"field and relationships is not clickable");
						log(LogStatus.SKIP,"field and relationships is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"activity object is not clickable");
					log(LogStatus.SKIP,"activity object is not clickable",YesNo.Yes);
				}
				ThreadSleep(3000);
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				sa.assertTrue(false,"new window is not found, so cannot change watchlist label");
				log(LogStatus.SKIP,"new window is not found, so cannot change watchlist label",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"setup link is not clickable");
			log(LogStatus.SKIP,"setup link is not clickable",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc044_CreatePiplelineWithNewStageValues_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String pipe,company,stage,sf,sc;
			if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object4Tab,YesNo.No);	
				pipe= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP4", excelLabel.Deal_Name);
				company= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP4", excelLabel.Company_Name);
				sf= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP4", excelLabel.Source_Firm);
				sc= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP4", excelLabel.Source_Contact);
				stage= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP4", excelLabel.Stage);
				String[][] otherLabels = {{excelLabel.Source_Contact.toString(),sc},{excelLabel.Source_Firm.toString(),sf}};
				refresh(driver);
				ThreadSleep(3000);
				if (fp.createDeal(projectName,"",pipe, company, stage,otherLabels, 15)) {
					log(LogStatus.INFO,"Created Deal : "+pipe,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Deal  : "+pipe);
					log(LogStatus.SKIP,"Not Able to Create Deal  : "+pipe,YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to click on deal tab");
				log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
			}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc044_CreatePiplelineWithNewStageValues_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		totalDealsshown=2;dealQualityScore=NDASignedScore;averageDealQualityScore=(NDASignedScore+loiScore)/totalDealsshown;
		
		int totalDealsForContact=1;
		double avgscore=NDASignedScore/totalDealsForContact;
		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.NDA_Signed.toString(),Smoke_HSRPipeline4Stage,String.valueOf(dealQualityScore)};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		String labelValues2[]={String.valueOf(avgscore),String.valueOf(totalDealsForContact)};

		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, Smoke_HSRPipeline4Name, 10)){
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
							log(LogStatus.SKIP,"successfully verified "+labelName[i],YesNo.No);
							
						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}

					}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+Smoke_HSRPipeline1Name);
				log(LogStatus.SKIP,"Not Able to click "+Smoke_HSRPipeline1Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={Smoke_HSRINS2Name,Smoke_HSRContact2FName+" "+Smoke_HSRContact2LName};
		int j=0;
		String temp[];
		for (TabName tab:tabName) {
			if (lp.clickOnTab(projectName, tab)) {
				if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
						if (j==0)
							temp=labelValues1;
						else
							temp=labelValues2;
						for (int i =0;i<labelName1.length;i++) {
							if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],temp[i])) {
								log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);
								
							}else {
								sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
								log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
							}

						}
					}else {
						sa.assertTrue(false,"not able to click on details tab");
						log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"Not Able to click "+records[j]);
					log(LogStatus.SKIP,"Not Able to click "+records[j],YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to click on deal tab");
				log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
			}
			j++;
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc045_CreateNewPipleline_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String pipe,company,stage,sf,sc;
			if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object4Tab,YesNo.No);	
				pipe= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP5", excelLabel.Deal_Name);
				company= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP5", excelLabel.Company_Name);
				sf= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP5", excelLabel.Source_Firm);
				sc= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP5", excelLabel.Source_Contact);
				stage= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP5", excelLabel.Stage);
				String[][] otherLabels = {{excelLabel.Source_Contact.toString(),sc},{excelLabel.Source_Firm.toString(),sf}};
				refresh(driver);
				ThreadSleep(3000);
				if (fp.createDeal(projectName,"",pipe, company, stage,otherLabels, 15)) {
					log(LogStatus.INFO,"Created Deal : "+pipe,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Deal  : "+pipe);
					log(LogStatus.SKIP,"Not Able to Create Deal  : "+pipe,YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to click on deal tab");
				log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
			}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc045_CreateNewPipleline_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		totalDealsshown=1;dealQualityScore=loiScore;averageDealQualityScore=loiScore/totalDealsshown;
		
		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Smoke_HSRPipeline5Stage,Smoke_HSRPipeline5Stage,String.valueOf(dealQualityScore)};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		String labelValues2[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, Smoke_HSRPipeline5Name, 10)){
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
							log(LogStatus.SKIP,"successfully verified "+labelName[i],YesNo.No);
							
						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}

					}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+Smoke_HSRPipeline1Name);
				log(LogStatus.SKIP,"Not Able to click "+Smoke_HSRPipeline1Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={Smoke_HSRINS5Name,Smoke_HSRContact3FName+" "+Smoke_HSRContact3LName};
		int j=0;
		String temp[];
		for (TabName tab:tabName) {
			if (lp.clickOnTab(projectName, tab)) {
				if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
						for (int i =0;i<labelName1.length;i++) {
							if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
								log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);
								
							}else {
								sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
								log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
							}

						}
					}else {
						sa.assertTrue(false,"not able to click on details tab");
						log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"Not Able to click "+records[j]);
					log(LogStatus.SKIP,"Not Able to click "+records[j],YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to click on deal tab");
				log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
			}
			j++;
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	
	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc046_1_VerifyConvertToPortfolio(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		String parentID=null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
			List<String> layoutName = new ArrayList<String>();
			layoutName.add("Pipeline Layout");
			HashMap<String, String> sourceANDDestination = new HashMap<String, String>();
			sourceANDDestination.put("Mobile & Lightning<break>"+PageLabel.Convert_to_Portfolio.toString(), PageLabel.New_Task.toString());
			List<String> abc = sp.DragNDrop("", mode, object.Deal, ObjectFeatureName.pageLayouts, layoutName, sourceANDDestination);
			ThreadSleep(10000);
			if (!abc.isEmpty()) {
				log(LogStatus.FAIL, "field not added/already present 1", YesNo.Yes);
				sa.assertTrue(false, "field not added/already present 1");
			}else{
				log(LogStatus.INFO, "field added/already present 1", YesNo.Yes);
			}
			driver.close();
			driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch, so cannot add field", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch, so cannot add field");
			}
		}
		else {
			log(LogStatus.FAIL, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc046_2_VerifyConvertToPortfolio(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		String parentID=null;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (dp.clickOnAlreadyCreatedItem(projectName, Smoke_HSRPipeline5Name, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10), "convert to portfolio button", action.BOOLEAN)) {
					if (dp.getconvertToPortfolioMessage(Smoke_HSRINS3Name,10)!=null) {
						log(LogStatus.INFO, "successfully verified convert to portfolio text message", YesNo.No);
					}else {
						sa.assertTrue(false,"could not verify convert to portfolio text message");
						log(LogStatus.SKIP,"could not verify convert to portfolio text message",YesNo.Yes);
					}
					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						log(LogStatus.INFO, "successfully clicked next button", YesNo.No);
						if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "successfully verified finish button after convert to portfolio", YesNo.No);
							ExcelUtils.writeData(phase1DataSheetFilePath, Stage.Closed.toString(), "Deal", excelLabel.Variable_Name, "HSRPIP5",excelLabel.Updated_Stage);
						}else {
							sa.assertTrue(false,"could not verify convert to portfolio as finish button not clicked");
							log(LogStatus.SKIP,"could not verify convert to portfolio as finish button not clicked",YesNo.Yes);
						}

					}else {
						sa.assertTrue(false,"not able to click on next button");
						log(LogStatus.SKIP,"not able to click on next button",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"not able to click on convert to portfolio button");
					log(LogStatus.SKIP,"not able to click on convert to portfolio button",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to click on "+Smoke_HSRPipeline5Name);
				log(LogStatus.SKIP,"not able to click on "+Smoke_HSRPipeline5Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
		
	
	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc047_ImpactConvertToPortfolio(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		String parentID=null;
		dealQualityScore=closedScore;totalDealsshown=1;averageDealQualityScore=dealQualityScore/totalDealsshown;
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.Closed.toString(),Stage.Closed.toString(),String.valueOf(dealQualityScore)};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		String labelValues2[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		ExcelUtils.writeData(phase1DataSheetFilePath, Stage.Closed.toString(), "Deal", excelLabel.Variable_Name, "HSRPIP5",excelLabel.Stage);
		WebElement ele;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (dp.clickOnAlreadyCreatedItem(projectName, Smoke_HSRPipeline5Name, 10)) {
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
							log(LogStatus.SKIP,"successfully verified "+labelName[i],YesNo.No);
							
						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}

					}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+Smoke_HSRPipeline1Name);
				log(LogStatus.SKIP,"Not Able to click "+Smoke_HSRPipeline1Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={Smoke_HSRINS5Name,Smoke_HSRContact3FName+" "+Smoke_HSRContact3LName};
		int j=0;
		String temp[];
		for (TabName tab:tabName) {
			if (lp.clickOnTab(projectName, tab)) {
				if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
						for (int i =0;i<labelName1.length;i++) {
							if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
								log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);
								
							}else {
								sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
								log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
							}

						}
					}else {
						sa.assertTrue(false,"not able to click on details tab");
						log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"Not Able to click "+records[j]);
					log(LogStatus.SKIP,"Not Able to click "+records[j],YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to click on tab "+tab);
				log(LogStatus.SKIP,"not able to click on tab "+tab,YesNo.Yes);
			}
			j++;
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc048_CreateDealAssociatedWithInstitution_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword,appName);
		String pipe,company,stage,sf,sc;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object4Tab,YesNo.No);	
			pipe= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP6", excelLabel.Deal_Name);
			company= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP6", excelLabel.Company_Name);
			sf= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP6", excelLabel.Source_Firm);
			sc= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP6", excelLabel.Source_Contact);
			stage= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "HSRPIP6", excelLabel.Stage);
			String[][] otherLabels = {{excelLabel.Source_Contact.toString(),sc},{excelLabel.Source_Firm.toString(),sf}};
			refresh(driver);
			ThreadSleep(3000);
			if (fp.createDeal(projectName,"",pipe, company, stage,otherLabels, 15)) {
				log(LogStatus.INFO,"Created Deal : "+pipe,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Deal  : "+pipe);
				log(LogStatus.SKIP,"Not Able to Create Deal  : "+pipe,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc048_CreateDealAssociatedWithInstitution_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		dealQualityScore=loiScore;totalDealsshown=2;averageDealQualityScore=(dealQualityScore+closedScore)/totalDealsshown;
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Smoke_HSRPipeline6Stage,Smoke_HSRPipeline6Stage,String.valueOf(dealQualityScore)};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		String labelValues2[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		WebElement ele;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (dp.clickOnAlreadyCreatedItem(projectName, Smoke_HSRPipeline6Name, 10)) {
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
							log(LogStatus.SKIP,"successfully verified "+labelName[i],YesNo.No);
							
						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}

					}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+Smoke_HSRPipeline1Name);
				log(LogStatus.SKIP,"Not Able to click "+Smoke_HSRPipeline1Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={Smoke_HSRINS4Name,Smoke_HSRContact3FName+" "+Smoke_HSRContact3LName};
		int j=0;
		String temp[];
		for (TabName tab:tabName) {
			if (lp.clickOnTab(projectName, tab)) {
				if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
						if (j==1) {
							for (int i =0;i<labelName1.length;i++) {
								if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
									log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);

								}else {
									sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
									log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
								}

							}
						}
						else {
							for (int i =0;i<labelName1.length;i++) {
								if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {

									sa.assertTrue(false,"Following value present but should not be "+labelName1[i]);
									log(LogStatus.SKIP,"Following value present but should not be "+labelName1[i],YesNo.Yes);
								}else {
									log(LogStatus.SKIP,"successfully verified absence of "+labelName1[i],YesNo.No);
								}

							}
						}
					}else {
						sa.assertTrue(false,"not able to click on details tab");
						log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"Not Able to click "+records[j]);
					log(LogStatus.SKIP,"Not Able to click "+records[j],YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to click on tab "+tab);
				log(LogStatus.SKIP,"not able to click on tab "+tab,YesNo.Yes);
			}
			j++;
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2HighestStageReachedtc049_PostCondition(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		String parentID=null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String[][] newAndOldStage= {{Stage.NonDisclosureAgreement.toString(),Stage.NDA_Signed.toString().replace("_", " ")},
				{Stage.IndicationOfInterest.toString(),Stage.IOI.toString()}};
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject(environment, mode,object.Deal )) {
					if(sp.clickOnObjectFeature(environment, mode,object.Deal, ObjectFeatureName.FieldAndRelationShip)) {
						if (sendKeys(driver, sp.getsearchTextboxFieldsAndRelationships(10), excelLabel.Stage.toString()+Keys.ENTER, "status", action.BOOLEAN)) {

							if (sp.clickOnAlreadyCreatedLayout(excelLabel.Stage.toString())) {
								for (int i = 0;i<newAndOldStage.length;i++) {
									switchToDefaultContent(driver);
									switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
									WebElement ele=sp.clickOnEditInFrontOfFieldValues(projectName, newAndOldStage[i][0]);
									if (click(driver, ele, "watchlist", action.BOOLEAN)) {
										switchToDefaultContent(driver);
										ThreadSleep(3000);
										switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
										sendKeys(driver, sp.getFieldLabelTextBox1(10), newAndOldStage[i][1], "label", action.BOOLEAN);
											

										if (click(driver, fp.getCustomTabSaveBtn(10), "save", action.BOOLEAN)) {

											log(LogStatus.INFO, "successfully changed watchlist label", YesNo.No);
										}else {
											sa.assertTrue(false,"not able to click on save button");
											log(LogStatus.SKIP,"not able to click on save button",YesNo.Yes);
										}

									}else {
										sa.assertTrue(false,"edit button is not clickable");
										log(LogStatus.SKIP,"edit button is not clickable",YesNo.Yes);
									}
								}
								ThreadSleep(5000);
								switchToDefaultContent(driver);
								switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
								WebElement ele=sp.clickOnEditInFrontOfFieldValues(projectName, newAndOldStage[0][1]);
								WebElement ele1=null;
								ele1=sp.clickOnEditInFrontOfFieldValues(projectName, newAndOldStage[1][1]);
								if ((ele!=null)&&(ele1!=null)) {
									log(LogStatus.INFO,"successfully verified rename of stage values",YesNo.No);	
									
								}else {
									log(LogStatus.ERROR,"stage field is not renamed",YesNo.No);	
									sa.assertTrue(false,"stage field is not renamed" );
								}
							}else {
								sa.assertTrue(false,"stage field is not clickable");
								log(LogStatus.SKIP,"stage field is not clickable",YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"field textbox is not visible");
							log(LogStatus.SKIP,"field textbox is not visible",YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"field and relationships is not clickable");
						log(LogStatus.SKIP,"field and relationships is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"activity object is not clickable");
					log(LogStatus.SKIP,"activity object is not clickable",YesNo.Yes);
				}
				ThreadSleep(3000);
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				sa.assertTrue(false,"new window is not found, so cannot change watchlist label");
				log(LogStatus.SKIP,"new window is not found, so cannot change watchlist label",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"setup link is not clickable");
			log(LogStatus.SKIP,"setup link is not clickable",YesNo.Yes);
		}
		
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject(environment, mode,object.Activity )) {
					if(sp.clickOnObjectFeature(environment, mode,object.Activity, ObjectFeatureName.FieldAndRelationShip)) {
						if (sp.clickOnAlreadyCreatedLayout(PageLabel.Watch_list.toString().replace("_", " "))) {
							switchToFrame(driver, 10, sp.getFrame(PageName.ActivityLayoutPage, 10));
							if (click(driver, ip.getEditButton(environment,  Mode.Classic.toString(),10), "edit classic", action.BOOLEAN)) {
								switchToDefaultContent(driver);
								switchToFrame(driver, 10, sp.getFrame(PageName.ActivityLayoutPage, 10));
								sp.getFieldLabelTextBox(10).sendKeys(PageLabel.Watchlist.toString());
								
								
								if (click(driver, fp.getCustomTabSaveBtn(10), "save", action.BOOLEAN)) {
											switchToAlertAndAcceptOrDecline(driver, 10, action.ACCEPT);
											click(driver, fp.getCustomTabSaveBtn(10), "save", action.BOOLEAN);
												
											log(LogStatus.INFO, "successfully changed watchlist label", YesNo.No);
									}else {
										sa.assertTrue(false,"not able to click on save button");
										log(LogStatus.SKIP,"not able to click on save button",YesNo.Yes);
									}
								
								
							}else {
								sa.assertTrue(false,"edit button is not clickable");
								log(LogStatus.SKIP,"edit button is not clickable",YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"watchlist layout is not clickable");
							log(LogStatus.SKIP,"watchlist layout is not clickable",YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"field and relationships is not clickable");
						log(LogStatus.SKIP,"field and relationships is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"activity object is not clickable");
					log(LogStatus.SKIP,"activity object is not clickable",YesNo.Yes);
				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				sa.assertTrue(false,"new window is not found, so cannot change watchlist label");
				log(LogStatus.SKIP,"new window is not found, so cannot change watchlist label",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"setup link is not clickable");
			log(LogStatus.SKIP,"setup link is not clickable",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
}

