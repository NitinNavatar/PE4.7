package com.navatar.scripts;


import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;
import static com.navatar.generic.SmokeCommonVariables.crmUser1EmailID;
import static com.navatar.generic.SmokeCommonVariables.rgUserPassword;
import static com.navatar.generic.SmokeCommonVariables.rgOrgPassword;
import static com.navatar.generic.SmokeCommonVariables.rgContact1;
import static com.navatar.generic.SmokeCommonVariables.rgContact3;
import static com.navatar.generic.SmokeCommonVariables.rgUser1;
import static com.navatar.generic.SmokeCommonVariables.rgUser2;
import static com.navatar.generic.SmokeCommonVariables.rgUser3;
import static com.navatar.generic.SmokeCommonVariables.rgContactPassword;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import com.navatar.generic.*;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.Header;
import com.navatar.generic.EnumConstants.InstRecordType;
import com.navatar.generic.EnumConstants.InstitutionPageFieldLabelText;
import com.navatar.generic.EnumConstants.MetaDataSetting;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
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
	public void ADETc001_1_createCRMUser(String projectName) {
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
	public void ADETc001_2_AddListView(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		String[] tabs= {tabObj1,tabObj2};
		TabName[] tab= {TabName.Object1Tab,TabName.Object2Tab};
		int i=0;
		for (TabName t:tab) {

			if (lp.clickOnTab(projectName, t)) {	
				if (lp.createListViewAll(projectName, tabs[i], 10)) {
					log(LogStatus.INFO,"list view added on "+tabs[i],YesNo.No);
				} else {
					log(LogStatus.FAIL,"list view could not added on "+tabs[i],YesNo.Yes);
					sa.assertTrue(false, "list view could not added on "+tabs[i]);
				}
			} else {
				log(LogStatus.FAIL,"could not click on "+tabs[i],YesNo.Yes);
				sa.assertTrue(false, "could not click on "+tabs[i]);
			}
			i++;
			ThreadSleep(5000);
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
		String[][] EntityOrAccounts = {
				{ ADEIns18, ADEIns18RecordType ,null} , { ADEIns2, ADEIns2RecordType ,null},
		 { ADEIns3, ADEIns3RecordType ,null}, { ADEIns19, ADEIns19RecordType ,null}, { ADEIns5, ADEIns5RecordType ,null},
		 { ADEIns7, ADEIns7RecordType ,null},
		 { ADEIns16, ADEIns16RecordType ,null}};
		

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
				String str = getText(driver, bp.verifydefaultCreatedItemOnPageAcuty(Header.Company, TabName.Acuity.toString()), "legal Name Label Text",action.SCROLLANDBOOLEAN);
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
				
				String str = getText(driver, bp.verifydefaultCreatedItemOnPageAcuty(Header.Company, TabName.Acuity.toString()), "legal Name Label Text",action.SCROLLANDBOOLEAN);
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

					if (cp.createContactAcuity(projectName, ADEContact1FName, ADEContact1LName, ADEIns18, ADEContact1EmailID,ADEContact1RecordType, null, null, CreationPage.ContactPage, null, null)) {
						log(LogStatus.INFO,"successfully Created Contact : "+ADEContact1FName+" "+ADEContact1LName,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Contact : "+ADEContact1FName+" "+ADEContact1LName);
						log(LogStatus.SKIP,"Not Able to Create Contact: "+ADEContact1FName+" "+ADEContact1LName,YesNo.Yes);
					}

					String str = getText(driver, bp.verifydefaultCreatedItemOnPageAcuty(Header.Company, TabName.Acuity.toString()), "legal Name Label Text",action.SCROLLANDBOOLEAN);
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
						log(LogStatus.INFO,"successfully Created Contact : "+ADEContact2FName+" "+M3Contact1LName,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Contact : "+ADEContact2FName+" "+ADEContact2LName);
						log(LogStatus.SKIP,"Not Able to Create Contact: "+ADEContact2FName+" "+ADEContact2LName,YesNo.Yes);
					}

					String str = getText(driver, bp.verifydefaultCreatedItemOnPageAcuty(Header.Company,TabName.Acuity.toString()), "legal Name Label Text",action.SCROLLANDBOOLEAN);
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
					
					ADEContact26EmailID=	lp.generateRandomEmailId(gmailUserName);
					ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact26EmailID, "Contact", excelLabel.Variable_Name, "ADEContact26",excelLabel.Contact_EmailId);

					if (cp.createContactAcuity(projectName, ADEContact26FName, ADEContact26LName, ADEIns3, ADEContact26EmailID,ADEContact26RecordType, null, null, CreationPage.ContactPage, null, null)) {
						log(LogStatus.INFO,"successfully Created Contact : "+ADEContact26FName+" "+ADEContact26LName,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Contact : "+ADEContact26FName+" "+ADEContact26LName);
						log(LogStatus.SKIP,"Not Able to Create Contact: "+ADEContact26FName+" "+ADEContact26LName,YesNo.Yes);
					}

					String str = getText(driver, bp.verifydefaultCreatedItemOnPageAcuty(Header.Company, TabName.Acuity.toString()), "legal Name Label Text",action.SCROLLANDBOOLEAN);
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

					if (cp.createContactAcuity(projectName, ADEContact4FName, ADEContact4LName, ADEIns19, ADEContact4EmailID,ADEContact4RecordType, null, null, CreationPage.ContactPage, null, null)) {
						log(LogStatus.INFO,"successfully Created Contact : "+ADEContact4FName+" "+ADEContact4LName,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Contact : "+ADEContact4FName+" "+ADEContact4LName);
						log(LogStatus.SKIP,"Not Able to Create Contact: "+ADEContact4FName+" "+ADEContact4LName,YesNo.Yes);
					}

					String str = getText(driver, bp.verifydefaultCreatedItemOnPageAcuty(Header.Company, TabName.Acuity.toString()), "legal Name Label Text",action.SCROLLANDBOOLEAN);
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
						log(LogStatus.INFO,"successfully Created Contact : "+ADEContact5FName+" "+ADEContact5LName,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Contact : "+ADEContact5FName+" "+ADEContact5LName);
						log(LogStatus.SKIP,"Not Able to Create Contact: "+ADEContact5FName+" "+ADEContact5LName,YesNo.Yes);
					}

					String str = getText(driver, bp.verifydefaultCreatedItemOnPageAcuty(Header.Company,TabName.Acuity.toString()), "legal Name Label Text",action.SCROLLANDBOOLEAN);
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
						log(LogStatus.INFO,"successfully Created Contact : "+ADEContact6FName+" "+ADEContact6LName,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Contact : "+ADEContact6FName+" "+ADEContact6LName);
						log(LogStatus.SKIP,"Not Able to Create Contact: "+ADEContact6FName+" "+ADEContact6LName,YesNo.Yes);
					}

					String str = getText(driver, bp.verifydefaultCreatedItemOnPageAcuty(Header.Company, TabName.Acuity.toString()), "legal Name Label Text",action.SCROLLANDBOOLEAN);
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
					
					ADEContact24EmailID=	lp.generateRandomEmailId(gmailUserName);
					ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact24EmailID, "Contact", excelLabel.Variable_Name, "ADEContact24",excelLabel.Contact_EmailId);

					if (cp.createContactAcuity(projectName, ADEContact24FName, ADEContact24LName, ADEIns16, ADEContact24EmailID,ADEContact24RecordType, null, null, CreationPage.ContactPage, null, null)) {
						log(LogStatus.INFO,"successfully Created Contact : "+ADEContact24FName+" "+ADEContact24FName,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Contact : "+ADEContact24FName+" "+ADEContact24FName);
					log(LogStatus.SKIP,"Not Able to Create Contact: "+ADEContact24FName+" "+ADEContact24FName,YesNo.Yes);
					}

					String str = getText(driver, bp.verifydefaultCreatedItemOnPageAcuty(Header.Company, TabName.Acuity.toString()), "legal Name Label Text",action.SCROLLANDBOOLEAN);
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
					
					ADEContact24EmailID=	lp.generateRandomEmailId(gmailUserName);
					ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact7EmailID, "Contact", excelLabel.Variable_Name, "ADEContact7",excelLabel.Contact_EmailId);

					if (cp.createContactAcuity(projectName, ADEContact7FName, ADEContact7LName, ADEIns7, ADEContact7EmailID,ADEContact7RecordType, null, null, CreationPage.ContactPage, null, null)) {
						log(LogStatus.INFO,"successfully Created Contact : "+ADEContact7FName+" "+ADEContact7FName,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Contact : "+ADEContact7FName+" "+ADEContact7FName);
					log(LogStatus.SKIP,"Not Able to Create Contact: "+ADEContact7FName+" "+ADEContact7FName,YesNo.Yes);
					}

					String str = getText(driver, bp.verifydefaultCreatedItemOnPageAcuty(Header.Company, TabName.Acuity.toString()), "legal Name Label Text",action.SCROLLANDBOOLEAN);
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
		String AccountName = ADEIns18;
		String dealHeader = "Deal Name<break>Stage<break>Highest Stage Reached<break>Date Received";
		String message = bp.ErrorMessageAcuity;

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
								null,contactsSectionHeaderName, null);
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
								null,contactsSectionHeaderName, null);
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
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String recordType = "";
		String dealName = ADEDeal3;
		String companyName = ADEDeal3CompanyName;
		String stage = ADEDeal3Stage;
		String dateReceived = todaysDate;
		String hsr = ADEDeal3Stage;
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

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns18, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns18, YesNo.No);
                  ThreadSleep(3000);
				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
							if (BP.dealAcuityHSRName(dealName, hsr, 30) != null) {
								log(LogStatus.PASS, "HSR: " + hsr + " is present", YesNo.No);
								String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
								
								if (cp.verifyDate(todaysDate,null, actualDate)) {
									log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
								}
								else {
								log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
									sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
								}
							} else {
								log(LogStatus.FAIL, "HSR stage name not present: " + hsr, YesNo.Yes);
								sa.assertTrue(false, "HSR stage name not present: " + hsr);

							}
						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns18);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns18, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}

		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns19, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns19, YesNo.No);

				if (BP.dealAcuityDealName(ADEDeal3, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + "Test D" + " is hyperlink and is present", YesNo.No);
				} else {
					sa.assertTrue(true, "is hyperlink and not present  : " + "Test D" + " For : " + TabName.Object4Tab);
					log(LogStatus.SKIP, "is hyperlink and not present  : " + "Test D" + " For : " + TabName.Object4Tab,
							YesNo.Yes);
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns19);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns19, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
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
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String recordType = "";
		String dealName = ADEDeal4;
		String companyName = ADEDeal4CompanyName;
		String stage = ADEDeal4Stage;
		String hsr = ADEDeal4Stage;
		String dateReceived = todaysDate;
		String ContactName = ADEContact2FName + " " + ADEContact2LName;

		String labellabels = EditPageLabel.Source_Firm +"<Break>"+ excelLabel.Source_Contact +"<Break>"+ PageLabel.Date_Received;
		String otherLabelValues = ADEDeal4SourceFirm + "<Break>" + ADEContact2FName + " " + ADEContact2LName + "<Break>"+ todaysDate;
				
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
							if (BP.dealAcuityHSRName(dealName, hsr, 30) != null) {
								log(LogStatus.PASS, "HSR: " + hsr + " is present", YesNo.No);
								String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
								
								if (cp.verifyDate(todaysDate,null, actualDate)) {
									log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
								}
								else {
								log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
									sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
								}
							} else {
								log(LogStatus.FAIL, "HSR stage name not present: " + hsr, YesNo.Yes);
								sa.assertTrue(false, "HSR stage name not present: " + hsr);

							}
						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

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
							if (BP.dealAcuityHSRName(dealName, hsr, 30) != null) {
								log(LogStatus.PASS, "HSR: " + hsr + " is present", YesNo.No);
								String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
								
								if (cp.verifyDate(todaysDate,null, actualDate)) {
									log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
								}
								else {
								log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
									sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
								}
							} else {
								log(LogStatus.FAIL, "HSR stage name not present: " + hsr, YesNo.Yes);
								sa.assertTrue(false, "HSR stage name not present: " + hsr);

							}
						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

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
							if (BP.dealAcuityHSRName(dealName, hsr, 30) != null) {
								log(LogStatus.PASS, "HSR: " + hsr + " is present", YesNo.No);
								String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
								
								if (cp.verifyDate(todaysDate,null, actualDate)) {
									log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
								}
								else {
								log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
									sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
								}
							} else {
								log(LogStatus.FAIL, "HSR stage name not present: " + hsr, YesNo.Yes);
								sa.assertTrue(false, "HSR stage name not present: " + hsr);

							}
						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}
			} else {

				sa.assertTrue(false, "Not Able to open created source contact : " + ContactName);
				log(LogStatus.SKIP, "Not Able to open created source contact : " + ContactName, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		}
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
		String hsr = ADEDeal5Stage;
		String dateReceived = todaysDate;

		String labellabels = EditPageLabel.Source_Firm +"<Break>"+ PageLabel.Date_Received;
		String otherLabelValues = ADEDeal5SourceFirm + "<Break>"+ todaysDate;

		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			ADEContact8EmailID = lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact8EmailID, "Contact", excelLabel.Variable_Name,
					"ADEContact8", excelLabel.Contact_EmailId);

			if (cp.createContact(projectName, ADEContact8FName, ADEContact8LName, ADEIns18, ADEContact8EmailID,
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

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns18, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns18, YesNo.No);

				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
							if (BP.dealAcuityHSRName(dealName, hsr, 30) != null) {
								log(LogStatus.PASS, "HSR: " + hsr + " is present", YesNo.No);
								String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
								
								if (cp.verifyDate(todaysDate,null, actualDate)) {
									log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
								}
								else {
								log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
									sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
								}
							} else {
								log(LogStatus.FAIL, "HSR stage name not present: " + hsr, YesNo.Yes);
								sa.assertTrue(false, "HSR stage name not present: " + hsr);

							}
						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

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

				} else {
					log(LogStatus.ERROR, dealName[i] + " deal is not created", YesNo.No);
					sa.assertTrue(false, dealName[i] + " deal is not created");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
				sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
			}
		}

		
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
		String[] hsr = ADEDealStage1.split("<Section>", -1);
		String dateReceived = todaysDate;
		String contactname = ADEContact26FName + " " + ADEContact26LName;
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns18, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns18, YesNo.No);
				for (int i = 0; i < 9; i++) {
					if (BP.dealAcuityDealName(dealName[i], 30) != null) {
						log(LogStatus.PASS, "Deal Name: " + dealName[i] + " is hyperlink and is present", YesNo.No);
						if (BP.dealAcuityStageName(dealName[i], dealStage[i], 30) != null) {
							log(LogStatus.PASS, "Stage Name: " + dealStage[i] + " is present", YesNo.No);
								if (BP.dealAcuityHSRName(dealName[i],dealStage[i], 30) != null) {
									log(LogStatus.PASS, "HSR: " + dealStage[i] + " is present", YesNo.No);

								} else {
									log(LogStatus.FAIL, "HSR stage name not present: " + hsr, YesNo.Yes);
									sa.assertTrue(false, "HSR stage name not present: " + hsr);

								}
						} else {
							log(LogStatus.FAIL, "stage name not present: " + dealStage[i], YesNo.Yes);
							sa.assertTrue(false, "stage name not present: " + dealStage[i]);

						}

					
				} else {
					log(LogStatus.FAIL, "Not able to Click on Deal Name not present: " + dealName, YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on Deal Name not present: " + dealName);

				}
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns18);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns18, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns19, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns19, YesNo.No);
				for (int i = 9; i < 18; i++) {
					if (BP.dealAcuityDealName(dealName[i], 30) != null) {
						log(LogStatus.PASS, "Deal Name: " + dealName[i] + " is hyperlink and is present", YesNo.No);
						if (BP.dealAcuityStageName(dealName[i], dealStage[i], 30) != null) {
							log(LogStatus.PASS, "Stage Name: " + dealStage[i] + " is present", YesNo.No);
//							if (BP.dealAcuityDateReceived(dealName[i], dateReceived, 30) != null) {
//								log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
								if (BP.dealAcuityHSRName(dealName[i], dealStage[i], 30) != null) {
									log(LogStatus.PASS, "HSR: " + dealStage[i] + " is present", YesNo.No);

								} else {
									log(LogStatus.FAIL, "HSR stage name not present: " + hsr, YesNo.Yes);
									sa.assertTrue(false, "HSR stage name not present: " + hsr);

								}
//							} else {
//								log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
//								sa.assertTrue(false, "date receivednot present: " + dateReceived);
//							}
						} else {
							log(LogStatus.FAIL, "stage name not present: " + dealStage[i], YesNo.Yes);
							sa.assertTrue(false, "stage name not present: " + dealStage[i]);

						}

					
				} else {
					log(LogStatus.FAIL, "Not able to Click on Deal Name not present: " + dealName, YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on Deal Name not present: " + dealName);

				}
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns19);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns19, YesNo.Yes);

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
						log(LogStatus.PASS, "Deal Name: " + dealName[i] + " is hyperlink and is present", YesNo.No);
						if (BP.dealAcuityStageName(dealName[i], dealStage[i], 30) != null) {
							log(LogStatus.PASS, "Stage Name: " + dealStage[i] + " is present", YesNo.No);
								if (BP.dealAcuityHSRName(dealName[i], dealStage[i], 30) != null) {
									log(LogStatus.PASS, "HSR: " + dealStage[i] + " is present", YesNo.No);

								} else {
									log(LogStatus.FAIL, "HSR stage name not present: " + hsr, YesNo.Yes);
									sa.assertTrue(false, "HSR stage name not present: " + hsr);

								}
							} else {
								log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
								sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

							}
						} else {
							log(LogStatus.FAIL, "stage name not present: " + dealStage, YesNo.Yes);
							sa.assertTrue(false, "stage name not present: " + dealStage);

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
		lp.CRMlogout();
		sa.assertAll();
	}

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
						ThreadSleep(3000);
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

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns18, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns18, YesNo.No);

				if (BP.dealAcuityDealName(ADEDeal3, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + "Test D" + " is hyperlink and is present", YesNo.No);
				} else {
					sa.assertTrue(true, "is hyperlink and not present  : " + "Test D" + " For : " + TabName.Object4Tab);
					log(LogStatus.SKIP, "is hyperlink and not present  : " + "Test D" + " For : " + TabName.Object4Tab,
							YesNo.Yes);
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns18);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns18, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
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
		
		String addRemoveTabName="";
		
		addRemoveTabName="Recycle Bin";
		if (lp.addTab_Lighting( addRemoveTabName, 5)) {
			log(LogStatus.INFO,"Tab added : "+addRemoveTabName,YesNo.No);
		} else {
			log(LogStatus.FAIL,"Tab not added : "+addRemoveTabName,YesNo.No);
			sa.assertTrue(false, "Tab not added : "+addRemoveTabName);
		}	

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

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns18, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns18, YesNo.No);

				if (BP.dealAcuityDealName(ADEDeal3, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + "Test D" + " is hyperlink and is present", YesNo.No);
				} else {
					sa.assertTrue(false,
							"is hyperlink and not present  : " + "Test D" + " For : " + TabName.Object4Tab);
					log(LogStatus.SKIP, "is hyperlink and not present  : " + "Test D" + " For : " + TabName.Object4Tab,
							YesNo.Yes);
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns18);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns18, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
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
			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns18, 10)) {
				cp.clickOnShowMoreDropdownOnly(projectName, PageName.Object1Page, "");
				log(LogStatus.INFO, "Able to Click on Show more Icon : " + TabName.Object1Tab + " For : " + ADEIns18,
						YesNo.No);
				ThreadSleep(500);
				WebElement ele = cp.actionDropdownElement(projectName, PageName.Object1Page,
						ShowMoreActionDropDownList.Delete, 15);
				if (ele == null) {
					ele = cp.getDeleteButton(projectName, 30);
				}

				if (click(driver, ele, "Delete More Icon", action.BOOLEAN)) {
					log(LogStatus.INFO,
							"Able to Click on Delete more Icon : " + TabName.Object1Tab + " For : " + ADEIns18,
							YesNo.No);
					ThreadSleep(1000);
					if (click(driver, cp.getDeleteButtonOnDeletePopUp(projectName, 30), "Delete Button",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Able to Click on Delete button on Delete PoPup : " + TabName.Object1Tab
								+ " For : " + ADEIns1, YesNo.No);
						ThreadSleep(3000);
						if (cp.clickOnTab(projectName, TabName.Object1Tab)) {
							log(LogStatus.INFO, "Clicked on Tab : " + TabName.Object1Tab + " For : " + ADEIns18,
									YesNo.No);
							ThreadSleep(1000);
							if (!cp.clickOnAlreadyCreatedItem(projectName, TabName.Object1Tab, ADEIns1, 10)) {
								log(LogStatus.INFO, "Item has been Deleted after delete operation  : " + ADEIns1
										+ " For : " + TabName.Object1Tab, YesNo.No);

							} else {
								sa.assertTrue(false, "Item has not been Deleted after delete operation  : " + ADEIns18
										+ " For : " + TabName.Object1Tab);
								log(LogStatus.SKIP, "Item has not been Deleted after delete operation  : " + ADEIns18
										+ " For : " + TabName.Object1Tab, YesNo.Yes);
							}

						} else {
							sa.assertTrue(false, "Not Able to Click on Tab after delete : " + TabName.Object1Tab
									+ " For : " + ADEIns1);
							log(LogStatus.SKIP, "Not Able to Click on Tab after delete : " + TabName.Object1Tab
									+ " For : " + ADEIns18, YesNo.Yes);
						}
					} else {
						log(LogStatus.INFO, "not able to click on delete button, so not deleted : " + TabName.Object1Tab
								+ " For : " + ADEIns1, YesNo.No);
						sa.assertTrue(false, "not able to click on delete button, so not deleted : "
								+ TabName.Object1Tab + " For : " + ADEIns18);
					}
				} else {
					log(LogStatus.INFO,
							"not Able to Click on Delete more Icon : " + TabName.Object1Tab + " For : " + ADEIns18,
							YesNo.No);
					sa.assertTrue(false,
							"not Able to Click on Delete more Icon : " + TabName.Object1Tab + " For : " + ADEIns18);
				}
			} else {
				log(LogStatus.INFO, "not Able to Click on " + ADEIns18, YesNo.No);
				sa.assertTrue(false, "not Able to Click on " + ADEIns18);
			}
		} else {
			log(LogStatus.INFO, "not Able to Click on " + TabName.Object1Tab, YesNo.No);
			sa.assertTrue(false, "not Able to Click on " + TabName.Object1Tab);
		}
		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns19, 10)) {
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
								+ TabName.Object1Tab + " For : " + ADEIns19);
					}
				} else {
					log(LogStatus.INFO,
							"not Able to Click on Delete more Icon : " + TabName.Object1Tab + " For : " + ADEIns4,
							YesNo.No);
					sa.assertTrue(false,
							"not Able to Click on Delete more Icon : " + TabName.Object1Tab + " For : " + ADEIns4);
				}
			} else {
				log(LogStatus.INFO, "not Able to Click on " + ADEIns19, YesNo.No);
				sa.assertTrue(false, "not Able to Click on " + ADEIns19);
			}
		} else {
			log(LogStatus.INFO, "not Able to Click on " + TabName.Object1Tab, YesNo.No);
			sa.assertTrue(false, "not Able to Click on " + TabName.Object1Tab);
		}
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
		String name = ADEIns18;

		if (cp.clickOnTab(projectName, tabName)) {
			log(LogStatus.INFO, "Clicked on Tab : " + tabName + " For : " + name, YesNo.No);
			ThreadSleep(1000);
			cp.clickOnAlreadyCreatedItem(projectName, tabName, ADEIns18, 20);
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

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns18, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns18, YesNo.No);

				ArrayList<String> result1 = BP
						.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
								contactsSectionHeaderName, null, dealHeaders, null, connectionsSectionHeaderName, null,contactsSectionHeaderName, null);
				if (result1.isEmpty()) {
					log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
				} else {
					log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
					sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns18);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns18, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}

		String name1 = ADEIns19;

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

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns19, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns19, YesNo.No);

				ArrayList<String> result1 = BP
						.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
								contactsSectionHeaderName, null, dealHeaders, null, connectionsSectionHeaderName, null,contactsSectionHeaderName, null);
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
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETc012_1_EditExistingCompanyDealVerifyimpactDealCompanyAccount(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
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
		String stage = Stage.LOI.toString();
		String hsr = Stage.LOI.toString();
		String dateReceived = todaysDate;

		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns18, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns18, YesNo.No);

				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
							if (BP.dealAcuityHSRName(dealName, hsr, 30) != null) {
								log(LogStatus.PASS, "HSR: " + hsr + " is present", YesNo.No);

								String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();

         

								
								if (cp.verifyDate(todaysDate,null, actualDate)) {
									log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
								}
								else {
								log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
									sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
								}
							} else {
								log(LogStatus.FAIL, "HSR stage name not present: " + hsr, YesNo.Yes);
								sa.assertTrue(false, "HSR stage name not present: " + hsr);

							}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);
					}
						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);
							}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns18);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns18, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		String dealName1 = "ADECTD New";
		String stage1 = Stage.LOI.toString();
		String hsr1 = Stage.LOI.toString();
		String dateReceived1 = todaysDate;
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

				if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns19, 30)) {

					log(LogStatus.INFO, "open created item" + ADEIns19, YesNo.No);

					if (BP.dealAcuityDealName(dealName1, 30) != null) {
						log(LogStatus.PASS, "Deal Name: " + dealName1 + " is hyperlink and is present", YesNo.No);
						if (BP.dealAcuityStageName(dealName1, stage1, 30) != null) {
							log(LogStatus.PASS, "Stage Name: " + stage1 + " is present", YesNo.No);
//							if (BP.dealAcuityDateReceived(dealName1, dateReceived1, 30) != null) {
//								log(LogStatus.PASS, "Date Received: " + dateReceived1 + " is present", YesNo.No);
								if (BP.dealAcuityHSRName(dealName1, hsr1, 30) != null) {
									log(LogStatus.PASS, "HSR: " + hsr1 + " is present", YesNo.No);

								} else {
									log(LogStatus.FAIL, "HSR stage name not present: " + hsr1, YesNo.Yes);
									sa.assertTrue(false, "HSR stage name not present: " + hsr1);

								}
//							} else {
//								log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName1, YesNo.Yes);
//								sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName1);
//
//							}
						} else {
							log(LogStatus.FAIL, "stage name not present: " + stage1, YesNo.Yes);
							sa.assertTrue(false, "stage name not present: " + stage1);

						}

					} else {
						log(LogStatus.FAIL, "date received not present: " + dateReceived1, YesNo.Yes);
						sa.assertTrue(false, "date receivednot present: " + dateReceived1);
					}
				} else {

					sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns19);
					log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns19, YesNo.Yes);

				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			}

		}
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
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
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
		String stage1 =Stage.LOI.toString();
		String hsr1 = Stage.LOI.toString();
		String dateReceived1 = todaysDate;
		String companyname = ADEIns18;
		String labellabels = EditPageLabel.Source_Firm.toString();
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

					if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns18, 30)) {

						log(LogStatus.INFO, "open created item" + ADEIns18, YesNo.No);

						if (BP.dealAcuityDealName(dealName1, 30) != null) {
							log(LogStatus.PASS, "Deal Name: " + dealName1 + " is hyperlink and is present", YesNo.No);
							if (BP.dealAcuityStageName(dealName1, stage1, 30) != null) {
								log(LogStatus.PASS, "Stage Name: " + stage1 + " is present", YesNo.No);
									if (BP.dealAcuityHSRName(dealName1, hsr1, 30) != null) {
										log(LogStatus.PASS, "HSR: " + hsr1 + " is present", YesNo.No);
										String actualDate= BP.dealAcuityDateReceived2(dealName1, 30).getText();
										
										if (cp.verifyDate(todaysDate,null, actualDate)) {
											log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived1 + " is present", YesNo.No);
										}
										else {
										log(LogStatus.ERROR, "Date Received is not matched For : "+dealName1+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
											sa.assertTrue(false,"Date Received is not matched For : "+dealName1+" Actual : "+actualDate+" /t Expected : "+todaysDate );
										}
									} else {
										log(LogStatus.FAIL, "HSR stage name not present: " + hsr1, YesNo.Yes);
										sa.assertTrue(false, "HSR stage name not present: " + hsr1);

									}
								} else {
									log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName1, YesNo.Yes);
									sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName1);

								}

						} else {
							log(LogStatus.FAIL, "date received not present: " + dateReceived1, YesNo.Yes);
							sa.assertTrue(false, "date receivednot present: " + dateReceived1);
						}
					} else {

						sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns18);
						log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns18, YesNo.Yes);

					}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
				}

			}
			if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

				if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns19, 30)) {

					log(LogStatus.INFO, "open created item" + ADEIns19, YesNo.No);

					if (BP.dealAcuityDealName(dealName1, 30) != null) {
						log(LogStatus.PASS, "Deal Name: " + dealName1 + " is hyperlink and is present", YesNo.No);

					} else {
						log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName1, YesNo.Yes);
						sa.assertTrue(true, "Not able to Click on Deal Name: " + dealName1);

					}
				} else {

					sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns19);
					log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns19, YesNo.Yes);

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

					if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns18, 30)) {

						log(LogStatus.INFO, "open created item" + ADEIns18, YesNo.No);

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

				if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns19, 30)) {

					log(LogStatus.INFO, "open created item" + ADEIns19, YesNo.No);

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

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns18, 30)) {
				log(LogStatus.INFO, "open created item" + ADEIns18, YesNo.No);

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

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns18);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns18, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}

		refresh(driver);

		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns19, 30)) {
				log(LogStatus.INFO, "open created item" + ADEIns19, YesNo.No);
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

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns19);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns19, YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
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

		String labellabels = EditPageLabel.Source_Firm +"<Break>"+ excelLabel.Source_Contact +"<Break>"+ PageLabel.Date_Received;
		String otherLabelValues = ADEIns19 + "<Break>" + ADEContact1FName + " " + ADEContact1LName + "<Break>"
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
								ThreadSleep(3000);
								switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
								stage = stage.replace("_", " ");
								ThreadSleep(3000);
								ele = sp.getValuesNewButton(10);
								if (click(driver, ele, "new button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "click on Values New Button", YesNo.No);
									switchToDefaultContent(driver);
									ThreadSleep(5000);
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
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String dealName = ADEDeal11;
		String stage = ADEDeal11Stage;
		String hsr = ADEDeal11Stage;
		String dateReceived = todaysDate;
		String ContactName = ADEContact1FName + " " + ADEContact1LName;

		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns18, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns18, YesNo.No);

				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
							if (BP.dealAcuityHSRName(dealName, hsr, 30) != null) {
								log(LogStatus.PASS, "HSR: " + hsr + " is present", YesNo.No);
								String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
								
								if (cp.verifyDate(todaysDate,null, actualDate)) {
									log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
								}
								else {
								log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
									sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
								}
							} else {
								log(LogStatus.FAIL, "HSR stage name not present: " + hsr, YesNo.Yes);
								sa.assertTrue(false, "HSR stage name not present: " + hsr);

							}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}
				
			} else {
				log(LogStatus.FAIL, "Not able to Click on Deal Name not present: " + dealName, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Deal Name not present: " + dealName);

			}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns18);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns18, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns19, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns19, YesNo.No);

				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
//						if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
//							log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
							if (BP.dealAcuityHSRName(dealName, hsr, 30) != null) {
								log(LogStatus.PASS, "HSR: " + hsr + " is present", YesNo.No);

							} else {
								log(LogStatus.FAIL, "HSR stage name not present: " + hsr, YesNo.Yes);
								sa.assertTrue(false, "HSR stage name not present: " + hsr);

							}
//						} else {
//							log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
//							sa.assertTrue(false, "date receivednot present: " + dateReceived);
//						}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}

				
			} else {
				log(LogStatus.FAIL, "Not able to Click on Deal Name not present: " + dealName, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Deal Name not present: " + dealName);

			}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns19);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns19, YesNo.Yes);

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
//						if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
//							log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
							if (BP.dealAcuityHSRName(dealName, hsr, 30) != null) {
								log(LogStatus.PASS, "HSR: " + hsr + " is present", YesNo.No);

							} else {
								log(LogStatus.FAIL, "HSR stage name not present: " + hsr, YesNo.Yes);
								sa.assertTrue(false, "HSR stage name not present: " + hsr);

							}
