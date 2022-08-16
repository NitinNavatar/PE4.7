package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.PEFSTG_Tc025_RecordType2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sikuli.script.Screen;

import com.navatar.generic.CommonLib;
import com.navatar.generic.EnumConstants.SortOrder;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.columnName;
import com.relevantcodes.extentreports.LogStatus;

public class FirmPageBusinessLayer extends FirmPage{

	public FirmPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<String> verifyFirmRecordType(ArrayList<String> recordName)
	{
		ArrayList<String> recordTypeName=new ArrayList<String>();
		ArrayList<String> result=new ArrayList<String>();

		if (click(driver, getnewButton(50), "new button", action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on the new button", YesNo.No);
			ThreadSleep(3000);
			for(int i=0; i<getrecordTypeLabelName().size(); i++)
			{
				String text=CommonLib.getText(driver, getrecordTypeLabelName().get(i), "Record label name", action.SCROLLANDBOOLEAN);
				recordTypeName.add(text);
			}

			for(int i=0; i<recordName.size(); i++)
			{
				if(recordName.get(i).equals(recordTypeName.get(i)))
				{
					log(LogStatus.INFO, "Record Name: "+recordName.get(i)+" has been verified", YesNo.No);
				}
				else
				{
					log(LogStatus.ERROR, "Record Name: "+recordName.get(i)+" is not matched with the "+recordTypeName.get(i)+"", YesNo.No);
					result.add("Record Name: "+recordName.get(i)+" is not matched with the "+recordTypeName.get(i)+"");
				}
			}

		}
		else
		{
			log(LogStatus.INFO, "Not able to click on the new button", YesNo.Yes);
			result.add("Not able to click on the new button");

		}
		return result;

	}

	public ArrayList<String> verifyListViewOfFirm(ArrayList<String> listViewName) {
		boolean flag = false;
		ArrayList<String> result = new ArrayList<String>();
		if(CommonLib.click(driver, getClickedOnRecentlyViewed(30), "Recently Viewed", action.SCROLLANDBOOLEAN)) {

			appLog.info("clicked on recently viewed");

			List<String> listView = new ArrayList<String>();

			List<WebElement> lists= CommonLib.FindElements(driver, "//div[@class='scroller']//ul//li", "RecentlyViewedList");
			if(lists!=null) {
				for(int i=0;i<lists.size();i++)
				{
					WebElement element= lists.get(i);
					String listName= CommonLib.getText(driver, element, "list view of Firm", action.BOOLEAN);
					listView.add(listName.replace("\n", " "));
				}      
			}
			else {
				log(LogStatus.ERROR,"Could not get the list view name",YesNo.No);
				result.add("Could not get the list view name");
			}

			for(int i=0;i<lists.size()-1;i++)
			{
				if(listView.get(i+1).contains(listViewName.get(i)))
				{

					log(LogStatus.INFO, listView.get(i+1)+" List Name has been matched with "+listViewName.get(i), YesNo.No);

				}
				else {
					log(LogStatus.ERROR, listView.get(i+1)+" List Name is not matched with "+listViewName.get(i), YesNo.No);
					result.add(listView.get(i+1)+" List Name is not matched with "+listViewName.get(i));

				}
			}
		}
		else {
			appLog.error("Not able to click on recently viewed...");
			result.add("Not able to click on recently viewed...");

		}

		return result ;
	}


	public ArrayList<String> verifyFilterOnListView(String[] listViewName, String[] filter, String[] field, String[] Operator, String[] filterValue,String[] filterCondition )
	{
		String xPath="";
		WebElement ele;
		ArrayList<String> result=new ArrayList<String>();

		for(int i=0; i<filterCondition.length; i++)
		{
			String[] filterFiled=null;
			String[] fOperator=null;
			String[] FOperand=null;

			if(CommonLib.click(driver, getClickedOnRecentlyViewed(30), "Recently Viewed", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on the recently view button", YesNo.No);

				xPath="//ul[@class='slds-dropdown__list slds-show']//span[text()='"+listViewName[i]+"']";
				ele=CommonLib.FindElement(driver, xPath, listViewName[i],action.SCROLLANDBOOLEAN , 50);

				if(CommonLib.click(driver, ele, listViewName[i]+" element", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on "+listViewName[i]+" element"+" button", YesNo.No);

					if(filterCondition[i].equals("All Filters"))
					{

						if(CommonLib.click(driver, getshowFilter(50), "Show filter", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Show filter button", YesNo.No);

							String scopeLabelFilter=CommonLib.getText(driver, getscopeLabelFilter(50), "scope label filter", action.SCROLLANDBOOLEAN);
							if(scopeLabelFilter.equals(filter[i]))
							{
								log(LogStatus.INFO, scopeLabelFilter+ " is visible in the Filter by Owner", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, scopeLabelFilter+ " is not visible in the Filter by Owner", YesNo.Yes);
								result.add(scopeLabelFilter+ " is not visible in the Filter by Owner");
							}

							filterFiled=field[i].split("<FieldBreak>");
							fOperator=Operator[i].split("<OperatorBreak>");
							FOperand=filterValue[i].split("<valueBreak>");

							if(filterFiled.length==1)
							{
								String filterFieldLabel=CommonLib.getText(driver, getfilterFieldLabel(50), "Filter field label", action.SCROLLANDBOOLEAN);
								String filterOperator=CommonLib.getText(driver, getfilterOperator(50), "Filter Operator", action.SCROLLANDBOOLEAN);
								String filterOperand=CommonLib.getText(driver, getfilterOperand(50), "Filter Operand", action.SCROLLANDBOOLEAN);

								if(filterFieldLabel.equalsIgnoreCase(field[i]) && filterOperator.equalsIgnoreCase(Operator[i]) && filterOperand.equalsIgnoreCase(filterValue[i]))
								{
									log(LogStatus.INFO, filterFieldLabel+", "+filterOperator+" and "+filterOperand+ " have been matched" , YesNo.No);
								}
								else
								{
									log(LogStatus.ERROR, "Either Filter label name : "+filterFieldLabel+" or filter Operator :"+filterOperator+" Or Filter operand :"+filterOperand+ " are not matced with Filter label name : "+field+", filter Operator :"+Operator+", Filter operand :"+filterValue[i]+ "", YesNo.No);
									result.add("Either Filter label name : "+filterFieldLabel+" or filter Operator :"+filterOperator+" Or Filter operand :"+filterOperand+ " are not matced with Filter label name : "+field+", filter Operator :"+Operator+", Filter operand :"+filterValue[i]+ "");
								}
							}
							else
							{

								for(int j=0; j<filterFiled.length;j++)
								{
									xPath="//div[@id='filterPanelFieldCriterion"+j+"']//div[@class='fieldLabel']";
									ele=CommonLib.FindElement(driver, xPath, "Field Label", action.SCROLLANDBOOLEAN, 50);
									String filterFieldLabel=CommonLib.getText(driver,ele, "Filter field label", action.SCROLLANDBOOLEAN);

									xPath="//div[@id='filterPanelFieldCriterion"+j+"']//span[@class='test-operatorWrapper']";
									ele=CommonLib.FindElement(driver, xPath, "Field Label", action.SCROLLANDBOOLEAN, 50);
									String filterOperator=CommonLib.getText(driver, ele, "Filter Operator", action.SCROLLANDBOOLEAN);							

									xPath="//div[@id='filterPanelFieldCriterion"+j+"']//span[@class='test-operandsWrapper']";
									ele=CommonLib.FindElement(driver, xPath, "Field Label", action.SCROLLANDBOOLEAN, 50);
									String filterOperand=CommonLib.getText(driver, ele, "Filter Operand", action.SCROLLANDBOOLEAN);						

									if(filterFieldLabel.equalsIgnoreCase(filterFiled[j]) && filterOperator.equalsIgnoreCase(fOperator[j]) && filterOperand.equalsIgnoreCase(FOperand[j]))
									{
										log(LogStatus.INFO, filterFieldLabel+", "+filterOperator+" and "+filterOperand+ " have been matched" , YesNo.No);
									}
									else
									{
										log(LogStatus.ERROR, "Either Filter label name : "+filterFieldLabel+" or filter Operator :"+filterOperator+" Or Filter operand :"+filterOperand+ " are not matced with Filter label name : "+filterFiled[j]+", filter Operator :"+fOperator[j]+", Filter operand :"+FOperand[j]+ "", YesNo.Yes);
										result.add("Either Filter label name : "+filterFieldLabel+" or filter Operator :"+filterOperator+" Or Filter operand :"+filterOperand+ " are not matced with Filter label name : "+filterFiled[j]+", filter Operator :"+fOperator[j]+", Filter operand :"+FOperand[j]+ "");
									}

									if(filterLogic(50)!=null)
									{
										log(LogStatus.INFO, "Filter logic is visible", YesNo.No);
									}
									else
									{
										log(LogStatus.INFO, "Filter logic is nto visible", YesNo.Yes);
										result.add("Filter logic is not visible");
									}


								}
							}

							CommonLib.refresh(driver);
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Show filter button", YesNo.Yes);
							result.add("Not able to click on Show filter button");
						}

					}

					else if(filterCondition[i].trim().equalsIgnoreCase("Only Filter_By_Owner"))
					{

						if(CommonLib.click(driver, getshowFilter(50), "Show filter", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Show filter button", YesNo.No);

							String scopeLabelFilter=CommonLib.getText(driver, getscopeLabelFilter(50), "scope label filter", action.SCROLLANDBOOLEAN);
							if(scopeLabelFilter.equals(filter[i]))
							{
								log(LogStatus.INFO, scopeLabelFilter+ " is visible in the Filter by Owner", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, scopeLabelFilter+ " is not visible in the Filter by Owner for "+listViewName[i], YesNo.Yes);
								result.add(scopeLabelFilter+ " is not visible in the Filter by Owner for "+listViewName[i]);
							}

							CommonLib.refresh(driver);
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Show filter button", YesNo.Yes);
							result.add("Not able to click on Show filter button");
						}

					}

					else if(filterCondition[i].trim().equalsIgnoreCase("Only Filter_icon_Availability"))
					{

						ele=getshowFilter(50);
						if(ele==null)
						{
							log(LogStatus.INFO, "Filter icon is disable for list view : "+listViewName[i] , YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Filter icon is not disable for list view : "+listViewName[i], YesNo.Yes);
							result.add("Filter icon is not disable for list view : "+listViewName[i]);
						}
						CommonLib.refresh(driver);		
					}


					else
					{
						log(LogStatus.ERROR, "Not able to click on "+listViewName[i]+"", YesNo.Yes);
						result.add("Not able to click on "+listViewName[i]+"");
					}

				}
				else
				{
					log(LogStatus.ERROR, listViewName[i]+" element not found", YesNo.Yes);
					result.add(listViewName[i]+" element not found");
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to click on recently view", YesNo.Yes);
				result.add("Not able to click on recently view");
			}
		}
		return result;

	}

	public ArrayList<String> verifyFieldsOnListview(String listViewAndFieldData, int timeOut)
	{
		String xPath="";
		WebElement ele;
		List<WebElement> elements;
		ArrayList<String> actualFieldValue=new ArrayList<String>();
		ArrayList<String> expectedFieldValue=new ArrayList<String>();
		ArrayList<String> result=new ArrayList<String>();
		String[] listViewAndFieldName=listViewAndFieldData.split("<break>");

		for(int i=0; i<listViewAndFieldName.length; i++)
		{
			String data[]=listViewAndFieldName[i].split("<fieldBreak>");
			String listViewName=data[0];
			String fieldData[]=data[1].split("<f>");

			for(int k=0; k<fieldData.length; k++)
			{
				String val=fieldData[k];
				expectedFieldValue.add(val);
			}
			if(CommonLib.click(driver, getClickedOnRecentlyViewed(timeOut), "Recently Viewed ero icon", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on the recently view ero icon", YesNo.No);

				xPath="//ul[@class='slds-dropdown__list slds-show']//span[text()='"+listViewName+"']";
				ele=CommonLib.FindElement(driver, xPath, listViewName,action.SCROLLANDBOOLEAN , timeOut);

				if(CommonLib.click(driver, ele, listViewName+" element", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on "+listViewName+" element"+" button", YesNo.No);
					ThreadSleep(10000);
					xPath="//table[@data-aura-class='uiVirtualDataTable']//thead//th//span[@class='slds-truncate' and text()!='']";
					elements=CommonLib.FindElements(driver, xPath, listViewName+"'s field");
					for(int j=0; j<elements.size(); j++)
					{
						String text=CommonLib.getText(driver, elements.get(j), listViewName+" field", action.SCROLLANDBOOLEAN);
						actualFieldValue.add(text);
					}

					for(int a=0; a<expectedFieldValue.size(); a++)
					{
						if(expectedFieldValue.get(a).equals(actualFieldValue.get(a)))
						{
							log(LogStatus.INFO, "Expected field value : "+expectedFieldValue.get(a)+" has been matched with the Actual field value : "+actualFieldValue.get(a), YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Expected field value : "+expectedFieldValue.get(a)+" is not matched with the Actual field value : "+actualFieldValue.get(a), YesNo.No);	
							result.add("Expected field value : "+expectedFieldValue.get(a)+" is not matched with the Actual field value : "+actualFieldValue.get(a));
						}

					}


				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on the "+listViewName+" list view name", YesNo.Yes);
					result.add("Not able to click on the "+listViewName+" list view name");
				}
			}

			else
			{
				log(LogStatus.ERROR, "Not able to click on the recently view ero icon", YesNo.Yes);
				result.add("Not able to click on the recently view ero icon");
			}
			actualFieldValue.removeAll(actualFieldValue);
			expectedFieldValue.removeAll(expectedFieldValue);

		}

		return result;

	}

	public boolean VerifySortingOnEntityTypeField()
	{
		boolean flag=false;
		String xPath="";
		List<WebElement> elements;
		if(CommonLib.click(driver, geteditBtn(50), "edit button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on edit button", YesNo.No);

			if(CommonLib.click(driver, getentityTypeBtn(50), "entity type button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on entity type button", YesNo.No);

				xPath="//label[text()='Entity Type']/parent::lightning-combobox//lightning-base-combobox-item//span[@class='slds-truncate' and text()!='--None--']";
				elements=CommonLib.FindElements(driver, xPath, "Entity type list");

				if(CommonLib.checkSorting(driver, SortOrder.Assecending, elements))
				{
					log(LogStatus.INFO, "Entity type sorting order has been verified", YesNo.No);
					flag=true;
				}
				else
				{
					log(LogStatus.ERROR, "Entity type sorting order is not verified", YesNo.No);
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to click on the Entity type button", YesNo.No);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Not able to click on edit button", YesNo.No);
		}
		return flag;
	}

	public ArrayList<String> verifyHighlightPanel(ArrayList<String> highlightValueExpected)
	{
		String xPath="";
		WebElement ele;
		ArrayList<String> highlightValueActual=new ArrayList<String>();
		ArrayList<String> result=new ArrayList<String>();
		for(int i=0; i<highlightValueExpected.size(); i++)
		{
			xPath="//div[@class='slds-grid primaryFieldRow' or @class='secondaryFields']//*[text()='"+highlightValueExpected.get(i)+"']";
			ele=CommonLib.FindElement(driver, xPath, highlightValueExpected+" highlight value", action.SCROLLANDBOOLEAN, 50);
			String text=CommonLib.getText(driver, ele, highlightValueExpected+" highlight value", action.SCROLLANDBOOLEAN);
			highlightValueActual.add(text);
		}
		for(int i=0; i<highlightValueExpected.size(); i++)
		{
			if(highlightValueExpected.get(i).equals(highlightValueActual.get(i)))
			{
				log(LogStatus.INFO, "Highlight Value Expected result \""+highlightValueExpected.get(i)+"\" has been matched with the Highlight Value Actual result : "+highlightValueActual.get(i), YesNo.No);
			}
			else
			{
				log(LogStatus.ERROR, "Highlight Value Expected result \""+highlightValueExpected.get(i)+"\" is not matched with the Highlight Value Actual result : "+highlightValueActual.get(i), YesNo.No);				
				result.add("Highlight Value Expected result \""+highlightValueExpected.get(i)+"\" is not matched with the Highlight Value Actual result : "+highlightValueActual.get(i));
			}
		}
		return result;
	}


	public boolean verifyCustomAction(String data)
	{
		String xPath="",text="";
		WebElement ele;
		List<WebElement> elements;

		ArrayList<String> actualCustomAction=new ArrayList<String>();
		ArrayList<String> ExpectedCustomAction=new ArrayList<String>();
		//ArrayList<String> fieldValue=new ArrayList<String>();
		ArrayList<String> result=new ArrayList<String>();

		String tabNameFieldNameValue[]=data.split("<break>");

		for(int i=0; i<tabNameFieldNameValue.length; i++)
		{
			String[] tabNameAndFieldNameValue=tabNameFieldNameValue[i].split("<fieldBreak>");
			String[] fieldNameAndValue=tabNameAndFieldNameValue[1].split("<valueBreak>");
			String[] fieldName=fieldNameAndValue[0].split("<f>");

			for(int k=0; k<fieldName.length; k++)
			{
				ExpectedCustomAction.add(fieldName[k]);
			}
			String[] fieldValue=fieldNameAndValue[1].split("<v>");

			if(CommonLib.click(driver, getTabEroBtn(50), "tab ero button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on tab ero button", YesNo.No);
				xPath="//ul[@class='slds-button-group-list']//*[text()='"+tabNameAndFieldNameValue[0]+"']";
				ele=CommonLib.FindElement(driver, xPath, tabNameAndFieldNameValue[0]+" tab name", action.SCROLLANDBOOLEAN, 50);
				if(CommonLib.click(driver, ele, tabNameAndFieldNameValue[0]+" tab name", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on "+tabNameAndFieldNameValue[0]+" tab name button", YesNo.No);

					if(tabNameAndFieldNameValue[0].equals("New Contact"))
					{

						xPath="//div[contains(@class,'forcePageBlockSectionRow')]//div[contains(@class,'slds-form-element_readonly')]";
						ele=CommonLib.FindElement(driver, xPath, "New Contacts labels", action.SCROLLANDBOOLEAN, 50);
						text=CommonLib.getText(driver, ele, "New Contact label Element", action.SCROLLANDBOOLEAN);
						text = text.replace("\n", " ");
						actualCustomAction.add(text);

						xPath="//div[contains(@class,'forcePageBlockSectionRow')]//legend";
						ele=CommonLib.FindElement(driver, xPath, "New Contacts labels", action.SCROLLANDBOOLEAN, 50);
						text=CommonLib.getText(driver, ele, "New Contact label Element", action.SCROLLANDBOOLEAN);
						text = text.replace("\n", " ");
						actualCustomAction.add(text);

						xPath="//div[contains(@class,'forcePageBlockSectionRow')]//span[@class='uiPicklistLabel-top form-element__label uiPicklistLabel']";
						ele=CommonLib.FindElement(driver, xPath, "New Contacts labels", action.SCROLLANDBOOLEAN, 50);
						text=CommonLib.getText(driver, ele, "New Contact label Element", action.SCROLLANDBOOLEAN);
						text = text.replace("\n", " ");
						actualCustomAction.add(text);

						xPath="//div[contains(@class,'forcePageBlockSectionRow')]//label";
						elements=CommonLib.FindElements(driver, xPath, "New Contacts label");
						for(WebElement li:elements)
						{
							text=CommonLib.getText(driver, li, "New Contact label Element", action.SCROLLANDBOOLEAN);
							text = text.replace("\n", " ");
							actualCustomAction.add(text);							
						}					

						for(int j=0; j<ExpectedCustomAction.size(); j++)
						{
							if(ExpectedCustomAction.get(j).equals(actualCustomAction.get(j)))
							{
								log(LogStatus.INFO,"Expected result "+ExpectedCustomAction.get(j)+" has been matched with the Actual Result "+actualCustomAction.get(j), YesNo.No);

							}
							else
							{
								log(LogStatus.ERROR,"Expected result "+ExpectedCustomAction.get(j)+" is not matched with the Actual Result "+actualCustomAction.get(j), YesNo.No);
								result.add("Expected result "+ExpectedCustomAction.get(j)+" is not matched with the Actual Result "+actualCustomAction.get(j));
							}
						}

						xPath="//span[text()='"+fieldValue[0]+"']/../following-sibling::input";
						ele=CommonLib.FindElement(driver, xPath, fieldValue[0], action.SCROLLANDBOOLEAN, 50);
						if(CommonLib.sendKeys(driver, ele, fieldValue[1], fieldValue[0], action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, fieldValue[1]+" has been entered in the "+fieldValue[0]+" field", YesNo.No);

							xPath="//h2[text()='New Contact']/../following-sibling::footer//span[text()='Save']";
							ele=CommonLib.FindElement(driver, xPath, "Save button", action.SCROLLANDBOOLEAN, 50);
							if(CommonLib.click(driver, ele, "Save button", action.BOOLEAN))
							{
								log(LogStatus.INFO, "Clicked on Save button", YesNo.No);
								if(CommonLib.checkElementVisibility(driver, gettoastMessage(50), "Save confirmation message", 50))
								{
									log(LogStatus.INFO, "fieldName has been saved", YesNo.No);
								}
								else
								{
									log(LogStatus.ERROR, "fieldName is not saved", YesNo.No);
								}
							}
							else
							{
								log(LogStatus.ERROR, "Not able to click on Save button", YesNo.No);

							}
						}
						else
						{
							log(LogStatus.ERROR, "Not able to enter the value "+fieldValue[1]+" in "+fieldValue[0]+" field", YesNo.No);

						}

						ExpectedCustomAction.removeAll(ExpectedCustomAction);
						actualCustomAction.removeAll(actualCustomAction);
					}
					else if(tabNameAndFieldNameValue[0].equals("New Client"))
					{

						xPath="//div[contains(@class,'slds-p-top--none')]//div[contains(@class,'forcePageBlockSectionRow')]//div[contains(@class,'uiInput--default')]/*[1]";
						elements=CommonLib.FindElements(driver, xPath, "New Client label");
						for(WebElement li:elements)
						{
							text=CommonLib.getText(driver, li, "New Client label Element", action.SCROLLANDBOOLEAN);
							text = text.replace("\n", " ");
							actualCustomAction.add(text);							
						}

						xPath="//div[contains(@class,'forcePageBlockSectionRow')]//div[contains(@class,'slds-form-element_readonly')]";
						ele=CommonLib.FindElement(driver, xPath, "New Client labels", action.SCROLLANDBOOLEAN, 50);
						text=CommonLib.getText(driver, ele, "New Client label Element", action.SCROLLANDBOOLEAN);
						text = text.replace("\n", " ");
						actualCustomAction.add(text);

						for(int j=0; j<ExpectedCustomAction.size(); j++)
						{
							if(ExpectedCustomAction.get(j).equals(actualCustomAction.get(j)))
							{
								log(LogStatus.INFO,"Expected result "+ExpectedCustomAction.get(j)+" has been matched with the Actual Result "+actualCustomAction.get(j), YesNo.No);

							}
							else
							{
								log(LogStatus.ERROR,"Expected result "+ExpectedCustomAction.get(j)+" is not matched with the Actual Result "+actualCustomAction.get(j), YesNo.No);
								result.add("Expected result "+ExpectedCustomAction.get(j)+" is not matched with the Actual Result "+actualCustomAction.get(j));
							}
						}	

						xPath="//span[text()='"+fieldValue[0]+"']/../following-sibling::div//input";
						ele=CommonLib.FindElement(driver, xPath, fieldValue[0], action.SCROLLANDBOOLEAN, 50);
						if(CommonLib.sendKeys(driver, ele, fieldValue[1], fieldValue[0], action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, fieldValue[1]+" has been entered in the "+fieldValue[0]+" field", YesNo.No);
							CommonLib.ThreadSleep(2000);
							xPath="//span[text()='"+fieldValue[0]+"']/../following-sibling::div//ul[@class='lookup__list  visible']/li//div[contains(@class,'primaryLabel')]";
							elements=CommonLib.FindElements(driver, xPath, "New Client label");
							if(CommonLib.getSelectedOptionOfDropDown(driver, elements, fieldValue[0]+"drop down list",fieldValue[1]))
							{
								log(LogStatus.INFO, fieldValue[1]+" has been selected from the "+fieldValue[0]+" field drop down list", YesNo.No);
								xPath="//h2[text()='New Client']/../following-sibling::footer//span[text()='Save']";
								ele=CommonLib.FindElement(driver, xPath, "Save button", action.SCROLLANDBOOLEAN, 50);
								if(CommonLib.click(driver, ele, "Save button", action.BOOLEAN))
								{
									log(LogStatus.INFO, "Clicked on Save button", YesNo.No);
									if(CommonLib.checkElementVisibility(driver, gettoastMessage(50), "Save confirmation message", 50))
									{
										log(LogStatus.INFO, "fieldName has been saved", YesNo.No);
									}
									else
									{
										log(LogStatus.ERROR, "fieldName is not saved", YesNo.No);
									}
								}
								else
								{
									log(LogStatus.ERROR, "Not able to click on Save button", YesNo.No);

								}

							}
							else
							{
								log(LogStatus.ERROR, fieldValue[1]+" is not selected from the "+fieldValue[0]+" field drop down list", YesNo.No);

							}
						}
						else
						{
							log(LogStatus.ERROR, "Not able to enter the value "+fieldValue[1]+" in "+fieldValue[0]+" field", YesNo.No);
						}					
						ExpectedCustomAction.removeAll(ExpectedCustomAction);
						actualCustomAction.removeAll(actualCustomAction);
					}
					else if(tabNameAndFieldNameValue[0].equals("New Affiliation"))
					{
						CommonLib.ThreadSleep(6000);

						xPath="//div[contains(@class,'forcePageBlockSectionRow')]//div[contains(@class,'slds-form-element_readonly')]";
						ele=CommonLib.FindElement(driver, xPath, "New Client labels", action.SCROLLANDBOOLEAN, 50);
						text=CommonLib.getText(driver, ele, "New Client label Element", action.SCROLLANDBOOLEAN);
						text = text.replace("\n", " ");
						actualCustomAction.add(text);

						xPath="//div[contains(@class,'slds-p-top--none')]//div[contains(@class,'forcePageBlockSectionRow')]//div[contains(@class,'uiInput--default')]/*[1]";
						elements=CommonLib.FindElements(driver, xPath, "New Client label");
						for(WebElement li:elements)
						{
							text=CommonLib.getText(driver, li, "New Client label Element", action.SCROLLANDBOOLEAN);
							text = text.replace("\n", " ");
							actualCustomAction.add(text);							
						}

						xPath="//div[contains(@class,'forcePageBlockSectionRow')]//div[contains(@class,'slds-form-element__legend')]";
						ele=CommonLib.FindElement(driver, xPath, "New Client labels", action.SCROLLANDBOOLEAN, 50);
						text=CommonLib.getText(driver, ele, "New Client label Element", action.SCROLLANDBOOLEAN);
						text = text.replace("\n", " ");
						actualCustomAction.add(text);
						for(int j=0; j<ExpectedCustomAction.size(); j++)
						{
							if(ExpectedCustomAction.get(j).equals(actualCustomAction.get(j)))
							{
								log(LogStatus.INFO,"Expected result "+ExpectedCustomAction.get(j)+" has been matched with the Actual Result "+actualCustomAction.get(j), YesNo.No);

							}
							else
							{
								log(LogStatus.ERROR,"Expected result "+ExpectedCustomAction.get(j)+" is not matched with the Actual Result "+actualCustomAction.get(j), YesNo.No);
								result.add("Expected result "+ExpectedCustomAction.get(j)+" is not matched with the Actual Result "+actualCustomAction.get(j));
							}
						}
					}
				}
				else
				{
					log(LogStatus.ERROR, "Not able to "+tabNameAndFieldNameValue[i]+" tab name button", YesNo.No);
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to click on tab ero button", YesNo.No);
			}
		}
		return false;

	}

	public ArrayList<String> VerifyContactsTabOnAdvisorRecordPage(String contact,String affiliation,int timeOut)
	{
		String xPath,text;
		WebElement ele;
		List<WebElement> elements;
		ArrayList<String> actualFieldName=new ArrayList<String>();
		ArrayList<String> expectedFieldName=new ArrayList<String>();
		ArrayList<String> result=new ArrayList<String>();

		if(contact!=null)
		{
			String[] recordTypeAndButtonFieldNameSortValue=contact.split("<buttonBreak>");
			String recordType=recordTypeAndButtonFieldNameSortValue[0];
			String[] ButtonAndFieldNameSortValue=recordTypeAndButtonFieldNameSortValue[1].split("<fieldBreak>");
			String button=ButtonAndFieldNameSortValue[0];
			String[] FieldnameAndSortValue=ButtonAndFieldNameSortValue[1].split("<sortBreak>");
			String fieldnameString=FieldnameAndSortValue[0];
			String[] fieldName=fieldnameString.split("<f>");
			String[] SortValue=FieldnameAndSortValue[1].split("<valueBreak>");
			String sorting=SortValue[0];
			String Value=SortValue[1];

			//	String[] fieldName=ButtonAndFieldName[1].split("<f>");
			for(int i=0; i<fieldName.length; i++)
			{
				expectedFieldName.add(fieldName[i]);
			}
			xPath="//a[contains(@class,'slds-card__header-link') and text()='"+recordType+"']/ancestor::article//button[@class='slds-button slds-button_neutral']";
			ele=CommonLib.FindElement(driver, xPath, recordType+" element", action.SCROLLANDBOOLEAN, 50);
			text=CommonLib.getText(driver, ele, recordType+" text", action.SCROLLANDBOOLEAN);
			if(text.equals(button))
			{
				log(LogStatus.INFO,"Expected result "+recordType+" has been matched with the Actual Result "+text, YesNo.No);
			}
			else
			{
				log(LogStatus.ERROR,"Expected result "+recordType+" is not matched with the Actual Result "+text, YesNo.No);
				result.add("Expected result "+recordType+" is not matched with the Actual Result "+text);		
			}

			xPath="//a[contains(@class,'slds-card__header-link') and text()='"+recordType+"']/ancestor::article//div[contains(@class,'sdgcolheader')]";
			elements=CommonLib.FindElements(driver, xPath, button+" header");
			for(WebElement li:elements)
			{
				text=CommonLib.getText(driver, li, button+" header", action.SCROLLANDBOOLEAN);
				actualFieldName.add(text);							
			}

			for(int i=0; i<expectedFieldName.size(); i++)
			{
				if(expectedFieldName.get(i).equals(actualFieldName.get(i)))
				{
					log(LogStatus.INFO,"Expected result "+expectedFieldName.get(i)+" record name has been matched with the Actual Result "+actualFieldName.get(i), YesNo.No);

				}
				else
				{
					log(LogStatus.ERROR,"Expected result "+expectedFieldName.get(i)+" record name is not matched with the Actual Result "+actualFieldName.get(i), YesNo.No);
					result.add("Expected result "+expectedFieldName.get(i)+" record name is not matched with the Actual Result "+actualFieldName.get(i));
				}
			}

			String[] defaultSortFieldnameOrder=sorting.split("<s>");
			String defaultSortFieldName=defaultSortFieldnameOrder[0];
			String defaultSortOrder=defaultSortFieldnameOrder[1];
			if(defaultSortOrder.equals("ASC"))
			{
				xPath="//a[contains(@class,'slds-card__header-link') and text()='"+recordType+"']/ancestor::article//span[text()='"+defaultSortFieldName+"']//lightning-icon[contains(@class,'arrowup')]";
				ele=CommonLib.FindElement(driver, xPath, defaultSortFieldName+" sorting icon", action.SCROLLANDBOOLEAN, 50);
				if(ele!=null)
				{
					log(LogStatus.INFO,"Default Ascending sorting icon is visible", YesNo.No);
				}
				else
				{
					log(LogStatus.ERROR,"Default Ascending sorting icon is not visible", YesNo.No);
				}
			}
			else if(defaultSortOrder.equals("DESC"))
			{
				xPath="//a[contains(@class,'slds-card__header-link') and text()='"+recordType+"']/ancestor::article//span[text()='"+defaultSortFieldName+"']//lightning-icon[contains(@class,'arrowdown')]";
				ele=CommonLib.FindElement(driver, xPath, defaultSortFieldName+" sorting icon", action.SCROLLANDBOOLEAN, 50);
				if(ele!=null)
				{
					log(LogStatus.INFO,"Default Descending sorting icon is visible", YesNo.No);
				}
				else
				{
					log(LogStatus.ERROR,"Default Descending sorting icon is not visible", YesNo.No);
				}
			}

			xPath="//a[contains(@class,'slds-card__header-link') and text()='"+recordType+"']/ancestor::article//button[@class='slds-button slds-button_neutral']";
			ele=CommonLib.FindElement(driver, xPath, recordType+" element", action.SCROLLANDBOOLEAN, 50);
			CommonLib.click(driver, ele,recordType+" button", action.SCROLLANDBOOLEAN);

			xPath="//h2[text()='"+button+"']";
			ele=CommonLib.FindElement(driver, xPath, button+" element", action.SCROLLANDBOOLEAN, 50);

			if(ele!=null)
			{                                                                                  
				log(LogStatus.INFO,button+" popup has been open", YesNo.No);	
			}
			else
			{
				log(LogStatus.ERROR,"Not able to "+button+" popup  been open", YesNo.No);	
			}


		}
		actualFieldName.removeAll(actualFieldName);
		expectedFieldName.removeAll(expectedFieldName);

		if(affiliation!=null)
		{
			String[] recordTypeAndButtonFieldNameSortValue=affiliation.split("<buttonBreak>");
			String recordType=recordTypeAndButtonFieldNameSortValue[0];
			String[] ButtonAndFieldNameSortValue=recordTypeAndButtonFieldNameSortValue[1].split("<fieldBreak>");
			String button=ButtonAndFieldNameSortValue[0];
			String[] FieldnameAndSortValue=ButtonAndFieldNameSortValue[1].split("<sortBreak>");
			String fieldnameString=FieldnameAndSortValue[0];
			String[] fieldName=fieldnameString.split("<f>");
			String[] SortValue=FieldnameAndSortValue[1].split("<valueBreak>");
			String sorting=SortValue[0];
			String Value=SortValue[1];
			for(int i=0; i<fieldName.length; i++)
			{
				expectedFieldName.add(fieldName[i]);
			}

			xPath="//a[contains(@class,'slds-card__header-link') and text()='"+recordType+"']/ancestor::article//button[@class='slds-button slds-button_neutral']";
			ele=CommonLib.FindElement(driver, xPath, recordType+" element", action.SCROLLANDBOOLEAN, 50);
			text=CommonLib.getText(driver, ele, recordType+" text", action.SCROLLANDBOOLEAN);
			if(text.equals(button))
			{
				log(LogStatus.INFO,"Expected result "+recordType+" has been matched with the Actual Result "+text, YesNo.No);
			}
			else
			{
				log(LogStatus.ERROR,"Expected result "+recordType+" is not matched with the Actual Result "+text, YesNo.No);
				result.add("Expected result "+recordType+" is not matched with the Actual Result "+text);		
			}

			xPath="//a[contains(@class,'slds-card__header-link') and text()='"+recordType+"']/ancestor::article//div[contains(@class,'sdgcolheader')]";
			elements=CommonLib.FindElements(driver, xPath, button+" header");
			for(WebElement li:elements)
			{
				text=CommonLib.getText(driver, li, button+" header", action.SCROLLANDBOOLEAN);
				actualFieldName.add(text);							
			}

			for(int i=0; i<expectedFieldName.size(); i++)
			{
				if(expectedFieldName.get(i).equals(actualFieldName.get(i)))
				{
					log(LogStatus.INFO,"Expected result "+expectedFieldName.get(i)+" record name has been matched with the Actual Result "+actualFieldName.get(i), YesNo.No);

				}
				else
				{
					log(LogStatus.ERROR,"Expected result "+expectedFieldName.get(i)+" record name is not matched with the Actual Result "+actualFieldName.get(i), YesNo.No);
					result.add("Expected result "+expectedFieldName.get(i)+" record name is not matched with the Actual Result "+actualFieldName.get(i));
				}
			}

		}
		return result;

	}


	public boolean VerifySortingOnFields(String data) {
		boolean flag = false;
		String xPath="";
		WebElement ele, arrowUpElement, arrowDownElement;
		List<WebElement> elements;

		String[] FieldsName=data.split("<break>");
		for(int i=0; i<FieldsName.length; i++)
		{
			String[] gridNameAndfieldNameSortOrderNoSortOrder=FieldsName[i].split("<fieldBreak>");
			String gridName=gridNameAndfieldNameSortOrderNoSortOrder[0];
			String [] fieldNameAndSortOrderNoSortOrder=gridNameAndfieldNameSortOrderNoSortOrder[1].split("<defaultSortBreak>");
			String[] fieldName=fieldNameAndSortOrderNoSortOrder[0].split("<f>");
			String[] defaultSortorderAndNoSortOrder=fieldNameAndSortOrderNoSortOrder[1].split("<noSortOrderBreak>");
			String[] defaultSortFieldAndSortOrder=defaultSortorderAndNoSortOrder[0].split("<sortOrder>");
			String defaultSortFieldName=defaultSortFieldAndSortOrder[0];
			String defaultSortOrder=defaultSortFieldAndSortOrder[1];
			
			if(defaultSortOrder.contains("ASC"))
			{
				xPath="//a[text()='"+gridName+"']/ancestor::article//thead//span[text()='"+defaultSortFieldName+"']/lightning-icon";
				if(checkAscOrder(xPath, defaultSortFieldName))
				{
					log(LogStatus.INFO,"Default Ascending order is available on "+defaultSortFieldName, YesNo.No);
				}
				else
				{
					log(LogStatus.ERROR,"Default Ascending order is not available on "+defaultSortFieldName, YesNo.No);
				}
			}
			
			else if(defaultSortOrder.contains("DESC"))
			{
				xPath="//a[text()='"+gridName+"']/ancestor::article//thead//span[text()='"+defaultSortFieldName+"']/lightning-icon";
				if(checkDescOrder(xPath, defaultSortFieldName))
				{
					log(LogStatus.INFO,"Default Descending order is available on "+defaultSortFieldName, YesNo.No);
				}
				else
				{
					log(LogStatus.ERROR,"Default Descending order is not available on "+defaultSortFieldName, YesNo.No);
				}
			}

			for(int j=0; j<fieldName.length; j++)
			{
				xPath="//a[text()='"+gridName+"']/ancestor::article//thead//span[text()='"+fieldName[j]+"']";
				ele=FindElement(driver, xPath, fieldName[j]+" field name", action.SCROLLANDBOOLEAN, 50);
				CommonLib.scrollDownThroughWebelementInCenter(driver, ele, fieldName[j]+" field name");
				if(click(driver, ele,fieldName[j]+" field name", action.BOOLEAN))
				{
					log(LogStatus.INFO,"Clicked on "+fieldName[j]+" field name", YesNo.No);
					xPath="//a[text()='"+gridName+"']/ancestor::article//thead//span[text()='"+fieldName[j]+"']/lightning-icon";
					if(checkAscDescOrder(ele,xPath,fieldName[j]))
					{
						log(LogStatus.INFO,"Arrowup and Arrowdown icon is visible on "+fieldName[j], YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR,"Arrowup and Arrowdown icon is not visible on "+fieldName[j], YesNo.No);
					}
				}
				else
				{
					log(LogStatus.ERROR,"Not able to click on "+fieldName[j]+" field name", YesNo.No);
				}
			}
/*
			for(int j=0; j<fieldName.length; j++)
			{
				xPath="//a[text()='"+gridName+"']/ancestor::article//thead//span[text()='"+fieldName[j]+"']";
				ele=FindElement(driver, xPath, fieldName[j]+" field name", action.SCROLLANDBOOLEAN, 50);
				CommonLib.scrollDownThroughWebelementInCenter(driver, ele, fieldName[j]+" field name");
				xPath="//a[text()='"+gridName+"']/ancestor::article//thead//span[text()='"+fieldName[j]+"']/lightning-icon";
				if(checkAscDescOrder(ele,xPath,fieldName[j]))
				{
					log(LogStatus.INFO,"Arrowup and Arrowdown icon is visible on "+fieldName[j], YesNo.No);

				}
				else
				{
					log(LogStatus.ERROR,"Arrowup and Arrowdown icon is not visible on "+fieldName[j], YesNo.No);
				}

			}
			*/

		}
		return flag;
	}




			
		
		
		
		
	
	

	public ArrayList<String> verifywindowsApp(String contactsField,String affiliationField,String tabName)
	{

		String[] contactsFieldName=contactsField.split("<break>");
		String[] affiliationsField=affiliationField.split("<break>");
		ArrayList<String> result=new ArrayList<String>();
		String xPath="";
		String attName;
		WebElement ele;

		if(tabName.equals("Contacts"))
		{

			if(click(driver, getFirmsContactsTab(30), "Contacts Tab", action.SCROLLANDBOOLEAN)){
				log(LogStatus.INFO, "Clicked on Contact tab button", YesNo.No);	

				for(int i=0; i<contactsFieldName.length; i++)
				{
					if(contactsFieldName[i].equals("Name") || contactsFieldName[i].equals("Firm"))
					{
						xPath="//a[text()='Contacts']/ancestor::article//tbody//tr[1]//td[contains(@data-label,'"+contactsFieldName[i]+"')]//a";
						ele=CommonLib.FindElement(driver, xPath, contactsFieldName[i]+" record", action.SCROLLANDBOOLEAN, 40);
						if(CommonLib.clickUsingJavaScript(driver, ele,contactsFieldName[i]+" record" , action.SCROLLANDBOOLEAN))
						{
							log(LogStatus.INFO, "Clicked on "+contactsFieldName[i]+" record", YesNo.No);			
							String parentID=CommonLib.switchOnWindow(driver);
							xPath="//div[contains(@class,'entityNameTitle')]";
							ele=CommonLib.FindElement(driver, xPath, contactsFieldName[i]+" heading", action.SCROLLANDBOOLEAN, 40);
							if(ele!=null)
							{

								log(LogStatus.INFO, "Redirection is working properly on "+contactsFieldName[i], YesNo.No);
								driver.close();
								driver.switchTo().window(parentID);
							}
							else
							{
								log(LogStatus.ERROR, contactsFieldName[i]+" record is not redirecting on Contact tab", YesNo.No);	
								result.add(contactsFieldName[i]+" record is not redirecting");
								driver.close();
								driver.switchTo().window(parentID);
							}
						}		
						else
						{
							log(LogStatus.ERROR, "Not able to Click on "+contactsFieldName[i], YesNo.No);
							result.add("Not able to Click on "+contactsFieldName[i]);
						}

						xPath="//a[text()='Contacts']/ancestor::article//tbody//tr[2]//td[contains(@data-label,'"+contactsFieldName[i]+"')]//a";
						ele=CommonLib.FindElement(driver, xPath, contactsFieldName[i]+" record", action.SCROLLANDBOOLEAN, 40);
						if(clickUsingJavaScript(driver, ele,contactsFieldName[i]+" record" , action.SCROLLANDBOOLEAN)){
							log(LogStatus.INFO, "Clicked on "+contactsFieldName[i]+" record", YesNo.No);			
							String parentID=CommonLib.switchOnWindow(driver);
							xPath="//div[contains(@class,'entityNameTitle')]";
							ele=CommonLib.FindElement(driver, xPath, contactsFieldName[i]+" heading", action.SCROLLANDBOOLEAN, 40);
							if(ele!=null)
							{
								log(LogStatus.INFO, "Redirection is working properly on "+contactsFieldName[i], YesNo.No);
								driver.close();
								driver.switchTo().window(parentID);
							}
							else
							{
								log(LogStatus.ERROR, contactsFieldName[i]+" record is not redirecting ", YesNo.No);
								result.add(contactsFieldName[i]+" record is not redirecting on Contact tab");
								driver.close();
								driver.switchTo().window(parentID);
							}
						}		
						else
						{
							log(LogStatus.ERROR, "Not able to Click on "+contactsFieldName[i], YesNo.No);
							result.add("Not able to Click on "+contactsFieldName[i]);
						}
					}
					else if(contactsFieldName[i].equals("Email") || contactsFieldName[i].equals("Phone"))
					{
						xPath="//a[text()='Contacts']/ancestor::article//tbody//tr[1]//td[contains(@data-label,'"+contactsFieldName[i]+"')]//a";
						ele=CommonLib.FindElement(driver, xPath, contactsFieldName[i]+" record", action.SCROLLANDBOOLEAN, 40);
						attName=CommonLib.getAttribute(driver, ele, contactsFieldName[i]+" record", "href");
						if(attName!=null)
						{
							if(contactsFieldName[i].equals("Email"))
							{
								if(attName.contains("mailto:"))
								{
									log(LogStatus.INFO, "Pop-up to select Email has been open", YesNo.No);
								}
								else
								{
									log(LogStatus.ERROR, "Pop-up to select Email is not open", YesNo.No);
									result.add("Pop-up to select Email is not open");

								}
							}

							if(contactsFieldName[i].equals("Phone"))
							{
								if(attName.contains("tel:"))
								{
									log(LogStatus.INFO, "Pop-up to select Phone has been open", YesNo.No);
								}
								else
								{
									log(LogStatus.ERROR, "Pop-up to select Phone is not open", YesNo.No);
									result.add("Pop-up to select Phone is not open");

								}
							}
						}
						else
						{
							log(LogStatus.ERROR, "Not able to get the value of the href Attribute", YesNo.No);
							result.add("Not able to get the value of the href Attribute");
						}


						xPath="//a[text()='Contacts']/ancestor::article//tbody//tr[2]//td[contains(@data-label,'"+contactsFieldName[i]+"')]//a";
						ele=CommonLib.FindElement(driver, xPath, contactsFieldName[i]+" record", action.SCROLLANDBOOLEAN, 40);
						attName=CommonLib.getAttribute(driver, ele, contactsFieldName[i]+" record", "href");
						if(attName!=null)
						{
							if(contactsFieldName[i].equals("Email"))
							{
								if(attName.contains("mailto:"))
								{
									log(LogStatus.INFO, "Pop-up to select Email has been open", YesNo.No);
								}
								else
								{
									log(LogStatus.ERROR, "Pop-up to select Email is not open", YesNo.No);
									result.add( "Pop-up to select Email is not open");
								}
							}

							if(contactsFieldName[i].equals("Phone"))
							{
								if(attName.contains("tel:"))
								{
									log(LogStatus.INFO, "Pop-up to select Phone has been open", YesNo.No);
								}
								else
								{
									log(LogStatus.ERROR, "Pop-up to select Phone is not open", YesNo.No);
									result.add("Pop-up to select Phone is not open");
								}
							}
						}
						else
						{
							log(LogStatus.ERROR, "Not able to get the value of the href Attribute", YesNo.No);
							result.add("Not able to get the value of the href Attribute");
						}

					}

					else if(contactsFieldName[i].equals("Details"))
					{
						xPath="//a[text()='Contacts']/ancestor::article//tbody//tr//td[@class='actions']//button";
						ele=CommonLib.FindElement(driver, xPath, contactsFieldName[i]+" record", action.SCROLLANDBOOLEAN, 40);
						if(CommonLib.clickUsingJavaScript(driver, ele,contactsFieldName[i]+" record" , action.SCROLLANDBOOLEAN)){
							log(LogStatus.INFO, "Clicked on "+contactsFieldName[i]+" record", YesNo.No);			
							xPath="//div[contains(@class,'entityNameTitle')]";
							ele=CommonLib.FindElement(driver, xPath, contactsFieldName[i]+" heading", action.SCROLLANDBOOLEAN, 40);
							if(ele!=null)
							{
								log(LogStatus.INFO, "Redirection is working properly on "+contactsFieldName[i], YesNo.No);

							}
							else
							{
								log(LogStatus.ERROR, contactsFieldName[i]+" record is not redirecting on Contact tab", YesNo.No);	
								result.add(contactsFieldName[i]+" record is not redirecting on Contact tab");
							}
						}		
						else
						{
							log(LogStatus.ERROR, "Not able to Click on "+contactsFieldName[i], YesNo.No);
							result.add("Not able to Click on "+contactsFieldName[i]);
						}
					}		
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to click on Contact tab", YesNo.No);	
				result.add( "Not able to click on Contact tab");
			}
		}

		else if(tabName.equals("Affiliations"))
		{
			if(click(driver, getFirmsContactsTab(30), "Get Contacts Tab", action.SCROLLANDBOOLEAN)){
				log(LogStatus.INFO, "Clicked on Contact tab", YesNo.No);	
				CommonLib.ThreadSleep(10000);

				for(int i=0; i<affiliationsField.length; i++)
				{
					if(affiliationsField[i].equals("Name") || affiliationsField[i].equals("Firm"))
					{
						xPath="//a[text()='Contacts']/ancestor::article//tbody//tr[1]//td[contains(@data-label,'"+affiliationsField[i]+"')]//a";
						ele=CommonLib.FindElement(driver, xPath, affiliationsField[i]+" record", action.SCROLLANDBOOLEAN, 40);
						if(CommonLib.clickUsingJavaScript(driver, ele,affiliationsField[i]+" record" , action.SCROLLANDBOOLEAN)){
							log(LogStatus.INFO, "Clicked on "+affiliationsField[i]+" record", YesNo.No);			
							String parentID=CommonLib.switchOnWindow(driver);
							xPath="//div[contains(@class,'entityNameTitle')]";
							ele=CommonLib.FindElement(driver, xPath, affiliationsField[i]+" heading", action.SCROLLANDBOOLEAN, 40);
							if(ele!=null)
							{

								log(LogStatus.INFO, "Redirection is working properly on "+affiliationsField[i], YesNo.No);
								driver.close();
								driver.switchTo().window(parentID);
							}
							else
							{
								log(LogStatus.ERROR, affiliationsField[i]+" is not redirecting", YesNo.No);	
								result.add( affiliationsField[i]+" is not redirecting");
								driver.close();
								driver.switchTo().window(parentID);
							}
						}		
						else
						{
							log(LogStatus.ERROR, "Not able to Click on "+affiliationsField[i], YesNo.No);
							result.add("Not able to Click on "+affiliationsField[i]);
						}

						xPath="//a[text()='Contacts']/ancestor::article//tbody//tr[2]//td[contains(@data-label,'"+affiliationsField[i]+"')]//a";
						ele=CommonLib.FindElement(driver, xPath, affiliationsField[i]+" record", action.SCROLLANDBOOLEAN, 40);
						if(clickUsingJavaScript(driver, ele,affiliationsField[i]+" record" , action.SCROLLANDBOOLEAN)){
							log(LogStatus.INFO, "Clicked on "+affiliationsField[i]+" record", YesNo.No);			
							String parentID=CommonLib.switchOnWindow(driver);
							xPath="//div[contains(@class,'entityNameTitle')]";
							ele=CommonLib.FindElement(driver, xPath, affiliationsField[i]+" heading", action.SCROLLANDBOOLEAN, 40);
							if(ele!=null)
							{
								log(LogStatus.INFO, "Redirection is working properly on "+affiliationsField[i], YesNo.No);
								driver.close();
								driver.switchTo().window(parentID);
							}
							else
							{
								log(LogStatus.ERROR, "Redirection is not working properly on "+affiliationsField[i], YesNo.No);	
								result.add("Redirection is not working properly on "+affiliationsField[i]);
								driver.close();
								driver.switchTo().window(parentID);
							}
						}		
						else
						{
							log(LogStatus.ERROR, "Not able to Click on "+affiliationsField[i], YesNo.No);
							result.add("Not able to Click on "+affiliationsField[i]);
						}
					}
					else if(affiliationsField[i].equals("Email") || affiliationsField[i].equals("Phone"))
					{
						xPath="//a[text()='Contacts']/ancestor::article//tbody//tr[1]//td[contains(@data-label,'"+affiliationsField[i]+"')]//a";
						ele=CommonLib.FindElement(driver, xPath, affiliationsField[i]+" record", action.SCROLLANDBOOLEAN, 40);
						attName=CommonLib.getAttribute(driver, ele, affiliationsField[i]+" record", "href");
						if(attName!=null)
						{
							if(affiliationsField[i].equals("Email"))
							{
								if(attName.contains("mailto:"))
								{
									log(LogStatus.INFO, "Pop-up to select Email has been open", YesNo.No);
								}
								else
								{
									log(LogStatus.ERROR, "Pop-up to select Email is not open", YesNo.No);
									result.add("Pop-up to select Email is not open");

								}
							}

							if(affiliationsField[i].equals("Phone"))
							{
								if(attName.contains("tel:"))
								{
									log(LogStatus.INFO, "Pop-up to select Phone has been open", YesNo.No);
								}
								else
								{
									log(LogStatus.ERROR, "Pop-up to select Phone is not open", YesNo.No);
									result.add( "Pop-up to select Phone is not open");

								}
							}
						}
						else
						{
							log(LogStatus.ERROR, "Not able to get the value of the href Attribute", YesNo.No);
							result.add("Not able to get the value of the href Attribute");
						}


						xPath="//a[text()='Contacts']/ancestor::article//tbody//tr[2]//td[contains(@data-label,'"+affiliationsField[i]+"')]//a";
						ele=CommonLib.FindElement(driver, xPath, affiliationsField[i]+" record", action.SCROLLANDBOOLEAN, 40);
						attName=CommonLib.getAttribute(driver, ele, affiliationsField[i]+" record", "href");
						if(attName!=null)
						{
							if(affiliationsField[i].equals("Email"))
							{
								if(attName.contains("mailto:"))
								{
									log(LogStatus.INFO, "Pop-up to select Email has been open", YesNo.No);
								}
								else
								{
									log(LogStatus.ERROR, "Pop-up to select Email is not open", YesNo.No);
									result.add("Pop-up to select Email is not open");
								}
							}

							if(affiliationsField[i].equals("Phone"))
							{
								if(attName.contains("tel:"))
								{
									log(LogStatus.INFO, "Pop-up to select Phone has been open", YesNo.No);
								}
								else
								{
									log(LogStatus.ERROR, "Pop-up to select Phone is not open", YesNo.No);
									result.add("Pop-up to select Phone is not open");
								}
							}
						}
						else
						{
							log(LogStatus.ERROR, "Not able to get the value of the href Attribute", YesNo.No);
							result.add("Not able to get the value of the href Attribute");
						}

					}

					else if(affiliationsField[i].equals("Details"))
					{
						xPath="//a[text()='Contacts']/ancestor::article//tbody//tr//td[@class='actions']//button";
						ele=CommonLib.FindElement(driver, xPath, affiliationsField[i]+" record", action.SCROLLANDBOOLEAN, 40);
						if(CommonLib.clickUsingJavaScript(driver, ele,affiliationsField[i]+" record" , action.SCROLLANDBOOLEAN)){
							log(LogStatus.INFO, "Clicked on "+affiliationsField[i]+" record", YesNo.No);			
							xPath="//div[contains(@class,'entityNameTitle')]";
							ele=CommonLib.FindElement(driver, xPath, affiliationsField[i]+" heading", action.SCROLLANDBOOLEAN, 40);
							if(ele!=null)
							{
								log(LogStatus.INFO, "Redirection is working properly on "+affiliationsField[i], YesNo.No);

							}
							else
							{
								log(LogStatus.ERROR, "Redirection is not working properly on "+affiliationsField[i], YesNo.No);	
								result.add("Redirection is not working properly on "+affiliationsField[i]);
							}
						}		
						else
						{
							log(LogStatus.ERROR, "Not able to Click on "+affiliationsField[i], YesNo.No);
							result.add("Not able to Click on "+affiliationsField[i]);
						}
					}		
				}
			}
			else
			{
				log(LogStatus.ERROR, "Not able to click on Contact tab", YesNo.No);		
				result.add("Not able to click on Contact tab");
			}

		}
		return result;

	}

	public static boolean datePickerHandle(WebDriver driver, WebElement MonthSelector, WebElement MonthPreviousButton,
			String elementName, String Year, String Month, String Date) {
		boolean flag = false;
		try {

			int j = 0;
			for (int i = 0; i < 13; i++) {
				if (j < 12) {
					if (MonthSelector.getText().contains(Month)) {
						appLog.info("Month Selected : " + Month);
						break;
					} else {
						if(CommonLib.clickUsingJavaScript(driver, MonthPreviousButton, "", action.BOOLEAN))
						{
							appLog.info("CLicked on Month Previous Button");
						}
						else
						{
							appLog.error("Not able to click on month previous button");
						}
						j++;
					}
				} else {
					appLog.error("Month Format Is Not Correct");
					return false;

				}
			}
			String xpath = "//lightning-select//select";
			WebElement YearSelector = FindElement(driver, xpath, "YearSelector", action.SCROLLANDBOOLEAN, 25);
			if (CommonLib.selectVisibleTextFromDropDown(driver, YearSelector, "YearSelector: " + Year, Year))

			{
				appLog.info("Select The Year: " + Year);
				CommonLib.ThreadSleep(8000);
				List<WebElement> DaySelector = FindElements(driver,
						"//table[@class='slds-datepicker__month']//tr//td[not(contains(@class,'slds-day_adjacent-month'))]/span",
						"dateSelector");
				for (WebElement day : DaySelector) {
					try {

						if (Integer.parseInt(Date) < 10 && Date.length()==2) {
							Date = Date.replace("0", "");
						}
						if (day.getText().trim().equalsIgnoreCase(Date)) {
							if (click(driver, day, "", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, Date + " Date is Selected", YesNo.No);
								flag = true;
							} else {
								log(LogStatus.INFO, Date + " Date is Not Selected", YesNo.No);
								flag = false;
							}
						}
					}
					catch(StaleElementReferenceException e)
					{
						e.getMessage();
					}
				}

			} else {
				appLog.error("Not ABle to Select Year: " + Year);
			}

		} catch (Exception e) {
			flag = false;

			log(LogStatus.ERROR, elementName + " Date is Not Selected", YesNo.No);
			log(LogStatus.ERROR, e.getMessage(), YesNo.No);
		}

		return flag;

	}

	public ArrayList<String> DetailTabVerify(ArrayList<String> ExpectedAllTab,
			HashMap<String, ArrayList<String>> labelListMap) {

		FirmPage FP = new FirmPage(driver);
		ArrayList<String> ActaulDetailsTab = new ArrayList<String>();
		ArrayList<String> result = new ArrayList<String>();
		String extraFields[] = { "Commitment Details", "Workspace" };

		for (WebElement li : FP.getDetailsTab()) {
			// for loop for each carousel headline
			String getHeaderValue = CommonLib.getText(driver, li, "DetailsTab", action.SCROLLANDBOOLEAN);
			ArrayList<String> actualLabelNameList = new ArrayList<String>();
			ActaulDetailsTab.add(getHeaderValue);
			if (ExpectedAllTab.containsAll(ActaulDetailsTab)) {
				log(LogStatus.PASS, ActaulDetailsTab + " has been verified ", YesNo.Yes);

				String labelNamesXpath = "//span[text()='" + getHeaderValue
						+ "']//..//..//following-sibling::div//label  | //span[text()='" + getHeaderValue
						+ "']//..//..//following-sibling::div//span[contains(@class,'field-label')]";
				List<WebElement> getListOfLabelElement = CommonLib.FindElements(driver, labelNamesXpath,
						getHeaderValue + "'s field");
				for (WebElement labelElement : getListOfLabelElement) {
					String labelName = CommonLib.getText(driver, labelElement, "Label Name", action.SCROLLANDBOOLEAN);
					actualLabelNameList.add(labelName);
				}
				ArrayList<String> expectedLabelNames = new ArrayList<String>();
				for (Entry<String, ArrayList<String>> entry : labelListMap.entrySet()) {
					if (entry.getKey() == getHeaderValue) {
						expectedLabelNames = entry.getValue();
					}
				}
				if (expectedLabelNames.containsAll(actualLabelNameList)
						&& actualLabelNameList.containsAll(expectedLabelNames)) {
					log(LogStatus.PASS, expectedLabelNames + " has been verified for " + getHeaderValue, YesNo.Yes);
				}

			} else {
				log(LogStatus.FAIL, "Details tabs fileds are not matched ", YesNo.Yes);
				result.add("Details tab fields are not matched");
			}
		}

		for (int i = 0; i < extraFields.length; i++) {
			if (ExpectedAllTab.contains(extraFields[i])) {
				log(LogStatus.FAIL, "Detail Tab fields contains Extra field " + extraFields[i], YesNo.Yes);
			} else {
				log(LogStatus.PASS, "Verified : Detail Tab fields does not contains " + extraFields[i], YesNo.Yes);
				result.add("Verified : Detail Tab fields does not contains extra field " + extraFields[i]);
			}
		}
		return result;
	}


	public ArrayList<String> VerifyInlineEditingForContactsAndAffiliationsGrid(String record)
	{
		String xPath="";
		WebElement ele;
		Random random=new Random();
		ArrayList<String> columnName=new ArrayList<String>();
		ArrayList<String> inputType=new ArrayList<String>();
		ArrayList<String> result=new ArrayList<String>();
		String[] data=record.split("<break>");
		for(int i=0; i<data.length; i++)
		{
			String[] gridNameAndCoulmnNameInputType=data[i].split("<gridBreak>");
			String gridName=gridNameAndCoulmnNameInputType[0];
			String[] CoulmnNameAndInputType=gridNameAndCoulmnNameInputType[1].split("<f>");
			for(int j=0; j<CoulmnNameAndInputType.length; j++)
			{
				String[] ColumnNameAndDatatype=CoulmnNameAndInputType[j].split("<v>");
				columnName.add(ColumnNameAndDatatype[0]);
				inputType.add(ColumnNameAndDatatype[1]);
			}

			if(columnName.size()==inputType.size())
			{
				for(int j=0; j<columnName.size(); j++)
				{

					xPath="//a[text()='"+gridName+"']/ancestor::article//td[contains(@data-label,'"+columnName.get(j)+"')]//span";
					ele=CommonLib.FindElement(driver, xPath, columnName.get(j)+" heading", action.SCROLLANDBOOLEAN, 50);
					if(ele!=null)
					{
						if(CommonLib.mouseOverOperation(driver, ele))
						{
							log(LogStatus.INFO, "Mouse has been hover to "+columnName.get(j), YesNo.Yes);
							xPath="//a[text()='"+gridName+"']/ancestor::article//td[contains(@data-label,'"+columnName.get(j)+"')]//button[@title='Edit']";
							ele=CommonLib.FindElement(driver, xPath, columnName.get(j)+" record", action.BOOLEAN, 50);
							if(ele!=null)
							{
								log(LogStatus.INFO, "Edit icon has been located of "+columnName.get(j), YesNo.Yes);
								if(CommonLib.clickUsingJavaScript(driver, ele, columnName.get(j)+" record", action.SCROLLANDBOOLEAN)){
									log(LogStatus.INFO, "Clicked on "+columnName.get(j)+" record edit icon", YesNo.No);	

									if(inputType.get(j).equals("textbox"))
									{

										xPath="//a[text()='"+gridName+"']/ancestor::article//td[contains(@data-label,'"+columnName.get(j)+"')]//input";
										ele=CommonLib.FindElement(driver, xPath, columnName.get(j)+" record", action.SCROLLANDBOOLEAN, 50);
										CommonLib.scrollDownThroughWebelementInCenter(driver, ele, columnName.get(j)+" record");
										if(ele!=null)
										{
											if(columnName.get(j).equals("Phone"))
											{
												if(CommonLib.sendKeys(driver, ele, random.nextInt(1000000000)+"", columnName.get(j)+" input type",action.BOOLEAN))
												{
													log(LogStatus.INFO, "Phone number has been passed", YesNo.No);
												}
												else
												{
													log(LogStatus.ERROR, "Not able to enter the Phone numberd", YesNo.No);
													result.add("Not able to enter the Phone numberd");
												}
											}
											else if(columnName.get(j).equals("Email"))
											{
												if(CommonLib.sendKeys(driver, ele,"Automation"+random.nextInt(100000)+"@yopmail.com", columnName.get(j)+" input type",action.BOOLEAN))
												{
													log(LogStatus.INFO, "Email id has been passed", YesNo.No);
												}	
												else
												{
													log(LogStatus.ERROR, "Not able to enter the Email id", YesNo.No);
													result.add("Not able to enter the Email id");
												}
											}
											else 
											{
												if(CommonLib.sendKeys(driver, ele,"Automation"+random.nextInt(100000), columnName.get(j)+" input type",action.BOOLEAN))
												{

													log(LogStatus.INFO, "Value has been passed", YesNo.No);
												}	
												else
												{
													log(LogStatus.ERROR, "Not able to enter the value", YesNo.No);
													result.add("Not able to enter the value");
												}
											}

										}
										else
										{
											log(LogStatus.ERROR, "Not able to get the "+columnName.get(j)+" input icon", YesNo.Yes);	
											result.add("Not able to get the "+columnName.get(j)+" input icon");
										}
									}
									else if(inputType.get(j).equals("calender"))
									{

										xPath="//a[text()='"+gridName+"']/ancestor::article//td[contains(@data-label,'"+columnName.get(j)+"')]//input";
										ele=CommonLib.FindElement(driver, xPath, columnName.get(j)+" record", action.SCROLLANDBOOLEAN, 50);
										if(ele!=null)
										{
											if(CommonLib.click(driver, ele, columnName.get(j)+" input", action.BOOLEAN))
											{
												if(datePickerHandle(driver,monthInDatePicker(50),previousMonthButtonInDatePicker(50),"test","2022","July","22"))
												{
													log(LogStatus.INFO, "Date has been selected from the calender", YesNo.No);

												}
												else
												{
													log(LogStatus.ERROR, "Date is not selected from the calender", YesNo.No);
													result.add( "Date is not selected from the calender");
												}

											}
											else
											{
												log(LogStatus.ERROR, "Not able to click on "+columnName.get(j)+" input icon", YesNo.Yes);
												result.add("Not able to click on "+columnName.get(j)+" input icon");
											}
										}
										else
										{
											log(LogStatus.ERROR, "Not able to get the "+columnName.get(j)+" input icon", YesNo.Yes);	
											result.add( "Not able to get the "+columnName.get(j)+" input icon");
										}
									}

									else if(inputType.get(j).equals("multipicklist"))
									{
										xPath="//span[text()='Available']/parent::div//span[@class='slds-truncate']";
										ele=CommonLib.FindElement(driver, xPath, columnName.get(j)+" multipicklist record", action.SCROLLANDBOOLEAN, 50);
										if(ele!=null)
										{
											if(CommonLib.click(driver, ele, "Availabel picklist",action.BOOLEAN))
											{
												log(LogStatus.INFO, "multipicklist element has located", YesNo.Yes);
												xPath="//button[@title='Move selection to Chosen']";
												ele=CommonLib.FindElement(driver, xPath, "Move selection to Chosen icon ", action.SCROLLANDBOOLEAN, 50);
												if(ele!=null)
												{
													if(CommonLib.click(driver, ele, "Move selection to Chosen icon", action.BOOLEAN))
													{
														log(LogStatus.INFO, "clicked on Move selection to Chosen icon", YesNo.No);

													}
													else
													{
														log(LogStatus.ERROR, "Not able to clicked on Move selection to Chosen icon", YesNo.No);
														result.add("Not able to clicked on Move selection to Chosen icon");
													}
												}
												else
												{
													log(LogStatus.ERROR, "Not able to get the Move selection to Chosen locator", YesNo.No);
													result.add("Not able to get the Move selection to Chosen locator");
												}

											}
											else
											{
												log(LogStatus.ERROR, "Not able to click on Availabel list", YesNo.No);
												result.add("Not able to click on Availabel list");
											}
										}
										else
										{
											log(LogStatus.ERROR, "Not able to locate the multipicklist locator", YesNo.No);
											result.add("Not able to locate the multipicklist locator");
										}
									}
									else
									{
										log(LogStatus.ERROR, "Input type of record is incorrect", YesNo.No);
										result.add("Input type of record is incorrect");
									}

								}
								else
								{
									log(LogStatus.ERROR, "Not able to get the "+columnName.get(i)+" record edit icon", YesNo.No);
									result.add( "Not able to get the "+columnName.get(i)+" record edit icon");
								}

							}
							else
							{
								log(LogStatus.ERROR, "Not able to get the "+columnName.get(i)+" edit icon", YesNo.No);	
								result.add("Not able to get the "+columnName.get(i)+" edit icon");
							}
						}
						else
						{
							log(LogStatus.ERROR, "Not able to hover the record ", YesNo.No);
							result.add("Not able to hover the record ");

						}
					}
					else
					{
						log(LogStatus.ERROR, "Not able to get the locator of "+gridName+" record", YesNo.No);
						result.add("Not able to get the locator of "+gridName+" record");
					}

				}
			}
			else
			{
				log(LogStatus.ERROR, "Column Name size and Input type size are not matched. Please provide the correct input", YesNo.Yes);
				result.add( "Column Name size and Input type size are not matched. Please provide the correct input");
			}
/*
			xPath="//a[text()='"+gridName+"']/ancestor::article//div[contains(@class,'slds-form_inline')]";
			ele=CommonLib.FindElement(driver, xPath, gridName+ " ", action.SCROLLANDBOOLEAN, 50);
			CommonLib.doubleClickUsingAction(driver, ele); */
			xPath="//a[text()='Contacts']/ancestor::article//button[text()='New Contact']";
			ele=CommonLib.FindElement(driver, xPath, gridName+ " ", action.SCROLLANDBOOLEAN, 50);
			if(CommonLib.click(driver, ele, "Page border", action.BOOLEAN))
			{	
				xPath="//button[@class='slds-button slds-button_icon slds-modal__close closeIcon slds-button_icon-bare slds-button_icon-inverse']";
				ele=CommonLib.FindElement(driver, xPath, gridName+ " ", action.SCROLLANDBOOLEAN, 50);				
				CommonLib.click(driver, ele, "Page border", action.BOOLEAN); 
				
				log(LogStatus.INFO, "Clicked on Page border of "+gridName, YesNo.No);
				xPath="//a[text()='"+gridName+"']/ancestor::article//button[@title='Save']";
				ele=CommonLib.FindElement(driver, xPath, gridName+ " ", action.SCROLLANDBOOLEAN, 50);
				if(CommonLib.click(driver, ele, "save button", action.SCROLLANDBOOLEAN))
				{
					log(LogStatus.INFO, "Clicked on Save of "+gridName, YesNo.No);
					if(CommonLib.checkElementVisibility(driver, getsaveConfirmationMsg(50), "save confirmation message", 50))
					{
						log(LogStatus.INFO, gridName+" record is has been saved", YesNo.No);
					}
					else
					{
						log(LogStatus.ERROR, gridName+" record is not saved", YesNo.No);
						result.add(gridName+" record is not saved");
					}
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on save button", YesNo.No);
					result.add("Not able to click on save button");
				}

			}
			else
			{
				log(LogStatus.ERROR, "Not able to click on page border", YesNo.No);
				result.add("Not able to click on page border");
			}		
			inputType.removeAll(inputType);
			columnName.removeAll(columnName);
		}
		return result;
	}

	public ArrayList<String> ClientTabVerify(ArrayList<String> ExpectedAllTab,
			HashMap<String, ArrayList<String>> labelListMap) {

		FirmPage FP = new FirmPage(driver);
		ArrayList<String> ActaulClientTab = new ArrayList<String>();
		ArrayList<String> result = new ArrayList<String>();


		for (WebElement li : FP.getClientList()) {
			// for loop for each carousel headline
			String getHeaderValue = CommonLib.getText(driver, li, "Client Tab", action.SCROLLANDBOOLEAN);
			ArrayList<String> actualLabelNameList = new ArrayList<String>();
			ActaulClientTab.add(getHeaderValue);
			if (ExpectedAllTab.containsAll(ActaulClientTab)) {
				log(LogStatus.PASS, ActaulClientTab + " has been verified ", YesNo.Yes);

				String labelNamesXpath = "//div//span[contains(@class,'link_reset')]| //div//span[contains(@class,'sdgheader')]";

				List<WebElement> getListOfLabelElement = CommonLib.FindElements(driver, labelNamesXpath,
						getHeaderValue + "'s field");
				for (WebElement labelElement : getListOfLabelElement) {
					String labelName = CommonLib.getText(driver, labelElement, "Label Name", action.SCROLLANDBOOLEAN);
					actualLabelNameList.add(labelName);
				}
				ArrayList<String> expectedLabelNames = new ArrayList<String>();
				for (Entry<String, ArrayList<String>> entry : labelListMap.entrySet()) {
					if (entry.getKey().equals(getHeaderValue)) {
						expectedLabelNames = entry.getValue();
					}
				}
				if (expectedLabelNames.containsAll(actualLabelNameList)
						&& actualLabelNameList.containsAll(expectedLabelNames)) {
					log(LogStatus.PASS, expectedLabelNames + " has been verified for " + getHeaderValue, YesNo.Yes);
				}

			} else {
				log(LogStatus.FAIL, "Client tabs fields are not matched ", YesNo.No);
				result.add("Client tabs fields are not matched");
			}
		}


		return result;
	}
	
	public boolean verifyButtonAndCreateRecord(String button, String[] labelNameAndValue)
	{
		String xPath="";
		WebElement ele;

		xPath="//button[text()='"+button+"']";
		ele=CommonLib.FindElement(driver, xPath, button+ " button", action.SCROLLANDBOOLEAN, 50);

		if(ele!=null)
		{
			log(LogStatus.PASS, button+" button is visible", YesNo.No);
			sa.assertTrue(true,button+" button is visible");
			if(CommonLib.click(driver, ele, button+" button", action.SCROLLANDBOOLEAN))
			{	
				log(LogStatus.INFO, "Clickd on "+button+" ", YesNo.No);				
				if(CommonLib.checkElementVisibility(driver, getpopUpHeading(50), "Popup Heading", 50))
				{
					log(LogStatus.PASS, button+" popup has been open ", YesNo.No);	
					sa.assertTrue(true,button+" popup has been open ");
										
				}
				else
				{
					log(LogStatus.FAIL, button+" popup is not open ", YesNo.No);	
					sa.assertTrue(false,button+" popup is not open ");
				}
				
			}
			else
			{
				log(LogStatus.ERROR, "Not able to get the "+button+" locator", YesNo.No);
			}
		}
		else
		{
			log(LogStatus.FAIL, button+" button is not visible", YesNo.Yes);			
			sa.assertTrue(false,button+" button is not visible");
		}
		return false;

	}
	
	
	public boolean checkAscOrder(String xPath,String fieldName)
	{
		boolean flag=false;
		WebElement ele = CommonLib.FindElement(driver, xPath, "arrowup", action.SCROLLANDBOOLEAN, 30);
		if(ele!=null)
		{
			String className = ele.getAttribute("class");
			if (className.contains("arrowup")) {
				log(LogStatus.INFO, " Arrowup Icon is visible and "+fieldName+" field Name", YesNo.No);
			} else {
				log(LogStatus.ERROR, " Arrowup Icon is not visible on "+fieldName+" field Name", YesNo.No);
			}
		}
		else
		{
			log(LogStatus.ERROR, "Arrowup icon is not Visible on"+fieldName+" field Name", YesNo.No);
		}
		return flag;
	}
	
	public boolean checkDescOrder(String xPath,String fieldName)
	{
		boolean flag=false;
		WebElement ele = CommonLib.FindElement(driver, xPath, "arrowup", action.SCROLLANDBOOLEAN, 30);
		if(ele!=null)
		{
			String className = ele.getAttribute("class");
			if (className.contains("arrowdown")) {
				log(LogStatus.INFO, "arrowdown Icon is visible and "+fieldName+" field Name", YesNo.No);
			} else {
				log(LogStatus.ERROR, "arrowdown Icon is not visible on "+fieldName+" field Name", YesNo.No);
			}
		}
		else
		{
			log(LogStatus.ERROR, "arrowdown icon is not Visible on"+fieldName+" field Name", YesNo.No);
		}
		return flag;
	}

	public boolean checkAscDescOrder(WebElement columnName, String xPath,String fieldName) {

		boolean flag=false;
		int k=0;

		for (int i = 0; i < 2; i++) {
			// Check what class Name contain (up or down)
			WebElement ele = CommonLib.FindElement(driver, xPath, "arrowup", action.SCROLLANDBOOLEAN, 30);
			if(ele!=null)
			{
				String className = ele.getAttribute("class");
				if (className.contains("arrowup")) {
					log(LogStatus.INFO, " Arrowup Icon is visible and clicked on "+fieldName+" field Name", YesNo.No);
					k++;

				} else if (className.contains("arrowdown")) {
					log(LogStatus.INFO, " ArrowdownBtn Icon is Visible and clicked on "+fieldName+" field Name", YesNo.No);
					k++;
				}
				else
				{
					log(LogStatus.ERROR, "Either Arrowdown icon or Arrowup icon is not Visible on "+fieldName+" field Name", YesNo.No);	
				}

				if(CommonLib.click(driver, columnName, "Column element", action.BOOLEAN))
				{
					log(LogStatus.INFO, "Clicked on "+fieldName+" field name ", YesNo.No);
				}
				else
				{
					log(LogStatus.ERROR, "Not able to click on column name "+fieldName+" field Name", YesNo.No);
				}
			}
			else
			{
				log(LogStatus.ERROR, "Arrowdown icon or Arrowup icon is not Visible "+fieldName+" field Name", YesNo.No);
			}
			
		}
		
		if(k!=0)
		{
		    flag=true;
		}
		return flag;	
	}

}

