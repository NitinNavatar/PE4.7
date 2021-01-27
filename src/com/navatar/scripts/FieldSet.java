package com.navatar.scripts;
import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.EmailLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.CreationPage;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.excelLabel;
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
			ExcelUtils.writeData(fieldSetFilePath, mailID, "Contacts", excelLabel.Variable_Name, "C1",excelLabel.Contact_EmailId);
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
			
			
			
			if(setup.addCustomFieldforFormula(environment,mode,object.Contact,ObjectFeatureName.FieldAndRelationShip,FC_FieldType,FC_FieldLabelName1, labelAndValues, null,null)) {
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


				if (com.createCommitment(projectName, comm[0],comm[1], comm[2],comm[3],fieldSetFilePath, comm[4])) {
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


}
