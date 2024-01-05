package com.navatar.scripts;

import static com.navatar.generic.CommonLib.FindElement;
import static com.navatar.generic.CommonLib.ThreadSleep;
import static com.navatar.generic.CommonLib.click;
import static com.navatar.generic.CommonLib.clickUsingJavaScript;
import static com.navatar.generic.CommonLib.exit;
import static com.navatar.generic.CommonLib.getText;
import static com.navatar.generic.CommonLib.isAlertPresent;
import static com.navatar.generic.CommonLib.isDisplayed;
import static com.navatar.generic.CommonLib.isSelected;
import static com.navatar.generic.CommonLib.log;
import static com.navatar.generic.CommonLib.refresh;
import static com.navatar.generic.CommonLib.removeNumbersFromString;
import static com.navatar.generic.CommonLib.scrollDownThroughWebelement;
import static com.navatar.generic.CommonLib.selectVisibleTextFromDropDown;
import static com.navatar.generic.CommonLib.sendKeys;
import static com.navatar.generic.CommonLib.switchOnWindow;
import static com.navatar.generic.CommonLib.switchToDefaultContent;
import static com.navatar.generic.CommonLib.switchToFrame;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;
import static com.navatar.generic.SmokeCommonVariables.crmUser1EmailID;
import static com.navatar.generic.SmokeCommonVariables.superAdminUserName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.APIUtils;
import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.ActivityRelatedLabel;
import com.navatar.generic.EnumConstants.ApiHeader;
import com.navatar.generic.EnumConstants.CheckBox;
import com.navatar.generic.EnumConstants.CreateNew_DefaultValues;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.EditPageLabel;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.InstRecordType;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
import com.navatar.generic.EnumConstants.Stage;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.Watchlist;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;
import com.navatar.pageObjects.*;
import com.relevantcodes.extentreports.LogStatus;

	public class AcuityActivityTrigger extends BaseLib{
	
@Parameters({ "projectName"})
@Test
	public void AATc001_CreateUsers(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		String[] splitedUserLastName = removeNumbersFromString(crmUser1LastName);
		String UserLastName = splitedUserLastName[0] + lp.generateRandomNumber();
		String emailId = lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User1",excelLabel.User_Last_Name);
		lp.CRMLogin(superAdminUserName, adminPassword);
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
							crmUserProfile, null)) {
						log(LogStatus.INFO, "crm User is created Successfully: " + crmUser1FirstName + " " + UserLastName, YesNo.No);
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
				// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		appLog.info("ResetLinkIs: " + passwordResetLink);
		driver.get(passwordResetLink);
		if (lp.setNewPassword()) {
			appLog.info("Password is set successfully for crm User1: " + crmUser1FirstName + " " + UserLastName );
		} else {
			appLog.info("Password is not set for crm User1: " + crmUser1FirstName + " " + UserLastName);
			sa.assertTrue(false, "Password is not set for crm User1: " + crmUser1FirstName + " " + UserLastName);
			log(LogStatus.ERROR, "Password is not set for crm User1: " + crmUser1FirstName + " " + UserLastName,
					YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

@Parameters({ "projectName"})
@Test
	public void AATc002_CreatePreconditionData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		DealPageBusinessLayer fp = new DealPageBusinessLayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		FundraisingsPageBusinessLayer target=new FundraisingsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);//change
	
		String[][] entitys = {{ ATFirm1, ATRecordType1}, { ATFirm2, ATRecordType2},
				{ ATFirm3, ATRecordType3}, { ATFirm4, ATRecordType4}, { ATFirm5, ATRecordType5},{ ATFirm6, ATRecordType6}};
	
		ThreadSleep(5000);
		for(int i=0;i<entitys.length;i++) {
			if (lp.clickOnTab(projectName,mode, TabName.InstituitonsTab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.InstituitonsTab,YesNo.No);	
	
				if (ip.createInstitution(environment, mode, entitys[i][0], entitys[i][1], null,null)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+entitys[i][0]+" of record type : "+entitys[i][1],YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+entitys[i][0]+" of record type : "+entitys[i][1]);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+entitys[i][0]+" of record type : "+entitys[i][1],YesNo.Yes);
				}
	
	
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.InstituitonsTab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.InstituitonsTab,YesNo.Yes);
			}
	
		}
		
		refresh(driver);
		String[][] contacts = {{ATConFN1, ATConLN1,ATConFirm1,ATConEmail1},{ATConFN2, ATConLN2,ATConFirm2,ATConEmail2},
				{ATConFN3, ATConLN3,ATConFirm3,ATConEmail3},{ATConFN4, ATConLN4,ATConFirm4,ATConEmail4},
				{ATConFN5, ATConLN5,ATConFirm5,ATConEmail5},{ATConFN6, ATConLN6,ATConFirm6,ATConEmail6}};
	
		ThreadSleep(5000);
		for(int i=0;i<contacts.length;i++) {
			if (lp.clickOnTab(projectName,mode, TabName.ContactTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.ContactTab, YesNo.No);
	
				if (cp.createContact(projectName, contacts[i][0], contacts[i][1], contacts[i][2], contacts[i][3],
						"", null,null,CreationPage.ContactPage,null,null)) {//change
					log(LogStatus.INFO, "successfully Created Contact : " + contacts[i][0] + " " + contacts[i][1],
							YesNo.No);
				} else {
					sa.assertTrue(false, "Not Able to Create Contact : " + contacts[i][0] + " " + contacts[i][1]);
					log(LogStatus.SKIP, "Not Able to Create Contact: " + contacts[i][0] + " " + contacts[i][1],
							YesNo.Yes);
				}
	
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.ContactTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.ContactTab, YesNo.Yes);
			}
		}
	
	refresh(driver);	
		String[][] deals = {{ATDealName1, ATDealRT1, ATDealCompany1, ATDealStage1, ATDealStatus1},{ATDealName2, ATDealRT2, ATDealCompany2, ATDealStage2, ATDealStatus2}};
	
		ThreadSleep(5000);
		for(int i=0;i<deals.length;i++) {
			if (ip.clickOnTab(projectName, mode,TabName.Pipelines)) {
				if (fp.createDeal(projectName, mode,deals[i][1], deals[i][0], deals[i][2],deals[i][4],deals[i][3],null, 10)) {
					log(LogStatus.INFO,"successfully Created Deal : "+deals[i][0]+" of record type : "+deals[i][1],YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Deal : "+deals[i][0]+" of record type : "+deals[i][1]);
					log(LogStatus.SKIP,"Not Able to Create Deal : "+deals[i][0]+" of record type : "+deals[i][1],YesNo.Yes);
				}
			}else {
				log(LogStatus.FAIL, "Deal tab is not clickable", YesNo.Yes);
				sa.assertTrue(false, "Deal tab is not clickable");
			}
		}
	
		refresh(driver);
		ThreadSleep(2000);
		
		String[][] funds = {{ATFundName1, ATFundType1, ATFundCategory1},{ATFundName2, ATFundType2, ATFundCategory2}};
		for(int i=0;i<funds.length;i++) {
			if (ip.clickOnTab(projectName, mode,TabName.FundsTab)) {
				if (fund.createFund(projectName, funds[i][0], funds[i][1],funds[i][2], null, null)) {
					log(LogStatus.INFO,"successfully Created Deal : "+funds[i][0]+" of record type : "+funds[i][1],YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Deal : "+funds[i][0]+" of record type : "+funds[i][1]);
					log(LogStatus.SKIP,"Not Able to Create Deal : "+funds[i][0]+" of record type : "+funds[i][1],YesNo.Yes);
				}
			}else {
				log(LogStatus.FAIL, "Deal tab is not clickable", YesNo.Yes);
				sa.assertTrue(false, "Deal tab is not clickable");
			}
		}
	
	refresh(driver);
		ThreadSleep(2000);
		
		
		String[][] targets = {{ATTargetName1, ATDealCompany1, ATFundName1, ATTargetStage1},
				{ATTargetName2, ATDealCompany2, ATFundName2, ATTargetStage2}};
	
		ThreadSleep(5000);
		for(int i=0;i<targets.length;i++) {
			if (ip.clickOnTab(projectName, mode,TabName.FundraisingsTab)) {
				
				if (target.createFundRaising(environment, mode, targets[i][0], targets[i][2],targets[i][1],
						targets[i][3])) {
					appLog.info("Targets is created : " + targets[i][0]);
				} else {
					appLog.error("Not able to create Targets: " + targets[i][0]);
					sa.assertTrue(false, "Not able to create Targets: " + targets[i][0]);
				}
			}else {
			log(LogStatus.FAIL, "Target tab is not clickable", YesNo.Yes);
			sa.assertTrue(false, "Target tab is not clickable");
		}
		}
		
		switchToDefaultContent(driver);
		lp.CRMlogout(environment, mode);//Change
		sa.assertAll();
	}
//
//@Parameters({ "environment", "mode" })
//@Test
//	public void AATc002_addField(String environment, String mode) {
//		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
//		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
//		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
//		List<String> abc = null;
//		lp.CRMLogin(superAdminUserName, adminPassword);
//		if (home.clickOnSetUpLink(environment, mode)) {
//			List<String> layoutName = new ArrayList<String>();
//			layoutName.add("Pipeline Layout");
//			HashMap<String, String> sourceANDDestination = new HashMap<String, String>();
//			sourceANDDestination.put("Active", "Stage");
//			abc = setup.DragNDrop(environment, mode, object.Deal, ObjectFeatureName.pageLayouts,
//					layoutName, sourceANDDestination);
//			
//			if (!abc.isEmpty()) {
//				log(LogStatus.FAIL, "field not added/already present 1", YesNo.Yes);
//				sa.assertTrue(false, "field not added/already present 1");
//			} else {
//				log(LogStatus.INFO, "field added/already present 1", YesNo.Yes);
//			}
//		} else {
//			sa.assertTrue(false, "Not able to click on setup link so cannot Add Field");
//			log(LogStatus.FAIL, "Not able to click on setup link so cannot Add Field", YesNo.Yes);
//		}
//	
//		ThreadSleep(5000);
//		////////////////////////
//	
//		if (home.clickOnSetUpLink(environment, mode)) {
//			List<String> layoutName = new ArrayList<String>();
//			layoutName.add("Fundraising Layout");
//			HashMap<String, String> sourceANDDestination = new HashMap<String, String>();
//			sourceANDDestination.put("Active", "Fundraising Name");
//			abc = setup.DragNDrop(environment, mode, object.Fundraising, ObjectFeatureName.pageLayouts,
//					layoutName, sourceANDDestination);
//	
//			if (!abc.isEmpty()) {
//				log(LogStatus.FAIL, "field not added/already present 2, Reason: " + abc, YesNo.Yes);
//				sa.assertTrue(false, "field not added/already present 2, Reason: " + abc);
//			} else {
//				log(LogStatus.INFO, "field added/already present 2", YesNo.No);
//	
//			}
//	
//		} else {
//			sa.assertTrue(false, "Not able to click on setup link so cannot Add Field");
//			log(LogStatus.FAIL, "Not able to click on setup link so cannot Add Field", YesNo.Yes);
//		}
//	
//		//////////////////////////
//	
//		if (lp.activateLighting(environment, Mode.Classic.toString(), 20)) {
//			log(LogStatus.PASS, "Able to activated Lighting", YesNo.No);
//		} else {
//			log(LogStatus.FAIL, "No Able to activate Lighting", YesNo.Yes);
//			sa.assertTrue(false, "No Able to activate Lighting");
//		}
//		home.switchToLighting();
//		lp.CRMlogout(environment, mode);
//		sa.assertAll();
//	}

@Parameters({ "projectName"})
@Test
	public void AATc003_VerifyCreatedDealAndTarget(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		DealPageBusinessLayer deal = new DealPageBusinessLayer(driver);
		FundraisingsPageBusinessLayer fp = new FundraisingsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		ThreadSleep(3000);
		if(deal.clickOnTab(environment, mode, TabName.Pipelines)) {
			log(LogStatus.PASS, "clicked on deal tab", YesNo.No);
			if(deal.clickOnCreatedDeal(environment, mode, ATDealName1)) {
				log(LogStatus.PASS, "clicked on created deal", YesNo.No);
				ThreadSleep(3000);
				
			}else {
				log(LogStatus.FAIL, "Not able to click on created deal "+ATDealName1, YesNo.Yes);
				sa.assertTrue(false,  "Not able to click on created deal "+ATDealName1);
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on deal tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on deal tab");
		}
		
		refresh(driver);
		ThreadSleep(3000);
		if(deal.clickOnTab(environment, mode, TabName.FundraisingsTab)) {
			log(LogStatus.PASS, "clicked on Fundraisings tab", YesNo.No);
			if(fp.clickOnCreatedFundraising(environment, mode, ATTargetName1)) {
				log(LogStatus.PASS, "clicked on created Fundraising", YesNo.No);
				ThreadSleep(3000);
				
			}else {
				log(LogStatus.FAIL, "Not able to click on created Fundraising "+ATDealName1, YesNo.Yes);
				sa.assertTrue(false,  "Not able to click on created Fundraising "+ATDealName1);
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on Fundraising tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on Fundraising tab");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
@Parameters({ "projectName" })
@Test
	public void AATc004_CreateDealTeam(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
	
		String dealName = ATDTDealName1;
		String contactName = ATDTContact1;
		String role = ATDTRole1;
	
		String[][] data = { { EditPageLabel.Deal.toString(), dealName },
				{ PageLabel.Deal_Contact.toString(), contactName },
				{ PageLabel.Role.toString(), role }};
	
		if (BP.openAppFromAppLauchner(60, "Deal Team")) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);
	    ThreadSleep(2000);
		if (DTP.createDealTeam(projectName, dealName, data,TabName.Acuity.toString(), action.SCROLLANDBOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----", YesNo.No);
	
			log(LogStatus.INFO,
					"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
							+ contactName + " at Firm Tab under Acuity section---------",
					YesNo.No);
				WebElement ele = DTP.getDealTeamID(10);
					if (ele != null) {
						String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
						ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name, "ATDT1",
								excelLabel.DealTeamID);
						log(LogStatus.INFO, "successfully created and noted id of DT" + id + " and deal name " + dealName,
								YesNo.No);
					}
				} else {
					sa.assertTrue(false, "could not create DT " + dealName);
					log(LogStatus.SKIP, "could not create DT " + dealName, YesNo.Yes);
				}
			} else {
					log(LogStatus.ERROR, "Not able to click on " + TabName.Deal_Team + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + TabName.Deal_Team + " tab");
			}
		lp.CRMlogout();
		sa.assertAll();
	}

@Parameters({ "projectName" })
@Test
	public void AATc005_CreateContactContactRole(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundRaisingPageBusinessLayer tp=new FundRaisingPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		String recordName=ATCRTargetName1;
		String contactName=ATCRContactName1;
		String role=ATCRRole1;
		String[][] data = { { PageLabel.Fundraising.toString(), recordName },
				{ PageLabel.Role.toString(), role }, { PageLabel.Contact.toString(), contactName } };
		lp.CRMLogin(crmUser1EmailID, adminPassword);
	
		if (BP.openAppFromAppLauchner(10, "Fundraising Contacts")) {
			log(LogStatus.INFO, "Fundraising Contacts has been open from the app launcher", YesNo.No);

			if (tp.createFundraisingContact(projectName, recordName, data, action.SCROLLANDBOOLEAN, 25)) {
				log(LogStatus.INFO,
						"----Successfully Created the Fundraising Contacts for Contact: " + contactName + "----",
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "could not create a new Fundraising Contacts", YesNo.Yes);
				sa.assertTrue(false, "could not create a new Fundraising Contacts");
			}
		} else {
			log(LogStatus.ERROR, "could not open a Fundraising Contacts tab", YesNo.Yes);
			sa.assertTrue(false, "could not open a Fundraising Contacts tab");
		}
	
		lp.CRMlogout();	
		sa.assertAll();	
	}

@Parameters({ "projectName"})
@Test
	public void AATc006_VerifyActiveCheckboxOnDealAndFundraising(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		PipelinesPageBusinessLayer tp= new PipelinesPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		if (ip.clickOnTab(projectName, TabName.Deals)) {
			  log(LogStatus.INFO, "Click on Tab : " + TabName.DealTab, YesNo.No);
		      if (ip.clickOnAlreadyCreatedItem(projectName, TabName.DealTab, ATDealName1, 10)) {
		    	  String[] fieldsWithValues= {PageLabel.Active.toString(),CheckBox.Checked.toString()};
		    	  ThreadSleep(2000);
					WebElement ele2 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele2, RelatedTab.Details.toString(), action.BOOLEAN);
					ThreadSleep(3000);
			tp.fieldValueVerificationOnPipelinePage(environment,mode, fieldsWithValues[0],fieldsWithValues[1]);
			String xpath = "//span[text()='"+fieldsWithValues[0]+"']/../following-sibling::div//lightning-primitive-input-checkbox[@ " + fieldsWithValues[1] +" ]";
			
			WebElement ele = 	FindElement(driver, xpath, fieldsWithValues[0] + " label text with  " + fieldsWithValues[1], action.SCROLLANDBOOLEAN, 10);
			scrollDownThroughWebelement(driver, ele, fieldsWithValues[0] + " label text with  " + fieldsWithValues[1]);
			ele = 	isDisplayed(driver,ele,"Visibility", 10, fieldsWithValues[0] + " label text with  " + fieldsWithValues[1]);
			if (ele != null) {
				appLog.info(fieldsWithValues[0] + " label text with  " + fieldsWithValues[1]+" verified");
			} else {
				appLog.error("<<<<<<   "+fieldsWithValues[0] + " label text with  " + fieldsWithValues[1]+" not verified "+"   >>>>>>");
			}

			ThreadSleep(3000);
		}else {
			appLog.error("Deal name is not clickable so cannot verify Active checkbox functionality");
			sa.assertTrue(false, "Deal name is not clickable so cannot verify Active checkbox functionality");
		}
		}else {
			appLog.error("Deal tab is not clickable");
			sa.assertTrue(false, "deal tab is not clickable");
		}
		
		refresh(driver);
		ThreadSleep(3000);
		
		if (ip.clickOnTab(projectName, TabName.FundraisingsTab)) {
			  log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
		      if (ip.clickOnAlreadyCreatedItem(projectName, TabName.FundraisingsTab, ATTargetName1, 10)) {
		    	  String[] fieldsWithValues= {PageLabel.Active.toString(),CheckBox.Checked.toString()};
		    	  ThreadSleep(2000);
					WebElement ele2 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele2, RelatedTab.Details.toString(), action.BOOLEAN);
					ThreadSleep(3000);
			tp.fieldValueVerificationOnPipelinePage(environment,mode, fieldsWithValues[0],fieldsWithValues[1]);
			String xpath = "//span[text()='"+fieldsWithValues[0]+"']/../following-sibling::div//lightning-primitive-input-checkbox[@ " + fieldsWithValues[1] +" ]";
			
			WebElement ele = 	FindElement(driver, xpath, fieldsWithValues[0] + " label text with  " + fieldsWithValues[1], action.SCROLLANDBOOLEAN, 10);
			scrollDownThroughWebelement(driver, ele, fieldsWithValues[0] + " label text with  " + fieldsWithValues[1]);
			ele = 	isDisplayed(driver,ele,"Visibility", 10, fieldsWithValues[0] + " label text with  " + fieldsWithValues[1]);
			if (ele != null) {
				appLog.info(fieldsWithValues[0] + " label text with  " + fieldsWithValues[1]+" verified");
			} else {
				appLog.error("<<<<<<   "+fieldsWithValues[0] + " label text with  " + fieldsWithValues[1]+" not verified "+"   >>>>>>");
			}

			ThreadSleep(3000);
		}else {
			appLog.error("Fundraising name is not clickable so cannot verify Active checkbox functionality");
			sa.assertTrue(false, "Fundraising name is not clickable so cannot verify Active checkbox functionality");
		}
		}else {
			appLog.error("Fundraising tab is not clickable");
			sa.assertTrue(false, "Fundraising tab is not clickable");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

@Parameters({ "projectName" })
@Test
	public void AATc007_CreateRevenueInboxEvent(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		ThreadSleep(5000);
	
		APIUtils Api = new APIUtils();
		String eventTitle6= ATETSubject1;
		String[] contactName = {ATConFN1 +" "+ ATConLN1};
		String dealName = ATDealName1;
		String targetName = ATTargetName1;
		String[] firmsTaggedName = {ATFirm1};
		String[] firmsTaggedCount = {"1"};
		String[] contactsTaggedCount = {"1"};
//		String[] userAndContact6=ATETRelated1.split("<userBreak>");
//		String eventAttendees6=userAndContact6[0];
		String startDate6 = todaysDate;
		ExcelUtils.writeData(AcuityDataSheetFilePath, startDate6, "Activity Timeline", excelLabel.Variable_Name,
				"ATET01", excelLabel.Advance_Start_Date);
	
		String endDate6 = todaysDate;
		ExcelUtils.writeData(AcuityDataSheetFilePath, endDate6, "Activity Timeline", excelLabel.Variable_Name,
				"ATET01", excelLabel.Advance_End_Date);
	
		String descriptionBox6 = ATETNote1;
		
		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle6 + " through Outlook---------",
				YesNo.No);
		String sDate =  Api.convertDate(startDate6);
		String eDate =  Api.convertDate(endDate6);
		String RT = Api.getObjectRecordId("Contact", ATETRelated1);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(ApiHeader.Subject.toString(), eventTitle6);
		data.put(ApiHeader.StartDateTime.toString(), sDate);
		data.put(ApiHeader.EndDateTime.toString(), eDate);
		data.put(ApiHeader.Description.toString(), descriptionBox6);
		data.put(ApiHeader.WhoId.toString(), RT);
		
		String id = Api.EventObjectDataUpload(data);
		if (id != null) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle6 + " has been created-----",
					YesNo.No);
		}
	
		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle6
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle6
					+ " has not been created-----");
		}
	
		ThreadSleep(5000);
		
		if (lp.clickOnTab(projectName, TabName.ContactTab)) {
		
			log(LogStatus.INFO, "Click on Tab : " + TabName.ContactTab, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.ContactTab , contactName[0], 10)){
			log(LogStatus.INFO, "Click on contact : " + contactName[0], YesNo.No);
			ThreadSleep(5000);
			if (BP.InteractionRecord(eventTitle6,10) != null) {
				log(LogStatus.INFO,
						"Records on Intraction card have been verified with name " + eventTitle6,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Records on Intraction card is not created with name " + eventTitle6, YesNo.No);
				sa.assertTrue(false, "Records on Intraction card is not created with name " + eventTitle6);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + contactName[0], YesNo.No);
			sa.assertTrue(false, "Not able to click on " + contactName[0]);
		}
		}else {
			log(LogStatus.ERROR, "could not click on " + TabName.ContactTab, YesNo.Yes);
			sa.assertTrue(false,"could not click on " + TabName.ContactTab );
		}
		
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.Pipelines)) {
			
			log(LogStatus.INFO, "Click on Tab : " + TabName.Pipelines, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.Pipelines , dealName, 10)){
			log(LogStatus.INFO, "Click on contact : " + dealName, YesNo.No);
			ThreadSleep(5000);
		
		clickUsingJavaScript(driver, BP.getPeopleTabOnTagged(10), "People Tab");
		ThreadSleep(4000);
		
		ArrayList<String> result5=BP.verifyRecordAndReferencedTypeOnTagged(firmsTaggedName, firmsTaggedCount, contactName, contactsTaggedCount, null, null, false,null,null);
		if(result5.isEmpty())
		{
			log(LogStatus.INFO, "The record name and Contact Name have been verifed", YesNo.No);
		}
		else
		{
			log(LogStatus.ERROR,  "The record name and Contact Name are not verifed. "+result5, YesNo.No);
			sa.assertTrue(false,  "The record name and Contact Name are not verifed."+result5);
		}
		
		} else {
			log(LogStatus.ERROR, "Not able to click on " + dealName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + dealName);
		}
		}else {
			log(LogStatus.ERROR, "could not click on " + TabName.Pipelines, YesNo.Yes);
			sa.assertTrue(false,"could not click on " + TabName.Pipelines );
		}
		
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
			
			log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.FundraisingsTab , targetName, 10)){
			log(LogStatus.INFO, "Click on contact : " + targetName, YesNo.No);
			ThreadSleep(5000);
		
		clickUsingJavaScript(driver, BP.getPeopleTabOnTagged(10), "People Tab");
		ThreadSleep(4000);
		
		ArrayList<String> result5=BP.verifyRecordAndReferencedTypeOnTagged(firmsTaggedName, firmsTaggedCount, contactName, contactsTaggedCount, null, null, false,null,null);
		if(result5.isEmpty())
		{
			log(LogStatus.INFO, "The record name and Contact Name have been verifed", YesNo.No);
		}
		else
		{
			log(LogStatus.ERROR,  "The record name and Contact Name are not verifed. "+result5, YesNo.No);
			sa.assertTrue(false,  "The record name and Contact Name are not verifed."+result5);
		}
		
		} else {
			log(LogStatus.ERROR, "Not able to click on " + targetName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + targetName);
		}
		}else {
			log(LogStatus.ERROR, "could not click on " + TabName.FundraisingsTab, YesNo.Yes);
			sa.assertTrue(false,"could not click on " + TabName.FundraisingsTab );
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

@Parameters({ "projectName" })
@Test
	public void AATc008_CreateRevenueInboxCall(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		String activityType1=ATETAction2;
		String taskSubject1=ATETSubject2;
		String taskRelatedTo1=ATETRelated2;
		String taskNotes1=ATETNote2;
		String[] contactName = {ATConFN1 +" "+ ATConLN1};
		String dealName = ATDealName1;
		String targetName = ATTargetName1;
		//ATETSubject1,
		String[] subjects = {ATETSubject1,ATETSubject2};
		String taskDueDate1 = todaysDate;
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate1, "Activity Timeline", excelLabel.Variable_Name,
				"ATET02", excelLabel.Advance_Due_Date);
		
		String[][] basicsection1 = { { excelLabel.Subject.toString(), taskSubject1 }, { excelLabel.Notes.toString(), taskNotes1 }, { excelLabel.Related_To.toString(), taskRelatedTo1 } };
		String[][] advanceSection1 = { { excelLabel.Date.toString(), taskDueDate1 } };
	
		if (BP.createActivityTimeline(projectName, true, activityType1, basicsection1, advanceSection1, null, null, false, null, null,null, null,null,null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject1);
	
		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject1);
		}	
		
	ThreadSleep(5000);	
		if (lp.clickOnTab(projectName, TabName.ContactTab)) {
			
				log(LogStatus.INFO, "Click on Tab : " + TabName.ContactTab, YesNo.No);
				ThreadSleep(3000);
				
			if(lp.clickOnAlreadyCreated(environment, mode,TabName.ContactTab , contactName[0], 10)){
				log(LogStatus.INFO, "Click on contact : " + contactName[0], YesNo.No);
				ThreadSleep(5000);
				for(String subject : subjects) {
				if (BP.InteractionRecord(subject,10) != null) {
					log(LogStatus.INFO,
							"Records on Intraction card have been verified with name "+subject ,
							YesNo.No);
				} else {
					log(LogStatus.ERROR, "Records on Intraction card is not created with name "+subject , YesNo.No);
					sa.assertTrue(false, "Records on Intraction card is not created with name "+subject );
				}
					}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + contactName[0], YesNo.No);
				sa.assertTrue(false, "Not able to click on " + contactName[0]);
			}
		}else {
		log(LogStatus.ERROR, "could not click on " + TabName.ContactTab, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.ContactTab );
	}
		
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.Deals)) {
			
			log(LogStatus.INFO, "Click on Tab : " + TabName.Deals, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.Pipelines , dealName, 10)){
			log(LogStatus.INFO, "Click on contact : " + dealName, YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects) { 
			if (BP.InteractionRecord(subject,10) != null) {
				log(LogStatus.INFO,
						"Records on Intraction card have been verified with name " + subject,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
				sa.assertTrue(false, "Records on Intraction card is not created with name " + subject);
			}
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + dealName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + dealName);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.Deals, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.Deals );
	}		
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.FundraisingsTab , targetName, 10)){
			log(LogStatus.INFO, "Click on contact : " + targetName, YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects) { 
			if (BP.InteractionRecord(subject,10) != null) {
				log(LogStatus.INFO,
						"Records on Intraction card have been verified with name " + subject,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
				sa.assertTrue(false, "Records on Intraction card is not created with name " + subject);
			}
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + targetName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + targetName);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.FundraisingsTab, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.FundraisingsTab );
	}		
		lp.CRMlogout();
		sa.assertAll();
	}

