package com.navatar.scripts;

import static com.navatar.generic.CommonLib.ThreadSleep;
import static com.navatar.generic.CommonLib.click;
import static com.navatar.generic.CommonLib.clickUsingJavaScript;
import static com.navatar.generic.CommonLib.log;
import static com.navatar.generic.CommonLib.previousOrForwardDate;
import static com.navatar.generic.CommonLib.previousOrForwardDateAccordingToTimeZone;
import static com.navatar.generic.CommonLib.refresh;
import static com.navatar.generic.CommonLib.sendKeys;
import static com.navatar.generic.CommonLib.switchToDefaultContent;
import static com.navatar.generic.CommonVariables.*;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.GlobalActionItem;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.GlobalActionPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.TaskPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;

public class Module7New extends BaseLib {
	String meetingNone = "--None--";
	String statusCompleted = "Completed";
	String statusInProgess = "In Progress";
	String statusInStarted = "Not Started";
	
	@Parameters({ "projectName"})
	@Test
	public void M7NTc002_CreateEntityAndContact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		String value="";
		String type="";
		String[][] EntityOrAccounts = {{ M7NIns1, M7NIns1RecordType ,null},{ M7NIns6, M7NIns6RecordType ,null}};
		String xpath= "//section[contains(@class,'open notification')]//button[@title='Close']";
		
          WebElement ele= CommonLib.isDisplayed(driver,CommonLib.FindElement(driver,xpath, "Notification close button", action.BOOLEAN,
        			30), "Visibility", 20, "Notifiction close button");
        
