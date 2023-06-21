package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.SmokeCommonVariables.todaysDate;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.SoftAssert;
import com.navatar.generic.EnumConstants.*;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.BasePageErrorMessage;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.DealPageBusinessLayer;
import com.navatar.pageObjects.FundRaisingPageBusinessLayer;
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
	
	// Highest Stage Reached
	
	@Parameters({ "projectName"})
	@Test
	public void M2Tc001_createCRMUser(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(crmUser1LastName);
		String UserLastName = splitedUserLastName[0] + lp.generateRandomNumber();
		String emailId = lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User1",excelLabel.User_Last_Name);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		boolean flag = false;
		for (int i = 0; i < 3; i++) {
			try {
				if (home.clickOnSetUpLink()) {
					flag = true;
					parentWindow = switchOnWindow(driver);
					if (parentWindow == null) {
						sa.assertTrue(false,
								"No new window is open after click on setup link in lighting mode so cannot create CRM User1");
						log(LogStatus.SKIP,
								"No new window is open after click on setup link in lighting mode so cannot create CRM User1",
								YesNo.Yes);
						exit("No new window is open after click on setup link in lighting mode so cannot create CRM User1");
					}
					if (setup.createPEUser( crmUser1FirstName, UserLastName, emailId, crmUserLience,
							crmUserProfile, null)) {
						log(LogStatus.INFO, "CRM User is created Successfully: " + crmUser1FirstName + " " + UserLastName, YesNo.No);
						ExcelUtils.writeData(testCasesFilePath, emailId, "Users", excelLabel.Variable_Name, "User1",
								excelLabel.User_Email);
						ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User1",
								excelLabel.User_Last_Name);
						flag = true;
						break;

					}
					driver.close();
					driver.switchTo().window(parentWindow);

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
			}

		}
		if (flag) {
			if(!environment.equalsIgnoreCase(Environment.Sandbox.toString())) {
				if (setup.installedPackages(crmUser1FirstName, UserLastName)) {
					appLog.info("PE Package is installed Successfully in CRM User: " + crmUser1FirstName + " "
							+ UserLastName);

				} else {
					appLog.error(
							"Not able to install PE package in CRM User1: " + crmUser1FirstName + " " + UserLastName);
					sa.assertTrue(false,
							"Not able to install PE package in CRM User1: " + crmUser1FirstName + " " + UserLastName);
					log(LogStatus.ERROR,
							"Not able to install PE package in CRM User1: " + crmUser1FirstName + " " + UserLastName,
							YesNo.Yes);
				}
			}
			

		}else{

			log(LogStatus.ERROR, "could not click on setup link, test case fail", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link, test case fail");

		}

		lp.CRMlogout();
		closeBrowser();
		config(ExcelUtils.readDataFromPropertyFile("Browser"));
		lp = new LoginPageBusinessLayer(driver);
		String passwordResetLink=null;
		try {
			passwordResetLink = new EmailLib().getResetPasswordLink("passwordreset",
					ExcelUtils.readDataFromPropertyFile("gmailUserName"),
					ExcelUtils.readDataFromPropertyFile("gmailPassword"));
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		appLog.info("ResetLinkIs: " + passwordResetLink);
		driver.get(passwordResetLink);
		if (lp.setNewPassword()) {
			appLog.info("Password is set successfully for CRM User1: " + crmUser1FirstName + " " + UserLastName );
		} else {
			appLog.info("Password is not set for CRM User1: " + crmUser1FirstName + " " + UserLastName);
			sa.assertTrue(false, "Password is not set for CRM User1: " + crmUser1FirstName + " " + UserLastName);
			log(LogStatus.ERROR, "Password is not set for CRM User1: " + crmUser1FirstName + " " + UserLastName,
					YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2Tc002_AddNavigationTab(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		// Fund 
		String addRemoveTabName="";
		String tab1="";
		if (tabObj1.equalsIgnoreCase("Entity")){
			tab1="Entitie";
		}
		else{
			tab1=tabObj1;
		}
		addRemoveTabName=tab1+"s,"+tabObj2+"s,"+tabObj3+"s,"+tabObj4+"s,tab,"+"Fundraisings,"+"Partnerships"+","+"Tasks";
		if (lp.addTab_Lighting( addRemoveTabName, 5)) {
			log(LogStatus.INFO,"Tab added : "+addRemoveTabName,YesNo.No);
		} else {
			log(LogStatus.FAIL,"Tab not added : "+addRemoveTabName,YesNo.No);
			sa.assertTrue(false, "Tab not added : "+addRemoveTabName);
		}
		
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc002_CreatePreconditionData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String[][] entitys = {{Smoke_TWINS1Name, Smoke_TWINS1RecordType},{Smoke_TWINS2Name, Smoke_TWINS2RecordType},
				{Smoke_TWINS3Name, Smoke_TWINS3RecordType},{Smoke_TWINS4Name, Smoke_TWINS4RecordType}};
	
		String[][][] labelValue = {{{excelLabel.Status.toString(),Smoke_TWINS1Status}},{{excelLabel.Status.toString(),Smoke_TWINS2Status}},
				{{excelLabel.Status.toString(),Smoke_TWINS3Status}},{{excelLabel.Status.toString(),Smoke_TWINS4Status}}};

		String mailID = "";
		ThreadSleep(5000);
		for(int i=0;i<entitys.length;i++) {
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
				
				if (ip.createEntityOrAccount(projectName, mode, entitys[i][0], entitys[i][1], null, labelValue[i], 20)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+entitys[i][0]+" of record type : "+entitys[i][1],YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+entitys[i][0]+" of record type : "+entitys[i][1]);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+entitys[i][0]+" of record type : "+entitys[i][1],YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
			}
			
		}
		String[][] contacts = {{Smoke_TWContact1FName, Smoke_TWContact1LName,Smoke_TWContact1Ins},{Smoke_TWContact2FName, Smoke_TWContact2LName,Smoke_TWContact2Ins},
				{Smoke_TWContact3FName, Smoke_TWContact3LName,Smoke_TWContact3Ins},{Smoke_TWContact4FName, Smoke_TWContact4LName,Smoke_TWContact4Ins},{Smoke_TWContact5FName, Smoke_TWContact5LName,Smoke_TWContact5Ins}};
	
		
			ThreadSleep(5000);
			for(int i=0;i<contacts.length;i++) {
			if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

				mailID = lp.generateRandomEmailId(gmailUserName);
				
				if (cp.createContact(projectName, contacts[i][0], contacts[i][1], contacts[i][2], mailID, "",
						null, null, CreationPage.ContactPage, null, null)) {
					log(LogStatus.INFO, "successfully Created Contact : " + contacts[i][0] + " " + contacts[i][1],
							YesNo.No);
				} else {
					sa.assertTrue(false, "Not Able to Create Contact : " + contacts[i][0] + " " + contacts[i][1]);
					log(LogStatus.SKIP, "Not Able to Create Contact: " + contacts[i][0] + " " + contacts[i][1],
							YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object2Tab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object2Tab, YesNo.Yes);
			}
			}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
		
	@Parameters({ "projectName"})
	@Test
	public void M2tc003_AddWatchlistField(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
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
			sourceANDDestination.put(PageLabel.Watchlist.toString(), PageLabel.Subject.toString());
			List<String> abc = setup.DragNDrop("", mode, object.Task, ObjectFeatureName.pageLayouts, layoutName, sourceANDDestination);
			ThreadSleep(10000);
			if (!abc.isEmpty()) {
				log(LogStatus.FAIL, "field  added/already present 1", YesNo.Yes);
			}else{
				log(LogStatus.INFO, "field not added/already present 1", YesNo.Yes);

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
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TWINS2Name, 20)) {
//				WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Communications.toString(), 10);
//				click(driver, ele1, RelatedTab.Communications.toString(), action.BOOLEAN);
//				scrollThroughOutWindow(driver);
				ThreadSleep(3000);
				WebElement ele;
				String[][] basicsection= {{"Subject",TWTask1Subject},{"Related_To",Smoke_TWContact3FName+" "+Smoke_TWContact3LName}};
				String[][] advanceSection= {{"Priority","Normal"},{"Due Date",todaysDate}};
		        String[][] taskSection= {{"Subject","ABC"},{"Due Date Only","06/04/2020"},{"Status","In Progress"}};
//				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task , 10);
//				scrollDownThroughWebelement(driver, ele, "new task");
//				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task.toString(), action.BOOLEAN)) {
//				ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.Add , 10);
//				
//				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.Add.toString(), action.BOOLEAN)) {
//					log(LogStatus.ERROR,"able to click on add button", YesNo.Yes);
//				}else {
//					log(LogStatus.ERROR,"could not click on add button", YesNo.Yes);
//					sa.assertTrue(false,"could not click on add button" );
//				}
				
				//out of scope
//				scrollDownThroughWebelement(driver, ip.relatedAssociations(projectName).get(0), "related associatons");
//				if (clickUsingJavaScript(driver, ip.getrelatedAssociationsdropdownButton(projectName, 10), "dropdown button for related associations")) {
//					List<String> l=compareMultipleListContainsByTitle(driver, tabs, ip.listOfObjectsInRelatedAssctions(projectName));
//					if (l.isEmpty()) {
//						log(LogStatus.INFO, "successfully verified presence of all tabs "+tabs, YesNo.No);
//					}
//					else {
//						for (String a:l) {
//							log(LogStatus.ERROR,"not found "+a,  YesNo.Yes);
//							sa.assertTrue(false, "not found "+a);
//						}
//					}
//					l.clear();
//					l=compareMultipleListContainsByTitle(driver,ip.getTabName(projectName, TabName.Object2Tab).toString(), ip.listOfObjectsInRelatedAssctions(projectName));
//					if (l.isEmpty()) {
//						log(LogStatus.ERROR, "tab "+TabName.Object2Tab.toString()+" is present but should not be", YesNo.Yes);
//						sa.assertTrue(false, "tab "+TabName.Object2Tab.toString()+" is present but should not be");
//					}
//					else {
//						for (String a:l) {
//							log(LogStatus.INFO,"not found "+a+" as expected",  YesNo.Yes);
//
//						}
//					}
				
					//3
//					boolean flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object1Tab, Smoke_TWContact3FName+" "+Smoke_TWContact3LName, action.SCROLLANDBOOLEAN, 10);		
//					if (flag) {
//						log(LogStatus.SKIP,"Selected "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.No);
//
//					} else {
//						sa.assertTrue(false,"Not Able to Select "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name);
//						log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.Yes);
//
//					}
//					if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), TWTask1Subject, "Subject", action.SCROLLANDBOOLEAN)) {
//								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
//									log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
//								}
//								else {
//									log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
//									sa.assertTrue(false,"save button is not clickable so task not created" );
//								}
//					}
//					else {
//						log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
//						sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
//					}
		        
		        bp.createActivityTimeline(projectName,true,"Task", basicsection, advanceSection,null,null, false, null, null, null, null, null, null);
				System.err.println("donnnnnnnnnnneeeeeeeeeeeeeeeeeeeeeeeeeeeeee...................................................................");
				CommonLib.ThreadSleep(50000);
					
					refresh(driver);
					ThreadSleep(2000);
//					WebElement ele2 = bp.getRelatedTab(projectName, RelatedTab.Communications.toString(), 10);
//					click(driver, ele2, RelatedTab.Communications.toString(), action.BOOLEAN);
//					ThreadSleep(2000);
					ip.clickOnTab(projectName, TabName.TaskTab);
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.TaskPage,null, TWTask1Subject, SubjectElement.SubjectLink, 10);
					ThreadSleep(2000);
					if (click(driver, ele, "task name",action.SCROLLANDBOOLEAN)) {
						String[][] fieldsWithValues= {{PageLabel.Subject.toString(),TWTask1Subject},
								{PageLabel.Watchlist.toString(),Watchlist.True.toString()}};

						tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);

					}else {
						log(LogStatus.ERROR,"could not click on task on task page", YesNo.Yes);
						sa.assertTrue(false,"could not click on task on task page" );
					}
//				}else {
//					log(LogStatus.ERROR, "related association dropdown button is not clickable", YesNo.Yes);
//					sa.assertTrue(false,"related association dropdown button is not clickable" );
//				}
				}else {
					log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
					sa.assertTrue(false,"could not click on new task button" );
				}
			}else {
				log(LogStatus.ERROR, "could not find "+Smoke_TWINS2Name, YesNo.Yes);
				sa.assertTrue(false,"could not find "+Smoke_TWINS2Name );
			}
			
//		}else {
//			log(LogStatus.ERROR, "could not click on object 1 tab", YesNo.Yes);
//			sa.assertTrue(false,"could not click on object 1 tab" );
//		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc005_AddsdsWatchlistContactAndVerifyImpact (String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TWINS1Name, 20)) {
//				WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Communications.toString(), 10);
//				click(driver, ele1, RelatedTab.Communications.toString(), action.BOOLEAN);
				scrollThroughOutWindow(driver);
				ThreadSleep(3000);
				WebElement ele;
				String[][] basicsection= {{"Subject",TWTask2Subject},{"Related_To",Smoke_TWContact1FName+" "+Smoke_TWContact1LName}};
				String[][] advanceSection= {{"Priority","Normal"},{"Due Date",todaysDate}};
		        String[][] taskSection= {{"Subject","ABC"},{"Due Date Only","06/04/2020"},{"Status","In Progress"}};


/*
               bp.createActivityTimeline(projectName,true,"Task", basicsection, advanceSection,null,null, false, null, null, null, null, null, null);
				System.err.println("donnnnnnnnnnneeeeeeeeeeeeeeeeeeeeeeeeeeeeee...................................................................");
				CommonLib.ThreadSleep(50000);
					
					refresh(driver);
					ThreadSleep(2000);
	*/		/*		WebElement ele2 = bp.getRelatedTab(projectName, RelatedTab.Communications.toString(), 10);
					click(driver, ele2, RelatedTab.Communications.toString(), action.BOOLEAN);
					ThreadSleep(2000);
				*/
//				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task , 10);
//				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task.toString(), action.BOOLEAN)) {				
//				ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.Add , 10);
//				
//				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.Add.toString(), action.BOOLEAN)) {
//					log(LogStatus.ERROR,"able to click on add button", YesNo.Yes);
//				}else {
//					log(LogStatus.ERROR,"could not click on add button", YesNo.Yes);
//					sa.assertTrue(false,"could not click on add button" );
//				}

				
				//out of scope
//				if (clickUsingJavaScript(driver, ip.getrelatedAssociationsdropdownButton(projectName, 10), "dropdown button for related associations")) {
//					List<String> l=compareMultipleListContainsByTitle(driver, tabs, ip.listOfObjectsInRelatedAssctions(projectName));
//					if (l.isEmpty()) {
//						log(LogStatus.INFO, "successfully verified presence of all tabs "+tabs, YesNo.No);
//					}
//					else {
//						for (String a:l) {
//							log(LogStatus.ERROR,"not found "+a,  YesNo.Yes);
//							sa.assertTrue(false, "not found "+a);
//						}
//					}
//					l.clear();
//					l=compareMultipleListContainsByTitle(driver,ip.getTabName(projectName, TabName.Object2Tab).toString(), ip.listOfObjectsInRelatedAssctions(projectName));
//					if (l.isEmpty()) {
//						log(LogStatus.ERROR, "tab "+TabName.Object2Tab.toString()+" is present but should not be", YesNo.Yes);
//						sa.assertTrue(false, "tab "+TabName.Object2Tab.toString()+" is present but should not be");
//					}
//					else {
//						for (String a:l) {
//							log(LogStatus.INFO,"not found "+a+" as expected",  YesNo.Yes);
//
//						}
//					}
					//3
//					boolean flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object1Tab, Smoke_TWContact1FName+" "+Smoke_TWContact1LName, action.SCROLLANDBOOLEAN, 10);		
//					if (flag) {
//						log(LogStatus.SKIP,"Selected "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name,YesNo.No);
//
//					} else {
//						sa.assertTrue(false,"Not Able to Select "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name);
//						log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name,YesNo.Yes);
//
//					}
//
//					if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), TWTask2Subject, "Subject", action.SCROLLANDBOOLEAN)) {
//								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
//									log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
//								}
//								else {
//									log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
//									sa.assertTrue(false,"save button is not clickable so task not created" );
//								}
//					}
//					else {
//						log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
//						sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
//					}
//					ThreadSleep(2000);
//					refresh(driver);
//					ThreadSleep(2000);
					ip.clickOnTab(projectName, TabName.TaskTab);
					
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object3Page,ActivityType.Next, TWTask2Subject, SubjectElement.SubjectLink, 10);
					if (click(driver, ele, "task name",action.SCROLLANDBOOLEAN)) {
						String[][] fieldsWithValues= {{PageLabel.Subject.toString(),TWTask2Subject},
								{PageLabel.Watchlist.toString(),Watchlist.False.toString()}};

						tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);

					}else {
						log(LogStatus.ERROR,"could not click on task on task page", YesNo.Yes);
						sa.assertTrue(false,"could not click on task on task page" );
					}
//				}else {
//					log(LogStatus.ERROR, "related association dropdown button is not clickable", YesNo.Yes);
//					sa.assertTrue(false,"related association dropdown button is not clickable" );
//				}
				}else {
					log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
					sa.assertTrue(false,"could not click on new task button" );
				}
			}else {
				log(LogStatus.ERROR, "could not find "+Smoke_TWINS1Name, YesNo.Yes);
				sa.assertTrue(false,"could not find "+Smoke_TWINS1Name );
			}
			
//		}else {
//			log(LogStatus.ERROR, "could not click on object 1 tab", YesNo.Yes);
//			sa.assertTrue(false,"could not click on object 1 tab" );
//		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2tc006_AddWatchlistContactAndVerifyImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TWINS2Name, 20)) {
				
//				WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Communications.toString(), 10);
//				click(driver, ele1, RelatedTab.Communications.toString(), action.BOOLEAN);
//				scrollThroughOutWindow(driver);
				ThreadSleep(3000);
				WebElement ele;
				String[][] basicsection= {{"Subject",TWTask3Subject},{"Related_To",Smoke_TWContact3FName+" "+Smoke_TWContact3LName}};
				//String[][] advanceSection= {{"Priority","Normal"}};
				String[][] advanceSection= null;
		        
				String[][] taskSection= {{"Subject","ABC"},{"Due Date Only","06/04/2020"},{"Status","In Progress"}};



            bp.createActivityTimeline(projectName,true,"Call", basicsection, advanceSection,null,null, false, null, null, null, null, null, null);
				System.err.println("donnnnnnnnnnneeeeeeeeeeeeeeeeeeeeeeeeeeeeee...................................................................");
				CommonLib.ThreadSleep(50000);
					
					refresh(driver);
					ThreadSleep(2000);