@Parameters({ "projectName" })
@Test
	public void AATc009_CreateRevenueInboxTask(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		String activityType1=ATETAction3;
		String taskSubject1=ATETSubject3;
		String taskRelatedTo1=ATETRelated3;
		String taskNotes1=ATETNote3;
		String[] contactName = {ATConFN1 +" "+ ATConLN1};
		String[] firmsTaggedName = {AT_TaggedFirmsName1};
		String[] firmsTaggedCount = {AT_TaggedFirmsCount1};
		String[] PeopleTaggedName = {AT_TaggedPeopleName1};
		String[] PeopleTaggedCount = {AT_TaggedPeopleCount1};
		String dealName = ATDealName1;
		String targetName = ATTargetName1;
		String[] subjects = {ATETSubject1,ATETSubject2,ATETSubject3};
		String taskDueDate1 = tomorrowsDate;
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate1, "Activity Timeline", excelLabel.Variable_Name,
				"ATET03", excelLabel.Advance_Due_Date);
		String assignedTo1=ATETPriority3;
		String status=ATETStatus3;
		
		String[][] basicsection1 = { { excelLabel.Subject.toString(), taskSubject1 }, { excelLabel.Notes.toString(), taskNotes1 }, { excelLabel.Related_To.toString(), taskRelatedTo1 } };
		String[][] advanceSection1 = { { excelLabel.Due_Date.toString(), taskDueDate1 }, {excelLabel.Priority.toString(), assignedTo1}, {excelLabel.Status.toString(), status} };
	
		if (BP.createActivityTimeline(projectName, true, activityType1, basicsection1, advanceSection1, null, null, false, null, null,null, null,null,null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject1);
	
		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject1);
		}	
		
		ThreadSleep(5000);
		if (lp.clickOnTab(projectName, TabName.ContactTab)) {
		
			log(LogStatus.INFO, "Click on Tab : " + TabName.ContactTab, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.ContactTab , contactName[0], 10)){
			log(LogStatus.INFO, "Click on contact : " + contactName[0], YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects) {
			if (BP.InteractionRecord(subject,10) != null) {
				log(LogStatus.INFO,
						"Records on Intraction card have been verified with name " + subject,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
				sa.assertTrue(false, "Records on Intraction card is not created with name " + subject);
			}
				}	
		} else {
			log(LogStatus.ERROR, "Not able to click on " + contactName[0], YesNo.No);
			sa.assertTrue(false, "Not able to click on " + contactName[0]);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.ContactTab, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.ContactTab );
	}
		
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.Deals)) {
			
			log(LogStatus.INFO, "Click on Tab : " + TabName.Deals, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.Pipelines , dealName, 10)){
			log(LogStatus.INFO, "Click on contact : " + dealName, YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects) { 
			if (BP.InteractionRecord(subject,10) != null) {
				log(LogStatus.INFO,
						"Records on Intraction card have been verified with name " + subject,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
				sa.assertTrue(false, "Records on Intraction card is not created with name " + subject);
			}
				}
			
			ThreadSleep(2000);
			clickUsingJavaScript(driver, BP.getPeopleTabOnTagged(10), "People Tab");
			ThreadSleep(4000);
			
			ArrayList<String> result5=BP.verifyRecordAndReferencedTypeOnTagged(firmsTaggedName, firmsTaggedCount, PeopleTaggedName, PeopleTaggedCount, null, null,false, null,null);
			if(result5.isEmpty())
			{
				log(LogStatus.INFO, "The record name and Contact Name have been verifed", YesNo.No);
			}
			else
			{
				log(LogStatus.ERROR,  "The record name and Contact Name are not verifed. "+result5, YesNo.No);
				sa.assertTrue(false,  "The record name and Contact Name are not verifed."+result5);
			}
			
		} else {
			log(LogStatus.ERROR, "Not able to click on " + dealName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + dealName);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.Deals, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.Deals );
	}		
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.FundraisingsTab , targetName, 10)){
			log(LogStatus.INFO, "Click on contact : " + targetName, YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects) { 
			if (BP.InteractionRecord(subject,10) != null) {
				log(LogStatus.INFO,
						"Records on Intraction card have been verified with name " + subject,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
				sa.assertTrue(false, "Records on Intraction card is not created with name " + subject);
			}
				}
			ThreadSleep(2000);
			clickUsingJavaScript(driver, BP.getPeopleTabOnTagged(10), "People Tab");
			ThreadSleep(4000);
			
			ArrayList<String> result5=BP.verifyRecordAndReferencedTypeOnTagged(firmsTaggedName, firmsTaggedCount, PeopleTaggedName, PeopleTaggedCount, null, null, false,null,null);
			if(result5.isEmpty())
			{
				log(LogStatus.INFO, "The record name and Contact Name have been verifed", YesNo.No);
			}
			else
			{
				log(LogStatus.ERROR,  "The record name and Contact Name are not verifed. "+result5, YesNo.No);
				sa.assertTrue(false,  "The record name and Contact Name are not verifed."+result5);
			}
			
		} else {
			log(LogStatus.ERROR, "Not able to click on " + targetName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + targetName);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.FundraisingsTab, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.FundraisingsTab );
	}		
		lp.CRMlogout();
		sa.assertAll();
	}

