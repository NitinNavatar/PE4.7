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
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.GlobalActionItem;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.SubjectElement;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.ContactTransferTabBusinessLayer;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.GlobalActionPageBusinessLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.MarketingEventPageBusinessLayer;
import com.navatar.pageObjects.NavatarSetupPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;

public class PECloudSmoke2 extends BaseLib{
	
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc067_1_VerifyTheLinkInNavigationMenuWhenEnablefromNavatarSetup(String projectName) {

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
			ExcelUtils.writeData(phase1DataSheetFilePath, ToggleContact1EmailID, "Contacts", excelLabel.Variable_Name, "SMOKECTCON1",excelLabel.Contact_EmailId);
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
	public void SmokeTc068_CreateStandardTaskForTomLathamAndVerifyLastTouchpointOnContactDetailPage(String projectName) {
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
	public void SmokeTc069_CreateMultiTaggedTaskforContactJamesRoseAndVerifyLastTouchPoint(String projectName) {
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
	public void SmokeTc070_VerifyTheContactTransferButton_iMPACT(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele ;

		String meetingSubject="";


		TabName tabName =TabName.Object1Tab;
		String ctAccount ;
		String ctAccount1 = SmokeCTIns;
		String ctAccount2 = SmokeCTIns2;
		int i=0;
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
	
}
	

