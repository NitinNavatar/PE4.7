package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;

import org.testng.annotations.*;

import com.navatar.generic.*;

import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;

import com.navatar.pageObjects.*;

import com.relevantcodes.extentreports.*;

public class AcuityPhase2NotesTagging extends BaseLib {

	@Parameters({ "projectName" })

	@Test
	public void AcuityPhase2NTTc002_CreateTheRecordsFromDataSheetAndVerifyInTheOrg(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		FundsPageBusinessLayer fund = new FundsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);

		String[] fundNames = AP2NT_FundNames1.split("<Break>", -1);
		String[] fundTypes = AP2NT_FundTypes1.split("<Break>", -1);
		String[] investmentCategories = AP2NT_FundInvestmentCategories1.split("<Break>", -1);
		String otherLabelFields = null;
		String otherLabelValues = null;

		String[] fundraisingNames = AP2NT_FundraisingNames1.split("<Break>", -1);
		String[] fundraisingsFundName = AP2NT_FundraisingFundName1.split("<Break>", -1);
		String[] fundraisingsInstitutionName = AP2NT_FundraisingInstitutionName1.split("<Break>", -1);

		String dealRecordTypes = null;
		String[] dealName = AP2NT_DealName1.split("<Break>", -1);
		String[] dealCompany = AP2NT_DealCompany1.split("<Break>", -1);
		String[] dealStage = AP2NT_DealStage1.split("<Break>", -1);
		String[] dealOtherLabelFields = AP2NT_DealOtherLabelName1.split("<Break>", -1);
		String[] dealOtherLabelValues = AP2NT_DealOtherLabelValue1.split("<Break>", -1);

		String tabName = AP2NT_CustomObjectTab1;
		String textBoxRecordLabel = AP2NT_CustomObjectField1;
		String[] textBoxRecordNames = AP2NT_CustomObjectRecord1.split("<Break>", -1);

