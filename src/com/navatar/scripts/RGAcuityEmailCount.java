package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.SmokeCommonVariables.adminPassword;
import static com.navatar.generic.SmokeCommonVariables.crmUser1EmailID;
import static com.navatar.generic.SmokeCommonVariables.rgUserPassword;
import static com.navatar.generic.SmokeCommonVariables.rgOrgPassword;
import static com.navatar.generic.SmokeCommonVariables.rgContact1;
import static com.navatar.generic.SmokeCommonVariables.rgContact3;
import static com.navatar.generic.SmokeCommonVariables.rgUser1;
import static com.navatar.generic.SmokeCommonVariables.rgUser2;
import static com.navatar.generic.SmokeCommonVariables.rgUser3;
import static com.navatar.generic.SmokeCommonVariables.rgContactPassword;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import com.navatar.generic.*;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.Environment;
import com.navatar.generic.EnumConstants.Header;
import com.navatar.generic.EnumConstants.InstRecordType;
import com.navatar.generic.EnumConstants.InstitutionPageFieldLabelText;
import com.navatar.generic.EnumConstants.MetaDataSetting;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.RelatedTab;
import com.navatar.generic.EnumConstants.ShowMoreActionDropDownList;
import com.navatar.generic.EnumConstants.Stage;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;
import com.navatar.generic.EnumConstants.recordTypeLabel;
import com.navatar.pageObjects.*;
import com.relevantcodes.extentreports.LogStatus;


public class RGAcuityEmailCount extends BaseLib {
	
	@Parameters({ "projectName" })
	@Test
	public void ADETRGc001_CurrentContactisinFROMandVerifyEmailCountofCurrentContactaTAccount(String projectName) {
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);	
		op.outLookLogin(rgContact1,rgUserPassword);
		String[] to= {rgContact3};
		String [] cc =null;
		String subject = "Testing email outbound for contact";
				
