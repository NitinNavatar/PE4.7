package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.*;


import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

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


}
