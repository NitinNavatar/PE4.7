package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;

public interface FundDrawdownPageErrorMessage{
	public static String emailingText1="Emailing Capital Call Notice(s) of Fund Drawdown ID ";
	public static String emailingText2=" for Fund - ";
	public static String specifyRecepients = "Step 1. Specify the recipients to include";
	public static String congratulationErrorMsg="Congratulations!";
	public static String generatedEmailedRecipientErrorMsg(String noOfRecipients, String pageName) {
		if (pageName.contains("Bulk")) {
			return "Your emails have been generated and emailed to the "+noOfRecipients+" recipients you specified.";	
		} else {
			return "Your emails have been generated and emailed to the "+noOfRecipients+" recipient(s) you specified.";
		}
		
	}
}
