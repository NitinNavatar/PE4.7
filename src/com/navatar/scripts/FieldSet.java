package com.navatar.scripts;
import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import static com.navatar.generic.ExcelUtils.*;
import java.util.List;
import java.util.concurrent.Phaser;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.ObjectFeatureName;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.navatar.generic.EnumConstants.object;
import com.navatar.pageObjects.CommitmentsPageBusinessLayer;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.EditPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.MarketingEventPageBusinessLayer;
import com.navatar.pageObjects.PartnershipsPageBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;

public class FieldSet extends BaseLib {
	
	
	
	@Parameters({ "projectName"})
	@Test
	public void FSTc002_verifyDefaultFieldSet(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot verify default field set");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot verify default field set",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot verify default field set");
			}
			ThreadSleep(3000);
			if(setup.searchStandardOrCustomObject(environment,mode, object.Contact)) {
				log(LogStatus.INFO, "click on Object : "+object.Contact, YesNo.No);
				ThreadSleep(2000);
				if(setup.clickOnObjectFeature(environment,mode, object.Contact, ObjectFeatureName.FieldSets)) {
					log(LogStatus.INFO, "Clicked on feature : "+ObjectFeatureName.FieldSets, YesNo.No);
					ThreadSleep(1000);
					if(click(driver, setup.getCreatedFieldSetLabelNameText("Display_field_set", 20),"default field set", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.PASS, "clicked on default field set option", YesNo.No);
						switchToFrame(driver, 20, setup.getEditPageLayoutFrame_Lighting(20));
						List<WebElement> lst = setup.getDefaultFieldSetLabelsList();
						if(!lst.isEmpty()) {
							if(compareMultipleList(driver, "Firm,Title,Phone,Email,Tier,Owner,Last Touch Point", lst).isEmpty()) {
								log(LogStatus.PASS, "Default field set data is matched", YesNo.No);
							}else {
								log(LogStatus.ERROR, "Default Field set data is not matched", YesNo.No);
								sa.assertTrue(false, "Default Field set data is not matched");
							}
						}else {
							log(LogStatus.ERROR, "Not able to get default field set labels so cannot verify it", YesNo.Yes);
							sa.assertTrue(false, "Not able to get default field set labels so cannot verify it");
						}



					}else {
						log(LogStatus.ERROR, "Not able to click on default field set so cannot verify default field set option", YesNo.Yes);
						sa.assertTrue(false, "Not able to click on default field set so cannot verify default field set option");
					}
				}else {
					log(LogStatus.ERROR, "Not able to click on contact field set so cannot verify default field set", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on contact field set so cannot verify default field set");
				}

			}else {
				log(LogStatus.ERROR, "Not able to click on object manager so cannot verify default field set", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on object manager so cannot verify default field set");
			}
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		}else {
			log(LogStatus.ERROR, "Not able to click on setup link so cannot verify default field set", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link so cannot verify default field set");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void FSTc003_createFieldSet(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create Field Set Component");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create Field Set Component",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create Field Set Component");
			}
			ThreadSleep(3000);
			object object1 = object.valueOf(FS_Object1);
			
