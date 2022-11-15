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

					if(bp.verifyViewAllButtonOnIntractionCard(20))
					{
						log(LogStatus.INFO, "View all button is not visible on interaction section", YesNo.No);


						xPath="//a[text()='"+contactSectionName+"']/ancestor::tr/td[@data-col-key-value='1-button-1']";
						ele=FindElement(driver, xPath, contactSectionName+" connection icon", action.SCROLLANDBOOLEAN, 20);
						if(click(driver, ele, contactSectionName+" Connection icon", action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, "Clicked on connection icon of "+contactSectionName, YesNo.No);

							ArrayList<String> result=bp.verifyRecordOnConnectionsPopUpOfContactInAcuity(recordName, userName, contactSectionTitle, contactSectionDeal, contactSectionMeetingAndCalls, contactSectionEmail);
							if(result.isEmpty())
							{
								log(LogStatus.INFO, "The records on Connection popup have been verified for "+contactSectionName, YesNo.No);
								xPath="//a[text()='"+userName+"']/ancestor::tr//td[@data-label='Meetings and Calls']//button";
								ele=FindElement(driver, xPath, "meeting and call count of "+userName, action.SCROLLANDBOOLEAN, 20);
								if(click(driver, ele, contactSectionEmail, action.SCROLLANDBOOLEAN))
								{
									log(LogStatus.INFO, "clicked on the count of meeting and call of "+userName, YesNo.No);

									ArrayList<String> result1=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity(date, taskSubject, taskDetails, userName);
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
								log(LogStatus.ERROR, "The records on Connection popup are not verified for "+contactSectionName, YesNo.No);
								sa.assertTrue(false, "The records on Connection popup are not verified for "+contactSectionName);
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
	public void ATETc012_VerifyConnectionPopupOnIntermediaryAccountPage(String projectName) {

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

					if(bp.verifyViewAllButtonOnIntractionCard(20))
					{
						log(LogStatus.INFO, "View all button is not visible on interaction section", YesNo.No);


						xPath="//a[text()='"+contactSectionName+"']/ancestor::tr/td[@data-col-key-value='1-button-1']";
						ele=FindElement(driver, xPath, contactSectionName+" connection icon", action.SCROLLANDBOOLEAN, 20);
						if(click(driver, ele, contactSectionName+" Connection icon", action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, "Clicked on connection icon of "+contactSectionName, YesNo.No);

							ArrayList<String> result=bp.verifyRecordOnConnectionsPopUpOfContactInAcuity(recordName, userName, contactSectionTitle, contactSectionDeal, contactSectionMeetingAndCalls, contactSectionEmail);
							if(result.isEmpty())
							{
								log(LogStatus.INFO, "The records on Connection popup have been verified for "+contactSectionName, YesNo.No);
								xPath="//a[text()='"+userName+"']/ancestor::tr//td[@data-label='Meetings and Calls']//button";
								ele=FindElement(driver, xPath, "meeting and call count of "+userName, action.SCROLLANDBOOLEAN, 20);
								if(click(driver, ele, contactSectionEmail, action.SCROLLANDBOOLEAN))
								{
									log(LogStatus.INFO, "clicked on the count of meeting and call of "+userName, YesNo.No);

									ArrayList<String> result1=bp.verifyRecordOnMeetingsAndCallPopUpSectionInAcuity(date, taskSubject, taskDetails, userName);
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
								log(LogStatus.ERROR, "The records on Connection popup are not verified for "+contactSectionName, YesNo.No);
								sa.assertTrue(false, "The records on Connection popup are not verified for "+contactSectionName);
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

	
	


}