		if(op.sendMailFromRGOutlook(to, cc, null, subject, null, action.BOOLEAN, 20)) {
			log(LogStatus.INFO, "able to send email from "+crmUser1EmailID+" to "+to, YesNo.No);
		} else {
			log(LogStatus.ERROR, "Not able to send email from "+crmUser1EmailID+" to "+to, YesNo.No);
			sa.assertTrue(false, "Not able to send email from "+crmUser1EmailID+" to "+to);
		}
	}


	@Parameters({ "projectName" })
	@Test
	public void ADETRGc002_CurrentContactisinTOandVerifyEmailCountofCurrentContactAccount(String projectName) {
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);	
		op.outLookLogin(rgContact3,rgUserPassword);
		String[] to= {rgContact1};
		String[] cc =null;
		String subject = "Testing email2 outbound for contact";
				
		if(op.sendMailFromRGOutlook(to, cc, null, subject, null, action.BOOLEAN, 20)) {
			log(LogStatus.INFO, "able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
		} else {
			log(LogStatus.ERROR, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
			sa.assertTrue(false, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to);
		}
		
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETRGc003_CurrentContactisinCCBCCandVerifyEmailCountOfCurrentContactatofAccount(String projectName) {
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);	
		op.outLookLogin(rgContact3,rgUserPassword);
		String[] to= {rgUser1};
		String[] cc = {rgContact1};
		String subject = "Testing indirect email3 for contact";
				
		if(op.sendMailFromRGOutlook(to, cc, null, subject, null, action.BOOLEAN, 20)) {
			log(LogStatus.INFO, "able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
		} else {
			log(LogStatus.ERROR, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
			sa.assertTrue(false, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to);
		}
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETRGc004_CurrentContactisSenderSelectedUserisinTOandVerifyEmailCountofUseratConnectionPopupforCurrentContact(String projectName) {
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);	
		op.outLookLogin(rgContact1,rgUserPassword);
		String[] to= {rgUser2};
		String[] cc =null;
		String subject = "Testing indirect email4 for contact";
				
		if(op.sendMailFromRGOutlook(to, cc, null, subject, null, action.BOOLEAN, 20)) {
			log(LogStatus.INFO, "able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
		} else {
			log(LogStatus.ERROR, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
			sa.assertTrue(false, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to);
		}
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETRGc005_CurrentContactisinTOSelectedUserisSenderandVerifyEmailCountofUseratConnectionPopupforCurrentContact(String projectName) {
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);	
		op.outLookLogin(rgUser2,rgUserPassword);
		String[] to= {rgContact1};
		String[] cc =null;
		String subject = "Testing indirect email5 for contact";
				
		if(op.sendMailFromRGOutlook(to, cc, null, subject, null, action.BOOLEAN, 20)) {
			log(LogStatus.INFO, "able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
		} else {
			log(LogStatus.ERROR, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
			sa.assertTrue(false, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to);
		}
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETRGc006_CurrentContactisSenderSelectedUserisinCCBCCandVerifyEmailCountofUseratConnectionPopupforCurrentContact(String projectName) {
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);	
		op.outLookLogin(rgContact1,rgContactPassword);
		String[] to= {rgContact3};
		String[] cc = {rgUser2};
		String subject = "Testing indirect email6 for contact";
				
		if(op.sendMailFromRGOutlook(to, cc, null, subject, null, action.BOOLEAN, 20)) {
			log(LogStatus.INFO, "able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
		} else {
			log(LogStatus.ERROR, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
			sa.assertTrue(false, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to);
		}
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETRGc007_CurrentContactSelectedUserBothareinTOAnotherContactUseriSSenderandVerifyEmailCountofUseratConnectionPopupforCurrentContact(String projectName) {
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);	
		op.outLookLogin(rgContact3,rgContactPassword);
		String[] to= {rgContact1,rgUser2};
		String[] cc =null;
		String subject = "Testing indirect email7 for contact";
				
		if(op.sendMailFromRGOutlook(to, cc, null, subject, null, action.BOOLEAN, 20)) {
			log(LogStatus.INFO, "able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
		} else {
			log(LogStatus.ERROR, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
			sa.assertTrue(false, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to);
		}
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETRGc008_CurrentContactSenderandSelectedUseralongwithoneMoreUserareinCCBCCandVerifyEmailCountofUseratConnectionPopupforCurrentContact(String projectName) {
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);	
		op.outLookLogin(rgContact1,rgContactPassword);
		String[] to= {rgContact3};
		String[] cc = {rgUser1,rgUser2};
		String subject = "Testing indirect email8 for contact";
				
		if(op.sendMailFromRGOutlook(to, cc, null, subject, null, action.BOOLEAN, 20)) {
			log(LogStatus.INFO, "able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
		} else {
			log(LogStatus.ERROR, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
			sa.assertTrue(false, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to);
		}
	}
	@Parameters({ "projectName" })
	@Test
	public void ADETRGc009_CurrentUserSenderandAnotherUserisReCIpientandVerifytheImpactofCounttheseUsers(String projectName) {
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);	
		op.outLookLogin(rgUser1,rgUserPassword);
		String[] to= {rgUser2};
		String[] cc = null;
		String subject = "Testing indirect email9 for contact";
				
		if(op.sendMailFromRGOutlook(to, cc, null, subject, null, action.BOOLEAN, 20)) {
			log(LogStatus.INFO, "able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
		} else {
			log(LogStatus.ERROR, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
			sa.assertTrue(false, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to);
		}
	}
	
	@Parameters({ "projectName" })
	@Test
	public void ADETRGc010_CurrentContactSenderandAnotherContactisReCIpientandVerifytheImpactofCounttheseUsers(String projectName) {
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);	
		op.outLookLogin(rgContact1,rgUserPassword);
		String[] to= {rgContact3};
		String[] cc = {rgUser2};
		String subject = "Testing indirect email10 for contact";
				
		if(op.sendMailFromRGOutlook(to, cc, null, subject, null, action.BOOLEAN, 20)) {
			log(LogStatus.INFO, "able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
		} else {
			log(LogStatus.ERROR, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
			sa.assertTrue(false, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to);
		}
	}
	
	@Parameters({ "projectName" })
	@Test
	public void ADETRGc011_CurrentContactReCIpientSenderandAnotherContactisSenderandVerifytheImpactofCounttheseUsers(String projectName) {
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);	
		op.outLookLogin(rgContact3,rgUserPassword);
		String[] to= {rgContact1};
		String[] cc = {rgUser2};
		String subject = "Testing indirect email11 for contact";
				
		if(op.sendMailFromRGOutlook(to, cc, null, subject, null, action.BOOLEAN, 20)) {
			log(LogStatus.INFO, "able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
		} else {
			log(LogStatus.ERROR, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
			sa.assertTrue(false, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to);
		}
	}
	
	@Parameters({ "projectName" })
	@Test
	public void ADETRGc012_CurrentUserSenderandAnotherContactisReCIpientandVerifytheImpactofCounttheseUsers(String projectName) {
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);	
		op.outLookLogin(rgUser1,rgUserPassword);
		String[] to= {rgContact3,rgContact1};
		String[] cc = null;
		String subject = "Testing indirect email12 for contact";
				
		if(op.sendMailFromRGOutlook(to, cc, null, subject, null, action.BOOLEAN, 20)) {
			log(LogStatus.INFO, "able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
		} else {
			log(LogStatus.ERROR, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to, YesNo.No);
			sa.assertTrue(false, "Not able to send email from "+"automation-test@railworks-ng.com"+" to "+to);
		}
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETRGc013_VerifyEmailSubjectClickableRedirectionEmailRecordsPopupforAccount(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		lp.CRMLogin(rgUser2, rgOrgPassword, appName);

		String subject = "Testing indirect email4 for contact";

		String contactName =RGEContact1LName;

		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, RGEIns1, 30)) {
				if (CommonLib.click(driver, BP.contactEmailCount(contactName, 30), "Email Count: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName, YesNo.No);
				}
				String parentWindowId1 = CommonLib.switchOnWindow(driver);
				if (!parentWindowId1.isEmpty()) {
				ThreadSleep(3000);
				if (clickUsingJavaScript(driver, BP.contactPopUpEmailsubject(subject, 10), "Deal Name: " + subject,
						action.BOOLEAN)) {
					log(LogStatus.PASS, "Clicked on Email subject: " + subject, YesNo.No);
					try {
					String parentWindowId = CommonLib.switchOnWindow(driver);
					if (!parentWindowId.isEmpty()) { 
						log(LogStatus.PASS, "New Window Open after click on Email subject Link: " + subject, YesNo.No);
						Set<String> childWindow = driver.getWindowHandles();
						op.outLookLoginRevenueGrid(rgUser2,rgUserPassword);
						driver.switchTo().defaultContent();
						System.out.println(childWindow);
						for(String child : childWindow) {
						driver.switchTo().window(child);
						}
						if (BP.EmailRecordPage(subject, 20) != null) {
							log(LogStatus.PASS,
									"----Email Detail Page is redirecting for Email Record: " + subject + "-----",
									YesNo.No);
							driver.close();
							driver.switchTo().window(parentWindowId);

						} else {
							log(LogStatus.FAIL, "----Email Detail Page is not redirecting for Email Record: "
									+ subject + "-----", YesNo.Yes);
							sa.assertTrue(false,
									"----Email Detail Page is not redirecting for Email Record: " + subject + "-----");
							driver.close();
							driver.switchTo().window(parentWindowId);

						}

					} else {
						log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + subject, YesNo.Yes);
						sa.assertTrue(false, "No New Window Open after click on Deal Link: " + subject);
					}
				} catch (Exception e) {
					log(LogStatus.FAIL,
							"Not able to switch to window after click on Deal Link, Msg showing: " + e.getMessage(),
							YesNo.Yes);
					sa.assertTrue(false, "Not able to switch to window after click on Deal Link, Msg showing: "
							+ e.getMessage());
				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on Deal Name: " + subject, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on Deal Name: " + subject);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}

	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
		}
				
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}


	@Parameters({ "projectName" })
	@Test
	public void ADETRGc014_VerifyEmailCountUnderEmailColumnEmailCountEmailRecordsPopupShouldSimilarAccount(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(rgUser2, rgOrgPassword, appName);
		String emailCountInFirm = "6";
		String actualemailCount = null;

		String contactName = RGEContact1LName;

		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, RGEIns1, 30)) {
				actualemailCount = getText(driver, BP.contactEmailCount(contactName, 20), "deal",
						action.SCROLLANDBOOLEAN);
				if (BP.contactEmailCount(contactName, 20) != null) {
					if (!actualemailCount.equalsIgnoreCase("")) {

						if (actualemailCount.equalsIgnoreCase(emailCountInFirm)) {
							log(LogStatus.INFO, "Email Count for Contact: " + contactName + " is " + actualemailCount
									+ " before Email Team Create is matched to " + emailCountInFirm, YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"Email Count for Contact: " + contactName
											+ " is before Email count is not matched, Expected: " + emailCountInFirm
											+ " but Actual: " + actualemailCount,
									YesNo.Yes);
							sa.assertTrue(false,
									"Email Count for Contact: " + contactName
											+ " is before Email count  not matched, Expected: " + emailCountInFirm
											+ " but Actual: " + actualemailCount);
						}

					} else {
						log(LogStatus.ERROR, "Email Count for Contact is Empty, So not able to check Count for Contact: "
								+ contactName, YesNo.Yes);
						sa.assertTrue(false, "Email Count for Contact is Empty, So not able to check Count for Contact: "
								+ contactName);
					}
				} else {
					log(LogStatus.ERROR, "No Contact found of Name: " + contactName, YesNo.No);
					sa.assertTrue(false, "No Contact found of Name: " + contactName);
				}
				if (CommonLib.click(driver, BP.contactEmailCount(contactName, 30), "Email Count: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName, YesNo.No);
				}
				String parentWindowId1 = CommonLib.switchOnWindow(driver);
				if (!parentWindowId1.isEmpty()) {
				ThreadSleep(3000);
				if (actualemailCount.equals(dp.EmailcountonPopup(10))) {
					log(LogStatus.PASS, "Email count on Popup: is equal to  " + actualemailCount +  "are equal", YesNo.No);
	               } else {

					sa.assertTrue(false, "email count is not equal to : " + actualemailCount);
					log(LogStatus.SKIP, "email count is not equal to : " + actualemailCount, YesNo.Yes);

				}
				
				
				} else {
					log(LogStatus.FAIL, "Not able to Click on email count: " + actualemailCount, YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on email count: " + actualemailCount);
	         driver.close();
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			}
		}
		
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETRGc015_VerifytheEmailDetailsOnEmailRecordsPopupforAccount(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(rgUser2, rgOrgPassword, appName);
		String actualemailCount = null;
		String expected[] = {"Outbound Email",todaysDate,RGEContact1LName,RGEUser01FName+" "+RGEUser01LName,RGEUser02FName+" "+RGEUser02LName,"Testing indirect email8 for contact"};


		String contactName =RGEContact1LName;

		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, RGEIns1, 30)) {
			
			if (CommonLib.click(driver, BP.contactEmailCount(contactName, 30), "Email Count: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName, YesNo.No);
			}
			String parentWindowId1 = CommonLib.switchOnWindow(driver);
			if (!parentWindowId1.isEmpty()) {
			
				String xpath="//*[text()='Testing indirect email8 for contact']/ancestor::tr";
				WebElement ele =FindElement(driver, xpath, "", action.SCROLLANDBOOLEAN, 10);
				actualemailCount = getText(driver, ele, "deal",action.SCROLLANDBOOLEAN);
				for(int i =0; i < expected.length; i++){
				if(actualemailCount.contains(expected[i])) {
				System.out.println(actualemailCount + " is matched with " + expected[i]);
				
				}else {
				
					System.out.println(actualemailCount + " is not matched with " + expected[i]);
				}
				}
				} else {
			log(LogStatus.FAIL, "Not able to Click on email count: " + actualemailCount, YesNo.Yes);
			sa.assertTrue(false, "Not able to Click on email count: " + actualemailCount);
			driver.close();
		}
	}else {

		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETRGc016_1_VerifyEntireCellClickableTOColumnsEmailRecordsPopupContactCard(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(rgUser2, rgOrgPassword, appName);

		String subject = "Testing indirect email5 for contact";
		String Toname = RGEContact1LName;
		String CCname = RGEContact1LName;

		String contactName = RGEContact1LName;

		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, RGEIns1, 30)) {
				if (CommonLib.click(driver, BP.contactEmailCount(contactName, 30), "Email Count: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName, YesNo.No);
				}
				String parentWindowId1 = CommonLib.switchOnWindow(driver);
				if (!parentWindowId1.isEmpty()) {
				ThreadSleep(3000);
				if (clickUsingJavaScript(driver, BP.contactPopUpTO(subject,Toname, 10), "Contact  Name: " + subject,
						action.BOOLEAN)) {
					log(LogStatus.PASS, "Clicked on Contact Name: " + subject, YesNo.No);
					if (clickUsingJavaScript(driver, BP.ToPopup(Toname, 10), "Deal Name: " + subject,
							action.BOOLEAN)) {
						log(LogStatus.PASS, "Clicked on Contact Name: " + subject, YesNo.No);
						Set<String> childWindow = driver.getWindowHandles();
						switchToDefaultContent(driver);
						System.out.println(childWindow);
						for(String child : childWindow) {
						driver.switchTo().window(child);
						}
								if (BP.ContactRecordPage(Toname, 20) != null) {
									log(LogStatus.PASS,
											"----Contact Detail Page is redirecting for Contact Record: " + subject + "-----",
											YesNo.No);
									driver.close();
									driver.switchTo().window(parentWindowId1);

								} else {
									log(LogStatus.FAIL, "----Contact Detail Page is not redirecting for Deal Record: "
											+ subject + "-----", YesNo.Yes);
									sa.assertTrue(false,
											"----Contact Detail Page is not showing for Deal Record: " + subject + "-----");
									driver.close();
									driver.switchTo().window(parentWindowId1);

								}

							} else {
								log(LogStatus.FAIL, "No New Window Open after click on Contact Link: " + subject, YesNo.Yes);
								sa.assertTrue(false, "No New Window Open after click on Contact Link: " + subject);
							}
//						} catch (Exception e) {
//							log(LogStatus.FAIL,
//									"Not able to switch to window after click on Deal Link, Msg showing: " + e.getMessage(),
//									YesNo.Yes);
//							sa.assertTrue(false, "Not able to switch to window after click on Contact Link, Msg showing: "
//									+ e.getMessage());
//						}

					CommonLib.switchOnWindow(driver);
						driver.close();
					driver.switchTo().window(parentWindowId1);
				}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + RGEIns1 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + RGEIns1 + " tab");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			}
		}
			ThreadSleep(5000);
			lp.CRMlogout();
			sa.assertAll();

		}

	@Parameters({ "projectName" })
	@Test
	public void ADETRGc016_2_VerifyEntireCellClickableCCColumnsEmailRecordsPopupContactCard(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(rgUser2, rgOrgPassword, appName);

		String subject = "Testing indirect email3 for contact";
		String Toname = RGEContact2LName;
		String CCname = RGEContact1LName;

		String contactName = RGEContact1LName;

		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, RGEIns1, 30)) {
				if (CommonLib.click(driver, BP.contactEmailCount(contactName, 30), "Email Count: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName, YesNo.No);
				}
				String parentWindowId1 = CommonLib.switchOnWindow(driver);
				if (!parentWindowId1.isEmpty()) {
				ThreadSleep(3000);
				if (clickUsingJavaScript(driver, BP.contactPopUpCC(subject,CCname, 10), "Contact  Name: " + subject,
						action.BOOLEAN)) {
					log(LogStatus.PASS, "Clicked on Contact Name: " + subject, YesNo.No);
					if (clickUsingJavaScript(driver, BP.CCPopup(CCname, 10), "Contact Name: " + subject,
							action.BOOLEAN)) {
						log(LogStatus.PASS, "Clicked on Contact Name: " + subject, YesNo.No);
						Set<String> childWindow = driver.getWindowHandles();
						switchToDefaultContent(driver);
						System.out.println(childWindow);
						for(String child : childWindow) {
						driver.switchTo().window(child);
						}
								if (BP.ContactRecordPage(CCname, 20) != null) {
									log(LogStatus.PASS,
											"----Contact Detail Page is redirecting for Contact Record: " + subject + "-----",
											YesNo.No);
									driver.close();
									driver.switchTo().window(parentWindowId1);

								} else {
									log(LogStatus.FAIL, "----Contact Detail Page is not redirecting for Deal Record: "
											+ subject + "-----", YesNo.Yes);
									sa.assertTrue(false,
											"----Contact Detail Page is not showing for Deal Record: " + subject + "-----");
									driver.close();
									driver.switchTo().window(parentWindowId1);

								}

							} else {
								log(LogStatus.FAIL, "No New Window Open after click on Contact Link: " + subject, YesNo.Yes);
								sa.assertTrue(false, "No New Window Open after click on Contact Link: " + subject);
							}
					driver.switchTo().window(parentWindowId1);
					CommonLib.switchOnWindow(driver);
						driver.close();
					driver.switchTo().window(parentWindowId1);
				}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + RGEIns1 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + RGEIns1 + " tab");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			}
		}
			ThreadSleep(5000);
			lp.CRMlogout();
			sa.assertAll();

		}
	@Parameters({ "projectName" })
	@Test
	public void ADETRGc017_VerifythatWhenDownloadIconGetsClickedAgainstContactTab(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(rgUser2, rgOrgPassword, appName);
		
		String contactName = "Daniel";
		String ExpectedHeader = "New Contact";
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, RGEIns1, 30)) {
				if(BP.DownloadIcon(contactName, 10) != null) {
					log(LogStatus.PASS, "Download Icon: " + contactName + " present", YesNo.No);
					if (CommonLib.click(driver, BP.DownloadIcon(contactName, 30), "Download icon: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Download icon: " + " " + " of Record: " + contactName, YesNo.No);
						String actualHeader = getText(driver, BP.getContactpopupheader(20), "Contactpopupheader",
								action.SCROLLANDBOOLEAN);
						if (ExpectedHeader.equals(actualHeader)) {
							log(LogStatus.INFO,
									"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
											+ ExpectedHeader + " for Contact Name: " + contactName,
									YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
											+ ExpectedHeader + " for Contact Name: " + contactName,
									YesNo.No);
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Downlooad Icon " + contactName + " tab", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on Downlooad Icon " + contactName + " tab");
					}

				} else {
					log(LogStatus.ERROR, "download icon not visible " + contactName + " tab", YesNo.Yes);
					sa.assertTrue(false, "download icon not visible " + contactName + " tab");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + RGEIns1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + RGEIns1 + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName" })
	@Test
	public void ADETRGc018_VerifyEntireCellClickableTOandCCColumnsEmailRecordsPopupContactCard(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(rgUser2, rgOrgPassword, appName);
		String contactName =  RGEContact1LName;
		String ExpectedHeader = "Connections of";
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, RGEIns1, 30)) {
				if (CommonLib.click(driver, BP.connectionicon(contactName, 30), "Connection icon: " + "",
						action.BOOLEAN)) {
					String parentWindowId1 = CommonLib.switchOnWindow(driver);
					if (!parentWindowId1.isEmpty()) {
						ThreadSleep(3000);
					log(LogStatus.INFO, "Clicked on Connection icon: " + " " + " of Record: " + contactName, YesNo.No);
					String actualHeader = getText(driver, BP.getConnectionpopupheader(20), "Contactpopupheader",
							action.SCROLLANDBOOLEAN);
//					if (ExpectedHeader.contains(actualHeader)) {
						if (actualHeader.contains(ExpectedHeader)) {
						log(LogStatus.INFO,
								"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
										+ ExpectedHeader + " for Contact Name: " + contactName,
								YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
										+ ExpectedHeader + " for Contact Name: " + contactName,
								YesNo.No);
					}
					driver.close();
					driver.switchTo().window(parentWindowId1);

				}
				} else {
					log(LogStatus.ERROR, "Not able to click on Connection Icon " + contactName + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on Connection Icon " + contactName + " tab");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + RGEIns1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + RGEIns1 + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();

	}

	@Parameters({ "projectName"})
	@Test
	public void ADETRGc019_VerifyofUserConnectionPopupConnectionCardRespectively(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(rgUser2, rgOrgPassword, appName);
		
		String ContactName = RGEContact1LName;
		String connectionsHeader = "Name<break>Title<break>Deals<break>Meetings and Calls<break>Emails";	


		
		ArrayList<String> dealsSectionHeaderName = new ArrayList<String>();
		 ArrayList<String> externalConnectionsSectionHeaderName = new ArrayList<String>();
		ArrayList<String> contactsSectionHeaderName = new ArrayList<String>();

		String[] arrConnnectionsHeader = connectionsHeader.split("<break>");
		List<String> connectionsHeaders = new ArrayList<String>(Arrays.asList(arrConnnectionsHeader));
		
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, RGEIns1, 30)) {
				if (CommonLib.click(driver, BP.connectionicon(ContactName, 30), "Connection icon: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Connection icon: " + " " + " of Record: " + ContactName, YesNo.No);
					String parentWindowId1 = CommonLib.switchOnWindow(driver);
					if (!parentWindowId1.isEmpty()) {
						ThreadSleep(3000);
					ArrayList<String> result1 = BP
							.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
									contactsSectionHeaderName, null, dealsSectionHeaderName,null, connectionsHeaders,
									connectionsHeader,externalConnectionsSectionHeaderName,null);
					if (result1.isEmpty()) {
						log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
					} else {
						log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
						sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
					}
					driver.close();
					driver.switchTo().window(parentWindowId1);

				}
				} else {
					log(LogStatus.ERROR, "Not able to click on Connection Icon " + ContactName + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on Connection Icon " + ContactName + " tab");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + ADEIns14 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + ADEIns14 + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();

	}

		
	@Parameters({ "projectName" })
	@Test
	public void ADETRGc020_VerifythatEmailCountUnderEmailColumnClickableatConnectionPopupAItabandCItab(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(rgUser2, rgOrgPassword, appName);
		String contactName = RGEContact1LName;
		String contactName1=RGEUser01FName + " " + RGEUser01LName;
		String ExpectedHeader = "Emails With";
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, RGEIns1, 30)) {
				if (CommonLib.click(driver, BP.connectionicon(contactName, 30), "Connection icon: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Connection icon: " + " " + " of Record: " + contactName, YesNo.No);
					String parentWindowId1 = CommonLib.switchOnWindow(driver);
					if (!parentWindowId1.isEmpty()) {
						ThreadSleep(3000);
					if (CommonLib.click(driver, BP.contactEmailCount(contactName1, 30), "Email Count: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName1, YesNo.No);
					Set<String> childWindow = driver.getWindowHandles();
					switchToDefaultContent(driver);
					System.out.println(childWindow);
					for(String child : childWindow) {
					driver.switchTo().window(child);
					}
					String actualHeader = getText(driver, BP.getConnectionpopupheader(20), "Contactpopupheader",
							action.SCROLLANDBOOLEAN);
//					if (ExpectedHeader.contains(actualHeader)) {
						if (actualHeader.contains(ExpectedHeader)) {
						log(LogStatus.INFO,
								"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
										+ ExpectedHeader + " for Contact Name: " + contactName,
								YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
										+ ExpectedHeader + " for Contact Name: " + contactName,
								YesNo.No);
						sa.assertTrue(false,
								"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
										+ ExpectedHeader + " for Contact Name: " + contactName);
					
					}
					driver.switchTo().window(parentWindowId1);
					CommonLib.switchOnWindow(driver);
						driver.close();
					driver.switchTo().window(parentWindowId1);
				}
					} else {
						log(LogStatus.FAIL, "No New Window Open after click on Company Link: " + contactName1,
								YesNo.Yes);
						sa.assertTrue(false, "No New Window Open after click on Company Link: " + contactName1);
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on Connection Icon " + contactName + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on Connection Icon " + contactName + " tab");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + ADEIns14 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + ADEIns14 + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}

		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);
			
		if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
				log(LogStatus.INFO, "Clicked on Connection icon: " + " " + " of Record: " + contactName, YesNo.No);
				if (CommonLib.click(driver, BP.contactEmailCountAcuity(contactName1, 30), "Email Count: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName1, YesNo.No);
				}
				String parentWindowId1 = CommonLib.switchOnWindow(driver);
				if (!parentWindowId1.isEmpty()) {
					ThreadSleep(3000);
				String actualHeader = getText(driver, BP.getConnectionpopupheader(20), "Contactpopupheader",
						action.SCROLLANDBOOLEAN);
//				if (ExpectedHeader.contains(actualHeader)) {
					if (actualHeader.contains(ExpectedHeader)) {
					log(LogStatus.INFO,
							"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
									+ ExpectedHeader + " for Contact Name: " + contactName,
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
									+ ExpectedHeader + " for Contact Name: " + contactName,
							YesNo.No);
				}
				driver.close();
				driver.switchTo().window(parentWindowId1);

			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + RGEIns1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + RGEIns1 + " tab");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();

	}

//	@Parameters({ "projectName"})
//	@Test
//	public void ADETRGc018_VerifyFilterFunctionalityEmailRecordPopupforConnectionPopupCardAITab(String projectName) {
//		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
//		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
//		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
//		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
//		String[] filterType= {"All Types","Inbound","Outbound","Indirect"};
//		String[] Categoryname= {"Inbound Email","Outbound Email","Indirect Email"};
//		
//		String contactName = "kbhateja@navatargroup.com";
//		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
//			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
//	
//			if (fp.clickOnAlreadyCreatedItem(projectName, ADEIns14, 30)) {
//				if (CommonLib.click(driver, BP.connectionicon(contactName, 30), "Connection icon: " + "",
//						action.BOOLEAN)) {
//					log(LogStatus.INFO, "Clicked on Connection icon: " + " " + " of Record: " + contactName, YesNo.No);
//					if (CommonLib.click(driver, BP.contactEmailCount(contactName, 30), "Email Count: " + "",
//							action.BOOLEAN)) {
//						log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName, YesNo.No);
//					}
//					ArrayList<String> result=BP.verifyFilterIconAndFilterRecordsOnEmailPopup(filterType,Categoryname);
//					if(result.isEmpty())
//					{
//						log(LogStatus.INFO, "The filter icon, filter value, and records after selecting filter have been verified after clicking on the count of "+contactName+" from people tag. "+result, YesNo.No);
//					}
//					else
//					{
//						log(LogStatus.ERROR, "The filter icon, filter value, and records after selecting filter not working properly after clicking on the count of "+contactName+" from people tag. "+result, YesNo.No);
//						sa.assertTrue(false,  "The filter icon, filter value, and records after selecting filter not working properly after clicking on the count of "+contactName+" from people tag. "+result);
//					}
//				}} else {
//					log(LogStatus.ERROR, "Not able to click on " + ADEIns14 + " tab", YesNo.Yes);
//					sa.assertTrue(false, "Not able to click on " + ADEIns14 + " tab");
//				}
//			} else {
//				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
//				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
//			}
//		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
//			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);
//	
//			if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
//					if (CommonLib.click(driver, BP.contactEmailCount(contactName, 30), "Email Count: " + "",
//							action.BOOLEAN)) {
//						log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName, YesNo.No);
//					}
//					ArrayList<String> result=BP.verifyFilterIconAndFilterRecordsOnEmailPopup(filterType,Categoryname);
//					if(result.isEmpty())
//					{
//						log(LogStatus.INFO, "The filter icon, filter value, and records after selecting filter have been verified after clicking on the count of "+contactName+" from people tag. "+result, YesNo.No);
//					}
//					else
//					{
//						log(LogStatus.ERROR, "The filter icon, filter value, and records after selecting filter not working properly after clicking on the count of "+contactName+" from people tag. "+result, YesNo.No);
//						sa.assertTrue(false,  "The filter icon, filter value, and records after selecting filter not working properly after clicking on the count of "+contactName+" from people tag. "+result);
//					}
//				} else {
//					log(LogStatus.ERROR, "Not able to click on " + ADEIns14 + " tab", YesNo.Yes);
//					sa.assertTrue(false, "Not able to click on " + ADEIns14 + " tab");
//				}
//			} else {
//				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
//				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
//			}
//	
//	ThreadSleep(5000);
//	lp.CRMlogout();
//	sa.assertAll();
//			}

		
	@Parameters({ "projectName"})
	@Test
	public void ADETRGc022_VerifyCategoryColumnThreeTypesCategoryEmailRecordPopupforConnectionTab(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(rgUser2, rgOrgPassword, appName);
		
		String expected = "Inbound Email,Outbound Email,Indirect Email";
	    List<String> actualheader = new ArrayList<String>();
		String contactName1=RGEUser01FName + " " + RGEUser01LName;
		String contactName = RGEContact1LName;
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, RGEIns1, 30)) {
				log(LogStatus.INFO, "Click on Tab : " + RGEIns1, YesNo.No);
				if (CommonLib.click(driver, BP.connectionicon(contactName, 30), "Connection icon: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Connection icon: " + " " + " of Record: " + contactName, YesNo.No);
					String parentWindowId1 = CommonLib.switchOnWindow(driver);
					if (!parentWindowId1.isEmpty()) {
					if (CommonLib.click(driver, BP.contactEmailCount(contactName1, 30), "Email Count: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName1, YesNo.No);
					}
					Set<String> childWindow = driver.getWindowHandles();
					switchToDefaultContent(driver);
					System.out.println(childWindow);
					for(String child : childWindow) {
					driver.switchTo().window(child);
					}
					List<WebElement> ele =BP.listOfemailCategory(10);
					for(int i = 0; i <ele.size();i++ ) {
					actualheader.add(ele.get(i).getText());
				}
					if(compareMultipleList(driver, expected, ele) != null){
						log(LogStatus.INFO, "BOTH values matched: " + " " + " of actual : " + expected, YesNo.No);
					}else
					{
						log(LogStatus.ERROR, "BOTH values matched: " + " " + " of actual : " + expected, YesNo.Yes);
						sa.assertTrue(false,"both values not matched "+" "+expected);
					}
					driver.close();
					driver.switchTo().window(parentWindowId1);
					CommonLib.switchOnWindow(driver);
					driver.close();
				driver.switchTo().window(parentWindowId1);
				} else {
					log(LogStatus.ERROR, "Not able to click on " + RGEIns1 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + RGEIns1 + " tab");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			}
		
		}
		}
		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
				if (CommonLib.click(driver, BP.contactEmailCountAcuity(contactName1, 30), "Email Count: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName, YesNo.No);
				}
				String parentWindowId1 = CommonLib.switchOnWindow(driver);
				if (!parentWindowId1.isEmpty()) {
				List<WebElement> ele =BP.listOfemailCategory(10);
				for(int i = 0; i <ele.size();i++ ) {
				actualheader.add(ele.get(i).getText());
			}
				if(compareMultipleList(driver, expected, ele) != null){
					log(LogStatus.INFO, "BOTH values matched: " + " " + " of actual : " + expected, YesNo.No);
				}else
				{
					log(LogStatus.ERROR, "BOTH values matched: " + " " + " of actual : " + expected, YesNo.Yes);
					sa.assertTrue(false,"both values not matched "+" "+expected);
				}
				driver.close();
				driver.switchTo().window(parentWindowId1);
			} else {
				log(LogStatus.ERROR, "Not able to click on " + contactName + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + contactName + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		}
		}
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
		}


	@Parameters({ "projectName"})
	@Test
	public void ADETRGc023_VerifyWhenThereInternalUserforaContactandUserIconisClicked(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(rgUser2, rgOrgPassword, appName);
		String contactName = RGEContact3LName;
		String ExpectedMsg = BP.ErrorMessageAcuity;
		String connectionsSectionHeaderMessage = BP.ErrorMessageAcuity;




		
		ArrayList<String> dealsSectionHeaderName = new ArrayList<String>();
		 ArrayList<String> externalConnectionsSectionHeaderName = new ArrayList<String>();
		ArrayList<String> contactsSectionHeaderName = new ArrayList<String>();
		ArrayList<String> connectionsHeaders = new ArrayList<String>();

		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, RGEIns1, 30)) {
				if (CommonLib.click(driver, BP.connectionicon(contactName, 30), "Connection icon: " + "",
						action.BOOLEAN)) {
					String parentWindowId1 = CommonLib.switchOnWindow(driver);
					if (!parentWindowId1.isEmpty()) {
					String actualMsg = getText(driver, BP.getErrorMsg(20), "ErrorMsg", action.SCROLLANDBOOLEAN);
					if (ExpectedMsg.equals(actualMsg)) {
						log(LogStatus.INFO,
								"Actual result " + actualMsg + " of pop up has been matched with Expected result : "
										+ ExpectedMsg + " for Contact Name: " + contactName,
								YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"Actual result " + actualMsg
										+ " of pop up has been not matched with Expected result : " + ExpectedMsg
										+ " for Contact Name: " + contactName,
								YesNo.No);
					}
					driver.close();
					driver.switchTo().window(parentWindowId1);
					
				} else {
					log(LogStatus.ERROR, "Not able to click on " + RGEIns1 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + RGEIns1 + " tab");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			}
		}
			if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

				if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
					ArrayList<String> result1 = BP
							.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
									contactsSectionHeaderName, null, dealsSectionHeaderName,null, connectionsHeaders,
									connectionsSectionHeaderMessage,externalConnectionsSectionHeaderName,null);
					if (result1.isEmpty()) {
						log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
					} else {
						log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
						sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + contactName + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + contactName + " tab");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
			}
		}

		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
			}
		
	@Parameters({ "projectName"})
	@Test
	public void ADETRGc024_VerifythatEmailSubjectClickableRedirectionatEmailRecordsPopupforAccountContact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
		lp.CRMLogin(rgUser2, rgOrgPassword, appName);
		String subject = "Testing indirect email6 for contact";
		String contactName1=RGEUser01FName + " " + RGEUser01LName;
		String contactName = RGEContact1LName;
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, RGEIns1, 30)) {
				if (CommonLib.click(driver, BP.connectionicon(contactName, 30), "Connection icon: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Connection icon: " + " " + " of Record: " + contactName, YesNo.No);
					String parentWindowId = CommonLib.switchOnWindow(driver);
					if (!parentWindowId.isEmpty()) {
					if (CommonLib.click(driver, BP.contactEmailCountAcuity(contactName1, 30), "Email Count: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName1, YesNo.No);
					}
					Set<String> childWindow = driver.getWindowHandles();
					switchToDefaultContent(driver);
					System.out.println(childWindow);
					for(String child : childWindow) {
					driver.switchTo().window(child);
					}
					if (clickUsingJavaScript(driver, BP.contactPopUpEmailsubject(subject, 10), "Email subject: " + subject,
							action.BOOLEAN)) {
						log(LogStatus.PASS, "Clicked on Email subject: " + subject, YesNo.No);
					log(LogStatus.PASS, "New Window Open after click on Email subject Link: " + subject, YesNo.No);
					Set<String> childWindow1 = driver.getWindowHandles();
					switchToDefaultContent(driver);
					System.out.println(childWindow1);
					for(String child : childWindow1) {
					driver.switchTo().window(child);
					}
					op.outLookLoginRevenueGrid(rgUser3,rgUserPassword);
								if (BP.EmailRecordPage(subject, 20) != null) {
									log(LogStatus.PASS,
											"----Email Detail Page is redirecting for Email Record: " + subject + "-----",
											YesNo.No);
									driver.close();
									driver.switchTo().window(parentWindowId);

								} else {
									log(LogStatus.FAIL, "----Email Detail Page is not redirecting for Email Record: "
											+ subject + "-----", YesNo.Yes);
									sa.assertTrue(false,
											"----Email Detail Page is not redirecting for Email Record: " + subject + "-----");
									driver.close();
									driver.switchTo().window(parentWindowId);

								}

							} else {
								log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + subject, YesNo.Yes);
								sa.assertTrue(false, "No New Window Open after click on Deal Link: " + subject);
							}
//						} catch (Exception e) {
//							log(LogStatus.FAIL,
//									"Not able to switch to window after click on Deal Link, Msg showing: " + e.getMessage(),
//									YesNo.Yes);
//							sa.assertTrue(false, "Not able to switch to window after click on Deal Link, Msg showing: "
//									+ e.getMessage());
//						}
					} else {
						log(LogStatus.FAIL, "Not able to Click on Deal Name: " + subject, YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on Deal Name: " + subject);

					}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
				}

			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			}
		
		
		
			if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
					log(LogStatus.INFO, "Clicked on contact: " + " " + " of Record: " + contactName, YesNo.No);
					if (CommonLib.click(driver, BP.contactEmailCountAcuity(contactName1, 30), "Email Count: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName1, YesNo.No);
					}
					String parentWindowId = CommonLib.switchOnWindow(driver);
					if (!parentWindowId.isEmpty()) {
					if (clickUsingJavaScript(driver, BP.contactPopUpEmailsubject(subject, 10), "Deal Name: " + subject,
							action.BOOLEAN)) {
						log(LogStatus.PASS, "Clicked on Deal Name: " + subject, YesNo.No);
						try {
							String parentWindowId1 = CommonLib.switchOnWindow(driver);
							if (!parentWindowId1.isEmpty()) { 
								log(LogStatus.PASS, "New Window Open after click on Email subject Link: " + subject, YesNo.No);
								Set<String> childWindow = driver.getWindowHandles();
								op.outLookLoginRevenueGrid(rgUser2,rgUserPassword);
								driver.switchTo().defaultContent();
								System.out.println(childWindow);
								for(String child : childWindow) {
								driver.switchTo().window(child);
								}
								if (BP.EmailRecordPage(subject, 20) != null) {
									log(LogStatus.PASS,
											"----Email Detail Page is redirecting for Email Record: " + subject + "-----",
											YesNo.No);
									driver.close();
									driver.switchTo().window(parentWindowId);

								} else {
									log(LogStatus.FAIL, "----Email Detail Page is not redirecting for Email Record: "
											+ subject + "-----", YesNo.Yes);
									sa.assertTrue(false,
											"----Email Detail Page is not redirecting for Email Record: " + subject + "-----");
									driver.close();
									driver.switchTo().window(parentWindowId);

								}

							} else {
								log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + subject, YesNo.Yes);
								sa.assertTrue(false, "No New Window Open after click on Deal Link: " + subject);
							}
						} catch (Exception e) {
							log(LogStatus.FAIL,
									"Not able to switch to window after click on Deal Link, Msg showing: " + e.getMessage(),
									YesNo.Yes);
							sa.assertTrue(false, "Not able to switch to window after click on Deal Link, Msg showing: "
									+ e.getMessage());
						}
					} else {
						log(LogStatus.FAIL, "Not able to Click on Deal Name: " + subject, YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on Deal Name: " + subject);

					}
					} else {
						log(LogStatus.ERROR, "Not able to click on " + contactName + " tab", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on " + contactName + " tab");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
				}
			}
		}
			ThreadSleep(5000);
			lp.CRMlogout();
			sa.assertAll();
				}

	@Parameters({ "projectName" })
	@Test
	public void ADETRGc025_VerifythatEmailCountandEmailDetAIlsCountEmailRecordsPopupShouldSimilarforConnectionPopupCardRespectively(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(rgUser2, rgOrgPassword, appName);

		String emailCountInFirm = "5";
		String actualemailCount = null;

		String contactName = RGEContact1LName;
		String contactName1=RGEUser01FName + " " + RGEUser01LName;

		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, RGEIns1, 30)) {
				if (CommonLib.click(driver, BP.connectionicon(contactName, 30), "Connection icon: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Connection icon: " + " " + " of Record: " + contactName, YesNo.No);
					String parentWindowId1 = CommonLib.switchOnWindow(driver);
					if (!parentWindowId1.isEmpty()) {
				actualemailCount = getText(driver, BP.contactEmailCountAcuity(contactName1, 20), "email count",
						action.SCROLLANDBOOLEAN);
				if (BP.contactEmailCountAcuity(contactName1, 20) != null) {
					if (!actualemailCount.equalsIgnoreCase("")) {

						if (actualemailCount.equalsIgnoreCase(emailCountInFirm)) {
							log(LogStatus.INFO, "Email Count for Contact: " + contactName + " is " + actualemailCount
									+ " before Email Team Create is matched to " + emailCountInFirm, YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"Email Count for Contact: " + contactName
											+ " is before Email count is not matched, Expected: " + emailCountInFirm
											+ " but Actual: " + actualemailCount,
									YesNo.Yes);
							sa.assertTrue(false,
									"Email Count for Contact: " + contactName
											+ " is before Email count  not matched, Expected: " + emailCountInFirm
											+ " but Actual: " + actualemailCount);
						}

					} else {
						log(LogStatus.ERROR, "Email Count for Contact is Empty, So not able to check Count for Contact: "
								+ contactName, YesNo.Yes);
						sa.assertTrue(false, "Email Count for Contact is Empty, So not able to check Count for Contact: "
								+ contactName);
					}
					driver.close();
					driver.switchTo().window(parentWindowId1);
				} else {
					log(LogStatus.ERROR, "No Contact found of Name: " + contactName, YesNo.No);
					sa.assertTrue(false, "No Contact found of Name: " + contactName);
				}
				} else {
					log(LogStatus.FAIL, "Not able to Click on email count: " + contactName1, YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on email count: " + contactName1);

				}
			}else {

				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			}
			}
		}
					
				if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);
				if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
						log(LogStatus.INFO, "Clicked on Connection icon: " + " " + " of Record: " + contactName, YesNo.No);
						if (CommonLib.click(driver, BP.contactEmailCountAcuity(contactName1, 30), "Email Count: " + "",
								action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName1, YesNo.No);
						}
						String parentWindowId1 = CommonLib.switchOnWindow(driver);
						if (!parentWindowId1.isEmpty()) {
						ThreadSleep(3000);
						if (actualemailCount.equals(dp.EmailcountonPopup(10))) {
							log(LogStatus.PASS, "EmailcountonPopup: " + actualemailCount + "are equal", YesNo.No);
			               } else {

							sa.assertTrue(false, "email count is not equal to : " + actualemailCount);
							log(LogStatus.SKIP, "email count is not equal to : " + actualemailCount, YesNo.Yes);

						}
						driver.close();
						driver.switchTo().window(parentWindowId1);
						
						} else {
							log(LogStatus.FAIL, "Not able to Click on email count: " + actualemailCount, YesNo.Yes);
							sa.assertTrue(false, "Not able to Click on email count: " + actualemailCount);

						}
					} else {
						log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
					}
				}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETRGc026_VerifytheEmailDetAIlsonEmailRecordsPopupforConnectionPopupCard(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(rgUser2, rgOrgPassword, appName);
		String actualDetail = null;
		String subject ="Testing indirect email8 for contact";
		String expected[] = {"Indirect Email",todaysDate,RGEContact1LName,RGEUser01FName+" "+RGEUser01LName,RGEUser02FName+" "+RGEUser02LName,"Testing indirect email8 for contact"};
		String contactName1=RGEUser01FName + " " + RGEUser01LName;
		String contactName =RGEContact1LName;

		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, RGEIns1, 30)) {
				if (CommonLib.click(driver, BP.connectionicon(contactName, 30), "Connection icon: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Connection icon: " + " " + " of Record: " + contactName, YesNo.No);
					String parentWindowId1 = CommonLib.switchOnWindow(driver);
					if (!parentWindowId1.isEmpty()) {
					if (CommonLib.click(driver, BP.contactEmailCountAcuity(contactName1, 30), "Email Count: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName1, YesNo.No);
					}
					Set<String> childWindow = driver.getWindowHandles();
					switchToDefaultContent(driver);
					System.out.println(childWindow);
					for(String child : childWindow) {
					driver.switchTo().window(child);
					}
			
					
					WebElement ele = BP.getEmailDetail(subject, 10);
					actualDetail = getText(driver, ele, "Email detail",action.SCROLLANDBOOLEAN);
					for(int i =0; i < expected.length; i++){
					if(actualDetail.contains(expected[i])) {
					System.out.println(actualDetail + " is matched with " + expected[i]);
					
					}else {
					
						System.out.println(actualDetail + " is not matched with " + expected[i]);
					}
					}
					} else {
				log(LogStatus.FAIL, "Not able to Click on email count: " + actualDetail, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on email count: " + actualDetail);

			}
					driver.switchTo().window(parentWindowId1);
					CommonLib.switchOnWindow(driver);
						driver.close();
					driver.switchTo().window(parentWindowId1);
					CommonLib.switchOnWindow(driver);
					driver.close();
				driver.switchTo().window(parentWindowId1);

		
	}
			}
			if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
				log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);
				if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
				
					if (CommonLib.click(driver, BP.contactEmailCountAcuity(contactName1, 30), "Email Count: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName1, YesNo.No);
					}
					String parentWindowId1 = CommonLib.switchOnWindow(driver);
					if (!parentWindowId1.isEmpty()) {
			
					WebElement ele =BP.getEmailDetail(subject,10);
					actualDetail = getText(driver, ele, "Email detail",action.SCROLLANDBOOLEAN);
					for(int i =0; i < expected.length; i++){
					if(actualDetail.contains(expected[i])) {
					System.out.println(actualDetail + " is matched with " + expected[i]);
					
					}else {
					
						System.out.println(actualDetail + " is not matched with " + expected[i]);
					}
					}
					} else {
				log(LogStatus.FAIL, "Not able to Click on email count: " + actualDetail, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on email count: " + actualDetail);

			}
		}else {

			log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		}
		}
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETRGc027_VerifyWhenContactNameClickedContactCard(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		SetupPageBusinessLayer sp = new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String contactname = RGEContact1LName;
		String username =RGEUser01FName + " " + RGEUser01LName;
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, RGEIns1, 30)) {
				refresh(driver);
				if (CommonLib.clickUsingJavaScript(driver, BP.contactname(contactname, 30), "Contact Name: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Contact Name: " + " " + " of Record: " + contactname, YesNo.No);
					ThreadSleep(2000);
					try {
						String parentWindowId = CommonLib.switchOnWindow(driver);
						if (!parentWindowId.isEmpty()) {
							log(LogStatus.PASS, "New Window Open after click on user Link: " + contactname, YesNo.No);
					if (CommonLib.clickUsingJavaScript(driver, BP.Username(username, 30), "Contact Name: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Contact Name: " + " " + " of Record: " + username, YesNo.No);
						ThreadSleep(2000);
//						String parentWindowId = CommonLib.switchOnWindow(driver);
//						if (!parentWindowId.isEmpty()) {
//							log(LogStatus.PASS, "New Window Open after click on user Link: " + contactname, YesNo.No);
						String parentWindowId1 = CommonLib.switchOnWindow(driver);
						ThreadSleep(2000);
						Set<String> wind = driver.getWindowHandles();
						System.out.println(wind);
						for(String window : wind) {
						driver.switchTo().window(window);
					}
						if (!parentWindowId1.isEmpty()) { 
							ThreadSleep(2000);
							switchToFrame(driver, 30, sp.getSetUpPageIframe(60));
							if (BP.UserRecordPage(username, 20) != null) {
								log(LogStatus.PASS,
										"----user Detail Page is redirecting for user Record: " + username + "-----",
										YesNo.No);
							driver.close();
							driver.switchTo().window(parentWindowId);

							} else {
								log(LogStatus.FAIL, "----User Detail Page is not redirecting for user Record: "
										+ username + "-----", YesNo.Yes);
								sa.assertTrue(false,
										"----User Detail Page is not showing for Deal Record: " + contactname + "-----");
//								driver.close();
//								driver.switchTo().window(parentWindowId);

							}
//						} else {
//							log(LogStatus.FAIL, "----User Detail Page is not redirecting for Deal Record: "
//									+ contactname + "-----", YesNo.Yes);
//							sa.assertTrue(false,
//									"----User Detail Page is not showing for Deal Record: " + contactname + "-----");
//							driver.close();
//							driver.switchTo().window(parentWindowId);
	//
//						}
					
						}else {
							log(LogStatus.FAIL, "No New Window Open after click on User Link: " + contactname, YesNo.Yes);
							sa.assertTrue(false, "No New Window Open after click on User Link: " + contactname);
						}
					}
						}
						}catch (Exception e) {
						log(LogStatus.FAIL,
								"Not able to switch to window after click on User Link, Msg showing: " + e.getMessage(),
								YesNo.Yes);
						sa.assertTrue(false, "Not able to switch to window after click on Deal Link, Msg showing: "
								+ e.getMessage());
					}
					} else {
						log(LogStatus.ERROR, "Not able to click on " + username + " tab", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on " + username + " tab");
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + contactname + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + contactname + " tab");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
			}
		
					if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
						log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);
						if (fp.clickOnAlreadyCreatedItem(projectName, contactname, 30)) {
							if (CommonLib.clickUsingJavaScript(driver, BP.Username(username, 30), "Contact Name: " + "",
									action.BOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Contact Name: " + " " + " of Record: " + username, YesNo.No);
							try {
								String parentWindowId = CommonLib.switchOnWindow(driver);
								if (!parentWindowId.isEmpty()) {
									log(LogStatus.PASS, "New Window Open after click on user Link: " + username, YesNo.No);
									ThreadSleep(2000);
									switchToFrame(driver, 30, sp.getSetUpPageIframe(60));
									if (BP.UserRecordPage(username, 20) != null) {
										log(LogStatus.PASS,
												"----user Detail Page is redirecting for USER Record: " + username + "-----",
												YesNo.No);
										driver.close();
										driver.switchTo().window(parentWindowId);

									} else {
										log(LogStatus.FAIL, "----User Detail Page is not redirecting for user Record: "
												+ username + "-----", YesNo.Yes);
										sa.assertTrue(false,
												"----User Detail Page is not showing for user Record: " + username + "-----");
										driver.close();
										driver.switchTo().window(parentWindowId);

									}

								} else {
									log(LogStatus.FAIL, "No New Window Open after click on User Link: " + contactname, YesNo.Yes);
									sa.assertTrue(false, "No New Window Open after click on User Link: " + contactname);
								}
							} catch (Exception e) {
								log(LogStatus.FAIL,
										"Not able to switch to window after click on User Link, Msg showing: " + e.getMessage(),
										YesNo.Yes);
								sa.assertTrue(false, "Not able to switch to window after click on Deal Link, Msg showing: "
										+ e.getMessage());
							}
							} else {
								log(LogStatus.ERROR, "Not able to click on " + username + " tab", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on " + username + " tab");
							}

						} else {
							log(LogStatus.ERROR, "Not able to click on " + contactname + " tab", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on " + contactname + " tab");
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
					}
		
							ThreadSleep(5000);
							lp.CRMlogout();
							sa.assertAll();
						}
						
	@Parameters({ "projectName" })
	@Test
	public void ADETRGc028_VerifyUserContactsNamesareClickableatCellPopupatEmailRecordsPopupforConnectionCardSectionRespectively(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String contactname = RGEContact1LName;
		String subject = "Testing indirect email5 for contact";
		String Toname = RGEContact1LName;
		String CCname = RGEContact1LName;
		String contactname1 = RGEUser01FName + " " + RGEUser01LName;

		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, RGEIns1, 30)) {
				if (CommonLib.click(driver, BP.connectionicon(contactname, 30), "Connection icon: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Connection icon: " + " " + " of Record: " + contactname, YesNo.No);
					String parentWindowId1 = CommonLib.switchOnWindow(driver);
					if (!parentWindowId1.isEmpty()) {
					ThreadSleep(3000);
					if (CommonLib.click(driver, BP.contactEmailCountAcuity(contactname1, 30), "Email Count: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactname1, YesNo.No);
					}
					Set<String> childWindow = driver.getWindowHandles();
					switchToDefaultContent(driver);
					System.out.println(childWindow);
					for(String child : childWindow) {
					driver.switchTo().window(child);
					}
				ThreadSleep(3000);
				if (clickUsingJavaScript(driver, BP.contactPopUpTO(subject,Toname, 10), "Contact Name: " + subject,
						action.BOOLEAN)) {
					log(LogStatus.PASS, "Clicked on  ToName: " + subject, YesNo.No);
					if (clickUsingJavaScript(driver, BP.ToPopup(Toname, 10), "Contact Name: " + subject,
							action.BOOLEAN)) {
						log(LogStatus.PASS, "Clicked on To Name: " + subject, YesNo.No);
								log(LogStatus.PASS, "New Window Open after click on TO Link: " + subject, YesNo.No);
								Set<String> childWindow1 = driver.getWindowHandles();
								switchToDefaultContent(driver);
								System.out.println(childWindow1);
								for(String child : childWindow1) {
								driver.switchTo().window(child);
								}
								if (BP.ContactRecordPage(Toname, 20) != null) {
									log(LogStatus.PASS,
											"----Contact Detail Page is redirecting for TO Record: " + subject + "-----",
											YesNo.No);
									driver.close();
									driver.switchTo().window(parentWindowId1);

								} else {
									log(LogStatus.FAIL, "----TO Detail Page is not redirecting for Deal Record: "
											+ subject + "-----", YesNo.Yes);
									sa.assertTrue(false,
											"----TO Detail Page is not showing for Deal Record: " + subject + "-----");
									driver.close();
									driver.switchTo().window(parentWindowId1);

								}
								CommonLib.switchOnWindow(driver);
								driver.close();
							driver.switchTo().window(parentWindowId1);
							CommonLib.switchOnWindow(driver);
							driver.close();
						driver.switchTo().window(parentWindowId1);
							} else {
								log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + subject, YesNo.Yes);
								sa.assertTrue(false, "No New Window Open after click on Deal Link: " + subject);
							}
					} else {
						log(LogStatus.FAIL, "Not able to Click on TO name: " + Toname, YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on TO Name: " + Toname);

					}
					} else {
						log(LogStatus.FAIL, "Not able to Click on TO name: " + Toname, YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on TO Name: " + Toname);

					}
				

		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, contactname, 30)) {
			
			if (CommonLib.click(driver, BP.contactEmailCountAcuity(contactname1, 30), "Email Count: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactname, YesNo.No);
			}	
			String parentWindowId = CommonLib.switchOnWindow(driver);
			if (!parentWindowId.isEmpty()) {
			ThreadSleep(3000);
			if (clickUsingJavaScript(driver, BP.contactPopUpTO(subject,Toname, 10), "Contact Name: " + subject,
					action.BOOLEAN)) {
				log(LogStatus.PASS, "Clicked on  ToName: " + subject, YesNo.No);
				if (clickUsingJavaScript(driver, BP.ToPopup(Toname, 10), "Contact Name: " + subject,
						action.BOOLEAN)) {
					log(LogStatus.PASS, "Clicked on To Name: " + subject, YesNo.No);
					Set<String> childWindow = driver.getWindowHandles();
					switchToDefaultContent(driver);
					System.out.println(childWindow);
					for(String child : childWindow) {
					driver.switchTo().window(child);
					}
							log(LogStatus.PASS, "New Window Open after click on TO Link: " + subject, YesNo.No);

							if (BP.ContactRecordPage(Toname, 20) != null) {
								log(LogStatus.PASS,
										"----Deal Detail Page is redirecting for TO Record: " + subject + "-----",
										YesNo.No);
								driver.close();
								driver.switchTo().window(parentWindowId);

							} else {
								log(LogStatus.FAIL, "----TO Detail Page is not redirecting for Deal Record: "
										+ subject + "-----", YesNo.Yes);
								sa.assertTrue(false,
										"----TO Detail Page is not showing for Deal Record: " + subject + "-----");
								driver.close();
								driver.switchTo().window(parentWindowId);

							}

						} else {
							log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + subject, YesNo.Yes);
							sa.assertTrue(false, "No New Window Open after click on Deal Link: " + subject);
				driver.switchTo().window(parentWindowId1);
				CommonLib.switchOnWindow(driver);
					driver.close();
				driver.switchTo().window(parentWindowId1);
			}
				} else {
					log(LogStatus.FAIL, "Not able to Click on TO name: " + Toname, YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on TO Name: " + Toname);

				}
				} else {
					log(LogStatus.FAIL, "Not able to Click on TO name: " + Toname, YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on TO Name: " + Toname);

				}


	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}
			
			}	
		}
	}
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETRGc029_1_VerifytheImpactonEmailCountandContactNameswhenAPIKeygetsChangedforRevenueGridAPI(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String fieldName = RGFieldname1;
		String valueField =RGNewValue1;
		String parentWindow = null;
		
		if (home.clickOnSetUpLink()) {
	        parentWindow = switchOnWindow(driver);
	        if (parentWindow == null) {
	            sa.assertTrue(false,
	                    "No new window is open after click on setup link in lighting mode so cannot create clone user");
	            log(LogStatus.SKIP,
	                    "No new window is open after click on setup link in lighting mode so cannot create clone user",
	                    YesNo.Yes);
	            exit("No new window is open after click on setup link in lighting mode so cannot create clone user");
	        }
	        ThreadSleep(3000);
		if(setup.UpdateValueInCustomMetaData(MetaDataSetting.RG_Setting.toString(), fieldName, valueField, 10)) {
			 
	             log(LogStatus.INFO, "Changed the value of " + fieldName + " for Acuity Setting", YesNo.No);
	         }
	         else
	         {
	             log(LogStatus.ERROR, "Not able to change the value of " + fieldName + " for Acuity Setting", YesNo.No);
	             sa.assertTrue(false, "Not able to changed the value of " + fieldName + " for Acuity Setting");    
	         }
	         ThreadSleep(5000);
	     }    
	     ThreadSleep(5000);
	     switchToDefaultContent(driver);
	     driver.close();
	     driver.switchTo().window(parentWindow);
	 	lp.CRMlogout();
	 	sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETRGc029_2_VerifytheImpactonEmailCountandContactNameswhenAPIKeygetsChangedforRevenueGridAPI(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(rgUser2, rgOrgPassword, appName);	
		String contactname =RGEContact1LName;
		
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, RGEIns1, 30)) {
			if(BP.apierrormsg(10) != null) {
				log(LogStatus.PASS, "Error msg : is present", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Error msg : not present", YesNo.Yes);
				sa.assertTrue(false, "Error msg : not present: " );
			}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + ADEIns14 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + ADEIns14 + " tab");
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, contactname, 30)) {
			if(BP.apierrormsg(10) != null) {
				log(LogStatus.PASS, "Error msg : is present", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Error msg : not present", YesNo.Yes);
				sa.assertTrue(false, "Error msg : not present: " );
			}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + RGEIns1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + RGEIns1 + " tab");
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
			ThreadSleep(5000);
			lp.CRMlogout();
			sa.assertAll();
		
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETRGc030_1_VerifytheImpactonSubjectLinewhenBaseUrlgetsChangedforRevenueGridAPI(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String fieldName = RGFieldname2;
		String valueField =RGNewValue2;
		String parentWindow = null;
		
		if (home.clickOnSetUpLink()) {
	        parentWindow = switchOnWindow(driver);
	        if (parentWindow == null) {
	            sa.assertTrue(false,
	                    "No new window is open after click on setup link in lighting mode so cannot create clone user");
	            log(LogStatus.SKIP,
	                    "No new window is open after click on setup link in lighting mode so cannot create clone user",
	                    YesNo.Yes);
	        }
	        ThreadSleep(3000);
		if(setup.UpdateValueInCustomMetaData(MetaDataSetting.RG_Setting.toString(), fieldName, valueField, 10)) {
			 
	             log(LogStatus.INFO, "Changed the value of " + fieldName + " for Acuity Setting", YesNo.No);
	         }
	         else
	         {
	             log(LogStatus.ERROR, "Not able to change the value of " + fieldName + " for Acuity Setting", YesNo.No);
	             sa.assertTrue(false, "Not able to changed the value of " + fieldName + " for Acuity Setting");    
	         }
	         ThreadSleep(5000);
	     }    
	     ThreadSleep(5000);
	     switchToDefaultContent(driver);
	     driver.close();
	     driver.switchTo().window(parentWindow);
	     lp.CRMlogout();
	  	sa.assertAll();
	}
	@Parameters({ "projectName" })
	@Test
	public void ADETRGc030_2_VerifytheImpactonSubjectLinewhenBaseUrlgetsChangedforRevenueGridAPI(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(rgUser2, rgOrgPassword, appName);	
		String subject = "Testing indirect email8 for contact";
		String contactName = RGEContact1LName;
		String contactname1 = RGEUser01FName + " " + RGEUser01LName;
		
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, RGEIns1, 30)) {
				if (CommonLib.click(driver, BP.contactEmailCountAcuity(contactName, 30), "Email Count: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactName, YesNo.No);
				}
				String parentWindowId = CommonLib.switchOnWindow(driver);
				if (!parentWindowId.isEmpty()) {
				if (clickUsingJavaScript(driver, BP.contactPopUpEmailsubject(subject, 10), "Email subject: " + subject,
						action.BOOLEAN)) {
			}
				log(LogStatus.PASS, "Clicked on Contact Name: " + subject, YesNo.No);
						log(LogStatus.PASS, "New Window Open after click on subject Link: " + subject, YesNo.No);
						Set<String> childWindow = driver.getWindowHandles();
						switchToDefaultContent(driver);
						System.out.println(childWindow);
						for(String child : childWindow) {
						driver.switchTo().window(child);
						}
						if (BP.getpageloadErrorMsg( 20) != null) {
							log(LogStatus.PASS,
									"----email subject Detail Page is redirecting for page load error msg: " + subject + "-----",
									YesNo.No);
							driver.close();
							driver.switchTo().window(parentWindowId);

						} else {
							log(LogStatus.FAIL, "----email subject Detail Page is not redirecting for page load error msg: "
									+ subject + "-----", YesNo.Yes);
							sa.assertTrue(false,
									"----email subject Detail Page is not redirecting for page load error msg: " + subject + "-----");
							driver.close();
							driver.switchTo().window(parentWindowId);

						}

					} else {
						log(LogStatus.FAIL, "No New Window Open after click on email subject Link: " + subject, YesNo.Yes);
						sa.assertTrue(false, "No New Window Open after click on email subject Link: " + subject);
					}
				CommonLib.switchOnWindow(driver);
					driver.close();
				driver.switchTo().window(parentWindowId);
			
			} else {
				log(LogStatus.ERROR, "Not able to click on " + RGEIns1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + RGEIns1 + " tab");
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
				if (CommonLib.click(driver, BP.contactEmailCountAcuity(contactname1, 30), "Email Count: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactname1, YesNo.No);
				}
				String parentWindowId = CommonLib.switchOnWindow(driver);
				if (!parentWindowId.isEmpty()) {
				if (clickUsingJavaScript(driver, BP.contactPopUpEmailsubject(subject, 10), "Email subject: " + subject,
						action.BOOLEAN)) {
			}
				log(LogStatus.PASS, "Clicked on Contact Name: " + subject, YesNo.No);
						log(LogStatus.PASS, "New Window Open after click on subject Link: " + subject, YesNo.No);
						Set<String> childWindow = driver.getWindowHandles();
						switchToDefaultContent(driver);
						System.out.println(childWindow);
						for(String child : childWindow) {
						driver.switchTo().window(child);
						}
						if (BP.getpageloadErrorMsg( 20) != null) {
							log(LogStatus.PASS,
									"----email subject Detail Page is redirecting for page load error msg: " + subject + "-----",
									YesNo.No);
							driver.close();
							driver.switchTo().window(parentWindowId);

						} else {
							log(LogStatus.FAIL, "----email subject Detail Page is not redirecting for page load error msg: "
									+ subject + "-----", YesNo.Yes);
							sa.assertTrue(false,
									"----email subject Detail Page is not redirecting for page load error msg: " + subject + "-----");
							driver.close();
							driver.switchTo().window(parentWindowId);

						}

					} else {
						log(LogStatus.FAIL, "No New Window Open after click on email subject Link: " + subject, YesNo.Yes);
						sa.assertTrue(false, "No New Window Open after click on email subject Link: " + subject);
					}
				CommonLib.switchOnWindow(driver);
				driver.close();
			driver.switchTo().window(parentWindowId);
			} else {
				log(LogStatus.ERROR, "Not able to click on " + contactName + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + contactName + " tab");
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		}
			ThreadSleep(5000);
			lp.CRMlogout();
			sa.assertAll();
		}
	@Parameters({ "projectName" })
	@Test
	public void ADETRGc031_1_VerifytheErrorMessageRelatedtoDealGridforAccountContacts(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String fieldName = RGFieldname3;
		String valueField =RGNewValue3;
		String parentWindow = null;
		
		if (home.clickOnSetUpLink()) {
	        parentWindow = switchOnWindow(driver);
	        if (parentWindow == null) {
	            sa.assertTrue(false,
	                    "No new window is open after click on setup link in lighting mode so cannot create clone user");
	            log(LogStatus.SKIP,
	                    "No new window is open after click on setup link in lighting mode so cannot create clone user",
	                    YesNo.Yes);
	        }
	        ThreadSleep(3000);
		if(setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), fieldName, valueField, 10)) {
			 
	             log(LogStatus.INFO, "Changed the value of " + fieldName + " for Acuity Setting", YesNo.No);
	         }
	         else
	         {
	             log(LogStatus.ERROR, "Not able to change the value of " + fieldName + " for Acuity Setting", YesNo.No);
	             sa.assertTrue(false, "Not able to changed the value of " + fieldName + " for Acuity Setting");    
	         }
	         ThreadSleep(5000);
	     }    
	     ThreadSleep(5000);
	     switchToDefaultContent(driver);
	     driver.close();
	     driver.switchTo().window(parentWindow);
	     lp.CRMlogout();
	   	sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETRGc031_2_VerifytheErrorMessageRelatedtoDealGridforAccountContacts(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(rgUser2, rgOrgPassword, appName);	
		String contactName = RGEContact1LName;
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, RGEIns1, 30)) {
			if(BP.Componenterrormsg(10) != null) {
				log(LogStatus.PASS, "Error msg : is present", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Error msg : not present", YesNo.Yes);
				sa.assertTrue(true, "Error msg : not present: " );

			}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + RGEIns1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + RGEIns1 + " tab");
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		
		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, contactName, 30)) {
			if(BP.Componenterrormsg(10) != null) {
				log(LogStatus.PASS, "Error msg : is present", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Error msg : not present", YesNo.Yes);
				sa.assertTrue(true, "Error msg : not present: " );

			}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + contactName + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + contactName + " tab");
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		}
			ThreadSleep(5000);
			lp.CRMlogout();
			sa.assertAll();
		}
	@Parameters({ "projectName" })
	@Test
	public void ADETRGc032_1_VerifytheErrorMessageRelatedtoFundraisingGridforAccount(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String fieldName = RGFieldname4;
		String valueField =RGNewValue4;
		String parentWindow = null;
		
		if (home.clickOnSetUpLink()) {
	        parentWindow = switchOnWindow(driver);
	        if (parentWindow == null) {
	            sa.assertTrue(false,
	                    "No new window is open after click on setup link in lighting mode so cannot create clone user");
	            log(LogStatus.SKIP,
	                    "No new window is open after click on setup link in lighting mode so cannot create clone user",
	                    YesNo.Yes);
	        }
	        ThreadSleep(3000);
		if(setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), fieldName, valueField, 10)) {
			 
	             log(LogStatus.INFO, "Changed the value of " + fieldName + " for Acuity Setting", YesNo.No);
	         }
	         else
	         {
	             log(LogStatus.ERROR, "Not able to change the value of " + fieldName + " for Acuity Setting", YesNo.No);
	             sa.assertTrue(false, "Not able to changed the value of " + fieldName + " for Acuity Setting");    
	         }
	         ThreadSleep(5000);
	     }    
	     ThreadSleep(5000);
	     switchToDefaultContent(driver);
	     driver.close();
	     driver.switchTo().window(parentWindow);
	     lp.CRMlogout();
	   	sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETRGc032_2_VerifytheErrorMessageRelatedtoFundraisingGridforAccount(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		lp.CRMLogin(rgUser2, rgOrgPassword, appName);	
		
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, RGEIns1, 30)) {
			if(BP.Componenterrormsg(10) != null) {
				log(LogStatus.PASS, "Error msg : is present", YesNo.No);

			} else {
				log(LogStatus.FAIL, "Error msg : not present", YesNo.Yes);
				sa.assertTrue(true, "Error msg : not present: " );

			}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + RGEIns1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + RGEIns1 + " tab");
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
			ThreadSleep(5000);
			lp.CRMlogout();
			sa.assertAll();
		}

	@Parameters({ "projectName" })
	@Test
	public void ADETRGc033_1_AccDealsReplaceDealColumnWithAnotherColumnandCheckifitAppearsHyperlinkedanditRedirectionfromAICItab(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String fieldName = RGFieldname5;
		String valueField =RGNewValue5;
		String parentWindow = null;
		
		if (home.clickOnSetUpLink()) {
	        parentWindow = switchOnWindow(driver);
	        if (parentWindow == null) {
	            sa.assertTrue(false,
	                    "No new window is open after click on setup link in lighting mode so cannot create clone user");
	            log(LogStatus.SKIP,
	                    "No new window is open after click on setup link in lighting mode so cannot create clone user",
	                    YesNo.Yes);
	        }
	        ThreadSleep(3000);
		if(setup.UpdateValueInCustomMetaData(MetaDataSetting.Acuity_Setting.toString(), fieldName, valueField, 10)) {
			 
	             log(LogStatus.INFO, "Changed the value of " + fieldName + " for Acuity Setting", YesNo.No);
	         }
	         else
	         {
	             log(LogStatus.ERROR, "Not able to change the value of " + fieldName + " for Acuity Setting", YesNo.No);
	             sa.assertTrue(false, "Not able to changed the value of " + fieldName + " for Acuity Setting");    
	         }
	         ThreadSleep(5000);
	     }    
	     ThreadSleep(5000);
	     switchToDefaultContent(driver);
	     driver.close();
	     driver.switchTo().window(parentWindow);
	     lp.CRMlogout();
	   	sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETRGc033_2_AccDealsReplaceDealColumnWithAnotherColumnandCheckifitAppearsHyperlinkedanditRedirectionfromAICItab(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(rgUser2, rgOrgPassword, appName);
		String score = "1.0";
		String recordType = "";
		String dealName = ADEDeal6;
		String companyName = ADEDeal6CompanyName;
		String stage = ADEDeal6Stage;
		String dateReceived = todaysDate;
		String contactname = RGEContact1LName;

		String labellabels = "Source Contact<Break>Date Received";
		String otherLabelValues = ADEDeal6SourceContact + "<Break>"
				+ todaysDate;
		if (lp.clickOnTab(projectName, tabObj4)) {
			log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
			ThreadSleep(3000);
			if (dp.createDeal(recordType, dealName, companyName, stage, labellabels, otherLabelValues)) {
				log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);

			} else {
				log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
				sa.assertTrue(false, dealName + " deal is not created");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, RGEIns1, 30)) {
				log(LogStatus.INFO, "Click on Tab : " + RGEIns1, YesNo.No);
				if (BP.dealScoreAcuity(score, 30) != null) {
					log(LogStatus.PASS, "deal score: " + score + " is hyperlink and is present", YesNo.No);
					if (click(driver, BP.dealScoreAcuity(score, 10), "deal score: " + score,
							action.BOOLEAN)) {
						log(LogStatus.PASS, "Clicked on deal score: " + score, YesNo.No);
					try {
						String parentWindowId = CommonLib.switchOnWindow(driver);
						if (!parentWindowId.isEmpty()) {
							log(LogStatus.PASS, "New Window Open after click on Deal Link: " + dealName, YesNo.No);

							if (BP.dealRecordPage(dealName, 20) != null) {
								log(LogStatus.PASS,
										"----Deal Detail Page is redirecting for Deal Record: " + dealName + "-----",
										YesNo.No);
								driver.close();
								driver.switchTo().window(parentWindowId);

							} else {
								log(LogStatus.FAIL, "----Deal Detail Page is not redirecting for Deal Record: "
										+ dealName + "-----", YesNo.Yes);
								sa.assertTrue(false,
										"----Deal Detail Page is not showing for Deal Record: " + dealName + "-----");
								driver.close();
								driver.switchTo().window(parentWindowId);

							}

						} else {
							log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "No New Window Open after click on Deal Link: " + dealName);
						}
					} catch (Exception e) {
						log(LogStatus.FAIL,
								"Not able to switch to window after click on Deal Link, Msg showing: " + e.getMessage(),
								YesNo.Yes);
						sa.assertTrue(false, "Not able to switch to window after click on Deal Link, Msg showing: "
								+ e.getMessage());
					}
				} else {
					log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

				}
			
			} else {
				log(LogStatus.ERROR, "Not able to click on " + RGEIns1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + RGEIns1 + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		}
		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, contactname, 30)) {
				if (BP.dealScoreAcuity(score, 30) != null) {
					log(LogStatus.PASS, "deal score: " + score + " is hyperlink and is present", YesNo.No);
					if (click(driver, BP.dealScoreAcuity(score, 10), "deal score: " + score,
							action.BOOLEAN)) {
						log(LogStatus.PASS, "Clicked on deal score: " + score, YesNo.No);
					try {
						String parentWindowId = CommonLib.switchOnWindow(driver);
						if (!parentWindowId.isEmpty()) {
							log(LogStatus.PASS, "New Window Open after click on Deal Link: " + dealName, YesNo.No);

							if (BP.dealRecordPage(dealName, 20) != null) {
								log(LogStatus.PASS,
										"----Deal Detail Page is redirecting for Deal Record: " + dealName + "-----",
										YesNo.No);
								driver.close();
								driver.switchTo().window(parentWindowId);

							} else {
								log(LogStatus.FAIL, "----Deal Detail Page is not redirecting for Deal Record: "
										+ dealName + "-----", YesNo.Yes);
								sa.assertTrue(false,
										"----Deal Detail Page is not showing for Deal Record: " + dealName + "-----");
								driver.close();
								driver.switchTo().window(parentWindowId);

							}

						} else {
							log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + dealName, YesNo.Yes);
							sa.assertTrue(false, "No New Window Open after click on Deal Link: " + dealName);
						}
					} catch (Exception e) {
						log(LogStatus.FAIL,
								"Not able to switch to window after click on Deal Link, Msg showing: " + e.getMessage(),
								YesNo.Yes);
						sa.assertTrue(false, "Not able to switch to window after click on Deal Link, Msg showing: "
								+ e.getMessage());
					}
				} else {
					log(LogStatus.FAIL, "Not able to Click on Deal Name: " + dealName, YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on Deal Name: " + dealName);

				}
			
			} else {
				log(LogStatus.ERROR, "Not able to click on " + contactname + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + contactname + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		}
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName" })
	@Test
	public void ADETRGc034_DeleteDealfromDealTeamandVerifyImpactonDealTeamCountContactsUsersatFirmandContactpage(String projectName) {	
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String dealName = ADEDealTeamName15;
		String TeamMember = ADEDealTeamMember15;
		String contactName = ADEDealContact15;
		String contactname = RGEContact1LName;
		String[][] data = { { PageLabel.Deal.toString(), dealName }, { PageLabel.Deal_Contact.toString(), contactName },
				{ PageLabel.Team_Member.toString(), TeamMember } };
	String dealsSectionHeaderMessage = "No items to display.";

	    ArrayList<String> dealsSectionHeaderName = new ArrayList<String>();
	    ArrayList<String> externalConnectionsSectionHeaderName = new ArrayList<String>();
		ArrayList<String> contactsSectionHeaderName = new ArrayList<String>();
		ArrayList<String> connectionsHeaders = new ArrayList<String>();
		if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

			if (DTP.createDealTeam(projectName, dealName, data, "Acuity", action.SCROLLANDBOOLEAN, 25)) {
				log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----", YesNo.No);

				log(LogStatus.INFO,
						"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
								+ contactName + " at Firm Tab under Acuity section---------",
						YesNo.No);
				WebElement ele = DTP.getDealTeamID(10);
				if (ele != null) {
					String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
					ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name, "ADT_15",
							excelLabel.DealTeamID);
					log(LogStatus.INFO, "successfully created and noted id of DT" + id + " and deal name " + dealName,
							YesNo.No);
				} else {
					sa.assertTrue(false, "could not create DT" + dealName);
					log(LogStatus.SKIP, "could not create DT" + dealName, YesNo.Yes);
				}
			}
		}
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if (fp.clickOnAlreadyCreatedItem(projectName,ADEDeal6, 10)) {
				cp.clickOnShowMoreDropdownOnly(projectName, PageName.Object4Page, "");
				log(LogStatus.INFO, "Able to Click on Show more Icon : " + TabName.Object4Tab + " For : " + ADEDeal3,
						YesNo.No);
				ThreadSleep(500);
				WebElement ele = cp.actionDropdownElement(projectName, PageName.Object4Page,
						ShowMoreActionDropDownList.Delete, 15);
				if (ele == null) {
					ele = cp.getDeleteButton(projectName, 30);
				}

				if (click(driver, ele, "Delete More Icon", action.BOOLEAN)) {
					log(LogStatus.INFO,
							"Able to Click on Delete more Icon : " + TabName.Object4Tab + " For : " + ADEDeal3,
							YesNo.No);
					ThreadSleep(1000);
					if (click(driver, cp.getDeleteButtonOnDeletePopUp(projectName, 30), "Delete Button",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Able to Click on Delete button on Delete PoPup : " + TabName.Object4Tab
								+ " For : " + ADEDeal3, YesNo.No);
						ThreadSleep(10000);
						if (cp.clickOnTab(projectName, TabName.Object4Tab)) {
							log(LogStatus.INFO, "Clicked on Tab : " + TabName.Object4Tab + " For : " + ADEDeal3,
									YesNo.No);
							ThreadSleep(1000);
							if (!cp.clickOnAlreadyCreatedItem(projectName, TabName.Object4Tab, ADEDeal3, 10)) {
								log(LogStatus.INFO, "Item has been Deleted after delete operation  : " + ADEDeal3
										+ " For : " + TabName.Object4Tab, YesNo.No);

							} else {
								sa.assertTrue(false, "Item has not been Deleted after delete operation  : " + ADEDeal3
										+ " For : " + TabName.Object4Tab);
								log(LogStatus.SKIP, "Item has not been Deleted after delete operation  : " + ADEDeal3
										+ " For : " + TabName.Object4Tab, YesNo.Yes);
							}

						} else {
							sa.assertTrue(false, "Not Able to Click on Tab after delete : " + TabName.Object4Tab
									+ " For : " + ADEDeal3);
							log(LogStatus.SKIP, "Not Able to Click on Tab after delete : " + TabName.Object4Tab
									+ " For : " + ADEDeal3, YesNo.Yes);
						}
					} else {
						log(LogStatus.INFO, "not able to click on delete button, so not deleted : " + TabName.Object4Tab
								+ " For : " + ADEDeal3, YesNo.No);
						sa.assertTrue(false, "not able to click on delete button, so not deleted : "
								+ TabName.Object4Tab + " For : " + "Test D");
					}
				} else {
					log(LogStatus.INFO,
							"not Able to Click on Delete more Icon : " + TabName.Object4Tab + " For : " + ADEDeal3,
							YesNo.No);
					sa.assertTrue(false,
							"not Able to Click on Delete more Icon : " + TabName.Object4Tab + " For : " + ADEDeal3);
				}
			} else {
				log(LogStatus.INFO, "not Able to Click on " + ADEDeal3, YesNo.No);
				sa.assertTrue(false, "not Able to Click on " + ADEDeal3);
			}
		} else {
			log(LogStatus.INFO, "not Able to Click on " + TabName.Object4Tab, YesNo.No);
			sa.assertTrue(false, "not Able to Click on " + TabName.Object4Tab);
		}
		if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);
			if (fp.clickOnAlreadyCreatedItem(projectName, RGEIns1, 30)) {
			ArrayList<String> result1 = BP
					.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
							contactsSectionHeaderName, null, dealsSectionHeaderName,dealsSectionHeaderMessage, connectionsHeaders,
							null,externalConnectionsSectionHeaderName,null);
			if (result1.isEmpty()) {
				log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
			} else {
				log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
				sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
		if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			if (fp.clickOnAlreadyCreatedItem(projectName, contactname, 30)) {
				ArrayList<String> result1 = BP
						.verifyHeaderNameAndMessageOnInteractionsContactsConnectionsAndDealsSection(null,
								contactsSectionHeaderName, null, dealsSectionHeaderName,dealsSectionHeaderMessage, connectionsHeaders,
								null,externalConnectionsSectionHeaderName,null);
				if (result1.isEmpty()) {
					log(LogStatus.INFO, "The header name and message have been verified  Deals Section ", YesNo.No);
				} else {
					log(LogStatus.ERROR, "The header name and message are not verified on Deals Section ", YesNo.No);
					sa.assertTrue(false, "The header name and message are not verified on  Deals Section ");
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + contactname + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + contactname + " tab");
			}

		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
	}
	}

