package com.navatar.pageObjects;

import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.ExcelUtils;

public abstract class HomePageErrorMessage {
	
	public static String fundraisingContactNoDataToDisplay="No data to display.";
	
	public static String NoRecordToDisplayOnFR;
	static {
		if (ExcelUtils.readDataFromPropertyFile("Mode").equalsIgnoreCase(Mode.Lightning.toString())) {
			NoRecordToDisplayOnFR="No items to display.";
		}else {
			NoRecordToDisplayOnFR="No records to display";
		}
	}

	public static String NoRecordToDisplayOnActivities;
	static {
		if (ExcelUtils.readDataFromPropertyFile("Mode").equalsIgnoreCase(Mode.Lightning.toString())) {
			NoRecordToDisplayOnActivities="No items to display.";
		}else {
			NoRecordToDisplayOnActivities="No records to display";
		}
	}

	public  static String selectCoInvestmentRelatedFundErrorMsg="Please select the Co-Investment Fund defined in Co-Investment settings.";

	public  static String step1_BuildYourOwn="Step 1. Build your own distribution list";
	
	public  static String bulkEmailErrorPopUpMsg="This report does not contain Contact ID. To use this report, please add the Contact ID column in the report. Else, please select another report.";

	public  static String bulkEmailErrorPopUpMsg1="The report must either be in tabular or summary format.";
	
	public  static String Step2_SelectAnEmailTemplate="Step 2. Select an email template";
	
	public  static String step3_ReviewAndConfirm="Step 3. Review and confirm";
	
	public  static String step4_YourEmail="Step 4. Your Emails Have Been Sent Successfully";
	
	public static String InsufficientPermissionErrorMsgOnCreateCommitmentPage1 ="Please select the fundraising associated with the";
	public static String InsufficientPermissionErrorMsgOnCreateCommitmentPage2 ="Co-Investment Fund defined in Co-Investment settings.";
	
	
}
