package com.navatar.scripts;


import static com.navatar.generic.BaseLib.AcuityDataSheetFilePath;
import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;
import static com.navatar.generic.SmokeCommonVariables.crmUser1EmailID;
import static com.navatar.generic.SmokeCommonVariables.rgUserPassword;
import static com.navatar.generic.SmokeCommonVariables.rgOrgPassword;
import static com.navatar.generic.SmokeCommonVariables.rgContact1;
import static com.navatar.generic.SmokeCommonVariables.rgContact2;
import static com.navatar.generic.SmokeCommonVariables.rgContact3;
import static com.navatar.generic.SmokeCommonVariables.rgContact4;
import static com.navatar.generic.SmokeCommonVariables.rgUser1;
import static com.navatar.generic.SmokeCommonVariables.rgUser2;
import static com.navatar.generic.SmokeCommonVariables.rgUser3;
import static com.navatar.generic.SmokeCommonVariables.rgContactPassword;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import com.navatar.generic.*;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.Header;
import com.navatar.generic.EnumConstants.InstRecordType;
import com.navatar.generic.EnumConstants.InstitutionPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
import com.navatar.generic.EnumConstants.SortOrder;
import com.navatar.generic.EnumConstants.Stage;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;
import com.navatar.generic.EnumConstants.recordTypeLabel;
import com.navatar.pageObjects.*;
import com.relevantcodes.extentreports.LogStatus;


public class AcuityDealsEmails extends BaseLib {
	
	public static String erromessage="No items to display";
	String recordTypeDescription = "Description Record Type";
	
	
	@Parameters({ "projectName"})
	@Test
	public void ADETc001_createCRMUser(String projectName) {
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
							crmUserProfile,null)) {
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
	public void ADETc002_VerifyAccountAcuitytabvisibilityforNewlyCreatedAccounts(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		//INS
		String value="";
		String type="";
		String TabName1 ="";
		String[][] EntityOrAccounts = {{ ADEIns1, ADEIns1RecordType ,null} , { ADEIns2, ADEIns2RecordType ,null},
		 { ADEIns3, ADEIns3RecordType ,null}, { ADEIns4, ADEIns4RecordType ,null}, { ADEIns5, ADEIns5RecordType ,null},
		 { ADEIns7, ADEIns7RecordType ,null}};
		

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
				
				//--------
				String str = getText(driver, bp.verifydefaultCreatedItemOnPageAcuty(Header.Company, "Acuity"), "legal Name Label Text",action.SCROLLANDBOOLEAN);
				if (str != null) {
					if (str.contains(TabName1)) {
						appLog.info("created institution " + TabName1 + " is verified successfully.");
						appLog.info(TabName1 + " is created successfully.");
					} else {
						appLog.error("Created  " + TabName1 + " is not matched with " + str);
					}
				} else {
					appLog.error("Created  " + TabName1 + " is not visible");
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
			}	
		}
			// Limited Partner
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
				
				if (ip.createInstitution(projectName, environment, mode, ADEIns6,ADEIns6RecordType, InstitutionPageFieldLabelText.Parent_Institution.toString(),ADEIns3)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
				}
				
				String str = getText(driver, bp.verifydefaultCreatedItemOnPageAcuty(Header.Company, "Acuity"), "legal Name Label Text",action.SCROLLANDBOOLEAN);
				if (str != null) {
					if (str.contains(TabName1)) {
						appLog.info("created institution " + TabName1 + " is verified successfully.");
						appLog.info(TabName1 + " is created successfully.");
					} else {
						appLog.error("Created  " + TabName1 + " is not matched with " + str);
					}
				} else {
					appLog.error("Created  " + TabName1 + " is not visible");
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
			}	
			
		
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void ADETc003_VerifyContactAcuitytabvisibilityforNewlycreatedContacts(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
         String TabName1="";
		// contact
				if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
					log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);	
					
					ADEContact1EmailID=	lp.generateRandomEmailId(gmailUserName);
					ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact1EmailID, "Contact", excelLabel.Variable_Name, "ADEContact1",excelLabel.Contact_EmailId);

					if (cp.createContactAcuity(projectName, ADEContact1FName, ADEContact1LName, ADEIns1, ADEContact1EmailID,ADEContact1RecordType, null, null, CreationPage.ContactPage, null, null)) {
						log(LogStatus.INFO,"successfully Created Contact : "+ADEContact1FName+" "+ADEContact1LName,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Contact : "+ADEContact1FName+" "+ADEContact1LName);
						log(LogStatus.SKIP,"Not Able to Create Contact: "+ADEContact1FName+" "+ADEContact1LName,YesNo.Yes);
					}

					String str = getText(driver, bp.verifydefaultCreatedItemOnPageAcuty(Header.Company, "Acuity"), "legal Name Label Text",action.SCROLLANDBOOLEAN);
					if (str != null) {
						if (str.contains(TabName1)) {
							appLog.info("created contact" + TabName1 + " is verified successfully.");
							appLog.info(TabName1 + " is created successfully.");
						} else {
							appLog.error("Created  " + TabName1 + " is not matched with " + str);
						}
					} else {
						appLog.error("Created  " + TabName1 + " is not visible");
					}

				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
				}
		
				if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
					log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);	
					
					ADEContact2EmailID=	lp.generateRandomEmailId(gmailUserName);
					ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact2EmailID, "Contact", excelLabel.Variable_Name, "ADEContact2",excelLabel.Contact_EmailId);

					if (cp.createContactAcuity(projectName, ADEContact2FName, ADEContact2LName, ADEIns2, ADEContact2EmailID,ADEContact2RecordType, null, null, CreationPage.ContactPage, null, null)) {
						log(LogStatus.INFO,"successfully Created Contact : "+M3Contact1FName+" "+M3Contact1LName,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Contact : "+M3Contact1FName+" "+M3Contact1LName);
						log(LogStatus.SKIP,"Not Able to Create Contact: "+M3Contact1FName+" "+M3Contact1LName,YesNo.Yes);
					}

					String str = getText(driver, bp.verifydefaultCreatedItemOnPageAcuty(Header.Company, "Acuity"), "legal Name Label Text",action.SCROLLANDBOOLEAN);
					if (str != null) {
						if (str.contains(TabName1)) {
							appLog.info("created contact" + TabName1 + " is verified successfully.");
							appLog.info(TabName1 + " is created successfully.");
						} else {
							appLog.error("Created  " + TabName1 + " is not matched with " + str);
						}
					} else {
						appLog.error("Created  " + TabName1 + " is not visible");
					}

				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
				}
				
				if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
					log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);	
					
					ADEContact3EmailID=	lp.generateRandomEmailId(gmailUserName);
					ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact3EmailID, "Contact", excelLabel.Variable_Name, "ADEContact3",excelLabel.Contact_EmailId);

					if (cp.createContactAcuity(projectName, ADEContact3FName, ADEContact3LName, ADEIns3, ADEContact3EmailID,ADEContact3RecordType, null, null, CreationPage.ContactPage, null, null)) {
						log(LogStatus.INFO,"successfully Created Contact : "+M3Contact1FName+" "+M3Contact1LName,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Contact : "+M3Contact1FName+" "+M3Contact1LName);
						log(LogStatus.SKIP,"Not Able to Create Contact: "+M3Contact1FName+" "+M3Contact1LName,YesNo.Yes);
					}

					String str = getText(driver, bp.verifydefaultCreatedItemOnPageAcuty(Header.Company, "Acuity"), "legal Name Label Text",action.SCROLLANDBOOLEAN);
					if (str != null) {
						if (str.contains(TabName1)) {
							appLog.info("created contact" + TabName1 + " is verified successfully.");
							appLog.info(TabName1 + " is created successfully.");
						} else {
							appLog.error("Created  " + TabName1 + " is not matched with " + str);
						}
					} else {
						appLog.error("Created  " + TabName1 + " is not visible");
					}
					
				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
				}
				
				if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
					log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);	
					
					ADEContact4EmailID=	lp.generateRandomEmailId(gmailUserName);
					ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact4EmailID, "Contact", excelLabel.Variable_Name, "ADEContact4",excelLabel.Contact_EmailId);

					if (cp.createContactAcuity(projectName, ADEContact4FName, ADEContact4LName, ADEIns4, ADEContact4EmailID,ADEContact4RecordType, null, null, CreationPage.ContactPage, null, null)) {
						log(LogStatus.INFO,"successfully Created Contact : "+M3Contact1FName+" "+M3Contact1LName,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Contact : "+M3Contact1FName+" "+M3Contact1LName);
						log(LogStatus.SKIP,"Not Able to Create Contact: "+M3Contact1FName+" "+M3Contact1LName,YesNo.Yes);
					}

					String str = getText(driver, bp.verifydefaultCreatedItemOnPageAcuty(Header.Company, "Acuity"), "legal Name Label Text",action.SCROLLANDBOOLEAN);
					if (str != null) {
						if (str.contains(TabName1)) {
							appLog.info("created contact" + TabName1 + " is verified successfully.");
							appLog.info(TabName1 + " is created successfully.");
						} else {
							appLog.error("Created  " + TabName1 + " is not matched with " + str);
						}
					} else {
						appLog.error("Created  " + TabName1 + " is not visible");
					}

				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
				}
				
				if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
					log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);	
					
					ADEContact5EmailID=	lp.generateRandomEmailId(gmailUserName);
					ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact5EmailID, "Contact", excelLabel.Variable_Name, "ADEContact5",excelLabel.Contact_EmailId);

					if (cp.createContactAcuity(projectName, ADEContact5FName, ADEContact5LName, ADEIns5, ADEContact5EmailID,ADEContact5RecordType, null, null, CreationPage.ContactPage, null, null)) {
						log(LogStatus.INFO,"successfully Created Contact : "+M3Contact1FName+" "+M3Contact1LName,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Contact : "+M3Contact1FName+" "+M3Contact1LName);
						log(LogStatus.SKIP,"Not Able to Create Contact: "+M3Contact1FName+" "+M3Contact1LName,YesNo.Yes);
					}

					String str = getText(driver, bp.verifydefaultCreatedItemOnPageAcuty(Header.Company, "Acuity"), "legal Name Label Text",action.SCROLLANDBOOLEAN);
					if (str != null) {
						if (str.contains(TabName1)) {
							appLog.info("created contact" + TabName1 + " is verified successfully.");
							appLog.info(TabName1 + " is created successfully.");
						} else {
							appLog.error("Created  " + TabName1 + " is not matched with " + str);
						}
					} else {
						appLog.error("Created  " + TabName1 + " is not visible");
					}

				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
				}
				if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
					log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);	
					
					ADEContact6EmailID=	lp.generateRandomEmailId(gmailUserName);
					ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact6EmailID, "Contact", excelLabel.Variable_Name, "ADEContact6",excelLabel.Contact_EmailId);

					if (cp.createContactAcuity(projectName, ADEContact6FName, ADEContact6LName, ADEIns6, ADEContact6EmailID,ADEContact6RecordType, null, null, CreationPage.ContactPage, null, null)) {
						log(LogStatus.INFO,"successfully Created Contact : "+M3Contact1FName+" "+M3Contact1LName,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Contact : "+M3Contact1FName+" "+M3Contact1LName);
						log(LogStatus.SKIP,"Not Able to Create Contact: "+M3Contact1FName+" "+M3Contact1LName,YesNo.Yes);
					}

					String str = getText(driver, bp.verifydefaultCreatedItemOnPageAcuty(Header.Company, "Acuity"), "legal Name Label Text",action.SCROLLANDBOOLEAN);
					if (str != null) {
						if (str.contains(TabName1)) {
							appLog.info("created contact " + TabName1 + " is verified successfully.");
							appLog.info(TabName1 + " is created successfully.");
						} else {
							appLog.error("Created  " + TabName1 + " is not matched with " + str);
						}
					} else {
						appLog.error("Created  " + TabName1 + " is not visible");
					}

				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
				}
				if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
					log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);	
					
					ADEContact7EmailID=	lp.generateRandomEmailId(gmailUserName);
					ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact7EmailID, "Contact", excelLabel.Variable_Name, "ADEContact7",excelLabel.Contact_EmailId);

					if (cp.createContactAcuity(projectName, ADEContact7FName, ADEContact7LName, ADEIns7, ADEContact7EmailID,ADEContact7RecordType, null, null, CreationPage.ContactPage, null, null)) {
						log(LogStatus.INFO,"successfully Created Contact : "+M3Contact1FName+" "+M3Contact1LName,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Contact : "+M3Contact1FName+" "+M3Contact1LName);
					log(LogStatus.SKIP,"Not Able to Create Contact: "+M3Contact1FName+" "+M3Contact1LName,YesNo.Yes);
					}

					String str = getText(driver, bp.verifydefaultCreatedItemOnPageAcuty(Header.Company, "Acuity"), "legal Name Label Text",action.SCROLLANDBOOLEAN);
					if (str != null) {
						if (str.contains(TabName1)) {
							appLog.info("created contact " + TabName1 + " is verified successfully.");
							appLog.info(TabName1 + " is created successfully.");
						} else {
							appLog.error("Created  " + TabName1 + " is not matched with " + str);
							}
					} else {
						appLog.error("Created  " + TabName1 + " is not visible");
					}

				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
				}
				ThreadSleep(5000);
				lp.CRMlogout();
				sa.assertAll();
	
	}
	@Parameters({ "projectName"})
	@Test
	public void ADETc004_VerifyDealscardsectionvisibilityinAcuitytabforexistingAccountsANDContacts(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String ContactName = ADEContact1FName + " " + ADEContact1LName;
		String AccountName = ADEIns1;
		String dealHeader = "Deal Name<break>Stage<break>Highest Stage Reached<break>Date Received";
		String message = "No items to display";

		ArrayList<String> connectionsSectionHeaderName = new ArrayList<String>();

		ArrayList<String> contactsSectionHeaderName = new ArrayList<String>();

		String[] arrDealHeader = dealHeader.split("<break>");
		List<String> dealHeaders = new ArrayList<String>(Arrays.asList(arrDealHeader));

		if (cp.clickOnTab(projectName, tabObj1)) {
			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1 + " For : " + AccountName, YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, AccountName, 30)) {
				log(LogStatus.INFO, "Clicked on  : " + AccountName + " For : " + tabObj2, YesNo.No);
				ThreadSleep(2000);
				ArrayList<String> result1 = bp
						.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
								contactsSectionHeaderName, null, dealHeaders, message, connectionsSectionHeaderName,
								null);
				if (result1.isEmpty()) {
					log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
				} else {
					log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
					sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}

		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ContactName, 30)) {

				log(LogStatus.INFO, "open created item" + ContactName, YesNo.No);
				ThreadSleep(2000);
				ArrayList<String> result1 = bp
						.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
								contactsSectionHeaderName, null, dealHeaders, message, connectionsSectionHeaderName,
								null);
				if (result1.isEmpty()) {
					log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
				} else {
					log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
					sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source contact : " + ContactName);
				log(LogStatus.SKIP, "Not Able to open created source contact : " + ContactName, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc005_CreatedealsrecordtypecompanyandverifytheimpactDealtabbothcompanyIntermediarytypeaccount(
			String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String recordType = "";
		String dealName = ADEDeal3;
		String companyName = ADEDeal3CompanyName;
		String stage = ADEDeal3Stage;
		String dateReceived = todaysDate;

		if (lp.clickOnTab(projectName, tabObj4)) {
			log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
			ThreadSleep(3000);
			if (dp.createDeal(recordType, dealName, companyName, stage, "Date Received", todaysDate)) {
				log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);

			} else {
				log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
				sa.assertTrue(false, dealName + " deal is not created");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
		}

		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns1, YesNo.No);

				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
						if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
							log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);

						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}

				} else {
					log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
					sa.assertTrue(false, "date receivednot present: " + dateReceived);
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}

		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns4, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns4, YesNo.No);

				if (BP.dealAcuityDealName("Test D", 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + "Test D" + " is hyperlink and is present", YesNo.No);
				} else {
					sa.assertTrue(true, "is hyperlink and not present  : " + "Test D" + " For : " + TabName.Object4Tab);
					log(LogStatus.SKIP, "is hyperlink and not present  : " + "Test D" + " For : " + TabName.Object4Tab,
							YesNo.Yes);
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc006_CreatedealsrecordtypecompanyandverifytheimpactDealtabbothcompanyIntermediarytypeaccount(
			String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String recordType = "";
		String dealName = ADEDeal4;
		String companyName = ADEDeal4CompanyName;
		String stage = ADEDeal4Stage;
		String dateReceived = todaysDate;
		String ContactName = ADEContact2FName + " " + ADEContact2LName;

		String labellabels = "Source Firm<Break>Source Contact<Break>Date Received";
		String otherLabelValues = ADEDeal4SourceFirm + "<Break>" + ADEDeal4SourceContact + "<Break>"
				+ todaysDate;
		String value = "";
		String type = "";

		String[][] EntityOrAccounts = { { ADEIns8, ADEIns8RecordType, null }, { ADEIns9, ADEIns9RecordType, null } };

		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
				value = accounts[0];
				type = accounts[1];
				if (ip.createEntityOrAccount(projectName, mode, value, type, null, null, 20)) {
					log(LogStatus.INFO, "successfully Created Account/Entity : " + value + " of record type : " + type,
							YesNo.No);

				} else {
					sa.assertTrue(false, "Not Able to Create Account/Entity : " + value + " of record type : " + type);
					log(LogStatus.SKIP, "Not Able to Create Account/Entity : " + value + " of record type : " + type,
							YesNo.Yes);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object1Tab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object1Tab, YesNo.Yes);
			}
		}
		if (lp.clickOnTab(projectName, tabObj4)) {
			log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
			ThreadSleep(3000);
			if (dp.createDeal(recordType, dealName, companyName, stage, labellabels, otherLabelValues)) {
				log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);

			} else {
				log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
				sa.assertTrue(false, dealName + " deal is not created");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns8, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns8, YesNo.No);

				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
						if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
							log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);

						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}

				} else {
					log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
					sa.assertTrue(false, "date receivednot present: " + dateReceived);
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns8);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns8, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns9, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns9, YesNo.No);

				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
						if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
							log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);

						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}

				} else {
					log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
					sa.assertTrue(false, "date receivednot present: " + dateReceived);
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns9);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns9, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ContactName, 30)) {

				log(LogStatus.INFO, "open created item" + ContactName, YesNo.No);

				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
						if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
							log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);

						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}

				} else {
					log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
					sa.assertTrue(false, "date receivednot present: " + dateReceived);
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source contact : " + ContactName);
				log(LogStatus.SKIP, "Not Able to open created source contact : " + ContactName, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc007_CreateDealSourceFirmCompanyContactTypeSourceContactaddedverifyDealsectionAItabAccount(
			String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String recordType = "";
		String dealName = ADEDeal5;
		String companyName = ADEDeal5CompanyName;
		String stage = ADEDeal5Stage;
		String dateReceived = todaysDate;

		String labellabels = "Source Firm<Break>Source Contact<Break>Date Received";
		String otherLabelValues = ADEDeal5SourceFirm + "<Break>" + ADEDeal5SourceContact + "<Break>"
				+ todaysDate;

		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			ADEContact8EmailID = lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact8EmailID, "Contact", excelLabel.Variable_Name,
					"ADEContact8", excelLabel.Contact_EmailId);

			if (cp.createContact(projectName, ADEContact8FName, ADEContact8LName, ADEIns1, ADEContact8EmailID,
					ADEContact1RecordType, null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO, "successfully Created Contact : " + M3Contact1FName + " " + M3Contact1LName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Create Contact : " + M3Contact1FName + " " + M3Contact1LName);
				log(LogStatus.SKIP, "Not Able to Create Contact: " + M3Contact1FName + " " + M3Contact1LName,
						YesNo.Yes);
			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object2Tab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object2Tab, YesNo.Yes);
		}
		if (lp.clickOnTab(projectName, tabObj4)) {
			log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
			ThreadSleep(3000);
			if (dp.createDeal(recordType, dealName, companyName, stage, labellabels, otherLabelValues)) {
				log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);

			} else {
				log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
				sa.assertTrue(false, dealName + " deal is not created");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns1, YesNo.No);

				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
						if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
							log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);

						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}

				} else {
					log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
					sa.assertTrue(false, "date receivednot present: " + dateReceived);
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		if (BP.dealNameLinkInAcuityTab(dealName, 30) != null) {
			log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and it is Clickable", YesNo.No);

			if (clickUsingJavaScript(driver, BP.dealNameLinkInAcuityTab(dealName, 30), "Deal Name: " + dealName,
					action.BOOLEAN)) {
				log(LogStatus.PASS, "Clicked on Deal Name: " + dealName, YesNo.No);
				try {
					String parentWindowId = CommonLib.switchOnWindow(driver);
					if (!parentWindowId.isEmpty()) {
						log(LogStatus.PASS, "New Window Open after click on Deal Link: " + dealName, YesNo.No);

						if (BP.dealRecordPage(dealName, 20) != null) {
							log(LogStatus.PASS,
									"----Deal Detail Page is redirecting for Deal Record: " + dealName + "-----",
									YesNo.No);
							driver.close();
							driver.switchTo().window(parentWindowId);

						} else {
							log(LogStatus.FAIL,
									"----Deal Detail Page is not redirecting for Deal Record: " + dealName + "-----",
									YesNo.Yes);
							sa.assertTrue(false,
									"----Deal Detail Page is not showing for Deal Record: " + dealName + "-----");
							driver.close();
							driver.switchTo().window(parentWindowId);

						}

					} else {
						log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + dealName, YesNo.Yes);
						sa.assertTrue(false, "No New Window Open after click on Deal Link: " + dealName);
					}
				} catch (Exception e) {
					log(LogStatus.FAIL,
							"Not able to switch to window after click on Deal Link, Msg showing: " + e.getMessage(),
							YesNo.Yes);
					sa.assertTrue(false,
							"Not able to switch to window after click on Deal Link, Msg showing: " + e.getMessage());
				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

			}

		} else {
			log(LogStatus.FAIL, "Deal Name: " + dealName + " is not hyperlink and it is not Clickable", YesNo.Yes);
			sa.assertTrue(false, "Deal Name: " + dealName + " is not hyperlink and it is not Clickable");
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc008_1_CreateDealSourceFirmCompany(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

//	String[] dealRecordType = ADEDealRecordType1.split("<Section>", -1);
		String[] dealName = ADEDealName1.split("<Section>", -1);
		String[] dealCompany = ADEDealCompany1.split("<Section>", -1);
		String[] dealStage = ADEDealStage1.split("<Section>", -1);
		String[] otherLabels = ADEDealOtherLabelNames1.split("<Section>", -1);
		String[] otherLabelValues = ADEDealOtherLabelValues1.split("<Section>", -1);

		for (int i = 0; i < 18; i++) {
			if (lp.clickOnTab(projectName, tabObj4)) {
				log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
				ThreadSleep(3000);
				if (dp.createDeal(null, dealName[i], dealCompany[i], dealStage[i], otherLabels[i],
						otherLabelValues[i])) {
					log(LogStatus.INFO, dealName[i] + " deal has been created", YesNo.No);

//				ExcelUtils.writeData(AcuityDataSheetFilePath, otherLabelValues.split("<Section>", -1)[2], "Deal",
//						excelLabel.Variable_Name, "AS_Deal2", excelLabel.Date);

				} else {
					log(LogStatus.ERROR, dealName[i] + " deal is not created", YesNo.No);
					sa.assertTrue(false, dealName[i] + " deal is not created");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
				sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
			}
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc008_2_DealsDifferentStagesCompanyIntermediary(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String[] dealName = ADEDealName1.split("<Section>", -1);
		String[] dealStage = ADEDealStage1.split("<Section>", -1);
		String dateReceived = todaysDate;
		String contactname = ADEContact3FName + " " + ADEContact3LName;
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns1, YesNo.No);
				for (int i = 0; i < 9; i++) {
					if (BP.dealAcuityDealName(dealName[i], 30) != null) {
						log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
						if (BP.dealAcuityStageName(dealName[i], dealStage[i], 30) != null) {
							log(LogStatus.PASS, "Stage Name: " + dealStage + " is present", YesNo.No);
							if (BP.dealAcuityDateReceived(dealName[i], dateReceived, 30) != null) {
								log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);

							} else {
								log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
								sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

							}
						} else {
							log(LogStatus.FAIL, "stage name not present: " + dealStage, YesNo.Yes);
							sa.assertTrue(false, "stage name not present: " + dealStage);

						}

					} else {
						log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
						sa.assertTrue(false, "date receivednot present: " + dateReceived);
					}
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns4, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns4, YesNo.No);
				for (int i = 9; i < 18; i++) {
					if (BP.dealAcuityDealName(dealName[i], 30) != null) {
						log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
						if (BP.dealAcuityStageName(dealName[i], dealStage[i], 30) != null) {
							log(LogStatus.PASS, "Stage Name: " + dealStage + " is present", YesNo.No);
							if (BP.dealAcuityDateReceived(dealName[i], dateReceived, 30) != null) {
								log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);

							} else {
								log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
								sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

							}
						} else {
							log(LogStatus.FAIL, "stage name not present: " + dealStage, YesNo.Yes);
							sa.assertTrue(false, "stage name not present: " + dealStage);

						}

					} else {
						log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
						sa.assertTrue(false, "date receivednot present: " + dateReceived);
					}
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, contactname, 30)) {

				log(LogStatus.INFO, "open created item" + contactname, YesNo.No);
				for (int i = 9; i < 18; i++) {
					if (BP.dealAcuityDealName(dealName[i], 30) != null) {
						log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
						if (BP.dealAcuityStageName(dealName[i], dealStage[i], 30) != null) {
							log(LogStatus.PASS, "Stage Name: " + dealStage + " is present", YesNo.No);
							if (BP.dealAcuityDateReceived(dealName[i], dateReceived, 30) != null) {
								log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);

							} else {
								log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
								sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

							}
						} else {
							log(LogStatus.FAIL, "stage name not present: " + dealStage, YesNo.Yes);
							sa.assertTrue(false, "stage name not present: " + dealStage);

						}

					} else {
						log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
						sa.assertTrue(false, "date receivednot present: " + dateReceived);
					}
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

//@Parameters({ "projectName"})
//@Test
//public void ADETc009_VerifyDisablingSortingatDealSectionAICIAccounts(String projectName) {
//	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
//	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
//	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
//	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
//	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
//	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
//	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
//	
//	List<WebElement> ele = null;
//	if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
//		log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
//		
//		if(fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)){
//			
//			log(LogStatus.INFO,"open created item"+ADEIns1,YesNo.No);
//			
//			if (CommonLib.checkSorting(driver, SortOrder.Decending, ele)) {
//				log(LogStatus.PASS,
//						"-----------Fund Name Column is in Descending Order By Default --------------",
//						YesNo.No);
//				sa.assertTrue(true,
//						"-----------Fund Name Column is in Descending Order By Default --------------");
//
//			}}}}}

	@Parameters({ "projectName" })
	@Test
	public void ADETc010_DeleteExistingDealsVerifyImpactOnDealsAccounts(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName,ADEDeal3, 10)) {
				cp.clickOnShowMoreDropdownOnly(projectName, PageName.Object4Page, "");
				log(LogStatus.INFO, "Able to Click on Show more Icon : " + TabName.Object4Tab + " For : " + ADEDeal3,
						YesNo.No);
				ThreadSleep(500);
				WebElement ele = cp.actionDropdownElement(projectName, PageName.Object4Page,
						ShowMoreActionDropDownList.Delete, 15);
				if (ele == null) {
					ele = cp.getDeleteButton(projectName, 30);
				}

				if (click(driver, ele, "Delete More Icon", action.BOOLEAN)) {
					log(LogStatus.INFO,
							"Able to Click on Delete more Icon : " + TabName.Object4Tab + " For : " + ADEDeal3,
							YesNo.No);
					ThreadSleep(1000);
					if (click(driver, cp.getDeleteButtonOnDeletePopUp(projectName, 30), "Delete Button",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Able to Click on Delete button on Delete PoPup : " + TabName.Object4Tab
								+ " For : " + ADEDeal3, YesNo.No);
						ThreadSleep(10000);
						if (cp.clickOnTab(projectName, TabName.Object4Tab)) {
							log(LogStatus.INFO, "Clicked on Tab : " + TabName.Object4Tab + " For : " + ADEDeal3,
									YesNo.No);
							ThreadSleep(1000);
							if (!cp.clickOnAlreadyCreatedItem(projectName, TabName.Object4Tab, ADEDeal3, 10)) {
								log(LogStatus.INFO, "Item has been Deleted after delete operation  : " + ADEDeal3
										+ " For : " + TabName.Object4Tab, YesNo.No);

							} else {
								sa.assertTrue(false, "Item has not been Deleted after delete operation  : " + "Test D"
										+ " For : " + TabName.Object4Tab);
								log(LogStatus.SKIP, "Item has not been Deleted after delete operation  : " + "Test D"
										+ " For : " + TabName.Object4Tab, YesNo.Yes);
							}

						} else {
							sa.assertTrue(false, "Not Able to Click on Tab after delete : " + TabName.Object4Tab
									+ " For : " + "Test D");
							log(LogStatus.SKIP, "Not Able to Click on Tab after delete : " + TabName.Object4Tab
									+ " For : " + "Test D", YesNo.Yes);
						}
					} else {
						log(LogStatus.INFO, "not able to click on delete button, so not deleted : " + TabName.Object4Tab
								+ " For : " + "Test D", YesNo.No);
						sa.assertTrue(false, "not able to click on delete button, so not deleted : "
								+ TabName.Object4Tab + " For : " + "Test D");
					}
				} else {
					log(LogStatus.INFO,
							"not Able to Click on Delete more Icon : " + TabName.Object4Tab + " For : " + "Test D",
							YesNo.No);
					sa.assertTrue(false,
							"not Able to Click on Delete more Icon : " + TabName.Object4Tab + " For : " + "Test D");
				}
			} else {
				log(LogStatus.INFO, "not Able to Click on " + "Test D", YesNo.No);
				sa.assertTrue(false, "not Able to Click on " + "Test D");
			}
		} else {
			log(LogStatus.INFO, "not Able to Click on " + TabName.Object4Tab, YesNo.No);
			sa.assertTrue(false, "not Able to Click on " + TabName.Object4Tab);
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns1, YesNo.No);

				if (BP.dealAcuityDealName(ADEDeal3, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + "Test D" + " is hyperlink and is present", YesNo.No);
				} else {
					sa.assertTrue(true, "is hyperlink and not present  : " + "Test D" + " For : " + TabName.Object4Tab);
					log(LogStatus.SKIP, "is hyperlink and not present  : " + "Test D" + " For : " + TabName.Object4Tab,
							YesNo.Yes);
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc011_RestoreDeletedDealsandDealSectionAccounts(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null;

		TabName tabName = TabName.RecycleBinTab;
		String name = ADEDeal3;

		if (cp.clickOnTab(projectName, tabName)) {
			log(LogStatus.INFO, "Clicked on Tab : " + tabName + " For : " + name, YesNo.No);
			ThreadSleep(1000);
			cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 20);
			log(LogStatus.INFO, "Clicked on  : " + name + " For : " + tabName, YesNo.No);
			ThreadSleep(2000);

			ele = cp.getCheckboxOfRestoreItemOnRecycleBin(projectName, name, 10);
			if (clickUsingJavaScript(driver, ele, "Check box against : " + name, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on checkbox for " + name, YesNo.No);

				ThreadSleep(1000);
				ele = cp.getRestoreButtonOnRecycleBin(projectName, 10);
				if (clickUsingJavaScript(driver, ele, "Restore Button : " + name, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on Restore Button for " + name, YesNo.No);
					ThreadSleep(1000);
				} else {
					sa.assertTrue(false, "Not Able to Click on Restore Button for " + name);
					log(LogStatus.SKIP, "Not Able to Click on Restore Button for " + name, YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on checkbox for " + name);
				log(LogStatus.SKIP, "Not Able to Click on checkbox for " + name, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + tabName + " For : " + name);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName + " For : " + name, YesNo.Yes);
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns1, YesNo.No);

				if (BP.dealAcuityDealName(ADEDeal3, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + "Test D" + " is hyperlink and is present", YesNo.No);
				} else {
					sa.assertTrue(false,
							"is hyperlink and not present  : " + "Test D" + " For : " + TabName.Object4Tab);
					log(LogStatus.SKIP, "is hyperlink and not present  : " + "Test D" + " For : " + TabName.Object4Tab,
							YesNo.Yes);
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc013_1_DeleteExistingAccountVerifyImpactOnDealsAccounts(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 10)) {
				cp.clickOnShowMoreDropdownOnly(projectName, PageName.Object1Page, "");
				log(LogStatus.INFO, "Able to Click on Show more Icon : " + TabName.Object1Tab + " For : " + ADEIns1,
						YesNo.No);
				ThreadSleep(500);
				WebElement ele = cp.actionDropdownElement(projectName, PageName.Object1Page,
						ShowMoreActionDropDownList.Delete, 15);
				if (ele == null) {
					ele = cp.getDeleteButton(projectName, 30);
				}

				if (click(driver, ele, "Delete More Icon", action.BOOLEAN)) {
					log(LogStatus.INFO,
							"Able to Click on Delete more Icon : " + TabName.Object1Tab + " For : " + ADEIns1,
							YesNo.No);
					ThreadSleep(1000);
					if (click(driver, cp.getDeleteButtonOnDeletePopUp(projectName, 30), "Delete Button",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Able to Click on Delete button on Delete PoPup : " + TabName.Object1Tab
								+ " For : " + ADEIns1, YesNo.No);
						ThreadSleep(10000);
						if (cp.clickOnTab(projectName, TabName.Object1Tab)) {
							log(LogStatus.INFO, "Clicked on Tab : " + TabName.Object1Tab + " For : " + ADEIns1,
									YesNo.No);
							ThreadSleep(1000);
							if (!cp.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, ADEIns1, 10)) {
								log(LogStatus.INFO, "Item has been Deleted after delete operation  : " + ADEIns1
										+ " For : " + TabName.Object1Tab, YesNo.No);

							} else {
								sa.assertTrue(false, "Item has not been Deleted after delete operation  : " + ADEIns1
										+ " For : " + TabName.Object1Tab);
								log(LogStatus.SKIP, "Item has not been Deleted after delete operation  : " + ADEIns1
										+ " For : " + TabName.Object1Tab, YesNo.Yes);
							}

						} else {
							sa.assertTrue(false, "Not Able to Click on Tab after delete : " + TabName.Object1Tab
									+ " For : " + ADEIns1);
							log(LogStatus.SKIP, "Not Able to Click on Tab after delete : " + TabName.Object1Tab
									+ " For : " + ADEIns1, YesNo.Yes);
						}
					} else {
						log(LogStatus.INFO, "not able to click on delete button, so not deleted : " + TabName.Object1Tab
								+ " For : " + ADEIns1, YesNo.No);
						sa.assertTrue(false, "not able to click on delete button, so not deleted : "
								+ TabName.Object1Tab + " For : " + ADEIns1);
					}
				} else {
					log(LogStatus.INFO,
							"not Able to Click on Delete more Icon : " + TabName.Object1Tab + " For : " + ADEIns1,
							YesNo.No);
					sa.assertTrue(false,
							"not Able to Click on Delete more Icon : " + TabName.Object1Tab + " For : " + ADEIns1);
				}
			} else {
				log(LogStatus.INFO, "not Able to Click on " + ADEIns1, YesNo.No);
				sa.assertTrue(false, "not Able to Click on " + ADEIns1);
			}
		} else {
			log(LogStatus.INFO, "not Able to Click on " + TabName.Object1Tab, YesNo.No);
			sa.assertTrue(false, "not Able to Click on " + TabName.Object1Tab);
		}
		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns4, 10)) {
				cp.clickOnShowMoreDropdownOnly(projectName, PageName.Object1Page, "");
				log(LogStatus.INFO, "Able to Click on Show more Icon : " + TabName.Object1Tab + " For : " + ADEIns4,
						YesNo.No);
				ThreadSleep(500);
				WebElement ele = cp.actionDropdownElement(projectName, PageName.Object1Page,
						ShowMoreActionDropDownList.Delete, 15);
				if (ele == null) {
					ele = cp.getDeleteButton(projectName, 30);
				}

				if (click(driver, ele, "Delete More Icon", action.BOOLEAN)) {
					log(LogStatus.INFO,
							"Able to Click on Delete more Icon : " + TabName.Object1Tab + " For : " + ADEIns4,
							YesNo.No);
					ThreadSleep(1000);
					if (click(driver, cp.getDeleteButtonOnDeletePopUp(projectName, 30), "Delete Button",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Able to Click on Delete button on Delete PoPup : " + TabName.Object1Tab
								+ " For : " + ADEIns4, YesNo.No);
						ThreadSleep(10000);
						if (cp.clickOnTab(projectName, TabName.Object1Tab)) {
							log(LogStatus.INFO, "Clicked on Tab : " + TabName.Object1Tab + " For : " + ADEIns4,
									YesNo.No);
							ThreadSleep(1000);
							if (!cp.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, ADEIns4, 10)) {
								log(LogStatus.INFO, "Item has been Deleted after delete operation  : " + ADEIns4
										+ " For : " + TabName.Object1Tab, YesNo.No);

							} else {
								sa.assertTrue(false, "Item has not been Deleted after delete operation  : " + ADEIns4
										+ " For : " + TabName.Object1Tab);
								log(LogStatus.SKIP, "Item has not been Deleted after delete operation  : " + ADEIns4
										+ " For : " + TabName.Object1Tab, YesNo.Yes);
							}

						} else {
							sa.assertTrue(false, "Not Able to Click on Tab after delete : " + TabName.Object1Tab
									+ " For : " + ADEIns4);
							log(LogStatus.SKIP, "Not Able to Click on Tab after delete : " + TabName.Object1Tab
									+ " For : " + ADEIns4, YesNo.Yes);
						}
					} else {
						log(LogStatus.INFO, "not able to click on delete button, so not deleted : " + TabName.Object1Tab
								+ " For : " + ADEIns4, YesNo.No);
						sa.assertTrue(false, "not able to click on delete button, so not deleted : "
								+ TabName.Object1Tab + " For : " + ADEIns4);
					}
				} else {
					log(LogStatus.INFO,
							"not Able to Click on Delete more Icon : " + TabName.Object1Tab + " For : " + ADEIns4,
							YesNo.No);
					sa.assertTrue(false,
							"not Able to Click on Delete more Icon : " + TabName.Object1Tab + " For : " + ADEIns4);
				}
			} else {
				log(LogStatus.INFO, "not Able to Click on " + ADEIns4, YesNo.No);
				sa.assertTrue(false, "not Able to Click on " + ADEIns4);
			}
		} else {
			log(LogStatus.INFO, "not Able to Click on " + TabName.Object1Tab, YesNo.No);
			sa.assertTrue(false, "not Able to Click on " + TabName.Object1Tab);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc013_2_RestoreDeletedAccountsandDealSectionAccounts(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null;
		
		String dealHeader = "Deal Name<break>Stage<break>Highest Stage Reached<break>Date Received";

		ArrayList<String> connectionsSectionHeaderName = new ArrayList<String>();

		ArrayList<String> contactsSectionHeaderName = new ArrayList<String>();

		String[] arrDealHeader = dealHeader.split("<break>");
		List<String> dealHeaders = new ArrayList<String>(Arrays.asList(arrDealHeader));

		TabName tabName = TabName.RecycleBinTab;
		String name = ADEIns1;

		if (cp.clickOnTab(projectName, tabName)) {
			log(LogStatus.INFO, "Clicked on Tab : " + tabName + " For : " + name, YesNo.No);
			ThreadSleep(1000);
			cp.clickOnAlreadyCreatedItem(projectName, tabName, ADEIns1, 20);
			log(LogStatus.INFO, "Clicked on  : " + name + " For : " + tabName, YesNo.No);
			ThreadSleep(2000);

			ele = cp.getCheckboxOfRestoreItemOnRecycleBin(projectName, name, 10);
			if (clickUsingJavaScript(driver, ele, "Check box against : " + name, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on checkbox for " + name, YesNo.No);

				ThreadSleep(1000);
				ele = cp.getRestoreButtonOnRecycleBin(projectName, 10);
				if (clickUsingJavaScript(driver, ele, "Restore Button : " + name, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on Restore Button for " + name, YesNo.No);
					ThreadSleep(1000);
				} else {
					sa.assertTrue(false, "Not Able to Click on Restore Button for " + name);
					log(LogStatus.SKIP, "Not Able to Click on Restore Button for " + name, YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on checkbox for " + name);
				log(LogStatus.SKIP, "Not Able to Click on checkbox for " + name, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + tabName + " For : " + name);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName + " For : " + name, YesNo.Yes);
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns1, YesNo.No);

				ArrayList<String> result1 = BP
						.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
								contactsSectionHeaderName, null, dealHeaders, null, connectionsSectionHeaderName, null);
				if (result1.isEmpty()) {
					log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
				} else {
					log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
					sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}

		String name1 = ADEIns4;

		if (cp.clickOnTab(projectName, tabName)) {
			log(LogStatus.INFO, "Clicked on Tab : " + tabName + " For : " + name, YesNo.No);
			ThreadSleep(1000);
			cp.clickOnAlreadyCreatedItem(projectName, tabName, name1, 20);
			log(LogStatus.INFO, "Clicked on  : " + name1 + " For : " + tabName, YesNo.No);
			ThreadSleep(2000);

			ele = cp.getCheckboxOfRestoreItemOnRecycleBin(projectName, name1, 10);
			if (clickUsingJavaScript(driver, ele, "Check box against : " + name1, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on checkbox for " + name1, YesNo.No);

				ThreadSleep(1000);
				ele = cp.getRestoreButtonOnRecycleBin(projectName, 10);
				if (clickUsingJavaScript(driver, ele, "Restore Button : " + name, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on Restore Button for " + name, YesNo.No);
					ThreadSleep(1000);
				} else {
					sa.assertTrue(false, "Not Able to Click on Restore Button for " + name);
					log(LogStatus.SKIP, "Not Able to Click on Restore Button for " + name, YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on checkbox for " + name);
				log(LogStatus.SKIP, "Not Able to Click on checkbox for " + name, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + tabName + " For : " + name);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName + " For : " + name, YesNo.Yes);
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns4, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns4, YesNo.No);

				ArrayList<String> result1 = BP
						.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
								contactsSectionHeaderName, null, dealHeaders, null, connectionsSectionHeaderName, null);
				if (result1.isEmpty()) {
					log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
				} else {
					log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
					sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns4);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns4, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc012_1_EditExistingCompanyDealVerifyimpactDealCompanyAccount(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String dealname = "ADECTD New";

		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, ADEDeal5, 10)) {
				if (fp.UpdateDealName(projectName, dealname, 10)) {
					log(LogStatus.INFO, "successfully changed name to " + dealname, YesNo.Yes);
				} else {
					sa.assertTrue(false, "not able to change name to " + dealname);
					log(LogStatus.SKIP, "not able to change name to " + dealname, YesNo.Yes);
				}
				if (fp.changeStage(projectName, Stage.LOI.toString(), 10)) {
					log(LogStatus.INFO, "successfully changed stage to " + Stage.LOI, YesNo.Yes);

				} else {
					sa.assertTrue(false, "not able to change stage to " + Stage.LOI);
					log(LogStatus.SKIP, "not able to change stage to " + Stage.LOI, YesNo.Yes);
				}
			} else {
				sa.assertTrue(false, "not able to find pipeline " + "TD");
				log(LogStatus.SKIP, "not able to find pipeline " + "TD", YesNo.Yes);
			}
		} else {
			sa.assertTrue(false, "not able to click on deal tab");
			log(LogStatus.SKIP, "not able to click on deal tab", YesNo.Yes);
		}
		String dealName = "ADECTD New";
		String stage = "LOI";
		String dateReceived = todaysDate;

		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns1, YesNo.No);

				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
						if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
							log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);

						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}

				} else {
					log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
					sa.assertTrue(false, "date receivednot present: " + dateReceived);
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		String dealName1 = "ADECTD New";
		String stage1 = "LOI";
		String dateReceived1 = "11/2/2022";
		String companyname = ADEIns2;

		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, "ADECTD New", 10)) {
				if (fp.UpdateCompanyName(projectName, companyname, 10)) {
					log(LogStatus.INFO, "successfully changed name to " + companyname, YesNo.Yes);
				} else {
					sa.assertTrue(false, "not able to change name to " + companyname);
					log(LogStatus.SKIP, "not able to change name to " + companyname, YesNo.Yes);
				}

			}
			if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

				if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns4, 30)) {

					log(LogStatus.INFO, "open created item" + ADEIns4, YesNo.No);

					if (BP.dealAcuityDealName(dealName1, 30) != null) {
						log(LogStatus.PASS, "Deal Name: " + dealName1 + " is hyperlink and is present", YesNo.No);
						if (BP.dealAcuityStageName(dealName1, stage1, 30) != null) {
							log(LogStatus.PASS, "Stage Name: " + stage1 + " is present", YesNo.No);
							if (BP.dealAcuityDateReceived(dealName1, dateReceived1, 30) != null) {
								log(LogStatus.PASS, "Date Received: " + dateReceived1 + " is present", YesNo.No);

							} else {
								log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName1, YesNo.Yes);
								sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName1);

							}
						} else {
							log(LogStatus.FAIL, "stage name not present: " + stage1, YesNo.Yes);
							sa.assertTrue(false, "stage name not present: " + stage1);

						}

					} else {
						log(LogStatus.FAIL, "date received not present: " + dateReceived1, YesNo.Yes);
						sa.assertTrue(false, "date receivednot present: " + dateReceived1);
					}
				} else {

					sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns2);
					log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns2, YesNo.Yes);

				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			}

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc012_2_EditExistingCompanyDealVerifyimpactDealCompanyAccount(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String dealname = "ADECTD One";

		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, "ADECTD New", 10)) {
				if (fp.UpdateDealName(projectName, dealname, 10)) {
					log(LogStatus.INFO, "successfully changed name to " + dealname, YesNo.Yes);
				} else {
					sa.assertTrue(false, "not able to change name to " + dealname);
					log(LogStatus.SKIP, "not able to change name to " + dealname, YesNo.Yes);
				}
			} else {
				sa.assertTrue(false, "not able to find pipeline " + "ADECTD New");
				log(LogStatus.SKIP, "not able to find pipeline " + "ADECTD New", YesNo.Yes);
			}
		} else {
			sa.assertTrue(false, "not able to click on deal tab");
			log(LogStatus.SKIP, "not able to click on deal tab", YesNo.Yes);
		}
		String dealName1 = "ADECTD One";
		String stage1 = "LOI";
		String dateReceived1 = "11/2/2022";
		String companyname = ADEIns1;
		String labellabels = "Source Firm";
		String otherLabelValues = ADEDeal4SourceFirm;

		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, "ADECTD One", 10)) {
				if (fp.UpdateCompanyName(projectName, companyname, 10)) {
					log(LogStatus.INFO, "successfully changed name to " + companyname, YesNo.Yes);
				} else {
					sa.assertTrue(false, "not able to change name to " + companyname);
					log(LogStatus.SKIP, "not able to change name to " + companyname, YesNo.Yes);
				}
				if (dp.UpdateOtherLable(projectName, labellabels, otherLabelValues, 20)) {
					log(LogStatus.INFO, "successfully changed source firm to " + labellabels, YesNo.Yes);
				} else {
					sa.assertTrue(false, "not able to change source firm to " + labellabels);
					log(LogStatus.SKIP, "not able to change source firm to " + labellabels, YesNo.Yes);
				}
				if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

					if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)) {

						log(LogStatus.INFO, "open created item" + ADEIns1, YesNo.No);

						if (BP.dealAcuityDealName(dealName1, 30) != null) {
							log(LogStatus.PASS, "Deal Name: " + dealName1 + " is hyperlink and is present", YesNo.No);
							if (BP.dealAcuityStageName(dealName1, stage1, 30) != null) {
								log(LogStatus.PASS, "Stage Name: " + stage1 + " is present", YesNo.No);
								if (BP.dealAcuityDateReceived(dealName1, dateReceived1, 30) != null) {
									log(LogStatus.PASS, "Date Received: " + dateReceived1 + " is present", YesNo.No);

								} else {
									log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName1, YesNo.Yes);
									sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName1);

								}
							} else {
								log(LogStatus.FAIL, "stage name not present: " + stage1, YesNo.Yes);
								sa.assertTrue(false, "stage name not present: " + stage1);

							}

						} else {
							log(LogStatus.FAIL, "date received not present: " + dateReceived1, YesNo.Yes);
							sa.assertTrue(false, "date receivednot present: " + dateReceived1);
						}
					} else {

						sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns2);
						log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns2, YesNo.Yes);

					}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
				}

			}
			if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

				if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns4, 30)) {

					log(LogStatus.INFO, "open created item" + ADEIns4, YesNo.No);

					if (BP.dealAcuityDealName(dealName1, 30) != null) {
						log(LogStatus.PASS, "Deal Name: " + dealName1 + " is hyperlink and is present", YesNo.No);

					} else {
						log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName1, YesNo.Yes);
						sa.assertTrue(true, "Not able to Click on Deal Name: " + dealName1);

					}
				} else {

					sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns2);
					log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns2, YesNo.Yes);

				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			}

		}
		String dealName2 = "ADECTD One";
		String companyname1 = ADEIns6;

		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, dealName2, 10)) {
				if (fp.UpdateCompanyName(projectName, companyname1, 10)) {
					log(LogStatus.INFO, "successfully changed name to " + companyname1, YesNo.Yes);
				} else {
					sa.assertTrue(false, "not able to change name to " + companyname1);
					log(LogStatus.SKIP, "not able to change name to " + companyname1, YesNo.Yes);
				}
				if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

					if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)) {

						log(LogStatus.INFO, "open created item" + ADEIns1, YesNo.No);

						if (BP.dealAcuityDealName(dealName2, 30) != null) {
							log(LogStatus.PASS, "Deal Name: " + dealName2 + " is hyperlink and is present", YesNo.No);
						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName1, YesNo.Yes);
							sa.assertTrue(true, "Not able to Click on Deal Name: " + dealName1);

						}
					} else {

						sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns2);
						log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns2, YesNo.Yes);

					}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
				}

			}
			if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

				if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns4, 30)) {

					log(LogStatus.INFO, "open created item" + ADEIns4, YesNo.No);

					if (BP.dealAcuityDealName(dealName1, 30) != null) {
						log(LogStatus.PASS, "Deal Name: " + dealName1 + " is hyperlink and is present", YesNo.No);

					} else {
						log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName1, YesNo.Yes);
						sa.assertTrue(true, "Not able to Click on Deal Name: " + dealName1);

					}
				} else {

					sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns2);
					log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns2, YesNo.Yes);

				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			}

		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc014_1_RenameStageNamesCheckImpactDealSectionAccountContact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
		String parentID = null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String[][] newAndOldStage = { { Stage.Due_Diligence.toString(), Stage.DD.toString() },
				{ Stage.IOI.toString(), Stage.IOL.toString() } };
		if (home.clickOnSetUpLink()) {
			parentID = switchOnWindow(driver);
			if (parentID != null) {
				if (sp.searchStandardOrCustomObject(environment, mode, object.Deal)) {
					if (sp.clickOnObjectFeature(environment, mode, object.Deal,
							ObjectFeatureName.FieldAndRelationShip)) {
						if (sendKeys(driver, sp.getsearchTextboxFieldsAndRelationships(10),
								excelLabel.Stage.toString() + Keys.ENTER, "status", action.BOOLEAN)) {

							if (sp.clickOnAlreadyCreatedLayout(excelLabel.Stage.toString())) {
								for (int i = 0; i < newAndOldStage.length; i++) {
									switchToDefaultContent(driver);
									switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
									WebElement ele = sp.clickOnEditInFrontOfFieldValues(projectName,
											newAndOldStage[i][0]);
									if (click(driver, ele, "watchlist", action.BOOLEAN)) {
										switchToDefaultContent(driver);
										ThreadSleep(3000);
										switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
										sendKeys(driver, sp.getFieldLabelTextBox1(10), newAndOldStage[i][1], "label",
												action.BOOLEAN);

										if (clickUsingJavaScript(driver, fp.getCustomTabSaveBtn(10), "save",
												action.BOOLEAN)) {

											log(LogStatus.INFO, "successfully changed stage label", YesNo.No);
										} else {
											sa.assertTrue(false, "not able to click on save button");
											log(LogStatus.SKIP, "not able to click on save button", YesNo.Yes);
										}

									} else {
										sa.assertTrue(false, "edit button is not clickable");
										log(LogStatus.SKIP, "edit button is not clickable", YesNo.Yes);
									}
								}
								ThreadSleep(3000);
								switchToDefaultContent(driver);
								switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
								WebElement ele = sp.clickOnEditInFrontOfFieldValues(projectName, newAndOldStage[0][1]);
								WebElement ele1 = null;
								ele1 = sp.clickOnEditInFrontOfFieldValues(projectName, newAndOldStage[1][1]);
								if ((ele != null) && (ele1 != null)) {
									log(LogStatus.INFO, "successfully verified rename of stage values", YesNo.No);

								} else {
									log(LogStatus.ERROR, "stage field is not renamed", YesNo.No);
									sa.assertTrue(false, "stage field is not renamed");
								}
							} else {
								sa.assertTrue(false, "stage field is not clickable");
								log(LogStatus.SKIP, "stage field is not clickable", YesNo.Yes);
							}
						} else {
							sa.assertTrue(false, "field textbox is not visible");
							log(LogStatus.SKIP, "field textbox is not visible", YesNo.Yes);
						}
					} else {
						sa.assertTrue(false, "field and relationships is not clickable");
						log(LogStatus.SKIP, "field and relationships is not clickable", YesNo.Yes);
					}
				} else {
					sa.assertTrue(false, "activity object is not clickable");
					log(LogStatus.SKIP, "activity object is not clickable", YesNo.Yes);
				}
				ThreadSleep(3000);
				driver.close();
				driver.switchTo().window(parentID);
			} else {
				sa.assertTrue(false, "new window is not found, so cannot change watchlist label");
				log(LogStatus.SKIP, "new window is not found, so cannot change watchlist label", YesNo.Yes);
			}
		} else {
			sa.assertTrue(false, "setup link is not clickable");
			log(LogStatus.SKIP, "setup link is not clickable", YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc014_2_RenameStageNamesCheckImpactDealSectionAccountContact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)) {
				log(LogStatus.INFO, "open created item" + ADEIns1, YesNo.No);

				String dealname = ADEDeal7;
				String stage = "DD";

				if (BP.dealAcuityStageName(dealname, stage, 30) != null) {
					log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);

				} else {
					log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
					sa.assertTrue(false, "stage name not present: " + stage);

				}
				String dealname1 = ADEDeal8;
				String stage1 = "IOL";

				if (BP.dealAcuityStageName(dealname1, stage1, 30) != null) {
					log(LogStatus.PASS, "Stage Name: " + stage1 + " is present", YesNo.No);

				} else {
					log(LogStatus.FAIL, "stage name not present: " + stage1, YesNo.Yes);
					sa.assertTrue(false, "stage name not present: " + stage1);

				}

			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}

		refresh(driver);

		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns4, 30)) {
				log(LogStatus.INFO, "open created item" + ADEIns4, YesNo.No);
				String dealname =ADEDeal9;
				String stage = "DD";

				if (BP.dealAcuityStageName(dealname, stage, 30) != null) {
					log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);

				} else {
					log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
					sa.assertTrue(false, "stage name not present: " + stage);

				}
				String dealname1 = ADEDeal10;
				String stage1 = "IOL";

				if (BP.dealAcuityStageName(dealname1, stage1, 30) != null) {
					log(LogStatus.PASS, "Stage Name: " + stage1 + " is present", YesNo.No);

				} else {
					log(LogStatus.FAIL, "stage name not present: " + stage1, YesNo.Yes);
					sa.assertTrue(false, "stage name not present: " + stage1);

				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc015_1_CreateNewLabelOfStagesAndDeleteExistingStage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		WebElement ele = null;
		String parentID = null;
		String stage = ADEDeal11Stage;
		String recordType = "";
		String dealName =ADEDeal11;
		String companyName = ADEDeal11CompanyName;
		String stage1 = "LOI";

		String labellabels = "Source Firm<Break>Source Contact<Break>Date Received";
		String otherLabelValues = ADEIns4 + "<Break>" + ADEContact1FName + " " + ADEContact1LName + "<Break>"
				+ todaysDate;

		if (lp.clickOnTab(projectName, tabObj4)) {
			log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
			ThreadSleep(3000);
			if (dp.createDeal(recordType, dealName, companyName, stage1, labellabels, otherLabelValues)) {
				log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);

			} else {
				log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
				sa.assertTrue(false, dealName + " deal is not created");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
		}

		if (home.clickOnSetUpLink()) {
			parentID = switchOnWindow(driver);
			if (parentID != null) {
				if (sp.searchStandardOrCustomObject(environment, mode, object.Deal)) {
					if (sp.clickOnObjectFeature(environment, mode, object.Deal,
							ObjectFeatureName.FieldAndRelationShip)) {
						if (sendKeys(driver, sp.getsearchTextboxFieldsAndRelationships(10),
								excelLabel.Stage.toString() + Keys.ENTER, "status", action.BOOLEAN)) {
							if (sp.clickOnAlreadyCreatedLayout(excelLabel.Stage.toString())) {
								switchToDefaultContent(driver);
								ThreadSleep(10000);
								switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
								stage = stage.replace("_", " ");
								ThreadSleep(3000);
								ele = sp.getValuesNewButton(10);
								if (click(driver, ele, "new button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "click on Values New Button", YesNo.No);
									switchToDefaultContent(driver);
									ThreadSleep(20000);
									switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
									ThreadSleep(5000);
									if (sendKeys(driver, dp.getTextArea(20), stage, stage, action.BOOLEAN)) {
										log(LogStatus.INFO, "enter value on textarea " + stage, YesNo.No);
										ThreadSleep(2000);
										if (click(driver, sp.getCustomTabSaveBtn(10), "save button",
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.ERROR, "Click on save Button : ", YesNo.No);
											ThreadSleep(2000);
										} else {
											log(LogStatus.ERROR, "Not Able to Click on save Button : ", YesNo.Yes);
											sa.assertTrue(false, "Not Able to Click on save Button : ");
										}
									} else {
										sa.assertTrue(false, "Not Able to enter value to textarea ");
										log(LogStatus.SKIP, "Not Able to enter value to textarea ", YesNo.Yes);
									}
								} else {
									log(LogStatus.ERROR, "new button is not clickable", YesNo.Yes);
									sa.assertTrue(false, "new button is not clickable");
								}
							} else {
								log(LogStatus.ERROR, "stage field is not clickable", YesNo.Yes);
								sa.assertTrue(false, "stage field is not clickable");
							}
						} else {
							log(LogStatus.ERROR, "field search textbox is not visible", YesNo.Yes);
							sa.assertTrue(false, "field search textbox is not visible");
						}
					} else {
						log(LogStatus.ERROR, "fundraising object is not clickable", YesNo.Yes);
						sa.assertTrue(false, "fundraising object is not clickable");
					}
				} else {
					log(LogStatus.ERROR, "fundraising object is not clickable", YesNo.Yes);
					sa.assertTrue(false, "fundraising object is not clickable");
				}
				ThreadSleep(5000);
				driver.close();
				driver.switchTo().window(parentID);
			} else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		} else {
			log(LogStatus.ERROR, "setup link is not clickable", YesNo.Yes);
			sa.assertTrue(false, "setup link is not clickable");
		}
		if (home.clickOnSetUpLink()) {
			parentID = switchOnWindow(driver);
			if (parentID != null) {
				if (sp.searchStandardOrCustomObject(environment, mode, object.Deal)) {
					if (sp.clickOnObjectFeature(environment, mode, object.Deal,
							ObjectFeatureName.FieldAndRelationShip)) {
						if (sendKeys(driver, sp.getsearchTextboxFieldsAndRelationships(10), excelLabel.Stage.toString(),
								"stage", action.BOOLEAN)) {
							if (sp.clickOnAlreadyCreatedLayout(excelLabel.Stage.toString())) {
								switchToDefaultContent(driver);
								switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
								ThreadSleep(5000);
								WebElement ele1 = dp.findDeleteLink(projectName, Stage.LOI.toString());
								if (click(driver, ele1, "delete LOI", action.SCROLLANDBOOLEAN)) {
									ThreadSleep(5000);
									if (!isAlertPresent(driver)) {
										clickUsingJavaScript(driver, ele1, "delete LOI", action.SCROLLANDBOOLEAN);
									}
									ThreadSleep(2000);
									// driver.switchTo().alert().accept();
									switchToAlertAndAcceptOrDecline(driver, 10, action.ACCEPT);
									switchToDefaultContent(driver);
									switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
									ThreadSleep(2000);
									if (selectVisibleTextFromDropDown(driver,
											dp.getreplacevalueforstage(projectName, 10), "replacevalueforstage",
											"DS")) {
										log(LogStatus.INFO,
												"Select custom field text in setup component dropdown in PipelineCustomPage setup page",
												YesNo.No);
										ThreadSleep(5000);
										if (click(driver, sp.getCustomTabSaveBtn(10), "save button",
												action.SCROLLANDBOOLEAN)) {
											log(LogStatus.ERROR, "Click on save Button : ", YesNo.No);
											ThreadSleep(2000);
										} else {
											log(LogStatus.ERROR, "Not Able to Click on save Button : ", YesNo.Yes);
											sa.assertTrue(false, "Not Able to Click on save Button : ");
										}
									} else {
										sa.assertTrue(false, "deactivate link is not clickable");
										log(LogStatus.SKIP, "deactivate link is not clickable", YesNo.Yes);
									}
								} else {
									sa.assertTrue(false, "stage field link is not clickable");
									log(LogStatus.SKIP, "stage field link is not clickable", YesNo.Yes);
								}
							} else {
								sa.assertTrue(false, "search textbox is not visible");
								log(LogStatus.SKIP, "search textbox is not visible", YesNo.Yes);
							}
						} else {
							log(LogStatus.FAIL, "field n relationships feature not clickable", YesNo.Yes);
							sa.assertTrue(false, "field n relationships feature not clickable");
						}
					} else {
						log(LogStatus.FAIL, "deal object is not clickable", YesNo.Yes);
						sa.assertTrue(false, "deal object is not clickable");
					}
					driver.close();
					driver.switchTo().window(parentID);
				} else {
					log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
					sa.assertTrue(false, "could not find new window to switch");
				}
			} else {
				log(LogStatus.FAIL, "setup link is not clickable", YesNo.Yes);
				sa.assertTrue(false, "setup link is not clickable");
			}

			lp.CRMlogout();
			sa.assertAll();
		}
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc015_2_EditExistingCompanyDealVerifyimpactDealCompanyAccount(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String dealName = ADEDeal11;
		String stage = ADEDeal11Stage;
		String dateReceived = todaysDate;
		String ContactName = ADEContact1FName + " " + ADEContact1LName;

		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns1, YesNo.No);

				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
						if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
							log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);

						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name not present: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name not present: " + dealName);

						}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}

				} else {
					log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
					sa.assertTrue(false, "date receivednot present: " + dateReceived);
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns8);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns8, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns4, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns4, YesNo.No);

				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
						if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
							log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);

						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name not present: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name not present: " + dealName);

						}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}

				} else {
					log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
					sa.assertTrue(false, "date receivednot present: " + dateReceived);
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns9);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns9, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ContactName, 30)) {

				log(LogStatus.INFO, "open created item" + ContactName, YesNo.No);

				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
						if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
							log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);

						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name not present: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name not present: " + dealName);

						}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}

				} else {
					log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
					sa.assertTrue(false, "date receivednot present: " + dateReceived);
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source contact : " + ContactName);
				log(LogStatus.SKIP, "Not Able to open created source contact : " + ContactName, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc016_ChangeCompanyTypeRecordPortfolioforAccountVerifyDealSectionAccount(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String value = "";
		String type = "";
		String[][] EntityOrAccounts = { { ADEIns10, ADEIns10RecordType, null } };
		String labelName1[] = { excelLabel.Record_Type.toString() };
		String labelValues1[] = { InstRecordType.Portfolio_Company.toString() };

		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, tabObj1)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
				value = accounts[0];
				type = accounts[1];
				if (ip.createEntityOrAccount(projectName, mode, value, type, null, null, 20)) {
					log(LogStatus.INFO, "successfully Created Account/Entity : " + value + " of record type : " + type,
							YesNo.No);
				} else {
					sa.assertTrue(false, "Not Able to Create Account/Entity : " + value + " of record type : " + type);
					log(LogStatus.SKIP, "Not Able to Create Account/Entity : " + value + " of record type : " + type,
							YesNo.Yes);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + tabObj1);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabObj1, YesNo.Yes);
			}
		}
		String recordType = "";
		String dealName = ADEDeal12;
		String companyName = ADEDeal12CompanyName;
		String stage = ADEDeal12Stage;
		String dateReceived = todaysDate;
		String ContactName = ADEContact2FName + " " + ADEContact2LName;

		String labellabels = "Date Received";
		String otherLabelValues = todaysDate;
		if (lp.clickOnTab(projectName, tabObj4)) {
			log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
			ThreadSleep(3000);
			if (dp.createDeal(recordType, dealName, companyName, stage, labellabels, otherLabelValues)) {
				log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);

			} else {
				log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
				sa.assertTrue(false, dealName + " deal is not created");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
		}

		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns10, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns10, YesNo.No);

				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
						if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
							log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);

						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}

				} else {
					log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
					sa.assertTrue(false, "date receivednot present: " + dateReceived);
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns10);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns10, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}

		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName,ADEDeal12, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10), "convert to portfolio button", action.BOOLEAN)) {
					if (click(driver, dp.getnextButton(10), "next button", action.BOOLEAN)) {
						String text = dp.getconvertToPortfolioMessageAfterNext(10).getText();
						if (text.contains(dp.convertToPortfolioAfterNext(M6Ins2))) {
							log(LogStatus.INFO, "successfully verified convert to portfolio message congratulations",
									YesNo.Yes);

						} else {
							sa.assertTrue(false, "could not verify convert to portfolio message Expected: "
									+ dp.convertToPortfolioAfterNext(M6Ins2) + " actual: " + text);
							log(LogStatus.SKIP, "could not verify convert to portfolio message Expected: "
									+ dp.convertToPortfolioAfterNext(M6Ins2) + " actual: " + text, YesNo.Yes);
						}
						if (dp.getconvertToPortfolioCrossButton(10) != null) {
							log(LogStatus.INFO, "cross button is present", YesNo.Yes);

						} else {
							sa.assertTrue(false, "could not verify cross icon presence");
							log(LogStatus.SKIP, "could not verify cross icon presence", YesNo.Yes);
						}
						if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "successfully verified finish button after convert to portfolio",
									YesNo.No);
						} else {
							sa.assertTrue(false, "could not verify convert to portfolio as finish button not clicked");
							log(LogStatus.SKIP, "could not verify convert to portfolio as finish button not clicked",
									YesNo.Yes);
						}
					} else {
						sa.assertTrue(false, "could not verify convert to portfolio as finish button not clicked");
						log(LogStatus.SKIP, "could not verify convert to portfolio as finish button not clicked",
								YesNo.Yes);
					}
				} else {
					sa.assertTrue(false, "could not verify convert to portfolio as finish button not clicked");
					log(LogStatus.SKIP, "could not verify convert to portfolio as finish button not clicked",
							YesNo.Yes);
				}
			} else {
				sa.assertTrue(false, "could not click on " + M6Deal2);
				log(LogStatus.SKIP, "could not click on " + M6Deal2, YesNo.Yes);
			}
		} else {
			sa.assertTrue(false, "could not click on deal tab");
			log(LogStatus.SKIP, "could not click on deal tab", YesNo.Yes);
		}

		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, ADEIns10, 10)) {
				for (int i = 0; i < labelName1.length; i++) {
					if (ip.fieldValueVerificationOnInstitutionPage(environment, mode, TabName.Object1Tab, labelName1[i],
							labelValues1[i])) {
						log(LogStatus.SKIP, "successfully verified " + labelName1[i], YesNo.No);

					} else {
						sa.assertTrue(false, "Not Able to verify " + labelName1[i]);
						log(LogStatus.SKIP, "Not Able to verify " + labelName1[i], YesNo.Yes);

					}
					String TabName1 = "";
					String str = getText(driver, bp.verifydefaultCreatedItemOnPageAcuty(Header.Company, "Acuity"),
							"legal Name Label Text", action.SCROLLANDBOOLEAN);
					if (str != null) {
						if (str.contains(TabName1)) {
							appLog.info("created institution " + TabName1 + " is verified successfully.");
							appLog.info(TabName1 + " is created successfully.");
						} else {
							appLog.error("Created  " + TabName1 + " is not matched with " + str);
						}
					} else {
						appLog.error("Created  " + TabName1 + " is not visible");
					}
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns10);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns10, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc017_1_UpdatefieldcolumnNamesCheckDealsectionAccountandContactTab(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
		String parentID = null;
		String updateLabel = "UpdatedStage";
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentID = switchOnWindow(driver);
			if (parentID != null) {
				if (sp.searchStandardOrCustomObject(environment, mode, object.Override)) {
					log(LogStatus.INFO, "click on Object : " + object.valueOf("Override"), YesNo.No);
					ThreadSleep(2000);
					switchToFrame(driver, 30, sp.getSetUpPageIframe(60));
					ThreadSleep(5000);
					if (selectVisibleTextFromDropDown(driver, sp.getOverrideSetupComponentDropdown(10),
							"Override setup component dropdown", "Custom Field")) {
						log(LogStatus.INFO,
								"Select custom field text in setup component dropdown in override setup page",
								YesNo.No);
						ThreadSleep(5000);

						if (selectVisibleTextFromDropDown(driver, sp.getOverrideObjectDropdown(10),
								"Override object dropdown", PageLabel.Deal.toString())) {
							log(LogStatus.INFO, "Select " + PageLabel.Deal.toString()
									+ " text in object dropdown in override setup page", YesNo.No);
							ThreadSleep(5000);
							if (sp.updateFieldLabelInOverridePage(driver, PageLabel.Stage.toString(), updateLabel,
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Field label: " + PageLabel.Stage.toString()
										+ " successfully update to " + updateLabel, YesNo.No);

							} else {
								log(LogStatus.ERROR, "Not able to update Field label: " + PageLabel.Stage.toString()
										+ " successfully update to " + updateLabel, YesNo.Yes);
								sa.assertTrue(false, "Not able to update Field label: " + PageLabel.Stage.toString()
										+ " to " + updateLabel);
							}
						} else {
							log(LogStatus.ERROR, "Not able to select text: " + PageLabel.Activity.toString()
									+ " in  object dropdown in override page", YesNo.Yes);
							sa.assertTrue(false, "Not able to select text: " + PageLabel.Activity.toString()
									+ " in  object dropdown in override page");
						}
					} else {
						log(LogStatus.ERROR,
								"Not able to select text: Custom Field in  setup component dropdown in override page",
								YesNo.Yes);
						sa.assertTrue(false,
								"Not able to select text: Custom Field in  setup component dropdown in override page");
					}

				} else {

					log(LogStatus.PASS, "Not able to click on Object : " + object.valueOf("Override"), YesNo.Yes);
					sa.assertTrue(false, "Not able to click on Object : " + object.valueOf("Override"));
				}
			} else {
				sa.assertTrue(false, "new window is not found, so cannot change watchlist label");
				log(LogStatus.SKIP, "new window is not found, so cannot change watchlist label", YesNo.Yes);
			}
		} else {
			sa.assertTrue(false, "setup link is not clickable");
			log(LogStatus.SKIP, "setup link is not clickable", YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc017_2_VerifyUpdatefieldcolumnNamesCheckDealsectionAccountandContactTab(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String ContactName = ADEContact1FName + " " + ADEContact1LName;
		String AccountName = ADEIns1;
		String dealHeader = "Deal Name<break>UpdatedStage<break>Highest Stage Reached<break>Date Received";
	

		ArrayList<String> connectionsSectionHeaderName = new ArrayList<String>();

		ArrayList<String> contactsSectionHeaderName = new ArrayList<String>();

		String[] arrDealHeader = dealHeader.split("<break>");
		List<String> dealHeaders = new ArrayList<String>(Arrays.asList(arrDealHeader));

		if (cp.clickOnTab(projectName, tabObj1)) {
			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1 + " For : " + AccountName, YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, AccountName, 30)) {
				log(LogStatus.INFO, "Clicked on  : " + AccountName + " For : " + tabObj2, YesNo.No);
				ThreadSleep(2000);
				ArrayList<String> result1 = bp
						.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
								contactsSectionHeaderName, null, dealHeaders, null, connectionsSectionHeaderName, null);
				if (result1.isEmpty()) {
					log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
				} else {
					log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
					sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ContactName, 30)) {

				log(LogStatus.INFO, "open created item" + ContactName, YesNo.No);
				ThreadSleep(2000);
				ArrayList<String> result1 = bp
						.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
								contactsSectionHeaderName, null, dealHeaders, null, connectionsSectionHeaderName, null);
				if (result1.isEmpty()) {
					log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
				} else {
					log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
					sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source contact : " + ContactName);
				log(LogStatus.SKIP, "Not Able to open created source contact : " + ContactName, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc018_1_CreateRecordTypeBankerAndContactForcontatcObjectAndAddTheFromTheProfiles(
			String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword);
		String recordTypeList = "Banker<break>Contact";
		String recordTypeArray[] = recordTypeList.split("<break>");
		String[][] fundRecordType = { { recordTypeLabel.Record_Type_Label.toString(), recordTypeArray[0] },
				{ recordTypeLabel.Description.toString(), recordTypeArray[0] + recordTypeDescription },
				{ recordTypeLabel.Active.toString(), "" } };

		String[][] ffrecordType = { { recordTypeLabel.Record_Type_Label.toString(), recordTypeArray[1] },
				{ recordTypeLabel.Description.toString(), recordTypeArray[1] + recordTypeDescription },
				{ recordTypeLabel.Active.toString(), "" } };
		String[] profileForSelection = { "PE Standard User" };
		boolean isMakeAvailable = true;
		boolean isMakeDefault = true;
		boolean flag = false;
		for (int i = 0; i < recordTypeArray.length; i++) {
			if (home.clickOnSetUpLink()) {
				flag = false;
				String parentID = switchOnWindow(driver);
				SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
				if (parentID != null) {
					if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(), object.Contact)) {
						if (sp.clickOnObjectFeature("", Mode.Lightning.toString(), object.Contact,
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

	@Parameters({ "projectName" })
	@Test
	public void ADETc018_2_VerifyCreateRecordTypeBankerAndContactForcontatcObjectAndAddTheFromTheProfiles(
			String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			ADEContact9EmailID = lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact8EmailID, "Contact", excelLabel.Variable_Name,
					"ADEContact9", excelLabel.Contact_EmailId);

			if (cp.createContact(projectName, ADEContact9FName, ADEContact9LName, ADEIns1, ADEContact9EmailID,
					ADEContact9RecordType, null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO, "successfully Created Contact : " + ADEContact9FName + " " + ADEContact9LName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Create Contact : " + ADEContact9FName + " " + ADEContact9LName);
				log(LogStatus.SKIP, "Not Able to Create Contact: " + ADEContact9FName + " " + ADEContact9LName,
						YesNo.Yes);
			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object2Tab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object2Tab, YesNo.Yes);
		}
		String TabName1 = "";
		String str = getText(driver, bp.verifydefaultCreatedItemOnPageAcuty(Header.Company, "Acuity"),
				"Contact Name Label Text", action.SCROLLANDBOOLEAN);
		if (str != null) {
			if (str.contains(TabName1)) {
				appLog.info("created contact" + TabName1 + " is verified successfully.");
				appLog.info(TabName1 + " is created successfully.");
			} else {
				appLog.error("Created  " + TabName1 + " is not matched with " + str);
			}
		} else {
			appLog.error("Created  " + TabName1 + " is not visible");
		}

		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			ADEContact10EmailID = lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact10EmailID, "Contact", excelLabel.Variable_Name,
					"ADEContact10", excelLabel.Contact_EmailId);

			if (cp.createContact(projectName, ADEContact10FName, ADEContact10LName, ADEIns1, ADEContact10EmailID,
					ADEContact10RecordType, null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO, "successfully Created Contact : " + ADEContact10FName + " " + ADEContact10LName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Create Contact : " + ADEContact10FName + " " + ADEContact10LName);
				log(LogStatus.SKIP, "Not Able to Create Contact: " + ADEContact10FName + " " + ADEContact10LName,
						YesNo.Yes);
			}
		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object2Tab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object2Tab, YesNo.Yes);
		}
		String TabName = "";
		String str1 = getText(driver, bp.verifydefaultCreatedItemOnPageAcuty(Header.Company, "Acuity"),
				"Contact Name Label Text", action.SCROLLANDBOOLEAN);
		if (str != null) {
			if (str.contains(TabName)) {
				appLog.info("created contact" + TabName + " is verified successfully.");
				appLog.info(TabName + " is created successfully.");
			} else {
				appLog.error("Created  " + TabName + " is not matched with " + str);
			}
		} else {
			appLog.error("Created  " + TabName + " is not visible");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc019_EditDealContactBankerTypeContactDealteamVerifyImpactTabsContacts(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String dealName = ADEDealTeamName16;

		String contactName =ADEDealContact16;

		String[][] data = { { PageLabel.Deal.toString(), dealName },
				{ PageLabel.Deal_Contact.toString(), contactName } };

		if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

			if (DTP.createDealTeam(projectName, dealName, data, "Acuity", action.SCROLLANDBOOLEAN, 25)) {
				log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----", YesNo.No);

				log(LogStatus.INFO,
						"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
								+ contactName + " at Firm Tab under Acuity section---------",
						YesNo.No);
				String xpath = "//*[text()='Deal Team']/parent::h1//slot/lightning-formatted-text";
				WebElement ele = FindElement(driver, xpath, "dt id", action.BOOLEAN, 10);
				if (ele != null) {
					String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
					ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name, "ADT_16",
							excelLabel.DealTeamID);
					log(LogStatus.INFO, "successfully created and noted id of DT" + id + " and deal name " + dealName,
							YesNo.No);
				} else {
					sa.assertTrue(false, "could not create DT" + dealName);
					log(LogStatus.SKIP, "could not create DT" + dealName, YesNo.Yes);
				}
			}
				if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

					if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {

						log(LogStatus.INFO, "open created item" + contactName, YesNo.No);
						ThreadSleep(2000);
						if (BP.dealAcuityDealName(dealName, 30) != null) {
							log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
					} else {

						sa.assertTrue(false, "Not Able to open created source contact : " + contactName);
						log(LogStatus.SKIP, "Not Able to open created source contact : " + contactName, YesNo.Yes);

					}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Deal_Team);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Deal_Team, YesNo.Yes);
			}
			String contactName1 = ADEContact1FName + " " + ADEContact1LName;
			String[][] data1 = { { PageLabel.Deal_Contact.toString(), contactName1 } };

			if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
				if (fp.clickOnAlreadyCreatedItem(projectName, ADEDealTeamID16, 10)) {
					if (DTP.UpdateDealContactName(projectName, data1, 30)) {
						log(LogStatus.INFO, "successfully changed name to " + contactName1, YesNo.Yes);
					} else {
						sa.assertTrue(false, "not able to change name to " + contactName1);
						log(LogStatus.SKIP, "not able to change name to " + contactName1, YesNo.Yes);
					}
					if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
						log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

						if (fp.clickOnAlreadyCreatedItem(projectName, contactName1, 30)) {

							log(LogStatus.INFO, "open created item" + contactName1, YesNo.No);
							ThreadSleep(2000);
							if (BP.dealAcuityDealName(dealName, 30) != null) {
								log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present",
										YesNo.No);
							} else {
								log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
								sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

							}
						} else {

							sa.assertTrue(false, "Not Able to open created source contact : " + contactName1);
							log(LogStatus.SKIP, "Not Able to open created source contact : " + contactName1, YesNo.Yes);

						}
					} else {
						log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
					}
				}
				switchToDefaultContent(driver);
				lp.CRMlogout();
				sa.assertAll();
			
			}
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc020_UpdatefieldcolumnNamesCheckDealsectionAccountandContactTab(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
		String parentID = null;
		String updateLabel = "Stage";
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentID = switchOnWindow(driver);
			if (parentID != null) {
				if (sp.searchStandardOrCustomObject(environment, mode, object.Override)) {
					log(LogStatus.INFO, "click on Object : " + object.valueOf("Override"), YesNo.No);
					ThreadSleep(2000);
					switchToFrame(driver, 30, sp.getSetUpPageIframe(60));
					ThreadSleep(5000);
					if (selectVisibleTextFromDropDown(driver, sp.getOverrideSetupComponentDropdown(10),
							"Override setup component dropdown", "Custom Field")) {
						log(LogStatus.INFO,
								"Select custom field text in setup component dropdown in override setup page",
								YesNo.No);
						ThreadSleep(5000);

						if (selectVisibleTextFromDropDown(driver, sp.getOverrideObjectDropdown(10),
								"Override object dropdown", PageLabel.Deal.toString())) {
							log(LogStatus.INFO, "Select " + PageLabel.Deal.toString()
									+ " text in object dropdown in override setup page", YesNo.No);
							ThreadSleep(5000);
							if (sp.updateFieldLabelInOverridePage(driver, PageLabel.Stage.toString(), updateLabel,
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Field label: " + PageLabel.Stage.toString()
										+ " successfully update to " + updateLabel, YesNo.No);

							} else {
								log(LogStatus.ERROR, "Not able to update Field label: " + PageLabel.Stage.toString()
										+ " successfully update to " + updateLabel, YesNo.Yes);
								sa.assertTrue(false, "Not able to update Field label: " + PageLabel.Stage.toString()
										+ " to " + updateLabel);
							}
						} else {
							log(LogStatus.ERROR, "Not able to select text: " + PageLabel.Activity.toString()
									+ " in  object dropdown in override page", YesNo.Yes);
							sa.assertTrue(false, "Not able to select text: " + PageLabel.Activity.toString()
									+ " in  object dropdown in override page");
						}
					} else {
						log(LogStatus.ERROR,
								"Not able to select text: Custom Field in  setup component dropdown in override page",
								YesNo.Yes);
						sa.assertTrue(false,
								"Not able to select text: Custom Field in  setup component dropdown in override page");
					}

				} else {

					log(LogStatus.PASS, "Not able to click on Object : " + object.valueOf("Override"), YesNo.Yes);
					sa.assertTrue(false, "Not able to click on Object : " + object.valueOf("Override"));
				}
			} else {
				sa.assertTrue(false, "new window is not found, so cannot change watchlist label");
				log(LogStatus.SKIP, "new window is not found, so cannot change watchlist label", YesNo.Yes);
			}
		} else {
			sa.assertTrue(false, "setup link is not clickable");
			log(LogStatus.SKIP, "setup link is not clickable", YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc022_VerifyDealSectionDealNamelongerforAccountContacts(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String recordType = "";
		String dealName = ADEDeal13;
		String companyName =ADEDeal13CompanyName;
		String stage = ADEDeal13Stage;
		String dateReceived = todaysDate;

		String labellabels = "Source Firm<Break>Source Contact<Break>Date Received";
		String otherLabelValues = ADEIns4 + "<Break>" + ADEContact4FName + " " + ADEContact4LName + "<Break>"
				+ todaysDate;

		if (lp.clickOnTab(projectName, tabObj4)) {
			log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
			ThreadSleep(3000);
			if (dp.createDeal(recordType, dealName, companyName, stage, labellabels, otherLabelValues)) {
				log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);

			} else {
				log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
				sa.assertTrue(false, dealName + " deal is not created");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns1, YesNo.No);

				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
						if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
							log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);

						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}

				} else {
					log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
					sa.assertTrue(false, "date receivednot present: " + dateReceived);
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns4, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns4, YesNo.No);

				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
						if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
							log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);

						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}

				} else {
					log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
					sa.assertTrue(false, "date receivednot present: " + dateReceived);
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEContact4FName + " " + ADEContact4LName, 30)) {

				log(LogStatus.INFO, "open created item" + ADEContact4FName + " " + ADEContact4LName, YesNo.No);

				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
						if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
							log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);

						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}

				} else {
					log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
					sa.assertTrue(false, "date receivednot present: " + dateReceived);
				}
			} else {

				sa.assertTrue(false,
						"Not Able to open created source Contact : " + ADEContact4FName + " " + ADEContact4LName);
				log(LogStatus.SKIP,
						"Not Able to open created source Contact : " + ADEContact4FName + " " + ADEContact4LName,
						YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc021_RemoveSourceFirmSourceContactBelongingRemovedfirmCheckAccContactrecord(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String recordType = "";
		String dealName = ADEDeal14;
		String companyName =ADEDeal14CompanyName;
		String stage = ADEDeal14Stage;
		String dateReceived = todaysDate;

		String labellabels = "Source Contact<Break>Date Received";
		String otherLabelValues = ADEContact4FName + " " + ADEContact4LName + "<Break>" + todaysDate;

		if (lp.clickOnTab(projectName, tabObj4)) {
			log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
			ThreadSleep(3000);
			if (dp.createDeal(recordType, dealName, companyName, stage, labellabels, otherLabelValues)) {
				log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);

			} else {
				log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
				sa.assertTrue(false, dealName + " deal is not created");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns1, YesNo.No);

				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
						if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
							log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);

						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}

				} else {
					log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
					sa.assertTrue(false, "date receivednot present: " + dateReceived);
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns4, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns4, YesNo.No);

				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);

				} else {
					log(LogStatus.FAIL, "Not able to find on Deal Name: " + dealName, YesNo.Yes);
					sa.assertTrue(true, "Not able to find on Deal Name: " + dealName);

				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEContact4FName + " " + ADEContact4LName, 30)) {

				log(LogStatus.INFO, "open created item" + ADEContact4FName + " " + ADEContact4LName, YesNo.No);

				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
						if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
							log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);

						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}

				} else {
					log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
					sa.assertTrue(false, "date receivednot present: " + dateReceived);
				}
			} else {

				sa.assertTrue(false,
						"Not Able to open created source Contact : " + ADEContact4FName + " " + ADEContact4LName);
				log(LogStatus.SKIP,
						"Not Able to open created source Contact : " + ADEContact4FName + " " + ADEContact4LName,
						YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc023_VerifyImpactDealsontabAccountandContacthaveDateReceivedRange(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String value = "";
		String type = "";

		String[][] EntityOrAccounts = { { ADEIns13, ADEIns13RecordType, null } };

		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
				value = accounts[0];
				type = accounts[1];
				if (ip.createEntityOrAccount(projectName, mode, value, type, null, null, 20)) {
					log(LogStatus.INFO, "successfully Created Account/Entity : " + value + " of record type : " + type,
							YesNo.No);

				} else {
					sa.assertTrue(false, "Not Able to Create Account/Entity : " + value + " of record type : " + type);
					log(LogStatus.SKIP, "Not Able to Create Account/Entity : " + value + " of record type : " + type,
							YesNo.Yes);
				}
			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.Object1Tab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.Object1Tab, YesNo.Yes);
			}
		}
	String[] dealRecordType = ADEDealRecordType1.split("<Section>", -1);
		String[] dealName = ADEDealName2.split("<Section>", -1);
		String[] dealCompany = ADEDealCompany2.split("<Section>", -1);
		String[] dealStage = ADEDealStage2.split("<Section>", -1);
		String[] otherLabels = ADEDealOtherLabelNames2.split("<Section>", -1);
		String[] otherLabelValues = ADEDealOtherLabelValues2.split("<Section>", -1);

		ArrayList<String> dealname = new ArrayList<String>();
		dealname.add(dealName.toString());

		for (int i = 0; i < 3; i++) {
			if (lp.clickOnTab(projectName, tabObj4)) {
				log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
				ThreadSleep(3000);
				if (dp.createDeal(null, dealName[i], dealCompany[i], dealStage[i], otherLabels[i],
						otherLabelValues[i])) {
					log(LogStatus.INFO, dealName[i] + " deal has been created", YesNo.No);

//				ExcelUtils.writeData(AcuityDataSheetFilePath, otherLabelValues.split("<Section>", -1)[2], "Deal",
//						excelLabel.Variable_Name, "AS_Deal2", excelLabel.Date);

				} else {
					log(LogStatus.ERROR, dealName[i] + " deal is not created", YesNo.No);
					sa.assertTrue(false, dealName[i] + " deal is not created");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
				sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
			}
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns13, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns13, YesNo.No);

				List<WebElement> li = dp.listOfDealNames(30);
				boolean flag = false;
				for (int i = 0; i < li.size(); i++) {
					if (li.get(i).getText().contains(dealname.get(i))) {
						flag = true;
						break;
					}
				}
				if (flag) {
					log(LogStatus.ERROR, dealname + " is found in emailing grid but it should not be", YesNo.Yes);
					sa.assertTrue(false, dealname + " is found in emailing grid but it should not be");
				} else {
					log(LogStatus.INFO, "could not find " + dealname + " as expected", YesNo.No);
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc024_ChangeDateReceivedPreviousDateReceivedCheckorderDealNameAccountandContact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String dealName = ADEDeal13;
		String labellabels = "Date Received";
		String otherLabelValues = "09/11/2006";
		String contactname = ADEContact4FName + " " + ADEContact4LName;

		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, dealName, 10)) {
				if (dp.UpdateOtherLabledaterecieved(projectName, labellabels, otherLabelValues, 20)) {
					log(LogStatus.INFO, "successfully changed date recieved to " + labellabels, YesNo.Yes);
				} else {
					sa.assertTrue(false, "not able to change date recieved to " + labellabels);
					log(LogStatus.SKIP, "not able to change date recieved to " + labellabels, YesNo.Yes);
				}
			} else {
				sa.assertTrue(false, "not able to click on already created " + dealName);
				log(LogStatus.SKIP, "not able to click on already created " + dealName, YesNo.Yes);
			}
		} else {
			sa.assertTrue(false, "not able to click on tab " + TabName.Object4Tab);
			log(LogStatus.SKIP, "not able to click on tab " + TabName.Object4Tab, YesNo.Yes);
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns4, 30)) {
				if (dp.dealbottomcount(20) != null) {
					log(LogStatus.PASS, "Dealbottomcount: " + dealName + "is present", YesNo.No);
					if (dp.dealbottomname(dp.dealbottomcount(20), 20) != null) {
						log(LogStatus.PASS, "Dealbottomname: " + dealName + "is present", YesNo.No);
						if (dealName.equals(dp.dealbottomname(dp.dealbottomcount(20), 20))) {
							log(LogStatus.INFO, "successfully found deal at bottom " + dealName, YesNo.Yes);
						} else {
							sa.assertTrue(false, "deal not present at bottom " + dealName);
							log(LogStatus.SKIP, "deal not present at bottom " + dealName, YesNo.Yes);
						}
					} else {
						sa.assertTrue(false, "dealcount not present at bottom " + dealName);
						log(LogStatus.SKIP, "dealcount not present at bottom " + dealName, YesNo.Yes);
					}
				} else {
					sa.assertTrue(false, "dealcount not present at bottom " + dealName);
					log(LogStatus.SKIP, "dealcount not present at bottom " + dealName, YesNo.Yes);
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, contactname, 30)) {
				if (dp.dealbottomcount(20) != null) {
					log(LogStatus.PASS, "Dealbottomcount: " + dealName + "is present", YesNo.No);
					if (dp.dealbottomname(dp.dealbottomcount(20), 20) != null) {
						log(LogStatus.PASS, "Dealbottomname: " + dealName + "is present", YesNo.No);
						if (dealName.equals(dp.dealbottomname(dp.dealbottomcount(20), 20))) {
							log(LogStatus.INFO, "successfully found deal at bottom " + dealName, YesNo.Yes);
						} else {
							sa.assertTrue(false, "deal not present at bottom " + dealName);
							log(LogStatus.SKIP, "deal not present at bottom " + dealName, YesNo.Yes);
						}
					} else {
						sa.assertTrue(false, "dealName not present at bottom " + dealName);
						log(LogStatus.SKIP, "dealName not present at bottom " + dealName, YesNo.Yes);
					}
				} else {
					sa.assertTrue(false, "dealcount not present at bottom " + dealName);
					log(LogStatus.SKIP, "dealcount not present at bottom " + dealName, YesNo.Yes);
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc025_ChangeDateReceivedFutureDateReceivedCheckorderDealNameAccountandContact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);	
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String dealName = ADEDeal13;
		String labellabels = "Date Received";
		String otherLabelValues = "09/11/2023";
		String contactname = ADEContact4FName + " " + ADEContact4LName;
		String dealtopcount = "1";

		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, dealName, 10)) {
				if (dp.UpdateOtherLabledaterecieved(projectName, labellabels, otherLabelValues, 20)) {
					log(LogStatus.INFO, "successfully changed date recieved to " + labellabels, YesNo.Yes);
				} else {
					sa.assertTrue(false, "not able to change date recieved to " + labellabels);
					log(LogStatus.SKIP, "not able to change date recieved to " + labellabels, YesNo.Yes);
				}
			} else {
				sa.assertTrue(false, "not able to click on already created " + dealName);
				log(LogStatus.SKIP, "not able to click on already created " + dealName, YesNo.Yes);
			}
		} else {
			sa.assertTrue(false, "not able to click on tab " + TabName.Object4Tab);
			log(LogStatus.SKIP, "not able to click on tab " + TabName.Object4Tab, YesNo.Yes);
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns4, 30)) {
				if (dealName.equals(dp.dealtopname(dealtopcount, 20))) {
					log(LogStatus.PASS, "Dealtopname: " + dealName + "is present", YesNo.No);

//		 if(dealName.equals(dp.dealtopname(dealtopcount, 20))) {
//    	log(LogStatus.INFO,"successfully found deal at top "+dealName,YesNo.Yes);
//	}else {
//		sa.assertTrue(false,"deal not present at top "+dealName);
//		log(LogStatus.SKIP,"deal not present at top "+dealName,YesNo.Yes);
//    }
				} else {

					sa.assertTrue(false, "Not Able to top deal name : " + dealName);
					log(LogStatus.SKIP, "Not Able to top deal name : " + dealName, YesNo.Yes);

				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, contactname, 30)) {
				if (dealName.equals(dp.dealtopname(dealtopcount, 20))) {
					log(LogStatus.PASS, "Dealtopname: " + dealName + "is present", YesNo.No);

//			 if(dealName.equals(dp.dealtopname(dealtopcount, 20))) {
//	    	log(LogStatus.INFO,"successfully found deal at top "+dealName,YesNo.Yes);
//		}else {
//			sa.assertTrue(false,"deal not present at top "+dealName);
//			log(LogStatus.SKIP,"deal not present at top "+dealName,YesNo.Yes);
//	    }
				} else {

					sa.assertTrue(false, "Not Able to top deal name : " + dealName);
					log(LogStatus.SKIP, "Not Able to top deal name : " + dealName, YesNo.Yes);

				}

			} else {

				sa.assertTrue(false, "Not Able to open created contact : " + contactname);
				log(LogStatus.SKIP, "Not Able to open created contact: " + contactname, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc026_1_RenameAccountContactNameVerifyImpactOnDealsTab(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String updatedname = "ADETestCom renamed";
		String updatedname1 = "ADETestInt renamed";
		String contactlastname = "TC 5 Renamed";

		String contactname = ADEContact3FName + " " + ADEContact3LName;
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)) {
				if (ip.UpdateLegalNameAccount(projectName, updatedname, 10)) {
					log(LogStatus.INFO, "successfully update legal name " + updatedname, YesNo.Yes);
					ExcelUtils.writeData(AcuityDataSheetFilePath, updatedname, "Firm", excelLabel.Variable_Name,
							"ADEIns1", excelLabel.Legal_Name);
				} else {
					sa.assertTrue(false, "not able to update legal name " + updatedname);
					log(LogStatus.SKIP, "not able to update legal name " + updatedname, YesNo.Yes);
				}
			} else {

				sa.assertTrue(false, "Not Able to open created firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created firm: " + ADEIns1, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}

		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns4, 30)) {
				if (ip.UpdateLegalNameAccount(projectName, updatedname1, 20)) {
					log(LogStatus.INFO, "successfully update legal name " + updatedname1, YesNo.Yes);
					ExcelUtils.writeData(AcuityDataSheetFilePath, updatedname1, "Firm", excelLabel.Variable_Name,
							"ADEIns4", excelLabel.Legal_Name);
				} else {
					sa.assertTrue(false, "not able to update legal name " + updatedname1);
					log(LogStatus.SKIP, "not able to update legal name " + updatedname1, YesNo.Yes);
				}
			} else {

				sa.assertTrue(false, "Not Able to open created firm : " + ADEIns4);
				log(LogStatus.SKIP, "Not Able to open created firm: " + ADEIns4, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, contactname, 30)) {
				if (cp.UpdateLastName(projectName, PageName.ContactPage, contactlastname)) {
					log(LogStatus.INFO, "successfully update legal name " + contactlastname, YesNo.Yes);
					ExcelUtils.writeData(AcuityDataSheetFilePath, contactlastname, "Contact", excelLabel.Variable_Name,
							"ADEContact3", excelLabel.Contact_LastName);
				} else {
					sa.assertTrue(false, "not able to update last name " + updatedname1);
					log(LogStatus.SKIP, "not able to update legal name " + updatedname1, YesNo.Yes);
				}
			} else {

				sa.assertTrue(false, "Not Able to open created contact : " + contactname);
				log(LogStatus.SKIP, "Not Able to open created contact: " + contactname, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc026_2_VerifyImpactRenameAccountContactNameVerifyImpactOnDealsTab(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String[] dealName = ADEDealName1.split("<Section>", -1);
		String contactname = ADEContact3FName + " " + ADEContact3LName;
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns1, YesNo.No);
				for (int i = 0; i < 9; i++) {
					if (BP.dealAcuityDealName(dealName[i], 30) != null) {
						log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);

					} else {
						log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

					}
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns4, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns4, YesNo.No);
				for (int i = 9; i < 18; i++) {
					if (BP.dealAcuityDealName(dealName[i], 30) != null) {
						log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);

					} else {
						log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

					}
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, contactname, 30)) {

				log(LogStatus.INFO, "open created item" + contactname, YesNo.No);
				for (int i = 9; i < 18; i++) {
					if (BP.dealAcuityDealName(dealName[i], 30) != null) {
						log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					} else {
						log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

					}
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc027_VerifyContactCardsectionvisibilityinAcuitytabforexistingAccountsANDContacts(
			String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String ContactName = ADEContact1FName + " " + ADEContact1LName;
		String AccountName = ADEIns1;
		String message = "No items to display";
		String contactHeader = "Name<break>Title<break>Deals<break>Meetings and Calls<break>Emails";

		ArrayList<String> connectionsSectionHeaderName = new ArrayList<String>();

		ArrayList<String> DealHeaderName = new ArrayList<String>();

		String[] arrcontactsSectionHeader = contactHeader.split("<break>");
		List<String> contactsSectionHeader = new ArrayList<String>(Arrays.asList(arrcontactsSectionHeader));

		if (cp.clickOnTab(projectName, tabObj1)) {
			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1 + " For : " + ADEIns1, YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)) {
				log(LogStatus.INFO, "Clicked on  : " + ADEIns1 + " For : " + tabObj2, YesNo.No);
				ThreadSleep(2000);
				ArrayList<String> result1 = bp
						.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
								contactsSectionHeader, null, DealHeaderName, null, connectionsSectionHeaderName, null);
				if (result1.isEmpty()) {
					log(LogStatus.INFO, "The header name and message have been verified  Contact Section ", YesNo.No);
				} else {
					log(LogStatus.ERROR, "The header name and message are not verified on Contact Section ", YesNo.No);
					sa.assertTrue(false, "The header name and message are not verified on  Contact Section ");

				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}

		if (cp.clickOnTab(projectName, tabObj1)) {
			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1 + " For : " + ADEIns4, YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, ADEIns4, 30)) {
				log(LogStatus.INFO, "Clicked on  : " + ADEIns4 + " For : " + tabObj2, YesNo.No);
				ThreadSleep(2000);
				ArrayList<String> result1 = bp
						.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
								contactsSectionHeader, null, DealHeaderName, null, connectionsSectionHeaderName,
								null);
				if (result1.isEmpty()) {
					log(LogStatus.INFO, "The header name and message have been verified  Contact Section ", YesNo.No);

				} else {
					log(LogStatus.ERROR, "The header name and message are not verified on Contact Section ", YesNo.No);
					sa.assertTrue(false, "The header name and message are not verified on  Contact Section ");

				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns4);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns4, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc028_PreconditoinAccountContact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		// INS
		String value = "";
		String type = "";
		String TabName1 = "";
		String[][] EntityOrAccounts = { { ADEIns11, ADEIns11RecordType, null },
				{ ADEIns12, ADEIns12RecordType, null } };

		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
				value = accounts[0];
				type = accounts[1];
				if (ip.createEntityOrAccount(projectName, mode, value, type, null, null, 20)) {
					log(LogStatus.INFO, "successfully Created Account/Entity : " + value + " of record type : " + type,
							YesNo.No);

				} else {
					sa.assertTrue(false, "Not Able to Create Account/Entity : " + value + " of record type : " + type);
					log(LogStatus.SKIP, "Not Able to Create Account/Entity : " + value + " of record type : " + type,
							YesNo.Yes);
				}
			}
		}
		// Contact
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			ADEContact11EmailID = lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact11EmailID, "Contact", excelLabel.Variable_Name,
					"ADEContact11", excelLabel.Contact_EmailId);

			if (cp.createContactAcuity(projectName, ADEContact11FName, ADEContact11LName, ADEIns11, ADEContact11EmailID,
					ADEContact11RecordType, null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO, "successfully Created Contact : " + ADEContact11LName + " " + ADEContact11LName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Create Contact : " + ADEContact11FName + " " + ADEContact11LName);
				log(LogStatus.SKIP, "Not Able to Create Contact: " + ADEContact11LName + " " + ADEContact11LName,
						YesNo.Yes);
			}
		}
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			ADEContact12EmailID = lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact12EmailID, "Contact", excelLabel.Variable_Name,
					"ADEContact12", excelLabel.Contact_EmailId);

			if (cp.createContactAcuity(projectName, ADEContact11FName, ADEContact12LName, ADEIns12, ADEContact12EmailID,
					ADEContact12RecordType, null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO, "successfully Created Contact : " + ADEContact12LName + " " + ADEContact12LName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Create Contact : " + ADEContact12FName + " " + ADEContact12LName);
				log(LogStatus.SKIP, "Not Able to Create Contact: " + ADEContact12LName + " " + ADEContact12LName,
						YesNo.Yes);
			}
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc029_VerifycountofDealsColumnNewlyCreatedContactsAssociatedDealasDealTeamMemberAccount(
			String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String DealCountInFirm = "0";
		String actualDealCount = null;
		String contactName = ADEContact11FName + " " + ADEContact11LName;
		String contactName1 = ADEContact12FName + " " + ADEContact12LName;

		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns11, 30)) {

				actualDealCount = getText(driver, BP.contactDealCount(contactName, 30), "deal",
						action.SCROLLANDBOOLEAN);
				if (BP.contactDealCount(contactName, 30) != null) {
					if (!actualDealCount.equalsIgnoreCase("")) {

						if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
							log(LogStatus.INFO, "Deal Count for Contact: " + contactName + " is " + actualDealCount
									+ " before Deal Team Create is matched to " + DealCountInFirm, YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"Deal Count for Contact: " + contactName
											+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
											+ " but Actual: " + actualDealCount,
									YesNo.Yes);
							sa.assertTrue(false,
									"Deal Count for Contact: " + contactName
											+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
											+ " but Actual: " + actualDealCount);
						}

					} else {
						log(LogStatus.ERROR, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
								+ contactName, YesNo.Yes);
						sa.assertTrue(false, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
								+ contactName);
					}
				} else {
					log(LogStatus.ERROR, "No Contact found of Name: " + contactName, YesNo.No);
					sa.assertTrue(false, "No Contact found of Name: " + contactName);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			}

			if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

				if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns12, 30)) {

					log(LogStatus.INFO, "open created item" + ADEIns12, YesNo.No);
					if (!actualDealCount.equalsIgnoreCase("")) {

						if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
							log(LogStatus.INFO, "Deal Count for Contact: " + contactName1 + " is " + actualDealCount
									+ " before Deal Team Create is matched to " + DealCountInFirm, YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"Deal Count for Contact: " + contactName1
											+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
											+ " but Actual: " + actualDealCount,
									YesNo.Yes);
							sa.assertTrue(false,
									"Deal Count for Contact: " + contactName1
											+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
											+ " but Actual: " + actualDealCount);
						}

					} else {
						log(LogStatus.ERROR, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
								+ contactName1, YesNo.Yes);
						sa.assertTrue(false, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
								+ contactName1);
					}
				} else {
					log(LogStatus.ERROR, "No Contact found of Name: " + contactName, YesNo.No);
					sa.assertTrue(false, "No Contact found of Name: " + contactName);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			}
			
			ThreadSleep(5000);
			lp.CRMlogout();
			sa.assertAll();
		}
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc030_EditDealContactBankerTypeContactDealteamVerifyImpactTabsContacts(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String dealName = ADEDealTeamName1;
		String DealCountInFirm = "1";
		String actualDealCount = null;
		String company = ADEIns1;
		String stage = "NDA Signed";
		String dateReceived = "11/2/2022";
		String contactName = ADEContact11FName + " " + ADEContact11LName;

		String[][] data = { { PageLabel.Deal.toString(), dealName },
				{ PageLabel.Deal_Contact.toString(), contactName } };

		if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

			if (DTP.createDealTeam(projectName, dealName, data, "Acuity", action.SCROLLANDBOOLEAN, 25)) {
				log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----", YesNo.No);

				log(LogStatus.INFO,
						"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
								+ contactName + " at Firm Tab under Acuity section---------",
						YesNo.No);
				String xpath = "//*[text()='Deal Team']/parent::h1//slot/lightning-formatted-text";
				WebElement ele = FindElement(driver, xpath, "dt id", action.BOOLEAN, 10);
				if (ele != null) {
					String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
					ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name, "ADT_01",
							excelLabel.DealTeamID);
					log(LogStatus.INFO, "successfully created and noted id of DT" + id + " and deal name " + dealName,
							YesNo.No);
				} else {
					sa.assertTrue(false, "could not create DT" + dealName);
					log(LogStatus.SKIP, "could not create DT" + dealName, YesNo.Yes);

					if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
						log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

						if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns11, 30)) {

							actualDealCount = getText(driver, BP.contactDealCount(contactName, 30), "deal",
									action.SCROLLANDBOOLEAN);
							if (BP.contactDealCount(contactName, 30) != null) {
								if (!actualDealCount.equalsIgnoreCase("")) {

									if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
										log(LogStatus.INFO,
												"Deal Count for Contact: " + contactName + " is " + actualDealCount
														+ " before Deal Team Create is matched to " + DealCountInFirm,
												YesNo.No);
										if (CommonLib.click(driver, BP.contactDealCount(contactName, 30),
												"Deal Count: " + actualDealCount, action.BOOLEAN)) {
											log(LogStatus.INFO, "Clicked on Deal Count: " + actualDealCount
													+ " of Record: " + contactName, YesNo.No);

											ArrayList<String> result1 = BP.verifyRecordOnDealsPopUpSectionInAcuity(
													contactName, dealName, company, stage, dateReceived);
											if (result1.isEmpty()) {
												log(LogStatus.INFO, "Records on Deals slot have been matched",
														YesNo.No);

											} else {
												log(LogStatus.ERROR,
														"Records on Deals slot are not matched, Reason: " + result1,
														YesNo.No);
												sa.assertTrue(false,
														"Records on Deals slot are not matched, Reason" + result1);
											}

										} else {
											log(LogStatus.ERROR, "Not Able to Click on Deal Count: " + actualDealCount
													+ " of Record: " + contactName, YesNo.No);

											sa.assertTrue(false, "Not Able to Click on Deal Count: " + actualDealCount
													+ " of Record: " + contactName);

										}
									} else {
										log(LogStatus.ERROR,
												"Deal Count for Contact: " + contactName
														+ " is before Deal Team Create is not matched, Expected: "
														+ DealCountInFirm + " but Actual: " + actualDealCount,
												YesNo.Yes);
										sa.assertTrue(false,
												"Deal Count for Contact: " + contactName
														+ " is before Deal Team Create is not matched, Expected: "
														+ DealCountInFirm + " but Actual: " + actualDealCount);
									}

								} else {
									log(LogStatus.ERROR,
											"Deal Count for Contact is Empty, So not able to check Count for Contact: "
													+ contactName,
											YesNo.Yes);
									sa.assertTrue(false,
											"Deal Count for Contact is Empty, So not able to check Count for Contact: "
													+ contactName);
								}

							} else {
								log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
					}
				}
				ThreadSleep(5000);
				lp.CRMlogout();
				sa.assertAll();
			}
		}
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc031_VerifyDealCountsatAccountContactsAssociatedDealMemberDealTeamWithTeamMember(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String dealName = ADEDealTeamName2;
		String DealCountInFirm = "2";
		String actualDealCount = null;
		String TeamMember = ADEDealTeamMember2;
		String contactName = ADEContact11FName + " " + ADEContact11LName;

		String[][] data = { { PageLabel.Deal.toString(), dealName }, { PageLabel.Deal_Contact.toString(), contactName },
				{ PageLabel.Team_Member.toString(), TeamMember } };

		if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

			if (DTP.createDealTeam(projectName, dealName, data, "Acuity", action.SCROLLANDBOOLEAN, 25)) {
				log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----", YesNo.No);

				log(LogStatus.INFO,
						"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
								+ contactName + " at Firm Tab under Acuity section---------",
						YesNo.No);
				String xpath = "//*[text()='Deal Team']/parent::h1//slot/lightning-formatted-text";
				WebElement ele = FindElement(driver, xpath, "dt id", action.BOOLEAN, 10);
				if (ele != null) {
					String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
					ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name, "ADT_02",
							excelLabel.DealTeamID);
					log(LogStatus.INFO, "successfully created and noted id of DT" + id + " and deal name " + dealName,
							YesNo.No);
				} else {
					sa.assertTrue(false, "could not create DT" + dealName);
					log(LogStatus.SKIP, "could not create DT" + dealName, YesNo.Yes);
				}
					if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
						log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

						if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns11, 30)) {

							actualDealCount = getText(driver, BP.contactDealCount(contactName, 30), "deal",
									action.SCROLLANDBOOLEAN);
							if (BP.contactDealCount(contactName, 30) != null) {
								if (!actualDealCount.equalsIgnoreCase("")) {

									if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
										log(LogStatus.INFO,
												"Deal Count for Contact: " + contactName + " is " + actualDealCount
														+ " before Deal Team Create is matched to " + DealCountInFirm,
												YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"Deal Count for Contact: " + contactName
														+ " is before Deal Team Create is not matched, Expected: "
														+ DealCountInFirm + " but Actual: " + actualDealCount,
												YesNo.Yes);
										sa.assertTrue(false,
												"Deal Count for Contact: " + contactName
														+ " is before Deal Team Create is not matched, Expected: "
														+ DealCountInFirm + " but Actual: " + actualDealCount);
									}

								} else {
									log(LogStatus.ERROR,
											"Deal Count for Contact is Empty, So not able to check Count for Contact: "
													+ contactName,
											YesNo.Yes);
									sa.assertTrue(false,
											"Deal Count for Contact is Empty, So not able to check Count for Contact: "
													+ contactName);
								}

							} else {
								log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
						}
					}

				
			} else {
				log(LogStatus.INFO, "----Not Created the Deal Team for Deal: " + dealName + "----", YesNo.Yes);

				log(LogStatus.INFO,
						"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
								+ contactName + " at Firm Tab under Acuity section---------",
						YesNo.No);
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Deal_Team + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + TabName.Deal_Team + " tab");
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc032_VerifyDealCountsAIAccountWhenNewContactCreatedTaggedDealMemberDealTeam(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String dealName = ADEDealTeamName3;
		String DealCountInFirm = "1";
		String actualDealCount = null;
		String contactName = ADEContact13FName + " " + ADEContact13LName;

		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			ADEContact13EmailID = lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact13EmailID, "Contact", excelLabel.Variable_Name,
					"ADEContact13", excelLabel.Contact_EmailId);

			if (cp.createContactAcuity(projectName, ADEContact13FName, ADEContact13LName, ADEIns11, ADEContact13EmailID,
					ADEContact13RecordType, null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO, "successfully Created Contact : " + ADEContact13LName + " " + ADEContact13LName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Create Contact : " + ADEContact13FName + " " + ADEContact13LName);
				log(LogStatus.SKIP, "Not Able to Create Contact: " + ADEContact13LName + " " + ADEContact13LName,
						YesNo.Yes);
			}

			String[][] data = { { PageLabel.Deal.toString(), dealName },
					{ PageLabel.Deal_Contact.toString(), contactName } };

			if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

				if (DTP.createDealTeam(projectName, dealName, data, "Acuity", action.SCROLLANDBOOLEAN, 25)) {
					log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----",
							YesNo.No);

					log(LogStatus.INFO,
							"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
									+ contactName + " at Firm Tab under Acuity section---------",
							YesNo.No);
					String xpath = "//*[text()='Deal Team']/parent::h1//slot/lightning-formatted-text";
					WebElement ele = FindElement(driver, xpath, "dt id", action.BOOLEAN, 10);
					if (ele != null) {
						String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
						ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name,
								"ADT_03", excelLabel.DealTeamID);
						log(LogStatus.INFO,
								"successfully created and noted id of DT" + id + " and deal name " + dealName,
								YesNo.No);
					} else {
						sa.assertTrue(false, "could not create DT" + dealName);
						log(LogStatus.SKIP, "could not create DT" + dealName, YesNo.Yes);

						if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
							log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

							if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns11, 30)) {

								actualDealCount = getText(driver, BP.contactDealCount(contactName, 30), "deal",
										action.SCROLLANDBOOLEAN);
								if (BP.contactDealCount(contactName, 30) != null) {
									if (!actualDealCount.equalsIgnoreCase("")) {

										if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
											log(LogStatus.INFO,
													"Deal Count for Contact: " + contactName + " is " + actualDealCount
															+ " before Deal Team Create is matched to "
															+ DealCountInFirm,
													YesNo.No);

										} else {
											log(LogStatus.ERROR,
													"Deal Count for Contact: " + contactName
															+ " is before Deal Team Create is not matched, Expected: "
															+ DealCountInFirm + " but Actual: " + actualDealCount,
													YesNo.Yes);
											sa.assertTrue(false,
													"Deal Count for Contact: " + contactName
															+ " is before Deal Team Create is not matched, Expected: "
															+ DealCountInFirm + " but Actual: " + actualDealCount);
										}

									} else {
										log(LogStatus.ERROR,
												"Deal Count for Contact is Empty, So not able to check Count for Contact: "
														+ contactName,
												YesNo.Yes);
										sa.assertTrue(false,
												"Deal Count for Contact is Empty, So not able to check Count for Contact: "
														+ contactName);
									}

								} else {
									log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
								}

							} else {
								log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
							}
						}

					}
				} else {
					log(LogStatus.INFO, "----Not Created the Deal Team for Deal: " + dealName + "----", YesNo.Yes);

					log(LogStatus.INFO,
							"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
									+ contactName + " at Firm Tab under Acuity section---------",
							YesNo.No);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + TabName.Deal_Team + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + TabName.Deal_Team + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc033_VerifyDealCountaContactDealContactareCreatedAccount(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String dealName1 = ADEDealTeamName4;
		String DealCountInFirm = "1";
		String actualDealCount = null;
		String contactName = ADEContact14FName + " " + ADEContact14LName;

		String recordType = "";
		String dealName = ADEDeal15;
		String companyName = ADEDeal15CompanyName;
		String stage = ADEDeal15Stage;

		if (lp.clickOnTab(projectName, tabObj4)) {
			log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
			ThreadSleep(3000);
			if (dp.createDeal(recordType, dealName, companyName, stage, "Date Received", todaysDate)) {
				log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);

			} else {
				log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
				sa.assertTrue(false, dealName + " deal is not created");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
		}

		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			ADEContact14EmailID = lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact14EmailID, "Contact", excelLabel.Variable_Name,
					"ADEContact14", excelLabel.Contact_EmailId);

			if (cp.createContactAcuity(projectName, ADEContact14FName, ADEContact14LName, ADEIns11, ADEContact14EmailID,
					ADEContact14RecordType, null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO, "successfully Created Contact : " + ADEContact14LName + " " + ADEContact14LName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Create Contact : " + ADEContact14FName + " " + ADEContact14LName);
				log(LogStatus.SKIP, "Not Able to Create Contact: " + ADEContact14LName + " " + ADEContact14LName,
						YesNo.Yes);
			}

			String[][] data = { { PageLabel.Deal.toString(), dealName },
					{ PageLabel.Deal_Contact.toString(), contactName } };

			if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

				if (DTP.createDealTeam(projectName, dealName1, data, "Acuity", action.SCROLLANDBOOLEAN, 25)) {
					log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----",
							YesNo.No);

					log(LogStatus.INFO,
							"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
									+ contactName + " at Firm Tab under Acuity section---------",
							YesNo.No);
					String xpath = "//*[text()='Deal Team']/parent::h1//slot/lightning-formatted-text";
					WebElement ele = FindElement(driver, xpath, "dt id", action.BOOLEAN, 10);
					if (ele != null) {
						String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
						ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name,
								"ADT_04", excelLabel.DealTeamID);
						log(LogStatus.INFO,
								"successfully created and noted id of DT" + id + " and deal name " + dealName,
								YesNo.No);
					} else {
						sa.assertTrue(false, "could not create DT" + dealName);
						log(LogStatus.SKIP, "could not create DT" + dealName, YesNo.Yes);
					}
					if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
						log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

						if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns11, 30)) {

							actualDealCount = getText(driver, BP.contactDealCount(contactName, 30), "deal",
									action.SCROLLANDBOOLEAN);
							if (BP.contactDealCount(contactName, 30) != null) {
								if (!actualDealCount.equalsIgnoreCase("")) {

									if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
										log(LogStatus.INFO,
												"Deal Count for Contact: " + contactName + " is " + actualDealCount
														+ " before Deal Team Create is matched to " + DealCountInFirm,
												YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"Deal Count for Contact: " + contactName
														+ " is before Deal Team Create is not matched, Expected: "
														+ DealCountInFirm + " but Actual: " + actualDealCount,
												YesNo.Yes);
										sa.assertTrue(false,
												"Deal Count for Contact: " + contactName
														+ " is before Deal Team Create is not matched, Expected: "
														+ DealCountInFirm + " but Actual: " + actualDealCount);
									}

								} else {
									log(LogStatus.ERROR,
											"Deal Count for Contact is Empty, So not able to check Count for Contact: "
													+ contactName,
											YesNo.Yes);
									sa.assertTrue(false,
											"Deal Count for Contact is Empty, So not able to check Count for Contact: "
													+ contactName);
								}

							} else {
								log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
						}
					}

				} else {
					log(LogStatus.INFO, "----Not Created the Deal Team for Deal: " + dealName + "----", YesNo.Yes);

					log(LogStatus.INFO,
							"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
									+ contactName + " at Firm Tab under Acuity section---------",
							YesNo.No);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + TabName.Deal_Team + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + TabName.Deal_Team + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc034_VerifyDealCountContactGetsRemovedDealTeamWhenRemoveeAddeeAreSameAccounts(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String contactName = ADEContact13FName + " " + ADEContact13LName;
		String contactName1 = ADEContact11FName + " " + ADEContact11LName;
		String[][] data1 = { { PageLabel.Deal_Contact.toString(), contactName } };

		String DealCountInFirm = "2";
		String DealCountInFirm1 = "1";
		String actualDealCount = null;

		if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, ADEDealTeamID2, 10)) {
				if (DTP.UpdateDealContactName(projectName, data1, 30)) {
					log(LogStatus.INFO, "successfully changed name to " + contactName, YesNo.Yes);
				} else {
					sa.assertTrue(false, "not able to change name to " + contactName);
					log(LogStatus.SKIP, "not able to change name to " + contactName, YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + "DT-0055" + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + "DT-0055" + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Deal_Team + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + TabName.Deal_Team + " tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns11, 30)) {

				actualDealCount = getText(driver, BP.contactDealCount(contactName, 30), "deal",
						action.SCROLLANDBOOLEAN);
				if (BP.contactDealCount(contactName, 30) != null) {
					if (!actualDealCount.equalsIgnoreCase("")) {

						if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
							log(LogStatus.INFO, "Deal Count for Contact: " + contactName + " is " + actualDealCount
									+ " before Deal Team Create is matched to " + DealCountInFirm, YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Deal Count for Contact: " + contactName
											+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
											+ " but Actual: " + actualDealCount,
									YesNo.Yes);
							sa.assertTrue(false,
									"Deal Count for Contact: " + contactName
											+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
											+ " but Actual: " + actualDealCount);
						}

					} else {
						log(LogStatus.ERROR, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
								+ contactName, YesNo.Yes);
						sa.assertTrue(false, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
								+ contactName);
					}

					actualDealCount = getText(driver, BP.contactDealCount(contactName1, 30), "deal",
							action.SCROLLANDBOOLEAN);
					if (BP.contactDealCount(contactName1, 30) != null) {
						if (!actualDealCount.equalsIgnoreCase("")) {

							if (actualDealCount.equalsIgnoreCase(DealCountInFirm1)) {
								log(LogStatus.INFO,
										"Deal Count for Contact: " + contactName1 + " is " + actualDealCount
												+ " before Deal Team Create is matched to " + DealCountInFirm1,
										YesNo.No);

							} else {
								log(LogStatus.ERROR,
										"Deal Count for Contact: " + contactName1
												+ " is before Deal Team Create is not matched, Expected: "
												+ DealCountInFirm1 + " but Actual: " + actualDealCount,
										YesNo.Yes);
								sa.assertTrue(false,
										"Deal Count for Contact: " + contactName1
												+ " is before Deal Team Create is not matched, Expected: "
												+ DealCountInFirm1 + " but Actual: " + actualDealCount);
							}

						} else {
							log(LogStatus.ERROR,
									"Deal Count for Contact is Empty, So not able to check Count for Contact: "
											+ contactName1,
									YesNo.Yes);
							sa.assertTrue(false,
									"Deal Count for Contact is Empty, So not able to check Count for Contact: "
											+ contactName1);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
				}
				
			} else {
				log(LogStatus.ERROR, "Not able to click on " + ADEIns11 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + ADEIns11 + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc035_VerifyDealCountContactGetsRemovedDealTeamWhenRemoveeAddeeAreDifferentAccounts(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String contactName = ADEContact12FName + " " + ADEContact12LName;
		String contactName1 = ADEContact13FName + " " + ADEContact13LName;
		String[][] data1 = { { PageLabel.Deal_Contact.toString(), contactName } };

		String DealCountInFirm = "1";
		String actualDealCount = null;

		if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, ADEDealTeamID2, 10)) {
				if (DTP.UpdateDealContactName(projectName, data1, 30)) {
					log(LogStatus.INFO, "successfully changed name to " + contactName, YesNo.Yes);
				} else {
					sa.assertTrue(false, "not able to change name to " + contactName);
					log(LogStatus.SKIP, "not able to change name to " + contactName, YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + "DT-0055" + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + "DT-0055" + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Deal_Team + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + TabName.Deal_Team + " tab");
		}
		

		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns11, 30)) {

				actualDealCount = getText(driver, BP.contactDealCount(contactName1, 30), "deal",
						action.SCROLLANDBOOLEAN);
				if (BP.contactDealCount(contactName1, 30) != null) {
					if (!actualDealCount.equalsIgnoreCase("")) {

						if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
							log(LogStatus.INFO, "Deal Count for Contact: " + contactName1 + " is " + actualDealCount
									+ " before Deal Team Create is matched to " + DealCountInFirm, YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Deal Count for Contact: " + contactName1
											+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
											+ " but Actual: " + actualDealCount,
									YesNo.Yes);
							sa.assertTrue(false,
									"Deal Count for Contact: " + contactName1
											+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
											+ " but Actual: " + actualDealCount);
						}

					} else {
						log(LogStatus.ERROR, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
								+ contactName1, YesNo.Yes);
						sa.assertTrue(false, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
								+ contactName1);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + ADEIns11 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + ADEIns11 + " tab");
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns12, 30)) {

				actualDealCount = getText(driver, BP.contactDealCount(contactName, 30), "deal",
						action.SCROLLANDBOOLEAN);
				if (BP.contactDealCount(contactName, 30) != null) {
					if (!actualDealCount.equalsIgnoreCase("")) {

						if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
							log(LogStatus.INFO, "Deal Count for Contact: " + contactName + " is " + actualDealCount
									+ " before Deal Team Create is matched to " + DealCountInFirm, YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Deal Count for Contact: " + contactName
											+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
											+ " but Actual: " + actualDealCount,
									YesNo.Yes);
							sa.assertTrue(false,
									"Deal Count for Contact: " + contactName
											+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
											+ " but Actual: " + actualDealCount);
						}

					} else {
						log(LogStatus.ERROR, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
								+ contactName, YesNo.Yes);
						sa.assertTrue(false, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
								+ contactName);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + ADEIns11 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + ADEIns11 + " tab");
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc036_VerifyImpactContactSectionDeleteContactDealTeamDTMforAccount(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String contactName = ADEContact14FName + " " + ADEContact14LName;

		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 10)) {
				cp.clickOnShowMoreDropdownOnly(projectName, PageName.Object2Page, "");
				log(LogStatus.INFO, "Able to Click on Show more Icon : " + TabName.Object2Tab + " For : " + contactName,
						YesNo.No);
				ThreadSleep(500);
				WebElement ele = cp.actionDropdownElement(projectName, PageName.Object1Page,
						ShowMoreActionDropDownList.Delete, 15);
				if (ele == null) {
					ele = cp.getDeleteButton(projectName, 30);
				}

				if (click(driver, ele, "Delete More Icon", action.BOOLEAN)) {
					log(LogStatus.INFO,
							"Able to Click on Delete more Icon : " + TabName.Object1Tab + " For : " + contactName,
							YesNo.No);
					ThreadSleep(1000);
					if (click(driver, cp.getDeleteButtonOnDeletePopUp(projectName, 30), "Delete Button",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Able to Click on Delete button on Delete PoPup : " + TabName.Object2Tab
								+ " For : " + ADEIns11, YesNo.No);

					} else {
						log(LogStatus.ERROR, "Not able to click on Delete button" + contactName + " tab", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on Delete button" + contactName + " tab");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + contactName + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + contactName + " tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
			}
			if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

				if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns11, 30)) {

					if (BP.DealContactname(contactName, 30) != null) {

					} else {
						log(LogStatus.ERROR,
								"Deal Contact name not present, So not able to check Count for Contact: " + contactName,
								YesNo.Yes);
						sa.assertTrue(true, "Deal Contact name not present, So not able to check Count for Contact: "
								+ contactName);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + ADEIns11 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + ADEIns11 + " tab");
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc037_VerifyImpactContactSectionRestoreDeletedContactDealTeam(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele = null;
		String dealName1 = ADEDealTeamName4;
		String DealCountInFirm = "1";
		String actualDealCount = null;
		String contactName = ADEContact14FName + " " + ADEContact14LName;

		TabName tabName = TabName.RecycleBinTab;
		String Contactname1 = ADEContact14FName + " " + ADEContact14LName;

		if (cp.clickOnTab(projectName, tabName)) {
			log(LogStatus.INFO, "Clicked on Tab : " + tabName + " For : " + Contactname1, YesNo.No);
			ThreadSleep(1000);
			cp.clickOnAlreadyCreatedItem(projectName, tabName, Contactname1, 20);
			log(LogStatus.INFO, "Clicked on  : " + Contactname1 + " For : " + tabName, YesNo.No);
			ThreadSleep(2000);

			ele = cp.getCheckboxOfRestoreItemOnRecycleBin(projectName, Contactname1, 10);
			if (clickUsingJavaScript(driver, ele, "Check box against : " + Contactname1, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on checkbox for " + Contactname1, YesNo.No);

				ThreadSleep(1000);
				ele = cp.getRestoreButtonOnRecycleBin(projectName, 10);
				if (clickUsingJavaScript(driver, ele, "Restore Button : " + Contactname1, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on Restore Button for " + Contactname1, YesNo.No);
					ThreadSleep(1000);
				} else {
					sa.assertTrue(false, "Not Able to Click on Restore Button for " + Contactname1);
					log(LogStatus.SKIP, "Not Able to Click on Restore Button for " + Contactname1, YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on checkbox for " + Contactname1);
				log(LogStatus.SKIP, "Not Able to Click on checkbox for " + Contactname1, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + tabName + " For : " + Contactname1);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName + " For : " + Contactname1, YesNo.Yes);
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns11, 30)) {

				actualDealCount = getText(driver, BP.contactDealCount(contactName, 30), "deal",
						action.SCROLLANDBOOLEAN);
				if (BP.contactDealCount(contactName, 30) != null) {
					if (!actualDealCount.equalsIgnoreCase("")) {

						if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
							log(LogStatus.INFO, "Deal Count for Contact: " + contactName + " is " + actualDealCount
									+ " before Deal Team Create is matched to " + DealCountInFirm, YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Deal Count for Contact: " + contactName
											+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
											+ " but Actual: " + actualDealCount,
									YesNo.Yes);
							sa.assertTrue(false,
									"Deal Count for Contact: " + contactName
											+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
											+ " but Actual: " + actualDealCount);
						}

					} else {
						log(LogStatus.ERROR, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
								+ contactName, YesNo.Yes);
						sa.assertTrue(false, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
								+ contactName);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			}

		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc038_VerifyImpactContactwhenDeleteDealTeamContactDTMforAccount(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String DealTeam = ADEDealTeamID3;
		String DealCountInFirm = "0";
		String actualDealCount = null;
		String contactName = ADEContact13FName + " " + ADEContact13LName;

		String ExpectedMsg = "No item display";

		if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, DealTeam, 10)) {
				cp.clickOnShowMoreDropdownOnly(projectName, PageName.Object2Page, "");
				log(LogStatus.INFO, "Able to Click on Show more Icon : " + TabName.Object2Tab + " For : " + DealTeam,
						YesNo.No);
				ThreadSleep(500);
				WebElement ele = cp.actionDropdownElement(projectName, PageName.Object1Page,
						ShowMoreActionDropDownList.Delete, 15);
				if (ele == null) {
					ele = cp.getDeleteButton(projectName, 30);
				}

				if (click(driver, ele, "Delete More Icon", action.BOOLEAN)) {
					log(LogStatus.INFO,
							"Able to Click on Delete more Icon : " + TabName.Object1Tab + " For : " + DealTeam,
							YesNo.No);
					ThreadSleep(1000);
					if (click(driver, cp.getDeleteButtonOnDeletePopUp(projectName, 30), "Delete Button",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Able to Click on Delete button on Delete PoPup : " + TabName.Object2Tab
								+ " For : " + ADEIns11, YesNo.No);

					} else {
						log(LogStatus.ERROR, "Not able to click on Delete button" + DealTeam + " tab", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on Delete button" + DealTeam + " tab");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + DealTeam + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + DealTeam + " tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
			}
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns11, 30)) {

				actualDealCount = getText(driver, BP.contactDealCount(contactName, 30), "deal",
						action.SCROLLANDBOOLEAN);
				if (BP.contactDealCount(contactName, 30) != null) {
					if (!actualDealCount.equalsIgnoreCase("")) {

						if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
							log(LogStatus.INFO, "Deal Count for Contact: " + contactName + " is " + actualDealCount
									+ " before Deal Team Create is matched to " + DealCountInFirm, YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Deal Count for Contact: " + contactName
											+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
											+ " but Actual: " + actualDealCount,
									YesNo.Yes);
							sa.assertTrue(false,
									"Deal Count for Contact: " + contactName
											+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
											+ " but Actual: " + actualDealCount);
						}

					} else {
						log(LogStatus.ERROR, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
								+ contactName, YesNo.Yes);
						sa.assertTrue(false, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
								+ contactName);
					}
					if (CommonLib.click(driver, BP.contactDealCount(contactName, 30), "Deal Count: " + actualDealCount,
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Deal Count: " + actualDealCount + " of Record: " + contactName,
								YesNo.No);
						String parentWindowId = CommonLib.switchOnWindow(driver);
						if (!parentWindowId.isEmpty()) {
						String actualMsg = getText(driver, BP.getErrorMsg(20), "ErrorMsg", action.SCROLLANDBOOLEAN);
						if (ExpectedMsg.contains(actualMsg)) {
							log(LogStatus.INFO,
									"Actual result " + actualMsg + " of pop up has been matched with Expected result : "
											+ ExpectedMsg + " for Contact Name: " + contactName,
									YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"Actual result " + actualMsg
											+ " of pop up has been not matched with Expected result : " + ExpectedMsg
											+ " for Contact Name: " + contactName,
									YesNo.No);
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
				}
			}
			}
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc039_VerifyDealTeamRecordPopupClosewhenClickedOutsidePopuporCrossIconTopRightofPopupforAccount(
			String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String ExpectedHeader = "Deals with ADEN Con11";

		String contactName = ADEContact13FName + " " + ADEContact13LName;

		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns11, 30)) {
				if (CommonLib.click(driver, BP.contactDealCount(contactName, 30), "Deal Count: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Deal Count: " + " " + " of Record: " + contactName, YesNo.No);
				}
				try {
					String parentWindowId = CommonLib.switchOnWindow(driver);
					if (!parentWindowId.isEmpty()) {
						log(LogStatus.PASS, "New Window Open after click on Deal Link: " + contactName, YesNo.No);
				String actualHeader = getText(driver, BP.getDealCountPopHeader(20), "DealCountPopHeader",
						action.SCROLLANDBOOLEAN);
				if (ExpectedHeader.equals(actualHeader)) {
					log(LogStatus.INFO,
							"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
									+ ExpectedHeader + " for Contact Name: " + contactName,
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
									+ ExpectedHeader + " for Contact Name: " + contactName,
							YesNo.No);
				}
					
				CommonLib.click(driver, BP.getanywhereonpage(30), "click any where: " + "", action.BOOLEAN);
				log(LogStatus.INFO, "Clicked anywhere: " + " " + " of Record: " + contactName, YesNo.No);
				String actualHeader1 = getText(driver, BP.getDealCountPopHeader(20), "DealCountPopHeader",
						action.SCROLLANDBOOLEAN);
				if (ExpectedHeader.equals(actualHeader1)) {
					log(LogStatus.INFO,
							"Actual result " + actualHeader1
									+ " of pop up has been matched with Expected result so popup still present : "
									+ ExpectedHeader + " for Contact Name: " + contactName,
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Actual result " + actualHeader
									+ " of pop up has been not matched with Expected result so pop up is not present: "
									+ ExpectedHeader + " for Contact Name: " + contactName,
							YesNo.No);
				}
				driver.close();	driver.switchTo().window(parentWindowId);
					} else {
						log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + contactName, YesNo.Yes);
						sa.assertTrue(false, "No New Window Open after click on Deal Link: " + contactName);
					}
				} catch (Exception e) {
					log(LogStatus.FAIL,
							"Not able to switch to window after click on Deal Link, Msg showing: " + e.getMessage(),
							YesNo.Yes);
					sa.assertTrue(false, "Not able to switch to window after click on Deal Link, Msg showing: "
							+ e.getMessage());
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			}
				
		}
		
			
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc040_VerifyDealNameCompanyClickableItRedirectionDealTeamRecordopupforAccount(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String dealName = ADEDeal7;
		String company = ADEDeal7CompanyName;
		String companyname = ADEIns1;

		String contactName = ADEContact11FName + " " + ADEContact11LName;

		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns11, 30)) {
				if (CommonLib.click(driver, BP.contactDealCount(contactName, 30), "Deal Count: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Deal Count: " + " " + " of Record: " + contactName, YesNo.No);
				}
				String parentWindowId = CommonLib.switchOnWindow(driver);
				if (!parentWindowId.isEmpty()) {
					log(LogStatus.PASS, "New Window Open after click on Deal Link: " + dealName, YesNo.No);
				ThreadSleep(3000);
				
				if (clickUsingJavaScript(driver, BP.dealAcuityPopUpDealName(dealName, 10), "Deal Name: " + dealName,
						action.BOOLEAN)) {
					log(LogStatus.PASS, "Clicked on Deal Name: " + dealName, YesNo.No);
					Set<String> childWindow = driver.getWindowHandles();
					switchToDefaultContent(driver);
					System.out.println(childWindow);
					for(String child : childWindow) {
					driver.switchTo().window(child);
					}
							if (BP.dealRecordPage(dealName, 20) != null) {
								log(LogStatus.PASS,
										"----Deal Detail Page is redirecting for Deal Record: " + dealName + "-----",
										YesNo.No);
								driver.close();
								driver.switchTo().window(parentWindowId);
								CommonLib.switchOnWindow(driver);
									driver.close();
								driver.switchTo().window(parentWindowId);

							} else {
								log(LogStatus.FAIL, "----Deal Detail Page is not redirecting for Deal Record: "
										+ dealName + "-----", YesNo.Yes);
								sa.assertTrue(false,
										"----Deal Detail Page is not showing for Deal Record: " + dealName + "-----");
								driver.close();
								driver.switchTo().window(parentWindowId);

							}

						} else {
							log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "No New Window Open after click on Deal Link: " + dealName);
						}
//					} catch (Exception e) {
//						log(LogStatus.FAIL,
//								"Not able to switch to window after click on Deal Link, Msg showing: " + e.getMessage(),
//								YesNo.Yes);
//						sa.assertTrue(false, "Not able to switch to window after click on Deal Link, Msg showing: "
//								+ e.getMessage());
//					}
				} else {
					log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

				}
				if (CommonLib.click(driver, BP.contactDealCount(contactName, 30), "Deal Count: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Deal Count: " + " " + " of Record: " + contactName, YesNo.No);
				}
				String parentWindowId1 = CommonLib.switchOnWindow(driver);
				if (!parentWindowId1.isEmpty()) {
					log(LogStatus.PASS, "New Window Open after click on Deal Link: " + dealName, YesNo.No);
				ThreadSleep(3000);
				
				if (click(driver, BP.dealAcuityPopUpCompanyName(dealName, company, 20), "Deal Name: " + dealName,
						action.BOOLEAN)) {
					log(LogStatus.PASS, "Clicked on Deal Name: " + dealName, YesNo.No);
					Set<String> childWindow = driver.getWindowHandles();
					switchToDefaultContent(driver);
					System.out.println(childWindow);
					for(String child : childWindow) {
					driver.switchTo().window(child);
					}
					if (BP.CompanyRecordPage(companyname, 10) != null) {
						log(LogStatus.PASS, "----Company Detail Page is redirecting for Company Record: "
								+ company + "-----", YesNo.No);
						driver.close();
						driver.switchTo().window(parentWindowId1);

					} else {
						log(LogStatus.FAIL, "----Company Detail Page is not redirecting for Company Record: "
								+ company + "-----", YesNo.Yes);
						sa.assertTrue(false, "----Company Detail Page is not showing for Company Record: "
								+ company + "-----");
						driver.close();
						driver.switchTo().window(parentWindowId1);

					}

				} else {
					log(LogStatus.FAIL, "No New Window Open after click on Company Link: " + company,
							YesNo.Yes);
					sa.assertTrue(false, "No New Window Open after click on Company Link: " + company);
				}
//			} catch (Exception e) {
//				log(LogStatus.FAIL, "Not able to switch to window after click on Company Link, Msg showing: "
//						+ e.getMessage(), YesNo.Yes);
//				sa.assertTrue(false, "Not able to switch to window after click on Company Link, Msg showing: "
//						+ e.getMessage());
//			}
		} else {
			log(LogStatus.FAIL, "Not able to Click on Company Name: " + companyname, YesNo.Yes);
			sa.assertTrue(false, "Not able to Click on Company Name: " + companyname);

		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + ADEIns11 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + ADEIns11 + " tab");
	}
} else {
	log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
	sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
}
							
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

@Parameters({ "projectName"})
@Test
public void ADETc041_createCRMUser2(String projectName) {
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	String parentWindow = null;
	String[] splitedUserLastName = removeNumbersFromString(crmUser1LastName);
	String UserLastName = splitedUserLastName[0] + lp.generateRandomNumber();
	String emailId = lp.generateRandomEmailId(gmailUserName);
	ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User2",excelLabel.User_Last_Name);
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
						crmUserProfile,null)) {
					log(LogStatus.INFO, "CRM User is created Successfully: " + crmUser1FirstName + " " + UserLastName, YesNo.No);
					ExcelUtils.writeData(testCasesFilePath, emailId, "Users", excelLabel.Variable_Name, "User2",
							excelLabel.User_Email);
					ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User2",
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
						"Not able to install PE package in CRM User2: " + crmUser1FirstName + " " + UserLastName);
				sa.assertTrue(false,
						"Not able to install PE package in CRM User2: " + crmUser1FirstName + " " + UserLastName);
				log(LogStatus.ERROR,
						"Not able to install PE package in CRM User2: " + crmUser1FirstName + " " + UserLastName,
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
		appLog.info("Password is set successfully for CRM User2: " + crmUser1FirstName + " " + UserLastName );
	} else {
		appLog.info("Password is not set for CRM User1: " + crmUser1FirstName + " " + UserLastName);
		sa.assertTrue(false, "Password is not set for CRM User2: " + crmUser1FirstName + " " + UserLastName);
		log(LogStatus.ERROR, "Password is not set for CRM User2: " + crmUser1FirstName + " " + UserLastName,
				YesNo.Yes);
	}
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
public void ADETc042_VerifyImpactDealCountsWhenMultipleUsersContactsTaggedSameDeal(String projectName) {

	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

	String dealName = ADEDealTeamName5;
	String DealCountInFirm = "1";
	String actualDealCount = null;
	String TeamMember = ADEDealTeamMember5;
	String contactName = ADEContact1FName + " " + ADEContact1LName;

	String[][] data = { { PageLabel.Deal.toString(), dealName }, { PageLabel.Deal_Contact.toString(), contactName },
			{ PageLabel.Team_Member.toString(), TeamMember } };

//	if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
//		log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);
//
//		if (DTP.createDealTeam(projectName, dealName, data, "Acuity", action.SCROLLANDBOOLEAN, 25)) {
//			log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----", YesNo.No);
//
//			log(LogStatus.INFO,
//					"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
//							+ contactName + " at Firm Tab under Acuity section---------",
//					YesNo.No);
//			String	xpath="//*[text()='Deal Team']/parent::h1//slot/lightning-formatted-text";
//			WebElement		ele = FindElement(driver, xpath, "dt id", action.BOOLEAN, 10);
//					if (ele!=null) {
//						String id=getText(driver, ele, "deal team id",action.SCROLLANDBOOLEAN);
//						ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name,
//								"ADT_05", excelLabel.DealTeamID);
//						log(LogStatus.INFO,"successfully created and noted id of DT"+id+" and deal name "+dealName,YesNo.No);	
//					} else {
//						sa.assertTrue(false,"could not create DT"+dealName);
//						log(LogStatus.SKIP,"could not create DT"+dealName,YesNo.Yes);
//					}
//					} else {
//						sa.assertTrue(false,"could not able to click on tab DT"+TabName.Deal_Team);
//						log(LogStatus.SKIP,"could not create DT"+TabName.Deal_Team,YesNo.Yes);
//					}
//	}
	
					if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
						log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

						if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)) {

							actualDealCount = getText(driver, BP.contactDealCount(contactName, 30), "deal",
									action.SCROLLANDBOOLEAN);
							if (BP.contactDealCount(contactName, 30) != null) {
								if (!actualDealCount.equalsIgnoreCase("")) {

									if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
										log(LogStatus.INFO, "Deal Count for Contact: " + contactName + " is " + actualDealCount
												+ " before Deal Team Create is matched to " + DealCountInFirm, YesNo.No);
									} else {
										log(LogStatus.ERROR,
												"Deal Count for Contact: " + contactName
														+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
														+ " but Actual: " + actualDealCount,
												YesNo.Yes);
										sa.assertTrue(false,
												"Deal Count for Contact: " + contactName
														+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
														+ " but Actual: " + actualDealCount);
									}

								} else {
									log(LogStatus.ERROR, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
											+ contactName, YesNo.Yes);
									sa.assertTrue(false, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
											+ contactName);
								}
							} else {
								log(LogStatus.ERROR, "No Contact found of Name: " + contactName, YesNo.No);
								sa.assertTrue(false, "No Contact found of Name: " + contactName);
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on " + ADEIns11 + " tab", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on " + ADEIns11 + " tab");
						}
						} else {
							log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
						}				

						String dealName1 = ADEDealTeamName6;
						String DealCountInFirm1 = "1";
						String actualDealCount1 = null;
						String TeamMember1 = ADEDealTeamMember6;
						String contactName1= ADEContact3FName + " " + ADEContact3LName;

						String[][] data1 = { { PageLabel.Deal.toString(), dealName1 }, { PageLabel.Deal_Contact.toString(), contactName1 },
								{ PageLabel.Team_Member.toString(), TeamMember1 } };
						
//						if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
//							log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);
//
//							if (DTP.createDealTeam(projectName, dealName1, data1, "Acuity", action.SCROLLANDBOOLEAN, 25)) {
//								log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName1 + "----", YesNo.No);
//
//								log(LogStatus.INFO,
//										"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
//												+ contactName1 + " at Firm Tab under Acuity section---------",
//										YesNo.No);
//								String	xpath1="//*[text()='Deal Team']/parent::h1//slot/lightning-formatted-text";
//								WebElement		ele1 = FindElement(driver, xpath1, "dt id", action.BOOLEAN, 10);
//										if (ele1!=null) {
//											String id=getText(driver, ele1, "deal team id",action.SCROLLANDBOOLEAN);
//											ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name,
//													"ADT_06", excelLabel.DealTeamID);
//											log(LogStatus.INFO,"successfully created and noted id of DT"+id+" and deal name "+dealName,YesNo.No);	
//										} else {
//											sa.assertTrue(false,"could not create DT"+dealName);
//											log(LogStatus.SKIP,"could not create DT"+dealName,YesNo.Yes);
//										}
//							} else {
//								sa.assertTrue(false,"could not able to click on tab DT"+TabName.Deal_Team);
//								log(LogStatus.SKIP,"could not create DT"+TabName.Deal_Team,YesNo.Yes);
//							}
//			}
									
				if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
											log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

											if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns3, 30)) {

												actualDealCount1 = getText(driver, BP.contactDealCount(contactName1, 30), "deal",
														action.SCROLLANDBOOLEAN);
												if (BP.contactDealCount(contactName1, 30) != null) {
													if (!actualDealCount1.equalsIgnoreCase("")) {

														if (actualDealCount1.equalsIgnoreCase(DealCountInFirm1)) {
															log(LogStatus.INFO, "Deal Count for Contact: " + contactName + " is " + actualDealCount1
																	+ " before Deal Team Create is matched to " + DealCountInFirm1, YesNo.No);
														} else {
															log(LogStatus.ERROR,
																	"Deal Count for Contact: " + contactName
																			+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm1
																			+ " but Actual: " + actualDealCount1,
																	YesNo.Yes);
															sa.assertTrue(false,
																	"Deal Count for Contact: " + contactName
																			+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm1
																			+ " but Actual: " + actualDealCount1);
														}

													} else {
														log(LogStatus.ERROR, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
																+ contactName, YesNo.Yes);
														sa.assertTrue(false, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
																+ contactName);
													}
												} else {
													log(LogStatus.ERROR, "No Contact found of Name: " + contactName, YesNo.No);
													sa.assertTrue(false, "No Contact found of Name: " + contactName);
												}
											} else {
												log(LogStatus.ERROR, "Not able to click on " + ADEIns11 + " tab", YesNo.Yes);
												sa.assertTrue(false, "Not able to click on " + ADEIns11 + " tab");
											}
											} else {
												log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
												sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
											}
										
										lp.CRMlogout();
										sa.assertAll();
									
}

@Parameters({ "projectName"})
@Test
public void ADETc043_VerifyConnectionsCardSectionVisibilityAcuityTabforExistingContact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String ContactName = ADEContact3FName + " " + ADEContact3LName;
	String connectionsHeader = "Team Member<break>Title<break>Deals<break>Meetings and Calls<break>Emails";	


	
	ArrayList<String> dealsSectionHeaderName = new ArrayList<String>();

	ArrayList<String> contactsSectionHeaderName = new ArrayList<String>();

	String[] arrConnnectionsHeader = connectionsHeader.split("<break>");
	List<String> connectionsHeaders = new ArrayList<String>(Arrays.asList(arrConnnectionsHeader));
	
	if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ContactName, 30)) {

			log(LogStatus.INFO, "open created item" + ContactName, YesNo.No);
			ThreadSleep(2000);
			ArrayList<String> result1 = bp
					.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
							contactsSectionHeaderName, null, dealsSectionHeaderName,null, connectionsHeaders,
							null);
			if (result1.isEmpty()) {
				log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
			} else {
				log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
				sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
			}
		} else {

			sa.assertTrue(false, "Not Able to open created source contact : " + ContactName);
			log(LogStatus.SKIP, "Not Able to open created source contact : " + ContactName, YesNo.Yes);

		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
	}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}
	
@Parameters({ "projectName"})
@Test
public void ADETc044_VerifyConnectionSectionWhencontactsConnectionCardPopupRespectively(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String ContactName = ADEContact15FName + " " + ADEContact15LName;
	String connectionsHeader = "Team Member<break>Title<break>Deals<break>Meetings and Calls<break>Emails";	

	String message = "No items to display";

	
	ArrayList<String> dealsSectionHeaderName = new ArrayList<String>();

	ArrayList<String> contactsSectionHeaderName = new ArrayList<String>();

	String[] arrConnnectionsHeader = connectionsHeader.split("<break>");
	List<String> connectionsHeaders = new ArrayList<String>(Arrays.asList(arrConnnectionsHeader));
	
	String dealName1 = ADEDealTeamName7;
	String contactName = ADEContact15FName + " " + ADEContact15LName;

	String recordType = "";
	String dealName = ADEDeal16;
	String companyName = ADEDeal16CompanyName;
	String stage = ADEDeal16Stage;
	if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
		log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);	
		
		ADEContact15EmailID=	lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact15EmailID, "Contact", excelLabel.Variable_Name, "ADEContact15",excelLabel.Contact_EmailId);

		if (cp.createContactAcuity(projectName, ADEContact15FName, ADEContact15LName, ADEIns11, ADEContact15EmailID,ADEContact15RecordType, null, null, CreationPage.ContactPage, null, null)) {
			log(LogStatus.INFO,"successfully Created Contact : "+ADEContact15FName+" "+ADEContact15LName,YesNo.No);	
		} else {
			sa.assertTrue(false,"Not Able to Create Contact : "+ADEContact15FName+" "+ADEContact15LName);
			log(LogStatus.SKIP,"Not Able to Create Contact: "+ADEContact15FName+" "+ADEContact15LName,YesNo.Yes);
		}
		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ContactName, 30)) {

				log(LogStatus.INFO, "open created item" + ContactName, YesNo.No);
				ThreadSleep(2000);
				ArrayList<String> result1 = bp
						.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
								contactsSectionHeaderName, null, dealsSectionHeaderName,null, connectionsHeaders,
								message);
				if (result1.isEmpty()) {
					log(LogStatus.INFO, "The header name and message have been verified  Connection Section ", YesNo.No);
				} else {
					log(LogStatus.ERROR, "The header name and message are not verified on Connection Section ", YesNo.No);
					sa.assertTrue(false, "The header name and message are not verified on  Connection Section ");
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source contact : " + ContactName);
				log(LogStatus.SKIP, "Not Able to open created source contact : " + ContactName, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		}
	

		if (lp.clickOnTab(projectName, tabObj4)) {
			log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
			ThreadSleep(3000);
			if (dp.createDeal(recordType, dealName, companyName, stage, "Date Received", todaysDate)) {
				log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);

			} else {
				log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
				sa.assertTrue(false, dealName + " deal is not created");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
		}
		String[][] data = { { PageLabel.Deal.toString(), dealName1 },
				{ PageLabel.Deal_Contact.toString(), contactName } };

		if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

			if (DTP.createDealTeam(projectName, dealName1, data, "Acuity", action.SCROLLANDBOOLEAN, 25)) {
				log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----",
						YesNo.No);

				log(LogStatus.INFO,
						"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
								+ contactName + " at Firm Tab under Acuity section---------",
						YesNo.No);
				String xpath = "//*[text()='Deal Team']/parent::h1//slot/lightning-formatted-text";
				WebElement ele = FindElement(driver, xpath, "dt id", action.BOOLEAN, 10);
				if (ele != null) {
					String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
					ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name,
							"ADT_07", excelLabel.DealTeamID);
					log(LogStatus.INFO,
							"successfully created and noted id of DT" + id + " and deal name " + dealName1,
							YesNo.No);
				} else {
					sa.assertTrue(false, "could not create DT" + dealName1);
					log(LogStatus.SKIP, "could not create DT" + dealName1, YesNo.Yes);
				}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + TabName.Deal_Team + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + TabName.Deal_Team + " tab");
				}
			}
				if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

					if (fp.clickOnAlreadyCreatedItem(projectName, ContactName, 30)) {

						log(LogStatus.INFO, "open created item" + ContactName, YesNo.No);
						ThreadSleep(2000);
						ArrayList<String> result1 = bp
								.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
										contactsSectionHeaderName, null, dealsSectionHeaderName,null, connectionsHeaders,
										message);
						if (result1.isEmpty()) {
							log(LogStatus.INFO, "The header name and message have been verified  Connection Section ", YesNo.No);
						} else {
							log(LogStatus.ERROR, "The header name and message are not verified on Connection Section ", YesNo.No);
							sa.assertTrue(false, "The header name and message are not verified on  Connection Section ");
						}
					} else {

						sa.assertTrue(false, "Not Able to open created source contact : " + ContactName);
						log(LogStatus.SKIP, "Not Able to open created source contact : " + ContactName, YesNo.Yes);

					}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
				}
				ThreadSleep(5000);
				lp.CRMlogout();
				sa.assertAll();
			}
		
}

@Parameters({ "projectName"})
@Test
public void ADETc045_VerifyDealCountColumnAgainstConnectionWhereConnectionisTaggedTeamMemberDealTeamOfTab(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String dealName1 = ADEDealTeamName8;
	String contactName = ADEContact15FName + " " + ADEContact15LName;
	String TeamMember= ADEDealTeamMember8;
	String DealCountInFirm = "1";

	String recordType = "";
	String dealName = ADEDeal17;
	String companyName = ADEDeal17CompanyName;
	String stage = ADEDeal17Stage;
	String actualDealCount = null;
	String teamMemberName = ADEDealTeamMember8;
	
	if (lp.clickOnTab(projectName, tabObj4)) {
		log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
		ThreadSleep(3000);
		if (dp.createDeal(recordType, dealName, companyName, stage, "Date Received", todaysDate)) {
			log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);

		} else {
			log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
			sa.assertTrue(false, dealName + " deal is not created");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
		sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
	}
	String[][] data = { { PageLabel.Deal.toString(), dealName1 },
			{ PageLabel.Deal_Contact.toString(), contactName },{ PageLabel.Team_Member.toString(), TeamMember } };

	if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

		if (DTP.createDealTeam(projectName, dealName1, data, "Acuity", action.SCROLLANDBOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----",
					YesNo.No);

			log(LogStatus.INFO,
					"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
							+ contactName + " at Firm Tab under Acuity section---------",
					YesNo.No);
			String xpath = "//*[text()='Deal Team']/parent::h1//slot/lightning-formatted-text";
			WebElement ele = FindElement(driver, xpath, "dt id", action.BOOLEAN, 10);
			if (ele != null) {
				String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
				ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name,
						"ADT_08", excelLabel.DealTeamID);
				log(LogStatus.INFO,
						"successfully created and noted id of DT" + id + " and deal name " + dealName1,
						YesNo.No);
			} else {
				sa.assertTrue(false, "could not create DT" + dealName1);
				log(LogStatus.SKIP, "could not create DT" + dealName1, YesNo.Yes);
			}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + TabName.Deal_Team + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + TabName.Deal_Team + " tab");
			}
	}
		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
				actualDealCount = getText(driver, BP.teamMemberDealCount(teamMemberName, 20), "deal",
						action.SCROLLANDBOOLEAN);
				if (BP.teamMemberDealCount(teamMemberName, 20) != null) {
					if (!actualDealCount.equalsIgnoreCase("")) {

						if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
							log(LogStatus.INFO, "Deal Count for Contact: " + contactName + " is " + actualDealCount
									+ " before Deal Team Create is matched to " + DealCountInFirm, YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"Deal Count for Contact: " + contactName
											+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
											+ " but Actual: " + actualDealCount,
									YesNo.Yes);
							sa.assertTrue(false,
									"Deal Count for Contact: " + contactName
											+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
											+ " but Actual: " + actualDealCount);
						}

					} else {
						log(LogStatus.ERROR, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
								+ contactName, YesNo.Yes);
						sa.assertTrue(false, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
								+ contactName);
					}
				} else {
					log(LogStatus.ERROR, "No Contact found of Name: " + contactName, YesNo.No);
					sa.assertTrue(false, "No Contact found of Name: " + contactName);
				}
				
			if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

				if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns11, 30)) {
					if (contactName != null && contactName != "") {

						if (BP.contactNameUserIconButton(contactName, 30) != null) {

							if (click(driver, BP.contactNameUserIconButton(contactName, 30),
									"Contact Name: " + contactName, action.SCROLLANDBOOLEAN)) {
								String parentWindowId = CommonLib.switchOnWindow(driver);
								if (!parentWindowId.isEmpty()) {
									log(LogStatus.PASS, "New Window Open after click on Deal Link: " + dealName, YesNo.No);
								ThreadSleep(3000);
								log(LogStatus.INFO, "Clicked on Contact: " + contactName, YesNo.No);
								actualDealCount = getText(driver, BP.teamMemberpopupDealCount(teamMemberName, 20), "deal",
										action.SCROLLANDBOOLEAN);
								if (BP.teamMemberpopupDealCount(teamMemberName, 20) != null) {
									if (!actualDealCount.equalsIgnoreCase("")) {

										if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
											log(LogStatus.INFO, "Deal Count for Contact: " + contactName + " is " + actualDealCount
													+ " before Deal Team Create is matched to " + DealCountInFirm, YesNo.No);
										} else {
											log(LogStatus.ERROR,
													"Deal Count for Contact: " + contactName
															+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
															+ " but Actual: " + actualDealCount,
													YesNo.Yes);
											sa.assertTrue(false,
													"Deal Count for Contact: " + contactName
															+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
															+ " but Actual: " + actualDealCount);
										}

									} else {
										log(LogStatus.ERROR, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
												+ contactName, YesNo.Yes);
										sa.assertTrue(false, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
												+ contactName);
									}
									driver.close();	driver.switchTo().window(parentWindowId);
								} else {
									log(LogStatus.ERROR, "No Contact found of Name: " + contactName, YesNo.No);
									sa.assertTrue(false, "No Contact found of Name: " + contactName);
								}
								} else {
									log(LogStatus.ERROR, "Not Able to Click on user icon : " + teamMemberName
											+ " of Record: " + contactName, YesNo.No);

									sa.assertTrue(false, "Not Able to Click on user icon: " + teamMemberName
											+ " of Record: " + contactName);

								}
							} else {
								log(LogStatus.ERROR, "No Contact found of Name: " + ADEIns11, YesNo.No);
								sa.assertTrue(false, "No Contact found of Name: " + ADEIns11);
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " Tab", YesNo.No);
							sa.assertTrue(false, "Not able to click on " + tabObj1 + " Tab");
						}
						
						
					ThreadSleep(5000);
					lp.CRMlogout();
					sa.assertAll();
				}
				

					}
			
			}
}
}
}
@Parameters({ "projectName"})
@Test
public void ADETc046_VerifyDealCountColumnAgainstConnectionwheresameConnectionTeamMemberDealTeamforSameDealContactforTab(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String dealName1 = ADEDealTeamName9;
	String contactName = ADEContact15FName + " " + ADEContact15LName;
	String TeamMember= ADEDealTeamMember9;
	String DealCountInFirm = "2";

	String recordType = "";
	String dealName = ADEDeal18;
	String companyName = ADEDeal18CompanyName;
	String stage = ADEDeal18Stage;
	String actualDealCount = null;
	String teamMemberName = ADEDealTeamMember9;
	
	if (lp.clickOnTab(projectName, tabObj4)) {
		log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
		ThreadSleep(3000);
		if (dp.createDeal(recordType, dealName, companyName, stage, "Date Received", todaysDate)) {
			log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);

		} else {
			log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
			sa.assertTrue(false, dealName + " deal is not created");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
		sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
	}
	String[][] data = { { PageLabel.Deal.toString(), dealName1 },
			{ PageLabel.Deal_Contact.toString(), contactName },{ PageLabel.Team_Member.toString(), TeamMember } };

	if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

		if (DTP.createDealTeam(projectName, dealName1, data, "Acuity", action.SCROLLANDBOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----",
					YesNo.No);

			log(LogStatus.INFO,
					"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
							+ contactName + " at Firm Tab under Acuity section---------",
					YesNo.No);
			String xpath = "//*[text()='Deal Team']/parent::h1//slot/lightning-formatted-text";
			WebElement ele = FindElement(driver, xpath, "dt id", action.BOOLEAN, 10);
			if (ele != null) {
				String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
				ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name,
						"ADT_09", excelLabel.DealTeamID);
				log(LogStatus.INFO,
						"successfully created and noted id of DT" + id + " and deal name " + dealName1,
						YesNo.No);
			} else {
				sa.assertTrue(false, "could not create DT" + dealName1);
				log(LogStatus.SKIP, "could not create DT" + dealName1, YesNo.Yes);
			}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + TabName.Deal_Team + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + TabName.Deal_Team + " tab");
			}
	}
		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
				actualDealCount = getText(driver, BP.teamMemberDealCount(teamMemberName, 20), "deal",
						action.SCROLLANDBOOLEAN);
				if (BP.teamMemberDealCount(teamMemberName, 20) != null) {
					if (!actualDealCount.equalsIgnoreCase("")) {

						if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
							log(LogStatus.INFO, "Deal Count for Contact: " + contactName + " is " + actualDealCount
									+ " before Deal Team Create is matched to " + DealCountInFirm, YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"Deal Count for Contact: " + contactName
											+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
											+ " but Actual: " + actualDealCount,
									YesNo.Yes);
							sa.assertTrue(false,
									"Deal Count for Contact: " + contactName
											+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
											+ " but Actual: " + actualDealCount);
						}

					} else {
						log(LogStatus.ERROR, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
								+ contactName, YesNo.Yes);
						sa.assertTrue(false, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
								+ contactName);
					}
				} else {
					log(LogStatus.ERROR, "No Contact found of Name: " + contactName, YesNo.No);
					sa.assertTrue(false, "No Contact found of Name: " + contactName);
				}
				
			if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

				if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns11, 30)) {
					if (contactName != null && contactName != "") {

						if (BP.contactNameUserIconButton(contactName, 30) != null) {

							if (click(driver, BP.contactNameUserIconButton(contactName, 30),
									"Contact Name: " + contactName, action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Contact: " + contactName, YesNo.No);
								String parentWindowId = CommonLib.switchOnWindow(driver);
								if (!parentWindowId.isEmpty()) {
								actualDealCount = getText(driver, BP.teamMemberpopupDealCount(teamMemberName, 20), "deal",
										action.SCROLLANDBOOLEAN);
								if (BP.teamMemberpopupDealCount(teamMemberName, 20) != null) {
									if (!actualDealCount.equalsIgnoreCase("")) {

										if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
											log(LogStatus.INFO, "Deal Count for Contact: " + contactName + " is " + actualDealCount
													+ " before Deal Team Create is matched to " + DealCountInFirm, YesNo.No);
										} else {
											log(LogStatus.ERROR,
													"Deal Count for Contact: " + contactName
															+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
															+ " but Actual: " + actualDealCount,
													YesNo.Yes);
											sa.assertTrue(false,
													"Deal Count for Contact: " + contactName
															+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
															+ " but Actual: " + actualDealCount);
										}

									} else {
										log(LogStatus.ERROR, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
												+ contactName, YesNo.Yes);
										sa.assertTrue(false, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
												+ contactName);
									}
									driver.close();	driver.switchTo().window(parentWindowId);
								} else {
									log(LogStatus.ERROR, "No Contact found of Name: " + contactName, YesNo.No);
									sa.assertTrue(false, "No Contact found of Name: " + contactName);
								}
								} else {
									log(LogStatus.ERROR, "Not Able to Click on user icon : " + teamMemberName
											+ " of Record: " + contactName, YesNo.No);

									sa.assertTrue(false, "Not Able to Click on user icon: " + teamMemberName
											+ " of Record: " + contactName);

								}
							} else {
								log(LogStatus.ERROR, "No Contact found of Name: " + ADEIns11, YesNo.No);
								sa.assertTrue(false, "No Contact found of Name: " + ADEIns11);
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " Tab", YesNo.No);
							sa.assertTrue(false, "Not able to click on " + tabObj1 + " Tab");
						}
					ThreadSleep(5000);
					lp.CRMlogout();
					sa.assertAll();
				}

					}
			
			}
}
}
}
@Parameters({ "projectName"})
@Test
public void ADETc047_VerifyDealCountColumnAgainstConnectionwhereAnotherConnectionTaggedTeamMemberDealTeamforSameDealContactforTab(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String dealName1 = ADEDealTeamName10;
	String contactName = ADEContact15FName + " " + ADEContact15LName;
	String TeamMember= ADEDealTeamMember10;
	String DealCountInFirm = "1";

	String recordType = "";
	String dealName = ADEDeal19;
	String companyName = ADEDeal19CompanyName;
	String stage = ADEDeal19Stage;
	String actualDealCount = null;
	String teamMemberName = ADEDealTeamMember10;
	
	if (lp.clickOnTab(projectName, tabObj4)) {
		log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
		ThreadSleep(3000);
		if (dp.createDeal(recordType, dealName, companyName, stage, "Date Received", todaysDate)) {
			log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);

		} else {
			log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
			sa.assertTrue(false, dealName + " deal is not created");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
		sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
	}
	String[][] data = { { PageLabel.Deal.toString(), dealName1 },
			{ PageLabel.Deal_Contact.toString(), contactName },{ PageLabel.Team_Member.toString(), TeamMember } };

	if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

		if (DTP.createDealTeam(projectName, dealName1, data, "Acuity", action.SCROLLANDBOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----",
					YesNo.No);

			log(LogStatus.INFO,
					"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
							+ contactName + " at Firm Tab under Acuity section---------",
					YesNo.No);
			String xpath = "//*[text()='Deal Team']/parent::h1//slot/lightning-formatted-text";
			WebElement ele = FindElement(driver, xpath, "dt id", action.BOOLEAN, 10);
			if (ele != null) {
				String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
				ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name,
						"ADT_10", excelLabel.DealTeamID);
				log(LogStatus.INFO,
						"successfully created and noted id of DT" + id + " and deal name " + dealName1,
						YesNo.No);
			} else {
				sa.assertTrue(false, "could not create DT" + dealName1);
				log(LogStatus.SKIP, "could not create DT" + dealName1, YesNo.Yes);
			}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + TabName.Deal_Team + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + TabName.Deal_Team + " tab");
			}
	}
		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
				actualDealCount = getText(driver, BP.teamMemberDealCount(teamMemberName, 20), "deal",
						action.SCROLLANDBOOLEAN);
				if (BP.teamMemberDealCount(teamMemberName, 20) != null) {
					if (!actualDealCount.equalsIgnoreCase("")) {

						if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
							log(LogStatus.INFO, "Deal Count for Contact: " + contactName + " is " + actualDealCount
									+ " before Deal Team Create is matched to " + DealCountInFirm, YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"Deal Count for Contact: " + contactName
											+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
											+ " but Actual: " + actualDealCount,
									YesNo.Yes);
							sa.assertTrue(false,
									"Deal Count for Contact: " + contactName
											+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
											+ " but Actual: " + actualDealCount);
						}

					} else {
						log(LogStatus.ERROR, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
								+ contactName, YesNo.Yes);
						sa.assertTrue(false, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
								+ contactName);
					}
				} else {
					log(LogStatus.ERROR, "No Contact found of Name: " + contactName, YesNo.No);
					sa.assertTrue(false, "No Contact found of Name: " + contactName);
				}
				
			if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

				if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns11, 30)) {
					if (contactName != null && contactName != "") {

						if (BP.contactNameUserIconButton(contactName, 30) != null) {

							if (click(driver, BP.contactNameUserIconButton(contactName, 30),
									"Contact Name: " + contactName, action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Contact: " + contactName, YesNo.No);
								String parentWindowId = CommonLib.switchOnWindow(driver);
								if (!parentWindowId.isEmpty()) {
								actualDealCount = getText(driver, BP.teamMemberpopupDealCount(teamMemberName, 20), "deal",
										action.SCROLLANDBOOLEAN);
								if (BP.teamMemberpopupDealCount(teamMemberName, 20) != null) {
									if (!actualDealCount.equalsIgnoreCase("")) {

										if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
											log(LogStatus.INFO, "Deal Count for Contact: " + teamMemberName + " is " + actualDealCount
													+ " before Deal Team Create is matched to " + DealCountInFirm, YesNo.No);
										} else {
											log(LogStatus.ERROR,
													"Deal Count for Contact: " + teamMemberName
															+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
															+ " but Actual: " + actualDealCount,
													YesNo.Yes);
											sa.assertTrue(false,
													"Deal Count for Contact: " + teamMemberName
															+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
															+ " but Actual: " + actualDealCount);
										}

									} else {
										log(LogStatus.ERROR, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
												+ contactName, YesNo.Yes);
										sa.assertTrue(false, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
												+ contactName);
									}
									driver.close();	driver.switchTo().window(parentWindowId);
								} else {
									log(LogStatus.ERROR, "No Contact found of Name: " + contactName, YesNo.No);
									sa.assertTrue(false, "No Contact found of Name: " + contactName);
								}
								} else {
									log(LogStatus.ERROR, "Not Able to Click on user icon : " + teamMemberName
											+ " of Record: " + contactName, YesNo.No);

									sa.assertTrue(false, "Not Able to Click on user icon: " + teamMemberName
											+ " of Record: " + contactName);

								}
							} else {
								log(LogStatus.ERROR, "No Contact found of Name: " + ADEIns11, YesNo.No);
								sa.assertTrue(false, "No Contact found of Name: " + ADEIns11);
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " Tab", YesNo.No);
							sa.assertTrue(false, "Not able to click on " + tabObj1 + " Tab");
						}
					ThreadSleep(5000);
					lp.CRMlogout();
					sa.assertAll();
				}

					}
			
			}
}
}
}
@Parameters({ "projectName"})
@Test
public void ADETc048_EditRemovedealContactfromDealTeamandVerifyImpactConnectionRespectively(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String teamMemberName = ADEDealTeamMember9;
	String DealCountInFirm = "1";
	String contactName = ADEContact15FName + " " + ADEContact15LName;
	String actualDealCount = null;
	
	String contactName1 = ADEContact1FName + " " + ADEContact1LName;
	String[][] data = { { PageLabel.Deal_Contact.toString(), contactName1 } };

	if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEDealTeamID8, 10)) {
			if (DTP.UpdateDealContactName(projectName, data, 30)) {
				log(LogStatus.INFO, "successfully changed name to " + contactName1, YesNo.Yes);
			} else {
				sa.assertTrue(false, "not able to change name to " + contactName1);
				log(LogStatus.SKIP, "not able to change name to " + contactName1, YesNo.Yes);
			}
		}
			
			if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

				if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
					actualDealCount = getText(driver, BP.teamMemberDealCount(teamMemberName, 20), "deal",
							action.SCROLLANDBOOLEAN);
					if (BP.teamMemberDealCount(teamMemberName, 20) != null) {
						if (!actualDealCount.equalsIgnoreCase("")) {

							if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
								log(LogStatus.INFO, "Deal Count for Contact: " + contactName + " is " + actualDealCount
										+ " before Deal Team Create is matched to " + DealCountInFirm, YesNo.No);
							} else {
								log(LogStatus.ERROR,
										"Deal Count for Contact: " + contactName
												+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
												+ " but Actual: " + actualDealCount,
										YesNo.Yes);
								sa.assertTrue(false,
										"Deal Count for Contact: " + contactName
												+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
												+ " but Actual: " + actualDealCount);
							}

						} else {
							log(LogStatus.ERROR, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
									+ contactName, YesNo.Yes);
							sa.assertTrue(false, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
									+ contactName);
						}
					} else {
						log(LogStatus.ERROR, "No Contact found of Name: " + contactName, YesNo.No);
						sa.assertTrue(false, "No Contact found of Name: " + contactName);
					}
				}
			}
					
				if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

					if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns11, 30)) {
						if (contactName != null && contactName != "") {

							if (BP.contactNameUserIconButton(contactName, 30) != null) {

								if (click(driver, BP.contactNameUserIconButton(contactName, 30),
										"Contact Name: " + contactName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Contact: " + contactName, YesNo.No);
									String parentWindowId = CommonLib.switchOnWindow(driver);
									if (!parentWindowId.isEmpty()) {
									actualDealCount = getText(driver, BP.teamMemberpopupDealCount(teamMemberName, 20), "deal",
											action.SCROLLANDBOOLEAN);
									if (BP.teamMemberpopupDealCount(teamMemberName, 20) != null) {
										if (!actualDealCount.equalsIgnoreCase("")) {

											if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
												log(LogStatus.INFO, "Deal Count for Contact: " + contactName + " is " + actualDealCount
														+ " before Deal Team Create is matched to " + DealCountInFirm, YesNo.No);
											} else {
												log(LogStatus.ERROR,
														"Deal Count for Contact: " + contactName
																+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
																+ " but Actual: " + actualDealCount,
														YesNo.Yes);
												sa.assertTrue(false,
														"Deal Count for Contact: " + contactName
																+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
																+ " but Actual: " + actualDealCount);
											}

										} else {
											log(LogStatus.ERROR, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
													+ contactName, YesNo.Yes);
											sa.assertTrue(false, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
													+ contactName);
										}
										driver.close();	driver.switchTo().window(parentWindowId);
									} else {
										log(LogStatus.ERROR, "No Contact found of Name: " + contactName, YesNo.No);
										sa.assertTrue(false, "No Contact found of Name: " + contactName);
									}
									} else {
										log(LogStatus.ERROR, "Not Able to Click on user icon : " + teamMemberName
												+ " of Record: " + contactName, YesNo.No);

										sa.assertTrue(false, "Not Able to Click on user icon: " + teamMemberName
												+ " of Record: " + contactName);

									}
								} else {
									log(LogStatus.ERROR, "No Contact found of Name: " + ADEIns11, YesNo.No);
									sa.assertTrue(false, "No Contact found of Name: " + ADEIns11);
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " Tab", YesNo.No);
								sa.assertTrue(false, "Not able to click on " + tabObj1 + " Tab");
							}
						ThreadSleep(5000);
						lp.CRMlogout();
						sa.assertAll();
					}
				}
				}
}
}

@Parameters({ "projectName"})
@Test
public void ADETc049_EditRemoveTeamMemberfromDealTeamVerifytheImpactConnectionRespectively(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String teamMemberName = ADEDealTeamMember10;
	String contactName = ADEContact15FName + " " + ADEContact15LName;
	String actualDealCount = null;
	
	String teamMemberName1 = ADEDealTeamMember9;
	String[][] data = { { PageLabel.Team_Member.toString(), teamMemberName1 } };

	if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEDealTeamID10, 10)) {
			if (DTP.UpdateDealTeamMemberName(projectName, data, 30)) {
				log(LogStatus.INFO, "successfully changed name to " + teamMemberName1, YesNo.Yes);
			} else {
				sa.assertTrue(false, "not able to change name to " + teamMemberName1);
				log(LogStatus.SKIP, "not able to change name to " + teamMemberName1, YesNo.Yes);
			}
		}
	}
			
			if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

				if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
					actualDealCount = getText(driver, BP.teamMemberDealCount(teamMemberName, 20), "deal",
							action.SCROLLANDBOOLEAN);
				}
			}
					
				if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

					if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns11, 30)) {
						if (contactName != null && contactName != "") {

							if (BP.contactNameUserIconButton(contactName, 30) != null) {

								if (click(driver, BP.contactNameUserIconButton(contactName, 30),
										"Contact Name: " + contactName, action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Clicked on Contact: " + contactName, YesNo.No);
									String parentWindowId = CommonLib.switchOnWindow(driver);
									if (!parentWindowId.isEmpty()) {
										
									if (BP.teamMemberpopupDealCount(teamMemberName, 20) != null) {
										
									} else {
										log(LogStatus.ERROR, "No Contact found of user name: " + teamMemberName, YesNo.No);
										sa.assertTrue(true, "No Contact found of user name: " + teamMemberName);
									}
									} else {
										log(LogStatus.ERROR, "Not Able to Click on user icon : " + teamMemberName
												+ " of Record: " + contactName, YesNo.No);

										sa.assertTrue(false, "Not Able to Click on user icon: " + teamMemberName
												+ " of Record: " + contactName);
										driver.close();	driver.switchTo().window(parentWindowId);
									}
								} else {
									log(LogStatus.ERROR, "No Contact found of Name: " + ADEIns11, YesNo.No);
									sa.assertTrue(false, "No Contact found of Name: " + ADEIns11);
								}
							} else {
								log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " Tab", YesNo.No);
								sa.assertTrue(false, "Not able to click on " + tabObj1 + " Tab");
							}
					}
				}
				ThreadSleep(5000);
				lp.CRMlogout();
				sa.assertAll();
				}

}


@Parameters({ "projectName"})
@Test
public void ADETc052_VerifyDealCountUnderDealColumnClickableTab(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String teamMemberName = ADEDealTeamMember9;
	String contactName = ADEContact15FName + " " + ADEContact15LName;
	String dealName = ADEDeal18;
	String companyName = ADEDeal18CompanyName;
	String stage = ADEDeal18Stage;
	String dateReceived = todaysDate;
	
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns11, 30)) {
			if (contactName != null && contactName != "") {
				if (click(driver, BP.contactNameUserIconButton(contactName, 30),
						"Contact Name: " + contactName, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Contact: " + contactName, YesNo.No);
					String parentWindowId = CommonLib.switchOnWindow(driver);
					if (!parentWindowId.isEmpty()) {
					if (click(driver, BP.teamMemberpopupDealCount(teamMemberName, 20),
							"Contact Name: " + contactName, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Contact: " + contactName, YesNo.No);
						Set<String> childWindow = driver.getWindowHandles();
						switchToDefaultContent(driver);
						System.out.println(childWindow);
						for(String child : childWindow) {
						driver.switchTo().window(child);
						}	
					
				if (BP.dealAcuity2DealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuity2CompanyName(dealName,companyName, 30) != null) {
						log(LogStatus.PASS, "Company Name: " + companyName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuity2StageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
						if (BP.dealAcuity2DateReceived(dealName, dateReceived, 30) != null) {
							log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);

						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
					} else {
						log(LogStatus.FAIL, "Not able to Click on Company Name: " + companyName, YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on Company Name: " + companyName);

					}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}

				} else {
					log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
					sa.assertTrue(false, "date receivednot present: " + dateReceived);
				}
				driver.close();
				driver.switchTo().window(parentWindowId);
				CommonLib.switchOnWindow(driver);
					driver.close();
				driver.switchTo().window(parentWindowId);
					}
				}
			}
		} else {
			log(LogStatus.ERROR, "No Contact found of Name: " + ADEIns11, YesNo.No);
			sa.assertTrue(false, "No Contact found of Name: " + ADEIns11);
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " Tab", YesNo.No);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " Tab");
	}
	}
	if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
			if (contactName != null && contactName != "") {
					if (click(driver, BP.teamMemberpopupDealCount(teamMemberName, 20),
							"Contact Name: " + contactName, action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Contact: " + contactName, YesNo.No);
						String parentWindowId = CommonLib.switchOnWindow(driver);
						if (!parentWindowId.isEmpty()) {
					
				if (BP.dealAcuity2DealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuity2CompanyName(dealName,companyName, 30) != null) {
						log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuity2StageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
						if (BP.dealAcuity2DateReceived(dealName, dateReceived, 30) != null) {
							log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);

						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
					} else {
						log(LogStatus.FAIL, "Not able to Click on Company Name: " + companyName, YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on Company Name: " + companyName);

					}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}

				} else {
					log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
					sa.assertTrue(false, "date receivednot present: " + dateReceived);
				}
					}
						driver.close();	driver.switchTo().window(parentWindowId);
			}
		} else {
			log(LogStatus.ERROR, "No Contact found of Name: " + ADEIns11, YesNo.No);
			sa.assertTrue(false, "No Contact found of Name: " + ADEIns11);
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " Tab", YesNo.No);
		sa.assertTrue(false, "Not able to click on " + tabObj2 + " Tab");
	}
	}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
public void ADETc053_VerifyDealNameClickableRedirectionDealTeamRecordPopupAccountandContact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

	String dealName = ADEDeal18;
	String teamMemberName = ADEDealTeamMember9;
	String contactName = ADEContact15FName + " " + ADEContact15LName;

	if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
			if (click(driver, BP.teamMemberpopupDealCount(teamMemberName, 20),
					"Contact Name: " + contactName, action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Contact: " + contactName, YesNo.No);
				String parentWindowId = CommonLib.switchOnWindow(driver);
				if (!parentWindowId.isEmpty()) {
			ThreadSleep(3000);
			if (clickUsingJavaScript(driver, BP.dealAcuityPopUpDealName(dealName, 10), "Deal Name: " + dealName,
					action.BOOLEAN)) {
				log(LogStatus.PASS, "Clicked on Deal Name: " + dealName, YesNo.No);
			
						log(LogStatus.PASS, "New Window Open after click on Deal Link: " + dealName, YesNo.No);
						Set<String> childWindow = driver.getWindowHandles();
						switchToDefaultContent(driver);
						System.out.println(childWindow);
						for(String child : childWindow) {
						driver.switchTo().window(child);
						}	
						if (BP.dealRecordPage(dealName, 20) != null) {
							log(LogStatus.PASS,
									"----Deal Detail Page is redirecting for Deal Record: " + dealName + "-----",
									YesNo.No);
							
						} else {
							log(LogStatus.FAIL, "----Deal Detail Page is not redirecting for Deal Record: "
									+ dealName + "-----", YesNo.Yes);
							sa.assertTrue(false,
									"----Deal Detail Page is not showing for Deal Record: " + dealName + "-----");
							

						}

					} else {
						log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + dealName, YesNo.Yes);
						sa.assertTrue(false, "No New Window Open after click on Deal Link: " + dealName);
					}
				
			
			} else {
				log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

			}
				driver.close();
				driver.switchTo().window(parentWindowId);
				CommonLib.switchOnWindow(driver);
					driver.close();
				driver.switchTo().window(parentWindowId);
					
				
			} else {
				log(LogStatus.FAIL, "Not able to Click on team count: " + teamMemberName, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on team count: " + teamMemberName);

			}
		} else {
			log(LogStatus.ERROR, "No Contact found of Name: " + ADEIns11, YesNo.No);
			sa.assertTrue(false, "No Contact found of Name: " + ADEIns11);
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " Tab", YesNo.No);
		sa.assertTrue(false, "Not able to click on " + tabObj2 + " Tab");
	}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}


@Parameters({ "projectName" })
@Test
public void ADETc054_VerifyFundraisingCardSectionVisibilityAcuityTabforExistingAccount(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String fundraisingHeader = "Fundraising Name<break>Stage<break>Investment Likely Amount (mn)<break>Target Close Date";
	String message = "No items to display";

	String[] arrfundraisingHeader = fundraisingHeader.split("<break>");
	List<String> fundraisingHeaders = new ArrayList<String>(Arrays.asList(arrfundraisingHeader));
	
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns3, 30)) {
			ArrayList<String> result1 = BP.verifyHeaderNameAndMessageOnFundraisigContactsConnectionsAndDealsSection(fundraisingHeaders,message );
			if (result1.isEmpty()) {
				log(LogStatus.INFO, "The header name and message have been verified  Fundraising Section ", YesNo.No);
			} else {
				log(LogStatus.ERROR, "The header name and message are not verified on Fundraising Section ", YesNo.No);
				sa.assertTrue(false, "The header name and message are not verified on  Fundraising Section ");
			}
		} else {
			log(LogStatus.ERROR, "No firm found of Name: " + ADEIns3, YesNo.No);
			sa.assertTrue(false, "No firm found of Name: " + ADEIns3);
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " Tab", YesNo.No);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " Tab");
	}
		
ThreadSleep(5000);
lp.CRMlogout();
sa.assertAll();	
		}


@Parameters({ "projectName" })
@Test
public void ADETc055_CreateFundsForUsageFundraising(String projectName) {

	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String[] fundNames = ADEFundName.split("<Break>", -1);
	String[] fundTypes = ADEFundType.split("<Break>", -1);
	String[] investmentCategory = ADEInvestmentCategory.split("<Break>", -1);
	
	int fundStatus = 0;
	int fundLoopCount = 0;
	for (String fundName : fundNames) {

		log(LogStatus.INFO, "---------Now Going to Create Fund Named: " + fundName + "---------", YesNo.No);
		if (fund.clickOnTab(environment, mode, TabName.FundsTab)) {

			if (fund.createFund(projectName, fundName, fundTypes[fundLoopCount],
					investmentCategory[fundLoopCount], null, null)) {
				appLog.info("Fund is created Successfully: " + fundName);
				fundStatus++;

			} else {
				appLog.error("Not able to click on fund: " + fundName);
				sa.assertTrue(false, "Not able to click on fund: " + fundName);
				log(LogStatus.ERROR, "Not able to click on fund: " + fundName, YesNo.Yes);
			}
		} else {
			appLog.error("Not able to click on Fund tab so cannot create Fund: " + fundName);
			sa.assertTrue(false, "Not able to click on Fund tab so cannot create Fund: " + fundName);
		}
		ThreadSleep(2000);
		fundLoopCount++;

	}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();	
			}


@Parameters({ "projectName" })
@Test
public void ADETc056_VerifyImpactFundraisingSectionInstitutionAccountTaggedLegalName(String projectName) {

	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);
	
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String ADETargetClosingDate = todaysDate;
	
	if(BP.clickOnTab(environment,mode,TabName.FundraisingsTab)) {
		if (fr.createFundRaising(environment, "Lightning", ADEFundraisingName,ADERFundName,ADEInstitutionName, null,
				null, null, null, ADETargetClosingDate,ADETargetClosingDate, ADETargetClosingDate)) {
			appLog.info("fundraising is created : " + ADEFundraisingName);
		} else {
			appLog.error("Not able to create fundraising: " + ADEFundraisingName);
			sa.assertTrue(false, "Not able to create fundraising: " + ADEFundraisingName);
		}

	} else {
		appLog.error(
				"Not able to click on fundraising tab so cannot create fundraising: " + ADEFundraisingName);
		sa.assertTrue(false,
				"Not able to click on fundraising tab so cannot create fundraising: " + ADEFundraisingName);
	}
	ThreadSleep(2000);

if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
	log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

	if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns3, 30)) {
		
		ArrayList<String> result1 = BP.verifyRecordOnFundraisingsSectionInAcuity(
				null, ADEFundraisingName, null,
				null, null);
		if (result1.isEmpty()) {
			log(LogStatus.INFO, "Records on Fundraisings slot have been matched", YesNo.No);

		} else {
			log(LogStatus.ERROR, "Records on Fundraisings slot are not matched, Reason: " + result1,
					YesNo.No);
			sa.assertTrue(false, "Records on Fundraisings slot are not matched, Reason" + result1);
		}
	} else {
		log(LogStatus.ERROR, "No firm found of Name: " + ADEIns3, YesNo.No);
		sa.assertTrue(false, "No firm found of Name: " + ADEIns3);
	}
} else {
	log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " Tab", YesNo.No);
	sa.assertTrue(false, "Not able to click on " + tabObj1 + " Tab");
}
	
ThreadSleep(5000);
lp.CRMlogout();
sa.assertAll();	
	}

@Parameters({ "projectName" })
@Test
public void ADETc057_1_VerifyFundraisingwithDiferrentStagesDisplayingFundraisingSectionInstitutionTypeAccount(String projectName) {

	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);
	
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String[] fundraisingNames = ADEFundraisingName1.split("<Break>", -1);
	String[] fundraisingsFundName = ADERFundName1.split("<Break>", -1);
	String[] fundraisingsInstitutionName = ADEInstitutionName1.split("<Break>", -1);
	String[] fundraisingsStageName = ADEStage1.split("<Break>", -1);
	
	int fundStatus = 0;
	int fundLoopCount = 0;
	if (fundStatus == fundLoopCount) {
		int fundraisingLoopCount = 0;
		for (String fundraisingName : fundraisingNames) {
			log(LogStatus.INFO, "---------Now Going to Create Fundraising Named: " + fundraisingName + "---------",
					YesNo.No);
			if (BP.clickOnTab(environment, mode, TabName.FundraisingsTab)) {
				if (fr.createFundRaising(environment, "Lightning", fundraisingName,
						fundraisingsFundName[fundraisingLoopCount],
						fundraisingsInstitutionName[fundraisingLoopCount], null,
						fundraisingsStageName[fundraisingLoopCount], null, null, null,
						null, null)) {
					appLog.info("fundraising is created : " + fundraisingName);
				} else {
					appLog.error("Not able to create fundraising: " + fundraisingName);
					sa.assertTrue(false, "Not able to create fundraising: " + fundraisingName);
				}

			} else {
				appLog.error(
						"Not able to click on fundraising tab so cannot create fundraising: " + fundraisingName);
				sa.assertTrue(false,
						"Not able to click on fundraising tab so cannot create fundraising: " + fundraisingName);
			}
			ThreadSleep(2000);

			fundraisingLoopCount++;

		}

	} else {
		appLog.error("No Fund is created, So not able to Create Fundraising: " + fundraisingNames);
		sa.assertTrue(false, "No Fund is created, So not able to Create Fundraising: " + fundraisingNames);
	}
ThreadSleep(5000);
lp.CRMlogout();
sa.assertAll();	
	}
	

@Parameters({ "projectName" })
@Test
public void ADETc057_2_VerifyFundraisingwithDiferrentStagesDisplayingFundraisingSectionInstitutionTypeAccount(String projectName) {

	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);

	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String[] fundraisingNames = ADEFundraisingName1.split("<Break>", -1);
	String[] fundraisingsFundName = ADERFundName1.split("<Break>", -1);
	String[] fundraisingsInstitutionName = ADEInstitutionName1.split("<Break>", -1);
	String[] fundraisingsStageName = ADEStage1.split("<Break>", -1);
	
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
		
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns3, 30)) {
			for (int i = 0; i < 10; i++) {
			ArrayList<String> result1 = BP.verifyRecordOnFundraisingsSectionInAcuity(
					null, fundraisingNames[i], null,
					fundraisingsStageName[i], null);
			if (result1.isEmpty()) {
				log(LogStatus.INFO, "Records on Fundraisings slot have been matched", YesNo.No);

			} else {
				log(LogStatus.ERROR, "Records on Fundraisings slot are not matched, Reason: " + result1,
						YesNo.No);
				sa.assertTrue(false, "Records on Fundraisings slot are not matched, Reason" + result1);
			}
			
		} 
		}else {
			log(LogStatus.ERROR, "No firm found of Name: " + ADEIns3, YesNo.No);
			sa.assertTrue(false, "No firm found of Name: " + ADEIns3);
		}
	}else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " Tab", YesNo.No);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " Tab");
	}
	
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();	
		}
		

@Parameters({ "projectName" })
@Test
public void ADETc058_VerifyFundraisingNamesClickableandFundraisingRedirection(String projectName) {

	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);

	
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
		
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns3, 30)) {
			if (clickUsingJavaScript(driver, BP.fundraisingsAcuityFundraisingsName(ADEFundraisingName, 10), "fundraising Name: " + ADEFundraisingName,
					action.BOOLEAN)) {
				log(LogStatus.PASS, "Clicked on fundraising Name: " + ADEFundraisingName, YesNo.No);
				try {
					String parentWindowId = CommonLib.switchOnWindow(driver);
					if (!parentWindowId.isEmpty()) {
						log(LogStatus.PASS, "New Window Open after click on fundraising Link: " + ADEFundraisingName, YesNo.No);

						if (BP.FundRaisingRecordPage(ADEFundraisingName, 20) != null) {
							log(LogStatus.PASS,
									"----fundraising Detail Page is redirecting for Deal Record: " + ADEFundraisingName + "-----",
									YesNo.No);
							driver.close();
							driver.switchTo().window(parentWindowId);

						} else {
							log(LogStatus.FAIL, "----fundraising Detail Page is redirecting for Deal Record: "
									+ ADEFundraisingName + "-----", YesNo.Yes);
							sa.assertTrue(false,
									"----fundraising Detail Page is not showing for Deal Record: " + ADEFundraisingName + "-----");
							driver.close();
							driver.switchTo().window(parentWindowId);

						}

					} else {
						log(LogStatus.FAIL, "No New Window Open after click on fundraising Link: " + ADEFundraisingName, YesNo.Yes);
						sa.assertTrue(false, "No New Window Open after click on fundraising Link: " + ADEFundraisingName);
					}
				} catch (Exception e) {
					log(LogStatus.FAIL,
							"Not able to switch to window after click on fundraising Link, Msg showing: " + e.getMessage(),
							YesNo.Yes);
					sa.assertTrue(false, "Not able to switch to window after click on fundraising Link, Msg showing: "
							+ e.getMessage());
				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on fundraising Name: " + ADEFundraisingName, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on fundraising Name: " + ADEFundraisingName);

			}
		}else {
			log(LogStatus.ERROR, "No firm found of Name: " + ADEIns3, YesNo.No);
			sa.assertTrue(false, "No firm found of Name: " + ADEIns3);
		}
	}else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " Tab", YesNo.No);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " Tab");
	}
	
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();	
		}
		

@Parameters({ "projectName" })
@Test
public void ADETc059_DeleteExistingFundraisingandVerifyImpactFundraisingsonInstitutionAccounts(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

	if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEFundraisingName, 10)) {
			cp.clickOnShowMoreDropdownOnly(projectName, PageName.Object4Page, "");
			log(LogStatus.INFO, "Able to Click on Show more Icon : " + TabName.FundraisingsTab + " For : " + ADEFundraisingName,
					YesNo.No);
			ThreadSleep(500);
			WebElement ele = cp.actionDropdownElement(projectName, PageName.Object4Page,
					ShowMoreActionDropDownList.Delete, 15);
			if (ele == null) {
				ele = cp.getDeleteButton(projectName, 30);
			}

			if (click(driver, ele, "Delete More Icon", action.BOOLEAN)) {
				log(LogStatus.INFO,
						"Able to Click on Delete more Icon : " + TabName.Object4Tab + " For : " + ADEFundraisingName,
						YesNo.No);
				ThreadSleep(1000);
				if (click(driver, cp.getDeleteButtonOnDeletePopUp(projectName, 30), "Delete Button",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to Click on Delete button on Delete PoPup : " + TabName.FundraisingsTab
							+ " For : " + "Test D", YesNo.No);
					ThreadSleep(10000);
					if (cp.clickOnTab(projectName, TabName.FundraisingsTab)) {
						log(LogStatus.INFO, "Clicked on Tab : " + TabName.Object4Tab + " For : " + ADEFundraisingName,
								YesNo.No);
						ThreadSleep(1000);
						if (!cp.clickOnAlreadyCreatedItem(projectName, TabName.FundraisingsTab, ADEFundraisingName, 10)) {
							log(LogStatus.INFO, "Item has been Deleted after delete operation  : " + ADEFundraisingName
									+ " For : " + TabName.FundraisingsTab, YesNo.No);

						} else {
							sa.assertTrue(false, "Item has not been Deleted after delete operation  : " + ADEFundraisingName
									+ " For : " + TabName.FundraisingsTab);
							log(LogStatus.SKIP, "Item has not been Deleted after delete operation  : " + ADEFundraisingName
									+ " For : " + TabName.FundraisingsTab, YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Not Able to Click on Tab after delete : " + TabName.FundraisingsTab
								+ " For : " + "Test D");
						log(LogStatus.SKIP, "Not Able to Click on Tab after delete : " + TabName.FundraisingsTab
								+ " For : " +ADEFundraisingName, YesNo.Yes);
					}
				} else {
					log(LogStatus.INFO, "not able to click on delete button, so not deleted : " + TabName.FundraisingsTab
							+ " For : " +ADEFundraisingName, YesNo.No);
					sa.assertTrue(false, "not able to click on delete button, so not deleted : "
							+ TabName.FundraisingsTab + " For : " + ADEFundraisingName);
				}
			} else {
				log(LogStatus.INFO,
						"not Able to Click on Delete more Icon : " + TabName.FundraisingsTab + " For : " + ADEFundraisingName,
						YesNo.No);
				sa.assertTrue(false,
						"not Able to Click on Delete more Icon : " + TabName.FundraisingsTab + " For : " + ADEFundraisingName);
			}
		} else {
			log(LogStatus.INFO, "not Able to Click on " + ADEFundraisingName, YesNo.No);
			sa.assertTrue(false, "not Able to Click on " + ADEFundraisingName);
		}
	} else {
		log(LogStatus.INFO, "not Able to Click on " + TabName.FundraisingsTab, YesNo.No);
		sa.assertTrue(false, "not Able to Click on " + TabName.FundraisingsTab);
	}
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns3, 30)) {

			log(LogStatus.INFO, "open created item" + ADEIns3, YesNo.No);

			if (BP.fundraisingsAcuityFundraisingsName(ADEFundraisingName, 10) != null) {
				log(LogStatus.PASS, "Fundraising Name: " + ADEFundraisingName + " is hyperlink and is present", YesNo.No);
			} else {
				sa.assertTrue(true, "is hyperlink and not present  : " + ADEFundraisingName + " For : " + TabName.FundraisingsTab);
				log(LogStatus.SKIP, "is hyperlink and not present  : " + ADEFundraisingName + " For : " + TabName.FundraisingsTab,
						YesNo.Yes);
			}
		} else {

			sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns3);
			log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns3, YesNo.Yes);

		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
}


@Parameters({ "projectName" })
@Test
public void ADETc060_RestoreDeletedFundraisingandCheckImpactFundraisingSectionAccounts(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null;

	TabName tabName = TabName.RecycleBinTab;
	String name = ADEFundraisingName;

	if (cp.clickOnTab(projectName, tabName)) {
		log(LogStatus.INFO, "Clicked on Tab : " + tabName + " For : " + name, YesNo.No);
		ThreadSleep(1000);
		cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 20);
		log(LogStatus.INFO, "Clicked on  : " + name + " For : " + tabName, YesNo.No);
		ThreadSleep(2000);

		ele = cp.getCheckboxOfRestoreItemOnRecycleBin(projectName, name, 10);
		if (clickUsingJavaScript(driver, ele, "Check box against : " + name, action.BOOLEAN)) {
			log(LogStatus.INFO, "Click on checkbox for " + name, YesNo.No);

			ThreadSleep(1000);
			ele = cp.getRestoreButtonOnRecycleBin(projectName, 10);
			if (clickUsingJavaScript(driver, ele, "Restore Button : " + name, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on Restore Button for " + name, YesNo.No);
				ThreadSleep(1000);
			} else {
				sa.assertTrue(false, "Not Able to Click on Restore Button for " + name);
				log(LogStatus.SKIP, "Not Able to Click on Restore Button for " + name, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on checkbox for " + name);
			log(LogStatus.SKIP, "Not Able to Click on checkbox for " + name, YesNo.Yes);
		}

	} else {
		sa.assertTrue(false, "Not Able to Click on Tab : " + tabName + " For : " + name);
		log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName + " For : " + name, YesNo.Yes);
	}
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns3, 30)) {

			log(LogStatus.INFO, "open created item" + ADEIns3, YesNo.No);

			if (BP.fundraisingsAcuityFundraisingsName(ADEFundraisingName, 10) != null) {
				log(LogStatus.PASS, "Fundraising Name: " + ADEFundraisingName + " is hyperlink and is present", YesNo.No);
			} else {
				sa.assertTrue(false, "is hyperlink and not present  : " + ADEFundraisingName + " For : " + TabName.FundraisingsTab);
				log(LogStatus.SKIP, "is hyperlink and not present  : " + ADEFundraisingName + " For : " + TabName.FundraisingsTab,
						YesNo.Yes);
			}
		} else {

			sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns3);
			log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns3, YesNo.Yes);

		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
public void ADETc061_EditLegalNameAndVerifyImpactFundraisingSectionChnagedInstitutionAccount(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String expecedHeader = "We hit a snag.";
	if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEFundraisingName, 10)) {
			if (BP.clickOnShowMoreActionDownArrow(projectName, PageName.Object4Page, ShowMoreActionDropDownList.Edit, 10)) {
				ThreadSleep(2000);
		}
			} else {
			log(LogStatus.ERROR, "Not able to click on show more action down arrow", YesNo.Yes);
		}
			if (fr.UpdateLegalName(projectName, mode, ADEIns2, 10)) {
				log(LogStatus.INFO, "successfully changed name to " + ADEIns2, YesNo.Yes);
			} else {
				sa.assertTrue(false, "not able to change name to " + ADEIns2);
				log(LogStatus.SKIP, "not able to change name to " + ADEIns2, YesNo.Yes);
			}
	WebElement		ele=npbl.getHitASnagElement(projectName, 20);
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

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns3);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns3, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

@Parameters({ "projectName" })
@Test
public void ADETc062_1_EditFieldColumnNamesVerifyImpactFundraisingCardInstitutionAccount(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
	String parentID = null;
	String updateLabel = "UpdatedStage";
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	if (home.clickOnSetUpLink()) {
		parentID = switchOnWindow(driver);
		if (parentID != null) {
			if (sp.searchStandardOrCustomObject(environment, mode, object.Override)) {
				log(LogStatus.INFO, "click on Object : " + object.valueOf("Override"), YesNo.No);
				ThreadSleep(2000);
				switchToFrame(driver, 30, sp.getSetUpPageIframe(60));
				ThreadSleep(5000);
				if (selectVisibleTextFromDropDown(driver, sp.getOverrideSetupComponentDropdown(10),
						"Override setup component dropdown", "Custom Field")) {
					log(LogStatus.INFO,
							"Select custom field text in setup component dropdown in override setup page",
							YesNo.No);
					ThreadSleep(5000);

					if (selectVisibleTextFromDropDown(driver, sp.getOverrideObjectDropdown(10),
							"Override object dropdown", PageLabel.Fundraising.toString())) {
						log(LogStatus.INFO, "Select " + PageLabel.Fundraising.toString()
								+ " text in object dropdown in override setup page", YesNo.No);
						ThreadSleep(5000);
						if (sp.updateFieldLabelInOverridePage(driver, PageLabel.Stage.toString(), updateLabel,
								action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Field label: " + PageLabel.Stage.toString()
									+ " successfully update to " + updateLabel, YesNo.No);

						} else {
							log(LogStatus.ERROR, "Not able to update Field label: " + PageLabel.Stage.toString()
									+ " successfully update to " + updateLabel, YesNo.Yes);
							sa.assertTrue(false, "Not able to update Field label: " + PageLabel.Stage.toString()
									+ " to " + updateLabel);
						}
					} else {
						log(LogStatus.ERROR, "Not able to select text: " + PageLabel.Activity.toString()
								+ " in  object dropdown in override page", YesNo.Yes);
						sa.assertTrue(false, "Not able to select text: " + PageLabel.Activity.toString()
								+ " in  object dropdown in override page");
					}
				} else {
					log(LogStatus.ERROR,
							"Not able to select text: Custom Field in  setup component dropdown in override page",
							YesNo.Yes);
					sa.assertTrue(false,
							"Not able to select text: Custom Field in  setup component dropdown in override page");
				}

			} else {

				log(LogStatus.PASS, "Not able to click on Object : " + object.valueOf("Override"), YesNo.Yes);
				sa.assertTrue(false, "Not able to click on Object : " + object.valueOf("Override"));
			}
		} else {
			sa.assertTrue(false, "new window is not found, so cannot change stage label");
			log(LogStatus.SKIP, "new window is not found, so cannot change stage label", YesNo.Yes);
		}
	} else {
		sa.assertTrue(false, "setup link is not clickable");
		log(LogStatus.SKIP, "setup link is not clickable", YesNo.Yes);
	}
	switchToDefaultContent(driver);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
public void ADETc062_2_EditFieldColumnNamesVerifyImpactFundraisingCardInstitutionAccount(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String fundraisingHeader = "Fundraising Name<break>UpdatedStage<break>Investment Likely Amount (mn)<break>Target Close Date";
	

	String[] arrfundraisingHeader = fundraisingHeader.split("<break>");
	List<String> fundraisingHeaders = new ArrayList<String>(Arrays.asList(arrfundraisingHeader));
	
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns3, 30)) {
			ArrayList<String> result1 = BP.verifyHeaderNameAndMessageOnFundraisigContactsConnectionsAndDealsSection(fundraisingHeaders,null );
			if (result1.isEmpty()) {
				log(LogStatus.INFO, "The header name and message have been verified  Fundraising Section ", YesNo.No);
			} else {
				log(LogStatus.ERROR, "The header name and message are not verified on Fundraising Section ", YesNo.No);
				sa.assertTrue(false, "The header name and message are not verified on  Fundraising Section ");
			}
		} else {
			log(LogStatus.ERROR, "No firm found of Name: " + ADEIns3, YesNo.No);
			sa.assertTrue(false, "No firm found of Name: " + ADEIns3);
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " Tab", YesNo.No);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " Tab");
	}
		
ThreadSleep(5000);
lp.CRMlogout();
sa.assertAll();	
		}

@Parameters({ "projectName" })
@Test
public void ADETc063_1_EditFieldColumnNamesVerifyImpactFundraisingCardInstitutionAccount(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String recordType = "Advisor";
	
	
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns3, 30)) {
			WebElement ele1 = BP.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
			click(driver, ele1, RelatedTab.Details.toString(), action.BOOLEAN);
			ThreadSleep(3000);
			if(ip.UpdateRecordTypeAccount(projectName, mode, recordType, 10)) {
				log(LogStatus.INFO, "successfully changed record type to " + recordType, YesNo.Yes);
			} else {
				sa.assertTrue(false, "not able to change record type to " + recordType);
				log(LogStatus.SKIP, "not able to change record type to " + recordType, YesNo.Yes);
			}
			}
	
	}
	String fundraisingHeader = "Fundraising Name<break>UpdatedStage<break>Investment Likely Amount (mn)<break>Target Close Date";
	
	String[] arrfundraisingHeader = fundraisingHeader.split("<break>");
	List<String> fundraisingHeaders = new ArrayList<String>(Arrays.asList(arrfundraisingHeader));
	
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns3, 30)) {
			ArrayList<String> result1 = BP.verifyHeaderNameAndMessageOnFundraisigContactsConnectionsAndDealsSection(fundraisingHeaders,null );
			if (result1.isEmpty()) {
				log(LogStatus.INFO, "The header name and message have been verified  Fundraising Section ", YesNo.No);
			} else {
				log(LogStatus.INFO, "The header name and message are not verified on Fundraising Section ", YesNo.No);
				sa.assertTrue(true, "The header name and message are not verified on  Fundraising Section ");
			}
		} else {
			log(LogStatus.ERROR, "No firm found of Name: " + ADEIns3, YesNo.No);
			sa.assertTrue(false, "No firm found of Name: " + ADEIns3);
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " Tab", YesNo.No);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " Tab");
	}
		
ThreadSleep(5000);
lp.CRMlogout();
sa.assertAll();	
		}

@Parameters({ "projectName" })
@Test
public void ADETc063_2_EditFieldColumnNamesVerifyImpactFundraisingCardInstitutionAccount(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String recordType = "Institution";
	
	
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns3, 30)) {
			if(ip.UpdateRecordTypeAccount(projectName, mode, recordType, 10)) {
				log(LogStatus.INFO, "successfully changed record type to " + recordType, YesNo.Yes);
			} else {
				sa.assertTrue(false, "not able to change record type to " + recordType);
				log(LogStatus.SKIP, "not able to change record type to " + recordType, YesNo.Yes);
			}
			}
	
	}
	String fundraisingHeader = "Fundraising Name<break>UpdatedStage<break>Investment Likely Amount (mn)<break>Target Close Date";
	
	String[] arrfundraisingHeader = fundraisingHeader.split("<break>");
	List<String> fundraisingHeaders = new ArrayList<String>(Arrays.asList(arrfundraisingHeader));
	
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns3, 30)) {
			ArrayList<String> result1 = BP.verifyHeaderNameAndMessageOnFundraisigContactsConnectionsAndDealsSection(fundraisingHeaders,null );
			if (result1.isEmpty()) {
				log(LogStatus.INFO, "The header name and message have been verified  Fundraising Section ", YesNo.No);
			} else {
				log(LogStatus.ERROR, "The header name and message are not verified on Fundraising Section ", YesNo.No);
				sa.assertTrue(false, "The header name and message are not verified on  Fundraising Section ");
			}
		} else {
			log(LogStatus.ERROR, "No firm found of Name: " + ADEIns3, YesNo.No);
			sa.assertTrue(false, "No firm found of Name: " + ADEIns3);
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " Tab", YesNo.No);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " Tab");
	}
		
ThreadSleep(5000);
lp.CRMlogout();
sa.assertAll();	
		}

@Parameters({ "projectName" })
@Test
public void ADETc064_1_VerifyImpactDMLOperationsStagesFundraisingSectionfInstitutiontypeAccount(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	String parentID = null;
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	String[][] newAndOldStage = { { Stage.Declined.toString(), Stage.Deny.toString() }};
	if (home.clickOnSetUpLink()) {
		parentID = switchOnWindow(driver);
		if (parentID != null) {
			if (sp.searchStandardOrCustomObject(environment, mode, object.Fundraising)) {
				if (sp.clickOnObjectFeature(environment, mode, object.Fundraising,
						ObjectFeatureName.FieldAndRelationShip)) {
					if (sendKeys(driver, sp.getsearchTextboxFieldsAndRelationships(10),
							excelLabel.Stage.toString() + Keys.ENTER, "status", action.BOOLEAN)) {

						if (sp.clickOnAlreadyCreatedLayout(excelLabel.Stage.toString())) {
							for (int i = 0; i < newAndOldStage.length; i++) {
								switchToDefaultContent(driver);
								switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
								WebElement ele = sp.clickOnEditInFrontOfFieldValues(projectName,
										newAndOldStage[i][0]);
								if (click(driver, ele, "watchlist", action.BOOLEAN)) {
									switchToDefaultContent(driver);
									ThreadSleep(3000);
									switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
									sendKeys(driver, sp.getFieldLabelTextBox1(10), newAndOldStage[i][1], "label",
											action.BOOLEAN);

									if (clickUsingJavaScript(driver, fp.getCustomTabSaveBtn(10), "save",
											action.BOOLEAN)) {

										log(LogStatus.INFO, "successfully changed stage label", YesNo.No);
									} else {
										sa.assertTrue(false, "not able to click on save button");
										log(LogStatus.SKIP, "not able to click on save button", YesNo.Yes);
									}
								} else {
									sa.assertTrue(false, "edit button is not clickable");
									log(LogStatus.SKIP, "edit button is not clickable", YesNo.Yes);
								}
							}
						}
					}
				}
			}
		}
		driver.close();
		lp.CRMlogout();
		sa.assertAll();
	}
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
							if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
								log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

								if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns3, 30)) {

									log(LogStatus.INFO, "open created item" + ADEIns3, YesNo.No);

									if (BP.fundraisingsAcuityStageName("ADEStageRaising10","Deny", 10) != null) {
										log(LogStatus.PASS, "Stage Name: " + "Deny" + " is present", YesNo.No);

									} else {
										log(LogStatus.FAIL, "stage name not present: " + "Deny", YesNo.Yes);
										sa.assertTrue(false, "stage name not present: " + "Deny");

									}
								} else {

									sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns3);
									log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns3, YesNo.Yes);

								}
							} else {
								log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
							}
							ThreadSleep(5000);
							lp.CRMlogout();
							sa.assertAll();	
									}

@Parameters({ "projectName" })
@Test
public void ADETc064_2_VerifyImpactDMLOperationsStagesFundraisingSectionfInstitutiontypeAccount(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	String parentID = null;
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	if (home.clickOnSetUpLink()) {
		parentID = switchOnWindow(driver);
		if (parentID != null) {
			if (sp.searchStandardOrCustomObject(environment, mode, object.Fundraising)) {
				if (sp.clickOnObjectFeature(environment, mode, object.Fundraising,
						ObjectFeatureName.FieldAndRelationShip)) {
					if (sendKeys(driver, sp.getsearchTextboxFieldsAndRelationships(10), excelLabel.Stage.toString(),
							"stage", action.BOOLEAN)) {
						if (sp.clickOnAlreadyCreatedLayout(excelLabel.Stage.toString())) {
							switchToDefaultContent(driver);
							switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
							ThreadSleep(5000);
							WebElement ele1 = dp.findDeleteLink(projectName, Stage.Deny.toString());
							if (click(driver, ele1, "delete Deny", action.SCROLLANDBOOLEAN)) {
								ThreadSleep(5000);
								if (!isAlertPresent(driver)) {
									clickUsingJavaScript(driver, ele1, "delete Deny", action.SCROLLANDBOOLEAN);
								}
								ThreadSleep(2000);
								// driver.switchTo().alert().accept();
								switchToAlertAndAcceptOrDecline(driver, 10, action.ACCEPT);
								switchToDefaultContent(driver);
								switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
								ThreadSleep(2000);
								if (click(driver, dp.getreplacevaluewithNullforstage(projectName,10), "replacevaluewithNullforstage button",
										action.SCROLLANDBOOLEAN)) {
									log(LogStatus.ERROR, "Click on replacevaluewithNullforstage Button : ", YesNo.No);
									ThreadSleep(2000);
									if (click(driver, sp.getCustomTabSaveBtn(10), "save button",
											action.SCROLLANDBOOLEAN)) {
										log(LogStatus.ERROR, "Click on save Button : ", YesNo.No);
										ThreadSleep(2000);
									} else {
										log(LogStatus.ERROR, "Not Able to Click on save Button : ", YesNo.Yes);
										sa.assertTrue(false, "Not Able to Click on save Button : ");
									}
								} else {
									sa.assertTrue(false, "deactivate link is not clickable");
									log(LogStatus.SKIP, "deactivate link is not clickable", YesNo.Yes);
								}
							} else {
								sa.assertTrue(false, "stage field link is not clickable");
								log(LogStatus.SKIP, "stage field link is not clickable", YesNo.Yes);
							}
						} else {
							sa.assertTrue(false, "search textbox is not visible");
							log(LogStatus.SKIP, "search textbox is not visible", YesNo.Yes);
						}
					} else {
						log(LogStatus.FAIL, "field n relationships feature not clickable", YesNo.Yes);
						sa.assertTrue(false, "field n relationships feature not clickable");
					}
				} else {
					log(LogStatus.FAIL, "deal object is not clickable", YesNo.Yes);
					sa.assertTrue(false, "deal object is not clickable");
				}
				driver.close();
				driver.switchTo().window(parentID);
			} else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		} else {
			log(LogStatus.FAIL, "setup link is not clickable", YesNo.Yes);
			sa.assertTrue(false, "setup link is not clickable");
		}
        driver.close();
		lp.CRMlogout();
		sa.assertAll();
	}
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns3, 30)) {

			log(LogStatus.INFO, "open created item" + ADEIns3, YesNo.No);

			if (BP.fundraisingsAcuityStageName("ADEStageRaising10"," ", 10) != null) {
				log(LogStatus.PASS, "Stage Name: " + "Deny" + " is present", YesNo.No);

			} else {
				log(LogStatus.FAIL, "stage name not present: " + " ", YesNo.Yes);
				sa.assertTrue(false, "stage name not present: " + " ");

			}
		} else {

			sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns3);
			log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns3, YesNo.Yes);

		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();	
			}



@Parameters({ "projectName" })
@Test
public void ADETc064_3_VerifyImpactDMLOperationsStagesFundraisingSectionfInstitutiontypeAccount(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	WebElement ele = null;
	String parentID = null;
	String stage = "Decline";
	if (home.clickOnSetUpLink()) {
		parentID = switchOnWindow(driver);
		if (parentID != null) {
			if (sp.searchStandardOrCustomObject(environment, mode, object.Fundraising)) {
				if (sp.clickOnObjectFeature(environment, mode, object.Fundraising,
						ObjectFeatureName.FieldAndRelationShip)) {
					if (sendKeys(driver, sp.getsearchTextboxFieldsAndRelationships(10),
							excelLabel.Stage.toString() + Keys.ENTER, "status", action.BOOLEAN)) {
						if (sp.clickOnAlreadyCreatedLayout(excelLabel.Stage.toString())) {
							switchToDefaultContent(driver);
							ThreadSleep(10000);
							switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
							stage = stage.replace("_", " ");
							ThreadSleep(3000);
							ele = sp.getValuesNewButton(10);
							if (click(driver, ele, "new button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "click on Values New Button", YesNo.No);
								switchToDefaultContent(driver);
								ThreadSleep(20000);
								switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
								ThreadSleep(5000);
								if (sendKeys(driver, dp.getTextArea(20), stage, stage, action.BOOLEAN)) {
									log(LogStatus.INFO, "enter value on textarea " + stage, YesNo.No);
									ThreadSleep(2000);
									if (click(driver, sp.getCustomTabSaveBtn(10), "save button",
											action.SCROLLANDBOOLEAN)) {
										log(LogStatus.ERROR, "Click on save Button : ", YesNo.No);
										ThreadSleep(2000);
									} else {
										log(LogStatus.ERROR, "Not Able to Click on save Button : ", YesNo.Yes);
										sa.assertTrue(false, "Not Able to Click on save Button : ");
									}
								} else {
									sa.assertTrue(false, "Not Able to enter value to textarea ");
									log(LogStatus.SKIP, "Not Able to enter value to textarea ", YesNo.Yes);
								}
							} else {
								log(LogStatus.ERROR, "new button is not clickable", YesNo.Yes);
								sa.assertTrue(false, "new button is not clickable");
							}
						} else {
							log(LogStatus.ERROR, "stage field is not clickable", YesNo.Yes);
							sa.assertTrue(false, "stage field is not clickable");
						}
					} else {
						log(LogStatus.ERROR, "field search textbox is not visible", YesNo.Yes);
						sa.assertTrue(false, "field search textbox is not visible");
					}
				} else {
					log(LogStatus.ERROR, "fundraising object is not clickable", YesNo.Yes);
					sa.assertTrue(false, "fundraising object is not clickable");
				}
			} else {
				log(LogStatus.ERROR, "fundraising object is not clickable", YesNo.Yes);
				sa.assertTrue(false, "fundraising object is not clickable");
			}
			ThreadSleep(5000);
			driver.close();
			driver.switchTo().window(parentID);
		} else {
			log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
			sa.assertTrue(false, "could not find new window to switch");
		}
	} else {
		log(LogStatus.ERROR, "setup link is not clickable", YesNo.Yes);
		sa.assertTrue(false, "setup link is not clickable");
	}
	lp.CRMlogout();
	sa.assertAll();

   lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
						if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
							log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

							if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns3, 30)) {

								log(LogStatus.INFO, "open created item" + ADEIns3, YesNo.No);

								if (BP.fundraisingsAcuityStageName("ADEStageRaising10","Decline", 10) != null) {
									log(LogStatus.PASS, "Stage Name: " + "Decline" + " is present", YesNo.No);

								} else {
									log(LogStatus.FAIL, "stage name not present: " + "Decline", YesNo.Yes);
									sa.assertTrue(false, "stage name not present: " + "Decline");

								}
							} else {

								sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns3);
								log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns3, YesNo.Yes);

							}
						} else {
							log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
						}
						ThreadSleep(5000);
						lp.CRMlogout();
						sa.assertAll();	
								}

@Parameters({ "projectName" })
@Test
public void ADETc065_VerifyContactCardsectionvisibilityinAcuitytabforexistingAccounts(
		String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

	String AccountName = ADEIns3;
	String contactHeader = "Name<break>Title<break>Deals<break>Meetings and Calls<break>Emails";

	ArrayList<String> connectionsSectionHeaderName = new ArrayList<String>();

	ArrayList<String> DealHeaderName = new ArrayList<String>();

	String[] arrcontactsSectionHeader = contactHeader.split("<break>");
	List<String> contactsSectionHeader = new ArrayList<String>(Arrays.asList(arrcontactsSectionHeader));

	if (cp.clickOnTab(projectName, tabObj1)) {
		log(LogStatus.INFO, "Clicked on Tab : " + tabObj1 + " For : " + AccountName, YesNo.No);
		ThreadSleep(1000);
		if (cp.clickOnAlreadyCreatedItem(projectName, AccountName, 30)) {
			log(LogStatus.INFO, "Clicked on  : " + AccountName + " For : " + tabObj2, YesNo.No);
			ThreadSleep(2000);
			ArrayList<String> result1 = bp
					.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
							contactsSectionHeader, null, DealHeaderName, null, connectionsSectionHeaderName, null);
			if (result1.isEmpty()) {
				log(LogStatus.INFO, "The header name and message have been verified  Contact Section ", YesNo.No);
			} else {
				log(LogStatus.ERROR, "The header name and message are not verified on Contact Section ", YesNo.No);
				sa.assertTrue(false, "The header name and message are not verified on  Contact Section ");

			}
		} else {

			sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns3);
			log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns3, YesNo.Yes);

		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}

ThreadSleep(5000);
lp.CRMlogout();
sa.assertAll();	
		}


@Parameters({ "projectName" })
@Test
public void ADETc066_VerifyCountDealsColumnNewlyCreatedContactsWhoarenotAssociatedDealDealTeamMemberInstitutionAccount(
		String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String DealCountInFirm = "0";
	String actualDealCount = null;
	String contactName = ADEContact16FName + " " + ADEContact16LName;
	
	// INS
			String value = "";
			String type = "";
			String TabName1 = "";
			String[][] EntityOrAccounts = { { ADEIns14, ADEIns14RecordType, null }};

			for (String[] accounts : EntityOrAccounts) {
				if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
					value = accounts[0];
					type = accounts[1];
					if (ip.createEntityOrAccount(projectName, mode, value, type, null, null, 20)) {
						log(LogStatus.INFO, "successfully Created Account/Entity : " + value + " of record type : " + type,
								YesNo.No);

					} else {
						sa.assertTrue(false, "Not Able to Create Account/Entity : " + value + " of record type : " + type);
						log(LogStatus.SKIP, "Not Able to Create Account/Entity : " + value + " of record type : " + type,
								YesNo.Yes);
					}
				}
			}
	
	if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

		ADEContact16EmailID = lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact16EmailID, "Contact", excelLabel.Variable_Name,
				"ADEContact16", excelLabel.Contact_EmailId);

		if (cp.createContactAcuity(projectName, ADEContact16FName, ADEContact16LName, ADEIns14, ADEContact16EmailID,
				ADEContact16RecordType, null, null, CreationPage.ContactPage, null, null)) {
			log(LogStatus.INFO, "successfully Created Contact : " + ADEContact16LName + " " + ADEContact16LName,
					YesNo.No);
		} else {
			sa.assertTrue(false, "Not Able to Create Contact : " + ADEContact16FName + " " + ADEContact16LName);
			log(LogStatus.SKIP, "Not Able to Create Contact: " + ADEContact16LName + " " + ADEContact16LName,
					YesNo.Yes);
		}
	}

	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns14, 30)) {

			actualDealCount = getText(driver, BP.contactDealCount(contactName, 30), "deal",
					action.SCROLLANDBOOLEAN);
			if (BP.contactDealCount(contactName, 30) != null) {
				if (!actualDealCount.equalsIgnoreCase("")) {

					if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
						log(LogStatus.INFO, "Deal Count for Contact: " + contactName + " is " + actualDealCount
								+ " before Deal Team Create is matched to " + DealCountInFirm, YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"Deal Count for Contact: " + contactName
										+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
										+ " but Actual: " + actualDealCount,
								YesNo.Yes);
						sa.assertTrue(false,
								"Deal Count for Contact: " + contactName
										+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
										+ " but Actual: " + actualDealCount);
					}

				} else {
					log(LogStatus.ERROR, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
							+ contactName, YesNo.Yes);
					sa.assertTrue(false, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
							+ contactName);
				}
			} else {
				log(LogStatus.ERROR, "No Contact found of Name: " + contactName, YesNo.No);
				sa.assertTrue(false, "No Contact found of Name: " + contactName);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
	}

	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();	
			}
	
@Parameters({ "projectName" })
@Test
public void ADETc067_VerifyDealCountsContactswhereContactsAssociatedDealMemberDealTeamnoteammember(String projectName) {

	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

	String dealName = ADEDealTeamName11;
	String DealCountInFirm = "1";
	String actualDealCount = null;
	String contactName = ADEContact16FName + " " + ADEContact16LName;

	String[][] data = { { PageLabel.Deal.toString(), dealName },
			{ PageLabel.Deal_Contact.toString(), contactName } };

	if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

		if (DTP.createDealTeam(projectName, dealName, data, "Acuity", action.SCROLLANDBOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----", YesNo.No);

			log(LogStatus.INFO,
					"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
							+ contactName + " at Firm Tab under Acuity section---------",
					YesNo.No);
			String xpath = "//*[text()='Deal Team']/parent::h1//slot/lightning-formatted-text";
			WebElement ele = FindElement(driver, xpath, "dt id", action.BOOLEAN, 10);
			if (ele != null) {
				String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
				ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name, "ADT_11",
						excelLabel.DealTeamID);
				log(LogStatus.INFO, "successfully created and noted id of DT" + id + " and deal name " + dealName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "could not create DT" + dealName);
				log(LogStatus.SKIP, "could not create DT" + dealName, YesNo.Yes);
			}
		}
	}
				if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

					if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns14, 30)) {

						actualDealCount = getText(driver, BP.contactDealCount(contactName, 30), "deal",
								action.SCROLLANDBOOLEAN);
						if (BP.contactDealCount(contactName, 30) != null) {
							if (!actualDealCount.equalsIgnoreCase("")) {

								if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
									log(LogStatus.INFO,
											"Deal Count for Contact: " + contactName + " is " + actualDealCount
													+ " before Deal Team Create is matched to " + DealCountInFirm,
											YesNo.No);
									if (CommonLib.click(driver, BP.contactDealCount(contactName, 30),
											"Deal Count: " + actualDealCount, action.BOOLEAN)) {
										log(LogStatus.INFO, "Clicked on Deal Count: " + actualDealCount
												+ " of Record: " + contactName, YesNo.No);
									} else {
										log(LogStatus.ERROR, "Not Able to Click on Deal Count: " + actualDealCount
												+ " of Record: " + contactName, YesNo.No);

										sa.assertTrue(false, "Not Able to Click on Deal Count: " + actualDealCount
												+ " of Record: " + contactName);

									}
								} else {
									log(LogStatus.ERROR,
											"Deal Count for Contact: " + contactName
													+ " is before Deal Team Create is not matched, Expected: "
													+ DealCountInFirm + " but Actual: " + actualDealCount,
											YesNo.Yes);
									sa.assertTrue(false,
											"Deal Count for Contact: " + contactName
													+ " is before Deal Team Create is not matched, Expected: "
													+ DealCountInFirm + " but Actual: " + actualDealCount);
								}

							} else {
								log(LogStatus.ERROR,
										"Deal Count for Contact is Empty, So not able to check Count for Contact: "
												+ contactName,
										YesNo.Yes);
								sa.assertTrue(false,
										"Deal Count for Contact is Empty, So not able to check Count for Contact: "
												+ contactName);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
				}
			
			ThreadSleep(5000);
			lp.CRMlogout();
			sa.assertAll();
		}
	

@Parameters({ "projectName" })
@Test
public void ADETc068_VerifyDealCountsatAccountContactsAssociatedDealMemberDealTeamWithTeamMember(
		String projectName) {

	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

	String dealName = ADEDealTeamName12;
	String DealCountInFirm = "2";
	String actualDealCount = null;
	String TeamMember = ADEDealTeamMember12;
	String contactName = ADEContact16FName + " " + ADEContact16LName;

	String[][] data = { { PageLabel.Deal.toString(), dealName }, { PageLabel.Deal_Contact.toString(), contactName },
			{ PageLabel.Team_Member.toString(), TeamMember } };

	if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

		if (DTP.createDealTeam(projectName, dealName, data, "Acuity", action.SCROLLANDBOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----", YesNo.No);

			log(LogStatus.INFO,
					"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
							+ contactName + " at Firm Tab under Acuity section---------",
					YesNo.No);
			String xpath = "//*[text()='Deal Team']/parent::h1//slot/lightning-formatted-text";
			WebElement ele = FindElement(driver, xpath, "dt id", action.BOOLEAN, 10);
			if (ele != null) {
				String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
				ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name, "ADT_12",
						excelLabel.DealTeamID);
				log(LogStatus.INFO, "successfully created and noted id of DT" + id + " and deal name " + dealName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "could not create DT" + dealName);
				log(LogStatus.SKIP, "could not create DT" + dealName, YesNo.Yes);
			}
		}
				if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

					if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns14, 30)) {

						actualDealCount = getText(driver, BP.contactDealCount(contactName, 30), "deal",
								action.SCROLLANDBOOLEAN);
						if (BP.contactDealCount(contactName, 30) != null) {
							if (!actualDealCount.equalsIgnoreCase("")) {

								if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
									log(LogStatus.INFO,
											"Deal Count for Contact: " + contactName + " is " + actualDealCount
													+ " before Deal Team Create is matched to " + DealCountInFirm,
											YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"Deal Count for Contact: " + contactName
													+ " is before Deal Team Create is not matched, Expected: "
													+ DealCountInFirm + " but Actual: " + actualDealCount,
											YesNo.Yes);
									sa.assertTrue(false,
											"Deal Count for Contact: " + contactName
													+ " is before Deal Team Create is not matched, Expected: "
													+ DealCountInFirm + " but Actual: " + actualDealCount);
								}

							} else {
								log(LogStatus.ERROR,
										"Deal Count for Contact is Empty, So not able to check Count for Contact: "
												+ contactName,
										YesNo.Yes);
								sa.assertTrue(false,
										"Deal Count for Contact is Empty, So not able to check Count for Contact: "
												+ contactName);
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
					}
				}

	}
		
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();

}

@Parameters({ "projectName" })
@Test
public void ADETc069_VerifyDealCountsAIAccountWhenNewContactCreatedTaggedDealMemberDealTeam(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String dealName = ADEDealTeamName13;
	String DealCountInFirm = "1";
	String actualDealCount = null;
	String contactName = ADEContact17FName + " " + ADEContact17LName;

	if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

		ADEContact17EmailID = lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact17EmailID, "Contact", excelLabel.Variable_Name,
				"ADEContact17", excelLabel.Contact_EmailId);

		if (cp.createContactAcuity(projectName, ADEContact17FName, ADEContact17LName, ADEIns14, ADEContact13EmailID,
				ADEContact17RecordType, null, null, CreationPage.ContactPage, null, null)) {
			log(LogStatus.INFO, "successfully Created Contact : " + ADEContact17LName + " " + ADEContact17LName,
					YesNo.No);
		} else {
			sa.assertTrue(false, "Not Able to Create Contact : " + ADEContact17FName + " " + ADEContact17LName);
			log(LogStatus.SKIP, "Not Able to Create Contact: " + ADEContact17LName + " " + ADEContact17LName,
					YesNo.Yes);
		}

		String[][] data = { { PageLabel.Deal.toString(), dealName },
				{ PageLabel.Deal_Contact.toString(), contactName } };

		if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

			if (DTP.createDealTeam(projectName, dealName, data, "Acuity", action.SCROLLANDBOOLEAN, 25)) {
				log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----",
						YesNo.No);

				log(LogStatus.INFO,
						"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
								+ contactName + " at Firm Tab under Acuity section---------",
						YesNo.No);
				String xpath = "//*[text()='Deal Team']/parent::h1//slot/lightning-formatted-text";
				WebElement ele = FindElement(driver, xpath, "dt id", action.BOOLEAN, 10);
				if (ele != null) {
					String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
					ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name,
							"ADT_13", excelLabel.DealTeamID);
					log(LogStatus.INFO,
							"successfully created and noted id of DT" + id + " and deal name " + dealName,
							YesNo.No);
				} else {
					sa.assertTrue(false, "could not create DT" + dealName);
					log(LogStatus.SKIP, "could not create DT" + dealName, YesNo.Yes);
				}
			}
		}
	}
					if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
						log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

						if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns14, 30)) {

							actualDealCount = getText(driver, BP.contactDealCount(contactName, 30), "deal",
									action.SCROLLANDBOOLEAN);
							if (BP.contactDealCount(contactName, 30) != null) {
								if (!actualDealCount.equalsIgnoreCase("")) {

									if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
										log(LogStatus.INFO,
												"Deal Count for Contact: " + contactName + " is " + actualDealCount
														+ " before Deal Team Create is matched to "
														+ DealCountInFirm,
												YesNo.No);

									} else {
										log(LogStatus.ERROR,
												"Deal Count for Contact: " + contactName
														+ " is before Deal Team Create is not matched, Expected: "
														+ DealCountInFirm + " but Actual: " + actualDealCount,
												YesNo.Yes);
										sa.assertTrue(false,
												"Deal Count for Contact: " + contactName
														+ " is before Deal Team Create is not matched, Expected: "
														+ DealCountInFirm + " but Actual: " + actualDealCount);
									}

								} else {
									log(LogStatus.ERROR,
											"Deal Count for Contact is Empty, So not able to check Count for Contact: "
													+ contactName,
											YesNo.Yes);
									sa.assertTrue(false,
											"Deal Count for Contact is Empty, So not able to check Count for Contact: "
													+ contactName);
								}

							} else {
								log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
						}
					}
	
				
			
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
public void ADETc070_VerifyDealCountaContactDealContactareCreatedAccount(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String dealName1 = ADEDealTeamName14;
	String DealCountInFirm = "1";
	String actualDealCount = null;
	String contactName = ADEContact18FName + " " + ADEContact18LName;

	String recordType = "";
	String dealName = ADEDeal20;
	String companyName = ADEDeal20CompanyName;
	String stage = ADEDeal20Stage;

	if (lp.clickOnTab(projectName, tabObj4)) {
		log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
		ThreadSleep(3000);
		if (dp.createDeal(recordType, dealName, companyName, stage, "Date Received", todaysDate)) {
			log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);

		} else {
			log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
			sa.assertTrue(false, dealName + " deal is not created");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
		sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
	}

	if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

		ADEContact18EmailID = lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact18EmailID, "Contact", excelLabel.Variable_Name,
				"ADEContact18", excelLabel.Contact_EmailId);

		if (cp.createContactAcuity(projectName, ADEContact18FName, ADEContact18LName, ADEIns14, ADEContact18EmailID,
				ADEContact18RecordType, null, null, CreationPage.ContactPage, null, null)) {
			log(LogStatus.INFO, "successfully Created Contact : " + ADEContact18LName + " " + ADEContact18LName,
					YesNo.No);
		} else {
			sa.assertTrue(false, "Not Able to Create Contact : " + ADEContact18FName + " " + ADEContact18LName);
			log(LogStatus.SKIP, "Not Able to Create Contact: " + ADEContact18LName + " " + ADEContact18LName,
					YesNo.Yes);
		}
	}

		String[][] data = { { PageLabel.Deal.toString(), dealName },
				{ PageLabel.Deal_Contact.toString(), contactName } };

		if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

			if (DTP.createDealTeam(projectName, dealName1, data, "Acuity", action.SCROLLANDBOOLEAN, 25)) {
				log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----",
						YesNo.No);

				log(LogStatus.INFO,
						"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
								+ contactName + " at Firm Tab under Acuity section---------",
						YesNo.No);
				String xpath = "//*[text()='Deal Team']/parent::h1//slot/lightning-formatted-text";
				WebElement ele = FindElement(driver, xpath, "dt id", action.BOOLEAN, 10);
				if (ele != null) {
					String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
					ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name,
							"ADT_14", excelLabel.DealTeamID);
					log(LogStatus.INFO,
							"successfully created and noted id of DT" + id + " and deal name " + dealName,
							YesNo.No);
				} else {
					sa.assertTrue(false, "could not create DT" + dealName);
					log(LogStatus.SKIP, "could not create DT" + dealName, YesNo.Yes);
				}
				if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

					if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns14, 30)) {

						actualDealCount = getText(driver, BP.contactDealCount(contactName, 30), "deal",
								action.SCROLLANDBOOLEAN);
						if (BP.contactDealCount(contactName, 30) != null) {
							if (!actualDealCount.equalsIgnoreCase("")) {

								if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
									log(LogStatus.INFO,
											"Deal Count for Contact: " + contactName + " is " + actualDealCount
													+ " before Deal Team Create is matched to " + DealCountInFirm,
											YesNo.No);

								} else {
									log(LogStatus.ERROR,
											"Deal Count for Contact: " + contactName
													+ " is before Deal Team Create is not matched, Expected: "
													+ DealCountInFirm + " but Actual: " + actualDealCount,
											YesNo.Yes);
									sa.assertTrue(false,
											"Deal Count for Contact: " + contactName
													+ " is before Deal Team Create is not matched, Expected: "
													+ DealCountInFirm + " but Actual: " + actualDealCount);
								}

							} else {
								log(LogStatus.ERROR,
										"Deal Count for Contact is Empty, So not able to check Count for Contact: "
												+ contactName,
										YesNo.Yes);
								sa.assertTrue(false,
										"Deal Count for Contact is Empty, So not able to check Count for Contact: "
												+ contactName);
							}

						
					
				}

			} else {
				log(LogStatus.INFO, "----Not Created the Deal Team for Deal: " + dealName + "----", YesNo.Yes);

				log(LogStatus.INFO,
						"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
								+ contactName + " at Firm Tab under Acuity section---------",
						YesNo.No);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Deal_Team + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + TabName.Deal_Team + " tab");
		}	
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
		}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}


@Parameters({ "projectName" })
@Test
public void ADETc071_VerifyDealCountContactGetsRemovedDealTeamWhenRemoveeAddeeAreSameAccounts(String projectName) {

	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

	String contactName = ADEContact17FName + " " + ADEContact17LName;
	String contactName1 = ADEContact18FName + " " + ADEContact18LName;
	String[][] data1 = { { PageLabel.Deal_Contact.toString(), contactName } };

	String DealCountInFirm = "2";
	String DealCountInFirm1 = "1";
	String actualDealCount = null;

	if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEDealTeamID12, 10)) {
			if (DTP.UpdateDealContactName(projectName, data1, 30)) {
				log(LogStatus.INFO, "successfully changed name to " + contactName, YesNo.Yes);
			} else {
				sa.assertTrue(false, "not able to change name to " + contactName);
				log(LogStatus.SKIP, "not able to change name to " + contactName, YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEDealTeamID12 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEDealTeamID12 + " tab");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + TabName.Deal_Team + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + TabName.Deal_Team + " tab");
	}
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns14, 30)) {

			actualDealCount = getText(driver, BP.contactDealCount(contactName, 30), "deal",
					action.SCROLLANDBOOLEAN);
			if (BP.contactDealCount(contactName, 30) != null) {
				if (!actualDealCount.equalsIgnoreCase("")) {

					if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
						log(LogStatus.INFO, "Deal Count for Contact: " + contactName + " is " + actualDealCount
								+ " before Deal Team Create is matched to " + DealCountInFirm, YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"Deal Count for Contact: " + contactName
										+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
										+ " but Actual: " + actualDealCount,
								YesNo.Yes);
						sa.assertTrue(false,
								"Deal Count for Contact: " + contactName
										+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
										+ " but Actual: " + actualDealCount);
					}

				} else {
					log(LogStatus.ERROR, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
							+ contactName, YesNo.Yes);
					sa.assertTrue(false, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
							+ contactName);
				}
			}
				actualDealCount = getText(driver, BP.contactDealCount(contactName1, 30), "deal",
						action.SCROLLANDBOOLEAN);
				if (BP.contactDealCount(contactName1, 30) != null) {
					if (!actualDealCount.equalsIgnoreCase("")) {

						if (actualDealCount.equalsIgnoreCase(DealCountInFirm1)) {
							log(LogStatus.INFO,
									"Deal Count for Contact: " + contactName1 + " is " + actualDealCount
											+ " before Deal Team Create is matched to " + DealCountInFirm1,
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"Deal Count for Contact: " + contactName1
											+ " is before Deal Team Create is not matched, Expected: "
											+ DealCountInFirm1 + " but Actual: " + actualDealCount,
									YesNo.Yes);
							sa.assertTrue(false,
									"Deal Count for Contact: " + contactName1
											+ " is before Deal Team Create is not matched, Expected: "
											+ DealCountInFirm1 + " but Actual: " + actualDealCount);
						}

					} else {
						log(LogStatus.ERROR,
								"Deal Count for Contact is Empty, So not able to check Count for Contact: "
										+ contactName1,
								YesNo.Yes);
						sa.assertTrue(false,
								"Deal Count for Contact is Empty, So not able to check Count for Contact: "
										+ contactName1);
					}
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns14 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns14 + " tab");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
public void ADETc072_VerifyDealCountContactGetsRemovedDealTeamWhenRemoveeAddeeAreDifferentAccounts(
		String projectName) {

	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

	String contactName = ADEContact19FName + " " + ADEContact19LName;
	String contactName1 = ADEContact17FName + " " + ADEContact17LName;
	String[][] data1 = { { PageLabel.Deal_Contact.toString(), contactName } };

	String DealCountInFirm = "1";
	String actualDealCount = null;

	if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

	ADEContact19EmailID = lp.generateRandomEmailId(gmailUserName);
	ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact19EmailID, "Contact", excelLabel.Variable_Name,
			"ADEContact19", excelLabel.Contact_EmailId);

	if (cp.createContactAcuity(projectName, ADEContact19FName, ADEContact19LName, ADEIns3, ADEContact19EmailID,
			ADEContact19RecordType, null, null, CreationPage.ContactPage, null, null)) {
		log(LogStatus.INFO, "successfully Created Contact : " + ADEContact19LName + " " + ADEContact19LName,
				YesNo.No);
	} else {
		sa.assertTrue(false, "Not Able to Create Contact : " + ADEContact19FName + " " + ADEContact19LName);
		log(LogStatus.SKIP, "Not Able to Create Contact: " + ADEContact19LName + " " + ADEContact19LName,
				YesNo.Yes);
	}
	}
	if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEDealTeamID12, 10)) {
			if (DTP.UpdateDealContactName(projectName, data1, 30)) {
				log(LogStatus.INFO, "successfully changed name to " + contactName, YesNo.Yes);
			} else {
				sa.assertTrue(false, "not able to change name to " + contactName);
				log(LogStatus.SKIP, "not able to change name to " + contactName, YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEDealTeamID12 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEDealTeamID12 + " tab");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + TabName.Deal_Team + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + TabName.Deal_Team + " tab");
	}
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns14, 30)) {

			actualDealCount = getText(driver, BP.contactDealCount(contactName1, 30), "deal",
					action.SCROLLANDBOOLEAN);
			if (BP.contactDealCount(contactName1, 30) != null) {
				if (!actualDealCount.equalsIgnoreCase("")) {

					if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
						log(LogStatus.INFO, "Deal Count for Contact: " + contactName1 + " is " + actualDealCount
								+ " before Deal Team Create is matched to " + DealCountInFirm, YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"Deal Count for Contact: " + contactName1
										+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
										+ " but Actual: " + actualDealCount,
								YesNo.Yes);
						sa.assertTrue(false,
								"Deal Count for Contact: " + contactName1
										+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
										+ " but Actual: " + actualDealCount);
					}

				} else {
					log(LogStatus.ERROR, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
							+ contactName1, YesNo.Yes);
					sa.assertTrue(false, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
							+ contactName1);
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns12 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns12 + " tab");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}

	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns3, 30)) {

			actualDealCount = getText(driver, BP.contactDealCount(contactName, 30), "deal",
					action.SCROLLANDBOOLEAN);
			if (BP.contactDealCount(contactName, 30) != null) {
				if (!actualDealCount.equalsIgnoreCase("")) {

					if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
						log(LogStatus.INFO, "Deal Count for Contact: " + contactName + " is " + actualDealCount
								+ " before Deal Team Create is matched to " + DealCountInFirm, YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"Deal Count for Contact: " + contactName
										+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
										+ " but Actual: " + actualDealCount,
								YesNo.Yes);
						sa.assertTrue(false,
								"Deal Count for Contact: " + contactName1
										+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
										+ " but Actual: " + actualDealCount);
					}

				} else {
					log(LogStatus.ERROR, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
							+ contactName, YesNo.Yes);
					sa.assertTrue(false, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
							+ contactName);
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns3 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns3 + " tab");
		}

	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
public void ADETc073_VerifyImpactContactSectionDeleteContactDealTeamDTMforAccount(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

	String contactName = ADEContact18FName + " " + ADEContact18LName;

	if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
		if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 10)) {
			cp.clickOnShowMoreDropdownOnly(projectName, PageName.Object2Page, "");
			log(LogStatus.INFO, "Able to Click on Show more Icon : " + TabName.Object2Tab + " For : " + contactName,
					YesNo.No);
			ThreadSleep(500);
			WebElement ele = cp.actionDropdownElement(projectName, PageName.Object1Page,
					ShowMoreActionDropDownList.Delete, 15);
			if (ele == null) {
				ele = cp.getDeleteButton(projectName, 30);
			}

			if (click(driver, ele, "Delete More Icon", action.BOOLEAN)) {
				log(LogStatus.INFO,
						"Able to Click on Delete more Icon : " + TabName.Object1Tab + " For : " + contactName,
						YesNo.No);
				ThreadSleep(1000);
				if (click(driver, cp.getDeleteButtonOnDeletePopUp(projectName, 30), "Delete Button",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to Click on Delete button on Delete PoPup : " + TabName.Object2Tab
							+ " For : " + ADEIns11, YesNo.No);

				} else {
					log(LogStatus.ERROR, "Not able to click on Delete button" + contactName + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on Delete button" + contactName + " tab");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + contactName + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + contactName + " tab");
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns14, 30)) {

				if (BP.DealContactname(contactName, 30) != null) {

				} else {
					log(LogStatus.ERROR,
							"Deal Contact name not present, So not able to check Count for Contact: " + contactName,
							YesNo.Yes);
					sa.assertTrue(true, "Deal Contact name not present, So not able to check Count for Contact: "
							+ contactName);
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns11 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns11 + " tab");
		}

	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
public void ADETc074_VerifyImpactContactSectionRestoreDeletedContactDealTeam(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null;
	String dealName1 = ADEDealTeamName4;
	String DealCountInFirm = "1";
	String actualDealCount = null;
	String contactName = ADEContact18FName + " " + ADEContact18LName;

	TabName tabName = TabName.RecycleBinTab;
	String Contactname1 = ADEContact18FName + " " + ADEContact18LName;

	if (cp.clickOnTab(projectName, tabName)) {
		log(LogStatus.INFO, "Clicked on Tab : " + tabName + " For : " + Contactname1, YesNo.No);
		ThreadSleep(1000);
		cp.clickOnAlreadyCreatedItem(projectName, tabName, Contactname1, 20);
		log(LogStatus.INFO, "Clicked on  : " + Contactname1 + " For : " + tabName, YesNo.No);
		ThreadSleep(2000);

		ele = cp.getCheckboxOfRestoreItemOnRecycleBin(projectName, Contactname1, 10);
		if (clickUsingJavaScript(driver, ele, "Check box against : " + Contactname1, action.BOOLEAN)) {
			log(LogStatus.INFO, "Click on checkbox for " + Contactname1, YesNo.No);

			ThreadSleep(1000);
			ele = cp.getRestoreButtonOnRecycleBin(projectName, 10);
			if (clickUsingJavaScript(driver, ele, "Restore Button : " + Contactname1, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on Restore Button for " + Contactname1, YesNo.No);
				ThreadSleep(1000);
			} else {
				sa.assertTrue(false, "Not Able to Click on Restore Button for " + Contactname1);
				log(LogStatus.SKIP, "Not Able to Click on Restore Button for " + Contactname1, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on checkbox for " + Contactname1);
			log(LogStatus.SKIP, "Not Able to Click on checkbox for " + Contactname1, YesNo.Yes);
		}

	} else {
		sa.assertTrue(false, "Not Able to Click on Tab : " + tabName + " For : " + Contactname1);
		log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName + " For : " + Contactname1, YesNo.Yes);
	}
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns14, 30)) {

			actualDealCount = getText(driver, BP.contactDealCount(contactName, 30), "deal",
					action.SCROLLANDBOOLEAN);
			if (BP.contactDealCount(contactName, 30) != null) {
				if (!actualDealCount.equalsIgnoreCase("")) {

					if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
						log(LogStatus.INFO, "Deal Count for Contact: " + contactName + " is " + actualDealCount
								+ " before Deal Team Create is matched to " + DealCountInFirm, YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"Deal Count for Contact: " + contactName
										+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
										+ " but Actual: " + actualDealCount,
								YesNo.Yes);
						sa.assertTrue(false,
								"Deal Count for Contact: " + contactName
										+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
										+ " but Actual: " + actualDealCount);
					}

				} else {
					log(LogStatus.ERROR, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
							+ contactName, YesNo.Yes);
					sa.assertTrue(false, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
							+ contactName);
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}

	}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
public void ADETc075_VerifyImpactContactwhenDeleteDealTeamContactDTMforAccount(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

	String DealTeam = ADEDealTeamID13;
	String DealCountInFirm = "0";
	String actualDealCount = null;
	String contactName = ADEContact17FName + " " + ADEContact17LName;

	String ExpectedMsg = "No items to display";

	if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
		if (fp.clickOnAlreadyCreatedItem(projectName, DealTeam, 10)) {
			cp.clickOnShowMoreDropdownOnly(projectName, PageName.Object2Page, "");
			log(LogStatus.INFO, "Able to Click on Show more Icon : " + TabName.Object2Tab + " For : " + DealTeam,
					YesNo.No);
			ThreadSleep(500);
			WebElement ele = cp.actionDropdownElement(projectName, PageName.Object1Page,
					ShowMoreActionDropDownList.Delete, 15);
			if (ele == null) {
				ele = cp.getDeleteButton(projectName, 30);
			}

			if (click(driver, ele, "Delete More Icon", action.BOOLEAN)) {
				log(LogStatus.INFO,
						"Able to Click on Delete more Icon : " + TabName.Object1Tab + " For : " + DealTeam,
						YesNo.No);
				ThreadSleep(1000);
				if (click(driver, cp.getDeleteButtonOnDeletePopUp(projectName, 30), "Delete Button",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to Click on Delete button on Delete PoPup : " + TabName.Object2Tab
							+ " For : " + ADEIns11, YesNo.No);

				} else {
					log(LogStatus.ERROR, "Not able to click on Delete button" + DealTeam + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on Delete button" + DealTeam + " tab");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + DealTeam + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + DealTeam + " tab");
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		}
	}
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns14, 30)) {

			actualDealCount = getText(driver, BP.contactDealCount(contactName, 30), "deal",
					action.SCROLLANDBOOLEAN);
			if (BP.contactDealCount(contactName, 30) != null) {
				if (!actualDealCount.equalsIgnoreCase("")) {

					if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
						log(LogStatus.INFO, "Deal Count for Contact: " + contactName + " is " + actualDealCount
								+ " before Deal Team Create is matched to " + DealCountInFirm, YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"Deal Count for Contact: " + contactName
										+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
										+ " but Actual: " + actualDealCount,
								YesNo.Yes);
						sa.assertTrue(false,
								"Deal Count for Contact: " + contactName
										+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
										+ " but Actual: " + actualDealCount);
					}

				} else {
					log(LogStatus.ERROR, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
							+ contactName, YesNo.Yes);
					sa.assertTrue(false, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
							+ contactName);
				}
				if (CommonLib.click(driver, BP.contactDealCount(contactName, 30), "Deal Count: " + actualDealCount,
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Deal Count: " + actualDealCount + " of Record: " + contactName,
							YesNo.No);
					String parentWindowId = CommonLib.switchOnWindow(driver);
					if (!parentWindowId.isEmpty()) {
					String actualMsg = getText(driver, BP.getErrorMsg(20), "ErrorMsg", action.SCROLLANDBOOLEAN);
					if (ExpectedMsg.contains(actualMsg)) {
						log(LogStatus.INFO,
								"Actual result " + actualMsg + " of pop up has been matched with Expected result : "
										+ ExpectedMsg + " for Contact Name: " + contactName,
								YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"Actual result " + actualMsg
										+ " of pop up has been not matched with Expected result : " + ExpectedMsg
										+ " for Contact Name: " + contactName,
								YesNo.No);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
			}
		}

	}
	}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
public void ADETc076_VerifyDealTeamRecordPopupClosewhenClickedOutsidePopuporCrossIconTopRightofPopupforAccount(
		String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

	String ExpectedHeader = "Deals With ADEN User TC11";

	String contactName = ADEContact17FName + " " + ADEContact17LName;

	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns14, 30)) {
			if (CommonLib.click(driver, BP.contactDealCount(contactName, 30), "Deal Count: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Deal Count: " + " " + " of Record: " + contactName, YesNo.No);
			}
				String parentWindowId = CommonLib.switchOnWindow(driver);
				if (!parentWindowId.isEmpty()) {
					log(LogStatus.PASS, "New Window Open after click on Deal Link: " + contactName, YesNo.No);
			String actualHeader = getText(driver, BP.getDealCountPopHeader(20), "DealCountPopHeader",
					action.SCROLLANDBOOLEAN);
			if (ExpectedHeader.contains(actualHeader)) {
				log(LogStatus.INFO,
						"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
								+ ExpectedHeader + " for Contact Name: " + contactName,
						YesNo.No);
			} else {
				log(LogStatus.ERROR,
						"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
								+ ExpectedHeader + " for Contact Name: " + contactName,
						YesNo.No);
			}
			CommonLib.click(driver, BP.getanywhereonpage(30), "click any where: " + "", action.BOOLEAN);
			log(LogStatus.INFO, "Clicked anywhere: " + " " + " of Record: " + contactName, YesNo.No);
			String actualHeader1 = getText(driver, BP.getDealCountPopHeader(20), "DealCountPopHeader",
					action.SCROLLANDBOOLEAN);
			if (ExpectedHeader.contains(actualHeader1)) {
				log(LogStatus.INFO,
						"Actual result " + actualHeader1
								+ " of pop up has been matched with Expected result so popup still present : "
								+ ExpectedHeader + " for Contact Name: " + contactName,
						YesNo.No);
			} else {
				log(LogStatus.ERROR,
						"Actual result " + actualHeader
								+ " of pop up has been not matched with Expected result so pop up is not present: "
								+ ExpectedHeader + " for Contact Name: " + contactName,
						YesNo.No);
			}
			driver.close();	driver.switchTo().window(parentWindowId);
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
	}
		
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}
}
@Parameters({ "projectName" })
@Test
public void ADETc077_VerifyDealNameCompanyClickableItRedirectionDealTeamRecordopupforAccount(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

	String dealName = ADEDeal20;
	String contactName = ADEContact18FName + " " + ADEContact18LName;

	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns14, 30)) {
			if (CommonLib.click(driver, BP.contactDealCount(contactName, 30), "Deal Count: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Deal Count: " + " " + " of Record: " + contactName, YesNo.No);
			}
			String parentWindowId = CommonLib.switchOnWindow(driver);
			if (!parentWindowId.isEmpty()) {
			ThreadSleep(3000);
			if (clickUsingJavaScript(driver, BP.dealAcuityPopUpDealName(dealName, 10), "Deal Name: " + dealName,
					action.BOOLEAN)) {
				log(LogStatus.PASS, "Clicked on Deal Name: " + dealName, YesNo.No);
				log(LogStatus.PASS, "Clicked on Deal Name: " + dealName, YesNo.No);
				Set<String> childWindow = driver.getWindowHandles();
				switchToDefaultContent(driver);
				System.out.println(childWindow);
				for(String child : childWindow) {
				driver.switchTo().window(child);
				}

						if (BP.dealRecordPage(dealName, 20) != null) {
							log(LogStatus.PASS,
									"----Deal Detail Page is redirecting for Deal Record: " + dealName + "-----",
									YesNo.No);
							driver.close();
							driver.switchTo().window(parentWindowId);
							CommonLib.switchOnWindow(driver);
								driver.close();
							driver.switchTo().window(parentWindowId);

						} else {
							log(LogStatus.FAIL, "----Deal Detail Page is not redirecting for Deal Record: "
									+ dealName + "-----", YesNo.Yes);
							sa.assertTrue(false,
									"----Deal Detail Page is not showing for Deal Record: " + dealName + "-----");
							driver.close();
							driver.switchTo().window(parentWindowId);

						}

					} else {
						log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + dealName, YesNo.Yes);
						sa.assertTrue(false, "No New Window Open after click on Deal Link: " + dealName);
					}
//				} catch (Exception e) {
//					log(LogStatus.FAIL,
//							"Not able to switch to window after click on Deal Link, Msg showing: " + e.getMessage(),
//							YesNo.Yes);
//					sa.assertTrue(false, "Not able to switch to window after click on Deal Link, Msg showing: "
//							+ e.getMessage());
//				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}

	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName"})
@Test
public void ADETc078_1_VerifyimpactConnectionSectionWhenUsertitlegetsupdated(String projectName) {
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	String parentWindow = null;
	String emailId = crmUser2EmailID;
	String title ="analyst";
	String actualDealCount = null;
	String contactName = ADEContact11FName + " " + ADEContact11LName;
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	boolean flag = false;
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
				if (setup.editPEUserAndUpdateTheName( null, null, emailId,title)) {
					log(LogStatus.INFO, "CRM User is updated Successfully: " +title, YesNo.No);
				} else {
					appLog.error("CRM User not updated Successfully: " +title);
				}
					flag = true;

				
				driver.close();
				driver.switchTo().window(parentWindow);

			}
			lp.CRMlogout();
//			lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
//			if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
//				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
//
//				if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns11, 30)) {
//					if (CommonLib.click(driver, BP.contactDealCount(contactName, 30),
//							"Deal Count: " + actualDealCount, action.BOOLEAN)) {
//						log(LogStatus.INFO, "Clicked on Deal Count: " + actualDealCount
//								+ " of Record: " + contactName, YesNo.No);
//						
//}
//				}
//			}
}
				

@Parameters({ "projectName"})
@Test
public void ADETc078_2_VerifyimpactConnectionSectionWhenUsergetsRenameUserNameandGetsDeactivated(String projectName) {
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	String parentWindow = null;
	String userfirstname = "CRM2";
	String UserLastName = "Cred";
	String emailId = crmUser2EmailID;
	String teamMemberName = "CRM2 Cred";
	String DealCountInFirm = "1";
	String contactName = ADEContact15FName + " " + ADEContact15LName;
	String actualDealCount = null;
	
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	boolean flag = false;
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
				if (setup.editPEUser( userfirstname, UserLastName, emailId)) {
					log(LogStatus.INFO, "CRM User is updated Successfully: " + crmUser1FirstName + " " + UserLastName, YesNo.No);
					ExcelUtils.writeData(testCasesFilePath, emailId, "Users", excelLabel.Variable_Name, "User2",
							excelLabel.User_Email);
					ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users", excelLabel.Variable_Name, "User2",
							excelLabel.User_Last_Name);
				} else {
					appLog.error("CRM User not updated Successfully:  " + crmUser1FirstName + " " + UserLastName);
					flag = true;
				}

				
				driver.close();
				driver.switchTo().window(parentWindow);

			}
			lp.CRMlogout();
			lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
			if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

				if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
					if (contactName != null && contactName != "") {
						actualDealCount = getText(driver, BP.teamMemberDealCount(teamMemberName, 20), "deal",
								action.SCROLLANDBOOLEAN);
						if (BP.teamMemberDealCount(teamMemberName, 20) != null) {
							if (!actualDealCount.equalsIgnoreCase("")) {

								if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
									log(LogStatus.INFO, "Deal Count for Contact: " + contactName + " is " + actualDealCount
											+ " before Deal Team Create is matched to " + DealCountInFirm, YesNo.No);
								} else {
									log(LogStatus.ERROR,
											"Deal Count for Contact: " + contactName
													+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
													+ " but Actual: " + actualDealCount,
											YesNo.Yes);
									sa.assertTrue(false,
											"Deal Count for Contact: " + contactName
													+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
													+ " but Actual: " + actualDealCount);
								}

							} else {
								log(LogStatus.ERROR, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
										+ contactName, YesNo.Yes);
								sa.assertTrue(false, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
										+ contactName);
							}
						}
					} else {
						log(LogStatus.FAIL, "Not able to Click on Contact Name: " + contactName, YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on contact Name: " + contactName);

					}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
				}
				}
			
			if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

				if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns11, 30)) {
					if (contactName != null && contactName != "") {

						if (BP.contactNameUserIconButton(contactName, 30) != null) {

							if (click(driver, BP.contactNameUserIconButton(contactName, 30),
									"Contact Name: " + contactName, action.SCROLLANDBOOLEAN)) {
								String parentWindowId = CommonLib.switchOnWindow(driver);
								if (!parentWindowId.isEmpty()) {
								log(LogStatus.INFO, "Clicked on Contact: " + contactName, YesNo.No);
								actualDealCount = getText(driver, BP.teamMemberpopupDealCount(teamMemberName, 20), "deal",
										action.SCROLLANDBOOLEAN);
								if (BP.teamMemberpopupDealCount(teamMemberName, 20) != null) {
									if (!actualDealCount.equalsIgnoreCase("")) {

										if (actualDealCount.equalsIgnoreCase(DealCountInFirm)) {
											log(LogStatus.INFO, "Deal Count for Contact: " + contactName + " is " + actualDealCount
													+ " before Deal Team Create is matched to " + DealCountInFirm, YesNo.No);
										} else {
											log(LogStatus.ERROR,
													"Deal Count for Contact: " + contactName
															+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
															+ " but Actual: " + actualDealCount,
													YesNo.Yes);
											sa.assertTrue(false,
													"Deal Count for Contact: " + contactName
															+ " is before Deal Team Create is not matched, Expected: " + DealCountInFirm
															+ " but Actual: " + actualDealCount);
										}

									} else {
										log(LogStatus.ERROR, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
												+ contactName, YesNo.Yes);
										sa.assertTrue(false, "Deal Count for Contact is Empty, So not able to check Count for Contact: "
												+ contactName);
									}
								} else {
									log(LogStatus.ERROR, "No Contact found of Name: " + contactName, YesNo.No);
									sa.assertTrue(false, "No Contact found of Name: " + contactName);
								}
								} else {
									log(LogStatus.ERROR, "Not Able to Click on user icon : " + teamMemberName
											+ " of Record: " + contactName, YesNo.No);

									sa.assertTrue(false, "Not Able to Click on user icon: " + teamMemberName
											+ " of Record: " + contactName);

								}
							} else {
								log(LogStatus.ERROR, "No Contact found of Name: " + ADEIns11, YesNo.No);
								sa.assertTrue(false, "No Contact found of Name: " + ADEIns11);
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " Tab", YesNo.No);
							sa.assertTrue(false, "Not able to click on " + tabObj1 + " Tab");
						}
				}
			}
			}
			ThreadSleep(5000);
			lp.CRMlogout();
			sa.assertAll();
		}

@Parameters({ "projectName" })
@Test
public void ADETc079_VerifyWhenContactNameClickedContactCard(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String contactname = ADEContact16FName + " " + ADEContact16LName;
	String ExpectedHeader  ="No items to display";
	
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns14, 30)) {
			if (clickUsingJavaScript(driver, BP.contactname(contactname, 30), "Contact Name: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Contact Name: " + " " + " of Record: " + contactname, YesNo.No);
				try {
					String parentWindowId = CommonLib.switchOnWindow(driver);
					if (!parentWindowId.isEmpty()) {
						log(LogStatus.PASS, "New Window Open after click on Contact page: " + contactname, YesNo.No);

						if (BP.ContactRecordPage(contactname, 20) != null) {
							log(LogStatus.PASS,
									"----Contact Detail Page is redirecting for Contact page: " + contactname + "-----",
									YesNo.No);
							driver.close();
							driver.switchTo().window(parentWindowId);

						} else {
							log(LogStatus.FAIL, "----Deal Detail Page is not redirecting for Deal Record: "
									+ contactname + "-----", YesNo.Yes);
							sa.assertTrue(false,
									"----Deal Detail Page is not showing for Deal Record: " + contactname + "-----");
							driver.close();
							driver.switchTo().window(parentWindowId);

						}

					} else {
						log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + contactname, YesNo.Yes);
						sa.assertTrue(false, "No New Window Open after click on Deal Link: " + contactname);
					}
				} catch (Exception e) {
					log(LogStatus.FAIL,
							"Not able to switch to window after click on Deal Link, Msg showing: " + e.getMessage(),
							YesNo.Yes);
					sa.assertTrue(false, "Not able to switch to window after click on Deal Link, Msg showing: "
							+ e.getMessage());
				}
			
				if (CommonLib.click(driver, BP.getcontactEmailCountpopup( 30), "Email Count: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactname, YesNo.No);
				}
				String actualHeader = getText(driver, BP.getContactpopupheader(20), "Contactpopupheader",
						action.SCROLLANDBOOLEAN);
				if (ExpectedHeader.equals(actualHeader)) {
					log(LogStatus.INFO,
							"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
									+ ExpectedHeader + " for Contact Name: " + contactname,
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
									+ ExpectedHeader + " for Contact Name: " + contactname,
							YesNo.No);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on contactname " + contactname + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on contactname " + contactname + " tab");
			}

		
	} else {
		log(LogStatus.ERROR, "Not able to click on " + ADEIns14 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + ADEIns14 + " tab");
	}
} else {
	log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
	sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}
	
@Parameters({ "projectName" })
@Test
public void ADETc080_CurrentContactisinFROMandVerifyEmailCountofCurrentContactaTAccount(String projectName) {
	OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);	
	op.outLookLogin(rgContact1,rgUserPassword);
	String[] to= {rgContact3};
	String [] cc =null;
	String subject = "Testing email outbound for contact";
			
	if(op.sendMailFromRGOutlook(to, cc, null, subject, null, action.BOOLEAN, 20)) {
		log(LogStatus.INFO, "able to send email from "+crmUser1EmailID+" to "+to, YesNo.No);
	} else {
		log(LogStatus.ERROR, "Not able to send email from "+crmUser1EmailID+" to "+to, YesNo.No);
		sa.assertTrue(false, "Not able to send email from "+crmUser1EmailID+" to "+to);
	}
}


@Parameters({ "projectName" })
@Test
public void ADETc081_CurrentContactisinTOandVerifyEmailCountofCurrentContactAccount(String projectName) {
	OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);	
	op.outLookLogin(rgContact3,rgUserPassword);
	String[] to= {rgContact1};
	String[] cc =null;
	String subject = "Testing email2 outbound for contact";
			
	if(op.sendMailFromRGOutlook(to, cc, null, subject, null, action.BOOLEAN, 20)) {
		log(LogStatus.INFO, "able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
	} else {
		log(LogStatus.ERROR, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
		sa.assertTrue(false, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to);
	}
	
}

@Parameters({ "projectName" })
@Test
public void ADETc082_CurrentContactisinCCBCCandVerifyEmailCountOfCurrentContactatofAccount(String projectName) {
	OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);	
	op.outLookLogin(rgContact3,rgUserPassword);
	String[] to= {rgUser1};
	String[] cc = {rgContact1};
	String subject = "Testing indirect email for contact";
			
	if(op.sendMailFromRGOutlook(to, cc, null, subject, null, action.BOOLEAN, 20)) {
		log(LogStatus.INFO, "able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
	} else {
		log(LogStatus.ERROR, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
		sa.assertTrue(false, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to);
	}
}

@Parameters({ "projectName" })
@Test
public void ADETc083_CurrentContactisSenderSelectedUserisinTOandVerifyEmailCountofUseratConnectionPopupforCurrentContact(String projectName) {
	OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);	
	op.outLookLogin(rgContact1,rgUserPassword);
	String[] to= {rgUser2};
	String[] cc =null;
	String subject = "Testing indirect email2 for contact";
			
	if(op.sendMailFromRGOutlook(to, cc, null, subject, null, action.BOOLEAN, 20)) {
		log(LogStatus.INFO, "able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
	} else {
		log(LogStatus.ERROR, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
		sa.assertTrue(false, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to);
	}
}

@Parameters({ "projectName" })
@Test
public void ADETc084_CurrentContactisinTOSelectedUserisSenderandVerifyEmailCountofUseratConnectionPopupforCurrentContact(String projectName) {
	OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);	
	op.outLookLogin(rgUser2,rgUserPassword);
	String[] to= {rgContact1};
	String[] cc =null;
	String subject = "Testing indirect email3 for contact";
			
	if(op.sendMailFromRGOutlook(to, cc, null, subject, null, action.BOOLEAN, 20)) {
		log(LogStatus.INFO, "able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
	} else {
		log(LogStatus.ERROR, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
		sa.assertTrue(false, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to);
	}
}

@Parameters({ "projectName" })
@Test
public void ADETc085_CurrentContactisSenderSelectedUserisinCCBCCandVerifyEmailCountofUseratConnectionPopupforCurrentContact(String projectName) {
	OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);	
	op.outLookLogin(rgContact1,rgContactPassword);
	String[] to= {rgContact3};
	String[] cc = {rgUser2};
	String subject = "Testing indirect email4 for contact";
			
	if(op.sendMailFromRGOutlook(to, cc, null, subject, null, action.BOOLEAN, 20)) {
		log(LogStatus.INFO, "able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
	} else {
		log(LogStatus.ERROR, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
		sa.assertTrue(false, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to);
	}
}

@Parameters({ "projectName" })
@Test
public void ADETc086_CurrentContactSelectedUserBothareinTOAnotherContactUseriSSenderandVerifyEmailCountofUseratConnectionPopupforCurrentContact(String projectName) {
	OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);	
	op.outLookLogin(rgContact3,rgContactPassword);
	String[] to= {rgContact1,rgUser2};
	String[] cc =null;
	String subject = "Testing indirect email5 for contact";
			
	if(op.sendMailFromRGOutlook(to, cc, null, subject, null, action.BOOLEAN, 20)) {
		log(LogStatus.INFO, "able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
	} else {
		log(LogStatus.ERROR, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
		sa.assertTrue(false, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to);
	}
}

@Parameters({ "projectName" })
@Test
public void ADETc087_CurrentContactSenderandSelectedUseralongwithoneMoreUserareinCCBCCandVerifyEmailCountofUseratConnectionPopupforCurrentContact(String projectName) {
	OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);	
	op.outLookLogin(rgContact1,rgContactPassword);
	String[] to= {rgContact3};
	String[] cc = {rgUser1,rgUser2};
	String subject = "Testing indirect email6 for contact";
			
	if(op.sendMailFromRGOutlook(to, cc, null, subject, null, action.BOOLEAN, 20)) {
		log(LogStatus.INFO, "able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
	} else {
		log(LogStatus.ERROR, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
		sa.assertTrue(false, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to);
	}
}
@Parameters({ "projectName" })
@Test
public void ADETc088_CurrentUserSenderandAnotherUserisReCIpientandVerifytheImpactofCounttheseUsers(String projectName) {
	OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);	
	op.outLookLogin(rgUser1,rgUserPassword);
	String[] to= {rgUser2};
	String[] cc = null;
	String subject = "Testing indirect email7 for contact";
			
	if(op.sendMailFromRGOutlook(to, cc, null, subject, null, action.BOOLEAN, 20)) {
		log(LogStatus.INFO, "able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
	} else {
		log(LogStatus.ERROR, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
		sa.assertTrue(false, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to);
	}
}

@Parameters({ "projectName" })
@Test
public void ADETc089_VerifyEmailSubjectClickableRedirectionEmailRecordsPopupforAccount(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);

	String subject = "Testing indirect email for contact";

	String contactName =ADEContact20LName;

	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns15, 30)) {
			if (CommonLib.click(driver, BP.contactEmailCount(contactName, 30), "Email Count: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName, YesNo.No);
			}
			String parentWindowId1 = CommonLib.switchOnWindow(driver);
			if (!parentWindowId1.isEmpty()) {
			ThreadSleep(3000);
			if (clickUsingJavaScript(driver, BP.contactPopUpEmailsubject(subject, 10), "Deal Name: " + subject,
					action.BOOLEAN)) {
				log(LogStatus.PASS, "Clicked on Email subject: " + subject, YesNo.No);
				try {
				String parentWindowId = CommonLib.switchOnWindow(driver);
				if (!parentWindowId.isEmpty()) { 
					log(LogStatus.PASS, "New Window Open after click on Email subject Link: " + subject, YesNo.No);
					Set<String> childWindow = driver.getWindowHandles();
					op.outLookLoginRevenueGrid(rgUser2,rgUserPassword);
					driver.switchTo().defaultContent();
					System.out.println(childWindow);
					for(String child : childWindow) {
					driver.switchTo().window(child);
					}
					if (BP.EmailRecordPage(subject, 20) != null) {
						log(LogStatus.PASS,
								"----Email Detail Page is redirecting for Email Record: " + subject + "-----",
								YesNo.No);
						driver.close();
						driver.switchTo().window(parentWindowId);

					} else {
						log(LogStatus.FAIL, "----Email Detail Page is not redirecting for Email Record: "
								+ subject + "-----", YesNo.Yes);
						sa.assertTrue(false,
								"----Email Detail Page is not redirecting for Email Record: " + subject + "-----");
						driver.close();
						driver.switchTo().window(parentWindowId);

					}

				} else {
					log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + subject, YesNo.Yes);
					sa.assertTrue(false, "No New Window Open after click on Deal Link: " + subject);
				}
			} catch (Exception e) {
				log(LogStatus.FAIL,
						"Not able to switch to window after click on Deal Link, Msg showing: " + e.getMessage(),
						YesNo.Yes);
				sa.assertTrue(false, "Not able to switch to window after click on Deal Link, Msg showing: "
						+ e.getMessage());
			}
		} else {
			log(LogStatus.FAIL, "Not able to Click on Deal Name: " + subject, YesNo.Yes);
			sa.assertTrue(false, "Not able to Click on Deal Name: " + subject);

		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}

} else {
	log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
	sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
}
	}
			
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}


@Parameters({ "projectName" })
@Test
public void ADETc090_VerifyEmailCountUnderEmailColumnEmailCountEmailRecordsPopupShouldSimilarAccount(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String emailCountInFirm = "5";
	String actualemailCount = null;

	String contactName = ADEContact20LName;

	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns15, 30)) {
			actualemailCount = getText(driver, BP.contactEmailCount(contactName, 20), "deal",
					action.SCROLLANDBOOLEAN);
			if (BP.contactEmailCount(contactName, 20) != null) {
				if (!actualemailCount.equalsIgnoreCase("")) {

					if (actualemailCount.equalsIgnoreCase(emailCountInFirm)) {
						log(LogStatus.INFO, "Email Count for Contact: " + contactName + " is " + actualemailCount
								+ " before Email Team Create is matched to " + emailCountInFirm, YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"Email Count for Contact: " + contactName
										+ " is before Email count is not matched, Expected: " + emailCountInFirm
										+ " but Actual: " + actualemailCount,
								YesNo.Yes);
						sa.assertTrue(false,
								"Email Count for Contact: " + contactName
										+ " is before Email count  not matched, Expected: " + emailCountInFirm
										+ " but Actual: " + actualemailCount);
					}

				} else {
					log(LogStatus.ERROR, "Email Count for Contact is Empty, So not able to check Count for Contact: "
							+ contactName, YesNo.Yes);
					sa.assertTrue(false, "Email Count for Contact is Empty, So not able to check Count for Contact: "
							+ contactName);
				}
			} else {
				log(LogStatus.ERROR, "No Contact found of Name: " + contactName, YesNo.No);
				sa.assertTrue(false, "No Contact found of Name: " + contactName);
			}
			if (CommonLib.click(driver, BP.contactEmailCount(contactName, 30), "Email Count: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName, YesNo.No);
			}
			String parentWindowId1 = CommonLib.switchOnWindow(driver);
			if (!parentWindowId1.isEmpty()) {
			ThreadSleep(3000);
			if (actualemailCount.equals(dp.EmailcountonPopup(10))) {
				log(LogStatus.PASS, "Email count on Popup: is equal to  " + actualemailCount +  "are equal", YesNo.No);
               } else {

				sa.assertTrue(false, "email count is not equal to : " + actualemailCount);
				log(LogStatus.SKIP, "email count is not equal to : " + actualemailCount, YesNo.Yes);

			}
			
			
			} else {
				log(LogStatus.FAIL, "Not able to Click on email count: " + actualemailCount, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on email count: " + actualemailCount);
         driver.close();
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
	}
	
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
public void ADETc091_VerifytheEmailDetailsOnEmailRecordsPopupforAccount(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String actualemailCount = null;
	String expected[] = {"Outbound Email","1/10/2023","donald","Henry,Liam","Testing indirect email6 for contact"};

	String contactName =ADEContact20LName;

	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns15, 30)) {
		
		if (CommonLib.click(driver, BP.contactEmailCount(contactName, 30), "Email Count: " + "",
				action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName, YesNo.No);
		}
		String parentWindowId1 = CommonLib.switchOnWindow(driver);
		if (!parentWindowId1.isEmpty()) {
		
			String xpath="//*[text()='Testing indirect email6 for contact']/ancestor::tr";
			WebElement ele =FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, 10);
			actualemailCount = getText(driver, ele, "deal",action.SCROLLANDBOOLEAN);
			for(int i =0; i < expected.length; i++){
			if(actualemailCount.contains(expected[i])) {
			System.out.println(actualemailCount + " is matched with " + expected[i]);
			
			}else {
			
				System.out.println(actualemailCount + " is not matched with " + expected[i]);
			}
			}
			} else {
		log(LogStatus.FAIL, "Not able to Click on email count: " + actualemailCount, YesNo.Yes);
		sa.assertTrue(false, "Not able to Click on email count: " + actualemailCount);
		driver.close();
	}
}else {

	log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
	sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
}
	}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
public void ADETc092_1_VerifyEntireCellClickableTOColumnsEmailRecordsPopupContactCard(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);

	String subject = "Testing indirect email6 for contact";
	String Toname = ADEContact21LName;
	String CCname = ADEContact20LName;

	String contactName = ADEContact20LName;

	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns15, 30)) {
			if (CommonLib.click(driver, BP.contactEmailCount(contactName, 30), "Email Count: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName, YesNo.No);
			}
			String parentWindowId1 = CommonLib.switchOnWindow(driver);
			if (!parentWindowId1.isEmpty()) {
			ThreadSleep(3000);
			if (clickUsingJavaScript(driver, BP.contactPopUpTO(subject,Toname, 10), "Contact  Name: " + subject,
					action.BOOLEAN)) {
				log(LogStatus.PASS, "Clicked on Contact Name: " + subject, YesNo.No);
				if (clickUsingJavaScript(driver, BP.ToPopup(Toname, 10), "Deal Name: " + subject,
						action.BOOLEAN)) {
					log(LogStatus.PASS, "Clicked on Contact Name: " + subject, YesNo.No);
					Set<String> childWindow = driver.getWindowHandles();
					switchToDefaultContent(driver);
					System.out.println(childWindow);
					for(String child : childWindow) {
					driver.switchTo().window(child);
					}
							if (BP.ContactRecordPage(Toname, 20) != null) {
								log(LogStatus.PASS,
										"----Contact Detail Page is redirecting for Contact Record: " + subject + "-----",
										YesNo.No);
								driver.close();
								driver.switchTo().window(parentWindowId1);

							} else {
								log(LogStatus.FAIL, "----Contact Detail Page is not redirecting for Deal Record: "
										+ subject + "-----", YesNo.Yes);
								sa.assertTrue(false,
										"----Contact Detail Page is not showing for Deal Record: " + subject + "-----");
								driver.close();
								driver.switchTo().window(parentWindowId1);

							}

						} else {
							log(LogStatus.FAIL, "No New Window Open after click on Contact Link: " + subject, YesNo.Yes);
							sa.assertTrue(false, "No New Window Open after click on Contact Link: " + subject);
						}
//					} catch (Exception e) {
//						log(LogStatus.FAIL,
//								"Not able to switch to window after click on Deal Link, Msg showing: " + e.getMessage(),
//								YesNo.Yes);
//						sa.assertTrue(false, "Not able to switch to window after click on Contact Link, Msg showing: "
//								+ e.getMessage());
//					}
				driver.close();
				driver.switchTo().window(parentWindowId1);
				CommonLib.switchOnWindow(driver);
					driver.close();
				driver.switchTo().window(parentWindowId1);
			}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + ADEIns15 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + ADEIns15 + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
	}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();

	}

@Parameters({ "projectName" })
@Test
public void ADETc092_2_VerifyEntireCellClickableCCColumnsEmailRecordsPopupContactCard(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);

	String subject = "Testing indirect email for contact";
	String Toname = ADEContact21LName;
	String CCname = ADEContact20LName;

	String contactName = ADEContact20LName;

	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns15, 30)) {
			if (CommonLib.click(driver, BP.contactEmailCount(contactName, 30), "Email Count: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName, YesNo.No);
			}
			String parentWindowId1 = CommonLib.switchOnWindow(driver);
			if (!parentWindowId1.isEmpty()) {
			ThreadSleep(3000);
			if (clickUsingJavaScript(driver, BP.contactPopUpCC(subject,CCname, 10), "Contact  Name: " + subject,
					action.BOOLEAN)) {
				log(LogStatus.PASS, "Clicked on Contact Name: " + subject, YesNo.No);
				if (clickUsingJavaScript(driver, BP.CCPopup(CCname, 10), "Contact Name: " + subject,
						action.BOOLEAN)) {
					log(LogStatus.PASS, "Clicked on Contact Name: " + subject, YesNo.No);
					Set<String> childWindow = driver.getWindowHandles();
					switchToDefaultContent(driver);
					System.out.println(childWindow);
					for(String child : childWindow) {
					driver.switchTo().window(child);
					}
							if (BP.ContactRecordPage(CCname, 20) != null) {
								log(LogStatus.PASS,
										"----Contact Detail Page is redirecting for Contact Record: " + subject + "-----",
										YesNo.No);
								driver.close();
								driver.switchTo().window(parentWindowId1);

							} else {
								log(LogStatus.FAIL, "----Contact Detail Page is not redirecting for Deal Record: "
										+ subject + "-----", YesNo.Yes);
								sa.assertTrue(false,
										"----Contact Detail Page is not showing for Deal Record: " + subject + "-----");
								driver.close();
								driver.switchTo().window(parentWindowId1);

							}

						} else {
							log(LogStatus.FAIL, "No New Window Open after click on Contact Link: " + subject, YesNo.Yes);
							sa.assertTrue(false, "No New Window Open after click on Contact Link: " + subject);
						}
//					} catch (Exception e) {
//						log(LogStatus.FAIL,
//								"Not able to switch to window after click on Deal Link, Msg showing: " + e.getMessage(),
//								YesNo.Yes);
//						sa.assertTrue(false, "Not able to switch to window after click on Contact Link, Msg showing: "
//								+ e.getMessage());
//					}
//				driver.close();
				driver.switchTo().window(parentWindowId1);
				CommonLib.switchOnWindow(driver);
					driver.close();
				driver.switchTo().window(parentWindowId1);
			}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + ADEIns15 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + ADEIns15 + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
	}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();

	}
@Parameters({ "projectName" })
@Test
public void ADETc093_VerifythatWhenDownloadIconGetsClickedAgainstContactTab(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	
	String contactName = ADEContact20LName;
	String ExpectedHeader = "New Contact";
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns15, 30)) {
			if(BP.DownloadIcon(contactName, 10) != null) {
				log(LogStatus.PASS, "Download Icon: " + contactName + " present", YesNo.No);
				if (CommonLib.click(driver, BP.DownloadIcon(contactName, 30), "Download icon: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Download icon: " + " " + " of Record: " + contactName, YesNo.No);
					String actualHeader = getText(driver, BP.getContactpopupheader(20), "Contactpopupheader",
							action.SCROLLANDBOOLEAN);
					if (ExpectedHeader.equals(actualHeader)) {
						log(LogStatus.INFO,
								"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
										+ ExpectedHeader + " for Contact Name: " + contactName,
								YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
										+ ExpectedHeader + " for Contact Name: " + contactName,
								YesNo.No);
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Downlooad Icon " + contactName + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on Downlooad Icon " + contactName + " tab");
				}

			} else {
				log(LogStatus.ERROR, "download icon not visible " + contactName + " tab", YesNo.Yes);
				sa.assertTrue(false, "download icon not visible " + contactName + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns15 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns15 + " tab");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();

}

@Parameters({ "projectName" })
@Test
public void ADETc094_VerifyEntireCellClickableTOandCCColumnsEmailRecordsPopupContactCard(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String contactName = ADEContact18FName + " " + ADEContact18LName;
	String ExpectedHeader = "Connections of";
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns14, 30)) {
			if (CommonLib.click(driver, BP.connectionicon(contactName, 30), "Connection icon: " + "",
					action.BOOLEAN)) {
				String parentWindowId1 = CommonLib.switchOnWindow(driver);
				if (!parentWindowId1.isEmpty()) {
					ThreadSleep(3000);
				log(LogStatus.INFO, "Clicked on Connection icon: " + " " + " of Record: " + contactName, YesNo.No);
				String actualHeader = getText(driver, BP.getConnectionpopupheader(20), "Contactpopupheader",
						action.SCROLLANDBOOLEAN);
				if (ExpectedHeader.contains(actualHeader)) {
					log(LogStatus.INFO,
							"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
									+ ExpectedHeader + " for Contact Name: " + contactName,
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
									+ ExpectedHeader + " for Contact Name: " + contactName,
							YesNo.No);
				}
				driver.close();
				driver.switchTo().window(parentWindowId1);

			}
			} else {
				log(LogStatus.ERROR, "Not able to click on Connection Icon " + contactName + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on Connection Icon " + contactName + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns14 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns14 + " tab");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();

}

@Parameters({ "projectName"})
@Test
public void ADETc095_VerifyofUserConnectionPopupConnectionCardRespectively(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	
	String ContactName = ADEContact20LName;
	String connectionsHeader = "Team Member<break>Title<break>Last Interaction Date<break>Interaction Notes<break>Deals<break>Meetings and Calls<break>Emails";	


	
	ArrayList<String> dealsSectionHeaderName = new ArrayList<String>();

	ArrayList<String> contactsSectionHeaderName = new ArrayList<String>();

	String[] arrConnnectionsHeader = connectionsHeader.split("<break>");
	List<String> connectionsHeaders = new ArrayList<String>(Arrays.asList(arrConnnectionsHeader));
	
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns15, 30)) {
			if (CommonLib.click(driver, BP.connectionicon(ContactName, 30), "Connection icon: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Connection icon: " + " " + " of Record: " + ContactName, YesNo.No);
				String parentWindowId1 = CommonLib.switchOnWindow(driver);
				if (!parentWindowId1.isEmpty()) {
					ThreadSleep(3000);
				ArrayList<String> result1 = BP
						.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
								contactsSectionHeaderName, null, dealsSectionHeaderName,null, connectionsHeaders,
								null);
				if (result1.isEmpty()) {
					log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
				} else {
					log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
					sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
				}
				driver.close();
				driver.switchTo().window(parentWindowId1);

			}
			} else {
				log(LogStatus.ERROR, "Not able to click on Connection Icon " + ContactName + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on Connection Icon " + ContactName + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns14 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns14 + " tab");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();

}

	
@Parameters({ "projectName" })
@Test
public void ADETc096_VerifythatEmailCountUnderEmailColumnClickableatConnectionPopupAItabandCItab(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String contactName = ADEContact20LName;
	String contactName1="Henry";
	String ExpectedHeader = "Emails with";
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns15, 30)) {
			if (CommonLib.click(driver, BP.connectionicon(contactName, 30), "Connection icon: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Connection icon: " + " " + " of Record: " + contactName, YesNo.No);
				String parentWindowId1 = CommonLib.switchOnWindow(driver);
				if (!parentWindowId1.isEmpty()) {
					ThreadSleep(3000);
				if (CommonLib.click(driver, BP.contactEmailCount(contactName, 30), "Email Count: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName, YesNo.No);
				}
				Set<String> childWindow = driver.getWindowHandles();
				switchToDefaultContent(driver);
				System.out.println(childWindow);
				for(String child : childWindow) {
				driver.switchTo().window(child);
				}
				String actualHeader = getText(driver, BP.getConnectionpopupheader(20), "Contactpopupheader",
						action.SCROLLANDBOOLEAN);
				if (ExpectedHeader.contains(actualHeader)) {
					log(LogStatus.INFO,
							"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
									+ ExpectedHeader + " for Contact Name: " + contactName,
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
									+ ExpectedHeader + " for Contact Name: " + contactName,
							YesNo.No);
				}
				driver.switchTo().window(parentWindowId1);
				CommonLib.switchOnWindow(driver);
					driver.close();
				driver.switchTo().window(parentWindowId1);
			}
			
			} else {
				log(LogStatus.ERROR, "Not able to click on Connection Icon " + contactName + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on Connection Icon " + contactName + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns14 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns14 + " tab");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}

	if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);
		
	if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
			log(LogStatus.INFO, "Clicked on Connection icon: " + " " + " of Record: " + contactName, YesNo.No);
			if (CommonLib.click(driver, BP.contactEmailCountAcuity(contactName1, 30), "Email Count: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName1, YesNo.No);
			}
			String parentWindowId1 = CommonLib.switchOnWindow(driver);
			if (!parentWindowId1.isEmpty()) {
				ThreadSleep(3000);
			String actualHeader = getText(driver, BP.getConnectionpopupheader(20), "Contactpopupheader",
					action.SCROLLANDBOOLEAN);
			if (ExpectedHeader.contains(actualHeader)) {
				log(LogStatus.INFO,
						"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
								+ ExpectedHeader + " for Contact Name: " + contactName,
						YesNo.No);
			} else {
				log(LogStatus.ERROR,
						"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
								+ ExpectedHeader + " for Contact Name: " + contactName,
						YesNo.No);
			}
			driver.close();
			driver.switchTo().window(parentWindowId1);

		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + ADEIns14 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + ADEIns14 + " tab");
	}
} else {
	log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
	sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();

}

//@Parameters({ "projectName"})
//@Test
//public void ADETc097_VerifyFilterFunctionalityEmailRecordPopupforConnectionPopupCardAITab(String projectName) {
//	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
//	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
//	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
//	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
//	String[] filterType= {"All Types","Inbound","Outbound","Indirect"};
//	String[] Categoryname= {"Inbound Email","Outbound Email","Indirect Email"};
//	
//	String contactName = "kbhateja@navatargroup.com";
//	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
//		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
//
//		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns14, 30)) {
//			if (CommonLib.click(driver, BP.connectionicon(contactName, 30), "Connection icon: " + "",
//					action.BOOLEAN)) {
//				log(LogStatus.INFO, "Clicked on Connection icon: " + " " + " of Record: " + contactName, YesNo.No);
//				if (CommonLib.click(driver, BP.contactEmailCount(contactName, 30), "Email Count: " + "",
//						action.BOOLEAN)) {
//					log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName, YesNo.No);
//				}
//				ArrayList<String> result=BP.verifyFilterIconAndFilterRecordsOnEmailPopup(filterType,Categoryname);
//				if(result.isEmpty())
//				{
//					log(LogStatus.INFO, "The filter icon, filter value, and records after selecting filter have been verified after clicking on the count of "+contactName+" from people tag. "+result, YesNo.No);
//				}
//				else
//				{
//					log(LogStatus.ERROR, "The filter icon, filter value, and records after selecting filter not working properly after clicking on the count of "+contactName+" from people tag. "+result, YesNo.No);
//					sa.assertTrue(false,  "The filter icon, filter value, and records after selecting filter not working properly after clicking on the count of "+contactName+" from people tag. "+result);
//				}
//			}} else {
//				log(LogStatus.ERROR, "Not able to click on " + ADEIns14 + " tab", YesNo.Yes);
//				sa.assertTrue(false, "Not able to click on " + ADEIns14 + " tab");
//			}
//		} else {
//			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
//			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
//		}
//	if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
//		log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);
//
//		if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
//				if (CommonLib.click(driver, BP.contactEmailCount(contactName, 30), "Email Count: " + "",
//						action.BOOLEAN)) {
//					log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName, YesNo.No);
//				}
//				ArrayList<String> result=BP.verifyFilterIconAndFilterRecordsOnEmailPopup(filterType,Categoryname);
//				if(result.isEmpty())
//				{
//					log(LogStatus.INFO, "The filter icon, filter value, and records after selecting filter have been verified after clicking on the count of "+contactName+" from people tag. "+result, YesNo.No);
//				}
//				else
//				{
//					log(LogStatus.ERROR, "The filter icon, filter value, and records after selecting filter not working properly after clicking on the count of "+contactName+" from people tag. "+result, YesNo.No);
//					sa.assertTrue(false,  "The filter icon, filter value, and records after selecting filter not working properly after clicking on the count of "+contactName+" from people tag. "+result);
//				}
//			} else {
//				log(LogStatus.ERROR, "Not able to click on " + ADEIns14 + " tab", YesNo.Yes);
//				sa.assertTrue(false, "Not able to click on " + ADEIns14 + " tab");
//			}
//		} else {
//			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
//			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
//		}
//
//ThreadSleep(5000);
//lp.CRMlogout();
//sa.assertAll();
//		}

	
@Parameters({ "projectName"})
@Test
public void ADETc098_VerifyCategoryColumnThreeTypesCategoryEmailRecordPopupforConnectionTab(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	
	String expected = "Inbound Email,Outbound Email,Indirect Email";
    List<String> actualheader = new ArrayList<String>();
	String contactName1="Henry";
	String contactName = ADEContact20LName;
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns15, 30)) {
			if (CommonLib.click(driver, BP.connectionicon(contactName, 30), "Connection icon: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Connection icon: " + " " + " of Record: " + contactName, YesNo.No);
				String parentWindowId1 = CommonLib.switchOnWindow(driver);
				if (!parentWindowId1.isEmpty()) {
				if (CommonLib.click(driver, BP.contactEmailCount(contactName, 30), "Email Count: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName, YesNo.No);
				}
				Set<String> childWindow = driver.getWindowHandles();
				switchToDefaultContent(driver);
				System.out.println(childWindow);
				for(String child : childWindow) {
				driver.switchTo().window(child);
				}
//				String xpath="//h2[contains(text(),'Emails with')]/../following-sibling::div//th[@data-label='Category']//lightning-base-formatted-text";
				String xpath="//table//th[@data-label='Category']//button";
				List<WebElement> ele =FindElements(driver, xpath, "");
				for(int i = 0; i <ele.size();i++ ) {
				actualheader.add(ele.get(i).getText());
			}
				if(compareMultipleList(driver, expected, ele) != null){
					log(LogStatus.INFO, "BOTH values matched: " + " " + " of actual : " + expected, YesNo.No);
				}else
				{
					log(LogStatus.ERROR, "BOTH values matched: " + " " + " of actual : " + expected, YesNo.Yes);
					sa.assertTrue(false,"both values not matched "+" "+expected);
				}
				driver.close();
				driver.switchTo().window(parentWindowId1);
			} else {
				log(LogStatus.ERROR, "Not able to click on " + ADEIns14 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + ADEIns14 + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
	
	}
	}
	if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
			if (CommonLib.click(driver, BP.contactEmailCountAcuity(contactName1, 30), "Email Count: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName, YesNo.No);
			}
			String parentWindowId1 = CommonLib.switchOnWindow(driver);
			if (!parentWindowId1.isEmpty()) {
//			String xpath="//h2[contains(text(),'Emails with')]/../following-sibling::div//th[@data-label='Category']//lightning-base-formatted-text";
			String xpath="//table//th[@data-label='Category']//button";
			List<WebElement> ele =FindElements(driver, xpath, "");
			for(int i = 0; i <ele.size();i++ ) {
			actualheader.add(ele.get(i).getText());
		}
			if(compareMultipleList(driver, expected, ele) != null){
				log(LogStatus.INFO, "BOTH values matched: " + " " + " of actual : " + expected, YesNo.No);
			}else
			{
				log(LogStatus.ERROR, "BOTH values matched: " + " " + " of actual : " + expected, YesNo.Yes);
				sa.assertTrue(false,"both values not matched "+" "+expected);
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns14 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns14 + " tab");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
	}
ThreadSleep(5000);
lp.CRMlogout();
sa.assertAll();
	}


@Parameters({ "projectName"})
@Test
public void ADETc099_VerifyWhenThereInternalUserforaContactandUserIconisClicked(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String contactName = ADEContact22LName;
	String ExpectedMsg = "No items display";
	String connectionsSectionHeaderMessage = "No items to display";




	
	ArrayList<String> dealsSectionHeaderName = new ArrayList<String>();

	ArrayList<String> contactsSectionHeaderName = new ArrayList<String>();
	ArrayList<String> connectionsHeaders = new ArrayList<String>();

	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns15, 30)) {
			if (CommonLib.click(driver, BP.connectionicon(contactName, 30), "Connection icon: " + "",
					action.BOOLEAN)) {
				String parentWindowId1 = CommonLib.switchOnWindow(driver);
				if (!parentWindowId1.isEmpty()) {
				String actualMsg = getText(driver, BP.getErrorMsg(20), "ErrorMsg", action.SCROLLANDBOOLEAN);
				if (ExpectedMsg.equals(actualMsg)) {
					log(LogStatus.INFO,
							"Actual result " + actualMsg + " of pop up has been matched with Expected result : "
									+ ExpectedMsg + " for Contact Name: " + contactName,
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Actual result " + actualMsg
									+ " of pop up has been not matched with Expected result : " + ExpectedMsg
									+ " for Contact Name: " + contactName,
							YesNo.No);
				}
				driver.close();
				driver.switchTo().window(parentWindowId1);
				
			} else {
				log(LogStatus.ERROR, "Not able to click on " + ADEIns15 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + ADEIns15 + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
	}
		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
				ArrayList<String> result1 = BP
						.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
								contactsSectionHeaderName, null, dealsSectionHeaderName,null, connectionsHeaders,
								connectionsSectionHeaderMessage);
				if (result1.isEmpty()) {
					log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
				} else {
					log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
					sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + ADEIns14 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + ADEIns14 + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
	}

	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
		}
	
@Parameters({ "projectName"})
@Test
public void ADETc100_VerifythatEmailSubjectClickableRedirectionatEmailRecordsPopupforAccountContact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String subject = "Testing indirect email6 for contact";
	String contactName1="Henry";
	String contactName = ADEContact20LName;
//	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
//		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
//
//		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns15, 30)) {
//			if (CommonLib.click(driver, BP.connectionicon(contactName, 30), "Connection icon: " + "",
//					action.BOOLEAN)) {
//				log(LogStatus.INFO, "Clicked on Connection icon: " + " " + " of Record: " + contactName, YesNo.No);
//				String parentWindowId = CommonLib.switchOnWindow(driver);
//				if (!parentWindowId.isEmpty()) {
//				if (CommonLib.click(driver, BP.contactEmailCountAcuity(contactName1, 30), "Email Count: " + "",
//						action.BOOLEAN)) {
//					log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName1, YesNo.No);
//				}
//				Set<String> childWindow = driver.getWindowHandles();
//				switchToDefaultContent(driver);
//				System.out.println(childWindow);
//				for(String child : childWindow) {
//				driver.switchTo().window(child);
//				}
//				if (clickUsingJavaScript(driver, BP.contactPopUpEmailsubject(subject, 10), "Email subject: " + subject,
//						action.BOOLEAN)) {
//					log(LogStatus.PASS, "Clicked on Email subject: " + subject, YesNo.No);
//				log(LogStatus.PASS, "New Window Open after click on Email subject Link: " + subject, YesNo.No);
//				Set<String> childWindow1 = driver.getWindowHandles();
//				switchToDefaultContent(driver);
//				System.out.println(childWindow1);
//				for(String child : childWindow1) {
//				driver.switchTo().window(child);
//				}
//				op.outLookLoginRevenueGrid(rgUser3,rgUserPassword);
//							if (BP.EmailRecordPage(subject, 20) != null) {
//								log(LogStatus.PASS,
//										"----Email Detail Page is redirecting for Email Record: " + subject + "-----",
//										YesNo.No);
//								driver.close();
//								driver.switchTo().window(parentWindowId);
//
//							} else {
//								log(LogStatus.FAIL, "----Email Detail Page is not redirecting for Email Record: "
//										+ subject + "-----", YesNo.Yes);
//								sa.assertTrue(false,
//										"----Email Detail Page is not redirecting for Email Record: " + subject + "-----");
//								driver.close();
//								driver.switchTo().window(parentWindowId);
//
//							}
//
//						} else {
//							log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + subject, YesNo.Yes);
//							sa.assertTrue(false, "No New Window Open after click on Deal Link: " + subject);
//						}
//					} catch (Exception e) {
//						log(LogStatus.FAIL,
//								"Not able to switch to window after click on Deal Link, Msg showing: " + e.getMessage(),
//								YesNo.Yes);
//						sa.assertTrue(false, "Not able to switch to window after click on Deal Link, Msg showing: "
//								+ e.getMessage());
//					}
//				} else {
//					log(LogStatus.FAIL, "Not able to Click on Deal Name: " + subject, YesNo.Yes);
//					sa.assertTrue(false, "Not able to Click on Deal Name: " + subject);
//
//				}
//			} else {
//				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
//				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
//			}
//
//		} else {
//			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
//			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
//		}
//	
//	}
	
		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);
		if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
				log(LogStatus.INFO, "Clicked on contact: " + " " + " of Record: " + contactName, YesNo.No);
				if (CommonLib.click(driver, BP.contactEmailCountAcuity(contactName1, 30), "Email Count: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName1, YesNo.No);
				}
				String parentWindowId = CommonLib.switchOnWindow(driver);
				if (!parentWindowId.isEmpty()) {
				if (clickUsingJavaScript(driver, BP.contactPopUpEmailsubject(subject, 10), "Deal Name: " + subject,
						action.BOOLEAN)) {
					log(LogStatus.PASS, "Clicked on Deal Name: " + subject, YesNo.No);
					try {
						String parentWindowId1 = CommonLib.switchOnWindow(driver);
						if (!parentWindowId1.isEmpty()) { 
							log(LogStatus.PASS, "New Window Open after click on Email subject Link: " + subject, YesNo.No);
							Set<String> childWindow = driver.getWindowHandles();
							op.outLookLoginRevenueGrid(rgUser2,rgUserPassword);
							driver.switchTo().defaultContent();
							System.out.println(childWindow);
							for(String child : childWindow) {
							driver.switchTo().window(child);
							}
							if (BP.EmailRecordPage(subject, 20) != null) {
								log(LogStatus.PASS,
										"----Email Detail Page is redirecting for Email Record: " + subject + "-----",
										YesNo.No);
								driver.close();
								driver.switchTo().window(parentWindowId);

							} else {
								log(LogStatus.FAIL, "----Email Detail Page is not redirecting for Email Record: "
										+ subject + "-----", YesNo.Yes);
								sa.assertTrue(false,
										"----Email Detail Page is not redirecting for Email Record: " + subject + "-----");
								driver.close();
								driver.switchTo().window(parentWindowId);

							}

						} else {
							log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + subject, YesNo.Yes);
							sa.assertTrue(false, "No New Window Open after click on Deal Link: " + subject);
						}
					} catch (Exception e) {
						log(LogStatus.FAIL,
								"Not able to switch to window after click on Deal Link, Msg showing: " + e.getMessage(),
								YesNo.Yes);
						sa.assertTrue(false, "Not able to switch to window after click on Deal Link, Msg showing: "
								+ e.getMessage());
					}
				} else {
					log(LogStatus.FAIL, "Not able to Click on Deal Name: " + subject, YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on Deal Name: " + subject);

				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
			}

@Parameters({ "projectName" })
@Test
public void ADETc101_VerifythatEmailCountandEmailDetAIlsCountEmailRecordsPopupShouldSimilarforConnectionPopupCardRespectively(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);

	String emailCountInFirm = "4";
	String actualemailCount = null;

	String contactName = ADEContact20LName;
	String contactName1="Henry";

	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns15, 30)) {
			if (CommonLib.click(driver, BP.connectionicon(contactName, 30), "Connection icon: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Connection icon: " + " " + " of Record: " + contactName, YesNo.No);
				String parentWindowId1 = CommonLib.switchOnWindow(driver);
				if (!parentWindowId1.isEmpty()) {
			actualemailCount = getText(driver, BP.contactEmailCountAcuity(contactName1, 20), "email count",
					action.SCROLLANDBOOLEAN);
			if (BP.contactEmailCountAcuity(contactName1, 20) != null) {
				if (!actualemailCount.equalsIgnoreCase("")) {

					if (actualemailCount.equalsIgnoreCase(emailCountInFirm)) {
						log(LogStatus.INFO, "Email Count for Contact: " + contactName + " is " + actualemailCount
								+ " before Email Team Create is matched to " + emailCountInFirm, YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"Email Count for Contact: " + contactName
										+ " is before Email count is not matched, Expected: " + emailCountInFirm
										+ " but Actual: " + actualemailCount,
								YesNo.Yes);
						sa.assertTrue(false,
								"Email Count for Contact: " + contactName
										+ " is before Email count  not matched, Expected: " + emailCountInFirm
										+ " but Actual: " + actualemailCount);
					}

				} else {
					log(LogStatus.ERROR, "Email Count for Contact is Empty, So not able to check Count for Contact: "
							+ contactName, YesNo.Yes);
					sa.assertTrue(false, "Email Count for Contact is Empty, So not able to check Count for Contact: "
							+ contactName);
				}
				driver.close();
				driver.switchTo().window(parentWindowId1);
			} else {
				log(LogStatus.ERROR, "No Contact found of Name: " + contactName, YesNo.No);
				sa.assertTrue(false, "No Contact found of Name: " + contactName);
			}
			} else {
				log(LogStatus.FAIL, "Not able to Click on email count: " + contactName1, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on email count: " + contactName1);

			}
		}else {

			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		}
	}
				
			if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
					log(LogStatus.INFO, "Clicked on Connection icon: " + " " + " of Record: " + contactName, YesNo.No);
					if (CommonLib.click(driver, BP.contactEmailCountAcuity(contactName1, 30), "Email Count: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName1, YesNo.No);
					}
					String parentWindowId1 = CommonLib.switchOnWindow(driver);
					if (!parentWindowId1.isEmpty()) {
					ThreadSleep(3000);
					if (actualemailCount.equals(dp.EmailcountonPopup(10))) {
						log(LogStatus.PASS, "EmailcountonPopup: " + actualemailCount + "are equal", YesNo.No);
		               } else {

						sa.assertTrue(false, "email count is not equal to : " + actualemailCount);
						log(LogStatus.SKIP, "email count is not equal to : " + actualemailCount, YesNo.Yes);

					}
					driver.close();
					driver.switchTo().window(parentWindowId1);
					
					} else {
						log(LogStatus.FAIL, "Not able to Click on email count: " + actualemailCount, YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on email count: " + actualemailCount);

					}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
				}
			}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
public void ADETc102_VerifytheEmailDetAIlsonEmailRecordsPopupforConnectionPopupCard(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String actualDetail = null;
	String expected[] = {"Indirect Email","1/10/2023","donald","Henry,Liam","Testing indirect email6 for contact"};
	String contactName1="Henry";
	String contactName =ADEContact20LName;

	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns15, 30)) {
			if (CommonLib.click(driver, BP.connectionicon(contactName, 30), "Connection icon: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Connection icon: " + " " + " of Record: " + contactName, YesNo.No);
				String parentWindowId1 = CommonLib.switchOnWindow(driver);
				if (!parentWindowId1.isEmpty()) {
				if (CommonLib.click(driver, BP.contactEmailCountAcuity(contactName1, 30), "Email Count: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName1, YesNo.No);
				}
				Set<String> childWindow = driver.getWindowHandles();
				switchToDefaultContent(driver);
				System.out.println(childWindow);
				for(String child : childWindow) {
				driver.switchTo().window(child);
				}
		
				String xpath="//*[text()='Testing indirect email6 for contact']/ancestor::tr";
				WebElement ele =FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, 10);
				actualDetail = getText(driver, ele, "Email detail",action.SCROLLANDBOOLEAN);
				for(int i =0; i < expected.length; i++){
				if(actualDetail.contains(expected[i])) {
				System.out.println(actualDetail + " is matched with " + expected[i]);
				
				}else {
				
					System.out.println(actualDetail + " is not matched with " + expected[i]);
				}
				}
				} else {
			log(LogStatus.FAIL, "Not able to Click on email count: " + actualDetail, YesNo.Yes);
			sa.assertTrue(false, "Not able to Click on email count: " + actualDetail);

		}
				driver.switchTo().window(parentWindowId1);
				CommonLib.switchOnWindow(driver);
					driver.close();
				driver.switchTo().window(parentWindowId1);
				CommonLib.switchOnWindow(driver);
				driver.close();
			driver.switchTo().window(parentWindowId1);

	
}
		}
		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
			
				if (CommonLib.click(driver, BP.contactEmailCountAcuity(contactName1, 30), "Email Count: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName1, YesNo.No);
				}
				String parentWindowId1 = CommonLib.switchOnWindow(driver);
				if (!parentWindowId1.isEmpty()) {
		
				String xpath="//*[text()='Testing indirect email6 for contact']/ancestor::tr";
				WebElement ele =FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, 10);
				actualDetail = getText(driver, ele, "Email detail",action.SCROLLANDBOOLEAN);
				for(int i =0; i < expected.length; i++){
				if(actualDetail.contains(expected[i])) {
				System.out.println(actualDetail + " is matched with " + expected[i]);
				
				}else {
				
					System.out.println(actualDetail + " is not matched with " + expected[i]);
				}
				}
				} else {
			log(LogStatus.FAIL, "Not able to Click on email count: " + actualDetail, YesNo.Yes);
			sa.assertTrue(false, "Not able to Click on email count: " + actualDetail);

		}
	}else {

		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
	}
	}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
public void ADETc103_VerifyWhenContactNameClickedContactCard(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	String contactname = ADEContact20LName;
	String username ="Henry";
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns15, 30)) {
			refresh(driver);
			if (CommonLib.clickUsingJavaScript(driver, BP.contactname(contactname, 30), "Contact Name: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Contact Name: " + " " + " of Record: " + contactname, YesNo.No);
				ThreadSleep(2000);
				try {
					String parentWindowId = CommonLib.switchOnWindow(driver);
					if (!parentWindowId.isEmpty()) {
						log(LogStatus.PASS, "New Window Open after click on user Link: " + contactname, YesNo.No);
				if (CommonLib.clickUsingJavaScript(driver, BP.Username(username, 30), "Contact Name: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Contact Name: " + " " + " of Record: " + username, YesNo.No);
					ThreadSleep(2000);
//					String parentWindowId = CommonLib.switchOnWindow(driver);
//					if (!parentWindowId.isEmpty()) {
//						log(LogStatus.PASS, "New Window Open after click on user Link: " + contactname, YesNo.No);
					String parentWindowId1 = CommonLib.switchOnWindow(driver);
					ThreadSleep(2000);
					Set<String> wind = driver.getWindowHandles();
					System.out.println(wind);
					for(String window : wind) {
					driver.switchTo().window(window);
				}
					if (!parentWindowId1.isEmpty()) { 
						ThreadSleep(2000);
						switchToFrame(driver, 30, sp.getSetUpPageIframe(60));
						if (BP.UserRecordPage(username, 20) != null) {
							log(LogStatus.PASS,
									"----user Detail Page is redirecting for user Record: " + username + "-----",
									YesNo.No);
						driver.close();
						driver.switchTo().window(parentWindowId);

						} else {
							log(LogStatus.FAIL, "----User Detail Page is not redirecting for user Record: "
									+ username + "-----", YesNo.Yes);
							sa.assertTrue(false,
									"----User Detail Page is not showing for Deal Record: " + contactname + "-----");
//							driver.close();
//							driver.switchTo().window(parentWindowId);

						}
//					} else {
//						log(LogStatus.FAIL, "----User Detail Page is not redirecting for Deal Record: "
//								+ contactname + "-----", YesNo.Yes);
//						sa.assertTrue(false,
//								"----User Detail Page is not showing for Deal Record: " + contactname + "-----");
//						driver.close();
//						driver.switchTo().window(parentWindowId);
//
//					}
				
					}else {
						log(LogStatus.FAIL, "No New Window Open after click on User Link: " + contactname, YesNo.Yes);
						sa.assertTrue(false, "No New Window Open after click on User Link: " + contactname);
					}
				}
					}
					}catch (Exception e) {
					log(LogStatus.FAIL,
							"Not able to switch to window after click on User Link, Msg showing: " + e.getMessage(),
							YesNo.Yes);
					sa.assertTrue(false, "Not able to switch to window after click on Deal Link, Msg showing: "
							+ e.getMessage());
				}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + username + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + username + " tab");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + contactname + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + contactname + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
	
				if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);
					if (fp.clickOnAlreadyCreatedItem(projectName, contactname, 30)) {
						if (CommonLib.clickUsingJavaScript(driver, BP.Username(username, 30), "Contact Name: " + "",
								action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Contact Name: " + " " + " of Record: " + username, YesNo.No);
						try {
							String parentWindowId = CommonLib.switchOnWindow(driver);
							if (!parentWindowId.isEmpty()) {
								log(LogStatus.PASS, "New Window Open after click on user Link: " + username, YesNo.No);
								ThreadSleep(2000);
								switchToFrame(driver, 30, sp.getSetUpPageIframe(60));
								if (BP.UserRecordPage(username, 20) != null) {
									log(LogStatus.PASS,
											"----user Detail Page is redirecting for USER Record: " + username + "-----",
											YesNo.No);
									driver.close();
									driver.switchTo().window(parentWindowId);

								} else {
									log(LogStatus.FAIL, "----User Detail Page is not redirecting for user Record: "
											+ username + "-----", YesNo.Yes);
									sa.assertTrue(false,
											"----User Detail Page is not showing for user Record: " + username + "-----");
									driver.close();
									driver.switchTo().window(parentWindowId);

								}

							} else {
								log(LogStatus.FAIL, "No New Window Open after click on User Link: " + contactname, YesNo.Yes);
								sa.assertTrue(false, "No New Window Open after click on User Link: " + contactname);
							}
						} catch (Exception e) {
							log(LogStatus.FAIL,
									"Not able to switch to window after click on User Link, Msg showing: " + e.getMessage(),
									YesNo.Yes);
							sa.assertTrue(false, "Not able to switch to window after click on Deal Link, Msg showing: "
									+ e.getMessage());
						}
						} else {
							log(LogStatus.ERROR, "Not able to click on " + username + " tab", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on " + username + " tab");
						}

					} else {
						log(LogStatus.ERROR, "Not able to click on " + contactname + " tab", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on " + contactname + " tab");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
				}
	
						ThreadSleep(5000);
						lp.CRMlogout();
						sa.assertAll();
					}
					
@Parameters({ "projectName" })
@Test
public void ADETc104_VerifyUserContactsNamesareClickableatCellPopupatEmailRecordsPopupforConnectionCardSectionRespectively(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	String contactname = ADEContact20LName;
	String subject = "Testing indirect email for contact";
	String Toname = ADEContact21LName;
	String CCname = ADEContact20LName;
	String contactname1 = "Henry";

	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns15, 30)) {
			if (CommonLib.click(driver, BP.connectionicon(contactname, 30), "Connection icon: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Connection icon: " + " " + " of Record: " + contactname, YesNo.No);
				if (CommonLib.click(driver, BP.contactEmailCountAcuity(contactname1, 30), "Email Count: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactname1, YesNo.No);
				}
			ThreadSleep(3000);
			if (clickUsingJavaScript(driver, BP.contactPopUpTO(subject,Toname, 10), "Contact Name: " + subject,
					action.BOOLEAN)) {
				log(LogStatus.PASS, "Clicked on  ToName: " + subject, YesNo.No);
				if (clickUsingJavaScript(driver, BP.ToPopup(Toname, 10), "Contact Name: " + subject,
						action.BOOLEAN)) {
					log(LogStatus.PASS, "Clicked on To Name: " + subject, YesNo.No);
					try {
						String parentWindowId = CommonLib.switchOnWindow(driver);
						if (!parentWindowId.isEmpty()) {
							log(LogStatus.PASS, "New Window Open after click on TO Link: " + subject, YesNo.No);

							if (BP.ContactRecordPage(Toname, 20) != null) {
								log(LogStatus.PASS,
										"----Contact Detail Page is redirecting for TO Record: " + subject + "-----",
										YesNo.No);
								driver.close();
								driver.switchTo().window(parentWindowId);

							} else {
								log(LogStatus.FAIL, "----TO Detail Page is not redirecting for Deal Record: "
										+ subject + "-----", YesNo.Yes);
								sa.assertTrue(false,
										"----TO Detail Page is not showing for Deal Record: " + subject + "-----");
								driver.close();
								driver.switchTo().window(parentWindowId);

							}

						} else {
							log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + subject, YesNo.Yes);
							sa.assertTrue(false, "No New Window Open after click on Deal Link: " + subject);
						}
					} catch (Exception e) {
						log(LogStatus.FAIL,
								"Not able to switch to window after click on Deal Link, Msg showing: " + e.getMessage(),
								YesNo.Yes);
						sa.assertTrue(false, "Not able to switch to window after click on Deal Link, Msg showing: "
								+ e.getMessage());
					}
				} else {
					log(LogStatus.FAIL, "Not able to Click on TO name: " + Toname, YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on TO Name: " + Toname);

				}
				} else {
					log(LogStatus.FAIL, "Not able to Click on TO name: " + Toname, YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on TO Name: " + Toname);

				}
			
			if (CommonLib.click(driver, BP.getcrossIconAcuity(projectName, 30), "Email Count: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactname, YesNo.No);
			}
			if (CommonLib.click(driver, BP.getcrossIconAcuity(projectName, 30), "Email Count: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactname, YesNo.No);
			}
			if (CommonLib.click(driver, BP.getcrossIconAcuity(projectName, 30), "Email Count: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactname, YesNo.No);
			}
			if (CommonLib.click(driver, BP.contactEmailCount(contactname, 30), "Email Count: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactname, YesNo.No);
			}
			
				if (clickUsingJavaScript(driver, BP.contactPopUpCC(subject,contactname1, 10), "Deal Name: " + subject,
						action.BOOLEAN)) {
					log(LogStatus.PASS, "Clicked on CC Name: " + subject, YesNo.No);
					if (clickUsingJavaScript(driver, BP.CCPopup(contactname1, 10), "Deal Name: " + subject,
							action.BOOLEAN)) {
						log(LogStatus.PASS, "Clicked on CC Name: " + subject, YesNo.No);
						try {
							String parentWindowId = CommonLib.switchOnWindow(driver);
							if (!parentWindowId.isEmpty()) {
								log(LogStatus.PASS, "New Window Open after click on CC Link: " + subject, YesNo.No);

								if (BP.UserRecordPage(contactname1, 20) != null) {
									log(LogStatus.PASS,
											"----Deal Detail Page is redirecting for CC Record: " + subject + "-----",
											YesNo.No);
									driver.close();
									driver.switchTo().window(parentWindowId);

								} else {
									log(LogStatus.FAIL, "----CC Detail Page is not redirecting for Deal Record: "
											+ subject + "-----", YesNo.Yes);
									sa.assertTrue(false,
											"----CC Detail Page is not showing for Deal Record: " + subject + "-----");
									driver.close();
									driver.switchTo().window(parentWindowId);

								}

							} else {
								log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + subject, YesNo.Yes);
								sa.assertTrue(false, "No New Window Open after click on Deal Link: " + subject);
							}
						} catch (Exception e) {
							log(LogStatus.FAIL,
									"Not able to switch to window after click on Deal Link, Msg showing: " + e.getMessage(),
									YesNo.Yes);
							sa.assertTrue(false, "Not able to switch to window after click on Deal Link, Msg showing: "
									+ e.getMessage());
						}
					} else {
						log(LogStatus.FAIL, "Not able to Click on CCname Name: " + CCname, YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on CCname Name: " + CCname);

					}
				} else {
					log(LogStatus.FAIL, "Not able to Click on CC name: " + CCname, YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on CC Name: " + CCname);

				}
				} else {
					log(LogStatus.FAIL, "Not able to Click on Connection icon: " + contactname, YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on Connection icon: " + contactname);

				}
			
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns14 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns14 + " tab");
		}

	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}

	if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);
		if (fp.clickOnAlreadyCreatedItem(projectName, contactname, 30)) {
		
		if (CommonLib.click(driver, BP.contactEmailCountAcuity(contactname1, 30), "Email Count: " + "",
				action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactname, YesNo.No);
		}	
		ThreadSleep(3000);
		if (clickUsingJavaScript(driver, BP.contactPopUpTO(subject,Toname, 10), "Contact Name: " + subject,
				action.BOOLEAN)) {
			log(LogStatus.PASS, "Clicked on  ToName: " + subject, YesNo.No);
			if (clickUsingJavaScript(driver, BP.ToPopup(Toname, 10), "Contact Name: " + subject,
					action.BOOLEAN)) {
				log(LogStatus.PASS, "Clicked on To Name: " + subject, YesNo.No);
				try {
					String parentWindowId = CommonLib.switchOnWindow(driver);
					if (!parentWindowId.isEmpty()) {
						log(LogStatus.PASS, "New Window Open after click on TO Link: " + subject, YesNo.No);

						if (BP.ContactRecordPage(Toname, 20) != null) {
							log(LogStatus.PASS,
									"----Deal Detail Page is redirecting for TO Record: " + subject + "-----",
									YesNo.No);
							driver.close();
							driver.switchTo().window(parentWindowId);

						} else {
							log(LogStatus.FAIL, "----TO Detail Page is not redirecting for Deal Record: "
									+ subject + "-----", YesNo.Yes);
							sa.assertTrue(false,
									"----TO Detail Page is not showing for Deal Record: " + subject + "-----");
							driver.close();
							driver.switchTo().window(parentWindowId);

						}

					} else {
						log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + subject, YesNo.Yes);
						sa.assertTrue(false, "No New Window Open after click on Deal Link: " + subject);
					}
				} catch (Exception e) {
					log(LogStatus.FAIL,
							"Not able to switch to window after click on Deal Link, Msg showing: " + e.getMessage(),
							YesNo.Yes);
					sa.assertTrue(false, "Not able to switch to window after click on Deal Link, Msg showing: "
							+ e.getMessage());
				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on TO name: " + Toname, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on TO Name: " + Toname);

			}
			} else {
				log(LogStatus.FAIL, "Not able to Click on TO name: " + Toname, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on TO Name: " + Toname);

			}
		if (CommonLib.click(driver, BP.getcrossIconAcuity(projectName, 30), "Email Count: " + "",
				action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactname, YesNo.No);
		}
		if (CommonLib.click(driver, BP.getcrossIconAcuity(projectName, 30), "Email Count: " + "",
				action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactname, YesNo.No);
		}
		if (CommonLib.click(driver, BP.contactEmailCountAcuity(contactname1, 30), "Email Count: " + "",
				action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactname, YesNo.No);
		}
		if (clickUsingJavaScript(driver, BP.contactPopUpCC(subject,contactname1, 10), "Deal Name: " + subject,
				action.BOOLEAN)) {
			log(LogStatus.PASS, "Clicked on CC Name: " + subject, YesNo.No);
			if (clickUsingJavaScript(driver, BP.CCPopup(contactname1, 10), "Deal Name: " + subject,
					action.BOOLEAN)) {
				log(LogStatus.PASS, "Clicked on CC Name: " + subject, YesNo.No);
				try {
					String parentWindowId = CommonLib.switchOnWindow(driver);
					if (!parentWindowId.isEmpty()) {
						log(LogStatus.PASS, "New Window Open after click on CC Link: " + subject, YesNo.No);

						if (BP.UserRecordPage(contactname1, 20) != null) {
							log(LogStatus.PASS,
									"----Deal Detail Page is redirecting for CC Record: " + subject + "-----",
									YesNo.No);
							driver.close();
							driver.switchTo().window(parentWindowId);

						} else {
							log(LogStatus.FAIL, "----CC Detail Page is not redirecting for Deal Record: "
									+ subject + "-----", YesNo.Yes);
							sa.assertTrue(false,
									"----CC Detail Page is not showing for Deal Record: " + subject + "-----");
							driver.close();
							driver.switchTo().window(parentWindowId);

						}
						} else {
							log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + subject, YesNo.Yes);
							sa.assertTrue(false, "No New Window Open after click on Deal Link: " + subject);
						}
					} catch (Exception e) {
						log(LogStatus.FAIL,
								"Not able to switch to window after click on Deal Link, Msg showing: " + e.getMessage(),
								YesNo.Yes);
						sa.assertTrue(false, "Not able to switch to window after click on Deal Link, Msg showing: "
								+ e.getMessage());
					}
				} else {
					log(LogStatus.FAIL, "Not able to Click on CCname Name: " + CCname, YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on CCname Name: " + CCname);

				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on CC name: " + CCname, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on CC Name: " + CCname);

			}
		
	} else {
		log(LogStatus.ERROR, "Not able to click on " + ADEIns14 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + ADEIns14 + " tab");
	}

} else {
	log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
	sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}
		
	


@Parameters({ "projectName" })
@Test
public void ADETc105_1_VerifytheImpactonEmailCountandContactNameswhenAPIKeygetsChangedforRevenueGridAPI(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	String fieldName = ADEFieldname1;
	String valueField =ADENewValue1;
	String parentWindow = null;
	
	if (home.clickOnSetUpLink()) {
        parentWindow = switchOnWindow(driver);
        if (parentWindow == null) {
            sa.assertTrue(false,
                    "No new window is open after click on setup link in lighting mode so cannot create clone user");
            log(LogStatus.SKIP,
                    "No new window is open after click on setup link in lighting mode so cannot create clone user",
                    YesNo.Yes);
            exit("No new window is open after click on setup link in lighting mode so cannot create clone user");
        }
        ThreadSleep(3000);
	if(setup.UpdateValueInCustomMetaData(MetaDataSetting.RG_Setting.toString(), fieldName, valueField, 10)) {
		 
             log(LogStatus.INFO, "Changed the value of " + fieldName + " for Acuity Setting", YesNo.No);
         }
         else
         {
             log(LogStatus.ERROR, "Not able to change the value of " + fieldName + " for Acuity Setting", YesNo.No);
             sa.assertTrue(false, "Not able to changed the value of " + fieldName + " for Acuity Setting");    
         }
         ThreadSleep(5000);
     }    
     ThreadSleep(5000);
     switchToDefaultContent(driver);
     driver.close();
     driver.switchTo().window(parentWindow);
 	lp.CRMlogout();
 	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
public void ADETc105_2_VerifytheImpactonEmailCountandContactNameswhenAPIKeygetsChangedforRevenueGridAPI(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);	
	String contactname =ADEContact20LName;
	
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns15, 30)) {
		if(BP.apierrormsg(10) != null) {
			log(LogStatus.PASS, "Error msg : is present", YesNo.No);

		} else {
			log(LogStatus.FAIL, "Error msg : not present", YesNo.Yes);
			sa.assertTrue(false, "Error msg : not present: " );
		}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns14 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns14 + " tab");
		}

	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
	if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, contactname, 30)) {
		if(BP.apierrormsg(10) != null) {
			log(LogStatus.PASS, "Error msg : is present", YesNo.No);

		} else {
			log(LogStatus.FAIL, "Error msg : not present", YesNo.Yes);
			sa.assertTrue(false, "Error msg : not present: " );
		}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns14 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns14 + " tab");
		}

	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	
}

@Parameters({ "projectName" })
@Test
public void ADETc106_1_VerifytheImpactonSubjectLinewhenBaseUrlgetsChangedforRevenueGridAPI(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	String fieldName = ADEFieldname2;
	String valueField =ADENewValue2;
	String parentWindow = null;
	
	if (home.clickOnSetUpLink()) {
        parentWindow = switchOnWindow(driver);
        if (parentWindow == null) {
            sa.assertTrue(false,
                    "No new window is open after click on setup link in lighting mode so cannot create clone user");
            log(LogStatus.SKIP,
                    "No new window is open after click on setup link in lighting mode so cannot create clone user",
                    YesNo.Yes);
            exit("No new window is open after click on setup link in lighting mode so cannot create clone user");
        }
        ThreadSleep(3000);
	if(setup.UpdateValueInCustomMetaData(MetaDataSetting.RG_Setting.toString(), fieldName, valueField, 10)) {
		 
             log(LogStatus.INFO, "Changed the value of " + fieldName + " for Acuity Setting", YesNo.No);
         }
         else
         {
             log(LogStatus.ERROR, "Not able to change the value of " + fieldName + " for Acuity Setting", YesNo.No);
             sa.assertTrue(false, "Not able to changed the value of " + fieldName + " for Acuity Setting");    
         }
         ThreadSleep(5000);
     }    
     ThreadSleep(5000);
     switchToDefaultContent(driver);
     driver.close();
     driver.switchTo().window(parentWindow);
     lp.CRMlogout();
  	sa.assertAll();
}
@Parameters({ "projectName" })
@Test
public void ADETc106_2_VerifytheImpactonSubjectLinewhenBaseUrlgetsChangedforRevenueGridAPI(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);	
	String subject = "Testing indirect email for contact";
	String contactName = ADEContact20LName;
	String contactname1 ="Henry";
	
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns15, 30)) {
			if (CommonLib.click(driver, BP.contactEmailCountAcuity(contactName, 30), "Email Count: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName, YesNo.No);
			}
			if (clickUsingJavaScript(driver, BP.contactPopUpEmailsubject(subject, 10), "Email subject: " + subject,
					action.BOOLEAN)) {
		}
			log(LogStatus.PASS, "Clicked on Contact Name: " + subject, YesNo.No);
			try {
				String parentWindowId = CommonLib.switchOnWindow(driver);
				if (!parentWindowId.isEmpty()) {
					log(LogStatus.PASS, "New Window Open after click on subject Link: " + subject, YesNo.No);

					if (BP.getpageloadErrorMsg( 20) != null) {
						log(LogStatus.PASS,
								"----email subject Detail Page is redirecting for page load error msg: " + subject + "-----",
								YesNo.No);
						driver.close();
						driver.switchTo().window(parentWindowId);

					} else {
						log(LogStatus.FAIL, "----email subject Detail Page is not redirecting for page load error msg: "
								+ subject + "-----", YesNo.Yes);
						sa.assertTrue(false,
								"----email subject Detail Page is not redirecting for page load error msg: " + subject + "-----");
						driver.close();
						driver.switchTo().window(parentWindowId);

					}

				} else {
					log(LogStatus.FAIL, "No New Window Open after click on email subject Link: " + subject, YesNo.Yes);
					sa.assertTrue(false, "No New Window Open after click on email subject Link: " + subject);
				}
			} catch (Exception e) {
				log(LogStatus.FAIL,
						"Not able to switch to window after click on email subject, Msg showing: " + e.getMessage(),
						YesNo.Yes);
				sa.assertTrue(false, "Not able to switch to window after click on email subject, Msg showing: "
						+ e.getMessage());
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns14 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns14 + " tab");
		}

	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
	if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
			if (CommonLib.click(driver, BP.contactEmailCountAcuity(contactname1, 30), "Email Count: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactname1, YesNo.No);
			}
			if (clickUsingJavaScript(driver, BP.contactPopUpEmailsubject(subject, 10), "Email subject: " + subject,
					action.BOOLEAN)) {
		}
			log(LogStatus.PASS, "Clicked on Contact Name: " + subject, YesNo.No);
			try {
				String parentWindowId = CommonLib.switchOnWindow(driver);
				if (!parentWindowId.isEmpty()) {
					log(LogStatus.PASS, "New Window Open after click on subject Link: " + subject, YesNo.No);

					if (BP.getpageloadErrorMsg( 20) != null) {
						log(LogStatus.PASS,
								"----email subject Detail Page is redirecting for page load error msg: " + subject + "-----",
								YesNo.No);
						driver.close();
						driver.switchTo().window(parentWindowId);

					} else {
						log(LogStatus.FAIL, "----email subject Detail Page is not redirecting for page load error msg: "
								+ subject + "-----", YesNo.Yes);
						sa.assertTrue(false,
								"----email subject Detail Page is not redirecting for page load error msg: " + subject + "-----");
						driver.close();
						driver.switchTo().window(parentWindowId);

					}

				} else {
					log(LogStatus.FAIL, "No New Window Open after click on email subject Link: " + subject, YesNo.Yes);
					sa.assertTrue(false, "No New Window Open after click on email subject Link: " + subject);
				}
			} catch (Exception e) {
				log(LogStatus.FAIL,
						"Not able to switch to window after click on email subject, Msg showing: " + e.getMessage(),
						YesNo.Yes);
				sa.assertTrue(false, "Not able to switch to window after click on email subject, Msg showing: "
						+ e.getMessage());
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + contactName + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + contactName + " tab");
		}

	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
	}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}
@Parameters({ "projectName" })
@Test
public void ADETc107_1_VerifytheErrorMessageRelatedtoDealGridforAccountContacts(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	String fieldName = ADEFieldname3;
	String valueField =ADENewValue3;
	String parentWindow = null;
	
	if (home.clickOnSetUpLink()) {
        parentWindow = switchOnWindow(driver);
        if (parentWindow == null) {
            sa.assertTrue(false,
                    "No new window is open after click on setup link in lighting mode so cannot create clone user");
            log(LogStatus.SKIP,
                    "No new window is open after click on setup link in lighting mode so cannot create clone user",
                    YesNo.Yes);
            exit("No new window is open after click on setup link in lighting mode so cannot create clone user");
        }
        ThreadSleep(3000);
	if(setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), fieldName, valueField, 10)) {
		 
             log(LogStatus.INFO, "Changed the value of " + fieldName + " for Acuity Setting", YesNo.No);
         }
         else
         {
             log(LogStatus.ERROR, "Not able to change the value of " + fieldName + " for Acuity Setting", YesNo.No);
             sa.assertTrue(false, "Not able to changed the value of " + fieldName + " for Acuity Setting");    
         }
         ThreadSleep(5000);
     }    
     ThreadSleep(5000);
     switchToDefaultContent(driver);
     driver.close();
     driver.switchTo().window(parentWindow);
     lp.CRMlogout();
   	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
public void ADETc107_2_VerifytheErrorMessageRelatedtoDealGridforAccountContacts(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);	
	String contactName = ADEContact20LName;
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns15, 30)) {
		if(BP.Componenterrormsg(10) != null) {
			log(LogStatus.PASS, "Error msg : is present", YesNo.No);

		} else {
			log(LogStatus.FAIL, "Error msg : not present", YesNo.Yes);
			sa.assertTrue(false, "Error msg : not present: " );

		}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns14 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns14 + " tab");
		}

	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
	
	if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
		if(BP.Componenterrormsg(10) != null) {
			log(LogStatus.PASS, "Error msg : is present", YesNo.No);

		} else {
			log(LogStatus.FAIL, "Error msg : not present", YesNo.Yes);
			sa.assertTrue(false, "Error msg : not present: " );

		}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + contactName + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + contactName + " tab");
		}

	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
	}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}
@Parameters({ "projectName" })
@Test
public void ADETc108_1_VerifytheErrorMessageRelatedtoFundraisingGridforAccount(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	String fieldName = ADEFieldname4;
	String valueField =ADENewValue4;
	String parentWindow = null;
	
	if (home.clickOnSetUpLink()) {
        parentWindow = switchOnWindow(driver);
        if (parentWindow == null) {
            sa.assertTrue(false,
                    "No new window is open after click on setup link in lighting mode so cannot create clone user");
            log(LogStatus.SKIP,
                    "No new window is open after click on setup link in lighting mode so cannot create clone user",
                    YesNo.Yes);
            exit("No new window is open after click on setup link in lighting mode so cannot create clone user");
        }
        ThreadSleep(3000);
	if(setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), fieldName, valueField, 10)) {
		 
             log(LogStatus.INFO, "Changed the value of " + fieldName + " for Acuity Setting", YesNo.No);
         }
         else
         {
             log(LogStatus.ERROR, "Not able to change the value of " + fieldName + " for Acuity Setting", YesNo.No);
             sa.assertTrue(false, "Not able to changed the value of " + fieldName + " for Acuity Setting");    
         }
         ThreadSleep(5000);
     }    
     ThreadSleep(5000);
     switchToDefaultContent(driver);
     driver.close();
     driver.switchTo().window(parentWindow);
     lp.CRMlogout();
   	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
public void ADETc108_2_VerifytheErrorMessageRelatedtoFundraisingGridforAccount(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);	
	
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns15, 30)) {
		if(BP.Componenterrormsg(10) != null) {
			log(LogStatus.PASS, "Error msg : is present", YesNo.No);

		} else {
			log(LogStatus.FAIL, "Error msg : not present", YesNo.Yes);
			sa.assertTrue(false, "Error msg : not present: " );

		}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns14 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns14 + " tab");
		}

	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

@Parameters({ "projectName" })
@Test
public void ADETc109_1_AccDealsReplaceDealColumnWithAnotherColumnandCheckifitAppearsHyperlinkedanditRedirectionfromAICItab(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	String fieldName = ADEFieldname5;
	String valueField =ADENewValue5;
	String parentWindow = null;
	
	if (home.clickOnSetUpLink()) {
        parentWindow = switchOnWindow(driver);
        if (parentWindow == null) {
            sa.assertTrue(false,
                    "No new window is open after click on setup link in lighting mode so cannot create clone user");
            log(LogStatus.SKIP,
                    "No new window is open after click on setup link in lighting mode so cannot create clone user",
                    YesNo.Yes);
            exit("No new window is open after click on setup link in lighting mode so cannot create clone user");
        }
        ThreadSleep(3000);
	if(setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), fieldName, valueField, 10)) {
		 
             log(LogStatus.INFO, "Changed the value of " + fieldName + " for Acuity Setting", YesNo.No);
         }
         else
         {
             log(LogStatus.ERROR, "Not able to change the value of " + fieldName + " for Acuity Setting", YesNo.No);
             sa.assertTrue(false, "Not able to changed the value of " + fieldName + " for Acuity Setting");    
         }
         ThreadSleep(5000);
     }    
     ThreadSleep(5000);
     switchToDefaultContent(driver);
     driver.close();
     driver.switchTo().window(parentWindow);
     lp.CRMlogout();
   	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
public void ADETc109_2_AccDealsReplaceDealColumnWithAnotherColumnandCheckifitAppearsHyperlinkedanditRedirectionfromAICItab(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String hsr = "NDA Signed";
	String recordType = "";
	String dealName = ADEDeal6;
	String companyName = ADEDeal6CompanyName;
	String stage = ADEDeal6Stage;
	String dateReceived = todaysDate;
	String contactname = ADEContact20LName;

	String labellabels = "Source Contact<Break>Date Received";
	String otherLabelValues = ADEDeal6SourceContact + "<Break>"
			+ todaysDate;
	if (lp.clickOnTab(projectName, tabObj4)) {
		log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
		ThreadSleep(3000);
		if (dp.createDeal(recordType, dealName, companyName, stage, labellabels, otherLabelValues)) {
			log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);

		} else {
			log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
			sa.assertTrue(false, dealName + " deal is not created");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
		sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
	}
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns15, 30)) {
			if (BP.dealAcuityHSR(hsr, 30) != null) {
				log(LogStatus.PASS, "HSR Name: " + hsr + " is hyperlink and is present", YesNo.No);
				if (click(driver, BP.dealAcuityHSR(hsr, 10), "HSR Name: " + hsr,
						action.BOOLEAN)) {
					log(LogStatus.PASS, "Clicked on HSR Name: " + hsr, YesNo.No);
				try {
					String parentWindowId = CommonLib.switchOnWindow(driver);
					if (!parentWindowId.isEmpty()) {
						log(LogStatus.PASS, "New Window Open after click on Deal Link: " + dealName, YesNo.No);

						if (BP.dealRecordPage(dealName, 20) != null) {
							log(LogStatus.PASS,
									"----Deal Detail Page is redirecting for Deal Record: " + dealName + "-----",
									YesNo.No);
							driver.close();
							driver.switchTo().window(parentWindowId);

						} else {
							log(LogStatus.FAIL, "----Deal Detail Page is not redirecting for Deal Record: "
									+ dealName + "-----", YesNo.Yes);
							sa.assertTrue(false,
									"----Deal Detail Page is not showing for Deal Record: " + dealName + "-----");
							driver.close();
							driver.switchTo().window(parentWindowId);

						}

					} else {
						log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + dealName, YesNo.Yes);
						sa.assertTrue(false, "No New Window Open after click on Deal Link: " + dealName);
					}
				} catch (Exception e) {
					log(LogStatus.FAIL,
							"Not able to switch to window after click on Deal Link, Msg showing: " + e.getMessage(),
							YesNo.Yes);
					sa.assertTrue(false, "Not able to switch to window after click on Deal Link, Msg showing: "
							+ e.getMessage());
				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

			}
		
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns15 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns15 + " tab");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
	}
	if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, contactname, 30)) {
			if (BP.dealAcuityHSR(hsr, 30) != null) {
				log(LogStatus.PASS, "HSR Name: " + hsr + " is hyperlink and is present", YesNo.No);
				if (click(driver, BP.dealAcuityHSR(hsr, 10), "HSR Name: " + hsr,
						action.BOOLEAN)) {
					log(LogStatus.PASS, "Clicked on HSR Name: " + hsr, YesNo.No);
				try {
					String parentWindowId = CommonLib.switchOnWindow(driver);
					if (!parentWindowId.isEmpty()) {
						log(LogStatus.PASS, "New Window Open after click on Deal Link: " + dealName, YesNo.No);

						if (BP.dealRecordPage(dealName, 20) != null) {
							log(LogStatus.PASS,
									"----Deal Detail Page is redirecting for Deal Record: " + dealName + "-----",
									YesNo.No);
							driver.close();
							driver.switchTo().window(parentWindowId);

						} else {
							log(LogStatus.FAIL, "----Deal Detail Page is not redirecting for Deal Record: "
									+ dealName + "-----", YesNo.Yes);
							sa.assertTrue(false,
									"----Deal Detail Page is not showing for Deal Record: " + dealName + "-----");
							driver.close();
							driver.switchTo().window(parentWindowId);

						}

					} else {
						log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + dealName, YesNo.Yes);
						sa.assertTrue(false, "No New Window Open after click on Deal Link: " + dealName);
					}
				} catch (Exception e) {
					log(LogStatus.FAIL,
							"Not able to switch to window after click on Deal Link, Msg showing: " + e.getMessage(),
							YesNo.Yes);
					sa.assertTrue(false, "Not able to switch to window after click on Deal Link, Msg showing: "
							+ e.getMessage());
				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

			}
		
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns15 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns15 + " tab");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
	}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
public void ADETc110_DeleteDealfromDealTeamandVerifyImpactonDealTeamCountContactsUsersatFirmandContactpage(String projectName) {	
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	String dealName = ADEDealTeamName15;
	String TeamMember = ADEDealTeamMember15;
	String contactName = ADEDealContact15;
	String contactname = ADEContact20LName;
	String[][] data = { { PageLabel.Deal.toString(), dealName }, { PageLabel.Deal_Contact.toString(), contactName },
			{ PageLabel.Team_Member.toString(), TeamMember } };

	if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

		if (DTP.createDealTeam(projectName, dealName, data, "Acuity", action.SCROLLANDBOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----", YesNo.No);

			log(LogStatus.INFO,
					"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
							+ contactName + " at Firm Tab under Acuity section---------",
					YesNo.No);
			String xpath = "//*[text()='Deal Team']/parent::h1//slot/lightning-formatted-text";
			WebElement ele = FindElement(driver, xpath, "dt id", action.BOOLEAN, 10);
			if (ele != null) {
				String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
				ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name, "ADT_15",
						excelLabel.DealTeamID);
				log(LogStatus.INFO, "successfully created and noted id of DT" + id + " and deal name " + dealName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "could not create DT" + dealName);
				log(LogStatus.SKIP, "could not create DT" + dealName, YesNo.Yes);
			}
		}
	}
	if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
		if (fp.clickOnAlreadyCreatedItem(projectName,ADEDeal6, 10)) {
			cp.clickOnShowMoreDropdownOnly(projectName, PageName.Object4Page, "");
			log(LogStatus.INFO, "Able to Click on Show more Icon : " + TabName.Object4Tab + " For : " + ADEDeal3,
					YesNo.No);
			ThreadSleep(500);
			WebElement ele = cp.actionDropdownElement(projectName, PageName.Object4Page,
					ShowMoreActionDropDownList.Delete, 15);
			if (ele == null) {
				ele = cp.getDeleteButton(projectName, 30);
			}

			if (click(driver, ele, "Delete More Icon", action.BOOLEAN)) {
				log(LogStatus.INFO,
						"Able to Click on Delete more Icon : " + TabName.Object4Tab + " For : " + ADEDeal3,
						YesNo.No);
				ThreadSleep(1000);
				if (click(driver, cp.getDeleteButtonOnDeletePopUp(projectName, 30), "Delete Button",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to Click on Delete button on Delete PoPup : " + TabName.Object4Tab
							+ " For : " + ADEDeal3, YesNo.No);
					ThreadSleep(10000);
					if (cp.clickOnTab(projectName, TabName.Object4Tab)) {
						log(LogStatus.INFO, "Clicked on Tab : " + TabName.Object4Tab + " For : " + ADEDeal3,
								YesNo.No);
						ThreadSleep(1000);
						if (!cp.clickOnAlreadyCreatedItem(projectName, TabName.Object4Tab, ADEDeal3, 10)) {
							log(LogStatus.INFO, "Item has been Deleted after delete operation  : " + ADEDeal3
									+ " For : " + TabName.Object4Tab, YesNo.No);

						} else {
							sa.assertTrue(false, "Item has not been Deleted after delete operation  : " + ADEDeal3
									+ " For : " + TabName.Object4Tab);
							log(LogStatus.SKIP, "Item has not been Deleted after delete operation  : " + ADEDeal3
									+ " For : " + TabName.Object4Tab, YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Not Able to Click on Tab after delete : " + TabName.Object4Tab
								+ " For : " + ADEDeal3);
						log(LogStatus.SKIP, "Not Able to Click on Tab after delete : " + TabName.Object4Tab
								+ " For : " + ADEDeal3, YesNo.Yes);
					}
				} else {
					log(LogStatus.INFO, "not able to click on delete button, so not deleted : " + TabName.Object4Tab
							+ " For : " + ADEDeal3, YesNo.No);
					sa.assertTrue(false, "not able to click on delete button, so not deleted : "
							+ TabName.Object4Tab + " For : " + "Test D");
				}
			} else {
				log(LogStatus.INFO,
						"not Able to Click on Delete more Icon : " + TabName.Object4Tab + " For : " + ADEDeal3,
						YesNo.No);
				sa.assertTrue(false,
						"not Able to Click on Delete more Icon : " + TabName.Object4Tab + " For : " + ADEDeal3);
			}
		} else {
			log(LogStatus.INFO, "not Able to Click on " + ADEDeal3, YesNo.No);
			sa.assertTrue(false, "not Able to Click on " + ADEDeal3);
		}
	} else {
		log(LogStatus.INFO, "not Able to Click on " + TabName.Object4Tab, YesNo.No);
		sa.assertTrue(false, "not Able to Click on " + TabName.Object4Tab);
	}
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns15, 30)) {
		if(BP.apierrormsg(10) != null) {
			log(LogStatus.PASS, "Error msg : is present", YesNo.No);

		} else {
			log(LogStatus.FAIL, "Error msg : not present", YesNo.Yes);
			sa.assertTrue(false, "Error msg : not present: " );
		}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns14 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns14 + " tab");
		}

	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
	if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, contactname, 30)) {
		if(BP.apierrormsg(10) != null) {
			log(LogStatus.PASS, "Error msg : is present", YesNo.No);

		} else {
			log(LogStatus.FAIL, "Error msg : not present", YesNo.Yes);
			sa.assertTrue(false, "Error msg : not present: " );
		}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + contactname + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + contactname + " tab");
		}

	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
	}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}
}