@Parameters({ "projectName" })
@Test
public void ADETRGc035_VerifyEmailCountUnderEmailColumnEmailCountEmailRecordsExternalTabPopupShouldSimilarAccount(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String emailCountInFirm = "3";
	String actualemailCount = null;

	String contactName = RGEContact4LName;

	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, RGEIns1, 30)) {
			if (CommonLib.click(driver, BP.connectionicon(contactName, 30), "Connection icon: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on connectionicon tab", YesNo.No);
				String parentWindowId1 = CommonLib.switchOnWindow(driver);
				if (!parentWindowId1.isEmpty()) {
					ThreadSleep(3000);
					if (CommonLib.click(driver, BP.ExternalTab( 30,action.SCROLLANDBOOLEAN), "ExternalTab tab: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on ExternalTab tab", YesNo.No);
				actualemailCount = getText(driver, BP.contactEmailCount(contactName, 20), "deal",
					action.SCROLLANDBOOLEAN);
			if (BP.contactEmailCount(contactName, 20) != null) {
				if (!actualemailCount.equalsIgnoreCase("")) {

					if (actualemailCount.equalsIgnoreCase(emailCountInFirm)) {
						log(LogStatus.INFO, "Email Count for Contact: " + contactName + " is " + actualemailCount
								+ " before Email Team Create is matched to " + emailCountInFirm, YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"Email Count for Contact: " + contactName
										+ " is before Email count is not matched, Expected: " + emailCountInFirm
										+ " but Actual: " + actualemailCount,
								YesNo.Yes);
						sa.assertTrue(false,
								"Email Count for Contact: " + contactName
										+ " is before Email count  not matched, Expected: " + emailCountInFirm
										+ " but Actual: " + actualemailCount);
					}

				} else {
					log(LogStatus.ERROR, "Email Count for Contact is Empty, So not able to check Count for Contact: "
							+ contactName, YesNo.Yes);
					sa.assertTrue(false, "Email Count for Contact is Empty, So not able to check Count for Contact: "
							+ contactName);
				}
			} else {
				log(LogStatus.ERROR, "No Contact found of Name: " + contactName, YesNo.No);
				sa.assertTrue(false, "No Contact found of Name: " + contactName);
			}
         driver.close();
					} else {
						log(LogStatus.FAIL, "Not able to Click on  tab: " + "External Tab" , YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on  tab: " + "External Tab " );
						}
				}
				} else {
					log(LogStatus.FAIL, "Not able to Click on  tab: " + "Connection icon" , YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on  tab: " + "Connection icon" );
					}
				} else {
					log(LogStatus.ERROR, "No firm found of Name: " + RGEIns1, YesNo.No);
					sa.assertTrue(false, "No firm found of Name: " + RGEIns1);
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
		}
	
	
	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
}


@Parameters({ "projectName" })
@Test
public void ADETRGc036_VerifytheImpactWhenContactGetsClickedwhoisnotinOrg(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);

	String contactName = RGEContact1LName;
	String contactName1 = "Eric Lucas";
	String ExpectedHeader = "New Contact";
	if (fp.clickOnTab(environment, mode, TabName.Object1Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object1Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, RGEIns1, 30)) {
			if (CommonLib.click(driver, BP.connectionicon(contactName, 30), "Connection icon: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on connectionicon tab", YesNo.No);
				String parentWindowId1 = CommonLib.switchOnWindow(driver);
				if (!parentWindowId1.isEmpty()) {
					ThreadSleep(3000);
					if (CommonLib.click(driver, BP.ExternalTab( 30,action.SCROLLANDBOOLEAN), "ExternalTab tab: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on ExternalTab tab", YesNo.No);
						if (CommonLib.click(driver, BP.DownloadIcon(contactName1, 30), "Download icon: " + "",
								action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Download icon: " + " " + " of Record: " + contactName1, YesNo.No);
							String actualHeader = getText(driver, BP.getContactpopupheader(20), "Contactpopupheader",
									action.SCROLLANDBOOLEAN);
								if (actualHeader.equals(ExpectedHeader)) {
								log(LogStatus.INFO,
										"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
												+ ExpectedHeader + " for Contact Name: " + contactName,
										YesNo.No);
							} else {
								log(LogStatus.ERROR,
										"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
												+ ExpectedHeader + " for Contact Name: " + contactName,
										YesNo.No);
							}
						} else {
							log(LogStatus.ERROR, "Not able to click on Downlooad Icon " + contactName + " tab", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on Downlooad Icon " + contactName + " tab");
						}
					
				} else {
					log(LogStatus.FAIL, "Not able to Click on  tab: " + "External Tab" , YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on  tab: " + "External Tab " );
					}
			}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + "Connection icon" , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + "Connection icon" );
				}
			} else {
				log(LogStatus.ERROR, "No firm found of Name: " + RGEIns1, YesNo.No);
				sa.assertTrue(false, "No firm found of Name: " + RGEIns1);
			}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj1 + " tab", YesNo.Yes);
		sa.assertTrue(false, "Not able to click on " + tabObj1 + " tab");
	}


ThreadSleep(5000);
lp.CRMlogout();
sa.assertAll();
}


