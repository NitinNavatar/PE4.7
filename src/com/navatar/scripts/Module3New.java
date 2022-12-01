package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;
import static com.navatar.generic.SmokeCommonVariables.crmUser1EmailID;
import static com.navatar.generic.SmokeCommonVariables.todaysDate;
import static com.navatar.pageObjects.NavigationPageBusineesLayer.navigationParentLabelWithChildAndOrder;
import static com.navatar.pageObjects.NavigationPageBusineesLayer.navigationParentLabelWithChildSorted;
import static com.navatar.pageObjects.NavigationPageBusineesLayer.navigationParentLabelWithOrder;
import static com.navatar.pageObjects.NavigationPageBusineesLayer.sortByValue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.navatar.generic.BaseLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.ActivityTimeLineItem;
import com.navatar.generic.EnumConstants.AppSetting;
import com.navatar.generic.EnumConstants.CSVLabel;
import com.navatar.generic.EnumConstants.ClickOrCheckEnableDisableCheckBox;
import com.navatar.generic.EnumConstants.CommitmentType;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.DataImportType;
import com.navatar.generic.EnumConstants.EditViewMode;
import com.navatar.generic.EnumConstants.EmailTemplateType;
import com.navatar.generic.EnumConstants.EnableDisable;
import com.navatar.generic.EnumConstants.FolderAccess;
import com.navatar.generic.EnumConstants.FundraisingContactPageTab;
import com.navatar.generic.EnumConstants.IndiviualInvestorFieldLabel;
import com.navatar.generic.EnumConstants.InstitutionPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.NavatarSetupSideMenuTab;
import com.navatar.generic.EnumConstants.NavigationMenuItems;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.navatar.generic.EnumConstants.ObjectName;
import com.navatar.generic.EnumConstants.ObjectType;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.PopUpName;
import com.navatar.generic.EnumConstants.ReportDashboardFolderType;
import com.navatar.generic.EnumConstants.ReportField;
import com.navatar.generic.EnumConstants.ReportFormatName;
import com.navatar.generic.EnumConstants.SDGCreationLabel;
import com.navatar.generic.EnumConstants.SDGLabels;
import com.navatar.generic.EnumConstants.SearchBasedOnExistingFundsOptions;
import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.TopOrBottom;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.customObjectLabel;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;
import com.navatar.generic.EnumConstants.recordTypeLabel;
import com.navatar.generic.EnumConstants.searchContactInEmailProspectGrid;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.BasePageErrorMessage;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.CustomObjPageBusinessLayer;
import com.navatar.pageObjects.DataLoaderWizardPageBusinessLayer;
import com.navatar.pageObjects.DealPageBusinessLayer;
import com.navatar.pageObjects.EmailMyTemplatesPageBusinessLayer;
import com.navatar.pageObjects.FundRaisingPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
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



public class Module3New extends BaseLib {
//	Scanner scn = new Scanner(System.in);
	String passwordResetLink = null;
	String navigationMenuName=NavigationMenuItems.New_Interactions.toString();
	public  String NavigationMenuTestData_PEExcel = System.getProperty("user.dir")+"\\UploadFiles\\Module 3\\UploadCSV\\Navigation Menu Test Data - Parent - Child - All.csv";
	public  String NavigationMenuTestData_PESheet = "asd";
	String navigationTab="Navigation";
	String dashBoardTab="Dashboards";
	String recordTypeDescription = "Description Record Type";
	String upDated="Updated";
	String customNavigationMenu = "Custom Navigation Menu";
//	Scanner scn = new Scanner(System.in);
	List<String> csvRecords = new ArrayList<String>();
	
	// Bulk Email Check
	//Commitment creation Check
	// Deal Creation Uncheck
	// Individual Investor uncheck
	// All Navigation Item should be deleted Except - Bulk Email- Bulk Fundraising - Bulk Commitments  - Call - Meeting - Task - New Deal- New Institution- New Contact
	// Fund CReation Popupoen without any rdio button i.e no record type should be selected
	// Fund and Fund of Funds record type should be deleted
	// Custom object should not present
	//Delete Navigation record type & custom navigation from Utility Items.
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc001_1_createCRMUser(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		boolean flag = false;

		String addRemoveTabName = "";
		String tab1 = "";
		if (tabObj1.equalsIgnoreCase("Entity")) {
			tab1 = "Entitie";
		} else {
			tab1 = tabObj1;
		}
		addRemoveTabName = tab1 + "s," + tabObj2 + "s," + tabObj3 + "s," + navigationTab + "," + dashBoardTab + ","
				+ "Fundraisings," + "Partnerships";
		if (lp.addTab_Lighting(addRemoveTabName, 5)) {
			log(LogStatus.INFO, "Tab added : " + addRemoveTabName, YesNo.No);
		} else {
			log(LogStatus.FAIL, "Tab not added : " + addRemoveTabName, YesNo.No);
			sa.assertTrue(false, "Tab not added : " + addRemoveTabName);
		}

		addRemoveTabName = tab1 + "s," + tabObj2 + "s," + tabObj3 + "s," + navigationTab + "," + "Fundraisings,"
				+ "Partnerships";
		String[] tabs = addRemoveTabName.split(",");
		String tab = "";
		for (int i = 0; i < tabs.length; i++) {
			tab = tabs[i];
			if (lp.clickOnTab(projectName, tab)) {
				if (lp.addAutomationAllListView(projectName, tab, 10)) {
					log(LogStatus.INFO, "list view added on " + tab, YesNo.No);
				} else {
					log(LogStatus.FAIL, "list view could not added on " + tab, YesNo.Yes);
					sa.assertTrue(false, "list view could not added on " + tab);
				}
			} else {
				log(LogStatus.FAIL, "could not click on " + tab, YesNo.Yes);
				sa.assertTrue(false, "could not click on " + tab);
			}
			i++;
			ThreadSleep(5000);
		}

		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(crmUser1LastName);
		String UserLastName = splitedUserLastName[0] + lp.generateRandomNumber();
		String emailId = lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User1",
				excelLabel.User_Last_Name);

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
					if (setup.createPEUser(crmUser1FirstName, UserLastName, emailId, crmUserLience, crmUserProfile, null)) {
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
			} catch (Exception e) { // TODO Auto-generated catch block
				log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
			}

		}
		if (flag) {

			if (!environment.equalsIgnoreCase(Environment.Sandbox.toString())) {
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
		config(ExcelUtils.readDataFromPropertyFile("Browser"));
		lp = new LoginPageBusinessLayer(driver);
		String passwordResetLink = null;
		try {
			passwordResetLink = new EmailLib().getResetPasswordLink("passwordreset",
					ExcelUtils.readDataFromPropertyFile("gmailUserName"),
					ExcelUtils.readDataFromPropertyFile("gmailPassword"));
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
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

	@Parameters({ "projectName"})
	@Test
	public void M3Tc001_2_CreatePreconditionData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		// Fund 
		String addRemoveTabName="";
		String tab1="";
		if (tabObj1.equalsIgnoreCase("Entity")){
			tab1="Entitie";
		}
		else{
			tab1=tabObj1;
		}
		addRemoveTabName=tab1+"s,"+tabObj2+"s,"+tabObj3+"s,"+navigationTab+","+dashBoardTab+","+"Fundraisings,"+"Partnerships";
		if (lp.addTab_Lighting( addRemoveTabName, 5)) {
			log(LogStatus.INFO,"Tab added : "+addRemoveTabName,YesNo.No);
		} else {
			log(LogStatus.FAIL,"Tab not added : "+addRemoveTabName,YesNo.No);
			sa.assertTrue(false, "Tab not added : "+addRemoveTabName);
		}
		
		if (lp.clickOnTab(projectName, TabName.Object3Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object3Tab,YesNo.No);	
			String[] funds = {M3Fund1,M3Fund1Type,M3Fund1Category,M3Fund1RecordType};
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
		
		// INS
		
		String value="";
		String type="";
		String[][] EntityOrAccounts = {{ M3Ins1, M3Ins1RecordType ,null} , { M3Ins10, M3Ins10RecordType ,null}};

		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
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
		
		// contact
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);	
			
			M3Contact1EmailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, M3Contact1EmailID, "Contacts", excelLabel.Variable_Name, "M3CON1",excelLabel.Contact_EmailId);

			if (cp.createContact(projectName, M3Contact1FName, M3Contact1LName, M3Ins1, M3Contact1EmailID,M3Contact1RecordType, null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+M3Contact1FName+" "+M3Contact1LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+M3Contact1FName+" "+M3Contact1LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+M3Contact1FName+" "+M3Contact1LName,YesNo.Yes);
			}


		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}
		
		// FR
		FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);
		if(bp.clickOnTab(environment,mode,TabName.FundraisingsTab)) {
			if(fr.createFundRaising(environment,mode,M3FRName1,M3Fund1,M3Ins1, null, null, null, null)){
				appLog.info("fundraising is created : "+M3FRName1);
			}else {
				appLog.error("Not able to create fundraising: "+M3FRName1);
				sa.assertTrue(false, "Not able to create fundraising: "+M3FRName1);
			}
		}else {
			appLog.error("Not able to click on fundraising tab so cannot create fundraising: "+M3FRName1);
			sa.assertTrue(false,"Not able to click on fundraising tab so cannot create fundraising: "+M3FRName1);
		}
		
		// Limited Partner
		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
			
			if (ip.createInstitution(projectName, environment, mode, M3Ins9,M3Ins9RecordType, InstitutionPageFieldLabelText.Parent_Institution.toString(),M3Ins9Parent)) {
				log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
				log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
			}
			
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
		if(bp.clickOnTab(projectName,TabName.PartnershipsTab)) {
			if(ins.createPartnership(environment,mode,M3PartnerShip1,M3Fund1)) {
				appLog.info("partnership is created: "+M3PartnerShip1);
			}else {
				appLog.error("Not able to create partnership: "+M3PartnerShip1);
				sa.assertTrue(false, "Not able to create partnership: "+M3PartnerShip1);
			}
		}else {
			appLog.error("Not able to click on partnership tab so cannot create partnership: "+M3PartnerShip1);
			sa.assertTrue(false, "Not able to click on partnership tab so cannot create partnership: "+M3PartnerShip1);
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
		
	@Parameters({ "projectName"})
	@Test
	public void M3Tc002_VerifyTheNavigationMenuItems(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String filesName = "";

		// Verification on navigation menu
	/*	navigationMenuName = NavigationMenuItems.Bulk_Actions.toString();*/
		navigationMenuName = NavigationMenuItems.Create.toString();

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

		/*navigationMenuName = NavigationMenuItems.New_Interactions.toString();*/
		navigationMenuName = NavigationMenuItems.Create.toString();
		filesName=NewInteractions_DefaultValues.Call.toString()+","+
				/*NewInteractions_DefaultValues.Meeting.toString()+","+*/
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


		navigationMenuName = NavigationMenuItems.Create.toString();

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
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc003_VerifyImpactOnGlobalActionByDoingAllAvailableLinksToGetDisableFromNavatarSetup(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavatarSetupPageBusinessLayer np = new NavatarSetupPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		NavatarSetupSideMenuTab[] navatarSetupSideMenuTab = { NavatarSetupSideMenuTab.BulkEmail,
				NavatarSetupSideMenuTab.CommitmentCreation };
		NavatarSetupSideMenuTab setupSideMenuTab = null;

		/*navigationMenuName = NavigationMenuItems.Bulk_Actions.toString();*/
		navigationMenuName = NavigationMenuItems.Create.toString();
		String[] navigationLabel = { BulkActions_DefaultValues.Bulk_Email.toString(),
				BulkActions_DefaultValues.Bulk_Commitments.toString(),
				BulkActions_DefaultValues.Bulk_Fundraising.toString() };

		boolean flag = false;

		log(LogStatus.INFO, "<<<<<< Going to Uncheck >>>>>>>", YesNo.No);
		for (int i = 0; i < navatarSetupSideMenuTab.length; i++) {
			flag = false;
			setupSideMenuTab = navatarSetupSideMenuTab[i];
			switchToDefaultContent(driver);
			flag = np.EnableOrDisableSettingOnNavatarSetUp(projectName, setupSideMenuTab, false);
			if (flag) {
				switchToDefaultContent(driver);
				refresh(driver);
				if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
					log(LogStatus.INFO, "Able to Click on " + navigationMenuName, YesNo.No);
					WebElement ele = npbl.getNavigationLabel(projectName, navigationLabel[i], action.BOOLEAN, 10);
					if (ele == null) {
						log(LogStatus.INFO, navigationLabel[i] + " is not present on " + navigationMenuName
								+ " after uncheck " + setupSideMenuTab, YesNo.No);
					} else {
						log(LogStatus.ERROR, navigationLabel[i] + " should not present on " + navigationMenuName
								+ " after uncheck " + setupSideMenuTab, YesNo.Yes);
						sa.assertTrue(false, navigationLabel[i] + " should not present on " + navigationMenuName
								+ " after uncheck " + setupSideMenuTab);
					}
					ele = npbl.getNavigationLabel(projectName, navigationLabel[2], action.BOOLEAN, 10);
					if (ele != null) {
						log(LogStatus.INFO, navigationLabel[2] + " is present on " + navigationMenuName, YesNo.No);
					} else {
						log(LogStatus.ERROR, navigationLabel[2] + " should be present on " + navigationMenuName,
								YesNo.Yes);
						sa.assertTrue(false, navigationLabel[2] + " should be present on " + navigationMenuName);

					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on " + navigationMenuName
							+ " so cannot uncheck absenece of " + navigationLabel[i], YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on " + navigationMenuName
							+ " so cannot uncheck absenece of " + navigationLabel[i]);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to disable " + setupSideMenuTab + " so cannot uncheck absenece of "
						+ navigationLabel[i] + " on " + navigationMenuName, YesNo.Yes);
				sa.assertTrue(false, "Not Able to disable " + setupSideMenuTab + " so cannot uncheck absenece of "
						+ navigationLabel[i] + " on " + navigationMenuName);
			}
			refresh(driver);
			ThreadSleep(5000);
		}
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
	
	ThreadSleep(5000);
	lp.CRMlogout();
	
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
		flag = np.EnableOrDisableSettingOnNavatarSetUp(projectName, setupSideMenuTab, false);

		//// CHeck log(LogStatus.INFO, "<<<<<< Going to check >>>>>>>", YesNo.No);
		for (int i = 0; i < navatarSetupSideMenuTab.length; i++) {
			flag = false;
			setupSideMenuTab = navatarSetupSideMenuTab[i];
			switchToDefaultContent(driver);
			flag = np.EnableOrDisableSettingOnNavatarSetUp(projectName, setupSideMenuTab, true);
			;
			if (flag) { // Verification on navigation menu
				switchToDefaultContent(driver);
				refresh(driver);
				if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
					log(LogStatus.INFO, "Able to Click on " + navigationMenuName, YesNo.No);
					WebElement ele = npbl.getNavigationLabel(projectName, navigationLabel[i], action.BOOLEAN, 10);
					if (ele != null) {
						log(LogStatus.INFO, navigationLabel[i] + " is  present on " + navigationMenuName
								+ " after check " + setupSideMenuTab, YesNo.No);

					} else {
						log(LogStatus.ERROR, navigationLabel[i] + " should be present on " + navigationMenuName
								+ " after check " + setupSideMenuTab, YesNo.Yes);
						sa.assertTrue(false, navigationLabel[i] + " should be present on " + navigationMenuName
								+ " after check " + setupSideMenuTab);

					}

					ele = npbl.getNavigationLabel(projectName, navigationLabel[2], action.BOOLEAN, 10);
					if (ele != null) {
						log(LogStatus.INFO, navigationLabel[i] + " is present on " + navigationMenuName, YesNo.No);

					} else {
						log(LogStatus.ERROR, navigationLabel[i] + " should be present on " + navigationMenuName,
								YesNo.Yes);
						sa.assertTrue(false, navigationLabel[i] + " should be present on " + navigationMenuName);

					}

					ele = npbl.getNavigationLabel(projectName, navigationLabel[2], action.BOOLEAN, 10);
					if (ele != null) {
						log(LogStatus.INFO, navigationLabel[2] + " is present on " + navigationMenuName, YesNo.No);
					} else {
						log(LogStatus.ERROR, navigationLabel[2] + " should be present on " + navigationMenuName,
								YesNo.Yes);
						sa.assertTrue(false, navigationLabel[2] + " should be present on " + navigationMenuName);

					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on " + navigationMenuName + " so cannot check presence of "
							+ navigationLabel[i], YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on " + navigationMenuName + " so cannot check presence of "
							+ navigationLabel[i]);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Enable " + setupSideMenuTab + " so cannot uncheck presence of "
						+ navigationLabel[i] + " on " + navigationMenuName, YesNo.Yes);
				sa.assertTrue(false, "Not Able to Enable " + setupSideMenuTab + " so cannot uncheck presence of "
						+ navigationLabel[i] + " on " + navigationMenuName);

			}
			refresh(driver);
			ThreadSleep(5000);
		}

		refresh(driver);
		ThreadSleep(5000);
		
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
		}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc004_VerifyTheLinkInNavigationMenuWhenEnablefromNavatarSetup(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavatarSetupPageBusinessLayer np= new NavatarSetupPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		
		NavatarSetupSideMenuTab[] navatarSetupSideMenuTab = {NavatarSetupSideMenuTab.DealCreation,NavatarSetupSideMenuTab.IndividualInvestorCreation};
		NavatarSetupSideMenuTab setupSideMenuTab=null;

	/*	navigationMenuName = NavigationMenuItems.Bulk_Actions.toString();*/
		navigationMenuName = NavigationMenuItems.Create.toString();
		String[] vfPage = {"Deal_Creation","IndividualInvestor"};
		String[] navigationLabels = {BulkActions_DefaultValues.Deal_Creation.toString(),
				BulkActions_DefaultValues.Individual_Investor_Creation.toString()};
		String[] urlLabels = {"/apex/navpeII__Deal_Creation?retURL=/lightning/page/home","/apex/navpeII__IndividualInvestor?retURL=/lightning/page/home"};

		boolean flag=false;
		//// CHeck

		log(LogStatus.INFO, "<<<<<< Going to check >>>>>>>", YesNo.No);
		for (int i = 0; i < navatarSetupSideMenuTab.length; i++) {
			flag=false;
			setupSideMenuTab=navatarSetupSideMenuTab[i];
			switchToDefaultContent(driver);
			flag=np.EnableOrDisableSettingOnNavatarSetUp(projectName, setupSideMenuTab, true);
			refresh(driver);
			switchToDefaultContent(driver);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				WebElement ele = npbl.getNavigationLabel(projectName, navigationLabels[i], action.BOOLEAN, 10);
				if (ele==null) {
					log(LogStatus.INFO, navigationLabels[i]+" is not present on "+navigationMenuName+" even after enabling "+setupSideMenuTab, YesNo.No);
				} else {
					log(LogStatus.ERROR, navigationLabels[i]+" should not present on "+navigationMenuName+" even after enabling "+setupSideMenuTab, YesNo.Yes);
					sa.assertTrue(false,navigationLabels[i]+" should not present on "+navigationMenuName+"even after enabling "+setupSideMenuTab);

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot check absenece of "+navigationLabels[i], YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot check absenece of "+navigationLabels[i]);
			}
			
		}

		// create navigation item 