//					WebElement ele2 = bp.getRelatedTab(projectName, RelatedTab.Communications.toString(), 10);
//					click(driver, ele2, RelatedTab.Communications.toString(), action.BOOLEAN);
//					ThreadSleep(2000);
					
					//out of scopr
//				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Call , 10);
//				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Call.toString(), action.BOOLEAN)) {
//				ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Call , 10);
//					//3
//					boolean flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object1Tab, Smoke_TWContact3FName+" "+Smoke_TWContact3LName, action.SCROLLANDBOOLEAN, 10);		
//					if (flag) {
//						log(LogStatus.SKIP,"Selected "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.No);
//
//					} else {
//						sa.assertTrue(false,"Not Able to Select "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name);
//						log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.Yes);
//
//					}
//
//					ele=ip.getLabelTextBox("PE", PageName.NewTaskPage.toString(), "Subject",20);
//					ThreadSleep(1000);
//					JavascriptExecutor js = (JavascriptExecutor) driver;
//					ele.clear();
//					ThreadSleep(1000);
//					js.executeScript("arguments[0].value='"+TWTask3Subject+"';", ele);
//					ele.sendKeys(Keys.BACK_SPACE);
//					ele.sendKeys(Keys.ENTER);
//					ThreadSleep(1000);
//				
//					if (sendKeys(driver, ele, TWTask3Subject, "Subject", action.SCROLLANDBOOLEAN)) {
//								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
//									log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
//								}
//								else {
//									log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
//									sa.assertTrue(false,"save button is not clickable so task not created" );
//								}
//					}
//					else {
//						log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
//						sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
//					}
//					ThreadSleep(2000);
//					refresh(driver);
//					ThreadSleep(2000);
					ip.clickOnTab(projectName, TabName.TaskTab);
					
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.TaskPage,null, TWTask3Subject, SubjectElement.SubjectLink, 10);
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
			
//		}else {
//			log(LogStatus.ERROR, "could not click on object 1 tab", YesNo.Yes);
//			sa.assertTrue(false,"could not click on object 1 tab" );
//		}
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
		BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TWINS1Name, 20)) {
//				WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Communications.toString(), 10);
//				click(driver, ele1, RelatedTab.Communications.toString(), action.BOOLEAN);
//				scrollThroughOutWindow(driver);
				ThreadSleep(3000);
				WebElement ele;
				String[][] basicsection= {{"Subject",TWTask4Subject},{"Related_To",Smoke_TWContact1FName+" "+Smoke_TWContact1LName}};
				//String[][] advanceSection= {{"Priority","Normal"}};
				String[][] advanceSection= null;
		        String[][] taskSection= {{"Subject","ABC"},{"Due Date Only","06/04/2020"},{"Status","In Progress"}};



            bp.createActivityTimeline(projectName,true,"Call", basicsection, advanceSection,null,null, false, null, null, null, null, null, null);
				System.err.println("donnnnnnnnnnneeeeeeeeeeeeeeeeeeeeeeeeeeeeee...................................................................");
				CommonLib.ThreadSleep(50000);
					
					refresh(driver);
//					ThreadSleep(2000);
//					WebElement ele2 = bp.getRelatedTab(projectName, RelatedTab.Communications.toString(), 10);
//					click(driver, ele2, RelatedTab.Communications.toString(), action.BOOLEAN);
					ThreadSleep(2000);
					//out of scope
//				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Call , 10);
//				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Call.toString(), action.BOOLEAN)) {
//				ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Call , 10);
//					//3
//					boolean flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object1Tab, Smoke_TWContact1FName+" "+Smoke_TWContact1LName, action.SCROLLANDBOOLEAN, 10);		
//					if (flag) {
//						log(LogStatus.SKIP,"Selected "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name,YesNo.No);
//
//					} else {
//						sa.assertTrue(false,"Not Able to Select "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name);
//						log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name,YesNo.Yes);
//
//					}
//					due is removed from log a call layout
//					tp.getdueDateTextBoxInNewTask(projectName, 20).clear();	
					
					//out of scope
//					ele=ip.getLabelTextBox("PE", PageName.NewTaskPage.toString(), "Subject",20);
//					ThreadSleep(1000);
//					JavascriptExecutor js = (JavascriptExecutor) driver;
//					ele.clear();
//					ThreadSleep(1000);
//					js.executeScript("arguments[0].value='"+TWTask4Subject+"';", ele);
//					ele.sendKeys(Keys.BACK_SPACE);
//					ele.sendKeys(Keys.ENTER);
//					ThreadSleep(1000);
//					if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), TWTask4Subject, "Subject", action.SCROLLANDBOOLEAN)) {
//								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
//									log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
//								}
//								else {
//									log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
//									sa.assertTrue(false,"save button is not clickable so task not created" );
//								}
//					}
//					else {
//						log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
//						sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
//					}
//					ThreadSleep(2000);
//					refresh(driver);
//					ThreadSleep(2000);
					ip.clickOnTab(projectName, TabName.TaskTab);
					
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.TaskPage,ActivityType.Past, TWTask4Subject, SubjectElement.SubjectLink, 10);
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
			
//		}else {
//			log(LogStatus.ERROR, "could not click on object 1 tab", YesNo.Yes);
//			sa.assertTrue(false,"could not click on object 1 tab" );
//		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc008_AddMultipleWatchlistContactAndVerifyImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TWINS2Name, 20)) {
//				WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Communications.toString(), 10);
//				click(driver, ele1, RelatedTab.Communications.toString(), action.BOOLEAN);
//				scrollThroughOutWindow(driver);
				ThreadSleep(3000);
				WebElement ele;
				String[][] basicsection= {{"Subject",TWTask5Subject},{"Related_To",Smoke_TWContact1FName+" "+Smoke_TWContact1LName},{"Related_To",Smoke_TWContact3FName+" "+Smoke_TWContact3LName}};
				String[][] advanceSection= {/*{"Priority","Normal"},*/{"Date",todaysDate}};
		        String[][] taskSection= {{"Subject","ABC"},{"Due Date Only","06/04/2020"},{"Status","In Progress"}};



                bp.createActivityTimeline(projectName,true,"Call", basicsection, advanceSection,null,null, false, null, null, null, null, null, null);
				System.err.println("donnnnnnnnnnneeeeeeeeeeeeeeeeeeeeeeeeeeeeee...................................................................");
				CommonLib.ThreadSleep(50000);
					
					refresh(driver);
					ThreadSleep(2000);
//					WebElement ele2 = bp.getRelatedTab(projectName, RelatedTab.Communications.toString(), 10);
//					click(driver, ele2, RelatedTab.Communications.toString(), action.BOOLEAN);
//					ThreadSleep(2000);
					//out of scope 2
//				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task , 10);
//				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task.toString(), action.BOOLEAN)) {
//					ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.Add , 10);
//				
//				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.Add.toString(), action.BOOLEAN)) {
//					log(LogStatus.ERROR,"able to click on add button", YesNo.Yes);
//				}else {
//					log(LogStatus.ERROR,"could not click on add button", YesNo.Yes);
//					sa.assertTrue(false,"could not click on add button" );
//				}
				//out of scope
//				scrollDownThroughWebelement(driver, ip.relatedAssociations(projectName).get(0), "related associatons");
//				if (clickUsingJavaScript(driver, ip.getrelatedAssociationsdropdownButton(projectName, 10), "dropdown button for related associations")) {
//					List<String> l=compareMultipleListContainsByTitle(driver, tabs, ip.listOfObjectsInRelatedAssctions(projectName));
//					if (l.isEmpty()) {
//						log(LogStatus.INFO, "successfully verified presence of all tabs "+tabs, YesNo.No);
//					}
//					else {
//						for (String a:l) {
//							log(LogStatus.ERROR,"not found "+a,  YesNo.Yes);
//							sa.assertTrue(false, "not found "+a);
//						}
//					}
//					l.clear();
//					l=compareMultipleListContainsByTitle(driver,ip.getTabName(projectName, TabName.Object2Tab).toString(), ip.listOfObjectsInRelatedAssctions(projectName));
//					if (l.isEmpty()) {
//						log(LogStatus.ERROR, "tab "+TabName.Object2Tab.toString()+" is present but should not be", YesNo.Yes);
//						sa.assertTrue(false, "tab "+TabName.Object2Tab.toString()+" is present but should not be");
//					}
//					else {
//						for (String a:l) {
//							log(LogStatus.INFO,"not found "+a+" as expected",  YesNo.Yes);
//
//						}
//					}
					//3
					//out of scope 2
//					boolean flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object1Tab, Smoke_TWContact3FName+" "+Smoke_TWContact3LName, action.SCROLLANDBOOLEAN, 10);		
//					if (flag) {
//						log(LogStatus.SKIP,"Selected "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.No);
//
//					} else {
//						sa.assertTrue(false,"Not Able to Select "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name);
//						log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.Yes);
//
//					}
//					flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object1Tab, Smoke_TWContact1FName+" "+Smoke_TWContact1LName, action.SCROLLANDBOOLEAN, 10);		
//					if (flag) {
//						log(LogStatus.SKIP,"Selected "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name,YesNo.No);
//
//					} else {
//						sa.assertTrue(false,"Not Able to Select "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name);
//						log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name,YesNo.Yes);
//
//					}
//					
//					if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), TWTask5Subject, "Subject", action.SCROLLANDBOOLEAN)) {
//								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
//									log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
//								}
//								else {
//									log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
//									sa.assertTrue(false,"save button is not clickable so task not created" );
//								}
//					}
//					else {
//						log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
//						sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
//					}
//					ThreadSleep(2000);
//					refresh(driver);
//					ThreadSleep(2000);
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object3Page,ActivityType.Next, TWTask5Subject, SubjectElement.SubjectLink, 10);
					if (click(driver, ele, "task name",action.SCROLLANDBOOLEAN)) {
						String[][] fieldsWithValues= {{PageLabel.Subject.toString(),TWTask5Subject},
								{PageLabel.Watchlist.toString(),Watchlist.True.toString()}};

						tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);

					}else {
						appLog.error("could not click on task on task page");
						sa.assertTrue(false,"could not click on task on task page" );
					}
//				}else {
//					log(LogStatus.ERROR, "related association dropdown button is not clickable", YesNo.Yes);
//					sa.assertTrue(false,"related association dropdown button is not clickable" );
//				}
				}else {
					log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
					sa.assertTrue(false,"could not click on new task button" );
				}
			}else {
				log(LogStatus.ERROR, "could not find "+Smoke_TWINS2Name, YesNo.Yes);
				sa.assertTrue(false,"could not find "+Smoke_TWINS2Name );
			}
			
//		}else {
//			log(LogStatus.ERROR, "could not click on object 1 tab", YesNo.Yes);
//			sa.assertTrue(false,"could not click on object 1 tab" );
//		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc009_AddMultipleWatchlistContactAndVerifyImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TWINS2Name, 20)) {
//				WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Communications.toString(), 10);
//				click(driver, ele1, RelatedTab.Communications.toString(), action.BOOLEAN);
//				scrollThroughOutWindow(driver);
				ThreadSleep(3000);
				WebElement ele;
				String[][] basicsection= {{"Subject",TWTask6Subject},{"Related_To",Smoke_TWContact3FName+" "+Smoke_TWContact3LName},{"Related_To",Smoke_TWContact1FName+" "+Smoke_TWContact1LName}};
				String[][] advanceSection= {/*{"Priority","Normal"},*/{"Date",todaysDate}};
		        String[][] taskSection= {{"Subject","ABC"},{"Due Date Only","06/04/2020"},{"Status","In Progress"}};



                 bp.createActivityTimeline(projectName,true,"Call", basicsection, advanceSection,null,null, false, null, null, null, null, null, null);
				System.err.println("donnnnnnnnnnneeeeeeeeeeeeeeeeeeeeeeeeeeeeee...................................................................");
				CommonLib.ThreadSleep(50000);
					
					refresh(driver);
					ThreadSleep(2000);
//					WebElement ele2 = bp.getRelatedTab(projectName, RelatedTab.Communications.toString(), 10);
//					click(driver, ele2, RelatedTab.Communications.toString(), action.BOOLEAN);
					ThreadSleep(2000);
				//out of scope 2
//				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Call , 10);
//				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Call.toString(), action.BOOLEAN)) {
//				ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Call , 10);
				
				
				//out of scope
//				scrollDownThroughWebelement(driver, ip.relatedAssociations(projectName).get(0), "related associatons");
//				if (clickUsingJavaScript(driver, ip.getrelatedAssociationsdropdownButton(projectName, 10), "dropdown button for related associations")) {
//					List<String> l=compareMultipleListContainsByTitle(driver, tabs, ip.listOfObjectsInRelatedAssctions(projectName));
//					if (l.isEmpty()) {
//						log(LogStatus.INFO, "successfully verified presence of all tabs "+tabs, YesNo.No);
//					}
//					else {
//						for (String a:l) {
//							log(LogStatus.ERROR,"not found "+a,  YesNo.Yes);
//							sa.assertTrue(false, "not found "+a);
//						}
//					}
//					l.clear();
//					l=compareMultipleListContainsByTitle(driver,ip.getTabName(projectName, TabName.Object2Tab).toString(), ip.listOfObjectsInRelatedAssctions(projectName));
//					if (l.isEmpty()) {
//						log(LogStatus.ERROR, "tab "+TabName.Object2Tab.toString()+" is present but should not be", YesNo.Yes);
//						sa.assertTrue(false, "tab "+TabName.Object2Tab.toString()+" is present but should not be");
//					}
//					else {
//						for (String a:l) {
//							log(LogStatus.INFO,"not found "+a+" as expected",  YesNo.Yes);
//
//						}
//					}
					//3
					// out of scope 2
//					boolean flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object1Tab, Smoke_TWContact3FName+" "+Smoke_TWContact3LName, action.SCROLLANDBOOLEAN, 10);		
//					if (flag) {
//						log(LogStatus.SKIP,"Selected "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.No);
//
//					} else {
//						sa.assertTrue(false,"Not Able to Select "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name);
//						log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.Yes);
//
//					}
//					flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object1Tab, Smoke_TWContact1FName+" "+Smoke_TWContact1LName, action.SCROLLANDBOOLEAN, 10);		
//					if (flag) {
//						log(LogStatus.SKIP,"Selected "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name,YesNo.No);
//
//					} else {
//						sa.assertTrue(false,"Not Able to Select "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name);
//						log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name,YesNo.Yes);
//
//					}
//				due is removed from log a call layout	
//					tp.getdueDateTextBoxInNewTask(projectName, 20).clear();	
					// out of scope 2
//					ele=ip.getLabelTextBox("PE", PageName.NewTaskPage.toString(), "Subject",20);
//					ThreadSleep(1000);
//					JavascriptExecutor js = (JavascriptExecutor) driver;
//					ele.clear();
//					ThreadSleep(1000);
//					js.executeScript("arguments[0].value='"+TWTask6Subject+"';", ele);
//					ele.sendKeys(Keys.BACK_SPACE);
//					ele.sendKeys(Keys.ENTER);
//					ThreadSleep(1000);
//					if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), TWTask6Subject, "Subject", action.SCROLLANDBOOLEAN)) {
//								if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
//									log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
//								}
//								else {
//									log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
//									sa.assertTrue(false,"save button is not clickable so task not created" );
//								}
//					}
//					else {
//						log(LogStatus.ERROR, "subject textbox is not visible so task could not be created", YesNo.Yes);
//						sa.assertTrue(false,"subject textbox is not visible so task could not be created" );
//					}
//					ThreadSleep(2000);
//					refresh(driver);
//					ThreadSleep(2000);
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object3Page,ActivityType.Past, TWTask6Subject, SubjectElement.SubjectLink, 10);
					if (click(driver, ele, "task name",action.SCROLLANDBOOLEAN)) {
						String[][] fieldsWithValues= {{PageLabel.Subject.toString(),TWTask6Subject},
								{PageLabel.Watchlist.toString(),Watchlist.True.toString()}};

						tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);

					}else {
						log(LogStatus.ERROR,"could not click on task on task page", YesNo.Yes);
						sa.assertTrue(false,"could not click on task on task page" );
					}
//				}else {
//					log(LogStatus.ERROR, "related association dropdown button is not clickable", YesNo.Yes);
//					sa.assertTrue(false,"related association dropdown button is not clickable" );
//				}
				}else {
					log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
					sa.assertTrue(false,"could not click on new task button" );
				}
			}else {
				log(LogStatus.ERROR, "could not find "+Smoke_TWINS2Name, YesNo.Yes);
				sa.assertTrue(false,"could not find "+Smoke_TWINS2Name );
			}
			
