/**
 * 
 */
package com.navatar.pageObjects;

/**
 * @author Ankur Rana
 *
 */
public interface BasePageErrorMessage {

	public String PendingDisclaimerPopUpMessage="There are disclaimer(s) that need to be accepted before accessing the documents. Please click on the button below to view the disclaimer(s).";
	public String AccessDeniedPopUpMessage = "You are required to accept the disclaimer in order to access this document. Please click on the button below to view the disclaimer.";
	public String BulkDownloadAccessDeniedPopUpMessage = "You are required to accept the disclaimer in order to download document(s).Please click on the button below to view the disclaimer.";
	public String AddFolderInfoIconMessage="Standard: You define contact access; All subfolders will be Standard as well";
	public String AddFolderInfoIconMessage1 = "Common: All contacts with any access to the Workspace have access to this folder;";
	public String AddFolderInfoIconMessage2 = "All subfolders will be Common as well";
	public String AddFolderInfoIconMessage3 = "Shared: You define contact access; All subfolders will be Shared as well";
	public String AddFolderInfoIconMessage4 = "Internal: Only for internal users within your firm; All subfolders will be Internal as well";
	public String YouAreAlreadyRegistered = "You are already registered for Navatar Investor"; 
	public String eightCharactersMessage = "(Use at least 8 characters)";
	public String deleteHeaderMessage = "Confirm Deletion";
	public String deleteTextMessage = "Are you sure you want to delete this Document?";
	public String deleteTaskMessage="Are you sure you want to delete this";
	public String alertMsgWithoutSelectingAFolder="Please Select a folder for search.";
	public String nodataDisplayMsg="No data to display.";
	public String alertMsgWithoutEnteringValue="Please enter a value.";
	public String lessThanTwoChars="Your search term must have 2 or more characters.";
	public String noRecordsToDisplayMsg="No records to display";
	public String noPastActivityMsg1="No past activity.";
	public String noPastActivityMsg2="Past meetings and tasks marked as done show up here.";
	public String noNextActivityMsg1="No next steps";
	public String noNextActivityMsg2="To get things moving, add a task or set up a meeting.";
	public String insufficientPopup1="You do not have permission to edit this information.";
	public String insufficientPopup2="Please contact your System Administrator.";
	public String incomepleteField="Complete this field";
	public String XpathForFundLookUpIconOnNewProjectPopUp= "//img[@title='Fund Lookup (New Window)']";
	public String revertToDefaultError1="Any layout change made will be rolled back to the default setting.";
	public String revertToDefaultError2="Are you sure you want to proceed?";
	public String ReviewTheErrorMsg ="Review the errors on this page.";
	public String RequiredFieldMustBeCompletedMsg ="These required fields must be completed: ";
	public String CompleteThisField ="Complete this field";
	public String listViewUpdated="List view updated";
	public String nextStepsMessage1="No next steps. To get things moving, add a ";
	public String nextStepsMessage2="task";
	public String nextStepsMessage3="or set up a meeting.";
	public String pastActivityMessage1="No past ";
	public String pastActivityMessage2="activity";
	public String pastActivityMessage3=". Past meetings and ";
	public String pastActivityMessage4="tasks";
	public String pastActivityMessage5=" marked as done show up here.";
	public String UpcomingTaskMsg="You have an upcoming Task with";
	public String PastTask="You had a Task";
	public String StrikedText="strikedText";
	public String UpcomingTaskMsg1="has an upcoming Task";
	public String LoggedACall="You logged a call with";
	public String LoggedACall1="logged a call with";
	public String LoggedACallAbout="logged a call about";
	public String YouLoggedACall="You logged a call";
	public String YouLoggedACallAbout="You logged a call about";
	public String UpcomingTaskMsgAbout="You have an upcoming Task about";
	public String YouHadATaskAbout="You had a Task about";
	public String UpcomingTaskMsg2="has an upcoming Task with";
	public String UpcomingTaskMsg3="You have an upcoming Task";
	public String UpcomingTaskMsg4="has an upcoming Task about";
	public String AmericaLosAngelesTimeZone="America/Los_Angeles";
	public String NewZealandTimeZone="Pacific/Auckland";
	
	public String filesName = "Enter one or more research terms";
	public String recordTypeDescription = "Description Record Type";
	public String errorName = "Your search term must have 2 or more characters.";
	public String errorName1 = "No results for";
	
	public static String defaultPhotoText="standard";

	public static String defaultPhotoTextForAdminPhoto="profilephoto";
	public static String titleHighlightColor="RecordType.DeveloperName;Firm_Events:#6464FF,Third_Party_Event:#D2691E;";
	public static String filter="Organizer__c = '<<recordid>>' or recordtype.developername = 'Firm_Events'";
	public static String onclickTitle="Event_Invitees";
	public static String HitASnagMessage="Record Type ID: this ID value isn't valid for the user:";
	public static String ErrorMessage="No item display";
	public static String OldTaskMsg(String user,String contactName,  int otherContactNum) {
		String msg="a Task";
		if (user==null) {
			msg="You had "+msg;
		}
		else {
			msg=user+" had "+msg;
		}
		if (contactName!=null) {
			msg+=" with "+contactName;
			if (otherContactNum>0) {
				msg+= " and "+ otherContactNum + " other";
			}
		}
		
		return msg;
	}
	public static String UpcomingEventMsg(String user,String contactName,  int otherContactNum) {
		String msg="an upcoming Event";
		if (user==null) {
			msg="You have "+msg;
		}
		else {
			msg=user+" has "+msg;
		}
		if (contactName!=null) {
			msg+=" with "+contactName;
			if (otherContactNum>0) {
				msg+= " and "+ otherContactNum + " other";
			}
		}
		
		return msg;
	}
	public static String OldEventMsg(String user,String contactName,  int otherContactNum) {
		String msg="an Event";
		if (user==null) {
			msg="You had "+msg;
		}
		else {
			msg=user+" had "+msg;
		}
		if (contactName!=null) {
			msg+=" with "+contactName;
			if (otherContactNum>0) {
				msg+= " and "+ otherContactNum + " other";
			}
		}
		
		return msg;
	}
	
	public static String UpcomingTaskMsg(String user,String contactName,  int otherContactNum) {
		String msg="an upcoming Task";
		if (user==null) {
			msg="You have "+msg;
		}
		else {
			msg=user+" has "+msg;
		}
		if (contactName!=null) {
			msg+=" with "+contactName;
			if (otherContactNum>0) {
				msg+= " and "+ otherContactNum + " other";
			}
		}
		
		return msg;
	}

	
	public static String invalidImageErrorMsg ="Incorrect file format. Please upload a JPG, JPEG, GIF or PNG file.";
	public static String updatePhotoErrorMsg="You can upload a JPG, JPEG, GIF or PNG file. Maximum file size is 16 MB.";
	public static String currentPhotoTextMsg="Current Photo:";
	
	public static String acuityDefaultMessage="No items to display.";
	
	
}
