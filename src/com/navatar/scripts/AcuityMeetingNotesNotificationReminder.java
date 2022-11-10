/*
 * package com.navatar.scripts;
 * 
 * import static com.navatar.generic.CommonLib.ThreadSleep; import static
 * com.navatar.generic.CommonLib.convertDateFromOneFormatToAnother; import
 * static com.navatar.generic.CommonLib.log; import static
 * com.navatar.generic.CommonLib.refresh; import static
 * com.navatar.generic.CommonLib.switchOnWindow; import static
 * com.navatar.generic.CommonLib.switchToDefaultContent; import static
 * com.navatar.generic.CommonVariables.AS_ATActivityType15; import static
 * com.navatar.generic.CommonVariables.AS_ATActivityType16; import static
 * com.navatar.generic.CommonVariables.AS_ATNotes15; import static
 * com.navatar.generic.CommonVariables.AS_ATNotes16; import static
 * com.navatar.generic.CommonVariables.AS_ATRelatedTo15; import static
 * com.navatar.generic.CommonVariables.AS_ATRelatedTo16; import static
 * com.navatar.generic.CommonVariables.AS_ATSubject15; import static
 * com.navatar.generic.CommonVariables.AS_ATSubject16; import static
 * com.navatar.generic.CommonVariables.AS_ContactEmail12; import static
 * com.navatar.generic.CommonVariables.AS_ContactFirstName12; import static
 * com.navatar.generic.CommonVariables.AS_ContactLastName12; import static
 * com.navatar.generic.CommonVariables.AS_DealCompany5; import static
 * com.navatar.generic.CommonVariables.AS_DealName5; import static
 * com.navatar.generic.CommonVariables.AS_DealRecordType4; import static
 * com.navatar.generic.CommonVariables.AS_DealRecordType5; import static
 * com.navatar.generic.CommonVariables.AS_DealStage5; import static
 * com.navatar.generic.CommonVariables.AS_FirmLabelNames17; import static
 * com.navatar.generic.CommonVariables.AS_FirmLabelValues17; import static
 * com.navatar.generic.CommonVariables.AS_FirmLegalName17; import static
 * com.navatar.generic.CommonVariables.AS_FirmRecordType17; import static
 * com.navatar.generic.CommonVariables.AS_FundInvestmentCategory4; import static
 * com.navatar.generic.CommonVariables.AS_FundName4; import static
 * com.navatar.generic.CommonVariables.AS_FundRecordType3; import static
 * com.navatar.generic.CommonVariables.AS_FundRecordType4; import static
 * com.navatar.generic.CommonVariables.AS_FundType4; import static
 * com.navatar.generic.CommonVariables.AS_FundraisingClosingDate3; import static
 * com.navatar.generic.CommonVariables.AS_FundraisingLegalName3; import static
 * com.navatar.generic.CommonVariables.AS_FundraisingName3; import static
 * com.navatar.generic.CommonVariables.AS_FundraisingRecordType2; import static
 * com.navatar.generic.CommonVariables.AS_FundraisingRecordType3; import static
 * com.navatar.generic.CommonVariables.AS_FundraisingStageName3; import static
 * com.navatar.generic.CommonVariables.crmUser1EmailID; import static
 * com.navatar.generic.CommonVariables.environment; import static
 * com.navatar.generic.CommonVariables.mode; import static
 * com.navatar.generic.CommonVariables.superAdminUserName; import static
 * com.navatar.generic.CommonVariables.tabObj1; import static
 * com.navatar.generic.CommonVariables.tabObj4; import static
 * com.navatar.generic.SmokeCommonVariables.adminPassword;
 * 
 * import org.testng.annotations.Test;
 * 
 * import com.navatar.generic.BaseLib; import com.navatar.generic.CommonLib;
 * import com.navatar.generic.EnumConstants.CreationPage; import
 * com.navatar.generic.EnumConstants.Mode; import
 * com.navatar.generic.EnumConstants.ObjectFeatureName; import
 * com.navatar.generic.EnumConstants.TabName; import
 * com.navatar.generic.EnumConstants.YesNo; import
 * com.navatar.generic.EnumConstants.action; import
 * com.navatar.generic.EnumConstants.object; import
 * com.navatar.generic.EnumConstants.recordTypeLabel; import
 * com.navatar.pageObjects.BasePageBusinessLayer; import
 * com.navatar.pageObjects.ContactsPageBusinessLayer; import
 * com.navatar.pageObjects.DealPageBusinessLayer; import
 * com.navatar.pageObjects.FundRaisingPageBusinessLayer; import
 * com.navatar.pageObjects.FundsPageBusinessLayer; import
 * com.navatar.pageObjects.HomePageBusineesLayer; import
 * com.navatar.pageObjects.InstitutionsPageBusinessLayer; import
 * com.navatar.pageObjects.LoginPageBusinessLayer; import
 * com.navatar.pageObjects.SetupPageBusinessLayer; import
 * com.relevantcodes.extentreports.LogStatus;
 * 
 * public class AcuityMeetingNotesNotificationReminder extends BaseLib {
 * 
 * @Test public void AcuitySmokeTc028_CreateRecordTypeAndData(String
 * projectName) {
 * 
 * LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
 * HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
 * InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
 * ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
 * DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
 * FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
 * FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);
 * BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
 * 
 * String[] institutionName = AS_FirmLegalName17.split("<Break>", -1); String[]
 * recordType = AS_FirmRecordType17.split("<Break>", -1); String[]
 * labelsOfFirmPopUp = AS_FirmLabelNames17.split("<Break>", -1); String[]
 * valuesOfFirmPopUp = AS_FirmLabelValues17.split("<Break>", -1);
 * 
 * String[] contactFirstNames = AS_ContactFirstName12.split("<Break>", -1);
 * String[] contactLastNames = AS_ContactLastName12.split("<Break>", -1);
 * String[] contactEmailIds = AS_ContactEmail12.split("<Break>", -1);
 * 
 * String[] dealRecordTypes = AS_DealRecordType5.split("<Break>", -1); String[]
 * dealName = AS_DealName5.split("<Break>", -1); String[] dealCompany =
 * AS_DealCompany5.split("<Break>", -1); String[] dealStage =
 * AS_DealStage5.split("<Break>", -1);
 * 
 * String[] fundNames = AS_FundName4.split("<Break>", -1); String[]
 * fundRecordTypes = AS_FundRecordType4.split("<Break>", -1); String[] fundTypes
 * = AS_FundType4.split("<Break>", -1); String[] investmentCategories =
 * AS_FundInvestmentCategory4.split("<Break>", -1); String otherLabelFields =
 * null; String otherLabelValues = null;
 * 
 * String[] ClosingDates = AS_FundraisingClosingDate3.split("<Break>", -1);
 * String[] fundraisingRecordTypes = AS_FundraisingRecordType3.split("<Break>",
 * -1); String[] fundraisingNames = AS_FundraisingName3.split("<Break>", -1);
 * String[] fundraisingsFundName = fundNames; String[]
 * fundraisingsInstitutionName = AS_FundraisingLegalName3.split("<Break>", -1);
 * String[] fundraisingsStageName = AS_FundraisingStageName3.split("<Break>",
 * -1); String[] fundraisingsClosingDate = new String[ClosingDates.length];
 * 
 * int loop = 0; for (String ClosingDate : ClosingDates) {
 * fundraisingsClosingDate[loop] =
 * convertDateFromOneFormatToAnother(ClosingDate, "MM/dd/yyyy", "dd/MMM/yyyy");
 * loop++; }
 * 
 * // String val=AS_ATDay1; String AdvanceDueDate =
 * CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy",
 * Integer.parseInt("10")); // ExcelUtils.writeData(AcuityDataSheetFilePath,
 * AdvanceDueDate, "Activity Timeline" , excelLabel.Variable_Name, "AT_001",
 * excelLabel.Advance_Due_Date);
 * 
 * String[][] task1BasicSection = { { "Subject", AS_ATSubject15 }, { "Notes",
 * AS_ATNotes15 }, { "Related_To", AS_ATRelatedTo15 } };
 * 
 * String[][] task1AdvancedSection = { { "Due Date Only", AdvanceDueDate } };
 * 
 * String[][] task2BasicSection = { { "Subject", AS_ATSubject16 }, { "Notes",
 * AS_ATNotes16 }, { "Related_To", AS_ATRelatedTo16 } };
 * 
 * String[][] task2AdvancedSection = { { "Due Date Only", AdvanceDueDate } };
 * 
 * lp.CRMLogin(crmUser1EmailID, adminPassword);
 * 
 * int firmLoopCount = 0; log(LogStatus.INFO, "---------Now Going to Create " +
 * tabObj1 + "---------", YesNo.No); home.notificationPopUpClose(); for (String
 * instName : institutionName) { if (lp.clickOnTab(projectName, tabObj1)) {
 * log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
 * 
 * if (ip.createInstitution(projectName, environment, mode, instName,
 * recordType[firmLoopCount], labelsOfFirmPopUp[firmLoopCount],
 * valuesOfFirmPopUp[firmLoopCount]))
 * 
 * { log(LogStatus.INFO, "successfully Created Account/Entity : " + instName +
 * " of record type : " + recordType[firmLoopCount], YesNo.No); } else {
 * sa.assertTrue(false, "Not Able to Create Account/Entity : " + instName +
 * " of record type : " + recordType[firmLoopCount]); log(LogStatus.SKIP,
 * "Not Able to Create Account/Entity : " + instName + " of record type : " +
 * recordType[firmLoopCount], YesNo.Yes); } } else { sa.assertTrue(false,
 * "Not Able to Click on Tab : " + tabObj1); log(LogStatus.SKIP,
 * "Not Able to Click on Tab : " + tabObj1, YesNo.Yes); } ThreadSleep(2000);
 * firmLoopCount++; }
 * 
 * log(LogStatus.INFO, "---------Now Going to Create " + TabName.Object2Tab +
 * "---------", YesNo.No); int contactLoopCount = 0; for (String
 * contactFirstName : contactFirstNames) { if (lp.clickOnTab(projectName,
 * TabName.Object2Tab)) { log(LogStatus.INFO, "Click on Tab : " +
 * TabName.Object2Tab, YesNo.No);
 * 
 * if (cp.createContact(projectName, contactFirstName,
 * contactLastNames[contactLoopCount], institutionName[contactLoopCount],
 * contactEmailIds[contactLoopCount], "", null, null, CreationPage.ContactPage,
 * null, null)) { log(LogStatus.INFO, "Successfully Created Contact : " +
 * contactFirstName + " " + contactLastNames[contactLoopCount], YesNo.No); }
 * 
 * else { sa.assertTrue(false, "Not Able to Create Contact : " +
 * contactFirstName + " " + contactLastNames[contactLoopCount]);
 * log(LogStatus.SKIP, "Not Able to Create Contact : " + contactFirstName + " "
 * + contactLastNames[contactLoopCount], YesNo.Yes); }
 * 
 * } else { sa.assertTrue(false, "Not Able to Click on Tab : " +
 * TabName.Object2Tab); log(LogStatus.SKIP, "Not Able to Click on Tab : " +
 * TabName.Object2Tab, YesNo.Yes); } ThreadSleep(2000); contactLoopCount++; }
 * 
 * log(LogStatus.INFO, "---------Now Going to Create " + tabObj4 + "---------",
 * YesNo.No); for (int i = 0; i < dealName.length; i++) { if
 * (lp.clickOnTab(projectName, tabObj4)) { log(LogStatus.INFO, "Click on Tab : "
 * + tabObj4, YesNo.No); ThreadSleep(3000); if
 * (dp.createDeal(dealRecordTypes[i], dealName[i], dealCompany[i], dealStage[i],
 * null, null)) { log(LogStatus.INFO, dealName[i] + " deal has been created",
 * YesNo.No);
 * 
 * } else { log(LogStatus.ERROR, dealName[i] + " deal is not created",
 * YesNo.No); sa.assertTrue(false, dealName[i] + " deal is not created"); } }
 * else { log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab",
 * YesNo.No); sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
 * } }
 * 
 * int fundStatus = 0; int fundLoopCount = 0; for (String fundName : fundNames)
 * {
 * 
 * log(LogStatus.INFO, "---------Now Going to Create Fund Named: " + fundName +
 * "---------", YesNo.No); if (fund.clickOnTab(environment, mode,
 * TabName.FundsTab)) {
 * 
 * if (fund.createFund(projectName, fundRecordTypes[fundLoopCount], fundName,
 * fundTypes[fundLoopCount], investmentCategories[fundLoopCount],
 * otherLabelFields, otherLabelValues)) {
 * appLog.info("Fund is created Successfully: " + fundName); fundStatus++;
 * 
 * } else { appLog.error("Not able to click on fund: " + fundName);
 * sa.assertTrue(false, "Not able to click on fund: " + fundName);
 * log(LogStatus.ERROR, "Not able to click on fund: " + fundName, YesNo.Yes); }
 * } else { appLog.error("Not able to click on Fund tab so cannot create Fund: "
 * + fundName); sa.assertTrue(false,
 * "Not able to click on Fund tab so cannot create Fund: " + fundName); }
 * ThreadSleep(2000); fundLoopCount++;
 * 
 * }
 * 
 * if (fundStatus == fundLoopCount) { int fundraisingLoopCount = 0; for (String
 * fundraisingName : fundraisingNames) { log(LogStatus.INFO,
 * "---------Now Going to Create Fundraising Named: " + fundraisingName +
 * "---------", YesNo.No); if (BP.clickOnTab(environment, mode,
 * TabName.FundraisingsTab)) {
 * 
 * String[] targetClosingCompleteDate =
 * fundraisingsClosingDate[fundraisingLoopCount].split("/"); String
 * targetClosingDate = targetClosingCompleteDate[0]; String targetClosingMonth =
 * targetClosingCompleteDate[1]; String targetClosingYear =
 * targetClosingCompleteDate[2];
 * 
 * if (fr.createFundRaising(environment, "Lightning",
 * fundraisingRecordTypes[fundraisingLoopCount], fundraisingName,
 * fundraisingsFundName[fundraisingLoopCount],
 * fundraisingsInstitutionName[fundraisingLoopCount], null,
 * fundraisingsStageName[fundraisingLoopCount], null, null, targetClosingYear,
 * targetClosingMonth, targetClosingDate)) {
 * appLog.info("fundraising is created : " + fundraisingName); } else {
 * appLog.error("Not able to create fundraising: " + fundraisingName);
 * sa.assertTrue(false, "Not able to create fundraising: " + fundraisingName); }
 * 
 * } else { appLog.error(
 * "Not able to click on fundraising tab so cannot create fundraising: " +
 * fundraisingName); sa.assertTrue(false,
 * "Not able to click on fundraising tab so cannot create fundraising: " +
 * fundraisingName); } ThreadSleep(2000);
 * 
 * fundraisingLoopCount++;
 * 
 * }
 * 
 * } else {
 * appLog.error("No Fund is created, So not able to Create Fundraising: " +
 * fundraisingNames); sa.assertTrue(false,
 * "No Fund is created, So not able to Create Fundraising: " +
 * fundraisingNames); }
 * 
 * log(LogStatus.INFO, "---------Now Going to Create Task: " + AS_ATSubject15 +
 * " in Activity Timeline Section---------", YesNo.No);
 * CommonLib.refresh(driver); if (lp.clickOnTab(projectName, TabName.HomeTab)) {
 * log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
 * home.notificationPopUpClose(); if (BP.createActivityTimeline(projectName,
 * true, AS_ATActivityType15, task1BasicSection, task1AdvancedSection, null,
 * null)) { log(LogStatus.PASS, "Activity timeline record has been created",
 * YesNo.No);
 * 
 * ThreadSleep(4000); if (CommonLib.getText(driver, BP.taskDetailPageHeader(15),
 * "Task: " + AS_ATSubject15, action.BOOLEAN) .equalsIgnoreCase(AS_ATSubject15))
 * { log(LogStatus.PASS, "-----Task Detail Page: " + AS_ATSubject15 +
 * " has been open after Task Create-----", YesNo.No); }
 * 
 * else { log(LogStatus.FAIL, "-----Task Detail Page: " + AS_ATSubject15 +
 * " has not been open after Task Create-----", YesNo.No); sa.assertTrue(false,
 * "-----Task Detail Page: " + AS_ATSubject15 +
 * " has not been open after Task Create-----"); }
 * 
 * } else { log(LogStatus.FAIL, "Activity timeline record is not created",
 * YesNo.No); sa.assertTrue(false, "Activity timeline record is not created"); }
 * 
 * } else { sa.assertTrue(false, "Not Able to Click on Tab : " +
 * TabName.HomeTab); log(LogStatus.SKIP, "Not Able to Click on Tab : " +
 * TabName.HomeTab, YesNo.Yes); }
 * 
 * ThreadSleep(5000); lp.CRMlogout(); ThreadSleep(5000);
 * 
 * lp.CRMLogin(superAdminUserName, adminPassword);
 * 
 * log(LogStatus.INFO, "---------Now Going to Create Task: " + AS_ATSubject16 +
 * " in Activity Timeline Section---------", YesNo.No);
 * CommonLib.refresh(driver);
 * 
 * home.notificationPopUpClose(); if (BP.createActivityTimeline(projectName,
 * true, AS_ATActivityType16, task2BasicSection, task2AdvancedSection, null,
 * null)) { log(LogStatus.PASS, "Activity timeline record has been created",
 * YesNo.No);
 * 
 * ThreadSleep(4000); if (CommonLib.getText(driver, BP.taskDetailPageHeader(15),
 * "Task: " + AS_ATSubject16, action.BOOLEAN) .equalsIgnoreCase(AS_ATSubject16))
 * { log(LogStatus.PASS, "-----Task Detail Page: " + AS_ATSubject16 +
 * " has been open after Task Create-----", YesNo.No); }
 * 
 * else { log(LogStatus.FAIL, "-----Task Detail Page: " + AS_ATSubject16 +
 * " has not been open after Task Create-----", YesNo.No); sa.assertTrue(false,
 * "-----Task Detail Page: " + AS_ATSubject16 +
 * " has not been open after Task Create-----"); }
 * 
 * } else { log(LogStatus.FAIL, "Activity timeline record is not created",
 * YesNo.No); sa.assertTrue(false, "Activity timeline record is not created"); }
 * 
 * ThreadSleep(5000); lp.CRMlogout(); sa.assertAll(); }
 * 
 * }
 */