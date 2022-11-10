package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;
import static com.navatar.generic.SmokeCommonVariables.crmUser1EmailID;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
	public void ADETc002_VerifyAccountAcuitytabvisibilityforNewlyCreatedAccounts(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		//INS
		String value="";
		String type="";
		String TabName1 ="";
		String[][] EntityOrAccounts = {{ ADEIns1, ADEIns1RecordType ,null} , { ADEIns2, ADEIns2RecordType ,null},
		 { ADEIns3, ADEIns3RecordType ,null}, { ADEIns4, ADEIns4RecordType ,null}, { ADEIns5, ADEIns5RecordType ,null},
		 { ADEIns6, ADEIns6RecordType ,null}};
		

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
	public void ADETc004_VerifyContactAcuitytabvisibilityforNewlycreatedContacts(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
         String TabName1="";
		// contact
				if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
					log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);	
					
					ADEContact1EmailID=	lp.generateRandomEmailId(gmailUserName);
					ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact1EmailID, "Contact", excelLabel.Variable_Name, "ADEContact1",excelLabel.Contact_EmailId);

					if (cp.createContactAcuity(projectName, ADEContact1FName, ADEContact1LName, ADEIns1, ADEContact1EmailID,ADEContact1RecordType, null, null, CreationPage.ContactPage, null, null)) {
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
	public void ADETc006_VerifyDealscardsectionvisibilityinAcuitytabforexistingAccountsANDContacts(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String ContactName= ADEContact1FName+" "+ ADEContact1LName;
		String AccountName =ADEIns1;
		String dealHeader="Deal Name<break>Company<break>Stage<break>Date Received";
		String message="No items to display";
		
		ArrayList<String> connectionsSectionHeaderName=new ArrayList<String>();
		
		ArrayList<String> contactsSectionHeaderName=new ArrayList<String>();
		
		String[] arrDealHeader=dealHeader.split("<break>");
		List<String> dealHeaders = new ArrayList<String>(Arrays.asList(arrDealHeader));
	
		
		if (cp.clickOnTab(projectName, tabObj1)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabObj1+" For : "+AccountName,YesNo.No);
			ThreadSleep(1000);
			if (cp.clickOnAlreadyCreatedItem(projectName, AccountName, 30)) {
				log(LogStatus.INFO,"Clicked on  : "+AccountName+" For : "+tabObj2,YesNo.No);
				ThreadSleep(2000);
				ArrayList<String> result1=bp.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,contactsSectionHeaderName,null,dealHeaders,message,connectionsSectionHeaderName,null);
				if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
						sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
					}
                 }else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+ADEIns1);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+ADEIns1,YesNo.Yes);
			
             }
               } else {
	              log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
	           sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
                 }
		
		if(fp.clickOnTab(environment,mode, TabName.Object2Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);
			
			if(fp.clickOnAlreadyCreatedItem(projectName, ContactName, 30)){
				
				log(LogStatus.INFO,"open created item"+ContactName,YesNo.No);
				ThreadSleep(2000);
				ArrayList<String> result1=bp.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,contactsSectionHeaderName,null,dealHeaders,message,connectionsSectionHeaderName,null);
				if(result1.isEmpty())
					{
						log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
						sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
					}
                }else{
				
				sa.assertTrue(false,"Not Able to open created source contact : "+ContactName);
				log(LogStatus.SKIP,"Not Able to open created source contact : "+ContactName,YesNo.Yes);
			
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
public void ADETc010_CreatedealsrecordtypecompanyandverifytheimpactDealtabbothcompanyIntermediarytypeaccount(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String recordType = "";
	String dealName = "Test D";
	String companyName =ADEIns1;
	String stage ="NDA Signed";
	String dateReceived = todaysDate;
	
	
	if (lp.clickOnTab(projectName, tabObj4)) {
		log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
		ThreadSleep(3000);
		if (dp.createDeal(recordType, dealName, companyName, stage, "Date Received",
				todaysDate)) {
			log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);

			
		} else {
			log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
			sa.assertTrue(false, dealName + " deal is not created");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
		sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
	}
	
	if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
		log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
		
		if(fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)){
			
			log(LogStatus.INFO,"open created item"+ADEIns1,YesNo.No);
			
			if (BP.dealAcuityDealName(dealName, 30) != null) {
				log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
				if (BP.dealAcuityStageName(dealName,stage, 30) != null) {
					log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
					if (BP.dealAcuityDateReceived(dealName,dateReceived, 30) != null) {
						log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
						
						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
				} else {
					log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
					sa.assertTrue(false, "stage name not present: " + stage);

				}

					}	else {
					log(LogStatus.FAIL, "date received not present: " +dateReceived , YesNo.Yes);
					sa.assertTrue(false, "date receivednot present: " + dateReceived);
				}
             }else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+ADEIns1);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+ADEIns1,YesNo.Yes);
			
             }
               } else {
	              log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
	           sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
                 }	
	
	if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
		log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
		
		if(fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)){
			
			log(LogStatus.INFO,"open created item"+ADEIns1,YesNo.No);
			
			if (BP.dealAcuityDealName("Test D", 30) != null) {
				log(LogStatus.PASS, "Deal Name: " + "Test D" + " is hyperlink and is present", YesNo.No);
			}else {
				sa.assertTrue(true,"is hyperlink and not present  : "+"Test D"+" For : "+TabName.Object4Tab);
				log(LogStatus.SKIP,"is hyperlink and not present  : "+"Test D"+" For : "+TabName.Object4Tab,YesNo.Yes);
			}
		 }else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+ADEIns1);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+ADEIns1,YesNo.Yes);
			
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
public void ADETc011_CreatedealsrecordtypecompanyandverifytheimpactDealtabbothcompanyIntermediarytypeaccount(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String recordType = "";
	String dealName = "NGCSD";
	String companyName =ADEIns8;
	String stage ="LOI";
	String dateReceived = todaysDate;
	String ContactName= ADEContact2FName+" "+ ADEContact2LName;

	String labellabels ="Source Firm<Break>Source Contact<Break>Date Received";
	String otherLabelValues = ADEIns9+"<Break>"+ADEContact2FName+" "+ ADEContact2LName+"<Break>"+todaysDate;
	String value="";
	String type="";
	
	String[][] EntityOrAccounts = {{ ADEIns8, ADEIns8RecordType ,null} , { ADEIns9, ADEIns9RecordType ,null}};
	
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
	if (lp.clickOnTab(projectName, tabObj4)) {
		log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
		ThreadSleep(3000);
		if (dp.createDeal(recordType, dealName, companyName, stage, labellabels,
				otherLabelValues)) {
			log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);

			
		} else {
			log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
			sa.assertTrue(false, dealName + " deal is not created");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
		sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
	}
	if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
		log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
		
		if(fp.clickOnAlreadyCreatedItem(projectName, ADEIns8, 30)){
			
			log(LogStatus.INFO,"open created item"+ADEIns8,YesNo.No);
			
			if (BP.dealAcuityDealName(dealName, 30) != null) {
				log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
				if (BP.dealAcuityStageName(dealName,stage, 30) != null) {
					log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
					if (BP.dealAcuityDateReceived(dealName,dateReceived, 30) != null) {
						log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
						
						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
				} else {
					log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
					sa.assertTrue(false, "stage name not present: " + stage);

				}

					}	else {
					log(LogStatus.FAIL, "date received not present: " +dateReceived , YesNo.Yes);
					sa.assertTrue(false, "date receivednot present: " + dateReceived);
				}
             }else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+ADEIns8);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+ADEIns8,YesNo.Yes);
			
             }
               } else {
	              log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
	           sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
                 }
	if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
		log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
		
		if(fp.clickOnAlreadyCreatedItem(projectName, ADEIns9, 30)){
			
			log(LogStatus.INFO,"open created item"+ADEIns9,YesNo.No);
			
			if (BP.dealAcuityDealName(dealName, 30) != null) {
				log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
				if (BP.dealAcuityStageName(dealName,stage, 30) != null) {
					log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
					if (BP.dealAcuityDateReceived(dealName,dateReceived, 30) != null) {
						log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
						
						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
				} else {
					log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
					sa.assertTrue(false, "stage name not present: " + stage);

				}

					}	else {
					log(LogStatus.FAIL, "date received not present: " +dateReceived , YesNo.Yes);
					sa.assertTrue(false, "date receivednot present: " + dateReceived);
				}
             }else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+ADEIns9);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+ADEIns9,YesNo.Yes);
			
             }
               } else {
	              log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
	           sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
                 }
	if(fp.clickOnTab(environment,mode, TabName.Object2Tab)){
		log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);
		
		if(fp.clickOnAlreadyCreatedItem(projectName, ContactName, 30)){
			
			log(LogStatus.INFO,"open created item"+ContactName,YesNo.No);
			
			if (BP.dealAcuityDealName(dealName, 30) != null) {
				log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
				if (BP.dealAcuityStageName(dealName,stage, 30) != null) {
					log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
					if (BP.dealAcuityDateReceived(dealName,dateReceived, 30) != null) {
						log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
						
						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
				} else {
					log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
					sa.assertTrue(false, "stage name not present: " + stage);

				}

					}	else {
					log(LogStatus.FAIL, "date received not present: " +dateReceived , YesNo.Yes);
					sa.assertTrue(false, "date receivednot present: " + dateReceived);
				}
             }else{
				
				sa.assertTrue(false,"Not Able to open created source contact : "+ContactName);
				log(LogStatus.SKIP,"Not Able to open created source contact : "+ContactName,YesNo.Yes);
			
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
public void ADETc012_CreateDealSourceFirmCompanyContactTypeSourceContactaddedverifyDealsectionAItabAccount(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String recordType = "";
	String dealName = "TD";
	String companyName =ADEIns1;
	String stage ="NDA Signed";
	String dateReceived = todaysDate;
	

	String labellabels ="Source Contact<Break>Date Received";
	String otherLabelValues = ADEContact8FName+" "+ ADEContact8LName+"<Break>"+todaysDate;
	
	if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
		log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);	
		
		ADEContact8EmailID=	lp.generateRandomEmailId(gmailUserName);
		ExcelUtils.writeData(AcuityDataSheetFilePath, ADEContact8EmailID, "Contact", excelLabel.Variable_Name, "ADEContact8",excelLabel.Contact_EmailId);

		if (cp.createContact(projectName, ADEContact8FName, ADEContact8LName, ADEIns1, ADEContact8EmailID,ADEContact1RecordType, null, null, CreationPage.ContactPage, null, null)) {
			log(LogStatus.INFO,"successfully Created Contact : "+M3Contact1FName+" "+M3Contact1LName,YesNo.No);	
		} else {
			sa.assertTrue(false,"Not Able to Create Contact : "+M3Contact1FName+" "+M3Contact1LName);
			log(LogStatus.SKIP,"Not Able to Create Contact: "+M3Contact1FName+" "+M3Contact1LName,YesNo.Yes);
		}
	} else {
		sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
		log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
	}
	if (lp.clickOnTab(projectName, tabObj4)) {
		log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
		ThreadSleep(3000);
		if (dp.createDeal(recordType, dealName, companyName, stage, labellabels,
				otherLabelValues)) {
			log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);

			
		} else {
			log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
			sa.assertTrue(false, dealName + " deal is not created");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
		sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
	}
	if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
		log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
		
		if(fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)){
			
			log(LogStatus.INFO,"open created item"+ADEIns1,YesNo.No);
			
			if (BP.dealAcuityDealName(dealName, 30) != null) {
				log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
				if (BP.dealAcuityStageName(dealName,stage, 30) != null) {
					log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
					if (BP.dealAcuityDateReceived(dealName,dateReceived, 30) != null) {
						log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
						
						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
				} else {
					log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
					sa.assertTrue(false, "stage name not present: " + stage);

				}

					}	else {
					log(LogStatus.FAIL, "date received not present: " +dateReceived , YesNo.Yes);
					sa.assertTrue(false, "date receivednot present: " + dateReceived);
				}
             }else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+ADEIns1);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+ADEIns1,YesNo.Yes);
			
             }
               } else {
	              log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
	           sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	            }
	if (BP.dealNameLinkInAcuityTab(dealName, 30) != null) {
		log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and it is Clickable", YesNo.No);

		if (clickUsingJavaScript(driver, BP.dealNameLinkInAcuityTab(dealName, 30),
				"Deal Name: " + dealName, action.BOOLEAN)) {
			log(LogStatus.PASS, "Clicked on Deal Name: " + dealName, YesNo.No);
			try {
				String parentWindowId = CommonLib.switchOnWindow(driver);
				if (!parentWindowId.isEmpty()) {
					log(LogStatus.PASS, "New Window Open after click on Deal Link: " + dealName,
							YesNo.No);

					if (BP.dealRecordPage(dealName, 20) != null) {
						log(LogStatus.PASS, "----Deal Detail Page is redirecting for Deal Record: "
								+ dealName + "-----", YesNo.No);
						driver.close();
						driver.switchTo().window(parentWindowId);

					} else {
						log(LogStatus.FAIL, "----Deal Detail Page is not redirecting for Deal Record: "
								+ dealName + "-----", YesNo.Yes);
						sa.assertTrue(false, "----Deal Detail Page is not showing for Deal Record: "
								+ dealName + "-----");
						driver.close();
						driver.switchTo().window(parentWindowId);

					}

				} else {
					log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + dealName,
							YesNo.Yes);
					sa.assertTrue(false, "No New Window Open after click on Deal Link: " + dealName);
				}
			} catch (Exception e) {
				log(LogStatus.FAIL,
						"Not able to switch to window after click on Deal Link, Msg showing: "
								+ e.getMessage(),
						YesNo.Yes);
				sa.assertTrue(false,
						"Not able to switch to window after click on Deal Link, Msg showing: "
								+ e.getMessage());
			}
		} else {
			log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
			sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

		}

	} else {
		log(LogStatus.FAIL, "Deal Name: " + dealName + " is not hyperlink and it is not Clickable",
				YesNo.Yes);
		sa.assertTrue(false, "Deal Name: " + dealName + " is not hyperlink and it is not Clickable");
	}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();

}
@Parameters({ "projectName"})
@Test
public void ADETc013_1_CreateDealSourceFirmCompany(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

	
//	String[] dealRecordType = ADEDealRecordType1.split("<Section>", -1);
	String[] dealName = ADEDealName1.split("<Section>", -1);
	String[] dealCompany = ADEDealCompany1.split("<Section>", -1);
	String[] dealStage = ADEDealStage1.split("<Section>", -1);
	String[]otherLabels = ADEDealOtherLabelNames1.split("<Section>", -1);
	String[]otherLabelValues = ADEDealOtherLabelValues1.split("<Section>", -1);
	
	
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

@Parameters({ "projectName"})
@Test
public void ADETc013_2_DealsDifferentStagesCompanyIntermediary(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
	
	String[] dealName = ADEDealName1.split("<Section>", -1);
	String[] dealStage = ADEDealStage1.split("<Section>", -1);
	String[]otherLabels = ADEDealOtherLabelNames1.split("<Section>", -1);
	String[]otherLabelValues = ADEDealOtherLabelValues1.split("<Section>", -1);
	String dateReceived = todaysDate;
	
	if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
		log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
		
		if(fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)){
			
			log(LogStatus.INFO,"open created item"+ADEIns1,YesNo.No);
			for (int i = 0; i <9; i++) {
			if (BP.dealAcuityDealName(dealName[i], 30) != null) {
				log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
				if (BP.dealAcuityStageName(dealName[i],dealStage[i], 30) != null) {
					log(LogStatus.PASS, "Stage Name: " + dealStage + " is present", YesNo.No);
					if (BP.dealAcuityDateReceived(dealName[i],dateReceived, 30) != null) {
						log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
						
						} else {
							log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

						}
				} else {
					log(LogStatus.FAIL, "stage name not present: " + dealStage, YesNo.Yes);
					sa.assertTrue(false, "stage name not present: " + dealStage);

				}

					}	else {
					log(LogStatus.FAIL, "date received not present: " +dateReceived , YesNo.Yes);
					sa.assertTrue(false, "date receivednot present: " + dateReceived);
					}}
             }else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+ADEIns1);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+ADEIns1,YesNo.Yes);
			
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
//public void ADETc014_VerifyDisablingSortingatDealSectionAICIAccounts(String projectName) {
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
	@Parameters({ "projectName"})
	@Test	
	public void ADETc015_DeleteExistingDealsVerifyImpactOnDealsAccounts(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName, "Test D", 10)) {
				cp.clickOnShowMoreDropdownOnly(projectName, PageName.Object4Page,"");
				log(LogStatus.INFO,"Able to Click on Show more Icon : "+TabName.Object4Tab+" For : "+"Test D",YesNo.No);
				ThreadSleep(500);
				WebElement ele = cp.actionDropdownElement(projectName, PageName.Object4Page, ShowMoreActionDropDownList.Delete, 15);
				 if (ele==null) {
					 ele =cp.getDeleteButton(projectName, 30);
				} 
				
				 if (click(driver, ele, "Delete More Icon", action.BOOLEAN)) {
					log(LogStatus.INFO,"Able to Click on Delete more Icon : "+TabName.Object4Tab+" For : "+"Test D",YesNo.No);
					ThreadSleep(1000);
					if (click(driver, cp.getDeleteButtonOnDeletePopUp(projectName, 30), "Delete Button", action.BOOLEAN)) {
						log(LogStatus.INFO,"Able to Click on Delete button on Delete PoPup : "+TabName.Object4Tab+" For : "+"Test D",YesNo.No);
						ThreadSleep(10000);
						if (cp.clickOnTab(projectName, TabName.Object4Tab)) {
							log(LogStatus.INFO,"Clicked on Tab : "+TabName.Object4Tab+" For : "+"Test D",YesNo.No);
							ThreadSleep(1000);
							if (!cp.clickOnAlreadyCreatedItem(projectName, TabName.Object4Tab, "Test D", 10)) {
								log(LogStatus.INFO,"Item has been Deleted after delete operation  : "+"Test D"+" For : "+TabName.Object4Tab,YesNo.No);

							}else {
								sa.assertTrue(false,"Item has not been Deleted after delete operation  : "+"Test D"+" For : "+TabName.Object4Tab);
								log(LogStatus.SKIP,"Item has not been Deleted after delete operation  : "+"Test D"+" For : "+TabName.Object4Tab,YesNo.Yes);
							}

						}else {
							sa.assertTrue(false,"Not Able to Click on Tab after delete : "+TabName.Object4Tab+" For : "+"Test D");
							log(LogStatus.SKIP,"Not Able to Click on Tab after delete : "+TabName.Object4Tab+" For : "+"Test D",YesNo.Yes);	
						}
					}else {
						log(LogStatus.INFO,"not able to click on delete button, so not deleted : "+TabName.Object4Tab+" For : "+"Test D",YesNo.No);
						sa.assertTrue(false,"not able to click on delete button, so not deleted : "+TabName.Object4Tab+" For : "+"Test D");
					}
				 }else {
					 log(LogStatus.INFO,"not Able to Click on Delete more Icon : "+TabName.Object4Tab+" For : "+"Test D",YesNo.No);
					 sa.assertTrue(false,"not Able to Click on Delete more Icon : "+TabName.Object4Tab+" For : "+"Test D");
				 }
			}else {
				 log(LogStatus.INFO,"not Able to Click on "+"Test D",YesNo.No);
				 sa.assertTrue(false,"not Able to Click on "+"Test D");
			 }
		}else {
			 log(LogStatus.INFO,"not Able to Click on "+TabName.Object4Tab,YesNo.No);
			 sa.assertTrue(false,"not Able to Click on "+TabName.Object4Tab);
		 }
		if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
			
			if(fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)){
				
				log(LogStatus.INFO,"open created item"+ADEIns1,YesNo.No);
				
				if (BP.dealAcuityDealName("Test D", 30) != null) {
					log(LogStatus.PASS, "Deal Name: " + "Test D" + " is hyperlink and is present", YesNo.No);
				}else {
					sa.assertTrue(true,"is hyperlink and not present  : "+"Test D"+" For : "+TabName.Object4Tab);
					log(LogStatus.SKIP,"is hyperlink and not present  : "+"Test D"+" For : "+TabName.Object4Tab,YesNo.Yes);
				}
			 }else{
					
					sa.assertTrue(false,"Not Able to open created source firm : "+ADEIns1);
					log(LogStatus.SKIP,"Not Able to open created source firm : "+ADEIns1,YesNo.Yes);
				
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
public void ADETc016_RestoreDeletedDealsandDealSectionAccounts(String projectName) {
			LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
			ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
			FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
			BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
			lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
			WebElement ele = null ;
		
			
			TabName tabName = TabName.RecycleBinTab;
			String name = "Test D";
			
				if (cp.clickOnTab(projectName, tabName)) {
					log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
					ThreadSleep(1000);
					cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 20);
						log(LogStatus.INFO,"Clicked on  : "+name+" For : "+tabName,YesNo.No);
						ThreadSleep(2000);
						
						 ele=cp.getCheckboxOfRestoreItemOnRecycleBin(projectName, name, 10);
						 if (clickUsingJavaScript(driver, ele, "Check box against : "+name, action.BOOLEAN)) {
							 log(LogStatus.INFO,"Click on checkbox for "+name,YesNo.No);
							 
							 ThreadSleep(1000);
							 ele=cp.getRestoreButtonOnRecycleBin(projectName, 10);
							 if (clickUsingJavaScript(driver, ele, "Restore Button : "+name, action.BOOLEAN)) {
								 log(LogStatus.INFO,"Click on Restore Button for "+name,YesNo.No);
								 ThreadSleep(1000);
							} else {
								sa.assertTrue(false,"Not Able to Click on Restore Button for "+name);
								log(LogStatus.SKIP,"Not Able to Click on Restore Button for "+name,YesNo.Yes);
							}
							 
						} else {
							sa.assertTrue(false,"Not Able to Click on checkbox for "+name);
							log(LogStatus.SKIP,"Not Able to Click on checkbox for "+name,YesNo.Yes);
						}

				} else {
					sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
					log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
				}
				if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
					log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
					
					if(fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)){
						
						log(LogStatus.INFO,"open created item"+ADEIns1,YesNo.No);
						
						if (BP.dealAcuityDealName("Test D", 30) != null) {
							log(LogStatus.PASS, "Deal Name: " + "Test D" + " is hyperlink and is present", YesNo.No);
						}else {
							sa.assertTrue(false,"is hyperlink and not present  : "+"Test D"+" For : "+TabName.Object4Tab);
							log(LogStatus.SKIP,"is hyperlink and not present  : "+"Test D"+" For : "+TabName.Object4Tab,YesNo.Yes);
						}
					 }else{
							
							sa.assertTrue(false,"Not Able to open created source firm : "+ADEIns1);
							log(LogStatus.SKIP,"Not Able to open created source firm : "+ADEIns1,YesNo.Yes);
						
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
public void ADETc017_EditExistingCompanyDealVerifyimpactDealCompanyAccount(String projectName) {
			LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
			ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
			FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
			BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
			lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
			String dealname ="ADECTD New";
			
			if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
				if (fp.clickOnAlreadyCreatedItem(projectName, "TD", 10)){
					if (fp.UpdateDealName(projectName,dealname , 10)) {
						log(LogStatus.INFO,"successfully changed name to "+dealname,YesNo.Yes);
					}else {
						sa.assertTrue(false,"not able to change name to "+dealname);
						log(LogStatus.SKIP,"not able to change name to "+dealname,YesNo.Yes);
					}
					if (fp.changeStage(projectName, Stage.LOI.toString(), 10)) {
						log(LogStatus.INFO,"successfully changed stage to "+Stage.LOI,YesNo.Yes);
						
					}else {
						sa.assertTrue(false,"not able to change stage to "+Stage.LOI);
						log(LogStatus.SKIP,"not able to change stage to "+Stage.LOI,YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"not able to find pipeline "+"TD");
					log(LogStatus.SKIP,"not able to find pipeline "+"TD",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"not able to click on deal tab");
				log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
			}
			String dealName = "ADECTD New";
			String stage ="LOI";
			String dateReceived ="11/2/2022";
			
			if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
				
				if(fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)){
					
					log(LogStatus.INFO,"open created item"+ADEIns1,YesNo.No);
					
					if (BP.dealAcuityDealName(dealName, 30) != null) {
						log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
						if (BP.dealAcuityStageName(dealName,stage, 30) != null) {
							log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
							if (BP.dealAcuityDateReceived(dealName,dateReceived, 30) != null) {
								log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
								
								} else {
									log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
									sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

								}
						} else {
							log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
							sa.assertTrue(false, "stage name not present: " + stage);

						}

							}	else {
							log(LogStatus.FAIL, "date received not present: " +dateReceived , YesNo.Yes);
							sa.assertTrue(false, "date receivednot present: " + dateReceived);
						}
		             }else{
						
						sa.assertTrue(false,"Not Able to open created source firm : "+ADEIns1);
						log(LogStatus.SKIP,"Not Able to open created source firm : "+ADEIns1,YesNo.Yes);
					
		             }
		               } else {
			              log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			           sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			            }
			String dealName1 = "ADECTD New";
			String stage1 ="LOI";
			String dateReceived1 ="11/2/2022";
			String companyname = ADEIns2;
			if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
				if (fp.clickOnAlreadyCreatedItem(projectName, "ADECTD New", 10)){
					if (fp.UpdateCompanyName(projectName,companyname , 10)) {
						log(LogStatus.INFO,"successfully changed name to "+companyname,YesNo.Yes);
					}else {
						sa.assertTrue(false,"not able to change name to "+companyname);
						log(LogStatus.SKIP,"not able to change name to "+companyname,YesNo.Yes);
					}	
					if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
						log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
						
						if(fp.clickOnAlreadyCreatedItem(projectName, ADEIns2, 30)){
							
							log(LogStatus.INFO,"open created item"+ADEIns2,YesNo.No);
							
							if (BP.dealAcuityDealName(dealName1, 30) != null) {
								log(LogStatus.PASS, "Deal Name: " + dealName1 + " is hyperlink and is present", YesNo.No);
								if (BP.dealAcuityStageName(dealName1,stage1, 30) != null) {
									log(LogStatus.PASS, "Stage Name: " + stage1 + " is present", YesNo.No);
									if (BP.dealAcuityDateReceived(dealName1,dateReceived1, 30) != null) {
										log(LogStatus.PASS, "Date Received: " + dateReceived1 + " is present", YesNo.No);
										
										} else {
											log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName1, YesNo.Yes);
											sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName1);

										}
								} else {
									log(LogStatus.FAIL, "stage name not present: " + stage1, YesNo.Yes);
									sa.assertTrue(false, "stage name not present: " + stage1);

								}

									}	else {
									log(LogStatus.FAIL, "date received not present: " +dateReceived1 , YesNo.Yes);
									sa.assertTrue(false, "date receivednot present: " + dateReceived1);
								}
				             }else{
								
								sa.assertTrue(false,"Not Able to open created source firm : "+ADEIns2);
								log(LogStatus.SKIP,"Not Able to open created source firm : "+ADEIns2,YesNo.Yes);
							
				             }
				               } else {
					              log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
					           sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
					            }
					
				}

			switchToDefaultContent(driver);
			lp.CRMlogout();
			sa.assertAll();
		}}
