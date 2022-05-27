package com.navatar.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.SDGCreationLabel;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;

import static com.navatar.generic.CommonLib.*;

import java.util.ArrayList;
import java.util.List;

public class EditPage extends BasePageBusinessLayer {

	public EditPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	@FindBy (xpath = "//iframe[@class='surfaceFrame']")
	private WebElement editPageFrame;

	/**
	 * @return the editPageFrame
	 */
	public WebElement getEditPageFrame(String projectName,int timeOut) {
		return isDisplayed(driver, editPageFrame, "Visibility", timeOut, "Edit Page Frame");
	}
	
	@FindBy (xpath = "//*[text()='SDG Config Data Provider']/following-sibling::div/input")
	private WebElement sdgConfigDataProviderTextBox;

	/**
	 * @return the sdgConfigDataProviderTextBox
	 */
	public WebElement getsdgConfigDataProviderTextBox(String projectName,int timeOut) {
		return isDisplayed(driver, sdgConfigDataProviderTextBox, "Visibility", timeOut, "sdg Config Data Provider TextBox");
	}
	
	
	@FindBy (xpath = "//*[text()='Default SDG Toggle']/following-sibling::div/input")
	private WebElement defaultSDGToggleTextBox;

	/**
	 * @return the defaultSDGToggleTextBox
	 */
	public WebElement getDefaultSDGToggleTextBox(String projectName,int timeOut) {
		return isDisplayed(driver, defaultSDGToggleTextBox, "Visibility", timeOut, "Default SDG Toggle TextBox");
	}
	
	@FindBy (xpath = "//button[text()='Save']")
	private WebElement editPageSaveButton;

	/**
	 * @return the editPageSaveButton
	 */
	public WebElement getEditPageSaveButton(String projectName,int timeOut) {
		return isDisplayed(driver, editPageSaveButton, "Visibility", timeOut, "Edit Page Save Button");
	}
	
	@FindBy (xpath = "//a[contains(@class,'backButton')]")
	private WebElement editPageBackButton;

	/**
	 * @return the editPageBackButton
	 */
	public WebElement getEditPageBackButton(String projectName,int timeOut) {
		return isDisplayed(driver, editPageBackButton, "Visibility", timeOut, "Edit Page Back Button");
	}
	
	
	@FindBy (xpath = "//*[text()='Search...']/..//*[@placeholder='Search...']")
	private WebElement editPageSeachTextBox;

	/**
	 * @return the editPageSeachTextBox
	 */
	public WebElement getEditPageSeachTextBox(String projectName,int timeOut) {
		return isDisplayed(driver, editPageSeachTextBox, "Visibility", timeOut, "Edit Page Search TextBox");
	}
	
	@FindBy (xpath = "//*[text()='Data Provider']/following-sibling::div//input")
	private WebElement elgDataProviderTextBox;

	/**
	 * @return the elgDataProviderTextBox
	 */
	public WebElement getElgDataProviderTextBox(String projectName,int timeOut) {
		return isDisplayed(driver, elgDataProviderTextBox, "Visibility", timeOut, "ELG Data Provider TextBox");
	}
	
	
	@FindBy (xpath = "//*[text()='Title']/..//following-sibling::div//input[@type='text']")
	private WebElement elgTitleTextBox;

	/**
	 * @return the elgTitleTextBox
	 */
	public WebElement getElgTitleTextBox(String projectName,int timeOut) {
		return isDisplayed(driver, elgTitleTextBox, "Visibility", timeOut, "ELG Title TextBox");
	}
	
	
	@FindBy (xpath = "//*[text()='Enable Toggle']/..//preceding-sibling::input")
	private WebElement enableToggleCheckBox;

	/**
	 * @return the enableToggleCheckBox
	 */
	public WebElement getEnableToggleCheckBox(String projectName,int timeOut) {
		return isDisplayed(driver, enableToggleCheckBox, "Visibility", timeOut, "Enable Toggle CheckBox");
	}
	
	@FindBy(xpath = "//*[text()='Field Set Name']/following-sibling::div/input")
	private WebElement fieldSetNameTextBox;

	public WebElement getFieldSetNameTextBox(int timeOut) {
		return isDisplayed(driver, fieldSetNameTextBox, "Visibility", timeOut, "field set name text box");
	}
	
	@FindBy(xpath = "//a[@aria-describedby='backButton']")
	private WebElement backButton;

	public WebElement getBackButton(int timeOut) {
		return isDisplayed(driver, backButton, "Visibility", timeOut, "back button");
	}
	
	@FindBy (xpath = "//a[text()='Add Component(s) Here']")
	private WebElement addComponent;

