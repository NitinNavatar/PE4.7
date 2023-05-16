package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.SmokeCommonVariables.SmokeC5_FName;
import static com.navatar.generic.SmokeCommonVariables.SmokeC5_LName;
import static com.navatar.generic.SmokeCommonVariables.Smoke_NewTask1Subject;
import static com.navatar.generic.SmokeCommonVariables.Smoke_TaskSTD1Subject;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;
import static com.navatar.generic.SmokeCommonVariables.crmUser1EmailID;
import static com.navatar.generic.SmokeCommonVariables.crmUser1FirstName;
import static com.navatar.generic.SmokeCommonVariables.crmUser1LastName;
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
import com.navatar.generic.EnumConstants.ActivityRelatedLabel;
import com.navatar.generic.EnumConstants.ActivityType;
import com.navatar.generic.EnumConstants.BulkActions_DefaultValues;
import com.navatar.generic.EnumConstants.Buttons;
import com.navatar.generic.EnumConstants.CSVLabel;
import com.navatar.generic.EnumConstants.ClickOrCheckEnableDisableCheckBox;
import com.navatar.generic.EnumConstants.CollapseExpandIcon;
import com.navatar.generic.EnumConstants.CommitmentType;
import com.navatar.generic.EnumConstants.CreateNew_DefaultValues;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.DealStage;
import com.navatar.generic.EnumConstants.EditPageLabel;
import com.navatar.generic.EnumConstants.EditViewMode;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.Fields;
import com.navatar.generic.EnumConstants.FolderAccess;
import com.navatar.generic.EnumConstants.FundraisingContactPageTab;
import com.navatar.generic.EnumConstants.GlobalActionItem;
import com.navatar.generic.EnumConstants.InstitutionPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.NavatarSetupSideMenuTab;
import com.navatar.generic.EnumConstants.NavigationMenuItems;
import com.navatar.generic.EnumConstants.NewInteractions_DefaultValues;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.navatar.generic.EnumConstants.Operator;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.PopUpName;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.ReportDashboardFolderType;
import com.navatar.generic.EnumConstants.ReportField;
import com.navatar.generic.EnumConstants.ReportFormatName;
import com.navatar.generic.EnumConstants.SDGCreationLabel;
import com.navatar.generic.EnumConstants.SDGGridName;
import com.navatar.generic.EnumConstants.SDGGridSideIcons;
import com.navatar.generic.EnumConstants.SDGLabels;
import com.navatar.generic.EnumConstants.SearchBasedOnExistingFundsOptions;
import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
import com.navatar.generic.EnumConstants.Stage;
import com.navatar.generic.EnumConstants.SubjectElement;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.Task;
import com.navatar.generic.EnumConstants.ToggleButtonGroup;
import com.navatar.generic.EnumConstants.TopOrBottom;
import com.navatar.generic.EnumConstants.ViewAllAndViewCalendarLink;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;
import com.navatar.generic.EnumConstants.searchContactInEmailProspectGrid;
import com.navatar.pageObjects.AffiliationPageBusinessLayer;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.ContactTransferTabBusinessLayer;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.DealPageBusinessLayer;
import com.navatar.pageObjects.EditPageBusinessLayer;
import com.navatar.pageObjects.EditPageErrorMessage;

