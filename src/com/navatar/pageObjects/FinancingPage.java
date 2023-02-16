/**
 * 
 */
package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.action;

import static com.navatar.generic.CommonLib.*;
/**
 * @author Parul Singh
 *
 */
public class FinancingPage extends BasePageBusinessLayer {

	/**
	 * @param driver
	 */
	public FinancingPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath = "//button[@title='Move selection to Chosen']")
	private WebElement addButtonMultipleBox_Lightning;

	public WebElement getAddButtonMultipleBox(int timeOut) {
		return isDisplayed(driver, addButtonMultipleBox_Lightning, "Visibility", timeOut, "addButtonMultipleBox Select Box Mode Lightning");

	}
	/**
	 * @return the statusDropDownList
	 */
	public WebElement getListTextbox(String projectName,String memberOrDeal,int timeOut) {
		String xpath = "//*[text()='"+memberOrDeal+"']/..//..//input";
		WebElement ele=FindElement(driver, xpath, memberOrDeal, action.BOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "memberOrDeal");
	}
	@FindBy(xpath = "//label[text()='Firm']/following-sibling::div//button")
	private WebElement FirmCrossIcon;

	public WebElement getFirmCrossIcon(String projectName, int timeOut) {
		return isDisplayed(driver, FirmCrossIcon, "Visibility", timeOut, "Firm Cross Icon");
	}
}
