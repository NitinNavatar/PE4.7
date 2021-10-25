package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.navatar.generic.BaseLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.ActivityTimeLineItem;
import com.navatar.generic.EnumConstants.ActivityType;
import com.navatar.generic.EnumConstants.CreateNew_DefaultValues;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.GlobalActionItem;
import com.navatar.generic.EnumConstants.InstitutionPageFieldLabelText;
import com.navatar.generic.EnumConstants.NavigationMenuItems;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.Stage;
import com.navatar.generic.EnumConstants.SubjectElement;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.ContactTransferTabBusinessLayer;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.DealPageBusinessLayer;
import com.navatar.pageObjects.DealPageErrorMessage;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.GlobalActionPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.MarketingEventPageBusinessLayer;
import com.navatar.pageObjects.NavatarSetupPageBusinessLayer;
import com.navatar.pageObjects.NavigationPageBusineesLayer;
import com.navatar.pageObjects.SDGPageBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.navatar.pageObjects.TaskPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;

public class PECloudSmoke2 extends BaseLib{
	
	String navigationMenuName = NavigationMenuItems.Create_New.toString();
	String DealTab="Deal";
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc067_1_AddContactTransferButtonOnTheContactPage(String projectName) {

		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		ContactTransferTabBusinessLayer ctt = new ContactTransferTabBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavatarSetupPageBusinessLayer np= new NavatarSetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		boolean flag=false;
		NavatarSetupSideMenuTab setupSideMenuTab=NavatarSetupSideMenuTab.ContactTransfer;
		flag=np.EnableOrDisableSettingOnNavatarSetUp(projectName, setupSideMenuTab, true);
		if (flag) {
			log(LogStatus.INFO, "Not Able to Enable "+setupSideMenuTab , YesNo.No);
		} else {
			log(LogStatus.ERROR, "Not Able to Enable "+setupSideMenuTab , YesNo.Yes);
			sa.assertTrue(false,"Not Able to Enable "+setupSideMenuTab);

		}
		if (bp.clickOnTab(projectName, TabName.NavatarSetup)) {
			appLog.info("Clicked on Navatar Set Up Tab");
			if (ctt.clickOnNavatarSetupSideMenusTab(projectName, NavatarSetupSideMenuTab.ContactTransfer)) {
				appLog.error("Clicked on Contact Transfer Tab");
				if (click(driver, ctt.getEditButtonforNavatarSetUpSideMenuTab(projectName,NavatarSetupSideMenuTab.ContactTransfer, 10), "Edit Button", action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Edit Button", YesNo.No);

					String keepActivitiesDefaultValue = "Old Institution Only";
					String defaultvalue = getSelectedOptionOfDropDown(driver,ctt.getKeepActivitiesAtSelectList(environment, mode, EditViewMode.Edit, 10),keepActivitiesDefaultValue, "Text");
					if (keepActivitiesDefaultValue.equalsIgnoreCase(defaultvalue)) {
						log(LogStatus.INFO, "Keep Activities Default Value Matched: " + defaultvalue, YesNo.No);
					} else {
						sa.assertTrue(false, "Keep Activities Default default value not matched Actual : "+ defaultvalue + " \t Expected : " + keepActivitiesDefaultValue);
						log(LogStatus.INFO, "Keep Activities Default default value not matched Actual : " + defaultvalue+ " \t Expected : " + keepActivitiesDefaultValue, YesNo.Yes);
					}

					String includeActivities = "Contact Only";
					defaultvalue = getSelectedOptionOfDropDown(driver,ctt.getIncludeActivitiesSelectList(environment, mode, EditViewMode.Edit, 10),
							includeActivities, "Text");
					if (includeActivities.equalsIgnoreCase(defaultvalue)) {
						log(LogStatus.INFO, "Include Activities Related to Default Value Matched: " + defaultvalue,YesNo.No);
					} else {
						sa.assertTrue(false, "Include Activities Related to default value not matched Actual : "+ defaultvalue + " \t Expected : " + includeActivities);
						log(LogStatus.INFO, "Include Activities Related to default value not matched Actual : "+ defaultvalue + " \t Expected : " + includeActivities, YesNo.Yes);
					}


					String selectIncludeActivitiesValue = includeActivities;
					if (selectVisibleTextFromDropDown(driver,ctt.getIncludeActivitiesSelectList(environment, mode, EditViewMode.Edit, 10),selectIncludeActivitiesValue, selectIncludeActivitiesValue)) {
						log(LogStatus.INFO, "Selected Include Activities related to : " + selectIncludeActivitiesValue,YesNo.No);
					} else {
						sa.assertTrue(false,"Not Able to Select Include Activities related to : " + selectIncludeActivitiesValue);
						log(LogStatus.SKIP,"Not Able to Select Include Activities related to : " + selectIncludeActivitiesValue,YesNo.Yes);

					}

					String selectKeepActivitiesValue = keepActivitiesDefaultValue;
					if (selectVisibleTextFromDropDown(driver,ctt.getIncludeActivitiesSelectList(environment, mode, EditViewMode.Edit, 10),selectKeepActivitiesValue, selectKeepActivitiesValue)) {
						log(LogStatus.INFO, "Selected Keep Activities related to : " + selectKeepActivitiesValue,YesNo.No);
					} else {
						sa.assertTrue(false,"Not Able to Select Keep Activities related to : " + selectKeepActivitiesValue);
						log(LogStatus.SKIP,"Not Able to Select Keep Activities related to : " + selectKeepActivitiesValue,YesNo.Yes);

					}



					if (!isSelected(driver, ctt.getEnableCheckBoxforNavatarSetUpSideMenuTab(environment, mode,NavatarSetupSideMenuTab.ContactTransfer, EditViewMode.Edit, ClickOrCheckEnableDisableCheckBox.Click, 10), "Enabled CheckBox")) {
						log(LogStatus.INFO, "Enable Contact Transfer is Unchecked", YesNo.No);
						if (click(driver,ctt.getEnableCheckBoxforClickNavatarSetUpSideMenuTab(projectName,NavatarSetupSideMenuTab.ContactTransfer, EditViewMode.Edit, 10),"Enabled Contact Transfer", action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Enable Contact Transfer Box Checkbox", YesNo.No);
							ThreadSleep(2000);

						} else {
							sa.assertTrue(false, "Not Able to Click on Enable Contact Transfer Checkbox");
							log(LogStatus.SKIP, "Not Able to Click on Enable Contact Transfer Checkbox", YesNo.Yes);
						}

					} else {
						log(LogStatus.SKIP, "Enable Contact Transfer is Already checked", YesNo.Yes);
					}


					if (click(driver, ctt.getSaveButtonforNavatarSetUpSideMenuTab(projectName,NavatarSetupSideMenuTab.ContactTransfer, 10, TopOrBottom.TOP), "Save Button", action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Save Button for No Button", YesNo.No);
						ThreadSleep(10000);

						keepActivitiesDefaultValue = "Old Institution Only";
						defaultvalue = getSelectedOptionOfDropDown(driver,ctt.getKeepActivitiesAtSelectList(environment, mode, EditViewMode.Edit, 10),keepActivitiesDefaultValue, "Text");
						if (keepActivitiesDefaultValue.equalsIgnoreCase(defaultvalue)) {
							log(LogStatus.INFO, "Keep Activities Default Value Matched: " + defaultvalue, YesNo.No);
						} else {
							sa.assertTrue(false, "Keep Activities Default default value not matched Actual : "+ defaultvalue + " \t Expected : " + keepActivitiesDefaultValue);
							log(LogStatus.INFO, "Keep Activities Default default value not matched Actual : " + defaultvalue+ " \t Expected : " + keepActivitiesDefaultValue, YesNo.Yes);
						}

						includeActivities = "Contact Only";
						defaultvalue = getSelectedOptionOfDropDown(driver,ctt.getIncludeActivitiesSelectList(environment, mode, EditViewMode.Edit, 10),
								includeActivities, "Text");
						if (includeActivities.equalsIgnoreCase(defaultvalue)) {
							log(LogStatus.INFO, "Include Activities Related to Default Value Matched: " + defaultvalue,YesNo.No);
						} else {
							sa.assertTrue(false, "Include Activities Related to default value not matched Actual : "+ defaultvalue + " \t Expected : " + includeActivities);
							log(LogStatus.INFO, "Include Activities Related to default value not matched Actual : "+ defaultvalue + " \t Expected : " + includeActivities, YesNo.Yes);
						}


					} else {
						sa.assertTrue(false, "Not Able to Click on Save Button for No Button");
						log(LogStatus.SKIP, "Not Able to Click on Save Button No Button", YesNo.Yes);
					}


				}else{
					sa.assertTrue(false, "Not Able to Click on Edit Button");
					log(LogStatus.SKIP, "Not Able to Click on Edit Button", YesNo.Yes);	
				}

			} else {
				appLog.error("Not Able to Click on Contact Transfer Tab");
				sa.assertTrue(false, "Not Able to Click on Contact Transfer Tab");
				log(LogStatus.SKIP, "Not Able to Click on Contact Transfer Tab", YesNo.Yes);
			}

		} else {
			appLog.error("Not Able to Click on Navatar Set Up Tab");
			sa.assertTrue(false, "Not Able to Click on Navatar Set Up Tab");
			log(LogStatus.SKIP, "Not Able to Click on Navatar Set Up Tab", YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc067_2_CreateAccountAndContactRelatedcToCT(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String value="";
		String type="";
		String[][] EntityOrAccounts = {{ SmokeCTIns, SmokeCTInsRecordType ,null},
				{ SmokeCTIns1, SmokeCTIns1RecordType ,null},
				{ SmokeCTIns2, SmokeCTIns2RecordType ,null}};
		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, tabObj1)) {
				log(LogStatus.INFO,"Click on Tab : "+tabObj1,YesNo.No);	
				value = accounts[0];
				type = accounts[1];
				if (ip.createEntityOrAccount(projectName, mode, value, type, null, null, 20)) {
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

		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			SmokeCTContact1EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, SmokeCTContact1EmailID, "Contacts", excelLabel.Variable_Name, "SMOKECTCON1",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, SmokeCTContact1FName, SmokeCTContact1LName, SmokeCTContact1Inst, SmokeCTContact1EmailID,SmokeCTContact1RecordType, null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+SmokeCTContact1FName+" "+SmokeCTContact1LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+SmokeCTContact1FName+" "+SmokeCTContact1LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+SmokeCTContact1FName+" "+SmokeCTContact1LName,YesNo.Yes);
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
	public void SmokeTc068_CreateSomeActivityAndVerifyTheActivitytimelineOnTestContact1RelatedPage(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName=SmokeCTContact1FName+" "+SmokeCTContact1LName;
		SmokeCTTask1dueDate=todaysDate;
		String task = SmokeCTTask1Subject;
		String[][] task1 = {{PageLabel.Subject.toString(),task},
				{PageLabel.Name.toString(),contactName},
				{PageLabel.Related_To.toString(),SmokeCTContact1Inst},
				{PageLabel.Due_Date.toString(),SmokeCTTask1dueDate},
				{PageLabel.Priority.toString(),SmokeCTTask1Priority}};

		if (gp.clickOnGlobalActionAndEnterValue(projectName, GlobalActionItem.New_Task, task1)) {
			log(LogStatus.INFO,"Able to Enter Value for : "+task,YesNo.No);
			ExcelUtils.writeData(phase1DataSheetFilePath,SmokeCTTask1dueDate, "Task1", excelLabel.Variable_Name, "SmokeCTTask1", excelLabel.Due_Date);
			if (click(driver, gp.getSaveButtonForEvent(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);		
				
			}else {
				sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
			}
		} else {
			sa.assertTrue(false,"Not Able to Click/Enter Value : "+task);
			log(LogStatus.SKIP,"Not Able to Click/Enter Value : "+task,YesNo.Yes);	
		}


		SmokeCTEvent1StartDate=todaysDate1;
		SmokeCTEvent1EndDate=todaysDate1;
		task = SmokeCTEvent1Subject;
		String[][] event1 = {{PageLabel.Subject.toString(),task},
				{PageLabel.Name.toString(),contactName},{PageLabel.Related_To.toString(),SmokeCTContact1Inst},
				{PageLabel.Start_Date.toString(),SmokeCTEvent1StartDate},
				{PageLabel.End_Date.toString(),SmokeCTEvent1EndDate},
				{PageLabel.Location.toString(),SmokeCTEvent1EndDate}};

		if (gp.clickOnGlobalActionAndEnterValue(projectName, GlobalActionItem.New_Event, event1)) {
			log(LogStatus.INFO,"Able to Enter Value for : "+task,YesNo.No);
			ExcelUtils.writeData(phase1DataSheetFilePath,SmokeCTEvent1StartDate, "Events", excelLabel.Variable_Name, "SmokeCTEvent1", excelLabel.Start_Date);
			ExcelUtils.writeData(phase1DataSheetFilePath,SmokeCTEvent1EndDate, "Events", excelLabel.Variable_Name, "SmokeCTEvent1", excelLabel.End_Date);

			if (click(driver, gp.getSaveButtonForEvent(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);		
				
			}else {
				sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
			}
		} else {
			sa.assertTrue(false,"Not Able to Click/Enter Value : "+task);
			log(LogStatus.SKIP,"Not Able to Click/Enter Value : "+task,YesNo.Yes);	
		}

		task = SmokeCTLogACall1Subject;
		String[][] logACall = {{PageLabel.Subject.toString(),task},
				{PageLabel.Name.toString(),contactName},{PageLabel.Related_To.toString(),SmokeCTContact1Inst},
				{PageLabel.Comments.toString(),SmokeCTLogACall1Comment}};

		if (gp.clickOnGlobalActionAndEnterValue(projectName, GlobalActionItem.Log_a_Call, logACall)) {
			log(LogStatus.INFO,"Able to Enter Value for : "+task,YesNo.No);
			if (click(driver, gp.getSaveButtonForEvent(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);		
				
			}else {
				sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
			}
		} else {
			sa.assertTrue(false,"Not Able to Click/Enter Value : "+task);
			log(LogStatus.SKIP,"Not Able to Click/Enter Value : "+task,YesNo.Yes);	
		}


		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void SmokeTc069_VerifyTheActivitytimelineOnTestContact1(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
		String task="";
		String secondaryContact="";
			
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+secondaryContact,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, secondaryContact, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+secondaryContact+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					task = SmokeCTTask1Subject;
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, task, SubjectElement.SubjectLink, 10);
					if (ele!=null) {
						log(LogStatus.INFO,task+" is present in Next Activity Section",YesNo.No);	
					} else {
						sa.assertTrue(false,task+" should be present in Next Activity Section");
						log(LogStatus.SKIP,task+" should be present in Next Activity Section",YesNo.Yes);
					}
					
					task = SmokeCTEvent1Subject;
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, task, SubjectElement.SubjectLink, 10);
					if (ele!=null) {
						log(LogStatus.INFO,task+" is present in Next Activity Section",YesNo.No);	
					} else {
						sa.assertTrue(false,task+" should be present in Next Activity Section");
						log(LogStatus.SKIP,task+" should be present in Next Activity Section",YesNo.Yes);
					}
					
					task = SmokeCTLogACall1Subject;
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, task, SubjectElement.SubjectLink, 10);
					if (ele!=null) {
						log(LogStatus.INFO,task+" is present in Past Activity Section",YesNo.No);	
					} else {
						sa.assertTrue(false,task+" should be present in Past Activity Section");
						log(LogStatus.SKIP,task+" should be present in Past Activity Section",YesNo.Yes);
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
	
	@Parameters({"projectName"})
	@Test
	public void SmokeTc070_VerifyTheContactTransferButton_Action(String projectName) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		appLog.info("Login with User");
		appLog.info("Going on Contact Tab");
		TabName tabName = TabName.Object2Tab;
		String navatarCTCon1= SmokeCTContact1FName+" "+SmokeCTContact1LName;
		if (bp.clickOnTab(projectName, tabName)) {
			if (cp.clickOnAlreadyCreatedItem(projectName, tabName, navatarCTCon1, 20)) {
				log(LogStatus.INFO, "Click on Created Contact : " + navatarCTCon1, YesNo.No);
				ThreadSleep(2000);
				if (cp.clickOnShowMoreActionDownArrow(projectName, PageName.Object2Page, ShowMoreActionDropDownList.Contact_Transfer, 10)) {
					log(LogStatus.INFO, "Clicked on Contact Transfer", YesNo.No);	

					if (cp.enteringValueforLegalNameOnContactTransferPage(projectName, SmokeCTIns1, 10)) {
						log(LogStatus.PASS, "Able to Transfer Contact", YesNo.No);
						ThreadSleep(2000);
						refresh(driver);

						if (cp.fieldValueVerification(projectName, PageName.Object2Page, PageLabel.Account_Name, SmokeCTIns1, 5)) {
							log(LogStatus.PASS, "Label Verified after contact Transfer", YesNo.Yes);	
							ThreadSleep(2000);
						} else {
							sa.assertTrue(false, "Label Not Verified after contact Transfer");
							log(LogStatus.FAIL, "Label Not Verified after contact Transfer", YesNo.Yes);
						}


					} else {
						sa.assertTrue(false, "Not Able to Transfer Contact");
						log(LogStatus.FAIL, "Not Able to Transfer Contact", YesNo.Yes);
					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Contact Transfer");
					log(LogStatus.SKIP, "Not Able to Click on Contact Transfer", YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Created Contact : " + navatarCTCon1);
				log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + navatarCTCon1,YesNo.Yes);

			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Contact Tab");
			log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
		appLog.info("Pass");
	}

	@Parameters({"projectName"})
	@Test
	public void SmokeTc070_VerifyTheContactTransferButton_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele ;
		TabName tabName =TabName.Object1Tab;
		String ctAccount ;
		String ctAccount1 = SmokeCTIns;
		String ctAccount2 = SmokeCTIns2;
		for (int j = 0; j < 2; j++) {

			if (j==0) {
				ctAccount = ctAccount1;
			} else {
				ctAccount = ctAccount2;
			}

			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+ctAccount,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, tabName, ctAccount, 20)) {
					log(LogStatus.INFO,"Clicked on  : "+ctAccount+" For : "+tabName,YesNo.No);
					ThreadSleep(1000);
					if (j==0 || j==1) {

						String task = SmokeCTTask1Subject;
						ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, task, SubjectElement.SubjectLink, 10);
						if (ele!=null) {
							log(LogStatus.INFO,task+" is present in Next Activity Section in old institution "+ctAccount,YesNo.No);	
						} else {
							sa.assertTrue(false,task+" should be present in Next Activity Section in old institution "+ctAccount);
							log(LogStatus.SKIP,task+" should be present in Next Activity Section in old institution "+ctAccount,YesNo.Yes);
						}
						
						task = SmokeCTEvent1Subject;
						ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, task, SubjectElement.SubjectLink, 10);
						if (ele!=null) {
							log(LogStatus.INFO,task+" is present in Next Activity Section in old institution "+ctAccount,YesNo.No);	
						} else {
							sa.assertTrue(false,task+" should be present in Next Activity Section in old institution "+ctAccount);
							log(LogStatus.SKIP,task+" should be present in Next Activity Section in old institution "+ctAccount,YesNo.Yes);
						}
						
						task = SmokeCTLogACall1Subject;
						ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, task, SubjectElement.SubjectLink, 10);
						if (ele!=null) {
							log(LogStatus.INFO,task+" is present in Past Activity Section in old institution "+ctAccount,YesNo.No);	
						} else {
							sa.assertTrue(false,task+" should be present in Past Activity Section in old institution "+ctAccount);
							log(LogStatus.SKIP,task+" should be present in Past Activity Section in old institution "+ctAccount,YesNo.Yes);
						}
						
					} else {

//						String task = SmokeCTTask1Subject;
//						ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, task, SubjectElement.SubjectLink, 10);
//						if (ele==null) {
//							log(LogStatus.INFO,task+" is not present in Next Activity Section in new institution "+ctAccount,YesNo.No);	
//						} else {
//							sa.assertTrue(false,task+" should not be present in Next Activity Section in new institution "+ctAccount);
//							log(LogStatus.SKIP,task+" should not be present in Next Activity Section in new institution "+ctAccount,YesNo.Yes);
//						}
//						
//						task = SmokeCTEvent1Subject;
//						ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, task, SubjectElement.SubjectLink, 10);
//						if (ele==null) {
//							log(LogStatus.INFO,task+" is not present in Next Activity Section in new institution "+ctAccount,YesNo.No);	
//						} else {
//							sa.assertTrue(false,task+" should not be present in Next Activity Section in new institution "+ctAccount);
//							log(LogStatus.SKIP,task+" should not be present in Next Activity Section in new institution "+ctAccount,YesNo.Yes);
//						}
//						
//						task = SmokeCTLogACall1Subject;
//						ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, task, SubjectElement.SubjectLink, 10);
//						if (ele==null) {
//							log(LogStatus.INFO,task+" is not present in Past Activity Section in new institution "+ctAccount,YesNo.No);	
//						} else {
//							sa.assertTrue(false,task+" should not be present in Past Activity Section in new institution "+ctAccount);
//							log(LogStatus.SKIP,task+" should not be present in Past Activity Section in new institution "+ctAccount,YesNo.Yes);
//						}
						
					}

				} else {
					sa.assertTrue(false,"Item Not Found : "+ctAccount+" For : "+tabName);
					log(LogStatus.SKIP,"Item Not Found : "+ctAccount+" For : "+tabName,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+ctAccount);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+ctAccount,YesNo.Yes);
			}


		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc071_CreateACompanyAndSourceFirmAndSourceContact(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String value="";
		String type="";
		String[][] EntityOrAccounts = {{ SmokeDealIns1, SmokeDealIns1RecordType ,null},
				{ SmokeDealIns2, SmokeDealIns2RecordType ,null}};
		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, tabObj1)) {
				log(LogStatus.INFO,"Click on Tab : "+tabObj1,YesNo.No);	
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

		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			SmokeDealContact1EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, SmokeDealContact1EmailID, "Contacts", excelLabel.Variable_Name, "SMOKEDEALCON1",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, SmokeDealContact1FName, SmokeDealContact1LName, SmokeDealContact1Inst, SmokeDealContact1EmailID,SmokeDealContact1RecordType, null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+SmokeDealContact1FName+" "+SmokeDealContact1LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+SmokeDealContact1FName+" "+SmokeDealContact1LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+SmokeDealContact1FName+" "+SmokeDealContact1LName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}

		boolean flag = false;
		WebElement ele=null;
		String createNewNavigationLink=CreateNew_DefaultValues.New_Deal.toString();
		flag=false;
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName+" Going to click on : "+createNewNavigationLink+" for creation ", YesNo.No);

			ele = npbl.getNavigationLabel(projectName, createNewNavigationLink, action.BOOLEAN, 10);
			if (ele!=null && click(driver, ele, createNewNavigationLink, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on "+createNewNavigationLink+" so going for creation", YesNo.No);
				flag = true;
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+createNewNavigationLink+" so cannot create data related to this ", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+createNewNavigationLink+" so cannot create data related to this ");

			}

			if (flag) {
				String[][] labelswithValues = {{excelLabel.Source_Contact.toString(),SmokeDealContact1FName+" "+SmokeDealContact1LName},{excelLabel.Source_Firm.toString(),SmokeDealIns2}};
				if (fp.createDealPopUp(projectName,SmokeDeal1RecordType,SmokeDeal1,SmokeDeal1CompanyName, SmokeDeal1Stage,labelswithValues, 15)) {
					log(LogStatus.INFO,"Created Deal : "+SmokeDeal1+" through "+createNewNavigationLink,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Deal  : "+" through "+createNewNavigationLink);
					log(LogStatus.SKIP,"Not Able to Create Deal  : "+" through "+createNewNavigationLink,YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on : "+createNewNavigationLink+" for creation ", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on : "+createNewNavigationLink+" for creation ");
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on : "+createNewNavigationLink+" for creation ", YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on : "+createNewNavigationLink+" for creation ");
		}


		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName"})
	@Test
	public void SmokeTc072_VerifyTheDealAndLinkontheDealDetailPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
		String label="";
		String value="";
		String dealName=SmokeDeal1;
		String header="";
		if (cp.clickOnTab(projectName, DealTab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+DealTab+" For : "+dealName,YesNo.No);
			ThreadSleep(1000);
			String[][] labelWithValues = { 
					{excelLabel.Company_Name.toString(),SmokeDealIns1,Header.Institution.toString()},
					{excelLabel.Source_Contact.toString(),SmokeDealContact1FName+" "+SmokeDealContact1LName,Header.Contact.toString()},
					{excelLabel.Source_Firm.toString(),SmokeDealIns2,Header.Institution.toString()}} ;
			if (cp.clickOnAlreadyCreatedItem(projectName, dealName, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+dealName+" For : "+DealTab,YesNo.No);
				ThreadSleep(2000);
				for (String[] labelWithValue : labelWithValues) {
					label=labelWithValue[0];
					value=labelWithValue[1];
					header=labelWithValue[2];
					ele=cp.getElementAtPage(projectName, label, value, action.SCROLLANDBOOLEAN, 20);
					if (ele!=null) {
						log(LogStatus.INFO, label+" with value "+value+" not found", YesNo.No);
						if (click(driver, ele, value, action.BOOLEAN)) {
							log(LogStatus.INFO, "Click on "+value, YesNo.Yes);	
							ThreadSleep(2000);
							ele = cp.verifyCreatedItemOnPage(header, value);
							if (ele!=null) {
								log(LogStatus.INFO, "On "+header+" Page "+value+" verified", YesNo.Yes);		
							} else {
								log(LogStatus.ERROR, "On "+header+" Page "+value+" not verified", YesNo.Yes);
								sa.assertTrue(false,"On "+header+" Page "+value+" not verified");
							}
							driver.navigate().back();
							ThreadSleep(5000);
						} else {
							log(LogStatus.ERROR, "Not Able to Click on "+value, YesNo.Yes);
							sa.assertTrue(false,"Not Able to Click on "+value);

						}
					}else {
						log(LogStatus.ERROR, label+" with value "+value+" not found", YesNo.Yes);
						sa.assertTrue(false,label+" with value "+value+" not found");
					}
				}

			} else {
				sa.assertTrue(false,"Item Not Found : "+dealName+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+dealName+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+dealName);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+dealName,YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc073_VerifyListViewsonDealPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
		String dealName=SmokeDeal1;
		String[] viewLists = {"Active Deals","All","Deals Needing Attention","Recently Viewed"};
		if (cp.clickOnTab(projectName, DealTab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+DealTab+" For : "+dealName,YesNo.No);
			ThreadSleep(1000);
			click(driver, cp.getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN);
			for (String viewList : viewLists) {
				ele=cp.getViewListElement(viewList);;
				if (ele!=null) {
					log(LogStatus.INFO, viewList+" is present on "+DealTab , YesNo.No);
				} else {
					log(LogStatus.ERROR, viewList+" should be present on "+DealTab, YesNo.Yes);
					sa.assertTrue(false,  viewList+" should be present on "+DealTab);
				}	
			}
			click(driver, cp.getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN);
			if (cp.clickOnAlreadyCreatedItem(projectName, dealName, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+dealName+" For : "+DealTab,YesNo.No);
				ThreadSleep(2000);

			} else {
				sa.assertTrue(false,"Item Not Found : "+dealName+" For : "+tabObj2);
				log(LogStatus.SKIP,"Item Not Found : "+dealName+" For : "+tabObj2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+dealName);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+dealName,YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void SmokeTc074_VerifyTheDealQualityScoreHighestStageReachedAndLastStagechangeDateForAllRecordPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=dealReceivedScore;totalDealsshown=1;averageDealQualityScore=dealQualityScore/totalDealsshown;
		WebElement ele;
		String label="";
		String value="";
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			log(LogStatus.INFO,"Click on deal tab",YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, SmokeDeal1, 10)){
				log(LogStatus.INFO," Able to click "+SmokeDeal1,YesNo.No);
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"click on details tab",YesNo.No);

					String labelWithValues[][] = {{excelLabel.Highest_Stage_Reached.toString(),SmokeDeal1Stage},
							{excelLabel.Stage.toString(),SmokeDeal1Stage},
							{excelLabel.Deal_Quality_Score.toString(),String.valueOf(dealQualityScore)}};

					for (String[] lbWithValue : labelWithValues) {
						label=lbWithValue[0];
						value=lbWithValue[1];
						if (fp.FieldValueVerificationOnFundPage(projectName, label,value)) {
							log(LogStatus.INFO, label+" with value "+value+" verified", YesNo.No);

						}else {
							log(LogStatus.ERROR, label+" with value "+value+" not verified", YesNo.Yes);
							sa.assertTrue(false,label+" with value "+value+" not verified");
						}
					}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+SmokeDeal1);
				log(LogStatus.SKIP,"Not Able to click "+SmokeDeal1,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={SmokeDealIns2,SmokeDealContact1FName+" "+SmokeDealContact1LName};
		int j=0;
		for (TabName tab:tabName) {
			if (lp.clickOnTab(projectName, tab)) {
				log(LogStatus.INFO,"Click on tab for "+records[j],YesNo.No);
				if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
					log(LogStatus.INFO,"Click on "+records[j],YesNo.No);
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Click on deatails tab for "+records[j],YesNo.No);

						String labelWithValues[][] = {{excelLabel.Average_Deal_Quality_Score.toString(),String.valueOf(averageDealQualityScore)}};

						for (String[] lbWithValue : labelWithValues) {
							label=lbWithValue[0];
							value=lbWithValue[1];
							if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, label,value)) {
								log(LogStatus.INFO, label+" with value "+value+" verified", YesNo.No);

							}else {
								log(LogStatus.ERROR, label+" with value "+value+" not verified", YesNo.Yes);
								sa.assertTrue(false,label+" with value "+value+" not verified");
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
	public void SmokeTc075_ChangeTheDealStageAndVerifyTheDealQualityScore(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=managementMeetingScore;totalDealsshown=1;averageDealQualityScore=dealQualityScore/totalDealsshown;
		WebElement ele;
		String label="";
		String value="";
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			log(LogStatus.INFO,"Click on deal tab",YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, SmokeDeal1, 10)){
				log(LogStatus.INFO," Able to click "+SmokeDeal1,YesNo.No);

				if (fp.changeStage(projectName, Stage.Management_Meeting.toString(), 10)) {	
					log(LogStatus.INFO,"not able to change stage to "+Stage.Management_Meeting,YesNo.No);
				}else {
					sa.assertTrue(false,"not able to change stage to "+Stage.Management_Meeting);
					log(LogStatus.SKIP,"not able to change stage to "+Stage.Management_Meeting,YesNo.Yes);
				}


				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"click on details tab",YesNo.No);

					String labelWithValues[][] = {{excelLabel.Highest_Stage_Reached.toString(),SmokeDeal1Stage},
							{excelLabel.Stage.toString(),SmokeDeal1Stage},
							{excelLabel.Deal_Quality_Score.toString(),String.valueOf(dealQualityScore)}};

					for (String[] lbWithValue : labelWithValues) {
						label=lbWithValue[0];
						value=lbWithValue[1];
						if (fp.FieldValueVerificationOnFundPage(projectName, label,value)) {
							log(LogStatus.INFO, label+" with value "+value+" verified", YesNo.No);

						}else {
							log(LogStatus.ERROR, label+" with value "+value+" not verified", YesNo.Yes);
							sa.assertTrue(false,label+" with value "+value+" not verified");
						}
					}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+SmokeDeal1);
				log(LogStatus.SKIP,"Not Able to click "+SmokeDeal1,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}


		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={SmokeDealIns2,SmokeDealContact1FName+" "+SmokeDealContact1LName};
		int j=0;
		for (TabName tab:tabName) {
			if (lp.clickOnTab(projectName, tab)) {
				log(LogStatus.INFO,"Click on tab for "+records[j],YesNo.No);
				if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
					log(LogStatus.INFO,"Click on "+records[j],YesNo.No);
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Click on deatails tab for "+records[j],YesNo.No);

						String labelWithValues[][] = {{excelLabel.Average_Deal_Quality_Score.toString(),String.valueOf(averageDealQualityScore)}};

						for (String[] lbWithValue : labelWithValues) {
							label=lbWithValue[0];
							value=lbWithValue[1];
							if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, label,value)) {
								log(LogStatus.INFO, label+" with value "+value+" verified", YesNo.No);

							}else {
								log(LogStatus.ERROR, label+" with value "+value+" not verified", YesNo.Yes);
								sa.assertTrue(false,label+" with value "+value+" not verified");
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
	public void SmokeTc076_VerifyTheStagePathOnTheDealDetailPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele;
		String label="";
		String value="";
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			log(LogStatus.INFO,"Click on deal tab",YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, SmokeDeal1, 10)){
				log(LogStatus.INFO," Able to click "+SmokeDeal1,YesNo.No);
				String stage = DealStage.Current.toString();
				String stageValue = Stage.Management_Meeting.toString();
				ele =  dp.getStagePath(stage,stageValue);
				if (ele!=null) {
					log(LogStatus.INFO,"Stage Path have "+stage+" stage as "+stageValue,YesNo.No);
				}else {
					sa.assertTrue(false,"Stage Path should have "+stage+" stage as "+stageValue);
					log(LogStatus.SKIP,"Stage Path Should have "+stage+" stage as "+stageValue,YesNo.Yes);
				}
				ele = dp.getMarkStageCompleteButton(10);
				if (click(driver, ele, "Mark Stage as Complete button ", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"click on Mark Stage as Complete button ",YesNo.No);
					ThreadSleep(10000);
					stage = DealStage.Completed.toString();
					stageValue = Stage.Management_Meeting.toString();
					ele =  dp.getStagePath(stage,stageValue);
					if (ele!=null) {
						log(LogStatus.INFO,"Stage Path have "+stage+" stage as "+stageValue+" after click on Mark Stage as Complete button ",YesNo.No);
					}else {
						sa.assertTrue(false,"Stage Path should have "+stage+" stage as "+stageValue+" after click on Mark Stage as Complete button ");
						log(LogStatus.SKIP,"Stage Path Should have "+stage+" stage as "+stageValue+" after click on Mark Stage as Complete button ",YesNo.Yes);
					}

					stage = DealStage.Current.toString();
					stageValue = Stage.IOI.toString();
					ele =  dp.getStagePath(stage,stageValue);
					if (ele!=null) {
						log(LogStatus.INFO,"Stage Path have "+stage+" stage as "+stageValue+" after click on Mark Stage as Complete button ",YesNo.No);
					}else {
						sa.assertTrue(false,"Stage Path should have "+stage+" stage as "+stageValue+" after click on Mark Stage as Complete button ");
						log(LogStatus.SKIP,"Stage Path Should have "+stage+" stage as "+stageValue+" after click on Mark Stage as Complete button ",YesNo.Yes);
					}

					stageValue = Stage.IOI.toString();
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"click on details tab",YesNo.No);

						String labelWithValues[][] = {{excelLabel.Highest_Stage_Reached.toString(),stageValue},
								{excelLabel.Stage.toString(),stageValue}};

						for (String[] lbWithValue : labelWithValues) {
							label=lbWithValue[0];
							value=lbWithValue[1];
							if (fp.FieldValueVerificationOnFundPage(projectName, label,value)) {
								log(LogStatus.INFO, label+" with value "+value+" verified", YesNo.No);

							}else {
								log(LogStatus.ERROR, label+" with value "+value+" not verified", YesNo.Yes);
								sa.assertTrue(false,label+" with value "+value+" not verified");
							}
						}
					}else {
						sa.assertTrue(false,"not able to click on details tab");
						log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
					}

				}else {
					sa.assertTrue(false,"Not able to click on Mark Stage as Complete button ");
					log(LogStatus.SKIP,"Not able to click on Mark Stage as Complete button ",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+SmokeDeal1);
				log(LogStatus.SKIP,"Not Able to click "+SmokeDeal1,YesNo.Yes);
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
	public void SmokeTc077_CreateACompanyAndSourceFirmAndSourceContact(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String value="";
		String type="";
		String[][] EntityOrAccounts = {{ SmokePFIns1, SmokePFIns1RecordType ,null},
				{ SmokePFIns2, SmokePFIns2RecordType ,null}};
		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, tabObj1)) {
				log(LogStatus.INFO,"Click on Tab : "+tabObj1,YesNo.No);	
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

		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			SmokePFContact1EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, SmokePFContact1EmailID, "Contacts", excelLabel.Variable_Name, "SMOKEPFCON1",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, SmokePFContact1FName, SmokePFContact1LName, SmokePFContact1Inst, SmokePFContact1EmailID,SmokePFContact1RecordType, null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+SmokePFContact1FName+" "+SmokePFContact1LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+SmokePFContact1FName+" "+SmokePFContact1LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+SmokePFContact1FName+" "+SmokePFContact1LName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}

		boolean flag = false;
		WebElement ele=null;
		String createNewNavigationLink=CreateNew_DefaultValues.New_Deal.toString();
		flag=false;
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName+" Going to click on : "+createNewNavigationLink+" for creation ", YesNo.No);

			ele = npbl.getNavigationLabel(projectName, createNewNavigationLink, action.BOOLEAN, 10);
			if (ele!=null && click(driver, ele, createNewNavigationLink, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on "+createNewNavigationLink+" so going for creation", YesNo.No);
				flag = true;
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+createNewNavigationLink+" so cannot create data related to this ", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+createNewNavigationLink+" so cannot create data related to this ");

			}

			if (flag) {
				String[][] labelswithValues = {{excelLabel.Source_Contact.toString(),SmokePFContact1FName+" "+SmokePFContact1LName},{excelLabel.Source_Firm.toString(),SmokePFIns2}};
				if (fp.createDealPopUp(projectName,SmokeDeal2RecordType,SmokeDeal2,SmokeDeal2CompanyName, SmokeDeal2Stage,labelswithValues, 25)) {
					log(LogStatus.INFO,"Created Deal : "+SmokeDeal2+" through "+createNewNavigationLink,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Deal  : "+" through "+createNewNavigationLink);
					log(LogStatus.SKIP,"Not Able to Create Deal  : "+" through "+createNewNavigationLink,YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on : "+createNewNavigationLink+" for creation ", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on : "+createNewNavigationLink+" for creation ");
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on : "+createNewNavigationLink+" for creation ", YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on : "+createNewNavigationLink+" for creation ");
		}


		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();

	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc078_VerifyConvertToPortfolioButtonForTheDealRecord(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			log(LogStatus.INFO,"Click on deal tab",YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, SmokeDeal2, 10)){
				log(LogStatus.INFO," Able to click "+SmokeDeal2,YesNo.No);	

				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					log(LogStatus.INFO,"click on convert to portfolio button",YesNo.No);

					if (dp.getconvertToPortfolioMessage(SmokeDeal2,10)!=null) {
						log(LogStatus.INFO, "successfully verified convert to portfolio text message : "+SmokeDeal2, YesNo.No);
					}else {
						sa.assertTrue(false,"could not verify convert to portfolio text message before next : "+SmokeDeal2);
						log(LogStatus.SKIP,"could not verify convert to portfolio text message before next : "+SmokeDeal2,YesNo.Yes);
					}

					if (dp.getconvertToPortfolioMessage(DealPageErrorMessage.convertingPortfoliaMsg,10)!=null) {
						log(LogStatus.INFO, "successfully verified convert to portfolio text message : "+DealPageErrorMessage.convertingPortfoliaMsg, YesNo.No);
					}else {
						sa.assertTrue(false,"could not verify convert to portfolio text message before next : "+DealPageErrorMessage.convertingPortfoliaMsg);
						log(LogStatus.SKIP,"could not verify convert to portfolio text message before next : "+DealPageErrorMessage.convertingPortfoliaMsg,YesNo.Yes);
					}

					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						log(LogStatus.INFO, "successfully clicked next button", YesNo.No);
						if(dp.getconvertToPortfolioMessageAfterNext( 10)!=null) {
							if(dp.getconvertToPortfolioMessageAfterNext( 10).getText().contains(M6Ins2) && 
									dp.getconvertToPortfolioMessageAfterNext( 10).getText().contains(dp.convertToPortfolioAfterNext(M6Ins2))) {
								log(LogStatus.INFO, "successfully verified after next convert to portfolio text message", YesNo.No);
							}else {
								sa.assertTrue(false,"could not verify after next convert to portfolio text message");
								log(LogStatus.SKIP,"could not verify after next convert to portfolio text message",YesNo.Yes);
							}

						}else {
							sa.assertTrue(false,"could not verify after next convert to portfolio text message");
							log(LogStatus.SKIP,"could not verify after next convert to portfolio text message",YesNo.Yes);
						}


						if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "successfully verified finish button after convert to portfolio", YesNo.No);


							String labelWithValues[][] = {{excelLabel.Stage.toString(),Stage.Closed.toString()}};

							for (String[] lbWithValue : labelWithValues) {
								String label = lbWithValue[0];
								String value = lbWithValue[1];
								if (fp.FieldValueVerificationOnFundPage(projectName, label,value)) {
									log(LogStatus.INFO, label+" with value "+value+" verified after converting to portfolio", YesNo.No);

								}else {
									log(LogStatus.ERROR, label+" with value "+value+" not verified after converting to portfolio", YesNo.Yes);
									sa.assertTrue(false,label+" with value "+value+" not verified after converting to portfolio");
								}
							}

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
				sa.assertTrue(false,"Not Able to click "+SmokeDeal2);
				log(LogStatus.SKIP,"Not Able to click "+SmokeDeal2,YesNo.Yes);
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
	public void SmokeTc079_VerifyTheDealQualityScoreHighestStageReachedAfterConvetingToPortFolio(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=closedScore;totalDealsshown=1;averageDealQualityScore=dealQualityScore/totalDealsshown;
		WebElement ele;
		String label="";
		String value="";
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			log(LogStatus.INFO,"Click on deal tab",YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, SmokeDeal2, 10)){
				log(LogStatus.INFO," Able to click "+SmokeDeal2,YesNo.No);
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"click on details tab",YesNo.No);
					SmokeDeal2Stage=Stage.Closed.toString();
					String labelWithValues[][] = {{excelLabel.Highest_Stage_Reached.toString(),SmokeDeal2Stage},
							{excelLabel.Stage.toString(),SmokeDeal2Stage},
							{excelLabel.Deal_Quality_Score.toString(),String.valueOf(dealQualityScore)}};

					for (String[] lbWithValue : labelWithValues) {
						label=lbWithValue[0];
						value=lbWithValue[1];
						if (fp.FieldValueVerificationOnFundPage(projectName, label,value)) {
							log(LogStatus.INFO, label+" with value "+value+" verified", YesNo.No);

						}else {
							log(LogStatus.ERROR, label+" with value "+value+" not verified", YesNo.Yes);
							sa.assertTrue(false,label+" with value "+value+" not verified");
						}
					}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+SmokeDeal2);
				log(LogStatus.SKIP,"Not Able to click "+SmokeDeal2,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={SmokePFIns2,SmokePFContact1FName+" "+SmokePFContact1LName};
		int j=0;
		for (TabName tab:tabName) {
			if (lp.clickOnTab(projectName, tab)) {
				log(LogStatus.INFO,"Click on tab for "+records[j],YesNo.No);
				if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
					log(LogStatus.INFO,"Click on "+records[j],YesNo.No);
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Click on deatails tab for "+records[j],YesNo.No);

						String labelWithValues[][] = {{excelLabel.Average_Deal_Quality_Score.toString(),String.valueOf(averageDealQualityScore)}};

						for (String[] lbWithValue : labelWithValues) {
							label=lbWithValue[0];
							value=lbWithValue[1];
							if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, label,value)) {
								log(LogStatus.INFO, label+" with value "+value+" verified", YesNo.No);

							}else {
								log(LogStatus.ERROR, label+" with value "+value+" not verified", YesNo.Yes);
								sa.assertTrue(false,label+" with value "+value+" not verified");
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
	
	
}
	