@Parameters({ "projectName"})
@Test
public void ADETc018_1_RenameStageNamesCheckImpactDealSectionAccountContact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
	String parentID=null;
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	String[][] newAndOldStage = {  {Stage.Due_Diligence.toString(),Stage.DD.toString()}, 
			{Stage.IOI.toString(),Stage.IOL.toString()}};
	if (home.clickOnSetUpLink()) {
		parentID=switchOnWindow(driver);
		if (parentID!=null) {
			if (sp.searchStandardOrCustomObject(environment, mode,object.Deal )) {
				if(sp.clickOnObjectFeature(environment, mode,object.Deal, ObjectFeatureName.FieldAndRelationShip)) {
					if (sendKeys(driver, sp.getsearchTextboxFieldsAndRelationships(10), excelLabel.Stage.toString()+Keys.ENTER, "status", action.BOOLEAN)) {

						if (sp.clickOnAlreadyCreatedLayout(excelLabel.Stage.toString())) {
							for (int i = 0;i<newAndOldStage.length;i++) {
								switchToDefaultContent(driver);
								switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
								WebElement ele=sp.clickOnEditInFrontOfFieldValues(projectName, newAndOldStage[i][0]);
								if (click(driver, ele, "watchlist", action.BOOLEAN)) {
									switchToDefaultContent(driver);
									ThreadSleep(3000);
									switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
									sendKeys(driver, sp.getFieldLabelTextBox1(10), newAndOldStage[i][1], "label", action.BOOLEAN);
										

									if (clickUsingJavaScript(driver, fp.getCustomTabSaveBtn(10), "save", action.BOOLEAN)) {

										log(LogStatus.INFO, "successfully changed watchlist label", YesNo.No);
									}else {
										sa.assertTrue(false,"not able to click on save button");
										log(LogStatus.SKIP,"not able to click on save button",YesNo.Yes);
									}

								}else {
									sa.assertTrue(false,"edit button is not clickable");
									log(LogStatus.SKIP,"edit button is not clickable",YesNo.Yes);
								}
							}
							ThreadSleep(3000);
							switchToDefaultContent(driver);
							switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
							WebElement ele=sp.clickOnEditInFrontOfFieldValues(projectName, newAndOldStage[0][1]);
							WebElement ele1=null;
							ele1=sp.clickOnEditInFrontOfFieldValues(projectName, newAndOldStage[1][1]);
							if ((ele!=null)&&(ele1!=null)) {
								log(LogStatus.INFO,"successfully verified rename of stage values",YesNo.No);	
								
							}else {
								log(LogStatus.ERROR,"stage field is not renamed",YesNo.No);	
								sa.assertTrue(false,"stage field is not renamed" );
							}
						}else {
							sa.assertTrue(false,"stage field is not clickable");
							log(LogStatus.SKIP,"stage field is not clickable",YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"field textbox is not visible");
						log(LogStatus.SKIP,"field textbox is not visible",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"field and relationships is not clickable");
					log(LogStatus.SKIP,"field and relationships is not clickable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"activity object is not clickable");
				log(LogStatus.SKIP,"activity object is not clickable",YesNo.Yes);
			}
			ThreadSleep(3000);
			driver.close();
			driver.switchTo().window(parentID);
		}else {
			sa.assertTrue(false,"new window is not found, so cannot change watchlist label");
			log(LogStatus.SKIP,"new window is not found, so cannot change watchlist label",YesNo.Yes);
		}
	}else {
		sa.assertTrue(false,"setup link is not clickable");
		log(LogStatus.SKIP,"setup link is not clickable",YesNo.Yes);
	}
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName"})
@Test
public void ADETc018_2_RenameStageNamesCheckImpactDealSectionAccountContact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
		log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
		
		if(fp.clickOnAlreadyCreatedItem(projectName, ADEIns1, 30)){
			log(LogStatus.INFO,"open created item"+ADEIns1,YesNo.No);
			
			String dealname = "ADEMD2";
			String stage ="DD";
			
				if (BP.dealAcuityStageName(dealname,stage, 30) != null) {
					log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
						
				} else {
					log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
					sa.assertTrue(false, "stage name not present: " + stage);

				}
				String dealname1 = "ADEMD3";
				String stage1 ="IOL";
				
					if (BP.dealAcuityStageName(dealname1,stage1, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
							
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);

					}
				
             }else{
				
				sa.assertTrue(false,"Not Able to open created source firm : "+ADEIns1);
				log(LogStatus.SKIP,"Not Able to open created source firm : "+ADEIns1,YesNo.Yes);
			
             }
               } else {
	              log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
	           sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	            }
	
	
	refresh(driver);
	
	
	if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
		log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
		
		if(fp.clickOnAlreadyCreatedItem(projectName, ADEIns2, 30)){
			log(LogStatus.INFO,"open created item"+ADEIns2,YesNo.No);
			String dealname = "ADEMD14";
			String stage ="DD";
			
				if (BP.dealAcuityStageName(dealname,stage, 30) != null) {
					log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
						
				} else {
					log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
					sa.assertTrue(false, "stage name not present: " + stage);

				}
				String dealname1 = "ADEMD13";
				String stage1 ="IOL";
				
					if (BP.dealAcuityStageName(dealname1,stage1, 30) != null) {
						log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
							
					} else {
						log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
						sa.assertTrue(false, "stage name not present: " + stage);
					
		             }
		}else{
						
						sa.assertTrue(false,"Not Able to open created source firm : "+ADEIns1);
						log(LogStatus.SKIP,"Not Able to open created source firm : "+ADEIns1,YesNo.Yes);
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
public void ADETc019_ChangeCompanyTypeRecordPortfolioforAccountVerifyDealSectionAccount(String projectName) {
			LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
			ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
			FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
			BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
			InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
			InstitutionsPageBusinessLayer ins = new InstitutionsPageBusinessLayer(driver);
			BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
			DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
			lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
			String value="";
			String type="";
			String[][] EntityOrAccounts = {{ ADEIns10,ADEIns10RecordType ,null}};
			String labelName1[]={excelLabel.Record_Type.toString()};
			String labelValues1[]={InstRecordType.Portfolio_Company.toString()};
			
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
				String dealName = "MNC D1";
				String companyName =ADEIns10;
				String stage ="DD";
				String dateReceived = todaysDate;
				String ContactName= ADEContact2FName+" "+ ADEContact2LName;

				String labellabels = "Date Received";
				String otherLabelValues =todaysDate;
				if (lp.clickOnTab(projectName, tabObj4)) {
					log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
					ThreadSleep(3000);
					if (dp.createDeal(recordType, dealName, companyName, stage, labellabels,
							otherLabelValues)) {
						log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);

						
					} else {
						log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
						sa.assertTrue(false, dealName + " deal is not created");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
					sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
				}
				
				
				if(fp.clickOnTab(environment,mode, TabName.Object1Tab)){
					log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);
					
					if(fp.clickOnAlreadyCreatedItem(projectName, ADEIns10, 30)){
						
						log(LogStatus.INFO,"open created item"+ADEIns10,YesNo.No);
						
						if (BP.dealAcuityDealName(dealName, 30) != null) {
							log(LogStatus.PASS, "Deal Name: " + dealName + " is hyperlink and is present", YesNo.No);
							if (BP.dealAcuityStageName(dealName,stage, 30) != null) {
								log(LogStatus.PASS, "Stage Name: " + stage + " is present", YesNo.No);
								if (BP.dealAcuityDateReceived(dealName,dateReceived, 30) != null) {
									log(LogStatus.PASS, "Date Received: " + dateReceived + " is present", YesNo.No);
									
									} else {
										log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
										sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

									}
							} else {
								log(LogStatus.FAIL, "stage name not present: " + stage, YesNo.Yes);
								sa.assertTrue(false, "stage name not present: " + stage);

							}

								}	else {
								log(LogStatus.FAIL, "date received not present: " +dateReceived , YesNo.Yes);
								sa.assertTrue(false, "date receivednot present: " + dateReceived);
							}
			             }else{
							
							sa.assertTrue(false,"Not Able to open created source firm : "+ADEIns10);
							log(LogStatus.SKIP,"Not Able to open created source firm : "+ADEIns10,YesNo.Yes);
						
			             }
			               } else {
				              log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				           sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
				            }
				
				
				
				if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
					if (ip.clickOnAlreadyCreatedItem(projectName, "MNC D1", 10)) {
						if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
							if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
								String text=dp.getconvertToPortfolioMessageAfterNext(10).getText();
								if (text.contains(dp.convertToPortfolioAfterNext(M6Ins2))) {
									log(LogStatus.INFO,"successfully verified convert to portfolio message congratulations",YesNo.Yes);
									
								}else {
									sa.assertTrue(false,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins2)+" actual: "+text);
									log(LogStatus.SKIP,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins2)+" actual: "+text,YesNo.Yes);
								}
								if (dp.getconvertToPortfolioCrossButton(10)!=null) {
									log(LogStatus.INFO,"cross button is present",YesNo.Yes);
									
								}else {
									sa.assertTrue(false,"could not verify cross icon presence");
									log(LogStatus.SKIP,"could not verify cross icon presence",YesNo.Yes);
								}
								if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
									log(LogStatus.INFO, "successfully verified finish button after convert to portfolio", YesNo.No);
								}else {
									sa.assertTrue(false,"could not verify convert to portfolio as finish button not clicked");
									log(LogStatus.SKIP,"could not verify convert to portfolio as finish button not clicked",YesNo.Yes);
								}
							}else {
								sa.assertTrue(false,"could not verify convert to portfolio as finish button not clicked");
								log(LogStatus.SKIP,"could not verify convert to portfolio as finish button not clicked",YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"could not verify convert to portfolio as finish button not clicked");
							log(LogStatus.SKIP,"could not verify convert to portfolio as finish button not clicked",YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"could not click on "+M6Deal2);
						log(LogStatus.SKIP,"could not click on "+M6Deal2,YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"could not click on deal tab");
					log(LogStatus.SKIP,"could not click on deal tab",YesNo.Yes);
				}
				
				
				if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
					if (ip.clickOnAlreadyCreatedItem(projectName, ADEIns10, 10)) {
						for(int i = 0;i<labelName1.length;i++) {
						if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
							log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);

						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
						
						 
						}
						String TabName1 = "";
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
						}
					}else{
								
								sa.assertTrue(false,"Not Able to open created source firm : "+ADEIns10);
								log(LogStatus.SKIP,"Not Able to open created source firm : "+ADEIns10,YesNo.Yes);
							
				             
				               }
				}else {
					              log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
					           sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
					            }
						switchToDefaultContent(driver);
						lp.CRMlogout();
						sa.assertAll();
				}
						
	
