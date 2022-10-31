package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.AttendeeLabels;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.relevantcodes.extentreports.LogStatus;

public class DealTeamPageBusinessLayer extends DealTeamPage{

	public DealTeamPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param dealName
	 * @param requestInfo
	 * @param basedOnValue
	 * @param action
	 * @param timeOut
	 * @return true if Deal Team created successfully
	 */
	public boolean createDealTeam(String projectName,String dealName,String[][] requestInfo,String basedOnValue,action action,int timeOut) {
		boolean flag=true;
		String label;
		String value;
		String xpath="";
		WebElement ele;
		
		if(clickUsingJavaScript(driver, getNewButton(projectName, timeOut), "deal team")) {
			log(LogStatus.INFO,"click on New deal team Button",YesNo.Yes);

			for (String[] reuestData : requestInfo) {
				label=reuestData[0].replace("_", " ");
				value=reuestData[1];

				if(PageLabel.Team_Member.toString().equals(reuestData[0]) || PageLabel.Deal.toString().equals(reuestData[0])){
					if (sendKeys(driver, getListTextbox(projectName,label, timeOut), value, label+" : "+value,action)) {
						ThreadSleep(3000);
						log(LogStatus.INFO,"Able to send "+value+" to label : "+label,YesNo.Yes);
						if (click(driver,FindElement(driver,"//span[contains(@class,'listbox')]//*[@title='"+value+"']","ATTENDEE Name List", action, 30),
								value + "   :   Company Name", action)) {
							log(LogStatus.INFO,"Able to select "+value+" to label : "+label,YesNo.No);
						} else {
							sa.assertTrue(false,"Not Able to select "+value+" to label : "+label);
							log(LogStatus.SKIP,"Not Able to select "+value+" to label : "+label,YesNo.Yes);
							flag=false;
						}

					} else {
						sa.assertTrue(false,"Not Able to send "+value+" to label : "+label);
						log(LogStatus.SKIP,"Not Able to send "+value+" to label : "+label,YesNo.Yes);
						return false;
					}
				}else if(PageLabel.Team_Member_Role.toString().equals(reuestData[0]) || PageLabel.Deal_Contact_Type.toString().equals(reuestData[0])) {

					if (click(driver, getListTextbox(projectName, label, 10), label, action)) {
						ThreadSleep(2000);
						log(LogStatus.INFO,"Able to Click on "+label,YesNo.No);

						xpath="//span[@title='"+value+"']";
						ele = FindElement(driver,xpath, value,action, timeOut);
						ThreadSleep(4000);
						if (click(driver, ele, value, action)) {
							log(LogStatus.INFO,"Able to select "+value+" to label : "+label,YesNo.No);	
						} else {
							sa.assertTrue(false,"Not Able to select "+value+" to label : "+label);
							log(LogStatus.SKIP,"Not Able to select "+value+" to label : "+label,YesNo.Yes);
							flag=false;
						}

					} else {
						sa.assertTrue(false,"Not Able to Click on "+label);
						log(LogStatus.SKIP,"Not Able to Click on "+label,YesNo.Yes);
						flag=false;
					}

				}
				/*else if(AttendeeLabels.Notes.toString().equals(reuestData[0])) {
					AttendeePage ap = new AttendeePage(driver);
					if (sendKeys(driver, ap.labelTextBox(projectName,reuestData[0], 60), value, "notes",
							action.BOOLEAN)) {

					} else {
						sa.assertTrue(false,"not able to enter text to "+label);
						log(LogStatus.SKIP,"not able to enter text to "+label,YesNo.Yes);
						flag=false;
					}
				}*/
			}

			

			if (click(driver, getRecordPageSettingSave(timeOut), "save button", action)) {
				appLog.info("clicked on save button");
				
				ThreadSleep(3000);
				refresh(driver);
				ThreadSleep(3000);
				xpath="//*[text()='Deal Team']/../*/*[@slot='primaryField']/*";
				ele = FindElement(driver, xpath, "dt id", action, timeOut);
				if (ele!=null) {
					String id=getText(driver, ele, "deal team id",action.SCROLLANDBOOLEAN);
					ExcelUtils.writeData(phase1DataSheetFilePath,id, "Deal Team", excelLabel.Variable_Name, basedOnValue,
							excelLabel.DTID);
				
					log(LogStatus.INFO,"successfully created and noted id of DT"+id+" and deal name "+dealName,YesNo.No);	
				} else {
					sa.assertTrue(false,"could not create DT"+dealName);
					log(LogStatus.SKIP,"could not create DT"+dealName,YesNo.Yes);
					flag=false;
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on save button so cannot create deal team");
				log(LogStatus.SKIP,"Not Able to Click on save button so cannot create deal team",YesNo.Yes);
				flag=false;
			}


		}else {
			sa.assertTrue(false,"Not able to click on deal team button");
			log(LogStatus.SKIP,"Not able to click on deal team button",YesNo.Yes);
			flag=false;

		}
		return flag;
	}
	
	
	public boolean createDealTeam(String projectName,String[][] requestInfo,action action,int timeOut) {
		boolean flag=true;
		String label;
		String value;
		String xpath="";
		WebElement ele;
		
		if(clickUsingJavaScript(driver, getNewButton(projectName, timeOut), "deal team")) {
			log(LogStatus.INFO,"click on New deal team Button",YesNo.Yes);

			for (String[] reuestData : requestInfo) {
				label=reuestData[0].replace("_", " ");
				value=reuestData[1];

				if(PageLabel.Team_Member.toString().equals(reuestData[0]) || PageLabel.Deal.toString().equals(reuestData[0])){
					if (sendKeys(driver, getListTextbox(projectName,label, timeOut), value, label+" : "+value,action)) {
						ThreadSleep(3000);
						log(LogStatus.INFO,"Able to send "+value+" to label : "+label,YesNo.Yes);
						if (click(driver,FindElement(driver,"//span[contains(@class,'listbox')]//*[@title='"+value+"']","ATTENDEE Name List", action, 30),
								value + "   :   Company Name", action)) {
							log(LogStatus.INFO,"Able to select "+value+" to label : "+label,YesNo.No);
						} else {
							sa.assertTrue(false,"Not Able to select "+value+" to label : "+label);
							log(LogStatus.SKIP,"Not Able to select "+value+" to label : "+label,YesNo.Yes);
							flag=false;
						}

					} else {
						sa.assertTrue(false,"Not Able to send "+value+" to label : "+label);
						log(LogStatus.SKIP,"Not Able to send "+value+" to label : "+label,YesNo.Yes);
						return false;
					}
				}else if(PageLabel.Team_Member_Role.toString().equals(reuestData[0]) || PageLabel.Deal_Contact_Type.toString().equals(reuestData[0])) {

					if (click(driver, getListTextbox(projectName, label, 10), label, action)) {
						ThreadSleep(2000);
						log(LogStatus.INFO,"Able to Click on "+label,YesNo.No);

						xpath="//span[@title='"+value+"']";
						ele = FindElement(driver,xpath, value,action, timeOut);
						ThreadSleep(4000);
						if (click(driver, ele, value, action)) {
							log(LogStatus.INFO,"Able to select "+value+" to label : "+label,YesNo.No);	
						} else {
							sa.assertTrue(false,"Not Able to select "+value+" to label : "+label);
							log(LogStatus.SKIP,"Not Able to select "+value+" to label : "+label,YesNo.Yes);
							flag=false;
						}

					} else {
						sa.assertTrue(false,"Not Able to Click on "+label);
						log(LogStatus.SKIP,"Not Able to Click on "+label,YesNo.Yes);
						flag=false;
					}

				}

			}

			if (click(driver, getRecordPageSettingSave(timeOut), "save button", action)) {
				appLog.info("clicked on save button");
				
			} else {
				sa.assertTrue(false,"Not Able to Click on save button so cannot create deal team");
				log(LogStatus.SKIP,"Not Able to Click on save button so cannot create deal team",YesNo.Yes);
				flag=false;
			}


		}else {
			sa.assertTrue(false,"Not able to click on deal team button");
			log(LogStatus.SKIP,"Not able to click on deal team button",YesNo.Yes);
			flag=false;

		}
		return flag;
	}
	
}
