package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.object;
import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.CommonLib.*;

import java.util.List;
import java.util.Scanner;

public class SetupPage extends BasePageBusinessLayer {

	public SetupPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		// TODO Auto-generated constructor stub
	}

	public WebElement getQucikSearchInSetupPage(int timeOut ) {
		WebElement ele = null;
		String xpath="";
		xpath="//input[contains(@placeholder,'Quick Find')]";
		ele=FindElement(driver, xpath, "search text box in ", action.SCROLLANDBOOLEAN,30);
		return isDisplayed(driver,ele,"visibility",30,"quick search text box in ");
	}

	@FindBy(xpath="//ul[contains(@class,'tabBarItems slds-grid')]//span[contains(@class,'title slds-truncate')][contains(text(),'Object Manager')]")
	private WebElement objectManager_Lighting;

	/**
	 * @return the objectManage_Lighting
	 */
	public WebElement getObjectManager_Lighting(int timeOut) {
		return isDisplayed(driver, objectManager_Lighting, "Visibility", timeOut, "object manage");
	}

	@FindBy(xpath="//input[@id='globalQuickfind']")
	private WebElement quickSearchInObjectManager_Lighting;

	/**
	 * @return the quickSearchInObjectManager_Lighting
	 */
	public WebElement getQuickSearchInObjectManager_Lighting(int timeOut) {
		return isDisplayed(driver, quickSearchInObjectManager_Lighting, "Visibility", timeOut, "quick search in object manager in lighting");
	}


	@FindBy(xpath="//iframe[contains(@title,'Salesforce - Enterprise Edition')]")
	private WebElement editPageLayoutFrame_Lighting;

	/**
	 * @return the editPageLayoutFrame_Lighting
	 */
	public WebElement getEditPageLayoutFrame_Lighting(int timeOut) {
		return isDisplayed(driver, editPageLayoutFrame_Lighting, "Visibility", timeOut, "edit page layout frame in lighting");
	}

	@FindBy(xpath="//em[@class='x-btn-split']//button[@type='button'][contains(text(),'Save')]")
	private WebElement pageLayoutSaveBtn;

	/**
	 * @return the pageLayoutSaveBtn
	 */
	public WebElement getPageLayoutSaveBtn(object obj,int timeOut) {
		String xpath="";
		WebElement ele=null;
		if (obj==object.Global_Actions) {
			xpath="//table[@id='saveBtn']";
			ele=FindElement(driver, xpath, "save", action.BOOLEAN, 10);
			return isDisplayed(driver, ele, "Visibility", timeOut, "pagelayout save button");
		}
		else
			return isDisplayed(driver, pageLayoutSaveBtn, "Visibility", timeOut, "pagelayout save button");
	}

	@FindBy(xpath="//iframe[contains(@title,'Salesforce - Enterprise Edition')]")
	private WebElement setupPageIframe;

	/**
	 * @return the userIframe
	 */
	public WebElement getSetUpPageIframe(int timeOut) {
		ThreadSleep(10000);
		return isDisplayed(driver, setupPageIframe, "Visibility", timeOut, "active users iframe");
	}

	@FindBy(xpath="//td[contains(@id,'ButtonRow')]//input[@name='save']")
	private WebElement createUserSaveBtn_Lighting;

	/**
	 * @return the createUserSaveBtn
	 */
	public WebElement getCreateUserSaveBtn_Lighting(int timeOut) {
		return isDisplayed(driver, createUserSaveBtn_Lighting, "Visibility", timeOut, " save button in lighting");
	}

	@FindBy(id = "ImportedPackage_font")
	private WebElement installedPackageLink_Classic;

	/**
	 * @return the installedpackageLink
	 */
	@FindBy(xpath="//a[contains(@href,'/setup/ImportedPackage/home')]")
	private WebElement installedPackageLink_Lighting;

	public WebElement getInstalledPackageLink(int timeOut) {
		return isDisplayed(driver, installedPackageLink_Lighting, "Visibility", timeOut, "Installed package in ");
	}

	@FindBy(xpath="//iframe[@id='available']")
	private WebElement installedPackageFrame;

	@FindBy(xpath="//iframe[contains(@title,'Add Users:')]")
	private WebElement installedPackageParentFrame_Lighting;

	/**
	 * @return the installedPackageFrame_Lighting
	 */
	public WebElement getInstalledPackageParentFrame_Lighting(int timeOut) {
		return isDisplayed(driver, installedPackageParentFrame_Lighting, "Visibility", timeOut, " Installed Package Parent Frame in Lightning");
	}

	/**
	 * @return the installedPackageFrame
	 */
	public WebElement getInstalledPackageFrame(int timeOut) {
		return isDisplayed(driver, installedPackageFrame, "Visibility", timeOut, "Add Users frame in installed package.");
	}

	@FindBy(xpath = "//label[text()='Quick Find']/following-sibling::input")
	private WebElement quickFindSearch;

	public WebElement getquickFindSearch(int timeOut) {
		return isDisplayed(driver, quickFindSearch, "Visibility", timeOut, "quickFindSearch");
	}

	@FindBy(xpath = "//button[@title='Custom Field']")
	private WebElement customFieldNewButton;

	public WebElement getCustomFieldNewButton(int timeOut) {
		return isDisplayed(driver, customFieldNewButton, "Visibility", timeOut, "custom field new button");
	}

	public WebElement getNewCustomFieldFrame(String objectName, int timeOut) {
		if(objectName.toString().equalsIgnoreCase(object.Deal.toString())) {
			objectName="Pipeline";
		}
		String xpath="//iframe[contains(@title,'"+objectName.replace("_", " ")+"')]";
		WebElement ele= FindElement(driver, xpath,objectName+" new object frame xpath", action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "visibility", timeOut,objectName+" new object frame xpath");
	}

	public WebElement getNewCustomFieldDataTypeOrFormulaReturnType(String dataType, int timeOut) {
		String xpath="//label[text()='"+dataType+"']/preceding-sibling::input";
		WebElement ele= FindElement(driver, xpath,dataType+" data type xpath", action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "visibility", timeOut,dataType+" data type xpath");
	}


	@FindBy(xpath = "//div[@class='pbTopButtons']//input[@name='goNext']")
	private WebElement customFieldNextBtn;

	public WebElement getCustomFieldNextBtn(int timeOut) {
		return isDisplayed(driver, customFieldNextBtn, "Visibility", timeOut, "custom field next button");
	}

	@FindBy(xpath="//input[@id='quickfind']")
	private WebElement quickFindSearchBox;

	public WebElement getQuickFindSearchBox(String environment, String mode,int timeOut) {
		return isDisplayed(driver, quickFindSearchBox, "Visibility", timeOut, "quick Find Search Box "+mode);
	}


	@FindBy(xpath = "//input[@id='MasterLabel']")
	private WebElement fieldLabelTextBox;
	@FindBy(xpath = "//*[text()='Label']/../following-sibling::td//input")
	private WebElement fieldLabelTextBox1;

	public WebElement getFieldLabelTextBox1(int timeOut) {
		return isDisplayed(driver, fieldLabelTextBox1, "Visibility", timeOut, "field label text box");
	}

	@FindBy(xpath = "//*[text()='API Name']/following-sibling::td//input")
	private WebElement apiLabelTextBox;

	public WebElement getapiLabelTextBox(int timeOut) {
		return isDisplayed(driver, apiLabelTextBox, "Visibility", timeOut, "field label text box");
	}
	public WebElement getFieldLabelTextBox(int timeOut) {
		return isDisplayed(driver, fieldLabelTextBox, "Visibility", timeOut, "field label text box");
	}

	@FindBy(xpath = "//div[@class='pbTopButtons']//input[@name='save']")
	private WebElement customFieldSaveBtn;

	public WebElement getCustomFieldSaveBtn(int timeOut) {
		return isDisplayed(driver, customFieldSaveBtn, "Visibility", timeOut, "custom field save button");
	}


	public WebElement getObjectFeatureNewButton(ObjectFeatureName objectFeatureName, int timeOut) {
		String xpath ="//button[@title='"+objectFeatureName+"']";
		WebElement ele= FindElement(driver, xpath,objectFeatureName+" new button xpath", action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "visibility", timeOut,objectFeatureName+" new button xpath");
	}

	@FindBy(xpath = "//input[@id='MasterLabel']")
	private WebElement fieldSetLabelTextBox;

	public WebElement getFieldSetLabelTextBox(int timeOut) {
		return isDisplayed(driver, fieldSetLabelTextBox, "visibility", timeOut,"field set label name");
	}

	@FindBy(xpath = "//textarea[@id='Description']")
	private WebElement fieldSetWhereIsThisUsedTextArea;

	public WebElement getFieldSetWhereIsThisUsedTextArea(int timeOut) {
		return isDisplayed(driver, fieldSetWhereIsThisUsedTextArea, "Visibility", timeOut, "field Set Where Is This Used TextArea");
	}

	@FindBy(xpath = "//td[@id='topButtonRow']//input[@title='Save']")
	private WebElement saveButton;

	public WebElement getSaveButton(int timeOut) {
		return isDisplayed(driver, saveButton, "visibility", timeOut,"save button");
	}

	@FindBy(xpath = "//div[@id='defaultView']/div")
	private WebElement FieldSetdefaultViewDragAndDropTextLabel;

	public WebElement getFieldSetdefaultViewDragAndDropTextLabel(int timeOut) {
		return isDisplayed(driver, FieldSetdefaultViewDragAndDropTextLabel, "Visibility", timeOut, "default view drag and drop text label");
	}

	public WebElement getCreatedFieldSetLabelNameText(String fieldSetName, int timeOut) {
		String xpath="//span[contains(text(),'"+fieldSetName+"')]/..";
		WebElement ele= FindElement(driver, xpath,fieldSetName+" label name text", action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "visibility", timeOut,fieldSetName+" label name text");
	}


	public List<WebElement> getDefaultFieldSetLabelsList(){
		return FindElements(driver, "//div[@id='defaultView']/div//table//td[2]/div", "default field set label text");
	}

	@FindBy(xpath = "//iframe[contains(@title,'Field Set: New Field Set')]")
	private WebElement fieldSetComponentFrame;

	public WebElement getFieldSetComponentFrame(int timeOut) {
		return isDisplayed(driver, fieldSetComponentFrame, "Visibility", timeOut, "field set component frame");
	}

	@FindBy(xpath = "//iframe[contains(@title,'Salesforce - Enterprise Edition')]")
	private WebElement fieldAndRelationShipFrame;

	public WebElement getFieldAndRelationShipFrame(int timeOut) {
		return isDisplayed(driver, fieldAndRelationShipFrame, "Visibility", timeOut, "View Field Accessibility Frame");
	}

	public WebElement getObjectEditOrSetFieldSecurityOrViewFieldAccessbilityBtn(String objectButtonLabelName, int timeOut) {
		String xpath="//input[@title='"+objectButtonLabelName+"']";
		WebElement ele= FindElement(driver, xpath,objectButtonLabelName+" button xpath", action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "visibility", timeOut,objectButtonLabelName+" button xpath");
	}

	@FindBy(xpath = "//select[@id='zSelect']")
	private WebElement fieldAccessbilityDropDown;

	public WebElement getFieldAccessbilityDropDown(int timeOut) {
		return isDisplayed(driver, fieldAccessbilityDropDown, "Visibility", timeOut, "field accessbility drop down");
	}


	public WebElement getfieldAccessOptionLink(String fieldLabel, String profileName, int timeOut) {
		String xpath="//div[contains(@id,'"+fieldLabel+"') and contains(@style,'block')]//th[text()='"+profileName+"']/following-sibling::td/a";
		WebElement ele= FindElement(driver, xpath,profileName+" link xpath", action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "visibility", timeOut,profileName+" link xpath");
	}

	@FindBy(xpath = "//input[@id='p11']")
	private WebElement fieldLevelSecurityVisibleCheckBox;

	public WebElement getFieldLevelSecurityVisibleCheckBox(int timeOut) {
		return isDisplayed(driver, fieldLevelSecurityVisibleCheckBox, "Visibility", timeOut, "field Level Security Visible CheckBox");
	}

	@FindBy(xpath = "//td[@class='pbButtonb']//input[@title='Save']")
	private WebElement viewAccessbilityDropDownSaveButton;

	public WebElement getViewAccessbilityDropDownSaveButton(int timeOut) {
		return isDisplayed(driver, viewAccessbilityDropDownSaveButton, "Visibility", timeOut, "view Accessbility DropDown Save Button");
	}

	public List<WebElement> getDraggedObjectListInCreateFieldSet(){
		return FindElements(driver, "//div[@id='defaultView']/div", "dragged object list in created field set");
	}

	@FindBy(xpath = "//div[@class='pbBody canvasDrop']")
	private WebElement pageLayoutDropLocation;

	public WebElement getPageLayoutDropLocation(int timeOut) {
		return isDisplayed(driver, pageLayoutDropLocation, "Visibility", timeOut, "page layout drop location");
	}

	public WebElement getDraggedFieldsLabelAndValueXpath(String label, int timeOut) {
		String xpath="//div[@class='itemLabel']/span[text()='"+label+"']/../following-sibling::div[text()='www.salesforce.com']";
		WebElement ele= FindElement(driver, xpath,label+" xpath", action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "visibility", timeOut,label+" xpath");
	}

	@FindBy(xpath = "//div[@data-aura-class='uiScroller']")
	private WebElement appMangerScroll;

	public WebElement getAppMangerScroll(int timeOut) {
		return isDisplayed(driver, appMangerScroll, "Visibility", timeOut, "ap pManger Scroll");
	}

	@FindBy(xpath = "//*[@title='Record Type']")
	private WebElement recordTypeNewButton;

	public WebElement getRecordTypeNewButton(int timeOut) {
		return isDisplayed(driver, recordTypeNewButton, "Visibility", timeOut, "Record Type New Button");
	}

	@FindBy(xpath = "//input[@id='selectAllProfiles']")
	private WebElement makeAvailableCheckBox;

	public WebElement getMakeAvailableCheckBox(int timeOut) {
		return isDisplayed(driver, makeAvailableCheckBox, "Visibility", timeOut, "make Available CheckBox");
	}

	@FindBy(xpath = "//div[@class='pbBottomButtons']//input[@name='goNext']")
	private WebElement customFieldNextBtn2;

	public WebElement getCustomFieldNextBtn2(int timeOut) {
		return isDisplayed(driver, customFieldNextBtn2, "Visibility", timeOut, " next button");
	}

	@FindBy(xpath = "//input[@id='selectAllMakeDefaults']")
	private WebElement makeDefaultCheckBox;

	public WebElement getMakeDefaultCheckBoxCheckBox(int timeOut) {
		return isDisplayed(driver, makeDefaultCheckBox, "Visibility", timeOut, "make Default CheckBox");
	}

	@FindBy(xpath = "//input[@title='New Custom Object Tabs']")
	private WebElement customObjectTabNewBtn;

	public WebElement getCustomObjectTabNewBtn(int timeOut) {
		return isDisplayed(driver, customObjectTabNewBtn, "Visibility", timeOut, "custom Object Tab NewBtn");
	}

	@FindBy(xpath = "//select[@id='p1']")
	private WebElement objectDropDown;

	public WebElement getObjectDropDown(int timeOut) {
		return isDisplayed(driver, objectDropDown, "Visibility", timeOut, "Object drop down");
	}

	@FindBy(xpath="//img[@title='Lookup (New Window)']")
	private WebElement tabObjectLookUpIcon;

	/**
	 * @return the tabObjectLookUpIcon
	 */
	public WebElement getTabObjectLookUpIcon(int timeOut) {
		return isDisplayed(driver, tabObjectLookUpIcon, "Visibility", timeOut, "tab Object Look Up Icon");
	}


	@FindBy(xpath="//div[@aria-labelledby='appImageLabel']//span")
	private WebElement imgName;

	/**
	 * @return the tabObjectLookUpIcon
	 */
	public WebElement getImgName(int timeOut) {
		return isDisplayed(driver, imgName, "Visibility", timeOut, "Image Name");
	}



	@FindBy(xpath = "//input[@type='file']")
	private WebElement uploadPhotoButton;

	public WebElement getuploadPhotoButton(String projectName,int timeOut) {
		return isDisplayed(driver, uploadPhotoButton, "Visibility", timeOut, "upload photo button");

	}

	@FindBy(xpath = "//button[text()='Clear']")
	private WebElement imgClearButton;

	public WebElement getImgClearButton(String projectName,int timeOut) {
		return isDisplayed(driver, imgClearButton, "Visibility", timeOut, "Img Clear button");

	}

	@FindBy(xpath = "//*[text()='New Flow']")
	private WebElement newFlowButton;

	public WebElement getNewFlowButton(String projectName,int timeOut) {
		return isDisplayed(driver, newFlowButton, "Visibility", timeOut, "New Flow button");

	}

	@FindBy(xpath = "//*[text()='Screen Flow']")
	private WebElement screenFlow;

	public WebElement getScreenFlow(String projectName,int timeOut) {
		return isDisplayed(driver, screenFlow, "Visibility", timeOut, "Screen Flow button");

	}
	
	@FindBy(xpath = "//*[text()='Screen Flow']")
	private WebElement freeForm;

	public WebElement getfreeForm(String projectName,int timeOut) {
		return isDisplayed(driver, freeForm, "Visibility", timeOut, "Free Form");

	}
	
	@FindBy(xpath = "//*[text()='Industry']/../following-sibling::*//select")
	private WebElement industryDropdownList;

	public WebElement getIndustryDropdownList(int timeOut) {
		scrollDownThroughWebelement(driver, industryDropdownList, "Industry DropDown List");
		return isDisplayed(driver, industryDropdownList, "Visibility", timeOut, "Industry DropDown List");

	}
	

	public WebElement getIndustryDropdownList(String dropDownLabel,String dropDownValue,int timeOut) {
		ThreadSleep(1000);
		try {
			if (dropDownValue.contains("None") || dropDownValue.contains("none")) {
				
				String xpath = "//*[text()='"+dropDownLabel+"']/following-sibling::*";
				WebElement ele = FindElement(driver, xpath, dropDownLabel+" "+dropDownValue, action.SCROLLANDBOOLEAN, timeOut);
				System.err.println("getIndustryDropdownList "+ele.getText().trim());
				if (ele.getText().trim().isEmpty() || ele.getText().trim().equals("")) {
					System.err.println("verified getIndustryDropdownList "+ele.getText().trim());	
					return ele;
				} else {
					log(LogStatus.ERROR, "Value not verified "+dropDownValue, YesNo.Yes);
					sa.assertTrue(false, "Value not verified "+dropDownValue);
				}
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String xpath = "//*[text()='"+dropDownLabel+"']/following-sibling::*[text()='"+dropDownValue+"']";
		WebElement ele = FindElement(driver, xpath, dropDownLabel+" "+dropDownValue, action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, dropDownLabel+" "+dropDownValue);

	}
	
	public WebElement getRegionDropdownList(String dropDownLabel,String dropDownValue,int timeOut) {
		ThreadSleep(1000);
		try {
			if (dropDownValue.contains("None") || dropDownValue.contains("none")) {
				
				String xpath = "//*[text()='"+dropDownLabel+"']/following-sibling::*";
				WebElement ele = FindElement(driver, xpath, dropDownLabel+" "+dropDownValue, action.SCROLLANDBOOLEAN, timeOut);
				System.err.println("getRegionDropdownList "+ele.getText().trim());
				if (ele.getText().trim().isEmpty() || ele.getText().trim().equals("")) {
					System.err.println("verified getRegionDropdownList "+ele.getText().trim());	
					return ele;
				} else {
					log(LogStatus.ERROR, "Value not verified "+dropDownValue, YesNo.Yes);
					sa.assertTrue(false, "Value not verified "+dropDownValue);
				}
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String xpath = "//*[text()='"+dropDownLabel+"']/following-sibling::*[text()='"+dropDownValue+"']";
		WebElement ele = FindElement(driver, xpath, dropDownLabel+" "+dropDownValue, action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, dropDownLabel+" "+dropDownValue);

	}
	
	@FindBy(xpath = "//*[text()='Region']/../following-sibling::*//select")
	private WebElement regionDropdownList;

	public WebElement getRegionDropdownList(int timeOut) {
		scrollDownThroughWebelement(driver, regionDropdownList, "Region DropDown List");
		return isDisplayed(driver, regionDropdownList, "Visibility", timeOut, "Region DropDown List");

	}
	@FindBy(xpath = "//*[text()='Time Zone']/../following-sibling::*//select")
	private WebElement timezoneDropdownList;

	public WebElement gettimezoneDropdownList(int timeOut) {
		scrollDownThroughWebelement(driver, timezoneDropdownList, "time zone Dropdown List");
		return isDisplayed(driver, timezoneDropdownList, "Visibility", timeOut, "time zone Dropdown List");

	}
	
	@FindBy(xpath = "//input[@title='New Action']")
	private WebElement newActionBtn;

	public WebElement getNewActionBtnNewBtn(int timeOut) {
		return isDisplayed(driver, newActionBtn, "Visibility", timeOut, "New ACtion Button");
	}

	@FindBy(xpath = "//h3[text()='Predefined Field Values']/../following-sibling::td//input")
	private WebElement predefinedFieldValuesNewButton;

	public WebElement getpredefinedFieldValuesNewButtonn(int timeOut) {
		return isDisplayed(driver, predefinedFieldValuesNewButton, "Visibility", timeOut, "Predefined Field Values New Button");
	}
	
	@FindBy(xpath = "//select[@id='ColumnEnumOrId']")
	private WebElement selectFieldName;

	public WebElement getSelectFieldName(int timeOut) {
		return isDisplayed(driver, selectFieldName, "Visibility", timeOut, "Select Field Name");
	}
	
	@FindBy(xpath = "//textarea[@id='DefaultValueFormula']")
	private WebElement formulaValueTextArea;

	public WebElement getFormulaValueTextArea(int timeOut) {
		return isDisplayed(driver, formulaValueTextArea, "Visibility", timeOut, "formula Value TextArea");
	}
	
	@FindBy(xpath = "//*[@title='Page Layout']")
	private WebElement pageLayoutNewButton;

	public WebElement getPageLayoutNewButton(int timeOut) {
		return isDisplayed(driver, pageLayoutNewButton, "Visibility", timeOut, "Page Layout New Button");
	}
	
	@FindBy(xpath = "//select[@id='p2']")
	private WebElement selectExistingPageLayout;

	public WebElement getSelectExistingPageLayout(int timeOut) {
		return isDisplayed(driver, selectExistingPageLayout, "Visibility", timeOut, "Select Existing Page Layout");
	}

	@FindBy(xpath = "//select[@id='p13']")
	private WebElement applyOneLayoutToAllProfiles;

	public WebElement getApplyOneLayoutToAllProfiles(int timeOut) {
		return isDisplayed(driver, applyOneLayoutToAllProfiles, "Visibility", timeOut, "Select Page Layout");
	}
	@FindBy(xpath = "//div[@title='Visualforce Pages']//a")
	private WebElement visualForcePagesLink;

	public WebElement getvisualForcePagesLink(int timeOut) {
		return isDisplayed(driver, visualForcePagesLink, "Visibility", timeOut, "Select Page Layout");
	}
	
	@FindBy(xpath = "//h3[text()='Values']/../following-sibling::td//input[@name='new']")
	private WebElement valuesNewButton;

	public WebElement getValuesNewButton(int timeOut) {
		return isDisplayed(driver, valuesNewButton, "Visibility", timeOut, "Values New Button");
	}
	
	@FindBy(xpath = "//*[text()='Add Utility Item']")
	private WebElement addUtilityItem;

	public WebElement getAddUtilityItem(int timeOut) {
		return isDisplayed(driver, addUtilityItem, "Visibility", timeOut, "Add Utility Item");
	}
	
	@FindBy(xpath = "//div[@class='paletteSearch']//input")
	private WebElement searchIconOnUtilityItem;

	public WebElement getSearchIconOnUtilityItem(int timeOut) {
		return isDisplayed(driver, searchIconOnUtilityItem, "Visibility", timeOut, "Search Icon on Add Utility Item");
	}
	
	@FindBy(xpath = "//div[@class='buttonsDiv']/button//span[text()='Move this utility down the list.']/..")
	private WebElement moveThisUtilityDownTheList;

	public WebElement getMoveThisUtilityDownTheList(int timeOut) {
		String xpath = "//div[@class='buttonsDiv']/button//span[text()='Move this utility down the list.']/..";
		List<WebElement> eleList = FindElements(driver, xpath, "Move this utility down the list.");
		WebElement ele = null;
		for (WebElement webElement : eleList) {
		webElement=isDisplayed(driver, webElement, "Visibility", 2, "Move this utility down the list.");
		if (webElement!=null) {
				return webElement;
			} else {

			}
		}
		return ele;

	
	}
	
	public WebElement getRemoveUtilityTheList(int timeOut) {
		String xpath = "//div[@class='buttonsDiv']/button[3]";
		List<WebElement> eleList = FindElements(driver, xpath, "Remove");
		WebElement ele = null;
		for (WebElement webElement : eleList) {
		webElement=isDisplayed(driver, webElement, "Visibility", 2, "Remove");
		if (webElement!=null) {
				return webElement;
			} else {

			}
		}
		return ele;

	
	}
	
	
	
}
