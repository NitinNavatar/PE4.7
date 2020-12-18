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

import com.navatar.generic.BaseLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.*;
import com.navatar.pageObjects.BasePageErrorMessage;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.CustomObjPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
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
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object3Page,ActivityType.Next, TWTask3Subject, SubjectElement.SubjectLink, 10);
					if (click(driver, ele, "task name",action.SCROLLANDBOOLEAN)) {
						String[][] fieldsWithValues= {{PageLabel.Subject.toString(),TWTask3Subject},
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


	
}
