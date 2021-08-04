/**
 * 
 */
package com.navatar.pageObjects;

import static com.navatar.generic.AppListeners.appLog;
import static com.navatar.generic.CommonLib.FindElement;
import static com.navatar.generic.CommonLib.ThreadSleep;
import static com.navatar.generic.CommonLib.click;
import static com.navatar.generic.CommonLib.clickUsingJavaScript;
import static com.navatar.generic.CommonLib.matchTitle;
import static com.navatar.generic.CommonLib.scrollDownThroughWebelement;
import static com.navatar.generic.CommonLib.sendKeys;
import static com.navatar.generic.CommonLib.switchToDefaultContent;
import static com.navatar.generic.CommonVariables.URL;
import static com.navatar.generic.CommonLib.exit;
import static com.navatar.generic.CommonLib.isDisplayed;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.SDGCreationLabel;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.ExcelUtils;
/**
 * @author Parul Singh
 *
 */
public class LoginPageBusinessLayer extends LoginPage implements LoginErrorPage {
	/**
	 * @param driver
	 */
	public LoginPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	/**@author Akul Bhutani
	 * @param username
	 * @param password
	 * @param appName TODO
	 * @return true/false
	 * @description this method is used to login to lightning
	 */
	public boolean CRMLogin(String username, String password) {
		BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
		driver.get("https://"+URL);
		sendKeys(driver, getUserNameTextBox(20), username, "Username Text Box", action.THROWEXCEPTION);
		sendKeys(driver, getPasswordTextBox(20), password, "Password Text Box", action.THROWEXCEPTION);
		click(driver, getLoginButton(20), "Login Button", action.THROWEXCEPTION);
		click(driver, getLightingCloseButton(10), "Lighting Pop-Up Close Button.", action.BOOLEAN);
		ThreadSleep(1000);
		
		String mode=ExcelUtils.readDataFromPropertyFile("Mode");
		
		if (mode.contains("Light") || mode.contains("light") ) {
			appLog.info("Going for Lighting");
			if (switchToLighting()) {
				appLog.info("Successfully Switched to Lighting");
				return true;
			} else{
				appLog.error("Not Able to Switched to Lighting");
			}
			
		} else {

			appLog.info("Going for Classic");
			if (switchToClassic()) {
				appLog.info("Successfully Switched to Classic");
				return true;
			} else{
				appLog.error("Not Able to Switched to Classic");
			}
			
		}
		if (bp.getSalesForceLightingIcon(20) != null) {
			ThreadSleep(2000);
			click(driver, bp.getSalesForceLightingIcon(60), "sales force lighting icon", action.THROWEXCEPTION);
			ThreadSleep(1000);
			click(driver, bp.getSwitchToClassic(60), "sales force switch to classic link", action.THROWEXCEPTION);
			appLog.info("Sales Force is switched in classic mode successfully.");
		} else {
			appLog.info("Sales Force is open in classic mode.");
		}
		if (matchTitle(driver, "Salesforce - Enterprise Edition", 20) || matchTitle(driver, "Salesforce - Developer Edition", 20)) {
			appLog.info("User Successfully Logged In.");
			return true;
		} else {
			appLog.info("Kindly Check Username and Password.");
			exit("User is not able to log in.");
			return false;
		}
	}
	
