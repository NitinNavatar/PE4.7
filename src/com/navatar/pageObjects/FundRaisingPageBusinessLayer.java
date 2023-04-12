/**
 * 
 */
package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.navatar.generic.ExcelUtils;
import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;


import com.navatar.generic.EnumConstants.PageLabel;

import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.AppListeners.*;
import static com.navatar.generic.CommonLib.*;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.management.ObjectName;

/**
 * @author Parul Singh
 *
 */
public class FundRaisingPageBusinessLayer extends FundRaisingPage {

	BasePageBusinessLayer bp = new BasePageBusinessLayer(driver);
	
	/**
	 * @param driver
	 */
	public FundRaisingPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	// Lightning Mthod....
	/**
	 * @author Azhar Alam
	 * @param environment
	 * @param mode
	 * @param fundraisingName
	 * @param fundName
	 * @param legalName
	 * @param closing                TODO
	 * @param stage                  TODO
	 * @param investmentLikelyAmount TODO
	 * @param statusNote             TODO
	 * @return true if able to create FundRaising
	 */
	public boolean createFundRaising(String environment, String mode, String fundraisingName, String fundName,
			String legalName, String closing, String stage, String investmentLikelyAmount, String statusNote) {
		refresh(driver);
		ThreadSleep(5000);
		if (click(driver, getNewButton(environment, mode, 60), "New Button", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(500);
			if (sendKeys(driver, getFundraisingName(environment, mode, 60), fundraisingName, "FundRaising Name",
					action.BOOLEAN)) {
				ThreadSleep(500);
				if (sendKeys(driver, getFundName(environment, mode, 60), fundName, "Fund Name", action.BOOLEAN)) {
					ThreadSleep(500);
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						ThreadSleep(2000);
						if (click(driver,
								FindElement(driver,
										"//*[contains(@class,'slds-listbox__option-text')]/*[@title='" + fundName
												+ "']",
										"Fund Name List", action.THROWEXCEPTION, 30),
								fundName + "   :   Fund Name", action.BOOLEAN)) {
							appLog.info(fundName + "  is present in list.");
						} else {
							appLog.info(fundName + "  is not present in the list.");
						}
					}
					if (sendKeys(driver, getLegalName(environment, mode, 60), legalName, "Legal Name",
							action.BOOLEAN)) {
						ThreadSleep(500);
						if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
							ThreadSleep(1000);
							if (click(driver,
									FindElement(driver,
											"//*[contains(@class,'slds-listbox__option-text')]/*[@title='" + legalName
													+ "']",
											"Legal Name List", action.THROWEXCEPTION, 30),
									legalName + "   :   Legal Name", action.SCROLLANDBOOLEAN)) {
								appLog.info(legalName + "  is present in list.");
							} else {
								appLog.info(legalName + "  is not present in the list.");
							}
						}

						try {
							if (!closing.equals("") || closing != null) {
								if (click(
										driver, FindElement(driver, "//*[text()='Closing']/..//button/..",
												"Clsoing input", action.THROWEXCEPTION, 30),
										"Clsoing input", action.BOOLEAN)) {
									appLog.info("Click on closing input");
									ThreadSleep(2000);
									click(driver, FindElement(driver,
											"//*[text()='Closing']/..//following-sibling::div//span[text()='" + closing
													+ "']/../..",
											"Clsoing list", action.THROWEXCEPTION, 30), "Clsoing list", action.BOOLEAN);
									appLog.info("Click on closing list item:" + closing);

								} else {
									appLog.info("Not able to Click on closing input");
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							if (!stage.equals("") || stage != null) {
								if (click(driver, FindElement(driver, "//*[text()='Stage']/..//button", "Stage input",
										action.THROWEXCEPTION, 30), "Stage input", action.BOOLEAN)) {
									appLog.info("Click on stage input");
									ThreadSleep(2000);
									click(driver, FindElement(driver, "//*[text()='" + stage + "']/..", "stage list",
											action.THROWEXCEPTION, 30), "stage list", action.BOOLEAN);
									appLog.info("Click on stage list item:" + stage);

								} else {
									appLog.info("Not able to Click on stage input");
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							if (!investmentLikelyAmount.equals("") || investmentLikelyAmount != null) {
								WebElement ele = FindElement(driver,
										"//input[@name='navpeII__Investment_Likely_Amount_USD_mn__c']",
										"investment likely amount input box", action.BOOLEAN, 20);
								if (sendKeys(driver, ele, investmentLikelyAmount, "investment likely amount input box",
										action.BOOLEAN)) {
									appLog.info("Enter investment likley amount:" + investmentLikelyAmount);

								} else {
									appLog.info("Not able to Enter investment likley amount:" + investmentLikelyAmount);
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							if (!statusNote.equals("") || statusNote != null) {
								WebElement ele = FindElement(driver, "//*[text()='Status Notes']/..//textarea",
										"status note", action.BOOLEAN, 20);
								if (sendKeys(driver, ele, statusNote, "status note", action.BOOLEAN)) {
									appLog.info("Enter status note:" + statusNote);

								} else {
									appLog.info("not able to Enter status note:" + statusNote);
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (click(driver, getCustomTabSaveBtn("", 60), "Save Button", action.SCROLLANDBOOLEAN)) {
							ThreadSleep(500);

							ThreadSleep(2000);
							String fundraising = null;
							WebElement ele;
							if (Mode.Lightning.toString().equalsIgnoreCase(mode)) {
								String xpath = "//*[contains(text(),'Fundraising')]/..//*[text()='" + fundraisingName
										+ "']";
								ele = FindElement(driver, xpath, "Header : " + fundraisingName, action.BOOLEAN, 30);

							} else {
								ele = getFundraisingNameInViewMode(environment, mode, 60);
							}

							if (ele != null) {
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

	public WebElement getEditBtn(String projectName, String stage, action action, int timeOut) {
		String xpath = "//a[contains(@title,'Edit')][contains(@title,'" + stage + "')]";
		WebElement ele = FindElement(driver, xpath, stage, action, timeOut);
		return ele;
	}
	
	

	// Lightning Mthod....
	/**
	 * @author Sourabh Kumar
	 * @param environment
	 * @param mode
	 * @param fundraisingName
	 * @param fundName
	 * @param legalName
	 * @param closing                TODO
	 * @param stage                  TODO
	 * @param investmentLikelyAmount TODO
	 * @param statusNote             TODO
	 * @param Year                   TODO
	 * @param Month                  TODO
	 * @param Date                   TODO
	 * 
	 * @return true if able to create FundRaising
	 */
	public boolean createFundRaising(String environment, String mode, String fundraisingName, String fundName,
			String legalName, String closing, String stage, String investmentLikelyAmount, String statusNote,
			String targetClosingYear, String targetClosingMonth, String targetClosingDate) {
		refresh(driver);
		ThreadSleep(5000);
		if (click(driver, getNewButton(environment, mode, 60), "New Button", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(500);
			if (sendKeys(driver, getFundraisingName(environment, mode, 60), fundraisingName, "FundRaising Name",
					action.BOOLEAN)) {
				ThreadSleep(500);
				if (sendKeys(driver, getFundName(environment, mode, 60), fundName, "Fund Name", action.BOOLEAN)) {
					ThreadSleep(500);
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						ThreadSleep(2000);
						if (click(driver,
								FindElement(driver,
										"//*[contains(@class,'slds-listbox__option-text')]/*[@title='" + fundName
												+ "']",
										"Fund Name List", action.THROWEXCEPTION, 30),
								fundName + "   :   Fund Name", action.BOOLEAN)) {
							appLog.info(fundName + "  is present in list.");
						} else {
							appLog.info(fundName + "  is not present in the list.");
						}
					}
					if (sendKeys(driver, getLegalName(environment, mode, 60), legalName, "Legal Name",
							action.BOOLEAN)) {
						ThreadSleep(500);
						if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
							ThreadSleep(1000);
							if (click(driver,
									FindElement(driver,
											"//*[contains(@class,'slds-listbox__option-text')]/*[@title='" + legalName
													+ "']",
											"Legal Name List", action.THROWEXCEPTION, 30),
									legalName + "   :   Legal Name", action.SCROLLANDBOOLEAN)) {
								appLog.info(legalName + "  is present in list.");
							} else {
								appLog.info(legalName + "  is not present in the list.");
							}
						}

						try {
							if (!"".equals(closing) && closing != null) {
								if (click(
										driver, FindElement(driver, "//*[text()='Closing']/..//button/..",
												"Clsoing input", action.THROWEXCEPTION, 30),
										"Clsoing input", action.BOOLEAN)) {
									appLog.info("Click on closing input");
									ThreadSleep(2000);
									click(driver, FindElement(driver,
											"//*[text()='Closing']/..//following-sibling::div//span[text()='" + closing
													+ "']/../..",
											"Clsoing list", action.THROWEXCEPTION, 30), "Clsoing list", action.BOOLEAN);
									appLog.info("Click on closing list item:" + closing);

								} else {
									appLog.info("Not able to Click on closing input");
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							if (!"".equals(stage) && stage != null) {
								if (click(driver, FindElement(driver, "//*[text()='Stage']/..//button", "Stage input",
										action.THROWEXCEPTION, 30), "Stage input", action.BOOLEAN)) {
									appLog.info("Click on stage input");
									ThreadSleep(2000);
									click(driver, FindElement(driver,
											"//*[text()='" + stage + "']/../ancestor::lightning-base-combobox-item",
											"stage list", action.THROWEXCEPTION, 30), "stage list", action.BOOLEAN);
									appLog.info("Click on stage list item:" + stage);

								} else {
									appLog.info("Not able to Click on stage input");
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							if (!"".equals(investmentLikelyAmount) && investmentLikelyAmount != null) {
								WebElement ele = FindElement(driver,
										"//input[@name='navpeII__Investment_Likely_Amount_USD_mn__c']",
										"investment likely amount input box", action.BOOLEAN, 20);
								if (sendKeys(driver, ele, investmentLikelyAmount, "investment likely amount input box",
										action.BOOLEAN)) {
									appLog.info("Enter investment likley amount:" + investmentLikelyAmount);

								} else {
									appLog.info("Not able to Enter investment likley amount:" + investmentLikelyAmount);
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							if (!"".equals(statusNote) && statusNote != null) {
								WebElement ele = FindElement(driver, "//*[text()='Status Notes']/..//textarea",
										"status note", action.BOOLEAN, 20);
								if (sendKeys(driver, ele, statusNote, "status note", action.BOOLEAN)) {
									appLog.info("Enter status note:" + statusNote);

								} else {
									appLog.info("not able to Enter status note:" + statusNote);
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							if ((!"".equals(targetClosingYear) && targetClosingYear != null)
									&& (!"".equals(targetClosingMonth) && targetClosingMonth != null
											&& (!"".equals(targetClosingDate) && targetClosingDate != null))) {
								WebElement ele = FindElement(driver, "//label[text()='Target Close Date']/..//input",
										"Closing Date", action.BOOLEAN, 20);
								CommonLib.scrollDownThroughWebelementInCenter(driver, ele, "Calender");
								CommonLib.click(driver, ele, "Calender", action.BOOLEAN);

								if (CommonLib.datePickerHandle(driver, "Calender", targetClosingYear,
										targetClosingMonth, targetClosingDate)) {
									appLog.info("Date has been selected from Calender");
									CommonLib.ThreadSleep(3000);
								} else {
									appLog.info("Date has been selected from Calender");
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						if (click(driver, fundRaisingSaveButton("", 60), "Save Button", action.SCROLLANDBOOLEAN)) {
							ThreadSleep(500);

							ThreadSleep(2000);
							String fundraising = null;
							WebElement ele;
							if (Mode.Lightning.toString().equalsIgnoreCase(mode)) {
								String xpath = "//*[contains(text(),'Fundraising')]/..//*[text()='" + fundraisingName
										+ "']";
								ele = FindElement(driver, xpath, "Header : " + fundraisingName, action.BOOLEAN, 30);

							} else {
								ele = getFundraisingNameInViewMode(environment, mode, 60);
							}

							if (ele != null) {
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

	public boolean CreateNewFundraisingContactFromTab(String projectName, ArrayList<String> labelName,
			ArrayList<String> value, ArrayList<String> inputType) {
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		boolean flag = false;

		CommonLib.refresh(driver);
		CommonLib.ThreadSleep(3000);
		if (CommonLib.clickUsingJavaScript(driver, getfundraisingContactTab(50), "Fundraising tab",
				action.SCROLLANDBOOLEAN)) {
			appLog.info("Clicked on fundraising contact tab");
			if (BP.createNewRecordThroughSDG(projectName, "Fundraising Contacts", "New Fundraising Contact", labelName,
					value, inputType, 50)) {
				log(LogStatus.PASS, "New Fundraising Contact has been created", YesNo.No);
				sa.assertTrue(true, "New Fundraising Contact has been created");
				flag = true;
			} else {
				log(LogStatus.FAIL, "New Fundraising Contact is not created", YesNo.No);
				sa.assertTrue(false, "New Fundraising Contact is not created");
			}

		} else {
			appLog.error("Not able to click on fundraising contact tab");
		}
		return flag;

	}

	/**
	 * @author Sourabh Kumar
	 * @param environment
	 * @param mode
	 * @param fundraisingName
	 * @param fundName
	 * @param legalName
	 * @param closing                TODO
	 * @param stage                  TODO
	 * @param investmentLikelyAmount TODO
	 * @param statusNote             TODO
	 * @param Year                   TODO
	 * @param Month                  TODO
	 * @param Date                   TODO
	 * 
	 * @return true if able to create FundRaising
	 */
	public boolean createFundRaisingContactFromIcon(String environment, String mode, String ContactName, String Role) {
		Boolean flag = false;
		if (clickUsingJavaScript(driver, getFundraisingContactIcon( 60), "Fundraising Contact Icon", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(500);
			if (sendKeys(driver, getPopUpfundraisingContact(60), ContactName, "Contact Name", action.BOOLEAN)) {
				ThreadSleep(500);
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					ThreadSleep(2000);
					//*[contains(@class,'slds')]/*[text()='" + ContactName+ "']
					if (click(driver,
							FindElement(driver,"//div[@title='" + ContactName
											+ "']/ancestor::a",
									"Fund Name List", action.THROWEXCEPTION, 30),
							ContactName + "   :   Fund Name", action.BOOLEAN)) {
						appLog.info(ContactName + "  is present in list.");
					} else {
						appLog.info(ContactName + "  is not present in the list.");
					}
				}
	}
			try {
				if (!"".equals(Role) && Role != null) {
					if (click(driver, FindElement(driver, "//*[text()='Role']/..//button", "Role input",
							action.THROWEXCEPTION, 30), "Role input", action.BOOLEAN)) {
						appLog.info("Click on stage input");
						ThreadSleep(2000);
						click(driver, FindElement(driver,
								"//*[text()='" + Role + "']/../ancestor::lightning-base-combobox-item",
								"Role list", action.THROWEXCEPTION, 30), "Role list", action.BOOLEAN);
						appLog.info("Click on stage list item:" + Role);

					} else {
						appLog.info("Not able to Click on Role input");
					}

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (click(driver, getNewFinancingPopupSaveIcon(60), "Save Button", action.SCROLLANDBOOLEAN)) {
				ThreadSleep(500);
			} else {
				appLog.error("Not able to click on save button");
			}
		} else {
			appLog.error("Not able to Click on fundraising contact icon");
		}
		return flag;
	}		
				
	public boolean createFundRaisingFromIcon(String environment, String mode, String fundraisingName, String fundName,
			String CompanyName, String closing, String stage, String investmentLikelyAmount, String statusNote,
			String targetClosingYear, String targetClosingMonth, String targetClosingDate) {
		Boolean flag = false;
//		refresh(driver);
//		ThreadSleep(5000);
//		if (click(driver, getNewButton(environment, mode, 60), "New Button", action.SCROLLANDBOOLEAN)) {
//			ThreadSleep(500);
//			if (sendKeys(driver, getFundraisingName(environment, mode, 60), fundraisingName, "FundRaising Name",
//					action.BOOLEAN)) {
//				ThreadSleep(500);
				if (sendKeys(driver, getPopUpfundraisingFundName(60), fundName, "Fund Name", action.BOOLEAN)) {
					ThreadSleep(500);
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						ThreadSleep(2000);
						if (click(driver,
								FindElement(driver,
										"//*[contains(@class,'slds-p-around')]/*[text()='" + fundName
												+ "']",
										"Fund Name List", action.THROWEXCEPTION, 30),
								fundName + "   :   Fund Name", action.BOOLEAN)) {
							appLog.info(fundName + "  is present in list.");
						} else {
							appLog.info(fundName + "  is not present in the list.");
						}
					}
					try {
						if (CompanyName != null) {
					if (sendKeys(driver, getfundraisingCompanyName( 60), CompanyName, "Legal Name",
							action.BOOLEAN)) {
						ThreadSleep(500);
						if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
							ThreadSleep(1000);
							if (click(driver,
									FindElement(driver,
											"//*[contains(@class,'slds-p-around')]/*[text()='" + CompanyName
													+ "']",
											"Legal Name List", action.THROWEXCEPTION, 30),
									CompanyName + "   :   Legal Name", action.SCROLLANDBOOLEAN)) {
								appLog.info(CompanyName + "  is present in list.");
							} else {
								appLog.info(CompanyName + "  is not present in the list.");
							}
						}
					}

						}else {
						log(LogStatus.ERROR, "Company Name not Provided", YesNo.No);
						
					}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
						try {
							if (!"".equals(closing) && closing != null) {
								if (click(
										driver, FindElement(driver, "//*[text()='Closing']/..//button/..",
												"Clsoing input", action.THROWEXCEPTION, 30),
										"Clsoing input", action.BOOLEAN)) {
									appLog.info("Click on closing input");
									ThreadSleep(2000);
									click(driver, FindElement(driver,
											"//*[text()='Closing']/..//following-sibling::div//span[text()='" + closing
													+ "']/../..",
											"Clsoing list", action.THROWEXCEPTION, 30), "Clsoing list", action.BOOLEAN);
									appLog.info("Click on closing list item:" + closing);

								} else {
									appLog.info("Not able to Click on closing input");
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							if (!"".equals(stage) && stage != null) {
								if (click(driver, FindElement(driver, "//*[text()='Stage']/..//button", "Stage input",
										action.THROWEXCEPTION, 30), "Stage input", action.BOOLEAN)) {
									appLog.info("Click on stage input");
									ThreadSleep(2000);
									click(driver, FindElement(driver,
											"//*[text()='" + stage + "']/../ancestor::lightning-base-combobox-item",
											"stage list", action.THROWEXCEPTION, 30), "stage list", action.BOOLEAN);
									appLog.info("Click on stage list item:" + stage);

								} else {
									appLog.info("Not able to Click on stage input");
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							if (!"".equals(investmentLikelyAmount) && investmentLikelyAmount != null) {
								WebElement ele = FindElement(driver,
										"//input[@name='navpeII__Investment_Likely_Amount_USD_mn__c']",
										"investment likely amount input box", action.BOOLEAN, 20);
								if (sendKeys(driver, ele, investmentLikelyAmount, "investment likely amount input box",
										action.BOOLEAN)) {
									appLog.info("Enter investment likley amount:" + investmentLikelyAmount);

								} else {
									appLog.info("Not able to Enter investment likley amount:" + investmentLikelyAmount);
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							if (!"".equals(statusNote) && statusNote != null) {
								WebElement ele = FindElement(driver, "//*[text()='Status Notes']/..//textarea",
										"status note", action.BOOLEAN, 20);
								if (sendKeys(driver, ele, statusNote, "status note", action.BOOLEAN)) {
									appLog.info("Enter status note:" + statusNote);

								} else {
									appLog.info("not able to Enter status note:" + statusNote);
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							if ((!"".equals(targetClosingYear) && targetClosingYear != null)
									&& (!"".equals(targetClosingMonth) && targetClosingMonth != null
											&& (!"".equals(targetClosingDate) && targetClosingDate != null))) {
								WebElement ele = FindElement(driver, "//label[text()='Target Close Date']/..//input",
										"Closing Date", action.BOOLEAN, 20);
								CommonLib.scrollDownThroughWebelementInCenter(driver, ele, "Calender");
								CommonLib.click(driver, ele, "Calender", action.BOOLEAN);

								if (CommonLib.datePickerHandle(driver, "Calender", targetClosingYear,
										targetClosingMonth, targetClosingDate)) {
									appLog.info("Date has been selected from Calender");
									CommonLib.ThreadSleep(3000);
								} else {
									appLog.info("Date has been selected from Calender");
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						if (click(driver, fundRaisingpupupSaveButton("", 60), "Save Button", action.SCROLLANDBOOLEAN)) {
							ThreadSleep(500);

							ThreadSleep(2000);
//							String fundraising = null;
//							WebElement ele;
//							if (Mode.Lightning.toString().equalsIgnoreCase(mode)) {
//								String xpath = "//*[contains(text(),'Fundraising')]/..//*[text()='" + fundraisingName
//										+ "']";
//								ele = FindElement(driver, xpath, "Header : " + fundraisingName, action.BOOLEAN, 30);
//
//							} else {
//								ele = getFundraisingNameInViewMode(environment, mode, 60);
//							}
//
//							if (ele != null) {
//								appLog.info("Fundraising is created successfully.:" + fundraisingName);
//								return true;
//							} else {
//								appLog.info("FundRaising is not created successfully.:" + fundraisingName);
//							}
							
						} else {
							appLog.error("Not able to click on save button");
						}
					} else {
						appLog.error("Not able to enter legal Name");
					}
//				} else {
//					appLog.error("Not able to enter fund name");
//				}
//			} else {
//				appLog.error("Not able to enter value in fundraiisng text box");
//			}
//		} else {
//			appLog.error("Not able to click on new button so we cannot create fundraising");
//		}
		return flag;
	}

	public boolean createFundraisingContact(String projectName,String fundraisingName,String[][] requestInfo,action action,int timeOut) {
		boolean flag=true;
		String label;
		String value;
		String xpath="";
		WebElement ele;
		
		if(clickUsingJavaScript(driver, getNewButton(projectName, timeOut), "Fundraising Contacts")) {
			log(LogStatus.INFO,"click on New deal team Button",YesNo.Yes);

			for (String[] reuestData : requestInfo) {
				label=reuestData[0].replace("_", " ");
				value=reuestData[1];

				if(PageLabel.Fundraising.toString().equals(reuestData[0]) || PageLabel.Contact.toString().equals(reuestData[0]) ||PageLabel.Firm.toString().equals(reuestData[0])){
					if (sendKeys(driver, getListTextbox(projectName,label, timeOut), value, label+" : "+value,action)) {
						ThreadSleep(3000);
						log(LogStatus.INFO,"Able to send "+value+" to label : "+label,YesNo.Yes);
						if (click(driver,FindElement(driver,"//span[contains(@class,'listbox')]//*[@title='"+value+"']","ATTENDEE Name List", action, 10),
								value + "   :   Company Name", action)) {
							log(LogStatus.INFO,"Able to select "+value+" to label : "+label,YesNo.No);
						} else {
							sa.assertTrue(false,"Not Able to select "+value+" to label : "+label);
							log(LogStatus.SKIP,"Not Able to select "+value+" to label : "+label,YesNo.Yes);
							flag=false;
						}

					} else {
						sa.assertTrue(false,"Not Able to send "+value+" to label : "+label);
						log(LogStatus.SKIP,"Not Able to send "+value+" to label : "+label,YesNo.Yes);
						return false;
					}
				} else if(PageLabel.Role.toString().equals(reuestData[0])) {if (click(driver, getRoleDropDownList(projectName, 10), label, action)) {
					ThreadSleep(2000);
					log(LogStatus.INFO,"Able to Click on "+label,YesNo.No);

					xpath="//span[@title='"+value+"']";
					ele = FindElement(driver,xpath, value,action, timeOut);
					ThreadSleep(4000);
					if (click(driver, ele, value, action)) {
						log(LogStatus.INFO,"Able to select "+value+" to label : "+label,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to select "+value+" to label : "+label);
						log(LogStatus.SKIP,"Not Able to select "+value+" to label : "+label,YesNo.Yes);
						flag=false;
					}

				} else {
					sa.assertTrue(false,"Not Able to Click on "+label);
					log(LogStatus.SKIP,"Not Able to Click on "+label,YesNo.Yes);
					flag=false;
				}
				}
			}

			if (click(driver, getRecordPageSettingSave(timeOut), "save button", action)) {
				appLog.info("clicked on save button");
				
				ThreadSleep(3000);
				refresh(driver);
				ThreadSleep(3000);
				xpath="//*[text()='Fundraising Contact']/parent::h1//slot/lightning-formatted-text";
				ele = FindElement(driver, xpath, "dt id", action, timeOut);
				if (ele!=null) {
					String id=getText(driver, ele, "deal team id",action.SCROLLANDBOOLEAN);
					log(LogStatus.INFO,"successfully created and noted id of DT"+id+" and deal name "+fundraisingName,YesNo.No);	
				} else {
					sa.assertTrue(false,"could not create DT"+fundraisingName);
					log(LogStatus.SKIP,"could not create DT"+fundraisingName,YesNo.Yes);
					flag=false;
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on save button so cannot create deal team");
				log(LogStatus.SKIP,"Not Able to Click on save button so cannot create deal team",YesNo.Yes);
				flag=false;
			}


		}else {
			sa.assertTrue(false,"Not able to click on deal team button");
			log(LogStatus.SKIP,"Not able to click on deal team button",YesNo.Yes);
			flag=false;

		}
		return flag;
	}
	
	/**
	 * @author Ankur Alam
	 * @param environment
	 * @param mode
	 * @param fundraisingName
	 * @param fundName
	 * @param legalName
	 * @param closing                TODO
	 * @param stage                  TODO
	 * @param investmentLikelyAmount TODO
	 * @param statusNote             TODO
	 * @return true if able to create FundRaising
	 */
	public boolean createFundRaising(String environment, String mode, String fundraisingName, String fundName,
			String legalName, String closing, String stage, String investmentLikelyAmount, String statusNote,
			String CompanyName) {
		refresh(driver);
		ThreadSleep(5000);
		if (click(driver, getNewButton(environment, mode, 60), "New Button", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(500);
			if (sendKeys(driver, getFundraisingName(environment, mode, 60), fundraisingName, "FundRaising Name",
					action.BOOLEAN)) {
				ThreadSleep(500);
				if (sendKeys(driver, getFundName(environment, mode, 60), fundName, "Fund Name", action.BOOLEAN)) {
					ThreadSleep(500);
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						ThreadSleep(5000);
						if (click(driver,
								FindElement(driver,
										"//*[contains(@class,'slds-listbox__option-text')]/*[@title='" + fundName
												+ "']/ancestor::lightning-base-combobox-item",
										"Fund Name List", action.SCROLLANDBOOLEAN, 30),
								fundName + "   :   Fund Name", action.SCROLLANDBOOLEAN)) {
							appLog.info(fundName + "  is present in list.");
						} else {
							appLog.info(fundName + "  is not present in the list.");
						}
					}
					if (sendKeys(driver, getLegalName(environment, mode, 60), legalName, "Legal Name",
							action.BOOLEAN)) {
						ThreadSleep(500);
						if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
							ThreadSleep(5000);
							if (clickUsingJavaScript(driver,
									FindElement(driver,
											"//*[contains(@class,'slds-listbox__option-text')]/*[@title='" + legalName
													+ "']/ancestor::lightning-base-combobox-item",
											"Legal Name List", action.THROWEXCEPTION, 30),
									legalName + "   :   Legal Name", action.SCROLLANDBOOLEAN)) {
								appLog.info(legalName + "  is present in list.");
							} else {
								appLog.info(legalName + "  is not present in the list.");
							}
						}

						try {
							if (!"".equals(closing) && closing != null) {
								if (click(
										driver, FindElement(driver, "//*[text()='Closing']/..//button/..",
												"Clsoing input", action.THROWEXCEPTION, 30),
										"Clsoing input", action.BOOLEAN)) {
									appLog.info("Click on closing input");
									ThreadSleep(2000);
									click(driver, FindElement(driver,
											"//*[text()='Closing']/..//following-sibling::div//span[text()='" + closing
													+ "']/../..",
											"Clsoing list", action.THROWEXCEPTION, 30), "Clsoing list", action.BOOLEAN);
									appLog.info("Click on closing list item:" + closing);

								} else {
									appLog.info("Not able to Click on closing input");
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							if (!"".equals(stage) && stage != null) {
								if (click(driver, FindElement(driver, "//*[text()='Stage']/..//button", "Stage input",
										action.THROWEXCEPTION, 30), "Stage input", action.BOOLEAN)) {
									appLog.info("Click on stage input");
									ThreadSleep(2000);
									click(driver, FindElement(driver, "//*[text()='" + stage + "']/..", "stage list",
											action.THROWEXCEPTION, 30), "stage list", action.BOOLEAN);
									appLog.info("Click on stage list item:" + stage);

								} else {
									appLog.info("Not able to Click on stage input");
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							if (!"".equals(investmentLikelyAmount) && investmentLikelyAmount != null) {
								WebElement ele = FindElement(driver,
										"//input[@name='navpeII__Investment_Likely_Amount_USD_mn__c']",
										"investment likely amount input box", action.BOOLEAN, 20);
								if (sendKeys(driver, ele, investmentLikelyAmount, "investment likely amount input box",
										action.BOOLEAN)) {
									appLog.info("Enter investment likley amount:" + investmentLikelyAmount);

								} else {
									appLog.info("Not able to Enter investment likley amount:" + investmentLikelyAmount);
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							if (!"".equals(statusNote) && statusNote != null) {
								WebElement ele = FindElement(driver, "//*[text()='Status Notes']/..//textarea",
										"status note", action.BOOLEAN, 20);
								if (sendKeys(driver, ele, statusNote, "status note", action.BOOLEAN)) {
									appLog.info("Enter status note:" + statusNote);

								} else {
									appLog.info("not able to Enter status note:" + statusNote);
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							if (!"".equals(CompanyName) && CompanyName != null) {
								if (sendKeys(driver, getCompanyName(environment, mode, 60), CompanyName, "Company Name",
										action.BOOLEAN)) {
									ThreadSleep(500);
									if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
										ThreadSleep(5000);
										if (clickUsingJavaScript(driver, FindElement(driver,
												"//*[contains(@class,'slds-listbox__option-text')]/*[@title='"
														+ CompanyName + "']/ancestor::lightning-base-combobox-item",
												"Company Name List", action.THROWEXCEPTION, 30),
												CompanyName + "   :   Company Name", action.SCROLLANDBOOLEAN)) {
											appLog.info(CompanyName + "  is present in list.");
										} else {
											appLog.info(CompanyName + "  is not present in the list.");
										}
									}
								} else {
									appLog.error("Not able to Enter Company Name:" + CompanyName);
									sa.assertTrue(false, "Not able to Enter Company Name:" + CompanyName);
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						if (click(driver, getCustomTabSaveBtn("", 60), "Save Button", action.SCROLLANDBOOLEAN)) {
							ThreadSleep(500);

							ThreadSleep(2000);
							String fundraising = null;
							WebElement ele;
							if (Mode.Lightning.toString().equalsIgnoreCase(mode)) {
								String xpath = "//*[contains(text(),'Fundraising')]/..//*[text()='" + fundraisingName
										+ "']";
								ele = FindElement(driver, xpath, "Header : " + fundraisingName, action.BOOLEAN, 30);

							} else {
								ele = getFundraisingNameInViewMode(environment, mode, 60);
							}

							if (ele != null) {
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

	/**
	 * @author Ankur Huria
	 * @param environment
	 * @param mode
	 * @param fundraisingRecordType
	 * @param fundraisingName
	 * @param fundName
	 * @param legalName
	 * @param closing                TODO
	 * @param stage                  TODO
	 * @param investmentLikelyAmount TODO
	 * @param statusNote             TODO
	 * @param Year                   TODO
	 * @param Month                  TODO
	 * @param Date                   TODO
	 * 
	 * @return true if able to create FundRaising
	 */
	public boolean createFundRaising(String environment, String mode, String fundraisingRecordType,
			String fundraisingName, String fundName, String legalName, String closing, String stage,
			String investmentLikelyAmount, String statusNote, String targetClosingYear, String targetClosingMonth,
			String targetClosingDate) {
		refresh(driver);
		ThreadSleep(5000);
		if (click(driver, getNewButton(environment, mode, 60), "New Button", action.SCROLLANDBOOLEAN)) {
			ThreadSleep(500);

			if (fundraisingRecordType != null && !fundraisingRecordType.isEmpty()) {
				if (CommonLib.click(driver, recordTypeRadioButton(fundraisingRecordType, 20),
						"Radio button: " + fundraisingRecordType, action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Clicked on the radio button: " + fundraisingRecordType, YesNo.No);

					if (CommonLib.click(driver, nextButtonOnForm(20), "Next button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on the Next button", YesNo.No);

					} else {
						log(LogStatus.ERROR, "Not able to click on the Next button", YesNo.No);
					}

				} else {
					log(LogStatus.ERROR, "Not able to click on the Radio button: " + fundraisingRecordType, YesNo.No);
				}
			}

			if (sendKeys(driver, getFundraisingName(environment, mode, 60), fundraisingName, "FundRaising Name",
					action.BOOLEAN)) {
				ThreadSleep(500);
				if (sendKeys(driver, getFundName(environment, mode, 60), fundName, "Fund Name", action.BOOLEAN)) {
					ThreadSleep(500);
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						ThreadSleep(2000);
						if (click(driver,
								FindElement(driver,
										"//*[contains(@class,'slds-listbox__option-text')]/*[@title='" + fundName
												+ "']",
										"Fund Name List", action.THROWEXCEPTION, 30),
								fundName + "   :   Fund Name", action.BOOLEAN)) {
							appLog.info(fundName + "  is present in list.");
						} else {
							appLog.info(fundName + "  is not present in the list.");
						}
					}
					if (sendKeys(driver, getLegalName(environment, mode, 60), legalName, "Legal Name",
							action.BOOLEAN)) {
						ThreadSleep(500);
						if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
							ThreadSleep(1000);
							if (click(driver,
									FindElement(driver,
											"//*[contains(@class,'slds-listbox__option-text')]/*[@title='" + legalName
													+ "']",
											"Legal Name List", action.THROWEXCEPTION, 30),
									legalName + "   :   Legal Name", action.SCROLLANDBOOLEAN)) {
								appLog.info(legalName + "  is present in list.");
							} else {
								appLog.info(legalName + "  is not present in the list.");
							}
						}

						try {
							if (!"".equals(closing) && closing != null) {
								if (click(
										driver, FindElement(driver, "//*[text()='Closing']/..//button/..",
												"Clsoing input", action.THROWEXCEPTION, 30),
										"Clsoing input", action.BOOLEAN)) {
									appLog.info("Click on closing input");
									ThreadSleep(2000);
									click(driver, FindElement(driver,
											"//*[text()='Closing']/..//following-sibling::div//span[text()='" + closing
													+ "']/../..",
											"Clsoing list", action.THROWEXCEPTION, 30), "Clsoing list", action.BOOLEAN);
									appLog.info("Click on closing list item:" + closing);

								} else {
									appLog.info("Not able to Click on closing input");
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							if (!"".equals(stage) && stage != null) {
								if (click(driver, FindElement(driver, "//*[text()='Stage']/..//button", "Stage input",
										action.THROWEXCEPTION, 30), "Stage input", action.BOOLEAN)) {
									appLog.info("Click on stage input");
									ThreadSleep(2000);
									click(driver, FindElement(driver,
											"//*[text()='" + stage + "']/../ancestor::lightning-base-combobox-item",
											"stage list", action.THROWEXCEPTION, 30), "stage list", action.BOOLEAN);
									appLog.info("Click on stage list item:" + stage);

								} else {
									appLog.info("Not able to Click on stage input");
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							if (!"".equals(investmentLikelyAmount) && investmentLikelyAmount != null) {
								WebElement ele = FindElement(driver,
										"//input[@name='navpeII__Investment_Likely_Amount_USD_mn__c']",
										"investment likely amount input box", action.BOOLEAN, 20);
								if (sendKeys(driver, ele, investmentLikelyAmount, "investment likely amount input box",
										action.BOOLEAN)) {
									appLog.info("Enter investment likley amount:" + investmentLikelyAmount);

								} else {
									appLog.info("Not able to Enter investment likley amount:" + investmentLikelyAmount);
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							if (!"".equals(statusNote) && statusNote != null) {
								WebElement ele = FindElement(driver, "//*[text()='Status Notes']/..//textarea",
										"status note", action.BOOLEAN, 20);
								if (sendKeys(driver, ele, statusNote, "status note", action.BOOLEAN)) {
									appLog.info("Enter status note:" + statusNote);

								} else {
									appLog.info("not able to Enter status note:" + statusNote);
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							if ((!"".equals(targetClosingYear) && targetClosingYear != null)
									&& (!"".equals(targetClosingMonth) && targetClosingMonth != null
											&& (!"".equals(targetClosingDate) && targetClosingDate != null))) {
								WebElement ele = FindElement(driver, "//label[text()='Target Close Date']/..//input",
										"Closing Date", action.BOOLEAN, 20);
								CommonLib.scrollDownThroughWebelementInCenter(driver, ele, "Calender");
								CommonLib.click(driver, ele, "Calender", action.BOOLEAN);

								if (CommonLib.datePickerHandle(driver, "Calender", targetClosingYear,
										targetClosingMonth, targetClosingDate)) {
									appLog.info("Date has been selected from Calender");
									CommonLib.ThreadSleep(3000);
								} else {
									appLog.info("Date has been selected from Calender");
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						if (click(driver, getCustomTabSaveBtn("", 60), "Save Button", action.SCROLLANDBOOLEAN)) {
							ThreadSleep(500);

							ThreadSleep(2000);
							String fundraising = null;
							WebElement ele;
							if (Mode.Lightning.toString().equalsIgnoreCase(mode)) {
								String xpath = "//*[contains(text(),'Fundraising')]/..//*[text()='" + fundraisingName
										+ "']";
								ele = FindElement(driver, xpath, "Header : " + fundraisingName, action.BOOLEAN, 30);

							} else {
								ele = getFundraisingNameInViewMode(environment, mode, 60);
							}

							if (ele != null) {
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

	
	/**
	 * @author sahil bansal
	 * @param projectName
	 * @param companyname
	 * @param timeOut
	 * @return true if successfully change stage
	 */
	public boolean UpdateLegalName(String projectName,String mode, String legalName, int timeOut) {
		boolean flag = true;
		
		ThreadSleep(2000);
		
			if (click(driver, getLegalNameCrossIcon(projectName, 60), "Company Cross Icon", action.SCROLLANDBOOLEAN)) {
				appLog.info("Clicked on Legal Cross icon");
				ThreadSleep(3000);
			} else {
				appLog.info("Not able to click on Cross Icon button");
				log(LogStatus.INFO, "Not able to clicked on edit button so cannot Account Name ", YesNo.Yes);
				BaseLib.sa.assertTrue(false, "Not able to clicked on edit button so cannot Account Name ");
			}
			if (sendKeys(driver, getLegalName(projectName, mode, 60), legalName, "Legal Name",
					action.BOOLEAN)) {
				ThreadSleep(500);
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					ThreadSleep(1000);
					if (click(driver,
							FindElement(driver,
									"//*[contains(@class,'slds-listbox__option-text')]/*[@title='" + legalName
											+ "']",
									"Legal Name List", action.THROWEXCEPTION, 30),
							legalName + "   :   Legal Name", action.SCROLLANDBOOLEAN)) {
						appLog.info(legalName + "  is present in list.");
					} else {
						appLog.info(legalName + "  is not present in the list.");
					}
				}
				if (click(driver, getCustomTabSaveBtn("", 60), "Save Button", action.SCROLLANDBOOLEAN)) {
					ThreadSleep(500);
				} else {
					appLog.error("Not able to click on save button");
				}
			} else {
				appLog.error("Not able to enter legal Name");
			}
			return flag;
}

	/**
	 * @author sahil bansal
	 * @param projectName
	 * @param companyname
	 * @param timeOut
	 * @return true if successfully change stage
	 */
	public boolean UpdateFundraisingDetail(String environment, String mode, String fundName,
			String CompanyName, int timeOut) {
		boolean flag = true;
		
		ThreadSleep(2000);
		
		if (sendKeys(driver, getFundName(environment, mode, 60), fundName, "Fund Name", action.BOOLEAN)) {
			ThreadSleep(500);
			if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
				ThreadSleep(2000);
				if (click(driver,
						FindElement(driver,
								"//*[contains(@class,'slds-listbox__option-text')]/*[@title='" + fundName
										+ "']",
								"Fund Name List", action.THROWEXCEPTION, 30),
						fundName + "   :   Fund Name", action.BOOLEAN)) {
					appLog.info(fundName + "  is present in list.");
				} else {
					appLog.info(fundName + "  is not present in the list.");
				}
			}
		
				try {
					if (CompanyName != null) {
				if (sendKeys(driver, getfundraisingCompanyName( 60), CompanyName, "Legal Name",
						action.BOOLEAN)) {
					ThreadSleep(500);
					if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
						ThreadSleep(1000);
						if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
							ThreadSleep(1000);
							if (click(driver,
									FindElement(driver,
											"//*[contains(@class,'slds-listbox__option-text')]/*[@title='" + CompanyName
													+ "']",
											"Legal Name List", action.THROWEXCEPTION, 30),
									CompanyName + "   :   Legal Name", action.SCROLLANDBOOLEAN)) {
								appLog.info(CompanyName + "  is present in list.");
							} else {
								appLog.info(CompanyName + "  is not present in the list.");
							}
						}
					
				
					} 
				}
					}
				}catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if (click(driver, getCustomTabSaveBtn("", 60), "Save Button", action.SCROLLANDBOOLEAN)) {
					ThreadSleep(500);
				} else {
					appLog.error("Not able to click on save button");
				}
			} else {
				appLog.error("Not able to enter legal Name");
			}
				
		
			return flag;
				
		}
	
		
	public boolean UpdateFundRaisingName(String projectName, String fundraisingName, int timeOut) {
		boolean flag = true;
		WebElement ele;
		ThreadSleep(2000);
		if (bp.clickOnShowMoreActionDownArrow(projectName, PageName.FundraisingPage, ShowMoreActionDropDownList.Edit, 10)) {
			ThreadSleep(2000);
			ele = bp.getLabelTextBox(projectName, PageName.FundraisingPage.toString(), PageLabel.Fundraising_Name.toString(), timeOut);
			if (sendKeys(driver, ele, fundraisingName, "Fundraising Name", action.BOOLEAN)) {
				appLog.info("Successfully Entered value on Fundraising Name TextBox : " + fundraisingName);
			} else {
				appLog.error("Not Able to Entered value on Fundraising Name TextBox : " + fundraisingName);
			}
				ThreadSleep(1000);
			ThreadSleep(2000);
			if (click(driver, getCustomTabSaveBtn(projectName, 30), "Save Button", action.SCROLLANDBOOLEAN)) {
				appLog.error("Click on save Button");
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
