package com.navatar.pageObjects;

import com.navatar.generic.SmokeCommonVariables;

public interface MarketingInitiativesPageErrorMsg {
	
	public static String selectAReportsErrorMsg="Please select a report.";
	public static String contactIDMissingErrorMsg="This report does not contain Contact ID. To use this report, please add the Contact ID column in the report. Else, please select another report";
	
	public static String removeContactConfirmationErrorMsg(String NoOFContact) {
		return	NoOFContact+" contact(s) removed successfully.";
	}
	
	public static String selectRecipientOnStep3ErrorMsg(String noOfRecipients) {
		return "You currently have "+noOfRecipients+" recipient(s) selected to receive this email.";
	}
	
	public static String congratulationErrorMsg="Congratulations!";
	public static String generatedEmailedRecipientErrorMsg(String noOfRecipients, String pageName) {
		if (pageName.contains("Bulk")) {
			return "Your emails have been generated and emailed to the "+noOfRecipients+" recipients you specified.";	
		} else {
			return "Your emails have been generated and emailed to the "+noOfRecipients+" recipient(s) you specified.";
		}
		
	}

	public static String reportDoestNotExistErrorMsg="Report does not exist.";
	public static String enterAVAlueErrorMsg="Please Enter a value .";
	
	//**************************************Email Template Body Text****************************************//
	
	// Captial Call Notice of Commitment Email Template Error Message................................
	
	public static String companyName=SmokeCommonVariables.OrganizationName;
	
	public static String salutation(String ContactFirstName) {
		return "Dear "+ContactFirstName;
	}
	public static String ErrorMsg1="In accordance with Section 8.5 of the  Limited Partnership Agreement, the General Partner is hereby calling capital equal to % of 's commitment. This brings total capital called to % of your total capital commitment.";
	public static String ErrorMsg2="Your total commitment to the partnership is ; therefore, unless you have notified us of new bank account information, the bank account you designated in your subscription documents will be debited on  for .";
	public static String ErrorMsg3="If you have any questions regarding the amount of your capital call or the account from which you capital will be debited, please email us at abc@xyz.com";
	public static String Sincerely="Sincerely";
	public static String SelectMarketingInitiativeErrorMessage = "Please select a Marketing Initiative.";
	public static String SelectASearchCriteriaMessage = "Please select a search criteria.";
	public static String pleaseSelectAtemplate = "Please select a template.";
}