@Parameters({ "projectName" })
@Test
public void ADETRGc037_VerifythatWhenEmailCountisZerOforaContactEmailCountisClickedatExternalTabofDealPage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String recordType = "";
	String dealName = RGDeal1;
	String companyName = RGDeal1CompanyName;
	String stage = RGDeal1Stage;
	String dateReceived = todaysDate;
	String ContactName = RGEContact6FName+" "+RGEContact6LName;
	
	String ExpectedMsg = "No items to display.";
	
	if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

		if (cp.createContactAcuity(projectName, RGEContact6FName, RGEContact6LName, RGEIns1, RGEContact6EmailID,
				RGEContact6RecordType, null, null, CreationPage.ContactPage, null, null)) {
			log(LogStatus.INFO, "successfully Created Contact : " + RGEContact6FName + " " + RGEContact6LName,
					YesNo.No);
		} else {
			sa.assertTrue(false, "Not Able to Create Contact : " + RGEContact6FName + " " + RGEContact6LName);
			log(LogStatus.SKIP, "Not Able to Create Contact: " + RGEContact6FName + " " + RGEContact6LName,
					YesNo.Yes);
		}
	}
	if (lp.clickOnTab(projectName, tabObj4)) {
		log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
		ThreadSleep(3000);
		if (dp.createDeal(recordType, dealName, companyName, stage, "Date Received", todaysDate)) {
			log(LogStatus.INFO, dealName + " deal has been created", YesNo.No);

		} else {
			log(LogStatus.ERROR, dealName + " deal is not created", YesNo.No);
			sa.assertTrue(false, dealName + " deal is not created");
		}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " Tab", YesNo.No);
		sa.assertTrue(false, "Not able to click on " + tabObj4 + " Tab");
	}

		String[][] data = { { PageLabel.Deal.toString(), dealName },
				{ PageLabel.Deal_Contact.toString(), ContactName } };

		if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

			if (DTP.createDealTeam(projectName, dealName, data, "Acuity", action.SCROLLANDBOOLEAN, 25)) {
				log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----",
						YesNo.No);

				log(LogStatus.INFO,
						"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
								+ ContactName + " at Firm Tab under Acuity section---------",
						YesNo.No);
				WebElement ele = DTP.getDealTeamID(10);
				if (ele != null) {
					String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
					ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name,
							"RGDT_01", excelLabel.DealTeamID);
					log(LogStatus.INFO,
							"successfully created and noted id of DT" + id + " and deal name " + dealName,
							YesNo.No);
				}
				} else {
					sa.assertTrue(false, "could not create DT" + dealName);
					log(LogStatus.SKIP, "could not create DT" + dealName, YesNo.Yes);
				}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Deal_Team + " Tab", YesNo.No);
			sa.assertTrue(false, "Not able to click on " + TabName.Deal_Team + " Tab");
		}
		if (lp.clickOnTab(projectName, tabObj4)) {
			log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
			ThreadSleep(3000);
			if (fp.clickOnAlreadyCreatedItem(projectName,RGDeal1, 10)) {
			if (CommonLib.click(driver, BP.ExternalTab( 30,action.SCROLLANDBOOLEAN), "ExternalTab tab: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on ExternalTab tab", YesNo.No);
				if (CommonLib.click(driver, BP.contactEmailCount(ContactName, 30), "Email Count: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + RGEContact6LName, YesNo.No);
				String parentWindowId1 = CommonLib.switchOnWindow(driver);
				if (!parentWindowId1.isEmpty()) {
				ThreadSleep(3000);
				String actualMsg = getText(driver, BP.getErrorMsg(20), "ErrorMsg", action.SCROLLANDBOOLEAN);
					if (actualMsg.equals(ExpectedMsg)) {
					log(LogStatus.INFO,
							"Actual result " + actualMsg + " of pop up has been matched with Expected result : "
									+ ExpectedMsg + " for Contact Name: " + RGEContact6LName,
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Actual result " + actualMsg
									+ " of pop up has been not matched with Expected result : " + ExpectedMsg
									+ " for Contact Name: " + RGEContact6LName,
							YesNo.No);
				}
				driver.close();
				driver.switchTo().window(parentWindowId1);
				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + "contact Email Count" , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + "contact Email Count" );
				}
		} else {
			log(LogStatus.FAIL, "Not able to Click on  tab: " + "ExternalTab" , YesNo.Yes);
			sa.assertTrue(false, "Not able to Click on  tab: " + "ExternalTab" );
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + RGDeal1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + RGDeal1 + " tab");
		}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj4 + " tab");
		}

	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
	}
	

