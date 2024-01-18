package com.navatar.scripts;

import static com.navatar.generic.CommonLib.ThreadSleep;
import static com.navatar.generic.CommonLib.click;
import static com.navatar.generic.CommonLib.clickUsingJavaScript;
import static com.navatar.generic.CommonLib.exit;
import static com.navatar.generic.CommonLib.isDisplayed;
import static com.navatar.generic.CommonLib.log;
import static com.navatar.generic.CommonLib.refresh;
import static com.navatar.generic.CommonLib.removeNumbersFromString;
import static com.navatar.generic.CommonLib.selectVisibleTextFromDropDown;
import static com.navatar.generic.CommonLib.sendKeys;
import static com.navatar.generic.CommonLib.switchOnWindow;
import static com.navatar.generic.CommonLib.switchToDefaultContent;
import static com.navatar.generic.CommonLib.switchToFrame;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;
import static com.navatar.generic.SmokeCommonVariables.superAdminUserName;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.NavigationMenuItems;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.PermissionType;
import com.navatar.generic.EnumConstants.RecordType;
import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;
import com.navatar.generic.EnumConstants.recordTypeLabel;
import com.navatar.generic.ExcelUtils;
import com.navatar.pageObjects.BasePageBusinessLayer;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.NavigationPageBusineesLayer;
import com.navatar.pageObjects.ResearchPageBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;

public class AcuityQuickLinks extends BaseLib{

	String navigationMenuName=NavigationMenuItems.Create.toString().replace("_", " ");
	
@Parameters({ "projectName"})
@Test
	public void AQTc001_CreateUsers(String projectName) {
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
							glUserProfile, null)) {
						log(LogStatus.INFO, "CRM User is created Successfully: " + glUser1FirstName + " " + UserLastName, YesNo.No);
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
				if (setup.installedPackages(glUser1FirstName, UserLastName)) {
					appLog.info("PE Package is installed Successfully in CRM User: " + glUser1FirstName + " "
							+ UserLastName);

				} else {
					appLog.error(
							"Not able to install PE package in CRM User1: " + glUser1FirstName + " " + UserLastName);
					sa.assertTrue(false,
							"Not able to install PE package in CRM User1: " + glUser1FirstName + " " + UserLastName);
					log(LogStatus.ERROR,
							"Not able to install PE package in CRM User1: " + glUser1FirstName + " " + UserLastName,
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
		sa.assertAll();
		lp.CRMlogout();
	}

@Parameters({ "projectName"})
@Test
	public void AQTc002_VerifyTheNavigationMenuItems(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	ContactsPageBusinessLayer ip = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword);
	
	// Verification on navigation menu
	if (ip.clickOnTab(projectName, TabName.InstituitonsTab)) {
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on " + navigationMenuName, YesNo.No);
		if(npbl.getCreateLinkOnNavigationPage(5).getText().equalsIgnoreCase(navigationMenuName)) {
			log(LogStatus.INFO, "items verified at first position with name " + navigationMenuName, YesNo.No);
		} else {
			log(LogStatus.ERROR, "item is not verified at first position with name " + navigationMenuName, YesNo.Yes);
			sa.assertTrue(false,"item is not verified at first position with name " + navigationMenuName);
		}
	} else {
		log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName, YesNo.Yes);
		sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
	}
 } else {
	 	log(LogStatus.ERROR, "Not Able to Click on "+TabName.InstituitonsTab, YesNo.Yes);
		sa.assertTrue(false,"Not Able to Click on "+TabName.InstituitonsTab);
 }
	
	refresh(driver);
	if (ip.clickOnTab(projectName, TabName.InstituitonsTab)) {	
	if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
		log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
		ThreadSleep(2000);
		if(clickUsingJavaScript(driver, rp.getResearchMinimize(10),"Research Minimize Button", action.BOOLEAN)) {
			log(LogStatus.INFO, "Research popup successfully minimized", YesNo.No);
		} else {
			log(LogStatus.ERROR, "Research popup not successfully minimized", YesNo.Yes);
			sa.assertTrue(false,"Research popup not successfully minimized");
		}
	} else {
		log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName, YesNo.Yes);
		sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
	}
 } else {
	 	log(LogStatus.ERROR, "Not Able to Click on "+TabName.InstituitonsTab, YesNo.Yes);
		sa.assertTrue(false,"Not Able to Click on "+TabName.InstituitonsTab);
}
		ThreadSleep(2000);
		switchToDefaultContent(driver);
		sa.assertAll();
		lp.CRMlogout();
	}
	
@Parameters({ "projectName"})
@Test
	public void AQTc003_VerifyTheNavigationMenuItems(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	ContactsPageBusinessLayer ip = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword);
	
	// Verification on navigation menu
	if (ip.clickOnTab(projectName, TabName.InstituitonsTab)) {	
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(npbl.getFirmLinkOnCreateOption(5).getText().equalsIgnoreCase(PageLabel.Firm.toString())) {
				log(LogStatus.INFO, PageLabel.Firm.toString() + "is visible at 4th position", YesNo.No);
			} else {
				log(LogStatus.ERROR, PageLabel.Firm.toString() + "is not visible at 4th position", YesNo.Yes);
				sa.assertTrue(false,PageLabel.Firm.toString() + "is not visible at 4th position");
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
		}
	} else {
		log(LogStatus.ERROR, "Not Able to Click on "+ TabName.InstituitonsTab, YesNo.Yes);
		sa.assertTrue(false,"Not Able to Click on "+ TabName.InstituitonsTab);
	}
	refresh(driver);
	
	if (ip.clickOnTab(projectName, TabName.InstituitonsTab)) {
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(clickUsingJavaScript(driver, npbl.getFirmLinkOnCreateOption(5),"Firm Link on Create Option", action.BOOLEAN)) {
				log(LogStatus.INFO, "Able to Click on " + PageLabel.Firm.toString(), YesNo.No);
				
				if(npbl.getFirmNameTextBoxForCreateOption(5) != null) {
					log(LogStatus.INFO, "Firm Name from Create Option is visible", YesNo.No);
				} else {
					log(LogStatus.ERROR, "Firm Name from Create Option is not visible", YesNo.Yes);
					sa.assertTrue(false,"Firm Name from Create Option is not visible");
				}
				
				if(npbl.getFirmRecordTypeForCreateOption(5) != null) {
					log(LogStatus.INFO, "Firm Record Type from Create Option is visible", YesNo.No);
				} else {
					log(LogStatus.ERROR,"Firm Record Type from Create Option is not visible", YesNo.Yes);
					sa.assertTrue(false,"Firm Record Type from Create Option is not visible");
				}
				
				if(npbl.getCrossIconForQuickFirm(5) != null) {
					log(LogStatus.INFO, "Cross Icon for Quick Firm is visible", YesNo.No);
				} else {
					log(LogStatus.ERROR,"Cross Icon for Quick Firm is not visible", YesNo.Yes);
					sa.assertTrue(false,"Cross Icon for Quick Firm is not visible");
				}

			if(click(driver, npbl.getFirmNameTextBoxForCreateOption(5),"Firm Name Text Box For Create Option", action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Firm Name Text Box", YesNo.No);
				ThreadSleep(2000);
				if(npbl.getSaveButtonForCreateOption(5) != null) {
					log(LogStatus.INFO, "Save Button from Create Option is visible", YesNo.No);
					click(driver, npbl.getSaveButtonForCreateOption(5),"Save Button For Quick Firm", action.BOOLEAN);
					if(npbl.getErrorOnQuickFirm(5) != null) {
						log(LogStatus.INFO,"Error Message is visible" + npbl.getErrorOnQuickFirm(5), YesNo.No);
					} else {
						log(LogStatus.ERROR,"Error Message is not visible" + npbl.getErrorOnQuickFirm(5), YesNo.Yes);
						sa.assertTrue(false,"Error Message is not visible" + npbl.getErrorOnQuickFirm(5));
					}
					if(npbl.getErrorOnQuickFirmName(5) != null) {
						log(LogStatus.INFO,"Error Message is visible" + npbl.getErrorOnQuickFirmName(5), YesNo.No);
					} else {
						log(LogStatus.ERROR,"Error Message is not visible" + npbl.getErrorOnQuickFirmName(5), YesNo.Yes);
						sa.assertTrue(false,"Error Message is not visible" + npbl.getErrorOnQuickFirmName(5));
					}
				} else {
					log(LogStatus.ERROR,"Save Button from Create Option is not visible", YesNo.Yes);
					sa.assertTrue(false,"Save Button from Create Option is not visible");
				}
			} else {
				log(LogStatus.ERROR,"Not able to clicked on Firm Name Text Box", YesNo.Yes);
				sa.assertTrue(false,"Not able to clicked on Firm Name Text Box");
			}
				if(npbl.getCancelButtonForCreateOption(5) != null) {
					log(LogStatus.INFO, "Cancel Button from Create Option is visible", YesNo.No);
					clickUsingJavaScript(driver, npbl.getCancelButtonForCreateOption(5),"Cancel Button For Quick Firm", action.BOOLEAN);
					if(npbl.getCancelButtonForCreateOption(5) == null) {
						log(LogStatus.INFO,"Create Firm Popup is closed", YesNo.Yes);
					} else {
						log(LogStatus.ERROR,"Create Firm Popup is not closed", YesNo.Yes);
						sa.assertTrue(false,"Create Firm Popup is not closed");
					}
				} else {
					log(LogStatus.ERROR,"Cancel Button from Create Option is not visible", YesNo.Yes);
					sa.assertTrue(false,"Cancel Button from Create Option is not visible");
				}
				
			} else {
				log(LogStatus.ERROR, "Not able to Click on " + PageLabel.Firm.toString(), YesNo.Yes);
				sa.assertTrue(false,"Not able to Click on " + PageLabel.Firm.toString());
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
		}
	  } else {
			log(LogStatus.ERROR, "Not Able to Click on "+ TabName.InstituitonsTab, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+ TabName.InstituitonsTab);
		}
		ThreadSleep(2000);
		switchToDefaultContent(driver);
		sa.assertAll();
		lp.CRMlogout();
	}
	