@Parameters({ "projectName"})
@Test
public void ADETc020_1_UpdatefieldcolumnNamesCheckDealsectionAccountandContactTab(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
	SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
	String parentID=null;
	String updateLabel="Updated Stage";
	lp.CRMLogin(superAdminUserName, adminPassword, appName);
	if (home.clickOnSetUpLink()) {
		parentID=switchOnWindow(driver);
		if (parentID!=null) {
			if (sp.searchStandardOrCustomObject(environment, mode,object.Override)){
				log(LogStatus.INFO, "click on Object : " +object.valueOf("Override"), YesNo.No);
				ThreadSleep(2000);				
				switchToFrame(driver, 30, sp.getSetUpPageIframe(60));
				ThreadSleep(5000);	
				if(selectVisibleTextFromDropDown(driver, sp.getOverrideSetupComponentDropdown(10), "Override setup component dropdown", "Custom Field")){
					log(LogStatus.INFO, "Select custom field text in setup component dropdown in override setup page", YesNo.No);
					ThreadSleep(5000);	
					
					if(selectVisibleTextFromDropDown(driver, sp.getOverrideObjectDropdown(10), "Override object dropdown",PageLabel.Deal.toString())){
						log(LogStatus.INFO, "Select "+PageLabel.Deal.toString()+" text in object dropdown in override setup page", YesNo.No);
						ThreadSleep(5000);	
						if(sp.updateFieldLabelInOverridePage(driver, PageLabel.Stage.toString(), updateLabel, action.SCROLLANDBOOLEAN)){
							log(LogStatus.INFO, "Field label: "+PageLabel.Stage.toString()+" successfully update to "+updateLabel, YesNo.No);
							
						}else{
							log(LogStatus.ERROR, "Not able to update Field label: "+PageLabel.Stage.toString()+" successfully update to "+updateLabel, YesNo.Yes);
							sa.assertTrue(false, "Not able to update Field label: "+PageLabel.Stage.toString()+" to "+updateLabel);	
						}
					}else{
						log(LogStatus.ERROR, "Not able to select text: "+PageLabel.Activity.toString()+" in  object dropdown in override page", YesNo.Yes);
						sa.assertTrue(false, "Not able to select text: "+PageLabel.Activity.toString()+" in  object dropdown in override page");
					}
				}else{
					log(LogStatus.ERROR, "Not able to select text: Custom Field in  setup component dropdown in override page", YesNo.Yes);
					sa.assertTrue(false, "Not able to select text: Custom Field in  setup component dropdown in override page");
				}
			
		}else{
			
			log(LogStatus.PASS, "Not able to click on Object : " + object.valueOf("Override"), YesNo.Yes);
			sa.assertTrue(false, "Not able to click on Object : " + object.valueOf("Override"));
		}
		}else {
			sa.assertTrue(false,"new window is not found, so cannot change watchlist label");
			log(LogStatus.SKIP,"new window is not found, so cannot change watchlist label",YesNo.Yes);
		}
	}else {
		sa.assertTrue(false,"setup link is not clickable");
		log(LogStatus.SKIP,"setup link is not clickable",YesNo.Yes);
	}
	lp.CRMlogout();
	sa.assertAll();
}}
				