	/**@author Akul Bhutani
	 * @param username
	 * @param password
	 * @param appName
	 * @return true/false
	 * @description this method is used to login to crm and then select appname
	 */
	public boolean CRMLogin(String username, String password, String appName) {
        BasePageBusinessLayer bp=new BasePageBusinessLayer(driver);
        driver.get("https://"+URL);
        sendKeys(driver, getUserNameTextBox(20), username, "Username Text Box", action.THROWEXCEPTION);
        sendKeys(driver, getPasswordTextBox(20), password, "Password Text Box", action.THROWEXCEPTION);
        click(driver, getLoginButton(20), "Login Button", action.THROWEXCEPTION);
        click(driver, getLightingCloseButton(10), "Lighting Pop-Up Close Button.", action.BOOLEAN);
        ThreadSleep(1000);
        
        String mode=ExcelUtils.readDataFromPropertyFile("Mode");
        
        if (mode.contains("Light") || mode.contains("light") ) {
            appLog.info("Going for Lighting");
            if (switchToLighting()) {
                appLog.info("Successfully Switched to Lighting");
                if(getAppNameXpathInLightning(appName, 5)!=null) {
                    appLog.info(appName+" app is open successfully in lightning ");
                    return true;
                }else {
                    if(click(driver, getAppLuncherXpath(30), "app luncher xpath", action.SCROLLANDBOOLEAN)) {
                        appLog.info("Clicked on App Luncher Icon");
                        ThreadSleep(3000);
                        if(sendKeys(driver, getSearchAppTextBoxInAppLuncher(30), appName, "search text box in app luncher", action.SCROLLANDBOOLEAN)) {
                            appLog.info("Enter value in search app text box : "+appName);
                            ThreadSleep(5000);
                            if(clickUsingJavaScript(driver, getAppNameLabelTextInAppLuncher(appName, 30), "app name label text in app luncher")) {
                                appLog.info("clicked on app Name "+appName);
                                ThreadSleep(5000);
                                if(getAppNameXpathInLightning(appName, 60)!=null) {
                                    appLog.info(appName+" App is open successfully in lightning ");
                                    return true;
                                }else {
                                    appLog.error(appName+" App is not open after select app from app luncher");
                                }
                                
                            }else {
                                appLog.error("Not able to click on app Name "+appName+" so cannot select app "+appName+" from app luncher");
                            }
                        }else {
                            appLog.error("Not able to enter app name "+appName+" in search text box so cannot select "+appName);
                        }
                    }else {
                        appLog.error("Not able to click on app luncher icon so cannot select "+appName);
                    }
                }
            } else{
                appLog.error("Not Able to Switched to Lighting");
            }
        } else {

 

            appLog.info("Going for Classic");
            if (switchToClassic()) {
                appLog.info("Successfully Switched to Classic");
                return true;
            } else{
                appLog.error("Not Able to Switched to Classic");
            }
            
        }
        if (bp.getSalesForceLightingIcon(20) != null) {
            ThreadSleep(2000);
            click(driver, bp.getSalesForceLightingIcon(60), "sales force lighting icon", action.THROWEXCEPTION);
            ThreadSleep(1000);
            click(driver, bp.getSwitchToClassic(60), "sales force switch to classic link", action.THROWEXCEPTION);
            appLog.info("Sales Force is switched in classic mode successfully.");
        } else {
            appLog.info("Sales Force is open in classic mode.");
        }
        if (matchTitle(driver, "Salesforce - Enterprise Edition", 20) || matchTitle(driver, "Salesforce - Developer Edition", 20) || matchTitle(driver, "My Tasks | Salesforce", 20)) {
            appLog.info("User Successfully Logged In.");
            return true;
        } else {
            appLog.info("Kindly Check Username and Password.");
            exit("User is not able to log in.");
            return false;
        }
    }
/*******************************Activity Association****************************/
	/**
	 * @author Akul Bhutani
	 * @param environment
	 * @param mode
	 *@return true/false
	 *@description this method is used to logout
	 */
	public boolean CRMlogout() {
		boolean flag = false;
		List<WebElement> lst = getUserMenuTab_Lightning();
		for (int i = 0; i < lst.size(); i++) {
//			if(isDisplayed(driver, lst.get(i), "visibility", 5, "user menu tab")!=null) {
				if(clickUsingJavaScript(driver,lst.get(i), "User menu")) {
					ThreadSleep(500);
					flag = true;
				}else {
					if(i==lst.size()-1) {
						appLog.error("User menu tab not found");
					}
				}
				
//			}else {
//				if(i==lst.size()-1) {
//					appLog.error("User menu tab not visible so cannot click on it");
//				}
//			}
			
		}
	if(flag) {
		ThreadSleep(500);
		if (clickUsingJavaScript(driver, getLogoutButton( 30), "Log out button")) {
			if (matchTitle(driver, "Login | Salesforce", 20)) {
				appLog.info("User successfully Logged Out");
				return true;
			}
			else {
				appLog.error("Not logged out");
			}
		}else {
			appLog.error("Log out button in user menu tab not found");
		}
	}
	return false;
	}
	
	/**
	 * @author Azhar Alam
	 * @param appName
	 * @param timOut
	 * @return true if successfully search & clcik on App
	 */
	public boolean searchAndClickOnApp(String appName,int timOut) {
		boolean flag = false;

        if(click(driver, getAppLuncherXpath(30), "app luncher xpath", action.SCROLLANDBOOLEAN)) {
            appLog.info("Clicked on App Luncher Icon");
            ThreadSleep(3000);
            if(sendKeys(driver, getSearchAppTextBoxInAppLuncher(30), appName, "search text box in app luncher", action.SCROLLANDBOOLEAN)) {
                appLog.info("Enter value in search app text box : "+appName);
                ThreadSleep(5000);
                if(clickUsingJavaScript(driver, getAppNameLabelTextInAppLuncher(appName, 30), "app name label text in app luncher")) {
                    appLog.info("clicked on app Name "+appName);
                    ThreadSleep(5000);
                    if(getAppNameXpathInLightning(appName, 10)!=null) {
                        appLog.info(appName+" App is open successfully in lightning ");
                        flag =  true;
                    }else {
                        appLog.error(appName+" App is not open after select app from app luncher");
                    }
                    
                }else {
                    appLog.error("Not able to click on app Name "+appName+" so cannot select app "+appName+" from app luncher");
                }
            }else {
                appLog.error("Not able to enter app name "+appName+" in search text box so cannot select "+appName);
            }
        }else {
            appLog.error("Not able to click on app luncher icon so cannot select "+appName);
        }
    
        return flag;
		
	}
	
	
	
}
