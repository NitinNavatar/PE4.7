package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.FindElement;
import static com.navatar.generic.CommonLib.click;
import static com.navatar.generic.CommonLib.log;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.navatar.generic.CommonLib;
import com.navatar.generic.EnumConstants.Condition;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.relevantcodes.extentreports.LogStatus;

public class FieldAndRelationshipPageBusinessLayer extends FieldAndRelationshipPage {

	public FieldAndRelationshipPageBusinessLayer(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		// TODO Auto-generated constructor stub
	}


	/**
	 * @author Sourabh saini
	 * @param projectName, fieldName, value, Condition="Only Activated or Deactivated in the Condition"
	 * @return true if able to activated or deactivated the picklist
	 */
	public boolean activateOrDeactivatePiclistValueOfField(String projectName, String fieldName,String value, Condition condition)
	{
		String xPath="";
		WebElement ele;
		boolean flag=false;
		if(CommonLib.sendKeysAndPressEnter(driver, getQucikSearchInFieldAndRelationshipPage(50),fieldName , "Field", action.SCROLLANDBOOLEAN))
		{
			log(LogStatus.INFO,"Field value has been passed in "+fieldName,YesNo.No);
			CommonLib.ThreadSleep(6000);
			xPath="//span[text()='"+fieldName+"']";
			ele = FindElement(driver, xPath, fieldName + " xpath", action.SCROLLANDBOOLEAN, 30);
			if (CommonLib.click(driver, ele,fieldName+" field" , action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on Field" + fieldName, YesNo.No);
				CommonLib.ThreadSleep(7000);
				CommonLib.switchToFrame(driver, 40, getfieldsAndRelationshipsIframe(30));
				try
				{
					ele=new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[@scope='row' and text()='"+value+"']/preceding-sibling::td//a[contains(@title,'ctivate')]")));
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					log(LogStatus.ERROR,"Could not Found the Element",YesNo.Yes);
					return false;
				}

				String actionStatus=CommonLib.getText(driver, ele, fieldName+" Field", action.SCROLLANDBOOLEAN);

				if(condition.toString().equals("activate"))
				{
					if(!actionStatus.equals("Activate"))
					{
						if (CommonLib.click(driver, ele,value+" Deactivate button" , action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on the Deactivate button of " +value, YesNo.No);
							if(!CommonLib.isAlertPresent(driver))
							{		
								CommonLib.clickUsingJavaScript(driver, ele,value+" field" , action.SCROLLANDBOOLEAN);

							}
							CommonLib.switchToDefaultContent(driver);
							CommonLib.switchToAlertAndAcceptOrDecline(driver, 20, action.ACCEPT);
							flag =true;
						}					
					}
					else
					{
						CommonLib.switchToDefaultContent(driver);
						log(LogStatus.INFO, value+" is already Activated", YesNo.No);

						flag=true;
					}
				}
				else if(condition.toString().equals("deactivate"))
				{
					if(!actionStatus.equals("Deactivate"))
					{
						if (CommonLib.click(driver, ele,value+" Activate button" , action.SCROLLANDBOOLEAN)) {
							CommonLib.switchToDefaultContent(driver);
							log(LogStatus.INFO, "clicked on the Activate button of " +value, YesNo.No);	

							flag=true;
						}					
					}
					else
					{
						log(LogStatus.INFO, value+" is already Deactivated", YesNo.No);
						CommonLib.switchToDefaultContent(driver);
						flag=true;
					}
				}

				CommonLib.ThreadSleep(15000);
			}

			else
			{
				log(LogStatus.ERROR,"Could not click on the "+fieldName,YesNo.Yes);
				flag=false;
			}
		}
		else
		{
			log(LogStatus.ERROR,"Could not pass the Field value "+fieldName,YesNo.Yes);
			flag=false;
		}


		return flag;

	}


	public boolean editPicklistFieldLabel(String projectName, String fieldName,String labelName,String apiName)
	{
		String xPath="";
		WebElement ele;
		boolean flag=false;
		if(CommonLib.sendKeysAndPressEnter(driver, getQucikSearchInFieldAndRelationshipPage(50),fieldName , "Field", action.SCROLLANDBOOLEAN))
		{
			log(LogStatus.INFO,"Field value has been passed in "+fieldName,YesNo.No);
			CommonLib.ThreadSleep(6000);
			xPath="//span[text()='"+fieldName+"']";
			ele = FindElement(driver, xPath, fieldName + " xpath", action.SCROLLANDBOOLEAN, 30);
			if (CommonLib.click(driver, ele,fieldName+" field" , action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on Field" + fieldName, YesNo.No);
				CommonLib.ThreadSleep(7000);
				CommonLib.switchToFrame(driver, 40, getfieldsAndRelationshipsIframe(30));
				try
				{
					ele=new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='"+apiName+"']/preceding-sibling::td//a[contains(@title,'Edit')]")));
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					log(LogStatus.ERROR,"Could not found the "+fieldName+" Element",YesNo.Yes);
					return false;
				}
				if(CommonLib.click(driver, ele, fieldName+" Field", action.SCROLLANDBOOLEAN))
				{
					CommonLib.switchToDefaultContent(driver);
					CommonLib.switchToFrame(driver, 50, getpiclistEditiframe(30));
					if(CommonLib.sendKeys(driver, getEditPicklistLabelName(50), labelName, apiName+ " Picklist Value", action.SCROLLANDBOOLEAN))
					{
						log(LogStatus.INFO, labelName+" has been passed in the Picklist Label Name" , YesNo.No);
						if(CommonLib.click(driver, geteditPicklistSaveButton(30), "Save Button" , action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, "Clicked on the Save button" , YesNo.No);
							CommonLib.switchToDefaultContent(driver);
							CommonLib.switchToFrame(driver, 40, getfieldsAndRelationshipsIframe(30));			
							CommonLib.ThreadSleep(15000);
							try
							{
								ele=new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='"+apiName+"']/preceding-sibling::th")));
							}
							catch(Exception ex)
							{
								ex.printStackTrace();
								log(LogStatus.ERROR,"Could not found the "+labelName+" Element",YesNo.Yes);
								return false;
							}


							String Buttonstatus=CommonLib.getText(driver, ele, apiName+" value status", action.SCROLLANDBOOLEAN);
							if(Buttonstatus.equals(labelName))
							{
								log(LogStatus.PASS, apiName+" value has been changed"  , YesNo.No);
								flag=true;
							}
							else
							{
								log(LogStatus.ERROR,apiName+" value is not changed",YesNo.Yes);
								flag=false;
							}											
						}
						else
						{
							log(LogStatus.ERROR,"Could not click on the save button",YesNo.Yes);
							flag=false;
						}
					}
					else
					{
						log(LogStatus.ERROR,"Could not pass the label name of "+apiName,YesNo.Yes);
						flag=false;
					}
				}
				else
				{
					log(LogStatus.ERROR,"Could not click on the Edit Button of "+apiName,YesNo.Yes);
					flag=false;
				}

			}

			else
			{
				log(LogStatus.ERROR,"Could not click on the "+fieldName,YesNo.Yes);
				flag=false;
			}
		}
		else
		{
			log(LogStatus.ERROR,"Could not pass the Field value "+fieldName,YesNo.Yes);
			flag=false;
		}
		return flag;

	}



	public boolean deletePicklistOptionAndReplaceValue(String projectName, String fieldName,String ReplaceValue,String apiName,Condition condition)
	{
		String xPath="";
		WebElement ele;
		boolean flag=false;
		if(CommonLib.sendKeysAndPressEnter(driver, getQucikSearchInFieldAndRelationshipPage(50),fieldName , "Field", action.SCROLLANDBOOLEAN))
		{
			log(LogStatus.INFO,"Field value has been passed in "+fieldName,YesNo.No);
			CommonLib.ThreadSleep(6000);
			xPath="//span[text()='"+fieldName+"']";
			ele = FindElement(driver, xPath, fieldName + " xpath", action.SCROLLANDBOOLEAN, 30);
			if (CommonLib.click(driver, ele,fieldName+" field" , action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on Field" + fieldName, YesNo.No);
				CommonLib.ThreadSleep(7000);
				CommonLib.switchToFrame(driver, 40, getfieldsAndRelationshipsIframe(30));
				try
				{
					ele=new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='"+apiName+"']/preceding-sibling::td//a[contains(@title,'Del')]")));
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					log(LogStatus.ERROR,"Could not found the "+fieldName+" Element",YesNo.Yes);
					return false;
				}
				if(CommonLib.click(driver, ele, fieldName+" Field", action.SCROLLANDBOOLEAN))
				{				
					log(LogStatus.INFO, "clicked on the Del button of " +apiName, YesNo.No);
					if(!CommonLib.isAlertPresent(driver))
					{		
						CommonLib.clickUsingJavaScript(driver, ele,apiName+" field" , action.SCROLLANDBOOLEAN);
					}
					CommonLib.switchToAlertAndAcceptOrDecline(driver, 20, action.ACCEPT);
					CommonLib.switchToDefaultContent(driver);
					log(LogStatus.INFO, "Clicked on OK button on the Alert Popup", YesNo.No);
					CommonLib.switchToFrame(driver, 50, getfindAndReplaceIframe(50));

					if(condition.toString().equals("replaceWithValue"))
					{
						if(CommonLib.selectVisibleTextFromDropDown(driver, getreplaceValueDropDown(50), "Replace Value Drop Down", ReplaceValue))
						{
							if(CommonLib.click(driver, geteditPicklistSaveButton(30), "Save Button" , action.SCROLLANDBOOLEAN))
							{
								log(LogStatus.INFO, "Clicked on the Save button" , YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR,"Could not click on the save button",YesNo.Yes);
								flag=false;
							}
						}
						else
						{
							log(LogStatus.ERROR,"Could not select the "+ReplaceValue+ "from the Replace option ",YesNo.Yes);
							flag=false;
						}
					}
					else if(condition.toString().equals("replaceWithBlank"))
					{

						if(CommonLib.click(driver, getreplaceValueWithNull(30), "Save Button" , action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, "Clicked on the Replace value on records with blank value" , YesNo.No);
							if(CommonLib.click(driver, geteditPicklistSaveButton(30), "Save Button" , action.SCROLLANDBOOLEAN))
							{
								log(LogStatus.INFO, "Clicked on the Save button" , YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR,"Could not click on the save button",YesNo.Yes);
								flag=false;
							}
						}
						else
						{
							log(LogStatus.ERROR,"Could not Click on the Replace value on records with blank value",YesNo.Yes);
							flag=false;
						}

					}
					
					CommonLib.switchToDefaultContent(driver);
					CommonLib.switchToFrame(driver, 40, getfindAndReplaceIframe(30));			
					CommonLib.ThreadSleep(15000);

					if(!CommonLib.isElementPresent(ele))
					{
						log(LogStatus.INFO, apiName+" has been deleted" , YesNo.No);
						flag=true;
					}
					else
					{
						log(LogStatus.ERROR,apiName+" is not deleted",YesNo.Yes);
						flag=false;
					}

				}
				else
				{
					log(LogStatus.ERROR,"Could not click on the Del Button of "+apiName,YesNo.Yes);
					flag=false;
				}

			}
			else
			{
				log(LogStatus.ERROR,"Could not click on the "+fieldName,YesNo.Yes);
				flag=false;
			}
		}
		else
		{
			log(LogStatus.ERROR,"Could not pass the Field value "+fieldName,YesNo.Yes);
			flag=false;
		}
		return flag;

	}

}
