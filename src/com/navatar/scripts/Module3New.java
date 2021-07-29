package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.SmokeCommonVariables.AdminUserFirstName;
import static com.navatar.generic.SmokeCommonVariables.AdminUserLastName;
import static com.navatar.generic.SmokeCommonVariables.Smoke_EntityMeeting1Priority;
import static com.navatar.generic.SmokeCommonVariables.Smoke_EntityMeeting1Subject;
import static com.navatar.generic.SmokeCommonVariables.Smoke_EntityMeeting1Type;
import static com.navatar.generic.SmokeCommonVariables.Smoke_STDTask2OnSubject;
import static com.navatar.generic.SmokeCommonVariables.taskCustomObj1Name;

import java.util.ArrayList;
import java.util.List;


import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.navatar.generic.BaseLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.ActivityTimeLineItem;
import com.navatar.generic.EnumConstants.CSVLabel;
import com.navatar.generic.EnumConstants.ClickOrCheckEnableDisableCheckBox;
import com.navatar.generic.EnumConstants.CommitmentType;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.EditViewMode;
import com.navatar.generic.EnumConstants.EmailTemplateType;
import com.navatar.generic.EnumConstants.EnableDisable;
import com.navatar.generic.EnumConstants.FolderAccess;
import com.navatar.generic.EnumConstants.FundraisingContactPageTab;
import com.navatar.generic.EnumConstants.IndiviualInvestorFieldLabel;
import com.navatar.generic.EnumConstants.InstitutionPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.NavatarSetupSideMenuTab;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.PopUpName;
import com.navatar.generic.EnumConstants.ReportDashboardFolderType;
import com.navatar.generic.EnumConstants.ReportField;
import com.navatar.generic.EnumConstants.ReportFormatName;
import com.navatar.generic.EnumConstants.SearchBasedOnExistingFundsOptions;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.TopOrBottom;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.searchContactInEmailProspectGrid;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.BasePageErrorMessage;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.EmailMyTemplatesPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.NavatarSetupPageBusinessLayer;
import com.navatar.pageObjects.NavigationPageBusineesLayer;
import com.navatar.pageObjects.ReportsTabBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.navatar.pageObjects.TaskPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;



public class Module3New extends BaseLib {
	
	String passwordResetLink = null;
	String navigationMenuName="Navigation Menu";
	String contact = M3Contact1FName+" "+M3Contact1LName;
	// Deal Creation  -- UnCheck
	// Individual Investor -- UnCheck
	//Fund Name = ""Balanced Fund"" 
	//Aman Kumar
	
