/**
 * 
 */
package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.navatar.generic.EnumConstants.action;

//import static com.navatar.generic.CommonLib.*;

import static com.navatar.generic.CommonLib.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Parul Singh
 *
 */
public class LoginPage extends BasePageBusinessLayer {

	/**
	 * @param driver
	 */

	public LoginPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "username")
	private WebElement userNameTextBox;

	/**
	 * @return the userNameTextBox
	 */
	public WebElement getUserNameTextBox(int timeOut) {
		return isDisplayed(driver, userNameTextBox, "Visibility", timeOut, "Username Text Box.");
	}

	@FindBy(id = "password")
	private WebElement passwordTextBox;

	/**
	 * @return the passwordTextBox
	 */
	public WebElement getPasswordTextBox(int timeOut) {
		return isDisplayed(driver, passwordTextBox, "Visibility", timeOut, "Password Text Box.");
	}

	/*
	 * @FindBy(id = "Login") private WebElement loginButton;
	 * 
	 *//**
		 * @return the loginButton
		 *//*
			 * public WebElement getLoginButton(int timeOut) { return isDisplayed(driver,
			 * loginButton, "Visibility", timeOut, "Login Button."); }
			 */

	public WebElement getLoginButton(int timeOut) {

		String xpath = "//input[@id=\"Login\"]";

		List<WebElement> listOfElements = FindElements(driver, xpath, "getLoginButton").stream()
				.filter(x -> x.isDisplayed()).collect(Collectors.toList());

		if (listOfElements.size() > 0) {
			return listOfElements.get(0);
		} else {
			return null;

		}

	}

	/**********************************
	 * investor login
	 ************************************************/

	@FindBy(xpath = "//input[@id='page:frmid:username']")
	private WebElement investorUsernameTextbox;

	/**
	 * @return the investorUsernameText
	 */
	public WebElement getInvestorUsernameTextbox(int timeOut) {
		return isDisplayed(driver, investorUsernameTextbox, "Visibility", timeOut, "Investor username text box");
	}

	@FindBy(xpath = "//*[@id='page:frmid:password']")
	private WebElement investorPasswordTextbox;

	/**
	 * @return the investerPasswordTextbox
	 */
	public WebElement getInvestorPasswordTextbox(int timeOut) {
		return isDisplayed(driver, investorPasswordTextbox, "Visibility", timeOut, "Investor password textbox");
	}

	@FindBy(xpath = "//input[@title='Login']")
	private WebElement investorLoginButton;

	/**
	 * @return the investorLoginButton
	 */
	public WebElement getInvestorLoginButton(int timeOut) {
		return isDisplayed(driver, investorLoginButton, "Visibility", timeOut, "Investor login button ");
	}

	@FindBy(xpath = "//img[contains(@src,'twitter')]")
	private WebElement twitterIcon;

	/**
	 * @return the twitterIcon
	 */
	public WebElement getTwitterIcon(int timeOut) {
		return isDisplayed(driver, twitterIcon, "Visibility", timeOut, "Twitter icon");
	}

	@FindBy(xpath = "//img[contains(@src,'facebook')]")
	private WebElement facebookIcon;

	/**
	 * @return the facebookIcon
	 */
	public WebElement getFacebookIcon(int timeOut) {
		return isDisplayed(driver, facebookIcon, "Visibility", timeOut, "Facebook icon");
	}

	@FindBy(xpath = "//img[contains(@src,'navatar')]")
	private WebElement navatarImg;

	/**
	 * @return the navatarImg
	 */
	public WebElement getNavatarImg(int timeOut) {
		return isDisplayed(driver, navatarImg, "Visibility", timeOut, "Navatar image logo");
	}

	@FindBy(xpath = "//a[@title='Forgot your password?']")
	private WebElement forgotPasswordInvestor;

	/**
	 * @return the forgotPasswordInvestor
	 */
	public WebElement getForgotPasswordInvestor(int timeOut) {
		return isDisplayed(driver, forgotPasswordInvestor, "Visibility", timeOut,
				"Forgot your password link on investor login page");
	}

	@FindBy(xpath = "//a[@title='Forgot your user name?']")
	private WebElement forgotUsernameInvestor;

	/**
	 * @return the forgotPasswordInvestor
	 */
	public WebElement getForgotUsernameInvestor(int timeOut) {
		return isDisplayed(driver, forgotUsernameInvestor, "Visibility", timeOut,
				"Forgot your username link on investor login page");
	}

	@FindBy(xpath = "(//a[@title='Signup'])[1]")
	private WebElement investorAlreadyInvitedSignupLink;

	/**
	 * @return the investorAlreadyInvitedSignupLink
	 */
	public WebElement getInvestorAlreadyInvitedSignupLink(int timeOut) {
		return isDisplayed(driver, investorAlreadyInvitedSignupLink, "Visibility", timeOut,
				"Sign up button for investor and already invited on investor login page");
	}

	@FindBy(xpath = "(//a[@title='Signup'])[2]")
	private WebElement generalPartnerAlreadyInvitedSignupLink;

	/**
	 * @return the generalPartnerAlreadyInvitedSignupLink
	 */
	public WebElement getGeneralPartnerAlreadyInvitedSignupLink(int timeOut) {
		return isDisplayed(driver, generalPartnerAlreadyInvitedSignupLink, "Visibility", timeOut,
				"Sign up button for general partner and already invited on investor login page");
	}

	/**************************
	 * investor forgot password
	 *******************************/
	@FindBy(xpath = "//input[@id='forgotPassword:SiteTemplate:theForm:username']")
	private WebElement emailTextbox;

	/**
	 * @return the emailTextbox
	 */
	public WebElement getEmailTextbox(int timeOut) {
		return isDisplayed(driver, emailTextbox, "Visibility", timeOut, "Email textbox on forgot password page");
	}

	@FindBy(xpath = "//input[@id='forgotPassword:SiteTemplate:theForm:submit']")
	private WebElement submitButton;

	/**
	 * @return the submitButton
	 */
	public WebElement getSubmitButton(int timeOut) {
		return isDisplayed(driver, submitButton, "Visibility", timeOut, "Submit button on forgot password page");
	}

	/***************************
	 * investor forgot password reset page
	 **********************/
	@FindBy(xpath = "//span[@class='title']")
	private WebElement enterEmailIdTextForgotPasswordPage;

	/**
	 * @return the enterEmailIdTextForgotPasswordPage
	 */
	public WebElement getEnterEmailIdTextForgotPasswordPage(int timeOut) {
		return isDisplayed(driver, enterEmailIdTextForgotPasswordPage, "Visibility", timeOut,
				"Enter email id text present on forgot password page");
	}

	@FindBy(xpath = "//input[@id='changePassword:SiteTemplate:theForm:psw']")
	private WebElement forgotPasswordNewPasswordTextbox;

	/**
	 * @return the forgotPasswordNewPasswordTextbox
	 */
	public WebElement getForgotPasswordNewPasswordTextbox(int timeOut) {
		return isDisplayed(driver, forgotPasswordNewPasswordTextbox, "Visibility", timeOut,
				"new password textbox on forgot password page");
	}

	@FindBy(xpath = "//input[@id='changePassword:SiteTemplate:theForm:vpsw']")
	private WebElement forgotPasswordVerifyNewPasswordTextbox;

	/**
	 * @return the forgotPasswordNewPasswordTextbox
	 */
	public WebElement getForgotPasswordVerifyNewPasswordTextbox(int timeOut) {
		return isDisplayed(driver, forgotPasswordVerifyNewPasswordTextbox, "Visibility", timeOut,
				"verify new password textbox on forgot password page");
	}

	@FindBy(xpath = "//span[contains(text(),'An email has been sent to you with your temporary password')]")
	private WebElement emailHasBeenSentText;

	/**
	 * @return the emailHasBeenSentText
	 */
	public WebElement getEmailHasBeenSentText(int timeOut) {
		return isDisplayed(driver, emailHasBeenSentText, "Visibility", timeOut,
				"Email has been sent to reset password on forgot password page");
	}

	@FindBy(xpath = "//span[contains(text(),'Change Your Password')]")
	private WebElement changeYourPasswordText;

	/**
	 * @return the changeYourPasswordText
	 */
	public WebElement getChangeYourPasswordText(int timeOut) {
		return isDisplayed(driver, changeYourPasswordText, "Visibility", timeOut,
				"change your password text present on forgot password page");
	}

	@FindBy(xpath = "(//form[@id='changePassword:SiteTemplate:theForm']//label)[1]")
	private WebElement newPasswordText;

	/**
	 * @return the newPasswordText
	 */
	public WebElement getNewPasswordText(int timeOut) {
		return isDisplayed(driver, newPasswordText, "Visibility", timeOut,
				"new password text present on forgot password page");
	}

	@FindBy(xpath = "(//form[@id='changePassword:SiteTemplate:theForm']//label)[2]")
	private WebElement verifyYourPasswordText;

	/**
	 * @return the verifyYourPasswordText
	 */
	public WebElement getVerifyYourPasswordText(int timeOut) {
		return isDisplayed(driver, verifyYourPasswordText, "Visibility", timeOut,
				"verify your password text present on forgot password page");
	}

	@FindBy(xpath = "//input[@id='changePassword:SiteTemplate:theForm:cpwbtn']")
	private WebElement changePasswordBtnForgotPassword;

	/**
	 * @return the changePasswordBtnForgotPassword
	 */
	public WebElement getChangePasswordBtnForgotPassword(int timeOut) {
		return isDisplayed(driver, changePasswordBtnForgotPassword, "Visibility", timeOut,
				"change password buton on forgot password page");
	}

	@FindBy(xpath = "//td[contains(text(),'Please contact our')]")
	private WebElement pleaseContactOurLabel;

	/**
	 * @return the pleaseContactOurLabel
	 */
	public WebElement getPleaseContactOurLabel(int timeOut) {
		return isDisplayed(driver, pleaseContactOurLabel, "Visibility", timeOut, "Please contact our, label text");
	}

	@FindBy(xpath = "//a[contains(text(),'Customer Support')]")
	private WebElement customerSupportLink;

	/**
	 * @return the customerSupportLink
	 */
	public WebElement getCustomerSupportLink(int timeOut) {
		return isDisplayed(driver, customerSupportLink, "Visibility", timeOut,
				"Customer support url on forgot password page");
	}

	@FindBy(xpath = "//a[contains(text(),'close')]")
	private WebElement closeButtonForgotPassword;

	/**
	 * @return the closeButtonForgotPassword
	 */
	public WebElement getCloseButtonForgotPassword(int timeOut) {
		return isDisplayed(driver, closeButtonForgotPassword, "Visibility", timeOut,
				"close button forgot password page");
	}

	@FindBy(xpath = "//a[contains(text(),'About Navatar Group')]")
	private WebElement aboutNavatarLink;

	/**
	 * @return the aboutNavatarLink
	 */
	public WebElement getAboutNavatarLink(int timeOut) {
		return isDisplayed(driver, aboutNavatarLink, "Visibility", timeOut, "About Navatar url on forgot page");
	}

	@FindBy(xpath = "//a[contains(text(),'Contact Us')]")
	private WebElement contactUsLink;

	/**
	 * @return the contactUsLink
	 */
	public WebElement getContactUsLink(int timeOut) {
		return isDisplayed(driver, contactUsLink, "Visibility", timeOut, "contact us url on forgot password url");
	}

	/****************************************
	 * forgot password error messages
	 *********************************/
	@FindBy(xpath = "(//li[contains(text(),'Validation Error: Value is required')])[1]")
	private WebElement valueIsRequiredError;

	/**
	 * @return the valueIsRequiredError
	 */
	public WebElement getValueIsRequiredError(int timeOut) {
		return isDisplayed(driver, valueIsRequiredError, "Visibility", timeOut,
				"value is required error message on forgot password page");
	}

	@FindBy(xpath = "//div[contains(text(),'Error: You cannot reuse this old password')]")
	private WebElement cannotReuseOldPassword;

	/**
	 * @return the cannotReuseOldPassword
	 */
	public WebElement getCannotReuseOldPassword(int timeOut) {
		return isDisplayed(driver, cannotReuseOldPassword, "Visibility", timeOut,
				"cannot reuse old password error message on forgot password page");
	}

	@FindBy(xpath = "//div[contains(text(),'The passwords do not match')]")
	private WebElement passwordsNotMatch;

	/**
	 * @return the passwordsNotMatch
	 */
	public WebElement getPasswordsNotMatch(int timeOut) {
		return isDisplayed(driver, passwordsNotMatch, "Visibility", timeOut,
				"Passwords do not match error message on forgot password page");
	}

	public WebElement getAppNameXpathInLightning(String appName, int timeOut) {
		String xpath = "//span[contains(@class,'appName')]//span[text()='" + appName + "']";
		return isDisplayed(driver, FindElement(driver, xpath, appName + " App Name Xpath", action.BOOLEAN, timeOut),
				"visibility", timeOut, appName + " App Name xpath in Lightning");
	}

	@FindBy(xpath = "//button[@class='slds-button slds-show']/div[@class='slds-icon-waffle']")
	private WebElement appLuncherXpath;

	public WebElement getAppLuncherXpath(int timeOut) {
		return isDisplayed(driver, appLuncherXpath, "Visibility", timeOut, "app luncher xpath");
	}

	@FindBy(xpath = "//input[@placeholder='Search apps and items...']")
	private WebElement searchAppTextBoxInAppLuncher;

	public WebElement getSearchAppTextBoxInAppLuncher(int timeOut) {
		return isDisplayed(driver, searchAppTextBoxInAppLuncher, "Visibility", timeOut,
				"search app text box in app luncher ");
	}

	public WebElement getAppNameLabelTextInAppLuncher(String appName, int timeOut) {
		String xpath = "//a[@data-label='" + appName + "'][@role ='option']";
		return isDisplayed(driver,
				FindElement(driver, xpath, appName + " app name xpath in App luncher Name ", action.BOOLEAN, timeOut),
				"visibility", timeOut, appName + " app name xpath in App luncher Name ");

	}

	public WebElement getProfilePageLink(String projectName, String user, int timeOut) {
		String xpath = "//a[text()='" + user + "']/../../..//img[@alt='User']";
		return isDisplayed(driver, FindElement(driver, xpath, " user profile link", action.BOOLEAN, timeOut),
				"visibility", timeOut, " app name xpath in App luncher Name ");

	}

	@FindBy(xpath = "//button[@title='Close']/lightning-icon/parent::button")
	private WebElement notificationPopup;

	public WebElement notificationPopup(int timeOut) {
		return isDisplayed(driver, notificationPopup, "Visibility", timeOut, "notificationPopup");
	}

}
