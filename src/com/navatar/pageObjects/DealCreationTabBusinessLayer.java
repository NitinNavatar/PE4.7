package com.navatar.pageObjects;

import com.navatar.generic.BaseLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.ContactPageFieldLabelText;
import com.navatar.generic.EnumConstants.InstitutionPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.SoftAssert;
import com.relevantcodes.extentreports.LogStatus;
import static com.navatar.generic.EnumConstants.*;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.AppListeners.*;

public class DealCreationTabBusinessLayer extends DealCreationTab {

	public DealCreationTabBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public boolean createNewDealPipeLineforExistingSourceFirmAndSourceContact(String environment, String mode, String companyName, String stage, String existingSourceFirm,
			String existingSourceContact, String source) {
		
		boolean flag = false;;
		
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			switchToFrame(driver, 10, getnavatarSetUpTabFrame_Lighting(environment, 10));
		}
		
		WebElement ele = FindElement(driver, "//div[@class='ContentStart']//p[text()='Deal Creation']",
				"Deal Creation Page", action.SCROLLANDBOOLEAN, 10);
		if (ele != null) {
			appLog.info("Deal Creation Page is open");

			if (sendKeys(driver, getCompanyNameTextBox(environment, mode, 10), companyName,
					"Company Name : " + companyName, action.BOOLEAN)) {
				appLog.info("Entered Value on Company Name Text Box : " + companyName);
				ThreadSleep(1000);
				
				if (source != null) {
					if (selectVisibleTextFromDropDown(driver, getSourceDropDownList(environment, mode, 10),
							"Source Drop Down List", source)) {
						appLog.info("Selected value from Source Deop down List : " + source);
					} else {
						
						BaseLib.sa.assertTrue(false, "Not Able to Select value from Source Deop down List : " + source);
						appLog.error("Not Able to Select value from Source Deop down List : " + source);
					}
				}
				
				if(existingSourceFirm!=null){
					if (sendKeys(driver, getSourceFirmSelectExistingBox(environment, mode, 10), existingSourceFirm,
							"Source Firm : " + existingSourceFirm, action.BOOLEAN)) {
						appLog.info("Entered Value on Source Firm Text Box : " + existingSourceFirm);
					}else{
						
						BaseLib.sa.assertTrue(false, "Not Able to entered value on Source Firm Text Box : " + existingSourceFirm);
						appLog.error("Not Able to entered value on Source Firm Text Box : " + existingSourceFirm);
					}
				}
				
				if(existingSourceContact!=null){
					if (sendKeys(driver, getSourceContactSelectExistingBox(environment, mode, 10), existingSourceContact,
							"Source Firm : " + existingSourceContact, action.BOOLEAN)) {
						appLog.info("Entered Value on Source Contact Text Box : " + existingSourceContact);
					}else{
					
						BaseLib.sa.assertTrue(false, "Not Able to entered value on Source Contact Text Box : " + existingSourceContact);
						appLog.error("Not Able to entered value on Source Contact Text Box : " + existingSourceContact);
					}
				}
				

				String actualPipeLineName = getValueFromElementUsingJavaScript(driver,
						getPipeLineNameTextBox(environment, mode, 10), "PipeLine Value");
				String monthAndYear = getSystemDate("MMM") + " " + getSystemDate("yyyy");
				String expectedPipeLineName = companyName + " " + "-" + " " + monthAndYear;

				if ((expectedPipeLineName).equals(actualPipeLineName)) {
					appLog.info("PipeLine Name Value Verified : " + actualPipeLineName);
				} else {
					BaseLib.sa.assertTrue(false, "PipeLine Name Value Not Verified - Actual : " + actualPipeLineName
							+ "     Expected : " + expectedPipeLineName);
					appLog.error("PipeLine Name Value Not Verified - Actual : " + actualPipeLineName + "     Expected : "
							+ expectedPipeLineName);
				}
				ThreadSleep(1000);
				
				if (selectVisibleTextFromDropDown(driver, getStageDropDownList(environment, mode, 10),
						"Stage Drop Down List", stage)) {
					appLog.info("Selected value from Stage Deop down List : " + stage);
					
					if (click(driver, getCreateDealBtn(environment, mode, 10), "Create Deal Button", action.BOOLEAN)) {
						appLog.info("Clicked on Create Deal Button");	
						
						if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
							switchToDefaultContent(driver);
						}
						
						ThreadSleep(3000);
						flag=true;
						if (getPipelineNameInViewMode(environment, mode, 60) != null) {
							String pipeLineNameViewMode = getText(driver, getPipelineNameInViewMode(environment, mode, 60),
									"Fund Name", action.BOOLEAN);
							if (expectedPipeLineName.equalsIgnoreCase(pipeLineNameViewMode)) {
								appLog.info("PipeLine created successfully.:" + pipeLineNameViewMode);
								
							} else {
						
								BaseLib.sa.assertTrue(false, "PipeLine Created But not Not Verified - Actual : " + pipeLineNameViewMode+"  Expected  : "+expectedPipeLineName);
								appLog.error("PipeLine Created But not Not Verified - Actual : " + pipeLineNameViewMode+"  Expected  : "+expectedPipeLineName);
							}
						} else {
							
							BaseLib.sa.assertTrue(false, "Not able to find PipeLine Name in View Mode");
							appLog.error("Not able to find PipeLine Name in View Mode");
						}
						
						
					} else {
				
						BaseLib.sa.assertTrue(false, "Not Able to Click on Create Deal Button");
						appLog.error("Not Able to Click on Create Deal Button");
					}
					
				} else {
			
					BaseLib.sa.assertTrue(false, "Not Able to Select value from Stage Deop down List : " + stage);
					appLog.error("Not Able to Select value from Stage Deop down List : " + stage);
				}

			} else {
			
				BaseLib.sa.assertTrue(false, "Not Able to entered value on Company Name Text Box : " + companyName);
				appLog.error("Not Able to entered value on Company Name Text Box : " + companyName);
			}

		} else {
			
			BaseLib.sa.assertTrue(false, "Deal Creation Page is not open");
			appLog.error("Deal Creation Page is not open");
		}
		if (!mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			switchToDefaultContent(driver);
		}
		return flag;

	}
	
	public boolean addingMoreSelectFieldAndValuesToDealInformationLayout(String environment,String mode,String valuesToBeOnLeftSide,String valuesToBeOnRightSide){
		
		boolean flag = true;
		if(valuesToBeOnLeftSide!=null){
			String[] leftValues = valuesToBeOnLeftSide.split(",");
			for(int i=0;i<leftValues.length-1;i++){
				if (click(driver, getLeftAddIconforDealInformationLayout(environment, 10), "", action.SCROLLANDBOOLEAN)) {
					appLog.info("Click on Left Added Icon iteration : "+i);
					ThreadSleep(500);
				} else {
					flag = false;
					appLog.error("Not Able to Click on Left Added Icon iteration : "+i);
					log(LogStatus.INFO, "Not Able to Click on Left Added Icon iteration : "+i, YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Not Able to Click on Left Added Icon iteration : "+i);
				}
			}
			
		List<WebElement> leftAutoCompleteEle = getLeftAutoCompleteBoxforDealInformationLayout(environment, 10);
		
		if (!leftAutoCompleteEle.isEmpty() && leftAutoCompleteEle.size() == leftValues.length) {
			
			for(int i=0;i<leftValues.length;i++){
				
				if (sendKeys(driver, leftAutoCompleteEle.get(i), leftValues[i], "Value added on Left : "+leftValues[i], action.SCROLLANDBOOLEAN)) {
					appLog.info("Entered Value for Left iteration "+i+" : "+leftValues[i]);
					ThreadSleep(1000);
					WebElement ele = FindElement(driver, "//li/a[text()='"+leftValues[i]+"']", "Value to be clicked on LEFT : "+leftValues[i], action.SCROLLANDBOOLEAN, 10);
					
					if (click(driver, ele, "Clicked on "+leftValues[i], action.SCROLLANDBOOLEAN)) {
						appLog.info("Clicked for Left iteration "+i+" : "+leftValues[i]);	
					} else {
						flag = false;
						appLog.error("Not Able to Click for Left iteration "+i+" : "+leftValues[i]);
						log(LogStatus.SKIP, "Not Able to Click for Left iteration "+i+" : "+leftValues[i], YesNo.Yes);
						BaseLib.sa.assertTrue(false, "Not Able to Click for Left iteration "+i+" : "+leftValues[i]);
					}
					
				} else {
					flag = false;
					appLog.error("Not Able to Entered Value for Left iteration "+i+" : "+leftValues[i]);
					log(LogStatus.SKIP, "Not Able to Entered Value for Left iteration "+i+" : "+leftValues[i], YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Not Able to Entered Value for Left iteration "+i+" : "+leftValues[i]);
				}
				
			}
			
		} else {
			flag = false;
			appLog.error("Left List is empty or List is not eqaul to Array");
			log(LogStatus.SKIP, "Left List is empty or List is not eqaul to Array", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "Left List is empty or List is not eqaul to Array");
		}
		
	}
		
		// Right
		ThreadSleep(3000);
		if(valuesToBeOnRightSide!=null){
			String[] rightValues = valuesToBeOnRightSide.split(",");
			for(int i=0;i<rightValues.length-1;i++){
				if (click(driver, getRightAddIconforDealInformationLayout(environment, 10), "", action.SCROLLANDBOOLEAN)) {
					appLog.info("Click on Right Added Icon iteration : "+i);
				} else {
					flag = false;
					appLog.error("Not Able to Click on Right Added Icon iteration : "+i);
					log(LogStatus.INFO, "Not Able to Click on Right Added Icon iteration : "+i, YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Not Able to Click on Right Added Icon iteration : "+i);
				}
			}
			
		List<WebElement> rightAutoCompleteEle = getRightAutoCompleteBoxforDealInformationLayout(environment, 10);
		
		if (!rightAutoCompleteEle.isEmpty() && rightAutoCompleteEle.size() == rightValues.length) {
			
			for(int i=0;i<rightValues.length;i++){
				
				if (sendKeys(driver, rightAutoCompleteEle.get(i), rightValues[i], "Value added on Right : "+rightValues[i], action.SCROLLANDBOOLEAN)) {
					appLog.info("Entered Value for Right iteration "+i+" : "+rightValues[i]);
					ThreadSleep(1000);
					WebElement ele = FindElement(driver, "//li/a[text()='"+rightValues[i]+"']", "Value to be clicked on Right : "+rightValues[i], action.SCROLLANDBOOLEAN, 10);
					
					if (click(driver, ele, "Clicked on "+rightValues[i], action.SCROLLANDBOOLEAN)) {
						appLog.info("Clicked for Right iteration "+i+" : "+rightValues[i]);	
					} else {
						flag = false;
						appLog.error("Not Able to Click for Right iteration "+i+" : "+rightValues[i]);
						log(LogStatus.SKIP, "Not Able to Click for Right iteration "+i+" : "+rightValues[i], YesNo.Yes);
						BaseLib.sa.assertTrue(false, "Not Able to Click for Right iteration "+i+" : "+rightValues[i]);
					}
					
				} else {
					flag = false;
					appLog.error("Not Able to Entered Value for Right iteration "+i+" : "+rightValues[i]);
					log(LogStatus.SKIP, "Not Able to Entered Value for Right iteration "+i+" : "+rightValues[i], YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Not Able to Entered Value for Right iteration "+i+" : "+rightValues[i]);
				}
				
			}
			
		} else {
			flag = false;
			appLog.error("Right List is empty or List is not eqaul to Array");
			log(LogStatus.SKIP, "Right List is empty or List is not eqaul to Array", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "Right List is empty or List is not eqaul to Array");
		}
		
	}
		
		return flag;
		
	}
	
	public boolean addingMoreSelectFieldAndValuesToNewSourceFirmLayout(String environment,String mode,String valuesToBeAdded){
		
		boolean flag = true;
		if(valuesToBeAdded!=null){
			String[] values = valuesToBeAdded.split(",");
			for(int i=0;i<values.length-1;i++){
				if (click(driver, getAddIconforNewSourceFirmLayout(environment, 10), "", action.SCROLLANDBOOLEAN)) {
					appLog.info("Click on Added Icon iteration : "+i);
				} else {
					flag = false;
					appLog.error("Not Able to Click on  Added Icon iteration : "+i);
					log(LogStatus.INFO, "Not Able to Click on  Added Icon iteration : "+i, YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Not Able to Click on  Added Icon iteration : "+i);
				}
			}
			
		List<WebElement> autoCompleteEle = getAutoCompleteBoxforNewSourceFirmLayout(environment, 10);
		
		if (!autoCompleteEle.isEmpty() && autoCompleteEle.size() == values.length) {
			
			for(int i=0;i<values.length;i++){
				
				if (sendKeys(driver, autoCompleteEle.get(i), values[i], "Value added on  : "+values[i], action.SCROLLANDBOOLEAN)) {
					appLog.info("Entered Value for Left iteration "+i+" : "+values[i]);
					ThreadSleep(1000);
					WebElement ele = FindElement(driver, "//li/a[text()='"+values[i]+"']", "Value to be clicked on  : "+values[i], action.SCROLLANDBOOLEAN, 10);
					
					if (click(driver, ele, "Clicked on "+values[i], action.SCROLLANDBOOLEAN)) {
						appLog.info("Clicked for  iteration "+i+" : "+values[i]);	
					} else {
						flag = false;
						appLog.error("Not Able to Click for iteration "+i+" : "+values[i]);
						log(LogStatus.SKIP, "Not Able to Click for iteration "+i+" : "+values[i], YesNo.Yes);
						BaseLib.sa.assertTrue(false, "Not Able to Click for iteration "+i+" : "+values[i]);
					}
					
				} else {
					flag = false;
					appLog.error("Not Able to Entered Value for iteration "+i+" : "+values[i]);
					log(LogStatus.SKIP, "Not Able to Entered Value for iteration "+i+" : "+values[i], YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Not Able to Entered Value for iteration "+i+" : "+values[i]);
				}
				
			}
			
		} else {
			flag = false;
			appLog.error(" List is empty or List is not eqaul to Array");
			log(LogStatus.SKIP, " List is empty or List is not eqaul to Array", YesNo.Yes);
			BaseLib.sa.assertTrue(false, " List is empty or List is not eqaul to Array");
		}
		
	}
	
		
		return flag;
		
	}
	
	public boolean addingMoreSelectFieldAndValuesToNewSourceContactLayout(String environment,String mode,String valuesToBeAdded){
		
		boolean flag = true;
		if(valuesToBeAdded!=null){
			String[] values = valuesToBeAdded.split(",");
			for(int i=0;i<values.length-1;i++){
				if (click(driver, getAddIconforNewSourceContactLayout(environment, 10), "", action.SCROLLANDBOOLEAN)) {
					appLog.info("Click on Added Icon iteration : "+i);
				} else {
					flag = false;
					appLog.error("Not Able to Click on  Added Icon iteration : "+i);
					log(LogStatus.INFO, "Not Able to Click on  Added Icon iteration : "+i, YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Not Able to Click on  Added Icon iteration : "+i);
				}
			}
			
		List<WebElement> autoCompleteEle = getAutoCompleteBoxforNewSourceContactLayout(environment, 10);
		
		if (!autoCompleteEle.isEmpty() && autoCompleteEle.size() == values.length) {
			
			for(int i=0;i<values.length;i++){
				
				if (sendKeys(driver, autoCompleteEle.get(i), values[i], "Value added on  : "+values[i], action.SCROLLANDBOOLEAN)) {
					appLog.info("Entered Value for Left iteration "+i+" : "+values[i]);
					ThreadSleep(1000);
					WebElement ele = FindElement(driver, "//li/a[text()='"+values[i]+"']", "Value to be clicked on  : "+values[i], action.SCROLLANDBOOLEAN, 10);
					
					if (click(driver, ele, "Clicked on "+values[i], action.SCROLLANDBOOLEAN)) {
						appLog.info("Clicked for  iteration "+i+" : "+values[i]);	
					} else {
						flag = false;
						appLog.error("Not Able to Click for iteration "+i+" : "+values[i]);
						log(LogStatus.SKIP, "Not Able to Click for iteration "+i+" : "+values[i], YesNo.Yes);
						BaseLib.sa.assertTrue(false, "Not Able to Click for iteration "+i+" : "+values[i]);
					}
					
				} else {
					flag = false;
					appLog.error("Not Able to Entered Value for iteration "+i+" : "+values[i]);
					log(LogStatus.SKIP, "Not Able to Entered Value for iteration "+i+" : "+values[i], YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Not Able to Entered Value for iteration "+i+" : "+values[i]);
				}
				
			}
			
		} else {
			flag = false;
			appLog.error(" List is empty or List is not eqaul to Array");
			log(LogStatus.SKIP, " List is empty or List is not eqaul to Array", YesNo.Yes);
			BaseLib.sa.assertTrue(false, " List is empty or List is not eqaul to Array");
		}
		
	}
	
		
		return flag;
		
	}

	public boolean verificationOfDealCreationSideMenuTabViewMode(String environment,String mode,DealCreationPageLayout dealCreationPageLayout,String labels){
		String xpath;
		boolean flag=false;
		if(dealCreationPageLayout.toString().equalsIgnoreCase(DealCreationPageLayout.Deal_Information.toString())){
			xpath="((//div[@class='ContentBox'])[1]//table)[3]//td/label";
		}else if(dealCreationPageLayout.toString().equalsIgnoreCase(DealCreationPageLayout.New_Source_Firm.toString())){
			xpath="((//div[@class='ContentBox'])[2]//table)[3]//td/label";
		}else{
			xpath="((//div[@class='ContentBox contentBox_shadow'])[3]//table)[2]//td/label";
		}
		
		List<WebElement> labelEles = FindElements(driver, xpath, "Labels");
		List<String> returnlist  = compareMultipleList(driver, labels, labelEles);
		if (returnlist.isEmpty()) {
			appLog.info("Label for layout verified : "+dealCreationPageLayout.toString());
			flag=true;
		}
		else {
			appLog.error("Label for layout Not verified : "+dealCreationPageLayout.toString());
			BaseLib.sa.assertTrue(false, "Label for layout Not verified : "+dealCreationPageLayout.toString());
		}
		
		return flag;
	}

	public SoftAssert verifyingInstitutionRequiredFieldListDealInformationLayout(String environment,String mode,String dropdDownLayout,String[][] rowValues){
		SoftAssert saa = new SoftAssert();
		
		String defaultSelectedValue = getSelectedOptionOfDropDown(driver, getInstitution_DealInfo_DropDownList(environment, mode, 10), "Company", "text");
		if(defaultSelectedValue!=null){
			appLog.info("Default Selected Value verified Expected : "+"Company");	
		}else{
			appLog.error("Default Selected Value Not verified Expected : "+"Company");
			saa.assertTrue(false, "Default Selected Value Not verified Expected : "+"Company");	
		}
		
		if(dropdDownLayout!=null){
		if(selectVisibleTextFromDropDown(driver, getInstitution_DealInfo_DropDownList(environment, mode, 10), "LAYOUT : "+dropdDownLayout, dropdDownLayout)){
			appLog.error("Able to Select Layout: "+dropdDownLayout);
		}else{
			appLog.error("Not Able to Select Layout: "+dropdDownLayout);
			saa.assertTrue(false, "Not Able to Select Layout: "+dropdDownLayout);		
		}
		}
		
		String[] columnHeadingforRequiredFieldList ={"Field Label","Field Name/API Name","Data Type"};
		for (String headingValue : columnHeadingforRequiredFieldList) {
		WebElement eleHeader =	FindElement(driver, "//div[@id='RightMenuContentSectionAccountRequireCompId']//th/div[contains(text(),'"+headingValue+"')]", headingValue, action.SCROLLANDBOOLEAN, 10);
		
		if (eleHeader!=null) {
			appLog.info("Deal Information Institution Heading Verified : "+headingValue);
		}
		else {
			appLog.error("Deal Information Institution Heading Not Verified : "+headingValue);
			saa.assertTrue(false, "Deal Information Institution Heading Not Verified : "+headingValue);
		}
		}
		
		String fullXpath ;
		WebElement eleRowValue;
		for (String[] rowArray : rowValues) {
			String fieldLabelvalueXpath = "//div[@id='RightMenuContentSectionAccountRequireCompId']//tr[@class='slds-hint-parent']/td[text()='"+rowArray[0]+"']";
			String apiNameValueXpath ="/following-sibling::td[text()='"+rowArray[1]+"']";
			String dataTypeValueXpath ="/following-sibling::td[text()='"+rowArray[2]+"']";
			fullXpath = fieldLabelvalueXpath+apiNameValueXpath+dataTypeValueXpath;
			 eleRowValue =	FindElement(driver, fullXpath, rowArray[0]+" : "+rowArray[1]+" : "+rowArray[2], action.SCROLLANDBOOLEAN, 10);
			 if(eleRowValue!=null){
					appLog.info("Row with Data *** "+rowArray[0]+" : "+rowArray[1]+" : "+rowArray[2]+" ***  Verified"); 
			 }else{
					appLog.error("Row with Data <<  "+rowArray[0]+" : "+rowArray[1]+" : "+rowArray[2]+" >> not verified");
					saa.assertTrue(false, "Row with Data <<  "+rowArray[0]+" : "+rowArray[1]+" : "+rowArray[2]+" >> not verified"); 
			 }
			
		}
		
		return saa;
		
	}
	
	public SoftAssert verifyingPipeLineRequiredFieldListDealInformationLayout(String environment,String mode,String dropdDownLayout,String[][] rowValues){
		SoftAssert saa = new SoftAssert();
		
		String defaultSelectedValue = getSelectedOptionOfDropDown(driver, getPipeLine_DealInfo_DropDownList(environment, mode, 10), "Pipeline Layout", "text");
		if(defaultSelectedValue!=null){
			appLog.info("Default Selected Value verified for PipeLIne Layout Expected : "+"Pipeline Layout");	
		}else{
			appLog.error("Default Selected Value Not verified for PipeLIne Layout Expected : "+"Pipeline Layout");
			saa.assertTrue(false, "Default Selected Value Not verified for PipeLIne Layout Expected : "+"Pipeline Layout");	
		}
		
		if(dropdDownLayout!=null){
		if(selectVisibleTextFromDropDown(driver, getPipeLine_DealInfo_DropDownList(environment, mode, 10), "LAYOUT : "+dropdDownLayout, dropdDownLayout)){
			appLog.error("Able to Select Layout: "+dropdDownLayout);
		}else{
			appLog.error("Not Able to Select Layout: "+dropdDownLayout);
			saa.assertTrue(false, "Not Able to Select Layout: "+dropdDownLayout);		
		}
		}
		
		String[] columnHeadingforRequiredFieldList ={"Field Label","Field Name/API Name","Data Type"};
		for (String headingValue : columnHeadingforRequiredFieldList) {
		WebElement eleHeader =	FindElement(driver, "//div[@id='RightMenuContentSectionPipelineRequireCompId']//th/div[contains(text(),'"+headingValue+"')]", headingValue, action.SCROLLANDBOOLEAN, 10);
		
		if (eleHeader!=null) {
			appLog.info("Deal Information PipeLine Heading Verified : "+headingValue);
		}
		else {
			appLog.error("Deal Information PipeLine Heading Not Verified : "+headingValue);
			saa.assertTrue(false, "Deal Information PipeLine Heading Not Verified : "+headingValue);
		}
		}
		
		String fullXpath ;
		WebElement eleRowValue;
		for (String[] rowArray : rowValues) {
			String fieldLabelvalueXpath = "//div[@id='RightMenuContentSectionPipelineRequireCompId']//tr[@class='slds-hint-parent']/td[text()='"+rowArray[0]+"']";
			String apiNameValueXpath ="/following-sibling::td[text()='"+rowArray[1]+"']";
			String dataTypeValueXpath ="/following-sibling::td[text()='"+rowArray[2]+"']";
			fullXpath = fieldLabelvalueXpath+apiNameValueXpath+dataTypeValueXpath;
			 eleRowValue =	FindElement(driver, fullXpath, rowArray[0]+" : "+rowArray[1]+" : "+rowArray[2], action.SCROLLANDBOOLEAN, 10);
			 if(eleRowValue!=null){
					appLog.info("Row with Data *** "+rowArray[0]+" : "+rowArray[1]+" : "+rowArray[2]+" ***  Verified"); 
			 }else{
					appLog.error("Row with Data <<  "+rowArray[0]+" : "+rowArray[1]+" : "+rowArray[2]+" >> not verified");
					saa.assertTrue(false, "Row with Data <<  "+rowArray[0]+" : "+rowArray[1]+" : "+rowArray[2]+" >> not verified"); 
			 }
			
		}
		
		return saa;
		
	}
	
	public SoftAssert verifyingInstitutionRequiredFieldListNewSourceFirmLayout(String environment,String mode,String dropdDownLayout,String[][] rowValues){
		SoftAssert saa = new SoftAssert();
		
		String defaultSelectedValue = getSelectedOptionOfDropDown(driver, getInstitution_NewSourceFirm_DropDownList(environment, mode, 10), "Company", "text");
		if(defaultSelectedValue!=null){
			appLog.info("Default Selected Value verified for Institution Layout Expected : "+"Pipeline Layout");	
		}else{
			appLog.error("Default Selected Value Not verified for Institution Layout Expected : "+"Pipeline Layout");
			saa.assertTrue(false, "Default Selected Value Not verified for Institution Layout Expected : "+"Pipeline Layout");	
		}
		
		if(dropdDownLayout!=null){
		if(selectVisibleTextFromDropDown(driver, getInstitution_NewSourceFirm_DropDownList(environment, mode, 10), "LAYOUT : "+dropdDownLayout, dropdDownLayout)){
			appLog.error("Able to Select Layout: "+dropdDownLayout);
		}else{
			appLog.error("Not Able to Select Layout: "+dropdDownLayout);
			saa.assertTrue(false, "Not Able to Select Layout: "+dropdDownLayout);		
		}
		}
		
		String[] columnHeadingforRequiredFieldList ={"Field Label","Field Name/API Name","Data Type"};
		for (String headingValue : columnHeadingforRequiredFieldList) {
		WebElement eleHeader =	FindElement(driver, "//div[@id='RightMenuContentSectionSourceFirmRequireCompId']//th/div[contains(text(),'"+headingValue+"')]", headingValue, action.SCROLLANDBOOLEAN, 10);
		
		if (eleHeader!=null) {
			appLog.info("New Source Firm Institution Heading Verified : "+headingValue);
		}
		else {
			appLog.error("New Source Firm Institution Heading Not Verified : "+headingValue);
			saa.assertTrue(false, "New Source Firm Institution Heading Not Verified : "+headingValue);
		}
		}
		
		String fullXpath ;
		WebElement eleRowValue;
		for (String[] rowArray : rowValues) {
			String fieldLabelvalueXpath = "//div[@id='RightMenuContentSectionSourceFirmRequireCompId']//tr[@class='slds-hint-parent']/td[text()='"+rowArray[0]+"']";
			String apiNameValueXpath ="/following-sibling::td[text()='"+rowArray[1]+"']";
			String dataTypeValueXpath ="/following-sibling::td[text()='"+rowArray[2]+"']";
			fullXpath = fieldLabelvalueXpath+apiNameValueXpath+dataTypeValueXpath;
			 eleRowValue =	FindElement(driver, fullXpath, rowArray[0]+" : "+rowArray[1]+" : "+rowArray[2], action.SCROLLANDBOOLEAN, 10);
			 if(eleRowValue!=null){
					appLog.info("Row with Data *** "+rowArray[0]+" : "+rowArray[1]+" : "+rowArray[2]+" ***  Verified"); 
			 }else{
					appLog.error("Row with Data <<  "+rowArray[0]+" : "+rowArray[1]+" : "+rowArray[2]+" >> not verified");
					saa.assertTrue(false, "Row with Data <<  "+rowArray[0]+" : "+rowArray[1]+" : "+rowArray[2]+" >> not verified"); 
			 }
			
		}
		
		return saa;
		
	}

	public SoftAssert verifyingContactRequiredFieldListNewSourceContactLayout(String environment,String mode,String dropdDownLayout,String[][] rowValues){
		SoftAssert saa = new SoftAssert();
		
		String defaultSelectedValue = getSelectedOptionOfDropDown(driver, getContact_NewSourceContact_DropDownList(environment, mode, 10), "Contact Layout", "text");
		if(defaultSelectedValue!=null){
			appLog.info("Default Selected Value verified for Contact Layout Expected : "+"Pipeline Layout");	
		}else{
			appLog.error("Default Selected Value Not verified for Contact Layout Expected : "+"Pipeline Layout");
			saa.assertTrue(false, "Default Selected Value Not verified for Contact Layout Expected : "+"Pipeline Layout");	
		}
		
		if(dropdDownLayout!=null){
		if(selectVisibleTextFromDropDown(driver, getContact_NewSourceContact_DropDownList(environment, mode, 10), "LAYOUT : "+dropdDownLayout, dropdDownLayout)){
			appLog.error("Able to Select Layout: "+dropdDownLayout);
		}else{
			appLog.error("Not Able to Select Layout: "+dropdDownLayout);
			saa.assertTrue(false, "Not Able to Select Layout: "+dropdDownLayout);		
		}
		}
		
		String[] columnHeadingforRequiredFieldList ={"Field Label","Field Name/API Name","Data Type"};
		for (String headingValue : columnHeadingforRequiredFieldList) {
		WebElement eleHeader =	FindElement(driver, "//div[@id='RightMenuContentSectionSourceContactRequireCompId']//th/div[contains(text(),'"+headingValue+"')]", headingValue, action.SCROLLANDBOOLEAN, 10);
		
		if (eleHeader!=null) {
			appLog.info("New Source Contact Heading Verified : "+headingValue);
		}
		else {
			appLog.error("New Source Contact Heading Not Verified : "+headingValue);
			saa.assertTrue(false, "New Source Contact Heading Not Verified : "+headingValue);
		}
		}
		
		String fullXpath ;
		WebElement eleRowValue;
		for (String[] rowArray : rowValues) {
			String fieldLabelvalueXpath = "//div[@id='RightMenuContentSectionSourceContactRequireCompId']//tr[@class='slds-hint-parent']/td[text()='"+rowArray[0]+"']";
			String apiNameValueXpath ="/following-sibling::td[text()='"+rowArray[1]+"']";
			String dataTypeValueXpath ="/following-sibling::td[text()='"+rowArray[2]+"']";
			fullXpath = fieldLabelvalueXpath+apiNameValueXpath+dataTypeValueXpath;
			 eleRowValue =	FindElement(driver, fullXpath, rowArray[0]+" : "+rowArray[1]+" : "+rowArray[2], action.SCROLLANDBOOLEAN, 10);
			 if(eleRowValue!=null){
					appLog.info("Row with Data *** "+rowArray[0]+" : "+rowArray[1]+" : "+rowArray[2]+" ***  Verified"); 
			 }else{
					appLog.error("Row with Data <<  "+rowArray[0]+" : "+rowArray[1]+" : "+rowArray[2]+" >> not verified");
					saa.assertTrue(false, "Row with Data <<  "+rowArray[0]+" : "+rowArray[1]+" : "+rowArray[2]+" >> not verified"); 
			 }
			
		}
		
		return saa;
		
	}
	
	public boolean createNewDealPipeLine(String environment, String mode, String companyName, String stage, String source) {
		
		boolean flag = true;
		
		if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			switchToFrame(driver, 10, getnavatarSetUpTabFrame_Lighting(environment, 10));
		}
		
		WebElement ele = FindElement(driver, "//div[@class='ContentStart']//p[text()='Deal Creation']",
				"Deal Creation Page", action.SCROLLANDBOOLEAN, 10);
		if (ele != null) {
			appLog.info("Deal Creation Page is open");

			if (sendKeys(driver, getCompanyNameTextBox(environment, mode, 10), companyName,
					"Company Name : " + companyName, action.BOOLEAN)) {
				appLog.info("Entered Value on Company Name Text Box : " + companyName);
				ThreadSleep(1000);
				
				if (source != null) {
					if (selectVisibleTextFromDropDown(driver, getSourceDropDownList(environment, mode, 10),
							"Source Drop Down List", source)) {
						appLog.info("Selected value from Source Deop down List : " + source);
					} else {
						flag=false;
						BaseLib.sa.assertTrue(false, "Not Able to Select value from Source Deop down List : " + source);
						appLog.error("Not Able to Select value from Source Deop down List : " + source);
					}
				}
				
				

				String actualPipeLineName = getValueFromElementUsingJavaScript(driver,
						getPipeLineNameTextBox(environment, mode, 10), "PipeLine Value");
				String monthAndYear = getSystemDate("MMM") + " " + getSystemDate("yyyy");
				String expectedPipeLineName = companyName + " " + "-" + " " + monthAndYear;

				if ((expectedPipeLineName).equals(actualPipeLineName)) {
					appLog.info("PipeLine Name Value Verified : " + actualPipeLineName);
				} else {
					flag=false;
					BaseLib.sa.assertTrue(false, "PipeLine Name Value Not Verified - Actual : " + actualPipeLineName
							+ "     Expected : " + expectedPipeLineName);
					appLog.error("PipeLine Name Value Not Verified - Actual : " + actualPipeLineName + "     Expected : "
							+ expectedPipeLineName);
				}
				ThreadSleep(1000);
				
				if (selectVisibleTextFromDropDown(driver, getStageDropDownList(environment, mode, 10),
						"Stage Drop Down List", stage)) {
					appLog.info("Selected value from Stage Deop down List : " + stage);
					
					
				} else {
					flag=false;
					BaseLib.sa.assertTrue(false, "Not Able to Select value from Stage Deop down List : " + stage);
					appLog.error("Not Able to Select value from Stage Deop down List : " + stage);
				}

			} else {
				flag=false;
				BaseLib.sa.assertTrue(false, "Not Able to entered value on Company Name Text Box : " + companyName);
				appLog.error("Not Able to entered value on Company Name Text Box : " + companyName);
			}

		} else {
			flag=false;
			BaseLib.sa.assertTrue(false, "Deal Creation Page is not open");
			appLog.error("Deal Creation Page is not open");
		}
		
		return flag;

	}
	
	public boolean addSourceFirmToDealCreation(String environment,String mode,Existing existingsSourceFirm,String sourceFirm,String sourceFirmLabels,String sourceFirmValues){
		
		if(existingsSourceFirm.toString().equalsIgnoreCase(Existing.Yes.toString())){
			if (sendKeys(driver, getSourceFirmSelectExistingBox(environment, mode, 10), sourceFirm,
					"Source Firm : " + sourceFirm, action.BOOLEAN)) {
				appLog.info("Entered Value on Source Firm Text Box : " + sourceFirm);
				return true;
			}else{
				BaseLib.sa.assertTrue(false, "Not Able to entered value on Source Firm Text Box : " + sourceFirm);
				appLog.error("Not Able to entered value on Source Firm Text Box : " + sourceFirm);
			}
		
		}else{
		
			if (sendKeys(driver, getNewSourceFirmTextBox(environment, mode, 10), sourceFirm+"\t",
					"Source Firm : " + sourceFirm, action.BOOLEAN)) {
				appLog.info("Entered Value on Source Firm Text Box : " + sourceFirm);
				
				if(sourceFirmLabels!=null){
				WebElement popUpHeader = FindElement(driver, "//h2[text()='New Source Firm']", "New Source Firm popUp", action.BOOLEAN, 10);
				if(popUpHeader!=null){
					appLog.info("New Source Firm PopUp Opened");
					String legalNameXpath = "//div[contains(@class,'Additional_fields_for_new_Source_Firm')]//label[text()='Legal Name']/../following-sibling::td//span[text()='"+sourceFirm+"']";
					WebElement legalNameEle = FindElement(driver, legalNameXpath, "Legal Name : "+sourceFirm, action.BOOLEAN, 10);
					if(legalNameEle!=null){
						appLog.info("Label with Legal Name with value : "+sourceFirm+"  verified");
					}else{
						BaseLib.sa.assertTrue(false, "Label with Legal Name with value : "+sourceFirm+" is not verified");
						appLog.error("Label with Legal Name with value : "+sourceFirm+" is not verified");		
					}
					
						String[] labelNames = sourceFirmLabels.split(",");
						String[] labelValue = sourceFirmValues.split(",");
					
						for(int i=0; i<labelNames.length; i++) {
							ThreadSleep(3000);
							WebElement ele = getSourceFirmPopUpTextBoxOrRichTextBoxWebElement(environment, mode, labelNames[i].trim(), 30);
							if(sendKeys(driver, ele, labelValue[i], labelNames[i]+" text box", action.SCROLLANDBOOLEAN)) {
								appLog.info("passed value "+labelValue[i]+" in "+labelNames[i]+" field");
							}else {
								appLog.error("Not able to pass value "+labelValue[i]+" in "+labelNames[i]+" field");
								BaseLib.sa.assertTrue(false, "Not able to pass value "+labelValue[i]+" in "+labelNames[i]+" field");
							}
						
						}
						
						if(click(driver, getNewSourceFirmAddButton(environment, mode, 10), "Add Button on New Source Firm", action.BOOLEAN)){
							appLog.info("Clicked on Add Button");
							return true;
						}
					
				}else{
					BaseLib.sa.assertTrue(false, "New Source Firm PopUp is not Opened");
					appLog.error("New Source Firm PopUp is not Opened");	
				}
				}else{
					return true;
				}
				
				
			}else{
				BaseLib.sa.assertTrue(false, "Not Able to entered value on Source Firm Text Box : " + sourceFirm);
				appLog.error("Not Able to entered value on Source Firm Text Box : " + sourceFirm);
			}
			
		}
		return false;
		
	}
	
	public WebElement getSourceFirmPopUpTextBoxOrRichTextBoxWebElement(String environment, String mode,
			String labelName, int timeOut) {
		WebElement ele = null;
		String xpath = "", inputXpath = "", textAreaXpath = "", finalXpath = "", finalLabelName = "";
		if (labelName.contains("_")) {
			finalLabelName = labelName.replace("_", " ");
		} else {
			finalLabelName = labelName;
		}

		xpath = "//label[text()='" + finalLabelName + "']";
		inputXpath = "/../following-sibling::td//input";
		textAreaXpath = "/../following-sibling::td//textarea";

		if (labelName.equalsIgnoreCase(InstitutionPageFieldLabelText.Description.toString())
				|| labelName.equalsIgnoreCase(InstitutionPageFieldLabelText.Referral_Source_Description.toString())
				|| labelName.equalsIgnoreCase(InstitutionPageFieldLabelText.Street.toString())) {
			finalXpath = xpath + textAreaXpath;
		} else {
			finalXpath = xpath + inputXpath;
		}
		ele = isDisplayed(driver,
				FindElement(driver, finalXpath, finalLabelName + " text box in " + mode, action.SCROLLANDBOOLEAN, 30),
				"Visibility", timeOut, finalLabelName + "text box in " + mode);
		return ele;

	}

	public boolean addSourceContactToDealCreation(String environment,String mode,Existing existingSourceContact,String sourceContact,String sourceContactLabels,String sourceContactValues){
		
		if(existingSourceContact.toString().equalsIgnoreCase(Existing.Yes.toString())){
			if (sendKeys(driver, getSourceContactSelectExistingBox(environment, mode, 10), sourceContact,
					"Source Contact : " + sourceContact, action.BOOLEAN)) {
				appLog.info("Entered Value on Source Contact Text Box : " + sourceContact);
				return true;
			}else{
				BaseLib.sa.assertTrue(false, "Not Able to entered value on Source Contact Text Box : " + sourceContact);
				appLog.error("Not Able to entered value on Source Contact Text Box : " + sourceContact);
			}
		
		}else{
		
			if (sendKeys(driver, getNewSourceContactTextBox(environment, mode, 10), sourceContact+"\t",
					"Source Contact : " + sourceContact, action.BOOLEAN)) {
				appLog.info("Entered Value on Source Contact Text Box : " + sourceContact);
				
				if(sourceContactLabels!=null){
				WebElement popUpHeader = FindElement(driver, "//h2[text()='New Source Contact']", "New Source Contact popUp", action.BOOLEAN, 10);
				if(popUpHeader!=null){
					appLog.info("New Source Firm PopUp Opened");
					String lastNameXpath = "//div[contains(@class,'New_Source_Contact ')]//label[text()='Last Name']/../following-sibling::td//span[text()='"+sourceContact+"']";
					WebElement lastNameEle = FindElement(driver, lastNameXpath, "Legal Name : "+sourceContact, action.BOOLEAN, 10);
					if(lastNameEle!=null){
						appLog.info("Label with Last Name with value : "+sourceContact+"  verified");
					}else{
						BaseLib.sa.assertTrue(false, "Label with Last Name with value : "+sourceContact+" is not verified");
						appLog.error("Label with Last Name with value : "+sourceContact+" is not verified");		
					}
					
						String[] labelNames = sourceContactLabels.split(",");
						String[] labelValue = sourceContactValues.split(",");
					
						for(int i=0; i<labelNames.length; i++) {
							ThreadSleep(3000);
							WebElement ele = getSourceContactPopUpTextBoxOrRichTextBoxWebElement(environment, mode, labelNames[i].trim(), 30);
							if(sendKeys(driver, ele, labelValue[i], labelNames[i]+" text box", action.SCROLLANDBOOLEAN)) {
								appLog.info("passed value "+labelValue[i]+" in "+labelNames[i]+" field");
							}else {
								appLog.error("Not able to pass value "+labelValue[i]+" in "+labelNames[i]+" field");
								BaseLib.sa.assertTrue(false, "Not able to pass value "+labelValue[i]+" in "+labelNames[i]+" field");
							}
						}
						
						if(click(driver, getNewSourceContactAddButton(environment, mode, 10), "Add Button on New Source Contact PopUp", action.BOOLEAN)){
							appLog.info("Clicked on Add Button");
							return true;
						}
					
				}else{
					BaseLib.sa.assertTrue(false, "New Source Contact PopUp is not Opened");
					appLog.error("New Source Contact PopUp is not Opened");	
				}
				}else{
					return true;
				}
				
				
			}else{
				BaseLib.sa.assertTrue(false, "Not Able to entered value on Source Firm Text Box : " + sourceContact);
				appLog.error("Not Able to entered value on Source Firm Text Box : " + sourceContact);
			}
			
		}
		return false;
		
	}
	
	public WebElement getSourceContactPopUpTextBoxOrRichTextBoxWebElement(String environment, String mode,
			String labelName, int timeOut) {
		WebElement ele = null;
		String xpath = "", inputXpath = "", textAreaXpath = "", finalXpath = "", finalLabelName = "";
		if (labelName.contains("_")) {
			finalLabelName = labelName.replace("_", " ");
		} else {
			finalLabelName = labelName;
		}

		xpath = "//*[text()='" + finalLabelName + "']";
		inputXpath = "/../../following-sibling::td//input";
		textAreaXpath = "/../../following-sibling::td//textarea";

		if(labelName.equalsIgnoreCase(ContactPageFieldLabelText.Description.toString()) || labelName.equalsIgnoreCase(ContactPageFieldLabelText.Mailing_Street.toString()) || labelName.equalsIgnoreCase(ContactPageFieldLabelText.Other_Street.toString())) {
			finalXpath=xpath+textAreaXpath;
		}else {
			finalXpath=xpath+inputXpath;
		}
		ele = isDisplayed(driver,
				FindElement(driver, finalXpath, finalLabelName + " text box in " + mode, action.SCROLLANDBOOLEAN, 30),
				"Visibility", timeOut, finalLabelName + "text box in " + mode);
		return ele;

	}
	
	public List<String> enterValuesToCustomAddedFieldForPipeLineCreation(String environment, String mode,String otherLabelFields,String otherLabelValues){
		List<String> result = new ArrayList<String>();
		String labelNames[]=null;
		String labelValue[]=null;
		if(otherLabelFields!=null && otherLabelValues !=null) {
			labelNames= otherLabelFields.split(",");
			labelValue=otherLabelValues.split(",");
			
			String xpath;
			String tag;
			String fullXpath;
			String finalLabelName;
			for(int i=0;i<labelNames.length;i++){
				ThreadSleep(1000);
				if (labelNames[i].contains("_")) {
					finalLabelName = labelNames[i].replace("_", " ");
				} else {
					finalLabelName = labelNames[i];
				}
				xpath = "//label[text()='"+finalLabelName+"']/../following-sibling::td/label/";
				tag=getTagElementForGivenXpath(xpath+"*[1]");
				if(tag!=null){
					appLog.info("Tag Element for : " + finalLabelName+ " >>>> "+tag);
					fullXpath = xpath+tag;
					if(enteringValuesOnTheBasisOfTag(fullXpath, finalLabelName, labelValue[i], tag)){
						appLog.info(" value added to "+ finalLabelName +"  : " + labelValue[i]);	
					}else{
					result.add(labelNames[i]);	
					}
				}else{
					BaseLib.sa.assertTrue(false, "Tag Element is null for : " + finalLabelName);
					appLog.error("Tag Element is null for : " + finalLabelName);	
				}
			}
			
		}
		
		
		return result;
		
	}
	
	public String getTagElementForGivenXpath(String xpath){
		WebElement ele=FindElement(driver, xpath, "label xpath", action.SCROLLANDBOOLEAN,30);
		String tagName = ele.getTagName();
		return tagName;
	}
	
	public boolean enteringValuesOnTheBasisOfTag(String xpath,String label,String value,String tag){
		WebElement ele ;
		ele = FindElement(driver, xpath, xpath, action.SCROLLANDBOOLEAN, 10);
		ThreadSleep(1000);
		if(tag.equalsIgnoreCase(HTMLTAG.select.toString())){
		
			if (selectVisibleTextFromDropDown(driver, ele,label+" Drop Down List", value)) {
				appLog.info("Selected value from "+ label +" Drop down List : " + value);
				return true;
			}else{
				BaseLib.sa.assertTrue(false, "Not Able to Select value from "+ label +" Drop down List : " + value);
				appLog.error("Not Able to Select value from "+ label +" Drop down List : " + value);	
			}
			
		}else if(tag.equalsIgnoreCase(HTMLTAG.input.toString())) {
		
			if (sendKeys(driver, ele, value,label+" : " + value, action.BOOLEAN)) {
				appLog.info("Entered Value on "+label+" Text Box : " + value);
				return true;
			}else{
				BaseLib.sa.assertTrue(false, "Not Able to entered value on "+label+" Text Box : " + value);
				appLog.error("Not Able to entered value on "+label+" Text Box : " + value);
			}
			
		}else{
			appLog.info("Tag Does not Exist on HTMLTag Enum : "+tag);	
		}
			
		return false;
		
	}
	
	public boolean clickOnCreateDealButtonAndVerifyingLandingPage(String environment,String mode,String companyName){
		boolean flag = false;
		String monthAndYear = getSystemDate("MMM") + " " + getSystemDate("yyyy");
		String expectedPipeLineName = companyName + " " + "-" + " " + monthAndYear;

		if (click(driver, getCreateDealBtn(environment, mode, 10), "Create Deal Button", action.BOOLEAN)) {
			appLog.info("Clicked on Create Deal Button");	
			
			if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
				switchToDefaultContent(driver);
			}
			ExcelUtils.writeData(BaseLib.smokeFilePath, expectedPipeLineName, "PipeLine", excelLabel.Company_Name, companyName,
					excelLabel.Pipeline_Name);
			ThreadSleep(3000);
			flag = true;
			if (getPipelineNameInViewMode(environment, mode, 60) != null) {
				String pipeLineNameViewMode = getText(driver, getPipelineNameInViewMode(environment, mode, 60),
						"PipeLine Name", action.BOOLEAN);
				if (expectedPipeLineName.equalsIgnoreCase(pipeLineNameViewMode)) {
					appLog.info("PipeLine created successfully.:" + pipeLineNameViewMode);
			
				} else {
					BaseLib.sa.assertTrue(false, "PipeLine Created But not Not Verified - Actual : " + pipeLineNameViewMode+"  Expected  : "+expectedPipeLineName);
					appLog.error("PipeLine Created But not Not Verified - Actual : " + pipeLineNameViewMode+"  Expected  : "+expectedPipeLineName);
				}
			} else {
				BaseLib.sa.assertTrue(false, "Not able to find PipeLine Name in View Mode");
				appLog.error("Not able to find PipeLine Name in View Mode");
			}
			
			
		} else {
			BaseLib.sa.assertTrue(false, "Not Able to Click on Create Deal Button");
			appLog.error("Not Able to Click on Create Deal Button");
		}
		
		
		return flag;
		
	}

	public List<String> verifyLabelInViewModeforNavatarSetUpSideMenuTab(String environment,String mode,NavatarSetupSideMenuTab navatarSetupSideMenuTab,NavatarSetupSideMenuTabLayoutSection navatarSetupSideMenuTabLayoutSection,String[] labels){
		String labelXpath="";
		List<String> result = new ArrayList<String>();
		String section="";
		
		if (NavatarSetupSideMenuTab.DealCreation.toString().equalsIgnoreCase(navatarSetupSideMenuTab.toString())) {
		
			if(NavatarSetupSideMenuTabLayoutSection.DealCreation_DealInformation.toString().equalsIgnoreCase(navatarSetupSideMenuTabLayoutSection.toString())){
				section="Deal Information";
			}else if(NavatarSetupSideMenuTabLayoutSection.DealCreation_NewSourceFirm.toString().equalsIgnoreCase(navatarSetupSideMenuTabLayoutSection.toString())){
					section="Fields for New Source Firm";
			}else{
					section="Fields for New Source Contact";
			}
			
		} else if (NavatarSetupSideMenuTab.CommitmentCreation.toString().equalsIgnoreCase(navatarSetupSideMenuTab.toString())) {
		
			if(NavatarSetupSideMenuTabLayoutSection.CommitmentCreation_FundRaisingInformation.toString().equalsIgnoreCase(navatarSetupSideMenuTabLayoutSection.toString())){
				section="Fundraising Information";
			}else if(NavatarSetupSideMenuTabLayoutSection.CommitmentCreation_AdditionalInformation.toString().equalsIgnoreCase(navatarSetupSideMenuTabLayoutSection.toString())){
					section="Additional Information";
			}else if(NavatarSetupSideMenuTabLayoutSection.CommitmentCreation_FieldsForNewLimitedPartner.toString().equalsIgnoreCase(navatarSetupSideMenuTabLayoutSection.toString())){
					section="Fields for New Limited Partner";
			}else{
					section="Fields for New Partnership";
			}
			
		}else if (NavatarSetupSideMenuTab.IndividualInvestorCreation.toString().equalsIgnoreCase(navatarSetupSideMenuTab.toString())) {
		
			if(NavatarSetupSideMenuTabLayoutSection.Contact_Information.toString().equalsIgnoreCase(navatarSetupSideMenuTabLayoutSection.toString())){
				section="Contact Information";
			}else if(NavatarSetupSideMenuTabLayoutSection.Address_Information.toString().equalsIgnoreCase(navatarSetupSideMenuTabLayoutSection.toString())){
					section="Address Information";
			}else if(NavatarSetupSideMenuTabLayoutSection.Additional_Information.toString().equalsIgnoreCase(navatarSetupSideMenuTabLayoutSection.toString())){
					section="Additional Information";
			}
			
		}else {
			result.add("Code for "+navatarSetupSideMenuTab.toString()+" not added");
			log(LogStatus.FATAL, "Code for "+navatarSetupSideMenuTab.toString()+" not added", YesNo.No);
		}
		
		labelXpath = "//div[@class='ContentBox']//h2//span[text()='"+section+"']/ancestor::h2/..//label";
		for (String label : labels) {
			String newLabel=label.replace("_", " ");
			String newLabelXpath=labelXpath+"[text()='"+newLabel+"']";
			WebElement ele = FindElement(driver, newLabelXpath, newLabel, action.SCROLLANDBOOLEAN, 10);
			if(ele!=null){
				appLog.info(navatarSetupSideMenuTab.toString()+ " : Label found : "+newLabel);	
			}else{
				appLog.error(navatarSetupSideMenuTab.toString()+ " : Label Not found : "+newLabel);	
				result.add(label);
			}	
		}
		
		return result;
		
	}


	public SoftAssert createPipeLine(String environment, String mode, String companyName, String stage, String source,Existing existingsSourceFirm,String sourceFirm,String sourceFirmLabels,String sourceFirmValues,Existing existingSourceContact,String sourceContact,String sourceContactLabels,String sourceContactValues,String otherLabelFields,String otherLabelValues){
		
		SoftAssert msa = new SoftAssert();
		String monthAndYear = getSystemDate("MMM") + " " + getSystemDate("yyyy");
		String expectedPipeLineName = companyName + " " + "-" + " " + monthAndYear;
		if (createNewDealPipeLine(environment, mode, companyName, stage, source)) {
			log(LogStatus.INFO, "createNewDealPipeLine method is executed Successfully", YesNo.No);	
			
			if (addSourceFirmToDealCreation(environment, mode,  existingsSourceFirm, sourceFirm, sourceFirmLabels, sourceFirmValues)) {
				log(LogStatus.INFO, "addSourceFirmToDealCreation method is executed Successfully", YesNo.No);	
			} else {
				msa.assertTrue(false, "addSourceFirmToDealCreation method is not executed Successfully");
				log(LogStatus.FAIL, "addSourceFirmToDealCreation method is not executed Successfully", YesNo.Yes);
			}
			
			if (addSourceContactToDealCreation(environment, mode, existingSourceContact, sourceContact, sourceContactLabels, sourceContactValues)) {
				log(LogStatus.INFO, "addSourceContactToDealCreation method is executed Successfully", YesNo.No);	
			} else {
				msa.assertTrue(false, "addSourceContactToDealCreation method is not executed Successfully");
				log(LogStatus.FAIL, "addSourceContactToDealCreation method is not executed Successfully", YesNo.Yes);
			}
			
			List<String> failLabel =enterValuesToCustomAddedFieldForPipeLineCreation(environment, mode, otherLabelFields, otherLabelValues);
			
			if (failLabel.isEmpty()) {
				log(LogStatus.INFO, "enterValuesToCustomAddedFieldForPipeLineCreation method is executed Successfully", YesNo.No);
			} else {
				msa.assertTrue(false, "enterValuesToCustomAddedFieldForPipeLineCreation method is not executed Successfully");
				log(LogStatus.FAIL, "enterValuesToCustomAddedFieldForPipeLineCreation method is not executed Successfully", YesNo.Yes);
			}
			
			if (clickOnCreateDealButtonAndVerifyingLandingPage(environment, mode, companyName)) {
				log(LogStatus.INFO, "clickOnCreateDealButtonAndVerifyingLandingPage method is executed Successfully", YesNo.No);
			} else {
				msa.assertTrue(false, "clickOnCreateDealButtonAndVerifyingLandingPage method is not executed Successfully");
				log(LogStatus.FAIL, "clickOnCreateDealButtonAndVerifyingLandingPage method is not executed Successfully", YesNo.Yes);
			}
			
			
		} else {
			msa.assertTrue(false, "createNewDealPipeLine method is not executed Successfully");
			log(LogStatus.FAIL, "createNewDealPipeLine method is not executed Successfully", YesNo.Yes);
		}
		
		ExcelUtils.writeData(BaseLib.smokeFilePath, expectedPipeLineName, "PipeLine", excelLabel.Company_Name, companyName,
				excelLabel.Pipeline_Name);
		
		return msa;
		
	}
}
