package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.SmokeCommonVariables.Smoke_STDTask1Subject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.BaseLib;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.PopUpName;
import com.navatar.generic.EnumConstants.TaskRayProjectButtons;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.relevantcodes.extentreports.LogStatus;

import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.CommonLib.*;
public class TaskPageBusinessLayer extends TaskPage {

	public TaskPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	/**@author Akul Bhutani
	 * @param projectName
	 * @param pageName
	 * @param fieldsWithValues
	 * @param action
	 * @param timeOut
	 * @return true/false
	 * @description this is used to verify fields present on task page
	 */
	public boolean fieldVerificationForTaskInViewMode(String projectName,PageName pageName,String[][] fieldsWithValues,action action,int timeOut) {
		refresh(driver);
		String label;
		String value;
		String actualValue="";
		WebElement ele;
		boolean flag=true;
		for (String[] fieldWithValue : fieldsWithValues) {
			
			value=fieldWithValue[1];
			label= fieldWithValue[0];
			if(label.equalsIgnoreCase(PageLabel.Related_Associations.toString()) || label.equalsIgnoreCase(PageLabel.Related_Contacts.toString()))
				ThreadSleep(10000);
				switchToFrame(driver, 10, getFrame(PageName.TaskPage, 30));
			ele = getLabelForTaskInViewMode( projectName, pageName, label,action, timeOut);
			label= fieldWithValue[0].replace("_", " ");
			
			if (ele!=null) {
				if (!label.equalsIgnoreCase(PageLabel.Watchlist.toString()))
				actualValue=ele.getText().trim();
				else
					actualValue=ele.getAttribute("alt");
				if (value==null || value.equals("")){
					flag=actualValue.equals(value);
				} else {
					if (fieldWithValue[0].equalsIgnoreCase(PageLabel.Due_Date.toString())) {
						flag=verifyDate(value, actualValue);
						//flag=value.contains(actualValue);
					}else if(fieldWithValue[0].equalsIgnoreCase(PageLabel.Comments.toString())||fieldWithValue[0].equalsIgnoreCase(PageLabel.Watchlist.toString())){
						flag=actualValue.equals(value);	
					}else{
						flag=actualValue.contains(value);	
					}
				
				}
				/*if (label.equalsIgnoreCase(ActivityRelatedLabel.Due_Date.toString()))
					flag=value.contains(actualValue);*/
				if (flag) {
					log(LogStatus.INFO, actualValue+" matched with "+value+" For Label : "+label, YesNo.No);
				} else {
					log(LogStatus.ERROR,"Actual : "+actualValue+" Expected : "+value+" not matched For Label : "+label, YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Actual : "+actualValue+" Expected : "+value+" not matched For Label : "+label);
				}
			} else {
				flag=false;
				log(LogStatus.ERROR, "Label Not Found : "+label+" so can not verify value : "+value, YesNo.No);
				BaseLib.sa.assertTrue(false, "Label Not Found : "+label+" so can not verify value : "+value);
			}
			switchToDefaultContent(driver);
		}
		return flag;
	
	}

	/**@author Akul Bhutani
	 * @param projectName
	 * @param pageName
	 * @param label
	 * @param action
	 * @param timeOut
	 * @return WebElement
	 * @description this method is used to return text of fields present on task page in view mode
	 */
	public WebElement getLabelForTaskInViewMode(String projectName,PageName pageName,String label,action action,int timeOut) {

		String xpath="";
		String fieldLabel=label.replace("_", " ");;
		switchToDefaultContent(driver);
		if (PageLabel.Related_Associations.toString().equals(label) || PageLabel.Related_Contacts.toString().equals(label)) {
			switchToDefaultContent(driver);
			switchToFrame(driver, 20, getTaskPageFrame(projectName,timeOut));
			xpath="//label[text()='"+fieldLabel+"']//following-sibling::div";

		}else if(PageLabel.Comments.toString().equals(label)) {
		xpath="//span[text()='"+fieldLabel+"']/../following-sibling::div//span/span"	;
		}else if(PageLabel.Name.toString().equals(label)) {
		xpath="//span[text()='"+fieldLabel+"']/../following-sibling::div"	;
		}else if(PageLabel.Watchlist.toString().equals(label)) {
			xpath="//span[text()='"+fieldLabel+"']/../following-sibling::div//img"	;
		}
		else {
			xpath ="//span[text()='"+fieldLabel+"']/../following-sibling::div";
		} 
		WebElement ele = FindElement(driver, xpath,fieldLabel , action, timeOut);
		scrollDownThroughWebelement(driver, ele, fieldLabel);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, fieldLabel);
		return ele;
		
	}
	
