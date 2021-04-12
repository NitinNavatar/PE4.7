package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.FindElement;
import static com.navatar.generic.CommonLib.isDisplayed;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.navatar.generic.CommonLib;
import com.navatar.generic.EnumConstants.InstitutionPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;

public class PipelinesPage extends BasePageBusinessLayer {

	public PipelinesPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath="//span[text()='Pipeline Stage Logs']/ancestor::article//span[text()='View All']")
	private WebElement pipeLineStageLogViewAll_Lighting;
	
	
	/**
	 * @param environment
	 * @param timeOut
	 * @return
	 */
	public WebElement getPipeLineStageLogViewAll_Lighting(String environment,int timeOut) {
		CommonLib.scrollThroughOutWindow(driver);
		return isDisplayed(driver, pipeLineStageLogViewAll_Lighting, "Visibility", timeOut, "PipeLine Stage Log View All");
	
	}
	
	@FindBy(xpath="//label[text()='Stage']/../following-sibling::td//span/select")
	private WebElement pipeLineStageLabel_Classic;
	
	
	/**
	 * @param environment
	 * @param timeOut
	 * @return
	 */
	public WebElement getPipeLineStageLabel(String environment,String mode,int timeOut) {
		return isDisplayed(driver, pipeLineStageLabel_Classic, "Visibility", timeOut, "PipeLine Stage Label classic");
	
	}
	
	
	public WebElement getpipeLInePageTextBoxAllWebElement(String environment,String mode, String labelName, int timeOut) {
		WebElement ele=null;
		String xpath ="",inputXpath="",finalXpath="",finalLabelName="",xpath1="",xpath2="";
		if(labelName.contains("_")) {
			finalLabelName=labelName.replace("_", " ");
		}else {
			finalLabelName=labelName;
		}
		if(mode.equalsIgnoreCase(Mode.Classic.toString())) {
			
		}else {
			xpath="//span[text()='"+finalLabelName+"']";
			inputXpath="/..//following-sibling::input";
			xpath1="/../following-sibling::div//input";
			xpath2="/..//following-sibling::div//a";
			if(labelName.toString().equalsIgnoreCase(InstitutionPageFieldLabelText.Parent_Entity.toString())) {
				inputXpath="/..//following-sibling::div//input[@title='Search Institutions']";
			}
			
		}
		if(labelName.equalsIgnoreCase(excelLabel.Company_Name.toString())) {
			finalXpath=xpath+xpath1;
		}else if (labelName.equalsIgnoreCase(excelLabel.Stage.toString())) {
			finalXpath=finalXpath+xpath2;
		}else {
			finalXpath=xpath+inputXpath;
		}
		ele=isDisplayed(driver, FindElement(driver, finalXpath, finalLabelName+" text box in "+mode, action.SCROLLANDBOOLEAN,30), "Visibility", timeOut, finalLabelName+"text box in "+mode);
		return ele;
		
	}
	
	
	@FindBy(xpath="//input[@name='save']")
	private WebElement saveButtonClassic;
	
	@FindBy(xpath="//button[@title='Save']/span[text()='Save']")
	private WebElement saveButtonLighting;

	/**
	 * @return the saveButton
	 */
	public WebElement getSaveButton(int timeOut) {
			return isDisplayed(driver, saveButtonLighting, "Visibility", timeOut, "Save Button Lighting");
		
	}
	

	
	
}
