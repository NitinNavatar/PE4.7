package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.FC_FieldLabelName22;
import static com.navatar.generic.CommonVariables.FC_FieldLabelName23;
import static com.navatar.generic.CommonVariables.FC_FieldLabelName24;
import static com.navatar.generic.CommonVariables.FC_FieldLabelName25;
import static com.navatar.generic.CommonVariables.FC_FieldLabelName26;
import static com.navatar.generic.CommonVariables.FC_FieldLabelName27;
import static com.navatar.generic.CommonVariables.FC_FieldLabelName28;
import static com.navatar.generic.CommonVariables.FC_FieldLabelName29;
import static com.navatar.generic.CommonVariables.FC_FieldLabelName30;
import static com.navatar.generic.CommonVariables.FC_FieldLabelName31;
import static com.navatar.generic.CommonVariables.FC_FieldLabelName34;
import static com.navatar.generic.CommonVariables.FC_FieldType22;
import static com.navatar.generic.CommonVariables.FC_FieldType23;
import static com.navatar.generic.CommonVariables.FC_FieldType24;
import static com.navatar.generic.CommonVariables.FC_FieldType25;
import static com.navatar.generic.CommonVariables.FC_FieldType26;
import static com.navatar.generic.CommonVariables.FC_FieldType27;
import static com.navatar.generic.CommonVariables.FC_FieldType28;
import static com.navatar.generic.CommonVariables.FC_FieldType29;
import static com.navatar.generic.CommonVariables.FC_FieldType30;
import static com.navatar.generic.CommonVariables.FC_FieldType31;
import static com.navatar.generic.CommonVariables.FC_FieldType34;
import static com.navatar.generic.CommonVariables.FC_Length22;
import static com.navatar.generic.CommonVariables.FC_Length23;
import static com.navatar.generic.CommonVariables.FC_Length24;
import static com.navatar.generic.CommonVariables.FC_Length25;
import static com.navatar.generic.CommonVariables.FC_Length26;
import static com.navatar.generic.CommonVariables.FC_Length27;
import static com.navatar.generic.CommonVariables.FC_Length28;
import static com.navatar.generic.CommonVariables.FC_Length29;
import static com.navatar.generic.CommonVariables.FC_Length30;
import static com.navatar.generic.CommonVariables.FC_Length31;
import static com.navatar.generic.CommonVariables.adminPassword;
import static com.navatar.generic.CommonVariables.appName;
import static com.navatar.generic.CommonVariables.environment;
import static com.navatar.generic.CommonVariables.mode;
import static com.navatar.generic.CommonVariables.superAdminUserName;
import static com.navatar.generic.CommonVariables.tabCustomObj;
import static com.navatar.generic.CommonVariables.adminPassword;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.SmokeCommonVariables;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.customObjectLabel;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;
import com.relevantcodes.extentreports.LogStatus;

public class CustomObjPageBusinessLayer extends CustomObjPage {

	public CustomObjPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param recordType  TODO
	 * @param recordName
	 * @param value
	 * @param clickNew    TODO
	 * @return true/false
	 * @description this is used to create record of custom object page
	 */
	public boolean createRecord(String projectName, String recordType, String recordName, String value,
			boolean isCreatedFromTaskLayout) {
		// refresh(driver);
		ThreadSleep(10000);
		if (!isCreatedFromTaskLayout) {
			if (clickUsingJavaScript(driver, getNewButton(projectName, 60), "new button")) {
				appLog.info("clicked on new button");
			} else {
				appLog.error("Not able to click on new button so cannot create new record : " + value);
				return false;
			}
		}
		if (!recordType.equals("") || !recordType.isEmpty()) {
			ThreadSleep(2000);
			if (click(driver, getRadioButtonforRecordType(recordType, isCreatedFromTaskLayout, 5),
					"Radio Button for : " + recordType, action.SCROLLANDBOOLEAN)) {
				appLog.info("Clicked on radio Button  for record type : " + recordType);
				if (click(driver, getContinueOrNextButton(projectName, 5), "Continue Button", action.BOOLEAN)) {
					appLog.info("Clicked on Continue or Nxt Button");
					ThreadSleep(1000);
				} else {
					appLog.error("Not Able to Clicked on Next Button");
					return false;
				}
			} else {
				appLog.error("Not Able to Clicked on radio Button for record type : " + recordType);
				return false;
			}

		}

		if (sendKeys(driver, getFieldTextBox(20), value, SmokeCommonVariables.tabCustomObjField,
				action.SCROLLANDBOOLEAN)) {

			if (click(driver, getCustomTabSaveBtn(projectName, 60), "Save Button", action.BOOLEAN)) {
				if (getRecordNameInViewMode(projectName, 60) != null) {
					ThreadSleep(2000);
					String recordNameViewMode = getText(driver, getRecordNameInViewMode(projectName, 60),
							"Custom object Record Name", action.BOOLEAN);
					if (recordNameViewMode.contains(value)) {
						appLog.info("Record is created successfully.:" + value);
						return true;
					} else {
						appLog.error("record value does not match: " + recordNameViewMode + " and " + value);
					}
				} else {
					appLog.error("record value is not visible");
				}
			} else {
				appLog.error("save button is not clickable so cannot create record in custom object");
			}
		} else {
			appLog.error("field text box is not visible so cannot create new record");
		}
		return false;
	}

