/**
 * 
 */
package com.navatar.pageObjects;

/**
 * @author Ankur Rana
 *
 */
public interface EditPageErrorMessage {

	public String RecordInstitutionDirectCommitmentsParentAccount="Record Institution Direct Commitments Parent Account";
	
	public String EnhancedLightningGrid = "Enhanced Lightning Grid";
	public String NavatarSDGToggles = "Navatar SDG Toggles";
	public String noOfRecordsLimit="This value must be at least 1 and at most 30.";
	public String noOfRecordsError="Component 'RelatedListAccordion' has an invalid value for property 'Number of Records to Display'.";
	public String attendeeQuery="SELECT Attendee_Staff__c, Attendee_Staff__r.Name, Status__c, Id,Attendee_Staff__r.MediumPhotoURL FROM Attendee__c WHERE Marketing_Event__r.Id = '<<recordId>>' ORDER BY Name ASC";
	public String query1="SELECT Id, Name, Profile_Image__c FROM Marketing_Event__c  ORDER BY Name ASC";
	public String query3="SELECT Member__c, Member__r.Name,Member__r.Title,Team_Member_Role__c,Member__r.MediumPhotoURL FROM Deal_Team__c  ORDER BY Id ASC";
	public String query2="SELECT Id, Name, Profile_Image__c FROM navpeII__Pipeline__c  ORDER BY Name ASC";
	public String image1="Profile_Image__c";
	public String image3="Member__r.MediumPhotoURL";
	public String AttendeeImage="Attendee_Staff__r.MediumPhotoURL";
	
	public String image2="Profile_Image__c";
	//Navatar SDG Toggles
	
	public String RecordDealsQARequestsClosed="Record_Deals_QA_Requests_Closed";

	//Record_Deals_QA_Requests_Closed
	
}
