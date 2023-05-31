/*
 * package com.navatar.scripts;
 * 
 * import static com.navatar.generic.CommonLib.*;
 * 
 * import static com.navatar.generic.CommonVariables.*;
 * 
 * import java.util.ArrayList;
 * 
 * import org.testng.annotations.Parameters; import org.testng.annotations.Test;
 * 
 * import com.navatar.generic.BaseLib; import com.navatar.generic.EmailLib;
 * import com.navatar.generic.ExcelUtils; import
 * com.navatar.generic.EnumConstants.Environment; import
 * com.navatar.generic.EnumConstants.YesNo; import
 * com.navatar.generic.EnumConstants.excelLabel; import
 * com.navatar.generic.EnumConstants.object; import
 * com.navatar.pageObjects.BasePageBusinessLayer; import
 * com.navatar.pageObjects.HomePageBusineesLayer; import
 * com.navatar.pageObjects.LoginPageBusinessLayer; import
 * com.navatar.pageObjects.SetupPageBusinessLayer; import
 * com.relevantcodes.extentreports.LogStatus;
 * 
 * public class AcuityClip extends BaseLib{
 * 
 * @Parameters({ "projectName"})
 * 
 * @Test public void ACTc001_createCRMUser(String projectName) {
 * SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
 * HomePageBusineesLayer home = new HomePageBusineesLayer(driver); String
 * parentWindow = null; String[] splitedUserLastName =
 * removeNumbersFromString(crmUser1LastName); String UserLastName =
 * splitedUserLastName[0] + lp.generateRandomNumber(); String emailId =
 * lp.generateRandomEmailId(gmailUserName);
 * ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users",
 * excelLabel.Variable_Name, "User1",excelLabel.User_Last_Name);
 * lp.CRMLogin(superAdminUserName, adminPassword, appName); boolean flag =
 * false; for (int i = 0; i < 3; i++) { try { if (home.clickOnSetUpLink()) {
 * flag = true; parentWindow = switchOnWindow(driver); if (parentWindow == null)
 * { sa.assertTrue(false,
 * "No new window is open after click on setup link in lighting mode so cannot create CRM User1"
 * ); log(LogStatus.SKIP,
 * "No new window is open after click on setup link in lighting mode so cannot create CRM User1"
 * , YesNo.Yes);
 * exit("No new window is open after click on setup link in lighting mode so cannot create CRM User1"
 * ); } if (setup.createPEUser( crmUser1FirstName, UserLastName, emailId,
 * crmUserLience, crmUserProfile,null)) { log(LogStatus.INFO,
 * "CRM User is created Successfully: " + crmUser1FirstName + " " +
 * UserLastName, YesNo.No); ExcelUtils.writeData(testCasesFilePath, emailId,
 * "Users", excelLabel.Variable_Name, "User1", excelLabel.User_Email);
 * ExcelUtils.writeData(testCasesFilePath, UserLastName, "Users",
 * excelLabel.Variable_Name, "User1", excelLabel.User_Last_Name); flag = true;
 * break;
 * 
 * } driver.close(); driver.switchTo().window(parentWindow);
 * 
 * } } catch (Exception e) { // TODO Auto-generated catch block
 * log(LogStatus.INFO, "could not find setup link, trying again..", YesNo.No); }
 * 
 * } if (flag) {
 * if(!environment.equalsIgnoreCase(Environment.Sandbox.toString())) { if
 * (setup.installedPackages(crmUser1FirstName, UserLastName)) {
 * appLog.info("PE Package is installed Successfully in CRM User: " +
 * crmUser1FirstName + " " + UserLastName);
 * 
 * } else { appLog.error( "Not able to install PE package in CRM User1: " +
 * crmUser1FirstName + " " + UserLastName); sa.assertTrue(false,
 * "Not able to install PE package in CRM User1: " + crmUser1FirstName + " " +
 * UserLastName); log(LogStatus.ERROR,
 * "Not able to install PE package in CRM User1: " + crmUser1FirstName + " " +
 * UserLastName, YesNo.Yes); } }
 * 
 * 
 * }else{
 * 
 * log(LogStatus.ERROR, "could not click on setup link, test case fail",
 * YesNo.Yes); sa.assertTrue(false,
 * "could not click on setup link, test case fail");
 * 
 * }
 * 
 * lp.CRMlogout(); closeBrowser();
 * config(ExcelUtils.readDataFromPropertyFile("Browser")); lp = new
 * LoginPageBusinessLayer(driver); String passwordResetLink=null; try {
 * passwordResetLink = new EmailLib().getResetPasswordLink("passwordreset",
 * ExcelUtils.readDataFromPropertyFile("gmailUserName"),
 * ExcelUtils.readDataFromPropertyFile("gmailPassword")); } catch
 * (InterruptedException e2) { // TODO Auto-generated catch block
 * e2.printStackTrace(); } appLog.info("ResetLinkIs: " + passwordResetLink);
 * driver.get(passwordResetLink); if (lp.setNewPassword()) {
 * appLog.info("Password is set successfully for CRM User1: " +
 * crmUser1FirstName + " " + UserLastName ); } else {
 * appLog.info("Password is not set for CRM User1: " + crmUser1FirstName + " " +
 * UserLastName); sa.assertTrue(false, "Password is not set for CRM User1: " +
 * crmUser1FirstName + " " + UserLastName); log(LogStatus.ERROR,
 * "Password is not set for CRM User1: " + crmUser1FirstName + " " +
 * UserLastName, YesNo.Yes); } lp.CRMlogout(); sa.assertAll(); }
 * 
 * 
 * @Parameters({ "projectName"})
 * 
 * @Test public void
 * ACTc002_VerifyClipsOnTheNavigationMenuAndVerifySizeOfTheBoard(String
 * projectName) { SetupPageBusinessLayer setup = new
 * SetupPageBusinessLayer(driver); LoginPageBusinessLayer lp = new
 * LoginPageBusinessLayer(driver); HomePageBusineesLayer home = new
 * HomePageBusineesLayer(driver); BasePageBusinessLayer bp=new
 * BasePageBusinessLayer(driver); lp.CRMLogin(crmUser1EmailID, adminPassword,
 * appName);
 * 
 * if(bp.getClipIconOnNavigation(10)!=null) { log(LogStatus.INFO,
 * "Clipped icon has been verified on navigation menu. ", YesNo.No);
 * 
 * } else { log(LogStatus.ERROR,
 * "Clipped icon is not verified on navigation menu. ", YesNo.No);
 * sa.assertTrue(false, "Clipped icon is not verified on navigation menu. "); }
 * 
 * if(bp.getClipTextFromNavigation(10)!=null) { log(LogStatus.INFO,
 * "Clipped text has been verified on navigation menu. ", YesNo.No);
 * 
 * } else { log(LogStatus.ERROR,
 * "Clipped text is not verified on navigation menu. ", YesNo.No);
 * sa.assertTrue(false, "Clipped text is not verified on navigation menu. "); }
 * 
 * if(clickUsingJavaScript(driver, bp.getClipTextFromNavigation(10),
 * "clip on navigation")) { log(LogStatus.INFO, "Clicked on clip button",
 * YesNo.No);
 * 
 * ArrayList<String> result= bp.verifyUIOfClipPopupFromNavigation();
 * if(result.isEmpty()) { log(LogStatus.INFO,
 * "The UI of Clip has been verified", YesNo.No); } else { log(LogStatus.ERROR,
 * "The UI of Clip is not verified", YesNo.No); sa.assertTrue(false,
 * "The UI of Clip is not verified"); } } else { log(LogStatus.ERROR,
 * "Not able to click on clip button", YesNo.No); sa.assertTrue(false,
 * "Not able to click on clip button"); }
 * 
 * 
 * if(clickUsingJavaScript(driver, bp.getMinimizeIconOnpopup(10),
 * "minimize icon of popup")) { log(LogStatus.INFO,
 * "Clicked on minimize popup button", YesNo.No);
 * if(bp.getClipTextOnPopup(20)==null) { log(LogStatus.INFO,
 * "Clip popup has been minimized", YesNo.No); } else { log(LogStatus.ERROR,
 * "Clip popup is not minimized", YesNo.No);
 * sa.assertTrue(false,"Clip popup is not minimized"); } } else {
 * log(LogStatus.ERROR, "Not able to click on minimize popup button", YesNo.No);
 * sa.assertTrue(false, "Not able to click on minimize popup button"); }
 * 
 * if(clickUsingJavaScript(driver, bp.getClipTextFromNavigation(10),
 * "clip on navigation")) { log(LogStatus.INFO, "Clicked on clip button",
 * YesNo.No); if(clickUsingJavaScript(driver, bp.getPopOutIconOnpopup(10),
 * "clip pop-out")) { log(LogStatus.INFO, "Clicked on clip pop-out button",
 * YesNo.No); String parentID=switchOnWindow(driver); ArrayList<String> result1=
 * bp.verifyUIOfClipPopupAfterClickOfPopOut(); if(result1.isEmpty()) {
 * log(LogStatus.INFO,
 * "The UI of Clip popup after click on the popout button has been verified",
 * YesNo.No); } else { log(LogStatus.ERROR,
 * "The UI of Clip popup after click on the popout button is not verified",
 * YesNo.No); sa.assertTrue(false,
 * "The UI of Clip popup after click on the popout button is not verified"); }
 * if(clickUsingJavaScript(driver, bp.getPopInIconOnpopup(10), "clip pop-in")) {
 * log(LogStatus.INFO, "Clicked on clip pop-in button", YesNo.No);
 * 
 * } else { log(LogStatus.ERROR, "Not able to click on clip pop-in button",
 * YesNo.No); sa.assertTrue(false, "Not able to click on clip pop-in button"); }
 * driver.switchTo().window(parentID); } else { log(LogStatus.ERROR,
 * "Not able to click on clip pop-out button", YesNo.No); sa.assertTrue(false,
 * "Not able to click on clip pop-out button"); }
 * 
 * } else { log(LogStatus.ERROR, "Not able to click on clip button", YesNo.No);
 * sa.assertTrue(false, "Not able to click on clip button"); }
 * 
 * lp.CRMlogout(); ThreadSleep(10000); String parentWindow=null; boolean
 * flag=false; lp.CRMLogin(superAdminUserName, adminPassword, appName); if
 * (home.clickOnSetUpLink()) { flag = true; parentWindow =
 * switchOnWindow(driver); if (parentWindow == null) { sa.assertTrue(false,
 * "No new window is open after click on setup link in lighting mode so cannot create CRM User1"
 * ); log(LogStatus.SKIP,
 * "No new window is open after click on setup link in lighting mode so cannot create CRM User1"
 * , YesNo.Yes);
 * exit("No new window is open after click on setup link in lighting mode so cannot create CRM User1"
 * ); } if(setup.searchStandardOrCustomObject(projectName, mode,
 * object.App_Manager)) { log(LogStatus.INFO,
 * "App Manager object has been open", YesNo.No);
 * if(setup.verifyHieghtandWidthOfClip("340","405")) { log(LogStatus.INFO,
 * "panel height and panel width has been matched of clip", YesNo.No); } else {
 * log(LogStatus.ERROR, "panel height and panel width is not matched of clip",
 * YesNo.No); sa.assertTrue(false,
 * "panel height and panel width is not matched of clip"); } } else {
 * log(LogStatus.ERROR, "App Manager object is not open", YesNo.No);
 * sa.assertTrue(false, "App Manager object is not open"); }
 * 
 * }else{
 * 
 * log(LogStatus.ERROR, "could not click on setup link, test case fail",
 * YesNo.Yes); sa.assertTrue(false,
 * "could not click on setup link, test case fail");
 * 
 * }
 * 
 * driver.close(); driver.switchTo().window(parentWindow);
 * 
 * if (home.clickOnSetUpLink()) { flag = true; parentWindow =
 * switchOnWindow(driver); if (parentWindow == null) { sa.assertTrue(false,
 * "No new window is open after click on setup link in lighting mode so cannot create CRM User1"
 * ); log(LogStatus.SKIP,
 * "No new window is open after click on setup link in lighting mode so cannot create CRM User1"
 * , YesNo.Yes);
 * exit("No new window is open after click on setup link in lighting mode so cannot create CRM User1"
 * ); } if(setup.searchStandardOrCustomObject(projectName, mode,
 * object.Profiles)) { log(LogStatus.INFO, "Profile has been open", YesNo.No);
 * ArrayList<String>
 * result1=setup.verifyAcceddPermissionOfObject(UserProfile.PE_Standard_User,
 * ObjectType.Clips, true, true, true, false); if(result1.isEmpty()) {
 * log(LogStatus.INFO,
 * "The access permission has been verified for Clip object", YesNo.No); } else
 * { log(LogStatus.ERROR,
 * "The access permission is not verified for Clip object", YesNo.No);
 * sa.assertTrue(false,
 * "The access permission is not verified for Clip object"); } } else {
 * log(LogStatus.ERROR, "Profile is not open", YesNo.No); sa.assertTrue(false,
 * "Profile is not open"); }
 * 
 * }else{
 * 
 * log(LogStatus.ERROR, "could not click on setup link, test case fail",
 * YesNo.Yes); sa.assertTrue(false,
 * "could not click on setup link, test case fail");
 * 
 * } driver.close(); driver.switchTo().window(parentWindow); lp.CRMlogout();
 * sa.assertAll(); }
 * 
 * @Parameters({ "projectName"})
 * 
 * @Test public void ACTc003_A_CustonFieldforObject(String projectName) {
 * SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
 * HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
 * BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
 * 
 * 
 * String[][] labelAndValues = {
 * 
 * { AC_FieldType2, AC_FieldLabelName2, null,null, AC_ObjectName2 }, {
 * AC_FieldType1, AC_FieldLabelName1, excelLabel.Length.toString(),
 * AC_FieldLength1, AC_ObjectName1 }};
 * 
 * setup.createFieldsForCustomObject(projectName, labelAndValues);
 * 
 * }
 * 
 * @Parameters({ "projectName"})
 * 
 * @Test public void ACTc003_B_CreateTaskAndCall(String projectName) {
 * SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
 * HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
 * BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
 * 
 * 
 * 
 * 
 * }
 * 
 * 
 * 
 * 
 * }
 */