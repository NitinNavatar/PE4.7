package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import java.util.List;


import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.navatar.generic.BaseLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.CSVLabel;
import com.navatar.generic.EnumConstants.ClickOrCheckEnableDisableCheckBox;
import com.navatar.generic.EnumConstants.EditViewMode;
import com.navatar.generic.EnumConstants.NavatarSetupSideMenuTab;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.TopOrBottom;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.NavatarSetupPageBusinessLayer;
import com.navatar.pageObjects.NavigationPageBusineesLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;



public class Module3New extends BaseLib {
	
	String passwordResetLink = null;
	String navigationMenuName="Navigation Menu";
	
	// UnCheck the Enable Deal Creation
	// UnCheck Individual Investor
	
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
	
	
}