@Parameters({ "projectName"})
@Test
	public void AQTc004_CreateAccountFromQuickLinks(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		
		String[][] accounts = { { AQFirm1, AQRecordType1 },{ AQFirm2, AQRecordType2 },
				{ AQFirm3, AQRecordType3 },{ AQFirm4, AQRecordType4 },{ AQFirm41, AQRecordType41 },
				{ AQFirm42, AQRecordType42 },{ AQFirm43, AQRecordType43 }};
	for (String[] account : accounts) {
		try {
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(clickUsingJavaScript(driver, npbl.getFirmLinkOnCreateOption(5),"Firm Link on Create Option", action.BOOLEAN)) {
				log(LogStatus.INFO, "Able to Click on " + PageLabel.Firm.toString(), YesNo.No);
					if (ip.createInstitutionFromQuickLinks(projectName, environment, mode, account[0],account[1])) {
						log(LogStatus.INFO, "successfully Created Account/Entity : " + account[0]+ "of record type : " + account[1], YesNo.No);
					} else {
						sa.assertTrue(false, "Not Able to Create Account/Entity : " + account[0]+ " of record type : " + account[1]);
						log(LogStatus.SKIP, "Not Able to Create Account/Entity : " + account[0]+ " of record type : " + account[1], YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR,"Not able to Click on " + PageLabel.Firm.toString(), YesNo.Yes);
					sa.assertTrue(false,"Not able to Click on " + PageLabel.Firm.toString());
				}
			} else {
				log(LogStatus.ERROR,"Not Able to Click on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
			}
		} catch (Exception e) {
			log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
		}
	}
	ThreadSleep(2000);
	switchToDefaultContent(driver);
	sa.assertAll();
	lp.CRMlogout();
 }

@Parameters({ "projectName" })
@Test
	public void AQTc005_VerifyCreatedAccountName(String projectName) {
    LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
    FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
    InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		String[] accounts = {AQFirm1,AQFirm2,AQFirm3,AQFirm4,AQFirm41,AQFirm42,AQFirm43};
		String[] RecordTypes = {AQRecordType1,AQRecordType2,AQRecordType3,AQRecordType4,AQRecordType41,AQRecordType42,AQRecordType43};
		  
		for (String account : accounts) {
	   if (fp.clickOnTab(environment, mode, TabName.InstituitonsTab)) {
	       log(LogStatus.INFO, "Click on Tab : " + TabName.InstituitonsTab, YesNo.No);
	      if (fp.clickOnAlreadyCreatedItem(projectName, TabName.InstituitonsTab,account.replace("  ", "").replace("\"", ""), 10)) {
	          log(LogStatus.INFO, "Able to open created Account : " + account, YesNo.Yes);
	       } else {
	          sa.assertTrue(false, "Not Able to open created Account : " + account);
	          log(LogStatus.SKIP, "Not Able to open created Account: " + account, YesNo.Yes);
	      }
	   } else {
	       log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
	       sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	   }
	  }
	   refresh(driver);
	   
	  for(String RecordType : RecordTypes) {
	   if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(clickUsingJavaScript(driver, npbl.getFirmLinkOnCreateOption(5),"Firm Link on Create Option", action.BOOLEAN)) {
				log(LogStatus.INFO, "Able to Click on " + PageLabel.Firm.toString(), YesNo.No);
				ThreadSleep(5000);
				if(clickUsingJavaScript(driver, npbl.getFirmRecordTypeForCreateOption(5),"Save Button For Quick Firm", action.BOOLEAN)) {
					click(driver, npbl.getRecordTypeOnQuickFirm(RecordType, 5),"Select Record Type :" + RecordType, action.BOOLEAN);
					log(LogStatus.INFO, "Firm Record Type from Create Option is visible", YesNo.No);
				} else {
					log(LogStatus.ERROR,"Firm Record Type from Create Option is not visible", YesNo.Yes);
					sa.assertTrue(false,"Firm Record Type from Create Option is not visible");
				}
				
				if(npbl.getSaveButtonForCreateOption(5) != null) {
					log(LogStatus.INFO, "Save Button from Create Option is visible", YesNo.No);
					click(driver, npbl.getSaveButtonForCreateOption(5),"Save Button For Quick Firm", action.BOOLEAN);
					if(npbl.getErrorOnQuickFirm(5) != null) {
						log(LogStatus.INFO,"Error Message is visible", YesNo.Yes);
					} else {
						log(LogStatus.ERROR,"Error Message is not visible", YesNo.Yes);
						sa.assertTrue(false,"Error Message is not visible");
					}
					if(npbl.getErrorOnQuickFirmName(5) != null) {
						log(LogStatus.INFO,"Error Message is visible", YesNo.Yes);
					} else {
						log(LogStatus.ERROR,"Error Message is not visible", YesNo.Yes);
						sa.assertTrue(false,"Error Message is not visible");
					}
				} else {
					log(LogStatus.ERROR,"Save Button from Create Option is not visible", YesNo.Yes);
					sa.assertTrue(false,"Save Button from Create Option is not visible");
				}
			} else {
				log(LogStatus.ERROR, "Not able to Click on " + PageLabel.Firm.toString(), YesNo.Yes);
				sa.assertTrue(false,"Not able to Click on " + PageLabel.Firm.toString());
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
		}
	   		refresh(driver);
	  }
	  
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				if(clickUsingJavaScript(driver, npbl.getFirmLinkOnCreateOption(5),"Firm Link on Create Option", action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to Click on " + PageLabel.Firm.toString(), YesNo.No);
						if (ip.createInstitutionFromQuickLinks(projectName, environment, mode, AQFirm5,AQRecordType5)) {
							log(LogStatus.INFO, "successfully Created Account/Entity : " + AQFirm5 + "of record type : " + AQRecordType5, YesNo.No);
						} else {
							sa.assertTrue(false, "Not Able to Create Account/Entity : " + AQFirm5 + " of record type : " + AQRecordType5);
							log(LogStatus.SKIP, "Not Able to Create Account/Entity : " + AQFirm5 + " of record type : " + AQRecordType5, YesNo.Yes);
						}
					} else {
						log(LogStatus.ERROR,"Not able to Click on " + PageLabel.Firm.toString(), YesNo.Yes);
						sa.assertTrue(false,"Not able to Click on " + PageLabel.Firm.toString());
					}
				} else {
					log(LogStatus.ERROR,"Not Able to Click on "+navigationMenuName, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
				}
		sa.assertAll();
		lp.CRMlogout();
	}

@Parameters({ "projectName"})
@Test
	public void AQTc006_DeleteFirmAndVerifyImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		boolean flag = false;
			if (ip.clickOnTab(projectName, mode,TabName.InstituitonsTab)) {
				if (ip.clickOnAlreadyCreatedItem(projectName, TabName.InstituitonsTab, AQFirm1, 20)) {
					WebElement ele=null;
					cp.clickOnShowMoreDropdownOnly(environment, mode,PageName.Object1Page);
					log(LogStatus.INFO,"Able to Click on Show more Icon : "+TabName.InstituitonsTab+" For : "+AQFirm1,YesNo.No);
					ThreadSleep(500);
					ele = cp.actionDropdownElement(projectName, ShowMoreActionDropDownList.Delete, 15);
					if (ele==null) {
						ele =cp.getDeleteButton(30);
					}
					ThreadSleep(5000);
					if (clickUsingJavaScript(driver, ele, "delete", action.BOOLEAN)) {
						ThreadSleep(2000);
						if(clickUsingJavaScript(driver,cp.getDeleteButtonPopUp(projectName, 10), "delete", action.BOOLEAN)) {
							log(LogStatus.INFO,"Able to Click on delete button on delete popup : "+TabName.InstituitonsTab+" For : "+AQFirm1,YesNo.No); 
						}else {
							sa.assertTrue(false,"Not Able to Select delete button for "+AQFirm1+" For Label "+PageLabel.Firm_Name);
							log(LogStatus.SKIP,"Not Able to Select delete button for "+AQFirm1+" For Label "+PageLabel.Firm_Name,YesNo.Yes);
		
						}
					}else {
						sa.assertTrue(false,"Not Able to Select delete button for "+AQFirm1+" For Label "+PageLabel.Firm_Name);
						log(LogStatus.SKIP,"Not Able to Select delete button for "+AQFirm1+" For Label "+PageLabel.Firm_Name,YesNo.Yes);
		
					}
				}else {
					sa.assertTrue(false,"Not Able to find Firm "+AQFirm1+" For Label "+PageLabel.Firm_Name);
					log(LogStatus.SKIP,"Not Able to find Firm "+AQFirm1+" For Label "+PageLabel.Firm_Name,YesNo.Yes);
		
				}
			}else {
				sa.assertTrue(false,"not able to click on Firm tab");
				log(LogStatus.SKIP,"not able to click on Firm tab",YesNo.Yes);
			}
		
		refresh(driver);
			if (lp.restoreValueFromRecycleBin(projectName, AQFirm1)) {
				log(LogStatus.INFO, "Able to restore item from Recycle Bin " + AQFirm1, YesNo.Yes);
				flag = true;
			} else {
				sa.assertTrue(false, "Not Able to restore item from Recycle Bin " + AQFirm1);
				log(LogStatus.SKIP, "Not Able to restore item from Recycle Bin " + AQFirm1, YesNo.Yes);
			}
		refresh(driver);
		
		 if (fp.clickOnTab(environment, mode, TabName.InstituitonsTab)) {
		       log(LogStatus.INFO, "Click on Tab : " + TabName.InstituitonsTab, YesNo.No);
		      if (fp.clickOnAlreadyCreatedItem(projectName, TabName.InstituitonsTab,AQFirm1.replace("  ", "").replace("\"", ""), 10)) {
		          log(LogStatus.INFO, "Able to open created Account : " + AQFirm1, YesNo.Yes);
		       } else {
		          sa.assertTrue(false, "Not Able to open created Account : " + AQFirm1);
		          log(LogStatus.SKIP, "Not Able to open created Account: " + AQFirm1, YesNo.Yes);
		      }
		   } else {
		       log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		       sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		   }
		sa.assertAll();
		lp.CRMlogout();
	}

@Parameters({ "projectName"})
@Test
	public void AQTc007_CreateAccountFromQuickLinks(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		
		String[][] accounts = { { AQFirm6, AQRecordType6 },{ AQFirm7, AQRecordType7 } };
	for (String[] account : accounts) {
		try {
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(clickUsingJavaScript(driver, npbl.getFirmLinkOnCreateOption(5),"Firm Link on Create Option", action.BOOLEAN)) {
				log(LogStatus.INFO, "Able to Click on " + PageLabel.Firm.toString(), YesNo.No);
					if (ip.createInstitutionFromQuickLinks(projectName, environment, mode, account[0],account[1])) {
						log(LogStatus.INFO, "successfully Created Account/Entity : " + account[0]+ "of record type : " + account[1], YesNo.No);
					} else {
						sa.assertTrue(false, "Not Able to Create Account/Entity : " + account[0]+ " of record type : " + account[1]);
						log(LogStatus.SKIP, "Not Able to Create Account/Entity : " + account[0]+ " of record type : " + account[1], YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR,"Not able to Click on " + PageLabel.Firm.toString(), YesNo.Yes);
					sa.assertTrue(false,"Not able to Click on " + PageLabel.Firm.toString());
				}
			} else {
				log(LogStatus.ERROR,"Not Able to Click on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
			}
		} catch (Exception e) {
			log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
		}
	}
	ThreadSleep(2000);
	switchToDefaultContent(driver);
	sa.assertAll();
	lp.CRMlogout();
 }

@Parameters({ "projectName"})
@Test
	public void AQTc008_RemoveObjectPermissionForAccount_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword);
	
	String profileForSelection = "PE Standard User";
	String parentID=null, objects[] ={TabName.Account.toString()}, permissionTypes[] = {PermissionType.Read.toString(),PermissionType.Create.toString(),PermissionType.Edit.toString(),PermissionType.Delete.toString()}, status = "Not Checked";
	boolean flag = false;
	
		switchToDefaultContent(driver);
		home.notificationPopUpClose();
			if (home.clickOnSetUpLink()) {
				parentID = switchOnWindow(driver);
			if (parentID!=null) {
				ThreadSleep(2000);
					if(sp.giveAndRemoveObjectPermissionFromProfiles(profileForSelection,objects,permissionTypes,status)) {
						log(LogStatus.PASS,"Remove Permission from Contact Object", YesNo.No);
						flag=true;
					}else {
						log(LogStatus.ERROR,"Not able to remove permission from" + objects + "Object", YesNo.Yes);
						sa.assertTrue(false, "Not able to remove permission from" + objects + "Object");
					}
				driver.close();
				driver.switchTo().window(parentID);
				}else {
					log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
					sa.assertTrue(false, "setup link is not clickable");
				}
			} else {
				log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
				sa.assertTrue(false, "setup link is not clickable");
			}
		switchToDefaultContent(driver);
		ThreadSleep(5000);
		lp.CRMlogout();
		ThreadSleep(5000);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		switchToDefaultContent(driver);
		home.notificationPopUpClose();
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				clickUsingJavaScript(driver, npbl.getFirmLinkOnCreateOption(5),"Firm Link on Create Option", action.BOOLEAN);
				ThreadSleep(5000);
				if(isDisplayed(driver, npbl.getFirmNameLabelForCreateOption(10), "Visibility", 10, "Firm Name Label") == null) {
					log(LogStatus.INFO, PageLabel.Firm.toString() + "is not functional", YesNo.No);
				} else {
					log(LogStatus.ERROR, PageLabel.Firm.toString() + "is functional", YesNo.Yes);
					sa.assertTrue(false,PageLabel.Firm.toString() + "is functional");
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
			}
	switchToDefaultContent(driver);
	ThreadSleep(5000);
	sa.assertAll();
	lp.CRMlogout();		
	}

@Parameters({ "projectName"})
@Test
	public void AQTc009_AddObjectPermissionForAccount_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword);
	
	String profileForSelection = "PE Standard User";
	String parentID=null, objects[] ={TabName.Account.toString()}, permissionTypes[] = {PermissionType.Read.toString(),PermissionType.Create.toString(),PermissionType.Update.toString(),PermissionType.Delete.toString()}, status = "Checked";
	boolean flag = false;
	
		switchToDefaultContent(driver);
		home.notificationPopUpClose();
			if (home.clickOnSetUpLink()) {
				parentID = switchOnWindow(driver);
			if (parentID!=null) {
				ThreadSleep(2000);
					if(sp.giveAndRemoveObjectPermissionFromProfiles(profileForSelection,objects,permissionTypes,status)) {
						log(LogStatus.PASS,"Remove Permission from Contact Object", YesNo.No);
						flag=true;
					}else {
						log(LogStatus.ERROR,"Not able to remove permission from" + objects + "Object", YesNo.Yes);
						sa.assertTrue(false, "Not able to remove permission from" + objects + "Object");
					}
				driver.close();
				driver.switchTo().window(parentID);
				}else {
					log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
					sa.assertTrue(false, "setup link is not clickable");
				}
			} else {
				log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
				sa.assertTrue(false, "setup link is not clickable");
			}
		
		switchToDefaultContent(driver);
		ThreadSleep(5000);
		lp.CRMlogout();
		ThreadSleep(5000);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		switchToDefaultContent(driver);
		home.notificationPopUpClose();
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				clickUsingJavaScript(driver, npbl.getFirmLinkOnCreateOption(5),"Firm Link on Create Option", action.BOOLEAN);
				ThreadSleep(5000);
				if(isDisplayed(driver, npbl.getFirmNameLabelForCreateOption(10), "Visibility", 10, "Firm Name Label") != null) {
					log(LogStatus.INFO, PageLabel.Firm.toString() + "is functional", YesNo.No);
				} else {
					log(LogStatus.ERROR, PageLabel.Firm.toString() + "is not functional", YesNo.Yes);
					sa.assertTrue(false,PageLabel.Firm.toString() + "is not functional");
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
			}
	switchToDefaultContent(driver);
	ThreadSleep(5000);
	sa.assertAll();
	lp.CRMlogout();		
	}

@Parameters({ "projectName" })
@Test
	public void AQTc010_UpdateFieldName_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	ContactsPageBusinessLayer ip = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword);
	String parentWindow = "";
	boolean flag1 = false;
	String tabNames1 = TabName.Accounts.toString();
	String[] labelsWithValues1 = {"Account Name<break>Account Name Upd","City<break>City"};
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
			if (setup.searchStandardOrCustomObject(environment, mode, object.Rename_Tabs_and_Labels.toString())) {
				log(LogStatus.INFO, "click on Object : " + object.Rename_Tabs_and_Labels, YesNo.No);
				ThreadSleep(2000);
	
				if (setup.renameLabelsOfFields(driver, tabNames1, labelsWithValues1, 10)) {
					flag1 = true;
					log(LogStatus.PASS, labelsWithValues1[0] + " is updated as " +labelsWithValues1[1] , YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "Not able to search/click on " + object.Rename_Tabs_and_Labels, YesNo.Yes);
				sa.assertTrue(false, "Not able to search/click on " + object.Rename_Tabs_and_Labels);
			}	
			driver.close();
		} 
		ThreadSleep(2000);
		driver.switchTo().window(parentWindow);
		sa.assertAll();
		lp.CRMlogout();
		ThreadSleep(5000);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		switchToDefaultContent(driver);
		home.notificationPopUpClose();
		if (ip.clickOnTab(projectName, TabName.InstituitonsTab)) {
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				if(clickUsingJavaScript(driver, npbl.getFirmLinkOnCreateOption(5),"Firm Link on Create Option", action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to Click on " + PageLabel.Firm.toString(), YesNo.No);
					ThreadSleep(2000);
					String Value = npbl.getFirmNameLabelForCreateOption(5).getText();
					if(Value.contains(PageLabel.Account_Name_Upd.toString().replaceAll("_", " "))) {
						log(LogStatus.INFO, "Firm Name from Create Option is updated as " + PageLabel.Account_Name_Upd.toString(), YesNo.No);
					} else {
						log(LogStatus.ERROR, "Firm Name from Create Option is not updated as " + PageLabel.Account_Name_Upd.toString(), YesNo.Yes);
						sa.assertTrue(false,"Firm Name from Create Option is not updated as " + PageLabel.Account_Name_Upd.toString());
					}
				} else {
					log(LogStatus.ERROR, "Not able to Click on " + PageLabel.Firm.toString(), YesNo.Yes);
					sa.assertTrue(false,"Not able to Click on " + PageLabel.Firm.toString());
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
			}
		  } else {
				log(LogStatus.ERROR, "Not Able to Click on "+ TabName.InstituitonsTab, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+ TabName.InstituitonsTab);
			}
			ThreadSleep(2000);
			switchToDefaultContent(driver);
			sa.assertAll();
			lp.CRMlogout();
		 }