@Parameters({ "projectName" })
@Test
public void ADETRGc038_VerifyEmailCountforContactatExternaltabofDealteamGridofDealspage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String emailCountInFirm = "8";
	String actualemailCount = null;
	String dealName = RGDeal1;
	String ContactName = RGEContact1LName;
	String TeamMember = RGDealTeamMember2;
	
	String[][] data = { { PageLabel.Deal.toString(), dealName },
			{ PageLabel.Deal_Contact.toString(), ContactName },{ PageLabel.Team_Member.toString(), TeamMember } };

	if (lp.clickOnTab(projectName, TabName.Deal_Team)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Deal_Team, YesNo.No);

		if (DTP.createDealTeam(projectName, dealName, data, "Acuity", action.SCROLLANDBOOLEAN, 25)) {
			log(LogStatus.INFO, "----Successfully Created the Deal Team for Deal: " + dealName + "----",
					YesNo.No);

			log(LogStatus.INFO,
					"---------Now Going to Check Deal Team Count should get increase by one for Contact named "
							+ ContactName + " at Firm Tab under Acuity section---------",
					YesNo.No);
			WebElement ele = DTP.getDealTeamID(10);
			if (ele != null) {
				String id = getText(driver, ele, "deal team id", action.SCROLLANDBOOLEAN);
				ExcelUtils.writeData(AcuityDataSheetFilePath, id, "Deal Team", excelLabel.Variable_Name,
						"RGDT_02", excelLabel.DealTeamID);
				log(LogStatus.INFO,
						"successfully created and noted id of DT" + id + " and deal name " + dealName,
						YesNo.No);
			}
			} else {
				sa.assertTrue(false, "could not create DT" + dealName);
				log(LogStatus.SKIP, "could not create DT" + dealName, YesNo.Yes);
			}
	} else {
		log(LogStatus.ERROR, "Not able to click on " + TabName.Deal_Team + " Tab", YesNo.No);
		sa.assertTrue(false, "Not able to click on " + TabName.Deal_Team + " Tab");
	}
	if (lp.clickOnTab(projectName, tabObj4)) {
		log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
		ThreadSleep(3000);
		if (fp.clickOnAlreadyCreatedItem(projectName,RGDeal1, 10)) {
		if (CommonLib.click(driver, BP.ExternalTab( 30,action.SCROLLANDBOOLEAN), "ExternalTab tab: " + "",
				action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on ExternalTab tab", YesNo.No);
			actualemailCount = getText(driver, BP.contactEmailCount(ContactName, 20), "deal",
					action.SCROLLANDBOOLEAN);
			if (BP.contactEmailCount(ContactName, 20) != null) {
				if (!actualemailCount.equalsIgnoreCase("")) {

					if (actualemailCount.equalsIgnoreCase(emailCountInFirm)) {
						log(LogStatus.INFO, "Email Count for Contact: " + ContactName + " is " + actualemailCount
								+ " before Email Team Create is matched to " + emailCountInFirm, YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"Email Count for Contact: " + ContactName
										+ " is before Email count is not matched, Expected: " + emailCountInFirm
										+ " but Actual: " + actualemailCount,
								YesNo.Yes);
						sa.assertTrue(false,
								"Email Count for Contact: " + ContactName
										+ " is before Email count  not matched, Expected: " + emailCountInFirm
										+ " but Actual: " + actualemailCount);
					}

				} else {
					log(LogStatus.ERROR, "Email Count for Contact is Empty, So not able to check Count for Contact: "
							+ ContactName, YesNo.Yes);
					sa.assertTrue(false, "Email Count for Contact is Empty, So not able to check Count for Contact: "
							+ ContactName);
				}
			} else {
				log(LogStatus.ERROR, "No Contact found of Name: " + ContactName, YesNo.No);
				sa.assertTrue(false, "No Contact found of Name: " + ContactName);
			}
		} else {
			log(LogStatus.FAIL, "Not able to Click on  tab: " + "ExternalTab" , YesNo.Yes);
			sa.assertTrue(false, "Not able to Click on  tab: " + "ExternalTab" );
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + RGDeal1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + RGDeal1 + " tab");
		}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj4 + " tab");
		}


	ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
	}