@Parameters({ "projectName" })
@Test
	public void AATc010_ChangeDealStageToDecline(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	DealPageBusinessLayer dp=new DealPageBusinessLayer(driver);
	String dealName=ATDealName1;
//	String dealStatus=ATDealNewStatus1;
	String dealStage=ATDealNewStage1;
	lp.CRMLogin(crmUser1EmailID, adminPassword);
	
			if (lp.clickOnTab(projectName, TabName.Deals)) {
				log(LogStatus.INFO, "Clicked on Tab : " + TabName.Deals, YesNo.No);
		
				if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.Pipelines,dealName, 30)) {
					log(LogStatus.INFO, dealName + " record has been open", YesNo.No);
					
					if(dp.changeDealStatusAndDealStage(projectName,dealName,dealStage,20)) {
						log(LogStatus.INFO,"Deal Stage "+dealStage+" have been changed of record "+dealName, YesNo.No);	
					}
					else {
						log(LogStatus.ERROR,"Deal Stage "+dealStage+" is not changed of record "+dealName, YesNo.No);
						sa.assertTrue(false,  "Deal Stage "+dealStage+" is not changed of record "+dealName);
					}				
				}
				else {
					log(LogStatus.ERROR, "Not able to open record "+dealName, YesNo.No);
					sa.assertTrue(false,  "Not able to open record "+dealName);
				}
			}
			else {
				log(LogStatus.ERROR, "Not able to click on tab "+TabName.Pipelines, YesNo.No);
				sa.assertTrue(false,  "Not able to click on tab "+TabName.Pipelines);
			}
	lp.CRMlogout();	
	sa.assertAll();	
	}