@Parameters({ "projectName" })
@Test
	public void AQTc011_RevertUpdateFieldName_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	ContactsPageBusinessLayer ip = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword);
	String parentWindow = "";
	boolean flag1 = false;
	String tabNames1 = TabName.Accounts.toString();
	String[] labelsWithValues1 = {"Account Name<break>Legal Name","City<break>City"};
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
			if (setup.searchStandardOrCustomObject(environment, mode, object.Rename_Tabs_and_Labels.toString())) {
				log(LogStatus.INFO, "click on Object : " + object.Rename_Tabs_and_Labels, YesNo.No);
				ThreadSleep(2000);
	
				if (setup.renameLabelsOfFields(driver, tabNames1, labelsWithValues1, 10)) {
					flag1 = true;
					log(LogStatus.PASS, labelsWithValues1[0] + " is updated as " +labelsWithValues1[1] , YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "Not able to search/click on " + object.Rename_Tabs_and_Labels, YesNo.Yes);
				sa.assertTrue(false, "Not able to search/click on " + object.Rename_Tabs_and_Labels);
			}	
			driver.close();
		} 
		ThreadSleep(2000);
		driver.switchTo().window(parentWindow);
		sa.assertAll();
		lp.CRMlogout();
		ThreadSleep(5000);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		switchToDefaultContent(driver);
		home.notificationPopUpClose();
		if (ip.clickOnTab(projectName, TabName.InstituitonsTab)) {
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				if(clickUsingJavaScript(driver, npbl.getFirmLinkOnCreateOption(5),"Firm Link on Create Option", action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to Click on " + PageLabel.Firm.toString(), YesNo.No);
					ThreadSleep(2000);
					if(npbl.getFirmNameLabelForCreateOption(5).getText().contains(PageLabel.Legal_Name.toString().replaceAll("_", " "))) {
						log(LogStatus.INFO, "Firm Name from Create Option is updated as " + PageLabel.Legal_Name.toString(), YesNo.No);
					} else {
						log(LogStatus.ERROR, "Firm Name from Create Option is not updated as " + PageLabel.Legal_Name.toString(), YesNo.Yes);
						sa.assertTrue(false,"Firm Name from Create Option is not updated as " + PageLabel.Legal_Name.toString());
					}
				} else {
					log(LogStatus.ERROR, "Not able to Click on " + PageLabel.Firm.toString(), YesNo.Yes);
					sa.assertTrue(false,"Not able to Click on " + PageLabel.Firm.toString());
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
			}
		  } else {
				log(LogStatus.ERROR, "Not Able to Click on "+ TabName.InstituitonsTab, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+ TabName.InstituitonsTab);
			}
			ThreadSleep(2000);
			switchToDefaultContent(driver);
			sa.assertAll();
			lp.CRMlogout();
		 }