//		}else {
//			log(LogStatus.ERROR, "could not click on object 1 tab", YesNo.Yes);
//			sa.assertTrue(false,"could not click on object 1 tab" );
//		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc010_RemoveContactFromWatchlistContactAndVerifyImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.TaskTab)) {
			WebElement ele=tp.getTaskNameLinkInSideMMenu(projectName, TWTask5Subject, 15);
		if (click(driver, ele, TWTask5Subject, action.BOOLEAN)) {
			String[][] fieldsWithValues= {{PageLabel.Subject.toString(),TWTask5Subject},
					{PageLabel.Watchlist.toString(),Watchlist.True.toString()}};

			tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);
			ThreadSleep(3000);
			if (lp.clickOnShowMoreActionDownArrow(projectName, PageName.TaskPage, ShowMoreActionDropDownList.Edit, 20)) {
				ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.TaskPage, PageLabel.Name.toString(),false, Smoke_TWContact3FName+" "+Smoke_TWContact3LName, action.SCROLLANDBOOLEAN, 15);
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
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.TaskTab)) {
			WebElement ele=tp.getTaskNameLinkInSideMMenu(projectName, TWTask6Subject, 15);
		if (click(driver, ele, TWTask6Subject, action.BOOLEAN)) {
			String[][] fieldsWithValues= {{PageLabel.Subject.toString(),TWTask6Subject},
					{PageLabel.Watchlist.toString(),Watchlist.True.toString()}};

			tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);
			ThreadSleep(3000);
			if (lp.clickOnShowMoreActionDownArrow(projectName, PageName.TaskPage, ShowMoreActionDropDownList.Edit, 20)) {
				ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.TaskPage, PageLabel.Name.toString(),false, Smoke_TWContact3FName+" "+Smoke_TWContact3LName, action.SCROLLANDBOOLEAN, 15);
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
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		String relatedTo = Smoke_TWContact3FName+" "+Smoke_TWContact3LName;
		String[][] task1UpdateBasicSection = {{ "Related_To", relatedTo } };
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.TaskTab)) {
			WebElement ele=tp.getTaskNameLinkInSideMMenu(projectName, TWTask5Subject, 15);
		if (click(driver, ele, TWTask5Subject, action.BOOLEAN)) {
			String[][] fieldsWithValues= {{PageLabel.Subject.toString(),TWTask5Subject},
					{PageLabel.Watchlist.toString(),Watchlist.True.toString()}};

			tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);
			ThreadSleep(3000);
			if (lp.clickOnShowMoreActionDownArrow(projectName, PageName.TaskPage, ShowMoreActionDropDownList.Edit, 20)) {
				ThreadSleep(3000);
				boolean flag=BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
						null, null, null, false, null, null, null, null, null, null);
						//ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TaskPage, PageLabel.Name.toString(), TabName.TaskTab, Smoke_TWContact3FName+" "+Smoke_TWContact3LName, action.SCROLLANDBOOLEAN, 10);		
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
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		String relatedTo = Smoke_TWContact3FName+" "+Smoke_TWContact3LName;
		String[][] task1UpdateBasicSection = {{ "Related_To", relatedTo } };

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.TaskTab)) {
			WebElement ele=tp.getTaskNameLinkInSideMMenu(projectName, TWTask6Subject, 15);
		if (click(driver, ele, TWTask6Subject, action.BOOLEAN)) {
			String[][] fieldsWithValues= {{PageLabel.Subject.toString(),TWTask6Subject},
					{PageLabel.Watchlist.toString(),Watchlist.True.toString()}};

			tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);
			ThreadSleep(3000);
			if (lp.clickOnShowMoreActionDownArrow(projectName, PageName.TaskPage, ShowMoreActionDropDownList.Edit, 20)) {
				boolean flag=BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, null,
						null, null, null, false, null, null, null, null, null, null);
						//ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object1Tab, Smoke_TWContact3FName+" "+Smoke_TWContact3LName, action.SCROLLANDBOOLEAN, 10);		
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
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);

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
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		String[][] task1AdvancedSection = { { "Due Date Only", todaysDate } };
		String[][] task1Section = { { "Due Date Only", todaysDate } };
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
			if (lp.clickOnShowMoreActionDownArrow(projectName, PageName.TaskPage, ShowMoreActionDropDownList.Edit, 20)) {
				ThreadSleep(3000);
//				if (BP.updateActivityTimelineRecord(projectName, null, task1AdvancedSection,
//						task1Section, null, null)) {
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
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver); 
		String[][] task1BasicSection = { { "Subject", TWTask8Subject },{ "Related_To", Smoke_TWINS2Name } };
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
//		if (ip.clickOnTab(projectName, TabName.TaskTab)) {

			if (BP.createActivityTimeline(projectName, true, "Task", task1BasicSection, null,
					null, null, false, null, null, null, null, null, null)) {
//			if (cp.clickOnShowMoreActionDownArrow(projectName, PageName.TaskPage, ShowMoreActionDropDownList.New_Task, 15)) {
//				log(LogStatus.INFO,"Clicked on New Task Button for show more action",YesNo.No);
//				ThreadSleep(1000);
				//if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), TWTask8Subject, "Subject", action.SCROLLANDBOOLEAN)) {
					//boolean flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TaskPage, PageLabel.Related_To.toString(), TabName.Object1Tab, Smoke_TWINS2Name, action.SCROLLANDBOOLEAN, 10);		
//					if (flag) {
//						log(LogStatus.SKIP,"Selected "+Smoke_TWINS2Name+" For Label "+PageLabel.Related_To,YesNo.No);
//
//					} else {
//						sa.assertTrue(false,"Not Able to Select "+Smoke_TWINS2Name+" For Label "+PageLabel.Related_To);
//						log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWINS2Name+" For Label "+PageLabel.Related_To,YesNo.Yes);
//					}

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

//				}else {
//					appLog.error("edit button is not clickable so cannot verify watchlist checkbox functionality");
//					sa.assertTrue(false, "edit button is not clickable so cannot verify watchlist checkbox functionality");
//				}
//			}else {
//				appLog.error("task name is not clickable so cannot verify watchlist checkbox functionality");
//				sa.assertTrue(false, "task name is not clickable so cannot verify watchlist checkbox functionality");
//			}
		}else {
			appLog.error("task tab is not clickable");
			sa.assertTrue(false, "task tab is not clickable");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Test
	@Parameters({ "projectName"})
	public void M2tc017_AddWatchlistContactInStandardTask(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		
		String[][] task1UpdateBasicSection = {{ "Related_To", Smoke_TWContact4FName+" "+Smoke_TWContact4LName } };

		String[][] task1AdvancedSection = { { "Due Date Only", todaysDate },};
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
				if (lp.clickOnShowMoreActionDownArrow(projectName, PageName.TaskPage, ShowMoreActionDropDownList.Edit, 20)) {
					if (BP.updateActivityTimelineRecord(projectName, task1UpdateBasicSection, task1AdvancedSection,
							task1AdvancedSection, null, null, false, null, null, null, null, null, null)) {
//					if (sendKeys(driver, tp.getdueDateTextBoxInNewTask(projectName, 20), todaysDate, PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
//						log(LogStatus.INFO, "Entered value to Due Date Text Box", YesNo.Yes);
//						ThreadSleep(1000);
//					}else {
//						log(LogStatus.ERROR, "duedate textbox is not visible so task could not be created", YesNo.Yes);
//						sa.assertTrue(false,"duedate textbox is not visible so task could not be created" );
//					}
//
//					boolean flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TaskPage, PageLabel.Name.toString(), TabName.Object1Tab, Smoke_TWContact4FName+" "+Smoke_TWContact4LName, action.SCROLLANDBOOLEAN, 10);		
//					if (flag) {
//						log(LogStatus.SKIP,"Selected "+Smoke_TWContact4FName+" "+Smoke_TWContact4LName+" For Label "+PageLabel.Name,YesNo.No);
//
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
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		if (ip.clickOnTab(projectName, TabName.NavatarSetup)) {
				if (np.clickOnNavatarSetupSideMenusTab(projectName, NavatarSetupSideMenuTab.ContactTransfer)) {
					log(LogStatus.INFO,"Clicked on Contact Transfer Tab", YesNo.No);
					ThreadSleep(2000);
					if (click(driver, np.getEditButtonforNavatarSetUpSideMenuTab(projectName,NavatarSetupSideMenuTab.ContactTransfer, 10), "Edit Button", action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button", YesNo.No);
						ThreadSleep(5000);
						if (!isSelected(driver, np.getEnableCheckBoxforNavatarSetUpSideMenuTab(projectName,NavatarSetupSideMenuTab.ContactTransfer, EditViewMode.Edit, ClickOrCheckEnableDisableCheckBox.Click, 20), "Enabled CheckBox")) {

							log(LogStatus.INFO, "Enable Contact Transfer is Unchecked", YesNo.No);
							ThreadSleep(5000);
							if (clickUsingJavaScript(driver,np.getEnableCheckBoxforClickNavatarSetUpSideMenuTab(projectName,NavatarSetupSideMenuTab.ContactTransfer, EditViewMode.Edit, 20),"Contact Trasfer CheckBox", action.BOOLEAN)) {
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
	public void M2tc019_1_TransferContact_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object2Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, Smoke_TWContact5FName+" "+Smoke_TWContact5LName, 20)) {
//				WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Communications.toString(), 10);
//				click(driver, ele1, RelatedTab.Communications.toString(), action.BOOLEAN);
//				scrollThroughOutWindow(driver);
				ThreadSleep(3000);
				WebElement ele;
				String[][] basicsection= {{"Subject",TWTaskCR1Subject}, {"Related_To", Smoke_TWContact5FName+" "+Smoke_TWContact5LName}};
				String[][] advanceSection= {{"Priority","Normal"},{"Due Date Only",todaysDate}};
		        String[][] taskSection= {{"Subject","ABC"},{"Due Date Only","06/04/2020"},{"Status","In Progress"}};



                bp.createActivityTimeline(projectName,true,"Task", basicsection, advanceSection,null,null, false, null, null, null, null, null, null);
				System.err.println("donnnnnnnnnnneeeeeeeeeeeeeeeeeeeeeeeeeeeeee...................................................................");
				CommonLib.ThreadSleep(50000);
					
					refresh(driver);
					ThreadSleep(2000);
//					WebElement ele2 = bp.getRelatedTab(projectName, RelatedTab.Communications.toString(), 10);
//					click(driver, ele2, RelatedTab.Communications.toString(), action.BOOLEAN);
//					ThreadSleep(2000);
//				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task , 10);
//				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task.toString(), action.BOOLEAN)) {
//					
//					ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.Add , 10);
//					
//					if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.Add.toString(), action.BOOLEAN)) {
//						log(LogStatus.ERROR,"able to click on add button", YesNo.Yes);
//					}else {
//						log(LogStatus.ERROR,"could not click on add button", YesNo.Yes);
//						sa.assertTrue(false,"could not click on add button" );
//					}
//					ThreadSleep(2000);
//					if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), TWTaskCR1Subject, "Subject", action.SCROLLANDBOOLEAN)) {
//					if (sendKeys(driver, tp.getdueDateTextBoxInNewTask(projectName, 20), "", PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
//						
//					if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
//						log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
//					}
//					else {
//						log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
//						sa.assertTrue(false,"save button is not clickable so task not created" );
//					}
//					}
//					else {
//						log(LogStatus.ERROR, "due date textbox is not visible", YesNo.Yes);
//						sa.assertTrue(false,"due date textbox is not visible" );
//					}
//				}else {
//					log(LogStatus.ERROR, "subject textbox is not clickable", YesNo.Yes);
//					sa.assertTrue(false,"subject textbox is not clickable" );
//				}
//				}else {
//					log(LogStatus.ERROR, "new task button not clickable", YesNo.Yes);
//					sa.assertTrue(false,"new task button not clickable" );
//				}
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
	public void M2tc019_2_TransferContact_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		
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
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		String parentID=null;
		String updateLabel="Watch list";
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject(environment, mode,object.Override)){
					log(LogStatus.INFO, "click on Object : " +object.valueOf("Override"), YesNo.No);
					ThreadSleep(2000);				
					switchToFrame(driver, 30, sp.getSetUpPageIframe(60));
					ThreadSleep(5000);	
					if(selectVisibleTextFromDropDown(driver, sp.getOverrideSetupComponentDropdown(10), "Override setup component dropdown", "Custom Field")){
						log(LogStatus.INFO, "Select custom field text in setup component dropdown in override setup page", YesNo.No);
						ThreadSleep(5000);	
						
						if(selectVisibleTextFromDropDown(driver, sp.getOverrideObjectDropdown(10), "Override object dropdown",PageLabel.Activity.toString())){
							log(LogStatus.INFO, "Select "+PageLabel.Activity.toString()+" text in object dropdown in override setup page", YesNo.No);
							ThreadSleep(5000);
							if(sp.updateFieldLabelInOverridePage(driver, PageLabel.Watchlist.toString(), updateLabel, action.SCROLLANDBOOLEAN)){
								log(LogStatus.INFO, "Field label: "+PageLabel.Watchlist.toString()+" successfully update to "+updateLabel, YesNo.No);
								
							}else{
								log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Watchlist.toString()+" successfully update to "+updateLabel, YesNo.Yes);
								sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Watchlist.toString()+" to "+updateLabel);	
							}
						}else{
							log(LogStatus.ERROR, "Not able to select text: "+PageLabel.Activity.toString()+" in  object dropdown in override page", YesNo.Yes);
							sa.assertTrue(false, "Not able to select text: "+PageLabel.Activity.toString()+" in  object dropdown in override page");
						}
					}else{
						log(LogStatus.ERROR, "Not able to select text: Custom Field in  setup component dropdown in override page", YesNo.Yes);
						sa.assertTrue(false, "Not able to select text: Custom Field in  setup component dropdown in override page");
					}
				
			}else{
				
				log(LogStatus.PASS, "Not able to click on Object : " + object.valueOf("Override"), YesNo.Yes);
				sa.assertTrue(false, "Not able to click on Object : " + object.valueOf("Override"));
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
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		String[][] task1BasicSection = { { "Subject", TWTaskUpdateLabelSubject },
				{ "Related_To", Smoke_TWContact3FName+" "+Smoke_TWContact3LName }, 
				{ "Related_To", Smoke_TWContact1FName+" "+Smoke_TWContact1LName }};
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TWINS2Name, 20)) {
				
				if (BP.createActivityTimeline(projectName, true, "Task", task1BasicSection, null,
						null, null, false, null, null, null, null, null, null)) {
					
				}
//				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.TaskPage,ActivityTimeLineItem.New_Task , 10);
//				
//				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task_with_Multiple_Associations.toString(), action.BOOLEAN)) {
//					ele=lp.getActivityTimeLineItem(projectName,PageName.TaskPage,ActivityTimeLineItem.Add , 10);
					
//					if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.Add.toString(), action.BOOLEAN)) {
//						log(LogStatus.ERROR,"able to click on add button", YesNo.Yes);
//					}else {
//						log(LogStatus.ERROR,"could not click on add button", YesNo.Yes);
//						sa.assertTrue(false,"could not click on add button" );
//					}
					//if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), TWTaskUpdateLabelSubject, "Subject", action.SCROLLANDBOOLEAN)) {

//						boolean flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object2Tab, Smoke_TWContact3FName+" "+Smoke_TWContact3LName, action.SCROLLANDBOOLEAN, 10);		
//						if (flag) {
//							log(LogStatus.SKIP,"Selected "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.No);
//
//						} else {
//							sa.assertTrue(false,"Not Able to Select "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name);
//							log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.Yes);
//
//						}

//						flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object2Tab, Smoke_TWContact1FName+" "+Smoke_TWContact1LName, action.SCROLLANDBOOLEAN, 10);		
//						if (flag) {
//							log(LogStatus.SKIP,"Selected "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name,YesNo.No);
//
//						} else {
//							sa.assertTrue(false,"Not Able to Select "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name);
//							log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name,YesNo.Yes);
//
//						}
						if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,10), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
						}
						else {
							log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
							sa.assertTrue(false,"save button is not clickable so task not created" );
						}
//					}else {
//						log(LogStatus.ERROR, "subject textbox is not visible, so cannot create task", YesNo.Yes);
//						sa.assertTrue(false,"subject textbox is not visible, so cannot create task" );
//					}
//				}else {
//					log(LogStatus.ERROR, "could not click on new task button", YesNo.Yes);
//					sa.assertTrue(false,"could not click on new task button" );
//				}
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

				cp.clickOnShowMoreDropdownOnly(projectName,PageName.Object2Page,"");
				log(LogStatus.INFO,"Able to Click on Show more Icon : "+TabName.Object2Tab+" For : "+contact,YesNo.No);
				ThreadSleep(500);
				ele = cp.actionDropdownElement(projectName, PageName.Object2Page, ShowMoreActionDropDownList.Delete, 15);
				if (ele==null) {
					ele =cp.getDeleteButton(projectName, 30);
				}
				if (click(driver, ele, "delete", action.BOOLEAN)) {
					ThreadSleep(2000);
					if(clickUsingJavaScript(driver,cp.getDeleteButtonPopUp(projectName, 10), "delete", action.BOOLEAN)) {
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
				
				if (lp.clickOnShowMoreActionDownArrow(projectName, PageName.TaskPage, ShowMoreActionDropDownList.Edit, 20)) {
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
	public void M2tc022_1_ConvDateAddConversionDateField(String projectName) {
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
			List<String> abc = setup.DragNDrop("", mode, object.Firm, ObjectFeatureName.pageLayouts, layoutName, sourceANDDestination);
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
	public void M2tc022_2_ConvDateCreateInsCompany(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.createEntityOrAccount(projectName, mode, Smoke_CDINS1Name, Smoke_CDINS1RecordType, null, new String[][] {{PageLabel.Status.toString(),Smoke_CDINS1Status}}, 10)) {
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
	public void M2tc023_ConvDateChangeStatusOfCompanyAndCheck(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		String mode="Lightning";
	
		String labels[]={PageLabel.Status.toString(),PageLabel.Deal_Conversion_Date.toString()};
		int i=0;
		String values1[]={PageLabel.Under_Evaluation.toString().replace("_", " "),todaysDate};
		String values2[]={PageLabel.Watchlist.toString(),todaysDate};
		
		
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
		if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_CDINS1Name, 10)) {
			if (ip.changeStatus(projectName, PageLabel.Under_Evaluation.toString())) {
				for (String s:labels) {
					ThreadSleep(1000);
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
	public void M2tc024_ConvDateCreateNewCompanyUnderEvalThenChangeToPortfolioCompany(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		
		String values1[]={PageLabel.Under_Evaluation.toString(),""};
		String values2[]={PageLabel.Watchlist.toString(),""};
		String values3[]={PageLabel.Under_Evaluation.toString(),todaysDate};
		String values4[]={PageLabel.Portfolio_Company.toString(),todaysDate};
		
		String labels[]={PageLabel.Status.toString(),PageLabel.Deal_Conversion_Date.toString()};
		int i=0;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.createEntityOrAccount(projectName, mode, Smoke_CDINS2Name, Smoke_CDINS2RecordType, null, new String[][] {{PageLabel.Status.toString(),Smoke_CDINS2Status}}, 10)) {
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
	public void M2tc025_ConvDateRenameStatusValues(String projectName) {
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
				if (sp.searchStandardOrCustomObject(environment, mode,object.Firm )) {
					if(sp.clickOnObjectFeature(environment, mode,object.Firm, ObjectFeatureName.FieldAndRelationShip)) {
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
								ThreadSleep(4000);
								switchToFrame(driver, 10, sp.getFrame(PageName.AccountCustomFieldStatusPage, 10));
								ele=sp.clickOnEditInFrontOfFieldValues(projectName, PageLabel.Under_Evaluation.toString());
								if (clickUsingJavaScript(driver, ele, "watchlist", action.BOOLEAN)) {
									switchToDefaultContent(driver);
									ThreadSleep(4000);
									switchToFrame(driver, 10, sp.getstatusPicklistFrame(10));
									
									if (sendKeys(driver, sp.getFieldLabelTextBox1(10), PageLabel.RenameUnder_Evaluation.toString().replace("_", " "), "label", action.BOOLEAN)) {
										
										if (clickUsingJavaScript(driver, fp.getCustomTabSaveBtn(10), "save", action.BOOLEAN)) {
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
								ThreadSleep(4000);
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
	public void M2tc026_ConvDateCreateInsCompany(String projectName) {
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
		String values2[]={PageLabel.RenameUnder_Evaluation.toString(),todaysDate};
		
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.createEntityOrAccount(projectName, mode, Smoke_CDINS3Name, Smoke_CDINS3RecordType, null, new String[][] {{PageLabel.Status.toString(),status}}, 10)) {
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
	public void M2tc027_ConvDatePostConditionRevertRenameStatusValues(String projectName) {
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
				if (sp.searchStandardOrCustomObject(environment, mode,object.Firm )) {
					if(sp.clickOnObjectFeature(environment, mode,object.Firm, ObjectFeatureName.FieldAndRelationShip)) {
						if (sendKeys(driver, sp.getsearchTextboxFieldsAndRelationships(10), PageLabel.Status.toString()+Keys.ENTER, "status", action.BOOLEAN)) {
							if (sp.clickOnAlreadyCreatedLayout(PageLabel.Status.toString())) {
								switchToFrame(driver, 10, sp.getFrame(PageName.AccountCustomFieldStatusPage, 10));
								ThreadSleep(3000);
								WebElement ele=sp.clickOnEditInFrontOfFieldValues(projectName, PageLabel.RenameWatchlist.toString());
								ThreadSleep(3000);
								if (click(driver, ele, "watchlist", action.BOOLEAN)) {
									switchToDefaultContent(driver);
									ThreadSleep(4000);
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
								CommonLib.refresh(driver);								ThreadSleep(4000);
								switchToFrame(driver, 10, sp.getFrame(PageName.AccountCustomFieldStatusPage, 10));
								ele=sp.clickOnEditInFrontOfFieldValues(projectName, PageLabel.RenameUnder_Evaluation.toString());
								if (click(driver, ele, "watchlist", action.BOOLEAN)) {
									switchToDefaultContent(driver);
									ThreadSleep(4000);
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
								ThreadSleep(4000);
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
	public void M2tc028_CreateInstitutionData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		ThreadSleep(5000);
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
				
				if (ip.createEntityOrAccount(projectName, mode, M2_HSRINS1Name, M2_HSRINS1RecordType, null, null, 20)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+M2_HSRINS1Name+" of record type : "+M2_HSRINS1RecordType,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+M2_HSRINS1Name+" of record type : "+M2_HSRINS1RecordType);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+M2_HSRINS1Name+" of record type : "+M2_HSRINS1RecordType,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
			}
		
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc029_CreateContactData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String mailID = "";
		ThreadSleep(5000);
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			mailID = lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, mailID, "Contacts", excelLabel.Variable_Name, "M2HSRCON1",
					excelLabel.Contact_EmailId);

			if (cp.createContact(projectName, M2_HSRContact1FName, M2_HSRContact1LName, M2_HSRContact1Ins, mailID, "",
					null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO, "successfully Created Contact : " + M2_HSRContact1FName + " " + M2_HSRContact1LName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Create Contact : " + M2_HSRContact1FName + " " + M2_HSRContact1LName);
				log(LogStatus.SKIP, "Not Able to Create Contact: " + M2_HSRContact1FName + " " + M2_HSRContact1LName,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object2Tab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object2Tab, YesNo.Yes);
		}
			
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc030_CreateDealData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		ThreadSleep(5000);
		
		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
			
			if (ip.createEntityOrAccount(projectName, mode, M2_HSRINS2Name, M2_HSRINS2RecordType, null, null, 20)) {
				log(LogStatus.INFO,"successfully Created Account/Entity : "+M2_HSRINS2Name+" of record type : "+M2_HSRINS2RecordType,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Account/Entity : "+M2_HSRINS2Name+" of record type : "+M2_HSRINS2RecordType);
				log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+M2_HSRINS2Name+" of record type : "+M2_HSRINS2RecordType,YesNo.Yes);
			}


		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
	
		
		//deal	
		String[][] otherlabel={{excelLabel.Source_Firm.toString(),M2_HSRPipeline1SourceFirm},{excelLabel.Source_Contact.toString(),M2_HSRPipeline1SourceContact}};
		if(fp.clickOnTab(environment,mode, TabName.DealTab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.DealTab,YesNo.No);
			
			if(fp.createDeal(projectName, "", M2_HSRPipeline1Name, M2_HSRPipeline1Company, M2_HSRPipeline1Stage, otherlabel, 30)){
				
				log(LogStatus.INFO,"successfully Created deal : "+M2_HSRPipeline1Name,YesNo.No);	

			}else{
				
				sa.assertTrue(false,"Not Able to Create deal : "+M2_HSRPipeline1Name);
				log(LogStatus.SKIP,"Not Able to Create deal : "+M2_HSRPipeline1Name,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.DealTab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.DealTab,YesNo.Yes);
		}
		
		dealQualityScore=1;
		totalDealsshown=1;
		averageDealQualityScore= dealQualityScore/totalDealsshown;
		String label="";
		String value="";
		String[][] labelAndValue={{PageLabel.Highest_Stage_Reached.toString(),M2_HSRPipeline1Stage},{excelLabel.Deal_Quality_Score.toString(),String.valueOf(dealQualityScore)}};
		
		for(String[] data:labelAndValue){
			label = data[0];
			value =data[1];
		if(fp.fieldValueVerification(projectName, PageName.DealPage, PageLabel.valueOf(label), value, 30)){
			
			log(LogStatus.INFO,"successfully verify  field value  on : "+M2_HSRPipeline1Name,YesNo.No);	

		}else{
			sa.assertTrue(false,"not verify  field value  on : "+M2_HSRPipeline1Name);
			log(LogStatus.SKIP,"not verify  field value  on : "+M2_HSRPipeline1Name,YesNo.Yes);
		}
		}
		
		String[][] labelAndValue2={{excelLabel.Average_Deal_Quality_Score.toString(),String.valueOf(averageDealQualityScore)},{excelLabel.Total_Deals_Shown.toString(),String.valueOf(totalDealsshown)}};

		if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
			
			if(fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline1SourceFirm, 30)){
				
				log(LogStatus.INFO,"open created item"+M2_HSRPipeline1SourceFirm,YesNo.No);	
				

				for(String[] data:labelAndValue2){
					label = data[0];
					value =data[1];
				if(fp.fieldValueVerification(projectName, PageName.InstitutionsPage, PageLabel.valueOf(label), value, 30)){
					log(LogStatus.INFO,"successfully verify  field value  on : "+M2_HSRPipeline1SourceFirm,YesNo.No);	
					
				}else{
					sa.assertTrue(false,"not verify  field value  on : "+M2_HSRPipeline1SourceFirm);
					log(LogStatus.SKIP,"not verify  field value  on : "+M2_HSRPipeline1SourceFirm,YesNo.Yes);
				}
				}

			}else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+M2_HSRPipeline1SourceFirm);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+M2_HSRPipeline1SourceFirm,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
		
		
		if(fp.clickOnTab(environment,mode, TabName.Object2Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);
			
			if(fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline1SourceContact, 30)){
				
				log(LogStatus.INFO,"open created item"+M2_HSRPipeline1SourceContact,YesNo.No);	
				

				for(String[] data:labelAndValue2){
					label = data[0];
					value =data[1];
				if(fp.fieldValueVerification(projectName, PageName.DealPage, PageLabel.valueOf(label), value, 30)){
					
					log(LogStatus.INFO,"successfully verify  field value  on : "+M2_HSRPipeline1SourceContact,YesNo.No);	
					
				}else{
					sa.assertTrue(false,"not verify  field value  on : "+M2_HSRPipeline1SourceContact);
					log(LogStatus.SKIP,"not verify  field value  on : "+M2_HSRPipeline1SourceContact,YesNo.Yes);
				}
				}

			}else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+M2_HSRPipeline1SourceContact);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+M2_HSRPipeline1SourceContact,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2tc031_ChangeStageDealReceivedToNDASigned_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=1;
		totalDealsshown=1;
		averageDealQualityScore=dealQualityScore/totalDealsshown;;
		Double a=Double.valueOf(averageDealQualityScore);
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.NDA_Signed.toString(),Stage.NDA_Signed.toString(),String.valueOf(dealQualityScore)};
		
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		String labelValues1[]={String.valueOf(a),String.valueOf(totalDealsshown)};
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline1Name, 10)){
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
				sa.assertTrue(false,"not able to find pipeline "+M2_HSRPipeline1Name);
				log(LogStatus.SKIP,"not able to find pipeline "+M2_HSRPipeline1Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		
		if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
			
			if(fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline1SourceFirm, 30)){
				
				log(LogStatus.INFO,"open created item"+M2_HSRPipeline1SourceFirm,YesNo.No);	
				
				WebElement ele1 = BP.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				for (int i =0;i<labelName1.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName1[i],labelValues1[i])) {
						log(LogStatus.INFO,"successfully verified "+labelName1[i],YesNo.Yes);
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				
				}

			}else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+M2_HSRPipeline1SourceFirm);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+M2_HSRPipeline1SourceFirm,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
		
		
		if(fp.clickOnTab(environment,mode, TabName.Object2Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);
			
			if(fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline1SourceContact, 30)){
				
				WebElement ele1 = BP.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				log(LogStatus.INFO,"open created item"+M2_HSRPipeline1SourceContact,YesNo.No);	
				

				for (int i =0;i<labelName1.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName1[i],labelValues1[i])) {
						log(LogStatus.INFO,"successfully verified "+labelName1[i],YesNo.Yes);
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				
				}

			}else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+M2_HSRPipeline1SourceContact);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+M2_HSRPipeline1SourceContact,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc032_ChangeStageNDASignedToManagementMeeting_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=3;
		totalDealsshown=1;
		averageDealQualityScore=dealQualityScore/totalDealsshown;
		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.Management_Meeting.toString(),Stage.Management_Meeting.toString(),String.valueOf(dealQualityScore)};
		
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline1Name, 10)){
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
				sa.assertTrue(false,"not able to find pipeline "+M2_HSRPipeline1Name);
				log(LogStatus.SKIP,"not able to find pipeline "+M2_HSRPipeline1Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		
		if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
			
			if(fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline1SourceFirm, 30)){
				WebElement ele1 = BP.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				
				log(LogStatus.INFO,"open created item"+M2_HSRPipeline1SourceFirm,YesNo.No);	
				

				for (int i =0;i<labelName1.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName1[i],labelValues1[i])) {
						log(LogStatus.INFO,"successfully verified "+labelName1[i],YesNo.Yes);
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				
				}

			}else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+M2_HSRPipeline1SourceFirm);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+M2_HSRPipeline1SourceFirm,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
		
		
		if(fp.clickOnTab(environment,mode, TabName.Object2Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);
			
			if(fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline1SourceContact, 30)){
				WebElement ele1 = BP.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				log(LogStatus.INFO,"open created item"+M2_HSRPipeline1SourceContact,YesNo.No);	
				

				for (int i =0;i<labelName1.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName1[i],labelValues1[i])) {
						log(LogStatus.INFO,"successfully verified "+labelName1[i],YesNo.Yes);
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				
				}

			}else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+M2_HSRPipeline1SourceContact);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+M2_HSRPipeline1SourceContact,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc033_ChangeStageManagementMeetingToIOI_Action(String projectName){
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=3;
		totalDealsshown=1;
		averageDealQualityScore=dealQualityScore/totalDealsshown;;
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.IOI.toString(),Stage.IOI.toString(),String.valueOf(dealQualityScore)};
		
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline1Name, 10)){
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
				sa.assertTrue(false,"not able to find pipeline "+M2_HSRPipeline1Name);
				log(LogStatus.SKIP,"not able to find pipeline "+M2_HSRPipeline1Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		
		if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
			
			if(fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline1SourceFirm, 30)){
				
				WebElement ele1 = BP.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				log(LogStatus.INFO,"open created item"+M2_HSRPipeline1SourceFirm,YesNo.No);	
				

				for (int i =0;i<labelName1.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName1[i],labelValues1[i])) {
						log(LogStatus.INFO,"successfully verified "+labelName1[i],YesNo.Yes);
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				
				}

			}else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+M2_HSRPipeline1SourceFirm);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+M2_HSRPipeline1SourceFirm,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
		
		
		if(fp.clickOnTab(environment,mode, TabName.Object2Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);
			
			if(fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline1SourceContact, 30)){
				WebElement ele1 = BP.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				log(LogStatus.INFO,"open created item"+M2_HSRPipeline1SourceContact,YesNo.No);	
				

				for (int i =0;i<labelName1.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName1[i],labelValues1[i])) {
						log(LogStatus.INFO,"successfully verified "+labelName1[i],YesNo.Yes);
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				
				}

			}else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+M2_HSRPipeline1SourceContact);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+M2_HSRPipeline1SourceContact,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc034_ChangeStageIOIToLOI_Action(String projectName){
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=5;
		totalDealsshown=1;
		averageDealQualityScore=dealQualityScore/totalDealsshown;;
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.LOI.toString(),Stage.LOI.toString(),String.valueOf(dealQualityScore)};
		
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline1Name, 10)){
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
				sa.assertTrue(false,"not able to find pipeline "+M2_HSRPipeline1Name);
				log(LogStatus.SKIP,"not able to find pipeline "+M2_HSRPipeline1Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		
		if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
			
			if(fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline1SourceFirm, 30)){
				
				WebElement ele1 = BP.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				log(LogStatus.INFO,"open created item"+M2_HSRPipeline1SourceFirm,YesNo.No);	
				

				for (int i =0;i<labelName1.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName1[i],labelValues1[i])) {
						log(LogStatus.INFO,"successfully verified "+labelName1[i],YesNo.Yes);
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				
				}

			}else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+M2_HSRPipeline1SourceFirm);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+M2_HSRPipeline1SourceFirm,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
		
		
		if(fp.clickOnTab(environment,mode, TabName.Object2Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);
			
			if(fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline1SourceContact, 30)){
				WebElement ele1 = BP.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				log(LogStatus.INFO,"open created item"+M2_HSRPipeline1SourceContact,YesNo.No);	
				

				for (int i =0;i<labelName1.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName1[i],labelValues1[i])) {
						log(LogStatus.INFO,"successfully verified "+labelName1[i],YesNo.Yes);
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				
				}

			}else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+M2_HSRPipeline1SourceContact);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+M2_HSRPipeline1SourceContact,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}
		
		lp.CRMlogout();
		sa.assertAll();
	
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc035_ChangeStageLOIToDueDiligence_Action(String projectName){
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=5;
		totalDealsshown=1;
		averageDealQualityScore=dealQualityScore/totalDealsshown;;
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.Due_Diligence.toString(),Stage.Due_Diligence.toString(),String.valueOf(dealQualityScore)};
		
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline1Name, 10)){
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
				sa.assertTrue(false,"not able to find pipeline "+M2_HSRPipeline1Name);
				log(LogStatus.SKIP,"not able to find pipeline "+M2_HSRPipeline1Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		
		if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
			
			if(fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline1SourceFirm, 30)){
				WebElement ele1 = BP.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				log(LogStatus.INFO,"open created item"+M2_HSRPipeline1SourceFirm,YesNo.No);	
				

				for (int i =0;i<labelName1.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName1[i],labelValues1[i])) {
						log(LogStatus.INFO,"successfully verified "+labelName1[i],YesNo.Yes);
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				
				}

			}else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+M2_HSRPipeline1SourceFirm);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+M2_HSRPipeline1SourceFirm,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
		
		
		if(fp.clickOnTab(environment,mode, TabName.Object2Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);
			
			if(fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline1SourceContact, 30)){
				WebElement ele1 = BP.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				log(LogStatus.INFO,"open created item"+M2_HSRPipeline1SourceContact,YesNo.No);	
				

				for (int i =0;i<labelName1.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName1[i],labelValues1[i])) {
						log(LogStatus.INFO,"successfully verified "+labelName1[i],YesNo.Yes);
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				
				}

			}else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+M2_HSRPipeline1SourceContact);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+M2_HSRPipeline1SourceContact,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}
		
		lp.CRMlogout();
		sa.assertAll();
	
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc036_ChangeStageDueDiligenceToParked_Action(String projectName){
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=5;
		totalDealsshown=1;
		averageDealQualityScore=dealQualityScore/totalDealsshown;;
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.Due_Diligence.toString(),Stage.Parked.toString(),String.valueOf(dealQualityScore)};
		
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline1Name, 10)){
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
				sa.assertTrue(false,"not able to find pipeline "+M2_HSRPipeline1Name);
				log(LogStatus.SKIP,"not able to find pipeline "+M2_HSRPipeline1Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		
		if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
			
			if(fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline1SourceFirm, 30)){
				WebElement ele1 = BP.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				log(LogStatus.INFO,"open created item"+M2_HSRPipeline1SourceFirm,YesNo.No);	
				

				for (int i =0;i<labelName1.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName1[i],labelValues1[i])) {
						log(LogStatus.INFO,"successfully verified "+labelName1[i],YesNo.Yes);
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				
				}

			}else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+M2_HSRPipeline1SourceFirm);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+M2_HSRPipeline1SourceFirm,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
		
		
		if(fp.clickOnTab(environment,mode, TabName.Object2Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);
			
			if(fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline1SourceContact, 30)){
				WebElement ele1 = BP.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				log(LogStatus.INFO,"open created item"+M2_HSRPipeline1SourceContact,YesNo.No);	
				

				for (int i =0;i<labelName1.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName1[i],labelValues1[i])) {
						log(LogStatus.INFO,"successfully verified "+labelName1[i],YesNo.Yes);
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				
				}

			}else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+M2_HSRPipeline1SourceContact);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+M2_HSRPipeline1SourceContact,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}
		
		lp.CRMlogout();
		sa.assertAll();
	
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc037_CreateDealData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		ThreadSleep(5000);
		
		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
			
			if (ip.createEntityOrAccount(projectName, mode, M2_HSRINS3Name, M2_HSRINS3RecordType, null, null, 20)) {
				log(LogStatus.INFO,"successfully Created Account/Entity : "+M2_HSRINS3Name+" of record type : "+M2_HSRINS3RecordType,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Account/Entity : "+M2_HSRINS3Name+" of record type : "+M2_HSRINS3RecordType);
				log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+M2_HSRINS3Name+" of record type : "+M2_HSRINS3RecordType,YesNo.Yes);
			}


		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
	
		
		//deal	
		String[][] otherlabel={{excelLabel.Source_Firm.toString(),M2_HSRPipeline2SourceFirm},{excelLabel.Source_Contact.toString(),M2_HSRPipeline2SourceContact}};
		if(fp.clickOnTab(environment,mode, TabName.DealTab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.DealTab,YesNo.No);
			
			if(fp.createDeal(projectName, "", M2_HSRPipeline2Name, M2_HSRPipeline2Company, M2_HSRPipeline2Stage, otherlabel, 30)){
				
				WebElement ele1 = BP.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				log(LogStatus.INFO,"successfully Created deal : "+M2_HSRPipeline2Name,YesNo.No);	

			}else{
				
				sa.assertTrue(false,"Not Able to Create deal : "+M2_HSRPipeline2Name);
				log(LogStatus.SKIP,"Not Able to Create deal : "+M2_HSRPipeline2Name,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.DealTab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.DealTab,YesNo.Yes);
		}
		dealQualityScore=1;
		totalDealsshown=2;
		averageDealQualityScore=(parkedScore+dealQualityScore)/totalDealsshown;
		String label="";
		String value="";
		String[][] labelAndValue={{excelLabel.Highest_Stage_Reached.toString(),M2_HSRPipeline2Stage},{excelLabel.Deal_Quality_Score.toString(),String.valueOf(dealQualityScore)}};
		String[][] labelAndValue2={{excelLabel.Average_Deal_Quality_Score.toString(),String.valueOf(averageDealQualityScore)},{excelLabel.Total_Deals_Shown.toString(),String.valueOf(totalDealsshown)}};

		for(String[] data:labelAndValue){
			label = data[0];
			value =data[1];
		if(fp.fieldValueVerification(projectName, PageName.DealPage, PageLabel.valueOf(label), value, 30)){
			
			log(LogStatus.INFO,"successfully verify  field value  on : "+M2_HSRPipeline2Name,YesNo.No);	

		}else{
			sa.assertTrue(false,"not verify  field value  on : "+M2_HSRPipeline2Name);
			log(LogStatus.SKIP,"not verify  field value  on : "+M2_HSRPipeline2Name,YesNo.Yes);
		}
		}
		

		if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
			
			if(fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline2SourceFirm, 30)){
				WebElement ele1 = BP.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				log(LogStatus.INFO,"open created item"+M2_HSRPipeline2SourceFirm,YesNo.No);	
				

				for(String[] data:labelAndValue2){
					label = data[0];
					value =data[1];
				if(fp.fieldValueVerification(projectName, PageName.InstitutionsPage, PageLabel.valueOf(label), value, 30)){
					log(LogStatus.INFO,"successfully verify  field value  on : "+M2_HSRPipeline2SourceFirm,YesNo.No);	
					
				}else{
					sa.assertTrue(false,"not verify  field value  on : "+M2_HSRPipeline2SourceFirm);
					log(LogStatus.SKIP,"not verify  field value  on : "+M2_HSRPipeline2SourceFirm,YesNo.Yes);
				}
				}

			}else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+M2_HSRPipeline2SourceFirm);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+M2_HSRPipeline2SourceFirm,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
		
		
		if(fp.clickOnTab(environment,mode, TabName.Object2Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);
			
			if(fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline2SourceContact, 30)){
				WebElement ele1 = BP.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				log(LogStatus.INFO,"open created item"+M2_HSRPipeline2SourceContact,YesNo.No);	
				

				for(String[] data:labelAndValue2){
					label = data[0];
					value =data[1];
				if(fp.fieldValueVerification(projectName, PageName.ContactPage, PageLabel.valueOf(label), value, 30)){
					
					log(LogStatus.INFO,"successfully verify  field value  on : "+M2_HSRPipeline2SourceContact,YesNo.No);	
					
				}else{
					sa.assertTrue(false,"not verify  field value  on : "+M2_HSRPipeline2SourceContact);
					log(LogStatus.SKIP,"not verify  field value  on : "+M2_HSRPipeline2SourceContact,YesNo.Yes);
				}
				}

			}else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+M2_HSRPipeline1SourceContact);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+M2_HSRPipeline1SourceContact,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc038_ChangeStageDealReceivedToLOIAction(String projectName){
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=5;
		totalDealsshown=2;
		averageDealQualityScore=(parkedScore+dealQualityScore)/totalDealsshown;;
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.LOI.toString(),Stage.LOI.toString(),String.valueOf(dealQualityScore)};
		
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline2Name, 10)){
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
				sa.assertTrue(false,"not able to find pipeline "+M2_HSRPipeline2Name);
				log(LogStatus.SKIP,"not able to find pipeline "+M2_HSRPipeline2Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		
		if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
			
			if(fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline2SourceFirm, 30)){
				WebElement ele1 = BP.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				log(LogStatus.INFO,"open created item"+M2_HSRPipeline2SourceFirm,YesNo.No);	
				

				for (int i =0;i<labelName1.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName1[i],labelValues1[i])) {
						log(LogStatus.INFO,"successfully verified "+labelName1[i],YesNo.Yes);
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				
				}

			}else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+M2_HSRPipeline1SourceFirm);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+M2_HSRPipeline1SourceFirm,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
		
		
		if(fp.clickOnTab(environment,mode, TabName.Object2Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);
			
			if(fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline2SourceContact, 30)){
				WebElement ele1 = BP.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				log(LogStatus.INFO,"open created item"+M2_HSRPipeline2SourceContact,YesNo.No);	
				

				for (int i =0;i<labelName1.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName1[i],labelValues1[i])) {
						log(LogStatus.INFO,"successfully verified "+labelName1[i],YesNo.Yes);
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				
				}

			}else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+M2_HSRPipeline2SourceContact);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+M2_HSRPipeline2SourceContact,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}
		
		lp.CRMlogout();
		sa.assertAll();
	
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc039_ChangeStageLOIToParked_Action(String projectName){
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=5;
		totalDealsshown=2;
		averageDealQualityScore=(parkedScore+dealQualityScore)/totalDealsshown;
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.LOI.toString(),Stage.Parked.toString(),String.valueOf(dealQualityScore)};
		
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline2Name, 10)){
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
				sa.assertTrue(false,"not able to find pipeline "+M2_HSRPipeline2Name);
				log(LogStatus.SKIP,"not able to find pipeline "+M2_HSRPipeline2Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		
		if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
			
			if(fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline2SourceFirm, 30)){
				WebElement ele1 = BP.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				log(LogStatus.INFO,"open created item"+M2_HSRPipeline2SourceFirm,YesNo.No);	
				

				for (int i =0;i<labelName1.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName1[i],labelValues1[i])) {
						log(LogStatus.INFO,"successfully verified "+labelName1[i],YesNo.Yes);
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				
				}

			}else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+M2_HSRPipeline1SourceFirm);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+M2_HSRPipeline1SourceFirm,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
		
		
		if(fp.clickOnTab(environment,mode, TabName.Object2Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);
			
			if(fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline2SourceContact, 30)){
				WebElement ele1 = BP.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				log(LogStatus.INFO,"open created item"+M2_HSRPipeline2SourceContact,YesNo.No);	
				

				for (int i =0;i<labelName1.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName1[i],labelValues1[i])) {
						log(LogStatus.INFO,"successfully verified "+labelName1[i],YesNo.Yes);
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				
				}

			}else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+M2_HSRPipeline2SourceContact);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+M2_HSRPipeline2SourceContact,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}
		
		lp.CRMlogout();
		sa.assertAll();
	
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc040_ChangeStageParkedToDeclined_Action(String projectName){
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=5;
		totalDealsshown=2;
		averageDealQualityScore=(parkedScore+dealQualityScore)/totalDealsshown;;
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.LOI.toString(),Stage.DeclinedDead.toString(),String.valueOf(dealQualityScore)};
		
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline2Name, 10)){
				if (fp.changeStage(projectName, Stage.DeclinedDead.toString(), 10)) {
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele, "details", action.SCROLLANDBOOLEAN);
					log(LogStatus.INFO,"successfully changed stage to "+Stage.DeclinedDead,YesNo.Yes);
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
							log(LogStatus.INFO,"successfully verified "+labelName[i],YesNo.Yes);
						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}
					
					}
					
				}else {
					sa.assertTrue(false,"not able to change stage to "+Stage.DeclinedDead);
					log(LogStatus.SKIP,"not able to change stage to "+Stage.DeclinedDead,YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to find pipeline "+M2_HSRPipeline2Name);
				log(LogStatus.SKIP,"not able to find pipeline "+M2_HSRPipeline2Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		
		if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
			
			if(fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline2SourceFirm, 30)){
				WebElement ele1 = BP.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				log(LogStatus.INFO,"open created item"+M2_HSRPipeline2SourceFirm,YesNo.No);	
				

				for (int i =0;i<labelName1.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName1[i],labelValues1[i])) {
						log(LogStatus.INFO,"successfully verified "+labelName1[i],YesNo.Yes);
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				
				}

			}else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+M2_HSRPipeline1SourceFirm);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+M2_HSRPipeline1SourceFirm,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
		
		
		if(fp.clickOnTab(environment,mode, TabName.Object2Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);
			
			if(fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline2SourceContact, 30)){
				WebElement ele1 = BP.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				log(LogStatus.INFO,"open created item"+M2_HSRPipeline2SourceContact,YesNo.No);	
				

				for (int i =0;i<labelName1.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName1[i],labelValues1[i])) {
						log(LogStatus.INFO,"successfully verified "+labelName1[i],YesNo.Yes);
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				
				}

			}else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+M2_HSRPipeline2SourceContact);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+M2_HSRPipeline2SourceContact,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}
		
		lp.CRMlogout();
		sa.assertAll();
	
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc041_ChangeStageDeclinedToClosed_Action(String projectName){
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=5;
		totalDealsshown=2;
		averageDealQualityScore=(parkedScore+dealQualityScore)/totalDealsshown;;
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.Closed.toString(),Stage.Closed.toString(),String.valueOf(dealQualityScore)};
		
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline2Name, 10)){
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
					sa.assertTrue(false,"not able to change stage to "+Stage.Closed);
					log(LogStatus.SKIP,"not able to change stage to "+Stage.Closed,YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to find pipeline "+M2_HSRPipeline2Name);
				log(LogStatus.SKIP,"not able to find pipeline "+M2_HSRPipeline2Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		
		if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
			
			if(fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline2SourceFirm, 30)){
				WebElement ele1 = BP.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				log(LogStatus.INFO,"open created item"+M2_HSRPipeline2SourceFirm,YesNo.No);	
				

				for (int i =0;i<labelName1.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName1[i],labelValues1[i])) {
						log(LogStatus.INFO,"successfully verified "+labelName1[i],YesNo.Yes);
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				
				}

			}else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+M2_HSRPipeline1SourceFirm);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+M2_HSRPipeline1SourceFirm,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
		
		
		if(fp.clickOnTab(environment,mode, TabName.Object2Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);
			
			if(fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline2SourceContact, 30)){
				WebElement ele1 = BP.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				log(LogStatus.INFO,"open created item"+M2_HSRPipeline2SourceContact,YesNo.No);	
				

				for (int i =0;i<labelName1.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName1[i],labelValues1[i])) {
						log(LogStatus.INFO,"successfully verified "+labelName1[i],YesNo.Yes);
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
				
				}

			}else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+M2_HSRPipeline2SourceContact);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+M2_HSRPipeline2SourceContact,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc042_CreateInstitutionData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		ThreadSleep(5000);
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
				
				if (ip.createEntityOrAccount(projectName, mode, M2_HSRINS4Name, M2_HSRINS4RecordType, null, null, 20)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+M2_HSRINS4Name+" of record type : "+M2_HSRINS4RecordType,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+M2_HSRINS4Name+" of record type : "+M2_HSRINS4RecordType);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+M2_HSRINS4Name+" of record type : "+M2_HSRINS4RecordType,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
			}
		
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc043_CreateDealData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		ThreadSleep(5000);
		
		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
			
			if (ip.createEntityOrAccount(projectName, mode, M2_HSRINS5Name, M2_HSRINS5RecordType, null, null, 20)) {
				log(LogStatus.INFO,"successfully Created Account/Entity : "+M2_HSRINS5Name+" of record type : "+M2_HSRINS5RecordType,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Account/Entity : "+M2_HSRINS5Name+" of record type : "+M2_HSRINS5RecordType);
				log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+M2_HSRINS5Name+" of record type : "+M2_HSRINS5RecordType,YesNo.Yes);
			}


		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
	
		
		//deal	
		String[][] otherlabel={{excelLabel.Source_Firm.toString(),M2_HSRPipeline3SourceFirm},{excelLabel.Source_Contact.toString(),M2_HSRPipeline3SourceContact}};
		if(fp.clickOnTab(environment,mode, TabName.DealTab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.DealTab,YesNo.No);
			
			if(fp.createDeal(projectName, "", M2_HSRPipeline3Name, M2_HSRPipeline3Company, M2_HSRPipeline3Stage, otherlabel, 30)){
				
				log(LogStatus.INFO,"successfully Created deal : "+M2_HSRPipeline3Name,YesNo.No);	

			}else{
				
				sa.assertTrue(false,"Not Able to Create deal : "+M2_HSRPipeline3Name);
				log(LogStatus.SKIP,"Not Able to Create deal : "+M2_HSRPipeline3Name,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.DealTab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.DealTab,YesNo.Yes);
		}
		
		dealQualityScore=5;
		totalDealsshown=1;
		averageDealQualityScore=dealQualityScore/totalDealsshown;
		String label="";
		String value="";
		String[][] labelAndValue={{excelLabel.Highest_Stage_Reached.toString(),M2_HSRPipeline3Stage},{excelLabel.Deal_Quality_Score.toString(),String.valueOf(dealQualityScore)}};
		refresh(driver);
		for(String[] data:labelAndValue){
			label = data[0];
			value =data[1];
			refresh(driver);
		if(fp.fieldValueVerification(projectName, PageName.DealPage, PageLabel.valueOf(label), value, 30)){
			
			log(LogStatus.INFO,"successfully verify  field value  on : "+M2_HSRPipeline3Name,YesNo.No);	

		}else{
			sa.assertTrue(false,"not verify  field value  on : "+M2_HSRPipeline3Name);
			log(LogStatus.SKIP,"not verify  field value  on : "+M2_HSRPipeline3Name,YesNo.Yes);
		}
		}
		
	
		String[][] labelAndValue2={{excelLabel.Average_Deal_Quality_Score.toString(),String.valueOf(averageDealQualityScore)},{excelLabel.Total_Deals_Shown.toString(),String.valueOf(totalDealsshown)}};

		if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
			
			
				if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
						M2_HSRPipeline3SourceFirm, 30))
				{
				log(LogStatus.INFO,"open created item"+M2_HSRPipeline3SourceFirm,YesNo.No);	
				ThreadSleep(8000);
				refresh(driver);
				for(String[] data:labelAndValue2){
					label = data[0];
				value =data[1];
				if(fp.fieldValueVerification(projectName, PageName.InstitutionsPage, PageLabel.valueOf(label), value, 30)){
					log(LogStatus.INFO,"successfully verify  field value  on : "+M2_HSRPipeline3SourceFirm,YesNo.No);	
					
				}else{
					sa.assertTrue(false,"not verify  field value  on : "+M2_HSRPipeline3SourceFirm);
					log(LogStatus.SKIP,"not verify  field value  on : "+M2_HSRPipeline3SourceFirm,YesNo.Yes);
				}
				}

			}else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+M2_HSRPipeline3SourceFirm);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+M2_HSRPipeline3SourceFirm,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
		totalDealsshown=3;
		averageDealQualityScore=(parkedScore+closedScore+dealQualityScore)/totalDealsshown;
		String[][] labelAndValue3={{excelLabel.Average_Deal_Quality_Score.toString(),"5.0"},{excelLabel.Total_Deals_Shown.toString(),"3"}};
		
		if(fp.clickOnTab(environment,mode, TabName.Object2Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);
			refresh(driver);
		
				if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
						M2_HSRPipeline3SourceContact, 30)) {
				log(LogStatus.INFO,"open created item"+M2_HSRPipeline3SourceContact,YesNo.No);	
				

				for(String[] data:labelAndValue3){
				label = data[0];
				value =data[1];
				if(fp.fieldValueVerification(projectName, PageName.DealPage, PageLabel.valueOf(label), value, 30)){
					
					log(LogStatus.INFO,"successfully verify  field value  on : "+M2_HSRPipeline3SourceContact,YesNo.No);	
					
				}else{
					sa.assertTrue(false,"not verify  field value  on : "+M2_HSRPipeline3SourceContact);
					log(LogStatus.SKIP,"not verify  field value  on : "+M2_HSRPipeline3SourceContact,YesNo.Yes);
				}
				}

			}else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+M2_HSRPipeline3SourceContact);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+M2_HSRPipeline3SourceContact,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc044_HighestStageReachedDeletePipeline_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline1Name, 10)) {
				cp.clickOnShowMoreDropdownOnly(projectName, PageName.Object4Page,"");
				log(LogStatus.INFO,"Able to Click on Show more Icon : "+TabName.Object4Tab+" For : "+M2_HSRPipeline1Name,YesNo.No);
				ThreadSleep(500);
				WebElement ele = cp.actionDropdownElement(projectName, PageName.Object4Page, ShowMoreActionDropDownList.Delete, 15);
				 if (ele==null) {
					 ele =cp.getDeleteButton(projectName, 30);
				} 
				
				 if (click(driver, ele, "Delete More Icon", action.BOOLEAN)) {
					log(LogStatus.INFO,"Able to Click on Delete more Icon : "+TabName.Object4Tab+" For : "+M2_HSRPipeline1Name,YesNo.No);
					ThreadSleep(1000);
					if (click(driver, cp.getDeleteButtonOnDeletePopUp(projectName, 30), "Delete Button", action.BOOLEAN)) {
						log(LogStatus.INFO,"Able to Click on Delete button on Delete PoPup : "+TabName.Object4Tab+" For : "+M2_HSRPipeline1Name,YesNo.No);
						ThreadSleep(10000);
						if (cp.clickOnTab(projectName, TabName.Object4Tab)) {
							log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object4Tab+" For : "+M2_HSRPipeline1Name,YesNo.No);
							ThreadSleep(1000);
							if (!cp.clickOnAlreadyCreatedItem(projectName, TabName.Object4Tab, M2_HSRPipeline1Name, 10)) {
								log(LogStatus.INFO,"Item has been Deleted after delete operation  : "+M2_HSRPipeline1Name+" For : "+TabName.Object4Tab,YesNo.No);

							}else {
								sa.assertTrue(false,"Item has not been Deleted after delete operation  : "+M2_HSRPipeline1Name+" For : "+TabName.Object4Tab);
								log(LogStatus.SKIP,"Item has not been Deleted after delete operation  : "+M2_HSRPipeline1Name+" For : "+TabName.Object4Tab,YesNo.Yes);
							}

						}else {
							sa.assertTrue(false,"Not Able to Click on Tab after delete : "+TabName.Object4Tab+" For : "+M2_HSRPipeline1Name);
							log(LogStatus.SKIP,"Not Able to Click on Tab after delete : "+TabName.Object4Tab+" For : "+M2_HSRPipeline1Name,YesNo.Yes);	
						}
					}else {
						log(LogStatus.INFO,"not able to click on delete button, so not deleted : "+TabName.Object4Tab+" For : "+M2_HSRPipeline1Name,YesNo.No);
						sa.assertTrue(false,"not able to click on delete button, so not deleted : "+TabName.Object4Tab+" For : "+M2_HSRPipeline1Name);
					}
				 }else {
					 log(LogStatus.INFO,"not Able to Click on Delete more Icon : "+TabName.Object4Tab+" For : "+M2_HSRPipeline1Name,YesNo.No);
					 sa.assertTrue(false,"not Able to Click on Delete more Icon : "+TabName.Object4Tab+" For : "+M2_HSRPipeline1Name);
				 }
			}else {
				 log(LogStatus.INFO,"not Able to Click on "+M2_HSRPipeline1Name,YesNo.No);
				 sa.assertTrue(false,"not Able to Click on "+M2_HSRPipeline1Name);
			 }
		}else {
			 log(LogStatus.INFO,"not Able to Click on "+TabName.Object4Tab,YesNo.No);
			 sa.assertTrue(false,"not Able to Click on "+TabName.Object4Tab);
		 }
		
		
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		
		
		String temp[];
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={M2_HSRINS1Name,M2_HSRContact1FName+" "+M2_HSRContact1LName};
		int j=0;
		WebElement ele;
		for (TabName tab:tabName) {
		if (lp.clickOnTab(projectName, tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
				if (j==0){
					totalDealsshown=1;
					averageDealQualityScore=closedScore/totalDealsshown;
					String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
					temp=labelValues1;
				}else{
					totalDealsshown=2;
					averageDealQualityScore=(closedScore+loiScore)/totalDealsshown;
					String labelValues2[]={String.valueOf(averageDealQualityScore),"2"};
					temp=labelValues2;
				}
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
	public void M2tc045_1_HighestStageReachedRestorePipeline_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
	
		
		TabName tabName = TabName.RecycleBinTab;
		String name = M2_HSRPipeline1Name;
		
		if (lp.restoreValueFromRecycleBin(projectName, name)) {
			log(LogStatus.INFO,"Able to restore item from Recycle Bin "+name,YesNo.Yes);
			
		} else {
			sa.assertTrue(false,"Not Able to restore item from Recycle Bin "+name);
			log(LogStatus.SKIP,"Not Able to restore item from Recycle Bin "+name,YesNo.Yes);

		}

		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc045_2_HighestStageReachedRestorePipeline_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		totalDealsshown=2;
		dealQualityScore=5;
		averageDealQualityScore=(parkedScore+closedScore)/totalDealsshown;
		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString(),excelLabel.Deal_Quality_Score.toString()};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		
		String labelValues[]={Stage.Due_Diligence.toString(),Stage.Parked.toString(),String.valueOf(dealQualityScore)};

		String temp[];
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={M2_HSRINS1Name,M2_HSRContact1FName+" "+M2_HSRContact1LName};
		int j=0;
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline1Name, 10)){
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
							log(LogStatus.PASS,"successfully verified "+labelName[i],YesNo.No);
							
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
				sa.assertTrue(false,"Not Able to click "+M2_HSRPipeline1Name);
				log(LogStatus.SKIP,"Not Able to click "+M2_HSRPipeline1Name,YesNo.Yes);
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
						if (j==0){
							totalDealsshown=2;
							averageDealQualityScore=(parkedScore+closedScore)/totalDealsshown;
							String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
							temp=labelValues1;
						}
							
						else{
							
							totalDealsshown=3;
							averageDealQualityScore=(parkedScore+closedScore+loiScore)/totalDealsshown;
							String labelValues2[]={String.valueOf(dealQualityScore),String.valueOf(totalDealsshown)};

							temp=labelValues2;
						}
						for (int i =0;i<labelName1.length;i++) {
							if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],temp[i])) {
								log(LogStatus.PASS,"successfully verified "+labelName1[i],YesNo.No);

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
	public void M2tc046_HighestStageReachedUpdateStageNames(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
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
									ThreadSleep(4000);
									switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
									WebElement ele=sp.clickOnEditInFrontOfFieldValues(projectName, newAndOldStage[i][0]);
									if (click(driver, ele, "watchlist", action.BOOLEAN)) {
										switchToDefaultContent(driver);
										ThreadSleep(3000);
										switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
										sendKeys(driver, sp.getFieldLabelTextBox1(10), newAndOldStage[i][1], "label", action.BOOLEAN);
											

										if (clickUsingJavaScript(driver, fp.getCustomTabSaveBtn(10), "save", action.BOOLEAN)) {

											log(LogStatus.INFO, "successfully changed watchlist label", YesNo.No);
											ThreadSleep(10000);
											switchToDefaultContent(driver);
											refresh(driver);
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
								ThreadSleep(4000);
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
	public void M2tc047_CreateContactData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String mailID = "";
		ThreadSleep(5000);
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			mailID = lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, mailID, "Contacts", excelLabel.Variable_Name, "M2HSRCON2",
					excelLabel.Contact_EmailId);

			if (cp.createContact(projectName, M2_HSRContact2FName, M2_HSRContact2LName, M2_HSRContact2Ins, mailID, "",
					null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO, "successfully Created Contact : " + M2_HSRContact2FName + " " + M2_HSRContact2LName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Create Contact : " + M2_HSRContact2FName + " " + M2_HSRContact2LName);
				log(LogStatus.SKIP, "Not Able to Create Contact: " + M2_HSRContact2FName + " " + M2_HSRContact2LName,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object2Tab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object2Tab, YesNo.Yes);
		}
			
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc048_CreateDealData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		ThreadSleep(5000);
		
		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
			
			if (ip.createEntityOrAccount(projectName, mode, M2_HSRINS6Name, M2_HSRINS6RecordType, null, null, 20)) {
				log(LogStatus.INFO,"successfully Created Account/Entity : "+M2_HSRINS6Name+" of record type : "+M2_HSRINS6RecordType,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Account/Entity : "+M2_HSRINS6Name+" of record type : "+M2_HSRINS6RecordType);
				log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+M2_HSRINS6Name+" of record type : "+M2_HSRINS6RecordType,YesNo.Yes);
			}


		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
	
		
		//deal	
		String[][] otherlabel={{excelLabel.Source_Firm.toString(),M2_HSRPipeline4SourceFirm},{excelLabel.Source_Contact.toString(),M2_HSRPipeline4SourceContact}};
		if(fp.clickOnTab(environment,mode, TabName.DealTab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.DealTab,YesNo.No);
			
			if(fp.createDeal(projectName, "", M2_HSRPipeline4Name, M2_HSRPipeline4Company, M2_HSRPipeline4Stage, otherlabel, 30)){
				
				log(LogStatus.INFO,"successfully Created deal : "+M2_HSRPipeline4Name,YesNo.No);	

			}else{
				
				sa.assertTrue(false,"Not Able to Create deal : "+M2_HSRPipeline4Name);
				log(LogStatus.SKIP,"Not Able to Create deal : "+M2_HSRPipeline4Name,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.DealTab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.DealTab,YesNo.Yes);
		}
	
		dealQualityScore=1;
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString(),excelLabel.Deal_Quality_Score.toString()};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		
		String labelValues[]={Stage.NonDisclosureAgreement.toString(),Stage.NonDisclosureAgreement.toString(),String.valueOf(dealQualityScore)};

		String temp[];
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={M2_HSRPipeline4SourceFirm,M2_HSRPipeline4SourceContact};
		int j=0;
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline4Name, 10)){
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
							log(LogStatus.PASS,"successfully verified "+labelName[i],YesNo.No);
							
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
				sa.assertTrue(false,"Not Able to click "+M2_HSRPipeline4Name);
				log(LogStatus.SKIP,"Not Able to click "+M2_HSRPipeline4Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		for (TabName tab:tabName) {
			if (lp.clickOnTab(projectName, tab)) {
				if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
					refresh(driver);
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
						if (j==0){
							totalDealsshown=2;
							averageDealQualityScore =(loiScore+dealQualityScore)/totalDealsshown;
							String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
							temp=labelValues1;
						}else{
							totalDealsshown=1;
							averageDealQualityScore =dealQualityScore/totalDealsshown;
							String labelValues2[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};

							temp=labelValues2;
						
						}
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
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc049_HighestStageReachedCreateInstitutionData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		ThreadSleep(5000);
		String name="";
		String type ="";
		String[][] data = {{M2_HSRINS7Name,M2_HSRINS7RecordType},
							{M2_HSRINS8Name,M2_HSRINS8RecordType},
							{M2_HSRINS9Name,M2_HSRINS9RecordType},
							{M2_DQSINS4Name,M2_DQSINS4RecordType},
							{M2_DQSINS5Name,M2_DQSINS5RecordType},
							{M2_DQSINS6Name,M2_DQSINS6RecordType}};
		
		for(String[] value:data){
			name=value[0];
			type =value[1];
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
				
				if (ip.createEntityOrAccount(projectName, mode, name, type, null, null, 20)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+name+" of record type : "+type,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+name+" of record type : "+type);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+name+" of record type : "+type,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
			}
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M2tc050_CreateContactData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String mailID = "";
		ThreadSleep(5000);
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			mailID = lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, mailID, "Contacts", excelLabel.Variable_Name, "M2HSRCON3",
					excelLabel.Contact_EmailId);

			if (cp.createContact(projectName, M2_HSRContact3FName, M2_HSRContact3LName, M2_HSRContact3Ins, mailID, "",
					null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO, "successfully Created Contact : " + M2_HSRContact3FName + " " + M2_HSRContact3LName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Create Contact : " + M2_HSRContact3FName + " " + M2_HSRContact3LName);
				log(LogStatus.SKIP, "Not Able to Create Contact: " + M2_HSRContact3FName + " " + M2_HSRContact3LName,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object2Tab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object2Tab, YesNo.Yes);
		}
			
		
		refresh(driver);
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			mailID = lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, mailID, "Contacts", excelLabel.Variable_Name, "M2DQSCON3",
					excelLabel.Contact_EmailId);

			if (cp.createContact(projectName, M2_DQSContact3FName, M2_DQSContact3LName, M2_DQSContact3Ins, mailID, "",
					null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO, "successfully Created Contact : " + M2_DQSContact3FName + " " + M2_DQSContact3LName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Create Contact : " + M2_DQSContact3FName + " " + M2_DQSContact3LName);
				log(LogStatus.SKIP, "Not Able to Create Contact: " + M2_DQSContact3FName + " " + M2_DQSContact3LName,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object2Tab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object2Tab, YesNo.Yes);
		}
			
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M2tc051_CreateDealData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		ThreadSleep(5000);
		//deal	
		String[][] otherlabel={{excelLabel.Source_Firm.toString(),M2_HSRPipeline5SourceFirm},{excelLabel.Source_Contact.toString(),M2_HSRPipeline5SourceContact}};
		if(fp.clickOnTab(environment,mode, TabName.DealTab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.DealTab,YesNo.No);
			
			if(fp.createDeal(projectName, "", M2_HSRPipeline5Name, M2_HSRPipeline5Company, M2_HSRPipeline5Stage, otherlabel, 30)){
				
				log(LogStatus.INFO,"successfully Created deal : "+M2_HSRPipeline5Name,YesNo.No);	

			}else{
				
				sa.assertTrue(false,"Not Able to Create deal : "+M2_HSRPipeline5Name);
				log(LogStatus.SKIP,"Not Able to Create deal : "+M2_HSRPipeline5Name,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.DealTab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.DealTab,YesNo.Yes);
		}
	
		totalDealsshown=1;
		dealQualityScore=5;
		averageDealQualityScore=dealQualityScore/totalDealsshown;
		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString(),excelLabel.Deal_Quality_Score.toString()};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		
		String labelValues[]={Stage.LOI.toString(),Stage.LOI.toString(),String.valueOf(dealQualityScore)};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};

		String temp[];
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={M2_HSRPipeline5SourceFirm,M2_HSRPipeline5SourceContact};
		int j=0;
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline5Name, 10)){
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
				sa.assertTrue(false,"Not Able to click "+M2_HSRPipeline5Name);
				log(LogStatus.SKIP,"Not Able to click "+M2_HSRPipeline5Name,YesNo.Yes);
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
						temp=labelValues1;
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
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
		
	@Parameters({ "projectName"})
	@Test
	public void M2tc052_DQSVerifyConvertToPortfolio(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (dp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline5Name, 10)) {
				
				if (bp.clickOnShowMoreActionDownArrow(projectName, PageName.Object4Page, ShowMoreActionDropDownList.convertToPortfolio, 10)) {
					
					if (dp.getconvertToPortfolioMessage(M2_HSRPipeline5Company,10)!=null) {
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
				sa.assertTrue(false,"not able to click on "+M2_HSRPipeline5Name);
				log(LogStatus.SKIP,"not able to click on "+M2_HSRPipeline5Name,YesNo.Yes);
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
	public void M2tc053_DQSImpactConvertToPortfolio(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		dealQualityScore=5;
		totalDealsshown=1;
		averageDealQualityScore=dealQualityScore/totalDealsshown;
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.Closed.toString(),Stage.Closed.toString(),String.valueOf(dealQualityScore)};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		ExcelUtils.writeData(phase1DataSheetFilePath, Stage.Closed.toString(), "Deal", excelLabel.Variable_Name, "M2HSRPIP5",excelLabel.Stage);
		WebElement ele;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (dp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline5Name, 10)) {
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
				sa.assertTrue(false,"Not Able to click "+M2_HSRPipeline5Name);
				log(LogStatus.SKIP,"Not Able to click "+M2_HSRPipeline5Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={M2_HSRPipeline5SourceFirm,M2_HSRPipeline5SourceContact};
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
	public void M2tc054_1_DQSCreateDealAssociatedWithInstitution_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword,appName);
			
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object4Tab,YesNo.No);	
			String[][] otherLabels = {{excelLabel.Source_Contact.toString(),M2_DQSPipeline6SourceContact},{excelLabel.Source_Firm.toString(),M2_DQSPipeline6SourceFirm}};
			refresh(driver);
			ThreadSleep(3000);
			if (fp.createDeal(projectName,"",M2_DQSPipeline6Name, M2_DQSPipeline6Company, M2_DQSPipeline6Stage,otherLabels, 15)) {
				log(LogStatus.INFO,"Created Deal : "+M2_DQSPipeline6Name,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Deal  : "+M2_DQSPipeline6Name);
				log(LogStatus.SKIP,"Not Able to Create Deal  : "+M2_DQSPipeline6Name,YesNo.Yes);
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
	public void M2tc054_2_DQSCreateDealAssociatedWithInstitution_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		dealQualityScore=5;
		totalDealsshown=2;
		averageDealQualityScore=dealQualityScore;
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.LOI.toString(),Stage.LOI.toString(),String.valueOf(dealQualityScore)};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		WebElement ele;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (dp.clickOnAlreadyCreatedItem(projectName, M2_DQSPipeline6Name, 10)) {
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
							log(LogStatus.PASS,"successfully verified "+labelName[i],YesNo.No);
							
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
				sa.assertTrue(false,"Not Able to click "+M2_DQSPipeline6Name);
				log(LogStatus.SKIP,"Not Able to click "+M2_DQSPipeline6Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={M2_DQSPipeline6SourceFirm,M2_DQSPipeline6SourceContact};
		int j=0;
		for (TabName tab:tabName) {
			if (lp.clickOnTab(projectName, tab)) {
				if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
						if (j==1) {
							for (int i =0;i<labelName1.length;i++) {
								if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
									log(LogStatus.PASS,"successfully verified "+labelName1[i],YesNo.No);

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
									log(LogStatus.PASS,"successfully verified absence of "+labelName1[i],YesNo.No);
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
	public void M2tc055_DQSPostCondition(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
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
									ThreadSleep(4000);
									switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
									ThreadSleep(3000);
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
								ThreadSleep(4000);
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
		
		String updateLabel= PageLabel.Watchlist.toString();
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject(environment, mode,object.Override)){
					log(LogStatus.INFO, "click on Object : " +object.valueOf("Override"), YesNo.No);
					ThreadSleep(2000);				
					switchToFrame(driver, 30, sp.getSetUpPageIframe(60));
					ThreadSleep(5000);	
					if(selectVisibleTextFromDropDown(driver, sp.getOverrideSetupComponentDropdown(10), "Override setup component dropdown", "Custom Field")){
						log(LogStatus.INFO, "Select custom field text in setup component dropdown in override setup page", YesNo.No);
						ThreadSleep(5000);	
						
						if(selectVisibleTextFromDropDown(driver, sp.getOverrideObjectDropdown(10), "Override object dropdown",PageLabel.Activity.toString())){
							log(LogStatus.INFO, "Select "+PageLabel.Activity.toString()+" text in object dropdown in override setup page", YesNo.No);
							ThreadSleep(5000);
							if(sp.updateFieldLabelInOverridePage(driver, PageLabel.Watch_list.toString(), updateLabel, action.SCROLLANDBOOLEAN)){
								log(LogStatus.INFO, "Field label: "+PageLabel.Watch_list.toString()+" successfully update to "+updateLabel, YesNo.No);
								
							}else{
								log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Watch_list.toString()+" successfully update to "+updateLabel, YesNo.Yes);
								sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Watch_list.toString()+" to "+updateLabel);	
							}
						}else{
							log(LogStatus.ERROR, "Not able to select text: "+PageLabel.Activity.toString()+" in  object dropdown in override page", YesNo.Yes);
							sa.assertTrue(false, "Not able to select text: "+PageLabel.Activity.toString()+" in  object dropdown in override page");
						}
					}else{
						log(LogStatus.ERROR, "Not able to select text: Custom Field in  setup component dropdown in override page", YesNo.Yes);
						sa.assertTrue(false, "Not able to select text: Custom Field in  setup component dropdown in override page");
					}
				
			}else{
				
				log(LogStatus.PASS, "Not able to click on Object : " + object.valueOf("Override"), YesNo.Yes);
				sa.assertTrue(false, "Not able to click on Object : " + object.valueOf("Override"));
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
	
	
	
	
	
	
	
	
	
	
/*	
	// Deal Quality Score
	 
	@Parameters({"projectName"})

	
	
	
	
	//@Test
	public void M2Tc001_DQScreateCRMUser(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(crmUser2LastName);
		String UserLastName = splitedUserLastName[0] + lp.generateRandomNumber();
		String emailId = lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User2",excelLabel.User_Last_Name);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		boolean flag = false;
		for (int i = 0; i < 3; i++) {
			try {
				if (home.clickOnSetUpLink()) {
					flag = true;
					parentWindow = switchOnWindow(driver);
					if (parentWindow == null) {
						sa.assertTrue(false,
								"No new window is open after click on setup link in lighting mode so cannot create CRM User2");
						log(LogStatus.SKIP,
								"No new window is open after click on setup link in lighting mode so cannot create CRM User2",
								YesNo.Yes);
						exit("No new window is open after click on setup link in lighting mode so cannot create CRM User2");
					}
					if (setup.createPEUser( crmUser2FirstName, UserLastName, emailId, crmUserLience,
							crmUserProfile)) {
						log(LogStatus.INFO, "CRM User is created Successfully: " + crmUser2FirstName + " " + UserLastName, YesNo.No);
						ExcelUtils.writeData(testCasesFilePath, emailId, "Users", excelLabel.Variable_Name, "User2",
								excelLabel.User_Email);
						ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User2",
								excelLabel.User_Last_Name);
						flag = true;
						break;

					}
					driver.close();
					driver.switchTo().window(parentWindow);

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
			}

		}
		if (flag) {
			if(!environment.equalsIgnoreCase(Environment.Sandbox.toString())) {
				if (setup.installedPackages(crmUser2FirstName, UserLastName)) {
					appLog.info("PE Package is installed Successfully in CRM User: " + crmUser2FirstName + " "
							+ UserLastName);

				} else {
					appLog.error(
							"Not able to install PE package in CRM User1: " + crmUser2FirstName + " " + UserLastName);
					sa.assertTrue(false,
							"Not able to install PE package in CRM User1: " + crmUser2FirstName + " " + UserLastName);
					log(LogStatus.ERROR,
							"Not able to install PE package in CRM User1: " + crmUser2FirstName + " " + UserLastName,
							YesNo.Yes);
				}
			}
			

		}else{

			log(LogStatus.ERROR, "could not click on setup link, test case fail", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link, test case fail");

		}

		lp.CRMlogout();
		closeBrowser();
		config(ExcelUtils.readDataFromPropertyFile("Browser"));
		lp = new LoginPageBusinessLayer(driver);
		String passwordResetLink=null;
		try {
			passwordResetLink = new EmailLib().getResetPasswordLink("passwordreset",
					ExcelUtils.readDataFromPropertyFile("gmailUserName"),
					ExcelUtils.readDataFromPropertyFile("gmailPassword"));
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		appLog.info("ResetLinkIs: " + passwordResetLink);
		driver.get(passwordResetLink);
		if (lp.setNewPassword()) {
			appLog.info("Password is set successfully for CRM User2: " + crmUser2FirstName + " " + UserLastName );
		} else {
			appLog.info("Password is not set for CRM User2: " + crmUser2FirstName + " " + UserLastName);
			sa.assertTrue(false, "Password is not set for CRM User2: " + crmUser2FirstName + " " + UserLastName);
			log(LogStatus.ERROR, "Password is not set for CRM User2: " + crmUser2FirstName + " " + UserLastName,
					YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	//@Test
	public void M2tc002_DQSCreateInstitutionAndContactData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String name ="";
		String type ="";
		String[][] data ={{M2_DQSINS1Name, M2_DQSINS1RecordType},{M2_DQSINS2Name, M2_DQSINS2RecordType}};
		ThreadSleep(5000);
		for(String[] value:data){
			name=value[0];
			type= value[1];
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
				
				if (ip.createEntityOrAccount(projectName, mode, name, type, null, null, 20)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+name+" of record type : "+type,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+name+" of record type : "+type);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+name+" of record type : "+type,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
			}
		}
		
			String mailID = "";
			ThreadSleep(5000);
			if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

				mailID = lp.generateRandomEmailId(gmailUserName);
				ExcelUtils.writeData(phase1DataSheetFilePath, mailID, "Contacts", excelLabel.Variable_Name, "M2DQSCON1",
						excelLabel.Contact_EmailId);

				if (cp.createContact(projectName, M2_DQSContact1FName, M2_DQSContact1LName, M2_DQSContact1Ins, mailID, "",
						null, null, CreationPage.ContactPage, null, null)) {
					log(LogStatus.INFO, "successfully Created Contact : " + M2_DQSContact1FName + " " + M2_DQSContact1LName,
							YesNo.No);
				} else {
					sa.assertTrue(false, "Not Able to Create Contact : " + M2_DQSContact1FName + " " + M2_DQSContact1LName);
					log(LogStatus.SKIP, "Not Able to Create Contact: " + M2_DQSContact1FName + " " + M2_DQSContact1LName,
							YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object2Tab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object2Tab, YesNo.Yes);
			}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	//@Test
	public void M2tc003_DQSCreateDealData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		ThreadSleep(5000);
		
		//deal	
		String[][] otherlabel={{excelLabel.Source_Firm.toString(),M2_DQSPipeline1SourceFirm},{excelLabel.Source_Contact.toString(),M2_DQSPipeline1SourceContact}};
		if(fp.clickOnTab(environment,mode, TabName.DealTab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.DealTab,YesNo.No);
			
			if(fp.createDeal(projectName, "", M2_DQSPipeline1Name, M2_DQSPipeline1Company, M2_DQSPipeline1Stage, otherlabel, 30)){
				
				log(LogStatus.INFO,"successfully Created deal : "+M2_DQSPipeline1Name,YesNo.No);	

			}else{
				
				sa.assertTrue(false,"Not Able to Create deal : "+M2_DQSPipeline1Name);
				log(LogStatus.SKIP,"Not Able to Create deal : "+M2_DQSPipeline1Name,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.DealTab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.DealTab,YesNo.Yes);
		}
	
		totalDealsshown=1;
		dealQualityScore=1;
		averageDealQualityScore=dealQualityScore/totalDealsshown;
		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString(),excelLabel.Deal_Quality_Score.toString()};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		
		String labelValues[]={Stage.Deal_Received.toString(),Stage.Deal_Received.toString(),String.valueOf(dealQualityScore)};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		String labelValues2[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};

		String temp[];
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={M2_DQSPipeline1Company,M2_DQSPipeline1SourceContact};
		int j=0;
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_DQSPipeline1Name, 10)){
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
				sa.assertTrue(false,"Not Able to click "+M2_DQSPipeline1Name);
				log(LogStatus.SKIP,"Not Able to click "+M2_DQSPipeline1Name,YesNo.Yes);
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
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	//@Test
	public void M2tc004_DQSChangeStageDealReceivedToNDASignedAction(String projectName){
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		totalDealsshown=1;
		dealQualityScore=1;
		averageDealQualityScore=dealQualityScore/totalDealsshown;
		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString(),excelLabel.Deal_Quality_Score.toString()};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		
		String labelValues[]={Stage.NDA_Signed.toString(),Stage.NDA_Signed.toString(),String.valueOf(dealQualityScore)};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		String labelValues2[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};

		String temp[];
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={M2_DQSPipeline1SourceFirm,M2_DQSPipeline1SourceContact};
		int j=0;
		WebElement ele;
		
		//change stage
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_DQSPipeline1Name, 10)){
				if (fp.changeStage(projectName, Stage.LOI.toString(), 10)) {
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele, "details", action.SCROLLANDBOOLEAN);
					log(LogStatus.INFO,"successfully changed stage to "+Stage.LOI,YesNo.Yes);
				}else {
					sa.assertTrue(false,"not able to change stage to "+Stage.LOI);
					log(LogStatus.SKIP,"not able to change stage to "+Stage.LOI,YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to find pipeline "+M2_DQSPipeline1Name);
				log(LogStatus.SKIP,"not able to find pipeline "+M2_DQSPipeline1Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		
		//verify changes on deal
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_DQSPipeline1Name, 10)){
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
				sa.assertTrue(false,"Not Able to click "+M2_DQSPipeline1Name);
				log(LogStatus.SKIP,"Not Able to click "+M2_DQSPipeline1Name,YesNo.Yes);
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
	//@Test
	public void M2tc005_DQSChangeStageNDASignedToManagementMeetingAction(String projectName){
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		totalDealsshown=1;
		dealQualityScore=3;
		averageDealQualityScore=dealQualityScore/totalDealsshown;
		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString(),excelLabel.Deal_Quality_Score.toString()};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		
		String labelValues[]={Stage.Management_Meeting.toString(),Stage.Management_Meeting.toString(),String.valueOf(dealQualityScore)};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		String labelValues2[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};

		String temp[];
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={M2_DQSPipeline1SourceFirm,M2_DQSPipeline1SourceContact};
		int j=0;
		WebElement ele;
		
		//change stage
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_DQSPipeline1Name, 10)){
				if (fp.changeStage(projectName, Stage.LOI.toString(), 10)) {
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele, "details", action.SCROLLANDBOOLEAN);
					log(LogStatus.INFO,"successfully changed stage to "+Stage.LOI,YesNo.Yes);
				}else {
					sa.assertTrue(false,"not able to change stage to "+Stage.LOI);
					log(LogStatus.SKIP,"not able to change stage to "+Stage.LOI,YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to find pipeline "+M2_DQSPipeline1Name);
				log(LogStatus.SKIP,"not able to find pipeline "+M2_DQSPipeline1Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		
		//verify changes on deal
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_DQSPipeline1Name, 10)){
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
				sa.assertTrue(false,"Not Able to click "+M2_DQSPipeline1Name);
				log(LogStatus.SKIP,"Not Able to click "+M2_DQSPipeline1Name,YesNo.Yes);
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
	//@Test
	public void M2tc006_DQSChangeStageManagementMeetingToIOIAction(String projectName){
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		totalDealsshown=1;
		dealQualityScore=3;
		averageDealQualityScore=dealQualityScore/totalDealsshown;
		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString(),excelLabel.Deal_Quality_Score.toString()};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		
		String labelValues[]={Stage.IOI.toString(),Stage.IOI.toString(),String.valueOf(dealQualityScore)};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		String labelValues2[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};

		String temp[];
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={M2_DQSPipeline1SourceFirm,M2_DQSPipeline1SourceContact};
		int j=0;
		WebElement ele;
		
		//change stage
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_DQSPipeline1Name, 10)){
				if (fp.changeStage(projectName, Stage.LOI.toString(), 10)) {
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele, "details", action.SCROLLANDBOOLEAN);
					log(LogStatus.INFO,"successfully changed stage to "+Stage.LOI,YesNo.Yes);
				}else {
					sa.assertTrue(false,"not able to change stage to "+Stage.LOI);
					log(LogStatus.SKIP,"not able to change stage to "+Stage.LOI,YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to find pipeline "+M2_DQSPipeline1Name);
				log(LogStatus.SKIP,"not able to find pipeline "+M2_DQSPipeline1Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		
		//verify changes on deal
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_DQSPipeline1Name, 10)){
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
				sa.assertTrue(false,"Not Able to click "+M2_DQSPipeline1Name);
				log(LogStatus.SKIP,"Not Able to click "+M2_DQSPipeline1Name,YesNo.Yes);
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
	//@Test
	public void M2tc007_DQSChangeStageIOIToLOIAction(String projectName){
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		totalDealsshown=1;
		dealQualityScore=5;
		averageDealQualityScore=dealQualityScore/totalDealsshown;
		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString(),excelLabel.Deal_Quality_Score.toString()};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		
		String labelValues[]={Stage.LOI.toString(),Stage.LOI.toString(),String.valueOf(dealQualityScore)};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		String labelValues2[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};

		String temp[];
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={M2_DQSPipeline1SourceFirm,M2_DQSPipeline1SourceContact};
		int j=0;
		WebElement ele;
		
		//change stage
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_DQSPipeline1Name, 10)){
				if (fp.changeStage(projectName, Stage.LOI.toString(), 10)) {
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele, "details", action.SCROLLANDBOOLEAN);
					log(LogStatus.INFO,"successfully changed stage to "+Stage.LOI,YesNo.Yes);
				}else {
					sa.assertTrue(false,"not able to change stage to "+Stage.LOI);
					log(LogStatus.SKIP,"not able to change stage to "+Stage.LOI,YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to find pipeline "+M2_DQSPipeline1Name);
				log(LogStatus.SKIP,"not able to find pipeline "+M2_DQSPipeline1Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		
		//verify changes on deal
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_DQSPipeline1Name, 10)){
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
				sa.assertTrue(false,"Not Able to click "+M2_DQSPipeline1Name);
				log(LogStatus.SKIP,"Not Able to click "+M2_DQSPipeline1Name,YesNo.Yes);
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
	//@Test
	public void M2tc008_DQSChangeStageLOIToDueDiligenceAction(String projectName){
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		totalDealsshown=1;
		dealQualityScore=5;
		averageDealQualityScore=dealQualityScore/totalDealsshown;
		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString(),excelLabel.Deal_Quality_Score.toString()};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		
		String labelValues[]={Stage.Due_Diligence.toString(),Stage.Due_Diligence.toString(),String.valueOf(dealQualityScore)};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		String labelValues2[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};

		String temp[];
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={M2_DQSPipeline1SourceFirm,M2_DQSPipeline1SourceContact};
		int j=0;
		WebElement ele;
		
		//change stage
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_DQSPipeline1Name, 10)){
				if (fp.changeStage(projectName, Stage.LOI.toString(), 10)) {
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele, "details", action.SCROLLANDBOOLEAN);
					log(LogStatus.INFO,"successfully changed stage to "+Stage.LOI,YesNo.Yes);
				}else {
					sa.assertTrue(false,"not able to change stage to "+Stage.LOI);
					log(LogStatus.SKIP,"not able to change stage to "+Stage.LOI,YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to find pipeline "+M2_DQSPipeline1Name);
				log(LogStatus.SKIP,"not able to find pipeline "+M2_DQSPipeline1Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		
		//verify changes on deal
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_DQSPipeline1Name, 10)){
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
				sa.assertTrue(false,"Not Able to click "+M2_DQSPipeline1Name);
				log(LogStatus.SKIP,"Not Able to click "+M2_DQSPipeline1Name,YesNo.Yes);
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
	//@Test
	public void M2tc009_DQSChangeStageDueDiligenceToClosedAction(String projectName){
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		totalDealsshown=1;
		dealQualityScore=3;
		averageDealQualityScore=dealQualityScore/totalDealsshown;
		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString(),excelLabel.Deal_Quality_Score.toString()};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		
		String labelValues[]={Stage.Management_Meeting.toString(),Stage.Management_Meeting.toString(),String.valueOf(dealQualityScore)};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		String labelValues2[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};

		String temp[];
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={M2_DQSPipeline1SourceFirm,M2_DQSPipeline1SourceContact};
		int j=0;
		WebElement ele;
		
		//change stage
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_DQSPipeline1Name, 10)){
				if (fp.changeStage(projectName, Stage.LOI.toString(), 10)) {
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele, "details", action.SCROLLANDBOOLEAN);
					log(LogStatus.INFO,"successfully changed stage to "+Stage.LOI,YesNo.Yes);
				}else {
					sa.assertTrue(false,"not able to change stage to "+Stage.LOI);
					log(LogStatus.SKIP,"not able to change stage to "+Stage.LOI,YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to find pipeline "+M2_DQSPipeline1Name);
				log(LogStatus.SKIP,"not able to find pipeline "+M2_DQSPipeline1Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		
		//verify changes on deal
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_DQSPipeline1Name, 10)){
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
				sa.assertTrue(false,"Not Able to click "+M2_DQSPipeline1Name);
				log(LogStatus.SKIP,"Not Able to click "+M2_DQSPipeline1Name,YesNo.Yes);
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
	//@Test
	public void M2tc010_DQSCreateDealData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		ThreadSleep(5000);
		
		//deal	
		String[][] otherlabel={{excelLabel.Source_Firm.toString(),M2_DQSPipeline2SourceFirm},{excelLabel.Source_Contact.toString(),M2_DQSPipeline2SourceContact}};
		if(fp.clickOnTab(environment,mode, TabName.DealTab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.DealTab,YesNo.No);
			
			if(fp.createDeal(projectName, "", M2_DQSPipeline2Name, M2_DQSPipeline2Company, M2_DQSPipeline2Stage, otherlabel, 30)){
				
				log(LogStatus.INFO,"successfully Created deal : "+M2_DQSPipeline2Name,YesNo.No);	

			}else{
				
				sa.assertTrue(false,"Not Able to Create deal : "+M2_DQSPipeline2Name);
				log(LogStatus.SKIP,"Not Able to Create deal : "+M2_DQSPipeline2Name,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.DealTab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.DealTab,YesNo.Yes);
		}
	
		totalDealsshown=2;
		dealQualityScore=1;
		averageDealQualityScore=(closedScore+dealQualityScore)/totalDealsshown;

		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString(),excelLabel.Deal_Quality_Score.toString()};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		
		String labelValues[]={Stage.Deal_Received.toString(),Stage.Deal_Received.toString(),String.valueOf(dealQualityScore)};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		String labelValues2[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};

		String temp[];
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={M2_DQSPipeline2SourceFirm,M2_DQSPipeline2SourceContact};
		int j=0;
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_DQSPipeline2Name, 10)){
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
				sa.assertTrue(false,"Not Able to click "+M2_DQSPipeline2Name);
				log(LogStatus.SKIP,"Not Able to click "+M2_DQSPipeline2Name,YesNo.Yes);
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
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	//@Test
	public void M2tc0011_DQSChangeStageDealReceivedToManagementIOIAction(String projectName){
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		totalDealsshown=2;
		dealQualityScore=3;
		averageDealQualityScore= (closedScore+dealQualityScore)/totalDealsshown;
		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString(),excelLabel.Deal_Quality_Score.toString()};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		
		String labelValues[]={Stage.IOI.toString(),Stage.IOI.toString(),String.valueOf(dealQualityScore)};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		String labelValues2[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};

		String temp[];
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={M2_DQSPipeline2SourceFirm,M2_DQSPipeline2SourceContact};
		int j=0;
		WebElement ele;
		
		//change stage
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_DQSPipeline2Name, 10)){
				if (fp.changeStage(projectName, Stage.LOI.toString(), 10)) {
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele, "details", action.SCROLLANDBOOLEAN);
					log(LogStatus.INFO,"successfully changed stage to "+Stage.IOI,YesNo.Yes);
				}else {
					sa.assertTrue(false,"not able to change stage to "+Stage.IOI);
					log(LogStatus.SKIP,"not able to change stage to "+Stage.IOI,YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to find pipeline "+M2_DQSPipeline2Name);
				log(LogStatus.SKIP,"not able to find pipeline "+M2_DQSPipeline2Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		
		//verify changes on deal
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_DQSPipeline2Name, 10)){
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
				sa.assertTrue(false,"Not Able to click "+M2_DQSPipeline2Name);
				log(LogStatus.SKIP,"Not Able to click "+M2_DQSPipeline2Name,YesNo.Yes);
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
	//@Test
	public void M2tc0012_DQSChangeStageIOIToManagementClosedAction(String projectName){
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		totalDealsshown=2;
		dealQualityScore=5;
		averageDealQualityScore=(closedScore+dealQualityScore)/totalDealsshown;
		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString(),excelLabel.Deal_Quality_Score.toString()};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		
		String labelValues[]={Stage.Closed.toString(),Stage.Closed.toString(),String.valueOf(dealQualityScore)};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		String labelValues2[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};

		String temp[];
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={M2_DQSPipeline2SourceFirm,M2_DQSPipeline2SourceContact};
		int j=0;
		WebElement ele;
		
		//change stage
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_DQSPipeline2Name, 10)){
				if (fp.changeStage(projectName, Stage.LOI.toString(), 10)) {
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele, "details", action.SCROLLANDBOOLEAN);
					log(LogStatus.INFO,"successfully changed stage to "+Stage.IOI,YesNo.Yes);
				}else {
					sa.assertTrue(false,"not able to change stage to "+Stage.IOI);
					log(LogStatus.SKIP,"not able to change stage to "+Stage.IOI,YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to find pipeline "+M2_DQSPipeline2Name);
				log(LogStatus.SKIP,"not able to find pipeline "+M2_DQSPipeline2Name,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		
		//verify changes on deal
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_DQSPipeline2Name, 10)){
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
				sa.assertTrue(false,"Not Able to click "+M2_DQSPipeline2Name);
				log(LogStatus.SKIP,"Not Able to click "+M2_DQSPipeline2Name,YesNo.Yes);
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
	//@Test
	public void M2tc013_DQSCreateInstitutionData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String name ="";
		String type ="";
		String[][] data ={{M2_DQSINS3Name, M2_DQSINS3RecordType}};
		ThreadSleep(5000);
		for(String[] value:data){
			name=value[0];
			type= value[1];
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
				
				if (ip.createEntityOrAccount(projectName, mode, name, type, null, null, 20)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+name+" of record type : "+type,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+name+" of record type : "+type);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+name+" of record type : "+type,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
			}
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	//@Test
	public void M2tc014_DQSCreateDealData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		ThreadSleep(5000);
		
		//deal	
		String[][] otherlabel={{excelLabel.Source_Firm.toString(),M2_DQSPipeline3SourceFirm},{excelLabel.Source_Contact.toString(),M2_DQSPipeline3SourceContact}};
		if(fp.clickOnTab(environment,mode, TabName.DealTab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.DealTab,YesNo.No);
			
			if(fp.createDeal(projectName, "", M2_DQSPipeline3Name, M2_DQSPipeline3Company, M2_DQSPipeline3Stage, otherlabel, 30)){
				
				log(LogStatus.INFO,"successfully Created deal : "+M2_DQSPipeline3Name,YesNo.No);	

			}else{
				
				sa.assertTrue(false,"Not Able to Create deal : "+M2_DQSPipeline3Name);
				log(LogStatus.SKIP,"Not Able to Create deal : "+M2_DQSPipeline3Name,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.DealTab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.DealTab,YesNo.Yes);
		}
		dealQualityScore=5;
		
		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString(),excelLabel.Deal_Quality_Score.toString()};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		
		String labelValues[]={Stage.LOI.toString(),Stage.LOI.toString(),String.valueOf(dealQualityScore)};

		String temp[];
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={M2_DQSPipeline3SourceFirm,M2_DQSPipeline3SourceContact};
		int j=0;
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_DQSPipeline3Name, 10)){
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
				sa.assertTrue(false,"Not Able to click "+M2_DQSPipeline3Name);
				log(LogStatus.SKIP,"Not Able to click "+M2_DQSPipeline3Name,YesNo.Yes);
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
						if (j==0){
							totalDealsshown=1;
							averageDealQualityScore=(dealQualityScore)/totalDealsshown;
							String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
							temp=labelValues1;
						}else{
							totalDealsshown=3;
							averageDealQualityScore=(closedScore+closedScore+dealQualityScore)/totalDealsshown;
							String labelValues2[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
							temp=labelValues2;
						}
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
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	//@Test
	public void M2tc015_DQSDeletePipeline_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_DQSPipeline1Name, 10)) {
				cp.clickOnShowMoreDropdownOnly(projectName, PageName.Object4Page);
				log(LogStatus.INFO,"Able to Click on Show more Icon : "+TabName.Object4Tab+" For : "+M2_DQSPipeline1Name,YesNo.No);
				ThreadSleep(500);
				WebElement ele = cp.actionDropdownElement(projectName, PageName.Object4Page, ShowMoreActionDropDownList.Delete, 15);
				 if (ele==null) {
					 ele =cp.getDeleteButton(projectName, 30);
				} 
				
				 if (click(driver, ele, "Delete More Icon", action.BOOLEAN)) {
					log(LogStatus.INFO,"Able to Click on Delete more Icon : "+TabName.Object4Tab+" For : "+M2_DQSPipeline1Name,YesNo.No);
					ThreadSleep(1000);
					if (click(driver, cp.getDeleteButtonOnDeletePopUp(projectName, 30), "Delete Button", action.BOOLEAN)) {
						log(LogStatus.INFO,"Able to Click on Delete button on Delete PoPup : "+TabName.Object4Tab+" For : "+M2_DQSPipeline1Name,YesNo.No);
						ThreadSleep(10000);
						if (cp.clickOnTab(projectName, TabName.Object4Tab)) {
							log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object4Tab+" For : "+M2_DQSPipeline1Name,YesNo.No);
							ThreadSleep(1000);
							if (!cp.clickOnAlreadyCreatedItem(projectName, TabName.Object4Tab, M2_DQSPipeline1Name, 10)) {
								log(LogStatus.INFO,"Item has been Deleted after delete operation  : "+M2_DQSPipeline1Name+" For : "+TabName.Object4Tab,YesNo.No);

							}else {
								sa.assertTrue(false,"Item has not been Deleted after delete operation  : "+M2_DQSPipeline1Name+" For : "+TabName.Object4Tab);
								log(LogStatus.SKIP,"Item has not been Deleted after delete operation  : "+M2_DQSPipeline1Name+" For : "+TabName.Object4Tab,YesNo.Yes);
							}

						}else {
							sa.assertTrue(false,"Not Able to Click on Tab after delete : "+TabName.Object4Tab+" For : "+M2_DQSPipeline1Name);
							log(LogStatus.SKIP,"Not Able to Click on Tab after delete : "+TabName.Object4Tab+" For : "+M2_DQSPipeline1Name,YesNo.Yes);	
						}
					}else {
						log(LogStatus.INFO,"not able to click on delete button, so not deleted : "+TabName.Object4Tab+" For : "+M2_DQSPipeline1Name,YesNo.No);
						sa.assertTrue(false,"not able to click on delete button, so not deleted : "+TabName.Object4Tab+" For : "+M2_DQSPipeline1Name);
					}
				 }else {
					 log(LogStatus.INFO,"not Able to Click on Delete more Icon : "+TabName.Object4Tab+" For : "+M2_DQSPipeline1Name,YesNo.No);
					 sa.assertTrue(false,"not Able to Click on Delete more Icon : "+TabName.Object4Tab+" For : "+M2_DQSPipeline1Name);
				 }
			}else {
				 log(LogStatus.INFO,"not Able to Click on "+M2_DQSPipeline1Name,YesNo.No);
				 sa.assertTrue(false,"not Able to Click on "+M2_DQSPipeline1Name);
			 }
		}else {
			 log(LogStatus.INFO,"not Able to Click on "+TabName.Object4Tab,YesNo.No);
			 sa.assertTrue(false,"not Able to Click on "+TabName.Object4Tab);
		 }
		
		dealQualityScore=5;
		
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};

		String temp[];
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={M2_DQSPipeline1SourceFirm,M2_DQSPipeline1SourceContact};
		int j=0;
		WebElement ele;
		for (TabName tab:tabName) {
		if (lp.clickOnTab(projectName, tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
				if (j==0){
					totalDealsshown=1;
					averageDealQualityScore=(closedScore)/totalDealsshown;
					String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
					temp=labelValues1;
				}else{
					totalDealsshown=2;
					averageDealQualityScore=(closedScore+dealQualityScore)/totalDealsshown;
					String labelValues2[]={String.valueOf(dealQualityScore),String.valueOf(totalDealsshown)};
					temp=labelValues2;
				}
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
	//@Test
	public void M2tc016_1_DQSRestorePipeline_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		WebElement ele = null ;
	
		
		TabName tabName = TabName.RecycleBinTab;
		String name = M2_DQSPipeline1Name;
		
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
	//@Test
	public void M2tc016_2_DQSRestorePipeline_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=5;
		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString(),excelLabel.Deal_Quality_Score.toString()};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		
		String labelValues[]={Stage.Closed.toString(),Stage.Closed.toString(),String.valueOf(dealQualityScore)};

		String temp[];
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={M2_DQSPipeline1SourceFirm,M2_DQSPipeline1SourceContact};
		int j=0;
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_HSRPipeline1Name, 10)){
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
				sa.assertTrue(false,"Not Able to click "+M2_HSRPipeline1Name);
				log(LogStatus.SKIP,"Not Able to click "+M2_HSRPipeline1Name,YesNo.Yes);
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
						if (j==0){
							totalDealsshown=2;
							averageDealQualityScore=(closedScore+closedScore)/totalDealsshown;
							String labelValues1[]={String.valueOf(dealQualityScore),String.valueOf(totalDealsshown)};
							temp=labelValues1;
						}else{
							totalDealsshown=3;
							averageDealQualityScore=(closedScore+closedScore+dealQualityScore)/totalDealsshown;
							String labelValues2[]={String.valueOf(dealQualityScore),String.valueOf(totalDealsshown)};
							temp=labelValues2;
						}
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
	//@Test
	public void M2tc017_DQSUpdateStageNames(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
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
	//@Test
	public void M2tc018_DQSCreateContactData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String mailID = "";
		ThreadSleep(5000);
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			mailID = lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, mailID, "Contacts", excelLabel.Variable_Name, "M2DQSCON2",
					excelLabel.Contact_EmailId);

			if (cp.createContact(projectName, M2_DQSContact2FName, M2_DQSContact2LName, M2_DQSContact2Ins, mailID, "",
					null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO, "successfully Created Contact : " + M2_DQSContact2FName + " " + M2_DQSContact2LName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Create Contact : " + M2_DQSContact2FName + " " + M2_DQSContact2LName);
				log(LogStatus.SKIP, "Not Able to Create Contact: " + M2_DQSContact2FName + " " + M2_DQSContact2LName,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object2Tab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object2Tab, YesNo.Yes);
		}
			
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	//@Test
	public void M2tc019_DQSCreateDealData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		ThreadSleep(5000);
		
		//deal	
		String[][] otherlabel={{excelLabel.Source_Firm.toString(),M2_DQSPipeline4SourceFirm},{excelLabel.Source_Contact.toString(),M2_DQSPipeline4SourceContact}};
		if(fp.clickOnTab(environment,mode, TabName.DealTab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.DealTab,YesNo.No);
			
			if(fp.createDeal(projectName, "", M2_DQSPipeline4Name, M2_DQSPipeline4Company, M2_DQSPipeline4Stage, otherlabel, 30)){
				
				log(LogStatus.INFO,"successfully Created deal : "+M2_DQSPipeline4Name,YesNo.No);	

			}else{
				
				sa.assertTrue(false,"Not Able to Create deal : "+M2_DQSPipeline4Name);
				log(LogStatus.SKIP,"Not Able to Create deal : "+M2_DQSPipeline4Name,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.DealTab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.DealTab,YesNo.Yes);
		}
	
		
		dealQualityScore=1;
		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString(),excelLabel.Deal_Quality_Score.toString()};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		
		String labelValues[]={Stage.NonDisclosureAgreement.toString(),Stage.NonDisclosureAgreement.toString(),String.valueOf(dealQualityScore)};
		String temp[];
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={M2_DQSPipeline4SourceFirm,M2_DQSPipeline4SourceContact};
		int j=0;
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_DQSPipeline4Name, 10)){
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
				sa.assertTrue(false,"Not Able to click "+M2_DQSPipeline4Name);
				log(LogStatus.SKIP,"Not Able to click "+M2_DQSPipeline4Name,YesNo.Yes);
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
						if (j==0) {
							totalDealsshown=2;
							averageDealQualityScore=(loiScore+dealQualityScore)/totalDealsshown;
							String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
							temp=labelValues1;
						}else {
							totalDealsshown=1;
							averageDealQualityScore=dealQualityScore/totalDealsshown;
							String labelValues2[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
							temp=labelValues2;
						}
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
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	//@Test
	public void M2tc020_DQSCreateInstitutionData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		ThreadSleep(5000);
		String name="";
		String type ="";
		String[][] data = {{M2_DQSINS4Name,M2_DQSINS4RecordType},
							{M2_DQSINS5Name,M2_DQSINS5RecordType},
							{M2_DQSINS6Name,M2_DQSINS6RecordType}};
		
		for(String[] value:data){
			name=value[0];
			type =value[1];
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
				
				if (ip.createEntityOrAccount(projectName, mode, name, type, null, null, 20)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+name+" of record type : "+type,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+name+" of record type : "+type);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+name+" of record type : "+type,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
			}
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	//@Test
	public void M2tc021_DQSCreateContactData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String mailID = "";
		ThreadSleep(5000);
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			mailID = lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, mailID, "Contacts", excelLabel.Variable_Name, "M2DQSCON3",
					excelLabel.Contact_EmailId);

			if (cp.createContact(projectName, M2_DQSContact3FName, M2_DQSContact3LName, M2_DQSContact3Ins, mailID, "",
					null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO, "successfully Created Contact : " + M2_DQSContact3FName + " " + M2_DQSContact3LName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Create Contact : " + M2_DQSContact3FName + " " + M2_DQSContact3LName);
				log(LogStatus.SKIP, "Not Able to Create Contact: " + M2_DQSContact3FName + " " + M2_DQSContact3LName,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object2Tab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object2Tab, YesNo.Yes);
		}
			
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	//@Test
	public void M2tc022_DQSCreateDealData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		ThreadSleep(5000);
		
		//deal	
		String[][] otherlabel={{excelLabel.Source_Firm.toString(),M2_DQSPipeline5SourceFirm},{excelLabel.Source_Contact.toString(),M2_DQSPipeline5SourceContact}};
		if(fp.clickOnTab(environment,mode, TabName.DealTab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.DealTab,YesNo.No);
			
			if(fp.createDeal(projectName, "", M2_DQSPipeline5Name, M2_DQSPipeline5Company, M2_DQSPipeline5Stage, otherlabel, 30)){
				
				log(LogStatus.INFO,"successfully Created deal : "+M2_DQSPipeline5Name,YesNo.No);	

			}else{
				
				sa.assertTrue(false,"Not Able to Create deal : "+M2_DQSPipeline5Name);
				log(LogStatus.SKIP,"Not Able to Create deal : "+M2_DQSPipeline5Name,YesNo.Yes);
			}
			
		}else{
			
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.DealTab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.DealTab,YesNo.Yes);
		}
	
		totalDealsshown=1;
		dealQualityScore=5;
		averageDealQualityScore=dealQualityScore/totalDealsshown;

		
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString(),excelLabel.Deal_Quality_Score.toString()};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()};
		
		String labelValues[]={Stage.LOI.toString(),Stage.LOI.toString(),String.valueOf(dealQualityScore)};
		String labelValues1[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};
		String labelValues2[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};

		String temp[];
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={M2_DQSPipeline5SourceFirm,M2_DQSPipeline5SourceContact};
		int j=0;
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, M2_DQSPipeline5Name, 10)){
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
				sa.assertTrue(false,"Not Able to click "+M2_DQSPipeline5Name);
				log(LogStatus.SKIP,"Not Able to click "+M2_DQSPipeline5Name,YesNo.Yes);
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
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

*/
}