import com.navatar.pageObjects.FundRaisingPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.GlobalActionPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.NavatarSetupPageBusinessLayer;
import com.navatar.pageObjects.NavigationPageBusineesLayer;
import com.navatar.pageObjects.ReportsTabBusinessLayer;
import com.navatar.pageObjects.SDGPageBusinessLayer;
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
							crmUserProfile, null)) {
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
			
			ThreadSleep(3000);
			if(appVersion!=null&&!appVersion.isEmpty()&&!appVersion.equalsIgnoreCase("")){
				
				if (setup.checkInstalledPackageVersion(appVersion, 30)) {
					log(LogStatus.PASS, "Installed App package verison:" + appVersion + " successfully verified",
							YesNo.No);

				} else {
					sa.assertTrue(false, "Installed App package verison:" + appVersion + " is not verified");
					log(LogStatus.FAIL, "Installed App package verison:" + appVersion + " is not verified", YesNo.No);
				}
				}else{
					log(LogStatus.FAIL, "Installed App package verison: "+ appVersion+" not mentioned" , YesNo.No);

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
			addRemoveTabName=tab1+"s,"+tabObj2+"s,"+tabObj3+"s,"+tabObj4+"s,"+"Fundraisings"+","+"Navatar Setup"+","+"Reports"+","+"Dashboards"+","+"Deal Center"+","+"Fund Center";
			if (lp.addTab_Lighting( addRemoveTabName, 5)) {
				log(LogStatus.INFO,"Tab added : "+addRemoveTabName,YesNo.No);
			} else {
				log(LogStatus.FAIL,"Tab not added : "+addRemoveTabName,YesNo.No);
				sa.assertTrue(false, "Tab not added : "+addRemoveTabName);
			}	
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({"projectName"})
	@Test
	public void smokeTc002_verifyHomepageLogoAndTabs(String projectName){
		
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		LoginPageBusinessLayer lp= new LoginPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String tabs	=tabObj1+"s,"+tabObj2+"s,"+tabObj3+"s,"+tabObj4+"s,"+"Fundraisings"+","+"Reports"+","+"Dashboards"+","+"Deal Center"+","+"Fund Center";

		if(mode.equalsIgnoreCase(Mode.Lightning.toString())){
			
			if(home.verifyHomepageAppLogo(appName)){
			
				log(LogStatus.PASS, "App logo in successfully verified on homepage", YesNo.No);
			}else{
			
				log(LogStatus.FAIL, "App logo in not verified on homepage", YesNo.No);
				sa.assertTrue(false,"App logo in not verified on homepage");
			}
		}
		
		if(home.verifyAddedTabsInHomepage(tabs,mode)){
			
			log(LogStatus.PASS, "Added tab is successfully verified on homepage", YesNo.No);
		}else{
			
			log(LogStatus.FAIL, "Added tab in not verified on homepage", YesNo.No);
			sa.assertTrue(false,"Added tab in not verified on homepage");
		}
				
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({"projectName"})
	@Test
	public void smokeTc003_1_createDataAndVerifyOnHomepageSDGAndComponent(String projectName){
		
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
	public void smokeTc003_2_createDataAndVerifyOnHomepageSDGAndComponent(String projectName){
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

					if (cp.createContact(projectName, SMOKCon1FirstName, SMOKCon1LastName, SMOKCon1InstitutionName, SMOKCon1ContactEmail,"", excelLabel.Phone.toString(), SMOKCon1Phone, CreationPage.ContactPage, null,SMOKCon1Tier)) {
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
				
				SMOKDeal1LogInDate= todaysDate;
				ExcelUtils.writeData(phase1DataSheetFilePath, SMOKDeal1LogInDate, "Deal", excelLabel.Variable_Name, "SMOKDeal1",excelLabel.Log_In_Date);

			//deal	
				String[][] otherlabel={{excelLabel.Source_Firm.toString(),SMOKDeal1SourceFirm},{excelLabel.Investment_Size.toString(),SMOKDeal1InvestmentSize},
						{excelLabel.Source_Contact.toString(),SMOKDeal1SourceContact},{excelLabel.Log_In_Date.toString(),SMOKDeal1LogInDate}};
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
				
				SMOKDeal2LogInDate= previousOrForwardDateAccordingToTimeZone(-5, "M/d/YYYY", BasePageBusinessLayer.AmericaLosAngelesTimeZone);
				ExcelUtils.writeData(phase1DataSheetFilePath, SMOKDeal2LogInDate, "Deal", excelLabel.Variable_Name, "SMOKDeal2",excelLabel.Log_In_Date);

				String[][] otherlabel2={{excelLabel.Source_Firm.toString(),SMOKDeal2SourceFirm},{excelLabel.Investment_Size.toString(),SMOKDeal2InvestmentSize},
						{excelLabel.Source_Contact.toString(),SMOKDeal2SourceContact},{excelLabel.Log_In_Date.toString(),SMOKDeal2LogInDate}};
				if(bp.clickOnTab(environment,mode, TabName.DealTab)){
					log(LogStatus.INFO,"Click on Tab : "+TabName.DealTab,YesNo.No);
					
					if(fp.createDeal(projectName, "", SMOKDeal2DealName, SMOKDeal2CompanyName, SMOKDeal2Stage, otherlabel2, 30)){
						
						log(LogStatus.INFO,"successfully Created deal : "+SMOKDeal2DealName,YesNo.No);	

					}else{
						
						sa.assertTrue(false,"Not Able to Create deal : "+SMOKDeal2DealName);
						log(LogStatus.SKIP,"Not Able to Create deal : "+SMOKDeal2DealName,YesNo.Yes);
					}
					
				}else{
					
					sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.DealTab);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.DealTab,YesNo.Yes);
				}
				
				String[][] label = {{excelLabel.Vintage_Year.toString(),SMOKFund1VintageYear}};
				ThreadSleep(2000);
				//fund
				if (bp.clickOnTab(environment,mode, TabName.FundsTab)) {
					log(LogStatus.INFO,"Click on Tab : "+TabName.FundsTab,YesNo.No);	
					if (fp.createFundPE(projectName,SMOKFund1FundName,"",SMOKFund1FundType,SMOKFund1InvestmentCategory, label, 15)) {
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
					if(fr.createFundRaising(environment,mode,SMOKFR1FundraisingName,SMOKFR1FundName,SMOKFR1InstitutionName, SMOKFR1Closing, SMOKFR1Satge, SMOKFR1InvestmentLikelyAmountMN, "")){
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
				String[][] taskData = {{"Due Date",SMOKTask1DueDate},{PageLabel.Status.toString(),SMOKTask1Status}};
				
				String [][] basicSection = {{PageLabel.Subject.toString(),task},{PageLabel.Related_To.toString(),SMOKTask1Name}};
					
				if (lp.createActivityTimeline("", true, "Task", basicSection, taskData, null, null, false, null, null, null, null, null, null)) {
					log(LogStatus.INFO,"Able to create task Value for : "+task,YesNo.No);
						
					ExcelUtils.writeData(phase1DataSheetFilePath,SMOKTask1DueDate, "Task1", excelLabel.Variable_Name, "SMOKTask1", excelLabel.Due_Date);
					
				} else {
					sa.assertTrue(false,"Not Able to create task Value : "+task);
					log(LogStatus.SKIP,"Not Able to create task : "+task,YesNo.Yes);	
				}
				
				lp.CRMlogout();
				sa.assertAll();
		
	}

	@Parameters("projectName")
	@Test
	public void smokeTc004_1_verifyCreatedDataOnHomepage(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String labelName="";
		String[] dealLabel = {SMOKDeal1DealName+"<break>"+SMOKDeal1Stage+"<break>"+SMOKDeal1SourceFirm+"<break>"+SMOKDeal1SourceContact,
								SMOKDeal2DealName+"<break>"+SMOKDeal2Stage+"<break>"+SMOKDeal2SourceFirm+"<break>"+SMOKDeal2SourceContact};
		
		for(int i=0;i<dealLabel.length;i++){
			
			String deal ="";
			if(i==0){
				deal=SMOKDeal1DealName;
			}
			
			if(i==1){
				deal=SMOKDeal2DealName;
				
			}
			ThreadSleep(5000);
		if (click(driver, home.sdgGridSideIcons(SDGGridName.Deals, SDGGridSideIcons.Toggle_Filters, 10), "filter icon",
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "click on filter icon so going to search deal name : " + deal, YesNo.Yes);
			if (home.SearchDealFilterDataOnHomePage(SDGGridName.Deals, "Deal", deal, Operator.Equals,
					YesNo.Yes)) {
				log(LogStatus.PASS, "Search Deal Name in filter " + deal, YesNo.No);
				ThreadSleep(7000);
				
				List<WebElement> all =home.sdgGridFirstRowData(SDGGridName.Deals);
				if(compareMultipleListSepratedByBreak(driver, dealLabel[i], all).isEmpty()){
					log(LogStatus.PASS, deal+" Deal SDG Record is verified " , YesNo.No);
					
				}else{
					log(LogStatus.PASS, deal+" Deal SDG Record is not matched ", YesNo.No);
					sa.assertTrue(false, SMOKDeal1DealName+" Deal SDG Record is not matched ");
				}
				
			} else {
				log(LogStatus.FAIL, "Not able to Search Deal Name in filter " + deal, YesNo.No);
				sa.assertTrue(false, "Not able to Search Deal Name in filter " + deal);
			}
		} else {
			log(LogStatus.FAIL, "Not able to click on filter icon so cannot search deal name : " + deal,
					YesNo.Yes);
			sa.assertTrue(false, "Not able to click on filter icon so cannot search deal name : " + deal);
		}
		refresh(driver);
	}
		
		String changedInvestmentAmount=changeNumberIntoUSDollarFormat(SMOKFR1InvestmentLikelyAmountMN);
		 labelName = SMOKFR1FundraisingName+"<break>"+SMOKFR1Satge+"<break>"+SMOKFR1Closing+"<break>"+(changedInvestmentAmount);
		if (click(driver, home.sdgGridSideIcons(SDGGridName.Fundraising, SDGGridSideIcons.Toggle_Filters, 10),
				"filter icon", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "click on filter icon so cannot search FundRaising name : " + SMOKFR1FundraisingName, YesNo.Yes);
			if (home.SearchDealFilterDataOnHomePage(SDGGridName.Fundraising, "Fundraising", SMOKFR1FundraisingName, Operator.Equals,
					YesNo.Yes)) {
				log(LogStatus.PASS, "Search FundRaising Name in filter " + SMOKFR1FundraisingName, YesNo.No);
				ThreadSleep(7000);
				
				List<WebElement> all =home.sdgGridFirstRowData(SDGGridName.Fundraising);
				if(compareMultipleListSepratedByBreak(driver, labelName, all).isEmpty()){
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

		
		 labelName = SMOKCon1FirstName+" "+SMOKCon1LastName+"<break>"+SMOKCon1InstitutionName+"<break>"+SMOKCon1Phone;
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
				if(compareMultipleListSepratedByBreak(driver, labelName, all).isEmpty()){
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
	public void smokeTc004_2_verifyWrenchIconOnHomepageSDG(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin( superAdminUserName, adminPassword );
		ThreadSleep(5000);
		
		SDGGridSideIcons[] icon ={SDGGridSideIcons.Open_SDG_Record,SDGGridSideIcons.Toggle_Filters,SDGGridSideIcons.Reload};
		for(int i=0;i<icon.length;i++){
			
			if (click(driver, home.sdgGridSideIcons(SDGGridName.Deals, icon[i], 5),
					icon[i]+": sdg  icon", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.PASS, "clicked on "+icon[i]+" sdg field icon of " + SDGGridName.Deals, YesNo.No);

				if (i == 0) {
					String parent = switchOnWindow(driver);
					if (parent != null) {
						log(LogStatus.PASS,
								SDGGridName.Deals + ":SDG is open in new tab after clicking on open sdg icon",
								YesNo.No);
						driver.close();
						driver.switchTo().window(parent);
					} else {
						log(LogStatus.FAIL,
								SDGGridName.Deals + ": SDG is not opening in new tab on clicking on open sdg icon",
								YesNo.Yes);
						sa.assertTrue(false,
								SDGGridName.Deals + ": SDG is not opening in new tab on clicking on open sdg icon");
					}
				}

				if (i == 1) {
					WebElement ele=FindElement(driver, "//div[@class='filter LARGE']", "filter expanded section", action.BOOLEAN, 10);
					if(ele!=null){
						
						log(LogStatus.PASS,
								"Filter section is displaying on clicking filter of icon of SDG:"+SDGGridName.Deals,
								YesNo.No);
					}else{
						
						log(LogStatus.FAIL,
								"Filter section is not displaying after click on filter of icon of SDG:"+SDGGridName.Deals,
								YesNo.Yes);
						sa.assertTrue(false,
								"Filter section is not displaying after click on filter of icon of SDG:"+SDGGridName.Deals);
					}
				}
				if (i == 2) {

					log(LogStatus.PASS,
							SDGGridName.Deals + ":SDG is reload successfully after click on reload icon",
							YesNo.No);
				}
			} else {

				log(LogStatus.FAIL, "Not able to click on "+icon[i]+" sdg field icon of " + SDGGridName.Deals, YesNo.Yes);
				sa.assertTrue(false, "Not able to click on "+icon[i]+" sdg field icon of " + SDGGridName.Deals);
			}
			refresh(driver);
			ThreadSleep(5000);
		}
		
		ThreadSleep(3000);
		
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
	public void smokeTc004_3_verifyWrenchIconOnHomepageSDG(String projectName){
		
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
	public void smokeTc005_createDataForTodaysTaskAndTodaysEvent(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		WebElement ele = null;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		ThreadSleep(5000);
		SMOKTask2DueDate =todaysDate;
		ExcelUtils.writeData(phase1DataSheetFilePath, SMOKTask2DueDate, "Task1", excelLabel.Variable_Name,
				"SMOKTask2", excelLabel.Due_Date);
		 
		//task
		String task = SMOKTask2Subject;
		String[][] taskData = {{"Due Date",SMOKTask2DueDate}};
		
		String [][] basicSection = {{PageLabel.Subject.toString(),task},{PageLabel.Related_To.toString(),SMOKTask2Name}};
			
		if (lp.createActivityTimeline("", true, "Task", basicSection, taskData, null, null, false, null, null, null, null, null, null)) {
			log(LogStatus.INFO,"Able to create task Value for : "+task,YesNo.No);
							
		} else {
			sa.assertTrue(false,"Not Able to create task Value : "+task);
			log(LogStatus.SKIP,"Not Able to create task : "+task,YesNo.Yes);	
		}
		
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
//		
		// remove bcoz of event creation is disable enable thogh RG 
//		List<WebElement> name2 = home.ctreatedTaskAndEventListOnHomePage(Task.TodayEvents);
//		
//			if (compareMultipleList(driver, SMOKEvent1Subject.toString(), name2).isEmpty()) {
//				log(LogStatus.PASS, "event is displaying in event section on homepage", YesNo.No);
//			} else {
//				log(LogStatus.PASS, "event is not displaying in event section on homepage", YesNo.No);
//				sa.assertTrue(false, "event is not displaying in event section on homepage");
//			}
//
//			home.clickOnTab(projectName, TabName.HomeTab);
//			ThreadSleep(5000);
//		
//		if(click(driver, home.viewAllAndviewClendarLink(Task.TodayEvents, ViewAllAndViewCalendarLink.ViewCalendar, 10), "Event view calendar link", action.BOOLEAN)){
//			
//			log(LogStatus.PASS, "click on view calendar link of event section", YesNo.No);
//			
//		}else{
//			
//			log(LogStatus.PASS, "Not able to click on view calendar link of event section", YesNo.No);
//			sa.assertTrue(false, "Not able to click on view calendar link of event section");
//		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters("projectName")
	@Test
	public void smokeTc006_verifyDealSDGInlineEditSortingDeletionOfRecord(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		String investment1 =changeNumberIntoUSDollarFormat(SMOKDeal1InvestmentSize).replace("$", "").replace(".00","");
		String investment2 =changeNumberIntoUSDollarFormat(SMOKDeal2InvestmentSize).replace("$", "").replace(".00","");

		String[] dealLabel = {SMOKDeal1DealName+"<break>"+SMOKDeal1Stage+"<break>"+investment1+"<break>"+SMOKDeal1SourceFirm+"<break>"+SMOKDeal1SourceContact,
								SMOKDeal2DealName+"<break>"+SMOKDeal2Stage+"<break>"+investment2+"<break>"+SMOKDeal2SourceFirm+"<break>"+SMOKDeal2SourceContact};
		
		for(int i=0;i<dealLabel.length;i++){
			
			String deal ="";
			if(i==0){
				deal=SMOKDeal1DealName;
			}
			
			if(i==1){
				deal=SMOKDeal2DealName;
				
			}
			ThreadSleep(5000);
		if (click(driver, home.sdgGridSideIcons(SDGGridName.Deals, SDGGridSideIcons.Toggle_Filters, 10), "filter icon",
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "click on filter icon so going to search deal name : " + deal, YesNo.Yes);
			if (home.SearchDealFilterDataOnHomePage(SDGGridName.Deals, "Deal", deal, Operator.Equals,
					YesNo.Yes)) {
				log(LogStatus.PASS, "Search Deal Name in filter " + deal, YesNo.No);
				ThreadSleep(7000);
				
				List<WebElement> all =home.sdgGridFirstRowData(SDGGridName.Deals);
				if(compareMultipleListSepratedByBreak(driver, dealLabel[i], all).isEmpty()){
					log(LogStatus.PASS, deal+" Deal SDG Record is verified " , YesNo.No);
					
				}else{
					log(LogStatus.PASS, deal+" Deal SDG Record is not matched ", YesNo.No);
					sa.assertTrue(false, SMOKDeal1DealName+" Deal SDG Record is not matched ");
				}
				
			} else {
				log(LogStatus.FAIL, "Not able to Search Deal Name in filter " + deal, YesNo.No);
				sa.assertTrue(false, "Not able to Search Deal Name in filter " + deal);
			}
		} else {
			log(LogStatus.FAIL, "Not able to click on filter icon so cannot search deal name : " + deal,
					YesNo.Yes);
			sa.assertTrue(false, "Not able to click on filter icon so cannot search deal name : " + deal);
		}
		refresh(driver);
	}
		
		// filter by stage and verify
		if (click(driver, home.sdgGridSideIcons(SDGGridName.Deals, SDGGridSideIcons.Toggle_Filters, 10), "filter icon",
				action.SCROLLANDBOOLEAN)) {
			if (home.SearchDealFilterDataOnHomePage(SDGGridName.Deals, excelLabel.Stage.toString(), "",Operator.NDASigned, 
					YesNo.No)) {
				log(LogStatus.PASS, "Search Stage NDA Signed in filter stage" , YesNo.No);
				ThreadSleep(7000);
				
				click(driver, home.sdgGridSideIcons(SDGGridName.Deals, SDGGridSideIcons.Toggle_Filters, 10), "filter icon",
						action.SCROLLANDBOOLEAN);
				
				List<WebElement> all =home.sdgGridFirstRowData(SDGGridName.Deals);
				if(compareMultipleListSepratedByBreak(driver, dealLabel[1], all).isEmpty()){
					log(LogStatus.PASS, SMOKDeal2DealName+" Deal SDG Record is verified " , YesNo.No);
					
				}else{
					log(LogStatus.PASS, SMOKDeal2DealName+" Deal SDG Record is not matched ", YesNo.No);
					sa.assertTrue(false, SMOKDeal2DealName+" Deal SDG Record is not matched ");
				}
			
			} else {
				log(LogStatus.FAIL, "Not able to Search stage Name in filter " + "NDA Signed", YesNo.No);
				sa.assertTrue(false, "Not able to Search Deal Name in filter NDA Signed");
			}
		} else {
			log(LogStatus.FAIL, "Not able to click on filter icon so cannot search deal name : " + SDGGridName.Deals,
					YesNo.Yes);
			sa.assertTrue(false, "Not able to click on filter icon so cannot search deal name : " + SDGGridName.Deals);
		}
		
		//filter by stage with blank
		if (click(driver, home.sdgGridSideIcons(SDGGridName.Deals, SDGGridSideIcons.Toggle_Filters, 10), "filter icon",
				action.SCROLLANDBOOLEAN)) {
			
			if (home.SearchDealFilterDataOnHomePage(SDGGridName.Deals, excelLabel.Stage.toString(), "",Operator.Blank, 
					YesNo.No)) {
				log(LogStatus.PASS, "Search Stage NDA Signed in filter stage" , YesNo.No);
				ThreadSleep(7000);
				
				click(driver, home.sdgGridSideIcons(SDGGridName.Deals, SDGGridSideIcons.Toggle_Filters, 10), "filter icon",
						action.SCROLLANDBOOLEAN);
				
			} else {
				log(LogStatus.FAIL, "Not able to Search stage Name in filter " + "Blank", YesNo.No);
				sa.assertTrue(false, "Not able to Search Deal Name in filter Blank");
			}
				
		} else {
			log(LogStatus.FAIL, "Not able to click on filter icon so cannot search deal name : " + SDGGridName.Deals,
					YesNo.Yes);
			sa.assertTrue(false, "Not able to click on filter icon so cannot search deal name : " + SDGGridName.Deals);
		}
		
		// verify data after selecting stage blank
		for(int i=0;i<dealLabel.length;i++){
			
			String deal ="";
			if(i==0){
				deal=SMOKDeal1DealName;
			}
			
			if(i==1){
				deal=SMOKDeal2DealName;
				
			}
			ThreadSleep(5000);
		if (click(driver, home.sdgGridSideIcons(SDGGridName.Deals, SDGGridSideIcons.Toggle_Filters, 10), "filter icon",
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "click on filter icon so going to search deal name : " + deal, YesNo.Yes);
			if (home.SearchDealFilterDataOnHomePage(SDGGridName.Deals, "Deal", deal, Operator.Equals,
					YesNo.Yes)) {
				log(LogStatus.PASS, "Search Deal Name in filter " + deal, YesNo.No);
				ThreadSleep(7000);
				click(driver, home.sdgGridSideIcons(SDGGridName.Deals, SDGGridSideIcons.Toggle_Filters, 10), "filter icon",
						action.SCROLLANDBOOLEAN);
				
				List<WebElement> all =home.sdgGridFirstRowData(SDGGridName.Deals);
				if(compareMultipleListSepratedByBreak(driver, dealLabel[i], all).isEmpty()){
					log(LogStatus.PASS, deal+" Deal SDG Record is verified " , YesNo.No);
					
				}else{
					log(LogStatus.PASS, deal+" Deal SDG Record is not matched ", YesNo.No);
					sa.assertTrue(false, SMOKDeal1DealName+" Deal SDG Record is not matched ");
				}
				
			} else {
				log(LogStatus.FAIL, "Not able to Search Deal Name in filter " + deal, YesNo.No);
				sa.assertTrue(false, "Not able to Search Deal Name in filter " + deal);
			}
		} else {
			log(LogStatus.FAIL, "Not able to click on filter icon so cannot search deal name : " + deal,
					YesNo.Yes);
			sa.assertTrue(false, "Not able to click on filter icon so cannot search deal name : " + deal);
		}
		refresh(driver);
	}
		
		if (click(driver, home.sdgGridSideIcons(SDGGridName.Deals, SDGGridSideIcons.Toggle_Filters, 10), "filter icon",
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "click on filter icon so going to search deal name : " + SMOKDeal1DealName, YesNo.Yes);
			if (home.SearchDealFilterDataOnHomePage(SDGGridName.Deals, "Deal", SMOKDeal1DealName, Operator.Equals,
					YesNo.Yes)) {
				log(LogStatus.PASS, "Search Deal Name in filter " + SMOKDeal1DealName, YesNo.No);
				ThreadSleep(7000);
				click(driver, home.sdgGridSideIcons(SDGGridName.Deals, SDGGridSideIcons.Toggle_Filters, 10), "filter icon",
						action.BOOLEAN);
				ThreadSleep(3000);
				if (home.clickOnEditButtonOnSDGGridOnHomePage(projectName,SMOKDeal1DealName , excelLabel.Investment_Size.toString(), 10)) {
					ThreadSleep(3000);
					log(LogStatus.PASS, "mouse over on Investmetn column of Deal : "+SMOKDeal1DealName, YesNo.No);

					WebElement ele=home.SDGInputTextbox(projectName, excelLabel.Investment_Size.toString(), 10);
					sendKeys(driver, ele, SMOKDeal1UpdatedInvestmentSize, "title textbox", action.BOOLEAN);
					
					if(home.clickOnCheckboxOnSDGGridOnHomePage(projectName, SMOKDeal1DealName, excelLabel.Investment_Size.toString(), 10)){
						ThreadSleep(3000);
						log(LogStatus.INFO, "successfully clicked on checkbox button", YesNo.No);

						if (click(driver, home.getsdgSaveButton(projectName,10), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "successfully clicked on save button", YesNo.No);
							ThreadSleep(2000);
							
						}else{
							log(LogStatus.ERROR, "Not able to click on save button of deal "+SMOKDeal1DealName, YesNo.Yes);
							sa.assertTrue(false,"Not able to click on save button of deal "+SMOKDeal1DealName );
						}
					}else{
						log(LogStatus.ERROR, "Not able to click on checkbox of deal "+SMOKDeal1DealName, YesNo.Yes);
						sa.assertTrue(false,"Not able to click on checkbox of deal "+SMOKDeal1DealName );
					}
				
				}else {
					log(LogStatus.ERROR, "could not click on edit button : "+SMOKDeal1DealName, YesNo.Yes);
					sa.assertTrue(false,"could not click on edit button: "+SMOKDeal1DealName );
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
		String updatedInvestment =changeNumberIntoUSDollarFormat(SMOKDeal1UpdatedInvestmentSize).replace("$", "").replace(".00","");
		
		//verify upadte deal deata
		String labelValue=SMOKDeal1DealName+"<break>"+SMOKDeal1Stage+"<break>"+updatedInvestment+"<break>"+SMOKDeal1SourceFirm+"<break>"+SMOKDeal1SourceContact;
		if (click(driver, home.sdgGridSideIcons(SDGGridName.Deals, SDGGridSideIcons.Toggle_Filters, 10), "filter icon",
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "click on filter icon so going to search deal name : " + SMOKDeal1DealName, YesNo.Yes);
			if (home.SearchDealFilterDataOnHomePage(SDGGridName.Deals, "Deal", SMOKDeal1DealName, Operator.Equals,
					YesNo.Yes)) {
				log(LogStatus.PASS, "Search Deal Name in filter " + SMOKDeal1DealName, YesNo.No);
				ThreadSleep(7000);
				click(driver, home.sdgGridSideIcons(SDGGridName.Deals, SDGGridSideIcons.Toggle_Filters, 10), "filter icon",
						action.SCROLLANDBOOLEAN);
				
				List<WebElement> all =home.sdgGridFirstRowData(SDGGridName.Deals);
				if(compareMultipleListSepratedByBreak(driver, labelValue, all).isEmpty()){
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
		
		// delete link
		
		if (click(driver, home.sdgGridSideIcons(SDGGridName.Deals, SDGGridSideIcons.Toggle_Filters, 10), "filter icon",
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "click on filter icon so going to search deal name : " + SMOKDeal2DealName, YesNo.Yes);
			if (home.SearchDealFilterDataOnHomePage(SDGGridName.Deals, "Deal", SMOKDeal2DealName, Operator.Equals,
					YesNo.Yes)) {
				log(LogStatus.PASS, "Search Deal Name in filter " + SMOKDeal2DealName, YesNo.No);
				ThreadSleep(7000);
				click(driver, home.sdgGridSideIcons(SDGGridName.Deals, SDGGridSideIcons.Toggle_Filters, 10), "filter icon",
						action.SCROLLANDBOOLEAN);
				ThreadSleep(3000);
				if(clickUsingJavaScript(driver, home.sdgGridHeadersDealsNameList().get(0), "first deal name list", action.BOOLEAN)){
					log(LogStatus.PASS, "click on deal name link of deal:" + SMOKDeal2DealName, YesNo.No);
					ThreadSleep(3000);

					String parent =switchOnWindow(driver);
					
					if(home.clickOnShowMoreActionDownArrow(projectName, PageName.DealPage, ShowMoreActionDropDownList.Delete, 30)){
						log(LogStatus.PASS, "click on delete button of deal:" + SMOKDeal2DealName, YesNo.No);

						ThreadSleep(5000);
						if(click(driver, home.getDeleteButtonOnDeletePopUp(projectName, 30), "Delete popup buttton", action.BOOLEAN)){
							ThreadSleep(5000);
							log(LogStatus.PASS, "click on delete confirm popup button of deal:" + SMOKDeal2DealName, YesNo.No);

							driver.close();
							driver.switchTo().window(parent);
							
							if (click(driver, home.sdgGridSideIcons(SDGGridName.Deals, SDGGridSideIcons.Reload, 10), "reload icon",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.PASS, "click on Reload icon on deal SDG of deal:" + SMOKDeal2DealName, YesNo.No);

								ThreadSleep(5000);

							} else {
								log(LogStatus.FAIL, "Not able to click on reload icon so cannot search deal name : " + SMOKDeal2DealName,
										YesNo.Yes);
								sa.assertTrue(false, "Not able to click on reload icon so cannot search deal name : " + SMOKDeal2DealName);
							}
						}else{
							log(LogStatus.FAIL, "Not able to click on delete confirm popup button of deal: " + SMOKDeal2DealName, YesNo.No);
							sa.assertTrue(false, "Not able to click on delete confirm popup button of deal: " + SMOKDeal2DealName);
						}
					}else{
						log(LogStatus.FAIL, "Not able to click on delete button of deal: " + SMOKDeal2DealName, YesNo.No);
						sa.assertTrue(false, "Not able to click on delete button of deal: " + SMOKDeal2DealName);
						
					}
				}else{
					
					log(LogStatus.FAIL, "Not able to clickon  Deal Name link " + SMOKDeal2DealName, YesNo.No);
					sa.assertTrue(false, "Not able to clickon  Deal Name link " + SMOKDeal2DealName);
				}

			} else {
				log(LogStatus.FAIL, "Not able to Search Deal Name in filter " + SMOKDeal2DealName, YesNo.No);
				sa.assertTrue(false, "Not able to Search Deal Name in filter " + SMOKDeal2DealName);
			}
		} else {
			log(LogStatus.FAIL, "Not able to click on filter icon so cannot search deal name : " + SMOKDeal2DealName,
					YesNo.Yes);
			sa.assertTrue(false, "Not able to click on filter icon so cannot search deal name : " + SMOKDeal2DealName);
		}
		
		//verify after delete data
		
		if (click(driver, home.sdgGridSideIcons(SDGGridName.Deals, SDGGridSideIcons.Toggle_Filters, 10), "filter icon",
				action.SCROLLANDBOOLEAN)) {
			log(LogStatus.PASS, "click on filter icon so going to search deal name : " + SMOKDeal1DealName, YesNo.Yes);
			if (home.SearchDealFilterDataOnHomePage(SDGGridName.Deals, "Deal", SMOKDeal2DealName, Operator.Equals,
					YesNo.Yes)) {
				log(LogStatus.PASS, "Search Deal Name in filter " + SMOKDeal2DealName, YesNo.No);
				ThreadSleep(7000);
				click(driver, home.sdgGridSideIcons(SDGGridName.Deals, SDGGridSideIcons.Toggle_Filters, 10), "filter icon",
						action.SCROLLANDBOOLEAN);
				
				List<WebElement> all =home.sdgGridFirstRowData(SDGGridName.Deals);
				if(all.size()==0){
					log(LogStatus.PASS, SMOKDeal2DealName+" Deal is deleted successflly  " , YesNo.No);
					
				}else{
					log(LogStatus.PASS, SMOKDeal2DealName+" Deal is not deleted present in SDG grid after delete ", YesNo.No);
					sa.assertTrue(false, SMOKDeal2DealName+" Deal is not deleted present in SDG grid after delete ");
				}
				
			} else {
				log(LogStatus.FAIL, "Not able to Search Deal Name in filter " + SMOKDeal2DealName, YesNo.No);
				sa.assertTrue(false, "Not able to Search Deal Name in filter " + SMOKDeal2DealName);
			}
		} else {
			log(LogStatus.FAIL, "Not able to click on filter icon so cannot search deal name : " + SMOKDeal2DealName,
					YesNo.Yes);
			sa.assertTrue(false, "Not able to click on filter icon so cannot search deal name : " + SMOKDeal1DealName);
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
		
	@Parameters("projectName")
	@Test
	public void smokeTc007_verifyNavigationMenuOnHomepage(String projectName){
		
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String filesName = "";
		String iconName="";
		String iconValue="";

		// Verification on navigation menu
		navigationMenuName = NavigationMenuItems.Create.toString();

		filesName=BulkActions_DefaultValues.Bulk_Email.toString()+","+
				BulkActions_DefaultValues.Bulk_Fundraising.toString()+","+
				BulkActions_DefaultValues.Bulk_Commitments.toString();

		iconName="add";
		 iconValue= getAttribute(driver, home.getNavigationMenuLinkIcon(navigationMenuName, 30), "","class");
		if(iconValue.contains(iconName)){
			
			log(LogStatus.INFO, "Lightning icon is verified for navigation menu:"+navigationMenuName, YesNo.No);

		}else{
			log(LogStatus.ERROR, "Lightning icon is not verified for navigation menu:"+navigationMenuName, YesNo.Yes);
			sa.assertTrue(false,"Lightning icon is not verified for navigation menu:"+navigationMenuName);
			
		}
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

		navigationMenuName = NavigationMenuItems.Create.toString();

		filesName=NewInteractions_DefaultValues.Call.toString()+","+
				NewInteractions_DefaultValues.Task.toString();

		iconName="add";
		 iconValue= getAttribute(driver, home.getNavigationMenuLinkIcon(navigationMenuName, 30), "","class");
		if(iconValue.contains(iconName)){
			
			log(LogStatus.INFO, "Notepad icon is verified for navigation menu:"+navigationMenuName, YesNo.No);

		}else{
			log(LogStatus.ERROR, "Notepad icon is not verified for navigation menu:"+navigationMenuName, YesNo.Yes);
			sa.assertTrue(false,"Notepad icon is not verified for navigation menu:"+navigationMenuName);
			
		}
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


		navigationMenuName = NavigationMenuItems.Create.toString();

		filesName=CreateNew_DefaultValues.New_Deal.toString()+","+
				CreateNew_DefaultValues.New_Institution.toString()+","+
				CreateNew_DefaultValues.New_Contact.toString();
		
		iconName="add";
		 iconValue= getAttribute(driver, home.getNavigationMenuLinkIcon(navigationMenuName, 30), "","class");
		if(iconValue.contains(iconName)){
			
			log(LogStatus.INFO, "Plus(Add) icon is verified for navigation menu:"+navigationMenuName, YesNo.No);

		}else{
			log(LogStatus.ERROR, "Plus(Add) icon is not verified for navigation menu:"+navigationMenuName, YesNo.Yes);
			sa.assertTrue(false,"Plus(Add) icon is not verified for navigation menu:"+navigationMenuName);
			
		}

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
	public void smokeTc008_createDataForBulkAction(String projectName){
		
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
		String phone="";
		String[][] contacts = {{ SMOKCon2FirstName, SMOKCon2LastName ,SMOKCon2InstitutionName,SMOKCon2ContactEmail,SMOKCon2Phone} ,
							{ SMOKCon3FirstName, SMOKCon3LastName ,SMOKCon3InstitutionName,SMOKCon3ContactEmail,SMOKCon3Phone},
							{ SMOKCon4FirstName, SMOKCon4LastName ,SMOKCon4InstitutionName,SMOKCon4ContactEmail,SMOKCon4Phone},
							{ SMOKCon5FirstName, SMOKCon5LastName ,SMOKCon5InstitutionName,SMOKCon5ContactEmail,SMOKCon5Phone},
							{ SMOKCon6FirstName, SMOKCon6LastName ,SMOKCon6InstitutionName,SMOKCon6ContactEmail,SMOKCon6Phone},
							{ SMOKCon7FirstName, SMOKCon7LastName ,SMOKCon7InstitutionName,SMOKCon7ContactEmail,SMOKCon7Phone}};

		ThreadSleep(2000);
		// contact
		for (String[] contact : contacts) {
			
				if (bp.clickOnTab(environment,mode, TabName.ContactTab)) {
					log(LogStatus.INFO,"Click on Tab : "+TabName.ContactTab,YesNo.No);	
					firstName = contact[0];
					lastName = contact[1];
					legalName=contact[2];
					email=contact[3];
					phone =contact[4];
					
					if (cp.createContact(projectName, firstName, lastName, legalName, email,"", excelLabel.Phone.toString(), phone, CreationPage.ContactPage, null,null)) {
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
				
				String[][] labelWithValue ={{excelLabel.Vintage_Year.toString(),SMOKFund2VintageYear}};
				//fund
				if (bp.clickOnTab(environment,mode, TabName.FundsTab)) {
					log(LogStatus.INFO,"Click on Tab : "+TabName.FundsTab,YesNo.No);	
					if (fp.createFundPE(projectName,SMOKFund2FundName,"",SMOKFund2FundType,SMOKFund2InvestmentCategory, labelWithValue, 15)) {
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
	public void smokeTc009_1_enableDisableCheckboxOfBulkAction(String projectName){
		
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
	}
	
	@Parameters("projectName")
	@Test
	public void smokeTc009_2_enableDisableINVAndCreateCustomNavigationLink(String projectName){
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
		navigationMenuName = NavigationMenuItems.Bulk_Actions.toString();
		String navigationTypeValue=navigationMenuName;
		boolean flag=false;

		navigationMenuName = NavigationMenuItems.Bulk_Actions.toString();
		String[] navigationLabel = {BulkActions_DefaultValues.Individual_Investor_Creation.toString()};
	

			setupSideMenuTab=navatarSetupSideMenuTab[0];
			ThreadSleep(5000);
			log(LogStatus.INFO, "<<<<<< Going to uncheck >>>>>>>", YesNo.No);
			
				np.EnableOrDisableSettingOnNavatarSetUp(projectName, setupSideMenuTab, false);
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
			log(LogStatus.INFO, "<<<<<< Going to Check >>>>>>>", YesNo.No);
			
			if(np.EnableOrDisableSettingOnNavatarSetUp(projectName, setupSideMenuTab, true)){
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
				refresh(driver);
				ThreadSleep(5000);
			if(flag){
				
				if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
					log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
					WebElement ele2 = npbl.getNavigationLabel(projectName, navigationLabel[0], action.BOOLEAN, 10);
					
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

			//np.EnableOrDisableSettingOnNavatarSetUp(projectName, setupSideMenuTab, true);
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
			
			String updatedNavigationLabel ="Update "+navigationLabel[0].toString();
			String[][] editedLabel= {{navigationLabel1,updatedNavigationLabel},
					{orderLabel,"0"}};

			if (npbl.clickOnTab(projectName, navigationTab)) {
				log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);
				if(npbl.clickOnAlreadyCreatedItem(projectName, TabName.Navigation,navigationLabel[0], 30)) {
					log(LogStatus.INFO, "Click on "+navigationLabel[0]+" going to edit ", YesNo.No);
					
					npbl.enteringValueForNavigation(projectName, editedLabel, action.BOOLEAN, 30);
					ThreadSleep(2000);
					if (click(driver,  npbl.getNavigationTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.ERROR, "Click on save Button ", YesNo.No);
						ThreadSleep(5000);
					} else {
						log(LogStatus.ERROR, "Not Able to Click on save Button ", YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on save Button ");
					}
					
				}else {
					log(LogStatus.ERROR, "Not Able to Click on created item so cannot Update", YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on created item so cannot update");

				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on Tab : "+navigationTab, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on Tab : "+navigationTab);
			}
			refresh(driver);
			ThreadSleep(5000);
			
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				WebElement ele3 = npbl.getNavigationLabel(projectName, updatedNavigationLabel, action.BOOLEAN, 10);
				if (ele3!=null) {
					log(LogStatus.INFO, updatedNavigationLabel+" is present on "+navigationMenuName, YesNo.No);
				} else {
					log(LogStatus.ERROR, updatedNavigationLabel+" should be present on "+navigationMenuName, YesNo.Yes);
					sa.assertTrue(false,updatedNavigationLabel+" should be present on "+navigationMenuName);

				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot uncheck presence of "+updatedNavigationLabel, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot uncheck presence of "+updatedNavigationLabel);
			}	
			
			if (npbl.clickOnTab(projectName, navigationTab)) {
				log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);
				if(npbl.clickOnAlreadyCreatedItem(projectName, TabName.Navigation,updatedNavigationLabel, 30)) {
					log(LogStatus.INFO, "Click on "+updatedNavigationLabel+" going to delete ", YesNo.No);
					if(npbl.clickOnShowMoreActionDownArrow(projectName, PageName.NavigationPage, ShowMoreActionDropDownList.Delete, 30)){
						log(LogStatus.INFO, "Click on show more action delete button on navaigation "+updatedNavigationLabel, YesNo.No);
						if(click(driver, npbl.getDeleteButtonOnDeletePopUp(projectName, 30), "delete button", action.BOOLEAN)){
							log(LogStatus.INFO, updatedNavigationLabel+"deleted successfully", YesNo.No);

							ThreadSleep(2000);
						}else{
							
							log(LogStatus.ERROR, "Not Able to Click on new button so cannot Update", YesNo.Yes);
							sa.assertTrue(false, "Not Able to Click on new button so cannot update");
						}
					}else{
						log(LogStatus.ERROR, "Not Able to Click on new button so cannot Update", YesNo.Yes);
						sa.assertTrue(false, "Not Able to Click on new button so cannot update");
					}
				}else {
					log(LogStatus.ERROR, "Not Able to Click on new button so cannot Update", YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on new button so cannot update");
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on Tab : "+navigationTab, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on Tab : "+navigationTab);
			}
			refresh(driver);
			ThreadSleep(5000);
			
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				WebElement ele3 = npbl.getNavigationLabel(projectName, updatedNavigationLabel, action.BOOLEAN, 10);
				if (ele3==null) {
					log(LogStatus.INFO, updatedNavigationLabel+" is not present after delete on "+navigationMenuName, YesNo.No);
				} else {
					log(LogStatus.ERROR, updatedNavigationLabel+" should not be present after delete on "+navigationMenuName, YesNo.Yes);
					sa.assertTrue(false,updatedNavigationLabel+" should not be present after delete on "+navigationMenuName);

				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot uncheck presence of "+updatedNavigationLabel, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot uncheck presence of "+updatedNavigationLabel);
			}	
			
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters("projectName")
	@Test
	public void smokeTc010_verifyBulkActioMenu(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ReportsTabBusinessLayer report = new ReportsTabBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword);
		String[] splitedReportFolderName = removeNumbersFromString(SmokeReport2FolderName);
		SmokeReport2FolderName = splitedReportFolderName[0] + lp.generateRandomNumber();
		if (report.createCustomReportOrDashboardFolder(environment, SmokeReport2FolderName,
				ReportDashboardFolderType.ReportFolder, FolderAccess.ReadOnly)) {

			ExcelUtils.writeData(phase1DataSheetFilePath, SmokeReport2FolderName, "Report", excelLabel.Variable_Name, "SmokeReport2",
					excelLabel.Report_Folder_Name);
			String[] splitedReportName = removeNumbersFromString(SmokeReport2Name);
			SmokeReport2Name = splitedReportName[0] + lp.generateRandomNumber();

			ReportField[] field={ReportField.ContactID,ReportField.Phone,ReportField.Email,ReportField.Contact_Full_Name};
			
			ExcelUtils.writeData(phase1DataSheetFilePath, SmokeReport2Name, "Report", excelLabel.Variable_Name, "SmokeReport2",
					excelLabel.Report_Name);
			if (report.createCustomReportForFolder(environment, mode, SmokeReport2FolderName,ReportFormatName.Null,SmokeReport2Name,
					SmokeReport2Name, SmokeReport2Type, field, SmokeReport2Show, null, SmokeReport2Range, null, null)) {
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
				ThreadSleep(5000);
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
						ThreadSleep(5000);
						switchToFrame(driver, 30, home.getCreateFundraisingsFrame_Lighting(60));

						ThreadSleep(5000);
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
															if(hp.clickOnAlreadyCreatedItem(projectName, fr, 120)) {
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
						String futureDate=previousOrForwardDateAccordingToTimeZone(20, "M/d/YYY", BasePageBusinessLayer.AmericaLosAngelesTimeZone);
						
						String[][] commitmentInformation= {{limitedPartner,"200000",partnership,todaysDate},{limitedPartner,"300000",partnership,futureDate}};
						ThreadSleep(5000);
						if(hp.selectFundraisingNameOrCommitmentType(environment, mode, SMOKFR2FundraisingName, null, null, null, CommitmentType.fundraisingName)) {
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
	public void smokeTc011_verifyNewInteractionNavigationMenu(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		NavigationPageBusineesLayer  npbl = new NavigationPageBusineesLayer(driver) ;
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		navigationMenuName = NavigationMenuItems.New_Interactions.toString();

		String[]  newInteractionsNavigationLinks = {NewInteractions_DefaultValues.Call.toString(),
				NewInteractions_DefaultValues.Task.toString()};
		int i=0;
		boolean flag = false;
		String adminUerName = crmUser1FirstName+" "+crmUser1LastName;
		String subject ="";
		String dueDate=previousOrForwardDateAccordingToTimeZone(2, "M/d/YYYY", BasePageBusinessLayer.AmericaLosAngelesTimeZone);
		String contactNAme= SMOKCon1FirstName+" "+SMOKCon1LastName;
		String[][] dropDownLabelWithValues = new String[3][];

		for (i=0;i<newInteractionsNavigationLinks.length;i++) {
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
				if (flag) {

					refresh(driver);
					ThreadSleep(5000);
					if (i==0) {
						
						subject =SMOKTask3Subject;
						String[][] basicCall= {{excelLabel.Subject.toString(),subject},{excelLabel.Related_To.toString(),contactNAme}};
						String[][] advanceCall= {{"Date",dueDate}};
						
						ExcelUtils.writeData(phase1DataSheetFilePath,dueDate, "Task1", excelLabel.Variable_Name, "SMOKTask3", excelLabel.Due_Date);
						
						if(lp.createActivityTimeline("", true,NewInteractions_DefaultValues.Call.toString(), basicCall, advanceCall, null, null, false, null, null, null, null, null, null)) {
							log(LogStatus.INFO,"successfully created : "+subject+" for "+newInteractionsNavigationLink,  YesNo.No);

						}else {
							sa.assertTrue(false,"Not able to create : "+subject+" for "+newInteractionsNavigationLink);
							log(LogStatus.SKIP,"Not abel to create : "+subject+" for "+newInteractionsNavigationLink,YesNo.Yes);
						}

					} else{
						subject =SMOKTask5Subject;
						ExcelUtils.writeData(phase1DataSheetFilePath,dueDate, "Task1", excelLabel.Variable_Name, "SMOKTask5", excelLabel.Due_Date);
						String[][] basicTask= {{excelLabel.Subject.toString(),subject},{excelLabel.Related_To.toString(),contactNAme}};
						String[][] advanceTask= {{"Date",dueDate}};
						

						if(lp.createActivityTimeline("", true,NewInteractions_DefaultValues.Call.toString(), basicTask, advanceTask, null, null, false, null, null, null, null, null, null)) {
							log(LogStatus.INFO,"successfully created : "+subject+" for "+newInteractionsNavigationLink,  YesNo.No);

						}else {
							sa.assertTrue(false,"Not able to create : "+subject+" for "+newInteractionsNavigationLink);
							log(LogStatus.SKIP,"Not abel to create : "+subject+" for "+newInteractionsNavigationLink,YesNo.Yes);
						}
					}
	
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
							refresh(driver);
							ThreadSleep(5000);	
					
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
	public void smokeTc012_verifyCreateNewNavigationMenu(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		NavigationPageBusineesLayer  npbl = new NavigationPageBusineesLayer(driver) ;
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		navigationMenuName = NavigationMenuItems.Create.toString();

		String[]  createNewNavigationLinks = {CreateNew_DefaultValues.New_Deal.toString(),
				CreateNew_DefaultValues.New_Institution.toString(),
				CreateNew_DefaultValues.New_Contact.toString()};
		int i=0;
		boolean flag = false;
		WebElement ele=null;
		for (i=0;i<createNewNavigationLinks.length;i++) {
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

	@Parameters({ "projectName"})
	@Test
	public void SmokeTc013_1_AddContactTransferButtonOnTheContactPage(String projectName) {

		ContactTransferTabBusinessLayer ctt = new ContactTransferTabBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavatarSetupPageBusinessLayer np= new NavatarSetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		boolean flag=false;
		NavatarSetupSideMenuTab setupSideMenuTab=NavatarSetupSideMenuTab.ContactTransfer;
		flag=np.EnableOrDisableSettingOnNavatarSetUp(projectName, setupSideMenuTab, true);
		if (flag) {
			log(LogStatus.INFO, "Able to Enable "+setupSideMenuTab , YesNo.No);
		} else {
			log(LogStatus.ERROR, "Not Able to Enable "+setupSideMenuTab , YesNo.Yes);
			sa.assertTrue(false,"Not Able to Enable "+setupSideMenuTab);

		}
		
			if (ctt.clickOnNavatarSetupSideMenusTab(projectName, NavatarSetupSideMenuTab.ContactTransfer)) {
				appLog.error("Clicked on Contact Transfer Tab");
				if (click(driver, ctt.getEditButtonforNavatarSetUpSideMenuTab(projectName,NavatarSetupSideMenuTab.ContactTransfer, 10), "Edit Button", action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Edit Button", YesNo.No);

					ThreadSleep(5000);
					String keepActivitiesDefaultValue = "Old Institution Only";
					String defaultvalue = getSelectedOptionOfDropDown(driver,ctt.getKeepActivitiesAtSelectList(environment, mode, EditViewMode.Edit, 10),keepActivitiesDefaultValue, "Text");
					if (keepActivitiesDefaultValue.equalsIgnoreCase(defaultvalue)) {
						log(LogStatus.INFO, "Keep Activities Default Value Matched: " + defaultvalue, YesNo.No);
					} else {
						sa.assertTrue(false, "Keep Activities Default default value not matched Actual : "+ defaultvalue + " \t Expected : " + keepActivitiesDefaultValue);
						log(LogStatus.INFO, "Keep Activities Default default value not matched Actual : " + defaultvalue+ " \t Expected : " + keepActivitiesDefaultValue, YesNo.Yes);
					}

					String includeActivities = "Contact Only";
					defaultvalue = getSelectedOptionOfDropDown(driver,ctt.getIncludeActivitiesSelectList(environment, mode, EditViewMode.Edit, 10),
							includeActivities, "Text");
					if (includeActivities.equalsIgnoreCase(defaultvalue)) {
						log(LogStatus.INFO, "Include Activities Related to Default Value Matched: " + defaultvalue,YesNo.No);
					} else {
						sa.assertTrue(false, "Include Activities Related to default value not matched Actual : "+ defaultvalue + " \t Expected : " + includeActivities);
						log(LogStatus.INFO, "Include Activities Related to default value not matched Actual : "+ defaultvalue + " \t Expected : " + includeActivities, YesNo.Yes);
					}

					ThreadSleep(5000);
					String selectIncludeActivitiesValue = includeActivities;
					if (selectVisibleTextFromDropDown(driver,ctt.getIncludeActivitiesSelectList(environment, mode, EditViewMode.Edit, 10),selectIncludeActivitiesValue, selectIncludeActivitiesValue)) {
						log(LogStatus.INFO, "Selected Include Activities related to : " + selectIncludeActivitiesValue,YesNo.No);
					} else {
						sa.assertTrue(false,"Not Able to Select Include Activities related to : " + selectIncludeActivitiesValue);
						log(LogStatus.SKIP,"Not Able to Select Include Activities related to : " + selectIncludeActivitiesValue,YesNo.Yes);

					}
					ThreadSleep(5000);
					String selectKeepActivitiesValue = keepActivitiesDefaultValue;
					if (selectVisibleTextFromDropDown(driver,ctt.getKeepActivitiesAtSelectList(environment, mode, EditViewMode.Edit, 10),selectKeepActivitiesValue, selectKeepActivitiesValue)) {
						log(LogStatus.INFO, "Selected Keep Activities related to : " + selectKeepActivitiesValue,YesNo.No);
					} else {
						sa.assertTrue(false,"Not Able to Select Keep Activities related to : " + selectKeepActivitiesValue);
						log(LogStatus.SKIP,"Not Able to Select Keep Activities related to : " + selectKeepActivitiesValue,YesNo.Yes);

					}


					if (!isSelected(driver, ctt.getEnableCheckBoxforNavatarSetUpSideMenuTab(environment, mode,NavatarSetupSideMenuTab.ContactTransfer, EditViewMode.Edit, ClickOrCheckEnableDisableCheckBox.Click, 10), "Enabled CheckBox")) {
						log(LogStatus.INFO, "Enable Contact Transfer is Unchecked", YesNo.No);
						if (click(driver,ctt.getEnableCheckBoxforClickNavatarSetUpSideMenuTab(projectName,NavatarSetupSideMenuTab.ContactTransfer, EditViewMode.Edit, 10),"Enabled Contact Transfer", action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Enable Contact Transfer Box Checkbox", YesNo.No);
							ThreadSleep(2000);

						} else {
							sa.assertTrue(false, "Not Able to Click on Enable Contact Transfer Checkbox");
							log(LogStatus.SKIP, "Not Able to Click on Enable Contact Transfer Checkbox", YesNo.Yes);
						}

					} else {
						log(LogStatus.SKIP, "Enable Contact Transfer is Already checked", YesNo.Yes);
					}


					if (click(driver, ctt.getSaveButtonforNavatarSetUpSideMenuTab(projectName,NavatarSetupSideMenuTab.ContactTransfer, 10, TopOrBottom.TOP), "Save Button", action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Save Button for No Button", YesNo.No);
						ThreadSleep(10000);

						keepActivitiesDefaultValue = "Old Institution Only";
						defaultvalue = getSelectedOptionOfDropDown(driver,ctt.getKeepActivitiesAtSelectList(environment, mode, EditViewMode.View, 10),keepActivitiesDefaultValue, "Text");
						if (keepActivitiesDefaultValue.equalsIgnoreCase(defaultvalue)) {
							log(LogStatus.INFO, "Keep Activities Default Value Matched: " + defaultvalue, YesNo.No);
						} else {
							sa.assertTrue(false, "Keep Activities Default default value not matched Actual : "+ defaultvalue + " \t Expected : " + keepActivitiesDefaultValue);
							log(LogStatus.INFO, "Keep Activities Default default value not matched Actual : " + defaultvalue+ " \t Expected : " + keepActivitiesDefaultValue, YesNo.Yes);
						}
						ThreadSleep(5000);
						includeActivities = "Contact Only";
						defaultvalue = getSelectedOptionOfDropDown(driver,ctt.getIncludeActivitiesSelectList(environment, mode, EditViewMode.View, 10),
								includeActivities, "Text");
						if (includeActivities.equalsIgnoreCase(defaultvalue)) {
							log(LogStatus.INFO, "Include Activities Related to Default Value Matched: " + defaultvalue,YesNo.No);
						} else {
							sa.assertTrue(false, "Include Activities Related to default value not matched Actual : "+ defaultvalue + " \t Expected : " + includeActivities);
							log(LogStatus.INFO, "Include Activities Related to default value not matched Actual : "+ defaultvalue + " \t Expected : " + includeActivities, YesNo.Yes);
						}


					} else {
						sa.assertTrue(false, "Not Able to Click on Save Button for No Button");
						log(LogStatus.SKIP, "Not Able to Click on Save Button No Button", YesNo.Yes);
					}


				}else{
					sa.assertTrue(false, "Not Able to Click on Edit Button");
					log(LogStatus.SKIP, "Not Able to Click on Edit Button", YesNo.Yes);	
				}

			} else {
				appLog.error("Not Able to Click on Contact Transfer Tab");
				sa.assertTrue(false, "Not Able to Click on Contact Transfer Tab");
				log(LogStatus.SKIP, "Not Able to Click on Contact Transfer Tab", YesNo.Yes);
			}

		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc013_2_CreateAccountAndContactRelatedcToCT(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String value="";
		String type="";
		String[][] EntityOrAccounts = {{ SmokeCTIns, SmokeCTInsRecordType ,null},
				{ SmokeCTIns1, SmokeCTIns1RecordType ,null}
				};
		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, tabObj1)) {
				log(LogStatus.INFO,"Click on Tab : "+tabObj1,YesNo.No);	
				value = accounts[0];
				type = accounts[1];
				if (ip.createEntityOrAccount(projectName, mode, value, type, null, null, 20)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj1);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj1,YesNo.Yes);
			}
		}

		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			SmokeCTContactEmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, SmokeCTContactEmailID, "Contacts", excelLabel.Variable_Name, "SMOKECTCON",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, SmokeCTContactFName, SmokeCTContactLName, SmokeCTContactInst, SmokeCTContactEmailID,SmokeCTContactRecordType, null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+SmokeCTContactFName+" "+SmokeCTContactLName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+SmokeCTContactFName+" "+SmokeCTContactLName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+SmokeCTContactFName+" "+SmokeCTContactLName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}

		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);
			
			if(lp.clickOnAlreadyCreatedItem(projectName, TabName.ContactTab, SmokeCTContactFName+" "+SmokeCTContactLName, 30)){
				
				log(LogStatus.INFO,"click on Created Contact : "+SmokeCTContactFName+" "+SmokeCTContactLName,YesNo.No);	
				ThreadSleep(3000);
				if(lp.verifyPresenceOfActionButtonOfShowMoreActionDownArrow(projectName, PageName.ContactPage, ShowMoreActionDropDownList.Contact_Transfer, 30)){
					log(LogStatus.INFO,"Contact transfer button is present on contact detail page",YesNo.No);	

				}else{
					sa.assertTrue(false,"Contact transfer button is not present on contact detail page");
					log(LogStatus.SKIP,"Contact transfer button is not present on contact detail page",YesNo.Yes);
				}
			}else{
				
				sa.assertTrue(false,"Not Able to click on Create Contact : "+SmokeCTContactFName+" "+SmokeCTContactLName);
				log(LogStatus.SKIP,"Not Able to click on created Contact: "+SmokeCTContactFName+" "+SmokeCTContactLName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName"})
	@Test
	public void SmokeTc014_1_CreateSomeActivityAndVerifyTheActivitytimelineOnTestContact1RelatedPage(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele =null;
		String contactName=SmokeCTContactFName+" "+SmokeCTContactLName;

		if(lp.clickOnTab(contactName, mode, TabName.ContactTab)){
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);
			if(lp.clickOnAlreadyCreatedItem(projectName, TabName.ContactTab, contactName, 30)){
				log(LogStatus.INFO,"click on Created Contact : "+SmokeCTContactFName+" "+SmokeCTContactLName,YesNo.No);	
				ThreadSleep(3000);
				if(click(driver, lp.getRelatedTab(mode, RelatedTab.Communications.toString(), 10), RelatedTab.Details.toString(), action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"click on communication related tab",YesNo.No);	
					ThreadSleep(5000);
					
				}else {
					sa.assertTrue(false,"not able to click on communication related tab");
					log(LogStatus.SKIP,"not able to click on communication related tab",YesNo.Yes);
				}
				ele=lp.getActivityTimelineGridOnRelatedTab(30);
				if(ele!=null){
					log(LogStatus.INFO,"Activity timeline grid is present on contact detail page",YesNo.No);	

				}else{
					sa.assertTrue(false,"Activity timeline grid is not present on contact detail page");
					log(LogStatus.SKIP,"Activity timeline grid is not present on contact detail page",YesNo.Yes);
				}
			}else{
				sa.assertTrue(false,"Not Able to click on Create Contact : "+contactName);
				log(LogStatus.SKIP,"Not Able to click on created Contact: "+contactName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
		
		SmokeCTTask1dueDate=todaysDate;
		String task = SmokeCTTask1Subject;
		
		String[][] basictask = {{ActivityRelatedLabel.Subject.toString(),task},{excelLabel.Related_To.toString(),contactName},{excelLabel.Related_To.toString(),SmokeCTContactInst}};
		String[][] advancetask ={{"Due Date",SmokeCTTask1dueDate},{PageLabel.Priority.toString(),SmokeCTTask1Priority}};

		if (lp.createActivityTimeline("", true, NewInteractions_DefaultValues.Task.toString(), basictask, advancetask, null, null, false, null, null, null, null, null, null)) {
			log(LogStatus.INFO,"Able to create task : "+task,YesNo.No);
			ExcelUtils.writeData(phase1DataSheetFilePath,SmokeCTTask1dueDate, "Task1", excelLabel.Variable_Name, "SmokeCTTask1", excelLabel.Due_Date);

		} else {
			sa.assertTrue(false,"Not Able to create task : "+task);
			log(LogStatus.SKIP,"Not Able Able to create task : "+task,YesNo.Yes);	
		}
		
		refresh(driver);
		ThreadSleep(5000);
		task = SmokeCTLogACall1Subject;
		
		String[][] basiccall = {{ActivityRelatedLabel.Subject.toString(),task},{excelLabel.Notes.toString(),SmokeCTLogACall1Comment},{excelLabel.Related_To.toString(),contactName},{excelLabel.Related_To.toString(),SmokeCTContactInst}};
		String[][] advancecall ={{"Date",todaysDate}/*,{PageLabel.Priority.toString(),SmokeCTTask1Priority}*/};

		if (lp.createActivityTimeline("", true, NewInteractions_DefaultValues.Call.toString(), basiccall, advancecall, null, null, false, null, null, null, null, null, null)) {
			log(LogStatus.INFO,"Able to create task : "+task,YesNo.No);

		} else {
			sa.assertTrue(false,"Not Able to create task : "+task);
			log(LogStatus.SKIP,"Not Able Able to create task : "+task,YesNo.Yes);	
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void SmokeTc014_2_VerifyTheActivitytimelineOnTestContact1(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
		String task="";
		String secondaryContact=SmokeCTContactFName+" "+SmokeCTContactLName;
			
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+secondaryContact,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, secondaryContact, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+secondaryContact+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					if(click(driver, lp.getRelatedTab(mode, RelatedTab.Communications.toString(), 10), RelatedTab.Details.toString(), action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"click on communication related tab",YesNo.No);	
						ThreadSleep(5000);
						
					}else {
						sa.assertTrue(false,"not able to click on communication related tab");
						log(LogStatus.SKIP,"not able to click on communication related tab",YesNo.Yes);
					}
					task = SmokeCTTask1Subject;
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, task, SubjectElement.SubjectLink, 20);
					if (ele!=null) {
						log(LogStatus.INFO,task+" is present in Next Activity Section",YesNo.No);	
					} else {
						sa.assertTrue(false,task+" should be present in Next Activity Section");
						log(LogStatus.SKIP,task+" should be present in Next Activity Section",YesNo.Yes);
					}
					
					task = SmokeCTLogACall1Subject;
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, task, SubjectElement.SubjectLink, 20);
					if (ele!=null) {
						log(LogStatus.INFO,task+" is present in Past Activity Section",YesNo.No);	
					} else {
						sa.assertTrue(false,task+" should be present in Past Activity Section");
						log(LogStatus.SKIP,task+" should be present in Past Activity Section",YesNo.Yes);
					}
					
				} else {
					sa.assertTrue(false,"Item Not Found : "+secondaryContact+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+secondaryContact+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+secondaryContact);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+secondaryContact,YesNo.Yes);
			}	
			
			
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({"projectName"})
	@Test
	public void SmokeTc015_1_VerifyTheContactTransferButton_Action(String projectName) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		appLog.info("Login with User");
		appLog.info("Going on Contact Tab");
		TabName tabName = TabName.Object2Tab;
		String navatarCTCon1= SmokeCTContactFName+" "+SmokeCTContactLName;
		if (bp.clickOnTab(projectName, tabName)) {
			if (cp.clickOnAlreadyCreatedItem(projectName, tabName, navatarCTCon1, 20)) {
				log(LogStatus.INFO, "Click on Created Contact : " + navatarCTCon1, YesNo.No);
				ThreadSleep(2000);
				if (cp.clickOnShowMoreActionDownArrow(projectName, PageName.Object2Page, ShowMoreActionDropDownList.Contact_Transfer, 10)) {
					log(LogStatus.INFO, "Clicked on Contact Transfer", YesNo.No);	

					if (cp.enteringValueforLegalNameOnContactTransferPage(projectName, SmokeCTIns1, 10)) {
						log(LogStatus.PASS, "Able to Transfer Contact", YesNo.No);
						ThreadSleep(2000);
						refresh(driver);
						ThreadSleep(5000);
						if(click(driver, lp.getRelatedTab(mode, RelatedTab.Details.toString(), 10), RelatedTab.Details.toString(), action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"click on Details related tab",YesNo.No);	
							ThreadSleep(5000);
							
						}else {
							sa.assertTrue(false,"not able to click on Details related tab");
							log(LogStatus.SKIP,"not able to click on Details related tab",YesNo.Yes);
						}
						if (cp.fieldValueVerification(projectName, PageName.Object2Page, PageLabel.Account_Name, SmokeCTIns1, 5)) {
							log(LogStatus.PASS, "Label Verified after contact Transfer", YesNo.Yes);	
							ThreadSleep(2000);
						} else {
							sa.assertTrue(false, "Label Not Verified after contact Transfer");
							log(LogStatus.FAIL, "Label Not Verified after contact Transfer", YesNo.Yes);
						}


					} else {
						sa.assertTrue(false, "Not Able to Transfer Contact");
						log(LogStatus.FAIL, "Not Able to Transfer Contact", YesNo.Yes);
					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Contact Transfer");
					log(LogStatus.SKIP, "Not Able to Click on Contact Transfer", YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Created Contact : " + navatarCTCon1);
				log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + navatarCTCon1,YesNo.Yes);

			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Contact Tab");
			log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
		appLog.info("Pass");
	}

	@Parameters({"projectName"})
	@Test
	public void SmokeTc015_2_VerifyTheContactTransferButton_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele ;
		TabName tabName =TabName.Object1Tab;
		String ctAccount ;
		String ctAccount1 = SmokeCTIns;
		String ctAccount2 = SmokeCTIns1;
		for (int j = 0; j < 2; j++) {

			if (j==0) {
				ctAccount = ctAccount1;
			} else {
				ctAccount = ctAccount2;
			}

			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+ctAccount,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, tabName, ctAccount, 20)) {
					log(LogStatus.INFO,"Clicked on  : "+ctAccount+" For : "+tabName,YesNo.No);
					ThreadSleep(1000);
					if (j==0 || j==1) {
						if(click(driver, lp.getRelatedTab(mode, RelatedTab.Communications.toString(), 10), RelatedTab.Details.toString(), action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"click on communication related tab",YesNo.No);	
							ThreadSleep(5000);
							
						}else {
							sa.assertTrue(false,"not able to click on communication related tab");
							log(LogStatus.SKIP,"not able to click on communication related tab",YesNo.Yes);
						}
						String task = SmokeCTTask1Subject;
						ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, task, SubjectElement.SubjectLink, 10);
						if (ele!=null) {
							log(LogStatus.INFO,task+" is present in Next Activity Section in old institution "+ctAccount,YesNo.No);	
						} else {
							sa.assertTrue(false,task+" should be present in Next Activity Section in old institution "+ctAccount);
							log(LogStatus.SKIP,task+" should be present in Next Activity Section in old institution "+ctAccount,YesNo.Yes);
						}
						
						
						task = SmokeCTLogACall1Subject;
						ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, task, SubjectElement.SubjectLink, 10);
						if (ele!=null) {
							log(LogStatus.INFO,task+" is present in Past Activity Section in old institution "+ctAccount,YesNo.No);	
						} else {
							sa.assertTrue(false,task+" should be present in Past Activity Section in old institution "+ctAccount);
							log(LogStatus.SKIP,task+" should be present in Past Activity Section in old institution "+ctAccount,YesNo.Yes);
						}
					} 

				} else {
					sa.assertTrue(false,"Item Not Found : "+ctAccount+" For : "+tabName);
					log(LogStatus.SKIP,"Item Not Found : "+ctAccount+" For : "+tabName,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+ctAccount);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+ctAccount,YesNo.Yes);
			}
		}
		
		//tab will be avaialbel only on contact page
//		if (cp.clickOnRelatedList(environment, mode, RecordType.Contact, RelatedList.Affiliations, RelatedTab.Affiliations.toString())){
//			log(LogStatus.INFO, "Click on Affiliations", YesNo.Yes);    
//		} else {
//			sa.assertTrue(false, "Not Able to Click on Affiliations");
//			log(LogStatus.SKIP, "Not Able to Click on Affiliations", YesNo.Yes);
//		}        

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc016_1_chnageContactTransferSettingAndCreateData(String projectName) {

		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		ContactTransferTabBusinessLayer ctt = new ContactTransferTabBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String defaultvalue="";
		String includeActivities="";
		
		if (bp.clickOnTab(projectName, TabName.NavatarSetup)) {
			appLog.info("Clicked on Navatar Set Up Tab");
			if (ctt.clickOnNavatarSetupSideMenusTab(projectName, NavatarSetupSideMenuTab.ContactTransfer)) {
				appLog.error("Clicked on Contact Transfer Tab");
				if (click(driver, ctt.getEditButtonforNavatarSetUpSideMenuTab(projectName,NavatarSetupSideMenuTab.ContactTransfer, 10), "Edit Button", action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Edit Button", YesNo.No);

					String keepActivitiesDefaultValue = "Do not move Activities";
					ThreadSleep(5000);
					String selectKeepActivitiesValue = keepActivitiesDefaultValue;
					if (selectVisibleTextFromDropDown(driver,ctt.getKeepActivitiesAtSelectList(environment, mode, EditViewMode.Edit, 10),selectKeepActivitiesValue, selectKeepActivitiesValue)) {
						log(LogStatus.INFO, "Selected Keep Activities related to : " + selectKeepActivitiesValue,YesNo.No);
					} else {
						sa.assertTrue(false,"Not Able to Select Keep Activities related to : " + selectKeepActivitiesValue);
						log(LogStatus.SKIP,"Not Able to Select Keep Activities related to : " + selectKeepActivitiesValue,YesNo.Yes);

					}



					if (!isSelected(driver, ctt.getEnableCheckBoxforNavatarSetUpSideMenuTab(environment, mode,NavatarSetupSideMenuTab.ContactTransfer, EditViewMode.Edit, ClickOrCheckEnableDisableCheckBox.Click, 10), "Enabled CheckBox")) {
						log(LogStatus.INFO, "Enable Contact Transfer is Unchecked", YesNo.No);
						if (click(driver,ctt.getEnableCheckBoxforClickNavatarSetUpSideMenuTab(projectName,NavatarSetupSideMenuTab.ContactTransfer, EditViewMode.Edit, 10),"Enabled Contact Transfer", action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Enable Contact Transfer Box Checkbox", YesNo.No);
							ThreadSleep(2000);

						} else {
							sa.assertTrue(false, "Not Able to Click on Enable Contact Transfer Checkbox");
							log(LogStatus.SKIP, "Not Able to Click on Enable Contact Transfer Checkbox", YesNo.Yes);
						}

					} else {
						log(LogStatus.SKIP, "Enable Contact Transfer is Already checked", YesNo.Yes);
					}


					if (click(driver, ctt.getSaveButtonforNavatarSetUpSideMenuTab(projectName,NavatarSetupSideMenuTab.ContactTransfer, 10, TopOrBottom.TOP), "Save Button", action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Save Button for No Button", YesNo.No);
						ThreadSleep(10000);

						keepActivitiesDefaultValue = "Do not move Activities";
						defaultvalue = getSelectedOptionOfDropDown(driver,ctt.getKeepActivitiesAtSelectList(environment, mode, EditViewMode.View, 10),keepActivitiesDefaultValue, "Text");
						if (keepActivitiesDefaultValue.equalsIgnoreCase(defaultvalue)) {
							log(LogStatus.INFO, "Keep Activities Default Value Matched: " + defaultvalue, YesNo.No);
						} else {
							sa.assertTrue(false, "Keep Activities Default default value not matched Actual : "+ defaultvalue + " \t Expected : " + keepActivitiesDefaultValue);
							log(LogStatus.INFO, "Keep Activities Default default value not matched Actual : " + defaultvalue+ " \t Expected : " + keepActivitiesDefaultValue, YesNo.Yes);
						}

						includeActivities = "Contact Only";
						defaultvalue = getSelectedOptionOfDropDown(driver,ctt.getIncludeActivitiesSelectList(environment, mode, EditViewMode.View, 10),
								includeActivities, "Text");
						if (includeActivities.equalsIgnoreCase(defaultvalue)) {
							log(LogStatus.INFO, "Include Activities Related to Default Value Matched: " + defaultvalue,YesNo.No);
						} else {
							sa.assertTrue(false, "Include Activities Related to default value not matched Actual : "+ defaultvalue + " \t Expected : " + includeActivities);
							log(LogStatus.INFO, "Include Activities Related to default value not matched Actual : "+ defaultvalue + " \t Expected : " + includeActivities, YesNo.Yes);
						}


					} else {
						sa.assertTrue(false, "Not Able to Click on Save Button for No Button");
						log(LogStatus.SKIP, "Not Able to Click on Save Button No Button", YesNo.Yes);
					}


				}else{
					sa.assertTrue(false, "Not Able to Click on Edit Button");
					log(LogStatus.SKIP, "Not Able to Click on Edit Button", YesNo.Yes);	
				}

			} else {
				appLog.error("Not Able to Click on Contact Transfer Tab");
				sa.assertTrue(false, "Not Able to Click on Contact Transfer Tab");
				log(LogStatus.SKIP, "Not Able to Click on Contact Transfer Tab", YesNo.Yes);
			}

		} else {
			appLog.error("Not Able to Click on Navatar Set Up Tab");
			sa.assertTrue(false, "Not Able to Click on Navatar Set Up Tab");
			log(LogStatus.SKIP, "Not Able to Click on Navatar Set Up Tab", YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc016_2_chnageContactTransferSettingAndCreateData(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String value="";
		String type="";
		String[][] EntityOrAccounts = {{ SmokeCTIns3, SmokeCTIns3RecordType ,null},
				{ SmokeCTIns4, SmokeCTIns4RecordType ,null}};
		
		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, tabObj1)) {
				log(LogStatus.INFO,"Click on Tab : "+tabObj1,YesNo.No);	
				value = accounts[0];
				type = accounts[1];
				if (ip.createEntityOrAccount(projectName, mode, value, type, null, null, 20)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj1);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj1,YesNo.Yes);
			}
		}

		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			SmokeCTContact2EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, SmokeCTContact2EmailID, "Contacts", excelLabel.Variable_Name, "SMOKECTCON2",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, SmokeCTContact2FName, SmokeCTContact2LName, SmokeCTContact2Inst, SmokeCTContact2EmailID,"", null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+SmokeCTContact2FName+" "+SmokeCTContact2LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+SmokeCTContact2FName+" "+SmokeCTContact2LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+SmokeCTContact2FName+" "+SmokeCTContact2LName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}

		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName"})
	@Test
	public void SmokeTc017_1_CreateSomeActivityAndVerifyTheActivitytimelineOnTestContact2RelatedPage(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName=SmokeCTContact2FName+" "+SmokeCTContact2LName;
		SmokeCTTask2dueDate=todaysDate;
		ExcelUtils.writeData(phase1DataSheetFilePath,SmokeCTTask2dueDate, "Task1", excelLabel.Variable_Name, "SmokeCTTask2", excelLabel.Due_Date);
		String task = SmokeCTTask2Subject;
		
	
		if(lp.clickOnTab(contactName, mode, TabName.ContactTab)){
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);
			if(lp.clickOnAlreadyCreatedItem(projectName, TabName.ContactTab, contactName, 30)){
				log(LogStatus.INFO,"click on Created Contact : "+contactName,YesNo.No);	
				ThreadSleep(3000);
				
			}else{
				sa.assertTrue(false,"Not Able to click on Create Contact : "+contactName);
				log(LogStatus.SKIP,"Not Able to click on created Contact: "+contactName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
		
		
		String[][] basictask = {{ActivityRelatedLabel.Subject.toString(),task},{excelLabel.Related_To.toString(),contactName},{excelLabel.Related_To.toString(),SmokeCTContactInst}};
		String[][] advancetask ={{"Due Date",SmokeCTTask2dueDate},{PageLabel.Priority.toString(),SmokeCTTask2Priority}};

		if (lp.createActivityTimeline("", true, NewInteractions_DefaultValues.Task.toString(), basictask, advancetask, null, null, false, null, null, null, null, null, null)) {
			log(LogStatus.INFO,"Able to create task : "+task,YesNo.No);
			ExcelUtils.writeData(phase1DataSheetFilePath,SmokeCTTask1dueDate, "Task1", excelLabel.Variable_Name, "SmokeCTTask1", excelLabel.Due_Date);

		} else {
			sa.assertTrue(false,"Not Able to create task : "+task);
			log(LogStatus.SKIP,"Not Able Able to create task : "+task,YesNo.Yes);	
		}
		refresh(driver);
		ThreadSleep(5000);
		task = SmokeCTLogACall2Subject;
		
		String[][] basiccall = {{ActivityRelatedLabel.Subject.toString(),task},{excelLabel.Notes.toString(),SmokeCTLogACall2Comment},{excelLabel.Related_To.toString(),contactName},{excelLabel.Related_To.toString(),SmokeCTContact2Inst}};
		String[][] advancecall ={{"Date",todaysDate}};

		if (lp.createActivityTimeline("", true, NewInteractions_DefaultValues.Call.toString(), basiccall, advancecall, null, null, false, null, null, null, null, null, null)) {
			log(LogStatus.INFO,"Able to create task : "+task,YesNo.No);

		} else {
			sa.assertTrue(false,"Not Able to create task : "+task);
			log(LogStatus.SKIP,"Not Able Able to create task : "+task,YesNo.Yes);	
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void SmokeTc017_2_VerifyTheActivitytimelineOnTestContact2(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null ;
		String task="";
		String secondaryContact=SmokeCTContact2FName+" "+SmokeCTContact2LName;
			
			if (cp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+secondaryContact,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, secondaryContact, 30)) {
					log(LogStatus.INFO,"Clicked on  : "+secondaryContact+" For : "+tabObj2,YesNo.No);
					ThreadSleep(2000);
					if(click(driver, lp.getRelatedTab(mode, RelatedTab.Communications.toString(), 10), RelatedTab.Details.toString(), action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"click on communication related tab",YesNo.No);	
						ThreadSleep(5000);
						
					}else {
						sa.assertTrue(false,"not able to click on communication related tab");
						log(LogStatus.SKIP,"not able to click on communication related tab",YesNo.Yes);
					}
					task = SmokeCTTask2Subject;
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, task, SubjectElement.SubjectLink, 10);
					if (ele!=null) {
						log(LogStatus.INFO,task+" is present in Next Activity Section",YesNo.No);	
					} else {
						sa.assertTrue(false,task+" should be present in Next Activity Section");
						log(LogStatus.SKIP,task+" should be present in Next Activity Section",YesNo.Yes);
					}
					
					
					task = SmokeCTLogACall2Subject;
					ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, task, SubjectElement.SubjectLink, 10);
					if (ele!=null) {
						log(LogStatus.INFO,task+" is present in Past Activity Section",YesNo.No);	
					} else {
						sa.assertTrue(false,task+" should be present in Past Activity Section");
						log(LogStatus.SKIP,task+" should be present in Past Activity Section",YesNo.Yes);
					}
					
				} else {
					sa.assertTrue(false,"Item Not Found : "+secondaryContact+" For : "+tabObj2);
					log(LogStatus.SKIP,"Item Not Found : "+secondaryContact+" For : "+tabObj2,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+secondaryContact);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+secondaryContact,YesNo.Yes);
			}	
			
			
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc018_1_VerifyTheContactTransferButton_Action(String projectName) {
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		appLog.info("Login with User");
		appLog.info("Going on Contact Tab");
		TabName tabName = TabName.Object2Tab;
		String navatarCTCon1= SmokeCTContact2FName+" "+SmokeCTContact2LName;
		if (bp.clickOnTab(projectName, tabName)) {
			if (cp.clickOnAlreadyCreatedItem(projectName, tabName, navatarCTCon1, 20)) {
				log(LogStatus.INFO, "Click on Created Contact : " + navatarCTCon1, YesNo.No);
				ThreadSleep(2000);
				if (cp.clickOnShowMoreActionDownArrow(projectName, PageName.Object2Page, ShowMoreActionDropDownList.Contact_Transfer, 10)) {
					log(LogStatus.INFO, "Clicked on Contact Transfer", YesNo.No);	

					if (cp.enteringValueforLegalNameOnContactTransferPage(projectName, SmokeCTIns4, 10)) {
						log(LogStatus.PASS, "Able to Transfer Contact", YesNo.No);
						ThreadSleep(2000);
						refresh(driver);

						if (cp.fieldValueVerification(projectName, PageName.Object2Page, PageLabel.Account_Name, SmokeCTIns4, 5)) {
							log(LogStatus.PASS, "Label Verified after contact Transfer", YesNo.Yes);	
							ThreadSleep(2000);
						} else {
							sa.assertTrue(false, "Label Not Verified after contact Transfer");
							log(LogStatus.FAIL, "Label Not Verified after contact Transfer", YesNo.Yes);
						}


					} else {
						sa.assertTrue(false, "Not Able to Transfer Contact");
						log(LogStatus.FAIL, "Not Able to Transfer Contact", YesNo.Yes);
					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Contact Transfer");
					log(LogStatus.SKIP, "Not Able to Click on Contact Transfer", YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Created Contact : " + navatarCTCon1);
				log(LogStatus.SKIP, "Not Able to Click on Created Contact : " + navatarCTCon1,YesNo.Yes);

			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Contact Tab");
			log(LogStatus.SKIP, "Not Able to Click on Contact Tab", YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
		appLog.info("Pass");
	}

	@Parameters({"projectName"})
	@Test
	public void SmokeTc018_2_VerifyTheContactTransferButton_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele ;
		TabName tabName =TabName.Object1Tab;
		String ctAccount ;
		String ctAccount1 = SmokeCTIns3;
		String ctAccount2 = SmokeCTIns4;
		for (int j = 0; j < 2; j++) {

			if (j==0) {
				ctAccount = ctAccount1;
			} else {
				ctAccount = ctAccount2;
			}

			if (cp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+ctAccount,YesNo.No);
				ThreadSleep(1000);
				if (cp.clickOnAlreadyCreatedItem(projectName, tabName, ctAccount, 20)) {
					log(LogStatus.INFO,"Clicked on  : "+ctAccount+" For : "+tabName,YesNo.No);
					ThreadSleep(1000);
					if (j==0 || j==1) {
						if(click(driver, lp.getRelatedTab(mode, RelatedTab.Communications.toString(), 10), RelatedTab.Details.toString(), action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO,"click on communication related tab",YesNo.No);	
							ThreadSleep(5000);
							
						}else {
							sa.assertTrue(false,"not able to click on communication related tab");
							log(LogStatus.SKIP,"not able to click on communication related tab",YesNo.Yes);
						}
						String task = SmokeCTTask2Subject;
						ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Next, task, SubjectElement.SubjectLink, 20);
						if (ele!=null) {
							log(LogStatus.INFO,task+" is present in Next Activity Section in old institution "+ctAccount,YesNo.No);	
						} else {
							sa.assertTrue(false,task+" should be present in Next Activity Section in old institution "+ctAccount);
							log(LogStatus.SKIP,task+" should be present in Next Activity Section in old institution "+ctAccount,YesNo.Yes);
						}
						
						
						task = SmokeCTLogACall2Subject;
						ele = cp.getElementForActivityTimeLineTask(projectName, PageName.Object2Page,ActivityType.Past, task, SubjectElement.SubjectLink, 20);
						if (ele!=null) {
							log(LogStatus.INFO,task+" is present in Past Activity Section in old institution "+ctAccount,YesNo.No);	
						} else {
							sa.assertTrue(false,task+" should be present in Past Activity Section in old institution "+ctAccount);
							log(LogStatus.SKIP,task+" should be present in Past Activity Section in old institution "+ctAccount,YesNo.Yes);
						}
					} 

				} else {
					sa.assertTrue(false,"Item Not Found : "+ctAccount+" For : "+tabName);
					log(LogStatus.SKIP,"Item Not Found : "+ctAccount+" For : "+tabName,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+ctAccount);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+ctAccount,YesNo.Yes);
			}
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc019_CreateACompanyAndSourceFirmAndSourceContact(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String value="";
		String type="";
		String[][] EntityOrAccounts = {{ SmokeDealIns1, SmokeDealIns1RecordType ,null},
				{ SmokeDealIns2, SmokeDealIns2RecordType ,null}};
		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, tabObj1)) {
				log(LogStatus.INFO,"Click on Tab : "+tabObj1,YesNo.No);	
				value = accounts[0];
				type = accounts[1];
				if (ip.createEntityOrAccount(projectName,"", value, type,null, null, 20)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj1);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj1,YesNo.Yes);
			}
		}

		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			SmokeDealContact1EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, SmokeDealContact1EmailID, "Contacts", excelLabel.Variable_Name, "SMOKEDEALCON1",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, SmokeDealContact1FName, SmokeDealContact1LName, SmokeDealContact1Inst, SmokeDealContact1EmailID,"", null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+SmokeDealContact1FName+" "+SmokeDealContact1LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+SmokeDealContact1FName+" "+SmokeDealContact1LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+SmokeDealContact1FName+" "+SmokeDealContact1LName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}

		navigationMenuName=NavigationMenuItems.Create.toString();
		boolean flag = false;
		WebElement ele=null;
		String createNewNavigationLink=CreateNew_DefaultValues.New_Deal.toString();
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
				String[][] labelswithValues = {{excelLabel.Source_Contact.toString(),SmokeDealContact1FName+" "+SmokeDealContact1LName},{excelLabel.Source_Firm.toString(),SmokeDealIns2}};
				if (fp.createDealPopUp(projectName,SmokeDeal1RecordType,SmokeDeal1,SmokeDeal1CompanyName, SmokeDeal1Stage,labelswithValues, 15)) {
					log(LogStatus.INFO,"Created Deal : "+SmokeDeal1+" through "+createNewNavigationLink,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Deal  : "+" through "+createNewNavigationLink);
					log(LogStatus.SKIP,"Not Able to Create Deal  : "+" through "+createNewNavigationLink,YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on : "+createNewNavigationLink+" for creation ", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on : "+createNewNavigationLink+" for creation ");
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on : "+createNewNavigationLink+" for creation ", YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on : "+createNewNavigationLink+" for creation ");
		}


		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();

	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc020_1_VerifyTheDealQualityScoreHighestStageReachedAndLastStagechangeDateForAllRecordPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=dealReceivedScore;totalDealsshown=1;averageDealQualityScore=dealQualityScore/totalDealsshown;
		WebElement ele;
		String label="";
		String value="";
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			log(LogStatus.INFO,"Click on deal tab",YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, SmokeDeal1, 10)){
				log(LogStatus.INFO," Able to click "+SmokeDeal1,YesNo.No);
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"click on details tab",YesNo.No);

					String labelWithValues[][] = {{excelLabel.Highest_Stage_Reached.toString(),SmokeDeal1Stage},
							{excelLabel.Stage.toString(),SmokeDeal1Stage},
							{excelLabel.Deal_Quality_Score.toString(),String.valueOf(dealQualityScore)}};

					for (String[] lbWithValue : labelWithValues) {
						label=lbWithValue[0];
						value=lbWithValue[1];
						if (fp.FieldValueVerificationOnFundPage(projectName, label,value)) {
							log(LogStatus.INFO, label+" with value "+value+" verified", YesNo.No);

						}else {
							log(LogStatus.ERROR, label+" with value "+value+" not verified", YesNo.Yes);
							sa.assertTrue(false,label+" with value "+value+" not verified");
						}
					}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+SmokeDeal1);
				log(LogStatus.SKIP,"Not Able to click "+SmokeDeal1,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		
		
		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={SmokeDealIns2,SmokeDealContact1FName+" "+SmokeDealContact1LName};
		int j=0;
		for (TabName tab:tabName) {
			if (lp.clickOnTab(projectName, tab)) {
				log(LogStatus.INFO,"Click on tab for "+records[j],YesNo.No);
				if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
					log(LogStatus.INFO,"Click on "+records[j],YesNo.No);
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Click on deatails tab for "+records[j],YesNo.No);

						String labelWithValues[][] = {{excelLabel.Average_Deal_Quality_Score.toString(),String.valueOf(averageDealQualityScore)}};

						for (String[] lbWithValue : labelWithValues) {
							label=lbWithValue[0];
							value=lbWithValue[1];
							if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, label,value)) {
								log(LogStatus.INFO, label+" with value "+value+" verified", YesNo.No);

							}else {
								log(LogStatus.ERROR, label+" with value "+value+" not verified", YesNo.Yes);
								sa.assertTrue(false,label+" with value "+value+" not verified");
							}
						}


					}else {
						sa.assertTrue(false,"not able to click on details tab");
						log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"Not Able to click "+records[j]);
					log(LogStatus.SKIP,"Not Able to click "+records[j],YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to click on deal tab");
				log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
			}
			j++;
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc020_2_ChangeTheDealStageAndVerifyTheDealQualityScore(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=managementMeetingScore;totalDealsshown=1;averageDealQualityScore=dealQualityScore/totalDealsshown;
		WebElement ele;
		String label="";
		String value="";
		String stageValue=Stage.Management_Meeting.toString();
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			log(LogStatus.INFO,"Click on deal tab",YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, SmokeDeal1, 10)){
				log(LogStatus.INFO," Able to click "+SmokeDeal1,YesNo.No);

				ExcelUtils.writeData(phase1DataSheetFilePath, stageValue, "Deal", excelLabel.Variable_Name, "SMOKEDEAL1", excelLabel.Updated_Stage);
				if (fp.changeStage(projectName, stageValue, 10)) {	
					log(LogStatus.INFO,"not able to change stage to "+stageValue,YesNo.No);
				}else {
					sa.assertTrue(false,"not able to change stage to "+stageValue);
					log(LogStatus.SKIP,"not able to change stage to "+stageValue,YesNo.Yes);
				}


				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"click on details tab",YesNo.No);

					String labelWithValues[][] = {{excelLabel.Highest_Stage_Reached.toString(),Stage.Management_Meeting.toString()},
							{excelLabel.Stage.toString(),Stage.Management_Meeting.toString()},
							{excelLabel.Deal_Quality_Score.toString(),String.valueOf(dealQualityScore)}};

					for (String[] lbWithValue : labelWithValues) {
						label=lbWithValue[0];
						value=lbWithValue[1];
						if (fp.FieldValueVerificationOnFundPage(projectName, label,value)) {
							log(LogStatus.INFO, label+" with value "+value+" verified", YesNo.No);

						}else {
							log(LogStatus.ERROR, label+" with value "+value+" not verified", YesNo.Yes);
							sa.assertTrue(false,label+" with value "+value+" not verified");
						}
					}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+SmokeDeal1);
				log(LogStatus.SKIP,"Not Able to click "+SmokeDeal1,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}


		TabName tabName[]={TabName.Object1Tab,TabName.Object2Tab};
		String records[]={SmokeDealIns2,SmokeDealContact1FName+" "+SmokeDealContact1LName};
		int j=0;
		for (TabName tab:tabName) {
			if (lp.clickOnTab(projectName, tab)) {
				log(LogStatus.INFO,"Click on tab for "+records[j],YesNo.No);
				if (fp.clickOnAlreadyCreatedItem(projectName, records[j], 10)){
					log(LogStatus.INFO,"Click on "+records[j],YesNo.No);
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Click on deatails tab for "+records[j],YesNo.No);

						String labelWithValues[][] = {{excelLabel.Average_Deal_Quality_Score.toString(),String.valueOf(averageDealQualityScore)}};

						for (String[] lbWithValue : labelWithValues) {
							label=lbWithValue[0];
							value=lbWithValue[1];
							if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, label,value)) {
								log(LogStatus.INFO, label+" with value "+value+" verified", YesNo.No);

							}else {
								log(LogStatus.ERROR, label+" with value "+value+" not verified", YesNo.Yes);
								sa.assertTrue(false,label+" with value "+value+" not verified");
							}
						}


					}else {
						sa.assertTrue(false,"not able to click on details tab");
						log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"Not Able to click "+records[j]);
					log(LogStatus.SKIP,"Not Able to click "+records[j],YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to click on deal tab");
				log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
			}
			j++;
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc021_1_VerifyTheStagePathOnTheDealDetailPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele;
		String label="";
		String value="";
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			log(LogStatus.INFO,"Click on deal tab",YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, SmokeDeal1, 10)){
				log(LogStatus.INFO," Able to click "+SmokeDeal1,YesNo.No);
				String stage = DealStage.Current.toString();
				String stageValue = Stage.Management_Meeting.toString();
				ele =  dp.getStagePath(stage,stageValue);
				if (ele!=null) {
					log(LogStatus.INFO,"Stage Path have "+stage+" stage as "+stageValue,YesNo.No);
				}else {
					sa.assertTrue(false,"Stage Path should have "+stage+" stage as "+stageValue);
					log(LogStatus.SKIP,"Stage Path Should have "+stage+" stage as "+stageValue,YesNo.Yes);
				}
				ele = dp.getMarkStageCompleteButton(10);
				if (click(driver, ele, "Mark Stage as Complete button ", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"click on Mark Stage as Complete button ",YesNo.No);
					ThreadSleep(10000);
					stage = DealStage.Completed.toString();
					stageValue = Stage.Management_Meeting.toString();
					ele =  dp.getStagePath(stage,stageValue);
					if (ele!=null) {
						log(LogStatus.INFO,"Stage Path have "+stage+" stage as "+stageValue+" after click on Mark Stage as Complete button ",YesNo.No);
					}else {
						sa.assertTrue(false,"Stage Path should have "+stage+" stage as "+stageValue+" after click on Mark Stage as Complete button ");
						log(LogStatus.SKIP,"Stage Path Should have "+stage+" stage as "+stageValue+" after click on Mark Stage as Complete button ",YesNo.Yes);
					}

					stage = DealStage.Current.toString();
					stageValue = Stage.IOI.toString();
					ele =  dp.getStagePath(stage,stageValue);
					if (ele!=null) {
						log(LogStatus.INFO,"Stage Path have "+stage+" stage as "+stageValue+" after click on Mark Stage as Complete button ",YesNo.No);
					}else {
						sa.assertTrue(false,"Stage Path should have "+stage+" stage as "+stageValue+" after click on Mark Stage as Complete button ");
						log(LogStatus.SKIP,"Stage Path Should have "+stage+" stage as "+stageValue+" after click on Mark Stage as Complete button ",YesNo.Yes);
					}

					stageValue = Stage.IOI.toString();
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"click on details tab",YesNo.No);

						String labelWithValues[][] = {{excelLabel.Highest_Stage_Reached.toString(),stageValue},
								{excelLabel.Stage.toString(),stageValue},
								{excelLabel.Last_Stage_Change_Date.toString(),getDateAccToTimeZone("America/Los_Angles", "MM/dd/yyyy")}};

						for (String[] lbWithValue : labelWithValues) {
							label=lbWithValue[0];
							value=lbWithValue[1];
							if (fp.FieldValueVerificationOnFundPage(projectName, label,value)) {
								log(LogStatus.INFO, label+" with value "+value+" verified", YesNo.No);

							}else {
								log(LogStatus.ERROR, label+" with value "+value+" not verified", YesNo.Yes);
								sa.assertTrue(false,label+" with value "+value+" not verified");
							}
						}
					}else {
						sa.assertTrue(false,"not able to click on details tab");
						log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
					}

				}else {
					sa.assertTrue(false,"Not able to click on Mark Stage as Complete button ");
					log(LogStatus.SKIP,"Not able to click on Mark Stage as Complete button ",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+SmokeDeal1);
				log(LogStatus.SKIP,"Not Able to click "+SmokeDeal1,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc021_2_ChangeTheDealStageAndVerifyTheHighestStageAndLastStage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele;
		String label="";
		String value="";
		String stageValue =Stage.DeclinedDead.toString();
		String stage ="";
		
		ExcelUtils.writeData(phase1DataSheetFilePath, stageValue, "Deal", excelLabel.Variable_Name, "SMOKEDEAL1", excelLabel.Updated_Stage);
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			log(LogStatus.INFO,"Click on deal tab",YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, SmokeDeal1, 10)){
				log(LogStatus.INFO," Able to click "+SmokeDeal1,YesNo.No);

				if (fp.changeStage(projectName, stageValue, 10)) {	
					log(LogStatus.INFO,"not able to change stage to "+stageValue,YesNo.No);
				}else {
					sa.assertTrue(false,"not able to change stage to "+stageValue);
					log(LogStatus.SKIP,"not able to change stage to "+stageValue,YesNo.Yes);
				}


				ele = dp.getmarkAsCurrentStage(10);
				if (click(driver, ele, "Mark Stage as current button ", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"click on Mark Stage as current button ",YesNo.No);
					ThreadSleep(10000);
					stage = DealStage.Current.toString();
					stageValue = Stage.DeclinedDead.toString();
					ele =  dp.getStagePath(stage,stageValue);
					if (ele!=null) {
						log(LogStatus.INFO,"Stage Path have "+stage+" stage as "+stageValue+" after click on Mark Stage as curremt button ",YesNo.No);
					}else {
						sa.assertTrue(false,"Stage Path should have "+stage+" stage as "+stageValue+" after click on Mark Stage as Complete button ");
						log(LogStatus.SKIP,"Stage Path Should have "+stage+" stage as "+stageValue+" after click on Mark Stage as Complete button ",YesNo.Yes);
					}

					
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"click on details tab",YesNo.No);

						String labelWithValues[][] = {{excelLabel.Highest_Stage_Reached.toString(),stageValue},
								{excelLabel.Stage.toString(),stageValue},
								{excelLabel.Last_Stage_Change_Date.toString(),getDateAccToTimeZone("America/Los_Angles", "MM/dd/yyyy")}};

						for (String[] lbWithValue : labelWithValues) {
							label=lbWithValue[0];
							value=lbWithValue[1];
							if (fp.FieldValueVerificationOnFundPage(projectName, label,value)) {
								log(LogStatus.INFO, label+" with value "+value+" verified", YesNo.No);

							}else {
								log(LogStatus.ERROR, label+" with value "+value+" not verified", YesNo.Yes);
								sa.assertTrue(false,label+" with value "+value+" not verified");
							}
						}
					}else {
						sa.assertTrue(false,"not able to click on details tab");
						log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
					}

				}else {
					sa.assertTrue(false,"Not able to click on Mark Stage as Complete button ");
					log(LogStatus.SKIP,"Not able to click on Mark Stage as Complete button ",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+SmokeDeal1);
				log(LogStatus.SKIP,"Not Able to click "+SmokeDeal1,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
	}

	@Parameters({ "projectName"})
	@Test
	public void SmokeTc022_CreateACompanyAndSourceFirmAndSourceContact(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String value="";
		String type="";
		String[][] EntityOrAccounts = {{ SmokePFIns1, SmokePFIns1RecordType ,null},
				{ SmokePFIns2, SmokePFIns2RecordType ,null}};
		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, tabObj1)) {
				log(LogStatus.INFO,"Click on Tab : "+tabObj1,YesNo.No);	
				value = accounts[0];
				type = accounts[1];
				if (ip.createEntityOrAccount(projectName,"", value, type,null, null, 20)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj1);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj1,YesNo.Yes);
			}
		}

		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			SmokePFContact1EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, SmokePFContact1EmailID, "Contacts", excelLabel.Variable_Name, "SMOKEPFCON1",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, SmokePFContact1FName, SmokePFContact1LName, SmokePFContact1Inst, SmokePFContact1EmailID,"", null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+SmokePFContact1FName+" "+SmokePFContact1LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+SmokePFContact1FName+" "+SmokePFContact1LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+SmokePFContact1FName+" "+SmokePFContact1LName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
		navigationMenuName = NavigationMenuItems.Create.toString();
		boolean flag = false;
		WebElement ele=null;
		String createNewNavigationLink = CreateNew_DefaultValues.New_Deal.toString();
		flag=false;
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName,navigationMenuName, action.BOOLEAN, 30)) {
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
				String[][] labelswithValues = {{excelLabel.Source_Contact.toString(),SmokePFContact1FName+" "+SmokePFContact1LName},{excelLabel.Source_Firm.toString(),SmokePFIns2}};
				if (fp.createDealPopUp(projectName,SmokeDeal2RecordType,SmokeDeal2,SmokeDeal2CompanyName, SmokeDeal2Stage,labelswithValues, 25)) {
					log(LogStatus.INFO,"Created Deal : "+SmokeDeal2+" through "+createNewNavigationLink,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Deal  : "+" through "+createNewNavigationLink);
					log(LogStatus.SKIP,"Not Able to Create Deal  : "+" through "+createNewNavigationLink,YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on : "+createNewNavigationLink+" for creation ", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on : "+createNewNavigationLink+" for creation ");
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on : "+createNewNavigationLink+" for creation ", YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on : "+createNewNavigationLink+" for creation ");
		}


		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();

	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc023_1_VerifyConvertToPortfolioButtonForTheDealRecord(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			log(LogStatus.INFO,"Click on deal tab",YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, SmokeDeal2, 10)){
				log(LogStatus.INFO," Able to click "+SmokeDeal2,YesNo.No);	

				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					log(LogStatus.INFO,"click on convert to portfolio button",YesNo.No);

					if (dp.getconvertToPortfolioMessage(SmokeDeal2CompanyName,10)!=null) {
						log(LogStatus.INFO, "successfully verified convert to portfolio text message : "+SmokeDeal2, YesNo.No);
					}else {
						sa.assertTrue(false,"could not verify convert to portfolio text message before next : "+SmokeDeal2);
						log(LogStatus.SKIP,"could not verify convert to portfolio text message before next : "+SmokeDeal2,YesNo.Yes);
					}

					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						log(LogStatus.INFO, "successfully clicked next button", YesNo.No);
						if(dp.getconvertToPortfolioMessageAfterNext( 10)!=null) {
							String msg =dp.getconvertToPortfolioMessageAfterNext( 10).getText();
							System.out.println("message:"+msg);
							if(msg.contains(SmokeDeal2CompanyName) && msg.contains(dp.convertToPortfolioAfterNext(SmokeDeal2CompanyName))) {
								log(LogStatus.INFO, "successfully verified after next convert to portfolio text message", YesNo.No);
							}else {
								sa.assertTrue(false,"could not verify after next convert to portfolio text message");
								log(LogStatus.SKIP,"could not verify after next convert to portfolio text message",YesNo.Yes);
							}

						}else {
							sa.assertTrue(false,"could not verify after next convert to portfolio text message");
							log(LogStatus.SKIP,"could not verify after next convert to portfolio text message",YesNo.Yes);
						}


						if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "successfully verified finish button after convert to portfolio", YesNo.No);
							ThreadSleep(2000);
							String labelWithValues[][] = {{excelLabel.Stage.toString(),Stage.Closed.toString()}};

							for (String[] lbWithValue : labelWithValues) {
								String label = lbWithValue[0];
								String value = lbWithValue[1];
								if (fp.FieldValueVerificationOnFundPage(projectName, label,value)) {
									log(LogStatus.INFO, label+" with value "+value+" verified after converting to portfolio", YesNo.No);

								}else {
									log(LogStatus.ERROR, label+" with value "+value+" not verified after converting to portfolio", YesNo.Yes);
									sa.assertTrue(false,label+" with value "+value+" not verified after converting to portfolio");
								}
							}

						}else {
							sa.assertTrue(false,"could not verify convert to portfolio as finish button not clicked");
							log(LogStatus.SKIP,"could not verify convert to portfolio as finish button not clicked",YesNo.Yes);
						}


					}else {
						sa.assertTrue(false,"not able to click on next button");
						log(LogStatus.SKIP,"not able to click on next button",YesNo.Yes);
					}


				}else {
					sa.assertTrue(false,"not able to click on convert to portfolio button");
					log(LogStatus.SKIP,"not able to click on convert to portfolio button",YesNo.Yes);
				}

			}else {
				sa.assertTrue(false,"Not Able to click "+SmokeDeal2);
				log(LogStatus.SKIP,"Not Able to click "+SmokeDeal2,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		//////////////////

		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Click on institution tab",YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, SmokeDeal2CompanyName, 10)){
				log(LogStatus.INFO," Able to click "+SmokeDeal2CompanyName,YesNo.No);
				
				String labelWithValues[][] = {{excelLabel.Record_Type.toString(),"Portfolio Company"}};
				for (String[] lbWithValue : labelWithValues) {
					String label = lbWithValue[0];
					String value = lbWithValue[1];
					if (fp.FieldValueVerificationOnFundPage(projectName, label,value)) {
						log(LogStatus.INFO, label+" with value "+value+" verified after converting to portfolio", YesNo.No);

					}else {
						log(LogStatus.ERROR, label+" with value "+value+" not verified after converting to portfolio", YesNo.Yes);
						sa.assertTrue(false,label+" with value "+value+" not verified after converting to portfolio");
					}
				}
				
			}else {
				sa.assertTrue(false,"Not Able to click "+SmokeDeal2CompanyName);
				log(LogStatus.SKIP,"Not Able to click "+SmokeDeal2CompanyName,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on institution tab");
			log(LogStatus.SKIP,"not able to click on institution tab",YesNo.Yes);
		}
		
		
		////////////////
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc023_2_VerifyTheDealQualityScoreHighestStageReachedAfterConvetingToPortFolio(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		dealQualityScore=closedScore;totalDealsshown=1;averageDealQualityScore=dealQualityScore/totalDealsshown;
		WebElement ele;
		String label="";
		String value="";
		
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			log(LogStatus.INFO,"Click on deal tab",YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, SmokeDeal2, 10)){
				log(LogStatus.INFO," Able to click "+SmokeDeal2,YesNo.No);	

				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					log(LogStatus.INFO,"click on convert to portfolio button",YesNo.No);

					if (dp.getconvertToPortfolioMessage(SmokeDeal2CompanyName,10)!=null) {
						log(LogStatus.INFO, "successfully verified convert to portfolio text message : "+SmokeDeal2, YesNo.No);
					}else {
						sa.assertTrue(false,"could not verify convert to portfolio text message before next : "+SmokeDeal2);
						log(LogStatus.SKIP,"could not verify convert to portfolio text message before next : "+SmokeDeal2,YesNo.Yes);
					}

//					if (dp.getconvertToPortfolioMessage1(DealPageErrorMessage.convertingPortfoliaMsg,10)!=null) {
//						log(LogStatus.INFO, "successfully verified convert to portfolio text message : "+DealPageErrorMessage.convertingPortfoliaMsg, YesNo.No);
//					}else {
//						sa.assertTrue(false,"could not verify convert to portfolio text message before next : "+DealPageErrorMessage.convertingPortfoliaMsg);
//						log(LogStatus.SKIP,"could not verify convert to portfolio text message before next : "+DealPageErrorMessage.convertingPortfoliaMsg,YesNo.Yes);
//					}

					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						log(LogStatus.INFO, "successfully clicked next button", YesNo.No);
						
						ThreadSleep(4000);
						
						if (dp.getconvertToPortfolioMessageRepeat("Convert to Portfolio",10)!=null) {
							
							String text=dp.getconvertToPortfolioMessageRepeat("Convert to Portfolio",10).getText().trim();
							String expected = dp.convertToPortfolioRepeat(SmokePFIns1);
							if (text.contains(expected)) {
								log(LogStatus.INFO,"successfully verified already portfolio message : "+expected,YesNo.Yes);
								
							}else {
								sa.assertTrue(false,"could not verify already portfolio message\nExpected: "+text+"\nActual: "+expected);
								log(LogStatus.SKIP,"could not verify already portfolio message\\nExpected: "+text+"\nActual: "+expected,YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"not visible already portfolio message");
							log(LogStatus.SKIP,"not visible already portfolio message",YesNo.Yes);
						}
						


						if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "successfully clicked on finish button after convert to portfolio", YesNo.No);


							String labelWithValues[][] = {{excelLabel.Stage.toString(),Stage.Closed.toString()},
																{excelLabel.Deal_Quality_Score.toString(),String.valueOf(dealQualityScore)}};

							for (String[] lbWithValue : labelWithValues) {
								 label = lbWithValue[0];
								 value = lbWithValue[1];
								if (fp.FieldValueVerificationOnFundPage(projectName, label,value)) {
									log(LogStatus.INFO, label+" with value "+value+" verified after converting to portfolio", YesNo.No);

								}else {
									log(LogStatus.ERROR, label+" with value "+value+" not verified after converting to portfolio", YesNo.Yes);
									sa.assertTrue(false,label+" with value "+value+" not verified after converting to portfolio");
								}
							}

						}else {
							sa.assertTrue(false,"could not verify convert to portfolio as finish button not clicked");
							log(LogStatus.SKIP,"could not verify convert to portfolio as finish button not clicked",YesNo.Yes);
						}
						int i=0;
						String[][] labelWithValues = { 
								{ excelLabel.Company.toString(),SmokePFIns1,Header.Institution.toString()},
								{excelLabel.Source_Contact.toString(),SmokePFContact1FName+" "+SmokePFContact1LName,Header.Contact.toString()},
								{excelLabel.Source_Firm.toString(),SmokePFIns2,Header.Institution.toString()}} ;
							ThreadSleep(2000);
							for (String[] labelWithValue : labelWithValues) {
								label=labelWithValue[0];
								value=labelWithValue[1];
								String header = labelWithValue[2];
								ele=cp.getElementAtPage(projectName, label, value, action.SCROLLANDBOOLEAN, 20);
								if (ele!=null) {
									log(LogStatus.INFO, label+" with value "+value+" found", YesNo.No);
									if (click(driver, ele, value, action.BOOLEAN)) {
										log(LogStatus.INFO, "Click on "+value, YesNo.Yes);	
										ThreadSleep(2000);
										ele = cp.verifyCreatedItemOnPage(header, value);
										if (ele!=null) {
											log(LogStatus.INFO, "On "+header+" Page "+value+" verified", YesNo.Yes);		
										} else {
											log(LogStatus.ERROR, "On "+header+" Page "+value+" not verified", YesNo.Yes);
											sa.assertTrue(false,"On "+header+" Page "+value+" not verified");
										}
										if (i==0) {
											label=excelLabel.Record_Type.toString();
											value="Portfolio Company";
										} else {
											label=excelLabel.Average_Deal_Quality_Score.toString();
											value=String.valueOf(averageDealQualityScore);
											
										}
										if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, label,value)) {
											log(LogStatus.INFO, label+" with value "+value+" verified", YesNo.No);

										}else {
											log(LogStatus.ERROR, label+" with value "+value+" not verified", YesNo.Yes);
											sa.assertTrue(false,label+" with value "+value+" not verified");
										}
										driver.navigate().back();
										ThreadSleep(5000);
									} else {
										log(LogStatus.ERROR, "Not Able to Click on "+value, YesNo.Yes);
										sa.assertTrue(false,"Not Able to Click on "+value);

									}
								}else {
									log(LogStatus.ERROR, label+" with value "+value+" not found", YesNo.Yes);
									sa.assertTrue(false,label+" with value "+value+" not found");
								}
								i++;
								
							}

					}else {
						sa.assertTrue(false,"not able to click on next button");
						log(LogStatus.SKIP,"not able to click on next button",YesNo.Yes);
					}


				}else {
					sa.assertTrue(false,"not able to click on convert to portfolio button");
					log(LogStatus.SKIP,"not able to click on convert to portfolio button",YesNo.Yes);
				}

			}else {
				sa.assertTrue(false,"Not Able to click "+SmokeDeal2);
				log(LogStatus.SKIP,"Not Able to click "+SmokeDeal2,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	
	}

	@Parameters({ "projectName"})
	@Test
	public void SmokeTc024_CreatedFundInstitutionFundraisingRecord(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		//institution

			if (lp.clickOnTab(projectName, tabObj1)) {
				log(LogStatus.INFO,"Click on Tab : "+tabObj1,YesNo.No);	
				
				if (ip.createEntityOrAccount(projectName,"", SmokeFRIns1, SmokeFRIns1RecordType,null, null, 20)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+SmokeFRIns1+" of record type : "+SmokeFRIns1RecordType,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+SmokeFRIns1+" of record type : "+SmokeFRIns1RecordType);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+SmokeFRIns1+" of record type : "+SmokeFRIns1RecordType,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj1);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj1,YesNo.Yes);
			}
		
		
		// Fund 
		if (lp.clickOnTab(projectName, TabName.Object3Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object3Tab,YesNo.No);	
			String[] funds = {SmokeFRFund1,SmokeFRFund1Type,SmokeFRFund1Category,SmokeFRFund1RecordType};
			if (fp.createFundPE(projectName, funds[0], funds[3], funds[1], funds[2], null, 15)) {
				log(LogStatus.INFO,"Created Fund : "+funds[0],YesNo.No);			
			
			} else {
				sa.assertTrue(false,"Not Able to Create Fund : "+funds[0]);
				log(LogStatus.SKIP,"Not Able to Create Fund  : "+funds[0],YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object3Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object3Tab,YesNo.Yes);
		}
		
		// FR
		if (bp.clickOnTab(environment, mode, TabName.FundraisingsTab)) {
			if (fr.createFundRaising(environment, mode, SmokeFR2, SmokeFR2Fund, SmokeFR2LegalName, null, SmokeFR2Stage,
					null, null)) {
				appLog.info("fundraising is created : " + SmokeFR2);

			} else {
				appLog.error("Not able to create fundraising: " + SmokeFR2);
				sa.assertTrue(false, "Not able to create fundraising: " + SmokeFR2);
			}
		} else {
			appLog.error("Not able to click on fundraising tab so cannot create fundraising: " + SmokeFR2);
			sa.assertTrue(false, "Not able to click on fundraising tab so cannot create fundraising: " + SmokeFR2);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc025_1_VerifyTheStagePathOnTheFundRaisingPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele;
		String label="";
		String value="";
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
			log(LogStatus.INFO,"Click on Fundraisings tab",YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, SmokeFR2, 10)){
				log(LogStatus.INFO," Able to click "+SmokeFR2,YesNo.No);
				String stage = DealStage.Current.toString();
				String stageValue = SmokeFR2Stage;
				ele =  dp.getStagePath(stage,stageValue);
				if (ele!=null) {
					log(LogStatus.INFO,"Stage Path have "+stage+" stage as "+stageValue,YesNo.No);
				}else {
					sa.assertTrue(false,"Stage Path should have "+stage+" stage as "+stageValue);
					log(LogStatus.SKIP,"Stage Path Should have "+stage+" stage as "+stageValue,YesNo.Yes);
				}
				
				label=excelLabel.Last_Stage_Change_Date.toString();
				value=todaysDate;
				if (fp.FieldValueVerificationOnFundPage(projectName, label,value)) {
					log(LogStatus.INFO, label+" with value "+value+" verified", YesNo.No);

				}else {
					log(LogStatus.ERROR, label+" with value "+value+" not verified", YesNo.Yes);
					sa.assertTrue(false,label+" with value "+value+" not verified");
				}
				
				ele = dp.getMarkStageCompleteButton(10);
				if (click(driver, ele, "Mark Stage as Complete button ", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"click on Mark Stage as Complete button ",YesNo.No);
					ThreadSleep(10000);
					stage = DealStage.Completed.toString();
					stageValue = SmokeFR2Stage;
					ele =  dp.getStagePath(stage,stageValue);
					if (ele!=null) {
						log(LogStatus.INFO,"Stage Path have "+stage+" stage as "+stageValue+" after click on Mark Stage as Complete button ",YesNo.No);
					}else {
						sa.assertTrue(false,"Stage Path should have "+stage+" stage as "+stageValue+" after click on Mark Stage as Complete button ");
						log(LogStatus.SKIP,"Stage Path Should have "+stage+" stage as "+stageValue+" after click on Mark Stage as Complete button ",YesNo.Yes);
					}

					stage = DealStage.Current.toString();
					stageValue = Stage.Sent_PPM.toString();
					ele =  dp.getStagePath(stage,stageValue);
					if (ele!=null) {
						log(LogStatus.INFO,"Stage Path have "+stage+" stage as "+stageValue+" after click on Mark Stage as Complete button ",YesNo.No);
					}else {
						sa.assertTrue(false,"Stage Path should have "+stage+" stage as "+stageValue+" after click on Mark Stage as Complete button ");
						log(LogStatus.SKIP,"Stage Path Should have "+stage+" stage as "+stageValue+" after click on Mark Stage as Complete button ",YesNo.Yes);
					}

					label=excelLabel.Last_Stage_Change_Date.toString();
					value=todaysDate;
					if (fp.FieldValueVerificationOnFundPage(projectName, label,value)) {
						log(LogStatus.INFO, label+" with value "+value+" verified", YesNo.No);

					}else {
						log(LogStatus.ERROR, label+" with value "+value+" not verified", YesNo.Yes);
						sa.assertTrue(false,label+" with value "+value+" not verified");
					}
			}else {
					sa.assertTrue(false,"Not able to click on Mark Stage as Complete button ");
					log(LogStatus.SKIP,"Not able to click on Mark Stage as Complete button ",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"Not Able to click "+SmokeFR2);
				log(LogStatus.SKIP,"Not Able to click "+SmokeFR2,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on Fundraisings tab");
			log(LogStatus.SKIP,"not able to click on Fundraisings tab",YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc025_2_ChangeTheFundraisingStageagAndVerifyTheImpactOnTheLastStageChangeDate(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String label="";
		String value="";
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
			log(LogStatus.INFO,"Click on Fundraisings tab",YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, SmokeFR2, 10)){
				log(LogStatus.INFO," Able to click "+SmokeFR2,YesNo.No);
				String stage = Stage.Declined.toString();
				if (fp.changeStage(projectName, stage, 10)) {	
					log(LogStatus.INFO," able to change stage to "+stage,YesNo.No);
				}else {
					sa.assertTrue(false,"not able to change stage to "+stage);
					log(LogStatus.SKIP,"not able to change stage to "+stage,YesNo.Yes);
				}
				
				ThreadSleep(5000);
				String labelWithValues[][] = {{excelLabel.Last_Stage_Change_Date.toString(),todaysDate},
						{excelLabel.Stage.toString(),stage}};

				for (String[] lbWithValue : labelWithValues) {
					label=lbWithValue[0];
					value=lbWithValue[1];
					if (fp.FieldValueVerificationOnFundPage(projectName, label,value)) {
						log(LogStatus.INFO, label+" with value "+value+" verified", YesNo.No);

					}else {
						log(LogStatus.ERROR, label+" with value "+value+" not verified", YesNo.Yes);
						sa.assertTrue(false,label+" with value "+value+" not verified");
					}
				}
				
			}else {
				sa.assertTrue(false,"Not Able to click "+SmokeFR2);
				log(LogStatus.SKIP,"Not Able to click "+SmokeFR2,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on Fundraisings tab");
			log(LogStatus.SKIP,"not able to click on Fundraisings tab",YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc026_CreateACompanyAndVerifyConversionDate(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String value="";
		String type="";
		String status="";
		String[][] EntityOrAccounts = {{ SmokeCdIns1, SmokeCdIns1RecordType ,SmokeCdIns1Status},
				{ SmokeCdIns2, SmokeCdIns2RecordType ,SmokeCdIns2Status}};
		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, tabObj1)) {
				log(LogStatus.INFO,"Click on Tab : "+tabObj1,YesNo.No);	
				value = accounts[0];
				type = accounts[1];
				status = accounts[2];
				if (ip.createEntityOrAccount(projectName, mode, value, type, null, new String[][]{{"Status",status}}, 20)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj1);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj1,YesNo.Yes);
			}
		}

		String fieldName ="";
		String fieldValue="";
		String updatedStatus= SmokeCdIns2Status;
		String[][] otherLabel ={{"Status",SmokeCdIns1Status},{"Deal Conversion Date",""}};
		if (lp.clickOnTab(projectName, tabObj1)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj1,YesNo.No);	
			
			if(lp.clickOnAlreadyCreatedItem(projectName, TabName.InstituitonsTab,SmokeCdIns1, 30)){
				log(LogStatus.INFO,"Click on created institution : "+SmokeCdIns1,YesNo.No);	
				for(String[] labelWithValue:otherLabel){
					
					fieldName=labelWithValue[0];
					fieldValue=labelWithValue[1];
					
				if(ip.fieldValueVerificationOnInstitutionPage(environment, mode, TabName.InstituitonsTab, fieldName,fieldValue)){
					
					log(LogStatus.INFO,"field :"+fieldName+"  is matched with value:"+fieldValue,YesNo.No);	

				}else{
					sa.assertTrue(false,"field :"+fieldName+"  is not matched with value:"+fieldValue);
					log(LogStatus.SKIP,"field :"+fieldName+"  is not matched with value:"+fieldValue,YesNo.Yes);
					
				}
				}
				
				if(ip.changeStatus(projectName,updatedStatus)){
					String[][]	newOtherLabel ={{"Status",updatedStatus},{"Deal Conversion Date",todaysDate}};
					for(String[] labelWithValue:newOtherLabel){
						
						fieldName=labelWithValue[0];
						fieldValue=labelWithValue[1];
						
					if(ip.fieldValueVerificationOnInstitutionPage(environment, mode, TabName.InstituitonsTab, fieldName,fieldValue)){
						log(LogStatus.INFO,"field :"+fieldName+"  is matched with value:"+fieldValue,YesNo.No);	

						
					}else{
						
						sa.assertTrue(false,"field :"+fieldName+"  is not matched with value:"+fieldValue);
						log(LogStatus.SKIP,"field :"+fieldName+"  is not matched with value:"+fieldValue,YesNo.Yes);
					}
					}
					
				}else{
					sa.assertTrue(false,"Not able to change status with vlaue:"+updatedStatus);
					log(LogStatus.SKIP,"Not able to change status with vlaue:"+updatedStatus,YesNo.Yes);
				}
					
				
			}else{
				sa.assertTrue(false,"Not able to open institution:"+SmokeCdIns1);
				log(LogStatus.SKIP,"Not able to open institution:"+SmokeCdIns1,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj1);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj1,YesNo.Yes);
		}
		
		String[][] otherLabel2 ={{"Status",SmokeCdIns2Status},{"Deal Conversion Date",""}};
		if (lp.clickOnTab(projectName, tabObj1)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj1,YesNo.No);	
			
			if(lp.clickOnAlreadyCreatedItem(projectName, TabName.InstituitonsTab,SmokeCdIns2, 30)){
				log(LogStatus.INFO,"Click on created institution : "+SmokeCdIns2,YesNo.No);	
				for(String[] labelWithValue:otherLabel2){
					
					fieldName=labelWithValue[0];
					fieldValue=labelWithValue[1];
					
				if(ip.fieldValueVerificationOnInstitutionPage(environment, mode, TabName.InstituitonsTab, fieldName,fieldValue)){
					
					log(LogStatus.INFO,"field :"+fieldName+"  is matched with value:"+fieldValue,YesNo.No);	

				}else{
					sa.assertTrue(false,"field :"+fieldName+"  is not matched with value:"+fieldValue);
					log(LogStatus.SKIP,"field :"+fieldName+"  is not matched with value:"+fieldValue,YesNo.Yes);
					
				}
				}
				updatedStatus =SmokeCdIns1Status;
				if(ip.changeStatus(projectName,updatedStatus)){
					String[][]	newOtherLabel ={{"Status",updatedStatus},{"Deal Conversion Date",""}};
					for(String[] labelWithValue:newOtherLabel){
						
						fieldName=labelWithValue[0];
						fieldValue=labelWithValue[1];
						
					if(ip.fieldValueVerificationOnInstitutionPage(environment, mode, TabName.InstituitonsTab, fieldName,fieldValue)){
						log(LogStatus.INFO,"field :"+fieldName+"  is matched with value:"+fieldValue,YesNo.No);	

						
					}else{
						
						sa.assertTrue(false,"field :"+fieldName+"  is not matched with value:"+fieldValue);
						log(LogStatus.SKIP,"field :"+fieldName+"  is not matched with value:"+fieldValue,YesNo.Yes);
					}
					}
					
				}else{
					sa.assertTrue(false,"Not able to change status with vlaue:"+updatedStatus);
					log(LogStatus.SKIP,"Not able to change status with vlaue:"+updatedStatus,YesNo.Yes);
				}
					
				
			}else{
				sa.assertTrue(false,"Not able to open institution:"+SmokeCdIns2);
				log(LogStatus.SKIP,"Not able to open institution:"+SmokeCdIns2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj1);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj1,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void SmokeTc027_CreateACompanyAndContactWithStatusWatchlist(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String value="";
		String type="";
		String status="";
		String[][] EntityOrAccounts = {{ SmokeWlIns1, SmokeWlIns1RecordType ,SmokeWlIns1Status},
				{ SmokeWlIns2, SmokeWlIns2RecordType ,SmokeWlIns2Status}};
		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, tabObj1)) {
				log(LogStatus.INFO,"Click on Tab : "+tabObj1,YesNo.No);	
				value = accounts[0];
				type = accounts[1];
				status = accounts[2];
				if (ip.createEntityOrAccount(projectName, mode, value, type, null, new String[][]{{"Status",status}}, 20)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj1);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj1,YesNo.Yes);
			}
		}

		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			SmokeWlContact1EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, SmokeWlContact1EmailID, "Contacts", excelLabel.Variable_Name, "SMOKEWLCON1",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, SmokeWlContact1FName, SmokeWlContact1LName, SmokeWlContact1Inst, SmokeWlContact1EmailID,"", null, null, CreationPage.ContactPage, null, SmokeWlContact1Tier)) {
				log(LogStatus.INFO,"successfully Created Contact : "+SmokeWlContact1FName+" "+SmokeWlContact1LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+SmokeWlContact1FName+" "+SmokeWlContact1LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+SmokeWlContact1FName+" "+SmokeWlContact1LName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
		
		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			SmokeWlContact2EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, SmokeWlContact2EmailID, "Contacts", excelLabel.Variable_Name, "SMOKEWLCON2",excelLabel.Contact_EmailId);
			if (cp.createContact(projectName, SmokeWlContact2FName, SmokeWlContact2LName, SmokeWlContact2Inst, SmokeWlContact2EmailID,"", null, null, CreationPage.ContactPage, null, SmokeWlContact2Tier)) {
				log(LogStatus.INFO,"successfully Created Contact : "+SmokeWlContact2FName+" "+SmokeWlContact2LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+SmokeWlContact2FName+" "+SmokeWlContact2LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+SmokeWlContact2FName+" "+SmokeWlContact2LName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}

		

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();

	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc028_CreateWatchlistTaskEventsAndCall(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String task = "";
		
		SmokeWLTask1dueDate=todaysDate;
		SmokeWLTask2dueDate=todaysDate;
		
		ExcelUtils.writeData(phase1DataSheetFilePath,SmokeWLTask1dueDate, "Task1", excelLabel.Variable_Name, "SmokeWLTask1", excelLabel.Due_Date);
		ExcelUtils.writeData(phase1DataSheetFilePath,SmokeWLTask1dueDate, "Task1", excelLabel.Variable_Name, "SmokeWLTask2", excelLabel.Due_Date);

		String[][][] basicTask = {{{PageLabel.Subject.toString(),SmokeWLTask1Subject},
			{PageLabel.Related_To.toString(),SmokeWlContact1FName+" "+SmokeWlContact1LName},
			{PageLabel.Related_To.toString(),SmokeWlContact1Inst}},
				
				{{PageLabel.Subject.toString(),SmokeWLTask2Subject},
				{PageLabel.Related_To.toString(),SmokeWlContact2FName+" "+SmokeWlContact2LName},
				{PageLabel.Related_To.toString(),SmokeWlContact2Inst}}};
		
		String[][][] advancetask= {{{"Due Date",SmokeWLTask1dueDate},
			{PageLabel.Priority.toString(),SmokeWLTask1Priority},
			{PageLabel.Status.toString(),SmokeWLTask1Status}},
			{{"Due Date",SmokeWLTask2dueDate},
			{PageLabel.Priority.toString(),SmokeWLTask2Priority},
			{PageLabel.Status.toString(),SmokeWLTask2Status}}};
		
		
		for(int i=0;i<2;i++){
			
			
			if (lp.createActivityTimeline("", true, NewInteractions_DefaultValues.Task.toString(), basicTask[i], advancetask[i], null, null, false, null, null, null, null, null, null)) {
				log(LogStatus.INFO,"Able to create task : "+task,YesNo.No);

			} else {
				sa.assertTrue(false,"Not Able to create task : "+task);
				log(LogStatus.SKIP,"Not Able Able to create task : "+task,YesNo.Yes);	
			}
			refresh(driver);
			ThreadSleep(5000);
		}
		

		// Event functionality remove after acutiy changes
//		SmokeWLEvent1StartDate=todaysDate;
//		SmokeWLEvent1EndDate=todaysDate;
//		SmokeWLEvent2StartDate=todaysDate;
//		SmokeWLEvent2EndDate=todaysDate;
//		
//		ExcelUtils.writeData(phase1DataSheetFilePath,SmokeWLEvent1StartDate, "Events", excelLabel.Variable_Name, "SmokeWLEvent1", excelLabel.Start_Date);
//		ExcelUtils.writeData(phase1DataSheetFilePath,SmokeWLEvent1EndDate, "Events", excelLabel.Variable_Name, "SmokeWLEvent1", excelLabel.End_Date);
//
//		ExcelUtils.writeData(phase1DataSheetFilePath,SmokeWLEvent1StartDate, "Events", excelLabel.Variable_Name, "SmokeWLEvent2", excelLabel.Start_Date);
//		ExcelUtils.writeData(phase1DataSheetFilePath,SmokeWLEvent2EndDate, "Events", excelLabel.Variable_Name, "SmokeWLEvent2", excelLabel.End_Date);
//
//		String[][][] event1 = {{{PageLabel.Subject.toString(),SmokeWLEvent1Subject},
//				{PageLabel.Name.toString(),SmokeWlContact1FName+" "+SmokeWlContact1LName},
//				{PageLabel.Start_Date.toString(),SmokeWLEvent1StartDate},
//				{PageLabel.End_Date.toString(),SmokeWLEvent1EndDate}},
//				
//				
//				{{PageLabel.Subject.toString(),SmokeWLEvent2Subject},
//				{PageLabel.Name.toString(),SmokeWlContact2FName+" "+SmokeWlContact2LName},
//				{PageLabel.Related_To.toString(),SmokeWlContact2Inst},
//				{PageLabel.Start_Date.toString(),SmokeWLEvent2StartDate},
//				{PageLabel.End_Date.toString(),SmokeWLEvent2EndDate}}};
//
//		for(int i=0;i<event1.length;i++){
//			task = event1[i][0][1];
//		if (gp.clickOnGlobalActionAndEnterValue(projectName, GlobalActionItem.New_Event, event1[i])) {
//			log(LogStatus.INFO,"Able to Enter Value for : "+task,YesNo.No);
//			
//			if (click(driver, gp.getSaveButtonForEvent(projectName, 10), "Save Button", action.SCROLLANDBOOLEAN)) {
//				log(LogStatus.INFO,"Click on Save Button For Task : "+task,YesNo.No);		
//				
//			}else {
//				sa.assertTrue(false,"Not Able to Click on Save Button For Task : "+task);
//				log(LogStatus.SKIP,"Not Able to Click on Save Button For Task : "+task,YesNo.Yes);	
//			}
//		} else {
//			sa.assertTrue(false,"Not Able to Click/Enter Value : "+task);
//			log(LogStatus.SKIP,"Not Able to Click/Enter Value : "+task,YesNo.Yes);	
//		}
//		}
		
		
		String[][][] basicCall= {{{PageLabel.Subject.toString(),SmokeWLLogACall1Subject},
			{PageLabel.Related_To.toString(),SmokeWlContact1FName+" "+SmokeWlContact1LName},
			{PageLabel.Related_To.toString(),SmokeWlContact1Inst},
			
			{PageLabel.Subject.toString(),SmokeWLLogACall2Subject},
			{PageLabel.Related_To.toString(),SmokeWlContact2FName+" "+SmokeWlContact2LName},
			{PageLabel.Related_To.toString(),SmokeWlContact2Inst}}};
		
		for(int i=0;i<basicCall.length;i++){
			

			if (lp.createActivityTimeline("", true, NewInteractions_DefaultValues.Call.toString(), basicCall[i], null, null, null, false, null, null, null, null, null, null)) {
				log(LogStatus.INFO,"Able to create task : "+task,YesNo.No);

			} else {
				sa.assertTrue(false,"Not Able to create task : "+task);
				log(LogStatus.SKIP,"Not Able Able to create task : "+task,YesNo.Yes);	
			}
			refresh(driver);
			ThreadSleep(5000);
		}
		String[] task2= {SmokeWLTask1Subject,SmokeWLTask2Subject};
		for(int i=0;i<task2.length;i++){
		
		
		if(tp.clickOnTab(projectName, mode, TabName.TaskTab)){
			log(LogStatus.PASS,"Click on task tab",YesNo.Yes);

			if(tp.clickOnAlreadyCreatedItem(projectName,TabName.TaskTab, task2[i], 30)){
				log(LogStatus.PASS,"Click on created task:"+task,YesNo.Yes);
				WebElement ele= tp.getLabelForTaskInViewMode( projectName, PageName.TaskPage,PageLabel.Watchlist.toString(),action.BOOLEAN, 10);
				if(ele==null){
					log(LogStatus.INFO,"Watchlist Check Box Filed Not present : "+task,YesNo.No);
				}else{
					sa.assertTrue(false,"Watchlist Check Box Filed is present but it should not be present: "+task);
					log(LogStatus.SKIP,"Watchlist Check Box Filed is present but it should not be present : "+task,YesNo.Yes);	
				}
			}else{
				sa.assertTrue(false,"not able to Click on created task:"+task);
				log(LogStatus.SKIP,"not able to Click on created task:"+task,YesNo.Yes);
				
			}
		}else{
			
			sa.assertTrue(false,"Not able to Click on task tab"+task);
			log(LogStatus.SKIP,"Not able to Click on task tab"+task,YesNo.Yes);
		}}
		String[] logACall1= {SmokeWLLogACall1Subject,SmokeWLLogACall2Subject};
		for(int i=0;i<logACall1.length;i++){
		
		
		if(tp.clickOnTab(projectName, mode, TabName.TaskTab)){
			log(LogStatus.PASS,"Click on task tab",YesNo.Yes);

			if(tp.clickOnAlreadyCreatedItem(projectName,TabName.TaskTab, logACall1[i], 30)){
				log(LogStatus.PASS,"Click on created task:"+task,YesNo.Yes);
				WebElement ele= tp.getLabelForTaskInViewMode( projectName, PageName.TaskPage,PageLabel.Watchlist.toString(),action.BOOLEAN, 10);
				if(ele==null){
					log(LogStatus.INFO,"Watchlist Check Box Filed Not present : "+task,YesNo.No);
				}else{
					sa.assertTrue(false,"Watchlist Check Box Filed is present but it should not be present: "+task);
					log(LogStatus.SKIP,"Watchlist Check Box Filed is present but it should not be present : "+task,YesNo.Yes);	
				}
			}else{
				sa.assertTrue(false,"not able to Click on created task:"+task);
				log(LogStatus.SKIP,"not able to Click on created task:"+task,YesNo.Yes);
				
			}
		}else{
			
			sa.assertTrue(false,"Not able to Click on task tab"+task);
			log(LogStatus.SKIP,"Not able to Click on task tab"+task,YesNo.Yes);
		}}
//		String primaryContact=SmokeWlContact1FName+" "+SmokeWlContact1LName;
//		String secondaryContact=SmokeWlContact2FName+" "+SmokeWlContact2LName;
//		String task3 = SmokeWLEvent1Subject;
//		if (cp.clickOnTab(projectName, tabObj2)) {
//			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+primaryContact,YesNo.No);
//			if (cp.clickOnAlreadyCreatedItem(projectName, primaryContact, 30)) {
//				log(LogStatus.INFO,"Clicked on  : "+primaryContact+" For : "+tabObj2,YesNo.No);
//				
//				click(driver, lp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10), RelatedTab.Details.toString(), action.SCROLLANDBOOLEAN);
//				ThreadSleep(5000);
//				if (click(driver, lp.getTaskLink(projectName, task3), task3, action.SCROLLANDBOOLEAN)) {
//					log(LogStatus.INFO,"Click on Task : "+task3,YesNo.No);
//					ThreadSleep(2000);
//					WebElement ele= tp.getLabelForTaskInViewMode( projectName, PageName.TaskPage,PageLabel.Watchlist.toString(),action.BOOLEAN, 10);
//					if(ele==null){
//						log(LogStatus.INFO,"Watchlist Check Box Filed Not present : "+task,YesNo.No);
//					}else{
//						sa.assertTrue(false,"Watchlist Check Box Filed is present but it should not be present: "+task);
//						log(LogStatus.SKIP,"Watchlist Check Box Filed is present but it should not be present : "+task,YesNo.Yes);	
//					}	
//				} else {
//					sa.assertTrue(false,"Not Able to Click on Task : "+task);
//					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
//				}
//
//			} else {
//				sa.assertTrue(false,"Item Not Found : "+primaryContact+" For : "+tabObj2);
//				log(LogStatus.SKIP,"Item Not Found : "+primaryContact+" For : "+tabObj2,YesNo.Yes);
//			}
//		} else {
//			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact);
//			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+primaryContact,YesNo.Yes);
//		}
//		
		// Event functionality removed after acutiy changes
//		String task4 = SmokeWLEvent2Subject;
//		if (cp.clickOnTab(projectName, tabObj2)) {
//			log(LogStatus.INFO,"Clicked on Tab : "+tabObj2+" For : "+secondaryContact,YesNo.No);
//			if (cp.clickOnAlreadyCreatedItem(projectName, secondaryContact, 30)) {
//				log(LogStatus.INFO,"Clicked on  : "+secondaryContact+" For : "+tabObj2,YesNo.No);
//				if (click(driver, lp.getTaskLink(projectName, task4), task4, action.SCROLLANDBOOLEAN)) {
//					log(LogStatus.INFO,"Click on Task : "+task4,YesNo.No);
//					ThreadSleep(2000);
//					WebElement ele= tp.getLabelForTaskInViewMode( projectName, PageName.TaskPage,PageLabel.Watchlist.toString(),action.BOOLEAN, 10);
//					if(ele==null){
//						log(LogStatus.INFO,"Watchlist Check Box Filed Not present : "+task,YesNo.No);
//					}else{
//						sa.assertTrue(false,"Watchlist Check Box Filed is present but it should not be present: "+task);
//						log(LogStatus.SKIP,"Watchlist Check Box Filed is present but it should not be present : "+task,YesNo.Yes);	
//					}	
//				} else {
//					sa.assertTrue(false,"Not Able to Click on Task : "+task);
//					log(LogStatus.SKIP,"Not Able to Click on Task : "+task,YesNo.Yes);
//				}
//
//			} else {
//				sa.assertTrue(false,"Item Not Found : "+secondaryContact+" For : "+tabObj2);
//				log(LogStatus.SKIP,"Item Not Found : "+secondaryContact+" For : "+tabObj2,YesNo.Yes);
//			}
//		} else {
//			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2+" For : "+secondaryContact);
//			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2+" For : "+secondaryContact,YesNo.Yes);
//		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc029_UpdateWatchlistAndVerifyContact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		TaskPageBusinessLayer tp= new TaskPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String task = SmokeWLTask1Subject;
		
		if(tp.clickOnTab(projectName, mode, TabName.TaskTab)){
			log(LogStatus.PASS,"Click on task tab",YesNo.Yes);

			if(tp.clickOnAlreadyCreatedItem(projectName,TabName.TaskTab, task, 30)){
				log(LogStatus.PASS,"Click on created task:"+task,YesNo.Yes);

				ThreadSleep(5000);
				if(tp.EditEnterNameAndSave(projectName, task, SmokeWlContact2FName+" "+SmokeWlContact2LName,true)){
					log(LogStatus.PASS,"Name successfully upated of task :"+task,YesNo.Yes);
				}else{
					
					sa.assertTrue(false,"Not Able to Name  of task :"+task);
					log(LogStatus.SKIP,"Not Able to Name  of task :"+task,YesNo.Yes);
				}
			
			}else{
				sa.assertTrue(false,"not able to Click on created task:"+task);
				log(LogStatus.SKIP,"not able to Click on created task:"+task,YesNo.Yes);
				
			}
		}else{
			
			sa.assertTrue(false,"Not able to Click on task tab"+task);
			log(LogStatus.SKIP,"Not able to Click on task tab"+task,YesNo.Yes);
		}
		WebElement ele= tp.getLabelForTaskInViewMode( projectName, PageName.TaskPage,PageLabel.Watchlist.toString(),action.BOOLEAN, 10);
		if(ele==null){
			log(LogStatus.INFO,"Watchlist Check Box Filed Not present : "+task,YesNo.No);
		}else{
			sa.assertTrue(false,"Watchlist Check Box Filed is present but it should not be present: "+task);
			log(LogStatus.SKIP,"Watchlist Check Box Filed is present but it should not be present : "+task,YesNo.Yes);	
		}
		 
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc030_CreateACompanyAndContact(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

			if (lp.clickOnTab(projectName, tabObj1)) {
				log(LogStatus.INFO,"Click on Tab : "+tabObj1,YesNo.No);	
				
				if (ip.createEntityOrAccount(projectName, mode, SmokeLTPIns1, SmokeLTPIns1RecordType, null, null, 20)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+SmokeLTPIns1+" of record type : "+SmokeLTPIns1RecordType,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+SmokeLTPIns1+" of record type : "+SmokeLTPIns1RecordType);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+SmokeLTPIns1+" of record type : "+SmokeLTPIns1RecordType,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj1);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj1,YesNo.Yes);
			}
		

		String email="";
		String[][] contacts ={{SmokeLTPContact1FName,SmokeLTPContact1LName,SmokeLTPContact1Inst,SmokeLTPContact1Tier},
								{SmokeLTPContact2FName,SmokeLTPContact2LName,SmokeLTPContact2Inst,SmokeLTPContact2Tier},
								{SmokeLTPContact3FName,SmokeLTPContact3LName,SmokeLTPContact3Inst,SmokeLTPContact3Tier}};
		for(int i=0;i<contacts.length;i++){
		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			email=	lp.generateRandomEmailId(gmailUserName);
			if (cp.createContact(projectName, contacts[i][0], contacts[i][1], contacts[i][2], email,"", null, null, CreationPage.ContactPage, null, contacts[i][3])) {
				log(LogStatus.INFO,"successfully Created Contact : "+contacts[i][0]+" "+contacts[i][1],YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+contacts[i][0]+" "+contacts[i][1]);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+contacts[i][0]+" "+contacts[i][1],YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();

	}	
	
	@Parameters({"projectName"})
	@Test
	public void SmokeTc031_CreateCallAndVerifyLastTouchPointAtContactPage(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName=SmokeLTPContact1FName+" "+SmokeLTPContact1LName;
		String contactName2=SmokeLTPContact2FName+" "+SmokeLTPContact2LName;

		SmokeCTTask1dueDate=todaysDate;
		String task = SmokeLTPLogACall1Subject;

		SmokeLTPLogACall1dueDate=todaysDate;
		String[][] logACall = {{PageLabel.Subject.toString(),task},
				{PageLabel.Related_To.toString(),contactName2},{PageLabel.Related_To.toString(),contactName}};
		//String[][] advanceCall={{PageLabel.Status.toString(),SmokeLTPLogACall1Status}};
		String[][] advanceCall= null;
				

		if (lp.createActivityTimeline("",true, "Call", logACall, advanceCall, null, null, false, null, null, null, null, null, null)) {
			log(LogStatus.INFO,"Able to create task for : "+task,YesNo.No);
			
		} else {
			sa.assertTrue(false,"Not Able to create task : "+task);
			log(LogStatus.SKIP,"Not Able to create task : "+task,YesNo.Yes);	
		}
		if(lp.clickOnTab(contactName, mode, TabName.ContactTab)){
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);
			if(lp.clickOnAlreadyCreatedItem(projectName, contactName, 30)){
				log(LogStatus.INFO,"click on Created Contact : "+contactName,YesNo.No);	
				ThreadSleep(3000);
				click(driver, lp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10), "Related details tab", action.BOOLEAN);
				ThreadSleep(3000);
				String value= cp.getlastTouchPointValue(projectName, 30).getText();
//				if(cp.verifyDate(SmokeLTPLogACall1dueDate, value)){
					if(bp.verifyDate(SmokeLTPLogACall1dueDate, null, "M/d/yyyy")){
					log(LogStatus.INFO,"Last touch point value is matched in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Last touch point value is not matched in contact :"+contactName);
					log(LogStatus.SKIP,"Last touch point value is not matched in contact :"+contactName,YesNo.Yes);
				}
				
			}else{
				sa.assertTrue(false,"Not Able to click on Create Contact : "+contactName);
				log(LogStatus.SKIP,"Not Able to click on created Contact: "+contactName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
		
		if(lp.clickOnTab(contactName, mode, TabName.ContactTab)){
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);
			if(lp.clickOnAlreadyCreatedItem(projectName, contactName2, 30)){
				log(LogStatus.INFO,"click on Created Contact : "+contactName2,YesNo.No);	
				ThreadSleep(3000);
				click(driver, lp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10), "Related details tab", action.BOOLEAN);
				ThreadSleep(3000);
				String value= cp.getlastTouchPointValue(projectName, 30).getText();
//				if(cp.verifyDate(SmokeLTPLogACall1dueDate, value)){
					if(bp.verifyDate(SmokeLTPLogACall1dueDate, null, "M/d/yyyy")){
					log(LogStatus.INFO,"Last touch point value is matched in :"+contactName2,YesNo.No);	

				}else{
					sa.assertTrue(false,"Last touch point value is not matched in contact :"+contactName2);
					log(LogStatus.SKIP,"Last touch point value is not matched in contact :"+contactName2,YesNo.Yes);
				}
				
			}else{
				sa.assertTrue(false,"Not Able to click on Create Contact : "+contactName2);
				log(LogStatus.SKIP,"Not Able to click on created Contact: "+contactName2,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({"projectName"})
	@Test
	public void SmokeTc032_CreateEventAndVerifyLastTouchPointAtContactPage(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName=SmokeLTPContact3FName+" "+SmokeLTPContact3LName;
		
		String task = SmokeLTPEvent1Subject;
		
		if(lp.clickOnTab(contactName, mode, TabName.ContactTab)){
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);
			if(lp.clickOnAlreadyCreatedItem(projectName, TabName.ContactTab, contactName, 30)){
				log(LogStatus.INFO,"click on Created Contact : "+contactName,YesNo.No);	
				ThreadSleep(3000);
				
				SmokeLTPEvent1StartDate=previousOrForwardDateAccordingToTimeZone(-1, "M/d/YYYY", BasePageBusinessLayer.AmericaLosAngelesTimeZone);
				SmokeLTPEvent1EndDate=todaysDate;
				String[][] event1 = {{PageLabel.Subject.toString(),task},
						{PageLabel.Name.toString(),contactName},
						{PageLabel.Start_Date.toString(),SmokeLTPEvent1StartDate},
						{PageLabel.End_Date.toString(),SmokeLTPEvent1EndDate}
						};

				if (gp.clickOnGlobalActionAndEnterValue(projectName, GlobalActionItem.New_Event, event1)) {
					log(LogStatus.INFO,"Able to Enter Value for : "+task,YesNo.No);
					ExcelUtils.writeData(phase1DataSheetFilePath,SmokeLTPEvent1StartDate, "Events", excelLabel.Variable_Name, "SmokeLTPEvent1", excelLabel.Start_Date);
					ExcelUtils.writeData(phase1DataSheetFilePath,SmokeLTPEvent1EndDate, "Events", excelLabel.Variable_Name, "SmokeLTPEvent1", excelLabel.End_Date);

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
				
				refresh(driver);
				ThreadSleep(5000);
				String value= cp.getlastTouchPointValue(projectName, 30).getText();
//				if(cp.verifyDate(todaysDate, value)){
					if(bp.verifyDate(todaysDate, null, "M/d/yyyy")){
					log(LogStatus.INFO,"Last touch point value is matched in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Last touch point value is not matched in contact :"+contactName);
					log(LogStatus.SKIP,"Last touch point value is not matched in contact :"+contactName,YesNo.Yes);
				}
				
			}else{
				sa.assertTrue(false,"Not Able to click on Create Contact : "+contactName);
				log(LogStatus.SKIP,"Not Able to click on created Contact: "+contactName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}

		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({"projectName"})
	@Test
	public void SmokeTc033_UpdateTierAndVerifyLastTouchPointAndNextTouchPointAtContactPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName=SmokeNTPContact1FName+" "+SmokeNTPContact1LName;
				
		if (lp.clickOnTab(projectName, tabObj1)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj1,YesNo.No);	
			
			if (ip.createEntityOrAccount(projectName, mode, SmokeNTPIns1, SmokeNTPIns1RecordType, null, null, 20)) {
				log(LogStatus.INFO,"successfully Created Account/Entity : "+SmokeNTPIns1+" of record type : "+SmokeNTPIns1RecordType,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Account/Entity : "+SmokeNTPIns1+" of record type : "+SmokeNTPIns1RecordType);
				log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+SmokeNTPIns1+" of record type : "+SmokeNTPIns1RecordType,YesNo.Yes);
			}


		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj1);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj1,YesNo.Yes);
		}
	
	if (lp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
		SmokeNTPContact1EmailID=	lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(phase1DataSheetFilePath, SmokeNTPContact1EmailID, "Contacts", excelLabel.Variable_Name, "SMOKENTPCON1",excelLabel.Contact_EmailId);
		if (cp.createContact(projectName, SmokeNTPContact1FName, SmokeNTPContact1LName, SmokeNTPContact1Inst, SmokeNTPContact1EmailID,"", null, null, CreationPage.ContactPage, null,null)) {
			log(LogStatus.INFO,"successfully Created Contact : "+contactName,YesNo.No);	
			refresh(driver);	
			ThreadSleep(10000);
			String lastTouchPoint= cp.getlastTouchPointValue(projectName, 30).getText();
			if(lastTouchPoint.contains("")||lastTouchPoint.equalsIgnoreCase("")){
				log(LogStatus.INFO,"Last touch point value is matched As blank in :"+contactName,YesNo.No);	

			}else{
				sa.assertTrue(false,"Last touch point value is not matched As blank in contact :"+contactName);
				log(LogStatus.SKIP,"Last touch point value is not matched As blank in contact :"+contactName,YesNo.Yes);
			}
			
			int days=120;
			String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
			String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "M/d/yyyy", "America/Los_Angles");
		if(cp.verifyDate(expectedDate, actualDate)){
//				if(bp.verifyDate(expectedDate, null,actualDate)){
				log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	

			}else{
				sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
				log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Create Contact : "+contactName);
			log(LogStatus.SKIP,"Not Able to Create Contact: "+contactName,YesNo.Yes);
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
		log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
	}
	
	String tier ="1";
	if(lp.clickOnTab(contactName, mode, TabName.ContactTab)){
		log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);
		if(lp.clickOnAlreadyCreatedItem(projectName, TabName.ContactTab, contactName, 30)){
			log(LogStatus.INFO,"click on Created Contact : "+contactName,YesNo.No);	
			ThreadSleep(3000);
			
			if(cp.UpdateContactTier(projectName, PageName.ContactPage, tier)){
				
				log(LogStatus.INFO,"Contact tier updated as: "+tier,YesNo.No);	
				refresh(driver);		
				ThreadSleep(5000);
				String lastTouchPoint= cp.getlastTouchPointValue(projectName, 30).getText();
				if(lastTouchPoint.contains("")||lastTouchPoint.equalsIgnoreCase("")){
					log(LogStatus.INFO,"Last touch point value is matched As blank in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Last touch point value is not matched As blank in contact :"+contactName);
					log(LogStatus.SKIP,"Last touch point value is not matched As blank in contact :"+contactName,YesNo.Yes);
				}
				
				int days=90;
				String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
				String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "M/d/yyyy", "America/Los_Angles");
				if(cp.verifyDate(expectedDate, actualDate)){
					log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
					log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
				}
			}else{
				sa.assertTrue(false,"Not Able to Update Contact  tier: "+contactName);
				log(LogStatus.SKIP,"Not Able to Update Contact  tier: "+contactName,YesNo.Yes);
			}
			
		}else{
			sa.assertTrue(false,"Not Able to click on Create Contact : "+contactName);
			log(LogStatus.SKIP,"Not Able to click on created Contact: "+contactName,YesNo.Yes);
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
		log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
	}
	
	 tier ="2";
	if(lp.clickOnTab(contactName, mode, TabName.ContactTab)){
		log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);
		if(lp.clickOnAlreadyCreatedItem(projectName, TabName.ContactTab, contactName, 30)){
			log(LogStatus.INFO,"click on Created Contact : "+contactName,YesNo.No);	
			ThreadSleep(3000);
			
			if(cp.UpdateContactTier(projectName, PageName.ContactPage, tier)){
				
				log(LogStatus.INFO,"Contact tier updated as: "+tier,YesNo.No);	
				refresh(driver);		
				ThreadSleep(5000);
				String lastTouchPoint= cp.getlastTouchPointValue(projectName, 30).getText();
				if(lastTouchPoint.contains("")||lastTouchPoint.equalsIgnoreCase("")){
					log(LogStatus.INFO,"Last touch point value is matched As blank in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Last touch point value is not matched As blank in contact :"+contactName);
					log(LogStatus.SKIP,"Last touch point value is not matched As blank in contact :"+contactName,YesNo.Yes);
				}
				
				int days=120;
				String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
				String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "M/d/yyyy", "America/Los_Angles");
				if(cp.verifyDate(expectedDate, actualDate)){
					log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
					log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
				}
			}else{
				sa.assertTrue(false,"Not Able to Update Contact  tier: "+contactName);
				log(LogStatus.SKIP,"Not Able to Update Contact  tier: "+contactName,YesNo.Yes);
			}
			
		}else{
			sa.assertTrue(false,"Not Able to click on Create Contact : "+contactName);
			log(LogStatus.SKIP,"Not Able to click on created Contact: "+contactName,YesNo.Yes);
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
		log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
	}
	
	 tier ="3";
	if(lp.clickOnTab(contactName, mode, TabName.ContactTab)){
		log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);
		if(lp.clickOnAlreadyCreatedItem(projectName, TabName.ContactTab, contactName, 30)){
			log(LogStatus.INFO,"click on Created Contact : "+contactName,YesNo.No);	
			ThreadSleep(3000);
			
			if(cp.UpdateContactTier(projectName, PageName.ContactPage, tier)){
				
				log(LogStatus.INFO,"Contact tier updated as: "+tier,YesNo.No);	
				refresh(driver);		
				ThreadSleep(5000);
				String lastTouchPoint= cp.getlastTouchPointValue(projectName, 30).getText();
				if(lastTouchPoint.contains("")||lastTouchPoint.equalsIgnoreCase("")){
					log(LogStatus.INFO,"Last touch point value is matched As blank in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Last touch point value is not matched As blank in contact :"+contactName);
					log(LogStatus.SKIP,"Last touch point value is not matched As blank in contact :"+contactName,YesNo.Yes);
				}
				
				int days=180;
				String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
				String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "M/d/yyyy", "America/Los_Angles");
				if(cp.verifyDate(expectedDate, actualDate)){
					log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
					log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
				}
			}else{
				sa.assertTrue(false,"Not Able to Update Contact  tier: "+contactName);
				log(LogStatus.SKIP,"Not Able to Update Contact  tier: "+contactName,YesNo.Yes);
			}
			
		}else{
			sa.assertTrue(false,"Not Able to click on Create Contact : "+contactName);
			log(LogStatus.SKIP,"Not Able to click on created Contact: "+contactName,YesNo.Yes);
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
		log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
	}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({"projectName"})
	@Test
	public void SmokeTc034_CreateEventAndVerifyNextTouchPointAtContactPage(String projectName) {
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String contactName=SmokeNTPContact1FName+" "+SmokeNTPContact1LName;
		
		String task = SmokeNTPEvent1Subject;
		
		if(lp.clickOnTab(contactName, mode, TabName.ContactTab)){
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);
			if(lp.clickOnAlreadyCreatedItem(projectName, TabName.ContactTab, contactName, 30)){
				log(LogStatus.INFO,"click on Created Contact : "+contactName,YesNo.No);	
				ThreadSleep(3000);
				
				SmokeNTPEvent1StartDate=previousOrForwardDateAccordingToTimeZone(-1, "M/d/YYYY", BasePageBusinessLayer.AmericaLosAngelesTimeZone);
				SmokeNTPEvent1EndDate=previousOrForwardDateAccordingToTimeZone(3, "M/d/YYYY", BasePageBusinessLayer.AmericaLosAngelesTimeZone);;
				String[][] event1 = {{PageLabel.Subject.toString(),task},
						{PageLabel.Name.toString(),contactName},
						{PageLabel.Start_Date.toString(),SmokeNTPEvent1StartDate},
						{PageLabel.End_Date.toString(),SmokeNTPEvent1EndDate}
						};

				if (gp.clickOnGlobalActionAndEnterValue(projectName, GlobalActionItem.New_Event, event1)) {
					log(LogStatus.INFO,"Able to Enter Value for : "+task,YesNo.No);
					ExcelUtils.writeData(phase1DataSheetFilePath,SmokeNTPEvent1StartDate, "Events", excelLabel.Variable_Name, "SmokeNTPEvent1", excelLabel.Start_Date);
					ExcelUtils.writeData(phase1DataSheetFilePath,SmokeNTPEvent1EndDate, "Events", excelLabel.Variable_Name, "SmokeNTPEvent1", excelLabel.End_Date);

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
				
				refresh(driver);
				ThreadSleep(5000);
				String value= cp.getlastTouchPointValue(projectName, 30).getText();
//				if(cp.verifyDate(SmokeNTPEvent1EndDate, value)){
					if(bp.verifyDate(SmokeNTPEvent1EndDate, null, "M/d/yyyy")){
					log(LogStatus.INFO,"Last touch point value is matched in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Last touch point value is not matched in contact :"+contactName);
					log(LogStatus.SKIP,"Last touch point value is not matched in contact :"+contactName,YesNo.Yes);
				}
				
				int days=183;
				String actualDate= cp.getNextTouchPointDateValue(projectName, 30).getText();
				String expectedDate =previousOrForwardDateAccordingToTimeZone(days, "M/d/yyyy", "America/Los_Angles");
				if(cp.verifyDate(expectedDate, actualDate)){
					log(LogStatus.INFO,"Next touch point value is matched As after "+days+" days from created date  in :"+contactName,YesNo.No);	

				}else{
					sa.assertTrue(false,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName);
					log(LogStatus.SKIP,"Next touch point value is not matched As after "+days+" days from created date  in contact :"+contactName,YesNo.Yes);
				}
				
			}else{
				sa.assertTrue(false,"Not Able to click on Create Contact : "+contactName);
				log(LogStatus.SKIP,"Not Able to click on created Contact: "+contactName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}

		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({"projectName"})
	@Test
	public void SmokeTc035_CreateInstitutionContactAndVerifyConnectionComponent(String projectName){
		
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, tabObj1)) {
			log(LogStatus.INFO, "Click on Tab : " + tabObj1, YesNo.No);

			if (ip.createEntityOrAccount(projectName, mode, SmokeCCIns1, SmokeCCIns1RecordType, null, null, 20)) {
				log(LogStatus.INFO, "successfully Created Account/Entity : " + SmokeCCIns1 + " of record type : "
						+ SmokeCCIns1RecordType, YesNo.No);

				refresh(driver);
				ThreadSleep(5000);
				if (click(driver, ip.getRelatedTab(projectName, PageName.InstitutionsPage, RelatedTab.Connections, 30),
						"Connection related tab", action.BOOLEAN)) {
					ThreadSleep(2000);
					log(LogStatus.INFO, "Click on Related Tab : connection ", YesNo.No);
					
					RelatedTab[] componentTab ={RelatedTab.Recent_Moves,
							RelatedTab.All_Contacts,
							RelatedTab.Board_Members};
					for(RelatedTab tab:componentTab){

					if (click(driver,
							ip.getRelatedTab(projectName, PageName.InstitutionsPage, tab, 30),
							tab+": component tab", action.BOOLEAN)) {
						ThreadSleep(5000);
						log(LogStatus.INFO, "Click on component Tab :" + tab, YesNo.No);

						WebElement ele = ip.getComponentNoDataToDisplayMessage(tab.toString(),30);
						if (ele != null) {
							log(LogStatus.INFO,
									"No data to display message verified on component tab:" +tab,
									YesNo.No);

						} else {
							sa.assertTrue(false, "No data to display message is not verified on component tab:"
									+ tab);
							log(LogStatus.SKIP, "No data to display message is not verified on component tab:"
									+ tab, YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Not Able to Click on component Tab :" + tab);
						log(LogStatus.SKIP, "Not Able to Click on component Tab : " + tab,
								YesNo.Yes);
					}
					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Related Tab :" + RelatedTab.Connections);
					log(LogStatus.SKIP, "Not Able to Click on Related Tab : " + RelatedTab.Connections, YesNo.Yes);
				}

				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+SmokeCCIns1+" of record type : "+SmokeCCIns1RecordType);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+SmokeCCIns1+" of record type : "+SmokeCCIns1RecordType,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj1);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj1,YesNo.Yes);
			}
		

		String[][] contacts ={{SmokeCCContact1FName,SmokeCCContact1LName,SmokeCCContact1Inst},
								{SmokeCCContact2FName,SmokeCCContact2LName,SmokeCCContact2Inst},
								{SmokeCCContact3FName,SmokeCCContact3LName,SmokeCCContact3Inst}};
		for(int i=0;i<contacts.length;i++){
		if (lp.clickOnTab(projectName, tabObj2)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
			if (cp.createContact(projectName, contacts[i][0], contacts[i][1], contacts[i][2], "","", null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+contacts[i][0]+" "+contacts[i][1],YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+contacts[i][0]+" "+contacts[i][1]);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+contacts[i][0]+" "+contacts[i][1],YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
		}
		}
		
		if (lp.clickOnTab(projectName, tabObj1)) {
			log(LogStatus.INFO, "Click on Tab : " + tabObj1, YesNo.No);

			if(ip.clickOnAlreadyCreatedItem(projectName, TabName.InstituitonsTab, SmokeCCIns1, 30)){
				log(LogStatus.INFO, "Able to click created institution :"+SmokeCCIns1, YesNo.No);

				if (click(driver, ip.getRelatedTab(projectName, PageName.InstitutionsPage, RelatedTab.Connections, 30),
						"Connection related tab", action.BOOLEAN)) {
					ThreadSleep(2000);
					log(LogStatus.INFO, "Click on Related Tab : connection ", YesNo.No);
					if (click(driver,
							ip.getRelatedTab(projectName, PageName.InstitutionsPage, RelatedTab.All_Contacts, 30),
							RelatedTab.All_Contacts + ": component tab", action.BOOLEAN)) {
						ThreadSleep(2000);
						log(LogStatus.INFO, "Click on component Tab :" + RelatedTab.All_Contacts, YesNo.No);

						String filesName = SmokeCCContact1FName + " " + SmokeCCContact1LName + ","
								+ SmokeCCContact2FName + " " + SmokeCCContact2LName + "," + SmokeCCContact3FName + " "
								+ SmokeCCContact3LName;
						List<WebElement> link = ip.getComponentInsAndContactNameLinkList();
						if (compareMultipleList(dDriver, filesName, link).isEmpty()) {

							log(LogStatus.INFO, "All three contact are matched and presetn in All contact componet",
									YesNo.No);

						} else {
							sa.assertTrue(false,
									"All three contact are  not matched and not presetn in All contact componet");
							log(LogStatus.SKIP,
									"All three contact are  not matched and not presetn in All contact componet",
									YesNo.Yes);
						}
					} else {
						sa.assertTrue(false, "Not Able to Click on component Tab :" + RelatedTab.All_Contacts);
						log(LogStatus.SKIP, "Not Able to Click on component Tab : " + RelatedTab.All_Contacts,
								YesNo.Yes);
					}
					RelatedTab[] componentTab ={RelatedTab.Recent_Moves,RelatedTab.Board_Members};
					
					for(RelatedTab tab:componentTab){

					if (click(driver,
							ip.getRelatedTab(projectName, PageName.InstitutionsPage, tab, 30),
							tab+": component tab", action.BOOLEAN)) {
						ThreadSleep(5000);
						log(LogStatus.INFO, "Click on component Tab :" + tab, YesNo.No);

						WebElement ele = ip.getComponentNoDataToDisplayMessage(tab.toString(),30);
						if (ele != null) {
							log(LogStatus.INFO,
									"No data to display message verified on component tab:" +tab,
									YesNo.No);

						} else {
							sa.assertTrue(false, "No data to display message is not verified on component tab:"
									+ tab);
							log(LogStatus.SKIP, "No data to display message is not verified on component tab:"
									+ tab, YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Not Able to Click on component Tab :" + tab);
						log(LogStatus.SKIP, "Not Able to Click on component Tab : " + tab,
								YesNo.Yes);
					}
					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Related Tab :" + RelatedTab.Connections);
					log(LogStatus.SKIP, "Not Able to Click on Related Tab : " + RelatedTab.Connections, YesNo.Yes);
				}

			}else{
				sa.assertTrue(false,"Able to click created institution :"+SmokeCCIns1);
				log(LogStatus.SKIP,"Able to click created institution :"+SmokeCCIns1,YesNo.Yes);
			}
		}else{
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj1);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj1,YesNo.Yes);
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({"projectName"})
	@Test
	public void SmokeTc036_CreateAffiliationRoleAndVerifyConnectionComponent(String projectName){
		
		LoginPageBusinessLayer lp=new LoginPageBusinessLayer(driver);
		AffiliationPageBusinessLayer af= new AffiliationPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		String[][][] labelWithValue = {{{excelLabel.Firm.toString(),SmokeCCIns1},{excelLabel.Contact.toString(),SmokeCCContact1FName+" "+SmokeCCContact1LName},{excelLabel.Role.toString(),"Board Member"}},
				{{excelLabel.Firm.toString(),SmokeCCIns1},{excelLabel.Contact.toString(),SmokeCCContact2FName+" "+SmokeCCContact2LName},{excelLabel.Role.toString(),"Former Employee"}},
				{{excelLabel.Firm.toString(),SmokeCCIns1},{excelLabel.Contact.toString(),SmokeCCContact3FName+" "+SmokeCCContact3LName},{excelLabel.Role.toString(),"Former Employee"}}};
		
		for (String[][] labeAndValue : labelWithValue) {

			if (af.createAffiliationItem(projectName, labeAndValue, 10)) {
				log(LogStatus.INFO, "Able to create Affiliation", YesNo.No);
			} else {
				log(LogStatus.FAIL, "Not Able to create Affiliation:" + TabName.Affiliations, YesNo.Yes);
				sa.assertTrue(false, "Not Able to create Affiliation:" + TabName.Affiliations);
			}
		}
		
		if (lp.clickOnTab(projectName, tabObj1)) {
			log(LogStatus.INFO, "Click on Tab : " + tabObj1, YesNo.No);

			if(af.clickOnAlreadyCreatedItem(projectName, TabName.InstituitonsTab, SmokeCCIns1, 30)){
				log(LogStatus.INFO, "Able to click created institution :"+SmokeCCIns1, YesNo.No);

				if (click(driver, af.getRelatedTab(projectName, PageName.InstitutionsPage, RelatedTab.Connections, 30),
						"Connection related tab", action.BOOLEAN)) {
					ThreadSleep(2000);
					log(LogStatus.INFO, "Click on Related Tab : connection ", YesNo.No);
					
					if (click(driver,
							af.getRelatedTab(projectName, PageName.InstitutionsPage, RelatedTab.All_Contacts, 30),
							RelatedTab.All_Contacts + ": component tab", action.BOOLEAN)) {
						ThreadSleep(5000);
						log(LogStatus.INFO, "Click on component Tab :" + RelatedTab.All_Contacts, YesNo.No);

						String contactname = SmokeCCContact1FName + " " + SmokeCCContact1LName + "," +
								SmokeCCContact2FName + " " + SmokeCCContact2LName + "," + 
								SmokeCCContact3FName + " "+ SmokeCCContact3LName;
						List<WebElement> link = af.getComponentInsAndContactNameLinkList();
						if (compareMultipleList(dDriver, contactname, link).isEmpty()) {

							log(LogStatus.INFO, contactname+":All three contact are matched and presetn in All contact componet",
									YesNo.No);

						} else {
							sa.assertTrue(false,
									contactname+":All three contact are  not matched and not presetn in All contact componet");
							log(LogStatus.SKIP,
									contactname+":All three contact are  not matched and not presetn in All contact componet",
									YesNo.Yes);
						}
					} else {
						sa.assertTrue(false, "Not Able to Click on component Tab :" + RelatedTab.All_Contacts);
						log(LogStatus.SKIP, "Not Able to Click on component Tab : " + RelatedTab.All_Contacts,
								YesNo.Yes);
					}
					
					
					if (click(driver,
							af.getRelatedTab(projectName, PageName.InstitutionsPage, RelatedTab.Board_Members, 30),
							RelatedTab.Board_Members + ": component tab", action.BOOLEAN)) {
						ThreadSleep(5000);
						log(LogStatus.INFO, "Click on component Tab :" + RelatedTab.Board_Members, YesNo.No);

						String contactname = SmokeCCContact1FName + " " + SmokeCCContact1LName ;
						List<WebElement> link = af.getComponentInsAndContactNameLinkList();
						if (compareMultipleList(dDriver, contactname, link).isEmpty()) {

							log(LogStatus.INFO, contactname+": contact are matched and presetn in Board Member componet",
									YesNo.No);

						} else {
							sa.assertTrue(false,
									contactname+": contact are not matched and presetn in Board Member componet");
							log(LogStatus.SKIP,
									contactname+": contact are not matched and presetn in Board Member componet",
									YesNo.Yes);
						}
					} else {
						sa.assertTrue(false, "Not Able to Click on component Tab :" + RelatedTab.Board_Members);
						log(LogStatus.SKIP, "Not Able to Click on component Tab : " + RelatedTab.Board_Members,
								YesNo.Yes);
					}
					
					
					if (click(driver,
							af.getRelatedTab(projectName, PageName.InstitutionsPage, RelatedTab.Recent_Moves, 30),
							RelatedTab.Recent_Moves + ": component tab", action.BOOLEAN)) {
						ThreadSleep(5000);
						log(LogStatus.INFO, "Click on component Tab :" + RelatedTab.Recent_Moves, YesNo.No);

						String contactname = SmokeCCContact2FName + " " + SmokeCCContact2LName + "," + SmokeCCContact3FName + " "+ SmokeCCContact3LName;
						
						List<WebElement> link = af.getComponentInsAndContactNameLinkList();
						if (compareMultipleList(dDriver, contactname, link).isEmpty()) {

							log(LogStatus.INFO, contactname+": contact are matched and presetn in Board Member componet",
									YesNo.No);

						} else {
							sa.assertTrue(false,
									contactname+": contact are not matched and presetn in Board Member componet");
							log(LogStatus.SKIP,
									contactname+": contact are not matched and presetn in Board Member componet",
									YesNo.Yes);
						}
					} else {
						sa.assertTrue(false, "Not Able to Click on component Tab :" + RelatedTab.Recent_Moves);
						log(LogStatus.SKIP, "Not Able to Click on component Tab : " + RelatedTab.Recent_Moves,
								YesNo.Yes);
					}
					
				} else {
					sa.assertTrue(false, "Not Able to Click on Related Tab :" + RelatedTab.Connections);
					log(LogStatus.SKIP, "Not Able to Click on Related Tab : " + RelatedTab.Connections, YesNo.Yes);
				}

			}else{
				sa.assertTrue(false,"Able to click created institution :"+SmokeCCIns1);
				log(LogStatus.SKIP,"Able to click created institution :"+SmokeCCIns1,YesNo.Yes);
			}
		}else{
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj1);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj1,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({"projectName"})
	@Test
	public void SmokeTc037_CreateTaskWithMultipleContactAndVerifyConnectionComponent(String projectName){
		
		LoginPageBusinessLayer lp=new LoginPageBusinessLayer(driver);
		AffiliationPageBusinessLayer af= new AffiliationPageBusinessLayer(driver);
		GlobalActionPageBusinessLayer gp =new GlobalActionPageBusinessLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		SmokeCCTask1dueDate=todaysDate;
		String task = SmokeCCTask1Subject;
		String[][] task1 = {{PageLabel.Subject.toString(),task},
				{PageLabel.Name.toString(),SmokeCCContact1FName+" "+SmokeCCContact1LName},
				{PageLabel.Name.toString(),SmokeCCContact2FName+" "+SmokeCCContact2LName},
				{PageLabel.Name.toString(),SmokeCCContact3FName+" "+SmokeCCContact3LName},
				{PageLabel.Related_To.toString(),SmokeCCIns1},
				{PageLabel.Due_Date.toString(),SmokeCCTask1dueDate},
				{PageLabel.Priority.toString(),SmokeCCTask1Priority},
				{PageLabel.Status.toString(),SmokeCCTask1Status}};
		

		if (lp.clickOnTab(projectName, tabObj1)) {
			log(LogStatus.INFO, "Click on Tab : " + tabObj1, YesNo.No);

			if(af.clickOnAlreadyCreatedItem(projectName, TabName.InstituitonsTab, SmokeCCIns1, 30)){
				log(LogStatus.INFO, "Able to click created institution :"+SmokeCCIns1, YesNo.No);

				
				if (gp.clickOnGlobalActionAndEnterValue(projectName, GlobalActionItem.New_Task, task1)) {
					log(LogStatus.INFO,"Able to Enter Value for : "+task,YesNo.No);
					ExcelUtils.writeData(phase1DataSheetFilePath,SmokeCTTask1dueDate, "Task1", excelLabel.Variable_Name, "SmokeCTTask1", excelLabel.Due_Date);
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
				refresh(driver);
				
				ThreadSleep(5000);
				if (click(driver, af.getRelatedTab(projectName, PageName.InstitutionsPage, RelatedTab.Connections, 30),
						"Connection related tab", action.BOOLEAN)) {
					ThreadSleep(2000);
					log(LogStatus.INFO, "Click on Related Tab : connection ", YesNo.No);
					
					if (click(driver,
							af.getRelatedTab(projectName, PageName.InstitutionsPage, RelatedTab.All_Contacts, 30),
							RelatedTab.All_Contacts + ": component tab", action.BOOLEAN)) {
						ThreadSleep(2000);
						log(LogStatus.INFO, "Click on component Tab :" + RelatedTab.All_Contacts, YesNo.No);

						String contactname = SmokeCCContact1FName + " " + SmokeCCContact1LName + "," +
								SmokeCCContact2FName + " " + SmokeCCContact2LName + "," + 
								SmokeCCContact3FName + " "+ SmokeCCContact3LName;
						List<WebElement> link = af.getComponentInsAndContactNameLinkList();
						if (compareMultipleList(dDriver, contactname, link).isEmpty()) {

							log(LogStatus.INFO, contactname+":All three contact are matched and presetn in All contact componet",
									YesNo.No);

						} else {
							sa.assertTrue(false,
									contactname+":All three contact are  not matched and not presetn in All contact componet");
							log(LogStatus.SKIP,
									contactname+":All three contact are  not matched and not presetn in All contact componet",
									YesNo.Yes);
						}
						
						String linkCount=af.getHyperLinkAtConnectionComponent(SmokeCCContact1FName + " " + SmokeCCContact1LName).getText();
						String linkCount2=af.getHyperLinkAtConnectionComponent(SmokeCCContact2FName + " " + SmokeCCContact2LName).getText();
						String linkCount3=af.getHyperLinkAtConnectionComponent(SmokeCCContact3FName + " " + SmokeCCContact3LName).getText();

						if(linkCount.contains("1")&& linkCount2.contains("1")&&linkCount3.contains("1")){
							
							log(LogStatus.INFO, "Hyper link with 1 contact verify in connection component",
									YesNo.No);
						}else{
							sa.assertTrue(false,
									"Hyper link with 1 contact is not verify in connection component");
							log(LogStatus.SKIP,
									"Hyper link with 1 contact is not verify in connection component",
									YesNo.Yes);
						}
					} else {
						sa.assertTrue(false, "Not Able to Click on component Tab :" + RelatedTab.All_Contacts);
						log(LogStatus.SKIP, "Not Able to Click on component Tab : " + RelatedTab.All_Contacts,
								YesNo.Yes);
					}
					
					
					if (click(driver,
							af.getRelatedTab(projectName, PageName.InstitutionsPage, RelatedTab.Board_Members, 30),
							RelatedTab.Board_Members + ": component tab", action.BOOLEAN)) {
						ThreadSleep(2000);
						log(LogStatus.INFO, "Click on component Tab :" + RelatedTab.Board_Members, YesNo.No);

						String contactname = SmokeCCContact1FName + " " + SmokeCCContact1LName ;
						List<WebElement> link = af.getComponentInsAndContactNameLinkList();
						if (compareMultipleList(dDriver, contactname, link).isEmpty()) {

							log(LogStatus.INFO, contactname+": contact are matched and presetn in Board Member componet",
									YesNo.No);

						} else {
							sa.assertTrue(false,
									contactname+": contact are matched and presetn in Board Member componet");
							log(LogStatus.SKIP,
									contactname+": contact are matched and presetn in Board Member componet",
									YesNo.Yes);
						}
					} else {
						sa.assertTrue(false, "Not Able to Click on component Tab :" + RelatedTab.Board_Members);
						log(LogStatus.SKIP, "Not Able to Click on component Tab : " + RelatedTab.Board_Members,
								YesNo.Yes);
					}
					String linkCount=af.getHyperLinkAtConnectionComponent(SmokeCCContact1FName + " " + SmokeCCContact1LName).getText();
					
					if(linkCount.contains("1")){
						
						log(LogStatus.INFO, "Hyper link with 1 contact verify in connection component",
								YesNo.No);
					}else{
						sa.assertTrue(false,
								"Hyper link with 1 contact is not verify in connection component");
						log(LogStatus.SKIP,
								"Hyper link with 1 contact is not verify in connection component",
								YesNo.Yes);
					}
					
					if (click(driver,
							af.getRelatedTab(projectName, PageName.InstitutionsPage, RelatedTab.Recent_Moves, 30),
							RelatedTab.Recent_Moves + ": component tab", action.BOOLEAN)) {
						ThreadSleep(2000);
						log(LogStatus.INFO, "Click on component Tab :" + RelatedTab.Recent_Moves, YesNo.No);

						String contactname = SmokeCCContact2FName + " " + SmokeCCContact2LName + "," + SmokeCCContact3FName + " "+ SmokeCCContact3LName;
						
						List<WebElement> link = af.getComponentInsAndContactNameLinkList();
						if (compareMultipleList(dDriver, contactname, link).isEmpty()) {

							log(LogStatus.INFO, contactname+": contact are matched and presetn in Board Member componet",
									YesNo.No);

						} else {
							sa.assertTrue(false,
									contactname+": contact are matched and presetn in Board Member componet");
							log(LogStatus.SKIP,
									contactname+": contact are matched and presetn in Board Member componet",
									YesNo.Yes);
						}
						
						String linkCount2=af.getHyperLinkAtConnectionComponent(SmokeCCContact2FName + " " + SmokeCCContact2LName).getText();
						String linkCount3=af.getHyperLinkAtConnectionComponent(SmokeCCContact3FName + " " + SmokeCCContact3LName).getText();

						if( linkCount2.contains("1")&&linkCount3.contains("1")){
							
							log(LogStatus.INFO, "Hyper link with 1 contact verify in connection component",
									YesNo.No);
						}else{
							sa.assertTrue(false,
									"Hyper link with 1 contact is not verify in connection component");
							log(LogStatus.SKIP,
									"Hyper link with 1 contact is not verify in connection component",
									YesNo.Yes);
						}
					} else {
						sa.assertTrue(false, "Not Able to Click on component Tab :" + RelatedTab.Recent_Moves);
						log(LogStatus.SKIP, "Not Able to Click on component Tab : " + RelatedTab.Recent_Moves,
								YesNo.Yes);
					}
					
				} else {
					sa.assertTrue(false, "Not Able to Click on Related Tab :" + RelatedTab.Connections);
					log(LogStatus.SKIP, "Not Able to Click on Related Tab : " + RelatedTab.Connections, YesNo.Yes);
				}

			}else{
				sa.assertTrue(false,"Able to click created institution :"+SmokeCCIns1);
				log(LogStatus.SKIP,"Able to click created institution :"+SmokeCCIns1,YesNo.Yes);
			}
		}else{
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj1);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj1,YesNo.Yes);
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void SmokeTc038_CreateAInstituionForFieldSet(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String value=SmokeFSIns1;
		String type=SmokeFSIns1RecordType;
		
			if (lp.clickOnTab(projectName, tabObj1)) {
				log(LogStatus.INFO,"Click on Tab : "+tabObj1,YesNo.No);	
				
				if (ip.createEntityOrAccount(projectName,"", value, type,null, null, 20)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj1);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj1,YesNo.Yes);
			}
		


		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();

	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc039_1_CreateFieldSet(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);

		String parentWindow = null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create Field Set Component");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create Field Set Component",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create Field Set Component");
			}
			ThreadSleep(3000);
			object object1 = object.Firm;
			if(setup.createFieldSetComponent(object1, ObjectFeatureName.FieldSets, FS_FieldSetLabel6, FS_NameSpacePrefix6, FS_FieldsName6)) {
				log(LogStatus.PASS, "Field Set Component is created for : "+FS_FieldSetLabel6, YesNo.No);
			}else {
				log(LogStatus.ERROR,"Field Set Component is not created for : "+FS_FieldSetLabel6, YesNo.Yes);
				sa.assertTrue(false, "Field Set Component is not created for : "+FS_FieldSetLabel6);
			}
			
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		}else {
			log(LogStatus.ERROR, "Not able to click on setup link so cannot create Field Set Component", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link so cannot create Field Set Component");
		}
		
		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
			if(ip.clickOnAlreadyCreatedItem(projectName, SmokeFSIns1, 30)) {
				log(LogStatus.INFO,"clicked on created institution : "+SmokeFSIns1, YesNo.No);
				ThreadSleep(5000);
				if(edit.clickOnEditPageLink()) {
					if(edit.dragAndDropLayOutFromEditPage(projectName, PageName.Object1Page, RelatedTab.Details, "Navatar Fieldset",FS_FieldSetLabel6,"Profile_Image__c")) {
						log(LogStatus.INFO, "Field set component is added on institution page :"+SmokeFSIns1, YesNo.No);
					}else {
						log(LogStatus.ERROR, "Field set component is not added on institution page :"+SmokeFSIns1, YesNo.Yes);
						sa.assertTrue(false, "Field set component is not added on institution page :"+SmokeFSIns1);
					}
					
				}else {
					log(LogStatus.ERROR, "Not able to click on edit page so cannot add field set component on institution page : "+SmokeFSIns1, YesNo.Yes);
					sa.assertTrue(false, "Not able to click on edit page so cannot add field set component on institution page : "+SmokeFSIns1);
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on created institution "+SmokeFSIns1+" so cannot drag and drop Advanced field set component", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on created institution "+SmokeFSIns1+" so cannot drag and drop Advanced field set component");
			}
		} else {
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab+" so cannot drag and drop Advanced field set component",YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab+" so cannot drag and drop Advanced field set component");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void SmokeTc039_2_VerifyFieldSetLayoutOnInstitutionPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
			if(ip.clickOnAlreadyCreatedItem(projectName, SmokeFSIns1, 30)) {
				log(LogStatus.INFO,"clicked on created institution : "+SmokeFSIns1, YesNo.No);
				ThreadSleep(5000);
				String[] ss = FS_FieldsName6.split("<break>");
				
				for(String field:ss) {
					if(ip.verifyFieldSetComponent(field,"")) {
						log(LogStatus.PASS, field+" is verified : "+"", YesNo.No);
					}else {
						log(LogStatus.ERROR, field+" is not verified : "+"", YesNo.Yes);
						sa.assertTrue(false, field+" is not verified : "+"");
					}
					
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on created institution "+SmokeFSIns1+" so cannot verify Advanced field set component", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on created institution "+SmokeFSIns1+" so cannot verify Advanced field set component");
			}
		} else {
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab+" so cannot verify Advanced field set component",YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab+" so cannot verify Advanced field set component");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void SmokeTc040_VerifyCameraIconUploadImageAndRelativePath(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String imagepath="\\UploadImages\\JPG1Image.jpg";
		
			if (lp.clickOnTab(projectName,TabName.InstituitonsTab )) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.InstituitonsTab,YesNo.No);
				if(lp.clickOnAlreadyCreatedItem(projectName,TabName.InstituitonsTab ,SmokeFSIns1,20)) {
					log(LogStatus.INFO,"clicked on created item : "+SmokeFSIns1, YesNo.No);
					ThreadSleep(3000);
					if(lp.updatePhoto(projectName, TabName.InstituitonsTab.toString(), imagepath,false)) {
						log(LogStatus.PASS, "photo is updated successfully in "+SmokeFSIns1+" on "+TabName.InstituitonsTab, YesNo.No);
						ThreadSleep(3000);
						
						String imageRelativepath =lp.getUploadedImageRelativeXpath(10).getAttribute("src").toString();
						ThreadSleep(2000);
						if (imageRelativepath!=null) {
							log(LogStatus.PASS, "Photo is updated and verified successfully", YesNo.No);
							ThreadSleep(3000);
							
						}else {
							log(LogStatus.SKIP,"Photo is not updated ",YesNo.Yes);
							sa.assertTrue(false, "Photo is not updated ");
						}
					}else {
						log(LogStatus.PASS, "photo is not updated in "+SmokeFSIns1+" on "+TabName.InstituitonsTab, YesNo.No);
						sa.assertTrue(false, "photo is not updated in "+SmokeFSIns1+" on "+TabName.InstituitonsTab);
					}
					
				// going to delete photo
					if (cp.deleteImage(projectName, SmokeFSIns1)) {
						log(LogStatus.INFO, "successfully deleted photo", YesNo.No);

						WebElement ele =lp.getUploadedImageRelativeXpath(10);
						ThreadSleep(2000);
						if (ele==null) {
							log(LogStatus.PASS, "Photo is deleted and verified successfully", YesNo.No);
							ThreadSleep(3000);
							
						}else {
							log(LogStatus.SKIP,"Photo is not deleted ",YesNo.Yes);
							sa.assertTrue(false, "Photo is not deleted ");
						}
					}else {
						log(LogStatus.ERROR, "could not deleted photo", YesNo.Yes);
						sa.assertTrue(false,"could not deleted photo" );
					}
					
					// going to update photo
					if(lp.updatePhoto(projectName, TabName.InstituitonsTab.toString(), imagepath,false)) {
						log(LogStatus.PASS, "photo is updated successfully in "+SmokeFSIns1+" on "+TabName.InstituitonsTab, YesNo.No);
						ThreadSleep(3000);
						
						String imageRelativepath =lp.getUploadedImageRelativeXpath(10).getAttribute("src").toString();
						ThreadSleep(2000);
						if (imageRelativepath!=null) {
							log(LogStatus.PASS, "Photo is updated and verified successfully", YesNo.No);
							ThreadSleep(3000);
							
						}else {
							log(LogStatus.SKIP,"Photo is not updated ",YesNo.Yes);
							sa.assertTrue(false, "Photo is not updated ");
						}
					}else {
						log(LogStatus.PASS, "photo is not updated in "+SmokeFSIns1+" on "+TabName.InstituitonsTab, YesNo.No);
						sa.assertTrue(false, "photo is not updated in "+SmokeFSIns1+" on "+TabName.InstituitonsTab);
					}
					
					
				}else {
					log(LogStatus.ERROR, "Not able to click on created item "+SmokeFSIns1+" so cannot verify medium image size", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on created item "+SmokeFSIns1+" so cannot verify medium image size");
				}
			} else {
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.InstituitonsTab+" so cannot verify medium image size",YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.InstituitonsTab+" so cannot verify medium image size");
			}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void SmokeTc041_CreateToggleButtonAtInstitutionContactTab(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
	
		String sdgConfigDataProviderTextBox = "";
		String defaultSdgToggle = "";
		String itemValue;
		String relatedTab;

		String[] toggles=null;
		String toggleButton ;
		
		String dropImageLocation = ".\\AutoIT\\EditPage\\Contact.PNG";
		
		
		String NavatarSDGToggleImg = ".\\AutoIT\\EditPage\\NavatarSDGToggle.PNG";
		String sValue = EditPageErrorMessage.NavatarSDGToggles;
		
		WebElement ele;
		
		
			if (lp.clickOnTab(projectName, TabName.InstituitonsTab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.InstituitonsTab,YesNo.No);
				itemValue = SmokeFSIns1;
				if (ip.clickOnAlreadyCreatedItem(projectName, TabName.InstituitonsTab,itemValue, 15)) {
					log(LogStatus.INFO,"Item found: "+itemValue, YesNo.Yes);
					ThreadSleep(2000);
					if (hp.clickOnEditPageLinkOnSetUpLink()) {
						log(LogStatus.INFO,"click on Edit Page SetUp Link", YesNo.No);
						ThreadSleep(10000);	
						//// scn.nextLine();
						//switchToDefaultContent(driver);
						////////////////////////new////////////////////////////////////////
						relatedTab=SmokeToggleCheck1RelatedTab;
						 toggles = SmokeToggleCheck1ToggleButtons.split(breakSP);
						switchToFrame(driver, 30, edit.getEditPageFrame(projectName,120));
						ThreadSleep(5000);
						 ele = FindElement(driver, "//a[@title='"+relatedTab+"']", "sub tab", action.BOOLEAN, 30);
						if (clickUsingJavaScript(driver, ele, relatedTab.toString(), action.BOOLEAN)) {
							log(LogStatus.INFO,"Click on Sub Tab : "+relatedTab,YesNo.No);
							ThreadSleep(2000);
							switchToDefaultContent(driver);
							if (sendKeys(driver, edit.getEditPageSeachTextBox(projectName, 180),sValue,"Search TextBox",action.BOOLEAN)) {
								ThreadSleep(20000);
								log(LogStatus.INFO,"send value to Search TextBox : "+sValue,YesNo.No);
								ThreadSleep(10000);
								if (edit.dragNDropUsingScreen(projectName, NavatarSDGToggleImg, dropImageLocation, 20)) {
									log(LogStatus.INFO,"Able to DragNDrop : "+sValue,YesNo.No);
									ThreadSleep(2000);
									
										sdgConfigDataProviderTextBox = toggles[0]+":"+EditPageErrorMessage.EntityContactListSDG+","+toggles[1]+":"+EditPageErrorMessage.EntityAffiliationAllRolesListSDG;
										defaultSdgToggle=EditPageErrorMessage.EntityAffiliationAllRolesListSDG;
									
									
									if (sendKeysWithoutClearingTextBox(driver, edit.getsdgConfigDataProviderTextBox(projectName, 30),sdgConfigDataProviderTextBox,"sdg Config Data Provider TextBox : "+sdgConfigDataProviderTextBox,action.BOOLEAN)) {
										ThreadSleep(500);
										log(LogStatus.INFO,"send value to sdg Config Data Provider TextBox : "+sdgConfigDataProviderTextBox,YesNo.No);

										if (sendKeys(driver, edit.getDefaultSDGToggleTextBox(projectName, 10),defaultSdgToggle,"Default SDG Toggle TextBox : "+defaultSdgToggle,action.BOOLEAN)) {
											ThreadSleep(200);
											log(LogStatus.INFO,"send value to Default SDG Toggle TextBox : "+defaultSdgToggle,YesNo.No);
											if (click(driver, edit.getEditPageSaveButton(projectName, 10),"Edit Page Save Button", action.BOOLEAN)) {
												log(LogStatus.INFO,"Click on Edit Page Save Button",YesNo.No);
												ThreadSleep(10000);
											} else {
												sa.assertTrue(false, "Not Able to Click on Edit Page Save Button");
												log(LogStatus.SKIP,"Not Able to Click on Edit Page Save Button",YesNo.Yes);
											}

										} else {
											sa.assertTrue(false, "Not Able to send value to Default SDG Toggle TextBox : "+defaultSdgToggle);
											log(LogStatus.SKIP,"Not Able to send value to Default SDG Toggle TextBox : "+defaultSdgToggle,YesNo.Yes);
										}

									} else {
										sa.assertTrue(false, "Not Able to send value to sdg Config Data Provider TextBox : "+sdgConfigDataProviderTextBox);
										log(LogStatus.FAIL,"Not Able to send value to sdg Config Data Provider TextBox : "+sdgConfigDataProviderTextBox,YesNo.Yes);
									}

								} else {
									sa.assertTrue(false, "Not Able to DragNDrop : "+sValue);
									log(LogStatus.FAIL,"Not Able to DragNDrop : "+sValue,YesNo.Yes);
								}

							} else {
								sa.assertTrue(false, "Not Able to send value to Search TextBox : "+sValue);
								log(LogStatus.FAIL,"Not Able to send value to Search TextBox : "+sValue,YesNo.Yes);
							}
							ThreadSleep(2000);
							switchToDefaultContent(driver);
							ThreadSleep(2000);
							switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
							System.err.println(">>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
							// scn.nextLine();
							ThreadSleep(10000);
								for (int j = 0; j < toggles.length; j++) {
									toggleButton=toggles[j];
									ele=ip.toggleSDGButtons(projectName, toggleButton,ToggleButtonGroup.SDGButton, action.BOOLEAN, true, 30);
									if (clickUsingJavaScript(driver, ele, toggleButton+" SDG", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.PASS,"Click on "+toggleButton,YesNo.No);
										ThreadSleep(200);
										switchToDefaultContent(driver);
										if (click(driver, edit.getEnableToggleCheckBox(projectName, 120), "Enable Toggle CheckBox", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO,"click on Enable Toggle CheckBox for : "+toggleButton,YesNo.No);
											ThreadSleep(200);

											if (click(driver, edit.getEditPageSaveButton(projectName, 10),"Edit Page Save Button", action.BOOLEAN)) {
												log(LogStatus.INFO,"Click on Edit Page Save Button",YesNo.No);
												ThreadSleep(10000);
												
											} else {
												sa.assertTrue(false, "Not Able to Click on Edit Page Save Button");
												log(LogStatus.FAIL,"Not Able to Click on Edit Page Save Button",YesNo.Yes);
											}

										} else {
											sa.assertTrue(false, "Not Able to click on Enable Toggle CheckBox for : "+toggleButton);
											log(LogStatus.FAIL,"Not Able to click on Enable Toggle CheckBox for : "+toggleButton,YesNo.Yes);

										}

									}else{
										sa.assertTrue(false, "Not Able to Click on "+toggleButton);
										log(LogStatus.FAIL,"Not Able to Click on "+toggleButton,YesNo.Yes);
									}
									switchToDefaultContent(driver);
									switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
								}
								switchToDefaultContent(driver);
								switchToFrame(driver, 30, edit.getEditPageFrame(projectName,120));
								toggleButton = toggles[0];
								ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 30);
								if (ele!=null) {
									log(LogStatus.INFO,"Toggle is present : "+toggleButton,YesNo.No);
									ThreadSleep(2000);
								} else {
									sa.assertTrue(false,"Toggle should be present : "+toggleButton);
									log(LogStatus.SKIP,"Toggle should be present : "+toggleButton,YesNo.Yes);
								}
								
								toggleButton = toggles[1];
								ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 30);
								if (ele!=null) {
									log(LogStatus.INFO,"Toggle is present : "+toggleButton,YesNo.No);
									ThreadSleep(2000);
								} else {
									sa.assertTrue(false,"Toggle should be present : "+toggleButton);
									log(LogStatus.SKIP,"Toggle should be present : "+toggleButton,YesNo.Yes);
								}
								
								switchToDefaultContent(driver);
								if (clickUsingJavaScript(driver, edit.getEditPageBackButton(projectName, 10),"Edit Page Back Button", action.BOOLEAN)) {
									log(LogStatus.INFO,"Click on Edit Page Back Button",YesNo.No);
									//// scn.nextLine();
									ThreadSleep(5000);
									refresh(driver);
									ThreadSleep(5000);
									if (click(driver, ip.getRelatedTab(projectName,relatedTab, 30), relatedTab, action.BOOLEAN)) {
										log(LogStatus.INFO,"Click on Sub Tab : "+relatedTab,YesNo.No);
										ThreadSleep(10000);

										toggleButton = toggles[1];
										ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 30);
										if (ele!=null) {
											log(LogStatus.INFO,"Toggle is present : "+toggleButton,YesNo.No);
											ThreadSleep(2000);
										} else {
											sa.assertTrue(false,"Toggle should be present : "+toggleButton);
											log(LogStatus.SKIP,"Toggle should be present : "+toggleButton,YesNo.Yes);
										}
										
										if (ip.toggleSDGButtons(projectName, toggleButton,ToggleButtonGroup.SDGButton, action.BOOLEAN, true, 30)!=null) {
											log(LogStatus.PASS,"After Save "+toggleButton+" is selected by default",YesNo.No);
										} else {
											sa.assertTrue(false,"After Save "+toggleButton+" should be selected by default");
											log(LogStatus.FAIL,"After Save "+toggleButton+" should be selected by default",YesNo.Yes);
										}
										
										toggleButton = toggles[0];
										
										ele = ip.toggleButton(projectName, toggleButton, action.BOOLEAN, 30);
										if (ele!=null) {
											log(LogStatus.INFO,"Toggle is present : "+toggleButton,YesNo.No);
											ThreadSleep(2000);
										} else {
											sa.assertTrue(false,"Toggle should be present : "+toggleButton);
											log(LogStatus.SKIP,"Toggle should be present : "+toggleButton,YesNo.Yes);
										}
										
										if (ip.toggleSDGButtons(projectName, toggleButton,ToggleButtonGroup.SDGButton, action.BOOLEAN, true, 2)==null) {
											log(LogStatus.PASS,"After Save "+toggleButton+" is not selected by default",YesNo.No);
										} else {
											sa.assertTrue(false,"After Save "+toggleButton+" should not be selected by default");
											log(LogStatus.FAIL,"After Save "+toggleButton+" should not be selected by default",YesNo.Yes);
										}
										
									
									} else {
										sa.assertTrue(false,"Not Able to Click on Sub Tab : "+relatedTab);
										log(LogStatus.SKIP,"Not Able to Click on Sub Tab : "+relatedTab,YesNo.Yes);
									}

								} else {
									sa.assertTrue(false, "Not Able to Click on Edit Page Back Button");
									log(LogStatus.SKIP,"Not Able to Click on Edit Page Back Button",YesNo.Yes);
								}
								
						} else {
							sa.assertTrue(false,"Not Able to Click on Sub Tab : "+relatedTab);
							log(LogStatus.SKIP,"Not Able to Click on Sub Tab : "+relatedTab,YesNo.Yes);
						}
						
						switchToDefaultContent(driver);
						clickUsingJavaScript(driver, edit.getEditPageBackButton(projectName, 10),"Edit Page Back Button", action.BOOLEAN);
						
					} else {
						log(LogStatus.ERROR,"Not Able to click on Edit Page SetUp Link", YesNo.Yes);
						sa.assertTrue(false,"Not Able to click on Edit Page SetUp Link");
					}
				}else {

					log(LogStatus.ERROR,"Item not found: "+itemValue, YesNo.Yes);
					sa.assertTrue(false,"Item not found: "+itemValue);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.InstituitonsTab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.InstituitonsTab,YesNo.Yes);
			}
			ThreadSleep(5000);
			refresh(driver);
			ThreadSleep(10000);
			//break;
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({"projectName"})
	@Test
	public void SmokeTc042_CreateContactAndCustomSDG(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String fields=SDGLabels.APIName.toString();String values="";
		lp.searchAndClickOnApp(SDG, 30);
			log(LogStatus.INFO,"Able to Click/Search : "+SDG+" going to create custom SDG",YesNo.No);	 
			ThreadSleep(3000);

			if (lp.clickOnTab(projectName, TabName.SDGTab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.SDGTab,YesNo.No);

				String[][] sdgLabels = {{SDGCreationLabel.SDG_Name.toString(),SmokeSdg1Name},
						{SDGCreationLabel.SDG_Tag.toString(),SmokeSdg1TagName},
						{SDGCreationLabel.sObjectName.toString(),SmokeSdg1ObjectName},{SDGCreationLabel.Parent_Field_Name.toString(),SmokeSdg1ParentName}};

				if (sdg.createCustomSDG(projectName, SmokeSdg1Name, sdgLabels, action.BOOLEAN, 20)) {
					log(LogStatus.PASS,"create/verify created SDG : "+SmokeSdg1Name,YesNo.No);
					for(int i = 0;i<5;i++) {
						String api=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "CField" + (i+1), excelLabel.APIName);
						
						values=api;
						if (sdg.addFieldOnSDG(projectName,fields,values)) {
							log(LogStatus.INFO,"Successfully added fields on "+SmokeSdg1Name,YesNo.Yes);

						}else {
							sa.assertTrue(false,"Not Able to add fields on SDG : "+SmokeSdg1Name);
							log(LogStatus.SKIP,"Not Able to add fields on SDG : "+SmokeSdg1Name,YesNo.Yes);
						}
					}
				} else {
					sa.assertTrue(false,"Not Able to create/verify created SDG : "+SmokeSdg1Name);
					log(LogStatus.SKIP,"Not Able to create/verify created SDG : "+SmokeSdg1Name,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.SDGTab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.SDGTab,YesNo.Yes);
			}

			String email=lp.generateRandomEmailId(gmailUserName);;
			
			
			if (lp.clickOnTab(projectName, tabObj2)) {
				log(LogStatus.INFO,"Click on Tab : "+tabObj2,YesNo.No);	
				ExcelUtils.writeData(phase1DataSheetFilePath, email, "Contacts", excelLabel.Variable_Name, "SMOKEACDCON1",excelLabel.Contact_EmailId);
				if (cp.createContact(projectName, SmokeACDContact1FName, SmokeACDContact1LName, SmokeACDContact1Inst, email,"", Fields.Phone.toString(), SmokeACDContact1Phone, CreationPage.ContactPage, SmokeACDContact1Title, null)) {
					log(LogStatus.INFO,"successfully Created Contact : "+SmokeACDContact1FName+" "+SmokeACDContact1LName,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Contact : "+SmokeACDContact1FName+" "+SmokeACDContact1LName);
					log(LogStatus.SKIP,"Not Able to Create Contact: "+SmokeACDContact1FName+" "+SmokeACDContact1LName,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj2,YesNo.Yes);
			}
			
			
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void  SmokeTc043_1_AddRelatedListAccordianOnEntityPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String tab=SmokeSdg1Name;
		String fieldValues[]={EditPageLabel.Title.toString()+"<break>"+tab,EditPageLabel.Query.toString()+"<break>"+ep.InstitutionSDGQuery(""),
				EditPageLabel.Number_of_Records_to_Display.toString()+"<break>6",
				EditPageLabel.SDG_Name.toString()+"<break>"+tab,EditPageLabel.Popup_Title.toString()+"<break>"+tab
		};
		String source= System.getProperty("user.dir")+"\\AutoIT\\EditPage\\RelatedListAccordion.png";
		String target= System.getProperty("user.dir")+"\\AutoIT\\EditPage\\AccordionInsSection.png";
		
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, SmokeFSIns1, 10)) {
				if (ep.clickOnEditPageLink()) {
					log(LogStatus.INFO, "successfully reached edit page", YesNo.No);
					ThreadSleep(10000);
					if (ep.dragAndDropAccordian(projectName, PageName.Object1Page, "Navatar Related List Accordion", fieldValues, source, target)) {
						log(LogStatus.INFO, "successfully added accordion on entity page", YesNo.No);
							
					}else {
						log(LogStatus.ERROR, "could not drop accordion on entity page", YesNo.Yes);
						sa.assertTrue(false,"could not drop accordion on entity page" );
					}
				}else {
					log(LogStatus.ERROR, "Not able to open edit page, so cannot add accordion", YesNo.Yes);
					sa.assertTrue(false,"Not able to open edit page, so cannot add accordion" );
				}
			}else {
				log(LogStatus.ERROR, "Not able to go to entity record "+SmokeFSIns1, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to entity record "+SmokeFSIns1 );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on entity tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on entity tab" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void SmokeTc043_2_VerifyAccordionOnDealPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
 
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String user=ExcelUtils.readData(phase1DataSheetFilePath,"Deal Team",excelLabel.Variable_Name, "M4DT1", excelLabel.Member);
		
		String fieldValue[]={PageLabel.Name.toString()+breakSP+SmokeACDContact1FName+" "+SmokeACDContact1LName,"Title"+breakSP+SmokeACDContact1Title,"Contact_EmailId"+breakSP+SmokeACDContact1EmailID,
				"Business Phone"+breakSP+SmokeACDContact1Phone};
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, SmokeFSIns1,10)) {
				
					if (ip.verifyAccordion(projectName, user, fieldValue,10)) {
						log(LogStatus.INFO, "successfully verified fields and values in accordion", YesNo.No);

					}else {
						log(LogStatus.ERROR, "could not verify fields and values in accordion", YesNo.Yes);
						sa.assertTrue(false,"could not verify fields and values in accordion" );
					}
				
			}else {
				log(LogStatus.ERROR, "Not able to go to entity record "+SmokeFSIns1, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to entity record "+SmokeFSIns1 );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on entity tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on entity tab" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}

}
	