@Parameters({ "projectName" })
@Test
	public void AQTc012_UpdateDefaultRecordType_VerifyImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
		ContactsPageBusinessLayer ip = new ContactsPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);

		boolean flag = false;
		lp.CRMLogin(superAdminUserName, adminPassword);
		if (home.clickOnSetUpLink()) {
			flag = false;
			String parentID = switchOnWindow(driver);
			if (parentID != null) {
	
				if (sp.searchStandardOrCustomObject(environment, mode, object.Profiles.toString())) {
					log(LogStatus.INFO, "Profile has been open", YesNo.Yes);
	
					if (sp.defaultRecordTypeSelect("PE Standard User", "Account", "Advisor")) {
						log(LogStatus.INFO,
								"-----The record type of Account has been selected to: " + "Advisor" + "-----",
								YesNo.No);
						flag = true;
	
					} else {
						log(LogStatus.ERROR,
								"-----The record type of Account has not been selected to: " + "Advisor" + "-----",
								YesNo.No);
						sa.assertTrue(false,
								"-----The record type of Account has not been selected to: " + "Advisor" + "-----");
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Search the " + object.Profiles + " object", YesNo.Yes);
					sa.assertTrue(false, "Not Able to Search the Object" + object.Profiles + " object");
				}
	
				driver.close();
				driver.switchTo().window(parentID);
				switchToDefaultContent(driver);
			} else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		} else {
			log(LogStatus.ERROR, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}
	
		lp.CRMlogout();
		ThreadSleep(5000);
		lp.CRMLogin(crmUser1EmailID, adminPassword);

			if (ip.clickOnTab(projectName, TabName.InstituitonsTab)) {
				if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
					log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
					if(clickUsingJavaScript(driver, npbl.getFirmLinkOnCreateOption(5),"Firm Link on Create Option", action.BOOLEAN)) {
						log(LogStatus.INFO, "Able to Click on " + PageLabel.Firm.toString(), YesNo.No);
						ThreadSleep(2000);
						if(npbl.getFirmRecordTypeForCreateOption(5).getAttribute("data-value").equalsIgnoreCase(object.Advisor.toString())) {
							log(LogStatus.INFO, "Default Record Type is updated as " + object.Advisor.toString(), YesNo.No);
						} else {
							log(LogStatus.ERROR,"Default Record Type is not updated as " + object.Advisor.toString(), YesNo.Yes);
							sa.assertTrue(false,"Default Record Type is not updated as " + object.Advisor.toString());
						}
						
					} else {
						log(LogStatus.ERROR, "Not able to Click on " + PageLabel.Firm.toString(), YesNo.Yes);
						sa.assertTrue(false,"Not able to Click on " + PageLabel.Firm.toString());
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
				}
			  } else {
					log(LogStatus.ERROR, "Not Able to Click on "+ TabName.InstituitonsTab, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+ TabName.InstituitonsTab);
				}
		CommonLib.ThreadSleep(3000);
		sa.assertAll();
		lp.CRMlogout();
		
}

@Parameters({ "projectName"})
@Test
	public void AQTc013_CreateNewRecordType(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		ResearchPageBusinessLayer rp=new ResearchPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		
		String contactRecordTypeList = AQRecordType8;
		String contactRecordTypeArray[] = contactRecordTypeList.split(breakSP,-1);
		
		String[][][] contactrecordType = {
				{ { recordTypeLabel.Record_Type_Label.toString(), contactRecordTypeArray[0] },
						{ recordTypeLabel.Description.toString(), contactRecordTypeArray[0] + bp.recordTypeDescription },
						{ recordTypeLabel.Active.toString(), "" } } };
		
		String[] profileForSelection = { "PE Standard User"};
		boolean isMakeAvailable = false;
		boolean isMakeDefault = false;
		boolean flag = false;
		String parentID=null;
		
		for (int i = 0; i < contactRecordTypeArray.length; i++) {
			home.notificationPopUpClose();
			if (home.clickOnSetUpLink()) {
				flag = false;
				parentID = switchOnWindow(driver);
				if (parentID != null) {
					if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(), object.Institution.toString())) {
						if (sp.clickOnObjectFeature("", Mode.Lightning.toString(), object.Institution,
								ObjectFeatureName.recordTypes)) {
								if (sp.listOfRecordTypes().contains(contactrecordType[i][0][1])) {
									log(LogStatus.INFO, "Record Type: " + contactrecordType[i][0][1]
											+ " is already created, So not going to Create", YesNo.No);
									flag = true;
								} else {
									flag = sp.createRecordTypeForObject(projectName, contactrecordType[i], isMakeAvailable,
											profileForSelection, isMakeDefault, PageLabel.Advisor.toString().replace("_", " "), 10);
								}
							if (flag) {
								log(LogStatus.INFO, "Created Record Type : " + contactRecordTypeArray[i], YesNo.No);
							} else {
								log(LogStatus.ERROR, "Not Able to Create Record Type : " + contactRecordTypeArray[i],
										YesNo.Yes);
								sa.assertTrue(false, "Not Able to Create Record Type : " + contactRecordTypeArray[i]);
							}
		
						} else {
							log(LogStatus.ERROR,
									"object feature " + ObjectFeatureName.recordTypes + " is not clickable", YesNo.Yes);
							sa.assertTrue(false,
									"object feature " + ObjectFeatureName.recordTypes + " is not clickable");
						}
					} else {
						log(LogStatus.ERROR, object.Contact + " object could not be found in object manager", YesNo.Yes);
						sa.assertTrue(false, object.Contact + " object could not be found in object manager");
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
		
		for(int k=0; k<profileForSelection.length; k++) {
			home.notificationPopUpClose();
			if (home.clickOnSetUpLink()) {
				parentID = switchOnWindow(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject(environment, mode, object.Profiles.toString())) {
					log(LogStatus.INFO, "click on Object : " + object.Profiles, YesNo.No);
					ThreadSleep(2000);
					switchToDefaultContent(driver);
					switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
					ThreadSleep(2000);
					if (clickUsingJavaScript(driver, rp.getProfileSelected(profileForSelection[k],10), profileForSelection[k].toString(), action.BOOLEAN)) {
						log(LogStatus.INFO, "able to click on " + profileForSelection[k], YesNo.No);
						ThreadSleep(2000);
						switchToDefaultContent(driver);
						ThreadSleep(2000);
						switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
						ThreadSleep(2000);
						if (click(driver, rp.getEditButtonForRecordTypes(TabName.Account.toString(), 10), "Edit Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "able to click on edit button for record type settiing", YesNo.No);
							switchToDefaultContent(driver);
							ThreadSleep(5000);
							switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
							ThreadSleep(2000);
							if (selectVisibleTextFromDropDown(driver, sp.getavailableRecordType(10),
									"Selected Tab List", RecordType.New_Demo_Company.toString().replace("_", " "))) {
								appLog.info(object.Accounts.toString() + " is selected successfully in Available tabs");
								if (click(driver, sp.getAddBtn(10), "Custom Tab Add Button",
										action.SCROLLANDBOOLEAN)) {
									appLog.error("clicked on Add button");
								} else {
									appLog.error("Not able to click on Add button so cannot add custom tabs");
									sa.assertTrue(false,RecordType.New_Demo_Company.toString().replace("_", " ") + " record type is not Selected list Tab for " + profileForSelection[k]);
								}
							} else {
								appLog.error(RecordType.New_Demo_Company.toString().replace("_", " ") + " record type is not Selected list Tab.");
							}
							if (click(driver, sp.getCreateUserSaveBtn_Lighting(10), "Save Button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on save button for record type settiing", YesNo.No);
								ThreadSleep(2000);
							} else {
								log(LogStatus.ERROR, "not able to click on save button for record type settiing", YesNo.Yes);
								sa.assertTrue(false,"not able to click on save button for record type settiing");
		
							}
							}else {
								log(LogStatus.ERROR, "not able to click on edit button for record type settiing", YesNo.Yes);
								sa.assertTrue(false,"not able to click on edit button for record type settiing");
							}
					}else {
						log(LogStatus.ERROR, profileForSelection[k]+" profile is not clickable", YesNo.Yes);
						sa.assertTrue(false,profileForSelection[k]+" profile is not clickable");
					}
				
				} else {
					log(LogStatus.ERROR, "profiles tab is not clickable", YesNo.Yes);
					sa.assertTrue(false,"profiles tab is not clickable");
				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
				sa.assertTrue(false, "setup link is not clickable");
			}
		}else {
			log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
			sa.assertTrue(false, "setup link is not clickable");
		}
	}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		ThreadSleep(5000);
		lp.CRMLogin(crmUser1EmailID, adminPassword,appName);
		ThreadSleep(2000);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(clickUsingJavaScript(driver, npbl.getFirmLinkOnCreateOption(5),"Firm Link on Create Option", action.BOOLEAN)) {
				log(LogStatus.INFO, "Able to Click on " + PageLabel.Firm.toString(), YesNo.No);
						log(LogStatus.INFO, "Default Record Type is updated as " + object.Advisor.toString(), YesNo.No);
						ThreadSleep(2000);
						click(driver, ip.firmRecordTypeQuickLinksPopUp(10), "firmRecordTypeQuickLinksPopUp",
								action.SCROLLANDBOOLEAN);
						if(click(driver, ip.firmRecordTypeDropDownElementQuickLinksPopUp(RecordType.New_Demo_Company.toString().replace("_", " "), 5),"Select Record Type :" + RecordType.New_Demo_Company.toString().replace("_", " "), action.BOOLEAN)) {
							log(LogStatus.INFO, RecordType.New_Demo_Company.toString().replace("_", " ")+" Record Type from Create Option is visible", YesNo.No);
						} else {
							log(LogStatus.ERROR,RecordType.New_Demo_Company.toString().replace("_", " ") +" Record Type from Create Option is not visible", YesNo.Yes);
							sa.assertTrue(false,RecordType.New_Demo_Company.toString().replace("_", " ") +" Record Type from Create Option is not visible");
						}
				} else {
					log(LogStatus.ERROR,"Not able to Click on " + PageLabel.Firm.toString(), YesNo.Yes);
					sa.assertTrue(false,"Not able to Click on " + PageLabel.Firm.toString());
				}
			} else {
				log(LogStatus.ERROR,"Not Able to Click on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
			}
		switchToDefaultContent(driver);
		ThreadSleep(5000);
		sa.assertAll();
		lp.CRMlogout();
	}

@Parameters({ "projectName"})
@Test
	public void AQTc014_DeleteNewRecordType(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		ResearchPageBusinessLayer rp=new ResearchPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		
		String contactRecordTypeList = AQRecordType8;
		String contactRecordTypeArray[] = contactRecordTypeList.split(breakSP,-1);
		
		String[][] RecordTypes = { { recordTypeLabel.Active.toString(), "Not Checked" }};
		
		String[] profileForSelection = { "PE Standard User"};
		boolean flag = false;
		String parentID=null;
		for(int k=0; k<profileForSelection.length; k++) {
			home.notificationPopUpClose();
			if (home.clickOnSetUpLink()) {
				parentID = switchOnWindow(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject(environment, mode, object.Profiles.toString())) {
					log(LogStatus.INFO, "click on Object : " + object.Profiles, YesNo.No);
					ThreadSleep(2000);
					switchToDefaultContent(driver);
					switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
					ThreadSleep(2000);
					if (clickUsingJavaScript(driver, rp.getProfileSelected(profileForSelection[k],10), profileForSelection[k].toString(), action.BOOLEAN)) {
						log(LogStatus.INFO, "able to click on " + profileForSelection[k], YesNo.No);
						ThreadSleep(2000);
						switchToDefaultContent(driver);
						ThreadSleep(2000);
						switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
						ThreadSleep(2000);
						if (click(driver, rp.getEditButtonForRecordTypes(TabName.Account.toString(), 10), "Edit Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "able to click on edit button for record type settiing", YesNo.No);
							switchToDefaultContent(driver);
							ThreadSleep(5000);
							switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
							ThreadSleep(2000);
							if (selectVisibleTextFromDropDown(driver, sp.getSelectedRecordType(10),
									"Selected Tab List", RecordType.New_Demo_Company.toString().replace("_", " "))) {
								appLog.info(object.Accounts.toString() + " is selected successfully in Selected tabs");
								if (click(driver, sp.getRemoveBtn(10), "Custom Tab Remove Button",
										action.SCROLLANDBOOLEAN)) {
									appLog.error("clicked on Remove button");
								} else {
									appLog.error("Not able to click on Remove button so cannot add custom tabs");
									sa.assertTrue(false,RecordType.New_Demo_Company.toString().replace("_", " ") + " record type is not Available list Tab for " + profileForSelection[k]);
								}
							} else {
								appLog.error(RecordType.New_Demo_Company.toString().replace("_", " ") + " record type is not Available list Tab.");
							}
							if (click(driver, sp.getCreateUserSaveBtn_Lighting(10), "Save Button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on save button for record type settiing", YesNo.No);
								ThreadSleep(2000);
							} else {
								log(LogStatus.ERROR, "not able to click on save button for record type settiing", YesNo.Yes);
								sa.assertTrue(false,"not able to click on save button for record type settiing");
		
							}
							}else {
								log(LogStatus.ERROR, "not able to click on edit button for record type settiing", YesNo.Yes);
								sa.assertTrue(false,"not able to click on edit button for record type settiing");
							}
					}else {
						log(LogStatus.ERROR, profileForSelection[k]+" profile is not clickable", YesNo.Yes);
						sa.assertTrue(false,profileForSelection[k]+" profile is not clickable");
					}
				
				} else {
					log(LogStatus.ERROR, "profiles tab is not clickable", YesNo.Yes);
					sa.assertTrue(false,"profiles tab is not clickable");
				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
				sa.assertTrue(false, "setup link is not clickable");
			}
		}else {
			log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
			sa.assertTrue(false, "setup link is not clickable");
		}
	}
		switchToDefaultContent(driver);
		ThreadSleep(2000);
		
			home.notificationPopUpClose();
			if (home.clickOnSetUpLink()) {
				flag = false;
				parentID = switchOnWindow(driver);
				if (parentID != null) {
					if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Firm.toString())) {
						if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Firm,
								ObjectFeatureName.recordTypes)) {
							if (sp.clickOnAlreadyCreatedLayout(contactRecordTypeArray[0])) {
								if (sp.editRecordTypeForObject(projectName, RecordTypes, 10)) {
									log(LogStatus.ERROR,contactRecordTypeArray[0]+" has been updated ",YesNo.Yes);	
								}else {
									log(LogStatus.ERROR,contactRecordTypeArray[0]+" not updated ",YesNo.Yes);
									sa.assertTrue(false, contactRecordTypeArray[0]+" not updated ");
								}
							
							}else {
								log(LogStatus.ERROR, contactRecordTypeArray[0]+" is not clickable", YesNo.Yes);
								sa.assertTrue(false, contactRecordTypeArray[0]+" is not clickable");
							}
					
						}else {
							log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
						}
					}else {
						log(LogStatus.ERROR, "Target object could not be found in object manager", YesNo.Yes);
						sa.assertTrue(false, "Target object could not be found in object manager");
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
		ThreadSleep(2000);
		switchToDefaultContent(driver);
		lp.CRMlogout();
		ThreadSleep(3000);
		lp.CRMLogin(crmUser1EmailID, adminPassword,appName);
		ThreadSleep(2000);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(clickUsingJavaScript(driver, npbl.getFirmLinkOnCreateOption(5),"Firm Link on Create Option", action.BOOLEAN)) {
				log(LogStatus.INFO, "Able to Click on " + PageLabel.Firm.toString(), YesNo.No);
					if(npbl.getRecordTypeOnQuickFirm(RecordType.New_Demo_Company.toString().replace("_", " "), 5) == null) {
						log(LogStatus.INFO, RecordType.New_Demo_Company.toString().replace("_", " ")+" Record Type from Create Option is not visible", YesNo.No);
					} else {
						log(LogStatus.ERROR,RecordType.New_Demo_Company.toString().replace("_", " ") +" Record Type from Create Option is visible", YesNo.Yes);
						sa.assertTrue(false,RecordType.New_Demo_Company.toString().replace("_", " ") +" Record Type from Create Option is visible");
					}
				} else {
					log(LogStatus.ERROR,"Not able to Click on " + PageLabel.Firm.toString(), YesNo.Yes);
					sa.assertTrue(false,"Not able to Click on " + PageLabel.Firm.toString());
				}
			} else {
				log(LogStatus.ERROR,"Not Able to Click on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
			}
		switchToDefaultContent(driver);
		ThreadSleep(5000);
		sa.assertAll();
		lp.CRMlogout();
	}

@Parameters({ "projectName"})
@Test
	public void AQTc015_RemoveRecordTypeFromProfiles(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		ResearchPageBusinessLayer rp=new ResearchPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		String master= "--Master--";
		String[] profileForSelection = { "PE Standard User"};
		String parentID=null;
		for(int k=0; k<profileForSelection.length; k++) {
			home.notificationPopUpClose();
			if (home.clickOnSetUpLink()) {
				parentID = switchOnWindow(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject(environment, mode, object.Profiles.toString())) {
					log(LogStatus.INFO, "click on Object : " + object.Profiles, YesNo.No);
					ThreadSleep(2000);
					switchToDefaultContent(driver);
					switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
					ThreadSleep(2000);
					if (clickUsingJavaScript(driver, rp.getProfileSelected(profileForSelection[k],10), profileForSelection[k].toString(), action.BOOLEAN)) {
						log(LogStatus.INFO, "able to click on " + profileForSelection[k], YesNo.No);
						ThreadSleep(2000);
						switchToDefaultContent(driver);
						ThreadSleep(2000);
						switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
						ThreadSleep(2000);
						if (click(driver, rp.getEditButtonForRecordTypes(TabName.Account.toString(), 10), "Edit Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "able to click on edit button for record type settiing", YesNo.No);
							switchToDefaultContent(driver);
							ThreadSleep(5000);
							switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
							ThreadSleep(2000);
							if (selectVisibleTextFromDropDown(driver, sp.getSelectedRecordType(10),
									"Selected Tab List", object.Advisor.toString())) {
								appLog.info(object.Accounts.toString() + " is selected successfully in Selected tabs");
								if (click(driver, sp.getRemoveBtn(10), "Custom Tab Remove Button",
										action.SCROLLANDBOOLEAN)) {
									appLog.error("clicked on Remove button");
								} else {
									appLog.error("Not able to click on Remove button so cannot add custom tabs");
									sa.assertTrue(false,object.Advisor.toString().replace("_", " ") + " record type is not Available list Tab for " + profileForSelection[k]);
								}
							} else {
								appLog.error(object.Advisor.toString()+ " record type is not Available list Tab.");
							}
							
							if (selectVisibleTextFromDropDown(driver, sp.getavailableRecordType(10),
									"Available Tab List", master)) {
								appLog.info(object.Advisor.toString() + " is selected successfully in Available tabs");
								if (click(driver, sp.getAddBtn(10), "Custom Tab Add Button",
										action.SCROLLANDBOOLEAN)) {
									appLog.error("clicked on add button");
								} else {
									appLog.error("Not able to click on add button so cannot add custom tabs");
								}
							} else {
								appLog.error(object.Advisor.toString() + " record type is not in Available list Tab.");
							}	
							
							if (click(driver, sp.getCreateUserSaveBtn_Lighting(10), "Save Button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on save button for record type settiing", YesNo.No);
								ThreadSleep(2000);
							} else {
								log(LogStatus.ERROR, "not able to click on save button for record type settiing", YesNo.Yes);
								sa.assertTrue(false,"not able to click on save button for record type settiing");
		
							}
							}else {
								log(LogStatus.ERROR, "not able to click on edit button for record type settiing", YesNo.Yes);
								sa.assertTrue(false,"not able to click on edit button for record type settiing");
							}
					}else {
						log(LogStatus.ERROR, profileForSelection[k]+" profile is not clickable", YesNo.Yes);
						sa.assertTrue(false,profileForSelection[k]+" profile is not clickable");
					}
				
				} else {
					log(LogStatus.ERROR, "profiles tab is not clickable", YesNo.Yes);
					sa.assertTrue(false,"profiles tab is not clickable");
				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
				sa.assertTrue(false, "setup link is not clickable");
			}
		}else {
			log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
			sa.assertTrue(false, "setup link is not clickable");
		}
	}
		ThreadSleep(2000);
		switchToDefaultContent(driver);
		lp.CRMlogout();
		ThreadSleep(3000);
		lp.CRMLogin(crmUser1EmailID, adminPassword,appName);
		ThreadSleep(2000);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(clickUsingJavaScript(driver, npbl.getFirmLinkOnCreateOption(5),"Firm Link on Create Option", action.BOOLEAN)) {
				log(LogStatus.INFO, "Able to Click on " + PageLabel.Firm.toString(), YesNo.No);
					if(npbl.getRecordTypeOnQuickFirm(object.Advisor.toString().replace("_", " "), 5) == null) {
						log(LogStatus.INFO, object.Advisor.toString().replace("_", " ")+" Record Type from Create Option is not visible", YesNo.No);
					} else {
						log(LogStatus.ERROR,object.Advisor.toString().replace("_", " ") +" Record Type from Create Option is visible", YesNo.Yes);
						sa.assertTrue(false,object.Advisor.toString().replace("_", " ") +" Record Type from Create Option is visible");
					}
				} else {
					log(LogStatus.ERROR,"Not able to Click on " + PageLabel.Firm.toString(), YesNo.Yes);
					sa.assertTrue(false,"Not able to Click on " + PageLabel.Firm.toString());
				}
			} else {
				log(LogStatus.ERROR,"Not Able to Click on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
			}
		switchToDefaultContent(driver);
		ThreadSleep(5000);
		sa.assertAll();
		lp.CRMlogout();
	}

@Parameters({ "projectName"})
@Test
	public void AQTc016_AddRecordTypeFromProfiles(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		ResearchPageBusinessLayer rp=new ResearchPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		String recordTypes = "Account";
		String master= "Advisor";
		String[] profileForSelection = { "PE Standard User"};
		String parentID=null;
		for(int k=0; k<profileForSelection.length; k++) {
			home.notificationPopUpClose();
			if (home.clickOnSetUpLink()) {
				parentID = switchOnWindow(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject(environment, mode, object.Profiles.toString())) {
					log(LogStatus.INFO, "click on Object : " + object.Profiles, YesNo.No);
					ThreadSleep(2000);
					switchToDefaultContent(driver);
					switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
					ThreadSleep(2000);
					if (clickUsingJavaScript(driver, rp.getProfileSelected(profileForSelection[k],10), profileForSelection[k].toString(), action.BOOLEAN)) {
						log(LogStatus.INFO, "able to click on " + profileForSelection[k], YesNo.No);
						ThreadSleep(2000);
						switchToDefaultContent(driver);
						ThreadSleep(2000);
						switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
						ThreadSleep(2000);
						if (click(driver, rp.getEditButtonForRecordTypes(recordTypes, 10), "Edit Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "able to click on edit button for record type settiing", YesNo.No);
							switchToDefaultContent(driver);
							ThreadSleep(5000);
							switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
							ThreadSleep(2000);
							if (selectVisibleTextFromDropDown(driver, sp.getavailableRecordType(10),
									"Selected Tab List", master)) {
								appLog.info(recordTypes + " is selected successfully in Available tabs");
								if (click(driver, sp.getAddBtn(10), "Custom Tab Add Button",
										action.SCROLLANDBOOLEAN)) {
									appLog.error("clicked on Add button");
								} else {
									appLog.error("Not able to click on Remove button so cannot add custom tabs");
								}
							} else {
								appLog.error(master + " record type is not Available list Tab.");
							}
							if (click(driver, sp.getCreateUserSaveBtn_Lighting(10), "Save Button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on save button for record type settiing", YesNo.No);
								ThreadSleep(2000);
							} else {
								log(LogStatus.ERROR, "not able to click on save button for record type settiing", YesNo.Yes);
								sa.assertTrue(false,"not able to click on save button for record type settiing");
		
							}
							}else {
								log(LogStatus.ERROR, "not able to click on edit button for record type settiing", YesNo.Yes);
								sa.assertTrue(false,"not able to click on edit button for record type settiing");
							}
					}else {
						log(LogStatus.ERROR, profileForSelection[k]+" profile is not clickable", YesNo.Yes);
						sa.assertTrue(false,profileForSelection[k]+" profile is not clickable");
					}
				} else {
					log(LogStatus.ERROR, "profiles tab is not clickable", YesNo.Yes);
					sa.assertTrue(false,"profiles tab is not clickable");
				}
				driver.close();
				driver.switchTo().window(parentID);
		}else {
			log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
			sa.assertTrue(false, "setup link is not clickable");
		}
			}else {
				log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
				sa.assertTrue(false, "setup link is not clickable");
			}
	}
		ThreadSleep(2000);
		switchToDefaultContent(driver);
		lp.CRMlogout();
		ThreadSleep(3000);
		lp.CRMLogin(crmUser1EmailID, adminPassword,appName);
		ThreadSleep(2000);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(clickUsingJavaScript(driver, npbl.getFirmLinkOnCreateOption(5),"Firm Link on Create Option", action.BOOLEAN)) {
				log(LogStatus.INFO, "Able to Click on " + PageLabel.Firm.toString(), YesNo.No);
					if(npbl.getRecordTypeOnQuickFirm(object.Advisor.toString().replace("_", " "), 5) != null) {
						log(LogStatus.INFO, object.Advisor.toString().replace("_", " ")+" Record Type from Create Option is visible", YesNo.No);
					} else {
						log(LogStatus.ERROR,object.Advisor.toString().replace("_", " ") +" Record Type from Create Option is not visible", YesNo.Yes);
						sa.assertTrue(false,object.Advisor.toString().replace("_", " ") +" Record Type from Create Option is not visible");
					}
				} else {
					log(LogStatus.ERROR,"Not able to Click on " + PageLabel.Firm.toString(), YesNo.Yes);
					sa.assertTrue(false,"Not able to Click on " + PageLabel.Firm.toString());
				}
			} else {
				log(LogStatus.ERROR,"Not Able to Click on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
			}
		switchToDefaultContent(driver);
		ThreadSleep(5000);
		sa.assertAll();
		lp.CRMlogout();
	}

@Parameters({ "projectName"})
@Test
	public void AQTc016_RenameRecordTypeFromObjectManager(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		
		String contactRecordTypeList = AQRecordType1;
		String contactRecordTypeArray[] = contactRecordTypeList.split(breakSP,-1);
		
		String[][] RecordTypes = {{recordTypeLabel.Record_Type_Label.toString(),"New " +contactRecordTypeArray[0]}};
		
		boolean flag = false;
		String parentID=null;
			home.notificationPopUpClose();
			if (home.clickOnSetUpLink()) {
				flag = false;
				parentID = switchOnWindow(driver);
				if (parentID != null) {
					if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Institution.toString())) {
						if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Institution,
								ObjectFeatureName.recordTypes)) {
							if (sp.clickOnAlreadyCreatedLayout(contactRecordTypeArray[0])) {
								if (sp.editRecordTypeForObject(projectName, RecordTypes, 10)) {
									log(LogStatus.ERROR,contactRecordTypeArray[0]+" has been updated ",YesNo.Yes);	
								}else {
									log(LogStatus.ERROR,contactRecordTypeArray[0]+" not updated ",YesNo.Yes);
									sa.assertTrue(false, contactRecordTypeArray[0]+" not updated ");
								}
							
							}else {
								log(LogStatus.ERROR, contactRecordTypeArray[0]+" is not clickable", YesNo.Yes);
								sa.assertTrue(false, contactRecordTypeArray[0]+" is not clickable");
							}
					
						}else {
							log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
						}
					}else {
						log(LogStatus.ERROR, "Target object could not be found in object manager", YesNo.Yes);
						sa.assertTrue(false, "Target object could not be found in object manager");
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
		ThreadSleep(2000);
		switchToDefaultContent(driver);
		lp.CRMlogout();
		ThreadSleep(3000);
		lp.CRMLogin(crmUser1EmailID, adminPassword,appName);
		ThreadSleep(2000);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(clickUsingJavaScript(driver, npbl.getFirmLinkOnCreateOption(5),"Firm Link on Create Option", action.BOOLEAN)) {
				log(LogStatus.INFO, "Able to Click on " + PageLabel.Firm.toString(), YesNo.No);
				ThreadSleep(2000);
				click(driver, ip.firmRecordTypeQuickLinksPopUp(10), "firmRecordTypeQuickLinksPopUp",
						action.SCROLLANDBOOLEAN);
				if(click(driver, ip.firmRecordTypeDropDownElementQuickLinksPopUp(RecordType.New_Institution.toString().replace("_", " "), 5),"Select Record Type :" + RecordType.New_Institution.toString().replace("_", " "), action.BOOLEAN)) {
						log(LogStatus.INFO, RecordType.New_Institution.toString().replace("_", " ")+" Record Type from Create Option is visible", YesNo.No);
					} else {
						log(LogStatus.ERROR,RecordType.New_Institution.toString().replace("_", " ") +" Record Type from Create Option is not visible", YesNo.Yes);
						sa.assertTrue(false,RecordType.New_Institution.toString().replace("_", " ") +" Record Type from Create Option is not visible");
					}
				} else {
					log(LogStatus.ERROR,"Not able to Click on " + PageLabel.Firm.toString(), YesNo.Yes);
					sa.assertTrue(false,"Not able to Click on " + PageLabel.Firm.toString());
				}
			} else {
				log(LogStatus.ERROR,"Not Able to Click on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
			}
		switchToDefaultContent(driver);
		ThreadSleep(5000);
		sa.assertAll();
		lp.CRMlogout();
	}

@Parameters({ "projectName"})
@Test
	public void AQTc017_UpdateRecordTypesAsInactive(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword);
	
	String[][] RecordType = { { recordTypeLabel.Active.toString(), "Not Checked" }};

		boolean flag = false;
		String parentID=null;
	
		String[] profileForSelection = { "System Administrator","PE Standard User"};
		
		for(int k=0; k<profileForSelection.length; k++) {
			home.notificationPopUpClose();
			if (home.clickOnSetUpLink()) {
				parentID = switchOnWindow(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject(environment, mode, object.Profiles.toString())) {
					log(LogStatus.INFO, "click on Object : " + object.Profiles, YesNo.No);
					ThreadSleep(2000);
					switchToDefaultContent(driver);
					switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
					ThreadSleep(2000);
					if (clickUsingJavaScript(driver, rp.getProfileSelected(profileForSelection[k],10), profileForSelection[k].toString(), action.BOOLEAN)) {
						log(LogStatus.INFO, "able to click on " + profileForSelection[k], YesNo.No);
						ThreadSleep(2000);
						switchToDefaultContent(driver);
						ThreadSleep(2000);
						switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
						ThreadSleep(2000);
						if (click(driver, rp.getEditButtonForRecordTypes(TabName.Account.toString(), 10), "Edit Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "able to click on edit button for record type settiing", YesNo.No);
							switchToDefaultContent(driver);
							ThreadSleep(5000);
							switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
							ThreadSleep(2000);
							if (selectVisibleTextFromDropDown(driver, sp.getSelectedRecordType(10),
									"Selected Tab List", object.Advisor.toString().replace("_", " "))) {
								appLog.info(object.Accounts.toString() + " is selected successfully in Available tabs");
								if (click(driver, sp.getRemoveBtn(10), "Custom Tab Add Button",
										action.SCROLLANDBOOLEAN)) {
									appLog.error("clicked on Add button");
								} else {
									appLog.error("Not able to click on Add button so cannot add custom tabs");
									sa.assertTrue(false,object.Advisor.toString().replace("_", " ") + " record type is not Selected list Tab for " + profileForSelection[k]);
								}
							} else {
								appLog.error(object.Advisor.toString().replace("_", " ") + " record type is not Selected list Tab.");
							}
							if (click(driver, sp.getCreateUserSaveBtn_Lighting(10), "Save Button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on save button for record type settiing", YesNo.No);
								ThreadSleep(2000);
							} else {
								log(LogStatus.ERROR, "not able to click on save button for record type settiing", YesNo.Yes);
								sa.assertTrue(false,"not able to click on save button for record type settiing");
		
							}
							}else {
								log(LogStatus.ERROR, "not able to click on edit button for record type settiing", YesNo.Yes);
								sa.assertTrue(false,"not able to click on edit button for record type settiing");
							}
					}else {
						log(LogStatus.ERROR, profileForSelection[k]+" profile is not clickable", YesNo.Yes);
						sa.assertTrue(false,profileForSelection[k]+" profile is not clickable");
					}
				
				} else {
					log(LogStatus.ERROR, "profiles tab is not clickable", YesNo.Yes);
					sa.assertTrue(false,"profiles tab is not clickable");
				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
				sa.assertTrue(false, "setup link is not clickable");
			}
		}else {
			log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
			sa.assertTrue(false, "setup link is not clickable");
		}
			refresh(driver);
	}	
			home.notificationPopUpClose();
			if (home.clickOnSetUpLink()) {
				flag = false;
				parentID = switchOnWindow(driver);
				if (parentID != null) {
					if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Firm.toString())) {
						if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Firm,
								ObjectFeatureName.recordTypes)) {
							if (sp.clickOnAlreadyCreatedLayout(AQRecordType2)) {
								if (sp.editRecordTypeForObject(projectName, RecordType, 10)) {
									log(LogStatus.ERROR,AQRecordType2+" has been updated ",YesNo.Yes);	
								}else {
									log(LogStatus.ERROR,AQRecordType2+" not updated ",YesNo.Yes);
									sa.assertTrue(false, AQRecordType2+" not updated ");
								}
							
							}else {
								log(LogStatus.ERROR, AQRecordType2+" is not clickable", YesNo.Yes);
								sa.assertTrue(false, AQRecordType2+" is not clickable");
							}
					
						}else {
							log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
						}
					}else {
						log(LogStatus.ERROR, "Target object could not be found in object manager", YesNo.Yes);
						sa.assertTrue(false, "Target object could not be found in object manager");
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
		lp.switchToLighting();
		ThreadSleep(2000);
		switchToDefaultContent(driver);
		lp.CRMlogout();
		ThreadSleep(3000);
		lp.CRMLogin(crmUser1EmailID, adminPassword,appName);
		ThreadSleep(2000);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(clickUsingJavaScript(driver, npbl.getFirmLinkOnCreateOption(5),"Firm Link on Create Option", action.BOOLEAN)) {
				log(LogStatus.INFO, "Able to Click on " + PageLabel.Firm.toString(), YesNo.No);
				click(driver, ip.firmRecordTypeQuickLinksPopUp(10), "firmRecordTypeQuickLinksPopUp",
						action.SCROLLANDBOOLEAN);
				if(click(driver, ip.firmRecordTypeDropDownElementQuickLinksPopUp(object.Advisor.toString().replace("_", " "), 5),"Select Record Type :" + object.Advisor.toString().replace("_", " "), action.BOOLEAN)) {
						log(LogStatus.INFO, object.Advisor.toString().replace("_", " ")+" Record Type from Create Option is visible", YesNo.No);
						sa.assertTrue(false,object.Advisor.toString().replace("_", " ") +" Record Type from Create Option is visible");
					} else {
						log(LogStatus.ERROR,object.Advisor.toString().replace("_", " ") +" Record Type from Create Option is not visible", YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR,"Not able to Click on " + PageLabel.Firm.toString(), YesNo.Yes);
					sa.assertTrue(false,"Not able to Click on " + PageLabel.Firm.toString());
				}
			} else {
				log(LogStatus.ERROR,"Not Able to Click on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
			}
		switchToDefaultContent(driver);
		ThreadSleep(5000);
		sa.assertAll();
		lp.CRMlogout();
	}

@Parameters({ "projectName"})
@Test
	public void AQTc018_UpdateRecordTypesAsActive(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	ResearchPageBusinessLayer rp = new ResearchPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	
	lp.CRMLogin(superAdminUserName, adminPassword);
	
	String[][] RecordType = { { recordTypeLabel.Active.toString(), "Checked" }};
	String[] profileForSelection = { "PE Standard User"};
		boolean flag = false;
		String parentID=null;
	
			home.notificationPopUpClose();
			if (home.clickOnSetUpLink()) {
				flag = false;
				parentID = switchOnWindow(driver);
				if (parentID != null) {
					if (sp.searchStandardOrCustomObject(environment, Mode.Lightning.toString(), object.Firm.toString())) {
						if (sp.clickOnObjectFeature(environment, Mode.Lightning.toString(), object.Firm,
								ObjectFeatureName.recordTypes)) {
							if (sp.clickOnAlreadyCreatedLayout(AQRecordType2)) {
								if (sp.editRecordTypeForObject(projectName, RecordType, 10)) {
									log(LogStatus.ERROR,AQRecordType2+" has been updated ",YesNo.Yes);	
								}else {
									log(LogStatus.ERROR,AQRecordType2+" not updated ",YesNo.Yes);
									sa.assertTrue(false, AQRecordType2+" not updated ");
								}
							
							}else {
								log(LogStatus.ERROR, AQRecordType2+" is not clickable", YesNo.Yes);
								sa.assertTrue(false, AQRecordType2+" is not clickable");
							}
					
						}else {
							log(LogStatus.ERROR, "object feature "+ObjectFeatureName.recordTypes+" is not clickable", YesNo.Yes);
							sa.assertTrue(false, "object feature "+ObjectFeatureName.recordTypes+" is not clickable");
						}
					}else {
						log(LogStatus.ERROR, "Target object could not be found in object manager", YesNo.Yes);
						sa.assertTrue(false, "Target object could not be found in object manager");
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
			
			for(int k=0; k<profileForSelection.length; k++) {
				home.notificationPopUpClose();
				if (home.clickOnSetUpLink()) {
					parentID = switchOnWindow(driver);
				if (parentID!=null) {
					if (sp.searchStandardOrCustomObject(environment, mode, object.Profiles.toString())) {
						log(LogStatus.INFO, "click on Object : " + object.Profiles, YesNo.No);
						ThreadSleep(2000);
						switchToDefaultContent(driver);
						switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
						ThreadSleep(2000);
						if (clickUsingJavaScript(driver, rp.getProfileSelected(profileForSelection[k],10), profileForSelection[k].toString(), action.BOOLEAN)) {
							log(LogStatus.INFO, "able to click on " + profileForSelection[k], YesNo.No);
							ThreadSleep(2000);
							switchToDefaultContent(driver);
							ThreadSleep(2000);
							switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
							ThreadSleep(2000);
							if (click(driver, rp.getEditButtonForRecordTypes(TabName.Account.toString(), 10), "Edit Button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "able to click on edit button for record type settiing", YesNo.No);
								switchToDefaultContent(driver);
								ThreadSleep(5000);
								switchToFrame(driver, 10, sp.getSetUpPageIframe(10));
								ThreadSleep(2000);
								if (selectVisibleTextFromDropDown(driver, sp.getavailableRecordType(10),
										"Selected Tab List", object.Advisor.toString().replace("_", " "))) {
									appLog.info(object.Accounts.toString() + " is selected successfully in Available tabs");
									if (click(driver, sp.getAddBtn(10), "Custom Tab Add Button",
											action.SCROLLANDBOOLEAN)) {
										appLog.error("clicked on Add button");
									} else {
										appLog.error("Not able to click on Add button so cannot add custom tabs");
										sa.assertTrue(false,object.Advisor.toString().replace("_", " ") + " record type is not Selected list Tab for " + profileForSelection[k]);
									}
								} else {
									appLog.error(object.Advisor.toString().replace("_", " ") + " record type is not Selected list Tab.");
								}
								if (click(driver, sp.getCreateUserSaveBtn_Lighting(10), "Save Button",
										action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "clicked on save button for record type settiing", YesNo.No);
									ThreadSleep(2000);
								} else {
									log(LogStatus.ERROR, "not able to click on save button for record type settiing", YesNo.Yes);
									sa.assertTrue(false,"not able to click on save button for record type settiing");
			
								}
								}else {
									log(LogStatus.ERROR, "not able to click on edit button for record type settiing", YesNo.Yes);
									sa.assertTrue(false,"not able to click on edit button for record type settiing");
								}
						}else {
							log(LogStatus.ERROR, profileForSelection[k]+" profile is not clickable", YesNo.Yes);
							sa.assertTrue(false,profileForSelection[k]+" profile is not clickable");
						}
					
					} else {
						log(LogStatus.ERROR, "profiles tab is not clickable", YesNo.Yes);
						sa.assertTrue(false,"profiles tab is not clickable");
					}
					driver.close();
					driver.switchTo().window(parentID);
				}else {
					log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
					sa.assertTrue(false, "setup link is not clickable");
				}
			}else {
				log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
				sa.assertTrue(false, "setup link is not clickable");
			}
		}
			
		lp.switchToLighting();
		ThreadSleep(2000);
		switchToDefaultContent(driver);
		lp.CRMlogout();
		ThreadSleep(3000);
		lp.CRMLogin(crmUser1EmailID, adminPassword,appName);
		ThreadSleep(2000);
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(clickUsingJavaScript(driver, npbl.getFirmLinkOnCreateOption(5),"Firm Link on Create Option", action.BOOLEAN)) {
				log(LogStatus.INFO, "Able to Click on " + PageLabel.Firm.toString(), YesNo.No);
				click(driver, ip.firmRecordTypeQuickLinksPopUp(10), "firmRecordTypeQuickLinksPopUp",
						action.SCROLLANDBOOLEAN);
				if(click(driver, ip.firmRecordTypeDropDownElementQuickLinksPopUp(object.Advisor.toString().replace("_", " "), 5),"Select Record Type :" + object.Advisor.toString().replace("_", " "), action.BOOLEAN)) {
						log(LogStatus.INFO, object.Advisor.toString().replace("_", " ")+" Record Type from Create Option is visible", YesNo.No);
					} else {
						log(LogStatus.ERROR,object.Advisor.toString().replace("_", " ") +" Record Type from Create Option is not visible", YesNo.Yes);
						sa.assertTrue(false,object.Advisor.toString().replace("_", " ") +" Record Type from Create Option is not visible");
					}
				} else {
					log(LogStatus.ERROR,"Not able to Click on " + PageLabel.Firm.toString(), YesNo.Yes);
					sa.assertTrue(false,"Not able to Click on " + PageLabel.Firm.toString());
				}
			} else {
				log(LogStatus.ERROR,"Not Able to Click on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
			}
		switchToDefaultContent(driver);
		ThreadSleep(5000);
		sa.assertAll();
		lp.CRMlogout();
	}

	/*Test Cases for Acuity Quick Contact Create */
@Parameters({ "projectName"})
@Test
	public void AQTc019_VerifyTheNavigationMenuItems(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	ContactsPageBusinessLayer ip = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword);
		
		// Verification on navigation menu
		if (ip.clickOnTab(projectName, TabName.Object2Tab)) {	
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ThreadSleep(2000);
				String Value = npbl.getContactLinkOnCreateOption(5).getText();
				if(Value.equalsIgnoreCase(PageLabel.Contact.toString())) {
					log(LogStatus.INFO, PageLabel.Contact.toString() + "is visible at 3rd position", YesNo.No);
				} else {
					log(LogStatus.ERROR, PageLabel.Contact.toString() + "is not visible at 3rd position", YesNo.Yes);
					sa.assertTrue(false,PageLabel.Contact.toString() + "is not visible at 3rd position");
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+ TabName.ContactTab, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+ TabName.ContactTab);
		}
		refresh(driver);
		
		if (ip.clickOnTab(projectName, TabName.Object2Tab)) {
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				if(clickUsingJavaScript(driver, npbl.getContactLinkOnCreateOption(5),"Firm Link on Create Option", action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to Click on " + PageLabel.Contact.toString(), YesNo.No);
					
					if(npbl.getFirstNameTextBoxForCreateOption(5) != null) {
						log(LogStatus.INFO, "First Name from Contact Option is visible", YesNo.No);
					} else {
						log(LogStatus.ERROR, "First Name from Contact Option is not visible", YesNo.Yes);
						sa.assertTrue(false,"First Name from Contact Option is not visible");
					}
					
					if(npbl.getLastNameTextBoxForCreateOption(5) != null) {
						log(LogStatus.INFO, "Last Name from Contact Option is visible", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Last Name from Contact Option is not visible", YesNo.Yes);
						sa.assertTrue(false,"Last Name from Contact Option is not visible");
					}
					
					if(npbl.getFirmNameTextBoxForContactOption(5) != null) {
						log(LogStatus.INFO, "Firm Name from Create Option is visible", YesNo.No);
					} else {
						log(LogStatus.ERROR,"Firm Name from Create Option is not visible", YesNo.Yes);
						sa.assertTrue(false,"Firm Name from Create Option is not visible");
					}
					
					if(npbl.getEmailTextBoxForContactOption(5) != null) {
						log(LogStatus.INFO, "Email from Contact Option is visible", YesNo.No);
					} else {
						log(LogStatus.ERROR,"Email from Contact Option is not visible", YesNo.Yes);
						sa.assertTrue(false,"Email from Contact Option is not visible");
					}
					
					if(npbl.getPhoneTextBoxForContactOption(5) != null) {
						log(LogStatus.INFO, "Phone from Contact Option is visible", YesNo.No);
					} else {
						log(LogStatus.ERROR,"Phone from Contact Option is not visible", YesNo.Yes);
						sa.assertTrue(false,"Phone from Contact Option is not visible");
					}
					
					if(npbl.getCrossIconForQuickFirm(5) != null) {
						log(LogStatus.INFO, "Cross Icon for Quick Firm is visible", YesNo.No);
					} else {
						log(LogStatus.ERROR,"Cross Icon for Quick Firm is not visible", YesNo.Yes);
						sa.assertTrue(false,"Cross Icon for Quick Firm is not visible");
					}
	
				if(click(driver, npbl.getLastNameTextBoxForCreateOption(5),"Last Name Text Box For Create Option", action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Last Name Text Box", YesNo.No);
					ThreadSleep(2000);
					if(npbl.getSaveButtonForCreateOption(5) != null) {
						log(LogStatus.INFO, "Save Button from Create Option is visible", YesNo.No);
						click(driver, npbl.getSaveButtonForCreateOption(5),"Save Button For Quick Firm", action.BOOLEAN);
						if(npbl.getErrorOnQuickContact(5) != null) {
							log(LogStatus.INFO,"Error Message is visible" + npbl.getErrorOnQuickContact(5), YesNo.No);
						} else {
							log(LogStatus.ERROR,"Error Message is not visible" + npbl.getErrorOnQuickContact(5), YesNo.Yes);
							sa.assertTrue(false,"Error Message is not visible" + npbl.getErrorOnQuickContact(5));
						}
						if(npbl.getErrorOnQuickFirmName(5) != null) {
							log(LogStatus.INFO,"Error Message is visible" + npbl.getErrorOnQuickFirmName(5), YesNo.No);
						} else {
							log(LogStatus.ERROR,"Error Message is not visible" + npbl.getErrorOnQuickFirmName(5), YesNo.Yes);
							sa.assertTrue(false,"Error Message is not visible" + npbl.getErrorOnQuickFirmName(5));
						}
					} else {
						log(LogStatus.ERROR,"Save Button from Create Option is not visible", YesNo.Yes);
						sa.assertTrue(false,"Save Button from Create Option is not visible");
					}
				} else {
					log(LogStatus.ERROR,"Not able to clicked on Firm Name Text Box", YesNo.Yes);
					sa.assertTrue(false,"Not able to clicked on Firm Name Text Box");
				}
				ThreadSleep(2000);
					if(npbl.getCancelButtonForCreateOption(5) != null) {
						log(LogStatus.INFO, "Cancel Button from Create Option is visible", YesNo.No);
						click(driver, npbl.getCancelButtonForCreateOption(5),"Cancel Button For Quick Conatct", action.BOOLEAN);
						if(npbl.getCancelButtonForCreateOption(5) == null) {
							log(LogStatus.INFO,"Create Contact Popup is closed", YesNo.Yes);
						} else {
							log(LogStatus.ERROR,"Create Contact Popup is not closed", YesNo.Yes);
							sa.assertTrue(false,"Create Contact Popup is not closed");
						}
					} else {
						log(LogStatus.ERROR,"Cancel Button from Create Option is not visible", YesNo.Yes);
						sa.assertTrue(false,"Cancel Button from Create Option is not visible");
					}
					
				} else {
					log(LogStatus.ERROR, "Not able to Click on " + PageLabel.Contact.toString(), YesNo.Yes);
					sa.assertTrue(false,"Not able to Click on " + PageLabel.Contact.toString());
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
			}
		  } else {
				log(LogStatus.ERROR, "Not Able to Click on "+ TabName.ContactTab, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+ TabName.ContactTab);
			}
			ThreadSleep(2000);
			switchToDefaultContent(driver);
			sa.assertAll();
			lp.CRMlogout();
		}
		
@Parameters({ "projectName"})
@Test
	public void AQTc020_CreateContactFromQuickLinks(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		
		String[][] accounts = {{AQConFN1,AQConLN1,AQConFirm1,AQConEmail1,AQConPhone1},{AQConFN2,AQConLN2,AQConFirm2,null,null},
				{AQConFN3,AQConLN3,null,AQConEmail3,AQConPhone3}};
		for (String[] account : accounts) {
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				if(clickUsingJavaScript(driver, npbl.getContactLinkOnCreateOption(5),"Firm Link on Create Option", action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to Click on " + PageLabel.Contact.toString(), YesNo.No);
						if (cp.createContactFromQuickLinks(environment, mode, account[0], account[1],account[2],account[3],account[4])) {
							log(LogStatus.INFO,
									"successfully Created Contact : " + account[0] + " " + account[1],
									YesNo.No);
						} else {
							sa.assertTrue(false,"Not Able to Create Contact : " + account[0] + " " + account[1]);
							log(LogStatus.SKIP,"Not Able to Create Contact: " + account[0] + " " + account[1],YesNo.Yes);
						}
					} else {
						log(LogStatus.ERROR,"Not able to Click on " + PageLabel.Contact.toString(), YesNo.Yes);
						sa.assertTrue(false,"Not able to Click on " + PageLabel.Contact.toString());
					}
				} else {
					log(LogStatus.ERROR,"Not Able to Click on "+navigationMenuName, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
				}
			refresh(driver);
			ThreadSleep(2000);
		}
	ThreadSleep(2000);
	switchToDefaultContent(driver);
	sa.assertAll();
	lp.CRMlogout();
 }

@Parameters({ "projectName"})
@Test
	public void AQTc021_VerifyTheNavigationMenuItems(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	ContactsPageBusinessLayer ip = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword);
		
		if (ip.clickOnTab(projectName, TabName.Object2Tab)) {
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				if(clickUsingJavaScript(driver, npbl.getContactLinkOnCreateOption(5),"Firm Link on Create Option", action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to Click on " + PageLabel.Contact.toString(), YesNo.No);
										
					if(npbl.getFirmNameTextBoxForContactOption(5) != null) {
						log(LogStatus.INFO, "Firm Name from Create Option is visible", YesNo.No);
					} else {
						log(LogStatus.ERROR,"Firm Name from Create Option is not visible", YesNo.Yes);
						sa.assertTrue(false,"Firm Name from Create Option is not visible");
					}
					
					if(npbl.getEmailTextBoxForContactOption(5) != null) {
						log(LogStatus.INFO, "Email from Contact Option is visible", YesNo.No);
					} else {
						log(LogStatus.ERROR,"Email from Contact Option is not visible", YesNo.Yes);
						sa.assertTrue(false,"Email from Contact Option is not visible");
					}
					
					if(npbl.getSaveButtonForCreateOption(5) != null) {
						log(LogStatus.INFO, "Save Button from Create Option is visible", YesNo.No);
						click(driver, npbl.getSaveButtonForCreateOption(5),"Save Button For Quick Firm", action.BOOLEAN);
						if(npbl.getErrorOnQuickContact(5) != null) {
							log(LogStatus.INFO,"Error Message is visible", YesNo.Yes);
						} else {
							log(LogStatus.ERROR,"Error Message is not visible", YesNo.Yes);
							sa.assertTrue(false,"Error Message is not visible");
						}
					} else {
						log(LogStatus.ERROR,"Save Button from Create Option is not visible", YesNo.Yes);
						sa.assertTrue(false,"Save Button from Create Option is not visible");
					}
					
					if(npbl.getCancelButtonForCreateOption(5) != null) {
						log(LogStatus.INFO, "Cancel Button from Create Option is visible", YesNo.No);
						click(driver, npbl.getCancelButtonForCreateOption(5),"Cancel Button For Quick Conatct", action.BOOLEAN);
						if(npbl.getCancelButtonForCreateOption(5) == null) {
							log(LogStatus.INFO,"Create Contact Popup is closed", YesNo.Yes);
						} else {
							log(LogStatus.ERROR,"Create Contact Popup is not closed", YesNo.Yes);
							sa.assertTrue(false,"Create Contact Popup is not closed");
						}
					} else {
						log(LogStatus.ERROR,"Cancel Button from Create Option is not visible", YesNo.Yes);
						sa.assertTrue(false,"Cancel Button from Create Option is not visible");
					}
					
				} else {
					log(LogStatus.ERROR, "Not able to Click on " + PageLabel.Contact.toString(), YesNo.Yes);
					sa.assertTrue(false,"Not able to Click on " + PageLabel.Contact.toString());
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
			}
		  } else {
				log(LogStatus.ERROR, "Not Able to Click on "+ TabName.ContactTab, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+ TabName.ContactTab);
			}
			ThreadSleep(2000);
			switchToDefaultContent(driver);
			sa.assertAll();
			lp.CRMlogout();
		}
	
@Parameters({ "projectName"})
@Test
	public void AQTc022_VerifyingErrorOnCreatingContact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		boolean flag = false;
		String[][] accounts = {{AQConFN5,AQConLN5,AQConFirm5,AQConEmail5,AQConPhone5}};
		for (String[] account : accounts) {
			try {
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				if(clickUsingJavaScript(driver, npbl.getContactLinkOnCreateOption(5),"Firm Link on Create Option", action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to Click on " + PageLabel.Contact.toString(), YesNo.No);
					flag = cp.createContactFromQuickLinks(environment, mode, account[0], account[1],account[2],account[3],account[4]);
					if(flag) {
						log(LogStatus.INFO,"Successfully Created Contact with maximum character limit of First Name(40 Characters): " + account[0] + " and Last Name(80 Characters)" + account[1],YesNo.No);
						} else {
							sa.assertTrue(false,"Not able to Create Contact : " + account[0] + " " + account[1]);
							log(LogStatus.ERROR,
									"Not able to Create Contact : " + account[0] + " " + account[1],
									YesNo.Yes);
						}
					} else {
						log(LogStatus.ERROR,"Not able to Click on " + PageLabel.Contact.toString(), YesNo.Yes);
						sa.assertTrue(false,"Not able to Click on " + PageLabel.Contact.toString());
					}
				} else {
					log(LogStatus.ERROR,"Not Able to Click on "+navigationMenuName, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
				}
			} catch (Exception e) {
				log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
			}
		}
	ThreadSleep(2000);
	switchToDefaultContent(driver);
	sa.assertAll();
	lp.CRMlogout();
	}

@Parameters({ "projectName"})
@Test
	public void AQTc023_VerifyingErrorOnCreatingContact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		boolean flag = false;
		String[][] accounts = {{AQConFN4,AQConLN4,AQConFirm4,AQConEmail4,AQConPhone4}};
		for (String[] account : accounts) {
			try {
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				if(clickUsingJavaScript(driver, npbl.getContactLinkOnCreateOption(5),"Firm Link on Create Option", action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to Click on " + PageLabel.Contact.toString(), YesNo.No);
					if (sendKeys(driver, cp.contactLastNameQuickLinksPopUp(20), account[1], "lastName name text box: " + account[1],
							action.SCROLLANDBOOLEAN)) {
						appLog.info("passed data in text box: " + account[1]);

					} else {
						appLog.error("Not Able to Pass the value: " + account[1]);
					}
					
					if (sendKeys(driver, cp.contactEmailQuickLinksPopUp(5), account[3], "Email name text box: " + account[3],
							action.SCROLLANDBOOLEAN)) {
						appLog.info("passed data in text box: " + account[3]);

					} else {
						appLog.error("Not Able to Pass the value: " + account[3]);
					}
						ThreadSleep(2000);
						click(driver, cp.saveButtonQuickLinksPopUp(10), "saveButtonQuickLinksPopUp: ", action.SCROLLANDBOOLEAN);
						ThreadSleep(2000);
				
						if(npbl.getErrorOnEmailOfQuickContact(5) != null) {
							log(LogStatus.INFO,"Error Message is visible" + npbl.getErrorOnEmailOfQuickContact(5), YesNo.No);
						} else {
							log(LogStatus.ERROR,"Error Message is not visible" + npbl.getErrorOnEmailOfQuickContact(5), YesNo.Yes);
							sa.assertTrue(false,"Error Message is not visible" + npbl.getErrorOnEmailOfQuickContact(5));
						}
					} else {
						log(LogStatus.ERROR,"Not able to Click on " + PageLabel.Contact.toString(), YesNo.Yes);
						sa.assertTrue(false,"Not able to Click on " + PageLabel.Contact.toString());
					}
				} else {
					log(LogStatus.ERROR,"Not Able to Click on "+navigationMenuName, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
				}
			} catch (Exception e) {
				log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
			}
		}
	ThreadSleep(2000);
	switchToDefaultContent(driver);
	sa.assertAll();
	lp.CRMlogout();
	}

@Parameters({ "projectName"})
@Test
	public void AQTc024_DeleteContactAndVerifyImpact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		boolean flag = false;
			if (ip.clickOnTab(projectName, mode,TabName.ContactTab)) {
				if (ip.clickOnAlreadyCreatedItem(projectName, TabName.ContactTab, AQConName1, 20)) {
					WebElement ele=null;
					cp.clickOnShowMoreDropdownOnly(environment, mode,PageName.Object2Page);
					log(LogStatus.INFO,"Able to Click on Show more Icon : "+TabName.ContactTab+" For : "+AQConName1,YesNo.No);
					ThreadSleep(500);
					ele = cp.actionDropdownElement(projectName, ShowMoreActionDropDownList.Delete, 15);
					if (ele==null) {
						ele =cp.getDeleteButton(30);
					}
					ThreadSleep(5000);
					if (clickUsingJavaScript(driver, ele, "delete", action.BOOLEAN)) {
						ThreadSleep(2000);
						if(clickUsingJavaScript(driver,cp.getDeleteButtonPopUp(projectName, 10), "delete", action.BOOLEAN)) {
							log(LogStatus.INFO,"Able to Click on delete button on delete popup : "+TabName.ContactTab+" For : "+AQConName1,YesNo.No); 
						}else {
							sa.assertTrue(false,"Not Able to Select delete button for "+AQConName1+" For Label "+PageLabel.Contact_Name);
							log(LogStatus.SKIP,"Not Able to Select delete button for "+AQConName1+" For Label "+PageLabel.Contact_Name,YesNo.Yes);
		
						}
					}else {
						sa.assertTrue(false,"Not Able to Select delete button for "+AQConName1+" For Label "+PageLabel.Contact_Name);
						log(LogStatus.SKIP,"Not Able to Select delete button for "+AQConName1+" For Label "+PageLabel.Contact_Name,YesNo.Yes);
		
					}
				}else {
					sa.assertTrue(false,"Not Able to find Firm "+AQConName1+" For Label "+PageLabel.Contact_Name);
					log(LogStatus.SKIP,"Not Able to find Firm "+AQConName1+" For Label "+PageLabel.Contact_Name,YesNo.Yes);
		
				}
			}else {
				sa.assertTrue(false,"not able to click on Firm tab");
				log(LogStatus.SKIP,"not able to click on Firm tab",YesNo.Yes);
			}
		refresh(driver);
			if (lp.restoreValueFromRecycleBin(projectName, AQConName1)) {
				log(LogStatus.INFO, "Able to restore item from Recycle Bin " + AQFirm1, YesNo.Yes);
				flag = true;
			} else {
				sa.assertTrue(false, "Not Able to restore item from Recycle Bin " + AQFirm1);
				log(LogStatus.SKIP, "Not Able to restore item from Recycle Bin " + AQFirm1, YesNo.Yes);
			}
		refresh(driver);
		
		 if (fp.clickOnTab(environment, mode, TabName.ContactTab)) {
		       log(LogStatus.INFO, "Click on Tab : " + TabName.ContactTab, YesNo.No);
		      if (fp.clickOnAlreadyCreatedItem(projectName, TabName.ContactTab,AQConName1.replace("  ", "").replace("\"", ""), 10)) {
		          log(LogStatus.INFO, "Able to open created Account : " + AQConName1, YesNo.Yes);
		       } else {
		          sa.assertTrue(false, "Not Able to open created Account : " + AQConName1);
		          log(LogStatus.SKIP, "Not Able to open created Account: " + AQConName1, YesNo.Yes);
		      }
		   } else {
		       log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
		       sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		   }
	}

@Parameters({ "projectName"})
@Test
	public void AQTc025_RemoveObjectPermissionForContact_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword);
	
	String profileForSelection = "PE Standard User";
	String parentID=null, objects[] ={object.Contact.toString()}, permissionTypes[] = {PermissionType.Read.toString(),PermissionType.Create.toString(),PermissionType.Edit.toString(),PermissionType.Delete.toString()}, status = "Not Checked";
	boolean flag = false;
	
		switchToDefaultContent(driver);
		home.notificationPopUpClose();
			if (home.clickOnSetUpLink()) {
				parentID = switchOnWindow(driver);
			if (parentID!=null) {
				ThreadSleep(2000);
					if(sp.giveAndRemoveObjectPermissionFromProfiles(profileForSelection,objects,permissionTypes,status)) {
						log(LogStatus.PASS,"Remove Permission from Contact Object", YesNo.No);
						flag=true;
					}else {
						log(LogStatus.ERROR,"Not able to remove permission from" + objects + "Object", YesNo.Yes);
						sa.assertTrue(false, "Not able to remove permission from" + objects + "Object");
					}
				driver.close();
				driver.switchTo().window(parentID);
				}else {
					log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
					sa.assertTrue(false, "setup link is not clickable");
				}
			} else {
				log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
				sa.assertTrue(false, "setup link is not clickable");
			}
		switchToDefaultContent(driver);
		ThreadSleep(5000);
		lp.CRMlogout();
		ThreadSleep(5000);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		switchToDefaultContent(driver);
		home.notificationPopUpClose();
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ThreadSleep(2000);
				clickUsingJavaScript(driver, npbl.getContactLinkOnCreateOption(5),"Contact Link on Create Option", action.BOOLEAN);
				ThreadSleep(5000);
				if(isDisplayed(driver, npbl.getEmailTextBoxForContactOption(10), "Visibility", 10, "Email Text Box On Contact") == null) {
					log(LogStatus.INFO, PageLabel.Contact.toString() + "is not functional", YesNo.No);
				} else {
					log(LogStatus.ERROR, PageLabel.Contact.toString() + "is functional", YesNo.Yes);
					sa.assertTrue(false,PageLabel.Contact.toString() + "is functional");
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
			}
	switchToDefaultContent(driver);
	ThreadSleep(5000);
	sa.assertAll();
	lp.CRMlogout();		
	}

@Parameters({ "projectName"})
@Test
	public void AQTc026_AddObjectPermissionForContact_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword);
	
	String profileForSelection = "PE Standard User";
	String parentID=null, objects[] ={object.Contact.toString()}, permissionTypes[] = {PermissionType.Read.toString(),PermissionType.Create.toString(),PermissionType.Update.toString(),PermissionType.Delete.toString()}, status = "Checked";
	boolean flag = false;
	
		switchToDefaultContent(driver);
		home.notificationPopUpClose();
		try {
			if (home.clickOnSetUpLink()) {
				parentID = switchOnWindow(driver);
			if (parentID!=null) {
				ThreadSleep(2000);
					if(sp.giveAndRemoveObjectPermissionFromProfiles(profileForSelection,objects,permissionTypes,status)) {
						log(LogStatus.PASS,"Remove Permission from Contact Object", YesNo.No);
						flag=true;
					}else {
						log(LogStatus.ERROR,"Not able to remove permission from" + objects + "Object", YesNo.Yes);
						sa.assertTrue(false, "Not able to remove permission from" + objects + "Object");
					}
				driver.close();
				driver.switchTo().window(parentID);
				}else {
					log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
					sa.assertTrue(false, "setup link is not clickable");
				}
			} else {
				log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
				sa.assertTrue(false, "setup link is not clickable");
			}
		} catch (Exception e) {
			log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
		}
		
		switchToDefaultContent(driver);
		ThreadSleep(5000);
		lp.CRMlogout();
		ThreadSleep(5000);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		switchToDefaultContent(driver);
		home.notificationPopUpClose();
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				ThreadSleep(2000);
				clickUsingJavaScript(driver, npbl.getContactLinkOnCreateOption(5),"Contact Link on Create Option", action.BOOLEAN);
				ThreadSleep(5000);
				if(isDisplayed(driver, npbl.getEmailTextBoxForContactOption(10), "Visibility", 10, "Email Text Box On Contact") != null) {
					log(LogStatus.INFO, PageLabel.Contact.toString() + "is functional", YesNo.No);
				} else {
					log(LogStatus.ERROR, PageLabel.Contact.toString() + "is not functional", YesNo.Yes);
					sa.assertTrue(false,PageLabel.Contact.toString() + "is not functional");
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
			}
	switchToDefaultContent(driver);
	ThreadSleep(5000);
	sa.assertAll();
	lp.CRMlogout();		
	}

@Parameters({ "projectName" })
@Test
	public void AQTc027_MakeFieldInvisibleForContactPage_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	ContactsPageBusinessLayer ip = new ContactsPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	
	lp.CRMLogin(superAdminUserName, adminPassword);
	String parentWindow = "";

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

		if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Contact,
				ObjectFeatureName.FieldAndRelationShip, "Email", PermissionType.removePermission, "PE Standard User")) {
			log(LogStatus.PASS,
					"Email field Permission is given in the Contact Object Manager",
					YesNo.No);
		} else {
			log(LogStatus.ERROR,
					"Email field Permission is not given in the Contact Object Manager",
					YesNo.No);
			sa.assertTrue(false,
					"Email field Permission is not given in the Contact Object Manager");
		}
		
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		driver.close();
	}
		driver.switchTo().window(parentWindow);
		
		lp.CRMlogout();	
		ThreadSleep(3000);
		lp.CRMLogin(glUser1EmailID, adminPassword);
		ThreadSleep(2000);
		if (ip.clickOnTab(projectName, TabName.Object2Tab)) {
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				if(clickUsingJavaScript(driver, npbl.getContactLinkOnCreateOption(5),"Contact Link on Create Option", action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to Click on " + PageLabel.Contact.toString(), YesNo.No);
					
					if(npbl.getEmailTextBoxForContactOption(5) == null) {
						log(LogStatus.INFO, "Email from Contact Option is not visible", YesNo.No);
					} else {
						log(LogStatus.ERROR,"Email from Contact Option is visible", YesNo.Yes);
						sa.assertTrue(false,"Email from Contact Option is visible");
					}
					
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
				}
			  } else {
					log(LogStatus.ERROR, "Not Able to Click on "+ TabName.ContactTab, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+ TabName.ContactTab);
				}
					
			driver.switchTo().window(parentWindow);
		}
		sa.assertAll();
		lp.CRMlogout();
	}
	
@Parameters({ "projectName" })
@Test
	public void AQTc028_MakeFieldVisibleForContactPage_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	ContactsPageBusinessLayer ip = new ContactsPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	
	lp.CRMLogin(superAdminUserName, adminPassword);
	String parentWindow = "";

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

		if (setup.giveAndRemoveObjectPermissionFromObjectManager(object.Contact,
				ObjectFeatureName.FieldAndRelationShip, "Email", PermissionType.givePermission, "PE Standard User")) {
			log(LogStatus.PASS,
					"Email field Permission is given in the Contact Object Manager",
					YesNo.No);
		} else {
			log(LogStatus.ERROR,
					"Email field Permission is not given in the Contact Object Manager",
					YesNo.No);
			sa.assertTrue(false,
					"Email field Permission is not given in the Contact Object Manager");
		}
		
		CommonLib.switchToDefaultContent(driver);
		ThreadSleep(2000);
		driver.close();
	}
		driver.switchTo().window(parentWindow);
		
		lp.CRMlogout();	
		ThreadSleep(3000);
		lp.CRMLogin(glUser1EmailID, adminPassword);
		ThreadSleep(2000);
		if (ip.clickOnTab(projectName, TabName.Object2Tab)) {
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				if(clickUsingJavaScript(driver, npbl.getContactLinkOnCreateOption(5),"Contact Link on Create Option", action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to Click on " + PageLabel.Contact.toString(), YesNo.No);
					
					if(npbl.getEmailTextBoxForContactOption(5) != null) {
						log(LogStatus.INFO, "Email from Contact Option is visible", YesNo.No);
					} else {
						log(LogStatus.ERROR,"Email from Contact Option is not visible", YesNo.Yes);
						sa.assertTrue(false,"Email from Contact Option is not visible");
					}
					
				} else {
					log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
				}
			  } else {
					log(LogStatus.ERROR, "Not Able to Click on "+ TabName.ContactTab, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+ TabName.ContactTab);
				}
					
			driver.switchTo().window(parentWindow);
		}
		sa.assertAll();
		lp.CRMlogout();
	}