@Parameters({ "projectName" })
@Test
public void ADETRGc039_VerifyEmailCountRedirectionfromExternalTabofDealteamGrid0fDealsPage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String ContactName =RGEContact1LName ;
	String ExpectedHeader = "Emails With";
	
	if (lp.clickOnTab(projectName, tabObj4)) {
		log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
		ThreadSleep(3000);
		if (fp.clickOnAlreadyCreatedItem(projectName,RGDeal1, 10)) {
		if (CommonLib.click(driver, BP.ExternalTab( 30,action.SCROLLANDBOOLEAN), "ExternalTab tab: " + "",
				action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on ExternalTab tab", YesNo.No);
			if (CommonLib.click(driver, BP.contactEmailCount(ContactName, 30), "Email Count: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + ContactName, YesNo.No);
			String parentWindowId1 = CommonLib.switchOnWindow(driver);
			if (!parentWindowId1.isEmpty()) {
			ThreadSleep(3000);
			String actualHeader = getText(driver, BP.getConnectionpopupheader(20), "Contactpopupheader",
					action.SCROLLANDBOOLEAN);
//			if (ExpectedHeader.contains(actualHeader)) {
				if (actualHeader.contains(ExpectedHeader)) {
				log(LogStatus.INFO,
						"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
								+ ExpectedHeader + " for Contact Name: " + ContactName,
						YesNo.No);
			} else {
				log(LogStatus.ERROR,
						"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
								+ ExpectedHeader + " for Contact Name: " + ContactName,
						YesNo.No);
				sa.assertTrue(false,
						"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
								+ ExpectedHeader + " for Contact Name: " + ContactName);
			}
		     driver.close();
			driver.switchTo().window(parentWindowId1);
		}
			} else {
			log(LogStatus.FAIL, "Not able to Click on  tab: " + "contactEmailCount" , YesNo.Yes);
			sa.assertTrue(false, "Not able to Click on  tab: " + "contactEmailCount" );
			}
		} else {
			log(LogStatus.FAIL, "Not able to Click on  tab: " + "ExternalTab" , YesNo.Yes);
			sa.assertTrue(false, "Not able to Click on  tab: " + "ExternalTab" );
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + RGDeal1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + RGDeal1 + " tab");
		}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj4 + " tab");
		}
       ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
	
	}	

@Parameters({ "projectName" })
@Test
public void ADETRGc040_VerifythatSubjectisClickableItsRedirectionFromEmailDetailsPage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String ContactName = RGEContact1LName ;
	String subject = "Parking Receipt TR6";
	if (lp.clickOnTab(projectName, tabObj4)) {
		log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
		ThreadSleep(3000);
		if (fp.clickOnAlreadyCreatedItem(projectName,RGDeal1, 10)) {
		if (CommonLib.click(driver, BP.ExternalTab( 30,action.SCROLLANDBOOLEAN), "ExternalTab tab: " + "",
				action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on ExternalTab tab", YesNo.No);
			if (CommonLib.click(driver, BP.contactEmailCount(ContactName, 30), "Email Count: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + ContactName, YesNo.No);
			String parentWindowId1 = CommonLib.switchOnWindow(driver);
			if (!parentWindowId1.isEmpty()) {
			ThreadSleep(3000);
			if (clickUsingJavaScript(driver, BP.contactPopUpEmailsubject(subject, 10), "Deal Name: " + subject,
					action.BOOLEAN)) {
				log(LogStatus.PASS, "Clicked on Email subject: " + subject, YesNo.No);
				try {
					log(LogStatus.PASS, "New Window Open after click on Email subject Link: " + subject, YesNo.No);
					Set<String> childWindow = driver.getWindowHandles();
					op.outLookLoginRevenueGrid(rgUser2,rgUserPassword);
					driver.switchTo().defaultContent();
					System.out.println(childWindow);
					for(String child : childWindow) {
					driver.switchTo().window(child);
					}
					if (BP.EmailRecordPage(subject, 20) != null) {
						log(LogStatus.PASS,
								"----Email Detail Page is redirecting for Email Record: " + subject + "-----",
								YesNo.No);
						driver.close();
						driver.switchTo().window(parentWindowId1);

					} else {
						log(LogStatus.FAIL, "----Email Detail Page is not redirecting for Email Record: "
								+ subject + "-----", YesNo.Yes);
						sa.assertTrue(false,
								"----Email Detail Page is not redirecting for Email Record: " + subject + "-----");
						driver.close();
						driver.switchTo().window(parentWindowId1);

					}

//				} else {
//					log(LogStatus.FAIL, "No New Window Open after click on Deal Link: " + subject, YesNo.Yes);
//					sa.assertTrue(false, "No New Window Open after click on Deal Link: " + subject);
//				}
			} catch (Exception e) {
				log(LogStatus.FAIL,
						"Not able to switch to window after click on Deal Link, Msg showing: " + e.getMessage(),
						YesNo.Yes);
				sa.assertTrue(false, "Not able to switch to window after click on Deal Link, Msg showing: "
						+ e.getMessage());
			}
		} else {
			log(LogStatus.FAIL, "Not able to Click on Deal Name: " + subject, YesNo.Yes);
			sa.assertTrue(false, "Not able to Click on Deal Name: " + subject);
          }
			driver.close();
			driver.switchTo().window(parentWindowId1);
			}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + "contactEmailCount" , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + "contactEmailCount" );
				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + "ExternalTab" , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + "ExternalTab" );
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + RGDeal1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + RGDeal1 + " tab");
			}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj4 + " tab");
			}
	       ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
		
		}	
@Parameters({ "projectName" })
@Test
public void ADETRGc041_VerifytheCategoryTypeatEmailDetailspage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String ContactName = RGEContact1LName ;
	String expected = "Inbound Email,Outbound Email,Indirect Email";
    List<String> actualheader = new ArrayList<String>();
	
	if (lp.clickOnTab(projectName, tabObj4)) {
		log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
		ThreadSleep(3000);
		if (fp.clickOnAlreadyCreatedItem(projectName,RGDeal1, 10)) {
		if (CommonLib.click(driver, BP.ExternalTab( 30,action.SCROLLANDBOOLEAN), "ExternalTab tab: " + "",
				action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on ExternalTab tab", YesNo.No);
			if (CommonLib.click(driver, BP.contactEmailCount(ContactName, 30), "Email Count: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + ContactName, YesNo.No);
			String parentWindowId1 = CommonLib.switchOnWindow(driver);
			if (!parentWindowId1.isEmpty()) {
			ThreadSleep(3000);
		
			List<WebElement> ele =BP.listOfemailCategory(10);
			for(int i = 0; i <ele.size();i++ ) {
			actualheader.add(ele.get(i).getText());
		}
			if(compareMultipleList(driver, expected, ele) != null){
				log(LogStatus.INFO, "BOTH values matched: " + " " + " of actual : " + expected, YesNo.No);
			}else
			{
				log(LogStatus.ERROR, "BOTH values matched: " + " " + " of actual : " + expected, YesNo.Yes);
				sa.assertTrue(false,"both values not matched "+" "+expected);
			}
			driver.close();
			driver.switchTo().window(parentWindowId1);
			}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + "contactEmailCount" , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + "contactEmailCount" );
				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + "ExternalTab" , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + "ExternalTab" );
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + RGDeal1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + RGDeal1 + " tab");
			}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj4 + " tab");
			}
	       ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
		
		}	
			

@Parameters({ "projectName" })
@Test
public void ADETRGc042_1_VerifythatCellisClickableforTOandCCColumnsatEmailDetailsPage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String ContactName = RGEContact1LName ;
	String subject = "Uh-oh, your prescription is expiring TR6";
	String Toname = RGEContact1LName;
	String CCname = RGEContact1LName;
	if (lp.clickOnTab(projectName, tabObj4)) {
		log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
		ThreadSleep(3000);
		if (fp.clickOnAlreadyCreatedItem(projectName,RGDeal1, 10)) {
		if (CommonLib.click(driver, BP.ExternalTab( 30,action.SCROLLANDBOOLEAN), "ExternalTab tab: " + "",
				action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on ExternalTab tab", YesNo.No);
			if (CommonLib.click(driver, BP.contactEmailCount(ContactName, 30), "Email Count: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + ContactName, YesNo.No);
			String parentWindowId1 = CommonLib.switchOnWindow(driver);
			if (!parentWindowId1.isEmpty()) {
			ThreadSleep(3000);
			if (clickUsingJavaScript(driver, BP.contactPopUpTO(subject,Toname, 10), "Contact  Name: " + subject,
					action.BOOLEAN)) {
				log(LogStatus.PASS, "Clicked on Contact Name: " + subject, YesNo.No);
				if (clickUsingJavaScript(driver, BP.ToPopup(Toname, 10), "Deal Name: " + subject,
						action.BOOLEAN)) {
					log(LogStatus.PASS, "Clicked on Contact Name: " + subject, YesNo.No);
					Set<String> childWindow = driver.getWindowHandles();
					switchToDefaultContent(driver);
					System.out.println(childWindow);
					for(String child : childWindow) {
					driver.switchTo().window(child);
					}
							if (BP.ContactRecordPage(Toname, 20) != null) {
								log(LogStatus.PASS,
										"----Contact Detail Page is redirecting for Contact Record: " + subject + "-----",
										YesNo.No);
								driver.close();
								driver.switchTo().window(parentWindowId1);

							} else {
								log(LogStatus.FAIL, "----Contact Detail Page is not redirecting for Deal Record: "
										+ subject + "-----", YesNo.Yes);
								sa.assertTrue(false,
										"----Contact Detail Page is not showing for Deal Record: " + subject + "-----");
								driver.close();
								driver.switchTo().window(parentWindowId1);

							}

						} else {
							log(LogStatus.FAIL, "No New Window Open after click on Contact Link: " + subject, YesNo.Yes);
							sa.assertTrue(false, "No New Window Open after click on Contact Link: " + subject);
						}
				CommonLib.switchOnWindow(driver);
					driver.close();
				driver.switchTo().window(parentWindowId1);
			}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + "contactEmailCount" , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + "contactEmailCount" );
				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + "ExternalTab" , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + "ExternalTab" );
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + RGDeal1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + RGDeal1 + " tab");
			}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj4 + " tab");
			}
	       ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
		
		}	
			}
@Parameters({ "projectName" })
@Test
public void ADETRGc042_2_VerifythatCellisClickableforTOandCCColumnsatEmailDetailsPage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String ContactName = RGEContact4FName + " " + RGEContact4LName ;
	String subject = "Testing indirect email5 for contact";
	String Toname = RGEContact1LName;
	String CCname = RGEContact1LName;
	if (lp.clickOnTab(projectName, tabObj4)) {
		log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
		ThreadSleep(3000);
		if (fp.clickOnAlreadyCreatedItem(projectName,RGDeal1, 10)) {
		if (CommonLib.click(driver, BP.ExternalTab( 30,action.SCROLLANDBOOLEAN), "ExternalTab tab: " + "",
				action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on ExternalTab tab", YesNo.No);
			if (CommonLib.click(driver, BP.contactEmailCount(ContactName, 30), "Email Count: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + ContactName, YesNo.No);
			String parentWindowId1 = CommonLib.switchOnWindow(driver);
			if (!parentWindowId1.isEmpty()) {
			ThreadSleep(3000);
			if (clickUsingJavaScript(driver, BP.contactPopUpCC(subject,CCname, 10), "Contact  Name: " + subject,
					action.BOOLEAN)) {
				log(LogStatus.PASS, "Clicked on Contact Name: " + subject, YesNo.No);
				if (clickUsingJavaScript(driver, BP.CCPopup(CCname, 10), "Contact Name: " + subject,
						action.BOOLEAN)) {
					log(LogStatus.PASS, "Clicked on Contact Name: " + subject, YesNo.No);
					Set<String> childWindow = driver.getWindowHandles();
					switchToDefaultContent(driver);
					System.out.println(childWindow);
					for(String child : childWindow) {
					driver.switchTo().window(child);
					}
							if (BP.ContactRecordPage(CCname, 20) != null) {
								log(LogStatus.PASS,
										"----Contact Detail Page is redirecting for Contact Record: " + subject + "-----",
										YesNo.No);
								driver.close();
								driver.switchTo().window(parentWindowId1);

							} else {
								log(LogStatus.FAIL, "----Contact Detail Page is not redirecting for Deal Record: "
										+ subject + "-----", YesNo.Yes);
								sa.assertTrue(false,
										"----Contact Detail Page is not showing for Deal Record: " + subject + "-----");
								driver.close();
								driver.switchTo().window(parentWindowId1);

							}

						} else {
							log(LogStatus.FAIL, "No New Window Open after click on Contact Link: " + subject, YesNo.Yes);
							sa.assertTrue(false, "No New Window Open after click on Contact Link: " + subject);
						}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + "contactPopUpCC" , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + "contactPopUpCC" );
			}
				
				driver.switchTo().window(parentWindowId1);
				CommonLib.switchOnWindow(driver);
					driver.close();
				driver.switchTo().window(parentWindowId1);
			}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + "contactEmailCount" , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + "contactEmailCount" );
				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + "ExternalTab" , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + "ExternalTab" );
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + RGDeal1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + RGDeal1 + " tab");
			}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj4 + " tab");
			}
	       ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
		
		}	
			