//		scn.next();
		String secondID;
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String navigationLabel=CSVLabel.Navigation_Label.toString();
		String orderLabel=CSVLabel.Order.toString();
		String urlLabel = CSVLabel.URL.toString();
		String navigationTypeLabel = CSVLabel.Navigation_Type.toString();
		
		for (int i = 0; i < navigationLabels.length; i++) {
			String navigationLabelValue=navigationLabels[i];
			String orderLabelValue=String.valueOf(i+4);
			String urlLabelValue=urlLabels[i];
			String navigationTypeLabelValue=navigationMenuName;
			
			String[][] labelWithValue= {{navigationLabel,navigationLabelValue},{orderLabel,orderLabelValue},
					{urlLabel,urlLabelValue},{navigationTypeLabel,navigationTypeLabelValue}};
			
			if (npbl.createNavigationItem(projectName, labelWithValue, 20)) {
				log(LogStatus.INFO, "created "+navigationLabelValue, YesNo.No);
				switchToDefaultContent(driver);
				refresh(driver);
				if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
					log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
					WebElement ele = npbl.getNavigationLabel(projectName, navigationLabels[i], action.BOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO, navigationLabels[i]+" is  present on "+navigationMenuName+" after creation", YesNo.No);

					} else {
						log(LogStatus.ERROR, navigationLabels[i]+" should be present on "+navigationMenuName+" after creation", YesNo.Yes);
						sa.assertTrue(false,navigationLabels[i]+" should be present on "+navigationMenuName+" after creation");

					}
					
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot check presence of "+navigationLabels[i]+" after creation", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot check presence of "+navigationLabels[i]+" after creation");
				}
				

			} else {
				log(LogStatus.ERROR, "Not Able to create "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false, "Not Able to create "+navigationLabelValue);

			}
		}
		for (int i = 0; i < navigationLabels.length; i++) {
			String urlLabelValue=urlLabels[i];
			flag=false;
			 if (hp.clickOnSetUpLink()) {
				 String parentID = switchOnWindow(driver);
				 if (parentID!=null) {
					 secondID=driver.getWindowHandle();
					 log(LogStatus.INFO, "Able to switch on new window, so going to update Navigation Label", YesNo.No);
					 if (sendKeys(driver, setup.getQucikSearchInSetupPage(120), "Visualforce Pages", "quick search", action.BOOLEAN)) {
						 ThreadSleep(3000);
						 if (click(driver, setup.getvisualForcePagesLink(120), "visual force link", action.BOOLEAN)) {
							 switchToFrame(driver,60, setup.getFrame(PageName.VisualForcePage, 120));
							 WebElement ele = setup.VFPagePreviewLink(projectName,vfPage[i]);
							 if (click(driver, ele, "preview link for bulk email", action.SCROLLANDBOOLEAN)) {
								 for (String handle:driver.getWindowHandles()) {
									 if(!handle.equals(secondID) && !handle.equals(parentID)) {
										 driver.switchTo().window(handle);
										 flag=true;
										 break;
									 }
								 }
								 if (!flag) {
									 log(LogStatus.ERROR, "could not switch to third window , so cannot get "+vfPage[i]+" url link", YesNo.Yes);
									 sa.assertTrue(false, "could not switch to third window , so cannot get "+vfPage[i]+" url link");
								 }
								 ThreadSleep(3000);
								 urlLabelValue=getURL(driver, 10);
								 System.err.println(urlLabelValue);
								 switchToDefaultContent(driver);
								 driver.close();
								 driver.switchTo().window(secondID);
							 }else {
								 sa.assertTrue(false, "preview link of "+vfPage[i]+" is not clickable");
								 log(LogStatus.FAIL, "preview link of "+vfPage[i]+" is not clickable", YesNo.Yes);
							 }
						 }else {
							 sa.assertTrue(false, "visual force page link is not clickable");
							 log(LogStatus.FAIL, "visual force page link is not clickable", YesNo.Yes);
						 }
					 }else {
						 sa.assertTrue(false, "search textbox is not visible");
						 log(LogStatus.FAIL, "search textbox is not visible", YesNo.Yes);
					 }
					 driver.close();
					 driver.switchTo().window(parentID);
				 }else {
					 sa.assertTrue(false, "could not find window to switch");
					 log(LogStatus.FAIL, "could not find window to switch", YesNo.Yes);
				 }
			 }else {
				sa.assertTrue(false, "setup link is not clickable, so cannot get "+vfPage[i]+" url link");
				log(LogStatus.FAIL, "setup link is not clickable, so cannot get "+vfPage[i]+" url link", YesNo.Yes);
			}
			
			/////////////////////////////
			
			/*
			 * String[][] labelWithValue=
			 * {{navigationLabel,navigationLabelValue},{orderLabel,orderLabelValue},
			 * {urlLabel,urlLabelValue},{navigationTypeLabel,navigationTypeLabelValue}};
			 * 
			 * if (npbl.createNavigationItem(projectName, labelWithValue, 20)) {
			 * log(LogStatus.INFO, "created "+navigationLabelValue, YesNo.No);
			 * switchToDefaultContent(driver); refresh(driver); if
			 * (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName,
			 * action.BOOLEAN, 30)) { log(LogStatus.INFO,
			 * "Able to Click on "+navigationMenuName, YesNo.No); WebElement ele =
			 * npbl.getNavigationLabel(projectName, navigationLabels[i], action.BOOLEAN,
			 * 10); if (ele!=null) { log(LogStatus.INFO,
			 * navigationLabels[i]+" is  present on "+navigationMenuName+" after creation",
			 * YesNo.No);
			 * 
			 * } else { log(LogStatus.ERROR, navigationLabels[i]+" should be present on "
			 * +navigationMenuName+" after creation", YesNo.Yes);
			 * sa.assertTrue(false,navigationLabels[i]+" should be present on "
			 * +navigationMenuName+" after creation");
			 * 
			 * }
			 * 
			 * } else { log(LogStatus.ERROR,
			 * "Not Able to Click on "+navigationMenuName+" so cannot check presence of "
			 * +navigationLabels[i]+" after creation", YesNo.Yes);
			 * sa.assertTrue(false,"Not Able to Click on "
			 * +navigationMenuName+" so cannot check presence of "+navigationLabels[i]
			 * +" after creation"); }
			 * 
			 * 
			 * } else { log(LogStatus.ERROR, "Not Able to create "+navigationLabelValue,
			 * YesNo.Yes); sa.assertTrue(false, "Not Able to create "+navigationLabelValue);
			 * 
			 * }
			 */
		}
		
		//// Uncheck
		log(LogStatus.INFO, "<<<<<< Going to uncheck >>>>>>>", YesNo.No);
			for (int i = 0; i < navatarSetupSideMenuTab.length; i++) {
				flag=false;
				setupSideMenuTab=navatarSetupSideMenuTab[i];
				switchToDefaultContent(driver);
				flag=np.EnableOrDisableSettingOnNavatarSetUp(projectName, setupSideMenuTab, false);
				refresh(driver);
				// Verification on navigation menu
				switchToDefaultContent(driver);
				if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
					log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
					WebElement ele = npbl.getNavigationLabel(projectName, navigationLabels[i], action.BOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO, navigationLabels[i]+" is present on "+navigationMenuName+" even after disabling "+setupSideMenuTab+" but after creation from Navigation Tab", YesNo.No);
					} else {
						log(LogStatus.ERROR, navigationLabels[i]+" should be present on "+navigationMenuName+" even after disabling "+setupSideMenuTab+" but after creation from Navigation Tab", YesNo.Yes);
						sa.assertTrue(false,navigationLabels[i]+" should be present on "+navigationMenuName+"even after disabling "+setupSideMenuTab+" but after creation from Navigation Tab");
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot check presence of "+navigationLabels[i], YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot check presence of "+navigationLabels[i]);
				}
			}
		
			//// CHeck

			log(LogStatus.INFO, "<<<<<< Going to check >>>>>>>", YesNo.No);
			for (int i = 0; i < navatarSetupSideMenuTab.length; i++) {
				flag=false;
				setupSideMenuTab=navatarSetupSideMenuTab[i];
				switchToDefaultContent(driver);
				flag=np.EnableOrDisableSettingOnNavatarSetUp(projectName, setupSideMenuTab, true);
				refresh(driver);
				switchToDefaultContent(driver);
				if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
					log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
					WebElement ele = npbl.getNavigationLabel(projectName, navigationLabels[i], action.BOOLEAN, 10);
					if (ele==null) {
						log(LogStatus.INFO, navigationLabels[i]+" is not present on "+navigationMenuName+" even after enabling "+setupSideMenuTab, YesNo.No);
					} else {
						log(LogStatus.ERROR, navigationLabels[i]+" should not present on "+navigationMenuName+" even after enabling "+setupSideMenuTab, YesNo.Yes);
						sa.assertTrue(false,navigationLabels[i]+" should not present on "+navigationMenuName+"even after enabling "+setupSideMenuTab);

					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot check absenece of "+navigationLabels[i], YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot check absenece of "+navigationLabels[i]);
				}}
				
			
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc005_VerifyTheBulkActionsNavigationLink(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ReportsTabBusinessLayer report = new ReportsTabBusinessLayer(driver);
		EmailMyTemplatesPageBusinessLayer emailtemplate = new EmailMyTemplatesPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword);

		// Create Contact Custom Report with Contact ID
		
		String[] splitedReportFolderName = removeNumbersFromString(SmokeReportFolderName);
		SmokeReportFolderName = splitedReportFolderName[0] + lp.generateRandomNumber();
		if (report.createCustomReportOrDashboardFolder(environment, SmokeReportFolderName,
				ReportDashboardFolderType.ReportFolder, FolderAccess.ReadOnly)) {

			ReportField[] field = { ReportField.ContactID };
			String[] splitedReportName = removeNumbersFromString(SmokeReportName);
			SmokeReportName = splitedReportName[0] + lp.generateRandomNumber();

			ExcelUtils.writeData(phase1DataSheetFilePath, SmokeReportFolderName, "Report", excelLabel.Variable_Name,
					"SmokeReport1", excelLabel.Report_Folder_Name);
			if (report.createCustomReportForFolder(environment, mode, SmokeReportFolderName, ReportFormatName.Null,
					SmokeReportName, SmokeReportName, SmokeReportType, field, SmokeReportShow, null, SmokeReportRange,
					null, null)) {
				appLog.info("Custom Report is created succesdfully : " + SmokeReportName);
				ExcelUtils.writeData(phase1DataSheetFilePath, SmokeReportName, "Report", excelLabel.Variable_Name,
						"SmokeReport1", excelLabel.Report_Name);
			} else {
				appLog.error("Not able to create Custom Report : " + SmokeReportName);
				sa.assertTrue(false, "Not able to create Custom Report : " + SmokeReportName);
				log(LogStatus.ERROR, "Not able to create Custom Report : " + SmokeReportName, YesNo.Yes);
			}
			switchToDefaultContent(driver);
			home.clickOnSetUpLink(environment, Mode.Classic.toString());
			if (home.clickOnTab(environment, Mode.Classic.toString(), TabName.HomeTab)) {
	
				SmokeReportName = "R2" + SmokeReportName;
				if (report.createCustomReportForFolder(environment, mode, SmokeReportFolderName, ReportFormatName.Null,
						SmokeReportName, SmokeReportName, SmokeReportType, null, SmokeReportShow, null,
						SmokeReportRange, null, null)) {
					appLog.info("Custom Report is created succesdfully : R2" + SmokeReportName);
				} else {
					appLog.error("Not able to create Custom Report : R2" + SmokeReportName);
					sa.assertTrue(false, "Not able to create Custom Report : R2" + SmokeReportName);
					log(LogStatus.ERROR, "Not able to create Custom Report : R2" + SmokeReportName, YesNo.Yes);
				}
			}
		} else {
			appLog.error("Not able to create Custom Report folder: " + SmokeReportFolderName);
			sa.assertTrue(false, "Not able to create Custom Report folder: " + SmokeReportFolderName);
			log(LogStatus.ERROR, "Not able to create Custom Report folder: " + SmokeReportFolderName, YesNo.Yes);
		}
		home.switchToClassic();
		if (home.clickOnSetUpLink(environment, Mode.Classic.toString())) {
			String[] splitedEmailTemplateFolderName = removeNumbersFromString(EmailTemplate1_FolderName);
			EmailTemplate1_FolderName = splitedEmailTemplateFolderName[0] + lp.generateRandomNumber();
			if (emailtemplate.createCustomEmailFolder(environment, Mode.Classic.toString(), EmailTemplate1_FolderName,
					FolderAccess.ReadWrite)) {
				log(LogStatus.PASS, "Email Template Folder is created : " + EmailTemplate1_FolderName, YesNo.No);
				ExcelUtils.writeData(phase1DataSheetFilePath, EmailTemplate1_FolderName, "CustomEmailFolder",
						excelLabel.Variable_Name, "EmailTemplate1", excelLabel.Email_Template_Folder_Label);
				ThreadSleep(2000);
				String[] splitedEmailTemplateName = removeNumbersFromString(EmailTemplate1_TemplateName);
				EmailTemplate1_TemplateName = splitedEmailTemplateName[0] + lp.generateRandomNumber();
				if (emailtemplate.createCustomEmailTemplate(environment, Mode.Classic.toString(),
						EmailTemplate1_FolderName, EmailTemplateType.Text, EmailTemplate1_TemplateName,
						EmailTemplate1_TemplateDescription, EmailTemplate1_Subject, EmailTemplate1_Body)) {
					appLog.info("EMail Template is created :" + EmailTemplate1_TemplateName);

					ExcelUtils.writeData(phase1DataSheetFilePath, EmailTemplate1_TemplateName, "CustomEmailFolder",
							excelLabel.Variable_Name, "EmailTemplate1", excelLabel.Email_Template_Name);

				} else {
					appLog.error("EMail Template is not created :" + EmailTemplate1_TemplateName);
					sa.assertTrue(false, "EMail Template is not created :" + EmailTemplate1_TemplateName);
					log(LogStatus.ERROR, "EMail Template is not created :" + EmailTemplate1_TemplateName, YesNo.Yes);
				}
			} else {
				appLog.error("Not able to create Custom Email folder: " + EmailTemplate1_FolderName);
				sa.assertTrue(false, "Not able to create Custom Email folder: " + EmailTemplate1_FolderName);
				log(LogStatus.ERROR, "Not able to create Custom Email folder: " + EmailTemplate1_FolderName, YesNo.Yes);
			}
		} else {
			appLog.error("Not able to clicked on setup link so cannot create Email Folder And Template");
			sa.assertTrue(false, "Not able to clicked on setup link so cannot create Email Folder And Template");
			log(LogStatus.ERROR, "Not able to clicked on setup link so cannot create Email Folder And Template",
					YesNo.Yes);
		}

		home.switchToLighting();

		// Verification on navigation menu
		/*navigationMenuName = NavigationMenuItems.Bulk_Actions.toString();*/
		navigationMenuName = NavigationMenuItems.Create.toString();
		NavigationPageBusineesLayer  npbl = new NavigationPageBusineesLayer(driver) ;
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);

		String[]  BulkActionNavigationLinks = {BulkActions_DefaultValues.Bulk_Email.toString(),
				BulkActions_DefaultValues.Bulk_Fundraising.toString(),
				BulkActions_DefaultValues.Bulk_Commitments.toString(),
				BulkActions_DefaultValues.Deal_Creation.toString(),
				BulkActions_DefaultValues.Individual_Investor_Creation.toString()};
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
						String reportName=SmokeReportName;
						String templateName=SmokeReportFolderName;
						String fname=M3Contact1FName;
						String lname = M3Contact1LName;
						String folderName=EmailTemplate1_FolderName;
						String emailTemplateName = EmailTemplate1_TemplateName;
						if (hp.VerifyBulkEmailFunctionality(environment, mode, templateName, reportName, fname, lname, lname, searchContactInEmailProspectGrid.Yes, folderName, emailTemplateName)) {
							log(LogStatus.INFO, bulkActionNavigationLink+" functionality is verified succesfuly ", YesNo.No);

						} else {
							log(LogStatus.ERROR, bulkActionNavigationLink+" functionality not verified ", YesNo.Yes);
							sa.assertTrue(false,bulkActionNavigationLink+" functionality not verified ");

						}

					} else if(i==1) {
						String fr = M3Ins1+" - "+M3Fund1;
						List<String> contactNamelist= new ArrayList<String>();
						contactNamelist.add(M3Contact1FName+" "+M3Contact1LName);
						List<String> accountlist= new ArrayList<String>();
						accountlist.add(M3Ins1);

						switchToFrame(driver, 60, home.getCreateFundraisingsFrame_Lighting(120));

						if(hp.selectFundNameOrCompanyNameOnCreateFundraisings(environment,mode, PopUpName.selectFundPopUp, M3Fund1, null)) {
							log(LogStatus.INFO, "Select Fund : "+M3Fund1, YesNo.No);
							ThreadSleep(3000);
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

					}else if(i==2){
						
						String[][] commitmentInformation= {{M3Ins9,"100",M3PartnerShip1}};

						if(hp.selectFundraisingNameOrCommitmentType(environment, mode, M3FRName1, null, null, null, CommitmentType.fundraisingName)) {
							if(hp.commitmentInfoAndAdditionalInfo(environment, mode, commitmentInformation, null,null,null)) {
								log(LogStatus.INFO, "All commitment information and additional information is passed successfully", YesNo.Yes);
								ThreadSleep(3000);
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
					}else if(i==3){
					
						if (hp.createNewDealPipeLine(environment, mode, M3Ins10, "Due Diligence",null)) {
							appLog.info("VAlue added for mandatory Field for Deal Creation");
							if (hp.clickOnCreateDealButtonAndVerifyingLandingPage(environment, mode, M3Ins10)) {
								appLog.info("Pipe Line Created and Verifiied : ");
							} else {
								sa.assertTrue(false, "Not Able to Click on Create Deal Button or Landing Page Not Verified");
								log(LogStatus.SKIP, "Not Able to Click on Create Deal Button or Landing Page Not Verified",YesNo.Yes);
							}

						}else{

							sa.assertTrue(false, "Not Able to Add values for mandatory Field for Deal Creation popup");
							log(LogStatus.SKIP, "Not Able to Add values for mandatory Field for Deal Creation popup", YesNo.Yes);
						}
					}else{
						//String emailId = lp.generateRandomEmailId();
						
						String SmokeINDINV4 = M3Contact4FName+" "+M3Contact4LName+" - "+"HNI";
						String[][] labelNamesAndValue= {
								{IndiviualInvestorFieldLabel.First_Name.toString(),M3Contact4FName},
								{IndiviualInvestorFieldLabel.Last_Name.toString(),M3Contact4LName}};
					
						if(hp.createIndiviualInvestor(environment, mode, labelNamesAndValue, null, TopOrBottom.TOP)) {
							log(LogStatus.INFO, "Successfully Create Indiviual Investor "+SmokeINDINV4, YesNo.No);
						//ExcelUtils.writeData(smokeFilePath, emailId, "Contacts", excelLabel.Variable_Name,"SmokeC7", excelLabel.Contact_EmailId);
							if(hp.getLabelHeaderText(environment, mode, 60).getText().trim().contains(SmokeINDINV4)) {
								log(LogStatus.INFO, SmokeINDINV4+" header text is verified", YesNo.No);
							}else {
								log(LogStatus.ERROR, SmokeINDINV4+" header text is not matched ", YesNo.Yes);
								sa.assertTrue(false, SmokeINDINV4+" header text is not matched ");
							}
						}else {
							log(LogStatus.ERROR, "Not able to Create Indiviual Investor "+SmokeINDINV4, YesNo.Yes);
							sa.assertTrue(false, "Not able to Create Indiviual Investor "+SmokeINDINV4);
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
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc006_VerifyTheNewInteractionNavigationLink(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		NavigationPageBusineesLayer  npbl = new NavigationPageBusineesLayer(driver);
		BasePageBusinessLayer  bl = new BasePageBusinessLayer(driver);
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		/*navigationMenuName = NavigationMenuItems.New_Interactions.toString();*/
		navigationMenuName = NavigationMenuItems.Create.toString();

		String[]  newInteractionsNavigationLinks = {NewInteractions_DefaultValues.Call.toString(),
				/*NewInteractions_DefaultValues.Meeting.toString(),*/
				NewInteractions_DefaultValues.Task.toString()};
		int i=0;
		boolean flag = false;
		WebElement ele;
		String adminUerName = crmUser1FirstName+" "+crmUser1LastName;
		String subject ="";
		String dueDate=todaysDate;
		String status="";
		String priority="";
		String contactNAme= M3Contact1FName+" "+M3Contact1LName;
		String[][] dropDownLabelWithValues = new String[3][];
		String[][] dropDownLabel1WithValues = new String[3][];

		for (i=0;i<newInteractionsNavigationLinks.length;i++) {
			String newInteractionsNavigationLink=newInteractionsNavigationLinks[i];
			flag=false;
			String[][] basicsection= {{"Subject",M3Call1Subject},{"Related_To",M3Contact1FName+" "+M3Contact1LName}};
			String[][] advanceSection= {{"Priority","Normal"},{"Due Date Only",todaysDate}};
	        String[][] taskSection= {{"Subject","ABC"},{"Due Date Only","06/04/2020"},{"Status","In Progress"}};
			if(bl.createActivityTimeline(projectName, true, newInteractionsNavigationLink, basicsection, advanceSection, null,null))
//			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
//				log(LogStatus.INFO, "Able to Click on "+navigationMenuName+" Going to click on : "+newInteractionsNavigationLink+" for creation ", YesNo.No);

//				WebElement ele = npbl.getNavigationLabel(projectName, newInteractionsNavigationLink, action.BOOLEAN, 10);
//				if (ele!=null && click(driver, ele, newInteractionsNavigationLink, action.BOOLEAN)) {
//					log(LogStatus.INFO, "Click on "+newInteractionsNavigationLink+" so going for creation", YesNo.No);
//					flag = true;
//				} else {
//					log(LogStatus.ERROR, "Not Able to Click on "+newInteractionsNavigationLink+" so cannot create data related to this ", YesNo.Yes);
//					sa.assertTrue(false,"Not Able to Click on "+newInteractionsNavigationLink+" so cannot create data related to this ");
//
//				}
//				flag=true;
//				if (flag) {
//
//					ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.CallPopUp, PageLabel.Assigned_To.toString(),false, adminUerName, action.SCROLLANDBOOLEAN, 20);
//					if (ele!=null) {
//						log(LogStatus.INFO, adminUerName+" Found For Label "+PageLabel.Assigned_To.toString(),YesNo.No);	
//					} else {
//						sa.assertTrue(false,adminUerName+" not Found For Label "+PageLabel.Assigned_To.toString());
//						log(LogStatus.ERROR, adminUerName+" not Found For Label "+PageLabel.Assigned_To.toString(),YesNo.Yes);
//
//					}

					if (i==0) {
						subject =M3Call1Subject;
						status=M3Call1Status;
						priority=M3Cal11Priority;
						
//						String[][] dropDownLabel1WithValues1 = {{PageLabel.Subject.toString(),subject},
//								{PageLabel.Name.toString(),contactNAme}};
//
//						dropDownLabel1WithValues=dropDownLabel1WithValues1;
//
//						String[][] dropDownLabelWithValues1 = {{PageLabel.Status.toString(),status},
//								{PageLabel.Priority.toString(),priority}};
//
//						dropDownLabelWithValues=dropDownLabelWithValues1;

					} else if(i==1) {

						subject =M3Meeting1Subject;
						String[][] dropDownLabel1WithValues2 = {{PageLabel.Subject.toString(),subject},
								{PageLabel.Name.toString(),contactNAme}};

						dropDownLabel1WithValues=dropDownLabel1WithValues2;

						dropDownLabelWithValues=null;

					}else{
						subject =M3Task1Subject;
						status=M3Task1Status;
						priority=M3Task1Priority;
						
						String[][] dropDownLabel1WithValues3 = {{PageLabel.Subject.toString(),subject},
								{PageLabel.Name.toString(),contactNAme}};

						dropDownLabel1WithValues=dropDownLabel1WithValues3;
						String[][] dropDownLabelWithValues3 = {{PageLabel.Status.toString(),status},
								{PageLabel.Priority.toString(),priority}};	
						dropDownLabelWithValues=dropDownLabelWithValues3;
					}

//					if (tp.enteringSubjectAndSelectDropDownValuesonTaskPopUp(projectName, PageName.TaskPage, subject, dropDownLabelWithValues, action.SCROLLANDBOOLEAN, 10)) {
//						log(LogStatus.INFO, "Entered value to Subject Text Box ", YesNo.No);
//						ThreadSleep(1000);
//						if(i!=1) {
//						if (sendKeys(driver, tp.getdueDateTextBoxInNewTask(projectName, 20), dueDate, PageLabel.Due_Date.toString(), action.SCROLLANDBOOLEAN)) {
//							log(LogStatus.INFO, "Entered value to Due Date Text Box", YesNo.No);
//							ThreadSleep(1000);
//						}else {
//							log(LogStatus.ERROR, "Not able to enter value on duedate textbox "+newInteractionsNavigationLink, YesNo.Yes);
//							sa.assertTrue(false,"Not able to enter value on duedate textbox "+newInteractionsNavigationLink );
//						}
//						}
//						flag = cp.selectRelatedAssociationOrContactOrRelatedToDropDownAndClickOnItem(projectName, PageName.TaskPage, PageLabel.Name.toString(), TabName.TaskTab, contactNAme, action.SCROLLANDBOOLEAN, 10);		
//						if (flag) {
//							ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.TaskPage, PageLabel.Name.toString(),true, contactNAme, action.SCROLLANDBOOLEAN, 5);
//							if (ele!=null) {
//								log(LogStatus.INFO, contactNAme+" Found For Label "+PageLabel.Name.toString()+" at "+newInteractionsNavigationLink,YesNo.No);	
//							} else {
//								sa.assertTrue(false,contactNAme+" not Found For Label "+PageLabel.Name.toString()+" at "+newInteractionsNavigationLink);
//								log(LogStatus.ERROR, contactNAme+" not Found For Label "+PageLabel.Name.toString()+" at "+newInteractionsNavigationLink,YesNo.Yes);
//							}
//						} else {
//							sa.assertTrue(false,"Not Able to Select "+contactNAme+" For Label "+PageLabel.Name);
//							log(LogStatus.SKIP,"Not Able to Select "+contactNAme+" For Label "+PageLabel.Name,YesNo.Yes);
//
//						}
//						if (clickUsingJavaScript(driver, tp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
//							log(LogStatus.INFO,"successfully created : "+subject+" for "+newInteractionsNavigationLink,  YesNo.No);
//							ExcelUtils.writeData(phase1DataSheetFilePath,dueDate, "Task1", excelLabel.Variable_Name, "M3CALL1", excelLabel.Due_Date);
//							ele = tp.getCreatedConfirmationMsg(projectName, 15);
//							if (ele!=null) {
//								String actualValue = ele.getText().trim();
//								String expectedValue=tp.taskCreatesMsg(projectName, subject);
//								if (expectedValue.contains(actualValue)) {
//									log(LogStatus.INFO,expectedValue+" matched FOR Confirmation Msg", YesNo.No);
//								} else {
//									log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg", YesNo.Yes);
//									BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+expectedValue+" not matched FOR Confirmation Msg");
//								}
//							} else {
//								sa.assertTrue(false,"Created Task Msg Ele not Found");
//								log(LogStatus.SKIP,"Created Task Msg Ele not Found",YesNo.Yes);
//							}
							
							if(i!=1) {
								String[][] fieldsWithValues= {
										{PageLabel.Subject.toString(),subject},
										{PageLabel.Due_Date.toString(),dueDate},
										{PageLabel.Name.toString(),contactNAme},
										{PageLabel.Status.toString(),status},
										{PageLabel.Priority.toString(),priority}};
								
								tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);
							}else {
								String[][] fieldsWithValues= {
										{PageLabel.Subject.toString(),subject},
										{PageLabel.Name.toString(),contactNAme}};
								
								tp.fieldVerificationForTaskInViewMode(projectName, PageName.TaskPage, fieldsWithValues, action.BOOLEAN, 10);
							}
//						}
//						else {
//							log(LogStatus.ERROR, "Save Button is not visible so could not be create "+newInteractionsNavigationLink, YesNo.Yes);
//							sa.assertTrue(false,"Save Button is not visible so could not be create "+newInteractionsNavigationLink );
//						}
//
//
//					}else {
//						log(LogStatus.ERROR, "Subject textbox is not visible so could not be create "+newInteractionsNavigationLink, YesNo.Yes);
//						sa.assertTrue(false,"Subject textbox is not visible so could not be create "+newInteractionsNavigationLink );
//					}



					if(hp.clickOnTab(environment, mode, TabName.ContactTab)) {
						log(LogStatus.INFO, "clicked on Contact Tab", YesNo.No);
						if(hp.clickOnAlreadyCreatedItem(projectName, contactNAme, 120)) {
							log(LogStatus.INFO, "Clicked on "+contactNAme, YesNo.No);
							ele = lp.moreStepsBtn(projectName, EnableDisable.Enable, 10);
							click(driver, ele, "More Steps" ,action.BOOLEAN);

							ele = tp.getActivityTimeLineItem2(projectName, PageName.Object2Page, ActivityTimeLineItem.Expand_All, 10);
							click(driver, ele, ActivityTimeLineItem.Expand_All.toString(), action.BOOLEAN);
							tp.verifyActivityAtNextStep2(projectName, PageName.Object2Page, contactNAme,subject, null, dueDate, false, null, false, "", 10);

						}else {
							log(LogStatus.ERROR, "Not able to Clicked on "+contactNAme, YesNo.Yes);
							sa.assertTrue(false,  "Not able to Clicked on "+contactNAme);
						}
					}else {
						log(LogStatus.ERROR, "Not able to click on Contact tab so can not check created "+subject+" for contact "+contactNAme, YesNo.Yes);
						sa.assertTrue(false,"Not able to click on Contact tab so can not check created "+subject+" for contact "+contactNAme);
					}
				}
//			} else {
//				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on : "+newInteractionsNavigationLink+" for creation ", YesNo.Yes);
//				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on : "+newInteractionsNavigationLink+" for creation ");
//			}
//		}
		lp.CRMlogout();
		sa.assertAll();

}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc007_VerifyTheCreateNewNavigationLink(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		NavigationPageBusineesLayer  npbl = new NavigationPageBusineesLayer(driver) ;
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		navigationMenuName = NavigationMenuItems.Create.toString();

		String[]  createNewNavigationLinks = {CreateNew_DefaultValues.New_Deal.toString(),
				CreateNew_DefaultValues.New_Contact.toString(),
				CreateNew_DefaultValues.New_Institution.toString(),
				CreateNew_DefaultValues.New_Institution.toString(),
				CreateNew_DefaultValues.New_Institution.toString(),
				CreateNew_DefaultValues.New_Institution.toString(),
				CreateNew_DefaultValues.New_Institution.toString(),
				CreateNew_DefaultValues.New_Institution.toString(),
				CreateNew_DefaultValues.New_Institution.toString(),
				CreateNew_DefaultValues.New_Institution.toString(),
				CreateNew_DefaultValues.New_Institution.toString(),
				CreateNew_DefaultValues.New_Institution.toString()
				};
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

					if (i == 0) {

						if (fp.createDealPopUp(projectName, M3Deal1RecordType, M3Deal1, M3Deal1CompanyName,
								M3Deal1Stage, null, 15)) {
							log(LogStatus.INFO, "Created Deal : " + M3Deal1 + " through " + createNewNavigationLink,
									YesNo.No);
						} else {
							sa.assertTrue(false, "Not Able to Create Deal  : " + " through " + createNewNavigationLink);
							log(LogStatus.SKIP, "Not Able to Create Deal  : " + " through " + createNewNavigationLink,
									YesNo.Yes);
						}

					} else if (i == 1) {

						M3Contact2EmailID = lp.generateRandomEmailId(gmailUserName);
						ExcelUtils.writeData(phase1DataSheetFilePath, M3Contact2EmailID, "Contacts",
								excelLabel.Variable_Name, "M3CON2", excelLabel.Contact_EmailId);

						if (cp.createContactPopUp(projectName, M3Contact2FName, M3Contact2LName, M3Ins1,
								M3Contact2EmailID, M3Contact2RecordType, null, null, CreationPage.ContactPage, null)) {
							log(LogStatus.INFO,
									"successfully Created Contact : " + M3Contact2FName + " " + M3Contact2LName,
									YesNo.No);
						} else {
							sa.assertTrue(false,
									"Not Able to Create Contact : " + M3Contact2FName + " " + M3Contact2LName);
							log(LogStatus.SKIP,
									"Not Able to Create Contact: " + M3Contact2FName + " " + M3Contact2LName,
									YesNo.Yes);
						}

					} else {
						String value = "";
						String type = "";
						String parent = null;
						
						if (i == 2) {
							value = M3Ins3;
							type = M3Ins3RecordType;
						} else if (i == 3) {
							value = M3Ins4;
							type = M3Ins4RecordType;
						} else if (i == 4) {
							value = M3Ins5;
							type = M3Ins5RecordType;

						} else if (i == 5) {
							value = M3Ins6;
							type = M3Ins6RecordType;
							parent = M3Ins6Parent;
						} else if (i == 6) {
							value = M3Ins7;
							type = M3Ins7RecordType;
						} else if (i == 7) {
							value = M3Ins8;
							type = M3Ins8RecordType;
							parent = M3Ins8Parent;
						} else if (i == 8) {
							value = M3Ins11;
							type = M3Ins11RecordType;
						} else if (i == 9) {
							value = M3Ins12;
							type = M3Ins12RecordType;
						} else if (i == 10) {
							value = M3Ins13;
							type = M3Ins13RecordType;
						}else if(i==11) {
						value= M3Ins14;
						type= M3Ins14RecordType;
					}
						 
						 if (ip.createInstitutionPopUp(projectName, environment, mode, value,type, InstitutionPageFieldLabelText.Parent_Institution.toString(),parent)) {
								log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
							} else {
								sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
								log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
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
	public void M3Tc008_VerifyTheFunctionlityOfMeetingTypeFieldInTaskPopup(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer  npbl = new NavigationPageBusineesLayer(driver) ;
		lp.CRMLogin(crmUser1EmailID, adminPassword);

	/*	navigationMenuName = NavigationMenuItems.New_Interactions.toString();*/
		navigationMenuName = NavigationMenuItems.Create.toString();

		String[]  newInteractionsNavigationLinks = {
				NewInteractions_DefaultValues.Meeting.toString(),
				NewInteractions_DefaultValues.Meeting.toString()};
		int i=0;
		boolean flag = false;
		String homeUrl = getURL(driver, 20);
		String actualUrl="";
		WebElement ele = null;

		for (String newInteractionsNavigationLink : newInteractionsNavigationLinks) {
			flag=false;
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 120)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName+" Going to click on : "+newInteractionsNavigationLink+" for creation ", YesNo.No);

				ele = npbl.getNavigationLabel(projectName, newInteractionsNavigationLink, action.BOOLEAN, 60);
				if (ele!=null && click(driver, ele, newInteractionsNavigationLink, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+newInteractionsNavigationLink, YesNo.No);
					ThreadSleep(10000);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+newInteractionsNavigationLink, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+newInteractionsNavigationLink);

				}

				if (flag) {
					if (i==0) {
						ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
					} else if(i==1) {
						ele=npbl.getCancelButtonForNavigationLabelPopuP(projectName, action.BOOLEAN, 30);
					}
					if (click(driver, ele, "Cross/Cancel Buton on Meting pop up ", action.BOOLEAN)) {
						log(LogStatus.INFO, "Able to Click on Cross/Cancel Buton on Meting pop up",YesNo.No);
						ThreadSleep(10000);
						actualUrl=getURL(driver, 30);
						if (homeUrl.equalsIgnoreCase(actualUrl)) {
							log(LogStatus.INFO, "After Click on Cross/Cancel Buton on Meeting pop up page is redirected to previous page/url",YesNo.Yes);
						} else {
							log(LogStatus.ERROR, "After Click on Cross/Cancel Buton on Meeting pop up page is not redirected to previous page/url \n Actual : "+actualUrl+" \n Expeted : "+homeUrl,YesNo.Yes);
							sa.assertTrue(false,"After Click on Cross/Cancel Buton on Meeting pop up page is not redirected to previous page/url \n Actual : "+actualUrl+" \n Expeted : "+homeUrl);
						}
						ThreadSleep(2000);
					} else {
						log(LogStatus.ERROR, "Not Able to Click on Cross/Cancel Buton on Meting pop up",YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on Cross/Cancel Buton on Meting pop up");
					}
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on : "+newInteractionsNavigationLink,YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on : "+newInteractionsNavigationLink);
			}
			i++;
			refresh(driver);
			ThreadSleep(5000);
		}
		lp.CRMlogout();
		sa.assertAll();

}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc009_VerifyNavigationLinkAfterClickingBackOnBrowserButton(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer  npbl = new NavigationPageBusineesLayer(driver) ;
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		int i=0;
		boolean flag = false;
		String homeUrl = getURL(driver, 20);
		String actualUrl="";
		WebElement ele = null;
		
		// Bulk Email 
		
	/*	navigationMenuName = NavigationMenuItems.Bulk_Actions.toString();*/
		navigationMenuName = NavigationMenuItems.Create.toString();
		String[]  newInteractionsNavigationLinks = {
				BulkActions_DefaultValues.Bulk_Email.toString(),
				BulkActions_DefaultValues.Bulk_Fundraising.toString(),
				BulkActions_DefaultValues.Bulk_Commitments.toString()};
		i=0;
		System.err.println(" "+i);
		for (String newInteractionsNavigationLink : newInteractionsNavigationLinks) {
			flag=false;
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName+" Going to click on : "+newInteractionsNavigationLink+" for creation ", YesNo.No);
				ele = npbl.getNavigationLabel(projectName, newInteractionsNavigationLink, action.BOOLEAN, 10);
				if (ele!=null && click(driver, ele, newInteractionsNavigationLink, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+newInteractionsNavigationLink, YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+newInteractionsNavigationLink, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+newInteractionsNavigationLink);

				}
				if (flag) {
					ThreadSleep(10000);
					driver.navigate().back();
					ThreadSleep(5000);
					actualUrl=getURL(driver, 30);
					if (homeUrl.equalsIgnoreCase(actualUrl)) {
						log(LogStatus.INFO, newInteractionsNavigationLink+" After Click on Browser Back Buton page is redirected to previous page/url ",YesNo.Yes);

					} else {
						log(LogStatus.ERROR, newInteractionsNavigationLink+" After Click on Browser Back Buton page is not redirected to previous page/url \n Actual : "+actualUrl+" \n Expeted : "+homeUrl,YesNo.Yes);
						sa.assertTrue(false,newInteractionsNavigationLink+" After Click on Browser Back Buton page is not redirected to previous page/url \n Actual : "+actualUrl+" \n Expeted : "+homeUrl);
					}
					String tabName = lp.getTabName(projectName, TabName.HomeTab);

					if (lp.getSelectedTab(projectName, tabName)) {
						log(LogStatus.INFO, newInteractionsNavigationLink+" After Click on Browser Back Buton Page is redirected to "+tabName,YesNo.Yes);

					} else {
						log(LogStatus.ERROR, newInteractionsNavigationLink+" After Click on Browser Back Buton Page is not redirected to "+tabName,YesNo.Yes);
						sa.assertTrue(false,newInteractionsNavigationLink+" After Click on Browser Back Buton Page is not redirected to "+tabName);
					}

				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on : "+newInteractionsNavigationLink,YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on : "+newInteractionsNavigationLink);
			}
			ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 2);
			click(driver, ele, "", action.BOOLEAN);
			i++;
			ThreadSleep(2000);
			refresh(driver);
		}
		
		
		// New Interaction
		
		/*navigationMenuName = NavigationMenuItems.New_Interactions.toString();*/
		navigationMenuName = NavigationMenuItems.Create.toString();
		String[]  newInteractionsNavigationLinks1 = {
				NewInteractions_DefaultValues.Call.toString(),
				/*NewInteractions_DefaultValues.Meeting.toString(),*/
				NewInteractions_DefaultValues.Task.toString()};
		i=0;
		for (String newInteractionsNavigationLink : newInteractionsNavigationLinks1) {
			flag=false;
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName+" Going to click on : "+newInteractionsNavigationLink+" for creation ", YesNo.No);
				ele = npbl.getNavigationLabel(projectName, newInteractionsNavigationLink, action.BOOLEAN, 10);
				if (ele!=null && click(driver, ele, newInteractionsNavigationLink, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+newInteractionsNavigationLink, YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+newInteractionsNavigationLink, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+newInteractionsNavigationLink);

				}
				if (flag) {
					ThreadSleep(10000);
					driver.navigate().back();
					ThreadSleep(5000);
					actualUrl=getURL(driver, 30);
					if (homeUrl.equalsIgnoreCase(actualUrl)) {
						log(LogStatus.INFO, newInteractionsNavigationLink+" After Click on Browser Back Buton page is redirected to previous page/url ",YesNo.Yes);

					} else {
						log(LogStatus.ERROR, newInteractionsNavigationLink+" After Click on Browser Back Buton page is not redirected to previous page/url \n Actual : "+actualUrl+" \n Expeted : "+homeUrl,YesNo.Yes);
						sa.assertTrue(false,newInteractionsNavigationLink+" After Click on Browser Back Buton page is not redirected to previous page/url \n Actual : "+actualUrl+" \n Expeted : "+homeUrl);
					}
					String tabName = lp.getTabName(projectName, TabName.HomeTab);

					if (lp.getSelectedTab(projectName, tabName)) {
						log(LogStatus.INFO, newInteractionsNavigationLink+" After Click on Browser Back Buton Page is redirected to "+tabName,YesNo.Yes);

					} else {
						log(LogStatus.ERROR, newInteractionsNavigationLink+" After Click on Browser Back Buton Page is not redirected to "+tabName,YesNo.Yes);
						sa.assertTrue(false,newInteractionsNavigationLink+" After Click on Browser Back Buton Page is not redirected to "+tabName);
					}

				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on : "+newInteractionsNavigationLink,YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on : "+newInteractionsNavigationLink);
			}
			ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 2);
			click(driver, ele, "", action.BOOLEAN);
			i++;
			ThreadSleep(2000);
			refresh(driver);
		}
		
		// Create New 
		
		navigationMenuName = NavigationMenuItems.Create.toString();
		String[]  newInteractionsNavigationLinks2 = {
				CreateNew_DefaultValues.New_Deal.toString(),
				CreateNew_DefaultValues.New_Contact.toString(),
				CreateNew_DefaultValues.New_Institution.toString()};
		i=0;
		for (String newInteractionsNavigationLink : newInteractionsNavigationLinks2) {
			flag=false;
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName+" Going to click on : "+newInteractionsNavigationLink+" for creation ", YesNo.No);
				ele = npbl.getNavigationLabel(projectName, newInteractionsNavigationLink, action.BOOLEAN, 10);
				if (ele!=null && click(driver, ele, newInteractionsNavigationLink, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+newInteractionsNavigationLink, YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+newInteractionsNavigationLink, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+newInteractionsNavigationLink);

				}
				if (flag) {
					ThreadSleep(10000);
					driver.navigate().back();
					ThreadSleep(5000);
					actualUrl=getURL(driver, 30);
					if (homeUrl.equalsIgnoreCase(actualUrl)) {
						log(LogStatus.INFO, newInteractionsNavigationLink+" After Click on Browser Back Buton page is redirected to previous page/url ",YesNo.Yes);

					} else {
						log(LogStatus.ERROR, newInteractionsNavigationLink+" After Click on Browser Back Buton page is not redirected to previous page/url \n Actual : "+actualUrl+" \n Expeted : "+homeUrl,YesNo.Yes);
						sa.assertTrue(false,newInteractionsNavigationLink+" After Click on Browser Back Buton page is not redirected to previous page/url \n Actual : "+actualUrl+" \n Expeted : "+homeUrl);
					}
					String tabName = lp.getTabName(projectName, TabName.HomeTab);

					if (lp.getSelectedTab(projectName, tabName)) {
						log(LogStatus.INFO, newInteractionsNavigationLink+" After Click on Browser Back Buton Page is redirected to "+tabName,YesNo.Yes);

					} else {
						log(LogStatus.ERROR, newInteractionsNavigationLink+" After Click on Browser Back Buton Page is not redirected to "+tabName,YesNo.Yes);
						sa.assertTrue(false,newInteractionsNavigationLink+" After Click on Browser Back Buton Page is not redirected to "+tabName);
					}

				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on : "+newInteractionsNavigationLink,YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on : "+newInteractionsNavigationLink);
			}
			ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 2);
			click(driver, ele, "", action.BOOLEAN);
			i++;
			ThreadSleep(2000);
			refresh(driver);
		}
		
		lp.CRMlogout();
		sa.assertAll();

}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc010_VerifyNavigationLabelAlignmentWithImageAndLabelName(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer  npbl = new NavigationPageBusineesLayer(driver) ;
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		String[] navigationMenuNames = {NavigationMenuItems.Create.toString()};

		WebElement ele = null;

		for (int i = 0; i < navigationMenuNames.length; i++) {
			navigationMenuName = navigationMenuNames[i];
			if (npbl.clickOnNavatarEdgeLink(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				if (npbl.clickOnNavatarEdgeLink(projectName, navigationMenuName, action.BOOLEAN, 30)) {
					log(LogStatus.INFO, "Second Time Click on "+navigationMenuName+" so it should be minimzed", YesNo.No);
					ThreadSleep(3000);
					ele = npbl.getNavatarQuickLinkMinimize_Lighting(projectName, 5);
					if (ele==null) {
						log(LogStatus.INFO, "After Click on "+navigationMenuName+" Second Time it is minimized", YesNo.No);
					} else {
						log(LogStatus.ERROR, "After Click on "+navigationMenuName+" Second Time it is not minimized",YesNo.Yes);
						sa.assertTrue(false,"After Click on "+navigationMenuName+" Second Time it is not minimized");
					}
				} else {
					log(LogStatus.ERROR, "Second Time Click on "+navigationMenuName+" so it is not minimzed",YesNo.Yes);
					sa.assertTrue(false,"Second Time Click on "+navigationMenuName+" so it is not minimzed");
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify image and icon",YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify image and icon");
			}
		}
		lp.CRMlogout();
		sa.assertAll();

}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc011_1_UploadCsvToCreateTheNavigationData_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		DataLoaderWizardPageBusinessLayer dataload = new DataLoaderWizardPageBusinessLayer(driver);
		mode=Mode.Classic.toString();
		boolean flag = false;
		lp.CRMLogin(superAdminUserName, adminPassword);
		lp.switchToClassic();
		if(dataload.dataImportWizard(ObjectName.Navigation, ObjectType.Custom, "\\UploadFiles\\Module 3\\UploadCSV\\Navigation Menu Test Data - Parent.csv", DataImportType.AddNewRecords, "8")) {
			appLog.info("Parent Data is imported Successfully in "+ObjectName.Navigation);
			flag=true;
		}else {
			appLog.error("Parent Data is not imported in "+ObjectName.Navigation+" so child data cannot imported");
			sa.assertTrue(false, "Parent Data is not imported in "+ObjectName.Navigation+" so child data cannot imported");
		}
		if(flag) {
			
			if(dataload.dataImportWizard(ObjectName.Navigation, ObjectType.Custom, "\\UploadFiles\\Module 3\\UploadCSV\\Navigation Menu Test Data - Child.csv", DataImportType.AddNewRecords, "9")) {
				appLog.info("Data is imported Successfully in "+ObjectName.Navigation);
				flag=true;

			}else {
				appLog.error("Data is not imported in "+ObjectName.Navigation);
				sa.assertTrue(false, "Data is not imported in "+ObjectName.Navigation);
			}
		}
		lp.switchToLighting();
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc011_2_UploadCsvToCreateTheNavigationData_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		System.err.println(NavigationMenuTestData_PEExcel+" >>>>>>>>>>>>><<<<<<<<<<<< "+NavigationMenuTestData_PESheet);

		List<String> csvRecords = ExcelUtils.readAllDataFromCSVFileIntoList(NavigationMenuTestData_PEExcel, false);
		if (!csvRecords.isEmpty()) {
			log(LogStatus.INFO, "Records Fetched from CSV File : "+NavigationMenuTestData_PEExcel, YesNo.No);

			Map<String, Integer> navigationParentLabelWithOrder = navigationParentLabelWithOrder(csvRecords);
			Map<String, Integer> navigationParentLabelWithSortedOrder = sortByValue(true, navigationParentLabelWithOrder);
			Map<String, String> navigationParentLabelWithChildAndOrder = navigationParentLabelWithChildAndOrder(csvRecords);
			Map<String, String> navigationParentLabelWithChildSorted = navigationParentLabelWithChildSorted(navigationParentLabelWithChildAndOrder);


			// Verification on navigation menu
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				npbl.verifyingNavigationMenuLink(projectName, navigationParentLabelWithSortedOrder, navigationParentLabelWithChildSorted, action.BOOLEAN, 0);;
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify order", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify order");
			}

			// Verification on navigation Tab
			String labelValue="";
			boolean isSelectAll = true;
			if (npbl.clickOnTab(projectName, navigationTab)) {
				log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);

				for (String labelName : csvRecords) {
					labelValue=labelName.split(commaSP)[0];
					if (npbl.CheckAlreadyCreatedItem(projectName, labelValue, isSelectAll, 15)) {
						log(LogStatus.INFO,"Item found: "+labelValue+" on Tab : "+navigationTab, YesNo.No);
					}else {

						log(LogStatus.ERROR,"Item not found: "+labelValue+" on Tab : "+navigationTab, YesNo.Yes);
						sa.assertTrue(false,"Item not found: "+labelValue+" on Tab : "+navigationTab);
					}
				}

				isSelectAll=false;
			} else {
				log(LogStatus.ERROR, "Not Able to Click on Tab : "+navigationTab, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on Tab : "+navigationTab);
			}

		} else {
			log(LogStatus.FAIL, "Unable to Fetch Records from CSV File : "+NavigationMenuTestData_PEExcel, YesNo.Yes);
			sa.assertTrue(false, "Unable to Fetch Records from CSV File : "+NavigationMenuTestData_PEExcel);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc012_RenameNavigationLabelAndVerifyImpactOnNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		/*navigationMenuName=NavigationMenuItems.New_Interactions.toString();*/
		navigationMenuName=NavigationMenuItems.Create.toString();
		String updatedName="";
		WebElement ele;
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		String updatedNavigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Update_Navigation_Label_Name);
		String navLb;
		navLb=navigationLabel;
		if (npbl.clickOnTab(projectName, navigationTab)) {
			log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);
			if (npbl.clickOnAlreadyCreatedItem(projectName, navLb, true, 15)) {
				log(LogStatus.INFO,"Item found: "+navLb+" on Tab : "+navigationTab, YesNo.No);
				npbl.clickOnShowMoreDropdownOnly(projectName);
				ele = npbl.actionDropdownElement(projectName, ShowMoreActionDropDownList.Edit, 10);
				if (click(driver, ele, ShowMoreActionDropDownList.Edit.toString(), action.BOOLEAN)) {
					log(LogStatus.INFO, "Not Able to Click on Edit Button : "+navLb, YesNo.No);
					ele = npbl.getNavigationField(projectName, CSVLabel.Navigation_Label.toString(), action.BOOLEAN, 20);
					updatedName=updatedNavigationLabel;
					if (sendKeys(driver, ele, updatedName, CSVLabel.Navigation_Label.toString(), action.BOOLEAN)) {
						log(LogStatus.INFO, "Able to enter "+CSVLabel.Navigation_Label.toString(), YesNo.No);
						ThreadSleep(2000);
						if (click(driver, npbl.getCustomTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.ERROR, "Click on save Button : "+navLb, YesNo.No);
							ThreadSleep(2000);
							if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, updatedName, CSVLabel.Navigation_Label.toString(), navLb, CSVLabel.Navigation_Label.toString())) {
								log(LogStatus.INFO, updatedName+" value has been written under "+CSVLabel.Navigation_Label.toString()+" for "+navLb, YesNo.No);
							} else {
								log(LogStatus.ERROR, updatedName+" value has not been written under "+CSVLabel.Navigation_Label.toString()+" for "+navLb, YesNo.Yes);
								sa.assertTrue(false, updatedName+" value has not been written under "+CSVLabel.Navigation_Label.toString()+" for "+navLb);
							}
							
						} else {
							log(LogStatus.ERROR, "Not Able to Click on save Button : "+navLb, YesNo.Yes);
							sa.assertTrue(false,"Not Able to Click on save Button : "+navLb);}

					} else {
						log(LogStatus.ERROR, "Not Able to enter "+CSVLabel.Navigation_Label.toString(), YesNo.Yes);
						sa.assertTrue(false,"Not Able to enter "+CSVLabel.Navigation_Label.toString());
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on InLine Edit Button : "+navLb, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on InLine Edit Button : "+navLb);
				}

			}else {

				log(LogStatus.ERROR,"Item not found: "+navLb+" on Tab : "+navigationTab+" so cannot update navigation label for "+navLb, YesNo.Yes);
				sa.assertTrue(false,"Item not found: "+navLb+" on Tab : "+navigationTab+" so cannot update navigation label for "+navLb);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on Tab : "+navigationTab+" so cannot update navigation label for "+navLb, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on Tab : "+navigationTab+" so cannot update navigation label for "+navLb);
		}
		refresh(driver);
		ThreadSleep(2000);
		updatedName=updatedNavigationLabel;
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			ele=npbl.getNavigationLabel(projectName, updatedName, action.BOOLEAN, 10);
			if (ele!=null) {
				log(LogStatus.INFO, navLb+" has been updated to : "+updatedName, YesNo.No);	
			} else {
				log(LogStatus.ERROR, navLb+" should updated to : "+updatedName, YesNo.Yes);
				sa.assertTrue(false,navLb+" should updated to : "+updatedName);

			}
			csvRecords = ExcelUtils.readAllDataFromCSVFileIntoList(NavigationMenuTestData_PEExcel, false);
			Map<String, Integer> navigationParentLabelWithOrder = navigationParentLabelWithOrder(csvRecords);
			Map<String, Integer> navigationParentLabelWithSortedOrder = sortByValue(true, navigationParentLabelWithOrder);
			Map<String, String> navigationParentLabelWithChildAndOrder = navigationParentLabelWithChildAndOrder(csvRecords);
			Map<String, String> navigationParentLabelWithChildSorted = navigationParentLabelWithChildSorted(navigationParentLabelWithChildAndOrder);

			npbl.verifyingNavigationMenuLink(projectName, navigationParentLabelWithSortedOrder, navigationParentLabelWithChildSorted, action.BOOLEAN, 0);;
			
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify update label Name : "+updatedName, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify update label Name : "+updatedName);
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc013_ChangeTheOrderOfTheLabelAndVerifyImpactOnNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		/*navigationMenuName=NavigationMenuItems.New_Interactions.toString();*/
		navigationMenuName=NavigationMenuItems.Create.toString();
		String updatedOrder="";
		String[] navigationLabels=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name).split(breakSP);
		String[] updatedOrderLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Updated_Order).split(breakSP);
		String navigationLabel="";
		String orderLabel=CSVLabel.Order.toString();
		
		for (int j = 0; j < navigationLabels.length; j++) {
			navigationLabel=navigationLabels[j];
			System.out.println(navigationLabel);
			
			if (j==4) {
				updatedOrder="";
			} else {
				updatedOrder=updatedOrderLabel[j];
			}
			
			System.out.println(updatedOrder);
			if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, new String[][]{{orderLabel,updatedOrder}}, 20)) {
				log(LogStatus.INFO, "Able to update "+orderLabel+" with value "+updatedOrder+" field for "+navigationLabels, YesNo.No);
				if (ExcelUtils.writeDataOnCSVFile(NavigationMenuTestData_PEExcel, updatedOrder, CSVLabel.Navigation_Label.toString(), navigationLabel, CSVLabel.Order.toString())) {
					log(LogStatus.INFO, updatedOrder+" value has been written under "+CSVLabel.Order.toString()+" for "+navigationLabel, YesNo.No);
				} else {
					log(LogStatus.ERROR, updatedOrder+" value has not been written under "+CSVLabel.Order.toString()+" for "+navigationLabel, YesNo.Yes);
					sa.assertTrue(false, updatedOrder+" value has not been written under "+CSVLabel.Order.toString()+" for "+navigationLabel);
				}

			}else{
				log(LogStatus.ERROR, "Not Able to update "+orderLabel+"with value "+updatedOrder+" field for "+navigationLabels, YesNo.Yes);
				sa.assertTrue(false, "Not Able to update "+orderLabel+"with value "+updatedOrder+" field for "+navigationLabels);
			}
		}
		
		switchToDefaultContent(driver);
		ThreadSleep(2000);
		refresh(driver);
		ThreadSleep(2000);
		csvRecords=ExcelUtils.readAllDataFromCSVFileIntoList(NavigationMenuTestData_PEExcel, false);
		System.err.println(csvRecords);
		if (!csvRecords.isEmpty()) {
			log(LogStatus.INFO, "Records Fetched from CSV File : "+NavigationMenuTestData_PEExcel, YesNo.No);

			Map<String, Integer> navigationParentLabelWithOrder = navigationParentLabelWithOrder(csvRecords);
			System.err.println(navigationParentLabelWithOrder);
			Map<String, Integer> navigationParentLabelWithSortedOrder = sortByValue(true, navigationParentLabelWithOrder);
			System.err.println(navigationParentLabelWithOrder);
			Map<String, String> navigationParentLabelWithChildAndOrder = navigationParentLabelWithChildAndOrder(csvRecords);
			System.err.println(navigationParentLabelWithChildAndOrder);
			Map<String, String> navigationParentLabelWithChildSorted = navigationParentLabelWithChildSorted(navigationParentLabelWithChildAndOrder);
			System.err.println(navigationParentLabelWithChildSorted);
			// Verification on navigation menu
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				npbl.verifyingNavigationMenuLink(projectName, navigationParentLabelWithSortedOrder, navigationParentLabelWithChildSorted, action.BOOLEAN, 1);;
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify order", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify order");
			}

		} else {
			log(LogStatus.FAIL, "Unable to Fetch Records from CSV File : "+NavigationMenuTestData_PEExcel, YesNo.Yes);
			sa.assertTrue(false, "Unable to Fetch Records from CSV File : "+NavigationMenuTestData_PEExcel);
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M3Tc016_AddNewNavigationItemsAndVerifyItOnNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);

		/*navigationMenuName=NavigationMenuItems.New_Interactions.toString();*/
		navigationMenuName=NavigationMenuItems.Create.toString();
		String all ="All ";
		String link="";
		String name="";
		String xpath ;
		WebElement ele;
		String tab=dashBoardTab;
		boolean flag=false;
		if (npbl.clickOnTab(projectName, tab)) {
			log(LogStatus.INFO, "Click on Tab : "+tab, YesNo.No);
			refresh(driver);
			String all1=all+tab;
			if(click(driver, npbl.getAll(projectName, tab, action.BOOLEAN, 20), all1, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on "+all1+" so going to write url for : "+tab, YesNo.No);
				ThreadSleep(5000);
				xpath = "//table/tbody//tr/th//a";
				ele = FindElement(driver, xpath, "Actual "+tab+" link", action.BOOLEAN, 20);
				if (ele!=null) {
					log(LogStatus.INFO, tab+" is found after click on "+all1 , YesNo.No);
					link= ele.getAttribute("href").trim();
					name=ele.getText().trim();
					if (link.startsWith("http")) {
						log(LogStatus.INFO, "url link found for : "+name, YesNo.No);
						flag=true;

					} else {
						log(LogStatus.ERROR, "url link not found for : "+name, YesNo.Yes);
						sa.assertTrue(false, "url link not found for : "+name);
					}
				} else {
					log(LogStatus.ERROR, tab+" is not found after click on "+all1, YesNo.Yes);
					sa.assertTrue(false, tab+" is not found after click on "+all1);
				}
				all1="";

			}else {
				log(LogStatus.ERROR, "Not Able to Click on "+all1+" so can not  write url for : "+tab, YesNo.Yes);
				sa.assertTrue(false, "Not Able to Click on "+all1+" so can not  write url for : "+tab);

			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on Tab : "+tab, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on Tab : "+tab);
		}



		String navigationLabel=CSVLabel.Navigation_Label.toString();
		String navigationLabelValue=dashBoardTab;
		String orderLabel=CSVLabel.Order.toString();
		String orderLabelValue="45";
		String urlLabel=CSVLabel.URL.toString();
		String urlValue=link;
		String navigationTypeLabel=CSVLabel.Navigation_Type.toString();
		String navigationTypeValue=navigationMenuName;

		if (flag) {
			flag=false;
			String[][] labelWithValue= {{navigationLabel,navigationLabelValue},
					{orderLabel,orderLabelValue},
					{urlLabel,urlValue},
					{navigationTypeLabel,navigationTypeValue}};

			if (npbl.createNavigationItem(projectName, labelWithValue, 20)) {
				log(LogStatus.INFO, "created "+navigationLabelValue, YesNo.No);
				flag=true;
			} else {
				log(LogStatus.ERROR, "Not Able to create "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false, "Not Able to create "+navigationLabelValue);

			}
		} else {
			log(LogStatus.ERROR, "Not Able to retrieve url from DashBoard so cannot create navigation label : "+navigationLabelValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to retrieve url from DashBoard so cannot create navigation label : "+navigationLabelValue);
		}

		if (flag) {	
			refresh(driver);
			ThreadSleep(5000);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);

				csvRecords=ExcelUtils.readAllDataFromCSVFileIntoList(NavigationMenuTestData_PEExcel, false);
				System.err.println(csvRecords);
				if (!csvRecords.isEmpty()) {
					Map<String, Integer> navigationParentLabelWithOrder = navigationParentLabelWithOrder(csvRecords);
					navigationParentLabelWithOrder.put(navigationLabelValue, Integer.parseInt(orderLabelValue));
					System.err.println(navigationParentLabelWithOrder);
					Map<String, Integer> navigationParentLabelWithSortedOrder = sortByValue(true, navigationParentLabelWithOrder);
					System.err.println(navigationParentLabelWithOrder);
					Map<String, String> navigationParentLabelWithChildAndOrder = navigationParentLabelWithChildAndOrder(csvRecords);
					System.err.println(navigationParentLabelWithChildAndOrder);
					Map<String, String> navigationParentLabelWithChildSorted = navigationParentLabelWithChildSorted(navigationParentLabelWithChildAndOrder);
					System.err.println(navigationParentLabelWithChildSorted);
					npbl.verifyingNavigationMenuLink(projectName, navigationParentLabelWithSortedOrder, navigationParentLabelWithChildSorted, action.BOOLEAN, 1);;

				} else {
					log(LogStatus.FAIL, "Unable to Fetch Records from CSV File : "+NavigationMenuTestData_PEExcel, YesNo.Yes);
					sa.assertTrue(false, "Unable to Fetch Records from CSV File : "+NavigationMenuTestData_PEExcel);
				}

				ele=npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
					ThreadSleep(5000);
					if (lp.getSelectedTab(projectName, dashBoardTab)) {
						log(LogStatus.INFO, "After Click on "+navigationLabelValue+" it is redirected to "+dashBoardTab,YesNo.Yes);

					} else {
						log(LogStatus.ERROR, "After Click on "+navigationLabelValue+"it should be redirected to "+dashBoardTab,YesNo.Yes);
						sa.assertTrue(false,"After Click on "+navigationLabelValue+"it should be redirected to "+dashBoardTab);
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot check label : "+navigationLabelValue);
			}

		} else {
			log(LogStatus.ERROR, "Not Able to Create "+navigationLabelValue+"so type : "+navigationTypeValue+" so cannot check order on "+navigationTypeValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Create "+navigationLabelValue+"so type : "+navigationTypeValue+" so cannot check order on "+navigationTypeValue);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc017_DeleteTheNavigationItemAndVerifyItOnNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		WebElement ele;
		/*navigationMenuName=NavigationMenuItems.New_Interactions.toString();*/
		navigationMenuName=NavigationMenuItems.Create.toString();
		String navigationLabel=dashBoardTab;
		String navLb;
		navLb=navigationLabel;
		boolean flag=false;
		if (npbl.clickOnTab(projectName, navigationTab)) {
			log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);

			if (npbl.clickOnAlreadyCreatedItem(projectName, navLb, true, 15)) {
				log(LogStatus.INFO,"Item found: "+navLb+" on Tab : "+navigationTab, YesNo.No);

				npbl.clickOnShowMoreDropdownOnly(projectName);
				ele = npbl.actionDropdownElement(projectName, ShowMoreActionDropDownList.Delete, 10);

				if (click(driver, ele, ShowMoreActionDropDownList.Delete.toString(), action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on delete Button : "+navLb, YesNo.No);

					if(click(driver,npbl.getDeleteButtonPopUp(projectName, 10), "delete", action.BOOLEAN)) {
						log(LogStatus.INFO,"Able to Click on delete button on delete popup : "+TabName.Object2Tab+" For : "+navLb,YesNo.No); 
						ThreadSleep(5000);
						flag=true;
					}else {
						sa.assertTrue(false,"Not Able to Select delete button for "+navLb);
						log(LogStatus.SKIP,"Not Able to Select delete button for "+navLb,YesNo.Yes);

					}
				}

				else {
					log(LogStatus.ERROR, "Not Able to Click on delete Button : "+navLb, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on delete Button : "+navLb);
				}


			}else {

				log(LogStatus.ERROR,"Item not found: "+navLb+" on Tab : "+navigationTab, YesNo.Yes);
				sa.assertTrue(false,"Item not found: "+navLb+" on Tab : "+navigationTab);
			}


		} else {
			log(LogStatus.ERROR, "Not Able to Click on Tab : "+navigationTab, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on Tab : "+navigationTab);
		}

		if (flag) {
			refresh(driver);
			ThreadSleep(5000);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ele = npbl.getNavigationLabel(projectName, navLb, action.BOOLEAN, 10);
				if (ele==null) {
					log(LogStatus.INFO, navigationLabel+" label is not present after delete", YesNo.No);	
				} else {
					log(LogStatus.ERROR, navigationLabel+" label should not present after delete", YesNo.Yes);
					sa.assertTrue(false,navigationLabel+" label should not present after delete");

				}
				
				csvRecords=ExcelUtils.readAllDataFromCSVFileIntoList(NavigationMenuTestData_PEExcel, false);
				System.err.println(csvRecords);
				if (!csvRecords.isEmpty()) {	
					Map<String, Integer> navigationParentLabelWithOrder = navigationParentLabelWithOrder(csvRecords);
					System.err.println(navigationParentLabelWithOrder);
					Map<String, Integer> navigationParentLabelWithSortedOrder = sortByValue(true, navigationParentLabelWithOrder);
					System.err.println(navigationParentLabelWithOrder);
					Map<String, String> navigationParentLabelWithChildAndOrder = navigationParentLabelWithChildAndOrder(csvRecords);
					System.err.println(navigationParentLabelWithChildAndOrder);
					Map<String, String> navigationParentLabelWithChildSorted = navigationParentLabelWithChildSorted(navigationParentLabelWithChildAndOrder);
					System.err.println(navigationParentLabelWithChildSorted);
					npbl.verifyingNavigationMenuLink(projectName, navigationParentLabelWithSortedOrder, navigationParentLabelWithChildSorted, action.BOOLEAN, 1);;
	
				} else {
					log(LogStatus.FAIL, "Unable to Fetch Records from CSV File : "+NavigationMenuTestData_PEExcel, YesNo.Yes);
					sa.assertTrue(false, "Unable to Fetch Records from CSV File : "+NavigationMenuTestData_PEExcel);
				}


				


			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify order", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify order");
			}
		} else {
			log(LogStatus.ERROR, "Not Able to  delete : "+navLb, YesNo.Yes);
			sa.assertTrue(false,"Not Able to delete  : "+navLb);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc018_1_VerifyQuickCreateObjectWhenRecordHasMultipleRecordTypes_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		WebElement ele;
		////////////////////////////////
		String[] userNames= {"PE Standard User","System Administrator"};
		String recordType="Company";
	
		for (String userName : userNames) {
			switchToDefaultContent(driver);
			if (home.clickOnSetUpLink()) {
				String parentID = switchOnWindow(driver);
				if (parentID!=null) {
					log(LogStatus.INFO, "Able to switch on new window, so going to update Navigation Label", YesNo.No);
					ThreadSleep(500);
					if(setup.searchStandardOrCustomObject(environment,mode, object.Profiles)) {
						log(LogStatus.INFO, "click on Object : "+object.Profiles, YesNo.No);
						ThreadSleep(2000);
						if (setup.changeRecordTypeSetting(driver, userName, recordType, 60 )) {
							log(LogStatus.PASS,recordType+" is selected for "+userName,YesNo.No);
						} else {
							sa.assertTrue(false, recordType+" is not selected for "+userName);
							log(LogStatus.FAIL,recordType+" is not selected for "+userName,YesNo.Yes);
						}
					}else {
						log(LogStatus.ERROR, "Not able to search/click on "+object.Profiles, YesNo.Yes);
						sa.assertTrue(false, "Not able to search/click on "+object.Profiles);
					}
					driver.close();
					driver.switchTo().window(parentID);
				}else {
					log(LogStatus.FAIL, "could not find new window to switch, so cannot select "+recordType, YesNo.Yes);
					sa.assertTrue(false, "could not find new window to switch, so cannot select "+recordType);
				}

			}else {
				log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link");	
			}
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc018_2_VerifyQuickCreateObjectWhenRecordHasMultipleRecordTypes_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		if (lp.clickOnTab(projectName,tabObj1)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj1,YesNo.No);
			if (click(driver, ip.getNewButton(projectName, 90) ,"new button",action.BOOLEAN)) {
				log(LogStatus.SKIP,"Click on New Button",YesNo.Yes);
				String recordType="Company";
				ele=npbl.getRadioButtonforRecordType(recordType, 30);
				if (ele!=null) {
					log(LogStatus.INFO, recordType+" radio button is present", YesNo.No);
					if (isSelected(driver, ele, recordType+" radio button")) {
						log(LogStatus.INFO, recordType+" radio button is selected by default", YesNo.No);	
					} else {
						log(LogStatus.ERROR, recordType+" radio button is not selected by default", YesNo.Yes);
						sa.assertTrue(false, recordType+" radio button is not selected by default");
					}

				} else {
					log(LogStatus.ERROR, recordType+" radio button not present", YesNo.Yes);
					sa.assertTrue(false, recordType+" radio button not present");
				}
				String expecedHeader="New Firm";
				String actualHeader="";
				ele=npbl.getPopUpHeader1(projectName, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "PopUp is open" , YesNo.No);
					actualHeader=ele.getText().trim();
					if (actualHeader.equals(expecedHeader)) {
						log(LogStatus.INFO, "Header Text verified : "+expecedHeader, YesNo.Yes);

					} else {
						log(LogStatus.ERROR, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader, YesNo.Yes);
						sa.assertTrue(false, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader);
					}

				} else {
					log(LogStatus.ERROR, "No PopUp is open so cannot verify Heading "+expecedHeader, YesNo.Yes);
					sa.assertTrue(false, "No PopUp is open so cannot verify Heading "+expecedHeader);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on New Button");
				log(LogStatus.SKIP,"Not Able to Click on New Button",YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj1);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj1,YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc019_CreateRecordTypeFundAndFundOfFundsForFundObjectAndAddTheFromTheProfiles(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		
		lp.CRMLogin(superAdminUserName, adminPassword);
		String recordTypeList = ExcelUtils.readData(phase1DataSheetFilePath, "FilePath", excelLabel.TestCases_Name,
				currentlyExecutingTC, excelLabel.Record_Type);
		String recordTypeArray[] = recordTypeList.split(breakSP);
		String[][] fundRecordType = { { recordTypeLabel.Record_Type_Label.toString(), recordTypeArray[0] },
				{ recordTypeLabel.Description.toString(), recordTypeArray[0] + recordTypeDescription },
				{ recordTypeLabel.Active.toString(), "" } };

		String[][] ffrecordType = { { recordTypeLabel.Record_Type_Label.toString(), recordTypeArray[1] },
				{ recordTypeLabel.Description.toString(), recordTypeArray[1] + recordTypeDescription },
				{ recordTypeLabel.Active.toString(), "" } };
		String[] profileForSelection= {"PE Standard User","System Administrator"};
		boolean isMakeAvailable = true;
		boolean isMakeDefault = true;
		boolean flag = false;
		for (int i = 0; i < recordTypeArray.length; i++) {
			if (home.clickOnSetUpLink()) {
				flag = false;
				String parentID = switchOnWindow(driver);
				SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
				if (parentID != null) {
					if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(), object.Fund)) {
						if (sp.clickOnObjectFeature("", Mode.Lightning.toString(), object.Fund,
								ObjectFeatureName.recordTypes)) {
							if (i == 0) {
								flag = sp.createRecordTypeForObject(projectName, fundRecordType, isMakeAvailable,
										profileForSelection, isMakeDefault, null, 10);
							} else {
								isMakeDefault = false;
								flag = sp.createRecordTypeForObject(projectName, ffrecordType, isMakeAvailable,
										profileForSelection, isMakeDefault, null, 10);
							}
							if (flag) {
								log(LogStatus.ERROR, "Created Record Type : " + recordTypeArray[i], YesNo.No);
							} else {
								log(LogStatus.ERROR, "Not Able to Create Record Type : " + recordTypeArray[i],
										YesNo.Yes);
								sa.assertTrue(false, "Not Able to Create Record Type : " + recordTypeArray[i]);
							}

						} else {
							log(LogStatus.ERROR,
									"object feature " + ObjectFeatureName.recordTypes + " is not clickable", YesNo.Yes);
							sa.assertTrue(false,
									"object feature " + ObjectFeatureName.recordTypes + " is not clickable");
						}
					} else {
						log(LogStatus.ERROR, "Fund object could not be found in object manager", YesNo.Yes);
						sa.assertTrue(false, "Fund object could not be found in object manager");
					}
					driver.close();
					driver.switchTo().window(parentID);
					switchToDefaultContent(driver);
					refresh(driver);
				} else {
					log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
					sa.assertTrue(false, "could not find new window to switch");
				}
			} else {
				log(LogStatus.ERROR, "could not click on setup link", YesNo.Yes);
				sa.assertTrue(false, "could not click on setup link");
			}

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
		@Parameters({ "projectName"})
	@Test
	public void M3Tc020_VerifyQuickCreateObjectWhenRecordHasMultipleRecordTypes(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		/*navigationMenuName=NavigationMenuItems.New_Interactions.toString();*/
		navigationMenuName=NavigationMenuItems.Create.toString();
		String actionObjectLabel=CSVLabel.Action_Object.toString();
		String actionObjectLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Action_Object);;
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		String[][] labelWithValue= {{actionObjectLabel,actionObjectLabelValue}};

		String dependentTC="M3Tc019_CreateRecordTypeFundAndFundOfFundsForFundObjectAndAddTheFromTheProfiles";
		String recordTypeList=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Record_Type);
		String recordTypeArray[] =recordTypeList.split(breakSP);
		boolean flag=false;
		//Verify Quick Create Object when a record has multiple record types

		if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, labelWithValue, 20)) {
			log(LogStatus.INFO, actionObjectLabelValue+" value has been updated & saved under "+actionObjectLabel+" for "+navigationLabel, YesNo.No);
			flag=true;
			
		}else{
			log(LogStatus.ERROR, "Not Able to enter value on" +actionObjectLabel+" for "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false, "Not Able to enter value on" +actionObjectLabel+" for "+navigationLabel);
		}
		if (flag) {
			refresh(driver);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabel, YesNo.No);
					ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 120);
					if (ele!=null) {
						log(LogStatus.INFO, "Pop Up open after clicking on "+navigationLabel , YesNo.No);
						for (int i = 0; i < recordTypeArray.length; i++) {
							ele=npbl.getRadioButtonforRecordTypeAtAccount(recordTypeArray[i], 5);
							if (ele!=null) {
								log(LogStatus.INFO, recordTypeArray[i]+" radio button is present", YesNo.No);
								if (i==0) {
									if (isSelected(driver, ele, recordTypeArray[i]+" radio button")) {
										log(LogStatus.INFO, recordTypeArray[i]+" radio button is selected by default", YesNo.No);	
									} else {
										log(LogStatus.ERROR, recordTypeArray[i]+" radio button is not selected by default", YesNo.Yes);
										sa.assertTrue(false, recordTypeArray[i]+" radio button is not selected by default");
									}
								}
								
								ele=npbl.getrecordTypeWithDescription(recordTypeArray[i], recordTypeArray[i]+recordTypeDescription, 10);
								if (ele!=null) {
									log(LogStatus.INFO, "Record Type & description verified : "+recordTypeArray[i]+recordTypeDescription , YesNo.No);

								} else {
									log(LogStatus.ERROR, "Record Type & description not verified : "+recordTypeArray[i]+recordTypeDescription, YesNo.Yes);
									sa.assertTrue(false, "Record Type & description not verified : "+recordTypeArray[i]+recordTypeDescription);
								}
								
							} else {
								log(LogStatus.ERROR, recordTypeArray[i]+" radio button not present", YesNo.Yes);
								sa.assertTrue(false, recordTypeArray[i]+" radio button not present");
							}

						}
						
						ele=npbl.getnavigationPopUpHeader(projectName, 10);
						String expecedHeader="New Fund";;
						if (ele!=null) {
							log(LogStatus.INFO, "PopUp is open" , YesNo.No);
							String actualHeader = ele.getText().trim();
							if (ele.getText().trim().equals(expecedHeader)) {
								log(LogStatus.INFO, "Header Text verified : "+expecedHeader, YesNo.Yes);
								
							} else {
								log(LogStatus.ERROR, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader, YesNo.Yes);
								sa.assertTrue(false, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader);
							}

						} else {
							log(LogStatus.ERROR, "No PopUp is open so cannoy verify Heading "+expecedHeader, YesNo.Yes);
							sa.assertTrue(false, "No PopUp is open so cannoy verify Heading "+expecedHeader);
						}
						
						ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
						if (click(driver, ele, navigationLabel+" pop up cross button", action.BOOLEAN)) {
							log(LogStatus.INFO, "click on cross button "+navigationLabel , YesNo.No);
						} else {
							log(LogStatus.ERROR, "Not Able to click on cross button "+navigationLabel, YesNo.Yes);
							sa.assertTrue(false, "Not Able to click on cross button "+navigationLabel);
						}
					} else {
						log(LogStatus.ERROR, "No Pop Up is open after clicking on "+navigationLabel, YesNo.Yes);
						sa.assertTrue(false, "No Pop Up is open after clicking on "+navigationLabel);
					}
			
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabel);
				}
				npbl.clickOnNavatarEdgeLink(projectName, navigationMenuName, action.BOOLEAN, 5);
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on label : "+navigationLabel, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on label : "+navigationLabel);

			}

			refresh(driver);
			if (lp.clickOnTab(projectName,tabObj3)) {
				log(LogStatus.INFO,"Click on Tab : "+tabObj1,YesNo.No);	
				if (click(driver, lp.getNewButton(projectName, 90), "new button",action.BOOLEAN)) {
					log(LogStatus.SKIP,"Click on New Button",YesNo.Yes);
					for (int i = 0; i < recordTypeArray.length; i++) {
						ele=npbl.getRadioButtonforRecordTypeAtAccount(recordTypeArray[i], 30);
						if (ele!=null) {
							log(LogStatus.ERROR, recordTypeArray[i]+" radio button is present", YesNo.No);
							if (i==0) {
								if (isSelected(driver, ele, recordTypeArray[i]+" radio button")) {
									log(LogStatus.INFO, recordTypeArray[i]+" radio button is selected by default", YesNo.No);	
								} else {
									log(LogStatus.ERROR, recordTypeArray[i]+" radio button is not selected by default", YesNo.Yes);
									sa.assertTrue(false, recordTypeArray[i]+" radio button is not selected by default");
								}
							}
							ele=npbl.getrecordTypeWithDescription(recordTypeArray[i], recordTypeArray[i]+recordTypeDescription, 10);
							if (ele!=null) {
								log(LogStatus.INFO, "Record Type & description verified : "+recordTypeArray[i]+recordTypeDescription , YesNo.No);

							} else {
								log(LogStatus.ERROR, "Record Type & description not verified : "+recordTypeArray[i]+recordTypeDescription, YesNo.Yes);
								sa.assertTrue(false, "Record Type & description not verified : "+recordTypeArray[i]+recordTypeDescription);
							}
							
						} else {
							log(LogStatus.ERROR, recordTypeArray[i]+" radio button not present", YesNo.Yes);
							sa.assertTrue(false, recordTypeArray[i]+" radio button not present");
						}

					}
					String expecedHeader="New Fund";
					String actualHeader="";
					ele=npbl.getPopUpHeader(projectName, 10);
					if (ele!=null) {
						log(LogStatus.INFO, "PopUp is open" , YesNo.No);
						actualHeader=ele.getText().trim();
						if (actualHeader.equals(expecedHeader)) {
							log(LogStatus.INFO, "Header Text verified : "+expecedHeader, YesNo.Yes);

						} else {
							log(LogStatus.ERROR, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader, YesNo.Yes);
							sa.assertTrue(false, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader);
						}

					} else {
						log(LogStatus.ERROR, "No PopUp is open so cannot verify Heading "+expecedHeader, YesNo.Yes);
						sa.assertTrue(false, "No PopUp is open so cannot verify Heading "+expecedHeader);
					}


				} else {
					sa.assertTrue(false,"Not Able to Click on New Button");
					log(LogStatus.SKIP,"Not Able to Click on New Button",YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj3);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj3,YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to enter value on" +actionObjectLabel+" for "+navigationLabel+" so cannot check on Navigation Menu", YesNo.Yes);
			sa.assertTrue(false, "Not Able to enter value on" +actionObjectLabel+" for "+navigationLabel+" so cannot check on Navigation Menu");
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc021_1_UpdateTheRecordTypeAndDescriptionImpactOnNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		

		String dependentTC="M3Tc019_CreateRecordTypeFundAndFundOfFundsForFundObjectAndAddTheFromTheProfiles";
		String recordTypeList=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Record_Type);
		String recordTypeArray[] =recordTypeList.split(breakSP);
		
		String[][] fundRecordType = {{recordTypeLabel.Record_Type_Label.toString(),upDated+recordTypeArray[0]},
				{recordTypeLabel.Description.toString(),upDated+recordTypeArray[0]+recordTypeDescription}};
		
		
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
	
		/////////////////////////////////////////////////////
		for (int i = 0; i < 1; i++) {
			if (home.clickOnSetUpLink()) {
				String parentID = switchOnWindow(driver);
				SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
				if (parentID!=null) {
					if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(),object.Fund )) {
						if(sp.clickOnObjectFeature("", Mode.Lightning.toString(),object.Fund, ObjectFeatureName.recordTypes)) {
							if (sp.clickOnAlreadyCreatedLayout(recordTypeArray[i])) {
								if (sp.editRecordTypeForObject(projectName, fundRecordType, 10)) {
									log(LogStatus.ERROR,recordTypeArray[i]+" has been updated ",YesNo.Yes);	
								}else {
									log(LogStatus.ERROR,recordTypeArray[i]+" not updated ",YesNo.Yes);
									sa.assertTrue(false, recordTypeArray[i]+" not updated ");
								}
							
							}else {
								log(LogStatus.ERROR, recordTypeArray[i]+" is not clickable", YesNo.Yes);
								sa.assertTrue(false, recordTypeArray[i]+" is not clickable");
							}
					
						}else {
							log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
						}
					}else {
						log(LogStatus.ERROR, "Fund object could not be found in object manager", YesNo.Yes);
						sa.assertTrue(false, "Fund object could not be found in object manager");
					}
					driver.close();
					driver.switchTo().window(parentID);
					switchToDefaultContent(driver);
				}else {
					log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
					sa.assertTrue(false, "could not find new window to switch");
				}
			}else {
				log(LogStatus.ERROR, "could not click on setup link", YesNo.Yes);
				sa.assertTrue(false, "could not click on setup link");
			}

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc021_2_UpdateTheRecordTypeAndDescriptionImpactOnNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		/*navigationMenuName=NavigationMenuItems.New_Interactions.toString();*/
		navigationMenuName=NavigationMenuItems.Create.toString();
		String dependentTC="M3Tc019_CreateRecordTypeFundAndFundOfFundsForFundObjectAndAddTheFromTheProfiles";
		String recordTypeList=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Record_Type);
		String recordTypeArray[] =recordTypeList.split(breakSP);
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);

		//Verify Quick Create Object when a record has multiple record types


		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 10);
			if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on "+navigationLabel, YesNo.No);
				ThreadSleep(5000);
				ThreadSleep(5000);
				ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
				if (ele!=null) {
					log(LogStatus.INFO, "Pop Up open after clicking on "+navigationLabel , YesNo.No);
					ele=npbl.getrecordTypeWithDescription(upDated+recordTypeArray[0], upDated+recordTypeArray[0]+recordTypeDescription, 10);
					if (ele!=null) {
						log(LogStatus.INFO, "Record Type & description Updated & verified" , YesNo.No);

					} else {
						log(LogStatus.ERROR, "Record Type & description not Updated & not verified "+upDated+recordTypeArray[0] +" "+ upDated+recordTypeArray[0]+recordTypeDescription, YesNo.Yes);
						sa.assertTrue(false, "Record Type & description not Updated & not verified "+upDated+recordTypeArray[0] +" "+ upDated+recordTypeArray[0]+recordTypeDescription);
					}
				} else {
					log(LogStatus.ERROR, "No Pop Up is open after clicking on "+navigationLabel, YesNo.Yes);
					sa.assertTrue(false, "No Pop Up is open after clicking on "+navigationLabel);
				}

				String expecedHeader="New Fund";
				String actualHeader="";
				ele=npbl.getPopUpHeader(projectName, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "PopUp is open" , YesNo.No);
					actualHeader=ele.getText().trim();
					if (actualHeader.equals(expecedHeader)) {
						log(LogStatus.INFO, "Header Text verified : "+expecedHeader, YesNo.Yes);

					} else {
						log(LogStatus.ERROR, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader, YesNo.Yes);
						sa.assertTrue(false, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader);
					}

				} else {
					log(LogStatus.ERROR, "No PopUp is open so cannot verify Heading "+expecedHeader, YesNo.Yes);
					sa.assertTrue(false, "No PopUp is open so cannot verify Heading "+expecedHeader);
				}

				ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 5);
				click(driver, ele, "Cross Icon", action.BOOLEAN);
				ThreadSleep(3000);

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationLabel);
			}

		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on label : "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on label : "+navigationLabel);

		}


		if (lp.clickOnTab(projectName,tabObj3)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj3,YesNo.No);	
			if (click(driver, lp.getNewButton(projectName, 90), "new button",action.BOOLEAN)) {
				log(LogStatus.INFO,"Click on New Button",YesNo.Yes);
				ele=npbl.getrecordTypeWithDescription(upDated+recordTypeArray[0], upDated+recordTypeArray[0]+recordTypeDescription, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "Record Type & description Updated & verified" , YesNo.No);

				} else {
					log(LogStatus.ERROR, "Record Type & description not Updated & not verified "+upDated+recordTypeArray[0] +" "+ upDated+recordTypeArray[0]+recordTypeDescription, YesNo.Yes);
					sa.assertTrue(false, "Record Type & description not Updated & not verified "+upDated+recordTypeArray[0] +" "+ upDated+recordTypeArray[0]+recordTypeDescription);
				}

				String expecedHeader="New Fund";
				String actualHeader="";
				ele=npbl.getPopUpHeader(projectName, 10);
				if (ele!=null) {
					log(LogStatus.INFO, "PopUp is open" , YesNo.No);
					actualHeader=ele.getText().trim();
					if (actualHeader.equals(expecedHeader)) {
						log(LogStatus.INFO, "Header Text verified : "+expecedHeader, YesNo.Yes);

					} else {
						log(LogStatus.ERROR, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader, YesNo.Yes);
						sa.assertTrue(false, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader);
					}

				} else {
					log(LogStatus.ERROR, "No PopUp is open so cannot verify Heading "+expecedHeader, YesNo.Yes);
					sa.assertTrue(false, "No PopUp is open so cannot verify Heading "+expecedHeader);
				}
				ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 5);
				click(driver, ele, "Cross Icon", action.BOOLEAN);
				ThreadSleep(3000);	

			} else {
				sa.assertTrue(false,"Not Able to Click on New Button");
				log(LogStatus.SKIP,"Not Able to Click on New Button",YesNo.Yes);
			}


		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj3);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj3,YesNo.Yes);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc022_1_CreateACustomObject(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		String customObject=tabCustomObj+"s";
		/////////////////////////////////////////////////////
		
		boolean flag = false;
		if (home.clickOnSetUpLink()) {
			flag = false;
			String parentID = switchOnWindow(driver);
			if (parentID != null) {
				if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(), object.Create)) {
					log(LogStatus.INFO, "Click on Create/Custom object so going to create : " + customObject, YesNo.No);
					String[][] labelWithValue = { { customObjectLabel.Label.toString(), tabCustomObj },
							{ customObjectLabel.Plural_Label.toString(), customObject } };
					if (sp.createCustomObject(projectName, labelWithValue, 10)) {
						log(LogStatus.INFO, "Custom Object Created : " + customObject, YesNo.No);
						flag = true;
					} else {
						log(LogStatus.ERROR, "Not Able to Create : " + customObject, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Create : " + customObject);
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on Create/Custom object so cannot create : " + customObject,
							YesNo.Yes);
					sa.assertTrue(false,
							"Not Able to Click on Create/Custom object so cannot create : " + customObject);
				}
				driver.close();
				driver.switchTo().window(parentID);
			} else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		} else {
			log(LogStatus.ERROR, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}

		switchToDefaultContent(driver);
		refresh(driver);
		ThreadSleep(5000);
		if (flag) {
			switchToDefaultContent(driver);
			refresh(driver);
			ThreadSleep(5000);
			if (home.clickOnSetUpLink()) {
				flag=false;
				String parentID = switchOnWindow(driver);
				if (parentID!=null) {
					
					if(sp.addObjectToTab(environment, mode, projectName, object.Tabs, tabCustomObj, "Bell",parentID)) {
						log(LogStatus.PASS, customObject+" added to Tab", YesNo.No);
						flag=true;
					}else {
						log(LogStatus.PASS, customObject+" not added to Tab", YesNo.Yes);
						sa.assertTrue(false, customObject+" not added to Tab");
					} 
					driver.close();
					driver.switchTo().window(parentID);
				}else {
					log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
					sa.assertTrue(false, "could not find new window to switch");
				}
			}else {
				log(LogStatus.ERROR, "could not click on setup link so cannot add custom object to Tab", YesNo.Yes);
				sa.assertTrue(false, "could not click on setup link so cannot add custom object to Tab");
			}
			
		}else{
			log(LogStatus.ERROR, "Not Able to create : "+customObject+" so cannot add custom object to Tab", YesNo.Yes);
			sa.assertTrue(false, "Not Able to create : "+customObject+" so cannot add custom object to Tab");
		}
		
		String[] userNames= {"PE Standard User"};
		String onObject=customObject;
		String permission="Create";
		for (String userName : userNames) {
			switchToDefaultContent(driver);
			if (home.clickOnSetUpLink()) {
				String parentID = switchOnWindow(driver);
				if (parentID!=null) {
					log(LogStatus.INFO, "Able to switch on new window, so going to set"+permission+" for "+onObject, YesNo.No);
					ThreadSleep(500);
					if(setup.searchStandardOrCustomObject(environment,mode, object.Profiles)) {
						log(LogStatus.INFO, "click on Object : "+object.Profiles, YesNo.No);
						ThreadSleep(2000);
						if (setup.permissionChangeForUserONObject(driver, userName, new String[][]{{onObject,permission},{onObject,"Delete"}}, 20)) {
							log(LogStatus.PASS,permission+ " permission change for "+userName+" on object "+onObject,YesNo.No);
						} else {
							sa.assertTrue(false, permission+ " permission not change for "+userName+" on object "+onObject);
							log(LogStatus.FAIL,permission+ " permission not change for "+userName+" on object "+onObject,YesNo.Yes);
						}
					}else {
						log(LogStatus.ERROR, "Not able to search/click on "+object.Profiles, YesNo.Yes);
						sa.assertTrue(false, "Not able to search/click on "+object.Profiles);
					}
					driver.close();
					driver.switchTo().window(parentID);
				}else {
					log(LogStatus.FAIL, "could not find new window to switch, so cannot to set"+permission+" for "+onObject, YesNo.Yes);
					sa.assertTrue(false, "could not find new window to switch, to set"+permission+" for "+onObject);
				}

			}else {
				log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link");	
			}
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc022_2_AddCustomObectInNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		boolean flag=false;
		/*navigationMenuName=NavigationMenuItems.New_Interactions.toString();*/
		navigationMenuName=NavigationMenuItems.Create.toString();
		 
		String navigationLabel=CSVLabel.Navigation_Label.toString();
		String navigationLabelValue=tabCustomObj;
		String orderLabel=CSVLabel.Order.toString();
		String orderLabelValue="49";
		String actionObjectLabel=CSVLabel.Action_Object.toString();
		String actionObjecValue=tabCustomObjAPIName;
		
		String navigationTypeLabel=CSVLabel.Navigation_Type.toString();
		String navigationTypeValue=navigationMenuName;

		
		String[][] labelWithValue= {{navigationLabel,navigationLabelValue},{orderLabel,orderLabelValue},
				{actionObjectLabel,actionObjecValue},
				{navigationTypeLabel,navigationTypeValue}};
		WebElement ele;
		if (npbl.createNavigationItem(projectName, labelWithValue, 20)) {
			log(LogStatus.INFO, "created "+navigationLabelValue, YesNo.No);
			flag=true;
			
		} else {
			log(LogStatus.ERROR, "Not Able to create "+navigationLabelValue, YesNo.Yes);
			sa.assertTrue(false, "Not Able to create "+navigationLabelValue);

		}
		if (flag) {
			
			if (npbl.clickOnTab(projectName, navigationTab)) {
				log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);
				if (npbl.CheckAlreadyCreatedItem(projectName, navigationLabelValue, true, 15)) {
						log(LogStatus.INFO,"Item found: "+navigationLabelValue+" on Tab : "+navigationTab, YesNo.No);
					}else {
						log(LogStatus.ERROR,"Item not found: "+navigationLabelValue+" on Tab : "+navigationTab, YesNo.Yes);
						sa.assertTrue(false,"Item not found: "+navigationLabelValue+" on Tab : "+navigationTab);
					}
				

			} else {
				log(LogStatus.ERROR, "Not Able to Click on Tab : "+navigationTab, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on Tab : "+navigationTab);
			}
			
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ele=npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 60);
				if (ele!=null) {
					log(LogStatus.INFO, navigationLabelValue+" is present on "+navigationMenuName, YesNo.No);
				} else {
					log(LogStatus.ERROR, navigationLabelValue+" is not present on "+navigationMenuName, YesNo.Yes);
					sa.assertTrue(false,navigationLabelValue+" is not present on "+navigationMenuName);

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot check label : "+navigationLabelValue);
			}
			
		} else {
			log(LogStatus.ERROR, "Not Able to create "+navigationLabelValue+" so cannot check on "+navigationMenuName, YesNo.Yes);
			sa.assertTrue(false, "Not Able to create "+navigationLabelValue+" so cannot check on "+navigationMenuName);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc023_CreateRecordTypeForCustomObjectAndAddTheFromTheProfiles(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		String recordTypeList=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Record_Type);
		String recordTypeArray[] =recordTypeList.split(breakSP);
		String[][] fundRecordType = {{recordTypeLabel.Record_Type_Label.toString(),recordTypeArray[0]},
				{recordTypeLabel.Description.toString(),recordTypeArray[0]+recordTypeDescription},
				{recordTypeLabel.Active.toString(),""}};

		String[][] ffrecordType = {{recordTypeLabel.Record_Type_Label.toString(),recordTypeArray[1]},
				{recordTypeLabel.Description.toString(),recordTypeArray[1]+recordTypeDescription},
				{recordTypeLabel.Active.toString(),""}};

		boolean isMakeAvailable=true;
		boolean isMakeDefault =true;
		boolean flag=false;
		for (int i = 0; i < recordTypeArray.length; i++) {
			if (home.clickOnSetUpLink()) {
				flag=false;
				String parentID = switchOnWindow(driver);
				SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
				if (parentID!=null) {
					if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(),object.Test_Custom_Object )) {
						if(sp.clickOnObjectFeature("", Mode.Lightning.toString(),object.Test_Custom_Object, ObjectFeatureName.recordTypes)) {
							if (i==0) {
								flag=sp.createRecordTypeForObject(projectName, fundRecordType, isMakeAvailable, null,isMakeDefault, null, 10);	
							} else {
								isMakeDefault=false;
								flag=sp.createRecordTypeForObject(projectName, ffrecordType, isMakeAvailable, null,isMakeDefault, null, 10);
							}
							if (flag) {
								log(LogStatus.ERROR, "Created Record Type : "+recordTypeArray[i], YesNo.No);
							} else {
								log(LogStatus.ERROR, "Not Able to Create Record Type : "+recordTypeArray[i], YesNo.Yes);
								sa.assertTrue(false,"Not Able to Create Record Type : "+recordTypeArray[i]);
							}

						}else {
							log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
						}
					}else {
						log(LogStatus.ERROR, tabCustomObj+" could not be found in object manager", YesNo.Yes);
						sa.assertTrue(false, tabCustomObj+" could not be found in object manager");
					}
					driver.close();
					driver.switchTo().window(parentID);
					switchToDefaultContent(driver);
					refresh(driver);
				}else {
					log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
					sa.assertTrue(false, "could not find new window to switch");
				}
			}else {
				log(LogStatus.ERROR, "could not click on setup link", YesNo.Yes);
				sa.assertTrue(false, "could not click on setup link");
			}

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc024_VerifyQuickCreateObjectForCustomObjectWhenRecordHasMultipleRecordTypes(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		/*navigationMenuName=NavigationMenuItems.New_Interactions.toString();*/
		navigationMenuName=NavigationMenuItems.Create.toString();
		String actionObjectLabel=CSVLabel.Action_Object.toString();
		String actionObjectLabelValue=tabCustomObjAPIName;
		String navigationLabel=tabCustomObj;

		String[][] labelWithValue= {{actionObjectLabel,actionObjectLabelValue}};
		boolean flag=false;

		String dependentTC="M3Tc023_CreateRecordTypeForCustomObjectAndAddTheFromTheProfiles";
		String recordTypeList=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Record_Type);
		String recordTypeArray[] =recordTypeList.split(breakSP);

		//Verify Quick Create Object when a record has multiple record types

		if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, navigationLabel, labelWithValue, 20)) {
			log(LogStatus.INFO, actionObjectLabelValue+" value has been updated & saved under "+actionObjectLabel+" for "+navigationLabel, YesNo.No);
			flag=true;
		}else{

			log(LogStatus.ERROR, "Not Able to enter value on" +actionObjectLabel+" for "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false, "Not Able to enter value on" +actionObjectLabel+" for "+navigationLabel);
		}
		
		if (flag) {
			
			if (lp.clickOnTab(projectName,tabCustomObj)) {
				log(LogStatus.INFO,"Click on Tab : "+tabCustomObj,YesNo.No);	
				if (click(driver, lp.getNewButton(projectName, 90), "new button",action.BOOLEAN)) {
					log(LogStatus.SKIP,"Click on New Button",YesNo.Yes);
					for (int i = 0; i < recordTypeArray.length; i++) {
						ele=npbl.getRadioButtonforRecordTypeAtAccount(recordTypeArray[i], 30);
						if (ele!=null) {
							log(LogStatus.ERROR, recordTypeArray[i]+" radio button is present", YesNo.No);
							if (i==0) {
								if (isSelected(driver, ele, recordTypeArray[i]+" radio button")) {
									log(LogStatus.INFO, recordTypeArray[i]+" radio button is selected by default", YesNo.No);	
								} else {
									log(LogStatus.ERROR, recordTypeArray[i]+" radio button is not selected by default", YesNo.Yes);
									sa.assertTrue(false, recordTypeArray[i]+" radio button is not selected by default");
								}
							}

							ele=npbl.getrecordTypeWithDescription(recordTypeArray[i], recordTypeArray[i]+recordTypeDescription, 10);
							if (ele!=null) {
								log(LogStatus.INFO, "Record Type & description verified : "+recordTypeArray[i]+recordTypeDescription , YesNo.No);

							} else {
								log(LogStatus.ERROR, "Record Type & description not verified : "+recordTypeArray[i]+recordTypeDescription, YesNo.Yes);
								sa.assertTrue(false, "Record Type & description not verified : "+recordTypeArray[i]+recordTypeDescription);
							}

							String expecedHeader="New "+tabCustomObj;
							String actualHeader="";
							ele=npbl.getPopUpHeader(projectName, 10);
							if (ele!=null) {
								log(LogStatus.INFO, "PopUp is open" , YesNo.No);
								actualHeader=ele.getText().trim();
								if (actualHeader.equals(expecedHeader)) {
									log(LogStatus.INFO, "Header Text verified : "+expecedHeader, YesNo.Yes);

								} else {
									log(LogStatus.ERROR, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader, YesNo.Yes);
									sa.assertTrue(false, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader);
								}

							} else {
								log(LogStatus.ERROR, "No PopUp is open so cannot verify Heading "+expecedHeader, YesNo.Yes);
								sa.assertTrue(false, "No PopUp is open so cannot verify Heading "+expecedHeader);
							}

						} else {
							log(LogStatus.ERROR, recordTypeArray[i]+" radio button not present", YesNo.Yes);
							sa.assertTrue(false, recordTypeArray[i]+" radio button not present");
						}

					}

					ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 5);
					click(driver, ele, navigationLabel+" pop up cross button", action.BOOLEAN);

				} else {
					sa.assertTrue(false,"Not Able to Click on New Button");
					log(LogStatus.SKIP,"Not Able to Click on New Button",YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabCustomObj);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabCustomObj,YesNo.Yes);
			}
			
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 60)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 60);
				if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabel, YesNo.No);
					log(LogStatus.INFO, "Pop Up open after clicking on "+navigationLabel , YesNo.No);
						for (int i = 0; i < recordTypeArray.length; i++) {
							ele=npbl.getRadioButtonforRecordTypeAtAccount(recordTypeArray[i], 20);
							if (ele!=null) {
								log(LogStatus.INFO, recordTypeArray[i]+" radio button is present", YesNo.No);
								if (i==0) {
									if (isSelected(driver, ele, recordTypeArray[i]+" radio button")) {
										log(LogStatus.INFO, recordTypeArray[i]+" radio button is selected by default", YesNo.No);	
									} else {
										log(LogStatus.ERROR, recordTypeArray[i]+" radio button is not selected by default", YesNo.Yes);
										sa.assertTrue(false, recordTypeArray[i]+" radio button is not selected by default");
									}
								}

								ele=npbl.getrecordTypeWithDescription(recordTypeArray[i], recordTypeArray[i]+recordTypeDescription, 10);
								if (ele!=null) {
									log(LogStatus.INFO, "Record Type & description verified : "+recordTypeArray[i]+recordTypeDescription , YesNo.No);

								} else {
									log(LogStatus.ERROR, "Record Type & description not verified : "+recordTypeArray[i]+recordTypeDescription, YesNo.Yes);
									sa.assertTrue(false, "Record Type & description not verified : "+recordTypeArray[i]+recordTypeDescription);
								}

							} else {
								log(LogStatus.ERROR, recordTypeArray[i]+" radio button not present", YesNo.Yes);
								sa.assertTrue(false, recordTypeArray[i]+" radio button not present");
							}

						}

						String expecedHeader="New "+tabCustomObj;
						String actualHeader="";
						ele=npbl.getPopUpHeader(projectName, 10);
						if (ele!=null) {
							log(LogStatus.INFO, "PopUp is open" , YesNo.No);
							actualHeader=ele.getText().trim();
							if (actualHeader.equals(expecedHeader)) {
								log(LogStatus.INFO, "Header Text verified : "+expecedHeader, YesNo.Yes);

							} else {
								log(LogStatus.ERROR, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader, YesNo.Yes);
								sa.assertTrue(false, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader);
							}

						} else {
							log(LogStatus.ERROR, "No PopUp is open so cannot verify Heading "+expecedHeader, YesNo.Yes);
							sa.assertTrue(false, "No PopUp is open so cannot verify Heading "+expecedHeader);
						}

						ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
						click(driver, ele, navigationLabel+" pop up cross button", action.BOOLEAN);
					

				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabel);
				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on label : "+navigationLabel, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on label : "+navigationLabel);

			}
		} else {
			log(LogStatus.ERROR, "Not Able to enter value on" +actionObjectLabel+" for "+navigationLabel+" so cannot check on navigation menu and tab", YesNo.Yes);
			sa.assertTrue(false, "Not Able to enter value on" +actionObjectLabel+" for "+navigationLabel+" so cannot check on navigation menu and tab");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc025_VerifyCustomObjectInNavigationMenuwhenOneRecordTypeHasDeactivated(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);

		WebElement ele;
	/*	navigationMenuName=NavigationMenuItems.New_Interactions.toString();*/
		navigationMenuName=NavigationMenuItems.Create.toString();
		String dependentTC="M3Tc023_CreateRecordTypeForCustomObjectAndAddTheFromTheProfiles";
		String recordTypeList=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Record_Type);
		String recordTypeArray[] =recordTypeList.split(breakSP);
		String recordTypeName="";
		String navigationLabel=tabCustomObj;
		/////////////////////////////////////////////////////
		boolean flag=false;
		int i=0;
		for (i = recordTypeArray.length-1; i > 0; i--) {
			if (home.clickOnSetUpLink()) {
				flag=false;
				String parentID = switchOnWindow(driver);
				SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
				if (parentID!=null) {
					if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(),object.Test_Custom_Object )) {
						if(sp.clickOnObjectFeature("", Mode.Lightning.toString(),object.Test_Custom_Object, ObjectFeatureName.recordTypes)) {
							if (sp.clickOnAlreadyCreatedLayout(recordTypeArray[i])) {
								switchToFrame(driver, 60, sp.getSetUpPageIframe(150));
								if (click(driver, sp.getEditButton(environment,"Classic",10), "edit", action.SCROLLANDBOOLEAN)) {
									switchToFrame(driver, 60, sp.getSetUpPageIframe(120));
									ele=sp.getRecordTypeLabel(projectName, recordTypeLabel.Active.toString(), 20);
									if (ele!=null) {
										log(LogStatus.INFO,recordTypeArray[i]+" ele found on edit page",YesNo.No);
										if (i==1) {
											if (isSelected(driver, ele, recordTypeArray[i]+" "+recordTypeLabel.Active)) {
												ThreadSleep(2000);
												if (click(driver, ele, recordTypeArray[i]+" "+recordTypeLabel.Active, action.BOOLEAN)) {
													log(LogStatus.INFO,recordTypeArray[i]+" "+recordTypeLabel.Active+" is clicked",YesNo.No);
													ThreadSleep(2000);
													if (click(driver, sp.getCreateUserSaveBtn_Lighting(30), "Save Button",action.SCROLLANDBOOLEAN)) {
														flag=true;
														log(LogStatus.INFO, "clicked on save button", YesNo.No);
														ThreadSleep(10000);
													} else {
														log(LogStatus.ERROR, "not able to click on save button", YesNo.Yes);
														sa.assertTrue(false, "not able to click on save button");
													}
												} else {
													log(LogStatus.ERROR,recordTypeArray[i]+" "+recordTypeLabel.Active+" is not clickable",YesNo.Yes);
													sa.assertTrue(false, recordTypeArray[i]+" "+recordTypeLabel.Active+" is not clickable");
												}
											} 
										} else {
											ele=sp.getRecordTypeLabel(projectName, recordTypeLabel.Record_Type_Name.toString(), 20);
											recordTypeName=ele.getAttribute("value");
											System.err.println("recordTypeName "+recordTypeName);
											flag=true;
										}


									} else {
										log(LogStatus.ERROR,recordTypeArray+" ele not found on edit page",YesNo.Yes);
										sa.assertTrue(false, recordTypeArray+" ele not found on edit page");
									}

								}else {
									log(LogStatus.ERROR,"edit button is not clickable",YesNo.Yes);
									sa.assertTrue(false, "edit button is not clickable");
								}


							}else {
								log(LogStatus.ERROR, recordTypeArray[i]+" is not clickable", YesNo.Yes);
								sa.assertTrue(false, recordTypeArray[i]+" is not clickable");
							}

						}else {
							log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
						}
					}else {
						log(LogStatus.ERROR, "Custom object could not be found in object manager", YesNo.Yes);
						sa.assertTrue(false, "Custom object could not be found in object manager");
					}
					driver.close();
					driver.switchTo().window(parentID);
					switchToDefaultContent(driver);
				}else {
					log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
					sa.assertTrue(false, "could not find new window to switch");
				}
			}else {
				log(LogStatus.ERROR, "could not click on setup link", YesNo.Yes);
				sa.assertTrue(false, "could not click on setup link");
			}

			i=0;
			String labelValue;
			String recordType=recordTypeArray[i];
			labelValue=navigationLabel;
			if (flag) {
				String expecedHeader="New "+tabCustomObj+": "+recordType;
				if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
					log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
					ele=npbl.getNavigationLabel(projectName, labelValue, action.BOOLEAN, 10);
					if (click(driver, ele, labelValue, action.BOOLEAN)) {
						log(LogStatus.INFO, "Click on "+labelValue+" going to verify record type options", YesNo.No);
						ThreadSleep(5000);
						String actualHeader="";
						ele=npbl.getPopUpHeader(projectName, 10);
						if (ele!=null) {
							log(LogStatus.INFO, "PopUp is open" , YesNo.No);
							actualHeader=ele.getText().trim();
							if (actualHeader.equals(expecedHeader)) {
								log(LogStatus.INFO, "Header Text verified : "+expecedHeader, YesNo.Yes);

							} else {
								log(LogStatus.ERROR, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader, YesNo.Yes);
								sa.assertTrue(false, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader);
							}

						} else {
							log(LogStatus.ERROR, "No PopUp is open so cannot verify Heading "+expecedHeader, YesNo.Yes);
							sa.assertTrue(false, "No PopUp is open so cannot verify Heading "+expecedHeader);
						}


					} else {
						log(LogStatus.ERROR, "Not Able to Click on "+labelValue+" so cannot verify pop up window", YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on "+labelValue+" so cannot verify pop up window");

					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify pop up window for label : "+labelValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify pop up window for label : "+labelValue);
				}

			} else {
				log(LogStatus.ERROR, "Not Able to deactivate record type for "+labelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to deactivate record type for "+labelValue);
			}

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc026_1_DeActivateARecordTypeForInstitution_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		WebElement ele;
		String recordType=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Record_Type);
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(),object.Firm )) {
					if(sp.clickOnObjectFeature("", Mode.Lightning.toString(),object.Firm, ObjectFeatureName.recordTypes)) {
						if (sp.clickOnAlreadyCreatedLayout(recordType)) {
							switchToFrame(driver, 60, sp.getSetUpPageIframe(120));
							if (click(driver, sp.getEditButton(environment,"Classic",10), "edit", action.SCROLLANDBOOLEAN)) {
								switchToFrame(driver, 60, sp.getSetUpPageIframe(120));
								ele=sp.getRecordTypeLabel(projectName, recordTypeLabel.Active.toString(), 20);
								if (ele!=null) {
									log(LogStatus.INFO,recordType+" ele found on edit page",YesNo.No);
									if (isSelected(driver, ele, recordType+" "+recordTypeLabel.Active)) {
										ThreadSleep(2000);
										if (click(driver, ele, recordType+" "+recordTypeLabel.Active, action.BOOLEAN)) {
											log(LogStatus.INFO,recordType+" "+recordTypeLabel.Active+" is clicked",YesNo.No);
											ThreadSleep(2000);
											if (click(driver, sp.getCreateUserSaveBtn_Lighting(30), "Save Button",action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, "clicked on save button", YesNo.No);
												ThreadSleep(10000);
											} else {
												log(LogStatus.ERROR, "not able to click on save button", YesNo.Yes);
												sa.assertTrue(false, "not able to click on save button");
											};
										} else {
											log(LogStatus.ERROR,recordType+" "+recordTypeLabel.Active+" is not clickable",YesNo.Yes);
											sa.assertTrue(false, recordType+" "+recordTypeLabel.Active+" is not clickable");
										}
									} else{
										log(LogStatus.ERROR,recordType+" "+recordTypeLabel.Active+" is already inactive",YesNo.Yes);
									//	sa.assertTrue(false, recordType+" "+recordTypeLabel.Active+" is already inactive");	
									}
								} else {
									log(LogStatus.ERROR,recordType+" ele not found on edit page",YesNo.Yes);
									sa.assertTrue(false, recordType+" ele not found on edit page");
								}

							}else {
								log(LogStatus.ERROR,"edit button is not clickable",YesNo.Yes);
								sa.assertTrue(false, "edit button is not clickable");
							}
						}else {
							log(LogStatus.ERROR, recordType+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, recordType+" is not clickable");
						}

					}else {
						log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
						sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "Institution could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, "Institution could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
				switchToDefaultContent(driver);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			log(LogStatus.ERROR, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}

		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc026_2_DeActivateARecordTypeForInstitution_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		/*navigationMenuName=NavigationMenuItems.New_Interactions.toString();*/
		navigationMenuName=NavigationMenuItems.Create.toString();
		String dependentTC="M3Tc026_1_DeActivateARecordTypeForInstitution_Action";
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Record_Type);

		if (lp.clickOnTab(projectName,tabObj1)) {
			log(LogStatus.INFO,"Click on Tab : "+tabObj1,YesNo.No);	
			if (click(driver, lp.getNewButton(projectName, 90), "new button",action.BOOLEAN)) {
				log(LogStatus.INFO,"Click on New Button",YesNo.Yes);
				ThreadSleep(3000);
				String expecedHeader="New "+tabObj1;
				ele=npbl.getPopUpHeader(projectName, 30);
				if (ele!=null) {
					log(LogStatus.INFO, "PopUp is open" , YesNo.No);
					String actualHeader = ele.getText().trim();
					if (actualHeader.equals(expecedHeader)) {
						log(LogStatus.INFO, "Header Text verified : "+expecedHeader, YesNo.Yes);

					} else {
						log(LogStatus.ERROR, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader, YesNo.Yes);
						sa.assertTrue(false, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader);
					}

				} else {
					log(LogStatus.ERROR, "No PopUp is open so cannot verify Heading "+expecedHeader, YesNo.Yes);
					sa.assertTrue(false, "No PopUp is open so cannot verify Heading "+expecedHeader);
				}
				
				ele=npbl.getRadioButtonforRecordTypeAtAccount(navigationLabel, 5);
				if (ele==null) {
					log(LogStatus.INFO, navigationLabel+" radio button is not present", YesNo.No);
				} else {
					log(LogStatus.ERROR, navigationLabel+" radio button should not be present", YesNo.Yes);
					sa.assertTrue(false, navigationLabel+" radio button should not be present");
				}

				ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
				click(driver, ele, navigationLabel+" pop up cross button", action.BOOLEAN);
				

			} else {
				sa.assertTrue(false,"Not Able to Click on New Button");
				log(LogStatus.SKIP,"Not Able to Click on New Button",YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj1);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabObj1,YesNo.Yes);
		}
		
		
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			ele=npbl.getNavigationLabel(projectName, navigationLabel, action.BOOLEAN, 30);
			if (click(driver, ele, navigationLabel, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on "+navigationLabel, YesNo.No);
				String expecedHeader="New "+tabObj1+": "+navigationLabel;
				ele=npbl.getPopUpHeader(projectName, 30);
				if (ele!=null) {
					log(LogStatus.INFO, "PopUp is open" , YesNo.No);
					String actualHeader = ele.getText().trim();
					if (actualHeader.equals(expecedHeader)) {
						log(LogStatus.INFO, "Header Text verified : "+expecedHeader, YesNo.Yes);

					} else {
						log(LogStatus.ERROR, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader, YesNo.Yes);
						sa.assertTrue(false, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader);
					}

				} else {
					log(LogStatus.ERROR, "No PopUp is open so cannot verify Heading "+expecedHeader, YesNo.Yes);
					sa.assertTrue(false, "No PopUp is open so cannot verify Heading "+expecedHeader);
				}

				String institutionName="inst1233";
				if (sendKeys(driver, ip.getLegalName(projectName,20), institutionName, "leagl name text box",action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO,"passed data in legal text box: " + institutionName,YesNo.No);
					if (click(driver, lp.getCustomTabSaveBtn(projectName,20), "save", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO,"Clicked on Save Buton",  YesNo.No);
						expecedHeader=BasePageErrorMessage.HitASnagMessage;
						ele=npbl.getHitASnagElement(projectName, 20);
						if (ele!=null) {
							log(LogStatus.INFO, "PopUp is open" , YesNo.No);
							String actualHeader = ele.getText().trim();
							if (actualHeader.contains(expecedHeader)) {
								log(LogStatus.INFO, "Error Message verified after click on save button : "+expecedHeader, YesNo.Yes);
							} else {
								log(LogStatus.ERROR, "Error Message not verified after click on save button Actual : "+actualHeader+" \t Expected : "+expecedHeader, YesNo.Yes);
								sa.assertTrue(false, "Error Message not verified after click on save button Actual : "+actualHeader+" \t Expected : "+expecedHeader);
							}

						} else {
							log(LogStatus.ERROR, "No PopUp is open so cannot verify Heading "+expecedHeader, YesNo.Yes);
							sa.assertTrue(false, "No PopUp is open so cannot verify Heading "+expecedHeader);
						}
					}
					else {
						log(LogStatus.ERROR, "save button is not clickable so cannot check eror message : " ,YesNo.Yes);
						sa.assertTrue(false,"save button is not clickable so cannot check eror message : " );
					}
				} else {
					sa.assertTrue(false,"Not able to pass data in legal name text box so cannot click on save button ");
					log(LogStatus.SKIP,"Not able to pass data in legal name text box so cannot click on save button ",YesNo.Yes);
				}
				ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 30);
				click(driver, ele, navigationLabel+" pop up cross button", action.BOOLEAN);
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationLabel, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationLabel);
			}

		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on label : "+navigationLabel, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on label : "+navigationLabel);

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc027_VerifyAndCreateNavigationItemWhichAreRedirectedtoListView(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		/*navigationMenuName=NavigationMenuItems.New_Interactions.toString();*/
		navigationMenuName=NavigationMenuItems.Create.toString();
		String link="";
		WebElement ele;
		String tab=navigationTab;
		boolean flag=false;
		if (npbl.clickOnTab(projectName, tab)) {
			log(LogStatus.INFO, "Click on Tab : "+tab, YesNo.No);
			ThreadSleep(3000);
			flag=true;
			link = driver.getCurrentUrl();
			link= link.split(".com")[1].substring(0);
			System.err.println("Link : "+link);
		} else {
			log(LogStatus.ERROR, "Not Able to Click on Tab : "+tab, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on Tab : "+tab);
		}



		String navigationLabel=CSVLabel.Navigation_Label.toString();
		String navigationLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		String orderLabel=CSVLabel.Order.toString();
		String orderLabelValue="95";
		String urlLabel=CSVLabel.URL.toString();
		String urlValue=link;
		String navigationTypeLabel=CSVLabel.Navigation_Type.toString();
		String navigationTypeValue=navigationMenuName;

		if (flag) {
			flag=false;
			String[][] labelWithValue= {{navigationLabel,navigationLabelValue},
					{orderLabel,orderLabelValue},
					{urlLabel,urlValue},
					{navigationTypeLabel,navigationTypeValue}};

			if (npbl.createNavigationItem(projectName, labelWithValue, 20)) {
				log(LogStatus.INFO, "created "+navigationLabelValue, YesNo.No);
				flag=true;
			} else {
				log(LogStatus.ERROR, "Not Able to create "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false, "Not Able to create "+navigationLabelValue);

			}
		} else {
			log(LogStatus.ERROR, "Not Able to retrieve url from Navigation so cannot create navigation label : "+navigationLabelValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to retrieve url from Navigation so cannot create navigation label : "+navigationLabelValue);
		}

		if (flag) {
			flag=false;
			refresh(driver);
			String allListView= "Recently Viewed";
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 60)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ele=npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 30);
				if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
					flag=npbl.isAutomationAllListViewForObject(projectName,navigationTab,allListView, 30);;
					if (flag) {
						log(LogStatus.INFO, allListView+" is selected after clicking on "+navigationTab , YesNo.No);
					} else {
						log(LogStatus.ERROR, allListView+" is not selected after clicking on after clicking on "+navigationTab, YesNo.Yes);
						sa.assertTrue(false, allListView+" is not selected after clicking on after clicking on "+navigationTab);
					}
					
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot check label : "+navigationLabelValue);
			}

		} else {
			log(LogStatus.ERROR, "Not Able to Create "+navigationLabelValue+"so type : "+navigationTypeValue+" so cannot check on "+navigationTypeValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Create "+navigationLabelValue+"so type : "+navigationTypeValue+" so cannot check on "+navigationTypeValue);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc028_CreateRecordthroughnavigationMenuandVerifyitontabsListview(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		/*navigationMenuName=NavigationMenuItems.New_Interactions.toString();*/
		navigationMenuName=NavigationMenuItems.Create.toString();
		WebElement ele;

		String navigationLabelValues=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name)+"<break>"+tabCustomObj;
		String[] navigationLabelValuess=navigationLabelValues.split("<break>");

		for (int i = 0; i < navigationLabelValuess.length; i++) {
			String navigationLabelValue=navigationLabelValuess[i];

			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ele=npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 10);
				if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);

					if (i==0) {
						M3Contact3EmailID=	lp.generateRandomEmailId(gmailUserName);
						if (cp.createContactPopUp(projectName, M3Contact3FName, M3Contact3LName, M3Ins1, M3Contact3EmailID,M3Contact3RecordType, null, null, CreationPage.ContactPage, null)) {
							log(LogStatus.INFO,"successfully Created Contact : "+M3Contact3FName+" "+M3Contact3LName,YesNo.No);	
							ThreadSleep(2000);
							String labelValue="";
							boolean isSelectAll = true;
							if (npbl.clickOnTab(projectName, tabObj2)) {
								log(LogStatus.INFO, "Click on Tab : "+tabObj2, YesNo.No);
								labelValue=M3Contact3FName+" "+M3Contact3LName;
								if (npbl.CheckAlreadyCreatedItem(projectName, labelValue, isSelectAll, 15)) {
									log(LogStatus.INFO,"Item found: "+labelValue+" on Tab : "+tabObj2, YesNo.No);
								}else {

									log(LogStatus.ERROR,"Item not found: "+labelValue+" on Tab : "+tabObj2, YesNo.Yes);
									sa.assertTrue(false,"Item not found: "+labelValue+" on Tab : "+tabObj2);
								}
							} else {
								log(LogStatus.ERROR, "Not Able to Click on Tab : "+tabObj2, YesNo.Yes);
								sa.assertTrue(false,"Not Able to Click on Tab : "+tabObj2);
							}

						} else {
							sa.assertTrue(false,"Not Able to Create Contact : "+M3Contact3FName+" "+M3Contact3LName);
							log(LogStatus.SKIP,"Not Able to Create Contact: "+M3Contact3FName+" "+M3Contact3LName,YesNo.Yes);
						}

					} else {
						CustomObjPageBusinessLayer cop = new CustomObjPageBusinessLayer(driver);
						if (cop.createRecord(projectName, M3TestCustomObj1RecordType, null, M3TestCustomObj1Name, true)) {
							log(LogStatus.INFO,"successfully Created custom record : "+M3TestCustomObj1Name,YesNo.No);	
							ThreadSleep(2000);
							String labelValue="";
							boolean isSelectAll = true;
							if (npbl.clickOnTab(projectName, tabCustomObj)) {
								log(LogStatus.INFO, "Click on Tab : "+tabCustomObj, YesNo.No);
								labelValue=M3TestCustomObj1Name;
								if (npbl.CheckAlreadyCreatedItem(projectName, labelValue, isSelectAll, 15)) {
									log(LogStatus.INFO,"Item found: "+labelValue+" on Tab : "+tabCustomObj, YesNo.No);
								}else {

									log(LogStatus.ERROR,"Item not found: "+labelValue+" on Tab : "+tabCustomObj, YesNo.Yes);
									sa.assertTrue(false,"Item not found: "+labelValue+" on Tab : "+tabCustomObj);
								}
							} else {
								log(LogStatus.ERROR, "Not Able to Click on Tab : "+tabCustomObj, YesNo.Yes);
								sa.assertTrue(false,"Not Able to Click on Tab : "+tabCustomObj);
							}

						} else {
							sa.assertTrue(false,"Not Able to Create custom record  : "+M3TestCustomObj1Name);
							log(LogStatus.SKIP,"Not Able to Create custom record  : "+M3TestCustomObj1Name,YesNo.Yes);
						}

					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);

				}

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot create record for label : "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot create record for label : "+navigationLabelValue);
			}
			ele=npbl.getCrossButtonForNavigationLabelPopuP(projectName, "", action.BOOLEAN, 2);
			click(driver, ele, " pop up cross button", action.BOOLEAN);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc029_VerifyWhereChildNodeIsMovedFromOneParentToAnotherInNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		//////////////////////////////////////////////////
		
	/*	String navigationMenuName=NavigationMenuItems.New_Interactions.toString();*/
		String navigationMenuName=NavigationMenuItems.Create.toString();
		int order = 3;
		
		String[] AllParents=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Parent).split(breakSP);
		String actionObject=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Action_Object);
		String child=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		
		String AllParent="";
		for (int i = 0; i < AllParents.length; i++) {
			AllParent=AllParents[i];
			order=3+i;
			String[][] labels={{CSVLabel.Navigation_Label.toString(),AllParent},
					{CSVLabel.Order.toString(),String.valueOf(order)},
					{CSVLabel.Action_Object.toString(),actionObject},
					{CSVLabel.Navigation_Type.toString(),navigationMenuName}};
			if (npbl.createNavigationItem(projectName,labels , 20)) {
				log(LogStatus.INFO, "created "+AllParent, YesNo.No);
			} else {
				log(LogStatus.ERROR, "Not Able to create "+AllParent, YesNo.Yes);
				sa.assertTrue(false, "Not Able to create "+AllParent);

			}
		}
		order=6;
		String[][] labels={{CSVLabel.Navigation_Label.toString(),child},
				{CSVLabel.Order.toString(),String.valueOf(order)},
				{CSVLabel.Action_Object.toString(),actionObject},
				{CSVLabel.Parent.toString(),AllParents[0]},
				{CSVLabel.Navigation_Type.toString(),navigationMenuName}};
		if (npbl.createNavigationItem(projectName,labels , 20)) {
			log(LogStatus.INFO, "created "+child, YesNo.No);
		} else {
			log(LogStatus.ERROR, "Not Able to create "+child, YesNo.Yes);
			sa.assertTrue(false, "Not Able to create "+child);

		}
			
		refresh(driver);
		
		Map<String,String> parentWithChild = new LinkedHashMap<String,String>(); 
		parentWithChild.put(AllParents[0],child);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			npbl.verifyingNavigationMenuLink(projectName, null, parentWithChild, action.BOOLEAN, 0);;
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify "+parentWithChild, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify "+parentWithChild);
		}
		
		refresh(driver);
		
		String parentLabel=CSVLabel.Parent.toString();
		String parentValue=AllParents[1];
		String[][] labelWithValue= {{parentLabel,parentValue}};

		if (npbl.enterValueOnEditPopUpForNavigationTab(projectName, child, labelWithValue, 20)) {
			log(LogStatus.INFO, parentValue+" value has been updated & saved under "+parentLabel+" for "+child, YesNo.No);
		} else {
			log(LogStatus.ERROR, parentValue+" value has not been updated & saved under "+parentLabel+" for "+child, YesNo.Yes);
			sa.assertTrue(false, parentValue+" value has not been updated & saved under "+parentLabel+" for "+child);

		}
		
		refresh(driver);
		
		parentWithChild = new LinkedHashMap<String,String>(); 
		parentWithChild.put(AllParents[1],child);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			npbl.verifyingNavigationMenuLink(projectName, null, parentWithChild, action.BOOLEAN, 0);;
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify "+parentWithChild, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify "+parentWithChild);
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
		
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc030_VerifyTheImpactOnNavigationMenuWhenDeletingParentNodeu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		WebElement ele;
		/*navigationMenuName=NavigationMenuItems.New_Interactions.toString();*/
		navigationMenuName=NavigationMenuItems.Create.toString();
		
		String dependentTc = "M3Tc029_VerifyWhereChildNodeIsMovedFromOneParentToAnotherInNavigationMenu";
		String[] AllParents=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTc, excelLabel.Parent).split(breakSP);
		String child=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTc, excelLabel.Navigation_Label_Name);
		
		String navLb;
		navLb=AllParents[1];
		boolean flag=false;
		if (npbl.clickOnTab(projectName, navigationTab)) {
			log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);

			if (npbl.clickOnAlreadyCreatedItem(projectName, navLb, true, 15)) {
				log(LogStatus.INFO,"Item found: "+navLb+" on Tab : "+navigationTab, YesNo.No);

				npbl.clickOnShowMoreDropdownOnly(projectName);
				ele = npbl.actionDropdownElement(projectName, ShowMoreActionDropDownList.Delete, 10);

				if (click(driver, ele, ShowMoreActionDropDownList.Delete.toString(), action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on delete Button : "+navLb, YesNo.No);

					if(click(driver,npbl.getDeleteButtonPopUp(projectName, 10), "delete", action.BOOLEAN)) {
						log(LogStatus.INFO,"Able to Click on delete button on delete popup : "+TabName.Object2Tab+" For : "+navLb,YesNo.No); 
						ThreadSleep(5000);
						flag=true;
					}else {
						sa.assertTrue(false,"Not Able to Select delete button for "+navLb);
						log(LogStatus.SKIP,"Not Able to Select delete button for "+navLb,YesNo.Yes);

					}
				}

				else {
					log(LogStatus.ERROR, "Not Able to Click on delete Button : "+navLb, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on delete Button : "+navLb);
				}


			}else {

				log(LogStatus.ERROR,"Item not found: "+navLb+" on Tab : "+navigationTab, YesNo.Yes);
				sa.assertTrue(false,"Item not found: "+navLb+" on Tab : "+navigationTab);
			}


		} else {
			log(LogStatus.ERROR, "Not Able to Click on Tab : "+navigationTab, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on Tab : "+navigationTab);
		}

		if (flag) {
			refresh(driver);
			ThreadSleep(5000);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				
				ele = npbl.getNavigationLabel1(projectName, child, action.BOOLEAN, 10);
				if (ele!=null) {
					log(LogStatus.INFO, child+" becomes parent after delete of "+navLb, YesNo.No);	
				} else {
					log(LogStatus.ERROR, child+" should becomes parent after delete of "+navLb, YesNo.Yes);
					sa.assertTrue(false,child+" should becomes parent after delete of "+navLb);

				}
				
				ele = npbl.getNavigationLabel1(projectName, navLb+"/"+child, action.BOOLEAN, 10);
				if (ele==null) {
					log(LogStatus.INFO, child+" is not under "+navLb+" after deletion of "+navLb, YesNo.No);	
				} else {
					log(LogStatus.ERROR, child+" should not be under "+navLb+" after deletion of "+navLb, YesNo.Yes);
					sa.assertTrue(false,child+" should not be under "+navLb+" after deletion of "+navLb);

				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to  delete : "+navLb, YesNo.Yes);
			sa.assertTrue(false,"Not Able to delete  : "+navLb);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc031_RestoringDeletedItemFromRecycleBinAndCheckImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		String dependentTc = "M3Tc029_VerifyWhereChildNodeIsMovedFromOneParentToAnotherInNavigationMenu";
		String[] AllParents=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTc, excelLabel.Parent).split(breakSP);
		String child=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTc, excelLabel.Navigation_Label_Name);
		
		String name = AllParents[1];
		WebElement ele ;
		boolean flag=false;
		String recycleTab= lp.getTabName(projectName, TabName.RecycleBinTab);
		lp.searchAndClickOnApp(recycleTab, 60);
			 ele = lp.getCheckboxOfRestoreItemOnRecycleBin(projectName, name, 30);
			 if (clickUsingJavaScript(driver, ele, "Check box against : "+name, action.BOOLEAN)) {
				 log(LogStatus.INFO,"Click on checkbox for "+name,YesNo.No);;
				 ele=lp.getRestoreButtonOnRecycleBin(projectName, 30);
				 if (clickUsingJavaScript(driver, ele, "Restore Button : "+name, action.BOOLEAN)) {
					 log(LogStatus.INFO,"Click on Restore Button for "+name,YesNo.No);
					 flag=true;
				} else {
					sa.assertTrue(false,"Not Able to Click on Restore Button for "+name);
					log(LogStatus.SKIP,"Not Able to Click on Restore Button for "+name,YesNo.Yes);
				}
				 
			} else {
				sa.assertTrue(false,"Not Able to Click on checkbox for "+name);
				log(LogStatus.SKIP,"Not Able to Click on checkbox for "+name,YesNo.Yes);
			}
			 
	

		if (flag) {

			if (npbl.clickOnTab(projectName, navigationTab)) {
				log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);
				if (npbl.clickOnAlreadyCreatedItem(projectName, child, true, 15)) {
					log(LogStatus.INFO,"Item found: "+child+" on Tab : "+navigationTab, YesNo.No);	
				}else {
					log(LogStatus.ERROR,"Item not found: "+child+" on Tab : "+navigationTab, YesNo.Yes);
					sa.assertTrue(false,"Item not found: "+child+" on Tab : "+navigationTab);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on Tab : "+navigationTab, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on Tab : "+navigationTab);
			}

			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 60)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ele = npbl.getNavigationLabel1(projectName, name+"/"+child, action.BOOLEAN, 60);
				if (ele!=null) {
					log(LogStatus.INFO, child+" is under "+name+" after restortion of "+name, YesNo.No);	
				} else {
					log(LogStatus.ERROR, child+" should be under "+name+" after restortion of "+name, YesNo.Yes);
					sa.assertTrue(false,child+" should be under "+name+" after restortion of "+name);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Restore : "+name, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Restore  : "+name);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc032_VerifyWhetherCustomNavigationwillbeaddedintheNavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject(environment, mode,object.Navigation )) {
					log(LogStatus.INFO,"Searched & Clicked on "+object.Navigation,YesNo.No);	
					if(sp.clickOnObjectFeature(environment, mode,object.Navigation, ObjectFeatureName.FieldAndRelationShip)) {
						log(LogStatus.INFO, "object feature "+ObjectFeatureName.FieldAndRelationShip+" is clickable", YesNo.No);
						if (sendKeys(driver, sp.getsearchTextboxFieldsAndRelationships(10), PageLabel.Navigation_Type.toString()+Keys.ENTER, "Navigation Type", action.BOOLEAN)) {
							log(LogStatus.INFO,PageLabel.Navigation_Type.toString()+" is clickable",YesNo.No);
							if (sp.clickOnAlreadyCreatedLayout(PageLabel.Navigation_Type.toString())) {
								log(LogStatus.INFO,PageLabel.Navigation_Type.toString()+" is clickable",YesNo.No);	
								switchToFrame(driver, 10, sp.getFrame(PageName.CustomNavigationPage, 10));
								if (click(driver, sp.getValuesNewButton(20), "Values New Button", action.BOOLEAN)) {
									log(LogStatus.INFO, "click on Values New Button", YesNo.No);
									switchToDefaultContent(driver);
									switchToFrame(driver, 60, sp.getFrame(PageName.NavigationPickListPage, 120));
									ThreadSleep(5000);
									if (sendKeys(driver, dp.getTextArea(20), customNavigationMenu, customNavigationMenu, action.BOOLEAN)) {
										log(LogStatus.INFO,"enter value on textarea "+customNavigationMenu,YesNo.No);
										if (click(driver, npbl.getCustomTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.ERROR, "Click on save Button : "+customNavigationMenu, YesNo.No);
											ThreadSleep(10000);
										}else {
											log(LogStatus.ERROR, "Not Able to Click on save Button : "+customNavigationMenu, YesNo.Yes);
											sa.assertTrue(false,"Not Able to Click on save Button : "+customNavigationMenu);
										}
									}else {
										sa.assertTrue(false,"Not Able to enter value to textarea "+customNavigationMenu);
										log(LogStatus.SKIP,"Not Able to enter value to textarea "+customNavigationMenu,YesNo.Yes);
									}
								} else {
									log(LogStatus.ERROR, "Not able to click on Values New Button", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on New Button");
								}

							}else {
								log(LogStatus.ERROR,PageLabel.Navigation_Type.toString()+" is not clickable",YesNo.Yes);	
								sa.assertTrue(false,PageLabel.Navigation_Type.toString()+"status field is not clickable" );
							}
						}else {
							log(LogStatus.ERROR,PageLabel.Navigation_Type.toString()+" search is not visible",YesNo.Yes);	
							sa.assertTrue(false,PageLabel.Navigation_Type.toString()+" search is not visible" );
						}
					}else {
						log(LogStatus.ERROR, "object feature "+ObjectFeatureName.FieldAndRelationShip+" is not clickable", YesNo.Yes);
						sa.assertTrue(false, "object feature "+ObjectFeatureName.FieldAndRelationShip+" is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "Navigation object could not be found in object manager", YesNo.Yes);
					sa.assertTrue(false, "Navigation object could not be found in object manager");
				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			log(LogStatus.ERROR, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}


		switchToDefaultContent(driver);
		refresh(driver);
		ThreadSleep(2000);
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {
				log(LogStatus.INFO, "Able to switch on new window, so going to add "+customNavigationMenu, YesNo.No);
				ThreadSleep(500);
				if(setup.searchStandardOrCustomObject(environment,mode, object.App_Manager)) {
					log(LogStatus.INFO, "click on Object : "+object.App_Manager, YesNo.No);
					ThreadSleep(2000);
					if(setup.clickOnEditForApp(driver, appName, AppDeveloperName,AppDescription, 60)) {
						log(LogStatus.INFO,"able to click on edit button against "+appName+" : "+AppDeveloperName+" "+AppDescription, YesNo.No);
						ThreadSleep(1000);
						if (setup.clickOnAppSettingList(driver, AppSetting.Utility_Items, 30)) {
							log(LogStatus.INFO, "able to click on "+AppSetting.Utility_Items, YesNo.No);
							ThreadSleep(500);
							if (click(driver, setup.getAddUtilityItem(30), "Add Utility Item", action.BOOLEAN)) {
								log(LogStatus.INFO, "Able to click on Add Utility Item", YesNo.No);
								ThreadSleep(500);
								if (setup.searchAndClickOnAddUtilityItem(environment,mode, object.navatarEdgeMenu.toString())) {
									log(LogStatus.INFO, "Able to search and click on "+object.navatarEdgeMenu.toString(), YesNo.No);
									ThreadSleep(500);
									if (sendKeys(driver, setup.commonInputElement(projectName, PageLabel.Label.toString(), action.BOOLEAN, 30),customNavigationMenu,PageLabel.Label.toString()+" textbox value : "+customNavigationMenu,action.BOOLEAN)) {
										ThreadSleep(500);
										log(LogStatus.INFO,"send value to "+PageLabel.Label.toString()+" textbox value : "+customNavigationMenu,YesNo.No);
										if (sendKeys(driver, setup.commonInputElement(projectName, PageLabel.Navigation_Type.toString(), action.BOOLEAN, 10),customNavigationMenu,PageLabel.Label.toString()+" textbox value : "+customNavigationMenu,action.BOOLEAN)) {
											ThreadSleep(500);
											log(LogStatus.INFO,"send value to "+PageLabel.Navigation_Type.toString()+" textbox value : "+customNavigationMenu,YesNo.No);
											
											for (int i = 0; i < 3; i++) {
												if (click(driver, setup.getMoveThisUtilityDownTheList(60),"Move this utility down the list.", action.BOOLEAN)) {
													log(LogStatus.INFO,"Click on Move this utility down the list Button",YesNo.No);
													ThreadSleep(1000);			
												} else {
													sa.assertTrue(false, "Not Able to Click on Move this utility down the list Button");
													log(LogStatus.FAIL,"Not Able to Click on Move this utility down the list Button",YesNo.Yes);
												}
											}
											
											if (click(driver, setup.getCustomTabSaveBtn(projectName, 30)," Save Button", action.BOOLEAN)) {
												log(LogStatus.INFO,"Click on Save Button",YesNo.No);
												ThreadSleep(20000);			
											} else {
												sa.assertTrue(false, "Not Able to Click on Save Button");
												log(LogStatus.FAIL,"Not Able to Click on Save Button",YesNo.Yes);
											}
										} else {
											sa.assertTrue(false, "Not Able to send value to "+PageLabel.Navigation_Type.toString()+" textbox value : "+customNavigationMenu);
											log(LogStatus.FAIL,"Not Able to send value to "+PageLabel.Navigation_Type.toString()+" textbox value : "+customNavigationMenu,YesNo.Yes);
										}
									} else {
										sa.assertTrue(false, "Not Able to send value to "+PageLabel.Label.toString()+" textbox value : "+customNavigationMenu);
										log(LogStatus.FAIL,"Not Able to send value to "+PageLabel.Label.toString()+" textbox value : "+customNavigationMenu,YesNo.Yes);
									}
								} else {
									log(LogStatus.ERROR, "Not Able to search and click on "+object.navatarEdgeMenu.toString(), YesNo.Yes);
									sa.assertTrue(false,"Able Able to search and click on "+object.navatarEdgeMenu.toString());
								}

							} else {
								log(LogStatus.ERROR, "Not able to click on Add Utility Item", YesNo.Yes);
								sa.assertTrue(false,"Not able to click on Add Utility Item");
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on "+AppSetting.Utility_Items, YesNo.Yes);
							sa.assertTrue(false,"Not able to click on "+AppSetting.Utility_Items);
						}
					}else {
						log(LogStatus.ERROR,"Not able to click on edit button against "+appName+" : "+AppDeveloperName+" "+AppDescription, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on edit button against "+appName+" : "+AppDeveloperName+" "+AppDescription);
					}

				}else {
					log(LogStatus.ERROR, "Not able to search/click on Apps so cannot add "+customNavigationMenu, YesNo.Yes);
					sa.assertTrue(false, "Not able to search/click on Apps manager so add "+customNavigationMenu);
				}

				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch, so cannot add "+customNavigationMenu, YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch, so cannot add "+customNavigationMenu);
			}

		}
		switchToDefaultContent(driver);
		refresh(driver);
		List<String> ExpectedNavigationMenuitem=new LinkedList<String>();
		ExpectedNavigationMenuitem.add(NavigationMenuItems.Create.toString());
		ExpectedNavigationMenuitem.add(NavigationMenuItems.Research.toString());
		ExpectedNavigationMenuitem.add(NavigationMenuItems.Support.toString());
		ExpectedNavigationMenuitem.add(customNavigationMenu);
		List<String> ActualNavigationMenuitem=home.getNavigationMenuitem();
		if (ExpectedNavigationMenuitem.equals(ActualNavigationMenuitem)) {
			log(LogStatus.PASS, "Navigation Menu verified "+ExpectedNavigationMenuitem, YesNo.No);
				
		} else {
			log(LogStatus.FAIL, "Navigation Menu not verified Actual : "+ActualNavigationMenuitem+" Expected : "+ExpectedNavigationMenuitem, YesNo.Yes);
			sa.assertTrue(false,"Navigation Menu not verified Actual : "+ActualNavigationMenuitem+" Expected : "+ExpectedNavigationMenuitem);

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc033_VerifyTheFunctionalityByAddingCustomNavigationLinkInTheNewlyAddedCustomNavigation(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		navigationMenuName=customNavigationMenu;
		WebElement ele;
		boolean flag=false;

		String dependentTC="M3Tc032_VerifyWhetherCustomNavigationwillbeaddedintheNavigationMenu";
		String navigationLabel=CSVLabel.Navigation_Label.toString();
		String navigationLabelValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Navigation_Label_Name);
		
		String orderLabel=CSVLabel.Order.toString();
		String orderLabelValue="1";
	
		String navigationTypeLabel=CSVLabel.Navigation_Type.toString();
		String navigationTypeValue=navigationMenuName;

		String actionObjectLabel=CSVLabel.Action_Object.toString();
		String actionObjecValue=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, dependentTC, excelLabel.Action_Object);
		
		String[][] labelWithValue= {{navigationLabel,navigationLabelValue},
				{orderLabel,orderLabelValue},
				{actionObjectLabel,actionObjecValue},
				{navigationTypeLabel,navigationTypeValue}};

		if (npbl.createNavigationItem(projectName, labelWithValue, 20)) {
			log(LogStatus.INFO, "created "+navigationLabelValue, YesNo.No);
			flag=true;
		} else {
			log(LogStatus.ERROR, "Not Able to create "+navigationLabelValue, YesNo.Yes);
			sa.assertTrue(false, "Not Able to create "+navigationLabelValue);

		}
		if (flag) {
			flag=false;
			refresh(driver);
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 60)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ele=npbl.getNavigationLabel(projectName, navigationLabelValue, action.BOOLEAN, 60);
				if (click(driver, ele, navigationLabelValue, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+navigationLabelValue, YesNo.No);
					
					String expecedHeader="New "+tabObj1;
					String actualHeader="";
					ele=npbl.getPopUpHeader(projectName, 30);
					if (ele!=null) {
						log(LogStatus.INFO, "PopUp is open" , YesNo.No);
						actualHeader=ele.getText().trim();
						if (actualHeader.equals(expecedHeader)) {
							log(LogStatus.INFO, "Header Text verified : "+expecedHeader, YesNo.Yes);
						} else {
							log(LogStatus.ERROR, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader, YesNo.Yes);
							sa.assertTrue(false, "Header Text not verified Actual : "+actualHeader+" \t Expected : "+expecedHeader);
						}

					} else {
						log(LogStatus.ERROR, "No PopUp is open so cannot verify Heading "+expecedHeader, YesNo.Yes);
						sa.assertTrue(false, "No PopUp is open so cannot verify Heading "+expecedHeader);
					}
					
				} else {
						log(LogStatus.ERROR, "Not Able to Click on "+navigationLabelValue, YesNo.Yes);
						sa.assertTrue(false,"Not Able to Click on "+navigationLabelValue);
					}
				
				

			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot check label : "+navigationLabelValue, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot check label : "+navigationLabelValue);
			}

		} else {
			log(LogStatus.ERROR, "Not Able to Create "+navigationLabelValue+"of type : "+navigationTypeValue+" so cannot check on "+navigationTypeValue, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Create "+navigationLabelValue+"of type : "+navigationTypeValue+" so cannot check on "+navigationTypeValue);
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc034_1_VerifyTheFunctionalityByrenamingAndDeletingandRemovingUtilityItemCustomLabelintheCustomnavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		navigationMenuName=customNavigationMenu;
		String updatedName="";
		WebElement ele;
		String navigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Navigation_Label_Name);
		String updatedNavigationLabel=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, currentlyExecutingTC, excelLabel.Update_Navigation_Label_Name);
		String navLb;
		navLb=navigationLabel;

		// Rename
		if (npbl.clickOnTab(projectName, navigationTab)) {
			log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);
			if (npbl.clickOnAlreadyCreatedItem(projectName, navLb, true, 15)) {
				log(LogStatus.INFO,"Item found: "+navLb+" on Tab : "+navigationTab, YesNo.No);
				npbl.clickOnShowMoreDropdownOnly(projectName);
				ele = npbl.actionDropdownElement(projectName, ShowMoreActionDropDownList.Edit, 10);
				if (click(driver, ele, ShowMoreActionDropDownList.Edit.toString(), action.BOOLEAN)) {
					log(LogStatus.INFO, "Not Able to Click on Edit Button : "+navLb, YesNo.No);
					ele = npbl.getNavigationField(projectName, CSVLabel.Navigation_Label.toString(), action.BOOLEAN, 20);
					updatedName=updatedNavigationLabel;
					if (sendKeys(driver, ele, updatedName, CSVLabel.Navigation_Label.toString(), action.BOOLEAN)) {
						log(LogStatus.INFO, "Able to enter "+CSVLabel.Navigation_Label.toString(), YesNo.No);
						ThreadSleep(2000);
						if (click(driver, npbl.getCustomTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.ERROR, "Click on save Button : "+navLb, YesNo.No);
							ThreadSleep(2000);


						} else {
							log(LogStatus.ERROR, "Not Able to Click on save Button : "+navLb, YesNo.Yes);
							sa.assertTrue(false,"Not Able to Click on save Button : "+navLb);}

					} else {
						log(LogStatus.ERROR, "Not Able to enter "+CSVLabel.Navigation_Label.toString(), YesNo.Yes);
						sa.assertTrue(false,"Not Able to enter "+CSVLabel.Navigation_Label.toString());
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on InLine Edit Button : "+navLb, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on InLine Edit Button : "+navLb);
				}

			}else {

				log(LogStatus.ERROR,"Item not found: "+navLb+" on Tab : "+navigationTab+" so cannot update navigation label for "+navLb, YesNo.Yes);
				sa.assertTrue(false,"Item not found: "+navLb+" on Tab : "+navigationTab+" so cannot update navigation label for "+navLb);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on Tab : "+navigationTab+" so cannot update navigation label for "+navLb, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on Tab : "+navigationTab+" so cannot update navigation label for "+navLb);
		}
		refresh(driver);
		ThreadSleep(2000);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			ele=npbl.getNavigationLabel(projectName, updatedName, action.BOOLEAN, 10);
			if (ele!=null) {
				log(LogStatus.INFO, navLb+" has been updated to : "+updatedName, YesNo.No);	
			} else {
				log(LogStatus.ERROR, navLb+" should updated to : "+updatedName, YesNo.Yes);
				sa.assertTrue(false,navLb+" should updated to : "+updatedName);

			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify update label Name : "+updatedName, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify update label Name : "+updatedName);
		}


		//Delete 
		navLb=updatedNavigationLabel;
		if (npbl.clickOnTab(projectName, navigationTab)) {
			log(LogStatus.INFO, "Click on Tab : "+navigationTab, YesNo.No);
			if (npbl.clickOnAlreadyCreatedItem(projectName, navLb, true, 15)) {
				log(LogStatus.INFO,"Item found: "+navLb+" on Tab : "+navigationTab, YesNo.No);
				npbl.clickOnShowMoreDropdownOnly(projectName);
				ele = npbl.actionDropdownElement(projectName, ShowMoreActionDropDownList.Delete, 10);
				if (click(driver, ele, ShowMoreActionDropDownList.Delete.toString(), action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on delete Button : "+navLb, YesNo.No);
					if(click(driver,npbl.getDeleteButtonPopUp(projectName, 10), "delete", action.BOOLEAN)) {
						log(LogStatus.INFO,"Able to Click on delete button on delete popup : "+navLb+" For : "+navLb,YesNo.No); 
						ThreadSleep(5000);
						if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
							log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
							ele = npbl.getNavigationLabel(projectName, navLb, action.BOOLEAN, 10);
							if (ele==null) {
								log(LogStatus.INFO, navigationLabel+" label is not present after delete", YesNo.No);	
							} else {
								log(LogStatus.ERROR, navigationLabel+" label should not present after delete", YesNo.Yes);
								sa.assertTrue(false,navigationLabel+" label should not present after delete");

							}
						} else {
							log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot verify order", YesNo.Yes);
							sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot verify order");
						}

					}else {
						sa.assertTrue(false,"Not Able to Select delete button for "+navLb);
						log(LogStatus.SKIP,"Not Able to Select delete button for "+navLb,YesNo.Yes);

					}
				}
				else {
					log(LogStatus.ERROR, "Not Able to Click on delete Button : "+navLb, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on delete Button : "+navLb);
				}
			}else {

				log(LogStatus.ERROR,"Item not found: "+navLb+" on Tab : "+navigationTab, YesNo.Yes);
				sa.assertTrue(false,"Item not found: "+navLb+" on Tab : "+navigationTab);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on Tab : "+navigationTab, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on Tab : "+navigationTab);
		}

		// App Manager
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc034_2_VerifyTheFunctionalityByrenamingAndDeletingandRemovingUtilityItemCustomLabelintheCustomnavigationMenu(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		navigationMenuName=customNavigationMenu;
		// App Manager
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {
				log(LogStatus.INFO, "Able to switch on new window, so going to remove "+customNavigationMenu, YesNo.No);
				ThreadSleep(500);
				if(setup.searchStandardOrCustomObject(environment,mode, object.App_Manager)) {
					log(LogStatus.INFO, "click on Object : "+object.App_Manager, YesNo.No);
					ThreadSleep(2000);
					if(setup.clickOnEditForApp(driver, appName, AppDeveloperName,AppDescription, 90)) {
						log(LogStatus.INFO,"able to click on edit button against "+appName+" : "+AppDeveloperName+" "+AppDescription, YesNo.No);
						if (setup.clickOnAppSettingList(driver, AppSetting.Utility_Items, 90)) {
							log(LogStatus.INFO, "able to click on "+AppSetting.Utility_Items, YesNo.No);
								if (setup.ClickAndRemoveUtilityItem(environment,mode, customNavigationMenu)) {
									log(LogStatus.INFO, "Able to click and remove"+customNavigationMenu, YesNo.No);
									if (click(driver, setup.getCustomTabSaveBtn(projectName, 30)," Save Button", action.BOOLEAN)) {
										log(LogStatus.INFO,"Click on Save Button",YesNo.No);
										ThreadSleep(20000);			
									} else {
										sa.assertTrue(false, "Not Able to Click on Save Button");
										log(LogStatus.FAIL,"Not Able to Click on Save Button",YesNo.Yes);
									}

								} else {
									log(LogStatus.ERROR, "Not Able to click and remove "+customNavigationMenu, YesNo.Yes);
									sa.assertTrue(false,"Not Able to click and remove "+customNavigationMenu);
								}

						} else {
							log(LogStatus.ERROR, "Not able to click on "+AppSetting.Utility_Items, YesNo.Yes);
							sa.assertTrue(false,"Not able to click on "+AppSetting.Utility_Items);
						}
					}else {
						log(LogStatus.ERROR,"Not able to click on edit button against "+appName+" : "+AppDeveloperName+" "+AppDescription, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on edit button against "+appName+" : "+AppDeveloperName+" "+AppDescription);
					}

				}else {
					log(LogStatus.ERROR, "Not able to search/click on Apps so cannot remove "+customNavigationMenu, YesNo.Yes);
					sa.assertTrue(false, "Not able to search/click on Apps manager so cannot remove "+customNavigationMenu);
				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch, so cannot remove "+customNavigationMenu, YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch, so cannot remove "+customNavigationMenu);
			}

		}
		switchToDefaultContent(driver);
		refresh(driver);
		if (!npbl.clickOnNavatarEdgeLink(projectName, navigationMenuName, action.BOOLEAN, 30)) {
			log(LogStatus.PASS, "Navigation Menu verified After removing "+navigationMenuName, YesNo.No);
			} else {
			log(LogStatus.FAIL, "Navigation Menu not verified After removing "+navigationMenuName, YesNo.Yes);
			sa.assertTrue(false,"Navigation Menu not verified After removing "+navigationMenuName);

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	

}