	/**
	 * @return the addComponent
	 */
	public WebElement getAddComponent(String projectName,int timeOut) {
		return isDisplayed(driver, addComponent, "Visibility", timeOut, "Add Component(s) Here");
	}
	
	
	@FindBy (xpath = "//*[text()='Data Provider']/following-sibling::div//*[@data-key='search']/../..")
	private WebElement elgDataProviderTextBoxSearchIcon;

	/**
	 * @return the elgDataProviderTextBoxSearchIcon
	 */
	public WebElement getElgDataProviderTextBoxSearchIcon(String projectName,int timeOut) {
		return isDisplayed(driver, elgDataProviderTextBoxSearchIcon, "Visibility", timeOut, "ELG Data Provider TextBox Search Box Icon");
	}
	
	@FindBy(xpath = "//div[@data-label='Navatar Fieldset']/div[@class='toolbox']")
	private WebElement fieldSetCompoentXpath;

	public WebElement getFieldSetCompoentXpath(int timeOut) {
		scrollDownThroughWebelement(driver, fieldSetCompoentXpath, "");
		return fieldSetCompoentXpath;
	}
	
	@FindBy(xpath = "//*[contains(text(),'Image Field  Name')]/following-sibling::div/input")
	private WebElement imageFieldNameTextBox;

	public WebElement getImageFieldNameTextBox(int timeOut) {
		return isDisplayed(driver, imageFieldNameTextBox, "Visibility", timeOut, "image field name text box");
	}
	
	@FindBy (xpath = "//button[text()='Activate']")
	private WebElement activateButton;

	/**
	 * @return the activateButton
	 */
	public WebElement getActivateButton(String projectName,int timeOut) {
		return isDisplayed(driver, activateButton, "Visibility", timeOut, "Activate Button");
	}
	
	@FindBy (xpath = "(//button[text()='Save'])[2]")
	private WebElement editPageSaveButton2;

	/**
	 * @return the editPageSaveButton
	 */
	public WebElement getEditPageSaveButton2(String projectName,int timeOut) {
		return isDisplayed(driver, editPageSaveButton2, "Visibility", timeOut, "Edit Page Save Button2");
	}
	
	@FindBy (xpath = "//button[text()='Finish']")
	private WebElement finishButton2;

	/**
	 * @return the finishButton2
	 */
	public WebElement getFinishButton2(String projectName,int timeOut) {
		return isDisplayed(driver, finishButton2, "Visibility", timeOut, "Finish Button2");
	}
	
	
	public WebElement getFieldTextbox(String projectName,String pageLabel,int timeOut) {
		String label=pageLabel.replace("_", " ");
		String xpath = "//*[text()='"+label+"']/following-sibling::div/input";
		WebElement ele=FindElement(driver, xpath,pageLabel.toString(), action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "Finish Button2");
	}
	
	@FindBy (xpath = "//*[text()='Number of Records to Display']/following-sibling::div[2]")
	private WebElement noOfRecordsErrorMessage;

	/**
	 * @return the finishButton2
	 */
	public WebElement getnoOfRecordsErrorMessage(String projectName,int timeOut) {
		return isDisplayed(driver, noOfRecordsErrorMessage, "Visibility", timeOut, "noOfRecordsErrorMessage");
	}
	@FindBy (xpath = "//span[text()='Expanded']/../..//input[@type='checkbox']")
	private WebElement expandedCheckbox;

	/**
	 * @return the finishButton2
	 */
	public WebElement getexpandedCheckbox(String projectName,int timeOut) {
		return isDisplayed(driver, expandedCheckbox, "Visibility", timeOut, "expandedCheckbox");
	}
	
	@FindBy (xpath = "//*[@data-id='Field Set Name']/div/*/div[@role='alert']")
	private WebElement noOfRecordsErrorPopup;

	/**
	 * @return the finishButton2
	 */
	public WebElement getnoOfRecordsErrorPopup(String projectName,int timeOut) {
		return isDisplayed(driver, noOfRecordsErrorPopup, "Visibility", timeOut, "noOfRecordsErrorPopup");
	}
	
	@FindBy (xpath = "//div[@class='error']")
	private WebElement noOfRecordsErrorPopupForAccordion;

	/**
	 * @return the finishButton2
	 */
	public WebElement getnoOfRecordsErrorPopupForAccordion(String projectName,int timeOut) {
		return isDisplayed(driver, noOfRecordsErrorPopupForAccordion, "Visibility", timeOut, "noOfRecordsErrorPopup");
	}
	@FindBy (xpath = "//h2[text()='Error']/../following-sibling::div[contains(@class,'footer')]//button[text()='OK']")
	private WebElement noOfRecordsErrorPopupOK;

