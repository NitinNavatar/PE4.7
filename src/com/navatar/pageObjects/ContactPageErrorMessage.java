/**
 * 
 */
package com.navatar.pageObjects;

/**
 * @author Ankur Rana
 *
 */
public interface ContactPageErrorMessage {

	public String TransferConfirmationPopUpMessage="How would you like to handle the mailing and other addresses associated with the contact?";
	public static String emailSubject(String CCID, String commitmentID) {
		return "Email: Capital Call Notice "+CCID+" of Commitment "+commitmentID;
	}
}