@Parameters({ "projectName"})
@Test
	public void AATc011_VerifyInactiveCheckboxOnDeal(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		PipelinesPageBusinessLayer tp= new PipelinesPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		if (ip.clickOnTab(projectName, TabName.Deals)) {
			  log(LogStatus.INFO, "Click on Tab : " + TabName.DealTab, YesNo.No);
		      if (ip.clickOnAlreadyCreatedItem(projectName, TabName.DealTab, ATDealName1, 10)) {
		    	  String[] fieldsWithValues= {PageLabel.Active.toString(),CheckBox.Checked.toString()};
		    	  ThreadSleep(2000);
					WebElement ele2 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele2, RelatedTab.Details.toString(), action.BOOLEAN);
					ThreadSleep(3000);
			tp.fieldValueVerificationOnPipelinePage(environment,mode, fieldsWithValues[0],fieldsWithValues[1]);
			String xpath = "//span[text()='"+fieldsWithValues[0]+"']/../following-sibling::div//lightning-primitive-input-checkbox[@ " + fieldsWithValues[1] +" ]";
			
			WebElement ele = 	FindElement(driver, xpath, fieldsWithValues[0] + " label text with  " + fieldsWithValues[1], action.SCROLLANDBOOLEAN, 10);
			scrollDownThroughWebelement(driver, ele, fieldsWithValues[0] + " label text with  " + fieldsWithValues[1]);
			ele = 	isDisplayed(driver,ele,"Visibility", 10, fieldsWithValues[0] + " label text with  " + fieldsWithValues[1]);
			if (ele == null) {
				appLog.info(fieldsWithValues[0] + " label text with  " + fieldsWithValues[1]+" verified");
			} else {
				appLog.error("<<<<<<   "+fieldsWithValues[0] + " label text with  " + fieldsWithValues[1]+" not verified "+"   >>>>>>");
			}

			ThreadSleep(3000);
		}else {
			appLog.error("Deal name is not clickable so cannot verify Active checkbox functionality");
			sa.assertTrue(false, "Deal name is not clickable so cannot verify Active checkbox functionality");
		}
		}else {
			appLog.error("Deal tab is not clickable");
			sa.assertTrue(false, "deal tab is not clickable");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

@Parameters({ "projectName" })
@Test
	public void AATc012_CreateRevenueInboxEvent(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		ThreadSleep(5000);
		APIUtils Api = new APIUtils();
		String eventTitle6= ATETSubject4;
		String[] contactName = {ATConFN1 +" "+ ATConLN1};
		String dealName = ATDealName1;
		String targetName = ATTargetName1;
		String[] subjects = {ATETSubject1,ATETSubject2,ATETSubject3};
		String[] subjects1 = {ATETSubject1,ATETSubject2,ATETSubject3,ATETSubject4};
//		String[] userAndContact6=ATETRelated4.split("<userBreak>");
//		String eventAttendees6=userAndContact6[0];
		String startDate6 = todaysDate;
		ExcelUtils.writeData(AcuityDataSheetFilePath, startDate6, "Activity Timeline", excelLabel.Variable_Name,
				"ATET04", excelLabel.Advance_Start_Date);
	
		String endDate6 = todaysDate;
		ExcelUtils.writeData(AcuityDataSheetFilePath, endDate6, "Activity Timeline", excelLabel.Variable_Name,
				"ATET04", excelLabel.Advance_End_Date);
	
		String descriptionBox6 = ATETNote4;
		
		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle6 + " through Outlook---------",
				YesNo.No);
		String sDate =  Api.convertDate(startDate6);
		String eDate =  Api.convertDate(endDate6);
		String RT = Api.getObjectRecordId("Contact", ATETRelated4);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(ApiHeader.Subject.toString(), eventTitle6);
		data.put(ApiHeader.StartDateTime.toString(), sDate);
		data.put(ApiHeader.EndDateTime.toString(), eDate);
		data.put(ApiHeader.Description.toString(), descriptionBox6);
		data.put(ApiHeader.WhoId.toString(), RT);
		
		String id = Api.EventObjectDataUpload(data);
		if (id != null) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle6 + " has been created-----",
					YesNo.No);
		}
	
		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle6
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle6
					+ " has not been created-----");
		}
	
		ThreadSleep(5000);
		if (lp.clickOnTab(projectName, TabName.ContactTab)) {
		
			log(LogStatus.INFO, "Click on Tab : " + TabName.ContactTab, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.ContactTab , contactName[0], 10)){
			log(LogStatus.INFO, "Click on contact : " + contactName[0], YesNo.No);
			ThreadSleep(5000);
			if (BP.InteractionRecord(eventTitle6,10) != null) {
				log(LogStatus.INFO,
						"Records on Intraction card have been verified with name " + eventTitle6,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Records on Intraction card is not created with name " + eventTitle6, YesNo.No);
				sa.assertTrue(false, "Records on Intraction card is not created with name " + eventTitle6);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + contactName[0], YesNo.No);
			sa.assertTrue(false, "Not able to click on " + contactName[0]);
		}
		}else {
			log(LogStatus.ERROR, "could not click on " + TabName.ContactTab, YesNo.Yes);
			sa.assertTrue(false,"could not click on " + TabName.ContactTab );
		}
		
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.Pipelines)) {
			
			log(LogStatus.INFO, "Click on Tab : " + TabName.Pipelines, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.Pipelines , dealName, 10)){
			log(LogStatus.INFO, "Click on contact : " + dealName, YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects) { 
			if (BP.InteractionRecord(subject,10) != null) {
				log(LogStatus.INFO,
						"Records on Intraction card have been verified with name " + subject,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
				sa.assertTrue(false, "Records on Intraction card is not created with name " + subject);
			}
			
			if (BP.InteractionRecord(eventTitle6, 7) == null) {
				log(LogStatus.INFO, "Subject: " + eventTitle6 + " not found on All Interaction Page",
						YesNo.No);

			} else {
				log(LogStatus.ERROR, "Subject: " + eventTitle6 + " found on All Interaction Page",
						YesNo.Yes);
				sa.assertTrue(false, "Subject: " + eventTitle6 + " found on All Interaction Page");

			}
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + dealName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + dealName);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.Pipelines, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.Pipelines );
	}		
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.FundraisingsTab , targetName, 10)){
			log(LogStatus.INFO, "Click on contact : " + targetName, YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects1) { 
			if (BP.InteractionRecord(subject,10) != null) {
				log(LogStatus.INFO,
						"Records on Intraction card have been verified with name " + subject,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
				sa.assertTrue(false, "Records on Intraction card is not created with name " + subject);
			}
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + targetName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + targetName);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.FundraisingsTab, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.FundraisingsTab );
	}		
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
@Parameters({ "projectName" })
@Test
	public void AATc013_CreateRevenueInboxCall(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		String activityType1=ATETAction5;
		String taskSubject1=ATETSubject5;
		String taskRelatedTo1=ATETRelated5;
		String taskNotes1=ATETNote5;
		String[] contactName = {ATConFN1 +" "+ ATConLN1};
		String dealName = ATDealName1;
		String targetName = ATTargetName1;
		String[] subjects = {ATETSubject1,ATETSubject2,ATETSubject3};
		String[] subjects1 = {ATETSubject1,ATETSubject2,ATETSubject3,ATETSubject4,ATETSubject5};
		String taskDueDate1 = todaysDate;
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate1, "Activity Timeline", excelLabel.Variable_Name,
				"ATET05", excelLabel.Advance_Due_Date);
		
		String[][] basicsection1 = { { excelLabel.Subject.toString(), taskSubject1 }, { excelLabel.Notes.toString(), taskNotes1 }, { excelLabel.Related_To.toString(), taskRelatedTo1 } };
		String[][] advanceSection1 = { { excelLabel.Date.toString(), taskDueDate1 } };
	
		if (BP.createActivityTimeline(projectName, true, activityType1, basicsection1, advanceSection1, null, null, false, null, null,null, null,null,null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject1);
	
		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject1);
		}	
		
	ThreadSleep(5000);	
		if (lp.clickOnTab(projectName, TabName.ContactTab)) {
			
				log(LogStatus.INFO, "Click on Tab : " + TabName.ContactTab, YesNo.No);
				ThreadSleep(3000);
				
			if(lp.clickOnAlreadyCreated(environment, mode,TabName.ContactTab , contactName[0], 10)){
				log(LogStatus.INFO, "Click on contact : " + contactName[0], YesNo.No);
				ThreadSleep(5000);
				for(String subject : subjects) {
				if (BP.verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(subject)) {
					log(LogStatus.INFO,
							"Records on Intraction card have been verified with name "+subject ,
							YesNo.No);
				} else {
					log(LogStatus.ERROR, "Records on Intraction card is not created with name "+subject , YesNo.No);
					sa.assertTrue(false, "Records on Intraction card is not created with name "+subject );
				}
					}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + contactName[0], YesNo.No);
				sa.assertTrue(false, "Not able to click on " + contactName[0]);
			}
		}else {
		log(LogStatus.ERROR, "could not click on " + TabName.ContactTab, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.ContactTab );
	}
		
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.Deals)) {
			
			log(LogStatus.INFO, "Click on Tab : " + TabName.Deals, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.Pipelines , dealName, 10)){
			log(LogStatus.INFO, "Click on contact : " + dealName, YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects) { 
			if (BP.InteractionRecord(subject,10) != null) {
				log(LogStatus.INFO,
						"Records on Intraction card have been verified with name " + subject,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
				sa.assertTrue(false, "Records on Intraction card is not created with name " + subject);
			}
			
			if (BP.InteractionRecord(taskSubject1, 7) == null) {
				log(LogStatus.INFO, "Subject: " + taskSubject1 + " not found on All Interaction Page",
						YesNo.No);

			} else {
				log(LogStatus.ERROR, "Subject: " + taskSubject1 + " found on All Interaction Page",
						YesNo.Yes);
				sa.assertTrue(false, "Subject: " + taskSubject1 + " found on All Interaction Page");

			}
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + dealName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + dealName);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.Deals, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.Deals );
	}		
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.FundraisingsTab , targetName, 10)){
			log(LogStatus.INFO, "Click on contact : " + targetName, YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects1) { 
			if (BP.verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(subject)) {
				log(LogStatus.INFO,
						"Records on Intraction card have been verified with name " + subject,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
				sa.assertTrue(false, "Records on Intraction card is not created with name " + subject);
			}
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + targetName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + targetName);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.FundraisingsTab, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.FundraisingsTab );
	}		
		lp.CRMlogout();
		sa.assertAll();
	}

@Parameters({ "projectName" })
@Test
	public void AATc014_CreateRevenueInboxTask(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		String activityType1=ATETAction6;
		String taskSubject1=ATETSubject6;
		String taskRelatedTo1=ATETRelated6;
		String taskNotes1=ATETNote6;
		String[] contactName = {ATConFN1 +" "+ ATConLN1};
		String[] firmsTaggedName = {AT_TaggedFirmsName2};
		String[] firmsTaggedCount = {AT_TaggedFirmsCount2};
		String[] PeopleTaggedName = {AT_TaggedPeopleName2};
		String[] PeopleTaggedCount = {AT_TaggedPeopleCount2};
		String[] firmsTaggedName1 = {AT_TaggedFirmsName3};
		String[] firmsTaggedCount1 = {AT_TaggedFirmsCount3};
		String[] PeopleTaggedName1 = {AT_TaggedPeopleName3};
		String[] PeopleTaggedCount1 = {AT_TaggedPeopleCount3};
		String dealName = ATDealName1;
		String targetName = ATTargetName1;
		String[] subjects = {ATETSubject1,ATETSubject2,ATETSubject3};
		String[] subjects1 = {ATETSubject1,ATETSubject2,ATETSubject3,ATETSubject4,ATETSubject5,ATETSubject6};
		String taskDueDate1 = tomorrowsDate;
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate1, "Activity Timeline", excelLabel.Variable_Name,
				"ATET06", excelLabel.Advance_Due_Date);
		String assignedTo1=ATETPriority6;
		String status=ATETStatus6;
		
		String[][] basicsection1 = { { excelLabel.Subject.toString(), taskSubject1 }, { excelLabel.Notes.toString(), taskNotes1 }, { excelLabel.Related_To.toString(), taskRelatedTo1 } };
		String[][] advanceSection1 = { { excelLabel.Due_Date.toString(), taskDueDate1 }, {excelLabel.Priority.toString(), assignedTo1}, {excelLabel.Status.toString(), status} };
	
		if (BP.createActivityTimeline(projectName, true, activityType1, basicsection1, advanceSection1, null, null, false, null, null,null, null,null,null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject1);
	
		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject1);
		}	
		
		ThreadSleep(5000);
		if (lp.clickOnTab(projectName, TabName.ContactTab)) {
		
			log(LogStatus.INFO, "Click on Tab : " + TabName.ContactTab, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.ContactTab , contactName[0], 10)){
			log(LogStatus.INFO, "Click on contact : " + contactName[0], YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects) {
				if (BP.verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(subject)) {
				log(LogStatus.INFO,
						"Records on Intraction card have been verified with name " + subject,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
				sa.assertTrue(false, "Records on Intraction card is not created with name " + subject);
			}
				}	
		} else {
			log(LogStatus.ERROR, "Not able to click on " + contactName[0], YesNo.No);
			sa.assertTrue(false, "Not able to click on " + contactName[0]);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.ContactTab, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.ContactTab );
	}
		
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.Deals)) {
			
			log(LogStatus.INFO, "Click on Tab : " + TabName.Deals, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.Pipelines , dealName, 10)){
			log(LogStatus.INFO, "Click on contact : " + dealName, YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects) { 
			if (BP.InteractionRecord(subject,10) != null) {
				log(LogStatus.INFO,
						"Records on Intraction card have been verified with name " + subject,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
				sa.assertTrue(false, "Records on Intraction card is not created with name " + subject);
			}
			
			if (BP.InteractionRecord(taskSubject1, 7) == null) {
				log(LogStatus.INFO, "Subject: " + taskSubject1 + " not found on All Interaction Page",
						YesNo.No);

			} else {
				log(LogStatus.ERROR, "Subject: " + taskSubject1 + " found on All Interaction Page",
						YesNo.Yes);
				sa.assertTrue(false, "Subject: " + taskSubject1 + " found on All Interaction Page");

			}
				}
			
			ThreadSleep(2000);
			clickUsingJavaScript(driver, BP.getPeopleTabOnTagged(10), "People Tab");
			ThreadSleep(4000);
			
			ArrayList<String> result5=BP.verifyRecordAndReferencedTypeOnTagged(firmsTaggedName, firmsTaggedCount, PeopleTaggedName, PeopleTaggedCount, null, null,false,null,null);
			if(result5.isEmpty())
			{
				log(LogStatus.INFO, "The record name and Contact Name have been verifed", YesNo.No);
			}
			else
			{
				log(LogStatus.ERROR,  "The record name and Contact Name are not verifed. "+result5, YesNo.No);
				sa.assertTrue(false,  "The record name and Contact Name are not verifed."+result5);
			}
			
		} else {
			log(LogStatus.ERROR, "Not able to click on " + dealName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + dealName);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.Deals, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.Deals );
	}		
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.FundraisingsTab , targetName, 10)){
			log(LogStatus.INFO, "Click on contact : " + targetName, YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects1) { 
			if (BP.verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(subject)) {
				log(LogStatus.INFO,
						"Records on Intraction card have been verified with name " + subject,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
				sa.assertTrue(false, "Records on Intraction card is not created with name " + subject);
			}
				}
			ThreadSleep(2000);
			clickUsingJavaScript(driver, BP.getPeopleTabOnTagged(10), "People Tab");
			ThreadSleep(4000);
			
			ArrayList<String> result5=BP.verifyRecordAndReferencedTypeOnTagged(firmsTaggedName1, firmsTaggedCount1, PeopleTaggedName1, PeopleTaggedCount1, null, null,false,null,null);
			if(result5.isEmpty())
			{
				log(LogStatus.INFO, "The record name and Contact Name have been verifed", YesNo.No);
			}
			else
			{
				log(LogStatus.ERROR,  "The record name and Contact Name are not verifed. "+result5, YesNo.No);
				sa.assertTrue(false,  "The record name and Contact Name are not verifed."+result5);
			}
			
		} else {
			log(LogStatus.ERROR, "Not able to click on " + targetName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + targetName);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.FundraisingsTab, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.FundraisingsTab );
	}		
		lp.CRMlogout();
		sa.assertAll();
	}