	@Parameters({ "projectName"})
	@Test
	public void M3Tc001_1_createCRMUser(String projectName) {
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
	public void M3Tc002_VerifyTheNavigationMenuItems(String projectName) {
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

	@Parameters({ "projectName"})
	@Test
	public void M3Tc003_VerifyImpactOnGlobalActionByDoingAllAvailableLinksToGetDisableFromNavatarSetup(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavatarSetupPageBusinessLayer np= new NavatarSetupPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		
		NavatarSetupSideMenuTab[] navatarSetupSideMenuTab = {NavatarSetupSideMenuTab.BulkEmail,NavatarSetupSideMenuTab.CommitmentCreation};
		NavatarSetupSideMenuTab setupSideMenuTab=null;

		navigationMenuName = NavigationMenuItems.Bulk_Actions.toString();
		String[] navigationLabel = {BulkActions_DefaultValues.Bulk_Email.toString(),
				BulkActions_DefaultValues.Bulk_Commitments.toString(),BulkActions_DefaultValues.Bulk_Fundraising.toString()};
		
		log(LogStatus.INFO, "<<<<<< Going to Uncheck >>>>>>>", YesNo.No);
		for (int i = 0; i < navatarSetupSideMenuTab.length; i++) {
			setupSideMenuTab=navatarSetupSideMenuTab[i];
			switchToDefaultContent(driver);
			if (ip.clickOnTab(projectName, TabName.NavatarSetup)) {
				log(LogStatus.INFO, "Clicked on Navatar Setup ", YesNo.No);
				if (np.clickOnNavatarSetupSideMenusTab(projectName, setupSideMenuTab)) {
					log(LogStatus.INFO,"Clicked on "+setupSideMenuTab, YesNo.No);
					if (clickUsingJavaScript(driver, np.getEditButtonforNavatarSetUpSideMenuTab(projectName,setupSideMenuTab, 10), "Edit Button for "+setupSideMenuTab, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button "+setupSideMenuTab, YesNo.No);
						ThreadSleep(7000);
						if (clickUsingJavaScript(driver,np.getEnableCheckBoxforClickNavatarSetUpSideMenuTab(projectName,setupSideMenuTab, EditViewMode.Edit, 10),setupSideMenuTab+" CheckBox", action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Enable "+setupSideMenuTab+" Box Checkbox", YesNo.No);
							ThreadSleep(2000);
							if (click(driver, np.getSaveButtonforNavatarSetUpSideMenuTab(projectName, setupSideMenuTab, 10, TopOrBottom.TOP), "Save Button", action.BOOLEAN)) {
								ThreadSleep(5000);
								log(LogStatus.INFO, "Clicked on Save Button for : "+setupSideMenuTab, YesNo.No);
								ThreadSleep(10000);
								if (!isSelected(driver, np.getEnableCheckBoxforNavatarSetUpSideMenuTab(projectName,setupSideMenuTab, EditViewMode.View, ClickOrCheckEnableDisableCheckBox.Click, 10), "Enabled " +setupSideMenuTab+" CheckBox")) {
									log(LogStatus.INFO, "Enable "+setupSideMenuTab+" is uncheck and verified ", YesNo.No);
								
									// Verification on navigation menu
									switchToDefaultContent(driver);
									if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
										log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
										WebElement ele = npbl.getNavigationLabel(projectName, navigationLabel[i], action.BOOLEAN, 10);
										if (ele==null) {
											log(LogStatus.INFO, navigationLabel[i]+" is not present on "+navigationMenuName+" after uncheck "+setupSideMenuTab, YesNo.No);
										} else {
											log(LogStatus.ERROR, navigationLabel[i]+" should not present on "+navigationMenuName+" after uncheck "+setupSideMenuTab, YesNo.Yes);
											sa.assertTrue(false,navigationLabel[i]+" should not present on "+navigationMenuName+" after uncheck "+setupSideMenuTab);
										}
										ele = npbl.getNavigationLabel(projectName, navigationLabel[2], action.BOOLEAN, 10);
										if (ele!=null) {
											log(LogStatus.INFO, navigationLabel[i]+" is present on "+navigationMenuName, YesNo.No);
										} else {
											log(LogStatus.ERROR, navigationLabel[i]+" should be present on "+navigationMenuName, YesNo.Yes);
											sa.assertTrue(false,navigationLabel[i]+" should be present on "+navigationMenuName);

										}
									} else {
										log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot uncheck absenece of "+navigationLabel[i], YesNo.Yes);
										sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot uncheck absenece of "+navigationLabel[i]);
									}
									
								}else {
									log(LogStatus.FAIL, setupSideMenuTab+" Checkbox should be uncheck", YesNo.Yes);
									sa.assertTrue(false, setupSideMenuTab+" Checkbox should be uncheck");
								}
							} else {
								sa.assertTrue(false, "Not Able to Click on Save Button for : "+setupSideMenuTab);
								log(LogStatus.SKIP, "Not Able to Click on Save Button for : "+setupSideMenuTab, YesNo.Yes);
							}
						} else {
							sa.assertTrue(false, "Not Able to Click on Enable "+setupSideMenuTab+" Checkbox");
							log(LogStatus.SKIP, "Not Able to Click on Enable "+setupSideMenuTab+" Checkbox", YesNo.Yes);
						}
						refresh(driver);
					}else {
						sa.assertTrue(false, "edit button is not clickable "+setupSideMenuTab);
						log(LogStatus.SKIP, "edit button is not clickable "+setupSideMenuTab, YesNo.Yes);
					}
				}else {
					sa.assertTrue(false, setupSideMenuTab+" side menu tab is not clickable");
					log(LogStatus.SKIP, setupSideMenuTab+" side menu tab is not clickable", YesNo.Yes);
				}
			}else {
				sa.assertTrue(false, "navatar setup tab is not clickable so cannot uncheck : "+setupSideMenuTab);
				log(LogStatus.SKIP, "navatar setup tab is not clickable so cannot uncheck : "+setupSideMenuTab, YesNo.Yes);
			}
			refresh(driver);
		}

		//// CHeck

		log(LogStatus.INFO, "<<<<<< Going to check >>>>>>>", YesNo.No);
		for (int i = 0; i < navatarSetupSideMenuTab.length; i++) {
			setupSideMenuTab=navatarSetupSideMenuTab[i];
			switchToDefaultContent(driver);
			if (ip.clickOnTab(projectName, TabName.NavatarSetup)) {
				log(LogStatus.INFO, "Clicked on Navatar Setup ", YesNo.No);
				if (np.clickOnNavatarSetupSideMenusTab(projectName, setupSideMenuTab)) {
					log(LogStatus.INFO,"Clicked on "+setupSideMenuTab, YesNo.No);
					if (clickUsingJavaScript(driver, np.getEditButtonforNavatarSetUpSideMenuTab(projectName,setupSideMenuTab, 10), "Edit Button for "+setupSideMenuTab, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button "+setupSideMenuTab, YesNo.No);
						ThreadSleep(2000);
						if (!isSelected(driver, np.getEnableCheckBoxforNavatarSetUpSideMenuTab(projectName,setupSideMenuTab, EditViewMode.Edit, ClickOrCheckEnableDisableCheckBox.Click, 10), "Enabled " +setupSideMenuTab+" CheckBox")) {
							log(LogStatus.INFO, "Enable "+setupSideMenuTab+" is Unchecked going to checked", YesNo.No);
							if (clickUsingJavaScript(driver,np.getEnableCheckBoxforClickNavatarSetUpSideMenuTab(projectName,setupSideMenuTab, EditViewMode.Edit, 10),setupSideMenuTab+" CheckBox", action.BOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Enable "+setupSideMenuTab+" Box Checkbox", YesNo.No);
								ThreadSleep(2000);
								if (click(driver, np.getSaveButtonforNavatarSetUpSideMenuTab(projectName, setupSideMenuTab, 10, TopOrBottom.TOP), "Save Button", action.BOOLEAN)) {
									ThreadSleep(5000);
									log(LogStatus.INFO, "Clicked on Save Button for : "+setupSideMenuTab, YesNo.No);
									ThreadSleep(10000);
									if (isSelected(driver, np.getEnableCheckBoxforNavatarSetUpSideMenuTab(projectName,setupSideMenuTab, EditViewMode.View, ClickOrCheckEnableDisableCheckBox.Click, 10), "Enabled " +setupSideMenuTab+" CheckBox")) {
										log(LogStatus.INFO, "Enable "+setupSideMenuTab+" is Checked and verified ", YesNo.No);
									
										// Verification on navigation menu
										switchToDefaultContent(driver);
										
										if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
											log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
											WebElement ele = npbl.getNavigationLabel(projectName, navigationLabel[i], action.BOOLEAN, 10);
											if (ele!=null) {
												log(LogStatus.INFO, navigationLabel[i]+" is  present on "+navigationMenuName+" after check "+setupSideMenuTab, YesNo.No);

											} else {
												log(LogStatus.ERROR, navigationLabel[i]+" should be present on "+navigationMenuName+" after check "+setupSideMenuTab, YesNo.Yes);
												sa.assertTrue(false,navigationLabel[i]+" should be present on "+navigationMenuName+" after check "+setupSideMenuTab);

											}

											ele = npbl.getNavigationLabel(projectName, navigationLabel[2], action.BOOLEAN, 10);
											if (ele!=null) {
												log(LogStatus.INFO, navigationLabel[i]+" is present on "+navigationMenuName, YesNo.No);

											} else {
												log(LogStatus.ERROR, navigationLabel[i]+" should be present on "+navigationMenuName, YesNo.Yes);
												sa.assertTrue(false,navigationLabel[i]+" should be present on "+navigationMenuName);

											}
										} else {
											log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot check presence of "+navigationLabel[i], YesNo.Yes);
											sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot check presence of "+navigationLabel[i]);
										}
									
									}else {
										log(LogStatus.FAIL, setupSideMenuTab+" Checkbox should be checked", YesNo.Yes);
										sa.assertTrue(false, setupSideMenuTab+" Checkbox should be checked");

									}
								} else {
									sa.assertTrue(false, "Not Able to Click on Save Button for : "+setupSideMenuTab);
									log(LogStatus.SKIP, "Not Able to Click on Save Button for : "+setupSideMenuTab, YesNo.Yes);
								}
							} else {
								sa.assertTrue(false, "Not Able to Click on Enable "+setupSideMenuTab+" Checkbox");
								log(LogStatus.SKIP, "Not Able to Click on Enable "+setupSideMenuTab+" Checkbox", YesNo.Yes);
							}
						}else {
							log(LogStatus.SKIP, setupSideMenuTab+" Checkbox is already checked", YesNo.Yes);
							sa.assertTrue(false, setupSideMenuTab+" Checkbox is already checked");
						}
					}else {
						sa.assertTrue(false, "edit button is not clickable "+setupSideMenuTab);
						log(LogStatus.SKIP, "edit button is not clickable "+setupSideMenuTab, YesNo.Yes);
					}
				}else {
					sa.assertTrue(false, setupSideMenuTab+" side menu tab is not clickable");
					log(LogStatus.SKIP, setupSideMenuTab+" side menu tab is not clickable", YesNo.Yes);
				}
			}else {
				sa.assertTrue(false, "navatar setup tab is not clickable so cannot uncheck : "+setupSideMenuTab);
				log(LogStatus.SKIP, "navatar setup tab is not clickable so cannot uncheck : "+setupSideMenuTab, YesNo.Yes);
			}
			refresh(driver);
		}

		lp.CRMlogout();
		sa.assertAll();
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

		navigationMenuName = NavigationMenuItems.Bulk_Actions.toString();
		
		String[] navigationLabels = {BulkActions_DefaultValues.Deal_Creation.toString(),
				BulkActions_DefaultValues.Individual_Investor_Creation.toString()};
		

		//// CHeck

		log(LogStatus.INFO, "<<<<<< Going to check >>>>>>>", YesNo.No);
		for (int i = 0; i < navatarSetupSideMenuTab.length; i++) {
			setupSideMenuTab=navatarSetupSideMenuTab[i];
			switchToDefaultContent(driver);
			if (ip.clickOnTab(projectName, TabName.NavatarSetup)) {
				log(LogStatus.INFO, "Clicked on Navatar Setup ", YesNo.No);
				if (np.clickOnNavatarSetupSideMenusTab(projectName, setupSideMenuTab)) {
					log(LogStatus.INFO,"Clicked on "+setupSideMenuTab, YesNo.No);
					if (clickUsingJavaScript(driver, np.getEditButtonforNavatarSetUpSideMenuTab(projectName,setupSideMenuTab, 10), "Edit Button for "+setupSideMenuTab, action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Edit Button "+setupSideMenuTab, YesNo.No);
						ThreadSleep(2000);
						if (!isSelected(driver, np.getEnableCheckBoxforNavatarSetUpSideMenuTab(projectName,setupSideMenuTab, EditViewMode.Edit, ClickOrCheckEnableDisableCheckBox.Click, 10), "Enabled " +setupSideMenuTab+" CheckBox")) {
							log(LogStatus.INFO, "Enable "+setupSideMenuTab+" is Unchecked going to checked", YesNo.No);
							if (clickUsingJavaScript(driver,np.getEnableCheckBoxforClickNavatarSetUpSideMenuTab(projectName,setupSideMenuTab, EditViewMode.Edit, 10),setupSideMenuTab+" CheckBox", action.BOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Enable "+setupSideMenuTab+" Box Checkbox", YesNo.No);
								ThreadSleep(2000);
								if (click(driver, np.getSaveButtonforNavatarSetUpSideMenuTab(projectName, setupSideMenuTab, 10, TopOrBottom.TOP), "Save Button", action.BOOLEAN)) {
									ThreadSleep(5000);
									log(LogStatus.INFO, "Clicked on Save Button for : "+setupSideMenuTab, YesNo.No);
									ThreadSleep(10000);
									if (isSelected(driver, np.getEnableCheckBoxforNavatarSetUpSideMenuTab(projectName,setupSideMenuTab, EditViewMode.View, ClickOrCheckEnableDisableCheckBox.Click, 10), "Enabled " +setupSideMenuTab+" CheckBox")) {
										log(LogStatus.INFO, "Enable "+setupSideMenuTab+" is Checked and verified ", YesNo.No);

										// Verification on navigation menu
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

									}else {
										log(LogStatus.FAIL, setupSideMenuTab+" Checkbox should be checked", YesNo.Yes);
										sa.assertTrue(false, setupSideMenuTab+" Checkbox should be checked");

									}
								} else {
									sa.assertTrue(false, "Not Able to Click on Save Button for : "+setupSideMenuTab);
									log(LogStatus.SKIP, "Not Able to Click on Save Button for : "+setupSideMenuTab, YesNo.Yes);
								}
							} else {
								sa.assertTrue(false, "Not Able to Click on Enable "+setupSideMenuTab+" Checkbox");
								log(LogStatus.SKIP, "Not Able to Click on Enable "+setupSideMenuTab+" Checkbox", YesNo.Yes);
							}
						}else {
							log(LogStatus.SKIP, setupSideMenuTab+" Checkbox is already checked", YesNo.Yes);
							sa.assertTrue(false, setupSideMenuTab+" Checkbox is already checked");
						}
					}else {
						sa.assertTrue(false, "edit button is not clickable "+setupSideMenuTab);
						log(LogStatus.SKIP, "edit button is not clickable "+setupSideMenuTab, YesNo.Yes);
					}
				}else {
					sa.assertTrue(false, setupSideMenuTab+" side menu tab is not clickable");
					log(LogStatus.SKIP, setupSideMenuTab+" side menu tab is not clickable", YesNo.Yes);
				}
			}else {
				sa.assertTrue(false, "navatar setup tab is not clickable so cannot check : "+setupSideMenuTab);
				log(LogStatus.SKIP, "navatar setup tab is not clickable so cannot check : "+setupSideMenuTab, YesNo.Yes);
			}
			refresh(driver);
		}

		// create navigation item 
		

		String navigationLabel=CSVLabel.Navigation_Label.toString();
		String orderLabel=CSVLabel.Order.toString();
		String urlLabel = CSVLabel.URL.toString();
		String navigationTypeLabel = CSVLabel.Navigation_Type.toString();
		
		for (int i = 0; i < navigationLabels.length; i++) {
			
			String navigationLabelValue=navigationLabels[i];
			String orderLabelValue=String.valueOf(i+4);
			String urlLabelValue="";
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
						log(LogStatus.INFO, navigationLabels[i]+" is  present on "+navigationMenuName+" after check "+setupSideMenuTab, YesNo.No);

					} else {
						log(LogStatus.ERROR, navigationLabels[i]+" should be present on "+navigationMenuName+" after check "+setupSideMenuTab, YesNo.Yes);
						sa.assertTrue(false,navigationLabels[i]+" should be present on "+navigationMenuName+" after check "+setupSideMenuTab);

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
		
	//// Uncheck

			log(LogStatus.INFO, "<<<<<< Going to uncheck >>>>>>>", YesNo.No);
			for (int i = 0; i < navatarSetupSideMenuTab.length; i++) {
				setupSideMenuTab=navatarSetupSideMenuTab[i];
				switchToDefaultContent(driver);
				if (ip.clickOnTab(projectName, TabName.NavatarSetup)) {
					log(LogStatus.INFO, "Clicked on Navatar Setup ", YesNo.No);
					if (np.clickOnNavatarSetupSideMenusTab(projectName, setupSideMenuTab)) {
						log(LogStatus.INFO,"Clicked on "+setupSideMenuTab, YesNo.No);
						if (clickUsingJavaScript(driver, np.getEditButtonforNavatarSetUpSideMenuTab(projectName,setupSideMenuTab, 10), "Edit Button for "+setupSideMenuTab, action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Edit Button "+setupSideMenuTab, YesNo.No);
							ThreadSleep(2000);
							if (isSelected(driver, np.getEnableCheckBoxforNavatarSetUpSideMenuTab(projectName,setupSideMenuTab, EditViewMode.Edit, ClickOrCheckEnableDisableCheckBox.Click, 10), "Enabled " +setupSideMenuTab+" CheckBox")) {
								log(LogStatus.INFO, "Enable "+setupSideMenuTab+" is checked going to Unchecked", YesNo.No);
								if (clickUsingJavaScript(driver,np.getEnableCheckBoxforClickNavatarSetUpSideMenuTab(projectName,setupSideMenuTab, EditViewMode.Edit, 10),setupSideMenuTab+" CheckBox", action.BOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Enable "+setupSideMenuTab+" Box Checkbox", YesNo.No);
									ThreadSleep(2000);
									if (click(driver, np.getSaveButtonforNavatarSetUpSideMenuTab(projectName, setupSideMenuTab, 10, TopOrBottom.TOP), "Save Button", action.BOOLEAN)) {
										ThreadSleep(5000);
										log(LogStatus.INFO, "Clicked on Save Button for : "+setupSideMenuTab, YesNo.No);
										ThreadSleep(10000);
										if (!isSelected(driver, np.getEnableCheckBoxforNavatarSetUpSideMenuTab(projectName,setupSideMenuTab, EditViewMode.View, ClickOrCheckEnableDisableCheckBox.Click, 10), "Enabled " +setupSideMenuTab+" CheckBox")) {
											log(LogStatus.INFO, "Enable "+setupSideMenuTab+" is UnChecked and verified ", YesNo.No);

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

										}else {
											log(LogStatus.FAIL, setupSideMenuTab+" Checkbox should be checked", YesNo.Yes);
											sa.assertTrue(false, setupSideMenuTab+" Checkbox should be checked");

										}
									} else {
										sa.assertTrue(false, "Not Able to Click on Save Button for : "+setupSideMenuTab);
										log(LogStatus.SKIP, "Not Able to Click on Save Button for : "+setupSideMenuTab, YesNo.Yes);
									}
								} else {
									sa.assertTrue(false, "Not Able to Click on Enable "+setupSideMenuTab+" Checkbox");
									log(LogStatus.SKIP, "Not Able to Click on Enable "+setupSideMenuTab+" Checkbox", YesNo.Yes);
								}
							}else {
								log(LogStatus.SKIP, setupSideMenuTab+" Checkbox is already unchecked", YesNo.Yes);
								sa.assertTrue(false, setupSideMenuTab+" Checkbox is already unchecked");
							}
						}else {
							sa.assertTrue(false, "edit button is not clickable "+setupSideMenuTab);
							log(LogStatus.SKIP, "edit button is not clickable "+setupSideMenuTab, YesNo.Yes);
						}
					}else {
						sa.assertTrue(false, setupSideMenuTab+" side menu tab is not clickable");
						log(LogStatus.SKIP, setupSideMenuTab+" side menu tab is not clickable", YesNo.Yes);
					}
				}else {
					sa.assertTrue(false, "navatar setup tab is not clickable so cannot uncheck : "+setupSideMenuTab);
					log(LogStatus.SKIP, "navatar setup tab is not clickable so cannot uncheck : "+setupSideMenuTab, YesNo.Yes);
				}
				refresh(driver);
			}
			
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

			String[] splitedReportName = removeNumbersFromString(SmokeReportName);
			SmokeReportName = splitedReportName[0] + lp.generateRandomNumber();

			ExcelUtils.writeData(phase1DataSheetFilePath, SmokeReportFolderName, "Report", excelLabel.Variable_Name, "SmokeReport1",
					excelLabel.Report_Folder_Name);
			if (report.createCustomReportForFolder(environment, mode, SmokeReportFolderName,ReportFormatName.Null,SmokeReportName,
					SmokeReportName, SmokeReportType, ReportField.ContactID, SmokeReportShow, null, SmokeReportRange, null, null)) {
				appLog.info("Custom Report is created succesdfully : " + SmokeReportName);
				ExcelUtils.writeData(phase1DataSheetFilePath, SmokeReportName, "Report", excelLabel.Variable_Name, "SmokeReport1",
						excelLabel.Report_Name);
			} else {
				appLog.error("Not able to create Custom Report : " + SmokeReportName);
				sa.assertTrue(false, "Not able to create Custom Report : " + SmokeReportName);
				log(LogStatus.ERROR, "Not able to create Custom Report : " + SmokeReportName, YesNo.Yes);
			}
			switchToDefaultContent(driver);
			home.clickOnSetUpLink(environment, Mode.Classic.toString());
			if (home.clickOnTab(environment, Mode.Classic.toString(), TabName.HomeTab)) {
				SmokeReportName="R2"+SmokeReportName;
				if (report.createCustomReportForFolder(environment, mode, SmokeReportFolderName,ReportFormatName.Null,SmokeReportName,
						SmokeReportName, SmokeReportType, null, SmokeReportShow, null, SmokeReportRange, null, null)) {
					appLog.info("Custom Report is created succesdfully : R2"+SmokeReportName);
				} else {
					appLog.error("Not able to create Custom Report : R2"+ SmokeReportName);
					sa.assertTrue(false, "Not able to create Custom Report : R2"+SmokeReportName);
					log(LogStatus.ERROR, "Not able to create Custom Report : R2"+SmokeReportName, YesNo.Yes);
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
			if (emailtemplate.createCustomEmailFolder(environment, Mode.Classic.toString(), EmailTemplate1_FolderName, FolderAccess.ReadWrite)) {
				log(LogStatus.PASS, "Email Template Folder is created : "+EmailTemplate1_FolderName, YesNo.No);
				ExcelUtils.writeData(phase1DataSheetFilePath, EmailTemplate1_FolderName, "CustomEmailFolder", excelLabel.Variable_Name, "EmailTemplate1",
						excelLabel.Email_Template_Folder_Label);
				ThreadSleep(2000);
				String[] splitedEmailTemplateName = removeNumbersFromString(EmailTemplate1_TemplateName);
				EmailTemplate1_TemplateName = splitedEmailTemplateName[0] + lp.generateRandomNumber();
				if (emailtemplate.createCustomEmailTemplate(environment, Mode.Classic.toString(), EmailTemplate1_FolderName, EmailTemplateType.Text,
						EmailTemplate1_TemplateName, EmailTemplate1_TemplateDescription, EmailTemplate1_Subject, EmailTemplate1_Body)) {
					appLog.info("EMail Template is created :" + EmailTemplate1_TemplateName);

					ExcelUtils.writeData(phase1DataSheetFilePath, EmailTemplate1_TemplateName, "CustomEmailFolder", excelLabel.Variable_Name, "EmailTemplate1",
							excelLabel.Email_Template_Name);

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
		navigationMenuName = NavigationMenuItems.Bulk_Actions.toString();
		NavigationPageBusineesLayer  npbl = new NavigationPageBusineesLayer(driver) ;
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);

		String[]  BulkActionNavigationLinks = {BulkActions_DefaultValues.Bulk_Email.toString(),
				BulkActions_DefaultValues.Bulk_Fundraising.toString(),
				BulkActions_DefaultValues.Bulk_Commitments.toString(),
				BulkActions_DefaultValues.Deal_Creation.toString(),
				BulkActions_DefaultValues.Individual_Investor_Creation.toString()};
		int i=0;
		boolean flag = false;
		for (String bulkActionNavigationLink : BulkActionNavigationLinks) {
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

				if (flag) {
					if (i==0) {
						String reportName="Public Reports";
						String templateName="Test 123";
						String fname="";
						String lname = "";
						String folderName=EmailTemplate1_FolderName;
						String emailTemplateName = EmailTemplate1_TemplateName;
						if (hp.VerifyBulkEmailFunctionality(environment, mode, reportName, templateName, fname, lname, lname, searchContactInEmailProspectGrid.Yes, folderName, emailTemplateName)) {
							log(LogStatus.INFO, bulkActionNavigationLink+" functionality is verified succesfuly ", YesNo.No);

						} else {
							log(LogStatus.ERROR, bulkActionNavigationLink+" functionality not verified ", YesNo.Yes);
							sa.assertTrue(false,bulkActionNavigationLink+" functionality not verified ");

						}

					} else if(i==1) {
						String Smoke_Fund1 = "Balanced Fund";
						String institue = "Aman Institute";
						String contact = "Aman Kumar";
						String fr = institue+" - "+Smoke_Fund1;
						List<String> contactNamelist= new ArrayList<String>();
						contactNamelist.add(contact);
						List<String> accountlist= new ArrayList<String>();
						accountlist.add(institue);

						switchToFrame(driver, 30, home.getCreateFundraisingsFrame_Lighting(20));

						if(hp.selectFundNameOrCompanyNameOnCreateFundraisings(environment,mode, PopUpName.selectFundPopUp, Smoke_Fund1, null)) {
							log(LogStatus.INFO, "Select Fund : "+Smoke_Fund1, YesNo.No);
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
														//	ExcelUtils.writeData(smokeFilePath,SmokeINS1+" - "+Smoke_Fund1, "Fundraisings",excelLabel.Variable_Name, "SmokeFR1",excelLabel.FundRaising_Name);
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
						String Smoke_FR1 = "Relaince Fundraising";
						String Smoke_LP1= "Reliance Digital";
						String Smoke_P1= "Reliance Partner";
						String[][] commitmentInformation= {{Smoke_LP1,Smoke_P1}};

						if(hp.selectFundraisingNameOrCommitmentType(environment, mode, Smoke_FR1, null, null, null, CommitmentType.fundraisingName)) {
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
					}else if(i==3){
						String Smoke_PL2CompanyName ="Reliance";
						String Smoke_PL2Stage ="Due Deligence";
						if (hp.createNewDealPipeLine(environment, mode, Smoke_PL2CompanyName, Smoke_PL2Stage,null)) {
							appLog.info("VAlue added for mandatory Field for Deal Creation");
							String monthAndYear = getSystemDate("MMM") + " " + getSystemDate("yyyy");
							String expectedPipeLineName = Smoke_PL2CompanyName + " " + "-" + " " + monthAndYear;

							if (hp.clickOnCreateDealButtonAndVerifyingLandingPage(environment, mode, Smoke_PL2CompanyName)) {
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
						String SmokeC7_FName = "";
						String SmokeC7_LName ="";
						String SmokeINDINV4 = SmokeC7_FName+" "+SmokeC7_LName+" - "+"HNI";
						String[][] labelNamesAndValue= {
								{IndiviualInvestorFieldLabel.First_Name.toString(),SmokeC7_FName},
								{IndiviualInvestorFieldLabel.Last_Name.toString(),SmokeC7_LName}};
					
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
				i++;
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
		NavigationPageBusineesLayer  npbl = new NavigationPageBusineesLayer(driver) ;
		HomePageBusineesLayer hp = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);

		navigationMenuName = NavigationMenuItems.New_Interactions.toString();

		String[]  newInteractionsNavigationLinks = {NewInteractions_DefaultValues.Call.toString(),
				NewInteractions_DefaultValues.Meeting.toString(),
				NewInteractions_DefaultValues.Task.toString()};
		int i=0;
		boolean flag = false;
		String adminUerName = AdminUserFirstName+" "+AdminUserLastName;
		String subject ="";
		String dueDate=todaysDate;
		String status="";
		String priority="";
		String meetingType = "";
		String contactNAme= contact;
		String[][] dropDownLabelWithValues = new String[3][];

		for (String newInteractionsNavigationLink : newInteractionsNavigationLinks) {
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

					ele=cp.getCrossButtonForAlreadySelectedItem(projectName, PageName.TestCustomObjectPage, PageLabel.Assigned_To.toString(),false, adminUerName, action.SCROLLANDBOOLEAN, 20);
					if (ele!=null) {
						log(LogStatus.INFO, adminUerName+" Found For Label "+PageLabel.Assigned_To.toString(),YesNo.No);	
					} else {
						sa.assertTrue(false,adminUerName+" not Found For Label "+PageLabel.Assigned_To.toString());
						log(LogStatus.ERROR, adminUerName+" not Found For Label "+PageLabel.Assigned_To.toString(),YesNo.Yes);

					}

					if (i==0) {
						subject =M3Call1Subject;
						status=M3Call1Status;
						priority=M3Cal11Priority;

						String[][] dropDownLabelWithValues1 = {{PageLabel.Status.toString(),status},
								{PageLabel.Priority.toString(),priority}};

						dropDownLabelWithValues=dropDownLabelWithValues1;

					} else if(i==1) {

						subject =M3Meeting1Subject;
						status=M3Meeting1Status;
						priority=M3Meeting1Priority;
						meetingType=M3Meeting1MeetingType;

						String[][] dropDownLabelWithValues2 = {{PageLabel.Status.toString(),status},
								{PageLabel.Priority.toString(),priority},
								{PageLabel.Meeting_Type.toString(),meetingType}};
						dropDownLabelWithValues=dropDownLabelWithValues2;

					}else{
						subject =M3Task1Subject;
						status=M3Task1Status;
						priority=M3Task1Priority;
						String[][] dropDownLabelWithValues3 = {{PageLabel.Status.toString(),status},
								{PageLabel.Priority.toString(),priority}};	
						dropDownLabelWithValues=dropDownLabelWithValues3;
					}

					if (tp.enteringSubjectAndSelectDropDownValuesonTaskPopUp(projectName, PageName.Object1Page, subject, dropDownLabelWithValues, action.SCROLLANDBOOLEAN, 10)) {
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
								if (actualValue.contains(expectedValue)) {
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
									{PageLabel.Status.toString(),status},
									{PageLabel.Priority.toString(),priority},
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
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on : "+newInteractionsNavigationLink+" for creation ", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on : "+newInteractionsNavigationLink+" for creation ");
			}
			i++;
		}
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
		lp.CRMLogin(superAdminUserName, adminPassword);

		navigationMenuName = NavigationMenuItems.Create_New.toString();

		String[]  createNewNavigationLinks = {CreateNew_DefaultValues.New_Deal.toString(),
				CreateNew_DefaultValues.New_Contact.toString(),
				CreateNew_DefaultValues.New_Institution.toString(),
				CreateNew_DefaultValues.New_Institution.toString(),
				CreateNew_DefaultValues.New_Institution.toString(),
				CreateNew_DefaultValues.New_Institution.toString(),
				CreateNew_DefaultValues.New_Institution.toString(),
				CreateNew_DefaultValues.New_Institution.toString()
				};
		int i=0;
		boolean flag = false;

		for (String createNewNavigationLink : createNewNavigationLinks) {
			flag=false;
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 30)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName+" Going to click on : "+createNewNavigationLink+" for creation ", YesNo.No);

				WebElement ele = npbl.getNavigationLabel(projectName, createNewNavigationLink, action.BOOLEAN, 10);
				if (ele!=null && click(driver, ele, createNewNavigationLink, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on "+createNewNavigationLink+" so going for creation", YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+createNewNavigationLink+" so cannot create data related to this ", YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+createNewNavigationLink+" so cannot create data related to this ");

				}

				if (flag) {

					if (i==0) {
						
						if (fp.createDeal(projectName,M3Deal1RecordType,M3Deal1,M3Deal1CompanyName, M3Deal1Stage,null, 15)) {
							log(LogStatus.INFO,"Created Deal : "+M3Deal1+" through "+createNewNavigationLink,YesNo.No);	
						} else {
							sa.assertTrue(false,"Not Able to Create Deal  : "+" through "+createNewNavigationLink);
							log(LogStatus.SKIP,"Not Able to Create Deal  : "+" through "+createNewNavigationLink,YesNo.Yes);
						}
						
					} else if(i==1) {
						
						
						M3Contact2EmailID=	lp.generateRandomEmailId(gmailUserName);
						ExcelUtils.writeData(phase1DataSheetFilePath, M3Contact2EmailID, "Contacts", excelLabel.Variable_Name, "M3CON2",excelLabel.Contact_EmailId);

						if (cp.createContact(projectName, M3Contact2FName, M3Contact2LName, M3Ins2, M3Contact2EmailID,M3Contact2RecordType, null, null, CreationPage.ContactPage, null)) {
							log(LogStatus.INFO,"successfully Created Contact : "+M3Contact2FName+" "+M3Contact2LName,YesNo.No);	
						} else {
							sa.assertTrue(false,"Not Able to Create Contact : "+M3Contact2FName+" "+M3Contact2LName);
							log(LogStatus.SKIP,"Not Able to Create Contact: "+M3Contact2FName+" "+M3Contact2LName,YesNo.Yes);
						}
						
					}else{
						String value="";
						String type="";
						String parent=null;
						 if (i==2) {
							value=M3Ins3;
							type=M3Ins3RecordType;
						} else if(i==3) {
							value=M3Ins4;
							type=M3Ins4RecordType;
						}else if(i==4) {
							value=M3Ins5;
							type=M3Ins5RecordType;
						} else if(i==5) {
							value=M3Ins6;
							type=M3Ins6RecordType;
							parent=M3Ins6Parent;
						}else if(i==6) {
							value=M3Ins7;
							type=M3Ins7RecordType;
						}else if(i==7) {
							value=M3Ins8;
							type=M3Ins8RecordType;
							parent=M3Ins8Parent;
						}
						
//						 if (ip.createEntityOrAccount(projectName, value, type, null, 20)) {
//								log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
//							} else {
//								sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
//								log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
//							}
						 
						 if (ip.createInstitution(projectName, environment, mode, value,type, InstitutionPageFieldLabelText.Parent_Institution.toString(),parent)) {
								log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
							} else {
								sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
								log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
							}
						 
						 
					}

					
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName+" so cannot click on : "+createNewNavigationLink+" for creation ", YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName+" so cannot click on : "+createNewNavigationLink+" for creation ");
			}
			i++;
			refresh(driver);
		}
		lp.CRMlogout();
		sa.assertAll();

}
	
}