	public void CreateACustomObject(String projectName, String ObjectLabel, String[] userTypesToGivePermissions) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		NavigationPageBusineesLayer npbl = new NavigationPageBusineesLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword);
		String customObject = ObjectLabel + "s";
		String parentID = null;
		boolean flag = false;
		if (home.clickOnSetUpLink()) {
			flag = false;
			parentID = switchOnWindow(driver);
			if (parentID != null) {
				if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(), object.Create)) {
					log(LogStatus.INFO, "Click on Create/Custom object so going to create : " + customObject, YesNo.No);
					String[][] labelWithValue = { { customObjectLabel.Label.toString(), ObjectLabel },
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
		parentID = null;
		if (flag) {
			switchToDefaultContent(driver);
			refresh(driver);
			ThreadSleep(5000);
			if (home.clickOnSetUpLink()) {
				flag = false;
				parentID = switchOnWindow(driver);
				if (parentID != null) {

					if (sp.addObjectToTab(environment, mode, projectName, object.Tabs, ObjectLabel, "Bell", parentID)) {
						log(LogStatus.PASS, customObject + " added to Tab", YesNo.No);
						flag = true;
					} else {
						log(LogStatus.FAIL, customObject + " not added to Tab", YesNo.Yes);
						sa.assertTrue(false, customObject + " not added to Tab");
					}
					driver.close();
					driver.switchTo().window(parentID);
				} else {
					log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
					sa.assertTrue(false, "could not find new window to switch");
				}
			} else {
				log(LogStatus.ERROR, "could not click on setup link so cannot add custom object to Tab", YesNo.Yes);
				sa.assertTrue(false, "could not click on setup link so cannot add custom object to Tab");
			}

		} else {
			log(LogStatus.ERROR, "Not Able to create : " + customObject + " so cannot add custom object to Tab",
					YesNo.Yes);
			sa.assertTrue(false, "Not Able to create : " + customObject + " so cannot add custom object to Tab");
		}

		// String[] userNames= {"PE Standard User"};
		String onObject = customObject;
		String permission1 = "Create";
		String permission2 = "Delete";
		parentID = null;
		for (String userName : userTypesToGivePermissions) {
			switchToDefaultContent(driver);
			if (home.clickOnSetUpLink()) {
				parentID = switchOnWindow(driver);
				if (parentID != null) {
					log(LogStatus.INFO,
							"Able to switch on new window, so going to set" + permission1 + " for " + onObject,
							YesNo.No);
					ThreadSleep(500);
					if (setup.searchStandardOrCustomObject(environment, mode, object.Profiles)) {
						log(LogStatus.INFO, "click on Object : " + object.Profiles, YesNo.No);
						ThreadSleep(2000);
						if (setup.permissionChangeOfUserONObject(driver, userName,
								new String[][] { { onObject, permission1 }, { onObject, permission2 } }, 20)) {
							log(LogStatus.PASS,
									permission1 + " permission change for " + userName + " on object " + onObject,
									YesNo.No);

						} else {
							sa.assertTrue(false,
									permission1 + " permission not change for " + userName + " on object " + onObject);
							log(LogStatus.FAIL,
									permission1 + " permission not change for " + userName + " on object " + onObject,
									YesNo.Yes);
						}
					} else {
						log(LogStatus.ERROR, "Not able to search/click on " + object.Profiles, YesNo.Yes);
						sa.assertTrue(false, "Not able to search/click on " + object.Profiles);
					}

				} else {
					log(LogStatus.FAIL,
							"could not find new window to switch, so cannot to set" + permission1 + " for " + onObject,
							YesNo.Yes);
					sa.assertTrue(false,
							"could not find new window to switch, to set" + permission1 + " for " + onObject);
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link");
			}
		}

		if (parentID != null) {
			driver.close();
			driver.switchTo().window(parentID);
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	public void CreateACustomObjectAndNotAddToTab(String projectName, String ObjectLabel,
			String[] userTypesToGivePermissions) {

		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String customObject = ObjectLabel + "s";
		String parentID = null;
		boolean flag = false;
		if (home.clickOnSetUpLink()) {
			flag = false;
			parentID = switchOnWindow(driver);
			if (parentID != null) {
				if (sp.searchStandardOrCustomObject("", Mode.Lightning.toString(), object.Create)) {
					log(LogStatus.INFO, "Click on Create/Custom object so going to create : " + customObject, YesNo.No);
					String[][] labelWithValue = { { customObjectLabel.Label.toString(), ObjectLabel },
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
		parentID = null;

		// String[] userNames= {"PE Standard User"};

		if (flag) {
			String onObject = customObject;
			String permission1 = "Create";
			String permission2 = "Delete";
			parentID = null;
			for (String userName : userTypesToGivePermissions) {
				switchToDefaultContent(driver);
				if (home.clickOnSetUpLink()) {
					parentID = switchOnWindow(driver);
					if (parentID != null) {
						log(LogStatus.INFO,
								"Able to switch on new window, so going to set" + permission1 + " for " + onObject,
								YesNo.No);
						ThreadSleep(500);
						if (setup.searchStandardOrCustomObject(environment, mode, object.Profiles)) {
							log(LogStatus.INFO, "click on Object : " + object.Profiles, YesNo.No);
							ThreadSleep(2000);
							if (setup.permissionChangeOfUserONObject(driver, userName,
									new String[][] { { onObject, permission1 }, { onObject, permission2 } }, 20)) {
								log(LogStatus.PASS,
										permission1 + " permission change for " + userName + " on object " + onObject,
										YesNo.No);

							} else {
								sa.assertTrue(false, permission1 + " permission not change for " + userName
										+ " on object " + onObject);
								log(LogStatus.FAIL, permission1 + " permission not change for " + userName
										+ " on object " + onObject, YesNo.Yes);
							}
						} else {
							log(LogStatus.ERROR, "Not able to search/click on " + object.Profiles, YesNo.Yes);
							sa.assertTrue(false, "Not able to search/click on " + object.Profiles);
						}

					} else {
						log(LogStatus.FAIL, "could not find new window to switch, so cannot to set" + permission1
								+ " for " + onObject, YesNo.Yes);
						sa.assertTrue(false,
								"could not find new window to switch, to set" + permission1 + " for " + onObject);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on setup link");
				}
			}

		} else {
			log(LogStatus.ERROR, "Object: " + customObject + " not Created, SO not able to give permissions",
					YesNo.Yes);
			sa.assertTrue(false, "Object: " + customObject + " not Created, SO not able to give permissions");
		}
		if (parentID != null) {
			driver.close();
			driver.switchTo().window(parentID);
		}

	}

	public boolean objectPermissionGivenOrRemove(String[][] objectAndPermissionAndGivenOrGivenNot,
			String[] userTypesToGivePermissions) {

		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);

		boolean flag = false;
		String parentID = null;
		for (String userName : userTypesToGivePermissions) {
			switchToDefaultContent(driver);
			if (home.clickOnSetUpLink()) {
				parentID = switchOnWindow(driver);
				if (parentID != null) {
					log(LogStatus.INFO, "Able to switch on new window, so going to set permission for objects"
							+ objectAndPermissionAndGivenOrGivenNot, YesNo.No);
					ThreadSleep(500);
					if (setup.searchStandardOrCustomObject(environment, mode, object.Profiles)) {
						log(LogStatus.INFO, "click on Object : " + object.Profiles, YesNo.No);
						ThreadSleep(2000);
						if (setup.permissionChangeOfUserONObject(driver, userName,
								objectAndPermissionAndGivenOrGivenNot, 20)) {
							log(LogStatus.PASS,
									"Permission Set for Object is: " + objectAndPermissionAndGivenOrGivenNot, YesNo.No);
							flag = true;

						} else {
							sa.assertTrue(false,
									"Permission not Set for Object is: " + objectAndPermissionAndGivenOrGivenNot);
							log(LogStatus.FAIL,
									"Permission not Set for Object is: " + objectAndPermissionAndGivenOrGivenNot,
									YesNo.Yes);
						}
					} else {
						log(LogStatus.ERROR, "Not able to search/click on " + object.Profiles, YesNo.Yes);
						sa.assertTrue(false, "Not able to search/click on " + object.Profiles);
					}

				} else {
					log(LogStatus.FAIL,
							"could not find new window to switch, so cannot to set permission for object: "
									+ "Permission not Set for Object is: " + objectAndPermissionAndGivenOrGivenNot,
							YesNo.Yes);
					sa.assertTrue(false, "could not find new window to switch, so cannot to set permission for object: "
							+ "Permission not Set for Object is: " + objectAndPermissionAndGivenOrGivenNot);
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link");
			}
		}

		if (parentID != null) {
			driver.close();
			driver.switchTo().window(parentID);
		}
		return flag;
	}

}