@Parameters({ "projectName" })
@Test
	public void AATc015_ChangeDealStageToDecline(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	DealPageBusinessLayer dp=new DealPageBusinessLayer(driver);
	FundraisingsPageBusinessLayer tp = new FundraisingsPageBusinessLayer(driver);
	String dealName=ATDealName1;
	String dealStage=ATDealNewStage2;
	String dealstatus=ATDealStatus2;
	lp.CRMLogin(crmUser1EmailID, adminPassword);
	
			if (lp.clickOnTab(projectName, TabName.Deals)) {
				log(LogStatus.INFO, "Clicked on Tab : " + TabName.Deals, YesNo.No);
		
				if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.Pipelines,dealName, 30)) {
					log(LogStatus.INFO, dealName + " record has been open", YesNo.No);
					
					if(dp.changeDealStatusAndDealStage(projectName,dealName,dealStage,20)) {
						log(LogStatus.INFO,"Deal Stage "+dealStage+" and deal status "+dealstatus+" have been changed of record "+dealName, YesNo.No);	
					}
					else {
						log(LogStatus.ERROR,"Deal Stage "+dealStage+" is not changed of record "+dealName, YesNo.No);
						sa.assertTrue(false,  "Deal Stage "+dealStage+" is not changed of record "+dealName);
					}				
				}
				else {
					log(LogStatus.ERROR, "Not able to open record "+dealName, YesNo.No);
					sa.assertTrue(false,  "Not able to open record "+dealName);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on tab "+TabName.Deals, YesNo.No);
				sa.assertTrue(false,  "Not able to click on tab "+TabName.Deals);
			}
			
			ThreadSleep(2000);
			String stageValue = Stage.Legal_Closed.toString().replace("_", " ");
			String targetName = ATTargetName1;
			ExcelUtils.writeData(AcuityDataSheetFilePath, stageValue, "Fundraising", excelLabel.Variable_Name, "ATTarget1",
					excelLabel.Closing);
			if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
				log(LogStatus.INFO, "Clicked on Tab : " + TabName.FundraisingsTab, YesNo.No);
		
				if (bp.clickOnAlreadyCreated_Lighting(environment, mode, TabName.FundraisingsTab,targetName, 30)) {
					log(LogStatus.INFO, dealName + " record has been open", YesNo.No);
					
					if (tp.changeFundraisingStage(projectName, mode, stageValue, 10)) {
						log(LogStatus.INFO, "not able to change stage to " + stageValue, YesNo.No);
					} else {
						sa.assertTrue(false, "not able to change stage to " + stageValue);
						log(LogStatus.SKIP, "not able to change stage to " + stageValue, YesNo.Yes);
					}			
				}
				else {
					log(LogStatus.ERROR, "Not able to open record "+targetName, YesNo.No);
					sa.assertTrue(false,  "Not able to open record "+targetName);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on tab "+TabName.FundraisingsTab, YesNo.No);
				sa.assertTrue(false,  "Not able to click on tab "+TabName.FundraisingsTab);
			}
		
	lp.CRMlogout();	
	sa.assertAll();	
	}