	/**
	 * @return the finishButton2
	 */
	public WebElement getnoOfRecordsErrorPopupOK(String projectName,int timeOut) {
		return isDisplayed(driver, noOfRecordsErrorPopupOK, "Visibility", timeOut, "noOfRecordsErrorPopupOK");
	}
	@FindBy (xpath = "//div[contains(@class,'elementProxyContainer')]//a")
	private WebElement calendarOnEditPage;

	/**
	 * @return the finishButton2
	 */
	public WebElement getcalendarOnEditPage(String projectName,int timeOut) {
		return isDisplayed(driver, calendarOnEditPage, "Visibility", timeOut, "calendarOnEditPage");
	}
	
	@FindBy(xpath="//button[contains(@class,'addTab')]")
	private WebElement addTabLink;

	/**
	 * @return the addTabLink
	 */
	public WebElement getAddTabEditPageLink(int timeOut) {
		return isDisplayed(driver, addTabLink, "Visibility", timeOut, "Add a Tab Link");
	}
	
	
	/**
	 * @return the addTabLink
	 */
	public List<WebElement> getAlreadyAddedTabListEditPage(int timeOut) {
		String xpath = "//div[@class='visualEditorSortableList']//li";
		List<WebElement> list = new ArrayList<WebElement>();

		list = FindElements(driver, xpath, "already added tab list on edit page");
		return list;
	}
	
	@FindBy(xpath="//section[@class='visualEditorComponentPropertiesEditorSection']//select")
	private WebElement tablabelDropdownEditPage;

	/**
	 * @return the addTabLink
	 */
	public WebElement getTabLabelDropdownOnEditPage(int timeOut) {
		return isDisplayed(driver, tablabelDropdownEditPage, "Visibility", timeOut, "Add a Tab Link");
	}
	
	@FindBy(xpath="//section[@class='visualEditorComponentPropertiesEditorSection']//input")
	private WebElement tablabelInputEditPage;

	/**
	 * @return the addTabLink
	 */
	public WebElement getTabLabelInputOnEditPage(int timeOut) {
		return isDisplayed(driver, tablabelInputEditPage, "Visibility", timeOut, "Add a Tab Link");
	}
	
	@FindBy(xpath="//button[contains(@class,'ItemAttributesEditorDone')]")
	private WebElement tablabelDoneButtonOnEditPage;

	/**
	 * @return the donebutton
	 */
	public WebElement getTabLabelDoneButtonOnEditPage(int timeOut) {
		return isDisplayed(driver, tablabelDoneButtonOnEditPage, "Visibility", timeOut, "Add a Tab Link");
	}
	@FindBy(xpath="//button[contains(@class,'save')]")
	private WebElement saveButtonEditpage;

