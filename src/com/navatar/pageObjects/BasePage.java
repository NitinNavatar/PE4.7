/**
 * 
 */
package com.navatar.pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.FindElements;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import com.navatar.generic.BaseLib;
import com.navatar.generic.SoftAssert;
import com.navatar.generic.EnumConstants.*;
import static com.navatar.generic.AppListeners.appLog;
import static com.navatar.generic.CommonLib.*;

import java.util.Iterator;
import java.util.List;
/**
 * @author Parul Singh
 *
 */
public abstract class BasePage extends BaseLib {
	
	protected WebDriver driver;

	public BasePage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	@FindBy(xpath = "//a[@title='Logout']")
	private WebElement logout;
	
	@FindBy(xpath = "//a[contains(@href,'logout')]")
	private WebElement logout_Lightning;
	
	
	
	public WebElement getLogoutButton(int timeOut) {
			return isDisplayed(driver, logout_Lightning, "Visibility", timeOut, "Logout in User menu");
	}
	
	@FindBy(xpath = "//*[contains(text(),'Maintenance')]")
	private WebElement scheduledMaintenancePopUp;

	/**
	 * @return the scheduledMaintenancePopUp
	 */
	public WebElement getScheduledMaintenancePopUp(int timeOut) {
		return isDisplayed(driver, scheduledMaintenancePopUp, "Visibility", timeOut, "Scheduled Maintenance Pop Up", action.SCROLL);
	}
	
	@FindBy(xpath="//a[@class='continue']")
	private WebElement scheduledMaintenanceContinueLink;

	/**
	 * @return the scheduledMaintenanceContinueLink
	 */
	public WebElement getScheduledMaintenanceContinueLink(int timeOut) {
		return isDisplayed(driver, scheduledMaintenanceContinueLink, "Visibility", timeOut, "scheduled Maintenance Continue Link");
	}
	
	@FindBy(xpath="//a[@title='Contacts Tab']")
	private WebElement contactsTab;

	/**
	 * @return the contactsTab
	 */
	public WebElement getContactsTab(int timeOut) {
		return isDisplayed(driver, contactsTab, "Visibility", timeOut, "Contacts Tab");
	}
	
	@FindBy(xpath="//a[@title='Funds Tab']")
	private WebElement fundsTab;

	/**
	 * @return the fundsTab
	 */
	public WebElement getFundsTab(int timeOut) {
		return isDisplayed(driver, fundsTab, "Visibility", timeOut, "Funds Tab");
	}
	
	@FindBy(xpath="//a[@title='Institutions Tab']")
	private WebElement institutionsTab;

	/**
	 * @return the fundsTab
	 */
	public WebElement getInstitutionsTab(int timeOut) {
		return isDisplayed(driver, institutionsTab, "Visibility", timeOut, "Institutions Tab");
	}
	
	@FindBy(xpath="//a[@title='Fundraisings Tab']")
	private WebElement fundRaisingsTab;

	/**
	 * @return the fundRaisingsTab
	 */
	public WebElement getFundRaisingsTab(int timeOut) {
		return isDisplayed(driver, fundRaisingsTab, "Visibility", timeOut, "FundRaisings Tab");
	} 
	
	@FindBy(xpath="//select[@name='fcf']")
	private WebElement viewDropdown;

	/**
	 * @return the viewDropdown
	 */
	public WebElement getViewDropdown(int timeOut) {
		return isDisplayed(driver, viewDropdown, "Visibility", timeOut, "View Dropdown");
	}
	@FindBy(xpath="//input[@name='go']")
	private WebElement goButton;

	/**
	 * @return the goButton
	 */
	public WebElement getGoButton(int timeOut) {
		return isDisplayed(driver, goButton, "Visibility", timeOut, "Go Button");
	} 
	
	@FindBy(xpath="//input[@name='new']")
	private WebElement newButtonClassic;

	
	/**
	 * @return the newButton
	 */
	public WebElement getNewButton(String environment,String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			return isDisplayed(driver, newButtonClassic, "Visibility", timeOut, "New Button Classic");	
		}else{
			return newButtonLighting;	
		}
		
	}
	 
	@FindBy(xpath = "//a[text()='Close']")
	private WebElement lightingCloseButton;

	/**
	 * @return the lightingCloseButton
	 */
	public WebElement getLightingCloseButton(int timeOut) {
		return isDisplayed(driver, lightingCloseButton, "Visibility", timeOut, "Linghting Pop-Up Close Icon");
	}
	

	@FindBy(xpath = "//div[@id='userNavButton']/span")
	private WebElement userMenuTab;
	
	
	public List<WebElement> getUserMenuTab_Lightning(){
		return FindElements(driver, "//span[contains(@class,'userProfileCardTriggerRoot')]//img[@alt='User']", "user menu tab in lightning");
	}
	
	/**
	 * @return the userMenuTab
	 */
	public WebElement getUserMenuTab(int timeOut) {
			return isDisplayed(driver, userMenuTab, "Visibility", timeOut, "User Menu Tab");
			
	}

	
	@FindBy(xpath="//a[contains(@class,'menuTriggerLink slds-button')]//div[contains(@class,'tooltip-trigger uiTooltip')]")
	private WebElement settingIcon_Lighting;
	
	
	/**
	 * @return the settingTab_Lighting
	 */
	public WebElement getSettingLink_Lighting(int timeOut) {
		return isDisplayed(driver, settingIcon_Lighting, "Visibility", timeOut, "setting tab in lighting");
	}
	

	@FindBy(xpath="//div[@class='menuButton menuButtonRounded']")
	private WebElement UserNameAtUserMenuTab;
	
	
	/**
	 * @return the userNameAtUserMenuTab
	 */
	public WebElement getUserNameAtUserMenuTab(int timeOut) {
		return isDisplayed(driver, UserNameAtUserMenuTab, "Visibility", timeOut, "User Name at user menu tab");
	}
	
	@FindBy(xpath = "//a[text()='Settings']")
	private WebElement settingsLinkLightning;
	
	
	/**
	 * @return the settingsLinkLightning
	 */
	public WebElement getSettingsLinkLightning(int timeOut) {
		return isDisplayed(driver, settingsLinkLightning, "Visibility", timeOut, "settings Link on user menu Lightning");
	}

	@FindBy(xpath = "//a[text()='Display & Layout']")
	private WebElement displayAndLayout_Lightning;
	
	
	/**
	 * @return the displayAndLayout_Lightning
	 */
	public WebElement getDisplayAndLayout_Lightning(int timeOut) {
		return isDisplayed(driver, displayAndLayout_Lightning, "Visibility", timeOut, "displayAndLayout_Lightning");
	}
	
	@FindBy(xpath = "//a[text()='Record Page Settings']")
	private WebElement recordPageSettings;
	
	
	/**
	 * @return the recordPageSettings
	 */
	public WebElement getRecordPageSettings(int timeOut) {
		return isDisplayed(driver, recordPageSettings, "Visibility", timeOut, "recordPageSettings");
	}
	
	@FindBy(xpath = "//img[@alt='Select Related Lists']")
	private WebElement relatedListRecordPageSetting;
	
	

	/**
	 * @return the relatedListRecordPageSetting
	 */
	public WebElement getRelatedListRecordPageSetting(int timeOut) {
		return isDisplayed(driver, relatedListRecordPageSetting, "Visibility", timeOut, "relatedListRecordPageSetting");
	}
	
	@FindBy(xpath = "//img[@alt='Select Activity Timeline']")
	private WebElement activityTimelineRecordPageSetting;
	
	

	/**
	 * @return the activityTimelineRecordPageSetting
	 */
	public WebElement getActivityTimelineRecordPageSetting(int timeOut) {
		return isDisplayed(driver, activityTimelineRecordPageSetting, "Visibility", timeOut, "activityTimelineRecordPageSetting");
	}
	
	@FindBy(xpath = "//button[text()='Save']")
	private WebElement recordPageSettingSave;
	
	
	/**
	 * @return the recordPageSettingSave
	 */
	public WebElement getRecordPageSettingSave(int timeOut) {
		return isDisplayed(driver, recordPageSettingSave, "Visibility", timeOut, "recordPageSettingSave");
}

	@FindBy(xpath="//li[@data-aura-class='uiMenuItem onesetupSetupMenuItem']/a[@title='Setup']")
	private WebElement setupLink_Lighting;
	

	@FindBy(xpath = "//a[@title='Setup']")
	private WebElement userMenuSetupLink;

	/**
	 * @return the userMenuSetupLink
	 */
	public WebElement getUserMenuSetupLink(int timeOut) {
		WebElement ele=null;
			ele=setupLink_Lighting;
		return isDisplayed(driver, ele, "Visibility", timeOut, "Setup Link");
	}
	
	
	@FindBy(xpath="//li[@data-aura-class='uiMenuItem onesetupSetupMenuItem']/a[@title='Edit Page']")
	private WebElement editPageLink_Lighting;
	


	
	public WebElement getEditPageLink_Lighting(int timeOut) {
		return isDisplayed(driver, editPageLink_Lighting, "Visibility", timeOut, "edit page Link");
	}

	public WebElement getUserMenuSetupLink(String mode,int timeOut) {
		WebElement ele=null;
		if (mode.equalsIgnoreCase(Mode.Classic.toString()))
			ele=userMenuSetupLink;
		else
			ele=setupLink_Lighting;
		return isDisplayed(driver, ele, "Visibility", timeOut, "Setup Link");
	}
	
	
	
	@FindBy(xpath = "(//a[@title='Logout'])[1]")
	private WebElement logOutButton;

	/**
	 * @return the logOutButton
	 */
	public WebElement getLogOutButton(int timeOut) {
		return isDisplayed(driver, logOutButton, "Visibility", timeOut, "Logout Button");
	}
	
	
	@FindBy(xpath = "//a[@id='ManageUsers_font']")
	private WebElement userLink_Classic;
	
	@FindBy(xpath="//a[text()='Users'][contains(@href,'ManageUsers/home')]")
	private WebElement userLink_Lighting;
	/**
	 * @return the usersLink
	 */
	public WebElement getUsersLink(int timeOut) {
			return isDisplayed(driver, userLink_Lighting, "Visibility", timeOut, "Users Link in ");
	}
	
	@FindBy(xpath = "(//input[@title='New User'])[1]")
	private WebElement newUserLink;

	/**
	 * @return the newUserLink
	 */
	public WebElement getNewUserLink(int timeOut) {
		return isDisplayed(driver, newUserLink, "Visibility", timeOut, "New User Button");
	}
	
	@FindBy(id="name_firstName")
	private WebElement userFirstName;

	/**
	 * @return the userFirstName
	 */
	public WebElement getUserFirstName(int timeOut) {
		return isDisplayed(driver, userFirstName, "Visibility", timeOut, "User First Name");
	}
	
	@FindBy(id="name_lastName")
	private WebElement userLastName;

	/**
	 * @return the userLastName
	 */
	public WebElement getUserLastName(int timeOut) {
		return isDisplayed(driver, userLastName, "Visibility", timeOut, "User Last Name");
	}
	@FindBy(id="user_license_id")
	private WebElement userUserLicenseDropDownList;

	/**
	 * @return the userUserLicenseDropDownList
	 */
	public WebElement getUserUserLicenseDropDownList(int timeOut) {
		return isDisplayed(driver, userUserLicenseDropDownList, "Visibility", timeOut, "User License Drop Down List");
	}
	
	@FindBy(id="Profile")
	private WebElement userProfileDropDownList;

	/**
	 * @return the userProfileDropDownList
	 */
	public WebElement getUserProfileDropDownList(int timeOut) {
		return isDisplayed(driver, userProfileDropDownList, "Visibility", timeOut, "User Profile Drop Down List");
	}
	
	@FindBy(id="Email")
	private WebElement userEmailId;

	/**
	 * @return the userEmailId
	 */
	public WebElement getUserEmailId(int timeOut) {
		return isDisplayed(driver, userEmailId, "Visibility", timeOut, "User Email Id");
	}
	
	@FindBy(xpath="//td[text()='Email']/../td[2]/a")
	private WebElement userEmailIDLabeltext;

	/**
	 * @return the userEmailIDLabeltext
	 */
	public WebElement getUserEmailIDLabeltext(int timeOut) {
		return isDisplayed(driver, userEmailIDLabeltext, "Visibility", timeOut, "Email ID Label Text");
	}
	
	
	
	@FindBy(xpath = "//a[contains(@title,'Manage Licenses')]")
	private WebElement manageLicensesLink;

	/**
	 * @return the manageLicensesLink
	 */
	public WebElement getManageLicensesLink(int timeOut) {
		return isDisplayed(driver, manageLicensesLink, "Visibility", timeOut, "Manage Licenses Link");
	}
	
	@FindBy(xpath = "//input[@title='Add Users']")
	private WebElement addUsersbutton;

	/**
	 * @return the addUsersbutton
	 */
	public WebElement getAddUsersbutton(int timeOut) {
		return isDisplayed(driver, addUsersbutton, "Visibility", timeOut, "Add Users Button");
	}
	
	
	
	@FindBy(xpath="//div[@class='listRelatedObject userBlock']//form/div[2]/table//tr[1]//a[contains(@title,'Active')]")
	private WebElement ActiveUserTab;

	/**
	 * @return the activeUserTab
	 */
	public WebElement getActiveUserTab(int timeOut) {
		return isDisplayed(driver, ActiveUserTab, "Visibility", timeOut, "Active User Tab");
	}
	
	@FindBy(xpath="//input[@title='Add']")
	private WebElement activeUserAddButton;

	/**
	 * @return the activeUserAddButton
	 */
	public WebElement getActiveUserAddButton(int timeOut) {
		return isDisplayed(driver, activeUserAddButton, "Visibility", timeOut, "Active User Add Button");
	}
	@FindBy(xpath="//label[text()='Salesforce CRM Content User']/../..//input")
	private WebElement SalesforceCRMContentUserCheckBox;

	/**
	 * @return the salesforceCRMContentUserCheckBox
	 */
	public WebElement getSalesforceCRMContentUserCheckBox(int timeOut) {
		return isDisplayed(driver, SalesforceCRMContentUserCheckBox, "Visibility", timeOut, "Salesforce CRM Content User Check Box");
	}
	
	@FindBy(xpath = "//h2[contains(text(),'Change Your Password')]")
	private WebElement chnageYourPassword;

	/**
	 * @return the chnageYourPassword
	 */
	public WebElement getChnageYourPassword(int timeOut) {
		return isDisplayed(driver, chnageYourPassword, "Visibility", timeOut, "Change password Text label");
	}
	
	@FindBy(id = "newpassword")
	private WebElement newPassword;

	/**
	 * @return the newPassword
	 */
	public WebElement getNewPassword(int timeOut) {
		return isDisplayed(driver, newPassword,"Visibility", timeOut, "New password Text Box");
	}
	
	@FindBy(id = "confirmpassword")
	private WebElement confimpassword;

	/**
	 * @return the confimpassword
	 */
	public WebElement getConfimpassword(int timeOut) {
		return isDisplayed(driver, confimpassword, "Visibility", timeOut, "Confirm password Text Box");
	}

	@FindBy(id = "question")
	private WebElement question;

	/**
	 * @return the question
	 */
	public WebElement getQuestion(int timeOut) {
		return isDisplayed(driver, question, "Visibility", timeOut, "Question drop Doown List");
	}

	@FindBy(id = "answer")
	private WebElement answer;

	/**
	 * @return the answer
	 */
	public WebElement getAnswer(int timeOut) {
		return isDisplayed(driver, answer, "Visibility", timeOut, "Answer Text Box");
	}

	@FindBy(xpath = "//button[contains(text(),' Change Password')]")
	private WebElement changePassword;

	/**
	 * @return the changePassword
	 */
	public WebElement getChangePassword(int timeOut) {
		return isDisplayed(driver, changePassword, "Visibility", timeOut, "Change Password Button");
	}

	@FindBy(xpath="//li[@id='AllTab_Tab']/a")
	private WebElement allTabBtn;

	/**
	 * @return the allTabBtn
	 */
	public WebElement getAllTabBtn(int timeOut) {
		return isDisplayed(driver, allTabBtn, "Visibility", timeOut, "All Tab Button");
	}
	
	@FindBy(xpath="//div[@class='bDescription']/a")
	private WebElement addTabLink;

	/**
	 * @return the addTabLink
	 */
	public WebElement getAddTabLink(int timeOut) {
		return isDisplayed(driver, addTabLink, "Visibility", timeOut, "Add a Tab Link");
	}
	@FindBy(xpath="//tr[@ class='last detailRow']//table//td[1]/select")
	private WebElement availableTabList;

	/**
	 * @return the availableTabList
	 */
	public WebElement getAvailableTabList(int timeOut) {
		return isDisplayed(driver, availableTabList, "Visibility", timeOut, "Available Tab Drop Down List");
	}
	
	@FindBy(xpath="//tr[@ class='last detailRow']//td[2]/div[2]/a")
	private WebElement customTabAddBtn;

	/**
	 * @return the customTabAddBtn
	 */
	public WebElement getCustomTabAddBtn(int timeOut) {
		return isDisplayed(driver, customTabAddBtn, "Visibility", timeOut, "Custom Tab Add Button");
	}
	@FindBy(xpath="//a//img[@title='Add']")
	private WebElement AddBtn;

	/**
	 * @return the customTabAddBtn
	 */
	public WebElement getAddBtn(int timeOut) {
		return isDisplayed(driver, AddBtn, "Visibility", timeOut, "Custom Tab Add Button");
	}
	@FindBy(xpath = "//label[text()='Available Fields']/../following-sibling::select")
	private WebElement availableFieldsLayout;
	
	public WebElement getavailableFieldsLayout(int timeOut) {
		return isDisplayed(driver, availableFieldsLayout, "Visibility", timeOut, "available Fields on Layout");
	}
	
	@FindBy(xpath = "//img[@title='Add']")
	private WebElement addButtonForFieldsLayout;
	
	public WebElement getaddButtonForFieldsLayout(int timeOut) {
		return isDisplayed(driver, addButtonForFieldsLayout, "Visibility", timeOut, "add button Fields on Layout");
	}
	@FindBy(xpath="//div[@id='ep']/div[3]//input[@name='save']")
	private WebElement customTabSaveBtn;

	/**
	 * @return the customTabSaveBtn
	 */
	public WebElement getCustomTabSaveBtn(int timeOut) {
		return isDisplayed(driver, customTabSaveBtn, "Visibility", timeOut, "Custom Tab Save Button");
	}
	
	public WebElement getCustomTabSaveBtn(String projectName,int timeOut) {
		List<WebElement> eleList = FindElements(driver, "//*[@title='Save' or text()='Save']", "Save Button");
		for (WebElement webElement : eleList) {
		webElement=isDisplayed(driver, webElement, "Visibility", 2, "Custom Tab Save Button lightning");
		if (webElement!=null) {
				return webElement;
			} else {

			}
		}
		return isDisplayed(driver, save_Lightning, "Visibility", timeOut, "Custom Tab Save Button lightning");

	}
	
	@FindBy(xpath = "//button[text()='Compact Layout Assignment']")
	private WebElement compactLayoutAssignmentLight;
	
	public WebElement getcompactLayoutAssignmentLight(String projectName,int timeOut) {
		return isDisplayed(driver, compactLayoutAssignmentLight, "Visibility", timeOut, "compactLayoutAssignmentLightning button");
		
	}
	
	@FindBy(xpath = "//input[@title='Edit Assignment']")
	private WebElement editAssignment;
	
	public WebElement geteditAssignment(String projectName,int timeOut) {
		return isDisplayed(driver, editAssignment, "Visibility", timeOut, "editAssignment");
		
	}@FindBy(xpath = "//button[text()='Page Layout Assignment']")
	private WebElement pageLayoutAssignment;
	
	public WebElement getpageLayoutAssignment(String projectName,int timeOut) {
		return isDisplayed(driver, pageLayoutAssignment, "Visibility", timeOut, "editAssignment");
		
	}
	
	@FindBy(xpath = "//select[@id='defaultCompactLayoutSelector']")
	private WebElement defaultCompactLayout;
	
	public WebElement getdefaultCompactLayoutDropdown(String projectName,int timeOut) {
		return isDisplayed(driver, defaultCompactLayout, "Visibility", timeOut, "defaultCompactLayout");
		
	}
	@FindBy(xpath = "//*[@title='Save' or text()='Save']")
	private WebElement save_Lightning;
	
	@FindBy(xpath = "//input[@placeholder='Search all items...']")
	private WebElement searchTabTextbox;
	public WebElement getsearchTabTextbox(int timeOut) {
		return isDisplayed(driver, searchTabTextbox, "Visibility", timeOut, "search Tab Textbox");
		
	}
	
	@FindBy(xpath="//tr[@ class='last detailRow']//table//td[3]/select")
	private WebElement customTabSelectedList;

	/**
	 * @return the customTabSelectedList
	 */
	public WebElement getCustomTabSelectedList(int timeOut) {
		return isDisplayed(driver, customTabSelectedList, "Visibility", timeOut, "Custom Tab Selected List");
	}
	@FindBy(xpath="//tr[@ class='last detailRow']//td[2]/div[3]/a")
	private WebElement customTabRemoveBtn;

	/**
	 * @return the customTabRemoveBtn
	 */
	public WebElement getCustomTabRemoveBtn(int timeOut) {
		return isDisplayed(driver, customTabRemoveBtn, "Visibility", timeOut, "Custom Tab Remove Button");
	}
	
	@FindBy(xpath="//a[@title='Navatar Investor Manager Tab']")
	private WebElement navatarInvestorManagerTab;

	/**
	 * @return the navatarInvestorManagerTab
	 */
	public WebElement getNavatarInvestorManagerTab(int timeOut) {
		return isDisplayed(driver, navatarInvestorManagerTab, "Visibility", timeOut, "Navatar Investor Manager Tab");
	}

	@FindBy(xpath="(//div[contains(@class,'uiTooltip')])[7]")
	private WebElement salesForceLightingIcon;

	/**
	 * @return the salesForceLightingIcon
	 */
	public WebElement getSalesForceLightingIcon(int timeOut) {
		return isDisplayed(driver, salesForceLightingIcon, "Visibility", timeOut, "Sales Force Lighting Icon");
	}
	@FindBy(xpath="//div[contains(@class,'userProfilePanel')]//a[contains(@class,'switch-to-aloha')]")
	private WebElement switchToClassic;

	/**
	 * @return the switchToClassic
	 */
	public WebElement getSwitchToClassic(int timeOut) {
		return isDisplayed(driver, switchToClassic, "Visibility", timeOut, "Sales force switch to classic mode");
	}
	
	@FindBy(xpath="//img[@name='Workspace']")
	private WebElement workspaceExpandIcon;

	/**
	 * @return the workspaceExpandIcon
	 */
	public WebElement getWorkspaceExpandIcon(int timeOut) {
		return isDisplayed(driver, workspaceExpandIcon, "Visibility", timeOut, "Workspace Expand Icon");
	}
	
	@FindBys(@FindBy(xpath="//span[@id='setupErrorFR']"))
	private List<WebElement>  getErrorMessageOnAllPages;

	/**
	 * @return the errorMessageBeforeAdminRegistrationOnAllPages
	 */
	
	public List<WebElement> getErrorMessageOnAllPages(int timeOut) {
		if(checkElementsVisibility(driver, getErrorMessageOnAllPages, "Error Messages", timeOut)){
			return getErrorMessageOnAllPages;
		} else {
			return null;
		}
	}
	
	public WebElement getFolderTypeRadioButton(FolderType folderType, Workspace workspace,PageName pageName, int timeOut){
		String workspaceSelector = "";
		if(folderType.toString().equalsIgnoreCase(FolderType.Common.toString())&&pageName.toString().equalsIgnoreCase(PageName.NavatarInvestorManager.toString())){
			folderType=FolderType.Global;
		}
		String xpath = "//input[@id='chk"+folderType.toString()+"Folder']";
		if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector = "BWFR";
			} else {
				workspaceSelector = "BWINV";
			}
			xpath = "//input[@id='chk"+folderType.toString()+"Folder"+workspaceSelector+"']";
		} else if (pageName.toString().equalsIgnoreCase(PageName.ManageFolderPopUp.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector = "fundraising";
			} else {
				workspaceSelector = "investor";
			}
			xpath = "//input[@id='checkbox"+folderType.toString().toLowerCase()+"_grid1"+workspaceSelector+"']";
		}
		return isDisplayed(driver, FindElement(driver, xpath, folderType.toString()+" Folder Radio button", action.BOOLEAN, timeOut), "Visibility", timeOut, folderType.toString()+" Folder Radio button");
	}
	
	public WebElement getParentFolderNameTextBox(Workspace workspace,PageName pageName, int timeOut){
		String workspaceSelector = "";
		String xpath = "//input[@id='txtParentFolderName']";
		if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector = "BWFR";
			} else {
				workspaceSelector = "BWINV";
			}
			xpath = "//input[@id='txtParentFolderName"+workspaceSelector+"']";
		} else if (pageName.toString().equalsIgnoreCase(PageName.ManageFolderPopUp.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector = "fundraising";
			} else {
				workspaceSelector = "investor";
			}
			xpath = "//input[@id='foldecommonaddfund"+workspaceSelector+"']";
		}
		return isDisplayed(driver, FindElement(driver, xpath, "Parent Folder Name Text Box", action.BOOLEAN, timeOut), "Visibility", timeOut, "Parent Folder Name Text Box");
	}
	
	public WebElement getParentFolderSaveButton(Workspace workspace, PageName pageName, int timeOut){
		String workspaceSelector = "";
		String xpath = "//a[contains(@onclick,'checkAndCreateFolder')][contains(@onclick,'txtParentFolderName')]";
		if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector = "BWFR";
			} else {
				workspaceSelector = "BWINV";
			}
			xpath = "//a[contains(@onclick,'checkAndCreateFolder"+workspaceSelector+"')][contains(@onclick,'txtParentFolderName"+workspaceSelector+"')]";
		} else if (pageName.toString().equalsIgnoreCase(PageName.ManageFolderPopUp.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector = "fundraising";
			} else {
				workspaceSelector = "investor";
			}
			xpath = "//a[contains(@onclick,'CreateCommon_pop1"+workspaceSelector+"();')]";
		}
		return isDisplayed(driver, FindElement(driver, xpath, "Parent Folder Save Button", action.BOOLEAN, timeOut), "Visibility", timeOut, "Parent Folder Save Button");
	}
	
	public WebElement getChildFolderNameTextBox(Workspace workspace, PageName pageName, int timeOut){
		String workspaceSelector = "";
		String xpath = "//input[@id='txtChildFolderName']";
		if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector = "BWFR";
			} else {
				workspaceSelector = "BWINV";
			}
			xpath = "//input[@id='txtChildFolderName"+workspaceSelector+"']";
		} else if (pageName.toString().equalsIgnoreCase(PageName.ManageFolderPopUp.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector = "fundraising";
			} else {
				workspaceSelector = "investor";
			}
			xpath = "//input[@id='foldernameaddfundr2"+workspaceSelector+"']";
		}
		return isDisplayed(driver, FindElement(driver, xpath, "Child Folder Name Text Box", action.BOOLEAN, timeOut), "Visibility", timeOut, "child Folder Name Text Box");
	}
	
	public WebElement getParentRenameFolderNameTextBox(Workspace workspace, PageName pageName, int timeOut){
		String workspaceSelector = "";
		String xpath = "";
		if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString()) || pageName.toString().equalsIgnoreCase(PageName.BuildStep2Of3.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector = "FR";
			} else {
				workspaceSelector = "INV";
			}
			xpath = "//input[@id='txtRenameFolderBW"+workspaceSelector+"']";
		} else if (pageName.toString().equalsIgnoreCase(PageName.ManageFolderPopUp.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector = "fundraising";
			} else {
				workspaceSelector = "investor";
			}
			xpath = "//input[@id='renamefol_pop1"+workspaceSelector+"']";
		}
		return isDisplayed(driver, FindElement(driver, xpath, "parent rename Folder Name Text Box", action.BOOLEAN, timeOut), "Visibility", timeOut, "parent rename Folder Name Text Box");
	}
	
	public WebElement getChildRenameFolderNameTextBox(Workspace workspace, PageName pageName, int timeOut){
		String workspaceSelector = "";
		String xpath = "";
		if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString())){
			//code needs to be write
		} else if (pageName.toString().equalsIgnoreCase(PageName.ManageFolderPopUp.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector = "fundraising";
			} else {
				workspaceSelector = "investor";
			}
			xpath = "//input[@id='renamefol_pop1"+workspaceSelector+"']";
		}
		return isDisplayed(driver, FindElement(driver, xpath, "parent rename Folder Name Text Box", action.BOOLEAN, timeOut), "Visibility", timeOut, "parent rename Folder Name Text Box");
	}
	
	public WebElement getCreatedFolderWebelement(String folderPath,PageName pageName,int timeOut) {
		WebElement ele = null;
		String folderStruct[] = folderPath.split("/");
		if(folderStruct.length==1) {
			ele = isDisplayed(driver, FindElement(driver,
					"//span[contains(text(),'All Folders')]/span/../../../following-sibling::ul/li/div//label[contains(text(),'"
							+ folderStruct[0] + "')]",
							" created folder", action.SCROLLANDBOOLEAN, timeOut), "visibility", timeOut, "created folder");
		}else {
			ele = isDisplayed(driver, FindElement(driver,
					"//span[contains(text(),'All Folders')]/span/../../../following-sibling::ul/li/div//label[contains(text(),'"
							+ folderStruct[0] + "')]/../../../following-sibling::ul/li/div//label[contains(text(),'"
							+ folderStruct[1] + "')]",
							"created sub folder", action.SCROLLANDBOOLEAN, timeOut), "visibility", timeOut, "created sub folder");
		}
		return ele;
	}
	
	
	
	
	public WebElement getChildFolderSaveButton(Workspace workspace, PageName pageName, int timeOut){
		String workspaceSelector = "";
		String xpath = "(//a[contains(@onclick,'checkAndCreateFolder')])[2]";
		if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector = "BWFR";
			} else {
				workspaceSelector = "BWINV";
			}
			xpath = "//a[contains(@onclick,'checkAndCreateFolder"+workspaceSelector+"')][contains(@id,'btnSubFolderSave')]";
		} else if (pageName.toString().equalsIgnoreCase(PageName.ManageFolderPopUp.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector = "fundraising";
			} else {
				workspaceSelector = "investor";
			}
			xpath = "//a[contains(@onclick,'CreateFolder_pop1"+workspaceSelector+"();')]";
		}
		return isDisplayed(driver, FindElement(driver, xpath, "Parent Folder Save Button", action.BOOLEAN, timeOut), "Visibility", timeOut, "Parent Folder Save Button");
	}
	
	public WebElement getParentFolderErrorMsg(Workspace workspace, PageName pageName, ErrorMessageType ErrorMessageType, int timeOut){
		String workspaceSelector = "";
		String xpath = "";
		if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString())){
			// code need to be write for fund page
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector = "FR";
			} else {
				workspaceSelector = "INV";
			}
			if(ErrorMessageType.toString().equalsIgnoreCase(ErrorMessageType.BlankErrorMsg.toString())) {
				xpath = "//span[@id='err1stLvlFolderBW"+workspaceSelector+"']";
			}else if (ErrorMessageType.toString().equalsIgnoreCase(ErrorMessageType.PrefixErrorMsg.toString())) {
				xpath = "//span[@id='err1stLvlFolderForCommonBW"+workspaceSelector+"']";
			}else if (ErrorMessageType.toString().equalsIgnoreCase(ErrorMessageType.SpiecalCharErrorMsg.toString())) {
				xpath = "//span[@id='errforInvalidCharFirstFundBW"+workspaceSelector+"']";
			}else if (ErrorMessageType.toString().equalsIgnoreCase(ErrorMessageType.FolderCreationRestrictionErrorMsg.toString())) {
				xpath="//div[@id='errMsgSecondCommonFolderBW"+ workspaceSelector +"']";
			} else {
				xpath = "//div[@id='errMsgDupFolderBW"+ workspaceSelector +"']";
			}
		} else if (pageName.toString().equalsIgnoreCase(PageName.ManageFolderPopUp.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
					workspaceSelector = "fundraising";
			} else {
				workspaceSelector = "investor";
			}
			if(ErrorMessageType.toString().equalsIgnoreCase(ErrorMessageType.BlankErrorMsg.toString())) {
				xpath = "//span[@id='errorfoldecommonaddfund"+workspaceSelector+"']";
			}else if (ErrorMessageType.toString().equalsIgnoreCase(ErrorMessageType.PrefixErrorMsg.toString())) {
				xpath = "//span[@id='errorfoldecommonaddfund22"+workspaceSelector+"']";
			}else if (ErrorMessageType.toString().equalsIgnoreCase(ErrorMessageType.SpiecalCharErrorMsg.toString())) {
				xpath = "//span[@id='errforInvalidCharFirst1"+workspaceSelector+"']";
			}else if (ErrorMessageType.toString().equalsIgnoreCase(ErrorMessageType.FolderCreationRestrictionErrorMsg.toString())) {
				xpath="//div[@id='CommonError1investor']//p";
			}
		}
		return isDisplayed(driver, FindElement(driver, xpath, "Folder Error Message", action.BOOLEAN, timeOut), "Visibility", timeOut, "Folder Error Message");
	}
	
	public WebElement getSubFolderErrorMsg(Workspace workspace, PageName pageName, ErrorMessageType ErrorMessageType, int timeOut){
		String workspaceSelector = "";
		String xpath = "";
		if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString())){
			// code need to be write for fund page
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector = "FR";
			} else {
				workspaceSelector = "INV";
			}
			if(ErrorMessageType.toString().equalsIgnoreCase(ErrorMessageType.BlankErrorMsg.toString())) {
				xpath = "//span[@id='errSubFolderNameBW"+workspaceSelector+"']";
			}else if (ErrorMessageType.toString().equalsIgnoreCase(ErrorMessageType.PrefixErrorMsg.toString())) {
				xpath = "//span[@id='errSubFolderNameforCommonBW"+workspaceSelector+"']";
			}else if (ErrorMessageType.toString().equalsIgnoreCase(ErrorMessageType.SpiecalCharErrorMsg.toString())) {
				xpath = "//span[@id='errforInvalidCharSubFundBW"+workspaceSelector+"']";
			} else {
				xpath="//span[@id='errDupSubFolderNameBW" + workspaceSelector + "']";
			}
		} else if (pageName.toString().equalsIgnoreCase(PageName.ManageFolderPopUp.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
					workspaceSelector = "fundraising";
			} else {
				workspaceSelector = "investor";
			}
			if(ErrorMessageType.toString().equalsIgnoreCase(ErrorMessageType.BlankErrorMsg.toString())) {
				xpath = "//span[@id='errorfoldernameaddfundr2"+workspaceSelector+"']";
			}else if (ErrorMessageType.toString().equalsIgnoreCase(ErrorMessageType.PrefixErrorMsg.toString())) {
				xpath = "//span[@id='errorfoldernameaddfundr2"+workspaceSelector+"22']";
			}else if (ErrorMessageType.toString().equalsIgnoreCase(ErrorMessageType.SpiecalCharErrorMsg.toString())) {
				xpath = "//span[@id='addsubInvalid1"+workspaceSelector+"']";
			}
		}
		return isDisplayed(driver, FindElement(driver, xpath, "Sub Folder Error Message", action.BOOLEAN, timeOut), "Visibility", timeOut, "Sub Folder Error Message");
	}
	
	
	public WebElement getParentRenameFolderErrorMsg(Workspace workspace, PageName pageName, ErrorMessageType ErrorMessageType, int timeOut){
		String workspaceSelector = "";
		String xpath = "";
		if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString()) || pageName.toString().equalsIgnoreCase(PageName.BuildStep2Of3.toString())){
			// code need to be write for fund page
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector = "FR";
			} else {
				workspaceSelector = "INV";
			}
			if(ErrorMessageType.toString().equalsIgnoreCase(ErrorMessageType.BlankErrorMsg.toString())) {
				xpath = "//span[@id='errRenFolderNameBW"+workspaceSelector+"']";
			}else if (ErrorMessageType.toString().equalsIgnoreCase(ErrorMessageType.PrefixErrorMsg.toString())) {
//				xpath = "//span[@id='errRenFolderNameForCommonBW"+workspaceSelector+"11']";
				xpath = "//span[@id='errRenFolderNameForCommonBW"+workspaceSelector+"']";
			}else if (ErrorMessageType.toString().equalsIgnoreCase(ErrorMessageType.SpiecalCharErrorMsg.toString())) {
				xpath = "//span[@id='errforCharRenameBW"+workspaceSelector+"']";
			}
		} else if (pageName.toString().equalsIgnoreCase(PageName.ManageFolderPopUp.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
					workspaceSelector = "fundraising";
			} else {
				workspaceSelector = "investor";
			}
			if(ErrorMessageType.toString().equalsIgnoreCase(ErrorMessageType.BlankErrorMsg.toString())) {
				xpath = "//span[@id='errorrenamefol_pop1"+workspaceSelector+"']";
			}else if (ErrorMessageType.toString().equalsIgnoreCase(ErrorMessageType.PrefixErrorMsg.toString())) {
//				xpath = "//span[@id='errorrenamefol_pop1"+workspaceSelector+"11']";
				xpath = "//span[@id='errorrenamefol_pop1"+workspaceSelector+"']";
			}else if (ErrorMessageType.toString().equalsIgnoreCase(ErrorMessageType.SpiecalCharErrorMsg.toString())) {
				xpath = "//span[@id='renameInvalid1"+workspaceSelector+"']";
			}
		}
		return isDisplayed(driver, FindElement(driver, xpath, "Rename Folder Error Message", action.BOOLEAN, timeOut), "Visibility", timeOut, "Rename Folder Error Message");
	}
	
	public WebElement getParentRenameFolderSaveButton(Workspace workspace, PageName pageName, int timeOut){
		String workspaceSelector = "";
		String xpath = "(//a[contains(@onclick,'checkAndCreateFolder')])[2]";
		if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString()) || pageName.toString().equalsIgnoreCase(PageName.BuildStep2Of3.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector = "FR";
			} else {
				workspaceSelector = "INV";
			}
			if(pageName.toString().equalsIgnoreCase(PageName.BuildStep2Of3.toString())){
				xpath = "//a[contains(@onclick,'saveRenameFolderBW"+workspaceSelector+"()')]";
			} else {
				xpath = "//a[contains(@onclick,'saveRenameFolderBW"+workspaceSelector+"();')]";
			}
		} else if (pageName.toString().equalsIgnoreCase(PageName.ManageFolderPopUp.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector = "fundraising";
			} else {
				workspaceSelector = "investor";
			}
			xpath = "//a[contains(@onclick,'Save_Rename_folder_pop1"+workspaceSelector+"();')]";
		}
		return isDisplayed(driver, FindElement(driver, xpath, "Parent Rename Folder Save Button", action.BOOLEAN, timeOut), "Visibility", timeOut, "Parent Rename Folder Save Button");
	}
	
	public WebElement getParentRenameFolderCrossIcon(Workspace workspace, PageName pageName, int timeOut){
		String workspaceSelector = "";
		String xpath = "";
		if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString())){
			//needs to be write
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector = "FR";
			} else {
				workspaceSelector = "INV";
			}
