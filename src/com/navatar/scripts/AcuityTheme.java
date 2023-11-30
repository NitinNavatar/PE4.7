package com.navatar.scripts;

import com.navatar.generic.EnumConstants.IconType;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.action;
import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;
import static com.navatar.generic.SmokeCommonVariables.crmUser1EmailID;
import static com.navatar.generic.SmokeCommonVariables.gmailUserName;
import static com.navatar.generic.SmokeCommonVariables.rgUserPassword;
import static com.navatar.generic.SmokeCommonVariables.superAdminUserName;
import static com.navatar.generic.SmokeCommonVariables.rgOrgPassword;
import static com.navatar.generic.SmokeCommonVariables.rgContact1;
import static com.navatar.generic.SmokeCommonVariables.rgContact3;
import static com.navatar.generic.SmokeCommonVariables.rgUser1;
import static com.navatar.generic.SmokeCommonVariables.rgUser2;
import static com.navatar.generic.SmokeCommonVariables.rgUser3;
import static com.navatar.generic.SmokeCommonVariables.rgContactPassword;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

public class AcuityTheme extends BaseLib {

	
	@Parameters({ "projectName" })

	@Test
	public void AcuityThemeTc001_CreateCRMUser1(String projectName) {
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

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc001_CreateCRMUser2(String projectName) {
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

	@Parameters({ "projectName" })

	@Test
	public void AcuityThemeTc002_CreateCustomFieldsForObjects(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);

		String[][] labelAndValues = {
				{ ATFC_1_FieldType, ATFC_1_FieldLabel, excelLabel.Length.toString(), ATFC_1_FieldValues,
						ATFC_1_ObjectName },
				{ ATFC_2_FieldType, ATFC_2_FieldLabel, excelLabel.Length.toString(), ATFC_2_FieldValues,
						ATFC_2_ObjectName },
				{ ATFC_3_FieldType, ATFC_3_FieldLabel, excelLabel.Length.toString(), ATFC_3_FieldValues,
						ATFC_3_ObjectName },
				{ ATFC_4_FieldType, ATFC_4_FieldLabel, excelLabel.Length.toString(), ATFC_4_FieldValues,
						ATFC_4_ObjectName },
				{ ATFC_5_FieldType, ATFC_5_FieldLabel, excelLabel.Length.toString(), ATFC_5_FieldValues,
						ATFC_5_ObjectName },
				{ ATFC_6_FieldType, ATFC_6_FieldLabel, excelLabel.Length.toString(), ATFC_6_FieldValues,
						ATFC_6_ObjectName },
				{ ATFC_7_FieldType, ATFC_7_FieldLabel, excelLabel.Length.toString(), ATFC_7_FieldValues,
						ATFC_7_ObjectName } };

		setup.createFieldsForCustomObjects(projectName, labelAndValues);
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityThemeTc003_1_CreateCustomRecordTypeForContactDealAndTarget(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);

		String contactRecordTypeList = AT_RecordTypes_1;
		String contactRecordTypeArray[] = contactRecordTypeList.split(breakSP, -1);

		String dealRecordTypeList = AT_RecordTypes_2;
		String dealRecordTypeArray[] = dealRecordTypeList.split(breakSP, -1);

		String targetRecordTypeList = AT_RecordTypes_3;
		String targetRecordTypeArray[] = targetRecordTypeList.split(breakSP, -1);
		String[] profileForSelection = { "MnA Standard User" };
		boolean isMakeAvailable = false;
		boolean isMakeDefault = true;
		boolean flag = false;
		String recordTypeDescription = AT_RecordTypes_Description_1;
		String dealLayout = AT_RecordTypes_Layout_2;

		String[][][] contactRecordType = {
				{ { recordTypeLabel.Record_Type_Label.toString(), contactRecordTypeArray[0] },
						{ recordTypeLabel.Description.toString(), contactRecordTypeArray[0] + recordTypeDescription } },
				{ { recordTypeLabel.Record_Type_Label.toString(), contactRecordTypeArray[1] }, {
						recordTypeLabel.Description.toString(), contactRecordTypeArray[1] + recordTypeDescription } } };

		String[][][] dealRecordType = {
				{ { recordTypeLabel.Record_Type_Label.toString(), dealRecordTypeArray[0] },
						{ recordTypeLabel.Description.toString(), dealRecordTypeArray[0] + recordTypeDescription } },
				{ { recordTypeLabel.Record_Type_Label.toString(), dealRecordTypeArray[1] },
						{ recordTypeLabel.Description.toString(), dealRecordTypeArray[1] + recordTypeDescription } },
				{ { recordTypeLabel.Record_Type_Label.toString(), dealRecordTypeArray[2] },
						{ recordTypeLabel.Description.toString(), dealRecordTypeArray[2] + recordTypeDescription } },
				{ { recordTypeLabel.Record_Type_Label.toString(), dealRecordTypeArray[3] },
						{ recordTypeLabel.Description.toString(), dealRecordTypeArray[3] + recordTypeDescription } },
				{ { recordTypeLabel.Record_Type_Label.toString(), dealRecordTypeArray[4] },
						{ recordTypeLabel.Description.toString(), dealRecordTypeArray[4] + recordTypeDescription } } };

		String[][][] targetRecordType = {
				{ { recordTypeLabel.Record_Type_Label.toString(), targetRecordTypeArray[0] },
						{ recordTypeLabel.Description.toString(), targetRecordTypeArray[0] + recordTypeDescription } },
				{ { recordTypeLabel.Record_Type_Label.toString(), targetRecordTypeArray[1] }, {
						recordTypeLabel.Description.toString(), targetRecordTypeArray[1] + recordTypeDescription } } };

		lp.CRMLogin(superAdminUserName, adminPassword);
		for (int i = 0; i < contactRecordTypeArray.length; i++) {
			home.notificationPopUpClose();
			if (home.clickOnSetUpLink()) {
				flag = false;
				String parentID = switchOnWindow(driver);

				if (parentID != null) {
					if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(), object.Contact.toString())) {
						if (sp.clickOnObjectFeature("", Mode.Lightning.toString(), object.Contact,
								ObjectFeatureName.recordTypes)) {
							if (i == 0) {
								if (sp.listOfRecordTypes().contains(contactRecordType[i][0][1])) {
									log(LogStatus.INFO, "Record Type: " + contactRecordType[i][0][1]
											+ " is already created, So not going to Create", YesNo.No);
									flag = true;
								} else {
									flag = sp.createRecordTypeForObject(projectName, contactRecordType[i],
											isMakeAvailable, profileForSelection, isMakeDefault, null, 10);
								}

							} else {
								isMakeDefault = false;

								if (sp.listOfRecordTypes().contains(contactRecordType[i][0][1])) {
									log(LogStatus.INFO, "Record Type: " + contactRecordType[i][0][1]
											+ " is already created, So not going to Create", YesNo.No);
									flag = true;
								} else {
									flag = sp.createRecordTypeForObject(projectName, contactRecordType[i],
											isMakeAvailable, profileForSelection, isMakeDefault, null, 10);
								}
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
						log(LogStatus.ERROR, object.Contact + " object could not be found in object manager",
								YesNo.Yes);
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

		for (int i = 0; i < dealRecordTypeArray.length; i++) {
			home.notificationPopUpClose();
			if (home.clickOnSetUpLink()) {
				flag = false;
				String parentID = switchOnWindow(driver);

				if (parentID != null) {
					if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(), object.Deal.toString())) {
						if (sp.clickOnObjectFeature("", Mode.Lightning.toString(), object.Deal,
								ObjectFeatureName.recordTypes)) {
							if (i == 0) {
								if (sp.listOfRecordTypes().contains(dealRecordType[i][0][1])) {
									log(LogStatus.INFO, "Record Type: " + dealRecordType[i][0][1]
											+ " is already created, So not going to Create", YesNo.No);
									flag = true;
								} else {
									flag = sp.createRecordTypeForObject(projectName, dealRecordType[i], isMakeAvailable,
											profileForSelection, isMakeDefault, dealLayout, 10);
								}

							} else {
								isMakeDefault = false;

								if (sp.listOfRecordTypes().contains(dealRecordType[i][0][1])) {
									log(LogStatus.INFO, "Record Type: " + dealRecordType[i][0][1]
											+ " is already created, So not going to Create", YesNo.No);
									flag = true;
								} else {
									flag = sp.createRecordTypeForObject(projectName, dealRecordType[i], isMakeAvailable,
											profileForSelection, isMakeDefault, dealLayout, 10);
								}
							}
							if (flag) {
								log(LogStatus.INFO, "Created Record Type : " + dealRecordTypeArray[i], YesNo.No);
							} else {
								log(LogStatus.ERROR, "Not Able to Create Record Type : " + dealRecordTypeArray[i],
										YesNo.Yes);
								sa.assertTrue(false, "Not Able to Create Record Type : " + dealRecordTypeArray[i]);
							}

						} else {
							log(LogStatus.ERROR,
									"object feature " + ObjectFeatureName.recordTypes + " is not clickable", YesNo.Yes);
							sa.assertTrue(false,
									"object feature " + ObjectFeatureName.recordTypes + " is not clickable");
						}
					} else {
						log(LogStatus.ERROR, object.Deal + " object could not be found in object manager", YesNo.Yes);
						sa.assertTrue(false, object.Deal + " object could not be found in object manager");
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

		for (int i = 0; i < targetRecordTypeArray.length; i++) {
			home.notificationPopUpClose();
			if (home.clickOnSetUpLink()) {
				flag = false;
				String parentID = switchOnWindow(driver);

				if (parentID != null) {
					if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(), object.Target.toString())) {
						if (sp.clickOnObjectFeature("", Mode.Lightning.toString(), object.Target,
								ObjectFeatureName.recordTypes)) {
							if (i == 0) {
								if (sp.listOfRecordTypes().contains(targetRecordType[i][0][1])) {
									log(LogStatus.INFO, "Record Type: " + targetRecordType[i][0][1]
											+ " is already created, So not going to Create", YesNo.No);
									flag = true;
								} else {
									flag = sp.createRecordTypeForObject(projectName, targetRecordType[i],
											isMakeAvailable, profileForSelection, isMakeDefault, null, 10);
								}

							} else {
								isMakeDefault = false;

								if (sp.listOfRecordTypes().contains(targetRecordType[i][0][1])) {
									log(LogStatus.INFO, "Record Type: " + targetRecordType[i][0][1]
											+ " is already created, So not going to Create", YesNo.No);
									flag = true;
								} else {
									flag = sp.createRecordTypeForObject(projectName, targetRecordType[i],
											isMakeAvailable, profileForSelection, isMakeDefault, null, 10);
								}
							}
							if (flag) {
								log(LogStatus.INFO, "Created Record Type : " + targetRecordTypeArray[i], YesNo.No);
							} else {
								log(LogStatus.ERROR, "Not Able to Create Record Type : " + targetRecordTypeArray[i],
										YesNo.Yes);
								sa.assertTrue(false, "Not Able to Create Record Type : " + targetRecordTypeArray[i]);
							}

						} else {
							log(LogStatus.ERROR,
									"object feature " + ObjectFeatureName.recordTypes + " is not clickable", YesNo.Yes);
							sa.assertTrue(false,
									"object feature " + ObjectFeatureName.recordTypes + " is not clickable");
						}
					} else {
						log(LogStatus.ERROR, object.Target + " object could not be found in object manager", YesNo.Yes);
						sa.assertTrue(false, object.Target + " object could not be found in object manager");
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

		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityThemeTc003_2_RecordTypeOfObjectsInActive(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String contactRecordTypeList = AT_RecordTypes_1;
		String contactRecordTypeArray[] = contactRecordTypeList.split(breakSP, -1);

		String dealRecordTypeList = AT_RecordTypes_4;
		String dealRecordTypeArray[] = dealRecordTypeList.split(breakSP, -1);

		String targetRecordTypeList = AT_RecordTypes_3;
		String targetRecordTypeArray[] = targetRecordTypeList.split(breakSP, -1);

		String[][] recordTypeFields = { { recordTypeLabel.Active.toString(), "Not Checked" } };
		String profileName = "MnA Standard User";

		lp.CRMLogin(superAdminUserName, adminPassword);

		if (bp.editOfRecordType(projectName, contactRecordTypeArray, recordTypeFields, object.Contact, true,
				profileName)) {
			log(LogStatus.INFO, "----Record Types Gets InActive for Object: " + object.Contact + "----", YesNo.No);

		} else {
			log(LogStatus.ERROR, "----Record Types not Gets InActive for Object: " + object.Contact + "----", YesNo.No);
			sa.assertTrue(false, "----Record Types not Gets InActive for Object: " + object.Contact + "----");
		}

		if (bp.editOfRecordType(projectName, dealRecordTypeArray, recordTypeFields, object.Deal, true, profileName)) {
			log(LogStatus.INFO, "----Record Types Gets InActive for Object: " + object.Deal + "----", YesNo.No);

		} else {
			log(LogStatus.ERROR, "----Record Types not Gets InActive for Object: " + object.Deal + "----", YesNo.No);
			sa.assertTrue(false, "----Record Types not Gets InActive for Object: " + object.Deal + "----");
		}

		if (bp.editOfRecordType(projectName, targetRecordTypeArray, recordTypeFields, object.Target, true,
				profileName)) {
			log(LogStatus.INFO, "----Record Types Gets InActive for Object: " + object.Target + "----", YesNo.No);

		} else {
			log(LogStatus.ERROR, "----Record Types not Gets InActive for Object: " + object.Target + "----", YesNo.No);
			sa.assertTrue(false, "----Record Types not Gets InActive for Object: " + object.Target + "----");
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityThemeTc004_CreateThemeRecordFromNewThemeButton(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String[] themeNameAndDescriptions = AT_Theme1.split("<Section>", -1);

		String themeTabName = tabObj9;

		String[] buttonName = AT_Theme_ButtonName_1.split("<downArrowBreak>", -1);
		List<String> ExpectedButtonsOnPage = Arrays.asList(buttonName[0].split("<Break>", -1));
		List<String> ExpectedButtonsInDownArrowButton = Arrays.asList(buttonName[1].split("<Break>", -1));

		List<String> expectedListOfTabs = Arrays.asList(AT_Theme_TabName_1.split("<break>", -1));
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (lp.clickOnTab(projectName, themeTabName)) {
			log(LogStatus.INFO, "Click on Tab : " + themeTabName, YesNo.No);

			if (theme.themeNoItemDisplay(10) != null) {

				log(LogStatus.INFO, "Verified: No items to display msg is showing", YesNo.No);

			} else {

				log(LogStatus.ERROR, "No items to display msg is not showing", YesNo.No);
				sa.assertTrue(false, "No items to display msg is not showing");
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + themeTabName + " Tab", YesNo.No);
			sa.assertTrue(false, "Record: " + themeTabName + " has not been Created under: " + themeTabName);
		}

		String themeName = null;
		String themeDescription = null;
		for (String themeNameAndDescription : themeNameAndDescriptions) {

			String[] themeNameAndDescriptionList = themeNameAndDescription.split("<Break>", -1);

			themeName = themeNameAndDescriptionList[0];
			themeDescription = themeNameAndDescriptionList[1];
			if (theme.createTheme(projectName, themeTabName, themeName, themeDescription)) {
				log(LogStatus.INFO, "Record: " + themeName + " has been Created under: " + themeTabName, YesNo.No);
			} else {
				log(LogStatus.ERROR, "Record: " + themeName + " has not been Created under: " + themeTabName, YesNo.No);
				sa.assertTrue(false, "Record: " + themeName + " has not been Created under: " + themeTabName);
			}

		}

		List<String> negativeResultOfButtons = theme.verifyButtonsOnAPageAndInDownArrowButtonInTheme(true, projectName,
				themeTabName, themeName, ExpectedButtonsOnPage, ExpectedButtonsInDownArrowButton, false, false);
		if (negativeResultOfButtons.isEmpty()) {
			log(LogStatus.PASS, "-----Verified Buttons on Page & in DownArrow Button-----", YesNo.No);
			sa.assertTrue(true, "-----Verified Buttons on Page & in DownArrow Button-----");
		} else {
			sa.assertTrue(false,
					"-----Not Verified Buttons on Page & in DownArrow Button-----: " + negativeResultOfButtons);
			log(LogStatus.FAIL, "Not Verified Buttons on Page & in DownArrow Button-----: " + negativeResultOfButtons,
					YesNo.Yes);
		}

		List<String> negativeResult = theme.verifyTabsOnAPage(expectedListOfTabs);

		if (negativeResult.isEmpty()) {
			log(LogStatus.INFO, "----Verified Tabs in Theme Tab----", YesNo.No);

		} else {
			log(LogStatus.ERROR, "----Not Verified Tabs in Theme Tab, Reason: " + negativeResult + "----", YesNo.No);
			sa.assertTrue(false, "----Not Verified Tabs in Theme Tab, Reason: " + negativeResult + "----");
		}
		driver.close();
		driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityThemeTc005_CopyThemeFunctionalityWithCoppyAllInteractionAndWithoutCopyAll(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeTabName = tabObj9;

		String themeName = AT_Theme2;
		String themeDescription = "";
		String newNameOfThemeForCopy = null;
		String errorMsg = BasePageErrorMessage.copyThemeErrorMsg1;

		String themeName2 = AT_Theme2;
		String themeDescription2 = AT_Theme_Description_3;
		String newNameOfThemeForCopy2 = AT_Theme3;
		String errorMsg2 = null;

		String themeName3 = AT_Theme2;
		String themeDescription3 = AT_Theme_Description_3;
		String newNameOfThemeForCopy3 = AT_Theme4;
		String errorMsg3 = null;

		String themeName4 = AT_Theme2;
		String themeDescription4 = "";
		String newNameOfThemeForCopy4 = null;
		String errorMsg4 = BasePageErrorMessage.copyThemeErrorMsg2;
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		if (theme.createCopyTheme(false, true, true, true, true, true, PageName.ThemesPage, projectName, themeTabName,
				themeName, null, null, newNameOfThemeForCopy, themeDescription, errorMsg)) {
			log(LogStatus.INFO, "Copy Theme of Theme Name: " + themeName + " has been created", YesNo.No);
		} else {
			log(LogStatus.ERROR, "Copy Theme of Theme Name: " + themeName + " has not been created", YesNo.No);
			sa.assertTrue(false, "Copy Theme of Theme Name: " + themeName + " has not been created");
		}

		if (theme.createCopyTheme(false, true, true, true, true, false, PageName.ThemesPage, projectName, themeTabName,
				themeName2, null, null, newNameOfThemeForCopy2, themeDescription2, errorMsg2)) {
			log(LogStatus.INFO, "Copy Theme of Existing Theme Name: " + themeName2 + " has been created with Name: "
					+ newNameOfThemeForCopy2, YesNo.No);
		} else {
			log(LogStatus.ERROR, "Copy Theme of Existing Theme Name: " + themeName2
					+ " has not been created with Name: " + newNameOfThemeForCopy2, YesNo.No);
			sa.assertTrue(false, "Copy Theme of Existing Theme Name: " + themeName2
					+ " has not been created with Name: " + newNameOfThemeForCopy2);
		}

		if (theme.createCopyTheme(true, true, true, true, true, false, PageName.ThemesPage, projectName, themeTabName,
				themeName3, null, null, newNameOfThemeForCopy3, themeDescription3, errorMsg3)) {
			log(LogStatus.INFO, "Copy Theme of Existing Theme Name: " + themeName3 + " has been created with Name: "
					+ newNameOfThemeForCopy3, YesNo.No);
		} else {
			log(LogStatus.ERROR, "Copy Theme of Existing Theme Name: " + themeName3
					+ " has not been created with Name: " + newNameOfThemeForCopy3, YesNo.No);
			sa.assertTrue(false, "Copy Theme of Existing Theme Name: " + themeName3
					+ " has not been created with Name: " + newNameOfThemeForCopy3);
		}

		if (theme.createCopyTheme(false, true, true, true, true, false, PageName.ThemesPage, projectName, themeTabName,
				themeName4, null, null, newNameOfThemeForCopy4, themeDescription4, errorMsg4)) {
			log(LogStatus.INFO, "Copy Theme of Theme Name: " + themeName + " has been created", YesNo.No);
		} else {
			log(LogStatus.ERROR, "Copy Theme of Theme Name: " + themeName + " has not been created", YesNo.No);
			sa.assertTrue(false, "Copy Theme of Theme Name: " + themeName + " has not been created");
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityThemeTc006_VerifyEditThemeFunctionality(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeTabName = tabObj9;

		String themeName = AT_Theme2;
		String updatedThemeName = AT_Theme5;
		String updatedThemeDescription1 = AT_Theme_Description_5;
		String updatedThemeDescription2 = "";
		String updatedThemeDescription3 = AT_Theme_Description_6;

		String[][] labelAndValues1 = { { themeLabels.Description.toString(), updatedThemeDescription1 } };
		String[][] labelAndValues2 = { { themeLabels.Description.toString(), updatedThemeDescription2 } };
		String[][] labelAndValues3 = { { themeLabels.Description.toString(), updatedThemeDescription3 } };
		String member = AT_Theme_TeamMember_7;
		String updatedThemeDescription2Verification = AT_Theme_Description_7;

		lp.CRMLogin(crmUser1EmailID, adminPassword);
		if (theme.UpdateTheme(true, themeTabName, themeName, true, projectName, updatedThemeName, labelAndValues3,
				false, 20, updateThemeButton.Cancel)) {
			log(LogStatus.INFO, "Theme " + themeName + " has been Updated", YesNo.No);
			CommonLib.refresh(driver);
			if (theme.checkDescriptionAndTeamMember(AT_Theme_Description_7, member, false)) {
				log(LogStatus.INFO, "-----Verified Theme " + themeName + " description and Team Member Value------",
						YesNo.No);

			} else {
				log(LogStatus.ERROR,
						"-----Theme " + themeName + " description and Team Member Value not verified------", YesNo.No);
				sa.assertTrue(false,
						"-----Theme " + themeName + " description and Team Member Value not verified------");
			}
		} else {
			log(LogStatus.ERROR, "Theme " + themeName + " has not been Updated", YesNo.No);
			sa.assertTrue(false, "Theme " + themeName + " has not been Updated");
		}

		if (theme.UpdateTheme(false, themeTabName, themeName, true, projectName, null, labelAndValues1, false, 20,
				updateThemeButton.Save)) {
			log(LogStatus.INFO, "Theme " + themeName + " has been Updated", YesNo.No);
			CommonLib.refresh(driver);
			if (theme.checkDescriptionAndTeamMember(updatedThemeDescription1, member, false)) {
				log(LogStatus.INFO, "-----Verified Theme " + themeName + " description and Team Member Value------",
						YesNo.No);

			} else {
				log(LogStatus.ERROR,
						"-----Theme " + themeName + " description and Team Member Value not verified------", YesNo.No);
				sa.assertTrue(false,
						"-----Theme " + themeName + " description and Team Member Value not verified------");
			}
		} else {
			log(LogStatus.ERROR, "Theme " + themeName + " has not been Updated", YesNo.No);
			sa.assertTrue(false, "Theme " + themeName + " has not been Updated");
		}

		if (theme.UpdateTheme(false, themeTabName, themeName, true, projectName, null, labelAndValues2, false, 20,
				updateThemeButton.Save)) {
			log(LogStatus.INFO, "Theme " + themeName + " has been Updated", YesNo.No);

			CommonLib.refresh(driver);
			if (theme.checkDescriptionAndTeamMember(updatedThemeDescription2Verification, member, false)) {
				log(LogStatus.INFO, "-----Verified Theme " + themeName + " description and Team Member Value------",
						YesNo.No);

			} else {
				log(LogStatus.ERROR,
						"-----Theme " + themeName + " description and Team Member Value not verified------", YesNo.No);
				sa.assertTrue(false,
						"-----Theme " + themeName + " description and Team Member Value not verified------");
			}
		} else {
			log(LogStatus.ERROR, "Theme " + themeName + " has not been Updated", YesNo.No);
			sa.assertTrue(false, "Theme " + themeName + " has not been Updated");
		}

		if (theme.UpdateTheme(false, themeTabName, themeName, true, projectName, updatedThemeName, labelAndValues3,
				true, 20, updateThemeButton.Save_And_New)) {
			log(LogStatus.INFO, "Theme " + themeName + " has been Updated", YesNo.No);

			if (theme.navigateToTheme(projectName, themeTabName, updatedThemeName, false)) {
				log(LogStatus.ERROR, "-----Able to Navigate To Theme: " + updatedThemeName + "------", YesNo.No);
				if (theme.checkDescriptionAndTeamMember(updatedThemeDescription3, member, true)) {
					log(LogStatus.INFO,
							"-----Verified Theme " + updatedThemeName + " description and Team Member Value------",
							YesNo.No);

				} else {
					log(LogStatus.ERROR,
							"-----Theme " + updatedThemeName + " description and Team Member Value not verified------",
							YesNo.No);
					sa.assertTrue(false,
							"-----Theme " + updatedThemeName + " description and Team Member Value not verified------");

				}
			} else {
				log(LogStatus.ERROR, "-----Not Able to Navigate To Theme: " + updatedThemeName + "------", YesNo.No);
				sa.assertTrue(false, "-----Not Able to Navigate To Theme: " + updatedThemeName + "------");
			}

		} else {
			log(LogStatus.ERROR, "Theme " + themeName + " has not been Updated", YesNo.No);
			sa.assertTrue(false, "Theme " + themeName + " has not been Updated");
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityThemeTc007_CreateTeamMember(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeTabName = tabObj9;

		String themeName = AT_Theme5;
		String updatedThemeDescription = AT_Theme_Description_6;

		String member1 = crmUser1FirstName + " " + crmUser1LastName;
		String role1 = AT_Theme_TeamRole_8;
		String title1 = "";
		String member2 = crmUser2FirstName + " " + crmUser2LastName;
		String role2 = AT_Theme_TeamRole_9;
		String title2 = "";
		String member = crmUser1FirstName + " " + crmUser1LastName;
		String role = AT_Theme_TeamRole_9;
		String title = "";

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (theme.createTeamMember(true, themeTabName, themeName, projectName, false, themeName, member1, role1, title1,
				true, teamMemberNavigation.Action_Button, true, false, null)) {
			log(LogStatus.INFO,
					"Team Member has been Created: " + member1 + " with Role: " + role1 + " of Theme: " + themeName,
					YesNo.No);
			CommonLib.refresh(driver);
			if (theme.checkDescriptionAndTeamMember(updatedThemeDescription, member1, false)) {
				log(LogStatus.INFO, "-----Verified Theme " + themeName + " description and Team Member Value------",
						YesNo.No);

			} else {
				log(LogStatus.ERROR,
						"-----Theme " + themeName + " description and Team Member Value not verified------", YesNo.No);
				sa.assertTrue(false,
						"-----Theme " + themeName + " description and Team Member Value not verified------");
			}

		} else {
			log(LogStatus.ERROR,
					"Team Member has not been Created: " + member1 + " with Role: " + role1 + " of Theme: " + themeName,
					YesNo.No);
			sa.assertTrue(false, "Team Member has not been Created: " + member1 + " with Role: " + role1 + " of Theme: "
					+ themeName);
		}
		if (theme.createTeamMember(false, themeTabName, themeName, projectName, false, themeName, member2, role2,
				title2, true, teamMemberNavigation.Action_Button, true, false, null)) {
			log(LogStatus.INFO,
					"Team Member has been Created: " + member2 + " with Role: " + role2 + " of Theme: " + themeName,
					YesNo.No);
			CommonLib.refresh(driver);
			if (theme.checkDescriptionAndTeamMember(updatedThemeDescription, member2, false)) {
				log(LogStatus.INFO, "-----Verified Theme " + themeName + " description and Team Member Value------",
						YesNo.No);

			} else {
				log(LogStatus.ERROR,
						"-----Theme " + themeName + " description and Team Member Value not verified------", YesNo.No);
				sa.assertTrue(false,
						"-----Theme " + themeName + " description and Team Member Value not verified------");
			}

		} else {
			log(LogStatus.ERROR,
					"Team Member has not been Created: " + member2 + " with Role: " + role2 + " of Theme: " + themeName,
					YesNo.No);
			sa.assertTrue(false, "Team Member has not been Created: " + member2 + " with Role: " + role2 + " of Theme: "
					+ themeName);
		}

		if (theme.createTeamMember(false, themeTabName, themeName, projectName, false, themeName, member, role, title,
				true, teamMemberNavigation.Action_Button, true, false, null)) {
			log(LogStatus.INFO,
					"Team Member has been Created: " + member + " with Role: " + role + " of Theme: " + themeName,
					YesNo.No);
			CommonLib.refresh(driver);
			if (theme.checkDescriptionAndTeamMember(updatedThemeDescription, member1, false)) {
				log(LogStatus.INFO, "-----Verified Theme " + themeName + " description and Team Member Value------",
						YesNo.No);

			} else {
				log(LogStatus.ERROR,
						"-----Theme " + themeName + " description and Team Member Value not verified------", YesNo.No);
				sa.assertTrue(false,
						"-----Theme " + themeName + " description and Team Member Value not verified------");
			}

			if (theme.checkDescriptionAndTeamMember(updatedThemeDescription, member2, false)) {
				log(LogStatus.INFO, "-----Verified Theme " + themeName + " description and Team Member Value------",
						YesNo.No);

			} else {
				log(LogStatus.ERROR,
						"-----Theme " + themeName + " description and Team Member Value not verified------", YesNo.No);
				sa.assertTrue(false,
						"-----Theme " + themeName + " description and Team Member Value not verified------");
			}
			if (theme.checkDescriptionAndTeamMember(updatedThemeDescription, member, true)) {
				log(LogStatus.INFO, "-----Verified Theme " + themeName + " description and Team Member Value------",
						YesNo.No);

			} else {
				log(LogStatus.ERROR,
						"-----Theme " + themeName + " description and Team Member Value not verified------", YesNo.No);
				sa.assertTrue(false,
						"-----Theme " + themeName + " description and Team Member Value not verified------");
				driver.close();
				driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
			}

		} else {
			log(LogStatus.ERROR,
					"Team Member has not been Created: " + member + " with Role: " + role + " of Theme: " + themeName,
					YesNo.No);
			sa.assertTrue(false,
					"Team Member has not been Created: " + member + " with Role: " + role + " of Theme: " + themeName);
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityThemeTc008_UpdateUserNameAndTitleThenVerifyTeamMemberInTheme(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);

		String themeTabName = tabObj9;

		String userfirstname = crmUser1FirstName;
		String userlastname = crmUser1LastName + "updated";
		String userEmailID = crmUser1EmailID;
		String title = AT_Theme_TeamTitle_10;
		String basedOnValueToWriteInExcel = "User 1";

		String themeName = AT_Theme5;
		String updatedThemeDescription = AT_Theme_Description_6;

		String member = userfirstname + " " + userlastname;
		String role = AT_Theme_TeamRole_9;

		String userfirstnameReversed = crmUser1FirstName;
		String userlastnameReversed = crmUser1LastName;

		if (setup.loginAndEditUser(userfirstname, userlastname, userEmailID, title, basedOnValueToWriteInExcel,
				false)) {
			log(LogStatus.INFO, "-----User Name and Title has been Update------", YesNo.No);

			lp.CRMlogout();
			CommonLib.ThreadSleep(5000);
			lp.CRMLogin(crmUser1EmailID, adminPassword);

			if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {
				log(LogStatus.ERROR, "-----Able to Navigate To Theme: " + themeName + "------", YesNo.No);

				List<String> negativeResult = theme.teamMemberDataVerify(false, projectName, themeTabName, themeName,
						member, title, role, false);

				if (negativeResult.isEmpty()) {
					log(LogStatus.INFO, "-----Verified Theme " + themeName
							+ " description, Team Member and Role Value in Theme Subtab------", YesNo.No);

				} else {
					log(LogStatus.ERROR, "-----Not Verified Theme " + themeName
							+ " description, Team Member and Role Value in Theme Subtab------", YesNo.No);
					sa.assertTrue(false, "-----Not Verified Theme " + themeName
							+ " description, Team Member and Role Value in Theme Subtab------");
				}

				if (theme.checkDescriptionAndTeamMember(updatedThemeDescription, member, true)) {
					log(LogStatus.INFO, "-----Verified Theme " + themeName + " description and Team Member Value------",
							YesNo.No);

				} else {
					log(LogStatus.ERROR,
							"-----Theme " + themeName + " description and Team Member Value not verified------",
							YesNo.No);
					sa.assertTrue(false,
							"-----Theme " + themeName + " description and Team Member Value not verified------");
				}

			} else {
				log(LogStatus.ERROR, "-----Not Able to Navigate To Theme: " + themeName + "------", YesNo.No);
				sa.assertTrue(false, "-----Not Able to Navigate To Theme: " + themeName + "------");
			}

			lp.CRMlogout();

			CommonLib.ThreadSleep(5000);

			if (setup.loginAndEditUser(userfirstnameReversed, userlastnameReversed, userEmailID, title,
					basedOnValueToWriteInExcel, false)) {
				log(LogStatus.INFO, "-----User Name and Title has been Update------", YesNo.No);

			} else {
				log(LogStatus.ERROR, "-----User Name and Title has not been Update------", YesNo.No);
				sa.assertTrue(false, "-----User Name and Title has not been Update------");
			}

			lp.CRMlogout();

		} else {
			log(LogStatus.ERROR, "-----User Name and Title has not been Update------", YesNo.No);
			sa.assertTrue(false, "-----User Name and Title has not been Update------");
			lp.CRMlogout();
		}

		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityThemeTc009_VerifyDeleteAndRestoreThemeFunctionality(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeTabName = tabObj9;
		String updatedThemeDescription = AT_Theme_Description_6;

		String member = crmUser1FirstName + " " + crmUser1LastName;

		String themeName = AT_Theme5;
		boolean themeDelete = false;
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		if (theme.deleteTheme(true, themeTabName, themeName, projectName, true, 10)) {
			log(LogStatus.INFO, "Theme " + themeName + " has been Deleted, verified by Toast Msg", YesNo.No);

			if (theme.verifyThemePresence(true, false, themeName, projectName)) {
				log(LogStatus.INFO, "-----Verified Theme " + themeName + " has been Deleted------", YesNo.No);

				themeDelete = true;

			} else {
				log(LogStatus.ERROR, "-----Theme " + themeName + " has  not been Deleted------", YesNo.No);
				sa.assertTrue(false, "-----Theme " + themeName + " has  not been Deleted------");
			}

		} else {
			log(LogStatus.ERROR, "Theme " + themeName + " has not been Deleted", YesNo.No);
			sa.assertTrue(false, "Theme " + themeName + " has not been Deleted");
		}

		if (themeDelete) {
			log(LogStatus.INFO, "-----Now, Going to Restore Theme " + themeName + "------", YesNo.No);

			String recycleTab = lp.getTabName(projectName, TabName.RecycleBinTab);
			lp.searchAndClickOnApp(recycleTab, 60);
			WebElement ele = lp.getCheckboxOfRestoreItemOnRecycleBin(projectName, themeName, 30);
			if (clickUsingJavaScript(driver, ele, "Check box against : " + themeName, action.BOOLEAN)) {
				log(LogStatus.INFO, "Click on checkbox for " + themeName, YesNo.No);

				ele = lp.getRestoreButtonOnRecycleBin(projectName, 30);
				if (clickUsingJavaScript(driver, ele, "Restore Button : " + themeName, action.BOOLEAN)) {
					log(LogStatus.INFO, "Click on Restore Button for " + themeName, YesNo.No);
					CommonLib.ThreadSleep(5000);
					if (theme.verifyThemePresence(true, true, themeName, projectName)) {
						log(LogStatus.INFO,
								"-----Verified Theme " + themeName + " has been Present after Restore------", YesNo.No);

						if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {
							log(LogStatus.ERROR, "-----Able to Navigate To Theme: " + themeName + "------", YesNo.No);

							if (theme.checkDescriptionAndTeamMember(updatedThemeDescription, member, true)) {
								log(LogStatus.INFO,
										"-----Verified Theme " + themeName + " description and Team Member Value------",
										YesNo.No);

							} else {
								log(LogStatus.ERROR, "-----Theme " + themeName
										+ " description and Team Member Value not verified------", YesNo.No);
								sa.assertTrue(false, "-----Theme " + themeName
										+ " description and Team Member Value not verified------");
							}

						} else {
							log(LogStatus.ERROR, "-----Not Able to Navigate To Theme: " + themeName + "------",
									YesNo.No);
							sa.assertTrue(false, "-----Not Able to Navigate To Theme: " + themeName + "------");
						}

					} else {
						log(LogStatus.ERROR, "-----Theme " + themeName + " has not been Present after Restore------",
								YesNo.No);
						sa.assertTrue(false, "-----Theme " + themeName + " has not been Present after Restore------");
					}
				} else {
					sa.assertTrue(false, "Not Able to Click on Restore Button for " + themeName);
					log(LogStatus.SKIP, "Not Able to Click on Restore Button for " + themeName, YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on checkbox for " + themeName);
				log(LogStatus.SKIP, "Not Able to Click on checkbox for " + themeName, YesNo.Yes);
			}

		} else {
			log(LogStatus.ERROR, "Theme " + themeName + " has not been Deleted, SO not able to Restore It", YesNo.No);
			sa.assertTrue(false, "Theme " + themeName + " has not been Deleted, SO not able to Restore It");
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc010_RemoveTeamMemberAndRevertThemeName(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeTabName = tabObj9;
		String updatedThemeDescription = AT_Theme_Description_6;

		String member = crmUser2FirstName + " " + crmUser2LastName;
		String role = AT_Theme_TeamRole_9;

		String updatedThemeName = AT_Theme2;
		String[][] labelAndValues = null;

		String themeName = AT_Theme5;
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		if (theme.removeTeamMember(true, themeTabName, themeName, projectName, true, member, role, true, false, null)) {
			log(LogStatus.INFO, "Verified Team Member has Been Removed for Theme " + themeName, YesNo.No);

			if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {
				log(LogStatus.ERROR, "-----Able to Navigate To Theme: " + themeName + "------", YesNo.No);

				if (!theme.checkDescriptionAndTeamMember(updatedThemeDescription, member, true)) {
					log(LogStatus.INFO, "-----Verified Theme " + themeName + " description and Team Member Value------",
							YesNo.No);

				} else {
					log(LogStatus.ERROR,
							"-----Theme " + themeName + " description and Team Member Value not verified------",
							YesNo.No);
					sa.assertTrue(false,
							"-----Theme " + themeName + " description and Team Member Value not verified------");
				}
				driver.close();
				driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

			} else {
				log(LogStatus.ERROR, "-----Not Able to Navigate To Theme: " + themeName + "------", YesNo.No);
				sa.assertTrue(false, "-----Not Able to Navigate To Theme: " + themeName + "------");
			}

		} else {
			log(LogStatus.ERROR, "Team Member has not Been Removed for Theme " + themeName, YesNo.No);
			sa.assertTrue(false, "Team Member has not Been Removed for Theme " + themeName);
		}

		if (theme.UpdateTheme(true, themeTabName, themeName, true, projectName, updatedThemeName, labelAndValues, true,
				20, updateThemeButton.Save)) {
			log(LogStatus.INFO, "Theme " + themeName + " has been Updated", YesNo.No);
		} else {
			log(LogStatus.ERROR, "Theme " + themeName + " has not been Updated", YesNo.No);
			sa.assertTrue(false, "Theme " + themeName + " has not been Updated");
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}
	
	@Parameters({ "projectName" })

	@Test
	public void AcuityThemeTc011_RecordTypeOfObjectsActive(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String contactRecordTypeList = AT_RecordTypes_1;
		String contactRecordTypeArray[] = contactRecordTypeList.split(breakSP, -1);

		String dealRecordTypeList = AT_RecordTypes_2;
		String dealRecordTypeArray[] = dealRecordTypeList.split(breakSP, -1);

		String targetRecordTypeList = AT_RecordTypes_3;
		String targetRecordTypeArray[] = targetRecordTypeList.split(breakSP, -1);

		String[][] recordTypeFields = { { recordTypeLabel.Active.toString(), "Checked" } };
		String profileName = "MnA Standard User";

		lp.CRMLogin(superAdminUserName, adminPassword);

		if (bp.editOfRecordType(projectName, contactRecordTypeArray, recordTypeFields, object.Contact, false,
				profileName)) {
			log(LogStatus.INFO, "----Record Types Gets Active for Object: " + object.Contact + "----", YesNo.No);

		} else {
			log(LogStatus.ERROR, "----Record Types not Gets Active for Object: " + object.Contact + "----", YesNo.No);
			sa.assertTrue(false, "----Record Types not Gets Active for Object: " + object.Contact + "----");
		}

		if (bp.editOfRecordType(projectName, dealRecordTypeArray, recordTypeFields, object.Deal, false, profileName)) {
			log(LogStatus.INFO, "----Record Types Gets Active for Object: " + object.Deal + "----", YesNo.No);

		} else {
			log(LogStatus.ERROR, "----Record Types not Gets Active for Object: " + object.Deal + "----", YesNo.No);
			sa.assertTrue(false, "----Record Types not Gets Active for Object: " + object.Deal + "----");
		}

		if (bp.editOfRecordType(projectName, targetRecordTypeArray, recordTypeFields, object.Target, false,
				profileName)) {
			log(LogStatus.INFO, "----Record Types Gets Active for Object: " + object.Target + "----", YesNo.No);

		} else {
			log(LogStatus.ERROR, "----Record Types not Gets Active for Object: " + object.Target + "----", YesNo.No);
			sa.assertTrue(false, "----Record Types not Gets Active for Object: " + object.Target + "----");
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Test
	public void AcuityThemeTc011_1_createAccountByApi() {
		
		String filePath =System.getProperty("user.dir")+"\\API Files\\Acuity Theme\\Account.xlsx";
		String sheetName="Account";
		new APIUtils().AccountObjectDataUpload(filePath, sheetName);
		
	}
	
	@Test
	public void AcuityThemeTc011_2_createContactByApi() {
		
		String filePath =System.getProperty("user.dir")+"\\API Files\\Acuity Theme\\Contact.xlsx";
		String sheetName="Contact";
		new APIUtils().ContactObjectDataUpload(filePath, sheetName);
		
	}
	
	@Test
	public void AcuityThemeTc011_3_createDealByApi() {
		
		String filePath =System.getProperty("user.dir")+"\\API Files\\Acuity Theme\\Deal.xlsx";
		String sheetName="Deal";
		new APIUtils().DealObjectDataUpload(filePath, sheetName);
		
	}
	
	@Test
	public void AcuityThemeTc011_4_createTargetByApi() {
		
		String filePath =System.getProperty("user.dir")+"\\API Files\\Acuity Theme\\Target.xlsx";
		String sheetName="Target";
		new APIUtils().TargetObjectDataUpload(filePath, sheetName);
		
	}
	
	@Test
	public void AcuityThemeTc011_5_createClipByApi() {
		
		String filePath =System.getProperty("user.dir")+"\\API Files\\Acuity Theme\\Clip.xlsx";
		String sheetName="Clip";
		new APIUtils().ClipObjectDataUpload(filePath, sheetName);
		
	}
	
	@Test
	public void AcuityThemeTc011_6_createThemeByApi() {
		
		String filePath =System.getProperty("user.dir")+"\\API Files\\Acuity Theme\\Theme.xlsx";
		String sheetName="Theme";
		new APIUtils().ThemeObjectDataUpload(filePath, sheetName);
		
	}
	
	@Test
	public void AcuityThemeTc011_7_createThemeRelationByApi() {
		
		String filePath =System.getProperty("user.dir")+"\\API Files\\Acuity Theme\\Theme Relation.xlsx";
		String sheetName="Theme Relation";
		new APIUtils().ThemeRelationObjectDataUpload(filePath, sheetName);
		
	}
	
	@Test
	public void AcuityThemeTc011_8_createTaskByApi() {
		
		String filePath =System.getProperty("user.dir")+"\\API Files\\Acuity Theme\\Task.xlsx";
		String sheetName="Task";
		new APIUtils().TaskObjectDataUpload(filePath, sheetName);
		
	}
	
	@Test
	public void AcuityThemeTc011_9_createEventByApi() {
		
		String filePath =System.getProperty("user.dir")+"\\API Files\\Acuity Theme\\Event.xlsx";
		String sheetName="Event";
		new APIUtils().EventObjectDataUpload(filePath, sheetName);
		
	}
	
//////Before Execution of TC012, Kindly Upload Data through Data Loader From User1///////
	@Parameters({ "projectName" })

	@Test
	public void AcuityThemeTc012_RecordTypeOfObjectsInActive(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String contactRecordTypeList = AT_RecordTypes_1;
		String contactRecordTypeArray[] = contactRecordTypeList.split(breakSP, -1);

		String dealRecordTypeList = AT_RecordTypes_4;
		String dealRecordTypeArray[] = dealRecordTypeList.split(breakSP, -1);

		String targetRecordTypeList = AT_RecordTypes_3;
		String targetRecordTypeArray[] = targetRecordTypeList.split(breakSP, -1);

		String[][] recordTypeFields = { { recordTypeLabel.Active.toString(), "Not Checked" } };
		String profileName = "MnA Standard User";

		lp.CRMLogin(superAdminUserName, adminPassword);

		if (bp.editOfRecordType(projectName, contactRecordTypeArray, recordTypeFields, object.Contact, true,
				profileName)) {
			log(LogStatus.INFO, "----Record Types Gets InActive for Object: " + object.Contact + "----", YesNo.No);

		} else {
			log(LogStatus.ERROR, "----Record Types not Gets InActive for Object: " + object.Contact + "----", YesNo.No);
			sa.assertTrue(false, "----Record Types not Gets InActive for Object: " + object.Contact + "----");
		}

		if (bp.editOfRecordType(projectName, dealRecordTypeArray, recordTypeFields, object.Deal, true, profileName)) {
			log(LogStatus.INFO, "----Record Types Gets InActive for Object: " + object.Deal + "----", YesNo.No);

		} else {
			log(LogStatus.ERROR, "----Record Types not Gets InActive for Object: " + object.Deal + "----", YesNo.No);
			sa.assertTrue(false, "----Record Types not Gets InActive for Object: " + object.Deal + "----");
		}

		if (bp.editOfRecordType(projectName, targetRecordTypeArray, recordTypeFields, object.Target, true,
				profileName)) {
			log(LogStatus.INFO, "----Record Types Gets InActive for Object: " + object.Target + "----", YesNo.No);

		} else {
			log(LogStatus.ERROR, "----Record Types not Gets InActive for Object: " + object.Target + "----", YesNo.No);
			sa.assertTrue(false, "----Record Types not Gets InActive for Object: " + object.Target + "----");
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityThemeTc013_CheckNavAndGridCountsOfTheme(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeTabName = tabObj9;
		String themeName = AT_Theme2;
		HashMap<String, Integer> expectedSectionNameAndCount = new HashMap<String, Integer>();
		expectedSectionNameAndCount.put(AT_Theme_Grid_11, 5);
		expectedSectionNameAndCount.put(AT_Theme_Grid_12, 5);
		expectedSectionNameAndCount.put(AT_Theme_Grid_13, 5);
		expectedSectionNameAndCount.put(AT_Theme_Grid_14, 5);
		expectedSectionNameAndCount.put(AT_Theme_Grid_15, 5);
		expectedSectionNameAndCount.put(AT_Theme_Grid_16, 2);
		expectedSectionNameAndCount.put(AT_Theme_Grid_17, 2);
		expectedSectionNameAndCount.put(AT_Theme_Grid_18, 5);
		expectedSectionNameAndCount.put(AT_Theme_Grid_19, 5);
		expectedSectionNameAndCount.put(AT_Theme_Grid_20, 5);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (theme.verifyItsLeftCountAndGridCountSepratelyAndTotalSumWithAllCount(true, themeTabName, themeName,
				projectName, true, expectedSectionNameAndCount, true, true, true, true)) {
			log(LogStatus.PASS, "-----Verified Counts of Theme Section Wise-----", YesNo.No);

		} else {
			sa.assertTrue(false, "-----Not Verified Counts of Theme Section Wise-----");
			log(LogStatus.FAIL, "-----Not Verified Counts of Theme Section Wise-----", YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc014_CheckThemesRedirectionsAndSortingFromTaggedSectionOfVariousObjects(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String themeName = AT_Theme2;
		String themeTabName = tabObj9;
		String themeNameForAddToTheme = AT_Theme21;

		List<String> expectedThemeRecordsToCheckRedirection = new ArrayList<String>();
		expectedThemeRecordsToCheckRedirection.add(themeName);

		String[] accountNames = AT_Firm1.split("<break>", -1);

		String[] contactNames = AT_Con1.split("<break>", -1);

		String[] dealNames1 = AT_Deal1.split("<break>", -1);
		String[] dealNames2 = AT_Deal2.split("<break>", -1);

		String[] targetNames = AT_Target1.split("<break>", -1);

		String[][] addToThemeData = { { AT_Theme_Grid_11, tabObj1, accountNames[0] },
				{ AT_Theme_Grid_15, tabObj2, contactNames[0] }, { AT_Theme_Grid_16, tabObj4, dealNames1[0] },
				{ AT_Theme_Grid_17, tabObj4, dealNames2[0] }, { AT_Theme_Grid_18, tabObj3, targetNames[0] } };

		Integer addToThemeLoopCount = 0;

		lp.CRMLogin(crmUser1EmailID, adminPassword);
		if (theme.navigateToTheme(projectName, themeTabName, themeNameForAddToTheme, false)) {
			log(LogStatus.PASS, "Successfully Navigate to theme: " + themeName, YesNo.No);

		}

		else {
			sa.assertTrue(false, "Not Successfully Navigate to theme: " + themeName);
			log(LogStatus.FAIL, "Not Successfully Navigate to theme: " + themeName, YesNo.Yes);
		}
		for (String[] data : addToThemeData) {
			CommonLib.refresh(driver);
			if (theme.createAddToTheme(false, true, false, true, false, PageName.ThemesPage, projectName, themeTabName,
					themeNameForAddToTheme, data[0], data[1], data[2], null, null, false, false, false, null)) {
				log(LogStatus.PASS, "-----Add To Theme Created for Theme: " + themeNameForAddToTheme + " for Object: "
						+ data[1] + " and for Record: " + data[2] + " -----", YesNo.No);
			} else {
				sa.assertTrue(false, "-----Add To Theme Not Created for Theme: " + themeNameForAddToTheme
						+ " for Object: " + data[1] + " and for Record: " + data[2] + " -----");
				log(LogStatus.FAIL, "-----Add To Theme Not Created for Theme: " + themeNameForAddToTheme
						+ " for Object: " + data[1] + " and for Record: " + data[2] + " -----", YesNo.Yes);
			}

			if (addToThemeLoopCount.equals(addToThemeData.length - 1)) {
				driver.close();
				driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
			}

			addToThemeLoopCount++;

		}

		for (String recordName : accountNames) {
			if (BP.navigateToRecordAndClickOnSubTab(projectName, tabObj1, recordName, null)) {
				log(LogStatus.INFO, "Able to Open the Record: " + recordName, YesNo.No);
				List<String> negativeResult = theme.verifyRedirectionOnClickOnThemeAndSortingInTaggedSection(false,
						TaggedName.Themes, tabObj9, expectedThemeRecordsToCheckRedirection, true);
				if (negativeResult.isEmpty()) {
					log(LogStatus.PASS, "-----Verified Theme records Redirection to Specific Page Records in case of "
							+ tabObj1 + " -----", YesNo.No);

				} else {
					sa.assertTrue(false,
							"-----Not Verified Theme records Redirection to Specific Page Records in case of " + tabObj1
									+ "-----");
					log(LogStatus.FAIL,
							"-----Not Verified Theme records Redirection to Specific Page Records in case of " + tabObj1
									+ "-----",
							YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Not able to Open the Record: " + recordName, YesNo.No);
				sa.assertTrue(false, "Not able to Open the Record: " + recordName);
			}
		}

		for (String recordName : contactNames) {
			if (BP.navigateToRecordAndClickOnSubTab(projectName, tabObj2, recordName, null)) {
				log(LogStatus.INFO, "Able to Open the Record: " + recordName, YesNo.No);
				List<String> negativeResult = theme.verifyRedirectionOnClickOnThemeAndSortingInTaggedSection(false,
						TaggedName.Themes, tabObj9, expectedThemeRecordsToCheckRedirection, true);
				if (negativeResult.isEmpty()) {
					log(LogStatus.PASS, "-----Verified Theme records Redirection to Specific Page Records in case of "
							+ tabObj2 + " -----", YesNo.No);

				} else {
					sa.assertTrue(false,
							"-----Not Verified Theme records Redirection to Specific Page Records in case of " + tabObj2
									+ "-----");
					log(LogStatus.FAIL,
							"-----Not Verified Theme records Redirection to Specific Page Records in case of " + tabObj2
									+ "-----",
							YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Not able to Open the Record: " + recordName, YesNo.No);
				sa.assertTrue(false, "Not able to Open the Record: " + recordName);
			}
		}

		for (String recordName : dealNames1) {
			if (BP.navigateToRecordAndClickOnSubTab(projectName, tabObj4, recordName, null)) {
				log(LogStatus.INFO, "Able to Open the Record: " + recordName, YesNo.No);
				List<String> negativeResult = theme.verifyRedirectionOnClickOnThemeAndSortingInTaggedSection(false,
						TaggedName.Themes, tabObj9, expectedThemeRecordsToCheckRedirection, true);
				if (negativeResult.isEmpty()) {
					log(LogStatus.PASS, "-----Verified Theme records Redirection to Specific Page Records in case of "
							+ tabObj4 + " -----", YesNo.No);

				} else {
					sa.assertTrue(false,
							"-----Not Verified Theme records Redirection to Specific Page Records in case of " + tabObj4
									+ "-----");
					log(LogStatus.FAIL,
							"-----Not Verified Theme records Redirection to Specific Page Records in case of " + tabObj4
									+ "-----",
							YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Not able to Open the Record: " + recordName, YesNo.No);
				sa.assertTrue(false, "Not able to Open the Record: " + recordName);
			}
		}

		for (String recordName : dealNames2) {
			if (BP.navigateToRecordAndClickOnSubTab(projectName, tabObj4, recordName, null)) {
				log(LogStatus.INFO, "Able to Open the Record: " + recordName, YesNo.No);
				List<String> negativeResult = theme.verifyRedirectionOnClickOnThemeAndSortingInTaggedSection(false,
						TaggedName.Themes, tabObj9, expectedThemeRecordsToCheckRedirection, true);
				if (negativeResult.isEmpty()) {
					log(LogStatus.PASS, "-----Verified Theme records Redirection to Specific Page Records in case of "
							+ tabObj4 + " -----", YesNo.No);

				} else {
					sa.assertTrue(false,
							"-----Not Verified Theme records Redirection to Specific Page Records in case of " + tabObj4
									+ "-----");
					log(LogStatus.FAIL,
							"-----Not Verified Theme records Redirection to Specific Page Records in case of " + tabObj4
									+ "-----",
							YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Not able to Open the Record: " + recordName, YesNo.No);
				sa.assertTrue(false, "Not able to Open the Record: " + recordName);
			}
		}

		for (String recordName : targetNames) {
			if (BP.navigateToRecordAndClickOnSubTab(projectName, tabObj3, recordName, null)) {
				log(LogStatus.INFO, "Able to Open the Record: " + recordName, YesNo.No);
				List<String> negativeResult = theme.verifyRedirectionOnClickOnThemeAndSortingInTaggedSection(false,
						TaggedName.Themes, tabObj9, expectedThemeRecordsToCheckRedirection, true);
				if (negativeResult.isEmpty()) {
					log(LogStatus.PASS, "-----Verified Theme records Redirection to Specific Page Records in case of "
							+ tabObj3 + " -----", YesNo.No);

				} else {
					sa.assertTrue(false,
							"-----Not Verified Theme records Redirection to Specific Page Records in case of " + tabObj3
									+ "-----");
					log(LogStatus.FAIL,
							"-----Not Verified Theme records Redirection to Specific Page Records in case of " + tabObj3
									+ "-----",
							YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Not able to Open the Record: " + recordName, YesNo.No);
				sa.assertTrue(false, "Not able to Open the Record: " + recordName);
			}
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc015_CheckSortingOfEachGridsOfTheme(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		String[] gridNames = AT_Theme_Grid_22.split("<break>", -1);

		List<List<String>> columnsInGrid = new ArrayList<>();
		List<List<String>> dateColumnInGrid = new ArrayList<>();
		List<List<String>> amountColumnInGrid = new ArrayList<>();
		List<List<String>> pickListColumnAndValuesInGrid = new ArrayList<>();
		columnsInGrid.add(Arrays.asList(AT_Theme_GridColumns_22.split("<break>", -1)));
		columnsInGrid.add(Arrays.asList(AT_Theme_GridColumns_22.split("<break>", -1)));
		columnsInGrid.add(Arrays.asList(AT_Theme_GridColumns_22.split("<break>", -1)));
		columnsInGrid.add(Arrays.asList(AT_Theme_GridColumns_22.split("<break>", -1)));
		columnsInGrid.add(Arrays.asList(AT_Theme_GridColumns_23.split("<break>", -1)));
		columnsInGrid.add(Arrays.asList(AT_Theme_GridColumns_24.split("<break>", -1)));
		columnsInGrid.add(Arrays.asList(AT_Theme_GridColumns_24.split("<break>", -1)));
		columnsInGrid.add(Arrays.asList(AT_Theme_GridColumns_25.split("<break>", -1)));

		dateColumnInGrid.add(Arrays.asList(AT_Theme_GridColumns_26.split("<break>", -1)));
		dateColumnInGrid.add(Arrays.asList(AT_Theme_GridColumns_26.split("<break>", -1)));
		dateColumnInGrid.add(Arrays.asList(AT_Theme_GridColumns_26.split("<break>", -1)));
		dateColumnInGrid.add(Arrays.asList(AT_Theme_GridColumns_26.split("<break>", -1)));
		dateColumnInGrid.add(Arrays.asList(AT_Theme_GridColumns_26.split("<break>", -1)));
		dateColumnInGrid.add(Arrays.asList(AT_Theme_GridColumns_27.split("<break>", -1)));
		dateColumnInGrid.add(Arrays.asList(AT_Theme_GridColumns_27.split("<break>", -1)));
		dateColumnInGrid.add(Arrays.asList(AT_Theme_GridColumns_28.split("<break>", -1)));

		amountColumnInGrid.add(Arrays.asList("".split("<break>", -1)));
		amountColumnInGrid.add(Arrays.asList("".split("<break>", -1)));
		amountColumnInGrid.add(Arrays.asList("".split("<break>", -1)));
		amountColumnInGrid.add(Arrays.asList("".split("<break>", -1)));
		amountColumnInGrid.add(Arrays.asList("".split("<break>", -1)));
		amountColumnInGrid.add(Arrays.asList("".split("<break>", -1)));
		amountColumnInGrid.add(Arrays.asList("".split("<break>", -1)));
		amountColumnInGrid.add(Arrays.asList("".split("<break>", -1)));

		pickListColumnAndValuesInGrid.add(Arrays.asList("".split("<break>", -1)));
		pickListColumnAndValuesInGrid.add(Arrays.asList("".split("<break>", -1)));
		pickListColumnAndValuesInGrid.add(Arrays.asList("".split("<break>", -1)));
		pickListColumnAndValuesInGrid.add(Arrays.asList("".split("<break>", -1)));
		pickListColumnAndValuesInGrid.add(Arrays.asList("".split("<break>", -1)));
		pickListColumnAndValuesInGrid.add(Arrays.asList("".split("<break>", -1)));
		pickListColumnAndValuesInGrid.add(Arrays.asList("".split("<break>", -1)));
		pickListColumnAndValuesInGrid.add(Arrays.asList("".split("<break>", -1)));

		lp.CRMLogin(crmUser1EmailID, adminPassword);
		if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {
			log(LogStatus.PASS, "Successfully Navigate to theme: " + themeName, YesNo.No);

			Integer loopCount = 0;
			for (String gridName : gridNames) {
				theme.verifyColumnAscendingDescendingOrderInThemeGrids(gridName, columnsInGrid.get(loopCount),
						dateColumnInGrid.get(loopCount), amountColumnInGrid.get(loopCount),
						pickListColumnAndValuesInGrid.get(loopCount), "No");
				loopCount++;
			}

			driver.close();
			driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
		}

		else {
			sa.assertTrue(false, "Not Successfully Navigate to theme: " + themeName);
			log(LogStatus.FAIL, "Not Successfully Navigate to theme: " + themeName, YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc016_verifyColumnHeadersofGrids(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		HashMap<String, List<String>> gridAndColumns = new HashMap<>();

		gridAndColumns.put(AT_Theme_Grid_11, Arrays.asList(AT_Theme_GridColumns_29.split("<break>", -1)));
		gridAndColumns.put(AT_Theme_Grid_12, Arrays.asList(AT_Theme_GridColumns_29.split("<break>", -1)));
		gridAndColumns.put(AT_Theme_Grid_13, Arrays.asList(AT_Theme_GridColumns_29.split("<break>", -1)));
		gridAndColumns.put(AT_Theme_Grid_14, Arrays.asList(AT_Theme_GridColumns_29.split("<break>", -1)));
		gridAndColumns.put(AT_Theme_Grid_15, Arrays.asList(AT_Theme_GridColumns_30.split("<break>", -1)));
		gridAndColumns.put(AT_Theme_Grid_16, Arrays.asList(AT_Theme_GridColumns_31.split("<break>", -1)));
		gridAndColumns.put(AT_Theme_Grid_17, Arrays.asList(AT_Theme_GridColumns_31.split("<break>", -1)));
		gridAndColumns.put(AT_Theme_Grid_18, Arrays.asList(AT_Theme_GridColumns_32.split("<break>", -1)));
		gridAndColumns.put(AT_Theme_Grid_19, Arrays.asList(AT_Theme_GridColumns_33.split("<break>", -1)));
		gridAndColumns.put(AT_Theme_Grid_20, Arrays.asList(AT_Theme_GridColumns_34.split("<break>", -1)));
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		List<String> negativeResult = theme.themeGridHeadersVerification(true, projectName, themeTabName, themeName,
				gridAndColumns, true, true);
		if (negativeResult.isEmpty()) {
			log(LogStatus.PASS, "-----Verified Grid Column Names of Theme-----", YesNo.No);

		} else {
			sa.assertTrue(false, "-----Not Verified Grid Column Names of Theme-----");
			log(LogStatus.FAIL, "-----Not Verified Grid Column Names of Theme-----", YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc017_CreateTaskFromActionButtonOfTheme(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy", Integer.parseInt("0"));

		String getAdvanceDueDate = AdvanceDueDate;

		String task1SubjectName = AT_Activity_Subject19;
		String task1Notes = "";
		String relatedTo = themeName + "<Prefilled>";
		String priority = AT_Activity_AdvancePriority19;
		String status = AT_Activity_AdvanceStatus19;

		String[][] updateTask1BasicSection = { { AMNNR_TaskLabel1, task1SubjectName }, { AMNNR_TaskLabel2, task1Notes },
				{ AMNNR_TaskLabel3, relatedTo } };

		String[][] updateTask1AdvancedSection = { { AMNNR_TaskLabel4, getAdvanceDueDate }, { AMNNR_TaskLabel5, status },
				{ AMNNR_TaskLabel6, priority } };

		String[] SuggestedTags = null;
		String[][] createNewRecordPopUp = null;
		String[][] addContactsToDealTeamPopUp = null;
		String[][] addContactsToFundraisingObjectPopup = null;
		String[][][] detailSectionVerifcation = null;

		lp.CRMLogin(crmUser1EmailID, adminPassword);
		if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {
			log(LogStatus.PASS, "Successfully Navigate to theme: " + themeName, YesNo.No);

			if (BP.clickOnShowMoreActionDownArrow(projectName, PageName.ThemesPage,
					ShowMoreActionDropDownList.Create_Task, 20)) {
				log(LogStatus.INFO, "Clicked on " + ShowMoreActionDropDownList.Create_Task + " Action Button",
						YesNo.No);

				if (BP.updateActivityTimelineRecord(projectName, updateTask1BasicSection, updateTask1AdvancedSection,
						null, SuggestedTags, null, false, null, null, createNewRecordPopUp, addContactsToDealTeamPopUp,
						addContactsToFundraisingObjectPopup, detailSectionVerifcation)) {
					log(LogStatus.PASS,
							"-----Activity timeline record has been Created for Subject: " + task1SubjectName + "-----",
							YesNo.No);

				} else {
					log(LogStatus.FAIL,
							"-----Activity timeline record is not Created for Subject: " + task1SubjectName + "-----",
							YesNo.No);
					sa.assertTrue(false,
							"-----Activity timeline record is not Created for Subject: " + task1SubjectName + "-----");
				}
			} else {
				log(LogStatus.ERROR,
						"Not Able to Click on " + ShowMoreActionDropDownList.Create_Task + " Action Button", YesNo.No);
				sa.assertTrue(false,
						"Not Able to Click on " + ShowMoreActionDropDownList.Create_Task + " Action Button");

			}

			driver.close();
			driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
		}

		else {
			sa.assertTrue(false, "Not Successfully Navigate to theme: " + themeName);
			log(LogStatus.FAIL, "Not Successfully Navigate to theme: " + themeName, YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc018_verifyNumberOfTaskCallAndEvents(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		Integer taskCount = 1;
		Integer callCount = 12;
		Integer EventCount = 12;
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		List<String> negativeResult = theme.verifyRecordsonInteractionsViewAllPopup(true, projectName, themeTabName,
				themeName, taskCount, callCount, EventCount, true);
		if (negativeResult.isEmpty()) {
			log(LogStatus.PASS, "-----Verified Number of Tasks, Calls and Events of Theme-----", YesNo.No);

		} else {
			sa.assertTrue(false,
					"-----Not Verified Number of Tasks, Calls and Events of Theme-----, Reason: " + negativeResult);
			log(LogStatus.FAIL,
					"-----Not Verified Number of Tasks, Calls and Events of Theme-----, Reason: " + negativeResult,
					YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc019_VerifyHyperLinksAndNonHyperLinksOfGridsRecord(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		String[] gridNames = AT_Theme_Grid_22.split("<break>", -1);

		List<List<String>> hyperLinkColumns = new ArrayList<>();
		List<List<String>> nonHyperLinkColumns = new ArrayList<>();
		List<List<String>> hyperLinkColumnsWithPopups = new ArrayList<>();

		hyperLinkColumns.add(Arrays.asList(AT_Theme_GridColumns_35.split("<break>", -1)));
		hyperLinkColumns.add(Arrays.asList(AT_Theme_GridColumns_35.split("<break>", -1)));
		hyperLinkColumns.add(Arrays.asList(AT_Theme_GridColumns_35.split("<break>", -1)));
		hyperLinkColumns.add(Arrays.asList(AT_Theme_GridColumns_35.split("<break>", -1)));
		hyperLinkColumns.add(Arrays.asList(AT_Theme_GridColumns_36.split("<break>", -1)));
		hyperLinkColumns.add(Arrays.asList(AT_Theme_GridColumns_37.split("<break>", -1)));
		hyperLinkColumns.add(Arrays.asList(AT_Theme_GridColumns_37.split("<break>", -1)));
		hyperLinkColumns.add(Arrays.asList(AT_Theme_GridColumns_38.split("<break>", -1)));
		hyperLinkColumns.add(Arrays.asList(AT_Theme_GridColumns_39.split("<break>", -1)));
		hyperLinkColumns.add(Arrays.asList(AT_Theme_GridColumns_40.split("<break>", -1)));

		nonHyperLinkColumns.add(Arrays.asList(AT_Theme_GridColumns_41.split("<break>", -1)));
		nonHyperLinkColumns.add(Arrays.asList(AT_Theme_GridColumns_41.split("<break>", -1)));
		nonHyperLinkColumns.add(Arrays.asList(AT_Theme_GridColumns_41.split("<break>", -1)));
		nonHyperLinkColumns.add(Arrays.asList(AT_Theme_GridColumns_41.split("<break>", -1)));
		nonHyperLinkColumns.add(Arrays.asList(AT_Theme_GridColumns_42.split("<break>", -1)));
		nonHyperLinkColumns.add(Arrays.asList(AT_Theme_GridColumns_43.split("<break>", -1)));
		nonHyperLinkColumns.add(Arrays.asList(AT_Theme_GridColumns_43.split("<break>", -1)));
		nonHyperLinkColumns.add(Arrays.asList(AT_Theme_GridColumns_44.split("<break>", -1)));
		nonHyperLinkColumns.add(Arrays.asList(AT_Theme_GridColumns_45.split("<break>", -1)));
		nonHyperLinkColumns.add(Arrays.asList(AT_Theme_GridColumns_46.split("<break>", -1)));

		hyperLinkColumnsWithPopups.add(Arrays.asList(AT_Theme_GridColumns_47.split("<break>", -1)));
		hyperLinkColumnsWithPopups.add(Arrays.asList(AT_Theme_GridColumns_47.split("<break>", -1)));
		hyperLinkColumnsWithPopups.add(Arrays.asList(AT_Theme_GridColumns_47.split("<break>", -1)));
		hyperLinkColumnsWithPopups.add(Arrays.asList(AT_Theme_GridColumns_47.split("<break>", -1)));
		hyperLinkColumnsWithPopups.add(Arrays.asList(AT_Theme_GridColumns_47.split("<break>", -1)));
		hyperLinkColumnsWithPopups.add(Arrays.asList(new String[] {}));
		hyperLinkColumnsWithPopups.add(Arrays.asList(new String[] {}));
		hyperLinkColumnsWithPopups.add(Arrays.asList(new String[] {}));
		hyperLinkColumnsWithPopups.add(Arrays.asList(new String[] {}));
		hyperLinkColumnsWithPopups.add(Arrays.asList(new String[] {}));
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {
			log(LogStatus.PASS, "Successfully Navigate to theme: " + themeName, YesNo.No);

			Integer loopCount = 0;
			for (String gridName : gridNames) {
				List<String> negativeResult = theme.themeGridHyperAndNonHyperLinksNavigationOfFirstRow(false,
						projectName, themeTabName, themeName, gridName, hyperLinkColumns.get(loopCount),
						nonHyperLinkColumns.get(loopCount), hyperLinkColumnsWithPopups.get(loopCount), false);
				if (negativeResult.isEmpty()) {
					log(LogStatus.PASS, "-----Verify HyperLinks And Non HyperLinks Of Grids Record-----", YesNo.No);

				} else {
					sa.assertTrue(false, "-----Not Verify HyperLinks And Non HyperLinks Of Grids Record-----, Reason: "
							+ negativeResult);
					log(LogStatus.FAIL, "-----Not Verify HyperLinks And Non HyperLinks Of Grids Record-----, Reason: "
							+ negativeResult, YesNo.Yes);
				}

				loopCount++;
			}

			driver.close();
			driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
		}

		else {
			sa.assertTrue(false, "Not Successfully Navigate to theme: " + themeName);
			log(LogStatus.FAIL, "Not Successfully Navigate to theme: " + themeName, YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc020_ViewNotePopupVerificationCheckForAdvisor(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		String task1SubjectName = AT_Activity_Subject1;
		String task1Notes = AT_Activity_Notes1;

		String getAdvance1DueDate = AT_Activity_DueDate1;
		String relatedTo1 = AT_Activity_RelatedTo1 + crmUser1FirstName + " " + crmUser1LastName;

		String task2SubjectName = AT_Activity_Subject2;
		String task2Notes = AT_Activity_Notes1;

		String getAdvance2DueDate = AT_Activity_DueDate2;
		String relatedTo2 = AT_Activity_RelatedTo2 + crmUser1FirstName + " " + crmUser1LastName;

		String sectionName = AT_Theme_Grid_11;
		String columnNames = AT_Theme_GridColumns_47;

		String record1 = AT_Firm2;
		String record2 = AT_Firm3;

		String[][] task1BasicSection = { { AMNNR_CallLabel1, task1SubjectName }, { AMNNR_CallLabel2, task1Notes },
				{ AMNNR_CallLabel3, relatedTo1 } };

		String[][] task1AdvancedSection = { { AMNNR_CallLabel4, getAdvance1DueDate },
				{ AMNNR_CallLabel5, crmUser1FirstName + " " + crmUser1LastName } };

		String[][] task2BasicSection = { { AMNNR_CallLabel1, task2SubjectName }, { AMNNR_CallLabel2, task2Notes },
				{ AMNNR_CallLabel3, relatedTo2 } };

		String[][] task2AdvancedSection = { { AMNNR_CallLabel4, getAdvance2DueDate },
				{ AMNNR_CallLabel5, crmUser1FirstName + " " + crmUser1LastName } };

		List<DataContainer> datas = new ArrayList<DataContainer>();

		datas.add(new DataContainer(sectionName, columnNames, record1, task1SubjectName, task1BasicSection,
				task1AdvancedSection, IconType.Call, PageName.ThemesPage));
		datas.add(new DataContainer(sectionName, columnNames, record2, task2SubjectName, task2BasicSection,
				task2AdvancedSection, IconType.Meeting, PageName.ThemesPage));

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {
			log(LogStatus.PASS, "Successfully Navigate to theme: " + themeName, YesNo.No);

			List<String> negativeResult = theme.verifyViewNotePopupAfterClick(datas);
			if (negativeResult.isEmpty()) {
				log(LogStatus.PASS,
						"-----Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----",
						YesNo.No);

			} else {
				sa.assertTrue(false,
						"-----Not Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----, Reason: "
								+ negativeResult);
				log(LogStatus.FAIL,
						"-----Not Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----, Reason: "
								+ negativeResult,
						YesNo.Yes);
			}

			driver.close();
			driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
		}

		else {
			sa.assertTrue(false, "Not Successfully Navigate to theme: " + themeName);
			log(LogStatus.FAIL, "Not Successfully Navigate to theme: " + themeName, YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}
	
	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc021_ViewNotePopupVerificationCheckForCompany(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		String task1SubjectName = AT_Activity_Subject3;
		String task1Notes = AT_Activity_Notes1;

		String getAdvance1DueDate = AT_Activity_DueDate1;
		String relatedTo1 = AT_Activity_RelatedTo3 + crmUser1FirstName + " " + crmUser1LastName;

		String task2SubjectName = AT_Activity_Subject4;
		String task2Notes = AT_Activity_Notes1;

		String getAdvance2DueDate = AT_Activity_DueDate2;
		String relatedTo2 = AT_Activity_RelatedTo4 + crmUser1FirstName + " " + crmUser1LastName;

		String sectionName = AT_Theme_Grid_12;
		String columnNames = AT_Theme_GridColumns_47;

		String record1 = AT_Firm4;
		String record2 = AT_Firm5;

		String[][] task1BasicSection = { { AMNNR_CallLabel1, task1SubjectName }, { AMNNR_CallLabel2, task1Notes },
				{ AMNNR_CallLabel3, relatedTo1 } };

		String[][] task1AdvancedSection = { { AMNNR_CallLabel4, getAdvance1DueDate },
				{ AMNNR_CallLabel5, crmUser1FirstName + " " + crmUser1LastName } };

		String[][] task2BasicSection = { { AMNNR_CallLabel1, task2SubjectName }, { AMNNR_CallLabel2, task2Notes },
				{ AMNNR_CallLabel3, relatedTo2 } };

		String[][] task2AdvancedSection = { { AMNNR_CallLabel4, getAdvance2DueDate },
				{ AMNNR_CallLabel5, crmUser1FirstName + " " + crmUser1LastName } };

		List<DataContainer> datas = new ArrayList<DataContainer>();

		datas.add(new DataContainer(sectionName, columnNames, record1, task1SubjectName, task1BasicSection,
				task1AdvancedSection, IconType.Call, PageName.ThemesPage));
		datas.add(new DataContainer(sectionName, columnNames, record2, task2SubjectName, task2BasicSection,
				task2AdvancedSection, IconType.Meeting, PageName.ThemesPage));

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {
			log(LogStatus.PASS, "Successfully Navigate to theme: " + themeName, YesNo.No);

			List<String> negativeResult = theme.verifyViewNotePopupAfterClick(datas);
			if (negativeResult.isEmpty()) {
				log(LogStatus.PASS,
						"-----Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----",
						YesNo.No);

			} else {
				sa.assertTrue(false,
						"-----Not Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----, Reason: "
								+ negativeResult);
				log(LogStatus.FAIL,
						"-----Not Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----, Reason: "
								+ negativeResult,
						YesNo.Yes);
			}

			driver.close();
			driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
		}

		else {
			sa.assertTrue(false, "Not Successfully Navigate to theme: " + themeName);
			log(LogStatus.FAIL, "Not Successfully Navigate to theme: " + themeName, YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc022_ViewNotePopupVerificationCheckForFinancialSponsor(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		String task1SubjectName = AT_Activity_Subject5;
		String task1Notes = AT_Activity_Notes1;

		String getAdvance1DueDate = AT_Activity_DueDate1;
		String relatedTo1 = AT_Activity_RelatedTo5 + crmUser1FirstName + " " + crmUser1LastName;

		String task2SubjectName = AT_Activity_Subject6;
		String task2Notes = AT_Activity_Notes1;

		String getAdvance2DueDate = AT_Activity_DueDate2;
		String relatedTo2 = AT_Activity_RelatedTo6 + crmUser1FirstName + " " + crmUser1LastName;

		String sectionName = AT_Theme_Grid_13;
		String columnNames = AT_Theme_GridColumns_47;

		String record1 = AT_Firm6;
		String record2 = AT_Firm7;

		String[][] task1BasicSection = { { AMNNR_CallLabel1, task1SubjectName }, { AMNNR_CallLabel2, task1Notes },
				{ AMNNR_CallLabel3, relatedTo1 } };

		String[][] task1AdvancedSection = { { AMNNR_CallLabel4, getAdvance1DueDate },
				{ AMNNR_CallLabel5, crmUser1FirstName + " " + crmUser1LastName } };

		String[][] task2BasicSection = { { AMNNR_CallLabel1, task2SubjectName }, { AMNNR_CallLabel2, task2Notes },
				{ AMNNR_CallLabel3, relatedTo2 } };

		String[][] task2AdvancedSection = { { AMNNR_CallLabel4, getAdvance2DueDate },
				{ AMNNR_CallLabel5, crmUser1FirstName + " " + crmUser1LastName } };

		List<DataContainer> datas = new ArrayList<DataContainer>();

		datas.add(new DataContainer(sectionName, columnNames, record1, task1SubjectName, task1BasicSection,
				task1AdvancedSection, IconType.Call, PageName.ThemesPage));
		datas.add(new DataContainer(sectionName, columnNames, record2, task2SubjectName, task2BasicSection,
				task2AdvancedSection, IconType.Meeting, PageName.ThemesPage));

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {
			log(LogStatus.PASS, "Successfully Navigate to theme: " + themeName, YesNo.No);

			List<String> negativeResult = theme.verifyViewNotePopupAfterClick(datas);
			if (negativeResult.isEmpty()) {
				log(LogStatus.PASS,
						"-----Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----",
						YesNo.No);

			} else {
				sa.assertTrue(false,
						"-----Not Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----, Reason: "
								+ negativeResult);
				log(LogStatus.FAIL,
						"-----Not Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----, Reason: "
								+ negativeResult,
						YesNo.Yes);
			}

			driver.close();
			driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
		}

		else {
			sa.assertTrue(false, "Not Successfully Navigate to theme: " + themeName);
			log(LogStatus.FAIL, "Not Successfully Navigate to theme: " + themeName, YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc023_1_ViewNotePopupVerificationCheckForStrategicCorporation(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		String task1SubjectName = AT_Activity_Subject7;
		String task1Notes = AT_Activity_Notes1;

		String getAdvance1DueDate = AT_Activity_DueDate1;
		String relatedTo1 = AT_Activity_RelatedTo7 + crmUser1FirstName + " " + crmUser1LastName;

		String task2SubjectName = AT_Activity_Subject8;
		String task2Notes = AT_Activity_Notes1;

		String getAdvance2DueDate = AT_Activity_DueDate2;
		String relatedTo2 = AT_Activity_RelatedTo8 + crmUser1FirstName + " " + crmUser1LastName;

		String sectionName = AT_Theme_Grid_14;
		String columnNames = AT_Theme_GridColumns_47;

		String record1 = AT_Firm8;
		String record2 = AT_Firm9;

		String[][] task1BasicSection = { { AMNNR_CallLabel1, task1SubjectName }, { AMNNR_CallLabel2, task1Notes },
				{ AMNNR_CallLabel3, relatedTo1 } };

		String[][] task1AdvancedSection = { { AMNNR_CallLabel4, getAdvance1DueDate },
				{ AMNNR_CallLabel5, crmUser1FirstName + " " + crmUser1LastName } };

		String[][] task2BasicSection = { { AMNNR_CallLabel1, task2SubjectName }, { AMNNR_CallLabel2, task2Notes },
				{ AMNNR_CallLabel3, relatedTo2 } };

		String[][] task2AdvancedSection = { { AMNNR_CallLabel4, getAdvance2DueDate },
				{ AMNNR_CallLabel5, crmUser1FirstName + " " + crmUser1LastName } };

		List<DataContainer> datas = new ArrayList<DataContainer>();

		datas.add(new DataContainer(sectionName, columnNames, record1, task1SubjectName, task1BasicSection,
				task1AdvancedSection, IconType.Call, PageName.ThemesPage));
		datas.add(new DataContainer(sectionName, columnNames, record2, task2SubjectName, task2BasicSection,
				task2AdvancedSection, IconType.Meeting, PageName.ThemesPage));

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {
			log(LogStatus.PASS, "Successfully Navigate to theme: " + themeName, YesNo.No);

			List<String> negativeResult = theme.verifyViewNotePopupAfterClick(datas);
			if (negativeResult.isEmpty()) {
				log(LogStatus.PASS,
						"-----Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----",
						YesNo.No);

			} else {
				sa.assertTrue(false,
						"-----Not Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----, Reason: "
								+ negativeResult);
				log(LogStatus.FAIL,
						"-----Not Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----, Reason: "
								+ negativeResult,
						YesNo.Yes);
			}

			driver.close();
			driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
		}

		else {
			sa.assertTrue(false, "Not Successfully Navigate to theme: " + themeName);
			log(LogStatus.FAIL, "Not Successfully Navigate to theme: " + themeName, YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc023_2_ViewNotePopupVerificationCheckForContact(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		String task1SubjectName = AT_Activity_Subject9;
		String task1Notes = AT_Activity_Notes1;

		String getAdvance1DueDate = AT_Activity_DueDate1;
		String relatedTo1 = AT_Activity_RelatedTo9 + crmUser1FirstName + " " + crmUser1LastName;

		String task2SubjectName = AT_Activity_Subject2;
		String task2Notes = AT_Activity_Notes1;

		String getAdvance2DueDate = AT_Activity_DueDate2;
		String relatedTo2 = AT_Activity_RelatedTo2 + crmUser1FirstName + " " + crmUser1LastName;

		String sectionName = AT_Theme_Grid_15;
		String columnNames = AT_Theme_GridColumns_47;

		String record1 = AT_Con2;
		String record2 = AT_Con3;

		String[][] task1BasicSection = { { AMNNR_CallLabel1, task1SubjectName }, { AMNNR_CallLabel2, task1Notes },
				{ AMNNR_CallLabel3, relatedTo1 } };

		String[][] task1AdvancedSection = { { AMNNR_CallLabel4, getAdvance1DueDate },
				{ AMNNR_CallLabel5, crmUser1FirstName + " " + crmUser1LastName } };

		String[][] task2BasicSection = { { AMNNR_CallLabel1, task2SubjectName }, { AMNNR_CallLabel2, task2Notes },
				{ AMNNR_CallLabel3, relatedTo2 } };

		String[][] task2AdvancedSection = { { AMNNR_CallLabel4, getAdvance2DueDate },
				{ AMNNR_CallLabel5, crmUser1FirstName + " " + crmUser1LastName } };

		List<DataContainer> datas = new ArrayList<DataContainer>();

		datas.add(new DataContainer(sectionName, columnNames, record1, task1SubjectName, task1BasicSection,
				task1AdvancedSection, IconType.Call, PageName.ThemesPage));
		datas.add(new DataContainer(sectionName, columnNames, record2, task2SubjectName, task2BasicSection,
				task2AdvancedSection, IconType.Meeting, PageName.ThemesPage));

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {
			log(LogStatus.PASS, "Successfully Navigate to theme: " + themeName, YesNo.No);

			List<String> negativeResult = theme.verifyViewNotePopupAfterClick(datas);
			if (negativeResult.isEmpty()) {
				log(LogStatus.PASS,
						"-----Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----",
						YesNo.No);

			} else {
				sa.assertTrue(false,
						"-----Not Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----, Reason: "
								+ negativeResult);
				log(LogStatus.FAIL,
						"-----Not Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----, Reason: "
								+ negativeResult,
						YesNo.Yes);
			}

			driver.close();
			driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
		}

		else {
			sa.assertTrue(false, "Not Successfully Navigate to theme: " + themeName);
			log(LogStatus.FAIL, "Not Successfully Navigate to theme: " + themeName, YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc024_CreateAddToThemeForVariousSections(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		List<String> expectedThemeRecordsToCheckRedirection = new ArrayList<String>();
		expectedThemeRecordsToCheckRedirection.add(themeName);

		String[] advisorNames = AT_Theme_AddToTheme_48.split("<break>", -1);

		String[] companyNames = AT_Theme_AddToTheme_49.split("<break>", -1);
		String[] financialSponsorNames = AT_Theme_AddToTheme_50.split("<break>", -1);
		String[] strategicCorpNames = AT_Theme_AddToTheme_51.split("<break>", -1);

		String[] contactNames = AT_Theme_AddToTheme_52.split("<break>", -1);

		String[] dealNames1 = AT_Theme_AddToTheme_53.split("<break>", -1);
		String[] dealNames2 = AT_Theme_AddToTheme_54.split("<break>", -1);

		String[] targetNames = AT_Theme_AddToTheme_55.split("<break>", -1);

		String[] themeNames = AT_Theme_AddToTheme_56.split("<break>", -1);

		String[] clipNames = AT_Theme_AddToTheme_57.split("<break>", -1);

		String[][] addToThemeData = { { AT_Theme_Grid_11, tabObj1, advisorNames[0], AT_Theme_GridColumns_48 },
				{ AT_Theme_Grid_12, tabObj1, companyNames[0], AT_Theme_GridColumns_48 },
				{ AT_Theme_Grid_13, tabObj1, financialSponsorNames[0], AT_Theme_GridColumns_48 },
				{ AT_Theme_Grid_14, tabObj1, strategicCorpNames[0], AT_Theme_GridColumns_48 },
				{ AT_Theme_Grid_15, tabObj2, contactNames[0], AT_Theme_GridColumns_49 },
				{ AT_Theme_Grid_16, tabObj4, dealNames1[0], AT_Theme_GridColumns_50 },
				{ AT_Theme_Grid_17, tabObj4, dealNames2[0], AT_Theme_GridColumns_50 },
				{ AT_Theme_Grid_18, tabObj3, targetNames[0], AT_Theme_GridColumns_51 },
				{ AT_Theme_Grid_19, tabObj9, themeNames[0], AT_Theme_GridColumns_52 },
				{ AT_Theme_Grid_20, tabObj10, clipNames[0], AT_Theme_GridColumns_53 } };

		Integer addToThemeLoopCount = 0;

		lp.CRMLogin(crmUser1EmailID, adminPassword);
		if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {
			log(LogStatus.PASS, "Successfully Navigate to theme: " + themeName, YesNo.No);

		}

		else {
			sa.assertTrue(false, "Not Successfully Navigate to theme: " + themeName);
			log(LogStatus.FAIL, "Not Successfully Navigate to theme: " + themeName, YesNo.Yes);
		}
		for (String[] data : addToThemeData) {
			CommonLib.refresh(driver);
			if (theme.createAddToTheme(false, true, false, true, false, PageName.ThemesPage, projectName, themeTabName,
					themeName, data[0], data[1], data[2], null, null, false, false, false, null)) {
				log(LogStatus.PASS, "-----Add To Theme Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Record: " + data[2] + " -----", YesNo.No);

				if (theme.verifyRecordInGrid(data[0], data[3], data[2].split("<break>", -1))) {
					log(LogStatus.PASS, "-----Verified Add To Theme Created for Theme: " + themeName + " for Object: "
							+ data[1] + " and for Record: " + data[2] + " -----", YesNo.No);
				} else {
					sa.assertTrue(false, "-----Not Verified Add To Theme Not Created for Theme: " + themeName
							+ " for Object: " + data[1] + " and for Record: " + data[2] + " -----");
					log(LogStatus.FAIL, "-----Not Verified Add To Theme Not Created for Theme: " + themeName
							+ " for Object: " + data[1] + " and for Record: " + data[2] + " -----", YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "-----Add To Theme Not Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Record: " + data[2] + " -----");
				log(LogStatus.FAIL, "-----Add To Theme Not Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Record: " + data[2] + " -----", YesNo.Yes);
			}

			if (addToThemeLoopCount.equals(addToThemeData.length - 1)) {
				driver.close();
				driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
			}

			addToThemeLoopCount++;

		}

		for (String recordName : companyNames) {
			if (BP.navigateToRecordAndClickOnSubTab(projectName, tabObj1, recordName, null)) {
				log(LogStatus.INFO, "Able to Open the Record: " + recordName, YesNo.No);
				List<String> negativeResult = theme.verifyRedirectionOnClickOnThemeAndSortingInTaggedSection(false,
						TaggedName.Themes, tabObj9, expectedThemeRecordsToCheckRedirection, true);
				if (negativeResult.isEmpty()) {
					log(LogStatus.PASS, "-----Verified Theme records Redirection to Specific Page Records in case of "
							+ tabObj1 + " -----", YesNo.No);

				} else {
					sa.assertTrue(false,
							"-----Not Verified Theme records Redirection to Specific Page Records in case of " + tabObj1
									+ "-----");
					log(LogStatus.FAIL,
							"-----Not Verified Theme records Redirection to Specific Page Records in case of " + tabObj1
									+ "-----",
							YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Not able to Open the Record: " + recordName, YesNo.No);
				sa.assertTrue(false, "Not able to Open the Record: " + recordName);
			}
		}

		for (String recordName : financialSponsorNames) {
			if (BP.navigateToRecordAndClickOnSubTab(projectName, tabObj1, recordName, null)) {
				log(LogStatus.INFO, "Able to Open the Record: " + recordName, YesNo.No);
				List<String> negativeResult = theme.verifyRedirectionOnClickOnThemeAndSortingInTaggedSection(false,
						TaggedName.Themes, tabObj9, expectedThemeRecordsToCheckRedirection, true);
				if (negativeResult.isEmpty()) {
					log(LogStatus.PASS, "-----Verified Theme records Redirection to Specific Page Records in case of "
							+ tabObj1 + " -----", YesNo.No);

				} else {
					sa.assertTrue(false,
							"-----Not Verified Theme records Redirection to Specific Page Records in case of " + tabObj1
									+ "-----");
					log(LogStatus.FAIL,
							"-----Not Verified Theme records Redirection to Specific Page Records in case of " + tabObj1
									+ "-----",
							YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Not able to Open the Record: " + recordName, YesNo.No);
				sa.assertTrue(false, "Not able to Open the Record: " + recordName);
			}
		}

		for (String recordName : strategicCorpNames) {
			if (BP.navigateToRecordAndClickOnSubTab(projectName, tabObj1, recordName, null)) {
				log(LogStatus.INFO, "Able to Open the Record: " + recordName, YesNo.No);
				List<String> negativeResult = theme.verifyRedirectionOnClickOnThemeAndSortingInTaggedSection(false,
						TaggedName.Themes, tabObj9, expectedThemeRecordsToCheckRedirection, true);
				if (negativeResult.isEmpty()) {
					log(LogStatus.PASS, "-----Verified Theme records Redirection to Specific Page Records in case of "
							+ tabObj1 + " -----", YesNo.No);

				} else {
					sa.assertTrue(false,
							"-----Not Verified Theme records Redirection to Specific Page Records in case of " + tabObj1
									+ "-----");
					log(LogStatus.FAIL,
							"-----Not Verified Theme records Redirection to Specific Page Records in case of " + tabObj1
									+ "-----",
							YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Not able to Open the Record: " + recordName, YesNo.No);
				sa.assertTrue(false, "Not able to Open the Record: " + recordName);
			}
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc025_RecordsRemoveFromThemeGridsFunctionality(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		String[][] data = { AT_Theme_RemoveToTheme_48.split("<Section>", -1),
				AT_Theme_RemoveToTheme_49.split("<Section>", -1), AT_Theme_RemoveToTheme_50.split("<Section>", -1),
				AT_Theme_RemoveToTheme_51.split("<Section>", -1), AT_Theme_RemoveToTheme_52.split("<Section>", -1),
				AT_Theme_RemoveToTheme_53.split("<Section>", -1), AT_Theme_RemoveToTheme_54.split("<Section>", -1),
				AT_Theme_RemoveToTheme_55.split("<Section>", -1), AT_Theme_RemoveToTheme_56.split("<Section>", -1),
				AT_Theme_RemoveToTheme_57.split("<Section>", -1), };
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {
			log(LogStatus.PASS, "Successfully Navigate to theme: " + themeName, YesNo.No);
			for (String[] removeData : data) {

				if (theme.removeFromThemeBasedOnRecordOfColumnNumber(false, themeTabName, themeName, projectName, false,
						removeData[0], removeData[1], Integer.valueOf(removeData[2]),
						Boolean.parseBoolean(removeData[3]), Boolean.parseBoolean(removeData[4]))) {
					log(LogStatus.PASS, "-----Verify Records Removed from Grids-----", YesNo.No);

				} else {
					sa.assertTrue(false, "-----Not Verify Records Removed from Grids-----");
					log(LogStatus.FAIL, "-----Not Verify Records Removed from Grids-----", YesNo.Yes);
				}

			}

			driver.close();
			driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
		}

		else {
			sa.assertTrue(false, "Not Successfully Navigate to theme: " + themeName);
			log(LogStatus.FAIL, "Not Successfully Navigate to theme: " + themeName, YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc026_CreateLogACallNoteAndVerificationForAdvisor(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		String task1SubjectName = AT_Activity_Subject10;
		String task1Notes = AT_Activity_Notes1;

		String getAdvance1DueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy",
				Integer.parseInt("-1"));
		String relatedTo1 = AT_Activity_RelatedTo10;

		String sectionName = AT_Theme_Grid_11;
		String columnNames = AT_Theme_GridColumns_47;

		String record1 = AT_Activity_RelatedTo20;

		String[][] task1BasicSection = { { AMNNR_CallLabel1, task1SubjectName }, { AMNNR_CallLabel2, task1Notes },
				{ AMNNR_CallLabel3, relatedTo1 } };

		String[][] task1AdvancedSection = { { AMNNR_CallLabel4, getAdvance1DueDate },
				{ AMNNR_CallLabel5, crmUser1FirstName + " " + crmUser1LastName } };

		List<DataContainer> datas = new ArrayList<DataContainer>();

		datas.add(new DataContainer(sectionName, columnNames, record1, task1SubjectName, task1BasicSection,
				task1AdvancedSection, IconType.Call, PageName.ThemesPage));

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		List<String> negativeResultOfCalls = theme.createLogANoteFromSectionOfThemeAndVerification(true, themeTabName,
				themeName, task1SubjectName, projectName, task1BasicSection, task1AdvancedSection, null, null, null,
				false, null, null, null, null, null, null, datas.get(0).gridName,
				datas.get(0).recordNameBasedOnColumnSelect, false);

		if (negativeResultOfCalls.isEmpty()) {
			log(LogStatus.PASS, "-----Verified Create Log a Note-----", YesNo.No);

			List<String> negativeResult = theme.verifyViewNotePopupAfterClick(datas);
			if (negativeResult.isEmpty()) {
				log(LogStatus.PASS,
						"-----Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----",
						YesNo.No);

			} else {
				sa.assertTrue(false,
						"-----Not Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----, Reason: "
								+ negativeResult);
				log(LogStatus.FAIL,
						"-----Not Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----, Reason: "
								+ negativeResult,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "-----Not Verified Create Log a Note-----, Reason: " + negativeResultOfCalls);
			log(LogStatus.FAIL, "-----Not Verified Create Log a Note-----, Reason: " + negativeResultOfCalls,
					YesNo.Yes);
		}

		driver.close();
		driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc027_CreateLogACallNoteAndVerificationForCompany(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		String task1SubjectName = AT_Activity_Subject11;
		String task1Notes = AT_Activity_Notes1;

		String getAdvance1DueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy",
				Integer.parseInt("-1"));
		String relatedTo1 = AT_Activity_RelatedTo11;

		String sectionName = AT_Theme_Grid_12;
		String columnNames = AT_Theme_GridColumns_47;

		String record1 = AT_Activity_RelatedTo21;

		String[][] task1BasicSection = { { AMNNR_CallLabel1, task1SubjectName }, { AMNNR_CallLabel2, task1Notes },
				{ AMNNR_CallLabel3, relatedTo1 } };

		String[][] task1AdvancedSection = { { AMNNR_CallLabel4, getAdvance1DueDate },
				{ AMNNR_CallLabel5, crmUser1FirstName + " " + crmUser1LastName } };

		List<DataContainer> datas = new ArrayList<DataContainer>();

		datas.add(new DataContainer(sectionName, columnNames, record1, task1SubjectName, task1BasicSection,
				task1AdvancedSection, IconType.Call, PageName.ThemesPage));

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		List<String> negativeResultOfCalls = theme.createLogANoteFromSectionOfThemeAndVerification(true, themeTabName,
				themeName, task1SubjectName, projectName, task1BasicSection, task1AdvancedSection, null, null, null,
				false, null, null, null, null, null, null, datas.get(0).gridName,
				datas.get(0).recordNameBasedOnColumnSelect, false);

		if (negativeResultOfCalls.isEmpty()) {
			log(LogStatus.PASS, "-----Verified Create Log a Note-----", YesNo.No);

			List<String> negativeResult = theme.verifyViewNotePopupAfterClick(datas);
			if (negativeResult.isEmpty()) {
				log(LogStatus.PASS,
						"-----Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----",
						YesNo.No);

			} else {
				sa.assertTrue(false,
						"-----Not Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----, Reason: "
								+ negativeResult);
				log(LogStatus.FAIL,
						"-----Not Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----, Reason: "
								+ negativeResult,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "-----Not Verified Create Log a Note-----, Reason: " + negativeResultOfCalls);
			log(LogStatus.FAIL, "-----Not Verified Create Log a Note-----, Reason: " + negativeResultOfCalls,
					YesNo.Yes);
		}

		driver.close();
		driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc028_CreateLogACallNoteAndVerificationForFinancialSponsor(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		String task1SubjectName = AT_Activity_Subject12;
		String task1Notes = AT_Activity_Notes1;

		String getAdvance1DueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy",
				Integer.parseInt("-1"));
		String relatedTo1 = AT_Activity_RelatedTo12;

		String sectionName = AT_Theme_Grid_13;
		String columnNames = AT_Theme_GridColumns_47;

		String record1 = AT_Activity_RelatedTo22;

		String[][] task1BasicSection = { { AMNNR_CallLabel1, task1SubjectName }, { AMNNR_CallLabel2, task1Notes },
				{ AMNNR_CallLabel3, relatedTo1 } };

		String[][] task1AdvancedSection = { { AMNNR_CallLabel4, getAdvance1DueDate },
				{ AMNNR_CallLabel5, crmUser1FirstName + " " + crmUser1LastName } };

		List<DataContainer> datas = new ArrayList<DataContainer>();

		datas.add(new DataContainer(sectionName, columnNames, record1, task1SubjectName, task1BasicSection,
				task1AdvancedSection, IconType.Call, PageName.ThemesPage));

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		List<String> negativeResultOfCalls = theme.createLogANoteFromSectionOfThemeAndVerification(true, themeTabName,
				themeName, task1SubjectName, projectName, task1BasicSection, task1AdvancedSection, null, null, null,
				false, null, null, null, null, null, null, datas.get(0).gridName,
				datas.get(0).recordNameBasedOnColumnSelect, false);

		if (negativeResultOfCalls.isEmpty()) {
			log(LogStatus.PASS, "-----Verified Create Log a Note-----", YesNo.No);

			List<String> negativeResult = theme.verifyViewNotePopupAfterClick(datas);
			if (negativeResult.isEmpty()) {
				log(LogStatus.PASS,
						"-----Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----",
						YesNo.No);

			} else {
				sa.assertTrue(false,
						"-----Not Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----, Reason: "
								+ negativeResult);
				log(LogStatus.FAIL,
						"-----Not Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----, Reason: "
								+ negativeResult,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "-----Not Verified Create Log a Note-----, Reason: " + negativeResultOfCalls);
			log(LogStatus.FAIL, "-----Not Verified Create Log a Note-----, Reason: " + negativeResultOfCalls,
					YesNo.Yes);
		}

		driver.close();
		driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc029_CreateLogACallNoteAndVerificationForStrategicCorp(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		String task1SubjectName = AT_Activity_Subject13;
		String task1Notes = AT_Activity_Notes1;

		String getAdvance1DueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy",
				Integer.parseInt("-1"));

		String relatedTo1 = AT_Activity_RelatedTo13;

		String sectionName = AT_Theme_Grid_14;
		String columnNames = AT_Theme_GridColumns_47;

		String record1 = AT_Activity_RelatedTo23;

		String[][] task1BasicSection = { { AMNNR_CallLabel1, task1SubjectName }, { AMNNR_CallLabel2, task1Notes },
				{ AMNNR_CallLabel3, relatedTo1 } };

		String[][] task1AdvancedSection = { { AMNNR_CallLabel4, getAdvance1DueDate },
				{ AMNNR_CallLabel5, crmUser1FirstName + " " + crmUser1LastName } };

		List<DataContainer> datas = new ArrayList<DataContainer>();

		datas.add(new DataContainer(sectionName, columnNames, record1, task1SubjectName, task1BasicSection,
				task1AdvancedSection, IconType.Call, PageName.ThemesPage));

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		List<String> negativeResultOfCalls = theme.createLogANoteFromSectionOfThemeAndVerification(true, themeTabName,
				themeName, task1SubjectName, projectName, task1BasicSection, task1AdvancedSection, null, null, null,
				false, null, null, null, null, null, null, datas.get(0).gridName,
				datas.get(0).recordNameBasedOnColumnSelect, false);

		if (negativeResultOfCalls.isEmpty()) {
			log(LogStatus.PASS, "-----Verified Create Log a Note-----", YesNo.No);

			List<String> negativeResult = theme.verifyViewNotePopupAfterClick(datas);
			if (negativeResult.isEmpty()) {
				log(LogStatus.PASS,
						"-----Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----",
						YesNo.No);

			} else {
				sa.assertTrue(false,
						"-----Not Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----, Reason: "
								+ negativeResult);
				log(LogStatus.FAIL,
						"-----Not Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----, Reason: "
								+ negativeResult,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "-----Not Verified Create Log a Note-----, Reason: " + negativeResultOfCalls);
			log(LogStatus.FAIL, "-----Not Verified Create Log a Note-----, Reason: " + negativeResultOfCalls,
					YesNo.Yes);
		}

		driver.close();
		driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc030_CreateLogACallNoteAndVerificationForContact(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		String task1SubjectName = AT_Activity_Subject14;
		String task1Notes = AT_Activity_Notes1;

		String getAdvance1DueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy",
				Integer.parseInt("-1"));

		String sectionName = AT_Theme_Grid_15;
		String columnNames = AT_Theme_GridColumns_47;

		String record1 = AT_Activity_RelatedTo10;

		String[][] task1BasicSection = { { AMNNR_CallLabel1, task1SubjectName }, { AMNNR_CallLabel2, task1Notes } };

		String[][] task1AdvancedSection = { { AMNNR_CallLabel4, getAdvance1DueDate },
				{ AMNNR_CallLabel5, crmUser1FirstName + " " + crmUser1LastName } };

		List<DataContainer> datas = new ArrayList<DataContainer>();

		datas.add(new DataContainer(sectionName, columnNames, record1, task1SubjectName, task1BasicSection,
				task1AdvancedSection, IconType.Call, PageName.ThemesPage));

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		List<String> negativeResultOfCalls = theme.createLogANoteFromSectionOfThemeAndVerification(true, themeTabName,
				themeName, task1SubjectName, projectName, task1BasicSection, task1AdvancedSection, null, null, null,
				false, null, null, null, null, null, null, datas.get(0).gridName,
				datas.get(0).recordNameBasedOnColumnSelect, false);

		if (negativeResultOfCalls.isEmpty()) {
			log(LogStatus.PASS, "-----Verified Create Log a Note-----", YesNo.No);

			List<String> negativeResult = theme.verifyViewNotePopupAfterClick(datas);
			if (negativeResult.isEmpty()) {
				log(LogStatus.PASS,
						"-----Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----",
						YesNo.No);

			} else {
				sa.assertTrue(false,
						"-----Not Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----, Reason: "
								+ negativeResult);
				log(LogStatus.FAIL,
						"-----Not Verified View Note PopUp After Click on Interaction Notes and Last Interaction Date-----, Reason: "
								+ negativeResult,
						YesNo.Yes);
			}

		} else {
			sa.assertTrue(false, "-----Not Verified Create Log a Note-----, Reason: " + negativeResultOfCalls);
			log(LogStatus.FAIL, "-----Not Verified Create Log a Note-----, Reason: " + negativeResultOfCalls,
					YesNo.Yes);
		}

		driver.close();
		driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc031_CreateLogACallNoteAndVerificationForSellSideDeal(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		String task1SubjectName = AT_Activity_Subject15;
		String task1Notes = AT_Activity_Notes1;

		String getAdvance1DueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy",
				Integer.parseInt("-1"));

		String record1 = AT_Activity_RelatedTo15;

		String relatedToVerification1 = record1 + "<break>" + themeName;

		String sectionName = AT_Theme_Grid_16;
		String columnNames = AT_Theme_GridColumns_47;

		String[][] task1BasicSection = { { AMNNR_CallLabel1, task1SubjectName }, { AMNNR_CallLabel2, task1Notes } };

		String[][] task1BasicSectionVerification = { { AMNNR_CallLabel1, task1SubjectName },
				{ AMNNR_CallLabel2, task1Notes }, { AMNNR_CallLabel3, relatedToVerification1 } };

		String[][] task1AdvancedSection = { { AMNNR_CallLabel4, getAdvance1DueDate },
				{ AMNNR_CallLabel5, crmUser1FirstName + " " + crmUser1LastName } };

		List<DataContainer> datas = new ArrayList<DataContainer>();

		datas.add(new DataContainer(sectionName, columnNames, record1, task1SubjectName, task1BasicSection,
				task1AdvancedSection, IconType.Call, PageName.ThemesPage));

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		List<String> negativeResultOfCalls = theme.createLogANoteFromSectionOfThemeAndVerification(true, themeTabName,
				themeName, task1SubjectName, projectName, task1BasicSection, task1AdvancedSection, null, null, null,
				false, null, null, null, null, null, null, datas.get(0).gridName,
				datas.get(0).recordNameBasedOnColumnSelect, false);

		if (negativeResultOfCalls.isEmpty()) {
			log(LogStatus.PASS, "-----Verified Create Log a Note-----", YesNo.No);

			ArrayList<String> subjectLinkPopUpNegativeResult = BP.verifySubjectLinkPopUpOnIntraction(driver,
					task1SubjectName, task1BasicSectionVerification, task1AdvancedSection, IconType.Call,
					PageName.FromViewAll);

			if (subjectLinkPopUpNegativeResult.isEmpty()) {
				log(LogStatus.PASS,
						"------" + task1SubjectName
								+ " record is able to open popup after click on it and verify its data" + "------",
						YesNo.No);

			} else {
				log(LogStatus.ERROR, "------" + task1SubjectName + " record link popup is not verified, Reason: "
						+ subjectLinkPopUpNegativeResult + "------", YesNo.Yes);
				sa.assertTrue(false, "------" + task1SubjectName + " record link popup is not verified, Reason: "
						+ subjectLinkPopUpNegativeResult + "------");

			}

		} else {
			sa.assertTrue(false, "-----Not Verified Create Log a Note-----, Reason: " + negativeResultOfCalls);
			log(LogStatus.FAIL, "-----Not Verified Create Log a Note-----, Reason: " + negativeResultOfCalls,
					YesNo.Yes);
		}

		driver.close();
		driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc032_CreateLogACallNoteAndVerificationForTarget(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		String task1SubjectName = AT_Activity_Subject16;
		String task1Notes = AT_Activity_Notes1;

		String getAdvance1DueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy",
				Integer.parseInt("-1"));

		String record1 = AT_Activity_RelatedTo16;

		String relatedToVerification1 = record1 + "<break>" + themeName;

		String sectionName = AT_Theme_Grid_18;
		String columnNames = AT_Theme_GridColumns_47;

		String[][] task1BasicSection = { { AMNNR_CallLabel1, task1SubjectName }, { AMNNR_CallLabel2, task1Notes } };

		String[][] task1BasicSectionVerification = { { AMNNR_CallLabel1, task1SubjectName },
				{ AMNNR_CallLabel2, task1Notes }, { AMNNR_CallLabel3, relatedToVerification1 } };

		String[][] task1AdvancedSection = { { AMNNR_CallLabel4, getAdvance1DueDate },
				{ AMNNR_CallLabel5, crmUser1FirstName + " " + crmUser1LastName } };

		List<DataContainer> datas = new ArrayList<DataContainer>();

		datas.add(new DataContainer(sectionName, columnNames, record1, task1SubjectName, task1BasicSection,
				task1AdvancedSection, IconType.Call, PageName.ThemesPage));

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		List<String> negativeResultOfCalls = theme.createLogANoteFromSectionOfThemeAndVerification(true, themeTabName,
				themeName, task1SubjectName, projectName, task1BasicSection, task1AdvancedSection, null, null, null,
				false, null, null, null, null, null, null, datas.get(0).gridName,
				datas.get(0).recordNameBasedOnColumnSelect, false);

		if (negativeResultOfCalls.isEmpty()) {
			log(LogStatus.PASS, "-----Verified Create Log a Note-----", YesNo.No);

			ArrayList<String> subjectLinkPopUpNegativeResult = BP.verifySubjectLinkPopUpOnIntraction(driver,
					task1SubjectName, task1BasicSectionVerification, task1AdvancedSection, IconType.Call,
					PageName.FromViewAll);

			if (subjectLinkPopUpNegativeResult.isEmpty()) {
				log(LogStatus.PASS,
						"------" + task1SubjectName
								+ " record is able to open popup after click on it and verify its data" + "------",
						YesNo.No);

			} else {
				log(LogStatus.ERROR, "------" + task1SubjectName + " record link popup is not verified, Reason: "
						+ subjectLinkPopUpNegativeResult + "------", YesNo.Yes);
				sa.assertTrue(false, "------" + task1SubjectName + " record link popup is not verified, Reason: "
						+ subjectLinkPopUpNegativeResult + "------");

			}

		} else {
			sa.assertTrue(false, "-----Not Verified Create Log a Note-----, Reason: " + negativeResultOfCalls);
			log(LogStatus.FAIL, "-----Not Verified Create Log a Note-----, Reason: " + negativeResultOfCalls,
					YesNo.Yes);
		}

		driver.close();
		driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc033_CreateLogACallNoteAndVerificationForTheme(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		String task1SubjectName = AT_Activity_Subject17;
		String task1Notes = AT_Activity_Notes1;

		String getAdvance1DueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy",
				Integer.parseInt("-1"));

		String record1 = AT_Activity_RelatedTo17;

		String relatedToVerification1 = record1 + "<break>" + themeName;

		String sectionName = AT_Theme_Grid_19;
		String columnNames = AT_Theme_GridColumns_47;

		String[][] task1BasicSection = { { AMNNR_CallLabel1, task1SubjectName }, { AMNNR_CallLabel2, task1Notes } };

		String[][] task1BasicSectionVerification = { { AMNNR_CallLabel1, task1SubjectName },
				{ AMNNR_CallLabel2, task1Notes }, { AMNNR_CallLabel3, relatedToVerification1 } };

		String[][] task1AdvancedSection = { { AMNNR_CallLabel4, getAdvance1DueDate },
				{ AMNNR_CallLabel5, crmUser1FirstName + " " + crmUser1LastName } };

		List<DataContainer> datas = new ArrayList<DataContainer>();

		datas.add(new DataContainer(sectionName, columnNames, record1, task1SubjectName, task1BasicSection,
				task1AdvancedSection, IconType.Call, PageName.ThemesPage));

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		List<String> negativeResultOfCalls = theme.createLogANoteFromSectionOfThemeAndVerification(true, themeTabName,
				themeName, task1SubjectName, projectName, task1BasicSection, task1AdvancedSection, null, null, null,
				false, null, null, null, null, null, null, datas.get(0).gridName,
				datas.get(0).recordNameBasedOnColumnSelect, false);

		if (negativeResultOfCalls.isEmpty()) {
			log(LogStatus.PASS, "-----Verified Create Log a Note-----", YesNo.No);

			ArrayList<String> subjectLinkPopUpNegativeResult = BP.verifySubjectLinkPopUpOnIntraction(driver,
					task1SubjectName, task1BasicSectionVerification, task1AdvancedSection, IconType.Call,
					PageName.FromViewAll);

			if (subjectLinkPopUpNegativeResult.isEmpty()) {
				log(LogStatus.PASS,
						"------" + task1SubjectName
								+ " record is able to open popup after click on it and verify its data" + "------",
						YesNo.No);

			} else {
				log(LogStatus.ERROR, "------" + task1SubjectName + " record link popup is not verified, Reason: "
						+ subjectLinkPopUpNegativeResult + "------", YesNo.Yes);
				sa.assertTrue(false, "------" + task1SubjectName + " record link popup is not verified, Reason: "
						+ subjectLinkPopUpNegativeResult + "------");

			}

		} else {
			sa.assertTrue(false, "-----Not Verified Create Log a Note-----, Reason: " + negativeResultOfCalls);
			log(LogStatus.FAIL, "-----Not Verified Create Log a Note-----, Reason: " + negativeResultOfCalls,
					YesNo.Yes);
		}

		driver.close();
		driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc034_CreateLogACallNoteAndVerificationForBuySideDeal(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		String task1SubjectName = AT_Activity_Subject18;
		String task1Notes = AT_Activity_Notes1;

		String getAdvance1DueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "M/d/yyyy",
				Integer.parseInt("-1"));

		String record1 = AT_Activity_RelatedTo18;

		String relatedToVerification1 = record1 + "<break>" + themeName;

		String sectionName = AT_Theme_Grid_17;
		String columnNames = AT_Theme_GridColumns_47;

		String[][] task1BasicSection = { { AMNNR_CallLabel1, task1SubjectName }, { AMNNR_CallLabel2, task1Notes } };

		String[][] task1BasicSectionVerification = { { AMNNR_CallLabel1, task1SubjectName },
				{ AMNNR_CallLabel2, task1Notes }, { AMNNR_CallLabel3, relatedToVerification1 } };

		String[][] task1AdvancedSection = { { AMNNR_CallLabel4, getAdvance1DueDate },
				{ AMNNR_CallLabel5, crmUser1FirstName + " " + crmUser1LastName } };

		List<DataContainer> datas = new ArrayList<DataContainer>();

		datas.add(new DataContainer(sectionName, columnNames, record1, task1SubjectName, task1BasicSection,
				task1AdvancedSection, IconType.Call, PageName.ThemesPage));

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		List<String> negativeResultOfCalls = theme.createLogANoteFromSectionOfThemeAndVerification(true, themeTabName,
				themeName, task1SubjectName, projectName, task1BasicSection, task1AdvancedSection, null, null, null,
				false, null, null, null, null, null, null, datas.get(0).gridName,
				datas.get(0).recordNameBasedOnColumnSelect, false);

		if (negativeResultOfCalls.isEmpty()) {
			log(LogStatus.PASS, "-----Verified Create Log a Note-----", YesNo.No);

			ArrayList<String> subjectLinkPopUpNegativeResult = BP.verifySubjectLinkPopUpOnIntraction(driver,
					task1SubjectName, task1BasicSectionVerification, task1AdvancedSection, IconType.Call,
					PageName.FromViewAll);

			if (subjectLinkPopUpNegativeResult.isEmpty()) {
				log(LogStatus.PASS,
						"------" + task1SubjectName
								+ " record is able to open popup after click on it and verify its data" + "------",
						YesNo.No);

			} else {
				log(LogStatus.ERROR, "------" + task1SubjectName + " record link popup is not verified, Reason: "
						+ subjectLinkPopUpNegativeResult + "------", YesNo.Yes);
				sa.assertTrue(false, "------" + task1SubjectName + " record link popup is not verified, Reason: "
						+ subjectLinkPopUpNegativeResult + "------");

			}

		} else {
			sa.assertTrue(false, "-----Not Verified Create Log a Note-----, Reason: " + negativeResultOfCalls);
			log(LogStatus.FAIL, "-----Not Verified Create Log a Note-----, Reason: " + negativeResultOfCalls,
					YesNo.Yes);
		}

		driver.close();
		driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityThemeTc035_CopyThemeFunctionalityWithCopyAllInteractionAndWithoutCopyAllWhenThereisData(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeTabName = tabObj9;

		String themeName = AT_Theme2;
		String themeDescription = "";
		String newNameOfThemeForCopy = AT_Theme58;
		String errorMsg = null;

		String themeName3 = AT_Theme2;
		String themeDescription3 = "";
		String newNameOfThemeForCopy3 = AT_Theme59;
		String errorMsg3 = null;

		String themeName4 = AT_Theme2;
		String themeDescription4 = "";
		String newNameOfThemeForCopy4 = AT_Theme60;
		String errorMsg4 = null;

		HashMap<String, Integer> expectedSectionNameAndCount = new HashMap<String, Integer>();
		expectedSectionNameAndCount.put(AT_Theme_Grid_11, 5);
		expectedSectionNameAndCount.put(AT_Theme_Grid_12, 5);
		expectedSectionNameAndCount.put(AT_Theme_Grid_13, 5);
		expectedSectionNameAndCount.put(AT_Theme_Grid_14, 5);
		expectedSectionNameAndCount.put(AT_Theme_Grid_15, 5);
		expectedSectionNameAndCount.put(AT_Theme_Grid_16, 2);
		expectedSectionNameAndCount.put(AT_Theme_Grid_17, 2);
		expectedSectionNameAndCount.put(AT_Theme_Grid_18, 5);
		expectedSectionNameAndCount.put(AT_Theme_Grid_19, 5);
		expectedSectionNameAndCount.put(AT_Theme_Grid_20, 5);
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		if (theme.createCopyTheme(false, true, true, true, true, false, PageName.ThemesPage, projectName, themeTabName,
				themeName, themeName, null, newNameOfThemeForCopy, themeDescription, errorMsg)) {
			log(LogStatus.INFO, "Copy Theme of Theme Name: " + themeName + " has been created", YesNo.No);

			if (theme.navigateToTheme(projectName, themeTabName, newNameOfThemeForCopy, false)) {
				log(LogStatus.ERROR, "-----Able to Navigate To Theme: " + newNameOfThemeForCopy + "------", YesNo.No);

				if (theme.themeNoItemDisplay(10) != null) {

					log(LogStatus.INFO,
							"Verified: No items to display msg is showing in Interaction Section after Copy All Interaction is not Selected",
							YesNo.No);

				} else {

					log(LogStatus.ERROR,
							"No items to display msg is not showing in Interaction Section after Copy All Interaction is not Selected",
							YesNo.No);
					sa.assertTrue(false,
							"No items to display msg is not showing in Interaction Section after Copy All Interaction is not Selected");
				}

			}

			else {
				sa.assertTrue(false, "Not Successfully Navigate to theme: " + newNameOfThemeForCopy);
				log(LogStatus.FAIL, "Not Successfully Navigate to theme: " + newNameOfThemeForCopy, YesNo.Yes);
			}

			if (theme.verifyItsLeftCountAndGridCountSepratelyAndTotalSumWithAllCount(false, themeTabName, themeName,
					projectName, false, expectedSectionNameAndCount, true, true, true, true)) {
				log(LogStatus.PASS, "-----Verified Counts of Theme Section Wise-----", YesNo.No);

			} else {
				sa.assertTrue(false, "-----Not Verified Counts of Theme Section Wise-----");
				log(LogStatus.FAIL, "-----Not Verified Counts of Theme Section Wise-----", YesNo.Yes);
			}

			driver.close();
			driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());

		} else {
			log(LogStatus.ERROR, "Copy Theme of Theme Name: " + themeName + " has not been created", YesNo.No);
			sa.assertTrue(false, "Copy Theme of Theme Name: " + themeName + " has not been created");
		}

		if (theme.createCopyTheme(true, true, true, true, true, false, PageName.ThemesPage, projectName, themeTabName,
				themeName, themeName, null, newNameOfThemeForCopy3, themeDescription3, errorMsg3)) {
			log(LogStatus.INFO, "Copy Theme of Existing Theme Name: " + themeName3 + " has been created with Name: "
					+ newNameOfThemeForCopy3, YesNo.No);

			if (theme.navigateToTheme(projectName, themeTabName, newNameOfThemeForCopy3, false)) {
				log(LogStatus.ERROR, "-----Able to Navigate To Theme: " + newNameOfThemeForCopy3 + "------", YesNo.No);

				if (theme.themeNoItemDisplay(8) == null) {

					log(LogStatus.INFO,
							"Verified: No items to display msg is not showing in Interaction Section after Copy All Interaction is Selected",
							YesNo.No);

				} else {

					log(LogStatus.ERROR,
							"No items to display msg is showing in Interaction Section after Copy All Interaction is Selected",
							YesNo.No);
					sa.assertTrue(false,
							"No items to display msg is showing in Interaction Section after Copy All Interaction is Selected");
				}

			}

			else {
				sa.assertTrue(false, "Not Successfully Navigate to theme: " + newNameOfThemeForCopy3);
				log(LogStatus.FAIL, "Not Successfully Navigate to theme: " + newNameOfThemeForCopy3, YesNo.Yes);
			}

			if (theme.verifyItsLeftCountAndGridCountSepratelyAndTotalSumWithAllCount(false, themeTabName, themeName,
					projectName, false, expectedSectionNameAndCount, true, true, true, true)) {
				log(LogStatus.PASS, "-----Verified Counts of Theme Section Wise-----", YesNo.No);

			} else {
				sa.assertTrue(false, "-----Not Verified Counts of Theme Section Wise-----");
				log(LogStatus.FAIL, "-----Not Verified Counts of Theme Section Wise-----", YesNo.Yes);
			}

			driver.close();
			driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
		} else {
			log(LogStatus.ERROR, "Copy Theme of Existing Theme Name: " + themeName3
					+ " has not been created with Name: " + newNameOfThemeForCopy3, YesNo.No);
			sa.assertTrue(false, "Copy Theme of Existing Theme Name: " + themeName3
					+ " has not been created with Name: " + newNameOfThemeForCopy3);
		}

		if (theme.createCopyTheme(false, true, true, true, true, false, PageName.ThemesPage, projectName, themeTabName,
				themeName4, null, null, newNameOfThemeForCopy4, themeDescription4, errorMsg4)) {
			log(LogStatus.INFO, "Copy Theme of Theme Name: " + themeName + " has been created", YesNo.No);
		} else {
			log(LogStatus.ERROR, "Copy Theme of Theme Name: " + themeName + " has not been created", YesNo.No);
			sa.assertTrue(false, "Copy Theme of Theme Name: " + themeName + " has not been created");
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc036_AllRecordsRemoveFromThemeGridsFunctionality(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeName = AT_Theme60;
		String themeTabName = tabObj9;

		String[][] data = { AT_Theme_RemoveToTheme_61.split("<Section>", -1),
				AT_Theme_RemoveToTheme_62.split("<Section>", -1), AT_Theme_RemoveToTheme_63.split("<Section>", -1),
				AT_Theme_RemoveToTheme_64.split("<Section>", -1), AT_Theme_RemoveToTheme_65.split("<Section>", -1),
				AT_Theme_RemoveToTheme_66.split("<Section>", -1), AT_Theme_RemoveToTheme_67.split("<Section>", -1),
				AT_Theme_RemoveToTheme_68.split("<Section>", -1), AT_Theme_RemoveToTheme_69.split("<Section>", -1),
				AT_Theme_RemoveToTheme_70.split("<Section>", -1), };
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {
			log(LogStatus.PASS, "Successfully Navigate to theme: " + themeName, YesNo.No);
			for (String[] removeData : data) {

				if (theme.removeFromThemeBasedOnRecordOfColumnNumber(false, themeTabName, themeName, projectName, false,
						removeData[0], removeData[1], Integer.valueOf(removeData[2]),
						Boolean.parseBoolean(removeData[3]), Boolean.parseBoolean(removeData[4]))) {
					log(LogStatus.PASS, "-----Verify Records Removed from Grids-----", YesNo.No);

				} else {
					sa.assertTrue(false, "-----Not Verify Records Removed from Grids-----");
					log(LogStatus.FAIL, "-----Not Verify Records Removed from Grids-----", YesNo.Yes);
				}

			}

			driver.close();
			driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
		}

		else {
			sa.assertTrue(false, "Not Successfully Navigate to theme: " + themeName);
			log(LogStatus.FAIL, "Not Successfully Navigate to theme: " + themeName, YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc037_CreateAddToThemeFromDifferentObjects(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String themeName = AT_Theme2;
		String themeTabName = tabObj9;
		String newTheme = null;
		String themeDescription = null;

		List<String> expectedThemeRecordsToCheckRedirection = new ArrayList<String>();
		expectedThemeRecordsToCheckRedirection.add(themeName);

		String accountName1 = AT_Theme_AddToTheme_61;
		String accountName2 = AT_Theme_AddToTheme_62;
		String accountName3 = AT_Theme_AddToTheme_63;
		String accountName4 = AT_Theme_AddToTheme_64;
		String contactName1 = AT_Theme_AddToTheme_65;
		String dealName1 = AT_Theme_AddToTheme_66;
		String dealName2 = AT_Theme_AddToTheme_67;
		String targetName1 = AT_Theme_AddToTheme_68;
		String[] themeNameAndDescriptions = AT_Theme61.split("<Section>", -1);

		String[][] addToThemeData = { { AT_Theme_Grid_11, tabObj1, accountName1 },
				{ AT_Theme_Grid_12, tabObj1, accountName2 }, { AT_Theme_Grid_13, tabObj1, accountName3 },
				{ AT_Theme_Grid_14, tabObj1, accountName4 }, { AT_Theme_Grid_15, tabObj2, contactName1 },
				{ AT_Theme_Grid_16, tabObj4, dealName1 }, { AT_Theme_Grid_17, tabObj4, dealName2 },
				{ AT_Theme_Grid_18, tabObj3, targetName1 } };

		String clipName = AT_Clip1;
		String clipSummary = "";
		String relatedTag = themeName;

		String[][] clipPanel = { { "tag", relatedTag } };
		String[][] clipPopup = { { "Clip Name", clipName }, { "Summary", clipSummary } };
		String[] suggestedPopup = null;

		lp.CRMLogin(crmUser1EmailID, adminPassword);
		for (String[] addToTheme : addToThemeData) {
			if (BP.navigateToRecordAndClickOnSubTab(projectName, addToTheme[1], addToTheme[2], null)) {
				log(LogStatus.INFO, "Able to Open the Record: " + addToTheme[2], YesNo.No);

				if (theme.createAddToTheme(false, false, true, true, false, PageName.ThemesPage, projectName,
						themeTabName, themeName, addToTheme[0], null, themeName, null, null, false, false,
						false, null)) {
					log(LogStatus.PASS, "-----Add To Theme Created for Theme: " + themeName + " for Object: "
							+ addToTheme[1] + " and for Record: " + addToTheme[2] + " -----", YesNo.No);
				} else {
					sa.assertTrue(false, "-----Add To Theme Not Created for Theme: " + themeName + " for Object: "
							+ addToTheme[1] + " and for Record: " + addToTheme[2] + " -----");
					log(LogStatus.FAIL, "-----Add To Theme Not Created for Theme: " + themeName + " for Object: "
							+ addToTheme[1] + " and for Record: " + addToTheme[2] + " -----", YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Not able to Open the Record: " + addToTheme[2], YesNo.No);
				sa.assertTrue(false, "Not able to Open the Record: " + addToTheme[2]);
			}
		}

		for (String themeNameAndDescription : themeNameAndDescriptions) {

			String[] themeNameAndDescriptionList = themeNameAndDescription.split("<Break>", -1);

			newTheme = themeNameAndDescriptionList[0];
			themeDescription = themeNameAndDescriptionList[1];
			if (theme.createTheme(projectName, themeTabName, newTheme, themeDescription)) {
				log(LogStatus.INFO, "Record: " + newTheme + " has been Created under: " + themeTabName, YesNo.No);

				if (theme.createAddToTheme(true, false, true, true, true, PageName.ThemesPage, projectName,
						themeTabName, newTheme, null, null, themeName, null, null, false, false, false, null)) {
					log(LogStatus.PASS,
							"-----Add To Theme Created for Theme: " + newTheme + " for Object: " + tabObj9 + " -----",
							YesNo.No);
				} else {
					sa.assertTrue(false, "-----Add To Theme Not Created for Theme: " + newTheme + " for Object: "
							+ tabObj9 + " -----");
					log(LogStatus.FAIL, "-----Add To Theme Not Created for Theme: " + newTheme + " for Object: "
							+ tabObj9 + " -----", YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "Record: " + newTheme + " has not been Created under: " + themeTabName
						+ " So not able to Create Add to Theme", YesNo.No);
				sa.assertTrue(false, "Record: " + newTheme + " has not been Created under: " + themeTabName
						+ " So not able to Create Add to Theme");
			}

		}

		CommonLib.refresh(driver);

		if (clickUsingJavaScript(driver, BP.getClipTextFromNavigation(10), "clip on navigation")) {
			log(LogStatus.INFO, "Clicked on clip button from navigation", YesNo.No);
			if (BP.createClip(clipPanel, clipPopup, suggestedPopup)) {
				log(LogStatus.INFO, "The Clip has been created. clip name " + clipName, YesNo.No);
			} else {
				log(LogStatus.ERROR, "Not able to create the clip. clip name " + clipName, YesNo.No);
				sa.assertTrue(false, "Not able to create the clip. clip name " + clipName);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on clip button from navigation", YesNo.No);
			sa.assertTrue(false, "Not able to click on clip button from navigation");

		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityThemeTc038_CheckNavAndGridCountsOfThemeAfterCreateAddToThemeFromDifferentObjects(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeTabName = tabObj9;
		String themeName = AT_Theme2;
		HashMap<String, Integer> expectedSectionNameAndCount = new HashMap<String, Integer>();
		expectedSectionNameAndCount.put(AT_Theme_Grid_11, 6);
		expectedSectionNameAndCount.put(AT_Theme_Grid_12, 6);
		expectedSectionNameAndCount.put(AT_Theme_Grid_13, 6);
		expectedSectionNameAndCount.put(AT_Theme_Grid_14, 6);
		expectedSectionNameAndCount.put(AT_Theme_Grid_15, 6);
		expectedSectionNameAndCount.put(AT_Theme_Grid_16, 3);
		expectedSectionNameAndCount.put(AT_Theme_Grid_17, 3);
		expectedSectionNameAndCount.put(AT_Theme_Grid_18, 6);
		expectedSectionNameAndCount.put(AT_Theme_Grid_19, 6);
		expectedSectionNameAndCount.put(AT_Theme_Grid_20, 6);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (theme.verifyItsLeftCountAndGridCountSepratelyAndTotalSumWithAllCount(true, themeTabName, themeName,
				projectName, true, expectedSectionNameAndCount, true, true, true, true)) {
			log(LogStatus.PASS, "-----Verified Counts of Theme Section Wise-----", YesNo.No);

		} else {
			sa.assertTrue(false, "-----Not Verified Counts of Theme Section Wise-----");
			log(LogStatus.FAIL, "-----Not Verified Counts of Theme Section Wise-----", YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc039_VerifyAddToThemeErrorMsgAndCreateAddToThemeFromResearchWhenIncludeAllCheckboxIsInActive(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeName = AT_Theme71;
		String themeTabName = tabObj9;
		String errorMsginResearch = BasePageErrorMessage.errorMsginResearchTheme;
		ResearchDataContainer researchDataContainer = new ResearchDataContainer(AT_Theme_Grid_12, "", "", "", "", "",
				action.BOOLEAN, 20);

		String[] objectOrRecordTypesNames = AT_Theme_Grid_12.split("<break>", -1);

		String[][] addToThemeData = { { AT_Theme_Grid_11, tabObj1 } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		for (String[] data : addToThemeData) {

			if (theme.createAddToTheme(true, true, false, true, true, PageName.ThemesPage, projectName, themeTabName,
					themeName, data[0], data[1], null, researchDataContainer, objectOrRecordTypesNames, false, true,
					true, errorMsginResearch)) {
				log(LogStatus.PASS, "-----Add To Theme Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Objects: " + objectOrRecordTypesNames + " -----", YesNo.No);
			} else {
				sa.assertTrue(false, "-----Add To Theme Not Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Objects: " + objectOrRecordTypesNames + " -----");
				log(LogStatus.FAIL, "-----Add To Theme Not Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Objects: " + objectOrRecordTypesNames + " -----", YesNo.Yes);
			}

		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityThemeTc040_CheckNavAndGridCountsOfThemeAfterCreateAddToThemeResearchPageWhenIncludeAllCheckboxIsInActive(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeTabName = tabObj9;
		String themeName = AT_Theme71;
		HashMap<String, Integer> expectedSectionNameAndCount = new HashMap<String, Integer>();

		expectedSectionNameAndCount.put(AT_Theme_Grid_12, 12);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (theme.verifyItsLeftCountAndGridCountSepratelyAndTotalSumWithAllCount(true, themeTabName, themeName,
				projectName, true, expectedSectionNameAndCount, true, true, true, true)) {
			log(LogStatus.PASS, "-----Verified Counts of Theme Section Wise-----", YesNo.No);

		} else {
			sa.assertTrue(false, "-----Not Verified Counts of Theme Section Wise-----");
			log(LogStatus.FAIL, "-----Not Verified Counts of Theme Section Wise-----", YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}
	
	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc041_CreateAddToThemeFromResearchWhenIncludeAllCheckboxIsActive(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeName = AT_Theme71;
		String themeTabName = tabObj9;
		String errorMsginResearch = BasePageErrorMessage.errorMsginResearchTheme;
		ResearchDataContainer researchDataContainer = new ResearchDataContainer(AT_Theme_Grid_12, "", "", "", "", "",
				action.BOOLEAN, 20);

		String[] objectOrRecordTypesNames = AT_Theme_Grid_12.split("<break>", -1);

		String[][] addToThemeData = { { AT_Theme_Grid_11, tabObj1 } };
		boolean flag = false;

		HashMap<String, Integer> expectedSectionNameAndCount = new HashMap<String, Integer>();

		expectedSectionNameAndCount.put(AT_Theme_Grid_12, 12);
		expectedSectionNameAndCount.put(AT_Theme_Grid_15, 6);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		for (String[] data : addToThemeData) {

			if (theme.createAddToTheme(true, true, false, true, false, PageName.ThemesPage, projectName, themeTabName,
					themeName, data[0], data[1], null, researchDataContainer, objectOrRecordTypesNames, true, true,
					true, errorMsginResearch)) {
				log(LogStatus.PASS, "-----Add To Theme Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Objects: " + objectOrRecordTypesNames + " -----", YesNo.No);
				flag = true;
			} else {
				sa.assertTrue(false, "-----Add To Theme Not Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Objects: " + objectOrRecordTypesNames + " -----");
				log(LogStatus.FAIL, "-----Add To Theme Not Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Objects: " + objectOrRecordTypesNames + " -----", YesNo.Yes);
			}

		}

		if (flag) {

			CommonLib.refresh(driver);
			if (theme.verifyItsLeftCountAndGridCountSepratelyAndTotalSumWithAllCount(false, themeTabName, themeName,
					projectName, false, expectedSectionNameAndCount, true, true, true, true)) {
				log(LogStatus.PASS, "-----Verified Counts of Theme Section Wise-----", YesNo.No);

			} else {
				sa.assertTrue(false, "-----Not Verified Counts of Theme Section Wise-----");
				log(LogStatus.FAIL, "-----Not Verified Counts of Theme Section Wise-----", YesNo.Yes);
			}

			driver.close();
			driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
		} else {
			sa.assertTrue(false,
					"-----Add To Theme not Created Successfully, So not able to Check Counts of Grids in Theme-----");
			log(LogStatus.FAIL,
					"-----Add To Theme not Created Successfully, So not able to Check Counts of Grids in Theme-----",
					YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc042_CreateAddToThemeFromResearchWhenIncludeAllCheckboxIsActiveRepeatSoToCheckNoDuplicateContactsAdded(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeName = AT_Theme71;
		String themeTabName = tabObj9;
		String errorMsginResearch = null;
		ResearchDataContainer researchDataContainer = new ResearchDataContainer(AT_Theme_Grid_12, "", "", "", "", "",
				action.BOOLEAN, 20);

		String[] objectOrRecordTypesNames = AT_Theme_Grid_12.split("<break>", -1);

		String[][] addToThemeData = { { AT_Theme_Grid_11, tabObj1 } };
		boolean flag = false;

		HashMap<String, Integer> expectedSectionNameAndCount = new HashMap<String, Integer>();

		expectedSectionNameAndCount.put(AT_Theme_Grid_12, 12);
		expectedSectionNameAndCount.put(AT_Theme_Grid_15, 6);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		for (String[] data : addToThemeData) {

			if (theme.createAddToTheme(true, true, false, true, false, PageName.ThemesPage, projectName, themeTabName,
					themeName, data[0], data[1], null, researchDataContainer, objectOrRecordTypesNames, true, true,
					true, errorMsginResearch)) {
				log(LogStatus.PASS, "-----Add To Theme Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Objects: " + objectOrRecordTypesNames + " -----", YesNo.No);
				flag = true;
			} else {
				sa.assertTrue(false, "-----Add To Theme Not Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Objects: " + objectOrRecordTypesNames + " -----");
				log(LogStatus.FAIL, "-----Add To Theme Not Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Objects: " + objectOrRecordTypesNames + " -----", YesNo.Yes);
			}

		}

		if (flag) {

			CommonLib.refresh(driver);
			if (theme.verifyItsLeftCountAndGridCountSepratelyAndTotalSumWithAllCount(false, themeTabName, themeName,
					projectName, false, expectedSectionNameAndCount, true, true, true, true)) {
				log(LogStatus.PASS, "-----Verified Counts of Theme Section Wise-----", YesNo.No);

			} else {
				sa.assertTrue(false, "-----Not Verified Counts of Theme Section Wise-----");
				log(LogStatus.FAIL, "-----Not Verified Counts of Theme Section Wise-----", YesNo.Yes);
			}

			driver.close();
			driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
		} else {
			sa.assertTrue(false,
					"-----Add To Theme not Created Successfully, So not able to Check Counts of Grids in Theme-----");
			log(LogStatus.FAIL,
					"-----Add To Theme not Created Successfully, So not able to Check Counts of Grids in Theme-----",
					YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc043_CreateAddToThemeFromResearchWhenSearchForOneSpecificRecord(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeName = AT_Theme72;
		String themeTabName = tabObj9;
		String errorMsginResearch = null;

		ResearchDataContainer researchDataContainer = new ResearchDataContainer(AT_Theme_ResearchFinding_72, tabObj1,
				AT_Firm5, "", "", "", action.BOOLEAN, 20);

		String[] objectOrRecordTypesNames = AT_Theme_ResearchCategoriesSelect_72.split("<break>", -1);

		String[][] addToThemeData = { { AT_Theme_Grid_11, tabObj1 } };
		boolean flag = false;

		HashMap<String, Integer> expectedSectionNameAndCount = new HashMap<String, Integer>();
		expectedSectionNameAndCount.put(AT_Theme_Grid_12, 1);
		expectedSectionNameAndCount.put(AT_Theme_Grid_15, 1);
		expectedSectionNameAndCount.put(AT_Theme_Grid_17, 2);
		expectedSectionNameAndCount.put(AT_Theme_Grid_19, 2);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		for (String[] data : addToThemeData) {

			if (theme.createAddToTheme(true, true, false, true, false, PageName.ThemesPage, projectName, themeTabName,
					themeName, data[0], data[1], null, researchDataContainer, objectOrRecordTypesNames, true, true,
					true, errorMsginResearch)) {
				log(LogStatus.PASS, "-----Add To Theme Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Objects: " + objectOrRecordTypesNames + " -----", YesNo.No);
				flag = true;
			} else {
				sa.assertTrue(false, "-----Add To Theme Not Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Objects: " + objectOrRecordTypesNames + " -----");
				log(LogStatus.FAIL, "-----Add To Theme Not Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Objects: " + objectOrRecordTypesNames + " -----", YesNo.Yes);
			}

		}

		if (flag) {

			CommonLib.refresh(driver);
			if (theme.verifyItsLeftCountAndGridCountSepratelyAndTotalSumWithAllCount(false, themeTabName, themeName,
					projectName, false, expectedSectionNameAndCount, false, true, true, true)) {
				log(LogStatus.PASS, "-----Verified Counts of Theme Section Wise-----", YesNo.No);

			} else {
				sa.assertTrue(false, "-----Not Verified Counts of Theme Section Wise-----");
				log(LogStatus.FAIL, "-----Not Verified Counts of Theme Section Wise-----", YesNo.Yes);
			}

			driver.close();
			driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
		} else {
			sa.assertTrue(false,
					"-----Add To Theme not Created Successfully, So not able to Check Counts of Grids in Theme-----");
			log(LogStatus.FAIL,
					"-----Add To Theme not Created Successfully, So not able to Check Counts of Grids in Theme-----",
					YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc044_CreateAddToThemeFromResearchWhenSearchForSpecificRecords(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeName = AT_Theme73;
		String themeTabName = tabObj9;
		String errorMsginResearch = null;

		ResearchDataContainer researchDataContainer = new ResearchDataContainer(AT_Theme_ResearchFinding_72,
				tabObj1 + "<break>" + tabObj2 + "<break>" + tabObj4, AT_Theme_ResearchObjectValue_73, "", "", "",
				action.BOOLEAN, 20);

		String[] objectOrRecordTypesNames = AT_Theme_ResearchCategoriesSelect_72.split("<break>", -1);

		String[][] addToThemeData = { { AT_Theme_Grid_11, tabObj1 } };
		boolean flag = false;

		HashMap<String, Integer> expectedSectionNameAndCount = new HashMap<String, Integer>();

		expectedSectionNameAndCount.put(AT_Theme_Grid_12, 1);
		expectedSectionNameAndCount.put(AT_Theme_Grid_15, 1);
		expectedSectionNameAndCount.put(AT_Theme_Grid_17, 2);
		expectedSectionNameAndCount.put(AT_Theme_Grid_19, 3);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		for (String[] data : addToThemeData) {

			if (theme.createAddToTheme(true, true, false, true, false, PageName.ThemesPage, projectName, themeTabName,
					themeName, data[0], data[1], null, researchDataContainer, objectOrRecordTypesNames, false, true,
					true, errorMsginResearch)) {
				log(LogStatus.PASS, "-----Add To Theme Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Objects: " + objectOrRecordTypesNames + " -----", YesNo.No);
				flag = true;
			} else {
				sa.assertTrue(false, "-----Add To Theme Not Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Objects: " + objectOrRecordTypesNames + " -----");
				log(LogStatus.FAIL, "-----Add To Theme Not Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Objects: " + objectOrRecordTypesNames + " -----", YesNo.Yes);
			}

		}

		if (flag) {

			CommonLib.refresh(driver);
			if (theme.verifyItsLeftCountAndGridCountSepratelyAndTotalSumWithAllCount(false, themeTabName, themeName,
					projectName, false, expectedSectionNameAndCount, true, true, true, true)) {
				log(LogStatus.PASS, "-----Verified Counts of Theme Section Wise-----", YesNo.No);

			} else {
				sa.assertTrue(false, "-----Not Verified Counts of Theme Section Wise-----");
				log(LogStatus.FAIL, "-----Not Verified Counts of Theme Section Wise-----", YesNo.Yes);
			}

			if (theme.themeNoItemDisplay(5) == null) {

				log(LogStatus.INFO,
						"Verified: No items to display msg is not showing in Interaction Section after Select All Category",
						YesNo.No);

			} else {

				log(LogStatus.ERROR,
						"No items to display msg is showing in Interaction Section after Select All Category",
						YesNo.No);
				sa.assertTrue(false,

						"No items to display msg is showing in Interaction Section after Select All Category");
			}

			driver.close();
			driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
		} else {
			sa.assertTrue(false,
					"-----Add To Theme not Created Successfully, So not able to Check Counts of Grids in Theme-----");
			log(LogStatus.FAIL,
					"-----Add To Theme not Created Successfully, So not able to Check Counts of Grids in Theme-----",
					YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc045_CreateAddToThemeFromResearchWhenSearchByFieldParameter(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeName = AT_Theme74;
		String themeTabName = tabObj9;
		String errorMsginResearch = null;

		ResearchDataContainer researchDataContainer = new ResearchDataContainer(AT_Theme_ResearchFinding_72, "", "",
				AT_Theme_ResearchFieldName_74, AT_Theme_ResearchFieldOperator_74, AT_Theme_ResearchFieldValue_74,
				action.BOOLEAN, 20);

		String[] objectOrRecordTypesNames = AT_Theme_ResearchCategoriesSelect_72.split("<break>", -1);

		String[][] addToThemeData = { { AT_Theme_Grid_11, tabObj1 } };
		boolean flag = false;

		HashMap<String, Integer> expectedSectionNameAndCount = new HashMap<String, Integer>();
		expectedSectionNameAndCount.put(AT_Theme_Grid_11, 10);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		for (String[] data : addToThemeData) {

			if (theme.createAddToTheme(true, true, false, true, false, PageName.ThemesPage, projectName, themeTabName,
					themeName, data[0], data[1], null, researchDataContainer, objectOrRecordTypesNames, false, true,
					true, errorMsginResearch)) {
				log(LogStatus.PASS, "-----Add To Theme Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Objects: " + objectOrRecordTypesNames + " -----", YesNo.No);
				flag = true;
			} else {
				sa.assertTrue(false, "-----Add To Theme Not Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Objects: " + objectOrRecordTypesNames + " -----");
				log(LogStatus.FAIL, "-----Add To Theme Not Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Objects: " + objectOrRecordTypesNames + " -----", YesNo.Yes);
			}

		}

		if (flag) {

			CommonLib.refresh(driver);
			if (theme.verifyItsLeftCountAndGridCountSepratelyAndTotalSumWithAllCount(false, themeTabName, themeName,
					projectName, false, expectedSectionNameAndCount, true, true, true, true)) {
				log(LogStatus.PASS, "-----Verified Counts of Theme Section Wise-----", YesNo.No);

			} else {
				sa.assertTrue(false, "-----Not Verified Counts of Theme Section Wise-----");
				log(LogStatus.FAIL, "-----Not Verified Counts of Theme Section Wise-----", YesNo.Yes);
			}

			driver.close();
			driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
		} else {
			sa.assertTrue(false,
					"-----Add To Theme not Created Successfully, So not able to Check Counts of Grids in Theme-----");
			log(LogStatus.FAIL,
					"-----Add To Theme not Created Successfully, So not able to Check Counts of Grids in Theme-----",
					YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc046_CreateAddToThemeFromResearchWhen3SearchByFieldParameter(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeName = AT_Theme75;
		String themeTabName = tabObj9;
		String errorMsginResearch = null;

		ResearchDataContainer researchDataContainer = new ResearchDataContainer(AT_Theme_ResearchFinding_72, "", "",
				AT_Theme_ResearchFieldName_75, AT_Theme_ResearchFieldOperator_75, AT_Theme_ResearchFieldValue_75,
				action.BOOLEAN, 20);

		String[] objectOrRecordTypesNames = AT_Theme_ResearchCategoriesSelect_72.split("<break>", -1);

		String[][] addToThemeData = { { AT_Theme_Grid_11, tabObj1 } };
		boolean flag = false;

		HashMap<String, Integer> expectedSectionNameAndCount = new HashMap<String, Integer>();
		expectedSectionNameAndCount.put(AT_Theme_Grid_11, 10);
		expectedSectionNameAndCount.put(AT_Theme_Grid_15, 1);
		expectedSectionNameAndCount.put(AT_Theme_Grid_16, 4);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		for (String[] data : addToThemeData) {

			if (theme.createAddToTheme(true, true, false, true, false, PageName.ThemesPage, projectName, themeTabName,
					themeName, data[0], data[1], null, researchDataContainer, objectOrRecordTypesNames, false, true,
					true, errorMsginResearch)) {
				log(LogStatus.PASS, "-----Add To Theme Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Objects: " + objectOrRecordTypesNames + " -----", YesNo.No);
				flag = true;
			} else {
				sa.assertTrue(false, "-----Add To Theme Not Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Objects: " + objectOrRecordTypesNames + " -----");
				log(LogStatus.FAIL, "-----Add To Theme Not Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Objects: " + objectOrRecordTypesNames + " -----", YesNo.Yes);
			}

		}

		if (flag) {

			CommonLib.refresh(driver);
			if (theme.verifyItsLeftCountAndGridCountSepratelyAndTotalSumWithAllCount(false, themeTabName, themeName,
					projectName, false, expectedSectionNameAndCount, true, true, true, true)) {
				log(LogStatus.PASS, "-----Verified Counts of Theme Section Wise-----", YesNo.No);

			} else {
				sa.assertTrue(false, "-----Not Verified Counts of Theme Section Wise-----");
				log(LogStatus.FAIL, "-----Not Verified Counts of Theme Section Wise-----", YesNo.Yes);
			}

			driver.close();
			driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
		} else {
			sa.assertTrue(false,
					"-----Add To Theme not Created Successfully, So not able to Check Counts of Grids in Theme-----");
			log(LogStatus.FAIL,
					"-----Add To Theme not Created Successfully, So not able to Check Counts of Grids in Theme-----",
					YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc047_CreateAddToThemeFromResearchWhen3SearchBySpecificRecordAnd3SearchByFieldParameter(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeName = AT_Theme76;
		String themeTabName = tabObj9;
		String errorMsginResearch = null;

		ResearchDataContainer researchDataContainer = new ResearchDataContainer(AT_Theme_ResearchFinding_72,
				tabObj1 + "<break>" + tabObj2 + "<break>" + tabObj4, AT_Theme_ResearchObjectValue_76,
				AT_Theme_ResearchFieldName_75, AT_Theme_ResearchFieldOperator_75, AT_Theme_ResearchFieldValue_75,
				action.BOOLEAN, 20);

		String[] objectOrRecordTypesNames = AT_Theme_ResearchCategoriesSelect_72.split("<break>", -1);

		String[][] addToThemeData = { { AT_Theme_Grid_11, tabObj1 } };
		boolean flag = false;

		HashMap<String, Integer> expectedSectionNameAndCount = new HashMap<String, Integer>();
		expectedSectionNameAndCount.put(AT_Theme_Grid_11, 1);
		expectedSectionNameAndCount.put(AT_Theme_Grid_15, 1);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		for (String[] data : addToThemeData) {

			if (theme.createAddToTheme(true, true, false, true, false, PageName.ThemesPage, projectName, themeTabName,
					themeName, data[0], data[1], null, researchDataContainer, objectOrRecordTypesNames, false, true,
					true, errorMsginResearch)) {
				log(LogStatus.PASS, "-----Add To Theme Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Objects: " + objectOrRecordTypesNames + " -----", YesNo.No);
				flag = true;
			} else {
				sa.assertTrue(false, "-----Add To Theme Not Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Objects: " + objectOrRecordTypesNames + " -----");
				log(LogStatus.FAIL, "-----Add To Theme Not Created for Theme: " + themeName + " for Object: " + data[1]
						+ " and for Objects: " + objectOrRecordTypesNames + " -----", YesNo.Yes);
			}

		}

		if (flag) {

			CommonLib.refresh(driver);
			if (theme.verifyItsLeftCountAndGridCountSepratelyAndTotalSumWithAllCount(false, themeTabName, themeName,
					projectName, false, expectedSectionNameAndCount, true, true, true, true)) {
				log(LogStatus.PASS, "-----Verified Counts of Theme Section Wise-----", YesNo.No);

			} else {
				sa.assertTrue(false, "-----Not Verified Counts of Theme Section Wise-----");
				log(LogStatus.FAIL, "-----Not Verified Counts of Theme Section Wise-----", YesNo.Yes);
			}

			driver.close();
			driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
		} else {
			sa.assertTrue(false,
					"-----Add To Theme not Created Successfully, So not able to Check Counts of Grids in Theme-----");
			log(LogStatus.FAIL,
					"-----Add To Theme not Created Successfully, So not able to Check Counts of Grids in Theme-----",
					YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityThemeTc048_CreateThemeRecordFromNewThemeButtonAndVerifyErrorMsg(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String[] themeNameAndDescriptions = AT_Theme77.split("<Section>", -1);

		String themeTabName = tabObj9;

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		String themeName = null;
		String themeDescription = null;
		for (String themeNameAndDescription : themeNameAndDescriptions) {

			String[] themeNameAndDescriptionList = themeNameAndDescription.split("<Break>", -1);

			themeName = themeNameAndDescriptionList[0];
			themeDescription = themeNameAndDescriptionList[1];

			if (theme.createTheme(projectName, themeTabName, false, themeName, themeDescription, null, false,
					"Please Enter the Theme Name", false)) {
				log(LogStatus.INFO, "Cancel Theme and Validation Rule Error Msg Verified", YesNo.No);
			} else {
				log(LogStatus.ERROR, "Cancel Theme and Validation Rule Error Msg has not been Verified", YesNo.No);
				sa.assertTrue(false, "Cancel Theme and Validation Rule Error Msg has not been Verified");
			}

		}

		for (String themeNameAndDescription : themeNameAndDescriptions) {

			String[] themeNameAndDescriptionList = themeNameAndDescription.split("<Break>", -1);

			themeName = themeNameAndDescriptionList[0];
			themeDescription = themeNameAndDescriptionList[1];

			if (theme.createTheme(projectName, themeTabName, false, themeName, themeDescription, null, false, null,
					true)) {
				log(LogStatus.INFO, "Record: " + themeName + " has been Created under: " + themeTabName, YesNo.No);
			} else {
				log(LogStatus.ERROR, "Record: " + themeName + " has not been Created under: " + themeTabName, YesNo.No);
				sa.assertTrue(false, "Record: " + themeName + " has not been Created under: " + themeTabName);
			}

		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityThemeTc049_CreateThemeRecordFromNewThemeButtonWithCopyExistingThemeAndWithoutCopyAllInteraction(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String[] themeNameAndDescriptions = AT_Theme78.split("<Section>", -1);

		String themeTabName = tabObj9;

		String themeName = null;
		String themeDescription = null;
		String existingThemeName = AT_Theme2;
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		for (String themeNameAndDescription : themeNameAndDescriptions) {

			String[] themeNameAndDescriptionList = themeNameAndDescription.split("<Break>", -1);

			themeName = themeNameAndDescriptionList[0];
			themeDescription = themeNameAndDescriptionList[1];

			if (theme.createTheme(projectName, themeTabName, true, themeName, themeDescription, existingThemeName,
					false, null, false)) {
				log(LogStatus.INFO,
						"Cancel Functionality of Copy Theme from New Theme with uncheck of Copy All Interaction has been Verified",
						YesNo.No);
			} else {
				log(LogStatus.ERROR,
						"Cancel Functionality of Copy Theme from New Theme with uncheck of Copy All Interaction has not been Verified",
						YesNo.No);
				sa.assertTrue(false,
						"Cancel Functionality of Copy Theme from New Theme with uncheck of Copy All Interaction has not been Verified");
			}

		}

		for (String themeNameAndDescription : themeNameAndDescriptions) {

			String[] themeNameAndDescriptionList = themeNameAndDescription.split("<Break>", -1);

			themeName = themeNameAndDescriptionList[0];
			themeDescription = themeNameAndDescriptionList[1];

			if (theme.createTheme(projectName, themeTabName, true, themeName, themeDescription, existingThemeName,
					false, null, true)) {
				log(LogStatus.INFO, "Copy Theme Record: " + themeName + " has been Created under: " + themeTabName,
						YesNo.No);
			} else {
				log(LogStatus.ERROR, "Copy Theme Record: " + themeName + " has not been Created under: " + themeTabName,
						YesNo.No);
				sa.assertTrue(false,
						"Copy Theme Record: " + themeName + " has not been Created under: " + themeTabName);
			}

		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityThemeTc050_CreateThemeRecordFromNewThemeButtonWithCopyExistingThemeAndWithCopyAllInteraction(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String[] themeNameAndDescriptions = AT_Theme79.split("<Section>", -1);

		String themeTabName = tabObj9;

		String themeName = null;
		String themeDescription = null;
		String existingThemeName = AT_Theme2;
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		for (String themeNameAndDescription : themeNameAndDescriptions) {

			String[] themeNameAndDescriptionList = themeNameAndDescription.split("<Break>", -1);

			themeName = themeNameAndDescriptionList[0];
			themeDescription = themeNameAndDescriptionList[1];

			if (theme.createTheme(projectName, themeTabName, true, themeName, themeDescription, existingThemeName, true,
					null, false)) {
				log(LogStatus.INFO,
						"Cancel Functionality of Copy Theme from New Theme with check of Copy All Interaction has been Verified",
						YesNo.No);
			} else {
				log(LogStatus.ERROR,
						"Cancel Functionality of Copy Theme from New Theme with check of Copy All Interaction has not been Verified",
						YesNo.No);
				sa.assertTrue(false,
						"Cancel Functionality of Copy Theme from New Theme with check of Copy All Interaction has not been Verified");
			}

		}

		for (String themeNameAndDescription : themeNameAndDescriptions) {

			String[] themeNameAndDescriptionList = themeNameAndDescription.split("<Break>", -1);

			themeName = themeNameAndDescriptionList[0];
			themeDescription = themeNameAndDescriptionList[1];

			if (theme.createTheme(projectName, themeTabName, true, themeName, themeDescription, existingThemeName, true,
					null, true)) {
				log(LogStatus.INFO, "Record: " + themeName + " has been Created under: " + themeTabName, YesNo.No);

				if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {
					log(LogStatus.ERROR, "-----Able to Navigate To Theme: " + themeName + "------", YesNo.No);

					if (theme.themeNoItemDisplay(8) == null) {

						log(LogStatus.INFO,
								"Verified: No items to display msg is not showing in Interaction Section after Copy All Interaction is Selected",
								YesNo.No);

					} else {

						log(LogStatus.ERROR,
								"No items to display msg is showing in Interaction Section after Copy All Interaction is Selected",
								YesNo.No);
						sa.assertTrue(false,

								"No items to display msg is showing in Interaction Section after Copy All Interaction is Selected");
					}

				}

				else {
					sa.assertTrue(false, "Not Successfully Navigate to theme: " + themeName);
					log(LogStatus.FAIL, "Not Successfully Navigate to theme: " + themeName, YesNo.Yes);
				}

				if (driver.getWindowHandles().size() == 2) {
					driver.close();
					driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
				}

			} else {
				log(LogStatus.ERROR, "Record: " + themeName + " has not been Created under: " + themeTabName, YesNo.No);
				sa.assertTrue(false, "Record: " + themeName + " has not been Created under: " + themeTabName);
			}

		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityThemeTc051_ObjectsPermissionRemoveAndVerifyGridsThenReverBackPermissions(String projectName) {
		CustomObjPageBusinessLayer co = new CustomObjPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String[] userTypesToGivePermissions = { "MnA Standard User" };
		String[][] objectAndPermissionAndGivenOrGivenNot = {
				{ "Deals", "Read", PermissionType.removePermission.toString() },
				{ "Accounts", "Read", PermissionType.removePermission.toString() },
				{ "Contacts", "Read", PermissionType.removePermission.toString() },
				{ "Targets", "Read", PermissionType.removePermission.toString() },
				{ "Clips", "Read", PermissionType.removePermission.toString() } };

		String[][] objectAndPermissionAndGivenOrGivenNotRevertBack = {
				{ "Deals", "Create<break>Delete",
						PermissionType.givePermission.toString() + "<break>"
								+ PermissionType.givePermission.toString() },
				{ "Accounts", "Create<break>Delete",
						PermissionType.givePermission.toString() + "<break>"
								+ PermissionType.givePermission.toString() },
				{ "Contacts", "Create<break>Delete",
						PermissionType.givePermission.toString() + "<break>"
								+ PermissionType.givePermission.toString() },
				{ "Targets", "Create<break>Delete",
						PermissionType.givePermission.toString() + "<break>"
								+ PermissionType.givePermission.toString() },
				{ "Clips", "Create<break>Delete", PermissionType.givePermission.toString() + "<break>"
						+ PermissionType.givePermission.toString() } };

		String themeTabName = tabObj9;
		String themeName = AT_Theme2;
		List<String> expectedGridsBeforeRevertBack = new ArrayList<>(Arrays.asList(AT_Theme_Grid_19.split("<break>")));
		List<String> expectedGridsAfterRevertBack = new ArrayList<>(Arrays.asList(AT_Theme_Grid_80.split("<break>")));

		boolean flag1 = false;
		lp.CRMLogin(superAdminUserName, adminPassword);

		if (co.objectPermissionGivenOrRemove(objectAndPermissionAndGivenOrGivenNot, userTypesToGivePermissions)) {
			flag1 = true;
		}

		lp.CRMlogout();

		CommonLib.ThreadSleep(3000);

		if (flag1) {

			CommonLib.ThreadSleep(3000);

			lp.CRMLogin(crmUser1EmailID, adminPassword);

			if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {

				if (theme.themeGrids().size() > 0) {

					List<String> themeGridsText = theme.themeGrids().stream()
							.map(x -> x.getText().trim().substring(0, x.getText().trim().indexOf(" (")))
							.collect(Collectors.toList());

					Collections.sort(themeGridsText);
					Collections.sort(expectedGridsBeforeRevertBack);

					if (expectedGridsBeforeRevertBack.equals(themeGridsText)) {

						log(LogStatus.INFO, "-----Grids verified after disabling object permissions and i.e.: "
								+ themeGridsText + "-----", YesNo.No);

					} else {
						log(LogStatus.ERROR,
								"-----Grids not verified after disabling object permissions, Expected: "
										+ expectedGridsBeforeRevertBack + " , But Actual: " + themeGridsText + "-----",
								YesNo.No);
						sa.assertTrue(false,

								"-----Grids not verified after disabling object permissions, Expected: "
										+ expectedGridsBeforeRevertBack + " , But Actual: " + themeGridsText + "-----");
					}

				} else {

					log(LogStatus.ERROR, "-----Grids not showing after disabling object permissions-----", YesNo.No);
					sa.assertTrue(false,

							"-----Grids not showing after disabling object permissions-----");
				}
			} else {
				log(LogStatus.ERROR,
						"Not Able to Navigate to Theme: " + themeName
								+ " So, Not able to verify Grids After Objects Permissions Removed Other Than Theme",
						YesNo.No);
				sa.assertTrue(false,

						"Not Able to Navigate to Theme: " + themeName
								+ " So, Not able to verify Grids After Objects Permissions Removed Other Than Theme");
			}

			if (driver.getWindowHandles().size() == 2) {
				driver.close();
				driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
			}
			lp.CRMlogout();

			boolean flag2 = false;
			CommonLib.ThreadSleep(3000);
			lp.CRMLogin(superAdminUserName, adminPassword);
			log(LogStatus.INFO, "Now, Going to Revert Back the Object Permissions", YesNo.No);
			if (co.objectPermissionGivenOrRemove(objectAndPermissionAndGivenOrGivenNotRevertBack,
					userTypesToGivePermissions)) {

				log(LogStatus.INFO, "------Objects Permissions have been reverted Back------", YesNo.No);
				flag2 = true;
			} else {
				log(LogStatus.ERROR, "------Not Able to Revert Back the permissions of Objects------", YesNo.Yes);
				sa.assertTrue(false, "------Not Able to Revert Back the permissions of Objects------");
			}

			lp.CRMlogout();

			if (flag2) {
				CommonLib.ThreadSleep(3000);

				lp.CRMLogin(crmUser1EmailID, adminPassword);

				if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {

					if (theme.themeGrids().size() > 0) {

						List<String> themeGridsText = theme.themeGrids().stream()
								.map(x -> x.getText().trim().substring(0, x.getText().trim().indexOf(" (")))
								.collect(Collectors.toList());

						Collections.sort(themeGridsText);
						Collections.sort(expectedGridsAfterRevertBack);

						if (expectedGridsAfterRevertBack.equals(themeGridsText)) {

							log(LogStatus.INFO, "-----Grids verified after enabling object permissions and i.e.: "
									+ themeGridsText + "-----", YesNo.No);

						} else {
							log(LogStatus.ERROR, "-----Grids not verified after enabling object permissions, Expected: "
									+ expectedGridsAfterRevertBack + " , But Actual: " + themeGridsText + "-----",
									YesNo.No);
							sa.assertTrue(false,

									"-----Grids not verified after enabling object permissions, Expected: "
											+ expectedGridsAfterRevertBack + " , But Actual: " + themeGridsText
											+ "-----");
						}

					} else {

						log(LogStatus.ERROR, "-----Grids not showing after enabling object permissions-----", YesNo.No);
						sa.assertTrue(false,

								"-----Grids not showing after enabling object permissions-----");
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Navigate to Theme: " + themeName
							+ " So, Not able to verify Grids After Objects Permissions Revert Back", YesNo.No);
					sa.assertTrue(false,

							"Not Able to Navigate to Theme: " + themeName
									+ " So, Not able to verify Grids After Objects Permissions Revert Back");
				}

				if (driver.getWindowHandles().size() == 2) {
					driver.close();
					driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
				}
				lp.CRMlogout();
			} else {
				log(LogStatus.ERROR,
						"------As the Permission not Revert Back, So not able to verify Grids in Theme------",
						YesNo.Yes);
				sa.assertTrue(false,
						"------As the Permission not Revert Back, So not able to verify Grids in Theme------");
			}

		} else {
			log(LogStatus.ERROR, "------As the Permission not removed, So not able to verify Grids in Theme------",
					YesNo.Yes);
			sa.assertTrue(false, "------As the Permission not removed, So not able to verify Grids in Theme------");
		}

		sa.assertAll();
		CommonLib.ThreadSleep(3000);
	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc052_TeamMemberUserNameNotClickableAndCheckDuplicateTeamMemberWillNotAdd(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String themeTabName = tabObj9;

		String member = crmUser1FirstName + " " + crmUser1LastName;
		String role = AT_Theme_TeamRole_9;

		String themeName = AT_Theme2;
		String subTabName = RelatedTab.Team.toString();
		String errorMsg = BasePageErrorMessage.errorMsgTeamMember;

		String title = "";
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {
			log(LogStatus.ERROR, "-----Able to Navigate To Theme: " + themeName + "------", YesNo.No);

			if (BP.clicktabOnPage(subTabName)) {
				log(LogStatus.PASS, "Clicked on SubTab: " + subTabName, YesNo.No);

				if (theme.AddTeamMemberNameData().contains(member)) {
					Integer loopCount = 0;

					for (String AddTeamMemberNameData : theme.AddTeamMemberNameData()) {

						if (AddTeamMemberNameData.equals(member)) {

							log(LogStatus.PASS, "Team Member is Present: " + member, YesNo.No);

							WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
							int initialElementCount = driver.findElements(By.tagName("div")).size();
							wait.until(ExpectedConditions
									.elementToBeClickable(theme.AddTeamMemberNameDataElements().get(loopCount)));

							if (wait.until(ExpectedConditions.numberOfElementsToBe(By.tagName("div"),
									initialElementCount)) != null) {
								log(LogStatus.PASS, "Verified: Team Member named: " + member + " is not Clickable",
										YesNo.No);

							} else {
								log(LogStatus.ERROR, "Team Member with Name: " + member + "is Clickable", YesNo.Yes);
								sa.assertTrue(false, "Team Member with Name: " + member + "is Clickable");
							}

						}

						loopCount++;
					}

				} else {
					log(LogStatus.ERROR, "No Team Member with Name: " + member
							+ " is present there, So not able to check Member is Clickable or Not", YesNo.Yes);
					sa.assertTrue(false, "No Team Member with Name: " + member
							+ " is present there, So not able to check Member is Clickable or Not");
				}

				CommonLib.refresh(driver);

				if (BP.clicktabOnPage(subTabName)) {
					log(LogStatus.PASS, "Clicked on SubTab: " + subTabName, YesNo.No);

					List<String> beforeDuplicateTeamMemberCreateNameData = theme.AddTeamMemberNameData();

					if (theme.createTeamMember(false, themeTabName, themeName, projectName, false, themeName, member,
							role, title, true, teamMemberNavigation.Sub_Tab, false, true, errorMsg)) {
						log(LogStatus.INFO, "Error Message has been Verified for Duplicate Record for Member: " + member
								+ " with Role: " + role + " of Theme: " + themeName, YesNo.No);

						CommonLib.refresh(driver);
						if (BP.clicktabOnPage(subTabName)) {
							log(LogStatus.PASS, "Clicked on SubTab: " + subTabName, YesNo.No);

							List<String> afterDuplicateTeamMemberCreateNameData = theme.AddTeamMemberNameData();

							if (beforeDuplicateTeamMemberCreateNameData
									.equals(afterDuplicateTeamMemberCreateNameData)) {
								log(LogStatus.INFO, "Verified No Duplicate Team Member has been Created with member: "
										+ member + " and role: " + role, YesNo.No);
							} else {
								log(LogStatus.ERROR, "Duplicate Team Member has been Created with member: " + member
										+ " and role: " + role, YesNo.Yes);
								sa.assertTrue(false, "Duplicate Team Member has been Created with member: " + member
										+ " and role: " + role);
							}
						} else {

							log(LogStatus.ERROR, "Not able to click on " + subTabName + " tab", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on " + subTabName + " tab");
						}

					} else {
						log(LogStatus.ERROR, "Error Message has not been Verified for Duplicate Record for Member: "
								+ member + " with Role: " + role + " of Theme: " + themeName, YesNo.No);
						sa.assertTrue(false, "Error Message has not been Verified for Duplicate Record for Member: "
								+ member + " with Role: " + role + " of Theme: " + themeName);
					}

				} else {

					log(LogStatus.ERROR, "Not able to click on " + subTabName + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + subTabName + " tab");
				}

			} else {

				log(LogStatus.ERROR, "Not able to click on " + subTabName + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + subTabName + " tab");
			}

		} else {
			log(LogStatus.ERROR, "-----Not Able to Navigate To Theme: " + themeName + "------", YesNo.No);
			sa.assertTrue(false, "-----Not Able to Navigate To Theme: " + themeName + "------");
		}
		if (driver.getWindowHandles().size() == 2) {
			driver.close();
			driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
		}
		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc053_VerifyTeamMemberAfterInActiveUserAndThenRevertBack(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		String themeName = AT_Theme2;
		String member1 = crmUser2FirstName + " " + crmUser2LastName;
		String role1 = AT_Theme_TeamRole_9;
		String title1 = "";
		String themeTabName = tabObj9;
		boolean deactivateFlag = false;

		boolean flag = false;
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		if (theme.createTeamMember(true, themeTabName, themeName, projectName, true, themeName, member1, role1, title1,
				true, teamMemberNavigation.Action_Button, true, false, null)) {
			log(LogStatus.INFO,
					"Team Member has been Created: " + member1 + " with Role: " + role1 + " of Theme: " + themeName,
					YesNo.No);

			flag = true;

		} else {
			log(LogStatus.ERROR, "Team Member has not been Created: " + member1 + " with Role: " + role1 + " of Theme: "
					+ themeName + " So not able to InActive User2: " + member1, YesNo.No);
			sa.assertTrue(false, "Team Member has not been Created: " + member1 + " with Role: " + role1 + " of Theme: "
					+ themeName + " So not able to InActive User2: " + member1);
		}

		if (flag) {
			lp.CRMlogout();
			CommonLib.ThreadSleep(3000);
			lp.CRMLogin(superAdminUserName, adminPassword);

			if (home.clickOnSetUpLink()) {
				flag = false;
				String parentID = switchOnWindow(driver);

				if (parentID != null) {

					if (setup.userActiveOrInActive(crmUser2FirstName, crmUser2LastName, crmUser2EmailID,
							PermissionType.removePermission.toString())) {

						log(LogStatus.INFO, "Able to Deactivate User: " + member1, YesNo.No);
						deactivateFlag = true;
					} else {

						log(LogStatus.ERROR, "Not Able to Deactivate User: " + member1, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Deactivate User: " + member1);
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

		if (deactivateFlag) {

			lp.CRMlogout();
			CommonLib.ThreadSleep(3000);
			lp.CRMLogin(crmUser1EmailID, adminPassword);
			List<String> negativeResult = theme.teamMemberDataVerify(true, projectName, themeTabName, themeName,
					member1, title1, role1, true);

			if (negativeResult.contains("Team Member Name not Found: " + member1)) {
				log(LogStatus.INFO, "-----Team Member named: " + member1 + " Not Found After DeActivate the User: "
						+ member1 + "------", YesNo.No);

			} else {
				log(LogStatus.ERROR, "-----Team Member named: " + member1 + " Found After DeActivate the User: "
						+ member1 + "------", YesNo.No);
				sa.assertTrue(false, "-----Team Member named: " + member1 + " Found After DeActivate the User: "
						+ member1 + "------");
			}

			lp.CRMlogout();

			CommonLib.ThreadSleep(3000);

			lp.CRMLogin(superAdminUserName, adminPassword);

			if (home.clickOnSetUpLink()) {
				flag = false;
				String parentID = switchOnWindow(driver);

				if (parentID != null) {

					if (setup.userActiveOrInActive(crmUser2FirstName, crmUser2LastName, crmUser2EmailID,
							PermissionType.givePermission.toString())) {

						log(LogStatus.INFO, "Able to Activate User: " + member1, YesNo.No);
						deactivateFlag = true;
					} else {

						log(LogStatus.ERROR, "Not Able to Activate User: " + member1, YesNo.Yes);
						sa.assertTrue(false, "Not Able to Activate User: " + member1);
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

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc054_VerifyErrorMessageInThemeTeam(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String themeTabName = tabObj9;

		String member = null;
		String role = null;

		String themeName = AT_Theme2;
		String subTabName = RelatedTab.Team.toString();
		String errorMsg = BasePageErrorMessage.errorMsgTeamMember2;

		String title = null;
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {
			log(LogStatus.ERROR, "-----Able to Navigate To Theme: " + themeName + "------", YesNo.No);

			if (BP.clicktabOnPage(subTabName)) {
				log(LogStatus.PASS, "Clicked on SubTab: " + subTabName, YesNo.No);

				if (theme.createTeamMember(false, themeTabName, themeName, projectName, true, null, member, role, title,
						true, teamMemberNavigation.Sub_Tab, false, true, errorMsg)) {
					log(LogStatus.INFO, "Error Message has been Verified for Theme Team When No Data is Added",
							YesNo.No);

				} else {
					log(LogStatus.ERROR, "Error Message has not been Verified for Theme Team When No Data is Added",
							YesNo.No);
					sa.assertTrue(false, "Error Message has not been Verified for Theme Team When No Data is Added");
				}

			} else {

				log(LogStatus.ERROR, "Not able to click on " + subTabName + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + subTabName + " tab");
			}

		} else {
			log(LogStatus.ERROR, "-----Not Able to Navigate To Theme: " + themeName + "------", YesNo.No);
			sa.assertTrue(false, "-----Not Able to Navigate To Theme: " + themeName + "------");
		}
		if (driver.getWindowHandles().size() == 2) {
			driver.close();
			driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
		}
		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc055_EditTheThemeTeamAndVerifyData(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String themeTabName = tabObj9;

		String member = crmUser2FirstName + " " + crmUser2LastName;
		String role = AT_Theme_TeamRole_9;

		String themeName = AT_Theme2;
		String subTabName = RelatedTab.Team.toString();
		String updatedRole = AT_Theme_Grid_11;

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {
			log(LogStatus.ERROR, "-----Able to Navigate To Theme: " + themeName + "------", YesNo.No);

			if (BP.clicktabOnPage(subTabName)) {
				log(LogStatus.PASS, "Clicked on SubTab: " + subTabName, YesNo.No);

				if (CommonLib.click(driver, theme.editIconLinkOfTeamMember(member, role, 15),
						"editIconLinkOfTeamMember: " + member, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Edit Icon Link of Member: " + member + " with role: " + role,
							YesNo.No);
					if (CommonLib.click(driver, theme.teamMemberEditRolePicklistButton(15),
							"teamMemberEditRolePicklistButton: ", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Picklist Button", YesNo.No);
						if (CommonLib.click(driver, theme.teamMemberEditRolePicklistValue(updatedRole, 15),
								"teamMemberEditRolePicklistValue: " + updatedRole, action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Able to Select Role: " + updatedRole + " from PickList", YesNo.No);
							if (CommonLib.click(driver, theme.teamMemberEditRoleFooterButtonName("Save", 15),
									"teamMemberEditRoleFooterButtonName: " + "Save", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Save Button", YesNo.No);

								CommonLib.ThreadSleep(5000);
								CommonLib.refresh(driver);
								log(LogStatus.INFO, "-------Now going to Verify Data-------", YesNo.No);

								List<String> negativeResult = theme.teamMemberDataVerify(false, projectName,
										themeTabName, themeName, member, null, updatedRole, true);

								if (negativeResult.isEmpty()) {
									log(LogStatus.INFO, "-----Verified Theme " + themeName
											+ " Team Member and Role Value in Theme Subtab has been updated------",
											YesNo.No);

								} else {
									log(LogStatus.ERROR, "-----Not Verified Theme " + themeName
											+ " Team Member and Role Value in Theme Subtab has not been updated------",
											YesNo.No);
									sa.assertTrue(false, "-----Not Verified Theme " + themeName
											+ " Team Member and Role Value in Theme Subtab Subtab has not been updated------");
								}

							} else {
								log(LogStatus.ERROR, "Not Able to Click on Save Button", YesNo.No);
								sa.assertTrue(false, "Not Able to Click on Save Button");

							}

						} else {
							log(LogStatus.ERROR, "Not Able to Select Role: " + updatedRole + " from PickList",
									YesNo.No);
							sa.assertTrue(false, "Not Able to Select Role: " + updatedRole + " from PickList");

						}

					} else {
						log(LogStatus.ERROR, "Not Click on Picklist Button", YesNo.No);
						sa.assertTrue(false, "Not Click on Picklist Button");

					}

				} else {
					log(LogStatus.ERROR,
							"Not Able to Clicked on Edit Icon Link of Member: " + member + " with role: " + role,
							YesNo.No);
					sa.assertTrue(false,
							"Not Able to Clicked on Edit Icon Link of Member: " + member + " with role: " + role);

				}

			} else {

				log(LogStatus.ERROR, "Not able to click on " + subTabName + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + subTabName + " tab");
			}

		} else {
			log(LogStatus.ERROR, "-----Not Able to Navigate To Theme: " + themeName + "------", YesNo.No);
			sa.assertTrue(false, "-----Not Able to Navigate To Theme: " + themeName + "------");
		}

		if (driver.getWindowHandles().size() == 2) {
			driver.close();
			driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
		}
		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityThemeTc056_EditThemeTeamRoleNameAndVerifyDataThenRevertBack(String projectName) {

		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		FieldAndRelationshipPageBusinessLayer fr = new FieldAndRelationshipPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		String themeTabName = tabObj9;

		String member = crmUser1FirstName + " " + crmUser1LastName;
		String roleUpdated = AT_Theme_TeamRole_9 + " Updated";
		String role = AT_Theme_TeamRole_9;
		String title = null;

		String themeName = AT_Theme2;

		lp.CRMLogin(superAdminUserName, adminPassword);
		if (home.clickOnSetUpLink(environment, mode)) {
			String parentWindowID = switchOnWindow(driver);
			if (parentWindowID == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create App Page");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create App Page",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create App Page");
			}

			if (setup.searchStandardOrCustomObject(projectName, mode, object.Theme_Team.toString())) {

				if (setup.clickOnObjectFeature(projectName, mode, object.Theme_Team,
						ObjectFeatureName.FieldAndRelationShip)) {
					if (fr.editPicklistFieldLabel(projectName, "Role", roleUpdated, role)) {
						log(LogStatus.PASS, "Label Name has been Changed", YesNo.No);
						driver.close();
						driver.switchTo().window(parentWindowID);
						CommonLib.refresh(driver);

						lp.CRMlogout();
						CommonLib.ThreadSleep(5000);

						lp.CRMLogin(crmUser1EmailID, adminPassword);

						List<String> negativeResult = theme.teamMemberDataVerify(true, projectName, themeTabName,
								themeName, member, title, roleUpdated, true);

						if (negativeResult.isEmpty()) {
							log(LogStatus.INFO,
									"-----Verified Theme " + themeName
											+ " description, Team Member and Role Value in Theme Subtab------",
									YesNo.No);

						} else {
							log(LogStatus.ERROR,
									"-----Not Verified Theme " + themeName
											+ " description, Team Member and Role Value in Theme Subtab------",
									YesNo.No);
							sa.assertTrue(false, "-----Not Verified Theme " + themeName
									+ " description, Team Member and Role Value in Theme Subtab------");
						}

						lp.CRMlogout();
						CommonLib.ThreadSleep(5000);
						lp.CRMLogin(superAdminUserName, adminPassword);
						if (home.clickOnSetUpLink(environment, mode)) {
							String parentWindowID2 = switchOnWindow(driver);
							if (parentWindowID2 == null) {
								sa.assertTrue(false,
										"No new window is open after click on setup link in lighting mode so cannot create App Page");
								log(LogStatus.SKIP,
										"No new window is open after click on setup link in lighting mode so cannot create App Page",
										YesNo.Yes);
								exit("No new window is open after click on setup link in lighting mode so cannot create App Page");
							}

							if (setup.searchStandardOrCustomObject(projectName, mode, object.Theme_Team.toString())) {

								if (setup.clickOnObjectFeature(projectName, mode, object.Theme_Team,
										ObjectFeatureName.FieldAndRelationShip)) {
									if (fr.editPicklistFieldLabel(projectName, "Role", role, role)) {
										log(LogStatus.PASS, "Label Name has been Changed", YesNo.No);
										driver.close();
										driver.switchTo().window(parentWindowID2);
										CommonLib.refresh(driver);
										log(LogStatus.INFO, "-----Label Name has been Reverted Back to: " + role
												+ " of Object: " + object.Theme_Team + "------", YesNo.No);

									} else {
										log(LogStatus.ERROR, "-----Label Name has not been Reverted Back to: " + role
												+ " of Object: " + object.Theme_Team + "------", YesNo.Yes);
										sa.assertTrue(false, "-----Label Name has not been Reverted Back to: " + role
												+ " of Object: " + object.Theme_Team + "------");
									}

								} else {
									log(LogStatus.ERROR, "Not Able to Click on Object and Feature name", YesNo.Yes);
									sa.assertTrue(false, "Not Able to Click on Object and Feature name");
								}
							} else {
								log(LogStatus.ERROR, "Not Able to Search the Object: " + object.Theme_Team, YesNo.Yes);
								sa.assertTrue(false, "Not Able to Search the Object: " + object.Theme_Team);
							}

							if (driver.getWindowHandles().size() == 2) {
								driver.close();
								driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
							}

						} else {
							log(LogStatus.ERROR, "Not Able to open the setup page", YesNo.Yes);
							sa.assertTrue(false, "Not Able to open the setup page");
						}

					} else {
						log(LogStatus.ERROR, "Could not edit the Picklist", YesNo.Yes);
						sa.assertTrue(false, "Could not edit the Picklist");
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Object and Feature name", YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Object and Feature name");
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Search the Object: " + object.Theme_Team, YesNo.Yes);
				sa.assertTrue(false, "Not Able to Search the Object: " + object.Theme_Team);
			}

			if (driver.getWindowHandles().size() == 2) {
				driver.close();
				driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
			}

		} else {
			log(LogStatus.ERROR, "Not Able to open the setup page", YesNo.Yes);
			sa.assertTrue(false, "Not Able to open the setup page");
		}

		lp.CRMlogout(environment, mode);

		sa.assertAll();

	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityThemeTc057_CreateThemeTeamRoleNameAndVerifyData(String projectName) {

		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		FieldAndRelationshipPageBusinessLayer fr = new FieldAndRelationshipPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		String themeTabName = tabObj9;
		String member = crmUser2FirstName + " " + crmUser2LastName;
		String newPickListValue = "Test Picklist Value of Role";
		String title = null;
		String themeName = AT_Theme2;
		lp.CRMLogin(superAdminUserName, adminPassword);
		if (home.clickOnSetUpLink(environment, mode)) {
			String parentWindowID = switchOnWindow(driver);
			if (parentWindowID == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create App Page");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create App Page",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create App Page");
			}

			if (setup.searchStandardOrCustomObject(projectName, mode, object.Theme_Team.toString())) {

				if (setup.clickOnObjectFeature(projectName, mode, object.Theme_Team,
						ObjectFeatureName.FieldAndRelationShip)) {
					if (fr.newPicklistValueCreate(projectName, "Role", newPickListValue)) {
						log(LogStatus.PASS, "Label Name has been Changed", YesNo.No);
						driver.close();
						driver.switchTo().window(parentWindowID);
						CommonLib.refresh(driver);

						lp.CRMlogout();
						CommonLib.ThreadSleep(5000);

						lp.CRMLogin(crmUser1EmailID, adminPassword);

						if (theme.createTeamMember(true, themeTabName, themeName, projectName, false, themeName, member,
								newPickListValue, title, true, teamMemberNavigation.Action_Button, true, false, null)) {
							log(LogStatus.INFO, "Team Member has been Created: " + member + " with Role: "
									+ newPickListValue + " of Theme: " + themeName, YesNo.No);
							CommonLib.refresh(driver);
							if (theme.checkDescriptionAndTeamMember(null, member, true)) {
								log(LogStatus.INFO,
										"-----Verified Theme " + themeName + " description and Team Member Value------",
										YesNo.No);

							} else {
								log(LogStatus.ERROR, "-----Theme " + themeName
										+ " description and Team Member Value not verified------", YesNo.No);
								sa.assertTrue(false, "-----Theme " + themeName
										+ " description and Team Member Value not verified------");
							}

						} else {
							log(LogStatus.ERROR, "Team Member has not been Created: " + member + " with Role: "
									+ newPickListValue + " of Theme: " + themeName, YesNo.No);
							sa.assertTrue(false, "Team Member has not been Created: " + member + " with Role: "
									+ newPickListValue + " of Theme: " + themeName);
						}

					} else {
						log(LogStatus.ERROR, "Could not Create New Picklist Value: " + newPickListValue, YesNo.Yes);
						sa.assertTrue(false, "Could not Create New Picklist Value: " + newPickListValue);
					}

				} else {
					log(LogStatus.ERROR, "Not Able to Click on Object and Feature name", YesNo.Yes);
					sa.assertTrue(false, "Not Able to Click on Object and Feature name");
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Search the Object: " + object.Theme_Team, YesNo.Yes);
				sa.assertTrue(false, "Not Able to Search the Object: " + object.Theme_Team);
			}

			if (driver.getWindowHandles().size() == 2) {
				driver.close();
				driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
			}

		} else {
			log(LogStatus.ERROR, "Not Able to open the setup page", YesNo.Yes);
			sa.assertTrue(false, "Not Able to open the setup page");
		}

		lp.CRMlogout(environment, mode);

		sa.assertAll();

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc058_CancelAndErrorVerificationOfTeamMember(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeTabName = tabObj9;

		String user1 = crmUser1FirstName + " " + crmUser1LastName;
		String user2 = crmUser2FirstName + " " + crmUser2LastName;

		String[] members = (user1 + "<break>" + user1 + "<break>" + user2 + "<break>" + user2).split("<break>");
		String[] roles = AT_Theme_TeamRole_81.split("<break>");

		String remove2Members = (user1 + "<break>" + user1);
		String remove2Roles = AT_Theme_TeamRole_82;

		String themeName = AT_Theme81;
		String errorMsg = BasePageErrorMessage.errorMsgTeamMember3;
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		Integer loopCount = 0;
		if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {
			log(LogStatus.ERROR, "-----Able to Navigate To Theme: " + themeName + "------", YesNo.No);

			for (String member : members) {

				if (theme.createTeamMember(false, themeTabName, themeName, projectName, false, themeName, member,
						roles[loopCount], null, true, teamMemberNavigation.Action_Button, true, false, null)) {
					log(LogStatus.INFO, "Team Member has been Created: " + member + " with Role: " + roles[loopCount]
							+ " of Theme: " + themeName, YesNo.No);

				} else {
					log(LogStatus.ERROR, "Team Member has not been Created: " + member + " with Role: "
							+ roles[loopCount] + " of Theme: " + themeName, YesNo.No);
					sa.assertTrue(false, "Team Member has not been Created: " + member + " with Role: "
							+ roles[loopCount] + " of Theme: " + themeName);
				}

				loopCount++;
			}

		} else {
			log(LogStatus.ERROR, "-----Not Able to Navigate To Theme: " + themeName + "------", YesNo.No);
			sa.assertTrue(false, "-----Not Able to Navigate To Theme: " + themeName + "------");
		}

		CommonLib.refresh(driver);

		if (theme.removeTeamMember(false, themeTabName, themeName, projectName, false, remove2Members, remove2Roles,
				false, false, null)) {
			log(LogStatus.INFO, "Verified Cancel Functionality for Theme: " + themeName, YesNo.No);

		} else {
			log(LogStatus.ERROR, "Not Verified Cancel Functionality for Theme: " + themeName, YesNo.No);
			sa.assertTrue(false, "Not Verified Cancel Functionality for Theme: " + themeName);
		}

		CommonLib.refresh(driver);
		if (theme.removeTeamMember(false, themeTabName, themeName, projectName, true, null, remove2Roles, true, false,
				errorMsg)) {
			log(LogStatus.INFO, "Error Msg Has Been Verified for Theme " + themeName, YesNo.No);

		} else {
			log(LogStatus.ERROR, "Error Msg Has not Been Verified for Theme " + themeName, YesNo.No);
			sa.assertTrue(false, "Error Msg Has not Been Verified for Theme " + themeName);
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc059_Remove2TeamMembersAndAllFunctionality(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeTabName = tabObj9;

		String user1 = crmUser1FirstName + " " + crmUser1LastName;

		String remove2Members = (user1 + "<break>" + user1);
		String remove2Roles = AT_Theme_TeamRole_82;

		String themeName = AT_Theme81;
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		CommonLib.refresh(driver);

		if (theme.removeTeamMember(true, themeTabName, themeName, projectName, false, remove2Members, remove2Roles,
				true, false, null)) {
			log(LogStatus.INFO, "Verified 2 Team Member has Been Removed for Theme " + themeName, YesNo.No);

			for (String member : remove2Members.split("<break>")) {
				if (!theme.checkDescriptionAndTeamMember(null, member, false)) {
					log(LogStatus.INFO, "-----Verified Theme " + themeName + " description and Team Member Value------",
							YesNo.No);

				} else {
					log(LogStatus.ERROR,
							"-----Theme " + themeName + " description and Team Member Value not verified------",
							YesNo.No);
					sa.assertTrue(false,
							"-----Theme " + themeName + " description and Team Member Value not verified------");
				}
			}

		} else {
			log(LogStatus.ERROR, "2 Team Member has not Been Removed for Theme " + themeName, YesNo.No);
			sa.assertTrue(false, "2 Team Member has not Been Removed for Theme " + themeName);
		}

		CommonLib.refresh(driver);
		if (theme.removeTeamMember(false, themeTabName, themeName, projectName, false, remove2Members, remove2Roles,
				true, true, null)) {
			log(LogStatus.INFO, "All Team Member has Been Removed for Theme " + themeName, YesNo.No);

			if (theme.checkDescriptionAndTeamMember(null, AT_Theme_TeamMember_7, true)) {
				log(LogStatus.INFO, "-----Verified Theme " + themeName + " description and Team Member Value------",
						YesNo.No);

			} else {
				log(LogStatus.ERROR,
						"-----Theme " + themeName + " description and Team Member Value not verified------", YesNo.No);
				sa.assertTrue(false,
						"-----Theme " + themeName + " description and Team Member Value not verified------");
			}

		} else {
			log(LogStatus.ERROR, "All Team Member has not Been Removed for Theme " + themeName, YesNo.No);
			sa.assertTrue(false, "All Team Member has not Been Removed for Theme " + themeName);
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc060_UpdateCustomMetaDataForFieldsOfThemeUnderTaggedSection(String projectName) {

		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String parentWindow = null;

		String[] metaDataName = { AT_MetaDataName1, AT_MetaDataName1, AT_MetaDataName1 };
		String[] metaDataValue = { AT_MetaDataNewValue1, AT_MetaDataNewValue2, AT_MetaDataNewValue3 };
		String[] customFieldsRecordsInPlaceOfThemeName = { AT_Theme_Data_87, AT_Theme_Data_88, AT_Theme_Data_89 };
		List<String> customFieldsRecordsInPlaceOfThemeNameList = Arrays.asList(customFieldsRecordsInPlaceOfThemeName);

		String themeName = AT_Theme81;
		String themeTabName = tabObj9;

		String[][] addToThemeData = { { AT_Theme_Grid_13, tabObj1, AT_Theme_AddToTheme_83 },
				{ AT_Theme_Grid_15, tabObj2, AT_Theme_AddToTheme_84 },
				{ AT_Theme_Grid_16, tabObj4, AT_Theme_AddToTheme_85 },
				{ AT_Theme_Grid_18, tabObj3, AT_Theme_AddToTheme_86 } };

		String contactRecordTypeList = AT_RecordTypes_1;
		String contactRecordTypeArray[] = contactRecordTypeList.split(breakSP, -1);

		String targetRecordTypeList = AT_RecordTypes_3;
		String targetRecordTypeArray[] = targetRecordTypeList.split(breakSP, -1);

		String[][] recordTypeFields = { { recordTypeLabel.Active.toString(), "Checked" } };
		String[][] recordTypeFieldsInActive = { { recordTypeLabel.Active.toString(), "Not Checked" } };
		String profileName = "MnA Standard User";

		lp.CRMLogin(superAdminUserName, adminPassword);

		if (BP.editOfRecordType(projectName, contactRecordTypeArray, recordTypeFields, object.Contact, false,
				profileName)) {
			log(LogStatus.INFO, "----Record Types Gets Active for Object: " + object.Contact + "----", YesNo.No);

		} else {
			log(LogStatus.ERROR, "----Record Types not Gets Active for Object: " + object.Contact + "----", YesNo.No);
			sa.assertTrue(false, "----Record Types not Gets Active for Object: " + object.Contact + "----");
		}

		if (BP.editOfRecordType(projectName, targetRecordTypeArray, recordTypeFields, object.Target, false,
				profileName)) {
			log(LogStatus.INFO, "----Record Types Gets Active for Object: " + object.Target + "----", YesNo.No);

		} else {
			log(LogStatus.ERROR, "----Record Types not Gets Active for Object: " + object.Target + "----", YesNo.No);
			sa.assertTrue(false, "----Record Types not Gets Active for Object: " + object.Target + "----");
		}

		lp.CRMlogout();
		CommonLib.ThreadSleep(5000);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		for (String[] addToTheme : addToThemeData) {
			if (BP.navigateToRecordAndClickOnSubTab(projectName, addToTheme[1], addToTheme[2], null)) {
				log(LogStatus.INFO, "Able to Open the Record: " + addToTheme[2], YesNo.No);

				if (theme.createAddToTheme(false, false, true, true, false, PageName.ThemesPage, projectName,
						themeTabName, themeName, addToTheme[0], null, themeName, null, null, false, false,
						false, null)) {
					log(LogStatus.PASS, "-----Add To Theme Created for Theme: " + themeName + " for Object: "
							+ addToTheme[1] + " and for Record: " + addToTheme[2] + " -----", YesNo.No);
				} else {
					sa.assertTrue(false, "-----Add To Theme Not Created for Theme: " + themeName + " for Object: "
							+ addToTheme[1] + " and for Record: " + addToTheme[2] + " -----");
					log(LogStatus.FAIL, "-----Add To Theme Not Created for Theme: " + themeName + " for Object: "
							+ addToTheme[1] + " and for Record: " + addToTheme[2] + " -----", YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Not able to Open the Record: " + addToTheme[2], YesNo.No);
				sa.assertTrue(false, "Not able to Open the Record: " + addToTheme[2]);
			}
		}

		lp.CRMlogout();

		ThreadSleep(3000);
		if (metaDataName.length == metaDataValue.length) {
			for (int i = 0; i < metaDataName.length; i++) {

				lp.CRMLogin(superAdminUserName, adminPassword);
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

					if (setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), metaDataName[i],
							metaDataValue[i], 10)) {
						log(LogStatus.INFO, "Changed the value of " + metaDataName[i] + " for Acuity Setting",
								YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"Not able to change the value of " + metaDataName[i] + " for Acuity Setting", YesNo.No);
						sa.assertTrue(false,
								"Not able to changed the value of " + metaDataName[i] + " for Acuity Setting");
					}
					ThreadSleep(5000);

					switchToDefaultContent(driver);
					driver.close();
					driver.switchTo().window(parentWindow);
				} else {
					log(LogStatus.ERROR, "Not able to click on setup link so cannot change value", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on setup link so cannot change value");
				}

				lp.CRMlogout();
				CommonLib.ThreadSleep(5000);
				lp.CRMLogin(crmUser1EmailID, adminPassword);

				for (String[] addToThemeDataSingleArray : addToThemeData) {
					if (BP.navigateToRecordAndClickOnSubTab(projectName, addToThemeDataSingleArray[1],
							addToThemeDataSingleArray[2], null)) {
						log(LogStatus.INFO, "Able to Open the Record: " + addToThemeDataSingleArray[2], YesNo.No);
						List<String> negativeResult = theme
								.verifyRedirectionOnClickOnFieldRelatedToThemeAndSortingInTaggedSection(false,
										TaggedName.Themes, tabObj9,
										Arrays.asList(customFieldsRecordsInPlaceOfThemeNameList.get(i)),
										Arrays.asList(themeName), false);
						if (negativeResult.isEmpty()) {
							log(LogStatus.PASS,
									"-----Verified Theme records Redirection to Specific Page Records in case of Object: "
											+ addToThemeDataSingleArray[1] + ", for Record: "
											+ addToThemeDataSingleArray[2] + " and for Record: "
											+ customFieldsRecordsInPlaceOfThemeNameList.get(i)
											+ " under Tagged Section-----",
									YesNo.No);

						} else {
							sa.assertTrue(false,
									"-----Not Verified Theme records Redirection to Specific Page Records in case of Object: "
											+ addToThemeDataSingleArray[1] + ", for Record: "
											+ addToThemeDataSingleArray[2] + " and for Record: "
											+ customFieldsRecordsInPlaceOfThemeNameList.get(i)
											+ " under Tagged Section-----");
							log(LogStatus.FAIL,
									"-----Not Verified Theme records Redirection to Specific Page Records in case of Object: "
											+ addToThemeDataSingleArray[1] + ", for Record: "
											+ addToThemeDataSingleArray[2] + " and for Record: "
											+ customFieldsRecordsInPlaceOfThemeNameList.get(i)
											+ " under Tagged Section-----",
									YesNo.Yes);
						}

					} else {
						log(LogStatus.ERROR, "Not able to Open the Record: " + addToThemeDataSingleArray[2], YesNo.No);
						sa.assertTrue(false, "Not able to Open the Record: " + addToThemeDataSingleArray[2]);
					}
				}

				lp.CRMlogout();

			}
		} else {
			log(LogStatus.ERROR, "The size of metadata name and metadata value are not equal", YesNo.No);
			sa.assertTrue(false, "The size of metadata name and metadata value are not equal");
		}

		lp.CRMLogin(superAdminUserName, adminPassword);

		if (BP.editOfRecordType(projectName, contactRecordTypeArray, recordTypeFieldsInActive, object.Contact, true,
				profileName)) {
			log(LogStatus.INFO, "----Record Types Gets InActive for Object: " + object.Contact + "----", YesNo.No);

		} else {
			log(LogStatus.ERROR, "----Record Types not Gets InActive for Object: " + object.Contact + "----", YesNo.No);
			sa.assertTrue(false, "----Record Types not Gets InActive for Object: " + object.Contact + "----");
		}

		if (BP.editOfRecordType(projectName, targetRecordTypeArray, recordTypeFieldsInActive, object.Target, true,
				profileName)) {
			log(LogStatus.INFO, "----Record Types Gets InActive for Object: " + object.Target + "----", YesNo.No);

		} else {
			log(LogStatus.ERROR, "----Record Types not Gets InActive for Object: " + object.Target + "----", YesNo.No);
			sa.assertTrue(false, "----Record Types not Gets InActive for Object: " + object.Target + "----");
		}

		lp.CRMlogout();

		CommonLib.ThreadSleep(5000);
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc061_UpdateCustomMetaDataForFieldsOfThemeInCaseOfFirm(String projectName) {

		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		String parentWindow = null;
		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		String[][] metaDataNames = { { AT_MetaDataName4, AT_MetaDataName5, AT_MetaDataName6 },
				{ AT_MetaDataName5, AT_MetaDataName6, AT_MetaDataName4 },
				{ AT_MetaDataName6, AT_MetaDataName4, AT_MetaDataName5 } };
		String[][] metaDataValues = { { AT_MetaDataNewValue4, AT_MetaDataNewValue5, AT_MetaDataNewValue6 },
				{ AT_MetaDataNewValue4, AT_MetaDataNewValue5, AT_MetaDataNewValue6 },
				{ AT_MetaDataNewValue4, AT_MetaDataNewValue5, AT_MetaDataNewValue6 } };

		String[][] dataVerification = { { AT_Theme_Data_90, AT_Theme_Data_91, AT_Theme_Data_92 },
				{ AT_Theme_Data_90, AT_Theme_Data_91, AT_Theme_Data_92 },
				{ AT_Theme_Data_90, AT_Theme_Data_91, AT_Theme_Data_92 } };

		String[][] revertBackMetaDataNames = { { AT_MetaDataName4, AT_MetaDataName5, AT_MetaDataName6 } };
		String[][] revertBackMetaDataValues = { { AT_MetaDataOldValue4, AT_MetaDataOldValue5, AT_MetaDataOldValue6 } };

		Integer loopCount = 0;
		for (String[] metaDataName : metaDataNames) {

			Integer status = 0;
			Integer mainLoopCount = 0;
			lp.CRMLogin(superAdminUserName, adminPassword);
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

				if (metaDataName.length == metaDataValues[loopCount].length) {

					for (int i = 0; i < metaDataName.length; i++) {
						if (setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(),
								metaDataName[i], metaDataValues[loopCount][i], 10)) {
							log(LogStatus.INFO, "Changed the value of " + metaDataName[i] + " for Acuity Setting",
									YesNo.No);
							status++;
						} else {
							log(LogStatus.ERROR, "Not able to change the value of " + metaDataName[i]
									+ " for Acuity Setting, So Not Able to Verify Grids", YesNo.No);
							sa.assertTrue(false, "Not able to change the value of " + metaDataName[i]
									+ " for Acuity Setting, So Not Able to Verify Grids");
						}
						ThreadSleep(5000);
						mainLoopCount++;
					}

				} else {
					log(LogStatus.ERROR, "The size of metadata name and metadata value are not equal", YesNo.No);
					sa.assertTrue(false, "The size of metadata name and metadata value are not equal");
				}
				switchToDefaultContent(driver);
				driver.close();
				driver.switchTo().window(parentWindow);
				lp.CRMlogout();

				if (mainLoopCount.equals(status) && !status.equals(0)) {

					lp.CRMLogin(crmUser1EmailID, adminPassword);

					if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {
						log(LogStatus.PASS, "Successfully Navigate to theme: " + themeName, YesNo.No);
						for (int i = 0; i < dataVerification[loopCount].length; i++) {

							String[] data = dataVerification[loopCount][i].split("<Section>", -1);

							if (theme.verifyRecordInGrid(data[0], data[1], data[2].split("<break>", -1))) {
								log(LogStatus.PASS,
										"-----Verified Grid Data for Theme: " + themeName + " for Grid: " + data[0]
												+ " , for Grid: " + data[1] + " and for Record: " + data[2] + " -----",
										YesNo.No);
							} else {
								sa.assertTrue(false,
										"-----Not Verified Grid Data for Theme: " + themeName + " for Grid: " + data[0]
												+ " , for Grid: " + data[1] + " and for Record: " + data[2] + " -----");
								log(LogStatus.FAIL,
										"-----Not Verified Grid Data for Theme: " + themeName + " for Grid: " + data[0]
												+ " , for Grid: " + data[1] + " and for Record: " + data[2] + " -----",
										YesNo.Yes);
							}

						}

						driver.close();
						driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
						lp.CRMlogout();
					}

					else {
						sa.assertTrue(false, "Not Successfully Navigate to theme: " + themeName);
						log(LogStatus.FAIL, "Not Successfully Navigate to theme: " + themeName, YesNo.Yes);
					}

				}
			} else {
				log(LogStatus.ERROR, "Not able to click on setup link so cannot change value", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link so cannot change value");
			}

			loopCount++;
		}

		Integer loopCount2 = 0;
		for (String[] revertMetaDataName : revertBackMetaDataNames) {

			lp.CRMLogin(superAdminUserName, adminPassword);
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

				if (revertMetaDataName.length == revertBackMetaDataValues[loopCount2].length) {

					for (int i = 0; i < revertMetaDataName.length; i++) {
						if (setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(),
								revertMetaDataName[i], revertBackMetaDataValues[loopCount2][i], 10)) {
							log(LogStatus.INFO, "Changed the value of " + revertMetaDataName[i] + " for Acuity Setting",
									YesNo.No);

						} else {
							log(LogStatus.ERROR, "Not able to change the value of " + revertMetaDataName[i]
									+ " for Acuity Setting, So Not Able to Verify Grids", YesNo.No);
							sa.assertTrue(false, "Not able to change the value of " + revertMetaDataName[i]
									+ " for Acuity Setting, So Not Able to Verify Grids");
						}
						ThreadSleep(5000);

					}

				} else {
					log(LogStatus.ERROR, "The size of metadata name and metadata value are not equal", YesNo.No);
					sa.assertTrue(false, "The size of metadata name and metadata value are not equal");
				}
				switchToDefaultContent(driver);
				driver.close();
				driver.switchTo().window(parentWindow);

			} else {
				log(LogStatus.ERROR, "Not able to click on setup link so cannot change value", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link so cannot change value");
			}

		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc062_UpdateCustomMetaDataForFieldsOfThemeInCaseOfContact(String projectName) {

		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		String parentWindow = null;
		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		String[][] metaDataNames = { { AT_MetaDataName7, AT_MetaDataName8, AT_MetaDataName9 },
				{ AT_MetaDataName8, AT_MetaDataName9, AT_MetaDataName7 },
				{ AT_MetaDataName9, AT_MetaDataName7, AT_MetaDataName8 } };
		String[][] metaDataValues = { { AT_MetaDataNewValue7, AT_MetaDataNewValue8, AT_MetaDataNewValue9 },
				{ AT_MetaDataNewValue7, AT_MetaDataNewValue8, AT_MetaDataNewValue9 },
				{ AT_MetaDataNewValue7, AT_MetaDataNewValue8, AT_MetaDataNewValue9 } };

		String[][] dataVerification = { { AT_Theme_Data_93, AT_Theme_Data_94, AT_Theme_Data_95 },
				{ AT_Theme_Data_93, AT_Theme_Data_94, AT_Theme_Data_95 },
				{ AT_Theme_Data_93, AT_Theme_Data_94, AT_Theme_Data_95 } };

		String[][] revertBackMetaDataNames = { { AT_MetaDataName7, AT_MetaDataName8, AT_MetaDataName9 } };
		String[][] revertBackMetaDataValues = { { AT_MetaDataOldValue7, AT_MetaDataOldValue8, AT_MetaDataOldValue9 } };

		Integer loopCount = 0;
		for (String[] metaDataName : metaDataNames) {

			Integer status = 0;
			Integer mainLoopCount = 0;
			lp.CRMLogin(superAdminUserName, adminPassword);
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

				if (metaDataName.length == metaDataValues[loopCount].length) {

					for (int i = 0; i < metaDataName.length; i++) {
						if (setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(),
								metaDataName[i], metaDataValues[loopCount][i], 10)) {
							log(LogStatus.INFO, "Changed the value of " + metaDataName[i] + " for Acuity Setting",
									YesNo.No);
							status++;
						} else {
							log(LogStatus.ERROR, "Not able to change the value of " + metaDataName[i]
									+ " for Acuity Setting, So Not Able to Verify Grids", YesNo.No);
							sa.assertTrue(false, "Not able to change the value of " + metaDataName[i]
									+ " for Acuity Setting, So Not Able to Verify Grids");
						}
						ThreadSleep(5000);
						mainLoopCount++;
					}

				} else {
					log(LogStatus.ERROR, "The size of metadata name and metadata value are not equal", YesNo.No);
					sa.assertTrue(false, "The size of metadata name and metadata value are not equal");
				}
				switchToDefaultContent(driver);
				driver.close();
				driver.switchTo().window(parentWindow);
				lp.CRMlogout();

				if (mainLoopCount.equals(status) && !status.equals(0)) {

					lp.CRMLogin(crmUser1EmailID, adminPassword);

					if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {
						log(LogStatus.PASS, "Successfully Navigate to theme: " + themeName, YesNo.No);
						for (int i = 0; i < dataVerification[loopCount].length; i++) {

							String[] data = dataVerification[loopCount][i].split("<Section>", -1);

							if (theme.verifyRecordInGrid(data[0], data[1], data[2].split("<break>", -1))) {
								log(LogStatus.PASS,
										"-----Verified Grid Data for Theme: " + themeName + " for Grid: " + data[0]
												+ " , for Grid: " + data[1] + " and for Record: " + data[2] + " -----",
										YesNo.No);
							} else {
								sa.assertTrue(false,
										"-----Not Verified Grid Data for Theme: " + themeName + " for Grid: " + data[0]
												+ " , for Grid: " + data[1] + " and for Record: " + data[2] + " -----");
								log(LogStatus.FAIL,
										"-----Not Verified Grid Data for Theme: " + themeName + " for Grid: " + data[0]
												+ " , for Grid: " + data[1] + " and for Record: " + data[2] + " -----",
										YesNo.Yes);
							}

						}

						driver.close();
						driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
						lp.CRMlogout();
					}

					else {
						sa.assertTrue(false, "Not Successfully Navigate to theme: " + themeName);
						log(LogStatus.FAIL, "Not Successfully Navigate to theme: " + themeName, YesNo.Yes);
					}

				}
			} else {
				log(LogStatus.ERROR, "Not able to click on setup link so cannot change value", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link so cannot change value");
			}

			loopCount++;
		}

		Integer loopCount2 = 0;
		for (String[] revertMetaDataName : revertBackMetaDataNames) {

			lp.CRMLogin(superAdminUserName, adminPassword);
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

				if (revertMetaDataName.length == revertBackMetaDataValues[loopCount2].length) {

					for (int i = 0; i < revertMetaDataName.length; i++) {
						if (setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(),
								revertMetaDataName[i], revertBackMetaDataValues[loopCount2][i], 10)) {
							log(LogStatus.INFO, "Changed the value of " + revertMetaDataName[i] + " for Acuity Setting",
									YesNo.No);

						} else {
							log(LogStatus.ERROR, "Not able to change the value of " + revertMetaDataName[i]
									+ " for Acuity Setting, So Not Able to Verify Grids", YesNo.No);
							sa.assertTrue(false, "Not able to change the value of " + revertMetaDataName[i]
									+ " for Acuity Setting, So Not Able to Verify Grids");
						}
						ThreadSleep(5000);

					}

				} else {
					log(LogStatus.ERROR, "The size of metadata name and metadata value are not equal", YesNo.No);
					sa.assertTrue(false, "The size of metadata name and metadata value are not equal");
				}
				switchToDefaultContent(driver);
				driver.close();
				driver.switchTo().window(parentWindow);

			} else {
				log(LogStatus.ERROR, "Not able to click on setup link so cannot change value", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link so cannot change value");
			}

		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc063_UpdateCustomMetaDataForFieldsOfThemeInCaseOfDeal(String projectName) {

		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		String parentWindow = null;
		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		String[][] metaDataNames = {
				{ AT_MetaDataName10, AT_MetaDataName11, AT_MetaDataName12, AT_MetaDataName13, AT_MetaDataName14 },
				{ AT_MetaDataName11, AT_MetaDataName12, AT_MetaDataName13, AT_MetaDataName14, AT_MetaDataName10 },
				{ AT_MetaDataName12, AT_MetaDataName13, AT_MetaDataName14, AT_MetaDataName10, AT_MetaDataName11 },
				{ AT_MetaDataName13, AT_MetaDataName14, AT_MetaDataName10, AT_MetaDataName11, AT_MetaDataName12 },
				{ AT_MetaDataName14, AT_MetaDataName10, AT_MetaDataName11, AT_MetaDataName12, AT_MetaDataName13 } };
		String[][] metaDataValues = {
				{ AT_MetaDataNewValue10, AT_MetaDataNewValue11, AT_MetaDataNewValue12, AT_MetaDataNewValue13,
						AT_MetaDataNewValue14 },
				{ AT_MetaDataNewValue10, AT_MetaDataNewValue11, AT_MetaDataNewValue12, AT_MetaDataNewValue13,
						AT_MetaDataNewValue14 },
				{ AT_MetaDataNewValue10, AT_MetaDataNewValue11, AT_MetaDataNewValue12, AT_MetaDataNewValue13,
						AT_MetaDataNewValue14 },
				{ AT_MetaDataNewValue10, AT_MetaDataNewValue11, AT_MetaDataNewValue12, AT_MetaDataNewValue13,
						AT_MetaDataNewValue14 },
				{ AT_MetaDataNewValue10, AT_MetaDataNewValue11, AT_MetaDataNewValue12, AT_MetaDataNewValue13,
						AT_MetaDataNewValue14 } };

		String[][] dataVerification = {
				{ AT_Theme_Data_96, AT_Theme_Data_97, AT_Theme_Data_98, AT_Theme_Data_99, AT_Theme_Data_100 },
				{ AT_Theme_Data_96, AT_Theme_Data_97, AT_Theme_Data_98, AT_Theme_Data_99, AT_Theme_Data_100 },
				{ AT_Theme_Data_96, AT_Theme_Data_97, AT_Theme_Data_98, AT_Theme_Data_99, AT_Theme_Data_100 },
				{ AT_Theme_Data_96, AT_Theme_Data_97, AT_Theme_Data_98, AT_Theme_Data_99, AT_Theme_Data_100 },
				{ AT_Theme_Data_96, AT_Theme_Data_97, AT_Theme_Data_98, AT_Theme_Data_99, AT_Theme_Data_100 } };

		String[][] revertBackMetaDataNames = {
				{ AT_MetaDataName10, AT_MetaDataName11, AT_MetaDataName12, AT_MetaDataName13, AT_MetaDataName14 } };
		String[][] revertBackMetaDataValues = { { AT_MetaDataOldValue10, AT_MetaDataOldValue11, AT_MetaDataOldValue12,
				AT_MetaDataOldValue13, AT_MetaDataOldValue14 } };

		Integer loopCount = 0;
		for (String[] metaDataName : metaDataNames) {

			Integer status = 0;
			Integer mainLoopCount = 0;
			lp.CRMLogin(superAdminUserName, adminPassword);
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

				if (metaDataName.length == metaDataValues[loopCount].length) {

					for (int i = 0; i < metaDataName.length; i++) {
						if (setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(),
								metaDataName[i], metaDataValues[loopCount][i], 10)) {
							log(LogStatus.INFO, "Changed the value of " + metaDataName[i] + " for Acuity Setting",
									YesNo.No);
							status++;
						} else {
							log(LogStatus.ERROR, "Not able to change the value of " + metaDataName[i]
									+ " for Acuity Setting, So Not Able to Verify Grids", YesNo.No);
							sa.assertTrue(false, "Not able to change the value of " + metaDataName[i]
									+ " for Acuity Setting, So Not Able to Verify Grids");
						}
						ThreadSleep(5000);
						mainLoopCount++;
					}

				} else {
					log(LogStatus.ERROR, "The size of metadata name and metadata value are not equal", YesNo.No);
					sa.assertTrue(false, "The size of metadata name and metadata value are not equal");
				}
				switchToDefaultContent(driver);
				driver.close();
				driver.switchTo().window(parentWindow);
				lp.CRMlogout();

				if (mainLoopCount.equals(status) && !status.equals(0)) {

					lp.CRMLogin(crmUser1EmailID, adminPassword);

					if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {
						log(LogStatus.PASS, "Successfully Navigate to theme: " + themeName, YesNo.No);
						for (int i = 0; i < dataVerification[loopCount].length; i++) {

							String[] data = dataVerification[loopCount][i].split("<Section>", -1);

							if (theme.verifyRecordInGrid(data[0], data[1], data[2].split("<break>", -1))) {
								log(LogStatus.PASS,
										"-----Verified Grid Data for Theme: " + themeName + " for Grid: " + data[0]
												+ " , for Grid: " + data[1] + " and for Record: " + data[2] + " -----",
										YesNo.No);
							} else {
								sa.assertTrue(false,
										"-----Not Verified Grid Data for Theme: " + themeName + " for Grid: " + data[0]
												+ " , for Grid: " + data[1] + " and for Record: " + data[2] + " -----");
								log(LogStatus.FAIL,
										"-----Not Verified Grid Data for Theme: " + themeName + " for Grid: " + data[0]
												+ " , for Grid: " + data[1] + " and for Record: " + data[2] + " -----",
										YesNo.Yes);
							}

						}

						driver.close();
						driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
						lp.CRMlogout();
					}

					else {
						sa.assertTrue(false, "Not Successfully Navigate to theme: " + themeName);
						log(LogStatus.FAIL, "Not Successfully Navigate to theme: " + themeName, YesNo.Yes);
					}

				}
			} else {
				log(LogStatus.ERROR, "Not able to click on setup link so cannot change value", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link so cannot change value");
			}

			loopCount++;
		}

		Integer loopCount2 = 0;
		for (String[] revertMetaDataName : revertBackMetaDataNames) {

			lp.CRMLogin(superAdminUserName, adminPassword);
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

				if (revertMetaDataName.length == revertBackMetaDataValues[loopCount2].length) {

					for (int i = 0; i < revertMetaDataName.length; i++) {
						if (setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(),
								revertMetaDataName[i], revertBackMetaDataValues[loopCount2][i], 10)) {
							log(LogStatus.INFO, "Changed the value of " + revertMetaDataName[i] + " for Acuity Setting",
									YesNo.No);

						} else {
							log(LogStatus.ERROR, "Not able to change the value of " + revertMetaDataName[i]
									+ " for Acuity Setting, So Not Able to Verify Grids", YesNo.No);
							sa.assertTrue(false, "Not able to change the value of " + revertMetaDataName[i]
									+ " for Acuity Setting, So Not Able to Verify Grids");
						}
						ThreadSleep(5000);

					}

				} else {
					log(LogStatus.ERROR, "The size of metadata name and metadata value are not equal", YesNo.No);
					sa.assertTrue(false, "The size of metadata name and metadata value are not equal");
				}
				switchToDefaultContent(driver);
				driver.close();
				driver.switchTo().window(parentWindow);

			} else {
				log(LogStatus.ERROR, "Not able to click on setup link so cannot change value", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link so cannot change value");
			}

		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc064_UpdateCustomMetaDataForFieldsOfThemeInCaseOfTarget(String projectName) {

		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		String parentWindow = null;
		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		String[][] metaDataNames = {
				{ AT_MetaDataName15, AT_MetaDataName16, AT_MetaDataName17, AT_MetaDataName18, AT_MetaDataName19 },
				{ AT_MetaDataName16, AT_MetaDataName17, AT_MetaDataName18, AT_MetaDataName19, AT_MetaDataName15 },
				{ AT_MetaDataName17, AT_MetaDataName18, AT_MetaDataName19, AT_MetaDataName15, AT_MetaDataName16 },
				{ AT_MetaDataName18, AT_MetaDataName19, AT_MetaDataName15, AT_MetaDataName16, AT_MetaDataName17 },
				{ AT_MetaDataName19, AT_MetaDataName15, AT_MetaDataName16, AT_MetaDataName17, AT_MetaDataName18 } };
		String[][] metaDataValues = {
				{ AT_MetaDataNewValue15, AT_MetaDataNewValue16, AT_MetaDataNewValue17, AT_MetaDataNewValue18,
						AT_MetaDataNewValue19 },
				{ AT_MetaDataNewValue15, AT_MetaDataNewValue16, AT_MetaDataNewValue17, AT_MetaDataNewValue18,
						AT_MetaDataNewValue19 },
				{ AT_MetaDataNewValue15, AT_MetaDataNewValue16, AT_MetaDataNewValue17, AT_MetaDataNewValue18,
						AT_MetaDataNewValue19 },
				{ AT_MetaDataNewValue15, AT_MetaDataNewValue16, AT_MetaDataNewValue17, AT_MetaDataNewValue18,
						AT_MetaDataNewValue19 },
				{ AT_MetaDataNewValue15, AT_MetaDataNewValue16, AT_MetaDataNewValue17, AT_MetaDataNewValue18,
						AT_MetaDataNewValue19 } };

		String[][] dataVerification = {
				{ AT_Theme_Data_101, AT_Theme_Data_102, AT_Theme_Data_103, AT_Theme_Data_104, AT_Theme_Data_105 },
				{ AT_Theme_Data_101, AT_Theme_Data_102, AT_Theme_Data_103, AT_Theme_Data_104, AT_Theme_Data_105 },
				{ AT_Theme_Data_101, AT_Theme_Data_102, AT_Theme_Data_103, AT_Theme_Data_104, AT_Theme_Data_105 },
				{ AT_Theme_Data_101, AT_Theme_Data_102, AT_Theme_Data_103, AT_Theme_Data_104, AT_Theme_Data_105 },
				{ AT_Theme_Data_101, AT_Theme_Data_102, AT_Theme_Data_103, AT_Theme_Data_104, AT_Theme_Data_105 } };

		String[][] revertBackMetaDataNames = {
				{ AT_MetaDataName15, AT_MetaDataName16, AT_MetaDataName17, AT_MetaDataName18, AT_MetaDataName19 } };
		String[][] revertBackMetaDataValues = { { AT_MetaDataOldValue15, AT_MetaDataOldValue16, AT_MetaDataOldValue17,
				AT_MetaDataOldValue18, AT_MetaDataOldValue19 } };

		Integer loopCount = 0;
		for (String[] metaDataName : metaDataNames) {

			Integer status = 0;
			Integer mainLoopCount = 0;
			lp.CRMLogin(superAdminUserName, adminPassword);
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

				if (metaDataName.length == metaDataValues[loopCount].length) {

					for (int i = 0; i < metaDataName.length; i++) {
						if (setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(),
								metaDataName[i], metaDataValues[loopCount][i], 10)) {
							log(LogStatus.INFO, "Changed the value of " + metaDataName[i] + " for Acuity Setting",
									YesNo.No);
							status++;
						} else {
							log(LogStatus.ERROR, "Not able to change the value of " + metaDataName[i]
									+ " for Acuity Setting, So Not Able to Verify Grids", YesNo.No);
							sa.assertTrue(false, "Not able to change the value of " + metaDataName[i]
									+ " for Acuity Setting, So Not Able to Verify Grids");
						}
						ThreadSleep(5000);
						mainLoopCount++;
					}

				} else {
					log(LogStatus.ERROR, "The size of metadata name and metadata value are not equal", YesNo.No);
					sa.assertTrue(false, "The size of metadata name and metadata value are not equal");
				}
				switchToDefaultContent(driver);
				driver.close();
				driver.switchTo().window(parentWindow);
				lp.CRMlogout();

				if (mainLoopCount.equals(status) && !status.equals(0)) {

					lp.CRMLogin(crmUser1EmailID, adminPassword);

					if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {
						log(LogStatus.PASS, "Successfully Navigate to theme: " + themeName, YesNo.No);
						for (int i = 0; i < dataVerification[loopCount].length; i++) {

							String[] data = dataVerification[loopCount][i].split("<Section>", -1);

							if (theme.verifyRecordInGrid(data[0], data[1], data[2].split("<break>", -1))) {
								log(LogStatus.PASS,
										"-----Verified Grid Data for Theme: " + themeName + " for Grid: " + data[0]
												+ " , for Grid: " + data[1] + " and for Record: " + data[2] + " -----",
										YesNo.No);
							} else {
								sa.assertTrue(false,
										"-----Not Verified Grid Data for Theme: " + themeName + " for Grid: " + data[0]
												+ " , for Grid: " + data[1] + " and for Record: " + data[2] + " -----");
								log(LogStatus.FAIL,
										"-----Not Verified Grid Data for Theme: " + themeName + " for Grid: " + data[0]
												+ " , for Grid: " + data[1] + " and for Record: " + data[2] + " -----",
										YesNo.Yes);
							}

						}

						driver.close();
						driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
						lp.CRMlogout();
					}

					else {
						sa.assertTrue(false, "Not Successfully Navigate to theme: " + themeName);
						log(LogStatus.FAIL, "Not Successfully Navigate to theme: " + themeName, YesNo.Yes);
					}

				}
			} else {
				log(LogStatus.ERROR, "Not able to click on setup link so cannot change value", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link so cannot change value");
			}

			loopCount++;
		}

		Integer loopCount2 = 0;
		for (String[] revertMetaDataName : revertBackMetaDataNames) {

			lp.CRMLogin(superAdminUserName, adminPassword);
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

				if (revertMetaDataName.length == revertBackMetaDataValues[loopCount2].length) {

					for (int i = 0; i < revertMetaDataName.length; i++) {
						if (setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(),
								revertMetaDataName[i], revertBackMetaDataValues[loopCount2][i], 10)) {
							log(LogStatus.INFO, "Changed the value of " + revertMetaDataName[i] + " for Acuity Setting",
									YesNo.No);

						} else {
							log(LogStatus.ERROR, "Not able to change the value of " + revertMetaDataName[i]
									+ " for Acuity Setting, So Not Able to Verify Grids", YesNo.No);
							sa.assertTrue(false, "Not able to change the value of " + revertMetaDataName[i]
									+ " for Acuity Setting, So Not Able to Verify Grids");
						}
						ThreadSleep(5000);

					}

				} else {
					log(LogStatus.ERROR, "The size of metadata name and metadata value are not equal", YesNo.No);
					sa.assertTrue(false, "The size of metadata name and metadata value are not equal");
				}
				switchToDefaultContent(driver);
				driver.close();
				driver.switchTo().window(parentWindow);

			} else {
				log(LogStatus.ERROR, "Not able to click on setup link so cannot change value", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link so cannot change value");
			}

		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc065_UpdateCustomMetaDataForFieldsOfThemeInCaseOfTheme(String projectName) {

		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		String parentWindow = null;
		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		String[][] metaDataNames = { { AT_MetaDataName20, AT_MetaDataName21 }, { AT_MetaDataName21, AT_MetaDataName20 },
				{ AT_MetaDataName20 } };
		String[][] metaDataValues = { { AT_MetaDataNewValue20, AT_MetaDataNewValue21 },
				{ AT_MetaDataNewValue20, AT_MetaDataNewValue21 }, { AT_MetaDataNewValue22 } };

		String[][] dataVerification = { { AT_Theme_Data_106, AT_Theme_Data_107 },
				{ AT_Theme_Data_106, AT_Theme_Data_107 }, { AT_Theme_Data_108 } };

		String[][] revertBackMetaDataNames = { { AT_MetaDataName20, AT_MetaDataName21 } };
		String[][] revertBackMetaDataValues = { { AT_MetaDataOldValue20, AT_MetaDataOldValue21 } };

		Integer loopCount = 0;
		for (String[] metaDataName : metaDataNames) {

			Integer status = 0;
			Integer mainLoopCount = 0;
			lp.CRMLogin(superAdminUserName, adminPassword);
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

				if (metaDataName.length == metaDataValues[loopCount].length) {

					for (int i = 0; i < metaDataName.length; i++) {
						if (setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(),
								metaDataName[i], metaDataValues[loopCount][i], 10)) {
							log(LogStatus.INFO, "Changed the value of " + metaDataName[i] + " for Acuity Setting",
									YesNo.No);
							status++;
						} else {
							log(LogStatus.ERROR, "Not able to change the value of " + metaDataName[i]
									+ " for Acuity Setting, So Not Able to Verify Grids", YesNo.No);
							sa.assertTrue(false, "Not able to change the value of " + metaDataName[i]
									+ " for Acuity Setting, So Not Able to Verify Grids");
						}
						ThreadSleep(5000);
						mainLoopCount++;
					}

				} else {
					log(LogStatus.ERROR, "The size of metadata name and metadata value are not equal", YesNo.No);
					sa.assertTrue(false, "The size of metadata name and metadata value are not equal");
				}
				switchToDefaultContent(driver);
				driver.close();
				driver.switchTo().window(parentWindow);
				lp.CRMlogout();

				if (mainLoopCount.equals(status) && !status.equals(0)) {

					lp.CRMLogin(crmUser1EmailID, adminPassword);

					if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {
						log(LogStatus.PASS, "Successfully Navigate to theme: " + themeName, YesNo.No);
						for (int i = 0; i < dataVerification[loopCount].length; i++) {

							String[] data = dataVerification[loopCount][i].split("<Section>", -1);

							if (theme.verifyRecordInGrid(data[0], data[1], data[2].split("<break>", -1))) {
								log(LogStatus.PASS,
										"-----Verified Grid Data for Theme: " + themeName + " for Grid: " + data[0]
												+ " , for Grid: " + data[1] + " and for Record: " + data[2] + " -----",
										YesNo.No);
							} else {
								sa.assertTrue(false,
										"-----Not Verified Grid Data for Theme: " + themeName + " for Grid: " + data[0]
												+ " , for Grid: " + data[1] + " and for Record: " + data[2] + " -----");
								log(LogStatus.FAIL,
										"-----Not Verified Grid Data for Theme: " + themeName + " for Grid: " + data[0]
												+ " , for Grid: " + data[1] + " and for Record: " + data[2] + " -----",
										YesNo.Yes);
							}

						}

						driver.close();
						driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
						lp.CRMlogout();
					}

					else {
						sa.assertTrue(false, "Not Successfully Navigate to theme: " + themeName);
						log(LogStatus.FAIL, "Not Successfully Navigate to theme: " + themeName, YesNo.Yes);
					}

				}
			} else {
				log(LogStatus.ERROR, "Not able to click on setup link so cannot change value", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link so cannot change value");
			}

			loopCount++;
		}

		Integer loopCount2 = 0;
		for (String[] revertMetaDataName : revertBackMetaDataNames) {

			lp.CRMLogin(superAdminUserName, adminPassword);
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

				if (revertMetaDataName.length == revertBackMetaDataValues[loopCount2].length) {

					for (int i = 0; i < revertMetaDataName.length; i++) {
						if (setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(),
								revertMetaDataName[i], revertBackMetaDataValues[loopCount2][i], 10)) {
							log(LogStatus.INFO, "Changed the value of " + revertMetaDataName[i] + " for Acuity Setting",
									YesNo.No);

						} else {
							log(LogStatus.ERROR, "Not able to change the value of " + revertMetaDataName[i]
									+ " for Acuity Setting, So Not Able to Verify Grids", YesNo.No);
							sa.assertTrue(false, "Not able to change the value of " + revertMetaDataName[i]
									+ " for Acuity Setting, So Not Able to Verify Grids");
						}
						ThreadSleep(5000);

					}

				} else {
					log(LogStatus.ERROR, "The size of metadata name and metadata value are not equal", YesNo.No);
					sa.assertTrue(false, "The size of metadata name and metadata value are not equal");
				}
				switchToDefaultContent(driver);
				driver.close();
				driver.switchTo().window(parentWindow);

			} else {
				log(LogStatus.ERROR, "Not able to click on setup link so cannot change value", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link so cannot change value");
			}

		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc066_UpdateCustomMetaDataForFieldsOfThemeInCaseOfClip(String projectName) {

		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		String parentWindow = null;
		String themeName = AT_Theme2;
		String themeTabName = tabObj9;

		String[][] metaDataNames = { { AT_MetaDataName23, AT_MetaDataName24 }, { AT_MetaDataName24, AT_MetaDataName23 },
				{ AT_MetaDataName23 } };
		String[][] metaDataValues = { { AT_MetaDataNewValue23, AT_MetaDataNewValue24 },
				{ AT_MetaDataNewValue23, AT_MetaDataNewValue24 }, { AT_MetaDataNewValue25 } };

		String[][] dataVerification = { { AT_Theme_Data_109, AT_Theme_Data_110 },
				{ AT_Theme_Data_109, AT_Theme_Data_110 }, { AT_Theme_Data_111 } };

		String[][] revertBackMetaDataNames = { { AT_MetaDataName23, AT_MetaDataName24 } };
		String[][] revertBackMetaDataValues = { { AT_MetaDataOldValue23, AT_MetaDataOldValue24 } };

		Integer loopCount = 0;
		for (String[] metaDataName : metaDataNames) {

			Integer status = 0;
			Integer mainLoopCount = 0;
			lp.CRMLogin(superAdminUserName, adminPassword);
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

				if (metaDataName.length == metaDataValues[loopCount].length) {

					for (int i = 0; i < metaDataName.length; i++) {
						if (setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(),
								metaDataName[i], metaDataValues[loopCount][i], 10)) {
							log(LogStatus.INFO, "Changed the value of " + metaDataName[i] + " for Acuity Setting",
									YesNo.No);
							status++;
						} else {
							log(LogStatus.ERROR, "Not able to change the value of " + metaDataName[i]
									+ " for Acuity Setting, So Not Able to Verify Grids", YesNo.No);
							sa.assertTrue(false, "Not able to change the value of " + metaDataName[i]
									+ " for Acuity Setting, So Not Able to Verify Grids");
						}
						ThreadSleep(5000);
						mainLoopCount++;
					}

				} else {
					log(LogStatus.ERROR, "The size of metadata name and metadata value are not equal", YesNo.No);
					sa.assertTrue(false, "The size of metadata name and metadata value are not equal");
				}
				switchToDefaultContent(driver);
				driver.close();
				driver.switchTo().window(parentWindow);
				lp.CRMlogout();

				if (mainLoopCount.equals(status) && !status.equals(0)) {

					lp.CRMLogin(crmUser1EmailID, adminPassword);

					if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {
						log(LogStatus.PASS, "Successfully Navigate to theme: " + themeName, YesNo.No);
						for (int i = 0; i < dataVerification[loopCount].length; i++) {

							String[] data = dataVerification[loopCount][i].split("<Section>", -1);

							if (theme.verifyRecordInGrid(data[0], data[1], data[2].split("<break>", -1))) {
								log(LogStatus.PASS,
										"-----Verified Grid Data for Theme: " + themeName + " for Grid: " + data[0]
												+ " , for Grid: " + data[1] + " and for Record: " + data[2] + " -----",
										YesNo.No);
							} else {
								sa.assertTrue(false,
										"-----Not Verified Grid Data for Theme: " + themeName + " for Grid: " + data[0]
												+ " , for Grid: " + data[1] + " and for Record: " + data[2] + " -----");
								log(LogStatus.FAIL,
										"-----Not Verified Grid Data for Theme: " + themeName + " for Grid: " + data[0]
												+ " , for Grid: " + data[1] + " and for Record: " + data[2] + " -----",
										YesNo.Yes);
							}

						}

						driver.close();
						driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
						lp.CRMlogout();
					}

					else {
						sa.assertTrue(false, "Not Successfully Navigate to theme: " + themeName);
						log(LogStatus.FAIL, "Not Successfully Navigate to theme: " + themeName, YesNo.Yes);
					}

				}
			} else {
				log(LogStatus.ERROR, "Not able to click on setup link so cannot change value", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link so cannot change value");
			}

			loopCount++;
		}

		Integer loopCount2 = 0;
		for (String[] revertMetaDataName : revertBackMetaDataNames) {

			lp.CRMLogin(superAdminUserName, adminPassword);
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

				if (revertMetaDataName.length == revertBackMetaDataValues[loopCount2].length) {

					for (int i = 0; i < revertMetaDataName.length; i++) {
						if (setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(),
								revertMetaDataName[i], revertBackMetaDataValues[loopCount2][i], 10)) {
							log(LogStatus.INFO, "Changed the value of " + revertMetaDataName[i] + " for Acuity Setting",
									YesNo.No);

						} else {
							log(LogStatus.ERROR, "Not able to change the value of " + revertMetaDataName[i]
									+ " for Acuity Setting, So Not Able to Verify Grids", YesNo.No);
							sa.assertTrue(false, "Not able to change the value of " + revertMetaDataName[i]
									+ " for Acuity Setting, So Not Able to Verify Grids");
						}
						ThreadSleep(5000);

					}

				} else {
					log(LogStatus.ERROR, "The size of metadata name and metadata value are not equal", YesNo.No);
					sa.assertTrue(false, "The size of metadata name and metadata value are not equal");
				}
				switchToDefaultContent(driver);
				driver.close();
				driver.switchTo().window(parentWindow);

			} else {
				log(LogStatus.ERROR, "Not able to click on setup link so cannot change value", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link so cannot change value");
			}

		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityThemeTc067_RecordTypeOfContactDealAndTargetObjectsActive(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String contactRecordTypeList = AT_RecordTypes_1;
		String contactRecordTypeArray[] = contactRecordTypeList.split(breakSP, -1);

		String dealRecordTypeList = AT_RecordTypes_5;
		String dealRecordTypeArray[] = dealRecordTypeList.split(breakSP, -1);

		String targetRecordTypeList = AT_RecordTypes_3;
		String targetRecordTypeArray[] = targetRecordTypeList.split(breakSP, -1);

		String[][] recordTypeFields = { { recordTypeLabel.Active.toString(), "Checked" } };
		String profileName = "MnA Standard User";

		lp.CRMLogin(superAdminUserName, adminPassword);

		if (bp.editOfRecordType(projectName, contactRecordTypeArray, recordTypeFields, object.Contact, false,
				profileName)) {
			log(LogStatus.INFO, "----Record Types Gets Active for Object: " + object.Contact + "----", YesNo.No);

		} else {
			log(LogStatus.ERROR, "----Record Types not Gets Active for Object: " + object.Contact + "----", YesNo.No);
			sa.assertTrue(false, "----Record Types not Gets Active for Object: " + object.Contact + "----");
		}

		if (bp.editOfRecordType(projectName, dealRecordTypeArray, recordTypeFields, object.Deal, false, profileName)) {
			log(LogStatus.INFO, "----Record Types Gets Active for Object: " + object.Deal + "----", YesNo.No);

		} else {
			log(LogStatus.ERROR, "----Record Types not Gets Active for Object: " + object.Deal + "----", YesNo.No);
			sa.assertTrue(false, "----Record Types not Gets Active for Object: " + object.Deal + "----");
		}

		if (bp.editOfRecordType(projectName, targetRecordTypeArray, recordTypeFields, object.Target, false,
				profileName)) {
			log(LogStatus.INFO, "----Record Types Gets Active for Object: " + object.Target + "----", YesNo.No);

		} else {
			log(LogStatus.ERROR, "----Record Types not Gets Active for Object: " + object.Target + "----", YesNo.No);
			sa.assertTrue(false, "----Record Types not Gets Active for Object: " + object.Target + "----");
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityThemeTc068_CheckNavAndGridCountsOfThemeAfterActivatingRecordTypesOfContactDealAndTargetObjects(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);

		String themeTabName = tabObj9;
		String themeName = AT_Theme2;
		HashMap<String, Integer> expectedSectionNameAndCount = new HashMap<String, Integer>();
		expectedSectionNameAndCount.put(AT_Theme_Grid_11, 6);
		expectedSectionNameAndCount.put(AT_Theme_Grid_12, 6);
		expectedSectionNameAndCount.put(AT_Theme_Grid_13, 6);
		expectedSectionNameAndCount.put(AT_Theme_Grid_14, 6);
		expectedSectionNameAndCount.put(AT_Theme_Grid_112, 6);
		expectedSectionNameAndCount.put(AT_Theme_Grid_16, 3);
		expectedSectionNameAndCount.put(AT_Theme_Grid_17, 3);
		expectedSectionNameAndCount.put(AT_Theme_Grid_113, 6);
		expectedSectionNameAndCount.put(AT_Theme_Grid_19, 6);
		expectedSectionNameAndCount.put(AT_Theme_Grid_20, 6);

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		if (theme.verifyItsLeftCountAndGridCountSepratelyAndTotalSumWithAllCount(true, themeTabName, themeName,
				projectName, true, expectedSectionNameAndCount, true, true, true, true)) {
			log(LogStatus.PASS, "-----Verified Counts of Theme Section Wise-----", YesNo.No);

		} else {
			sa.assertTrue(false, "-----Not Verified Counts of Theme Section Wise-----");
			log(LogStatus.FAIL, "-----Not Verified Counts of Theme Section Wise-----", YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityThemeTc069_RecordTypeOfContactDealAndTargetObjectsInActive(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);

		String contactRecordTypeList = AT_RecordTypes_1;
		String contactRecordTypeArray[] = contactRecordTypeList.split(breakSP, -1);

		String dealRecordTypeList = AT_RecordTypes_4;
		String dealRecordTypeArray[] = dealRecordTypeList.split(breakSP, -1);

		String targetRecordTypeList = AT_RecordTypes_3;
		String targetRecordTypeArray[] = targetRecordTypeList.split(breakSP, -1);

		String[][] recordTypeFields = { { recordTypeLabel.Active.toString(), "Not Checked" } };
		String profileName = "MnA Standard User";

		lp.CRMLogin(superAdminUserName, adminPassword);

		if (bp.editOfRecordType(projectName, contactRecordTypeArray, recordTypeFields, object.Contact, true,
				profileName)) {
			log(LogStatus.INFO, "----Record Types Gets InActive for Object: " + object.Contact + "----", YesNo.No);

		} else {
			log(LogStatus.ERROR, "----Record Types not Gets InActive for Object: " + object.Contact + "----", YesNo.No);
			sa.assertTrue(false, "----Record Types not Gets InActive for Object: " + object.Contact + "----");
		}

		if (bp.editOfRecordType(projectName, dealRecordTypeArray, recordTypeFields, object.Deal, true, profileName)) {
			log(LogStatus.INFO, "----Record Types Gets InActive for Object: " + object.Deal + "----", YesNo.No);

		} else {
			log(LogStatus.ERROR, "----Record Types not Gets InActive for Object: " + object.Deal + "----", YesNo.No);
			sa.assertTrue(false, "----Record Types not Gets InActive for Object: " + object.Deal + "----");
		}

		if (bp.editOfRecordType(projectName, targetRecordTypeArray, recordTypeFields, object.Target, true,
				profileName)) {
			log(LogStatus.INFO, "----Record Types Gets InActive for Object: " + object.Target + "----", YesNo.No);

		} else {
			log(LogStatus.ERROR, "----Record Types not Gets InActive for Object: " + object.Target + "----", YesNo.No);
			sa.assertTrue(false, "----Record Types not Gets InActive for Object: " + object.Target + "----");
		}

		lp.CRMlogout();
		sa.assertAll();
		CommonLib.ThreadSleep(3000);

	}

	@Parameters({ "projectName" })
	@Test
	public void AcuityThemeTc070_VerifyLocaleImpactLastInteractionDateOnThemePage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);

		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		ThemePageBusinessLayer theme = new ThemePageBusinessLayer(driver);
		int i = 0;

		String locale[] = { "German (Germany)", "English (United Kingdom)", "English (Australia)", "French (France)" };
		String timezone[] = { "(GMT+02:00) Central European Summer Time (Europe/Berlin)",
				"(GMT+01:00) British Summer Time (Europe/London)",
				"(GMT+08:00) Australian Western Standard Time (Australia/Perth)",
				"(GMT+02:00) Central European Summer Time (Europe/Paris)" };

		String[][] dataVerification = { { AT_Theme_Grid_11, AT_Theme_Grid_15 }, { AT_Theme_Grid_11, AT_Theme_Grid_15 },
				{ AT_Theme_Grid_11, AT_Theme_Grid_15 }, { AT_Theme_Grid_11, AT_Theme_Grid_15 } };
		String[] expectedDate = AT_Theme_Data_114.split("<break>", -1);
		String themeTabName = tabObj9;
		String themeName = AT_Theme2;
		Integer loopCount = 0;
		for (String t : timezone) {
			boolean flag = false;
			lp.CRMLogin(superAdminUserName, adminPassword, appName);
			String parentID = null;
			if (home.clickOnSetUpLink("", Mode.Lightning.toString())) {
				parentID = switchOnWindow(driver);
				if (parentID != null) {
					log(LogStatus.INFO, "Able to switch on  new window", YesNo.No);
					ThreadSleep(3000);
					refresh(driver);
					ThreadSleep(5000);
					System.out.println("Url:" + driver.getCurrentUrl());
					if (setup.searchStandardOrCustomObject(environment, mode, object.Users.toString())) {
						log(LogStatus.INFO, "click on Object : " + object.Users, YesNo.No);
						ThreadSleep(2000);

						if (setup.clickOnEditBtnForCRMUser(driver, crmUser1LastName, crmUser1FirstName, 20)) {
							log(LogStatus.INFO, "Click on edit Button " + crmUser1LastName + "," + crmUser1FirstName,
									YesNo.No);
							ThreadSleep(2000);

							if (selectVisibleTextFromDropDown(driver, setup.getLocaleDropdownList(10),
									"locale dropdown", locale[i])) {
								log(LogStatus.INFO, "selected visbible text from the Timezone dropdown " + locale[i],
										YesNo.No);
								ThreadSleep(2000);
								switchToDefaultContent(driver);
								ThreadSleep(5000);
								switchToFrame(driver, 20, setup.getSetUpPageIframe(20));
								if (selectVisibleTextFromDropDown(driver, setup.gettimezoneDropdownList(10),
										"timezone dropdown", t)) {
									log(LogStatus.INFO, "selected visbible text from the Timezone dropdown " + t,
											YesNo.No);
									ThreadSleep(2000);
									if (clickUsingJavaScript(driver, setup.getSaveButton(20), "Save Button",
											action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Clicked on Save Button", YesNo.No);
										ThreadSleep(5000);
										switchToDefaultContent(driver);

										flag = true;
									} else {
										log(LogStatus.ERROR, "Not Able to Click on Save Button for  " + crmUser1LastName
												+ "," + crmUser1FirstName, YesNo.Yes);
										sa.assertTrue(false, "Not Able to Click on Save Button for  " + crmUser1LastName
												+ "," + crmUser1FirstName);
									}
								} else {
									log(LogStatus.ERROR, "Not Able to select timezone dropdown for  " + crmUser1LastName
											+ "," + crmUser1FirstName, YesNo.Yes);
									sa.assertTrue(false, "Not Able to select timezone dropdown for  " + crmUser1LastName
											+ "," + crmUser1FirstName);
								}
							} else {
								log(LogStatus.ERROR, "Not Able to select locale dropdown for  " + crmUser1LastName + ","
										+ crmUser1FirstName, YesNo.Yes);
								sa.assertTrue(false, "Not Able to select locale dropdown for  " + crmUser1LastName + ","
										+ crmUser1FirstName);
							}
						} else {
							log(LogStatus.ERROR,
									"edit button is not clickable for " + crmUser1LastName + "," + crmUser1FirstName,
									YesNo.Yes);
							sa.assertTrue(false,
									"edit button is not clickable for " + crmUser1LastName + "," + crmUser1FirstName);
						}
					} else {
						log(LogStatus.ERROR, "users object is not clickable", YesNo.Yes);
						sa.assertTrue(false, "users object is not clickable");
					}

					driver.close();
					driver.switchTo().window(parentID);
					lp.CRMlogout();
				} else {
					log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
					sa.assertTrue(false, "could not find new window to switch");
				}
			} else {
				log(LogStatus.ERROR, "setup link is not clickable", YesNo.Yes);
				sa.assertTrue(false, "setup link is not clickable");
			}
			i++;

			ThreadSleep(5000);
			refresh(driver);

			if (flag) {
				lp.CRMLogin(crmUser1EmailID, adminPassword);

				if (theme.navigateToTheme(projectName, themeTabName, themeName, false)) {
					log(LogStatus.PASS, "Successfully Navigate to theme: " + themeName, YesNo.No);
					for (int k = 0; k < dataVerification[loopCount].length; k++) {

						String[] data = dataVerification[loopCount][k].split("<Section>", -1);

						if (expectedDate[loopCount] != null) {
							if (theme.verifyRecordInGrid(data[0], "Last Interaction Date",
									expectedDate[loopCount].split("<break>"))) {
								log(LogStatus.PASS,
										"-----Verified Last Interaction Date for Theme: " + themeName + " for Grid: "
												+ data[0] + " and i.e. " + expectedDate[loopCount] + " -----",
										YesNo.No);
							} else {
								sa.assertTrue(false, "-----Not Verified Last Interaction Date for Theme: " + themeName
										+ " for Grid: " + data[0] + " and i.e. " + expectedDate[loopCount] + " -----");
								log(LogStatus.FAIL, "-----Not Verified Last Interaction Date for Theme: " + themeName
										+ " for Grid: " + data[0] + " and i.e. " + expectedDate[loopCount] + " -----",
										YesNo.Yes);
							}
						} else {
							sa.assertTrue(false, "-----Not Verified Last Interaction Date for Theme: " + themeName
									+ " for Grid: " + data[0] + " as Expected Date Convert in null-----");
							log(LogStatus.FAIL, "-----Not Verified Last Interaction Date for Theme: " + themeName
									+ " for Grid: " + data[0] + " as Expected Date Convert in null-----", YesNo.Yes);
						}

					}

					driver.close();
					driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
					lp.CRMlogout();
				} else {
					log(LogStatus.ERROR, "-----Not Able to Navigate To Theme: " + themeName + "------", YesNo.No);
					sa.assertTrue(false, "-----Not Able to Navigate To Theme: " + themeName + "------");
				}
			} else {
				log(LogStatus.ERROR,
						"Not Able to Change the Loacle Setting to " + t
								+ " So Canot Verify Format of Last Interaction Date Under Grids on Theme Page",
						YesNo.Yes);
				sa.assertTrue(false, "Not Able to Change the Loacle Setting to " + t
						+ " So Canot Verify Format of Last Interaction Date Under Grids on Theme Page");

			}

			ThreadSleep(5000);

			loopCount++;
		}

		sa.assertAll();
	}
	
	public class DataContainer {
		public String gridName;
		public String columns;
		public String recordNameBasedOnColumnSelect;
		public String popUpHeader;

		public String[][] basicSection;
		public String[][] advanceSection;
		public IconType iconType;
		public PageName pageName;

		public DataContainer(String gridName, String columns, String recordNameBasedOnColumnSelect, String popUpHeader,
				String[][] basicSection, String[][] advanceSection, IconType iconType, PageName pageName) {
			this.gridName = gridName;
			this.columns = columns;
			this.recordNameBasedOnColumnSelect = recordNameBasedOnColumnSelect;
			this.popUpHeader = popUpHeader;

			this.basicSection = basicSection;
			this.advanceSection = advanceSection;
			this.iconType = iconType;
			this.pageName = pageName;
		}

		// Getters and setters for the fields
	}

	public class ResearchDataContainer {

		public String value;
		public String tabNames;
		public String tabValue;
		public String field;
		public String operator;
		public String values;
		public action action;
		public int timeout;

		public ResearchDataContainer(String value, String tabNames, String tabValue, String field, String operator,
				String values, action action, int timeout) {
			this.value = value;
			this.tabNames = tabNames;
			this.tabValue = tabValue;
			this.field = field;

			this.operator = operator;
			this.values = values;
			this.action = action;
			this.timeout = timeout;
		}

		// Getters and setters for the fields
	}
}