//						} else {
//							log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
//							sa.assertTrue(false, "date receivednot present: " + dateReceived);
//						}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}

				
			} else {
				log(LogStatus.FAIL, "Not able to Click on Deal Name not present: " + dealName, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Deal Name not present: " + dealName);

			}
			} else {

				sa.assertTrue(false, "Not Able to open created source contact : " + ContactName);
				log(LogStatus.SKIP, "Not Able to open created source contact : " + ContactName, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		}
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
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
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
		String hsr = ADEDeal12Stage;
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
							if (BP.dealAcuityHSRName(dealName, hsr, 30) != null) {
								log(LogStatus.PASS, "HSR: " + hsr + " is present", YesNo.No);

								String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
		
								if (cp.verifyDate(todaysDate,null, actualDate)) {
									log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
								}
								else {
								log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
									sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
								}
							} else {
								log(LogStatus.FAIL, "HSR stage name not present: " + hsr, YesNo.Yes);
								sa.assertTrue(false, "HSR stage name not present: " + hsr);

							}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}
                 } else {
	         	log(LogStatus.FAIL, "Not able to Click on Deal Name not present: " + dealName, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Deal Name not present: " + dealName);

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
				if (fp.clickOnShowMoreActionDownArrow(projectName, PageName.Object4Page, ShowMoreActionDropDownList.Convert_to_Portfolio, 10)) {
					ThreadSleep(2000);
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
						log(LogStatus.INFO, "successfully verified " + labelName1[i], YesNo.No);

					} else {
						sa.assertTrue(false, "Not Able to verify " + labelName1[i]);
						log(LogStatus.SKIP, "Not Able to verify " + labelName1[i], YesNo.Yes);

					}
					String TabName1 = "";
					String str = getText(driver, bp.verifydefaultCreatedItemOnPageAcuty(Header.Company, TabName.Acuity.toString()),
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
		driver.close();
		driver.switchTo().window(parentID);
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
		String AccountName = ADEIns18;
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
								contactsSectionHeaderName, null, dealHeaders, null, connectionsSectionHeaderName, null,contactsSectionHeaderName, null);
				if (result1.isEmpty()) {
					log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
				} else {
					log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
					sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns18);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns18, YesNo.Yes);

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
								contactsSectionHeaderName, null, dealHeaders, null, connectionsSectionHeaderName, null,contactsSectionHeaderName, null);
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

			if (DTP.createDealTeam(projectName, dealName, data, TabName.Acuity.toString(), action.SCROLLANDBOOLEAN, 25)) {
				log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----", YesNo.No);

				log(LogStatus.INFO,
						"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
								+ contactName + " at Firm Tab under Acuity section---------",
						YesNo.No);
				WebElement ele = DTP.getDealTeamID(10);
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
						driver.close();	driver.switchTo().window(parentID);
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
	public void ADETc022_VerifyDealSectionDealNamelongerforAccountContacts(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String recordType = "";
		String dealName = ADEDeal13;
		String companyName =ADEDeal13CompanyName;
		String stage = ADEDeal13Stage;
		String hsr = ADEDeal13Stage;
		String dateReceived = todaysDate;

		String labellabels = EditPageLabel.Source_Firm +"<Break>"+ excelLabel.Source_Contact +"<Break>"+ PageLabel.Date_Received;
		String otherLabelValues = ADEIns19 + "<Break>" + ADEContact4FName + " " + ADEContact4LName + "<Break>"
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

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns18, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns18, YesNo.No);

				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
							if (BP.dealAcuityHSRName(dealName, hsr, 30) != null) {
								log(LogStatus.PASS, "HSR: " + hsr + " is present", YesNo.No);
								String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
								
								if (cp.verifyDate(todaysDate,null, actualDate)) {
									log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
								}
								else {
								log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
									sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
								}
							} else {
								log(LogStatus.FAIL, "HSR stage name not present: " + hsr, YesNo.Yes);
								sa.assertTrue(false, "HSR stage name not present: " + hsr);

							}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}
				
			} else {
				log(LogStatus.FAIL, "Not able to Click on Deal Name not present: " + dealName, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Deal Name not present: " + dealName);

			}

			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns18);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns18, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns19, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns19, YesNo.No);

				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
							if (BP.dealAcuityHSRName(dealName, hsr, 30) != null) {
								log(LogStatus.PASS, "HSR: " + hsr + " is present", YesNo.No);
								String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
								
								if (cp.verifyDate(todaysDate,null, actualDate)) {
									log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
								}
								else {
								log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
									sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
								}
							} else {
								log(LogStatus.FAIL, "HSR stage name not present: " + hsr, YesNo.Yes);
								sa.assertTrue(false, "HSR stage name not present: " + hsr);

							}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}
				
			} else {
				log(LogStatus.FAIL, "Not able to Click on Deal Name not present: " + dealName, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Deal Name not present: " + dealName);

			}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns19);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns19, YesNo.Yes);

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
							if (BP.dealAcuityHSRName(dealName, hsr, 30) != null) {
								log(LogStatus.PASS, "HSR: " + hsr + " is present", YesNo.No);
								String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
								
								if (cp.verifyDate(todaysDate,null, actualDate)) {
									log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
								}
								else {
								log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
									sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
								}
							} else {
								log(LogStatus.FAIL, "HSR stage name not present: " + hsr, YesNo.Yes);
								sa.assertTrue(false, "HSR stage name not present: " + hsr);

							}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}
				
			} else {
				log(LogStatus.FAIL, "Not able to Click on Deal Name not present: " + dealName, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Deal Name not present: " + dealName);

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
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		String recordType = "";
		String dealName = ADEDeal14;
		String companyName =ADEDeal14CompanyName;
		String stage = ADEDeal14Stage;
		String hsr = ADEDeal14Stage;
		String dateReceived = todaysDate;
		String labellabels = excelLabel.Source_Contact+"<Break>"+ PageLabel.Date_Received;
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

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns18, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns18, YesNo.No);

				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
							if (BP.dealAcuityHSRName(dealName, hsr, 30) != null) {
								log(LogStatus.PASS, "HSR: " + hsr + " is present", YesNo.No);
								String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
								
								if (cp.verifyDate(todaysDate,null, actualDate)) {
									log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
								}
								else {
								log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
									sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
								}
							} else {
								log(LogStatus.FAIL, "HSR stage name not present: " + hsr, YesNo.Yes);
								sa.assertTrue(false, "HSR stage name not present: " + hsr);

							}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}
				
			} else {
				log(LogStatus.FAIL, "Not able to Click on Deal Name not present: " + dealName, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Deal Name not present: " + dealName);

			}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns18);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns18, YesNo.Yes);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns19, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns19, YesNo.No);

				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);

				} else {
					log(LogStatus.FAIL, "Not able to find on Deal Name: " + dealName, YesNo.Yes);
					sa.assertTrue(true, "Not able to find on Deal Name: " + dealName);

				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns19);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns19, YesNo.Yes);

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
							if (BP.dealAcuityHSRName(dealName, hsr, 30) != null) {
								log(LogStatus.PASS, "HSR: " + hsr + " is present", YesNo.No);
								String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
								
								if (cp.verifyDate(todaysDate,null, actualDate)) {
									log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
								}
								else {
								log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
									sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
								}
							} else {
								log(LogStatus.FAIL, "HSR stage name not present: " + hsr, YesNo.Yes);
								sa.assertTrue(false, "HSR stage name not present: " + hsr);

							}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}
						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

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
		String labellabels = PageLabel.Date_Received.toString();
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

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns19, 30)) {
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

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns19);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns19, YesNo.Yes);

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
		String labellabels = PageLabel.Date_Received.toString();
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

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns19, 30)) {
				if (dealName.equals(dp.dealtopname(dealtopcount, 20))) {
					log(LogStatus.PASS, "Dealtopname: " + dealName + "is present", YesNo.No);

		 if(dealName.equals(dp.dealtopname(dealtopcount, 20))) {
    	log(LogStatus.INFO,"successfully found deal at top "+dealName,YesNo.Yes);
	}else {
		sa.assertTrue(false,"deal not present at top "+dealName);
		log(LogStatus.SKIP,"deal not present at top "+dealName,YesNo.Yes);
    }
				} else {

					sa.assertTrue(false, "Not Able to top deal name : " + dealName);
					log(LogStatus.SKIP, "Not Able to top deal name : " + dealName, YesNo.Yes);

				}
			} else {

				sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns19);
				log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns19, YesNo.Yes);

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

			 if(dealName.equals(dp.dealtopname(dealtopcount, 20))) {
	    	log(LogStatus.INFO,"successfully found deal at top "+dealName,YesNo.Yes);
		}else {
			sa.assertTrue(false,"deal not present at top "+dealName);
			log(LogStatus.SKIP,"deal not present at top "+dealName,YesNo.Yes);
	    }
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

		String contactname = ADEContact26FName + " " + ADEContact26LName;
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns18, 30)) {
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

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns19, 30)) {
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
		String message = bp.ErrorMessageAcuity;
		String contactHeader = "Name<break>Title<break>Deals<break>Meetings and Calls<break>Emails";

//		ArrayList<String> connectionsSectionHeaderName = new ArrayList<String>();
//
//		ArrayList<String> DealHeaderName = new ArrayList<String>();

		String[] arrcontactsSectionHeader = contactHeader.split("<break>");
		List<String> contactsSectionHeader = new ArrayList<String>(Arrays.asList(arrcontactsSectionHeader));

		if (cp.clickOnTab(projectName, tabObj1)) {
			log(LogStatus.INFO, "Clicked on Tab : " + tabObj1 + " For : " + ADEIns1, YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)) {
				log(LogStatus.INFO, "Clicked on  : " + ADEIns1 + " For : " + tabObj2, YesNo.No);
				ThreadSleep(2000);
//				ArrayList<String> result1 = bp
//						.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection1(null,
//								contactsSectionHeader, null, DealHeaderName, null, connectionsSectionHeaderName, null,contactsSectionHeader, null);
				ArrayList<String> result1 = bp
						.verifyHeaderNameAndMessageOnContactsSection(contactsSectionHeader, null);
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
//				ArrayList<String> result1 = bp
//						.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection1(null,
//								contactsSectionHeader, null, DealHeaderName, null, connectionsSectionHeaderName,
//								null,contactsSectionHeader, null);
				ArrayList<String> result1 = bp
						.verifyHeaderNameAndMessageOnContactsSection(contactsSectionHeader, null);
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
		String stage = Operator.NDASigned.toString();
		String dateReceived = todaysDate;
		String contactName = ADEContact11FName + " " + ADEContact11LName;
		
		TabName tabName = TabName.Deal_Team;
		String name = ADEDeal3;
		
		String addRemoveTabName="";
		
		addRemoveTabName="Deal Team";
		if (lp.addTab_Lighting( addRemoveTabName, 5)) {
			log(LogStatus.INFO,"Tab added : "+addRemoveTabName,YesNo.No);
		} else {
			log(LogStatus.FAIL,"Tab not added : "+addRemoveTabName,YesNo.No);
			sa.assertTrue(false, "Tab not added : "+addRemoveTabName);
		}

		String[][] data = { { PageLabel.Deal.toString(), dealName },
				{ PageLabel.Deal_Contact.toString(), contactName } };

		if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

			if (DTP.createDealTeam(projectName, dealName, data, TabName.Acuity.toString(), action.SCROLLANDBOOLEAN, 25)) {
				log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----", YesNo.No);

				log(LogStatus.INFO,
						"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
								+ contactName + " at Firm Tab under Acuity section---------",
						YesNo.No);
				WebElement ele = DTP.getDealTeamID(10);
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
		String TeamMember = crmUser1FirstName + " " + crmUser1LastName;
		String contactName = ADEContact11FName + " " + ADEContact11LName;
		
		

		String[][] data = { { PageLabel.Deal.toString(), dealName }, { PageLabel.Deal_Contact.toString(), contactName },
				{ PageLabel.Team_Member.toString(), TeamMember } };

		if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

			if (DTP.createDealTeam(projectName, dealName, data,TabName.Acuity.toString(), action.SCROLLANDBOOLEAN, 25)) {
				log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----", YesNo.No);

				log(LogStatus.INFO,
						"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
								+ contactName + " at Firm Tab under Acuity section---------",
						YesNo.No);
				WebElement ele = DTP.getDealTeamID(10);
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

				if (DTP.createDealTeam(projectName, dealName, data,TabName.Acuity.toString(), action.SCROLLANDBOOLEAN, 25)) {
					log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----",
							YesNo.No);

					log(LogStatus.INFO,
							"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
									+ contactName + " at Firm Tab under Acuity section---------",
							YesNo.No);
					WebElement ele = DTP.getDealTeamID(10);
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

				if (DTP.createDealTeam(projectName, dealName1, data, TabName.Acuity.toString(), action.SCROLLANDBOOLEAN, 25)) {
					log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----",
							YesNo.No);

					log(LogStatus.INFO,
							"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
									+ contactName + " at Firm Tab under Acuity section---------",
							YesNo.No);
					WebElement ele = DTP.getDealTeamID(10);
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
		String ExpectedMsg = BP.ErrorMessageAcuity;

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

		String ExpectedHeader = "Deals with "+ADEContact13FName + " " + ADEContact13LName;

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
					if (actualHeader1.equals(ExpectedHeader)) {
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
				
				ThreadSleep(2000);
				if (clickUsingJavaScript(driver, BP.dealAcuityPopUpDealName(dealName, 10), "Deal Name: " + dealName,
						action.BOOLEAN)) {
					log(LogStatus.PASS, "New Window Open after click on Deal Link: " + dealName, YesNo.No);
					ThreadSleep(3000);
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
				
				if (clickUsingJavaScript(driver, BP.dealAcuityPopUpCompanyName(dealName, companyname, 20), "Deal Name: " + dealName,
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
						driver.switchTo().window(parentWindowId);
						CommonLib.switchOnWindow(driver);
							driver.close();
						driver.switchTo().window(parentWindowId);

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
	String[] splitedUserLastName = removeNumbersFromString(crmUser2LastName);
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
				if (setup.createPEUser( crmUser2FirstName, UserLastName, emailId, crmUserLience,
						crmUserProfile,null)) {
					log(LogStatus.INFO, "CRM User is created Successfully: " + crmUser2FirstName + " " + UserLastName, YesNo.No);
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
	String TeamMember = crmUser2FirstName + " " + crmUser2LastName;
	String contactName = ADEContact1FName + " " + ADEContact1LName;

	String[][] data = { { PageLabel.Deal.toString(), dealName }, { PageLabel.Deal_Contact.toString(), contactName },
			{ PageLabel.Team_Member.toString(), TeamMember } };

	if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

		if (DTP.createDealTeam(projectName, dealName, data, TabName.Acuity.toString(), action.SCROLLANDBOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----", YesNo.No);

			log(LogStatus.INFO,
					"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
							+ contactName + " at Firm Tab under Acuity section---------",
					YesNo.No);
			WebElement ele = DTP.getDealTeamID(10);
					if (ele!=null) {
						String id=getText(driver, ele, "deal team id",action.SCROLLANDBOOLEAN);
						ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name,
								"ADT_05", excelLabel.DealTeamID);
						log(LogStatus.INFO,"successfully created and noted id of DT"+id+" and deal name "+dealName,YesNo.No);	
					} else {
						sa.assertTrue(false,"could not create DT"+dealName);
						log(LogStatus.SKIP,"could not create DT"+dealName,YesNo.Yes);
					}
					} else {
						sa.assertTrue(false,"could not able to click on tab DT"+TabName.Deal_Team);
						log(LogStatus.SKIP,"could not create DT"+TabName.Deal_Team,YesNo.Yes);
					}
	}
	
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
						String TeamMember1 = crmUser1FirstName + " " + crmUser1LastName;
						String contactName1= ADEContact3FName + " " + ADEContact3LName;

						String[][] data1 = { { PageLabel.Deal.toString(), dealName1 }, { PageLabel.Deal_Contact.toString(), contactName1 },
								{ PageLabel.Team_Member.toString(), TeamMember1 } };
						
						if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
							log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

							if (DTP.createDealTeam(projectName, dealName1, data1,TabName.Acuity.toString(), action.SCROLLANDBOOLEAN, 25)) {
								log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName1 + "----", YesNo.No);

								log(LogStatus.INFO,
										"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
												+ contactName1 + " at Firm Tab under Acuity section---------",
										YesNo.No);
								WebElement ele1 = DTP.getDealTeamID(10);
								if (ele1!=null) {
											String id=getText(driver, ele1, "deal team id",action.SCROLLANDBOOLEAN);
											ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name,
													"ADT_06", excelLabel.DealTeamID);
											log(LogStatus.INFO,"successfully created and noted id of DT"+id+" and deal name "+dealName,YesNo.No);	
										} else {
											sa.assertTrue(false,"could not create DT"+dealName);
											log(LogStatus.SKIP,"could not create DT"+dealName,YesNo.Yes);
										}
							} else {
								sa.assertTrue(false,"could not able to click on tab DT"+TabName.Deal_Team);
								log(LogStatus.SKIP,"could not create DT"+TabName.Deal_Team,YesNo.Yes);
							}
			}
									
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
	String connectionsHeader = "Name<break>Title<break>Deals<break>Meetings and Calls<break>Emails";	


	
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
							null,contactsSectionHeaderName, null);
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
	String connectionsHeader = "Name<break>Title<break>Deals<break>Meetings and Calls<break>Emails";	

	String message = bp.ErrorMessageAcuity;

	
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
								message,contactsSectionHeaderName,null);
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

			if (DTP.createDealTeam(projectName, dealName1, data, TabName.Acuity.toString(), action.SCROLLANDBOOLEAN, 25)) {
				log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----",
						YesNo.No);

				log(LogStatus.INFO,
						"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
								+ contactName + " at Firm Tab under Acuity section---------",
						YesNo.No);
				WebElement ele = DTP.getDealTeamID(10);
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
										message,contactsSectionHeaderName, null);
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
	String TeamMember= crmUser2FirstName + " " + crmUser2LastName;
	String DealCountInFirm = "1";

	String recordType = "";
	String dealName = ADEDeal17;
	String companyName = ADEDeal17CompanyName;
	String stage = ADEDeal17Stage;
	String actualDealCount = null;
	String teamMemberName = crmUser2FirstName + " " + crmUser2LastName;
	
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

		if (DTP.createDealTeam(projectName, dealName1, data,TabName.Acuity.toString(), action.SCROLLANDBOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----",
					YesNo.No);

			log(LogStatus.INFO,
					"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
							+ contactName + " at Firm Tab under Acuity section---------",
					YesNo.No);
			WebElement ele = DTP.getDealTeamID(10);
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
	String TeamMember= crmUser2FirstName + " " + crmUser2LastName;
	String DealCountInFirm = "2";

	String recordType = "";
	String dealName = ADEDeal18;
	String companyName = ADEDeal18CompanyName;
	String stage = ADEDeal18Stage;
	String actualDealCount = null;
	String teamMemberName = crmUser2FirstName + " " + crmUser2LastName;
	
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

		if (DTP.createDealTeam(projectName, dealName1, data, TabName.Acuity.toString(), action.SCROLLANDBOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----",
					YesNo.No);

			log(LogStatus.INFO,
					"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
							+ contactName + " at Firm Tab under Acuity section---------",
					YesNo.No);
			WebElement ele = DTP.getDealTeamID(10);
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

		if (DTP.createDealTeam(projectName, dealName1, data,TabName.Acuity.toString(), action.SCROLLANDBOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----",
					YesNo.No);

			log(LogStatus.INFO,
					"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
							+ contactName + " at Firm Tab under Acuity section---------",
					YesNo.No);
			WebElement ele = DTP.getDealTeamID(10);
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
				      }
                    }
			      }
                 }
               }
		lp.CRMlogout();
		sa.assertAll();
}
@Parameters({ "projectName"})
@Test
public void ADETc048_EditRemovedealContactfromDealTeamandVerifyImpactConnectionRespectively(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String teamMemberName = crmUser2FirstName + " " + crmUser2LastName;
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
	                    }
				      }
	                 }
	               }
			lp.CRMlogout();
			sa.assertAll();
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
	
	String teamMemberName1 = crmUser2FirstName + " " + crmUser2LastName;
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
										driver.close();
										driver.switchTo().window(parentWindowId);
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
	
	String teamMemberName = crmUser2FirstName + " " + crmUser2LastName;
	String contactName = ADEContact15FName + " " + ADEContact15LName;
	String dealName = ADEDeal18;
	String companyName = ADEDeal18CompanyName;
	String stage = ADEDeal18Stage;
	String hsr = ADEDeal18Stage;
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
							log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
							sa.assertTrue(false, "date receivednot present: " + dateReceived);

						}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}
					} else {
						log(LogStatus.FAIL, "Not able to Click on Company Name: " + companyName, YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on Company Name: " + companyName);

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
							if (BP.dealAcuityHSRName(dealName, hsr, 30) != null) {
								log(LogStatus.PASS, "HSR: " + hsr + " is present", YesNo.No);

							} else {
								log(LogStatus.FAIL, "HSR stage name not present: " + hsr, YesNo.Yes);
								sa.assertTrue(false, "HSR stage name not present: " + hsr);

							}
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
	String teamMemberName = crmUser2FirstName + " " + crmUser2LastName;
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
	String message = BP.ErrorMessageAcuity;

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
					ThreadSleep(3000);
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
								+ " For : " + "ADEFundraisingName");
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
				if (CommonLib.click(driver, BP.getNewFinancingPopupCrossIcon( 30), "New Fianacing Cross Icon: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on New Fianacing Cross Icon", YesNo.No);
				} else {
					log(LogStatus.ERROR, "Not able to click on New Fianacing Cross Icon", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on New Fianacing Cross Icon" + " tab");
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
	driver.close();	
	driver.switchTo().window(parentID);
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
public void ADETc062_3_EditFieldColumnNamesVerifyImpactFundraisingCardInstitutionAccount(String projectName) {
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
public void ADETc063_1_EditFieldColumnNamesVerifyImpactFundraisingCardInstitutionAccount(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String recordType = excelLabel.Advisor.toString();
	
	
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
	String recordType = excelLabel.Institution.toString();
	
	
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
								ThreadSleep(3000);
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
		driver.switchTo().window(parentID);
		lp.CRMlogout();
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
			} else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		} else {
			log(LogStatus.FAIL, "setup link is not clickable", YesNo.Yes);
			sa.assertTrue(false, "setup link is not clickable");
		}
		driver.close();	
		driver.switchTo().window(parentID);
		lp.CRMlogout();
		
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

//	ArrayList<String> connectionsSectionHeaderName = new ArrayList<String>();
//
//	ArrayList<String> DealHeaderName = new ArrayList<String>();

	String[] arrcontactsSectionHeader = contactHeader.split("<break>");
	List<String> contactsSectionHeader = new ArrayList<String>(Arrays.asList(arrcontactsSectionHeader));

	if (cp.clickOnTab(projectName, tabObj1)) {
		log(LogStatus.INFO, "Clicked on Tab : " + tabObj1 + " For : " + AccountName, YesNo.No);
		ThreadSleep(1000);
		if (cp.clickOnAlreadyCreatedItem(projectName, AccountName, 30)) {
			log(LogStatus.INFO, "Clicked on  : " + AccountName + " For : " + tabObj2, YesNo.No);
			ThreadSleep(2000);
//			ArrayList<String> result1 = bp
//					.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection1(null,
//							contactsSectionHeader, null, DealHeaderName, null, connectionsSectionHeaderName, null,contactsSectionHeader, null);
			ArrayList<String> result1 = bp
					.verifyHeaderNameAndMessageOnContactsSection(contactsSectionHeader, null);
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

		if (DTP.createDealTeam(projectName, dealName, data, TabName.Acuity.toString(), action.SCROLLANDBOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----", YesNo.No);

			log(LogStatus.INFO,
					"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
							+ contactName + " at Firm Tab under Acuity section---------",
					YesNo.No);
			WebElement ele = DTP.getDealTeamID(10);
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
	String TeamMember = crmUser1FirstName + " " + crmUser1LastName;
	String contactName = ADEContact16FName + " " + ADEContact16LName;

	String[][] data = { { PageLabel.Deal.toString(), dealName }, { PageLabel.Deal_Contact.toString(), contactName },
			{ PageLabel.Team_Member.toString(), TeamMember } };

	if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

		if (DTP.createDealTeam(projectName, dealName, data, TabName.Acuity.toString(), action.SCROLLANDBOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----", YesNo.No);

			log(LogStatus.INFO,
					"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
							+ contactName + " at Firm Tab under Acuity section---------",
					YesNo.No);
			WebElement ele = DTP.getDealTeamID(10);
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

			if (DTP.createDealTeam(projectName, dealName, data, TabName.Acuity.toString(), action.SCROLLANDBOOLEAN, 25)) {
				log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----",
						YesNo.No);

				log(LogStatus.INFO,
						"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
								+ contactName + " at Firm Tab under Acuity section---------",
						YesNo.No);
				WebElement ele = DTP.getDealTeamID(10);
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

			if (DTP.createDealTeam(projectName, dealName1, data, TabName.Acuity.toString(), action.SCROLLANDBOOLEAN, 25)) {
				log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----",
						YesNo.No);

				log(LogStatus.INFO,
						"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
								+ contactName + " at Firm Tab under Acuity section---------",
						YesNo.No);
				WebElement ele = DTP.getDealTeamID(10);
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
			sa.assertAll();
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
	String DealCountInFirm = "2";
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
	String ExpectedHeader  ="No items to display.";
	
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
                          ThreadSleep(3000);
						if (BP.ContactRecordPage(contactname, 30) != null) {
							log(LogStatus.PASS,
									"----Contact Detail Page is redirecting for Contact page: " + contactname + "-----",
									YesNo.No);
							driver.close();
							driver.switchTo().window(parentWindowId);

						} else {
							log(LogStatus.FAIL, "----Contact Detail Page is not redirecting for Deal Record: "
									+ contactname + "-----", YesNo.Yes);
							sa.assertTrue(false,
									"----Contact Detail Page is not showing for Deal Record: " + contactname + "-----");
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
public void ADETc079_1_RenameStageNamesCheckImpactDealSectionAccountContact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
	String parentID = null;
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	String[][] newAndOldStage = { {Stage.DD.toString(),Stage.Due_Diligence.toString() },
			{  Stage.IOL.toString(),Stage.IOI.toString() } };
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

//Advisor record type

@Parameters({ "projectName" })
@Test
public void ADETc080_VerifyDealGridWhenAdvisorFirmContactGetsAddedIntoaDealTeamasaDealContact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

	String recordType = "";
	String dealName = ADEDeal21;
	String companyName = ADEDeal21CompanyName;
	String stage = ADEDeal21Stage;
	String dateReceived = todaysDate;
	String contactName = ADEContact2FName + " " + ADEContact2LName;

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
	

	String[][] data = { { PageLabel.Deal.toString(), dealName }, { PageLabel.Deal_Contact.toString(), contactName } };

	if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

		if (DTP.createDealTeam(projectName, dealName, data, TabName.Acuity.toString(), action.SCROLLANDBOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----", YesNo.No);

			log(LogStatus.INFO,
					"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
							+ contactName + " at Firm Tab under Acuity section---------",
					YesNo.No);
			WebElement ele = DTP.getDealTeamID(10);
			if (ele != null) {
				String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
				ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name, "ADT_17",
						excelLabel.DealTeamID);
				log(LogStatus.INFO, "successfully created and noted id of DT" + id + " and deal name " + dealName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "could not create DT" + dealName);
				log(LogStatus.SKIP, "could not create DT" + dealName, YesNo.Yes);
			}
		} else {
			sa.assertTrue(false, "could not create DT" + dealName);
			log(LogStatus.SKIP, "could not create DT" + dealName, YesNo.Yes);
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + TabName.Deal_Team + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + TabName.Deal_Team + " tab");
	}
	
			if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

				if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns2, 30)) {

					log(LogStatus.INFO, "open created item" + ADEIns2, YesNo.No);

					if (BP.dealAcuityDealName(dealName, 30) != null) {
						log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
						if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
							log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
//							if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
//								log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
								if (BP.dealAcuityHSRName(dealName, stage, 30) != null) {

									log(LogStatus.PASS, "HSR: " + stage + " is present", YesNo.No);

									String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
									
									if (cp.verifyDate(todaysDate,null, actualDate)) {
										log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
									}
									else {
									log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
										sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
									}

								} else {
									log(LogStatus.FAIL, "HSR stage name not present: " + stage, YesNo.Yes);
									sa.assertTrue(false, "HSR stage name not present: " + stage);

								}
						} else {
							log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
							sa.assertTrue(false, "stage name not present: " + stage);

						}

//					} else {
//						log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
//						sa.assertTrue(false, "date receivednot present: " + dateReceived);
//					}

					} else {
						log(LogStatus.FAIL, "HSR stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "HSR stage name not present: " + stage);

					}

				} else {

					sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns2);
					log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns2, YesNo.Yes);

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
public void ADETc081_VerifyDealGridWhenAdvisorFirmContactGetsAddedIntoaDealTeamasaDealContact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String recordType = "";
	String dealName = ADEDeal22;
	String companyName = ADEDeal22CompanyName;
	String stage = ADEDeal22Stage;
	String dateReceived = todaysDate;
	String contactName = ADEContact2FName + " " + ADEContact2LName;

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
	

	String[][] data = { { PageLabel.Deal.toString(), dealName }, { PageLabel.Deal_Contact.toString(), contactName } };

	if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

		if (DTP.createDealTeam(projectName, dealName, data, TabName.Acuity.toString(), action.SCROLLANDBOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----", YesNo.No);

			log(LogStatus.INFO,
					"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
							+ contactName + " at Firm Tab under Acuity section---------",
					YesNo.No);
			WebElement ele = DTP.getDealTeamID(10);
			if (ele != null) {
				String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
				ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name, "ADT_18",
						excelLabel.DealTeamID);
				log(LogStatus.INFO, "successfully created and noted id of DT" + id + " and deal name " + dealName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "could not create DT" + dealName);
				log(LogStatus.SKIP, "could not create DT" + dealName, YesNo.Yes);
			}
		} else {
			sa.assertTrue(false, "could not create DT" + dealName);
			log(LogStatus.SKIP, "could not create DT" + dealName, YesNo.Yes);
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + TabName.Deal_Team + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + TabName.Deal_Team + " tab");
	}
	
			if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

				if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns2, 30)) {

					log(LogStatus.INFO, "open created item" + ADEIns2, YesNo.No);

					if (BP.dealAcuityDealName(dealName, 30) != null) {
						log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
						if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
							log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
//							if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
//								log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
								if (BP.dealAcuityHSRName(dealName, stage, 30) != null) {

									log(LogStatus.PASS, "HSR: " + stage + " is present", YesNo.No);
									String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
									
									if (cp.verifyDate(todaysDate,null, actualDate)) {
										log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
									}
									else {
									log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
										sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
									}
								} else {
									log(LogStatus.FAIL, "HSR stage name not present: " + stage, YesNo.Yes);
									sa.assertTrue(false, "HSR stage name not present: " + stage);

								}
						} else {
							log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
							sa.assertTrue(false, "stage name not present: " + stage);

						}
							} else {
								log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
								sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

							}
						

//					} else {
//						log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
//						sa.assertTrue(false, "date receivednot present: " + dateReceived);
//					}

					

				} else {

					sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns2);
					log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns2, YesNo.Yes);

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
public void ADETc082_RemoveContactfromDealTeamandVerifyImpactonDealGridofAdvisorFirm(String projectName) {

	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

	String contactName = ADEContact14FName + " " + ADEContact14LName;
	String contactName1 = ADEContact11FName + " " + ADEContact11LName;
	String[][] data1 = { { PageLabel.Deal_Contact.toString(), contactName } };

	String dealName = ADEDeal21;

	if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEDealTeamID17, 10)) {
			if (DTP.UpdateDealContactName(projectName, data1, 30)) {
				log(LogStatus.INFO, "successfully changed name to " + contactName, YesNo.Yes);
			} else {
				sa.assertTrue(false, "not able to change name to " + contactName);
				log(LogStatus.SKIP, "not able to change name to " + contactName, YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEDealTeamID17 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEDealTeamID17 + " tab");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + TabName.Deal_Team + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + TabName.Deal_Team + " tab");
	}
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns2, 30)) {

			log(LogStatus.INFO, "open created item" + ADEIns2, YesNo.No);

			if (BP.dealAcuityDealName(dealName, 30) != null) {
				log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
			} else {
				log(LogStatus.FAIL, "Deal Name not present: " + dealName, YesNo.Yes);
				sa.assertTrue(true, "Deal Name not present: " + dealName);
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
public void ADETc083_1_DeleteDealTeamVerifyImpactDealGridAdvisorFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String dealsSectionHeaderMessage = "No items to display.";

    ArrayList<String> dealsSectionHeaderName = new ArrayList<String>();
    ArrayList<String> externalConnectionsSectionHeaderName = new ArrayList<String>();

	ArrayList<String> contactsSectionHeaderName = new ArrayList<String>();
	ArrayList<String> connectionsHeaders = new ArrayList<String>();

	if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
		if (fp.clickOnAlreadyCreatedItem(projectName,ADEDealTeamID18, 10)) {
			cp.clickOnShowMoreDropdownOnly(projectName, PageName.Deal_Team, "");
			log(LogStatus.INFO, "Able to Click on Show more Icon : " + TabName.Deal_Team + " For : " + ADEDealTeamID18,
					YesNo.No);
			ThreadSleep(500);
			WebElement ele = cp.actionDropdownElement(projectName, PageName.Deal_Team,
					ShowMoreActionDropDownList.Delete, 15);
			if (ele == null) {
				ele = cp.getDeleteButton(projectName, 30);
			}

			if (click(driver, ele, "Delete More Icon", action.BOOLEAN)) {
				log(LogStatus.INFO,
						"Able to Click on Delete more Icon : " + TabName.Deal_Team + " For : " + ADEDealTeamID18,
						YesNo.No);
				ThreadSleep(1000);
				if (click(driver, cp.getDeleteButtonOnDeletePopUp(projectName, 30), "Delete Button",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to Click on Delete button on Delete PoPup : " + TabName.Deal_Team
							+ " For : " + ADEDealTeamID18, YesNo.No);
					ThreadSleep(10000);
					if (cp.clickOnTab(projectName, TabName.Deal_Team)) {
						log(LogStatus.INFO, "Clicked on Tab : " + TabName.Deal_Team + " For : " + ADEDealTeamID18,
								YesNo.No);
						ThreadSleep(1000);
						if (!cp.clickOnAlreadyCreatedItem(projectName, TabName.Deal_Team, ADEDeal3, 10)) {
							log(LogStatus.INFO, "Item has been Deleted after delete operation  : " + ADEDealTeamID18
									+ " For : " + TabName.Deal_Team, YesNo.No);

						} else {
							sa.assertTrue(false, "Item has not been Deleted after delete operation  : " + ADEDealTeamID18
									+ " For : " + TabName.Deal_Team);
							log(LogStatus.SKIP, "Item has not been Deleted after delete operation  : " + ADEDealTeamID18
									+ " For : " + TabName.Deal_Team, YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Not Able to Click on Tab after delete : " + TabName.Deal_Team
								+ " For : " + ADEDealTeamID18);
						log(LogStatus.SKIP, "Not Able to Click on Tab after delete : " + TabName.Deal_Team
								+ " For : " + ADEDealTeamID18, YesNo.Yes);
					}
				} else {
					log(LogStatus.INFO, "not able to click on delete button, so not deleted : " + TabName.Deal_Team
							+ " For : " + ADEDealTeamID18, YesNo.No);
					sa.assertTrue(false, "not able to click on delete button, so not deleted : "
							+ TabName.Deal_Team + " For : " + ADEDealTeamID18);
				}
			} else {
				log(LogStatus.INFO,
						"not Able to Click on Delete more Icon : " + TabName.Deal_Team + " For : " + ADEDealTeamID18,
						YesNo.No);
				sa.assertTrue(false,
						"not Able to Click on Delete more Icon : " + TabName.Deal_Team + " For : " + ADEDealTeamID18);
			}
		} else {
			log(LogStatus.INFO, "not Able to Click on " + ADEDealTeamID18, YesNo.No);
			sa.assertTrue(false, "not Able to Click on " + ADEDealTeamID18);
		}
	} else {
		log(LogStatus.INFO, "not Able to Click on " + TabName.Deal_Team, YesNo.No);
		sa.assertTrue(false, "not Able to Click on " + TabName.Deal_Team);
	}
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns2, 30)) {
		ArrayList<String> result1 = BP
				.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
						contactsSectionHeaderName, null, dealsSectionHeaderName,dealsSectionHeaderMessage, connectionsHeaders,
						null,externalConnectionsSectionHeaderName,null);
		if (result1.isEmpty()) {
			log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
		} else {
			log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
			sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
		}

		} else {

			sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns2);
			log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns2, YesNo.Yes);

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
public void ADETc083_2_RestoreDealTeamVerifyImpactDealGridAdvisorFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null;

	TabName tabName = TabName.RecycleBinTab;
	String name = ADEDealTeamID18;
	String dealName = ADEDeal22;
	String stage = ADEDeal22Stage;
	String dateReceived = todaysDate;
	
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

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns2, 30)) {

			log(LogStatus.INFO, "open created item" + ADEIns2, YesNo.No);

			if (BP.dealAcuityDealName(dealName, 30) != null) {
				log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
				if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
					log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
//					if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
//						log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
						if (BP.dealAcuityHSRName(dealName, stage, 30) != null) {

							log(LogStatus.PASS, "HSR: " + stage + " is present", YesNo.No);
							String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
							
							if (cp.verifyDate(todaysDate,null, actualDate)) {
								log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
							}
							else {
							log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
								sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
							}

						} else {
							log(LogStatus.FAIL, "HSR stage name not present: " + stage, YesNo.Yes);
							sa.assertTrue(false, "HSR stage name not present: " + stage);

						}
				} else {
					log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
					sa.assertTrue(false, "stage name not present: " + stage);

				}
					} else {
						log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

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
public void ADETc084_1_DeleteDealContactVerifyImpactDealGridAdvisorFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String contactName = ADEContact2FName + " " + ADEContact2LName;
	String dealsSectionHeaderMessage = "No items to display.";

    ArrayList<String> dealsSectionHeaderName = new ArrayList<String>();
    ArrayList<String> externalConnectionsSectionHeaderName = new ArrayList<String>();
	ArrayList<String> contactsSectionHeaderName = new ArrayList<String>();
	ArrayList<String> connectionsHeaders = new ArrayList<String>();

	if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
		if (fp.clickOnAlreadyCreatedItem(projectName,contactName, 10)) {
			cp.clickOnShowMoreDropdownOnly(projectName, PageName.Object2Page, "");
			log(LogStatus.INFO, "Able to Click on Show more Icon : " + TabName.Object2Tab + " For : " + contactName,
					YesNo.No);
			ThreadSleep(500);
			WebElement ele = cp.actionDropdownElement(projectName, PageName.Object2Page,
					ShowMoreActionDropDownList.Delete, 15);
			if (ele == null) {
				ele = cp.getDeleteButton(projectName, 30);
			}

			if (click(driver, ele, "Delete More Icon", action.BOOLEAN)) {
				log(LogStatus.INFO,
						"Able to Click on Delete more Icon : " + TabName.Object2Tab + " For : " + contactName,
						YesNo.No);
				ThreadSleep(1000);
				if (click(driver, cp.getDeleteButtonOnDeletePopUp(projectName, 30), "Delete Button",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to Click on Delete button on Delete PoPup : " + TabName.Object2Tab
							+ " For : " + contactName, YesNo.No);
					ThreadSleep(10000);
					if (cp.clickOnTab(projectName, TabName.Object2Tab)) {
						log(LogStatus.INFO, "Clicked on Tab : " + TabName.Object2Tab + " For : " + contactName,
								YesNo.No);
						ThreadSleep(1000);
						if (!cp.clickOnAlreadyCreatedItem(projectName, TabName.Object2Tab, ADEDeal3, 10)) {
							log(LogStatus.INFO, "Item has been Deleted after delete operation  : " + contactName
									+ " For : " + TabName.Object2Tab, YesNo.No);

						} else {
							sa.assertTrue(false, "Item has not been Deleted after delete operation  : " + contactName
									+ " For : " + TabName.Object2Tab);
							log(LogStatus.SKIP, "Item has not been Deleted after delete operation  : " + contactName
									+ " For : " + TabName.Object2Tab, YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Not Able to Click on Tab after delete : " + TabName.Object2Tab
								+ " For : " + contactName);
						log(LogStatus.SKIP, "Not Able to Click on Tab after delete : " + TabName.Object2Tab
								+ " For : " + contactName, YesNo.Yes);
					}
				} else {
					log(LogStatus.INFO, "not able to click on delete button, so not deleted : " + TabName.Object2Tab
							+ " For : " + contactName, YesNo.No);
					sa.assertTrue(false, "not able to click on delete button, so not deleted : "
							+ TabName.Object2Tab + " For : " + contactName);
				}
			} else {
				log(LogStatus.INFO,
						"not Able to Click on Delete more Icon : " + TabName.Object2Tab + " For : " + contactName,
						YesNo.No);
				sa.assertTrue(false,
						"not Able to Click on Delete more Icon : " + TabName.Object2Tab + " For : " + contactName);
			}
		} else {
			log(LogStatus.INFO, "not Able to Click on " + contactName, YesNo.No);
			sa.assertTrue(false, "not Able to Click on " + contactName);
		}
	} else {
		log(LogStatus.INFO, "not Able to Click on " + TabName.Object2Tab, YesNo.No);
		sa.assertTrue(false, "not Able to Click on " + TabName.Object2Tab);
	}
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns2, 30)) {
		ArrayList<String> result1 = BP
				.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
						contactsSectionHeaderName, null, dealsSectionHeaderName,dealsSectionHeaderMessage, connectionsHeaders,
						null,externalConnectionsSectionHeaderName,null);
		if (result1.isEmpty()) {
			log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
		} else {
			log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
			sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
		}

		} else {

			sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns2);
			log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns2, YesNo.Yes);

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
public void ADETc084_2_RestoreDealContactVerifyImpactDealGridAdvisorFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null;

	TabName tabName = TabName.RecycleBinTab;
	String contactName = ADEContact2FName + " " + ADEContact2LName;
	String dealName = ADEDeal22;
	String stage = ADEDeal22Stage;
	String dateReceived = todaysDate;
	
	if (cp.clickOnTab(projectName, tabName)) {
		log(LogStatus.INFO, "Clicked on Tab : " + tabName + " For : " + contactName, YesNo.No);
		ThreadSleep(1000);
		cp.clickOnAlreadyCreatedItem(projectName, tabName, contactName, 20);
		log(LogStatus.INFO, "Clicked on  : " + contactName + " For : " + tabName, YesNo.No);
		ThreadSleep(2000);

		ele = cp.getCheckboxOfRestoreItemOnRecycleBin(projectName, contactName, 10);
		if (clickUsingJavaScript(driver, ele, "Check box against : " + contactName, action.BOOLEAN)) {
			log(LogStatus.INFO, "Click on checkbox for " + contactName, YesNo.No);

			ThreadSleep(1000);
			ele = cp.getRestoreButtonOnRecycleBin(projectName, 10);
			if (clickUsingJavaScript(driver, ele, "Restore Button : " + contactName, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on Restore Button for " + contactName, YesNo.No);
				ThreadSleep(1000);
			} else {
				sa.assertTrue(false, "Not Able to Click on Restore Button for " + contactName);
				log(LogStatus.SKIP, "Not Able to Click on Restore Button for " + contactName, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on checkbox for " + contactName);
			log(LogStatus.SKIP, "Not Able to Click on checkbox for " + contactName, YesNo.Yes);
		}

	} else {
		sa.assertTrue(false, "Not Able to Click on Tab : " + tabName + " For : " + contactName);
		log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName + " For : " + contactName, YesNo.Yes);
	}
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns2, 30)) {

			log(LogStatus.INFO, "open created item" + ADEIns2, YesNo.No);

			if (BP.dealAcuityDealName(dealName, 30) != null) {
				log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
				if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
					log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
//					if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
//						log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
						if (BP.dealAcuityHSRName(dealName, stage, 30) != null) {

							log(LogStatus.PASS, "HSR: " + stage + " is present", YesNo.No);
							String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
							
							if (cp.verifyDate(todaysDate,null, actualDate)) {
								log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
							}
							else {
							log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
								sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
							}

						} else {
							log(LogStatus.FAIL, "HSR stage name not present: " + stage, YesNo.Yes);
							sa.assertTrue(false, "HSR stage name not present: " + stage);

						}
					
				} else {
					log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
					sa.assertTrue(false, "stage name not present: " + stage);

				}

//			} else {
//				log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
//				sa.assertTrue(false, "date receivednot present: " + dateReceived);
//			}


			} else {
				log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

			}

		} else {

			sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns2);
			log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns2, YesNo.Yes);

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
public void ADETc085_1_DeleteDealVerifyImpactDealGridAdvisorFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String dealsSectionHeaderMessage = "No items to display.";
	

    ArrayList<String> dealsSectionHeaderName = new ArrayList<String>();
    ArrayList<String> externalConnectionsSectionHeaderName = new ArrayList<String>();
	ArrayList<String> contactsSectionHeaderName = new ArrayList<String>();
	ArrayList<String> connectionsHeaders = new ArrayList<String>();

	if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
		if (fp.clickOnAlreadyCreatedItem(projectName,ADEDeal22, 10)) {
			cp.clickOnShowMoreDropdownOnly(projectName, PageName.Object4Page, "");
			log(LogStatus.INFO, "Able to Click on Show more Icon : " + TabName.Object4Tab + " For : " + ADEDeal22,
					YesNo.No);
			ThreadSleep(500);
			WebElement ele = cp.actionDropdownElement(projectName, PageName.Object4Page,
					ShowMoreActionDropDownList.Delete, 15);
			if (ele == null) {
				ele = cp.getDeleteButton(projectName, 30);
			}

			if (click(driver, ele, "Delete More Icon", action.BOOLEAN)) {
				log(LogStatus.INFO,
						"Able to Click on Delete more Icon : " + TabName.Object4Tab + " For : " + ADEDeal22,
						YesNo.No);
				ThreadSleep(1000);
				if (click(driver, cp.getDeleteButtonOnDeletePopUp(projectName, 30), "Delete Button",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to Click on Delete button on Delete PoPup : " + TabName.Object4Tab
							+ " For : " + ADEDeal22, YesNo.No);
					ThreadSleep(10000);
					if (cp.clickOnTab(projectName, TabName.Object4Tab)) {
						log(LogStatus.INFO, "Clicked on Tab : " + TabName.Object4Tab + " For : " + ADEDeal22,
								YesNo.No);
						ThreadSleep(1000);
						if (!cp.clickOnAlreadyCreatedItem(projectName, TabName.Object4Tab, ADEDeal22, 10)) {
							log(LogStatus.INFO, "Item has been Deleted after delete operation  : " + ADEDeal22
									+ " For : " + TabName.Object4Tab, YesNo.No);

						} else {
							sa.assertTrue(false, "Item has not been Deleted after delete operation  : " + ADEDeal22
									+ " For : " + TabName.Object4Tab);
							log(LogStatus.SKIP, "Item has not been Deleted after delete operation  : " + ADEDeal22
									+ " For : " + TabName.Object4Tab, YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Not Able to Click on Tab after delete : " + TabName.Object4Tab
								+ " For : " + ADEDeal22);
						log(LogStatus.SKIP, "Not Able to Click on Tab after delete : " + TabName.Object4Tab
								+ " For : " + ADEDeal22, YesNo.Yes);
					}
				} else {
					log(LogStatus.INFO, "not able to click on delete button, so not deleted : " + TabName.Object4Tab
							+ " For : " + ADEDeal22, YesNo.No);
					sa.assertTrue(false, "not able to click on delete button, so not deleted : "
							+ TabName.Object4Tab + " For : " + ADEDeal22);
				}
			} else {
				log(LogStatus.INFO,
						"not Able to Click on Delete more Icon : " + TabName.Object4Tab + " For : " + ADEDeal22,
						YesNo.No);
				sa.assertTrue(false,
						"not Able to Click on Delete more Icon : " + TabName.Object4Tab + " For : " + ADEDeal22);
			}
		} else {
			log(LogStatus.INFO, "not Able to Click on " + ADEDeal22, YesNo.No);
			sa.assertTrue(false, "not Able to Click on " + ADEDeal22);
		}
	} else {
		log(LogStatus.INFO, "not Able to Click on " + TabName.Object4Tab, YesNo.No);
		sa.assertTrue(false, "not Able to Click on " + TabName.Object4Tab);
	}
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns2, 30)) {
		ArrayList<String> result1 = BP
				.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
						contactsSectionHeaderName, null, dealsSectionHeaderName,dealsSectionHeaderMessage, connectionsHeaders,
						null,externalConnectionsSectionHeaderName,null);
		if (result1.isEmpty()) {
			log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
		} else {
			log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
			sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
		}

		} else {

			sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns2);
			log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns2, YesNo.Yes);

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
public void ADETc085_2_RestoreDealVerifyImpactDealGridAdvisorFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null;

	TabName tabName = TabName.RecycleBinTab;
	
	String dealName = ADEDeal22;
	String stage = ADEDeal22Stage;
	String dateReceived = todaysDate;
	
	if (cp.clickOnTab(projectName, tabName)) {
		log(LogStatus.INFO, "Clicked on Tab : " + tabName + " For : " + dealName, YesNo.No);
		ThreadSleep(1000);
		cp.clickOnAlreadyCreatedItem(projectName, tabName, dealName, 20);
		log(LogStatus.INFO, "Clicked on  : " + dealName + " For : " + tabName, YesNo.No);
		ThreadSleep(2000);

		ele = cp.getCheckboxOfRestoreItemOnRecycleBin(projectName, dealName, 10);
		if (clickUsingJavaScript(driver, ele, "Check box against : " + dealName, action.BOOLEAN)) {
			log(LogStatus.INFO, "Click on checkbox for " + dealName, YesNo.No);

			ThreadSleep(1000);
			ele = cp.getRestoreButtonOnRecycleBin(projectName, 10);
			if (clickUsingJavaScript(driver, ele, "Restore Button : " + dealName, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on Restore Button for " + dealName, YesNo.No);
				ThreadSleep(1000);
			} else {
				sa.assertTrue(false, "Not Able to Click on Restore Button for " + dealName);
				log(LogStatus.SKIP, "Not Able to Click on Restore Button for " + dealName, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on checkbox for " + dealName);
			log(LogStatus.SKIP, "Not Able to Click on checkbox for " + dealName, YesNo.Yes);
		}

	} else {
		sa.assertTrue(false, "Not Able to Click on Tab : " + tabName + " For : " + dealName);
		log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName + " For : " + dealName, YesNo.Yes);
	}
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns2, 30)) {

			log(LogStatus.INFO, "open created item" + ADEIns2, YesNo.No);

			if (BP.dealAcuityDealName(dealName, 30) != null) {
				log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
				if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
					log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
//					if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
//						log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
						if (BP.dealAcuityHSRName(dealName, stage, 30) != null) {

							log(LogStatus.PASS, "HSR: " + stage + " is present", YesNo.No);
							String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
							
							if (cp.verifyDate(todaysDate,null, actualDate)) {
								log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
							}
							else {
							log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
								sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
							}

						} else {
							log(LogStatus.FAIL, "HSR stage name not present: " + stage, YesNo.Yes);
							sa.assertTrue(false, "HSR stage name not present: " + stage);

						}
					
				} else {
					log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
					sa.assertTrue(false, "stage name not present: " + stage);

				}

//			} else {
//				log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
//				sa.assertTrue(false, "date receivednot present: " + dateReceived);
//			}
			} else {
				log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

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
public void ADETc086_ChangeStageofDealandVerifytheImpactonHighestStageColumnatDealGrid(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String name = ADEDealTeamID18;
	String dealName = ADEDeal22;
	String stage = "Due Diligence";
	String dateReceived = todaysDate;
	
	if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEDeal22, 10)){
			if (fp.changeStage(projectName, Stage.Due_Diligence.toString(), 10)) {
			}else {
				sa.assertTrue(false,"not able to change stage to "+Stage.Due_Diligence);
				log(LogStatus.SKIP,"not able to change stage to "+Stage.Due_Diligence,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to find pipeline "+ADEDeal22);
			log(LogStatus.SKIP,"not able to find pipeline "+ADEDeal22,YesNo.Yes);
		}
	}else {
		sa.assertTrue(false,"not able to click on deal tab");
		log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
	}
			
	
if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
	log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

	if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns2, 30)) {

		log(LogStatus.INFO, "open created item" + ADEIns2, YesNo.No);

		if (BP.dealAcuityDealName(dealName, 30) != null) {
			log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
			if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
				log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
//				if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
//					log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
					if (BP.dealAcuityHSRName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "HSR: " + stage + " is present", YesNo.No);
						String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
						
						if (cp.verifyDate(todaysDate,null, actualDate)) {
							log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
						}
						else {
						log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
							sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
						}
					} else {
						log(LogStatus.FAIL, "HSR stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "HSR stage name not present: " + stage);

					}
				
			} else {
				log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
				sa.assertTrue(false, "stage name not present: " + stage);

			}


//		} else {
//			log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
//			sa.assertTrue(false, "date receivednot present: " + dateReceived);
//		}

		} else {
			log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
			sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

		}

	} else {

		sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
		log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

	}
} else {
	log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
	sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
}