@Parameters({ "projectName"})
@Test
	public void AATc016_VerifyActiveDealAndInActiveFundraising(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		PipelinesPageBusinessLayer tp= new PipelinesPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		if (ip.clickOnTab(projectName, TabName.Deals)) {
			  log(LogStatus.INFO, "Click on Tab : " + TabName.DealTab, YesNo.No);
		      if (ip.clickOnAlreadyCreatedItem(projectName, TabName.DealTab, ATDealName1, 10)) {
		    	  String[] fieldsWithValues= {PageLabel.Active.toString(),CheckBox.Checked.toString()};
		    	  ThreadSleep(2000);
					WebElement ele2 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele2, RelatedTab.Details.toString(), action.BOOLEAN);
					ThreadSleep(3000);
			tp.fieldValueVerificationOnPipelinePage(environment,mode, fieldsWithValues[0],fieldsWithValues[1]);
			String xpath = "//span[text()='"+fieldsWithValues[0]+"']/../following-sibling::div//lightning-primitive-input-checkbox[@ " + fieldsWithValues[1] +" ]";
			
			WebElement ele = 	FindElement(driver, xpath, fieldsWithValues[0] + " label text with  " + fieldsWithValues[1], action.SCROLLANDBOOLEAN, 10);
			scrollDownThroughWebelement(driver, ele, fieldsWithValues[0] + " label text with  " + fieldsWithValues[1]);
			ele = 	isDisplayed(driver,ele,"Visibility", 10, fieldsWithValues[0] + " label text with  " + fieldsWithValues[1]);
			if (ele != null) {
				appLog.info(fieldsWithValues[0] + " label text with  " + fieldsWithValues[1]+" verified");
			} else {
				appLog.error("<<<<<<   "+fieldsWithValues[0] + " label text with  " + fieldsWithValues[1]+" not verified "+"   >>>>>>");
			}

			ThreadSleep(3000);
		}else {
			appLog.error("Deal name is not clickable so cannot verify Active checkbox functionality");
			sa.assertTrue(false, "Deal name is not clickable so cannot verify Active checkbox functionality");
		}
		}else {
			appLog.error("Deal tab is not clickable");
			sa.assertTrue(false, "deal tab is not clickable");
		}
		
		refresh(driver);
		ThreadSleep(3000);
		
		if (ip.clickOnTab(projectName, TabName.FundraisingsTab)) {
			  log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
		      if (ip.clickOnAlreadyCreatedItem(projectName, TabName.FundraisingsTab, ATTargetName1, 10)) {
		    	  String[] fieldsWithValues= {PageLabel.Active.toString(),CheckBox.Checked.toString()};
		    	  ThreadSleep(2000);
					WebElement ele2 = bp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele2, RelatedTab.Details.toString(), action.BOOLEAN);
					ThreadSleep(3000);
			tp.fieldValueVerificationOnPipelinePage(environment,mode, fieldsWithValues[0],fieldsWithValues[1]);
			String xpath = "//span[text()='"+fieldsWithValues[0]+"']/../following-sibling::div//lightning-primitive-input-checkbox[@ " + fieldsWithValues[1] +" ]";
			
			WebElement ele = 	FindElement(driver, xpath, fieldsWithValues[0] + " label text with  " + fieldsWithValues[1], action.SCROLLANDBOOLEAN, 10);
			scrollDownThroughWebelement(driver, ele, fieldsWithValues[0] + " label text with  " + fieldsWithValues[1]);
			ele = 	isDisplayed(driver,ele,"Visibility", 10, fieldsWithValues[0] + " label text with  " + fieldsWithValues[1]);
			if (ele == null) {
				appLog.info(fieldsWithValues[0] + " label text with  " + fieldsWithValues[1]+" verified");
			} else {
				appLog.error("<<<<<<   "+fieldsWithValues[0] + " label text with  " + fieldsWithValues[1]+" not verified "+"   >>>>>>");
			}

			ThreadSleep(3000);
		}else {
			appLog.error("Fundraising name is not clickable so cannot verify Active checkbox functionality");
			sa.assertTrue(false, "Fundraising name is not clickable so cannot verify Active checkbox functionality");
		}
		}else {
			appLog.error("Fundraising tab is not clickable");
			sa.assertTrue(false, "Fundraising tab is not clickable");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

@Parameters({ "projectName" })
@Test
	public void AATc017_CreateRevenueInboxEvent(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		
		APIUtils Api = new APIUtils();
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);
	
		ThreadSleep(5000);
	
		String eventTitle6= ATETSubject7;
		String[] contactName = {ATConFN1 +" "+ ATConLN1};
		String dealName = ATDealName1;
		String targetName = ATTargetName1;
		String[] subjects = {ATETSubject1,ATETSubject2,ATETSubject3,ATETSubject7};
		String[] subjects1 = {ATETSubject1,ATETSubject2,ATETSubject3,ATETSubject4,ATETSubject5,ATETSubject6};
		String[] subjects2 = {ATETSubject1,ATETSubject2,ATETSubject3,ATETSubject4,ATETSubject5,ATETSubject6,ATETSubject7};
//		String[] firmsTaggedCount = {"2"};
//		String[] contactsTaggedCount = {"2"};
		String startDate6 = todaysDate;
		ExcelUtils.writeData(AcuityDataSheetFilePath, startDate6, "Activity Timeline", excelLabel.Variable_Name,
				"ATET07", excelLabel.Advance_Start_Date);
	
		String endDate6 = todaysDate;
		ExcelUtils.writeData(AcuityDataSheetFilePath, endDate6, "Activity Timeline", excelLabel.Variable_Name,
				"ATET07", excelLabel.Advance_End_Date);
	
		String descriptionBox6 = ATETNote7;
		
		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle6 + " through Outlook---------",
				YesNo.No);
		String sDate =  Api.convertDate(startDate6);
		String eDate =  Api.convertDate(endDate6);
		String RT = Api.getObjectRecordId("Contact", ATETRelated7);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(ApiHeader.Subject.toString(), eventTitle6);
		data.put(ApiHeader.StartDateTime.toString(), sDate);
		data.put(ApiHeader.EndDateTime.toString(), eDate);
		data.put(ApiHeader.Description.toString(), descriptionBox6);
		data.put(ApiHeader.WhoId.toString(), RT);
		
		String id = Api.EventObjectDataUpload(data);
		if (id != null) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle6 + " has been created-----",
					YesNo.No);
		}
	
		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle6
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle6
					+ " has not been created-----");
		}
	
		ThreadSleep(5000);
		
		if (lp.clickOnTab(projectName, TabName.ContactTab)) {
		
			log(LogStatus.INFO, "Click on Tab : " + TabName.ContactTab, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.ContactTab , contactName[0], 10)){
			log(LogStatus.INFO, "Click on contact : " + contactName[0], YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects2) {
				if (BP.verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(subject)) {
					log(LogStatus.INFO,
							"Records on Intraction card have been verified with name " + eventTitle6,
							YesNo.No);
				} else {
					log(LogStatus.ERROR, "Records on Intraction card is not created with name " + eventTitle6, YesNo.No);
					sa.assertTrue(false, "Records on Intraction card is not created with name " + eventTitle6);
				}
				ThreadSleep(2000);
		 	}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + contactName[0], YesNo.No);
			sa.assertTrue(false, "Not able to click on " + contactName[0]);
		}
		}else {
			log(LogStatus.ERROR, "could not click on " + TabName.ContactTab, YesNo.Yes);
			sa.assertTrue(false,"could not click on " + TabName.ContactTab );
		}
		
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.Pipelines)) {
			
			log(LogStatus.INFO, "Click on Tab : " + TabName.Pipelines, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.Pipelines , dealName, 10)){
			log(LogStatus.INFO, "Click on contact : " + dealName, YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects) { 
			if (BP.verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(subject)) {
				log(LogStatus.INFO,
						"Records on Intraction card have been verified with name " + subject,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
				sa.assertTrue(false, "Records on Intraction card is not created with name " + subject);
			}
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + dealName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + dealName);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.Pipelines, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.Pipelines );
	}		
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.FundraisingsTab , targetName, 10)){
			log(LogStatus.INFO, "Click on contact : " + targetName, YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects1) { 
			if (BP.verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(subject)) {
				log(LogStatus.INFO,
						"Records on Intraction card have been verified with name " + subject,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
				sa.assertTrue(false, "Records on Intraction card is not created with name " + subject);
			}
			
			if (BP.getActivityTimelineSubjectinAllIntearctionPage(eventTitle6, 7) == null) {
				log(LogStatus.INFO, "Subject: " + eventTitle6 + " not found on All Interaction Page",
						YesNo.No);

			} else {
				log(LogStatus.ERROR, "Subject: " + eventTitle6 + " found on All Interaction Page",
						YesNo.Yes);
				sa.assertTrue(false, "Subject: " + eventTitle6 + " found on All Interaction Page");

			}
			
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + targetName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + targetName);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.FundraisingsTab, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.FundraisingsTab );
	}		
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName" })
	@Test
	public void AATc018_CreateRevenueInboxCall(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		String activityType1=ATETAction8;
		String taskSubject1=ATETSubject8;
		String taskRelatedTo1=ATETRelated8;
		String taskNotes1=ATETNote8;
		String[] contactName = {ATConFN1 +" "+ ATConLN1};
		String dealName = ATDealName1;
		String targetName = ATTargetName1;
		String[] subjects = {ATETSubject1,ATETSubject2,ATETSubject3,ATETSubject7,ATETSubject8};
		String[] subjects1 = {ATETSubject1,ATETSubject2,ATETSubject3,ATETSubject4,ATETSubject5,ATETSubject6};
		String[] subjects2 = {ATETSubject1,ATETSubject2,ATETSubject3,ATETSubject4,ATETSubject5,ATETSubject6,ATETSubject7,ATETSubject8};
		String taskDueDate1 = todaysDate;
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate1, "Activity Timeline", excelLabel.Variable_Name,
				"ATET08", excelLabel.Advance_Due_Date);
		
		String[][] basicsection1 = { { excelLabel.Subject.toString(), taskSubject1 }, { excelLabel.Notes.toString(), taskNotes1 }, { excelLabel.Related_To.toString(), taskRelatedTo1 } };
		String[][] advanceSection1 = { { excelLabel.Date.toString(), taskDueDate1 } };
	
		if (BP.createActivityTimeline(projectName, true, activityType1, basicsection1, advanceSection1, null, null, false, null, null,null, null,null,null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject1);
	
		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject1);
		}	
		
	ThreadSleep(5000);	
		if (lp.clickOnTab(projectName, TabName.ContactTab)) {
			
				log(LogStatus.INFO, "Click on Tab : " + TabName.ContactTab, YesNo.No);
				ThreadSleep(3000);
				
			if(lp.clickOnAlreadyCreated(environment, mode,TabName.ContactTab , contactName[0], 10)){
				log(LogStatus.INFO, "Click on contact : " + contactName[0], YesNo.No);
				ThreadSleep(5000);
				for(String subject : subjects2) {
				if (BP.verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(subject)) {
					log(LogStatus.INFO,
							"Records on Intraction card have been verified with name "+subject ,
							YesNo.No);
				} else {
					log(LogStatus.ERROR, "Records on Intraction card is not created with name "+subject , YesNo.No);
					sa.assertTrue(false, "Records on Intraction card is not created with name "+subject );
				}
					}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + contactName[0], YesNo.No);
				sa.assertTrue(false, "Not able to click on " + contactName[0]);
			}
		}else {
		log(LogStatus.ERROR, "could not click on " + TabName.ContactTab, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.ContactTab );
	}
		
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.Deals)) {
			
			log(LogStatus.INFO, "Click on Tab : " + TabName.Deals, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.Pipelines , dealName, 10)){
			log(LogStatus.INFO, "Click on contact : " + dealName, YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects) { 
			if (BP.verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(subject)) {
				log(LogStatus.INFO,
						"Records on Intraction card have been verified with name " + subject,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
				sa.assertTrue(false, "Records on Intraction card is not created with name " + subject);
			}
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + dealName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + dealName);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.Deals, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.Deals );
	}		
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.FundraisingsTab , targetName, 10)){
			log(LogStatus.INFO, "Click on contact : " + targetName, YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects1) { 
			if (BP.verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(subject)) {
				log(LogStatus.INFO,
						"Records on Intraction card have been verified with name " + subject,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
				sa.assertTrue(false, "Records on Intraction card is not created with name " + subject);
			}
			
			if (BP.getActivityTimelineSubjectinAllIntearctionPage(taskSubject1, 7) == null) {
				log(LogStatus.INFO, "Subject: " + taskSubject1 + " not found on All Interaction Page",
						YesNo.No);

			} else {
				log(LogStatus.ERROR, "Subject: " + taskSubject1 + " found on All Interaction Page",
						YesNo.Yes);
				sa.assertTrue(false, "Subject: " + taskSubject1 + " found on All Interaction Page");

			}
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + targetName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + targetName);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.FundraisingsTab, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.FundraisingsTab );
	}		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName" })
	@Test
	public void AATc019_CreateRevenueInboxTask(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		String activityType1=ATETAction9;
		String taskSubject1=ATETSubject9;
		String taskRelatedTo1=ATETRelated9;
		String taskNotes1=ATETNote9;
		String[] contactName = {ATConFN1 +" "+ ATConLN1};
		String[] firmsTaggedName = {AT_TaggedFirmsName3};
		String[] firmsTaggedCount = {AT_TaggedFirmsCount3};
		String[] PeopleTaggedName = {AT_TaggedPeopleName3};
		String[] PeopleTaggedCount = {AT_TaggedPeopleCount3};
		String[] firmsTaggedName1 = {AT_TaggedFirmsName4};
		String[] firmsTaggedCount1 = {AT_TaggedFirmsCount4};
		String[] PeopleTaggedName1 = {AT_TaggedPeopleName4};
		String[] PeopleTaggedCount1 = {AT_TaggedPeopleCount4};
		String dealName = ATDealName1;
		String targetName = ATTargetName1;
		String[] subjects = {ATETSubject1,ATETSubject2,ATETSubject3,ATETSubject7,ATETSubject8,ATETSubject9};
		String[] subjects1 = {ATETSubject1,ATETSubject2,ATETSubject3,ATETSubject4,ATETSubject5,ATETSubject6};
		String[] subjects2 = {ATETSubject1,ATETSubject2,ATETSubject3,ATETSubject4,ATETSubject5,ATETSubject6,ATETSubject7,ATETSubject8,ATETSubject9};
		String taskDueDate1 = tomorrowsDate;
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate1, "Activity Timeline", excelLabel.Variable_Name,
				"ATET09", excelLabel.Advance_Due_Date);
		String assignedTo1=ATETPriority9;
		String status=ATETStatus9;
		
		String[][] basicsection1 = { { excelLabel.Subject.toString(), taskSubject1 }, { excelLabel.Notes.toString(), taskNotes1 }, { excelLabel.Related_To.toString(), taskRelatedTo1 } };
		String[][] advanceSection1 = { { excelLabel.Due_Date.toString(), taskDueDate1 }, {excelLabel.Priority.toString(), assignedTo1}, {excelLabel.Status.toString(), status} };
	
		if (BP.createActivityTimeline(projectName, true, activityType1, basicsection1, advanceSection1, null, null, false, null, null,null, null,null,null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject1);
	
		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject1);
		}	
		
		ThreadSleep(5000);
		if (lp.clickOnTab(projectName, TabName.ContactTab)) {
		
			log(LogStatus.INFO, "Click on Tab : " + TabName.ContactTab, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.ContactTab , contactName[0], 10)){
			log(LogStatus.INFO, "Click on contact : " + contactName[0], YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects2) {
			if (BP.verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(subject)) {
				log(LogStatus.INFO,
						"Records on Intraction card have been verified with name " + subject,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
				sa.assertTrue(false, "Records on Intraction card is not created with name " + subject);
			}
				}	
		} else {
			log(LogStatus.ERROR, "Not able to click on " + contactName[0], YesNo.No);
			sa.assertTrue(false, "Not able to click on " + contactName[0]);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.ContactTab, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.ContactTab );
	}
		
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.Deals)) {
			
			log(LogStatus.INFO, "Click on Tab : " + TabName.Deals, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.Pipelines , dealName, 10)){
			log(LogStatus.INFO, "Click on contact : " + dealName, YesNo.No);
			ThreadSleep(5000);
				for(String subject : subjects) { 
					if (BP.verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(subject)) {
						log(LogStatus.INFO,
								"Records on Intraction card have been verified with name " + subject,
								YesNo.No);
					} else {
						log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
						sa.assertTrue(false, "Records on Intraction card is not created with name " + subject);
					}
					
				}
			ThreadSleep(2000);
			clickUsingJavaScript(driver, BP.getPeopleTabOnTagged(10), "People Tab");
			ThreadSleep(4000);
			
			ArrayList<String> result5=BP.verifyRecordAndReferencedTypeOnTagged(firmsTaggedName, firmsTaggedCount, PeopleTaggedName, PeopleTaggedCount, null, null, false, null,null);
			if(result5.isEmpty())
			{
				log(LogStatus.INFO, "The record name and Contact Name have been verifed", YesNo.No);
			}
			else
			{
				log(LogStatus.ERROR,  "The record name and Contact Name are not verifed. "+result5, YesNo.No);
				sa.assertTrue(false,  "The record name and Contact Name are not verifed."+result5);
			}
			
		} else {
			log(LogStatus.ERROR, "Not able to click on " + dealName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + dealName);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.Deals, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.Deals );
	}		
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.FundraisingsTab , targetName, 10)){
			log(LogStatus.INFO, "Click on contact : " + targetName, YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects1) { 
				if (BP.verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(subject)) {
					log(LogStatus.INFO,
							"Records on Intraction card have been verified with name " + subject,
							YesNo.No);
				} else {
					log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
					sa.assertTrue(false, "Records on Intraction card is not created with name " + subject);
				}
				
				if (BP.getActivityTimelineSubjectinAllIntearctionPage(taskSubject1, 7) == null) {
					log(LogStatus.INFO, "Subject: " + taskSubject1 + " not found on All Interaction Page",
							YesNo.No);

				} else {
					log(LogStatus.ERROR, "Subject: " + taskSubject1 + " found on All Interaction Page",
							YesNo.Yes);
					sa.assertTrue(false, "Subject: " + taskSubject1 + " found on All Interaction Page");

				}
					}
			ThreadSleep(2000);
			clickUsingJavaScript(driver, BP.getPeopleTabOnTagged(10), "People Tab");
			ThreadSleep(4000);
			
			ArrayList<String> result5=BP.verifyRecordAndReferencedTypeOnTagged(firmsTaggedName1, firmsTaggedCount1, PeopleTaggedName1, PeopleTaggedCount1, null, null, false,null,null);
			if(result5.isEmpty())
			{
				log(LogStatus.INFO, "The record name and Contact Name have been verifed", YesNo.No);
			}
			else
			{
				log(LogStatus.ERROR,  "The record name and Contact Name are not verifed. "+result5, YesNo.No);
				sa.assertTrue(false,  "The record name and Contact Name are not verifed."+result5);
			}
			
		} else {
			log(LogStatus.ERROR, "Not able to click on " + targetName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + targetName);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.FundraisingsTab, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.FundraisingsTab );
	}		
		lp.CRMlogout();
		sa.assertAll();
	}

@Parameters({ "projectName" })
@Test
	public void AATc020_CreateDealTeam_VerifyImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
	
		String dealName = ATDTDealName2;
		String contactName = ATDTContact2;
		String role = ATDTRole2;
	
		String[][] data = { { EditPageLabel.Deal.toString(), dealName },
				{ PageLabel.Deal_Contact.toString(), contactName },
				{ PageLabel.Role.toString(), role }};
	
		if (BP.openAppFromAppLauchner(60, "Deal Team")) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);
	    ThreadSleep(2000);
		if (DTP.createDealTeam(projectName, dealName, data,TabName.Acuity.toString(), action.SCROLLANDBOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----", YesNo.No);
	
			log(LogStatus.INFO,
					"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
							+ contactName + " at Firm Tab under Acuity section---------",
					YesNo.No);
				WebElement ele = DTP.getDealTeamID(10);
					if (ele != null) {
						String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
						ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name, "ATDT2",
								excelLabel.DealTeamID);
						log(LogStatus.INFO, "successfully created and noted id of DT" + id + " and deal name " + dealName,
								YesNo.No);
					}
				} else {
					sa.assertTrue(false, "could not create DT" + dealName);
					log(LogStatus.SKIP, "could not create DT" + dealName, YesNo.Yes);
				}
			} else {
					log(LogStatus.ERROR, "Not able to click on " + TabName.Deal_Team + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + TabName.Deal_Team + " tab");
			}
		lp.CRMlogout();
		sa.assertAll();
	}

@Parameters({ "projectName" })
@Test
	public void AATc021_CreateContactContactRole_VerifyImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		FundRaisingPageBusinessLayer tp=new FundRaisingPageBusinessLayer(driver);
	
		String TargetName=ATCRTargetName2;
		String contactName=ATCRContactName2;
		String[][] data = { { PageLabel.Fundraising.toString(), TargetName }, { PageLabel.Contact.toString(), contactName } };
		lp.CRMLogin(crmUser1EmailID, adminPassword);
	
		if (bp.openAppFromAppLauchner(10, "Fundraising Contacts")) {
			log(LogStatus.INFO, "Fundraising Contacts has been open from the app launcher", YesNo.No);

			if (tp.createFundraisingContact(projectName, TargetName, data, action.SCROLLANDBOOLEAN, 25)) {
				log(LogStatus.INFO,
						"----Successfully Created the Fundraising Contacts for Contact: " + contactName + "----",
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "could not create a new Fundraising Contacts", YesNo.Yes);
				sa.assertTrue(false, "could not create a new Fundraising Contacts");
			}
		} else {
			log(LogStatus.ERROR, "could not open a Fundraising Contacts tab", YesNo.Yes);
			sa.assertTrue(false, "could not open a Fundraising Contacts tab");
		}
				
		lp.CRMlogout();	
		sa.assertAll();	
	}

