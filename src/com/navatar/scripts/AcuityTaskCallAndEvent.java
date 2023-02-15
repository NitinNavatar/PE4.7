package com.navatar.scripts;

import static com.navatar.generic.CommonLib.ThreadSleep;
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

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.pageObjects.BasePageBusinessLayer;
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
	
}


