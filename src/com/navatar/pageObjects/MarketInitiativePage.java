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
public class MarketInitiativePage extends BasePageBusinessLayer {

	/**
	 * @param driver
	 */
	public MarketInitiativePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		PageFactory.initElements(driver, this);
	}
	
	

}