@Parameters({ "projectName" })
@Test
	public void AATc022_CreateRevenueInboxEvent(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
	
		ThreadSleep(5000);
		APIUtils Api = new APIUtils();
		String eventTitle6= ATETSubject10;
		String[] contactName = {ATConFN2 +" "+ ATConLN2};
		String dealName = ATDealName2;
		String targetName = ATTargetName2;
		String[] subjects = {ATETSubject10};
		
//		String[] userAndContact6=ATETRelated10.split("<userBreak>");
//		String eventAttendees6=userAndContact6[0];
		String startDate6 = todaysDate;
		ExcelUtils.writeData(AcuityDataSheetFilePath, startDate6, "Activity Timeline", excelLabel.Variable_Name,
				"ATET10", excelLabel.Advance_Start_Date);
	
		String endDate6 = todaysDate;
		ExcelUtils.writeData(AcuityDataSheetFilePath, endDate6, "Activity Timeline", excelLabel.Variable_Name,
				"ATET10", excelLabel.Advance_End_Date);
	
		String descriptionBox6 = ATETNote10;
	
		log(LogStatus.INFO, "---------Now Going to Create Event: " + eventTitle6 + " through Outlook---------",
				YesNo.No);
		String sDate =  Api.convertDate(startDate6);
		String eDate =  Api.convertDate(endDate6);
		String RT = Api.getObjectRecordId("Contact", ATETRelated10);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(ApiHeader.Subject.toString(), eventTitle6);
		data.put(ApiHeader.StartDateTime.toString(), sDate);
		data.put(ApiHeader.EndDateTime.toString(), eDate);
		data.put(ApiHeader.Description.toString(), descriptionBox6);
		data.put(ApiHeader.WhoId.toString(), RT);
		
		String id = Api.EventObjectDataUpload(data);
		if (id != null) {
			log(LogStatus.INFO,
					"-----Event Created Msg is showing, So Event of Title: " + eventTitle6 + " has been created-----",
					YesNo.No);
		}
	
		else {
			log(LogStatus.ERROR, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle6
					+ " has not been created-----", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "-----Event Created Msg is not showing, So Event of Title: " + eventTitle6
					+ " has not been created-----");
		}
	
		ThreadSleep(5000);
		
		if (lp.clickOnTab(projectName, TabName.ContactTab)) {
		
			log(LogStatus.INFO, "Click on Tab : " + TabName.ContactTab, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.ContactTab , contactName[0], 10)){
			log(LogStatus.INFO, "Click on contact : " + contactName[0], YesNo.No);
			ThreadSleep(5000);
			if (BP.InteractionRecord(eventTitle6,10) != null) {
				log(LogStatus.INFO,
						"Records on Intraction card have been verified with name " + eventTitle6,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Records on Intraction card is not created with name " + eventTitle6, YesNo.No);
				sa.assertTrue(false, "Records on Intraction card is not created with name " + eventTitle6);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + contactName[0], YesNo.No);
			sa.assertTrue(false, "Not able to click on " + contactName[0]);
		}
		}else {
			log(LogStatus.ERROR, "could not click on " + TabName.ContactTab, YesNo.Yes);
			sa.assertTrue(false,"could not click on " + TabName.ContactTab );
		}
		
		ThreadSleep(2000);
			if (lp.clickOnTab(projectName, TabName.Deals)) {
				
				log(LogStatus.INFO, "Click on Tab : " + TabName.Deals, YesNo.No);
				ThreadSleep(3000);
				
			if(lp.clickOnAlreadyCreated(environment, mode,TabName.Pipelines , dealName, 10)){
				log(LogStatus.INFO, "Click on contact : " + dealName, YesNo.No);
				ThreadSleep(5000);
				for(String subject : subjects) { 
				if (!BP.verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(subject)) {
					log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
				} else {
					log(LogStatus.INFO,
							"Records on Intraction card have been verified with name " + subject,
							YesNo.No);
					sa.assertTrue(false, "Records on Intraction card have been verified with name " + subject);
				}
					}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + dealName, YesNo.No);
				sa.assertTrue(false, "Not able to click on " + dealName);
			}
		}else {
			log(LogStatus.ERROR, "could not click on " + TabName.Deals, YesNo.Yes);
			sa.assertTrue(false,"could not click on " + TabName.Deals );
		}		
			
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.FundraisingsTab , targetName, 10)){
			log(LogStatus.INFO, "Click on contact : " + targetName, YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects) { 
			if (BP.InteractionRecord(subject,10) == null) {
				log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
			} else {
				log(LogStatus.INFO,
						"Records on Intraction card have been verified with name " + subject,
						YesNo.No);
				sa.assertTrue(false, "Records on Intraction card have been verified with name " + subject);
			}
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + targetName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + targetName);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.FundraisingsTab, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.FundraisingsTab );
	}		
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
@Parameters({ "projectName" })
@Test
	public void AATc023_CreateRevenueInboxCall(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		String activityType1=ATETAction11;
		String taskNotes1=ATETNote11;
		String dealName = ATDTDealName2;
		String targetName=ATCRTargetName2;
		String taskSubject1=ATETSubject11;
		String taskRelatedTo1=ATETRelated11;
		String[] contactName = {ATConFN2 +" "+ ATConLN2};
		String taskDueDate1 = todaysDate;
		String[] subjects = {ATETSubject10,ATETSubject11};
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate1, "Activity Timeline", excelLabel.Variable_Name,
				"ATET11", excelLabel.Advance_Due_Date);
		
		String[][] basicsection1 = { { excelLabel.Subject.toString(), taskSubject1 }, { excelLabel.Notes.toString(), taskNotes1 }, { excelLabel.Related_To.toString(), taskRelatedTo1 } };
		String[][] advanceSection1 = { { excelLabel.Date.toString(), taskDueDate1 } };
	
		if (BP.createActivityTimeline(projectName, true, activityType1, basicsection1, advanceSection1, null, null, false, null, null,null, null,null,null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject1);
	
		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject1);
		}	
		
	ThreadSleep(5000);	
		if (lp.clickOnTab(projectName, TabName.ContactTab)) {
			
				log(LogStatus.INFO, "Click on Tab : " + TabName.ContactTab, YesNo.No);
				ThreadSleep(3000);
				
			if(lp.clickOnAlreadyCreated(environment, mode,TabName.ContactTab , contactName[0], 10)){
				log(LogStatus.INFO, "Click on contact : " + contactName[0], YesNo.No);
				ThreadSleep(5000);
				for(String subject : subjects) {
				if (BP.InteractionRecord(subject,10) != null) {
					log(LogStatus.INFO,
							"Records on Intraction card have been verified with name "+subject ,
							YesNo.No);
				} else {
					log(LogStatus.ERROR, "Records on Intraction card is not created with name "+subject , YesNo.No);
					sa.assertTrue(false, "Records on Intraction card is not created with name "+subject );
				}
					}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + contactName[0], YesNo.No);
				sa.assertTrue(false, "Not able to click on " + contactName[0]);
			}
		}else {
		log(LogStatus.ERROR, "could not click on " + TabName.ContactTab, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.ContactTab );
	}
	
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.Deals)) {
			
			log(LogStatus.INFO, "Click on Tab : " + TabName.Deals, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.Pipelines , dealName, 10)){
			log(LogStatus.INFO, "Click on contact : " + dealName, YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects) { 
			if (!BP.verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(subject)) {
				log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
			} else {
				log(LogStatus.INFO,
						"Records on Intraction card have been verified with name " + subject,
						YesNo.No);
				sa.assertTrue(false, "Records on Intraction card have been verified with name " + subject);
			}
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + dealName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + dealName);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.Deals, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.Deals );
	}		
		
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.FundraisingsTab , targetName, 10)){
			log(LogStatus.INFO, "Click on contact : " + targetName, YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects) { 
				if (!BP.verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(subject)) {
					log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
				} else {
					log(LogStatus.INFO,
							"Records on Intraction card have been verified with name " + subject,
							YesNo.No);
					sa.assertTrue(false, "Records on Intraction card have been verified with name " + subject);
				}
					}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + targetName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + targetName);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.FundraisingsTab, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.FundraisingsTab );
	}		
		lp.CRMlogout();
		sa.assertAll();
	}

@Parameters({ "projectName" })
@Test
	public void AATc024_CreateRevenueInboxTask(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		String activityType1=ATETAction12;
		String taskSubject1=ATETSubject12;
		String taskRelatedTo1=ATETRelated12;
		String taskNotes1=ATETNote12;
		String dealName = ATDealName2;
		String targetName = ATTargetName2;
		String[] subjects = {ATETSubject10,ATETSubject11,ATETSubject12};
		String taskDueDate1 = tomorrowsDate;
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate1, "Activity Timeline", excelLabel.Variable_Name,
				"ATET12", excelLabel.Advance_Due_Date);
//		String assignedTo1=ATETPriority12;
		
		String[][] basicsection1 = { { excelLabel.Subject.toString(), taskSubject1 }, { excelLabel.Notes.toString(), taskNotes1 }, { excelLabel.Related_To.toString(), taskRelatedTo1 } };
		String[][] advanceSection1 = { { excelLabel.Due_Date.toString(), taskDueDate1 } };
	
		if (BP.createActivityTimeline(projectName, true, activityType1, basicsection1, advanceSection1, null, null, false, null, null,null, null,null,null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject1);
	
		} else {
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject1);
		}	
		
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.Deals)) {
			
			log(LogStatus.INFO, "Click on Tab : " + TabName.Deals, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.Pipelines , dealName, 10)){
			log(LogStatus.INFO, "Click on contact : " + dealName, YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects) { 
			if (!BP.verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(subject)) {
				log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
			} else {
				log(LogStatus.INFO,
						"Records on Intraction card have been verified with name " + subject,
						YesNo.No);
				sa.assertTrue(false, "Records on Intraction card have been verified with name " + subject);
			}
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + dealName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + dealName);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.Deals, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.Deals );
	}		
				
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.FundraisingsTab , targetName, 10)){
			log(LogStatus.INFO, "Click on contact : " + targetName, YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects) { 
				if (!BP.verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(subject)) {
					log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
				} else {
					log(LogStatus.INFO,
							"Records on Intraction card have been verified with name " + subject,
							YesNo.No);
					sa.assertTrue(false, "Records on Intraction card have been verified with name " + subject);
				}
					}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + targetName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + targetName);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.FundraisingsTab, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.FundraisingsTab );
	}		
		lp.CRMlogout();
		sa.assertAll();
	}

@Parameters({ "projectName" })
@Test
	public void AATc025_UpdateContactOnTask(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		String dealName = ATDTDealName1;
		String RelatedTo1 = ATConFN3 +" "+ ATConLN3;
//		String role = none;
//		String TargetName=ATCRTargetName;
		String contact=ATCRContactName1;
		String taskSubject1=ATETSubject3;
		String taskRelatedTo1=ATETRelated3;
		String[] contactName = {ATConFN3 +" "+ ATConLN3};
		
		String[][] data = { { PageLabel.Deal.toString(), dealName },
				{ PageLabel.Deal_Contact.toString(), RelatedTo1 }};
		
		String[] removesection1 = {taskRelatedTo1};
		String[][] basicsection1 = {{excelLabel.Related_To.toString(), RelatedTo1}};
		
		if (home.globalSearchAndNavigate(taskSubject1, "Tasks", false, projectName)) {

			log(LogStatus.INFO, "-----Verified Task named: " + taskSubject1 + " found in Tasks Object-----",YesNo.No);
			
			if(CommonLib.clickUsingJavaScript(driver, BP.getEditButtonOnPopup(taskSubject1, 20), "Edit button of popup of subject name "+taskSubject1))
			{
				log(LogStatus.INFO,"Clicked on edit button of subject "+taskSubject1,YesNo.No);
				

				if (BP.updateActivityTimelineRecord(projectName, null, null, null, null, removesection1)) {
					log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject1, YesNo.No);
					sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject1);
				} else {
					log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject1, YesNo.No);
					sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject1);
				} 
				
			}
			else
			{
				log(LogStatus.ERROR,"Not able to click on edit button of subject "+taskSubject1,YesNo.No);
				BaseLib.sa.assertTrue(false,"Not able to click on edit button of subject "+taskSubject1);
				
			}
			
			
		}
		 else {

				log(LogStatus.ERROR, "-----Task named: " + taskSubject1 + " not found in Tasks Object-----",YesNo.Yes);
				BaseLib.sa.assertTrue(false,"-----Task named: " + taskSubject1 + " not found in Tasks Object-----");

			}	