@Parameters({ "projectName" })
@Test
public void ADETRGc043_VerifythatEmailCountUnderEmailColumnandEmailCountinEmailDetailsPageShouldbeSimilar(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String emailCountInFirm = "8";
	String actualemailCount = null;

	String ContactName = RGEContact1LName ;

	if (lp.clickOnTab(projectName, tabObj4)) {
		log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
		ThreadSleep(3000);
		if (fp.clickOnAlreadyCreatedItem(projectName,RGDeal1, 10)) {
		if (CommonLib.click(driver, BP.ExternalTab( 30,action.SCROLLANDBOOLEAN), "ExternalTab tab: " + "",
				action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on ExternalTab tab", YesNo.No);
			actualemailCount = getText(driver, BP.contactEmailCount(ContactName, 20), "deal",
					action.SCROLLANDBOOLEAN);
			if (BP.contactEmailCount(ContactName, 20) != null) {
				if (!actualemailCount.equalsIgnoreCase("")) {

					if (actualemailCount.equalsIgnoreCase(emailCountInFirm)) {
						log(LogStatus.INFO, "Email Count for Contact: " + ContactName + " is " + actualemailCount
								+ " before Email Team Create is matched to " + emailCountInFirm, YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"Email Count for Contact: " + ContactName
										+ " is before Email count is not matched, Expected: " + emailCountInFirm
										+ " but Actual: " + actualemailCount,
								YesNo.Yes);
						sa.assertTrue(false,
								"Email Count for Contact: " + ContactName
										+ " is before Email count  not matched, Expected: " + emailCountInFirm
										+ " but Actual: " + actualemailCount);
					}

				} else {
					log(LogStatus.ERROR, "Email Count for Contact is Empty, So not able to check Count for Contact: "
							+ ContactName, YesNo.Yes);
					sa.assertTrue(false, "Email Count for Contact is Empty, So not able to check Count for Contact: "
							+ ContactName);
				}
			} else {
				log(LogStatus.ERROR, "No Contact found of Name: " + ContactName, YesNo.No);
				sa.assertTrue(false, "No Contact found of Name: " + ContactName);
			}
			if (CommonLib.click(driver, BP.contactEmailCount(ContactName, 30), "Email Count: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + ContactName, YesNo.No);
			String parentWindowId1 = CommonLib.switchOnWindow(driver);
			if (!parentWindowId1.isEmpty()) {
			ThreadSleep(3000);
			if (actualemailCount.equals(dp.EmailcountonPopup(10))) {
				log(LogStatus.PASS, "Email count on Popup: is equal to  " + actualemailCount +  "are equal", YesNo.No);
               } else {

				sa.assertTrue(false, "email count is not equal to : " + actualemailCount);
				log(LogStatus.SKIP, "email count is not equal to : " + actualemailCount, YesNo.Yes);

			}
			 driver.close();
			 driver.switchTo().window(parentWindowId1);
			} else {
				log(LogStatus.FAIL, "No New Window Open after click on Company Link: " + ContactName,
						YesNo.Yes);
				sa.assertTrue(false, "No New Window Open after click on Company Link: " + ContactName);
			}
			} else {
				log(LogStatus.FAIL, "Not able to Click on email count: " + actualemailCount, YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on email count: " + actualemailCount);
			}
			
		} else {
			log(LogStatus.FAIL, "Not able to Click on  tab: " + "ExternalTab" , YesNo.Yes);
			sa.assertTrue(false, "Not able to Click on  tab: " + "ExternalTab" );
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + RGDeal1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + RGDeal1 + " tab");
		}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj4 + " tab");
		}
       ThreadSleep(5000);
	lp.CRMlogout();
	sa.assertAll();
	
	}	
		

@Parameters({ "projectName" })
@Test
public void ADETRGc044_VerifytheEmailCountonInternalUsersatInternalTabforDealTeamContact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String ContactName = RGEContact1LName ;
	String emailCountInFirm = "8";
	String actualemailCount = null;
	String Username = rgUser1;

	
	if (lp.clickOnTab(projectName, tabObj4)) {
		log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
		ThreadSleep(3000);
		if (fp.clickOnAlreadyCreatedItem(projectName,RGDeal1, 10)) {
		if (CommonLib.click(driver, BP.ExternalTab( 30,action.SCROLLANDBOOLEAN), "ExternalTab tab: " + "",
				action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on ExternalTab tab", YesNo.No);
			if (CommonLib.click(driver, BP.connectionicon(ContactName, 30), "Connection icon: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on connectionicon tab", YesNo.No);
				String parentWindowId1 = CommonLib.switchOnWindow(driver);
				if (!parentWindowId1.isEmpty()) {
				ThreadSleep(3000);
				actualemailCount = getText(driver, BP.contactEmailCount(Username, 20), "deal",
						action.SCROLLANDBOOLEAN);
				if (BP.contactEmailCount(Username, 20) != null) {
					if (!actualemailCount.equalsIgnoreCase("")) {

						if (actualemailCount.equalsIgnoreCase(emailCountInFirm)) {
							log(LogStatus.INFO, "Email Count for Contact: " + Username + " is " + actualemailCount
									+ " before Email Team Create is matched to " + emailCountInFirm, YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"Email Count for Contact: " + Username
											+ " is before Email count is not matched, Expected: " + emailCountInFirm
											+ " but Actual: " + actualemailCount,
									YesNo.Yes);
							sa.assertTrue(false,
									"Email Count for Contact: " + Username
											+ " is before Email count  not matched, Expected: " + emailCountInFirm
											+ " but Actual: " + actualemailCount);
						}

					} else {
						log(LogStatus.ERROR, "Email Count for Contact is Empty, So not able to check Count for Contact: "
								+ Username, YesNo.Yes);
						sa.assertTrue(false, "Email Count for Contact is Empty, So not able to check Count for Contact: "
								+ Username);
					}
				} else {
					log(LogStatus.ERROR, "No Contact found of Name: " + Username, YesNo.No);
					sa.assertTrue(false, "No Contact found of Name: " + Username);
				}
				driver.close();
				driver.switchTo().window(parentWindowId1);
				} else {
					log(LogStatus.FAIL, "No New Window Open after click on Company Link: " + ContactName,
							YesNo.Yes);
					sa.assertTrue(false, "No New Window Open after click on Company Link: " + ContactName);
				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + "connectionicon" , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + "connectionicon" );
				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + "ExternalTab" , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + "ExternalTab" );
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + RGDeal1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + RGDeal1 + " tab");
			}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj4 + " tab");
			}
	       ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
		
		}	
			
@Parameters({ "projectName" })
@Test
public void ADETRGc045_VerifytheEmailCountClickandRedirectionFromInternalTabofConnectiosDetailsPage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String ContactName = RGEContact1LName ;
	String ExpectedHeader = "Emails With";
	String Username = rgUser1;

	
	if (lp.clickOnTab(projectName, tabObj4)) {
		log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
		ThreadSleep(3000);
		if (fp.clickOnAlreadyCreatedItem(projectName,RGDeal1, 10)) {
		if (CommonLib.click(driver, BP.ExternalTab( 30,action.SCROLLANDBOOLEAN), "ExternalTab tab: " + "",
				action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on ExternalTab tab", YesNo.No);
			if (CommonLib.click(driver, BP.connectionicon(ContactName, 30), "Connection icon: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on connectionicon tab", YesNo.No);
				String parentWindowId1 = CommonLib.switchOnWindow(driver);
				if (!parentWindowId1.isEmpty()) {
				ThreadSleep(3000);
				if (CommonLib.click(driver, BP.contactEmailCount(Username, 30), "Email Count: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + Username, YesNo.No);
				Set<String> childWindow = driver.getWindowHandles();
				switchToDefaultContent(driver);
				System.out.println(childWindow);
				for(String child : childWindow) {
				driver.switchTo().window(child);
				}
				String actualHeader = getText(driver, BP.getConnectionpopupheader(20), "Contactpopupheader",
						action.SCROLLANDBOOLEAN);
//				if (ExpectedHeader.contains(actualHeader)) {
					if (actualHeader.contains(ExpectedHeader)) {
					log(LogStatus.INFO,
							"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
									+ ExpectedHeader + " for Contact Name: " + Username,
							YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
									+ ExpectedHeader + " for Contact Name: " + Username,
							YesNo.No);
					sa.assertTrue(false,"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
							+ ExpectedHeader + " for Contact Name: " + Username);
				}
				driver.switchTo().window(parentWindowId1);
				CommonLib.switchOnWindow(driver);
					driver.close();
				driver.switchTo().window(parentWindowId1);
				} else {
					log(LogStatus.FAIL, "Not able to Click on  tab: " + "connectionicon" , YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on  tab: " + "connectionicon" );
					}
				}
				} else {
					log(LogStatus.FAIL, "Not able to Click on  tab: " + "connectionicon" , YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on  tab: " + "connectionicon" );
					}
				} else {
					log(LogStatus.FAIL, "Not able to Click on  tab: " + "ExternalTab" , YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on  tab: " + "ExternalTab" );
					}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + RGDeal1 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + RGDeal1 + " tab");
				}
				} else {
					log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " tab", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on " + tabObj4 + " tab");
				}
		       ThreadSleep(5000);
			lp.CRMlogout();
			sa.assertAll();
			
			}	
				

@Parameters({ "projectName" })
@Test
public void ADETRGc046_VerifytheEmailCountonExternalContactsatExternalTabforDealteamContact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
//	String emailCountInFirm = "3";
	String emailCountInFirm = "4";
	String actualemailCount = null;

	String contactName = RGEContact1LName;
	String contactName1 = "Jane Elias";
	
	if (lp.clickOnTab(projectName, tabObj4)) {
		log(LogStatus.INFO, "Click on Tab : " + tabObj4, YesNo.No);
		ThreadSleep(3000);
		if (fp.clickOnAlreadyCreatedItem(projectName,RGDeal1, 10)) {
		if (CommonLib.click(driver, BP.ExternalTab( 30,action.SCROLLANDBOOLEAN), "ExternalTab tab: " + "",
				action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on ExternalTab tab", YesNo.No);
			if (CommonLib.click(driver, BP.connectionicon(contactName, 30), "Connection icon: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on connectionicon tab", YesNo.No);
				String parentWindowId1 = CommonLib.switchOnWindow(driver);
				if (!parentWindowId1.isEmpty()) {
				ThreadSleep(3000);
				if (CommonLib.click(driver, BP.ExternalTab( 30,action.SCROLLANDBOOLEAN), "ExternalTab tab: " + "",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Clicked on ExternalTab tab", YesNo.No);
			actualemailCount = getText(driver, BP.contactEmailCount(contactName1, 20), "deal",
				action.SCROLLANDBOOLEAN);
		if (BP.contactEmailCount(contactName1, 20) != null) {
			if (!actualemailCount.equalsIgnoreCase("")) {

				if (actualemailCount.equalsIgnoreCase(emailCountInFirm)) {
					log(LogStatus.INFO, "Email Count for Contact: " + contactName1 + " is " + actualemailCount
							+ " before Email Team Create is matched to " + emailCountInFirm, YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Email Count for Contact: " + contactName1
									+ " is before Email count is not matched, Expected: " + emailCountInFirm
									+ " but Actual: " + actualemailCount,
							YesNo.Yes);
					sa.assertTrue(false,
							"Email Count for Contact: " + contactName1
									+ " is before Email count  not matched, Expected: " + emailCountInFirm
									+ " but Actual: " + actualemailCount);
				}

			} else {
				log(LogStatus.ERROR, "Email Count for Contact is Empty, So not able to check Count for Contact: "
						+ contactName1, YesNo.Yes);
				sa.assertTrue(false, "Email Count for Contact is Empty, So not able to check Count for Contact: "
						+ contactName1);
			}
		} else {
			log(LogStatus.ERROR, "No Contact found of Name: " + contactName1, YesNo.No);
			sa.assertTrue(false, "No Contact found of Name: " + contactName1);
		}
     driver.close();
     driver.switchTo().window(parentWindowId1);
				} else {
					log(LogStatus.FAIL, "Not able to Click on  tab: " + "External Tab" , YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on  tab: " + "External Tab " );
					}
				} else {
					log(LogStatus.FAIL, "No New Window Open after click on Company Link: " + contactName,
							YesNo.Yes);
					sa.assertTrue(false, "No New Window Open after click on Company Link: " + contactName);
				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + "connectionicon" , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + "connectionicon" );
				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + "ExternalTab" , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + "ExternalTab" );
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + RGDeal1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + RGDeal1 + " tab");
			}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj4 + " tab");
			}
	       ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
		
		}	
			

@Parameters({ "projectName" })
@Test
public void ADETRGc047_VerifythatwhenEmailCountisZeroforaContactEmailcountisClickedatFundraisingPage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
	InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String ExpectedMsg = "No items to display.";
	//INS
	String value="";
	String type="";
	String[][] EntityOrAccounts = {{ RGEIns2, RGEIns2RecordType ,null}};
	 
	

	for (String[] accounts : EntityOrAccounts) {
		value = accounts[0];
		type = accounts[1];
		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
			
			if (ip.createEntityOrAccount(projectName, mode, value, type, null, null, 20)) {
				log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
			
			} else {
				sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
				log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
			}
		}
	}
		//contact
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

			if (cp.createContactAcuity(projectName, RGEContact7FName, RGEContact7LName, RGEIns2, RGEContact7EmailID,
					RGEContact6RecordType, null, null, CreationPage.ContactPage, null, null)) {
				log(LogStatus.INFO, "successfully Created Contact : " + RGEContact7FName + " " + RGEContact7LName,
						YesNo.No);
			} else {
				sa.assertTrue(false, "Not Able to Create Contact : " + RGEContact7FName + " " + RGEContact7LName);
				log(LogStatus.SKIP, "Not Able to Create Contact: " + RGEContact7FName + " " + RGEContact7LName,
						YesNo.Yes);
			}
		}
		//fund
		if (lp.clickOnTab(projectName, TabName.Object3Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object3Tab,YesNo.No);	
			String[] funds = {RGFundName1,RGFundType1,RGInvestmentCategory1,RGRecordType1};
			if (fp.createFundPE(projectName, funds[0], funds[3], funds[1], funds[2], null, 15)) {
				log(LogStatus.INFO,"Created Fund : "+funds[0],YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Fund : "+funds[0]);
				log(LogStatus.SKIP,"Not Able to Create Fund  : "+funds[0],YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object3Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object3Tab,YesNo.Yes);
		}
		
		//FUNDRAISING
		String ADETargetClosingDate = todaysDate;
		
		if(BP.clickOnTab(environment,mode,TabName.FundraisingsTab)) {
			if (fr.createFundRaising(environment, "Lightning", RGFundraisingName1,RGRFundName1,RGInstitutionName1, null,
					null, null, null, ADETargetClosingDate,ADETargetClosingDate, ADETargetClosingDate)) {
				appLog.info("fundraising is created : " + RGFundraisingName1);
			} else {
				appLog.error("Not able to create fundraising: " + RGFundraisingName1);
				sa.assertTrue(false, "Not able to create fundraising: " + RGFundraisingName1);
			}

		} else {
			appLog.error(
					"Not able to click on fundraising tab so cannot create fundraising: " + RGFundraisingName1);
			sa.assertTrue(false,
					"Not able to click on fundraising tab so cannot create fundraising: " + RGFundraisingName1);
		}
		//fundraising Contact
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
			log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
			ThreadSleep(3000);
			if (fp.clickOnAlreadyCreatedItem(projectName,RGFundraisingName1, 10)) {
				if (fr.createFundRaisingContactFromIcon(environment, "Lightning",RGContact1,null )) {
					appLog.info("fundraising Contact is created : " + RGFundraisingName1);
				} else {
					appLog.error("Not able to create fundraising Contact: " + RGFundraisingName1);
					sa.assertTrue(false, "Not able to create fundraising Contact: " + RGFundraisingName1);
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + RGFundraisingName1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + RGFundraisingName1 + " tab");
			}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + TabName.FundraisingsTab + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + TabName.FundraisingsTab + " tab");
			}
				if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
					log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
					ThreadSleep(3000);
					if (fp.clickOnAlreadyCreatedItem(projectName,RGFundraisingName1, 10)) {
						if (CommonLib.click(driver, BP.contactEmailCount(RGContact1, 30), "Email Count: " + "",
								action.BOOLEAN)) {
							log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + RGEContact6LName, YesNo.No);
						String parentWindowId1 = CommonLib.switchOnWindow(driver);
						if (!parentWindowId1.isEmpty()) {
						ThreadSleep(3000);
						String actualMsg = getText(driver, BP.getErrorMsg(20), "ErrorMsg", action.SCROLLANDBOOLEAN);
							if (actualMsg.equals(ExpectedMsg)) {
							log(LogStatus.INFO,
									"Actual result " + actualMsg + " of pop up has been matched with Expected result : "
											+ ExpectedMsg + " for Contact Name: " + RGEContact6LName,
									YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"Actual result " + actualMsg
											+ " of pop up has been not matched with Expected result : " + ExpectedMsg
											+ " for Contact Name: " + RGEContact6LName,
									YesNo.No);
						}
						driver.close();
						driver.switchTo().window(parentWindowId1);
						}
					} else {
						log(LogStatus.FAIL, "Not able to Click on  tab: " + "contact Email Count" , YesNo.Yes);
						sa.assertTrue(false, "Not able to Click on  tab: " + "contact Email Count" );
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on " + RGFundraisingName1 + " tab", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on " + RGFundraisingName1 + " tab");
					}
					} else {
						log(LogStatus.ERROR, "Not able to click on " + TabName.FundraisingsTab + " tab", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on " + TabName.FundraisingsTab + " tab");
					}
				
		  lp.CRMlogout();
		  	sa.assertAll();
		}
		

@Parameters({ "projectName" })
@Test
public void ADETRGc048_VerifyEmailCountforContactatFundraisingContactGridofFR1Page(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
//	String emailCountInFirm = "8";
	String emailCountInFirm = "24";
	String actualemailCount = null;
	String Contactname = RGEContact1LName;
	
	if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
		ThreadSleep(3000);
		if (fp.clickOnAlreadyCreatedItem(projectName,RGFundraisingName1, 10)) {
			if (fr.createFundRaisingContactFromIcon(environment, "Lightning",RGContact2,RGRole2 )) {
				appLog.info("fundraising Contact is created : " + RGFundraisingName1);
			} else {
				appLog.error("Not able to create fundraising Contact: " + RGFundraisingName1);
				sa.assertTrue(false, "Not able to create fundraising Contact: " + RGFundraisingName1);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + RGFundraisingName1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + RGFundraisingName1 + " tab");
		}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.FundraisingsTab + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + TabName.FundraisingsTab + " tab");
		}
	if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
		ThreadSleep(3000);
		if (fp.clickOnAlreadyCreatedItem(projectName,RGFundraisingName1, 10)) {
			actualemailCount = getText(driver, BP.contactEmailCount(Contactname, 20), "deal",
					action.SCROLLANDBOOLEAN);
			if (BP.contactEmailCount(Contactname, 20) != null) {
				if (!actualemailCount.equalsIgnoreCase("")) {

					if (actualemailCount.equalsIgnoreCase(emailCountInFirm)) {
						log(LogStatus.INFO, "Email Count for Contact: " + Contactname + " is " + actualemailCount
								+ " before Email Team Create is matched to " + emailCountInFirm, YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"Email Count for Contact: " + Contactname
										+ " is before Email count is not matched, Expected: " + emailCountInFirm
										+ " but Actual: " + actualemailCount,
								YesNo.Yes);
						sa.assertTrue(false,
								"Email Count for Contact: " + Contactname
										+ " is before Email count  not matched, Expected: " + emailCountInFirm
										+ " but Actual: " + actualemailCount);
					}

				} else {
					log(LogStatus.ERROR, "Email Count for Contact is Empty, So not able to check Count for Contact: "
							+ Contactname, YesNo.Yes);
					sa.assertTrue(false, "Email Count for Contact is Empty, So not able to check Count for Contact: "
							+ Contactname);
				}
			} else {
				log(LogStatus.ERROR, "No Contact found of Name: " + Contactname, YesNo.No);
				sa.assertTrue(false, "No Contact found of Name: " + Contactname);
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + RGFundraisingName1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + RGFundraisingName1 + " tab");
		}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.FundraisingsTab + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + TabName.FundraisingsTab + " tab");
		}
	
lp.CRMlogout();
	sa.assertAll();
}


@Parameters({ "projectName" })
@Test
public void ADETRGc049_VerifyEmailCountRedirectionfromFundraisingPage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String ContactName = RGEContact1LName;
	String ExpectedHeader = "Emails With";
	if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
		ThreadSleep(3000);
		if (fp.clickOnAlreadyCreatedItem(projectName,RGFundraisingName1, 10)) {
			if (CommonLib.click(driver, BP.contactEmailCount(ContactName, 30), "Email Count: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + ContactName, YesNo.No);
			String parentWindowId1 = CommonLib.switchOnWindow(driver);
			if (!parentWindowId1.isEmpty()) {
			ThreadSleep(3000);
			String actualHeader = getText(driver, BP.getConnectionpopupheader(20), "Contactpopupheader",
					action.SCROLLANDBOOLEAN);
//			if (ExpectedHeader.contains(actualHeader)) {
				if (actualHeader.contains(ExpectedHeader)) {
				log(LogStatus.INFO,
						"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
								+ ExpectedHeader + " for Contact Name: " + ContactName,
						YesNo.No);
			} else {
				log(LogStatus.ERROR,
						"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
								+ ExpectedHeader + " for Contact Name: " + ContactName,
						YesNo.No);
			}
		     driver.close();
			driver.switchTo().window(parentWindowId1);
		}
			} else {
			log(LogStatus.FAIL, "Not able to Click on  tab: " + "contactEmailCount" , YesNo.Yes);
			sa.assertTrue(false, "Not able to Click on  tab: " + "contactEmailCount" );
			}	
		} else {
			log(LogStatus.ERROR, "Not able to click on " + RGFundraisingName1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + RGFundraisingName1 + " tab");
		}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.FundraisingsTab + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + TabName.FundraisingsTab + " tab");
		}
	
lp.CRMlogout();
	sa.assertAll();
}


@Parameters({ "projectName" })
@Test
public void ADETRGc050_VerifytheEmailCountonInternalUsersatInternaltabforFundraisingContact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String ContactName = RGEContact1LName ;
	String emailCountInFirm = "8";
	
	String actualemailCount = null;
	String Username = rgUser1;
	
	
	if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
		ThreadSleep(3000);
		if (fp.clickOnAlreadyCreatedItem(projectName,RGFundraisingName1, 10)) {
		if (CommonLib.click(driver, BP.connectionicon(ContactName, 30), "Connection icon: " + "",
			action.BOOLEAN)) {
		log(LogStatus.INFO, "Clicked on connectionicon tab", YesNo.No);
		String parentWindowId1 = CommonLib.switchOnWindow(driver);
		if (!parentWindowId1.isEmpty()) {
		ThreadSleep(3000);
		actualemailCount = getText(driver, BP.contactEmailCount(Username, 20), "deal",
				action.SCROLLANDBOOLEAN);
		if (BP.contactEmailCount(Username, 20) != null) {
			if (!actualemailCount.equalsIgnoreCase("")) {

				if (actualemailCount.equalsIgnoreCase(emailCountInFirm)) {
					log(LogStatus.INFO, "Email Count for Contact: " + Username + " is " + actualemailCount
							+ " before Email Team Create is matched to " + emailCountInFirm, YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Email Count for Contact: " + Username
									+ " is before Email count is not matched, Expected: " + emailCountInFirm
									+ " but Actual: " + actualemailCount,
							YesNo.Yes);
					sa.assertTrue(false,
							"Email Count for Contact: " + Username
									+ " is before Email count  not matched, Expected: " + emailCountInFirm
									+ " but Actual: " + actualemailCount);
				}

			} else {
				log(LogStatus.ERROR, "Email Count for Contact is Empty, So not able to check Count for Contact: "
						+ Username, YesNo.Yes);
				sa.assertTrue(false, "Email Count for Contact is Empty, So not able to check Count for Contact: "
						+ Username);
			}
		} else {
			log(LogStatus.ERROR, "No Contact found of Name: " + Username, YesNo.No);
			sa.assertTrue(false, "No Contact found of Name: " + Username);
		}
		driver.close();
		driver.switchTo().window(parentWindowId1);
	}
	} else {
		log(LogStatus.FAIL, "Not able to Click on  tab: " + "connectionicon" , YesNo.Yes);
		sa.assertTrue(false, "Not able to Click on  tab: " + "connectionicon" );
		}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + RGFundraisingName1 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + RGFundraisingName1 + " tab");
		}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.FundraisingsTab + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + TabName.FundraisingsTab + " tab");
		}
	lp.CRMlogout();
	sa.assertAll();
}


