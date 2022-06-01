package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import static com.navatar.generic.CommonLib.*;
public class CoInvestmentSettingsTab extends NavatarSetupPageBusinessLayer {

	public CoInvestmentSettingsTab(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	
	

}
