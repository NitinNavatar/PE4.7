package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.SmokeCommonVariables.Smoke_TaskSTD1Subject;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;
import static com.navatar.generic.SmokeCommonVariables.crmUser1EmailID;
import static com.navatar.generic.SmokeCommonVariables.crmUser1FirstName;
import static com.navatar.generic.SmokeCommonVariables.crmUser1LastName;
import static com.navatar.generic.SmokeCommonVariables.dayAfterTomorrowsDate;
import static com.navatar.generic.SmokeCommonVariables.superAdminUserName;
import static com.navatar.generic.SmokeCommonVariables.todaysDate;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.navatar.generic.BaseLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.Buttons;
import com.navatar.generic.EnumConstants.CollapseExpandIcon;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.GlobalActionItem;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.Operator;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.SDGGridName;
import com.navatar.generic.EnumConstants.SDGGridSideIcons;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.CustomObjPageBusinessLayer;
import com.navatar.pageObjects.FundRaisingPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.GlobalActionPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;

public class PECloudSmoke extends BaseLib{
	
	@Parameters({ "projectName"})
	@Test
	public void smokeTc001_1_CreateCRMUser(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(crmUser1LastName);
		String UserLastName = splitedUserLastName[0] + lp.generateRandomNumber();
		String emailId = lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User1",excelLabel.User_Last_Name);
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
					if (setup.createPEUser( crmUser1FirstName, UserLastName, emailId, crmUserLience,
							crmUserProfile)) {
						log(LogStatus.INFO, "CRM User is created Successfully: " + crmUser1FirstName + " " + UserLastName, YesNo.No);
						ExcelUtils.writeData(testCasesFilePath, emailId, "Users", excelLabel.Variable_Name, "User1",
								excelLabel.User_Email);
						ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User1",
								excelLabel.User_Last_Name);
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
			
			if(!environment.equalsIgnoreCase(Environment.Sandbox.toString())) {
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
		}else{

			log(LogStatus.ERROR, "could not click on setup link, test case fail", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link, test case fail");

		}

		lp.CRMlogout();
		closeBrowser();
		config(ExcelUtils.readDataFromPropertyFile("Browser"));
		lp = new LoginPageBusinessLayer(driver);
		String passwordResetLink=null;
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
			appLog.info("Password is set successfully for CRM User1: " + crmUser1FirstName + " " + UserLastName );
		} else {
			appLog.info("Password is not set for CRM User1: " + crmUser1FirstName + " " + UserLastName);
			sa.assertTrue(false, "Password is not set for CRM User1: " + crmUser1FirstName + " " + UserLastName);
			log(LogStatus.ERROR, "Password is not set for CRM User1: " + crmUser1FirstName + " " + UserLastName,
					YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void smokeTc001_2_Prerequisite(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		String[][] userAndPassword = {{superAdminUserName,adminPassword},{crmUser1EmailID,adminPassword}};
		for (String[] userPass : userAndPassword) {
			lp.CRMLogin(userPass[0], userPass[1], appName);


			String addRemoveTabName="";
			String tab1="";
			if (tabObj1.equalsIgnoreCase("Entity")){
				tab1="Entitie";
			}
			else{
				tab1=tabObj1;
			}
			addRemoveTabName=tab1+"s,"+tabObj2+"s,"+tabObj3+"s,"+tabObj4+"s,"+"Marketing Event"+","+"Fundraisings"+","+"Maketing Inititatives"+","+"Navatar Setup"+","+"Reports"+","+"Dashboard";
			if (lp.addTab_Lighting( addRemoveTabName, 5)) {
				log(LogStatus.INFO,"Tab added : "+addRemoveTabName,YesNo.No);
			} else {
				log(LogStatus.FAIL,"Tab not added : "+addRemoveTabName,YesNo.No);
				sa.assertTrue(false, "Tab not added : "+addRemoveTabName);
			}	
		}
	}
				
	@Parameters({"projectName"})
	@Test
	public void smokeTc002_verifyOrgVersion(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentWindow=null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		
		if(home.clickOnSetUpLink()){
			parentWindow= switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create Fields object Entity for object");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create Fields object Entity for object",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create Fields object Entity for object");
			}
			ThreadSleep(3000);
			String verison="1.1176 (Beta 15)";
			if(setup.checkInstalledPackageVersion(verison,30)){
				log(LogStatus.PASS, "Installed package verison:"+verison+" successfully verified", YesNo.No);
				
			}else{
				sa.assertTrue(false,"Installed package verison:"+verison+" is not verified");
				log(LogStatus.FAIL, "Installed package verison:"+verison+" is not verified", YesNo.No);
			}
			driver.close();
			driver.switchTo().window(parentWindow);		
		}else{
			sa.assertTrue(false,"Not able to click on setup link");
			log(LogStatus.FAIL, "Not able to click on setup link", YesNo.Yes);
		}
		
		lp.CRMlogout();
		sa.assertAll();
		
	}

	@Parameters({"projectName"})
	@Test
	public void smokeTc003_verifyHomepageLogoAndTabs(String projectName){
		
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		LoginPageBusinessLayer lp= new LoginPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String tabs	=tabObj1+"s,"+tabObj2+"s,"+tabObj3+"s,"+tabObj4+"s,"+"Marketing Event"+","+"Fundraisings"+",Maketing Inititatives"+",Navatar Setup"+",Reports,Dashboard";

		if(mode.equalsIgnoreCase(Mode.Lightning.toString())){
			
			if(home.verifyHomepageAppLogo(appName)){
			
				log(LogStatus.PASS, "App logo in successfully verified on homepage", YesNo.No);
			}else{
			
				log(LogStatus.FAIL, "App logo in not verified on homepage", YesNo.No);
				sa.assertTrue(false,"App logo in not verified on homepage");
			}
		}
		
		if(home.verifyAddedTabsInHomepage(tabs,mode)){
			
			log(LogStatus.PASS, "App logo in successfully verified on homepage", YesNo.No);
		}else{
			
			log(LogStatus.FAIL, "App logo in not verified on homepage", YesNo.No);
			sa.assertTrue(false,"App logo in not verified on homepage");
		}
				
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({"projectName"})
	@Test
	public void smokeTc004_verifyHomepageSDGAndComponent(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		WebElement[] ele = { home.sdgGridHeaderName(SDGGridName.Deals, 5),
				home.sdgGridHeaderName(SDGGridName.Fundraising, 5),
				home.sdgGridHeaderName(SDGGridName.My_Call_List, 5) };
		String[] labelName = { "Deal", "FundRaising", "My Call List" };
		for (int i = 0; i < ele.length; i++) {
			if (ele[i] != null) {
				log(LogStatus.PASS, "SDG Grid is displaying " + labelName[i], YesNo.No);
			} else {
				log(LogStatus.FAIL, "SDG Grid is not displaying " + labelName[i], YesNo.Yes);
				sa.assertTrue(false, "SDG Grid is not displaying " + labelName[i]);
			}
		}
		WebElement[] ele1 = { home.sdgGridExpandCollpaseIcon(SDGGridName.Deals, CollapseExpandIcon.Expand, 5),
				home.sdgGridExpandCollpaseIcon(SDGGridName.Fundraising, CollapseExpandIcon.Expand, 5),
				home.sdgGridExpandCollpaseIcon(SDGGridName.My_Call_List, CollapseExpandIcon.Expand, 5) };
		for (int i = 0; i < ele1.length; i++) {
			if (ele1[i] != null) {
				log(LogStatus.PASS, "SDG Grid is expanded " + labelName[i], YesNo.No);
			} else {
				log(LogStatus.FAIL, "SDG Grid is not expanded " + labelName[i], YesNo.Yes);
				sa.assertTrue(false, "SDG Grid is not expanded " + labelName[i]);
			}
		}
		
		if(home.verifyTodaayTaskandTodayEventSectionVisibleOnHomepage(40)){
			
			log(LogStatus.PASS, "Today task and today event section is present on homepage", YesNo.No);
		}else{
			
			log(LogStatus.FAIL, "Today task and today event section is not present on homepage", YesNo.Yes);
			sa.assertTrue(false, "Today task and today event section is not present on homepage");
		}
		
		
		lp.CRMlogout();
		sa.assertAll();

		
	}
	
	@Parameters({"projectName"})
	@Test
	public void smokeTc005_createDataForHomepage(String projectName){
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp= new FundsPageBusinessLayer(driver);
		FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);
		GlobalActionPageBusinessLayer gp=new GlobalActionPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		
		String value="";
		String type="";
		String[][] EntityOrAccounts = {{ SMOKIns1InsName, SMOKIns1RecordType ,null} ,{ SMOKIns2InsName, SMOKIns2RecordType ,null},{ SMOKIns3InsName, SMOKIns3RecordType ,null}};

		//ins
		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, TabName.InstituitonsTab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.InstituitonsTab,YesNo.No);	
				value = accounts[0];
				type = accounts[1];
				if (ip.createEntityOrAccount(projectName, value, type, null, 20)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
			}
		}
		
		// contact
		
				if (lp.clickOnTab(projectName, TabName.ContactTab)) {
					log(LogStatus.INFO,"Click on Tab : "+TabName.ContactTab,YesNo.No);	
					
					SMOKCon1ContactEmail=	lp.generateRandomEmailId(gmailUserName);
					ExcelUtils.writeData(phase1DataSheetFilePath, SMOKCon1ContactEmail, "Contacts", excelLabel.Variable_Name, "SMOKCON1",excelLabel.Contact_EmailId);

					if (cp.createContact(projectName, SMOKCon1FirstName, SMOKCon1LastName, SMOKCon1InstitutionName, SMOKCon1ContactEmail,null, null, null, CreationPage.ContactPage, null,SMOKCon1Tier)) {
						log(LogStatus.INFO,"successfully Created Contact : "+SMOKCon1FirstName+" "+SMOKCon1LastName,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Contact : "+SMOKCon1FirstName+" "+SMOKCon1LastName);
						log(LogStatus.SKIP,"Not Able to Create Contact: "+SMOKCon1FirstName+" "+SMOKCon1LastName,YesNo.Yes);
					}


				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.ContactTab);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.ContactTab,YesNo.Yes);
				}
				