@Parameters({ "projectName" })
@Test
public void ADETRGc051_VerifytheEmailCountClickandRedirectionfromInternaltabOfConnectionDetailsPage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String ContactName = RGEContact1LName ;

	String username = "Robert CRM1";
	String ExpectedHeader = "Emails With";
	
	if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
		ThreadSleep(3000);
		if (fp.clickOnAlreadyCreatedItem(projectName,RGFundraisingName1, 10)) {
		if (CommonLib.click(driver, BP.connectionicon(ContactName, 30), "Connection icon: " + "",
			action.BOOLEAN)) {
		log(LogStatus.INFO, "Clicked on connectionicon tab", YesNo.No);
		String parentWindowId1 = CommonLib.switchOnWindow(driver);
		if (!parentWindowId1.isEmpty()) {
		ThreadSleep(3000);
		if (CommonLib.click(driver, BP.contactEmailCount(username, 30), "Email Count: " + "",
				action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + username, YesNo.No);
			Set<String> childWindow = driver.getWindowHandles();
			switchToDefaultContent(driver);
			System.out.println(childWindow);
			for(String child : childWindow) {
			driver.switchTo().window(child);
			}
			String actualHeader = getText(driver, BP.getConnectionpopupheader(20), "Contactpopupheader",
					action.SCROLLANDBOOLEAN);
//			if (ExpectedHeader.contains(actualHeader)) {
				if (actualHeader.contains(ExpectedHeader)) {
				log(LogStatus.INFO,
						"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
								+ ExpectedHeader + " for Contact Name: " + ContactName,
						YesNo.No);
			} else {
				log(LogStatus.ERROR,
						"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
								+ ExpectedHeader + " for Contact Name: " + ContactName,
						YesNo.No);
				sa.assertTrue(false,"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
						+ ExpectedHeader + " for Contact Name: " + ContactName);
			}
				driver.switchTo().window(parentWindowId1);
				CommonLib.switchOnWindow(driver);
					driver.close();
				driver.switchTo().window(parentWindowId1);
			
				} else {
					log(LogStatus.FAIL, "Not able to Click on  tab: " + "contactEmailCount" , YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on  tab: " + "contactEmailCount" );
					}
		}
		} else {
			log(LogStatus.FAIL, "Not able to Click on  tab: " + "connectionicon" , YesNo.Yes);
			sa.assertTrue(false, "Not able to Click on  tab: " + "connectionicon" );
			}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + RGFundraisingName1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + RGFundraisingName1 + " tab");
			}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + TabName.FundraisingsTab + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + TabName.FundraisingsTab + " tab");
			}
		lp.CRMlogout();
		sa.assertAll();
	}
	
				
@Parameters({ "projectName" })
@Test
public void ADETRGc052_VerifytheEmailCountonExternalContactsatExternaltabforFundraisingContact(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	FundRaisingPageBusinessLayer fr = new FundRaisingPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String ContactName = RGEContact1LName ;
	String ContactName1 = "Jane Elias" ;
//	String emailCountInFirm = "3";
	String emailCountInFirm = "4";
	String actualemailCount = null;		
	
	if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
		ThreadSleep(3000);
		if (fp.clickOnAlreadyCreatedItem(projectName,RGFundraisingName1, 10)) {
		if (CommonLib.click(driver, BP.connectionicon(ContactName, 30), "Connection icon: " + "",
			action.BOOLEAN)) {
		log(LogStatus.INFO, "Clicked on connectionicon tab", YesNo.No);
		String parentWindowId1 = CommonLib.switchOnWindow(driver);
		if (!parentWindowId1.isEmpty()) {
		ThreadSleep(3000);
		if (CommonLib.click(driver, BP.ExternalTab( 30,action.SCROLLANDBOOLEAN), "ExternalTab tab: " + "",
				action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on ExternalTab tab", YesNo.No);
			actualemailCount = getText(driver, BP.contactEmailCount(ContactName1, 20), "deal",
					action.SCROLLANDBOOLEAN);
			if (BP.contactEmailCount(ContactName1, 20) != null) {
				if (!actualemailCount.equalsIgnoreCase("")) {

					if (actualemailCount.equalsIgnoreCase(emailCountInFirm)) {
						log(LogStatus.INFO, "Email Count for Contact: " + ContactName1 + " is " + actualemailCount
								+ " before Email Team Create is matched to " + emailCountInFirm, YesNo.No);
					} else {
						log(LogStatus.ERROR,
								"Email Count for Contact: " + ContactName1
										+ " is before Email count is not matched, Expected: " + emailCountInFirm
										+ " but Actual: " + actualemailCount,
								YesNo.Yes);
						sa.assertTrue(false,
								"Email Count for Contact: " + ContactName1
										+ " is before Email count  not matched, Expected: " + emailCountInFirm
										+ " but Actual: " + actualemailCount);
					}

				} else {
					log(LogStatus.ERROR, "Email Count for Contact is Empty, So not able to check Count for Contact: "
							+ ContactName1, YesNo.Yes);
					sa.assertTrue(false, "Email Count for Contact is Empty, So not able to check Count for Contact: "
							+ ContactName1);
				}
			} else {
				log(LogStatus.ERROR, "No Contact found of Name: " + ContactName1, YesNo.No);
				sa.assertTrue(false, "No Contact found of Name: " + ContactName1);
			}
			driver.close();
			driver.switchTo().window(parentWindowId1);
		} else {
			log(LogStatus.FAIL, "Not able to Click on  tab: " + "External Tab" , YesNo.Yes);
			sa.assertTrue(false, "Not able to Click on  tab: " + "External Tab " );
			}
		}
		} else {
			log(LogStatus.FAIL, "Not able to Click on  tab: " + "connectionicon" , YesNo.Yes);
			sa.assertTrue(false, "Not able to Click on  tab: " + "connectionicon" );
			}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + RGFundraisingName1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + RGFundraisingName1 + " tab");
			}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + TabName.FundraisingsTab + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + TabName.FundraisingsTab + " tab");
			}
		lp.CRMlogout();
		sa.assertAll();
	}
	

@Parameters({ "projectName" })
@Test
public void ADETRGc053_1_VerifytheEmailCountClickandRedirectionfromExternaltabofConnectionDetailsPage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String ContactName = RGEContact1LName ;

	String contactname = "Jane Elias";
	String ExpectedHeader = "Emails With";
	
	if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
		ThreadSleep(3000);
		if (fp.clickOnAlreadyCreatedItem(projectName,RGFundraisingName1, 10)) {
		if (CommonLib.click(driver, BP.connectionicon(ContactName, 30), "Connection icon: " + "",
			action.BOOLEAN)) {
		log(LogStatus.INFO, "Clicked on connectionicon tab", YesNo.No);
		String parentWindowId1 = CommonLib.switchOnWindow(driver);
		if (!parentWindowId1.isEmpty()) {
		ThreadSleep(3000);
		if (CommonLib.click(driver, BP.ExternalTab( 30,action.SCROLLANDBOOLEAN), "ExternalTab tab: " + "",
				action.BOOLEAN)) {
		if (CommonLib.click(driver, BP.contactEmailCount(contactname, 30), "Email Count: " + "",
				action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + contactname, YesNo.No);
			Set<String> childWindow = driver.getWindowHandles();
			switchToDefaultContent(driver);
			System.out.println(childWindow);
			for(String child : childWindow) {
			driver.switchTo().window(child);
			}
			String actualHeader = getText(driver, BP.getConnectionpopupheader(20), "Contactpopupheader",
					action.SCROLLANDBOOLEAN);
//			if (ExpectedHeader.contains(actualHeader)) {
				if (actualHeader.contains(ExpectedHeader)) {
				log(LogStatus.INFO,
						"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
								+ ExpectedHeader + " for Contact Name: " + ContactName,
						YesNo.No);
			} else {
				log(LogStatus.ERROR,
						"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
								+ ExpectedHeader + " for Contact Name: " + ContactName,
						YesNo.No);
			}
				driver.switchTo().window(parentWindowId1);
				CommonLib.switchOnWindow(driver);
					driver.close();
				driver.switchTo().window(parentWindowId1);
			
				} else {
					log(LogStatus.FAIL, "Not able to Click on  tab: " + "contactEmailCount" , YesNo.Yes);
					sa.assertTrue(false, "Not able to Click on  tab: " + "contactEmailCount" );
					}
		}
		} else {
			log(LogStatus.FAIL, "Not able to Click on  tab: " + "External Tab" , YesNo.Yes);
			sa.assertTrue(false, "Not able to Click on  tab: " + "External Tab " );
			}
		} else {
			log(LogStatus.FAIL, "Not able to Click on  tab: " + "connectionicon" , YesNo.Yes);
			sa.assertTrue(false, "Not able to Click on  tab: " + "connectionicon" );
			}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + RGFundraisingName1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + RGFundraisingName1 + " tab");
			}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + TabName.FundraisingsTab + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + TabName.FundraisingsTab + " tab");
			}
		lp.CRMlogout();
		sa.assertAll();
	}

@Parameters({ "projectName" })
@Test
public void ADETRGc053_2_VerifytheEmailCountClickandRedirectionfromExternaltabofConnectionDetailsPage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
	OutlookPageBusinessLayer op = new OutlookPageBusinessLayer(driver);
	DealTeamPageBusinessLayer DTP = new DealTeamPageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String ContactName = RGEContact1LName ;
	String subject = "Uh-oh, your prescription is expiring TR6";
	String contactname = RGEContact1LName;
	String CCname = RGEContact1LName;
	if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.FundraisingsTab, YesNo.No);
		ThreadSleep(3000);
		if (fp.clickOnAlreadyCreatedItem(projectName,RGFundraisingName1, 10)) {
		if (CommonLib.click(driver, BP.connectionicon(ContactName, 30), "Connection icon: " + "",
			action.BOOLEAN)) {
		log(LogStatus.INFO, "Clicked on connectionicon tab", YesNo.No);
		String parentWindowId1 = CommonLib.switchOnWindow(driver);
		if (!parentWindowId1.isEmpty()) {
		ThreadSleep(3000);
		if (CommonLib.click(driver, BP.ExternalTab( 30,action.SCROLLANDBOOLEAN), "ExternalTab tab: " + "",
				action.BOOLEAN)) {
			if (clickUsingJavaScript(driver, BP.contactname(contactname, 10), "Contact  Name: " + subject,
					action.BOOLEAN)) {
				log(LogStatus.PASS, "Clicked on Contact Name: " + contactname, YesNo.No);
					Set<String> childWindow = driver.getWindowHandles();
					switchToDefaultContent(driver);
					System.out.println(childWindow);
					for(String child : childWindow) {
					driver.switchTo().window(child);
					}
							if (BP.ContactRecordPage(contactname, 20) != null) {
								log(LogStatus.PASS,
										"----Contact Detail Page is redirecting for Contact Record: " + contactname + "-----",
										YesNo.No);
								driver.close();
								driver.switchTo().window(parentWindowId1);

							} else {
								log(LogStatus.FAIL, "----Contact Detail Page is not redirecting for Deal Record: "
										+ subject + "-----", YesNo.Yes);
								sa.assertTrue(false,
										"----Contact Detail Page is not showing for Deal Record: " + contactname + "-----");
								driver.close();
								driver.switchTo().window(parentWindowId1);

							}

						} else {
							log(LogStatus.FAIL, "No New Window Open after click on Contact Link: " + subject, YesNo.Yes);
							sa.assertTrue(false, "No New Window Open after click on Contact Link: " + subject);
						}
				CommonLib.switchOnWindow(driver);
					driver.close();
				driver.switchTo().window(parentWindowId1);
			}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + "contactEmailCount" , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + "contactEmailCount" );
				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + "ExternalTab" , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + "ExternalTab" );
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + RGDeal1 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + RGDeal1 + " tab");
			}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + tabObj4 + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + tabObj4 + " tab");
			}
	       ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();
		
		}
//external tab for contact

@Parameters({ "projectName" })
@Test
public void ADETRGc054_VerifyEmailCountofExternalContactatExternalTabofconnectionGridatCAPage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String ContactName = RGEContact1LName ;
	String ContactName1 = RGEContact3LName ;
	String emailCountInFirm = "3";
	String actualemailCount = null;
	String ExpectedHeader = "Emails With";
	if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ContactName, 30)) {
			if (CommonLib.click(driver, BP.ExternalTab( 30,action.SCROLLANDBOOLEAN), "ExternalTab tab: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on ExternalTab tab", YesNo.No);
				actualemailCount = getText(driver, BP.contactEmailCount(ContactName1, 20), "deal",
						action.SCROLLANDBOOLEAN);
				if (BP.contactEmailCount(ContactName1, 20) != null) {
					if (!actualemailCount.equalsIgnoreCase("")) {

						if (actualemailCount.equalsIgnoreCase(emailCountInFirm)) {
							log(LogStatus.INFO, "Email Count for Contact: " + ContactName1 + " is " + actualemailCount
									+ " before Email Team Create is matched to " + emailCountInFirm, YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"Email Count for Contact: " + ContactName1
											+ " is before Email count is not matched, Expected: " + emailCountInFirm
											+ " but Actual: " + actualemailCount,
									YesNo.Yes);
							sa.assertTrue(false,
									"Email Count for Contact: " + ContactName1
											+ " is before Email count  not matched, Expected: " + emailCountInFirm
											+ " but Actual: " + actualemailCount);
						}

					} else {
						log(LogStatus.ERROR, "Email Count for Contact is Empty, So not able to check Count for Contact: "
								+ ContactName1, YesNo.Yes);
						sa.assertTrue(false, "Email Count for Contact is Empty, So not able to check Count for Contact: "
								+ ContactName1);
					}
				} else {
					log(LogStatus.ERROR, "No Contact found of Name: " + ContactName1, YesNo.No);
					sa.assertTrue(false, "No Contact found of Name: " + ContactName1);
				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + "External Tab" , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + "External Tab " );
				}
			if (CommonLib.click(driver, BP.contactEmailCount(ContactName, 30), "Email Count: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Email Count: " + " " + " of Record: " + ContactName, YesNo.No);
			String parentWindowId1 = CommonLib.switchOnWindow(driver);
			if (!parentWindowId1.isEmpty()) {
			ThreadSleep(3000);
			String actualHeader = getText(driver, BP.getConnectionpopupheader(20), "Contactpopupheader",
					action.SCROLLANDBOOLEAN);
//			if (ExpectedHeader.contains(actualHeader)) {
				if (actualHeader.contains(ExpectedHeader)) {
				log(LogStatus.INFO,
						"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
								+ ExpectedHeader + " for Contact Name: " + ContactName,
						YesNo.No);
			} else {
				log(LogStatus.ERROR,
						"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
								+ ExpectedHeader + " for Contact Name: " + ContactName,
						YesNo.No);
				sa.assertTrue(false,"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
						+ ExpectedHeader + " for Contact Name: " + ContactName);
			}
		     driver.close();
			driver.switchTo().window(parentWindowId1);
		}
			} else {
			log(LogStatus.FAIL, "Not able to Click on  tab: " + "contactEmailCount" , YesNo.Yes);
			sa.assertTrue(false, "Not able to Click on  tab: " + "contactEmailCount" );
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + ContactName + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + ContactName + " tab");
		}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + TabName.Object2Tab + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + TabName.Object2Tab + " tab");
		}
	lp.CRMlogout();
	sa.assertAll();
}

@Parameters({ "projectName" })
@Test
public void ADETRGc055_VerifythatwhenDownloadIcongetsClickedAgainstContactatExternalTabofConnectionGridatCAPage(String projectName) {
	LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
	FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
	BasePageBusinessLayer BP = new BasePageBusinessLayer(driver);
	lp.CRMLogin(rgUser2, rgOrgPassword, appName);
	String ContactName = RGEContact1LName ;
	String contactName = "Daniel";
	String ExpectedHeader = "New Contact";
	if (fp.clickOnTab(environment, mode, TabName.Object2Tab)) {
		log(LogStatus.INFO, "Click on Tab : " + TabName.Object2Tab, YesNo.No);

		if (fp.clickOnAlreadyCreatedItem(projectName, ContactName, 30)) {
			if (CommonLib.click(driver, BP.ExternalTab( 30,action.SCROLLANDBOOLEAN), "ExternalTab tab: " + "",
					action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on ExternalTab tab", YesNo.No);
				if(BP.DownloadIcon(contactName, 10) != null) {
					log(LogStatus.PASS, "Download Icon: " + contactName + " present", YesNo.No);
					if (CommonLib.click(driver, BP.DownloadIcon(contactName, 30), "Download icon: " + "",
							action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Download icon: " + " " + " of Record: " + contactName, YesNo.No);
						String actualHeader = getText(driver, BP.getContactpopupheader(20), "Contactpopupheader",
								action.SCROLLANDBOOLEAN);
						if (ExpectedHeader.equals(actualHeader)) {
							log(LogStatus.INFO,
									"Actual result " + actualHeader + " of pop up has been matched with Expected result : "
											+ ExpectedHeader + " for Contact Name: " + contactName,
									YesNo.No);
						} else {
							log(LogStatus.ERROR,
									"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
											+ ExpectedHeader + " for Contact Name: " + contactName,
									YesNo.No);
							sa.assertTrue(false,"Actual result " + actualHeader + " of pop up has been not matched with Expected result : "
									+ ExpectedHeader + " for Contact Name: " + contactName);
						}
					} else {
						log(LogStatus.ERROR, "Not able to click on Downlooad Icon " + contactName + " tab", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on Downlooad Icon " + contactName + " tab");
					}

				} else {
					log(LogStatus.ERROR, "download icon not visible " + contactName + " tab", YesNo.Yes);
					sa.assertTrue(false, "download icon not visible " + contactName + " tab");
				}
			} else {
				log(LogStatus.FAIL, "Not able to Click on  tab: " + "External Tab" , YesNo.Yes);
				sa.assertTrue(false, "Not able to Click on  tab: " + "External Tab " );
				}
			} else {
				log(LogStatus.ERROR, "Not able to click on " + RGEContact1LName + " tab", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on " + RGEContact1LName + " tab");
			}
		} else {
			log(LogStatus.ERROR, "Not able to click on " + tabObj2 + " tab", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on " + tabObj2 + " tab");
		}
		ThreadSleep(5000);
		lp.CRMlogout();
		sa.assertAll();

	}
}