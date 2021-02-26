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
	public String ContactSDGQuery="SELECT Id, Name, Title, Industry_new__c, Region_new__c,Profile_Image__c FROM Contact WHERE (AccountId = '<<recordId>>') ORDER BY Name ASC";
	//Navatar SDG Toggles
	
	public String RecordDealsQARequestsClosed="Record_Deals_QA_Requests_Closed";

	//Record_Deals_QA_Requests_Closed
	
}