//			xpath = "//span[contains(@onclick,'clearTextBW"+workspaceSelector+"(') and contains(@onclick,'rename')  and contains(@onclick,';')]";
			xpath = "//a[contains(@onclick,'clearTextBW"+workspaceSelector+"(') and contains(@onclick,'rename')  and contains(@onclick,';')]";
		} else if (pageName.toString().equalsIgnoreCase(PageName.ManageFolderPopUp.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector = "fundraising";
			} else {
				workspaceSelector = "investor";
			}
			xpath = "//span[contains(@onclick,'closeRenamepop1"+workspaceSelector+"()')]";
		}
		return isDisplayed(driver, FindElement(driver, xpath, "Parent Rename Folder Cross Icon", action.BOOLEAN, timeOut), "Visibility", timeOut, "Parent Rename Folder Cross Icon");
	}
	
	public WebElement getParentRenameFolderCancelButton(Workspace workspace, PageName pageName, int timeOut){
		String workspaceSelector = "";
		String xpath = "";
		if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString())){
			//needs to be write
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector = "FR";
			} else {
				workspaceSelector = "INV";
			}
			xpath = "//a[contains(@onclick,'clearTextBW"+workspaceSelector+"(')  and contains(@onclick,'rename')  and contains(@title,'Cancel') ]";
		} else if (pageName.toString().equalsIgnoreCase(PageName.ManageFolderPopUp.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector = "fundraising";
			} else {
				workspaceSelector = "investor";
			}
			xpath = "//a[contains(@onclick,'closeRenamepop1"+workspaceSelector+"();')]";
		}
		return isDisplayed(driver, FindElement(driver, xpath, "Parent Rename Folder cancel button", action.BOOLEAN, timeOut), "Visibility", timeOut, "Parent Rename Folder Cancel button");
	}
	
	
	public WebElement getFolderErrorMsg(Workspace workspace,PageName pageName,ErrorMessageType ErrorMessageType,FolderType Foldertype,int timeOut) {
		String workspaceSelector = "";
		String xpath = "";
		if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString())){
			// code need to be write for fund page
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				if(Foldertype.toString().equalsIgnoreCase(FolderType.Common.toString())) {
					xpath = "//div[@id='errMsgSecondCommonFolderBWFR']";
				}else {
					xpath = "//div[@id='errMsgSecondInternalFolderBWFR']";
				}
					
			} else {
				if(Foldertype.toString().equalsIgnoreCase(FolderType.Common.toString())) {
					xpath = "//div[@id='errMsgSecondCommonFolderBWINV']";
				}else {
					xpath = "//div[@id='errMsgSecondInternalFolderBWINV']";
				}
			}
		} else if (pageName.toString().equalsIgnoreCase(PageName.ManageFolderPopUp.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				if(Foldertype.toString().equalsIgnoreCase(FolderType.Common.toString())) {
					workspaceSelector = "1fundraising";
				}else {
					workspaceSelector = "2fundraising";
				}
					
			} else {
				if(Foldertype.toString().equalsIgnoreCase(FolderType.Common.toString())) {
					workspaceSelector = "1investor";
				}else {
					workspaceSelector = "2investor";
				}
			}
			xpath="//div[@id='CommonError"+workspaceSelector+"']//p";
		}
		return isDisplayed(driver, FindElement(driver, xpath, "Folder Error Message", action.BOOLEAN, timeOut), "Visibility", timeOut, "Folder Error Message");
	}
	
	public WebElement getFolderCreationErrorMessageCrossIcon(Workspace workspace,PageName pageName,FolderType Foldertype,int timeOut) {
		String workspaceSelector = "";
		String xpath = "";
		if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				if(Foldertype.toString().equalsIgnoreCase(FolderType.Common.toString())) {
					xpath = "//div[@id='errMsgSecondCommonFolderBWFR']/../preceding-sibling::div[1]/a";
				}else {
					xpath = "//div[@id='errMsgSecondInternalFolderBWFR']/../preceding-sibling::div[1]/a";
				}
			} else {
				if(Foldertype.toString().equalsIgnoreCase(FolderType.Common.toString())) {
					xpath = "//div[@id='errMsgSecondCommonFolderBWINV']/../preceding-sibling::div[1]/a";
				}else {
					xpath = "//div[@id='errMsgSecondInternalFolderBWINV']/../preceding-sibling::div[1]/a";
				}
			}
		} else if (pageName.toString().equalsIgnoreCase(PageName.ManageFolderPopUp.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				if(Foldertype.toString().equalsIgnoreCase(FolderType.Common.toString())) {
					workspaceSelector = "1fundraising";
				}else {
					workspaceSelector = "2fundraising";
				}
					
			} else {
				if(Foldertype.toString().equalsIgnoreCase(FolderType.Common.toString())) {
					workspaceSelector = "1investor";
				}else {
					workspaceSelector = "2investor";
				}
			}
			xpath="(//div[@id='CommonError"+workspaceSelector+"']//a)[1]";
		}
		return isDisplayed(driver, FindElement(driver, xpath, "folder creation error message cross icon on parent level", action.BOOLEAN, timeOut), "Visibility", timeOut, "folder creation error message cross icon on parent level");
	}
	
	public WebElement getFolderCreationErrorMessageOkBtn(Workspace workspace,PageName pageName,FolderType Foldertype,int timeOut) {
		String workspaceSelector = "";
		String xpath = "";
		if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString())){
			// code need to be write for fund page
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				if(Foldertype.toString().equalsIgnoreCase(FolderType.Common.toString())) {
					xpath = "//div[@id='errMsgSecondCommonFolderBWFR']/../following-sibling::div[1]/a";
				}else {
					xpath = "//div[@id='errMsgSecondInternalFolderBWFR']/../following-sibling::div[1]/a";
				}
			} else {
				if(Foldertype.toString().equalsIgnoreCase(FolderType.Common.toString())) {
					xpath = "//div[@id='errMsgSecondCommonFolderBWINV']/../following-sibling::div[1]/a";
				}else {
					xpath = "//div[@id='errMsgSecondInternalFolderBWINV']/../following-sibling::div[1]/a";
				}
			}
		} else if (pageName.toString().equalsIgnoreCase(PageName.ManageFolderPopUp.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				if(Foldertype.toString().equalsIgnoreCase(FolderType.Common.toString())) {
					workspaceSelector = "1fundraising";
				}else {
					workspaceSelector = "2fundraising";
				}
					
			} else {
				if(Foldertype.toString().equalsIgnoreCase(FolderType.Common.toString())) {
					workspaceSelector = "1investor";
				}else {
					workspaceSelector = "2investor";
				}
			}
			xpath="(//div[@id='CommonError"+workspaceSelector+"']//a)[2]";
		}
		return isDisplayed(driver, FindElement(driver, xpath, "folder creation error message OK button on parent level", action.BOOLEAN, timeOut), "Visibility", timeOut, "folder creation error message Ok button on parent level");
	}
	
	public WebElement getFolderDeleteYesBtn(Workspace workspace, PageName pageName, int timeOut) {
		String workspaceSelector="";
		if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector = "FR";
			} else {
				workspaceSelector = "INV";
			}
			return isDisplayed(driver, FindElement(driver, "//a[@onclick='deleteFolderBW"+ workspaceSelector +"();']", "delete yes button", action.SCROLLANDBOOLEAN, timeOut),"visibility", timeOut,"delete yes button");
		} else {
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())) {
				workspaceSelector="fundraising";
			}else {
				workspaceSelector="investor";
			}
			return isDisplayed(driver, FindElement(driver, "//div[@id='deletefolderpopup1"+workspaceSelector+"']//a[text()='Yes']", "delete yes button", action.SCROLLANDBOOLEAN, timeOut),"visibility", timeOut,"delete yes button");
		}
	}
	
	public WebElement getFolderDeleteNoBtn(Workspace workspace, PageName pageName, int timeOut) {
		String workspaceSelector="";
		if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())) {
				workspaceSelector="FR";
			}else {
				workspaceSelector="INV";
			}
			return isDisplayed(driver, FindElement(driver, "//a[contains(@onclick,'idConfirmDeletionBW"+ workspaceSelector +"')]", "folder delete No button", action.SCROLLANDBOOLEAN, timeOut),"visibility", timeOut,"folder delete No button");
		} else {
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())) {
				workspaceSelector="fundraising";
			}else {
				workspaceSelector="investor";
			}
			return isDisplayed(driver, FindElement(driver, "//a[contains(@onclick,'closeaddpop1"+workspaceSelector+"();')]", "folder delete No button", action.SCROLLANDBOOLEAN, timeOut),"visibility", timeOut,"folder delete No button");
		}
	}
	
	public WebElement getFolderDeleteCrossIcon(Workspace workspace, PageName pageName, int timeOut) {
		String workspaceSelector="";
		if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())) {
				workspaceSelector="FR";
			}else {
				workspaceSelector="INV";
			}
			return isDisplayed(driver, FindElement(driver, "//span[contains(@onclick,'idConfirmDeletionBW"+ workspaceSelector +"')]", "folder delete Cross Icon", action.SCROLLANDBOOLEAN, timeOut),"visibility", timeOut,"folder delete Cross Icon");
		} else {
			
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())) {
				workspaceSelector="fundraising";
			}else {
				workspaceSelector="investor";
			}
			return isDisplayed(driver, FindElement(driver, "//a[contains(@onclick,'closeaddpop1"+workspaceSelector+"();')]", "folder delete Cross Icon", action.SCROLLANDBOOLEAN, timeOut),"visibility", timeOut,"folder delete Cross Icon");
		}
	}
	
	public WebElement getFolderDeleteErrorMsg1(Workspace workspace, PageName pageName, int timeOut) {
		String workspaceSelector="";
		if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())) {
				workspaceSelector="FR";
			}else {
				workspaceSelector="INV";
			}
			return isDisplayed(driver, FindElement(driver, "//span[contains(@onclick,'idConfirmDeletionBW"+ workspaceSelector +"')]/../following-sibling::p", "folder delete Error Message1", action.SCROLLANDBOOLEAN, timeOut),"visibility", timeOut,"folder delete Error Message1");
		} else {
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())) {
				workspaceSelector="fundraising";
			}else {
				workspaceSelector="investor";
			}
			return isDisplayed(driver, FindElement(driver, "//div[@id='deletefolderpopup1"+workspaceSelector+"content']/p[1]", "folder delete Error Message1", action.SCROLLANDBOOLEAN, timeOut),"visibility", timeOut,"folder delete Error Message1");
		}
	}
	

	public WebElement getFolderDeleteErrorMsg2(Workspace workspace,int timeOut) {
		String workspaceSelector="";
		if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())) {
			workspaceSelector="fundraising";
		}else {
			workspaceSelector="investor";
		}
		return isDisplayed(driver, FindElement(driver, "//div[@id='deletefolderpopup1"+workspaceSelector+"content']/p[2]", "folder delete Error Message2", action.SCROLLANDBOOLEAN, timeOut),"visibility", timeOut,"folder delete Error Message2");
	}
	
	 @FindBy(xpath="//iframe[contains(@title,'PE_Fund_NothingEnabled')]")
	 private WebElement fundPageFrame;
	 
	 @FindBy(xpath="//iframe[@title='Investor_Portal_Institution_Enabled']")
	 private WebElement institutionPageFrame;
	 
	 @FindBy(xpath="//iframe[@title='Investor_Portal_Contact_Enabled']")
	 private WebElement contactPageFrame;
	
	 @FindBy(xpath="//iframe[contains(@title,'Investor_Portal_Commitment_Enabled')]")
	 private WebElement commitmentPageFrame;
	 @FindBy(xpath="//iframe[contains(@title,'Task Compact Layout')]")
	 private WebElement compactLayoutFrame;
	 
