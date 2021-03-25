package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;

import static com.navatar.generic.CommonLib.*;

import java.util.List;

public class ContactsPage extends BasePageBusinessLayer {

	public ContactsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	

	@FindBy(xpath="//input[@name='name_firstcon2']")
	private WebElement contactFirstName_Classic;
	
	@FindBy(xpath="//*[text()='First Name']/following-sibling::*//input")
	private WebElement contactFirstName_Lighting;

	/**
	 * @return the contactFirstName
	 */
	public WebElement getContactFirstName(String projectName,int timeOut) {
		
			return isDisplayed(driver, contactFirstName_Lighting, "Visibility", timeOut, "Contact First Name Lighting");
	
	
	}
	
	@FindBy(xpath="//input[@name='name_lastcon2']")
	private WebElement contactLastName_Classic;
	
	@FindBy(xpath="//*[text()='Last Name']/following-sibling::*//input")
	private WebElement contactLastName_Lighting;

	/**
	 * @return the contactLastName
	 */
	public WebElement getContactLastName(String projectName,int timeOut) {
		
			return isDisplayed(driver, contactLastName_Lighting, "Visibility", timeOut, "Contact Last Name Lighting");
	
		
	}
	
	@FindBy(xpath="//*[text()='Title']/following-sibling::*//input")
	private WebElement contactTitle;

	/**
	 * @return the contactLastName
	 */
	public WebElement getcontactTitle(String projectName,int timeOut) {
		
			return isDisplayed(driver, contactTitle, "Visibility", timeOut, "contactTitle");
	
		
	}
	@FindBy(xpath="//div[@class='requiredInput']//span//input")
	private WebElement legalName_Classic;
	
	@FindBy(xpath="//span[text()='Account Name']/../following-sibling::div//input[@title='Search Accounts']")
	private WebElement accountName;
	
	@FindBy(xpath="//span[text()='Legal Name']/../following-sibling::div//input[@title='Search Institutions']")
	private WebElement legalName;
	
	@FindBy(xpath="//span[text()='Firm']/../following-sibling::div//input[@title='Search Entities']")
	private WebElement firmName;

	/**
	 * @return the legalName
	 */
	public WebElement getLegalName(String projectName,int timeOut) {
		
		if (ProjectName.MNA.toString().equals(projectName)) {
			return isDisplayed(driver, accountName, "Visibility", timeOut, "Account Name");
		} else if (ProjectName.PE.toString().equals(projectName)) {
			return isDisplayed(driver, legalName, "Visibility", timeOut, "Legal Name");
		}else{
			return isDisplayed(driver, firmName, "Visibility", timeOut, "Firm Name");
		}
			
		
	
	} 
	
	
	@FindBy(xpath="//table[@class='detailList']//input[@name='con15']")
	private WebElement emailId_Clasic;
	
	@FindBy(xpath="//span[text()='Email']/../following-sibling::input")
	private WebElement emailId_Lighting;

	/**
	 * @return the emailId
	 */
	public WebElement getEmailId(String projectName,int timeOut) {
		
			return isDisplayed(driver, emailId_Lighting, "Visibility", timeOut, "Email Id Lighting");
	
		
	}
	

	
	@FindBy(xpath="//span[@class='custom-truncate uiOutputText']")
	private WebElement contactFullNameInViewMode_Lighting;

	/**
	 * @return the contactFullNameLabel
	 */
	public WebElement getContactFullNameInViewMode(String projectName,int timeOut) {
		
			return isDisplayed(driver, contactFullNameInViewMode_Lighting, "Visibility", timeOut, "Contact Full Name In View Mode Lighting");
		
		
	}
	
