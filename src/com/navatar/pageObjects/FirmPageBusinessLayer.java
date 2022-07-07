package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.ThreadSleep;
import static com.navatar.generic.CommonLib.click;
import static com.navatar.generic.CommonLib.log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.CommonLib;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
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
								System.out.println("field  "+filterFiled[j]);
								
								xPath="//div[@id='filterPanelFieldCriterion"+j+"']//span[@class='test-operatorWrapper']";
								ele=CommonLib.FindElement(driver, xPath, "Field Label", action.SCROLLANDBOOLEAN, 50);
								String filterOperator=CommonLib.getText(driver, ele, "Filter Operator", action.SCROLLANDBOOLEAN);
								System.out.println("filter Operator  "+fOperator[j]);
								
								xPath="//div[@id='filterPanelFieldCriterion"+j+"']//span[@class='test-operandsWrapper']";
								ele=CommonLib.FindElement(driver, xPath, "Field Label", action.SCROLLANDBOOLEAN, 50);
								String filterOperand=CommonLib.getText(driver, ele, "Filter Operand", action.SCROLLANDBOOLEAN);
								System.out.println("Operand  "+FOperand[j]);
								
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

/*	
	
	public ArrayList<String> verifyFilterOnListView1(String[] listViewName, String[] filter, String[] field, String[] Operator, String[] filterValue,String[] filterCondition )
	{
		String xPath="";
		WebElement ele;
		ArrayList<String> result=new ArrayList<String>();

		for(int i=0; i<filterCondition.length; i++)
		{

			if(CommonLib.click(driver, getClickedOnRecentlyViewed(30), "Recently Viewed", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on the recently view button", YesNo.No);

				xPath="//ul[@class='slds-dropdown__list slds-show']//span[text()='"+listViewName[i]+"']";
				ele=CommonLib.FindElement(driver, xPath, listViewName[i],action.SCROLLANDBOOLEAN , 50);

				if(CommonLib.click(driver, ele, listViewName[i]+" element", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on "+listViewName[i]+" element"+" button", YesNo.No);

					if(listViewName[i].equals("All Advisors") || listViewName[i].equals("All Companies") || listViewName[i].equals("All Firms")|| listViewName[i].equals("All Institutions")|| listViewName[i].equals("All Lenders") || listViewName[i].equals("All Limited Partners") || listViewName[i].equals("All Portfolio Companies"))
					{

						if(CommonLib.click(driver, getshowFilter(50), "Show filter", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Show filter button", YesNo.No);

							String scopeLabelFilter=CommonLib.getText(driver, getscopeLabelFilter(50), "scope label filter", action.SCROLLANDBOOLEAN);
							if(scopeLabelFilter.equals(filter))
							{
								log(LogStatus.INFO, scopeLabelFilter+ " is visible in the Filter by Owner", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, scopeLabelFilter+ " is not visible in the Filter by Owner", YesNo.Yes);
								result.add(scopeLabelFilter+ " is not visible in the Filter by Owner");
							}

							String filterFieldLabel=CommonLib.getText(driver, getfilterFieldLabel(50), "Filter field label", action.SCROLLANDBOOLEAN);
							String filterOperator=CommonLib.getText(driver, getfilterOperator(50), "Filter Operator", action.SCROLLANDBOOLEAN);
							String filterOperand=CommonLib.getText(driver, getfilterOperand(50), "Filter Operand", action.SCROLLANDBOOLEAN);

							if(filterFieldLabel.equalsIgnoreCase(field) && filterOperator.equalsIgnoreCase(Operator) && filterOperand.equalsIgnoreCase(filterValue[i]))
							{
								log(LogStatus.INFO, filterFieldLabel+", "+filterOperator+" and "+filterOperand+ " have been matched" , YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Either Filter label name : "+filterFieldLabel+" or filter Operator :"+filterOperator+" Or Filter operand :"+filterOperand+ " are not matced with Filter label name : "+field+", filter Operator :"+Operator+", Filter operand :"+filterValue[i]+ "", YesNo.No);
								result.add("Either Filter label name : "+filterFieldLabel+" or filter Operator :"+filterOperator+" Or Filter operand :"+filterOperand+ " are not matced with Filter label name : "+field+", filter Operator :"+Operator+", Filter operand :"+filterValue[i]+ "");
							}
							CommonLib.refresh(driver);
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Show filter button", YesNo.Yes);
							result.add("Not able to click on Show filter button");
						}

					}
					else if(listViewName[i].equals("All Intermediaries"))
					{

						if(CommonLib.click(driver, getshowFilter(50), "Show filter", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Show filter button", YesNo.No);

							String scopeLabelFilter=CommonLib.getText(driver, getscopeLabelFilter(50), "scope label filter", action.SCROLLANDBOOLEAN);
							if(scopeLabelFilter.equals(filter))
							{
								log(LogStatus.INFO, scopeLabelFilter+ " is visible in the Filter by Owner", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, scopeLabelFilter+ " is not visible in the Filter by Owner", YesNo.Yes);
								result.add(scopeLabelFilter+ " is not visible in the Filter by Owner");
							}

							String filterFieldLabel=CommonLib.getText(driver, getfilterFieldLabel(50), "Filter field label", action.SCROLLANDBOOLEAN);
							String filterOperator=CommonLib.getText(driver, getfilterOperator(50), "Filter Operator", action.SCROLLANDBOOLEAN);
							String filterOperand=CommonLib.getText(driver, getfilterOperand(50), "Filter Operand", action.SCROLLANDBOOLEAN);

							if(filterFieldLabel.equalsIgnoreCase(field) && filterOperator.equalsIgnoreCase(Operator) && filterOperand.equalsIgnoreCase(filterValue[i]))
							{
								log(LogStatus.INFO, filterFieldLabel+", "+filterOperator+" and "+filterOperand+ " have been matched" , YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Either Filter label name : "+filterFieldLabel+" or filter Operator :"+filterOperator+" Or Filter operand :"+filterOperand+ " are not matced with Filter label name : "+field+", filter Operator :"+Operator+", Filter operand :"+filterValue[i]+ "", YesNo.No);
								result.add("Either Filter label name : "+filterFieldLabel+" or filter Operator :"+filterOperator+" Or Filter operand :"+filterOperand+ " are not matced with Filter label name : "+field+", filter Operator :"+Operator+", Filter operand :"+filterValue[i]+ "");
							}


							String scopeLabelFilter1=CommonLib.getText(driver, getscopeLabelFilter(50), "scope label filter", action.SCROLLANDBOOLEAN);
							if(scopeLabelFilter1.equals(filter))
							{
								log(LogStatus.INFO, scopeLabelFilter1+ " is visible in the Filter by Owner", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, scopeLabelFilter1+ " is not visible in the Filter by Owner", YesNo.Yes);
								result.add(scopeLabelFilter1+ " is not visible in the Filter by Owner");
							}

							String filterFieldLabel1=CommonLib.getText(driver, getfilterFieldLabel1(50), "Filter field label", action.SCROLLANDBOOLEAN);
							String filterOperator1=CommonLib.getText(driver, getfilterOperator1(50), "Filter Operator", action.SCROLLANDBOOLEAN);
							String filterOperand1=CommonLib.getText(driver, getfilterOperand1(50), "Filter Operand", action.SCROLLANDBOOLEAN);

							if(filterFieldLabel1.equalsIgnoreCase(field) && filterOperator1.equalsIgnoreCase(Operator) && filterOperand1.equalsIgnoreCase(filterValue[i]))
							{
								log(LogStatus.INFO, filterFieldLabel1+", "+filterOperator1+" and "+filterOperand1+ " have been matched" , YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, "Either Filter label name : "+filterFieldLabel1+" or filter Operator :"+filterOperator1+" Or Filter operand :"+filterOperand1+ " are not matced with Filter label name : "+field+", filter Operator :"+Operator+", Filter operand :"+filterValue[i]+ "", YesNo.No);
								result.add("Either Filter label name : "+filterFieldLabel1+" or filter Operator :"+filterOperator1+" Or Filter operand :"+filterOperand1+ " are not matced with Filter label name : "+field+", filter Operator :"+Operator+", Filter operand :"+filterValue[i]+ "");
							}

							CommonLib.refresh(driver);
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Show filter button", YesNo.Yes);
							result.add("Not able to click on Show filter button");
						}

					}

					else if(listViewName[i].equals("Recently Viewed Firms"))
					{

						if(CommonLib.click(driver, getshowFilter(50), "Show filter", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Show filter button", YesNo.No);

							String scopeLabelFilter=CommonLib.getText(driver, getscopeLabelFilter(50), "scope label filter", action.SCROLLANDBOOLEAN);
							if(scopeLabelFilter.equals(filter))
							{
								log(LogStatus.INFO, scopeLabelFilter+ " is visible in the Filter by Owner", YesNo.No);
							}
							else
							{
								log(LogStatus.ERROR, scopeLabelFilter+ " is not visible in the Filter by Owner", YesNo.Yes);
								result.add(scopeLabelFilter+ " is not visible in the Filter by Owner");
							}

							CommonLib.refresh(driver);
						}
						else
						{
							log(LogStatus.ERROR, "Not able to click on Show filter button", YesNo.Yes);
							result.add("Not able to click on Show filter button");
						}

					}

					else if(listViewName[i].equals("Recently Viewed  (Pinned list)"))
					{

						ele=getshowFilter(50);
						if(ele==null)
						{
							log(LogStatus.INFO, "Filter icon is disable", YesNo.No);
						}
						else
						{
							log(LogStatus.ERROR, "Filter icon is not disable", YesNo.Yes);
							result.add("Filter icon is not disable");
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

*/

}
