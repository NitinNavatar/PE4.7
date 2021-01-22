package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.server.handler.SendKeys;
import org.openqa.selenium.remote.server.handler.SwitchToWindow;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.android.dx.cf.iface.Field;
import com.navatar.generic.BaseLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.SoftAssert;
import com.navatar.generic.EnumConstants.*;
import com.navatar.pageObjects.BasePageErrorMessage;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.CustomObjPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.NavatarSetupPageBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.navatar.pageObjects.TaskPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;

public class TaskWatchlist extends BaseLib{
	@Parameters({ "projectName"})
	@Test
	public void TWtc001_1_CreateCRMUser(String projectName) {
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
							crmUserProfile)) {
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
	public void TWtc001_2_Prerequisite(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		String[][] userAndPassword = {{superAdminUserName,adminPassword},{crmUser1EmailID,adminPassword}};
		for (String[] userPass : userAndPassword) {
			lp.CRMLogin(userPass[0], userPass[1], appName);


			String addRemoveTabName="";
			String tab1="";
			if (tabObj1.equalsIgnoreCase("Entity")){
				tab1="Entitie";
			}
			else{
				tab1=tabObj1;
			}
			addRemoveTabName=tab1+"s,"+tabObj2+"s,"+tabObj3+"s,"+tabObj4+"s,"+"Tasks"+",Recycle Bin"+",Navatar Setup";
			if (lp.addTab_Lighting( addRemoveTabName, 5)) {
				log(LogStatus.INFO,"Tab added : "+addRemoveTabName,YesNo.No);
			} else {
				log(LogStatus.FAIL,"Tab not added : "+addRemoveTabName,YesNo.No);
				sa.assertTrue(false, "Tab not added : "+addRemoveTabName);
			}		



			ThreadSleep(5000);
			lp.CRMlogout();
			closeBrowser();
			config(ExcelUtils.readDataFromPropertyFile("Browser"));
			lp = new LoginPageBusinessLayer(driver);

		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void TWtc001_3_AddListView(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		String[] tabs= {tabObj1,tabObj2,tabObj3,tabObj4};
		TabName[] tab= {TabName.Object1Tab,TabName.Object2Tab,TabName.Object3Tab,TabName.Object4Tab};
		int i=0;
		for (TabName t:tab) {

			if (lp.clickOnTab(projectName, t)) {	
				if (lp.addAutomationAllListView(projectName, tabs[i], 10)) {
					log(LogStatus.INFO,"list view added on "+tabs[i],YesNo.No);
				} else {
					log(LogStatus.FAIL,"list view could not added on "+tabs[i],YesNo.Yes);
					sa.assertTrue(false, "list view could not added on "+tabs[i]);
				}
			} else {
				log(LogStatus.FAIL,"could not click on "+tabs[i],YesNo.Yes);
				sa.assertTrue(false, "could not click on "+tabs[i]);
			}
			i++;
			ThreadSleep(5000);
		}


		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void TWtc002_1_CreatePreconditionData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
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
				ExcelUtils.writeData(taskWatchlistFilePath, mailID, "Contacts", excelLabel.Variable_Name, "TWCON"+i,excelLabel.Contact_EmailId);

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
	public void TWtc003_1_AddWatchlistField(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
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
	public void TWtc004_AddWatchlistContactAndVerifyImpact(String projectName) {
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
				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task_with_Multiple_Associations , 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task_with_Multiple_Associations.toString(), action.BOOLEAN)) {
				ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task_with_Multiple_Associations , 10);
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

					//remove icon
					/*if (click(driver, ip.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object1Page, PageLabel.Related_Associations.toString(), true, Smoke_TaskINS1Name, action.SCROLLANDBOOLEAN, 10), "remove button", action.BOOLEAN)) {
						log(LogStatus.SKIP,"clicked on remove icon",YesNo.No);

					}
					else {
						log(LogStatus.ERROR, "remove Button is not clickable", YesNo.Yes);
						sa.assertTrue(false, "remove Button is not clickable");
					}*/

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
	public void TWtc005_AddUnderEvalContactAndVerifyImpact(String projectName) {
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
				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task_with_Multiple_Associations , 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task_with_Multiple_Associations.toString(), action.BOOLEAN)) {
				ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task_with_Multiple_Associations , 10);
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

					//remove icon
					/*if (click(driver, ip.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object1Page, PageLabel.Related_Associations.toString(), true, Smoke_TaskINS1Name, action.SCROLLANDBOOLEAN, 10), "remove button", action.BOOLEAN)) {
						log(LogStatus.SKIP,"clicked on remove icon",YesNo.No);

					}
					else {
						log(LogStatus.ERROR, "remove Button is not clickable", YesNo.Yes);
						sa.assertTrue(false, "remove Button is not clickable");
					}*/

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
	public void TWtc006_AddWatchlistContactAndVerifyImpact(String projectName) {
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
				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations , 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations.toString(), action.BOOLEAN)) {
				ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations , 10);
				scrollDownThroughWebelement(driver, ip.relatedAssociations(projectName).get(0), "related associatons");
					//3
					boolean flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object1Tab, Smoke_TWContact3FName+" "+Smoke_TWContact3LName, action.SCROLLANDBOOLEAN, 10);		
					if (flag) {
						log(LogStatus.SKIP,"Selected "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.No);

					} else {
						sa.assertTrue(false,"Not Able to Select "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWContact3FName+" "+Smoke_TWContact3LName+" For Label "+PageLabel.Name,YesNo.Yes);

					}

					//remove icon
					/*if (click(driver, ip.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object1Page, PageLabel.Related_Associations.toString(), true, Smoke_TaskINS1Name, action.SCROLLANDBOOLEAN, 10), "remove button", action.BOOLEAN)) {
						log(LogStatus.SKIP,"clicked on remove icon",YesNo.No);

					}
					else {
						log(LogStatus.ERROR, "remove Button is not clickable", YesNo.Yes);
						sa.assertTrue(false, "remove Button is not clickable");
					}*/
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
	public void TWtc007_AddUnderEvalContactAndVerifyImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		String tabs=ip.getTabName(projectName, TabName.Object1Tab)+","+ip.getTabName(projectName, TabName.Object3Tab);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, Smoke_TWINS1Name, 20)) {
				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations , 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations.toString(), action.BOOLEAN)) {
				ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations , 10);
				scrollDownThroughWebelement(driver, ip.relatedAssociations(projectName).get(0), "related associatons");
					//3
					boolean flag=ip.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.Object1Page, PageLabel.Name.toString(), TabName.Object1Tab, Smoke_TWContact1FName+" "+Smoke_TWContact1LName, action.SCROLLANDBOOLEAN, 10);		
					if (flag) {
						log(LogStatus.SKIP,"Selected "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name,YesNo.No);

					} else {
						sa.assertTrue(false,"Not Able to Select "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name);
						log(LogStatus.SKIP,"Not Able to Select "+Smoke_TWContact1FName+" "+Smoke_TWContact1LName+" For Label "+PageLabel.Name,YesNo.Yes);

					}

					//remove icon
					/*if (click(driver, ip.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object1Page, PageLabel.Related_Associations.toString(), true, Smoke_TaskINS1Name, action.SCROLLANDBOOLEAN, 10), "remove button", action.BOOLEAN)) {
						log(LogStatus.SKIP,"clicked on remove icon",YesNo.No);

					}
					else {
						log(LogStatus.ERROR, "remove Button is not clickable", YesNo.Yes);
						sa.assertTrue(false, "remove Button is not clickable");
					}*/
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
	public void TWtc008_AddMultipleWatchlistContactAndVerifyImpact(String projectName) {
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
				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task_with_Multiple_Associations , 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task_with_Multiple_Associations.toString(), action.BOOLEAN)) {
				ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task_with_Multiple_Associations , 10);
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
					//remove icon
					/*if (click(driver, ip.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object1Page, PageLabel.Related_Associations.toString(), true, Smoke_TaskINS1Name, action.SCROLLANDBOOLEAN, 10), "remove button", action.BOOLEAN)) {
						log(LogStatus.SKIP,"clicked on remove icon",YesNo.No);

					}
					else {
						log(LogStatus.ERROR, "remove Button is not clickable", YesNo.Yes);
						sa.assertTrue(false, "remove Button is not clickable");
					}*/

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
	public void TWtc009_AddMultipleWatchlistContactAndVerifyImpact(String projectName) {
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
				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations , 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations.toString(), action.BOOLEAN)) {
				ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.Log_a_Call_with_Multiple_Associations , 10);
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
					//remove icon
					/*if (click(driver, ip.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object1Page, PageLabel.Related_Associations.toString(), true, Smoke_TaskINS1Name, action.SCROLLANDBOOLEAN, 10), "remove button", action.BOOLEAN)) {
						log(LogStatus.SKIP,"clicked on remove icon",YesNo.No);

					}
					else {
						log(LogStatus.ERROR, "remove Button is not clickable", YesNo.Yes);
						sa.assertTrue(false, "remove Button is not clickable");
					}*/
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
	public void TWtc010_RemoveContactFromWatchlistContactAndVerifyImpact(String projectName) {
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
	public void TWtc011_RemoveContactFromWatchlistContactAndVerifyImpact(String projectName) {
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
	public void TWtc012_AddContactFromWatchlistContactAndVerifyImpact(String projectName) {
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
				//ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(),false, Smoke_TWContact3FName+" "+Smoke_TWContact3LName, action.SCROLLANDBOOLEAN, 15);
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
	public void TWtc013_AddContactFromWatchlistContactAndVerifyImpact(String projectName) {
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
				//ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(),false, Smoke_TWContact3FName+" "+Smoke_TWContact3LName, action.SCROLLANDBOOLEAN, 15);
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
	public void TWtc014_SetStatusAsWatchlistAndVerifyImpact(String projectName) {
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
					
					String xpath="//div[@class='select-options']//li/a[@title='"+PageLabel.Watchlist.toString()+"']";
					WebElement dealStatusEle = FindElement(driver,xpath, PageLabel.Watchlist.toString(),action.SCROLLANDBOOLEAN, 10);
					ThreadSleep(2000);
					if (click(driver, dealStatusEle, PageLabel.Watchlist.toString(), action.SCROLLANDBOOLEAN)) {
						appLog.info("Selected Status : "+PageLabel.Watchlist.toString());
						ThreadSleep(2000);
					} else {
						log(LogStatus.ERROR,"Not able to Select on Status : "+PageLabel.Watchlist.toString(),YesNo.No);
						sa.assertTrue(false, "Not able to Select on Status : "+PageLabel.Watchlist.toString());
					}
					if (click(driver, ip.getSaveButton(projectName,10), "save button", action.SCROLLANDBOOLEAN)) {
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
	public void TWtc015_SaveTaskAgainAndVerifyImpact(String projectName) {
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
	public void TWtc016_CreateNewTaskWithWatchlistAndVerifyImpact(String projectName) {
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
	public void TWtc017_AddWatchlistContactInStandardTask(String projectName) {
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
	public void TWtc018_EnableContactTransfer(String projectName) {
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
							//sa.assertTrue(false, "Not Able to Click on Enable Contact Transfer Checkbox");
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
	public void TWtc019_TransferContact_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object2Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, Smoke_TWContact5FName+" "+Smoke_TWContact5LName, 20)) {
				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task_with_Multiple_Associations , 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task_with_Multiple_Associations.toString(), action.BOOLEAN)) {
				if (sendKeys(driver, ip.getLabelTextBox(projectName, PageName.NewTaskPage.toString(), "Subject",20), TWTaskCR1Subject, "Subject", action.SCROLLANDBOOLEAN)) {
					if (clickUsingJavaScript(driver, ip.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"successfully created task",  YesNo.Yes);
					}
					else {
						log(LogStatus.ERROR, "save button is not clickable so task not created", YesNo.Yes);
						sa.assertTrue(false,"save button is not clickable so task not created" );
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
	public void TWtc019_TransferContact_Impact(String projectName) {
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
				msg+= " about "+Smoke_TWINS3Name;
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
	public void TWtc020_1_UpdateWatchlistLabels_Action(String projectName) {
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
	public void TWtc020_2_UpdateWatchlistLabels_Action(String projectName) {
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
				WebElement ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task_with_Multiple_Associations , 10);
				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Task_with_Multiple_Associations.toString(), action.BOOLEAN)) {
					ele=lp.getActivityTimeLineItem(projectName,PageName.Object1Page,ActivityTimeLineItem.New_Task_with_Multiple_Associations , 10);
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
	public void TWtc021_DeleteContactAndVerifyImpact(String projectName) {
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
						{PageLabel.Watchlist.toString(),Watchlist.True.toString()}};

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

		
	
}