if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
	if (fp.clickOnAlreadyCreatedItem(projectName, ADEDeal22, 10)){
		if (fp.changeStage(projectName, Stage.Parked.toString(), 10)) {
		}else {
			sa.assertTrue(false,"not able to change stage to "+Stage.Parked);
			log(LogStatus.SKIP,"not able to change stage to "+Stage.Parked,YesNo.Yes);
		}
	}else {
		sa.assertTrue(false,"not able to find pipeline "+ADEDeal22);
		log(LogStatus.SKIP,"not able to find pipeline "+ADEDeal22,YesNo.Yes);
	}
}else {
	sa.assertTrue(false,"not able to click on deal tab");
	log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
}
		
String stage1 = "Parked";

if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns2, 30)) {

	log(LogStatus.INFO, "open created item" + ADEIns2, YesNo.No);

	if (BP.dealAcuityDealName(dealName, 30) != null) {
		log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
		if (BP.dealAcuityStageName(dealName, stage1, 30) != null) {
			log(LogStatus.PASS, "Stage Name: " + stage1 + " is present", YesNo.No);
//			if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
//				log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
				if (BP.dealAcuityHSRName(dealName, stage1, 30) != null) {
					log(LogStatus.PASS, "HSR: " + stage1 + " is present", YesNo.No);
					String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
					
					if (cp.verifyDate(todaysDate,null, actualDate)) {
						log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
					}
					else {
					log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
						sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
					}
				} else {
					log(LogStatus.FAIL, "HSR stage name not present: " + stage1, YesNo.Yes);
					sa.assertTrue(false, "HSR stage name not present: " + stage1);

				}
			
		} else {
			log(LogStatus.FAIL, "stage name not present: " + stage1, YesNo.Yes);
			sa.assertTrue(false, "stage name not present: " + stage1);

		}

//	} else {
//		log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
//		sa.assertTrue(false, "date receivednot present: " + dateReceived);
//	}

	} else {
		log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
		sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

	}
} else {

	sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns2);
	log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns2, YesNo.Yes);

}
} else {
log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
}

if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
	if (fp.clickOnAlreadyCreatedItem(projectName, ADEDeal22, 10)){
		if (fp.changeStage(projectName, Stage.DeclinedDead.toString(), 10)) {
		}else {
			sa.assertTrue(false,"not able to change stage to "+Stage.DeclinedDead);
			log(LogStatus.SKIP,"not able to change stage to "+Stage.DeclinedDead,YesNo.Yes);
		}
	}else {
		sa.assertTrue(false,"not able to find pipeline "+ADEDeal22);
		log(LogStatus.SKIP,"not able to find pipeline "+ADEDeal22,YesNo.Yes);
	}
}else {
	sa.assertTrue(false,"not able to click on deal tab");
	log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
}
		
String stage2 = Stage.DeclinedDead.toString();

if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns2, 30)) {

	log(LogStatus.INFO, "open created item" + ADEIns2, YesNo.No);

	if (BP.dealAcuityDealName(dealName, 30) != null) {
		log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
		if (BP.dealAcuityStageName(dealName, stage2, 30) != null) {
			log(LogStatus.PASS, "Stage Name: " + stage2 + " is present", YesNo.No);
//			if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
//				log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
				if (BP.dealAcuityHSRName(dealName, stage1, 30) != null) {
					log(LogStatus.PASS, "HSR: " + stage1 + " is present", YesNo.No);
					String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
					
					if (cp.verifyDate(todaysDate,null, actualDate)) {
						log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
					}
					else {
					log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
						sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
					}
				} else {
					log(LogStatus.FAIL, "HSR stage name not present: " + stage1, YesNo.Yes);
					sa.assertTrue(false, "HSR stage name not present: " + stage1);

				}
			
		} else {
			log(LogStatus.FAIL, "stage name not present: " + stage2, YesNo.Yes);
			sa.assertTrue(false, "stage name not present: " + stage2);

		}

//	} else {
//		log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
//		sa.assertTrue(false, "date receivednot present: " + dateReceived);
//	}
} else {
	log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
	sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

}
} else {

	sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns2);
	log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns2, YesNo.Yes);

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
public void ADETc087_VerifyDefaultSortingDealGridofAdvisorPage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String recordType = "";
	String dealName = ADEDeal23;
	String companyName = ADEDeal23CompanyName;
	String stage = ADEDeal23Stage;

//	if (lp.clickOnTab(projectName, tabObj4)) {
//		log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
//		ThreadSleep(3000);
//		if (dp.createDeal(recordType, dealName, companyName, stage, "Date Received", tomorrowsDate)) {
//			log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);
//
//		} else {
//			log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
//			sa.assertTrue(false, dealName + " deal is not created");
//		}
//	} else {
//		log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
//		sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
//	}
	
	String recordType1 = "";
	String dealName1 = ADEDeal24;
	String companyName1 = ADEDeal24CompanyName;
	String stage1 = ADEDeal24Stage;

	if (lp.clickOnTab(projectName, tabObj4)) {
		log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
		ThreadSleep(3000);
		if (dp.createDeal(recordType1, dealName1, companyName1, stage1, "Date Received", yesterdaysDate)) {
			log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);		} else {
			log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
			sa.assertTrue(false, dealName + " deal is not created");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
		sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
	}
	
	ArrayList<String> dealname = new ArrayList<String>();
	dealname.add(dealName.toString());
	
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns2, 30)) {

			log(LogStatus.INFO, "open created item" + ADEIns2, YesNo.No);

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
public void ADETc088_VerifyThatDealNamesClickableandDealRedirectionforAccounts(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String dealName = ADEDeal22;

	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns2, 30)) {
			if (BP.dealAcuityDealName(dealName, 30) != null) {
				log(LogStatus.PASS, "deal name: " + dealName + " is hyperlink and is present", YesNo.No);
				if (clickUsingJavaScript(driver, BP.dealAcuityDealName(dealName, 10), "deal name: " + dealName,
						action.BOOLEAN)) {
					log(LogStatus.PASS, "Clicked on deal name: " + dealName, YesNo.No);
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
				log(LogStatus.FAIL, "deal name not present: " + dealName, YesNo.Yes);
				sa.assertTrue(false, "deal name not present: " + dealName);

			}
		
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns2 + " tab");
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
public void ADETc089_VerifyDealCountasZeroRedirectionContactatContactGridAdvisorFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String DealCountInFirm = "0";
	String actualDealCount = null;
	String contactName = ADEContact23FName + " " + ADEContact23LName;

	String ExpectedMsg = "No item to display.";
	if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
		log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);	
		
		ADEContact23EmailID=	lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact23EmailID, "Contact", excelLabel.Variable_Name, "ADEContact23",excelLabel.Contact_EmailId);

		if (cp.createContactAcuity(projectName, ADEContact23FName, ADEContact23LName, ADEIns2, ADEContact23EmailID,ADEContact15RecordType, null, null, CreationPage.ContactPage, null, null)) {
			log(LogStatus.INFO,"successfully Created Contact : "+ADEContact23FName+" "+ADEContact23LName,YesNo.No);	
		} else {
			sa.assertTrue(false,"Not Able to Create Contact : "+ADEContact23FName+" "+ADEContact23LName);
			log(LogStatus.SKIP,"Not Able to Create Contact: "+ADEContact23FName+" "+ADEContact23LName,YesNo.Yes);
		}
	}
		
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns2, 30)) {

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
					driver.close();
					driver.switchTo().window(parentWindowId);

				} else {
					log(LogStatus.ERROR, "Not able to click on " + ADEIns2 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + ADEIns2 + " tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
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
public void ADETc090_VerifyDealCountRedirectionForContactatContactGridofAdvisorFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String DealCountInFirm = "1";
	String actualDealCount = null;
	String contactName = ADEContact23FName + " " + ADEContact23LName;
	
	String recordType = "";
	String dealName = ADEDeal21;
	String companyName = ADEDeal21CompanyName;
	String stage = ADEDeal21Stage;
	String dateReceived = todaysDate;
	
	String[][] data = { { PageLabel.Deal.toString(), dealName }, { PageLabel.Deal_Contact.toString(), contactName }};

	if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

		if (DTP.createDealTeam(projectName, dealName, data, TabName.Acuity.toString(), action.SCROLLANDBOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----", YesNo.No);

			log(LogStatus.INFO,
					"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
							+ contactName + " at Firm Tab under Acuity section---------",
					YesNo.No);
			WebElement ele = DTP.getDealTeamID(10);
			if (ele != null) {
				String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
				ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name, "ADT_19",
						excelLabel.DealTeamID);
				log(LogStatus.INFO, "successfully created and noted id of DT" + id + " and deal name " + dealName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "could not create DT" + dealName);
				log(LogStatus.SKIP, "could not create DT" + dealName, YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns2 + " tab");
		}

	} else {
		log(LogStatus.ERROR, "Not able to click on " + TabName.Deal_Team + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + TabName.Deal_Team + " tab");
	}
		
			if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

				if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns2, 30)) {

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
						if (CommonLib.click(driver, BP.contactDealCount(contactName, 30), "Deal Count: " + actualDealCount,
								action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Deal Count: " + actualDealCount + " of Record: " + contactName,
									YesNo.No);
							String parentWindowId = CommonLib.switchOnWindow(driver);
							if (!parentWindowId.isEmpty()) {
								if (BP.dealAcuity2DealName(dealName, 30) != null) {
									log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
									if (BP.dealAcuity2StageName(dealName, stage, 30) != null) {
										log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
//										if (BP.dealAcuity2DateReceived(dealName, dateReceived, 30) != null) {
//											log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
											if (BP.dealAcuityPopUpCompanyName(dealName, companyName, 30) != null) {
												log(LogStatus.PASS, " Company name: " + companyName + " is present", YesNo.No);
												String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
												
												if (cp.verifyDate(todaysDate,null, actualDate)) {
													log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
												}
												else {
												log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
													sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
												}
											} else {
												log(LogStatus.FAIL, "HSR stage name not present: " + stage, YesNo.Yes);
												sa.assertTrue(false, "HSR stage name not present: " + stage);

											}
										
									} else {
										log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
										sa.assertTrue(false, "stage name not present: " + stage);

									}


//								} else {
//									log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
//									sa.assertTrue(false, "date receivednot present: " + dateReceived);
//								}
								} else {
									log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
									sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

								}
							}
							driver.close();
							driver.switchTo().window(parentWindowId);
							
							} else {
								log(LogStatus.FAIL, "Clicked on Deal Count: " + actualDealCount + " of Record: " + contactName, YesNo.Yes);
								sa.assertTrue(false,  "Clicked on Deal Count: " + actualDealCount + " of Record: " + contactName);

							}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + ADEIns2 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + ADEIns2 + " tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			}
			ThreadSleep(5000);
			lp.CRMlogout();
			sa.assertAll();
		}
		

//Lender record type