			if(setup.createFieldSetComponent(object1, ObjectFeatureName.FieldSets, FS_FieldSetLabel1, FS_NameSpacePrefix1, FS_FieldsName1)) {
				log(LogStatus.PASS, "Field Set Component is created for : "+FS_FieldSetLabel1, YesNo.No);
			}else {
				log(LogStatus.ERROR,"Field Set Component is not created for : "+FS_FieldSetLabel1, YesNo.Yes);
				sa.assertTrue(false, "Field Set Component is not created for : "+FS_FieldSetLabel1);
			}
			object object2 = object.valueOf(FS_Object2);
			if(setup.createFieldSetComponent(object2, ObjectFeatureName.FieldSets, FS_FieldSetLabel2, FS_NameSpacePrefix2, FS_FieldsName2)) {
				log(LogStatus.PASS, "Field Set Component is created for : "+FS_FieldSetLabel2, YesNo.No);
			}else {
				log(LogStatus.ERROR,"Field Set Component is not created for : "+FS_FieldSetLabel2, YesNo.Yes);
				sa.assertTrue(false, "Field Set Component is not created for : "+FS_FieldSetLabel2);
			}
			object object3 = object.valueOf(FS_Object3);
			if(setup.createFieldSetComponent(object3, ObjectFeatureName.FieldSets, FS_FieldSetLabel3, FS_NameSpacePrefix3, FS_FieldsName3)) {
				log(LogStatus.PASS, "Field Set Component is created for : "+FS_FieldSetLabel3, YesNo.No);
			}else {
				log(LogStatus.ERROR,"Field Set Component is not created for : "+FS_FieldSetLabel3, YesNo.Yes);
				sa.assertTrue(false, "Field Set Component is not created for : "+FS_FieldSetLabel3);
			}
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		}else {
			log(LogStatus.ERROR, "Not able to click on setup link so cannot create Field Set Component", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link so cannot create Field Set Component");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void FSTc004_CreatePreconditionData(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		ContactsPageBusinessLayer con = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String value="";
		String type="";
		String[][] EntityOrAccounts = {{ FS_Ins1,FS_Ins1RecordType ,null}};
		
		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
				value = accounts[0];
				type = accounts[1];
				if (ip.createEntityOrAccount(projectName, value, type, null, 20)) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
			}
		}
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);	
			String mailID=	lp.generateRandomEmailId(gmailUserName);
			ExcelUtils.writeData(phase1DataSheetFilePath, mailID, "Contacts", excelLabel.Variable_Name, "C1",excelLabel.Contact_EmailId);
			if (con.createContact(projectName, FS_Con1_FName, FS_Con1_LName, FS_Ins1, mailID,FS_Con1_RecordType,excelLabel.Phone.toString(), FS_Con1_Phone, CreationPage.ContactPage, null)) {
				log(LogStatus.INFO,"successfully Created Contact : "+FS_Con1_FName+" "+FS_Con1_LName,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Contact : "+FS_Con1_FName+" "+FS_Con1_LName);
				log(LogStatus.SKIP,"Not Able to Create Contact: "+FS_Con1_FName+" "+FS_Con1_LName,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object4Tab,YesNo.No);	
			String[][] otherLabels = {{excelLabel.Source_Contact.toString(),FS_Deal1SourceContact},{excelLabel.Source_Firm.toString(),FS_Deal1SourceFirm}};

			if (fp.createDeal(projectName,"",FS_DealName1, FS_Deal1CompanyName, FS_Deal1Stage,otherLabels, 15)) {
				log(LogStatus.INFO,"Created Deal : "+FS_DealName1,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Deal  : "+FS_DealName1);
				log(LogStatus.SKIP,"Not Able to Create Deal  : "+FS_DealName1,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object4Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object4Tab,YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void FSTc005_addAdvanceFieldSetLayoutOnContactAndDealPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer con = new ContactsPageBusinessLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);
			if(con.clickOnCreatedContact(projectName, FS_Con1_FName, FS_Con1_LName)) {
				log(LogStatus.INFO,"clicked on created contact : "+FS_Con1_FName+" "+FS_Con1_LName, YesNo.No);
				ThreadSleep(5000);
				if(edit.clickOnEditPageLink()) {
					if(edit.dragAndDropLayOutFromEditPage(projectName, PageName.Object2Page, RelatedTab.Details, "Navatar Fieldset",FS_FieldSetLabel1)) {
						log(LogStatus.INFO, "Field set component is added on contact page :"+FS_Con1_FName+" "+FS_Con1_LName, YesNo.No);
					}else {
						log(LogStatus.ERROR, "Field set component is not added on contact page :"+FS_Con1_FName+" "+FS_Con1_LName, YesNo.Yes);
						sa.assertTrue(false, "Field set component is not added on contact page :"+FS_Con1_FName+" "+FS_Con1_LName);
					}
					
				}else {
					log(LogStatus.ERROR, "Not able to click on edit page so cannot add field set component on contact page : "+FS_Con1_FName+" "+FS_Con1_LName, YesNo.Yes);
					sa.assertTrue(false, "Not able to click on edit page so cannot add field set component on contact page : "+FS_Con1_FName+" "+FS_Con1_LName);
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on created contact "+FS_Con1_FName+" "+FS_Con1_LName+" so cannot drag and drop Advanced field set component", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on created contact "+FS_Con1_FName+" "+FS_Con1_LName+" so cannot drag and drop Advanced field set component");
			}
		} else {
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab+" so cannot drag and drop Advanced field set component",YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab+" so cannot drag and drop Advanced field set component");
		}
		ThreadSleep(3000);
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if(lp.clickOnAlreadyCreatedItem(projectName,TabName.DealTab, FS_DealName1, 30)){
				log(LogStatus.INFO,"clicked on created Deal : "+FS_DealName1, YesNo.No);
				ThreadSleep(5000);
				if(edit.clickOnEditPageLink()) {
					if(edit.dragAndDropLayOutFromEditPage(projectName, PageName.Object4Page, RelatedTab.Details, "Navatar Fieldset",FS_FieldSetLabel3)) {
						log(LogStatus.INFO, "Field set component is added on Deal page :"+FS_DealName1, YesNo.No);
					}else {
						log(LogStatus.ERROR, "Field set component is added on Deal page :"+FS_DealName1, YesNo.Yes);
						sa.assertTrue(false, "Field set component is added on Deal page :"+FS_DealName1);
					}
				}else {
					log(LogStatus.ERROR, "Not able to click on edit page so cannot add field set component on deal page : "+FS_DealName1, YesNo.Yes);
					sa.assertTrue(false, "Not able to click on edit page so cannot add field set component on deal page : "+FS_DealName1);
				}
			}else {
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object4Tab+" so cannot drag and drop Advanced field set component",YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object4Tab+" so cannot drag and drop Advanced field set component");
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object4Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object4Tab,YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void FSTc006_verifyAdvanceFieldSetLayoutOnContactAndDealPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer con = new ContactsPageBusinessLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);
			if(con.clickOnCreatedContact(projectName, FS_Con1_FName, FS_Con1_LName)) {
				log(LogStatus.INFO,"clicked on created contact : "+FS_Con1_FName+" "+FS_Con1_LName, YesNo.No);
				ThreadSleep(5000);
				String[] ss = FS_FieldsName1.split("<break>");
				String[] ss1 = {FS_Con1_FName+" "+FS_Con1_LName,FS_Ins1,FS_Con1_Email,FS_Con1_Phone};
				for(int i=0; i<ss.length; i++) {
					if(con.verifyFieldSetComponent(ss[i],ss1[i])) {
						log(LogStatus.PASS, ss[i]+" is verified : "+ss1[i], YesNo.No);
					}else {
						log(LogStatus.ERROR, ss[i]+" is not verified : "+ss1[i], YesNo.Yes);
						sa.assertTrue(false, ss[i]+" is not verified : "+ss1[i]);
					}
					
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on created contact "+FS_Con1_FName+" "+FS_Con1_LName+" so cannot verify Advanced field set component", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on created contact "+FS_Con1_FName+" "+FS_Con1_LName+" so cannot verify Advanced field set component");
			}
		} else {
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab+" so cannot verify Advanced field set component",YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab+" so cannot verify Advanced field set component");
		}
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			if(lp.clickOnAlreadyCreatedItem(projectName,TabName.DealTab, FS_DealName1, 30)){
				log(LogStatus.INFO,"clicked on created Deal : "+FS_DealName1, YesNo.No);
				ThreadSleep(5000);
				String[] ss = FS_FieldsName3.split("<break>");
				String[] ss1 = {FS_DealName1,FS_Deal1Stage,FS_Deal1SourceContact,FS_Deal1SourceFirm};
				for(int i=0; i<ss.length; i++) {
					if(con.verifyFieldSetComponent(ss[i],ss1[i])) {
						log(LogStatus.PASS, ss[i]+" is verified : "+ss1[i], YesNo.No);
					}else {
						log(LogStatus.ERROR, ss[i]+" is not verified : "+ss1[i], YesNo.Yes);
						sa.assertTrue(false, ss[i]+" is not verified : "+ss1[i]);
					}
				}
			}else {
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object4Tab+" so cannot verify Advanced field set component",YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object4Tab+" so cannot verify Advanced field set component");
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object4Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object4Tab,YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void FSTc007_1_removeObjectPermissionFromObject(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ContactsPageBusinessLayer con = new ContactsPageBusinessLayer(driver);
		String parentWindow = null;
		boolean flag = true;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create Field Set Component");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create Field Set Component",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create Field Set Component");
			}
			ThreadSleep(3000);
			if(setup.giveAndRemoveObjectPermissionFromObjectManager(object.Contact,ObjectFeatureName.FieldAndRelationShip,PermissionType.removePermission,"Email","PE Standard User")) {
				log(LogStatus.PASS,"Remove Permission from Contact Object", YesNo.No);
				flag=true;
			}else {
				log(LogStatus.ERROR,"Not able to remove permission from Contact Object", YesNo.Yes);
				sa.assertTrue(false, "Not able to remove permission from Contact Object");
			}
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		}else {
			log(LogStatus.ERROR, "Not able to click on setup link so cannot remove permission on email object of contact object", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link so cannot remove permission on email object of contact object");
		}
		if(flag) {
			if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);
				if(con.clickOnCreatedContact(projectName, FS_Con1_FName, FS_Con1_LName)) {
					log(LogStatus.INFO,"clicked on created contact : "+FS_Con1_FName+" "+FS_Con1_LName, YesNo.No);
					ThreadSleep(3000);
					if(con.verifyFieldSetComponent("Email",FS_Con1_Email)) {
						log(LogStatus.PASS,"Email is displaying : "+FS_Con1_Email, YesNo.No);
					}else {
						log(LogStatus.ERROR,"Email is not displaying : "+FS_Con1_Email, YesNo.Yes);
						sa.assertTrue(false,"Email is not displaying : "+FS_Con1_Email);
					}
				}else {
					log(LogStatus.ERROR, "Not able to click on created contact "+FS_Con1_FName+" "+FS_Con1_LName+" so cannot verify field set component", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on created contact "+FS_Con1_FName+" "+FS_Con1_LName+" so cannot verify field set component");
				}
			} else {
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab+" so cannot verify field set component",YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab+" so cannot verify field set component");
			}
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void FSTc007_2_verifyRemoveObjectImpactFromPEUser(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ContactsPageBusinessLayer con = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);
			if(con.clickOnCreatedContact(projectName, FS_Con1_FName, FS_Con1_LName)) {
				log(LogStatus.INFO,"clicked on created contact : "+FS_Con1_FName+" "+FS_Con1_LName, YesNo.No);
				ThreadSleep(3000);
				if(!con.verifyFieldSetComponent("Email",FS_Con1_Email)) {
					log(LogStatus.PASS, "Email object is not displaying : ", YesNo.No);
				}else {
					log(LogStatus.ERROR,"Email Object is displaying : "+FS_Con1_Email, YesNo.Yes);
					sa.assertTrue(false, "Email Object is displaying : "+FS_Con1_Email);
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on created contact "+FS_Con1_FName+" "+FS_Con1_LName+" so cannot verify field set component", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on created contact "+FS_Con1_FName+" "+FS_Con1_LName+" so cannot verify field set component");
			}
		} else {
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab+" so cannot verify field set component",YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab+" so cannot verify field set component");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void FSTc008_1_giveObjectPermissionFromObject(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ContactsPageBusinessLayer con = new ContactsPageBusinessLayer(driver);
		String parentWindow = null;
		boolean flag = true;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create Field Set Component");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create Field Set Component",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create Field Set Component");
			}
			ThreadSleep(3000);
			if(setup.giveAndRemoveObjectPermissionFromObjectManager(object.Contact,ObjectFeatureName.FieldAndRelationShip,PermissionType.removePermission,"Email","PE Standard User")) {
				log(LogStatus.PASS,"Remove Permission from Contact Object", YesNo.No);
				flag=true;
			}else {
				log(LogStatus.ERROR,"Not able to remove permission from Contact Object", YesNo.Yes);
				sa.assertTrue(false, "Not able to remove permission from Contact Object");
			}
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		}else {
			log(LogStatus.ERROR, "Not able to click on setup link so cannot remove permission on email object of contact object", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link so cannot remove permission on email object of contact object");
		}
		if(flag) {
			if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);
				if(con.clickOnCreatedContact(projectName, FS_Con1_FName, FS_Con1_LName)) {
					log(LogStatus.INFO,"clicked on created contact : "+FS_Con1_FName+" "+FS_Con1_LName, YesNo.No);
					ThreadSleep(3000);
					if(con.verifyFieldSetComponent("Email",FS_Con1_Email)) {
						log(LogStatus.PASS,"Email is displaying : "+FS_Con1_Email, YesNo.No);
					}else {
						log(LogStatus.ERROR,"Email is not displaying : "+FS_Con1_Email, YesNo.Yes);
						sa.assertTrue(false,"Email is not displaying : "+FS_Con1_Email);
					}
				}else {
					log(LogStatus.ERROR, "Not able to click on created contact "+FS_Con1_FName+" "+FS_Con1_LName+" so cannot verify field set component", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on created contact "+FS_Con1_FName+" "+FS_Con1_LName+" so cannot verify field set component");
				}
			} else {
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab+" so cannot verify field set component",YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab+" so cannot verify field set component");
			}
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void FSTc008_2_verifyGiveObjectImpactFromPEUser(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ContactsPageBusinessLayer con = new ContactsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);
			if(con.clickOnCreatedContact(projectName, FS_Con1_FName, FS_Con1_LName)) {
				log(LogStatus.INFO,"clicked on created contact : "+FS_Con1_FName+" "+FS_Con1_LName, YesNo.No);
				ThreadSleep(3000);
				if(con.verifyFieldSetComponent("Email",FS_Con1_Email)) {
					log(LogStatus.PASS, "Email object is displaying : ", YesNo.No);
				}else {
					log(LogStatus.ERROR,"Email Object is not displaying : "+FS_Con1_Email, YesNo.Yes);
					sa.assertTrue(false, "Email Object is not displaying : "+FS_Con1_Email);
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on created contact "+FS_Con1_FName+" "+FS_Con1_LName+" so cannot verify field set component", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on created contact "+FS_Con1_FName+" "+FS_Con1_LName+" so cannot verify field set component");
			}
		} else {
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab+" so cannot verify field set component",YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab+" so cannot verify field set component");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void FSTc009_changeTheFieldPositionAndCheckImpact(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ContactsPageBusinessLayer con = new ContactsPageBusinessLayer(driver);
		String parentWindow = null;
		String reverseObjects=reverseString(FS_FieldsName1);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot change Field position");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot change Field position",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot change Field position");
			}
			ThreadSleep(3000);
			
			object object1 = object.valueOf(FS_Object1);
			
			if(setup.changePositionOfFieldSetComponent(object1,ObjectFeatureName.FieldSets,FS_FieldSetLabel1, reverseObjects, FS_FieldsName1)) {
				log(LogStatus.PASS, "Field Set Object position is changed for : "+FS_FieldSetLabel1, YesNo.No);
			}else {
				log(LogStatus.ERROR,"Field Set Object position is not changed for : "+FS_FieldSetLabel1, YesNo.Yes);
				sa.assertTrue(false, "Field Set Object position is not changed for : "+FS_FieldSetLabel1);
			}
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		}else {
			log(LogStatus.ERROR, "Not able to click on setup link so cannot change Field Set Component position", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link so cannot create Field Set Component position");
		}
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);
			if(con.clickOnCreatedContact(projectName, FS_Con1_FName, FS_Con1_LName)) {
				log(LogStatus.INFO,"clicked on created contact : "+FS_Con1_FName+" "+FS_Con1_LName, YesNo.No);
				ThreadSleep(5000);
				String[] ss = reverseObjects.split("<break>");
				String[] ss1 = {FS_Con1_Phone,FS_Con1_Email,FS_Ins1,FS_Con1_FName+" "+FS_Con1_LName};;
				
				for(int i=0; i<ss.length; i++) {
					if(con.verifyFieldSetComponent(ss[i],ss1[i])) {
						log(LogStatus.PASS, ss[i]+" is verified : "+ss1[i], YesNo.No);
					}else {
						log(LogStatus.ERROR, ss[i]+" is not verified : "+ss1[i], YesNo.Yes);
						sa.assertTrue(false, ss[i]+" is not verified : "+ss1[i]);
					}
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on created contact "+FS_Con1_FName+" "+FS_Con1_LName+" so cannot verify Advanced field set component", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on created contact "+FS_Con1_FName+" "+FS_Con1_LName+" so cannot verify Advanced field set component");
			}
		} else {
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab+" so cannot verify Advanced field set component",YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab+" so cannot verify Advanced field set component");
		}
		
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void FSTc010_createNewFieldWithMaxCharacterAndAddOnContactProfileComponent(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		ContactsPageBusinessLayer con = new ContactsPageBusinessLayer(driver);
		String parentWindow = null;
		boolean flag = false;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create Field Set Component");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create Field Set Component",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create Field Set Component");
			}
			ThreadSleep(3000);
			
			String[][] labelAndValues= {{"Length",FC_Length1}};
			
			
			
			if(setup.addCustomFieldforFormula(environment,mode,object.Contact,ObjectFeatureName.FieldAndRelationShip,FC_FieldType1,FC_FieldLabelName1, labelAndValues, null,null)) {
				log(LogStatus.PASS, "Field Component is created for :"+FC_FieldLabelName1, YesNo.No);
				flag=true;
			}else {
				log(LogStatus.PASS, "Field Component is not created for :"+FC_FieldLabelName1, YesNo.Yes);
				sa.assertTrue(false, "Field Component is not created for :"+FC_FieldLabelName1);
			}
			if(flag) {
				object object1 = object.valueOf(FS_Object1);
				
				if(setup.changePositionOfFieldSetComponent(object1,ObjectFeatureName.FieldSets,FS_FieldSetLabel1, FC_FieldLabel1SubString,null)) {
					log(LogStatus.PASS, FC_FieldLabelName1+" Field Object is dragged On : "+FS_FieldSetLabel1, YesNo.No);
				}else {
					log(LogStatus.ERROR,FC_FieldLabelName1+" Field Set Object is not dragged on : "+FS_FieldSetLabel1, YesNo.Yes);
					sa.assertTrue(false, FC_FieldLabelName1+" Field Set Object is not dragged on : "+FS_FieldSetLabel1);
				}
				
			}else {
				log(LogStatus.PASS, "Field Component is not created for :  "+FC_FieldLabelName1+" so cannot dragndrop component in "+FS_FieldSetLabel1, YesNo.Yes);
				sa.assertTrue(false, "Field Component is not created for : "+FC_FieldLabelName1+" so cannot dragndrop component in "+FS_FieldSetLabel1);
			}
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		}else {
			log(LogStatus.ERROR, "Not able to click on setup link so cannot create Field Set Component", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link so cannot create Field Set Component");
		}
		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);
			if(con.clickOnCreatedContact(projectName, FS_Con1_FName, FS_Con1_LName)) {
				log(LogStatus.INFO,"clicked on created contact : "+FS_Con1_FName+" "+FS_Con1_LName, YesNo.No);
				ThreadSleep(5000);
				if(con.verifyFieldSetComponent(FC_FieldLabelName1,"")) {
					log(LogStatus.PASS, FC_FieldLabelName1+" is verified : ", YesNo.No);
				}else {
					log(LogStatus.ERROR,FC_FieldLabelName1+" is not verified : ", YesNo.Yes);
					sa.assertTrue(false,FC_FieldLabelName1+" is not verified : ");
				}

			}else {
				log(LogStatus.ERROR, "Not able to click on created contact "+FS_Con1_FName+" "+FS_Con1_LName+" so cannot verify Advanced field set component", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on created contact "+FS_Con1_FName+" "+FS_Con1_LName+" so cannot verify Advanced field set component");
			}
		} else {
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab+" so cannot verify Advanced field set component",YesNo.Yes);
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab+" so cannot verify Advanced field set component");
		}
		
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void FSTc011_AddingFiftyFieldsInCreatedFieldSet(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create Field Set Component");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create Field Set Component",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create Field Set Component");
			}
			ThreadSleep(3000);
			object object1 = object.valueOf(FS_Object1);
			if(setup.changePositionOfFieldSetComponent(object1,ObjectFeatureName.FieldSets,FS_FieldSetLabel1, FS_ExtraFieldsName1,null)) {
				log(LogStatus.PASS, FC_FieldLabelName1+" Field Object is dragged On : "+FS_FieldSetLabel1, YesNo.No);
			}else {
				log(LogStatus.ERROR,FC_FieldLabelName1+" Field Set Object is not dragged on : "+FS_FieldSetLabel1, YesNo.Yes);
				sa.assertTrue(false, FC_FieldLabelName1+" Field Set Object is not dragged on : "+FS_FieldSetLabel1);
			}
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		}else {
			log(LogStatus.ERROR, "Not able to click on setup link so cannot create Field Set Component", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link so cannot create Field Set Component");
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void FSTc012_CreatePreconditionDataForUploadImage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		PartnershipsPageBusinessLayer partnership = new PartnershipsPageBusinessLayer(driver);
		CommitmentsPageBusinessLayer com = new CommitmentsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String value="";
		String type="";
		String[][] EntityOrAccounts = {{ FS_Ins2, FS_Ins2RecordType ,null,null},{ FS_LP1, FS_LP1RecordType,InstitutionPageFieldLabelText.Parent_Institution.toString(),FS_Ins2}};

		for (String[] accounts : EntityOrAccounts) {
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
				value = accounts[0];
				type = accounts[1];
				if (ip.createInstitution(projectName, environment, mode, accounts[0],accounts[1], accounts[2],accounts[3])) {
					log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
					log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
			}
		}
		String[][] fundsOrDeals = {{FS_Fund1,"",FS_Fund1Type,FS_Fund1InvestmentCategory,null},
				{FS_Fund2,"",FS_Fund2Type,FS_Fund2InvestmentCategory,null}};
		for (String[] funds : fundsOrDeals) {
			if (lp.clickOnTab(projectName, TabName.Object3Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object3Tab,YesNo.No);	


				if (fp.createFundPE(projectName, funds[0], funds[1], funds[2], funds[3], null, 15)) {
					log(LogStatus.INFO,"Created Fund : "+funds[0],YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Fund : "+funds[0]);
					log(LogStatus.SKIP,"Not Able to Create Fund  : "+funds[0],YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object3Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object3Tab,YesNo.Yes);
			}
		}
		String[][] partnerships = {{FS_PartnerShip1,FS_Fund1},
				{FS_PartnerShip2,FS_Fund2}};
		for (String[] p : partnerships) {
			if (lp.clickOnTab(projectName, TabName.Object6Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object6Tab,YesNo.No);	


				if (partnership.createPartnership(projectName, environment, mode, p[0], p[1])) {
					log(LogStatus.INFO,"Created partnership : "+p[0],YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create partnership : "+p[0]);
					log(LogStatus.SKIP,"Not Able to Create partnership  : "+p[0],YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object6Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object6Tab,YesNo.Yes);
			}
		}
		String[][] Commitments = {{FS_LP1,FS_PartnerShip1,FS_FinalCommitmentDate1,FS_CommitmentAmount1,"Com1"},
				{FS_LP1,FS_PartnerShip2,FS_FinalCommitmentDate2,FS_CommitmentAmount2,FS_CommitmentID2,"Com2"}};
		for (String[] comm : Commitments) {
			if (lp.clickOnTab(projectName, TabName.Object7Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object7Tab,YesNo.No);	


				if (com.createCommitment(projectName, comm[0],comm[1], comm[2],comm[3],phase1DataSheetFilePath, comm[4])) {
					log(LogStatus.INFO,"Created Commiments : "+comm[0]+" "+comm[1],YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Commiments : "+comm[0]+" "+comm[1]);
					log(LogStatus.SKIP,"Not Able to Create Commiments  : "+comm[0]+" "+comm[1],YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object7Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object7Tab,YesNo.Yes);
			}
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void FSTc013_createFieldsForStandardObject(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create Fields object Entity for object");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create Fields object Entity for object",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create Fields object Entity for object");
			}
			ThreadSleep(3000);
			
			String[][] labelAndValues= {{FC_FieldType2,FC_FieldLabelName2,excelLabel.Length.toString(),FC_Length2},
					{FC_FieldType3,FC_FieldLabelName3,excelLabel.Length.toString(),FC_Length3},
			{FC_FieldType4,FC_FieldLabelName4,excelLabel.Length.toString(),FC_Length4},
			{FC_FieldType5,FC_FieldLabelName5,excelLabel.Length.toString(),FC_Length5},
			{FC_FieldType6,FC_FieldLabelName6,excelLabel.Length.toString(),FC_Length6},
			{FC_FieldType7,FC_FieldLabelName7,excelLabel.Length.toString(),FC_Length7},
			{FC_FieldType8,FC_FieldLabelName8,excelLabel.Length.toString(),FC_Length8},
			{FC_FieldType9,FC_FieldLabelName9,excelLabel.Length.toString(),FC_Length9},
			{FC_FieldType10,FC_FieldLabelName10,excelLabel.Length.toString(),FC_Length10},
			{FC_FieldType11,FC_FieldLabelName11,excelLabel.Length.toString(),FC_Length11}};
			
			
			for(String[] objects : labelAndValues) {
				String[][] valuesandLabel = {{objects[2],objects[3]}};
				
				if(setup.addCustomFieldforFormula(environment,mode,object.Entity,ObjectFeatureName.FieldAndRelationShip,objects[0],objects[1], valuesandLabel, null,null)) {
					log(LogStatus.PASS, "Field Object is created for :"+objects[1], YesNo.No);
				}else {
					log(LogStatus.PASS, "Field Object is not created for :"+objects[1], YesNo.Yes);
					sa.assertTrue(false, "Field Object is not created for :"+objects[1]);
				}
			}
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		}else {
			log(LogStatus.ERROR, "Not able to click on setup link so cannot create Fields object Entity for object", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link so cannot create Fields object Entity for object");
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void FSTc014_CreatedataForPackageObject(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String[][] values = {{FS_DealName2,FS_Deal2CompanyName,FS_Deal2Stage},{FS_DealName3,FS_Deal3CompanyName,FS_Deal3Stage}};
		for(String[] value : values) {
			if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object4Tab,YesNo.No);	
				if (fp.createDeal(projectName,"",value[0], value[1], value[2],null, 15)) {
					log(LogStatus.INFO,"Created Deal : "+value[0],YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Deal  : "+value[0]);
					log(LogStatus.SKIP,"Not Able to Create Deal  : "+value[0],YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object4Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object4Tab,YesNo.Yes);
			}
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void FSTc015_createFieldsForPackageObject(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create Field object");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create Field object",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create Field object");
			}
			ThreadSleep(3000);
			
			String[][] labelAndValues= {{FC_FieldType12,FC_FieldLabelName12,excelLabel.Length.toString(),FC_Length12},
					{FC_FieldType13,FC_FieldLabelName13,excelLabel.Length.toString(),FC_Length13},
			{FC_FieldType14,FC_FieldLabelName14,excelLabel.Length.toString(),FC_Length14},
			{FC_FieldType15,FC_FieldLabelName15,excelLabel.Length.toString(),FC_Length15},
			{FC_FieldType16,FC_FieldLabelName16,excelLabel.Length.toString(),FC_Length16},
			{FC_FieldType17,FC_FieldLabelName17,excelLabel.Length.toString(),FC_Length17},
			{FC_FieldType18,FC_FieldLabelName18,excelLabel.Length.toString(),FC_Length18},
			{FC_FieldType19,FC_FieldLabelName19,excelLabel.Length.toString(),FC_Length19},
			{FC_FieldType20,FC_FieldLabelName20,excelLabel.Length.toString(),FC_Length20},
			{FC_FieldType21,FC_FieldLabelName21,excelLabel.Length.toString(),FC_Length21}};
			
			
			for(String[] objects : labelAndValues) {
				String[][] valuesandLabel = {{objects[2],objects[3]}};
				
				if(setup.addCustomFieldforFormula(environment,mode,object.Deal,ObjectFeatureName.FieldAndRelationShip,objects[0],objects[1], valuesandLabel, null,null)) {
					log(LogStatus.PASS, "Field Object is created for :"+objects[1], YesNo.No);
				}else {
					log(LogStatus.PASS, "Field Object is not created for :"+objects[1], YesNo.Yes);
					sa.assertTrue(false, "Field Object is not created for :"+objects[1]);
				}
			}
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		}else {
			log(LogStatus.ERROR, "Not able to click on setup link so cannot create Fields Objects for Deal object", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link so cannot create Fields Objects for Deal object Marketing");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void FSTc016_CreatedataForCustomObject(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		MarketingEventPageBusinessLayer me = new MarketingEventPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (lp.clickOnTab(projectName, TabName.Object5Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object5Tab,YesNo.No);	


			if (me.createMarketingEvent(projectName, FS_MarketingEvent1Name, FS_MarketingEvent1RecordType, FS_MarketingEvent1Date, FS_MarketingEvent1Organizer, 10)) {
				log(LogStatus.INFO,"Created Marketing Event : "+FS_MarketingEvent1Name,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Marketing Event  : "+FS_MarketingEvent1Name);
				log(LogStatus.SKIP,"Not Able to Create Marketing Event  : "+FS_MarketingEvent1Name,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object5Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object5Tab,YesNo.Yes);
		}
		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void FSTc017_createFieldsForCustomObject(String projectName) {
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentWindow = null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create Fields Objects for custom object Marketing Event");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create Fields Objects for custom object Marketing Event",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create Fields Objects for custom object Marketing Event");
			}
			ThreadSleep(3000);
			
			String[][] labelAndValues= {{FC_FieldType22,FC_FieldLabelName22,excelLabel.Length.toString(),FC_Length22},
					{FC_FieldType23,FC_FieldLabelName23,excelLabel.Length.toString(),FC_Length23},
			{FC_FieldType24,FC_FieldLabelName24,excelLabel.Length.toString(),FC_Length24},
			{FC_FieldType25,FC_FieldLabelName25,excelLabel.Length.toString(),FC_Length25},
			{FC_FieldType26,FC_FieldLabelName26,excelLabel.Length.toString(),FC_Length26},
			{FC_FieldType27,FC_FieldLabelName27,excelLabel.Length.toString(),FC_Length27},
			{FC_FieldType28,FC_FieldLabelName28,excelLabel.Length.toString(),FC_Length28},
			{FC_FieldType29,FC_FieldLabelName29,excelLabel.Length.toString(),FC_Length29},
			{FC_FieldType30,FC_FieldLabelName30,excelLabel.Length.toString(),FC_Length30},
			{FC_FieldType31,FC_FieldLabelName31,excelLabel.Length.toString(),FC_Length31}};
			
			
			for(String[] objects : labelAndValues) {
				String[][] valuesandLabel = {{objects[2],objects[3]}};
				
				if(setup.addCustomFieldforFormula(environment,mode,object.Marketing_Event,ObjectFeatureName.FieldAndRelationShip,objects[0],objects[1], valuesandLabel, null,null)) {
					log(LogStatus.PASS, "Field Object is created for :"+objects[1], YesNo.No);
				}else {
					log(LogStatus.PASS, "Field Object is not created for :"+objects[1], YesNo.Yes);
					sa.assertTrue(false, "Field Object is not created for :"+objects[1]);
				}
			}
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		}else {
			log(LogStatus.ERROR, "Not able to click on setup link so cannot create Fields Objects for custom object Marketing Event", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link so cannot create Fields Objects for custom object Marketing Event");
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void FSTc018_verifyFieldSetImagePath(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		EditPageBusinessLayer edit = new EditPageBusinessLayer(driver);
		List<String> tabNames = createListOutOfString(readAllDataForAColumn(phase1DataSheetFilePath,"UploadImageData",1,false));
		List<String> Items = createListOutOfString(readAllDataForAColumn(phase1DataSheetFilePath,"UploadImageData",2,false));
		List<String> ItemFieldName = createListOutOfString(readAllDataForAColumn(phase1DataSheetFilePath,"UploadImageData",3,false));
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		for(int i=0; i<tabNames.size(); i++) {
			TabName tabName =TabName.valueOf(tabNames.get(i));
			if (lp.clickOnTab(projectName, tabName)) {
				log(LogStatus.INFO,"Click on Tab : "+tabName,YesNo.No);
				if(lp.clickOnAlreadyCreatedItem(projectName, Items.get(i), 20)) {
					log(LogStatus.INFO,"clicked on created item : "+Items.get(i), YesNo.No);
					ThreadSleep(5000);
					if(edit.clickOnEditPageLink()) {
						ThreadSleep(2000);
						switchToFrame(driver, 30, edit.getEditPageFrame(projectName,30));
						if(click(driver, edit.getFieldSetCompoentXpath(10), "field set component xpath", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "clicked on field set component", YesNo.No);
							ThreadSleep(2000);
							switchToDefaultContent(driver);
							if(getValueFromElementUsingJavaScript(driver, edit.getImageFieldNameTextBox(10), "image field name xpath").equalsIgnoreCase(ItemFieldName.get(i))){
								log(LogStatus.PASS, "Image Field Name is verified on "+tabName+" for "+Items.get(i), YesNo.No);

							}else {
								log(LogStatus.ERROR, "Image Field Name is not verified on "+tabName+" for "+Items.get(i), YesNo.Yes);
								sa.assertTrue(false, "Image Field Name is verified on "+tabName+" for "+Items.get(i));
							}
							ThreadSleep(2000);
							if(clickUsingJavaScript(driver, edit.getBackButton(10), "back button", action.BOOLEAN)) {
								log(LogStatus.PASS, "clicked on back button", YesNo.No);
							}else {
								log(LogStatus.ERROR, "Not able to click on back button so cannot back on page ", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on back button so cannot back on page ");
							}
						}else {
							log(LogStatus.ERROR, "Not able to click on field set component "+tabNames.get(i)+" so cannot verify field set name", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on field set component "+tabNames.get(i)+" so cannot verify field set name");
						}
					}else {
						log(LogStatus.ERROR, "Not able to click on edit page so cannot add field set component on contact page : "+FS_Con1_FName+" "+FS_Con1_LName, YesNo.Yes);
						sa.assertTrue(false, "Not able to click on edit page so cannot add field set component on contact page : "+FS_Con1_FName+" "+FS_Con1_LName);
					}
				}else {
					log(LogStatus.ERROR, "Not able to click on created item "+Items.get(i)+" so cannot verify field set image", YesNo.Yes);
					sa.assertTrue(false, "Not able to click on created item "+Items.get(i)+" so cannot verify field set image");
				}
			} else {
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" so cannot verify field set image",YesNo.Yes);
				sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" so cannot verify field set image");
			}
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void FSTc019_addProfileImageFieldOnLayOut(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		HomePageBusineesLayer home= new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		String parentWindow =null;
		List<String> objectNames = createListOutOfString(readAllDataForAColumn(phase1DataSheetFilePath,"UploadImageData",4,false));
		List<String> objectAPINames = createListOutOfString(readAllDataForAColumn(phase1DataSheetFilePath,"UploadImageData",5,false));
		List<String> FieldsNames = createListOutOfString(readAllDataForAColumn(phase1DataSheetFilePath,"UploadImageData",6,false));
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		
		if (home.clickOnSetUpLink()) {
			parentWindow = switchOnWindow(driver);
			if (parentWindow == null) {
				sa.assertTrue(false,
						"No new window is open after click on setup link in lighting mode so cannot create Fields Objects for custom object Marketing Event");
				log(LogStatus.SKIP,
						"No new window is open after click on setup link in lighting mode so cannot create Fields Objects for custom object Marketing Event",
						YesNo.Yes);
				exit("No new window is open after click on setup link in lighting mode so cannot create Fields Objects for custom object Marketing Event");
			}
			ThreadSleep(3000);
			
			
			for(int i=0; i<objectNames.size(); i++) {
				object ObjectName =object.valueOf(objectNames.get(i));
				if(setup.searchStandardOrCustomObject(environment,mode, ObjectName)) {
					log(LogStatus.INFO, "click on Object : "+ObjectName, YesNo.No);
					ThreadSleep(2000);
					if(setup.clickOnObjectFeature(environment,mode, ObjectName, ObjectFeatureName.pageLayouts)) {
						log(LogStatus.INFO, "Clicked on feature : "+ObjectFeatureName.pageLayouts+" of "+ObjectName, YesNo.No);
						ThreadSleep(1000);
						ThreadSleep(1000);
						if(sendKeys(driver, setup.getQuickSearchInObjectManager_Lighting(10), objectAPINames.get(i), "search text box", action.BOOLEAN)) {
							String xpath="//span[text()='"+objectAPINames.get(i)+"']/..";
							WebElement ele = isDisplayed(driver, FindElement(driver, xpath, "field set label text", action.BOOLEAN, 3), "visibility", 3, "field set label text");
							if(ele!=null) {
								if(click(driver, ele, "create field set "+objectAPINames.get(i), action.BOOLEAN)) {
									log(LogStatus.INFO, "Field Set Label "+objectAPINames.get(i)+" is already created ", YesNo.No);
									ThreadSleep(3000);
									switchToFrame(driver, 20, setup.getEditPageLayoutFrame_Lighting(20));
									sendKeys(driver, setup.getQuickFindSearchBox(environment, mode, 10), FieldsNames.get(i), "Search Value : "+FieldsNames.get(i), action.BOOLEAN);
									ThreadSleep(1000);
									WebElement sourceElement =isDisplayed(driver, FindElement(driver, "//span[starts-with(text(),'"+FieldsNames.get(i)+"')]", "", action.BOOLEAN,10), "visibility",10,FieldsNames.get(i)+" page layout link");

									ThreadSleep(2000);
									scrollDownThroughWebelement(driver, setup.getPageLayoutDropLocation(10), "");
									if(dragNDropOperation(driver, sourceElement, setup.getPageLayoutDropLocation(10))) {
										log(LogStatus.INFO, "Dragged Successfully : "+FieldsNames.get(i)+" page layout of "+ObjectName, YesNo.No);
										ThreadSleep(2000);
										if(setup.getDraggedFieldsLabelAndValueXpath(FieldsNames.get(i), 5)!=null) {
											log(LogStatus.PASS, "Dragged Field is visible on page layout "+FieldsNames.get(i)+" with url www.salesforce.com", YesNo.No);
										}else {
											log(LogStatus.ERROR, "Dragged Field is not visible on page layout "+FieldsNames.get(i)+" with url www.salesforce.com", YesNo.Yes);
											sa.assertTrue(false, "Dragged Field is not visible on page layout "+FieldsNames.get(i)+" with url www.salesforce.com");
										}

										if(click(driver, setup.getPageLayoutSaveBtn(object.Global_Actions, 10), "page layouts save button", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Clicked on Save button", YesNo.No);

										}else {
											log(LogStatus.ERROR, "Not able to click on Save button cannot save pagelayout dragged object or section in field set component "+FieldsNames.get(i), YesNo.Yes);
										}

									}else {
										log(LogStatus.ERROR, "Not able to drag and drop field "+FieldsNames.get(i)+" in page layout of "+ObjectName, YesNo.Yes);
									}

								}else {
									log(LogStatus.INFO, "Not able to click on created Field Set Label "+objectAPINames.get(i)+" is not visible so cannot change position of labels", YesNo.Yes);
								}
							}else {
								log(LogStatus.INFO, "created Field Set Label "+objectAPINames.get(i)+" is not visible so cannot change position of labels", YesNo.Yes);
							}
						}else {
							log(LogStatus.INFO, "Not able to search created Field Set Label "+objectAPINames.get(i), YesNo.Yes);
						}
					}else {
						log(LogStatus.ERROR, "Not able to click on object feature : "+ObjectFeatureName.pageLayouts+" of "+ObjectName, YesNo.Yes);
					}
					
				}else {
					log(LogStatus.ERROR, "Not able to click on Object : "+ObjectName+" so cannot create field set component", YesNo.Yes);
				}
				switchToDefaultContent(driver);
			}
			
			
			
		
			switchToDefaultContent(driver);
			driver.close();
			driver.switchTo().window(parentWindow);
		}else {
			log(LogStatus.ERROR, "Not able to click on setup link so cannot create Fields Objects for custom object Marketing Event", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link so cannot create Fields Objects for custom object Marketing Event");
		}
		
		
		
		
		
		
		lp.CRMlogout();
		sa.assertAll();
	}





}