	public WebElement getContactPageTextBoxOrRichTextBoxWebElement(String projectName, String labelName, int timeOut) {
		WebElement ele=null;
		String xpath ="",inputXpath="", textAreaXpath="",finalXpath="",finalLabelName="";
		
		if(labelName.equalsIgnoreCase(excelLabel.Mailing_State.toString())) {
			labelName=ContactPageFieldLabelText.Mailing_State.toString();
		}else if (labelName.equalsIgnoreCase(excelLabel.Mailing_Zip.toString())) {
			labelName=ContactPageFieldLabelText.Mailing_Zip.toString();
		}else if (labelName.equalsIgnoreCase(excelLabel.Other_State.toString())) {
			labelName=ContactPageFieldLabelText.Other_State.toString();
		}else if (labelName.equalsIgnoreCase(excelLabel.Other_Zip.toString())) {
			labelName=ContactPageFieldLabelText.Other_Zip.toString();
		}
		
		if(labelName.contains("_")) {
			finalLabelName=labelName.replace("_", " ");
		}else {
			finalLabelName=labelName;
		}
		
			//span[text()='Description']/..//following-sibling::textarea
			xpath="//*[text()='"+finalLabelName+"']";
			inputXpath="/..//following-sibling::input";
			textAreaXpath="/..//following-sibling::textarea";
		
		
		if(labelName.equalsIgnoreCase(ContactPageFieldLabelText.Description.toString()) || labelName.equalsIgnoreCase(ContactPageFieldLabelText.Mailing_Street.toString()) || 
			labelName.equalsIgnoreCase(ContactPageFieldLabelText.Other_Street.toString())) {
			finalXpath=xpath+textAreaXpath;
		}else if (labelName.equalsIgnoreCase(excelLabel.Phone.toString())) {
			xpath="//*[starts-with(text(),'"+finalLabelName+"')]/..//following-sibling::input";
			finalXpath=xpath+inputXpath;
		}else if (labelName.equalsIgnoreCase(excelLabel.Region.toString())|| (labelName.equalsIgnoreCase(excelLabel.Industry.toString())|| labelName.equalsIgnoreCase(PageLabel.Profile_Image.toString()))) {
				xpath="//*[text()='"+finalLabelName+"']/..//following-sibling::*//input";
				finalXpath=xpath;
			}
		else {
			finalXpath=xpath+inputXpath;
		}
		ele=isDisplayed(driver, FindElement(driver, finalXpath, finalLabelName+" text box in "+projectName, action.SCROLLANDBOOLEAN,30), "Visibility", timeOut, finalLabelName+"text box in "+projectName);
		return ele;
		
	}

	@FindBy(xpath="//input[@title='Contact Transfer']")
	private WebElement contactTransferBtn_Classic;
	
	@FindBy(xpath="//div[@role='menu']//li/a[@title='Contact Transfer']")
	private WebElement contactTransferLink_Lighting;
	
	@FindBy(xpath="//div[@title='View Contact Hierarchy']/ancestor::li/following-sibling::li//a")
	private WebElement contactViewHierrachyIcon_Lighting;

	/**
	 * @return the contactFirstName
	 */
	public WebElement getContactTransfer(String projectName,int timeOut) {
		
			click(driver, contactViewHierrachyIcon_Lighting, "Contact View Hierrachy Icon", action.SCROLLANDBOOLEAN);
			ThreadSleep(2000);
			return contactTransferLink_Lighting;
		
	
	}
	
	@FindBy(xpath="//input[@title='Search Office Locations']")
	private WebElement officeLocationTextBox_Lighting;

	/**
	 * @return the contactFirstName
	 */
	public WebElement getOfficeLocationTextBox_Lighting(String environment,String mode,int timeOut) {
	
			return isDisplayed(driver, officeLocationTextBox_Lighting, "Visibility", timeOut, "Office Location Text Box");
		
	}
	
	
	@FindBy(xpath="//div/button/span[text()='Save']")
	private WebElement saveButtonTask_Lighting;
	
	@FindBy(xpath="//input[@name='save']")
	private WebElement saveButtonTask_Classic;

