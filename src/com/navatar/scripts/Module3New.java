package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.pageObjects.NavigationPageBusineesLayer.sortByValue;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.navatar.generic.BaseLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.NavigationPageBusineesLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;



public class Module3New extends BaseLib {
	
	String passwordResetLink = null;
	String navigationMenuName="Navigation Menu";
	
	
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

	
}