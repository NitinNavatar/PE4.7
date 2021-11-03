package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.navatar.generic.BaseLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.ActivityTimeLineItem;
import com.navatar.generic.EnumConstants.ActivityType;
import com.navatar.generic.EnumConstants.AppSetting;
import com.navatar.generic.EnumConstants.CreateNew_DefaultValues;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.FundraisingContactPageTab;
import com.navatar.generic.EnumConstants.GlobalActionItem;
import com.navatar.generic.EnumConstants.InstitutionPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.NavigationMenuItems;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.SearchBasedOnExistingFundsOptions;
import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
import com.navatar.generic.EnumConstants.Stage;
import com.navatar.generic.EnumConstants.SubjectElement;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.columnName;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;
import com.navatar.pageObjects.AffiliationPageBusinessLayer;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.ContactTransferTabBusinessLayer;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.DealPageBusinessLayer;
import com.navatar.pageObjects.DealPageErrorMessage;
import com.navatar.pageObjects.FinancialPerformancePageBusinessLayer;
import com.navatar.pageObjects.FinancingPageBusinessLayer;
import com.navatar.pageObjects.FundRaisingPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.GlobalActionPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.MarketInitiativePageBusinessLayer;
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
				if (ip.createEntityOrAccount(projectName,"", value, type,null, null, 20)) {
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
				if (ip.createEntityOrAccount(projectName,"", value, type,null, null, 20)) {
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
							if(dp.getconvertToPortfolioMessageAfterNext( 10).getText().contains(SmokeDeal2CompanyName) && 
									dp.getconvertToPortfolioMessageAfterNext( 10).getText().contains(dp.convertToPortfolioAfterNext(SmokeDeal2CompanyName))) {
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
		//////////////////

		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Click on institution tab",YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, SmokeDeal2CompanyName, 10)){
				log(LogStatus.INFO," Able to click "+SmokeDeal2CompanyName,YesNo.No);
				
				String labelWithValues[][] = {{excelLabel.Record_Type.toString(),"Portfolio Company"}};
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
				sa.assertTrue(false,"Not Able to click "+SmokeDeal2CompanyName);
				log(LogStatus.SKIP,"Not Able to click "+SmokeDeal2CompanyName,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on institution tab");
			log(LogStatus.SKIP,"not able to click on institution tab",YesNo.Yes);
		}
		
		
		////////////////
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc079_VerifyTheDealQualityScoreHighestStageReachedAfterConvetingToPortFolio(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
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

				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					log(LogStatus.INFO,"click on convert to portfolio button",YesNo.No);

					if (dp.getconvertToPortfolioMessage(SmokeDeal2,10)!=null) {
						log(LogStatus.INFO, "successfully verified convert to portfolio text message : "+SmokeDeal2, YesNo.No);
					}else {
						sa.assertTrue(false,"could not verify convert to portfolio text message before next : "+SmokeDeal2);
						log(LogStatus.SKIP,"could not verify convert to portfolio text message before next : "+SmokeDeal2,YesNo.Yes);
					}

					if (dp.getconvertToPortfolioMessage1(DealPageErrorMessage.convertingPortfoliaMsg,10)!=null) {
						log(LogStatus.INFO, "successfully verified convert to portfolio text message : "+DealPageErrorMessage.convertingPortfoliaMsg, YesNo.No);
					}else {
						sa.assertTrue(false,"could not verify convert to portfolio text message before next : "+DealPageErrorMessage.convertingPortfoliaMsg);
						log(LogStatus.SKIP,"could not verify convert to portfolio text message before next : "+DealPageErrorMessage.convertingPortfoliaMsg,YesNo.Yes);
					}

					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						log(LogStatus.INFO, "successfully clicked next button", YesNo.No);
						
						ThreadSleep(4000);
						
						if (dp.getconvertToPortfolioMessageRepeat("Convert to Portfolio",10)!=null) {
							
							String text=dp.getconvertToPortfolioMessageRepeat("Convert to Portfolio",10).getText().trim();
							String expected = dp.convertToPortfolioRepeat(SmokePFIns1);
							if (text.contains(expected)) {
								log(LogStatus.INFO,"successfully verified already portfolio message : "+expected,YesNo.Yes);
								
							}else {
								sa.assertTrue(false,"could not verify already portfolio message\nExpected: "+text+"\nActual: "+expected);
								log(LogStatus.SKIP,"could not verify already portfolio message\\nExpected: "+text+"\nActual: "+expected,YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"not visible already portfolio message");
							log(LogStatus.SKIP,"not visible already portfolio message",YesNo.Yes);
						}
						


						if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "successfully clicked on finish button after convert to portfolio", YesNo.No);


							String labelWithValues[][] = {{excelLabel.Stage.toString(),Stage.Closed.toString()},
																{excelLabel.Deal_Quality_Score.toString(),String.valueOf(dealQualityScore)}};

							for (String[] lbWithValue : labelWithValues) {
								 label = lbWithValue[0];
								 value = lbWithValue[1];
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
						int i=0;
						String[][] labelWithValues = { 
								{excelLabel.Company_Name.toString(),SmokePFIns1,Header.Institution.toString()},
								{excelLabel.Source_Contact.toString(),SmokePFContact1FName+" "+SmokePFContact1LName,Header.Contact.toString()},
								{excelLabel.Source_Firm.toString(),SmokePFIns2,Header.Institution.toString()}} ;
							ThreadSleep(2000);
							for (String[] labelWithValue : labelWithValues) {
								label=labelWithValue[0];
								value=labelWithValue[1];
								String header = labelWithValue[2];
								ele=cp.getElementAtPage(projectName, label, value, action.SCROLLANDBOOLEAN, 20);
								if (ele!=null) {
									log(LogStatus.INFO, label+" with value "+value+" found", YesNo.No);
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
										if (i==0) {
											label=excelLabel.Record_Type.toString();
											value="Portfolio Company";
										} else {
											label=excelLabel.Average_Deal_Quality_Score.toString();
											value=String.valueOf(averageDealQualityScore);
											
										}
										if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, label,value)) {
											log(LogStatus.INFO, label+" with value "+value+" verified", YesNo.No);

										}else {
											log(LogStatus.ERROR, label+" with value "+value+" not verified", YesNo.Yes);
											sa.assertTrue(false,label+" with value "+value+" not verified");
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
								i++;
								
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
	public void SmokeTc080_VerifyTheTabsButtonsAndHighlistPanelOnTheCreatedFundRecord(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		NavigationPageBusineesLayer np = new NavigationPageBusineesLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele;
		String tab="";
		
		// Fund 
		if (lp.clickOnTab(projectName, TabName.Object3Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object3Tab,YesNo.No);	
			String[] funds = {SmokeFund3,SmokeFund3Type,SmokeFund3Category,SmokeFund3RecordType};
			if (fp.createFundPE(projectName, funds[0], funds[3], funds[1], funds[2], null, 15)) {
				log(LogStatus.INFO,"Created Fund : "+funds[0],YesNo.No);	
				
				// Tabs
				String[] relatedTabs = {RelatedTab.Details.toString(),RelatedTab.Fundraising.toString()
						,RelatedTab.Fund_Management.toString(),RelatedTab.Fund_Investments.toString()
						,RelatedTab.Files.toString()};
				for (int i = 0; i < relatedTabs.length; i++) {
					tab = relatedTabs[i];
					ele=ip.getRelatedTab(projectName, tab, 10);
					if (ele!=null) {
						log(LogStatus.INFO,"Related Tab "+tab+" present at Fund",YesNo.No);	
					} else {
						sa.assertTrue(false,"Related Tab "+tab+" sholud be present at Fund");
						log(LogStatus.SKIP,"Related Tab "+tab+" sholud be present at Fund",YesNo.Yes);
					}
				}
				
				// Buttons
				np.clickOnShowMoreDropdownOnly(projectName);
				ShowMoreActionDropDownList[] buttons={ShowMoreActionDropDownList.Create_Distribution,
						ShowMoreActionDropDownList.Create_Drawdown,
						ShowMoreActionDropDownList.New_Partnership
						,ShowMoreActionDropDownList.New_Fundraising 
						,ShowMoreActionDropDownList.Bulk_Fundraising};
				
				ShowMoreActionDropDownList button=null;
				for (int i = 0; i < buttons.length; i++) {
					button=buttons[i];		
					ele =  np.actionDropdownElement(projectName, button, 10);
					if (ele!=null) {
						log(LogStatus.INFO,"Button "+button+" is present at Fund",YesNo.No);	
					} else {
						sa.assertTrue(false,"Button "+button+" sholud be present at Fund");
						log(LogStatus.SKIP,"Button "+button+" sholud be present at Fund",YesNo.Yes);
					}
					
				}
				
				String label="";
				String[] labels = {"Fund Type","Vintage Year","Target Commitments (mn)","Total Commitments (mn)","Total Capital Called (mn)"};
				for (int i = 0; i < labels.length; i++) {
					label=labels[i];
					ele =  fp.highLightFundLabel(label, 10);
					if (ele!=null) {
						log(LogStatus.INFO,label+" is present at Fund",YesNo.No);	
					} else {
						sa.assertTrue(false,label+" sholud be present at Fund");
						log(LogStatus.SKIP,label+" sholud be present at Fund",YesNo.Yes);
					}
				}
				
				ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object3Page,ActivityType.Next, "", SubjectElement.NextGrid, 10);;
				if (ele!=null) {
					log(LogStatus.INFO,"Activity grid is present for next",YesNo.No);
				} else {
					sa.assertTrue(false,"Activity grid not present for next");
					log(LogStatus.SKIP,"Activity grid not present for next",YesNo.Yes);
				}
				
				ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object3Page,ActivityType.Next, "", SubjectElement.PastGrid, 10);;
				if (ele!=null) {
					log(LogStatus.INFO,"Activity grid is present for Past",YesNo.No);
				} else {
					sa.assertTrue(false,"Activity grid not present for Past");
					log(LogStatus.SKIP,"Activity grid not present for Past",YesNo.Yes);
				}
				
			
			} else {
				sa.assertTrue(false,"Not Able to Create Fund : "+funds[0]);
				log(LogStatus.SKIP,"Not Able to Create Fund  : "+funds[0],YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object3Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object3Tab,YesNo.Yes);
		}
		
		
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc081_VerifyTheCreateFundraisingButton(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
		String fundName=SmokeFund3;
		if (cp.clickOnTab(projectName, tabObj3)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj3+" For : "+fundName,YesNo.No);
			if (lp.clickOnAlreadyCreatedItem(projectName, fundName, 10)){
				log(LogStatus.INFO," Able to click "+fundName,YesNo.No);	
				ele=lp.getRelatedTab(projectName, RelatedTab.Fundraising.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"click on Fundraising tab",YesNo.No);	
					if (click(driver,fund.getCreateFundRaisingBtn(environment, mode, PageName.FundsPage, 20),"create fundraising button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on Fundraising button", YesNo.No);
						switchToFrame(driver, 30, fund.getCreateFundraisingsFrame_Lighting(20));

						if(click(driver, home.getSearchBasedOnAccountsAndContactsTab(30), "Search Based On Accounts And Contacts Tab", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "click on Search Based On Accounts And Contacts Tab", YesNo.No);
							ThreadSleep(3000);
							if(home.applyFilterOnSearchBasedOnAccountsandContacts( FundraisingContactPageTab.SearchBasedOnAccountsAndContacts, SearchBasedOnExistingFundsOptions.AllContacts, environment,mode, null, "Contact:Contact Full Name", "not equal to", "", null)) {
								log(LogStatus.INFO, "apply filter logic", YesNo.No);
								click(driver, home.getSearchBasedOnAccountsAndContactsSearchBtn(30), "search button", action.SCROLLANDBOOLEAN);
								ThreadSleep(3000);
								if(home.selectInvestorsContactFromCreateFundRaising(2)) {
									log(LogStatus.INFO, "contact name is selected successfully",YesNo.No);
									if(click(driver, home.getAddToFundraisingListBtn(30), "Add To Fundraising List Button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "click on Add To Fundraising List", YesNo.No);
										if(click(driver, home.getCreateFundraisingBtn(PageName.CreateFundraisingPage, 30), "create fundraising button", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "clicked on create fundraising button", YesNo.No);
											if(click(driver,home.getCreateFundraisingConfirmationOkBtn(30), "ok button", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, "clicked on OK button", YesNo.No);
											}else {
												log(LogStatus.ERROR, "Not able to click on OK button of created fundraising", YesNo.Yes);
												sa.assertTrue(false, "Not able to click on OK button of created fundraising");
											} 

										}else {
											log(LogStatus.ERROR, "Not able to click on create fundraising button so cannot create fundraisings", YesNo.Yes);
											sa.assertTrue(false, "Not able to click on create fundraising button so cannot create fundraisings");
										}

									}else {
										log(LogStatus.ERROR, "Not able to click on Add To Fundraising List Button so cannot create fundraising", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on Add To Fundraising List Button so cannot create fundraising");
									}
								}else {
									log(LogStatus.ERROR, " Not able to select Contact Name from select investor grid so cannot create fundraising", YesNo.Yes);
									sa.assertTrue(false, " Not able to select Contact Name from select investor grid so cannot create fundraising");
								}

							}else {
								log(LogStatus.ERROR, "Not able to apply filter logic so cannot verify create fundraising page", YesNo.Yes);
								sa.assertTrue(false, "Not able to apply filter logic so cannot verify create fundraising page");
							}
						}else {
							log(LogStatus.ERROR, "Not able to click on Search Based On Accounts And Contacts Tab so cannot verify create fundraising page", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on Search Based On Accounts And Contacts Tab so cannot verify create fundraising page");
						}



					} else {
						log(LogStatus.ERROR, "Not able to click on  Fundraising button", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on  Fundraising button");
					}


				}else {
					sa.assertTrue(false,"not able to click on Fundraising tab");
					log(LogStatus.SKIP,"not able to click on Fundraising tab",YesNo.Yes);
				}


			}else {
				sa.assertTrue(false,"Not Able to click "+fundName);
				log(LogStatus.SKIP,"Not Able to click "+fundName,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj3+" For : "+fundName);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj3+" For : "+fundName,YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc082_VerifyListViewsonFundPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
		String fundName=SmokeFund3;
		String[] viewLists = {"All","Fund","Fund Of Funds","Recently Viewed"};
		if (cp.clickOnTab(projectName, tabObj3)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj3+" For : "+fundName,YesNo.No);
			ThreadSleep(1000);
			click(driver, cp.getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN);
			for (String viewList : viewLists) {
				ele=cp.getViewListElement(viewList);;
				if (ele!=null) {
					log(LogStatus.INFO, viewList+" is present on "+tabObj3 , YesNo.No);
				} else {
					log(LogStatus.ERROR, viewList+" should be present on "+tabObj3, YesNo.Yes);
					sa.assertTrue(false,  viewList+" should be present on "+tabObj3);
				}	
			}
			
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj3+" For : "+fundName);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj3+" For : "+fundName,YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc083_VerifyTheTabsButtonsAndHighlistPanelOnTheCreatedFundRaisingRecord(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		NavigationPageBusineesLayer np = new NavigationPageBusineesLayer(driver);
		WebElement ele;
		String tab="";
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		// FR
		FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);
		if(bp.clickOnTab(environment,mode,TabName.FundraisingsTab)) {
			if(fr.createFundRaising(environment,mode,SmokeFR1,SmokeFR1Fund,SmokeFR1LegalName, null, SmokeFR1Stage, null, null)){
				appLog.info("fundraising is created : "+SmokeFR1);
				
				// Tabs
				String[] relatedTabs = {RelatedTab.Details.toString(),RelatedTab.Fundraising_Contacts.toString(),RelatedTab.Files.toString()};
				for (int i = 0; i < relatedTabs.length; i++) {
					tab = relatedTabs[i];
					ele=ip.getRelatedTab(projectName, tab, 10);
					if (ele!=null) {
						log(LogStatus.INFO,"Related Tab "+tab+" present at fundraising",YesNo.No);	
					} else {
						sa.assertTrue(false,"Related Tab "+tab+" sholud be present at fundraising");
						log(LogStatus.SKIP,"Related Tab "+tab+" sholud be present at fundraising",YesNo.Yes);
					}
				}
				
				// Buttons
				np.clickOnShowMoreDropdownOnly(projectName);
				ShowMoreActionDropDownList[] buttons={ShowMoreActionDropDownList.Create_Commitments 
						,ShowMoreActionDropDownList.New_Fundraising_Contact};
				
				ShowMoreActionDropDownList button=null;
				for (int i = 0; i < buttons.length; i++) {
					button=buttons[i];		
					ele =  np.actionDropdownElement(projectName, button, 10);
					if (ele!=null) {
						log(LogStatus.INFO,"Button "+button+" is present at fundraising",YesNo.No);	
					} else {
						sa.assertTrue(false,"Button "+button+" sholud be present at fundraising");
						log(LogStatus.SKIP,"Button "+button+" sholud be present at fundraising",YesNo.Yes);
					}
					
				}
				
				String label="";
				String[] labels = {"Legal Name","Stage","Investment Likely Amount (mn)","Target Close Date","Closing"};
				for (int i = 0; i < labels.length; i++) {
					label=labels[i];
					ele =  fp.highLightFundLabel(label, 10);
					if (ele!=null) {
						log(LogStatus.INFO,label+" is present at fundraising",YesNo.No);	
					} else {
						sa.assertTrue(false,label+" sholud be present at fundraising");
						log(LogStatus.SKIP,label+" sholud be present at fundraising",YesNo.Yes);
					}
				}
				
				ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object3Page,ActivityType.Next, "", SubjectElement.NextGrid, 10);;
				if (ele!=null) {
					log(LogStatus.INFO,"Activity grid is present for next",YesNo.No);
				} else {
					sa.assertTrue(false,"Activity grid not present for next");
					log(LogStatus.SKIP,"Activity grid not present for next",YesNo.Yes);
				}
				
				ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object3Page,ActivityType.Next, "", SubjectElement.PastGrid, 10);;
				if (ele!=null) {
					log(LogStatus.INFO,"Activity grid is present for Past",YesNo.No);
				} else {
					sa.assertTrue(false,"Activity grid not present for Past");
					log(LogStatus.SKIP,"Activity grid not present for Past",YesNo.Yes);
				}
				
			}else {
				appLog.error("Not able to create fundraising: "+SmokeFR1);
				sa.assertTrue(false, "Not able to create fundraising: "+SmokeFR1);
			}
		}else {
			appLog.error("Not able to click on fundraising tab so cannot create fundraising: "+SmokeFR1);
			sa.assertTrue(false,"Not able to click on fundraising tab so cannot create fundraising: "+SmokeFR1);
		}
		
		
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc084_VerifyListViewsonFundRaisingPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
		String fundRaisingName=SmokeFR1;
		String[] viewLists = {"All","Fundraising Pipeline","Investor Commitments","Recently Viewed"};
		if (cp.clickOnTab(projectName, TabName.FundraisingsTab)) {
			log(LogStatus.INFO,"Clicked on Tab : "+TabName.FundraisingsTab+" For : "+fundRaisingName,YesNo.No);
			ThreadSleep(1000);
			click(driver, cp.getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN);
			for (String viewList : viewLists) {
				ele=cp.getViewListElement(viewList);;
				if (ele!=null) {
					log(LogStatus.INFO, viewList+" is present on "+TabName.FundraisingsTab , YesNo.No);
				} else {
					log(LogStatus.ERROR, viewList+" should be present on "+TabName.FundraisingsTab, YesNo.Yes);
					sa.assertTrue(false,  viewList+" should be present on "+TabName.FundraisingsTab);
				}	
			}
			
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj3+" For : "+fundRaisingName);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj3+" For : "+fundRaisingName,YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc085_VerifyTheStagePathOnTheFundRaisingPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele;
		String label="";
		String value="";
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
			log(LogStatus.INFO,"Click on Fundraisings tab",YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, SmokeFR1, 10)){
				log(LogStatus.INFO," Able to click "+SmokeFR1,YesNo.No);
				String stage = DealStage.Current.toString();
				String stageValue = SmokeFR1Stage;
				ele =  dp.getStagePath(stage,stageValue);
				if (ele!=null) {
					log(LogStatus.INFO,"Stage Path have "+stage+" stage as "+stageValue,YesNo.No);
				}else {
					sa.assertTrue(false,"Stage Path should have "+stage+" stage as "+stageValue);
					log(LogStatus.SKIP,"Stage Path Should have "+stage+" stage as "+stageValue,YesNo.Yes);
				}
				
				label=excelLabel.Last_Stage_Change_Date.toString();
				value="";
				if (fp.FieldValueVerificationOnFundPage(projectName, label,value)) {
					log(LogStatus.INFO, label+" with value "+value+" verified", YesNo.No);

				}else {
					log(LogStatus.ERROR, label+" with value "+value+" not verified", YesNo.Yes);
					sa.assertTrue(false,label+" with value "+value+" not verified");
				}
				
				ele = dp.getMarkStageCompleteButton(10);
				if (click(driver, ele, "Mark Stage as Complete button ", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"click on Mark Stage as Complete button ",YesNo.No);
					ThreadSleep(10000);
					stage = DealStage.Completed.toString();
					stageValue = SmokeFR1Stage;
					ele =  dp.getStagePath(stage,stageValue);
					if (ele!=null) {
						log(LogStatus.INFO,"Stage Path have "+stage+" stage as "+stageValue+" after click on Mark Stage as Complete button ",YesNo.No);
					}else {
						sa.assertTrue(false,"Stage Path should have "+stage+" stage as "+stageValue+" after click on Mark Stage as Complete button ");
						log(LogStatus.SKIP,"Stage Path Should have "+stage+" stage as "+stageValue+" after click on Mark Stage as Complete button ",YesNo.Yes);
					}

					stage = DealStage.Current.toString();
					stageValue = Stage.Sent_PPM.toString();
					ele =  dp.getStagePath(stage,stageValue);
					if (ele!=null) {
						log(LogStatus.INFO,"Stage Path have "+stage+" stage as "+stageValue+" after click on Mark Stage as Complete button ",YesNo.No);
					}else {
						sa.assertTrue(false,"Stage Path should have "+stage+" stage as "+stageValue+" after click on Mark Stage as Complete button ");
						log(LogStatus.SKIP,"Stage Path Should have "+stage+" stage as "+stageValue+" after click on Mark Stage as Complete button ",YesNo.Yes);
					}

					label=excelLabel.Last_Stage_Change_Date.toString();
					value=todaysDate;
					if (fp.FieldValueVerificationOnFundPage(projectName, label,value)) {
						log(LogStatus.INFO, label+" with value "+value+" verified", YesNo.No);

					}else {
						log(LogStatus.ERROR, label+" with value "+value+" not verified", YesNo.Yes);
						sa.assertTrue(false,label+" with value "+value+" not verified");
					}
			}else {
					sa.assertTrue(false,"Not able to click on Mark Stage as Complete button ");
					log(LogStatus.SKIP,"Not able to click on Mark Stage as Complete button ",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+SmokeFR1);
				log(LogStatus.SKIP,"Not Able to click "+SmokeFR1,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on Fundraisings tab");
			log(LogStatus.SKIP,"not able to click on Fundraisings tab",YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc086_ChangeTheFundraisingStageagAndVerifyTheImpactOnTheLastStageChangeDate(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String label="";
		String value="";
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
			log(LogStatus.INFO,"Click on Fundraisings tab",YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, SmokeFR1, 10)){
				log(LogStatus.INFO," Able to click "+SmokeFR1,YesNo.No);
				String stage = Stage.Interested.toString();
				if (fp.changeStage(projectName, stage, 10)) {	
					log(LogStatus.INFO,"not able to change stage to "+stage,YesNo.No);
				}else {
					sa.assertTrue(false,"not able to change stage to "+stage);
					log(LogStatus.SKIP,"not able to change stage to "+stage,YesNo.Yes);
				}
				
				
				String labelWithValues[][] = {{excelLabel.Last_Stage_Change_Date.toString(),todaysDate},
						{excelLabel.Stage.toString(),stage}};

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
				sa.assertTrue(false,"Not Able to click "+SmokeFR1);
				log(LogStatus.SKIP,"Not Able to click "+SmokeFR1,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on Fundraisings tab");
			log(LogStatus.SKIP,"not able to click on Fundraisings tab",YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc087_VerifyTheTabsRelatedListButtonsAndHighlightPanelOnTheFRContactage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer np = new NavigationPageBusineesLayer(driver);
		WebElement ele;
		String tab="Fundraising Contacts";
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String[][] labelWithValue = {{excelLabel.Fundraising.toString(),SmokeFR1} , 
				{excelLabel.Contact.toString(),SmokeCTContact1FName+" "+SmokeCTContact1LName},
				{excelLabel.Role.toString(),"Business User"}};
		if (lp.createItem(projectName,tab, labelWithValue, 10)) {
			log(LogStatus.INFO,"Able to create FR Contact",YesNo.No);
			
			if (lp.getSecondaryField(projectName, 10)==null) {
				log(LogStatus.INFO,"HighLight is not present at FR Contact",YesNo.No);
			} else {
				sa.assertTrue(false,"HighLight should not be present at FR Contact");
				log(LogStatus.SKIP,"HighLight should not be present at FR Contact",YesNo.Yes);
			}
			// Buttons
			np.clickOnShowMoreDropdownOnly(projectName);
			ShowMoreActionDropDownList[] buttons={ShowMoreActionDropDownList.Edit 
					,ShowMoreActionDropDownList.Delete
					,ShowMoreActionDropDownList.Clone,
					ShowMoreActionDropDownList.Printable_View};
			
			ShowMoreActionDropDownList button=null;
			for (int i = 0; i < buttons.length; i++) {
				button=buttons[i];		
				ele =  np.actionDropdownElement(projectName, button, 10);
				if (ele!=null) {
					log(LogStatus.INFO,"Button "+button+" is present at FR Contact",YesNo.No);	
				} else {
					sa.assertTrue(false,"Button "+button+" sholud be present at FR Contact");
					log(LogStatus.SKIP,"Button "+button+" sholud be present at FR Contact",YesNo.Yes);
				}
				
			}
			
			String[] relatedTabs = {RelatedTab.Details.toString(),RelatedTab.Related.toString()};
			for (int i = 0; i < relatedTabs.length; i++) {
				tab = relatedTabs[i];
				ele=lp.getRelatedTab(projectName, tab, 10);
				if (ele!=null) {
					log(LogStatus.INFO,"Related Tab "+tab+" present at FR Contact",YesNo.No);	
				} else {
					sa.assertTrue(false,"Related Tab "+tab+" sholud be present at FR Contact");
					log(LogStatus.SKIP,"Related Tab "+tab+" sholud be present at FR Contact",YesNo.Yes);
				}
				click(driver, ele, tab, action.BOOLEAN);
			}
			
			
			String[] relatedgrid = {"Files","Upcoming & Overdue"};
			int k=0;
			for (int i = 0; i < relatedgrid.length; i++) {
				tab = relatedgrid[i];
				if (i==1) {
					k=3;
				} else {
					k=2;
				}
				ele=lp.getRelatedListItem(tab, k);
				if (ele!=null) {
					log(LogStatus.INFO,"At Related Tab "+tab+" present at FR Contact",YesNo.No);	
				} else {
					sa.assertTrue(false,"At Related Tab "+tab+" sholud be present at FR Contact");
					log(LogStatus.SKIP,"At Related Tab "+tab+" sholud be present at FR Contact",YesNo.Yes);
				}
			}
			
			
			
		} else {
			sa.assertTrue(false,"Not able to create FR Contact");
			log(LogStatus.SKIP,"Not able to create FR Contact",YesNo.Yes);
		}
		
		
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc088_VerifyPickListValuesForRoleField(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject(environment, mode,object.Fundraising_Contact )) {
					log(LogStatus.INFO,"Searched & Clicked on "+object.Fundraising_Contact,YesNo.No);	
					if(sp.clickOnObjectFeature(environment, mode,object.Fundraising_Contact, ObjectFeatureName.FieldAndRelationShip)) {
						log(LogStatus.INFO, "object feature "+ObjectFeatureName.FieldAndRelationShip+" is clickable", YesNo.No);
						if (sendKeys(driver, sp.getsearchTextboxFieldsAndRelationships(10), PageLabel.Role.toString()+Keys.ENTER, "Fundraising_Contact Type", action.BOOLEAN)) {
							log(LogStatus.INFO,PageLabel.Role.toString()+" is clickable",YesNo.No);
							if (sp.clickOnAlreadyCreatedLayout(PageLabel.Role.toString())) {
								log(LogStatus.INFO,PageLabel.Role.toString()+" is clickable",YesNo.No);	
								switchToFrame(driver, 10, sp.getFrame(PageName.Fundraising_ContactPage, 10));

								String[] roleValues = {"Advisor","Business User","Decision Maker","Evaluator","Executive Sponsor","Gatekeeper","Influencer","Other"};
								for (int i = 0; i < roleValues.length; i++) {
									String role = roleValues[i];
									WebElement ele = sp.getValuesElementAtFieldRelationShip(projectName, role);
									if (ele!=null) {
										log(LogStatus.INFO,"Value picklist"+role+" present at Fundraising_Contact",YesNo.No);	
									} else {
										sa.assertTrue(false,"Value picklist"+role+" sholud be present at Fundraising_Contact");
										log(LogStatus.SKIP,"Value picklist"+role+" sholud be present at Fundraising_Contact",YesNo.Yes);
									}
								}
							}else {
								log(LogStatus.ERROR,PageLabel.Role.toString()+" is not clickable",YesNo.Yes);	
								sa.assertTrue(false,PageLabel.Role.toString()+"status field is not clickable" );
							}
						}else {
							log(LogStatus.ERROR,PageLabel.Role.toString()+" search is not visible",YesNo.Yes);	
							sa.assertTrue(false,PageLabel.Role.toString()+" search is not visible" );
						}
					}else {
						log(LogStatus.ERROR, "object feature "+ObjectFeatureName.FieldAndRelationShip+" is not clickable", YesNo.Yes);
						sa.assertTrue(false, "object feature "+ObjectFeatureName.FieldAndRelationShip+" is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "Fundraising_Contact object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, "Fundraising_Contact object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			log(LogStatus.ERROR, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc090_VerifyTheTabsRelatedListButtonsAndHighlightPanelOnTheAffiliationPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		AffiliationPageBusinessLayer af = new AffiliationPageBusinessLayer(driver);
		NavigationPageBusineesLayer np = new NavigationPageBusineesLayer(driver);
		
		WebElement ele;
		String tab="";
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String[][] labelWithValue = {{excelLabel.Institution.toString(),SmokeCTIns} , 
				{excelLabel.Contact.toString(),SmokeDealContact1FName+" "+SmokeDealContact1LName},
				{excelLabel.Start_Date.toString(),todaysDate} ,
				{excelLabel.End_Date.toString(),tomorrowsDate} ,
					{excelLabel.Role.toString(),"Consultant"}};
		if (af.createAffiliationItem(projectName, labelWithValue, 10)) {
			log(LogStatus.INFO,"Able to create Affiliation",YesNo.No);
			
			if (af.getSecondaryField(projectName, 10)==null) {
				log(LogStatus.INFO,"HighLight is not present at Affiliation",YesNo.No);
			} else {
				sa.assertTrue(false,"HighLight should not be present at Affiliation");
				log(LogStatus.SKIP,"HighLight should not be present at Affiliation",YesNo.Yes);
			}
			// Buttons
			np.clickOnShowMoreDropdownOnly(projectName);
			ShowMoreActionDropDownList[] buttons={ShowMoreActionDropDownList.Edit 
					,ShowMoreActionDropDownList.Delete
					,ShowMoreActionDropDownList.Clone,
					ShowMoreActionDropDownList.Printable_View};
			
			ShowMoreActionDropDownList button=null;
			for (int i = 0; i < buttons.length; i++) {
				button=buttons[i];		
				ele =  np.actionDropdownElement(projectName, button, 10);
				if (ele!=null) {
					log(LogStatus.INFO,"Button "+button+" is present at Affiliation",YesNo.No);	
				} else {
					sa.assertTrue(false,"Button "+button+" sholud be present at Affiliation");
					log(LogStatus.SKIP,"Button "+button+" sholud be present at Affiliation",YesNo.Yes);
				}
				
			}
			
			String[] relatedTabs = {RelatedTab.Details.toString(),RelatedTab.Related.toString()};
			for (int i = 0; i < relatedTabs.length; i++) {
				tab = relatedTabs[i];
				ele=lp.getRelatedTab(projectName, tab, 10);
				if (ele!=null) {
					log(LogStatus.INFO,"Related Tab "+tab+" present at Affiliation",YesNo.No);	
				} else {
					sa.assertTrue(false,"Related Tab "+tab+" sholud be present at Affiliation");
					log(LogStatus.SKIP,"Related Tab "+tab+" sholud be present at Affiliation",YesNo.Yes);
				}
				click(driver, ele, tab, action.BOOLEAN);
			}
			
			
			String[] relatedgrid = {"Files","Notes & Attachments","Upcoming & Overdue"};
			int k=0;
			for (int i = 0; i < relatedgrid.length; i++) {
				tab = relatedgrid[i];
				if (i==2) {
					k=3;
				} else {
					k=2;
				}
				ele=lp.getRelatedListItem(tab, k);
				if (ele!=null) {
					log(LogStatus.INFO,"At Related Tab "+tab+" present at Affiliation",YesNo.No);	
				} else {
					sa.assertTrue(false,"At Related Tab "+tab+" sholud be present at Affiliation");
					log(LogStatus.SKIP,"At Related Tab "+tab+" sholud be present at Affiliation",YesNo.Yes);
				}
			}
			
			
			
		} else {
			sa.assertTrue(false,"Not able to create Affiliation");
			log(LogStatus.SKIP,"Not able to create Affiliation",YesNo.Yes);
		}
		
		
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc091_VerifyPickListValuesForRoleField(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject(environment, mode,object.Affiliation )) {
					log(LogStatus.INFO,"Searched & Clicked on "+object.Affiliation,YesNo.No);	
					if(sp.clickOnObjectFeature(environment, mode,object.Affiliation, ObjectFeatureName.FieldAndRelationShip)) {
						log(LogStatus.INFO, "object feature "+ObjectFeatureName.FieldAndRelationShip+" is clickable", YesNo.No);
						if (sendKeys(driver, sp.getsearchTextboxFieldsAndRelationships(10), PageLabel.Role.toString()+Keys.ENTER, "Affiliation Type", action.BOOLEAN)) {
							log(LogStatus.INFO,PageLabel.Role.toString()+" is clickable",YesNo.No);
							if (sp.clickOnAlreadyCreatedLayout(PageLabel.Role.toString())) {
								log(LogStatus.INFO,PageLabel.Role.toString()+" is clickable",YesNo.No);	
								switchToFrame(driver, 10, sp.getFrame(PageName.AffiliationPage, 10));

								String[] roleValues = {"Trustee","Consultant","Attorney","Accountant","Person Institution","Board Member","Former Employee"};
								for (int i = 0; i < roleValues.length; i++) {
									String role = roleValues[i];
									WebElement ele = sp.getValuesElementAtFieldRelationShip(projectName, role);
									if (ele!=null) {
										log(LogStatus.INFO,"Value picklist"+role+" present at Affiliation",YesNo.No);	
									} else {
										sa.assertTrue(false,"Value picklist"+role+" sholud be present at Affiliation");
										log(LogStatus.SKIP,"Value picklist"+role+" sholud be present at Affiliation",YesNo.Yes);
									}
								}
							}else {
								log(LogStatus.ERROR,PageLabel.Role.toString()+" is not clickable",YesNo.Yes);	
								sa.assertTrue(false,PageLabel.Role.toString()+"status field is not clickable" );
							}
						}else {
							log(LogStatus.ERROR,PageLabel.Role.toString()+" search is not visible",YesNo.Yes);	
							sa.assertTrue(false,PageLabel.Role.toString()+" search is not visible" );
						}
					}else {
						log(LogStatus.ERROR, "object feature "+ObjectFeatureName.FieldAndRelationShip+" is not clickable", YesNo.Yes);
						sa.assertTrue(false, "object feature "+ObjectFeatureName.FieldAndRelationShip+" is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "Affiliation object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, "Affiliation object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			log(LogStatus.ERROR, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc092_VerifyTheTabsRelatedListButtonsAndHighlightPanelOnTheFinancingPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FinancingPageBusinessLayer fin = new FinancingPageBusinessLayer(driver);
		NavigationPageBusineesLayer np = new NavigationPageBusineesLayer(driver);
		
		WebElement ele;
		String tab="";
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String[][] labelWithValue = {{excelLabel.Institution.toString(),SMOKIns11InsName} , 
				{excelLabel.Company.toString(),SMOKIns12InsName},
				{excelLabel.Status.toString(),"Prospect"} ,
				{excelLabel.Type.toString(),"Debt"} ,
				{excelLabel.Type_Of_Debt.toString(),"Senior"}
				,{excelLabel.Amount.toString(),"25000"}};
		if (fin.createFinancingItem(projectName, labelWithValue, 10)) {
			log(LogStatus.INFO,"Able to create Financing",YesNo.No);
			
			if (fin.getSecondaryField(projectName, 10)==null) {
				log(LogStatus.INFO,"HighLight is not present at Financing",YesNo.No);
			} else {
				sa.assertTrue(false,"HighLight should not be present at Financing");
				log(LogStatus.SKIP,"HighLight should not be present at Financing",YesNo.Yes);
			}
			// Buttons
			np.clickOnShowMoreDropdownOnly(projectName);
			ShowMoreActionDropDownList[] buttons={ShowMoreActionDropDownList.Edit 
					,ShowMoreActionDropDownList.Delete
					,ShowMoreActionDropDownList.Clone,
					ShowMoreActionDropDownList.Printable_View};
			
			ShowMoreActionDropDownList button=null;
			for (int i = 0; i < buttons.length; i++) {
				button=buttons[i];		
				ele =  np.actionDropdownElement(projectName, button, 10);
				if (ele!=null) {
					log(LogStatus.INFO,"Button "+button+" is present at Financing",YesNo.No);	
				} else {
					sa.assertTrue(false,"Button "+button+" sholud be present at Financing");
					log(LogStatus.SKIP,"Button "+button+" sholud be present at Financing",YesNo.Yes);
				}
				
			}
			
			String[] relatedTabs = {RelatedTab.Details.toString(),RelatedTab.Related.toString()};
			for (int i = 0; i < relatedTabs.length; i++) {
				tab = relatedTabs[i];
				ele=lp.getRelatedTab(projectName, tab, 10);
				if (ele!=null) {
					log(LogStatus.INFO,"Related Tab "+tab+" present at Financing",YesNo.No);	
				} else {
					sa.assertTrue(false,"Related Tab "+tab+" sholud be present at Financing");
					log(LogStatus.SKIP,"Related Tab "+tab+" sholud be present at Financing",YesNo.Yes);
				}
				click(driver, ele, tab, action.BOOLEAN);
			}
			
			
			String[] relatedgrid = {"Files","Notes & Attachments","Upcoming & Overdue"};
			int k=0;
			for (int i = 0; i < relatedgrid.length; i++) {
				tab = relatedgrid[i];
				if (i==2) {
					k=3;
				} else {
					k=2;
				}
				ele=lp.getRelatedListItem(tab, k);
				if (ele!=null) {
					log(LogStatus.INFO,"At Related Tab "+tab+" present at Financing",YesNo.No);	
				} else {
					sa.assertTrue(false,"At Related Tab "+tab+" sholud be present at Financing");
					log(LogStatus.SKIP,"At Related Tab "+tab+" sholud be present at Financing",YesNo.Yes);
				}
			}
			
			
			
		} else {
			sa.assertTrue(false,"Not able to create Financing");
			log(LogStatus.SKIP,"Not able to create Financing",YesNo.Yes);
		}
		
		
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc093_VerifyPickListValuesForLenderStatusField(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject(environment, mode,object.Financing )) {
					log(LogStatus.INFO,"Searched & Clicked on "+object.Financing,YesNo.No);	
					if(sp.clickOnObjectFeature(environment, mode,object.Financing, ObjectFeatureName.FieldAndRelationShip)) {
						log(LogStatus.INFO, "object feature "+ObjectFeatureName.FieldAndRelationShip+" is clickable", YesNo.No);
						if (sendKeys(driver, sp.getsearchTextboxFieldsAndRelationships(10), PageLabel.Lender_Status.toString()+Keys.ENTER, "Financing Type", action.BOOLEAN)) {
							log(LogStatus.INFO,PageLabel.Lender_Status.toString()+" is clickable",YesNo.No);
							if (sp.clickOnAlreadyCreatedLayout(PageLabel.Lender_Status.toString())) {
								log(LogStatus.INFO,PageLabel.Lender_Status.toString()+" is clickable",YesNo.No);	
								switchToFrame(driver, 10, sp.getFrame(PageName.Financing, 10));

								String[] Lender_StatusValues = {"No Contact","In Conversations","Agreed","Declined"};
								for (int i = 0; i < Lender_StatusValues.length; i++) {
									String Lender_Status = Lender_StatusValues[i];
									WebElement ele = sp.getValuesElementAtFieldRelationShip(projectName, Lender_Status);
									if (ele!=null) {
										log(LogStatus.INFO,"Value picklist"+Lender_Status+" present at Financing",YesNo.No);	
									} else {
										sa.assertTrue(false,"Value picklist"+Lender_Status+" sholud be present at Financing");
										log(LogStatus.SKIP,"Value picklist"+Lender_Status+" sholud be present at Financing",YesNo.Yes);
									}
								}
							}else {
								log(LogStatus.ERROR,PageLabel.Lender_Status.toString()+" is not clickable",YesNo.Yes);	
								sa.assertTrue(false,PageLabel.Lender_Status.toString()+"status field is not clickable" );
							}
						}else {
							log(LogStatus.ERROR,PageLabel.Lender_Status.toString()+" search is not visible",YesNo.Yes);	
							sa.assertTrue(false,PageLabel.Lender_Status.toString()+" search is not visible" );
						}
					}else {
						log(LogStatus.ERROR, "object feature "+ObjectFeatureName.FieldAndRelationShip+" is not clickable", YesNo.Yes);
						sa.assertTrue(false, "object feature "+ObjectFeatureName.FieldAndRelationShip+" is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "Financing object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, "Financing object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			log(LogStatus.ERROR, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc094_VerifyTheTabsRelatedListButtonsAndHighlightPanelOnTheFinancialPerformancePage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FinancialPerformancePageBusinessLayer fin = new FinancialPerformancePageBusinessLayer(driver);
		NavigationPageBusineesLayer np = new NavigationPageBusineesLayer(driver);
		WebElement ele;
		String tab="";
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String[][] labelWithValue = {{excelLabel.Company.toString(),SMOKIns12InsName},
				{excelLabel.Fiscal_Year_end.toString(),tomorrowsDate} ,
				{excelLabel.Date.toString(),tomorrowsDate}, 
				{excelLabel.Frequency.toString(),"Monthly"}};
		if (fin.createFinancialPerformanceItem(projectName, labelWithValue, 10)) {
			log(LogStatus.INFO,"Able to create Financing Performance",YesNo.No);
			
			if (fin.getSecondaryField(projectName, 10)==null) {
				log(LogStatus.INFO,"HighLight is not present at Financing Performance",YesNo.No);
			} else {
				sa.assertTrue(false,"HighLight should not be present at Financing Performance");
				log(LogStatus.SKIP,"HighLight should not be present at Financing Performance",YesNo.Yes);
			}
			// Buttons
			np.clickOnShowMoreDropdownOnly(projectName);
			ShowMoreActionDropDownList[] buttons={ShowMoreActionDropDownList.Edit 
					,ShowMoreActionDropDownList.Delete
					,ShowMoreActionDropDownList.Clone,
					ShowMoreActionDropDownList.Printable_View};
			
			ShowMoreActionDropDownList button=null;
			for (int i = 0; i < buttons.length; i++) {
				button=buttons[i];		
				ele =  np.actionDropdownElement(projectName, button, 10);
				if (ele!=null) {
					log(LogStatus.INFO,"Button "+button+" is present at Financing Performance",YesNo.No);	
				} else {
					sa.assertTrue(false,"Button "+button+" sholud be present at Financing Performance");
					log(LogStatus.SKIP,"Button "+button+" sholud be present at Financing Performance",YesNo.Yes);
				}
				
			}
			
			String[] relatedTabs = {RelatedTab.Details.toString(),RelatedTab.Related.toString()};
			for (int i = 0; i < relatedTabs.length; i++) {
				tab = relatedTabs[i];
				ele=lp.getRelatedTab(projectName, tab, 10);
				if (ele!=null) {
					log(LogStatus.INFO,"Related Tab "+tab+" present at Financing Performance",YesNo.No);	
				} else {
					sa.assertTrue(false,"Related Tab "+tab+" sholud be present at Financing Performance");
					log(LogStatus.SKIP,"Related Tab "+tab+" sholud be present at Financing Performance",YesNo.Yes);
				}
				click(driver, ele, tab, action.BOOLEAN);
			}
			
			
			String[] relatedgrid = {"Files","Notes & Attachments","Upcoming & Overdue"};
			int k=0;
			for (int i = 0; i < relatedgrid.length; i++) {
				tab = relatedgrid[i];
				if (i==2) {
					k=3;
				} else {
					k=2;
				}
				ele=lp.getRelatedListItem(tab, k);
				if (ele!=null) {
					log(LogStatus.INFO,"At Related Tab "+tab+" present at Financing Performance",YesNo.No);	
				} else {
					sa.assertTrue(false,"At Related Tab "+tab+" sholud be present at Financing Performance");
					log(LogStatus.SKIP,"At Related Tab "+tab+" sholud be present at Financing Performance",YesNo.Yes);
				}
			}
			
			
			
		} else {
			sa.assertTrue(false,"Not able to create Financing Performance");
			log(LogStatus.SKIP,"Not able to create Financing Performance",YesNo.Yes);
		}
		
		
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc095_VerifyTheTabsRelatedListButtonsAndHighlightPanelOnTheMarketingInitativePage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		MarketInitiativePageBusinessLayer fin = new MarketInitiativePageBusinessLayer(driver);
		NavigationPageBusineesLayer np = new NavigationPageBusineesLayer(driver);
		WebElement ele;
		String tab="";
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String[][] labelWithValue = {{excelLabel.Name.toString(),SmokeMI1},
				{excelLabel.Status.toString(),"Planning"} ,
				{excelLabel.Start_Date.toString(),todaysDate}, 
				{excelLabel.End_Date.toString(),tomorrowsDate}};
		if (fin.createMIItem(projectName,SmokeMI1, labelWithValue, 10)) {
			log(LogStatus.INFO,"Able to create Marketing Initiative",YesNo.No);
			
			if (fin.getSecondaryField(projectName, 10)==null) {
				log(LogStatus.INFO,"HighLight is not present at Marketing Initiative",YesNo.No);
			} else {
				sa.assertTrue(false,"HighLight should not be present at Marketing Initiative");
				log(LogStatus.SKIP,"HighLight should not be present at Marketing Initiative",YesNo.Yes);
			}
			// Buttons
			np.clickOnShowMoreDropdownOnly(projectName);
			ShowMoreActionDropDownList[] buttons={ShowMoreActionDropDownList.Edit 
					,ShowMoreActionDropDownList.Delete
					,ShowMoreActionDropDownList.Clone,
					ShowMoreActionDropDownList.Printable_View,
					ShowMoreActionDropDownList.Change_Owner 
					,ShowMoreActionDropDownList.Sharing
					,ShowMoreActionDropDownList.Sharing_Hierarchy,
					ShowMoreActionDropDownList.Add_Prospect,
					ShowMoreActionDropDownList.Email_Prospect};
			
			ShowMoreActionDropDownList button=null;
			for (int i = 0; i < buttons.length; i++) {
				button=buttons[i];		
				ele =  np.actionDropdownElement(projectName, button, 10);
				if (ele!=null) {
					log(LogStatus.INFO,"Button "+button+" is present at Marketing Initiative",YesNo.No);	
				} else {
					sa.assertTrue(false,"Button "+button+" sholud be present at Marketing Initiative");
					log(LogStatus.SKIP,"Button "+button+" sholud be present at Marketing Initiative",YesNo.Yes);
				}
				
			}
			
			String[] relatedTabs = {RelatedTab.Details.toString(),RelatedTab.Related.toString()};
			for (int i = 0; i < relatedTabs.length; i++) {
				tab = relatedTabs[i];
				ele=lp.getRelatedTab(projectName, tab, 10);
				if (ele!=null) {
					log(LogStatus.INFO,"Related Tab "+tab+" present at Marketing Initiative",YesNo.No);	
				} else {
					sa.assertTrue(false,"Related Tab "+tab+" sholud be present at Marketing Initiative");
					log(LogStatus.SKIP,"Related Tab "+tab+" sholud be present at Marketing Initiative",YesNo.Yes);
				}
				click(driver, ele, tab, action.BOOLEAN);
			}
			
			
			String[] relatedgrid = {"Marketing Prospects","Notes & Attachments","Files","Upcoming & Overdue"};
			int k=0;
			for (int i = 0; i < relatedgrid.length; i++) {
				tab = relatedgrid[i];
				if (i==3) {
					k=3;
				} else {
					k=2;
				}
				ele=lp.getRelatedListItem(tab, k);
				if (ele!=null) {
					log(LogStatus.INFO,"At Related Tab "+tab+" present at Marketing Initiative",YesNo.No);	
				} else {
					sa.assertTrue(false,"At Related Tab "+tab+" sholud be present at Marketing Initiative");
					log(LogStatus.SKIP,"At Related Tab "+tab+" sholud be present at Marketing Initiative",YesNo.Yes);
				}
			}
			
			
			
		} else {
			sa.assertTrue(false,"Not able to create Marketing Initiative");
			log(LogStatus.SKIP,"Not able to create Marketing Initiative",YesNo.Yes);
		}
		
		
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	
}
	