	/**
	 * @return the getSaveButtonForTask
	 */
	public WebElement getSaveButtonForTask(String environment,String mode,int timeOut) {
		WebElement ele=null;
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
		//	return saveButtonTask_Classic;
			return isDisplayed(driver, saveButtonTask_Classic, "Visibility", timeOut, "Save Button : "+mode);
		}else{
			List<WebElement> eleList = FindElements(driver, "//div/button/span[text()='Save']", "Save Btn");
			if (eleList.size()>0) {
				for (int i = 0; i < eleList.size(); i++) {
				 ele =	isDisplayed(driver, eleList.get(i), "Visibility", timeOut, "Save Button : "+mode);
				if (ele!=null) {
					return ele;
				}
				}
			}
			return isDisplayed(driver, ele, "Visibility", timeOut, "Save Button : "+mode);
		//	return saveButtonTask_Lighting;
		}
		
	}
	
	@FindBy(xpath="//a[@title='Transfer']")
	private WebElement transferButton;


	/**
	 * @return the getTransferButton
	 */
	public WebElement getTransferButton(String projectName,int timeOut) {
		return isDisplayed(driver, transferButton, "Visibility", timeOut, "Transfer Button");
		
	}
	
	@FindBy(xpath="//input[@value='Retain Address']")
	private WebElement retainAddressButton;


	/**
	 * @return the getRetainAddressButton
	 */
	public WebElement getRetainAddressButton(String projectName,int timeOut) {
		return isDisplayed(driver, retainAddressButton, "Visibility", timeOut, "Retain Address Button");
		
	}
	
	@FindBy(xpath="//input[@value='Clear Address']")
	private WebElement clearAddressButton;


	/**
	 * @return the clearAddressButton
	 */
	public WebElement getClearAddressButton(String projectName,int timeOut) {
		return isDisplayed(driver, clearAddressButton, "Visibility", timeOut, "Clear Address Button");
		
	}
	
	@FindBy(xpath="//div[@class='PopupContentStart']/p")
	private WebElement contactTransferConfirmationMsg;


	/**
	 * @return the clearAddressButton
	 */
	public WebElement getContactTransferConfirmationMsg(String projectName,int timeOut) {
		return isDisplayed(driver, contactTransferConfirmationMsg, "Visibility", timeOut, "Contact Transfer Confirmation Msg");
		
	}
	
	public WebElement sendAnEmailButtonOnActivityHistory(String projectName, int timeOut) {
		String xpath="//h3[text()='Activity History']/../following-sibling::td//input[@value='Send an Email']";
		return isDisplayed(driver, FindElement(driver, xpath,"send an email", action.SCROLLANDBOOLEAN, timeOut/2), "visibility", timeOut/2, "send an email");
	}
	
	@FindBy(xpath = "//label[text()='Subject']/../following-sibling::td//input")
	private WebElement subjectTextbox;
	
	
	public WebElement getsubjectTextbox(String projectName,int timeOut) {
		return isDisplayed(driver, subjectTextbox, "Visibility", timeOut, "subject Text box");
		
	}
	
	@FindBy(xpath = "//label[text()='Body']/../following-sibling::td//textarea")
	private WebElement bodyTextbox;

	public WebElement getbodyTextbox(String projectName,int timeOut) {
		return isDisplayed(driver, bodyTextbox, "Visibility", timeOut, "body Text box");
		
	}
	
	@FindBy(xpath = "//input[@value='Attach File']")
	private WebElement attachFileButton;
	
	public WebElement getattachFileButton(String mode,String projectName,int timeOut) {
		WebElement ele=null;
		if (mode.equalsIgnoreCase(Mode.Classic.toString()))
			ele=attachFileButton;
		else
			ele=FindElement(driver, "//span[text()='Attach file']/parent::button", "attach", action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "attach File Button");
		
	}
	
	@FindBy(xpath = "//input[@id='file']")
	private WebElement uploadFileBrowseButton;
	
	public WebElement getuploadFileBrowseButton(String projectName,int timeOut) {
		return isDisplayed(driver, uploadFileBrowseButton, "Visibility", timeOut, "upload File Browse Button");
		
	}
	
	@FindBy(xpath = "//input[@title='Attach To Email (New Window)']")
	private WebElement attachToEmail;
	
	public WebElement getattachToEmail(String projectName,int timeOut) {
		return isDisplayed(driver, attachToEmail, "Visibility", timeOut, "attach To Email");
		
	}
	
	@FindBy(xpath = "//input[@title='Send']")
	private WebElement sendButton;
	
	public WebElement getsendButton(String projectName,int timeOut) {
		return isDisplayed(driver, sendButton, "Visibility", timeOut, "send Button");
		
	}
	
	@FindBy(xpath = "//span[contains(text(),'Upload')]/ancestor::button")
	private WebElement uploadFileButton;
	
	public WebElement getuploadFileButton(String projectName,int timeOut) {
		return isDisplayed(driver, uploadFileButton, "Visibility", timeOut, "uploadFileButton");
		
	}
	
	@FindBy(xpath = "//input[contains(@placeholder,'Enter Subject')]")
	private WebElement sendListEmailSubject;
	
	public WebElement getsendListEmailSubject(String projectName,int timeOut) {
		return isDisplayed(driver, sendListEmailSubject, "Visibility", timeOut, "sendListEmailSubject");
		
	}
	@FindBy(xpath = "//iframe[@title='CK Editor Container']")
	private WebElement containerFrameEmail;
	
	public WebElement getcontainerFrameEmail(String projectName,int timeOut) {
		return isDisplayed(driver, containerFrameEmail, "Visibility", timeOut, "container Frame");
		
	}
	@FindBy(xpath = "//iframe[@title='Email Body']")
	private WebElement emailBodyFrame;
	
	public WebElement getemailBodyFrame(String projectName,int timeOut) {
		return isDisplayed(driver, emailBodyFrame, "Visibility", timeOut, "email Body Frame");
		
	}
	
	@FindBy(xpath = "//body[@contenteditable='true']")
	private WebElement emailBody;
	

	public WebElement getemailBody(String projectName,int timeOut) {
		return isDisplayed(driver, emailBody, "Visibility", timeOut, "email Body");
		
	}
	@FindBy(xpath = "//button[contains(text(),'Send')]")
	private WebElement sendButtonListEmail;
	
	public WebElement getsendButtonListEmail(String projectName,int timeOut) {
		return isDisplayed(driver, sendButtonListEmail, "Visibility", timeOut, "send Button on List Email");
		
	}
	
	@FindBy(xpath = "//span[text()='Last Touch Point']/../following-sibling::div//span/*/*/*")
	private WebElement lastTouchPointValue;
	public WebElement getlastTouchPointValue(String projectName,int timeOut) {
		ThreadSleep(10000);
		return lastTouchPointValue;
	//	return isDisplayed(driver, lastTouchPointValue, "Visibility", timeOut, "last Touch Point Value");
		
	}
	
	@FindBy(xpath = "//span[text()='Is_Touchpoint']/../following-sibling::div//img")
	private WebElement iSTouchPoinCheckBox1;
	
	@FindBy(xpath = "//span[text()='Is_Touchpoint']/../following-sibling::input")
	private WebElement iSTouchPoinCheckBox2;
	
	public WebElement getIsTouchPoinCheckBox(String projectName,PageName pageName,int timeOut) {
		if (pageName==PageName.TaskPage) {
			return isDisplayed(driver, iSTouchPoinCheckBox1, "Visibility", timeOut, "Is Touch Point CheckBox");
		} else {
			return isDisplayed(driver, iSTouchPoinCheckBox2, "Visibility", timeOut, "Is Touch Point CheckBox");
		}
		
		
	}
	@FindBy(xpath = "//a[text()='Update Photo']")
	private WebElement updatePhotoLink;
	
	public WebElement getupdatePhotoLink(String projectName,ContactPagePhotoActions cpp,int timeOut) {
		WebElement ele=null;
		String action1 = cpp.toString().replace("_"," ");
		String xpath = "//a[@title='"+action1+"']";
		ele=FindElement(driver, xpath, "photo action link", action.SCROLLANDBOOLEAN, timeOut);
		//return ele;
		return ele;
		
	}
	
	
	@FindBy(xpath = "//input[@name='fileUploader']")
	private WebElement uploadPhotoButton;
	
	public WebElement getuploadPhotoButton(String projectName,int timeOut) {
		return isDisplayed(driver, uploadPhotoButton, "Visibility", timeOut, "upload photo button");
		
	}
	
	@FindBy(xpath = "//button[text()='Delete Photo']")
	private WebElement deletePhotoButton;
	
	public WebElement getdeletePhotoButton(String projectName,int timeOut) {
		return isDisplayed(driver, deletePhotoButton, "Visibility", timeOut, "delete photo button");
		
	}
	/*@FindBy(xpath = "//input[@name='fileUploader']")
	private WebElement deletePhotoButton;
	
	public WebElement getdeletePhotoButton(String projectName,int timeOut) {
		return isDisplayed(driver, deletePhotoButton, "Visibility", timeOut, "send Button on List Email");
		
	}*/
}
