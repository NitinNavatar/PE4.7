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
	public String attendeeQuery="SELECT Id, Name, Attendee_Staff__c, Status__c FROM Attendee WHERE (MarketingEventId = '<<recordId>>') ORDER BY Name ASC";
	//Navatar SDG Toggles
	
	public String RecordDealsQARequestsClosed="Record_Deals_QA_Requests_Closed";

	//Record_Deals_QA_Requests_Closed
	
}
