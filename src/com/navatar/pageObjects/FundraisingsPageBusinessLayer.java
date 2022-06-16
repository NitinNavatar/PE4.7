package com.navatar.pageObjects;

import static com.navatar.generic.AppListeners.appLog;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.BaseLib;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.SmokeCommonVariables.SmokeC3_FName;
import static com.navatar.generic.SmokeCommonVariables.SmokeC3_LName;
import static com.navatar.generic.SmokeCommonVariables.SmokeC4_FName;
import static com.navatar.generic.SmokeCommonVariables.SmokeC4_LName;
import static com.navatar.generic.SmokeCommonVariables.Smoke_FR1;

import java.util.ArrayList;
import java.util.List;


public class FundraisingsPageBusinessLayer extends FundraisingsPage {

	public FundraisingsPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * @author Azhar Alam
	 * @param environment
	 * @param mode
	 * @param fundraisingName
	 * @param fundName
	 * @param legalName
	 * @return true if able to create FundRaising
	 */
	public boolean createFundRaising(String environment,String mode,String fundraisingName, String fundName, String legalName) {
		ThreadSleep(5000);
		if (click(driver, getNewButton(environment,mode,60), "New Button", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(500);
			if (sendKeys(driver, getFundraisingName(environment,mode,60), fundraisingName, "FundRaising Name", action.BOOLEAN)) {
				ThreadSleep(500);
				if (sendKeys(driver, getFundName(environment,mode,60), fundName, "Fund Name", action.BOOLEAN)) {
					ThreadSleep(500);
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						ThreadSleep(1000);
						if (click(driver,
								FindElement(driver,
										"//div[contains(@class,'listbox')]//*[@title='"+fundName+"']",
										"Fund Name List", action.THROWEXCEPTION, 30),
								fundName + "   :   Fund Name", action.BOOLEAN)) {
							appLog.info(fundName + "  is present in list.");
						} else {
							appLog.info(fundName + "  is not present in the list.");
						}
					}
					if (sendKeys(driver, getLegalName(environment,mode,60), legalName, "Legal Name", action.BOOLEAN)) {
						ThreadSleep(500);
						if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
							ThreadSleep(1000);
							if (click(driver,
									FindElement(driver,
											"//li//*[@title='"+legalName+"']",
											"Legal Name List", action.THROWEXCEPTION, 30),
									legalName + "   :   Legal Name", action.SCROLLANDBOOLEAN)) {
								appLog.info(legalName + "  is present in list.");
							} else {
								appLog.info(legalName + "  is not present in the list.");
							}
						}
						if (click(driver, getCustomTabSaveBtn(environment,60), "Save Button", action.SCROLLANDBOOLEAN)) {
							ThreadSleep(500);
							
								ThreadSleep(2000);
								String fundraising=null;
								WebElement ele;
								if (Mode.Lightning.toString().equalsIgnoreCase(mode)) {
									String	xpath="//*[contains(text(),'Fundraising')]/..//*[text()='"+fundraisingName+"']";
									 ele = FindElement(driver, xpath, "Header : "+fundraisingName, action.BOOLEAN, 30);
								
								} else {
									ele=getFundraisingNameInViewMode(environment, mode, 60);
								}
								
								if (ele!=null) {
									appLog.info("Fundraising is created successfully.:" + fundraisingName);
									return true;
								} else {
									appLog.info("FundRaising is not created successfully.:" + fundraisingName);
								}
							
						} else {
							appLog.error("Not able to click on save button");
						}
					} else {
						appLog.error("Not able to enter legal Name");
					}
				} else {
					appLog.error("Not able to enter fund name");
				}
			} else {
				appLog.error("Not able to enter value in fundraiisng text box");
			}
		} else {
			appLog.error("Not able to click on new button so we cannot create fundraising");
		}
		return false;
	}

	public boolean clickOnCreatedFundRaising(String environment,String mode,String fundRaising){
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
		if (getSelectedOptionOfDropDown(driver, getViewDropdown(60), "View dropdown", "text").equalsIgnoreCase("All")) {
			if (click(driver, getGoButton(60), "Go button", action.BOOLEAN)) {

			}
			else {
				appLog.error("Go button not found");
				return false;
			}
		}
		WebElement CreatedfundRaising = FindElement(driver,
				"//div[@class='x-panel-bwrap']//a//span[contains(text(),'" + fundRaising + "')]", "FundRaising Name",
				action.BOOLEAN, 60);
		if (CreatedfundRaising != null) {
			if (click(driver, CreatedfundRaising, "FundRaising Name", action.SCROLLANDBOOLEAN)) {
				 CreatedfundRaising = FindElement(driver,
							"//div[@class='x-panel-bwrap']//a//span[contains(text(),'" + fundRaising + "')]", "FundRaising Name",
							action.BOOLEAN, 3);
				 click(driver, CreatedfundRaising, "FundRaising Name", action.SCROLLANDBOOLEAN);
				appLog.info("Clicked on fundRaising name.:" + fundRaising);
				return true;
				} else {
				appLog.error("Not able to click on fundRaisng Name");
		
			}
		} else {
			appLog.error("FundRaising Name is not Displaying.:" + fundRaising);
			
		}
	}else{
		if(clickOnAlreadyCreated_Lighting(environment, mode, TabName.FundraisingsTab, fundRaising, 30)){
			appLog.info("Clicked on fundRaising name.:" + fundRaising);
			return true;
		}else{
			appLog.error("Not able to click on fundRaisng Name : "+fundRaising);
		}
	}
		return false;
	
	}

	
	public List<String> verifyfundraisingContacts(String mode,List<List<String>> contactDetails) {
	
		
		try {
			String headerxpath = "//thead//th//*[contains(text(),'Fundraising Contact ID')]/../..//following-sibling::th//*[contains(text(),'Name')]/../..//following-sibling::th//*[contains(text(),'Firm')]/../..//following-sibling::th//*[contains(text(),'Role')]/../..//following-sibling::th//*[contains(text(),'Primary')]"; 
					WebElement ele = FindElement(driver, headerxpath, "Header", action.BOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO,"Header Verified " , YesNo.No);
					} else {
						log(LogStatus.INFO,"Header not Verified " , YesNo.No);
						BaseLib.sa.assertTrue(false,"Header not Verified ");
					
					}
					
					//a[text()='FC - 147']/../../../..
					//following-sibling::td//a[text()='Test M1Contact3']/../../../..
					//following-sibling::td//a[text()='TestM1Institution2']/../../../..
					//following-sibling::td/span//img
					
					List<String> stcontactID= new ArrayList<String>();
					List<String> sndtcontactID= new ArrayList<String>();
					for (List<String> list : contactDetails) {
						
						if (list.size()==1) {
							stcontactID.add(list.get(0));
						} else {
							stcontactID.add(list.get(0));
							sndtcontactID.add(list.get(1));
						}
						
					}
						System.err.println("Value going to vrified : "+stcontactID);
						System.err.println("Value going to vrified");
						String Idxpath= "//a[text()='"+stcontactID.get(0)+"']/../../../..";
						String Contactxpath= "//following-sibling::td//a[text()='"+stcontactID.get(1)+"']/../../../..";
						String Insxpath= "//following-sibling::td//a[text()='"+stcontactID.get(2)+"']/../../../..";
						String roleXpath = "//following-sibling::td";
						String primaryXpath= "//following-sibling::td/span//img";
						
						String fullXpath = Idxpath+Contactxpath+Insxpath+roleXpath;
						
						ele = FindElement(driver, fullXpath, "Value xpath", action.BOOLEAN, 10);
						if (ele!=null) {
							log(LogStatus.INFO,"Role Value Verified " , YesNo.No);
							System.err.println("Role Value Verified Text : "+ele.getText().trim()+" jkjj");
							
							if (stcontactID.get(3).isEmpty() || stcontactID.equals("")) {
								
								if (ele.getText().trim().isEmpty() || ele.getText().trim().equals("")) {
									log(LogStatus.INFO,"Role Value verified "+sndtcontactID.get(3) , YesNo.No);
								} else {
									log(LogStatus.INFO,"Role Value not verified "+sndtcontactID.get(3) , YesNo.No);
									BaseLib.sa.assertTrue(false,"Role Value not verified "+sndtcontactID.get(3));
								}
								
							} else {

								if (stcontactID.get(3).equals(ele.getText().trim())) {
									log(LogStatus.INFO,"Role Value verified "+sndtcontactID.get(3) , YesNo.No);
								} else {
									log(LogStatus.INFO,"Role Value not verified "+sndtcontactID.get(3) , YesNo.No);
									BaseLib.sa.assertTrue(false,"Role Value not verified "+sndtcontactID.get(3));
								}
								
							}
						} else {
							log(LogStatus.INFO,"Role Value not verified " , YesNo.No);
							BaseLib.sa.assertTrue(false,"Role Value not verified ");
						
						}
						
						
						fullXpath = Idxpath+Contactxpath+Insxpath+primaryXpath;
						
						ele = FindElement(driver, fullXpath, "Value xpath", action.BOOLEAN, 10);
						if (ele!=null) {
							log(LogStatus.INFO,"Primary Value Verified " , YesNo.No);
							System.err.println("Primary Value Verified Text : "+ele.getAttribute("alt").trim()+" jkjj");
							String primaryCheck="";
							if (sndtcontactID.get(4).contains("Not") || sndtcontactID.get(4).contains("not")) {
								primaryCheck="False";
							}else {
								primaryCheck="True";
							}
							if (primaryCheck.equals(ele.getAttribute("alt").trim())) {
								log(LogStatus.INFO,"Primary Value verified "+sndtcontactID.get(4) , YesNo.No);
							} else {
								log(LogStatus.INFO,"Primary Value not verified "+sndtcontactID.get(4) , YesNo.No);
								BaseLib.sa.assertTrue(false,"Primary Value not verified "+sndtcontactID.get(4));
							}
							
							
						} else {
							log(LogStatus.INFO,"Primary Value not verified " , YesNo.No);
							BaseLib.sa.assertTrue(false,"Primary Value not verified ");
						
						}
						
						if (!sndtcontactID.isEmpty()) {
							
							System.err.println("Value going to vrified : "+sndtcontactID);
							System.err.println("Value going to vrified");
							 Idxpath= "//a[text()='"+sndtcontactID.get(0)+"']/../../../..";
							 Contactxpath= "//following-sibling::td//a[text()='"+sndtcontactID.get(1)+"']/../../../..";
							 Insxpath= "//following-sibling::td//a[text()='"+sndtcontactID.get(2)+"']/../../../..";
							 roleXpath = "//following-sibling::td";
							 primaryXpath= "//following-sibling::td/span//img";
							
							 fullXpath = Idxpath+Contactxpath+Insxpath+roleXpath;
							
							ele = FindElement(driver, fullXpath, "Value xpath", action.BOOLEAN, 10);
							if (ele!=null) {
								log(LogStatus.INFO,"Role Value Verified " , YesNo.No);
								System.err.println("Role Value Verified Text : "+ele.getText().trim()+" jkjj");
							} else {
								log(LogStatus.INFO,"Role Value not verified " , YesNo.No);
								BaseLib.sa.assertTrue(false,"Role Value not verified ");
							
							}
							
							
							fullXpath = Idxpath+Contactxpath+Insxpath+primaryXpath;
							
							ele = FindElement(driver, fullXpath, "Value xpath", action.BOOLEAN, 10);
							if (ele!=null) {
								log(LogStatus.INFO,"Primary Value Verified " , YesNo.No);
								System.err.println("Primary Value Verified Text : "+ele.getAttribute("alt").trim()+" jkjj");
								String primaryCheck="";
								if (sndtcontactID.get(4).contains("Not") || sndtcontactID.get(4).contains("not")) {
									primaryCheck="False";
								}else {
									primaryCheck="True";
								}
								if (primaryCheck.equals(ele.getAttribute("alt").trim())) {
									log(LogStatus.INFO,"Primary Value verified "+sndtcontactID.get(4) , YesNo.No);
								} else {
									log(LogStatus.INFO,"Primary Value not verified "+sndtcontactID.get(4) , YesNo.No);
									BaseLib.sa.assertTrue(false,"Primary Value not verified "+sndtcontactID.get(4));
								}
							} else {
								log(LogStatus.INFO,"Primary Value not verified " , YesNo.No);
								BaseLib.sa.assertTrue(false,"Primary Value not verified ");
							
							}
						}
						
				
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<String> result = new ArrayList<String>();
	
		return result;	
	}
	
	public String getfundraisingContactID(String mode,String contactName) {
		WebElement ele= null;
		String id="";
		String xpath="";
		if(mode.equalsIgnoreCase(Mode.Lightning.toString())) {
			xpath="//*[contains(text(),'Fundraising Contact ID')]/../../../../following-sibling::tbody/tr//td//a[text()='"+contactName+"']/../../../../preceding-sibling::td//a";
		}else {
			xpath="//th[text()='Fundraising Contact: Fundraising Contact ID']/../following-sibling::tr//td/a[text()='"+contactName+"']/../preceding-sibling::th/a";
		}
		ele=FindElement(driver,xpath, "Contact id",action.BOOLEAN, 10);
		if(ele!=null) {
			id =ele.getText().trim();
			log(LogStatus.INFO, contactName+" Contact id is: "+id,YesNo.No);
		}else {
			log(LogStatus.ERROR, "Not able to get Contact "+contactName+" contact Id from fundraising page", YesNo.Yes);
		}
		return id;
	}
	
	public boolean verifyfundraisingContacts(String headder,String Name,String firm,String role,String primary) {

		boolean flag =true;
		try {
			String headerxpath = "//*[text()='Fundraising Contacts']/ancestor::article//*[text()='Name']/ancestor::th//following-sibling::th//*[text()='Firm']/ancestor::th//following-sibling::th//*[text()='Role']/ancestor::th//following-sibling::th//*[text()='Primary']"; 
			WebElement ele = FindElement(driver, headerxpath, "Header", action.BOOLEAN, 10);
			if (ele!=null) {
				log(LogStatus.INFO,"Header Verified " , YesNo.No);
			} else {
				log(LogStatus.INFO,"Header not Verified " , YesNo.No);
				BaseLib.sa.assertTrue(false,"Header not Verified ");
				flag=false;

			}
			
		
			ThreadSleep(2000);
			String xpath = "//*[text()='Fundraising Contacts']/ancestor::article//tr//td//*[@class='cls_des']";
			List<WebElement> eleList = FindElements(driver, xpath, "FR Contact Grid Data for : "+Name);
			String actual="";
			String expcted=Name+","+firm+","+role;
			
			if (!eleList.isEmpty()) {
				
				if(compareMultipleList(driver,expcted , eleList).isEmpty()) {
					
					log(LogStatus.INFO, "Grid value "+expcted+" verified", YesNo.No);

				}else {
					log(LogStatus.ERROR, "Grid value "+expcted+" is not  verified", YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Grid value "+expcted+" is not verified");
					flag=false;
				}
				
				
			} else {
				log(LogStatus.ERROR, "Grid value for "+Name+" with "+firm+" "+role+" "+primary+" not verified", YesNo.Yes);
				BaseLib.sa.assertTrue(false, "Grid value for "+Name+" with "+firm+" "+role+" "+primary+" not verified");
				flag=false;
			}

			headerxpath = "//*[text()='Fundraising Contacts']/ancestor::article//tr//*[text()='"+Name+"']/ancestor::td//following-sibling::td/span//img"; 
			ele = FindElement(driver, headerxpath, primary, action.BOOLEAN, 10);
			if (primary.equalsIgnoreCase("Checked")) {
				expcted="checked";
			} else {
				expcted="unchecked";
			}
			if (ele!=null) {
				log(LogStatus.INFO,primary+" Verified " , YesNo.No);
				actual=ele.getAttribute("class").trim();
				if (expcted.equals(actual)) {
					log(LogStatus.INFO, "Grid value for "+Name+" with "+expcted+" verified", YesNo.No);
				} else {
					flag=false;
					log(LogStatus.ERROR, "Grid value for "+Name+" with "+expcted+" not verified Actual : "+actual, YesNo.Yes);
					BaseLib.sa.assertTrue(false, "Grid value for "+Name+" with "+expcted+" not verified Actual : "+actual);
				}
			} else {
				flag=false;
				log(LogStatus.ERROR, "Grid value for "+Name+" with "+expcted+" not verified Actual : "+actual, YesNo.Yes);
				BaseLib.sa.assertTrue(false, "Grid value for "+Name+" with "+expcted+" not verified Actual : "+actual);

			}


		} catch (Exception e1) {
			// TODO Auto-generated catch block
			log(LogStatus.ERROR, "Grid value for "+Name+" with "+firm+" "+role+" "+primary+" not verified", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "Grid value for "+Name+" with "+firm+" "+role+" "+primary+" not verified");

		}
		return flag;
	}

}