        if(ele!=null) {
             click(driver, ele, "Notification close button", action.BOOLEAN);
             ThreadSleep(3000);
             ele= CommonLib.isDisplayed(driver, CommonLib.FindElement(driver, "", "", action.BOOLEAN, 30), "Visibility", 20, "Notifiction close button");
             if(ele==null) {
                 appLog.info("Successfully closed notification close button");
             }else {
                 
             }
        }
		
		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, tabObj1)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
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
		
		// contact
		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			M7NContact1EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, M7NContact1EmailID, "Contacts", excelLabel.Variable_Name, "M7NCON1",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, M7NContact1FName, M7NContact1LName, M7NIns1, M7NContact1EmailID,M7NContact1RecordType, null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+M7NContact1FName+" "+M7NContact1LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+M7NContact1FName+" "+M7NContact1LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+M7NContact1FName+" "+M7NContact1LName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
		
		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			M7NContact2EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, M7NContact2EmailID, "Contacts", excelLabel.Variable_Name, "M7NCON2",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, M7NContact2FName, M7NContact2LName, M7NIns1, M7NContact2EmailID,M7NContact2RecordType, null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+M7NContact2FName+" "+M7NContact2LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+M7NContact2FName+" "+M7NContact2LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+M7NContact2FName+" "+M7NContact2LName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
		
		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			M7NContact3EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, M7NContact3EmailID, "Contacts", excelLabel.Variable_Name, "M7NCON3",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, M7NContact3FName, M7NContact3LName, M7NIns1, M7NContact3EmailID,M7NContact3RecordType, null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+M7NContact3FName+" "+M7NContact3LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+M7NContact3FName+" "+M7NContact3LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+M7NContact3FName+" "+M7NContact3LName,YesNo.Yes);
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
public void M7NTc003_CreateStandardTaskForTomLathamAndVerifyLastTouchpointOnContactDetailPage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	boolean flag=false;
	WebElement ele = null ;
	String contactName=M7NContact2FName+" "+M7NContact2LName;
	M7NTask1dueDate=yesterdaysDate;
	String task = M7NTask1Subject;
	
	
	String[][] basicsection= {{PageLabel.Subject.toString(),task},{PageLabel.Related_To.toString(),contactName}};
	String[][] advanceSection= {{PageLabel.Priority.toString(),"Normal"},{PageLabel.Due_Date.toString(),M7NTask1dueDate},{PageLabel.Status.toString(),M7NTask1Status}};

     if ( bp.createActivityTimeline(projectName,true,"Task", basicsection, advanceSection,null,null, false, null, null, null, null, null, null)) {
    	 log(LogStatus.INFO," Able to create task  : "+task,YesNo.No);		
			flag=true;
			
     }else {
    	 sa.assertTrue(false,"Not Able to create task  : "+task);
			log(LogStatus.SKIP,"Not Able to create task  : "+task,YesNo.Yes);	
     }

	if (flag) {
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName,TabName.ContactTab, contactName, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
				ThreadSleep(2000);
				WebElement ele2 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele2, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(3000);
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
public void M7NTc004_CreateMultiTaggedTaskforContactJamesRoseAndVerifyLastTouchPoint(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null ;
	String primaryContact=M7NContact3FName+" "+M7NContact3LName;
	String secondaryContact=M7NContact1FName+" "+M7NContact1LName;
	M7NTask2dueDate=previousOrForwardDate(2, "M/d/YYYY");;
	String task = M7NTask2Subject;
	
		String[][] basicsection= {{PageLabel.Subject.toString(),task},{PageLabel.Related_To.toString(),secondaryContact}};
		String[][] advanceSection= {{PageLabel.Due_Date.toString(),M7NTask2dueDate},{PageLabel.Status.toString(),M7NTask2Status}};

		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				ThreadSleep(2000);
				ExcelUtils.writeData(phase1DataSheetFilePath,M7NTask2dueDate, "Task1", excelLabel.Variable_Name, "M7NTask2", excelLabel.Due_Date);
				///////////////////////
				
				if ( bp.createActivityTimeline(projectName,true,"Task", basicsection, advanceSection,null,null, false, null, null, null, null, null, null)) {
			    	 log(LogStatus.INFO," Able to create task  : "+task,YesNo.No);		
						
			     }else {
			    	 sa.assertTrue(false,"Not Able to create task  : "+task);
						log(LogStatus.SKIP,"Not Able to create task  : "+task,YesNo.Yes);	
			     }

				refresh(driver);
				ThreadSleep(5000);
				WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
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
				WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(3000);
				
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
public void M7NTc05_CreatefourNewContactToCheckTheLastTouchPointInCall(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	// contact
	if (lp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
		M7NContact4EmailID=	lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(phase1DataSheetFilePath, M7NContact4EmailID, "Contacts", excelLabel.Variable_Name, "M7NCON4",excelLabel.Contact_EmailId);
		if (cp.createContact(projectName, M7NContact4FName, M7NContact4LName, M7NIns1, M7NContact4EmailID,M7NContact4RecordType, null, null, CreationPage.ContactPage, null, null)) {
			log(LogStatus.INFO,"successfully Created Contact : "+M7NContact4FName+" "+M7NContact4LName,YesNo.No);	
		} else {
			sa.assertTrue(false,"Not Able to Create Contact : "+M7NContact4FName+" "+M7NContact4LName);
			log(LogStatus.SKIP,"Not Able to Create Contact: "+M7NContact4FName+" "+M7NContact4LName,YesNo.Yes);
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
		log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
	}
	
	if (lp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
		M7NContact5EmailID=	lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(phase1DataSheetFilePath, M7NContact5EmailID, "Contacts", excelLabel.Variable_Name, "M7NCON5",excelLabel.Contact_EmailId);
		if (cp.createContact(projectName, M7NContact5FName, M7NContact5LName, M7NIns1, M7NContact5EmailID,M7NContact5RecordType, null, null, CreationPage.ContactPage, null, null)) {
			log(LogStatus.INFO,"successfully Created Contact : "+M7NContact5FName+" "+M7NContact5LName,YesNo.No);	
		} else {
			sa.assertTrue(false,"Not Able to Create Contact : "+M7NContact5FName+" "+M7NContact5LName);
			log(LogStatus.SKIP,"Not Able to Create Contact: "+M7NContact5FName+" "+M7NContact5LName,YesNo.Yes);
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
		log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
	}
	
	if (lp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
		M7NContact6EmailID=	lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(phase1DataSheetFilePath, M7NContact6EmailID, "Contacts", excelLabel.Variable_Name, "M7NCON6",excelLabel.Contact_EmailId);
		if (cp.createContact(projectName, M7NContact6FName, M7NContact6LName, M7NIns1, M7NContact6EmailID,M7NContact6RecordType, null, null, CreationPage.ContactPage, null, null)) {
			log(LogStatus.INFO,"successfully Created Contact : "+M7NContact6FName+" "+M7NContact6LName,YesNo.No);	
		} else {
			sa.assertTrue(false,"Not Able to Create Contact : "+M7NContact6FName+" "+M7NContact6LName);
			log(LogStatus.SKIP,"Not Able to Create Contact: "+M7NContact6FName+" "+M7NContact6LName,YesNo.Yes);
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
		log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
	}
	
	if (lp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
		M7NContact6EmailID=	lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(phase1DataSheetFilePath, M7NContact7EmailID, "Contacts", excelLabel.Variable_Name, "M7NCON7",excelLabel.Contact_EmailId);
		if (cp.createContact(projectName, M7NContact7FName, M7NContact7LName, M7NIns1, M7NContact7EmailID,M7NContact7RecordType, null, null, CreationPage.ContactPage, null, null)) {
			log(LogStatus.INFO,"successfully Created Contact : "+M7NContact7FName+" "+M7NContact7LName,YesNo.No);	
		} else {
			sa.assertTrue(false,"Not Able to Create Contact : "+M7NContact7FName+" "+M7NContact7LName);
			log(LogStatus.SKIP,"Not Able to Create Contact: "+M7NContact7FName+" "+M7NContact7LName,YesNo.Yes);
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
public void M7NTc06_CreatLogACallForContactJhonAleaxVerifyLastTouchpointOnContactDetailPage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	boolean flag=false;
	WebElement ele = null ;
	String contactName=M7NContact4FName+" "+M7NContact4LName;
	M7NTask3dueDate=todaysDate;
	String task = M7NTask3Subject;
	
	String[][] basicsection= {{PageLabel.Subject.toString(),task},{PageLabel.Related_To.toString(),contactName}};
	String[][] advanceSection= {{PageLabel.Date.toString(),M7NTask3dueDate}};
	
	ExcelUtils.writeData(phase1DataSheetFilePath,M7NTask4dueDate, "Task1", excelLabel.Variable_Name, "M7NTask3", excelLabel.Due_Date);

    if ( bp.createActivityTimeline(projectName,true,"Call", basicsection, advanceSection,null,null, false, null, null, null, null, null, null)) {
		log(LogStatus.INFO,"Able to create Task : "+task,YesNo.No);		
		flag=true;
		
	}else {
		sa.assertTrue(false,"Not Able to create Task : "+task);
		log(LogStatus.SKIP,"Not Able to create Task : "+task,YesNo.Yes);	
	}
	
	if (flag) {
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
				ThreadSleep(2000);
				WebElement ele2 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele2, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				 ele=cp.getlastTouchPointValue(projectName, 10);
				if (ele!=null) {
					String expectedValue = M7NTask3dueDate; 
					String actualValue = ele.getText().trim();
					 if (cp.verifyDate(expectedValue,null, actualValue)) { 
					
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
		sa.assertTrue(false,"Not Able to create Task : "+task+" so cannot check last touch point value");
		log(LogStatus.SKIP,"Not Able to create Task : "+task+" so cannot check last touch point value",YesNo.Yes);
	}		
	
	
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
}
@Parameters({ "projectName"})
@Test
public void M7NTc07_CreateMultiTaggedCallforContactSamanthaRaoAndVerifyLastTouchPoint(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null ;
	String primaryContact=M7NContact5FName+" "+M7NContact5LName;
	String secondaryContact=M7NContact6FName+" "+M7NContact6LName;
	M7NTask4dueDate=todaysDate;
	String task = M7NTask4Subject;
	
	String[][] basicsection= {{PageLabel.Subject.toString(),task},{PageLabel.Related_To.toString(),secondaryContact}};
	String[][] advanceSection= {{PageLabel.Date.toString(),M7NTask4dueDate} };

	
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				ThreadSleep(2000);
				
				ExcelUtils.writeData(phase1DataSheetFilePath,M7NTask4dueDate, "Task1", excelLabel.Variable_Name, "M7NTask4", excelLabel.Due_Date);
				///////////////////////
				bp.createActivityTimeline(projectName,false,"Call", basicsection, advanceSection,null,null, false, null, null, null, null, null, null);
				System.err.println("donnnnnnnnnnneeeeeeeeeeeeeeeeeeeeeeeeeeeeee...................................................................");
				CommonLib.ThreadSleep(50000);
				
				
				//out of scope 2
//				ele = lp.getActivityTimeLineItem(projectName, PageName.Object2Page, ActivityTimeLineItem.New_Call, 10);
//				if (clickUsingJavaScript(driver, ele, ActivityTimeLineItem.New_Call.toString(), action.SCROLLANDBOOLEAN)) {
//					log(LogStatus.INFO,"Clicked on "+ActivityTimeLineItem.New_Call.toString(),  YesNo.Yes);
//					ThreadSleep(1000);
//					ele = lp.getCrossButtonForAlreadySelectedItem(projectName, PageName.Object2Page, PageLabel.Name.toString(), true, primaryContact, action.SCROLLANDBOOLEAN, 10);
//					if (ele!=null) {
//						log(LogStatus.INFO, "successfully verified presence of "+primaryContact+" in name field",YesNo.No);
//					} else {
//						sa.assertTrue(false,"not found "+ primaryContact+" For Label "+PageLabel.Name);
//						log(LogStatus.SKIP,"not found "+ primaryContact+" For Label "+PageLabel.Name,YesNo.Yes);
//
//					}
					
					/*
					 * ele=lp.getAlreadySelectedItem(projectName, PageName.Object2Page,
					 * PageLabel.Name.toString(), true, action.SCROLLANDBOOLEAN, 10).get(0);
					 * clickUsingJavaScript(driver, ele, "");
					 */
				//out of scope 2
//					gp.enterValueForTask(projectName, PageName.Object2Page, event1, 20);
//					String relatedValue=M7NIns1;
//					flag = lp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TaskPage, PageLabel.Related_To.toString(), TabName.Object1Tab, relatedValue, action.SCROLLANDBOOLEAN, 10);		
//					if (flag) {
//						log(LogStatus.INFO,"Selected "+relatedValue+" For Label "+PageLabel.Related_Associations,YesNo.No);
//
//					} else {
//						sa.assertTrue(false,"Not Able to Select "+relatedValue+" For Label "+PageLabel.Related_Associations);
//						log(LogStatus.SKIP,"Not Able to Select "+relatedValue+" For Label "+PageLabel.Related_Associations,YesNo.Yes);
//
//					}
//					
//					
//					if (clickUsingJavaScript(driver, gp.getCustomTabSaveBtn(projectName, 2), "Save Button", action.SCROLLANDBOOLEAN)) {
//						log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
//						ThreadSleep(5000);
//						flag=true;
//						
//					}else {
//						sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
//						log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
//					}
//					
//				}
//				else {
//					log(LogStatus.ERROR, "not able to click on "+ActivityTimeLineItem.New_Call.toString(), YesNo.Yes);
//					sa.assertTrue(false,"not able to click on "+ActivityTimeLineItem.New_Call.toString() );
//				}
				//////////////////////
				refresh(driver);
				ThreadSleep(3000);
				WebElement ele2 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele2, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(3000);
				ele=cp.getlastTouchPointValue(projectName, 10);
				if (ele!=null) {
					String expectedValue = M7NTask4dueDate; 
					String actualValue = ele.getText().trim();
					 if (cp.verifyDate(expectedValue,null, actualValue)) { 
	
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
				WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(3000);
				
				ele=cp.getlastTouchPointValue(projectName, 10);
				if (ele!=null) {
					String expectedValue = M7NTask4dueDate; 
					String actualValue = ele.getText().trim();
					 if (cp.verifyDate(expectedValue,null, actualValue)) { 
					
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
public void M7NTc08_CreateMultiTaggedCallforContactJohnAlexaAndVerifyLastTouchPoint(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null ;
	String primaryContact=M7NContact4FName+" "+M7NContact4LName;
	String secondaryContact=M7NContact6FName+" "+M7NContact6LName;
	M7NTask5dueDate=todaysDate;
	String task = M7NTask5Subject;
	
	        String[][] basicsection= {{PageLabel.Subject.toString(),task},{PageLabel.Related_To.toString(),secondaryContact}};
	        String[][] advanceSection= {{PageLabel.Date.toString(),M7NTask5dueDate}};

	
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				ThreadSleep(3000);
				ExcelUtils.writeData(phase1DataSheetFilePath,M7NTask5dueDate, "Task1", excelLabel.Variable_Name, "M7NTask5", excelLabel.Due_Date);
				///////////////////////
				 bp.createActivityTimeline(projectName,false,"Call", basicsection, advanceSection,null,null, false, null, null, null, null, null, null);
					System.err.println("donnnnnnnnnnneeeeeeeeeeeeeeeeeeeeeeeeeeeeee...................................................................");
					CommonLib.ThreadSleep(50000);
				
				//////////////////////
				refresh(driver);
				ThreadSleep(3000);
				WebElement ele2 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele2, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(3000);
				ele=cp.getlastTouchPointValue(projectName, 10);
				if (ele!=null) {
					String expectedValue = M7NTask5dueDate; 
					String actualValue = ele.getText().trim();
					if (cp.verifyDate(expectedValue,null, actualValue)) {
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
				WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(3000);
				ele=cp.getlastTouchPointValue(projectName, 10);
				if (ele!=null) {
					String expectedValue = M7NTask5dueDate; 
					String actualValue = ele.getText().trim();
					if (cp.verifyDate(expectedValue,null, actualValue)) {
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
public void M7NTc09_DeleteMATouchpointCall_1AndVerifyImpactOnLastTouchPointInContactPage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	boolean flag=false;
	WebElement ele = null ;
	String primaryContact=M7NContact4FName+" "+M7NContact4LName;
	String task = M7NTask3Subject;
	
	if (home.globalSearchAndDeleteTaskorCall(task, "Tasks", false)) {

		log(LogStatus.INFO, "-----Verified Task named: " + task + " found and delete in Tasks Object-----", YesNo.No);

	} else {

		log(LogStatus.ERROR, "-----Task named: " + task + " not deleted in Tasks Object-----", YesNo.Yes);
		BaseLib.sa.assertTrue(false, "-----Task named: " + task + " not deleted in Tasks Object-----");

	}

	String actualValue=null;
	String expectedValue = todaysDate;
	String contactName=primaryContact;
	if (flag) {
			contactName=primaryContact;
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
					ThreadSleep(3000);
					ele=cp.getlastTouchPointValue(projectName, 10);
					if (ele!=null) {
						actualValue=ele.getText().trim();
						if (cp.verifyDate(expectedValue,null, actualValue)) {
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
	
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName"})
@Test
public void M7NTc10_DeleteMATouchpointCall_3AndVerifyImpactOnLastTouchPointInContactPage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	boolean flag=false;
	WebElement ele = null ;
	String primaryContact=M7NContact4FName+" "+M7NContact4LName;
	String secondaryContact=M7NContact6FName+" "+M7NContact6LName;
	String task = M7NTask5Subject;
	
	

		if (home.globalSearchAndDeleteTaskorCall(task, "Tasks", false)) {
			flag=true;
	
			log(LogStatus.INFO, "-----Verified Task named: " + task + " found and delete in Tasks Object-----",
				YesNo.No);

			

	} else {

		log(LogStatus.ERROR, "-----Task named: " + task + " not deleted in Tasks Object-----", YesNo.Yes);
		BaseLib.sa.assertTrue(false, "-----Task named: " + task + " not deleted in Tasks Object-----");

	}
	
//	if (cp.clickOnTab(projectName, tabObj2)) {
//		log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
//		if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
//			log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
//			WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Communications.toString(), 10);
//			click(driver, ele1, RelatedTab.Communications.toString(), action.BOOLEAN);
//			ThreadSleep(3000);
//			if (click(driver, lp.getTaskLink(projectName, task), task, action.SCROLLANDBOOLEAN)) {
//				log(LogStatus.INFO,"Click on Task : "+task,YesNo.No);
//				if (lp.clickOnShowMoreActionDownArrow(projectName, PageName.TaskPage, ShowMoreActionDropDownList.Delete, 20)) {
//					log(LogStatus.INFO,"Able to Click on Delete button : "+task,YesNo.No);
//					ThreadSleep(2000);
//					if (click(driver, cp.getDeleteButtonPopUp(projectName, 20), "Delete Button", action.BOOLEAN)) {
//						log(LogStatus.INFO,"Able to Click on Delete button on PopUp : "+task,YesNo.No);
//						ThreadSleep(2000);
//						flag=true;
//					} else {
//						sa.assertTrue(false,"Able to Click on Delete button on PopUp : "+task);
//						log(LogStatus.SKIP,"Able to Click on Delete button on PopUp : "+task,YesNo.Yes);
//					}
//				} else {
//					sa.assertTrue(false,"Not Able to Click on Delete button : "+task);
//					log(LogStatus.SKIP,"Not Able to Click on Delete button : "+task,YesNo.Yes);
//				}
//			} else {
//				sa.assertTrue(false,"Not Able to Click on Task : "+task);
//				log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
//			}
//
//		} else {
//			sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
//			log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
//		}
//	} else {
//		sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
//		log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
//	}	

	String actualValue="";
	String expectedValue ="";
	String contactName=primaryContact;
	if (flag) {
			contactName=primaryContact;
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
					ThreadSleep(3000);
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
	if (cp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+secondaryContact,YesNo.No);
		ThreadSleep(1000);
		if (cp.clickOnAlreadyCreatedItem(projectName, secondaryContact, 30)) {
			log(LogStatus.INFO,"Clicked on  : "+secondaryContact+" For : "+tabObj2,YesNo.No);
			ThreadSleep(2000);
			WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
			click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
			ThreadSleep(3000);
			ele=cp.getlastTouchPointValue(projectName, 10);
			if (ele!=null) {
				String expectedValue1 =todaysDate; 
				String actualValue1 = ele.getText().trim();
				if (cp.verifyDate(actualValue,null,expectedValue )) {
					log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+secondaryContact, YesNo.No);
				}
				else {
					log(LogStatus.ERROR, "Last touch point value is not matched For : "+secondaryContact+" Actual : "+actualValue1+" /t Expected : "+expectedValue1, YesNo.Yes);
					sa.assertTrue(false,"last touch point value is not matched For : "+secondaryContact+" Actual : "+actualValue1+" /t Expected : "+expectedValue1 );
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
public void M7NTc011_RestoreTheDeletedTaskMATouchpointCall_1AndVerifyTheImpactOnLastTouchPointInContactPage(String projectName) {

	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele;
	boolean flag=false;
	String restoreItem = M7NTask3Subject;
	if (lp.restoreValueFromRecycleBin(projectName, restoreItem)) {
		log(LogStatus.INFO,"Able to restore item from Recycle Bin "+restoreItem,YesNo.Yes);
		flag=true;
	} else {
		sa.assertTrue(false,"Not Able to restore item from Recycle Bin "+restoreItem);
		log(LogStatus.SKIP,"Not Able to restore item from Recycle Bin "+restoreItem,YesNo.Yes);

	}
	String actualValue=null;
	String expectedValue = null;
	String contactName=M7NContact4FName+" "+M7NContact4LName;
	if (flag) {
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
					ThreadSleep(3000);
					expectedValue=M7NTask3dueDate;
					ele=cp.getlastTouchPointValue(projectName, 10);
					if (ele!=null) {
						actualValue=ele.getText().trim();
						if (cp.verifyDate(expectedValue,null, actualValue)) {
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
public void M7NTc012_CreateEntityAndContact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	// contact
	if (lp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
		M7NContact8EmailID=	lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(phase1DataSheetFilePath, M7NContact8EmailID, "Contacts", excelLabel.Variable_Name, "M7NCON8",excelLabel.Contact_EmailId);
		if (cp.createContact(projectName, M7NContact8FName, M7NContact8LName, M7NIns1, M7NContact8EmailID,M7NContact8RecordType, null, null, CreationPage.ContactPage, null, null)) {
			log(LogStatus.INFO,"successfully Created Contact : "+M7NContact8FName+" "+M7NContact8LName,YesNo.No);	
		} else {
			sa.assertTrue(false,"Not Able to Create Contact : "+M7NContact8FName+" "+M7NContact8LName);
			log(LogStatus.SKIP,"Not Able to Create Contact: "+M7NContact8FName+" "+M7NContact8LName,YesNo.Yes);
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
		log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
	}
	
	if (lp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
		M7NContact9EmailID=	lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(phase1DataSheetFilePath, M7NContact9EmailID, "Contacts", excelLabel.Variable_Name, "M7NCON9",excelLabel.Contact_EmailId);
		if (cp.createContact(projectName, M7NContact9FName, M7NContact9LName, M7NIns1, M7NContact9EmailID,M7NContact9RecordType, null, null, CreationPage.ContactPage, null, null)) {
			log(LogStatus.INFO,"successfully Created Contact : "+M7NContact9FName+" "+M7NContact9LName,YesNo.No);	
		} else {
			sa.assertTrue(false,"Not Able to Create Contact : "+M7NContact9FName+" "+M7NContact9LName);
			log(LogStatus.SKIP,"Not Able to Create Contact: "+M7NContact9FName+" "+M7NContact9LName,YesNo.Yes);
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
		log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
	}
	
	if (lp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
		M7NContact10EmailID=	lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(phase1DataSheetFilePath, M7NContact10EmailID, "Contacts", excelLabel.Variable_Name, "M7NCON10",excelLabel.Contact_EmailId);
		if (cp.createContact(projectName, M7NContact10FName, M7NContact10LName, M7NIns1, M7NContact10EmailID,M7NContact10RecordType, null, null, CreationPage.ContactPage, null, null)) {
			log(LogStatus.INFO,"successfully Created Contact : "+M7NContact10FName+" "+M7NContact10LName,YesNo.No);	
		} else {
			sa.assertTrue(false,"Not Able to Create Contact : "+M7NContact10FName+" "+M7NContact10LName);
			log(LogStatus.SKIP,"Not Able to Create Contact: "+M7NContact10FName+" "+M7NContact10LName,YesNo.Yes);
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
		log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
	}
	
	if (lp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
		M7NContact11EmailID=	lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(phase1DataSheetFilePath, M7NContact11EmailID, "Contacts", excelLabel.Variable_Name, "M7NCON11",excelLabel.Contact_EmailId);
		if (cp.createContact(projectName, M7NContact11FName, M7NContact11LName, M7NIns1, M7NContact11EmailID,M7NContact11RecordType, null, null, CreationPage.ContactPage, null, null)) {
			log(LogStatus.INFO,"successfully Created Contact : "+M7NContact11FName+" "+M7NContact11LName,YesNo.No);	
		} else {
			sa.assertTrue(false,"Not Able to Create Contact : "+M7NContact11FName+" "+M7NContact11LName);
			log(LogStatus.SKIP,"Not Able to Create Contact: "+M7NContact11FName+" "+M7NContact11LName,YesNo.Yes);
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
public void M7NTc013_CreateEventForEllieVokesAndVerifyLastTouchpointOnContactDetailPage(String projectName) {
	GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	boolean flag=false;
	WebElement ele = null ;
	String primaryContact=M7NContact8FName+" "+M7NContact8LName;
	M7NEvent1StartDate=yesterdaysDate;
	M7NEvent1EndDate=todaysDate;
	String task = M7NEvent1Subject;
	String relatedValue=M7NIns6;
	String[][] event1 = {{PageLabel.Subject.toString(),task},
			{PageLabel.Name.toString(),primaryContact},
			{PageLabel.Start_Date.toString(),M7NEvent1StartDate},
			{PageLabel.Start_Time.toString(),M7NEvent1StartTime},
			{PageLabel.End_Date.toString(),M7NEvent1EndDate},
			{PageLabel.End_Time.toString(),M7NEvent1EndTime}};
	
	if (lp.clickAnyCellonCalender(projectName)) {
		log(LogStatus.INFO,"Able to click on Calendar/Event Link",YesNo.No);
		gp.enterValueForTask(projectName, PageName.Object2Page, event1, 20);
		flag = lp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TaskPage, PageLabel.Related_To.toString(), TabName.Object1Tab, relatedValue, action.SCROLLANDBOOLEAN, 10);		
		if (flag) {
			log(LogStatus.INFO,"Selected "+relatedValue+" For Label "+PageLabel.Related_Associations,YesNo.No);

		} else {
			sa.assertTrue(false,"Not Able to Select "+relatedValue+" For Label "+PageLabel.Related_Associations);
			log(LogStatus.SKIP,"Not Able to Select "+relatedValue+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

		}
		
		if (clickUsingJavaScript(driver, gp.getCustomTabSaveBtn(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
			ExcelUtils.writeData(phase1DataSheetFilePath,M7NEvent1StartDate, "Events", excelLabel.Variable_Name, "M7NEvent1", excelLabel.Start_Date);
			ExcelUtils.writeData(phase1DataSheetFilePath,M7NEvent1EndDate, "Events", excelLabel.Variable_Name, "M7NEvent1", excelLabel.End_Date);
			flag=true;
			
		}else {
			sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
			log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Calendar/Event Link");
		log(LogStatus.SKIP,"Not Able to Click on Calendar/Event Link",YesNo.Yes);	
	}

	if (flag) {
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				ThreadSleep(2000);
				WebElement ele2 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele2, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(3000);
					ele=cp.getlastTouchPointValue(projectName, 10);
					String expectedValue = M7NEvent1EndDate;
					if (ele!=null) {
						String actualValue = ele.getText().trim();
						if (cp.verifyDate(expectedValue,null, actualValue)) {
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
public void M7NTc014_CreateEventfromCalendarforContactJohrYathamAndVerifyLastTouchPoint(String projectName) {
	GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	boolean flag=false;
	WebElement ele = null ;
	String primaryContact=M7NContact9FName+" "+M7NContact9LName;
	String secondaryContact=M7NContact10FName+" "+M7NContact10LName;
	M7NEvent2StartDate=previousOrForwardDate(-5, "M/d/YYYY");
	M7NEvent2EndDate=previousOrForwardDate(-2, "M/d/YYYY");
	String task = M7NEvent2Subject;
	String[][] Event2 = {{PageLabel.Subject.toString(),task},
			{PageLabel.Name.toString(),primaryContact},
			{PageLabel.Name.toString(),secondaryContact},
			{PageLabel.Start_Date.toString(),M7NEvent2StartDate},
			{PageLabel.Start_Time.toString(),M7NEvent2StartTime},
			{PageLabel.End_Date.toString(),M7NEvent2EndDate},
			{PageLabel.End_Time.toString(),M7NEvent2EndTime}};
	
	if (lp.clickAnyCellonCalender(projectName)) {
		log(LogStatus.INFO,"Able to click on Calendar/Event Link",YesNo.No);
		gp.enterValueForTask(projectName, PageName.Object2Page, Event2, 20);
		String relatedValue=M7NIns6;
		flag = lp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TaskPage, PageLabel.Related_To.toString(), TabName.Object1Tab, relatedValue, action.SCROLLANDBOOLEAN, 10);		
		if (flag) {
			log(LogStatus.INFO,"Selected "+relatedValue+" For Label "+PageLabel.Related_Associations,YesNo.No);

		} else {
			sa.assertTrue(false,"Not Able to Select "+relatedValue+" For Label "+PageLabel.Related_Associations);
			log(LogStatus.SKIP,"Not Able to Select "+relatedValue+" For Label "+PageLabel.Related_Associations,YesNo.Yes);

		}
		
		if (clickUsingJavaScript(driver, gp.getCustomTabSaveBtn(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
			ExcelUtils.writeData(phase1DataSheetFilePath,M7NEvent2StartDate, "Events", excelLabel.Variable_Name, "M7NEvent2", excelLabel.Start_Date);
			ExcelUtils.writeData(phase1DataSheetFilePath,M7NEvent2EndDate, "Events", excelLabel.Variable_Name, "M7NEvent2", excelLabel.End_Date);
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
					WebElement ele2 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele2, RelatedTab.Details.toString(), action.BOOLEAN);
					ThreadSleep(3000);
					expectedValue = M7NEvent2EndDate;
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
public void M7NTc015_CreateEventfromCalendarforContactEllieVokesAndVerifyLastTouchPoint(String projectName) {
	GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	boolean flag=false;
	WebElement ele = null ;
	String primaryContact=M7NContact8FName+" "+M7NContact8LName;
	String secondaryContact=M7NContact9FName+" "+M7NContact9LName;
	M7NEvent3StartDate=previousOrForwardDate(-5, "M/d/YYYY");;
	M7NEvent3EndDate=previousOrForwardDate(-3, "M/d/YYYY");;
	String task = M7NEvent3Subject;
	String[][] Event3 = {{PageLabel.Subject.toString(),task},
			{PageLabel.Start_Date.toString(),M7NEvent3StartDate},
			{PageLabel.End_Date.toString(),M7NEvent3EndDate},
			{PageLabel.Name.toString(),primaryContact},
			{PageLabel.Name.toString(),secondaryContact}};
	
	if (lp.clickAnyCellonCalender(projectName)) {
		log(LogStatus.INFO,"Able to click on Calendar/Event Link",YesNo.No);
		gp.enterValueForNewEvent(projectName, GlobalActionItem.New_Event, Event3, 10);
		ExcelUtils.writeData(phase1DataSheetFilePath,M7NEvent3StartDate, "Events", excelLabel.Variable_Name, "M7NEvent3", excelLabel.Start_Date);
		ExcelUtils.writeData(phase1DataSheetFilePath,M7NEvent3EndDate, "Events", excelLabel.Variable_Name, "M7NEvent3", excelLabel.End_Date);
		if (clickUsingJavaScript(driver, gp.getCustomTabSaveBtn(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
			
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
					WebElement ele2 = lp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele2, RelatedTab.Details.toString(), action.BOOLEAN);
					ThreadSleep(3000);
					if (j==0) {
						expectedValue = M7NEvent1EndDate;	
					} else {
						expectedValue = M7NEvent2EndDate;
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
public void M7NTc016_ModifyTheEndDateAndVerifyTheImpactOnTheLastTouchPointDate(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	boolean flag=false;
	WebElement ele = null ;
	String primaryContact=M7NContact8FName+" "+M7NContact8LName;
	String secondaryContact=M7NContact9FName+" "+M7NContact9LName;;
	M7NEvent3EndDate=previousOrForwardDate(2, "M/d/YYYY");;
	String task = M7NEvent3Subject;
	
	if (home.globalSearchAndEditTaskorCall(task, "Events", false)) {

		log(LogStatus.INFO, "-----Verified Event named: " + task + " found and Edit in Event Object-----", YesNo.No);

		
		if (sendKeys(driver, gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Event,
				PageLabel.End_Date.toString(), 20), M7NEvent3EndDate, "End Date", action.BOOLEAN)) {
			log(LogStatus.INFO, "Value Entered to End Date " + M7NEvent3EndDate, YesNo.No);
			if (sendKeys(driver, gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Event,
					PageLabel.End_Time.toString(), 20), M7NEvent3EndTime, "End Time", action.BOOLEAN)) {
				log(LogStatus.INFO, "Value Entered to End Time " + M7NEvent3EndTime, YesNo.No);
				ele = gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Event,
						PageLabel.Subject.toString(), 20);
				click(driver, ele, "", action.BOOLEAN);
				ThreadSleep(2000);
				if (clickUsingJavaScript(driver, lp.getCustomTabSaveBtn(projectName, 20), "save",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "successfully Updated task : " + task, YesNo.No);
					ThreadSleep(5000);
					ExcelUtils.writeData(phase1DataSheetFilePath, M7NEvent3EndDate, "Events", excelLabel.Variable_Name,
							"M7NEvent3", excelLabel.End_Date);
					flag = true;				
				} else {
					log(LogStatus.ERROR, "save button is not clickable so task not Updated : " + task, YesNo.Yes);
					sa.assertTrue(false, "save button is not clickable so task not Updated : " + task);
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Entered Value to End Date " + M7NEvent3EndDate, YesNo.Yes);
				BaseLib.sa.assertTrue(false, "Not Able to Entered Value to End Date " + M7NEvent3EndDate);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Entered Value to End Time " + M7NEvent3EndTime, YesNo.Yes);
			BaseLib.sa.assertTrue(false, "Not Able to Entered Value to End Time " + M7NEvent3EndTime);
		}
		
	} else {

		log(LogStatus.ERROR, "-----Event named: " + task + " not edit in events Object-----", YesNo.Yes);
		BaseLib.sa.assertTrue(false, "-----Event named: " + task + " not edit in events Object-----");

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
					WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
					ThreadSleep(3000);
					expectedValue = M7NEvent3EndDate;
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
public void M7NTc017_DeleteMATouchpointEvent_1AndVerifyImpactOnLastTouchPointOnContactPage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	boolean flag=false;
	WebElement ele = null ;
	String primaryContact=M7NContact8FName+" "+M7NContact8LName;
	String task = M7NEvent1Subject;

	if (home.globalSearchAndDeleteTaskorCall(task, "Events", false)) {

		log(LogStatus.INFO, "-----Verified Task named: " + task + " found and delete in Tasks Object-----", YesNo.No);
		flag=true;
	} else {

		log(LogStatus.ERROR, "-----Task named: " + task + " not deleted in Tasks Object-----", YesNo.Yes);
		BaseLib.sa.assertTrue(false, "-----Task named: " + task + " not deleted in Tasks Object-----");

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
					WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
					ThreadSleep(3000);
					ele=cp.getlastTouchPointValue(projectName, 10);
					expectedValue = M7NEvent3EndDate;
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
public void M7NTc018_AgainModifyTheEndDateAndVerifyTheImpactOnTheLastTouchPointDate(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	boolean flag=false;
	WebElement ele = null ;
	String primaryContact=M7NContact8FName+" "+M7NContact8LName;
	String secondaryContact=M7NContact9FName+" "+M7NContact9LName;;
	M7NEvent3EndDate=previousOrForwardDate(-3, "M/d/YYYY");;
	String task = M7NEvent3Subject;
	if (home.globalSearchAndEditTaskorCall(task, "Events", false)) {

		log(LogStatus.INFO, "-----Verified Event named: " + task + " found and Edit in Event Object-----", YesNo.No);

		if (sendKeys(driver, gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Event,
				PageLabel.End_Date.toString(), 20), M7NEvent3EndDate, "Due Date", action.BOOLEAN)) {
			log(LogStatus.INFO, "Value Entered to Due Date " + M7NEvent3EndDate, YesNo.No);
			if (sendKeys(driver, gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Event,
					PageLabel.End_Time.toString(), 20), M7NEvent3EndTime, "End Time", action.BOOLEAN)) {
				log(LogStatus.INFO, "Value Entered to End Time " + M7NEvent3EndTime, YesNo.No);
				ele = gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Event,
						PageLabel.Subject.toString(), 20);
				click(driver, ele, "", action.BOOLEAN);
				ThreadSleep(2000);
				if (clickUsingJavaScript(driver, lp.getCustomTabSaveBtn(projectName, 20), "save",
						action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "successfully Updated task : " + task, YesNo.No);
					ThreadSleep(5000);
					ExcelUtils.writeData(phase1DataSheetFilePath, M7NEvent3EndDate, "Events", excelLabel.Variable_Name,
							"M7NEvent3", excelLabel.End_Date);
					flag = true;
//					String[][] fieldsWithValues = { { PageLabel.End.toString(), M7NEvent3EndDate } };
//					tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues,
//							action.BOOLEAN, 30);
				} else {
					log(LogStatus.ERROR, "save button is not clickable so task not Updated : " + task, YesNo.Yes);
					sa.assertTrue(false, "save button is not clickable so task not Updated : " + task);
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Entered Value to Due Date " + M7Event3EndDate, YesNo.Yes);
				BaseLib.sa.assertTrue(false, "Not Able to Entered Value to Due Date " + M7Event3EndDate);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Entered Value to End Time " + M7NEvent3EndTime, YesNo.Yes);
			BaseLib.sa.assertTrue(false, "Not Able to Entered Value to End Time " + M7NEvent3EndTime);
		}

	} else {

		log(LogStatus.ERROR, "-----Event named: " + task + " not edit in events Object-----", YesNo.Yes);
		BaseLib.sa.assertTrue(false, "-----Event named: " + task + " not edit in events Object-----");

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
					WebElement ele1 = lp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
					ThreadSleep(3000);
					if (j==0) {
						expectedValue = M7NEvent3EndDate;
					} else {
						expectedValue = M7NEvent2EndDate;
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
public void M7NTc019_DeleteMATouchpointEvent_3AndVerifyImpactOnLastTouchPointOnContactPage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	boolean flag=false;
	WebElement ele = null ;
	String primaryContact=M7NContact8FName+" "+M7NContact8LName;
	String secondaryContact=M7NContact9FName+" "+M7NContact9LName;
	String task = M7NEvent3Subject;

	if (home.globalSearchAndDeleteTaskorCall(task, "Events", false)) {

		log(LogStatus.INFO, "-----Verified Task named: " + task + " found and delete in Tasks Object-----", YesNo.No);
		flag =true;
	} else {

		log(LogStatus.ERROR, "-----Task named: " + task + " not deleted in Tasks Object-----", YesNo.Yes);
		BaseLib.sa.assertTrue(false, "-----Task named: " + task + " not deleted in Tasks Object-----");

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
					WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
					ThreadSleep(3000);
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
							expectedValue=M7NEvent2EndDate;
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
public void M7NTc020_RestoreTheDeletedTaskMATouchpointEvent_1AndVerifyTheImpactOnLastTouchPointInContactPage(String projectName) {

	
	  LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	  ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	  BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	  lp.CRMLogin(crmUser1EmailID, adminPassword, appName); 
	  WebElement ele; boolean
	  flag=false; String restoreItem = M7NEvent1Subject; 
		
		  if(lp.restoreValueFromRecycleBin(projectName, restoreItem)) {
		  log(LogStatus.INFO,"Able to restore item from Recycle Bin "+restoreItem,YesNo
		  .Yes); flag=true; } else {
		  sa.assertTrue(false,"Not Able to restore item from Recycle Bin "+restoreItem)
		  ;
		  log(LogStatus.SKIP,"Not Able to restore item from Recycle Bin "+restoreItem,
		  YesNo.Yes);
		  
		  }
		 
	 
	String actualValue=null;
	String expectedValue = null;
	String contactName=M7NContact8FName+" "+M7NContact8LName;
	if (flag) {
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
					ThreadSleep(3000);
					expectedValue=M7NEvent1EndDate;
					ele=cp.getlastTouchPointValue(projectName, 10);
					if (ele!=null) {
						actualValue=ele.getText().trim();
						if (cp.verifyDate(expectedValue,null, actualValue)) {
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
public void M7NTc021_UpdatedTheEndDateAndVerifyTheImpactOnTheLastTouchPointDate(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	boolean flag=false;
	WebElement ele = null ;
	String primaryContact=M7NContact8FName+" "+M7NContact8LName;
	M7NEvent1EndDate=previousOrForwardDate(0, "M/d/YYYY");;
	String task = M7NEvent1Subject;
	if (home.globalSearchAndEditTaskorCall(task, "Events", false)) {

		log(LogStatus.INFO, "-----Verified Event named: " + task + " found and Edit in Event Object-----", YesNo.No);

		sendKeys(driver, gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Event,
				PageLabel.End_Time.toString(), 20), "11:30 PM", "End Time", action.BOOLEAN);
		if (sendKeys(driver, gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Event,
				PageLabel.End_Date.toString(), 20), M7NEvent1EndDate, "End Date", action.BOOLEAN)) {
			log(LogStatus.INFO, "Value Entered to Due Date " + M7NEvent1EndDate, YesNo.No);
			ele = gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Event,
					PageLabel.Subject.toString(), 20);
			click(driver, ele, "", action.BOOLEAN);
			ThreadSleep(2000);
			if (clickUsingJavaScript(driver, lp.getCustomTabSaveBtn(projectName, 20), "save",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "successfully Updated task : " + task, YesNo.No);
				ThreadSleep(5000);
				ExcelUtils.writeData(phase1DataSheetFilePath, M7NEvent1EndDate, "Events", excelLabel.Variable_Name,
						"M7NEvent1", excelLabel.End_Date);
				flag = true;
				
			} else {
				log(LogStatus.ERROR, "save button is not clickable so task not Updated : " + task, YesNo.Yes);
				sa.assertTrue(false, "save button is not clickable so task not Updated : " + task);
			}

		} else {
			log(LogStatus.ERROR, "Not Able to Entered Value to Due Date " + M7Event1EndDate, YesNo.Yes);
			BaseLib.sa.assertTrue(false, "Not Able to Entered Value to Due Date " + M7NEvent1EndDate);
		}
	} else {

		log(LogStatus.ERROR, "-----Event named: " + task + " not edit in events Object-----", YesNo.Yes);
		BaseLib.sa.assertTrue(false, "-----Event named: " + task + " not edit in events Object-----");

	}

	String contactName=primaryContact;
	if (flag) {
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
					ThreadSleep(3000);
					ele=cp.getlastTouchPointValue(projectName, 10);
					if (ele!=null) {
						String expectedValue = M7NEvent1EndDate; 
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
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
}}
@Parameters({ "projectName"})
@Test
public void M7NTc022_CreateNewEventToVerifyTheFutureDateInLastTouchPointDate(String projectName) {
	GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	boolean flag=false;
	WebElement ele = null ;
	String primaryContact=M7NContact8FName+" "+M7NContact8LName;
	String secondaryContact=M7NContact9FName+" "+M7NContact9LName;
	M7NEvent4StartDate=todaysDate;
	M7NEvent4EndDate=previousOrForwardDate(+7, "M/d/YYYY");
	String task = M7NEvent4Subject;
	String[][] Event4 = {{PageLabel.Subject.toString(),task},
			{PageLabel.Start_Date.toString(),M7NEvent4StartDate},
			{PageLabel.End_Date.toString(),M7NEvent4EndDate},
			{PageLabel.Name.toString(),primaryContact},
			{PageLabel.Name.toString(),secondaryContact}};

	

	if (lp.clickAnyCellonCalender(projectName)) {
		log(LogStatus.INFO,"Able to click on Calendar/Event Link",YesNo.No);
		gp.enterValueForTask(projectName, PageName.Object2Page, Event4, 20);
		if (clickUsingJavaScript(driver, gp.getCustomTabSaveBtn(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
			ExcelUtils.writeData(phase1DataSheetFilePath,M7NEvent4StartDate, "Events", excelLabel.Variable_Name, "M7NEvent4", excelLabel.Start_Date);
			ExcelUtils.writeData(phase1DataSheetFilePath,M7NEvent4EndDate, "Events", excelLabel.Variable_Name, "M7NEvent4", excelLabel.End_Date);
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
					WebElement ele1 = lp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
					ThreadSleep(3000);
					expectedValue = M7NEvent4EndDate;
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
public void M7NTc023_ModifyThestartDateAndVerifyTheImpactOnTheLastTouchPointDate(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	boolean flag=false;
	WebElement ele = null ;
	String primaryContact=M7NContact8FName+" "+M7NContact8LName;
	String secondaryContact=M7NContact9FName+" "+M7NContact9LName;;
	M7NEvent4StartDate=previousOrForwardDate(-9, "M/d/YYYY");;
	String task = M7NEvent4Subject;
	if (home.globalSearchAndEditTaskorCall(task, "Events", false)) {

		log(LogStatus.INFO, "-----Verified Event named: " + task + " found and Edit in Event Object-----", YesNo.No);

		if (sendKeys(driver, gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Event,
				PageLabel.Start_Date.toString(), 20), M7NEvent4StartDate, "Due Date", action.BOOLEAN)) {
			log(LogStatus.INFO, "Value Entered to Due Date " + M7NEvent4StartDate, YesNo.No);
			ele = gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Event,
					PageLabel.Subject.toString(), 20);
			click(driver, ele, "", action.BOOLEAN);
			ThreadSleep(2000);
			if (clickUsingJavaScript(driver, lp.getCustomTabSaveBtn(projectName, 20), "save",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "successfully Updated task : " + task, YesNo.No);
				ThreadSleep(5000);
				ExcelUtils.writeData(phase1DataSheetFilePath, M7NEvent4StartDate, "Events", excelLabel.Variable_Name,
						"M7NEvent4", excelLabel.Start_Date);
				flag = true;
				
			} else {
				log(LogStatus.ERROR, "save button is not clickable so task not Updated : " + task, YesNo.Yes);
				sa.assertTrue(false, "save button is not clickable so task not Updated : " + task);
			}

		} else {
			log(LogStatus.ERROR, "Not Able to Entered Value to Due Date " + M7NEvent4StartDate, YesNo.Yes);
			BaseLib.sa.assertTrue(false, "Not Able to Entered Value to Due Date " + M7NEvent4StartDate);
		}

	} else {

		log(LogStatus.ERROR, "-----Event named: " + task + " not edit in events Object-----", YesNo.Yes);
		BaseLib.sa.assertTrue(false, "-----Event named: " + task + " not edit in events Object-----");

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
					WebElement ele1 = lp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
					ThreadSleep(3000);
					expectedValue = M7NEvent4EndDate;
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
public void M7NTc024_CreateNewEventToVerifyAllDayEvent(String projectName) {
	GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	boolean flag=false;
	WebElement ele = null ;
	String primaryContact=M7NContact7FName+" "+M7NContact7LName;
	M7NEvent5StartDate=todaysDate1;
	String task = M7NEvent5Subject;
	String[][] Event5 = {{PageLabel.Subject.toString(),task},
			{PageLabel.Name.toString(),primaryContact},
			{PageLabel.Related_To.toString(),M7Ins1},
			{PageLabel.All_Day_Event.toString(),"true"}};

	if (lp.clickAnyCellonCalender(projectName)) {
		log(LogStatus.INFO,"Able to click on Calendar/Event Link",YesNo.No);
		gp.enterValueForTask(projectName, PageName.Object2Page, Event5, 20);
		ExcelUtils.writeData(phase1DataSheetFilePath,M7NEvent5StartDate, "Events", excelLabel.Variable_Name, "M7NEvent5", excelLabel.Start_Date);
		if (click(driver, gp.getCustomTabSaveBtn(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);		
			flag=true;
			
		}else {
			sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
			log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Calendar/Event Link");
		log(LogStatus.SKIP,"Not Able to Click on Calendar/Event Link",YesNo.Yes);	
	}
	
	if (flag) {
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				ThreadSleep(2000);
				WebElement ele1 = lp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
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
	} else {
		sa.assertTrue(false,"Call is not created so cannot check Last Touch Point for "+primaryContact);
		log(LogStatus.SKIP,"Call is not created so cannot check Last Touch Point for "+primaryContact,YesNo.Yes);
	}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();

}
@Parameters({ "projectName"})
@Test
public void M7NTc026_1_VerifytheLasttouchpointOnContactWhenCallIsCreated(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null ;
	String primaryContact=M7NContact11FName+" "+M7NContact11LName;
	 M7NTask6dueDate=todaysDate;
	String task = M7NTask6Subject;
	
	String[][] basicsection= {{PageLabel.Subject.toString(),task},{PageLabel.Related_To.toString(),primaryContact}};
	String[][] advanceSection= {{PageLabel.Date.toString(),M7NTask6dueDate}};
	
	ExcelUtils.writeData(phase1DataSheetFilePath,M7NTask6dueDate, "Task1", excelLabel.Variable_Name, "M7NTask6", excelLabel.Due_Date);

    if ( bp.createActivityTimeline(projectName,true,"Call", basicsection, advanceSection,null,null, false, null, null, null, null, null, null)) {
		log(LogStatus.INFO,"Able to create Task : "+task,YesNo.No);		
		
	}else {
		sa.assertTrue(false,"Not Able to create Task : "+task);
		log(LogStatus.SKIP,"Not Able to create Task : "+task,YesNo.Yes);	
	}
	
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				ThreadSleep(2000);
				WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(3000);
				ele=cp.getlastTouchPointValue(projectName, 10);
				String expectedValue = M7NTask6dueDate;
				if (ele!=null) {
					String actualValue = ele.getText().trim();
					if (cp.verifyDate(expectedValue,null, actualValue)) {
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
public void M7NTc026_2_VerifytheLasttouchpointOnContactWhenEventIsCreated(String projectName) {
	GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	boolean flag=false;
	WebElement ele = null ;
	String primaryContact=M7NContact11FName+" "+M7NContact11LName;
	M7NEvent6StartDate=previousOrForwardDate(-10, "M/d/YYYY");;
	M7NEvent6EndDate=previousOrForwardDate(-7, "M/d/YYYY");;
	String task = M7NEvent6Subject;
	String[][] Event6 = {{PageLabel.Subject.toString(),task},
			{PageLabel.Start_Date.toString(),M7NEvent6StartDate},
			{PageLabel.End_Date.toString(),M7NEvent6EndDate},
			{PageLabel.Name.toString(),primaryContact}};

	
	if (lp.clickAnyCellonCalender(projectName)) {
		log(LogStatus.INFO,"Able to click on Calendar/Event Link",YesNo.No);
		gp.enterValueForTask(projectName, PageName.Object2Page, Event6, 20);
		ExcelUtils.writeData(phase1DataSheetFilePath,M7NEvent6StartDate, "Events", excelLabel.Variable_Name, "M7NEvent6", excelLabel.Start_Date);
		ExcelUtils.writeData(phase1DataSheetFilePath,M7NEvent6EndDate, "Events", excelLabel.Variable_Name, "M7NEvent6", excelLabel.End_Date);
		
		if (click(driver, gp.getSaveButtonForEvent(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);		
			flag=true;
			
		}else {
			sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
			log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Calendar/Event Link");
		log(LogStatus.SKIP,"Not Able to Click on Calendar/Event Link",YesNo.Yes);	
	}
	
	
	if (flag) {
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
				ThreadSleep(2000);
				WebElement ele1 = lp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(3000);
					ele=cp.getlastTouchPointValue(projectName, 10);
					String expectedValue = M7NTask6dueDate;
					if (ele!=null) {
						String actualValue = ele.getText().trim();
						if (cp.verifyDate(expectedValue,null, actualValue)) {
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
public void M7NTc027_DeleteTheCreatedCallAndVerifyTheImpactOnLastTouchPointInContactPage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	boolean flag=false;
	WebElement ele = null ;
	String primaryContact=M7NContact11FName+" "+M7NContact11LName;
	String task = M7NTask6Subject;
	if (home.globalSearchAndDeleteTaskorCall(task, "Tasks", false)) {

		log(LogStatus.INFO, "-----Verified Task named: " + task + " found and delete in Tasks Object-----", YesNo.No);
		flag =true;
	} else {

		log(LogStatus.ERROR, "-----Task named: " + task + " not deleted in Tasks Object-----", YesNo.Yes);
		BaseLib.sa.assertTrue(false, "-----Task named: " + task + " not deleted in Tasks Object-----");

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
					WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
					ThreadSleep(3000);
					ele=cp.getlastTouchPointValue(projectName, 10);
					if (ele!=null) {
						actualValue=ele.getText().trim();
						expectedValue=M7NEvent6EndDate;
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
public void M7NTc028_DeleteTheCreatedEventAndVerifyTheImpactOnLastTouchPointInContactPage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	boolean flag=false;
	WebElement ele = null ;
	String primaryContact=M7NContact11FName+" "+M7NContact11LName;
	String task = M7NEvent6Subject;
	if (home.globalSearchAndDeleteTaskorCall(task, "Events", false)) {

		log(LogStatus.INFO, "-----Verified Task named: " + task + " found and delete in Tasks Object-----", YesNo.No);
		flag =true;
	} else {

		log(LogStatus.ERROR, "-----Task named: " + task + " not deleted in Tasks Object-----", YesNo.Yes);
		BaseLib.sa.assertTrue(false, "-----Task named: " + task + " not deleted in Tasks Object-----");

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
					WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
					ThreadSleep(3000);
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
public void M7NTc029_VerifytheErrormessageOnContactWhenEventIsCreated(String projectName) {
	GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String primaryContact=M7NContact11FName+" "+M7NContact11LName;
	M7NEvent7StartDate=previousOrForwardDate(-1, "M/d/YYYY");;
	M7NEvent7EndDate=previousOrForwardDate(+15, "M/d/YYYY");;
	String task = M7NEvent7Subject;
	String[][] Event6 = {{PageLabel.Subject.toString(),task},
			{PageLabel.Start_Date.toString(),M7NEvent7StartDate},
			{PageLabel.End_Date.toString(),M7NEvent7EndDate},
			{PageLabel.Name.toString(),primaryContact}};
	
	if (lp.clickAnyCellonCalender(projectName)) {
		log(LogStatus.INFO,"Able to click on Calendar/Event Link",YesNo.No);
		gp.enterValueForTask(projectName, PageName.Object2Page, Event6, 20);
		ExcelUtils.writeData(phase1DataSheetFilePath,M7NEvent7StartDate, "Events", excelLabel.Variable_Name, "M7NEvent7", excelLabel.Start_Date);
		ExcelUtils.writeData(phase1DataSheetFilePath,M7NEvent7EndDate, "Events", excelLabel.Variable_Name, "M7NEvent7", excelLabel.End_Date);
		
		if (click(driver, gp.getSaveButtonForEvent(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
			if (tp.get14DaysErrorMsg(M7NEvent7Subject,10)!=null) {		
				log(LogStatus.INFO, "successfully verified 14DaysErrorMsg text message", YesNo.No);
			}else {
				sa.assertTrue(false,"could not verify 14DaysErrorMsg text message");
				log(LogStatus.SKIP,"could not verify 14DaysErrorMsg text message",YesNo.Yes);
			}
			
		}else {
				sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
				log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
			}
		
	} else {
		sa.assertTrue(false,"Not Able to Click on Calendar/Event Link");
		log(LogStatus.SKIP,"Not Able to Click on Calendar/Event Link",YesNo.Yes);	
	}
	

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
}

@Parameters({ "projectName"})
@Test
public void M7NTc030_CreateacontactwithouttaskandupdatetheTierafterupgrade(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String contactName=M7NContact12FName+" "+M7NContact12LName;
	
	String value="";
	String type="";
	String[][] EntityOrAccounts = {{ M7NIns2, M7NIns2RecordType ,null}};

	for (String[] accounts : EntityOrAccounts) {
		if (lp.clickOnTab(projectName, tabObj1)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
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
	// contact
	if (lp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
		M7NContact12EmailID=	lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(phase1DataSheetFilePath, M7NContact12EmailID, "Contacts", excelLabel.Variable_Name, "M7NCON12",excelLabel.Contact_EmailId);
		if (cp.createContact(projectName, M7NContact12FName, M7NContact12LName, M7NIns2, M7NContact12EmailID,M7NContact12RecordType, null, null, CreationPage.ContactPage, null, null)) {
			log(LogStatus.INFO,"successfully Created Contact : "+M7NContact12FName+" "+M7NContact12LName,YesNo.No);	
		} else {
			sa.assertTrue(false,"Not Able to Create Contact : "+M7NContact12FName+" "+M7NContact12LName);
			log(LogStatus.SKIP,"Not Able to Create Contact: "+M7NContact12FName+" "+M7NContact12LName,YesNo.Yes);
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
		log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
	}
	if (cp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
		ThreadSleep(1000);
		if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
			log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
			ThreadSleep(2000);
			if(cp.UpdateContactTier(projectName, PageName.ContactPage,"1")) {
			log(LogStatus.INFO,"successfully updated Contact Tier :"+value+" of record type :"+type,YesNo.No);	
			}else {
				sa.assertTrue(false,"Not Able to updated Contact Tier : "+value+" of record type : "+type);
				log(LogStatus.SKIP,"Not Able to updated Contact Tier : "+value+" of record type : "+type,YesNo.Yes);
			}
		}else {
					sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
				}
			}else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
			}
			refresh(driver);		
			ThreadSleep(5000);
			WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
			click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
			ThreadSleep(3000);
			String lastTouchPoint= cp.getlastTouchPointValue(projectName, 30).getText();
			if(lastTouchPoint.contains("")||lastTouchPoint.equalsIgnoreCase("")){
				log(LogStatus.INFO,"Last touch point value is matched As blank in :"+contactName,YesNo.No);	

			}else{
				sa.assertTrue(false,"Last touch point value is not matched As blank in contact :"+contactName);
				log(LogStatus.SKIP,"Last touch point value is not matched As blank in contact :"+contactName,YesNo.Yes);
			}
			refresh(driver);	
			ThreadSleep(10000);
			WebElement ele2 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
			click(driver, ele2, RelatedTab.Details.toString(), action.BOOLEAN);
			ThreadSleep(3000);
			int days=90;
			String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
			String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "M/dd/yyyy", "America/Los_Angles");
			if(cp.verifyDate(expectedDate,actualDate)){
				log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	

			}else{
				sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
				log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
			}
			switchToDefaultContent(driver);
 			lp.CRMlogout();
			sa.assertAll();
		}


@Parameters({ "projectName"})
@Test
public void M7NTc031_CreatePreConditionData(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String value="";
	String type="";
	String[][] EntityOrAccounts = {{ M7NIns3, M7NIns3RecordType ,null} , { M7NIns4, M7NIns4RecordType ,null}};

	for (String[] accounts : EntityOrAccounts) {
		if (lp.clickOnTab(projectName, tabObj1)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
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
	String email=null;
	String[][] contactData= {{M7NContact13FName,M7NContact13LName,M7NIns3},
							{M7NContact14FName,M7NContact14LName,M7NIns3},
							{M7NContact15FName,M7NContact15LName,M7NIns3},
							{M7NContact16FName,M7NContact16LName,M7NIns2},
							{M7NContact17FName,M7NContact17LName,M7NIns4},
							{M7NContact18FName,M7NContact18LName,M7NIns4},
							{M7NContact19FName,M7NContact19LName,M7NIns4},
							{M7NContact20FName,M7NContact20LName,M7NIns4},
							{M7NContact21FName,M7NContact21LName,M7NIns4},
							{M7NContact22FName,M7NContact22LName,M7NIns4},
							{M7NContact23FName,M7NContact23LName,M7NIns4},
							{M7NContact24FName,M7NContact24LName,M7NIns4},
							{M7NContact25FName,M7NContact25LName,M7NIns4},
							{M7NContact26FName,M7NContact26LName,M7NIns4},
							{M7NContact27FName,M7NContact27LName,M7NIns4},
							};
	
	
	// contact
	for(String[] data:contactData)   {
		int i=0;
		
	if (lp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
		email=	lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(phase1DataSheetFilePath, email, "Contacts", excelLabel.Variable_Name, "M7NCON"+(i+13),excelLabel.Contact_EmailId);
		if (cp.createContact(projectName, data[0], data[1], data[2], email,"", null, null, CreationPage.ContactPage, null, null)) {
			log(LogStatus.INFO,"successfully Created Contact : "+data[0]+" "+data[1],YesNo.No);	
		} else {
			sa.assertTrue(false,"Not Able to Create Contact : "+data[0]+" "+data[1]);
			log(LogStatus.SKIP,"Not Able to Create Contact: "+data[0]+" "+data[1],YesNo.Yes);
		}
	
	
	} else {
		sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
		log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
	}
	i++;
}

switchToDefaultContent(driver);
lp.CRMlogout();
sa.assertAll();
}

@Parameters({ "projectName"})
@Test
public void M7NTc032_CreateacontactCallandupdatetheNameafterupgrade(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null ;
	String contactName=M7NContact13FName+" "+M7NContact13LName;
	M7NTask8dueDate=todaysDate;
	String task = M7NTask8Subject;
	
	String[][] basicsection= {{PageLabel.Subject.toString(),task},{PageLabel.Related_To.toString(),contactName}};
	String[][] advanceSection= {{PageLabel.Date.toString(),M7NTask8dueDate}};
	
    ExcelUtils.writeData(phase1DataSheetFilePath,M7NTask8dueDate, "Task1", excelLabel.Variable_Name, "M7NTask8", excelLabel.Due_Date);

    if ( bp.createActivityTimeline(projectName,true,"Call", basicsection, advanceSection,null,null, false, null, null, null, null, null, null)) {
		log(LogStatus.INFO,"Able to create Task : "+task,YesNo.No);		
		
	}else {
		sa.assertTrue(false,"Not Able to create Task : "+task);
		log(LogStatus.SKIP,"Not Able to create Task : "+task,YesNo.Yes);	
	}
	
//	String[][] basicsection1= {{PageLabel.Subject.toString(),"Callupgrade"}};
//	
//            
//			if (home.globalSearchAndEditTaskorCall(task, "Tasks", false)) {
//
//				log(LogStatus.INFO, "-----Verified Event named: " + task + " found and Edit in Event Object-----",
//						YesNo.No);
//				if (bp.updateActivityTimelineRecord(projectName, basicsection1, null, null, null, null,
//						false, null, null, null, null, null, null)) {
//					log(LogStatus.INFO, " Able to update  Task : " + task, YesNo.No);
//
//
//				} else {
//					sa.assertTrue(false, "Not Able to update  Task : " + task);
//					log(LogStatus.SKIP, "Not Able to update  Task : " + task, YesNo.Yes);
//				}
//
//			} else {
//
//				log(LogStatus.ERROR, "-----Event named: " + task + " not edit in events Object-----", YesNo.Yes);
//				BaseLib.sa.assertTrue(false, "-----Event named: " + task + " not edit in events Object-----");
//
//			}
            
	
	refresh(driver);		
	ThreadSleep(5000);
	if (cp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
		ThreadSleep(1000);
		if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
			log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
			ThreadSleep(2000);
			WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
			click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
			ThreadSleep(3000);
			 ele=cp.getlastTouchPointValue(projectName, 10);
			if (ele!=null) {
				String expectedValue = M7NTask8dueDate; 
				String actualValue = ele.getText().trim();
				if (cp.verifyDate(expectedValue,null, actualValue)) {
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
	refresh(driver);	
	ThreadSleep(10000);
	WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
	click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
	ThreadSleep(3000);
	int days=120;
	String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
	String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "M/dd/yyyy", "America/Los_Angles");
	if(cp.verifyDate(expectedDate, actualDate)){
		log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	

	}else{
		sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
		log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
	}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
}
	
	
@Parameters({ "projectName"})
@Test
public void M7NTc033_CreateaContactwithEventandUpdatetheEndDateAfterUpgrade(String projectName) {
	GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null ;
	String primaryContact=M7NContact14FName+" "+M7NContact14LName;
	M7NEvent8StartDate=previousOrForwardDate(-1, "M/d/YYYY");
	M7NEvent8EndDate=previousOrForwardDate(+2, "M/d/YYYY");
	String task = M7NEvent8Subject;
	String[][] Event4 = {{PageLabel.Subject.toString(),task},
			{PageLabel.Start_Date.toString(),M7NEvent8StartDate},
			{PageLabel.End_Date.toString(),M7NEvent8EndDate},
			{PageLabel.Name.toString(),primaryContact}};

	

	if (lp.clickAnyCellonCalender(projectName)) {
		log(LogStatus.INFO,"Able to click on Calendar/Event Link",YesNo.No);
		gp.enterValueForTask(projectName, PageName.Object2Page, Event4, 20);
		ExcelUtils.writeData(phase1DataSheetFilePath,M7NEvent8StartDate, "Events", excelLabel.Variable_Name, "M7NEvent8", excelLabel.Start_Date);
		ExcelUtils.writeData(phase1DataSheetFilePath,M7NEvent8EndDate, "Events", excelLabel.Variable_Name, "M7NEvent8", excelLabel.End_Date);
		if (click(driver, gp.getSaveButtonForEvent(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);		
			
		}else {
			sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
			log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Calendar/Event Link");
		log(LogStatus.SKIP,"Not Able to Click on Calendar/Event Link",YesNo.Yes);	
	}

	M7NEvent8EndDate=previousOrForwardDate(+4, "M/d/YYYY");
	if (home.globalSearchAndEditTaskorCall(task, "Events", false)) {

		log(LogStatus.INFO, "-----Verified Event named: " + task + " found and Edit in Event Object-----", YesNo.No);
		sendKeys(driver, gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Event,
				PageLabel.End_Time.toString(), 20), "11:30 PM", "End Time", action.BOOLEAN);
		if (sendKeys(driver, gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Event,
				PageLabel.End_Date.toString(), 20), M7NEvent8EndDate, "End Date", action.BOOLEAN)) {
			log(LogStatus.INFO, "Value Entered to Due Date " + M7NEvent8EndDate, YesNo.No);
			ele = gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Event,
					PageLabel.Subject.toString(), 20);
			click(driver, ele, "", action.BOOLEAN);
			ThreadSleep(2000);
			if (clickUsingJavaScript(driver, lp.getCustomTabSaveBtn(projectName, 20), "save",
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "successfully Updated task : " + task, YesNo.No);
				ThreadSleep(5000);
				ExcelUtils.writeData(phase1DataSheetFilePath, M7NEvent8EndDate, "Events", excelLabel.Variable_Name,
						"M7NEvent8", excelLabel.End_Date);
				
			} else {
				log(LogStatus.ERROR, "save button is not clickable so task not Updated : " + task, YesNo.Yes);
				sa.assertTrue(false, "save button is not clickable so task not Updated : " + task);
			}

		} else {
			log(LogStatus.ERROR, "Not Able to Entered Value to Due Date " + M7NEvent8EndDate, YesNo.Yes);
			BaseLib.sa.assertTrue(false, "Not Able to Entered Value to Due Date " + M7NEvent8EndDate);
		}
	} else {

		log(LogStatus.ERROR, "-----Event named: " + task + " not edit in events Object-----", YesNo.Yes);
		BaseLib.sa.assertTrue(false, "-----Event named: " + task + " not edit in events Object-----");

	}

	refresh(driver);
	ThreadSleep(10000);
	String actualValue = null;
	String expectedValue = null;
	if (cp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO, "Clicked on Tab : " + tabObj2 + " For : " + primaryContact, YesNo.No);
		ThreadSleep(1000);
		if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
			log(LogStatus.INFO, "Clicked on  : " + primaryContact + " For : " + tabObj2, YesNo.No);
			ThreadSleep(2000);
			WebElement ele1 = lp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
			click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
			ThreadSleep(3000);
			expectedValue = M7NEvent8EndDate;
			ele = cp.getlastTouchPointValue(projectName, 10);
			if (ele != null) {
				actualValue = ele.getText().trim();
				if (cp.verifyDate(expectedValue, actualValue)) {
					log(LogStatus.INFO,
							expectedValue + " successfully verified last touch point date For : " + primaryContact,
							YesNo.No);
				} else {
					log(LogStatus.ERROR, "Last touch point value is not matched For : " + primaryContact + " Actual : "
							+ actualValue + " /t Expected : " + expectedValue, YesNo.Yes);
					sa.assertTrue(false, "last touch point value is not matched For : " + primaryContact + " Actual : "
							+ actualValue + " /t Expected : " + expectedValue);
				}
			} else {
				log(LogStatus.ERROR, expectedValue + " last touch point value is not visible For : " + primaryContact,
						YesNo.Yes);
				sa.assertTrue(false, expectedValue + " last touch point value is not visible For : " + primaryContact);
			}

		} else {
			sa.assertTrue(false, "Item Not Found : " + primaryContact + " For : " + tabObj2);
			log(LogStatus.SKIP, "Item Not Found : " + primaryContact + " For : " + tabObj2, YesNo.Yes);
		}
	} else {
		sa.assertTrue(false, "Not Able to Click on Tab : " + tabObj2 + " For : " + primaryContact);
		log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabObj2 + " For : " + primaryContact, YesNo.Yes);
	}
	refresh(driver);
	ThreadSleep(10000);
	int days = 124;
	WebElement ele1 = lp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
	click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
	ThreadSleep(3000);
	String actualDate = cp.getNextTouchPointDateValue(projectName, 30).getText();
	String expectedDate = previousOrForwardDateAccordingToTimeZone(days, "MM/dd/yyyy", "America/Los_Angles");
	if (cp.verifyDate(expectedDate, actualDate)) {
		log(LogStatus.INFO,
				"Next touch point value is matched As after " + days + " days from created date  in :" + primaryContact,
				YesNo.No);

	} else {
		sa.assertTrue(false, "Next touch point value is not matched As after " + days
				+ " days from created date  in contact :" + primaryContact);
		log(LogStatus.SKIP, "Next touch point value is not matched As after " + days
				+ " days from created date  in contact :" + primaryContact, YesNo.Yes);
	}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
}
@Parameters({ "projectName"})
@Test
public void M7NTc034_CreateContactWithoutCallAndCreateTheTaskAfterUpgradeTier(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null ;
	String contactName=M7NContact15FName+" "+M7NContact15LName;
	M7NTask9dueDate=todaysDate;
	String task = M7NTask9Subject;

	String[][] basicsection= {{PageLabel.Subject.toString(),task},{PageLabel.Related_To.toString(),contactName}};
	String[][] advanceSection= {{PageLabel.Date.toString(),M7NTask9dueDate}};
	
	ExcelUtils.writeData(phase1DataSheetFilePath,M7NTask9dueDate, "Task1", excelLabel.Variable_Name, "M7NTask9", excelLabel.Due_Date);

    if ( bp.createActivityTimeline(projectName,true,"Call", basicsection, advanceSection,null,null, false, null, null, null, null, null, null)) {
		log(LogStatus.INFO,"Able to create Task : "+task,YesNo.No);		
		
	}else {
		sa.assertTrue(false,"Not Able to create Task : "+task);
		log(LogStatus.SKIP,"Not Able to create Task : "+task,YesNo.Yes);	
	}

	
	refresh(driver);		
	ThreadSleep(5000);
	if (cp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
		ThreadSleep(1000);
		if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
			log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
			ThreadSleep(2000);
			WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
			click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
			ThreadSleep(3000);
			 ele=cp.getlastTouchPointValue(projectName, 10);
			if (ele!=null) {
				String expectedValue = M7NTask9dueDate; 
				String actualValue = ele.getText().trim();
				if (cp.verifyDate(expectedValue,null, actualValue)) {
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
	refresh(driver);	
	ThreadSleep(10000);
	WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
	click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
	ThreadSleep(3000);
	int days=120;
	String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
	String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "M/dd/yyyy", "America/Los_Angles");
	if(cp.verifyDate(expectedDate, actualDate)){
		log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	

	}else{
		sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
		log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
	}
	String tier ="1";
	if (cp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
		ThreadSleep(1000);
		if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
			log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
			ThreadSleep(2000);
			if(cp.UpdateContactTier(projectName, PageName.ContactPage,tier)) {
				if(cp.UpdateContactTier(projectName, PageName.ContactPage, tier)){
					
					log(LogStatus.INFO,"Contact tier updated as: "+tier,YesNo.No);	
					refresh(driver);		
					ThreadSleep(5000);
					WebElement ele2 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele2, RelatedTab.Details.toString(), action.BOOLEAN);
					ThreadSleep(3000);
					String lastTouchPoint= cp.getlastTouchPointValue(projectName, 30).getText();
					if(lastTouchPoint.contains("")||lastTouchPoint.equalsIgnoreCase("")){
						log(LogStatus.INFO,"Last touch point value is matched As blank in :"+contactName,YesNo.No);	

					}else{
						sa.assertTrue(false,"Last touch point value is not matched As blank in contact :"+contactName);
						log(LogStatus.SKIP,"Last touch point value is not matched As blank in contact :"+contactName,YesNo.Yes);
					}
					
					int days1=90;
					
					String actualDate1= cp.getNextTouchPointDateValue(projectName, 30).getText();
					String expectedDate1 =previousOrForwardDateAccordingToTimeZone(days1, "M/dd/yyyy", "America/Los_Angles");
					if(cp.verifyDate(expectedDate1, actualDate1)){
						log(LogStatus.INFO,"Next touch point value is matched As after "+days1+" days from created date  in :"+contactName,YesNo.No);	

					}else{
						sa.assertTrue(false,"Next touch point value is not matched As after "+days1+" days from created date  in contact :"+contactName);
						log(LogStatus.SKIP,"Next touch point value is not matched As after "+days1+" days from created date  in contact :"+contactName,YesNo.Yes);
					}
				}else{
					sa.assertTrue(false,"Not Able to Update Contact  tier: "+contactName);
					log(LogStatus.SKIP,"Not Able to Update Contact  tier: "+contactName,YesNo.Yes);
				}
				
			}else{
				sa.assertTrue(false,"Not Able to click on Create Contact : "+contactName);
				log(LogStatus.SKIP,"Not Able to click on created Contact: "+contactName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
			switchToDefaultContent(driver);
			lp.CRMlogout();
			sa.assertAll();
		}}
@Parameters({ "projectName"})
@Test
public void M7NTc035_UpdateRossGellerContactwithEventandUpdatetheEndDateAfterUpgrade(String projectName) {
	GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null ;
	String primaryContact=M7NContact16FName+" "+M7NContact16LName;
	M7NEvent9StartDate=todaysDate;
	M7NEvent9EndDate=todaysDate;
	String task = M7NEvent9Subject;
	String[][] Event4 = {{PageLabel.Subject.toString(),task},
			{PageLabel.Start_Date.toString(),M7NEvent9StartDate},
			{PageLabel.End_Date.toString(),M7NEvent9EndDate},
			{PageLabel.Name.toString(),primaryContact}};

	if (lp.clickAnyCellonCalender(projectName)) {
		log(LogStatus.INFO,"Able to click on Calendar/Event Link",YesNo.No);
		gp.enterValueForTask(projectName, PageName.Object2Page, Event4, 20);
		ExcelUtils.writeData(phase1DataSheetFilePath,M7NEvent9StartDate, "Events", excelLabel.Variable_Name, "M7NEvent9", excelLabel.Start_Date);
		ExcelUtils.writeData(phase1DataSheetFilePath,M7NEvent9EndDate, "Events", excelLabel.Variable_Name, "M7NEvent9", excelLabel.End_Date);
		if (click(driver, gp.getSaveButtonForEvent(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);		
			
		}else {
			sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
			log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Calendar/Event Link");
		log(LogStatus.SKIP,"Not Able to Click on Calendar/Event Link",YesNo.Yes);	
	}

	
	
	M7NEvent9EndDate=previousOrForwardDate(+1, "M/d/YYYY");
	if (home.globalSearchAndEditTaskorCall(task, "Events", false)) {

		log(LogStatus.INFO, "-----Verified Event named: " + task + " found and Edit in Event Object-----", YesNo.No);

					sendKeys(driver, gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Event, PageLabel.End_Time.toString(),20), "11:30 PM", "End Time", action.BOOLEAN);
					if (sendKeys(driver, gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Event, PageLabel.End_Date.toString(),20), M7NEvent9EndDate, "End Date", action.BOOLEAN)) {
						log(LogStatus.INFO, "Value Entered to Due Date "+M7NEvent9EndDate, YesNo.No);	
						ele=gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Event, PageLabel.Subject.toString(),20);
						click(driver, ele, "", action.BOOLEAN);
						ThreadSleep(2000);
						if (clickUsingJavaScript(driver, lp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"successfully Updated task : "+task,  YesNo.No);
							ThreadSleep(5000);
							ExcelUtils.writeData(phase1DataSheetFilePath,M7NEvent9EndDate, "Events", excelLabel.Variable_Name, "M7NEvent9", excelLabel.End_Date);
							
						}else {
							log(LogStatus.ERROR, "save button is not clickable so task not Updated : "+task, YesNo.Yes);
							sa.assertTrue(false,"save button is not clickable so task not Updated : "+task );
						
						}
					}else {
						log(LogStatus.ERROR, "Not Able to Entered Value to Due Date "+M7NEvent9EndDate, YesNo.Yes);	
						BaseLib.sa.assertTrue(false, "Not Able to Entered Value to Due Date "+M7NEvent9EndDate);	
					}

	} else {

		log(LogStatus.ERROR, "-----Event named: " + task + " not edit in events Object-----", YesNo.Yes);
		BaseLib.sa.assertTrue(false, "-----Event named: " + task + " not edit in events Object-----");

	}
		refresh(driver);	
		ThreadSleep(10000);
		String actualValue=null;
		String expectedValue = null;
		String[] contactName={primaryContact};
	
				if (cp.clickOnTab(projectName, tabObj2)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
					ThreadSleep(1000);
					if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
						log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
						ThreadSleep(2000);
						WebElement ele1 = lp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
						click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
						ThreadSleep(3000);
						expectedValue = M7NEvent9EndDate;
						ele=cp.getlastTouchPointValue(projectName, 10);
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
		refresh(driver);	
		ThreadSleep(10000);
		WebElement ele1 = lp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
		click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
		ThreadSleep(3000);
		int days=121;
		String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
		String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "MM/dd/yyyy", "America/Los_Angles");
		if(cp.verifyDate(expectedDate, actualDate)){
			log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	

		}else{
			sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
			log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
		}
	
     switchToDefaultContent(driver);
       lp.CRMlogout();
      sa.assertAll();
}				
@Parameters({ "projectName"})
@Test
public void M7NTc036_NoraMingContactWithoutTierandVerifyLastTouchPointandNextTouchPointDate(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null ;
	String contactName=M7NContact17FName+" "+M7NContact17LName;
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
				ThreadSleep(2000);
				WebElement ele2 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele2, RelatedTab.Details.toString(), action.BOOLEAN);
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
		int days=120;
		String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
		String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "M/dd/yyyy", "America/Los_Angles");
		if(cp.verifyDate(expectedDate, actualDate)){
			log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	

		}else{
			sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
			log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
		}
	
	
	switchToDefaultContent(driver);
    lp.CRMlogout();
   sa.assertAll();
}
@Parameters({ "projectName"})
@Test
public void M7NTc037_NoraMingContactWithoutTier123andVerifyLastTouchPointandNextTouchPointDate(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String contactName=M7NContact17FName+" "+M7NContact17LName;
	String tier ="1";
	if(lp.clickOnTab(contactName, mode, TabName.ContactTab)){
		log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);
		if(lp.clickOnAlreadyCreatedItem(projectName, TabName.ContactTab, contactName, 30)){
			log(LogStatus.INFO,"click on Created Contact : "+contactName,YesNo.No);	
			ThreadSleep(3000);
			
			if(cp.UpdateContactTier(projectName, PageName.ContactPage, tier)){
				
				log(LogStatus.INFO,"Contact tier updated as: "+tier,YesNo.No);	
				refresh(driver);		
				ThreadSleep(5000);
				WebElement ele2 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele2, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				String lastTouchPoint= cp.getlastTouchPointValue(projectName, 30).getText();
				if(lastTouchPoint.contains("")||lastTouchPoint.equalsIgnoreCase("")){
					log(LogStatus.INFO,"Last touch point value is matched As blank in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Last touch point value is not matched As blank in contact :"+contactName);
					log(LogStatus.SKIP,"Last touch point value is not matched As blank in contact :"+contactName,YesNo.Yes);
				}
				
				int days=90;
				String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
				String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "M/dd/yyyy", "America/Los_Angles");
				if(cp.verifyDate(expectedDate, actualDate)){
					log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
					log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
				}
			}else{
				sa.assertTrue(false,"Not Able to Update Contact  tier: "+contactName);
				log(LogStatus.SKIP,"Not Able to Update Contact  tier: "+contactName,YesNo.Yes);
			}
			
		}else{
			sa.assertTrue(false,"Not Able to click on Create Contact : "+contactName);
			log(LogStatus.SKIP,"Not Able to click on created Contact: "+contactName,YesNo.Yes);
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
		log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
	}
	
	 tier ="2";
	if(lp.clickOnTab(contactName, mode, TabName.ContactTab)){
		log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);
		if(lp.clickOnAlreadyCreatedItem(projectName, TabName.ContactTab, contactName, 30)){
			log(LogStatus.INFO,"click on Created Contact : "+contactName,YesNo.No);	
			ThreadSleep(3000);
			
			if(cp.UpdateContactTier(projectName, PageName.ContactPage, tier)){
				
				log(LogStatus.INFO,"Contact tier updated as: "+tier,YesNo.No);	
				refresh(driver);		
				ThreadSleep(5000);
				WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(3000);
				String lastTouchPoint= cp.getlastTouchPointValue(projectName, 30).getText();
				if(lastTouchPoint.contains("")||lastTouchPoint.equalsIgnoreCase("")){
					log(LogStatus.INFO,"Last touch point value is matched As blank in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Last touch point value is not matched As blank in contact :"+contactName);
					log(LogStatus.SKIP,"Last touch point value is not matched As blank in contact :"+contactName,YesNo.Yes);
				}
				
				int days=120;
				String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
				String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "M/dd/yyyy", "America/Los_Angles");
				if(cp.verifyDate(expectedDate,actualDate)){
					log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
					log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
				}
			}else{
				sa.assertTrue(false,"Not Able to Update Contact  tier: "+contactName);
				log(LogStatus.SKIP,"Not Able to Update Contact  tier: "+contactName,YesNo.Yes);
			}
			
		}else{
			sa.assertTrue(false,"Not Able to click on Create Contact : "+contactName);
			log(LogStatus.SKIP,"Not Able to click on created Contact: "+contactName,YesNo.Yes);
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
		log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
	}
	
	 tier ="3";
	if(lp.clickOnTab(contactName, mode, TabName.ContactTab)){
		log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);
		if(lp.clickOnAlreadyCreatedItem(projectName, TabName.ContactTab, contactName, 30)){
			log(LogStatus.INFO,"click on Created Contact : "+contactName,YesNo.No);	
			ThreadSleep(3000);
			
			if(cp.UpdateContactTier(projectName, PageName.ContactPage, tier)){
				
				log(LogStatus.INFO,"Contact tier updated as: "+tier,YesNo.No);	
				refresh(driver);		
				ThreadSleep(5000);
				WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(3000);
				String lastTouchPoint= cp.getlastTouchPointValue(projectName, 30).getText();
				if(lastTouchPoint.contains("")||lastTouchPoint.equalsIgnoreCase("")){
					log(LogStatus.INFO,"Last touch point value is matched As blank in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Last touch point value is not matched As blank in contact :"+contactName);
					log(LogStatus.SKIP,"Last touch point value is not matched As blank in contact :"+contactName,YesNo.Yes);
				}
				
				int days=180;
				String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
				String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "M/dd/yyyy", "America/Los_Angles");
				if(cp.verifyDate(expectedDate, actualDate)){
					log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
					log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
				}
			}else{
				sa.assertTrue(false,"Not Able to Update Contact  tier: "+contactName);
				log(LogStatus.SKIP,"Not Able to Update Contact  tier: "+contactName,YesNo.Yes);
			}
			
		}else{
			sa.assertTrue(false,"Not Able to click on Create Contact : "+contactName);
			log(LogStatus.SKIP,"Not Able to click on created Contact: "+contactName,YesNo.Yes);
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
public void M7NTc038_WisselyStingContactWithoutTierandVerifyLastTouchPointandNextTouchPointDate(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null ;
	String contactName=M7NContact18FName+" "+M7NContact18LName;
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
				ThreadSleep(2000);
				WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(3000);
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
		int days=120;
		String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
		String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "M/dd/yyyy", "America/Los_Angles");
		if(cp.verifyDate(expectedDate, actualDate)){
			log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	

		}else{
			sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
			log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
		}
	
	
	switchToDefaultContent(driver);
    lp.CRMlogout();
   sa.assertAll();
}
@Parameters({ "projectName"})
@Test
public void M7NTc039_WisselyStingContactWithCallandVerifyLastTouchPointandNextTouchPointDate(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null ;
	String contactName=M7NContact18FName+" "+M7NContact18LName;
	M7NTask10dueDate=todaysDate;
	String task = M7NTask10Subject;
	
	String[][] basicsection= {{PageLabel.Subject.toString(),task},{PageLabel.Related_To.toString(),contactName}};
	String[][] advanceSection= {{PageLabel.Date.toString(),M7NTask10dueDate}};
	
	ExcelUtils.writeData(phase1DataSheetFilePath,M7NTask10dueDate, "Task1", excelLabel.Variable_Name, "M7NTask10", excelLabel.Due_Date);

    if ( bp.createActivityTimeline(projectName,true,"Call", basicsection, advanceSection,null,null, false, null, null, null, null, null, null)) {
		log(LogStatus.INFO,"Able to create Task : "+task,YesNo.No);		
		
	}else {
		sa.assertTrue(false,"Not Able to create Task : "+task);
		log(LogStatus.SKIP,"Not Able to create Task : "+task,YesNo.Yes);	
	}

	

		refresh(driver);		
		ThreadSleep(5000);
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
				ThreadSleep(2000);
				WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(3000);
				 ele=cp.getlastTouchPointValue(projectName, 10);
				if (ele!=null) {
					String expectedValue = M7NTask10dueDate; 
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
		refresh(driver);	
		ThreadSleep(10000);
		WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
		click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
		ThreadSleep(3000);
		int days=120;
		String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
		String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "M/dd/yyyy", "America/Los_Angles");
		if(cp.verifyDate(expectedDate, actualDate)){
			log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	
		
		}else{
			sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
			log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
}
@Parameters({ "projectName"})
@Test
public void M7NTc040_UpdateWoxKittContactVerifyLastTouchPointandNextTouchPointDate(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String contactName=M7NContact19FName+" "+M7NContact19LName;
	String value="";
	String type="";
	String[][] EntityOrAccounts = {{ M7NIns5, M7NIns5RecordType ,null}};

	
	for (String[] accounts : EntityOrAccounts) {
		if (lp.clickOnTab(projectName, tabObj1)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
			value = accounts[0];
			type = accounts[1];
			if (ip.createEntityOrAccount(projectName, mode, value, type, null, null, 20)) {
				log(LogStatus.INFO, "successfully Created Account/Entity : " + value + " of record type : " + type,
						YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Create Account/Entity : " + value + " of record type : " + type);
				log(LogStatus.SKIP, "Not Able to Create Account/Entity : " + value + " of record type : " + type,
						YesNo.Yes);
			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + tabObj1);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabObj1, YesNo.Yes);
		}
	}

		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
				ThreadSleep(2000);
				WebElement ele2 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele2, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				String lastTouchPoint= cp.getlastTouchPointValue(projectName, 30).getText();
				if(lastTouchPoint.contains("")||lastTouchPoint.equalsIgnoreCase("")){
					log(LogStatus.INFO,"Last touch point value is matched As blank in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Last touch point value is not matched As blank in contact :"+contactName);
					log(LogStatus.SKIP,"Last touch point value is not matched As blank in contact :"+contactName,YesNo.Yes);
				}
				refresh(driver);	
				ThreadSleep(10000);
				WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(2000);
				int days=120;
				String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
				String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "M/dd/yyyy", "America/Los_Angles");
				if(cp.verifyDate(expectedDate, actualDate)){
					log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
					log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
				}
		refresh(driver);		
		ThreadSleep(5000);
		String legalName =M7NIns5;
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
				ThreadSleep(2000);
					if(cp.UpdateLegalName(projectName, PageName.ContactPage, legalName)) {
					log(LogStatus.INFO,"successfully updated Contact Account :"+value+" of record type :"+type,YesNo.No);	
					ExcelUtils.writeData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M7NCON19", excelLabel.Record_Type);
					}else {
						sa.assertTrue(false,"Not Able to updated Contact Account : "+value+" of record type : "+type);
						log(LogStatus.SKIP,"Not Able to updated Contact Account : "+value+" of record type : "+type,YesNo.Yes);
					}
				}else {
							sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
							log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
						}
					}else {
							sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
							log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
					}	
		refresh(driver);		
		ThreadSleep(5000);
		WebElement ele3 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
		click(driver, ele3, RelatedTab.Details.toString(), action.BOOLEAN);
		ThreadSleep(2000);
		String lastTouchPoint1= cp.getlastTouchPointValue(projectName, 30).getText();
		if(lastTouchPoint1.contains("")||lastTouchPoint1.equalsIgnoreCase("")){
			log(LogStatus.INFO,"Last touch point value is matched As blank in :"+contactName,YesNo.No);	

		}else{
			sa.assertTrue(false,"Last touch point value is not matched As blank in contact :"+contactName);
			log(LogStatus.SKIP,"Last touch point value is not matched As blank in contact :"+contactName,YesNo.Yes);
		}
		refresh(driver);	
		ThreadSleep(10000);
		WebElement ele4 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
		click(driver, ele4, RelatedTab.Details.toString(), action.BOOLEAN);
		ThreadSleep(2000);
		String actualDate1= cp.getNextTouchPointDateValue(projectName, 30).getText();
		String expectedDate1 =previousOrForwardDateAccordingToTimeZone(days, "M/dd/yyyy", "America/Los_Angles");
		if(cp.verifyDate(expectedDate1, actualDate1)){
			log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	

		}else{
			sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
			log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}}}
@Parameters({ "projectName"})
@Test
public void M7NTc041_EllinaBingContactWithTier1andVerifyLastTouchPointandNextTouchPointDate(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String contactName=M7NContact20FName+" "+M7NContact20LName;
	String tier ="1";
	if(lp.clickOnTab(contactName, mode, TabName.ContactTab)){
		log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);
		if(lp.clickOnAlreadyCreatedItem(projectName, TabName.ContactTab, contactName, 30)){
			log(LogStatus.INFO,"click on Created Contact : "+contactName,YesNo.No);	
			ThreadSleep(3000);
			
			if(cp.UpdateContactTier(projectName, PageName.ContactPage, tier)){
				
				log(LogStatus.INFO,"Contact tier updated as: "+tier,YesNo.No);	
				refresh(driver);		
				ThreadSleep(5000);
				WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(3000);
				String lastTouchPoint= cp.getlastTouchPointValue(projectName, 30).getText();
				if(lastTouchPoint.contains("")||lastTouchPoint.equalsIgnoreCase("")){
					log(LogStatus.INFO,"Last touch point value is matched As blank in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Last touch point value is not matched As blank in contact :"+contactName);
					log(LogStatus.SKIP,"Last touch point value is not matched As blank in contact :"+contactName,YesNo.Yes);
				}
				
				int days=90;
				String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
				String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "M/dd/yyyy", "America/Los_Angles");
				if(cp.verifyDate(expectedDate, actualDate)){
					log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
					log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
				}
			}else{
				sa.assertTrue(false,"Not Able to Update Contact  tier: "+contactName);
				log(LogStatus.SKIP,"Not Able to Update Contact  tier: "+contactName,YesNo.Yes);
			}
			
		}else{
			sa.assertTrue(false,"Not Able to click on Create Contact : "+contactName);
			log(LogStatus.SKIP,"Not Able to click on created Contact: "+contactName,YesNo.Yes);
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
public void M7NTc041_1_EllinaBingContactWithCallandVerifyLastTouchPointandNextTouchPointDate(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null ;
	String contactName=M7NContact20FName+" "+M7NContact20LName;
	M7NTask11dueDate=todaysDate;
	String task = M7NTask11Subject;
	
	String[][] basicsection= {{PageLabel.Subject.toString(),task},{PageLabel.Related_To.toString(),contactName}};
	String[][] advanceSection= {{PageLabel.Date.toString(),M7NTask11dueDate}};
	
	ExcelUtils.writeData(phase1DataSheetFilePath,M7NTask11dueDate, "Task1", excelLabel.Variable_Name, "M7NTask11", excelLabel.Due_Date);

    if ( bp.createActivityTimeline(projectName,true,"Call", basicsection, advanceSection,null,null, false, null, null, null, null, null, null)) {
		log(LogStatus.INFO,"Able to create Task : "+task,YesNo.No);		
		
	}else {
		sa.assertTrue(false,"Not Able to create Task : "+task);
		log(LogStatus.SKIP,"Not Able to create Task : "+task,YesNo.Yes);	
	}
		refresh(driver);		
		ThreadSleep(5000);
		if (cp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
				ThreadSleep(2000);
				WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(3000);
				 ele=cp.getlastTouchPointValue(projectName, 10);
				if (ele!=null) {
					String expectedValue = M7NTask11dueDate; 
					String actualValue = ele.getText().trim();
					if (cp.verifyDate(expectedValue,null, actualValue)) {
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
		refresh(driver);	
		ThreadSleep(10000);
		WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
		click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
		ThreadSleep(3000);
		int days=90;
		String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
		String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "M/dd/yyyy", "America/Los_Angles");
		if(cp.verifyDate(expectedDate, actualDate)){
			log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	
		
		}else{
			sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
			log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
}
@Parameters({ "projectName"})
@Test
public void M7NTc042_EllinaBingContactWithUpdateCallandVerifyLastTouchPointandNextTouchPointDate(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null ;
	String contactName=M7NContact20FName+" "+M7NContact20LName;
	M7NTask11dueDate=previousOrForwardDate(+1, "M/d/YYYY");
	String task = M7NTask11Subject;
	
	String[][] basicsection = { { "Subject", M7NTask11Subject } };
	String[][] advanceSection = { { "Priority", "Normal" }, { "Due Date Only", M7NTask11dueDate } };

	if (home.globalSearchAndEditTaskorCall(task, "Tasks", false)) {

		log(LogStatus.INFO, "-----Verified Event named: " + task + " found and Edit in Event Object-----", YesNo.No);
		bp.updateActivityTimelineRecord(projectName, basicsection, advanceSection, null, null, null, false, null, null,
				null, null, null, null);
		ExcelUtils.writeData(phase1DataSheetFilePath, M7NTask11dueDate, "Task1", excelLabel.Variable_Name, "M7NTask11",
				excelLabel.Due_Date);
		ThreadSleep(1000);
		refresh(driver);
		ThreadSleep(5000);

	} else {

		log(LogStatus.ERROR, "-----Event named: " + task + " not edit in events Object-----", YesNo.Yes);
		BaseLib.sa.assertTrue(false, "-----Event named: " + task + " not edit in events Object-----");

	}
	
	if (cp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
		ThreadSleep(1000);
		if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
			log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
			ThreadSleep(2000);
			WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
			click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
			ThreadSleep(3000);
			 ele=cp.getlastTouchPointValue(projectName, 10);
			if (ele!=null) {
				String expectedValue = M7NTask11dueDate; 
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
	refresh(driver);	
	ThreadSleep(5000);
	WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
	click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
	ThreadSleep(3000);
	int days=91;
	String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
	String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "M/dd/yyyy", "America/Los_Angles");
	if(cp.verifyDate(expectedDate, actualDate)){
		log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	
	
	}else{
		sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
		log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
	}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
}
@Parameters({ "projectName"})
@Test
public void M7NTc043_EllinaBingContactWithUpdateEventandVerifyLastTouchPointandNextTouchPointDate(String projectName) {
	GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null ;
	String contactName=M7NContact20FName+" "+M7NContact20LName;
	M7NEvent10StartDate=previousOrForwardDate(-1, "M/d/YYYY");
	M7NEvent10EndDate=previousOrForwardDate(+3, "M/d/YYYY");
	String task = M7NEvent10Subject;
	String[][] Event4 = {
			{PageLabel.Start_Date.toString(),M7NEvent10StartDate},
			{PageLabel.End_Date.toString(),M7NEvent10EndDate},
			{PageLabel.Subject.toString(),task}};
	
	
	if (lp.clickAnyCellonCalender(projectName)) {
		log(LogStatus.INFO,"Able to click on Calendar/Event Link",YesNo.No);
		gp.enterValueForTask(projectName, PageName.Object2Page, Event4, 20);
		if (clickUsingJavaScript(driver, gp.getCustomTabSaveBtn(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
			ExcelUtils.writeData(phase1DataSheetFilePath,M7NEvent10StartDate, "Events", excelLabel.Variable_Name, "M7NEvent10", excelLabel.Start_Date);
			ExcelUtils.writeData(phase1DataSheetFilePath,M7NEvent10EndDate, "Events", excelLabel.Variable_Name, "M7NEvent10", excelLabel.End_Date);
		}else {
			sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
			log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Calendar/Event Link");
		log(LogStatus.SKIP,"Not Able to Click on Calendar/Event Link",YesNo.Yes);	
	}
	
	
	
	refresh(driver);		
	ThreadSleep(5000);
	if (cp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
		ThreadSleep(1000);
		if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
			log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
			ThreadSleep(2000);
			WebElement ele1 = lp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
			click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
			ThreadSleep(3000);
			 ele=cp.getlastTouchPointValue(projectName, 10);
			if (ele!=null) {
				String expectedValue = M7NEvent10EndDate; 
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
	refresh(driver);	
	ThreadSleep(5000);
	int days=93;
	WebElement ele1 = lp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
	click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
	ThreadSleep(3000);
	String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
	String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "MM/dd/yyyy", "America/Los_Angles");
	if(cp.verifyDate(expectedDate, actualDate)){
		log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	
	
	}else{
		sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
		log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
	}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName"})
@Test
public void M7NTc044_EllinaBingContactWithUpdateEventWithUpdateEndDateandVerifyLastTouchPointandNextTouchPointDate(String projectName) {
	GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null ;
	String contactName=M7NContact20FName+" "+M7NContact20LName;
	M7NEvent11StartDate=previousOrForwardDate(-1, "M/d/YYYY");
	M7NEvent11EndDate=previousOrForwardDate(+2, "M/d/YYYY");
	String task = M7NEvent11Subject;
	String[][] Event4 = {
			{PageLabel.Start_Date.toString(),M7NEvent11StartDate},
			{PageLabel.End_Date.toString(),M7NEvent11EndDate},
			{PageLabel.Subject.toString(),task}};
	
	
	if (lp.clickAnyCellonCalender(projectName)) {
		log(LogStatus.INFO,"Able to click on Calendar/Event Link",YesNo.No);
		gp.enterValueForTask(projectName, PageName.Object2Page, Event4, 20);
		if (clickUsingJavaScript(driver, gp.getCustomTabSaveBtn(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
			ExcelUtils.writeData(phase1DataSheetFilePath,M7NEvent11StartDate, "Events", excelLabel.Variable_Name, "M7NEvent11", excelLabel.Start_Date);
			ExcelUtils.writeData(phase1DataSheetFilePath,M7NEvent11EndDate, "Events", excelLabel.Variable_Name, "M7NEvent11", excelLabel.End_Date);
		}else {
			sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
			log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Calendar/Event Link");
		log(LogStatus.SKIP,"Not Able to Click on Calendar/Event Link",YesNo.Yes);	
	}
	refresh(driver);		
	ThreadSleep(5000);
	if (cp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
		ThreadSleep(1000);
		if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
			log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
			ThreadSleep(2000);
			WebElement ele1 = lp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
			click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
			ThreadSleep(3000);
			 ele=cp.getlastTouchPointValue(projectName, 10);
			if (ele!=null) {
				String expectedValue = M7NEvent10EndDate; 
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
	refresh(driver);	
	ThreadSleep(5000);
	int days=93;
	WebElement ele1 = lp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
	click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
	ThreadSleep(3000);
	String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
	String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "MM/dd/yyyy", "America/Los_Angles");
	if(cp.verifyDate(expectedDate, actualDate)){
		log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	
	
	}else{
		sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
		log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
	}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName"})
@Test
public void M7NTc045_EllinaBingContactDeleteEvent1VerifyLastTouchPointandNextTouchPointDate(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null ;
	String contactName=M7NContact20FName+" "+M7NContact20LName;
	M7NEvent11EndDate=previousOrForwardDate(+2, "M/d/YYYY");
	String task = M7NEvent10Subject;
	
	

	if (home.globalSearchAndDeleteTaskorCall(task, "Events", false)) {

		log(LogStatus.INFO, "-----Verified Task named: " + task + " found and delete in Tasks Object-----", YesNo.No);
	} else {

		log(LogStatus.ERROR, "-----Task named: " + task + " not deleted in Tasks Object-----", YesNo.Yes);
		BaseLib.sa.assertTrue(false, "-----Task named: " + task + " not deleted in Tasks Object-----");

	}

	
	refresh(driver);		
	ThreadSleep(5000);
	if (cp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
		ThreadSleep(1000);
		if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
			log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
			ThreadSleep(2000);
			WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
			click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
			ThreadSleep(3000);
			 ele=cp.getlastTouchPointValue(projectName, 10);
			if (ele!=null) {
				String expectedValue = M7NEvent11EndDate; 
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
	refresh(driver);	
	ThreadSleep(5000);
	WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
	click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
	ThreadSleep(3000);
	int days=92;
	String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
	String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "MM/dd/yyyy", "America/Los_Angles");
	if(cp.verifyDate(expectedDate, actualDate)){
		log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	
	
	}else{
		sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
		log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
	}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
}
@Parameters({ "projectName"})
@Test
public void M7NTc046_EllinaBingContactDeleteEvent2VerifyLastTouchPointandNextTouchPointDate(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null ;
	String contactName=M7NContact20FName+" "+M7NContact20LName;
	String task = M7NEvent11Subject;

	if (home.globalSearchAndDeleteTaskorCall(task, "Events", false)) {

		log(LogStatus.INFO, "-----Verified Task named: " + task + " found and delete in Tasks Object-----", YesNo.No);
	} else {

		log(LogStatus.ERROR, "-----Task named: " + task + " not deleted in Tasks Object-----", YesNo.Yes);
		BaseLib.sa.assertTrue(false, "-----Task named: " + task + " not deleted in Tasks Object-----");

	}

	refresh(driver);		
	ThreadSleep(5000);
	if (cp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
		ThreadSleep(1000);
		if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
			log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
			ThreadSleep(2000);
			WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
			click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
			ThreadSleep(3000);
			 ele=cp.getlastTouchPointValue(projectName, 10);
			if (ele!=null) {
				String expectedValue = M7NTask11dueDate; 
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
	refresh(driver);	
	ThreadSleep(10000);
	WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
	click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
	ThreadSleep(3000);
	int days=91;
	String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
	String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "MM/dd/yyyy", "America/Los_Angles");
	if(cp.verifyDate(expectedDate, actualDate)){
		log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	
	
	}else{
		sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
		log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
	}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
}
@Parameters({ "projectName"})
@Test
public void M7NTc047_EllinaBingContactDeleteCallVerifyLastTouchPointandNextTouchPointDate(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null ;
	String contactName=M7NContact20FName+" "+M7NContact20LName;
	String task = M7NTask11Subject;

	if (home.globalSearchAndDeleteTaskorCall(task, "Tasks", false)) {

		log(LogStatus.INFO, "-----Verified Task named: " + task + " found and delete in Tasks Object-----", YesNo.No);
	} else {

		log(LogStatus.ERROR, "-----Task named: " + task + " not deleted in Tasks Object-----", YesNo.Yes);
		BaseLib.sa.assertTrue(false, "-----Task named: " + task + " not deleted in Tasks Object-----");

	}

	refresh(driver);		
	ThreadSleep(5000);
	if (cp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
		ThreadSleep(1000);
		if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
			log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
			ThreadSleep(2000);
			WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
			click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
			ThreadSleep(3000);
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
					
	refresh(driver);	
	ThreadSleep(10000);
	WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
	click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
	ThreadSleep(3000);
	int days=90;
	String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
	String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "MM/dd/yyyy", "America/Los_Angles");
	if(cp.verifyDate(expectedDate, actualDate)){
		log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	
	
	}else{
		sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
		log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
	}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
}
@Parameters({ "projectName"})
@Test
public void M7NTc048_RestoreTheDeletedTaskEvent1AndVerifyVerifyLastTouchPointandNextTouchPointDate(String projectName) {

	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele;
	String restoreItem = M7NEvent10Subject;
	if (lp.restoreValueFromRecycleBin(projectName, restoreItem)) {
		log(LogStatus.INFO,"Able to restore item from Recycle Bin "+restoreItem,YesNo.Yes);
	} else {
		sa.assertTrue(false,"Not Able to restore item from Recycle Bin "+restoreItem);
		log(LogStatus.SKIP,"Not Able to restore item from Recycle Bin "+restoreItem,YesNo.Yes);

	}
	refresh(driver);		
	ThreadSleep(5000);
	String contactName=M7NContact20FName+" "+M7NContact20LName;
	if (cp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
		ThreadSleep(1000);
		if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
			log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
			ThreadSleep(2000);
			WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
			click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
			ThreadSleep(3000);
			 ele=cp.getlastTouchPointValue(projectName, 10);
			if (ele!=null) {
				String expectedValue = M7NEvent10EndDate; 
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
	refresh(driver);	
	ThreadSleep(10000);
	WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
	click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
	ThreadSleep(3000);
	int days=93;
	String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
	String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "MM/dd/yyyy", "America/Los_Angles");
	if(cp.verifyDate(expectedDate, actualDate)){
		log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	
	
	}else{
		sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
		log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
	}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
}
@Parameters({ "projectName"})
@Test
public void M7NTc049_Update4contactswithtier123andnoneAndVerifyVerifyLastTouchPointandNextTouchPointDate(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String contactName=M7NContact21FName+" "+M7NContact21LName;
	String tier ="1";
	if(lp.clickOnTab(contactName, mode, TabName.ContactTab)){
		log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);
		if(lp.clickOnAlreadyCreatedItem(projectName, TabName.ContactTab, contactName, 30)){
			log(LogStatus.INFO,"click on Created Contact : "+contactName,YesNo.No);	
			ThreadSleep(3000);
			
			if(cp.UpdateContactTier(projectName, PageName.ContactPage, tier)){
				
				log(LogStatus.INFO,"Contact tier updated as: "+tier,YesNo.No);	
				refresh(driver);		
				ThreadSleep(5000);
				WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(3000);
				String lastTouchPoint= cp.getlastTouchPointValue(projectName, 30).getText();
				if(lastTouchPoint.contains("")||lastTouchPoint.equalsIgnoreCase("")){
					log(LogStatus.INFO,"Last touch point value is matched As blank in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Last touch point value is not matched As blank in contact :"+contactName);
					log(LogStatus.SKIP,"Last touch point value is not matched As blank in contact :"+contactName,YesNo.Yes);
				}
				
				int days=90;
				String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
				String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "M/dd/yyyy", "America/Los_Angles");
				if(cp.verifyDate(expectedDate, actualDate)){
					log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
					log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
				}
			}else{
				sa.assertTrue(false,"Not Able to Update Contact  tier: "+contactName);
				log(LogStatus.SKIP,"Not Able to Update Contact  tier: "+contactName,YesNo.Yes);
			}
			
		}else{
			sa.assertTrue(false,"Not Able to click on Create Contact : "+contactName);
			log(LogStatus.SKIP,"Not Able to click on created Contact: "+contactName,YesNo.Yes);
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
		log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
	}
	String contactName1=M7NContact22FName+" "+M7NContact22LName;
	String tier1 ="2";
	if(lp.clickOnTab(contactName, mode, TabName.ContactTab)){
		log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);
		if(lp.clickOnAlreadyCreatedItem(projectName, TabName.ContactTab, contactName1, 30)){
			log(LogStatus.INFO,"click on Created Contact : "+contactName1,YesNo.No);	
			ThreadSleep(3000);
			
			if(cp.UpdateContactTier(projectName, PageName.ContactPage, tier1)){
				
				log(LogStatus.INFO,"Contact tier updated as: "+tier1,YesNo.No);	
				refresh(driver);		
				ThreadSleep(5000);
				WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(3000);
				String lastTouchPoint= cp.getlastTouchPointValue(projectName, 30).getText();
				if(lastTouchPoint.contains("")||lastTouchPoint.equalsIgnoreCase("")){
					log(LogStatus.INFO,"Last touch point value is matched As blank in :"+contactName1,YesNo.No);	

				}else{
					sa.assertTrue(false,"Last touch point value is not matched As blank in contact :"+contactName1);
					log(LogStatus.SKIP,"Last touch point value is not matched As blank in contact :"+contactName1,YesNo.Yes);
				}
				
				int days=120;
				String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
				String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "M/dd/yyyy", "America/Los_Angles");
				if(cp.verifyDate(expectedDate, actualDate)){
					log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName1,YesNo.No);	

				}else{
					sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName1);
					log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName1,YesNo.Yes);
				}
			}else{
				sa.assertTrue(false,"Not Able to Update Contact  tier: "+contactName1);
				log(LogStatus.SKIP,"Not Able to Update Contact  tier: "+contactName1,YesNo.Yes);
			}
			
		}else{
			sa.assertTrue(false,"Not Able to click on Create Contact : "+contactName1);
			log(LogStatus.SKIP,"Not Able to click on created Contact: "+contactName1,YesNo.Yes);
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
		log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
	}

	String contactName2=M7NContact23FName+" "+M7NContact23LName;
	String tier2 ="3";
	if(lp.clickOnTab(contactName, mode, TabName.ContactTab)){
		log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);
		if(lp.clickOnAlreadyCreatedItem(projectName, TabName.ContactTab, contactName2, 30)){
			log(LogStatus.INFO,"click on Created Contact : "+contactName2,YesNo.No);	
			ThreadSleep(3000);
			
			if(cp.UpdateContactTier(projectName, PageName.ContactPage, tier2)){
				
				log(LogStatus.INFO,"Contact tier updated as: "+tier2,YesNo.No);	
				refresh(driver);		
				ThreadSleep(5000);
				WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(3000);
				String lastTouchPoint= cp.getlastTouchPointValue(projectName, 30).getText();
				if(lastTouchPoint.contains("")||lastTouchPoint.equalsIgnoreCase("")){
					log(LogStatus.INFO,"Last touch point value is matched As blank in :"+contactName2,YesNo.No);	

				}else{
					sa.assertTrue(false,"Last touch point value is not matched As blank in contact :"+contactName2);
					log(LogStatus.SKIP,"Last touch point value is not matched As blank in contact :"+contactName2,YesNo.Yes);
				}
				
				int days=180;
				String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
				String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "M/dd/yyyy", "America/Los_Angles");
				if(cp.verifyDate(expectedDate, actualDate)){
					log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName2,YesNo.No);	

				}else{
					sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName2);
					log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName2,YesNo.Yes);
				}
			}else{
				sa.assertTrue(false,"Not Able to Update Contact  tier: "+contactName2);
				log(LogStatus.SKIP,"Not Able to Update Contact  tier: "+contactName2,YesNo.Yes);
			}
			
		}else{
			sa.assertTrue(false,"Not Able to click on Create Contact : "+contactName2);
			log(LogStatus.SKIP,"Not Able to click on created Contact: "+contactName2,YesNo.Yes);
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
		log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
	}
	String contactName3=M7NContact24FName+" "+M7NContact24LName;
	if(lp.clickOnTab(contactName, mode, TabName.ContactTab)){
		log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);
		if(lp.clickOnAlreadyCreatedItem(projectName, TabName.ContactTab, contactName3, 30)){
			log(LogStatus.INFO,"click on Created Contact : "+contactName3,YesNo.No);	
			ThreadSleep(3000);
			WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
			click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
			ThreadSleep(3000);
				String lastTouchPoint= cp.getlastTouchPointValue(projectName, 30).getText();
				if(lastTouchPoint.contains("")||lastTouchPoint.equalsIgnoreCase("")){
					log(LogStatus.INFO,"Last touch point value is matched As blank in :"+contactName3,YesNo.No);	

				}else{
					sa.assertTrue(false,"Last touch point value is not matched As blank in contact :"+contactName3);
					log(LogStatus.SKIP,"Last touch point value is not matched As blank in contact :"+contactName3,YesNo.Yes);
				}
				
				int days=120;
				String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
				String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "M/dd/yyyy", "America/Los_Angles");
				if(cp.verifyDate(expectedDate, actualDate)){
					log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName3,YesNo.No);	

				}else{
					sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName3);
					log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName3,YesNo.Yes);
				}
			}else{
			sa.assertTrue(false,"Not Able to click on Create Contact : "+contactName3);
			log(LogStatus.SKIP,"Not Able to click on created Contact: "+contactName3,YesNo.Yes);
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
public void M7NTc049_1_Update4contactswithEventAndVerifyVerifyLastTouchPointandNextTouchPointDate(String projectName) {
	GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null ;
	String contactName=M7NContact21FName+" "+M7NContact21LName;
	String contactName1=M7NContact22FName+" "+M7NContact22LName;
	String contactName2=M7NContact23FName+" "+M7NContact23LName;
	String contactName3=M7NContact24FName+" "+M7NContact24LName;
	M7NEvent12StartDate=previousOrForwardDate(-1, "M/d/YYYY");
	M7NEvent12EndDate=previousOrForwardDate(+3, "M/d/YYYY");
	String task = M7NEvent12Subject;
	String[][] Event4 = {{PageLabel.Subject.toString(),task},
			{PageLabel.Start_Date.toString(),M7NEvent12StartDate},
			{PageLabel.End_Date.toString(),M7NEvent12EndDate},
			{PageLabel.Name.toString(),contactName},
			{PageLabel.Name.toString(),contactName1},
			{PageLabel.Name.toString(),contactName2},
			{PageLabel.Name.toString(),contactName3}};
	
	if (lp.clickAnyCellonCalender(projectName)) {
		log(LogStatus.INFO,"Able to click on Calendar/Event Link",YesNo.No);
		gp.enterValueForTask(projectName, PageName.Object2Page, Event4, 20);
		if (clickUsingJavaScript(driver, gp.getCustomTabSaveBtn(projectName, 10), "Save Button",
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Click on Save Button For Task : " + task, YesNo.No);
			ExcelUtils.writeData(phase1DataSheetFilePath, M7NEvent12StartDate, "Events", excelLabel.Variable_Name,
					"M7NEvent12", excelLabel.Start_Date);
			ExcelUtils.writeData(phase1DataSheetFilePath, M7NEvent12EndDate, "Events", excelLabel.Variable_Name,
					"M7NEvent12", excelLabel.End_Date);
		} else {
			sa.assertTrue(false, "Not Able to Click on Save Button For Task : " + task);
			log(LogStatus.SKIP, "Not Able to Click on Save Button For Task : " + task, YesNo.Yes);
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Calendar/Event Link");
		log(LogStatus.SKIP,"Not Able to Click on Calendar/Event Link",YesNo.Yes);	
	}
	
	refresh(driver);
	ThreadSleep(5000);
	String contactName4 = M7NContact21FName + " " + M7NContact21LName;
	if (cp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO, "Clicked on Tab : " + tabObj2 + " For : " + contactName4, YesNo.No);
		ThreadSleep(1000);
		if (cp.clickOnAlreadyCreatedItem(projectName, contactName4, 30)) {
			log(LogStatus.INFO, "Clicked on  : " + contactName4 + " For : " + tabObj2, YesNo.No);
			ThreadSleep(2000);
			WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
			click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
			ThreadSleep(3000);
			ele = cp.getlastTouchPointValue(projectName, 10);
			if (ele != null) {
				String expectedValue = M7NEvent12EndDate;
				String actualValue = ele.getText().trim();
				if (cp.verifyDate(expectedValue, actualValue)) {
					log(LogStatus.INFO,
							expectedValue + " successfully verified last touch point date For : " + contactName4,
							YesNo.No);
				} else {
					log(LogStatus.ERROR, "Last touch point value is not matched For : " + contactName4 + " Actual : "
							+ actualValue + " /t Expected : " + expectedValue, YesNo.Yes);
					sa.assertTrue(false, "last touch point value is not matched For : " + contactName4 + " Actual : "
							+ actualValue + " /t Expected : " + expectedValue);
				}
			} else {
				log(LogStatus.ERROR, "last touch point value is not visible For : " + contactName4, YesNo.Yes);
				sa.assertTrue(false, "last touch point value is not visible For : " + contactName4);
			}
		} else {
			sa.assertTrue(false, "Item Not Found : " + contactName4 + " For : " + tabObj2);
			log(LogStatus.SKIP, "Item Not Found : " + contactName4 + " For : " + tabObj2, YesNo.Yes);
		}
	} else {
		sa.assertTrue(false, "Not Able to Click on Tab : " + tabObj2 + " For : " + contactName4);
		log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabObj2 + " For : " + contactName4, YesNo.Yes);
	}

	refresh(driver);
	ThreadSleep(10000);
	WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
	click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
	ThreadSleep(3000);
	int days = 93;
	String actualDate = cp.getNextTouchPointDateValue(projectName, 30).getText();
	String expectedDate = previousOrForwardDateAccordingToTimeZone(days, "MM/dd/yyyy", "America/Los_Angles");
	if (cp.verifyDate(expectedDate, actualDate)) {
		log(LogStatus.INFO,
				"Next touch point value is matched As after " + days + " days from created date  in :" + contactName4,
				YesNo.No);

	} else {
		sa.assertTrue(false, "Next touch point value is not matched As after " + days
				+ " days from created date  in contact :" + contactName4);
		log(LogStatus.SKIP, "Next touch point value is not matched As after " + days
				+ " days from created date  in contact :" + contactName4, YesNo.Yes);
	}

	refresh(driver);		
	ThreadSleep(5000);
	String contactName5=M7NContact22FName+" "+M7NContact22LName;
	if (cp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName5,YesNo.No);
		ThreadSleep(1000);
		if (cp.clickOnAlreadyCreatedItem(projectName, contactName5, 30)) {
			log(LogStatus.INFO,"Clicked on  : "+contactName5+" For : "+tabObj2,YesNo.No);
			ThreadSleep(2000);
			WebElement ele2 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
			click(driver, ele2, RelatedTab.Details.toString(), action.BOOLEAN);
			ThreadSleep(3000);
			 ele=cp.getlastTouchPointValue(projectName, 10);
			if (ele!=null) {
				String expectedValue = M7NEvent12EndDate; 
				String actualValue = ele.getText().trim();
				if (cp.verifyDate(expectedValue, actualValue)) {
					log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName5, YesNo.No);
				}
			else {
					log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName5+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
					sa.assertTrue(false,"last touch point value is not matched For : "+contactName5+" Actual : "+actualValue+" /t Expected : "+expectedValue );
				}
			}else {
				log(LogStatus.ERROR, "last touch point value is not visible For : "+contactName5, YesNo.Yes);
				sa.assertTrue(false,"last touch point value is not visible For : "+contactName5 );
			}
		} else {
			sa.assertTrue(false,"Item Not Found : "+contactName5+" For : "+tabObj2);
			log(LogStatus.SKIP,"Item Not Found : "+contactName5+" For : "+tabObj2,YesNo.Yes);
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName5);
		log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName5,YesNo.Yes);
	}
	refresh(driver);	
	ThreadSleep(10000);
	WebElement ele2 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
	click(driver, ele2, RelatedTab.Details.toString(), action.BOOLEAN);
	ThreadSleep(3000);
	int days1=123;
	String actualDate1= cp.getNextTouchPointDateValue(projectName, 30).getText();
	String expectedDate1 =previousOrForwardDateAccordingToTimeZone(days1, "MM/dd/yyyy", "America/Los_Angles");
	if(cp.verifyDate(expectedDate1, actualDate1)){
		log(LogStatus.INFO,"Next touch point value is matched As after "+days1+" days from created date  in :"+contactName5,YesNo.No);	
	
	}else{
		sa.assertTrue(false,"Next touch point value is not matched As after "+days1+" days from created date  in contact :"+contactName5);
		log(LogStatus.SKIP,"Next touch point value is not matched As after "+days1+" days from created date  in contact :"+contactName,YesNo.Yes);
	}
	refresh(driver);		
	ThreadSleep(5000);
	String contactName6=M7NContact23FName+" "+M7NContact23LName;
	if (cp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName6,YesNo.No);
		ThreadSleep(1000);
		if (cp.clickOnAlreadyCreatedItem(projectName, contactName6, 30)) {
			log(LogStatus.INFO,"Clicked on  : "+contactName6+" For : "+tabObj2,YesNo.No);
			ThreadSleep(2000);
			WebElement ele3 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
			click(driver, ele3, RelatedTab.Details.toString(), action.BOOLEAN);
			ThreadSleep(3000);
			 ele=cp.getlastTouchPointValue(projectName, 10);
			if (ele!=null) {
				String expectedValue = M7NEvent12EndDate; 
				String actualValue = ele.getText().trim();
				if (cp.verifyDate(expectedValue, actualValue)) {
					log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName6, YesNo.No);
				}
			else {
					log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName6+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
					sa.assertTrue(false,"last touch point value is not matched For : "+contactName6+" Actual : "+actualValue+" /t Expected : "+expectedValue );
				}
			}else {
				log(LogStatus.ERROR, "last touch point value is not visible For : "+contactName6, YesNo.Yes);
				sa.assertTrue(false,"last touch point value is not visible For : "+contactName );
			}
		} else {
			sa.assertTrue(false,"Item Not Found : "+contactName6+" For : "+tabObj2);
			log(LogStatus.SKIP,"Item Not Found : "+contactName6+" For : "+tabObj2,YesNo.Yes);
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName6);
		log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName6,YesNo.Yes);
	}
	refresh(driver);	
	ThreadSleep(10000);
	WebElement ele3 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
	click(driver, ele3, RelatedTab.Details.toString(), action.BOOLEAN);
	ThreadSleep(3000);
	int days2=183;
	String actualDate2= cp.getNextTouchPointDateValue(projectName, 30).getText();
	String expectedDate2 =previousOrForwardDateAccordingToTimeZone(days2, "MM/dd/yyyy", "America/Los_Angles");
	if(cp.verifyDate(expectedDate2, actualDate2)){
		log(LogStatus.INFO,"Next touch point value is matched As after "+days2+" days from created date  in :"+contactName6,YesNo.No);	
	
	}else{
		sa.assertTrue(false,"Next touch point value is not matched As after "+days2+" days from created date  in contact :"+contactName6);
		log(LogStatus.SKIP,"Next touch point value is not matched As after "+days2+" days from created date  in contact :"+contactName6,YesNo.Yes);
	}
	refresh(driver);		
	ThreadSleep(5000);
	String contactName7=M7NContact24FName+" "+M7NContact24LName;
	if (cp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName7,YesNo.No);
		ThreadSleep(1000);
		if (cp.clickOnAlreadyCreatedItem(projectName, contactName7, 30)) {
			log(LogStatus.INFO,"Clicked on  : "+contactName7+" For : "+tabObj2,YesNo.No);
			ThreadSleep(2000);
			WebElement ele4 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
			click(driver, ele4, RelatedTab.Details.toString(), action.BOOLEAN);
			ThreadSleep(3000);
			 ele=cp.getlastTouchPointValue(projectName, 10);
			if (ele!=null) {
				String expectedValue = M7NEvent12EndDate; 
				String actualValue = ele.getText().trim();
				if (cp.verifyDate(expectedValue, actualValue)) {
					log(LogStatus.INFO,expectedValue+" successfully verified last touch point date For : "+contactName7, YesNo.No);
				}
			else {
					log(LogStatus.ERROR, "Last touch point value is not matched For : "+contactName7+" Actual : "+actualValue+" /t Expected : "+expectedValue, YesNo.Yes);
					sa.assertTrue(false,"last touch point value is not matched For : "+contactName7+" Actual : "+actualValue+" /t Expected : "+expectedValue );
				}
			}else {
				log(LogStatus.ERROR, "last touch point value is not visible For : "+contactName7, YesNo.Yes);
				sa.assertTrue(false,"last touch point value is not visible For : "+contactName7 );
			}
		} else {
			sa.assertTrue(false,"Item Not Found : "+contactName7+" For : "+tabObj2);
			log(LogStatus.SKIP,"Item Not Found : "+contactName7+" For : "+tabObj2,YesNo.Yes);
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
		log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
	}
	refresh(driver);	
	ThreadSleep(10000);
	WebElement ele4 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
	click(driver, ele4, RelatedTab.Details.toString(), action.BOOLEAN);
	ThreadSleep(3000);
	int days3=123;
	String actualDate3= cp.getNextTouchPointDateValue(projectName, 30).getText();
	String expectedDate3 =previousOrForwardDateAccordingToTimeZone(days3, "MM/dd/yyyy", "America/Los_Angles");
	if(cp.verifyDate(expectedDate3, actualDate3)){
		log(LogStatus.INFO,"Next touch point value is matched As after "+days3+" days from created date  in :"+contactName7,YesNo.No);	
	
	}else{
		sa.assertTrue(false,"Next touch point value is not matched As after "+days3+" days from created date  in contact :"+contactName7);
		log(LogStatus.SKIP,"Next touch point value is not matched As after "+days3+" days from created date  in contact :"+contactName7,YesNo.Yes);
	}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
}
@Parameters({ "projectName"})
@Test
public void M7NTc050_Updatecontactswithtier12AndVerifyVerifyLastTouchPointandNextTouchPointDate(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null ;
	String contactName=M7NContact21FName+" "+M7NContact21LName;
	String tier ="2";
	if(lp.clickOnTab(contactName, mode, TabName.ContactTab)){
		log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);
		if(lp.clickOnAlreadyCreatedItem(projectName, TabName.ContactTab, contactName, 30)){
			log(LogStatus.INFO,"click on Created Contact : "+contactName,YesNo.No);	
			ThreadSleep(3000);
			
			if(cp.UpdateContactTier(projectName, PageName.ContactPage, tier))
			{log(LogStatus.INFO,"Contact tier updated as: "+tier,YesNo.No);	
				
				refresh(driver);
				ThreadSleep(5000);
				WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(3000);
						ele = cp.getlastTouchPointValue(projectName, 10);
						if (ele != null) {
							String expectedValue = M7NEvent12EndDate;
							String actualValue = ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,
										expectedValue + " successfully verified last touch point date For : " + contactName,
										YesNo.No);
							} else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : " + contactName + " Actual : "
										+ actualValue + " /t Expected : " + expectedValue, YesNo.Yes);
								sa.assertTrue(false, "last touch point value is not matched For : " + contactName + " Actual : "
										+ actualValue + " /t Expected : " + expectedValue);
							}
						} else {
							log(LogStatus.ERROR, "last touch point value is not visible For : " + contactName, YesNo.Yes);
							sa.assertTrue(false, "last touch point value is not visible For : " + contactName);
						}
					}
			refresh(driver);		
			ThreadSleep(5000);
			WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
			click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
			ThreadSleep(3000);
				int days=123;
				String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
				String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "M/dd/yyyy", "America/Los_Angles");
				if(cp.verifyDate(expectedDate, actualDate)){
					log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
					log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
				}
			}else{
				sa.assertTrue(false,"Not Able to Update Contact  tier: "+contactName);
				log(LogStatus.SKIP,"Not Able to Update Contact  tier: "+contactName,YesNo.Yes);
			}
			
		}else{
			sa.assertTrue(false,"Not Able to click on Create Contact : "+contactName);
			log(LogStatus.SKIP,"Not Able to click on created Contact: "+contactName,YesNo.Yes);
		}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
	}
@Parameters({ "projectName"})
@Test
public void M7NTc051_UpdatecontactROBERTswithtier3AndVerifyVerifyLastTouchPointandNextTouchPointDate(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null ;
	String contactName=M7NContact24FName+" "+M7NContact24LName;
	String tier ="3";
	if(lp.clickOnTab(contactName, mode, TabName.ContactTab)){
		log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);
		if(lp.clickOnAlreadyCreatedItem(projectName, TabName.ContactTab, contactName, 30)){
			log(LogStatus.INFO,"click on Created Contact : "+contactName,YesNo.No);	
			ThreadSleep(3000);
			
			if(cp.UpdateContactTier(projectName, PageName.ContactPage, tier))
			{log(LogStatus.INFO,"Contact tier updated as: "+tier,YesNo.No);	
				refresh(driver);
				ThreadSleep(5000);
				WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(3000);
						ele = cp.getlastTouchPointValue(projectName, 10);
						if (ele != null) {
							String expectedValue = M7NEvent12EndDate;
							String actualValue = ele.getText().trim();
							if (cp.verifyDate(expectedValue, actualValue)) {
								log(LogStatus.INFO,
										expectedValue + " successfully verified last touch point date For : " + contactName,
										YesNo.No);
							} else {
								log(LogStatus.ERROR, "Last touch point value is not matched For : " + contactName + " Actual : "
										+ actualValue + " /t Expected : " + expectedValue, YesNo.Yes);
								sa.assertTrue(false, "last touch point value is not matched For : " + contactName + " Actual : "
										+ actualValue + " /t Expected : " + expectedValue);
							}
						} else {
							log(LogStatus.ERROR, "last touch point value is not visible For : " + contactName, YesNo.Yes);
							sa.assertTrue(false, "last touch point value is not visible For : " + contactName);
						}
					}
			refresh(driver);		
			ThreadSleep(5000);
			WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
			click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
			ThreadSleep(3000);
				int days=183;
				String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
				String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "M/dd/yyyy", "America/Los_Angles");
				if(cp.verifyDate(expectedDate, actualDate)){
					log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
					log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
				}
			}else{
				sa.assertTrue(false,"Not Able to Update Contact  tier: "+contactName);
				log(LogStatus.SKIP,"Not Able to Update Contact  tier: "+contactName,YesNo.Yes);
			}
			
		}else{
			sa.assertTrue(false,"Not Able to click on Create Contact : "+contactName);
			log(LogStatus.SKIP,"Not Able to click on created Contact: "+contactName,YesNo.Yes);
		}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
	}
@Parameters({ "projectName"})
@Test
public void M7NTc052_UpdateJamesContactswithtier1andAndVerifyVerifyLastTouchPointandNextTouchPointDate(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String contactName=M7NContact25FName+" "+M7NContact25LName;
	String tier ="1";
	if(lp.clickOnTab(contactName, mode, TabName.ContactTab)){
		log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);
		if(lp.clickOnAlreadyCreatedItem(projectName, TabName.ContactTab, contactName, 30)){
			log(LogStatus.INFO,"click on Created Contact : "+contactName,YesNo.No);	
			ThreadSleep(3000);
			
			if(cp.UpdateContactTier(projectName, PageName.ContactPage, tier)){
				
				log(LogStatus.INFO,"Contact tier updated as: "+tier,YesNo.No);	
				refresh(driver);		
				ThreadSleep(5000);
				WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(3000);
				String lastTouchPoint= cp.getlastTouchPointValue(projectName, 30).getText();
				if(lastTouchPoint.contains("")||lastTouchPoint.equalsIgnoreCase("")){
					log(LogStatus.INFO,"Last touch point value is matched As blank in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Last touch point value is not matched As blank in contact :"+contactName);
					log(LogStatus.SKIP,"Last touch point value is not matched As blank in contact :"+contactName,YesNo.Yes);
				}
				
				int days=90;
				String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
				String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "M/dd/yyyy", "America/Los_Angles");
				if(cp.verifyDate(expectedDate, actualDate)){
					log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
					log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
				}
			}else{
				sa.assertTrue(false,"Not Able to Update Contact  tier: "+contactName);
				log(LogStatus.SKIP,"Not Able to Update Contact  tier: "+contactName,YesNo.Yes);
			}
			
		}else{
			sa.assertTrue(false,"Not Able to click on Create Contact : "+contactName);
			log(LogStatus.SKIP,"Not Able to click on created Contact: "+contactName,YesNo.Yes);
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
public void M7NTc052_1_UpdateJamesContactswithEventandAndVerifyVerifyLastTouchPointandNextTouchPointDate(String projectName) {
	GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String contactName=M7NContact25FName+" "+M7NContact25LName;
	M7NEvent13StartDate=previousOrForwardDate(+1, "M/d/YYYY");
	M7NEvent13EndDate=previousOrForwardDate(+2, "M/d/YYYY");
	String task = M7NEvent13Subject;
	String[][] Event4 = {
			{PageLabel.Start_Date.toString(),M7NEvent13StartDate},
			{PageLabel.End_Date.toString(),M7NEvent13EndDate},
			{PageLabel.Subject.toString(),task}};
	
	if (lp.clickAnyCellonCalender(projectName)) {
		log(LogStatus.INFO,"Able to click on Calendar/Event Link",YesNo.No);
		gp.enterValueForTask(projectName, PageName.Object2Page, Event4, 20);
		if (clickUsingJavaScript(driver, gp.getCustomTabSaveBtn(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
			ExcelUtils.writeData(phase1DataSheetFilePath,M7NEvent13StartDate, "Events", excelLabel.Variable_Name, "M7NEvent13", excelLabel.Start_Date);
			ExcelUtils.writeData(phase1DataSheetFilePath,M7NEvent13EndDate, "Events", excelLabel.Variable_Name, "M7NEvent13", excelLabel.End_Date);
		}else {
			sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
			log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Calendar/Event Link");
		log(LogStatus.SKIP,"Not Able to Click on Calendar/Event Link",YesNo.Yes);	
	}
	refresh(driver);		
	ThreadSleep(5000);
	WebElement ele1 = lp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
	click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
	ThreadSleep(3000);
	String lastTouchPoint= cp.getlastTouchPointValue(projectName, 30).getText();
	if(lastTouchPoint.contains("")||lastTouchPoint.equalsIgnoreCase("")){
		log(LogStatus.INFO,"Last touch point value is matched As blank in :"+contactName,YesNo.No);	

	}else{
		sa.assertTrue(false,"Last touch point value is not matched As blank in contact :"+contactName);
		log(LogStatus.SKIP,"Last touch point value is not matched As blank in contact :"+contactName,YesNo.Yes);
	}
		refresh(driver);	
		ThreadSleep(5000);
		int days=90;
		WebElement ele2 = lp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
		click(driver, ele2, RelatedTab.Details.toString(), action.BOOLEAN);
		ThreadSleep(3000);
		String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
		String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "MM/dd/yyyy", "America/Los_Angles");
		if(cp.verifyDate(expectedDate, actualDate)){
			log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	
		
		}else{
			sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
			log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
@Parameters({ "projectName"})
@Test
public void M7NTc053_UpdateTerryContactswithtier1andAndVerifyVerifyLastTouchPointandNextTouchPointDate(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String contactName=M7NContact26FName+" "+M7NContact26LName;
	String tier ="3";
	if(lp.clickOnTab(contactName, mode, TabName.ContactTab)){
		log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);
		if(lp.clickOnAlreadyCreatedItem(projectName, TabName.ContactTab, contactName, 30)){
			log(LogStatus.INFO,"click on Created Contact : "+contactName,YesNo.No);	
			ThreadSleep(3000);
			
			if(cp.UpdateContactTier(projectName, PageName.ContactPage, tier)){
				
				log(LogStatus.INFO,"Contact tier updated as: "+tier,YesNo.No);	
				refresh(driver);		
				ThreadSleep(5000);
				WebElement ele1 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
				ThreadSleep(3000);
				String lastTouchPoint= cp.getlastTouchPointValue(projectName, 30).getText();
				if(lastTouchPoint.contains("")||lastTouchPoint.equalsIgnoreCase("")){
					log(LogStatus.INFO,"Last touch point value is matched As blank in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Last touch point value is not matched As blank in contact :"+contactName);
					log(LogStatus.SKIP,"Last touch point value is not matched As blank in contact :"+contactName,YesNo.Yes);
				}
				
				int days=180;
				String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
				String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "M/dd/yyyy", "America/Los_Angles");
				if(cp.verifyDate(expectedDate, actualDate)){
					log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
					log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
				}
			}else{
				sa.assertTrue(false,"Not Able to Update Contact  tier: "+contactName);
				log(LogStatus.SKIP,"Not Able to Update Contact  tier: "+contactName,YesNo.Yes);
			}
			
		}else{
			sa.assertTrue(false,"Not Able to click on Create Contact : "+contactName);
			log(LogStatus.SKIP,"Not Able to click on created Contact: "+contactName,YesNo.Yes);
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
public void M7NTc053_1_UpdateTerryContactswithEventandAndVerifyVerifyLastTouchPointandNextTouchPointDate(String projectName) {
	GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null ;
	String contactName=M7NContact26FName+" "+M7NContact26LName;
	M7NEvent14StartDate=todaysDate;
	M7NEvent14EndDate=todaysDate;
	String task = M7NEvent14Subject;
	String[][] Event4 = {{PageLabel.Subject.toString(),task},
			{PageLabel.Start_Date.toString(),M7NEvent14StartDate},
			{PageLabel.End_Date.toString(),M7NEvent14EndDate},
			{PageLabel.All_Day_Event.toString(),"true"}};
	
	
	if (lp.clickAnyCellonCalender(projectName)) {
		log(LogStatus.INFO,"Able to click on Calendar/Event Link",YesNo.No);
		gp.enterValueForTask(projectName, PageName.Object2Page, Event4, 20);
		if (clickUsingJavaScript(driver, gp.getCustomTabSaveBtn(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);	
			ExcelUtils.writeData(phase1DataSheetFilePath,M7NEvent14StartDate, "Events", excelLabel.Variable_Name, "M7NEvent14", excelLabel.Start_Date);
			ExcelUtils.writeData(phase1DataSheetFilePath,M7NEvent14EndDate, "Events", excelLabel.Variable_Name, "M7NEvent14", excelLabel.End_Date);
		}else {
			sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
			log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Calendar/Event Link");
		log(LogStatus.SKIP,"Not Able to Click on Calendar/Event Link",YesNo.Yes);	
	}


	if (cp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+contactName,YesNo.No);
		ThreadSleep(1000);
		if (cp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
			log(LogStatus.INFO,"Clicked on  : "+contactName+" For : "+tabObj2,YesNo.No);
			ThreadSleep(2000);
				
		}else {
				sa.assertTrue(false,"Item Not Found : "+contactName+" For : "+tabObj2);
			log(LogStatus.SKIP,"Item Not Found : "+contactName+" For : "+tabObj2,YesNo.Yes);
		
		}} else {{
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+contactName,YesNo.Yes);
		}}
	refresh(driver);		
	ThreadSleep(5000);
	WebElement ele1 = lp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
	click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
	ThreadSleep(3000);
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

		refresh(driver);	
		ThreadSleep(5000);
		int days=180;
		WebElement ele2 = lp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
		click(driver, ele2, RelatedTab.Details.toString(), action.BOOLEAN);
		ThreadSleep(3000);
		String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
		String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "MM/dd/yyyy", "America/Los_Angles");
		if(cp.verifyDate(expectedDate, actualDate)){
			log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	
		
		}else{
			sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
			log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}}