	/**@author Azhar Alam
	 * @param projectName
	 * @param taskName
	 * @return String
	 * @description this is used to return successful task created message
	 */
	public String taskCreatesMsg(String projectName,String taskName) {
		return "Task "+taskName+" was created";
		
	}
	
	
	/**@author Azhar Alam
	 * @param projectName
	 * @param pageLabel
	 * @param text
	 * @return String
	 * @description this is used to return String by replacing '_'
	 */
	public String Comment(String projectName,PageLabel pageLabel,String text) {
		String label = pageLabel.toString().replace("_", " ");
		String value = label+": "+text;
		return value;
		
	}
	
	/**@author Azhar Alam
	 * @param projectName
	 * @param taskName
	 * @return String
	 * @description this is used to return successful task created message
	 */
	public String taskSavedMsg(String projectName,String taskName) {
		return "Task "+taskName+" was saved";
		
	}
	
	/**
	 * @param projectName
	 * @param timeOut
	 * @return true if Related Contact Field Value is Blank on Task Page
	 */
	public boolean isRelatedContactEmpty(String projectName,int timeOut) {
		WebElement ele=null;
		String actual=null;
		boolean flag=false;
		switchToDefaultContent(driver);
		switchToFrame(driver, 10, getFrame(PageName.TaskPage, 30));
		ele = getRelatedContactsLabel(projectName, timeOut);
		try {
			if (ele!=null) {
				actual=ele.getText().trim();
				
				if (actual.isEmpty() || actual.equals("")) {
					log(LogStatus.INFO, "Related Contact is empty ", YesNo.No);
					flag=true;
				} else {
					log(LogStatus.ERROR,"Related Contact Should be empty : Actual Value : "+actual, YesNo.Yes);
			}
			} else {
				log(LogStatus.ERROR,"Related Contact Ele not Found", YesNo.Yes);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log(LogStatus.ERROR,"Exception For Related Contact Ele As it is blank", YesNo.Yes);
		}
		switchToDefaultContent(driver);
		return flag;
	}
	
	public boolean isCommentsEmpty(String projectName,int timeOut) {
		WebElement ele=null;
		String actual=null;
		boolean flag=false;
		switchToDefaultContent(driver);
		switchToFrame(driver, 10, getFrame(PageName.TaskPage, 30));
		ele = getCommentsLabel(projectName, timeOut);
		try {
			if (ele!=null) {
				actual=ele.getText().trim();
				
				if (actual.isEmpty() || actual.equals("")) {
					log(LogStatus.INFO, "Comments is empty ", YesNo.No);
					flag=true;
				} else {
					log(LogStatus.ERROR,"Comments Should be empty : Actual Value : "+actual, YesNo.Yes);
			}
			} else {
				log(LogStatus.ERROR,"Comments Ele not Found", YesNo.Yes);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log(LogStatus.ERROR,"Exception For Comments Ele As it is blank", YesNo.Yes);
		}
		switchToDefaultContent(driver);
		return flag;
	}
	
//	/**@author Azhar Alam
//	 * @param projectName
//	 * @param taskName
//	 * @return String
//	 * @description this is used to return successful task created message
//	 */
//	public String taskDeletedMsg(String projectName,String taskName) {
//	//	return "Task \""+taskName+"\" was deleted";
//		return "was deleted";
//		
//	}
//	
//	/**@author Azhar Alam
//	 * @param projectName
//	 * @param taskName
//	 * @return String
//	 * @description this is used to return successful task created message
//	 */
//	public String restoreItemMsg(String projectName,String restoreItem) {
//		//return "\""+restoreItem+"\" was restored";
//		return "was restored";
//		
//	}
	
	
}
