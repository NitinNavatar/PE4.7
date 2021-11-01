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
import static com.navatar.pageObjects.NavigationPageBusineesLayer.navigationParentLabelWithChildAndOrder;
import static com.navatar.pageObjects.NavigationPageBusineesLayer.navigationParentLabelWithChildSorted;
import static com.navatar.pageObjects.NavigationPageBusineesLayer.navigationParentLabelWithOrder;
import static com.navatar.pageObjects.NavigationPageBusineesLayer.sortByValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.Logs;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.navatar.generic.BaseLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.ActivityTimeLineItem;
import com.navatar.generic.EnumConstants.BulkActions_DefaultValues;
import com.navatar.generic.EnumConstants.Buttons;
import com.navatar.generic.EnumConstants.CSVLabel;
import com.navatar.generic.EnumConstants.CollapseExpandIcon;
import com.navatar.generic.EnumConstants.CommitmentType;
import com.navatar.generic.EnumConstants.CreateNew_DefaultValues;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.EmailTemplateType;
import com.navatar.generic.EnumConstants.EnableDisable;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.FolderAccess;
import com.navatar.generic.EnumConstants.FundraisingContactPageTab;
import com.navatar.generic.EnumConstants.GlobalActionItem;
import com.navatar.generic.EnumConstants.IndiviualInvestorFieldLabel;
import com.navatar.generic.EnumConstants.InstitutionPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.NavatarSetupSideMenuTab;
import com.navatar.generic.EnumConstants.NavigationMenuItems;
import com.navatar.generic.EnumConstants.NewInteractions_DefaultValues;
import com.navatar.generic.EnumConstants.Operator;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.PopUpName;
import com.navatar.generic.EnumConstants.ReportDashboardFolderType;
import com.navatar.generic.EnumConstants.ReportField;
import com.navatar.generic.EnumConstants.ReportFormatName;
import com.navatar.generic.EnumConstants.SDGGridName;
import com.navatar.generic.EnumConstants.SDGGridSideIcons;
import com.navatar.generic.EnumConstants.SearchBasedOnExistingFundsOptions;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.Task;
import com.navatar.generic.EnumConstants.TopOrBottom;
import com.navatar.generic.EnumConstants.ViewAllAndViewCalendarLink;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.searchContactInEmailProspectGrid;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.CustomObjPageBusinessLayer;
import com.navatar.pageObjects.EmailMyTemplatesPageBusinessLayer;
import com.navatar.pageObjects.FundRaisingPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.GlobalActionPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.NavatarSetupPageBusinessLayer;
import com.navatar.pageObjects.NavigationPageBusineesLayer;
import com.navatar.pageObjects.ReportsTabBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.navatar.pageObjects.TaskPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;

public class PECloudSmoke extends BaseLib{
	
	String navigationMenuName=NavigationMenuItems.New_Interactions.toString();
	String navigationTab="Navigation";
	
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
		String tabs	=tabObj1+"s,"+tabObj2+"s,"+tabObj3+"s,"+tabObj4+"s,"+"Marketing Event"+","+"Fundraisings"+",Maketing Inititatives"+",Navatar Setup"+",Reports,Dashboard"+",Navigation";

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
				if (ip.createEntityOrAccount(projectName, mode, value, type, null, null, 20)) {
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
		
		
		ThreadSleep(2000);
		// contact
		
				if (bp.clickOnTab(environment,mode, TabName.ContactTab)) {
					log(LogStatus.INFO,"Click on Tab : "+TabName.ContactTab,YesNo.No);	
					
					SMOKCon1ContactEmail=	lp.generateRandomEmailId(gmailUserName);
					ExcelUtils.writeData(phase1DataSheetFilePath, SMOKCon1ContactEmail, "Contacts", excelLabel.Variable_Name, "SMOKCON1",excelLabel.Contact_EmailId);

					if (cp.createContact(projectName, SMOKCon1FirstName, SMOKCon1LastName, SMOKCon1InstitutionName, SMOKCon1ContactEmail,"", null, null, CreationPage.ContactPage, null,SMOKCon1Tier)) {
						log(LogStatus.INFO,"successfully Created Contact : "+SMOKCon1FirstName+" "+SMOKCon1LastName,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Contact : "+SMOKCon1FirstName+" "+SMOKCon1LastName);
						log(LogStatus.SKIP,"Not Able to Create Contact: "+SMOKCon1FirstName+" "+SMOKCon1LastName,YesNo.Yes);
					}


				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.ContactTab);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.ContactTab,YesNo.Yes);
				}
				
				
				ThreadSleep(2000);
				
