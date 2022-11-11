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

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
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

		for(int k=5; k<11; k++)
		{
			lp = new LoginPageBusinessLayer(driver);
			home = new HomePageBusineesLayer(driver);
			setup = new SetupPageBusinessLayer(driver);
			String[] firstName= {crmUser6FirstName,crmUser7FirstName,crmUser8FirstName,crmUser9FirstName,crmUser10FirstName,crmUser11FirstName,crmUser12FirstName,crmUser13FirstName,crmUser14FirstName,crmUser15FirstName,crmUser16FirstName};
			String[] lastName= {crmUser6LastName,crmUser7LastName,crmUser8LastName,crmUser9LastName,crmUser10LastName,crmUser11LastName,crmUser12LastName,crmUser13LastName,crmUser14LastName,crmUser15LastName,crmUser16LastName};
			String[] userLicence= {crmUser6Lience,crmUser7Lience,crmUser8Lience,crmUser9Lience,crmUser10Lience,crmUser11Lience,crmUser12Lience,crmUser13Lience,crmUser14Lience,crmUser15Lience,crmUser16Lience};
			String[] userProfile= {crmUser6Profile,crmUser7Profile,crmUser8Profile,crmUser9Profile,crmUser10Profile,crmUser11Profile,crmUser12Profile,crmUser13Profile,crmUser14Profile,crmUser15Profile,crmUser16Profile};

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
						if (setup.createPEUser(firstName[k], UserLastName, emailId, userLicence[k], userProfile[k])) {
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
			ThreadSleep(10000);

		}
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ATETc002_VerifyTheIntermediaryAccountAcuityTabWhenThereIsNoAccountConnectedWithDeal(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName="Nav Account 1";
		String sectionHeader="Tagged<break>Interactions<break>Contacts<break>Deals";
		String tabsOnTagged="Companies<break>People<break>Deals";
		String defaultTabOntagged="Companies";
		String message="No items to display";

		String contactHeader="Name<break>Title<break>Deals<break>Meetings and Calls<break>Emails";
		String dealHeader="Deal Name<break>Stage<break>Date Received";



		String[] arrSectionHeader=sectionHeader.split("<break>");		
		List<String> sectionHeaderName = new ArrayList<String>(Arrays.asList(arrSectionHeader));


		String[] arrTabName= tabsOnTagged.split("<break>");		
		List<String> tabNameOnTagged = new ArrayList<String>(Arrays.asList(arrTabName));

		String[] arrContactHeader=contactHeader.split("<break>");
		List<String> contactHeaders = new ArrayList<String>(Arrays.asList(arrContactHeader));

		String[] arrDealHeader=dealHeader.split("<break>");
		List<String> dealHeaders = new ArrayList<String>(Arrays.asList(arrDealHeader));

		List<String> connectionHeaders=new ArrayList<String>();
		List<String> connectionTooltips=new ArrayList<String>();


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
							log(LogStatus.ERROR, "The Column name, Time referenced and message are not verified ", YesNo.No);
							sa.assertTrue(false, "The Column name, Time referenced and message are not verified ");
						}

						ArrayList<String> result1=bp.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(message, contactHeaders, message, dealHeaders, message,connectionHeaders,null);

						if(result1.isEmpty())
						{
							log(LogStatus.INFO, "The header name and message have been verified on Interaction, Contacts and Deals Section ", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The header name and message are not verified on Interaction, Contacts and Deals Section ", YesNo.No);
							sa.assertTrue(false, "The header name and message are not verified on Interaction, Contacts and Deals Section ");
						}

						ArrayList<String> result2=bp.verifyToolTipOnDealsConnctionsAndContactsHeader(dealHeaders, contactHeaders, connectionTooltips);
						if(result2.isEmpty())
						{
							log(LogStatus.INFO, "The Tooltip on Deal and Contacts header have been verified", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The Tooltip on Deal and Contacts header are not verified", YesNo.No);
							sa.assertTrue(false, "The Tooltip on Deal and Contacts header are not verified");
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
		String recordName="Test Contact 1";
		String sectionHeader="Tagged<break>Interactions<break>Connections<break>Deals";
		String tabsOnTagged="Companies<break>People<break>Deals";
		String defaultTabOntagged="Companies";
		String message="No items to display";

		String connectionHeader="Team Member<break>Title<break>Deals<break>Meetings and Calls<break>Emails";

		String dealHeader="Deal Name<break>Company<break>Stage<break>Date Received";

		String dealName="Connection Deal 1";
		String dealCompany="Test Advisor 1";
		String dealStage="Deal Received";
		String dealDateRecived="8/30/2022";



		String[] arrSectionHeader=sectionHeader.split("<break>");		
		List<String> sectionHeaderName = new ArrayList<String>(Arrays.asList(arrSectionHeader));


		String[] arrTabName= tabsOnTagged.split("<break>");		
		List<String> tabNameOnTagged = new ArrayList<String>(Arrays.asList(arrTabName));

		String[] arrConnnectionHeader=connectionHeader.split("<break>");
		List<String> connnectionHeaders = new ArrayList<String>(Arrays.asList(arrConnnectionHeader));

		String[] arrDealHeader=dealHeader.split("<break>");
		List<String> dealHeaders = new ArrayList<String>(Arrays.asList(arrDealHeader));

		List<String> contactHeaders=new ArrayList<String>();

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
							log(LogStatus.ERROR, "The Column name, Time referenced and message are not verified ", YesNo.No);
							sa.assertTrue(false, "The Column name, Time referenced and message are not verified ");
						}

						ArrayList<String> result1=bp.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(message, contactHeaders, null, dealHeaders, null,connnectionHeaders,message);

						if(result1.isEmpty())
						{
							log(LogStatus.INFO, "The header name and message have been verified on Interaction, Connections and Deals Section ", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The header name and message are not verified on Interaction, Connections and Deals Section ", YesNo.No);
							sa.assertTrue(false, "The header name and message are not verified on Interaction, Connections and Deals Section ");
						}

						ArrayList<String> result2=bp.verifyToolTipOnDealsConnctionsAndContactsHeader(dealHeaders, contactHeaders, connnectionHeaders);
						if(result2.isEmpty())
						{
							log(LogStatus.INFO, "The Tooltip on Deal and Contacts header have been verified", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The Tooltip on Deal and Contacts header are not verified", YesNo.No);
							sa.assertTrue(false, "The Tooltip on Deal and Contacts header are not verified");
						}

						ArrayList<String> result3=bp.verifyRecordOnDealsSectionInAcuity(recordName, dealName, dealCompany, dealStage, dealDateRecived);
						if(result3.isEmpty())
						{
							log(LogStatus.INFO, "The records on Deal section have been verified for "+recordName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records on Deal section are not verified for "+recordName, YesNo.No);
							sa.assertTrue(false, "The records on Deal section are not verified for "+recordName);
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
			log(LogStatus.ERROR, "Not able to click on tab : "+tabObj2, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab : "+tabObj2);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}


	@Parameters({ "projectName" })
	@Test
	public void ATETc004_VerifyTheCompanyAccountAcuityTabWhenThereIsNoAccountConnectedWithDeal(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName="Nav Account 2";
		String sectionHeader="Tagged<break>Interactions<break>Contacts<break>Deals";
		String tabsOnTagged="Companies<break>People<break>Deals";
		String defaultTabOntagged="Companies";
		String message="No items to display";

		String contactHeader="Name<break>Title<break>Deals<break>Meetings and Calls<break>Emails";
		String dealHeader="Deal Name<break>Stage<break>Date Received";



		String[] arrSectionHeader=sectionHeader.split("<break>");		
		List<String> sectionHeaderName = new ArrayList<String>(Arrays.asList(arrSectionHeader));


		String[] arrTabName= tabsOnTagged.split("<break>");		
		List<String> tabNameOnTagged = new ArrayList<String>(Arrays.asList(arrTabName));

		String[] arrContactHeader=contactHeader.split("<break>");
		List<String> contactHeaders = new ArrayList<String>(Arrays.asList(arrContactHeader));

		String[] arrDealHeader=dealHeader.split("<break>");
		List<String> dealHeaders = new ArrayList<String>(Arrays.asList(arrDealHeader));

		List<String> connectionHeaders=new ArrayList<String>();
		List<String> connectionTooltips=new ArrayList<String>();

		String contactSectionName="Test Nav Contact 7";
		String contactSectionTitle="Associate";
		String contactSectionDeal="0";
		String contactSectionMeetingAndCalls="0";
		String contactSectionEmails="0";


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
							log(LogStatus.ERROR, "The Column name, Time referenced and message are not verified ", YesNo.No);
							sa.assertTrue(false, "The Column name, Time referenced and message are not verified ");
						}

						ArrayList<String> result1=bp.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(message, contactHeaders, null, dealHeaders, message,connectionHeaders,null);

						if(result1.isEmpty())
						{
							log(LogStatus.INFO, "The header name and message have been verified on Interaction, Contacts and Deals Section ", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The header name and message are not verified on Interaction, Contacts and Deals Section ", YesNo.No);
							sa.assertTrue(false, "The header name and message are not verified on Interaction, Contacts and Deals Section ");
						}

						ArrayList<String> result2=bp.verifyToolTipOnDealsConnctionsAndContactsHeader(dealHeaders, contactHeaders, connectionTooltips);
						if(result2.isEmpty())
						{
							log(LogStatus.INFO, "The Tooltip on Deal and Contacts header have been verified", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The Tooltip on Deal and Contacts header are not verified", YesNo.No);
							sa.assertTrue(false, "The Tooltip on Deal and Contacts header are not verified");
						}

						ArrayList<String> result3=bp.verifyRecordOnContactSectionAcuity(contactSectionName, contactSectionTitle, contactSectionDeal, contactSectionMeetingAndCalls, contactSectionEmails);
						if(result3.isEmpty())
						{
							log(LogStatus.INFO, "The records on Contact section have been verified for "+recordName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The records on Contact section are not verified for "+recordName, YesNo.No);
							sa.assertTrue(false, "The records on Contact section are not verified for "+recordName);
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
	public void ATETc005_VerifyContactPageAcuityTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName="Test Contact 4";
		String sectionHeader="Tagged<break>Interactions<break>Connections<break>Deals";
		String tabsOnTagged="Companies<break>People<break>Deals";
		String defaultTabOntagged="Companies";
		String message="No items to display";

		String connectionHeader="Team Member<break>Title<break>Deals<break>Meetings and Calls<break>Emails";

		String dealHeader="Deal Name<break>Company<break>Stage<break>Date Received";

		String[] arrSectionHeader=sectionHeader.split("<break>");		
		List<String> sectionHeaderName = new ArrayList<String>(Arrays.asList(arrSectionHeader));


		String[] arrTabName= tabsOnTagged.split("<break>");		
		List<String> tabNameOnTagged = new ArrayList<String>(Arrays.asList(arrTabName));

		String[] arrConnnectionHeader=connectionHeader.split("<break>");
		List<String> connnectionHeaders = new ArrayList<String>(Arrays.asList(arrConnnectionHeader));

		String[] arrDealHeader=dealHeader.split("<break>");
		List<String> dealHeaders = new ArrayList<String>(Arrays.asList(arrDealHeader));

		List<String> contactHeaders=new ArrayList<String>();

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
							log(LogStatus.ERROR, "The Column name, Time referenced and message are not verified ", YesNo.No);
							sa.assertTrue(false, "The Column name, Time referenced and message are not verified ");
						}

						ArrayList<String> result1=bp.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(message, contactHeaders, null, dealHeaders, message,connnectionHeaders,message);

						if(result1.isEmpty())
						{
							log(LogStatus.INFO, "The header name and message have been verified on Interaction, Connections and Deals Section ", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The header name and message are not verified on Interaction, Connections and Deals Section ", YesNo.No);
							sa.assertTrue(false, "The header name and message are not verified on Interaction, Connections and Deals Section ");
						}

						ArrayList<String> result2=bp.verifyToolTipOnDealsConnctionsAndContactsHeader(dealHeaders, contactHeaders, connnectionHeaders);
						if(result2.isEmpty())
						{
							log(LogStatus.INFO, "The Tooltip on Deal and Contacts header have been verified", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The Tooltip on Deal and Contacts header are not verified", YesNo.No);
							sa.assertTrue(false, "The Tooltip on Deal and Contacts header are not verified");
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
			log(LogStatus.ERROR, "Not able to click on tab : "+tabObj2, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab : "+tabObj2);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}

	@Parameters({ "projectName" })
	@Test
	public void ATETc006_VerifyContactPageAcuityTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName="Test Nav Contact 7";
		String sectionHeader="Tagged<break>Interactions<break>Connections<break>Deals";
		String tabsOnTagged="Companies<break>People<break>Deals";
		String defaultTabOntagged="Companies";
		String message="No items to display";

		String connectionHeader="Team Member<break>Title<break>Deals<break>Meetings and Calls<break>Emails";

		String dealHeader="Deal Name<break>Company<break>Stage<break>Date Received";



		String[] arrSectionHeader=sectionHeader.split("<break>");		
		List<String> sectionHeaderName = new ArrayList<String>(Arrays.asList(arrSectionHeader));


		String[] arrTabName= tabsOnTagged.split("<break>");		
		List<String> tabNameOnTagged = new ArrayList<String>(Arrays.asList(arrTabName));

		String[] arrConnnectionHeader=connectionHeader.split("<break>");
		List<String> connnectionHeaders = new ArrayList<String>(Arrays.asList(arrConnnectionHeader));

		String[] arrDealHeader=dealHeader.split("<break>");
		List<String> dealHeaders = new ArrayList<String>(Arrays.asList(arrDealHeader));

		List<String> contactHeaders=new ArrayList<String>();

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
							log(LogStatus.ERROR, "The Column name, Time referenced and message are not verified ", YesNo.No);
							sa.assertTrue(false, "The Column name, Time referenced and message are not verified ");
						}

						ArrayList<String> result1=bp.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(message, contactHeaders, null, dealHeaders, message,connnectionHeaders,message);

						if(result1.isEmpty())
						{
							log(LogStatus.INFO, "The header name and message have been verified on Interaction, Connections and Deals Section ", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The header name and message are not verified on Interaction, Connections and Deals Section ", YesNo.No);
							sa.assertTrue(false, "The header name and message are not verified on Interaction, Connections and Deals Section ");
						}

						ArrayList<String> result2=bp.verifyToolTipOnDealsConnctionsAndContactsHeader(dealHeaders, contactHeaders, connnectionHeaders);
						if(result2.isEmpty())
						{
							log(LogStatus.INFO, "The Tooltip on Deal and Contacts header have been verified", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "The Tooltip on Deal and Contacts header are not verified", YesNo.No);
							sa.assertTrue(false, "The Tooltip on Deal and Contacts header are not verified");
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
			log(LogStatus.ERROR, "Not able to click on tab : "+tabObj2, YesNo.No);
			sa.assertTrue(false,  "Not able to click on tab : "+tabObj2);
		}

		lp.CRMlogout();	
		sa.assertAll();	
	}


	@Parameters({ "projectName" })
	@Test
	public void ATETc007_CreateATaskWithIntermediaryFirmContactAndVerifyTheAccountAcuityTab(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String recordName="Test Account 1";
		String activityType="Task";
		String taskSubject="Nav Task 1";
		String taskRelatedTo="Test Contact 1<break>Test Account 2<break>Connection Deal 2";
		String taskNotes="This is Navatar Activity";
		String taskDueDate="08/31/2022";
		String taskStatus="In Progress";
		String taskPriority="Normal";

		String[] verifyRelatedToField= {"Test Contact 1",crmUser6FirstName+" "+crmUser6LastName,"+2"};

		String[] completedate = taskDueDate.split("/");
		char dayMonth = completedate[0].charAt(0);
		String month;
		if (dayMonth == '0') {
			month = completedate[0].replaceAll("0", "");
		} else {
			month = completedate[0];
		}

		String date = month + "/" +completedate[1]  + "/" + completedate[2];



		String contactSectionName="Test Contact 1";
		String contactSectionTitle="Managing Director";
		String contactSectionDeal="0";
		String contactSectionMeetingAndCalls="0";
		String contactSectionEmail="0";

		String contactSectionName1="Test Contact 2";
		String contactSectionTitle1="Board Member";
		String contactSectionDeal1="0";
		String contactSectionMeetingAndCalls1="0";
		String contactSectionEmail1="0";

		String dealSectionName="Connection Deal 1";
		String dealSectionStage="Deal Received";
		String dealSectionDateRecieved=date;

		String[] companiesTaggedName= {"Test Account 2"};
		String[] companiesTaggedTimeReference= {"1"};

		String[] dealTaggedName= {"Connection Deal 2"};
		String[] dealTaggedTimeReference= {"1"};


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

						ArrayList<String> result = bp.verifyRecordOnInteractionCard(date,taskSubject, taskNotes, true, false,verifyRelatedToField);
						if (result.isEmpty()) {
							log(LogStatus.PASS,taskSubject + " record has been verified on intraction",YesNo.No);
							sa.assertTrue(true,taskSubject + " record has been verified on intraction");
						} else {
							log(LogStatus.ERROR,taskSubject + " record is not verified on intraction",YesNo.No);
							sa.assertTrue(false,taskSubject + " record is not verified on intraction");
						}

						ArrayList<String> result1=bp.verifyRecordOnContactSectionAcuity(contactSectionName, contactSectionTitle, contactSectionDeal, contactSectionMeetingAndCalls, contactSectionEmail);
						if(result1.isEmpty())
						{
							log(LogStatus.INFO, contactSectionName+" record on Contact section has been verified for "+recordName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, contactSectionName+" record on Contact section is not verified for "+recordName, YesNo.No);
							sa.assertTrue(false, contactSectionName+" record on Contact section is not verified for "+recordName);
						}

						ArrayList<String> result2=bp.verifyRecordOnContactSectionAcuity(contactSectionName1, contactSectionTitle1, contactSectionDeal1, contactSectionMeetingAndCalls1, contactSectionEmail1);
						if(result2.isEmpty())
						{
							log(LogStatus.INFO, contactSectionName1+" record on Contact section has been verified for "+recordName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, contactSectionName1+" record on Contact section is not verified for "+recordName, YesNo.No);
							sa.assertTrue(false, contactSectionName1+" record on Contact section is not verified for "+recordName);
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

						ArrayList<String> result4=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, null, null, dealTaggedName, dealTaggedTimeReference);
						if(result4.isEmpty())
						{
							log(LogStatus.INFO, dealSectionName+" record on Deal section has been verified for "+recordName, YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR,  dealSectionName+" record on Deal section is not verified for "+recordName, YesNo.No);
							sa.assertTrue(false,  dealSectionName+" record on Deal section is not verified for "+recordName);
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
	public void ATETc008_VerifyConnectionPopupOnAccountPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String recordName="Test Account 1";
		String contactName="Test Contact 1";
		ArrayList<String> headerName=new ArrayList<String>();
		headerName.add("Team Member");
		headerName.add("Title");
		headerName.add("Deals");
		headerName.add("Meetings and Calls");
		headerName.add("Emails");
		
		String message="No items to display";
		
		
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
						}
						else
						{
							log(LogStatus.ERROR, "The UI of Connections popup have been verified", YesNo.No);
							sa.assertTrue(false,  "The UI of Connections popup have been verified");
							
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


	


}