@Parameters({ "projectName" })
@Test
	public void AQTc029_UpdateFieldNames_VerifyImpact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	ContactsPageBusinessLayer ip = new ContactsPageBusinessLayer(driver);
	
	lp.CRMLogin(superAdminUserName, adminPassword);
	String parentWindow = "";
	boolean flag1 = false;
	String tabNames2 = object.Contacts.toString() ;
	String[] labelsWithValues2 = {"First Name<break>F Name","Last Name<break>L Name","Email<break>Email ID","Phone<break>Phone Number"};
	
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
		if (setup.searchStandardOrCustomObject(environment, mode, object.Rename_Tabs_and_Labels.toString())) {
			log(LogStatus.INFO, "click on Object : " + object.Rename_Tabs_and_Labels, YesNo.No);
			ThreadSleep(2000);

		if (setup.renameLabelsOfFields(driver, tabNames2, labelsWithValues2, 10)) {
			flag1 = true;
			log(LogStatus.PASS, labelsWithValues2[0] + " is updated as " +labelsWithValues2[1] , YesNo.Yes);
		}
		} else {
			log(LogStatus.ERROR, "Not able to search/click on " + object.Rename_Tabs_and_Labels, YesNo.Yes);
			sa.assertTrue(false, "Not able to search/click on " + object.Rename_Tabs_and_Labels);
		}	
		driver.close();
	}		
	ThreadSleep(2000);
	driver.switchTo().window(parentWindow);
	lp.CRMlogout();
	ThreadSleep(2000);
	lp.CRMLogin(glUser1EmailID, adminPassword);
	ThreadSleep(2000);
	if (ip.clickOnTab(projectName, TabName.Object2Tab)) {
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(clickUsingJavaScript(driver, npbl.getContactLinkOnCreateOption(5),"Firm Link on Create Option", action.BOOLEAN)) {
				log(LogStatus.INFO, "Able to Click on " + PageLabel.Contact.toString(), YesNo.No);
				
				if(npbl.getFNameLabelForCreateOption(5) != null) {
					log(LogStatus.INFO, "First Name from Contact Option is visible", YesNo.No);
				} else {
					log(LogStatus.ERROR, "First Name from Contact Option is not visible", YesNo.Yes);
					sa.assertTrue(false,"First Name from Contact Option is not visible");
				}
				
				if(npbl.getLNameLabelForCreateOption(5) != null) {
					log(LogStatus.INFO, "Last Name from Contact Option is visible", YesNo.No);
				} else {
					log(LogStatus.ERROR, "Last Name from Contact Option is not visible", YesNo.Yes);
					sa.assertTrue(false,"Last Name from Contact Option is not visible");
				}
				
				if(npbl.getEmailIDLabelForCreateOption(5) != null) {
					log(LogStatus.INFO, "Firm Name from Create Option is visible", YesNo.No);
				} else {
					log(LogStatus.ERROR,"Firm Name from Create Option is not visible", YesNo.Yes);
					sa.assertTrue(false,"Firm Name from Create Option is not visible");
				}
				
				if(npbl.getPhoneNumberLabelForCreateOption(5) != null) {
					log(LogStatus.INFO, "Phone from Contact Option is visible", YesNo.No);
				} else {
					log(LogStatus.ERROR,"Phone from Contact Option is not visible", YesNo.Yes);
					sa.assertTrue(false,"Phone from Contact Option is not visible");
				}
				
			} else {
				log(LogStatus.ERROR, "Not able to Click on " + PageLabel.Contact.toString(), YesNo.Yes);
				sa.assertTrue(false,"Not able to Click on " + PageLabel.Contact.toString());
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on "+navigationMenuName, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
		}
	  } else {
			log(LogStatus.ERROR, "Not Able to Click on "+ TabName.ContactTab, YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on "+ TabName.ContactTab);
		}
	sa.assertAll();
	lp.CRMlogout();
}