//	 @FindBy(xpath="//iframe[@id='PEDisclaimersSetup']")
//	 private WebElement navatarInvestorAddOnFrame;

	 @FindBy(xpath="//iframe[@title='InvestorPortal_Alerts']")
	 private  WebElement homePageAlertsFrame;


	 @FindBy(xpath="//iframe[@id='theIframe']")
	 private WebElement NIMTabFrame;

	 @FindBy(xpath="//iframe[@id='NavatarInvestorAddOns']")
	private WebElement navatarInvestorAddOnFrame;
	 
	 
	 @FindBy(xpath = "//iframe[@title='TaskRay']")
		private WebElement taskRayPageFrame;

	 
	 @FindBy(xpath="//iframe[@title='TaskRay']")
	 private WebElement taskRayTabFrame;
	 
	 @FindBy(xpath="//iframe[@id='project-details-frame']")
		private WebElement projectDetailsPopUpFrame;
		 
	 @FindBy(xpath = "//iframe[@title='accessibility title']")
	 private WebElement taskPageFrame;
	 
	 @FindBy(xpath="//iframe[contains(@title,'Activity Custom Field')]")
	 private WebElement activityLayoutFrame;
	 @FindBy(xpath="//iframe[contains(@title,'Account Custom Field')]")
	 private WebElement accountLayoutFrame;
	 @FindBy(xpath="//iframe[contains(@title,'Salesforce - Enterprise Edition')]")
	 private WebElement pipelineLayoutFrame;
	 @FindBy(xpath="//iframe[contains(@title,'Convert to Portfolio')]")
	 private WebElement convertToPortfolioFrame;
	
	 @FindBy(xpath="//iframe[contains(@title,'Picklist Edit: ')]")
	 private WebElement statusPicklistFrame;
	 @FindBy(xpath="//iframe[contains(@title,'Sharing Settings ~ Salesforce - Enterprise Edition')]")
	 private WebElement sharingSettingsFrame;
	 @FindBy(xpath="//iframe[contains(@title,'Navigation Custom Field: Navigation Type')]")
	 private WebElement customNavigationFrame;
	 @FindBy(xpath="//iframe[contains(@title,'Add Picklist Values: Navigation Type')]")
	 private WebElement NavigationPickListFrame;
	 @FindBy(xpath="//iframe[contains(@title,'Affiliation Custom Field: Role ~ Salesforce - Enterprise Edition')]")
	 private WebElement affiliationFrame;
	 @FindBy(xpath="//iframe[contains(@title,'Fundraising Contact Custom Field: Role ~ Salesforce - Enterprise Edition')]")
	 private WebElement fundRaisingFrame;
	 @FindBy(xpath="//iframe[contains(@title,'Financing Custom Field: Lender Status ~ Salesforce - Enterprise Edition')]")
	 private WebElement financingFrame;
	 
	 public WebElement getstatusPicklistFrame(int timeOut) {
			return isDisplayed(driver, statusPicklistFrame, "Visibility", timeOut, "status Picklist Frame");
	}
	 @FindBy(xpath="//iframe[contains(@title,'Visualforce Pages')]")
	 private WebElement visualforcePageFrame;
	
	 
	 /**
	  * @author Ankit Jaiswal
	  * @param pageName
	  * @param timeOut
	  * @return webelement/null
	  */
	 public WebElement getFrame(PageName pageName,int timeOut) {
	  WebElement ele=null;
	  if(pageName.toString().equalsIgnoreCase(PageName.InstitutionsPage.toString())) {
	   ele=isDisplayed(driver, institutionPageFrame, "Visibility", timeOut, pageName+" frame");
	  }else if (pageName.toString().equalsIgnoreCase(PageName.Object2Page.toString())) {
	   ele=isDisplayed(driver, contactPageFrame, "Visibility", timeOut, pageName+" frame");
	  }else if (pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString())) {
	   ele=isDisplayed(driver, fundPageFrame, "Visibility", timeOut, pageName+" frame");
	  }else if (pageName.toString().equalsIgnoreCase(PageName.CommitmentsPage.toString())) {
	   ele=isDisplayed(driver, commitmentPageFrame, "Visibility", timeOut, pageName+" frame");
	  }else if(pageName.toString().equalsIgnoreCase(PageName.HomePage.toString())){
		  ele=isDisplayed(driver, homePageAlertsFrame, "Visibility", timeOut, pageName+" frame");
	  }else if(pageName.toString().equalsIgnoreCase(PageName.TaskRayPage.toString())){
		  ele=isDisplayed(driver, taskRayPageFrame, "Visibility", timeOut, pageName+" frame");
	  }else if(pageName.toString().equalsIgnoreCase(PageName.NavatarInvestorAddOnsPage.toString())){
		  ele=isDisplayed(driver, navatarInvestorAddOnFrame, "Visibility", timeOut, pageName+" frame");
	  } else if (pageName.toString().equalsIgnoreCase(PageName.NavatarInvestorManager.toString())){
		  ele=isDisplayed(driver, NIMTabFrame, "Visibility", timeOut, "NIM Frame");
	  }else if (pageName.toString().equalsIgnoreCase(PageName.TaskRayPage.toString())){
		  ele=isDisplayed(driver, taskRayTabFrame, "Visibility", timeOut, "TaskRay Frame");
	  }else if (pageName.toString().equalsIgnoreCase(PageName.ProjectDetailsPoPUp.toString())){
		  ele=isDisplayed(driver, projectDetailsPopUpFrame, "Visibility", timeOut, "Project Details PoPUp Frame");
	  }else if (pageName.toString().equalsIgnoreCase(PageName.TaskPage.toString())){
		  ele=isDisplayed(driver, taskPageFrame, "Visibility", timeOut, "Project Details PoPUp Frame");
	  } else if (pageName.toString().equalsIgnoreCase(PageName.ActivitiesOrMeetings.toString())){
		  ele=isDisplayed(driver, meetingOrActivitiesFrame, "Visibility", timeOut, "meeting or activties Frame");
	  }  else if (pageName.toString().equalsIgnoreCase(PageName.EmailUploadPage.toString())){
		  ele=isDisplayed(driver, emailUploadPageFrame, "Visibility", timeOut, "meeting or activties Frame");
	  }else if (pageName.toString().equalsIgnoreCase(PageName.CompactLayout.toString())){
		  ele=isDisplayed(driver, compactLayoutFrame, "Visibility", timeOut, "meeting or activties Frame");
	  }else if (pageName.toString().equalsIgnoreCase(PageName.ActivityLayoutPage.toString())){
		  ele=isDisplayed(driver, activityLayoutFrame, "Visibility", timeOut, "meeting or activties Frame");
	  }else if (pageName.toString().equalsIgnoreCase(PageName.VisualForcePage.toString())){
		  ele=isDisplayed(driver, visualforcePageFrame, "Visibility", timeOut, "visual force page frame");
	  }
	  else if (pageName.toString().equalsIgnoreCase(PageName.MeetingType.toString())){
		  ele=isDisplayed(driver, meetingTypeFrame, "Visibility", timeOut, "meeting or activties Frame");
	  } else if (pageName.toString().equalsIgnoreCase(PageName.AddPickListMeetingType.toString())){
		  ele=isDisplayed(driver, addPickListMeetingTypeFrame, "Visibility", timeOut, "Add PickList meeting or activties Frame");
	  }else if (pageName.toString().equalsIgnoreCase(PageName.AccountCustomFieldStatusPage.toString())){
		  ele=isDisplayed(driver, accountLayoutFrame, "Visibility", timeOut, "account Layout Frame");
	  }else if (pageName.toString().equalsIgnoreCase(PageName.PipelineCustomPage.toString())){
		  ele=isDisplayed(driver, pipelineLayoutFrame, "Visibility", timeOut, "pipeline custom page frame");
	  }else if (pageName.toString().equalsIgnoreCase(PageName.SharingSettingsPage.toString())){
		  ele=isDisplayed(driver, sharingSettingsFrame, "Visibility", timeOut, "Sharing Settings Page frame");
	  }else if (pageName.toString().equalsIgnoreCase(PageName.DashboardDeadDeals.toString())){
		  ele=isDisplayed(driver, dashboardFrame, "Visibility", timeOut, "Sharing Settings Page frame");
	  }else if (pageName.toString().equalsIgnoreCase(PageName.AccountReferral.toString())){
		  ele=isDisplayed(driver,accountReferralFrame , "Visibility", timeOut, "Sharing Settings Page frame");

	  }else if (pageName.toString().equalsIgnoreCase(PageName.CustomNavigationPage.toString())){
		  ele=isDisplayed(driver,customNavigationFrame , "Visibility", timeOut, "Custom Navigation Frame");
	  }else if (pageName.toString().equalsIgnoreCase(PageName.NavigationPickListPage.toString())){
		  ele=isDisplayed(driver,NavigationPickListFrame , "Visibility", timeOut, "Navigation PickList Page");

	  }else if (pageName.toString().equalsIgnoreCase(PageName.RecordTypePortfolioCompany.toString())){
		  ele=isDisplayed(driver,RTPortfolioCFrame , "Visibility", timeOut, "RecordTypePortfolioCompany");

	  }else if (pageName.toString().equalsIgnoreCase(PageName.ConvertToPortfolioFrame.toString())){
		  ele=isDisplayed(driver,convertToPortfolioFrame , "Visibility", timeOut, "convert to portfolio frame");

	  }else if (pageName.toString().equalsIgnoreCase(PageName.AffiliationPage.toString())){
		  ele=isDisplayed(driver,affiliationFrame , "Visibility", timeOut, "affiliation Frame");

	  }else if (pageName.toString().equalsIgnoreCase(PageName.Fundraising_ContactPage.toString())){
		  ele=isDisplayed(driver,fundRaisingFrame , "Visibility", timeOut, "FR Contact Frame");

	  }else if (pageName.toString().equalsIgnoreCase(PageName.Financing.toString())){
		  ele=isDisplayed(driver,financingFrame , "Visibility", timeOut, "Financing Frame");

	  }
	  return ele; 
	 }
	 
	 @FindBy(xpath = "//input[@id='globalQuickfind']")
	 private WebElement searchTextboxFieldsAndRelationships;
	 
	 public WebElement getsearchTextboxFieldsAndRelationships(int timeOut) {
			return isDisplayed(driver, searchTextboxFieldsAndRelationships, "Visibility", timeOut, "search Textbox on Fields And Relationships");
		}
	 @FindBy(xpath="//div[@class='pbHeader']//input[@title='Edit']")
	 private WebElement editButton_Classic;
	 
	 @FindBy(xpath="//a[@title='Edit']")
	 private WebElement editButton_Lighting;
	
	 /**
	 * @return the editButton
	 */
	public WebElement getEditButton(String environment,String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			return isDisplayed(driver, editButton_Classic, "Visibility", timeOut, "Edit Button Classic");	
		}else{
		return isDisplayed(driver, editButton_Lighting, "Visibility", timeOut, "Edit Button Lighting");	
		
		}
		
	}
	
	 @FindBy(xpath="//label[text()='Active']/../following-sibling::td//input")
	 private WebElement activeCheckbox;
	 public WebElement getactiveCheckbox(int timeOut) {
			return isDisplayed(driver, activeCheckbox, "Visibility", timeOut, "active Checkbox");
		}
	/**
	 * @param editButton the editButton to set
	 */
	public void setEditButton(WebElement editButton) {
		this.editButton_Classic = editButton;
	}

	/*****************************************************Investor registration*************************/
	 
	 
	@FindBy(xpath = "//label[contains(text(),'First Name')]")
	private WebElement firstNameLabel;
	
	
	 /**
	 * @return the firstNameLabel
	 */
	public WebElement getFirstNameLabel(int timeOut) {
		return isDisplayed(driver, firstNameLabel, "Visibility", timeOut, "first name text on investor registration page");
	}
	
	@FindBy(xpath = "//label[contains(text(),'Last Name')]")
	private WebElement lastNameLabel;
	
	
	/**
	 * @return the lastNameLabel
	 */
	public WebElement getLastNameLabel(int timeOut) {
		return isDisplayed(driver, lastNameLabel, "Visibility", timeOut, "last name text on investor registration page");
	}
	
	@FindBy(xpath = "//label[contains(text(),'Nick Name')]")
	private WebElement nickNameLabel;
	
	
	/**
	 * @return the nickNameLabel
	 */
	public WebElement getNickNameLabel(int timeOut) {
		return isDisplayed(driver, nickNameLabel, "Visibility", timeOut, "nick name label text on investor registratin page");
	}

	@FindBy(xpath = "//label[contains(text(),'E-mail (Username)')]")
	private WebElement emailLabel;
	
	
	/**
	 * @return the emailLabel
	 */
	public WebElement getEmailLabel(int timeOut) {
		return isDisplayed(driver, emailLabel, "Visibility", timeOut, "email text text on investor registration page");
	}
	@FindBy(xpath = "//label[contains(text(),'Firm Name')]")
	private WebElement firmNameLabel;
	
	
	/**
	 * @return the firmNameLabel
	 */
	public WebElement getFirmNameLabel(int timeOut) {
		return isDisplayed(driver, firmNameLabel, "Visibility", timeOut, "firm name text on investor registration page");
	}
	@FindBy(xpath = "(//label[contains(text(),'Password')])[1]")
	private WebElement passwordLabel;
	
	
	/**
	 * @return the passwordLabel
	 */
	public WebElement getPasswordLabel(int timeOut) {
		return isDisplayed(driver, passwordLabel, "Visibility", timeOut, "password text on investor registration page");
	}
	@FindBy(xpath = "//label[contains(text(),'Confirm Password')]")
	private WebElement confirmPasswordLabel;
	
	
	/**
	 * @return the confirmPasswordLabel
	 */
	public WebElement getConfirmPasswordLabel(int timeOut) {
		return isDisplayed(driver, confirmPasswordLabel, "Visibility", timeOut, "confirm password text on investor registration page");
	}
	@FindBy(xpath = "//font[contains(text(),'Use at least 8 characters')]")
	private WebElement eightCharactersInfoLabel;
	
	
	/**
	 * @return the eightCharactersInfoLabel
	 */
	public WebElement getEightCharactersInfoLabel(int timeOut) {
		return isDisplayed(driver, eightCharactersInfoLabel, "Visibility", timeOut, "use at least 8 characters information on investor registration page");
	}

	@FindBy(xpath="//input[@name='User_FirstLogin:Form:FirstName']")
	 private WebElement targetFirstName;

	/**
	 * @return the targetFirstName
	 */
	public WebElement getTargetFirstName(int timeOut) {
		return isDisplayed(driver, targetFirstName, "Visibility", timeOut, "target first name");
	}
	
	@FindBy(xpath="//input[@name='User_FirstLogin:Form:LastName']")
	private WebElement targetlastName;

	/**
	 * @return the targetlastName
	 */
	public WebElement getTargetlastName(int timeOut) {
		return isDisplayed(driver, targetlastName, "Visibility", timeOut, "target last name");
	}
	
	@FindBy(xpath="//input[@name='User_FirstLogin:Form:communityNickname']")
	private WebElement targetNickName;

	/**
	 * @return the targetNickName
	 */
	public WebElement getTargetNickName(int timeOut) {
		return isDisplayed(driver, targetNickName, "Visibility", timeOut, "target nick name");
	}
	
	@FindBy(xpath="//input[@name='User_FirstLogin:Form:UserName_Email']")
	private WebElement targetEmailId;
	
	
	/**
	 * @return the targetEmailId
	 */
	public WebElement getTargetEmailId(int timeOut) {
		return isDisplayed(driver, targetEmailId, "Visibility", timeOut, "target email id");
	}

	@FindBy(xpath="//input[@name='User_FirstLogin:Form:investorfirm']")
	private WebElement targetFirmName;

	/**
	 * @return the targetFirmName
	 */
	public WebElement getTargetFirmName(int timeOut) {
		return isDisplayed(driver, targetFirmName, "Visibility", timeOut, "target firm name");
	}
	
	@FindBy(xpath="//input[@name='User_FirstLogin:Form:pwd']")
	private WebElement targetpassword;

	/**
	 * @return the targetpassword
	 */
	public WebElement getTargetpassword(int timeOut) {
		return isDisplayed(driver, targetpassword, "Visibility", timeOut, "target password");
	}
	
	@FindBy(xpath="//input[@name='User_FirstLogin:Form:cpwd']")
	private WebElement targetConfirmPassword;

	/**
	 * @return the targetConfirmPassword
	 */
	public WebElement getTargetConfirmPassword(int timeOut) {
		return isDisplayed(driver, targetConfirmPassword, "Visibility", timeOut, "target confirm password");
	}
	
	@FindBy(xpath="//a[@class='GlobalButton']/span")
	private WebElement targetSignUpBtn;

	/**
	 * @return the targetSignUpBtn
	 */
	public WebElement getTargetSignUpBtn(int timeOut) {
		return isDisplayed(driver, targetSignUpBtn, "Visibility", timeOut, "sign button");
	}
	
	@FindBy(xpath="//a[@class='GlobalButtonCancel']/span")
	private WebElement targetcancelBtn;

	/**
	 * @return the targetcancelBtn
	 */
	public WebElement getTargetcancelBtn(int timeOut) {
		return isDisplayed(driver, targetcancelBtn, "Visibility", timeOut, "cancel button");
	}
	
	@FindBy(xpath = "//a[@title='Logout']")
	private WebElement investorLogout;

	/**
	 * @return the logout
	 */
	public WebElement getInvestorLogout() {
		return isDisplayed(driver, investorLogout, "Visibility", 30, "Investor logout button");
	}
	@FindBy(xpath = "//img[contains(@src,'navatar')]")
	private WebElement navatarImageInvRegistration;

	/**
	 * @return the navatarImage
	 */
	public WebElement getnavatarImageInvRegistration(int timeOut) {
		return isDisplayed(driver, navatarImageInvRegistration, "Visibility", timeOut, "Navatar image on investor registration page");
	}
	
	@FindBy(xpath="//div[@id='nodata']//h1")
	private WebElement errorMessageBeforeGivingInternalUserAccess;

	/**
	 * @return the errorMessageBeforeGivingInternalUserAccess
	 */
	public WebElement getErrorMessageBeforeGivingInternalUserAccess(int timeOut) {
		return isDisplayed(driver, errorMessageBeforeGivingInternalUserAccess, "Visibility", timeOut, "Error Message Before Giving Internal User Access");
	}
	@FindBy(xpath = "//a[@title='Sign In?']")
	private WebElement signInLink;

	@FindBy(xpath="//span[text()='Enabled Visualforce Page Access']")
	private WebElement enableVisualForcePageAccessLink;

	/**
	 * @return the enableVisualForcePageAccessLink
	 */
	public WebElement getEnableVisualForcePageAccessLink(int timeOut) {
		return isDisplayed(driver, enableVisualForcePageAccessLink, "Visibility", timeOut, "Enable VisiualForce Page Access Link");
	}
	
	@FindBy(xpath="//h3[text()='Enabled Visualforce Page Access']/../following-sibling::td//input")
	private WebElement enableVisualForcePageAccessEditButton;

	/**
	 * @return the enableVisualForcePageAccessEditButton
	 */
	public WebElement getEnableVisualForcePageAccessEditButton(int timeOut) {
		return isDisplayed(driver, enableVisualForcePageAccessEditButton, "Visibility", timeOut, "Edit Button");
	}
		
	/**
	 * @return the signInButton
	 */
	public WebElement getSignInLink(int timeOut) {
		return isDisplayed(driver, signInLink, "Visibility", timeOut, "Sign in button on investor registration page");
	}
	
	@FindBy(xpath="//a[contains(@title,'Last Login')]")
	private WebElement lastLogin;

	/**
	 * @return the lastLogin
	 */
	public WebElement getLastLogin(int timeOut) {
		return isDisplayed(driver, lastLogin, "Visibility", timeOut, "Last Login");
	}
	
	@FindBy(xpath="//select[@id='fcf']")
	private WebElement viewAllDropdownList;

	
	
	/**
	 * @return the viewAllDropdownList
	 */
	public WebElement getViewAllDropdownList(int timeOut) {
		return isDisplayed(driver, viewAllDropdownList, "Visibility", timeOut, "View All Dropdown List");
	}
	
	@FindBy(xpath="//input[@id='active']")
	private WebElement activeCheckboxInUserCreation;

	/**
	 * @return the activeCheckboxINUSerCreation
	 */
	public WebElement getActiveCheckboxInUserCreation(int timeOut) {
		return isDisplayed(driver, activeCheckboxInUserCreation, "Visibility", timeOut, "Active Checkbox In User Creation Page");
	}
	
	@FindBy(xpath="//select[@id='duel_select_0']")
	private WebElement VFPageMultiSelect;

	/**
	 * @return the vFPageMultiSelect
	 */
	public WebElement getVFPageMultiSelect(int timeOut) {
		return isDisplayed(driver, VFPageMultiSelect, "Visibility", timeOut, "VF Page Multi Select");
	}
	
	@FindBy(xpath="//img[@class='rightArrowIcon']")
	private WebElement multiSelectAddButton;

	/**
	 * @return the multiSelectAddButton
	 */
	public WebElement getMultiSelectAddButton(int timeOut) {
		return isDisplayed(driver, multiSelectAddButton, "Visibility", timeOut, "Multi Select Add Button");
	}
	
	@FindBy(xpath="//div[@id='pendingPopup']/div[@class='head_popup']")
	private WebElement pendingDisclaimerPopUpHeader;
	
	@FindBy(xpath="//td[@id='topButtonRow']//input[@title='Delete']")
	private WebElement deleteButton;

	/**
	 * @return the deleteButton
	 */
	public WebElement getDeleteButton(int timeOut) {
		return isDisplayed(driver, deleteButton, "Visibility", timeOut, "Delete Button");
	}

	/**
	 * @return the pendingDisclaimerPopUpHeader
	 */
	public WebElement getPendingDisclaimerPopUpHeader(int timeOut) {
		return isDisplayed(driver, pendingDisclaimerPopUpHeader, "Visibility", timeOut, "Pending disclaimer Pop Header");
	}

	@FindBy(xpath="//div[@id='pendingPopup']/div[@class='formbox']")
	private WebElement pendingDisclaimerPopUpMessage;

	/**
	 * @return the pendingDisclaimerPopUpMessage
	 */
	public WebElement getPendingDisclaimerPopUpMessage(int timeOut) {
		return isDisplayed(driver, pendingDisclaimerPopUpMessage, "Visibility", timeOut, "Pending disclaimer pop up message");
	}
	
	@FindBy(xpath="//div[@id='pendingPopup']/div[@class='paginationstyle']/a[@title='Go to Disclaimers']")
	private WebElement pendingDisclaimerPopGoToDisclaimerButton;

	/**
	 * @return the pendingDisclaimerPopGoToDisclaimerButton
	 */
	public WebElement getPendingDisclaimerPopGoToDisclaimerButton(int timeOut) {
		return isDisplayed(driver, pendingDisclaimerPopGoToDisclaimerButton, "Visibility", timeOut, "Goto Disclaimer Button");
	}
	
	@FindBy(xpath="//div[@id='pendingPopup']/div[@class='paginationstyle']/a[@title='Cancel']")
	private WebElement pendingDisclaimerPopUpCancelBUtton;

	/**
	 * @return the pendingDisclaimerPopUpCancelBUtton
	 */
	public WebElement getPendingDisclaimerPopUpCancelBUtton(int timeOut) {
		return isDisplayed(driver, pendingDisclaimerPopUpCancelBUtton, "Visibility", timeOut, "Pending disclaimer Pop Up Cancel Button");
	}
	
	@FindBy(xpath="//div[@id='pendingPopup']/div[@class='head_popup']/a")
	private WebElement pendingDisclaimerPopUpCrossIcon;

	/**
	 * @return the pendingDisclaimerPopUpCrossIcon
	 */
	public WebElement getPendingDisclaimerPopUpCrossIcon(int timeOut) {
		return isDisplayed(driver, pendingDisclaimerPopUpCrossIcon, "Visibility", timeOut, "");
	}
	
	
	@FindBy(xpath = "//select[contains(@id,'compHeadID:headerCompID:selectValue')]")
	private WebElement firmNameDropdown;

	/**
	 * @return the firmNameDropdown
	 */
	public WebElement getFirmNameDropdown(int timeOut) {
		return isDisplayed(driver, firmNameDropdown, "Visibility", timeOut, "");
	}

	
	@FindBy(xpath = "//a[@title='Profile']")
	private WebElement profileLink;

	/**
	 * @return the profileLink
	 */
	public WebElement getProfileLink(int timeOut) {
		return isDisplayed(driver, profileLink, "Visibility", timeOut, "Profile link on top right");
	}
	
	@FindBy(xpath="//div[@class='head_popup head_popupDisclaimer']")
	private WebElement accessDeniedPopUpHeader;

	/**
	 * @return the accessDeniedPopUpHeader
	 */
	public WebElement getAccessDeniedPopUpHeader(int timeOut) {
		return isDisplayed(driver, accessDeniedPopUpHeader, "Visibility", timeOut, "Access Denied Pop Up Header");
	}
	
	@FindBy(xpath="//div[@class='formbox formboxDisclaimer']")
	private WebElement accessDeniedPopUpMessage;

	/**
	 * @return the accessDeniedPopUpMessage
	 */
	public WebElement getAccessDeniedPopUpMessage(int timeOut) {
		return isDisplayed(driver, accessDeniedPopUpMessage, "Visibility", timeOut, "Access denied Pop Up message");
	}
	
	@FindBy(xpath="//div[@class='paginationstyle']/a[@title='Go to Disclaimers']")
	private WebElement accessDeniedPopUpGoToDisclaimerButton;

	/**
	 * @return the accessDeniedGoToDisclaimerButton
	 */
	public WebElement getAccessDeniedPopUpGoToDisclaimerButton(int timeOut) {
		return isDisplayed(driver, accessDeniedPopUpGoToDisclaimerButton, "Visibility", timeOut, "Access denied pop up Go to Disclaimer button");
	}
	
	@FindBy(xpath="//a[@id='downloadLink']")
	private WebElement downloadLink;

	/**
	 * @return the downloadLink
	 */
	public WebElement getDownloadLink(int timeOut) {
		return isDisplayed(driver, downloadLink, "Visibility", timeOut, "Download Button");
	}
	@FindBy(xpath = "//a[@title='Close']")
	private WebElement viewFileOnFileDistClose;
	
	
	/**
	 * @return the viewFileOnFileDistClose
	 */
	public WebElement getViewFileOnFileDistClose(int timeOut) {
		return isDisplayed(driver, viewFileOnFileDistClose, "Visibility", timeOut, "");
	}

	@FindBy(xpath = "//a[@title='Download']")
	private WebElement viewFileOnfileDistDownload;
	
	
	
	/**
	 * @return the fileDistDownload
	 */
	public WebElement getViewFileOnfileDistDownload(int timeOut) {
		return isDisplayed(driver, viewFileOnfileDistDownload, "Visibility", timeOut, "File Dist Download");
	}

	@FindBy(xpath="//a[@class='dynatree-title'][text()='All Folders']")
	private WebElement allFoldersOnBulkDownloadWindow;

	/**
	 * @return the allFoldersOnBulkDownloadWindow
	 */
	public WebElement getAllFoldersOnBulkDownloadWindow(int timeOut) {
		return isDisplayed(driver, allFoldersOnBulkDownloadWindow, "Visibility", timeOut, "All folders");
	}
	

	@FindBy(xpath="//a[@title='Close']/img")
	private WebElement documentCloseBtn;


	/**
	 * @return the doucmentCloseBtn
	 */
	public WebElement getDocumentCloseBtn(int timeOut) {
		return isDisplayed(driver, documentCloseBtn, "Visibility", timeOut, "Document Close Button");
	}
	
	
	
	@FindBy(xpath ="//span[@id='myGridfundr-scroll-box']")
	private WebElement scrollBoxforFundPage;

	/**
	 * @return the scrollBoxforFundPage
	 */
	public WebElement getScrollBoxforFundPage(int timeOut) {
		return isDisplayed(driver, scrollBoxforFundPage, "Visibility", timeOut,
				"Scroll Box");
	}
	

	public WebElement getWorkspaceSectionView(Workspace workspace, int timeOut){
		String workspaceSelector = "";
		if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
			workspaceSelector = "Fundraising";
		} else {
			workspaceSelector = "Investor";
		}
		return isDisplayed(driver, FindElement(driver, "//h3[text()='"+workspaceSelector+" Workspace']", workspace+" view section", action.BOOLEAN, 30), "visibility", 60, workspace+" view section");
	}
	
	public WebElement getAddFolderParentFolderCancelButton(Workspace workspace, PageName pageName, int timeOut){
		String workspaceSelector = "";
		
		if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString())){
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector = "FR";
			} else {
				workspaceSelector = "INV";
			}
			return isDisplayed(driver, FindElement(driver, "//a[contains(@onclick,'clearTextBW"+ workspaceSelector +"')][contains(@onclick,''none'')][contains(@onclick,';')]", "Parent Folder cancel Button", action.BOOLEAN, timeOut), "Visibility", timeOut, "Parent Folder cancel Button");
		} else {
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector = "fundraising";
			} else {
				workspaceSelector = "investor";
			}
			return isDisplayed(driver, FindElement(driver, "//a[contains(@onclick,'CreateCommon_pop1"+workspaceSelector+"();')]/following-sibling::a", "Parent Folder cancel Button", action.BOOLEAN, timeOut), "Visibility", timeOut, "Parent Folder cancel Button");
		}
	}
	
	@FindBy(xpath="//div[@class='tip-inner tip-bg-image']/span")
	public WebElement addFolderInfoIconMessage;

	/**
	 * @return the addFolderInfoIconMessage
	 */
	public WebElement getAddFolderInfoIconMessage(int timeOut) {
		return isDisplayed(driver, addFolderInfoIconMessage, "Visibility", timeOut, "Add folder info icon message;");
	}
	
	@FindBy(xpath="//a[@id='Customize_icon']//img")
	private WebElement customizeIcon;

	/**
	 * @return the customizeIcon
	 */
	public WebElement getCustomizeIcon(int timeOut) {
		return isDisplayed(driver, customizeIcon, "Visibility", timeOut, "Customize Icon");
	}
	
	@FindBy(xpath="//a[@id='Account_icon']//img")
	private WebElement accountsIconUnderCustomizeIcon;

	/**
	 * @return the accountsTabUnderCustomizeIcon
	 */
	public WebElement getAccountsIconUnderCustomizeIcon(int timeOut) {
		return isDisplayed(driver, accountsIconUnderCustomizeIcon, "Visibility", timeOut, "Accounts Icon under Customize Icon");
	}
	
	@FindBy(xpath="//a[@id='AccountFields_font']")
	private WebElement  fieldsLabelUnderAccountIcon;

	/**
	 * @return the fieldsLabelUnderAccountIcon
	 */
	public WebElement getFieldsLabelUnderAccountIcon(int timeOut) {
		return isDisplayed(driver, fieldsLabelUnderAccountIcon, "Visibility", timeOut, "Fields Label");
	}
	 
	@FindBy(xpath="//input[@name='new_field']")
	private WebElement newButtonInAccountsField;

	/**
	 * @return the newButtonInAccountsField
	 */
	public WebElement getNewButtonInAccountsField(int timeOut) {
		return isDisplayed(driver, newButtonInAccountsField, "Visibility", timeOut, "New Button");
	} 
	
	@FindBy(xpath="//div[@class='pbBottomButtons']//input[@title='Next']")
	private WebElement nextButton;

	/**
	 * @return the nextButton
	 */
	public WebElement getNextButton(int timeOut) {
		return isDisplayed(driver, nextButton, "Visibility", timeOut, "Next Button");
	} 
	
	@FindBy(xpath="//input[@id='MasterLabel']")
	private WebElement fieldLabelTextBox;

	/**
	 * @return the fieldLabelTextBox
	 */
	public WebElement getFieldLabelTextBox(int timeOut) {
		return isDisplayed(driver, fieldLabelTextBox, "Visibility", timeOut, "Field Label Text Box");
	}
	
	 @FindBy(xpath="//input[@id='startNum']")
	 private WebElement startingNumberFieldTextBox;

	/**
	 * @return the startingNumberFieldTextBox
	 */
	public WebElement getStartingNumberFieldTextBox(int timeOut) {
		return isDisplayed(driver, startingNumberFieldTextBox, "Visibility", timeOut, "Starting Number Field TextBox");
	}
	
	@FindBy(xpath="//input[@id='Scale']")
	private WebElement decimalPlacesTextbox;

	/**
	 * @return the decimalPlacesTextbox
	 */
	public WebElement getDecimalPlacesTextbox(int timeOut) {
		return isDisplayed(driver, decimalPlacesTextbox, "Visibility", timeOut, "Decimal Places Text box");
	}
	
	@FindBy(xpath="//div[@class='pbBottomButtons']//input[@name='save']")
	private WebElement saveButtonInCustomFields;

	/**
	 * @return the saveButtonInCustomFields
	 */
	public WebElement getSaveButtonInCustomFields(int timeOut) {
		return isDisplayed(driver, saveButtonInCustomFields, "Visibility", timeOut, "Save Button In custom Fields");
	}
	
	@FindBy(xpath="//a[@id='DevTools_icon']//img")
	private WebElement createIcon;

	/**
	 * @return the createIcon
	 */
	public WebElement getCreateIcon(int timeOut) {
		return isDisplayed(driver, createIcon, "Visibility", timeOut, "Create Icon");
	}
	
	@FindBy(xpath="//a[text()='Objects']")
	private WebElement objectsLabel;

	/**
	 * @return the objectsLabel
	 */
	public WebElement getObjectsLabel(int timeOut) {
		return isDisplayed(driver, objectsLabel, "Visibility", timeOut, "Objects Label");
	}
	
	@FindBy(xpath="//th[@class=' dataCell  ']//a[text()='Fundraising']")
	private WebElement fundraisingLabelInObject;

	/**
	 * @return the fundraisingLabelInObject
	 */
	public WebElement getFundraisingLabelInObject(int timeOut) {
		return isDisplayed(driver, fundraisingLabelInObject, "Visibility", timeOut, "FundRaisingLabel");
	}
	

	@FindBy(xpath="//textarea[@class='FormulaText']")
	private WebElement formulaTextBox;

	/**
	 * @return the folrulaTextBox
	 */
	public WebElement getFormulaTextBox(int timeOut) {
		return isDisplayed(driver, formulaTextBox, "Visibility", timeOut, "Formula TextBox");
	} 
	
	@FindBy(xpath="//input[@id='picklistTypeLOCAL_PICKLIST']")
	private WebElement multiPicklistValueRadioButton;

	/**
	 * @return the multiPicklistValueRadioButton
	 */
	public WebElement getMultiPicklistValueRadioButton(int timeOut) {
		return isDisplayed(driver, multiPicklistValueRadioButton, "Visibility", timeOut, "MultiPickListRadio Button");
	}
	
	@FindBy(xpath="//textarea[@id='ptext']")
	private WebElement valueTextBox;

	/**
	 * @return the valueTextBox
	 */
	public WebElement getValueTextBox(int timeOut) {
		return isDisplayed(driver, valueTextBox, "Visibility", timeOut, "Value Text Box");
	}
	
	public List<WebElement> getAllFundraisingscustomFields() {
		return FindElements(driver, "//div[@class='noStandardTab']//th[@class=' dataCell  ']//a", "All FundRaisings Custom Fields");
	}
	
	
	@FindBy(xpath="//th[@class=' dataCell  ']//a[text()='Commitment']")
	private WebElement commitmentLabelInObject;

	/**
	 * @return the commitmentLabelInObject
	 */
	public WebElement getCommitmentLabelInObject(int timeOut) {
		return isDisplayed(driver, commitmentLabelInObject, "Visibility", timeOut, "Commitment Label");
	}
	
	@FindBy(xpath="//input[@id='Length']")
	private WebElement lengthTextbox;

	/**
	 * @return the lengthTextbox
	 */
	public WebElement getLengthTextbox(int timeOut) {
		return isDisplayed(driver, lengthTextbox, "Visibility", timeOut, "Length TextBox");
	}
	
	public List<WebElement> getAllCommitmentscustomFields() {
		return FindElements(driver, "//div[@class='noStandardTab']//th[@class=' dataCell  ']//a", "All Commitments Custom Fields");
	}
	
	 
	/**
	 * @return the deleteFileNoButtonLinkContentGrid
	 */
	public WebElement getDeleteFileNoButtonContentGrid(Workspace workSpace,int timeOut) {
		int i =0;
		String prefixXpath = "(//div[contains(text(),'Confirm Deletion')])[";
		String suffixXpath = "]/following-sibling::div/a[@title='No']";
		String fullXpath="";
		WebElement ele;
		if(workSpace.toString().equalsIgnoreCase(Workspace.InvestorWorkspace.toString())){
			i=2;
			fullXpath=prefixXpath+i+suffixXpath;
			ele= FindElement(driver, fullXpath, "Delete File No Button", action.BOOLEAN, timeOut);
			return isDisplayed(driver, ele, "Visibility", timeOut, "Delete File No Button");
		}else{
			i=1;
			fullXpath=prefixXpath+i+suffixXpath;
			ele= FindElement(driver, fullXpath, "Delete File No Button", action.BOOLEAN, timeOut);
			return isDisplayed(driver, ele, "Visibility", timeOut, "Delete File No Button");
		}
	
		
		
	}
	

	/**
	 * @return the deleteFileCrossIconLinkContentGrid
	 */
	public WebElement getDeleteFileCrossIconLinkContentGrid(Workspace workSpace,int timeOut) {
		
		int i =0;
		String prefixXpath = "(//div[contains(text(),'Confirm Deletion')])[";
		String suffixXpath = "]/span[@title='Close']";
		String fullXpath="";
		WebElement ele;
		if(workSpace.toString().equalsIgnoreCase(Workspace.InvestorWorkspace.toString())){
			i=2;
			fullXpath=prefixXpath+i+suffixXpath;
			ele= FindElement(driver, fullXpath, "Delete File Cross Icon", action.BOOLEAN, timeOut);
			return isDisplayed(driver, ele, "Visibility", timeOut, "Delete File Cross Icon");
		}else{
			i=1;
			fullXpath=prefixXpath+i+suffixXpath;
			ele= FindElement(driver, fullXpath, "Delete File Cross Icon", action.BOOLEAN, timeOut);
			return isDisplayed(driver, ele, "Visibility", timeOut, "Delete File Cross Icon");
		}
	}
	

	/**
	 * @return the deleteFileYesButtonLinkContentGrid
	 */
	public WebElement getDeleteFileYesButtonContentGrid(Workspace workSpace , int timeOut) {
		
		int i =0;
		String prefixXpath = "(//div[contains(text(),'Confirm Deletion')])[";
		String suffixXpath = "]/following-sibling::div/a[@title='Yes']";
		String fullXpath="";
		WebElement ele;
		if(workSpace.toString().equalsIgnoreCase(Workspace.InvestorWorkspace.toString())){
			i=2;
			fullXpath=prefixXpath+i+suffixXpath;
			ele= FindElement(driver, fullXpath, "Delete File Yes Button", action.BOOLEAN, timeOut);
			return isDisplayed(driver, ele, "Visibility", timeOut, "Delete File Yes Button");
		}else{
			i=1;
			fullXpath=prefixXpath+i+suffixXpath;
			ele= FindElement(driver, fullXpath, "Delete File Yes Button", action.BOOLEAN, timeOut);
			return isDisplayed(driver, ele, "Visibility", timeOut, "Delete File Yes Button");
		}
	}
	
	public WebElement getDeleteFilePopUpMsgContentGrid(Workspace workSpace , int timeOut) {
		
		int i =0;
		String prefixXpath = "(//div[contains(text(),'Confirm Deletion')])[";
		String suffixXpath = "]/../p";
		String fullXpath="";
		WebElement ele;
		if(workSpace.toString().equalsIgnoreCase(Workspace.InvestorWorkspace.toString())){
			i=2;
			fullXpath=prefixXpath+i+suffixXpath;
			ele= FindElement(driver, fullXpath, "Delete File Msg", action.BOOLEAN, timeOut);
			return isDisplayed(driver, ele, "Visibility", timeOut, "Delete File Msg");
		}else{
			i=1;
			fullXpath=prefixXpath+i+suffixXpath;
			ele= FindElement(driver, fullXpath, "Delete File Msg", action.BOOLEAN, timeOut);
			return isDisplayed(driver, ele, "Visibility", timeOut, "Delete File Msg");
		}
	}
	
	public WebElement getDeleteFilePopUpHeaderContentGrid(Workspace workSpace , int timeOut) {
		
		int i =0;
		String prefixXpath = "(//div[contains(text(),'Confirm Deletion')])[";
		String suffixXpath = "]";
		String fullXpath="";
		WebElement ele;
		if(workSpace.toString().equalsIgnoreCase(Workspace.InvestorWorkspace.toString())){
			i=2;
			fullXpath=prefixXpath+i+suffixXpath;
			ele= FindElement(driver, fullXpath, "Delete File Pop Up Header", action.BOOLEAN, timeOut);
			return isDisplayed(driver, ele, "Visibility", timeOut, "Delete File Pop Up Header");
		}else{
			i=1;
			fullXpath=prefixXpath+i+suffixXpath;
			ele= FindElement(driver, fullXpath, "Delete  File Pop Up Header", action.BOOLEAN, timeOut);
			return isDisplayed(driver, ele, "Visibility", timeOut, "Delete File Pop Up Header");
		}
	}


