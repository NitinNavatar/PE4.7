package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;

import java.util.ArrayList;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.ExcelUtils;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.DealPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;

public class AcuitySomke extends BaseLib{

	@Parameters({ "projectName" })

	@Test
	public void ASTc001_CreateCRMUser(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(crmUser1LastName);
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
					if (setup.createPEUser(crmUser1FirstName, UserLastName, emailId, crmUserLience, crmUserProfile)) {
						log(LogStatus.INFO,
								"CRM User is created Successfully: " + crmUser1FirstName + " " + UserLastName,
								YesNo.No);
						ExcelUtils.writeData(testCasesFilePath, emailId, "Users", excelLabel.Variable_Name, "User1",
								excelLabel.User_Email);
						ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name,
								"User1", excelLabel.User_Last_Name);
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
				if (setup.installedPackages(crmUser1FirstName, UserLastName)) {
					appLog.info("PE Package is installed Successfully in CRM User: " + crmUser1FirstName + " "
							+ UserLastName);

				} else {
					appLog.error(
							"Not able to install PE package in CRM User1: " + crmUser1FirstName + " " + UserLastName);
					sa.assertTrue(false,
							"Not able to install PE package in CRM User1: " + crmUser1FirstName + " " + UserLastName);
					log(LogStatus.ERROR,
							"Not able to install PE package in CRM User1: " + crmUser1FirstName + " " + UserLastName,
							YesNo.Yes);
				}
			}
		} else {

			log(LogStatus.ERROR, "could not click on setup link, test case fail", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link, test case fail");

		}
		lp.CRMlogout();
		closeBrowser();
		//		driver.switchTo().window(parentWindow);
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
			appLog.info("Password is set successfully for CRM User1: " + crmUser1FirstName + " " + UserLastName);
		} else {
			appLog.info("Password is not set for CRM User1: " + crmUser1FirstName + " " + UserLastName);
			sa.assertTrue(false, "Password is not set for CRM User1: " + crmUser1FirstName + " " + UserLastName);
			log(LogStatus.ERROR, "Password is not set for CRM User1: " + crmUser1FirstName + " " + UserLastName,
					YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void ASTc002_CreateTaskToVerifyDataOnInteractionCard(String projectName) {


		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp=new ContactsPageBusinessLayer(driver);
		DealPageBusinessLayer dp=new DealPageBusinessLayer(driver);
		BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
		FundsPageBusinessLayer fd=new FundsPageBusinessLayer(driver);

		String xPath="";
		WebElement ele;

		String [] accountName=AS_FirmLegalName1.split("<break>");
		String [] recordType=AS_FirmRecordType1.split("<break>");

		String[] contactFirstName=AS_ContactFirstName.split("<break>");
		String[] contactLastName=AS_ContactLastName.split("<break>");
		String[] contactLegalName=AS_ContactLegalName.split("<break>");
		String[] contactEmail=AS_ContactEmail.split("<break>");

		String[] dealName=AS_DealName.split("<break>");
		String[] dealCompany=AS_DealCompany.split("<break>");
		String[] dealStage=AS_DealStage.split("<break>");

		String fundName=AS_FundName;
		String fundType=AS_FundType;
		String fundInvestmentCategory=AS_FundInvestmentCategory;
		String[] suggestedTag=AS_ATSuggestedTag1.split("<break>");
		String companyRecord=accountName[1];
		int status=0; 

		String[][] basicsection= {{"Subject",AS_ATSubject1},{"Notes",AS_ATNotes1},{"Related_To",AS_ATRelatedTo1}};
		String[][] advanceSection= {{"Due Date Only",AS_ATAdvanceDueDate1}};

		String[] completedate=AS_ATAdvanceDueDate1.split("/");
		char dayNum=completedate[1].charAt(0);
		String day;
		if(dayNum=='0')
		{
			day=completedate[1].replaceAll("0", "");
		}
		else
		{
			day=completedate[1];
		}

		String date=completedate[0]+"/"+day+"/"+completedate[2];

		lp.CRMLogin(superAdminUserName, adminPassword, appName);	
		if(accountName.length==recordType.length)
		{
			for(int i=0; i<accountName.length; i++)
			{
				if (lp.clickOnTab(projectName, tabObj1)) {

					log(LogStatus.INFO, "Click on Tab : " + tabObj1, YesNo.No);
					ThreadSleep(3000);
					if (ip.createEntityOrAccount(environment, mode, accountName[i], recordType[i],
							null, null,30)) {
						log(LogStatus.INFO, "successfully Created Firm : " + accountName[i] + " of record type : "
								+ recordType[i], YesNo.No);
						sa.assertTrue(true, "successfully Created Firm : " + accountName[i] + " of record type : "
								+ recordType[i]);
						status++;

					} else {
						sa.assertTrue(false, "Not Able to Create Firm : " + accountName[i] + " of record type : "
								+ recordType[i]);
						log(LogStatus.SKIP, "Not Able to Create Firm : " + accountName[i] + " of record type :"
								+ recordType[i], YesNo.Yes);
					}

				}
				else
				{
					log(LogStatus.FAIL, "Not able to click on "+tabObj1+" Tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on "+tabObj1+" Tab");	
				}

			}
		}
		else
		{
			log(LogStatus.FAIL, "The count of Legal name and Record Type are not equal. Either Legal Name or Record type value are not proper", YesNo.No);
			sa.assertTrue(false, "The count of Legal name and Record Type are not equal. Either Legal Name or Record type value are not proper");
		}

		if(status==accountName.length)
		{
			status=0;	

			for(int i=0; i<contactLastName.length; i++)
			{
				if (lp.clickOnTab(projectName, tabObj2)) {

					log(LogStatus.INFO, "Click on Tab : " + tabObj2, YesNo.No);
					ThreadSleep(3000);

					if(cp.createContact(projectName, contactFirstName[i], contactLastName[i], contactLegalName[i], contactEmail[i], "", null, null, CreationPage.ContactPage, null, null))
					{
						log(LogStatus.INFO, "successfully Created Contact : " + contactFirstName[i]+" "+contactLastName[i], YesNo.No);
						sa.assertTrue(true, "successfully Created Contact : " + contactFirstName[i]+" "+contactLastName[i]);
						status++;

					}
					else
					{
						log(LogStatus.FAIL, "Not able to create the Contact : " + contactFirstName[i]+" "+contactLastName[i], YesNo.No);
						sa.assertTrue(false, "Not able to create the Contact : " + contactFirstName[i]+" "+contactLastName[i]);
					}

				}
				else
				{
					log(LogStatus.FAIL, "Not able to click on "+tabObj2+" Tab", YesNo.No);
					sa.assertTrue(false,  "Not able to click on "+tabObj2+" Tab");	
				}
			}
			if(status==contactLastName.length)
			{
				status=0;
				for(int i=0; i<dealName.length; i++)
				{
					if (lp.clickOnTab(projectName, tabObj4)) {

						log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
						ThreadSleep(3000);

						if(dp.createDeal(projectName, dealName[i], dealCompany[i], dealStage[i]))
						{
							log(LogStatus.INFO, dealName[i]+" deal has been created", YesNo.No);
							sa.assertTrue(true,  dealName[i]+" deal has been created");
							status++;
						}
						else
						{
							log(LogStatus.ERROR, dealName[i]+" deal is not created", YesNo.No);
							sa.assertTrue(false,  dealName[i]+" deal is not created");	
						}

					}

					else
					{
						log(LogStatus.ERROR, "Not able to click on "+tabObj4+" Tab", YesNo.No);
						sa.assertTrue(false,  "Not able to click on "+tabObj4+" Tab");	
					}
				}
				if(status==dealName.length)
				{

					if (lp.clickOnTab(projectName, tabObj3)) {

						log(LogStatus.INFO, "Click on Tab : " + tabObj3, YesNo.No);
						ThreadSleep(3000);

						if(fd.createFund(projectName, fundName, fundType, fundInvestmentCategory, null, null))
						{
							log(LogStatus.INFO, fundName+" Fund has been created", YesNo.No);
							sa.assertTrue(true, fundName+" Fund has been created");


							if (lp.clickOnTab(projectName, tabObj1)) {

								log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

								if(bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,companyRecord , 30))
								{
									log(LogStatus.INFO, companyRecord+" reocrd has been open", YesNo.No);
									if(bp.clicktabOnPage("Communications"))
									{
										log(LogStatus.INFO, "clicked on Communications tab", YesNo.No);

										if(bp.createActivityTimeline(projectName,false,"New Task", basicsection, advanceSection,null,suggestedTag))
										{
											log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);
											sa.assertTrue(true, "Activity timeline record has been created");
											ThreadSleep(4000);
											if(bp.clicktabOnPage("Acuity"))
											{
												log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
												ArrayList<String> result=bp.verifyRecordOnInteractionCard(date,AS_ATSubject1, AS_ATNotes1, true, false);
												if(result.isEmpty())
												{
													log(LogStatus.PASS, AS_ATSubject1+" record has been verified on intraction", YesNo.No);
													sa.assertTrue(true, AS_ATSubject1+" record has been verified on intraction");
												}
												else
												{
													log(LogStatus.ERROR, AS_ATSubject1+" record is not verified on intraction", YesNo.No);
													sa.assertTrue(false, AS_ATSubject1+" record is not verified on intraction");
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
											log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
											sa.assertTrue(false, "Activity timeline record is not created");
										}

									}
									else
									{
										log(LogStatus.ERROR, "Not able to click on Communications tab", YesNo.No);
										sa.assertTrue(false, "Not able to click on Communications tab");
									}

								}
								else
								{
									log(LogStatus.ERROR, "Not able to open "+companyRecord+" reocrd", YesNo.No);
									sa.assertTrue(false, "Not able to open "+companyRecord+" reocrd");
								}
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
								sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
							}
						}
						else
						{
							log(LogStatus.ERROR, fundName+" Fund is not created", YesNo.No);
							sa.assertTrue(false,  fundName+" Fund is not created");	
						}

					}
					else
					{
						log(LogStatus.ERROR, "Not able to click on "+tabObj3+" Tab", YesNo.No);
						sa.assertTrue(false,  "Not able to click on "+tabObj3+" Tab");	
					}
				}
				else
				{
					log(LogStatus.ERROR, "Deal records are not created", YesNo.No);
					sa.assertTrue(false,  "Deal records are not created");
				}

			}
			else
			{
				log(LogStatus.ERROR, "Contact records are not created", YesNo.No);
				sa.assertTrue(false,  "Contact records are not created");
			}
		}
		else
		{
			log(LogStatus.FAIL, "Firm records are not created", YesNo.No);
			sa.assertTrue(false,  "Firm records are not created");	
		}

		lp.CRMlogout();
		sa.assertAll();


	}

	@Parameters({ "projectName" })

	@Test
	public void ASTc003_VerifyImpactAfterCreationOfTask(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
		String[] Companies=AS_ACompanies1.split("<break>");
		String[] People = new String[1];
		String[] Deals = new String[1];
		People[0]=AS_APeople1;
		Deals[0]=AS_ADeals1;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);


		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if(bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,AS_FirmLegalName2 , 30))
			{
				log(LogStatus.INFO, AS_FirmLegalName2+" reocrd has been open", YesNo.No);

				ArrayList<String> result=bp.verifyRecordOnTagged(Companies, People, Deals);
				if(result.isEmpty())
				{
					log(LogStatus.INFO, "Records on Company, People and Deals Tagged have been matched", YesNo.No);
					sa.assertTrue(true,  "Records on Company, People and Deals Tagged have been matched");		
				}
				else
				{
					log(LogStatus.ERROR, "Records on Company, People and Deals Tagged are not matched", YesNo.No);
					sa.assertTrue(false,  "Records on Company, People and Deals Tagged are not matched");	
				}
				ArrayList<String> result1=bp.verifyRecordOnContactSectionAcuity(AS_AContactName1, null, null, AS_AMeetingsAndCalls1, null);
				if(result1.isEmpty())
				{
					log(LogStatus.INFO, "Records on Contact slot have been matched", YesNo.No);
					sa.assertTrue(true,  "Records on Contact slot have been matched");		
				}
				else
				{
					log(LogStatus.ERROR, "Records on Contact slot are not matched", YesNo.No);
					sa.assertTrue(false,  "Records on Contact slot are not matched");	
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open "+AS_FirmLegalName2+" record", YesNo.No);
				sa.assertTrue(false,  "Not able to open "+AS_FirmLegalName2+" record");	
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on "+tabObj1+" tab", YesNo.No);
			sa.assertTrue(false,  "Not able to click on "+tabObj1+" tab");	
		}
		
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if(bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,AS_ContactName2 , 30))
			{
				log(LogStatus.INFO, AS_ContactName2+" reocrd has been open", YesNo.No);

				ArrayList<String> result=bp.verifyRecordOnTagged(Companies, People, Deals);
				if(result.isEmpty())
				{
					log(LogStatus.INFO, "Records on Company, People and Deals Tagged have been matched", YesNo.No);
					sa.assertTrue(true,  "Records on Company, People and Deals Tagged have been matched");		
				}
				else
				{
					log(LogStatus.ERROR, "Records on Company, People and Deals Tagged are not matched", YesNo.No);
					sa.assertTrue(false,  "Records on Company, People and Deals Tagged are not matched");	
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open "+AS_ContactName2+" record", YesNo.No);
				sa.assertTrue(false,  "Not able to open "+AS_ContactName2+" record");	
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on "+tabObj2+" tab", YesNo.No);
			sa.assertTrue(false,  "Not able to click on "+tabObj2+" tab");	
		}

		lp.CRMlogout();
		sa.assertAll();

	}
	
	@Parameters({ "projectName" })

	@Test
	public void ASTc004_CreateEventToVerifyDataOnInteractionCard(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp=new ContactsPageBusinessLayer(driver);
		DealPageBusinessLayer dp=new DealPageBusinessLayer(driver);
		BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
		FundsPageBusinessLayer fd=new FundsPageBusinessLayer(driver);
		
		String[][] basicsection= {{"Subject",AS_ATSubject2},{"Notes",AS_ATNotes2},{"Related_To",AS_ATRelatedTo2}};
		String[][] advanceSection= {{"Start Date",AS_ATAdvanceStartDate1},{"End Date",AS_ATAdvanceEndDate1}};
		String[] suggestedTag=new String[1];
		suggestedTag[0]=AS_ATSuggestedTag2;
		String[] completedate=AS_ATAdvanceStartDate1.split("/");
		char dayNum=completedate[1].charAt(0);
		String day;
		if(dayNum=='0')
		{
			day=completedate[1].replaceAll("0", "");
		}
		else
		{
			day=completedate[1];
		}

		String date=completedate[0]+"/"+day+"/"+completedate[2];
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if(bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, AS_FirmLegalName2 , 30))
			{
				log(LogStatus.INFO, AS_FirmLegalName2+" reocrd has been open", YesNo.No);
				if(bp.clicktabOnPage("Communications"))
				{
					log(LogStatus.INFO, "clicked on Communications tab", YesNo.No);

					if(bp.createActivityTimeline(projectName,false,AS_ATActivityType2, basicsection, advanceSection,null,suggestedTag))
					{
						log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);
						sa.assertTrue(true, "Activity timeline record has been created");
						ThreadSleep(4000);
						if(bp.clicktabOnPage("Acuity"))
						{
							log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
							ArrayList<String> result=bp.verifyRecordOnInteractionCard(date,AS_ATSubject2, AS_ATNotes2, false, true);
							if(result.isEmpty())
							{
								log(LogStatus.PASS, AS_ATSubject2+" record has been verified on intraction", YesNo.No);
								sa.assertTrue(true, AS_ATSubject2+" record has been verified on intraction");
							}
							else
							{
								log(LogStatus.ERROR, AS_ATSubject2+" record is not verified on intraction", YesNo.No);
								sa.assertTrue(false, AS_ATSubject2+" record is not verified on intraction");
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
						log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
						sa.assertTrue(false, "Activity timeline record is not created");
					}

				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Communications tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Communications tab");
				}

			}
			else
			{
				log(LogStatus.ERROR, "Not able to open "+AS_FirmLegalName2+" reocrd", YesNo.No);
				sa.assertTrue(false, "Not able to open "+AS_FirmLegalName2+" reocrd");
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	

	@Parameters({ "projectName" })

	@Test
	public void ASTc005_VerifyImpactAfterCreationOfEvent(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);

		String[] Companies = AS_ACompanies2.split("<break>");
		String[] People = new String[1];
		String[] Deals = new String[1];
		People[0] = AS_APeople2;
		Deals[0] = AS_ADeals2;

		lp.CRMLogin(superAdminUserName, adminPassword, appName);


		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if(bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,AS_FirmLegalName4 , 30))
			{
				log(LogStatus.INFO, AS_FirmLegalName4+" reocrd has been open", YesNo.No);

				ArrayList<String> result=bp.verifyRecordOnTagged(Companies, People, Deals);
				if(result.isEmpty())
				{
					log(LogStatus.INFO, "Records on Company, People and Deals Tagged have been matched", YesNo.No);
					sa.assertTrue(true,  "Records on Company, People and Deals Tagged have been matched");		
				}
				else
				{
					log(LogStatus.ERROR, "Records on Company, People and Deals Tagged are not matched", YesNo.No);
					sa.assertTrue(false,  "Records on Company, People and Deals Tagged are not matched");	
				}
				ArrayList<String> result1=bp.verifyRecordOnContactSectionAcuity(AS_AContactName2, null, null, AS_AMeetingsAndCalls2, null);
				if(result1.isEmpty())
				{
					log(LogStatus.INFO, "Records on Contact slot have been matched", YesNo.No);
					sa.assertTrue(true,  "Records on Contact slot have been matched");		
				}
				else
				{
					log(LogStatus.ERROR, "Records on Contact slot are not matched", YesNo.No);
					sa.assertTrue(false,  "Records on Contact slot are not matched");	
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open "+AS_FirmLegalName4+" record", YesNo.No);
				sa.assertTrue(false,  "Not able to open "+AS_FirmLegalName4+" record");	
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on "+tabObj1+" tab", YesNo.No);
			sa.assertTrue(false,  "Not able to click on "+tabObj1+" tab");	
		}
		
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if(bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,AS_ContactName3 , 30))
			{
				log(LogStatus.INFO, AS_ContactName3+" reocrd has been open", YesNo.No);

				ArrayList<String> result=bp.verifyRecordOnTagged(Companies, People, Deals);
				if(result.isEmpty())
				{
					log(LogStatus.INFO, "Records on Company, People and Deals Tagged have been matched", YesNo.No);
					sa.assertTrue(true,  "Records on Company, People and Deals Tagged have been matched");		
				}
				else
				{
					log(LogStatus.ERROR, "Records on Company, People and Deals Tagged are not matched", YesNo.No);
					sa.assertTrue(false,  "Records on Company, People and Deals Tagged are not matched");	
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open "+AS_ContactName3+" record", YesNo.No);
				sa.assertTrue(false,  "Not able to open "+AS_ContactName3+" record");	
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on "+tabObj2+" tab", YesNo.No);
			sa.assertTrue(false,  "Not able to click on "+tabObj2+" tab");	
		}

		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName" })

	@Test
	public void ASTc006_CreateLogACallToVerifyDataOnInteractionCard(String projectName) {
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp=new ContactsPageBusinessLayer(driver);
		DealPageBusinessLayer dp=new DealPageBusinessLayer(driver);
		BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
		FundsPageBusinessLayer fd=new FundsPageBusinessLayer(driver);
		
		String[][] basicsection= {{"Subject",AS_ATSubject3},{"Notes",AS_ATNotes3},{"Related_To",AS_ATRelatedTo3}};
		String[][] advanceSection= {{"Due Date Only",AS_ATAdvanceDueDate2}};
		
		String[] completedate=AS_ATAdvanceDueDate2.split("/");
		char dayNum=completedate[1].charAt(0);
		String day;
		if(dayNum=='0')
		{
			day=completedate[1].replaceAll("0", "");
		}
		else
		{
			day=completedate[1];
		}

		String date=completedate[0]+"/"+day+"/"+completedate[2];
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if(bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab, AS_FirmLegalName5 , 30))
			{
				log(LogStatus.INFO, AS_FirmLegalName5+" reocrd has been open", YesNo.No);
				if(bp.clicktabOnPage("Communications"))
				{
					log(LogStatus.INFO, "clicked on Communications tab", YesNo.No);

					if(bp.createActivityTimeline(projectName,false,AS_ATActivityType3, basicsection, advanceSection,null,null))
					{
						log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);
						sa.assertTrue(true, "Activity timeline record has been created");
						ThreadSleep(4000);
						if(bp.clicktabOnPage("Acuity"))
						{
							log(LogStatus.INFO, "clicked on Acuity tab", YesNo.No);
							ArrayList<String> result=bp.verifyRecordOnInteractionCard(date,AS_ATSubject3, AS_ATNotes3, true, false);
							if(result.isEmpty())
							{
								log(LogStatus.PASS, AS_ATSubject3+" record has been verified on intraction", YesNo.No);
								sa.assertTrue(true, AS_ATSubject3+" record has been verified on intraction");
							}
							else
							{
								log(LogStatus.ERROR, AS_ATSubject3+" record is not verified on intraction", YesNo.No);
								sa.assertTrue(false, AS_ATSubject3+" record is not verified on intraction");
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
						log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
						sa.assertTrue(false, "Activity timeline record is not created");
					}

				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Communications tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on Communications tab");
				}

			}
			else
			{
				log(LogStatus.ERROR, "Not able to open "+AS_FirmLegalName5+" reocrd", YesNo.No);
				sa.assertTrue(false, "Not able to open "+AS_FirmLegalName5+" reocrd");
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
			sa.assertTrue(false, "Not able to click on Tab : " + tabObj1);
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	
	@Parameters({ "projectName" })

	@Test
	public void ASTc007_VerifyImpactAfterCreationOfCall(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);

		String[] Companies = AS_ACompanies3.split("<break>");
		String[] People = new String[1];
		String[] Deals = new String[1];
		People[0] = AS_APeople3;
		Deals[0] = AS_ADeals3;

		lp.CRMLogin(superAdminUserName, adminPassword, appName);


		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if(bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,AS_FirmLegalName6 , 30))
			{
				log(LogStatus.INFO, AS_FirmLegalName6+" reocrd has been open", YesNo.No);

				ArrayList<String> result=bp.verifyRecordOnTagged(Companies, People, Deals);
				if(result.isEmpty())
				{
					log(LogStatus.INFO, "Records on Company, People and Deals Tagged have been matched", YesNo.No);
					sa.assertTrue(true,  "Records on Company, People and Deals Tagged have been matched");		
				}
				else
				{
					log(LogStatus.ERROR, "Records on Company, People and Deals Tagged are not matched", YesNo.No);
					sa.assertTrue(false,  "Records on Company, People and Deals Tagged are not matched");	
				}
				ArrayList<String> result1=bp.verifyRecordOnContactSectionAcuity(AS_AContactName3, null, null, AS_AMeetingsAndCalls3, null);
				if(result1.isEmpty())
				{
					log(LogStatus.INFO, "Records on Contact slot have been matched", YesNo.No);
					sa.assertTrue(true,  "Records on Contact slot have been matched");		
				}
				else
				{
					log(LogStatus.ERROR, "Records on Contact slot are not matched", YesNo.No);
					sa.assertTrue(false,  "Records on Contact slot are not matched");	
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open "+AS_FirmLegalName6+" record", YesNo.No);
				sa.assertTrue(false,  "Not able to open "+AS_FirmLegalName6+" record");	
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on "+tabObj1+" tab", YesNo.No);
			sa.assertTrue(false,  "Not able to click on "+tabObj1+" tab");	
		}
		
		if (lp.clickOnTab(projectName, tabObj2)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj2, YesNo.No);

			if(bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.ContactTab,AS_ContactName4 , 30))
			{
				log(LogStatus.INFO, AS_ContactName4+" reocrd has been open", YesNo.No);

				ArrayList<String> result=bp.verifyRecordOnTagged(Companies, People, Deals);
				if(result.isEmpty())
				{
					log(LogStatus.INFO, "Records on Company, People and Deals Tagged have been matched", YesNo.No);
					sa.assertTrue(true,  "Records on Company, People and Deals Tagged have been matched");		
				}
				else
				{
					log(LogStatus.ERROR, "Records on Company, People and Deals Tagged are not matched", YesNo.No);
					sa.assertTrue(false,  "Records on Company, People and Deals Tagged are not matched");	
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to open "+AS_ContactName4+" record", YesNo.No);
				sa.assertTrue(false,  "Not able to open "+AS_ContactName4+" record");	
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on "+tabObj2+" tab", YesNo.No);
			sa.assertTrue(false,  "Not able to click on "+tabObj2+" tab");	
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	
	@Parameters({ "projectName" })

	@Test
	public void ASTc008_VerifyInteractionCard(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
		String subjectName=AS_ATSubject1;
		//String notes=AS_ATNotes1;
		String notes="For verification of Task after Edit";
		
		
		String[] tag=AS_ATRelatedTo1.split("<break>");
		String[][] basicSection= {{"Notes",AS_ATNotes4}};
		
		String[] suggestedTag=new String[1];
		suggestedTag[0]=AS_ATSuggestedTag3;

		lp.CRMLogin(superAdminUserName, adminPassword, appName);


		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if(bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,AS_FirmLegalName6 , 30))
			{
				log(LogStatus.INFO, AS_FirmLegalName6+" reocrd has been open", YesNo.No);

				String xPath="//a[text()='"+subjectName+"']/../preceding-sibling::div//button[@title='Edit Note']";
				WebElement ele=CommonLib.FindElement(driver, xPath, "email", action.SCROLLANDBOOLEAN, 30);
				String url=getURL(driver, 10);

				if(click(driver, ele, xPath, action.SCROLLANDBOOLEAN))
				{
					log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

					ThreadSleep(10000);
					ArrayList<String> result= bp.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url,subjectName, notes,tag);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "Notes Popup has been verified and Notes popup is opening in same page with prefilled value", YesNo.No);
						sa.assertTrue(true, "Notes Popup has been verified and Notes popup is opening in same page with prefilled value");
						refresh(driver);
						ThreadSleep(2000);
						if(click(driver, ele, xPath, action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
							String[] test= {"Int Institution","Int Company"};
							if(bp.updateActivityTimelineRecord(projectName, basicSection, null, null, suggestedTag, test))
							{
								log(LogStatus.INFO, "Activity Timeline has been updated", YesNo.No);
								sa.assertTrue(true, "Activity Timeline has been updated");
							}
							else
							{
								log(LogStatus.ERROR, "Not able to update activity timeline", YesNo.No);
								sa.assertTrue(false, "Not able to update activity timeline");
							}
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}
					}
					else
					{
						log(LogStatus.ERROR, "Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value", YesNo.No);
						sa.assertTrue(false, "Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value");
					}
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
					sa.assertTrue(false, "Not able to click on Edit Note button");
				}

			}
			else
			{
				log(LogStatus.ERROR, "Not able to open "+AS_FirmLegalName6+" record", YesNo.No);
				sa.assertTrue(false,  "Not able to open "+AS_FirmLegalName6+" record");	
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on "+tabObj1+" tab", YesNo.No);
			sa.assertTrue(false,  "Not able to click on "+tabObj1+" tab");	
		}
		
		
		
		String subjectName1=AS_ATSubject2;
		String notes1=AS_ATNotes2;
		String[] tag1=AS_ATRelatedTo2.split("<break>");
		String[][] basicSection1= {{"Notes",AS_ATNotes5}};
		String[] suggestedTag1=new String[1];
		suggestedTag1[0]=AS_ATSuggestedTag4;
		
		
		if (lp.clickOnTab(projectName, tabObj1)) {

			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);

			if(bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.InstituitonsTab,AS_FirmLegalName6 , 30))
			{
				log(LogStatus.INFO, AS_FirmLegalName6+" reocrd has been open", YesNo.No);

				String xPath="//a[text()='"+subjectName1+"']/../preceding-sibling::div//button[@title='Add Note']";
				WebElement ele=CommonLib.FindElement(driver, xPath, "email", action.SCROLLANDBOOLEAN, 30);
				String url=getURL(driver, 10);

				if(click(driver, ele, xPath, action.SCROLLANDBOOLEAN))
				{
					log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);

					ThreadSleep(10000);
					ArrayList<String> result= bp.verifyNotesPopupWithPrefilledValueAndOnSameUrl(url,subjectName1, notes1,tag1);
					if(result.isEmpty())
					{
						log(LogStatus.INFO, "Notes Popup has been verified and Notes popup is opening in same page with prefilled value", YesNo.No);
						sa.assertTrue(true, "Notes Popup has been verified and Notes popup is opening in same page with prefilled value");
						refresh(driver);
						ThreadSleep(2000);
						if(click(driver, ele, xPath, action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, "clicked on Edit Note button", YesNo.No);
							if(bp.updateActivityTimelineRecord(projectName,basicSection1, null, null, suggestedTag1,null))
							{
								log(LogStatus.INFO, "Activity Timeline has been updated", YesNo.No);
								sa.assertTrue(true, "Activity Timeline has been updated");
							}
							else
							{
								log(LogStatus.ERROR, "Not able to update activity timeline", YesNo.No);
								sa.assertTrue(false,   "Not able to update activity timeline");
							}

						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
							sa.assertTrue(false, "Not able to click on Edit Note button");
						}
					}
					else
					{
						log(LogStatus.ERROR, "Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value", YesNo.No);
						sa.assertTrue(false, "Notes Popup is not verify. Either Notes popup is not opening in same page or with prefilled value");
					}
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on Edit Note button", YesNo.No);
					sa.assertTrue(false, "Not able to click on Edit Note button");
				}

			}
			else
			{
				log(LogStatus.ERROR, "Not able to open "+AS_FirmLegalName6+" record", YesNo.No);
				sa.assertTrue(false,  "Not able to open "+AS_FirmLegalName6+" record");	
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on "+tabObj1+" tab", YesNo.No);
			sa.assertTrue(false,  "Not able to click on "+tabObj1+" tab");	
		}
			
		lp.CRMlogout();
		sa.assertAll();
	}
	
	


}