//	ThreadSleep(5000);	
//	if (BP.openAppFromAppLauchner(60, "Deal Team")) {
//		log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);
//	    ThreadSleep(2000);
//		if (DTP.createDealTeam(projectName, dealName, data,TabName.Acuity.toString(), action.SCROLLANDBOOLEAN, 25)) {
//			log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----", YesNo.No);
//	
//			log(LogStatus.INFO,
//					"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
//							+ contactName + " at Firm Tab under Acuity section---------",
//					YesNo.No);
//				WebElement ele = DTP.getDealTeamID(10);
//					if (ele != null) {
//						String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
//						ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name, "ATDT3",
//								excelLabel.DealTeamID);
//						log(LogStatus.INFO, "successfully created and noted id of DT" + id + " and deal name " + dealName,
//								YesNo.No);
//					}
//				} else {
//					sa.assertTrue(false, "could not create DT" + dealName);
//					log(LogStatus.SKIP, "could not create DT" + dealName, YesNo.Yes);
//				}
//			} else {
//					log(LogStatus.ERROR, "Not able to click on " + TabName.Deal_Team + " tab", YesNo.Yes);
//					sa.assertTrue(false, "Not able to click on " + TabName.Deal_Team + " tab");
//			}			
		lp.CRMlogout();
		sa.assertAll();
	}

@Parameters({ "projectName" })
@Test
	public void AATc026_VerifyImpactAfterRemovingContactFromTask(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		ThreadSleep(5000);
	
		String eventTitle6= ATETSubject1;
		String[] contactName = {ATConFN1 +" "+ ATConLN1};
		String dealName = ATDealName1;
		String targetName = ATTargetName1;
		String[] firmsTaggedName = {ATFirm1};
		
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.Pipelines)) {
			
			log(LogStatus.INFO, "Click on Tab : " + TabName.Pipelines, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.Pipelines , dealName, 10)){
			log(LogStatus.INFO, "Click on contact : " + dealName, YesNo.No);
			ThreadSleep(5000);
		
		clickUsingJavaScript(driver, BP.getPeopleTabOnTagged(10), "People Tab");
		ThreadSleep(4000);
		
		ArrayList<String> result5=BP.verifyRecordAndReferencedTypeOnTagged(firmsTaggedName, null, contactName, null, null, null, false,null,null);
		if(result5.isEmpty())
		{
			log(LogStatus.INFO, "The record name and Contact Name have been verifed", YesNo.No);
		}
		else
		{
			log(LogStatus.ERROR,  "The record name and Contact Name are not verifed. "+result5, YesNo.No);
			sa.assertTrue(false,  "The record name and Contact Name are not verifed."+result5);
		}
		
			if (BP.verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle6)) {
				log(LogStatus.INFO,
						"Records on Intraction card have been verified with name "+eventTitle6 ,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Records on Intraction card is not created with name "+eventTitle6 , YesNo.No);
				sa.assertTrue(false, "Records on Intraction card is not created with name "+eventTitle6 );
			}
		
		} else {
			log(LogStatus.ERROR, "Not able to click on " + dealName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + dealName);
		}
		}else {
			log(LogStatus.ERROR, "could not click on " + TabName.Pipelines, YesNo.Yes);
			sa.assertTrue(false,"could not click on " + TabName.Pipelines );
		}
		
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
			
			log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.FundraisingsTab , targetName, 10)){
			log(LogStatus.INFO, "Click on contact : " + targetName, YesNo.No);
			ThreadSleep(5000);
		
		clickUsingJavaScript(driver, BP.getPeopleTabOnTagged(10), "People Tab");
		ThreadSleep(4000);
		
		ArrayList<String> result5=BP.verifyRecordAndReferencedTypeOnTagged(firmsTaggedName, null, contactName, null, null, null, false,null,null);
		if(result5.isEmpty())
		{
			log(LogStatus.INFO, "The record name and Contact Name have been verifed", YesNo.No);
		}
		else
		{
			log(LogStatus.ERROR,  "The record name and Contact Name are not verifed. "+result5, YesNo.No);
			sa.assertTrue(false,  "The record name and Contact Name are not verifed."+result5);
		}
		
		if (BP.verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(eventTitle6)) {
			log(LogStatus.INFO,
					"Records on Intraction card have been verified with name "+eventTitle6 ,
					YesNo.No);
		} else {
			log(LogStatus.ERROR, "Records on Intraction card is not created with name "+eventTitle6 , YesNo.No);
			sa.assertTrue(false, "Records on Intraction card is not created with name "+eventTitle6 );
		}
		
		} else {
			log(LogStatus.ERROR, "Not able to click on " + targetName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + targetName);
		}
		}else {
			log(LogStatus.ERROR, "could not click on " + TabName.FundraisingsTab, YesNo.Yes);
			sa.assertTrue(false,"could not click on " + TabName.FundraisingsTab );
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

@Parameters({ "projectName" })
@Test
	public void AATc027_UpdateContactOnTaskAndDealTeam(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		String dealName = ATDTDealName1;
		String RelatedTo1 = ATConFN3 +" "+ ATConLN3;
		String taskSubject1=ATETSubject3;
		String[] contactName = {ATConFN3 +" "+ ATConLN3};
		
		String[][] data = { { PageLabel.Deal_Contact.toString(), RelatedTo1 }};

		String[][] basicsection1 = {{excelLabel.Related_To.toString(), RelatedTo1}};
		
		if (home.globalSearchAndNavigate(taskSubject1, "Tasks", false, projectName)) {

			log(LogStatus.INFO, "-----Verified Task named: " + taskSubject1 + " found in Tasks Object-----",YesNo.No);
			
			if(CommonLib.clickUsingJavaScript(driver, BP.getEditButtonOnPopup(taskSubject1, 20), "Edit button of popup of subject name "+taskSubject1))
			{
				log(LogStatus.INFO,"Clicked on edit button of subject "+taskSubject1,YesNo.No);
				

				if (BP.updateActivityTimelineRecord(projectName, basicsection1, null, null, null, null)) {
					log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject1, YesNo.No);
					sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject1);
				} else {
					log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject1, YesNo.No);
					sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject1);
				} 
				
			}
			else
			{
				log(LogStatus.ERROR,"Not able to click on edit button of subject "+taskSubject1,YesNo.No);
				BaseLib.sa.assertTrue(false,"Not able to click on edit button of subject "+taskSubject1);
				
			}
			
			
		}
		 else {

				log(LogStatus.ERROR, "-----Task named: " + taskSubject1 + " not found in Tasks Object-----",YesNo.Yes);
				BaseLib.sa.assertTrue(false,"-----Task named: " + taskSubject1 + " not found in Tasks Object-----");

			}	
	ThreadSleep(5000);	
	if (BP.openAppFromAppLauchner(60, "Deal Team")) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);
	    ThreadSleep(2000);
		if (fp.clickOnAlreadyCreatedItem(projectName, ATDTDealTeam1, 10)) {
		if (DTP.UpdateDealContactName(projectName, data,25)) {
			log(LogStatus.INFO, "----Successfully Updated the Deal Team for Deal: " + dealName + "----", YesNo.No);
	
			log(LogStatus.INFO,
					"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
							+ contactName + " at Firm Tab under Acuity section---------",
					YesNo.No);
				WebElement ele = DTP.getDealTeamID(10);
					if (ele != null) {
						String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
						ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name, "ATDT1",
								excelLabel.DealTeamID);
						log(LogStatus.INFO, "successfully created and noted id of DT" + id + " and deal name " + dealName,
								YesNo.No);
					}
		}
				} else {
					sa.assertTrue(false, "could not create DT" + dealName);
					log(LogStatus.SKIP, "could not create DT" + dealName, YesNo.Yes);
				}
			} else {
					log(LogStatus.ERROR, "Not able to click on " + TabName.Deal_Team + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + TabName.Deal_Team + " tab");
			}			
		lp.CRMlogout();
		sa.assertAll();
	}

@Parameters({ "projectName" })
@Test
	public void AATc028_CreateRevenueInboxTask(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		String activityType1=ATETAction13;
		String taskSubject1=ATETSubject13;
		String taskRelatedTo1=ATETRelated13;
		String taskNotes1=ATETNote13;
		String[] contactName = {ATConFN3 +" "+ ATConLN3};
		String[] firmsTaggedName = {AT_TaggedFirmsName3};
		String[] PeopleTaggedName = {AT_TaggedPeopleName3};
		String dealName = ATDealName1;
		String targetName = ATTargetName1;
		String[] subjects = {ATETSubject13};
		String taskDueDate1 = tomorrowsDate;
		ExcelUtils.writeData(AcuityDataSheetFilePath, taskDueDate1, "Activity Timeline", excelLabel.Variable_Name,
				"ATET09", excelLabel.Advance_Due_Date);
		
		String[][] basicsection1 = { { excelLabel.Subject.toString(), taskSubject1 }, { excelLabel.Notes.toString(), taskNotes1 }, { excelLabel.Related_To.toString(), taskRelatedTo1 } };
		String[][] advanceSection1 = { { excelLabel.Due_Date.toString(), taskDueDate1 }};
	
		if (BP.createActivityTimeline(projectName, true, activityType1, basicsection1, advanceSection1, null, null, false, null, null,null, null,null,null)) {
			log(LogStatus.PASS, "Activity timeline record has been created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(true, "Activity timeline record has been created,  Subject name : "+taskSubject1);
	
		}
		else
		{
			log(LogStatus.ERROR, "Activity timeline record is not created, Subject name : "+taskSubject1, YesNo.No);
			sa.assertTrue(false, "Activity timeline record is not created,  Subject name : "+taskSubject1);
		}	
		
		ThreadSleep(5000);
		if (lp.clickOnTab(projectName, TabName.ContactTab)) {
		
			log(LogStatus.INFO, "Click on Tab : " + TabName.ContactTab, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.ContactTab , contactName[0], 10)){
			log(LogStatus.INFO, "Click on contact : " + contactName[0], YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects) {
			if (BP.InteractionRecord(subject,10) != null) {
				log(LogStatus.INFO,
						"Records on Intraction card have been verified with name " + subject,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
				sa.assertTrue(false, "Records on Intraction card is not created with name " + subject);
			}
				}	
		} else {
			log(LogStatus.ERROR, "Not able to click on " + contactName[0], YesNo.No);
			sa.assertTrue(false, "Not able to click on " + contactName[0]);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.ContactTab, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.ContactTab );
	}
		
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.Deals)) {
			
			log(LogStatus.INFO, "Click on Tab : " + TabName.Deals, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.Pipelines , dealName, 10)){
			log(LogStatus.INFO, "Click on contact : " + dealName, YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects) { 
			if (BP.verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(subject)) {
				log(LogStatus.INFO,
						"Records on Intraction card have been verified with name " + subject,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Records on Intraction card is not created with name " + subject, YesNo.No);
				sa.assertTrue(false, "Records on Intraction card is not created with name " + subject);
			}
				}
			
			ThreadSleep(2000);
			clickUsingJavaScript(driver, BP.getPeopleTabOnTagged(10), "People Tab");
			ThreadSleep(4000);
			
			ArrayList<String> result5=BP.verifyRecordAndReferencedTypeOnTagged(firmsTaggedName, null, PeopleTaggedName, null, null, null, false, null,null);
			if(result5.isEmpty())
			{
				log(LogStatus.INFO, "The record name and Contact Name have been verifed", YesNo.No);
			}
			else
			{
				log(LogStatus.ERROR,  "The record name and Contact Name are not verifed. "+result5, YesNo.No);
				sa.assertTrue(false,  "The record name and Contact Name are not verifed."+result5);
			}
			
		} else {
			log(LogStatus.ERROR, "Not able to click on " + dealName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + dealName);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.Deals, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.Deals );
	}		
		ThreadSleep(2000);
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
			ThreadSleep(3000);
			
		if(lp.clickOnAlreadyCreated(environment, mode,TabName.FundraisingsTab , targetName, 10)){
			log(LogStatus.INFO, "Click on contact : " + targetName, YesNo.No);
			ThreadSleep(5000);
			for(String subject : subjects) { 
			if (!BP.verifySubjectOfInteractionEitherOnCardOrInViewAllPopUp(subject)) {
				log(LogStatus.INFO,
						"Records on Intraction card is not visible verified with name " + subject,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Records on Intraction card is present with name " + subject, YesNo.No);
				sa.assertTrue(false, "Records on Intraction card is present with name " + subject);
			}
				}
			ThreadSleep(2000);
			clickUsingJavaScript(driver, BP.getPeopleTabOnTagged(10), "People Tab");
			ThreadSleep(4000);
			
			ArrayList<String> result5=BP.verifyRecordAndReferencedTypeOnTagged(firmsTaggedName, null, PeopleTaggedName, null, null, null, false,null,null);
			if(!result5.isEmpty())
			{
				log(LogStatus.INFO, "The record name and Contact Name are not present", YesNo.No);
			}
			else
			{
				log(LogStatus.ERROR,  "The record name and Contact Name have been verifed. "+result5, YesNo.No);
				sa.assertTrue(false,  "The record name and Contact Name have been verifed."+result5);
			}
			
		} else {
			log(LogStatus.ERROR, "Not able to click on " + targetName, YesNo.No);
			sa.assertTrue(false, "Not able to click on " + targetName);
		}
	}else {
		log(LogStatus.ERROR, "could not click on " + TabName.FundraisingsTab, YesNo.Yes);
		sa.assertTrue(false,"could not click on " + TabName.FundraisingsTab );
	}		
		lp.CRMlogout();
		sa.assertAll();
	}

}
