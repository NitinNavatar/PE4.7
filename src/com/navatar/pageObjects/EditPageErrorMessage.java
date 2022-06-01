/**
 * 
 */
package com.navatar.pageObjects;

import com.navatar.generic.CommonVariables;

/**
 * @author Ankur Rana
 *
 */
public interface EditPageErrorMessage {

	public String RecordInstitutionDirectCommitmentsParentAccount="Record Institution Direct Commitments Parent Account";
	
	public String EnhancedLightningGrid = "Enhanced Lightning Grid";
	public String NavatarSDGToggles = "Navatar SDG Toggles";
	public String NavatarSDG = "Navatar SDG";
	public String RelatedLists = "Related Lists";
	public String noOfRecordsLimit="This value must be at least 1 and at most 30.";
	public String noOfRecordsError="This field is required.";
	public String noOfRecordsErrorAccordion="Component 'RelatedListAccordion' has an invalid value for property 'Number of Records to Display'.";
	public String attendeeQuery="SELECT navpeII__Attendee_Staff__c, navpeII__Attendee_Staff__r.Name, navpeII__Status__c, Id,navpeII__Attendee_Staff__r.MediumPhotoUrl FROM navpeII__Attendee__c WHERE Marketing_Event__r.Id = '<<recordId>>' ORDER BY Name ASC";
	public String query1="SELECT Id, Name, Profile_Image__c FROM navpeII__Marketing_Event__c  ORDER BY Name ASC";
	public String query3="SELECT navpeII__Team_Member__c, navpeII__Team_Member__r.Name,navpeII__Team_Member__r.Title,navpeII__Team_Member_Role__c,navpeII__Team_Member__r.MediumPhotoUrl FROM navpeII__Deal_Team__c  ORDER BY Id ASC";
	public String query2="SELECT Id, Name, Profile_Image__c FROM navpeII__Pipeline__c  ORDER BY Name ASC";
	public String image1="navpeII__Profile_Image__c";
	public String image3="navpeII__Team_Member__r.MediumPhotoUrl";
	public String AttendeeImage="navpeII__Attendee_Staff__r.MediumPhotoURL";
	
	
	
	public String image2="Profile_Image__c";
	//Navatar SDG Toggles
	
	public String RecordDealsQARequestsClosed="Record_Deals_QA_Requests_Closed";
	
	public String EntityUpcomingEventSDG="Record_Entity_Entity's_Upcoming Third Party Events";
	public String EntityEventInviteeSDG="Record Entity Events Our Event Invitees";
	
	public String EntityContactListSDG="Record_Accounts_ContactList";
	public String EntityAffiliationAllRolesListSDG="Record_Affiliation_List_All_Roles_Account";
	
	public String DealOpenQuestion="Record_Deals_QA_Requests_Open";
	public String DealClosed="Record_Deals_QA_Requests_Closed";
	
	public String MarketingEventThirdPartyEvent=CommonVariables.M5Sdg2Name;
	public String MarketingEventOurEvent=CommonVariables.M5Sdg3Name;
	
	
	

	//Record_Deals_QA_Requests_Closed
	
}