	/**
	 * @return the donebutton
	 */
	public WebElement getSaveButtonOnEditPage(int timeOut) {
		return isDisplayed(driver, saveButtonEditpage, "Visibility", timeOut, "Add a Tab Link");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@FindBy(xpath="//iframe[@title='Salesforce - Enterprise Edition']")
	private WebElement locator;

	public WebElement getLocator() {
		return locator;
	}
	
	
	 
	
	public WebElement getspinnericon()
	{
		WebElement spinnericon=driver.findElement(By.xpath("//div[@class='forceDotsSpinner']/div[@role='status']"));
		return spinnericon;
	}

	public WebElement getnewButton() {
		WebElement newButton= driver.findElement(By.xpath("//input[@name='new']"));
		return newButton;
	}
	
	public WebElement getlabelName() {
		WebElement labelName=driver.findElement(By.xpath("//label[text()='Label']/following-sibling::div/input"));	
		return labelName;
	}
	
	public WebElement getnextButton() {
		WebElement nextButton=driver.findElement(By.xpath("//a[text()='Next' and contains(@class,'primary-button')]"));
		return nextButton;
	}
	

	public WebElement getfinishButton() {
		WebElement finishButton=driver.findElement(By.xpath("//a[text()='Finish']"));
		return finishButton;
		
	}
	
	@FindBy(xpath="//input[@placeholder='Search...']")
	private WebElement SearchonAppBuilder;

	public WebElement getSearchonAppBuilder(int timeOut) {
		return isDisplayed(driver, SearchonAppBuilder, "Visibility", timeOut, "object manage");
	}
	
	
	
	@FindBy(xpath="//iframe[@title='Surface']")
	private WebElement AppBuilderIframe;

	public WebElement getAppBuilderIframe(int timeOut) {
		return AppBuilderIframe;
	}
	
	@FindBy(xpath="//span[text()='Navatar SDG']")
	private WebElement NavatarSDGBtn;

	public WebElement getNavatarSDGBtn(int timeOut) {
		return isDisplayed(driver, NavatarSDGBtn, "Visibility", timeOut, "object manage");
		
	}
	

	
	
	@FindBy(xpath="//input[@name='Title']")
	
	private WebElement Title;

	public WebElement getTitle(int timeOut) {
		return isDisplayed(driver, Title, "Visibility", timeOut, "object manage");
		
	}
	
	
	@FindBy(xpath="//label[text()='Data Provider']/parent::lightning-grouped-combobox//input[@role='combobox']")
	private WebElement DataProvider;

	public WebElement getDataProvider(int timeOut) {
		return isDisplayed(driver, DataProvider, "Visibility", timeOut, "object manage");
		
	}
	
	@FindBy(xpath="//button[text()='Save']")
	private WebElement SaveButton;

	public WebElement getSaveButton(int timeOut) {
		return isDisplayed(driver, SaveButton, "Visibility", timeOut, "object manage");
		
	}
	
	@FindBy(xpath="//label[text()='Data Provider']/parent::lightning-grouped-combobox//lightning-base-combobox-formatted-text")
	private List<WebElement> DataProviderDropDownList;

	public List<WebElement> getDataProviderDropDownList(int timeOut) {
		return DataProviderDropDownList;
	}
	
	@FindBy(xpath="//button[text()='Activate']")
	private WebElement AvtivateButton;

	public WebElement getAvtivateButton(int timeOut) {
		return isDisplayed(driver, AvtivateButton, "Visibility", timeOut, "object manage");
		
	}
	
	@FindBy(xpath="//button[text()='Save' and contains(@class,'activateButton')]")
	private WebElement AvtivatesaveButton;

	public WebElement getAvtivatesaveButton(int timeOut) {
		return isDisplayed(driver, AvtivatesaveButton, "Visibility", timeOut, "object manage");
		
	}
	
	@FindBy(xpath="//button[text()='Finish']")
	private WebElement AvtivateFinishButton;

	public WebElement getAvtivateFinishButton(int timeOut) {
		return isDisplayed(driver, AvtivateFinishButton, "Visibility", timeOut, "object manage");
		
	}
	
	
	
	@FindBy(xpath="//button[text()='Activation...']")
	private WebElement AvtivationButton;

	public WebElement getAvtivationButton(int timeOut) {
		return isDisplayed(driver, AvtivationButton, "Visibility", timeOut, "object manage");
		
	}
	
	//a[@title='Insert a component before this one.']
	
	
	
	
			@FindBy(xpath="(//div[@id='brandBand_1']//div[@data-label='Navatar SDG'])[1]")
			private WebElement dropComponent;

			public WebElement dropComponent(int timeOut) {
				return isDisplayed(driver, dropComponent, "Visibility", timeOut, "object manage");
				
			}
			
			
			@FindBy(xpath="//span[text()='Navatar SDG']/parent::a")
			private WebElement dragComponent;

			public WebElement dragComponent(int timeOut) {
				return isDisplayed(driver, dragComponent, "Visibility", timeOut, "object manage");
				
			}
			
			@FindBy(xpath="(//a[@title='Insert a component before this one.'])[1]")
			private WebElement addComponentLink;

			public WebElement addComponentLink(int timeOut) {
				return isDisplayed(driver, addComponentLink, "Visibility", timeOut, "object manage");
				
			}
			
			@FindBy(xpath="(//a[@title='Insert a component before this one.'])[1]/parent::div")
			private WebElement addComponentDiv;

			public WebElement addComponentDiv(int timeOut) {
				return isDisplayed(driver, addComponentDiv, "Visibility", timeOut, "object manage");
				
			}
			@FindBy(xpath="//a[text()='Deals']")
			private WebElement dealHeader;

			public WebElement dealHeader(int timeOut) {
				return isDisplayed(driver, dealHeader, "Visibility", timeOut, "Deal Header");
				
			}@FindBy(xpath="//h2[contains(text(),'retrieved: 0')]")
			private WebElement afterAddComponentMsg;

			public WebElement afterAddComponentMsg(int timeOut) {
				return isDisplayed(driver, afterAddComponentMsg, "Visibility", timeOut, "Component Msg");
				
			}
			
			@FindBy(xpath = "//a[text()='Test']/ancestor::article//tbody/tr")
			private List<WebElement>  showDrpDownList;
			public List<WebElement> showDrpDownList(){
				return FindElements(driver, showDrpDownList, "Show DropDown List");
			}
		
			
			
			
			
			public WebElement TooltipElement(String Title) {
			WebElement TooltipElement;
			
			return TooltipElement = FindElement(driver,
					"//a[text()='" + Title + "']/ancestor::article/preceding-sibling::lightning-icon", "Tooltip",
					action.SCROLLANDBOOLEAN, 20);
			}
			
			//a[text()='Fund - First SDG Grid']/ancestor::article/preceding-sibling::lightning-icon
}