			//deal	
				String[][] otherlabel={{excelLabel.Source_Firm.toString(),SMOKDeal1SourceFirm},{excelLabel.Pipeline_Comments.toString(),SMOKDeal1PipelineComments}};
				if(lp.clickOnTab(projectName, TabName.DealTab)){
					log(LogStatus.INFO,"Click on Tab : "+TabName.DealTab,YesNo.No);
					
					if(fp.createDeal(projectName, null, SMOKDeal1DealName, SMOKDeal1CompanyName, SMOKDeal1Stage, otherlabel, 30)){
						
						log(LogStatus.INFO,"successfully Created deal : "+SMOKDeal1DealName,YesNo.No);	

					}else{
						
						sa.assertTrue(false,"Not Able to Create deal : "+SMOKDeal1DealName);
						log(LogStatus.SKIP,"Not Able to Create deal : "+SMOKDeal1DealName,YesNo.Yes);
					}
					
				}else{
					
					sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.DealTab);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.DealTab,YesNo.Yes);
				}
				
				
				// FR
				
				if(bp.clickOnTab(environment,mode,TabName.FundraisingsTab)) {
					if(fr.createFundRaising(environment,mode,SMOKFR1FundraisingName,SMOKFR1FundName,SMOKFR1InstitutionName)){
						appLog.info("fundraising is created : "+SMOKFR1FundraisingName);
					}else {
						appLog.error("Not able to create fundraising: "+SMOKFR1FundraisingName);
						sa.assertTrue(false, "Not able to create fundraising: "+SMOKFR1FundraisingName);
					}
				}else {
					appLog.error("Not able to click on fundraising tab so cannot create fundraising: "+SMOKFR1FundraisingName);
					sa.assertTrue(false,"Not able to click on fundraising tab so cannot create fundraising: "+SMOKFR1FundraisingName);
				}
				
				//task
				SMOKTask1DueDate=previousOrForwardMonthAccordingToTimeZone(-90, "M/d/YYYY", BasePageBusinessLayer.AmericaLosAngelesTimeZone);
				String task = SMOKTask1Subject;
				String[][] taskData = {{PageLabel.Subject.toString(),task},
						{PageLabel.Due_Date.toString(),M7Task1dueDate},
						{PageLabel.Name.toString(),SMOKTask1Name},
						{PageLabel.Status.toString(),SMOKTask1Status}};
				
					if (gp.clickOnGlobalActionAndEnterValue(projectName, GlobalActionItem.New_Task, taskData)) {
						log(LogStatus.INFO,"Able to Enter Value for : "+task,YesNo.No);
						ExcelUtils.writeData(phase1DataSheetFilePath,SMOKTask1DueDate, "Task1", excelLabel.Variable_Name, "SMOKTask1", excelLabel.Due_Date);
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
				
					lp.CRMlogout();
					sa.assertAll();
		
	}

	@Parameters("projectName")
	@Test
	public void smokeTc006_verifyCreatedDataOnHomepage(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		String labelName = SMOKDeal1DealName+","+SMOKDeal1Stage+","+SMOKDeal1SourceFirm+","+SMOKDeal1PipelineComments;
		
		if (click(driver, home.sdgGridSideIcons(SDGGridName.Deals, SDGGridSideIcons.Toggle_Filters, 10), "filter icon",
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "click on filter icon so going to search deal name : " + SMOKDeal1DealName, YesNo.Yes);
			if (home.SearchDealFilterDataOnHomePage(SDGGridName.Deals, "Deal", SMOKDeal1DealName, Operator.Equals,
					YesNo.Yes)) {
				log(LogStatus.PASS, "Search Deal Name in filter " + SMOKDeal1DealName, YesNo.No);
				ThreadSleep(3000);
				
				List<WebElement> all =home.sdgGridFirstRowData(SDGGridName.Deals);
				if(compareMultipleList(driver, labelName, all).isEmpty()){
					log(LogStatus.PASS, SMOKDeal1DealName+" Deal SDG Record is verified " , YesNo.No);
					
				}else{
					log(LogStatus.PASS, SMOKDeal1DealName+" Deal SDG Record is not matched ", YesNo.No);
					sa.assertTrue(false, SMOKDeal1DealName+" Deal SDG Record is not matched ");
				}
				
			} else {
				log(LogStatus.FAIL, "Not able to Search Deal Name in filter " + SMOKDeal1DealName, YesNo.No);
				sa.assertTrue(false, "Not able to Search Deal Name in filter " + SMOKDeal1DealName);
			}
		} else {
			log(LogStatus.FAIL, "Not able to click on filter icon so cannot search deal name : " + SMOKDeal1DealName,
					YesNo.Yes);
			sa.assertTrue(false, "Not able to click on filter icon so cannot search deal name : " + SMOKDeal1DealName);
		}

		
		 labelName = SMOKFR1FundraisingName+","+SMOKFR1Satge+","+SMOKFR1Closing+","+SMOKFR1InvestmentLikelyAmountMN+","+SMOKFR1Note;
		if (click(driver, home.sdgGridSideIcons(SDGGridName.Fundraising, SDGGridSideIcons.Toggle_Filters, 10),
				"filter icon", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "click on filter icon so cannot search FundRaising name : " + SMOKFR1FundraisingName, YesNo.Yes);
			if (home.SearchDealFilterDataOnHomePage(SDGGridName.Fundraising, "Fundraising", SMOKFR1FundraisingName, Operator.Equals,
					YesNo.Yes)) {
				log(LogStatus.PASS, "Search FundRaising Name in filter " + SMOKFR1FundraisingName, YesNo.No);
				ThreadSleep(3000);
				
				List<WebElement> all =home.sdgGridFirstRowData(SDGGridName.Fundraising);
				if(compareMultipleList(driver, labelName, all).isEmpty()){
					log(LogStatus.PASS, SMOKFR1FundraisingName+" fundraising SDG Record is verified " , YesNo.No);
					
				}else{
					log(LogStatus.FAIL, SMOKFR1FundraisingName+" fundraising SDG Record is not matched ", YesNo.No);
					sa.assertTrue(false, SMOKFR1FundraisingName+" fundraising SDG Record is not matched ");
				}				
			} else {
				log(LogStatus.FAIL, "Not able to Search FundRaising Name in filter " + SMOKFR1FundraisingName, YesNo.No);
				sa.assertTrue(false, "Not able to Search FundRaising Name in filter " + SMOKFR1FundraisingName);
			}
		} else {
			log(LogStatus.FAIL, "Not able to click on filter icon so cannot search FundRaising name : " + SMOKFR1FundraisingName,
					YesNo.Yes);
			sa.assertTrue(false, "Not able to click on filter icon so cannot search FundRaising name : " + SMOKFR1FundraisingName);
		}

		
		 labelName = SMOKCon1FirstName+" "+SMOKCon1LastName+","+SMOKCon1InstitutionName+","+SMOKCon1Phone;
		if (click(driver, home.sdgGridSideIcons(SDGGridName.My_Call_List, SDGGridSideIcons.Toggle_Filters, 10),
				"filter icon", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS,
					"click on filter icon so cannot search My Call List name : " + SMOKCon1FirstName + " " + SMOKCon1LastName,
					YesNo.Yes);
			if (home.SearchDealFilterDataOnHomePage(SDGGridName.My_Call_List, "Name", SMOKCon1FirstName + " " + SMOKCon1LastName,
					Operator.Equals, YesNo.Yes)) {
				log(LogStatus.PASS, "Search My Call List Name in filter " + SMOKCon1FirstName + " " + SMOKCon1LastName, YesNo.No);
				ThreadSleep(3000);

				List<WebElement> all =home.sdgGridFirstRowData(SDGGridName.My_Call_List);
				if(compareMultipleList(driver, labelName, all).isEmpty()){
					log(LogStatus.PASS, SMOKCon1FirstName + " " + SMOKCon1LastName+" my call list SDG Record is verified " , YesNo.No);
					
				}else{
					log(LogStatus.FAIL, SMOKCon1FirstName + " " + SMOKCon1LastName+" my call list SDG Record is not matched ", YesNo.No);
					sa.assertTrue(false, SMOKCon1FirstName + " " + SMOKCon1LastName+" my call list SDG Record is not matched ");
				}	
			} else {
				log(LogStatus.FAIL, "Not able to Search My Call List Name in filter " + SMOKCon1FirstName + " " + SMOKCon1LastName,
						YesNo.No);
				sa.assertTrue(false,
						"Not able to Search My Call List Name in filter " + SMOKCon1FirstName + " " + SMOKCon1LastName);
			}
		} else {
			log(LogStatus.FAIL, "Not able to click on filter icon so cannot search My Call List name : " + SMOKCon1FirstName
					+ " " + SMOKCon1LastName, YesNo.Yes);
			sa.assertTrue(false, "Not able to click on filter icon so cannot search My Call List name : " + SMOKCon1FirstName
					+ " " + SMOKCon1LastName);
		}
		
	}
	
	@Parameters("projectName")
	@Test
	public void smokeTc007_1_verifyWrenchIconOnHomepageSDG(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin( superAdminUserName, adminPassword );
		ThreadSleep(5000);
		if (click(driver, home.sdgGridSideIcons(SDGGridName.Deals, SDGGridSideIcons.Manage_fields, 5),
				"manage field icon", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "clicked on manage field icon of " + SDGGridName.Deals, YesNo.No);
			ThreadSleep(2000);
			List<WebElement> lst = home.sdgGridSelectVisibleFieldsListInManageFieldPopUp();

			for (int i = 0; i < 3; i++) {
				if (i == 0) {
					if (selectVisibleTextFromDropDown(driver,
							home.sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp(10),
							"drop down", "Deal Quality Score")) {
						log(LogStatus.PASS, "select Deal Quality Score text from  field finder", YesNo.No);
						ThreadSleep(2000);
						click(driver, home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(
								Buttons.Add, 10), "Add button", action.SCROLLANDBOOLEAN);

						if (click(driver,
								home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(
										Buttons.Save, 10),
								"Add button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.PASS, "clicked on save button for " + SDGGridName.Deals,
									YesNo.No);
							ThreadSleep(5000);

							lst = home.sdgGridHeadersLabelNameList(SDGGridName.Deals);
							String headerName = "Deal Quality Score";
							if (compareMultipleList(driver, headerName, lst).isEmpty()) {
								log(LogStatus.PASS,
										SDGGridName.Deals + " SDG Grid Header Name is verified ",
										YesNo.No);
							} else {
								log(LogStatus.FAIL,
										SDGGridName.Deals + " SDG Grid Header Name is not verified ",
										YesNo.Yes);
								sa.assertTrue(false,
										SDGGridName.Deals + " SDG Grid Header Name is not verified ");
							}
						} else {
							log(LogStatus.PASS,
									"Not able to click on save button for " + SDGGridName.Deals,
									YesNo.No);
							sa.assertTrue(false,
									"Not able to click on save button for " + SDGGridName.Deals);
						}

					} else {
						log(LogStatus.PASS, "Cannot select Deal Quality Score from field finder", YesNo.No);
						sa.assertTrue(false, "Cannot select Deal Quality Score from field finder");
					}

				}

				if (i == 1) {

					if (click(driver, home.sdgGridSideIcons(SDGGridName.Fundraising, SDGGridSideIcons.Manage_fields, 5),
							"manage field icon", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "clicked on manage field icon of " + SDGGridName.Fundraising, YesNo.No);
						ThreadSleep(2000);

						if (selectVisibleTextFromDropDown(driver,
								home.sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp(10),
								"drop down", "Fund Name")) {
							log(LogStatus.PASS, "select Fund Name text from field finder",
									YesNo.No);
							ThreadSleep(2000);

							click(driver, home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(
									Buttons.Add, 10), "Add button", action.SCROLLANDBOOLEAN);
							if (click(driver,
									home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(
											Buttons.Save, 10),
									"Add button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "clicked on save button for " + SDGGridName.Deals,
										YesNo.No);
								ThreadSleep(5000);

								lst = home.sdgGridHeadersLabelNameList(SDGGridName.Deals);
								String headerName = "Fund Name";
								if (compareMultipleList(driver, headerName, lst).isEmpty()) {
									log(LogStatus.PASS,
											SDGGridName.Deals + " SDG Grid Header Name is verified ",
											YesNo.No);
								} else {
									log(LogStatus.FAIL,
											SDGGridName.Deals + " SDG Grid Header Name is not verified ",
											YesNo.Yes);
									sa.assertTrue(false,
											SDGGridName.Deals + " SDG Grid Header Name is not verified ");
								}
							} else {
								log(LogStatus.PASS,
										"Not able to click on save button for " + SDGGridName.Deals,
										YesNo.No);
								sa.assertTrue(false,
										"Not able to click on save button for " + SDGGridName.Deals);
							}
						} else {
							log(LogStatus.PASS, "Cannot select Fund Name from field finder", YesNo.No);
							sa.assertTrue(false, "Cannot select Fund Name from field finder");
						}


					}else{
						log(LogStatus.PASS, "Cannot click on manage field", YesNo.No);
						sa.assertTrue(false, "Cannot click on manage field");
					}

				}

				if (i == 2) {

					if (click(driver, home.sdgGridSideIcons(SDGGridName.My_Call_List, SDGGridSideIcons.Manage_fields, 5),
							"manage field icon", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "clicked on manage field icon of " + SDGGridName.Deals, YesNo.No);
						ThreadSleep(2000);


						if (selectVisibleTextFromDropDown(driver,
								home.sdgGridSelectFieldToDisplayFieldFinderDropDownInManageFieldPopUp(10),
								"drop down", "Email")) {
							log(LogStatus.PASS, "select Email text from finder field", YesNo.No);
							ThreadSleep(2000);

							click(driver, home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(
									Buttons.Add, 10), "Add button", action.SCROLLANDBOOLEAN);

							if (click(driver, home.sdgGridSelectFieldToDisplaySaveCancelBtnInManageFieldPopUp(
									Buttons.Save, 10), "Add button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "clicked on save button for " + SDGGridName.Deals,
										YesNo.No);
								ThreadSleep(5000);

								lst = home.sdgGridHeadersLabelNameList(SDGGridName.Deals);
								String headerName = "Email";
								if (compareMultipleList(driver, headerName, lst).isEmpty()) {
									log(LogStatus.PASS,
											SDGGridName.Deals + " SDG Grid Header Name is verified ", YesNo.No);
								} else {
									log(LogStatus.FAIL,
											SDGGridName.Deals + " SDG Grid Header Name is not verified ",
											YesNo.Yes);
									sa.assertTrue(false,
											SDGGridName.Deals + " SDG Grid Header Name is not verified ");
								}
							} else {
								log(LogStatus.PASS, "Not able to click on save button for " + SDGGridName.Deals,
										YesNo.No);
								sa.assertTrue(false,
										"Not able to click on save button for " + SDGGridName.Deals);
							}

						} else {
							log(LogStatus.PASS, "Cannot select Email from field finder", YesNo.No);
							sa.assertTrue(false, "Cannot select Email from field finder");
						}
					}else{

						log(LogStatus.PASS, "Not able to click on manage field  for " + SDGGridName.Deals,
								YesNo.No);
						sa.assertTrue(false,
								"Not able to click on manage field  for " + SDGGridName.Deals);
					}

				}
			}


		} else {
			log(LogStatus.PASS, "Not able to click on manage field icon of " + SDGGridName.Deals, YesNo.No);
			sa.assertTrue(false, "Not able to click on manage field icon of " + SDGGridName.Deals);
		}
		lp.CRMlogout();
		sa.assertAll();	
	}
			
	@Parameters("projectName")
	@Test
	public void smokeTc007_2_verifyWrenchIconOnHomepageSDG(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin( crmUser1EmailID, adminPassword );
		ThreadSleep(5000);
		List<WebElement> lst;
		
		for(int i=0;i<3;i++){
			
			if(i==0){
				
				lst = home.sdgGridHeadersLabelNameList(SDGGridName.Deals);
				String headerName = "Deal Quality Score";
				if (compareMultipleList(driver, headerName, lst).isEmpty()) {
					log(LogStatus.PASS,
							SDGGridName.Deals + " SDG Grid Header Name is verified ",
							YesNo.No);
				} else {
					log(LogStatus.FAIL,
							SDGGridName.Deals + " SDG Grid Header Name is not verified ",
							YesNo.Yes);
					sa.assertTrue(false,
							SDGGridName.Deals + " SDG Grid Header Name is not verified ");
				}
			}else if(i==1){
				lst = home.sdgGridHeadersLabelNameList(SDGGridName.Fundraising);
				String headerName = "Fund Name";
				if (compareMultipleList(driver, headerName, lst).isEmpty()) {
					log(LogStatus.PASS,
							SDGGridName.Fundraising + " SDG Grid Header Name is verified ",
							YesNo.No);
				} else {
					log(LogStatus.FAIL,
							SDGGridName.Fundraising + " SDG Grid Header Name is not verified ",
							YesNo.Yes);
					sa.assertTrue(false,
							SDGGridName.Fundraising + " SDG Grid Header Name is not verified ");
				}
				
			}else if(i==2){
				lst = home.sdgGridHeadersLabelNameList(SDGGridName.My_Call_List);
				String headerName = "Email";
				if (compareMultipleList(driver, headerName, lst).isEmpty()) {
					log(LogStatus.PASS,
							SDGGridName.My_Call_List + " SDG Grid Header Name is verified ",
							YesNo.No);
				} else {
					log(LogStatus.FAIL,
							SDGGridName.My_Call_List + " SDG Grid Header Name is not verified ",
							YesNo.Yes);
					sa.assertTrue(false,
							SDGGridName.My_Call_List + " SDG Grid Header Name is not verified ");
				}
			}
			
			
		}
		log(LogStatus.PASS,
				"All SDG Grid Header Name is verified ",
				YesNo.No);
		lp.CRMlogout();
		sa.assertAll();
	}
		
	@Parameters("projectName")
	@Test
	public void smokeTc008_createDataForTodaysTaskAndTodaysEvent(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		WebElement ele = null;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		ThreadSleep(5000);
		SMOKTask2DueDate =todaysDate;
		
		 SMOKTask2Subject = ExcelUtils.readData(phase1DataSheetFilePath, "Task1", excelLabel.Variable_Name,
				"SMOKTask2" , excelLabel.Subject);
		SMOKTask2Name = ExcelUtils.readData(phase1DataSheetFilePath, "Task1", excelLabel.Variable_Name,
				"SMOKTask2" , excelLabel.Name);
		
		if (click(driver, gp.getGlobalActionIcon(projectName, 15), "Global Action Related item", action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Global Action Related item", YesNo.No);
			if (clickUsingJavaScript(driver, gp.getActionItem(projectName, GlobalActionItem.New_Task, 15),
					"New_Task Link", action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on New task Link", YesNo.Yes);
				ThreadSleep(2000);
				// subject
				ele = gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Task,
						PageLabel.Subject.toString(), 10);
				if (sendKeys(driver, ele, SMOKTask2Subject, "Subject", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Entered value to Subject Text Box : " + SMOKTask2Subject, YesNo.Yes);
					ThreadSleep(1000);

					// Due Date
					if (sendKeys(driver, ip.getdueDateTextBoxInNewTask(projectName, 20), SMOKTask2DueDate,
							PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Entered value to Due Date Text Box : " + SMOKTask2DueDate, YesNo.Yes);
						ThreadSleep(1000);

						// Name
						ele = cp.getLabelTextBoxForNameOrRelatedAssociationOnTask(projectName, PageName.TaskPage,
								PageLabel.Name.toString(), action.SCROLLANDBOOLEAN, 15);
						if (sendKeys(driver, ele, SMOKTask2Name, "Name Text Label", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Enter Value to Name Text Box : " + SMOKTask2Name, YesNo.Yes);
							ThreadSleep(1000);
							ele = cp.getContactNameOrRelatedAssociationNameOnTask(projectName, PageName.TaskPage,
									PageLabel.Name.toString(), SMOKTask2Name, action.SCROLLANDBOOLEAN, 15);
							if (click(driver, ele, "Select Name From Label : " + PageLabel.Name,
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on : " + SMOKTask2Name, YesNo.Yes);

								if (clickUsingJavaScript(driver, gp.getSaveButtonForEvent(projectName, 20), "save",
										action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "successfully created task : " + SMOKTask2Subject, YesNo.Yes);
									ThreadSleep(1000);
									ExcelUtils.writeData(phase1DataSheetFilePath, SMOKTask2DueDate, "Task1", excelLabel.Variable_Name,
											"SMOKTask2", excelLabel.Due_Date);
								} else {
									log(LogStatus.ERROR,
											"save button is not clickable so task not created : " + Smoke_TaskSTD1Subject,
											YesNo.Yes);
									sa.assertTrue(false,
											"save button is not clickable so task not created : " + Smoke_TaskSTD1Subject);
								}
							} else {
								sa.assertTrue(false, "Not Able to Click on : " + SMOKTask2Name);
								log(LogStatus.SKIP, "Not Able to Click on : " + SMOKTask2Name, YesNo.Yes);
							}



						} else {
							sa.assertTrue(false, "Not Able to Enter Value to Name Text Box : " + SMOKTask2Name);
							log(LogStatus.SKIP, "Not Able to Enter Value to Name Text Box : " + SMOKTask2Name, YesNo.Yes);
						}

					}else{
						sa.assertTrue(false, "Not Able to Enter Value to due date Text Box : " + SMOKTask2DueDate);
						log(LogStatus.SKIP, "Not Able to Enter Value to due date Text Box : " + SMOKTask2DueDate, YesNo.Yes);

					}
				}else{

					sa.assertTrue(false, "Not Able to Enter Value to subject Text Box : " + SMOKTask2Subject);
					log(LogStatus.SKIP, "Not Able to Enter Value to subject Text Box : " + SMOKTask2Subject, YesNo.Yes);

				}
			}else{

				sa.assertTrue(false, "Not Able to Clicked on New task Link");
				log(LogStatus.SKIP, "Not Able to Clicked on New task Link", YesNo.Yes);
			}

		}else{
			sa.assertTrue(false, "Not Able to Clicked on Global Action Related item");
			log(LogStatus.SKIP, "Not Able to Clicked on Global Action Related item", YesNo.Yes);
		}

		
		//event
		if (click(driver, gp.getGlobalActionIcon(projectName, 15), "Global Action Related item", action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Global Action Related item", YesNo.No);
			if (clickUsingJavaScript(driver, gp.getActionItem(projectName, GlobalActionItem.New_Event, 15),
					"New Event Link", action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on New Event Link", YesNo.Yes);
				ThreadSleep(2000);

				click(driver, gp.getMaximizeIcon(projectName, 15), "Maximize Icon", action.BOOLEAN);
				SMOKEvent1StartDate = todaysDate;
				SMOKEvent1EndDate = todaysDate;

				SMOKEvent1Subject = ExcelUtils.readData(phase1DataSheetFilePath, "Events",
						excelLabel.Variable_Name, "SMOKEvent1", excelLabel.Subject);

				SMOKEvent1Name = ExcelUtils.readData(phase1DataSheetFilePath, "Events", excelLabel.Variable_Name,
						"SMOKEvent1" , excelLabel.Name);


				ExcelUtils.writeData(phase1DataSheetFilePath, SMOKEvent1StartDate, "Events", excelLabel.Variable_Name,
						"SMOKEvent1", excelLabel.Start_Date);
				ExcelUtils.writeData(phase1DataSheetFilePath, SMOKEvent1EndDate, "Events", excelLabel.Variable_Name,
						"SMOKEvent1", excelLabel.End_Date);

				String[][] event1 = { { PageLabel.Subject.toString(), SMOKEvent1Subject },
						{ PageLabel.Start_Date.toString(), SMOKEvent1StartDate }, { PageLabel.End_Date.toString(), SMOKEvent1EndDate },
						{ PageLabel.Name.toString(), SMOKEvent1Name } };

				if( gp.enterValueForNewEvent(projectName, GlobalActionItem.New_Event, event1, 10)){
					
					log(LogStatus.PASS, "Successfully entered all the data to event", YesNo.Yes);
					if (click(driver, gp.getSaveButtonForEvent(projectName, 10), "Save Button",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Click on Save Button For Event : " + SMOKEvent1Subject,
								YesNo.No);
						ThreadSleep(500);
					} else {
						sa.assertTrue(false,
								"Not Able to Click on Save Button For Event : " + SMOKEvent1Subject);
						log(LogStatus.SKIP,
								"Not Able to Click on Save Button For Event : " + SMOKEvent1Subject,
								YesNo.Yes);
					}
					
				}else{
					sa.assertTrue(false, "Not Able to Enter  all the data to event");
					log(LogStatus.SKIP, "Not Able to  entered all the data to event", YesNo.Yes);
				}
			}else{
				sa.assertTrue(false, "Not Able to Clicked on New Event Link");
				log(LogStatus.SKIP, "Not Able to Clicked on New Event Link", YesNo.Yes);

			}
		}else{

			sa.assertTrue(false, "Not Able to Clicked on Global Action Related item");
			log(LogStatus.SKIP, "Not Able to Clicked on Global Action Related item", YesNo.Yes);
		}

	}
	
	
	@Parameters("projectName")
	@Test
	public void smokeTc009_verifyCreateDataForTodaysTaskAndTodaysEvent(String projectName){
		
		
		
	}
	
}
	