@Parameters({ "projectName" })
@Test
public void ADETc091_VerifythatNewFinancingpopupgetsClosewhenCancelandCrossIcongetsClicked(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String ExpectedHeader = "New Financing";
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns5, 30)) {
			if(BP.NewFinancingIcon( 10) != null) {
				log(LogStatus.PASS, "New Fianacing Icon is present", YesNo.No);
				if (CommonLib.click(driver, BP.NewFinancingIcon( 30), "New Fianacing Icon: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on New Fianacing Icon", YesNo.No);
					String actualHeader = getText(driver, BP.getNewFinanacingpopupheader(20), "NewFinanacingpopupheader",
							action.SCROLLANDBOOLEAN);
					/* if (ExpectedHeader.equals(actualHeader)) { */
						if (actualHeader.equals(ExpectedHeader)) {
						log(LogStatus.INFO,
								"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
										+ ExpectedHeader + " for New Finanacing popup",
								YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
										+ ExpectedHeader + "for New Finanacing popup",
								YesNo.No);
					}
					if (CommonLib.click(driver, BP.getNewFinancingPopupCancelIcon( 30), "New Fianacing Cancel Icon: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on New Fianacing cancel Icon", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to click on New Fianacing cancel Icon", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on New Fianacing cancel Icon" + " tab");
					}
					
				} else {
					log(LogStatus.ERROR, "Clicked on New Fianacing Icon ", YesNo.Yes);
					sa.assertTrue(false, "Clicked on New Fianacing Icon " + " tab");
				}

			} else {
				log(LogStatus.ERROR, "New Fianacing Icon not  present", YesNo.Yes);
				sa.assertTrue(false, "New Fianacing Icon not present");
			}
			refresh(driver);
			if(BP.NewFinancingIcon( 10) != null) {
				log(LogStatus.PASS, "New Fianacing Icon is present", YesNo.No);
				if (CommonLib.click(driver, BP.NewFinancingIcon( 30), "New Fianacing Icon: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on New Fianacing Icon", YesNo.No);
					String actualHeader = getText(driver, BP.getNewFinanacingpopupheader(20), "NewFinanacingpopupheader",
							action.SCROLLANDBOOLEAN);
					if (ExpectedHeader.equals(actualHeader)) {
						log(LogStatus.INFO,
								"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
										+ ExpectedHeader + " for New Finanacing popup",
								YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
										+ ExpectedHeader + "for New Finanacing popup",
								YesNo.No);
					}
					if (CommonLib.click(driver, BP.getNewFinancingPopupCrossIcon( 30), "New Fianacing Cross Icon: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on New Fianacing Cross Icon", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to click on New Fianacing Cross Icon", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on New Fianacing Cross Icon" + " tab");
					}
					
				} else {
					log(LogStatus.ERROR, "Clicked on New Fianacing Icon ", YesNo.Yes);
					sa.assertTrue(false, "Clicked on New Fianacing Icon " + " tab");
				}

			} else {
				log(LogStatus.ERROR, "New Fianacing Icon not  present", YesNo.Yes);
				sa.assertTrue(false, "New Fianacing Icon not present");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns2 + " tab");
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
public void ADETc092_VerifyDealGridWhenAdvisorFirmContactGetsAddedIntoaDealTeamasaDealContact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	FinancingPageBusinessLayer FTP = new FinancingPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String dealName = ADEDeal25;
	String companyName = ADEDeal25CompanyName;
	String stage = ADEDeal25Stage;
	String dateReceived = todaysDate;
	String contactName = ADEContact5FName + " " + ADEContact5LName;

	
	String[][] data = { { PageLabel.Deal.toString(), dealName }, { PageLabel.Firm.toString(), ADEIns5 }, { PageLabel.Company.toString(), ADEIns1 } };
	String recordType = "";
	

	String addRemoveTabName="";
	
	addRemoveTabName="Financing";
	if (lp.addTab_Lighting( addRemoveTabName, 5)) {
		log(LogStatus.INFO,"Tab added : "+addRemoveTabName,YesNo.No);
	} else {
		log(LogStatus.FAIL,"Tab not added : "+addRemoveTabName,YesNo.No);
		sa.assertTrue(false, "Tab not added : "+addRemoveTabName);
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
	
	if (lp.clickOnTab(projectName, TabName.Financing)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Financing, YesNo.No);

		if (FTP.createFinancing(projectName, dealName, data, "Acuity", action.SCROLLANDBOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Financing for Deal: " + dealName + "----", YesNo.No);

			log(LogStatus.INFO,
					"---------Now Going to Check Financing Count should get increase by one for Contact named "
							+ "" + " at Firm Tab under Acuity section---------",
					YesNo.No);
			String xpath = "//*[text()='Financing']/parent::h1//slot/lightning-formatted-text";
			WebElement ele = FindElement(driver, xpath, "financing id", action.BOOLEAN, 10);
			if (ele != null) {
				String id = getText(driver, ele, "financing id", action.SCROLLANDBOOLEAN);
				ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Financing", excelLabel.Variable_Name, "F_01",
						excelLabel.FinancingId);
				log(LogStatus.INFO, "successfully created and noted id of financing" + id + " and deal name " + dealName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "could not create Financing" + dealName);
				log(LogStatus.SKIP, "could not create Financing" + dealName, YesNo.Yes);
			}
		} else {
			sa.assertTrue(false, "could not create Financing" + dealName);
			log(LogStatus.SKIP, "could not create Financing" + dealName, YesNo.Yes);
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + TabName.Financing + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + TabName.Financing + " tab");
	}
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns5, 30)) {

			log(LogStatus.INFO, "open created item" + ADEIns5, YesNo.No);

			if (BP.dealAcuityDealName(dealName, 30) != null) {
				log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
				if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
					log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
//					if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
//						log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
						if (BP.dealAcuityHSRName(dealName, stage, 30) != null) {

							log(LogStatus.PASS, "HSR: " + stage + " is present", YesNo.No);
                           String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
							
							if (cp.verifyDate(todaysDate,null, actualDate)) {
								log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
							}
							else {
							log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
								sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
							}

						} else {
							log(LogStatus.FAIL, "HSR stage name not present: " + stage, YesNo.Yes);
							sa.assertTrue(false, "HSR stage name not present: " + stage);

						}
					
				} else {
					log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
					sa.assertTrue(false, "stage name not present: " + stage);

				}

//			} else {
//				log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
//				sa.assertTrue(false, "date receivednot present: " + dateReceived);
//			}
			} else {
				log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

			}
		} else {

			sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns5);
			log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns5, YesNo.Yes);

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
public void ADETc093_1_DeleteDealVerifyImpactDealGridLenderFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String dealsSectionHeaderMessage = "No items to display.";
	

    ArrayList<String> dealsSectionHeaderName = new ArrayList<String>();
    ArrayList<String> externalConnectionsSectionHeaderName = new ArrayList<String>();
	ArrayList<String> contactsSectionHeaderName = new ArrayList<String>();
	ArrayList<String> connectionsHeaders = new ArrayList<String>();

	if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
		if (fp.clickOnAlreadyCreatedItem(projectName,ADEDeal25, 10)) {
			cp.clickOnShowMoreDropdownOnly(projectName, PageName.Object4Page, "");
			log(LogStatus.INFO, "Able to Click on Show more Icon : " + TabName.Object4Tab + " For : " + ADEDeal25,
					YesNo.No);
			ThreadSleep(500);
			WebElement ele = cp.actionDropdownElement(projectName, PageName.Object4Page,
					ShowMoreActionDropDownList.Delete, 15);
			if (ele == null) {
				ele = cp.getDeleteButton(projectName, 30);
			}

			if (click(driver, ele, "Delete More Icon", action.BOOLEAN)) {
				log(LogStatus.INFO,
						"Able to Click on Delete more Icon : " + TabName.Object4Tab + " For : " + ADEDeal25,
						YesNo.No);
				ThreadSleep(1000);
				if (click(driver, cp.getDeleteButtonOnDeletePopUp(projectName, 30), "Delete Button",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to Click on Delete button on Delete PoPup : " + TabName.Object4Tab
							+ " For : " + ADEDeal25, YesNo.No);
					ThreadSleep(10000);
					if (cp.clickOnTab(projectName, TabName.Object4Tab)) {
						log(LogStatus.INFO, "Clicked on Tab : " + TabName.Object4Tab + " For : " + ADEDeal25,
								YesNo.No);
						ThreadSleep(1000);
						if (!cp.clickOnAlreadyCreatedItem(projectName, TabName.Object4Tab, ADEDeal25, 10)) {
							log(LogStatus.INFO, "Item has been Deleted after delete operation  : " + ADEDeal25
									+ " For : " + TabName.Object4Tab, YesNo.No);

						} else {
							sa.assertTrue(false, "Item has not been Deleted after delete operation  : " + ADEDeal25
									+ " For : " + TabName.Object4Tab);
							log(LogStatus.SKIP, "Item has not been Deleted after delete operation  : " + ADEDeal25
									+ " For : " + TabName.Object4Tab, YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Not Able to Click on Tab after delete : " + TabName.Object4Tab
								+ " For : " + ADEDeal25);
						log(LogStatus.SKIP, "Not Able to Click on Tab after delete : " + TabName.Object4Tab
								+ " For : " + ADEDeal25, YesNo.Yes);
					}
				} else {
					log(LogStatus.INFO, "not able to click on delete button, so not deleted : " + TabName.Object4Tab
							+ " For : " + ADEDeal25, YesNo.No);
					sa.assertTrue(false, "not able to click on delete button, so not deleted : "
							+ TabName.Object4Tab + " For : " + ADEDeal25);
				}
			} else {
				log(LogStatus.INFO,
						"not Able to Click on Delete more Icon : " + TabName.Object4Tab + " For : " + ADEDeal25,
						YesNo.No);
				sa.assertTrue(false,
						"not Able to Click on Delete more Icon : " + TabName.Object4Tab + " For : " + ADEDeal25);
			}
		} else {
			log(LogStatus.INFO, "not Able to Click on " + ADEDeal25, YesNo.No);
			sa.assertTrue(false, "not Able to Click on " + ADEDeal25);
		}
	} else {
		log(LogStatus.INFO, "not Able to Click on " + TabName.Object4Tab, YesNo.No);
		sa.assertTrue(false, "not Able to Click on " + TabName.Object4Tab);
	}
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns5, 30)) {
		ArrayList<String> result1 = BP
				.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
						contactsSectionHeaderName, null, dealsSectionHeaderName,dealsSectionHeaderMessage, connectionsHeaders,
						null,externalConnectionsSectionHeaderName,null);
		if (result1.isEmpty()) {
			log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
		} else {
			log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
			sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
		}

		} else {

			sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns5);
			log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns5, YesNo.Yes);

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
public void ADETc093_2_RestoreDealVerifyImpactDealGridLenderFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null;

	TabName tabName = TabName.RecycleBinTab;
	String name = ADEDealTeamID18;
	String dealName = ADEDeal25;
	String stage = ADEDeal25Stage;
	String dateReceived = todaysDate;
	
	if (cp.clickOnTab(projectName, tabName)) {
		log(LogStatus.INFO, "Clicked on Tab : " + tabName + " For : " + dealName, YesNo.No);
		ThreadSleep(1000);
		cp.clickOnAlreadyCreatedItem(projectName, tabName, dealName, 20);
		log(LogStatus.INFO, "Clicked on  : " + dealName + " For : " + tabName, YesNo.No);
		ThreadSleep(2000);

		ele = cp.getCheckboxOfRestoreItemOnRecycleBin(projectName, dealName, 10);
		if (clickUsingJavaScript(driver, ele, "Check box against : " + dealName, action.BOOLEAN)) {
			log(LogStatus.INFO, "Click on checkbox for " + dealName, YesNo.No);

			ThreadSleep(1000);
			ele = cp.getRestoreButtonOnRecycleBin(projectName, 10);
			if (clickUsingJavaScript(driver, ele, "Restore Button : " + dealName, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on Restore Button for " + dealName, YesNo.No);
				ThreadSleep(1000);
			} else {
				sa.assertTrue(false, "Not Able to Click on Restore Button for " + dealName);
				log(LogStatus.SKIP, "Not Able to Click on Restore Button for " + dealName, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on checkbox for " + dealName);
			log(LogStatus.SKIP, "Not Able to Click on checkbox for " + dealName, YesNo.Yes);
		}

	} else {
		sa.assertTrue(false, "Not Able to Click on Tab : " + tabName + " For : " + dealName);
		log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName + " For : " + dealName, YesNo.Yes);
	}
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns5, 30)) {

			log(LogStatus.INFO, "open created item" + ADEIns5, YesNo.No);

			if (BP.dealAcuityDealName(dealName, 30) != null) {
				log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
				if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
					log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
//					if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
//						log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
						if (BP.dealAcuityHSRName(dealName, stage, 30) != null) {
							log(LogStatus.PASS, "HSR: " + stage + " is present", YesNo.No);
	                           String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();

							
							if (cp.verifyDate(todaysDate,null, actualDate)) {
								log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
							}
							else {
							log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
								sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
							}

						} else {
							log(LogStatus.FAIL, "HSR stage name not present: " + stage, YesNo.Yes);
							sa.assertTrue(false, "HSR stage name not present: " + stage);

						}

					
				} else {
					log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
					sa.assertTrue(false, "stage name not present: " + stage);

				}

//			} else {
//				log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
//				sa.assertTrue(false, "date receivednot present: " + dateReceived);
//			}

			} else {
				log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

			}
		} else {

			sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns5);
			log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns5, YesNo.Yes);

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
public void ADETc094_1_DeleteFinancingVerifyImpactDealGridLenderFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String dealsSectionHeaderMessage = "No items to display.";
	

    ArrayList<String> dealsSectionHeaderName = new ArrayList<String>();
    ArrayList<String> externalConnectionsSectionHeaderName = new ArrayList<String>();
	ArrayList<String> contactsSectionHeaderName = new ArrayList<String>();
	ArrayList<String> connectionsHeaders = new ArrayList<String>();

	if (lp.clickOnTab(projectName, TabName.Financing)) {
		if (fp.clickOnAlreadyCreatedItem(projectName,ADEFinancingId1, 10)) {
			cp.clickOnShowMoreDropdownOnly(projectName, PageName.Object4Page, "");
			log(LogStatus.INFO, "Able to Click on Show more Icon : " + TabName.Financing + " For : " + ADEFinancingId1,
					YesNo.No);
			ThreadSleep(500);
			WebElement ele = cp.actionDropdownElement(projectName, PageName.Financing,
					ShowMoreActionDropDownList.Delete, 15);
			if (ele == null) {
				ele = cp.getDeleteButton(projectName, 30);
			}

			if (click(driver, ele, "Delete More Icon", action.BOOLEAN)) {
				log(LogStatus.INFO,
						"Able to Click on Delete more Icon : " + TabName.Financing + " For : " + ADEFinancingId1,
						YesNo.No);
				ThreadSleep(1000);
				if (click(driver, cp.getDeleteButtonOnDeletePopUp(projectName, 30), "Delete Button",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to Click on Delete button on Delete PoPup : " + TabName.Financing
							+ " For : " + ADEFinancingId1, YesNo.No);
					ThreadSleep(10000);
					if (cp.clickOnTab(projectName, TabName.Object4Tab)) {
						log(LogStatus.INFO, "Clicked on Tab : " + TabName.Financing + " For : " + ADEFinancingId1,
								YesNo.No);
						ThreadSleep(1000);
						if (!cp.clickOnAlreadyCreatedItem(projectName, TabName.Financing, ADEDeal25, 10)) {
							log(LogStatus.INFO, "Item has been Deleted after delete operation  : " + ADEFinancingId1
									+ " For : " + TabName.Financing, YesNo.No);

						} else {
							sa.assertTrue(false, "Item has not been Deleted after delete operation  : " + ADEDeal25
									+ " For : " + TabName.Financing);
							log(LogStatus.SKIP, "Item has not been Deleted after delete operation  : " + ADEDeal25
									+ " For : " + TabName.Financing, YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Not Able to Click on Tab after delete : " + TabName.Financing
								+ " For : " + ADEFinancingId1);
						log(LogStatus.SKIP, "Not Able to Click on Tab after delete : " + TabName.Financing
								+ " For : " + ADEFinancingId1, YesNo.Yes);
					}
				} else {
					log(LogStatus.INFO, "not able to click on delete button, so not deleted : " + TabName.Financing
							+ " For : " + ADEFinancingId1, YesNo.No);
					sa.assertTrue(false, "not able to click on delete button, so not deleted : "
							+ TabName.Financing + " For : " + ADEFinancingId1);
				}
			} else {
				log(LogStatus.INFO,
						"not Able to Click on Delete more Icon : " + TabName.Financing + " For : " + ADEFinancingId1,
						YesNo.No);
				sa.assertTrue(false,
						"not Able to Click on Delete more Icon : " + TabName.Financing + " For : " + ADEFinancingId1);
			}
		} else {
			log(LogStatus.INFO, "not Able to Click on " + ADEFinancingId1, YesNo.No);
			sa.assertTrue(false, "not Able to Click on " + ADEFinancingId1);
		}
	} else {
		log(LogStatus.INFO, "not Able to Click on " + TabName.Object4Tab, YesNo.No);
		sa.assertTrue(false, "not Able to Click on " + TabName.Object4Tab);
	}
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns5, 30)) {
		ArrayList<String> result1 = BP
				.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
						contactsSectionHeaderName, null, dealsSectionHeaderName,dealsSectionHeaderMessage, connectionsHeaders,
						null,externalConnectionsSectionHeaderName,null);
		if (result1.isEmpty()) {
			log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
		} else {
			log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
			sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
		}

		} else {

			sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns5);
			log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns5, YesNo.Yes);

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
public void ADETc094_2_RestoreFinancingVerifyImpactDealGridLenderFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null;

	TabName tabName = TabName.RecycleBinTab;
	String name = ADEFinancingId1;
	String dealName = ADEDeal25;
	String stage = ADEDeal25Stage;
	String dateReceived = todaysDate;
	
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

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns5, 30)) {

			log(LogStatus.INFO, "open created item" + ADEIns5, YesNo.No);

			if (BP.dealAcuityDealName(dealName, 30) != null) {
				log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
				if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
					log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
						if (BP.dealAcuityHSRName(dealName, stage, 30) != null) {

							log(LogStatus.PASS, "HSR: " + stage + " is present", YesNo.No);
                             String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
							
							if (cp.verifyDate(todaysDate,null, actualDate)) {
								log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
							}
							else {
							log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
								sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
							}

						} else {
							log(LogStatus.FAIL, "HSR stage name not present: " + stage, YesNo.Yes);
							sa.assertTrue(false, "HSR stage name not present: " + stage);

						}

					
				} else {
					log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
					sa.assertTrue(false, "stage name not present: " + stage);
					}

			} else {
				log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

			}
		} else {

			sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns5);
			log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns5, YesNo.Yes);

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
public void ADETc095_EditChangeFirmfromLendertoAnotherFirmandVerifyImpactatDealGridofLenderFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	FinancingPageBusinessLayer FTP = new FinancingPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String[][] data = { { PageLabel.Firm.toString(), ADEIns1 } };
	
String dealsSectionHeaderMessage = "No items to display.";
	

    ArrayList<String> dealsSectionHeaderName = new ArrayList<String>();
    ArrayList<String> externalConnectionsSectionHeaderName = new ArrayList<String>();
	ArrayList<String> contactsSectionHeaderName = new ArrayList<String>();
	ArrayList<String> connectionsHeaders = new ArrayList<String>();
	
	if (lp.clickOnTab(projectName, TabName.Financing)) {
		if (fp.clickOnAlreadyCreatedItem(projectName,ADEFinancingId1, 10)) {

		if (FTP.UpdateFinancingFirm(projectName, data, "Acuity", action.BOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Financing for Deal: " + data + "----", YesNo.No);

			log(LogStatus.INFO,
					"---------Now Going to Check Financing Count should get increase by one for Contact named "
							+ "" + " at Firm Tab under Acuity section---------",
					YesNo.No);
		}
		} else {

			sa.assertTrue(false, "Not Able to open created Financing: " + ADEFinancingId1);
			log(LogStatus.SKIP, "Not Able to open created Financing : " + ADEFinancingId1, YesNo.Yes);

		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + TabName.Financing + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + TabName.Financing + " tab");
	}
			if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
				if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns5, 30)) {
				ArrayList<String> result1 = BP
						.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
								contactsSectionHeaderName, null, dealsSectionHeaderName,dealsSectionHeaderMessage, connectionsHeaders,
								null,externalConnectionsSectionHeaderName,null);

				if (result1.isEmpty()) {
					log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
				} else {
					log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
					sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
				}

				} else {

					sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns5);
					log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns5, YesNo.Yes);

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
public void ADETc096_ChangeStageofDealandVerifytheImpactonHighestStageColumnatDealGrid(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	FinancingPageBusinessLayer FTP = new FinancingPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String[][] data = { { PageLabel.Firm.toString(), ADEIns5 } };
	String name = ADEDealTeamID18;
	String dealName = ADEDeal25;
	String stage = "Due Diligence";
	String dateReceived = todaysDate;
	
	if (lp.clickOnTab(projectName, TabName.Financing)) {
		if (fp.clickOnAlreadyCreatedItem(projectName,ADEFinancingId1, 10)) {

		if (FTP.UpdateFinancingFirm(projectName, data, "Acuity", action.BOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Financing for Deal: " + data + "----", YesNo.No);

			log(LogStatus.INFO,
					"---------Now Going to Check Financing Count should get increase by one for Contact named "
							+ "" + " at Firm Tab under Acuity section---------",
					YesNo.No);
		}
		} else {

			sa.assertTrue(false, "Not Able to open created Financing: " + ADEFinancingId1);
			log(LogStatus.SKIP, "Not Able to open created Financing : " + ADEFinancingId1, YesNo.Yes);

		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + TabName.Financing + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + TabName.Financing + " tab");
	}
	
	if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEDeal25, 10)){
			if (fp.changeStage(projectName, Stage.Due_Diligence.toString(), 10)) {
			}else {
				sa.assertTrue(false,"not able to change stage to "+Stage.LOI);
				log(LogStatus.SKIP,"not able to change stage to "+Stage.LOI,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to find pipeline "+ADEDeal22);
			log(LogStatus.SKIP,"not able to find pipeline "+ADEDeal22,YesNo.Yes);
		}
	}else {
		sa.assertTrue(false,"not able to click on deal tab");
		log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
	}
			
	
if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
	log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

	if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns5, 30)) {

		log(LogStatus.INFO, "open created item" + ADEIns5, YesNo.No);

		if (BP.dealAcuityDealName(dealName, 30) != null) {
			log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
			if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
				log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
					if (BP.dealAcuityHSRName(dealName, stage, 30) != null) {

						log(LogStatus.PASS, "HSR: " + stage + " is present", YesNo.No);
						String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
						
						if (cp.verifyDate(todaysDate,null, actualDate)) {
							log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
						}
						else {
						log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
							sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
						}

					} else {
						log(LogStatus.FAIL, "HSR stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "HSR stage name not present: " + stage);

					}
				
			} else {
				log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
				sa.assertTrue(false, "stage name not present: " + stage);

			}
		} else {
			log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
			sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

		}

	} else {

		sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
		log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

	}
} else {
	log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
	sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
}

if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
	if (fp.clickOnAlreadyCreatedItem(projectName, ADEDeal25, 10)){
		if (fp.changeStage(projectName, Stage.Parked.toString(), 10)) {
		}else {
			sa.assertTrue(false,"not able to change stage to "+Stage.LOI);
			log(LogStatus.SKIP,"not able to change stage to "+Stage.LOI,YesNo.Yes);
		}
	}else {
		sa.assertTrue(false,"not able to find pipeline "+ADEDeal22);
		log(LogStatus.SKIP,"not able to find pipeline "+ADEDeal22,YesNo.Yes);
	}
}else {
	sa.assertTrue(false,"not able to click on deal tab");
	log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
}
		
String stage1 = "Parked";

if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns5, 30)) {

	log(LogStatus.INFO, "open created item" + ADEIns5, YesNo.No);

	if (BP.dealAcuityDealName(dealName, 30) != null) {
		log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
		if (BP.dealAcuityStageName(dealName, stage1, 30) != null) {
			log(LogStatus.PASS, "Stage Name: " + stage1 + " is present", YesNo.No);
				if (BP.dealAcuityHSRName(dealName, stage1, 30) != null) {

					log(LogStatus.PASS, "HSR: " + stage1 + " is present", YesNo.No);
					String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
					
					if (cp.verifyDate(todaysDate,null, actualDate)) {
						log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
					}
					else {
					log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
						sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
					}
				} else {
					log(LogStatus.FAIL, "HSR stage name not present: " + stage1, YesNo.Yes);
					sa.assertTrue(false, "HSR stage name not present: " + stage1);

				}
			
		} else {
			log(LogStatus.FAIL, "stage name not present: " + stage1, YesNo.Yes);
			sa.assertTrue(false, "stage name not present: " + stage1);

		}
	} else {
		log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
		sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

	}

} else {

	sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns1);
	log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns1, YesNo.Yes);

}
} else {
log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
}

if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
	if (fp.clickOnAlreadyCreatedItem(projectName, ADEDeal25, 10)){
		if (fp.changeStage(projectName, Stage.DeclinedDead.toString(), 10)) {
		}else {
			sa.assertTrue(false,"not able to change stage to "+Stage.LOI);
			log(LogStatus.SKIP,"not able to change stage to "+Stage.LOI,YesNo.Yes);
		}
	}else {
		sa.assertTrue(false,"not able to find pipeline "+ADEDeal22);
		log(LogStatus.SKIP,"not able to find pipeline "+ADEDeal22,YesNo.Yes);
	}
}else {
	sa.assertTrue(false,"not able to click on deal tab");
	log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
}
		
String stage2 = Stage.DeclinedDead.toString();

if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns5, 30)) {

	log(LogStatus.INFO, "open created item" + ADEIns5, YesNo.No);

	if (BP.dealAcuityDealName(dealName, 30) != null) {
		log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
		if (BP.dealAcuityStageName(dealName, stage2, 30) != null) {
			log(LogStatus.PASS, "Stage Name: " + stage2 + " is present", YesNo.No);
				if (BP.dealAcuityHSRName(dealName, stage1, 30) != null) {

					log(LogStatus.PASS, "HSR: " + stage1 + " is present", YesNo.No);



					String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
					
					if (cp.verifyDate(todaysDate,null, actualDate)) {
						log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
					}
					else {
					log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
						sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
					}

				} else {
					log(LogStatus.FAIL, "HSR stage name not present: " + stage1, YesNo.Yes);
					sa.assertTrue(false, "HSR stage name not present: " + stage1);

				}

			
		} else {
			log(LogStatus.FAIL, "stage name not present: " + stage2, YesNo.Yes);
			sa.assertTrue(false, "stage name not present: " + stage2);

		}
	} else {
		log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
		sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

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
public void ADETc097_VerifyDefaultSortingDealGridofLenderPage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	FinancingPageBusinessLayer FTP = new FinancingPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String recordType = "";
	String dealName = ADEDeal26;
	String companyName = ADEDeal26CompanyName;
	String stage = ADEDeal26Stage;

	String[][] data = { { PageLabel.Deal.toString(), dealName }, { PageLabel.Firm.toString(), ADEIns5 }, { PageLabel.Company.toString(), ADEIns1 } };

	
	
	
	if (lp.clickOnTab(projectName, tabObj4)) {
		log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
		ThreadSleep(3000);
		if (dp.createDeal(recordType, dealName, companyName, stage, "Date Received", tomorrowsDate)) {
			log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);

		} else {
			log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
			sa.assertTrue(false, dealName + " deal is not created");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
		sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
	}
	
	String recordType1 = "";
	String dealName1 = ADEDeal27;
	String companyName1 = ADEDeal27CompanyName;
	String stage1 = ADEDeal27Stage;

	String[][] data1 = { { PageLabel.Deal.toString(), dealName1 }, { PageLabel.Firm.toString(), ADEIns5 }, { PageLabel.Company.toString(), ADEIns1 } };
	if (lp.clickOnTab(projectName, tabObj4)) {
		log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
		ThreadSleep(3000);
		if (dp.createDeal(recordType1, dealName1, companyName1, stage1, "Date Received", yesterdaysDate)) {
			log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);

		} else {
			log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
			sa.assertTrue(false, dealName + " deal is not created");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
		sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
	}
	
	
	if (lp.clickOnTab(projectName, TabName.Financing)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Financing, YesNo.No);

		if (FTP.createFinancing(projectName, dealName, data, "Acuity", action.SCROLLANDBOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Financing for Deal: " + dealName + "----", YesNo.No);

			log(LogStatus.INFO,
					"---------Now Going to Check Financing Count should get increase by one for Contact named "
							+ "" + " at Firm Tab under Acuity section---------",
					YesNo.No);
			String xpath = "//*[text()='Financing']/parent::h1//slot/lightning-formatted-text";
			WebElement ele = FindElement(driver, xpath, "financing id", action.BOOLEAN, 10);
			if (ele != null) {
				String id = getText(driver, ele, "financing id", action.SCROLLANDBOOLEAN);
				ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Financing", excelLabel.Variable_Name, "F_02",
						excelLabel.FinancingId);
				log(LogStatus.INFO, "successfully created and noted id of financing" + id + " and deal name " + dealName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "could not create Financing" + dealName);
				log(LogStatus.SKIP, "could not create Financing" + dealName, YesNo.Yes);
			}
		} else {
			sa.assertTrue(false, "could not create Financing" + dealName);
			log(LogStatus.SKIP, "could not create Financing" + dealName, YesNo.Yes);
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + TabName.Financing + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + TabName.Financing + " tab");
	}
	
	if (lp.clickOnTab(projectName, TabName.Financing)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Financing, YesNo.No);

		if (FTP.createFinancing(projectName, dealName1, data1, "Acuity", action.SCROLLANDBOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Financing for Deal: " + dealName + "----", YesNo.No);

			log(LogStatus.INFO,
					"---------Now Going to Check Financing Count should get increase by one for Contact named "
							+ "" + " at Firm Tab under Acuity section---------",
					YesNo.No);
			String xpath = "//*[text()='Financing']/parent::h1//slot/lightning-formatted-text";
			WebElement ele = FindElement(driver, xpath, "financing id", action.BOOLEAN, 10);
			if (ele != null) {
				String id = getText(driver, ele, "financing id", action.SCROLLANDBOOLEAN);
				ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Financing", excelLabel.Variable_Name, "F_03",
						excelLabel.FinancingId);
				log(LogStatus.INFO, "successfully created and noted id of financing" + id + " and deal name " + dealName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "could not create Financing" + dealName);
				log(LogStatus.SKIP, "could not create Financing" + dealName, YesNo.Yes);
			}
		} else {
			sa.assertTrue(false, "could not create Financing" + dealName);
			log(LogStatus.SKIP, "could not create Financing" + dealName, YesNo.Yes);
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + TabName.Financing + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + TabName.Financing + " tab");
	}
	ArrayList<String> dealname = new ArrayList<String>();
	dealname.add(dealName.toString());
	
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns5, 30)) {

			log(LogStatus.INFO, "open created item" + ADEIns5, YesNo.No);

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

			sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns5);
			log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns5, YesNo.Yes);

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
public void ADETc098_VerifyThatDealNamesClickableandDealRedirectionforAccounts(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String dealName = ADEDeal25;

	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns5, 30)) {
			if (BP.dealAcuityDealName(dealName, 30) != null) {
				log(LogStatus.PASS, "deal name: " + dealName + " is hyperlink and is present", YesNo.No);
				if (click(driver, BP.dealAcuityDealName(dealName, 10), "deal name: " + dealName,
						action.BOOLEAN)) {
					log(LogStatus.PASS, "Clicked on deal name: " + dealName, YesNo.No);
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
				log(LogStatus.FAIL, "deal name not present: " + dealName, YesNo.Yes);
				sa.assertTrue(false, "deal name not present: " + dealName);

			}
		
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns2 + " tab");
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
public void ADETc099_VerifyDealCountasZeroRedirectionContactatContactGridLenderFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String DealCountInFirm = "0";
	String actualDealCount = null;
	String contactName = ADEContact5FName + " " + ADEContact5LName;

	String ExpectedMsg = BP.ErrorMessageAcuity;
		
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns5, 30)) {

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
				if (CommonLib.click(driver, BP.contactDealCount(contactName, 30), "Deal Count: " + actualDealCount,
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Deal Count: " + actualDealCount + " of Record: " + contactName,
							YesNo.No);
					String parentWindowId = CommonLib.switchOnWindow(driver);
					if (!parentWindowId.isEmpty()) {
					String actualMsg = getText(driver, BP.getErrorMsg(20), "ErrorMsg", action.SCROLLANDBOOLEAN);
						if (actualMsg.equals(ExpectedMsg)) {
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
						driver.switchTo().window(parentWindowId);
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + "contactDealCount" + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + "contactDealCount" + " tab");
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns5 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns5 + " tab");
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
public void ADETc100_VerifyDealCountRedirectionForContactatContactGridofLenderFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String DealCountInFirm = "1";
	String actualDealCount = null;
	String contactName = ADEContact5FName + " " + ADEContact5LName;
	
	String recordType = "";
	String dealName = ADEDeal26;
	String companyName = ADEDeal26CompanyName;
	String stage = ADEDeal26Stage;
	String dateReceived = todaysDate;
	
	String[][] data = { { PageLabel.Deal.toString(), dealName }, { PageLabel.Deal_Contact.toString(), contactName }};

	if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

		if (DTP.createDealTeam(projectName, dealName, data,TabName.Acuity.toString(), action.SCROLLANDBOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----", YesNo.No);

			log(LogStatus.INFO,
					"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
							+ contactName + " at Firm Tab under Acuity section---------",
					YesNo.No);
			WebElement ele = DTP.getDealTeamID(10);
			if (ele != null) {
				String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
				ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name, "ADT_19",
						excelLabel.DealTeamID);
				log(LogStatus.INFO, "successfully created and noted id of DT" + id + " and deal name " + dealName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "could not create DT" + dealName);
				log(LogStatus.SKIP, "could not create DT" + dealName, YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns2 + " tab");
		}

	} else {
		log(LogStatus.ERROR, "Not able to click on " + TabName.Deal_Team + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + TabName.Deal_Team + " tab");
	}
		
			if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

				if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns5, 30)) {

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
						if (CommonLib.click(driver, BP.contactDealCount(contactName, 30), "Deal Count: " + actualDealCount,
								action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Deal Count: " + actualDealCount + " of Record: " + contactName,
									YesNo.No);
							String parentWindowId = CommonLib.switchOnWindow(driver);
							if (!parentWindowId.isEmpty()) {
								if (BP.dealAcuity2DealName(dealName, 30) != null) {
									log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
									if (BP.dealAcuity2StageName(dealName, stage, 30) != null) {
										log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
											if (BP.dealAcuityPopUpCompanyName(dealName, companyName, 30) != null) {
												log(LogStatus.PASS, " Company name: " + companyName + " is present", YesNo.No);
												String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
												
												if (cp.verifyDate(todaysDate,null, actualDate)) {
													log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
												}
												else {
												log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
													sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
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
										log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
										sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);
									}
							} else {
								log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + dealName, YesNo.Yes);
								sa.assertTrue(false, "No New Window Open after click on Deal Link: " + dealName);
							}
							driver.close();
							driver.switchTo().window(parentWindowId);
							
							} else {
								log(LogStatus.FAIL, "Clicked on Deal Count: " + actualDealCount + " of Record: " + contactName, YesNo.Yes);
								sa.assertTrue(false,  "Clicked on Deal Count: " + actualDealCount + " of Record: " + contactName);

							}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + ADEIns2 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + ADEIns2 + " tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			}
			ThreadSleep(5000);
			lp.CRMlogout();
			sa.assertAll();
		}


//PE Record Type

@Parameters({ "projectName" })
@Test
public void ADETc101_VerifythatDealsWhereCurrentFirmISTaggedinCompanyareDisplayinginFundstabatPEFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

	String recordType = "";
	String dealName = ADEDeal28;
	String companyName = ADEDeal28CompanyName;
	String stage = ADEDeal28Stage;
	String dateReceived = todaysDate;
	String contactName = ADEContact2FName + " " + ADEContact2LName;
	String ExpectedMsg = BP.ErrorMessageAcuity;
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

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns16, 30)) {

			log(LogStatus.INFO, "open created item" + ADEIns16, YesNo.No);

			if (BP.dealAcuityDealName(dealName, 30) != null) {
				log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
				if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
					log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
						if (BP.dealAcuityHSRName(dealName, stage, 30) != null) {

							log(LogStatus.PASS, "HSR: " + stage + " is present", YesNo.No);
							String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
							
							if (cp.verifyDate(todaysDate,null, actualDate)) {
								log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
							}
							else {
							log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
								sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
							}

						} else {
							log(LogStatus.FAIL, "HSR stage name not present: " + stage, YesNo.Yes);
							sa.assertTrue(false, "HSR stage name not present: " + stage);

						}
					
				} else {
					log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
					sa.assertTrue(false, "stage name not present: " + stage);
				}

			} else {
				log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

			}

			if (CommonLib.click(driver, BP.SourcedTab(30,action.SCROLLANDBOOLEAN), "Source tab: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Source tab", YesNo.No);
				String actualMsg = getText(driver, BP.getErrorMsg(20), "ErrorMsg", action.SCROLLANDBOOLEAN);
					if (actualMsg.equals(ExpectedMsg)) {
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
				log(LogStatus.FAIL, "Not able to Click on Source tab: " , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Source tab: " );
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns5 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns5 + " tab");
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
public void ADETc102_VerifythatDealswhereCurrentFirmistaggedinSourceFirmareDisplayinginSourcedtabatPEFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

	String recordType = "";
	String dealName1 = ADEDeal28;
	String dealName = ADEDeal29;
	String companyName = ADEDeal29CompanyName;
	String stage = ADEDeal29Stage;
	String dateReceived = todaysDate;
	String labellabels = EditPageLabel.Source_Firm +"<Break>"+ PageLabel.Date_Received;
	String otherLabelValues = ADEDeal29SourceFirm +"<Break>"+ todaysDate;
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

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns16, 30)) {

			log(LogStatus.INFO, "open created item" + ADEIns16, YesNo.No);

			if (BP.dealAcuityDealName(dealName1, 30) != null) {
				log(LogStatus.PASS, "Deal Name: " + dealName1 + " is hyperlink and is present", YesNo.No);
				if (BP.dealAcuityStageName(dealName1, stage, 30) != null) {
					log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
						if (BP.dealAcuityHSRName(dealName1, stage, 30) != null) {
							log(LogStatus.PASS, " HSR Stage: " + stage + " is present", YesNo.No);
							String actualDate= BP.dealAcuityDateReceived2(dealName1, 30).getText();
							
							if (cp.verifyDate(todaysDate,null, actualDate)) {
								log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
							}
							else {
							log(LogStatus.ERROR, "Date Received is not matched For : "+dealName1+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
								sa.assertTrue(false,"Date Received is not matched For : "+dealName1+" Actual : "+actualDate+" /t Expected : "+todaysDate );
							}
						} else {
							log(LogStatus.FAIL, "HSR stage name not present: " + stage, YesNo.Yes);
							sa.assertTrue(false, "HSR stage name not present: " + stage);
						}

					
				} else {
					log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
					sa.assertTrue(false, "stage name not present: " + stage);
				}

			} else {
				log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName1, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName1);

			}

			if (CommonLib.click(driver, BP.SourcedTab( 30,action.SCROLLANDBOOLEAN), "Source tab: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Source tab", YesNo.No);
				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
							if (BP.dealAcuityHSRName(dealName, stage, 30) != null) {

								log(LogStatus.PASS, "HSR: " + stage + " is present", YesNo.No);
								String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
								
								if (cp.verifyDate(todaysDate,null, actualDate)) {
									log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
								}
								else {
								log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
									sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
								}

							} else {
								log(LogStatus.FAIL, "HSR stage name not present: " + stage, YesNo.Yes);
								sa.assertTrue(false, "HSR stage name not present: " + stage);

							}
						
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);
					}
				} else {
					log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on Source tab: " , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Source tab: " );
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns5 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns5 + " tab");
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
public void ADETc103_ChangeCurrentFirmfromCompanytoSourceandverifyImpactonFundsSourcedtabsofDealgridatFAPageatPEFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String dealName = ADEDeal28;
	String dealName1 = ADEDeal29;
	String companyname = "";
	String labellabels = EditPageLabel.Source_Firm.toString();
	String otherLabelValues = ADEIns16;