/**
 * @return the searchIcon for AllPage  FundRaising or Investor Side
 */
	public  WebElement getSearchIcon(WebDriver driver, PageName pName, Workspace workspace, int timeOut) {

		String workSpaceXpath = null;
		WebElement ele = null;
		String searchIconXpath = "//a[@title='Search']";
		String fullXPath;

		if (pName.toString().equalsIgnoreCase(PageName.FundsPage.toString())) {

			if (workspace.toString().equalsIgnoreCase(Workspace.InvestorWorkspace.toString())) {
				workSpaceXpath = "//div[@id='invworkspace']";

			} else {
				workSpaceXpath = "//div[@id='frworkspace']";

			}

		} else if (pName.toString().equalsIgnoreCase(PageName.InstitutionsPage.toString())
				|| pName.toString().equalsIgnoreCase(PageName.Object2Page.toString())) {

			if (workspace.toString().equalsIgnoreCase(Workspace.InvestorWorkspace.toString())) {
				workSpaceXpath = "//div[@id='Investorgrid_div']";

			} else {
				workSpaceXpath = "//div[@id='divFrWorkspace']";
			}
		} else if (pName.toString().equalsIgnoreCase(PageName.CommitmentsPage.toString())) {

			if (workspace.toString().equalsIgnoreCase(Workspace.InvestorWorkspace.toString())) {
				workSpaceXpath = "//div[@class='content_div']";

			} else {
				workSpaceXpath = "//div[@class='content_div']";
			}
		}

		if (workSpaceXpath != null) {

			fullXPath = workSpaceXpath + searchIconXpath;

			ele = FindElement(driver, fullXPath, "Search Icon", action.SCROLLANDBOOLEAN, timeOut);

		} else {
			appLog.info(" Xpath Not Found for Seacrh Icon for " + pName.toString() + " : " + workspace.toString());
			BaseLib.sa.assertTrue(false,
					" Xpath Not Found for Seacrh Icon for " + pName.toString() + " : " + workspace.toString());
		}

		return ele;

	}
	
	/**
	 * @return the searchTextBox for AllPage  FundRaising or Investor Side
	 */

	public  WebElement getSearchTextBox(WebDriver driver, PageName pName, Workspace workspace, int timeOut) {

		String workSpaceXpath = null;
		WebElement ele = null;
		String searchTextBoxXpath = "//input[@class='input_txt_search']";
		String fullXPath;

		if (pName.toString().equalsIgnoreCase(PageName.FundsPage.toString())) {

			if (workspace.toString().equalsIgnoreCase(Workspace.InvestorWorkspace.toString())) {
				workSpaceXpath = "//div[@id='invworkspace']";

			} else {
				workSpaceXpath = "//div[@id='frworkspace']";

			}

		} else if (pName.toString().equalsIgnoreCase(PageName.InstitutionsPage.toString())
				|| pName.toString().equalsIgnoreCase(PageName.Object2Page.toString())) {

			if (workspace.toString().equalsIgnoreCase(Workspace.InvestorWorkspace.toString())) {
				workSpaceXpath = "//div[@id='Investorgrid_div']";

			} else {
				workSpaceXpath = "//div[@id='divFrWorkspace']";
			}
		} else if (pName.toString().equalsIgnoreCase(PageName.CommitmentsPage.toString())) {

			if (workspace.toString().equalsIgnoreCase(Workspace.InvestorWorkspace.toString())) {
				workSpaceXpath = "//div[@class='content_div']";

			} else {
				workSpaceXpath = "//div[@class='content_div']";
			}
		}

		if (workSpaceXpath != null) {

			fullXPath = workSpaceXpath + searchTextBoxXpath;

			ele = FindElement(driver, fullXPath, "Search Text Box", action.SCROLLANDBOOLEAN, timeOut);

		} else {
			appLog.info(" Xpath Not Found for Seacrh Text Box for " + pName.toString() + " : " + workspace.toString());
			BaseLib.sa.assertTrue(false,
					" Xpath Not Found for Seacrh Text Box for " + pName.toString() + " : " + workspace.toString());
		}

		return ele;

	}

	
	@FindBy(xpath="//div[@id='SearchId']//div[@class='head_popup']")
	private WebElement searchHeader;

	/**
	 * @return the searchHeader
	 */
	public WebElement getSearchHeader(int timeOut) {
		return isDisplayed(driver, searchHeader, "Visibility", timeOut, "Search Header");
	}
	
	@FindBy(xpath="//span[@id='Specify_the_recipients_to_include-headers']/span[contains(@id,'Specify_the_recipients_to_include-header-')]/span//span[3]")
	private List<WebElement> allLabelsOnSearchPopUp;

	/**
	 * @return the allLabelsOnSearchPopUp
	 */
	public List<WebElement> getAllLabelsOnSearchPopUp() {
		if(checkElementsVisibility(driver, allLabelsOnSearchPopUp, "All Label Search Pop", 60)){
			return allLabelsOnSearchPopUp;
		} else {
			return null;
		}
	}
	
	/**
	 * @return the getAllRadioButton
	 */
	public WebElement getAllFolderRadioButton(int timeOut) {
		String path = "//span[text()='Search All Folders']/..//input";
		WebElement ele =FindElement(driver, path, "All Folder Radio Button", action.SCROLLANDBOOLEAN, 30);
		return isDisplayed(driver, ele, "Visibility", timeOut, "Selected Folder Radio Button");
	}

	/**
	 * @return the getFolderRadioButton
	 */
	public WebElement getFolderRadioButton(String folder,int timeOut) {
		String path = "//span[@title='"+folder+"']/../..//input[@id='selectedFolder']";
		WebElement ele =FindElement(driver, path, folder, action.SCROLLANDBOOLEAN, 30);
		return isDisplayed(driver, ele, "Visibility", timeOut, " Folder Radio Button");
	}
	
	@FindBy(xpath="//a[contains(@onclick,'clear_gridDD')]")
	private WebElement clearSearchContentButtonSearchResultPopUp;

	/**
	 * @return the clearSearchContentButtonSearchResultPopUp
	 */
	public WebElement getClearSearchContentButtonSearchResultPopUp(int timeOut) {
		return isDisplayed(driver, clearSearchContentButtonSearchResultPopUp, "Visibility", timeOut, "clear Search Result Button On Search Result Popup.");
	}
	
	@FindBy(xpath="//div[contains(text(),'Search Results')]/following-sibling::div//input[@class='input_txt_search']")
	private WebElement searchResultPopUpTextBox;

	/**
	 * @return the searchResultPopUpTextBox
	 */
	public WebElement getSearchResultPopUpTextBox(int timeOut) {
		return isDisplayed(driver, searchResultPopUpTextBox, "Visibility", timeOut, "Search Result Pop Up Text Box.");
	}
	
	@FindBy(xpath="//div[contains(text(),'Search Results')]/following-sibling::div//a[@class='icon_btn_search']")
	private WebElement searchResultPopSearchIcon;

	/**
	 * @return the searchResultPopSearchIcon
	 */
	public WebElement getSearchResultPopSearchIcon(int timeOut) {
		return isDisplayed(driver, searchResultPopSearchIcon, "Visibility", timeOut, "search Result Search Icon Button");
	}
	
	@FindBy(xpath="//a[@onclick='refreshAllData(); return false;']")
	private WebElement searchPopCrossIcon;

	/**
	 * @return the searchPopCrossIcon
	 */
	public WebElement getSearchPopCrossIcon(int timeOut) {
		return isDisplayed(driver, searchPopCrossIcon, "Visibility", timeOut, "Search Pop Up Cross Icon");
	}
	
	/**
	 * @return the getSearchFolderPathColumnValue
	 */
	public List<WebElement> getDateColumnValue(){
		String xpath ="//span[contains(@id,'Specify_the_recipients_to_include-cell-0-')]";
		return FindElements(driver, xpath, "Date Column Value");
	}
	
	/**
	 * @return the getSearchFolderPathColumnValue
	 */
	public List<WebElement> getNameColumnValue(){
		String xpath ="//span[contains(@id,'Specify_the_recipients_to_include-cell-1-')]/a";
		return FindElements(driver, xpath, " Name Column Value");
	}
	
	/**
	 * @return the getSearchSizeColumnValue
	 */
	public List<WebElement> getStatusColumnValue(){
		String xpath ="//span[contains(@id,'Specify_the_recipients_to_include-cell-4-')]";
		return FindElements(driver, xpath, "Status Column Value");
	}
	
	/**
	 * @return the getSearchLastUpdatedColumnValue
	 */
	public List<WebElement> getOwnerColumnValue(){
		String xpath ="//span[contains(@id,'Specify_the_recipients_to_include-cell-5-')]";
		return FindElements(driver, xpath, "Ownerd Column Value");
	}
	
	/**
	 * @return the getSearchLastUpdatedColumnValue
	 */
	public List<WebElement> getMeetingTypeColumnValue(){
		String xpath ="//span[contains(@id,'Specify_the_recipients_to_include-cell-6-')]";
		return FindElements(driver, xpath, "Ownerd Column Value");
	}
	
	/**
	 * @return the getSearchLastUpdatedColumnValue
	 */
	public List<WebElement> getActivityColumnValue(){
		String xpath ="//span[contains(@id,'Specify_the_recipients_to_include-cell-7-')]";
		return FindElements(driver, xpath, "Activity Column Value");
	}
	
	@FindBy(xpath="//div[@id='clearsearchenbDD']/a")
	private WebElement searchPopSearchTextBoxCrossIcon;

	/**
	 * @return the searchPopSearchTextBoxCrossIcon
	 */
	public WebElement getSearchPopSearchTextBoxCrossIcon(int timeOut) {
		return isDisplayed(driver, searchPopSearchTextBoxCrossIcon, "Visibility", timeOut, "Search Pop Up Search Text Box Cross Icon");
	}
	
	@FindBy(xpath="//span[contains(@id,'SearchResultsFor_DealDetail_FileSearch-cell-')]/span")
	private WebElement noDataToDisplaySearchPopMsg;

	/**
	 * @return the noDataToDisplaySearchPopMsg
	 */
	public WebElement getNoDataToDisplaySearchPopMsg(int timeOut) {
		return isDisplayed(driver, noDataToDisplaySearchPopMsg, "Visibility", timeOut, "No Data To display Msg");
	}
	
	@FindBy(xpath="//div[@id='SearchId']//b/font")
	private WebElement noDataToDisplaySearchPopMsg1;

	/**
	 * @return the noDataToDisplaySearchPopMsg1
	 */
	public WebElement getNoDataToDisplaySearchPopMsg1(int timeOut) {
		return isDisplayed(driver, noDataToDisplaySearchPopMsg1, "Visibility", timeOut, "No Data To display Msg");
	}
	
	/**
	 * @deprecated Use {@link #getColumnHeads(PageName,Workspace)} instead
	 */
	public List<WebElement> getColumnHeads(){
		return getColumnHeads(PageName.FundsPage, Workspace.FundraisingWorkspace);
	}

	public List<WebElement> getColumnHeads(PageName pageName, Workspace workSpace){
		if(workSpace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
			return FindElements(driver, "//span[contains(@id,'myGridfundr-header-')]/span[3]", "column heads");	
		}else{
			return FindElements(driver, "//span[contains(@id,'myGrid-header-')]/span[3]", "column heads");		
		}
		
	}
	
	/**
	 * @deprecated Use {@link #getSortingArrow(PageName,Workspace)} instead
	 */
	public List<WebElement> getSortingArrow() {
		return getSortingArrow(PageName.FundsPage, Workspace.FundraisingWorkspace);
	}

	public List<WebElement> getSortingArrow(PageName pageNAME, Workspace workSpace) {
		if (workSpace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())) {
			return FindElements(driver, "//span[contains(@id,'myGridfundr-header-')]/span[3]/span",
					"sorting arrow for all columns");
		} else {
			return FindElements(driver, "//span[contains(@id,'myGrid-header-')]/span[3]/span",
					"sorting arrow for all columns");
		}
		}
	
	public List<WebElement> getColumnHeadsInvestor(){
		return FindElements(driver, "//span[contains(@id,'myGrid-header-')]/span[3]", "column heads Investor");
	}
	public List<WebElement> getSortingArrowInvestor() {
		return FindElements(driver, "//span[contains(@id,'myGrid-header-')]/span[3]/span", "sorting arrow for all columns Investor");
	}
	
	public WebElement getAlertHistoryLink(Workspace workspace,PageName pageName,int timeOut) {
		
		String workspaceSelector="";
		if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
			if(pageName.toString().equalsIgnoreCase(PageName.Object2Page.toString())) {
				workspaceSelector="pg:frm:ACTALTFR:AVTALT:theLinkPanel";
			}else {
				workspaceSelector = "page:formACTALTFR:ACTALTFR:AVTALT:theLinkPanel";
			}
		} else {
			if(pageName.toString().equalsIgnoreCase(PageName.Object2Page.toString())) {
				workspaceSelector="pg:frm:ACTALTINV:AVTALT:theLinkPanel";
			}else {
				workspaceSelector = "page:formACTALTINV:ACTALTINV:AVTALT:theLinkPanel";
			}
		}
		return isDisplayed(driver, FindElement(driver,"//span[@id='"+workspaceSelector+"']//a[@title='Alert History']", "alert history link", action.BOOLEAN, timeOut), "Visibility", timeOut, "alert history link");
	}
	
	public WebElement getAlertHistoryCrossIcon(Workspace workspace, int timeOut){
		String workspaceSelector="";
		if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
			workspaceSelector = "FR";
		} else {
			workspaceSelector = "INV";
		}
		return isDisplayed(driver, FindElement(driver, "//div[contains(@class,'ActivityAlerts_fancyboxACTALT"+workspaceSelector+"')]//a[@title='Close']", "alert history cross icon", action.BOOLEAN, timeOut), "Visibility", timeOut, "alert history cross icon");
	}
	
	
	public WebElement getFirmProfileUpdatedHeader(Workspace workspace,PageName pageName,int timeOut) {
		
		String workspaceSelector="";
			if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString()) || pageName.toString().equalsIgnoreCase(PageName.Object2Page.toString())) {
				if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector="ACTALTFR:AVTALT:rendrAccountHistory";
			}else {
				workspaceSelector = "ACTALTINV:AVTALT:rendrAccountHistory";
			}
		} else {
			if(pageName.toString().equalsIgnoreCase(PageName.HomePage.toString())) {
				workspaceSelector="page:formACTALT_new:ACTALT:AVTALT:rendrAccountHistory";
			}
		}
		return isDisplayed(driver, FindElement(driver,"//span[contains(@id,'"+workspaceSelector+"')]//div[@class='head_popup']", "alert history link", action.BOOLEAN, timeOut), "Visibility", timeOut, "Firm profile updtaed header");
	}
	
			
	public WebElement getFirmTextLabel(Workspace workspace,PageName pageName,int timeOut) {
			String workspaceSelector="";
					if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString()) || pageName.toString().equalsIgnoreCase(PageName.Object2Page.toString())) {
						if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
						workspaceSelector="formboxACTALTFR";
					}else {
						workspaceSelector = "formboxACTALTINV";
					}
				} else {
					if(pageName.toString().equalsIgnoreCase(PageName.HomePage.toString())) {
						workspaceSelector="formboxACTALT";
					}
				}
				return isDisplayed(driver, FindElement(driver,"(//div[@id='"+workspaceSelector+"']//strong[text()='Firm:']/..//following-sibling::td[@class='maxcharswordwrap'])[1]", "alert history link", action.BOOLEAN, timeOut), "Visibility", timeOut, "Firm value");
			}	
			
	@FindBy(xpath="//span[text()='Field']")
	private WebElement fieldLabelInFirmProfilePopup;

	/**
	 * @return the fieldLabelInFirmProfilePopup
	 */
	public WebElement getFieldLabelInFirmProfilePopup(int timeOut) {
		return isDisplayed(driver, fieldLabelInFirmProfilePopup, "Visibility", timeOut, "Field Label in firm profile popup");
	}
	@FindBy(xpath="//span[text()='New Value']")
	private WebElement valueLabelInFirmProfilePopup;

	/**
	 * @return the fieldLabelInFirmProfilePopup
	 */
	public WebElement getValueLabelInFirmProfilePopup(int timeOut) {
		return isDisplayed(driver, valueLabelInFirmProfilePopup, "Visibility", timeOut, "Value Label in firm profile popup");
	}	
			
	
	public WebElement getFirmProfileUpdateScrollBox(Workspace workspace,PageName pageName,int timeOut) {
		String workspaceSelector="";
				if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString()) || pageName.toString().equalsIgnoreCase(PageName.Object2Page.toString())) {
					if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
					workspaceSelector="grid_AccountProfileUpdatedACTALTFR-scroll-box";
				}else {
					workspaceSelector = "grid_AccountProfileUpdatedACTALTINV-scroll-box";
				}
			} else {
				if(pageName.toString().equalsIgnoreCase(PageName.HomePage.toString())) {
					workspaceSelector="grid_AccountProfileUpdatedACTALT-scroll-box";
				}
			}
			return isDisplayed(driver, FindElement(driver,"//span[@id='"+workspaceSelector+"']", "Firm Profile Updtae scrollBOx", action.BOOLEAN, timeOut), "Visibility", timeOut, "Firm Profile Updae scroll ox");
		}			
			
	public WebElement getGoToFirmButton(PageName pageName,Workspace workspace, int timeOut){
		String workspaceSelector="";
		if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString()) || pageName.toString().equalsIgnoreCase(PageName.Object2Page.toString())){
		if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
			workspaceSelector = "IDInvestorAccountACTALTFR";
		} else {
			workspaceSelector = "IDInvestorAccountACTALTINV";
		}
	}else{
		if(pageName.toString().equalsIgnoreCase(PageName.HomePage.toString())) {
			workspaceSelector="IDInvestorAccountACTALT";
	}
	}
		return isDisplayed(driver, FindElement(driver, "//div[@id='"+workspaceSelector+"']//a[text()='Go to Firm']", "Go to firm button", action.BOOLEAN, timeOut), "Visibility", timeOut, "Go to firm button");
	}
	
	
			
	public WebElement getFirmProfileUpdateCloseButton(Workspace workspace,PageName pageName,int timeOut) {
		String workspaceSelector="";
				if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString()) || pageName.toString().equalsIgnoreCase(PageName.Object2Page.toString())) {
					if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
					workspaceSelector="ACTALTFR:AVTALT:rendrAccountHistory";
				}else {
					workspaceSelector = "ACTALTINV:AVTALT:rendrAccountHistory";
				}
			} else {
				if(pageName.toString().equalsIgnoreCase(PageName.HomePage.toString())) {
					workspaceSelector="page:formACTALT_new:ACTALT:AVTALT:rendrAccountHistory";
				}
			}
			return isDisplayed(driver, FindElement(driver,"//span[contains(@id,'"+workspaceSelector+"')]//a[text()='Close']", "Firm Profile Updtae Close Button", action.BOOLEAN, timeOut), "Visibility", timeOut, "Firm Profile Update close button");
		}		
	
	public WebElement getFirmProfileUpdateCloseIcon(Workspace workspace,PageName pageName,int timeOut) {
		String workspaceSelector="";
				if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString()) || pageName.toString().equalsIgnoreCase(PageName.Object2Page.toString())) {
					if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
					workspaceSelector="IDInvestorAccountACTALTFR";
				}else {
					workspaceSelector = "IDInvestorAccountACTALTINV";
				}
			} else {
				if(pageName.toString().equalsIgnoreCase(PageName.HomePage.toString())) {
					workspaceSelector="IDInvestorAccountACTALT";
				}
			}
			return isDisplayed(driver, FindElement(driver,"//div[@id='"+workspaceSelector+"']//div[@class='head_popup']//a[@title='Close']", "Firm Profile Updtae Close icon", action.BOOLEAN, timeOut), "Visibility", timeOut, "Firm Profile Update close icon");
		}	
		
	public WebElement getContactProfileUpdatedHeader(Workspace workspace,PageName pageName,int timeOut) {
		
		String workspaceSelector="";
			if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString()) || pageName.toString().equalsIgnoreCase(PageName.Object2Page.toString())) {
				if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector="ACTALTFR:AVTALT:rendrContactHistory";
			}else {
				workspaceSelector = "ACTALTINV:AVTALT:rendrContactHistory";
			}
		} else {
			if(pageName.toString().equalsIgnoreCase(PageName.HomePage.toString())) {
				workspaceSelector="page:formACTALT_new:ACTALT:AVTALT:rendrContactHistory";
			}
		}
		return isDisplayed(driver, FindElement(driver,"//span[contains(@id,'"+workspaceSelector+"')]//div[@class='head_popup']", "Contact Profile header", action.BOOLEAN, timeOut), "Visibility", timeOut, "Contact profile updtaed header");
	}
	
	public WebElement getFirmTextLabelInContactProfileUpdated(Workspace workspace,PageName pageName,int timeOut) {
		String workspaceSelector="";
				if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString()) || pageName.toString().equalsIgnoreCase(PageName.Object2Page.toString())) {
					if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
					workspaceSelector="formboxACTALTFR";
				}else {
					workspaceSelector = "formboxACTALTINV";
				}
			} else {
				if(pageName.toString().equalsIgnoreCase(PageName.HomePage.toString())) {
					workspaceSelector="formboxACTALT";
				}
			}
			return isDisplayed(driver, FindElement(driver,"(//div[@id='"+workspaceSelector+"']//strong[text()='Firm:']/..//following-sibling::td[@class='maxcharswordwrap'])[2]", "Firm label in contact profile updtaed", action.BOOLEAN, timeOut), "Visibility", timeOut, "Firm value");
		}
	public WebElement getContactTextLabelInContactProfileUpdated(Workspace workspace,PageName pageName,int timeOut) {
		String workspaceSelector="";
				if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString()) || pageName.toString().equalsIgnoreCase(PageName.Object2Page.toString())) {
					if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
					workspaceSelector="formboxACTALTFR";
				}else {
					workspaceSelector = "formboxACTALTINV";
				}
			} else {
				if(pageName.toString().equalsIgnoreCase(PageName.HomePage.toString())) {
					workspaceSelector="formboxACTALT";
				}
			}
			return isDisplayed(driver, FindElement(driver,"(//div[@id='"+workspaceSelector+"']//strong[text()='Contact:']/..//following-sibling::td[@class='maxcharswordwrap'])[1]", "Contact label in contact profile updtaed", action.BOOLEAN, timeOut), "Visibility", timeOut, "Contact value");
		}
	public WebElement getContactProfileUpdateScrollBox(Workspace workspace,PageName pageName,int timeOut) {
		String workspaceSelector="";
				if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString()) || pageName.toString().equalsIgnoreCase(PageName.Object2Page.toString())) {
					if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
					workspaceSelector="grid_ContactProfileUpdatedACTALTFR-scroll-box";
				}else {
					workspaceSelector = "grid_ContactProfileUpdatedACTALTINV-scroll-box";
				}
			} else {
				if(pageName.toString().equalsIgnoreCase(PageName.HomePage.toString())) {
					workspaceSelector="grid_ContactProfileUpdatedACTALT-scroll-box";
				}
			}
			return isDisplayed(driver, FindElement(driver,"//span[@id='"+workspaceSelector+"']", "Contact Profile Updtae scrollBOx", action.BOOLEAN, timeOut), "Visibility", timeOut, "Contact Profile Updae scroll ox");
		}	
	

	public WebElement getGoToContactButton(PageName pageName,Workspace workspace, int timeOut){
		String workspaceSelector="";
		if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString()) || pageName.toString().equalsIgnoreCase(PageName.Object2Page.toString())) {
		if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
			workspaceSelector = "IDInvestorContactACTALTFR";
		} else {
			workspaceSelector = "IDInvestorContactACTALTINV";
		}
		}else{
			if(pageName.toString().equalsIgnoreCase(PageName.HomePage.toString())) {
				workspaceSelector="IDInvestorContactACTALT";
			}
		}
		return isDisplayed(driver, FindElement(driver, "//div[@id='"+workspaceSelector+"']//a[text()='Go to Contact']", "Go to Contact button", action.BOOLEAN, timeOut), "Visibility", timeOut, "Go to Contact button");
	}
	
	public WebElement getContactProfileUpdateCloseButton(Workspace workspace,PageName pageName,int timeOut) {
		String workspaceSelector="";
				if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString()) || pageName.toString().equalsIgnoreCase(PageName.Object2Page.toString())) {
					if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
					workspaceSelector="ACTALTFR:AVTALT:rendrContactHistory";
				}else {
					workspaceSelector = "ACTALTINV:AVTALT:rendrContactHistory";
				}
			} else {
				if(pageName.toString().equalsIgnoreCase(PageName.HomePage.toString())) {
					workspaceSelector="page:formACTALT_new:ACTALT:AVTALT:rendrContactHistory";
				}
			}
			return isDisplayed(driver, FindElement(driver,"//span[contains(@id,'"+workspaceSelector+"')]//a[text()='Close']", "Contact Profile Updtae Close Button", action.BOOLEAN, timeOut), "Visibility", timeOut, "Contact Profile Update close button");
		}		
	
	public WebElement getContactProfileUpdateCloseIcon(Workspace workspace,PageName pageName,int timeOut) {
		String workspaceSelector="";
				if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString()) || pageName.toString().equalsIgnoreCase(PageName.Object2Page.toString())) {
					if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
					workspaceSelector="ACTALTFR:AVTALT:rendrContactHistory";
				}else {
					workspaceSelector = "ACTALTINV:AVTALT:rendrContactHistory";
				}
			} else {
				if(pageName.toString().equalsIgnoreCase(PageName.HomePage.toString())) {
					workspaceSelector="page:formACTALT_new:ACTALT:AVTALT:rendrContactHistory";
				}
			}
			return isDisplayed(driver, FindElement(driver,"//span[contains(@id,'"+workspaceSelector+"')]//div[@class='head_popup']//a[@title='Close']", "Contact Profile Updtae Close icon", action.BOOLEAN, timeOut), "Visibility", timeOut, "Contact Profile Update close icon");
		}	
	
	public WebElement getAlertHistoryScrollbox(Workspace workspace, int timeOut){
		String workspaceSelector = "";
		if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
		workspaceSelector = "FR";
		} else {
		workspaceSelector = "INV";
		}
	return isDisplayed(driver, FindElement(driver, "//span[@id='myGridACTALT"+workspaceSelector+"-scroll-box']", "Alert history popup scrollbox", action.BOOLEAN, timeOut), "Visibility", timeOut, "Alert history popup Scroll box");
	}
	
	@FindBy(xpath="//span[text()='Date']//span")
	private WebElement dateSortIcon;

	/**
	 * @return the dateSortIcon
	 */
	public WebElement getDateSortIcon(int timeOut) {
		return isDisplayed(driver, dateSortIcon, "Visibility", timeOut, "Date sort icon");
	}	
	
	@FindBy(xpath="//span[text()='Activity Type']//span")
	private WebElement activityTypeSortIcon;

	/**
	 * @return the dateSortIcon
	 */
	public WebElement getActivityTypeSortIcon(int timeOut) {
		return isDisplayed(driver, activityTypeSortIcon, "Visibility", timeOut, "activity Type sort icon");
	}
	
	public List<WebElement> getActivityTypeColumnValue(PageName pageName,Workspace workspace){		
		if(pageName.toString().equalsIgnoreCase(PageName.FundsPage.toString()) || pageName.toString().equalsIgnoreCase(PageName.Object2Page.toString())){
			String workspaceSelector = "";
			if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
				workspaceSelector = "FR";
				} else {
				workspaceSelector = "INV";
				}
			String xpath ="//span[@id='myGridACTALT"+workspaceSelector+"-rows']//span[contains(@id,'myGridACTALT"+workspaceSelector+"-cell-0-')]";
			return FindElements(driver, xpath, "Activity type Column Value");
		}else{
			String xpath ="//span[@id='myGridACTALT-rows']//span[contains(@id,'myGridACTALT-cell-0-')]";
			return FindElements(driver, xpath, "Activity type Column Value");
		}
		
	}	
	
	@FindBy(xpath="//span[text()='Workspace']/span")
	private WebElement workspaceSortIconOnHomePageAlert;

	/**
	 * @return the workspaceSortIconOnHomePageAlert
	 */
	public WebElement getWorkspaceSortIconOnHomePageAlert(int timeOut) {
		return isDisplayed(driver, workspaceSortIconOnHomePageAlert, "Visibility", timeOut, "workspace Sort Icon On Home Page Alert");
	}
	@FindBy(xpath="//span[text()='Workspace']")
	private WebElement workspaceLabelOnHomePageAlert;

	/**
	 * @return the workspaceSortIconOnHomePageAlert
	 */
	public WebElement getWorkspaceLabelOnHomePageAlert(int timeOut) {
		return isDisplayed(driver, workspaceLabelOnHomePageAlert, "Visibility", timeOut, "workspace Label On Home Page Alert");
	}
	
	@FindBy(xpath="//span[text()='Field']/span")
	private WebElement fieldLabelSortIconOnProfileUpdatedPopupAlert;

	/**
	 * @return the fieldLabelSortIconOnProfileUpdatedPopupAlert
	 */
	public WebElement getFieldLabelSortIconOnProfileUpdatedPopupAlert(int timeOut) {
		return isDisplayed(driver, fieldLabelSortIconOnProfileUpdatedPopupAlert, "Visibility", timeOut, "field Label Sort Icon On Profile Updated Popup Alert ");
	}
	
	@FindBy(xpath="//div[@id='fileNotFound']")
	private WebElement fileNotFoundErrorMessage;

	/**
	 * @return the fileNotFoundErrorMessage
	 */
	public WebElement getFileNotFoundErrorMessage(int timeOut) {
		return isDisplayed(driver, fileNotFoundErrorMessage, "Visibility", timeOut, "File Not found error Message");
	}
	
	@FindBy(xpath="//a[text()='Create New View']")
	private WebElement createViewLink;

	/**
	 * @return the createViewLink
	 */
	public WebElement getCreateViewLink(int timeOut) {
		return isDisplayed(driver, createViewLink, "Visibility", timeOut, "Create View Link");
	}
	
	@FindBy(xpath="//label[text()='View Name:']/../following-sibling::td//input")
	private WebElement viewName;

	/**
	 * @return the viewName
	 */
	public WebElement getViewName(int timeOut) {
		return isDisplayed(driver, viewName, "Visibility", timeOut, "View Name");
	}
	
	@FindBy(xpath="//label[text()='View Unique Name:']/../following-sibling::td//input")
	private WebElement viewUniqueName;

	/**
	 * @return the viewUniqueName
	 */
	public WebElement getViewUniqueName(int timeOut) {
		return isDisplayed(driver, viewUniqueName, "Visibility", timeOut, "View Unique Name");
	}
	
	@FindBy(xpath="//select[@id='fcol1']")
	private WebElement fieldDropDown;

	/**
	 * @return the fieldDropDown
	 */
	public WebElement getFieldDropDown(int timeOut) {
		return isDisplayed(driver, fieldDropDown, "Visibility", timeOut, "Field Drop Down");
	}
	
	@FindBy(xpath="//select[@id='fop1']")
	private WebElement viewOperatorDropDown;

	/**
	 * @return the viewOperatorDropDown
	 */
	public WebElement getViewOperatorDropDown(int timeOut) {
		return isDisplayed(driver, viewOperatorDropDown, "Visibility", timeOut, "View Operator Drop Down");
	}
	
	@FindBy(xpath="//input[@id='fval1']")
	private WebElement viewValueBox;

	/**
	 * @return the viewValueBox
	 */
	public WebElement getViewValueBox(int timeOut) {
		return isDisplayed(driver, viewValueBox, "Visibility", timeOut, "View Text Box");
	}
	
	@FindBy(xpath="//td[text()='Folder Structure']/following-sibling::td/div")
	private WebElement folderStructureInHub;

	/**
	 * @return the folderStructureInHub
	 */
	public WebElement getFolderStructureInHub(int timeOut) {
		return isDisplayed(driver, folderStructureInHub, "Visibility", timeOut, "Folder Structure in hub");
	}
	
	@FindBy(xpath="(//td[text()='Related Folder IDs']/following-sibling::td/div)[1]")
	private WebElement relatedFolderIds;

	/**
	 * @return the relatedFolderIds
	 */
	public WebElement getRelatedFolderIds(int timeOut) {
		return isDisplayed(driver, relatedFolderIds, "Visibility", timeOut, "");
	}
	
	@FindBy(xpath="(//td[text()='Related Folder IDs2']/following-sibling::td/div)[1]")
	private WebElement relatedFolderIds2;

	/**
	 * @return the folderStrucutreIds2
	 */
	public WebElement getRelatedFolderIds2(int timeOut) {
		return isDisplayed(driver, relatedFolderIds2, "Visibility", timeOut, "Folder Structure ids2");
	}
	
	@FindBy(xpath="//input[@id='phSearchInput']")
	private WebElement globalSearch;

	/**
	 * @return the globalSearch
	 */
	public WebElement getGlobalSearch(int timeOut) {
		return isDisplayed(driver, globalSearch, "Visibility", timeOut, "Global Search");
	}
	
	@FindBy(xpath="//input[@id='phSearchButton']")
	private WebElement globalSearchButton;

	/**
	 * @return the globalSearch
	 */
	public WebElement getGlobalSearchButton(int timeOut) {
		return isDisplayed(driver, globalSearchButton, "Visibility", timeOut, "Global Search Button");
	}
	
	@FindBy(xpath="//a[@title='Developer Console (New Window)']")
	private WebElement devConsoleLink;

	/**
	 * @return the devConsoleLink
	 */
	public WebElement getDevConsoleLink(int timeOut) {
		return isDisplayed(driver, devConsoleLink, "Visibility", timeOut, "Dev Console");
	}
	
	@FindBy(xpath="//span[text()='Execute']")
	private WebElement executeButton;

	/**
	 * @return the executeButton
	 */
	public WebElement getExecuteButton(int timeOut) {
		return isDisplayed(driver, executeButton, "Visibility", timeOut, "Execute Button");
	}
	
	@FindBy(xpath="//div[@id='recordscountACTALT']")
	private WebElement recordCountOnHomePage;
	
	/**
	 * @return the recordCountOnHomePage
	 */
	public WebElement getRecordCountOnHomePage(int timeOut) {
		return isDisplayed(driver, recordCountOnHomePage, "Visibility", timeOut, "Record Count");
	}
	
	@FindBy(xpath="(//a[@title='Show All'])[1]")
	private WebElement showAllLinkFWR;
	
	@FindBy(xpath="(//a[@title='Show All'])[2]")
	private WebElement showAllLinkINV;
	
	@FindBy(xpath="//a[@title='Show All']")
	private WebElement showAllLink;

	/**
	 * @return the showAllLink
	 */
	public WebElement getShowAllLink(Workspace workspace, int timeOut) {
		if(workspace!=null && workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
			return isDisplayed(driver, showAllLinkFWR, "Visibility", timeOut, "Show All Link");
		} else if(workspace!=null && workspace.toString().equalsIgnoreCase(Workspace.InvestorWorkspace.toString())) {
			return isDisplayed(driver, showAllLinkINV, "Visibility", timeOut, "Show All Link");
		} else {
			return isDisplayed(driver, showAllLink, "Visibility", timeOut, "Show All Link");
		}
	}
	
	public WebElement getRecordCountAlertHistoryPopUp(Workspace workspace, int timeOut){
		String workspaceSelector = "";
		if(workspace.toString().equalsIgnoreCase(Workspace.FundraisingWorkspace.toString())){
			workspaceSelector = "FR";
		} else {
			workspaceSelector = "INV";
		}
		return isDisplayed(driver, FindElement(driver, "//div[@id='recordscountACTALT"+workspaceSelector+"']", "Record Count", action.BOOLEAN, 30), "Visibility", timeOut, "record Count");
	}

	@FindBy(xpath="//img[@title='Next']/..")
	private WebElement nextImageonPage;

	/**
	 * @return the nextImageonPage
	 */
	public WebElement getNextImageonPage(int timeOut) {
		return isDisplayed(driver, nextImageonPage, "Visibility", timeOut, "Next Image");
	}
	
	@FindBy(xpath="//span[@id='gridDivRecords']")
	private WebElement investorRecordCount;

	/**
	 * @return the investorRecordCount
	 */
	public WebElement getInvestorRecordCount(int timeOut) {
		return isDisplayed(driver, investorRecordCount, "Visibility", timeOut, "Investor Files Record Count");
	}
	
	// PE
	
	@FindBy(xpath = "//li[@id='MoreTabs_Tab']")
	private WebElement moreTabIConClassic;
	

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
	
	@FindBy(xpath="//div[@class='forceListViewManagerGrid']//div[contains(@class,'uiScroller')]")
	private WebElement scrollBoxforPageGrid;

	/**
	 * @return the scrollBoxforPageGrid
	 */
	public WebElement getScrollBoxforPageGrid(String environment,String mode,int timeOut) {
		return isDisplayed(driver, scrollBoxforPageGrid, "Visibility", timeOut, "Scroll Box for Page Grid");
		}
	@FindBy(xpath="//button[@title='Refresh']")
	private WebElement refrshActivity;

	/**
	 * @return the scrollBoxforPageGrid
	 */
	public WebElement getrefrshActivity(String projectName,int timeOut) {
		return isDisplayed(driver, refrshActivity, "Visibility", timeOut, "refrshActivity");
		}
	
	@FindBy(xpath="//button[@title='Select List View']")
	private WebElement selectListIcon_Lighting;

	/**
	 * @return the selectListIcon_Lighting
	 */
	public WebElement getSelectListIcon(int timeOut) {
		return isDisplayed(driver, selectListIcon_Lighting, "Visibility", timeOut, "Select List Icon");
		}
	
	
	
	
	/**
	 * @return the select list label
	 */
	public WebElement getSelectListLabelLink(String labelName,int timeOut) {
		String xpath="//li[contains(@class,'slds-dropdown__item has-icon--left')]//span[text()='"+labelName+"']/..";
		WebElement ele=FindElement(driver, xpath, "select list label", action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "Select List label");
		}
	
	
	
	/* return all links of select list icon option list*/
	public List<WebElement> getAllLinkOfSelectListIconOption(String mode,String tabName,int timeOut) {
		String xpath;
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			
			xpath="//select[@id='fcf'][@title='View:']/option";
		}else{
			
			xpath="//ul[@aria-label='"+tabName+" | List Views']/li/a/span";
		}
		return FindElements(driver, xpath, "Select list icon link");
		}
	
	@FindBy(xpath="//input[contains(@name,'search-input')]")
	private WebElement searchIcon_Lighting;

	/**
	 * @return the searchIcon_Lighting
	 */
	public WebElement getSearchIcon_Lighting(int timeOut) {
		return isDisplayed(driver, searchIcon_Lighting, "Visibility", timeOut, "Search Icon Lighting");
	}
	
	
	@FindBy(xpath="//a[@class='switch-to-lightning']")
	private WebElement switchToLightingLink;

	/**
	 * @return the switchToLightingLink
	 */
	public WebElement getSwitchToLightingLink(int timeOut) {
		return isDisplayed(driver, switchToLightingLink, "Visibility", timeOut, "Switch To Lighting Link");
	}
	
	@FindBy(xpath="//label[@for='lksrch']/following-sibling::input[@name='lksrch']")
	private WebElement lookUpSearchTextBox;

	/**
	 * @return the lookUpSearchTextBox
	 */
	public WebElement getLookUpSearchTextBox(int timeOut) {
		return isDisplayed(driver, lookUpSearchTextBox, "Visibility", timeOut, "look up search text box");
	}
	
	@FindBy(xpath="//label[@for='lksrch']/following-sibling::input[@name='go']")
	private WebElement lookUpSearchGoBtn;

	/**
	 * @return the lookUpSearchGoBtn
	 */
	public WebElement getLookUpSearchGoBtn(int timeOut) {
		return isDisplayed(driver, lookUpSearchGoBtn, "Visibility", timeOut, "look up search go button");
	}
	
	@FindBy(xpath="//frame[@title='Search']")
	private WebElement lookUpSearchSectionFrame;

	/**
	 * @return the lookUpSearchSectionFrame
	 */
	public WebElement getLookUpSearchFrame(int timeOut) {
		return isDisplayed(driver, lookUpSearchSectionFrame, "Visibility", timeOut, "look up search frame");
	}
	
	@FindBy(xpath="//frame[@title='Results']")
	private WebElement lookUpResultFrame;

	/**
	 * @return the lookUpResultFrame
	 */
	public WebElement getLookUpResultFrame(int timeOut) {
		return isDisplayed(driver, lookUpResultFrame, "Visibility", timeOut, "look up result frame");
	}
	
	public WebElement getCreateFundRaisingBtn(String environment,String mode, PageName pageName, int timeOut) {
		WebElement ele=null;
		String xpath="";
		if(mode.toString().equalsIgnoreCase(Mode.Lightning.toString())) {
			xpath="button";
		}else {
			xpath="input";
		}
		return isDisplayed(driver, FindElement(driver, "//"+xpath+"[text()='Bulk Fundraising' or @title='Bulk Fundraising']", "Create Fundraising button on "+pageName, action.SCROLLANDBOOLEAN,timeOut), "Visibility",timeOut, "Create Fundraising button on "+pageName);
	}
	
	
	@FindBy(xpath="//div[@id='OnloadPopup']//a[@title='Close']/img")
	private WebElement selectFundPopUpCrossIcon;
	

	/**
	 * @return the selectFundPopUpCrossIcon
	 */
	public WebElement getSelectFundPopUpCrossIcon(int timeOut) {
		return isDisplayed(driver, selectFundPopUpCrossIcon, "Visibility", timeOut, "select fund pop up cross icon");
	}
	
	
	@FindBy(xpath="//span[@title='Deals Sourced']/..")
	private WebElement dataSourcedLink;
	
	
	
	/**
	 * @return the dataSourcedLink
	 */
	public WebElement getDataSourcedLink(int timeOut) {
		return isDisplayed(driver, dataSourcedLink, "Visibility", timeOut, "data source link");
	}
	
	
	

	@FindBy(xpath="//h3[text()='Activity History']/ancestor::div[@class='bRelatedList']//div[@class='pbBody']//tr//th")
	private WebElement activityHistoryNoRecordsToDisplayMsg_Classic;
	
	@FindBy(xpath="//div[contains(@class,'emptyPastActivities')]//span")
	private WebElement activityHistoryNoRecordsToDisplayMsg_Lighting;

	/**
	 * @return the getActivityHistoryNoRecordsToDisplay
	 */
	public WebElement getActivityHistoryNoRecordsToDisplay(String environment,String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			return isDisplayed(driver, activityHistoryNoRecordsToDisplayMsg_Classic, "Visibility", timeOut, "No Records to Display Classic");
		}else{
			return isDisplayed(driver, activityHistoryNoRecordsToDisplayMsg_Lighting, "Visibility", timeOut, "No Past Activity Lighting");
		}
		
	}
	
	@FindBy(xpath="//h3[text()='Open Activities']/ancestor::div[@class='bRelatedList']//div[@class='pbBody']//tr//th")
	private WebElement openActivityNoRecordsToDisplayMsg_Classic;
	
	@FindBy(xpath="//div[contains(@class,'openActivities')]//span")
	private WebElement openActivityNoRecordsToDisplayMsg_Lighting;

	/**
	 * @return the getOpenActivitiesNoRecordsToDisplay
	 */
	public WebElement getOpenActivitiesNoRecordsToDisplay(String environment,String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			return isDisplayed(driver, openActivityNoRecordsToDisplayMsg_Classic, "Visibility", timeOut, "No Records to Display Classic");
		}else{
			return isDisplayed(driver, openActivityNoRecordsToDisplayMsg_Lighting, "Visibility", timeOut, "No Open Activity Lighting");
		}
		
	}
	
	@FindBy(xpath="//h3[text()='Activities']/ancestor::div[@class='bRelatedList']//div[@class='pbBody']//tr//th")
	private WebElement activitiesGridNoRecordsToDisplayMsg_Classic;
	
	@FindBy(xpath="//span[text()='Activities']/following-sibling::span[@title='(0)']")
	private WebElement activitiesGridNoRecordsToDisplayMsg_Lighting;

	/**
	 * @return the getOpenActivitiesNoRecordsToDisplay
	 */
	public WebElement getActivitiesGridNoRecordsToDisplay(String environment,String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			return isDisplayed(driver, activitiesGridNoRecordsToDisplayMsg_Classic, "Visibility", timeOut, "No Records to Display Classic");
		}else{
			return isDisplayed(driver, activitiesGridNoRecordsToDisplayMsg_Lighting, "Visibility", timeOut, "No Activities Lighting");
		}
		
	}

	@FindBy(xpath="//h3[text()='Activities']/ancestor::div[@class='bRelatedList']//div[@class='pbBody']//tr//th")
	private WebElement relatedListWithCount_Classic;
	
	@FindBy(xpath="//span[text()='Activities']/following-sibling::span[@title='(0)']")
	private WebElement relatedListWithCount_Lighting;

	/**
	 * @return the getOpenActivitiesNoRecordsToDisplay
	 */
	public WebElement getRelatedListWithCount(String environment,String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			return isDisplayed(driver, relatedListWithCount_Classic, "Visibility", timeOut, "Related List");
		}else{
			return isDisplayed(driver, relatedListWithCount_Lighting, "Visibility", timeOut, "No Activities Lighting");
		}
		
	}
	
	public WebElement getCreateCommitmentsBtn(String environment,String mode, PageName pageName, int timeOut) {
		WebElement ele=null;
		String xpath="";
		if(mode.toString().equalsIgnoreCase(Mode.Lightning.toString())) {
			xpath="a";
		}else {
			xpath="input";
		}
		return isDisplayed(driver, FindElement(driver, "//"+xpath+"[@title='Create Commitments']", "Create Commitments button on "+pageName, action.BOOLEAN,timeOut), "Visibility",timeOut, "Create Commitments button on "+pageName);
	}
	
	@FindBy(xpath="//h2[@class='topName']")
	private WebElement labelHeaderText;
	
	@FindBy(xpath="//h1//span[@data-aura-class='uiOutputText']")
	private WebElement labelHeaderText_Lightning;

	/**
	 * @return the labelHeaderText
	 */
	public WebElement getLabelHeaderText(String environment,String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			return isDisplayed(driver, labelHeaderText_Lightning, "Visibility", timeOut, "label header text");
		}else {
			return isDisplayed(driver, labelHeaderText, "Visibility", timeOut, "label header text");
			
		}
	}
	
	@FindBy(xpath="//div[@id='TotalComm']/span")
	private WebElement totalCommitment;




	/**
	 * @return the totalCommitment
	 */
	public WebElement getTotalCommitment(int timeOut) {
		return isDisplayed(driver, totalCommitment, "Visibility", timeOut, "Total commitment");
	}

	@FindBy(xpath="//div[@id='TotalCapital']")
	private WebElement totalRecallableCapital;

	/**
	 * @return the totalRecallablecapital
	 */
	public WebElement getTotalRecallablecapital(int timeOut) {
		return isDisplayed(driver, totalRecallableCapital, "Visibility", timeOut, "Total recallableCapital");
	}
	
	@FindBy(xpath="//div[@id='RecallCapital']")
	private WebElement totalCapitalCalled;

	/**
	 * @return the totalCapitalCalled
	 */
	public WebElement getTotalCapitalCalled(int timeOut) {
		return isDisplayed(driver, totalCapitalCalled, "Visibility", timeOut, "Total Capital Called");
	}
	
	@FindBy(xpath="//span[text()='Amount']/preceding-sibling::input")
	private WebElement amountRadioButton;

	/**
	 * @return the amountRadioButton
	 */
	public WebElement getAmountRadioButton(int timeOut) {
		return isDisplayed(driver, amountRadioButton, "Visibility", timeOut, "Amount Radio button");
	}
	
	@FindBy(xpath="//span[text()='Percentage']/preceding-sibling::input")
	private WebElement percentageRadioButton;

	/**
	 * @return the percentageRadioButton
	 */
	public WebElement getPercentageRadioButton(int timeOut) {
		return isDisplayed(driver, percentageRadioButton, "Visibility", timeOut, "percentage Radio Button");
	}
	
	@FindBy(xpath="//td[text()='Capital Amount']/following-sibling::td/input[@id='txtCapitalAmt']")
	private WebElement capitalAmount;

	/**
	 * @return the capitalAmount
	 */
	public WebElement getCapitalAmount(int timeOut) {
		return isDisplayed(driver, capitalAmount, "Visibility", timeOut, "Capital Amount");
	}
	
	@FindBy(xpath="//td[text()='Capital Amount']/following-sibling::td/input[@id='txtCapitalPercent']")
	private WebElement capitalAmountPercent;

	/**
	 * @return the capitalAmountPercent
	 */
	public WebElement getCapitalAmountPercent(int timeOut) {
		return isDisplayed(driver, capitalAmountPercent, "Visibility", timeOut, "Capital Amount Percentage");
	}
	
	@FindBy(xpath="//td[text()='Management Fee']/following-sibling::td/input[@id='txtManagementFeeAmt']")
	private WebElement managemanetFeesAmt;

	/**
	 * @return the managemanetFeesAmt
	 */
	public WebElement getManagemanetFeesAmt(int timeOut) {
		return isDisplayed(driver, managemanetFeesAmt, "Visibility", timeOut, "Management Fees Amount");
	}
	
	@FindBy(xpath="//td[text()='Management Fee']/following-sibling::td/input[@id='txtManagementFeePercent']")
	private WebElement managementFeesPercent;

	/**
	 * @return the managementFeesPercent
	 */
	public WebElement getManagementFeesPercent(int timeOut) {
		return isDisplayed(driver, managementFeesPercent, "Visibility", timeOut, "Management Fees Percentage");
	}
	
	@FindBy(xpath="//td[text()='Other Fee']/following-sibling::td/input[@id='txtOtherFeeAmt']")
	private WebElement otherFeesAmt;

	/**
	 * @return the otherFeesAmt
	 */
	public WebElement getOtherFeesAmt(int timeOut) {
		return isDisplayed(driver, otherFeesAmt, "Visibility", timeOut, "Other Fees Amount");
	}
	
	@FindBy(xpath="//td[text()='Other Fee']/following-sibling::td/input[@id='txtOtherFeePercent']")
	private WebElement otherFeesPercentage;

	/**
	 * @return the otherFeesPercentage
	 */
	public WebElement getOtherFeesPercentage(int timeOut) {
		return isDisplayed(driver, otherFeesPercentage, "Visibility", timeOut, "Other Fees Percentage");
	}
	
	@FindBy(xpath="//td[text()='Capital Call']/following-sibling::td//span[contains(@id,'capitalcallId')]")
	private WebElement capitalCall;

	/**
	 * @return the capitalCall
	 */
	public WebElement getCapitalCall(int timeOut) {
		return isDisplayed(driver, capitalCall, "Visibility", timeOut, "Capital Call");
	}
	
	@FindBy(xpath="//td[text()='Call Date']/following-sibling::td//input")
	private WebElement drawDownCallDate;

	/**
	 * @return the callDate
	 */
	public WebElement getDrawDownCallDate(int timeOut) {
		return isDisplayed(driver, drawDownCallDate, "Visibility", timeOut, "Call Date");
	}
	
	@FindBy(xpath="//td[text()='Call Date']/following-sibling::td//input")
	private WebElement drawDownDueDate;

	/**
	 * @return the dueDate
	 */
	public WebElement getDrawDownDueDate(int timeOut) {
		return isDisplayed(driver, drawDownDueDate, "Visibility", timeOut, "Due Date");
	}
	
	
	
	@FindBy(xpath="//span[text()='Setup Capital Calls']")
	private WebElement setupCapitalCallsButton;

	/**
	 * @return the setupCapitalCallsButton
	 */
	public WebElement getSetupCapitalCallsButton(int timeOut) {
		return isDisplayed(driver, setupCapitalCallsButton, "Visibility", timeOut, "Setup Capital Call button");
	}
	
	@FindBy(xpath="//input[@id='txtTotalPercent']")
	private WebElement totalPercentage;

	/**
	 * @return the totalPercentage
	 */
	public WebElement getTotalPercentage(int timeOut) {
		return isDisplayed(driver, totalPercentage, "Visibility", timeOut, "Total Percentage text box");
	}
	
	@FindBy(xpath="//input[contains(@id,'txtCallDate')]/following-sibling::span")
	private WebElement todaysCallDate;

	/**
	 * @return the todaysCallDate
	 */
	public WebElement getTodaysCallDate(int timeOut) {
		return isDisplayed(driver, todaysCallDate, "Visibility", timeOut, "Call Date today");
	}
	
	@FindBy(xpath="//input[contains(@id,'txtDueDate')]/following-sibling::span")
	private WebElement todaysDueDate;

	/**
	 * @return the todaysDueDate
	 */
	public WebElement getTodaysDueDate(int timeOut) {
		return isDisplayed(driver, todaysDueDate, "Visibility", timeOut, "Due date today");
	}
	
	@FindBy(xpath="//input[contains(@id,'txtDueDate')]")
	private WebElement dueDateTextBox;

	/**
	 * @return the dueDateTextBox
	 */
	public WebElement getDueDateTextBox(int timeOut) {
		return isDisplayed(driver, dueDateTextBox, "Visibility", timeOut, "Due Date Text Box");
	}
	
	@FindBy(xpath="//div[contains(@class,'slds-template_iframe')]//iframe[@title='accessibility title']")
	private WebElement createFundraisingsFrame_Lighting;

	/**
	 * @return the createFundraisingsFrame_Lighting
	 */
	public WebElement getCreateFundraisingsFrame_Lighting(int timeOut) {
		return isDisplayed(driver, createFundraisingsFrame_Lighting, "Visibility", timeOut, "create fundraisings frame on lighting");
	}
	
	@FindBy(xpath="//tr[contains(@class,'dataRow')]")
	private List<WebElement> totalCapitalCalls;

	/**
	 * @return the totalCapitalCalls
	 */
	public List<WebElement> getTotalCapitalCalls() {
		return totalCapitalCalls;
	} 
	
	@FindBy(xpath="//span[text()='Generate Capital Calls']")
	private WebElement generateCapitalCallsButton;

	/**
	 * @return the generateCapitalCallsButton
	 */
	public WebElement getGenerateCapitalCallsButton(int timeOut) {
		return isDisplayed(driver, generateCapitalCallsButton, "Visibility", timeOut, "Generate capital call");
	}
	
	@FindBy(xpath="//span[text()='Target Commitments (mn)']/../following-sibling::div/span/span")
	private WebElement targetCommitmentAmount_Lightning;
	
	@FindBy(xpath="//td[text()='Target Commitments (mn)']/following-sibling::td/div")
	private WebElement targetCommitmentAmount_Classic;

	/**
	 * @return the targetCommitmentAmount
	 */
	public WebElement getTargetCommitmentAmount(String mode, int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Lightning.toString())){
			return isDisplayed(driver, targetCommitmentAmount_Lightning, "Visibility", timeOut, "Target Commitment Amount");
		} else {
			return isDisplayed(driver, targetCommitmentAmount_Classic, "Visibility", timeOut, "Target Commitment Amount");
		}
	}
	
	@FindBy(xpath="//span[text()='Total Commitments (mn)']/../following-sibling::div/span/span")
	private WebElement totalCommitmentsAmount_Lightning;
	
	@FindBy(xpath="//td[text()='Total Commitments (mn)']/following-sibling::td/div")
	private WebElement totalCommitmentAmount_Classic;

	/**
	 * @return the totalCommitmentsAmount_Lightning
	 */
	public WebElement getTotalCommitmentsAmount(String mode, int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Lightning.toString())){
			return isDisplayed(driver, totalCommitmentsAmount_Lightning, "Visibility", timeOut, "Total Commitment Amount");
		} else {
			return isDisplayed(driver, totalCommitmentAmount_Classic, "Visibility", timeOut, "Total Commitment Amount");
		}
	}
	
	@FindBy(xpath="//span[text()='Remaining Commitments (mn)']/../following-sibling::div/span/span")
	private WebElement remainingCommitmentAmount_Lighting;
	
	@FindBy(xpath="//td[text()='Remaining Commitments (mn)']/following-sibling::td/div")
	private WebElement remainingCommitmentAmount_Classic;

	/**
	 * @return the remainingCommitmentAmount_Lighting
	 */
	public WebElement getRemainingCommitmentAmount(String mode, int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Lightning.toString())){
			return isDisplayed(driver, remainingCommitmentAmount_Lighting, "Visibility", timeOut, "Remaining Commitment Amount");
		} else {
			return isDisplayed(driver, remainingCommitmentAmount_Classic, "Visibility", timeOut, "Remaining Commitment Amount");
		}
	}
	
	@FindBy(xpath="//span[text()='Total Capital Called (mn)']/../following-sibling::div/span/span")
	private WebElement totalCapitalCalled_Lightning;

	@FindBy(xpath="//td[text()='Total Capital Called (mn)']/following-sibling::td/div")
	private WebElement totalCapitalCalled_Classic;
	/**
	 * @return the totalCapitalCalled_lightning
	 */
	public WebElement getTotalCapitalCalled(String mode, int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Lightning.toString())){
			return isDisplayed(driver, totalCapitalCalled_Lightning, "Visibility", timeOut, "Total Commitment Called Amount");
		} else {
			return isDisplayed(driver, totalCapitalCalled_Classic, "Visibility", timeOut, "Total Commitment Called Amount");
		}
	}
	
	@FindBy(xpath="//span[text()='Total Recallable Capital (mn)']/../following-sibling::div/span/span")
	private WebElement totalRecallableCapitalAmount_Lightning;
	
	@FindBy(xpath="//td[text()='Total Recallable Capital (mn)']/following-sibling::td/div")
	private WebElement totalRecallableCapitalAmount_Classic;

	/**
	 * @return the totalRecallableCapitalAmount
	 */
	public WebElement getTotalRecallableCapitalAmount(String mode, int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Lightning.toString())){
			return isDisplayed(driver, totalRecallableCapitalAmount_Lightning, "Visibility", timeOut, "Total Recallable capital Amount");
		} else {
			return isDisplayed(driver, totalRecallableCapitalAmount_Classic, "Visibility", timeOut, "Total Recallable capital Amount");
		}
	}
	
	@FindBy(xpath="//button[@title='Personalize your nav bar']")
	private WebElement personalizePencilIcon;

	/**
	 * @return the personalizePencilIcon
	 */
	public WebElement getPersonalizePencilIcon(int timeOut) {
				return isDisplayed(driver, personalizePencilIcon, "Visibility", timeOut, "Personalize Pencil Icon");
	
	}
	
	@FindBy(xpath="//button[contains(text(),'Add More Items')]")
	private WebElement addMoreItemsLink;

	/**
	 * @return the addMoreItemsLink
	 */
	public WebElement getAddMoreItemsLink(int timeOut) {
				return isDisplayed(driver, addMoreItemsLink, "Visibility", timeOut, "Add More Items");
	
	}
	
	@FindBy(xpath="//div[@role='list']//a[text()='All']")
	private WebElement allAddLink;

	/**
	 * @return the allAddLink
	 */
	public WebElement getAllAddLink(int timeOut) {
				return isDisplayed(driver, allAddLink, "Visibility", timeOut, "Add More Items");
	
	}
	
	@FindBy(xpath="//button/span[contains(text(),'Add')]")
	private WebElement addNavButton;

	/**
	 * @return the addNavButton
	 */
	public WebElement getAddNavButton(int timeOut) {
				return isDisplayed(driver, addNavButton, "Visibility", timeOut, "Add Nav Button");
	
	}
	
	@FindBy(xpath="//div[@data-aura-class='oneEditMyNav']/../following-sibling::div//button/span[contains(text(),'Save')]")
	private WebElement tabSaveButton;

	/**
	 * @return the addNavButton
	 */
	public WebElement getTabSaveButton(int timeOut) {
				return isDisplayed(driver, tabSaveButton, "Visibility", timeOut, "tAB Save Button");
	
	}
	
	@FindBy(xpath = "//input[@value='Tag Documents']")
	private WebElement tagDocsButton;
	
	public WebElement gettagDocsButton( int timeOut) {
		return isDisplayed(driver, tagDocsButton, "Visibility", timeOut, "tagDocsButton");

}
	
	public WebElement getMoreTabIcon(String environment,String mode,int timeOut) {
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
		return isDisplayed(driver, moreTabIConClassic, "Visibility", timeOut, "More Tab ICon Classic");
		}else{
			return isDisplayed(driver, moreTabIConLighting, "Visibility", timeOut, "More Tab ICon Lighting");	
		}
	}
	/***************************************Activity Association**************************************/
	@FindBy(xpath="//a[text()='Users']/../button")
	private WebElement expandUserIcon_Lighting;
	/**
	 * @return the expandManageUserIcon
	 */
	public WebElement getExpandUserIcon(int timeOut) {
			return isDisplayed(driver, expandUserIcon_Lighting, "Visibility", timeOut, "Expand User Icon");
	}
	
	/////////////////////////////////////////////////// Activity Association /////////////////////////////
	
	@FindBy(xpath = "//span[contains(text(),'More')]/..//lightning-primitive-icon")
	private WebElement moreTabIConLighting;
	
	public WebElement getMoreTabIcon(String projectName,int timeOut) {
		return isDisplayed(driver, moreTabIConLighting, "Visibility", timeOut, "More Tab ICon Lighting");	
	}
	
	@FindBy(xpath="//*[@title='New']")
	private WebElement newButtonLighting;
	
	/**
	 * @return the newButton
	 */
	public WebElement getNewButton(String projectName,int timeOut) {
			ThreadSleep(5000);
			return newButtonLighting;	
		
		
	}
	
	
	@FindBy(xpath="//div[contains(@class,'actionsContainer') or contains(@class,'forceModalActionContainer')]//button/span[text()='Save']")
	private WebElement saveButtonLighting1;

	/**
	 * @return the saveButton
	 */
	public WebElement getSaveButton(String projectName,int timeOut) {
		
			return isDisplayed(driver, saveButtonLighting1, "Visibility", timeOut, "Save Button Lighting");
		}
		
	
	@FindBy(xpath = "//span[text()='Next']")
	private WebElement nextButton1;

	/**
	 * @return the continueButton
	 */
	public WebElement getContinueOrNextButton(String projectName,int timeOut) {

			return isDisplayed(driver, nextButton1, "Visibility", timeOut, "Next Button");
	
	}
	@FindBy(xpath = "//div[@title='New Task with Multiple Associations']/..")
	private WebElement buttonNewTaskMultiple;
	
	/**
	 * @return the saveButton
	 */
	public WebElement getButtonNewTaskMultipleAssociations(String projectName,int timeOut) {
		
			return isDisplayed(driver, buttonNewTaskMultiple, "Visibility", timeOut, "buttonNewTaskMultiple");
		}
	
	@FindBy(xpath = "//div[@title='Log a Call with Multiple Associations']/..")
	private WebElement buttonLogCallMultiple;
	
	public WebElement getButtonLogCallMultiple(String projectName,int timeOut) {
		
		return isDisplayed(driver, buttonLogCallMultiple, "Visibility", timeOut, "buttonLogCallMultiple");
	}

	
	public WebElement getstatusDropdownInCreateNewTask(String projectName,int timeOut) {
		String xpath=returnXpathOfDropdownInTaskPage(PageLabel.Status.toString());
		return isDisplayed(driver, FindElement(driver, xpath, "Status", action.SCROLLANDBOOLEAN,
				timeOut), "Visibility", timeOut, "statusDropdownInCreateNewTask");
	}
	
	@FindBy(xpath = "//div[@title='User']/span/span[2]/span")
	private WebElement createNewTaskAssignedTo;
	
	public WebElement getcreateNewTaskAssignedTo(String projectName,int timeOut) {

		return isDisplayed(driver, createNewTaskAssignedTo, "Visibility", timeOut, "createNewTaskAssignedTo");
	}

	/*@FindBy(xpath = "//label[text()='Subject']/..//input")
	private WebElement createNewTaskSubjectTextbox;
	
	public WebElement getcreateNewTaskSubjectTextbox(String projectName,int timeOut) {

		return isDisplayed(driver, createNewTaskSubjectTextbox, "Visibility", timeOut, "createNewTaskSubjectTextbox");
	}
	*/
	@FindBy(xpath = "//label[text()='Name']/..//span//input")
	private WebElement nameTextBoxInNewTask;
	
	public WebElement getnameTextBoxInNewTask(String projectName,int timeOut) {

		return isDisplayed(driver, nameTextBoxInNewTask, "Visibility", timeOut, "nameTextBoxInNewTask");
	}
	
	@FindBy(xpath = "//span[text()='Due Date']/..//following-sibling::div//input")
	private WebElement dueDateTextBoxInNewTask;
	
	@FindBy(xpath = "//label[text()='Due Date']/..//input")
	private WebElement dueDateTextBoxInNewTask1;
	
	public WebElement getdueDateTextBoxInNewTask(String projectName,int timeOut) {
		WebElement ele = isDisplayed(driver, dueDateTextBoxInNewTask, "Visibility", timeOut, "dueDateTextBoxInNewTask");;
		if (ele!=null) {
			return ele;
		} else {

		}
		return isDisplayed(driver, dueDateTextBoxInNewTask1, "Visibility", timeOut, "dueDateTextBoxInNewTask");
	}
	
	public String returnXpathOfDropdownInTaskPage(String field) {
		
		return "//label[text()='"+field+"']/following-sibling::div//input";
	}
	
	public WebElement getmeetingTypeDropdown(String projectName,int timeOut) {
		String xpath=returnXpathOfDropdownInTaskPage(PageLabel.Meeting_Type.toString());
		return isDisplayed(driver, FindElement(driver, xpath,"Meeting Type" ,
				action.SCROLLANDBOOLEAN, timeOut), "Visibility", timeOut, "meetingTypeDropdown");
	}
	public WebElement getPriorityDropdown(String projectName,int timeOut) {
		String xpath=returnXpathOfDropdownInTaskPage(PageLabel.Priority.toString());
		return isDisplayed(driver, FindElement(driver, xpath,"priority" ,
				action.SCROLLANDBOOLEAN, timeOut), "Visibility", timeOut, "priority Dropdown");
	}
	public List<WebElement> relatedAssociations(String projectName) {
	return FindElements(driver, "//div[@id='relatedAssociation']//div[contains(@id,'objDropdown')]/div", "All Commitments Custom Fields");
	}
	
	@FindBy(xpath = "//*[text()='Comments']/../..//textarea")
	private WebElement commentsTextBox;
	
	public WebElement getcommentsTextBox(String projectName,int timeOut) {

		return isDisplayed(driver, commentsTextBox, "Visibility", timeOut, "commentsTextBox");
	}

	@FindBy(xpath = "//div[@id='relatedAssociation']//input")
	private WebElement relationAssociationsTextbox;
	
	public WebElement getrelatedAssociationsTextbox(String projectName,int timeOut) {

		return isDisplayed(driver, relationAssociationsTextbox, "Visibility", timeOut, "relationAssociationsTextbox");
	}
	
	@FindBy(xpath = "//div[contains(@class,'dropdownButton')]//button")
	private WebElement relatedAssociationsdropdownButton;
	
	public WebElement getrelatedAssociationsdropdownButton(String projectName,int timeOut) {
		
		return isDisplayed(driver, relatedAssociationsdropdownButton, "Visibility", timeOut, "relatedAssociationsdropdownButton");
	}
	
	
	
	@FindBy(xpath = "//button[@title='Save']/preceding-sibling::button[@title='Cancel']")
	private WebElement cancelButton1;
	
	public WebElement getcancelButton(String projectName,int timeOut) {

		return isDisplayed(driver, cancelButton1, "Visibility", timeOut, "cancelButton");
	}

	@FindBy(xpath = "//a[@title='Refresh']")
	private WebElement refresh;
	
	public WebElement getrefreshButton(String projectName,int timeOut) {

		return isDisplayed(driver, refresh, "Visibility", timeOut, "refresh");
	}
	
	@FindBy(xpath="//div[@class='cActivityTab']/following-sibling::iframe")
	private WebElement meetingOrActivitiesFrame;

	/**
	 * @return the meetingFrameOnContactPage
	 */
	public WebElement getmeetingOrActivitiesFrame(String projectName,int timeOut) {
		return isDisplayed(driver, meetingOrActivitiesFrame, "Visibility", timeOut, "meetingOrActivitiesFrame");
	}
	
	public List<WebElement> createdRelatedAssociationsOnWindow(String projectName) {
	List<WebElement> e=FindElements(driver, "//div[@id='RelatedAsspopupID']//a", "created related associations");
	return e;
	}
	
	
	@FindBy(xpath="//img[@id='lefttorightenableActivitySearchpage']")
	private WebElement moveToSelectedColToDisplay;
	
	public WebElement getmoveToSelectedColToDisplay(String projectName,int timeOut) {
		return isDisplayed(driver, moveToSelectedColToDisplay, "Visibility", timeOut, "moveToSelectedColToDisplay");
	}
	
	@FindBy(xpath="//img[@id='righttoleftenableActivitySearchpage']")
	private WebElement moveToAvailableColToDisplay;
	
	public WebElement getmoveToAvailableColToDisplay(String projectName,int timeOut) {
		return isDisplayed(driver, moveToAvailableColToDisplay, "Visibility", timeOut, "moveToAvailableColToDisplay");
	}
	public WebElement getOkButtonRAorComment(String projectName,String relatedOrComment,int timeOut) {
		String id="",xpath="";
		if (relatedOrComment.equalsIgnoreCase("Comment"))
			id="commentpopupID";
		else if (relatedOrComment.equalsIgnoreCase("CN"))
			id="conDIVid";
		else id="RelatedAsspopupID";
		xpath="//div[@id='"+id+"']//following-sibling::div//button[text()='OK']";
		return isDisplayed(driver, FindElement(driver, xpath, "ok button", action.BOOLEAN, timeOut), 
				"Visibility", timeOut, "createdRelAssocOkButton");
	}
	
	
	public WebElement getCrossIconRAorComment(String projectName,String relatedOrComment,int timeOut) {
		String id="",xpath="";
		if (relatedOrComment.equalsIgnoreCase("Comment"))
			id="page:form:commentid";
		else if (relatedOrComment.equalsIgnoreCase("CN"))
			id="page:form:contactpopupid";
		else id="page:form:RleatedAssociationpopupid";
		xpath="//span[@id='"+id+"']//a[@title='Close']";
		return isDisplayed(driver,  FindElement(driver, xpath, "cross button", action.BOOLEAN, timeOut)
				, "Visibility", timeOut, "createdRelAssocCrossIcon");
	}
	
	@FindBy(xpath = "//span[@id='page:form:field1']")
	private WebElement commentsTextOnPopup;
	
	public WebElement getcommentsTextOnPopup(String projectName,int timeOut) {
		return isDisplayed(driver, commentsTextOnPopup, "Visibility", timeOut, "commentsTextOnPopup");
	}
	
	
	public WebElement geDropdownOnTaskPopUp(String projectName,String label,action action,int timeOut) {
		String xpath=returnXpathOfDropdownInTaskPage(label);
		WebElement ele =  FindElement(driver, xpath,label ,action, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "Drop Down : "+label);
	}
	

	
	public WebElement getDropdownOnTaskPopUp(String projectName,PageName pageName,String label,action action,int timeOut) {
		String xpath="";
		label = label.replace("_", " ");
		if (PageName.TaskPage.toString().equals(pageName.toString()) || PageName.NewEventPopUp.toString().equals(pageName.toString()))
				//||PageName.CallPopUp.toString().equals(pageName.toString()) || PageName.Meet.toString().equals(pageName.toString()))
			{
			xpath = "//span[text()='"+label+"']/../following-sibling::div//a";
		} else {
			 xpath="//label[text()='"+label+"']/following-sibling::div//input";
		}
		
		WebElement ele =  FindElement(driver, xpath,label ,action, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "Drop Down : "+label);
	}
	


	@FindBy(xpath = "//a[@title='Columns to Display']")
	private WebElement wrenchIcon;
	
	public WebElement getwrenchIcon(String projectName,int timeOut) {
		return isDisplayed(driver, wrenchIcon, "Visibility", timeOut, "wrenchIcon");
	}
	
	@FindBy(xpath = "//div[@id='displayFilterText1']//img")
	private WebElement advancedFilterImg;
	
	public WebElement getadvancedFilterImg(String projectName,int timeOut) {
		return isDisplayed(driver, advancedFilterImg, "Visibility", timeOut, "advancedFilterImg");
	}

	@FindBy(xpath = "(//*[contains(@title,'more action')])[1]")
	private WebElement showMoreActionButton;
	
	public WebElement getShowMoreActionButton(String projectName,int timeOut) {

		return isDisplayed(driver, showMoreActionButton, "Visibility", timeOut, "show More Action Button");
	}
	
	@FindBy(xpath = "//select[@id='rangeid']")
	private WebElement rangeDropdown;
	
	public WebElement getrangeDropdown(String projectName,int timeOut) {

		return isDisplayed(driver, rangeDropdown, "Visibility", timeOut, "rangeDropdown");
	}
	
	@FindBy(xpath = "//input[@id='checkActivityid']")
	private WebElement activitiesCheckbox;
	
	public WebElement getactivitiesCheckbox(String projectName,int timeOut) {

		return isDisplayed(driver, activitiesCheckbox, "Visibility", timeOut, "activitiesCheckbox");
	}
	
	@FindBy(xpath = "//input[@id='checkAttchmentid']")
	private WebElement attachmentCheckbox;
	
	public WebElement getattachmentCheckbox(String projectName,int timeOut) {

		return isDisplayed(driver, attachmentCheckbox, "Visibility", timeOut, "attachmentCheckbox");
	}
	
	@FindBy(xpath = "//input[@id='SearchContent']//preceding-sibling::img[@title='Search']")
	private WebElement searchIconActivities;
	
	public WebElement getsearchIconActivities(String projectName,int timeOut) {

		return isDisplayed(driver, searchIconActivities, "Visibility", timeOut, "searchIcon");
	}
	
	@FindBy(xpath = "//input[@id='SearchContent']")
	private WebElement searchTextbox;
	
	public WebElement getsearchTextboxActivities(String projectName,int timeOut) {

		return isDisplayed(driver, searchTextbox, "Visibility", timeOut, "searchTextbox");
	}
	
	@FindBy(xpath = "//span[@id='ClearSearch']//img")
	private WebElement searchCrossActivities;
	
	public WebElement getsearchCrossActivities(String projectName,int timeOut) {

		return isDisplayed(driver, searchCrossActivities, "Visibility", timeOut, "searchCross");
	}
	
	@FindBy(xpath = "//span[contains(@class,'toastMessage')]")
	private WebElement createdConfirmationMsg;
	
	public WebElement getCreatedConfirmationMsg(String projectName,int timeOut) {

		return isDisplayed(driver, createdConfirmationMsg, "Visibility", timeOut, "Created Confirmation Msg");
	}
	

	@FindBy(xpath="//button[text()='Cancel']")
	private WebElement cancelButton;

	/**
	 * @return the cancelButton
	 */
	public WebElement getCancelButton(String projectName,int timeOut) {
		List<WebElement> eleList = FindElements(driver, "//button[text()='Cancel']", "cancelButton");
		for (int i = 0; i < eleList.size(); i++) {
			WebElement ele = eleList.get(i);
			ele=isDisplayed(driver, ele, "Visibility", timeOut, "cancelButton");
			if (ele!=null) {
				return ele;
			} else {
				if (i==eleList.size()-1) {
					return ele;
				}
			}
		}
		
		return isDisplayed(driver, cancelButton, "Visibility", timeOut, "Cancel");
	}
	
	@FindBy(xpath = "//button[@title='Close this window']")
	private WebElement crossIcon;
	
	public WebElement getcrossIcon(String projectName,int timeOut) {
		List<WebElement> eleList = FindElements(driver, "//button[@title='Close this window']", "crossIcon");
		for (int i = 0; i < eleList.size(); i++) {
			WebElement ele = eleList.get(i);
			ele=isDisplayed(driver, ele, "Visibility", timeOut, "crossIcon");
			if (ele!=null) {
				return ele;
			} else {
				if (i==eleList.size()-1) {
					return ele;
				}
			}
		}
		
		return isDisplayed(driver, crossIcon, "Visibility", timeOut, "crossIcon");
	}
	
	@FindBy(xpath="//div[@class='cActivityTab']/following-sibling::iframe")
	private WebElement meetingFrameOnContactPage;

	/**
	 * @return the meetingFrameOnContactPage
	 */
	public WebElement getMeetingFrameOnContactPage(String projectName,int timeOut) {
		return isDisplayed(driver, meetingFrameOnContactPage, "Visibility", timeOut, "Meeting frame on Contact Page");
	}
	
	@FindBy(xpath="//div[@title='New Meeting']")
	private WebElement newMeetingButton;

	/**
	 * @return the newMeetingButton
	 */
	public WebElement getNewMeetingButton(String projectName,int timeOut) {
		return isDisplayed(driver, newMeetingButton, "Visibility", timeOut, "New Meeting Button on Contact Page");
	}

	@FindBy(xpath = "//a[@id='_rtdeActivitySearchpage']/preceding-sibling::button")
	private WebElement cancelButtonOnColumnsToDisplay;
	
	public WebElement getCancelButtonOnColumnsToDisplay(String projectName,int timeOut) {

		return isDisplayed(driver, cancelButtonOnColumnsToDisplay, "Visibility", timeOut, "cancelButtonOnColumnsToDisplay");
	}
	
	
	public WebElement getsearchTextboxColToDisplay(String projectName,int timeOut) {
		String xpath="//input[@id='searchcon_grid1ActivitySearchpage']";
		return isDisplayed(driver, FindElement(driver, xpath, "searchTextbox", action.SCROLLANDBOOLEAN, timeOut), "Visibility", timeOut, "searchTextbox");
	}
	@FindBy(xpath = "//div[@id='wrenchpopup1ActivitySearchpage']//img[@title='Search']")
	private WebElement searchIconColToDisplay;
	
	public WebElement getsearchIconColToDisplay(String projectName,int timeOut) {

		return isDisplayed(driver, searchIconColToDisplay, "Visibility", timeOut, "searchIcon");
	}
	
	@FindBy(xpath = "//span[@id='clearsearchenb1ActivitySearchpage']//div[@title='Clear']//img")
	private WebElement searchCrossColToDisplay;
	
	public WebElement getsearchCrossColToDisplay(String projectName,int timeOut) {

		return isDisplayed(driver, searchCrossColToDisplay, "Visibility", timeOut, "searchCross");
	}
	
	@FindBy(id = "msgBlankId")
	private WebElement pleaseEnterAValueMsg;
	
	public WebElement getpleaseEnterAValueMsg(String projectName,int timeOut) {

		return isDisplayed(driver, pleaseEnterAValueMsg, "Visibility", timeOut, "pleaseEnterAValueMsg");
	}
	
	@FindBy(xpath = "//a[@id='closebtn']")
	private WebElement crossIconColToDisplay;
	
	public WebElement getcrossIconColToDisplay(String projectName,int timeOut) {

		return isDisplayed(driver, crossIconColToDisplay, "Visibility", timeOut, "crossIconColToDisplay");
	}
	
	@FindBy(xpath = "//div[@id='displayFilterText1']//img")
	private WebElement advancedFilterToggle;
	
	public WebElement getadvancedFilterToggle(String projectName,int timeOut) {

		return isDisplayed(driver, advancedFilterToggle, "Visibility", timeOut, "advancedFilterToggle");
	}
	
	@FindBy(xpath="//a[@title='Search Activities/Attachments Settings']//img")
	private WebElement searchActAttach;
	
	public WebElement getsearchActAttach(String projectName,int timeOut) {

		return isDisplayed(driver, searchActAttach, "Visibility", timeOut, "searchActAttach");
	}
	
	
	public List<WebElement> getfilterHeadings(String projectName,int timeOut) {

		return FindElements(driver, "//div[@id='SearchGridDiv1']//h3", "filterHeadings");
	}
	
	@FindBy(xpath = "//input[@name='page:form:advancefilterid:adv_cmp:j_id60']")
	private WebElement insuffOKButton;
	
	public WebElement getinsuffOKButton(String projectName,int timeOut) {

		return isDisplayed(driver, insuffOKButton, "Visibility", timeOut, "insuffOKButton");
	}
	
	@FindBy(xpath = "//div[contains(@class,'InsufficientPermissions')]//a[@title='Close']")
	private WebElement insuffCrossButton;
	
	public WebElement getinsuffCrossButton(String projectName,int timeOut) {

		return isDisplayed(driver, insuffCrossButton, "Visibility", timeOut, "insuffCrossButton");
	}
	
	
	public WebElement getrevertToDefaultColToDisplay(String projectName,EnableDisable ed,int timeOut) {
		String xpath="",e="";
		if (ed==EnableDisable.Disable)
			e="active";
		xpath="//a[@id='_rtde"+e+"ActivitySearchpage']";
		return isDisplayed(driver, FindElement(driver, xpath, "revertToDefault", action.SCROLLANDBOOLEAN, timeOut), "Visibility", timeOut, "revertToDefault");
	}
	
	public WebElement getApplyColToDisplay(String projectName,EnableDisable ed,int timeOut) {
		String xpath="",e="";
		if (ed==EnableDisable.Disable)
			e="deactve";
		xpath="//a[@id='_apply"+e+"ActivitySearchpage']";
		return isDisplayed(driver, FindElement(driver, xpath, "Apply", action.SCROLLANDBOOLEAN, timeOut), "Visibility", timeOut, "Apply");
	}
	
	public WebElement getMoveUpColToDisplay(String projectName,EnableDisable ed,int timeOut) {
		String xpath="",e="";
		if (ed==EnableDisable.Disable)
			e="disable";
		else
			e="enable";
		xpath="//img[@id='moveup"+e+"ActivitySearchpage']";
		return isDisplayed(driver, FindElement(driver, xpath, "Apply", action.SCROLLANDBOOLEAN, timeOut), "Visibility", timeOut, "Apply");
	}
	
	public WebElement getMoveDownColToDisplay(String projectName,EnableDisable ed,int timeOut) {
		String xpath="",e="";
		if (ed==EnableDisable.Disable)
			e="disable";
		else
			e="enable";
		xpath="//img[@id='movedown"+e+"ActivitySearchpage']";
		return isDisplayed(driver, FindElement(driver, xpath, "down", action.SCROLLANDBOOLEAN, timeOut), "Visibility", timeOut, "down");
	}
	
	public WebElement getMoveTopColToDisplay(String projectName,EnableDisable ed,int timeOut) {
		String xpath="",e="";
		if (ed==EnableDisable.Disable)
			e="disable";
		else
			e="enable";
		xpath="//img[@id='top"+e+"ActivitySearchpage']";
		return isDisplayed(driver, FindElement(driver, xpath, "top", action.SCROLLANDBOOLEAN, timeOut), "Visibility", timeOut, "top");
	}
	
	public WebElement getMoveBottomColToDisplay(String projectName,EnableDisable ed,int timeOut) {
		String xpath="",e="";
		if (ed==EnableDisable.Disable)
			e="disable";
		else
			e="enable";
		xpath="//img[@id='bottom"+e+"ActivitySearchpage']";
		return isDisplayed(driver, FindElement(driver, xpath, "Bottom", action.SCROLLANDBOOLEAN, timeOut), "Visibility", timeOut, "Bottom");
	}
	
	@FindBy(xpath = "//div[contains(@class,'InsufficientPermissions')]//div[@class='PopupContentStart']")
	private WebElement insufficientErrorMsg;
	
	public WebElement getinsufficientErrorMsg(String projectName,int timeOut) {

		return isDisplayed(driver, insufficientErrorMsg, "Visibility", timeOut, "insufficientErrorMsg");
	}
	
	@FindBy(xpath = "//a[@id='advanceflter_AddRowNew']")
	private WebElement addRowFilter;
	
	public WebElement getaddRowFilter(String projectName,int timeOut) {

		return isDisplayed(driver, addRowFilter, "Visibility", timeOut, "addRowFilter");
	}
	
	@FindBy(xpath = "//a[@title='Add Filter Logic']")
	private WebElement filterLogicLink;
	
	public WebElement getfilterLogicLink(String projectName,int timeOut) {

		return isDisplayed(driver, filterLogicLink, "Visibility", timeOut, "filterLogicLink");
	}
	
	public WebElement clearApplyButtonOnAdvancedFilter(String projectName, String clearOrApply,int timeOut){
		if (clearOrApply.equalsIgnoreCase("Apply"))
			clearOrApply="Apply";
		else if(clearOrApply.equalsIgnoreCase("Clear"))
			clearOrApply="Clear";
		String xpath="//div[@id='filterGridContactDiv1']//a[@title='"+clearOrApply+"']";
		return isDisplayed(driver, FindElement(driver, xpath, "clearOrApply link", action.BOOLEAN, timeOut), "Visibility", timeOut, "clearOrApply link");
	}
	
	public WebElement getEditButton(String projectName,int timeOut) {
	return isDisplayed(driver, editButton_Lighting, "Visibility", timeOut, "Edit Button Lighting");	
	}
	
	public List<WebElement> getDropdownListOnSearchActivitiesAttachmentSettings(String projectName,int timeOut) {
		String xpath = "//td[@class='td2']//select[contains(@name,'page:form:advancefilterid')]";
		return FindElements(driver, xpath, "dropdown list");
	}
	
	public WebElement getTextBoxInSearchActAttachSettings(String projectName,LeftRight lr, int count,int timeOut) {
		String side=lr.toString().toLowerCase();
		String xpath = "//input[@id='activityinput"+side+"colmn"+count+"']";
		
		return isDisplayed(driver, FindElement(driver, xpath, "textbox", action.SCROLLANDBOOLEAN, timeOut),
				"Visibility", timeOut, "textbox in search activities/attachments");	
	}
	
	public boolean selectSuggestionInTextboxInSearchActAttach(String projectName,String field, int timeOut) {
		String xpath="//ul//li//a//span[text()='"+field+"']";
		WebElement ele=FindElement(driver,xpath , "field suggestion", action.BOOLEAN, timeOut);
		ele= isDisplayed(driver, ele, "visibility", timeOut, "field suggestion");
		if (click(driver, ele, "field suggestion", action.BOOLEAN)) {
			return true;
		}
		else
			return false;
	}
	public WebElement getPlusIconInSearchActAttach(String projectName,LeftRight lr, int count,int timeOut) {
		String xpath="";
		if (count==1) {
			if (lr==LeftRight.Right)
			xpath="//a[@id='Auto_activityinputrighRow']";
			else
			xpath="//a[@id='Auto_activityinputleftRow']";
		}
		else
			xpath="//a[@id='Auto_activity"+lr.toString()+"RowCount"+count+"']";
		return isDisplayed(driver, FindElement(driver, xpath, "textbox", action.SCROLLANDBOOLEAN, timeOut),
				"Visibility", timeOut, "plus icon in search activities/attachments");
	}
	
	@FindBy(xpath = "//span[contains(@id,'page:form:advancefilterid:adv_cmp')]//a[@title='Close']")
	private WebElement crossIconForSearchActivitiesAttachment;
	
	public WebElement getcrossIconForSearchActivitiesAttachment(String projectName,int timeOut) {
		return isDisplayed(driver, crossIconForSearchActivitiesAttachment, "Visibility", timeOut, "crossIconForSearchActivitiesAttachment");	
	}
	
	@FindBy(xpath = "//input[@title='Cancel']")
	private WebElement cancelForSearchActivitiesAttachment;
	
	public WebElement getcancelForSearchActivitiesAttachment(String projectName,int timeOut) {
		return isDisplayed(driver, cancelForSearchActivitiesAttachment, "Visibility", timeOut, "cancelForSearchActivitiesAttachment");	
	}
	
	public WebElement revertToDefaultsSearchActivitiesAttachment(String projectName,EnableDisable ed,int timeOut) {
		String xpath="",i="";
		if (ed==EnableDisable.Disable)
			i="de";
		xpath="//button[@title='Revert to Default'][@id='"+i+"activebtn']";
		return isDisplayed(driver, FindElement(driver, xpath, "textbox", action.SCROLLANDBOOLEAN, timeOut),
				"Visibility", timeOut, "revert to default search activities/attachments");
	
	}
	
	public WebElement getCrossIconOfTextboxInSearchActivitiesAttachment(String projectName,LeftRight lr,int count,int timeOut) {
		String side=lr.toString().toLowerCase();
		String xpath="//input[@id='activityinput"+side+"colmn"+count+"']/..//following-sibling::a[@title='Delete']";
		return isDisplayed(driver, FindElement(driver, xpath, "textbox", action.SCROLLANDBOOLEAN, timeOut),
				"Visibility", timeOut, "cross icon beside search activities/attachments");
	}
	
	public WebElement getWarningPopupSearchActivitiesAttachmentYesOrNo(String projectName, YesNo yn,int timeOut) {
		String xpath="//div[@id='WarningPopUpId']//input[@value='"+yn.toString()+"']";
		return isDisplayed(driver, FindElement(driver, xpath, "textbox", action.SCROLLANDBOOLEAN, timeOut),
				"Visibility", timeOut, "yes/no warning popup search activities/attachments");
	}
	
	@FindBy(xpath = "//div[@id='WarningPopUpId']//div[@class='PopupContentStart']")
	private WebElement revertToDefaultErrorPopup;
	
	public WebElement getrevertToDefaultErrorPopup(String projectName,int timeOut) {
		return isDisplayed(driver, revertToDefaultErrorPopup, "Visibility", timeOut, "revertToDefaultErrorPopup");	
	}
	
	@FindBy(xpath="//div[@id='addFilterlogic']//a[@class='helpLink']")
	private WebElement infoLink;
	
	/**
	 * @return the infoLink
	 */
	public WebElement getInfoLink(String projectName,int timeOut) {
		return isDisplayed(driver, infoLink, "Visibility", timeOut, "Info Link");
	}
	
	public List<WebElement> getRowRemoveIcon(){
		return FindElements(driver, "//img[@title='Remove Row']", "remove icon");
	}
	
	@FindBy(xpath="//img[@title='Remove Row']")
	private WebElement rowRemoveIcon1;
	
	/**
	 * @return the addFilterLogicLink
	 */
	public WebElement getRowRemoveIcon1(String projectName,int timeOut) {
		return isDisplayed(driver, rowRemoveIcon1, "Visibility", timeOut, "Remove Row Icon");
	}
	
	@FindBy(xpath="//a[text()='Add Filter Logic']")
	private WebElement addFilterLogicLink;
	
	/**
	 * @return the addFilterLogicLink
	 */
	public WebElement getAddFilterLogicLink(String projectName,int timeOut) {
		return isDisplayed(driver, addFilterLogicLink, "Visibility", timeOut, "Add Filter Logic Link");
	}
	
	@FindBy(xpath="//a[text()='Clear Filter Logic']")
	private WebElement clearFilterLogicLink;
	
	/**
	 * @return the addFilterLogicLink
	 */
	public WebElement getClearFilterLogicLink(String projectName,int timeOut) {
		return isDisplayed(driver, clearFilterLogicLink, "Visibility", timeOut, "Clear Filter Logic Link");
	}
	
	@FindBy(xpath="//label[text()='Filter Logic']/following-sibling::input")
	private WebElement filterLogicInputBox;
	
	/**
	 * @return the addFilterLogicLink
	 */
	public WebElement getFilterLogicInputBox(String projectName,int timeOut) {
		return isDisplayed(driver, filterLogicInputBox, "Visibility", timeOut, "Filter Logic Input Box");
	}
	
	/**
	 * @return the radioButtonforNewInstitution
	 */
	public WebElement getRadioButtonforRecordType(String recordType,int timeOut) {
		String xpath="//div[@class='changeRecordTypeRow']//span[text()='"+recordType+"']/../..//input";
		WebElement ele = null;
		ele=FindElement(driver, xpath, "radio button of record type "+recordType, action.SCROLLANDBOOLEAN,timeOut);
		ele=isDisplayed(driver,ele,"visibility",timeOut,"radio button of record type "+recordType);
		if (ele!=null) {
			return ele;
		} else {
			 xpath="//fieldset//span[contains(text(),'"+recordType+"')]/preceding-sibling::span";
			ele=FindElement(driver, xpath, "radio button of record type "+recordType, action.SCROLLANDBOOLEAN,timeOut);
			return isDisplayed(driver,ele,"visibility",timeOut,"radio button of record type "+recordType);
		}
		
	}
	
	/**
	 * @return the radioButtonforNewInstitution
	 */
	public WebElement getRadioButtonforRecordType(String recordType,boolean creationFromTaskLayout,int timeOut) {
		String class1="";
		if(creationFromTaskLayout) class1="flexipageComponent";
		else class1="changeRecordTypeRow";
		String xpath="//div[@class='"+class1+"']//span[text()='"+recordType+"']/../..//input";
		WebElement ele = null;
		ele=FindElement(driver, xpath, "radio button of record type "+recordType, action.SCROLLANDBOOLEAN,timeOut);
		return isDisplayed(driver,ele,"visibility",timeOut,"radio button of record type "+recordType);
	}
	
	public WebElement getRadioButtonforRecordTypeNavigationPopup(String recordType,int timeOut) {
		String class1="";
		String xpath="//span[text()='"+recordType+"']/preceding-sibling::input";
		WebElement ele = null;
		ele=FindElement(driver, xpath, "radio button of record type "+recordType, action.SCROLLANDBOOLEAN,timeOut);
		return isDisplayed(driver,ele,"visibility",timeOut,"radio button of record type "+recordType);
	}
	public List<WebElement> createdRelatedContactNameOnPopUp(String projectName) {
		List<WebElement> e=FindElements(driver, "//div[@id='conDIVid']//a", "created Contact Name Popup");
		return e;
	}
	
	@FindBy(xpath = "//frame[@title='Results']")
	private WebElement emailUploadPageFrame;
	
	@FindBy(xpath="//*[@title='Delete' or text()='Delete']")
	private WebElement deleteButton1;
	
	/**
	 * @return the addFilterLogicLink
	 */
	public WebElement getDeleteButton(String projectName,int timeOut) {
		return isDisplayed(driver, deleteButton1, "Visibility", timeOut, "Delete Button");
	}
	
	@FindBy(xpath = "//input[@title='Clone']")
	private WebElement cloneButton;
	
	public WebElement getcloneButton(String projectName,int timeOut) {
		return isDisplayed(driver, cloneButton, "Visibility", timeOut, "clone Button");
	}
	
	@FindBy(xpath = "//div//h1/div[contains(text(),'Event')]/..")
	private WebElement eventName;
	
		public WebElement geteventName(String projectName,int timeOut) {
			return isDisplayed(driver, eventName, "Visibility", timeOut, "event Name");
		}
	public WebElement clickOnDocumentOnEmailPage(String projectName, String docName, int timeOut) {
		String xpath ="//span[@title='"+docName+"']";
		return isDisplayed(driver, FindElement(driver, xpath, "document", action.BOOLEAN, timeOut), "visibility", timeOut, "document");
	}
	
	@FindBy(xpath = "//a[@title='Send List Email']")
	private WebElement sendListEmail;
	
	public WebElement getsendListEmail(String projectName,int timeOut) {
		return isDisplayed(driver, sendListEmail, "Visibility", timeOut, "sendListEmail Button");
	}
	
	
	
	public WebElement getAnyMsg(String projectName,String msg,int timeOut) {
		String xpath = "//*[contains(text(),'"+msg+"')]";
		WebElement ele = FindElement(driver, xpath, msg, action.SCROLLANDBOOLEAN, timeOut);
		return ele;
	}
	
	@FindBy(xpath = "//*[text()='Edit Name']/..")
	private WebElement editName;
	
	public WebElement getEditName(String projectName,int timeOut) {
		return isDisplayed(driver, editName, "Visibility", timeOut, "Edit Name");
	}
	
	
	@FindBy(xpath="//span[@title='Delete' or text()='Delete']")
	private WebElement deleteButtonPopUp;
	
	/**
	 * @return the addFilterLogicLink
	 */
	public WebElement getDeleteButtonPopUp(String projectName,int timeOut) {
		return isDisplayed(driver, deleteButtonPopUp, "Visibility", timeOut, "Delete Button PopUp");
	}
	

	@FindBy(xpath="//h2[contains(text(),'Delete')]/../following-sibling::div//*[@title='Delete']")
	private WebElement deleteButtonOnDeletePopUp;


	/**
	 * @return the deleteButtonOnDeletePopUp
	 */

	public WebElement getDeleteButtonOnDeletePopUp(String projectName,int timeOut) {
		return isDisplayed(driver, deleteButtonOnDeletePopUp, "Visibility", timeOut, "Delete Button on Delete Popup");
	}
	
	@FindBy(xpath="//h2[contains(text(),'Delete')]/../following-sibling::div//*[@title='Cancel']")
	private WebElement cancelButtonOnDeletePopUp;


	/**
	 * @return the deleteButtonOnDeletePopUp
	 */

	public WebElement getCancelButtonOnDeletePopUp(String projectName,int timeOut) {
		return isDisplayed(driver, cancelButtonOnDeletePopUp, "Visibility", timeOut, "cancel Button on Delete Popup");
	}
	@FindBy(xpath = "//button[@title='List View Controls']")
	private WebElement listViewControls;

	public WebElement getlistViewControlsButton(String projectName,int timeOut) {
		return isDisplayed(driver, listViewControls, "Visibility", timeOut, "listViewControlsButton");
	}
	
	@FindBy(xpath = "//li//a//span[text()='New']")
	private WebElement newButtonListView;
	
	/**
	 * @return the addFilterLogicLink
	 */
	public WebElement getnewButtonListView(String projectName,int timeOut) {
		return isDisplayed(driver, newButtonListView, "Visibility", timeOut, "newButtonListView");
	}
	
	public WebElement getlistNameTextBox(String projectName,String listNameOrListAPIName,int timeOut) {
		
		String xpath = "//label[text()='"+listNameOrListAPIName+"']//following-sibling::div//input";
		
		return isDisplayed(driver, FindElement(driver, xpath,"listNameTextBox" , action.SCROLLANDBOOLEAN, 
				timeOut), "Visibility", timeOut, "listNameTextBox");
	}
	
	@FindBy(xpath = "//span[contains(text(),'All users')]/../preceding-sibling::input")
	private WebElement allUsersCheckbox;
	
	/**
	 * @return the addFilterLogicLink
	 */
	public WebElement getallUsersRB(String projectName,int timeOut) {
		return isDisplayed(driver, allUsersCheckbox, "Visibility", timeOut, "allUsersCheckbox");
	}
	
	@FindBy(xpath = "//h2[text()='New List View']/../..//span[text()='Save']/..")
	private WebElement listViewSaveButton;
	
	public WebElement getlistViewSaveButton(String projectName,int timeOut) {
		return isDisplayed(driver, listViewSaveButton, "Visibility", timeOut, "listViewSaveButton");
	}
	
	public WebElement getListFilterSection(String projectName,String tab, int timeOut) {
		tab=tab.toLowerCase();
		String xpath="//div[contains(text(),'Filter')]/following-sibling::span[contains(text(),'"+tab+"')]/ancestor::a";
		return isDisplayed(driver, FindElement(driver, xpath,"ListFilterSection" , action.SCROLLANDBOOLEAN, 
				timeOut), "Visibility", timeOut, "ListFilterSection");
	
	}
	
	@FindBy(xpath = "//span[contains(text(),'All ')]/../..//input")
	private WebElement allCheckboxForFilter;
	
	public WebElement getallCheckboxForFilter(String projectName,int timeOut) {
		return isDisplayed(driver, allCheckboxForFilter, "Visibility", timeOut, "allCheckboxForFilter");
	}
	
	@FindBy(xpath = "//span[text()='Done']/..")
	private WebElement doneButtonListView;
	
	public WebElement getdoneButtonListView(String projectName,int timeOut) {
		return isDisplayed(driver, doneButtonListView, "Visibility", timeOut, "doneButtonListView");
	}
	
	@FindBy(xpath = "//div[contains(@class,'SecondaryDisplayManager')]//button[text()='Save']")
	private WebElement filterSave;
	
	public WebElement getfilterSave(String projectName,int timeOut) {
		return isDisplayed(driver, filterSave, "Visibility", timeOut, "filterSave");
	}
	@FindBy(xpath = "//div[contains(@class,'slds-grid primaryFieldRow')]//*[text()='Show more actions']/../..")
	//@FindBy(xpath = "//div[@id='completeDiv' and @class='cActivityTimeline']/..//img")
	private WebElement activityLineItemsDropdown;
	
	public WebElement getactivityLineItemsDropdown(String projectName,int timeOut) {
		return isDisplayed(driver, activityLineItemsDropdown, "Visibility", timeOut, "activityLineItemsDropdown");
	}
	
	@FindBy(xpath = "//div[@class='Next_steps']//h2[contains(text(),'Next Steps')]")
	private WebElement nextStepsHead;
	
	public WebElement getnextStepsHead(String projectName,int timeOut) {
		return isDisplayed(driver, nextStepsHead, "Visibility", timeOut, "nextStepsHead");
	}
	
	@FindBy(xpath = "//div[contains(@class,'past_activity')]")
	private WebElement pastActivitiesHead;
	
	public WebElement getpastActivitiesHead(String projectName,int timeOut) {
		return isDisplayed(driver, pastActivitiesHead, "Visibility", timeOut, "pastActivitiesHead");
	}
	
	@FindBy(xpath = "//div[contains(@class,'past_activity')]/following-sibling::div//div[contains(@class,'emptyListContent')]")
	private WebElement pastActivitiesMessage;
	
	public WebElement getpastActivitiesMessage(String projectName,int timeOut) {
		return isDisplayed(driver, pastActivitiesMessage, "Visibility", timeOut, "pastActivitiesMessage");
	}
	
	@FindBy(xpath = "//div[@class='Next_steps']//h2[contains(text(),'Next Steps')]/../following-sibling::div//div[contains(@class,'emptyListContent')]")
	private WebElement nextStepsMessage;
	
	public WebElement getnextStepsMessage(String projectName,int timeOut) {
		return isDisplayed(driver, nextStepsMessage, "Visibility", timeOut, "nextStepsMessage");
	}
	
	public WebElement moreStepsBtn(String projectName, EnableDisable ed,int timeOut) {
		String xpath="";
		WebElement ele=null;
		if (EnableDisable.Disable==ed) {
			xpath="//button[contains(@class,'moresteps')][@disabled='true']";
		}
		else
			xpath = "//h2[contains(text(),'Next Steps')]/following-sibling::button[contains(@class,'moresteps')]";
		ele=FindElement(driver, xpath, "more steps btn", action.BOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "more steps btn");

	}
	
	public WebElement loadMorePastActivitiesBtn(String projectName, EnableDisable ed,int timeOut) {
		String xpath="";int i;
		WebElement ele=null;
		if (EnableDisable.Disable==ed) {
			xpath="//button[contains(text(),'More Past Activities')][@disabled='true']";
		}
		else
			xpath="//button[contains(text(),'More Past Activities')]";
		ele=FindElement(driver, xpath, "loadMorePast", action.BOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "loadMorePast");

	}
	
	@FindBy(xpath = "//button[contains(text(),'More Past Activities')]/following-sibling::div//button")
	private WebElement dropdownBtnMorePastAct;
	public WebElement dropdownBtnMorePastAct(String projectName,int timeOut) {
		return isDisplayed(driver, dropdownBtnMorePastAct, "Visibility", timeOut, "dropdown Btn of Load More Past Act");
	}
	
	@FindBy(xpath = "//input[contains(@title,'Search')]")
	private WebElement searchSalesforce;
	
	public WebElement getsearchSalesforce(String projectName,int timeOut) {
		return isDisplayed(driver, searchSalesforce, "Visibility", timeOut, "search Salesforce textbox");
	}
	
	@FindBy(xpath = "//div[@class='container']//div[@class='slds-modal__footer']//button/span[text()='Cancel']")
	private WebElement cancelBtnAtActivityTimeLineFilterPopuP;
	
	public WebElement getCancelBtnAtActivityTimeLineFilterPopuP(String projectName,int timeOut) {
		return isDisplayed(driver, cancelBtnAtActivityTimeLineFilterPopuP, "Visibility", timeOut, "cancel Btn At ActivityTimeLine Filter PopuP");
	}
	
	@FindBy(xpath = "//div[@class='container']//div[@class='slds-modal__footer']//button/span[text()='Apply']")
	private WebElement applyBtnAtActivityTimeLineFilterPopuP;
	
	public WebElement getApplyBtnAtActivityTimeLineFilterPopuP(String projectName,int timeOut) {
		return isDisplayed(driver, applyBtnAtActivityTimeLineFilterPopuP, "Visibility", timeOut, "Apply Btn At ActivityTimeLine Filter PopuP");
	}
	
	@FindBy(xpath = "//h2[contains(text(),'Delete')]/../following-sibling::div//div")
	private WebElement deleteTaskText;
	public WebElement getdeleteTaskText(String projectName,int timeOut) {
		return isDisplayed(driver, deleteTaskText, "Visibility", timeOut, "deleteTaskText");
	}
	
	public WebElement getCheckboxOfRestoreItemOnRecycleBin(String projectName, String restoreItem, int timeOut) {
        String xpath="//span[@title='"+restoreItem+"']/../../preceding-sibling::td//input/..";
        return isDisplayed(driver, FindElement(driver, xpath, "checkbox : "+restoreItem, action.SCROLLANDBOOLEAN, timeOut), "visibility", timeOut, "checkbox");
    }
	
	@FindBy(xpath = "//div[@title='Restore']")
	private WebElement restoreButtonOnRecycleBin;
	
	public WebElement getRestoreButtonOnRecycleBin(String projectName,int timeOut) {
		return isDisplayed(driver, restoreButtonOnRecycleBin, "Visibility", timeOut, "restore Button On Recycle Bin");
	}
	
	@FindBy(xpath = "//div[@class='listRelatedObject setupBlock']//td//input[@title='New Values']")
	private WebElement newButtonOnMeetingType;
	
	public WebElement getNewButtonOnMeetingType(String projectName,int timeOut) {
		return isDisplayed(driver, newButtonOnMeetingType, "Visibility", timeOut, "new Button On Meeting Type");
	}
	
	
	@FindBy(xpath = "//textarea[@title='Meeting Type']")
	private WebElement meetingTypePickListValuesTextArea;
	
	public WebElement getMeetingTypePickListValuesTextArea(String projectName,int timeOut) {
		return isDisplayed(driver, meetingTypePickListValuesTextArea, "Visibility", timeOut, "meeting Type PickList Values TextArea");
	}
	
	
	@FindBy(xpath = "//iframe[@title='Activity Custom Field: Meeting Type ~ Salesforce - Enterprise Edition']")
	 private WebElement meetingTypeFrame;
	
	@FindBy(xpath = "//iframe[@title='Add Picklist Values: Meeting Type ~ Salesforce - Enterprise Edition']")
	 private WebElement addPickListMeetingTypeFrame;

	public WebElement verifyEvent(String projectName,String eventName, int timeOut) {
		String xpath="//div//h1/div[contains(text(),'Event')]/..//*[text()='"+eventName+"']";
		return isDisplayed(driver,  FindElement(driver, xpath, "event", action.SCROLLANDBOOLEAN, timeOut), "Visibility", timeOut, "event");
		
	}
	
	
	@FindBy(xpath = "//a[@title='New Meeting' and @class='actionClass newWindowCss']")
	private WebElement newMeetingNewTabIconAfterMouseHover;
	
	public WebElement getNewMeetingNewTabIconAfterMouseHover(String projectName,int timeOut) {
		return isDisplayed(driver, newMeetingNewTabIconAfterMouseHover, "Visibility", timeOut, "new Meeting New Tab Icon After MouseHover");
	}
	
	@FindBy(xpath="//a[@class='headerLink']//button[@title='Close']")
	private WebElement pagePopUp;

	/**
	 * @return the searchIcon_Lighting
	 */
	public WebElement getPagePopUp(String projectName,int timeOut) {
		return isDisplayed(driver, pagePopUp, "Visibility", timeOut, "Page POP UP");
	}
	
	@FindBy(xpath = "//li[@data-aura-class='uiMenuItem onesetupSetupMenuItem']/a[@title='Edit Page']")
	private WebElement editPageOnSetUp;

	/**
	 * @return the userMenuSetupLink
	 */
	public WebElement getEditPageOnSetUp(int timeOut) {
		WebElement ele=null;
			ele=editPageOnSetUp;
		return isDisplayed(driver, ele, "Visibility", timeOut, "Edit Page");
	}
	
	@FindBy(xpath="//*[text()='Status']/..//div//input")
	private WebElement statusDropDownList;

	/**
	 * @return the statusDropDownList
	 */
	public WebElement getStatus(String projectName,int timeOut) {
		return isDisplayed(driver, statusDropDownList, "Visibility", timeOut, "Status ");
	}
	
	@FindBy(xpath = "//*[@id='parentDiv']/div")
	private WebElement defaultImageXpath;

	public WebElement getDefaultImageXpath(int timeOut) {
		return isDisplayed(driver, defaultImageXpath, "Visibility", timeOut, "default image xpath");
	}
	
	@FindBy(xpath = "//*[contains(@data-component-id,'DisplayFieldSet')]//img")
	private WebElement uploadedImageRelativeXpath;
	
	
