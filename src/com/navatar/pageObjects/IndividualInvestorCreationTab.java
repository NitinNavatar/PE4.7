package com.navatar.pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.navatar.generic.EnumConstants.NavatarSetupSideMenuTab;
import com.navatar.generic.EnumConstants.TopOrBottom;
import com.navatar.generic.EnumConstants.action;

import static com.navatar.generic.EnumConstants.*;
import static com.navatar.generic.CommonLib.*;
public class IndividualInvestorCreationTab extends NavatarSetupPageBusinessLayer {

	public IndividualInvestorCreationTab(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	
	public List<WebElement> getIndiviualInvestorFieldLabelInEditViewMode(String environment, String mode,IndiviualInvestorSectionsName indiviualInvestorSectionsName){
		List<WebElement> webElements = new ArrayList<WebElement>();
		if(indiviualInvestorSectionsName.toString().equalsIgnoreCase(IndiviualInvestorSectionsName.Additional_Information.toString())) {
			webElements = FindElements(driver, "//h2[text()='"+indiviualInvestorSectionsName.toString().replace("_", " ")+"']/../..//td[@class='td1']/label", "additional information section label list");
		}else {
			webElements = FindElements(driver, "//h2[text()='"+indiviualInvestorSectionsName.toString().replace("_", " ")+"']/../..//label", indiviualInvestorSectionsName.toString()+" section label list");
		}
		return webElements;
	}
	
	@FindBy(xpath="//input[@value='Create Individual Investor']")
	private WebElement createIndiviualInvestorBtn;

	/**
	 * @return the createIndiviualInvestorBtn
	 */
	public WebElement getCreateIndiviualInvestorBtn(int timeOut) {
		return isDisplayed(driver, createIndiviualInvestorBtn, "Visibility", timeOut, "create indiviual investor button");
	}
	
	
	public WebElement getCreateIndiviualInvestorBtn(String environment, String mode, TopOrBottom topOrBottom,int timeOut) {
		String xpath ;
		WebElement ele;
		if (TopOrBottom.TOP.toString().equalsIgnoreCase(topOrBottom.toString())) {
			xpath = "(//input[@value='Create Individual Investor'])[1]";
		} else {
			xpath = "(//input[@value='Create Individual Investor'])[2]";
		}
		ele = FindElement(driver, xpath, "create indiviual investor button : "+topOrBottom, action.SCROLLANDBOOLEAN, timeOut);
				
		return isDisplayed(driver, ele, "Visibility", timeOut, " create indiviual investor button : "+topOrBottom);
	}
	
	
	
	
	
	@FindBy(xpath="//input[@value='Cancel']")
	private WebElement indiviualInvestorCancelBtn;

	/**
	 * @return the indiviualInvestorCancelBtn
	 */
	public WebElement getIndiviualInvestorCancelBtn(int timeOut) {
		return isDisplayed(driver, indiviualInvestorCancelBtn, "Visibility", timeOut, "cancel button indiviual investor");
	}
	
	@FindBy(xpath="//p[text()='Individual Investor']")
	private WebElement indiviualInvestorHeaderText;

	/**
	 * @return the indiviualInvestorHeaderText
	 */
	public WebElement getIndiviualInvestorHeaderText(int timeOut) {
		return isDisplayed(driver, indiviualInvestorHeaderText, "Visibility", timeOut, "indiviual investor header text");
	}
	
	@FindBy(xpath="//iframe[@title='accessibility title']")
	private WebElement createCommitmentFrame_Lighting;

	/**
	 * @return the createCommitmentFrame_Lighting
	 */
	public WebElement getCreateCommitmentFrame_Lighting(int timeOut) {
		return isDisplayed(driver, createCommitmentFrame_Lighting, "Visibility", timeOut, "create commitment frame in lighting");
	}
	
	@FindBy(xpath="//a[text()='Copy Mailing Address to Other Address']")
	private WebElement coptyMailingAddressToOther;

	/**
	 * @return the coptyMailingAddressToOther
	 */
	public WebElement getCoptyMailingAddressToOther(int timeOut) {
		return isDisplayed(driver, coptyMailingAddressToOther, "Visibility", timeOut, "Copy address to other");
	}
	
	public WebElement getCancelButtonforCreateIndiviualInvestorPage(String environment, String mode, TopOrBottom topOrBottom,int timeOut) {
		String xpath ;
		WebElement ele;
		if (TopOrBottom.TOP.toString().equalsIgnoreCase(topOrBottom.toString())) {
			xpath = "(//div[@id='MainContentSection']//input[@value='Cancel'])[1]";
		} else {
			xpath = "(//div[@id='MainContentSection']//input[@value='Cancel'])[2]";
		}
		ele = FindElement(driver, xpath, "cancel Button : "+topOrBottom, action.SCROLLANDBOOLEAN, timeOut);
				
		return isDisplayed(driver, ele, "Visibility", timeOut, "Cancel Button for create indiviual investor : "+topOrBottom);
	}
	
	
	public List<WebElement> getAddIconOfAdditionalInformationPlusIcon(){
		return FindElements(driver, "//input[contains(@name,'ext') and contains(@name,'[]')]/../following-sibling::a[@title='Add']", "plsu icon in additional information");
	}
	
	public List<WebElement> getRemoveIconOfAdditionalInformationPlusIcon(){
		return FindElements(driver, "//input[contains(@name,'ext') and contains(@name,'[]')]/../following-sibling::a[@title='Delete']", "remove icon in additional information");
	}
	

}