String dealsSectionHeaderMessage = BP.ErrorMessageAcuity;;
	

    ArrayList<String> dealsSectionHeaderName = new ArrayList<String>();

	ArrayList<String> contactsSectionHeaderName = new ArrayList<String>();
	ArrayList<String> connectionsHeaders = new ArrayList<String>();
	
	if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
		if (fp.clickOnAlreadyCreatedItem(projectName, dealName, 10)) {
			if (BP.clickOnShowMoreActionDownArrow(projectName, PageName.Object4Page, ShowMoreActionDropDownList.Edit, 10)) {
				ThreadSleep(5000);
				if (clickUsingJavaScript(driver, fp.getCompanyCrossIcon(projectName, 60), "Company Cross Icon", action.SCROLLANDBOOLEAN)) {
					appLog.info("Clicked on Company Cross Icon");
					ThreadSleep(3000);
				} else {
					appLog.error("Not able to click on Cross Icon button");
					log(LogStatus.ERROR, "Not able to clicked on edit button so cannot Account Name ", YesNo.Yes);
				}
				if (CommonLib.click(driver, dp.getSaveButton(30), tabObj4 + " save button", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on save button", YesNo.No);

				} else {
					log(LogStatus.ERROR, "Not able to click on save button", YesNo.No);

				}
			} else {
				log(LogStatus.ERROR, "Not able to click on the edit button", YesNo.No);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + dealName + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + dealName + " tab");
		}

			} else {
				log(LogStatus.ERROR, "Not able to click on " + TabName.Object4Tab + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + TabName.Object4Tab + " tab");
			}
			
	if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
				if (fp.clickOnAlreadyCreatedItem(projectName, dealName, 10)) {
					if (dp.UpdateOtherLable(projectName, labellabels,otherLabelValues, 10)) {
						log(LogStatus.INFO, "successfully changed name to " + dealName, YesNo.Yes);
					} else {
						sa.assertTrue(false, "not able to change name to " + dealName);
						log(LogStatus.SKIP, "not able to change name to " + dealName, YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + dealName + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + dealName + " tab");
				}

					} else {
						log(LogStatus.ERROR, "Not able to click on " + TabName.Object4Tab + " tab", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on " + TabName.Object4Tab + " tab");
					}
	
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns16, 30)) {
		ArrayList<String> result1 = BP
				.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
						contactsSectionHeaderName, null, dealsSectionHeaderName,dealsSectionHeaderMessage, connectionsHeaders,
						null,contactsSectionHeaderName, null);
		if (result1.isEmpty()) {
			log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
		} else {
			log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
			sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
		}
		if (CommonLib.click(driver, BP.SourcedTab( 30,action.SCROLLANDBOOLEAN), "Source tab: " + "",
				action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Source tab", YesNo.No);
			if (BP.dealAcuityDealName(dealName, 30) != null) {
				log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
				if (BP.dealAcuityDealName(dealName1, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
				} else {
					log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName1, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName1);

			}
		} else {
			log(LogStatus.FAIL, "Not able to Click on Source tab: " , YesNo.Yes);
			sa.assertTrue(false, "Not able to Click on Source tab: " );
			}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + ADEIns5 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + ADEIns5 + " tab");
	}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
lp.CRMlogout();
sa.assertAll();
}


@Parameters({ "projectName" })
@Test
public void ADETc104_ChangeCurrentFirmfromSourcetoCompanyandVerifyImpactonFundsSourceTabsofDealGridatFApageatPEFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String dealName = ADEDeal28;
	String dealName1 = ADEDeal29;
	String companyname =ADEIns16;
	String stage = ADEDeal29Stage;
	String dateReceived = todaysDate;
	if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
		if (fp.clickOnAlreadyCreatedItem(projectName, dealName1, 10)) {
			
	if (BP.clickOnShowMoreActionDownArrow(projectName, PageName.Object4Page, ShowMoreActionDropDownList.Edit, 10)) {
		ThreadSleep(2000);
		if (click(driver, dp.getSourceFirmCrossIcon(projectName, 60), "Company Cross Icon", action.SCROLLANDBOOLEAN)) {
			appLog.info("Clicked on Legal Cross icon");
			ThreadSleep(3000);
		} else {
			appLog.info("Not able to click on Cross Icon button");
			log(LogStatus.INFO, "Not able to clicked on edit button so cannot Account Name ", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "Not able to clicked on edit button so cannot Account Name ");
		}
		if (CommonLib.click(driver, dp.getSaveButton(30), tabObj4 + " save button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on save button", YesNo.No);

		} else {
			log(LogStatus.ERROR, "Not able to click on save button", YesNo.No);

		}
	} else {
		log(LogStatus.ERROR, "Not able to click on the edit button", YesNo.No);
	}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + dealName1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + dealName1 + " tab");
		}

			} else {
				log(LogStatus.ERROR, "Not able to click on " + TabName.Object4Tab + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + TabName.Object4Tab + " tab");
			}

	if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
				if (fp.clickOnAlreadyCreatedItem(projectName, dealName1, 10)) {
					if (fp.UpdateCompanyName(projectName, companyname, 10)) {
						log(LogStatus.INFO, "successfully changed name to " + companyname, YesNo.Yes);
					} else {
						sa.assertTrue(false, "not able to change name to " + companyname);
						log(LogStatus.SKIP, "not able to change name to " + companyname, YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + dealName1 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + dealName1 + " tab");
				}

					} else {
						log(LogStatus.ERROR, "Not able to click on " + TabName.Object4Tab + " tab", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on " + TabName.Object4Tab + " tab");
					}
	
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns16, 30)) {

			log(LogStatus.INFO, "open created item" + ADEIns16, YesNo.No);
			if (BP.dealAcuityDealName(dealName1, 30) != null) {
				log(LogStatus.PASS, "Deal Name: " + dealName1 + " is hyperlink and is present", YesNo.No);
				if (BP.dealAcuityStageName(dealName1, stage, 30) != null) {
					log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
						if (BP.dealAcuityHSRName(dealName1, stage, 30) != null) {
							log(LogStatus.PASS, "HSR: " + stage + " is present", YesNo.No);
							String actualDate= BP.dealAcuityDateReceived2(dealName1, 30).getText();
							
							if (cp.verifyDate(todaysDate,null, actualDate)) {
								log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
							}
							else {
							log(LogStatus.ERROR, "Date Received is not matched For : "+dealName1+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
								sa.assertTrue(false,"Date Received is not matched For : "+dealName1+" Actual : "+actualDate+" /t Expected : "+todaysDate );
							}
						} else {
							log(LogStatus.FAIL, "HSR stage name not present: " + stage, YesNo.Yes);
							sa.assertTrue(false, "HSR stage name not present: " + stage);

						}
					} else {
						log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName1, YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName1);

					}
				} else {
					log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
					sa.assertTrue(false, "stage name not present: " + stage);
				}
			if (CommonLib.click(driver, BP.SourcedTab( 30,action.SCROLLANDBOOLEAN), "Source tab: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Source tab", YesNo.No);
				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
							if (BP.dealAcuityHSRName(dealName, stage, 30) != null) {
								log(LogStatus.PASS, "HSR: " + stage + " is present", YesNo.No);
								String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
								
								if (cp.verifyDate(todaysDate,null, actualDate)) {
									log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
								}
								else {
								log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
									sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
								}
							} else {
								log(LogStatus.FAIL, "HSR stage name not present: " + stage, YesNo.Yes);
								sa.assertTrue(false, "HSR stage name not present: " + stage);

							}
						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);
					}
			} else {
				log(LogStatus.FAIL, "Not able to Click on Source tab: " , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Source tab: " );
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns5 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns5 + " tab");
		}

			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			}
	lp.CRMlogout();
	sa.assertAll();
}
	

@Parameters({ "projectName" })
@Test
public void ADETc105_VerifythaNewDealpopupgetsClosewhenCancelCrossIcongetsClicked(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String ExpectedHeader = "New Deal";
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns16, 30)) {
			if(BP.NewDealIcon( 10) != null) {
				log(LogStatus.PASS, "New Deal Icon is present", YesNo.No);
				if (CommonLib.click(driver, BP.NewDealIcon( 30), "New Deal Icon: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on New Deal Icon", YesNo.No);
					String actualHeader = getText(driver, BP.getNewDealpopupheader(20), "NewDealpopupheader",
							action.SCROLLANDBOOLEAN);
						if (actualHeader.equals(ExpectedHeader)) {
						log(LogStatus.INFO,
								"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
										+ ExpectedHeader + " for New Finanacing popup",
								YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
										+ ExpectedHeader + "for New Finanacing popup",
								YesNo.No);
					}
					if (CommonLib.click(driver, BP.getNewFinancingPopupCancelIcon( 30), "New Fianacing Cancel Icon: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on New Fianacing cancel Icon", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to click on New Fianacing cancel Icon", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on New Fianacing cancel Icon" + " tab");
					}
					
				} else {
					log(LogStatus.ERROR, "Clicked on New Fianacing Icon ", YesNo.Yes);
					sa.assertTrue(false, "Clicked on New Fianacing Icon " + " tab");
				}

			} else {
				log(LogStatus.ERROR, "New Fianacing Icon not  present", YesNo.Yes);
				sa.assertTrue(false, "New Fianacing Icon not present");
			}
			refresh(driver);
			if(BP.NewDealIcon( 10) != null) {
				log(LogStatus.PASS, "New Deal Icon is present", YesNo.No);
				if (CommonLib.click(driver, BP.NewDealIcon( 30), "New Deal Icon: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on New Deal Icon", YesNo.No);
					String actualHeader = getText(driver, BP.getNewDealpopupheader(20), "NewDealpopupheader",
							action.SCROLLANDBOOLEAN);
					if (actualHeader.equals(ExpectedHeader)) {
						log(LogStatus.INFO,
								"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
										+ ExpectedHeader + " for New Finanacing popup",
								YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
										+ ExpectedHeader + "for New Finanacing popup",
								YesNo.No);
					}
					if (CommonLib.click(driver, BP.getNewFinancingPopupCrossIcon( 30), "New Fianacing Cross Icon: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on New Fianacing Cross Icon", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to click on New Fianacing Cross Icon", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on New Fianacing Cross Icon" + " tab");
					}
					
				} else {
					log(LogStatus.ERROR, "Clicked on New Fianacing Icon ", YesNo.Yes);
					sa.assertTrue(false, "Clicked on New Fianacing Icon " + " tab");
				}

			} else {
				log(LogStatus.ERROR, "New Fianacing Icon not  present", YesNo.Yes);
				sa.assertTrue(false, "New Fianacing Icon not present");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns2 + " tab");
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
public void ADETc106_VerfiytheFunctionalityofAddDealIconwhenFundsTabisSelectedatDealGridofPEFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String recordType = "";
	String dealName = ADEDeal30;
	String stage = ADEDeal30Stage;
	String dateReceived = "0";

	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns16, 30)) {
			if (CommonLib.click(driver, dp.NewDealIcon(30), tabObj4 + "New Deal Icon", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on the new Deal Icon", YesNo.No);
		if (dp.createDealfromIcon(recordType, dealName, stage, "Date Received", tomorrowsDate)) {
			log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);

		} else {
			log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
			sa.assertTrue(false, dealName + " deal is not created");
		}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + "NewDealIcon", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + "NewDealIcon");
			}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + ADEIns16 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + ADEIns16 + " tab");
			}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + TabName.Object1Tab + " Tab", YesNo.No);
		sa.assertTrue(false, "Not able to click on " + TabName.Object1Tab + " Tab");
	}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns16, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns16, YesNo.No);

				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
							if (BP.dealAcuityHSRName(dealName, stage, 30) != null) {
								log(LogStatus.PASS, "HSR: " + stage + " is present", YesNo.No);
								String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
								
								if (cp.verifyDate(todaysDate,null, actualDate)) {
									log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
								}else {
								log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
									sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
								}
							} else {
								log(LogStatus.FAIL, "HSR stage name not present: " + stage, YesNo.Yes);
								sa.assertTrue(false, "HSR stage name not present: " + stage);

							}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}
				} else {
					log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns16 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns16 + " tab");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
			lp.CRMlogout();
			sa.assertAll();
}


@Parameters({ "projectName" })
@Test
public void ADETc107_VerifythatNewDealpopupgetsClosewhenCancelCrossIcongetsClicked(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String ExpectedHeader = "New Sourced Deal";
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns16, 30)) {
			if (CommonLib.click(driver, BP.SourcedTab( 30,action.SCROLLANDBOOLEAN), "Source tab: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Source tab", YesNo.No);
			if(BP.NewSourcedDealIcon( 10) != null) {
				log(LogStatus.PASS, "New Deal Icon is present", YesNo.No);
				if (CommonLib.click(driver, BP.NewSourcedDealIcon( 30), "New Deal Icon: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on New Deal Icon", YesNo.No);
					String actualHeader = getText(driver, BP.getNewSourcedDealpopupheader(20), "NewSourcedDealpopupheader",
							action.SCROLLANDBOOLEAN);
						if (actualHeader.equals(ExpectedHeader)) {
						log(LogStatus.INFO,
								"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
										+ ExpectedHeader + " for New Finanacing popup",
								YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
										+ ExpectedHeader + "for New Finanacing popup",
								YesNo.No);
					}
					if (CommonLib.click(driver, BP.getNewFinancingPopupCancelIcon( 30), "New Fianacing Cancel Icon: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on New Fianacing cancel Icon", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to click on New Fianacing cancel Icon", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on New Fianacing cancel Icon" + " tab");
					}
					
				} else {
					log(LogStatus.ERROR, "Not able to Clicked on New Fianacing Icon ", YesNo.Yes);
					sa.assertTrue(false, "Not able to Clicked on New Fianacing Icon " + " tab");
				}

			} else {
				log(LogStatus.ERROR, "New Fianacing Icon not  present", YesNo.Yes);
				sa.assertTrue(false, "New Fianacing Icon not present");
			}
			refresh(driver);
			if (CommonLib.click(driver, BP.SourcedTab( 30,action.SCROLLANDBOOLEAN), "Source tab: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Source tab", YesNo.No);
			if(BP.NewSourcedDealIcon( 10) != null) {
				log(LogStatus.PASS, "New Deal Icon is present", YesNo.No);
				if (CommonLib.click(driver, BP.NewSourcedDealIcon( 30), "New Deal Icon: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on New Deal Icon", YesNo.No);
					String actualHeader = getText(driver, BP.getNewSourcedDealpopupheader(20), "NewSourcedDealpopupheader",
							action.SCROLLANDBOOLEAN);
					if (actualHeader.equals(ExpectedHeader)) {
						log(LogStatus.INFO,
								"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
										+ ExpectedHeader + " for New Finanacing popup",
								YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
										+ ExpectedHeader + "for New Finanacing popup",
								YesNo.No);
					}
					if (CommonLib.click(driver, BP.getNewFinancingPopupCrossIcon( 30), "New Fianacing Cross Icon: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on New Fianacing Cross Icon", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to click on New Fianacing Cross Icon", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on New Fianacing Cross Icon" + " tab");
					}
					
				} else {
					log(LogStatus.ERROR, "Clicked on New Fianacing Icon ", YesNo.Yes);
					sa.assertTrue(false, "Clicked on New Fianacing Icon " + " tab");
				}

			} else {
				log(LogStatus.ERROR, "New Fianacing Icon not  present", YesNo.Yes);
				sa.assertTrue(false, "New Fianacing Icon not present");
			}
			} else {
				log(LogStatus.FAIL, "Not able to Click on Source tab: " , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Source tab: " );
				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on Source tab: " , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Source tab: " );
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns2 + " tab");
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
public void ADETc108_VerfiytheFunctionalityofAddDealIconwhenFundsTabisSelectedatDealGridofPEFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String recordType = "";
	String dealName = ADEDeal31;
	String stage = ADEDeal31Stage;
	String dateReceived = "0";

	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns16, 30)) {
			if (CommonLib.click(driver, BP.SourcedTab( 30,action.SCROLLANDBOOLEAN), "Source tab: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Source tab", YesNo.No);
				ThreadSleep(1000);
			if (CommonLib.click(driver, dp.NewSourcedDealIcon(30), tabObj4 + "New Sourced Deal Icon", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on the New Sourced Deal Icon", YesNo.No);
		if (dp.createDealfromIcon(recordType, dealName, stage, "Date Received", tomorrowsDate)) {
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

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns16, 30)) {
                 log(LogStatus.INFO, "open created item" + ADEIns16, YesNo.No);
                 if (CommonLib.click(driver, BP.SourcedTab( 30,action.SCROLLANDBOOLEAN), "Source tab: " + "",
     					action.BOOLEAN)) {
				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
							if (BP.dealAcuityHSRName(dealName, stage, 30) != null) {
								log(LogStatus.PASS, "HSR: " + stage + " is present", YesNo.No);
								String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
								
								if (cp.verifyDate(todaysDate,null, actualDate)) {
									log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
								}
								else {
								log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
									sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
								}
							} else {
								log(LogStatus.FAIL, "HSR stage name not present: " + stage, YesNo.Yes);
								sa.assertTrue(false, "HSR stage name not present: " + stage);

							}
						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}
                 } else {
     				log(LogStatus.FAIL, "Not able to Click on Source tab: " , YesNo.Yes);
     				sa.assertTrue(false, "Not able to Click on Source tab: " );
     				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns2 + " tab");
		}
		} else {
			log(LogStatus.ERROR, "Not able to click on the New Sourced Deal Icon", YesNo.No);
			sa.assertTrue(false, "Not able to Click on New Sourced Deal Icon: " );
		}
			} else {
				log(LogStatus.FAIL, "Not able to Click on Source tab: " , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Source tab: " );
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
public void ADETc109_ChangeStageofDealandVerifytheImpactonHighestStageColumnatDealGrid(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String dealName = ADEDeal31;
	String stage = "Due Diligence";
	String dateReceived = todaysDate;
	
	if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEDeal31, 10)){
			if (fp.changeStage(projectName, Stage.Due_Diligence.toString(), 10)) {
			}else {
				sa.assertTrue(false,"not able to change stage to "+Stage.LOI);
				log(LogStatus.SKIP,"not able to change stage to "+Stage.LOI,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to find pipeline "+ADEDeal31);
			log(LogStatus.SKIP,"not able to find pipeline "+ADEDeal31,YesNo.Yes);
		}
	}else {
		sa.assertTrue(false,"not able to click on deal tab");
		log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
	}
			
	
if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
	log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

	if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns16, 30)) {

		log(LogStatus.INFO, "open created item" + ADEIns16, YesNo.No);
		if (CommonLib.click(driver, BP.SourcedTab( 30,action.SCROLLANDBOOLEAN), "Source tab: " + "",
				action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Source tab", YesNo.No);
			ThreadSleep(1000);
			if (BP.dealAcuityDealName(dealName, 30) != null) {
				log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
				if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
					log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
//					if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
//						log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
						if (BP.dealAcuityHSRName(dealName, stage, 30) != null) {
							log(LogStatus.PASS, "HSR: " + stage + " is present", YesNo.No);
							String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
							
							if (cp.verifyDate(todaysDate,null, actualDate)) {
								log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
							}
							else {
							log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
								sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
							}
						} else {
							log(LogStatus.FAIL, "HSR stage name not present: " + stage, YesNo.Yes);
							sa.assertTrue(false, "HSR stage name not present: " + stage);

						}
					} else {
						log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

					}
				} else {
					log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
					sa.assertTrue(false, "stage name not present: " + stage);

				}

//			} else {
//				log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
//				sa.assertTrue(false, "date receivednot present: " + dateReceived);
//			}
	} else {

		sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns16);
		log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns16, YesNo.Yes);
		}
	} else {
		log(LogStatus.FAIL, "Not able to Click on Source tab: " , YesNo.Yes);
		sa.assertTrue(false, "Not able to Click on Source tab: " );
		}
} else {
	log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
	sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
}

if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
	if (fp.clickOnAlreadyCreatedItem(projectName, ADEDeal31, 10)){
		if (fp.changeStage(projectName, Stage.Parked.toString(), 10)) {
		}else {
			sa.assertTrue(false,"not able to change stage to "+Stage.LOI);
			log(LogStatus.SKIP,"not able to change stage to "+Stage.LOI,YesNo.Yes);
		}
	}else {
		sa.assertTrue(false,"not able to find pipeline "+ADEDeal31);
		log(LogStatus.SKIP,"not able to find pipeline "+ADEDeal31,YesNo.Yes);
	}
}else {
	sa.assertTrue(false,"not able to click on deal tab");
	log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
}
		
String stage1 = "Parked";

if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns16, 30)) {
log(LogStatus.INFO, "open created item" + ADEIns16, YesNo.No);
ThreadSleep(1000);
if (CommonLib.click(driver, BP.SourcedTab( 30,action.SCROLLANDBOOLEAN), "Source tab: " + "",
		action.BOOLEAN)) {
	log(LogStatus.INFO, "Clicked on Source tab", YesNo.No);
	ThreadSleep(1000);
	if (BP.dealAcuityDealName(dealName, 30) != null) {
		log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
		if (BP.dealAcuityStageName(dealName, stage1, 30) != null) {
			log(LogStatus.PASS, "Stage Name: " + stage1 + " is present", YesNo.No);
//			if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
//				log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
				if (BP.dealAcuityHSRName(dealName, stage1, 30) != null) {
					log(LogStatus.PASS, "HSR: " + stage1 + " is present", YesNo.No);
					String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
					
					if (cp.verifyDate(todaysDate,null, actualDate)) {
						log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
					}
					else {
					log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
						sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
					}
				} else {
					log(LogStatus.FAIL, "HSR stage name not present: " + stage1, YesNo.Yes);
					sa.assertTrue(false, "HSR stage name not present: " + stage1);

				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

			}
		} else {
			log(LogStatus.FAIL, "stage name not present: " + stage1, YesNo.Yes);
			sa.assertTrue(false, "stage name not present: " + stage1);

		}

//	} else {
//		log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
//		sa.assertTrue(false, "date receivednot present: " + dateReceived);
//	}
} else {
	log(LogStatus.FAIL, "Not able to Click on Source tab: " , YesNo.Yes);
	sa.assertTrue(false, "Not able to Click on Source tab: " );
	}
} else {

	sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns16);
	log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns16, YesNo.Yes);

}
} else {
log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
}

if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
	if (fp.clickOnAlreadyCreatedItem(projectName, ADEDeal31, 10)){
		if (fp.changeStage(projectName, Stage.DeclinedDead.toString(), 10)) {
		}else {
			sa.assertTrue(false,"not able to change stage to "+Stage.LOI);
			log(LogStatus.SKIP,"not able to change stage to "+Stage.LOI,YesNo.Yes);
		}
	}else {
		sa.assertTrue(false,"not able to find pipeline "+ADEDeal22);
		log(LogStatus.SKIP,"not able to find pipeline "+ADEDeal22,YesNo.Yes);
	}
}else {
	sa.assertTrue(false,"not able to click on deal tab");
	log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
}
		
String stage2 = Stage.DeclinedDead.toString();