@Parameters({ "projectName"})
@Test
	public void AQTc030_CreateContactFromQuickLinks(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		
		String[][] accounts = {{AQConFN6,AQConLN6,AQConFirm6,AQConEmail6,AQConPhone6}};
		for (String[] account : accounts) {
			try {
			if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
				log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
				if(clickUsingJavaScript(driver, npbl.getContactLinkOnCreateOption(5),"Firm Link on Create Option", action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to Click on " + PageLabel.Contact.toString(), YesNo.No);
						if (cp.createContactFromQuickLinks(environment, mode, account[0], account[1],account[2],account[3],account[4])) {
							log(LogStatus.INFO,
									"successfully Created Contact : " + account[0] + " " + account[1],
									YesNo.No);
						} else {
							sa.assertTrue(false,"Not Able to Create Contact : " + account[0] + " " + account[1]);
							log(LogStatus.SKIP,"Not Able to Create Contact: " + account[0] + " " + account[1],YesNo.Yes);
						}
					} else {
						log(LogStatus.ERROR,"Not able to Click on " + PageLabel.Contact.toString(), YesNo.Yes);
						sa.assertTrue(false,"Not able to Click on " + PageLabel.Contact.toString());
					}
				} else {
					log(LogStatus.ERROR,"Not Able to Click on "+navigationMenuName, YesNo.Yes);
					sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
				}
			} catch (Exception e) {
				log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
			}
		}
	ThreadSleep(2000);
	switchToDefaultContent(driver);
	sa.assertAll();
	lp.CRMlogout();
	}