//	@FindBy(xpath = "//*[@id='parentDiv']//div[contains(@class,'imageIcon')]")
//	private WebElement updatePhotoCameraIcon;

	public WebElement getUploadedImageRelativeXpath(int timeOut) {
		return isDisplayed(driver, uploadedImageRelativeXpath, "Visibility", timeOut, "update photo camera icon");
	}

	public WebElement getUpdatePhotoCameraIcon(int timeOut) {
		
		String xpath="//*[@id='parentDiv']//div[contains(@class,'imageIcon')]";
		WebElement ele=null;
		ele= FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, timeOut);
		if(ele!=null) {
			((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('class','imageIcon show');",ele);
			xpath="//*[@id='parentDiv']//div[contains(@class,'imageIcon show')]";
			ele= FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, timeOut);
			return isDisplayed(driver, ele, "Visibility", timeOut, "update photo camera icon");
		}
		return null;
		
	}
	
	public WebElement updateAndDeletePhotoXpath(IconType iconType, int timeOut) {
		String photo="";
		if(iconType.toString().equalsIgnoreCase(IconType.updatePhoto.toString())) {
			photo="Update Photo";
		}else {
			photo="Delete Photo";
		}
		String xpath ="//*[@class='actionMenu']//a[@title='"+photo+"']";
		WebElement ele= FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, iconType+" xpath");
	}
	////*[text()='Upload Files']
	//input[@name='fileUploader']
	
	
	@FindBy(xpath = "//input[@name='fileUploader']")
	private WebElement uploadImageXpath;

	public WebElement getUploadImageXpath(int timeOut) {
		return isDisplayed(driver, uploadImageXpath, "Visibility", timeOut,"upload image xpath");
	}
	
	@FindBy(xpath = "//div[@id='parentDiv']//img")
	private WebElement imgLink;
	
	public WebElement getimgLink(String projectName,int timeOut) {
		return isDisplayed(driver, imgLink, "Visibility", timeOut, "send Button on List Email");
		
	}

	public WebElement getImgForObject(String object,int timeOut) {
		String xpath ="//*[text()='"+object+"']/../../preceding-sibling::*//img";
		WebElement ele= FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "img for "+object);
	}
	
	public WebElement getCustomNumberOfImg(String object,String record,int timeOut) {
		String xpath ="//article[contains(@class,'RelatedListAccordion')]//a[text()='"+object+"']/../../../following-sibling::div//a[text()='"+record+"']/../preceding-sibling::div/*/*/*";
		WebElement ele= FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "img for "+object);
	}

	@FindBy(xpath = "//*[@name='SaveEdit']")
	private WebElement navigationTabSaveBtn;
	
	public WebElement getNavigationTabSaveBtn(String projectName,int timeOut) {
		return isDisplayed(driver, navigationTabSaveBtn, "Visibility", timeOut, "Save Button");
		
	}
	
	@FindBy(xpath = "//div[@class='slds-global-header__logo']")
	private WebElement headerImg;
	
	public WebElement getHeaderImg(String projectName,int timeOut) {
		return isDisplayed(driver, headerImg, "Visibility", timeOut, "Header Image");
		
	}
	
	@FindBy(xpath = "//div[@class='forcePageError']/p")
	private WebElement invalidImageErrorMsg;

	public WebElement getInvalidImageErrorMsg(int timeOut) {
		return isDisplayed(driver, invalidImageErrorMsg, "Visibility", timeOut, "invalid image error message");
	}
	
	@FindBy(xpath = "//footer//*[text()='Cancel']")
	private WebElement cancelBtn;

	public WebElement getCancelBtn(int timeOut) {
		return isDisplayed(driver, cancelBtn, "Visibility", timeOut, "cancel button");
	}
	
	@FindBy(xpath = "//*[@data-aura-class='cFileUploader']//*[contains(@class,'slds-p-around_medium')]/div[2]")
	private WebElement uploadImageErrorMsg;

	public WebElement getUploadImageErrorMsg(int timeOut) {
		return isDisplayed(driver, uploadImageErrorMsg, "Visibility", timeOut, "upload image error message");
	}
	
	@FindBy(xpath = "//*[@data-aura-class='cFileUploader']//*[contains(@class,'slds-p-around_medium')]/div[4]/div")
	private WebElement currentPhotoText;

	public WebElement getCurrentPhotoText(int timeOut) {
		return isDisplayed(driver, currentPhotoText, "Visibility", timeOut, "current photo text message");
	}
	
	@FindBy(xpath = "//*[@data-aura-class='cFileUploader']//*[contains(@class,'slds-p-around_medium')]/div[5]")
	private WebElement UpdatePhotodefaultImageXpath;

	public WebElement getUpdatePhotodefaultImageXpath(int timeOut) {
		return isDisplayed(driver, UpdatePhotodefaultImageXpath, "Visibility", timeOut, "update photo default image xpath");
	}
	
	@FindBy(xpath = "//*[@data-aura-class='cFileUploader']//h2[text()='Update Photo']")
	private WebElement updatePhotoTextXpath;

	public WebElement getUpdatePhotoTextXpath(int timeOut) {
		return isDisplayed(driver, updatePhotoTextXpath, "Visibility", timeOut, "update photo text xpath");
	}
	
	@FindBy(xpath = "//*[@data-aura-class='cFileUploader']//*[text()='close']")
	private WebElement updatePhotoCrossIcon;

	public WebElement getUpdatePhotoCrossIcon(int timeOut) {
		return isDisplayed(driver, updatePhotoCrossIcon, "Visibility", timeOut, "update photo cross icon");
	}
	
	@FindBy(xpath = "//div[contains(@class,'deletePopup')]/div/div")
	private WebElement deletePhotoPopUpErrorMsg;

	public WebElement getDeletePhotoPopUpErrorMsg(int timeOut) {
		return isDisplayed(driver, deletePhotoPopUpErrorMsg, "Visibility", timeOut, "delete photo popup error message");
	}
	
	public WebElement deletePhotoPopUpCrossCancelDeleteButton(Buttons buttons, int timeOut) {
		String xpath= "";
		String button = buttons.toString().replace("_", " ");
		if(buttons.toString().equalsIgnoreCase(Buttons.close.toString())) {
			 xpath= "//div[contains(@class,'deletePopup')]//button/span[text()='"+button+"']";
		}else {
			xpath= "//div[contains(@class,'deletePopup')]//button[text()='"+button+"']";
		}
		WebElement ele= FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, button+" delete popUp xpath ");
	
	}

	@FindBy(xpath = "//footer//button[@title='Close']")
	private WebElement footerCloseButton;
	
	public WebElement getfooterCloseButton(String projectName,int timeOut) {
		return isDisplayed(driver, footerCloseButton, "Visibility", timeOut, "footerCloseButton");
		
	}
	@FindBy(xpath = "//div[contains(@class,'sdgborder')]//button[@title='Save' or text()='Save']")
	private WebElement sdgSave;
	
	public WebElement getsdgSaveButton(String projectName,int timeOut) {
		return isDisplayed(driver, sdgSave, "Visibility", timeOut, "sdgSaveButton");
		
	}
	
	
	@FindBy(xpath = "//div[contains(@class,'sdgborder')]//button[@title='Cancel' or text()='Cancel']")
	private WebElement sdgCancel;
	
	public WebElement getsdgCancelButton(String projectName,int timeOut) {
		return isDisplayed(driver, sdgCancel, "Visibility", timeOut, "sdgCancelbutton");
		
	}
	
	@FindBy(xpath = "//a[@title='Upload Files']")
	private WebElement uploadFiles;
	
	public WebElement getuploadFilesButton(String projectName,int timeOut) {
		return isDisplayed(driver, uploadFiles, "Visibility", timeOut, "uploadFiles");
		
	}
	@FindBy(xpath = "//span[text()='Delete Photo']/..")
	private WebElement deletePhotoButton;
	
	public WebElement getdeletePhotoButton(String projectName,int timeOut) {
		return isDisplayed(driver, deletePhotoButton, "Visibility", timeOut, "deletePhotoButton");
		
	}
	@FindBy(xpath = "(//*[contains(text(),'Actions')]/../../..//following-sibling::div//button[@title='Reload.'])[2]")
	private WebElement actionsSDGRefresh;
	
	public WebElement getactionsSDGRefresh(String projectName,int timeOut) {
		return isDisplayed(driver, actionsSDGRefresh, "Visibility", timeOut, "actionsSDGRefresh");
		
	}
	
	@FindBy (xpath = "//h2[contains(@class,'inlineTitle')]")
	private WebElement taskPopUpHeader;
	
	@FindBy (xpath = "//h2[@id='modal-heading-01']")
	private WebElement taskPopUpHeader1;
	
	/**
	 * @return the navigationPopUpHeader
	 */
	public WebElement getTaskPopUpHeader(String projectName,int timeOut) {
		WebElement ele=isDisplayed(driver, taskPopUpHeader, "Visibility", timeOut, "tASK PopUp Header");
		
		if(ele==null) {
			 return isDisplayed(driver, taskPopUpHeader1, "Visibility", timeOut, "tASK PopUp Header");
			
		}
		else return ele;
	}
	
	@FindBy(xpath = "//iframe[@title='Report Viewer']")
	private WebElement accountReferralFrame;
	
	@FindBy(xpath = "//iframe[@title='dashboard']")
	private WebElement dashboardFrame;
	@FindBy(xpath = "//iframe[contains(@title,'Record Type: Portfolio')]")
	private WebElement RTPortfolioCFrame;
	
	
	@FindBy(xpath = "//button[@title='Clear Selection']")
	private WebElement crossInSDGEdit;
	
	public WebElement getcrossInSDGEdit(String projectName,int timeOut) {
		return isDisplayed(driver, crossInSDGEdit, "Visibility", timeOut, "crossInSDGEdit");
		
	}
	

	/**
	 * @return the saveButton
	 */
	public WebElement getSaveButton(String environment,String mode,int timeOut) {
		ThreadSleep(2000);
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
			return isDisplayed(driver, saveButtonClassic, "Visibility", timeOut, "Save Button Classic");
		}else{
			return isDisplayed(driver, saveButtonLighting, "Visibility", timeOut, "Save Button Lighting");
		}
		
	}
	
	@FindBy(xpath="//span[@class='aw-bars-box ']")
	private WebElement emailProspectSelectProspectsGridScrollBox;

	/**
	 * @return the emailProspectSelectProspectsGridScrollBox
	 */
	public WebElement getEmailProspectSelectProspectsGridScrollBox(int timeOut) {
		return isDisplayed(driver, emailProspectSelectProspectsGridScrollBox, "Visibility", timeOut, "email prospect select prospect grid scroll box");
	}
	
	@FindBy(xpath="(//div[@class='step_1']//a[@title='Next'])[2]")
	private WebElement emailProspectStep1NextBtn;

	/**
	 * @return the emailProspectStep1NextBtn
	 */
	public WebElement getEmailProspectStep1NextBtn(int timeOut) {
		return isDisplayed(driver, emailProspectStep1NextBtn, "Visibility", timeOut, "email prospect steps 1 next button");
	}
	
	@FindBy(xpath="//div[@class='step_2']//a[@title='Next']")
	private WebElement emailProspectStep2NextBtn;

	/**
	 * @return the emailProspectStep2NextBtn
	 */
	public WebElement getEmailProspectStep2NextBtn(int timeOut) {
		return isDisplayed(driver, emailProspectStep2NextBtn, "Visibility", timeOut, "email prospect steps 2 nect button");
	}
	
	public WebElement getEmailProspectSendBtn(TopOrBottom topOrBottom, int timeOut) {


		WebElement ele=null;
		String xpath=null;

		if (TopOrBottom.TOP.equals(topOrBottom)) {
			xpath = "(//div[@class='step_3']//a[@title='Send'])[1]";
		} else {
			xpath = "(//div[@class='step_3']//a[@title='Send'])[2]";
		}


		ele = FindElement(driver, xpath, "Send Button : "+topOrBottom, action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "send button : "+topOrBottom);
	}

	@FindBy(xpath="//a[text()='Finished']")
	private WebElement emailProspectFinishBtn;

	/**
	 * @return the emailProspectFinishBtn
	 */
	public WebElement getEmailProspectFinishBtn(int timeOut) {
		return isDisplayed(driver, emailProspectFinishBtn, "Visibility", timeOut, "email prospect finish button");
	}
	
	@FindBy(xpath="//select[contains(@id,'page') and contains(@id,'frm') and contains(@id,'sl1')]")
	private WebElement emailProspectFolderDropDownList;

	/**
	 * @return the emailProspectFolderDropDownList
	 */
	public WebElement getEmailProspectFolderDropDownList(int timeOut) {
		return isDisplayed(driver, emailProspectFolderDropDownList, "Visibility", timeOut, "email prospect folder drop downlist");
	}
	
	@FindBy(xpath="(//span[@class='aw-bars-box '])[2]")
	private WebElement emailProspectStep2CustomEmailtemplateScrollBox;

	/**
	 * @return the emailProspectStep2CustomEmailtemplateScrollBox
	 */
	public WebElement getEmailProspectStep2CustomEmailtemplateScrollBox(int timeOut) {
		return isDisplayed(driver, emailProspectStep2CustomEmailtemplateScrollBox, "Visibility", timeOut, "email prospect custom email template scroll box");
	}
	
	
	public WebElement getCustomTabCrossIcon(String projectName,int timeOut) {
		List<WebElement> eleList = FindElements(driver, "//*[@title='Close this window' or text()='Close this window']", "Cross Icon");
		try {
			for (int i=eleList.size()-1;i>=0;i++) {
				WebElement webElement = eleList.get(i);
				webElement=isDisplayed(driver, webElement, "Visibility", 2, "Cross Icon");
				if (webElement!=null) {
					return webElement;
				} else {

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		return null;

	}
	
	
	public WebElement getCustomTabCancelBtn(String projectName,int timeOut) {
		List<WebElement> eleList = FindElements(driver, "//*[@title='Cancel' or text()='Cancel']", "Cancel Button");
		try {
			for (int i=eleList.size()-1;i>=0;i++) {
				WebElement webElement = eleList.get(i);
				webElement=isDisplayed(driver, webElement, "Visibility", 2, "Cross Icon");
				if (webElement!=null) {
					return webElement;
				} else {

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		return null;

	}
	
	public WebElement getSDGGridDropDown(SDGGridName sdgGridName , String labelName, int timeOut) {
		String xpath="//*[text()='"+labelName+"']/../following-sibling::*//select[@class='slds-select']";
		WebElement ele = FindElement(driver, xpath, "SDG grid drop down list "+sdgGridName, action.SCROLLANDBOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "SDG grid drop down list "+sdgGridName);
	
	}
	
	
	@FindBy (xpath = "//*[@class='outPopupBox']//h2")
	private WebElement popUpHeader;
	
	@FindBy (xpath = "//*[@class='actionBody']//h2")
	private WebElement popUpHeader1;
	
	/**
	 * @return the navigationPopUpHeader
	 */
	public WebElement getPopUpHeader(String projectName,int timeOut) {
		WebElement ele=isDisplayed(driver, popUpHeader, "Visibility", 10, "PopUp Header");
		
		if(ele==null) {
			 return isDisplayed(driver, popUpHeader1, "Visibility", 10, "PopUp Header");
			
		}
		else return ele;
	}
	
	@FindBy (xpath = "//div[@class='container']//li")
	private WebElement hitASnagElement;
	
	/**
	 * @return the navigationPopUpHeader
	 */
	public WebElement getHitASnagElement(String projectName,int timeOut) {
		WebElement ele=isDisplayed(driver, hitASnagElement, "Visibility", timeOut, "PopUp Header");
		return ele;
	}
	
	public WebElement getPopUpHeader1(String projectName,int timeOut) {

		return isDisplayed(driver, popUpHeader1, "Visibility", 10, "PopUp Header");

	}
	
	@FindBy(xpath="//*[text()='View Calendar']")
	private WebElement calenderIcon;

	/**
	 * @return the calenderIcon
	 */
	public WebElement getCalenderIcon(int timeOut) {
		return isDisplayed(driver, calenderIcon, "Visibility", timeOut, "View Calendar");
	}
	
	@FindBy(xpath="//div[@class='eventList']")
	private WebElement calenderCellIcon;

	/**
	 * @return the emailProspectFolderDropDownList
	 */
	public WebElement getCalenderCellIcon(int timeOut) {
		return isDisplayed(driver, calenderCellIcon, "Visibility", timeOut, "Calender Cell Icon");
	}
	
	@FindBy(xpath="//*[contains(text(),' New ')]/../following-sibling::footer/*[@title='Save' or text()='Save']")
	private WebElement taskNewSaveBtn;

	public WebElement getTaskSaveBtn(String projectName,int timeOut) {
		return isDisplayed(driver, taskNewSaveBtn, "Visibility", timeOut, "New Task Save Button");
	}
	
	@FindBy(xpath="//*[contains(text(),' Edit') ]/../following-sibling::footer/*[@title='Save' or text()='Save']")
	private WebElement taskEditSaveBtn;
	
	public WebElement geTaskEditSaveBtn(String projectName,int timeOut) {
		return isDisplayed(driver, taskEditSaveBtn, "Visibility", timeOut, "Edit Task Save Button");
	}
	
	@FindBy(xpath="//*[@name='secondaryFields']//p")
	private WebElement secondaryField;
	
	public WebElement getSecondaryField(String projectName,int timeOut) {
		return isDisplayed(driver, secondaryField, "Visibility", timeOut, "Secondary Field");
	}
	
	
	public WebElement getDropdownOnCreationPopUp(String projectName,PageName pageName,String label,action action,int timeOut) {
		String xpath="";
		label = label.replace("_", " ");
		xpath="//label[text()='"+label+"']/following-sibling::div//input";
		WebElement ele =  FindElement(driver, xpath,label ,action, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "Drop Down : "+label);
	}
	
	@FindBy(xpath = "//button[@title='Move selection to Chosen']")
	private WebElement addButtonMultipleBox_Lightning;

	public WebElement getAddButtonMultipleBox(int timeOut) {
		return isDisplayed(driver, addButtonMultipleBox_Lightning, "Visibility", timeOut, "addButtonMultipleBox Select Box Mode Lightning");

	}
	
	public WebElement getHeaderSectionGrid(String sectionName,action action,int timeOut) {
		String xpath2= "//*[@class='test-id__section-header-title slds-truncate' and text()='"+sectionName+"']/../..//button";
		WebElement element = FindElement(driver, xpath2, "Section :"+sectionName ,action, timeOut);
		return isDisplayed(driver, element, "Visibility", timeOut, "Section :"+sectionName );
	}
	
	public List<WebElement> getFieldLabelsOfSection(String sectionName,action action,int timeOut) {
		String xpath = "//*[@class='test-id__section-header-title slds-truncate' and text()='" + sectionName+"']/../../..//*[@class='test-id__field-label']";
		return FindElements(driver, xpath, "label field of section:"+sectionName);
	}
	
	@FindBy(xpath="//nav[@aria-label='Breadcrumbs']//span")
	private WebElement pageHeaderName;
	
	public WebElement getPageHeaderName(int timeOut) {
		
		return isDisplayed(driver, pageHeaderName, "Visibility", timeOut, "Page header name");
		
	}
	
	@FindBy(xpath="//*[@class='listViewManagerHeaderButton filterButton']//button")
	private WebElement filterButton;
	
	public WebElement getFilterButton(int timeOut) {
		
		return isDisplayed(driver, filterButton, "Visibility", timeOut, "filter button");
		
	}
	
	

	public List<WebElement> getListOfFilterPanelValue(int timeOut) {
		
		return FindElements(driver, "//div[@class='slds-panel__body panelBody']//li/div", "Filter value");
		
	}
	
	
	
	
	
	
}
