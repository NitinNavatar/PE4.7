package com.navatar.pageObjects;

import static com.navatar.generic.AppListeners.appLog;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.BaseLib;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
import com.navatar.generic.EnumConstants.TabName;
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
	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
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

	public boolean createFundRaising(String environment,String mode,String fundraisingName, String fundName, String legalName,String stage) {
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
						
						if (click(driver, getFundraisingStage(60), "Fundraising Stage", action.SCROLLANDBOOLEAN)) {
							WebElement fundTypeEle = FindElement(driver,
									"//label[text()='Stage']/following-sibling::div//span[@title='" + stage + "']", stage,
									action.SCROLLANDBOOLEAN, 10);
							ThreadSleep(500);
							if (click(driver, fundTypeEle, stage, action.SCROLLANDBOOLEAN)) {

							} else {
								appLog.error("Not able to Select Fund Type");
							}
						} else {
							appLog.error("Not able to Click on Fund Type");
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
	
	public boolean verifyfundraisingContacts(String headder,String Name,String firm,String role,String email) {

		boolean flag =true;
		try {
			String headerxpath = "//*[text()='Fundraising Contacts']/ancestor::article//*[text()='Name']/ancestor::th//following-sibling::th//*[text()='Firm']/ancestor::th//following-sibling::th//*[text()='Role']/ancestor::th//following-sibling::th//*[text()='Email']"; 
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
				log(LogStatus.ERROR, "Grid value for "+Name+" with "+firm+" "+role+" "+email+" not verified", YesNo.Yes);
				BaseLib.sa.assertTrue(false, "Grid value for "+Name+" with "+firm+" "+role+" "+email+" not verified");
				flag=false;
			}

//			headerxpath = "//*[text()='Fundraising Contacts']/ancestor::article//tr//*[text()='"+Name+"']/ancestor::td//following-sibling::td/span//img"; 
//			ele = FindElement(driver, headerxpath, primary, action.BOOLEAN, 10);
//			if (primary.equalsIgnoreCase("Checked")) {
//				expcted="checked";
//			} else {
//				expcted="unchecked";
//			}
//			if (ele!=null) {
//				log(LogStatus.INFO,primary+" Verified " , YesNo.No);
//				actual=ele.getAttribute("class").trim();
//				if (expcted.equals(actual)) {
//					log(LogStatus.INFO, "Grid value for "+Name+" with "+expcted+" verified", YesNo.No);
//				} else {
//					flag=false;
//					log(LogStatus.ERROR, "Grid value for "+Name+" with "+expcted+" not verified Actual : "+actual, YesNo.Yes);
//					BaseLib.sa.assertTrue(false, "Grid value for "+Name+" with "+expcted+" not verified Actual : "+actual);
//				}
//			} else {
//				flag=false;
//				log(LogStatus.ERROR, "Grid value for "+Name+" with "+expcted+" not verified Actual : "+actual, YesNo.Yes);
//				BaseLib.sa.assertTrue(false, "Grid value for "+Name+" with "+expcted+" not verified Actual : "+actual);
//
//			}


		} catch (Exception e1) {
			// TODO Auto-generated catch block
			log(LogStatus.ERROR, "Grid value for "+Name+" with "+firm+" "+role+" "+email+" not verified", YesNo.Yes);
			BaseLib.sa.assertTrue(false, "Grid value for "+Name+" with "+firm+" "+role+" "+email+" not verified");

		}
		return flag;
	}

	public boolean deleteFundraising(String projectName, int timeout)
	{
		boolean flag=false;
		if (bp.clickOnShowMoreActionDownArrow(projectName, PageName.FundraisingPage, ShowMoreActionDropDownList.Delete, 10)) {
			ThreadSleep(2000);
			if(clickUsingJavaScript(driver, getDeleteFundraisingConfirmationMsg(20), projectName))
			{
				log(LogStatus.INFO,"Clicked on Fundraising delete confirmation message " , YesNo.No);
				flag=true;
			}
			else
			{
				log(LogStatus.ERROR,"Not able to click on Fundraising delete confirmation message " , YesNo.No);
			}
		}
		else {
			appLog.error("Not Able to Click on edit Button");
		}
		return flag;
	}

	public boolean clickOnCreatedFundraising(String environment, String mode, String fundraisingName) {
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
			if (click(driver, getGoButton(60), "Go Button", action.BOOLEAN)) {
				WebElement partnershipName = FindElement(driver,
						"//div[@class='x-panel-bwrap']//span[text()='" + fundraisingName + "']", "Partnership Legal Name",
						action.BOOLEAN, 60);
				if (partnershipName != null) {
					if (click(driver, partnershipName, "Partnership Name", action.SCROLLANDBOOLEAN)) {
						appLog.info("Clicked on partnership name" + fundraisingName + "successfully.");
						return true;
					} else {
						appLog.error("Not able to click on partnership name");
					}
				} else {
					appLog.error("Partnership name is not displaying");
				}
			} else {
				appLog.error("Not able to click on go button so cannot click on created partnership");
			}
		} else {
			if (clickOnAlreadyCreated_Lighting(environment, mode, TabName.Fundraising, fundraisingName, 30)) {
				appLog.info("Clicked on target name" + fundraisingName + "successfully.");
				return true;
			} else {
				appLog.error("Not able to click on target name : " + fundraisingName);
				return false;
			}
		}
		return false;
	}

	public boolean changeFundraisingStage(String projectName, String mode, String stage, int timeOut) {
		boolean flag = false;
		stage = stage.replace("_", " ");
		ThreadSleep(2000);
		if (clickOnShowMoreActionDownArrow(projectName, PageName.Object4Page, ShowMoreActionDropDownList.Edit,
				10)) {
			ThreadSleep(2000);
			if (click(driver, getTargetStage(projectName, timeOut), "Fundraising stage : " + stage,
					action.SCROLLANDBOOLEAN)) {
				ThreadSleep(2000);
				appLog.info("Clicked on target stage");
				String xpath = "//span[@title='" + stage + "']";
				// String xpath="//div[@class='select-options']//li/a[@title='"+stage+"']";
				WebElement dealStageEle = FindElement(driver, xpath, stage, action.SCROLLANDBOOLEAN, timeOut);
				ThreadSleep(2000);
				if (click(driver, dealStageEle, stage, action.SCROLLANDBOOLEAN)) {
					appLog.info("Selected Fundraising stage : " + stage);
				} else {
					appLog.error("Not able to Select on Fundraising stage : " + stage);
				}

			} else {
				appLog.error("Not able to Click on Fundraising stage : ");
			}
			ThreadSleep(2000);
			if (click(driver, getEditSaveButton(30), "Save Button", action.SCROLLANDBOOLEAN)) {
				appLog.info("Click on save Button");
				flag = true;
				ThreadSleep(2000);
			} else {
				appLog.error("Not Able to Click on save Button");
			}
		} else {
			appLog.error("Not Able to Click on edit Button");
		}
		return flag;
	}

}
