package com.navatar.pageObjects;

import static com.navatar.generic.AppListeners.appLog;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.server.handler.SwitchToFrame;
import org.openqa.selenium.support.FindBy;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.SoftAssert;
import com.navatar.generic.EnumConstants.AddressAction;
import com.navatar.generic.EnumConstants.ContactPageFieldLabelText;
import com.navatar.generic.EnumConstants.LimitedPartnerPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RecordType;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.CommonLib.*;

public class EditPageBusinessLayer extends EditPage implements EditPageErrorMessage {
	
	public EditPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	

	/**
	 * @return the editPageSeachTextBox
	 */
	public WebElement getEditPageSeachValueLink(String projectName,String searchValue,int timeOut) {
		String xpath = "//span[text()='"+searchValue+"']";
		WebElement ele = FindElement(driver, xpath, "Search Value : "+searchValue, action.BOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "Search Value : "+searchValue);
	}
	
	
	public boolean clickOnELGSeachValueLink(String projectName,String searchValue,int timeOut) {
		 click(driver, getElgDataProviderTextBoxSearchIcon(projectName, 30), searchValue, action.BOOLEAN);
		String xpath = "//div[contains(@id,'dropdown-element')]//*[@title='"+searchValue+"']";
		WebElement ele = FindElement(driver, xpath, "Search Value : "+searchValue, action.BOOLEAN, timeOut);
		ele =  isDisplayed(driver, ele, "Visibility", timeOut, "Search Value : "+searchValue);
		return click(driver, ele, searchValue, action.BOOLEAN);
	}
	
	
	public boolean clickOnEditPageLink() {
		boolean flag = false;
		if(click(driver, getSettingLink_Lighting(20), "setting icon", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "clicked on setting icon", YesNo.No);
			ThreadSleep(1000);
			if(click(driver, getEditPageLink_Lighting(20), "edit page link", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on edit page link", YesNo.No);
				flag=true;
			}else {
				log(LogStatus.ERROR,"Not able to click on edit page link",YesNo.Yes);
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on setting icon", YesNo.Yes);
		}
		return flag;
	}
	
	
	public boolean dragAndDropLayOutFromEditPage(String projectName,PageName pageName, RelatedTab relatedTab,String DropComponentName, String fieldSetApiName) {
		boolean flag = false;
		WebElement ele=null,dropComponentXpath=null,dropLocation=null;
		if(switchToFrame(driver, 30, getEditPageFrame(projectName,30))) {
			String related = relatedTab.toString().replace("_", " ");
			String relatedTabXpath="//*[@role='tablist']//li//*[@title='"+related+"' or text()='"+related+"']";
			ele = isDisplayed(driver, FindElement(driver, relatedTabXpath, relatedTab.toString(), action.SCROLLANDBOOLEAN, 10)
					, "visiblity", 10, relatedTab.toString());
			if (ele!=null) {
				if (click(driver,ele,relatedTab.toString()+" tab xpath", action.BOOLEAN)) {
					log(LogStatus.INFO,"Click on Sub Tab : "+RelatedTab.Investment,YesNo.No);
					ThreadSleep(2000);
					switchToDefaultContent(driver);
					if(sendKeys(driver,getEditPageSeachTextBox(projectName, 10), DropComponentName,DropComponentName+" component xpath", action.BOOLEAN)) {
						log(LogStatus.INFO, "Enter component name in search box : "+DropComponentName, YesNo.No);
						
						String xpath = "//span[@title='"+DropComponentName+"' or text()='"+DropComponentName+"']";
						dropComponentXpath =  isDisplayed(driver, FindElement(driver, xpath, "Search Value : "+DropComponentName, action.BOOLEAN, 10), "Visibility", 10, "Search Value : "+DropComponentName);
						if(dropComponentXpath!=null) {
//							Actions builder = new Actions(driver);
//							builder.clickAndHold(dropComponentXpath).build().perform();
					        switchToFrame(driver, 30, getEditPageFrame(projectName,30));
					        String dropLocationXpath="(//a[@class='flexipageEditorContainerPlaceholder'])[1]";
					        dropLocation = FindElement(driver, dropLocationXpath,"header xpath",action.BOOLEAN, 10);
					    	if (dropLocation!=null) {
					    		switchToDefaultContent(driver);
                                Screen screen = new Screen();
                                try {
                                
//                                		screen.dropAt("\\AutoIT\\AddComponentHere.PNG");
                                		screen.dragDrop("\\AutoIT\\FIeldSet.PNG", "\\AutoIT\\AddComponentHere.PNG");
                                	
                                    ThreadSleep(500);
                                    if(sendKeys(driver, getFieldSetNameTextBox(10), fieldSetApiName, "field set name text box", action.BOOLEAN)) {
                                    	log(LogStatus.INFO, "field set name : "+fieldSetApiName, YesNo.No);
                                    	if(click(driver, getCustomTabSaveBtn(projectName, 10), "save button", action.BOOLEAN)) {
                                    		log(LogStatus.INFO, "clicked on save button", YesNo.No);
                                    		ThreadSleep(2000);
                                    		if(clickUsingJavaScript(driver, getBackButton(10), "back button", action.BOOLEAN)) {
                                    			log(LogStatus.PASS, "clicked on back button", YesNo.No);
                                    			flag=true;
                                    		}else {
												log(LogStatus.ERROR, "Not able to click on back button so cannot back on page ", YesNo.Yes);
											}
                                    	}else {
											log(LogStatus.ERROR, "Not able to click on save button so cannot add field set : "+fieldSetApiName, YesNo.No);
										}
                                    }else {
										log(LogStatus.ERROR, "Not able to enter field set name : "+fieldSetApiName+" so cannot add field set", YesNo.Yes);
									}
                                } catch (FindFailed e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
					    	}else {
					    		log(LogStatus.ERROR, "Drop location is not visible in list so cannot drag and drop component "+DropComponentName+" in "+relatedTab.toString(), YesNo.Yes);
							}
						}else {
							log(LogStatus.ERROR, "Searched component is not visible in list so cannot drag and drop component "+DropComponentName+" in "+relatedTab.toString(), YesNo.Yes);
						}
					}else {
						log(LogStatus.ERROR, "Not able to search on component so cannot drag and drop component "+DropComponentName+" in "+relatedTab.toString(), YesNo.Yes);
					}
				}else {
					log(LogStatus.ERROR, "Not able to click on related tab so cannot drag and drop component "+DropComponentName+" in "+relatedTab.toString(), YesNo.Yes);
				}
			}else {
				log(LogStatus.ERROR, "Related tab is not present so cannot drag and drop component "+DropComponentName+" in "+relatedTab.toString(), YesNo.Yes);
			}
		}else {
			log(LogStatus.ERROR, "Cannot switch in edit page iframe cannot drag and drop component "+DropComponentName+" in "+relatedTab.toString(), YesNo.Yes);
		}
		switchToDefaultContent(driver);
		return flag;
		
		
		
	}

	/**
	 * @return the editPageSeachTextBox
	 */
	public WebElement getElementWithText(String projectName,String searchValue,int timeOut) {
		String xpath = "//*[text()='"+searchValue+"']/ancestor::div[@role='tablist']/..";
		WebElement ele = FindElement(driver, xpath, "Search Value : "+searchValue, action.BOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "Search Value : "+searchValue);
	}
	
	/**
	 * @return the dragNDropUsingScreen
	 */
	public boolean dragNDropUsingScreen(String projectName,WebElement source,String imagePath,int timeOut) {
		boolean flag=false;
		Actions act = new Actions(driver);
//		WebElement source=getEditPageSeachValueLink(projectName, sValue, 10);
		act.clickAndHold(source).build().perform();
		ThreadSleep(1000);
		Screen screen = new Screen();
		try {
			screen.dropAt(imagePath);
			Robot robot = new Robot();
			ThreadSleep(1000);
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK); // press left click	
	       ThreadSleep(1000);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			flag=true;
		} catch (FindFailed | AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	
	
	/**
	 * @return the dragNDropUsingScreen
	 */
	public boolean dragNDropUsingJscript(WebDriver driver,WebElement source,WebElement target) {
		boolean flag=false;
	    try {
	    	final String java_script =
					"var src=arguments[0],tgt=arguments[1];var dataTransfer={dropEffe" +
							"ct:'',effectAllowed:'all',files:[],items:{},types:[],setData:fun" +
							"ction(format,data){this.items[format]=data;this.types.append(for" +
							"mat);},getData:function(format){return this.items[format];},clea" +
							"rData:function(format){}};var emit=function(event,target){var ev" +
							"t=document.createEvent('Event');evt.initEvent(event,true,false);" +
							"evt.dataTransfer=dataTransfer;target.dispatchEvent(evt);};emit('" +
							"dragstart',src);emit('dragenter',tgt);emit('dragover',tgt);emit(" +
							"'drop',tgt);emit('dragend',src);";
			            ((JavascriptExecutor)driver).executeScript(java_script, source, target);
			            flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return flag;
	}
	
	
	/**
	 * @return the editPageSeachTextBox
	 */
	public WebElement getElementWithText1(String projectName,String searchValue,int timeOut) {
		String xpath = "//*[text()='"+searchValue+"']/ancestor::article";
		WebElement ele = FindElement(driver, xpath, "Search Value : "+searchValue, action.BOOLEAN, timeOut);
		return isDisplayed(driver, ele, "Visibility", timeOut, "Search Value : "+searchValue);
	}
	
	public boolean dragAndDropAccordian(String projectName,PageName pageName, String DropComponentName, String[] fieldValues) {
		boolean flag = true;
		Actions actions = new Actions(driver);
		WebElement ele=null,dropComponentXpath=null,dropLocation=null;
		if(switchToFrame(driver, 30, getEditPageFrame(projectName,30))) {
			log(LogStatus.INFO,"Click on Sub Tab : "+RelatedTab.Investment,YesNo.No);
			ThreadSleep(2000);
			switchToDefaultContent(driver);
			if(sendKeys(driver,getEditPageSeachTextBox(projectName, 10), DropComponentName,DropComponentName+" component xpath", action.BOOLEAN)) {
				log(LogStatus.INFO, "Enter component name in search box : "+DropComponentName, YesNo.No);

				String xpath = "//span[@title='"+DropComponentName+"' or text()='"+DropComponentName+"']";
				appLog.error(">>>>");
				dropComponentXpath =  isDisplayed(driver, FindElement(driver, xpath, "Search Value : "+DropComponentName, action.BOOLEAN, 10), "Visibility", 10, "Search Value : "+DropComponentName);
				if(dropComponentXpath!=null) {
					//							Actions builder = new Actions(driver);
					//							builder.clickAndHold(dropComponentXpath).build().perform();
					switchToFrame(driver, 30, getEditPageFrame(projectName,30));
					String dropLocationXpath="(//a[@class='flexipageEditorContainerPlaceholder'])[1]";
					dropLocation = FindElement(driver, dropLocationXpath,"header xpath",action.BOOLEAN, 10);

					if (dropLocation!=null) {
						if (clickUsingJavaScript(driver,dropLocation , "add component", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on add component link", YesNo.No);

							switchToDefaultContent(driver);
							if (click(driver, dropComponentXpath,"accordion", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on accordion link"+dropLocation, YesNo.No);
								flag=true;

							}
							switchToDefaultContent(driver);
							Screen screen = new Screen();
						try {

							//                                		screen.dropAt("\\AutoIT\\AddComponentHere.PNG");
							screen.dragDrop("\\AutoIT\\FIeldSet.PNG", "\\AutoIT\\AddComponentHere.PNG");

							ThreadSleep(500);
						} catch (FindFailed e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							flag=false;
						}
							 if (flag) {
								 String value="",field="";
								 for (String fieldValue:fieldValues) {
									 value=fieldValue.split("<break>")[1];
									 field=fieldValue.split("<break>")[0];
									 if(sendKeys(driver, getFieldTextbox(projectName, field, 10), value, field, action.BOOLEAN)) {
										 log(LogStatus.INFO, "field : "+field+", value: "+value, YesNo.No);
									 }else {
										 log(LogStatus.ERROR, "Not able to enter : "+value+" on"+field, YesNo.Yes);
										 flag=false;
									 }
								 }
								 if (!isSelected(driver, getexpandedCheckbox(projectName, 10), "expanded")) {
									 if (click(driver, getexpandedCheckbox(projectName, 10),  "expanded", action.BOOLEAN)) {

										 log(LogStatus.INFO, "expanded checkbox is now selected", YesNo.No);
									 }
									 else {
										 log(LogStatus.ERROR, "could not click on expanded", YesNo.No);
										 flag=false;
									 }

								 }
								 else {
									 log(LogStatus.INFO, "expanded checkbox is already selected", YesNo.No);

								 }
								 if(click(driver, getCustomTabSaveBtn(projectName, 10), "save button", action.BOOLEAN)) {
									 log(LogStatus.INFO, "clicked on save button", YesNo.No);
									 ThreadSleep(2000);
									 actions.moveToElement(getBackButton(10)).build().perform();
									 ThreadSleep(2000);
									 if(clickUsingJavaScript(driver, getBackButton(10), "back button", action.BOOLEAN)) {
										 log(LogStatus.PASS, "clicked on back button", YesNo.No);
										 flag=true;
									 }else {
										 log(LogStatus.ERROR, "Not able to click on back button so cannot back on page ", YesNo.Yes);
									 }
								 }else {
									 log(LogStatus.ERROR, "Not able to click on save button so cannot create accordion : ", YesNo.No);
								 }
							 }else {
								 log(LogStatus.ERROR, "Drop location is not visible in list so cannot drag and drop component "+DropComponentName, YesNo.Yes);
							 }
						}else {
							log(LogStatus.ERROR, "could not click on add component link", YesNo.Yes);
						}
					}else {
						log(LogStatus.ERROR, "add component link is not visible in list so cannot drag and drop component "+DropComponentName, YesNo.Yes);
					}
				}else {
					log(LogStatus.ERROR, "add component link is not visible in list so cannot drag and drop component "+DropComponentName, YesNo.Yes);
				}
			}else {
				log(LogStatus.ERROR, "Not able to search on component so cannot drag and drop component "+DropComponentName, YesNo.Yes);
			}
		}else {
			log(LogStatus.ERROR, "Cannot switch in edit page iframe cannot drag and drop component "+DropComponentName, YesNo.Yes);
		}
		switchToDefaultContent(driver);
		return flag;



	}
	public WebElement clickOnAccordion(String projectName, TabName tabName) {
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
		String xpath = "//div[contains(@class,'RelatedListAccordion')]/following-sibling::article//header//a[contains(text(),'"+bp.getTabName(projectName,tabName)+"')]/ancestor::article/../following-sibling::*//div[@title='Move component']";
		WebElement ele=FindElement(driver, xpath, "accordian", action.BOOLEAN, 10);
		return isDisplayed(driver, ele, "visiblity",10, "acordion");
	}

	public String ContactSDGQuery(String fieldName) {
		return "SELECT Id, Name, Title,"+fieldName+" Industry__c, Region__c,Profile_Image__c FROM Contact WHERE (AccountId = '<<recordId>>') ORDER BY Name ASC";
}
	public String DealTeamSDGQuery(String fieldName) {
		return "SELECT Member__c, Member__r.Name,Member__r.Title,"+fieldName+"Team_Member_Role__c,Type__c,Member__r.MediumPhotoURL FROM Deal_Team__c WHERE ( Pipeline__c = '<<recordId>>' AND (member__c <> null)) ORDER BY Id ASC";
}

	public boolean dragAndDropCalender(String projectName,String source,String target,PageName pageName, RelatedTab relatedTab,String DropComponentName, String[] fieldValues) {
	boolean flag=false;
	Actions actions = new Actions(driver);	 
	String value="",field="";
	appLog.info(">>>>");
	ThreadSleep(3000);
	dragNDropUsingScreen(projectName, source, target, 10);
	ThreadSleep(3000);
		 for (String fieldValue:fieldValues) {
			 value=fieldValue.split("<break>")[1];
			 field=fieldValue.split("<break>")[0];
			 field=field.replace("_", " ");
			 if(sendKeys(driver, getFieldTextbox(projectName, field, 10), value, field, action.BOOLEAN)) {
				 log(LogStatus.INFO, "field : "+field+", value: "+value, YesNo.No);
			 }else {
				 log(LogStatus.ERROR, "Not able to enter : "+value+" on"+field, YesNo.Yes);
				 flag=false;
			 }
		 }if(click(driver, getCustomTabSaveBtn(projectName, 10), "save button", action.BOOLEAN)) {
			 log(LogStatus.INFO, "clicked on save button", YesNo.No);
			 ThreadSleep(2000);
			 actions.moveToElement(getBackButton(10)).build().perform();
			 ThreadSleep(2000);
			 if(clickUsingJavaScript(driver, getBackButton(10), "back button", action.BOOLEAN)) {
				 log(LogStatus.PASS, "clicked on back button", YesNo.No);
				 flag=true;
			 }else {
				 log(LogStatus.ERROR, "Not able to click on back button so cannot back on page ", YesNo.Yes);
			 }
		 }else {
			 log(LogStatus.ERROR, "Not able to click on save button so cannot create accordion : ", YesNo.No);
		 }
		return flag;
	}

	public boolean clickOnCalenderEditPage(String projectName) {
		String xpath = "//button[text()='Their Events']/../../../../../preceding-sibling::div";
		WebElement ele=FindElement(driver, xpath, "accordian", action.BOOLEAN, 10);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("return arguments[0].setAttribute('class','flexipageEditorNode flexipageEditorElement flexipageEditorComponent sf-interactions-liveSelected sf-interactions-proxyToolboxVisible')", ele);
		clickUsingJavaScript(driver, ele, "calender move component");
		
		return true;
	}
		
}
