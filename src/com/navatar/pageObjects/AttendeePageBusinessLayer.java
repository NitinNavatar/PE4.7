package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.FindElement;
import static com.navatar.generic.CommonLib.ThreadSleep;
import static com.navatar.generic.CommonLib.changeNumberIntoUSFormat;
import static com.navatar.generic.CommonLib.click;
import static com.navatar.generic.CommonLib.clickUsingJavaScript;
import static com.navatar.generic.CommonLib.getText;
import static com.navatar.generic.CommonLib.isDisplayed;
import static com.navatar.generic.CommonLib.log;
import static com.navatar.generic.CommonLib.refresh;
import static com.navatar.generic.CommonLib.sendKeys;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.BaseLib;
import com.navatar.generic.EnumConstants.AttendeeLabels;
import com.navatar.generic.EnumConstants.ContactPageFieldLabelText;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.IndiviualInvestorFieldLabel;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.RecordType;
import com.navatar.generic.EnumConstants.RelatedList;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.relevantcodes.extentreports.LogStatus;

public class AttendeePageBusinessLayer extends AttendeePage{

	public AttendeePageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param event
	 * @param staff
	 * @param status
	 * @param notes
	 * @return true if attendee created successfully
	 */
	public boolean createAttendee(String projectName, String event, String staff,String status,String notes) {
		String labelNames[]=null;
		String labelValue[]=null;

		if(click(driver, getnewAttendeeButton(projectName, 30), "new attendee button in "+projectName, action.SCROLLANDBOOLEAN)) {
			appLog.info("clicked on new attendee button");
		}else {
			appLog.error("Not able to click on new attendee button so cannot create attendee: "+event);
			return false;
		}

		refresh(driver);
		ThreadSleep(3000);
		ThreadSleep(5000);
		String fields[] = {AttendeeLabels.Attendee_Staff.toString(),AttendeeLabels.Marketing_Event.toString(),
				AttendeeLabels.Notes.toString()};
		String values[]={staff,event,notes};
		int i = 0;
		for (String labelName:fields){
			ThreadSleep(2000);
			String finalLabelName=labelName.replace("_", " ");
			if (sendKeys(driver, labelTextBox(projectName,finalLabelName, 60), values[i], "staff",
					action.BOOLEAN)) {

			}
			else {
				appLog.error("Not able to enter "+labelName);
			}
			if (labelName.equalsIgnoreCase(AttendeeLabels.Attendee_Staff.toString())|| (labelName.equalsIgnoreCase(AttendeeLabels.Marketing_Event.toString()))
					)
				if (click(driver,FindElement(driver,	"//span[@title='"+values[i]+"']",
						labelName, action.THROWEXCEPTION, 30),
						values[i] + "   :   Account Name", action.BOOLEAN)) {
					appLog.info(values[i] + "  is present in list.");
				} else {
					appLog.info(values[i] + "  is not present in the list.");
				}


		}
		if (click(driver, getSaveButton(projectName, 60), "Save Button",
				action.SCROLLANDBOOLEAN)) {
			appLog.info("Clicked on save button");

			if(projectName.equalsIgnoreCase(Mode.Lightning.toString())) {
				ThreadSleep(2000);
				refresh(driver);
				ThreadSleep(5000);
			}
			WebElement ele=getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
			click(driver, ele, RelatedTab.Details.toString(), action.SCROLLANDBOOLEAN);


			/*if (getContactFullNameInViewMode(projectName, 60) != null) {
										String contactFullName = getText(driver,
												getContactFullNameInViewMode(projectName, 60), "Contact Name",
												action.SCROLLANDBOOLEAN);
										System.err.println("Contact Name : "+contactFullName);
										if (contactFullName.contains(contactFirstName + " " + contactLastName)) {
											appLog.info("Contact Created Successfully :" + contactFirstName + " "
													+ contactLastName);
											if(labelNames!=null && labelValue!=null ) {
												for(int i=0; i<labelNames.length; i++) {
													if(fieldValueVerificationOnContactPage(projectName, null, labelNames[i].replace("_", " ").trim(),labelValue[i])){
														appLog.info(labelNames[i]+" label value "+labelValue[i]+" is matched successfully.");
													}else {
														appLog.info(labelNames[i]+" label value "+labelValue[i]+" is not matched successfully.");
														BaseLib.sa.assertTrue(false, labelNames[i]+" label value "+labelValue[i]+" is not matched.");
													}
												}
											}
											return true;
										} else {
											appLog.error("Contact did not get created successfully :" + contactFirstName
													+ " " + contactLastName);
										}
									} else {
										appLog.error("Not able to find contact name label");
									}*/

		}

		return false;
	}
		
}
