
package com.navatar.scripts;

import static com.navatar.generic.CommonLib.ThreadSleep;

import static com.navatar.generic.CommonLib.log;

import static com.navatar.generic.CommonVariables.*;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;

import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;


import com.navatar.pageObjects.BasePageBusinessLayer;

import com.navatar.pageObjects.HomePageBusineesLayer;

import com.navatar.pageObjects.LoginPageBusinessLayer;

import com.relevantcodes.extentreports.LogStatus;

public class AcuityMeetingNotesNotificationReminder extends BaseLib {

	@Parameters({ "projectName" })

	@Test
	public void AcuityMNNRTc044_CreateATaskAndAddTheNotesFromEditCommentButtonOfTaskLayout(String projectName) {

		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);

		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);

		// String val=AS_ATDay1;
		String AdvanceDueDate = CommonLib.getFutureDateAccToTimeZone("GMT+5:30", "MM/dd/yyyy", Integer.parseInt("1"));
		/*
		 * // ExcelUtils.writeData(AcuityDataSheetFilePath, AdvanceDueDate,
		 * "Activity Timeline" , excelLabel.Variable_Name, "AT_001",
		 * excelLabel.Advance_Due_Date);
		 */

		String task1SubjecName = "Send Invoice";
		String task1Notes = "";

		String[][] task1BasicSection = { { "Subject", task1SubjecName }, { "Notes", task1Notes },
				{ "Related_To", "Con 1<break>con 2<break>con 3<break>Sumo Logic<break>Houlihan Lokey<break>Vertica" } };

		String[][] task1AdvancedSection = { { "Due Date Only", AdvanceDueDate },
				{ "Assigned To ID", crmUser1FirstName + " " + crmUser1LastName }, { "Status", "Not started" },
				{ "Priority", "Normal" } };
		String task1ButtonName = "Task";
		String tabName = "Sumo Logic";

		
		
		
		lp.CRMLogin(crmUser1EmailID, adminPassword);

		log(LogStatus.INFO,
				"---------Now Going to Create Task: " + task1SubjecName + " in Activity Timeline Section---------",
				YesNo.No);
		CommonLib.refresh(driver);
		if (lp.clickOnTab(projectName, TabName.HomeTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.HomeTab, YesNo.No);
			home.notificationPopUpClose();
			if (BP.createActivityTimeline(projectName, true, task1ButtonName, task1BasicSection, task1AdvancedSection,
					null, null)) {
				log(LogStatus.PASS, "Activity timeline record has been created", YesNo.No);

				
				
				
				
				
				
				/*
				 * if (lp.clickOnTab(projectName, tabObj1)) {
				 * 
				 * log(LogStatus.INFO, "Clicked on Tab : " + tabObj1, YesNo.No);
				 * 
				 * if (BP.clickOnAlreadyCreated_Lighting(environment, mode,
				 * TabName.InstituitonsTab, tabName, 30)) { log(LogStatus.INFO, tabName +
				 * " record has been open", YesNo.No); ThreadSleep(4000); if
				 * (BP.clicktabOnPage("Acuity")) { log(LogStatus.INFO, "clicked on Acuity tab",
				 * YesNo.No); ArrayList<String> result =
				 * BP.verifyRecordOnInteractionCard(AdvanceDueDate, task1SubjecName, task1Notes,
				 * true, false, null); if (result.isEmpty()) { log(LogStatus.PASS, subjectName +
				 * " record has been verified on intraction", YesNo.No); sa.assertTrue(true,
				 * subjectName + " record has been verified on intraction"); } else {
				 * log(LogStatus.ERROR, subjectName + " record is not verified on intraction",
				 * YesNo.No); sa.assertTrue(false, subjectName +
				 * " record is not verified on intraction"); } } else { log(LogStatus.ERROR,
				 * "Not able to click on Acuity Tab", YesNo.No); sa.assertTrue(false,
				 * "Not able to click on Acuity Tab"); }
				 * 
				 * } else { log(LogStatus.ERROR, "Not able to open " + AS_FirmLegalName5 +
				 * " reocrd", YesNo.No); sa.assertTrue(false, "Not able to open " +
				 * AS_FirmLegalName5 + " reocrd"); } } else { log(LogStatus.ERROR,
				 * "Not able to click on Tab : " + tabObj1, YesNo.No); sa.assertTrue(false,
				 * "Not able to click on Tab : " + tabObj1); }
				 */
				
				
				
				
				
				
				
				
				
				

			} else {
				log(LogStatus.FAIL, "Activity timeline record is not created", YesNo.No);
				sa.assertTrue(false, "Activity timeline record is not created");
			}

		} else {
			sa.assertTrue(false, "Not Able to Click on Tab : " + TabName.HomeTab);
			log(LogStatus.SKIP, "Not Able to Click on Tab : " + TabName.HomeTab, YesNo.Yes);
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

}