if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
  if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns16, 30)) {
    log(LogStatus.INFO, "open created item" + ADEIns16, YesNo.No);
    ThreadSleep(1000);
    if (CommonLib.click(driver, BP.SourcedTab( 30,action.SCROLLANDBOOLEAN), "Source tab: " + "",
			action.BOOLEAN)) {
		log(LogStatus.INFO, "Clicked on Source tab", YesNo.No);
		ThreadSleep(1000);
		if (BP.dealAcuityDealName(dealName, 30) != null) {
			log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
			if (BP.dealAcuityStageName(dealName, stage2, 30) != null) {
				log(LogStatus.PASS, "Stage Name: " + stage2 + " is present", YesNo.No);
//				if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
//					log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
					if (BP.dealAcuityHSRName(dealName, stage1, 30) != null) {
						log(LogStatus.PASS, "HSR: " + stage1 + " is present", YesNo.No);
						String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
						
						if (cp.verifyDate(todaysDate,null, actualDate)) {
							log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
						}
						else {
						log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
							sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
						}
					} else {
						log(LogStatus.FAIL, "HSR stage name not present: " + stage1, YesNo.Yes);
						sa.assertTrue(false, "HSR stage name not present: " + stage1);

					}
				} else {
					log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

				}
			} else {
				log(LogStatus.FAIL, "stage name not present: " + stage2, YesNo.Yes);
				sa.assertTrue(false, "stage name not present: " + stage2);

			}

//		} else {
//			log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
//			sa.assertTrue(false, "date receivednot present: " + dateReceived);
//		}
    } else {
		log(LogStatus.FAIL, "Not able to Click on Source tab: " , YesNo.Yes);
		sa.assertTrue(false, "Not able to Click on Source tab: " );
		}
} else {

	sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns16);
	log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns16, YesNo.Yes);

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
public void ADETc110_VerifyThatDealNamesClickableandDealRedirectionforAccounts(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String dealName = ADEDeal30;

	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns16, 30)) {
			if (BP.dealAcuityDealName(dealName, 30) != null) {
				log(LogStatus.PASS, "deal name: " + dealName + " is hyperlink and is present", YesNo.No);
				ThreadSleep(3000);
				if (doubleClickUsingAction(driver, BP.dealAcuityDealName(dealName, 10))) {
					log(LogStatus.PASS, "Clicked on deal name: " + dealName, YesNo.No);
				try {
					String parentWindowId = CommonLib.switchOnWindow(driver);
					if (!parentWindowId.isEmpty()) {
						log(LogStatus.PASS, "New Window Open after click on Deal Link: " + dealName, YesNo.No);
                          ThreadSleep(3000);
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
				log(LogStatus.FAIL, "deal name not present: " + dealName, YesNo.Yes);
				sa.assertTrue(false, "deal name not present: " + dealName);

			}
		
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns16 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns16 + " tab");
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
public void ADETc111_VerifyDealCountasZeroRedirectionContactatContactGridLenderFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String DealCountInFirm = "0";
	String actualDealCount = null;
	String contactName = ADEContact24FName + " " + ADEContact24LName;

	String ExpectedMsg = "No items to display.";
		
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns16, 30)) {

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
				if (CommonLib.click(driver, BP.contactDealCount(contactName, 30), "Deal Count: " + actualDealCount,
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Deal Count: " + actualDealCount + " of Record: " + contactName,
							YesNo.No);
					String parentWindowId = CommonLib.switchOnWindow(driver);
					if (!parentWindowId.isEmpty()) {
					String actualMsg = getText(driver, BP.getErrorMsg(20), "ErrorMsg", action.SCROLLANDBOOLEAN);
					/* if (ExpectedMsg.contains(actualMsg)) { */
						if (actualMsg.equals(ExpectedMsg)) {
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
						driver.switchTo().window(parentWindowId);
					} else {
						log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + contactName, YesNo.Yes);
						sa.assertTrue(false, "No New Window Open after click on Deal Link: " + contactName);
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + "contactDealCount" + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + "contactDealCount" + " tab");
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns5 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns5 + " tab");
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
public void ADETc112_VerifyDealCountRedirectionForContactatContactGridofLenderFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String DealCountInFirm = "1";
	String actualDealCount = null;
	String contactName = ADEContact5FName + " " + ADEContact5LName;
	
	String recordType = "";
	String dealName = ADEDeal26;
	String companyName = ADEDeal26CompanyName;
	String stage = ADEDeal26Stage;
	String dateReceived = todaysDate;
	
	String[][] data = { { PageLabel.Deal.toString(), dealName }, { PageLabel.Deal_Contact.toString(), contactName }};

	if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

		if (DTP.createDealTeam(projectName, dealName, data, TabName.Acuity.toString(), action.SCROLLANDBOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----", YesNo.No);

			log(LogStatus.INFO,
					"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
							+ contactName + " at Firm Tab under Acuity section---------",
					YesNo.No);
			WebElement ele = DTP.getDealTeamID(10);
		     if (ele != null) {
				String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
				ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name, "ADT_19",
						excelLabel.DealTeamID);
				log(LogStatus.INFO, "successfully created and noted id of DT" + id + " and deal name " + dealName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "could not create DT" + dealName);
				log(LogStatus.SKIP, "could not create DT" + dealName, YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns2 + " tab");
		}

	} else {
		log(LogStatus.ERROR, "Not able to click on " + TabName.Deal_Team + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + TabName.Deal_Team + " tab");
	}
		
			if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

				if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns5, 30)) {

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
						if (CommonLib.click(driver, BP.contactDealCount(contactName, 30), "Deal Count: " + actualDealCount,
								action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Deal Count: " + actualDealCount + " of Record: " + contactName,
									YesNo.No);
							String parentWindowId = CommonLib.switchOnWindow(driver);
							if (!parentWindowId.isEmpty()) {
								if (BP.dealAcuity2DealName(dealName, 30) != null) {
									log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
									if (BP.dealAcuity2StageName(dealName, stage, 30) != null) {
										log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
//										if (BP.dealAcuity2DateReceived(dealName, dateReceived, 30) != null) {
//											log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
											if (BP.dealAcuityPopUpCompanyName(dealName, companyName, 30) != null) {
												log(LogStatus.PASS, " Company name: " + companyName + " is present", YesNo.No);

										} else {
											log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
											sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

										}
//									} else {
//										log(LogStatus.FAIL, "date not mached: " + dateReceived, YesNo.Yes);
//										sa.assertTrue(false," date not mached: " + dateReceived);
//
//									}

								} else {
									log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
									sa.assertTrue(false, "date receivednot present: " + dateReceived);
								}
								} else {
									log(LogStatus.FAIL, "HSR stage name not present: " + stage, YesNo.Yes);
									sa.assertTrue(false, "HSR stage name not present: " + stage);

								}
							}
							driver.close();
							driver.switchTo().window(parentWindowId);
							
							} else {
								log(LogStatus.FAIL, "Clicked on Deal Count: " + actualDealCount + " of Record: " + contactName, YesNo.Yes);
								sa.assertTrue(false,  "Clicked on Deal Count: " + actualDealCount + " of Record: " + contactName);

							}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + ADEIns2 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + ADEIns2 + " tab");
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
public void ADETc113_VerifyDefaultSortingatDealSectionAICIAccountsisDecending(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);	
	if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
		log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
		
		if(fp.clickOnAlreadyCreatedItem(projectName, ADEIns16, 30)){
			
			log(LogStatus.INFO,"open created item"+ADEIns16,YesNo.No);
			List<WebElement> ele = BP.getsortingDateRecived(10);	
			if (CommonLib.checkSorting(driver, SortOrder.Decending, ele)) {
				log(LogStatus.PASS,
						"-----------Deal Column is in Descending Order By Default  --------------",
						YesNo.No);
				sa.assertTrue(true,
						"-----------Deal Column is in Descending Order By Default --------------");
                   }
		else {
				log(LogStatus.ERROR, "-----------Deal Column is not in Descending Order By Default  --------------", YesNo.Yes);
				sa.assertTrue(false, "-----------Deal Column is not in Descending Order By Default  --------------");
				}
			if (CommonLib.click(driver, BP.SourcedTab( 30,action.SCROLLANDBOOLEAN), "Source tab: " + "",
					action.BOOLEAN)) {
				List<WebElement> ele1 =BP.getsortingDateRecived(10);
				if (CommonLib.checkSorting(driver, SortOrder.Decending, ele1)) {
					log(LogStatus.PASS,
							"-----------Deal Column is in Descending Order By Default  --------------",
							YesNo.No);
					sa.assertTrue(true,
							"-----------Deal Column is in Descending Order By Default --------------");
	                   }
			else {
					log(LogStatus.ERROR, "-----------Deal Column is not in Descending Order By Default  --------------", YesNo.Yes);
					sa.assertTrue(false, "-----------Deal Column is not in Descending Order By Default  --------------");
					}
			} else {
				log(LogStatus.FAIL, "Not able to Click on Source tab: " , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Source tab: " );
				}
			} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns16 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns16 + " tab");
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
public void ADETc114_VerifySortingatDealSectionDateReceivedAICIPEAccountsisAecending(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String order = "Date Received";	
	if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
		log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
		
		if(fp.clickOnAlreadyCreatedItem(projectName, ADEIns16, 30)){
			
			log(LogStatus.INFO,"open created item"+ADEIns16,YesNo.No);
			if (CommonLib.click(driver, BP.DateReciviedSortingIcon( 30), "Date Recivied Sorting Icon: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "CDate Recivied Sorting Icon", YesNo.No);
				if (CommonLib.click(driver, BP.sortingorder(order, 30), "Date Recivied Sorting order: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on New Deal order", YesNo.No);
					List<WebElement> ele = BP.getsortingDateRecived(10);
			if (CommonLib.checkSorting(driver, SortOrder.Assecending, ele)) {
				log(LogStatus.PASS,
						"-----------deal Column is in Assecending Order By clicking on deal reacieved  --------------",
						YesNo.No);
				sa.assertTrue(true,
						"-----------deal Name Column is in Assecending Order By clicking on deal reacieved --------------");

			}
			else {
				log(LogStatus.ERROR, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------", YesNo.Yes);
				sa.assertTrue(false, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------");
				}
			
				} else {
					log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting order", YesNo.No);
					sa.assertTrue(false, "Not able to Click on Date Recivied Sorting order: " );
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting Icon", YesNo.No);
				sa.assertTrue(false, "Not able to Click on Date Recivied Sorting Icon: " );
			}
			if (CommonLib.click(driver, BP.SourcedTab( 30,action.SCROLLANDBOOLEAN), "Source tab: " + "",
					action.BOOLEAN)) {
				if (CommonLib.click(driver, BP.DateReciviedSortingIcon( 30), "Date Recivied Sorting Icon: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "CDate Recivied Sorting Icon", YesNo.No);
					if (CommonLib.click(driver, BP.sortingorder(order, 30), "Date Recivied Sorting order: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on New Deal order", YesNo.No);
						List<WebElement> ele = BP.getsortingDateRecived(10);
				if (CommonLib.checkSorting(driver, SortOrder.Assecending, ele)) {
					log(LogStatus.PASS,
							"-----------deal Column is in Assecending Order By clicking on deal reacieved  --------------",
							YesNo.No);
					sa.assertTrue(true,
							"-----------deal Name Column is in Assecending Order By clicking on deal reacieved --------------");

				}
				else {
					log(LogStatus.ERROR, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------", YesNo.Yes);
					sa.assertTrue(false, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------");
					}
					} else {
						log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting order", YesNo.No);
						sa.assertTrue(false, "Not able to Click on Date Recivied Sorting order: " );
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting Icon", YesNo.No);
					sa.assertTrue(false, "Not able to Click on Date Recivied Sorting Icon: " );
				}
				} else {
					log(LogStatus.FAIL, "Not able to Click on Source tab: " , YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on Source tab: " );
					}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns16 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns16 + " tab");
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
public void ADETc115_VerifySortingatDealSectionStageColumeAICIPEAccountsisDecending(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String order = "Stage";
	if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
		log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
		
		if(fp.clickOnAlreadyCreatedItem(projectName, ADEIns16, 30)){
			
			log(LogStatus.INFO,"open created item"+ADEIns16,YesNo.No);
			if (CommonLib.click(driver, BP.DateReciviedSortingIcon( 30), "Date Recivied Sorting Icon: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Date Recivied Sorting Icon", YesNo.No);
				if (CommonLib.click(driver, BP.sortingorder(order, 30), "Date Recivied Sorting order: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on New Deal order", YesNo.No);
//			 		if (CommonLib.click(driver, BP.DateReciviedSortingIcon( 30), "Date Recivied Sorting Icon: " + "",
//							action.BOOLEAN)) {
//						log(LogStatus.INFO, "Date Recivied Sorting Icon", YesNo.No);
//						if (CommonLib.click(driver, BP.sortingorder(order, 30), "Date Recivied Sorting order: " + "",
//								action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on New Deal order", YesNo.No);
					List<WebElement> ele = BP.getsortingStage(10);
			if (CommonLib.checkStageSorting(driver, true, ele)) {
				log(LogStatus.PASS,
						"-----------deal Column is in Decending Order By clicking on stage  --------------",
						YesNo.No);
				sa.assertTrue(true,
						"-----------deal Name Column is in Decending Order By clicking on stage --------------");

			}
			else {
				log(LogStatus.ERROR, "-----------deal Name Column is not Decending Order By clicking on stage  --------------", YesNo.Yes);
				sa.assertTrue(false, "-----------deal Name Column is not Decending Order By clicking on stage  --------------");
				}
				} else {
					log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting order", YesNo.No);
					sa.assertTrue(false, "Not able to Click on Date Recivied Sorting order: " );
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting Icon", YesNo.No);
				sa.assertTrue(false, "Not able to Click on Date Recivied Sorting Icon: " );
			}
			if (CommonLib.click(driver, BP.SourcedTab( 30,action.SCROLLANDBOOLEAN), "Source tab: " + "",
					action.BOOLEAN)) {
			if (CommonLib.click(driver, BP.DateReciviedSortingIcon( 30), "Date Recivied Sorting Icon: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Date Recivied Sorting Icon", YesNo.No);
				if (CommonLib.click(driver, BP.sortingorder(order, 30), "Date Recivied Sorting order: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on New Deal order", YesNo.No);
					List<WebElement> ele1 =BP.getsortingStage(10);
					if (CommonLib.checkStageSorting(driver, true, ele1)) {
				log(LogStatus.PASS,
						"-----------deal Column is in Decending Order By clicking on stage  --------------",
						YesNo.No);
				sa.assertTrue(true,
						"-----------deal Name Column is in Decending Order By clicking on stage --------------");

			}
			else {
				log(LogStatus.ERROR, "-----------deal Name Column is not Decending Order By clicking on stage  --------------", YesNo.Yes);
				sa.assertTrue(false, "-----------deal Name Column is not Decending Order By clicking on stage  --------------");
				}
				} else {
					log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting order", YesNo.No);
					sa.assertTrue(false, "Not able to Click on Date Recivied Sorting order: " );
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting Icon", YesNo.No);
				sa.assertTrue(false, "Not able to Click on Date Recivied Sorting Icon: " );
			}
			} else {
				log(LogStatus.FAIL, "Not able to Click on Source tab: " , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Source tab: " );
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns16 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns16 + " tab");
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
public void ADETc116_VerifySortingatDealSectionStageAICIPEAccountsisAecending(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String order = "Stage";
	if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
		log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
		
		if(fp.clickOnAlreadyCreatedItem(projectName, ADEIns16, 30)){
			
			log(LogStatus.INFO,"open created item"+ADEIns16,YesNo.No);
			if (CommonLib.click(driver, BP.DateReciviedSortingIcon( 30), "Date Recivied Sorting Icon: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Date Recivied Sorting Icon", YesNo.No);
				if (CommonLib.click(driver, BP.sortingorder(order, 30), "Date Recivied Sorting order: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on New Deal order", YesNo.No);
					if (CommonLib.click(driver, BP.DateReciviedSortingIcon( 30), "Date Recivied Sorting Icon: " + "",
							action.BOOLEAN)) {
				log(LogStatus.INFO, "Date Recivied Sorting Icon", YesNo.No);
						if (CommonLib.click(driver, BP.sortingorder(order, 30), "Date Recivied Sorting order: " + "",
								action.BOOLEAN)) {
							List<WebElement> ele =BP.getsortingStage(10);
							if (CommonLib.checkStageSorting(driver, false, ele)) {
				log(LogStatus.PASS,
						"-----------deal Column is in Assecending Order By clicking on stage  --------------",
						YesNo.No);
				sa.assertTrue(true,
						"-----------deal Name Column is in Assecending Order By clicking on stage --------------");

			}
			else {
				log(LogStatus.ERROR, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------", YesNo.Yes);
				sa.assertTrue(false, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------");
				}
				} else {
					log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting order", YesNo.No);
					sa.assertTrue(false, "Not able to Click on Date Recivied Sorting order: " );
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting Icon", YesNo.No);
				sa.assertTrue(false, "Not able to Click on Date Recivied Sorting Icon: " );
			}
				}
			}
			if (CommonLib.click(driver, BP.SourcedTab( 30,action.SCROLLANDBOOLEAN), "Source tab: " + "",
					action.BOOLEAN)) {
			if (CommonLib.click(driver, BP.DateReciviedSortingIcon( 30), "Date Recivied Sorting Icon: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Date Recivied Sorting Icon", YesNo.No);
				if (CommonLib.click(driver, BP.sortingorder(order, 30), "Date Recivied Sorting order: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on New Deal order", YesNo.No);
					if (CommonLib.click(driver, BP.DateReciviedSortingIcon( 30), "Date Recivied Sorting Icon: " + "",
							action.BOOLEAN)) {
				log(LogStatus.INFO, "Date Recivied Sorting Icon", YesNo.No);
						if (CommonLib.click(driver, BP.sortingorder(order, 30), "Date Recivied Sorting order: " + "",
								action.BOOLEAN)) {
							List<WebElement> ele =BP.getsortingStage(10);
							if (CommonLib.checkStageSorting(driver, false, ele)) {
				log(LogStatus.PASS,
						"-----------deal Column is in Assecending Order By clicking on stage  --------------",
						YesNo.No);
				sa.assertTrue(true,
						"-----------deal Name Column is in Assecending Order By clicking on stage --------------");

			}
			else {
				log(LogStatus.ERROR, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------", YesNo.Yes);
				sa.assertTrue(false, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------");
				}
				} else {
					log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting order", YesNo.No);
					sa.assertTrue(false, "Not able to Click on Date Recivied Sorting order: " );
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting Icon", YesNo.No);
				sa.assertTrue(false, "Not able to Click on Date Recivied Sorting Icon: " );
			}
			} else {
				log(LogStatus.FAIL, "Not able to Click on Source tab: " , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Source tab: " );
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns16 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns16 + " tab");
		}

	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
		}
	}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName"})
@Test
public void ADETc117_1_VerifySortingatDealSectionDateReceivedForCompanyRecord(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String order = "Date Received";
	if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
		log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
		
		if(fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)){
			
			log(LogStatus.INFO,"open created item"+ADEIns1,YesNo.No);
					List<WebElement> ele =BP.getsortingDateRecived(10);
			if (CommonLib.checkSorting(driver, SortOrder.Decending, ele)) {
				log(LogStatus.PASS,
						"-----------deal Column is in Decending Order By clicking on deal reacieved  --------------",
						YesNo.No);
				sa.assertTrue(true,
						"-----------deal Name Column is in Decending Order By clicking on deal reacieved --------------");

			}
			else {
				log(LogStatus.ERROR, "-----------deal Name Column is not Decending Order By clicking on deal reacieved  --------------", YesNo.Yes);
				sa.assertTrue(false, "-----------deal Name Column is not Decending Order By clicking on deal reacieved  --------------");
			}
				if (CommonLib.click(driver, BP.DateReciviedSortingIcon( 30), "Date Recivied Sorting Icon: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Date Recivied Sorting Icon", YesNo.No);
					if (CommonLib.click(driver, BP.sortingorder(order, 30), "Date Recivied Sorting order: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on New Deal order", YesNo.No);
						List<WebElement> ele1 = BP.getsortingDateRecived(10);
				if (CommonLib.checkSorting(driver, SortOrder.Assecending, ele1)) {
					log(LogStatus.PASS,
							"-----------deal Column is in Assecending Order By clicking on deal reacieved  --------------",
							YesNo.No);
					sa.assertTrue(true,
							"-----------deal Name Column is in Assecending Order By clicking on deal reacieved --------------");

				}
				else {
					log(LogStatus.ERROR, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------", YesNo.Yes);
					sa.assertTrue(false, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------");
					}
					} else {
						log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting order", YesNo.No);
						sa.assertTrue(false, "Not able to Click on Date Recivied Sorting order: " );
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting Icon", YesNo.No);
					sa.assertTrue(false, "Not able to Click on Date Recivied Sorting Icon: " );
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns16 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns16 + " tab");
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
public void ADETc117_2_VerifySortingatDealSectionStageForCompanyRecord(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String order = "Stage";
	if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
		log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
		
		if(fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)){
			
			log(LogStatus.INFO,"open created item"+ADEIns1,YesNo.No);
			if (CommonLib.click(driver, BP.DateReciviedSortingIcon( 30), "Date Recivied Sorting Icon: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Date Recivied Sorting Icon", YesNo.No);
				if (CommonLib.click(driver, BP.sortingorder(order, 30), "Date Recivied Sorting order: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on New Deal order", YesNo.No);
//					if (CommonLib.click(driver, BP.DateReciviedSortingIcon( 30), "Date Recivied Sorting Icon: " + "",
//							action.BOOLEAN)) {
//				log(LogStatus.INFO, "Date Recivied Sorting Icon", YesNo.No);
//						if (CommonLib.click(driver, BP.sortingorder(order, 30), "Date Recivied Sorting order: " + "",
//								action.BOOLEAN)) {
							List<WebElement> ele =BP.getsortingStage(10);
							if (CommonLib.checkStageSorting(driver, true, ele)) {
				log(LogStatus.PASS,
						"-----------deal Column is in Decending Order By clicking on stage  --------------",
						YesNo.No);
				sa.assertTrue(true,
						"-----------deal Name Column is in Decending Order By clicking on stage --------------");

			}
			else {
				log(LogStatus.ERROR, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------", YesNo.Yes);
				sa.assertTrue(false, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------");
				}
				} else {
					log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting order", YesNo.No);
					sa.assertTrue(false, "Not able to Click on Date Recivied Sorting order: " );
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting Icon", YesNo.No);
				sa.assertTrue(false, "Not able to Click on Date Recivied Sorting Icon: " );
			}
				
			
			if (CommonLib.click(driver, BP.DateReciviedSortingIcon( 30), "Date Recivied Sorting Icon: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Date Recivied Sorting Icon", YesNo.No);
				if (CommonLib.click(driver, BP.sortingorder(order, 30), "Date Recivied Sorting order: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on New Deal order", YesNo.No);
							List<WebElement> ele =BP.getsortingStage(10);
							if (CommonLib.checkStageSorting(driver, false, ele)) {
				log(LogStatus.PASS,
						"-----------deal Column is in Assecending Order By clicking on stage  --------------",
						YesNo.No);
				sa.assertTrue(true,
						"-----------deal Name Column is in Assecending Order By clicking on stage --------------");

			}
			else {
				log(LogStatus.ERROR, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------", YesNo.Yes);
				sa.assertTrue(false, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------");
				}
				} else {
					log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting order", YesNo.No);
					sa.assertTrue(false, "Not able to Click on Date Recivied Sorting order: " );
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting Icon", YesNo.No);
				sa.assertTrue(false, "Not able to Click on Date Recivied Sorting Icon: " );
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns16 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns16 + " tab");
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
public void ADETc118_1_VerifySortingatDealSectionDateReceivedForIntermediaryRecord(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String order = "Date Received";	
	if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
		log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
		
		if(fp.clickOnAlreadyCreatedItem(projectName, ADEIns4, 30)){
			
			log(LogStatus.INFO,"open created item"+ADEIns4,YesNo.No);
					List<WebElement> ele =BP.getsortingDateRecived(10);
			if (CommonLib.checkSorting(driver, SortOrder.Decending, ele)) {
				log(LogStatus.PASS,
						"-----------deal Column is in Decending Order By clicking on deal reacieved  --------------",
						YesNo.No);
				sa.assertTrue(true,
						"-----------deal Name Column is in Decending Order By clicking on deal reacieved --------------");

			}
			else {
				log(LogStatus.ERROR, "-----------deal Name Column is not Decending Order By clicking on deal reacieved  --------------", YesNo.Yes);
				sa.assertTrue(false, "-----------deal Name Column is not Decending Order By clicking on deal reacieved  --------------");
			}
				if (CommonLib.click(driver, BP.DateReciviedSortingIcon( 30), "Date Recivied Sorting Icon: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Date Recivied Sorting Icon", YesNo.No);
					if (CommonLib.click(driver, BP.sortingorder(order, 30), "Date Recivied Sorting order: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on New Deal order", YesNo.No);
						List<WebElement> ele1 =BP.getsortingDateRecived(10);
				if (CommonLib.checkSorting(driver, SortOrder.Assecending, ele1)) {
					log(LogStatus.PASS,
							"-----------deal Column is in Assecending Order By clicking on deal reacieved  --------------",
							YesNo.No);
					sa.assertTrue(true,
							"-----------deal Name Column is in Assecending Order By clicking on deal reacieved --------------");

				}
				else {
					log(LogStatus.ERROR, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------", YesNo.Yes);
					sa.assertTrue(false, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------");
					}
					} else {
						log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting order", YesNo.No);
						sa.assertTrue(false, "Not able to Click on Date Recivied Sorting order: " );
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting Icon", YesNo.No);
					sa.assertTrue(false, "Not able to Click on Date Recivied Sorting Icon: " );
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns16 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns16 + " tab");
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
public void ADETc118_2_VerifySortingatDealSectionStageForIntermediateryRecord(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String order = "Stage";
	if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
		log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
		
		if(fp.clickOnAlreadyCreatedItem(projectName, ADEIns4, 30)){
			
			log(LogStatus.INFO,"open created item"+ADEIns4,YesNo.No);
			if (CommonLib.click(driver, BP.DateReciviedSortingIcon( 30), "Date Recivied Sorting Icon: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Date Recivied Sorting Icon", YesNo.No);
				if (CommonLib.click(driver, BP.sortingorder(order, 30), "Date Recivied Sorting order: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on New Deal order", YesNo.No);
//					if (CommonLib.click(driver, BP.DateReciviedSortingIcon( 30), "Date Recivied Sorting Icon: " + "",
//							action.BOOLEAN)) {
//				log(LogStatus.INFO, "Date Recivied Sorting Icon", YesNo.No);
//						if (CommonLib.click(driver, BP.sortingorder(order, 30), "Date Recivied Sorting order: " + "",
//								action.BOOLEAN)) {
							List<WebElement> ele =BP.getsortingStage(10);
							if (CommonLib.checkStageSorting(driver, true, ele)) {
				log(LogStatus.PASS,
						"-----------deal Column is in Decending Order By clicking on stage  --------------",
						YesNo.No);
				sa.assertTrue(true,
						"-----------deal Name Column is in Decending Order By clicking on stage --------------");

			}
			else {
				log(LogStatus.ERROR, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------", YesNo.Yes);
				sa.assertTrue(false, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------");
				}
				} else {
					log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting order", YesNo.No);
					sa.assertTrue(false, "Not able to Click on Date Recivied Sorting order: " );
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting Icon", YesNo.No);
				sa.assertTrue(false, "Not able to Click on Date Recivied Sorting Icon: " );
			}
				
			
			if (CommonLib.click(driver, BP.DateReciviedSortingIcon( 30), "Date Recivied Sorting Icon: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Date Recivied Sorting Icon", YesNo.No);
				if (CommonLib.click(driver, BP.sortingorder(order, 30), "Date Recivied Sorting order: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on New Deal order", YesNo.No);
							List<WebElement> ele =BP.getsortingStage(10);
							if (CommonLib.checkStageSorting(driver, false, ele)) {
				log(LogStatus.PASS,
						"-----------deal Column is in Assecending Order By clicking on stage  --------------",
						YesNo.No);
				sa.assertTrue(true,
						"-----------deal Name Column is in Assecending Order By clicking on stage --------------");

			}
			else {
				log(LogStatus.ERROR, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------", YesNo.Yes);
				sa.assertTrue(false, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------");
				}
				} else {
					log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting order", YesNo.No);
					sa.assertTrue(false, "Not able to Click on Date Recivied Sorting order: " );
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting Icon", YesNo.No);
				sa.assertTrue(false, "Not able to Click on Date Recivied Sorting Icon: " );
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns16 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns16 + " tab");
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
public void ADETc119_1_VerifySortingatDealSectionDateReceivedForPCRecord(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String order = "Date Received";
	if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
		log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
		
		if(fp.clickOnAlreadyCreatedItem(projectName, ADEIns7, 30)){
			
			log(LogStatus.INFO,"open created item"+ADEIns7,YesNo.No);
					List<WebElement> ele =BP.getsortingDateRecived(10);
			if (CommonLib.checkSorting(driver, SortOrder.Decending, ele)) {
				log(LogStatus.PASS,
						"-----------deal Column is in Decending Order By clicking on deal reacieved  --------------",
						YesNo.No);
				sa.assertTrue(true,
						"-----------deal Name Column is in Decending Order By clicking on deal reacieved --------------");

			}
			else {
				log(LogStatus.ERROR, "-----------deal Name Column is not Decending Order By clicking on deal reacieved  --------------", YesNo.Yes);
				sa.assertTrue(false, "-----------deal Name Column is not Decending Order By clicking on deal reacieved  --------------");
			}
				if (CommonLib.click(driver, BP.DateReciviedSortingIcon( 30), "Date Recivied Sorting Icon: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Date Recivied Sorting Icon", YesNo.No);
					if (CommonLib.click(driver, BP.sortingorder(order, 30), "Date Recivied Sorting order: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on New Deal order", YesNo.No);
						List<WebElement> ele1 =BP.getsortingDateRecived(10);
				if (CommonLib.checkSorting(driver, SortOrder.Assecending, ele1)) {
					log(LogStatus.PASS,
							"-----------deal Column is in Assecending Order By clicking on deal reacieved  --------------",
							YesNo.No);
					sa.assertTrue(true,
							"-----------deal Name Column is in Assecending Order By clicking on deal reacieved --------------");

				}
				else {
					log(LogStatus.ERROR, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------", YesNo.Yes);
					sa.assertTrue(false, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------");
					}
					} else {
						log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting order", YesNo.No);
						sa.assertTrue(false, "Not able to Click on Date Recivied Sorting order: " );
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting Icon", YesNo.No);
					sa.assertTrue(false, "Not able to Click on Date Recivied Sorting Icon: " );
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns16 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns16 + " tab");
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
public void ADETc119_2_VerifySortingatDealSectionStageForPCRecord(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String order = "Stage";
	if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
		log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
		
		if(fp.clickOnAlreadyCreatedItem(projectName, ADEIns4, 30)){
			
			log(LogStatus.INFO,"open created item"+ADEIns4,YesNo.No);
			if (CommonLib.click(driver, BP.DateReciviedSortingIcon( 30), "Date Recivied Sorting Icon: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Date Recivied Sorting Icon", YesNo.No);
				if (CommonLib.click(driver, BP.sortingorder(order, 30), "Date Recivied Sorting order: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on New Deal order", YesNo.No);
//					if (CommonLib.click(driver, BP.DateReciviedSortingIcon( 30), "Date Recivied Sorting Icon: " + "",
//							action.BOOLEAN)) {
//				log(LogStatus.INFO, "Date Recivied Sorting Icon", YesNo.No);
//						if (CommonLib.click(driver, BP.sortingorder(order, 30), "Date Recivied Sorting order: " + "",
//								action.BOOLEAN)) {
							List<WebElement> ele = BP.getsortingStage(10);
							if (CommonLib.checkStageSorting(driver, true, ele)) {
				log(LogStatus.PASS,
						"-----------deal Column is in Decending Order By clicking on stage  --------------",
						YesNo.No);
				sa.assertTrue(true,
						"-----------deal Name Column is in Decending Order By clicking on stage --------------");

			}
			else {
				log(LogStatus.ERROR, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------", YesNo.Yes);
				sa.assertTrue(false, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------");
				}
				} else {
					log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting order", YesNo.No);
					sa.assertTrue(false, "Not able to Click on Date Recivied Sorting order: " );
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting Icon", YesNo.No);
				sa.assertTrue(false, "Not able to Click on Date Recivied Sorting Icon: " );
			}
				
			
			if (CommonLib.click(driver, BP.DateReciviedSortingIcon( 30), "Date Recivied Sorting Icon: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Date Recivied Sorting Icon", YesNo.No);
				if (CommonLib.click(driver, BP.sortingorder(order, 30), "Date Recivied Sorting order: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on New Deal order", YesNo.No);
							List<WebElement> ele =BP.getsortingStage(10);
							if (CommonLib.checkStageSorting(driver, false, ele)) {
				log(LogStatus.PASS,
						"-----------deal Column is in Assecending Order By clicking on stage  --------------",
						YesNo.No);
				sa.assertTrue(true,
						"-----------deal Name Column is in Assecending Order By clicking on stage --------------");

			}
			else {
				log(LogStatus.ERROR, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------", YesNo.Yes);
				sa.assertTrue(false, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------");
				}
				} else {
					log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting order", YesNo.No);
					sa.assertTrue(false, "Not able to Click on Date Recivied Sorting order: " );
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting Icon", YesNo.No);
				sa.assertTrue(false, "Not able to Click on Date Recivied Sorting Icon: " );
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns16 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns16 + " tab");
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
public void ADETc120_1_VerifySortingatDealSectionDateReceivedForInstitutionRecord(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String order = "Stage";	
	if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
		log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
		
		if(fp.clickOnAlreadyCreatedItem(projectName, ADEIns3, 30)){
			
			log(LogStatus.INFO,"open created item"+ADEIns3,YesNo.No);
			refresh(driver);
			ThreadSleep(5000);
					List<WebElement> ele =BP.getsortingStage1(10);
					if (CommonLib.checkFundraisingStageSorting(driver, true, ele)) {
				log(LogStatus.PASS,
						"-----------deal Column is in Decending Order By clicking on deal reacieved  --------------",
						YesNo.No);
				sa.assertTrue(true,
						"-----------deal Name Column is in Decending Order By clicking on deal reacieved --------------");

			}
			else {
				log(LogStatus.ERROR, "-----------deal Name Column is not Decending Order By clicking on deal reacieved  --------------", YesNo.Yes);
				sa.assertTrue(false, "-----------deal Name Column is not Decending Order By clicking on deal reacieved  --------------");
			}
				if (CommonLib.click(driver, BP.DateReciviedSortingIcon( 30), "Date Recivied Sorting Icon: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Date Recivied Sorting Icon", YesNo.No);
					if (CommonLib.click(driver, BP.sortingorder(order, 30), "Date Recivied Sorting order: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on New Deal order", YesNo.No);
						List<WebElement> ele1 =BP.getsortingStage1(10);
						if (CommonLib.checkFundraisingStageSorting(driver, false, ele1)) {
					log(LogStatus.PASS,
							"-----------deal Column is in Assecending Order By clicking on deal reacieved  --------------",
							YesNo.No);
					sa.assertTrue(false,
							"-----------deal Name Column is in Assecending Order By clicking on deal reacieved --------------");

				}
				else {
					log(LogStatus.ERROR, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------", YesNo.Yes);
					sa.assertTrue(false, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------");
					}
					} else {
						log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting order", YesNo.No);
						sa.assertTrue(false, "Not able to Click on Date Recivied Sorting order: " );
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting Icon", YesNo.No);
					sa.assertTrue(false, "Not able to Click on Date Recivied Sorting Icon: " );
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns16 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns16 + " tab");
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
public void ADETc120_2_VerifySortingatDealSectionStageForInstitutionRecord(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String order = "Target Close Date";
	if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
		log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
		
		if(fp.clickOnAlreadyCreatedItem(projectName, ADEIns3, 30)){
			
			log(LogStatus.INFO,"open created item"+ADEIns3,YesNo.No);
			if (CommonLib.click(driver, BP.DateReciviedSortingIcon( 30), "Date Recivied Sorting Icon: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Date Recivied Sorting Icon", YesNo.No);
				if (CommonLib.click(driver, BP.sortingorder(order, 30), "Date Recivied Sorting order: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on New Deal order", YesNo.No);
//					if (CommonLib.click(driver, BP.DateReciviedSortingIcon( 30), "Date Recivied Sorting Icon: " + "",
//							action.BOOLEAN)) {
//				log(LogStatus.INFO, "Date Recivied Sorting Icon", YesNo.No);
//						if (CommonLib.click(driver, BP.sortingorder(order, 30), "Date Recivied Sorting order: " + "",
//								action.BOOLEAN)) {
							List<WebElement> ele = BP.getsortintTCD(10);
							
								if (CommonLib.checkSorting(driver, SortOrder.Decending, ele)) {
				log(LogStatus.PASS,
						"-----------deal Column is in Decending Order By clicking on stage  --------------",
						YesNo.No);
				sa.assertTrue(true,
						"-----------deal Name Column is in Decending Order By clicking on stage --------------");

			}
			else {
				log(LogStatus.ERROR, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------", YesNo.Yes);
				sa.assertTrue(false, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------");
				}
				} else {
					log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting order", YesNo.No);
					sa.assertTrue(false, "Not able to Click on Date Recivied Sorting order: " );
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting Icon", YesNo.No);
				sa.assertTrue(false, "Not able to Click on Date Recivied Sorting Icon: " );
			}
				
			
			if (CommonLib.click(driver, BP.DateReciviedSortingIcon( 30), "Date Recivied Sorting Icon: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Date Recivied Sorting Icon", YesNo.No);
				if (CommonLib.click(driver, BP.sortingorder(order, 30), "Date Recivied Sorting order: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on New Deal order", YesNo.No);
							List<WebElement> ele =BP.getsortintTCD(10);
						
								if (CommonLib.checkSorting(driver, SortOrder.Assecending, ele))
							
				log(LogStatus.PASS,
						"-----------deal Column is in Assecending Order By clicking on stage  --------------",
						YesNo.No);
				sa.assertTrue(true,
						"-----------deal Name Column is in Assecending Order By clicking on stage --------------");

			}
			else {
				log(LogStatus.ERROR, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------", YesNo.Yes);
				sa.assertTrue(false, "-----------deal Name Column is not Assecending Order By clicking on deal reacieved  --------------");
				}
				} else {
					log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting order", YesNo.No);
					sa.assertTrue(false, "Not able to Click on Date Recivied Sorting order: " );
				}
//			} else {
//				log(LogStatus.ERROR, "Not able to click on the Date Recivied Sorting Icon", YesNo.No);
//				sa.assertTrue(false, "Not able to Click on Date Recivied Sorting Icon: " );
//			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns16 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns16 + " tab");
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
public void ADETc121_1_RenameStageNamesCheckImpactDealSectionAccountContact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
	String parentID = null;
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	String[] newAndOldStage = 
			{ Stage.IOI.toString(), Stage.IOL.toString()  };
	if (home.clickOnSetUpLink()) {
		parentID = switchOnWindow(driver);
		if (parentID != null) {
			if (sp.searchStandardOrCustomObject(environment, mode, object.Deal)) {
				if (sp.clickOnObjectFeature(environment, mode, object.Deal,
						ObjectFeatureName.FieldAndRelationShip)) {
					if (sendKeys(driver, sp.getsearchTextboxFieldsAndRelationships(10),
							excelLabel.Stage.toString() + Keys.ENTER, "status", action.BOOLEAN)) {

						if (sp.clickOnAlreadyCreatedLayout(excelLabel.Stage.toString())) {
//							for (int i = 0; i < newAndOldStage.length; i++) {
								switchToDefaultContent(driver);
								switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
								WebElement ele = sp.clickOnEditInFrontOfFieldValues(projectName,
										newAndOldStage[0]);
								if (click(driver, ele, "watchlist", action.BOOLEAN)) {
									switchToDefaultContent(driver);
									ThreadSleep(3000);
									switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
									sendKeys(driver, sp.getFieldLabelTextBox1(10), newAndOldStage[1], "label",
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
//							}
							ThreadSleep(3000);
							switchToDefaultContent(driver);
							switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
							WebElement ele1 = sp.clickOnEditInFrontOfFieldValues(projectName, newAndOldStage[0]);
							WebElement ele2 = null;
							ele2 = sp.clickOnEditInFrontOfFieldValues(projectName, newAndOldStage[1]);
							if ((ele1 != null) && (ele2 != null)) {
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
public void ADETc121_2_RenameStageNamesCheckImpactDealSectionAccountContact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns2, 30)) {
			log(LogStatus.INFO, "open created item" + ADEIns2, YesNo.No);

			String dealname = ADEDeal21;
			String stage = "IOL";

			if (BP.dealAcuityStageName(dealname, stage, 30) != null) {
				log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);

			} else {
				log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
				sa.assertTrue(false, "stage name not present: " + stage);

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

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns5, 30)) {
			log(LogStatus.INFO, "open created item" + ADEIns5, YesNo.No);
			String dealname1 = ADEDeal25;
			String stage1 = "IOL";

			if (BP.dealAcuityStageName(dealname1, stage1, 30) != null) {
				log(LogStatus.PASS, "Stage Name: " + stage1 + " is present", YesNo.No);

			} else {
				log(LogStatus.FAIL, "stage name not present: " + stage1, YesNo.Yes);
				sa.assertTrue(false, "stage name not present: " + stage1);

			}
		} else {

			sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns5);
			log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns5, YesNo.Yes);
		}

	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns16, 30)) {
			log(LogStatus.INFO, "open created item" + ADEIns16, YesNo.No);
			String dealname1 = ADEDeal30;
			String stage1 = "IOL";

			if (BP.dealAcuityStageName(dealname1, stage1, 30) != null) {
				log(LogStatus.PASS, "Stage Name: " + stage1 + " is present", YesNo.No);

			} else {
				log(LogStatus.FAIL, "stage name not present: " + stage1, YesNo.Yes);
				sa.assertTrue(false, "stage name not present: " + stage1);

			}
		} else {

			sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns16);
			log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns16, YesNo.Yes);
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
public void ADETc122_1_CreateNewLabelOfStagesAndDeleteExistingStage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	WebElement ele = null;
	String parentID = null;
	String stage = "DS";
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
							WebElement ele1 = dp.findDeleteLink(projectName, Stage.IOL.toString());
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
public void ADETc122_2_EditExistingCompanyDealVerifyimpactDealCompanyAccount(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns2, 30)) {
			log(LogStatus.INFO, "open created item" + ADEIns2, YesNo.No);

			String dealname = ADEDeal21;
			String stage = "DS";

			if (BP.dealAcuityStageName(dealname, stage, 30) != null) {
				log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);

			} else {
				log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
				sa.assertTrue(false, "stage name not present: " + stage);

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

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns5, 30)) {
			log(LogStatus.INFO, "open created item" + ADEIns5, YesNo.No);
			String dealname1 = ADEDeal25;
			String stage1 = "DS";

			if (BP.dealAcuityStageName(dealname1, stage1, 30) != null) {
				log(LogStatus.PASS, "Stage Name: " + stage1 + " is present", YesNo.No);

			} else {
				log(LogStatus.FAIL, "stage name not present: " + stage1, YesNo.Yes);
				sa.assertTrue(false, "stage name not present: " + stage1);

			}
		} else {

			sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns5);
			log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns5, YesNo.Yes);
		}

	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns16, 30)) {
			log(LogStatus.INFO, "open created item" + ADEIns16, YesNo.No);
			String dealname1 = ADEDeal30;
			String stage1 = "DS";

			if (BP.dealAcuityStageName(dealname1, stage1, 30) != null) {
				log(LogStatus.PASS, "Stage Name: " + stage1 + " is present", YesNo.No);

			} else {
				log(LogStatus.FAIL, "stage name not present: " + stage1, YesNo.Yes);
				sa.assertTrue(false, "stage name not present: " + stage1);

			}
		} else {

			sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns16);
			log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns16, YesNo.Yes);
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
public void ADETc123_1_VerifytheImpactonSubjectLinewhenBaseUrlgetsChangedforDealGridAPIForAccounts(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	String fieldName = ADEFieldname1;
	String valueField =ADENewValue1;
	String fieldName1 = ADEFieldname2;
	String valueField1 =ADENewValue2;
	String fieldName2 = ADEFieldname3;
	String valueField2 =ADENewValue3;
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
 	if(setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), fieldName1, valueField1, 10)) {
 		 
              log(LogStatus.INFO, "Changed the value of " + fieldName + " for Acuity Setting", YesNo.No);
          }
          else
          {
              log(LogStatus.ERROR, "Not able to change the value of " + fieldName + " for Acuity Setting", YesNo.No);
              sa.assertTrue(false, "Not able to changed the value of " + fieldName + " for Acuity Setting");    
          }
          ThreadSleep(5000);
          switchToDefaultContent(driver);
          driver.close();
          driver.switchTo().window(parentWindow);
          }
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
 	if(setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), fieldName2, valueField2, 10)) {
 		 
              log(LogStatus.INFO, "Changed the value of " + fieldName + " for Acuity Setting", YesNo.No);
          }
          else
          {
              log(LogStatus.ERROR, "Not able to change the value of " + fieldName + " for Acuity Setting", YesNo.No);
              sa.assertTrue(false, "Not able to changed the value of " + fieldName + " for Acuity Setting");    
          }
          ThreadSleep(5000);
          switchToDefaultContent(driver);
          driver.close();
          driver.switchTo().window(parentWindow);
     }
     lp.CRMlogout();
  	sa.assertAll();
}


@Parameters({ "projectName" })
@Test
public void ADETc123_2_VerifyUpdatefieldcolumnNamesCheckDealsectionAccountsTab(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String [] account = {ADEIns1,ADEIns2,ADEIns4,ADEIns7,ADEIns5};
	String dealHeader = "Deal Name<break>Import Status<break>Deal Description<break>Stage";


	ArrayList<String> connectionsSectionHeaderName = new ArrayList<String>();

	ArrayList<String> contactsSectionHeaderName = new ArrayList<String>();

	String[] arrDealHeader = dealHeader.split("<break>");
	List<String> dealHeaders = new ArrayList<String>(Arrays.asList(arrDealHeader));
	for (int i = 0; i < account.length; i++) {
	if (cp.clickOnTab(projectName, tabObj1)) {
		log(LogStatus.INFO, "Clicked on Tab : " + tabObj1 + " For : " + account[i], YesNo.No);
		ThreadSleep(1000);
		if (cp.clickOnAlreadyCreatedItem(projectName, account[i], 30)) {
			log(LogStatus.INFO, "Clicked on  : " + ADEIns1 + " For : " + tabObj1, YesNo.No);
			ThreadSleep(2000);
			ArrayList<String> result1 = bp
					.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
							contactsSectionHeaderName, null, dealHeaders, null, connectionsSectionHeaderName, null,contactsSectionHeaderName, null);
			if (result1.isEmpty()) {
				log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
			} else {
				log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
				sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
			}
		} else {

			sa.assertTrue(false, "Not Able to open created source firm : " + account[i]);
			log(LogStatus.SKIP, "Not Able to open created source firm : " + account[i], YesNo.Yes);

		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
	}
	   lp.CRMlogout();
	  	sa.assertAll();
	}


@Parameters({ "projectName" })
@Test
public void ADETc124_1_VerifytheImpactonSubjectLinewhenBaseUrlgetsChangedforDealGridAPIForAccounts(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	String fieldName = ADEFieldname1;
	String valueField =ADENewValue2;
	String fieldName1 = ADEFieldname2;
	String valueField1 =ADENewValue3;
	String fieldName2 = ADEFieldname3;
	String valueField2 =ADENewValue1;
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
 	if(setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), fieldName1, valueField1, 10)) {
 		 
              log(LogStatus.INFO, "Changed the value of " + fieldName + " for Acuity Setting", YesNo.No);
          }
          else
          {
              log(LogStatus.ERROR, "Not able to change the value of " + fieldName + " for Acuity Setting", YesNo.No);
              sa.assertTrue(false, "Not able to changed the value of " + fieldName + " for Acuity Setting");    
          }
          ThreadSleep(5000);
          switchToDefaultContent(driver);
          driver.close();
          driver.switchTo().window(parentWindow);
          }
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
 	if(setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), fieldName2, valueField2, 10)) {
 		 
              log(LogStatus.INFO, "Changed the value of " + fieldName + " for Acuity Setting", YesNo.No);
          }
          else
          {
              log(LogStatus.ERROR, "Not able to change the value of " + fieldName + " for Acuity Setting", YesNo.No);
              sa.assertTrue(false, "Not able to changed the value of " + fieldName + " for Acuity Setting");    
          }
          ThreadSleep(5000);
          switchToDefaultContent(driver);
          driver.close();
          driver.switchTo().window(parentWindow);
     }
     lp.CRMlogout();
  	sa.assertAll();
}


@Parameters({ "projectName" })
@Test
public void ADETc124_2_VerifyUpdatefieldcolumnNamesCheckDealsectionAccountsTab(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String [] account = {ADEIns1,ADEIns2,ADEIns4,ADEIns7,ADEIns5};
	String dealHeader = "Deal Name<break>Deal Description<break>Stage<break>Import Status";


	ArrayList<String> connectionsSectionHeaderName = new ArrayList<String>();

	ArrayList<String> contactsSectionHeaderName = new ArrayList<String>();

	String[] arrDealHeader = dealHeader.split("<break>");
	List<String> dealHeaders = new ArrayList<String>(Arrays.asList(arrDealHeader));
	for (int i = 0; i < account.length; i++) {
	if (cp.clickOnTab(projectName, tabObj1)) {
		log(LogStatus.INFO, "Clicked on Tab : " + tabObj1 + " For : " + account[i], YesNo.No);
		ThreadSleep(1000);
		if (cp.clickOnAlreadyCreatedItem(projectName, account[i], 30)) {
			log(LogStatus.INFO, "Clicked on  : " + ADEIns1 + " For : " + tabObj1, YesNo.No);
			ThreadSleep(2000);
			ArrayList<String> result1 = bp
					.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
							contactsSectionHeaderName, null, dealHeaders, null, connectionsSectionHeaderName, null,contactsSectionHeaderName, null);
			if (result1.isEmpty()) {
				log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
			} else {
				log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
				sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
			}
		} else {

			sa.assertTrue(false, "Not Able to open created source firm : " + account[i]);
			log(LogStatus.SKIP, "Not Able to open created source firm : " + account[i], YesNo.Yes);

		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
	}
	   lp.CRMlogout();
	  	sa.assertAll();
	}


@Parameters({ "projectName" })
@Test
public void ADETc125_1_VerifytheImpactonSubjectLinewhenBaseUrlgetsChangedforDealGridAPIForContact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	String fieldName = ADEFieldname4;
	String valueField =ADENewValue1;
	String fieldName1 = ADEFieldname5;
	String valueField1 =ADENewValue2;
	String fieldName2 = ADEFieldname6;
	String valueField2 =ADENewValue3;
	String parentWindow = null;
	
	 if (home.clickOnSetUpLink()) {
         parentWindow = switchOnWindow(driver);
         if (parentWindow == null) {
           log(LogStatus.INFO,
                     "new window is open after click on setup link in lighting mode so cannot create clone user",
                     YesNo.Yes);
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
	 }
     if (home.clickOnSetUpLink()) {
         parentWindow = switchOnWindow(driver);
         if (parentWindow == null) {
           log(LogStatus.INFO,
                     "new window is open after click on setup link in lighting mode so cannot create clone user",
                     YesNo.Yes);
             if(setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), fieldName1, valueField1, 10)) {
        		 
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
          }
     if (home.clickOnSetUpLink()) {
         parentWindow = switchOnWindow(driver);
         if (parentWindow == null) {
           log(LogStatus.INFO,
                     "new window is open after click on setup link in lighting mode so cannot create clone user",
                     YesNo.Yes);
             if(setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), fieldName2, valueField2, 10)) {
        		 
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
     }
     lp.CRMlogout();
  	sa.assertAll();
}


@Parameters({ "projectName" })
@Test
public void ADETc123_3_VerifyUpdatefieldcolumnNamesCheckDealsectionContactTab(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String ContactName = ADEContact1FName + " " + ADEContact1LName;
	String dealHeader = "Deal Name<break>Import Status<break>Deal Description<break>Stage";


	ArrayList<String> connectionsSectionHeaderName = new ArrayList<String>();

	ArrayList<String> contactsSectionHeaderName = new ArrayList<String>();

	String[] arrDealHeader = dealHeader.split("<break>");
	List<String> dealHeaders = new ArrayList<String>(Arrays.asList(arrDealHeader));
	if (cp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO, "Clicked on Tab : " + tabObj2 + " For : " + ContactName, YesNo.No);
		ThreadSleep(1000);
		if (cp.clickOnAlreadyCreatedItem(projectName, ContactName, 30)) {
			log(LogStatus.INFO, "Clicked on  : " + ContactName + " For : " + tabObj2, YesNo.No);
			ThreadSleep(2000);
			ArrayList<String> result1 = bp
					.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
							contactsSectionHeaderName, null, dealHeaders, null, connectionsSectionHeaderName, null,contactsSectionHeaderName, null);
			if (result1.isEmpty()) {
				log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
			} else {
				log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
				sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
			}
		} else {

			sa.assertTrue(false, "Not Able to open created source firm : " + ContactName);
			log(LogStatus.SKIP, "Not Able to open created source firm : " + ContactName, YesNo.Yes);

		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
	}
	
	   lp.CRMlogout();
	  	sa.assertAll();
	}


@Parameters({ "projectName" })
@Test
public void ADETc126_1_VerifytheImpactonSubjectLinewhenBaseUrlgetsChangedforDealGridAPIForContact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	String fieldName = ADEFieldname4;
	String valueField =ADENewValue2;
	String fieldName1 = ADEFieldname5;
	String valueField1 =ADENewValue3;
	String fieldName2 = ADEFieldname6;
	String valueField2 =ADENewValue1;
	String parentWindow = null;
	
	 if (home.clickOnSetUpLink()) {
         parentWindow = switchOnWindow(driver);
         if (parentWindow == null) {
           log(LogStatus.INFO,
                     "new window is open after click on setup link in lighting mode so cannot create clone user",
                     YesNo.Yes);
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
	 }
	 if (home.clickOnSetUpLink()) {
         parentWindow = switchOnWindow(driver);
         if (parentWindow == null) {
           log(LogStatus.INFO,
                     "new window is open after click on setup link in lighting mode so cannot create clone user",
                     YesNo.Yes);
             if(setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), fieldName1, valueField1, 10)) {
        		 
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
          }
	 if (home.clickOnSetUpLink()) {
         parentWindow = switchOnWindow(driver);
         if (parentWindow == null) {
           log(LogStatus.INFO,
                     "new window is open after click on setup link in lighting mode so cannot create clone user",
                     YesNo.Yes);
             if(setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), fieldName2, valueField2, 10)) {
        		 
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
     }
     lp.CRMlogout();
  	sa.assertAll();
}


@Parameters({ "projectName" })
@Test
public void ADETc124_3_VerifyUpdatefieldcolumnNamesCheckDealsectionContactTab(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String ContactName = ADEContact1FName + " " + ADEContact1LName;
	String dealHeader = "Deal Name<break>Deal Description<break>Stage<break>Import Status";


	ArrayList<String> connectionsSectionHeaderName = new ArrayList<String>();

	ArrayList<String> contactsSectionHeaderName = new ArrayList<String>();

	String[] arrDealHeader = dealHeader.split("<break>");
	List<String> dealHeaders = new ArrayList<String>(Arrays.asList(arrDealHeader));

	if (cp.clickOnTab(projectName, tabObj2)) {
		log(LogStatus.INFO, "Clicked on Tab : " + tabObj2 + " For : " + ContactName, YesNo.No);
		ThreadSleep(1000);
		if (cp.clickOnAlreadyCreatedItem(projectName, ContactName, 30)) {
			log(LogStatus.INFO, "Clicked on  : " + ContactName + " For : " + tabObj2, YesNo.No);
			ThreadSleep(2000);
			ArrayList<String> result1 = bp
					.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
							contactsSectionHeaderName, null, dealHeaders, null, connectionsSectionHeaderName, null,contactsSectionHeaderName, null);
			if (result1.isEmpty()) {
				log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
			} else {
				log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
				sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
			}
		} else {

			sa.assertTrue(false, "Not Able to open created source firm : " + ContactName);
			log(LogStatus.SKIP, "Not Able to open created source firm : " + ContactName, YesNo.Yes);

		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
	}
	
	   lp.CRMlogout();
	  	sa.assertAll();
	}


//institution Record type

@Parameters({ "projectName" })
@Test
public void ADETc127_PreconditoinAccountContact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

	// INS
	String value = "";
	String type = "";
	String TabName1 = "";
	String[][] EntityOrAccounts = { { ADEIns17, ADEIns17RecordType, null },};

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

		ADEContact25EmailID = lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact25EmailID, "Contact", excelLabel.Variable_Name,
				"ADEContact25", excelLabel.Contact_EmailId);

		if (cp.createContactAcuity(projectName, ADEContact25FName, ADEContact25LName, ADEIns17, ADEContact25EmailID,
				ADEContact11RecordType, null, null, CreationPage.ContactPage, null, null)) {
			log(LogStatus.INFO, "successfully Created Contact : " + ADEContact25LName + " " + ADEContact25LName,
					YesNo.No);
		} else {
			sa.assertTrue(false, "Not Able to Create Contact : " + ADEContact25FName + " " + ADEContact25LName);
			log(LogStatus.SKIP, "Not Able to Create Contact: " + ADEContact25LName + " " + ADEContact25LName,
					YesNo.Yes);
		}
	}
	
	//Fund
	
	if (lp.clickOnTab(projectName, TabName.Object3Tab)) {
		log(LogStatus.INFO,"Click on Tab : "+TabName.Object3Tab,YesNo.No);	
		String[] funds = {ADEFundName1,ADEFundType1,ADEInvestmentCategory1,ADERecordType1};
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
	  lp.CRMlogout();
	  	sa.assertAll();
	}


@Parameters({ "projectName" })
@Test
public void ADETc128_1_VerifythatFundraisingswithInvesrmentCategoryFundDisplayinginFundstaboffundraisingatDummyInsfirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);
	
	String stage = ADEStage2;
	String ADETargetClosingDate = todaysDate;
	String[] fundraisingsClosingDate = convertDateFromOneFormatToAnother(ADETargetClosingDate, "MM/dd/yyyy", "dd/MMM/yyyy")
			.split("/", -1);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns17, 30)) {
				if (CommonLib.click(driver, BP.CoinvestmentTab( 30,action.SCROLLANDBOOLEAN), "Coinvestment Tab: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Coinvestment Tab", YesNo.No);
					ThreadSleep(1000);
				if (CommonLib.click(driver, dp.NewCoinvestmentFundraisingIcon(30), tabObj4 + "New Coinvestment Fundraising Icon", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "New Coinvestment Fundraising Icon", YesNo.No);
					String targetClosingDate = fundraisingsClosingDate[0];
					String targetClosingMonth = fundraisingsClosingDate[1];
					String targetClosingYear = fundraisingsClosingDate[2];
					if (fr.createFundRaisingFromIcon(environment, "Lightning", null,ADERFundName2,null, null,
							ADEStage2, ADEInvestmentLikelyAmountMN2, null, targetClosingYear,targetClosingMonth, targetClosingDate)) {
						appLog.info("fundraising is created : " + ADEFundraisingName);
					} else {
						appLog.error("Not able to create fundraising: " + ADEFundraisingName);
						sa.assertTrue(false, "Not able to create fundraising: " + ADEFundraisingName);
					}
					refresh(driver);
					ThreadSleep(3000);
					String xpath = "//td[@data-label='Stage']//lightning-base-formatted-text[text()='"+ stage+"']/ancestor::tr//lightning-primitive-cell-factory[@data-label='Fundraising Name']//a";
					WebElement ele = FindElement(driver, xpath, "fundraising name", action.BOOLEAN, 10);
					if (ele != null) {
						String name = getText(driver, ele, "fundraising name", action.SCROLLANDBOOLEAN);
						ExcelUtils.writeData(AcuityDataSheetFilePath, name, "Fundraisings", excelLabel.Variable_Name,
								"ADEFR3", excelLabel.FundRaising_Name);
						log(LogStatus.INFO,
								"successfully created and noted id of DT" + name + " and deal name " + ADEFundraisingName2,
								YesNo.No);
					} else {
						sa.assertTrue(false, "could not create fundraising" + ADEFundraisingName2);
						log(LogStatus.SKIP, "could not create DT" + ADEFundraisingName2, YesNo.Yes);
					}
				} else {
					appLog.error(
							"Not able to click on New Coinvestment Fundraising Icon so cannot create fundraising: " + ADEFundraisingName2);
					sa.assertTrue(false,
							"Not able to click on New Coinvestment Fundraising Icon so cannot create fundraising: " + ADEFundraisingName2);
				}
				} else {
					log(LogStatus.FAIL, "Not able to Click on Coinvestment tab: " , YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on Coinvestment tab: " );
					}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + ADEIns17 , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + ADEIns17 );
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
public void ADETc128_2_VerifythatFundraisingswithInvesrmentCategoryFundDisplayinginFundstaboffundraisingatDummyInsfirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);
	
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String dateReceived = todaysDate;
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns17, 30)) {
				log(LogStatus.INFO, "open created item" + ADEIns3, YesNo.No);

				if (BP.fundraisingsAcuityFundraisingsName(ADEFundraisingName2, 10) != null) {
					log(LogStatus.PASS, "Fundraising Name: " + ADEFundraisingName2 + " is hyperlink and is present", YesNo.No);
				} else {
					sa.assertTrue(true, "is hyperlink and not present  : " + ADEFundraisingName2 + " For : " + TabName.FundraisingsTab);
					log(LogStatus.SKIP, "is hyperlink and not present  : " + ADEFundraisingName2 + " For : " + TabName.FundraisingsTab,
							YesNo.Yes);
				}
				if (BP.fundraisingsAcuityStageName(ADEFundraisingName2,ADEStage2, 10) != null) {
					log(LogStatus.PASS, "Fundraising stage: " + ADEStage2 + "is present", YesNo.No);
				} else {
					sa.assertTrue(true, "Fundraising stage not present  : " + ADEStage2 + " For : " + TabName.FundraisingsTab);
					log(LogStatus.SKIP, "Fundraising stage  not present  : " + ADEStage2 + " For : " + TabName.FundraisingsTab,
							YesNo.Yes);
				}
				String actualDate= BP.FundraisingAcuityTargetCloseDate2(ADEFundraisingName2, 30).getText();
				
				if (cp.verifyDate(todaysDate,null, actualDate)) {
					log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
				}
				else {
				log(LogStatus.ERROR, "Date Received is not matched For : "+ADEFundraisingName2+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
					sa.assertTrue(false,"Date Received is not matched For : "+ADEFundraisingName2+" Actual : "+actualDate+" /t Expected : "+todaysDate );
				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + ADEIns17 , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + ADEIns17 );
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
public void ADETc129_1_VerifythatFundraisingswithInvesrmentCategoryCoInvestementFundFundDisplayinginFundstaboffundraisingatDummyInsfirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);
	
	String stage = ADEStage2;
	String ADETargetClosingDate = todaysDate;
	String[] fundraisingsClosingDate = convertDateFromOneFormatToAnother(ADETargetClosingDate, "MM/dd/yyyy", "dd/MMM/yyyy")
			.split("/", -1);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns17, 30)) {
				if (CommonLib.click(driver, BP.CoinvestmentTab( 30,action.SCROLLANDBOOLEAN), "Coinvestment Tab: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Coinvestment Tab", YesNo.No);
					ThreadSleep(1000);
				if (CommonLib.click(driver, dp.NewCoinvestmentFundraisingIcon(30), tabObj4 + "New Coinvestment Fundraising Icon", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "New Coinvestment Fundraising Icon", YesNo.No);
					String targetClosingDate = fundraisingsClosingDate[0];
					String targetClosingMonth = fundraisingsClosingDate[1];
					String targetClosingYear = fundraisingsClosingDate[2];
					if (fr.createFundRaisingFromIcon(environment, "Lightning", null,ADERFundName3,ADEInstitutionName3, null,
							ADEStage3, ADEInvestmentLikelyAmountMN3, null, targetClosingYear,targetClosingMonth, targetClosingDate)) {
						appLog.info("fundraising is created : " + "");
					} else {
						appLog.error("Not able to create fundraising: " + "");
						sa.assertTrue(false, "Not able to create fundraising: " + "");
					}
					refresh(driver);
					ThreadSleep(3000);
					String xpath = "//td[@data-label='Stage']//lightning-base-formatted-text[text()='"+ stage+"']/ancestor::tr//lightning-primitive-cell-factory[@data-label='Fundraising Name']//a";
					WebElement ele = FindElement(driver, xpath, "fundraising name", action.BOOLEAN, 10);
					if (ele != null) {
						String name = getText(driver, ele, "fundraising name", action.SCROLLANDBOOLEAN);
						ExcelUtils.writeData(AcuityDataSheetFilePath, name, "Fundraisings", excelLabel.Variable_Name,
								"ADEFR4", excelLabel.FundRaising_Name);
						log(LogStatus.INFO,
								"successfully created and noted name of Fundraising" + name + " and fundraising name " + ADEFundraisingName3,
								YesNo.No);
					} else {
						sa.assertTrue(false, "could not create fundraising" + ADEFundraisingName3);
						log(LogStatus.SKIP, "could not create DT" + ADEFundraisingName3, YesNo.Yes);
					}
				} else {
					appLog.error(
							"Not able to click on New Coinvestment Fundraising Icon so cannot create fundraising: " + ADEFundraisingName3);
					sa.assertTrue(false,
							"Not able to click on New Coinvestment Fundraising Icon so cannot create fundraising: " + ADEFundraisingName3);
				}
				} else {
					log(LogStatus.FAIL, "Not able to Click on Coinvestment tab: " , YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on Coinvestment tab: " );
					}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + ADEIns17 , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + ADEIns17 );
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
public void ADETc129_2_VerifythatFundraisingswithInvesrmentCategoryCoInvestementFundDisplayinginFundstaboffundraisingatDummyInsfirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);
	
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String dateReceived = todaysDate;
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns17, 30)) {
				log(LogStatus.INFO, "open created item" + ADEIns17, YesNo.No);
				if (CommonLib.click(driver, BP.CoinvestmentTab( 30,action.SCROLLANDBOOLEAN), "Source tab: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Coinvestment tab", YesNo.No);
				if (BP.fundraisingsAcuityFundraisingsCompany(ADEInstitutionName3, 10) != null) {
					log(LogStatus.PASS, "Fundraising Name: " + ADEInstitutionName3 + " is hyperlink and is present", YesNo.No);
				} else {
					sa.assertTrue(true, "is hyperlink and not present  : " + ADEInstitutionName3 + " For : " + TabName.FundraisingsTab);
					log(LogStatus.SKIP, "is hyperlink and not present  : " + ADEInstitutionName3 + " For : " + TabName.FundraisingsTab,
							YesNo.Yes);
				}
				if (BP.fundraisingsAcuityStageNameCoinvestment(ADEInstitutionName3,ADEStage3, 10) != null) {
					log(LogStatus.PASS, "Fundraising stage: " + ADEStage3 + "is present", YesNo.No);
				} else {
					sa.assertTrue(true, "Fundraising stage not present  : " + ADEStage3 + " For : " + TabName.FundraisingsTab);
					log(LogStatus.SKIP, "Fundraising stage  not present  : " + ADEStage3 + " For : " + TabName.FundraisingsTab,
							YesNo.Yes);
				}
				String actualDate= BP.FundraisingAcuityTargetCloseDateCoinvestement2(ADEInstitutionName3, 30).getText();
				
				if (cp.verifyDate(todaysDate,null, actualDate)) {
					log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
				}
				else {
				log(LogStatus.ERROR, "Date Received is not matched For : "+ADEInstitutionName3+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
					sa.assertTrue(false,"Date Received is not matched For : "+ADEInstitutionName3+" Actual : "+actualDate+" /t Expected : "+todaysDate );
				}
				} else {
					log(LogStatus.FAIL, "Not able to Click on  tab: " + "CoinvestmentTab" , YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on  tab: " + "CoinvestmentTab" );
					}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + ADEIns17 , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + ADEIns17 );
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
public void ADETc130_ChangeEditCoinvestmentFundCompanyCompanyinfundraisingwithFundInvestmentCategoryTypeandVerifyImpactonFundsCoinvestmenttabsoffundraisinggridatFApageatDummyInsfirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String dateReceived = todaysDate;
	String message = BP.ErrorMessageAcuity;

	ArrayList<String> fundraisingHeaders = new ArrayList<String>();
	if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEFundraisingName2, 10)) {
			if (BP.clickOnShowMoreActionDownArrow(projectName, PageName.Object4Page, ShowMoreActionDropDownList.Edit, 10)) {
				ThreadSleep(2000);
		}
			} else {
			log(LogStatus.ERROR, "Not able to click on show more action down arrow", YesNo.Yes);
		}
		if (click(driver, fr.getFundNameCrossIcon(projectName, 60), "Company Cross Icon", action.SCROLLANDBOOLEAN)) {
			appLog.info("Clicked on  Cross icon");
			ThreadSleep(3000);
		} else {
			appLog.info("Not able to click on Cross Icon button");
		}
	
			if (fr.UpdateFundraisingDetail(projectName, mode,ADEFundName1, ADEIns10, 10)) {
				log(LogStatus.INFO, "successfully changed detail to " + ADEIns10, YesNo.Yes);
			} else {
				sa.assertTrue(false, "not able to change detail to " + ADEIns10);
				log(LogStatus.SKIP, "not able to change detail to " + ADEIns10, YesNo.Yes);
			}
			if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
					if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns17, 30)) {
						log(LogStatus.INFO, "open created item" + ADEIns17, YesNo.No);
					ArrayList<String> result1 = BP.verifyHeaderNameAndMessageOnFundraisigContactsConnectionsAndDealsSection(fundraisingHeaders,message );
					if (result1.isEmpty()) {
						log(LogStatus.INFO, "The header name and message have been verified  Fundraising Section ", YesNo.No);
					} else {
						log(LogStatus.ERROR, "The header name and message are not verified on Fundraising Section ", YesNo.No);
						sa.assertTrue(false, "The header name and message are not verified on  Fundraising Section ");
					}
					if (CommonLib.click(driver, BP.CoinvestmentTab( 30,action.SCROLLANDBOOLEAN), "Source tab: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Coinvestment tab", YesNo.No);
					if (BP.fundraisingsAcuityFundraisingsCompany(ADEIns10, 10) != null) {
						log(LogStatus.PASS, "Fundraising Name: " + ADEIns10 + " is hyperlink and is present", YesNo.No);
					} else {
						sa.assertTrue(true, "is hyperlink and not present  : " + ADEIns10 + " For : " + TabName.FundraisingsTab);
						log(LogStatus.SKIP, "is hyperlink and not present  : " + ADEIns10 + " For : " + TabName.FundraisingsTab,
								YesNo.Yes);
					}
					if (BP.fundraisingsAcuityStageNameCoinvestment(ADEIns10,ADEStage2, 10) != null) {
						log(LogStatus.PASS, "Fundraising stage: " + ADEStage3 + "is present", YesNo.No);
					} else {
						sa.assertTrue(true, "Fundraising stage not present  : " + ADEStage2 + " For : " + TabName.FundraisingsTab);
						log(LogStatus.SKIP, "Fundraising stage  not present  : " + ADEStage2 + " For : " + TabName.FundraisingsTab,
								YesNo.Yes);
					}
					String actualDate= BP.FundraisingAcuityTargetCloseDateCoinvestement2(ADEIns10, 30).getText();
					
					if (cp.verifyDate(todaysDate,null, actualDate)) {
						log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
					}
					else {
					log(LogStatus.ERROR, "Date Received is not matched For : "+ADEIns10+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
						sa.assertTrue(false,"Date Received is not matched For : "+ADEIns10+" Actual : "+actualDate+" /t Expected : "+todaysDate );
					}
					} else {
						log(LogStatus.FAIL, "Not able to Click on  tab: " + "CoinvestmentTab" , YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on  tab: " + "CoinvestmentTab" );
						}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + ADEIns17 , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + ADEIns17 );
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
public void ADETc131_RemoveCoinvestmentCompanyFromFundraisingandVerifyImpactonFundsCoinvestmentTabsofFundraisingGridatFApageInsFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);
	NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);	
	if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEFundraisingName3, 10)) {
			if (BP.clickOnShowMoreActionDownArrow(projectName, PageName.Object4Page, ShowMoreActionDropDownList.Edit, 10)) {
				ThreadSleep(2000);
		}
			} else {
			log(LogStatus.ERROR, "Not able to click on show more action down arrow", YesNo.Yes);
		}
		if (click(driver, fr.getFundNameCrossIcon(projectName, 60), "Company Cross Icon", action.SCROLLANDBOOLEAN)) {
			appLog.info("Clicked on  Cross icon");
			ThreadSleep(3000);
		} else {
			appLog.info("Not able to click on Cross Icon button");
		}
	
			if (fr.UpdateFundraisingDetail(projectName, mode,ADERFundName2, null, 10)) {
				log(LogStatus.INFO, "successfully changed detail to " + ADERFundName2, YesNo.Yes);
			} else {
				sa.assertTrue(false, "not able to change detail to " + ADERFundName2);
				log(LogStatus.SKIP, "not able to change detail to " + ADERFundName2, YesNo.Yes);
			}
			if (BP.clickOnShowMoreActionDownArrow(projectName, PageName.Object4Page, ShowMoreActionDropDownList.Edit, 10)) {
				ThreadSleep(2000);
		}
			} else {
			log(LogStatus.ERROR, "Not able to click on show more action down arrow", YesNo.Yes);
		}
		if (click(driver, fr.getCompanyNameCrossIcon(projectName, 60), "Company Cross Icon", action.SCROLLANDBOOLEAN)) {
			appLog.info("Clicked on  Cross icon");
			ThreadSleep(3000);
		} else {
			appLog.info("Not able to click on Cross Icon button");
		}
		if (click(driver, fr.getCustomTabSaveBtn("", 60), "Save Button", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(500);
		} else {
			appLog.error("Not able to click on save button");
		}
		String dateReceived = todaysDate;
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
				if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns17, 30)) {
					log(LogStatus.INFO, "open created item" + ADEIns17, YesNo.No);

					if (BP.fundraisingsAcuityFundraisingsName(ADEFundraisingName3, 10) != null) {
						log(LogStatus.PASS, "Fundraising Name: " + ADEFundraisingName3 + " is hyperlink and is present", YesNo.No);
					} else {
						sa.assertTrue(true, "is hyperlink and not present  : " + ADEFundraisingName3 + " For : " + TabName.FundraisingsTab);
						log(LogStatus.SKIP, "is hyperlink and not present  : " + ADEFundraisingName3 + " For : " + TabName.FundraisingsTab,
								YesNo.Yes);
					}
					if (BP.fundraisingsAcuityStageName(ADEFundraisingName3,ADEStage3, 10) != null) {
						log(LogStatus.PASS, "Fundraising stage: " + ADEStage3 + "is present", YesNo.No);
					} else {
						sa.assertTrue(true, "Fundraising stage not present  : " + ADEStage3 + " For : " + TabName.FundraisingsTab);
						log(LogStatus.SKIP, "Fundraising stage  not present  : " + ADEStage3 + " For : " + TabName.FundraisingsTab,
								YesNo.Yes);
					}
					String actualDate= BP.FundraisingAcuityTargetCloseDate2(ADEFundraisingName3, 30).getText();
					
					if (cp.verifyDate(todaysDate,null, actualDate)) {
						log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
					}
					else {
					log(LogStatus.ERROR, "Date Received is not matched For : "+ADEFundraisingName3+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
						sa.assertTrue(false,"Date Received is not matched For : "+ADEFundraisingName3+" Actual : "+actualDate+" /t Expected : "+todaysDate );
					}
				} else {
					log(LogStatus.FAIL, "Not able to Click on  tab: " + ADEIns17 , YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on  tab: " + ADEIns17 );
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
public void ADETc132_1_VerifythatFundraisingswithInvesrmentCategoryFundDisplayinginFundstaboffundraisingatDummyInsfirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);
	
	String stage = ADEStage4;
	String ADETargetClosingDate = todaysDate;
	String[] fundraisingsClosingDate = convertDateFromOneFormatToAnother(ADETargetClosingDate, "MM/dd/yyyy", "dd/MMM/yyyy")
			.split("/", -1);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns17, 30)) {
				if (CommonLib.click(driver, BP.CoinvestmentTab( 30,action.SCROLLANDBOOLEAN), "Coinvestment Tab: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Coinvestment Tab", YesNo.No);
					ThreadSleep(1000);
				if (CommonLib.click(driver, dp.NewCoinvestmentFundraisingIcon(30), tabObj4 + "New Coinvestment Fundraising Icon", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "New Coinvestment Fundraising Icon", YesNo.No);
					String targetClosingDate = fundraisingsClosingDate[0];
					String targetClosingMonth = fundraisingsClosingDate[1];
					String targetClosingYear = fundraisingsClosingDate[2];
					if (fr.createFundRaisingFromIcon(environment, "Lightning", null,ADERFundName4,null, null,
							ADEStage4, ADEInvestmentLikelyAmountMN4, null, targetClosingYear,targetClosingMonth, targetClosingDate)) {
						appLog.info("fundraising is created : " + ADEFundraisingName);
					} else {
						appLog.error("Not able to create fundraising: " + ADEFundraisingName);
						sa.assertTrue(false, "Not able to create fundraising: " + ADEFundraisingName);
					}
					refresh(driver);
					ThreadSleep(3000);
					String xpath = "//td[@data-label='Stage']//lightning-base-formatted-text[text()='"+ stage+"']/ancestor::tr//lightning-primitive-cell-factory[@data-label='Fundraising Name']//a";
					WebElement ele = FindElement(driver, xpath, "fundraising name", action.BOOLEAN, 10);
					if (ele != null) {
						String name = getText(driver, ele, "fundraising name", action.SCROLLANDBOOLEAN);
						ExcelUtils.writeData(AcuityDataSheetFilePath, name, "Fundraisings", excelLabel.Variable_Name,
								"ADEFR5", excelLabel.FundRaising_Name);
						log(LogStatus.INFO,
								"successfully created and noted id of DT" + name + " and deal name " + ADEFundraisingName2,
								YesNo.No);
					} else {
						sa.assertTrue(false, "could not create fundraising" + ADEFundraisingName2);
						log(LogStatus.SKIP, "could not create DT" + ADEFundraisingName2, YesNo.Yes);
					}
				} else {
					appLog.error(
							"Not able to click on New Coinvestment Fundraising Icon so cannot create fundraising: " + ADEFundraisingName2);
					sa.assertTrue(false,
							"Not able to click on New Coinvestment Fundraising Icon so cannot create fundraising: " + ADEFundraisingName2);
				}
				} else {
					log(LogStatus.FAIL, "Not able to Click on Coinvestment tab: " , YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on Coinvestment tab: " );
					}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + ADEIns17 , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + ADEIns17 );
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
public void ADETc132_2_VerifythatFundraisingswithInvesrmentCategoryFundDisplayinginFundstaboffundraisingatDummyInsfirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);
	
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String dateReceived = todaysDate;
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns17, 30)) {
				log(LogStatus.INFO, "open created item" + ADEIns3, YesNo.No);

				if (BP.fundraisingsAcuityFundraisingsName(ADEFundraisingName4, 10) != null) {
					log(LogStatus.PASS, "Fundraising Name: " + ADEFundraisingName4 + " is hyperlink and is present", YesNo.No);
				} else {
					sa.assertTrue(true, "is hyperlink and not present  : " + ADEFundraisingName4 + " For : " + TabName.FundraisingsTab);
					log(LogStatus.SKIP, "is hyperlink and not present  : " + ADEFundraisingName4 + " For : " + TabName.FundraisingsTab,
							YesNo.Yes);
				}
				if (BP.fundraisingsAcuityStageName(ADEFundraisingName4,ADEStage4, 10) != null) {
					log(LogStatus.PASS, "Fundraising stage: " + ADEStage4 + "is present", YesNo.No);
				} else {
					sa.assertTrue(true, "Fundraising stage not present  : " + ADEStage4 + " For : " + TabName.FundraisingsTab);
					log(LogStatus.SKIP, "Fundraising stage  not present  : " + ADEStage4 + " For : " + TabName.FundraisingsTab,
							YesNo.Yes);
				}
				String actualDate= BP.FundraisingAcuityTargetCloseDate2(ADEFundraisingName4, 30).getText();
				
				if (cp.verifyDate(todaysDate,null, actualDate)) {
					log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
				}
				else {
				log(LogStatus.ERROR, "Date Received is not matched For : "+ADEFundraisingName4+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
					sa.assertTrue(false,"Date Received is not matched For : "+ADEFundraisingName4+" Actual : "+actualDate+" /t Expected : "+todaysDate );
				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + ADEIns17 , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + ADEIns17 );
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
public void ADETc133_1_VerifythatFundraisingswithInvesrmentCategoryCoInvestementFundFundDisplayinginFundstaboffundraisingatDummyInsfirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);
	
	String stage = ADEStage5;
	String ADETargetClosingDate = todaysDate;
	String[] fundraisingsClosingDate = convertDateFromOneFormatToAnother(ADETargetClosingDate, "MM/dd/yyyy", "dd/MMM/yyyy")
			.split("/", -1);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns17, 30)) {
				if (CommonLib.click(driver, BP.CoinvestmentTab( 30,action.SCROLLANDBOOLEAN), "Coinvestment Tab: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Coinvestment Tab", YesNo.No);
					ThreadSleep(1000);
				if (CommonLib.click(driver, dp.NewCoinvestmentFundraisingIcon(30), tabObj4 + "New Coinvestment Fundraising Icon", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "New Coinvestment Fundraising Icon", YesNo.No);
					String targetClosingDate = fundraisingsClosingDate[0];
					String targetClosingMonth = fundraisingsClosingDate[1];
					String targetClosingYear = fundraisingsClosingDate[2];
					if (fr.createFundRaisingFromIcon(environment, "Lightning", null,ADERFundName5,ADEInstitutionName5, null,
							ADEStage5, ADEInvestmentLikelyAmountMN5, null, targetClosingYear,targetClosingMonth, targetClosingDate)) {
						appLog.info("fundraising is created : " + "");
					} else {
						appLog.error("Not able to create fundraising: " + "");
						sa.assertTrue(false, "Not able to create fundraising: " + "");
					}
					refresh(driver);
					ThreadSleep(3000);
					String xpath = "//td[@data-label='Stage']//lightning-base-formatted-text[text()='"+ stage+"']/ancestor::tr//lightning-primitive-cell-factory[@data-label='Fundraising Name']//a";
					WebElement ele = FindElement(driver, xpath, "fundraising name", action.BOOLEAN, 10);
					if (ele != null) {
						String name = getText(driver, ele, "fundraising name", action.SCROLLANDBOOLEAN);
						ExcelUtils.writeData(AcuityDataSheetFilePath, name, "Fundraisings", excelLabel.Variable_Name,
								"ADEFR6", excelLabel.FundRaising_Name);
						log(LogStatus.INFO,
								"successfully created and noted name of Fundraising" + name + " and fundraising name " + ADEFundraisingName3,
								YesNo.No);
					} else {
						sa.assertTrue(false, "could not create fundraising" + ADEFundraisingName3);
						log(LogStatus.SKIP, "could not create DT" + ADEFundraisingName3, YesNo.Yes);
					}
				} else {
					appLog.error(
							"Not able to click on New Coinvestment Fundraising Icon so cannot create fundraising: " + ADEFundraisingName3);
					sa.assertTrue(false,
							"Not able to click on New Coinvestment Fundraising Icon so cannot create fundraising: " + ADEFundraisingName3);
				}
				} else {
					log(LogStatus.FAIL, "Not able to Click on Coinvestment tab: " , YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on Coinvestment tab: " );
					}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + ADEIns17 , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + ADEIns17 );
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
public void ADETc133_2_VerifythatFundraisingswithInvesrmentCategoryCoInvestementFundDisplayinginFundstaboffundraisingatDummyInsfirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);
	
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String dateReceived = todaysDate;
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns17, 30)) {
				log(LogStatus.INFO, "open created item" + ADEIns17, YesNo.No);
				if (CommonLib.click(driver, BP.CoinvestmentTab( 30,action.SCROLLANDBOOLEAN), "Source tab: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Coinvestment tab", YesNo.No);
				if (BP.fundraisingsAcuityFundraisingsCompany(ADEInstitutionName5, 10) != null) {
					log(LogStatus.PASS, "Fundraising Name: " + ADEInstitutionName5 + " is hyperlink and is present", YesNo.No);
				} else {
					sa.assertTrue(true, "is hyperlink and not present  : " + ADEInstitutionName5 + " For : " + TabName.FundraisingsTab);
					log(LogStatus.SKIP, "is hyperlink and not present  : " + ADEInstitutionName5 + " For : " + TabName.FundraisingsTab,
							YesNo.Yes);
				}
				if (BP.fundraisingsAcuityStageNameCoinvestment(ADEInstitutionName5,ADEStage5, 10) != null) {
					log(LogStatus.PASS, "Fundraising stage: " + ADEStage3 + "is present", YesNo.No);
				} else {
					sa.assertTrue(true, "Fundraising stage not present  : " + ADEStage3 + " For : " + TabName.FundraisingsTab);
					log(LogStatus.SKIP, "Fundraising stage  not present  : " + ADEStage3 + " For : " + TabName.FundraisingsTab,
							YesNo.Yes);
				}
				String actualDate= BP.FundraisingAcuityTargetCloseDateCoinvestement2(ADEInstitutionName5, 30).getText();
				
				if (cp.verifyDate(todaysDate,null, actualDate)) {
					log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
				}
				else {
				log(LogStatus.ERROR, "Date Received is not matched For : "+ADEInstitutionName5+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
					sa.assertTrue(false,"Date Received is not matched For : "+ADEInstitutionName5+" Actual : "+actualDate+" /t Expected : "+todaysDate );
				}
				} else {
					log(LogStatus.FAIL, "Not able to Click on  tab: " + "CoinvestmentTab" , YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on  tab: " + "CoinvestmentTab" );
					}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + ADEIns17 , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + ADEIns17 );
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
public void ADETc134_1_VerifytheImpactonSubjectLinewhenBaseUrlgetsChangedforFundraisingGridAPIForInstitution(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	String fieldName = ADEFieldname7;
	String valueField =ADENewValue7;
	String fieldName1 = ADEFieldname8;
	String valueField1 =ADENewValue8;
	String fieldName2 = ADEFieldname9;
	String valueField2 =ADENewValue9;
	String parentWindow = null;
	
	 if (home.clickOnSetUpLink()) {
         parentWindow = switchOnWindow(driver);
         if (parentWindow == null) {
           log(LogStatus.INFO,
                     "new window is open after click on setup link in lighting mode so cannot create clone user",
                     YesNo.Yes);
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
	 }
	 if (home.clickOnSetUpLink()) {
         parentWindow = switchOnWindow(driver);
         if (parentWindow == null) {
           log(LogStatus.INFO,
                     "new window is open after click on setup link in lighting mode so cannot create clone user",
                     YesNo.Yes);
             if(setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), fieldName1, valueField1, 10)) {
        		 
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
          }
	 if (home.clickOnSetUpLink()) {
         parentWindow = switchOnWindow(driver);
         if (parentWindow == null) {
           log(LogStatus.INFO,
                     "new window is open after click on setup link in lighting mode so cannot create clone user",
                     YesNo.Yes);
             if(setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), fieldName2, valueField2, 10)) {
        		 
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
     }
     lp.CRMlogout();
  	sa.assertAll();
}
@Parameters({ "projectName" })
@Test
public void ADETc134_2_EditFieldColumnNamesVerifyImpactFundraisingCardInstitutionAccount(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String fundraisingHeader = "Fundraising Name<break>Closing Date<break>Next Step<break>Status Notes";
	

	String[] arrfundraisingHeader = fundraisingHeader.split("<break>");
	List<String> fundraisingHeaders = new ArrayList<String>(Arrays.asList(arrfundraisingHeader));
	
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns17, 30)) {
			ArrayList<String> result1 = BP.verifyHeaderNameAndMessageOnFundraisigContactsConnectionsAndDealsSection(fundraisingHeaders,null );
			if (result1.isEmpty()) {
				log(LogStatus.INFO, "The header name and message have been verified  Fundraising Section ", YesNo.No);
			} else {
				log(LogStatus.ERROR, "The header name and message are not verified on Fundraising Section ", YesNo.No);
				sa.assertTrue(false, "The header name and message are not verified on  Fundraising Section ");
			}
			if (CommonLib.click(driver, BP.CoinvestmentTab( 30,action.SCROLLANDBOOLEAN), "Coinvestment tab: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Coinvestment tab", YesNo.No);
				ArrayList<String> result2 = BP.verifyHeaderNameAndMessageOnFundraisigContactsConnectionsAndDealsSection(fundraisingHeaders,null );
				if (result2.isEmpty()) {
					log(LogStatus.INFO, "The header name and message have been verified  Fundraising Section ", YesNo.No);
				} else {
					log(LogStatus.ERROR, "The header name and message are not verified on Fundraising Section ", YesNo.No);
					sa.assertTrue(false, "The header name and message are not verified on  Fundraising Section ");
				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + "CoinvestmentTab" , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + "CoinvestmentTab" );
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
public void ADETc135_1_VerifytheImpactonSubjectLinewhenBaseUrlgetsChangedforFundraisingGridAPIForInstitution(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
	HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	String fieldName = ADEFieldname7;
	String valueField =ADENewValue8;
	String fieldName1 = ADEFieldname8;
	String valueField1 =ADENewValue9;
	String fieldName2 = ADEFieldname9;
	String valueField2 =ADENewValue7;
	String parentWindow = null;
	
	 if (home.clickOnSetUpLink()) {
         parentWindow = switchOnWindow(driver);
         if (parentWindow == null) {
           log(LogStatus.INFO,
                     "new window is open after click on setup link in lighting mode so cannot create clone user",
                     YesNo.Yes);
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
	 }
	 if (home.clickOnSetUpLink()) {
         parentWindow = switchOnWindow(driver);
         if (parentWindow == null) {
           log(LogStatus.INFO,
                     "new window is open after click on setup link in lighting mode so cannot create clone user",
                     YesNo.Yes);
             if(setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), fieldName1, valueField1, 10)) {
        		 
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
          }
	 if (home.clickOnSetUpLink()) {
         parentWindow = switchOnWindow(driver);
         if (parentWindow == null) {
           log(LogStatus.INFO,
                     "new window is open after click on setup link in lighting mode so cannot create clone user",
                     YesNo.Yes);
             if(setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), fieldName2, valueField2, 10)) {
        		 
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
     }
     lp.CRMlogout();
  	sa.assertAll();
}
@Parameters({ "projectName" })
@Test
public void ADETc135_2_EditFieldColumnNamesVerifyImpactFundraisingCardInstitutionAccount(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String fundraisingHeader = "Fundraising Name<break>Next Step<break>Status Notes<break>Closing Date";
	

	String[] arrfundraisingHeader = fundraisingHeader.split("<break>");
	List<String> fundraisingHeaders = new ArrayList<String>(Arrays.asList(arrfundraisingHeader));
	
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns17, 30)) {
			ArrayList<String> result1 = BP.verifyHeaderNameAndMessageOnFundraisigContactsConnectionsAndDealsSection(fundraisingHeaders,null );
			if (result1.isEmpty()) {
				log(LogStatus.INFO, "The header name and message have been verified  Fundraising Section ", YesNo.No);
			} else {
				log(LogStatus.ERROR, "The header name and message are not verified on Fundraising Section ", YesNo.No);
				sa.assertTrue(false, "The header name and message are not verified on  Fundraising Section ");
			}
			if (CommonLib.click(driver, BP.CoinvestmentTab( 30,action.SCROLLANDBOOLEAN), "Coinvestment tab: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Coinvestment tab", YesNo.No);
				ArrayList<String> result2 = BP.verifyHeaderNameAndMessageOnFundraisigContactsConnectionsAndDealsSection(fundraisingHeaders,null );
				if (result2.isEmpty()) {
					log(LogStatus.INFO, "The header name and message have been verified  Fundraising Section ", YesNo.No);
				} else {
					log(LogStatus.ERROR, "The header name and message are not verified on Fundraising Section ", YesNo.No);
					sa.assertTrue(false, "The header name and message are not verified on  Fundraising Section ");
				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + "CoinvestmentTab" , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + "CoinvestmentTab" );
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
public void ADETc136_VerifythatCompanyandFundnameareClickableandtheirRedirectionfromFundraisingGrid(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns17, 30)) {
			if (CommonLib.click(driver, BP.CoinvestmentTab( 30,action.SCROLLANDBOOLEAN), "Source tab: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Coinvestment tab", YesNo.No);
			if (BP.fundraisingsAcuityFundraisingsCompany(ADEInstitutionName5, 10) != null) {
				log(LogStatus.PASS, "Company name: " + ADEInstitutionName5 + " is hyperlink and is present", YesNo.No);
				if (clickUsingJavaScript(driver, BP.fundraisingsAcuityFundraisingsCompany(ADEInstitutionName5, 10), "Company name: " + ADEInstitutionName5,
						action.BOOLEAN)) {
					log(LogStatus.PASS, "Clicked on Company name: " + ADEInstitutionName5, YesNo.No);
				try {
					String parentWindowId = CommonLib.switchOnWindow(driver);
					if (!parentWindowId.isEmpty()) {
						log(LogStatus.PASS, "New Window Open after click on Deal Link: " + ADEInstitutionName5, YesNo.No);

						if (BP.CompanyRecordPage(ADEInstitutionName5, 20) != null) {
							log(LogStatus.PASS,
									"----Company Detail Page is redirecting for Company Record: " + ADEInstitutionName5 + "-----",
									YesNo.No);
							driver.close();
							driver.switchTo().window(parentWindowId);

						} else {
							log(LogStatus.FAIL, "----Company Detail Page is not redirecting for Company Record: "
									+ ADEInstitutionName5 + "-----", YesNo.Yes);
							sa.assertTrue(false,
									"----Company Detail Page is not showing for Company Record: " + ADEInstitutionName5 + "-----");
							driver.close();
							driver.switchTo().window(parentWindowId);

						}

					} else {
						log(LogStatus.FAIL, "No New Window Open after click on Company Link: " + ADEInstitutionName5, YesNo.Yes);
						sa.assertTrue(false, "No New Window Open after click on Company Link: " + ADEInstitutionName5);
					}
				} catch (Exception e) {
					log(LogStatus.FAIL,
							"Not able to switch to window after click on Company Link, Msg showing: " + e.getMessage(),
							YesNo.Yes);
					sa.assertTrue(false, "Not able to switch to window after click on Company Link, Msg showing: "
							+ e.getMessage());
				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on Company Name: " + ADEInstitutionName5, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Company Name: " + ADEInstitutionName5);

			}
			} else {
				log(LogStatus.FAIL, "Company name not present: " + ADEInstitutionName5, YesNo.Yes);
				sa.assertTrue(false, "Company name not present: " + ADEInstitutionName5);

			}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + "CoinvestmentTab" , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + "CoinvestmentTab" );
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns17 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns17 + " tab");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}


//PC RECORF TYPE

@Parameters({ "projectName" })
@Test
public void ADETc137_VerifythaNewDealpopupgetsClosewhenCancelCrossIcongetsClicked(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String ExpectedHeader = "New Deal";
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns7, 30)) {
			if(BP.NewDealIcon( 10) != null) {
				log(LogStatus.PASS, "New Deal Icon is present", YesNo.No);
				if (CommonLib.click(driver, BP.NewDealIcon( 30), "New Deal Icon: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on New Deal Icon", YesNo.No);
					String actualHeader = getText(driver, BP.getNewDealpopupheader(20), "NewDealpopupheader",
							action.SCROLLANDBOOLEAN);
					/* if (ExpectedHeader.equals(actualHeader)) { */
						if (actualHeader.equals(ExpectedHeader)) {
						log(LogStatus.INFO,
								"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
										+ ExpectedHeader + " for New Finanacing popup",
								YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
										+ ExpectedHeader + "for New Finanacing popup",
								YesNo.No);
					}
					if (CommonLib.click(driver, BP.getNewFinancingPopupCancelIcon( 30), "New deal Cancel Icon: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on New deal cancel Icon", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to click on New deal cancel Icon", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on New deal cancel Icon" + " tab");
					}
					
				} else {
					log(LogStatus.ERROR, "Clicked on New deal Icon ", YesNo.Yes);
					sa.assertTrue(false, "Clicked on New deal Icon " + " tab");
				}

			} else {
				log(LogStatus.ERROR, "New deal Icon not  present", YesNo.Yes);
				sa.assertTrue(false, "New deal Icon not present");
			}
			refresh(driver);
			if(BP.NewDealIcon( 10) != null) {
				log(LogStatus.PASS, "New Deal Icon is present", YesNo.No);
				if (CommonLib.click(driver, BP.NewDealIcon( 30), "New Deal Icon: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on New Deal Icon", YesNo.No);
					String actualHeader = getText(driver, BP.getNewDealpopupheader(20), "NewDealpopupheader",
							action.SCROLLANDBOOLEAN);
					if (actualHeader.equals(ExpectedHeader)) {
						log(LogStatus.INFO,
								"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
										+ ExpectedHeader + " for New Finanacing popup",
								YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
										+ ExpectedHeader + "for New Finanacing popup",
								YesNo.No);
					}
					if (CommonLib.click(driver, BP.getNewFinancingPopupCrossIcon( 30), "New deal Cross Icon: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on New deal Cross Icon", YesNo.No);
					} else {
						log(LogStatus.ERROR, "Not able to click on New deal Cross Icon", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on New deal Cross Icon" + " tab");
					}
					
				} else {
					log(LogStatus.ERROR, "Clicked on New deal Icon ", YesNo.Yes);
					sa.assertTrue(false, "Clicked on New deal Icon " + " tab");
				}

			} else {
				log(LogStatus.ERROR, "New deal Icon not  present", YesNo.Yes);
				sa.assertTrue(false, "New deal Icon not present");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns7 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns7 + " tab");
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
public void ADETc138_VerfiytheFunctionalityofAddDealIconwhenFundsTabisSelectedatDealGridofPCFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String recordType = "";
	String dealName = ADEDeal32;
	String stage = ADEDeal32Stage;
	String dateReceived = "0";

	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns7, 30)) {
			if (CommonLib.click(driver, dp.NewDealIcon(30), tabObj4 + "New Deal Icon", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on the new Deal Icon", YesNo.No);
		if (dp.createDealfromIcon(recordType, dealName, stage, "Date Received", todaysDate)) {
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

			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns7, 30)) {

				log(LogStatus.INFO, "open created item" + ADEIns7, YesNo.No);

				if (BP.dealAcuityDealName(dealName, 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
					if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
//						if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
//							log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
							if (BP.dealAcuityHSRName(dealName, stage, 30) != null) {
								log(LogStatus.PASS, "HSR: " + stage + " is present", YesNo.No);
								String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
								
								if (cp.verifyDate(todaysDate,null, actualDate)) {
									log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
								}
								else {
								log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
									sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
								}
							} else {
								log(LogStatus.FAIL, "HSR stage name not present: " + stage, YesNo.Yes);
								sa.assertTrue(false, "HSR stage name not present: " + stage);

							}
						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}

//				} else {
//					log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
//					sa.assertTrue(false, "date receivednot present: " + dateReceived);
//				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns2 + " tab");
		}
		} else {
			log(LogStatus.ERROR, "Not able to click on the new button", YesNo.No);
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
public void ADETc139_1_DeleteDealVerifyImpactDealGridPEFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String dealsSectionHeaderMessage = "No items to display.";
	

    ArrayList<String> dealsSectionHeaderName = new ArrayList<String>();
    ArrayList<String> externalConnectionsSectionHeaderName = new ArrayList<String>();
	ArrayList<String> contactsSectionHeaderName = new ArrayList<String>();
	ArrayList<String> connectionsHeaders = new ArrayList<String>();

	if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
		if (fp.clickOnAlreadyCreatedItem(projectName,ADEDeal32, 10)) {
			cp.clickOnShowMoreDropdownOnly(projectName, PageName.Object4Page, "");
			log(LogStatus.INFO, "Able to Click on Show more Icon : " + TabName.Object4Tab + " For : " + ADEDeal32,
					YesNo.No);
			ThreadSleep(500);
			WebElement ele = cp.actionDropdownElement(projectName, PageName.Object4Page,
					ShowMoreActionDropDownList.Delete, 15);
			if (ele == null) {
				ele = cp.getDeleteButton(projectName, 30);
			}

			if (click(driver, ele, "Delete More Icon", action.BOOLEAN)) {
				log(LogStatus.INFO,
						"Able to Click on Delete more Icon : " + TabName.Object4Tab + " For : " + ADEDeal32,
						YesNo.No);
				ThreadSleep(1000);
				if (click(driver, cp.getDeleteButtonOnDeletePopUp(projectName, 30), "Delete Button",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Able to Click on Delete button on Delete PoPup : " + TabName.Object4Tab
							+ " For : " + ADEDeal32, YesNo.No);
					ThreadSleep(10000);
					if (cp.clickOnTab(projectName, TabName.Object4Tab)) {
						log(LogStatus.INFO, "Clicked on Tab : " + TabName.Object4Tab + " For : " + ADEDeal32,
								YesNo.No);
						ThreadSleep(1000);
						if (!cp.clickOnAlreadyCreatedItem(projectName, TabName.Object4Tab, ADEDeal32, 10)) {
							log(LogStatus.INFO, "Item has been Deleted after delete operation  : " + ADEDeal32
									+ " For : " + TabName.Object4Tab, YesNo.No);

						} else {
							sa.assertTrue(false, "Item has not been Deleted after delete operation  : " + ADEDeal32
									+ " For : " + TabName.Object4Tab);
							log(LogStatus.SKIP, "Item has not been Deleted after delete operation  : " + ADEDeal32
									+ " For : " + TabName.Object4Tab, YesNo.Yes);
						}

					} else {
						sa.assertTrue(false, "Not Able to Click on Tab after delete : " + TabName.Object4Tab
								+ " For : " + ADEDeal32);
						log(LogStatus.SKIP, "Not Able to Click on Tab after delete : " + TabName.Object4Tab
								+ " For : " + ADEDeal32, YesNo.Yes);
					}
				} else {
					log(LogStatus.INFO, "not able to click on delete button, so not deleted : " + TabName.Object4Tab
							+ " For : " + ADEDeal32, YesNo.No);
					sa.assertTrue(false, "not able to click on delete button, so not deleted : "
							+ TabName.Object4Tab + " For : " + ADEDeal32);
				}
			} else {
				log(LogStatus.INFO,
						"not Able to Click on Delete more Icon : " + TabName.Object4Tab + " For : " + ADEDeal32,
						YesNo.No);
				sa.assertTrue(false,
						"not Able to Click on Delete more Icon : " + TabName.Object4Tab + " For : " + ADEDeal32);
			}
		} else {
			log(LogStatus.INFO, "not Able to Click on " + ADEDeal32, YesNo.No);
			sa.assertTrue(false, "not Able to Click on " + ADEDeal32);
		}
	} else {
		log(LogStatus.INFO, "not Able to Click on " + TabName.Object4Tab, YesNo.No);
		sa.assertTrue(false, "not Able to Click on " + TabName.Object4Tab);
	}
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns7, 30)) {
		ArrayList<String> result1 = BP
				.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
						contactsSectionHeaderName, null, dealsSectionHeaderName,dealsSectionHeaderMessage, connectionsHeaders,
						null,externalConnectionsSectionHeaderName,null);
		if (result1.isEmpty()) {
			log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
		} else {
			log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
			sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
		}

		} else {

			sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns7);
			log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns7, YesNo.Yes);

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
public void ADETc139_2_RestoreDealVerifyImpactDealGridPEFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	WebElement ele = null;

	TabName tabName = TabName.RecycleBinTab;
	
	String dealName = ADEDeal32;
	String stage = ADEDeal32Stage;
	String dateReceived = todaysDate;
	
	if (cp.clickOnTab(projectName, tabName)) {
		log(LogStatus.INFO, "Clicked on Tab : " + tabName + " For : " + dealName, YesNo.No);
		ThreadSleep(1000);
		cp.clickOnAlreadyCreatedItem(projectName, tabName, dealName, 20);
		log(LogStatus.INFO, "Clicked on  : " + dealName + " For : " + tabName, YesNo.No);
		ThreadSleep(2000);

		ele = cp.getCheckboxOfRestoreItemOnRecycleBin(projectName, dealName, 10);
		if (clickUsingJavaScript(driver, ele, "Check box against : " + dealName, action.BOOLEAN)) {
			log(LogStatus.INFO, "Click on checkbox for " + dealName, YesNo.No);

			ThreadSleep(1000);
			ele = cp.getRestoreButtonOnRecycleBin(projectName, 10);
			if (clickUsingJavaScript(driver, ele, "Restore Button : " + dealName, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on Restore Button for " + dealName, YesNo.No);
				ThreadSleep(1000);
			} else {
				sa.assertTrue(false, "Not Able to Click on Restore Button for " + dealName);
				log(LogStatus.SKIP, "Not Able to Click on Restore Button for " + dealName, YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on checkbox for " + dealName);
			log(LogStatus.SKIP, "Not Able to Click on checkbox for " + dealName, YesNo.Yes);
		}

	} else {
		sa.assertTrue(false, "Not Able to Click on Tab : " + tabName + " For : " + dealName);
		log(LogStatus.SKIP, "Not Able to Click on Tab : " + tabName + " For : " + dealName, YesNo.Yes);
	}
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns7, 30)) {

			log(LogStatus.INFO, "open created item" + ADEIns7, YesNo.No);

			if (BP.dealAcuityDealName(dealName, 30) != null) {
				log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
				if (BP.dealAcuityStageName(dealName, stage, 30) != null) {
					log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
//					if (BP.dealAcuityDateReceived(dealName, dateReceived, 30) != null) {
//						log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
						if (BP.dealAcuityHSRName(dealName, stage, 30) != null) {

							log(LogStatus.PASS, "HSR: " + stage + " is present", YesNo.No);
							String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
							
							if (cp.verifyDate(todaysDate,null, actualDate)) {
								log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
							}
							else {
							log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
								sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
							}

						} else {
							log(LogStatus.FAIL, "HSR stage name not present: " + stage, YesNo.Yes);
							sa.assertTrue(false, "HSR stage name not present: " + stage);

						}
					
				} else {
					log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
					sa.assertTrue(false, "stage name not present: " + stage);

				}

//			} else {
//				log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
//				sa.assertTrue(false, "date receivednot present: " + dateReceived);
//			}
			} else {
				log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

			}

		} else {

			sa.assertTrue(false, "Not Able to open created source firm : " + ADEIns7);
			log(LogStatus.SKIP, "Not Able to open created source firm : " + ADEIns7, YesNo.Yes);

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
public void ADETc140_VerifyThatDealNamesClickableandDealRedirectionforAccounts(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String dealName = ADEDeal32;
	String recordType = "";
	String dealName1 = ADEDeal33;
	String stage = ADEDeal33Stage;
	String dateReceived = "0";

	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns7, 30)) {
			if (CommonLib.click(driver, dp.NewDealIcon(30), tabObj4 + "New Deal Icon", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on the new Deal Icon", YesNo.No);
		if (dp.createDealfromIcon(recordType, dealName1, stage, "Date Received", tomorrowsDate)) {
			log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);

		} else {
			log(LogStatus.ERROR, dealName1 + " deal is not created", YesNo.No);
			sa.assertTrue(false, dealName1 + " deal is not created");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
		sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
	}
		}
	}
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns7, 30)) {
			if (BP.dealAcuityDealName(dealName, 30) != null) {
				log(LogStatus.PASS, "deal name: " + dealName + " is hyperlink and is present", YesNo.No);
				if (clickUsingJavaScript(driver, BP.dealAcuityDealName(dealName, 10), "deal name: " + dealName,
						action.BOOLEAN)) {
					log(LogStatus.PASS, "Clicked on deal name: " + dealName, YesNo.No);
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
				log(LogStatus.FAIL, "deal name not present: " + dealName, YesNo.Yes);
				sa.assertTrue(false, "deal name not present: " + dealName);

			}
		
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns16 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns16 + " tab");
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
public void ADETc141_VerifyDealCountasZeroRedirectionContactatContactGridPEFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String DealCountInFirm = "0";
	String actualDealCount = null;
	String contactName = ADEContact7FName + " " + ADEContact7LName;

	String ExpectedMsg = BP.ErrorMessageAcuity;
		
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns7, 30)) {

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
						if (actualMsg.contains(ExpectedMsg)) {
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
						log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + contactName, YesNo.Yes);
						sa.assertTrue(false, "No New Window Open after click on Deal Link: " + contactName);
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + contactName + " contactDealCount", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + contactName + " contactDealCount");
				}
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns7 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns7 + " tab");
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
public void ADETc142_VerifyDealCountRedirectionForContactatContactGridofPEFirm(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	String DealCountInFirm = "1";
	String actualDealCount = null;
	String contactName = ADEDealContact22;
	
	String recordType = "";
	String dealName = ADEDeal32;
	String companyName = ADEDeal32CompanyName;
	String stage = ADEDeal32Stage;
	String dateReceived = todaysDate;
	
	String[][] data = { { PageLabel.Deal.toString(), dealName }, { PageLabel.Deal_Contact.toString(), contactName }};

	if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

		if (DTP.createDealTeam(projectName, dealName, data, TabName.Acuity.toString(), action.SCROLLANDBOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----", YesNo.No);

			log(LogStatus.INFO,
					"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
							+ contactName + " at Firm Tab under Acuity section---------",
					YesNo.No);
			WebElement ele = DTP.getDealTeamID(10);
		   if (ele != null) {
				String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
				ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name, "ADT_22",
						excelLabel.DealTeamID);
				log(LogStatus.INFO, "successfully created and noted id of DT" + id + " and deal name " + dealName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "could not create DT" + dealName);
				log(LogStatus.SKIP, "could not create DT" + dealName, YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ADEIns7 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ADEIns7 + " tab");
		}

	} else {
		log(LogStatus.ERROR, "Not able to click on " + TabName.Deal_Team + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + TabName.Deal_Team + " tab");
	}
		
			if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

				if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns7, 30)) {

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
						if (CommonLib.click(driver, BP.contactDealCount(contactName, 30), "Deal Count: " + actualDealCount,
								action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Deal Count: " + actualDealCount + " of Record: " + contactName,
									YesNo.No);
							String parentWindowId = CommonLib.switchOnWindow(driver);
							if (!parentWindowId.isEmpty()) {
								if (BP.dealAcuity2DealName(dealName, 30) != null) {
									log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
									if (BP.dealAcuity2StageName(dealName, stage, 30) != null) {
										log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
//										if (BP.dealAcuity2DateReceived(dealName, dateReceived, 30) != null) {
//											log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
											if (BP.dealAcuityPopUpCompanyName(dealName, companyName, 30) != null) {
												log(LogStatus.PASS, " Company name: " + companyName + " is present", YesNo.No);
												String actualDate= BP.dealAcuityDateReceived2(dealName, 30).getText();
												
												if (cp.verifyDate(todaysDate,null, actualDate)) {
													log(LogStatus.INFO,todaysDate+"Date Received: " + dateReceived + " is present", YesNo.No);
												}
												else {
												log(LogStatus.ERROR, "Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate, YesNo.Yes);
													sa.assertTrue(false,"Date Received is not matched For : "+dealName+" Actual : "+actualDate+" /t Expected : "+todaysDate );
												}
											} else {
												log(LogStatus.FAIL, "HSR stage name not present: " + stage, YesNo.Yes);
												sa.assertTrue(false, "HSR stage name not present: " + stage);

											}
										
									} else {
										log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
										sa.assertTrue(false, "stage name not present: " + stage);

									}


//								} else {
//									log(LogStatus.FAIL, "date received not present: " + dateReceived, YesNo.Yes);
//									sa.assertTrue(false, "date receivednot present: " + dateReceived);
//								}
								} else {
									log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
									sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

								}
							}
							driver.close();
							driver.switchTo().window(parentWindowId);
							
							} else {
								log(LogStatus.FAIL, "Clicked on Deal Count: " + actualDealCount + " of Record: " + contactName, YesNo.Yes);
								sa.assertTrue(false,  "Clicked on Deal Count: " + actualDealCount + " of Record: " + contactName);

							}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + ADEIns2 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + ADEIns2 + " tab");
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