@Parameters({ "projectName"})
@Test
	public void AQTc031_CreateContactFromQuickLinks(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		
		String[][] accounts = { {AQConFN7,AQConLN7,AQConFirm7,AQConEmail7,AQConPhone7},{AQConFN8,AQConLN8,AQConFirm8,AQConEmail8,AQConPhone8} };
	for (String[] account : accounts) {
		try {
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(clickUsingJavaScript(driver, npbl.getContactLinkOnCreateOption(5),"Contact Link on Create Option", action.BOOLEAN)) {
				log(LogStatus.INFO, "Able to Click on " + PageLabel.Contact.toString(), YesNo.No);
					if (cp.createContactFromQuickLinks(environment, mode, account[0], account[1],account[2],account[3],account[4])) {
						log(LogStatus.INFO,
								"successfully Created Contact : " + account[0] + " " + account[1],
								YesNo.No);
					} else {
						sa.assertTrue(false,"Not Able to Create Contact : " + account[0] + " " + account[1]);
						log(LogStatus.SKIP,"Not Able to Create Contact: " + account[0] + " " + account[1],YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR,"Not able to Click on " + PageLabel.Contact.toString(), YesNo.Yes);
					sa.assertTrue(false,"Not able to Click on " + PageLabel.Contact.toString());
				}
			} else {
				log(LogStatus.ERROR,"Not Able to Click on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
			}
		} catch (Exception e) {
			log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
		}
	}
	ThreadSleep(2000);
	switchToDefaultContent(driver);
	sa.assertAll();
	lp.CRMlogout();
 }

@Parameters({ "projectName" })
@Test
	public void AQTc032_VerifyTheErrorMessageOnContactPopUpWhenValidationRuleIsCreated(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	
	String fieldName = AQLabel1;
	String validationRuleName = AQLabel2;
	String validationRuleFormula = AQLabel3;
	String validationRuleMessage = AQLabel4;
	String validationRuleErrorMsgLocation = AQLabel5;
	String[] account =  {AQConFN9,AQConLN9,null,null,AQConPhone9};
	boolean flag = false;
	lp.CRMLogin(superAdminUserName, adminPassword);

	if (sp.createValidationRule(object.Contact, fieldName, validationRuleName, validationRuleFormula,
			validationRuleMessage, validationRuleErrorMsgLocation)) {
		log(LogStatus.PASS, "------Succesfully Created the Validation Rule named: " + validationRuleName + "------",
				YesNo.No);
	} else {

		log(LogStatus.ERROR, "------Not able to Create the Validation Rule Named: " + validationRuleName + "------",
				YesNo.No);
		sa.assertTrue(false,
				"------Not able to Create the Validation Rule Named: " + validationRuleName + "------");

	}
	lp.CRMlogout();
	ThreadSleep(3000);
	lp.CRMLogin(crmUser1EmailID, adminPassword);
		
		try {
		if (npbl.clickOnNavatarEdgeLinkHomePage(projectName, navigationMenuName, action.BOOLEAN, 10)) {
			log(LogStatus.INFO, "Able to Click on "+navigationMenuName, YesNo.No);
			if(clickUsingJavaScript(driver, npbl.getContactLinkOnCreateOption(5),"Contact Link on Create Option", action.BOOLEAN)) {
				log(LogStatus.INFO, "Able to Click on " + PageLabel.Contact.toString(), YesNo.No);
				if (sendKeys(driver, cp.contactLastNameQuickLinksPopUp(20), account[1], "lastName name text box: " + account[1],
						action.SCROLLANDBOOLEAN)) {
					appLog.info("passed data in text box: " + account[1]);

				} else {
					appLog.error("Not Able to Pass the value: " + account[1]);
				}
				
				if (sendKeys(driver, cp.contactPhoneQuickLinksPopUp(5), account[4], "Phone number text box: " + account[4],
						action.SCROLLANDBOOLEAN)) {
					appLog.info("passed data in text box: " + account[4]);

				} else {
					appLog.error("Not Able to Pass the value: " + account[4]);
				}
					ThreadSleep(2000);
					click(driver, cp.saveButtonQuickLinksPopUp(10), "saveButtonQuickLinksPopUp: ", action.SCROLLANDBOOLEAN);
					ThreadSleep(2000);
			
					if(npbl.getErrorOnEmailOfQuickContact(5) != null) {
						log(LogStatus.INFO,"Error Message is visible" + npbl.getErrorOnEmailOfQuickContact(5), YesNo.No);
					} else {
						log(LogStatus.ERROR,"Error Message is not visible" + npbl.getErrorOnEmailOfQuickContact(5), YesNo.Yes);
						sa.assertTrue(false,"Error Message is not visible" + npbl.getErrorOnEmailOfQuickContact(5));
					}
				} else {
					log(LogStatus.ERROR,"Not able to Click on " + PageLabel.Contact.toString(), YesNo.Yes);
					sa.assertTrue(false,"Not able to Click on " + PageLabel.Contact.toString());
				}
			} else {
				log(LogStatus.ERROR,"Not Able to Click on "+navigationMenuName, YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on "+navigationMenuName);
			}
		} catch (Exception e) {
			log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No);
		}

	sa.assertAll();
	lp.CRMlogout();
}

}
