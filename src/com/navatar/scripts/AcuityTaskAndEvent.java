package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.IconType;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.pageObjects.*;

import com.relevantcodes.extentreports.LogStatus;

public class AcuityTaskAndEvent extends BaseLib {
	@Parameters({ "projectName" })
	@Test
	public void ATETc001_CreateCRMUser(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		for(int k=0; k<11; k++)
		{
			lp = new LoginPageBusinessLayer(driver);
			home = new HomePageBusineesLayer(driver);
			setup = new SetupPageBusinessLayer(driver);
			String[] firstName= {crmUser6FirstName,crmUser7FirstName,crmUser8FirstName,crmUser9FirstName,crmUser10FirstName,crmUser11FirstName,crmUser12FirstName,crmUser13FirstName,crmUser14FirstName,crmUser15FirstName,crmUser16FirstName};
			String[] lastName= {crmUser6LastName,crmUser7LastName,crmUser8LastName,crmUser9LastName,crmUser10LastName,crmUser11LastName,crmUser12LastName,crmUser13LastName,crmUser14LastName,crmUser15LastName,crmUser16LastName};
			String[] userLicence= {crmUser6Lience,crmUser7Lience,crmUser8Lience,crmUser9Lience,crmUser10Lience,crmUser11Lience,crmUser12Lience,crmUser13Lience,crmUser14Lience,crmUser15Lience,crmUser16Lience};
			String[] userProfile= {crmUser6Profile,crmUser7Profile,crmUser8Profile,crmUser9Profile,crmUser10Profile,crmUser11Profile,crmUser12Profile,crmUser13Profile,crmUser14Profile,crmUser15Profile,crmUser16Profile};
            String[] userTitle= {crmUser6Title,crmUser7Title,crmUser8Title,crmUser9Title,crmUser10Title,crmUser11Title,crmUser12Title,crmUser13Title,crmUser14Title,crmUser15Title,crmUser16Title};
			String parentWindow = null;
			String[] splitedUserLastName = removeNumbersFromString(lastName[k]);
			String UserLastName = splitedUserLastName[0] + lp.generateRandomNumber();
			String emailId = lp.generateRandomEmailId(gmailUserName);

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
						if (setup.createPEUser(firstName[k], UserLastName, emailId, userLicence[k], userProfile[k], userTitle[k])) {
							log(LogStatus.INFO,
									"CRM User is created Successfully: " + firstName[k] + " " + UserLastName,
									YesNo.No);
							ExcelUtils.writeData(testCasesFilePath, emailId, "Users", excelLabel.Variable_Name, "User"+(k+6),
									excelLabel.User_Email);
							ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name,
									"User"+(k+6), excelLabel.User_Last_Name);
							flag = true;
							break;

						}
						driver.close();
						driver.switchTo().window(parentWindow);

					}
				} catch (Exception e) {
					log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
				}

			}
			if (flag) {

				if (!environment.equalsIgnoreCase(Environment.Sandbox.toString())) {
					switchToDefaultContent(driver);
					CommonLib.ThreadSleep(5000);
					if (setup.installedPackages(firstName[k], UserLastName)) {
						appLog.info("PE Package is installed Successfully in CRM User: " + firstName[k] + " "
								+ UserLastName);

					} else {
						appLog.error(
								"Not able to install PE package in CRM User1: " + firstName[k] + " " + UserLastName);
						sa.assertTrue(false,
								"Not able to install PE package in CRM User1: " + firstName[k] + " " + UserLastName);
						log(LogStatus.ERROR,
								"Not able to install PE package in CRM User1: " + firstName[k] + " " + UserLastName,
								YesNo.Yes);
					}
				}
			} else {

				log(LogStatus.ERROR, "could not click on setup link, test case fail", YesNo.Yes);
				sa.assertTrue(false, "could not click on setup link, test case fail");

			}
			lp.CRMlogout();
			closeBrowser();
			// driver.switchTo().window(parentWindow);
			config(ExcelUtils.readDataFromPropertyFile("Browser"));
			lp = new LoginPageBusinessLayer(driver);
			String passwordResetLink = null;
			try {
				passwordResetLink = new EmailLib().getResetPasswordLink("passwordreset",
						ExcelUtils.readDataFromPropertyFile("gmailUserName"),
						ExcelUtils.readDataFromPropertyFile("gmailPassword"));
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
			appLog.info("ResetLinkIs: " + passwordResetLink);
			driver.get(passwordResetLink);
			if (lp.setNewPassword()) {
				appLog.info("Password is set successfully for CRM User: " + firstName[k] + " " + UserLastName);
			} else {
				appLog.info("Password is not set for CRM User: " + firstName[k] + " " + UserLastName);
				sa.assertTrue(false, "Password is not set for CRM User: " + firstName[k] + " " + UserLastName);
				log(LogStatus.ERROR, "Password is not set for CRM User: " + firstName[k] + " " + UserLastName,
						YesNo.Yes);
			}
			lp.CRMlogout();
			ThreadSleep(8000);

		}
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ATETc002_VerifyTheIntermediaryAccountAcuityTabWhenThereIsNoAccountConnectedWithDeal(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATERecord1;		
		String sectionHeader=ATE_Section1;
		String tabsOnTagged=ATE_Tabs1;
		String defaultTabOntagged="Companies";
		String message=bp.acuityDefaultMessage;

		String contactHeader=ATE_ContactHeader1;

		String[] arrSectionHeader=sectionHeader.split("<break>");		
		List<String> sectionHeaderName = new ArrayList<String>(Arrays.asList(arrSectionHeader));


		String[] arrTabName= tabsOnTagged.split("<break>");		
		List<String> tabNameOnTagged = new ArrayList<String>(Arrays.asList(arrTabName));

		String[] arrContactHeader=contactHeader.split("<break>");
		List<String> contactHeaders = new ArrayList<String>(Arrays.asList(arrContactHeader));

		List<String> connectionHeaders=new ArrayList<String>();
		List<String> connectionTooltips=new ArrayList<String>();
		List<String> blankList=new ArrayList<String>();


		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);
				if(bp.clicktabOnPage("Acuity"))
				{
					log(LogStatus.INFO, "Clicked on Acuity Tab", YesNo.No);

					if(bp.verifySectionsAndTooltipOnAcuityTab(sectionHeaderName,sectionHeaderName))
					{
						log(LogStatus.INFO, "Section Headers have been verified on acuity tab", YesNo.No);

						if(bp.verifyTabsOnTaggedSection(tabNameOnTagged,defaultTabOntagged))
						{
							log(LogStatus.INFO, "Default selected Tab and Tabs have been verified on Tagged section ", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Default selected Tab and Tabs are not verified on Tagged section ", YesNo.No);
							sa.assertTrue(false, "Default selected Tab and Tabs are not verified on Tagged section ");
						}
						refresh(driver);

						ArrayList<String> result= bp.verifyColumnsAndMessageOnTabsOfTagged(tabNameOnTagged, message);
						if(result.isEmpty())
						{
							log(LogStatus.INFO, "The Column name, Time referenced and message has been verified ", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The Column name, Time referenced and message are not verified. "+result, YesNo.No);
							sa.assertTrue(false, "The Column name, Time referenced and message are not verified. "+result);
						}

						ArrayList<String> result1=bp.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(message, contactHeaders, message, blankList, null,connectionHeaders,null);

						if(result1.isEmpty())
						{
							log(LogStatus.INFO, "The header name and message have been verified on Interaction and Contacts ", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The header name and message are not verified on Interaction and Contacts. "+result1, YesNo.No);
							sa.assertTrue(false, "The header name and message are not verified on Interaction and Contacts "+result1);
						}

						ArrayList<String> result2=bp.verifyToolTipOnDealsConnctionsAndContactsHeader(blankList, contactHeaders, connectionTooltips);
						if(result2.isEmpty())
						{
							log(LogStatus.INFO, "The Tooltip on Contact header have been verified", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The Tooltip on Contact header are not verified. "+result2, YesNo.No);
							sa.assertTrue(false, "The Tooltip on Contact header are not verified. "+result2);
						}


						if (!bp.verifyViewAllButtonOnIntractionCard(5)) {
							log(LogStatus.INFO, "view All Button is not visible on Interaction section", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "view All Button is visible on Interaction section", YesNo.No);
							sa.assertTrue(false, "view All Button is visible on Interaction section");
						}
					}
					else
					{
						log(LogStatus.ERROR, "Section headers and Tooltip are not verified on acuity tab", YesNo.No);
						sa.assertTrue(false, "Section headers and Tooltip are not verified on acuity tab");
					}
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open "+recordName +" reocrd", YesNo.No);
				sa.assertTrue(false, "Not able to open "+recordName +" reocrd");
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab : "+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab : "+tabObj1);
		}
		lp.CRMlogout();	
		sa.assertAll();	
	}


	@Parameters({ "projectName" })
	@Test
	public void ATETc003_VerifyContactPageAcuityTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATE_Contact1;
		String sectionHeader=ATE_Section2;
		String tabsOnTagged=ATE_Tabs1;
		String defaultTabOntagged="Companies";
		String message=bp.acuityDefaultMessage;

		String connectionHeader=ATE_ConnectionHeader1;

		String[] arrSectionHeader=sectionHeader.split("<break>");		
		List<String> sectionHeaderName = new ArrayList<String>(Arrays.asList(arrSectionHeader));


		String[] arrTabName= tabsOnTagged.split("<break>");		
		List<String> tabNameOnTagged = new ArrayList<String>(Arrays.asList(arrTabName));

		String[] arrConnnectionHeader=connectionHeader.split("<break>");
		List<String> connnectionHeaders = new ArrayList<String>(Arrays.asList(arrConnnectionHeader));

		List<String> blankList=new ArrayList<String>();

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);
				if(bp.clicktabOnPage("Acuity"))
				{
					log(LogStatus.INFO, "Clicked on Acuity Tab", YesNo.No);

					if(bp.verifySectionsAndTooltipOnAcuityTab(sectionHeaderName,sectionHeaderName))
					{
						log(LogStatus.INFO, "Tagged Section Headers have been verified on acuity tab", YesNo.No);

						if(bp.verifyTabsOnTaggedSection(tabNameOnTagged,defaultTabOntagged))
						{
							log(LogStatus.INFO, "Default selected Tab and Tabs have been verified on Tagged section ", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Default selected Tab and Tabs are not verified on Tagged section ", YesNo.No);
							sa.assertTrue(false, "Default selected Tab and Tabs are not verified on Tagged section ");
						}
						refresh(driver);

						ArrayList<String> result= bp.verifyColumnsAndMessageOnTabsOfTagged(tabNameOnTagged, message);
						if(result.isEmpty())
						{
							log(LogStatus.INFO, "The Column name, Time referenced and message has been verified ", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The Column name, Time referenced and message are not verified. "+result, YesNo.No);
							sa.assertTrue(false, "The Column name, Time referenced and message are not verified. "+result);
						}
						
						ArrayList<String> result1=bp.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(message, blankList, null, blankList, null,connnectionHeaders,message);

						if(result1.isEmpty())
						{
							log(LogStatus.INFO, "The header name and message have been verified on Interaction and Connection Section. "+result1, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The header name and message are not verified on Interaction and Connection Section. "+result1, YesNo.No);
							sa.assertTrue(false, "The header name and message are not verified on Interaction and Connection Deals Section. "+result1);
						}

						ArrayList<String> result2=bp.verifyToolTipOnDealsConnctionsAndContactsHeader(blankList, blankList, connnectionHeaders);
						if(result2.isEmpty())
						{
							log(LogStatus.INFO, "The Tooltip on connection header have been verified", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The Tooltip on connection header are not verified. "+result2, YesNo.No);
							sa.assertTrue(false, "The Tooltip on connnection header are not verified. "+result2);
						}

						if (!bp.verifyViewAllButtonOnIntractionCard(5)) {
							log(LogStatus.INFO, "view All Button is not visible on Interaction section", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "view All Button is visible on Interaction section", YesNo.No);
							sa.assertTrue(false, "view All Button is visible on Interaction section");
						}
					}
					else
					{
						log(LogStatus.ERROR, "Tagged section headers and Tooltip are not verified on acuity tab", YesNo.No);
						sa.assertTrue(false, "Tagged section headers and Tooltip are not verified on acuity tab");
					}
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open "+recordName +" reocrd", YesNo.No);
				sa.assertTrue(false, "Not able to open "+recordName +" reocrd");
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab : "+tabObj2, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab : "+tabObj2);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}

	@Parameters({ "projectName" })
	@Test
	public void ATETc004_CreateATaskWithIntermediaryFirmContactAndVerifyTheAccountAcuityTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATERecord3;
		String activityType=ATE_ATActivityType1;
		String taskSubject=ATE_ATSubject1;
		String taskRelatedTo=ATE_ATRelatedTo1;
		String taskNotes=ATE_ATNotes1;
		String taskDueDate=ATE_AdvanceDueDate1;
		String taskStatus=ATE_AdvanceStatus1;
		String taskPriority=ATE_AdvancePriority1;

		String[] relatedToData=ATE_ARelatedTo1.split("<break>");
		String[] verifyRelatedToField= {relatedToData[0],crmUser6FirstName+" "+crmUser6LastName,relatedToData[1]};
		
		String contactSectionName=ATE_ContactName2;
		String contactSectionTitle=ATE_ContactTitle2;
		String contactSectionDeal=ATE_ContactDeal2;
		String contactSectionMeetingAndCalls=ATE_ContactMeetingAndCall2;
		String contactSectionEmail=ATE_ContactEmail2;

		String contactSectionName1=ATE_ContactName3;
		String contactSectionTitle1=ATE_ContactTitle3;
		String contactSectionDeal1=ATE_ContactDeal3;
		String contactSectionMeetingAndCalls1=ATE_ContactMeetingAndCall3;
		String contactSectionEmail1=ATE_ContactEmail3;
		
		String[] companiesTaggedName= {ATE_TaggedCompanyName1};
		String[] companiesTaggedTimeReference= {ATE_TaggedCompanyTimeReference1};

		String[] dealTaggedName= {ATE_TaggedDealName1};
		String[] dealTaggedTimeReference= {ATE_TaggedDealTimeReference1};


		String[][] basicsection = { { "Subject", taskSubject }, { "Notes", taskNotes }, { "Related_To", taskRelatedTo } };
		String[][] advanceSection = { { "Due Date Only", taskDueDate }, {"Status", taskStatus}, {"Priority", taskPriority} };

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (bp.createActivityTimeline(projectName, true, activityType, basicsection, advanceSection, null, null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject);

			if (lp.clickOnTab(projectName, tabObj1)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

				if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
						recordName, 30)) {
					log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

					if (bp.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						ArrayList<String> result = bp.verifyRecordOnInteractionCard(taskDueDate,IconType.Task,taskSubject, taskNotes, true, false,verifyRelatedToField,null);
						if (result.isEmpty()) {
							log(LogStatus.PASS,taskSubject + " record has been verified on intraction",YesNo.No);
							sa.assertTrue(true,taskSubject + " record has been verified on intraction");
						} else {
							log(LogStatus.ERROR,taskSubject + " record is not verified on intraction. "+result,YesNo.No);
							sa.assertTrue(false,taskSubject + " record is not verified on intraction. "+result);
						}

						ArrayList<String> result1=bp.verifyRecordOnContactSectionAcuity(contactSectionName, contactSectionTitle, contactSectionDeal, contactSectionMeetingAndCalls, contactSectionEmail);
						if(result1.isEmpty())
						{
							log(LogStatus.INFO, contactSectionName+" record on Contact section has been verified for "+recordName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, contactSectionName+" record on Contact section is not verified for "+recordName+". "+result1, YesNo.No);
							sa.assertTrue(false, contactSectionName+" record on Contact section is not verified for "+recordName+". "+result1);
						}

						ArrayList<String> result2=bp.verifyRecordOnContactSectionAcuity(contactSectionName1, contactSectionTitle1, contactSectionDeal1, contactSectionMeetingAndCalls1, contactSectionEmail1);
						if(result2.isEmpty())
						{
							log(LogStatus.INFO, contactSectionName1+" record on Contact section has been verified for "+recordName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, contactSectionName1+" record on Contact section is not verified for "+recordName+". "+result2, YesNo.No);
							sa.assertTrue(false, contactSectionName1+" record on Contact section is not verified for "+recordName+". "+result2);
						}

						ArrayList<String> result4=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, null, null, dealTaggedName, dealTaggedTimeReference);
						if(result4.isEmpty())
						{
							log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR,  "The record name and Time reference are not verifed. "+result4, YesNo.No);
							sa.assertTrue(false,  "The record name and Time reference are not verifed."+result4);
						}

						if (bp.verifySubjectLinkRedirectionOnIntraction(driver,taskSubject)) {
							log(LogStatus.INFO, "page successfully redirecting to the "+ taskSubject+ " page on new tab",	YesNo.No);			
						}
						else
						{
							log(LogStatus.ERROR, "page is not redirecting to the "+ taskSubject+ " page on new tab",	YesNo.No);		
							sa.assertTrue(false,  "page is not redirecting to the "+ taskSubject+ " page on new tab");

						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Acuity tab");
					}
				}
				else
				{
					log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
					sa.assertTrue(false,  "Not able to open record "+recordName);
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to click on tab "+tabObj1, YesNo.No);
				sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
			}

		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject);
		}		
		lp.CRMlogout();	
		sa.assertAll();	
	}


	@Parameters({ "projectName" })
	@Test
	public void ATETc005_VerifyConnectionPopupOnAccountPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATERecord3;
		String contactName=ATE_Contact1;

		String[] val=ATE_ConnectionHeader1.split("<break>");
		ArrayList<String> headerName=new ArrayList<String>();
		for(String txt:val)
		{
			headerName.add(txt);
		}

		String message=bp.acuityDefaultMessage1;

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
					String xPath="//a[text()='"+contactName+"']/ancestor::tr//button[@title='Connections']";
					WebElement ele=FindElement(driver, xPath, "Connection icon of "+contactName, action.SCROLLANDBOOLEAN, 20);
					if(click(driver, ele, "Connection icon of "+contactName, action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on Connection icon of "+contactName, YesNo.No);

						ArrayList<String> result=bp.verifyUIOfConnectionPopup(contactName, headerName, message);
						if(result.isEmpty())
						{
							log(LogStatus.INFO, "The UI of Connections popup have been verified", YesNo.No);
							sa.assertTrue(true,  "The UI of Connections popup have been verified");
						}
						else
						{
							log(LogStatus.ERROR, "The UI of Connections popup are not verified. "+result, YesNo.No);
							sa.assertTrue(false,  "The UI of Connections popup are not verified. "+result);

						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to Connection icon of "+contactName, YesNo.No);
						sa.assertTrue(false,  "Not able to Connection icon of "+contactName);
					}

				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab "+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}
		lp.CRMlogout();	
		sa.assertAll();	
	}


	@Parameters({ "projectName" })
	@Test
	public void ATETc006_VerifyContactPageAcuityTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATE_Contact1;

		String[] companiesTaggedName=new String[1];
		companiesTaggedName[0]=ATE_TaggedCompanyName1;

		String[] companiesTaggedTimeReference=new String[1];
		companiesTaggedTimeReference[0]=ATE_TaggedCompanyTimeReference1;

		String[] dealsTaggedName=new String[1];
		dealsTaggedName[0]=ATE_TaggedDealName1;

		String[] dealsTaggedTimeReference=new String[1];
		dealsTaggedTimeReference[0]=ATE_TaggedDealTimeReference1;

		ArrayList<String> emptyList=new ArrayList<String>();

		String[] val=ATE_ConnectionHeader1.split("<break>");
		ArrayList<String> connectionSectionHeadName=new ArrayList<String>();
		for(String txt:val)
		{
			connectionSectionHeadName.add(txt);
		}


		String taskDueDate=ATE_AdvanceDueDate1;

		String subject=ATE_ATSubject1;
		String notes=ATE_ATNotes1;

		String[] relatedToData=ATE_ARelatedTo1.split("<break>");
		String[] relatedTo= {relatedToData[0],crmUser6FirstName+" "+crmUser6LastName,relatedToData[1]};

		String[] relatedAssociation=ATE_ARelatedAsso1.split("<break>");

		ArrayList<String> tabName=new ArrayList<String>();
		tabName.add("People");

		String message=bp.acuityDefaultMessage;

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);					

					ArrayList<String> result=bp.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null, emptyList, null, emptyList, null, connectionSectionHeadName, message);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The message and header names of connection popup have been verified", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "Either the message or header names or both are not verified on Connection section. "+result, YesNo.No);
						sa.assertTrue(false,  "Either the message or header names or both are not verified on Connection section. "+result);
					}


					ArrayList<String> result2=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, null, null, dealsTaggedName, dealsTaggedTimeReference);
					if(result2.isEmpty())
					{
						log(LogStatus.INFO, "The record name and Referenced type count have been matched on Company and Deal tab", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The record name and Referenced type count are not matched on Company and Deal tab. "+result2, YesNo.No);
						sa.assertTrue(false,  "The record name and Referenced type count are not matched on Company and Deal tab. "+result2);
					}

					ArrayList<String> result3=bp.verifyColumnsAndMessageOnTabsOfTagged(tabName, message);
					if(result3.isEmpty())
					{
						log(LogStatus.INFO, "The message and columns has been verified on people tab", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The message and columns are not verified on people tab. "+result3 , YesNo.No);
						sa.assertTrue(false,  "The message and columns are not verified on people tab. "+result3);
					}

					ArrayList<String> result1=bp.verifyRecordOnInteractionCard(taskDueDate, IconType.Task,	subject, notes, true, false, relatedTo,relatedAssociation);
					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The task has been verified on Interaction card. subject name: "+subject , YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The task is not verified on Interaction card. subject name: "+subject+"  "+result1 , YesNo.No);
						sa.assertTrue(false,  "The task is not verified on Interaction card. subject name: "+subject+"  "+result1);
					}

				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab "+tabObj2, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj2);
		}
		lp.CRMlogout();	
		sa.assertAll();	
	}


	@Parameters({ "projectName" })
	@Test
	public void ATETc007_CreateALogACallTaskWithIntermediaryFirmsContactAndVerifyTheAccountAcuityTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATERecord3;
		String activityType=ATE_ATActivityType2;
		String taskSubject=ATE_ATSubject2;
		String taskRelatedTo=ATE_ATRelatedTo2;
		String taskNotes=ATE_ATNotes2;
		String taskDueDate=ATE_AdvanceDueDate2;
		String taskPriority=ATE_AdvancePriority2;

		String[] relatedToData=ATE_ARelatedTo2.split("<break>");

		String contactSectionName=ATE_ContactName4;
		String contactSectionTitle=ATE_ContactTitle4;
		String contactSectionDeal=ATE_ContactDeal4;
		String contactSectionMeetingAndCalls=ATE_ContactMeetingAndCall4;
		String contactSectionEmail=ATE_ContactEmail4;

		String contactSectionName1=ATE_ContactName3;
		String contactSectionTitle1=ATE_ContactTitle3;
		String contactSectionDeal1=ATE_ContactDeal3;
		String contactSectionMeetingAndCalls1=ATE_ContactMeetingAndCall3;
		String contactSectionEmail1=ATE_ContactEmail3;


		String[] companiesTaggedName=new String[2];
		String[] companiesTaggedTimeReference=new String[2];

		companiesTaggedName[0]= ATE_TaggedCompanyName1;
		companiesTaggedName[1]= ATE_TaggedCompanyName2;

		companiesTaggedTimeReference[0]=ATE_TaggedCompanyTimeReference1;
		companiesTaggedTimeReference[1]=ATE_TaggedCompanyTimeReference2;


		String[] dealTaggedName=new String[2];
		String[] dealTaggedTimeReference=new String[2];

		dealTaggedName[0]=ATE_TaggedDealName1;
		dealTaggedName[1]=ATE_TaggedDealName2;

		dealTaggedTimeReference[0]= ATE_TaggedDealTimeReference1;
		dealTaggedTimeReference[1]= ATE_TaggedDealTimeReference2;

		String[] peopleTagName={ATE_TaggedPeopleName1};
		String[] peopleTaggedTimeReference={ATE_TaggedPeopleTimeReference1};


		String[][] basicsection = { { "Subject", taskSubject }, { "Notes", taskNotes }, { "Related_To", taskRelatedTo } };
		String[][] advanceSection = { { "Due Date Only", taskDueDate }, {"Priority", taskPriority} };

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (bp.createActivityTimeline(projectName, true, activityType, basicsection, advanceSection, null, null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject);

			if (lp.clickOnTab(projectName, tabObj1)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

				if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
						recordName, 30)) {
					log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

					if (bp.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						ArrayList<String> result = bp.verifyRecordOnInteractionCard(taskDueDate,IconType.Call,taskSubject, taskNotes, true, false,relatedToData,null);
						if (result.isEmpty()) {
							log(LogStatus.PASS,taskSubject + " record has been verified on intraction",YesNo.No);
							sa.assertTrue(true,taskSubject + " record has been verified on intraction");
						} else {
							log(LogStatus.ERROR,taskSubject + " record not verified on intraction. "+result,YesNo.No);
							sa.assertTrue(false,taskSubject + " record not verified on intraction. "+result);
						}

						ArrayList<String> result1=bp.verifyRecordOnContactSectionAcuity(contactSectionName, contactSectionTitle, contactSectionDeal, contactSectionMeetingAndCalls, contactSectionEmail);
						if(result1.isEmpty())
						{
							log(LogStatus.INFO, contactSectionName+" record on Contact section has been verified for "+recordName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, contactSectionName+" record on Contact section is not verified for "+recordName+". "+result1, YesNo.No);
							sa.assertTrue(false, contactSectionName+" record on Contact section is not verified for "+recordName+". "+result1);
						}

						ArrayList<String> result2=bp.verifyRecordOnContactSectionAcuity(contactSectionName1, contactSectionTitle1, contactSectionDeal1, contactSectionMeetingAndCalls1, contactSectionEmail1);
						if(result2.isEmpty())
						{
							log(LogStatus.INFO, contactSectionName1+" record on Contact section has been verified for "+recordName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, contactSectionName1+" record on Contact section is not verified for "+recordName+". "+result2, YesNo.No);
							sa.assertTrue(false, contactSectionName1+" record on Contact section is not verified for "+recordName+". "+result2);
						}

						ArrayList<String> result4=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, peopleTagName, peopleTaggedTimeReference, dealTaggedName, dealTaggedTimeReference);
						if(result4.isEmpty())
						{
							log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR,  "The record name and Time reference are not verifed. "+result4, YesNo.No);
							sa.assertTrue(false,  "The record name and Time reference are not verifed. "+result4);
						}

						ArrayList<String> result6=bp.verifyRedirectionOnClickRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, peopleTagName, peopleTaggedTimeReference, dealTaggedName, dealTaggedTimeReference);
						if(result6.isEmpty())
						{
							log(LogStatus.INFO, "The redirection on click of record name and count of Time referenced have been verified", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The redirection on click of record name and count of time referenced is not working properly. "+result6, YesNo.No);
							sa.assertTrue(false,  "The redirection on click of record name and count of time referenced is not working properly. "+result6);
						}


						if (bp.verifySubjectLinkRedirectionOnIntraction(driver,taskSubject)) {
							log(LogStatus.INFO, "page successfully redirecting to the "+ taskSubject+ " page on new tab",	YesNo.No);			
						}
						else
						{
							log(LogStatus.ERROR, "page is not redirecting to the "+ taskSubject+ " page on new tab",	YesNo.No);		
							sa.assertTrue(false,  "page is not redirecting to the "+ taskSubject+ " page on new tab");
						}
						refresh(driver);

						String xPath="//a[text()='"+contactSectionName1+"']/ancestor::tr/td[@data-label='Meetings and Calls']//button";
						WebElement ele=FindElement(driver, xPath, "Meeting and call", action.SCROLLANDBOOLEAN, 30);
						if(click(driver, ele, "Meeting and call", action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, "Clicked on the count of meeting and call of "+contactSectionName1+" record on contact section",	YesNo.No);		
							xPath="//h2[contains(text(),'Meetings and Calls')]/../following-sibling::div//p[text()='No item display']";
							ele=FindElement(driver, xPath, "Message on Meeting and notes popup", action.SCROLLANDBOOLEAN, 20);
							if(ele!=null)
							{
								log(LogStatus.INFO, "Message : \"No items to display\" have been verified on meetings and notes popup",	YesNo.No);		
								sa.assertTrue(true,  "Message : \"No items to display\" have been verified on meetings and notes popup");

							}
							else
							{
								log(LogStatus.ERROR, "Message : \"No items to display\" is not verified on meetings and notes popup",	YesNo.No);		
								sa.assertTrue(false,  "Message : \"No items to display\" is not verified on meetings and notes popup");
							}
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+contactSectionName1+" record on contact section",	YesNo.No);		
							sa.assertTrue(false,  "Not able to click on the count of meeting and call of "+contactSectionName1+" record on contact section");
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Acuity tab");
					}
				}
				else
				{
					log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
					sa.assertTrue(false,  "Not able to open record "+recordName);
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to click on tab "+tabObj1, YesNo.No);
				sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
			}

		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject);
		}	
		lp.CRMlogout();	
		sa.assertAll();	
	}



	@Parameters({ "projectName" })
	@Test
	public void ATETc008_VerifyConnectionPopupOnIntermediaryAccountPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String xPath;
		WebElement ele;


		String taskSubject=ATE_ATSubject2;
		String taskDetails=ATE_ATNotes2;
		String taskDueDate=ATE_AdvanceDueDate2;

		String recordName=ATERecord3;
		String userName=crmUser6FirstName+" "+crmUser6LastName;

		String contactSectionName=ATE_ContactName4;
		String contactSectionTitle=ATE_ContactTitle4;
		String contactSectionDeal=ATE_ContactDeal4;
		String contactSectionMeetingAndCalls=ATE_ContactMeetingAndCall4;
		String contactSectionEmail=ATE_ContactEmail4;


		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					if(!bp.verifyViewAllButtonOnIntractionCard(20))
					{
						log(LogStatus.INFO, "View all button is not visible on interaction section", YesNo.No);


						ArrayList<String> result=bp.verifyRecordOnConnectionsPopUpOfContactInAcuity(contactSectionName, userName, contactSectionTitle, contactSectionDeal, contactSectionMeetingAndCalls, contactSectionEmail);
						if(result.isEmpty())
						{
							log(LogStatus.INFO, "The records on Connection popup have been verified for "+contactSectionName, YesNo.No);

							xPath="//a[text()='"+contactSectionName+"']/ancestor::tr/td[@data-col-key-value='1-button-1']//button";
							ele=FindElement(driver, xPath, contactSectionName+" connection icon", action.SCROLLANDBOOLEAN, 20);
							if(click(driver, ele, contactSectionName+" Connection icon", action.SCROLLANDBOOLEAN))
							{
								log(LogStatus.INFO, "Clicked on connection icon of "+contactSectionName, YesNo.No);


								xPath="//h2[contains(text(),'Connections of')]/../following-sibling::div//th[@data-label='Team Member']//a[text()='"+userName+"']";
								ele=FindElement(driver, xPath, userName+" user", action.SCROLLANDBOOLEAN, 20);
								ThreadSleep(2000);
								if(CommonLib.clickUsingJavaScript(driver, ele, userName+" user", action.SCROLLANDBOOLEAN))
								{
									log(LogStatus.INFO, "Clicked on user's name "+userName, YesNo.No);
									String id=switchOnWindow(driver);

									xPath="//span[text()='"+userName+"']";
									ele=FindElement(driver, xPath, "User name on user page", action.SCROLLANDBOOLEAN, 20);
									if(ele!=null)
									{
										log(LogStatus.INFO, "The link is redirecting to user page after clicking on username", YesNo.No);									

										driver.close();
										driver.switchTo().window(id);

										xPath="//a[text()='"+userName+"']/ancestor::tr//td[@data-label='Meetings and Calls']//button";
										ele=FindElement(driver, xPath, "meeting and call count of "+userName, action.SCROLLANDBOOLEAN, 20);
										if(click(driver, ele, contactSectionEmail, action.SCROLLANDBOOLEAN))
										{
											log(LogStatus.INFO, "clicked on the count of meeting and call of "+userName, YesNo.No);

											ArrayList<String> result1=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity("call",taskDueDate, taskSubject, taskDetails, userName);
											if(result1.isEmpty())
											{
												log(LogStatus.INFO, "The records on meeting & calls popup have been verified for "+contactSectionName, YesNo.No);
											}
											else
											{
												log(LogStatus.ERROR, "The records on meeting & calls popup are not verified for "+contactSectionName+". "+result1, YesNo.No);
												sa.assertTrue(false, "The records on meeting & calls popup are not verified for "+contactSectionName+". "+result1);
											}

										}
										else
										{
											log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+userName, YesNo.No);
											sa.assertTrue(false, "Not able to click on the count of meeting and call of "+userName);
										}
									}
									else
									{
										log(LogStatus.ERROR, "The link is not redirecting to user page after clicking on username", YesNo.No);
										sa.assertTrue(false, "The link is not redirecting to user page after clicking on username");
									}
								}
								else
								{
									log(LogStatus.ERROR, "Not able to click on user's name", YesNo.No);
									sa.assertTrue(false, "Not able to click on user's name");
								}

							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on connection icon of "+contactSectionName, YesNo.No);
								sa.assertTrue(false, "Not able to click on connection icon of "+contactSectionName);
							} 

						}
						else
						{
							log(LogStatus.ERROR, "The records on Connection popup are not verified for "+contactSectionName+". "+result , YesNo.No);
							sa.assertTrue(false, "The records on Connection popup are not verified for "+contactSectionName+". "+result);
						}
					}
					else
					{
						log(LogStatus.ERROR, "View all button is visible on interaction section", YesNo.No);
						sa.assertTrue(false,  "View all button is visible on interaction section");
					}

				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab "+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}



	@Parameters({ "projectName" })
	@Test
	public void ATETc009_VerifyContactPageAcuityTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String xPath;
		WebElement ele;

		String recordName=ATE_Contact1;
		String taskDetails=ATE_ATNotes2;	
		String taskSubject=ATE_ATSubject2;

		String taskNotes=ATE_ATNotes2;
		String taskDueDate=ATE_AdvanceDueDate2;

		String[] relatedToData=ATE_ARelatedTo2.split("<break>");

		String[] relatedAssocVal=ATE_ARelatedAsso2.split("<break>");

		String userName=crmUser6FirstName+" "+crmUser6LastName;

		String[] relatedAssociation=new String[relatedAssocVal.length+1];
		relatedAssociation[0]=userName;
		for(int i=1; i<relatedAssociation.length; i++)
		{
			relatedAssociation[i]=relatedAssocVal[i-1];
		}

		String[] completedate = taskDueDate.split("/");
		char dayMonth = completedate[0].charAt(0);
		String month;
		if (dayMonth == '0') {
			month = completedate[0].replaceAll("0", "");
		} else {
			month = completedate[0];
		}

		String date = month + "/" +completedate[1]  + "/" + completedate[2];

		String dealSectionName=ATE_DealName2;
		String dealSectionStage=ATE_DealStage2;
		String dealSectionDateRecieved=ATE_DealDateRecieved2;



		String connectionTeamMember=userName;
		String connectionTitle=ATE_ConnectionTitle1;
		String connectionDeal=ATE_ConnectionDeals1;
		String connectionMeetingAndCall=ATE_ConnectionMeetingAndCall1;
		String connectionEmail=ATE_ConnectionEmail1;


		String[] companiesTaggedName=new String[2];
		String[] companiesTaggedTimeReference=new String[2];

		companiesTaggedName[0]= ATE_TaggedCompanyName1;
		companiesTaggedName[1]= ATE_TaggedCompanyName2;

		companiesTaggedTimeReference[0]=ATE_TaggedCompanyTimeReference1;
		companiesTaggedTimeReference[1]=ATE_TaggedCompanyTimeReference2;


		String[] dealTaggedName=new String[2];
		String[] dealTaggedTimeReference=new String[2];

		dealTaggedName[0]=ATE_TaggedDealName1;
		dealTaggedName[1]=ATE_TaggedDealName2;

		dealTaggedTimeReference[0]= ATE_TaggedDealTimeReference1;
		dealTaggedTimeReference[1]= ATE_TaggedDealTimeReference2;

		String[] peopleTagName={ATE_TaggedPeopleName1};
		String[] peopleTaggedTimeReference={ATE_TaggedPeopleTimeReference1};

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					ArrayList<String> result = bp.verifyRecordOnInteractionCard(date,IconType.Call,taskSubject, taskNotes, true, false,relatedToData,relatedAssociation);
					if (result.isEmpty()) {
						log(LogStatus.PASS,taskSubject + " record has been verified on intraction",YesNo.No);
						sa.assertTrue(true,taskSubject + " record has been verified on intraction");
					} else {
						log(LogStatus.ERROR,taskSubject + " record is not verified on intraction",YesNo.No);
						sa.assertTrue(false,taskSubject + " record is not verified on intraction");
					}

					if(!bp.verifyViewAllButtonOnIntractionCard(20))
					{
						log(LogStatus.INFO, "View all button is not visible on interaction section", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "View all button is visible on interaction section", YesNo.No);
						sa.assertTrue(false,  "View all button is visible on interaction section");
					}

					ArrayList<String> result1=bp.verifyRecordOnConnectionsSectionInAcuity(recordName, connectionTeamMember, connectionTitle, connectionDeal, connectionMeetingAndCall, connectionEmail);

					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on Connection section in Acuity", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on Connection section in Acuity", YesNo.No);
						sa.assertTrue(false,  "The records are not verified on Connection section in Acuity");
					}

					ArrayList<String> result3=bp.verifyRecordOnDealsSectionInAcuity(recordName, dealSectionName, null, dealSectionStage, dealSectionDateRecieved);
					if(result3.isEmpty())
					{
						log(LogStatus.INFO, dealSectionName+" record on Deal section has been verified for "+recordName, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,  dealSectionName+" record on Deal section is not verified for "+recordName, YesNo.No);
						sa.assertTrue(false,  dealSectionName+" record on Deal section is not verified for "+recordName);
					}


					ArrayList<String> result4=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, peopleTagName, peopleTaggedTimeReference, dealTaggedName, dealTaggedTimeReference);
					if(result4.isEmpty())
					{
						log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,  "The record name and Time reference are not verifed", YesNo.No);
						sa.assertTrue(false,  "The record name and Time reference are not verifed");
					}

					ArrayList<String> result6=bp.verifyRedirectionOnClickRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, peopleTagName, peopleTaggedTimeReference, dealTaggedName, dealTaggedTimeReference);
					if(result6.isEmpty())
					{
						log(LogStatus.INFO, "The redirection on click of record name and count of Time referenced have been verified", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The redirection on click of record name and count of time referenced is not working properly", YesNo.No);
						sa.assertTrue(false,  "The redirection on click of record name and count of time referenced is not working properly");
					}

					if (bp.verifySubjectLinkRedirectionOnIntraction(driver,taskSubject)) {
						log(LogStatus.INFO, "page successfully redirecting to the "+ taskSubject+ " page on new tab",	YesNo.No);			
					}
					else
					{
						log(LogStatus.ERROR, "page is not redirecting to the "+ taskSubject+ " page on new tab",	YesNo.No);		
						sa.assertTrue(false,  "page is not redirecting to the "+ taskSubject+ " page on new tab");
					}						

					xPath="//a[text()='"+userName+"']/ancestor::tr//td[@data-label='Meetings and Calls']//button";
					ele=FindElement(driver, xPath, "meeting and call count of "+userName, action.SCROLLANDBOOLEAN, 20);
					if(click(driver, ele, recordName, action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on the count of meeting and call of "+userName, YesNo.No);

						ArrayList<String> result5=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity("call", date, taskSubject, taskDetails, userName);
						if(result5.isEmpty())
						{
							log(LogStatus.INFO, "The records on meeting & calls popup have been verified for "+recordName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records on meeting & calls popup are not verified for "+recordName, YesNo.No);
							sa.assertTrue(false, "The records on meeting & calls popup are not verified for "+recordName);
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+userName, YesNo.No);
						sa.assertTrue(false, "Not able to click on the count of meeting and call of "+userName);
					}
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab "+tabObj2, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj2);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}



	@Parameters({ "projectName" })
	@Test
	public void ATETc010_VerifyConnectionPopupOnIntermediaryAccountPageAndCountVerification(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String xPath;
		WebElement ele;

		String recordName=ATERecord3;
		String contactName=ATE_Contact1;

		String taskDetails=ATE_ATNotes2;	
		String taskSubject=ATE_ATSubject2;

		String taskDueDate=ATE_AdvanceDueDate2;

		String[] completedate = taskDueDate.split("/");
		char dayMonth = completedate[0].charAt(0);
		String month;
		if (dayMonth == '0') {
			month = completedate[0].replaceAll("0", "");
		} else {
			month = completedate[0];
		}

		String date = month + "/" +completedate[1]  + "/" + completedate[2];

		String taskDetails1=ATE_ATNotes3;	
		String taskSubject1=ATE_ATSubject3;

		String taskDueDate1=ATE_AdvanceStartDate1;

		String[] completedate1 = taskDueDate1.split("/");
		char dayMonth1 = completedate1[0].charAt(0);
		String month1;
		if (dayMonth1 == '0') {
			month1 = completedate1[0].replaceAll("0", "");
		} else {
			month1 = completedate1[0];
		}

		String date1 = month1 + "/" +completedate1[1]  + "/" + completedate1[2];		

		String userName=crmUser6FirstName+" "+crmUser6LastName;
		String userName1=crmUser7FirstName+" "+crmUser7LastName;

		String connectionTeamMember=userName;
		String connectionTitle=ATE_ConnectionTitle1;
		String connectionDeal=ATE_ConnectionDeals1;
		String connectionMeetingAndCall=ATE_ConnectionMeetingAndCall1;
		String connectionEmail=ATE_ConnectionEmail1;

		String connectionTeamMember1=userName1;
		String connectionTitle1=ATE_ConnectionTitle2;
		String connectionDeal1=ATE_ConnectionDeals2;
		String connectionMeetingAndCall1=ATE_ConnectionMeetingAndCall2;
		String connectionEmail1=ATE_ConnectionEmail2;


		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);



					ArrayList<String> result=bp.verifyRecordOnConnectionsPopUpOfContactInAcuity(contactName, connectionTeamMember, connectionTitle, connectionDeal, connectionMeetingAndCall, connectionEmail);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on Connection section in Acuity for user "+connectionTeamMember, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on Connection section in Acuity for user "+connectionTeamMember, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on Connection section in Acuity for user "+connectionTeamMember);
					}

					ArrayList<String> result1=bp.verifyRecordOnConnectionsPopUpOfContactInAcuity(contactName, connectionTeamMember1, connectionTitle1, connectionDeal1, connectionMeetingAndCall1, connectionEmail1);
					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on Connection section in Acuity for user "+connectionTeamMember1, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on Connection section in Acuity for user "+connectionTeamMember1, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on Connection section in Acuity for user "+connectionTeamMember1);
					}

					xPath="//a[text()='"+contactName+"']/ancestor::tr//td[@data-label='Meetings and Calls']//button";
					ele=FindElement(driver, xPath, "meeting and call count of "+contactName, action.SCROLLANDBOOLEAN, 20);
					if(click(driver, ele, contactName+" meetings and call count", action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on the count of meeting and call of "+contactName, YesNo.No);

						ArrayList<String> result5=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity("call", date, taskSubject, taskDetails, userName);
						if(result5.isEmpty())
						{
							log(LogStatus.INFO, "The records on meeting & calls popup have been verified for "+recordName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records on meeting & calls popup are not verified for "+recordName, YesNo.No);
							sa.assertTrue(false, "The records on meeting & calls popup are not verified for "+recordName);
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+contactName, YesNo.No);
						sa.assertTrue(false, "Not able to click on the count of meeting and call of "+contactName);
					}

					xPath="//a[text()='"+contactName+"']/ancestor::tr//td[@data-label='Meetings and Calls']//button";
					ele=FindElement(driver, xPath, "meeting and call count of "+contactName, action.SCROLLANDBOOLEAN, 20);
					if(click(driver, ele, contactName+" meetings and call count", action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on the count of meeting and call of "+contactName, YesNo.No);

						ArrayList<String> result5=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity("event", date1, taskSubject1, taskDetails1, userName1);
						if(result5.isEmpty())
						{
							log(LogStatus.INFO, "The records on meeting & calls popup have been verified for "+recordName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records on meeting & calls popup are not verified for "+recordName, YesNo.No);
							sa.assertTrue(false, "The records on meeting & calls popup are not verified for "+recordName);
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+contactName, YesNo.No);
						sa.assertTrue(false, "Not able to click on the count of meeting and call of "+contactName);
					}
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab "+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}


	@Parameters({ "projectName" })
	@Test
	public void ATETc011_VerifyAcuityTabOnContactRecordPageAndAlsoVerifyPlusCount(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String xPath;
		WebElement ele;

		String recordName=ATE_Contact1;
		String contactName=ATE_Contact1;


		String[] companiesTaggedName= {ATE_TaggedCompanyName3,ATE_TaggedCompanyName4,ATE_TaggedCompanyName5,ATE_TaggedCompanyName6,ATE_TaggedCompanyName7};
		String[] companiesTaggedTimeReference= {ATE_TaggedCompanyTimeReference3,ATE_TaggedCompanyTimeReference4,ATE_TaggedCompanyTimeReference5,ATE_TaggedCompanyTimeReference6,ATE_TaggedCompanyTimeReference7};

		String[] dealTaggedName= {ATE_TaggedDealName3,ATE_TaggedDealName4,ATE_TaggedDealName5};
		String[] dealTaggedTimeReference= {ATE_TaggedDealTimeReference3,ATE_TaggedDealTimeReference4,ATE_TaggedDealTimeReference5};

		String[] peopleTagName={ATE_TaggedPeopleName2,ATE_TaggedPeopleName3,ATE_TaggedPeopleName4};
		String[] peopleTaggedTimeReference={ATE_TaggedPeopleTimeReference2,ATE_TaggedPeopleTimeReference3,ATE_TaggedPeopleTimeReference4};


		String taskDetails=ATE_ATNotes2;	
		String taskSubject=ATE_ATSubject2;

		String taskDueDate=ATE_AdvanceDueDate2;
		String userName=crmUser6FirstName+" "+crmUser6LastName;
		String userName1=crmUser7FirstName+" "+crmUser7LastName;

		String[] completedate = taskDueDate.split("/");
		char dayMonth = completedate[0].charAt(0);
		String month;
		if (dayMonth == '0') {
			month = completedate[0].replaceAll("0", "");
		} else {
			month = completedate[0];
		}

		String date = month + "/" +completedate[1]  + "/" + completedate[2];

		String taskDetails1=ATE_ATNotes3;	
		String taskSubject1=ATE_ATSubject3;

		String taskDueDate1=ATE_AdvanceStartDate1;


		String[] relatedToData=ATE_ARelatedTo3.split("<break>");

		String[] relatedAssocVal=ATE_ARelatedAsso3.split("<break>");

		String[] relatedAssociation=new String[relatedAssocVal.length+1];
		relatedAssociation[0]=userName1;
		for(int i=1; i<relatedAssociation.length; i++)
		{
			relatedAssociation[i]=relatedAssocVal[i-1];
		}	

		String dealSectionName=ATE_DealName1;
		String dealSectionStage=ATE_DealStage1;
		String dealSectionCompany=ATE_DealCompany1;
		String dealSectionDateRecieved=ATE_DealDateRecieved1;


		String connectionTeamMember=userName;
		String connectionTitle=ATE_ConnectionTitle1;
		String connectionDeal=ATE_ConnectionDeals1;
		String connectionMeetingAndCall=ATE_ConnectionMeetingAndCall1;
		String connectionEmail=ATE_ConnectionEmail1;

		String connectionTeamMember1=userName1;
		String connectionTitle1=ATE_ConnectionTitle2;
		String connectionDeal1=ATE_ConnectionDeals2;
		String connectionMeetingAndCall1=ATE_ConnectionMeetingAndCall2;
		String connectionEmail1=ATE_ConnectionEmail2;

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	


					ArrayList<String> result1=bp.verifyRecordOnInteractionCard(taskDueDate1, IconType.Event,taskSubject1, taskDetails1, false, true, relatedToData,relatedAssociation);
					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The task has been verified on Interaction card. subject name: "+taskSubject1 , YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The task is not verified on Interaction card. subject name: "+taskSubject1 , YesNo.No);
						sa.assertTrue(false,  "The task is not verified on Interaction card. subject name: "+taskSubject1);
					}


					ArrayList<String> result4=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, peopleTagName, peopleTaggedTimeReference, dealTaggedName, dealTaggedTimeReference);
					if(result4.isEmpty())
					{
						log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,  "The record name and Time reference are not verifed", YesNo.No);
						sa.assertTrue(false,  "The record name and Time reference are not verifed");
					}


					ArrayList<String> result3=bp.verifyRecordOnDealsSectionInAcuity(recordName, dealSectionName, dealSectionCompany, dealSectionStage, dealSectionDateRecieved);
					if(result3.isEmpty())
					{
						log(LogStatus.INFO, dealSectionName+" record on Deal section has been verified for "+recordName, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,  dealSectionName+" record on Deal section is not verified for "+recordName, YesNo.No);
						sa.assertTrue(false,  dealSectionName+" record on Deal section is not verified for "+recordName);
					}



					ArrayList<String> result=bp.verifyRecordOnConnectionsSectionInAcuity(contactName, connectionTeamMember, connectionTitle, connectionDeal, connectionMeetingAndCall, connectionEmail);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on Connection section in Acuity for user "+connectionTeamMember, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on Connection section in Acuity for user "+connectionTeamMember, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on Connection section in Acuity for user "+connectionTeamMember);
					}


					ArrayList<String> result2=bp.verifyRecordOnConnectionsSectionInAcuity(contactName, connectionTeamMember1, connectionTitle1, connectionDeal1, connectionMeetingAndCall1, connectionEmail1);
					if(result2.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on Connection section in Acuity for user "+connectionTeamMember1, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on Connection section in Acuity for user "+connectionTeamMember1, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on Connection section in Acuity for user "+connectionTeamMember1);
					}

					xPath="//a[text()='"+userName+"']/ancestor::tr//td[@data-label='Meetings and Calls']//button";
					ele=FindElement(driver, xPath, "meeting and call count of "+contactName, action.SCROLLANDBOOLEAN, 20);
					if(click(driver, ele, contactName+" meetings and call count", action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on the count of meeting and call of "+contactName, YesNo.No);

						ArrayList<String> result5=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity("call", date, taskSubject, taskDetails, userName);
						if(result5.isEmpty())
						{
							log(LogStatus.INFO, "The records on meeting & calls popup have been verified for "+recordName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records on meeting & calls popup are not verified for "+recordName, YesNo.No);
							sa.assertTrue(false, "The records on meeting & calls popup are not verified for "+recordName);
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+contactName, YesNo.No);
						sa.assertTrue(false, "Not able to click on the count of meeting and call of "+contactName);
					}

					xPath="//a[text()='"+userName1+"']/ancestor::tr//td[@data-label='Meetings and Calls']//button";
					ele=FindElement(driver, xPath, "meeting and call count of "+contactName, action.SCROLLANDBOOLEAN, 20);
					if(click(driver, ele, contactName+" meetings and call count", action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on the count of meeting and call of "+contactName, YesNo.No);

						ArrayList<String> result5=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity("event", taskDueDate1, taskSubject1, taskDetails1, userName1);
						if(result5.isEmpty())
						{
							log(LogStatus.INFO, "The records on meeting & calls popup have been verified for "+recordName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records on meeting & calls popup are not verified for "+recordName, YesNo.No);
							sa.assertTrue(false, "The records on meeting & calls popup are not verified for "+recordName);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+contactName, YesNo.No);
						sa.assertTrue(false, "Not able to click on the count of meeting and call of "+contactName);
					}

					if (bp.verifySubjectLinkRedirectionOnIntraction(driver,taskSubject1)) {
						log(LogStatus.INFO, "page successfully redirecting to the "+ taskSubject1+ " page on new tab",	YesNo.No);			
					}
					else
					{
						log(LogStatus.ERROR, "page is not redirecting to the "+ taskSubject1+ " page on new tab",	YesNo.No);		
						sa.assertTrue(false,  "page is not redirecting to the "+ taskSubject1+ " page on new tab");

					}
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab "+tabObj2, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj2);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}


	@Parameters({ "projectName" })
	@Test
	public void ATETc0012_Creating1MoreNewTasks(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String activityType=ATE_ATActivityType4;
		String taskSubject=ATE_ATSubject4;
		String taskRelatedTo=ATE_ATRelatedTo4;
		String taskNotes=ATE_ATNotes4;

		String taskStatus=ATE_AdvanceStatus2;
		String taskPriority=ATE_AdvancePriority3;
		String day=ATE_Day1;

		String taskDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(day));
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate, "Activity Timeline", excelLabel.Variable_Name,
				"ATE_004", excelLabel.Advance_Due_Date);

		String[][] basicsection = { { "Subject", taskSubject }, { "Notes", taskNotes }, { "Related_To", taskRelatedTo } };
		String[][] advanceSection = { { "Due Date Only", taskDueDate }, {"Status", taskStatus}, {"Priority", taskPriority} };

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (bp.createActivityTimeline(projectName, true, activityType, basicsection, advanceSection, null, null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject);
		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject);
		}		
		lp.CRMlogout();	
		sa.assertAll();	
	}


	@Parameters({ "projectName" })
	@Test
	public void ATETc0017_VerifyViewAllLinkOnIntermediaryAccountAndContactPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String[] firmRecord= {ATERecord3,ATERecord4};
		String[] contactRecord= {ATE_Contact1,ATE_Contact2};

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);
		for(int i=0; i<firmRecord.length;i++)
		{
			if (lp.clickOnTab(projectName, tabObj1)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

				if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
						firmRecord[i], 30)) {
					log(LogStatus.INFO, firmRecord[i] + " reocrd has been open", YesNo.No);

					if (bp.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

						if(!bp.verifyViewAllButtonOnIntractionCard(12))
						{
							log(LogStatus.INFO, "View All link is not be Avaliable in the Middle of Interaction Section.", YesNo.No);
							sa.assertTrue(true, "View All link is not be Avaliable in the Middle of Interaction Section.");
						}
						else
						{
							log(LogStatus.ERROR, "View All link is avaliable in the Middle of Interaction Section.", YesNo.No);
							sa.assertTrue(false, "View All link is avaliable in the Middle of Interaction Section.");
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Acuity tab");
					}
				}
				else
				{
					log(LogStatus.ERROR, "Not able to open record "+firmRecord[i], YesNo.No);
					sa.assertTrue(false,  "Not able to open record "+firmRecord[i]);
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to click on tab "+tabObj1, YesNo.No);
				sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
			}					
		}

		for(int i=0; i<contactRecord.length;i++)
		{
			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
						contactRecord[i], 30)) {
					log(LogStatus.INFO, contactRecord[i] + " reocrd has been open", YesNo.No);

					if (bp.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

						if(!bp.verifyViewAllButtonOnIntractionCard(12))
						{
							log(LogStatus.INFO, "View All link is not be Avaliable in the Middle of Interaction Section.", YesNo.No);
							sa.assertTrue(true, "View All link is not be Avaliable in the Middle of Interaction Section.");
						}
						else
						{
							log(LogStatus.ERROR, "View All link is avaliable in the Middle of Interaction Section.", YesNo.No);
							sa.assertTrue(false, "View All link is avaliable in the Middle of Interaction Section.");
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Acuity tab");
					}
				}
				else
				{
					log(LogStatus.ERROR, "Not able to open record "+contactRecord[i], YesNo.No);
					sa.assertTrue(false,  "Not able to open record "+contactRecord[i]);
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to click on tab "+tabObj2, YesNo.No);
				sa.assertTrue(false,  "Not able to click on tab "+tabObj2);
			}				
		}	
		lp.CRMlogout();	
		sa.assertAll();	
	}


	@Parameters({ "projectName" })
	@Test
	public void ATETc0018_CreatingOneMoreTaskAndVerifyViewAllLinkOnIntermediaryAccountAndContactPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String firmRecord1=ATERecord3;
		String firmRecord2=ATERecord4;

		String contactRecord1=ATE_Contact1;
		String contactRecord2=ATE_Contact2;


		String activityType=ATE_ATActivityType5;
		String taskSubject=ATE_ATSubject5;
		String taskRelatedTo=ATE_ATRelatedTo5;
		String taskNotes=ATE_ATNotes5;

		String taskStatus=ATE_AdvanceStatus3;
		String taskPriority=ATE_AdvancePriority4;
		String day=ATE_Day2;

		String taskDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(day));
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate, "Activity Timeline", excelLabel.Variable_Name,
				"ATE_005", excelLabel.Advance_Due_Date);

		String[][] basicsection = { { "Subject", taskSubject }, { "Notes", taskNotes }, { "Related_To", taskRelatedTo } };
		String[][] advanceSection = { { "Due Date Only", taskDueDate }, {"Status", taskStatus}, {"Priority", taskPriority} };

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (bp.createActivityTimeline(projectName, true, activityType, basicsection, advanceSection, null, null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject);


			if (lp.clickOnTab(projectName, tabObj1)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

				if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
						firmRecord1, 30)) {
					log(LogStatus.INFO, firmRecord1 + " reocrd has been open", YesNo.No);

					if (bp.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

						if(bp.verifyViewAllButtonOnIntractionCard(12))
						{
							log(LogStatus.INFO, "View All link is avaliable in the Middle of Interaction Section.", YesNo.No);
							sa.assertTrue(true, "View All link is avaliable in the Middle of Interaction Section.");
						}
						else
						{
							log(LogStatus.ERROR, "View All link is not avaliable in the Middle of Interaction Section.", YesNo.No);
							sa.assertTrue(false, "View All link is not avaliable in the Middle of Interaction Section.");
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Acuity tab");
					}
				}
				else
				{
					log(LogStatus.ERROR, "Not able to open record "+firmRecord1, YesNo.No);
					sa.assertTrue(false,  "Not able to open record "+firmRecord1);
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to click on tab "+tabObj1, YesNo.No);
				sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
			}		


			if (lp.clickOnTab(projectName, tabObj1)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

				if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
						firmRecord2, 30)) {
					log(LogStatus.INFO, firmRecord2 + " reocrd has been open", YesNo.No);

					if (bp.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

						if(!bp.verifyViewAllButtonOnIntractionCard(12))
						{
							log(LogStatus.INFO, "View All link is not avaliable in the Middle of Interaction Section.", YesNo.No);
							sa.assertTrue(true, "View All link is not avaliable in the Middle of Interaction Section.");
						}
						else
						{
							log(LogStatus.ERROR, "View All link is avaliable in the Middle of Interaction Section.", YesNo.No);
							sa.assertTrue(false, "View All link is avaliable in the Middle of Interaction Section.");
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Acuity tab");
					}
				}
				else
				{
					log(LogStatus.ERROR, "Not able to open record "+firmRecord2, YesNo.No);
					sa.assertTrue(false,  "Not able to open record "+firmRecord2);
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to click on tab "+tabObj1, YesNo.No);
				sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
			}	


			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
						contactRecord1, 30)) {
					log(LogStatus.INFO, contactRecord1 + " reocrd has been open", YesNo.No);

					if (bp.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

						if(bp.verifyViewAllButtonOnIntractionCard(12))
						{
							log(LogStatus.INFO, "View All link is avaliable in the Middle of Interaction Section.", YesNo.No);
							sa.assertTrue(true, "View All link is avaliable in the Middle of Interaction Section.");
						}
						else
						{
							log(LogStatus.ERROR, "View All link is not avaliable in the Middle of Interaction Section.", YesNo.No);
							sa.assertTrue(false, "View All link is not avaliable in the Middle of Interaction Section.");
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Acuity tab");
					}
				}
				else
				{
					log(LogStatus.ERROR, "Not able to open record "+contactRecord1, YesNo.No);
					sa.assertTrue(false,  "Not able to open record "+contactRecord1);
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to click on tab "+tabObj2, YesNo.No);
				sa.assertTrue(false,  "Not able to click on tab "+tabObj2);
			}		

			if (lp.clickOnTab(projectName, tabObj2)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

				if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
						contactRecord2, 30)) {
					log(LogStatus.INFO, contactRecord2+ " reocrd has been open", YesNo.No);

					if (bp.clicktabOnPage("Acuity")) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

						if(!bp.verifyViewAllButtonOnIntractionCard(12))
						{
							log(LogStatus.INFO, "View All link is not avaliable in the Middle of Interaction Section.", YesNo.No);
							sa.assertTrue(true, "View All link is not avaliable in the Middle of Interaction Section.");
						}
						else
						{
							log(LogStatus.ERROR, "View All link is avaliable in the Middle of Interaction Section.", YesNo.No);
							sa.assertTrue(false, "View All link is avaliable in the Middle of Interaction Section.");
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Acuity tab");
					}
				}
				else
				{
					log(LogStatus.ERROR, "Not able to open record "+contactRecord2, YesNo.No);
					sa.assertTrue(false,  "Not able to open record "+contactRecord2);
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to click on tab "+tabObj2, YesNo.No);
				sa.assertTrue(false,  "Not able to click on tab "+tabObj2);
			}

		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject);
		}		
		lp.CRMlogout();	
		sa.assertAll();	

	}


	@Parameters({ "projectName" })
	@Test
	public void ATETc0019_VerifyViewAllLinkOnInteractionSectionOfIntermediaryAccountPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATERecord3;
		String xPath;
		WebElement ele;

		String[] completedate = ATE_AdvanceDueDate1.split("/");
		char dayMonth = completedate[0].charAt(0);
		String month;
		if (dayMonth == '0') {
			month = completedate[0].replaceAll("0", "");
		} else {
			month = completedate[0];
		}
		String date = month + "/" +completedate[1]  + "/" + completedate[2];


		String[] completedate1 = ATE_AdvanceDueDate2.split("/");
		char dayMonth1 = completedate1[0].charAt(0);
		String month1;
		if (dayMonth1 == '0') {
			month1 = completedate1[0].replaceAll("0", "");
		} else {
			month1 = completedate1[0];
		}
		String date1 = month1 + "/" +completedate1[1]  + "/" + completedate1[2];


		String[] completedate2 = ATE_AdvanceStartDate1.split("/");
		char dayMonth2 = completedate2[0].charAt(0);
		String month2;
		if (dayMonth2 == '0') {
			month2 = completedate2[0].replaceAll("0", "");
		} else {
			month2 = completedate2[0];
		}

		String date2 = month2 + "/" +completedate2[1]  + "/" + completedate2[2];


		String[] completedate3 = ATE_AdvanceDueDate3.split("/");
		char dayMonth3 = completedate3[0].charAt(0);
		String month3;
		if (dayMonth3 == '0') {
			month3 = completedate3[0].replaceAll("0", "");
		} else {
			month3 = completedate3[0];
		}
		String date3 = month3 + "/" +completedate3[1]  + "/" + completedate3[2];


		String[] completedate4 = ATE_AdvanceDueDate4.split("/");
		char dayMonth4 = completedate4[0].charAt(0);
		String month4;
		if (dayMonth4 == '0') {
			month4 = completedate4[0].replaceAll("0", "");
		} else {
			month4 = completedate4[0];
		}
		String date4 = month4 + "/" +completedate4[1]  + "/" + completedate4[2];

		IconType[] icon= {IconType.Task,IconType.Call,IconType.Event,IconType.Task,IconType.Task};
		String[] subjectName= {ATE_ATSubject1,ATE_ATSubject2,ATE_ATSubject3,ATE_ATSubject4,ATE_ATSubject5};
		String[] details= {ATE_ATNotes1,ATE_ATNotes2,ATE_ATNotes3,ATE_ATNotes4,ATE_ATNotes5};
		String[] dueDate= {date,date1,date2,date3,date4};
		String user1=crmUser6FirstName+" "+crmUser6LastName;
		String user2=crmUser7FirstName+" "+crmUser7LastName;
		String[] users= {user1,user1,user2,user1,user1};

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

					if(click(driver, bp.getViewAllBtnOnIntration(20), "View All button on Interaction", action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "Clicked on View all button on Interaction card", YesNo.No);

						ArrayList<String> result=bp.verifyRecordsonInteractionsViewAllPopup(icon, dueDate, subjectName, details, users, subjectName);
						if(result.isEmpty())
						{
							log(LogStatus.INFO, "All records on View All popup of Interaction card have been verified", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "All records on View All popup of Interaction card are not verified", YesNo.No);
							sa.assertTrue(false,  "All records on View All popup of Interaction card are not verified");
						}

						for(int i=0; i<subjectName.length; i++)
						{
							ThreadSleep(2000);
							xPath="//h2[contains(text(),'All Interactions with')]/../following-sibling::div//td[@data-label='Subject']//a[text()='"+subjectName[i]+"']";
							ele=FindElement(driver, xPath, subjectName+" subject", action.SCROLLANDBOOLEAN, 20);
							if(CommonLib.clickUsingJavaScript(driver, ele, subjectName[i]+" subject", action.SCROLLANDBOOLEAN))
							{
								log(LogStatus.INFO, "Clicked on subject name : "+subjectName[i], YesNo.No);
								String id=switchOnWindow(driver);
								xPath="//span[text()=\""+subjectName[i]+"\" and contains(@class,\"uiOutputText\")]";
								ele=FindElement(driver, xPath, subjectName[i]+" record", action.SCROLLANDBOOLEAN, 40);
								if(ele!=null)
								{
									log(LogStatus.INFO, subjectName[i]+" record is redirecting to new tab",YesNo.No);
								}
								else
								{
									log(LogStatus.ERROR, subjectName[i]+" is not redirecting to new tab",YesNo.No);
									sa.assertTrue(false, subjectName[i]+" is not redirecting to new tab");
								}
								driver.close();
								driver.switchTo().window(id);	
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on subject name : "+subjectName[i], YesNo.No);
								sa.assertTrue(false, "Not able to click on subject name : "+subjectName[i]);
							}	
						}					
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on View all button on Interaction card", YesNo.No);
						sa.assertTrue(false,  "Not able to click on View all button on Interaction card");
					}			
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab "+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	

	}

	@Parameters({ "projectName" })
	@Test
	public void ATETc0020_VerifyViewAllLinkOnInteractionSectionOnContactPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATE_Contact1;
		String xPath;
		WebElement ele;

		String[] completedate = ATE_AdvanceDueDate1.split("/");
		char dayMonth = completedate[0].charAt(0);
		String month;
		if (dayMonth == '0') {
			month = completedate[0].replaceAll("0", "");
		} else {
			month = completedate[0];
		}
		String date = month + "/" +completedate[1]  + "/" + completedate[2];


		String[] completedate1 = ATE_AdvanceDueDate2.split("/");
		char dayMonth1 = completedate1[0].charAt(0);
		String month1;
		if (dayMonth1 == '0') {
			month1 = completedate1[0].replaceAll("0", "");
		} else {
			month1 = completedate1[0];
		}
		String date1 = month1 + "/" +completedate1[1]  + "/" + completedate1[2];


		String[] completedate2 = ATE_AdvanceStartDate1.split("/");
		char dayMonth2 = completedate2[0].charAt(0);
		String month2;
		if (dayMonth2 == '0') {
			month2 = completedate2[0].replaceAll("0", "");
		} else {
			month2 = completedate2[0];
		}

		String date2 = month2 + "/" +completedate2[1]  + "/" + completedate2[2];


		String[] completedate3 = ATE_AdvanceDueDate3.split("/");
		char dayMonth3 = completedate3[0].charAt(0);
		String month3;
		if (dayMonth3 == '0') {
			month3 = completedate3[0].replaceAll("0", "");
		} else {
			month3 = completedate3[0];
		}
		String date3 = month3 + "/" +completedate3[1]  + "/" + completedate3[2];


		String[] completedate4 = ATE_AdvanceDueDate4.split("/");
		char dayMonth4 = completedate4[0].charAt(0);
		String month4;
		if (dayMonth4 == '0') {
			month4 = completedate4[0].replaceAll("0", "");
		} else {
			month4 = completedate4[0];
		}
		String date4 = month4 + "/" +completedate4[1]  + "/" + completedate4[2];

		IconType[] icon= {IconType.Task,IconType.Call,IconType.Event,IconType.Task,IconType.Task};
		String[] subjectName= {ATE_ATSubject1,ATE_ATSubject2,ATE_ATSubject3,ATE_ATSubject4,ATE_ATSubject5};
		String[] details= {ATE_ATNotes1,ATE_ATNotes2,ATE_ATNotes3,ATE_ATNotes4,ATE_ATNotes5};
		String[] dueDate= {date,date1,date2,date3,date4};
		String user1=crmUser6FirstName+" "+crmUser6LastName;
		String user2=crmUser7FirstName+" "+crmUser7LastName;
		String[] users= {user1,user1,user2,user1,user1};

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

					if(click(driver, bp.getViewAllBtnOnIntration(20), "View All button on Interaction", action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "Clicked on View all button on Interaction card", YesNo.No);

						ArrayList<String> result=bp.verifyRecordsonInteractionsViewAllPopup(icon, dueDate, subjectName, details, users, subjectName);
						if(result.isEmpty())
						{
							log(LogStatus.INFO, "All records on View All popup of Interaction card have been verified", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "All records on View All popup of Interaction card are not verified", YesNo.No);
							sa.assertTrue(false,  "All records on View All popup of Interaction card are not verified");
						}


						for(int i=0; i<subjectName.length; i++)
						{
							ThreadSleep(2000);
							xPath="//h2[contains(text(),'All Interactions with')]/../following-sibling::div//td[@data-label='Subject']//a[text()='"+subjectName[i]+"']";
							ele=FindElement(driver, xPath, subjectName+" subject", action.SCROLLANDBOOLEAN, 20);
							if(CommonLib.clickUsingJavaScript(driver, ele, subjectName[i]+" subject", action.SCROLLANDBOOLEAN))
							{
								log(LogStatus.INFO, "Clicked on subject name : "+subjectName[i], YesNo.No);
								String id=switchOnWindow(driver);
								xPath="//span[text()=\""+subjectName[i]+"\" and contains(@class,\"uiOutputText\")]";
								ele=FindElement(driver, xPath, subjectName[i]+" record", action.SCROLLANDBOOLEAN, 40);
								if(ele!=null)
								{
									log(LogStatus.INFO, subjectName[i]+" record is redirecting to new tab",YesNo.No);
								}
								else
								{
									log(LogStatus.ERROR, subjectName[i]+" is not redirecting to new tab",YesNo.No);
									sa.assertTrue(false, subjectName[i]+" is not redirecting to new tab");
								}
								driver.close();
								driver.switchTo().window(id);	
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on subject name : "+subjectName[i], YesNo.No);
								sa.assertTrue(false, "Not able to click on subject name : "+subjectName[i]);
							}	
						}			

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on View all button on Interaction card", YesNo.No);
						sa.assertTrue(false,  "Not able to click on View all button on Interaction card");
					}			
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else

		{
			log(LogStatus.ERROR, "Not able to click on tab "+tabObj2, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj2);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}

	@Parameters({ "projectName" })
	@Test
	public void ATETc0021_VerifyHyperlinkAndCountFunctionalityVerificationOnAccountPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATERecord3;
		String xPath;
		WebElement ele;

		String[] completedate = ATE_AdvanceDueDate1.split("/");
		char dayMonth = completedate[0].charAt(0);
		String month;
		if (dayMonth == '0') {
			month = completedate[0].replaceAll("0", "");
		} else {
			month = completedate[0];
		}
		String date = month + "/" +completedate[1]  + "/" + completedate[2];


		String[] completedate1 = ATE_AdvanceDueDate2.split("/");
		char dayMonth1 = completedate1[0].charAt(0);
		String month1;
		if (dayMonth1 == '0') {
			month1 = completedate1[0].replaceAll("0", "");
		} else {
			month1 = completedate1[0];
		}
		String date1 = month1 + "/" +completedate1[1]  + "/" + completedate1[2];


		String[] completedate2 = ATE_AdvanceStartDate1.split("/");
		char dayMonth2 = completedate2[0].charAt(0);
		String month2;
		if (dayMonth2 == '0') {
			month2 = completedate2[0].replaceAll("0", "");
		} else {
			month2 = completedate2[0];
		}

		String date2 = month2 + "/" +completedate2[1]  + "/" + completedate2[2];


		String[] completedate3 = ATE_AdvanceDueDate3.split("/");
		char dayMonth3 = completedate3[0].charAt(0);
		String month3;
		if (dayMonth3 == '0') {
			month3 = completedate3[0].replaceAll("0", "");
		} else {
			month3 = completedate3[0];
		}
		String date3 = month3 + "/" +completedate3[1]  + "/" + completedate3[2];


		String[] completedate4 = ATE_AdvanceDueDate4.split("/");
		char dayMonth4 = completedate4[0].charAt(0);
		String month4;
		if (dayMonth4 == '0') {
			month4 = completedate4[0].replaceAll("0", "");
		} else {
			month4 = completedate4[0];
		}
		String date4 = month4 + "/" +completedate4[1]  + "/" + completedate4[2];

		String user1=crmUser6FirstName+" "+crmUser6LastName;
		String user2=crmUser7FirstName+" "+crmUser7LastName;

		String dealName=ATE_DealName1;

		String companyTagName=ATE_TaggedCompanyName3;
		String companyTagTimeReferenceCount=ATE_TaggedCompanyTimeReference3;

		String peopleTagName=ATE_TaggedPeopleName5;
		String peopleTagTimeReferenceCount=ATE_TaggedPeopleTimeReference5;

		String dealTagName=ATE_TaggedDealName3;
		String dealTagTimeReferenceCount=ATE_TaggedDealTimeReference3;

		IconType[] companyIcon= {IconType.Task,IconType.Event};
		String[] companySubjectName= {ATE_ATSubject1,ATE_ATSubject3};
		String[] companyDetails= {ATE_ATNotes1,ATE_ATNotes3};
		String[] companyDueDate= {date,date2};
		String[] companyUsers= {user1,user2};


		IconType[] companyIcon1= {IconType.Call,IconType.Task,IconType.Task};
		String[] companySubjectName1= {ATE_ATSubject2,ATE_ATSubject4,ATE_ATSubject5};
		String[] companyDetails1= {ATE_ATNotes2,ATE_ATNotes4,ATE_ATNotes5};
		String[] companyDueDate1= {date1,date3,date4};
		String[] companyUsers1= {user1,user1,user1};

		IconType[] companyIcon2= {IconType.Task};
		String[] companySubjectName2= {ATE_ATSubject1};
		String[] companyDetails2= {ATE_ATNotes1};
		String[] companyDueDate2= {date};
		String[] companyUsers2= {user1};

		String meetingAndCallIcon=ATE_ATActivityType3;
		String meetingAndCallDate=date2;
		String meetingAndCallSubject=ATE_ATSubject3;
		String meetingAndCallDetails=ATE_ATNotes3;
		String meetingAndCallAssignedTo=user2;


		String meetingAndCallIcon1=ATE_ATActivityType2;
		String meetingAndCallDate1=date1;
		String meetingAndCallSubject1=ATE_ATSubject2;
		String meetingAndCallDetails1=ATE_ATNotes2;
		String meetingAndCallAssignedTo1=user1;


		String[] relatedAssociation= {ATE_ARelatedTo4};
		String[] relatedAssociationOnTagged= ATE_ARelatedAsso2.split("<break>");
		String subjectNameInteraction=ATE_ATSubject2;

		String contactRecord=ATE_ARelatedTo4;

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	
					ThreadSleep(10000);
					xPath="//th[@data-label='Deal Name']//a[text()='"+dealName+"']";
					ele=FindElement(driver, xPath, "Deal Name", action.SCROLLANDBOOLEAN, 20);
					if(CommonLib.clickUsingJavaScript(driver, ele, "deal name", action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on deal name :"+dealName, YesNo.No);
						String id=switchOnWindow(driver);
						xPath="//lightning-formatted-text[text()='"+dealName+"']";
						ele=FindElement(driver, xPath, dealName+" record", action.SCROLLANDBOOLEAN, 40);
						if(ele!=null)
						{
							log(LogStatus.INFO, dealName+" record is redirecting to new tab",YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, dealName+" is not redirecting to new tab",YesNo.No);
							sa.assertTrue(false, dealName+" is not redirecting to new tab");

						}
						driver.close();
						driver.switchTo().window(id);	
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on deal name :"+dealName, YesNo.No);
						sa.assertTrue(false,  "Not able to click on deal name :"+dealName);
					}
					ThreadSleep(2000);
					if (click(driver, bp.getTaggedRecordName("Companies", 30), "Companies tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Companies tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("Companies", companyTagName, companyTagTimeReferenceCount,30), companyTagName+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+companyTagName,YesNo.No);

							ArrayList<String> result=bp.verifyRecordsonInteractionsViewAllPopup(companyIcon, companyDueDate, companySubjectName, companyDetails, companyUsers, companySubjectName);
							if(result.isEmpty())
							{
								log(LogStatus.INFO, "All records on Interaction card have been verified for "+companyTagName+" record", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "All records on Interaction card are not verified for "+companyTagName+" record " +result, YesNo.No);
								sa.assertTrue(false,  "All records on Interaction card are not verified for "+companyTagName+" record "+result);
							}
							xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
							ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
							if(clickUsingJavaScript(driver, ele, "close button"))
							{
								log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
								sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+companyTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+companyTagName);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Companies tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Companies tab name");
					}

					ThreadSleep(2000);
					if (click(driver, bp.getTaggedRecordName("People", 30), "People tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on People tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("People", peopleTagName, peopleTagTimeReferenceCount,30), peopleTagName+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+peopleTagName,YesNo.No);

							ArrayList<String> result1=bp.verifyRecordsonInteractionsViewAllPopup(companyIcon1, companyDueDate1, companySubjectName1, companyDetails1, companyUsers1, companySubjectName1);
							if(result1.isEmpty())
							{
								log(LogStatus.INFO, "All records on Interaction card have been verified for "+peopleTagName+" record ", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "All records on Interaction card are not verified for "+peopleTagName+" record "+result1, YesNo.No);
								sa.assertTrue(false,  "All records on Interaction card are not verified for "+peopleTagName+" record "+result1);
							}
							xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
							ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
							if(clickUsingJavaScript(driver, ele, "close button"))
							{
								log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
								sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+peopleTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+peopleTagName);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on people tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on people tab name");
					}

					ThreadSleep(2000);
					if (click(driver, bp.getTaggedRecordName("Deals", 30), "Deals tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Deals tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("Deals", dealTagName, dealTagTimeReferenceCount,30), dealTagName+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+dealTagName,YesNo.No);

							ArrayList<String> result2=bp.verifyRecordsonInteractionsViewAllPopup(companyIcon2, companyDueDate2, companySubjectName2, companyDetails2, companyUsers2, companySubjectName2);
							if(result2.isEmpty())
							{
								log(LogStatus.INFO, "All records on Interaction card have been verified for "+dealTagName+" record", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "All records on Interaction card are not verified for "+dealTagName+" record "+result2, YesNo.No);
								sa.assertTrue(false,  "All records on Interaction card are not verified for "+dealTagName+" record "+result2);
							}
							xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
							ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
							if(clickUsingJavaScript(driver, ele, "close button"))
							{
								log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
								sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+dealTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+dealTagName);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Deals tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Deals tab name");
					}
					ThreadSleep(2000);
					ArrayList<String> result3=bp.verifyRedirectionOnClickOfTaggedRecord(subjectNameInteraction, relatedAssociation, relatedAssociationOnTagged);
					if(result3.isEmpty())
					{
						log(LogStatus.INFO, "The Link is redirecting to new tab on click of Tagged record",YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The Link is not redirecting to new tab on click of Tagged record "+result3,YesNo.No);
						sa.assertTrue(false,  "The Link is not redirecting to new tab on click of Tagged record "+result3);
					}
					ThreadSleep(2000);
					xPath="//a[text()='"+contactRecord+"']/ancestor::tr//td[@data-label='Meetings and Calls']//button";
					ele=FindElement(driver, xPath, "Count of "+contactRecord+" on contact section", action.SCROLLANDBOOLEAN, 20);
					if(CommonLib.clickUsingJavaScript(driver, ele,"Count of "+contactRecord+" on contact section" , action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on count of "+contactRecord,YesNo.No);
						ArrayList<String> result4=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity(meetingAndCallIcon, meetingAndCallDate, meetingAndCallSubject, meetingAndCallDetails, meetingAndCallAssignedTo);
						if(result4.isEmpty())
						{
							log(LogStatus.INFO, meetingAndCallSubject+" record has been verifid on meeting and call popup",YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, meetingAndCallSubject+" record is not verifid on meeting and call popup" +result4,YesNo.No);
							sa.assertTrue(false, meetingAndCallSubject+" record is not verifid on meeting and call popup" +result4);

						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on count of "+contactRecord,YesNo.No);
						sa.assertTrue(false,  "Not able to click on count of "+contactRecord);
					}
					ThreadSleep(2000);

					xPath="//a[text()='"+contactRecord+"']/ancestor::tr//td[@data-label='Meetings and Calls']//button";
					ele=FindElement(driver, xPath, "Count of "+contactRecord+" on contact section", action.SCROLLANDBOOLEAN, 20);
					if(CommonLib.clickUsingJavaScript(driver, ele,"Count of "+contactRecord+" on contact section" , action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on count of "+contactRecord,YesNo.No);
						ArrayList<String> result5=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity(meetingAndCallIcon1, meetingAndCallDate1, meetingAndCallSubject1, meetingAndCallDetails1, meetingAndCallAssignedTo1);
						if(result5.isEmpty())
						{
							log(LogStatus.INFO, meetingAndCallSubject+" record has been verifid on meeting and call popup",YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, meetingAndCallSubject+" record is not verifid on meeting and call popup "+result5,YesNo.No);
							sa.assertTrue(false, meetingAndCallSubject+" record is not verifid on meeting and call popup "+result5);
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on count of "+contactRecord,YesNo.No);
						sa.assertTrue(false,  "Not able to click on count of "+contactRecord);
					}

				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab "+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}


	@Parameters({ "projectName" })
	@Test
	public void ATETc0022_VerifyHyperlinkAndCountVerificationFunctionalityOnContactPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATE_Contact1;
		String xPath;
		WebElement ele;

		String[] completedate = ATE_AdvanceDueDate1.split("/");
		char dayMonth = completedate[0].charAt(0);
		String month;
		if (dayMonth == '0') {
			month = completedate[0].replaceAll("0", "");
		} else {
			month = completedate[0];
		}
		String date = month + "/" +completedate[1]  + "/" + completedate[2];


		String[] completedate1 = ATE_AdvanceDueDate2.split("/");
		char dayMonth1 = completedate1[0].charAt(0);
		String month1;
		if (dayMonth1 == '0') {
			month1 = completedate1[0].replaceAll("0", "");
		} else {
			month1 = completedate1[0];
		}
		String date1 = month1 + "/" +completedate1[1]  + "/" + completedate1[2];


		String[] completedate2 = ATE_AdvanceStartDate1.split("/");
		char dayMonth2 = completedate2[0].charAt(0);
		String month2;
		if (dayMonth2 == '0') {
			month2 = completedate2[0].replaceAll("0", "");
		} else {
			month2 = completedate2[0];
		}

		String date2 = month2 + "/" +completedate2[1]  + "/" + completedate2[2];


		String[] completedate3 = ATE_AdvanceDueDate3.split("/");
		char dayMonth3 = completedate3[0].charAt(0);
		String month3;
		if (dayMonth3 == '0') {
			month3 = completedate3[0].replaceAll("0", "");
		} else {
			month3 = completedate3[0];
		}
		String date3 = month3 + "/" +completedate3[1]  + "/" + completedate3[2];


		String[] completedate4 = ATE_AdvanceDueDate4.split("/");
		char dayMonth4 = completedate4[0].charAt(0);
		String month4;
		if (dayMonth4 == '0') {
			month4 = completedate4[0].replaceAll("0", "");
		} else {
			month4 = completedate4[0];
		}
		String date4 = month4 + "/" +completedate4[1]  + "/" + completedate4[2];

		String user1=crmUser6FirstName+" "+crmUser6LastName;
		String user2=crmUser7FirstName+" "+crmUser7LastName;

		String dealName=ATE_DealName1;

		String companyTagName=ATE_TaggedCompanyName3;
		String companyTagTimeReferenceCount=ATE_TaggedCompanyTimeReference3;

		String peopleTagName=ATE_TaggedPeopleName5;
		String peopleTagTimeReferenceCount=ATE_TaggedPeopleTimeReference5;

		String dealTagName=ATE_TaggedDealName3;
		String dealTagTimeReferenceCount=ATE_TaggedDealTimeReference3;

		IconType[] companyIcon= {IconType.Task,IconType.Event};
		String[] companySubjectName= {ATE_ATSubject1,ATE_ATSubject3};
		String[] companyDetails= {ATE_ATNotes1,ATE_ATNotes3};
		String[] companyDueDate= {date,date2};
		String[] companyUsers= {user1,user2};


		IconType[] companyIcon1= {IconType.Call,IconType.Task,IconType.Task};
		String[] companySubjectName1= {ATE_ATSubject2,ATE_ATSubject4,ATE_ATSubject5};
		String[] companyDetails1= {ATE_ATNotes2,ATE_ATNotes4,ATE_ATNotes5};
		String[] companyDueDate1= {date1,date3,date4};
		String[] companyUsers1= {user1,user1,user1};

		IconType[] companyIcon2= {IconType.Task};
		String[] companySubjectName2= {ATE_ATSubject1};
		String[] companyDetails2= {ATE_ATNotes1};
		String[] companyDueDate2= {date};
		String[] companyUsers2= {user1};




		String meetingAndCallIcon=ATE_ATActivityType2;
		String meetingAndCallDate=date1;
		String meetingAndCallSubject=ATE_ATSubject2;
		String meetingAndCallDetails=ATE_ATNotes2;
		String meetingAndCallAssignedTo=user1;


		String[] relatedAssociation= {ATE_ARelatedTo4};
		String[] relatedAssociationOnTagged= ATE_ARelatedAsso2.split("<break>");
		String subjectNameInteraction=ATE_ATSubject2;

		String contactRecord=ATE_ARelatedTo4;

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	
					ThreadSleep(10000);
					xPath="//th[@data-label='Deal Name']//a[text()='"+dealName+"']";
					ele=FindElement(driver, xPath, "Deal Name", action.SCROLLANDBOOLEAN, 20);
					if(CommonLib.clickUsingJavaScript(driver, ele, "deal name", action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on deal name :"+dealName, YesNo.No);
						String id=switchOnWindow(driver);
						xPath="//lightning-formatted-text[text()='"+dealName+"']";
						ele=FindElement(driver, xPath, dealName+" record", action.SCROLLANDBOOLEAN, 40);
						if(ele!=null)
						{
							log(LogStatus.INFO, dealName+" record is redirecting to new tab",YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, dealName+" is not redirecting to new tab",YesNo.No);
							sa.assertTrue(false, dealName+" is not redirecting to new tab");

						}
						driver.close();
						driver.switchTo().window(id);	
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on deal name :"+dealName, YesNo.No);
						sa.assertTrue(false,  "Not able to click on deal name :"+dealName);
					}
					ThreadSleep(2000);
					if (click(driver, bp.getTaggedRecordName("Companies", 30), "Companies tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Companies tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("Companies", companyTagName, companyTagTimeReferenceCount,30), companyTagName+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+companyTagName,YesNo.No);

							ArrayList<String> result=bp.verifyRecordsonInteractionsViewAllPopup(companyIcon, companyDueDate, companySubjectName, companyDetails, companyUsers, companySubjectName);
							if(result.isEmpty())
							{
								log(LogStatus.INFO, "All records on Interaction card have been verified for "+companyTagName+" record", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "All records on Interaction card are not verified for "+companyTagName+" record " +result, YesNo.No);
								sa.assertTrue(false,  "All records on Interaction card are not verified for "+companyTagName+" record "+result);
							}
							xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
							ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
							if(clickUsingJavaScript(driver, ele, "close button"))
							{
								log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
								sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+companyTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+companyTagName);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Companies tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Companies tab name");
					}

					ThreadSleep(2000);
					if (click(driver, bp.getTaggedRecordName("People", 30), "People tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on People tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("People", peopleTagName, peopleTagTimeReferenceCount,30), peopleTagName+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+peopleTagName,YesNo.No);

							ArrayList<String> result1=bp.verifyRecordsonInteractionsViewAllPopup(companyIcon1, companyDueDate1, companySubjectName1, companyDetails1, companyUsers1, companySubjectName1);
							if(result1.isEmpty())
							{
								log(LogStatus.INFO, "All records on Interaction card have been verified for "+peopleTagName+" record ", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "All records on Interaction card are not verified for "+peopleTagName+" record "+result1, YesNo.No);
								sa.assertTrue(false,  "All records on Interaction card are not verified for "+peopleTagName+" record "+result1);
							}
							xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
							ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
							if(clickUsingJavaScript(driver, ele, "close button"))
							{
								log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
								sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+peopleTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+peopleTagName);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on people tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on people tab name");
					}

					ThreadSleep(2000);
					if (click(driver, bp.getTaggedRecordName("Deals", 30), "Deals tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Deals tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("Deals", dealTagName, dealTagTimeReferenceCount,30), dealTagName+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+dealTagName,YesNo.No);

							ArrayList<String> result2=bp.verifyRecordsonInteractionsViewAllPopup(companyIcon2, companyDueDate2, companySubjectName2, companyDetails2, companyUsers2, companySubjectName2);
							if(result2.isEmpty())
							{
								log(LogStatus.INFO, "All records on Interaction card have been verified for "+dealTagName+" record", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "All records on Interaction card are not verified for "+dealTagName+" record "+result2, YesNo.No);
								sa.assertTrue(false,  "All records on Interaction card are not verified for "+dealTagName+" record "+result2);
							}
							xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
							ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
							if(clickUsingJavaScript(driver, ele, "close button"))
							{
								log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
								sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+dealTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+dealTagName);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Deals tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Deals tab name");
					}
					ThreadSleep(2000);
					ArrayList<String> result3=bp.verifyRedirectionOnClickOfTaggedRecord(subjectNameInteraction, relatedAssociation, relatedAssociationOnTagged);
					if(result3.isEmpty())
					{
						log(LogStatus.INFO, "The Link is redirecting to new tab on click of Tagged record",YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The Link is not redirecting to new tab on click of Tagged record "+result3,YesNo.No);
						sa.assertTrue(false,  "The Link is not redirecting to new tab on click of Tagged record "+result3);
					}
					ThreadSleep(2000);
					xPath="//a[text()='"+user1+"']/ancestor::tr//td[@data-label='Meetings and Calls']//button";
					ele=FindElement(driver, xPath, "Count of "+contactRecord+" on contact section", action.SCROLLANDBOOLEAN, 20);
					if(CommonLib.clickUsingJavaScript(driver, ele,"Count of "+contactRecord+" on contact section" , action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on count of "+contactRecord,YesNo.No);
						ArrayList<String> result4=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity(meetingAndCallIcon, meetingAndCallDate, meetingAndCallSubject, meetingAndCallDetails, meetingAndCallAssignedTo);
						if(result4.isEmpty())
						{
							log(LogStatus.INFO, meetingAndCallSubject+" record has been verifid on meeting and call popup",YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, meetingAndCallSubject+" record is not verifid on meeting and call popup" +result4,YesNo.No);
							sa.assertTrue(false, meetingAndCallSubject+" record is not verifid on meeting and call popup" +result4);
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on count of "+contactRecord,YesNo.No);
						sa.assertTrue(false,  "Not able to click on count of "+contactRecord);
					}			
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab "+tabObj2, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj2);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}



	@Parameters({ "projectName" })
	@Test
	public void ATETc0023_VerifyTheCountsForUsersOnContactPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATE_Contact1;
		String xPath;
		WebElement ele;
		String user1=crmUser6FirstName+" "+crmUser6LastName;

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	
					ThreadSleep(10000);
					xPath="//th[@data-label='Team Member']//a[text()='"+user1+"']";
					ele=FindElement(driver, xPath, "User 1 on Connection section", action.SCROLLANDBOOLEAN, 20);
					if(clickUsingJavaScript(driver, ele, "User 1 on Connection section"))
					{
						log(LogStatus.INFO, "clicked on User 1 on Connection section", YesNo.No);
						String id=switchOnWindow(driver);
						xPath="//span[@class='uiOutputText' and text()='"+user1+"']";
						ele=FindElement(driver, xPath, "user 1", action.SCROLLANDBOOLEAN, 20);
						if(ele!=null)
						{
							log(LogStatus.INFO, "User 1 default page is opening in org.", YesNo.No);
							sa.assertTrue(true, "User 1 default page is opening in org.");
						}
						else
						{
							log(LogStatus.ERROR, "User 1 default page is not opening in org.", YesNo.No);
							sa.assertTrue(false, "User 1 default page is not opening in org.");
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on User 1 on Connection section", YesNo.No);
						sa.assertTrue(false, "Not able to click on User 1 on Connection section");
					}		
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab "+tabObj2, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj2);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}



	@Parameters({ "projectName" })
	@Test
	public void ATETc0024_LoginWithPEUser2AndVerifyIntermediaryAccountRecordPageAcuityTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATERecord3;
		String xPath;
		WebElement ele;


		String taskDetails=ATE_ATNotes3;	
		String taskSubject=ATE_ATSubject3;
		String activityType=ATE_ATActivityType3;
		String taskDueDate=ATE_AdvanceStartDate1;


		String[] relatedToData=ATE_ARelatedTo3.split("<break>");

		String[] relatedAssocVal=ATE_ARelatedAsso3.split("<break>");

		String contactName=ATE_Contact1;
		String contactName1=ATE_Contact4;

		String userName=crmUser6FirstName+" "+crmUser6LastName;
		String userName1=crmUser7FirstName+" "+crmUser7LastName;

		String[] relatedAssociation=new String[relatedAssocVal.length+1];
		relatedAssociation[0]=userName1;
		for(int i=1; i<relatedAssociation.length; i++)
		{
			relatedAssociation[i]=relatedAssocVal[i-1];
		}

		String[] completedate = taskDueDate.split("/");
		char dayMonth = completedate[0].charAt(0);
		String month;
		if (dayMonth == '0') {
			month = completedate[0].replaceAll("0", "");
		} else {
			month = completedate[0];
		}
		String date = month + "/" +completedate[1]  + "/" + completedate[2];
		String activityType1=ATE_ATActivityType2;
		String taskSubject1=ATE_ATSubject2;
		String taskDetails1=ATE_ATNotes2;
		String taskDueDate1=ATE_AdvanceDueDate2;

		String[] completedate1 = taskDueDate1.split("/");
		char dayMonth1 = completedate1[0].charAt(0);
		String month1;
		if (dayMonth1 == '0') {
			month1 = completedate1[0].replaceAll("0", "");
		} else {
			month1 = completedate1[0];
		}

		String date1 = month1 + "/" +completedate1[1]  + "/" + completedate1[2];



		String dealSectionName=ATE_DealName2;
		String dealSectionStage=ATE_DealStage2;
		String dealSectionDateRecieved=ATE_DealDateRecieved2;


		String[] companiesTaggedName= {ATE_TaggedCompanyName8,ATE_TaggedCompanyName3,ATE_TaggedCompanyName5,ATE_TaggedCompanyName6,ATE_TaggedCompanyName7};

		String[] companiesTaggedTimeReference= {ATE_TaggedCompanyTimeReference8,ATE_TaggedCompanyTimeReference3,ATE_TaggedCompanyTimeReference5,ATE_TaggedCompanyTimeReference6,ATE_TaggedCompanyTimeReference7};


		String[] dealTaggedName= {ATE_TaggedDealName3,ATE_TaggedDealName4,ATE_TaggedDealName5};

		String[] dealTaggedTimeReference= {ATE_TaggedDealTimeReference3,ATE_TaggedDealTimeReference4,ATE_TaggedDealTimeReference5};

		String[] peopleTagName={ATE_TaggedPeopleName5,ATE_TaggedPeopleName3,ATE_TaggedPeopleName4};
		String[] peopleTaggedTimeReference={ATE_TaggedPeopleTimeReference5,ATE_TaggedPeopleTimeReference3,ATE_TaggedPeopleTimeReference4};


		String contactSectionName=ATE_ContactName5;
		String contactSectionTitle=ATE_ContactTitle5;
		String contactSectionDeal=ATE_ContactDeal5;
		String contactSectionMeetingAndCall=ATE_ContactMeetingAndCall5;
		String contactSectionEmail=ATE_ContactEmail5;


		String contactSectionName1=ATE_ContactName3;
		String contactSectionTitle1=ATE_ContactTitle3;
		String contactSectionDeal1=ATE_ContactDeal3;
		String contactSectionMeetingAndCall1=ATE_ContactMeetingAndCall3;
		String contactSectionEmail1=ATE_ContactEmail3;

		String connectionTeamMember=userName;
		String connectionTitle=ATE_ConnectionTitle1;
		String connectionDeal=ATE_ConnectionDeals1;
		String connectionMeetingAndCall=ATE_ConnectionMeetingAndCall1;
		String connectionEmail=ATE_ConnectionEmail1;

		String connectionTeamMember1=userName1;
		String connectionTitle1=ATE_ConnectionTitle2;
		String connectionDeal1=ATE_ConnectionDeals2;
		String connectionMeetingAndCall1=ATE_ConnectionMeetingAndCall2;
		String connectionEmail1=ATE_ConnectionEmail2;


		String message=bp.acuityDefaultMessage;

		ArrayList<String> meetingAndCallsPopupHeader=new ArrayList<String>();
		meetingAndCallsPopupHeader.add("Type");
		meetingAndCallsPopupHeader.add("Date");
		meetingAndCallsPopupHeader.add("Subject");
		meetingAndCallsPopupHeader.add("Details");
		meetingAndCallsPopupHeader.add("Assigned To");


		lp.CRMLogin(crmUser7EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	


					ArrayList<String> result=bp.verifyRecordOnInteractionCard(date, IconType.Event,taskSubject, taskDetails, false, true, relatedToData,relatedAssociation);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The task has been verified on Interaction card. subject name: "+taskSubject , YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The task is not verified on Interaction card. subject name: "+taskSubject+". "+result , YesNo.No);
						sa.assertTrue(false,  "The task is not verified on Interaction card. subject name: "+taskSubject+". "+result);
					}


					ArrayList<String> result1=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, peopleTagName, peopleTaggedTimeReference, dealTaggedName, dealTaggedTimeReference);
					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,  "The record name and Time reference are not verifed "+result1, YesNo.No);
						sa.assertTrue(false,  "The record name and Time reference are not verifed "+result1);
					}


					ArrayList<String> result2=bp.verifyRecordOnDealsSectionInAcuity(recordName, dealSectionName, null, dealSectionStage, dealSectionDateRecieved);
					if(result2.isEmpty())
					{
						log(LogStatus.INFO, dealSectionName+" record on Deal section has been verified for "+recordName, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,  dealSectionName+" record on Deal section is not verified for "+recordName+". "+result2, YesNo.No);
						sa.assertTrue(false,  dealSectionName+" record on Deal section is not verified for "+recordName+". "+result2);
					}


					ArrayList<String> result3=bp.verifyRecordOnContactSectionAcuity(contactSectionName, contactSectionTitle, contactSectionDeal, contactSectionMeetingAndCall, contactSectionEmail);
					if(result3.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on contact section in Acuity contact : "+contactSectionName, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on contact section in Acuity for contact : "+contactSectionName+". "+result3, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on contact section in Acuity for contact : "+contactSectionName+". "+result3);
					}


					ArrayList<String> result4=bp.verifyRecordOnContactSectionAcuity(contactSectionName1, contactSectionTitle1, contactSectionDeal1, contactSectionMeetingAndCall1, contactSectionEmail1);
					if(result4.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on contact section in Acuity contact : "+contactSectionName1, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on contact section in Acuity for contact : "+contactSectionName1+". "+result4, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on contact section in Acuity for contact : "+contactSectionName1+". "+result4);
					}



					ArrayList<String> result5=bp.verifyRecordOnConnectionsPopUpOfContactInAcuity(contactName, connectionTeamMember, connectionTitle, connectionDeal, connectionMeetingAndCall, connectionEmail);
					if(result5.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on Connection section in Acuity for user "+connectionTeamMember, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on Connection section in Acuity for user "+connectionTeamMember+". "+result5, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on Connection section in Acuity for user "+connectionTeamMember+". "+result5);
					}

					ArrayList<String> result6=bp.verifyRecordOnConnectionsPopUpOfContactInAcuity(contactName, connectionTeamMember1, connectionTitle1, connectionDeal1, connectionMeetingAndCall1, connectionEmail1);
					if(result6.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on Connection section in Acuity for user "+connectionTeamMember1, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on Connection section in Acuity for user "+connectionTeamMember1+". "+result6, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on Connection section in Acuity for user "+connectionTeamMember1+". "+result6);
					}



					xPath="//a[text()='"+contactName+"']/ancestor::tr//td[@data-label='Meetings and Calls']//button";
					ele=FindElement(driver, xPath, "meeting and call count of "+contactName, action.SCROLLANDBOOLEAN, 20);
					if(click(driver, ele, contactName+" meetings and call count", action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on the count of meeting and call of "+contactName, YesNo.No);

						ArrayList<String> result7=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity(activityType, date, taskSubject, taskDetails, userName1);
						if(result7.isEmpty())
						{
							log(LogStatus.INFO, "The records on meeting & calls popup have been verified for "+recordName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records on meeting & calls popup are not verified for "+recordName+". "+result7, YesNo.No);
							sa.assertTrue(false, "The records on meeting & calls popup are not verified for "+recordName+". "+result7);
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+contactName, YesNo.No);
						sa.assertTrue(false, "Not able to click on the count of meeting and call of "+contactName);
					}

					xPath="//a[text()='"+contactName+"']/ancestor::tr//td[@data-label='Meetings and Calls']//button";
					ele=FindElement(driver, xPath, "meeting and call count of "+contactName, action.SCROLLANDBOOLEAN, 20);
					if(click(driver, ele, contactName+" meetings and call count", action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on the count of meeting and call of "+contactName, YesNo.No);

						ArrayList<String> result8=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity(activityType1, date1, taskSubject1, taskDetails1, userName);
						if(result8.isEmpty())
						{
							log(LogStatus.INFO, "The records on meeting & calls popup have been verified for "+recordName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records on meeting & calls popup are not verified for "+recordName+". "+result8, YesNo.No);
							sa.assertTrue(false, "The records on meeting & calls popup are not verified for "+recordName+". "+result8);
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+contactName, YesNo.No);
						sa.assertTrue(false, "Not able to click on the count of meeting and call of "+contactName);
					}


					xPath="//a[text()='"+contactName1+"']/ancestor::tr//td[@data-label='Meetings and Calls']//button";
					ele=FindElement(driver, xPath, "meeting and call count of "+contactName1, action.SCROLLANDBOOLEAN, 20);
					if(click(driver, ele, contactName1+" meetings and call count", action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on the count of meeting and call of "+contactName, YesNo.No);


						ArrayList<String>result9 =bp.verifyUIOfMeetingAndCallsPopup(meetingAndCallsPopupHeader, message);
						if(result9.isEmpty())
						{
							log(LogStatus.INFO, "The UI and Message on Meeting and Calls popup have been verified", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR,  "The UI and Message on Meeting and Calls popup are not verified", YesNo.No);
							sa.assertTrue(false, "The UI and Message on Meeting and Calls popup are not verified");
						}

						xPath="//h2[contains(text(),'Meetings and Calls with')]/../button//lightning-icon";
						ele=FindElement(driver, xPath, "Meetings and Calls close", action.SCROLLANDBOOLEAN, 20);
						if(clickUsingJavaScript(driver, ele, "close button"))
						{
							log(LogStatus.INFO, "clicked on close button of Meetings and Calls popup", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on close button of Meetings and Calls popup", YesNo.No);
							sa.assertTrue(false,  "Not able to click on close button of Meetings and Calls popup");
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+contactName1, YesNo.No);
						sa.assertTrue(false, "Not able to click on the count of meeting and call of "+contactName1);
					}

				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab "+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}


	@Parameters({ "projectName" })
	@Test
	public void ATETc0025_LoginWithPEUser2AndVerifyContactRecordPageAcuityTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATE_Contact1;
		String xPath;
		WebElement ele;


		String taskDetails=ATE_ATNotes3;	
		String taskSubject=ATE_ATSubject3;
		String activityType=ATE_ATActivityType3;
		String taskDueDate=ATE_AdvanceStartDate1;

		String[] relatedToData=ATE_ARelatedTo3.split("<break>");

		String[] relatedAssocVal=ATE_ARelatedAsso3.split("<break>");

		String contactName=ATE_Contact1;
		String contactName1=ATE_Contact4;

		String userName=crmUser6FirstName+" "+crmUser6LastName;
		String userName1=crmUser7FirstName+" "+crmUser7LastName;

		String[] relatedAssociation=new String[relatedAssocVal.length+1];
		relatedAssociation[0]=userName1;
		for(int i=1; i<relatedAssociation.length; i++)
		{
			relatedAssociation[i]=relatedAssocVal[i-1];
		}

		String[] completedate = taskDueDate.split("/");
		char dayMonth = completedate[0].charAt(0);
		String month;
		if (dayMonth == '0') {
			month = completedate[0].replaceAll("0", "");
		} else {
			month = completedate[0];
		}
		String date = month + "/" +completedate[1]  + "/" + completedate[2];


		String activityType1=ATE_ATActivityType2;
		String taskSubject1=ATE_ATSubject2;
		String taskDetails1=ATE_ATNotes2;
		String taskDueDate1=ATE_AdvanceDueDate2;

		String[] completedate1 = taskDueDate1.split("/");
		char dayMonth1 = completedate1[0].charAt(0);
		String month1;
		if (dayMonth1 == '0') {
			month1 = completedate1[0].replaceAll("0", "");
		} else {
			month1 = completedate1[0];
		}

		String date1 = month1 + "/" +completedate1[1]  + "/" + completedate1[2];



		String dealSectionName=ATE_DealName1;
		String dealSectionCompany=ATE_DealCompany1;
		String dealSectionStage=ATE_DealStage1;
		String dealSectionDateRecieved=ATE_DealDateRecieved1;


		String[] companiesTaggedName= {ATE_TaggedCompanyName8,ATE_TaggedCompanyName3,ATE_TaggedCompanyName5,ATE_TaggedCompanyName6,ATE_TaggedCompanyName7};

		String[] companiesTaggedTimeReference= {ATE_TaggedCompanyTimeReference8,ATE_TaggedCompanyTimeReference3,ATE_TaggedCompanyTimeReference5,ATE_TaggedCompanyTimeReference6,ATE_TaggedCompanyTimeReference7};


		String[] dealTaggedName= {ATE_TaggedDealName3,ATE_TaggedDealName4,ATE_TaggedDealName5};

		String[] dealTaggedTimeReference= {ATE_TaggedDealTimeReference3,ATE_TaggedDealTimeReference4,ATE_TaggedDealTimeReference5};

		String[] peopleTagName={ATE_TaggedPeopleName5,ATE_TaggedPeopleName3,ATE_TaggedPeopleName4};
		String[] peopleTaggedTimeReference={ATE_TaggedPeopleTimeReference5,ATE_TaggedPeopleTimeReference3,ATE_TaggedPeopleTimeReference4};

		String connectionTeamMember=userName;
		String connectionTitle=ATE_ConnectionTitle1;
		String connectionDeal=ATE_ConnectionDeals1;
		String connectionMeetingAndCall=ATE_ConnectionMeetingAndCall1;
		String connectionEmail=ATE_ConnectionEmail1;

		String connectionTeamMember1=userName1;
		String connectionTitle1=ATE_ConnectionTitle2;
		String connectionDeal1=ATE_ConnectionDeals2;
		String connectionMeetingAndCall1=ATE_ConnectionMeetingAndCall2;
		String connectionEmail1=ATE_ConnectionEmail2;

		lp.CRMLogin(crmUser7EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	


					ArrayList<String> result=bp.verifyRecordOnInteractionCard(date, IconType.Event,taskSubject, taskDetails, false, true, relatedToData,relatedAssociation);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The task has been verified on Interaction card. subject name: "+taskSubject , YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The task is not verified on Interaction card. subject name: "+taskSubject+". "+result , YesNo.No);
						sa.assertTrue(false,  "The task is not verified on Interaction card. subject name: "+taskSubject+". "+result);
					}


					ArrayList<String> result1=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, peopleTagName, peopleTaggedTimeReference, dealTaggedName, dealTaggedTimeReference);
					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,  "The record name and Time reference are not verifed "+result1, YesNo.No);
						sa.assertTrue(false,  "The record name and Time reference are not verifed "+result1);
					}


					ArrayList<String> result2=bp.verifyRecordOnDealsSectionInAcuity(recordName, dealSectionName, dealSectionCompany, dealSectionStage, dealSectionDateRecieved);
					if(result2.isEmpty())
					{
						log(LogStatus.INFO, dealSectionName+" record on Deal section has been verified for "+recordName, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,  dealSectionName+" record on Deal section is not verified for "+recordName+". "+result2, YesNo.No);
						sa.assertTrue(false,  dealSectionName+" record on Deal section is not verified for "+recordName+". "+result2);
					}

					ArrayList<String> result5=bp.verifyRecordOnConnectionsSectionInAcuity(recordName, connectionTeamMember, connectionTitle, connectionDeal, connectionMeetingAndCall, connectionEmail);
					if(result5.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on Connection section in Acuity for user "+connectionTeamMember, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on Connection section in Acuity for user "+connectionTeamMember+". "+result5, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on Connection section in Acuity for user "+connectionTeamMember+". "+result5);
					}

					ArrayList<String> result6=bp.verifyRecordOnConnectionsSectionInAcuity(recordName, connectionTeamMember1, connectionTitle1, connectionDeal1, connectionMeetingAndCall1, connectionEmail1);
					if(result6.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on Connection section in Acuity for user "+connectionTeamMember1, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on Connection section in Acuity for user "+connectionTeamMember1+". "+result6, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on Connection section in Acuity for user "+connectionTeamMember1+". "+result6);
					}



					xPath="//a[text()='"+userName1+"']/ancestor::tr//td[@data-label='Meetings and Calls']//button";
					ele=FindElement(driver, xPath, "meeting and call count of "+userName1, action.SCROLLANDBOOLEAN, 20);
					if(click(driver, ele, userName1+" meetings and call count", action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on the count of meeting and call of "+userName1, YesNo.No);

						ArrayList<String> result7=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity(activityType, date, taskSubject, taskDetails, userName1);
						if(result7.isEmpty())
						{
							log(LogStatus.INFO, "The records on meeting & calls popup have been verified for "+userName1, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records on meeting & calls popup are not verified for "+userName1+". "+result7, YesNo.No);
							sa.assertTrue(false, "The records on meeting & calls popup are not verified for "+userName1+". "+result7);
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+contactName, YesNo.No);
						sa.assertTrue(false, "Not able to click on the count of meeting and call of "+contactName);
					}

					xPath="//a[text()='"+userName+"']/ancestor::tr//td[@data-label='Meetings and Calls']//button";
					ele=FindElement(driver, xPath, "meeting and call count of "+userName, action.SCROLLANDBOOLEAN, 20);
					if(click(driver, ele, userName+" meetings and call count", action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on the count of meeting and call of "+userName1, YesNo.No);

						ArrayList<String> result8=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity(activityType1, date1, taskSubject1, taskDetails1, userName);
						if(result8.isEmpty())
						{
							log(LogStatus.INFO, "The records on meeting & calls popup have been verified for "+userName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records on meeting & calls popup are not verified for "+userName+". "+result8, YesNo.No);
							sa.assertTrue(false, "The records on meeting & calls popup are not verified for "+userName+". "+result8);
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+contactName, YesNo.No);
						sa.assertTrue(false, "Not able to click on the count of meeting and call of "+contactName);
					}
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab "+tabObj2, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj2);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}



	@Parameters({ "projectName" })
	@Test
	public void ATETc0026_LoginWithPEUser1AndVerifyAcuityTabOnReferencedAccountsWhichIsTaggedFromRelatedAssociationFieldAndReferencedAccountIsOfIntermediaryRecordType(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATERecord5;
		String xPath;
		WebElement ele;

		String taskDetails=ATE_ATNotes3;	
		String taskSubject=ATE_ATSubject3;
		String activityType=ATE_ATActivityType3;
		String taskDueDate=ATE_AdvanceStartDate1;

		String[] relatedToData=ATE_ARelatedTo3.split("<break>");
		String[] relatedAssocVal=ATE_ARelatedAsso3.split("<break>");

		String userName=crmUser6FirstName+" "+crmUser6LastName;
		String userName1=crmUser7FirstName+" "+crmUser7LastName;

		String[] relatedAssociation=new String[relatedAssocVal.length+1];
		relatedAssociation[0]=userName1;
		for(int i=1; i<relatedAssociation.length; i++)
		{
			relatedAssociation[i]=relatedAssocVal[i-1];
		}

		String[] completedate = taskDueDate.split("/");
		char dayMonth = completedate[0].charAt(0);
		String month;
		if (dayMonth == '0') {
			month = completedate[0].replaceAll("0", "");
		} else {
			month = completedate[0];
		}
		String date = month + "/" +completedate[1]  + "/" + completedate[2];


		String activityType1=ATE_ATActivityType1;
		String taskSubject1=ATE_ATSubject1;
		String taskDetails1=ATE_ATNotes1;
		String taskDueDate1=ATE_AdvanceDueDate1;

		String[] completedate1 = taskDueDate1.split("/");
		char dayMonth1 = completedate1[0].charAt(0);
		String month1;
		if (dayMonth1 == '0') {
			month1 = completedate1[0].replaceAll("0", "");
		} else {
			month1 = completedate1[0];
		}

		String date1 = month1 + "/" +completedate1[1]  + "/" + completedate1[2];

		String[] data=ATE_ARelatedTo1.split("<break>");
		String[] relatedToData1=new String[data.length+1];
		relatedToData1[0]=userName;
		for(int i=1; i<relatedToData1.length; i++)
		{
			relatedToData1[i]=data[i-1];
		}

		String[] relatedAssocVal1=ATE_ARelatedAsso1.split("<break>");

		ArrayList<String> emptyList=new ArrayList<String>();
		ArrayList<String> dealSectionHeaders=new ArrayList<String>();
		dealSectionHeaders.add("Deal Name");
		dealSectionHeaders.add("Stage");
		dealSectionHeaders.add("Date Received");


		String[] companiesTaggedName= {ATE_TaggedCompanyName9,ATE_TaggedCompanyName10,ATE_TaggedCompanyName5,ATE_TaggedCompanyName6,ATE_TaggedCompanyName7};
		String[] companiesTaggedTimeReference= {ATE_TaggedCompanyTimeReference9,ATE_TaggedCompanyTimeReference10,ATE_TaggedCompanyTimeReference5,ATE_TaggedCompanyTimeReference6,ATE_TaggedCompanyTimeReference7};

		String[] dealTaggedName= {ATE_TaggedDealName3,ATE_TaggedDealName5};
		String[] dealTaggedTimeReference= {ATE_TaggedDealTimeReference3,ATE_TaggedDealTimeReference5};

		String[] peopleTagName={ATE_TaggedPeopleName6,ATE_TaggedPeopleName3,ATE_TaggedPeopleName4};
		String[] peopleTaggedTimeReference={ATE_TaggedPeopleTimeReference6,ATE_TaggedPeopleTimeReference3,ATE_TaggedPeopleTimeReference4};

		String contactSectionName=ATE_ContactName6;
		String contactSectionTitle=ATE_ContactTitle6;
		String contactSectionDeal=ATE_ContactDeal6;
		String contactSectionMeetingAndCall=ATE_ContactMeetingAndCall6;
		String contactSectionEmail=ATE_ContactEmail6;


		String contactSectionName1=ATE_ContactName7;
		String contactSectionTitle1=ATE_ContactTitle7;
		String contactSectionDeal1=ATE_ContactDeal7;
		String contactSectionMeetingAndCall1=ATE_ContactMeetingAndCall7;
		String contactSectionEmail1=ATE_ContactEmail7;

		String companyTagName=ATE_TaggedCompanyName9;
		String companyTagTimeReferenceCount=ATE_TaggedCompanyTimeReference9;

		IconType[] companyIcon= {IconType.Task,IconType.Event};
		String[] companySubjectName= {ATE_ATSubject1,ATE_ATSubject3};
		String[] companyDetails= {ATE_ATNotes1,ATE_ATNotes3};
		String[] companyDueDate= {date1,date};
		String[] companyUsers= {userName,userName1};

		String message=bp.acuityDefaultMessage;

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	


					ArrayList<String> result=bp.verifyRecordOnInteractionCard(date, IconType.Event,taskSubject, taskDetails, false, true, relatedToData,relatedAssociation);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The task has been verified on Interaction card. subject name: "+taskSubject , YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The task is not verified on Interaction card. subject name: "+taskSubject+". "+result , YesNo.No);
						sa.assertTrue(false,  "The task is not verified on Interaction card. subject name: "+taskSubject+". "+result);
					}

					ArrayList<String> result9=bp.verifyRecordOnInteractionCard(date1, IconType.Task, taskSubject1, taskDetails1, true, false, relatedToData1,relatedAssocVal1);
					if(result9.isEmpty())
					{
						log(LogStatus.INFO, "The task has been verified on Interaction card. subject name: "+taskSubject , YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The task is not verified on Interaction card. subject name: "+taskSubject+". "+result9 , YesNo.No);
						sa.assertTrue(false,  "The task is not verified on Interaction card. subject name: "+taskSubject+". "+result9);
					}

					ArrayList<String> result1=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, peopleTagName, peopleTaggedTimeReference, dealTaggedName, dealTaggedTimeReference);
					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,  "The record name and Time reference are not verifed "+result1, YesNo.No);
						sa.assertTrue(false,  "The record name and Time reference are not verifed "+result1);
					}

					ArrayList<String>result2=bp.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null, emptyList, null, dealSectionHeaders, message, emptyList, null);

					if(result2.isEmpty())
					{
						log(LogStatus.INFO, "The message and header name of deal section have been verified", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The message and header name of deal section are not verified. "+result2, YesNo.No);
						sa.assertTrue(false,  "The message and header name of deal section are not verified. "+result2);
					}


					ArrayList<String> result3=bp.verifyRecordOnContactSectionAcuity(contactSectionName, contactSectionTitle, contactSectionDeal, contactSectionMeetingAndCall, contactSectionEmail);
					if(result3.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on contact section in Acuity contact : "+contactSectionName, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on contact section in Acuity for contact : "+contactSectionName+". "+result3, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on contact section in Acuity for contact : "+contactSectionName+". "+result3);
					}

					ArrayList<String> result4=bp.verifyRecordOnContactSectionAcuity(contactSectionName1, contactSectionTitle1, contactSectionDeal1, contactSectionMeetingAndCall1, contactSectionEmail1);
					if(result4.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on contact section in Acuity contact : "+contactSectionName1, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on contact section in Acuity for contact : "+contactSectionName1+". "+result4, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on contact section in Acuity for contact : "+contactSectionName1+". "+result4);
					}

					if (click(driver, bp.getTaggedRecordName("Companies", 30), "Companies tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Companies tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("Companies", companyTagName, companyTagTimeReferenceCount,30), companyTagName+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+companyTagName,YesNo.No);

							ArrayList<String> result5=bp.verifyRecordsonInteractionsViewAllPopup(companyIcon, companyDueDate, companySubjectName, companyDetails, companyUsers, companySubjectName);
							if(result5.isEmpty())
							{
								log(LogStatus.INFO, "All records on Interaction card have been verified for "+companyTagName+" record", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "All records on Interaction card are not verified for "+companyTagName+" record " +result5, YesNo.No);
								sa.assertTrue(false,  "All records on Interaction card are not verified for "+companyTagName+" record "+result5);
							}
							xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
							ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
							if(clickUsingJavaScript(driver, ele, "close button"))
							{
								log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
								sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+companyTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+companyTagName);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Companies tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Companies tab name");
					}

				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab "+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}

	@Parameters({ "projectName" })
	@Test
	public void ATETc0027_VerifyAcuityTabOnReferencedAccountsWhichIsTaggedFromContactNameField(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATERecord6;
		String xPath;
		WebElement ele;

		String taskDetails=ATE_ATNotes3;	
		String taskSubject=ATE_ATSubject3;
		String taskDueDate=ATE_AdvanceStartDate1;

		String[] relatedToData=ATE_ARelatedTo3.split("<break>");
		String[] relatedAssocVal=ATE_ARelatedAsso3.split("<break>");

		String userName=crmUser6FirstName+" "+crmUser6LastName;
		String userName1=crmUser7FirstName+" "+crmUser7LastName;

		String[] relatedAssociation=new String[relatedAssocVal.length+1];
		relatedAssociation[0]=userName1;
		for(int i=1; i<relatedAssociation.length; i++)
		{
			relatedAssociation[i]=relatedAssocVal[i-1];
		}

		String[] completedate = taskDueDate.split("/");
		char dayMonth = completedate[0].charAt(0);
		String month;
		if (dayMonth == '0') {
			month = completedate[0].replaceAll("0", "");
		} else {
			month = completedate[0];
		}
		String date = month + "/" +completedate[1]  + "/" + completedate[2];

		String[] data=ATE_ARelatedTo1.split("<break>");
		String[] relatedToData1=new String[data.length+1];
		relatedToData1[0]=userName;
		for(int i=1; i<relatedToData1.length; i++)
		{
			relatedToData1[i]=data[i-1];
		}

		ArrayList<String> emptyList=new ArrayList<String>();
		ArrayList<String> dealSectionHeaders=new ArrayList<String>();
		dealSectionHeaders.add("Deal Name");
		dealSectionHeaders.add("Stage");
		dealSectionHeaders.add("Date Received");

		ArrayList<String> contactsSectionHeaders=new ArrayList<String>();
		contactsSectionHeaders.add("Name");
		contactsSectionHeaders.add("Title");
		contactsSectionHeaders.add("Deals");
		contactsSectionHeaders.add("Meetings and Calls");
		contactsSectionHeaders.add("Emails");


		String[] companiesTaggedName= {ATE_TaggedCompanyName10,ATE_TaggedCompanyName11,ATE_TaggedCompanyName12,ATE_TaggedCompanyName6,ATE_TaggedCompanyName7};
		String[] companiesTaggedTimeReference= {ATE_TaggedCompanyTimeReference10,ATE_TaggedCompanyTimeReference11,ATE_TaggedCompanyTimeReference12,ATE_TaggedCompanyTimeReference6,ATE_TaggedCompanyTimeReference7};

		String[] dealTaggedName= {ATE_TaggedDealName5};
		String[] dealTaggedTimeReference= {ATE_TaggedDealTimeReference5};

		String[] peopleTagName={ATE_TaggedPeopleName7,ATE_TaggedPeopleName3,ATE_TaggedPeopleName4};
		String[] peopleTaggedTimeReference={ATE_TaggedPeopleTimeReference7,ATE_TaggedPeopleTimeReference3,ATE_TaggedPeopleTimeReference4};

		String companyTagName=ATE_TaggedCompanyName11;
		String companyTagTimeReferenceCount=ATE_TaggedCompanyTimeReference11;

		IconType[] companyIcon= {IconType.Event};
		String[] companySubjectName= {ATE_ATSubject3};
		String[] companyDetails= {ATE_ATNotes3};
		String[] companyDueDate= {date};
		String[] companyUsers= {userName1};

		String message=bp.acuityDefaultMessage;

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	


					ArrayList<String> result=bp.verifyRecordOnInteractionCard(date, IconType.Event,taskSubject, taskDetails, false, true, relatedToData,relatedAssociation);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The task has been verified on Interaction card. subject name: "+taskSubject , YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The task is not verified on Interaction card. subject name: "+taskSubject+". "+result , YesNo.No);
						sa.assertTrue(false,  "The task is not verified on Interaction card. subject name: "+taskSubject+". "+result);
					}


					ArrayList<String> result1=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, peopleTagName, peopleTaggedTimeReference, dealTaggedName, dealTaggedTimeReference);
					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,  "The record name and Time reference are not verifed "+result1, YesNo.No);
						sa.assertTrue(false,  "The record name and Time reference are not verifed "+result1);
					}

					ArrayList<String>result2=bp.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null, contactsSectionHeaders , message, dealSectionHeaders, message, emptyList, null);

					if(result2.isEmpty())
					{
						log(LogStatus.INFO, "The message and header name of deal section and contact section have been verified", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The message and header name of deal section and contact section are not verified. "+result2, YesNo.No);
						sa.assertTrue(false,  "The message and header name of deal section and contact section are not verified. "+result2);
					}



					if (click(driver, bp.getTaggedRecordName("Companies", 30), "Companies tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Companies tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("Companies", companyTagName, companyTagTimeReferenceCount,30), companyTagName+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+companyTagName,YesNo.No);

							ArrayList<String> result5=bp.verifyRecordsonInteractionsViewAllPopup(companyIcon, companyDueDate, companySubjectName, companyDetails, companyUsers, companySubjectName);
							if(result5.isEmpty())
							{
								log(LogStatus.INFO, "All records on Interaction card have been verified for "+companyTagName+" record", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "All records on Interaction card are not verified for "+companyTagName+" record " +result5, YesNo.No);
								sa.assertTrue(false,  "All records on Interaction card are not verified for "+companyTagName+" record "+result5);
							}
							xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
							ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
							if(clickUsingJavaScript(driver, ele, "close button"))
							{
								log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
								sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+companyTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+companyTagName);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Companies tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Companies tab name");
					}

				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab "+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}



	@Parameters({ "projectName" })
	@Test
	public void ATETc0028_VerifyAcuityTabOnReferencedAccountsWhichIsCompanyRecord(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATERecord4;
		String xPath;
		WebElement ele;

		String taskDetails=ATE_ATNotes3;	
		String taskSubject=ATE_ATSubject3;

		String[] relatedToData=ATE_ARelatedTo3.split("<break>");
		String[] relatedAssocVal=ATE_ARelatedAsso3.split("<break>");

		String userName=crmUser6FirstName+" "+crmUser6LastName;
		String userName1=crmUser7FirstName+" "+crmUser7LastName;

		String[] relatedAssociation=new String[relatedAssocVal.length+1];
		relatedAssociation[0]=userName1;
		for(int i=1; i<relatedAssociation.length; i++)
		{
			relatedAssociation[i]=relatedAssocVal[i-1];
		}

		String taskSubject1=ATE_ATSubject2;
		String taskDetails1=ATE_ATNotes2;

		String[] completedate1 = ATE_AdvanceDueDate2.split("/");
		char dayMonth1 = completedate1[0].charAt(0);
		String month1;
		if (dayMonth1 == '0') {
			month1 = completedate1[0].replaceAll("0", "");
		} else {
			month1 = completedate1[0];
		}
		String date1 = month1 + "/" +completedate1[1]  + "/" + completedate1[2];


		String[] completedate2 = ATE_AdvanceStartDate1.split("/");
		char dayMonth2 = completedate2[0].charAt(0);
		String month2;
		if (dayMonth2 == '0') {
			month2 = completedate2[0].replaceAll("0", "");
		} else {
			month2 = completedate2[0];
		}

		String date2 = month2 + "/" +completedate2[1]  + "/" + completedate2[2];


		String[] completedate3 = ATE_AdvanceDueDate3.split("/");
		char dayMonth3 = completedate3[0].charAt(0);
		String month3;
		if (dayMonth3 == '0') {
			month3 = completedate3[0].replaceAll("0", "");
		} else {
			month3 = completedate3[0];
		}
		String date3 = month3 + "/" +completedate3[1]  + "/" + completedate3[2];


		String[] completedate4 = ATE_AdvanceDueDate4.split("/");
		char dayMonth4 = completedate4[0].charAt(0);
		String month4;
		if (dayMonth4 == '0') {
			month4 = completedate4[0].replaceAll("0", "");
		} else {
			month4 = completedate4[0];
		}
		String date4 = month4 + "/" +completedate4[1]  + "/" + completedate4[2];


		String[] data=ATE_ARelatedAsso2.split("<break>");
		String[] relatedAssociation1=new String[data.length+1];
		relatedAssociation1[0]=userName;
		for(int i=1; i<relatedAssociation1.length; i++)
		{
			relatedAssociation1[i]=data[i-1];
		}		

		String[] relatedToData1=ATE_ARelatedTo2.split("<break>");

		ArrayList<String> emptyList=new ArrayList<String>();
		ArrayList<String> dealSectionHeaders=new ArrayList<String>();
		dealSectionHeaders.add("Deal Name");
		dealSectionHeaders.add("Stage");
		dealSectionHeaders.add("Date Received");


		String[] companiesTaggedName= {ATE_TaggedCompanyName13,ATE_TaggedCompanyName12,ATE_TaggedCompanyName5,ATE_TaggedCompanyName6,ATE_TaggedCompanyName7};
		String[] companiesTaggedTimeReference= {ATE_TaggedCompanyTimeReference13,ATE_TaggedCompanyTimeReference12,ATE_TaggedCompanyTimeReference5,ATE_TaggedCompanyTimeReference6,ATE_TaggedCompanyTimeReference7};

		String[] dealTaggedName= {ATE_TaggedDealName4,ATE_TaggedDealName5};
		String[] dealTaggedTimeReference= {ATE_TaggedDealTimeReference4,ATE_TaggedDealTimeReference5};

		String[] peopleTagName={ATE_TaggedPeopleName8,ATE_TaggedPeopleName3,ATE_TaggedPeopleName4};
		String[] peopleTaggedTimeReference={ATE_TaggedPeopleTimeReference8,ATE_TaggedPeopleTimeReference3,ATE_TaggedPeopleTimeReference4};

		String contactSectionName=ATE_ContactName8;
		String contactSectionTitle=ATE_ContactTitle8;
		String contactSectionDeal=ATE_ContactDeal8;
		String contactSectionMeetingAndCall=ATE_ContactMeetingAndCall8;
		String contactSectionEmail=ATE_ContactEmail8;

		String companyTagName=ATE_TaggedCompanyName13;
		String companyTagTimeReferenceCount=ATE_TaggedCompanyTimeReference13;

		IconType[] icon= {IconType.Call,IconType.Event,IconType.Task,IconType.Task};
		String[] subjectName= {ATE_ATSubject2,ATE_ATSubject3,ATE_ATSubject4,ATE_ATSubject5};
		String[] details= {ATE_ATNotes2,ATE_ATNotes3,ATE_ATNotes4,ATE_ATNotes5};
		String[] dueDate= {date1,date2,date3,date4};
		String[] users= {userName,userName1,userName,userName};

		String message=bp.acuityDefaultMessage;

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	


					ArrayList<String> result=bp.verifyRecordOnInteractionCard(date2, IconType.Event,taskSubject, taskDetails, false, true, relatedToData,relatedAssociation);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The task has been verified on Interaction card. subject name: "+taskSubject , YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The task is not verified on Interaction card. subject name: "+taskSubject+". "+result , YesNo.No);
						sa.assertTrue(false,  "The task is not verified on Interaction card. subject name: "+taskSubject+". "+result);
					}

					ArrayList<String> result9=bp.verifyRecordOnInteractionCard(date1, IconType.Call, taskSubject1, taskDetails1, true, false, relatedToData1,relatedAssociation1);
					if(result9.isEmpty())
					{
						log(LogStatus.INFO, "The task has been verified on Interaction card. subject name: "+taskSubject1 , YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The task is not verified on Interaction card. subject name: "+taskSubject1+". "+result9 , YesNo.No);
						sa.assertTrue(false,  "The task is not verified on Interaction card. subject name: "+taskSubject1+". "+result9);
					}

					ArrayList<String> result1=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, peopleTagName, peopleTaggedTimeReference, dealTaggedName, dealTaggedTimeReference);
					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,  "The record name and Time reference are not verifed "+result1, YesNo.No);
						sa.assertTrue(false,  "The record name and Time reference are not verifed "+result1);
					}

					ArrayList<String>result2=bp.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null, emptyList, null, dealSectionHeaders, message, emptyList, null);

					if(result2.isEmpty())
					{
						log(LogStatus.INFO, "The message and header name of deal section have been verified", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The message and header name of deal section are not verified. "+result2, YesNo.No);
						sa.assertTrue(false,  "The message and header name of deal section are not verified. "+result2);
					}


					ArrayList<String> result3=bp.verifyRecordOnContactSectionAcuity(contactSectionName, contactSectionTitle, contactSectionDeal, contactSectionMeetingAndCall, contactSectionEmail);
					if(result3.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on contact section in Acuity contact : "+contactSectionName, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on contact section in Acuity for contact : "+contactSectionName+". "+result3, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on contact section in Acuity for contact : "+contactSectionName+". "+result3);
					}

					if (click(driver, bp.getTaggedRecordName("Companies", 30), "Companies tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Companies tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("Companies", companyTagName, companyTagTimeReferenceCount,30), companyTagName+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+companyTagName,YesNo.No);

							ArrayList<String> result5=bp.verifyRecordsonInteractionsViewAllPopup(icon, dueDate, subjectName, details, users, subjectName);
							if(result5.isEmpty())
							{
								log(LogStatus.INFO, "All records on Interaction card have been verified for "+companyTagName+" record", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "All records on Interaction card are not verified for "+companyTagName+" record " +result5, YesNo.No);
								sa.assertTrue(false,  "All records on Interaction card are not verified for "+companyTagName+" record "+result5);
							}
							xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
							ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
							if(clickUsingJavaScript(driver, ele, "close button"))
							{
								log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
								sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+companyTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+companyTagName);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Companies tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Companies tab name");
					}

				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab "+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}


	@Parameters({ "projectName" })
	@Test
	public void ATETc0029_VerifyAcuityTabOnReferencedAccountsWhichIsInstitutionRecord(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATERecord7;
		String xPath;
		WebElement ele;

		String taskDetails=ATE_ATNotes3;	
		String taskSubject=ATE_ATSubject3;

		String[] relatedToData=ATE_ARelatedTo3.split("<break>");
		String[] relatedAssocVal=ATE_ARelatedAsso3.split("<break>");

		String userName=crmUser6FirstName+" "+crmUser6LastName;
		String userName1=crmUser7FirstName+" "+crmUser7LastName;

		String[] relatedAssociation=new String[relatedAssocVal.length+1];
		relatedAssociation[0]=userName1;
		for(int i=1; i<relatedAssociation.length; i++)
		{
			relatedAssociation[i]=relatedAssocVal[i-1];
		}

		String[] completedate2 = ATE_AdvanceStartDate1.split("/");
		char dayMonth2 = completedate2[0].charAt(0);
		String month2;
		if (dayMonth2 == '0') {
			month2 = completedate2[0].replaceAll("0", "");
		} else {
			month2 = completedate2[0];
		}

		String date2 = month2 + "/" +completedate2[1]  + "/" + completedate2[2];

		String[] data=ATE_ARelatedAsso2.split("<break>");
		String[] relatedAssociation1=new String[data.length+1];
		relatedAssociation1[0]=userName;
		for(int i=1; i<relatedAssociation1.length; i++)
		{
			relatedAssociation1[i]=data[i-1];
		}		

		String[] relatedToData1=ATE_ARelatedTo2.split("<break>");


		String[] companiesTaggedName= {ATE_TaggedCompanyName11,ATE_TaggedCompanyName12,ATE_TaggedCompanyName2,ATE_TaggedCompanyName5,ATE_TaggedCompanyName7};
		String[] companiesTaggedTimeReference= {ATE_TaggedCompanyTimeReference11,ATE_TaggedCompanyTimeReference12,ATE_TaggedCompanyTimeReference2,ATE_TaggedCompanyTimeReference5,ATE_TaggedCompanyTimeReference7};

		String[] dealTaggedName= {ATE_TaggedDealName5};
		String[] dealTaggedTimeReference= {ATE_TaggedDealTimeReference5};

		String[] peopleTagName={ATE_TaggedPeopleName4,ATE_TaggedPeopleName7};
		String[] peopleTaggedTimeReference={ATE_TaggedPeopleTimeReference4,ATE_TaggedPeopleTimeReference7};

		String contactSectionName=ATE_ContactName9;
		String contactSectionTitle=ATE_ContactTitle9;
		String contactSectionDeal=ATE_ContactDeal9;
		String contactSectionMeetingAndCall=ATE_ContactMeetingAndCall9;
		String contactSectionEmail=ATE_ContactEmail9;

		String companyTagName=ATE_TaggedCompanyName11;
		String companyTagTimeReferenceCount=ATE_TaggedCompanyTimeReference11;

		IconType[] icon= {IconType.Event};
		String[] subjectName= {ATE_ATSubject3};
		String[] details= {ATE_ATNotes3};
		String[] dueDate= {date2};
		String[] users= {userName1};

		String message=bp.acuityDefaultMessage;

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	


					ArrayList<String> result=bp.verifyRecordOnInteractionCard(date2, IconType.Event,taskSubject, taskDetails, false, true, relatedToData,relatedAssociation);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The task has been verified on Interaction card. subject name: "+taskSubject , YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The task is not verified on Interaction card. subject name: "+taskSubject+". "+result , YesNo.No);
						sa.assertTrue(false,  "The task is not verified on Interaction card. subject name: "+taskSubject+". "+result);
					}



					ArrayList<String> result1=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, peopleTagName, peopleTaggedTimeReference, dealTaggedName, dealTaggedTimeReference);
					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,  "The record name and Time reference are not verifed "+result1, YesNo.No);
						sa.assertTrue(false,  "The record name and Time reference are not verifed "+result1);
					}

					ArrayList<String> result3=bp.verifyRecordOnContactSectionAcuity(contactSectionName, contactSectionTitle, contactSectionDeal, contactSectionMeetingAndCall, contactSectionEmail);
					if(result3.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on contact section in Acuity contact : "+contactSectionName, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on contact section in Acuity for contact : "+contactSectionName+". "+result3, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on contact section in Acuity for contact : "+contactSectionName+". "+result3);
					}

					if (click(driver, bp.getTaggedRecordName("Companies", 30), "Companies tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Companies tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("Companies", companyTagName, companyTagTimeReferenceCount,30), companyTagName+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+companyTagName,YesNo.No);

							ArrayList<String> result5=bp.verifyRecordsonInteractionsViewAllPopup(icon, dueDate, subjectName, details, users, subjectName);
							if(result5.isEmpty())
							{
								log(LogStatus.INFO, "All records on Interaction card have been verified for "+companyTagName+" record", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "All records on Interaction card are not verified for "+companyTagName+" record " +result5, YesNo.No);
								sa.assertTrue(false,  "All records on Interaction card are not verified for "+companyTagName+" record "+result5);
							}
							xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
							ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
							if(clickUsingJavaScript(driver, ele, "close button"))
							{
								log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
								sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+companyTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+companyTagName);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Companies tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Companies tab name");
					}

				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab "+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}



	@Parameters({ "projectName" })
	@Test
	public void ATETc0030_VerifyAcuityTabOnReferencedDealsAlsoOnRecordTypeOfAccountsWhichAcuityTabWillNotDisplay(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String dealRecord="Connection Deal 1";

		String[] firmRecords= {"Test Advisor 1","LPNav Test Account 20","PCNav Test Account 22"};

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj4)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj4, YesNo.No);
			if (bp.clickOnAlreadyCreatedItem(projectName, TabName.DealTab,
					dealRecord, 30)) {
				log(LogStatus.INFO, dealRecord + " reocrd has been open", YesNo.No);

				if(bp.getTabNameOnPage("Acuity",10)==null)
				{
					log(LogStatus.INFO, "Acuity Tab is not displaying for record "+dealRecord, YesNo.No);
					sa.assertTrue(false,  "Acuity Tab is not displaying for record "+dealRecord);
				}
				else
				{
					log(LogStatus.ERROR, "Acuity Tab is displaying for record "+dealRecord, YesNo.No);
					sa.assertTrue(false,  "Acuity Tab is displaying for record "+dealRecord);
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+dealRecord, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+dealRecord);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab "+tabObj4, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj4);
		}	


		for(int i=0; i<firmRecords.length; i++)
		{
			if (lp.clickOnTab(projectName, tabObj1)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
				if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
						firmRecords[i], 30)) {
					log(LogStatus.INFO, firmRecords[i] + " reocrd has been open", YesNo.No);

					if(bp.getTabNameOnPage("Acuity",10)==null)
					{
						log(LogStatus.INFO, "Acuity Tab is not displaying for record "+firmRecords[i], YesNo.No);
						sa.assertTrue(true,  "Acuity Tab is not displaying for record "+firmRecords[i]);
					}
					else
					{
						log(LogStatus.ERROR, "Acuity Tab is displaying for record "+firmRecords[i], YesNo.No);
						sa.assertTrue(false,  "Acuity Tab is displaying for record "+firmRecords[i]);
					}
				}
				else
				{
					log(LogStatus.ERROR, "Not able to open record "+firmRecords[i], YesNo.No);
					sa.assertTrue(false,  "Not able to open record "+firmRecords[i]);
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to click on tab "+tabObj1, YesNo.No);
				sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
			}
		}	
		lp.CRMlogout();	
		sa.assertAll();	
	}


	@Parameters({ "projectName" })
	@Test
	public void ATETc0031_VerifyIntermediaryAccountAcuityTabAndVerifyCompanyWhichHaveDealThoseCompanyWillBeHighlightedInCompanyTabRefrerenceSection(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATERecord3;

		String userName1=crmUser6FirstName+" "+crmUser6LastName;
		String userName2=crmUser7FirstName+" "+crmUser7LastName;
		String userName3=crmUser8FirstName+" "+crmUser8LastName;
		String userName4=crmUser9FirstName+" "+crmUser9LastName;
		String userName5=crmUser10FirstName+" "+crmUser10LastName;
		String userName6=crmUser11FirstName+" "+crmUser11LastName;
		String userName7=crmUser12FirstName+" "+crmUser12LastName;
		String userName8=crmUser13FirstName+" "+crmUser13LastName;
		String userName9=crmUser14FirstName+" "+crmUser14LastName;
		String userName10=crmUser15FirstName+" "+crmUser15LastName;
		String userName11=crmUser16FirstName+" "+crmUser16LastName;


		String[] companiesTaggedName= {ATE_TaggedCompanyName14,ATE_TaggedCompanyName15,ATE_TaggedCompanyName16,ATE_TaggedCompanyName17,ATE_TaggedCompanyName18,ATE_TaggedCompanyName19,ATE_TaggedCompanyName20,ATE_TaggedCompanyName21,ATE_TaggedCompanyName22,ATE_TaggedCompanyName23,ATE_TaggedCompanyName24,ATE_TaggedCompanyName25,ATE_TaggedCompanyName26,ATE_TaggedCompanyName27,ATE_TaggedCompanyName28,ATE_TaggedCompanyName29};
		String[] companiesTaggedTimeReference= {ATE_TaggedCompanyTimeReference14,ATE_TaggedCompanyTimeReference15,ATE_TaggedCompanyTimeReference16,ATE_TaggedCompanyTimeReference17,ATE_TaggedCompanyTimeReference18,ATE_TaggedCompanyTimeReference19,ATE_TaggedCompanyTimeReference20,ATE_TaggedCompanyTimeReference21,ATE_TaggedCompanyTimeReference22,ATE_TaggedCompanyTimeReference23,ATE_TaggedCompanyTimeReference24,ATE_TaggedCompanyTimeReference25,ATE_TaggedCompanyTimeReference26,ATE_TaggedCompanyTimeReference27,ATE_TaggedCompanyTimeReference28,ATE_TaggedCompanyTimeReference29};

		String[] dealTaggedName= {ATE_TaggedDealName6,ATE_TaggedDealName7,ATE_TaggedDealName8,ATE_TaggedDealName9,ATE_TaggedDealName10,ATE_TaggedDealName11,ATE_TaggedDealName12,ATE_TaggedDealName13,ATE_TaggedDealName14,ATE_TaggedDealName15,ATE_TaggedDealName16,ATE_TaggedDealName3,ATE_TaggedDealName4,ATE_TaggedDealName5};
		String[] dealTaggedTimeReference= {ATE_TaggedDealTimeReference6,ATE_TaggedDealTimeReference7,ATE_TaggedDealTimeReference8,ATE_TaggedDealTimeReference9,ATE_TaggedDealTimeReference10,ATE_TaggedDealTimeReference11,ATE_TaggedDealTimeReference12,ATE_TaggedDealTimeReference13,ATE_TaggedDealTimeReference14,ATE_TaggedDealTimeReference15,ATE_TaggedDealTimeReference16,ATE_TaggedDealTimeReference3,ATE_TaggedDealTimeReference4,ATE_TaggedDealTimeReference5};

		String[] peopleTagName={ATE_TaggedPeopleName9,ATE_TaggedPeopleName10,ATE_TaggedPeopleName11,ATE_TaggedPeopleName12,ATE_TaggedPeopleName13,ATE_TaggedPeopleName14,ATE_TaggedPeopleName15,ATE_TaggedPeopleName16,ATE_TaggedPeopleName3,ATE_TaggedPeopleName4};
		String[] peopleTaggedTimeReference={ATE_TaggedPeopleTimeReference9,ATE_TaggedPeopleTimeReference10,ATE_TaggedPeopleTimeReference11,ATE_TaggedPeopleTimeReference12,ATE_TaggedPeopleTimeReference13,ATE_TaggedPeopleTimeReference14,ATE_TaggedPeopleTimeReference15,ATE_TaggedPeopleTimeReference16,ATE_TaggedPeopleTimeReference3,ATE_TaggedPeopleTimeReference4};

		String contactSectionName[]= {ATE_ContactName10,ATE_ContactName11,ATE_ContactName12,ATE_ContactName13,ATE_ContactName14,ATE_ContactName15,ATE_ContactName16,ATE_ContactName17,ATE_ContactName18,ATE_ContactName19,ATE_ContactName20,ATE_ContactName21};
		String contactSectionTitle[]= {ATE_ContactTitle10,ATE_ContactTitle11,ATE_ContactTitle12,ATE_ContactTitle13,ATE_ContactTitle14,ATE_ContactTitle15,ATE_ContactTitle16,ATE_ContactTitle17,ATE_ContactTitle18,ATE_ContactTitle19,ATE_ContactTitle20,ATE_ContactTitle21};
		String contactSectionDeal[]= {ATE_ContactDeal10,ATE_ContactDeal11,ATE_ContactDeal12,ATE_ContactDeal13,ATE_ContactDeal14,ATE_ContactDeal15,ATE_ContactDeal16,ATE_ContactDeal17,ATE_ContactDeal18,ATE_ContactDeal19,ATE_ContactDeal20,ATE_ContactDeal21};
		String contactSectionMeetingAndCall[]= {ATE_ContactMeetingAndCall10,ATE_ContactMeetingAndCall11,ATE_ContactMeetingAndCall12,ATE_ContactMeetingAndCall13,ATE_ContactMeetingAndCall14,ATE_ContactMeetingAndCall15,ATE_ContactMeetingAndCall16,ATE_ContactMeetingAndCall17,ATE_ContactMeetingAndCall18,ATE_ContactMeetingAndCall19,ATE_ContactMeetingAndCall20,ATE_ContactMeetingAndCall21};
		String contactSectionEmail[]= {ATE_ContactEmail10,ATE_ContactEmail11,ATE_ContactEmail12,ATE_ContactEmail13,ATE_ContactEmail14,ATE_ContactEmail15,ATE_ContactEmail16,ATE_ContactEmail17,ATE_ContactEmail18,ATE_ContactEmail19,ATE_ContactEmail20,ATE_ContactEmail21};

		String[] iconType= {ATE_ATActivityType6,ATE_ATActivityType7,ATE_ATActivityType8,ATE_ATActivityType9,ATE_ATActivityType10,ATE_ATActivityType11,ATE_ATActivityType12,ATE_ATActivityType13,ATE_ATActivityType14,ATE_ATActivityType15,ATE_ATActivityType16,ATE_ATActivityType17,ATE_ATActivityType18,ATE_ATActivityType19,ATE_ATActivityType20,
				ATE_ATActivityType21,ATE_ATActivityType22,ATE_ATActivityType23,ATE_ATActivityType24,ATE_ATActivityType25,ATE_ATActivityType26,ATE_ATActivityType27,ATE_ATActivityType28,ATE_ATActivityType29,ATE_ATActivityType30,ATE_ATActivityType31,ATE_ATActivityType32,ATE_ATActivityType33,ATE_ATActivityType34,ATE_ATActivityType35,ATE_ATActivityType36,ATE_ATActivityType37,ATE_ATActivityType38,ATE_ATActivityType39,ATE_ATActivityType40,
				ATE_ATActivityType41,ATE_ATActivityType42,ATE_ATActivityType43,ATE_ATActivityType44,ATE_ATActivityType45,ATE_ATActivityType46,ATE_ATActivityType47,ATE_ATActivityType48,ATE_ATActivityType49,ATE_ATActivityType50,ATE_ATActivityType51,ATE_ATActivityType52,ATE_ATActivityType53,ATE_ATActivityType54,ATE_ATActivityType55,ATE_ATActivityType56,ATE_ATActivityType57,ATE_ATActivityType58,ATE_ATActivityType59,ATE_ATActivityType60,
				ATE_ATActivityType61,ATE_ATActivityType62,ATE_ATActivityType63,ATE_ATActivityType64,ATE_ATActivityType65,ATE_ATActivityType66,ATE_ATActivityType67,ATE_ATActivityType68,ATE_ATActivityType69,ATE_ATActivityType70,ATE_ATActivityType71,ATE_ATActivityType72,ATE_ATActivityType73,ATE_ATActivityType74,ATE_ATActivityType75};

		String[] subjectName= {ATE_ATSubject6,ATE_ATSubject7,ATE_ATSubject8,ATE_ATSubject9,ATE_ATSubject10,ATE_ATSubject11,ATE_ATSubject12,ATE_ATSubject13,ATE_ATSubject14,ATE_ATSubject15,ATE_ATSubject16,ATE_ATSubject17,ATE_ATSubject18,ATE_ATSubject19,ATE_ATSubject20,
				ATE_ATSubject21,ATE_ATSubject22,ATE_ATSubject23,ATE_ATSubject24,ATE_ATSubject25,ATE_ATSubject26,ATE_ATSubject27,ATE_ATSubject28,ATE_ATSubject29,ATE_ATSubject30,ATE_ATSubject31,ATE_ATSubject32,ATE_ATSubject33,ATE_ATSubject34,ATE_ATSubject35,ATE_ATSubject36,ATE_ATSubject37,ATE_ATSubject38,ATE_ATSubject39,ATE_ATSubject40,
				ATE_ATSubject41,ATE_ATSubject42,ATE_ATSubject43,ATE_ATSubject44,ATE_ATSubject45,ATE_ATSubject46,ATE_ATSubject47,ATE_ATSubject48,ATE_ATSubject49,ATE_ATSubject50,ATE_ATSubject51,ATE_ATSubject52,ATE_ATSubject53,ATE_ATSubject54,ATE_ATSubject55,ATE_ATSubject56,ATE_ATSubject57,ATE_ATSubject58,ATE_ATSubject59,ATE_ATSubject60,
				ATE_ATSubject61,ATE_ATSubject62,ATE_ATSubject63,ATE_ATSubject64,ATE_ATSubject65,ATE_ATSubject66,ATE_ATSubject67,ATE_ATSubject68,ATE_ATSubject69,ATE_ATSubject70,ATE_ATSubject71,ATE_ATSubject72,ATE_ATSubject73,ATE_ATSubject74,ATE_ATSubject75};

		String[] details= {ATE_ATNotes6,ATE_ATNotes7,ATE_ATNotes8,ATE_ATNotes9,ATE_ATNotes10,ATE_ATNotes11,ATE_ATNotes12,ATE_ATNotes13,ATE_ATNotes14,ATE_ATNotes15,ATE_ATNotes16,ATE_ATNotes17,ATE_ATNotes18,ATE_ATNotes19,ATE_ATNotes20,
				ATE_ATNotes21,ATE_ATNotes22,ATE_ATNotes23,ATE_ATNotes24,ATE_ATNotes25,ATE_ATNotes26,ATE_ATNotes27,ATE_ATNotes28,ATE_ATNotes29,ATE_ATNotes30,ATE_ATNotes31,ATE_ATNotes32,ATE_ATNotes33,ATE_ATNotes34,ATE_ATNotes35,ATE_ATNotes36,ATE_ATNotes37,ATE_ATNotes38,ATE_ATNotes39,ATE_ATNotes40,
				ATE_ATNotes41,ATE_ATNotes42,ATE_ATNotes43,ATE_ATNotes44,ATE_ATNotes45,ATE_ATNotes46,ATE_ATNotes47,ATE_ATNotes48,ATE_ATNotes49,ATE_ATNotes50,ATE_ATNotes51,ATE_ATNotes52,ATE_ATNotes53,ATE_ATNotes54,ATE_ATNotes55,ATE_ATNotes56,ATE_ATNotes57,ATE_ATNotes58,ATE_ATNotes59,ATE_ATNotes60,
				ATE_ATNotes61,ATE_ATNotes62,ATE_ATNotes63,ATE_ATNotes64,ATE_ATNotes65,ATE_ATNotes66,ATE_ATNotes67,ATE_ATNotes68,ATE_ATNotes69,ATE_ATNotes70,ATE_ATNotes71,ATE_ATNotes72,ATE_ATNotes73,ATE_ATNotes74,ATE_ATNotes75};

		String[] date= {ATE_AdvanceDueDate5,ATE_AdvanceDueDate6,ATE_AdvanceDueDate7,ATE_AdvanceDueDate8,ATE_AdvanceDueDate9,ATE_AdvanceDueDate10,ATE_AdvanceDueDate11,ATE_AdvanceDueDate12,ATE_AdvanceDueDate13,ATE_AdvanceDueDate14,ATE_AdvanceDueDate15,ATE_AdvanceDueDate16,ATE_AdvanceDueDate17,ATE_AdvanceDueDate18,ATE_AdvanceDueDate19,ATE_AdvanceDueDate20,
				ATE_AdvanceDueDate21,ATE_AdvanceDueDate22,ATE_AdvanceDueDate23,ATE_AdvanceDueDate24,ATE_AdvanceDueDate25,ATE_AdvanceDueDate26,ATE_AdvanceDueDate27,ATE_AdvanceDueDate28,ATE_AdvanceDueDate29,ATE_AdvanceDueDate30,ATE_AdvanceDueDate31,ATE_AdvanceDueDate32,ATE_AdvanceDueDate33,ATE_AdvanceDueDate34,ATE_AdvanceDueDate35,ATE_AdvanceDueDate36,ATE_AdvanceDueDate37,ATE_AdvanceDueDate38,ATE_AdvanceDueDate39,ATE_AdvanceDueDate40,
				ATE_AdvanceDueDate41,ATE_AdvanceDueDate42,ATE_AdvanceDueDate43,ATE_AdvanceDueDate44,ATE_AdvanceDueDate45,ATE_AdvanceStartDate2,ATE_AdvanceStartDate3,ATE_AdvanceStartDate4,ATE_AdvanceStartDate5,ATE_AdvanceStartDate6,ATE_AdvanceStartDate7,ATE_AdvanceStartDate8,ATE_AdvanceStartDate9,ATE_AdvanceStartDate10,ATE_AdvanceStartDate11,ATE_AdvanceStartDate12,ATE_AdvanceStartDate13,ATE_AdvanceStartDate14,ATE_AdvanceStartDate15,ATE_AdvanceStartDate16,ATE_AdvanceStartDate17,ATE_AdvanceStartDate18,ATE_AdvanceStartDate19,ATE_AdvanceStartDate20,
				ATE_AdvanceStartDate21,ATE_AdvanceStartDate22,ATE_AdvanceStartDate23,ATE_AdvanceStartDate24,ATE_AdvanceStartDate25,ATE_AdvanceStartDate26,ATE_AdvanceStartDate27,ATE_AdvanceStartDate28,ATE_AdvanceStartDate29,ATE_AdvanceStartDate30,ATE_AdvanceStartDate31};

		String[] userData= {ATE_User1,ATE_User2,ATE_User3,ATE_User4,ATE_User5,ATE_User6,ATE_User7,ATE_User8,ATE_User9,ATE_User10,ATE_User11,ATE_User12,ATE_User13,ATE_User14,ATE_User15,ATE_User16,ATE_User17,ATE_User18,ATE_User19,ATE_User20,
				ATE_User21,ATE_User22,ATE_User23,ATE_User24,ATE_User25,ATE_User26,ATE_User27,ATE_User28,ATE_User29,ATE_User30,ATE_User31,ATE_User32,ATE_User33,ATE_User34,ATE_User35,ATE_User36,ATE_User37,ATE_User38,ATE_User39,ATE_User40,
				ATE_User41,ATE_User42,ATE_User43,ATE_User44,ATE_User45,ATE_User46,ATE_User47,ATE_User48,ATE_User49,ATE_User50,ATE_User51,ATE_User52,ATE_User53,ATE_User54,ATE_User55,ATE_User56,ATE_User57,ATE_User58,ATE_User59,ATE_User60,
				ATE_User61,ATE_User62,ATE_User63,ATE_User64,ATE_User65,ATE_User66,ATE_User67,ATE_User68,ATE_User69,ATE_User70};

		String[] user=new String[userData.length];

		for(int i=0; i<userData.length; i++)
		{
			if(userData[i].toLowerCase().trim().equals("pe user 1"))
			{
				user[i]=userName1;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 2"))
			{
				user[i]=userName2;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 3"))
			{
				user[i]=userName3;
			}else if(userData[i].toLowerCase().trim().equals("pe user 4"))
			{
				user[i]=userName4;
			}else if(userData[i].toLowerCase().trim().equals("pe user 5"))
			{
				user[i]=userName5;
			}else if(userData[i].toLowerCase().trim().equals("pe user 6"))
			{
				user[i]=userName6;
			}else if(userData[i].toLowerCase().trim().equals("pe user 7"))
			{
				user[i]=userName7;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 8"))
			{
				user[i]=userName8;
			}else if(userData[i].toLowerCase().trim().equals("pe user 9"))
			{
				user[i]=userName9;
			}else if(userData[i].toLowerCase().trim().equals("pe user 10"))
			{
				user[i]=userName10;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 11"))
			{
				user[i]=userName11;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}
		}
		for(int i=0; i<subjectName.length; i++)
		{
			System.out.println("Icon - "+iconType[i]+" ||  subject - "+subjectName[i]+"  ||  Date - "+date[i] );
		}


		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	


					ArrayList<String> result=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, peopleTagName, peopleTaggedTimeReference, dealTaggedName, dealTaggedTimeReference);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,  "The record name and Time reference are not verifed "+result, YesNo.No);
						sa.assertTrue(false,  "The record name and Time reference are not verifed "+result);
					}
					ArrayList<String> result1=bp.verifyRecordOnContactSectionAcuity(contactSectionName, contactSectionTitle, contactSectionDeal, contactSectionMeetingAndCall, contactSectionEmail);
					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on contact section in Acuity contact", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on contact section in Acuity for contact : "+result1, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on contact section in Acuity for contact :  "+result1);
					}
					if(bp.verifyCountOfRelatedAssociationOnTaggedPopupOnInteractionSctionOfFirstRecord())
					{
						log(LogStatus.INFO, "The count of Tagged record have been verified on tagged popup", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The count of Tagged record is not verified on tagged popup", YesNo.No);
						sa.assertTrue(false, "The count of Tagged record is not verified on tagged popup");
					}

					if(CommonLib.clickUsingJavaScript(driver, bp.getViewAllBtnOnIntration(20), "View All button"))
					{
						log(LogStatus.INFO, "Clicked on View All button of Interaction section", YesNo.No);
						ArrayList<String> result2=bp.verifyRecordsonInteractionsViewAllPopup(iconType,date, subjectName, details, user, subjectName);
						if(result2.isEmpty())
						{
							log(LogStatus.INFO, "The records have been verified on interaction popup in Acuity", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records are not verified on interaction popup in Acuity : "+result2, YesNo.No);
							sa.assertTrue(false,  "The records are not verified on interaction popup in Acuity :  "+result2);
						}

						String xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
						WebElement ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
						if(clickUsingJavaScript(driver, ele, "close button"))
						{
							log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
							sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on View All button of Interaction section", YesNo.No);
						sa.assertTrue(false,  "Not able to click on View All button of Interaction section" );
					}					
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}


	@Parameters({ "projectName" })
	@Test
	public void ATETc0032_VerifyConnectionPopupOnIntermediaryAccountPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATERecord3;
		String contactName=ATE_Contact1;

		String xPath;
		WebElement ele;

		String userName1=crmUser6FirstName+" "+crmUser6LastName;
		String userName2=crmUser7FirstName+" "+crmUser7LastName;
		String userName3=crmUser8FirstName+" "+crmUser8LastName;
		String userName4=crmUser9FirstName+" "+crmUser9LastName;
		String userName5=crmUser10FirstName+" "+crmUser10LastName;
		String userName6=crmUser11FirstName+" "+crmUser11LastName;
		String userName7=crmUser12FirstName+" "+crmUser12LastName;
		String userName8=crmUser13FirstName+" "+crmUser13LastName;
		String userName9=crmUser14FirstName+" "+crmUser14LastName;
		String userName10=crmUser15FirstName+" "+crmUser15LastName;
		String userName11=crmUser16FirstName+" "+crmUser16LastName;


		String[] connectionEmail= {ATE_ConnectionEmail3,ATE_ConnectionEmail4,ATE_ConnectionEmail5,ATE_ConnectionEmail6,ATE_ConnectionEmail7,ATE_ConnectionEmail8,ATE_ConnectionEmail9,ATE_ConnectionEmail10,ATE_ConnectionEmail11,ATE_ConnectionEmail12};
		String[] connectionMeetingAndCall= {ATE_ConnectionMeetingAndCall3,ATE_ConnectionMeetingAndCall4,ATE_ConnectionMeetingAndCall5,ATE_ConnectionMeetingAndCall6,ATE_ConnectionMeetingAndCall7,ATE_ConnectionMeetingAndCall8,ATE_ConnectionMeetingAndCall9,ATE_ConnectionMeetingAndCall10,ATE_ConnectionMeetingAndCall11,ATE_ConnectionMeetingAndCall12};
		String[] connectionDeal= {ATE_ConnectionDeals3,ATE_ConnectionDeals4,ATE_ConnectionDeals5,ATE_ConnectionDeals6,ATE_ConnectionDeals7,ATE_ConnectionDeals8,ATE_ConnectionDeals9,ATE_ConnectionDeals10,ATE_ConnectionDeals11,ATE_ConnectionDeals12};
		String[] connectionTitle= {ATE_ConnectionTitle3,ATE_ConnectionTitle4,ATE_ConnectionTitle5,ATE_ConnectionTitle6,ATE_ConnectionTitle7,ATE_ConnectionTitle8,ATE_ConnectionTitle9,ATE_ConnectionTitle10,ATE_ConnectionTitle11,ATE_ConnectionTitle12};
		String[] userData= {ATE_ConnectionTeamMember3,ATE_ConnectionTeamMember4,ATE_ConnectionTeamMember5,ATE_ConnectionTeamMember6,ATE_ConnectionTeamMember7,ATE_ConnectionTeamMember8,ATE_ConnectionTeamMember9,ATE_ConnectionTeamMember10,ATE_ConnectionTeamMember11,ATE_ConnectionTeamMember12};

		String[] user=new String[userData.length];

		for(int i=0; i<userData.length; i++)
		{
			if(userData[i].toLowerCase().trim().equals("pe user 1"))
			{
				user[i]=userName1;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 2"))
			{
				user[i]=userName2;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 3"))
			{
				user[i]=userName3;
			}else if(userData[i].toLowerCase().trim().equals("pe user 4"))
			{
				user[i]=userName4;
			}else if(userData[i].toLowerCase().trim().equals("pe user 5"))
			{
				user[i]=userName5;
			}else if(userData[i].toLowerCase().trim().equals("pe user 6"))
			{
				user[i]=userName6;
			}else if(userData[i].toLowerCase().trim().equals("pe user 7"))
			{
				user[i]=userName7;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 8"))
			{
				user[i]=userName8;
			}else if(userData[i].toLowerCase().trim().equals("pe user 9"))
			{
				user[i]=userName9;
			}else if(userData[i].toLowerCase().trim().equals("pe user 10"))
			{
				user[i]=userName10;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 11"))
			{
				user[i]=userName11;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}
		}

		String[] icon= {ATE_ATActivityType46,ATE_ATActivityType47,ATE_ATActivityType48,ATE_ATActivityType49,ATE_ATActivityType50,ATE_ATActivityType51,ATE_ATActivityType52,ATE_ATActivityType53,ATE_ATActivityType54,ATE_ATActivityType55,ATE_ATActivityType56,ATE_ATActivityType57,ATE_ATActivityType58,ATE_ATActivityType59,ATE_ATActivityType60,
				ATE_ATActivityType61,ATE_ATActivityType62,ATE_ATActivityType63,ATE_ATActivityType64,ATE_ATActivityType65,ATE_ATActivityType66,ATE_ATActivityType67,ATE_ATActivityType68,ATE_ATActivityType69,ATE_ATActivityType70,ATE_ATActivityType71,ATE_ATActivityType72,ATE_ATActivityType73,ATE_ATActivityType74,ATE_ATActivityType75,ATE_ATActivityType2,ATE_ATActivityType16,ATE_ATActivityType17,ATE_ATActivityType36,ATE_ATActivityType37};

		String[] subjectName= {ATE_ATSubject46,ATE_ATSubject47,ATE_ATSubject48,ATE_ATSubject49,ATE_ATSubject50,ATE_ATSubject51,ATE_ATSubject52,ATE_ATSubject53,ATE_ATSubject54,ATE_ATSubject55,ATE_ATSubject56,ATE_ATSubject57,ATE_ATSubject58,ATE_ATSubject59,ATE_ATSubject60,
				ATE_ATSubject61,ATE_ATSubject62,ATE_ATSubject63,ATE_ATSubject64,ATE_ATSubject65,ATE_ATSubject66,ATE_ATSubject67,ATE_ATSubject68,ATE_ATSubject69,ATE_ATSubject70,ATE_ATSubject71,ATE_ATSubject72,ATE_ATSubject73,ATE_ATSubject74,ATE_ATSubject75,ATE_ATSubject2,ATE_ATSubject16,ATE_ATSubject17,ATE_ATSubject36,ATE_ATSubject37};

		String[] date= {ATE_AdvanceStartDate2,ATE_AdvanceStartDate3,ATE_AdvanceStartDate4,ATE_AdvanceStartDate5,ATE_AdvanceStartDate6,ATE_AdvanceStartDate7,ATE_AdvanceStartDate8,ATE_AdvanceStartDate9,ATE_AdvanceStartDate10,ATE_AdvanceStartDate11,ATE_AdvanceStartDate12,ATE_AdvanceStartDate13,ATE_AdvanceStartDate14,ATE_AdvanceStartDate15,ATE_AdvanceStartDate16,ATE_AdvanceStartDate17,ATE_AdvanceStartDate18,ATE_AdvanceStartDate19,ATE_AdvanceStartDate20,
				ATE_AdvanceStartDate21,ATE_AdvanceStartDate22,ATE_AdvanceStartDate23,ATE_AdvanceStartDate24,ATE_AdvanceStartDate25,ATE_AdvanceStartDate26,ATE_AdvanceStartDate27,ATE_AdvanceStartDate28,ATE_AdvanceStartDate29,ATE_AdvanceStartDate30,ATE_AdvanceStartDate31,ATE_AdvanceDueDate2,ATE_AdvanceDueDate16,ATE_AdvanceDueDate17,ATE_AdvanceDueDate35,ATE_AdvanceDueDate36};

		String[] details= {ATE_ATNotes46,ATE_ATNotes47,ATE_ATNotes48,ATE_ATNotes49,ATE_ATNotes50,ATE_ATNotes51,ATE_ATNotes52,ATE_ATNotes53,ATE_ATNotes54,ATE_ATNotes55,ATE_ATNotes56,ATE_ATNotes57,ATE_ATNotes58,ATE_ATNotes59,ATE_ATNotes60,
				ATE_ATNotes61,ATE_ATNotes62,ATE_ATNotes63,ATE_ATNotes64,ATE_ATNotes65,ATE_ATNotes66,ATE_ATNotes67,ATE_ATNotes68,ATE_ATNotes69,ATE_ATNotes70,ATE_ATNotes71,ATE_ATNotes72,ATE_ATNotes73,ATE_ATNotes74,ATE_ATNotes75,ATE_ATNotes2,ATE_ATNotes16,ATE_ATNotes17,ATE_ATNotes36,ATE_ATNotes37};

		String[] users= {ATE_User46,ATE_User47,ATE_User48,ATE_User49,ATE_User50,ATE_User51,ATE_User52,ATE_User53,ATE_User54,ATE_User55,ATE_User56,ATE_User57,ATE_User58,ATE_User59,ATE_User60,
				ATE_User61,ATE_User62,ATE_User63,ATE_User64,ATE_User65,ATE_User66,ATE_User67,ATE_User68,ATE_User69,ATE_User70,ATE_User71,ATE_User72,ATE_User73,ATE_User74,ATE_User75,ATE_User2,ATE_User16,ATE_User17,ATE_User36,ATE_User37};

		String[] meetingAndCallUser=new String[users.length];

		for(int i=0; i<users.length; i++)
		{
			if(users[i].toLowerCase().trim().equals("pe user 1"))
			{
				meetingAndCallUser[i]=userName1;
			}
			else if(users[i].toLowerCase().trim().equals("pe user 2"))
			{
				meetingAndCallUser[i]=userName2;
			}
			else if(users[i].toLowerCase().trim().equals("pe user 3"))
			{
				meetingAndCallUser[i]=userName3;
			}else if(users[i].toLowerCase().trim().equals("pe user 4"))
			{
				meetingAndCallUser[i]=userName4;
			}else if(users[i].toLowerCase().trim().equals("pe user 5"))
			{
				meetingAndCallUser[i]=userName5;
			}else if(users[i].toLowerCase().trim().equals("pe user 6"))
			{
				meetingAndCallUser[i]=userName6;
			}else if(users[i].toLowerCase().trim().equals("pe user 7"))
			{
				meetingAndCallUser[i]=userName7;
			}
			else if(users[i].toLowerCase().trim().equals("pe user 8"))
			{
				meetingAndCallUser[i]=userName8;
			}else if(users[i].toLowerCase().trim().equals("pe user 9"))
			{
				meetingAndCallUser[i]=userName9;
			}else if(users[i].toLowerCase().trim().equals("pe user 10"))
			{
				meetingAndCallUser[i]=userName10;
			}
			else if(users[i].toLowerCase().trim().equals("pe user 11"))
			{
				meetingAndCallUser[i]=userName11;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}
		}		

		String[] icon1= {ATE_ATActivityType3,ATE_ATActivityType46,ATE_ATActivityType55,ATE_ATActivityType57,ATE_ATActivityType58,ATE_ATActivityType60,ATE_ATActivityType61,ATE_ATActivityType63,ATE_ATActivityType64,ATE_ATActivityType66,ATE_ATActivityType67,ATE_ATActivityType69,ATE_ATActivityType70,ATE_ATActivityType72,ATE_ATActivityType73,ATE_ATActivityType74,ATE_ATActivityType50,ATE_ATActivityType52,ATE_ATActivityType53,ATE_ATActivityType18,ATE_ATActivityType19,ATE_ATActivityType38,ATE_ATActivityType39};

		String[] subjectName1= {ATE_ATSubject3,ATE_ATSubject46,ATE_ATSubject55,ATE_ATSubject57,ATE_ATSubject58,ATE_ATSubject60,ATE_ATSubject61,ATE_ATSubject63,ATE_ATSubject64,ATE_ATSubject66,ATE_ATSubject67,ATE_ATSubject69,ATE_ATSubject70,ATE_ATSubject72,ATE_ATSubject73,ATE_ATSubject74,ATE_ATSubject50,ATE_ATSubject52,ATE_ATSubject53,ATE_ATSubject18,ATE_ATSubject19,ATE_ATSubject38,ATE_ATSubject39};

		String[] date1= {ATE_AdvanceStartDate1,ATE_AdvanceStartDate2,ATE_AdvanceStartDate11,ATE_AdvanceStartDate13,ATE_AdvanceStartDate14,ATE_AdvanceStartDate16,ATE_AdvanceStartDate17,ATE_AdvanceStartDate19,ATE_AdvanceStartDate20,ATE_AdvanceStartDate22,ATE_AdvanceStartDate23,ATE_AdvanceStartDate25,ATE_AdvanceStartDate26,ATE_AdvanceStartDate28,ATE_AdvanceStartDate29,ATE_AdvanceStartDate30,ATE_AdvanceStartDate6,ATE_AdvanceStartDate8,ATE_AdvanceStartDate9,ATE_AdvanceDueDate17,ATE_AdvanceDueDate18,ATE_AdvanceDueDate37,ATE_AdvanceDueDate38};

		String[] details1= {ATE_ATNotes3,ATE_ATNotes46,ATE_ATNotes55,ATE_ATNotes57,ATE_ATNotes58,ATE_ATNotes60,ATE_ATNotes61,ATE_ATNotes63,ATE_ATNotes64,ATE_ATNotes66,ATE_ATNotes67,ATE_ATNotes69,ATE_ATNotes70,ATE_ATNotes72,ATE_ATNotes73,ATE_ATNotes74,ATE_ATNotes50,ATE_ATNotes52,ATE_ATNotes53,ATE_ATNotes18,ATE_ATNotes19,ATE_ATNotes38,ATE_ATNotes39};

		String[] users1= {ATE_User3,ATE_User46,ATE_User55,ATE_User57,ATE_User58,ATE_User60,ATE_User61,ATE_User63,ATE_User64,ATE_User66,ATE_User67,ATE_User69,ATE_User70,ATE_User72,ATE_User73,ATE_User74,ATE_User50,ATE_User52,ATE_User53,ATE_User18,ATE_User19,ATE_User38,ATE_User39};

		String[] meetingAndCallUser1=new String[users1.length];

		for(int i=0; i<users1.length; i++)
		{
			if(users1[i].toLowerCase().trim().equals("pe user 1"))
			{
				meetingAndCallUser1[i]=userName1;
			}
			else if(users1[i].toLowerCase().trim().equals("pe user 2"))
			{
				meetingAndCallUser1[i]=userName2;
			}
			else if(users1[i].toLowerCase().trim().equals("pe user 3"))
			{
				meetingAndCallUser1[i]=userName3;
			}else if(users1[i].toLowerCase().trim().equals("pe user 4"))
			{
				meetingAndCallUser1[i]=userName4;
			}else if(users1[i].toLowerCase().trim().equals("pe user 5"))
			{
				meetingAndCallUser1[i]=userName5;
			}else if(users1[i].toLowerCase().trim().equals("pe user 6"))
			{
				meetingAndCallUser1[i]=userName6;
			}else if(users1[i].toLowerCase().trim().equals("pe user 7"))
			{
				meetingAndCallUser1[i]=userName7;
			}
			else if(users1[i].toLowerCase().trim().equals("pe user 8"))
			{
				meetingAndCallUser1[i]=userName8;
			}else if(users1[i].toLowerCase().trim().equals("pe user 9"))
			{
				meetingAndCallUser1[i]=userName9;
			}else if(users1[i].toLowerCase().trim().equals("pe user 10"))
			{
				meetingAndCallUser1[i]=userName10;
			}
			else if(users1[i].toLowerCase().trim().equals("pe user 11"))
			{
				meetingAndCallUser1[i]=userName11;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}
		}
		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

					ThreadSleep(5000);
					ArrayList<String> result=bp.verifyRecordOnConnectionsPopUpOfContactInAcuity(contactName,user,connectionTitle,connectionDeal,connectionMeetingAndCall,connectionEmail);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The records on Connection popup have been verified", YesNo.No);	
					}
					else
					{
						log(LogStatus.ERROR, "The records on Connection popup are not verified. "+result, YesNo.No);	
						sa.assertTrue(false,  "The records on Connection popup are not verified. "+result);
					}

					if (CommonLib.clickUsingJavaScript(driver, bp.contactNameUserIconButton(contactName, 30), "Contact Name: " + contactName,
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on connection icon of contact : " + contactName, YesNo.No);

						xPath="//a[text()='"+userName1+"']/ancestor::tr//td[@data-label='Meetings and Calls']//button";
						ele=FindElement(driver, xPath, "Count of "+userName1+" on contact section", action.SCROLLANDBOOLEAN, 20);
						if(CommonLib.clickUsingJavaScript(driver, ele,"Count of "+userName1+" on contact section" , action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, "clicked on count of "+userName1,YesNo.No);
							ArrayList<String> result4=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity(icon, date, subjectName, details, meetingAndCallUser);
							if(result4.isEmpty())
							{
								log(LogStatus.INFO, "Records have been verified on meeting and call popup",YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Records are not verifid on meeting and call popup" +result4,YesNo.No);
								sa.assertTrue(false, "Records are not verifid on meeting and call popup" +result4);
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on count of "+contactName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on count of "+contactName);
						}		
						ThreadSleep(3000);
						xPath="//a[text()='"+userName2+"']/ancestor::tr//td[@data-label='Meetings and Calls']//button";
						ele=FindElement(driver, xPath, "Count of "+userName2+" on contact section", action.SCROLLANDBOOLEAN, 20);
						if(CommonLib.clickUsingJavaScript(driver, ele,"Count of "+userName2+" on contact section" , action.SCROLLANDBOOLEAN))
						{
							ThreadSleep(2000);
							log(LogStatus.INFO, "clicked on count of "+userName2,YesNo.No);
							ArrayList<String> result5=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity(icon1, date1, subjectName1, details1, meetingAndCallUser1);
							if(result5.isEmpty())
							{
								log(LogStatus.INFO, "Records have been verified on meeting and call popup",YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Records are not verifid on meeting and call popup user 2" +result5,YesNo.No);
								sa.assertTrue(false, "Records are not verifid on meeting and call popup user 2" +result5);
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on count of "+contactName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on count of "+contactName);
						}		


					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on connection icon of contact : " + contactName, YesNo.No);
						sa.assertTrue(false, "Not able to click on connection icon of contact : " + contactName);
					}
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}



	@Parameters({ "projectName" })
	@Test
	public void ATETc0033_VerifyIntermediaryContactAcuityTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATE_Contact1;
		String contactName=ATE_Contact1;

		String xPath;
		WebElement ele;

		String userName1=crmUser6FirstName+" "+crmUser6LastName;
		String userName2=crmUser7FirstName+" "+crmUser7LastName;
		String userName3=crmUser8FirstName+" "+crmUser8LastName;
		String userName4=crmUser9FirstName+" "+crmUser9LastName;
		String userName5=crmUser10FirstName+" "+crmUser10LastName;
		String userName6=crmUser11FirstName+" "+crmUser11LastName;
		String userName7=crmUser12FirstName+" "+crmUser12LastName;
		String userName8=crmUser13FirstName+" "+crmUser13LastName;
		String userName9=crmUser14FirstName+" "+crmUser14LastName;
		String userName10=crmUser15FirstName+" "+crmUser15LastName;
		String userName11=crmUser16FirstName+" "+crmUser16LastName;

		String[] companiesTaggedName= {ATE_TaggedCompanyName14,ATE_TaggedCompanyName15,ATE_TaggedCompanyName16,ATE_TaggedCompanyName17,ATE_TaggedCompanyName18,ATE_TaggedCompanyName19,ATE_TaggedCompanyName20,ATE_TaggedCompanyName21,ATE_TaggedCompanyName22,ATE_TaggedCompanyName23,ATE_TaggedCompanyName24,ATE_TaggedCompanyName25,ATE_TaggedCompanyName26,ATE_TaggedCompanyName27,ATE_TaggedCompanyName28,ATE_TaggedCompanyName29};
		String[] companiesTaggedTimeReference= {ATE_TaggedCompanyTimeReference14,ATE_TaggedCompanyTimeReference15,ATE_TaggedCompanyTimeReference16,ATE_TaggedCompanyTimeReference17,ATE_TaggedCompanyTimeReference18,ATE_TaggedCompanyTimeReference19,ATE_TaggedCompanyTimeReference20,ATE_TaggedCompanyTimeReference21,ATE_TaggedCompanyTimeReference22,ATE_TaggedCompanyTimeReference23,ATE_TaggedCompanyTimeReference24,ATE_TaggedCompanyTimeReference25,ATE_TaggedCompanyTimeReference26,ATE_TaggedCompanyTimeReference27,ATE_TaggedCompanyTimeReference28,ATE_TaggedCompanyTimeReference29};

		String[] dealTaggedName= {ATE_TaggedDealName6,ATE_TaggedDealName7,ATE_TaggedDealName8,ATE_TaggedDealName9,ATE_TaggedDealName10,ATE_TaggedDealName11,ATE_TaggedDealName12,ATE_TaggedDealName13,ATE_TaggedDealName14,ATE_TaggedDealName15,ATE_TaggedDealName16,ATE_TaggedDealName3,ATE_TaggedDealName4,ATE_TaggedDealName5};
		String[] dealTaggedTimeReference= {ATE_TaggedDealTimeReference6,ATE_TaggedDealTimeReference7,ATE_TaggedDealTimeReference8,ATE_TaggedDealTimeReference9,ATE_TaggedDealTimeReference10,ATE_TaggedDealTimeReference11,ATE_TaggedDealTimeReference12,ATE_TaggedDealTimeReference13,ATE_TaggedDealTimeReference14,ATE_TaggedDealTimeReference15,ATE_TaggedDealTimeReference16,ATE_TaggedDealTimeReference3,ATE_TaggedDealTimeReference4,ATE_TaggedDealTimeReference5};

		String[] peopleTagName={ATE_TaggedPeopleName9,ATE_TaggedPeopleName10,ATE_TaggedPeopleName11,ATE_TaggedPeopleName12,ATE_TaggedPeopleName13,ATE_TaggedPeopleName14,ATE_TaggedPeopleName15,ATE_TaggedPeopleName16,ATE_TaggedPeopleName3,ATE_TaggedPeopleName4};
		String[] peopleTaggedTimeReference={ATE_TaggedPeopleTimeReference9,ATE_TaggedPeopleTimeReference10,ATE_TaggedPeopleTimeReference11,ATE_TaggedPeopleTimeReference12,ATE_TaggedPeopleTimeReference13,ATE_TaggedPeopleTimeReference14,ATE_TaggedPeopleTimeReference15,ATE_TaggedPeopleTimeReference16,ATE_TaggedPeopleTimeReference3,ATE_TaggedPeopleTimeReference4};




		String[] connectionEmail= {ATE_ConnectionEmail3,ATE_ConnectionEmail4,ATE_ConnectionEmail5,ATE_ConnectionEmail6,ATE_ConnectionEmail7,ATE_ConnectionEmail8,ATE_ConnectionEmail9,ATE_ConnectionEmail10,ATE_ConnectionEmail11,ATE_ConnectionEmail12};
		String[] connectionMeetingAndCall= {ATE_ConnectionMeetingAndCall3,ATE_ConnectionMeetingAndCall4,ATE_ConnectionMeetingAndCall5,ATE_ConnectionMeetingAndCall6,ATE_ConnectionMeetingAndCall7,ATE_ConnectionMeetingAndCall8,ATE_ConnectionMeetingAndCall9,ATE_ConnectionMeetingAndCall10,ATE_ConnectionMeetingAndCall11,ATE_ConnectionMeetingAndCall12};
		String[] connectionDeal= {ATE_ConnectionDeals3,ATE_ConnectionDeals4,ATE_ConnectionDeals5,ATE_ConnectionDeals6,ATE_ConnectionDeals7,ATE_ConnectionDeals8,ATE_ConnectionDeals9,ATE_ConnectionDeals10,ATE_ConnectionDeals11,ATE_ConnectionDeals12};
		String[] connectionTitle= {ATE_ConnectionTitle3,ATE_ConnectionTitle4,ATE_ConnectionTitle5,ATE_ConnectionTitle6,ATE_ConnectionTitle7,ATE_ConnectionTitle8,ATE_ConnectionTitle9,ATE_ConnectionTitle10,ATE_ConnectionTitle11,ATE_ConnectionTitle12};
		String[] userData= {ATE_ConnectionTeamMember3,ATE_ConnectionTeamMember4,ATE_ConnectionTeamMember5,ATE_ConnectionTeamMember6,ATE_ConnectionTeamMember7,ATE_ConnectionTeamMember8,ATE_ConnectionTeamMember9,ATE_ConnectionTeamMember10,ATE_ConnectionTeamMember11,ATE_ConnectionTeamMember12};

		String[] user=new String[userData.length];

		for(int i=0; i<userData.length; i++)
		{
			if(userData[i].toLowerCase().trim().equals("pe user 1"))
			{
				user[i]=userName1;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 2"))
			{
				user[i]=userName2;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 3"))
			{
				user[i]=userName3;
			}else if(userData[i].toLowerCase().trim().equals("pe user 4"))
			{
				user[i]=userName4;
			}else if(userData[i].toLowerCase().trim().equals("pe user 5"))
			{
				user[i]=userName5;
			}else if(userData[i].toLowerCase().trim().equals("pe user 6"))
			{
				user[i]=userName6;
			}else if(userData[i].toLowerCase().trim().equals("pe user 7"))
			{
				user[i]=userName7;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 8"))
			{
				user[i]=userName8;
			}else if(userData[i].toLowerCase().trim().equals("pe user 9"))
			{
				user[i]=userName9;
			}else if(userData[i].toLowerCase().trim().equals("pe user 10"))
			{
				user[i]=userName10;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 11"))
			{
				user[i]=userName11;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}
		}

		String[] icon= {ATE_ATActivityType46,ATE_ATActivityType47,ATE_ATActivityType48,ATE_ATActivityType49,ATE_ATActivityType50,ATE_ATActivityType51,ATE_ATActivityType52,ATE_ATActivityType53,ATE_ATActivityType54,ATE_ATActivityType55,ATE_ATActivityType56,ATE_ATActivityType57,ATE_ATActivityType58,ATE_ATActivityType59,ATE_ATActivityType60,
				ATE_ATActivityType61,ATE_ATActivityType62,ATE_ATActivityType63,ATE_ATActivityType64,ATE_ATActivityType65,ATE_ATActivityType66,ATE_ATActivityType67,ATE_ATActivityType68,ATE_ATActivityType69,ATE_ATActivityType70,ATE_ATActivityType71,ATE_ATActivityType72,ATE_ATActivityType73,ATE_ATActivityType74,ATE_ATActivityType75,ATE_ATActivityType2,ATE_ATActivityType16,ATE_ATActivityType17,ATE_ATActivityType36,ATE_ATActivityType37};

		String[] subjectName= {ATE_ATSubject46,ATE_ATSubject47,ATE_ATSubject48,ATE_ATSubject49,ATE_ATSubject50,ATE_ATSubject51,ATE_ATSubject52,ATE_ATSubject53,ATE_ATSubject54,ATE_ATSubject55,ATE_ATSubject56,ATE_ATSubject57,ATE_ATSubject58,ATE_ATSubject59,ATE_ATSubject60,
				ATE_ATSubject61,ATE_ATSubject62,ATE_ATSubject63,ATE_ATSubject64,ATE_ATSubject65,ATE_ATSubject66,ATE_ATSubject67,ATE_ATSubject68,ATE_ATSubject69,ATE_ATSubject70,ATE_ATSubject71,ATE_ATSubject72,ATE_ATSubject73,ATE_ATSubject74,ATE_ATSubject75,ATE_ATSubject2,ATE_ATSubject16,ATE_ATSubject17,ATE_ATSubject36,ATE_ATSubject37};

		String[] date= {ATE_AdvanceStartDate2,ATE_AdvanceStartDate3,ATE_AdvanceStartDate4,ATE_AdvanceStartDate5,ATE_AdvanceStartDate6,ATE_AdvanceStartDate7,ATE_AdvanceStartDate8,ATE_AdvanceStartDate9,ATE_AdvanceStartDate10,ATE_AdvanceStartDate11,ATE_AdvanceStartDate12,ATE_AdvanceStartDate13,ATE_AdvanceStartDate14,ATE_AdvanceStartDate15,ATE_AdvanceStartDate16,ATE_AdvanceStartDate17,ATE_AdvanceStartDate18,ATE_AdvanceStartDate19,ATE_AdvanceStartDate20,
				ATE_AdvanceStartDate21,ATE_AdvanceStartDate22,ATE_AdvanceStartDate23,ATE_AdvanceStartDate24,ATE_AdvanceStartDate25,ATE_AdvanceStartDate26,ATE_AdvanceStartDate27,ATE_AdvanceStartDate28,ATE_AdvanceStartDate29,ATE_AdvanceStartDate30,ATE_AdvanceStartDate31,ATE_AdvanceDueDate2,ATE_AdvanceDueDate16,ATE_AdvanceDueDate17,ATE_AdvanceDueDate35,ATE_AdvanceDueDate36};

		String[] details= {ATE_ATNotes46,ATE_ATNotes47,ATE_ATNotes48,ATE_ATNotes49,ATE_ATNotes50,ATE_ATNotes51,ATE_ATNotes52,ATE_ATNotes53,ATE_ATNotes54,ATE_ATNotes55,ATE_ATNotes56,ATE_ATNotes57,ATE_ATNotes58,ATE_ATNotes59,ATE_ATNotes60,
				ATE_ATNotes61,ATE_ATNotes62,ATE_ATNotes63,ATE_ATNotes64,ATE_ATNotes65,ATE_ATNotes66,ATE_ATNotes67,ATE_ATNotes68,ATE_ATNotes69,ATE_ATNotes70,ATE_ATNotes71,ATE_ATNotes72,ATE_ATNotes73,ATE_ATNotes74,ATE_ATNotes75,ATE_ATNotes2,ATE_ATNotes16,ATE_ATNotes17,ATE_ATNotes36,ATE_ATNotes37};

		String[] users= {ATE_User46,ATE_User47,ATE_User48,ATE_User49,ATE_User50,ATE_User51,ATE_User52,ATE_User53,ATE_User54,ATE_User55,ATE_User56,ATE_User57,ATE_User58,ATE_User59,ATE_User60,
				ATE_User61,ATE_User62,ATE_User63,ATE_User64,ATE_User65,ATE_User66,ATE_User67,ATE_User68,ATE_User69,ATE_User70,ATE_User71,ATE_User72,ATE_User73,ATE_User74,ATE_User75,ATE_User2,ATE_User16,ATE_User17,ATE_User36,ATE_User37};

		String[] meetingAndCallUser=new String[users.length];

		for(int i=0; i<users.length; i++)
		{
			if(users[i].toLowerCase().trim().equals("pe user 1"))
			{
				meetingAndCallUser[i]=userName1;
			}
			else if(users[i].toLowerCase().trim().equals("pe user 2"))
			{
				meetingAndCallUser[i]=userName2;
			}
			else if(users[i].toLowerCase().trim().equals("pe user 3"))
			{
				meetingAndCallUser[i]=userName3;
			}else if(users[i].toLowerCase().trim().equals("pe user 4"))
			{
				meetingAndCallUser[i]=userName4;
			}else if(users[i].toLowerCase().trim().equals("pe user 5"))
			{
				meetingAndCallUser[i]=userName5;
			}else if(users[i].toLowerCase().trim().equals("pe user 6"))
			{
				meetingAndCallUser[i]=userName6;
			}else if(users[i].toLowerCase().trim().equals("pe user 7"))
			{
				meetingAndCallUser[i]=userName7;
			}
			else if(users[i].toLowerCase().trim().equals("pe user 8"))
			{
				meetingAndCallUser[i]=userName8;
			}else if(users[i].toLowerCase().trim().equals("pe user 9"))
			{
				meetingAndCallUser[i]=userName9;
			}else if(users[i].toLowerCase().trim().equals("pe user 10"))
			{
				meetingAndCallUser[i]=userName10;
			}
			else if(users[i].toLowerCase().trim().equals("pe user 11"))
			{
				meetingAndCallUser[i]=userName11;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}
		}		

		String[] icon1= {ATE_ATActivityType3,ATE_ATActivityType46,ATE_ATActivityType55,ATE_ATActivityType57,ATE_ATActivityType58,ATE_ATActivityType60,ATE_ATActivityType61,ATE_ATActivityType63,ATE_ATActivityType64,ATE_ATActivityType66,ATE_ATActivityType67,ATE_ATActivityType69,ATE_ATActivityType70,ATE_ATActivityType72,ATE_ATActivityType73,ATE_ATActivityType74,ATE_ATActivityType50,ATE_ATActivityType52,ATE_ATActivityType53,ATE_ATActivityType18,ATE_ATActivityType19,ATE_ATActivityType38,ATE_ATActivityType39};

		String[] subjectName1= {ATE_ATSubject3,ATE_ATSubject46,ATE_ATSubject55,ATE_ATSubject57,ATE_ATSubject58,ATE_ATSubject60,ATE_ATSubject61,ATE_ATSubject63,ATE_ATSubject64,ATE_ATSubject66,ATE_ATSubject67,ATE_ATSubject69,ATE_ATSubject70,ATE_ATSubject72,ATE_ATSubject73,ATE_ATSubject74,ATE_ATSubject50,ATE_ATSubject52,ATE_ATSubject53,ATE_ATSubject18,ATE_ATSubject19,ATE_ATSubject38,ATE_ATSubject39};

		String[] date1= {ATE_AdvanceStartDate1,ATE_AdvanceStartDate2,ATE_AdvanceStartDate11,ATE_AdvanceStartDate13,ATE_AdvanceStartDate14,ATE_AdvanceStartDate16,ATE_AdvanceStartDate17,ATE_AdvanceStartDate19,ATE_AdvanceStartDate20,ATE_AdvanceStartDate22,ATE_AdvanceStartDate23,ATE_AdvanceStartDate25,ATE_AdvanceStartDate26,ATE_AdvanceStartDate28,ATE_AdvanceStartDate29,ATE_AdvanceStartDate30,ATE_AdvanceStartDate6,ATE_AdvanceStartDate8,ATE_AdvanceStartDate9,ATE_AdvanceDueDate17,ATE_AdvanceDueDate18,ATE_AdvanceDueDate37,ATE_AdvanceDueDate38};

		String[] details1= {ATE_ATNotes3,ATE_ATNotes46,ATE_ATNotes55,ATE_ATNotes57,ATE_ATNotes58,ATE_ATNotes60,ATE_ATNotes61,ATE_ATNotes63,ATE_ATNotes64,ATE_ATNotes66,ATE_ATNotes67,ATE_ATNotes69,ATE_ATNotes70,ATE_ATNotes72,ATE_ATNotes73,ATE_ATNotes74,ATE_ATNotes50,ATE_ATNotes52,ATE_ATNotes53,ATE_ATNotes18,ATE_ATNotes19,ATE_ATNotes38,ATE_ATNotes39};

		String[] users1= {ATE_User3,ATE_User46,ATE_User55,ATE_User57,ATE_User58,ATE_User60,ATE_User61,ATE_User63,ATE_User64,ATE_User66,ATE_User67,ATE_User69,ATE_User70,ATE_User72,ATE_User73,ATE_User74,ATE_User50,ATE_User52,ATE_User53,ATE_User18,ATE_User19,ATE_User38,ATE_User39};

		String[] meetingAndCallUser1=new String[users1.length];

		for(int i=0; i<users1.length; i++)
		{
			if(users1[i].toLowerCase().trim().equals("pe user 1"))
			{
				meetingAndCallUser1[i]=userName1;
			}
			else if(users1[i].toLowerCase().trim().equals("pe user 2"))
			{
				meetingAndCallUser1[i]=userName2;
			}
			else if(users1[i].toLowerCase().trim().equals("pe user 3"))
			{
				meetingAndCallUser1[i]=userName3;
			}else if(users1[i].toLowerCase().trim().equals("pe user 4"))
			{
				meetingAndCallUser1[i]=userName4;
			}else if(users1[i].toLowerCase().trim().equals("pe user 5"))
			{
				meetingAndCallUser1[i]=userName5;
			}else if(users1[i].toLowerCase().trim().equals("pe user 6"))
			{
				meetingAndCallUser1[i]=userName6;
			}else if(users1[i].toLowerCase().trim().equals("pe user 7"))
			{
				meetingAndCallUser1[i]=userName7;
			}
			else if(users1[i].toLowerCase().trim().equals("pe user 8"))
			{
				meetingAndCallUser1[i]=userName8;
			}else if(users1[i].toLowerCase().trim().equals("pe user 9"))
			{
				meetingAndCallUser1[i]=userName9;
			}else if(users1[i].toLowerCase().trim().equals("pe user 10"))
			{
				meetingAndCallUser1[i]=userName10;
			}
			else if(users1[i].toLowerCase().trim().equals("pe user 11"))
			{
				meetingAndCallUser1[i]=userName11;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}
		}

		String[] interactionIconType= {ATE_ATActivityType6,ATE_ATActivityType7,ATE_ATActivityType8,ATE_ATActivityType9,ATE_ATActivityType10,ATE_ATActivityType11,ATE_ATActivityType12,ATE_ATActivityType13,ATE_ATActivityType14,ATE_ATActivityType15,ATE_ATActivityType16,ATE_ATActivityType17,ATE_ATActivityType18,ATE_ATActivityType19,ATE_ATActivityType20,
				ATE_ATActivityType21,ATE_ATActivityType22,ATE_ATActivityType23,ATE_ATActivityType24,ATE_ATActivityType25,ATE_ATActivityType26,ATE_ATActivityType27,ATE_ATActivityType28,ATE_ATActivityType29,ATE_ATActivityType30,ATE_ATActivityType31,ATE_ATActivityType32,ATE_ATActivityType33,ATE_ATActivityType34,ATE_ATActivityType35,ATE_ATActivityType36,ATE_ATActivityType37,ATE_ATActivityType38,ATE_ATActivityType39,ATE_ATActivityType40,
				ATE_ATActivityType41,ATE_ATActivityType42,ATE_ATActivityType43,ATE_ATActivityType44,ATE_ATActivityType45,ATE_ATActivityType46,ATE_ATActivityType47,ATE_ATActivityType48,ATE_ATActivityType49,ATE_ATActivityType50,ATE_ATActivityType51,ATE_ATActivityType52,ATE_ATActivityType53,ATE_ATActivityType54,ATE_ATActivityType55,ATE_ATActivityType56,ATE_ATActivityType57,ATE_ATActivityType58,ATE_ATActivityType59,ATE_ATActivityType60,
				ATE_ATActivityType61,ATE_ATActivityType62,ATE_ATActivityType63,ATE_ATActivityType64,ATE_ATActivityType65,ATE_ATActivityType66,ATE_ATActivityType67,ATE_ATActivityType68,ATE_ATActivityType69,ATE_ATActivityType70,ATE_ATActivityType71,ATE_ATActivityType72,ATE_ATActivityType73,ATE_ATActivityType74,ATE_ATActivityType75};

		String[] interactionSubjectName= {ATE_ATSubject6,ATE_ATSubject7,ATE_ATSubject8,ATE_ATSubject9,ATE_ATSubject10,ATE_ATSubject11,ATE_ATSubject12,ATE_ATSubject13,ATE_ATSubject14,ATE_ATSubject15,ATE_ATSubject16,ATE_ATSubject17,ATE_ATSubject18,ATE_ATSubject19,ATE_ATSubject20,
				ATE_ATSubject21,ATE_ATSubject22,ATE_ATSubject23,ATE_ATSubject24,ATE_ATSubject25,ATE_ATSubject26,ATE_ATSubject27,ATE_ATSubject28,ATE_ATSubject29,ATE_ATSubject30,ATE_ATSubject31,ATE_ATSubject32,ATE_ATSubject33,ATE_ATSubject34,ATE_ATSubject35,ATE_ATSubject36,ATE_ATSubject37,ATE_ATSubject38,ATE_ATSubject39,ATE_ATSubject40,
				ATE_ATSubject41,ATE_ATSubject42,ATE_ATSubject43,ATE_ATSubject44,ATE_ATSubject45,ATE_ATSubject46,ATE_ATSubject47,ATE_ATSubject48,ATE_ATSubject49,ATE_ATSubject50,ATE_ATSubject51,ATE_ATSubject52,ATE_ATSubject53,ATE_ATSubject54,ATE_ATSubject55,ATE_ATSubject56,ATE_ATSubject57,ATE_ATSubject58,ATE_ATSubject59,ATE_ATSubject60,
				ATE_ATSubject61,ATE_ATSubject62,ATE_ATSubject63,ATE_ATSubject64,ATE_ATSubject65,ATE_ATSubject66,ATE_ATSubject67,ATE_ATSubject68,ATE_ATSubject69,ATE_ATSubject70,ATE_ATSubject71,ATE_ATSubject72,ATE_ATSubject73,ATE_ATSubject74,ATE_ATSubject75};

		String[] interactionDetails= {ATE_ATNotes6,ATE_ATNotes7,ATE_ATNotes8,ATE_ATNotes9,ATE_ATNotes10,ATE_ATNotes11,ATE_ATNotes12,ATE_ATNotes13,ATE_ATNotes14,ATE_ATNotes15,ATE_ATNotes16,ATE_ATNotes17,ATE_ATNotes18,ATE_ATNotes19,ATE_ATNotes20,
				ATE_ATNotes21,ATE_ATNotes22,ATE_ATNotes23,ATE_ATNotes24,ATE_ATNotes25,ATE_ATNotes26,ATE_ATNotes27,ATE_ATNotes28,ATE_ATNotes29,ATE_ATNotes30,ATE_ATNotes31,ATE_ATNotes32,ATE_ATNotes33,ATE_ATNotes34,ATE_ATNotes35,ATE_ATNotes36,ATE_ATNotes37,ATE_ATNotes38,ATE_ATNotes39,ATE_ATNotes40,
				ATE_ATNotes41,ATE_ATNotes42,ATE_ATNotes43,ATE_ATNotes44,ATE_ATNotes45,ATE_ATNotes46,ATE_ATNotes47,ATE_ATNotes48,ATE_ATNotes49,ATE_ATNotes50,ATE_ATNotes51,ATE_ATNotes52,ATE_ATNotes53,ATE_ATNotes54,ATE_ATNotes55,ATE_ATNotes56,ATE_ATNotes57,ATE_ATNotes58,ATE_ATNotes59,ATE_ATNotes60,
				ATE_ATNotes61,ATE_ATNotes62,ATE_ATNotes63,ATE_ATNotes64,ATE_ATNotes65,ATE_ATNotes66,ATE_ATNotes67,ATE_ATNotes68,ATE_ATNotes69,ATE_ATNotes70,ATE_ATNotes71,ATE_ATNotes72,ATE_ATNotes73,ATE_ATNotes74,ATE_ATNotes75};

		String[] interactionDate= {ATE_AdvanceDueDate5,ATE_AdvanceDueDate6,ATE_AdvanceDueDate7,ATE_AdvanceDueDate8,ATE_AdvanceDueDate9,ATE_AdvanceDueDate10,ATE_AdvanceDueDate11,ATE_AdvanceDueDate12,ATE_AdvanceDueDate13,ATE_AdvanceDueDate14,ATE_AdvanceDueDate15,ATE_AdvanceDueDate16,ATE_AdvanceDueDate17,ATE_AdvanceDueDate18,ATE_AdvanceDueDate19,ATE_AdvanceDueDate20,
				ATE_AdvanceDueDate21,ATE_AdvanceDueDate22,ATE_AdvanceDueDate23,ATE_AdvanceDueDate24,ATE_AdvanceDueDate25,ATE_AdvanceDueDate26,ATE_AdvanceDueDate27,ATE_AdvanceDueDate28,ATE_AdvanceDueDate29,ATE_AdvanceDueDate30,ATE_AdvanceDueDate31,ATE_AdvanceDueDate32,ATE_AdvanceDueDate33,ATE_AdvanceDueDate34,ATE_AdvanceDueDate35,ATE_AdvanceDueDate36,ATE_AdvanceDueDate37,ATE_AdvanceDueDate38,ATE_AdvanceDueDate39,ATE_AdvanceDueDate40,
				ATE_AdvanceDueDate41,ATE_AdvanceDueDate42,ATE_AdvanceDueDate43,ATE_AdvanceDueDate44,ATE_AdvanceDueDate45,ATE_AdvanceStartDate2,ATE_AdvanceStartDate3,ATE_AdvanceStartDate4,ATE_AdvanceStartDate5,ATE_AdvanceStartDate6,ATE_AdvanceStartDate7,ATE_AdvanceStartDate8,ATE_AdvanceStartDate9,ATE_AdvanceStartDate10,ATE_AdvanceStartDate11,ATE_AdvanceStartDate12,ATE_AdvanceStartDate13,ATE_AdvanceStartDate14,ATE_AdvanceStartDate15,ATE_AdvanceStartDate16,ATE_AdvanceStartDate17,ATE_AdvanceStartDate18,ATE_AdvanceStartDate19,ATE_AdvanceStartDate20,
				ATE_AdvanceStartDate21,ATE_AdvanceStartDate22,ATE_AdvanceStartDate23,ATE_AdvanceStartDate24,ATE_AdvanceStartDate25,ATE_AdvanceStartDate26,ATE_AdvanceStartDate27,ATE_AdvanceStartDate28,ATE_AdvanceStartDate29,ATE_AdvanceStartDate30,ATE_AdvanceStartDate31};

		String[] interactionUserData= {ATE_User1,ATE_User2,ATE_User3,ATE_User4,ATE_User5,ATE_User6,ATE_User7,ATE_User8,ATE_User9,ATE_User10,ATE_User11,ATE_User12,ATE_User13,ATE_User14,ATE_User15,ATE_User16,ATE_User17,ATE_User18,ATE_User19,ATE_User20,
				ATE_User21,ATE_User22,ATE_User23,ATE_User24,ATE_User25,ATE_User26,ATE_User27,ATE_User28,ATE_User29,ATE_User30,ATE_User31,ATE_User32,ATE_User33,ATE_User34,ATE_User35,ATE_User36,ATE_User37,ATE_User38,ATE_User39,ATE_User40,
				ATE_User41,ATE_User42,ATE_User43,ATE_User44,ATE_User45,ATE_User46,ATE_User47,ATE_User48,ATE_User49,ATE_User50,ATE_User51,ATE_User52,ATE_User53,ATE_User54,ATE_User55,ATE_User56,ATE_User57,ATE_User58,ATE_User59,ATE_User60,
				ATE_User61,ATE_User62,ATE_User63,ATE_User64,ATE_User65,ATE_User66,ATE_User67,ATE_User68,ATE_User69,ATE_User70};


		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

					ThreadSleep(5000);

					ArrayList<String> result=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, peopleTagName, peopleTaggedTimeReference, dealTaggedName, dealTaggedTimeReference);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,  "The record name and Time reference are not verifed "+result, YesNo.No);
						sa.assertTrue(false,  "The record name and Time reference are not verifed "+result);
					}


					if(user.length==connectionTitle.length && user.length==connectionDeal.length && user.length==connectionMeetingAndCall.length && user.length==connectionEmail.length)
					{
						for(int i=0; i<user.length; i++)
						{
							ArrayList<String> result2=bp.verifyRecordOnConnectionsSectionInAcuity(recordName, user[i], connectionTitle[i], connectionDeal[i], connectionMeetingAndCall[i], connectionEmail[i]);
							if(result2.isEmpty())
							{
								log(LogStatus.INFO, "The records have been verified on connection section for User: "+user[i], YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "The records are not verified on connection section for User: "+user[i], YesNo.No);
								sa.assertTrue(false,  "The records are not verified on connection section for User: "+user[i]);
							}
						}
					}
					else
					{
						log(LogStatus.ERROR, "The size of User, Title, Deal, Meeting and call, Email are not equal", YesNo.No);
						sa.assertTrue(false,  "The size of User, Title, Deal, Meeting and call, Email are not equal");
					}

					if(bp.verifyCountOfRelatedAssociationOnTaggedPopupOnInteractionSctionOfFirstRecord())
					{
						log(LogStatus.INFO, "The count of Tagged record have been verified on tagged popup", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The count of Tagged record is not verified on tagged popup", YesNo.No);
						sa.assertTrue(false, "The count of Tagged record is not verified on tagged popup");
					}


					if(CommonLib.clickUsingJavaScript(driver, bp.getViewAllBtnOnIntration(20), "View All button"))
					{
						log(LogStatus.INFO, "Clicked on View All button of Interaction section", YesNo.No);
						ArrayList<String> result2=bp.verifyRecordsonInteractionsViewAllPopup(interactionIconType,interactionDate, interactionSubjectName, interactionDetails, interactionUserData, interactionSubjectName);
						if(result2.isEmpty())
						{
							log(LogStatus.INFO, "The records have been verified on interaction popup in Acuity", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records are not verified on interaction popup in Acuity : "+result2, YesNo.No);
							sa.assertTrue(false,  "The records are not verified on interaction popup in Acuity :  "+result2);
						}

						xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
						ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
						if(clickUsingJavaScript(driver, ele, "close button"))
						{
							log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
							sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on View All button of Interaction section", YesNo.No);
						sa.assertTrue(false,  "Not able to click on View All button of Interaction section" );
					}


					xPath="//a[text()='"+userName1+"']/ancestor::tr//td[@data-label='Meetings and Calls']//button";
					ele=FindElement(driver, xPath, "Count of "+contactName+" on contact section", action.SCROLLANDBOOLEAN, 20);
					if(CommonLib.clickUsingJavaScript(driver, ele,"Count of "+contactName+" on contact section" , action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on count of "+contactName,YesNo.No);
						ArrayList<String> result4=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity(icon, date, subjectName, details, meetingAndCallUser);
						if(result4.isEmpty())
						{
							log(LogStatus.INFO, "Records have been verified on meeting and call popup",YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Records are not verifid on meeting and call popup" +result4,YesNo.No);
							sa.assertTrue(false, "Records are not verifid on meeting and call popup" +result4);
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on count of "+contactName,YesNo.No);
						sa.assertTrue(false,  "Not able to click on count of "+contactName);
					}		
					ThreadSleep(3000);
					xPath="//a[text()='"+userName2+"']/ancestor::tr//td[@data-label='Meetings and Calls']//button";
					ele=FindElement(driver, xPath, "Count of "+userName2+" on contact section", action.SCROLLANDBOOLEAN, 20);
					if(CommonLib.clickUsingJavaScript(driver, ele,"Count of "+userName2+" on contact section" , action.SCROLLANDBOOLEAN))
					{
						ThreadSleep(2000);
						log(LogStatus.INFO, "clicked on count of "+userName2,YesNo.No);
						ArrayList<String> result5=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity(icon1, date1, subjectName1, details1, meetingAndCallUser1);
						if(result5.isEmpty())
						{
							log(LogStatus.INFO, "Records have been verified on meeting and call popup",YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Records are not verifid on meeting and call popup user 2" +result5,YesNo.No);
							sa.assertTrue(false, "Records are not verifid on meeting and call popup user 2" +result5);
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on count of "+contactName,YesNo.No);
						sa.assertTrue(false,  "Not able to click on count of "+contactName);
					}		
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}



	@Parameters({ "projectName" })
	@Test
	public void ATETc0034_VerifyCountFunctionalityVerificationOnAccountPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATERecord3;
		String contactName=ATE_Contact1;

		String xPath;
		WebElement ele;

		String userName1=crmUser6FirstName+" "+crmUser6LastName;
		String userName2=crmUser7FirstName+" "+crmUser7LastName;
		String userName3=crmUser8FirstName+" "+crmUser8LastName;
		String userName4=crmUser9FirstName+" "+crmUser9LastName;
		String userName5=crmUser10FirstName+" "+crmUser10LastName;
		String userName6=crmUser11FirstName+" "+crmUser11LastName;
		String userName7=crmUser12FirstName+" "+crmUser12LastName;
		String userName8=crmUser13FirstName+" "+crmUser13LastName;
		String userName9=crmUser14FirstName+" "+crmUser14LastName;
		String userName10=crmUser15FirstName+" "+crmUser15LastName;
		String userName11=crmUser16FirstName+" "+crmUser16LastName;

		String companyTagName=ATE_TaggedCompanyName16;
		String companyTagTimeReferenceCount=ATE_TaggedCompanyTimeReference16;

		String companyTagName1=ATE_TaggedCompanyName17;
		String companyTagTimeReferenceCount1=ATE_TaggedCompanyTimeReference17;

		String peopleTagName=ATE_TaggedPeopleName11;
		String peopleTagTimeReferenceCount=ATE_TaggedPeopleTimeReference11;

		String dealTagName=ATE_TaggedDealName16;
		String dealTagTimeReferenceCount=ATE_TaggedDealTimeReference16;

		IconType[] companyIcon= {IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event};
		String[] companySubjectName= {ATE_ATSubject26,ATE_ATSubject27,ATE_ATSubject28,ATE_ATSubject29,ATE_ATSubject35,ATE_ATSubject36,ATE_ATSubject37,ATE_ATSubject38,ATE_ATSubject39,ATE_ATSubject40, ATE_ATSubject41, ATE_ATSubject42, ATE_ATSubject43, ATE_ATSubject44, ATE_ATSubject45,ATE_ATSubject56,ATE_ATSubject57,ATE_ATSubject58,ATE_ATSubject59,ATE_ATSubject65,ATE_ATSubject66,ATE_ATSubject67,ATE_ATSubject68,ATE_ATSubject69,ATE_ATSubject70,ATE_ATSubject71,ATE_ATSubject72,ATE_ATSubject73,ATE_ATSubject74,ATE_ATSubject75};
		String[] companyDetails= {ATE_ATNotes26,ATE_ATNotes27,ATE_ATNotes28,ATE_ATNotes29,ATE_ATNotes35,ATE_ATNotes36,ATE_ATNotes37,ATE_ATNotes38,ATE_ATNotes39,ATE_ATNotes40,ATE_ATNotes41,ATE_ATNotes42,ATE_ATNotes43,ATE_ATNotes44,ATE_ATNotes45,ATE_ATNotes56,ATE_ATNotes57,ATE_ATNotes58,ATE_ATNotes59,ATE_ATNotes65,ATE_ATNotes66,ATE_ATNotes67,ATE_ATNotes68,ATE_ATNotes69,ATE_ATNotes70,ATE_ATNotes71,ATE_ATNotes72,ATE_ATNotes73,ATE_ATNotes74,ATE_ATNotes75};
		String[] companyDueDate= {ATE_AdvanceDueDate25,ATE_AdvanceDueDate26,ATE_AdvanceDueDate27,ATE_AdvanceDueDate28,ATE_AdvanceDueDate34,ATE_AdvanceDueDate35,ATE_AdvanceDueDate36,ATE_AdvanceDueDate37,ATE_AdvanceDueDate38,ATE_AdvanceDueDate39,ATE_AdvanceDueDate40,ATE_AdvanceDueDate41,ATE_AdvanceDueDate42,ATE_AdvanceDueDate43,ATE_AdvanceDueDate44,ATE_AdvanceStartDate12,ATE_AdvanceStartDate13,ATE_AdvanceStartDate14,ATE_AdvanceStartDate15,ATE_AdvanceStartDate21,ATE_AdvanceStartDate22,ATE_AdvanceStartDate23,ATE_AdvanceStartDate24,ATE_AdvanceStartDate25,ATE_AdvanceStartDate26,ATE_AdvanceStartDate27,ATE_AdvanceStartDate28,ATE_AdvanceStartDate29,ATE_AdvanceStartDate30,ATE_AdvanceStartDate31};
		String[] userData= {ATE_User26,ATE_User27,ATE_User28,ATE_User29,ATE_User35,ATE_User36,ATE_User37,ATE_User38,ATE_User39,ATE_User40,ATE_User41,ATE_User42,ATE_User43,ATE_User44,ATE_User45,ATE_User56,ATE_User57,ATE_User58,ATE_User59,ATE_User65,ATE_User66,ATE_User67,ATE_User68,ATE_User69,ATE_User70,ATE_User71,ATE_User72,ATE_User73,ATE_User74,ATE_User75};	
		String[] companyUsers=new String[userData.length];

		for(int i=0; i<userData.length; i++)
		{
			if(userData[i].toLowerCase().trim().equals("pe user 1"))
			{
				companyUsers[i]=userName1;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 2"))
			{
				companyUsers[i]=userName2;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 3"))
			{
				companyUsers[i]=userName3;
			}else if(userData[i].toLowerCase().trim().equals("pe user 4"))
			{
				companyUsers[i]=userName4;
			}else if(userData[i].toLowerCase().trim().equals("pe user 5"))
			{
				companyUsers[i]=userName5;
			}else if(userData[i].toLowerCase().trim().equals("pe user 6"))
			{
				companyUsers[i]=userName6;
			}else if(userData[i].toLowerCase().trim().equals("pe user 7"))
			{
				companyUsers[i]=userName7;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 8"))
			{
				companyUsers[i]=userName8;
			}else if(userData[i].toLowerCase().trim().equals("pe user 9"))
			{
				companyUsers[i]=userName9;
			}else if(userData[i].toLowerCase().trim().equals("pe user 10"))
			{
				companyUsers[i]=userName10;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 11"))
			{
				companyUsers[i]=userName11;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}
		}

		IconType[] companyIcon1= {IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Task,IconType.Task};
		String[] companySubjectName1= {ATE_ATSubject2,ATE_ATSubject26,ATE_ATSubject27,ATE_ATSubject28,ATE_ATSubject29,ATE_ATSubject30,ATE_ATSubject31,ATE_ATSubject32,ATE_ATSubject33,ATE_ATSubject34,ATE_ATSubject35,ATE_ATSubject36,ATE_ATSubject37,ATE_ATSubject38,ATE_ATSubject39,ATE_ATSubject46,ATE_ATSubject47,ATE_ATSubject48,ATE_ATSubject49,ATE_ATSubject50,ATE_ATSubject51,ATE_ATSubject52,ATE_ATSubject53,ATE_ATSubject55,ATE_ATSubject56,ATE_ATSubject57,ATE_ATSubject58,ATE_ATSubject59,ATE_ATSubject60,ATE_ATSubject61,ATE_ATSubject62,ATE_ATSubject63,ATE_ATSubject64,ATE_ATSubject65,ATE_ATSubject66,ATE_ATSubject67,ATE_ATSubject68,ATE_ATSubject69,ATE_ATSubject70,ATE_ATSubject71,ATE_ATSubject72,ATE_ATSubject73,ATE_ATSubject74,ATE_ATSubject75,ATE_ATSubject4,ATE_ATSubject5};
		String[] companyDetails1= {ATE_ATNotes2,ATE_ATNotes26,ATE_ATNotes27,ATE_ATNotes28,ATE_ATNotes29,ATE_ATNotes30,ATE_ATNotes31,ATE_ATNotes32,ATE_ATNotes33,ATE_ATNotes34,ATE_ATNotes35,ATE_ATNotes36,ATE_ATNotes37,ATE_ATNotes38,ATE_ATNotes39,ATE_ATNotes46,ATE_ATNotes47,ATE_ATNotes48,ATE_ATNotes49,ATE_ATNotes50,ATE_ATNotes51,ATE_ATNotes52,ATE_ATNotes53,ATE_ATNotes55,ATE_ATNotes56,ATE_ATNotes57,ATE_ATNotes58,ATE_ATNotes59,ATE_ATNotes60,ATE_ATNotes61,ATE_ATNotes62,ATE_ATNotes63,ATE_ATNotes64,ATE_ATNotes65,ATE_ATNotes66,ATE_ATNotes67,ATE_ATNotes68,ATE_ATNotes69,ATE_ATNotes70,ATE_ATNotes71,ATE_ATNotes72,ATE_ATNotes73,ATE_ATNotes74,ATE_ATNotes75,ATE_ATNotes4,ATE_ATNotes5};
		String[] companyDueDate1= {ATE_AdvanceDueDate2,ATE_AdvanceDueDate25,ATE_AdvanceDueDate26,ATE_AdvanceDueDate27,ATE_AdvanceDueDate28,ATE_AdvanceDueDate29,ATE_AdvanceDueDate30,ATE_AdvanceDueDate31,ATE_AdvanceDueDate32,ATE_AdvanceDueDate33,ATE_AdvanceDueDate34,ATE_AdvanceDueDate35,ATE_AdvanceDueDate36,ATE_AdvanceDueDate37,ATE_AdvanceDueDate38,ATE_AdvanceStartDate2,ATE_AdvanceStartDate3,ATE_AdvanceStartDate4,ATE_AdvanceStartDate5,ATE_AdvanceStartDate6,ATE_AdvanceStartDate7,ATE_AdvanceStartDate8,ATE_AdvanceStartDate9,ATE_AdvanceStartDate11,ATE_AdvanceStartDate12,ATE_AdvanceStartDate13,ATE_AdvanceStartDate14,ATE_AdvanceStartDate15,ATE_AdvanceStartDate16,ATE_AdvanceStartDate17,ATE_AdvanceStartDate18,ATE_AdvanceStartDate19,ATE_AdvanceStartDate20,ATE_AdvanceStartDate21,ATE_AdvanceStartDate22,ATE_AdvanceStartDate23,ATE_AdvanceStartDate24,ATE_AdvanceStartDate25,ATE_AdvanceStartDate26,ATE_AdvanceStartDate27,ATE_AdvanceStartDate28,ATE_AdvanceStartDate29,ATE_AdvanceStartDate30,ATE_AdvanceStartDate31,ATE_AdvanceDueDate3,ATE_AdvanceDueDate4};
		String[] userData1= {ATE_User2,ATE_User26,ATE_User27,ATE_User28,ATE_User29,ATE_User30,ATE_User31,ATE_User32,ATE_User33,ATE_User34,ATE_User35,ATE_User36,ATE_User37,ATE_User38,ATE_User39,ATE_User46,ATE_User47,ATE_User48,ATE_User49,ATE_User50,ATE_User51,ATE_User52,ATE_User53,ATE_User55,ATE_User56,ATE_User57,ATE_User58,ATE_User59,ATE_User60,ATE_User61,ATE_User62,ATE_User63,ATE_User64,ATE_User65,ATE_User66,ATE_User67,ATE_User68,ATE_User69,ATE_User70,ATE_User71,ATE_User72,ATE_User73,ATE_User74,ATE_User75,ATE_User4,ATE_User5};	

		String[] companyUsers1=new String[userData1.length];

		for(int i=0; i<userData1.length; i++)
		{
			if(userData1[i].toLowerCase().trim().equals("pe user 1"))
			{
				companyUsers1[i]=userName1;
			}
			else if(userData1[i].toLowerCase().trim().equals("pe user 2"))
			{
				companyUsers1[i]=userName2;
			}
			else if(userData1[i].toLowerCase().trim().equals("pe user 3"))
			{
				companyUsers1[i]=userName3;
			}else if(userData1[i].toLowerCase().trim().equals("pe user 4"))
			{
				companyUsers1[i]=userName4;
			}else if(userData1[i].toLowerCase().trim().equals("pe user 5"))
			{
				companyUsers1[i]=userName5;
			}else if(userData1[i].toLowerCase().trim().equals("pe user 6"))
			{
				companyUsers1[i]=userName6;
			}else if(userData1[i].toLowerCase().trim().equals("pe user 7"))
			{
				companyUsers1[i]=userName7;
			}
			else if(userData1[i].toLowerCase().trim().equals("pe user 8"))
			{
				companyUsers1[i]=userName8;
			}else if(userData1[i].toLowerCase().trim().equals("pe user 9"))
			{
				companyUsers1[i]=userName9;
			}else if(userData1[i].toLowerCase().trim().equals("pe user 10"))
			{
				companyUsers1[i]=userName10;
			}
			else if(userData1[i].toLowerCase().trim().equals("pe user 11"))
			{
				companyUsers1[i]=userName11;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}
		}
		
		
		IconType[] companyIcon2= {IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Event,IconType.Event,IconType.Event,IconType.Event};
		String[] companySubjectName2= {ATE_ATSubject26,ATE_ATSubject27,ATE_ATSubject28,ATE_ATSubject29,ATE_ATSubject56,ATE_ATSubject57,ATE_ATSubject58,ATE_ATSubject59};
		String[] companyDetails2= {ATE_ATNotes26,ATE_ATNotes27,ATE_ATNotes28,ATE_ATNotes29,ATE_ATNotes56,ATE_ATNotes57,ATE_ATNotes58,ATE_ATNotes59};
		String[] companyDueDate2= {ATE_AdvanceDueDate25,ATE_AdvanceDueDate26,ATE_AdvanceDueDate27,ATE_AdvanceDueDate28,ATE_AdvanceStartDate12,ATE_AdvanceStartDate13,ATE_AdvanceStartDate14,ATE_AdvanceStartDate15};
		String[] userData2= {ATE_User26,ATE_User27,ATE_User28,ATE_User29,ATE_User56,ATE_User57,ATE_User58,ATE_User59};	

		String[] companyUsers2=new String[userData2.length];

		for(int i=0; i<userData2.length; i++)
		{
			if(userData2[i].toLowerCase().trim().equals("pe user 1"))
			{
				companyUsers2[i]=userName1;
			}
			else if(userData2[i].toLowerCase().trim().equals("pe user 2"))
			{
				companyUsers2[i]=userName2;
			}
			else if(userData2[i].toLowerCase().trim().equals("pe user 3"))
			{
				companyUsers2[i]=userName3;
			}else if(userData2[i].toLowerCase().trim().equals("pe user 4"))
			{
				companyUsers2[i]=userName4;
			}else if(userData2[i].toLowerCase().trim().equals("pe user 5"))
			{
				companyUsers2[i]=userName5;
			}else if(userData2[i].toLowerCase().trim().equals("pe user 6"))
			{
				companyUsers2[i]=userName6;
			}else if(userData2[i].toLowerCase().trim().equals("pe user 7"))
			{
				companyUsers2[i]=userName7;
			}
			else if(userData2[i].toLowerCase().trim().equals("pe user 8"))
			{
				companyUsers2[i]=userName8;
			}else if(userData2[i].toLowerCase().trim().equals("pe user 9"))
			{
				companyUsers2[i]=userName9;
			}else if(userData2[i].toLowerCase().trim().equals("pe user 10"))
			{
				companyUsers2[i]=userName10;
			}
			else if(userData2[i].toLowerCase().trim().equals("pe user 11"))
			{
				companyUsers2[i]=userName11;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}
		}
		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

					ThreadSleep(5000);

					if (click(driver, bp.getTaggedRecordName("Companies", 30), "Companies tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Companies tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("Companies", companyTagName, companyTagTimeReferenceCount,30), companyTagName+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+companyTagName,YesNo.No);

							ArrayList<String> result=bp.verifyRecordsonInteractionsViewAllPopup(companyIcon, companyDueDate, companySubjectName, companyDetails, companyUsers, companySubjectName);
							if(result.isEmpty())
							{
								log(LogStatus.INFO, "All records on Interaction card have been verified for "+companyTagName+" record", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "All records on Interaction card are not verified for "+companyTagName+" record " +result, YesNo.No);
								sa.assertTrue(false,  "All records on Interaction card are not verified for "+companyTagName+" record "+result);
							}
							xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
							ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
							if(clickUsingJavaScript(driver, ele, "close button"))
							{
								log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
								sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+companyTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+companyTagName);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Companies tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Companies tab name");
					}
					
					
					if (click(driver, bp.getTaggedRecordName("Companies", 30), "Companies tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Companies tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("Companies", companyTagName1, companyTagTimeReferenceCount1,30), companyTagName1+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+companyTagName1,YesNo.No);

							ArrayList<String> result2=bp.verifyRecordsonInteractionsViewAllPopup(companyIcon, companyDueDate, companySubjectName, companyDetails, companyUsers, companySubjectName);
							if(result2.isEmpty())
							{
								log(LogStatus.INFO, "All records on Interaction card have been verified for "+companyTagName1+" record", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "All records on Interaction card are not verified for "+companyTagName1+" record " +result2, YesNo.No);
								sa.assertTrue(false,  "All records on Interaction card are not verified for "+companyTagName1+" record "+result2);
							}
							xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
							ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
							if(clickUsingJavaScript(driver, ele, "close button"))
							{
								log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
								sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+companyTagName1,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+companyTagName1);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Companies tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Companies tab name");
					}
					
					
					if (click(driver, bp.getTaggedRecordName("People", 30), "People tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on People tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("People", peopleTagName, peopleTagTimeReferenceCount,30), peopleTagName+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+peopleTagName,YesNo.No);

							ArrayList<String> result3=bp.verifyRecordsonInteractionsViewAllPopup(companyIcon1, companyDueDate1, companySubjectName1, companyDetails1, companyUsers1, companySubjectName1);
							if(result3.isEmpty())
							{
								log(LogStatus.INFO, "All records on Interaction card have been verified for "+peopleTagName+" record", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "All records on Interaction card are not verified for "+peopleTagName+" record " +result3, YesNo.No);
								sa.assertTrue(false,  "All records on Interaction card are not verified for "+peopleTagName+" record "+result3);
							}
							xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
							ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
							if(clickUsingJavaScript(driver, ele, "close button"))
							{
								log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
								sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+peopleTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+peopleTagName);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on People tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on People tab name");
					}
					
					if (click(driver, bp.getTaggedRecordName("Deals", 30), "Deals tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Deals tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("Deals", dealTagName, dealTagTimeReferenceCount,30), dealTagName+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+dealTagName,YesNo.No);

							ArrayList<String> result3=bp.verifyRecordsonInteractionsViewAllPopup(companyIcon2, companyDueDate2, companySubjectName2, companyDetails2, companyUsers2, companySubjectName2);
							if(result3.isEmpty())
							{
								log(LogStatus.INFO, "All records on Interaction card have been verified for "+dealTagName+" record", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "All records on Interaction card are not verified for "+dealTagName+" record " +result3, YesNo.No);
								sa.assertTrue(false,  "All records on Interaction card are not verified for "+dealTagName+" record "+result3);
							}
							xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
							ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
							if(clickUsingJavaScript(driver, ele, "close button"))
							{
								log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
								sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+dealTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+dealTagName);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Deals tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Deals tab name");
					}
					

				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}



	@Parameters({ "projectName" })
	@Test
	public void ATETc0035_VerifyCountFunctionalityVerificationOnContactPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATE_Contact1;

		String xPath;
		WebElement ele;

		String userName1=crmUser6FirstName+" "+crmUser6LastName;
		String userName2=crmUser7FirstName+" "+crmUser7LastName;
		String userName3=crmUser8FirstName+" "+crmUser8LastName;
		String userName4=crmUser9FirstName+" "+crmUser9LastName;
		String userName5=crmUser10FirstName+" "+crmUser10LastName;
		String userName6=crmUser11FirstName+" "+crmUser11LastName;
		String userName7=crmUser12FirstName+" "+crmUser12LastName;
		String userName8=crmUser13FirstName+" "+crmUser13LastName;
		String userName9=crmUser14FirstName+" "+crmUser14LastName;
		String userName10=crmUser15FirstName+" "+crmUser15LastName;
		String userName11=crmUser16FirstName+" "+crmUser16LastName;

		String companyTagName=ATE_TaggedCompanyName16;
		String companyTagTimeReferenceCount=ATE_TaggedCompanyTimeReference16;

		String companyTagName1=ATE_TaggedCompanyName17;
		String companyTagTimeReferenceCount1=ATE_TaggedCompanyTimeReference17;
		
		String dealTagName=ATE_TaggedDealName8;
		String dealTagTimeReferenceCount=ATE_TaggedDealTimeReference8;

		IconType[] companyIcon= {IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event};
		String[] companySubjectName= {ATE_ATSubject26,ATE_ATSubject27,ATE_ATSubject28,ATE_ATSubject29,ATE_ATSubject35,ATE_ATSubject36,ATE_ATSubject37,ATE_ATSubject38,ATE_ATSubject39,ATE_ATSubject40, ATE_ATSubject41, ATE_ATSubject42, ATE_ATSubject43, ATE_ATSubject44, ATE_ATSubject45,ATE_ATSubject56,ATE_ATSubject57,ATE_ATSubject58,ATE_ATSubject59,ATE_ATSubject65,ATE_ATSubject66,ATE_ATSubject67,ATE_ATSubject68,ATE_ATSubject69,ATE_ATSubject70,ATE_ATSubject71,ATE_ATSubject72,ATE_ATSubject73,ATE_ATSubject74,ATE_ATSubject75};
		String[] companyDetails= {ATE_ATNotes26,ATE_ATNotes27,ATE_ATNotes28,ATE_ATNotes29,ATE_ATNotes35,ATE_ATNotes36,ATE_ATNotes37,ATE_ATNotes38,ATE_ATNotes39,ATE_ATNotes40,ATE_ATNotes41,ATE_ATNotes42,ATE_ATNotes43,ATE_ATNotes44,ATE_ATNotes45,ATE_ATNotes56,ATE_ATNotes57,ATE_ATNotes58,ATE_ATNotes59,ATE_ATNotes65,ATE_ATNotes66,ATE_ATNotes67,ATE_ATNotes68,ATE_ATNotes69,ATE_ATNotes70,ATE_ATNotes71,ATE_ATNotes72,ATE_ATNotes73,ATE_ATNotes74,ATE_ATNotes75};
		String[] companyDueDate= {ATE_AdvanceDueDate25,ATE_AdvanceDueDate26,ATE_AdvanceDueDate27,ATE_AdvanceDueDate28,ATE_AdvanceDueDate34,ATE_AdvanceDueDate35,ATE_AdvanceDueDate36,ATE_AdvanceDueDate37,ATE_AdvanceDueDate38,ATE_AdvanceDueDate39,ATE_AdvanceDueDate40,ATE_AdvanceDueDate41,ATE_AdvanceDueDate42,ATE_AdvanceDueDate43,ATE_AdvanceDueDate44,ATE_AdvanceStartDate12,ATE_AdvanceStartDate13,ATE_AdvanceStartDate14,ATE_AdvanceStartDate15,ATE_AdvanceStartDate21,ATE_AdvanceStartDate22,ATE_AdvanceStartDate23,ATE_AdvanceStartDate24,ATE_AdvanceStartDate25,ATE_AdvanceStartDate26,ATE_AdvanceStartDate27,ATE_AdvanceStartDate28,ATE_AdvanceStartDate29,ATE_AdvanceStartDate30,ATE_AdvanceStartDate31};
		String[] userData= {ATE_User26,ATE_User27,ATE_User28,ATE_User29,ATE_User35,ATE_User36,ATE_User37,ATE_User38,ATE_User39,ATE_User40,ATE_User41,ATE_User42,ATE_User43,ATE_User44,ATE_User45,ATE_User56,ATE_User57,ATE_User58,ATE_User59,ATE_User65,ATE_User66,ATE_User67,ATE_User68,ATE_User69,ATE_User70,ATE_User71,ATE_User72,ATE_User73,ATE_User74,ATE_User75};	
		String[] companyUsers=new String[userData.length];

		for(int i=0; i<userData.length; i++)
		{
			if(userData[i].toLowerCase().trim().equals("pe user 1"))
			{
				companyUsers[i]=userName1;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 2"))
			{
				companyUsers[i]=userName2;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 3"))
			{
				companyUsers[i]=userName3;
			}else if(userData[i].toLowerCase().trim().equals("pe user 4"))
			{
				companyUsers[i]=userName4;
			}else if(userData[i].toLowerCase().trim().equals("pe user 5"))
			{
				companyUsers[i]=userName5;
			}else if(userData[i].toLowerCase().trim().equals("pe user 6"))
			{
				companyUsers[i]=userName6;
			}else if(userData[i].toLowerCase().trim().equals("pe user 7"))
			{
				companyUsers[i]=userName7;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 8"))
			{
				companyUsers[i]=userName8;
			}else if(userData[i].toLowerCase().trim().equals("pe user 9"))
			{
				companyUsers[i]=userName9;
			}else if(userData[i].toLowerCase().trim().equals("pe user 10"))
			{
				companyUsers[i]=userName10;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 11"))
			{
				companyUsers[i]=userName11;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}
		}	
		
		IconType[] companyIcon2= {IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Call,IconType.Event,IconType.Event,IconType.Event,IconType.Event,IconType.Event};
		String[] companySubjectName2= {ATE_ATSubject30,ATE_ATSubject31,ATE_ATSubject32,ATE_ATSubject33,ATE_ATSubject34,ATE_ATSubject60,ATE_ATSubject61,ATE_ATSubject62,ATE_ATSubject63,ATE_ATSubject64};
		String[] companyDetails2= {ATE_ATNotes30,ATE_ATNotes31,ATE_ATNotes32,ATE_ATNotes33,ATE_ATNotes34,ATE_ATNotes60,ATE_ATNotes61,ATE_ATNotes62,ATE_ATNotes63,ATE_ATNotes64};
		String[] companyDueDate2= {ATE_AdvanceDueDate29,ATE_AdvanceDueDate30,ATE_AdvanceDueDate31,ATE_AdvanceDueDate32,ATE_AdvanceDueDate34,ATE_AdvanceStartDate16,ATE_AdvanceStartDate17,ATE_AdvanceStartDate18,ATE_AdvanceStartDate19,ATE_AdvanceStartDate20};
		String[] userData2= {ATE_User30,ATE_User31,ATE_User32,ATE_User33,ATE_User34,ATE_User60,ATE_User61,ATE_User62,ATE_User63,ATE_User64};	

		String[] companyUsers2=new String[userData2.length];

		for(int i=0; i<userData2.length; i++)
		{
			if(userData2[i].toLowerCase().trim().equals("pe user 1"))
			{
				companyUsers2[i]=userName1;
			}
			else if(userData2[i].toLowerCase().trim().equals("pe user 2"))
			{
				companyUsers2[i]=userName2;
			}
			else if(userData2[i].toLowerCase().trim().equals("pe user 3"))
			{
				companyUsers2[i]=userName3;
			}else if(userData2[i].toLowerCase().trim().equals("pe user 4"))
			{
				companyUsers2[i]=userName4;
			}else if(userData2[i].toLowerCase().trim().equals("pe user 5"))
			{
				companyUsers2[i]=userName5;
			}else if(userData2[i].toLowerCase().trim().equals("pe user 6"))
			{
				companyUsers2[i]=userName6;
			}else if(userData2[i].toLowerCase().trim().equals("pe user 7"))
			{
				companyUsers2[i]=userName7;
			}
			else if(userData2[i].toLowerCase().trim().equals("pe user 8"))
			{
				companyUsers2[i]=userName8;
			}else if(userData2[i].toLowerCase().trim().equals("pe user 9"))
			{
				companyUsers2[i]=userName9;
			}else if(userData2[i].toLowerCase().trim().equals("pe user 10"))
			{
				companyUsers2[i]=userName10;
			}
			else if(userData2[i].toLowerCase().trim().equals("pe user 11"))
			{
				companyUsers2[i]=userName11;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}
		}
		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

					ThreadSleep(5000);

					if (click(driver, bp.getTaggedRecordName("Companies", 30), "Companies tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Companies tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("Companies", companyTagName, companyTagTimeReferenceCount,30), companyTagName+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+companyTagName,YesNo.No);

							ArrayList<String> result=bp.verifyRecordsonInteractionsViewAllPopup(companyIcon, companyDueDate, companySubjectName, companyDetails, companyUsers, companySubjectName);
							if(result.isEmpty())
							{
								log(LogStatus.INFO, "All records on Interaction card have been verified for "+companyTagName+" record", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "All records on Interaction card are not verified for "+companyTagName+" record " +result, YesNo.No);
								sa.assertTrue(false,  "All records on Interaction card are not verified for "+companyTagName+" record "+result);
							}
							xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
							ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
							if(clickUsingJavaScript(driver, ele, "close button"))
							{
								log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
								sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+companyTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+companyTagName);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Companies tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Companies tab name");
					}
					
					
					if (click(driver, bp.getTaggedRecordName("Companies", 30), "Companies tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Companies tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("Companies", companyTagName1, companyTagTimeReferenceCount1,30), companyTagName1+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+companyTagName1,YesNo.No);

							ArrayList<String> result2=bp.verifyRecordsonInteractionsViewAllPopup(companyIcon, companyDueDate, companySubjectName, companyDetails, companyUsers, companySubjectName);
							if(result2.isEmpty())
							{
								log(LogStatus.INFO, "All records on Interaction card have been verified for "+companyTagName1+" record", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "All records on Interaction card are not verified for "+companyTagName1+" record " +result2, YesNo.No);
								sa.assertTrue(false,  "All records on Interaction card are not verified for "+companyTagName1+" record "+result2);
							}
							xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
							ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
							if(clickUsingJavaScript(driver, ele, "close button"))
							{
								log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
								sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+companyTagName1,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+companyTagName1);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Companies tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Companies tab name");
					}
					
					
					if (click(driver, bp.getTaggedRecordName("Deals", 30), "Deals tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Deals tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("Deals", dealTagName, dealTagTimeReferenceCount,30), dealTagName+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+dealTagName,YesNo.No);

							ArrayList<String> result3=bp.verifyRecordsonInteractionsViewAllPopup(companyIcon2, companyDueDate2, companySubjectName2, companyDetails2, companyUsers2, companySubjectName2);
							if(result3.isEmpty())
							{
								log(LogStatus.INFO, "All records on Interaction card have been verified for "+dealTagName+" record", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "All records on Interaction card are not verified for "+dealTagName+" record " +result3, YesNo.No);
								sa.assertTrue(false,  "All records on Interaction card are not verified for "+dealTagName+" record "+result3);
							}
							xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
							ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
							if(clickUsingJavaScript(driver, ele, "close button"))
							{
								log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
								sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+dealTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+dealTagName);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Deals tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Deals tab name");
					}
					

				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}


	@Parameters({ "projectName" })
	@Test
	public void ATETc036_CreateATaskWithIntermediaryFirmContactAndVerifyTheAccountAcuityTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATERecord3;
		String activityType=ATE_ATActivityType76;
		String taskSubject=ATE_ATSubject76;
		String taskRelatedTo=ATE_ATRelatedTo6;
		String taskNotes=ATE_ATNotes76;

		String dueDay=ATE_Day3;
		String taskDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(dueDay));
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate, "Activity Timeline", excelLabel.Variable_Name,
				"ATE_076", excelLabel.Advance_Due_Date);

		String taskStatus=ATE_AdvanceStatus4;
		String taskPriority=ATE_AdvancePriority5;

		String activityType1=ATE_ATActivityType77;
		String taskSubject1=ATE_ATSubject77;
		String taskRelatedTo1=ATE_ATRelatedTo7;
		String taskNotes1=ATE_ATNotes77;
		String dueDay1=ATE_Day4;
		String taskDueDate1 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(dueDay1));
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate1, "Activity Timeline", excelLabel.Variable_Name,
				"ATE_077", excelLabel.Advance_Due_Date);
		String taskStatus1=ATE_AdvanceStatus5;
		String taskPriority1=ATE_AdvancePriority6;


		String activityType2=ATE_ATActivityType78;
		String taskSubject2=ATE_ATSubject78;
		String taskRelatedTo2=ATE_ATRelatedTo8;
		String taskNotes2=ATE_ATNotes78;

		String taskStartDay=ATE_Day5;
		String advanceStartDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(taskStartDay));
		ExcelUtils.writeData(AcuityDataSheetFilePath, advanceStartDate, "Activity Timeline", excelLabel.Variable_Name,
				"ATE_078", excelLabel.Advance_Start_Date);

		String taskEndDay=ATE_EndDay1;
		String advanceEndDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(taskEndDay));
		ExcelUtils.writeData(AcuityDataSheetFilePath, advanceStartDate, "Activity Timeline", excelLabel.Variable_Name,
				"ATE_078", excelLabel.Advance_End_Date);

		String[][] basicsection = { { "Subject", taskSubject }, { "Notes", taskNotes }, { "Related_To", taskRelatedTo } };
		String[][] advanceSection = { { "Due Date Only", taskDueDate }, {"Status", taskStatus}, {"Priority", taskPriority} };

		String[][] basicsection1 = { { "Subject", taskSubject1 }, { "Notes", taskNotes1 }, { "Related_To", taskRelatedTo1 } };
		String[][] advanceSection1 = { { "Due Date Only", taskDueDate1 }, {"Status", taskStatus1}, {"Priority", taskPriority1} };

		String[][] basicsection2 = { { "Subject", taskSubject2 }, { "Notes", taskNotes2 }, { "Related_To", taskRelatedTo2 } };
		String[][] advanceSection2 = { { "Start Date", advanceStartDate },{"End Date",advanceEndDate}};	

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (bp.createActivityTimeline(projectName, true, activityType, basicsection, advanceSection, null, null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject);

		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject);
		}	

		if (bp.createActivityTimeline(projectName, true, activityType1, basicsection1, advanceSection1, null, null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject1);

		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject1);
		}

		if (lp.clickAnyCellonCalender(projectName)) {
			log(LogStatus.INFO,"Able to click on Calendar/Event Link",YesNo.No);

			if (bp.updateActivityTimelineRecord(projectName, basicsection2, advanceSection2, null, null,null)) {
				log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject2, YesNo.No);
				sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject2);

			}
			else
			{
				log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject2, YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject2);
			}
		} else {
			log(LogStatus.ERROR,"Not Able to Click on Calendar/Event Link",YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on Calendar/Event Link");	
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}


	@Parameters({ "projectName" })
	@Test
	public void ATETc0037_VerifyImpactOnIntermediaryAccountAcuityTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATERecord3;

		String userName1=crmUser6FirstName+" "+crmUser6LastName;
		String userName2=crmUser7FirstName+" "+crmUser7LastName;
		String userName3=crmUser8FirstName+" "+crmUser8LastName;
		String userName4=crmUser9FirstName+" "+crmUser9LastName;
		String userName5=crmUser10FirstName+" "+crmUser10LastName;
		String userName6=crmUser11FirstName+" "+crmUser11LastName;
		String userName7=crmUser12FirstName+" "+crmUser12LastName;
		String userName8=crmUser13FirstName+" "+crmUser13LastName;
		String userName9=crmUser14FirstName+" "+crmUser14LastName;
		String userName10=crmUser15FirstName+" "+crmUser15LastName;
		String userName11=crmUser16FirstName+" "+crmUser16LastName;


		String[] companiesTaggedName= {ATE_TaggedCompanyName30};
		String[] companiesTaggedTimeReference= {ATE_TaggedCompanyTimeReference30};

		String[] peopleTagName={ATE_TaggedPeopleName17};
		String[] peopleTaggedTimeReference={ATE_TaggedPeopleTimeReference17};

		String contactSectionName[]= {ATE_ContactName22};
		String contactSectionTitle[]= {ATE_ContactTitle22};
		String contactSectionDeal[]= {ATE_ContactDeal22};
		String contactSectionMeetingAndCall[]= {ATE_ContactMeetingAndCall22};
		String contactSectionEmail[]= {ATE_ContactEmail22};

		String[] iconType= {ATE_ATActivityType76,ATE_ATActivityType77,ATE_ATActivityType78};

		String[] subjectName= {ATE_ATSubject76,ATE_ATSubject77,ATE_ATSubject78};

		String[] details= {ATE_ATNotes76,ATE_ATNotes77,ATE_ATNotes78};

		String[] date= {ATE_AdvanceDueDate46,ATE_AdvanceDueDate47,ATE_AdvanceStartDate32};

		String[] userData= {ATE_User76,ATE_User77,ATE_User78};

		String[] user=new String[userData.length];

		for(int i=0; i<userData.length; i++)
		{
			if(userData[i].toLowerCase().trim().equals("pe user 1"))
			{
				user[i]=userName1;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 2"))
			{
				user[i]=userName2;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 3"))
			{
				user[i]=userName3;
			}else if(userData[i].toLowerCase().trim().equals("pe user 4"))
			{
				user[i]=userName4;
			}else if(userData[i].toLowerCase().trim().equals("pe user 5"))
			{
				user[i]=userName5;
			}else if(userData[i].toLowerCase().trim().equals("pe user 6"))
			{
				user[i]=userName6;
			}else if(userData[i].toLowerCase().trim().equals("pe user 7"))
			{
				user[i]=userName7;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 8"))
			{
				user[i]=userName8;
			}else if(userData[i].toLowerCase().trim().equals("pe user 9"))
			{
				user[i]=userName9;
			}else if(userData[i].toLowerCase().trim().equals("pe user 10"))
			{
				user[i]=userName10;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 11"))
			{
				user[i]=userName11;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}
		}		
		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	


					ArrayList<String> result=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, peopleTagName, peopleTaggedTimeReference, null, null);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,  "The record name and Time reference are not verifed "+result, YesNo.No);
						sa.assertTrue(false,  "The record name and Time reference are not verifed "+result);
					}
					ArrayList<String> result1=bp.verifyRecordOnContactSectionAcuity(contactSectionName, contactSectionTitle, contactSectionDeal, contactSectionMeetingAndCall, contactSectionEmail);
					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on contact section in Acuity contact", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on contact section in Acuity for contact : "+result1, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on contact section in Acuity for contact :  "+result1);
					}
					if(bp.verifyCountOfRelatedAssociationOnTaggedPopupOnInteractionSctionOfFirstRecord())
					{
						log(LogStatus.INFO, "The count of Tagged record have been verified on tagged popup", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The count of Tagged record is not verified on tagged popup", YesNo.No);
						sa.assertTrue(false, "The count of Tagged record is not verified on tagged popup");
					}

					if(CommonLib.clickUsingJavaScript(driver, bp.getViewAllBtnOnIntration(20), "View All button"))
					{
						log(LogStatus.INFO, "Clicked on View All button of Interaction section", YesNo.No);
						ArrayList<String> result2=bp.verifyRecordsonInteractionsViewAllPopup(iconType,date, subjectName, details, user, subjectName);
						if(result2.isEmpty())
						{
							log(LogStatus.INFO, "The records have been verified on interaction popup in Acuity", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records are not verified on interaction popup in Acuity : "+result2, YesNo.No);
							sa.assertTrue(false,  "The records are not verified on interaction popup in Acuity :  "+result2);
						}

						String xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
						WebElement ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
						if(clickUsingJavaScript(driver, ele, "close button"))
						{
							log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
							sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on View All button of Interaction section", YesNo.No);
						sa.assertTrue(false,  "Not able to click on View All button of Interaction section" );
					}					
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}


	@Parameters({ "projectName" })
	@Test
	public void ATETc0038_VerifyConnectionPopupOnIntermediaryAccountPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATERecord3;
		String contactName=ATE_Contact1;

		String xPath;
		WebElement ele;

		String userName1=crmUser6FirstName+" "+crmUser6LastName;
		String userName2=crmUser7FirstName+" "+crmUser7LastName;
		String userName3=crmUser8FirstName+" "+crmUser8LastName;
		String userName4=crmUser9FirstName+" "+crmUser9LastName;
		String userName5=crmUser10FirstName+" "+crmUser10LastName;
		String userName6=crmUser11FirstName+" "+crmUser11LastName;
		String userName7=crmUser12FirstName+" "+crmUser12LastName;
		String userName8=crmUser13FirstName+" "+crmUser13LastName;
		String userName9=crmUser14FirstName+" "+crmUser14LastName;
		String userName10=crmUser15FirstName+" "+crmUser15LastName;
		String userName11=crmUser16FirstName+" "+crmUser16LastName;


		String[] connectionEmail= {ATE_ConnectionEmail13};
		String[] connectionMeetingAndCall= {ATE_ConnectionMeetingAndCall13};
		String[] connectionDeal= {ATE_ConnectionDeals13};
		String[] connectionTitle= {ATE_ConnectionTitle13};
		String[] userData= {ATE_ConnectionTeamMember13};

		String[] user=new String[userData.length];

		for(int i=0; i<userData.length; i++)
		{
			if(userData[i].toLowerCase().trim().equals("pe user 1"))
			{
				user[i]=userName1;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 2"))
			{
				user[i]=userName2;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 3"))
			{
				user[i]=userName3;
			}else if(userData[i].toLowerCase().trim().equals("pe user 4"))
			{
				user[i]=userName4;
			}else if(userData[i].toLowerCase().trim().equals("pe user 5"))
			{
				user[i]=userName5;
			}else if(userData[i].toLowerCase().trim().equals("pe user 6"))
			{
				user[i]=userName6;
			}else if(userData[i].toLowerCase().trim().equals("pe user 7"))
			{
				user[i]=userName7;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 8"))
			{
				user[i]=userName8;
			}else if(userData[i].toLowerCase().trim().equals("pe user 9"))
			{
				user[i]=userName9;
			}else if(userData[i].toLowerCase().trim().equals("pe user 10"))
			{
				user[i]=userName10;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 11"))
			{
				user[i]=userName11;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}
		}

		String[] icon= {ATE_ATActivityType77,ATE_ATActivityType78};

		String[] subjectName= {ATE_ATSubject77,ATE_ATSubject78};

		String[] date= {ATE_AdvanceDueDate47,ATE_AdvanceStartDate32};

		String[] details= {ATE_ATNotes77,ATE_ATNotes78};

		String[] users= {ATE_User77,ATE_User78};

		String[] meetingAndCallUser=new String[users.length];

		for(int i=0; i<users.length; i++)
		{
			if(users[i].toLowerCase().trim().equals("pe user 1"))
			{
				meetingAndCallUser[i]=userName1;
			}
			else if(users[i].toLowerCase().trim().equals("pe user 2"))
			{
				meetingAndCallUser[i]=userName2;
			}
			else if(users[i].toLowerCase().trim().equals("pe user 3"))
			{
				meetingAndCallUser[i]=userName3;
			}else if(users[i].toLowerCase().trim().equals("pe user 4"))
			{
				meetingAndCallUser[i]=userName4;
			}else if(users[i].toLowerCase().trim().equals("pe user 5"))
			{
				meetingAndCallUser[i]=userName5;
			}else if(users[i].toLowerCase().trim().equals("pe user 6"))
			{
				meetingAndCallUser[i]=userName6;
			}else if(users[i].toLowerCase().trim().equals("pe user 7"))
			{
				meetingAndCallUser[i]=userName7;
			}
			else if(users[i].toLowerCase().trim().equals("pe user 8"))
			{
				meetingAndCallUser[i]=userName8;
			}else if(users[i].toLowerCase().trim().equals("pe user 9"))
			{
				meetingAndCallUser[i]=userName9;
			}else if(users[i].toLowerCase().trim().equals("pe user 10"))
			{
				meetingAndCallUser[i]=userName10;
			}
			else if(users[i].toLowerCase().trim().equals("pe user 11"))
			{
				meetingAndCallUser[i]=userName11;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}
		}		



		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

					ThreadSleep(5000);
					ArrayList<String> result=bp.verifyRecordOnConnectionsPopUpOfContactInAcuity(contactName,user,connectionTitle,connectionDeal,connectionMeetingAndCall,connectionEmail);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The records on Connection popup have been verified", YesNo.No);	
					}
					else
					{
						log(LogStatus.ERROR, "The records on Connection popup are not verified. "+result, YesNo.No);	
						sa.assertTrue(false,  "The records on Connection popup are not verified. "+result);
					}

					if (CommonLib.clickUsingJavaScript(driver, bp.contactNameUserIconButton(contactName, 30), "Contact Name: " + contactName,
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on connection icon of contact : " + contactName, YesNo.No);

						xPath="//a[text()='"+userName1+"']/ancestor::tr//td[@data-label='Meetings and Calls']//button";
						ele=FindElement(driver, xPath, "Count of "+userName1+" on contact section", action.SCROLLANDBOOLEAN, 20);
						if(CommonLib.clickUsingJavaScript(driver, ele,"Count of "+userName1+" on contact section" , action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, "clicked on count of "+contactName,YesNo.No);
							ArrayList<String> result4=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity(icon, date, subjectName, details, meetingAndCallUser);
							if(result4.isEmpty())
							{
								log(LogStatus.INFO, "Records have been verified on meeting and call popup",YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Records are not verifid on meeting and call popup" +result4,YesNo.No);
								sa.assertTrue(false, "Records are not verifid on meeting and call popup" +result4);
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on count of "+contactName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on count of "+contactName);
						}		

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on connection icon of contact : " + contactName, YesNo.No);
						sa.assertTrue(false, "Not able to click on connection icon of contact : " + contactName);
					}
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}



	@Parameters({ "projectName" })
	@Test
	public void ATETc0039_VerifyIntermediaryContactAcuityTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATE_Contact1;

		String userName1=crmUser6FirstName+" "+crmUser6LastName;
		String userName2=crmUser7FirstName+" "+crmUser7LastName;
		String userName3=crmUser8FirstName+" "+crmUser8LastName;
		String userName4=crmUser9FirstName+" "+crmUser9LastName;
		String userName5=crmUser10FirstName+" "+crmUser10LastName;
		String userName6=crmUser11FirstName+" "+crmUser11LastName;
		String userName7=crmUser12FirstName+" "+crmUser12LastName;
		String userName8=crmUser13FirstName+" "+crmUser13LastName;
		String userName9=crmUser14FirstName+" "+crmUser14LastName;
		String userName10=crmUser15FirstName+" "+crmUser15LastName;
		String userName11=crmUser16FirstName+" "+crmUser16LastName;

		String xPath;
		WebElement ele;


		String[] companiesTaggedName= {ATE_TaggedCompanyName30};
		String[] companiesTaggedTimeReference= {ATE_TaggedCompanyTimeReference30};

		String[] peopleTagName={ATE_TaggedPeopleName17};
		String[] peopleTaggedTimeReference={ATE_TaggedPeopleTimeReference17};

		String[] iconType= {ATE_ATActivityType76,ATE_ATActivityType77,ATE_ATActivityType78};

		String[] subjectName= {ATE_ATSubject76,ATE_ATSubject77,ATE_ATSubject78};

		String[] details= {ATE_ATNotes76,ATE_ATNotes77,ATE_ATNotes78};

		String[] date= {ATE_AdvanceDueDate46,ATE_AdvanceDueDate47,ATE_AdvanceStartDate32};

		String[] userData= {ATE_User76,ATE_User77,ATE_User78};

		String[] user=new String[userData.length];

		for(int i=0; i<userData.length; i++)
		{
			if(userData[i].toLowerCase().trim().equals("pe user 1"))
			{
				user[i]=userName1;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 2"))
			{
				user[i]=userName2;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 3"))
			{
				user[i]=userName3;
			}else if(userData[i].toLowerCase().trim().equals("pe user 4"))
			{
				user[i]=userName4;
			}else if(userData[i].toLowerCase().trim().equals("pe user 5"))
			{
				user[i]=userName5;
			}else if(userData[i].toLowerCase().trim().equals("pe user 6"))
			{
				user[i]=userName6;
			}else if(userData[i].toLowerCase().trim().equals("pe user 7"))
			{
				user[i]=userName7;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 8"))
			{
				user[i]=userName8;
			}else if(userData[i].toLowerCase().trim().equals("pe user 9"))
			{
				user[i]=userName9;
			}else if(userData[i].toLowerCase().trim().equals("pe user 10"))
			{
				user[i]=userName10;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 11"))
			{
				user[i]=userName11;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}
		}


		String[] meetingAndCallIcon= {ATE_ATActivityType77,ATE_ATActivityType78};

		String[] meetingAndCallsubjectName= {ATE_ATSubject77,ATE_ATSubject78};

		String[] meetingAndCalldate= {ATE_AdvanceDueDate47,ATE_AdvanceStartDate32};

		String[] meetingAndCalldetails= {ATE_ATNotes77,ATE_ATNotes78};

		String[] users= {ATE_User77,ATE_User78};

		String[] meetingAndCallUser=new String[users.length];

		for(int i=0; i<users.length; i++)
		{
			if(users[i].toLowerCase().trim().equals("pe user 1"))
			{
				meetingAndCallUser[i]=userName1;
			}
			else if(users[i].toLowerCase().trim().equals("pe user 2"))
			{
				meetingAndCallUser[i]=userName2;
			}
			else if(users[i].toLowerCase().trim().equals("pe user 3"))
			{
				meetingAndCallUser[i]=userName3;
			}else if(users[i].toLowerCase().trim().equals("pe user 4"))
			{
				meetingAndCallUser[i]=userName4;
			}else if(users[i].toLowerCase().trim().equals("pe user 5"))
			{
				meetingAndCallUser[i]=userName5;
			}else if(users[i].toLowerCase().trim().equals("pe user 6"))
			{
				meetingAndCallUser[i]=userName6;
			}else if(users[i].toLowerCase().trim().equals("pe user 7"))
			{
				meetingAndCallUser[i]=userName7;
			}
			else if(users[i].toLowerCase().trim().equals("pe user 8"))
			{
				meetingAndCallUser[i]=userName8;
			}else if(users[i].toLowerCase().trim().equals("pe user 9"))
			{
				meetingAndCallUser[i]=userName9;
			}else if(users[i].toLowerCase().trim().equals("pe user 10"))
			{
				meetingAndCallUser[i]=userName10;
			}
			else if(users[i].toLowerCase().trim().equals("pe user 11"))
			{
				meetingAndCallUser[i]=userName11;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}
		}		



		String connectionEmail= ATE_ConnectionEmail13;
		String connectionMeetingAndCall= ATE_ConnectionMeetingAndCall13;
		String connectionDeal= ATE_ConnectionDeals13;
		String connectionTitle= ATE_ConnectionTitle13;
		String usersData= ATE_ConnectionTeamMember13;
		String connectionUser="";

		if(usersData.toLowerCase().trim().equals("pe user 1"))
		{
			connectionUser=userName1;
		}
		else if(usersData.toLowerCase().trim().equals("pe user 2"))
		{
			connectionUser=userName2;
		}
		else if(usersData.toLowerCase().trim().equals("pe user 3"))
		{
			connectionUser=userName3;
		}else if(usersData.toLowerCase().trim().equals("pe user 4"))
		{
			connectionUser=userName4;
		}else if(usersData.toLowerCase().trim().equals("pe user 5"))
		{
			connectionUser=userName5;
		}else if(usersData.toLowerCase().trim().equals("pe user 6"))
		{
			connectionUser=userName6;
		}else if(usersData.toLowerCase().trim().equals("pe user 7"))
		{
			connectionUser=userName7;
		}
		else if(usersData.toLowerCase().trim().equals("pe user 8"))
		{
			connectionUser=userName8;
		}else if(usersData.toLowerCase().trim().equals("pe user 9"))
		{
			connectionUser=userName9;
		}else if(usersData.toLowerCase().trim().equals("pe user 10"))
		{
			connectionUser=userName10;
		}
		else if(usersData.toLowerCase().trim().equals("pe user 11"))
		{
			connectionUser=userName11;
		}
		else
		{
			Assertion hardAssert = new Assertion();
			log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
			hardAssert.assertTrue(true == false);
		}



		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	


					ArrayList<String> result=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, peopleTagName, peopleTaggedTimeReference, null, null);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,  "The record name and Time reference are not verifed "+result, YesNo.No);
						sa.assertTrue(false,  "The record name and Time reference are not verifed "+result);
					}


					ArrayList<String> result10=bp.verifyDefaultSortingOfReferencedTypeOnTaggedSection();
					if(result10.isEmpty())
					{
						log(LogStatus.INFO, "Default decending order of times referenced count have been verified on tagged section", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "Default decending order of times referenced count are not verified on tagged section. "+result10, YesNo.No);
						sa.assertTrue(false, "Default decending order of times referenced count are not verified on tagged section. "+result10);
					}

					if(bp.verifyCountOfRelatedAssociationOnTaggedPopupOnInteractionSctionOfFirstRecord())
					{
						log(LogStatus.INFO, "The count of Tagged record have been verified on tagged popup", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The count of Tagged record is not verified on tagged popup", YesNo.No);
						sa.assertTrue(false, "The count of Tagged record is not verified on tagged popup");
					}						
					if(CommonLib.clickUsingJavaScript(driver, bp.getViewAllBtnOnIntration(20), "View All button"))
					{
						log(LogStatus.INFO, "Clicked on View All button of Interaction section", YesNo.No);
						ArrayList<String> result2=bp.verifyRecordsonInteractionsViewAllPopup(iconType,date, subjectName, details, user, subjectName);
						if(result2.isEmpty())
						{
							log(LogStatus.INFO, "The records have been verified on interaction popup in Acuity", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records are not verified on interaction popup in Acuity : "+result2, YesNo.No);
							sa.assertTrue(false,  "The records are not verified on interaction popup in Acuity :  "+result2);
						}

						xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
						ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
						if(clickUsingJavaScript(driver, ele, "close button"))
						{
							log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
							sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on View All button of Interaction section", YesNo.No);
						sa.assertTrue(false,  "Not able to click on View All button of Interaction section" );
					}


					ArrayList<String> result3=bp.verifyRecordOnConnectionsSectionInAcuity(recordName, connectionUser, connectionTitle, connectionDeal, connectionMeetingAndCall, connectionEmail);
					if(result3.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on Connection section for user :"+connectionUser, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are verified on Connection section for user :"+connectionUser+". "+result3, YesNo.No);
						sa.assertTrue(false, "The records are verified on Connection section for user :"+connectionUser+". "+result3);
					}

					xPath="//a[text()='"+userName1+"']/ancestor::tr//td[@data-label='Meetings and Calls']//button";
					ele=FindElement(driver, xPath, "Count of "+userName1+" on contact section", action.SCROLLANDBOOLEAN, 20);
					if(CommonLib.clickUsingJavaScript(driver, ele,"Count of "+userName1+" on contact section" , action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on count of "+userName1,YesNo.No);

						ArrayList<String> result4=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity(meetingAndCallIcon, meetingAndCalldate, meetingAndCallsubjectName, meetingAndCalldetails, meetingAndCallUser);
						if(result4.isEmpty())
						{
							log(LogStatus.INFO, "Records have been verified on meeting and call popup for user "+userName1,YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Records are not verifid on meeting and call popup for "+userName1+". " +result4,YesNo.No);
							sa.assertTrue(false, "Records are not verifid on meeting and call popup for "+userName1+". "  +result4);
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on count of "+userName1,YesNo.No);
						sa.assertTrue(false,  "Not able to click on count of "+userName1);
					}		
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj2, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj2);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}

	
	@Parameters({ "projectName" })
	@Test
	public void ATETc040_AssociateIntermediaryAccountWithReferenceAccountsContactInATaskAndEvent(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATERecord3;
		String activityType=ATE_ATActivityType79;
		String taskSubject=ATE_ATSubject79;
		String taskRelatedTo=ATE_ATRelatedTo9;
		String taskNotes=ATE_ATNotes79;

		String dueDay=ATE_Day6;
		String taskDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(dueDay));
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate, "Activity Timeline", excelLabel.Variable_Name,
				"ATE_079", excelLabel.Advance_Due_Date);

		String taskStatus=ATE_AdvanceStatus6;
		String taskPriority=ATE_AdvancePriority7;

		String activityType1=ATE_ATActivityType80;
		String taskSubject1=ATE_ATSubject80;
		String taskRelatedTo1=ATE_ATRelatedTo10;
		String taskNotes1=ATE_ATNotes80;
		String dueDay1=ATE_Day7;
		String taskDueDate1 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(dueDay1));
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate1, "Activity Timeline", excelLabel.Variable_Name,
				"ATE_080", excelLabel.Advance_Due_Date);
		String taskStatus1=ATE_AdvanceStatus7;
		String taskPriority1=ATE_AdvancePriority8;


		String activityType2=ATE_ATActivityType81;
		String taskSubject2=ATE_ATSubject81;
		String taskRelatedTo2=ATE_ATRelatedTo11;
		String taskNotes2=ATE_ATNotes81;

		String taskStartDay=ATE_Day8;
		String advanceStartDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(taskStartDay));
		ExcelUtils.writeData(AcuityDataSheetFilePath, advanceStartDate, "Activity Timeline", excelLabel.Variable_Name,
				"ATE_081", excelLabel.Advance_Start_Date);

		String taskEndDay=ATE_EndDay2;
		String advanceEndDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(taskEndDay));
		ExcelUtils.writeData(AcuityDataSheetFilePath, advanceStartDate, "Activity Timeline", excelLabel.Variable_Name,
				"ATE_081", excelLabel.Advance_End_Date);

		String[][] basicsection = { { "Subject", taskSubject }, { "Notes", taskNotes }, { "Related_To", taskRelatedTo } };
		String[][] advanceSection = { { "Due Date Only", taskDueDate }, {"Status", taskStatus}, {"Priority", taskPriority} };

		String[][] basicsection1 = { { "Subject", taskSubject1 }, { "Notes", taskNotes1 }, { "Related_To", taskRelatedTo1 } };
		String[][] advanceSection1 = { { "Due Date Only", taskDueDate1 }, {"Status", taskStatus1}, {"Priority", taskPriority1} };

		String[][] basicsection2 = { { "Subject", taskSubject2 }, { "Notes", taskNotes2 }, { "Related_To", taskRelatedTo2 } };
		String[][] advanceSection2 = { { "Start Date", advanceStartDate },{"End Date",advanceEndDate}};	

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (bp.createActivityTimeline(projectName, true, activityType, basicsection, advanceSection, null, null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject);

		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject);
		}	

		if (bp.createActivityTimeline(projectName, true, activityType1, basicsection1, advanceSection1, null, null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject1);

		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject1);
		}

		if (lp.clickAnyCellonCalender(projectName)) {
			log(LogStatus.INFO,"Able to click on Calendar/Event Link",YesNo.No);

			if (bp.updateActivityTimelineRecord(projectName, basicsection2, advanceSection2, null, null,null)) {
				log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject2, YesNo.No);
				sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject2);

			}
			else
			{
				log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject2, YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject2);
			}
		} else {
			log(LogStatus.ERROR,"Not Able to Click on Calendar/Event Link",YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on Calendar/Event Link");	
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}

	
	
	@Parameters({ "projectName" })
	@Test
	public void ATETc0041_VerifyImpactOnIntermediaryAccountAcuityTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATERecord3;

		String userName1=crmUser6FirstName+" "+crmUser6LastName;
		String userName2=crmUser7FirstName+" "+crmUser7LastName;
		String userName3=crmUser8FirstName+" "+crmUser8LastName;
		String userName4=crmUser9FirstName+" "+crmUser9LastName;
		String userName5=crmUser10FirstName+" "+crmUser10LastName;
		String userName6=crmUser11FirstName+" "+crmUser11LastName;
		String userName7=crmUser12FirstName+" "+crmUser12LastName;
		String userName8=crmUser13FirstName+" "+crmUser13LastName;
		String userName9=crmUser14FirstName+" "+crmUser14LastName;
		String userName10=crmUser15FirstName+" "+crmUser15LastName;
		String userName11=crmUser16FirstName+" "+crmUser16LastName;


		String[] companiesTaggedName= {ATE_TaggedCompanyName31};
		String[] companiesTaggedTimeReference= {ATE_TaggedCompanyTimeReference31};

		String[] peopleTagName={ATE_TaggedPeopleName18};
		String[] peopleTaggedTimeReference={ATE_TaggedPeopleTimeReference18};

		String contactSectionName[]= {ATE_ContactName22};
		String contactSectionTitle[]= {ATE_ContactTitle22};
		String contactSectionDeal[]= {ATE_ContactDeal22};
		String contactSectionMeetingAndCall[]= {ATE_ContactMeetingAndCall22};
		String contactSectionEmail[]= {ATE_ContactEmail22};

		String[] iconType= {ATE_ATActivityType79,ATE_ATActivityType80};

		String[] subjectName= {ATE_ATSubject79,ATE_ATSubject80};

		String[] details= {ATE_ATNotes79,ATE_ATNotes80};

		String[] date= {ATE_AdvanceDueDate48,ATE_AdvanceDueDate49};

		String[] userData= {ATE_User79,ATE_User80};

		String[] user=new String[userData.length];

		for(int i=0; i<userData.length; i++)
		{
			if(userData[i].toLowerCase().trim().equals("pe user 1"))
			{
				user[i]=userName1;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 2"))
			{
				user[i]=userName2;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 3"))
			{
				user[i]=userName3;
			}else if(userData[i].toLowerCase().trim().equals("pe user 4"))
			{
				user[i]=userName4;
			}else if(userData[i].toLowerCase().trim().equals("pe user 5"))
			{
				user[i]=userName5;
			}else if(userData[i].toLowerCase().trim().equals("pe user 6"))
			{
				user[i]=userName6;
			}else if(userData[i].toLowerCase().trim().equals("pe user 7"))
			{
				user[i]=userName7;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 8"))
			{
				user[i]=userName8;
			}else if(userData[i].toLowerCase().trim().equals("pe user 9"))
			{
				user[i]=userName9;
			}else if(userData[i].toLowerCase().trim().equals("pe user 10"))
			{
				user[i]=userName10;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 11"))
			{
				user[i]=userName11;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}
		}		
		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	


					ArrayList<String> result=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, peopleTagName, peopleTaggedTimeReference, null, null);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,  "The record name and Time reference are not verifed "+result, YesNo.No);
						sa.assertTrue(false,  "The record name and Time reference are not verifed "+result);
					}
					
					
					ArrayList<String> result10=bp.verifyDefaultSortingOfReferencedTypeOnTaggedSection();
					if(result10.isEmpty())
					{
						log(LogStatus.INFO, "Default decending order of times referenced count have been verified on tagged section", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "Default decending order of times referenced count are not verified on tagged section. "+result10, YesNo.No);
						sa.assertTrue(false, "Default decending order of times referenced count are not verified on tagged section. "+result10);
					}
					
					ArrayList<String> result1=bp.verifyRecordOnContactSectionAcuity(contactSectionName, contactSectionTitle, contactSectionDeal, contactSectionMeetingAndCall, contactSectionEmail);
					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on contact section in Acuity contact", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on contact section in Acuity for contact : "+result1, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on contact section in Acuity for contact :  "+result1);
					}
					if(bp.verifyCountOfRelatedAssociationOnTaggedPopupOnInteractionSctionOfFirstRecord())
					{
						log(LogStatus.INFO, "The count of Tagged record have been verified on tagged popup", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The count of Tagged record is not verified on tagged popup", YesNo.No);
						sa.assertTrue(false, "The count of Tagged record is not verified on tagged popup");
					}

					if(CommonLib.clickUsingJavaScript(driver, bp.getViewAllBtnOnIntration(20), "View All button"))
					{
						log(LogStatus.INFO, "Clicked on View All button of Interaction section", YesNo.No);
						ArrayList<String> result2=bp.verifyRecordsonInteractionsViewAllPopup(iconType,date, subjectName, details, user, subjectName);
						if(result2.isEmpty())
						{
							log(LogStatus.INFO, "The records have been verified on interaction popup in Acuity", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records are not verified on interaction popup in Acuity : "+result2, YesNo.No);
							sa.assertTrue(false,  "The records are not verified on interaction popup in Acuity :  "+result2);
						}

						String xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
						WebElement ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
						if(clickUsingJavaScript(driver, ele, "close button"))
						{
							log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
							sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on View All button of Interaction section", YesNo.No);
						sa.assertTrue(false,  "Not able to click on View All button of Interaction section" );
					}					
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}
	
	
	@Parameters({ "projectName" })
	@Test
	public void ATETc0042_VerifyConnectionPopupOnIntermediaryAccountPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATERecord3;
		String contactName=ATE_Contact1;

		String xPath;
		WebElement ele;

		String userName1=crmUser6FirstName+" "+crmUser6LastName;
		String userName2=crmUser7FirstName+" "+crmUser7LastName;
		String userName3=crmUser8FirstName+" "+crmUser8LastName;
		String userName4=crmUser9FirstName+" "+crmUser9LastName;
		String userName5=crmUser10FirstName+" "+crmUser10LastName;
		String userName6=crmUser11FirstName+" "+crmUser11LastName;
		String userName7=crmUser12FirstName+" "+crmUser12LastName;
		String userName8=crmUser13FirstName+" "+crmUser13LastName;
		String userName9=crmUser14FirstName+" "+crmUser14LastName;
		String userName10=crmUser15FirstName+" "+crmUser15LastName;
		String userName11=crmUser16FirstName+" "+crmUser16LastName;


		String[] connectionEmail= {ATE_ConnectionEmail13};
		String[] connectionMeetingAndCall= {ATE_ConnectionMeetingAndCall13};
		String[] connectionDeal= {ATE_ConnectionDeals13};
		String[] connectionTitle= {ATE_ConnectionTitle13};
		String[] userData= {ATE_ConnectionTeamMember13};

		String[] user=new String[userData.length];

		for(int i=0; i<userData.length; i++)
		{
			if(userData[i].toLowerCase().trim().equals("pe user 1"))
			{
				user[i]=userName1;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 2"))
			{
				user[i]=userName2;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 3"))
			{
				user[i]=userName3;
			}else if(userData[i].toLowerCase().trim().equals("pe user 4"))
			{
				user[i]=userName4;
			}else if(userData[i].toLowerCase().trim().equals("pe user 5"))
			{
				user[i]=userName5;
			}else if(userData[i].toLowerCase().trim().equals("pe user 6"))
			{
				user[i]=userName6;
			}else if(userData[i].toLowerCase().trim().equals("pe user 7"))
			{
				user[i]=userName7;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 8"))
			{
				user[i]=userName8;
			}else if(userData[i].toLowerCase().trim().equals("pe user 9"))
			{
				user[i]=userName9;
			}else if(userData[i].toLowerCase().trim().equals("pe user 10"))
			{
				user[i]=userName10;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 11"))
			{
				user[i]=userName11;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}
		}

		String[] icon= {ATE_ATActivityType77,ATE_ATActivityType78};

		String[] subjectName= {ATE_ATSubject77,ATE_ATSubject78};

		String[] date= {ATE_AdvanceDueDate47,ATE_AdvanceStartDate32};

		String[] details= {ATE_ATNotes77,ATE_ATNotes78};

		String[] users= {ATE_User77,ATE_User78};

		String[] meetingAndCallUser=new String[users.length];

		for(int i=0; i<users.length; i++)
		{
			if(users[i].toLowerCase().trim().equals("pe user 1"))
			{
				meetingAndCallUser[i]=userName1;
			}
			else if(users[i].toLowerCase().trim().equals("pe user 2"))
			{
				meetingAndCallUser[i]=userName2;
			}
			else if(users[i].toLowerCase().trim().equals("pe user 3"))
			{
				meetingAndCallUser[i]=userName3;
			}else if(users[i].toLowerCase().trim().equals("pe user 4"))
			{
				meetingAndCallUser[i]=userName4;
			}else if(users[i].toLowerCase().trim().equals("pe user 5"))
			{
				meetingAndCallUser[i]=userName5;
			}else if(users[i].toLowerCase().trim().equals("pe user 6"))
			{
				meetingAndCallUser[i]=userName6;
			}else if(users[i].toLowerCase().trim().equals("pe user 7"))
			{
				meetingAndCallUser[i]=userName7;
			}
			else if(users[i].toLowerCase().trim().equals("pe user 8"))
			{
				meetingAndCallUser[i]=userName8;
			}else if(users[i].toLowerCase().trim().equals("pe user 9"))
			{
				meetingAndCallUser[i]=userName9;
			}else if(users[i].toLowerCase().trim().equals("pe user 10"))
			{
				meetingAndCallUser[i]=userName10;
			}
			else if(users[i].toLowerCase().trim().equals("pe user 11"))
			{
				meetingAndCallUser[i]=userName11;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}
		}		



		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

					ThreadSleep(5000);
					ArrayList<String> result=bp.verifyRecordOnConnectionsPopUpOfContactInAcuity(contactName,user,connectionTitle,connectionDeal,connectionMeetingAndCall,connectionEmail);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The records on Connection popup have been verified", YesNo.No);	
					}
					else
					{
						log(LogStatus.ERROR, "The records on Connection popup are not verified. "+result, YesNo.No);	
						sa.assertTrue(false,  "The records on Connection popup are not verified. "+result);
					}			
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}

	@Parameters({ "projectName" })
	@Test
	public void ATETc0043_VerifyIntermediaryContactAcuityTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATE_Contact1;

		String userName1=crmUser6FirstName+" "+crmUser6LastName;
		String userName2=crmUser7FirstName+" "+crmUser7LastName;
		String userName3=crmUser8FirstName+" "+crmUser8LastName;
		String userName4=crmUser9FirstName+" "+crmUser9LastName;
		String userName5=crmUser10FirstName+" "+crmUser10LastName;
		String userName6=crmUser11FirstName+" "+crmUser11LastName;
		String userName7=crmUser12FirstName+" "+crmUser12LastName;
		String userName8=crmUser13FirstName+" "+crmUser13LastName;
		String userName9=crmUser14FirstName+" "+crmUser14LastName;
		String userName10=crmUser15FirstName+" "+crmUser15LastName;
		String userName11=crmUser16FirstName+" "+crmUser16LastName;

		String xPath;
		WebElement ele;


		String[] companiesTaggedName= {ATE_TaggedCompanyName30};
		String[] companiesTaggedTimeReference= {ATE_TaggedCompanyTimeReference30};

		String[] peopleTagName={ATE_TaggedPeopleName17};
		String[] peopleTaggedTimeReference={ATE_TaggedPeopleTimeReference17};
		
		String[] subjectName= {ATE_ATSubject79,ATE_ATSubject80,ATE_ATSubject81};

		String connectionEmail= ATE_ConnectionEmail13;
		String connectionMeetingAndCall= ATE_ConnectionMeetingAndCall13;
		String connectionDeal= ATE_ConnectionDeals13;
		String connectionTitle= ATE_ConnectionTitle13;
		String usersData= ATE_ConnectionTeamMember13;
		String connectionUser="";

		if(usersData.toLowerCase().trim().equals("pe user 1"))
		{
			connectionUser=userName1;
		}
		else if(usersData.toLowerCase().trim().equals("pe user 2"))
		{
			connectionUser=userName2;
		}
		else if(usersData.toLowerCase().trim().equals("pe user 3"))
		{
			connectionUser=userName3;
		}else if(usersData.toLowerCase().trim().equals("pe user 4"))
		{
			connectionUser=userName4;
		}else if(usersData.toLowerCase().trim().equals("pe user 5"))
		{
			connectionUser=userName5;
		}else if(usersData.toLowerCase().trim().equals("pe user 6"))
		{
			connectionUser=userName6;
		}else if(usersData.toLowerCase().trim().equals("pe user 7"))
		{
			connectionUser=userName7;
		}
		else if(usersData.toLowerCase().trim().equals("pe user 8"))
		{
			connectionUser=userName8;
		}else if(usersData.toLowerCase().trim().equals("pe user 9"))
		{
			connectionUser=userName9;
		}else if(usersData.toLowerCase().trim().equals("pe user 10"))
		{
			connectionUser=userName10;
		}
		else if(usersData.toLowerCase().trim().equals("pe user 11"))
		{
			connectionUser=userName11;
		}
		else
		{
			Assertion hardAssert = new Assertion();
			log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
			hardAssert.assertTrue(true == false);
		}

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

					ArrayList<String> result=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, peopleTagName, peopleTaggedTimeReference, null, null);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,  "The record name and Time reference are not verifed "+result, YesNo.No);
						sa.assertTrue(false,  "The record name and Time reference are not verifed "+result);
					}


					ArrayList<String> result10=bp.verifyDefaultSortingOfReferencedTypeOnTaggedSection();
					if(result10.isEmpty())
					{
						log(LogStatus.INFO, "Default decending order of times referenced count have been verified on tagged section", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "Default decending order of times referenced count are not verified on tagged section. "+result10, YesNo.No);
						sa.assertTrue(false, "Default decending order of times referenced count are not verified on tagged section. "+result10);
					}

					if(bp.verifyCountOfRelatedAssociationOnTaggedPopupOnInteractionSctionOfFirstRecord())
					{
						log(LogStatus.INFO, "The count of Tagged record have been verified on tagged popup", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The count of Tagged record is not verified on tagged popup", YesNo.No);
						sa.assertTrue(false, "The count of Tagged record is not verified on tagged popup");
					}
					
					if(CommonLib.clickUsingJavaScript(driver, bp.getViewAllBtnOnIntration(20), "View All button"))
					{
						log(LogStatus.INFO, "Clicked on View All button of Interaction section", YesNo.No);
						ArrayList<String> result2=bp.verifyActivityTimeLineRecordShouldNotVisibleOnViewAllInteractionPopup(subjectName);
						if(result2.isEmpty())
						{
							log(LogStatus.INFO, "The records are not available on View All Interaction section", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records are available on View All Interaction section: "+result2, YesNo.No);
							sa.assertTrue(false,  "The records are available on View All Interaction section  "+result2);
						}

						xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
						ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
						if(clickUsingJavaScript(driver, ele, "close button"))
						{
							log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
							sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on View All button of Interaction section", YesNo.No);
						sa.assertTrue(false,  "Not able to click on View All button of Interaction section" );
					}


					ArrayList<String> result3=bp.verifyRecordOnConnectionsSectionInAcuity(recordName, connectionUser, connectionTitle, connectionDeal, connectionMeetingAndCall, connectionEmail);
					if(result3.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on Connection section for user :"+connectionUser, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are verified on Connection section for user :"+connectionUser+". "+result3, YesNo.No);
						sa.assertTrue(false, "The records are verified on Connection section for user :"+connectionUser+". "+result3);
					}

					
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj2, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj2);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}

	
	@Parameters({ "projectName" })
	@Test
	public void ATETc044_AssociateIntermediaryAccountWithSomeOtherAccountsAlsoAssociateAccountsWithOtherFirmContactAndCreateSomeFollowUpTaskAsWell(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String activityType=ATE_ATActivityType82;
		String taskSubject=ATE_ATSubject82;
		String taskRelatedTo=ATE_ATRelatedTo12;
		String taskNotes=ATE_ATNotes82;

		String dueDay=ATE_Day9;
		String taskDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(dueDay));
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate, "Activity Timeline", excelLabel.Variable_Name,
				"ATE_082", excelLabel.Advance_Due_Date);

		String taskStatus=ATE_AdvanceStatus8;
		String taskPriority=ATE_AdvancePriority9;

		String activityType1=ATE_ATActivityType83;
		String taskSubject1=ATE_ATSubject83;
		String taskRelatedTo1=ATE_ATRelatedTo13;
		String taskNotes1=ATE_ATNotes83;
		String dueDay1=ATE_Day10;
		String taskDueDate1 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(dueDay1));
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate1, "Activity Timeline", excelLabel.Variable_Name,
				"ATE_083", excelLabel.Advance_Due_Date);
		String taskStatus1=ATE_AdvanceStatus9;
		String taskPriority1=ATE_AdvancePriority10;


		String activityType2=ATE_ATActivityType84;
		String taskSubject2=ATE_ATSubject84;
		String taskRelatedTo2=ATE_ATRelatedTo14;
		String taskNotes2=ATE_ATNotes84;

		String taskStartDay=ATE_Day11;
		String advanceStartDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(taskStartDay));
		ExcelUtils.writeData(AcuityDataSheetFilePath, advanceStartDate, "Activity Timeline", excelLabel.Variable_Name,
				"ATE_084", excelLabel.Advance_Start_Date);

		String taskEndDay=ATE_EndDay3;
		String advanceEndDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(taskEndDay));
		ExcelUtils.writeData(AcuityDataSheetFilePath, advanceStartDate, "Activity Timeline", excelLabel.Variable_Name,
				"ATE_084", excelLabel.Advance_End_Date);

		String[][] basicsection = { { "Subject", taskSubject }, { "Notes", taskNotes }, { "Related_To", taskRelatedTo } };
		String[][] advanceSection = { { "Due Date Only", taskDueDate }, {"Status", taskStatus}, {"Priority", taskPriority} };

		String[][] basicsection1 = { { "Subject", taskSubject1 }, { "Notes", taskNotes1 }, { "Related_To", taskRelatedTo1 } };
		String[][] advanceSection1 = { { "Due Date Only", taskDueDate1 }, {"Status", taskStatus1}, {"Priority", taskPriority1} };

		String[][] basicsection2 = { { "Subject", taskSubject2 }, { "Notes", taskNotes2 }, { "Related_To", taskRelatedTo2 } };
		String[][] advanceSection2 = { { "Start Date", advanceStartDate },{"End Date",advanceEndDate}};	

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (bp.createActivityTimeline(projectName, true, activityType, basicsection, advanceSection, null, null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject);

		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject);
		}	

		if (bp.createActivityTimeline(projectName, true, activityType1, basicsection1, advanceSection1, null, null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject1);

		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject1);
		}

		if (lp.clickAnyCellonCalender(projectName)) {
			log(LogStatus.INFO,"Able to click on Calendar/Event Link",YesNo.No);

			refresh(driver);
			if (bp.updateActivityTimelineRecord(projectName, basicsection2, advanceSection2, null, null,null)) {
				log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject2, YesNo.No);
				sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject2);

			}
			else
			{
				log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject2, YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject2);
			}
		} else {
			log(LogStatus.ERROR,"Not Able to Click on Calendar/Event Link",YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on Calendar/Event Link");	
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}

	@Parameters({ "projectName" })
	@Test
	public void ATETc0045_VerifyImpactOnIntermediaryAccountAcuityTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATERecord3;

		String userName1=crmUser6FirstName+" "+crmUser6LastName;
		String userName2=crmUser7FirstName+" "+crmUser7LastName;
		String userName3=crmUser8FirstName+" "+crmUser8LastName;
		String userName4=crmUser9FirstName+" "+crmUser9LastName;
		String userName5=crmUser10FirstName+" "+crmUser10LastName;
		String userName6=crmUser11FirstName+" "+crmUser11LastName;
		String userName7=crmUser12FirstName+" "+crmUser12LastName;
		String userName8=crmUser13FirstName+" "+crmUser13LastName;
		String userName9=crmUser14FirstName+" "+crmUser14LastName;
		String userName10=crmUser15FirstName+" "+crmUser15LastName;
		String userName11=crmUser16FirstName+" "+crmUser16LastName;


		String[] companiesTaggedName= {ATE_TaggedCompanyName32,ATE_TaggedCompanyName33,ATE_TaggedCompanyName34};
		String[] companiesTaggedTimeReference= {ATE_TaggedCompanyTimeReference32,ATE_TaggedCompanyTimeReference33,ATE_TaggedCompanyTimeReference34};

		String[] peopleTagName={ATE_TaggedPeopleName19,ATE_TaggedPeopleName20};
		String[] peopleTaggedTimeReference={ATE_TaggedPeopleTimeReference19,ATE_TaggedPeopleTimeReference20};
		
		String[] dealTagName={ATE_TaggedDealName17};
		String[] dealTaggedTimeReference={ATE_TaggedDealTimeReference17};

		String contactSectionName[]= {ATE_ContactName22};
		String contactSectionTitle[]= {ATE_ContactTitle22};
		String contactSectionDeal[]= {ATE_ContactDeal22};
		String contactSectionMeetingAndCall[]= {ATE_ContactMeetingAndCall22};
		String contactSectionEmail[]= {ATE_ContactEmail22};

		String[] iconType= {ATE_ATActivityType82,ATE_ATActivityType83,ATE_ATActivityType84};

		String[] subjectName= {ATE_ATSubject82,ATE_ATSubject83,ATE_ATSubject84};

		String[] details= {ATE_ATNotes82,ATE_ATNotes83,ATE_ATNotes84};

		String[] date= {ATE_AdvanceDueDate50,ATE_AdvanceDueDate51,ATE_AdvanceStartDate34};

		String[] userData= {ATE_User82,ATE_User83,ATE_User84};

		String[] user=new String[userData.length];

		for(int i=0; i<userData.length; i++)
		{
			if(userData[i].toLowerCase().trim().equals("pe user 1"))
			{
				user[i]=userName1;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 2"))
			{
				user[i]=userName2;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 3"))
			{
				user[i]=userName3;
			}else if(userData[i].toLowerCase().trim().equals("pe user 4"))
			{
				user[i]=userName4;
			}else if(userData[i].toLowerCase().trim().equals("pe user 5"))
			{
				user[i]=userName5;
			}else if(userData[i].toLowerCase().trim().equals("pe user 6"))
			{
				user[i]=userName6;
			}else if(userData[i].toLowerCase().trim().equals("pe user 7"))
			{
				user[i]=userName7;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 8"))
			{
				user[i]=userName8;
			}else if(userData[i].toLowerCase().trim().equals("pe user 9"))
			{
				user[i]=userName9;
			}else if(userData[i].toLowerCase().trim().equals("pe user 10"))
			{
				user[i]=userName10;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 11"))
			{
				user[i]=userName11;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}
		}		
		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	


					ArrayList<String> result=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, peopleTagName, peopleTaggedTimeReference, dealTagName, dealTaggedTimeReference);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,  "The record name and Time reference are not verifed "+result, YesNo.No);
						sa.assertTrue(false,  "The record name and Time reference are not verifed "+result);
					}
					
					
					ArrayList<String> result10=bp.verifyDefaultSortingOfReferencedTypeOnTaggedSection();
					if(result10.isEmpty())
					{
						log(LogStatus.INFO, "Default decending order of times referenced count have been verified on tagged section", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "Default decending order of times referenced count are not verified on tagged section. "+result10, YesNo.No);
						sa.assertTrue(false, "Default decending order of times referenced count are not verified on tagged section. "+result10);
					}
					
					ArrayList<String> result1=bp.verifyRecordOnContactSectionAcuity(contactSectionName, contactSectionTitle, contactSectionDeal, contactSectionMeetingAndCall, contactSectionEmail);
					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on contact section in Acuity contact", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on contact section in Acuity for contact : "+result1, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on contact section in Acuity for contact :  "+result1);
					}
					if(bp.verifyCountOfRelatedAssociationOnTaggedPopupOnInteractionSctionOfFirstRecord())
					{
						log(LogStatus.INFO, "The count of Tagged record have been verified on tagged popup", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The count of Tagged record is not verified on tagged popup", YesNo.No);
						sa.assertTrue(false, "The count of Tagged record is not verified on tagged popup");
					}

					if(CommonLib.clickUsingJavaScript(driver, bp.getViewAllBtnOnIntration(20), "View All button"))
					{
						log(LogStatus.INFO, "Clicked on View All button of Interaction section", YesNo.No);
						ArrayList<String> result2=bp.verifyRecordsonInteractionsViewAllPopup(iconType,date, subjectName, details, user, subjectName);
						if(result2.isEmpty())
						{
							log(LogStatus.INFO, "The records have been verified on interaction popup in Acuity", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records are not verified on interaction popup in Acuity : "+result2, YesNo.No);
							sa.assertTrue(false,  "The records are not verified on interaction popup in Acuity :  "+result2);
						}

						String xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
						WebElement ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
						if(clickUsingJavaScript(driver, ele, "close button"))
						{
							log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
							sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on View All button of Interaction section", YesNo.No);
						sa.assertTrue(false,  "Not able to click on View All button of Interaction section" );
					}					
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}
	
	
	
	@Parameters({ "projectName" })
	@Test
	public void ATETc0046_VerifyConnectionPopupOnIntermediaryAccountPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATERecord3;
		String contactName=ATE_Contact1;

		String userName1=crmUser6FirstName+" "+crmUser6LastName;
		String userName2=crmUser7FirstName+" "+crmUser7LastName;
		String userName3=crmUser8FirstName+" "+crmUser8LastName;
		String userName4=crmUser9FirstName+" "+crmUser9LastName;
		String userName5=crmUser10FirstName+" "+crmUser10LastName;
		String userName6=crmUser11FirstName+" "+crmUser11LastName;
		String userName7=crmUser12FirstName+" "+crmUser12LastName;
		String userName8=crmUser13FirstName+" "+crmUser13LastName;
		String userName9=crmUser14FirstName+" "+crmUser14LastName;
		String userName10=crmUser15FirstName+" "+crmUser15LastName;
		String userName11=crmUser16FirstName+" "+crmUser16LastName;


		String[] connectionEmail= {ATE_ConnectionEmail13};
		String[] connectionMeetingAndCall= {ATE_ConnectionMeetingAndCall13};
		String[] connectionDeal= {ATE_ConnectionDeals13};
		String[] connectionTitle= {ATE_ConnectionTitle13};
		String[] userData= {ATE_ConnectionTeamMember13};

		String[] user=new String[userData.length];

		for(int i=0; i<userData.length; i++)
		{
			if(userData[i].toLowerCase().trim().equals("pe user 1"))
			{
				user[i]=userName1;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 2"))
			{
				user[i]=userName2;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 3"))
			{
				user[i]=userName3;
			}else if(userData[i].toLowerCase().trim().equals("pe user 4"))
			{
				user[i]=userName4;
			}else if(userData[i].toLowerCase().trim().equals("pe user 5"))
			{
				user[i]=userName5;
			}else if(userData[i].toLowerCase().trim().equals("pe user 6"))
			{
				user[i]=userName6;
			}else if(userData[i].toLowerCase().trim().equals("pe user 7"))
			{
				user[i]=userName7;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 8"))
			{
				user[i]=userName8;
			}else if(userData[i].toLowerCase().trim().equals("pe user 9"))
			{
				user[i]=userName9;
			}else if(userData[i].toLowerCase().trim().equals("pe user 10"))
			{
				user[i]=userName10;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 11"))
			{
				user[i]=userName11;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}
		}

		String[] icon= {ATE_ATActivityType77,ATE_ATActivityType78};

		String[] subjectName= {ATE_ATSubject77,ATE_ATSubject78};

		String[] date= {ATE_AdvanceDueDate47,ATE_AdvanceStartDate32};

		String[] details= {ATE_ATNotes77,ATE_ATNotes78};

		String[] users= {ATE_User77,ATE_User78};

		String[] meetingAndCallUser=new String[users.length];

		for(int i=0; i<users.length; i++)
		{
			if(users[i].toLowerCase().trim().equals("pe user 1"))
			{
				meetingAndCallUser[i]=userName1;
			}
			else if(users[i].toLowerCase().trim().equals("pe user 2"))
			{
				meetingAndCallUser[i]=userName2;
			}
			else if(users[i].toLowerCase().trim().equals("pe user 3"))
			{
				meetingAndCallUser[i]=userName3;
			}else if(users[i].toLowerCase().trim().equals("pe user 4"))
			{
				meetingAndCallUser[i]=userName4;
			}else if(users[i].toLowerCase().trim().equals("pe user 5"))
			{
				meetingAndCallUser[i]=userName5;
			}else if(users[i].toLowerCase().trim().equals("pe user 6"))
			{
				meetingAndCallUser[i]=userName6;
			}else if(users[i].toLowerCase().trim().equals("pe user 7"))
			{
				meetingAndCallUser[i]=userName7;
			}
			else if(users[i].toLowerCase().trim().equals("pe user 8"))
			{
				meetingAndCallUser[i]=userName8;
			}else if(users[i].toLowerCase().trim().equals("pe user 9"))
			{
				meetingAndCallUser[i]=userName9;
			}else if(users[i].toLowerCase().trim().equals("pe user 10"))
			{
				meetingAndCallUser[i]=userName10;
			}
			else if(users[i].toLowerCase().trim().equals("pe user 11"))
			{
				meetingAndCallUser[i]=userName11;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}
		}		



		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

					ThreadSleep(5000);
					ArrayList<String> result=bp.verifyRecordOnConnectionsPopUpOfContactInAcuity(contactName,user,connectionTitle,connectionDeal,connectionMeetingAndCall,connectionEmail);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The records on Connection popup have been verified", YesNo.No);	
					}
					else
					{
						log(LogStatus.ERROR, "The records on Connection popup are not verified. "+result, YesNo.No);	
						sa.assertTrue(false,  "The records on Connection popup are not verified. "+result);
					}			
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}

	@Parameters({ "projectName" })
	@Test
	public void ATETc0047_VerifyIntermediaryContactAcuityTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATE_Contact1;

		String userName1=crmUser6FirstName+" "+crmUser6LastName;
		String userName2=crmUser7FirstName+" "+crmUser7LastName;
		String userName3=crmUser8FirstName+" "+crmUser8LastName;
		String userName4=crmUser9FirstName+" "+crmUser9LastName;
		String userName5=crmUser10FirstName+" "+crmUser10LastName;
		String userName6=crmUser11FirstName+" "+crmUser11LastName;
		String userName7=crmUser12FirstName+" "+crmUser12LastName;
		String userName8=crmUser13FirstName+" "+crmUser13LastName;
		String userName9=crmUser14FirstName+" "+crmUser14LastName;
		String userName10=crmUser15FirstName+" "+crmUser15LastName;
		String userName11=crmUser16FirstName+" "+crmUser16LastName;

		String xPath;
		WebElement ele;


		String[] companiesTaggedName= {ATE_TaggedCompanyName30};
		String[] companiesTaggedTimeReference= {ATE_TaggedCompanyTimeReference30};

		String[] peopleTagName={ATE_TaggedPeopleName17};
		String[] peopleTaggedTimeReference={ATE_TaggedPeopleTimeReference17};
		
		String[] subjectName= {ATE_ATSubject82,ATE_ATSubject83,ATE_ATSubject84};

		String connectionEmail= ATE_ConnectionEmail13;
		String connectionMeetingAndCall= ATE_ConnectionMeetingAndCall13;
		String connectionDeal= ATE_ConnectionDeals13;
		String connectionTitle= ATE_ConnectionTitle13;
		String usersData= ATE_ConnectionTeamMember13;
		String connectionUser="";

		if(usersData.toLowerCase().trim().equals("pe user 1"))
		{
			connectionUser=userName1;
		}
		else if(usersData.toLowerCase().trim().equals("pe user 2"))
		{
			connectionUser=userName2;
		}
		else if(usersData.toLowerCase().trim().equals("pe user 3"))
		{
			connectionUser=userName3;
		}else if(usersData.toLowerCase().trim().equals("pe user 4"))
		{
			connectionUser=userName4;
		}else if(usersData.toLowerCase().trim().equals("pe user 5"))
		{
			connectionUser=userName5;
		}else if(usersData.toLowerCase().trim().equals("pe user 6"))
		{
			connectionUser=userName6;
		}else if(usersData.toLowerCase().trim().equals("pe user 7"))
		{
			connectionUser=userName7;
		}
		else if(usersData.toLowerCase().trim().equals("pe user 8"))
		{
			connectionUser=userName8;
		}else if(usersData.toLowerCase().trim().equals("pe user 9"))
		{
			connectionUser=userName9;
		}else if(usersData.toLowerCase().trim().equals("pe user 10"))
		{
			connectionUser=userName10;
		}
		else if(usersData.toLowerCase().trim().equals("pe user 11"))
		{
			connectionUser=userName11;
		}
		else
		{
			Assertion hardAssert = new Assertion();
			log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
			hardAssert.assertTrue(true == false);
		}

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

					ArrayList<String> result=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, peopleTagName, peopleTaggedTimeReference, null, null);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,  "The record name and Time reference are not verifed "+result, YesNo.No);
						sa.assertTrue(false,  "The record name and Time reference are not verifed "+result);
					}


					ArrayList<String> result10=bp.verifyDefaultSortingOfReferencedTypeOnTaggedSection();
					if(result10.isEmpty())
					{
						log(LogStatus.INFO, "Default decending order of times referenced count have been verified on tagged section", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "Default decending order of times referenced count are not verified on tagged section. "+result10, YesNo.No);
						sa.assertTrue(false, "Default decending order of times referenced count are not verified on tagged section. "+result10);
					}

					
					
					if(CommonLib.clickUsingJavaScript(driver, bp.getViewAllBtnOnIntration(20), "View All button"))
					{
						log(LogStatus.INFO, "Clicked on View All button of Interaction section", YesNo.No);
						ArrayList<String> result2=bp.verifyActivityTimeLineRecordShouldNotVisibleOnViewAllInteractionPopup(subjectName);
						if(result2.isEmpty())
						{
							log(LogStatus.INFO, "The records are not available on View All Interaction section", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records are available on View All Interaction section: "+result2, YesNo.No);
							sa.assertTrue(false,  "The records are available on View All Interaction section  "+result2);
						}

						xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
						ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
						if(clickUsingJavaScript(driver, ele, "close button"))
						{
							log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
							sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on View All button of Interaction section", YesNo.No);
						sa.assertTrue(false,  "Not able to click on View All button of Interaction section" );
					}


					ArrayList<String> result3=bp.verifyRecordOnConnectionsSectionInAcuity(recordName, connectionUser, connectionTitle, connectionDeal, connectionMeetingAndCall, connectionEmail);
					if(result3.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on Connection section for user :"+connectionUser, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are verified on Connection section for user :"+connectionUser+". "+result3, YesNo.No);
						sa.assertTrue(false, "The records are verified on Connection section for user :"+connectionUser+". "+result3);
					}

					
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj2, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj2);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}
	
	@Parameters({ "projectName" })

	@Test
	public void ATETc0048_CreateFollowupTasksAndVerifyImpacts(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		TaskPageBusinessLayer taskBP = new TaskPageBusinessLayer(driver);
		
		String recordName=ATE_Contact2;

		String task1SubjectNameNavigation = ATE_ATSubject79;
		
		String activityType=ATE_ATActivityType85;
		String taskSubject=ATE_ATSubject85;
		String taskRelatedTo=ATE_ATRelatedTo15;
		String taskNotes=ATE_ATNotes85;

		String dueDay=ATE_Day12;
		String taskDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(dueDay));
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate, "Activity Timeline", excelLabel.Variable_Name,
				"ATE_085", excelLabel.Advance_Due_Date);

		String taskStatus=ATE_AdvanceStatus10;
		String taskPriority=ATE_AdvancePriority11;

		String[][] basicsection = { { "Subject", taskSubject }, { "Notes", taskNotes }, { "Related_To", taskRelatedTo } };
		String[][] advanceSection = { { "Due Date Only", taskDueDate }, {"Status", taskStatus}, {"Priority", taskPriority} };

		lp.CRMLogin(crmUser6EmailID, adminPassword);

		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (BP.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab, recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);
				ThreadSleep(4000);
				if (BP.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

					if (BP.clickOnSubjectOfInteractionEitherOnCardOrInViewAllPopUp(task1SubjectNameNavigation)) {
						log(LogStatus.INFO, "Task Detail Page has been open for Record: " + task1SubjectNameNavigation,
								YesNo.No);

						if (click(driver, taskBP.downArrowButton(20), "downArrowButton", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);

							if (click(driver, taskBP.buttonInTheDownArrowList("Create Follow-Up Task", 20),
									"Create Follow-Up Task Button in downArrowButton", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Create Follow-Up Task Button in  Down Arrow Button",
										YesNo.No);

								CommonLib.ThreadSleep(5000);
								if (BP.updateActivityTimelineRecord(projectName, basicsection, advanceSection, null, null,
										null)) {
									log(LogStatus.PASS, "Activity timeline record has been Updated", YesNo.No);
									driver.close();
									driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

								} else {
									log(LogStatus.FAIL, "Activity timeline record has not Updated", YesNo.No);
									sa.assertTrue(false, "Activity timeline record has not Updated");

									driver.close();
									driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
								}

							} else {
								log(LogStatus.ERROR,
										"Not Able Click on Create Follow-Up Task button in Down Arrow Button",
										YesNo.Yes);
								sa.assertTrue(false,
										"Not Able Click on Create Follow-Up Task button in Down Arrow Button");
								driver.close();
								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
							}

						} else {
							log(LogStatus.ERROR, "Not Able Click on Down Arrow Button", YesNo.Yes);
							sa.assertTrue(false, "Not Able Click on Down Arrow Button");
							driver.close();
							driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
						}

					} else {
						log(LogStatus.ERROR, "Task Detail Page has not open for Record: " + task1SubjectNameNavigation,
								YesNo.Yes);
						sa.assertTrue(false, "Task Detail Page has not open for Record: " + task1SubjectNameNavigation);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to open " + recordName + " record", YesNo.No);
				sa.assertTrue(false, "Not able to open " + recordName + " record");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj2, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj2);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	
	@Parameters({ "projectName" })
	@Test
	public void ATETc0049_VerifyImpactOnIntermediaryAccountAcuityTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATERecord3;

		String userName1=crmUser6FirstName+" "+crmUser6LastName;
		String userName2=crmUser7FirstName+" "+crmUser7LastName;
		String userName3=crmUser8FirstName+" "+crmUser8LastName;
		String userName4=crmUser9FirstName+" "+crmUser9LastName;
		String userName5=crmUser10FirstName+" "+crmUser10LastName;
		String userName6=crmUser11FirstName+" "+crmUser11LastName;
		String userName7=crmUser12FirstName+" "+crmUser12LastName;
		String userName8=crmUser13FirstName+" "+crmUser13LastName;
		String userName9=crmUser14FirstName+" "+crmUser14LastName;
		String userName10=crmUser15FirstName+" "+crmUser15LastName;
		String userName11=crmUser16FirstName+" "+crmUser16LastName;


		String[] companiesTaggedName= {ATE_TaggedCompanyName35,ATE_TaggedCompanyName36};
		String[] companiesTaggedTimeReference= {ATE_TaggedCompanyTimeReference35,ATE_TaggedCompanyTimeReference36};

		String[] peopleTagName={ATE_TaggedPeopleName21};
		String[] peopleTaggedTimeReference={ATE_TaggedPeopleTimeReference21};
		
		String[] dealTagName={ATE_TaggedDealName18};
		String[] dealTaggedTimeReference={ATE_TaggedDealTimeReference18};

		String contactSectionName[]= {ATE_ContactName22};
		String contactSectionTitle[]= {ATE_ContactTitle22};
		String contactSectionDeal[]= {ATE_ContactDeal22};
		String contactSectionMeetingAndCall[]= {ATE_ContactMeetingAndCall22};
		String contactSectionEmail[]= {ATE_ContactEmail22};

		String[] iconType= {ATE_ATActivityType85};

		String[] subjectName= {ATE_ATSubject85};

		String[] details= {ATE_ATNotes85};

		String[] date= {ATE_AdvanceDueDate52};

		String[] userData= {ATE_User85};

		String[] user=new String[userData.length];

		for(int i=0; i<userData.length; i++)
		{
			if(userData[i].toLowerCase().trim().equals("pe user 1"))
			{
				user[i]=userName1;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 2"))
			{
				user[i]=userName2;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 3"))
			{
				user[i]=userName3;
			}else if(userData[i].toLowerCase().trim().equals("pe user 4"))
			{
				user[i]=userName4;
			}else if(userData[i].toLowerCase().trim().equals("pe user 5"))
			{
				user[i]=userName5;
			}else if(userData[i].toLowerCase().trim().equals("pe user 6"))
			{
				user[i]=userName6;
			}else if(userData[i].toLowerCase().trim().equals("pe user 7"))
			{
				user[i]=userName7;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 8"))
			{
				user[i]=userName8;
			}else if(userData[i].toLowerCase().trim().equals("pe user 9"))
			{
				user[i]=userName9;
			}else if(userData[i].toLowerCase().trim().equals("pe user 10"))
			{
				user[i]=userName10;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 11"))
			{
				user[i]=userName11;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}
		}		
		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	


					ArrayList<String> result=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, peopleTagName, peopleTaggedTimeReference, dealTagName, dealTaggedTimeReference);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,  "The record name and Time reference are not verifed "+result, YesNo.No);
						sa.assertTrue(false,  "The record name and Time reference are not verifed "+result);
					}
					
					
					ArrayList<String> result10=bp.verifyDefaultSortingOfReferencedTypeOnTaggedSection();
					if(result10.isEmpty())
					{
						log(LogStatus.INFO, "Default decending order of times referenced count have been verified on tagged section", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "Default decending order of times referenced count are not verified on tagged section. "+result10, YesNo.No);
						sa.assertTrue(false, "Default decending order of times referenced count are not verified on tagged section. "+result10);
					}
					
					ArrayList<String> result1=bp.verifyRecordOnContactSectionAcuity(contactSectionName, contactSectionTitle, contactSectionDeal, contactSectionMeetingAndCall, contactSectionEmail);
					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on contact section in Acuity contact", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are not verified on contact section in Acuity for contact : "+result1, YesNo.No);
						sa.assertTrue(false,  "The records are not verified on contact section in Acuity for contact :  "+result1);
					}
					if(bp.verifyCountOfRelatedAssociationOnTaggedPopupOnInteractionSctionOfFirstRecord())
					{
						log(LogStatus.INFO, "The count of Tagged record have been verified on tagged popup", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The count of Tagged record is not verified on tagged popup", YesNo.No);
						sa.assertTrue(false, "The count of Tagged record is not verified on tagged popup");
					}

					if(CommonLib.clickUsingJavaScript(driver, bp.getViewAllBtnOnIntration(20), "View All button"))
					{
						log(LogStatus.INFO, "Clicked on View All button of Interaction section", YesNo.No);
						ArrayList<String> result2=bp.verifyRecordsonInteractionsViewAllPopup(iconType,date, subjectName, details, user, subjectName);
						if(result2.isEmpty())
						{
							log(LogStatus.INFO, "The records have been verified on interaction popup in Acuity", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records are not verified on interaction popup in Acuity : "+result2, YesNo.No);
							sa.assertTrue(false,  "The records are not verified on interaction popup in Acuity :  "+result2);
						}

						String xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
						WebElement ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
						if(clickUsingJavaScript(driver, ele, "close button"))
						{
							log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
							sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on View All button of Interaction section", YesNo.No);
						sa.assertTrue(false,  "Not able to click on View All button of Interaction section" );
					}					
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}
	

	@Parameters({ "projectName" })
	@Test
	public void ATETc0050_VerifyConnectionPopupOnIntermediaryAccountPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATERecord3;
		String contactName=ATE_Contact1;

		String userName1=crmUser6FirstName+" "+crmUser6LastName;
		String userName2=crmUser7FirstName+" "+crmUser7LastName;
		String userName3=crmUser8FirstName+" "+crmUser8LastName;
		String userName4=crmUser9FirstName+" "+crmUser9LastName;
		String userName5=crmUser10FirstName+" "+crmUser10LastName;
		String userName6=crmUser11FirstName+" "+crmUser11LastName;
		String userName7=crmUser12FirstName+" "+crmUser12LastName;
		String userName8=crmUser13FirstName+" "+crmUser13LastName;
		String userName9=crmUser14FirstName+" "+crmUser14LastName;
		String userName10=crmUser15FirstName+" "+crmUser15LastName;
		String userName11=crmUser16FirstName+" "+crmUser16LastName;


		String[] connectionEmail= {ATE_ConnectionEmail13};
		String[] connectionMeetingAndCall= {ATE_ConnectionMeetingAndCall13};
		String[] connectionDeal= {ATE_ConnectionDeals13};
		String[] connectionTitle= {ATE_ConnectionTitle13};
		String[] userData= {ATE_ConnectionTeamMember13};

		String[] user=new String[userData.length];

		for(int i=0; i<userData.length; i++)
		{
			if(userData[i].toLowerCase().trim().equals("pe user 1"))
			{
				user[i]=userName1;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 2"))
			{
				user[i]=userName2;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 3"))
			{
				user[i]=userName3;
			}else if(userData[i].toLowerCase().trim().equals("pe user 4"))
			{
				user[i]=userName4;
			}else if(userData[i].toLowerCase().trim().equals("pe user 5"))
			{
				user[i]=userName5;
			}else if(userData[i].toLowerCase().trim().equals("pe user 6"))
			{
				user[i]=userName6;
			}else if(userData[i].toLowerCase().trim().equals("pe user 7"))
			{
				user[i]=userName7;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 8"))
			{
				user[i]=userName8;
			}else if(userData[i].toLowerCase().trim().equals("pe user 9"))
			{
				user[i]=userName9;
			}else if(userData[i].toLowerCase().trim().equals("pe user 10"))
			{
				user[i]=userName10;
			}
			else if(userData[i].toLowerCase().trim().equals("pe user 11"))
			{
				user[i]=userName11;
			}
			else
			{
				Assertion hardAssert = new Assertion();
				log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
				hardAssert.assertTrue(true == false);
			}
		}

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " record has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

					ThreadSleep(5000);
					ArrayList<String> result=bp.verifyRecordOnConnectionsPopUpOfContactInAcuity(contactName,user,connectionTitle,connectionDeal,connectionMeetingAndCall,connectionEmail);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The records on Connection popup have been verified", YesNo.No);	
					}
					else
					{
						log(LogStatus.ERROR, "The records on Connection popup are not verified. "+result, YesNo.No);	
						sa.assertTrue(false,  "The records on Connection popup are not verified. "+result);
					}			
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}
	
		
	@Parameters({ "projectName" })
	@Test
	public void ATETc0051_VerifyIntermediaryContactAcuityTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATE_Contact1;

		String userName1=crmUser6FirstName+" "+crmUser6LastName;
		String userName2=crmUser7FirstName+" "+crmUser7LastName;
		String userName3=crmUser8FirstName+" "+crmUser8LastName;
		String userName4=crmUser9FirstName+" "+crmUser9LastName;
		String userName5=crmUser10FirstName+" "+crmUser10LastName;
		String userName6=crmUser11FirstName+" "+crmUser11LastName;
		String userName7=crmUser12FirstName+" "+crmUser12LastName;
		String userName8=crmUser13FirstName+" "+crmUser13LastName;
		String userName9=crmUser14FirstName+" "+crmUser14LastName;
		String userName10=crmUser15FirstName+" "+crmUser15LastName;
		String userName11=crmUser16FirstName+" "+crmUser16LastName;

		String xPath;
		WebElement ele;

		String[] companiesTaggedName= {ATE_TaggedCompanyName30};
		String[] companiesTaggedTimeReference= {ATE_TaggedCompanyTimeReference30};

		String[] peopleTagName={ATE_TaggedPeopleName17};
		String[] peopleTaggedTimeReference={ATE_TaggedPeopleTimeReference17};
		
		String[] subjectName= {ATE_ATSubject85};

		String connectionEmail= ATE_ConnectionEmail13;
		String connectionMeetingAndCall= ATE_ConnectionMeetingAndCall13;
		String connectionDeal= ATE_ConnectionDeals13;
		String connectionTitle= ATE_ConnectionTitle13;
		String usersData= ATE_ConnectionTeamMember13;
		String connectionUser="";

		if(usersData.toLowerCase().trim().equals("pe user 1"))
		{
			connectionUser=userName1;
		}
		else if(usersData.toLowerCase().trim().equals("pe user 2"))
		{
			connectionUser=userName2;
		}
		else if(usersData.toLowerCase().trim().equals("pe user 3"))
		{
			connectionUser=userName3;
		}else if(usersData.toLowerCase().trim().equals("pe user 4"))
		{
			connectionUser=userName4;
		}else if(usersData.toLowerCase().trim().equals("pe user 5"))
		{
			connectionUser=userName5;
		}else if(usersData.toLowerCase().trim().equals("pe user 6"))
		{
			connectionUser=userName6;
		}else if(usersData.toLowerCase().trim().equals("pe user 7"))
		{
			connectionUser=userName7;
		}
		else if(usersData.toLowerCase().trim().equals("pe user 8"))
		{
			connectionUser=userName8;
		}else if(usersData.toLowerCase().trim().equals("pe user 9"))
		{
			connectionUser=userName9;
		}else if(usersData.toLowerCase().trim().equals("pe user 10"))
		{
			connectionUser=userName10;
		}
		else if(usersData.toLowerCase().trim().equals("pe user 11"))
		{
			connectionUser=userName11;
		}
		else
		{
			Assertion hardAssert = new Assertion();
			log(LogStatus.ERROR, "user data is not correct on ecxel", YesNo.No);
			hardAssert.assertTrue(true == false);
		}

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	

					ArrayList<String> result=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, peopleTagName, peopleTaggedTimeReference, null, null);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,  "The record name and Time reference are not verifed "+result, YesNo.No);
						sa.assertTrue(false,  "The record name and Time reference are not verifed "+result);
					}


					ArrayList<String> result10=bp.verifyDefaultSortingOfReferencedTypeOnTaggedSection();
					if(result10.isEmpty())
					{
						log(LogStatus.INFO, "Default decending order of times referenced count have been verified on tagged section", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "Default decending order of times referenced count are not verified on tagged section. "+result10, YesNo.No);
						sa.assertTrue(false, "Default decending order of times referenced count are not verified on tagged section. "+result10);
					}

					
					
					if(CommonLib.clickUsingJavaScript(driver, bp.getViewAllBtnOnIntration(20), "View All button"))
					{
						log(LogStatus.INFO, "Clicked on View All button of Interaction section", YesNo.No);
						ArrayList<String> result2=bp.verifyActivityTimeLineRecordShouldNotVisibleOnViewAllInteractionPopup(subjectName);
						if(result2.isEmpty())
						{
							log(LogStatus.INFO, "The records are not available on View All Interaction section", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records are available on View All Interaction section: "+result2, YesNo.No);
							sa.assertTrue(false,  "The records are available on View All Interaction section  "+result2);
						}

						xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
						ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
						if(clickUsingJavaScript(driver, ele, "close button"))
						{
							log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
							sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on View All button of Interaction section", YesNo.No);
						sa.assertTrue(false,  "Not able to click on View All button of Interaction section" );
					}


					ArrayList<String> result3=bp.verifyRecordOnConnectionsSectionInAcuity(recordName, connectionUser, connectionTitle, connectionDeal, connectionMeetingAndCall, connectionEmail);
					if(result3.isEmpty())
					{
						log(LogStatus.INFO, "The records have been verified on Connection section for user :"+connectionUser, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The records are verified on Connection section for user :"+connectionUser+". "+result3, YesNo.No);
						sa.assertTrue(false, "The records are verified on Connection section for user :"+connectionUser+". "+result3);
					}					
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj2, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj2);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}
	
	
	
	@Parameters({ "projectName" })
	@Test
	public void ATETc0052_VerifyUIOfFilterSectionOnAccountAcuityTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATERecord3;
		String contactName=ATE_ContactName22;

		String xPath;
		WebElement ele;
		
		String[] filterType= {"All Types","Emails","Events","Logged Calls","Tasks"};
		String[] recordIconType= {"email","event","call","task"};
		
		String[] filterType1= {"All Types","Events","Logged Calls"};
		String[] recordIconType1= {"event","call"};
		
		String userName1=crmUser6FirstName+" "+crmUser6LastName;
		
		String companyTagName=ATE_TaggedCompanyName35;
		String companyTagTimeReferenceCount=ATE_TaggedCompanyTimeReference35;
		
		String peopleTagName=ATE_TaggedPeopleName21;
		String peopleTagTimeReferenceCount=ATE_TaggedPeopleTimeReference21;
		
		String dealTagName=ATE_TaggedDealName18;
		String dealTagTimeReferenceCount=ATE_TaggedDealTimeReference18;
		
		
		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	
					
					if (click(driver, bp.getTaggedRecordName("Companies", 30), "Companies tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Companies tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("Companies", companyTagName, companyTagTimeReferenceCount,30), companyTagName+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+companyTagName,YesNo.No);
							
							ArrayList<String> result=bp.verifyFilterIconAndFilterRecordsOnInteractionsPopup(filterType,recordIconType);
							if(result.isEmpty())
							{
								log(LogStatus.INFO, "The filter icon, filter value, and records after selecting filter have been verified after clicking on the count of "+companyTagName+" from people tag. "+result, YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "The filter icon, filter value, and records after selecting filter not working properly after clicking on the count of "+companyTagName+" from people tag. "+result, YesNo.No);
								sa.assertTrue(false,  "The filter icon, filter value, and records after selecting filter not working properly after clicking on the count of "+companyTagName+" from people tag. "+result);
							}				
							xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
							ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
							if(clickUsingJavaScript(driver, ele, "close button"))
							{
								log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
								sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+companyTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+companyTagName);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Companies tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Companies tab name");
					}
					
					
					if (click(driver, bp.getTaggedRecordName("People", 30), "People tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on People tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("People", peopleTagName, peopleTagTimeReferenceCount,30), peopleTagName+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+peopleTagName,YesNo.No);

							ArrayList<String> result1=bp.verifyFilterIconAndFilterRecordsOnInteractionsPopup(filterType,recordIconType);
							if(result1.isEmpty())
							{
								log(LogStatus.INFO, "The filter icon, filter value, and records after selecting filter have been verified after clicking on the count of "+peopleTagName+" from people tag", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "The filter icon, filter value, and records after selecting filter not working properly after clicking on the count of "+peopleTagName+" from people tag. "+result1, YesNo.No);
								sa.assertTrue(false,  "The filter icon, filter value, and records after selecting filter not working properly after clicking on the count of "+peopleTagName+" from people tag. "+result1);
							}
							
							xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
							ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
							if(clickUsingJavaScript(driver, ele, "close button"))
							{
								log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
								sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+peopleTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+peopleTagName);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on People tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on People tab name");
					}
					
					if (click(driver, bp.getTaggedRecordName("Deals", 30), "Deals tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Deals tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("Deals", dealTagName, dealTagTimeReferenceCount,30), dealTagName+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+dealTagName,YesNo.No);

							ArrayList<String> result2=bp.verifyFilterIconAndFilterRecordsOnInteractionsPopup(filterType,recordIconType);
							if(result2.isEmpty())
							{
								log(LogStatus.INFO, "The filter icon, filter value, and records after selecting filter have been verified after clicking on the count of "+dealTagName+" from people tag. "+result2, YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "The filter icon, filter value, and records after selecting filter not working properly after clicking on the count of "+dealTagName+" from people tag. "+result2, YesNo.No);
								sa.assertTrue(false,  "The filter icon, filter value, and records after selecting filter not working properly after clicking on the count of "+dealTagName+" from people tag. "+result2);
							}	
							
							xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
							ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
							if(clickUsingJavaScript(driver, ele, "close button"))
							{
								log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
								sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+dealTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+dealTagName);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Deals tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Deals tab name");
					}				
								
					if(CommonLib.clickUsingJavaScript(driver, bp.getViewAllBtnOnIntration(20), "View All button"))
					{
						log(LogStatus.INFO, "Clicked on View All button of Interaction section", YesNo.No);
						ArrayList<String> result3=bp.verifyFilterIconAndFilterRecordsOnInteractionsPopup(filterType,recordIconType);
						if(result3.isEmpty())
						{
							log(LogStatus.INFO, "The filter icon, filter value, and records after selecting filter have been verified after clicking on View All button"+result3, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The filter icon, filter value, and records after selecting filter not working properly after clicking on View All button. "+result3, YesNo.No);
							sa.assertTrue(false,  "The filter icon, filter value, and records after selecting filter not working properly after clicking on View All button. "+result3);
						}	

						xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
						ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
						if(clickUsingJavaScript(driver, ele, "close button"))
						{
							log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
							sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on View All button of Interaction section", YesNo.No);
						sa.assertTrue(false,  "Not able to click on View All button of Interaction section" );
					}	
									
					xPath="//a[text()='"+contactName+"']/ancestor::tr//td[@data-label='Meetings and Calls']//button";
					ele=FindElement(driver, xPath, "meeting and call count of "+contactName, action.SCROLLANDBOOLEAN, 20);
					if(click(driver, ele, contactName+" meetings and call count", action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on the count of meeting and call of "+contactName, YesNo.No);

						ArrayList<String> result4=bp.verifyFilterIconAndFilterRecordsOnMeetingAndCallPopup(filterType1,recordIconType1);
						if(result4.isEmpty())
						{
							log(LogStatus.INFO, "The filter icon, filter value, and records after selecting filter have been verified after clicking on meeting and call count of "+contactName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The filter icon, filter value, and records after selecting filter not working properly after clicking on meeting and call count of "+contactName+". "+result4, YesNo.No);
							sa.assertTrue(false,  "The filter icon, filter value, and records after selecting filter not working properly after clicking on meeting and call count of "+contactName+". "+result4);
						}
						xPath="//h2[contains(text(),'Meetings and Calls with')]/../button//lightning-icon";
						ele=FindElement(driver, xPath, "Meetings and Calls with popup close", action.SCROLLANDBOOLEAN, 20);
						if(clickUsingJavaScript(driver, ele, "close button"))
						{
							log(LogStatus.INFO, "clicked on close button of all Meetings and Calls with popup", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on close button of all Meetings and Calls with popup", YesNo.No);
							sa.assertTrue(false,  "Not able to click on close button of all Meetings and Calls with popup");
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+contactName, YesNo.No);
						sa.assertTrue(false, "Not able to click on the count of meeting and call of "+contactName);
					}
					
					
					xPath="//a[text()='"+contactName+"']/ancestor::tr//td//button[@title='Connections']";
					ele=FindElement(driver, xPath, "Connection icon of "+contactName, action.SCROLLANDBOOLEAN, 20);
					if(clickUsingJavaScript(driver, ele, "Connection icon of "+contactName))
					{
						log(LogStatus.INFO, "Clicked on the connection icon of "+contactName, YesNo.No);
						
						xPath="//a[text()='"+userName1+"']/ancestor::tr//td[@data-label='Meetings and Calls']//button";
						ele=FindElement(driver, xPath, "meeting and call count of "+userName1, action.SCROLLANDBOOLEAN, 20);
						if(click(driver, ele, userName1+" meetings and call count", action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, "clicked on the count of meeting and call of "+userName1, YesNo.No);

							ArrayList<String> result5=bp.verifyFilterIconAndFilterRecordsOnMeetingAndCallPopup(filterType1,recordIconType1);
							if(result5.isEmpty())
							{
								log(LogStatus.INFO, "The filter icon, filter value, and records after selecting filter have been verified after clicking on meeting and call count of "+userName1, YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "The filter icon, filter value, and records after selecting filter not working properly after clicking on meeting and call count of "+userName1+". "+result5, YesNo.No);
								sa.assertTrue(false,  "The filter icon, filter value, and records after selecting filter not working properly after clicking on meeting and call count of "+userName1+". "+result5);
							}
							xPath="//h2[contains(text(),'Meetings and Calls with')]/../button//lightning-icon";
							ele=FindElement(driver, xPath, "Meetings and Calls with popup close", action.SCROLLANDBOOLEAN, 20);
							if(clickUsingJavaScript(driver, ele, "close button"))
							{
								log(LogStatus.INFO, "clicked on close button of all Meetings and Calls with popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on close button of all Meetings and Calls with popup", YesNo.No);
								sa.assertTrue(false,  "Not able to click on close button of all Meetings and Calls with popup");
							}
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+userName1, YesNo.No);
							sa.assertTrue(false, "Not able to click on the count of meeting and call of "+userName1);
						}	
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on the connection icon of "+contactName, YesNo.No);
						sa.assertTrue(false, "Not able to click on the connection icon of "+contactName);
					}
					
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}
	
	
	@Parameters({ "projectName" })
	@Test
	public void ATETc0053_VerifyUIOfFilterSectionOnContactAcuityTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATE_Contact1;
		String contactName=ATE_ContactName22;

		String xPath;
		WebElement ele;
		
		String[] filterType= {"All Types","Emails","Events","Logged Calls","Tasks"};
		String[] recordIconType= {"email","event","call","task"};
		
		String[] filterType1= {"All Types","Events","Logged Calls"};
		String[] recordIconType1= {"event","call"};
		
		String userName1=crmUser6FirstName+" "+crmUser6LastName;
		
		String companyTagName=ATE_TaggedCompanyName30;
		String companyTagTimeReferenceCount=ATE_TaggedCompanyTimeReference30;
		
		String peopleTagName=ATE_TaggedPeopleName17;
		String peopleTagTimeReferenceCount=ATE_TaggedPeopleTimeReference17;
		
		String dealTagName=ATE_TaggedDealName19;
		String dealTagTimeReferenceCount=ATE_TaggedDealTimeReference19;
		
		
		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);
			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

				if (bp.clicktabOnPage("Acuity")) {
					log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);	
					
					if (click(driver, bp.getTaggedRecordName("Companies", 30), "Companies tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Companies tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("Companies", companyTagName, companyTagTimeReferenceCount,30), companyTagName+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+companyTagName,YesNo.No);
							
							ArrayList<String> result=bp.verifyFilterIconAndFilterRecordsOnInteractionsPopup(filterType,recordIconType);
							if(result.isEmpty())
							{
								log(LogStatus.INFO, "The filter icon, filter value, and records after selecting filter have been verified after clicking on the count of "+companyTagName+" from people tag. "+result, YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "The filter icon, filter value, and records after selecting filter not working properly after clicking on the count of "+companyTagName+" from people tag. "+result, YesNo.No);
								sa.assertTrue(false,  "The filter icon, filter value, and records after selecting filter not working properly after clicking on the count of "+companyTagName+" from people tag. "+result);
							}				
							xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
							ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
							if(clickUsingJavaScript(driver, ele, "close button"))
							{
								log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
								sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+companyTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+companyTagName);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Companies tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Companies tab name");
					}
					
					
					if (click(driver, bp.getTaggedRecordName("People", 30), "People tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on People tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("People", peopleTagName, peopleTagTimeReferenceCount,30), peopleTagName+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+peopleTagName,YesNo.No);

							ArrayList<String> result1=bp.verifyFilterIconAndFilterRecordsOnInteractionsPopup(filterType,recordIconType);
							if(result1.isEmpty())
							{
								log(LogStatus.INFO, "The filter icon, filter value, and records after selecting filter have been verified after clicking on the count of "+peopleTagName+" from people tag", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "The filter icon, filter value, and records after selecting filter not working properly after clicking on the count of "+peopleTagName+" from people tag. "+result1, YesNo.No);
								sa.assertTrue(false,  "The filter icon, filter value, and records after selecting filter not working properly after clicking on the count of "+peopleTagName+" from people tag. "+result1);
							}
							
							xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
							ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
							if(clickUsingJavaScript(driver, ele, "close button"))
							{
								log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
								sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+peopleTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+peopleTagName);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on People tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on People tab name");
					}
					
					if (click(driver, bp.getTaggedRecordName("Deals", 30), "Deals tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Deals tab name", YesNo.No);
						if (click(driver, bp.getTaggedRecordTimeReference("Deals", dealTagName, dealTagTimeReferenceCount,30), dealTagName+" on Company Tagged",action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Time reference count of "+dealTagName,YesNo.No);

							ArrayList<String> result2=bp.verifyFilterIconAndFilterRecordsOnInteractionsPopup(filterType,recordIconType);
							if(result2.isEmpty())
							{
								log(LogStatus.INFO, "The filter icon, filter value, and records after selecting filter have been verified after clicking on the count of "+dealTagName+" from people tag. "+result2, YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "The filter icon, filter value, and records after selecting filter not working properly after clicking on the count of "+dealTagName+" from people tag. "+result2, YesNo.No);
								sa.assertTrue(false,  "The filter icon, filter value, and records after selecting filter not working properly after clicking on the count of "+dealTagName+" from people tag. "+result2);
							}	
							
							xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
							ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
							if(clickUsingJavaScript(driver, ele, "close button"))
							{
								log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
								sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Time reference count of "+dealTagName,YesNo.No);
							sa.assertTrue(false,  "Not able to click on Time reference count of "+dealTagName);
						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on Deals tab name", YesNo.No);
						sa.assertTrue(false,  "Not able to click on Deals tab name");
					}				
								
					if(CommonLib.clickUsingJavaScript(driver, bp.getViewAllBtnOnIntration(20), "View All button"))
					{
						log(LogStatus.INFO, "Clicked on View All button of Interaction section", YesNo.No);
						ArrayList<String> result3=bp.verifyFilterIconAndFilterRecordsOnInteractionsPopup(filterType,recordIconType);
						if(result3.isEmpty())
						{
							log(LogStatus.INFO, "The filter icon, filter value, and records after selecting filter have been verified after clicking on View All button"+result3, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The filter icon, filter value, and records after selecting filter not working properly after clicking on View All button. "+result3, YesNo.No);
							sa.assertTrue(false,  "The filter icon, filter value, and records after selecting filter not working properly after clicking on View All button. "+result3);
						}	

						xPath="//h2[contains(text(),'All Interactions with')]/../button//lightning-icon";
						ele=FindElement(driver, xPath, "All Interaction popup close", action.SCROLLANDBOOLEAN, 20);
						if(clickUsingJavaScript(driver, ele, "close button"))
						{
							log(LogStatus.INFO, "clicked on close button of all Interaction popup", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on close button of all Interaction popup", YesNo.No);
							sa.assertTrue(false,  "Not able to click on close button of all Interaction popup");
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on View All button of Interaction section", YesNo.No);
						sa.assertTrue(false,  "Not able to click on View All button of Interaction section" );
					}	
													
						xPath="//a[text()='"+userName1+"']/ancestor::tr//td[@data-label='Meetings and Calls']//button";
						ele=FindElement(driver, xPath, "meeting and call count of "+userName1, action.SCROLLANDBOOLEAN, 20);
						if(click(driver, ele, userName1+" meetings and call count", action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, "clicked on the count of meeting and call of "+userName1, YesNo.No);

							ArrayList<String> result5=bp.verifyFilterIconAndFilterRecordsOnMeetingAndCallPopup(filterType1,recordIconType1);
							if(result5.isEmpty())
							{
								log(LogStatus.INFO, "The filter icon, filter value, and records after selecting filter have been verified after clicking on meeting and call count of "+userName1, YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "The filter icon, filter value, and records after selecting filter not working properly after clicking on meeting and call count of "+userName1+". "+result5, YesNo.No);
								sa.assertTrue(false,  "The filter icon, filter value, and records after selecting filter not working properly after clicking on meeting and call count of "+userName1+". "+result5);
							}
							xPath="//h2[contains(text(),'Meetings and Calls with')]/../button//lightning-icon";
							ele=FindElement(driver, xPath, "Meetings and Calls with popup close", action.SCROLLANDBOOLEAN, 20);
							if(clickUsingJavaScript(driver, ele, "close button"))
							{
								log(LogStatus.INFO, "clicked on close button of all Meetings and Calls with popup", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on close button of all Meetings and Calls with popup", YesNo.No);
								sa.assertTrue(false,  "Not able to click on close button of all Meetings and Calls with popup");
							}
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+userName1, YesNo.No);
							sa.assertTrue(false, "Not able to click on the count of meeting and call of "+userName1);
						}	
					
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Acuity tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on Acuity tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open record "+recordName, YesNo.No);
				sa.assertTrue(false,  "Not able to open record "+recordName);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab"+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab "+tabObj1);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}

}
