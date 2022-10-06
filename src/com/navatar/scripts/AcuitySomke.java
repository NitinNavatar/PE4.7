package com.navatar.scripts;

import static com.navatar.generic.CommonLib.exit;
import static com.navatar.generic.CommonLib.log;
import static com.navatar.generic.CommonLib.removeNumbersFromString;
import static com.navatar.generic.CommonLib.switchOnWindow;
import static com.navatar.generic.CommonLib.switchToDefaultContent;
import static com.navatar.generic.CommonVariables.AS_ContactEmail;
import static com.navatar.generic.CommonVariables.AS_ContactFirstName;
import static com.navatar.generic.CommonVariables.AS_ContactLastName;
import static com.navatar.generic.CommonVariables.AS_ContactLegalName;
import static com.navatar.generic.CommonVariables.AS_DealCompany;
import static com.navatar.generic.CommonVariables.AS_DealName;
import static com.navatar.generic.CommonVariables.AS_DealStage;
import static com.navatar.generic.CommonVariables.AS_FirmLegalName1;
import static com.navatar.generic.CommonVariables.AS_FirmRecordType1;
import static com.navatar.generic.CommonVariables.AS_FundInvestmentCategory;
import static com.navatar.generic.CommonVariables.AS_FundName;
import static com.navatar.generic.CommonVariables.AS_FundType;
import static com.navatar.generic.CommonVariables.adminPassword;
import static com.navatar.generic.CommonVariables.appName;
import static com.navatar.generic.CommonVariables.crmUser1FirstName;
import static com.navatar.generic.CommonVariables.crmUser1LastName;
import static com.navatar.generic.CommonVariables.crmUserLience;
import static com.navatar.generic.CommonVariables.crmUserProfile;
import static com.navatar.generic.CommonVariables.environment;
import static com.navatar.generic.CommonVariables.gmailUserName;
import static com.navatar.generic.CommonVariables.superAdminUserName;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.GlobalActionItem;
import com.navatar.generic.EnumConstants.NavigationMenuItems;
import com.navatar.generic.EnumConstants.PageName;
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
import com.navatar.pageObjects.NavigationPageBusineesLayer;
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

		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
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

		String companyRecord=accountName[1];
		
		int status=0;

		
		String[][] basicsection= {{"Subject","test"},{"Notes","it is teasting data"},{"Related_To","Int Institution<break>Int Intermediary"}};
		String[][] advanceSection= {{"Priority","High"}};        String[][] taskSection= {{"Subject","ABC"},{"Due Date Only","06/04/2020"},{"Status","In Progress"}};
		
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String navigationMenuName="Meeting";

		/*
		 * NavigationPageBusineesLayer npbl= new NavigationPageBusineesLayer(driver);
		 * boolean flag=false; if (npbl.clickOnNavatarEdgeLinkHomePage(projectName,
		 * NavigationMenuItems.Create.toString(), action.BOOLEAN, 30)) {
		 * log(LogStatus.INFO,
		 * "Able to Click on "+navigationMenuName+" Going to click on : "
		 * +NavigationMenuItems.Create.toString()+" for creation ", YesNo.No); ele =
		 * npbl.getNavigationLabel(projectName, navigationMenuName, action.BOOLEAN, 10);
		 * if (ele!=null && CommonLib.click(driver, ele, navigationMenuName,
		 * action.BOOLEAN)) { log(LogStatus.INFO,
		 * "Click on "+navigationMenuName+" so going for creation", YesNo.No); flag =
		 * true; } else { log(LogStatus.ERROR, "Not Able to Click on "
		 * +navigationMenuName+" so cannot create data related to this ", YesNo.Yes);
		 * sa.assertTrue(false,"Not Able to Click on "
		 * +navigationMenuName+" so cannot create data related to this "); } } else {
		 * log(LogStatus.ERROR,
		 * "Not Able to Click on "+NavigationMenuItems.Create.toString()
		 * +" so cannot click on : "+navigationMenuName+" for creation ", YesNo.Yes);
		 * sa.assertTrue(false,"Not Able to Click on "+NavigationMenuItems.Create.
		 * toString()+" so cannot click on : "+navigationMenuName+" for creation "); }
		 */
        
		bp.createActivityTimeline(projectName,true,"Task", basicsection, advanceSection,taskSection);
		System.err.println("donnnnnnnnnnneeeeeeeeeeeeeeeeeeeeeeeeeeeeee...................................................................");
		CommonLib.ThreadSleep(50000);
/*
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
							log(LogStatus.INFO, dealName[i]+" deal is not created", YesNo.No);
							sa.assertTrue(true,  dealName[i]+" deal is not created");	
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
										
										
									}
									else
									{
										log(LogStatus.ERROR, "Not able to click on Communications tab", YesNo.No);
									}
									
								}
								else
								{
									log(LogStatus.ERROR, "Not able to open "+companyRecord+" reocrd", YesNo.No);
								}
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on Tab : " + tabObj1, YesNo.No);
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
					log(LogStatus.FAIL, "Deal records are not created", YesNo.No);
					sa.assertTrue(false,  "Deal records are not created");
				}

			}
			else
			{
				log(LogStatus.FAIL, "Contact records are not created", YesNo.No);
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

*/
	}


}