			//deal	
				String[][] otherlabel={{excelLabel.Source_Firm.toString(),SMOKDeal1SourceFirm},{excelLabel.Pipeline_Comments.toString(),SMOKDeal1PipelineComments}};
				if(bp.clickOnTab(environment,mode, TabName.DealTab)){
					log(LogStatus.INFO,"Click on Tab : "+TabName.DealTab,YesNo.No);
					
					if(fp.createDeal(projectName, "", SMOKDeal1DealName, SMOKDeal1CompanyName, SMOKDeal1Stage, otherlabel, 30)){
						
						log(LogStatus.INFO,"successfully Created deal : "+SMOKDeal1DealName,YesNo.No);	

					}else{
						
						sa.assertTrue(false,"Not Able to Create deal : "+SMOKDeal1DealName);
						log(LogStatus.SKIP,"Not Able to Create deal : "+SMOKDeal1DealName,YesNo.Yes);
					}
					
				}else{
					
					sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.DealTab);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.DealTab,YesNo.Yes);
				}
				
				ThreadSleep(2000);
				//fund
				if (bp.clickOnTab(environment,mode, TabName.FundsTab)) {
					log(LogStatus.INFO,"Click on Tab : "+TabName.FundsTab,YesNo.No);	
					if (fp.createFundPE(projectName,SMOKFund1FundName,"",SMOKFund1FundType,SMOKFund1InvestmentCategory, null, 15)) {
						log(LogStatus.INFO,"Created Fund : "+SMOKFund1FundName,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Fund : "+SMOKFund1FundName);
						log(LogStatus.SKIP,"Not Able to Create Fund  : "+SMOKFund1FundName,YesNo.Yes);
					}

				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.FundsTab);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.FundsTab,YesNo.Yes);
				}
				
				ThreadSleep(2000);
				
				// FR
				
				if(bp.clickOnTab(environment,mode,TabName.FundraisingsTab)) {
					if(fr.createFundRaising(environment,mode,SMOKFR1FundraisingName,SMOKFR1FundName,SMOKFR1InstitutionName, SMOKFR1Closing, SMOKFR1Satge, SMOKFR1InvestmentLikelyAmountMN, SMOKFR1Note)){
						appLog.info("fundraising is created : "+SMOKFR1FundraisingName);
					}else {
						appLog.error("Not able to create fundraising: "+SMOKFR1FundraisingName);
						sa.assertTrue(false, "Not able to create fundraising: "+SMOKFR1FundraisingName);
					}
				}else {
					appLog.error("Not able to click on fundraising tab so cannot create fundraising: "+SMOKFR1FundraisingName);
					sa.assertTrue(false,"Not able to click on fundraising tab so cannot create fundraising: "+SMOKFR1FundraisingName);
				}
				ThreadSleep(2000);
				
				//task
				SMOKTask1DueDate=previousOrForwardDateAccordingToTimeZone(-92, "M/d/YYYY", BasePageBusinessLayer.AmericaLosAngelesTimeZone);
				String task = SMOKTask1Subject;
				String[][] taskData = {{PageLabel.Subject.toString(),task},
						{PageLabel.Due_Date.toString(),SMOKTask1DueDate},
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
				ThreadSleep(7000);
				
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
		
		String changedInvestmentAmount=changeNumberIntoUSDollarFormat(SMOKFR1InvestmentLikelyAmountMN);
		 labelName = SMOKFR1FundraisingName+","+SMOKFR1Satge+","+SMOKFR1Closing+","+(changedInvestmentAmount)+","+SMOKFR1Note;
		if (click(driver, home.sdgGridSideIcons(SDGGridName.Fundraising, SDGGridSideIcons.Toggle_Filters, 10),
				"filter icon", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "click on filter icon so cannot search FundRaising name : " + SMOKFR1FundraisingName, YesNo.Yes);
			if (home.SearchDealFilterDataOnHomePage(SDGGridName.Fundraising, "Fundraising", SMOKFR1FundraisingName, Operator.Equals,
					YesNo.Yes)) {
				log(LogStatus.PASS, "Search FundRaising Name in filter " + SMOKFR1FundraisingName, YesNo.No);
				ThreadSleep(7000);
				
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

		
		 labelName = SMOKCon1FirstName+" "+SMOKCon1LastName+","+SMOKCon1InstitutionName;
		if (click(driver, home.sdgGridSideIcons(SDGGridName.My_Call_List, SDGGridSideIcons.Toggle_Filters, 10),
				"filter icon", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS,
					"click on filter icon so cannot search My Call List name : " + SMOKCon1FirstName + " " + SMOKCon1LastName,
					YesNo.Yes);
			if (home.SearchDealFilterDataOnHomePage(SDGGridName.My_Call_List, "Name", SMOKCon1FirstName + " " + SMOKCon1LastName,
					Operator.Equals, YesNo.Yes)) {
				log(LogStatus.PASS, "Search My Call List Name in filter " + SMOKCon1FirstName + " " + SMOKCon1LastName, YesNo.No);
				ThreadSleep(5000);

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
		
		lp.CRMlogout();
		sa.assertAll();
		
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
								log(LogStatus.PASS, "clicked on save button for " + SDGGridName.Fundraising,
										YesNo.No);
								ThreadSleep(5000);

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
							} else {
								log(LogStatus.PASS,
										"Not able to click on save button for " + SDGGridName.Fundraising,
										YesNo.No);
								sa.assertTrue(false,
										"Not able to click on save button for " + SDGGridName.Fundraising);
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
						log(LogStatus.PASS, "clicked on manage field icon of " + SDGGridName.My_Call_List, YesNo.No);
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

								lst = home.sdgGridHeadersLabelNameList(SDGGridName.My_Call_List);
								String headerName = "Email";
								if (compareMultipleList(driver, headerName, lst).isEmpty()) {
									log(LogStatus.PASS,
											SDGGridName.My_Call_List + " SDG Grid Header Name is verified ", YesNo.No);
								} else {
									log(LogStatus.FAIL,
											SDGGridName.My_Call_List + " SDG Grid Header Name is not verified ",
											YesNo.Yes);
									sa.assertTrue(false,
											SDGGridName.My_Call_List + " SDG Grid Header Name is not verified ");
								}
							} else {
								log(LogStatus.PASS, "Not able to click on save button for " + SDGGridName.My_Call_List,
										YesNo.No);
								sa.assertTrue(false,
										"Not able to click on save button for " + SDGGridName.My_Call_List);
							}

						} else {
							log(LogStatus.PASS, "Cannot select Email from field finder", YesNo.No);
							sa.assertTrue(false, "Cannot select Email from field finder");
						}
					}else{

						log(LogStatus.PASS, "Not able to click on manage field  for " + SDGGridName.My_Call_List,
								YesNo.No);
						sa.assertTrue(false,
								"Not able to click on manage field  for " + SDGGridName.My_Call_List);
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
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		home.clickOnTab(projectName, TabName.HomeTab);	
		ThreadSleep(5000);
		List<WebElement> name = home.ctreatedTaskAndEventListOnHomePage(Task.TodayTasks);
		
			if (compareMultipleList(driver, SMOKTask2Subject.toString(), name).isEmpty()) {
				log(LogStatus.PASS, "task is displaying in today task section", YesNo.No);
			} else {
				log(LogStatus.PASS, "task is not displaying in today task section", YesNo.No);
				sa.assertTrue(false, "task is not displaying in today task section");
			}
			home.clickOnTab(projectName, TabName.HomeTab);
		
			ThreadSleep(5000);
		if(click(driver, home.viewAllAndviewClendarLink(Task.TodayTasks, ViewAllAndViewCalendarLink.viewALl, 30), "Task view all link", action.BOOLEAN)){
			
			log(LogStatus.PASS, "click on view all link of tassk section", YesNo.No);
			if (home.getHeaderNameText(10) != null) {
				log(LogStatus.PASS, "task object is opened", YesNo.No);
			} else {
				log(LogStatus.PASS, "task object is not opened", YesNo.No);
				sa.assertTrue(false, "task object is not opened");
			}
			
		}else{
			
			log(LogStatus.PASS, "Not able to click on view all link of tassk section", YesNo.No);
			sa.assertTrue(false, "Not able to click on view all link of tassk section");
		}
		home.clickOnTab(projectName, TabName.HomeTab);
		ThreadSleep(5000);
		
		List<WebElement> name2 = home.ctreatedTaskAndEventListOnHomePage(Task.TodayEvents);
		
			if (compareMultipleList(driver, SMOKEvent1Subject.toString(), name2).isEmpty()) {
				log(LogStatus.PASS, "event is displaying in event section on homepage", YesNo.No);
			} else {
				log(LogStatus.PASS, "event is not displaying in event section on homepage", YesNo.No);
				sa.assertTrue(false, "event is not displaying in event section on homepage");
			}

			home.clickOnTab(projectName, TabName.HomeTab);
			ThreadSleep(5000);
		
		if(click(driver, home.viewAllAndviewClendarLink(Task.TodayEvents, ViewAllAndViewCalendarLink.ViewCalendar, 10), "Event view calendar link", action.BOOLEAN)){
			
			log(LogStatus.PASS, "click on view calendar link of event section", YesNo.No);
			
		}else{
			
			log(LogStatus.PASS, "Not able to click on view calendar link of event section", YesNo.No);
			sa.assertTrue(false, "Not able to click on view calendar link of event section");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters("projectName")
	@Test
	public void smokeTC010_verifyNavigationMenuOnHomepage(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String filesName = "";

		// Verification on navigation menu
		navigationMenuName = NavigationMenuItems.Bulk_Actions.toString();

		filesName=BulkActions_DefaultValues.Bulk_Email.toString()+","+
				BulkActions_DefaultValues.Bulk_Fundraising.toString()+","+
				BulkActions_DefaultValues.Bulk_Commitments.toString();

		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			List<String> abc = compareMultipleList(driver, filesName, npbl.getNavigationList(projectName, 10));
			if (abc.isEmpty()) {
				log(LogStatus.INFO, "items verified "+filesName+" on "+navigationMenuName, YesNo.No);
			} else {
				log(LogStatus.ERROR, "items not verified "+filesName+" on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"items not verified "+filesName+" on "+navigationMenuName);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify list : "+filesName, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify list : "+filesName);
		}
		refresh(driver);

		navigationMenuName = NavigationMenuItems.New_Interactions.toString();

		filesName=NewInteractions_DefaultValues.Call.toString()+","+
				NewInteractions_DefaultValues.Meeting.toString()+","+
				NewInteractions_DefaultValues.Task.toString();

		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			List<String> abc = compareMultipleList(driver, filesName, npbl.getNavigationList(projectName, 10));
			if (abc.isEmpty()) {
				log(LogStatus.INFO, "items verified "+filesName+" on "+navigationMenuName, YesNo.No);
			} else {
				log(LogStatus.ERROR, "items not verified "+filesName+" on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"items not verified "+filesName+" on "+navigationMenuName);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify list : "+filesName, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify list : "+filesName);
		}

		refresh(driver);


		navigationMenuName = NavigationMenuItems.Create_New.toString();

		filesName=CreateNew_DefaultValues.New_Deal.toString()+","+
				CreateNew_DefaultValues.New_Institution.toString()+","+
				CreateNew_DefaultValues.New_Contact.toString();

		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			List<String> abc = compareMultipleList(driver, filesName, npbl.getNavigationList(projectName, 10));
			if (abc.isEmpty()) {
				log(LogStatus.INFO, "items verified "+filesName+" on "+navigationMenuName, YesNo.No);
			} else {
				log(LogStatus.ERROR, "items not verified "+filesName+" on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"items not verified "+filesName+" on "+navigationMenuName);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify list : "+filesName, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify list : "+filesName);
		}

		refresh(driver);
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
		
	}

	@Parameters("projectName")
	@Test
	public void smokeTc011_enableDisableCheckboxOfBulkAction(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavatarSetupPageBusinessLayer np= new NavatarSetupPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		NavatarSetupSideMenuTab[] navatarSetupSideMenuTab = {NavatarSetupSideMenuTab.CommitmentCreation};
		NavatarSetupSideMenuTab setupSideMenuTab=null;

		lp.CRMLogin(superAdminUserName, adminPassword );
		navigationMenuName = NavigationMenuItems.Bulk_Actions.toString();
		String[] navigationLabel = {BulkActions_DefaultValues.Bulk_Email.toString(),
				BulkActions_DefaultValues.Bulk_Commitments.toString(),BulkActions_DefaultValues.Bulk_Fundraising.toString()};
	

			setupSideMenuTab=navatarSetupSideMenuTab[0];
			ThreadSleep(5000);
			log(LogStatus.INFO, "<<<<<< Going to Uncheck >>>>>>>", YesNo.No);
			
			if (np.EnableOrDisableSettingOnNavatarSetUp(projectName, setupSideMenuTab, false)){
				switchToDefaultContent(driver);
				
				lp.CRMlogout();
				ThreadSleep(5000);
				lp.CRMLogin(crmUser1EmailID, adminPassword );
				ThreadSleep(5000);
				if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
					log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
					WebElement ele = npbl.getNavigationLabel(projectName, navigationLabel[1], action.BOOLEAN, 10);
					if (ele==null) {
						log(LogStatus.INFO, navigationLabel[1]+" is not present on "+navigationMenuName+" after uncheck "+setupSideMenuTab, YesNo.No);
					} else {
						log(LogStatus.ERROR, navigationLabel[1]+" should not present on "+navigationMenuName+" after uncheck "+setupSideMenuTab, YesNo.Yes);
						sa.assertTrue(false,navigationLabel[1]+" should not present on "+navigationMenuName+" after uncheck "+setupSideMenuTab);
					}
					ele = npbl.getNavigationLabel(projectName, navigationLabel[0], action.BOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO, navigationLabel[0]+" is present on "+navigationMenuName, YesNo.No);
					} else {
						log(LogStatus.ERROR, navigationLabel[0]+" should be present on "+navigationMenuName, YesNo.Yes);
						sa.assertTrue(false,navigationLabel[0]+" should be present on "+navigationMenuName);

					}
					if (ele!=null) {
						log(LogStatus.INFO, navigationLabel[2]+" is present on "+navigationMenuName, YesNo.No);
					} else {
						log(LogStatus.ERROR, navigationLabel[2]+" should be present on "+navigationMenuName, YesNo.Yes);
						sa.assertTrue(false,navigationLabel[2]+" should be present on "+navigationMenuName);

					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot uncheck absenece of "+navigationLabel[1], YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot uncheck absenece of "+navigationLabel[1]);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to disable "+setupSideMenuTab+" so cannot uncheck absenece of "+navigationLabel[1]+" on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"Not Able to disable "+setupSideMenuTab+" so cannot uncheck absenece of "+navigationLabel[1]+" on "+navigationMenuName);
			}
			refresh(driver);
			ThreadSleep(5000);
			lp.CRMlogout();
			ThreadSleep(5000);
			lp.CRMLogin(superAdminUserName, adminPassword );
			
			setupSideMenuTab=navatarSetupSideMenuTab[0];
			ThreadSleep(5000);
			log(LogStatus.INFO, "<<<<<< Going to Check >>>>>>>", YesNo.No);
			
			np.EnableOrDisableSettingOnNavatarSetUp(projectName, setupSideMenuTab, true);
				switchToDefaultContent(driver);
				
				lp.CRMlogout();
				lp.CRMLogin(crmUser1EmailID, adminPassword );
				ThreadSleep(5000);
				// Verification on navigation menu
				
				if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
					log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
					WebElement ele = npbl.getNavigationLabel(projectName, navigationLabel[1], action.BOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO, navigationLabel[1]+" is  present on "+navigationMenuName+" after check "+setupSideMenuTab, YesNo.No);

					} else {
						log(LogStatus.ERROR, navigationLabel[1]+" should be present on "+navigationMenuName+" after check "+setupSideMenuTab, YesNo.Yes);
						sa.assertTrue(false,navigationLabel[1]+" should be present on "+navigationMenuName+" after check "+setupSideMenuTab);

					}

					ele = npbl.getNavigationLabel(projectName, navigationLabel[0], action.BOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO, navigationLabel[0]+" is present on "+navigationMenuName, YesNo.No);

					} else {
						log(LogStatus.ERROR, navigationLabel[0]+" should be present on "+navigationMenuName, YesNo.Yes);
						sa.assertTrue(false,navigationLabel[0]+" should be present on "+navigationMenuName);

					}
					
					ele = npbl.getNavigationLabel(projectName, navigationLabel[2], action.BOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO, navigationLabel[2]+" is present on "+navigationMenuName, YesNo.No);
					} else {
						log(LogStatus.ERROR, navigationLabel[2]+" should be present on "+navigationMenuName, YesNo.Yes);
						sa.assertTrue(false,navigationLabel[2]+" should be present on "+navigationMenuName);

					}
					
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot check presence of "+navigationLabel[1], YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot check presence of "+navigationLabel[1]);
				}	
			
			refresh(driver);
			ThreadSleep(5000);
		
		

		lp.CRMlogout();
		sa.assertAll();
		closeBrowser();
	}

	@Parameters("projectName")
	//@Test		// need to discuss which deal comapny name should be select to create deal
	public void smokeTc012_createDataForBulkAction(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp= new FundsPageBusinessLayer(driver);
		FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		
		String value="";
		String type="";
		String[][] EntityOrAccounts = {{ SMOKIns4InsName, SMOKIns4RecordType ,null} ,{ SMOKIns5InsName, SMOKIns5RecordType ,null},{ SMOKIns6InsName, SMOKIns6RecordType ,null},
			{ SMOKIns7InsName, SMOKIns7RecordType ,null},{ SMOKIns8InsName, SMOKIns8RecordType ,null},{ SMOKIns9InsName, SMOKIns9RecordType ,null}};

		//ins
		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, TabName.InstituitonsTab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.InstituitonsTab,YesNo.No);	
				value = accounts[0];
				type = accounts[1];
				if (ip.createEntityOrAccount(projectName, mode, value, type, null, null, 20)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.InstituitonsTab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.InstituitonsTab,YesNo.Yes);
			}
		}
		
		String firstName="";
		String lastName="";
		String legalName="";
		String email="";
		String[][] contacts = {{ SMOKCon2FirstName, SMOKCon2LastName ,SMOKCon2InstitutionName,SMOKCon2ContactEmail} ,
							{ SMOKCon3FirstName, SMOKCon3LastName ,SMOKCon3InstitutionName,SMOKCon3ContactEmail},
							{ SMOKCon4FirstName, SMOKCon4LastName ,SMOKCon4InstitutionName,SMOKCon4ContactEmail},
							{ SMOKCon5FirstName, SMOKCon5LastName ,SMOKCon5InstitutionName,SMOKCon5ContactEmail},
							{ SMOKCon6FirstName, SMOKCon6LastName ,SMOKCon6InstitutionName,SMOKCon6ContactEmail},
							{ SMOKCon7FirstName, SMOKCon7LastName ,SMOKCon7InstitutionName,SMOKCon7ContactEmail}};

		ThreadSleep(2000);
		// contact
		for (String[] contact : contacts) {
				if (bp.clickOnTab(environment,mode, TabName.ContactTab)) {
					log(LogStatus.INFO,"Click on Tab : "+TabName.ContactTab,YesNo.No);	
					firstName = contact[0];
					lastName = contact[1];
					legalName=contact[2];
					email=contact[3];
					
					if (cp.createContact(projectName, firstName, lastName, legalName, email,"", null, null, CreationPage.ContactPage, null,null)) {
						log(LogStatus.INFO,"successfully Created Contact : "+firstName+" "+lastName,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Contact : "+firstName+" "+lastName);
						log(LogStatus.SKIP,"Not Able to Create Contact: "+firstName+" "+lastName,YesNo.Yes);
					}


				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.ContactTab);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.ContactTab,YesNo.Yes);
				}
				
		}
				ThreadSleep(2000);
				String dealName="";
				String stage="";
				String sourceContact="";
				String sourceFirm="";
				String[][] deals = {{ SMOKCon2FirstName, SMOKCon2LastName ,SMOKCon2InstitutionName,SMOKCon2ContactEmail} ,
									{ SMOKCon3FirstName, SMOKCon3LastName ,SMOKCon3InstitutionName,SMOKCon3ContactEmail},
									{ SMOKCon4FirstName, SMOKCon4LastName ,SMOKCon4InstitutionName,SMOKCon4ContactEmail},
									{ SMOKCon5FirstName, SMOKCon5LastName ,SMOKCon5InstitutionName,SMOKCon5ContactEmail},
									{ SMOKCon6FirstName, SMOKCon6LastName ,SMOKCon6InstitutionName,SMOKCon6ContactEmail},
									{ SMOKCon7FirstName, SMOKCon7LastName ,SMOKCon7InstitutionName,SMOKCon7ContactEmail}};

			//deal	
				for (String[] deal : deals) {
				if(bp.clickOnTab(environment,mode, TabName.DealTab)){
					log(LogStatus.INFO,"Click on Tab : "+TabName.DealTab,YesNo.No);
					dealName = deal[0];
					stage = deal[1];
					sourceContact=deal[2];
					sourceFirm=deal[3];
					String[][] otherlabel={{excelLabel.Source_Firm.toString(),sourceFirm},{excelLabel.Source_Contact.toString(),sourceContact}};

					if(fp.createDeal(projectName, "", dealName, "", stage, otherlabel, 30)){
						
						log(LogStatus.INFO,"successfully Created deal : "+dealName,YesNo.No);	

					}else{
						
						sa.assertTrue(false,"Not Able to Create deal : "+dealName);
						log(LogStatus.SKIP,"Not Able to Create deal : "+dealName,YesNo.Yes);
					}
					
				}else{
					
					sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.DealTab);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.DealTab,YesNo.Yes);
				}
				}
				ThreadSleep(2000);
				//fund
				if (bp.clickOnTab(environment,mode, TabName.FundsTab)) {
					log(LogStatus.INFO,"Click on Tab : "+TabName.FundsTab,YesNo.No);	
					if (fp.createFundPE(projectName,SMOKFund2FundName,"",SMOKFund2FundType,SMOKFund2InvestmentCategory, null, 15)) {
						log(LogStatus.INFO,"Created Fund : "+SMOKFund2FundName,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Fund : "+SMOKFund2FundName);
						log(LogStatus.SKIP,"Not Able to Create Fund  : "+SMOKFund2FundName,YesNo.Yes);
					}

				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.FundsTab);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.FundsTab,YesNo.Yes);
				}
				
				ThreadSleep(2000);
				
				// FR
				
				if(bp.clickOnTab(environment,mode,TabName.FundraisingsTab)) {
					if(fr.createFundRaising(environment,mode,SMOKFR2FundraisingName,SMOKFR2FundName,SMOKFR2InstitutionName, null, null, null, null)){
						appLog.info("fundraising is created : "+SMOKFR2FundraisingName);
					}else {
						appLog.error("Not able to create fundraising: "+SMOKFR2FundraisingName);
						sa.assertTrue(false, "Not able to create fundraising: "+SMOKFR2FundraisingName);
					}
				}else {
					appLog.error("Not able to click on fundraising tab so cannot create fundraising: "+SMOKFR2FundraisingName);
					sa.assertTrue(false,"Not able to click on fundraising tab so cannot create fundraising: "+SMOKFR2FundraisingName);
				}
				
				lp.CRMlogout();
				sa.assertAll();
				closeBrowser();
	
				
	}
	
	@Parameters("projectName")
	@Test
	public void smokeTc013_verifyBulkActioMenu(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ReportsTabBusinessLayer report = new ReportsTabBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword);

		if (report.createCustomReportOrDashboardFolder(environment, SmokeReport2FolderName,
				ReportDashboardFolderType.ReportFolder, FolderAccess.ReadOnly)) {

			String[] splitedReportName = removeNumbersFromString(SmokeReport2Name);
			SmokeReport2Name = splitedReportName[0] + lp.generateRandomNumber();

			ExcelUtils.writeData(phase1DataSheetFilePath, SmokeReport2FolderName, "Report", excelLabel.Variable_Name, "SmokeReport2",
					excelLabel.Report_Folder_Name);
			if (report.createCustomReportForFolder(environment, mode, SmokeReportFolderName,ReportFormatName.Null,SmokeReportName,
					SmokeReport2Name, SmokeReport2Type, ReportField.ContactID, SmokeReport2Show, null, SmokeReport2Range, null, null)) {
				appLog.info("Custom Report is created succesdfully : " + SmokeReport2Name);
				ExcelUtils.writeData(phase1DataSheetFilePath, SmokeReport2Name, "Report", excelLabel.Variable_Name, "SmokeReport2",
						excelLabel.Report_Name);
			} else {
				appLog.error("Not able to create Custom Report : " + SmokeReport2Name);
				sa.assertTrue(false, "Not able to create Custom Report : " + SmokeReport2Name);
				log(LogStatus.ERROR, "Not able to create Custom Report : " + SmokeReport2Name, YesNo.Yes);
			}
			switchToDefaultContent(driver);
	
		
		}
		home.switchToLighting();

		// Verification on navigation menu
		navigationMenuName = NavigationMenuItems.Bulk_Actions.toString();
		NavigationPageBusineesLayer  npbl = new NavigationPageBusineesLayer(driver) ;
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);

		String[]  BulkActionNavigationLinks = {BulkActions_DefaultValues.Bulk_Email.toString(),
				BulkActions_DefaultValues.Bulk_Fundraising.toString(),
				BulkActions_DefaultValues.Bulk_Commitments.toString()};
			
		int i=0;
		boolean flag = false;
		for (i=0;i<BulkActionNavigationLinks.length;i++) {
			String bulkActionNavigationLink=BulkActionNavigationLinks[i];
			flag=false;
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName+" Going to click on : "+bulkActionNavigationLink+" for creation ", YesNo.No);

				WebElement ele = npbl.getNavigationLabel(projectName, bulkActionNavigationLink, action.BOOLEAN, 10);
				if (ele!=null && click(driver, ele, bulkActionNavigationLink, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+bulkActionNavigationLink+" so going for creation", YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+bulkActionNavigationLink+" so cannot create data related to this ", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+bulkActionNavigationLink+" so cannot create data related to this ");

				}
				flag=true;
				if (flag) {
					if (i==0) {
						String reportName=SmokeReport2Name;
						String templateName=SmokeReport2FolderName;
						String fname=SMOKCon2FirstName;
						String lname = SMOKCon2LastName;
						String folderName="Capital Call Notice";
						String emailTemplateName = "Capital Call Notice";
						if (hp.VerifyBulkEmailFunctionality(environment, mode, reportName, templateName, fname, lname, lname, searchContactInEmailProspectGrid.Yes, folderName, emailTemplateName)) {
							log(LogStatus.INFO, bulkActionNavigationLink+" functionality is verified succesfuly ", YesNo.No);

						} else {
							log(LogStatus.ERROR, bulkActionNavigationLink+" functionality not verified ", YesNo.Yes);
							sa.assertTrue(false,bulkActionNavigationLink+" functionality not verified ");

						}

					} else if(i==1) {
						String fr = SMOKIns4InsName+" - "+SMOKFund2FundName;
						List<String> contactNamelist= new ArrayList<String>();
						contactNamelist.add(SMOKCon2FirstName+" "+SMOKCon2LastName);
						List<String> accountlist= new ArrayList<String>();
						accountlist.add(SMOKIns4InsName);

						switchToFrame(driver, 60, home.getCreateFundraisingsFrame_Lighting(120));

						if(hp.selectFundNameOrCompanyNameOnCreateFundraisings(environment,mode, PopUpName.selectFundPopUp, SMOKFund2FundName, null)) {
							log(LogStatus.INFO, "Select Fund : "+SMOKFund2FundName, YesNo.No);
							switchToFrame(driver, 30, home.getCreateFundraisingsFrame_Lighting(20));

							if(click(driver, home.getSelectFundNamePopUpContinueBtn(), "continue button", action.SCROLLANDBOOLEAN)) {
								ThreadSleep(3000);
								if(click(driver, home.getSearchBasedOnAccountsAndContactsTab(30), "Search Based On Accounts And Contacts Tab", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "click on Search Based On Accounts And Contacts Tab", YesNo.No);
									ThreadSleep(3000);
									if(hp.applyFilterOnSearchBasedOnAccountsandContacts( FundraisingContactPageTab.SearchBasedOnAccountsAndContacts, SearchBasedOnExistingFundsOptions.AllContacts, environment,mode, null, "Contact:Legal Name", "not equal to", "", null)) {
										log(LogStatus.INFO, "apply filter logic", YesNo.No);

										if(hp.selectInvestorsContactFromCreateFundRaising(contactNamelist,accountlist).isEmpty()) {
											log(LogStatus.INFO, "contact name is selected successfully",YesNo.No);
											if(click(driver, hp.getAddToFundraisingListBtn(30), "Add To Fundraising List Button", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, "click on Add To Fundraising List", YesNo.No);
												if(click(driver, hp.getCreateFundraisingBtn(PageName.CreateFundraisingPage, 30), "create fundraising button", action.SCROLLANDBOOLEAN)) {
													log(LogStatus.INFO, "clicked on create fundraising button", YesNo.No);
													if(click(driver,home.getCreateFundraisingConfirmationOkBtn(30), "ok button", action.SCROLLANDBOOLEAN)) {
														log(LogStatus.INFO, "clicked on OK button", YesNo.No);
														switchToDefaultContent(driver);
														if(home.clickOnTab(environment, mode, TabName.FundraisingsTab)) {
															log(LogStatus.INFO, "clicked on create fundraising button", YesNo.No);
															if(hp.clickOnAlreadyCreatedItem(projectName, fr,false, 120)) {
																log(LogStatus.INFO, "succescfuly found fundraising"+fr, YesNo.No);
															}else {
																log(LogStatus.ERROR, "Not able to found fundraising"+fr, YesNo.Yes);
																sa.assertTrue(false,  "Not able to found fundraising"+fr);
															}
														}else {
															log(LogStatus.ERROR, "Not able to click on fundraising tab so not check created "+fr, YesNo.Yes);
															sa.assertTrue(false,"Not able to click on fundraising tab so not check created "+fr);
														}

													}else {
														log(LogStatus.ERROR, "Not able to click on OK button so cannot get contact id and verify contact details on  created fundraising", YesNo.Yes);
														sa.assertTrue(false, "Not able to click on OK button so cannot get contact id and verify contact details on  created fundraising");
													} 
												}else {
													log(LogStatus.ERROR, "Not able to click on create fundraising button so cannot create fundraisings", YesNo.Yes);
													sa.assertTrue(false, "Not able to click on create fundraising button so cannot create fundraisings");
												}
											}else {
												log(LogStatus.ERROR, "Not able to click on Add To Fundraising List Button so cannot create fundraising", YesNo.Yes);
												sa.assertTrue(false, "Not able to click on Add To Fundraising List Button so cannot create fundraising");
											}
										}else {
											log(LogStatus.ERROR, " Not able to select Contact Name from select investor grid so cannot create fundraising", YesNo.Yes);
											sa.assertTrue(false, " Not able to select Contact Name from select investor grid so cannot create fundraising");
										}
									}else {
										log(LogStatus.ERROR, "Not able to apply filter logic so cannot verify create fundraising page", YesNo.Yes);
										sa.assertTrue(false, "Not able to apply filter logic so cannot verify create fundraising page");
									}
								}else {
									log(LogStatus.ERROR, "Not able to click on Search Based On Accounts And Contacts Tab so cannot verify create fundraising page", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on Search Based On Accounts And Contacts Tab so cannot verify create fundraising page");
								}
							}else {
								log(LogStatus.ERROR, "Not able to click on select fund continue button so cannot create fundraising", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on select fund continue button so cannot create fundraising");
							}



						}else {
							log(LogStatus.ERROR, "Not able to click on select fund Name from lookup popup", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on select fund Name from lookup popup");
						}

					}else {
						
						String limitedPartner="Test Limited partner-1";
						String partnership="Test partnership-1";
						
						String[][] commitmentInformation= {{limitedPartner,"200000",partnership},{limitedPartner,"300000",partnership}};

						if(hp.selectFundraisingNameOrCommitmentType(environment, mode, SMOKFR2FundName, null, null, null, CommitmentType.fundraisingName)) {
							if(hp.commitmentInfoAndAdditionalInfo(environment, mode, commitmentInformation, null,null,null)) {
								log(LogStatus.INFO, "All commitment information and additional information is passed successfully", YesNo.Yes);
								switchToFrame(driver, 30, home.getCreateCommitmentFrame_Lightning(20));
								//home.writeTotalAmountInExcelSheet(smokeFilePath, "SmokeFund1", "Funds")
								if(click(driver, home.getCreateCommitmentBtn(20, TopOrBottom.BOTTOM), "create commitment button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "click on create commitment button", YesNo.No);
									ThreadSleep(2000);
									if(click(driver, home.getCreateCommitmentOkBtn(30), "OK button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "clicked on commitment OK button", YesNo.No);
									}else {
										log(LogStatus.ERROR, "Not able to click on commitment OK button", YesNo.Yes);
										sa.assertTrue(false, "Not able to click on commitment OK button");
									}
								}else {
									log(LogStatus.ERROR, "Not able to click on create commitment button so cannot create commitment", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on create commitment button so cannot create commitment");
								}
							}else {
								log(LogStatus.ERROR, "All commitment information and additional information is not passed so cannot create commitment", YesNo.Yes);
								sa.assertTrue(false, "All commitment information and additional information is not passed so cannot create commitment");
							}
						}else {
							log(LogStatus.ERROR, "Not able to select fundraising name from commitment creation pop up so cannot create commitment",YesNo.Yes);
							sa.assertTrue(false,  "Not able to select fundraising name from commitment creation pop up so cannot create commitment");
						}
					
					}
				}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on : "+bulkActionNavigationLink+" for creation ", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on : "+bulkActionNavigationLink+" for creation ");
				}
				
				refresh(driver);
			
		}

		lp.CRMlogout();
		sa.assertAll();

	
		}

	@Parameters("projectName")
	@Test
	public void smokeTC014_verifyNewInteractionNavigationMenu(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		NavigationPageBusineesLayer  npbl = new NavigationPageBusineesLayer(driver) ;
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		navigationMenuName = NavigationMenuItems.New_Interactions.toString();

		String[]  newInteractionsNavigationLinks = {NewInteractions_DefaultValues.Call.toString(),
				NewInteractions_DefaultValues.Meeting.toString(),
				NewInteractions_DefaultValues.Task.toString()};
		int i=0;
		boolean flag = false;
		String adminUerName = crmUser1FirstName+" "+crmUser1LastName;
		String subject ="";
		String dueDate=previousOrForwardDateAccordingToTimeZone(2, "M/d/YYYY", BasePageBusinessLayer.AmericaLosAngelesTimeZone);
		String contactNAme= SMOKCon1FirstName+" "+SMOKCon1LastName;
		String[][] dropDownLabelWithValues = new String[3][];

		for (i=2;i<newInteractionsNavigationLinks.length;i++) {
			String newInteractionsNavigationLink=newInteractionsNavigationLinks[i];
			flag=false;
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName+" Going to click on : "+newInteractionsNavigationLink+" for creation ", YesNo.No);

				WebElement ele = npbl.getNavigationLabel(projectName, newInteractionsNavigationLink, action.BOOLEAN, 10);
				if (ele!=null && click(driver, ele, newInteractionsNavigationLink, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+newInteractionsNavigationLink+" so going for creation", YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+newInteractionsNavigationLink+" so cannot create data related to this ", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+newInteractionsNavigationLink+" so cannot create data related to this ");

				}
				flag=true;
				if (flag) {

					ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.CallPopUp, PageLabel.Assigned_To.toString(),false, adminUerName, action.SCROLLANDBOOLEAN, 20);
					if (ele!=null) {
						log(LogStatus.INFO, adminUerName+" Found For Label "+PageLabel.Assigned_To.toString(),YesNo.No);	
					} else {
						sa.assertTrue(false,adminUerName+" not Found For Label "+PageLabel.Assigned_To.toString());
						log(LogStatus.ERROR, adminUerName+" not Found For Label "+PageLabel.Assigned_To.toString(),YesNo.Yes);

					}

					if (i==0) {
						subject =SMOKTask3Subject;
						ExcelUtils.writeData(phase1DataSheetFilePath,dueDate, "Task1", excelLabel.Variable_Name, "SMOKTask3", excelLabel.Due_Date);
						dropDownLabelWithValues=null;

					} else if(i==1) {

						subject =SMOKTask4Subject;
						
						ExcelUtils.writeData(phase1DataSheetFilePath,dueDate, "Task1", excelLabel.Variable_Name, "SMOKTask4", excelLabel.Due_Date);

						dropDownLabelWithValues=null;

					}else{
						subject =SMOKTask5Subject;
						ExcelUtils.writeData(phase1DataSheetFilePath,dueDate, "Task1", excelLabel.Variable_Name, "SMOKTask5", excelLabel.Due_Date);
						dropDownLabelWithValues=null;
					}

					if (tp.enteringSubjectAndSelectDropDownValuesonTaskPopUp(projectName, PageName.TaskPage, subject, dropDownLabelWithValues, action.SCROLLANDBOOLEAN, 10)) {
						log(LogStatus.INFO, "Entered value to Subject Text Box ", YesNo.No);
						ThreadSleep(1000);

						if (sendKeys(driver, tp.getdueDateTextBoxInNewTask(projectName, 20), dueDate, PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Entered value to Due Date Text Box", YesNo.No);
							ThreadSleep(1000);
						}else {
							log(LogStatus.ERROR, "Not able to enter value on duedate textbox "+newInteractionsNavigationLink, YesNo.Yes);
							sa.assertTrue(false,"Not able to enter value on duedate textbox "+newInteractionsNavigationLink );
						}

						flag = cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TaskPage, PageLabel.Name.toString(), TabName.TaskTab, contactNAme, action.SCROLLANDBOOLEAN, 10);		
						if (flag) {
							ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.TaskPage, PageLabel.Name.toString(),true, contactNAme, action.SCROLLANDBOOLEAN, 5);
							if (ele!=null) {
								log(LogStatus.INFO, contactNAme+" Found For Label "+PageLabel.Name.toString()+" at "+newInteractionsNavigationLink,YesNo.No);	
							} else {
								sa.assertTrue(false,contactNAme+" not Found For Label "+PageLabel.Name.toString()+" at "+newInteractionsNavigationLink);
								log(LogStatus.ERROR, contactNAme+" not Found For Label "+PageLabel.Name.toString()+" at "+newInteractionsNavigationLink,YesNo.Yes);
							}
						} else {
							sa.assertTrue(false,"Not Able to Select "+contactNAme+" For Label "+PageLabel.Name);
							log(LogStatus.SKIP,"Not Able to Select "+contactNAme+" For Label "+PageLabel.Name,YesNo.Yes);

						}
						if (clickUsingJavaScript(driver, tp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"successfully created : "+subject+" for "+newInteractionsNavigationLink,  YesNo.No);
							ExcelUtils.writeData(phase1DataSheetFilePath,dueDate, "Task1", excelLabel.Variable_Name, "M3CALL1", excelLabel.Due_Date);
							ele = tp.getCreatedConfirmationMsg(projectName, 15);
							if (ele!=null) {
								String actualValue = ele.getText().trim();
								String expectedValue=tp.taskCreatesMsg(projectName, subject);
								if (expectedValue.contains(actualValue)) {
									log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);
								} else {
									log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
									BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
								}
							} else {
								sa.assertTrue(false,"Created Task Msg Ele not Found");
								log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);
							}
							String[][] fieldsWithValues= {
									{PageLabel.Subject.toString(),subject},
									{PageLabel.Due_Date.toString(),dueDate},
									{PageLabel.Name.toString(),contactNAme},
									{PageLabel.Assigned_To.toString(),adminUerName}};
							tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);
						}
						else {
							log(LogStatus.ERROR, "Save Button is not visible so could not be create "+newInteractionsNavigationLink, YesNo.Yes);
							sa.assertTrue(false,"Save Button is not visible so could not be create "+newInteractionsNavigationLink );
						}


					}else {
						log(LogStatus.ERROR, "Subject textbox is not visible so could not be create "+newInteractionsNavigationLink, YesNo.Yes);
						sa.assertTrue(false,"Subject textbox is not visible so could not be create "+newInteractionsNavigationLink );
					}


				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on : "+newInteractionsNavigationLink+" for creation ", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on : "+newInteractionsNavigationLink+" for creation ");
			}
		}
		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters("projectName")
	@Test
	public void smokeTC015_verifyCreateNewNavigationMenu(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		NavigationPageBusineesLayer  npbl = new NavigationPageBusineesLayer(driver) ;
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		navigationMenuName = NavigationMenuItems.Create_New.toString();

		String[]  createNewNavigationLinks = {CreateNew_DefaultValues.New_Deal.toString(),
				CreateNew_DefaultValues.New_Institution.toString(),
				CreateNew_DefaultValues.New_Contact.toString()};
		int i=0;
		boolean flag = false;
		WebElement ele=null;
		for (i=1;i<createNewNavigationLinks.length;i++) {
			String createNewNavigationLink=createNewNavigationLinks[i];
			flag=false;
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName+" Going to click on : "+createNewNavigationLink+" for creation ", YesNo.No);

				 ele = npbl.getNavigationLabel(projectName, createNewNavigationLink, action.BOOLEAN, 10);
				if (ele!=null && click(driver, ele, createNewNavigationLink, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+createNewNavigationLink+" so going for creation", YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+createNewNavigationLink+" so cannot create data related to this ", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+createNewNavigationLink+" so cannot create data related to this ");

				}

				if (flag) {

					if (i==0) {
						
						if (fp.createDealPopUp(projectName,"",SMOKDeal8DealName,SMOKDeal8CompanyName, SMOKDeal8Stage,null, 15)) {
							log(LogStatus.INFO,"Created Deal : "+SMOKDeal8DealName+" through "+createNewNavigationLink,YesNo.No);	
						} else {
							sa.assertTrue(false,"Not Able to Create Deal  : "+SMOKDeal8DealName+" through "+createNewNavigationLink);
							log(LogStatus.SKIP,"Not Able to Create Deal  : "+SMOKDeal8DealName+" through "+createNewNavigationLink,YesNo.Yes);
						}
						
					} else if(i==1) {
						
						 if (ip.createInstitutionPopUp(projectName, environment, mode, SMOKIns10InsName,SMOKIns10RecordType, InstitutionPageFieldLabelText.Phone.toString(),SMOKIns10Phone)) {
								log(LogStatus.INFO,"successfully Created Account/Entity : "+SMOKIns10InsName+" of record type : "+SMOKIns10RecordType,YesNo.No);	
							} else {
								sa.assertTrue(false,"Not Able to Create Account/Entity : "+SMOKIns10InsName+" of record type : "+SMOKIns10RecordType);
								log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+SMOKIns10InsName+" of record type : "+SMOKIns10RecordType,YesNo.Yes);
							}
						 
					}else{

						if (cp.createContactPopUp(projectName, SMOKCon8FirstName, SMOKCon8LastName, SMOKCon8InstitutionName, "","", null, null, CreationPage.ContactPage, null)) {
							log(LogStatus.INFO,"successfully Created Contact : "+SMOKCon8FirstName+" "+SMOKCon8LastName,YesNo.No);	
						} else {
							sa.assertTrue(false,"Not Able to Create Contact : "+SMOKCon8FirstName+" "+SMOKCon8LastName);
							log(LogStatus.SKIP,"Not Able to Create Contact: "+SMOKCon8FirstName+" "+SMOKCon8LastName,YesNo.Yes);
						}
						
						 
					}

					
				}
				npbl.clickOnNavatarEdgeLink(projectName, navigationMenuName, action.BOOLEAN, 30);
				ThreadSleep(5000);
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on : "+createNewNavigationLink+" for creation ", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on : "+createNewNavigationLink+" for creation ");
			}
			refresh(driver);
			ThreadSleep(5000);
		}
		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters("projectName")
	@Test
	public void smokeTC016_verifyNavigationLinkAndCreateCustomLink(String projectName){
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavatarSetupPageBusinessLayer np= new NavatarSetupPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		NavatarSetupSideMenuTab[] navatarSetupSideMenuTab = {NavatarSetupSideMenuTab.IndividualInvestorCreation};
		NavatarSetupSideMenuTab setupSideMenuTab=null;

		lp.CRMLogin(superAdminUserName, adminPassword );
		
		String navigationLabel1=CSVLabel.Navigation_Label.toString();
		String orderLabel=CSVLabel.Order.toString();
		String orderLabelValue="4";
		String urlLabel=CSVLabel.URL.toString();
		String urlValue="/apex/navpeII__IndividualInvestor?retURL=/lightning/page/home";
		String navigationTypeLabel=CSVLabel.Navigation_Type.toString();
		String navigationTypeValue=navigationMenuName;
		boolean flag=false;

		navigationMenuName = NavigationMenuItems.Bulk_Actions.toString();
		String[] navigationLabel = {BulkActions_DefaultValues.Individual_Investor_Creation.toString()};
	

			setupSideMenuTab=navatarSetupSideMenuTab[0];
			ThreadSleep(5000);
			log(LogStatus.INFO, "<<<<<< Going to check >>>>>>>", YesNo.No);
			
				np.EnableOrDisableSettingOnNavatarSetUp(projectName, setupSideMenuTab, true);
				switchToDefaultContent(driver);
				ThreadSleep(3000);
				if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
					log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
					WebElement ele = npbl.getNavigationLabel(projectName, navigationLabel[0], action.BOOLEAN, 10);
					if (ele==null) {
						log(LogStatus.INFO, navigationLabel[0]+" is not present on "+navigationMenuName+" after uncheck "+setupSideMenuTab, YesNo.No);
					} else {
						log(LogStatus.ERROR, navigationLabel[0]+" should not present on "+navigationMenuName+" after uncheck "+setupSideMenuTab, YesNo.Yes);
						sa.assertTrue(false,navigationLabel[0]+" should not present on "+navigationMenuName+" after uncheck "+setupSideMenuTab);
					}					
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot uncheck absenece of "+navigationLabel[0], YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot uncheck absenece of "+navigationLabel[0]);
				}
			
			refresh(driver);
			ThreadSleep(5000);
			setupSideMenuTab=navatarSetupSideMenuTab[0];
			log(LogStatus.INFO, "<<<<<< Going to UnCheck >>>>>>>", YesNo.No);
			
			if(np.EnableOrDisableSettingOnNavatarSetUp(projectName, setupSideMenuTab, false)){
				switchToDefaultContent(driver);
				
				lp.CRMlogout();
				lp.CRMLogin(crmUser1EmailID, adminPassword );
				ThreadSleep(5000);
				// Verification on navigation menu
				
				if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
					log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
					WebElement ele = npbl.getNavigationLabel(projectName, navigationLabel[0], action.BOOLEAN, 10);
					if (ele==null) {
						log(LogStatus.INFO, navigationLabel[0]+" is not present on "+navigationMenuName+" after uncheck "+setupSideMenuTab, YesNo.No);
					} else {
						log(LogStatus.ERROR, navigationLabel[0]+" should not present on "+navigationMenuName+" after uncheck "+setupSideMenuTab, YesNo.Yes);
						sa.assertTrue(false,navigationLabel[0]+" should not present on "+navigationMenuName+" after uncheck "+setupSideMenuTab);
					}
					
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot check presence of "+navigationLabel[0], YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot check presence of "+navigationLabel[0]);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to disable "+setupSideMenuTab+" so cannot uncheck absenece of "+navigationLabel[0]+" on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"Not Able to disable "+setupSideMenuTab+" so cannot uncheck absenece of "+navigationLabel[0]+" on "+navigationMenuName);
			}


				String[][] labelWithValue= {{navigationLabel1,navigationLabel[0]},
						{orderLabel,orderLabelValue},
						{urlLabel,urlValue},
						{navigationTypeLabel,navigationTypeValue}};

				if (npbl.createNavigationItem(projectName, labelWithValue, 20)) {
					log(LogStatus.INFO, "created "+navigationLabel[0], YesNo.No);
					flag=true;
				} else {
					log(LogStatus.ERROR, "Not Able to create navigation item "+navigationLabel[0], YesNo.Yes);
					sa.assertTrue(false, "Not Able to create navigation item  "+navigationLabel[0]);

				}

			if(flag){
				
				if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
					log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
					WebElement ele2 = npbl.getNavigationLabel(projectName, navigationLabel[1], action.BOOLEAN, 10);
					
					ele2 = npbl.getNavigationLabel(projectName, navigationLabel[0], action.BOOLEAN, 10);
					if (ele2!=null) {
						log(LogStatus.INFO, navigationLabel[0]+" is present on "+navigationMenuName, YesNo.No);
					} else {
						log(LogStatus.ERROR, navigationLabel[0]+" should be present on "+navigationMenuName, YesNo.Yes);
						sa.assertTrue(false,navigationLabel[0]+" should be present on "+navigationMenuName);

					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot uncheck presence of "+navigationLabel[0], YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot uncheck presence of "+navigationLabel[0]);
				}
			}

			np.EnableOrDisableSettingOnNavatarSetUp(projectName, setupSideMenuTab, true);
			switchToDefaultContent(driver);
			ThreadSleep(3000);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				WebElement ele3 = npbl.getNavigationLabel(projectName, navigationLabel[0], action.BOOLEAN, 10);
				if (ele3!=null) {
					log(LogStatus.INFO, navigationLabel[0]+" is present on "+navigationMenuName, YesNo.No);
				} else {
					log(LogStatus.ERROR, navigationLabel[0]+" should be present on "+navigationMenuName, YesNo.Yes);
					sa.assertTrue(false,navigationLabel[0]+" should be present on "+navigationMenuName);

				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot uncheck presence of "+navigationLabel[0], YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot uncheck presence of "+navigationLabel[1]);
			}				
			

		lp.CRMlogout();
		sa.assertAll();
		closeBrowser();
	}

	@Parameters("projectName")
	@Test
	public void smokeTC017_verifyAllInstituionRecordType(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp= new BasePageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String recordType ="Institution,Advisor,Company,Fund Manager,Fund Manager’s Fund,Individual Investor,Intermediary,"
				+ "Lender,Limited Partner,Portfolio Company";
		
		ThreadSleep(5000);
		if(bp.clickOnTab(projectName, mode, TabName.InstituitonsTab)){
			log(LogStatus.PASS,	"click on institution tab", YesNo.No);
			
			if(clickUsingJavaScript(driver, ip.getNewButton(environment, mode, 60), "new button")){
				
				log(LogStatus.PASS,	"click on new button", YesNo.No);
				ThreadSleep(3000);
				List<WebElement> lst=ip.getAllInstituitionRecrdTypeList(mode,30);
				if(compareMultipleList(driver, recordType, lst).isEmpty()){
					log(LogStatus.PASS,	"All Institution record type is verified", YesNo.No);
				}else{
					
					log(LogStatus.FAIL,	"All Institution record type is not  matched", YesNo.Yes);
					sa.assertTrue(false,"All Institution record type is not  matched");
				}
				
				
			}else{
				log(LogStatus.FAIL,	"not able to click on new button", YesNo.Yes);
				sa.assertTrue(false,"not able to click on new button");
			}
		}else{
			
			log(LogStatus.FAIL,	"not able to click on institution tab", YesNo.Yes);
			sa.assertTrue(false,"not able to click on institution tab");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void smokeTC018_verifyListViewOnInstituionObject(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp= new BasePageBusinessLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		String selectListLink ="All Companies,All Institutions,Automation All,Limited Partners,All Fund Manager's Funds,All Fund Managers,"
				+ "All Intermediaries,All Investors,All Limited Partners,My Institutions,My Call List,New Last Week,New This Week"
				+ ",Portfolio Companies,Recently Viewed Institutions,Top LPs,Watchlist";
		
		ThreadSleep(5000);
		if(bp.clickOnTab(projectName, mode, TabName.InstituitonsTab)){
			log(LogStatus.PASS,	"click on institution tab", YesNo.No);
			
			if (click(driver, bp.getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
				ThreadSleep(5000);
				List<WebElement> lst=bp.getAllLinkOfSelectListIconOption(mode,"Institutions", 30);
				if(compareMultipleList(driver, selectListLink, lst).isEmpty()){
					log(LogStatus.PASS,	"All link of select list icon  is verified", YesNo.No);
				}else{
					
					log(LogStatus.FAIL,	"All link of select list icon  is not verified", YesNo.Yes);
					sa.assertTrue(false,"All link of select list icon  is not verified");
				}
				
			} else {
				appLog.error("Not able to click on Select List Icon");
			}
		}else{
			
			log(LogStatus.FAIL,	"not able to click on institution tab", YesNo.Yes);
			sa.assertTrue(false,"not able to click on institution tab");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters("projectName")
	@Test
	public void smokeTC019_createDataAllRecordTypeInstitution(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		ThreadSleep(4000);
		
		String insName="";
		String recordType="";
		String entityType=null;
		String status=null;
		String parent=null;
		
		String[][] EntityOrAccounts = {{ SMOKIns11InsName, SMOKIns11RecordType ,SMOKIns11Status,SMOKIns11ParentInstitution,SMOKIns11EntityType} ,
										{ SMOKIns12InsName, SMOKIns12RecordType ,SMOKIns12Status,SMOKIns12ParentInstitution,null},
										{ SMOKIns13InsName, SMOKIns13RecordType ,SMOKIns13Status,SMOKIns13ParentInstitution,SMOKIns13EntityType},
										{ SMOKIns14InsName, SMOKIns14RecordType ,SMOKIns14Status,SMOKIns14ParentInstitution,SMOKIns14EntityType},
										{ SMOKIns15InsName, SMOKIns15RecordType ,SMOKIns15Status,SMOKIns15ParentInstitution,SMOKIns15EntityType},
										{ SMOKIns16InsName, SMOKIns16RecordType ,SMOKIns16Status,SMOKIns16ParentInstitution,SMOKIns16EntityType},
										{ SMOKIns17InsName, SMOKIns17RecordType ,SMOKIns17Status,SMOKIns17ParentInstitution,SMOKIns17EntityType},
										{ SMOKIns18InsName, SMOKIns18RecordType ,SMOKIns18Status,SMOKIns18ParentInstitution,SMOKIns18EntityType},
										{ SMOKIns19InsName, SMOKIns19RecordType ,SMOKIns19Status,SMOKIns19ParentInstitution,SMOKIns19EntityType},
										{ SMOKIns20InsName, SMOKIns20RecordType ,SMOKIns20Status,SMOKIns20ParentInstitution,null}};

		//ins
		for (int i=0;i<EntityOrAccounts.length;i++) {
			if (lp.clickOnTab(projectName, TabName.InstituitonsTab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.InstituitonsTab,YesNo.No);	
				String[] accounts =EntityOrAccounts[i];
				insName = accounts[0];
				recordType = accounts[1];
				
				if(i==0||i==2||i==6){
					
					entityType=accounts[4];
					if (ip.createEntityOrAccount(projectName, mode, insName, recordType, entityType, null, 20)) {
						log(LogStatus.INFO,"successfully Created Account/Entity : "+insName+" of record type : "+recordType,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Account/Entity : "+insName+" of record type : "+recordType);
						log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+insName+" of record type : "+recordType,YesNo.Yes);
					}

				}
				
				if(i==3||i==4||i==5||i==8){
					
					parent=accounts[3];
					
					if (ip.createInstitution(projectName, environment, mode, insName,recordType, InstitutionPageFieldLabelText.Parent_Institution.toString(),parent)) {
						log(LogStatus.INFO,"successfully Created Account/Entity : "+insName+" of record type : "+recordType,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Account/Entity : "+insName+" of record type : "+recordType);
						log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+insName+" of record type : "+recordType,YesNo.Yes);
					}

					
				}
				if(i==1||i==9){
					status=accounts[2];
					
				if (ip.createEntityOrAccount(projectName, mode, insName, recordType, null, new String[][]{{"Status",status}}, 20)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+insName+" of record type : "+recordType,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+insName+" of record type : "+recordType);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+insName+" of record type : "+recordType,YesNo.Yes);
				}

				}
				if(i==7){
					
				if (ip.createEntityOrAccount(projectName, mode, insName, recordType, null,null,20)){
					log(LogStatus.INFO,"successfully Created Account/Entity : "+insName+" of record type : "+recordType,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+insName+" of record type : "+recordType);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+insName+" of record type : "+recordType,YesNo.Yes);
				}

				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
			}
		}
		
		lp.CRMlogout();
		sa.assertAll();
		
	}
	
	@Parameters("projectName")
	@Test
	public void smokeTc020_verifyInstitutionRecordTypePage(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		NavigationPageBusineesLayer np = new NavigationPageBusineesLayer(driver);
		
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String tab;
		ThreadSleep(5000);
		
		if(bp.clickOnTab(projectName, TabName.InstituitonsTab)){
			log(LogStatus.INFO, "click on"+TabName.InstituitonsTab, YesNo.No);
			
			if(bp.clickOnAlreadyCreatedItem(projectName, SMOKIns11InsName, 30)){
				
				log(LogStatus.INFO, "click on created institution "+SMOKIns11InsName, YesNo.No);

				// details page related tab verification
				String[] relatedTabs = {RelatedTab.Details.toString(),RelatedTab.Contacts.toString(),RelatedTab.Consultants.toString(),RelatedTab.Fundraising.toString(),RelatedTab.Co_Investments.toString()
						,RelatedTab.Deals.toString(),RelatedTab.Events.toString(),RelatedTab.Files.toString()};
				
				for (int i = 0; i < relatedTabs.length; i++) {
				tab = relatedTabs[i];
				ele=ip.getRelatedTab(projectName, tab, 10);
				if (ele!=null) {
				log(LogStatus.INFO,"Related Tab "+tab+" present at institutions detail page",YesNo.No);
				} else {
				sa.assertTrue(false,"Related Tab "+tab+" sholud be present at institutions detail page");
				log(LogStatus.SKIP,"Related Tab "+tab+" sholud be present at institutions detail page",YesNo.Yes);
				}
				}
				
				ThreadSleep(2000);
				
				
				// Buttons
				np.clickOnShowMoreDropdownOnly(projectName);
				ThreadSleep(3000);
				ShowMoreActionDropDownList[] buttons={
						
	// these button is not present in the org need to discuss					
						//ShowMoreActionDropDownList.New_Affiliation,
						//ShowMoreActionDropDownList.New_Advisor,
						//ShowMoreActionDropDownList.New_Contact,
						ShowMoreActionDropDownList.New_Fundraising};
						
				
				ShowMoreActionDropDownList button=null;
				
				for (int i = 0; i < buttons.length; i++) {
					
				button=buttons[i];
				ele = np.actionDropdownElement(projectName, button, 10);
				if (ele!=null) {
				log(LogStatus.INFO,"Button "+button+" is present at instituion record type page",YesNo.No);
				} else {
				sa.assertTrue(false,"Button "+button+" sholud be present at instituion record type page");
				log(LogStatus.SKIP,"Button "+button+" sholud be present at instituion record type page",YesNo.Yes);
				}
				}


				
				// compact layout field visibiltiy verification
				
				String[] relatedField = {"Website",
										"Institution Type",
										"Phone",
										"Total Fund Commitments (mn)",
										"Total Co-investment Commitments (mn)"};
				String field;
				
				for (int i = 0; i < relatedField.length; i++) {
				field = relatedField[i];
				ele=ip.getHighlightPanelFieldLabel(projectName, field, 20);
				if (ele!=null) {
				log(LogStatus.INFO,"Related field "+field+" present  at institutions detail page",YesNo.No);
				} else {
				sa.assertTrue(false,"Related field "+field+" sholud be present at institutions detail page");
				log(LogStatus.SKIP,"Related field "+field+" sholud be present at institutions detail page",YesNo.Yes);
				}
				}
				
				// activity timeline visibility
				WebElement activityTimeline=ip.getActivityTimeLineBox(30);
				
				if(activityTimeline!=null){
					log(LogStatus.INFO,"Activity timeline is present in institution page",YesNo.No);
					
				}else{
					
					sa.assertTrue(false,"Activity timeline is not present in institution page");
					log(LogStatus.FAIL,"Activity timeline is not present in institution page",YesNo.Yes);
				}
				
			}else{
				sa.assertTrue(false,"Not able to click on created institution "+SMOKIns11InsName);
				log(LogStatus.FAIL,"Not able to click on created institution "+SMOKIns11InsName,YesNo.Yes);
			}
			
			
		}else{
			sa.assertTrue(false,"Not able to click on"+TabName.InstituitonsTab);
			log(LogStatus.FAIL,"Not able to click on"+TabName.InstituitonsTab,YesNo.Yes);
			
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters("projectName")
	@Test
	public void smokeTc021_verifyCompanyRecordTypePage(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		NavigationPageBusineesLayer np = new NavigationPageBusineesLayer(driver);
		
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String tab;
		ThreadSleep(5000);
		
		if(bp.clickOnTab(projectName, TabName.InstituitonsTab)){
			log(LogStatus.INFO, "click on"+TabName.InstituitonsTab, YesNo.No);
			
			if(bp.clickOnAlreadyCreatedItem(projectName, SMOKIns12InsName, 30)){
				
				log(LogStatus.INFO, "click on created institution "+SMOKIns12InsName, YesNo.No);

				// details page related tab verification
				String[] relatedTabs = {RelatedTab.Details.toString(),RelatedTab.Contacts.toString(),RelatedTab.Investors.toString()
						,RelatedTab.Deals.toString(),RelatedTab.Events.toString(),RelatedTab.Files.toString()};
				
				for (int i = 0; i < relatedTabs.length; i++) {
				tab = relatedTabs[i];
				ele=ip.getRelatedTab(projectName, tab, 10);
				if (ele!=null) {
				log(LogStatus.INFO,"Related Tab "+tab+" present at institutions detail page",YesNo.No);
				} else {
				sa.assertTrue(false,"Related Tab "+tab+" sholud be present at institutions detail page");
				log(LogStatus.SKIP,"Related Tab "+tab+" sholud be present at institutions detail page",YesNo.Yes);
				}
				}
				
				ThreadSleep(2000);
				
				
				// Buttons
				np.clickOnShowMoreDropdownOnly(projectName);
				ThreadSleep(2000);
				ShowMoreActionDropDownList[] buttons={
					
				//		this button is not present in menu in org
						
				//		ShowMoreActionDropDownList.New_Deal,
						ShowMoreActionDropDownList.New_Affiliation,
						ShowMoreActionDropDownList.New_Investor,
						ShowMoreActionDropDownList.New_Contact};
				
					ShowMoreActionDropDownList button=null;
				for (int i = 0; i < buttons.length; i++) {
				button=buttons[i];
				ele = np.actionDropdownElement(projectName, button, 10);
				if (ele!=null) {
				log(LogStatus.INFO,"Button "+button+" is present at instituion record type page",YesNo.No);
				} else {
				sa.assertTrue(false,"Button "+button+" sholud be present at instituion record type page");
				log(LogStatus.SKIP,"Button "+button+" sholud be present at instituion record type page",YesNo.Yes);
				}
				}


				
				// compact layout field visibiltiy verification
				
				String[] relatedField = {"Website",
										"Status",
										"Phone", 		
										"Sector",
										"Region"};
				String field;
				
				for (int i = 0; i < relatedField.length; i++) {
				field = relatedField[i];
				ele=ip.getHighlightPanelFieldLabel(projectName, field, 20);
				if (ele!=null) {
				log(LogStatus.INFO,"Related field "+field+" present at institutions detail page",YesNo.No);
				} else {
				sa.assertTrue(false,"Related field "+field+" sholud be present at institutions detail page");
				log(LogStatus.SKIP,"Related field "+field+" sholud be present at institutions detail page",YesNo.Yes);
				}
				}
				
				// activity timeline visibility
				WebElement activityTimeline=ip.getActivityTimeLineBox(30);
				
				if(activityTimeline!=null){
					log(LogStatus.INFO,"Activity timeline is present in institution page",YesNo.No);
					
				}else{
					
					sa.assertTrue(false,"Activity timeline is not present in institution page");
					log(LogStatus.FAIL,"Activity timeline is not present in institution page",YesNo.Yes);
				}
				
			}else{
				sa.assertTrue(false,"Not able to click on created institution "+SMOKIns12InsName);
				log(LogStatus.FAIL,"Not able to click on created institution "+SMOKIns12InsName,YesNo.Yes);
			}
			
			
		}else{
			sa.assertTrue(false,"Not able to click on"+TabName.InstituitonsTab);
			log(LogStatus.FAIL,"Not able to click on"+TabName.InstituitonsTab,YesNo.Yes);
			
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters("projectName")
	@Test
	public void smokeTc022_verifyIndividualInvestorRecordTypePage(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		NavigationPageBusineesLayer np = new NavigationPageBusineesLayer(driver);
		
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String tab;
		ThreadSleep(5000);
		
		if(bp.clickOnTab(projectName, TabName.InstituitonsTab)){
			log(LogStatus.INFO, "click on"+TabName.InstituitonsTab, YesNo.No);
			
			if(bp.clickOnAlreadyCreatedItem(projectName, SMOKIns13InsName, 30)){
				
				log(LogStatus.INFO, "click on created institution "+SMOKIns13InsName, YesNo.No);

				// details page related tab verification
				String[] relatedTabs = {
						RelatedTab.Details.toString(),
						RelatedTab.Contacts.toString(),
						RelatedTab.Fundraising.toString(),
						RelatedTab.Co_Investments.toString(),
						RelatedTab.Consultants.toString(),
						RelatedTab.Deals.toString(),
						RelatedTab.Events.toString(),
						RelatedTab.Files.toString()};
				
				for (int i = 0; i < relatedTabs.length; i++) {
				tab = relatedTabs[i];
				ele=ip.getRelatedTab(projectName, tab, 10);
				if (ele!=null) {
				log(LogStatus.INFO,"Related Tab "+tab+" present at institutions detail page",YesNo.No);
				} else {
				sa.assertTrue(false,"Related Tab "+tab+" sholud be present at institutions detail page");
				log(LogStatus.SKIP,"Related Tab "+tab+" sholud be present at institutions detail page",YesNo.Yes);
				}
				}
				
				ThreadSleep(2000);
				
				
				// Buttons
				np.clickOnShowMoreDropdownOnly(projectName);
				ThreadSleep(2000);
				ShowMoreActionDropDownList[] buttons={
						ShowMoreActionDropDownList.New_Fundraising,
						ShowMoreActionDropDownList.New_Advisor,
						ShowMoreActionDropDownList.New_Contact};
				
				ShowMoreActionDropDownList button=null;
				for (int i = 0; i < buttons.length; i++) {
				button=buttons[i];
				ele = np.actionDropdownElement(projectName, button, 10);
				if (ele!=null) {
				log(LogStatus.INFO,"Button "+button+" is present at instituion record type page",YesNo.No);
				} else {
				sa.assertTrue(false,"Button "+button+" sholud be present at instituion record type page");
				log(LogStatus.SKIP,"Button "+button+" sholud be present at instituion record type page",YesNo.Yes);
				}
				}


				
				// compact layout field visibiltiy verification
				
				String[] relatedField = {"Total Fund Commitments (mn)",
										"Total Co-investment Commitments (mn)",
										"Phone",
										"Sector",
										"Region"};
				String field;
				
				for (int i = 0; i < relatedField.length; i++) {
				field = relatedField[i];
				ele=ip.getHighlightPanelFieldLabel(projectName, field, 20);
				if (ele!=null) {
				log(LogStatus.INFO,"Related field "+field+" present at institutions detail page",YesNo.No);
				} else {
				sa.assertTrue(false,"Related field "+field+" sholud be present at institutions detail page");
				log(LogStatus.SKIP,"Related field "+field+" sholud be present at institutions detail page",YesNo.Yes);
				}
				}
				
				// activity timeline visibility
				WebElement activityTimeline=ip.getActivityTimeLineBox(30);
				
				if(activityTimeline!=null){
					log(LogStatus.INFO,"Activity timeline is present in institution page",YesNo.No);
					
				}else{
					
					sa.assertTrue(false,"Activity timeline is not present in institution page");
					log(LogStatus.FAIL,"Activity timeline is not present in institution page",YesNo.Yes);
				}
				
			}else{
				sa.assertTrue(false,"Not able to click on created institution "+SMOKIns13InsName);
				log(LogStatus.FAIL,"Not able to click on created institution "+SMOKIns13InsName,YesNo.Yes);
			}
			
			
		}else{
			sa.assertTrue(false,"Not able to click on"+TabName.InstituitonsTab);
			log(LogStatus.FAIL,"Not able to click on"+TabName.InstituitonsTab,YesNo.Yes);
			
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void smokeTc023_verifyLimitedPartnerRecordTypePage(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		NavigationPageBusineesLayer np = new NavigationPageBusineesLayer(driver);
		
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String tab;
		ThreadSleep(5000);
		
		if(bp.clickOnTab(projectName, TabName.InstituitonsTab)){
			log(LogStatus.INFO, "click on"+TabName.InstituitonsTab, YesNo.No);
			
			if(bp.clickOnAlreadyCreatedItem(projectName, SMOKIns14InsName, 30)){
				
				log(LogStatus.INFO, "click on created institution "+SMOKIns14InsName, YesNo.No);

				// details page related tab verification
				String[] relatedTabs = {
						RelatedTab.Details.toString(),
						RelatedTab.Contacts.toString(),
						RelatedTab.Fundraising.toString(),
						RelatedTab.Co_Investments.toString(),
						RelatedTab.Events.toString(),
						RelatedTab.Files.toString()};
				
				for (int i = 0; i < relatedTabs.length; i++) {
				tab = relatedTabs[i];
				ele=ip.getRelatedTab(projectName, tab, 10);
				if (ele!=null) {
				log(LogStatus.INFO,"Related Tab "+tab+" present at institutions detail page",YesNo.No);
				} else {
				sa.assertTrue(false,"Related Tab "+tab+" sholud be present at institutions detail page");
				log(LogStatus.SKIP,"Related Tab "+tab+" sholud be present at institutions detail page",YesNo.Yes);
				}
				}
				
				ThreadSleep(2000);
				
				
				// Buttons
				np.clickOnShowMoreDropdownOnly(projectName);
				ThreadSleep(2000);
				ShowMoreActionDropDownList[] buttons={
					
					// button is not presepent in limited partner showmore menu list
					//	ShowMoreActionDropDownList.New_Contact,
						ShowMoreActionDropDownList.New_Commitment,
						ShowMoreActionDropDownList.New_Fundraising};
				
				ShowMoreActionDropDownList button=null;
				for (int i = 0; i < buttons.length; i++) {
				button=buttons[i];
				ele = np.actionDropdownElement(projectName, button, 10);
				if (ele!=null) {
				log(LogStatus.INFO,"Button "+button+" is present at instituion record type page",YesNo.No);
				} else {
				sa.assertTrue(false,"Button "+button+" sholud be present at instituion record type page");
				log(LogStatus.SKIP,"Button "+button+" sholud be present at instituion record type page",YesNo.Yes);
				}
				}


				
				// compact layout field visibiltiy verification
				
				String[] relatedField = {"Total Fund Commitments (mn)",
										"Total Co-investment Commitments (mn)",
										"Phone",
										"Website"};
				String field;
				
				for (int i = 0; i < relatedField.length; i++) {
				field = relatedField[i];
				ele=ip.getHighlightPanelFieldLabel(projectName, field, 20);
				if (ele!=null) {
				log(LogStatus.INFO,"Related field "+field+" present at institutions detail page",YesNo.No);
				} else {
				sa.assertTrue(false,"Related field "+field+" sholud be present at institutions detail page");
				log(LogStatus.SKIP,"Related field "+field+" sholud be present at institutions detail page",YesNo.Yes);
				}
				}
				
				// activity timeline visibility
				WebElement activityTimeline=ip.getActivityTimeLineBox(30);
				
				if(activityTimeline!=null){
					log(LogStatus.INFO,"Activity timeline is present in institution page",YesNo.No);
					
				}else{
					
					sa.assertTrue(false,"Activity timeline is not present in institution page");
					log(LogStatus.FAIL,"Activity timeline is not present in institution page",YesNo.Yes);
				}
				
			}else{
				sa.assertTrue(false,"Not able to click on created institution "+SMOKIns14InsName);
				log(LogStatus.FAIL,"Not able to click on created institution "+SMOKIns14InsName,YesNo.Yes);
			}
			
			
		}else{
			sa.assertTrue(false,"Not able to click on"+TabName.InstituitonsTab);
			log(LogStatus.FAIL,"Not able to click on"+TabName.InstituitonsTab,YesNo.Yes);
			
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void smokeTc024_verifyFundManagerRecordTypePage(String projectName){

		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		NavigationPageBusineesLayer np = new NavigationPageBusineesLayer(driver);
		
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String tab;
		ThreadSleep(5000);
		
		if(bp.clickOnTab(projectName, TabName.InstituitonsTab)){
			log(LogStatus.INFO, "click on"+TabName.InstituitonsTab, YesNo.No);
			
			if(bp.clickOnAlreadyCreatedItem(projectName, SMOKIns15InsName, 30)){
				
				log(LogStatus.INFO, "click on created institution "+SMOKIns15InsName, YesNo.No);

				// details page related tab verification
				String[] relatedTabs = {
						RelatedTab.Details.toString()};
				
				for (int i = 0; i < relatedTabs.length; i++) {
				tab = relatedTabs[i];
				ele=ip.getRelatedTab(projectName, tab, 10);
				if (ele!=null) {
				log(LogStatus.INFO,"Related Tab "+tab+" present at institutions detail page",YesNo.No);
				} else {
				sa.assertTrue(false,"Related Tab "+tab+" sholud be present at institutions detail page");
				log(LogStatus.SKIP,"Related Tab "+tab+" sholud be present at institutions detail page",YesNo.Yes);
				}
				}
				
				ThreadSleep(2000);
				
				
				// verify standard object Buttons
				np.clickOnShowMoreDropdownOnly(projectName);
				ThreadSleep(2000);
				ShowMoreActionDropDownList[] buttons={
					
						ShowMoreActionDropDownList.New_Meeting,
						ShowMoreActionDropDownList.New_Task,
						ShowMoreActionDropDownList.New_Call};
				
				ShowMoreActionDropDownList button=null;
				for (int i = 0; i < buttons.length; i++) {
				button=buttons[i];
				ele = np.actionDropdownElement(projectName, button, 10);
				if (ele!=null) {
				log(LogStatus.INFO,"Button "+button+" is present at instituion record type page",YesNo.No);
				} else {
				sa.assertTrue(false,"Button "+button+" sholud be present at instituion record type page");
				log(LogStatus.SKIP,"Button "+button+" sholud be present at instituion record type page",YesNo.Yes);
				}
				}
				
				// verify custom object Buttons
				np.clickOnShowMoreDropdownOnly(projectName);
				ThreadSleep(2000);
				ShowMoreActionDropDownList[] buttons1={
					
						ShowMoreActionDropDownList.New_Contact,
						ShowMoreActionDropDownList.New_Affiliation,
						ShowMoreActionDropDownList.New_Advisor,
						ShowMoreActionDropDownList.New_Fundraising,
						ShowMoreActionDropDownList.New_Investor,};
				
				ShowMoreActionDropDownList button1=null;
				for (int i = 0; i < buttons1.length; i++) {
				button1=buttons1[i];
				ele = np.actionDropdownElement(projectName, button1, 10);
				if (ele==null) {
				log(LogStatus.INFO,"Button "+button1+" is not present at instituion record type page",YesNo.No);
				} else {
				sa.assertTrue(false,"Button "+button1+" sholud not be present at instituion record type page");
				log(LogStatus.SKIP,"Button "+button1+" sholud not be present at instituion record type page",YesNo.Yes);
				}
				}


				
				// compact layout field visibiltiy verification
				
				String[] relatedField = {"AUM (in Millions)",
										"Fundraising in Next 12 Months?",
										"Phone",
										"Employees"
										};
				String field;
				
				for (int i = 0; i < relatedField.length; i++) {
				field = relatedField[i];
				ele=ip.getHighlightPanelFieldLabel(projectName, field, 20);
				if (ele!=null) {
				log(LogStatus.INFO,"Related field "+field+" present at institutions detail page",YesNo.No);
				} else {
				sa.assertTrue(false,"Related field "+field+" sholud be present at institutions detail page");
				log(LogStatus.SKIP,"Related field "+field+" sholud be present at institutions detail page",YesNo.Yes);
				}
				}
				
				// activity timeline visibility
				WebElement activityTimeline=ip.getActivityTimeLineBox(30);
				
				if(activityTimeline!=null){
					log(LogStatus.INFO,"Activity timeline is present in institution page",YesNo.No);
					
				}else{
					
					sa.assertTrue(false,"Activity timeline is not present in institution page");
					log(LogStatus.FAIL,"Activity timeline is not present in institution page",YesNo.Yes);
				}
				
			}else{
				sa.assertTrue(false,"Not able to click on created institution "+SMOKIns15InsName);
				log(LogStatus.FAIL,"Not able to click on created institution "+SMOKIns15InsName,YesNo.Yes);
			}
			
			
		}else{
			sa.assertTrue(false,"Not able to click on"+TabName.InstituitonsTab);
			log(LogStatus.FAIL,"Not able to click on"+TabName.InstituitonsTab,YesNo.Yes);
			
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void smokeTc025_verifyFundManagerOfFundRecordTypePage(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		NavigationPageBusineesLayer np = new NavigationPageBusineesLayer(driver);
		
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String tab;
		ThreadSleep(5000);
		
		if(bp.clickOnTab(projectName, TabName.InstituitonsTab)){
			log(LogStatus.INFO, "click on"+TabName.InstituitonsTab, YesNo.No);
			
			if(bp.clickOnAlreadyCreatedItem(projectName, SMOKIns16InsName, 30)){
				
				log(LogStatus.INFO, "click on created institution "+SMOKIns16InsName, YesNo.No);

				// details page related tab verification
				String[] relatedTabs = {
						RelatedTab.Details.toString()};
				
				for (int i = 0; i < relatedTabs.length; i++) {
				tab = relatedTabs[i];
				ele=ip.getRelatedTab(projectName, tab, 10);
				if (ele!=null) {
				log(LogStatus.INFO,"Related Tab "+tab+" present at institutions detail page",YesNo.No);
				} else {
				sa.assertTrue(false,"Related Tab "+tab+" sholud be present at institutions detail page");
				log(LogStatus.SKIP,"Related Tab "+tab+" sholud be present at institutions detail page",YesNo.Yes);
				}
				}
				
				ThreadSleep(2000);
				
				
				// verify standard object Buttons
				np.clickOnShowMoreDropdownOnly(projectName);
				ThreadSleep(2000);
				ShowMoreActionDropDownList[] buttons={
					
						ShowMoreActionDropDownList.New_Meeting,
						ShowMoreActionDropDownList.New_Task,
						ShowMoreActionDropDownList.New_Call};
				
				ShowMoreActionDropDownList button=null;
				for (int i = 0; i < buttons.length; i++) {
				button=buttons[i];
				ele = np.actionDropdownElement(projectName, button, 10);
				if (ele!=null) {
				log(LogStatus.INFO,"Button "+button+" is present at instituion record type page",YesNo.No);
				} else {
				sa.assertTrue(false,"Button "+button+" sholud be present at instituion record type page");
				log(LogStatus.SKIP,"Button "+button+" sholud be present at instituion record type page",YesNo.Yes);
				}
				}
				
				// verify custom object Buttons
				np.clickOnShowMoreDropdownOnly(projectName);
				ThreadSleep(2000);
				ShowMoreActionDropDownList[] buttons1={
						
						ShowMoreActionDropDownList.New_Contact,
						ShowMoreActionDropDownList.New_Affiliation,
						ShowMoreActionDropDownList.New_Advisor,
						ShowMoreActionDropDownList.New_Fundraising,
						ShowMoreActionDropDownList.New_Investor,};
				
				ShowMoreActionDropDownList button1=null;
				for (int i = 0; i < buttons1.length; i++) {
				button1=buttons1[i];
				ele = np.actionDropdownElement(projectName, button1, 10);
				if (ele==null) {
				log(LogStatus.INFO,"Button "+button1+" is not present at instituion record type page",YesNo.No);
				} else {
				sa.assertTrue(false,"Button "+button1+" sholud not be present at instituion record type page");
				log(LogStatus.SKIP,"Button "+button1+" sholud not be present at instituion record type page",YesNo.Yes);
				}
				}


				
				// compact layout field visibiltiy verification
				
				String[] relatedField = {"Parent Institution",
										"Vintage",
										"Status",
										"Potential Investment",
										"Fund Strategy"
										};
				String field;
				
				for (int i = 0; i < relatedField.length; i++) {
				field = relatedField[i];
				ele=ip.getHighlightPanelFieldLabel(projectName, field, 20);
				if (ele!=null) {
				log(LogStatus.INFO,"Related field "+field+" present at institutions detail page",YesNo.No);
				} else {
				sa.assertTrue(false,"Related field "+field+" sholud be present at institutions detail page");
				log(LogStatus.SKIP,"Related field "+field+" sholud be present at institutions detail page",YesNo.Yes);
				}
				}
				
				// activity timeline visibility
				WebElement activityTimeline=ip.getActivityTimeLineBox(30);
				
				if(activityTimeline!=null){
					log(LogStatus.INFO,"Activity timeline is present in institution page",YesNo.No);
					
				}else{
					
					sa.assertTrue(false,"Activity timeline is not present in institution page");
					log(LogStatus.FAIL,"Activity timeline is not present in institution page",YesNo.Yes);
				}
				
			}else{
				sa.assertTrue(false,"Not able to click on created institution "+SMOKIns16InsName);
				log(LogStatus.FAIL,"Not able to click on created institution "+SMOKIns16InsName,YesNo.Yes);
			}
			
			
		}else{
			sa.assertTrue(false,"Not able to click on"+TabName.InstituitonsTab);
			log(LogStatus.FAIL,"Not able to click on"+TabName.InstituitonsTab,YesNo.Yes);
			
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void smokeTc026_verifyAdvisorRecordTypePage(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		NavigationPageBusineesLayer np = new NavigationPageBusineesLayer(driver);
		
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String tab;
		ThreadSleep(5000);
		
		if(bp.clickOnTab(projectName, TabName.InstituitonsTab)){
			log(LogStatus.INFO, "click on"+TabName.InstituitonsTab, YesNo.No);
			
			if(bp.clickOnAlreadyCreatedItem(projectName, SMOKIns17InsName, 30)){
				
				log(LogStatus.INFO, "click on created institution "+SMOKIns17InsName, YesNo.No);

				// details page related tab verification
				String[] relatedTabs = {
						RelatedTab.Details.toString(),
						RelatedTab.Contacts.toString(),
						RelatedTab.Clients.toString(),
						RelatedTab.Referrals.toString(),
						RelatedTab.Events.toString(),
						RelatedTab.Files.toString()};
				
				for (int i = 0; i < relatedTabs.length; i++) {
				tab = relatedTabs[i];
				ele=ip.getRelatedTab(projectName, tab, 10);
				if (ele!=null) {
				log(LogStatus.INFO,"Related Tab "+tab+" present at institutions detail page",YesNo.No);
				} else {
				sa.assertTrue(false,"Related Tab "+tab+" sholud be present at institutions detail page");
				log(LogStatus.SKIP,"Related Tab "+tab+" sholud be present at institutions detail page",YesNo.Yes);
				}
				}
				
				ThreadSleep(2000);
				
				
				// Buttons
				np.clickOnShowMoreDropdownOnly(projectName);
				ThreadSleep(2000);
				ShowMoreActionDropDownList[] buttons={
					
					// button is not presepent in advsior  showmore menu list
					//	ShowMoreActionDropDownList.New_Client,
						ShowMoreActionDropDownList.New_Contact,
						ShowMoreActionDropDownList.New_Affiliation};
				
				ShowMoreActionDropDownList button=null;
				for (int i = 0; i < buttons.length; i++) {
				button=buttons[i];
				ele = np.actionDropdownElement(projectName, button, 10);
				if (ele!=null) {
				log(LogStatus.INFO,"Button "+button+" is present at instituion record type page",YesNo.No);
				} else {
				sa.assertTrue(false,"Button "+button+" sholud be present at instituion record type page");
				log(LogStatus.SKIP,"Button "+button+" sholud be present at instituion record type page",YesNo.Yes);
				}
				}


				
				// compact layout field visibiltiy verification
				
				String[] relatedField = {"Entity Type",
										"Sector",
										"Region",
										"Phone",
										"Website"};
				String field;
				
				for (int i = 0; i < relatedField.length; i++) {
				field = relatedField[i];
				ele=ip.getHighlightPanelFieldLabel(projectName, field, 20);
				if (ele!=null) {
				log(LogStatus.INFO,"Related field "+field+" present at institutions detail page",YesNo.No);
				} else {
				sa.assertTrue(false,"Related field "+field+" sholud be present at institutions detail page");
				log(LogStatus.SKIP,"Related field "+field+" sholud be present at institutions detail page",YesNo.Yes);
				}
				}
				
				// activity timeline visibility
				WebElement activityTimeline=ip.getActivityTimeLineBox(30);
				
				if(activityTimeline!=null){
					log(LogStatus.INFO,"Activity timeline is present in institution page",YesNo.No);
					
				}else{
					
					sa.assertTrue(false,"Activity timeline is not present in institution page");
					log(LogStatus.FAIL,"Activity timeline is not present in institution page",YesNo.Yes);
				}
				
			}else{
				sa.assertTrue(false,"Not able to click on created institution "+SMOKIns17InsName);
				log(LogStatus.FAIL,"Not able to click on created institution "+SMOKIns17InsName,YesNo.Yes);
			}
			
			
		}else{
			sa.assertTrue(false,"Not able to click on"+TabName.InstituitonsTab);
			log(LogStatus.FAIL,"Not able to click on"+TabName.InstituitonsTab,YesNo.Yes);
			
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void smokeTc027_verifyIntermediaryRecordTypePage(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		NavigationPageBusineesLayer np = new NavigationPageBusineesLayer(driver);
		
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String tab;
		ThreadSleep(5000);
		
		if(bp.clickOnTab(projectName, TabName.InstituitonsTab)){
			log(LogStatus.INFO, "click on"+TabName.InstituitonsTab, YesNo.No);
			
			if(bp.clickOnAlreadyCreatedItem(projectName, SMOKIns18InsName, 30)){
				
				log(LogStatus.INFO, "click on created institution "+SMOKIns18InsName, YesNo.No);

				// details page related tab verification
				String[] relatedTabs = {
						RelatedTab.Details.toString(),
						RelatedTab.Contacts.toString(),
						RelatedTab.Deals.toString(),
						RelatedTab.Events.toString(),
						RelatedTab.Files.toString()};
				
				for (int i = 0; i < relatedTabs.length; i++) {
				tab = relatedTabs[i];
				ele=ip.getRelatedTab(projectName, tab, 10);
				if (ele!=null) {
				log(LogStatus.INFO,"Related Tab "+tab+" present at institutions detail page",YesNo.No);
				} else {
				sa.assertTrue(false,"Related Tab "+tab+" sholud be present at institutions detail page");
				log(LogStatus.SKIP,"Related Tab "+tab+" sholud be present at institutions detail page",YesNo.Yes);
				}
				}
				
				ThreadSleep(2000);
				
				
				// Buttons
				np.clickOnShowMoreDropdownOnly(projectName);
				ThreadSleep(2000);
				ShowMoreActionDropDownList[] buttons={

						
						ShowMoreActionDropDownList.New_Contact,
						ShowMoreActionDropDownList.New_Affiliation};
				
				ShowMoreActionDropDownList button=null;
				for (int i = 0; i < buttons.length; i++) {
				button=buttons[i];
				ele = np.actionDropdownElement(projectName, button, 10);
				if (ele!=null) {
				log(LogStatus.INFO,"Button "+button+" is present at instituion record type page",YesNo.No);
				} else {
				sa.assertTrue(false,"Button "+button+" sholud be present at instituion record type page");
				log(LogStatus.SKIP,"Button "+button+" sholud be present at instituion record type page",YesNo.Yes);
				}
				}

				ThreadSleep(2000);
				
				// compact layout field visibiltiy verification
				
				String[] relatedField = {"Total Deals Shown",
										"Average Deal Quality Score",
										"Website",
										"Sector",
										"Region"};
				String field;
				
				for (int i = 0; i < relatedField.length; i++) {
				field = relatedField[i];
				ele=ip.getHighlightPanelFieldLabel(projectName, field, 20);
				if (ele!=null) {
				log(LogStatus.INFO,"Related field "+field+" present at institutions detail page",YesNo.No);
				} else {
				sa.assertTrue(false,"Related field "+field+" sholud be present at institutions detail page");
				log(LogStatus.SKIP,"Related field "+field+" sholud be present at institutions detail page",YesNo.Yes);
				}
				}
				
				// activity timeline visibility
				WebElement activityTimeline=ip.getActivityTimeLineBox(30);
				
				if(activityTimeline!=null){
					log(LogStatus.INFO,"Activity timeline is present in institution page",YesNo.No);
					
				}else{
					
					sa.assertTrue(false,"Activity timeline is not present in institution page");
					log(LogStatus.FAIL,"Activity timeline is not present in institution page",YesNo.Yes);
				}
				
			}else{
				sa.assertTrue(false,"Not able to click on created institution "+SMOKIns18InsName);
				log(LogStatus.FAIL,"Not able to click on created institution "+SMOKIns18InsName,YesNo.Yes);
			}
			
			
		}else{
			sa.assertTrue(false,"Not able to click on"+TabName.InstituitonsTab);
			log(LogStatus.FAIL,"Not able to click on"+TabName.InstituitonsTab,YesNo.Yes);
			
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters("projectName")
	@Test
	public void smokeTc028_verifyLendorRecordTypePage(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		NavigationPageBusineesLayer np = new NavigationPageBusineesLayer(driver);
		
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String tab;
		ThreadSleep(5000);
		
		if(bp.clickOnTab(projectName, TabName.InstituitonsTab)){
			log(LogStatus.INFO, "click on"+TabName.InstituitonsTab, YesNo.No);
			
			if(bp.clickOnAlreadyCreatedItem(projectName, SMOKIns19InsName, 30)){
				
				log(LogStatus.INFO, "click on created institution "+SMOKIns19InsName, YesNo.No);

				// details page related tab verification
				String[] relatedTabs = {
						RelatedTab.Details.toString(),
						RelatedTab.Contacts.toString(),
						RelatedTab.Financing.toString(),
						RelatedTab.Events.toString(),
						RelatedTab.Files.toString()};
				
				for (int i = 0; i < relatedTabs.length; i++) {
				tab = relatedTabs[i];
				ele=ip.getRelatedTab(projectName, tab, 10);
				if (ele!=null) {
				log(LogStatus.INFO,"Related Tab "+tab+" present at institutions detail page",YesNo.No);
				} else {
				sa.assertTrue(false,"Related Tab "+tab+" sholud be present at institutions detail page");
				log(LogStatus.SKIP,"Related Tab "+tab+" sholud be present at institutions detail page",YesNo.Yes);
				}
				}
				
				ThreadSleep(2000);
				
				
				// Buttons
				np.clickOnShowMoreDropdownOnly(projectName);
				ThreadSleep(2000);
				ShowMoreActionDropDownList[] buttons={

						ShowMoreActionDropDownList.New_Contact,
						ShowMoreActionDropDownList.New_Financing,
						ShowMoreActionDropDownList.New_Affiliation};
				
				ShowMoreActionDropDownList button=null;
				for (int i = 0; i < buttons.length; i++) {
				button=buttons[i];
				ele = np.actionDropdownElement(projectName, button, 10);
				if (ele!=null) {
				log(LogStatus.INFO,"Button "+button+" is present at instituion record type page",YesNo.No);
				} else {
				sa.assertTrue(false,"Button "+button+" sholud be present at instituion record type page");
				log(LogStatus.SKIP,"Button "+button+" sholud be present at instituion record type page",YesNo.Yes);
				}
				}

				ThreadSleep(2000);
				
				// compact layout field visibiltiy verification
				
				String[] relatedField = {"Phone",
										"Annual Revenue",
										"Website",
										"Sector",
										"Region"};
				String field;
				
				for (int i = 0; i < relatedField.length; i++) {
				field = relatedField[i];
				ele=ip.getHighlightPanelFieldLabel(projectName, field, 20);
				if (ele!=null) {
				log(LogStatus.INFO,"Related field "+field+" present at institutions detail page",YesNo.No);
				} else {
				sa.assertTrue(false,"Related field "+field+" sholud be present at institutions detail page");
				log(LogStatus.SKIP,"Related field "+field+" sholud be present at institutions detail page",YesNo.Yes);
				}
				}
				
				// activity timeline visibility
				WebElement activityTimeline=ip.getActivityTimeLineBox(30);
				
				if(activityTimeline!=null){
					log(LogStatus.INFO,"Activity timeline is present in institution page",YesNo.No);
					
				}else{
					
					sa.assertTrue(false,"Activity timeline is not present in institution page");
					log(LogStatus.FAIL,"Activity timeline is not present in institution page",YesNo.Yes);
				}
				
			}else{
				sa.assertTrue(false,"Not able to click on created institution "+SMOKIns19InsName);
				log(LogStatus.FAIL,"Not able to click on created institution "+SMOKIns19InsName,YesNo.Yes);
			}
			
			
		}else{
			sa.assertTrue(false,"Not able to click on"+TabName.InstituitonsTab);
			log(LogStatus.FAIL,"Not able to click on"+TabName.InstituitonsTab,YesNo.Yes);
			
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters("projectName")
	@Test
	public void smokeTc029_verifyPortfolioCompanyRecordTypePage(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		NavigationPageBusineesLayer np = new NavigationPageBusineesLayer(driver);
		
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String tab;
		ThreadSleep(5000);
		
		if(bp.clickOnTab(projectName, TabName.InstituitonsTab)){
			log(LogStatus.INFO, "click on"+TabName.InstituitonsTab, YesNo.No);
			
			if(bp.clickOnAlreadyCreatedItem(projectName, SMOKIns20InsName, 30)){
				
				log(LogStatus.INFO, "click on created institution "+SMOKIns20InsName, YesNo.No);

				// details page related tab verification
				String[] relatedTabs = {
						RelatedTab.Details.toString(),
						RelatedTab.Contacts.toString(),
						RelatedTab.Deals.toString(),
						RelatedTab.Investors.toString(),
						RelatedTab.KPIs.toString(),
						RelatedTab.Events.toString(),
						RelatedTab.Files.toString()};
				
				for (int i = 0; i < relatedTabs.length; i++) {
				tab = relatedTabs[i];
				ele=ip.getRelatedTab(projectName, tab, 10);
				if (ele!=null) {
				log(LogStatus.INFO,"Related Tab "+tab+" present at institutions detail page",YesNo.No);
				} else {
				sa.assertTrue(false,"Related Tab "+tab+" sholud be present at institutions detail page");
				log(LogStatus.SKIP,"Related Tab "+tab+" sholud be present at institutions detail page",YesNo.Yes);
				}
				}
				
				ThreadSleep(2000);
				
				
				// Buttons
				np.clickOnShowMoreDropdownOnly(projectName);
				ThreadSleep(2000);
				ShowMoreActionDropDownList[] buttons={

						ShowMoreActionDropDownList.New_Contact,
						ShowMoreActionDropDownList.New_Financing,
						ShowMoreActionDropDownList.New_Investor,
						ShowMoreActionDropDownList.New_Affiliation};
				
				ShowMoreActionDropDownList button=null;
				for (int i = 0; i < buttons.length; i++) {
				button=buttons[i];
				ele = np.actionDropdownElement(projectName, button, 10);
				if (ele!=null) {
				log(LogStatus.INFO,"Button "+button+" is present at instituion record type page",YesNo.No);
				} else {
				sa.assertTrue(false,"Button "+button+" sholud be present at instituion record type page");
				log(LogStatus.SKIP,"Button "+button+" sholud be present at instituion record type page",YesNo.Yes);
				}
				}

				ThreadSleep(2000);
				
				// compact layout field visibiltiy verification
				
				String[] relatedField = {"Phone",
										"Entity Type",
										"LTM Revenue",
										"LTM EBITDA",
										"Sector",
										"Region"};
				String field;
				
				for (int i = 0; i < relatedField.length; i++) {
				field = relatedField[i];
				ele=ip.getHighlightPanelFieldLabel(projectName, field, 20);
				if (ele!=null) {
				log(LogStatus.INFO,"Related field "+field+" present at institutions detail page",YesNo.No);
				} else {
				sa.assertTrue(false,"Related field "+field+" sholud be present at institutions detail page");
				log(LogStatus.SKIP,"Related field "+field+" sholud be present at institutions detail page",YesNo.Yes);
				}
				}
				
				// activity timeline visibility
				WebElement activityTimeline=ip.getActivityTimeLineBox(30);
				
				if(activityTimeline!=null){
					log(LogStatus.INFO,"Activity timeline is present in institution page",YesNo.No);
					
				}else{
					
					sa.assertTrue(false,"Activity timeline is not present in institution page");
					log(LogStatus.FAIL,"Activity timeline is not present in institution page",YesNo.Yes);
				}
				
			}else{
				sa.assertTrue(false,"Not able to click on created institution "+SMOKIns20InsName);
				log(LogStatus.FAIL,"Not able to click on created institution "+SMOKIns20InsName,YesNo.Yes);
			}
			
			
		}else{
			sa.assertTrue(false,"Not able to click on"+TabName.InstituitonsTab);
			log(LogStatus.FAIL,"Not able to click on"+TabName.InstituitonsTab,YesNo.Yes);
			
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void smokeTc030_verifyContactRecord(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		NavigationPageBusineesLayer np = new NavigationPageBusineesLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		String tab;
		ThreadSleep(5000);
		
		
		if(bp.clickOnTab(projectName, TabName.Object2Tab)){
			log(LogStatus.INFO, "click on"+TabName.Object2Tab, YesNo.No);
			
			SMOKCon9ContactEmail=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, SMOKCon9ContactEmail, "Contacts", excelLabel.Variable_Name, "SMOKCON9",excelLabel.Contact_EmailId);

			if(cp.createContact(projectName, SMOKCon9FirstName, SMOKCon9LastName, SMOKCon9InstitutionName, SMOKCon9ContactEmail,"", null, null, CreationPage.ContactPage, null, null)){
				log(LogStatus.INFO, "Successfully created contact:"+SMOKCon9FirstName+" "+SMOKCon9LastName, YesNo.No);

				refresh(driver);
				ThreadSleep(5000);
			
				// details page related tab verification
				String[] relatedTabs = {
						RelatedTab.Details.toString(),
						RelatedTab.Network.toString(),
						RelatedTab.Investor_Relations.toString(),
						RelatedTab.Deals.toString(),
						RelatedTab.Events.toString(),
						RelatedTab.Files.toString()};
				
				for (int i = 0; i < relatedTabs.length; i++) {
				tab = relatedTabs[i];
				ele=ip.getRelatedTab(projectName, tab, 10);
				if (ele!=null) {
				log(LogStatus.INFO,"Related Tab "+tab+" present at institutions detail page",YesNo.No);
				} else {
				sa.assertTrue(false,"Related Tab "+tab+" sholud be present at institutions detail page");
				log(LogStatus.SKIP,"Related Tab "+tab+" sholud be present at institutions detail page",YesNo.Yes);
				}
				}
				
				ThreadSleep(2000);
				
				
				// Buttons
				np.clickOnShowMoreDropdownOnly(projectName);
				ThreadSleep(2000);
				ShowMoreActionDropDownList[] buttons={

						ShowMoreActionDropDownList.New_Referral,
						ShowMoreActionDropDownList.New_Affiliation};
				
				ShowMoreActionDropDownList button=null;
				for (int i = 0; i < buttons.length; i++) {
				button=buttons[i];
				ele = np.actionDropdownElement(projectName, button, 10);
				if (ele!=null) {
				log(LogStatus.INFO,"Button "+button+" is present at instituion record type page",YesNo.No);
				} else {
				sa.assertTrue(false,"Button "+button+" sholud be present at instituion record type page");
				log(LogStatus.SKIP,"Button "+button+" sholud be present at instituion record type page",YesNo.Yes);
				}
				}

				ThreadSleep(2000);
				
				// compact layout field visibiltiy verification
				
				String[] relatedField = {"Legal Name",
										"Tier",
										"Phone",
										"Last Touch Point",
										"Sector"};
				String field;
				
				for (int i = 0; i < relatedField.length; i++) {
				field = relatedField[i];
				ele=ip.getHighlightPanelFieldLabel(projectName, field, 20);
				if (ele!=null) {
				log(LogStatus.INFO,"Related field "+field+" present at institutions detail page",YesNo.No);
				} else {
				sa.assertTrue(false,"Related field "+field+" sholud be present at institutions detail page");
				log(LogStatus.SKIP,"Related field "+field+" sholud be present at institutions detail page",YesNo.Yes);
				}
				}
				
				// activity timeline visibility
				WebElement activityTimeline=ip.getActivityTimeLineBox(30);
				
				if(activityTimeline!=null){
					log(LogStatus.INFO,"Activity timeline is present in institution page",YesNo.No);
					
				}else{
					
					sa.assertTrue(false,"Activity timeline is not present in institution page");
					log(LogStatus.FAIL,"Activity timeline is not present in institution page",YesNo.Yes);
				}
				
			}else{
				sa.assertTrue(false,"Not able to create contact:"+SMOKCon9FirstName+" "+SMOKCon9LastName);
				log(LogStatus.FAIL,"Not able to create contact:"+SMOKCon9FirstName+" "+SMOKCon9LastName,YesNo.Yes);
			}
			
			
		}else{
			sa.assertTrue(false,"Not able to click on"+TabName.Object2Tab);
			log(LogStatus.FAIL,"Not able to click on"+TabName.Object2Tab,YesNo.Yes);
			
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters("projectName")
	@Test
	public void smokeTC031_verifyListViewOnContactObject(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp= new BasePageBusinessLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		String selectListLink ="All Contacts,Automation All,Birthdays This Month,Key Intermediaries,My Call List,My Contacts,New Last Week,"
				+ "New This Week,Recently Viewed Contacts,Tier 1 Intermediaries to Call,Top Bankers";
		
		ThreadSleep(5000);
		if(bp.clickOnTab(projectName, mode, TabName.ContactTab)){
			log(LogStatus.PASS,	"click on contacts tab", YesNo.No);
			
			if (click(driver, bp.getSelectListIcon(60), "Select List Icon", action.SCROLLANDBOOLEAN)) {
				ThreadSleep(5000);
				List<WebElement> lst=bp.getAllLinkOfSelectListIconOption(mode,"Contacts", 30);
				if(compareMultipleList(driver, selectListLink, lst).isEmpty()){
					log(LogStatus.PASS,	"All link of select list icon  is verified", YesNo.No);
				}else{
					
					log(LogStatus.FAIL,	"All link of select list icon  is not verified", YesNo.Yes);
					sa.assertTrue(false,"All link of select list icon  is not verified");
				}
				
			} else {
				appLog.error("Not able to click on Select List Icon");
			}
		}else{
			
			log(LogStatus.FAIL,	"not able to click on contact tab", YesNo.Yes);
			sa.assertTrue(false,"not able to click on contact tab");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
}
	

