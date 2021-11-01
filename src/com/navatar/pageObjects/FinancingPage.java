/**
 * 
 */
package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.navatar.generic.EnumConstants.Mode;

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
	

}
