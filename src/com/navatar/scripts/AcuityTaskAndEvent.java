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
		String recordName=ATERecord1;		
		String sectionHeader=ATE_Section1;
		String tabsOnTagged=ATE_Tabs1;
		String defaultTabOntagged="Companies";
		String message=bp.acuityDefaultMessage;

		String contactHeader=ATE_ContactHeader1;
		String dealHeader=ATE_DealHeader1;


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

		String recordName=ATE_Contact1;
		String sectionHeader=ATE_Section1;
		String tabsOnTagged=ATE_Tabs1;
		String defaultTabOntagged="Companies";
		String message=bp.acuityDefaultMessage;

		String connectionHeader=ATE_ConnectionHeader1;

		String dealHeader=ATE_DealHeader1;

		String dealName=ATE_DealName1;
		String dealCompany=ATE_DealCompany1;
		String dealStage=ATE_DealStage1;
		String dealDateRecived=ATE_DealDateRecieved1;

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

		String recordName=ATERecord2;

		String sectionHeader=ATE_Section1;
		String tabsOnTagged=ATE_Tabs1;
		String defaultTabOntagged="Companies";
		String message=bp.acuityDefaultMessage;

		String contactHeader=ATE_ContactHeader1;
		String dealHeader=ATE_DealHeader1;



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

		String contactSectionName=ATE_ContactName1;
		String contactSectionTitle=ATE_ContactTitle1;
		String contactSectionDeal=ATE_ContactDeal1;
		String contactSectionMeetingAndCalls=ATE_ContactMeetingAndCall1;
		String contactSectionEmails=ATE_ContactEmail1;


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


		String recordName=ATE_Contact2;
		String sectionHeader=ATE_Section1;
		String tabsOnTagged=ATE_Tabs1;
		String defaultTabOntagged="Companies";
		String message=bp.acuityDefaultMessage;

		String connectionHeader=ATE_ConnectionHeader1;

		String dealHeader=ATE_DealHeader1;

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

		String recordName=ATE_Contact3;
		String sectionHeader=ATE_Section1;
		String tabsOnTagged=ATE_Tabs1;
		String defaultTabOntagged="Companies";
		String message=bp.acuityDefaultMessage;

		String connectionHeader=ATE_ConnectionHeader1;

		String dealHeader=ATE_DealHeader1;

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

		String[] completedate = taskDueDate.split("/");
		char dayMonth = completedate[0].charAt(0);
		String month;
		if (dayMonth == '0') {
			month = completedate[0].replaceAll("0", "");
		} else {
			month = completedate[0];
		}

		String date = month + "/" +completedate[1]  + "/" + completedate[2];



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

		String dealSectionName=ATE_DealName2;
		String dealSectionStage=ATE_DealStage2;
		String dealSectionDateRecieved=ATE_DealDateRecieved2;

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

						ArrayList<String> result = bp.verifyRecordOnInteractionCard(date,null,taskSubject, taskNotes, true, false,verifyRelatedToField,null);
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
							log(LogStatus.INFO, "The record name and Time reference have been verifed", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR,  "The record name and Time reference are not verifed", YesNo.No);
							sa.assertTrue(false,  "The record name and Time reference are not verifed");
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
		String recordName=ATERecord3;
		String contactName=ATE_Contact1;

		String[] val=ATE_ConnectionHeader1.split("<break>");
		ArrayList<String> headerName=new ArrayList<String>();
		for(String txt:val)
		{
			headerName.add(txt);
		}

		String message=bp.acuityDefaultMessage;

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


	@Parameters({ "projectName" })
	@Test
	public void ATETc009_VerifyContactPageAcuityTab(String projectName) {

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

		String[] completedate = taskDueDate.split("/");
		char dayMonth = completedate[0].charAt(0);
		String month;
		if (dayMonth == '0') {
			month = completedate[0].replaceAll("0", "");
		} else {
			month = completedate[0];
		}

		String date = month + "/" +completedate[1]  + "/" + completedate[2];


		String dueDate=date;
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
						log(LogStatus.ERROR, "Either the message or header names or both are not verified on Connection section", YesNo.No);
						sa.assertTrue(false,  "Either the message or header names or both are not verified on Connection section");
					}


					ArrayList<String> result2=bp.verifyRecordAndReferencedTypeOnTagged(companiesTaggedName, companiesTaggedTimeReference, null, null, dealsTaggedName, dealsTaggedTimeReference);
					if(result2.isEmpty())
					{
						log(LogStatus.INFO, "The record name and Referenced type count have been matched on Company and Deal tab", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The record name and Referenced type count are not matched on Company and Deal tab", YesNo.No);
						sa.assertTrue(false,  "The record name and Referenced type count are not matched on Company and Deal tab");
					}

					ArrayList<String> result3=bp.verifyColumnsAndMessageOnTabsOfTagged(tabName, message);
					if(result3.isEmpty())
					{
						log(LogStatus.INFO, "The message and columns has been verified on people tab", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The message and columns are not verified on people tab", YesNo.No);
						sa.assertTrue(false,  "The message and columns are not verified on people tab");
					}

					ArrayList<String> result1=bp.verifyRecordOnInteractionCard(dueDate, IconType.Task,	subject, notes, true, false, relatedTo,relatedAssociation);
					if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The task has been verified on Interaction card. subject name: "+subject , YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The task is not verified on Interaction card. subject name: "+subject , YesNo.No);
						sa.assertTrue(false,  "The task is not verified on Interaction card. subject name: "+subject);
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
	public void ATETc010_CreateALogACallTaskWithIntermediaryFirmsContactAndVerifyTheAccountAcuityTab(String projectName) {

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

		String[] completedate = taskDueDate.split("/");
		char dayMonth = completedate[0].charAt(0);
		String month;
		if (dayMonth == '0') {
			month = completedate[0].replaceAll("0", "");
		} else {
			month = completedate[0];
		}

		String date = month + "/" +completedate[1]  + "/" + completedate[2];


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

		String dealSectionName=ATE_DealName2;
		String dealSectionStage=ATE_DealStage2;
		String dealSectionDateRecieved=ATE_DealDateRecieved2;


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

						ArrayList<String> result = bp.verifyRecordOnInteractionCard(date,IconType.Call,taskSubject, taskNotes, true, false,relatedToData,null);
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
						refresh(driver);

						String xPath="//a[text()='"+contactSectionName1+"']/ancestor::tr/td[@data-label='Meetings and Calls']//button";
						WebElement ele=FindElement(driver, xPath, "Meeting and call", action.SCROLLANDBOOLEAN, 30);
						if(click(driver, ele, "Meeting and call", action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, "Clicked on the count of meeting and call of "+contactSectionName1+" record on contact section",	YesNo.No);		
							xPath="//h2[contains(text(),'Meetings and Calls')]/../following-sibling::div//div[text()='No items to display']";
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
	public void ATETc011_VerifyConnectionPopupOnIntermediaryAccountPage(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String xPath;
		WebElement ele;


		String taskSubject=ATE_ATSubject2;
		String taskDetails=ATE_ATNotes2;
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

											ArrayList<String> result1=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity("call",date, taskSubject, taskDetails, userName);
											if(result1.isEmpty())
											{
												log(LogStatus.INFO, "The records on meeting & calls popup have been verified for "+contactSectionName, YesNo.No);
											}
											else
											{
												log(LogStatus.ERROR, "The records on meeting & calls popup are not verified for "+contactSectionName, YesNo.No);
												sa.assertTrue(false, "The records on meeting & calls popup are not verified for "+contactSectionName);
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
							log(LogStatus.ERROR, "The records on Connection popup are not verified for "+contactSectionName, YesNo.No);
							sa.assertTrue(false, "The records on Connection popup are not verified for "+contactSectionName);
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
	public void ATETc012_VerifyContactPageAcuityTab(String projectName) {

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
	public void ATETc014_VerifyConnectionPopupOnIntermediaryAccountPageAndCountVerification(String projectName) {

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
	public void ATETc015_VerifyAcuityTabOnContactRecordPageAndAlsoVerifyPlusCount(String projectName) {

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
	public void ATETc0016_Creating1MoreNewTasks(String projectName) {

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
	
	

}
