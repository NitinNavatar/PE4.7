package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.EnumConstants.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AttendeePage extends BasePageBusinessLayer{

	public AttendeePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	@FindBy(xpath = "//span[contains(text(),'Attendees')]/ancestor::header/following-sibling::div//a[@title='New']")
	private WebElement newAttendeeButton;
	
	
	public WebElement getnewAttendeeButton(String projectName, int timeOut) {
		return isDisplayed(driver, newAttendeeButton, "Visibility", timeOut, "newAttendeeButton");

	}
	public WebElement labelTextBox(String projectName, String field, int timeOut) {
		String xpath = "//*[text()='"+field+"']/..//input";
		if (field.equalsIgnoreCase(AttendeeLabels.Notes.toString()))
			xpath = "//*[text()='Notes']/..//textarea";
		WebElement ele=FindElement(driver, xpath, field, action.BOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, field);

	}
}
