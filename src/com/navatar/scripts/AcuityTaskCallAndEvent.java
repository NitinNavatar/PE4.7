package com.navatar.scripts;

import static com.navatar.generic.CommonLib.FindElement;
import static com.navatar.generic.CommonLib.ThreadSleep;
import static com.navatar.generic.CommonLib.click;
import static com.navatar.generic.CommonLib.exit;
import static com.navatar.generic.CommonLib.log;
import static com.navatar.generic.CommonLib.refresh;
import static com.navatar.generic.CommonLib.removeNumbersFromString;
import static com.navatar.generic.CommonLib.switchOnWindow;
import static com.navatar.generic.CommonLib.switchToDefaultContent;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;
import static com.navatar.generic.SmokeCommonVariables.superAdminUserName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.IconType;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;

public class AcuityTaskCallAndEvent extends BaseLib {

	public boolean isInstitutionRecord=false;
	

	@Parameters({ "projectName" })
	@Test
	public void ATCETc001_CreateCRMUser(String projectName) {
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
	public void ATCETc002_VerifyUIOfAcuityTabOnCompanyRecord(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATCERecord1;		
			
		String sectionHeader=ATCE_Section1;
		String tabsOnTagged=ATCE_Tabs1;
		String defaultTabOntagged="Firms";
		String message=bp.acuityDefaultMessage;

		String contactHeader=ATCE_ContactHeader1;

		String[] arrSectionHeader=sectionHeader.split("<break>");		
		List<String> sectionHeaderName = new ArrayList<String>(Arrays.asList(arrSectionHeader));

		String[] arrTabName= tabsOnTagged.split("<break>");		
		List<String> tabNameOnTagged = new ArrayList<String>(Arrays.asList(arrTabName));

		String[] arrContactHeader=contactHeader.split("<break>");
		List<String> contactHeaders = new ArrayList<String>(Arrays.asList(arrContactHeader));
		
		List<String> blankList=new ArrayList<String>();

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);
				if(bp.clicktabOnPage(TabName.Acuity.toString()))
				{
					log(LogStatus.INFO, "Clicked on Acuity Tab", YesNo.No);
					ArrayList<String> result3=bp.verifySectionsAndTooltipOnAcuityTab(sectionHeaderName,sectionHeaderName);
					if(result3.isEmpty())
					{
						log(LogStatus.INFO, "Section Headers have been verified on acuity tab", YesNo.No);
					}					
					else
					{
						log(LogStatus.ERROR, "Section headers and Tooltip are not verified on acuity tab. "+result3, YesNo.No);
						sa.assertTrue(false, "Section headers and Tooltip are not verified on acuity tab. "+result3);
					}
					ArrayList<String> result4=bp.verifyTabsOnTaggedSection(tabNameOnTagged,defaultTabOntagged);
					if(result4.isEmpty())
					{
						log(LogStatus.INFO, "Default selected Tab and Tabs have been verified on Tagged section. ", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "Default selected Tab and Tabs are not verified on Tagged section. "+result4, YesNo.No);
						sa.assertTrue(false, "Default selected Tab and Tabs are not verified on Tagged section. "+result4);
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

					ArrayList<String> result1=bp.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(message, contactHeaders, message, blankList, null,blankList,null,blankList,null);

					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The header name and message have been verified on Interaction and Contacts ", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The header name and message are not verified on Interaction and Contacts. "+result1, YesNo.No);
						sa.assertTrue(false, "The header name and message are not verified on Interaction and Contacts "+result1);
					}

					ArrayList<String> result2=bp.verifyToolTipOnDealsConnctionsAndContactsHeader(blankList, contactHeaders, blankList,blankList);
					if(result2.isEmpty())
					{
						log(LogStatus.INFO, "The Tooltip on Contact header have been verified", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The Tooltip on Contact header are not verified. "+result2, YesNo.No);
						sa.assertTrue(false, "The Tooltip on Contact header are not verified. "+result2);
					}
					
					if(bp.verifyUIOfLogACallAndCreateTaskButtonOnAcuity(true, true, true, true, false, false))
					{
						log(LogStatus.INFO, "The UI of Log a Call, Create Task and Add Contact icon on contact section have been verified", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The UI of Log a Call, Create Task and Add Contact icon on contact section are not verified", YesNo.No);
						sa.assertTrue(false, "The UI of Log a Call, Create Task and Add Contact icon on contact section are not verified");
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
	public void ATCETc003_VerifyUIOfAcuityTabOnInstitutionRecord(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATCERecord3;		
			
		String sectionHeader=ATCE_Section2;
		String tabsOnTagged=ATCE_Tabs2;
		String defaultTabOntagged="Firms";
		String message=bp.acuityDefaultMessage;

		String contactHeader=ATCE_ContactHeader1;

		String[] arrSectionHeader=sectionHeader.split("<break>");		
		List<String> sectionHeaderName = new ArrayList<String>(Arrays.asList(arrSectionHeader));

		String[] arrTabName= tabsOnTagged.split("<break>");		
		List<String> tabNameOnTagged = new ArrayList<String>(Arrays.asList(arrTabName));

		String[] arrContactHeader=contactHeader.split("<break>");
		List<String> contactHeaders = new ArrayList<String>(Arrays.asList(arrContactHeader));

		
		List<String> blankList=new ArrayList<String>();

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);
				if(bp.clicktabOnPage(TabName.Acuity.toString()))
				{
					log(LogStatus.INFO, "Clicked on Acuity Tab", YesNo.No);
					ArrayList<String> result3=bp.verifySectionsAndTooltipOnAcuityTab(sectionHeaderName,sectionHeaderName);
					if(result3.isEmpty())
					{
						log(LogStatus.INFO, "Section Headers have been verified on acuity tab", YesNo.No);
					}					
					else
					{
						log(LogStatus.ERROR, "Section headers and Tooltip are not verified on acuity tab. "+result3, YesNo.No);
						sa.assertTrue(false, "Section headers and Tooltip are not verified on acuity tab. "+result3);
					}
					ArrayList<String> result4=bp.verifyTabsOnTaggedSection(tabNameOnTagged,defaultTabOntagged);
					if(result4.isEmpty())
					{
						log(LogStatus.INFO, "Default selected Tab and Tabs have been verified on Tagged section. ", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "Default selected Tab and Tabs are not verified on Tagged section. "+result4, YesNo.No);
						sa.assertTrue(false, "Default selected Tab and Tabs are not verified on Tagged section. "+result4);
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

					ArrayList<String> result1=bp.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(message, contactHeaders, message, blankList, null,blankList,null,blankList,null);

					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The header name and message have been verified on Interaction and Contacts ", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The header name and message are not verified on Interaction and Contacts. "+result1, YesNo.No);
						sa.assertTrue(false, "The header name and message are not verified on Interaction and Contacts "+result1);
					}

					ArrayList<String> result2=bp.verifyToolTipOnDealsConnctionsAndContactsHeader(blankList, contactHeaders, blankList,blankList);
					if(result2.isEmpty())
					{
						log(LogStatus.INFO, "The Tooltip on Contact header have been verified", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The Tooltip on Contact header are not verified. "+result2, YesNo.No);
						sa.assertTrue(false, "The Tooltip on Contact header are not verified. "+result2);
					}
					
					if(bp.verifyUIOfLogACallAndCreateTaskButtonOnAcuity(true, true, true, true, false, false))
					{
						log(LogStatus.INFO, "The UI of Log a Call, Create Task and Add Contact icon on contact section have been verified", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The UI of Log a Call, Create Task and Add Contact icon on contact section are not verified", YesNo.No);
						sa.assertTrue(false, "The UI of Log a Call, Create Task and Add Contact icon on contact section are not verified");
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
	public void ATCETc004_VerifyUIOfAcuityTabOnAdvisorRecord(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATCERecord5;		
			
		String sectionHeader=ATCE_Section1;
		String tabsOnTagged=ATCE_Tabs1;
		String defaultTabOntagged="Firms";
		String message=bp.acuityDefaultMessage;

		String contactHeader=ATCE_ContactHeader1;

		String[] arrSectionHeader=sectionHeader.split("<break>");		
		List<String> sectionHeaderName = new ArrayList<String>(Arrays.asList(arrSectionHeader));

		String[] arrTabName= tabsOnTagged.split("<break>");		
		List<String> tabNameOnTagged = new ArrayList<String>(Arrays.asList(arrTabName));

		String[] arrContactHeader=contactHeader.split("<break>");
		List<String> contactHeaders = new ArrayList<String>(Arrays.asList(arrContactHeader));

		List<String> blankList=new ArrayList<String>();

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);
				if(bp.clicktabOnPage(TabName.Acuity.toString()))
				{
					log(LogStatus.INFO, "Clicked on Acuity Tab", YesNo.No);
					ArrayList<String> result3=bp.verifySectionsAndTooltipOnAcuityTab(sectionHeaderName,sectionHeaderName);
					if(result3.isEmpty())
					{
						log(LogStatus.INFO, "Section Headers have been verified on acuity tab", YesNo.No);
					}					
					else
					{
						log(LogStatus.ERROR, "Section headers and Tooltip are not verified on acuity tab. "+result3, YesNo.No);
						sa.assertTrue(false, "Section headers and Tooltip are not verified on acuity tab. "+result3);
					}
					ArrayList<String> result4=bp.verifyTabsOnTaggedSection(tabNameOnTagged,defaultTabOntagged);
					if(result4.isEmpty())
					{
						log(LogStatus.INFO, "Default selected Tab and Tabs have been verified on Tagged section. ", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "Default selected Tab and Tabs are not verified on Tagged section. "+result4, YesNo.No);
						sa.assertTrue(false, "Default selected Tab and Tabs are not verified on Tagged section. "+result4);
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

					ArrayList<String> result1=bp.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(message, contactHeaders, message, blankList, null,blankList,null,blankList,null);

					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The header name and message have been verified on Interaction and Contacts ", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The header name and message are not verified on Interaction and Contacts. "+result1, YesNo.No);
						sa.assertTrue(false, "The header name and message are not verified on Interaction and Contacts "+result1);
					}

					ArrayList<String> result2=bp.verifyToolTipOnDealsConnctionsAndContactsHeader(blankList, contactHeaders, blankList,blankList);
					if(result2.isEmpty())
					{
						log(LogStatus.INFO, "The Tooltip on Contact header have been verified", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The Tooltip on Contact header are not verified. "+result2, YesNo.No);
						sa.assertTrue(false, "The Tooltip on Contact header are not verified. "+result2);
					}
					
					if(bp.verifyUIOfLogACallAndCreateTaskButtonOnAcuity(true, true, true, true, false, false))
					{
						log(LogStatus.INFO, "The UI of Log a Call, Create Task and Add Contact icon on contact section have been verified", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The UI of Log a Call, Create Task and Add Contact icon on contact section are not verified", YesNo.No);
						sa.assertTrue(false, "The UI of Log a Call, Create Task and Add Contact icon on contact section are not verified");
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
	public void ATCETc005_VerifyUIOfAcuityTabOnLenderRecord(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATCERecord7;		
			
		String sectionHeader=ATCE_Section1;
		String tabsOnTagged=ATCE_Tabs1;
		String defaultTabOntagged="Firms";
		String message=bp.acuityDefaultMessage;

		String contactHeader=ATCE_ContactHeader1;

		String[] arrSectionHeader=sectionHeader.split("<break>");		
		List<String> sectionHeaderName = new ArrayList<String>(Arrays.asList(arrSectionHeader));

		String[] arrTabName= tabsOnTagged.split("<break>");		
		List<String> tabNameOnTagged = new ArrayList<String>(Arrays.asList(arrTabName));

		String[] arrContactHeader=contactHeader.split("<break>");
		List<String> contactHeaders = new ArrayList<String>(Arrays.asList(arrContactHeader));

		List<String> blankList=new ArrayList<String>();

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);
				if(bp.clicktabOnPage(TabName.Acuity.toString()))
				{
					log(LogStatus.INFO, "Clicked on Acuity Tab", YesNo.No);
					ArrayList<String> result3=bp.verifySectionsAndTooltipOnAcuityTab(sectionHeaderName,sectionHeaderName);
					if(result3.isEmpty())
					{
						log(LogStatus.INFO, "Section Headers have been verified on acuity tab", YesNo.No);
					}					
					else
					{
						log(LogStatus.ERROR, "Section headers and Tooltip are not verified on acuity tab. "+result3, YesNo.No);
						sa.assertTrue(false, "Section headers and Tooltip are not verified on acuity tab. "+result3);
					}
					ArrayList<String> result4=bp.verifyTabsOnTaggedSection(tabNameOnTagged,defaultTabOntagged);
					if(result4.isEmpty())
					{
						log(LogStatus.INFO, "Default selected Tab and Tabs have been verified on Tagged section. ", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "Default selected Tab and Tabs are not verified on Tagged section. "+result4, YesNo.No);
						sa.assertTrue(false, "Default selected Tab and Tabs are not verified on Tagged section. "+result4);
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

					ArrayList<String> result1=bp.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(message, contactHeaders, message, blankList, null,blankList,null,blankList,null);

					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The header name and message have been verified on Interaction and Contacts ", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The header name and message are not verified on Interaction and Contacts. "+result1, YesNo.No);
						sa.assertTrue(false, "The header name and message are not verified on Interaction and Contacts "+result1);
					}

					ArrayList<String> result2=bp.verifyToolTipOnDealsConnctionsAndContactsHeader(blankList, contactHeaders, blankList,blankList);
					if(result2.isEmpty())
					{
						log(LogStatus.INFO, "The Tooltip on Contact header have been verified", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The Tooltip on Contact header are not verified. "+result2, YesNo.No);
						sa.assertTrue(false, "The Tooltip on Contact header are not verified. "+result2);
					}
					
					if(bp.verifyUIOfLogACallAndCreateTaskButtonOnAcuity(true, true, true, true, false, false))
					{
						log(LogStatus.INFO, "The UI of Log a Call, Create Task and Add Contact icon on contact section have been verified", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The UI of Log a Call, Create Task and Add Contact icon on contact section are not verified", YesNo.No);
						sa.assertTrue(false, "The UI of Log a Call, Create Task and Add Contact icon on contact section are not verified");
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
	public void ATCETc006_VerifyUIOfAcuityTabOnIntermediaryRecord(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATCERecord9;		
			
		String sectionHeader=ATCE_Section1;
		String tabsOnTagged=ATCE_Tabs1;
		String defaultTabOntagged="Firms";
		String message=bp.acuityDefaultMessage;

		String contactHeader=ATCE_ContactHeader1;

		String[] arrSectionHeader=sectionHeader.split("<break>");		
		List<String> sectionHeaderName = new ArrayList<String>(Arrays.asList(arrSectionHeader));

		String[] arrTabName= tabsOnTagged.split("<break>");		
		List<String> tabNameOnTagged = new ArrayList<String>(Arrays.asList(arrTabName));

		String[] arrContactHeader=contactHeader.split("<break>");
		List<String> contactHeaders = new ArrayList<String>(Arrays.asList(arrContactHeader));

		List<String> blankList=new ArrayList<String>();

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);
				if(bp.clicktabOnPage(TabName.Acuity.toString()))
				{
					log(LogStatus.INFO, "Clicked on Acuity Tab", YesNo.No);
					ArrayList<String> result3=bp.verifySectionsAndTooltipOnAcuityTab(sectionHeaderName,sectionHeaderName);
					if(result3.isEmpty())
					{
						log(LogStatus.INFO, "Section Headers have been verified on acuity tab", YesNo.No);
					}					
					else
					{
						log(LogStatus.ERROR, "Section headers and Tooltip are not verified on acuity tab. "+result3, YesNo.No);
						sa.assertTrue(false, "Section headers and Tooltip are not verified on acuity tab. "+result3);
					}
					ArrayList<String> result4=bp.verifyTabsOnTaggedSection(tabNameOnTagged,defaultTabOntagged);
					if(result4.isEmpty())
					{
						log(LogStatus.INFO, "Default selected Tab and Tabs have been verified on Tagged section. ", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "Default selected Tab and Tabs are not verified on Tagged section. "+result4, YesNo.No);
						sa.assertTrue(false, "Default selected Tab and Tabs are not verified on Tagged section. "+result4);
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

					ArrayList<String> result1=bp.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(message, contactHeaders, message, blankList, null,blankList,null,blankList,null);

					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The header name and message have been verified on Interaction and Contacts ", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The header name and message are not verified on Interaction and Contacts. "+result1, YesNo.No);
						sa.assertTrue(false, "The header name and message are not verified on Interaction and Contacts "+result1);
					}

					ArrayList<String> result2=bp.verifyToolTipOnDealsConnctionsAndContactsHeader(blankList, contactHeaders, blankList,blankList);
					if(result2.isEmpty())
					{
						log(LogStatus.INFO, "The Tooltip on Contact header have been verified", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The Tooltip on Contact header are not verified. "+result2, YesNo.No);
						sa.assertTrue(false, "The Tooltip on Contact header are not verified. "+result2);
					}
					
					if(bp.verifyUIOfLogACallAndCreateTaskButtonOnAcuity(true, true, true, true, false, false))
					{
						log(LogStatus.INFO, "The UI of Log a Call, Create Task and Add Contact icon on contact section have been verified", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The UI of Log a Call, Create Task and Add Contact icon on contact section are not verified", YesNo.No);
						sa.assertTrue(false, "The UI of Log a Call, Create Task and Add Contact icon on contact section are not verified");
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
	public void ATCETc007_VerifyUIOfAcuityTabOnPortfolioCompanyRecord(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATCERecord11;		
			
		String sectionHeader=ATCE_Section1;
		String tabsOnTagged=ATCE_Tabs1;
		String defaultTabOntagged="Firms";
		String message=bp.acuityDefaultMessage;

		String contactHeader=ATCE_ContactHeader1;

		String[] arrSectionHeader=sectionHeader.split("<break>");		
		List<String> sectionHeaderName = new ArrayList<String>(Arrays.asList(arrSectionHeader));

		String[] arrTabName= tabsOnTagged.split("<break>");		
		List<String> tabNameOnTagged = new ArrayList<String>(Arrays.asList(arrTabName));

		String[] arrContactHeader=contactHeader.split("<break>");
		List<String> contactHeaders = new ArrayList<String>(Arrays.asList(arrContactHeader));

		List<String> blankList=new ArrayList<String>();

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);
				if(bp.clicktabOnPage(TabName.Acuity.toString()))
				{
					log(LogStatus.INFO, "Clicked on Acuity Tab", YesNo.No);
					ArrayList<String> result3=bp.verifySectionsAndTooltipOnAcuityTab(sectionHeaderName,sectionHeaderName);
					if(result3.isEmpty())
					{
						log(LogStatus.INFO, "Section Headers have been verified on acuity tab", YesNo.No);
					}					
					else
					{
						log(LogStatus.ERROR, "Section headers and Tooltip are not verified on acuity tab. "+result3, YesNo.No);
						sa.assertTrue(false, "Section headers and Tooltip are not verified on acuity tab. "+result3);
					}
					ArrayList<String> result4=bp.verifyTabsOnTaggedSection(tabNameOnTagged,defaultTabOntagged);
					if(result4.isEmpty())
					{
						log(LogStatus.INFO, "Default selected Tab and Tabs have been verified on Tagged section. ", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "Default selected Tab and Tabs are not verified on Tagged section. "+result4, YesNo.No);
						sa.assertTrue(false, "Default selected Tab and Tabs are not verified on Tagged section. "+result4);
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

					ArrayList<String> result1=bp.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(message, contactHeaders, message, blankList, null,blankList,null,blankList,null);

					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The header name and message have been verified on Interaction and Contacts ", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The header name and message are not verified on Interaction and Contacts. "+result1, YesNo.No);
						sa.assertTrue(false, "The header name and message are not verified on Interaction and Contacts "+result1);
					}

					ArrayList<String> result2=bp.verifyToolTipOnDealsConnctionsAndContactsHeader(blankList, contactHeaders, blankList,blankList);
					if(result2.isEmpty())
					{
						log(LogStatus.INFO, "The Tooltip on Contact header have been verified", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The Tooltip on Contact header are not verified. "+result2, YesNo.No);
						sa.assertTrue(false, "The Tooltip on Contact header are not verified. "+result2);
					}
					
					if(bp.verifyUIOfLogACallAndCreateTaskButtonOnAcuity(true, true, true, true, false, false))
					{
						log(LogStatus.INFO, "The UI of Log a Call, Create Task and Add Contact icon on contact section have been verified", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The UI of Log a Call, Create Task and Add Contact icon on contact section are not verified", YesNo.No);
						sa.assertTrue(false, "The UI of Log a Call, Create Task and Add Contact icon on contact section are not verified");
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
	public void ATCETc008_VerifyUIOfAcuityTabOnPrivateEquityRecord(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATCERecord13;		
			
		String sectionHeader=ATCE_Section1;
		String tabsOnTagged=ATCE_Tabs1;
		String defaultTabOntagged="Firms";
		String message=bp.acuityDefaultMessage;

		String contactHeader=ATCE_ContactHeader1;

		String[] arrSectionHeader=sectionHeader.split("<break>");		
		List<String> sectionHeaderName = new ArrayList<String>(Arrays.asList(arrSectionHeader));

		String[] arrTabName= tabsOnTagged.split("<break>");		
		List<String> tabNameOnTagged = new ArrayList<String>(Arrays.asList(arrTabName));

		String[] arrContactHeader=contactHeader.split("<break>");
		List<String> contactHeaders = new ArrayList<String>(Arrays.asList(arrContactHeader));

		List<String> blankList=new ArrayList<String>();

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);
				if(bp.clicktabOnPage(TabName.Acuity.toString()))
				{
					log(LogStatus.INFO, "Clicked on Acuity Tab", YesNo.No);
					ArrayList<String> result3=bp.verifySectionsAndTooltipOnAcuityTab(sectionHeaderName,sectionHeaderName);
					if(result3.isEmpty())
					{
						log(LogStatus.INFO, "Section Headers have been verified on acuity tab", YesNo.No);
					}					
					else
					{
						log(LogStatus.ERROR, "Section headers and Tooltip are not verified on acuity tab. "+result3, YesNo.No);
						sa.assertTrue(false, "Section headers and Tooltip are not verified on acuity tab. "+result3);
					}
					ArrayList<String> result4=bp.verifyTabsOnTaggedSection(tabNameOnTagged,defaultTabOntagged);
					if(result4.isEmpty())
					{
						log(LogStatus.INFO, "Default selected Tab and Tabs have been verified on Tagged section. ", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "Default selected Tab and Tabs are not verified on Tagged section. "+result4, YesNo.No);
						sa.assertTrue(false, "Default selected Tab and Tabs are not verified on Tagged section. "+result4);
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

					ArrayList<String> result1=bp.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(message, contactHeaders, message, blankList, null,blankList,null,blankList,null);

					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The header name and message have been verified on Interaction and Contacts ", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The header name and message are not verified on Interaction and Contacts. "+result1, YesNo.No);
						sa.assertTrue(false, "The header name and message are not verified on Interaction and Contacts "+result1);
					}

					ArrayList<String> result2=bp.verifyToolTipOnDealsConnctionsAndContactsHeader(blankList, contactHeaders, blankList,blankList);
					if(result2.isEmpty())
					{
						log(LogStatus.INFO, "The Tooltip on Contact header have been verified", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The Tooltip on Contact header are not verified. "+result2, YesNo.No);
						sa.assertTrue(false, "The Tooltip on Contact header are not verified. "+result2);
					}
					
					if(bp.verifyUIOfLogACallAndCreateTaskButtonOnAcuity(true, true, true, true, false, false))
					{
						log(LogStatus.INFO, "The UI of Log a Call, Create Task and Add Contact icon on contact section have been verified", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The UI of Log a Call, Create Task and Add Contact icon on contact section are not verified", YesNo.No);
						sa.assertTrue(false, "The UI of Log a Call, Create Task and Add Contact icon on contact section are not verified");
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
	public void ATETc009_VerifyUIOfAcuityTabOnContact(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName=ATCE_Con2;
		String sectionHeader=ATCE_Section3;
		String tabsOnTagged=ATCE_Tabs1;
		String defaultTabOntagged="Firms";
		String message=bp.acuityDefaultMessage;

		String internalconnectionHeader=ATCE_ConnectionHeader1;
		String externalconnectionHeader=ATCE_ConnectionHeader2;

		String[] arrSectionHeader=sectionHeader.split("<break>");		
		List<String> sectionHeaderName = new ArrayList<String>(Arrays.asList(arrSectionHeader));


		String[] arrTabName= tabsOnTagged.split("<break>");		
		List<String> tabNameOnTagged = new ArrayList<String>(Arrays.asList(arrTabName));

		String[] arrinternalConnnectionHeader=internalconnectionHeader.split("<break>");
		List<String> internalConnnectionHeaders = new ArrayList<String>(Arrays.asList(arrinternalConnnectionHeader));

		String[] arrExternalConnnectionHeader=externalconnectionHeader.split("<break>");
		List<String> externalConnnectionHeaders = new ArrayList<String>(Arrays.asList(arrExternalConnnectionHeader));

		
		List<String> blankList=new ArrayList<String>();

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);
				if(bp.clicktabOnPage(TabName.Acuity.toString()))
				{
					log(LogStatus.INFO, "Clicked on Acuity Tab", YesNo.No);

					ArrayList<String> result3=bp.verifySectionsAndTooltipOnAcuityTab(sectionHeaderName,sectionHeaderName);
					if(result3.isEmpty())
					{
						log(LogStatus.INFO, "Section Headers have been verified on acuity tab", YesNo.No);

					}					
					else
					{
						log(LogStatus.ERROR, "Section headers and Tooltip are not verified on acuity tab. "+result3, YesNo.No);
						sa.assertTrue(false, "Section headers and Tooltip are not verified on acuity tab. "+result3);
					}
					ArrayList<String> result4=bp.verifyTabsOnTaggedSection(tabNameOnTagged,defaultTabOntagged);
					if(result4.isEmpty())
					{
						log(LogStatus.INFO, "Default selected Tab and Tabs have been verified on Tagged section. ", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "Default selected Tab and Tabs are not verified on Tagged section. "+result4, YesNo.No);
						sa.assertTrue(false, "Default selected Tab and Tabs are not verified on Tagged section. "+result4);
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

						ArrayList<String> result1=bp.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(message, blankList, null, blankList, null,internalConnnectionHeaders,message,externalConnnectionHeaders,message);

						if(result1.isEmpty())
						{
							log(LogStatus.INFO, "The header name and message have been verified on Interaction and Connection Section. "+result1, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The header name and message are not verified on Interaction and Connection Section. "+result1, YesNo.No);
							sa.assertTrue(false, "The header name and message are not verified on Interaction and Connection Section. "+result1);
						}

						ArrayList<String> result2=bp.verifyToolTipOnDealsConnctionsAndContactsHeader(blankList, blankList, internalConnnectionHeaders, externalConnnectionHeaders);
						if(result2.isEmpty())
						{
							log(LogStatus.INFO, "The Tooltip on connection header have been verified", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The Tooltip on connection header are not verified. "+result2, YesNo.No);
							sa.assertTrue(false, "The Tooltip on connnection header are not verified. "+result2);
						}
						
						if(bp.verifyUIOfLogACallAndCreateTaskButtonOnAcuity(true, true, false, true, false, false))
						{
							log(LogStatus.INFO, "The UI of Log a Call, Create Task Notification icon, Internal button and External button have been verified", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The UI of Log a Call, Create Task Notification icon, Internal button and External button are not verified", YesNo.No);
							sa.assertTrue(false, "The UI of Log a Call, Create Task Notification icon, Internal button and External button are not verified");
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
	public void ATCETc010_VerifyUIOfAcuityTabOnDealRecord(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATCE_Deal1;		
			
		String sectionHeader=ATCE_Section4;
		String tabsOnTagged=ATCE_Tabs1;
		String defaultTabOntagged="Firms";
		String message=bp.acuityDefaultMessage;

		String[] arrSectionHeader=sectionHeader.split("<break>");		
		List<String> sectionHeaderName = new ArrayList<String>(Arrays.asList(arrSectionHeader));

		String[] arrTabName= tabsOnTagged.split("<break>");		
		List<String> tabNameOnTagged = new ArrayList<String>(Arrays.asList(arrTabName));

		List<String> blankList=new ArrayList<String>();

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj4)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj4, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.DealTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);
				if(bp.clicktabOnPage(TabName.Acuity.toString()))
				{
					log(LogStatus.INFO, "Clicked on Acuity Tab", YesNo.No);
					ArrayList<String> result3=bp.verifySectionsAndTooltipOnAcuityTab(sectionHeaderName,sectionHeaderName);
					if(result3.isEmpty())
					{
						log(LogStatus.INFO, "Section Headers have been verified on acuity tab", YesNo.No);
					}					
					else
					{
						log(LogStatus.ERROR, "Section headers and Tooltip are not verified on acuity tab. "+result3, YesNo.No);
						sa.assertTrue(false, "Section headers and Tooltip are not verified on acuity tab. "+result3);
					}
					ArrayList<String> result4=bp.verifyTabsOnTaggedSection(tabNameOnTagged,defaultTabOntagged);
					if(result4.isEmpty())
					{
						log(LogStatus.INFO, "Default selected Tab and Tabs have been verified on Tagged section. ", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "Default selected Tab and Tabs are not verified on Tagged section. "+result4, YesNo.No);
						sa.assertTrue(false, "Default selected Tab and Tabs are not verified on Tagged section. "+result4);
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

					
					if(bp.verifyUIOfLogACallAndCreateTaskButtonOnAcuity(true, true, false, true, false, false))
					{
						log(LogStatus.INFO, "The UI of Log a Call, Create Task and Add Contact icon on contact section have been verified", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The UI of Log a Call, Create Task and Add Contact icon on contact section are not verified", YesNo.No);
						sa.assertTrue(false, "The UI of Log a Call, Create Task and Add Contact icon on contact section are not verified");
					}
					
					ArrayList<String> result1=bp.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(message, blankList, null, blankList, null,blankList,null,blankList,null);

					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The header name and message have been verified on Interaction and Connection Section. "+result1, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The header name and message are not verified on Interaction and Connection Section. "+result1, YesNo.No);
						sa.assertTrue(false, "The header name and message are not verified on Interaction and Connection Section. "+result1);
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
			log(LogStatus.ERROR, "Not able to click on tab : "+tabObj4, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab : "+tabObj4);
		}
		lp.CRMlogout();	
		sa.assertAll();	
	}
	
	@Parameters({ "projectName" })
	@Test
	public void ATCETc011_VerifyUIOfAcuityTabOnFundRecord(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATCE_Fund1;		
			
		String sectionHeader=ATCE_Section4;
		String tabsOnTagged=ATCE_Tabs1;
		String defaultTabOntagged="Firms";
		String message=bp.acuityDefaultMessage;

		String[] arrSectionHeader=sectionHeader.split("<break>");		
		List<String> sectionHeaderName = new ArrayList<String>(Arrays.asList(arrSectionHeader));

		String[] arrTabName= tabsOnTagged.split("<break>");		
		List<String> tabNameOnTagged = new ArrayList<String>(Arrays.asList(arrTabName));

		List<String> blankList=new ArrayList<String>();

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj3)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj3, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.FundsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);
				if(bp.clicktabOnPage(TabName.Acuity.toString()))
				{
					log(LogStatus.INFO, "Clicked on Acuity Tab", YesNo.No);
					ArrayList<String> result3=bp.verifySectionsAndTooltipOnAcuityTab(sectionHeaderName,sectionHeaderName);
					if(result3.isEmpty())
					{
						log(LogStatus.INFO, "Section Headers have been verified on acuity tab", YesNo.No);
					}					
					else
					{
						log(LogStatus.ERROR, "Section headers and Tooltip are not verified on acuity tab. "+result3, YesNo.No);
						sa.assertTrue(false, "Section headers and Tooltip are not verified on acuity tab. "+result3);
					}
					ArrayList<String> result4=bp.verifyTabsOnTaggedSection(tabNameOnTagged,defaultTabOntagged);
					if(result4.isEmpty())
					{
						log(LogStatus.INFO, "Default selected Tab and Tabs have been verified on Tagged section. ", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "Default selected Tab and Tabs are not verified on Tagged section. "+result4, YesNo.No);
						sa.assertTrue(false, "Default selected Tab and Tabs are not verified on Tagged section. "+result4);
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

					
					if(bp.verifyUIOfLogACallAndCreateTaskButtonOnAcuity(true, true, false, true, false, false))
					{
						log(LogStatus.INFO, "The UI of Log a Call, Create Task and Add Contact icon on contact section have been verified", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The UI of Log a Call, Create Task and Add Contact icon on contact section are not verified", YesNo.No);
						sa.assertTrue(false, "The UI of Log a Call, Create Task and Add Contact icon on contact section are not verified");
					}
					
					ArrayList<String> result1=bp.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(message, blankList, null, blankList, null,blankList,null,blankList,null);

					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The header name and message have been verified on Interaction and Connection Section. "+result1, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The header name and message are not verified on Interaction and Connection Section. "+result1, YesNo.No);
						sa.assertTrue(false, "The header name and message are not verified on Interaction and Connection Section. "+result1);
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
			log(LogStatus.ERROR, "Not able to click on tab : "+tabObj3, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab : "+tabObj3);
		}
		lp.CRMlogout();	
		sa.assertAll();	
	}
	
	@Parameters({ "projectName" })
	@Test
	public void ATCETc012_VerifyUIOfAcuityTabOnFundraisingRecord(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATCE_Fundraising1;		
			
		String sectionHeader=ATCE_Section4;
		String tabsOnTagged=ATCE_Tabs3;
		String defaultTabOntagged="Firms";
		String message=bp.acuityDefaultMessage;

		String[] arrSectionHeader=sectionHeader.split("<break>");		
		List<String> sectionHeaderName = new ArrayList<String>(Arrays.asList(arrSectionHeader));

		String[] arrTabName= tabsOnTagged.split("<break>");		
		List<String> tabNameOnTagged = new ArrayList<String>(Arrays.asList(arrTabName));

		List<String> blankList=new ArrayList<String>();

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, TabName.Fundraising.toString())) {

			log(LogStatus.INFO, "Clicked on Tab : "+TabName.Fundraising.toString(), YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.FundraisingsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);
				if(bp.clicktabOnPage(TabName.Acuity.toString()))
				{
					log(LogStatus.INFO, "Clicked on Acuity Tab", YesNo.No);
					ArrayList<String> result3=bp.verifySectionsAndTooltipOnAcuityTab(sectionHeaderName,sectionHeaderName);
					if(result3.isEmpty())
					{
						log(LogStatus.INFO, "Section Headers have been verified on acuity tab", YesNo.No);
					}					
					else
					{
						log(LogStatus.ERROR, "Section headers and Tooltip are not verified on acuity tab. "+result3, YesNo.No);
						sa.assertTrue(false, "Section headers and Tooltip are not verified on acuity tab. "+result3);
					}
					ArrayList<String> result4=bp.verifyTabsOnTaggedSection(tabNameOnTagged,defaultTabOntagged);
					if(result4.isEmpty())
					{
						log(LogStatus.INFO, "Default selected Tab and Tabs have been verified on Tagged section. ", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "Default selected Tab and Tabs are not verified on Tagged section. "+result4, YesNo.No);
						sa.assertTrue(false, "Default selected Tab and Tabs are not verified on Tagged section. "+result4);
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

					
					if(bp.verifyUIOfLogACallAndCreateTaskButtonOnAcuity(true, true, false, true, false, false))
					{
						log(LogStatus.INFO, "The UI of Log a Call, Create Task and Add Contact icon on contact section have been verified", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The UI of Log a Call, Create Task and Add Contact icon on contact section are not verified", YesNo.No);
						sa.assertTrue(false, "The UI of Log a Call, Create Task and Add Contact icon on contact section are not verified");
					}
					
					ArrayList<String> result1=bp.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(message, blankList, null, blankList, null,blankList,null,blankList,null);

					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The header name and message have been verified on Interaction and Connection Section. "+result1, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The header name and message are not verified on Interaction and Connection Section. "+result1, YesNo.No);
						sa.assertTrue(false, "The header name and message are not verified on Interaction and Connection Section. "+result1);
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
			log(LogStatus.ERROR, "Not able to click on tab : "+TabName.Fundraising.toString(), YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab : "+TabName.Fundraising.toString());
		}
		lp.CRMlogout();	
		sa.assertAll();	
	}
	
	@Parameters({ "projectName" })
	@Test
	public void ATCETc013_VerifyUIOfAcuityTabOnThemeRecord(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATCE_Theme1;		
			
		String sectionHeader=ATCE_Section5;
		
		String message=bp.acuityDefaultMessage;

		String[] arrSectionHeader=sectionHeader.split("<break>");		
		List<String> sectionHeaderName = new ArrayList<String>(Arrays.asList(arrSectionHeader));

		
		List<String> blankList=new ArrayList<String>();

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, TabName.Themes.toString())) {

			log(LogStatus.INFO, "Clicked on Tab : "+TabName.Themes.toString(), YesNo.No);
			
			String parentWindowID=bp.clickOnThemeRecord(recordName);

			if (parentWindowID!=null) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);
				if(bp.clicktabOnPage(TabName.Acuity.toString()))
				{
					log(LogStatus.INFO, "Clicked on Acuity Tab", YesNo.No);
					ArrayList<String> result3=bp.verifySectionsAndTooltipOnAcuityTab(sectionHeaderName,sectionHeaderName);
					if(result3.isEmpty())
					{
						log(LogStatus.INFO, "Section Headers have been verified on acuity tab", YesNo.No);
					}					
					else
					{
						log(LogStatus.ERROR, "Section headers and Tooltip are not verified on acuity tab. "+result3, YesNo.No);
						sa.assertTrue(false, "Section headers and Tooltip are not verified on acuity tab. "+result3);
					}
					
					if(bp.verifyUIOfLogACallAndCreateTaskButtonOnAcuity(true, true, false, true, false, false))
					{
						log(LogStatus.INFO, "The UI of Log a Call, Create Task and Add Contact icon on contact section have been verified", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The UI of Log a Call, Create Task and Add Contact icon on contact section are not verified", YesNo.No);
						sa.assertTrue(false, "The UI of Log a Call, Create Task and Add Contact icon on contact section are not verified");
					}
					
					ArrayList<String> result1=bp.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(message, blankList, null, blankList, null,blankList,null,blankList,null);

					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The header name and message have been verified on Interaction and Connection Section. "+result1, YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The header name and message are not verified on Interaction and Connection Section. "+result1, YesNo.No);
						sa.assertTrue(false, "The header name and message are not verified on Interaction and Connection Section. "+result1);
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
					log(LogStatus.ERROR, "Not able to click on Acuity Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Acuity Tab");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open "+recordName +" reocrd", YesNo.No);
				sa.assertTrue(false, "Not able to open "+recordName +" reocrd");
			}
			driver.close();
			driver.switchTo().window(parentWindowID);
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on tab : "+TabName.Themes.toString(), YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab : "+TabName.Themes.toString());
		}
		lp.CRMlogout();	
		sa.assertAll();	
	}
	

	@Parameters({ "projectName" })
	@Test
	public void ATCETc014_CreateContactFromAddContactIconOnContactSection(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		ContactsPageBusinessLayer cp=new ContactsPageBusinessLayer(driver);
		
		String[] legalName= {ATCE_ContactLegalName1,ATCE_ContactLegalName2,ATCE_ContactLegalName3,ATCE_ContactLegalName4,ATCE_ContactLegalName5,ATCE_ContactLegalName6,ATCE_ContactLegalName7};
		
		String[][][] contactDetails= {{{"First Name",ATCE_ContactFirstName1},{"Last Name",ATCE_ContactLastName1},{"Email",ATCE_ContactEmail1},{"Title",ATCE_ContactTitle1}},
			{{"First Name",ATCE_ContactFirstName2},{"Last Name",ATCE_ContactLastName2},{"Email",ATCE_ContactEmail2},{"Title",ATCE_ContactTitle2}},
			{{"First Name",ATCE_ContactFirstName3},{"Last Name",ATCE_ContactLastName3},{"Email",ATCE_ContactEmail3},{"Title",ATCE_ContactTitle3}},
			{{"First Name",ATCE_ContactFirstName4},{"Last Name",ATCE_ContactLastName4},{"Email",ATCE_ContactEmail4},{"Title",ATCE_ContactTitle4}},
			{{"First Name",ATCE_ContactFirstName5},{"Last Name",ATCE_ContactLastName5},{"Email",ATCE_ContactEmail5},{"Title",ATCE_ContactTitle5}},
			{{"First Name",ATCE_ContactFirstName6},{"Last Name",ATCE_ContactLastName6},{"Email",ATCE_ContactEmail6},{"Title",ATCE_ContactTitle6}},
			{{"First Name",ATCE_ContactFirstName7},{"Last Name",ATCE_ContactLastName7},{"Email",ATCE_ContactEmail7},{"Title",ATCE_ContactTitle7}}};
		
		
		
		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);
		for(int i=0; i<legalName.length; i++)
		{
			System.err.println("Sou "+contactDetails[i]);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : "+tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					legalName[i], 30)) {
				log(LogStatus.INFO, legalName[i] + " reocrd has been open", YesNo.No);
				if(bp.clicktabOnPage(TabName.Acuity.toString()))
				{
					log(LogStatus.INFO, "Clicked on Acuity Tab", YesNo.No);
					
					if(cp.createContactFromContactSectionOfAcuity(null,contactDetails[i]))
					{
						log(LogStatus.INFO, "The Contact have been created contact Name: "+contactDetails[i][0][0]+" "+contactDetails[i][1][1], YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The Contact is not created contact Name: "+contactDetails[i][0][0]+" "+contactDetails[i][1][1], YesNo.No);
						sa.assertTrue(false, "The Contact is not created contact Name: "+contactDetails[i][0][0]+" "+contactDetails[i][1][1]);
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
				log(LogStatus.ERROR, "Not able to open "+legalName[i] +" reocrd", YesNo.No);
				sa.assertTrue(false, "Not able to open "+legalName[i] +" reocrd");
			}
		}

		else
		{
			log(LogStatus.ERROR, "Not able to click on tab : "+tabObj1, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab : "+tabObj1);
		}
		}
		lp.CRMlogout();	
		sa.assertAll();	
	}
	
	@Parameters({ "projectName" })
	@Test
	public void ATCETc015_VerifyUIOfConnectionPageAndMeetingAndCallPageOnCompanyRecord(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATCERecord1;		
		
		String contactName=ATCE_ContactFullName1;
		String message=bp.acuityDefaultMessage;
		
		String[] meetingAndCall=ATCE_MeetingAndCallHeader.split("<break>");
		
		ArrayList<String> headerNameMeetingAndCall=new ArrayList<String>();
		for(String txt:meetingAndCall)
		{
			headerNameMeetingAndCall.add(txt);
		}

		String[] externalConnectionHeaderArr=ATCE_ConnectionHeader2.split("<break>");
		ArrayList<String> exteralHeaderName=new ArrayList<String>();
		for(String txt:externalConnectionHeaderArr)
		{
			exteralHeaderName.add(txt);
		}
		
		
		String[] val=ATCE_ConnectionHeader1.split("<break>");
		ArrayList<String> headerName=new ArrayList<String>();
		for(String txt:val)
		{
			headerName.add(txt);
		}

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);
				if(bp.clicktabOnPage(TabName.Acuity.toString()))
				{
					log(LogStatus.INFO, "Clicked on Acuity Tab", YesNo.No);
					if(click(driver, bp.getConnectionIconOfContact(contactName, 20), "Connection icon of "+contactName, action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on Connection icon of "+contactName, YesNo.No);

						ArrayList<String> result=bp.verifyUIOfConnectionPopup(contactName, headerName, message,exteralHeaderName,message);
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
					
					
					if(click(driver, bp.getMeetingAndCallCount(contactName, 20), "Meeting and call count", action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "Clicked on the count of meeting and call of "+contactName+" record on contact section",YesNo.No);
						
						ArrayList<String> result1=bp.verifyUIOfMeetingAndCallsPopup( headerNameMeetingAndCall, message);
						if(result1.isEmpty())
						{
							log(LogStatus.INFO, "The UI of Meeting and call page have been verified", YesNo.No);
							sa.assertTrue(true,  "The UI of Meeting and call page have been verified");
						}
						else
						{
							log(LogStatus.ERROR, "The UI of Meeting and call page are not verified. "+result1, YesNo.No);
							sa.assertTrue(false,  "The UI of Meeting and call page are not verified. "+result1);

						}					
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+contactName+" record on contact section",	YesNo.No);		
						sa.assertTrue(false,  "Not able to click on the count of meeting and call of "+contactName+" record on contact section");
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
	public void ATCETc016_VerifyUIOfConnectionPageAndMeetingAndCallPageOnInstitutionRecord(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATCERecord3;		
		
		String contactName=ATCE_ContactFullName3;
		String message=bp.acuityDefaultMessage;
		
		String[] meetingAndCall=ATCE_MeetingAndCallHeader.split("<break>");
		
		ArrayList<String> headerNameMeetingAndCall=new ArrayList<String>();
		for(String txt:meetingAndCall)
		{
			headerNameMeetingAndCall.add(txt);
		}

		String[] externalConnectionHeaderArr=ATCE_ConnectionHeader2.split("<break>");
		ArrayList<String> exteralHeaderName=new ArrayList<String>();
		for(String txt:externalConnectionHeaderArr)
		{
			exteralHeaderName.add(txt);
		}
		
		
		String[] val=ATCE_ConnectionHeader1.split("<break>");
		ArrayList<String> headerName=new ArrayList<String>();
		for(String txt:val)
		{
			headerName.add(txt);
		}

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);
				if(bp.clicktabOnPage(TabName.Acuity.toString()))
				{
					log(LogStatus.INFO, "Clicked on Acuity Tab", YesNo.No);
					if(click(driver, bp.getConnectionIconOfContact(contactName, 20), "Connection icon of "+contactName, action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on Connection icon of "+contactName, YesNo.No);

						ArrayList<String> result=bp.verifyUIOfConnectionPopup(contactName, headerName, message,exteralHeaderName,message);
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
					
					
					if(click(driver, bp.getMeetingAndCallCount(contactName, 20), "Meeting and call count", action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "Clicked on the count of meeting and call of "+contactName+" record on contact section",YesNo.No);
						
						ArrayList<String> result1=bp.verifyUIOfMeetingAndCallsPopup( headerNameMeetingAndCall, message);
						if(result1.isEmpty())
						{
							log(LogStatus.INFO, "The UI of Meeting and call page have been verified", YesNo.No);
							sa.assertTrue(true,  "The UI of Meeting and call page have been verified");
						}
						else
						{
							log(LogStatus.ERROR, "The UI of Meeting and call page are not verified. "+result1, YesNo.No);
							sa.assertTrue(false,  "The UI of Meeting and call page are not verified. "+result1);

						}					
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+contactName+" record on contact section",	YesNo.No);		
						sa.assertTrue(false,  "Not able to click on the count of meeting and call of "+contactName+" record on contact section");
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
	public void ATCETc017_VerifyUIOfConnectionPageAndMeetingAndCallPageOnAdvisorRecord(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATCERecord5;		
		
		String contactName=ATCE_ContactFullName9;
		String message=bp.acuityDefaultMessage;
		
		String[] meetingAndCall=ATCE_MeetingAndCallHeader.split("<break>");
		
		ArrayList<String> headerNameMeetingAndCall=new ArrayList<String>();
		for(String txt:meetingAndCall)
		{
			headerNameMeetingAndCall.add(txt);
		}

		String[] externalConnectionHeaderArr=ATCE_ConnectionHeader2.split("<break>");
		ArrayList<String> exteralHeaderName=new ArrayList<String>();
		for(String txt:externalConnectionHeaderArr)
		{
			exteralHeaderName.add(txt);
		}
		
		
		String[] val=ATCE_ConnectionHeader1.split("<break>");
		ArrayList<String> headerName=new ArrayList<String>();
		for(String txt:val)
		{
			headerName.add(txt);
		}

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);
				if(bp.clicktabOnPage(TabName.Acuity.toString()))
				{
					log(LogStatus.INFO, "Clicked on Acuity Tab", YesNo.No);
					if(click(driver, bp.getConnectionIconOfContact(contactName, 20), "Connection icon of "+contactName, action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on Connection icon of "+contactName, YesNo.No);

						ArrayList<String> result=bp.verifyUIOfConnectionPopup(contactName, headerName, message,exteralHeaderName,message);
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
					
					
					if(click(driver, bp.getMeetingAndCallCount(contactName, 20), "Meeting and call count", action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "Clicked on the count of meeting and call of "+contactName+" record on contact section",YesNo.No);
						
						ArrayList<String> result1=bp.verifyUIOfMeetingAndCallsPopup( headerNameMeetingAndCall, message);
						if(result1.isEmpty())
						{
							log(LogStatus.INFO, "The UI of Meeting and call page have been verified", YesNo.No);
							sa.assertTrue(true,  "The UI of Meeting and call page have been verified");
						}
						else
						{
							log(LogStatus.ERROR, "The UI of Meeting and call page are not verified. "+result1, YesNo.No);
							sa.assertTrue(false,  "The UI of Meeting and call page are not verified. "+result1);

						}					
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+contactName+" record on contact section",	YesNo.No);		
						sa.assertTrue(false,  "Not able to click on the count of meeting and call of "+contactName+" record on contact section");
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
	public void ATCETc018_VerifyUIOfConnectionPageAndMeetingAndCallPageOnLenderRecord(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATCERecord7;		
		
		String contactName=ATCE_ContactFullName7;
		String message=bp.acuityDefaultMessage;
		
		String[] meetingAndCall=ATCE_MeetingAndCallHeader.split("<break>");
		
		ArrayList<String> headerNameMeetingAndCall=new ArrayList<String>();
		for(String txt:meetingAndCall)
		{
			headerNameMeetingAndCall.add(txt);
		}

		String[] externalConnectionHeaderArr=ATCE_ConnectionHeader2.split("<break>");
		ArrayList<String> exteralHeaderName=new ArrayList<String>();
		for(String txt:externalConnectionHeaderArr)
		{
			exteralHeaderName.add(txt);
		}
		
		
		String[] val=ATCE_ConnectionHeader1.split("<break>");
		ArrayList<String> headerName=new ArrayList<String>();
		for(String txt:val)
		{
			headerName.add(txt);
		}

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);
				if(bp.clicktabOnPage(TabName.Acuity.toString()))
				{
					log(LogStatus.INFO, "Clicked on Acuity Tab", YesNo.No);
					if(click(driver, bp.getConnectionIconOfContact(contactName, 20), "Connection icon of "+contactName, action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on Connection icon of "+contactName, YesNo.No);

						ArrayList<String> result=bp.verifyUIOfConnectionPopup(contactName, headerName, message,exteralHeaderName,message);
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
					
					
					if(click(driver, bp.getMeetingAndCallCount(contactName, 20), "Meeting and call count", action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "Clicked on the count of meeting and call of "+contactName+" record on contact section",YesNo.No);
						
						ArrayList<String> result1=bp.verifyUIOfMeetingAndCallsPopup( headerNameMeetingAndCall, message);
						if(result1.isEmpty())
						{
							log(LogStatus.INFO, "The UI of Meeting and call page have been verified", YesNo.No);
							sa.assertTrue(true,  "The UI of Meeting and call page have been verified");
						}
						else
						{
							log(LogStatus.ERROR, "The UI of Meeting and call page are not verified. "+result1, YesNo.No);
							sa.assertTrue(false,  "The UI of Meeting and call page are not verified. "+result1);

						}					
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+contactName+" record on contact section",	YesNo.No);		
						sa.assertTrue(false,  "Not able to click on the count of meeting and call of "+contactName+" record on contact section");
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
	public void ATCETc019_VerifyUIOfConnectionPageAndMeetingAndCallPageOnIntermediaryRecord(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATCERecord9;		
		
		String contactName=ATCE_ContactFullName5;
		String message=bp.acuityDefaultMessage;
		
		String[] meetingAndCall=ATCE_MeetingAndCallHeader.split("<break>");
		
		ArrayList<String> headerNameMeetingAndCall=new ArrayList<String>();
		for(String txt:meetingAndCall)
		{
			headerNameMeetingAndCall.add(txt);
		}

		String[] externalConnectionHeaderArr=ATCE_ConnectionHeader2.split("<break>");
		ArrayList<String> exteralHeaderName=new ArrayList<String>();
		for(String txt:externalConnectionHeaderArr)
		{
			exteralHeaderName.add(txt);
		}
		
		
		String[] val=ATCE_ConnectionHeader1.split("<break>");
		ArrayList<String> headerName=new ArrayList<String>();
		for(String txt:val)
		{
			headerName.add(txt);
		}

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);
				if(bp.clicktabOnPage(TabName.Acuity.toString()))
				{
					log(LogStatus.INFO, "Clicked on Acuity Tab", YesNo.No);
					if(click(driver, bp.getConnectionIconOfContact(contactName, 20), "Connection icon of "+contactName, action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on Connection icon of "+contactName, YesNo.No);

						ArrayList<String> result=bp.verifyUIOfConnectionPopup(contactName, headerName, message,exteralHeaderName,message);
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
					
					
					if(click(driver, bp.getMeetingAndCallCount(contactName, 20), "Meeting and call count", action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "Clicked on the count of meeting and call of "+contactName+" record on contact section",YesNo.No);
						
						ArrayList<String> result1=bp.verifyUIOfMeetingAndCallsPopup( headerNameMeetingAndCall, message);
						if(result1.isEmpty())
						{
							log(LogStatus.INFO, "The UI of Meeting and call page have been verified", YesNo.No);
							sa.assertTrue(true,  "The UI of Meeting and call page have been verified");
						}
						else
						{
							log(LogStatus.ERROR, "The UI of Meeting and call page are not verified. "+result1, YesNo.No);
							sa.assertTrue(false,  "The UI of Meeting and call page are not verified. "+result1);

						}					
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+contactName+" record on contact section",	YesNo.No);		
						sa.assertTrue(false,  "Not able to click on the count of meeting and call of "+contactName+" record on contact section");
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
	public void ATCETc020_VerifyUIOfConnectionPageAndMeetingAndCallPageOnPortfolioCompanyRecord(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATCERecord11;		
		
		String contactName=ATCE_ContactFullName11;
		String message=bp.acuityDefaultMessage;
		
		String[] meetingAndCall=ATCE_MeetingAndCallHeader.split("<break>");
		
		ArrayList<String> headerNameMeetingAndCall=new ArrayList<String>();
		for(String txt:meetingAndCall)
		{
			headerNameMeetingAndCall.add(txt);
		}

		String[] externalConnectionHeaderArr=ATCE_ConnectionHeader2.split("<break>");
		ArrayList<String> exteralHeaderName=new ArrayList<String>();
		for(String txt:externalConnectionHeaderArr)
		{
			exteralHeaderName.add(txt);
		}
		
		
		String[] val=ATCE_ConnectionHeader1.split("<break>");
		ArrayList<String> headerName=new ArrayList<String>();
		for(String txt:val)
		{
			headerName.add(txt);
		}

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);
				if(bp.clicktabOnPage(TabName.Acuity.toString()))
				{
					log(LogStatus.INFO, "Clicked on Acuity Tab", YesNo.No);
					if(click(driver, bp.getConnectionIconOfContact(contactName, 20), "Connection icon of "+contactName, action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on Connection icon of "+contactName, YesNo.No);

						ArrayList<String> result=bp.verifyUIOfConnectionPopup(contactName, headerName, message,exteralHeaderName,message);
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
					
					
					if(click(driver, bp.getMeetingAndCallCount(contactName, 20), "Meeting and call count", action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "Clicked on the count of meeting and call of "+contactName+" record on contact section",YesNo.No);
						
						ArrayList<String> result1=bp.verifyUIOfMeetingAndCallsPopup( headerNameMeetingAndCall, message);
						if(result1.isEmpty())
						{
							log(LogStatus.INFO, "The UI of Meeting and call page have been verified", YesNo.No);
							sa.assertTrue(true,  "The UI of Meeting and call page have been verified");
						}
						else
						{
							log(LogStatus.ERROR, "The UI of Meeting and call page are not verified. "+result1, YesNo.No);
							sa.assertTrue(false,  "The UI of Meeting and call page are not verified. "+result1);

						}					
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+contactName+" record on contact section",	YesNo.No);		
						sa.assertTrue(false,  "Not able to click on the count of meeting and call of "+contactName+" record on contact section");
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
	public void ATCETc021_VerifyUIOfConnectionPageAndMeetingAndCallPageOnPrivateEquityRecord(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATCERecord13;		
		
		String contactName=ATCE_ContactFullName13;
		String message=bp.acuityDefaultMessage;
		
		String[] meetingAndCall=ATCE_MeetingAndCallHeader.split("<break>");
		
		ArrayList<String> headerNameMeetingAndCall=new ArrayList<String>();
		for(String txt:meetingAndCall)
		{
			headerNameMeetingAndCall.add(txt);
		}

		String[] externalConnectionHeaderArr=ATCE_ConnectionHeader2.split("<break>");
		ArrayList<String> exteralHeaderName=new ArrayList<String>();
		for(String txt:externalConnectionHeaderArr)
		{
			exteralHeaderName.add(txt);
		}
		
		
		String[] val=ATCE_ConnectionHeader1.split("<break>");
		ArrayList<String> headerName=new ArrayList<String>();
		for(String txt:val)
		{
			headerName.add(txt);
		}

		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
					recordName, 30)) {
				log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);
				if(bp.clicktabOnPage(TabName.Acuity.toString()))
				{
					log(LogStatus.INFO, "Clicked on Acuity Tab", YesNo.No);
					if(click(driver, bp.getConnectionIconOfContact(contactName, 20), "Connection icon of "+contactName, action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "clicked on Connection icon of "+contactName, YesNo.No);

						ArrayList<String> result=bp.verifyUIOfConnectionPopup(contactName, headerName, message,exteralHeaderName,message);
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
					
					
					if(click(driver, bp.getMeetingAndCallCount(contactName, 20), "Meeting and call count", action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, "Clicked on the count of meeting and call of "+contactName+" record on contact section",YesNo.No);
						
						ArrayList<String> result1=bp.verifyUIOfMeetingAndCallsPopup( headerNameMeetingAndCall, message);
						if(result1.isEmpty())
						{
							log(LogStatus.INFO, "The UI of Meeting and call page have been verified", YesNo.No);
							sa.assertTrue(true,  "The UI of Meeting and call page have been verified");
						}
						else
						{
							log(LogStatus.ERROR, "The UI of Meeting and call page are not verified. "+result1, YesNo.No);
							sa.assertTrue(false,  "The UI of Meeting and call page are not verified. "+result1);

						}					
					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+contactName+" record on contact section",	YesNo.No);		
						sa.assertTrue(false,  "Not able to click on the count of meeting and call of "+contactName+" record on contact section");
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
	public void ATETc022_CreateATaskAndLogACall(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		
		String activityType=ATCE_ATActivityType1;
		String taskSubject=ATCE_ATSubject1;
		String taskRelatedTo=ATCE_ATRelatedTo1;
		String taskNotes=ATCE_ATNote1;
		String taskStatus=ATCE_AdvanceStatus1;
		String taskPriority=ATCE_AdvancePriority1;	
		String dueDateDay=ATCE_ATDay1;
		String taskDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(dueDateDay));
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate, "Activity Timeline", excelLabel.Variable_Name,
				"ATCE_001", excelLabel.Advance_Due_Date);
		
		String activityType1=ATCE_ATActivityType2;
		String taskSubject1=ATCE_ATSubject2;
		String taskRelatedTo1=ATCE_ATRelatedTo2;
		String taskNotes1=ATCE_ATNote2;	
		String dueDateDay1=ATCE_ATDay2;
		String taskDueDate1 = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt(dueDateDay1));
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate1, "Activity Timeline", excelLabel.Variable_Name,
				"ATCE_002", excelLabel.Advance_Due_Date);
	
		String[][] basicsection = { { "Subject", taskSubject }, { "Notes", taskNotes }, { "Related_To", taskRelatedTo } };
		String[][] advanceSection = { { "Due Date", taskDueDate }, {"Status", taskStatus}, {"Priority", taskPriority} };
		
		String[][] basicsection1 = { { "Subject", taskSubject1 }, { "Notes", taskNotes1 }, { "Related_To", taskRelatedTo1 } };
		String[][] advanceSection1 = { { "Date", taskDueDate1 }};
		
		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

		if (bp.createActivityTimeline(projectName, true, activityType, basicsection, advanceSection, null, null, false, null, null,null, null,null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject);

		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject);
		}	 
		
		if (bp.createActivityTimeline(projectName, true, activityType1, basicsection1, advanceSection1, null, null, false, null, null,null, null,null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject1);

		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject1);
		}	 

		lp.CRMlogout();	
		sa.assertAll();	
	}
	
	
	@Parameters({ "projectName" })
	@Test
	public void ATETc023_VerifyTaskCallFunctionalityOnCompanyRecordType(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATCERecord1;
		String taskSubject=ATCE_ATSubject1;
		String taskRelatedTo=ATCE_ATRelatedTo1;
		String taskNotes=ATCE_ATNote1;
		String taskStatus=ATCE_AdvanceStatus1;
		String taskPriority=ATCE_AdvancePriority1;	
		String taskDueDate =ATCE_ATDueDate1;
		
		String taskSubject1=ATCE_ATSubject2;
		String taskRelatedTo1=ATCE_ATRelatedTo2;
		String taskNotes1=ATCE_ATNote2;
		
		String taskDueDate1 =ATCE_ATDueDate2;
		
		String contactSectionName=ATCE_ConName1;
		String contactSectionTitle=ATCE_ConTitle1;
		String contactSectionDeal=ATCE_ConDeal1;
		String contactSectionMeetingAndCalls=ATCE_ConMeetingAndCall1;
		String contactSectionEmail=ATCE_ConEmail1;

		String[] firmsTaggedName= {ATCE_TaggedFirmsName1,ATCE_TaggedFirmsName2,ATCE_TaggedFirmsName3,ATCE_TaggedFirmsName4,ATCE_TaggedFirmsName5,ATCE_TaggedFirmsName6,ATCE_TaggedFirmsName7,ATCE_TaggedFirmsName8,ATCE_TaggedFirmsName9};
		String[] firmsTaggedTimeReference= {ATCE_TaggedFirmsCount1,ATCE_TaggedFirmsCount2,ATCE_TaggedFirmsCount3,ATCE_TaggedFirmsCount4,ATCE_TaggedFirmsCount5,ATCE_TaggedFirmsCount6,ATCE_TaggedFirmsCount7,ATCE_TaggedFirmsCount8,ATCE_TaggedFirmsCount9};

		String[] peopleTaggedName= {ATCE_TaggedPeopleName1,ATCE_TaggedPeopleName2,ATCE_TaggedPeopleName3,ATCE_TaggedPeopleName4,ATCE_TaggedPeopleName5,ATCE_TaggedPeopleName6};
		String[] peopleTaggedTimeReference= {ATCE_TaggedPeopleCount1,ATCE_TaggedPeopleCount2,ATCE_TaggedPeopleCount3,ATCE_TaggedPeopleCount4,ATCE_TaggedPeopleCount5,ATCE_TaggedPeopleCount6};
				
		String[] dealTaggedName= {ATCE_TaggedDealName1};
		String[] dealTaggedTimeReference= {ATCE_TaggedDealCount1};
		
		String[][] basicsection = { { "Subject", taskSubject }, { "Notes", taskNotes }, { "Related_To", taskRelatedTo } };
		String[][] advanceSection = { { "Due Date", taskDueDate }, {"Status", taskStatus}, {"Priority", taskPriority} };
		
		String[][] basicsection1 = { { "Subject", taskSubject1 }, { "Notes", taskNotes1 }, { "Related_To", taskRelatedTo1 } };
		String[][] advanceSection1 = { { "Date", taskDueDate1 }};
		
		String connectionUserName=crmUser6FirstName+" "+crmUser6LastName;
		String connectionTitle=ATCE_ConnectionTitle1;
		String connectionDeal=ATCE_ConnectionDeal1;
		String connectionMeetingaAndCall=ATCE_ConnectionMeetingAndCall1;
		String connectionEmail=ATCE_ConnectionEmail1;
		
		String userName1=crmUser6FirstName+" "+crmUser6LastName;
		String userName2=crmUser7FirstName+" "+crmUser7LastName;
		String userName3=crmUser8FirstName+" "+crmUser8LastName;
		
		String[] participantArr=ATCE_ATParticipants1.split("<break>");
		String[] participants=new String[participantArr.length];
		
		for(int i=0; i<participantArr.length; i++)
		{
			if(participantArr[i].trim().equalsIgnoreCase("PE User 1"))
			{
				participants[i]=userName1;
			}
			else if(participantArr[i].trim().equalsIgnoreCase("PE User 2"))
			{
				participants[i]=userName2;
			}
			else if(participantArr[i].trim().equalsIgnoreCase("PE User 3"))
			{
				participants[i]=userName3;
			}
			else
			{
				participants[i]=participantArr[i];
			}
		}
		
		
		String[] tagsArr=ATCE_ATTags1.split("<break>");
		
		
		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

			if (lp.clickOnTab(projectName, tabObj1)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

				if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.CompaniesTab,
						recordName, 30)) {
					log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

					if (bp.clicktabOnPage(TabName.Acuity.toString())) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						ArrayList<String> result = bp.verifyRecordOnInteractionCard(taskDueDate,IconType.Task,taskSubject, taskNotes, true, false,null,null);
						if (result.isEmpty()) {
							log(LogStatus.PASS,taskSubject + " record has been verified on intraction",YesNo.No);
							sa.assertTrue(true,taskSubject + " record has been verified on intraction");
						} else {
							log(LogStatus.ERROR,taskSubject + " record is not verified on intraction. "+result,YesNo.No);
							sa.assertTrue(false,taskSubject + " record is not verified on intraction. "+result);
						}
						
						ArrayList<String> result1 = bp.verifyRecordOnInteractionCard(taskDueDate1,IconType.Call,taskSubject1, taskNotes1, true, false,null,null);
						if (result1.isEmpty()) {
							log(LogStatus.PASS,taskSubject1 + " record has been verified on intraction",YesNo.No);
							sa.assertTrue(true,taskSubject1 + " record has been verified on intraction");
						} else {
							log(LogStatus.ERROR,taskSubject1 + " record is not verified on intraction. "+result1,YesNo.No);
							sa.assertTrue(false,taskSubject1 + " record is not verified on intraction. "+result1);
						}
						
						ArrayList<String> result2=bp.verifySubjectLinkPopUpOnIntraction(driver, taskSubject, basicsection, advanceSection, IconType.Task, PageName.AcuityDetails);
						
						if(result2.isEmpty())
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject+" has been verified",YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject+" has been verified");							
						}
						else
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject+" are not verified. "+result2,YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject+" are not verified. "+result2);
							
						}
						
                       ArrayList<String> result3=bp.verifySubjectLinkPopUpOnIntraction(driver, taskSubject1, basicsection1, advanceSection1, IconType.Call, PageName.AcuityDetails);
						
						if(result3.isEmpty())
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject1+" has been verified",YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject1+" has been verified");							
						}
						else
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject1+" are not verified. "+result3,YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject1+" are not verified. "+result3);
							
						}
						

						ArrayList<String> result4=bp.verifyRecordOnContactSectionAcuity(contactSectionName, contactSectionTitle, contactSectionDeal, contactSectionMeetingAndCalls, contactSectionEmail);
						if(result4.isEmpty())
						{
							log(LogStatus.INFO, contactSectionName+" record on Contact section has been verified for "+recordName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, contactSectionName+" record on Contact section is not verified for "+recordName+". "+result4, YesNo.No);
							sa.assertTrue(false, contactSectionName+" record on Contact section is not verified for "+recordName+". "+result4);
						}
						
						ArrayList<String> result5=bp.verifyRecordAndReferencedTypeOnTagged(firmsTaggedName, firmsTaggedTimeReference, peopleTaggedName, peopleTaggedTimeReference, dealTaggedName, dealTaggedTimeReference,isInstitutionRecord, null,null);
						if(result5.isEmpty())
						{
							log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR,  "The record name and Time reference are not verifed. "+result5, YesNo.No);
							sa.assertTrue(false,  "The record name and Time reference are not verifed."+result5);
						}
						
						if(click(driver, bp.getMeetingAndCallCount(contactSectionName, 20), "Meeting and call count", action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, "Clicked on the count of meeting and call of "+contactSectionName+" record on contact section",YesNo.No);
							
							ArrayList<String> result6=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity("call", taskDueDate1, taskSubject1, taskNotes1, participants, tagsArr);
							if(result6.isEmpty())
							{
								log(LogStatus.INFO, "The records on meeting & calls popup have been verified for "+recordName, YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "The records on meeting & calls popup are not verified for "+recordName+". "+result6, YesNo.No);
								sa.assertTrue(false, "The records on meeting & calls popup are not verified for "+recordName+". "+result6);
							}
						
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+contactSectionName+" record on contact section",	YesNo.No);		
							sa.assertTrue(false,  "Not able to click on the count of meeting and call of "+contactSectionName+" record on contact section");
						}
						
						
						ArrayList<String> result7=bp.verifyRecordOnConnectionsPopUpOfContactInAcuity(contactSectionName, connectionUserName, connectionTitle, connectionDeal, connectionMeetingaAndCall, connectionEmail);
						if(result7.isEmpty())
						{
							log(LogStatus.INFO, "The records on Connection popup have been verified for "+contactSectionName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records on Connection popup are not verified for "+contactSectionName+". "+result7 , YesNo.No);
							sa.assertTrue(false, "The records on Connection popup are not verified for "+contactSectionName+". "+result7);
						}
						
						if (CommonLib.clickUsingJavaScript(driver, bp.contactNameUserIconButton(contactSectionName, 30), "Contact Name: " + contactSectionName,
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on connection icon of contact : " + contactSectionName, YesNo.No);
							String parentID=switchOnWindow(driver);
							if(CommonLib.clickUsingJavaScript(driver, bp.getMeetingAndCallCount(userName1, 20),"Count of "+userName1+" on contact section" , action.SCROLLANDBOOLEAN))
							{
								log(LogStatus.INFO, "clicked on count of "+userName1,YesNo.No);
								ArrayList<String> result6=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity("call", taskDueDate1, taskSubject1, taskNotes1, participants, tagsArr);
								if(result6.isEmpty())
								{
									log(LogStatus.INFO, "The records on meeting & calls popup have been verified for "+recordName, YesNo.No);
								}
								else
								{
									log(LogStatus.ERROR, "The records on meeting & calls popup are not verified for "+recordName+". "+result6, YesNo.No);
									sa.assertTrue(false, "The records on meeting & calls popup are not verified for "+recordName+". "+result6);
								}
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on count of "+contactSectionName,YesNo.No);
								sa.assertTrue(false,  "Not able to click on count of "+contactSectionName);
							}		
							driver.close();
							driver.switchTo().window(parentID);
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on connection icon of contact : " + contactSectionName, YesNo.No);
							sa.assertTrue(false, "Not able to click on connection icon of contact : " + contactSectionName);
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
	public void ATETc024_VerifyTaskCallFunctionalityOnAdvisorRecordType(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATCERecord5;
		String taskSubject=ATCE_ATSubject1;
		String taskRelatedTo=ATCE_ATRelatedTo1;
		String taskNotes=ATCE_ATNote1;
		String taskStatus=ATCE_AdvanceStatus1;
		String taskPriority=ATCE_AdvancePriority1;	
		String taskDueDate =ATCE_ATDueDate1;
		
		String taskSubject1=ATCE_ATSubject2;
		String taskRelatedTo1=ATCE_ATRelatedTo2;
		String taskNotes1=ATCE_ATNote2;
		
		String taskDueDate1 =ATCE_ATDueDate2;
		
		String contactSectionName=ATCE_ConName2;
		String contactSectionTitle=ATCE_ConTitle2;
		String contactSectionDeal=ATCE_ConDeal2;
		String contactSectionMeetingAndCalls=ATCE_ConMeetingAndCall2;
		String contactSectionEmail=ATCE_ConEmail2;

		String[] firmsTaggedName= {ATCE_TaggedFirmsName1,ATCE_TaggedFirmsName2,ATCE_TaggedFirmsName4,ATCE_TaggedFirmsName5,ATCE_TaggedFirmsName6,ATCE_TaggedFirmsName7,ATCE_TaggedFirmsName8,ATCE_TaggedFirmsName9,ATCE_TaggedFirmsName10};
		String[] firmsTaggedTimeReference= {ATCE_TaggedFirmsCount1,ATCE_TaggedFirmsCount2,ATCE_TaggedFirmsCount4,ATCE_TaggedFirmsCount5,ATCE_TaggedFirmsCount6,ATCE_TaggedFirmsCount7,ATCE_TaggedFirmsCount8,ATCE_TaggedFirmsCount9,ATCE_TaggedFirmsCount10};

		String[] peopleTaggedName= {ATCE_TaggedPeopleName1,ATCE_TaggedPeopleName2,ATCE_TaggedPeopleName3,ATCE_TaggedPeopleName5,ATCE_TaggedPeopleName6,ATCE_TaggedPeopleName7};
		String[] peopleTaggedTimeReference= {ATCE_TaggedPeopleCount1,ATCE_TaggedPeopleCount2,ATCE_TaggedPeopleCount3,ATCE_TaggedPeopleCount5,ATCE_TaggedPeopleCount6,ATCE_TaggedPeopleCount7};
				
		String[] dealTaggedName= {ATCE_TaggedDealName1};
		String[] dealTaggedTimeReference= {ATCE_TaggedDealCount1};
		
		String[][] basicsection = { { "Subject", taskSubject }, { "Notes", taskNotes }, { "Related_To", taskRelatedTo } };
		String[][] advanceSection = { { "Due Date", taskDueDate }, {"Status", taskStatus}, {"Priority", taskPriority} };
		
		String[][] basicsection1 = { { "Subject", taskSubject1 }, { "Notes", taskNotes1 }, { "Related_To", taskRelatedTo1 } };
		String[][] advanceSection1 = { { "Date", taskDueDate1 }};
		
		String connectionUserName=crmUser6FirstName+" "+crmUser6LastName;
		String connectionTitle=ATCE_ConnectionTitle1;
		String connectionDeal=ATCE_ConnectionDeal1;
		String connectionMeetingaAndCall=ATCE_ConnectionMeetingAndCall1;
		String connectionEmail=ATCE_ConnectionEmail1;
		
		String userName1=crmUser6FirstName+" "+crmUser6LastName;
		String userName2=crmUser7FirstName+" "+crmUser7LastName;
		String userName3=crmUser8FirstName+" "+crmUser8LastName;
		
		String[] participantArr=ATCE_ATParticipants1.split("<break>");
		String[] participants=new String[participantArr.length];
		
		for(int i=0; i<participantArr.length; i++)
		{
			if(participantArr[i].trim().equalsIgnoreCase("PE User 1"))
			{
				participants[i]=userName1;
			}
			else if(participantArr[i].trim().equalsIgnoreCase("PE User 2"))
			{
				participants[i]=userName2;
			}
			else if(participantArr[i].trim().equalsIgnoreCase("PE User 3"))
			{
				participants[i]=userName3;
			}
			else
			{
				participants[i]=participantArr[i];
			}
		}
		
		
		String[] tagsArr=ATCE_ATTags1.split("<break>");
		
		
		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

			if (lp.clickOnTab(projectName, tabObj1)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

				if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
						recordName, 30)) {
					log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

					if (bp.clicktabOnPage(TabName.Acuity.toString())) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						ArrayList<String> result = bp.verifyRecordOnInteractionCard(taskDueDate,IconType.Task,taskSubject, taskNotes, true, false,null,null);
						if (result.isEmpty()) {
							log(LogStatus.PASS,taskSubject + " record has been verified on intraction",YesNo.No);
							sa.assertTrue(true,taskSubject + " record has been verified on intraction");
						} else {
							log(LogStatus.ERROR,taskSubject + " record is not verified on intraction. "+result,YesNo.No);
							sa.assertTrue(false,taskSubject + " record is not verified on intraction. "+result);
						}
						
						ArrayList<String> result1 = bp.verifyRecordOnInteractionCard(taskDueDate1,IconType.Call,taskSubject1, taskNotes1, true, false,null,null);
						if (result1.isEmpty()) {
							log(LogStatus.PASS,taskSubject1 + " record has been verified on intraction",YesNo.No);
							sa.assertTrue(true,taskSubject1 + " record has been verified on intraction");
						} else {
							log(LogStatus.ERROR,taskSubject1 + " record is not verified on intraction. "+result1,YesNo.No);
							sa.assertTrue(false,taskSubject1 + " record is not verified on intraction. "+result1);
						}
						
						ArrayList<String> result2=bp.verifySubjectLinkPopUpOnIntraction(driver, taskSubject, basicsection, advanceSection, IconType.Task, PageName.AcuityDetails);
						
						if(result2.isEmpty())
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject+" has been verified",YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject+" has been verified");							
						}
						else
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject+" are not verified. "+result2,YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject+" are not verified. "+result2);
							
						}
						
                       ArrayList<String> result3=bp.verifySubjectLinkPopUpOnIntraction(driver, taskSubject1, basicsection1, advanceSection1, IconType.Call, PageName.AcuityDetails);
						
						if(result3.isEmpty())
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject1+" has been verified",YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject1+" has been verified");							
						}
						else
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject1+" are not verified. "+result3,YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject1+" are not verified. "+result3);
							
						}
						

						ArrayList<String> result4=bp.verifyRecordOnContactSectionAcuity(contactSectionName, contactSectionTitle, contactSectionDeal, contactSectionMeetingAndCalls, contactSectionEmail);
						if(result4.isEmpty())
						{
							log(LogStatus.INFO, contactSectionName+" record on Contact section has been verified for "+recordName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, contactSectionName+" record on Contact section is not verified for "+recordName+". "+result4, YesNo.No);
							sa.assertTrue(false, contactSectionName+" record on Contact section is not verified for "+recordName+". "+result4);
						}
						
						ArrayList<String> result5=bp.verifyRecordAndReferencedTypeOnTagged(firmsTaggedName, firmsTaggedTimeReference, peopleTaggedName, peopleTaggedTimeReference, dealTaggedName, dealTaggedTimeReference,isInstitutionRecord, null,null);
						if(result5.isEmpty())
						{
							log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR,  "The record name and Time reference are not verifed. "+result5, YesNo.No);
							sa.assertTrue(false,  "The record name and Time reference are not verifed."+result5);
						}
						
						if(click(driver, bp.getMeetingAndCallCount(contactSectionName, 20), "Meeting and call count", action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, "Clicked on the count of meeting and call of "+contactSectionName+" record on contact section",YesNo.No);
							
							ArrayList<String> result6=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity("call", taskDueDate1, taskSubject1, taskNotes1, participants, tagsArr);
							if(result6.isEmpty())
							{
								log(LogStatus.INFO, "The records on meeting & calls popup have been verified for "+recordName, YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "The records on meeting & calls popup are not verified for "+recordName+". "+result6, YesNo.No);
								sa.assertTrue(false, "The records on meeting & calls popup are not verified for "+recordName+". "+result6);
							}
						
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+contactSectionName+" record on contact section",	YesNo.No);		
							sa.assertTrue(false,  "Not able to click on the count of meeting and call of "+contactSectionName+" record on contact section");
						}
						
						
						ArrayList<String> result7=bp.verifyRecordOnConnectionsPopUpOfContactInAcuity(contactSectionName, connectionUserName, connectionTitle, connectionDeal, connectionMeetingaAndCall, connectionEmail);
						if(result7.isEmpty())
						{
							log(LogStatus.INFO, "The records on Connection popup have been verified for "+contactSectionName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records on Connection popup are not verified for "+contactSectionName+". "+result7 , YesNo.No);
							sa.assertTrue(false, "The records on Connection popup are not verified for "+contactSectionName+". "+result7);
						}
						
						if (CommonLib.clickUsingJavaScript(driver, bp.contactNameUserIconButton(contactSectionName, 30), "Contact Name: " + contactSectionName,
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on connection icon of contact : " + contactSectionName, YesNo.No);
							String parentID=switchOnWindow(driver);
							if(CommonLib.clickUsingJavaScript(driver, bp.getMeetingAndCallCount(userName1, 20),"Count of "+userName1+" on contact section" , action.SCROLLANDBOOLEAN))
							{
								log(LogStatus.INFO, "clicked on count of "+userName1,YesNo.No);
								ArrayList<String> result6=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity("call", taskDueDate1, taskSubject1, taskNotes1, participants, tagsArr);
								if(result6.isEmpty())
								{
									log(LogStatus.INFO, "The records on meeting & calls popup have been verified for "+recordName, YesNo.No);
								}
								else
								{
									log(LogStatus.ERROR, "The records on meeting & calls popup are not verified for "+recordName+". "+result6, YesNo.No);
									sa.assertTrue(false, "The records on meeting & calls popup are not verified for "+recordName+". "+result6);
								}
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on count of "+contactSectionName,YesNo.No);
								sa.assertTrue(false,  "Not able to click on count of "+contactSectionName);
							}		
							driver.close();
							driver.switchTo().window(parentID);
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on connection icon of contact : " + contactSectionName, YesNo.No);
							sa.assertTrue(false, "Not able to click on connection icon of contact : " + contactSectionName);
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
	public void ATETc025_VerifyTaskCallFunctionalityOnLendorRecordType(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATCERecord7;
		String taskSubject=ATCE_ATSubject1;
		String taskRelatedTo=ATCE_ATRelatedTo1;
		String taskNotes=ATCE_ATNote1;
		String taskStatus=ATCE_AdvanceStatus1;
		String taskPriority=ATCE_AdvancePriority1;	
		String taskDueDate =ATCE_ATDueDate1;
		
		String taskSubject1=ATCE_ATSubject2;
		String taskRelatedTo1=ATCE_ATRelatedTo2;
		String taskNotes1=ATCE_ATNote2;
		
		String taskDueDate1 =ATCE_ATDueDate2;
		
		String contactSectionName=ATCE_ConName3;
		String contactSectionTitle=ATCE_ConTitle3;
		String contactSectionDeal=ATCE_ConDeal3;
		String contactSectionMeetingAndCalls=ATCE_ConMeetingAndCall3;
		String contactSectionEmail=ATCE_ConEmail3;

		String[] firmsTaggedName= {ATCE_TaggedFirmsName1,ATCE_TaggedFirmsName2,ATCE_TaggedFirmsName3,ATCE_TaggedFirmsName5,ATCE_TaggedFirmsName6,ATCE_TaggedFirmsName7,ATCE_TaggedFirmsName8,ATCE_TaggedFirmsName9,ATCE_TaggedFirmsName10};
		String[] firmsTaggedTimeReference= {ATCE_TaggedFirmsCount1,ATCE_TaggedFirmsCount3,ATCE_TaggedFirmsCount4,ATCE_TaggedFirmsCount5,ATCE_TaggedFirmsCount6,ATCE_TaggedFirmsCount7,ATCE_TaggedFirmsCount8,ATCE_TaggedFirmsCount9,ATCE_TaggedFirmsCount10};

		String[] peopleTaggedName= {ATCE_TaggedPeopleName1,ATCE_TaggedPeopleName2,ATCE_TaggedPeopleName3,ATCE_TaggedPeopleName4,ATCE_TaggedPeopleName5,ATCE_TaggedPeopleName6,ATCE_TaggedPeopleName7};
		String[] peopleTaggedTimeReference= {ATCE_TaggedPeopleCount1,ATCE_TaggedPeopleCount2,ATCE_TaggedPeopleCount3,ATCE_TaggedPeopleCount4,ATCE_TaggedPeopleCount5,ATCE_TaggedPeopleCount6,ATCE_TaggedPeopleCount7};
				
		String[] dealTaggedName= {ATCE_TaggedDealName1};
		String[] dealTaggedTimeReference= {ATCE_TaggedDealCount1};
		
		String[][] basicsection = { { "Subject", taskSubject }, { "Notes", taskNotes }, { "Related_To", taskRelatedTo } };
		String[][] advanceSection = { { "Due Date", taskDueDate }, {"Status", taskStatus}, {"Priority", taskPriority} };
		
		String[][] basicsection1 = { { "Subject", taskSubject1 }, { "Notes", taskNotes1 }, { "Related_To", taskRelatedTo1 } };
		String[][] advanceSection1 = { { "Date", taskDueDate1 }};
	
		String contactName=ATCE_ContactFullName7;
		String message=bp.acuityDefaultMessage;
		
		String[] meetingAndCall=ATCE_MeetingAndCallHeader.split("<break>");
		
		ArrayList<String> headerNameMeetingAndCall=new ArrayList<String>();
		for(String txt:meetingAndCall)
		{
			headerNameMeetingAndCall.add(txt);
		}

		String[] externalConnectionHeaderArr=ATCE_ConnectionHeader2.split("<break>");
		ArrayList<String> exteralHeaderName=new ArrayList<String>();
		for(String txt:externalConnectionHeaderArr)
		{
			exteralHeaderName.add(txt);
		}		
		String[] val=ATCE_ConnectionHeader1.split("<break>");
		ArrayList<String> headerName=new ArrayList<String>();
		for(String txt:val)
		{
			headerName.add(txt);
		}
		
		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);
			if (lp.clickOnTab(projectName, tabObj1)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

				if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
						recordName, 30)) {
					log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

					if (bp.clicktabOnPage(TabName.Acuity.toString())) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						ArrayList<String> result = bp.verifyRecordOnInteractionCard(taskDueDate,IconType.Task,taskSubject, taskNotes, true, false,null,null);
						if (result.isEmpty()) {
							log(LogStatus.PASS,taskSubject + " record has been verified on intraction",YesNo.No);
							sa.assertTrue(true,taskSubject + " record has been verified on intraction");
						} else {
							log(LogStatus.ERROR,taskSubject + " record is not verified on intraction. "+result,YesNo.No);
							sa.assertTrue(false,taskSubject + " record is not verified on intraction. "+result);
						}
						
						ArrayList<String> result1 = bp.verifyRecordOnInteractionCard(taskDueDate1,IconType.Call,taskSubject1, taskNotes1, true, false,null,null);
						if (result1.isEmpty()) {
							log(LogStatus.PASS,taskSubject1 + " record has been verified on intraction",YesNo.No);
							sa.assertTrue(true,taskSubject1 + " record has been verified on intraction");
						} else {
							log(LogStatus.ERROR,taskSubject1 + " record is not verified on intraction. "+result1,YesNo.No);
							sa.assertTrue(false,taskSubject1 + " record is not verified on intraction. "+result1);
						}
						
						ArrayList<String> result2=bp.verifySubjectLinkPopUpOnIntraction(driver, taskSubject, basicsection, advanceSection, IconType.Task, PageName.AcuityDetails);
						
						if(result2.isEmpty())
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject+" has been verified",YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject+" has been verified");							
						}
						else
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject+" are not verified. "+result2,YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject+" are not verified. "+result2);
							
						}
						
                       ArrayList<String> result3=bp.verifySubjectLinkPopUpOnIntraction(driver, taskSubject1, basicsection1, advanceSection1, IconType.Call, PageName.AcuityDetails);
						
						if(result3.isEmpty())
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject1+" has been verified",YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject1+" has been verified");							
						}
						else
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject1+" are not verified. "+result3,YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject1+" are not verified. "+result3);
							
						}
						

						ArrayList<String> result4=bp.verifyRecordOnContactSectionAcuity(contactSectionName, contactSectionTitle, contactSectionDeal, contactSectionMeetingAndCalls, contactSectionEmail);
						if(result4.isEmpty())
						{
							log(LogStatus.INFO, contactSectionName+" record on Contact section has been verified for "+recordName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, contactSectionName+" record on Contact section is not verified for "+recordName+". "+result4, YesNo.No);
							sa.assertTrue(false, contactSectionName+" record on Contact section is not verified for "+recordName+". "+result4);
						}
						
						ArrayList<String> result5=bp.verifyRecordAndReferencedTypeOnTagged(firmsTaggedName, firmsTaggedTimeReference, peopleTaggedName, peopleTaggedTimeReference, dealTaggedName, dealTaggedTimeReference,isInstitutionRecord, null,null);
						if(result5.isEmpty())
						{
							log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR,  "The record name and Time reference are not verifed. "+result5, YesNo.No);
							sa.assertTrue(false,  "The record name and Time reference are not verifed."+result5);
						}
						
						
						if(click(driver, bp.getConnectionIconOfContact(contactName, 20), "Connection icon of "+contactName, action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, "clicked on Connection icon of "+contactName, YesNo.No);

							ArrayList<String> result6=bp.verifyUIOfConnectionPopup(contactName, headerName, message,exteralHeaderName,message);
							if(result6.isEmpty())
							{
								log(LogStatus.INFO, "The UI of Connections popup have been verified", YesNo.No);
								sa.assertTrue(true,  "The UI of Connections popup have been verified");
							}
							else
							{
								log(LogStatus.ERROR, "The UI of Connections popup are not verified. "+result6, YesNo.No);
								sa.assertTrue(false,  "The UI of Connections popup are not verified. "+result6);

							}
						}
						else
						{
							log(LogStatus.ERROR, "Not able to Connection icon of "+contactName, YesNo.No);
							sa.assertTrue(false,  "Not able to Connection icon of "+contactName);
						}
						
						
						if(click(driver, bp.getMeetingAndCallCount(contactName, 20), "Meeting and call count", action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, "Clicked on the count of meeting and call of "+contactName+" record on contact section",YesNo.No);
							
							ArrayList<String> result7=bp.verifyUIOfMeetingAndCallsPopup( headerNameMeetingAndCall, message);
							if(result7.isEmpty())
							{
								log(LogStatus.INFO, "The UI of Meeting and call page have been verified", YesNo.No);
								sa.assertTrue(true,  "The UI of Meeting and call page have been verified");
							}
							else
							{
								log(LogStatus.ERROR, "The UI of Meeting and call page are not verified. "+result7, YesNo.No);
								sa.assertTrue(false,  "The UI of Meeting and call page are not verified. "+result7);

							}					
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+contactName+" record on contact section",	YesNo.No);		
							sa.assertTrue(false,  "Not able to click on the count of meeting and call of "+contactName+" record on contact section");
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
	public void ATETc026_VerifyTaskCallFunctionalityOnIntermediaryRecordType(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATCERecord9;
		String taskSubject=ATCE_ATSubject1;
		String taskRelatedTo=ATCE_ATRelatedTo1;
		String taskNotes=ATCE_ATNote1;
		String taskStatus=ATCE_AdvanceStatus1;
		String taskPriority=ATCE_AdvancePriority1;	
		String taskDueDate =ATCE_ATDueDate1;
		
		String taskSubject1=ATCE_ATSubject2;
		String taskRelatedTo1=ATCE_ATRelatedTo2;
		String taskNotes1=ATCE_ATNote2;
		
		String taskDueDate1 =ATCE_ATDueDate2;
		
		String contactSectionName=ATCE_ConName4;
		String contactSectionTitle=ATCE_ConTitle4;
		String contactSectionDeal=ATCE_ConDeal4;
		String contactSectionMeetingAndCalls=ATCE_ConMeetingAndCall4;
		String contactSectionEmail=ATCE_ConEmail4;

		String[] firmsTaggedName= {ATCE_TaggedFirmsName1,ATCE_TaggedFirmsName2,ATCE_TaggedFirmsName3,ATCE_TaggedFirmsName4,ATCE_TaggedFirmsName5,ATCE_TaggedFirmsName7,ATCE_TaggedFirmsName8,ATCE_TaggedFirmsName9,ATCE_TaggedFirmsName10};
		String[] firmsTaggedTimeReference= {ATCE_TaggedFirmsCount1,ATCE_TaggedFirmsCount3,ATCE_TaggedFirmsCount4,ATCE_TaggedFirmsCount4,ATCE_TaggedFirmsCount5,ATCE_TaggedFirmsCount7,ATCE_TaggedFirmsCount8,ATCE_TaggedFirmsCount9,ATCE_TaggedFirmsCount10};

		String[] peopleTaggedName= {ATCE_TaggedPeopleName1,ATCE_TaggedPeopleName2,ATCE_TaggedPeopleName3,ATCE_TaggedPeopleName4,ATCE_TaggedPeopleName5,ATCE_TaggedPeopleName6,ATCE_TaggedPeopleName7};
		String[] peopleTaggedTimeReference= {ATCE_TaggedPeopleCount1,ATCE_TaggedPeopleCount2,ATCE_TaggedPeopleCount3,ATCE_TaggedPeopleCount4,ATCE_TaggedPeopleCount5,ATCE_TaggedPeopleCount6,ATCE_TaggedPeopleCount7};
				
		String[] dealTaggedName= {ATCE_TaggedDealName1};
		String[] dealTaggedTimeReference= {ATCE_TaggedDealCount1};
		
		String[][] basicsection = { { "Subject", taskSubject }, { "Notes", taskNotes }, { "Related_To", taskRelatedTo } };
		String[][] advanceSection = { { "Due Date", taskDueDate }, {"Status", taskStatus}, {"Priority", taskPriority} };
		
		String[][] basicsection1 = { { "Subject", taskSubject1 }, { "Notes", taskNotes1 }, { "Related_To", taskRelatedTo1 } };
		String[][] advanceSection1 = { { "Date", taskDueDate1 }};
	
		String contactName=ATCE_ContactFullName5;
		String message=bp.acuityDefaultMessage;
		
		String[] meetingAndCall=ATCE_MeetingAndCallHeader.split("<break>");
		
		ArrayList<String> headerNameMeetingAndCall=new ArrayList<String>();
		for(String txt:meetingAndCall)
		{
			headerNameMeetingAndCall.add(txt);
		}

		String[] externalConnectionHeaderArr=ATCE_ConnectionHeader2.split("<break>");
		ArrayList<String> exteralHeaderName=new ArrayList<String>();
		for(String txt:externalConnectionHeaderArr)
		{
			exteralHeaderName.add(txt);
		}		
		String[] val=ATCE_ConnectionHeader1.split("<break>");
		ArrayList<String> headerName=new ArrayList<String>();
		for(String txt:val)
		{
			headerName.add(txt);
		}
		
		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);
			if (lp.clickOnTab(projectName, tabObj1)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

				if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
						recordName, 30)) {
					log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

					if (bp.clicktabOnPage(TabName.Acuity.toString())) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						ArrayList<String> result = bp.verifyRecordOnInteractionCard(taskDueDate,IconType.Task,taskSubject, taskNotes, true, false,null,null);
						if (result.isEmpty()) {
							log(LogStatus.PASS,taskSubject + " record has been verified on intraction",YesNo.No);
							sa.assertTrue(true,taskSubject + " record has been verified on intraction");
						} else {
							log(LogStatus.ERROR,taskSubject + " record is not verified on intraction. "+result,YesNo.No);
							sa.assertTrue(false,taskSubject + " record is not verified on intraction. "+result);
						}
						
						ArrayList<String> result1 = bp.verifyRecordOnInteractionCard(taskDueDate1,IconType.Call,taskSubject1, taskNotes1, true, false,null,null);
						if (result1.isEmpty()) {
							log(LogStatus.PASS,taskSubject1 + " record has been verified on intraction",YesNo.No);
							sa.assertTrue(true,taskSubject1 + " record has been verified on intraction");
						} else {
							log(LogStatus.ERROR,taskSubject1 + " record is not verified on intraction. "+result1,YesNo.No);
							sa.assertTrue(false,taskSubject1 + " record is not verified on intraction. "+result1);
						}
						
						ArrayList<String> result2=bp.verifySubjectLinkPopUpOnIntraction(driver, taskSubject, basicsection, advanceSection, IconType.Task, PageName.AcuityDetails);
						
						if(result2.isEmpty())
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject+" has been verified",YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject+" has been verified");							
						}
						else
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject+" are not verified. "+result2,YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject+" are not verified. "+result2);
							
						}
						
                       ArrayList<String> result3=bp.verifySubjectLinkPopUpOnIntraction(driver, taskSubject1, basicsection1, advanceSection1, IconType.Call, PageName.AcuityDetails);
						
						if(result3.isEmpty())
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject1+" has been verified",YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject1+" has been verified");							
						}
						else
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject1+" are not verified. "+result3,YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject1+" are not verified. "+result3);
							
						}
						

						ArrayList<String> result4=bp.verifyRecordOnContactSectionAcuity(contactSectionName, contactSectionTitle, contactSectionDeal, contactSectionMeetingAndCalls, contactSectionEmail);
						if(result4.isEmpty())
						{
							log(LogStatus.INFO, contactSectionName+" record on Contact section has been verified for "+recordName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, contactSectionName+" record on Contact section is not verified for "+recordName+". "+result4, YesNo.No);
							sa.assertTrue(false, contactSectionName+" record on Contact section is not verified for "+recordName+". "+result4);
						}
						
						ArrayList<String> result5=bp.verifyRecordAndReferencedTypeOnTagged(firmsTaggedName, firmsTaggedTimeReference, peopleTaggedName, peopleTaggedTimeReference, dealTaggedName, dealTaggedTimeReference,isInstitutionRecord, null,null);
						if(result5.isEmpty())
						{
							log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR,  "The record name and Time reference are not verifed. "+result5, YesNo.No);
							sa.assertTrue(false,  "The record name and Time reference are not verifed."+result5);
						}
						
						
						if(click(driver, bp.getConnectionIconOfContact(contactName, 20), "Connection icon of "+contactName, action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, "clicked on Connection icon of "+contactName, YesNo.No);

							ArrayList<String> result6=bp.verifyUIOfConnectionPopup(contactName, headerName, message,exteralHeaderName,message);
							if(result6.isEmpty())
							{
								log(LogStatus.INFO, "The UI of Connections popup have been verified", YesNo.No);
								sa.assertTrue(true,  "The UI of Connections popup have been verified");
							}
							else
							{
								log(LogStatus.ERROR, "The UI of Connections popup are not verified. "+result6, YesNo.No);
								sa.assertTrue(false,  "The UI of Connections popup are not verified. "+result6);

							}
						}
						else
						{
							log(LogStatus.ERROR, "Not able to Connection icon of "+contactName, YesNo.No);
							sa.assertTrue(false,  "Not able to Connection icon of "+contactName);
						}
						
						
						if(click(driver, bp.getMeetingAndCallCount(contactName, 20), "Meeting and call count", action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, "Clicked on the count of meeting and call of "+contactName+" record on contact section",YesNo.No);
							
							ArrayList<String> result7=bp.verifyUIOfMeetingAndCallsPopup( headerNameMeetingAndCall, message);
							if(result7.isEmpty())
							{
								log(LogStatus.INFO, "The UI of Meeting and call page have been verified", YesNo.No);
								sa.assertTrue(true,  "The UI of Meeting and call page have been verified");
							}
							else
							{
								log(LogStatus.ERROR, "The UI of Meeting and call page are not verified. "+result7, YesNo.No);
								sa.assertTrue(false,  "The UI of Meeting and call page are not verified. "+result7);

							}					
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+contactName+" record on contact section",	YesNo.No);		
							sa.assertTrue(false,  "Not able to click on the count of meeting and call of "+contactName+" record on contact section");
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
	public void ATETc027_VerifyTaskCallFunctionalityOnInstitutionRecordType(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATCERecord3;
		String taskSubject=ATCE_ATSubject1;
		String taskRelatedTo=ATCE_ATRelatedTo1;
		String taskNotes=ATCE_ATNote1;
		String taskStatus=ATCE_AdvanceStatus1;
		String taskPriority=ATCE_AdvancePriority1;	
		String taskDueDate =ATCE_ATDueDate1;
		
		String taskSubject1=ATCE_ATSubject2;
		String taskRelatedTo1=ATCE_ATRelatedTo2;
		String taskNotes1=ATCE_ATNote2;
		
		String taskDueDate1 =ATCE_ATDueDate2;
		
		String contactSectionName=ATCE_ConName5;
		String contactSectionTitle=ATCE_ConTitle5;
		String contactSectionDeal=ATCE_ConDeal5;
		String contactSectionMeetingAndCalls=ATCE_ConMeetingAndCall5;
		String contactSectionEmail=ATCE_ConEmail5;

		String[] firmsTaggedName= {ATCE_TaggedFirmsName2,ATCE_TaggedFirmsName3,ATCE_TaggedFirmsName4,ATCE_TaggedFirmsName5,ATCE_TaggedFirmsName7,ATCE_TaggedFirmsName8,ATCE_TaggedFirmsName9,ATCE_TaggedFirmsName10};
		String[] firmsTaggedTimeReference= {ATCE_TaggedFirmsCount3,ATCE_TaggedFirmsCount4,ATCE_TaggedFirmsCount4,ATCE_TaggedFirmsCount5,ATCE_TaggedFirmsCount7,ATCE_TaggedFirmsCount8,ATCE_TaggedFirmsCount9,ATCE_TaggedFirmsCount10};

		String[] peopleTaggedName= {ATCE_TaggedPeopleName1,ATCE_TaggedPeopleName2,ATCE_TaggedPeopleName3,ATCE_TaggedPeopleName4,ATCE_TaggedPeopleName5,ATCE_TaggedPeopleName6,ATCE_TaggedPeopleName7};
		String[] peopleTaggedTimeReference= {ATCE_TaggedPeopleCount1,ATCE_TaggedPeopleCount2,ATCE_TaggedPeopleCount3,ATCE_TaggedPeopleCount4,ATCE_TaggedPeopleCount5,ATCE_TaggedPeopleCount6,ATCE_TaggedPeopleCount7};
				
		String[] fundTaggedName= {ATCE_TaggedFundName1};
		String[] fundTaggedTimeReference= {ATCE_TaggedFundCount1};
		
		String[][] basicsection = { { "Subject", taskSubject }, { "Notes", taskNotes }, { "Related_To", taskRelatedTo } };
		String[][] advanceSection = { { "Due Date", taskDueDate }, {"Status", taskStatus}, {"Priority", taskPriority} };
		
		String[][] basicsection1 = { { "Subject", taskSubject1 }, { "Notes", taskNotes1 }, { "Related_To", taskRelatedTo1 } };
		String[][] advanceSection1 = { { "Date", taskDueDate1 }};
	
		String contactName=ATCE_ContactFullName3;
		String message=bp.acuityDefaultMessage;
		
		String[] meetingAndCall=ATCE_MeetingAndCallHeader.split("<break>");
		
		ArrayList<String> headerNameMeetingAndCall=new ArrayList<String>();
		for(String txt:meetingAndCall)
		{
			headerNameMeetingAndCall.add(txt);
		}

		String[] externalConnectionHeaderArr=ATCE_ConnectionHeader2.split("<break>");
		ArrayList<String> exteralHeaderName=new ArrayList<String>();
		for(String txt:externalConnectionHeaderArr)
		{
			exteralHeaderName.add(txt);
		}		
		String[] val=ATCE_ConnectionHeader1.split("<break>");
		ArrayList<String> headerName=new ArrayList<String>();
		for(String txt:val)
		{
			headerName.add(txt);
		}
		
		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);
			if (lp.clickOnTab(projectName, tabObj1)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

				if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
						recordName, 30)) {
					log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

					if (bp.clicktabOnPage(TabName.Acuity.toString())) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						ArrayList<String> result = bp.verifyRecordOnInteractionCard(taskDueDate,IconType.Task,taskSubject, taskNotes, true, false,null,null);
						if (result.isEmpty()) {
							log(LogStatus.PASS,taskSubject + " record has been verified on intraction",YesNo.No);
							sa.assertTrue(true,taskSubject + " record has been verified on intraction");
						} else {
							log(LogStatus.ERROR,taskSubject + " record is not verified on intraction. "+result,YesNo.No);
							sa.assertTrue(false,taskSubject + " record is not verified on intraction. "+result);
						}
						
						ArrayList<String> result1 = bp.verifyRecordOnInteractionCard(taskDueDate1,IconType.Call,taskSubject1, taskNotes1, true, false,null,null);
						if (result1.isEmpty()) {
							log(LogStatus.PASS,taskSubject1 + " record has been verified on intraction",YesNo.No);
							sa.assertTrue(true,taskSubject1 + " record has been verified on intraction");
						} else {
							log(LogStatus.ERROR,taskSubject1 + " record is not verified on intraction. "+result1,YesNo.No);
							sa.assertTrue(false,taskSubject1 + " record is not verified on intraction. "+result1);
						}
						
						ArrayList<String> result2=bp.verifySubjectLinkPopUpOnIntraction(driver, taskSubject, basicsection, advanceSection, IconType.Task, PageName.AcuityDetails);
						
						if(result2.isEmpty())
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject+" has been verified",YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject+" has been verified");							
						}
						else
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject+" are not verified. "+result2,YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject+" are not verified. "+result2);
							
						}
						
                       ArrayList<String> result3=bp.verifySubjectLinkPopUpOnIntraction(driver, taskSubject1, basicsection1, advanceSection1, IconType.Call, PageName.AcuityDetails);
						
						if(result3.isEmpty())
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject1+" has been verified",YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject1+" has been verified");							
						}
						else
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject1+" are not verified. "+result3,YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject1+" are not verified. "+result3);
							
						}
						

						ArrayList<String> result4=bp.verifyRecordOnContactSectionAcuity(contactSectionName, contactSectionTitle, contactSectionDeal, contactSectionMeetingAndCalls, contactSectionEmail);
						if(result4.isEmpty())
						{
							log(LogStatus.INFO, contactSectionName+" record on Contact section has been verified for "+recordName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, contactSectionName+" record on Contact section is not verified for "+recordName+". "+result4, YesNo.No);
							sa.assertTrue(false, contactSectionName+" record on Contact section is not verified for "+recordName+". "+result4);
						}
						
						ArrayList<String> result5=bp.verifyRecordAndReferencedTypeOnTagged(firmsTaggedName, firmsTaggedTimeReference, peopleTaggedName, peopleTaggedTimeReference, null, null,true, fundTaggedName,fundTaggedTimeReference);
						if(result5.isEmpty())
						{
							log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR,  "The record name and Time reference are not verifed. "+result5, YesNo.No);
							sa.assertTrue(false,  "The record name and Time reference are not verifed."+result5);
						}
						
						
						if(click(driver, bp.getConnectionIconOfContact(contactName, 20), "Connection icon of "+contactName, action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, "clicked on Connection icon of "+contactName, YesNo.No);

							ArrayList<String> result6=bp.verifyUIOfConnectionPopup(contactName, headerName, message,exteralHeaderName,message);
							if(result6.isEmpty())
							{
								log(LogStatus.INFO, "The UI of Connections popup have been verified", YesNo.No);
								sa.assertTrue(true,  "The UI of Connections popup have been verified");
							}
							else
							{
								log(LogStatus.ERROR, "The UI of Connections popup are not verified. "+result6, YesNo.No);
								sa.assertTrue(false,  "The UI of Connections popup are not verified. "+result6);

							}
						}
						else
						{
							log(LogStatus.ERROR, "Not able to Connection icon of "+contactName, YesNo.No);
							sa.assertTrue(false,  "Not able to Connection icon of "+contactName);
						}
						
						
						if(click(driver, bp.getMeetingAndCallCount(contactName, 20), "Meeting and call count", action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, "Clicked on the count of meeting and call of "+contactName+" record on contact section",YesNo.No);
							
							ArrayList<String> result7=bp.verifyUIOfMeetingAndCallsPopup( headerNameMeetingAndCall, message);
							if(result7.isEmpty())
							{
								log(LogStatus.INFO, "The UI of Meeting and call page have been verified", YesNo.No);
								sa.assertTrue(true,  "The UI of Meeting and call page have been verified");
							}
							else
							{
								log(LogStatus.ERROR, "The UI of Meeting and call page are not verified. "+result7, YesNo.No);
								sa.assertTrue(false,  "The UI of Meeting and call page are not verified. "+result7);

							}					
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+contactName+" record on contact section",	YesNo.No);		
							sa.assertTrue(false,  "Not able to click on the count of meeting and call of "+contactName+" record on contact section");
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
	public void ATETc028_VerifyTaskCallFunctionalityOnPortfolioCompanyRecordType(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATCERecord11;
		String taskSubject=ATCE_ATSubject1;
		String taskRelatedTo=ATCE_ATRelatedTo1;
		String taskNotes=ATCE_ATNote1;
		String taskStatus=ATCE_AdvanceStatus1;
		String taskPriority=ATCE_AdvancePriority1;	
		String taskDueDate =ATCE_ATDueDate1;
		
		String taskSubject1=ATCE_ATSubject2;
		String taskRelatedTo1=ATCE_ATRelatedTo2;
		String taskNotes1=ATCE_ATNote2;
		
		String taskDueDate1 =ATCE_ATDueDate2;
		
		String contactSectionName=ATCE_ConName6;
		String contactSectionTitle=ATCE_ConTitle6;
		String contactSectionDeal=ATCE_ConDeal6;
		String contactSectionMeetingAndCalls=ATCE_ConMeetingAndCall6;
		String contactSectionEmail=ATCE_ConEmail6;

		String[] firmsTaggedName= {ATCE_TaggedFirmsName1,ATCE_TaggedFirmsName2,ATCE_TaggedFirmsName3,ATCE_TaggedFirmsName4,ATCE_TaggedFirmsName5,ATCE_TaggedFirmsName6,ATCE_TaggedFirmsName7,ATCE_TaggedFirmsName9,ATCE_TaggedFirmsName10};
		String[] firmsTaggedTimeReference= {ATCE_TaggedFirmsCount1,ATCE_TaggedFirmsCount2,ATCE_TaggedFirmsCount3,ATCE_TaggedFirmsCount4,ATCE_TaggedFirmsCount5,ATCE_TaggedFirmsCount6,ATCE_TaggedFirmsCount7,ATCE_TaggedFirmsCount9,ATCE_TaggedFirmsCount10};

		String[] peopleTaggedName= {ATCE_TaggedPeopleName1,ATCE_TaggedPeopleName2,ATCE_TaggedPeopleName3,ATCE_TaggedPeopleName6,ATCE_TaggedPeopleName7};
		String[] peopleTaggedTimeReference= {ATCE_TaggedPeopleCount1,ATCE_TaggedPeopleCount2,ATCE_TaggedPeopleCount3,ATCE_TaggedPeopleCount6,ATCE_TaggedPeopleCount7};
				
		String[] dealTaggedName= {ATCE_TaggedDealName1};
		String[] dealTaggedTimeReference= {ATCE_TaggedDealCount1};
		
		String[][] basicsection = { { "Subject", taskSubject }, { "Notes", taskNotes }, { "Related_To", taskRelatedTo } };
		String[][] advanceSection = { { "Due Date", taskDueDate }, {"Status", taskStatus}, {"Priority", taskPriority} };
		
		String[][] basicsection1 = { { "Subject", taskSubject1 }, { "Notes", taskNotes1 }, { "Related_To", taskRelatedTo1 } };
		String[][] advanceSection1 = { { "Date", taskDueDate1 }};
		
		String connectionUserName=crmUser6FirstName+" "+crmUser6LastName;
		String connectionTitle=ATCE_ConnectionTitle1;
		String connectionDeal=ATCE_ConnectionDeal1;
		String connectionMeetingaAndCall=ATCE_ConnectionMeetingAndCall1;
		String connectionEmail=ATCE_ConnectionEmail1;
		
		String userName1=crmUser6FirstName+" "+crmUser6LastName;
		String userName2=crmUser7FirstName+" "+crmUser7LastName;
		String userName3=crmUser8FirstName+" "+crmUser8LastName;
		
		String[] participantArr=ATCE_ATParticipants1.split("<break>");
		String[] participants=new String[participantArr.length];
		
		for(int i=0; i<participantArr.length; i++)
		{
			if(participantArr[i].trim().equalsIgnoreCase("PE User 1"))
			{
				participants[i]=userName1;
			}
			else if(participantArr[i].trim().equalsIgnoreCase("PE User 2"))
			{
				participants[i]=userName2;
			}
			else if(participantArr[i].trim().equalsIgnoreCase("PE User 3"))
			{
				participants[i]=userName3;
			}
			else
			{
				participants[i]=participantArr[i];
			}
		}
		
		
		String[] tagsArr=ATCE_ATTags1.split("<break>");
		
		
		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

			if (lp.clickOnTab(projectName, tabObj1)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

				if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
						recordName, 30)) {
					log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

					if (bp.clicktabOnPage(TabName.Acuity.toString())) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						ArrayList<String> result = bp.verifyRecordOnInteractionCard(taskDueDate,IconType.Task,taskSubject, taskNotes, true, false,null,null);
						if (result.isEmpty()) {
							log(LogStatus.PASS,taskSubject + " record has been verified on intraction",YesNo.No);
							sa.assertTrue(true,taskSubject + " record has been verified on intraction");
						} else {
							log(LogStatus.ERROR,taskSubject + " record is not verified on intraction. "+result,YesNo.No);
							sa.assertTrue(false,taskSubject + " record is not verified on intraction. "+result);
						}
						
						ArrayList<String> result1 = bp.verifyRecordOnInteractionCard(taskDueDate1,IconType.Call,taskSubject1, taskNotes1, true, false,null,null);
						if (result1.isEmpty()) {
							log(LogStatus.PASS,taskSubject1 + " record has been verified on intraction",YesNo.No);
							sa.assertTrue(true,taskSubject1 + " record has been verified on intraction");
						} else {
							log(LogStatus.ERROR,taskSubject1 + " record is not verified on intraction. "+result1,YesNo.No);
							sa.assertTrue(false,taskSubject1 + " record is not verified on intraction. "+result1);
						}
						
						ArrayList<String> result2=bp.verifySubjectLinkPopUpOnIntraction(driver, taskSubject, basicsection, advanceSection, IconType.Task, PageName.AcuityDetails);
						
						if(result2.isEmpty())
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject+" has been verified",YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject+" has been verified");							
						}
						else
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject+" are not verified. "+result2,YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject+" are not verified. "+result2);
							
						}
						
                       ArrayList<String> result3=bp.verifySubjectLinkPopUpOnIntraction(driver, taskSubject1, basicsection1, advanceSection1, IconType.Call, PageName.AcuityDetails);
						
						if(result3.isEmpty())
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject1+" has been verified",YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject1+" has been verified");							
						}
						else
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject1+" are not verified. "+result3,YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject1+" are not verified. "+result3);
							
						}
						

						ArrayList<String> result4=bp.verifyRecordOnContactSectionAcuity(contactSectionName, contactSectionTitle, contactSectionDeal, contactSectionMeetingAndCalls, contactSectionEmail);
						if(result4.isEmpty())
						{
							log(LogStatus.INFO, contactSectionName+" record on Contact section has been verified for "+recordName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, contactSectionName+" record on Contact section is not verified for "+recordName+". "+result4, YesNo.No);
							sa.assertTrue(false, contactSectionName+" record on Contact section is not verified for "+recordName+". "+result4);
						}
						
						ArrayList<String> result5=bp.verifyRecordAndReferencedTypeOnTagged(firmsTaggedName, firmsTaggedTimeReference, peopleTaggedName, peopleTaggedTimeReference, dealTaggedName, dealTaggedTimeReference,isInstitutionRecord, null,null);
						if(result5.isEmpty())
						{
							log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR,  "The record name and Time reference are not verifed. "+result5, YesNo.No);
							sa.assertTrue(false,  "The record name and Time reference are not verifed."+result5);
						}
						
						if(click(driver, bp.getMeetingAndCallCount(contactSectionName, 20), "Meeting and call count", action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, "Clicked on the count of meeting and call of "+contactSectionName+" record on contact section",YesNo.No);
							
							ArrayList<String> result6=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity("call", taskDueDate1, taskSubject1, taskNotes1, participants, tagsArr);
							if(result6.isEmpty())
							{
								log(LogStatus.INFO, "The records on meeting & calls popup have been verified for "+recordName, YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "The records on meeting & calls popup are not verified for "+recordName+". "+result6, YesNo.No);
								sa.assertTrue(false, "The records on meeting & calls popup are not verified for "+recordName+". "+result6);
							}
						
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+contactSectionName+" record on contact section",	YesNo.No);		
							sa.assertTrue(false,  "Not able to click on the count of meeting and call of "+contactSectionName+" record on contact section");
						}
						
						
						ArrayList<String> result7=bp.verifyRecordOnConnectionsPopUpOfContactInAcuity(contactSectionName, connectionUserName, connectionTitle, connectionDeal, connectionMeetingaAndCall, connectionEmail);
						if(result7.isEmpty())
						{
							log(LogStatus.INFO, "The records on Connection popup have been verified for "+contactSectionName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records on Connection popup are not verified for "+contactSectionName+". "+result7 , YesNo.No);
							sa.assertTrue(false, "The records on Connection popup are not verified for "+contactSectionName+". "+result7);
						}
						
						if (CommonLib.clickUsingJavaScript(driver, bp.contactNameUserIconButton(contactSectionName, 30), "Contact Name: " + contactSectionName,
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on connection icon of contact : " + contactSectionName, YesNo.No);
							String parentID=switchOnWindow(driver);
							if(CommonLib.clickUsingJavaScript(driver, bp.getMeetingAndCallCount(userName1, 20),"Count of "+userName1+" on contact section" , action.SCROLLANDBOOLEAN))
							{
								log(LogStatus.INFO, "clicked on count of "+userName1,YesNo.No);
								ArrayList<String> result6=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity("call", taskDueDate1, taskSubject1, taskNotes1, participants, tagsArr);
								if(result6.isEmpty())
								{
									log(LogStatus.INFO, "The records on meeting & calls popup have been verified for "+recordName, YesNo.No);
								}
								else
								{
									log(LogStatus.ERROR, "The records on meeting & calls popup are not verified for "+recordName+". "+result6, YesNo.No);
									sa.assertTrue(false, "The records on meeting & calls popup are not verified for "+recordName+". "+result6);
								}
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on count of "+contactSectionName,YesNo.No);
								sa.assertTrue(false,  "Not able to click on count of "+contactSectionName);
							}		
							driver.close();
							driver.switchTo().window(parentID);
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on connection icon of contact : " + contactSectionName, YesNo.No);
							sa.assertTrue(false, "Not able to click on connection icon of contact : " + contactSectionName);
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
	public void ATETc029_VerifyTaskCallFunctionalityOnPrivateEquityRecordType(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATCERecord13;
		String taskSubject=ATCE_ATSubject1;
		String taskRelatedTo=ATCE_ATRelatedTo1;
		String taskNotes=ATCE_ATNote1;
		String taskStatus=ATCE_AdvanceStatus1;
		String taskPriority=ATCE_AdvancePriority1;	
		String taskDueDate =ATCE_ATDueDate1;
		
		String taskSubject1=ATCE_ATSubject2;
		String taskRelatedTo1=ATCE_ATRelatedTo2;
		String taskNotes1=ATCE_ATNote2;
		
		String taskDueDate1 =ATCE_ATDueDate2;
		
		String contactSectionName=ATCE_ConName7;
		String contactSectionTitle=ATCE_ConTitle7;
		String contactSectionDeal=ATCE_ConDeal7;
		String contactSectionMeetingAndCalls=ATCE_ConMeetingAndCall7;
		String contactSectionEmail=ATCE_ConEmail7;

		String[] firmsTaggedName= {ATCE_TaggedFirmsName1,ATCE_TaggedFirmsName2,ATCE_TaggedFirmsName3,ATCE_TaggedFirmsName4,ATCE_TaggedFirmsName5,ATCE_TaggedFirmsName6,ATCE_TaggedFirmsName7,ATCE_TaggedFirmsName8,ATCE_TaggedFirmsName10};
		String[] firmsTaggedTimeReference= {ATCE_TaggedFirmsCount1,ATCE_TaggedFirmsCount2,ATCE_TaggedFirmsCount3,ATCE_TaggedFirmsCount4,ATCE_TaggedFirmsCount5,ATCE_TaggedFirmsCount6,ATCE_TaggedFirmsCount7,ATCE_TaggedFirmsCount8,ATCE_TaggedFirmsCount10};

		String[] peopleTaggedName= {ATCE_TaggedPeopleName1,ATCE_TaggedPeopleName2,ATCE_TaggedPeopleName3,ATCE_TaggedPeopleName4,ATCE_TaggedPeopleName5,ATCE_TaggedPeopleName7};
		String[] peopleTaggedTimeReference= {ATCE_TaggedPeopleCount1,ATCE_TaggedPeopleCount2,ATCE_TaggedPeopleCount3,ATCE_TaggedPeopleCount4,ATCE_TaggedPeopleCount5,ATCE_TaggedPeopleCount7};
				
		String[] dealTaggedName= {ATCE_TaggedDealName1};
		String[] dealTaggedTimeReference= {ATCE_TaggedDealCount1};
		
		String[][] basicsection = { { "Subject", taskSubject }, { "Notes", taskNotes }, { "Related_To", taskRelatedTo } };
		String[][] advanceSection = { { "Due Date", taskDueDate }, {"Status", taskStatus}, {"Priority", taskPriority} };
		
		String[][] basicsection1 = { { "Subject", taskSubject1 }, { "Notes", taskNotes1 }, { "Related_To", taskRelatedTo1 } };
		String[][] advanceSection1 = { { "Date", taskDueDate1 }};
		
		String connectionUserName=crmUser6FirstName+" "+crmUser6LastName;
		String connectionTitle=ATCE_ConnectionTitle1;
		String connectionDeal=ATCE_ConnectionDeal1;
		String connectionMeetingaAndCall=ATCE_ConnectionMeetingAndCall1;
		String connectionEmail=ATCE_ConnectionEmail1;
		
		String userName1=crmUser6FirstName+" "+crmUser6LastName;
		String userName2=crmUser7FirstName+" "+crmUser7LastName;
		String userName3=crmUser8FirstName+" "+crmUser8LastName;
		
		String[] participantArr=ATCE_ATParticipants1.split("<break>");
		String[] participants=new String[participantArr.length];
		
		for(int i=0; i<participantArr.length; i++)
		{
			if(participantArr[i].trim().equalsIgnoreCase("PE User 1"))
			{
				participants[i]=userName1;
			}
			else if(participantArr[i].trim().equalsIgnoreCase("PE User 2"))
			{
				participants[i]=userName2;
			}
			else if(participantArr[i].trim().equalsIgnoreCase("PE User 3"))
			{
				participants[i]=userName3;
			}
			else
			{
				participants[i]=participantArr[i];
			}
		}
		
		
		String[] tagsArr=ATCE_ATTags1.split("<break>");
		
		
		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

			if (lp.clickOnTab(projectName, tabObj1)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

				if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
						recordName, 30)) {
					log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

					if (bp.clicktabOnPage(TabName.Acuity.toString())) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						ArrayList<String> result = bp.verifyRecordOnInteractionCard(taskDueDate,IconType.Task,taskSubject, taskNotes, true, false,null,null);
						if (result.isEmpty()) {
							log(LogStatus.PASS,taskSubject + " record has been verified on intraction",YesNo.No);
							sa.assertTrue(true,taskSubject + " record has been verified on intraction");
						} else {
							log(LogStatus.ERROR,taskSubject + " record is not verified on intraction. "+result,YesNo.No);
							sa.assertTrue(false,taskSubject + " record is not verified on intraction. "+result);
						}
						
						ArrayList<String> result1 = bp.verifyRecordOnInteractionCard(taskDueDate1,IconType.Call,taskSubject1, taskNotes1, true, false,null,null);
						if (result1.isEmpty()) {
							log(LogStatus.PASS,taskSubject1 + " record has been verified on intraction",YesNo.No);
							sa.assertTrue(true,taskSubject1 + " record has been verified on intraction");
						} else {
							log(LogStatus.ERROR,taskSubject1 + " record is not verified on intraction. "+result1,YesNo.No);
							sa.assertTrue(false,taskSubject1 + " record is not verified on intraction. "+result1);
						}
						
						ArrayList<String> result2=bp.verifySubjectLinkPopUpOnIntraction(driver, taskSubject, basicsection, advanceSection, IconType.Task, PageName.AcuityDetails);
						
						if(result2.isEmpty())
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject+" has been verified",YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject+" has been verified");							
						}
						else
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject+" are not verified. "+result2,YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject+" are not verified. "+result2);
							
						}
						
                       ArrayList<String> result3=bp.verifySubjectLinkPopUpOnIntraction(driver, taskSubject1, basicsection1, advanceSection1, IconType.Call, PageName.AcuityDetails);
						
						if(result3.isEmpty())
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject1+" has been verified",YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject1+" has been verified");							
						}
						else
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject1+" are not verified. "+result3,YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject1+" are not verified. "+result3);
							
						}
						

						ArrayList<String> result4=bp.verifyRecordOnContactSectionAcuity(contactSectionName, contactSectionTitle, contactSectionDeal, contactSectionMeetingAndCalls, contactSectionEmail);
						if(result4.isEmpty())
						{
							log(LogStatus.INFO, contactSectionName+" record on Contact section has been verified for "+recordName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, contactSectionName+" record on Contact section is not verified for "+recordName+". "+result4, YesNo.No);
							sa.assertTrue(false, contactSectionName+" record on Contact section is not verified for "+recordName+". "+result4);
						}
						
						ArrayList<String> result5=bp.verifyRecordAndReferencedTypeOnTagged(firmsTaggedName, firmsTaggedTimeReference, peopleTaggedName, peopleTaggedTimeReference, dealTaggedName, dealTaggedTimeReference,isInstitutionRecord, null,null);
						if(result5.isEmpty())
						{
							log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR,  "The record name and Time reference are not verifed. "+result5, YesNo.No);
							sa.assertTrue(false,  "The record name and Time reference are not verifed."+result5);
						}
						
						if(click(driver, bp.getMeetingAndCallCount(contactSectionName, 20), "Meeting and call count", action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, "Clicked on the count of meeting and call of "+contactSectionName+" record on contact section",YesNo.No);
							
							ArrayList<String> result6=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity("call", taskDueDate1, taskSubject1, taskNotes1, participants, tagsArr);
							if(result6.isEmpty())
							{
								log(LogStatus.INFO, "The records on meeting & calls popup have been verified for "+recordName, YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "The records on meeting & calls popup are not verified for "+recordName+". "+result6, YesNo.No);
								sa.assertTrue(false, "The records on meeting & calls popup are not verified for "+recordName+". "+result6);
							}
						
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+contactSectionName+" record on contact section",	YesNo.No);		
							sa.assertTrue(false,  "Not able to click on the count of meeting and call of "+contactSectionName+" record on contact section");
						}
						
						
						ArrayList<String> result7=bp.verifyRecordOnConnectionsPopUpOfContactInAcuity(contactSectionName, connectionUserName, connectionTitle, connectionDeal, connectionMeetingaAndCall, connectionEmail);
						if(result7.isEmpty())
						{
							log(LogStatus.INFO, "The records on Connection popup have been verified for "+contactSectionName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records on Connection popup are not verified for "+contactSectionName+". "+result7 , YesNo.No);
							sa.assertTrue(false, "The records on Connection popup are not verified for "+contactSectionName+". "+result7);
						}
						
						if (CommonLib.clickUsingJavaScript(driver, bp.contactNameUserIconButton(contactSectionName, 30), "Contact Name: " + contactSectionName,
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on connection icon of contact : " + contactSectionName, YesNo.No);
							String parentID=switchOnWindow(driver);
							if(CommonLib.clickUsingJavaScript(driver, bp.getMeetingAndCallCount(userName1, 20),"Count of "+userName1+" on contact section" , action.SCROLLANDBOOLEAN))
							{
								log(LogStatus.INFO, "clicked on count of "+userName1,YesNo.No);
								ArrayList<String> result6=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity("call", taskDueDate1, taskSubject1, taskNotes1, participants, tagsArr);
								if(result6.isEmpty())
								{
									log(LogStatus.INFO, "The records on meeting & calls popup have been verified for "+recordName, YesNo.No);
								}
								else
								{
									log(LogStatus.ERROR, "The records on meeting & calls popup are not verified for "+recordName+". "+result6, YesNo.No);
									sa.assertTrue(false, "The records on meeting & calls popup are not verified for "+recordName+". "+result6);
								}
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on count of "+contactSectionName,YesNo.No);
								sa.assertTrue(false,  "Not able to click on count of "+contactSectionName);
							}		
							driver.close();
							driver.switchTo().window(parentID);
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on connection icon of contact : " + contactSectionName, YesNo.No);
							sa.assertTrue(false, "Not able to click on connection icon of contact : " + contactSectionName);
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
	
	public void ATETc030_VerifyTaskCallFunctionalityOnContactRecordType(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName=ATCERecord13;
		String taskSubject=ATCE_ATSubject1;
		String taskRelatedTo=ATCE_ATRelatedTo1;
		String taskNotes=ATCE_ATNote1;
		String taskStatus=ATCE_AdvanceStatus1;
		String taskPriority=ATCE_AdvancePriority1;	
		String taskDueDate =ATCE_ATDueDate1;
		
		String taskSubject1=ATCE_ATSubject2;
		String taskRelatedTo1=ATCE_ATRelatedTo2;
		String taskNotes1=ATCE_ATNote2;
		
		String taskDueDate1 =ATCE_ATDueDate2;
		
		String contactSectionName=ATCE_ConName7;
		String contactSectionTitle=ATCE_ConTitle7;
		String contactSectionDeal=ATCE_ConDeal7;
		String contactSectionMeetingAndCalls=ATCE_ConMeetingAndCall7;
		String contactSectionEmail=ATCE_ConEmail7;

		String[] firmsTaggedName= {ATCE_TaggedFirmsName1,ATCE_TaggedFirmsName2,ATCE_TaggedFirmsName3,ATCE_TaggedFirmsName4,ATCE_TaggedFirmsName5,ATCE_TaggedFirmsName6,ATCE_TaggedFirmsName7,ATCE_TaggedFirmsName8,ATCE_TaggedFirmsName10};
		String[] firmsTaggedTimeReference= {ATCE_TaggedFirmsCount1,ATCE_TaggedFirmsCount2,ATCE_TaggedFirmsCount3,ATCE_TaggedFirmsCount4,ATCE_TaggedFirmsCount5,ATCE_TaggedFirmsCount6,ATCE_TaggedFirmsCount7,ATCE_TaggedFirmsCount8,ATCE_TaggedFirmsCount10};

		String[] peopleTaggedName= {ATCE_TaggedPeopleName1,ATCE_TaggedPeopleName2,ATCE_TaggedPeopleName3,ATCE_TaggedPeopleName4,ATCE_TaggedPeopleName5,ATCE_TaggedPeopleName7};
		String[] peopleTaggedTimeReference= {ATCE_TaggedPeopleCount1,ATCE_TaggedPeopleCount2,ATCE_TaggedPeopleCount3,ATCE_TaggedPeopleCount4,ATCE_TaggedPeopleCount5,ATCE_TaggedPeopleCount7};
				
		String[] dealTaggedName= {ATCE_TaggedDealName1};
		String[] dealTaggedTimeReference= {ATCE_TaggedDealCount1};
		
		String[][] basicsection = { { "Subject", taskSubject }, { "Notes", taskNotes }, { "Related_To", taskRelatedTo } };
		String[][] advanceSection = { { "Due Date", taskDueDate }, {"Status", taskStatus}, {"Priority", taskPriority} };
		
		String[][] basicsection1 = { { "Subject", taskSubject1 }, { "Notes", taskNotes1 }, { "Related_To", taskRelatedTo1 } };
		String[][] advanceSection1 = { { "Date", taskDueDate1 }};
		
		String connectionUserName=crmUser6FirstName+" "+crmUser6LastName;
		String connectionTitle=ATCE_ConnectionTitle1;
		String connectionDeal=ATCE_ConnectionDeal1;
		String connectionMeetingaAndCall=ATCE_ConnectionMeetingAndCall1;
		String connectionEmail=ATCE_ConnectionEmail1;
		
		String userName1=crmUser6FirstName+" "+crmUser6LastName;
		String userName2=crmUser7FirstName+" "+crmUser7LastName;
		String userName3=crmUser8FirstName+" "+crmUser8LastName;
		
		String[] participantArr=ATCE_ATParticipants1.split("<break>");
		String[] participants=new String[participantArr.length];
		
		for(int i=0; i<participantArr.length; i++)
		{
			if(participantArr[i].trim().equalsIgnoreCase("PE User 1"))
			{
				participants[i]=userName1;
			}
			else if(participantArr[i].trim().equalsIgnoreCase("PE User 2"))
			{
				participants[i]=userName2;
			}
			else if(participantArr[i].trim().equalsIgnoreCase("PE User 3"))
			{
				participants[i]=userName3;
			}
			else
			{
				participants[i]=participantArr[i];
			}
		}
		
		
		String[] tagsArr=ATCE_ATTags1.split("<break>");
		
		
		lp.CRMLogin(crmUser6EmailID, adminPassword, appName);

			if (lp.clickOnTab(projectName, tabObj1)) {

				log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

				if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,
						recordName, 30)) {
					log(LogStatus.INFO, recordName + " reocrd has been open", YesNo.No);

					if (bp.clicktabOnPage(TabName.Acuity.toString())) {
						log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);

						ArrayList<String> result = bp.verifyRecordOnInteractionCard(taskDueDate,IconType.Task,taskSubject, taskNotes, true, false,null,null);
						if (result.isEmpty()) {
							log(LogStatus.PASS,taskSubject + " record has been verified on intraction",YesNo.No);
							sa.assertTrue(true,taskSubject + " record has been verified on intraction");
						} else {
							log(LogStatus.ERROR,taskSubject + " record is not verified on intraction. "+result,YesNo.No);
							sa.assertTrue(false,taskSubject + " record is not verified on intraction. "+result);
						}
						
						ArrayList<String> result1 = bp.verifyRecordOnInteractionCard(taskDueDate1,IconType.Call,taskSubject1, taskNotes1, true, false,null,null);
						if (result1.isEmpty()) {
							log(LogStatus.PASS,taskSubject1 + " record has been verified on intraction",YesNo.No);
							sa.assertTrue(true,taskSubject1 + " record has been verified on intraction");
						} else {
							log(LogStatus.ERROR,taskSubject1 + " record is not verified on intraction. "+result1,YesNo.No);
							sa.assertTrue(false,taskSubject1 + " record is not verified on intraction. "+result1);
						}
						
						ArrayList<String> result2=bp.verifySubjectLinkPopUpOnIntraction(driver, taskSubject, basicsection, advanceSection, IconType.Task, PageName.AcuityDetails);
						
						if(result2.isEmpty())
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject+" has been verified",YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject+" has been verified");							
						}
						else
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject+" are not verified. "+result2,YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject+" are not verified. "+result2);
							
						}
						
                       ArrayList<String> result3=bp.verifySubjectLinkPopUpOnIntraction(driver, taskSubject1, basicsection1, advanceSection1, IconType.Call, PageName.AcuityDetails);
						
						if(result3.isEmpty())
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject1+" has been verified",YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject1+" has been verified");							
						}
						else
						{
							log(LogStatus.PASS,"The details on popup of subject "+taskSubject1+" are not verified. "+result3,YesNo.No);
							sa.assertTrue(true,"The details on popup of subject "+taskSubject1+" are not verified. "+result3);
							
						}
						

						ArrayList<String> result4=bp.verifyRecordOnContactSectionAcuity(contactSectionName, contactSectionTitle, contactSectionDeal, contactSectionMeetingAndCalls, contactSectionEmail);
						if(result4.isEmpty())
						{
							log(LogStatus.INFO, contactSectionName+" record on Contact section has been verified for "+recordName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, contactSectionName+" record on Contact section is not verified for "+recordName+". "+result4, YesNo.No);
							sa.assertTrue(false, contactSectionName+" record on Contact section is not verified for "+recordName+". "+result4);
						}
						
						ArrayList<String> result5=bp.verifyRecordAndReferencedTypeOnTagged(firmsTaggedName, firmsTaggedTimeReference, peopleTaggedName, peopleTaggedTimeReference, dealTaggedName, dealTaggedTimeReference,isInstitutionRecord, null,null);
						if(result5.isEmpty())
						{
							log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR,  "The record name and Time reference are not verifed. "+result5, YesNo.No);
							sa.assertTrue(false,  "The record name and Time reference are not verifed."+result5);
						}
						
						if(click(driver, bp.getMeetingAndCallCount(contactSectionName, 20), "Meeting and call count", action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, "Clicked on the count of meeting and call of "+contactSectionName+" record on contact section",YesNo.No);
							
							ArrayList<String> result6=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity("call", taskDueDate1, taskSubject1, taskNotes1, participants, tagsArr);
							if(result6.isEmpty())
							{
								log(LogStatus.INFO, "The records on meeting & calls popup have been verified for "+recordName, YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "The records on meeting & calls popup are not verified for "+recordName+". "+result6, YesNo.No);
								sa.assertTrue(false, "The records on meeting & calls popup are not verified for "+recordName+". "+result6);
							}
						
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on the count of meeting and call of "+contactSectionName+" record on contact section",	YesNo.No);		
							sa.assertTrue(false,  "Not able to click on the count of meeting and call of "+contactSectionName+" record on contact section");
						}
						
						
						ArrayList<String> result7=bp.verifyRecordOnConnectionsPopUpOfContactInAcuity(contactSectionName, connectionUserName, connectionTitle, connectionDeal, connectionMeetingaAndCall, connectionEmail);
						if(result7.isEmpty())
						{
							log(LogStatus.INFO, "The records on Connection popup have been verified for "+contactSectionName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records on Connection popup are not verified for "+contactSectionName+". "+result7 , YesNo.No);
							sa.assertTrue(false, "The records on Connection popup are not verified for "+contactSectionName+". "+result7);
						}
						
						if (CommonLib.clickUsingJavaScript(driver, bp.contactNameUserIconButton(contactSectionName, 30), "Contact Name: " + contactSectionName,
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on connection icon of contact : " + contactSectionName, YesNo.No);
							String parentID=switchOnWindow(driver);
							if(CommonLib.clickUsingJavaScript(driver, bp.getMeetingAndCallCount(userName1, 20),"Count of "+userName1+" on contact section" , action.SCROLLANDBOOLEAN))
							{
								log(LogStatus.INFO, "clicked on count of "+userName1,YesNo.No);
								ArrayList<String> result6=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity("call", taskDueDate1, taskSubject1, taskNotes1, participants, tagsArr);
								if(result6.isEmpty())
								{
									log(LogStatus.INFO, "The records on meeting & calls popup have been verified for "+recordName, YesNo.No);
								}
								else
								{
									log(LogStatus.ERROR, "The records on meeting & calls popup are not verified for "+recordName+". "+result6, YesNo.No);
									sa.assertTrue(false, "The records on meeting & calls popup are not verified for "+recordName+". "+result6);
								}
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on count of "+contactSectionName,YesNo.No);
								sa.assertTrue(false,  "Not able to click on count of "+contactSectionName);
							}		
							driver.close();
							driver.switchTo().window(parentID);
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on connection icon of contact : " + contactSectionName, YesNo.No);
							sa.assertTrue(false, "Not able to click on connection icon of contact : " + contactSectionName);
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
	
}