		String[][] contacts = {
				{ AP2NT_Con1FirstName, AP2NT_Con1LastName, AP2NT_Con1InstitutionName, AP2NT_Con1ContactEmail,
						AP2NT_Con1OtherLabelsNames, AP2NT_Con1OtherLabelsValues },
				{ AP2NT_Con2FirstName, AP2NT_Con2LastName, AP2NT_Con2InstitutionName, AP2NT_Con2ContactEmail,
						AP2NT_Con2OtherLabelsNames, AP2NT_Con2OtherLabelsValues } };

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		// contact
		for (String[] contact : contacts) {

			if (BP.clickOnTab(environment, mode, TabName.ContactTab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.ContactTab, YesNo.No);

				String firstName = "";
				String lastName = "";
				String legalName = "";
				String email = "";
				String contactOtherLabelNames = "";
				String contactOtherLabelValues = "";
				firstName = contact[0];
				lastName = contact[1];
				legalName = contact[2];
				email = contact[3];
				contactOtherLabelNames = contact[4];
				contactOtherLabelValues = contact[5];
				log(LogStatus.INFO, "---------Now Going to Create " + TabName.ContactTab + " : " + firstName + " "
						+ lastName + "---------", YesNo.No);
				if (cp.createContact(projectName, firstName, lastName, legalName, email, "", contactOtherLabelNames,
						contactOtherLabelValues, CreationPage.ContactPage, null, null)) {
					log(LogStatus.INFO, "successfully Created Contact : " + firstName + " " + lastName, YesNo.No);
				} else {
					sa.assertTrue(false, "Not Able to Create Contact : " + firstName + " " + lastName);
					log(LogStatus.SKIP, "Not Able to Create Contact: " + firstName + " " + lastName, YesNo.Yes);
				}

			} else {
				sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.ContactTab);
				log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.ContactTab, YesNo.Yes);
			}

		}

		int fundStatus = 0;
		int fundLoopCount = 0;
		for (String fundName : fundNames) {

			log(LogStatus.INFO, "---------Now Going to Create Fund Named: " + fundName + "---------", YesNo.No);
			if (fund.clickOnTab(environment, mode, TabName.FundsTab)) {

				if (fund.createFund(projectName, fundName, fundTypes[fundLoopCount],
						investmentCategories[fundLoopCount], otherLabelFields, otherLabelValues)) {
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

		if (fundStatus == fundLoopCount) {
			int fundraisingLoopCount = 0;
			for (String fundraisingName : fundraisingNames) {
				log(LogStatus.INFO, "---------Now Going to Create Fundraising Named: " + fundraisingName + "---------",
						YesNo.No);
				if (BP.clickOnTab(environment, mode, TabName.FundraisingsTab)) {

					if (fr.createFundRaising(environment, "Lightning", fundraisingName,
							fundraisingsFundName[fundraisingLoopCount],
							fundraisingsInstitutionName[fundraisingLoopCount], null, null, null, null, null, null,
							null)) {
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

		for (int i = 0; i < dealName.length; i++) {

			log(LogStatus.INFO, "---------Now Going to Create Deal Named: " + dealName[i] + "---------", YesNo.No);
			if (lp.clickOnTab(projectName, tabObj4)) {
				log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
				ThreadSleep(3000);
				if (dp.createDeal(dealRecordTypes, dealName[i], dealCompany[i], dealStage[i], dealOtherLabelFields[i],
						dealOtherLabelValues[i])) {
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

		for (String textBoxRecordName : textBoxRecordNames) {

			log(LogStatus.INFO,
					"---------Now Going to Create Custom Object Record Named: " + textBoxRecordName + "---------",
					YesNo.No);
			if (BP.createRecordForCustomObject(projectName, tabName, textBoxRecordLabel, textBoxRecordName)) {
				log(LogStatus.INFO, "Record: " + textBoxRecordName + " has been Created under: " + tabName, YesNo.No);
			} else {
				log(LogStatus.ERROR, "Record: " + textBoxRecordName + " has not been Created under: " + tabName,
						YesNo.No);
				sa.assertTrue(false, "Record: " + textBoxRecordName + " has not been Created under: " + tabName);
			}
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })

	@Test
	public void AcuityPhase2NTTc002_VerifyTheUIOfNotePopupWhenClickedOnTaskButtonNavigatedFromActionDropDown(
			String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		String task1SubjectName = "";
		String task1Notes = "";

		String getAdvanceDueDate = "";
		String priority = "Normal";
		String status = "Not Started";

		String taskSectionSubject = "";
		String taskSectionStatus = "Not Started";
		String taskSectionDueDateOnly = "";

		String[][] task1BasicSection = { { "Subject", task1SubjectName }, { "Notes", task1Notes } };

		String[][] task1AdvancedSection = { { "Due Date Only", getAdvanceDueDate }, { "Status", status },
				{ "Priority", priority } };

		String[][] task1TaskSection = { { "Subject", taskSectionSubject },
				{ "User", crmUser1FirstName + " " + crmUser1LastName }, { "Status", taskSectionStatus },
				{ "Due Date Only", taskSectionDueDateOnly } };

		String recordPageButtonName = "";

		String recordName = AMNNR_FirmLegalName1;
		String url = "";

		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO, "---------Now Going to Verify UI of Task: " + task1SubjectName
				+ " in Activity Timeline Section---------", YesNo.No);

		if (BP.navigateToRecordAndClickOnSubTab(projectName, tabObj1, recordName, null)) {
			log(LogStatus.INFO, "Able to Open the Record: " + recordName, YesNo.No);

			url = getURL(driver, 10);
			if (BP.clickOnRecordPageButtonForNewRecordCreation(recordPageButtonName, 30)) {
				log(LogStatus.INFO, "Clicked on Button: " + recordPageButtonName + " of Record Page: " + recordName,
						YesNo.No);
				BP.verifyUIOfTaskPopUp(url, task1BasicSection, task1AdvancedSection, task1TaskSection);

			} else {
				log(LogStatus.ERROR,
						"Not able to Click on Button: " + recordPageButtonName + " of Record Page: " + recordName,
						YesNo.No);
				sa.assertTrue(false,
						"Not able to Click on Button: " + recordPageButtonName + " of Record Page: " + recordName);
			}

		} else {
			log(LogStatus.ERROR, "Not able to Open the Record: " + recordName, YesNo.No);
			sa.assertTrue(false, "Not able to Open the Record: " + recordName);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

}
