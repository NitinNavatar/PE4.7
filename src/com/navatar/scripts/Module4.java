package com.navatar.scripts;


import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static com.navatar.generic.BaseLib.phase1DataSheetFilePath;
import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import com.navatar.generic.BaseLib;
import static com.navatar.generic.EnumConstants.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.*;
import com.navatar.pageObjects.*;
import com.relevantcodes.extentreports.LogStatus;

public class Module4 extends BaseLib{
	@Parameters({ "projectName"})
	@Test
	public void M4tc001_CreatePrecondition(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		MarketingEventPageBusinessLayer me = new MarketingEventPageBusinessLayer(driver);
		DealTeamPageBusinessLayer dtp = new DealTeamPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String value="";
		String type="";
		String status1=null;
		for (int i = 0;i<2;i++) {
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
				
				value=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M4INS"+(i+1), excelLabel.Institutions_Name);
				type=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M4INS"+(i+1), excelLabel.Record_Type);

				if (ip.createEntityOrAccount(projectName, value, type,null, 20)) {
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

		String fname="";
		String lname="";
		String mailID="";
		String ins="";
		String title=null;
		String region="";
		String phone="";
		String industry="";
		
		String recType;
		for (int i = 0;i<7;i++) {
			if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);
				
				fname=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON"+(i+1), excelLabel.Contact_FirstName);
				lname=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON"+(i+1), excelLabel.Contact_LastName);
				ins=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON"+(i+1), excelLabel.Institutions_Name);
				recType=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON"+(i+1), excelLabel.Record_Type);
				title=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON"+(i+1), excelLabel.Title);
				phone=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON"+(i+1), excelLabel.Phone);
				region=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON"+(i+1), excelLabel.Region);
				industry=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON"+(i+1), excelLabel.Industry);
				String[] contactsInfo = {  excelLabel.Phone.toString()+","+excelLabel.Region.toString()+","+excelLabel.Industry.toString(), phone+","+region+","+industry};
				mailID=	lp.generateRandomEmailId(gmailUserName);
				ExcelUtils.writeData(phase1DataSheetFilePath, mailID, "Contacts", excelLabel.Variable_Name, "M4CON"+(i+1),excelLabel.Contact_EmailId);
				System.err.println("field is "+contactsInfo[0]+" value is "+contactsInfo[1]);
				if (cp.createContact(projectName, fname, lname, ins, mailID,recType, contactsInfo[0], contactsInfo[1], CreationPage.ContactPage, title)) {
					log(LogStatus.INFO,"successfully Created Contact : "+fname+" "+lname,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create Contact : "+fname+" "+lname);
					log(LogStatus.SKIP,"Not Able to Create Contact: "+fname+" "+lname,YesNo.Yes);
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
			}
		}
		
		String pipe,company,stage,sf,sc;
		int i = 0;
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object4Tab,YesNo.No);	
			pipe= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M4Deal"+(i+1), excelLabel.Deal_Name);
			company= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M4Deal"+(i+1), excelLabel.Company_Name);
			stage= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M4Deal"+(i+1), excelLabel.Stage);
			refresh(driver);
			ThreadSleep(3000);
			if (fp.createDeal(projectName,"",pipe, company, stage,null, 15)) {
				log(LogStatus.INFO,"Created Deal : "+pipe,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Deal  : "+pipe);
				log(LogStatus.SKIP,"Not Able to Create Deal  : "+pipe,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		if (lp.clickOnTab(projectName, TabName.Object5Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object5Tab,YesNo.No);	
			
			if (me.createMarketingEvent(projectName, M4MarketingEvent1Name, M4MarketingEvent1RecordType, M4MarketingEvent1Date, M4MarketingEvent1Organizer, 10)) {
				log(LogStatus.INFO,"Created Marketing Event : "+M4MarketingEvent1Name,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Marketing Event  : "+M4MarketingEvent1Name);
				log(LogStatus.SKIP,"Not Able to Create Marketing Event  : "+M4MarketingEvent1Name,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object5Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object5Tab,YesNo.Yes);
		}
		String staff="",status="",event="",notes="";
		WebElement ele=null;
		for (i = 0;i<6;i++) {
			if (lp.clickOnTab(projectName, TabName.Object5Tab)) {

				if (ip.clickOnAlreadyCreatedItem(projectName, M4MarketingEvent1Name, true, 15)) {
					ele=ip.getRelatedTab(projectName, RelatedTab.Related.toString(), 10);
					click(driver, ele, "related", action.SCROLLANDBOOLEAN);
					log(LogStatus.INFO,"Item found: "+ToggleMarketingEvent1Name, YesNo.No);
					log(LogStatus.INFO,"Click on Tab : "+TabName.AttendeeTab,YesNo.No);	
					staff= ExcelUtils.readData(phase1DataSheetFilePath,"Attendees",excelLabel.Variable_Name, "M4Att" +(i+1), excelLabel.Attendee_Staff);
					status= ExcelUtils.readData(phase1DataSheetFilePath,"Attendees",excelLabel.Variable_Name, "M4Att"+(i+1), excelLabel.Status);
					event= ExcelUtils.readData(phase1DataSheetFilePath,"Attendees",excelLabel.Variable_Name, "M4Att"+(i+1), excelLabel.Marketing_Event);
					notes= ExcelUtils.readData(phase1DataSheetFilePath,"Attendees",excelLabel.Variable_Name, "M4Att"+(i+1), excelLabel.Notes);
					String[][] attendee1 = {{AttendeeLabels.Attendee_Staff.toString(),staff}
					,{AttendeeLabels.Status.toString(),status},{AttendeeLabels.Notes.toString(),notes}};

					if (me.createAttendee(projectName, event, attendee1, action.SCROLLANDBOOLEAN, 10)) {
						log(LogStatus.INFO,"Created Marketing Event : "+M4MarketingEvent1Name,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Marketing Event  : "+M4MarketingEvent1Name);
						log(LogStatus.SKIP,"Not Able to Create Marketing Event  : "+M4MarketingEvent1Name,YesNo.Yes);
					}
				}
				else {
					sa.assertTrue(false,"Not Able to Click on Marketing Event : "+M4MarketingEvent1Name);
					log(LogStatus.SKIP,"Not Able to Click on Marketing Event : "+M4MarketingEvent1Name,YesNo.Yes);
				}
			} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object5Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object5Tab,YesNo.Yes);
		}
		}
		String deal="",member="",role="";
		for (i = 0;i<11;i++) {
			if (lp.clickOnTab(projectName, TabName.Deal_Team)) {

				log(LogStatus.INFO,"Click on Tab : "+TabName.Deal_Team,YesNo.No);	
				deal= ExcelUtils.readData(phase1DataSheetFilePath,"Deal Team",excelLabel.Variable_Name, "M4DT" +(i+1), excelLabel.Deal_Name);
				member= ExcelUtils.readData(phase1DataSheetFilePath,"Deal Team",excelLabel.Variable_Name, "M4DT"+(i+1), excelLabel.Member);
				type= ExcelUtils.readData(phase1DataSheetFilePath,"Deal Team",excelLabel.Variable_Name, "M4DT"+(i+1), excelLabel.Type);
				role= ExcelUtils.readData(phase1DataSheetFilePath,"Deal Team",excelLabel.Variable_Name, "M4DT"+(i+1), excelLabel.Role);
				String[][] dt = {{PageLabel.Member.toString(),member},{PageLabel.Deal.toString(),deal}
				,{PageLabel.Team_Member_Role.toString(),role},{PageLabel.Type.toString(),type}};

				if (dtp.createDealTeam(projectName, deal, dt,"M4DT" +(i+1), action.SCROLLANDBOOLEAN, 10)) {
					log(LogStatus.INFO,"Created deal team for deal : "+deal,YesNo.No);	
				} else {
					sa.assertTrue(false,"Not Able to Create deal team for deals : "+deal);
					log(LogStatus.SKIP,"Not Able to Create deal team for deals : "+deal,YesNo.Yes);
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Deal_Team);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Deal_Team,YesNo.Yes);
			}
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M4tc002_CreateContactSDG(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, SDG);
		String fields=SDGLabels.APIName.toString();String values="";
		if (lp.searchAndClickOnApp(SDG, 30)) {
			log(LogStatus.INFO,"Able to Click/Search : "+SDG+" going to create custom SDG",YesNo.No);	 
			ThreadSleep(3000);

			if (lp.clickOnTab(projectName, TabName.SDGTab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.SDGTab,YesNo.No);

				String[][] sdgLabels = {{SDGCreationLabel.SDG_Name.toString(),M4Sdg1Name},
						{SDGCreationLabel.SDG_Tag.toString(),M4Sdg1TagName},
						{SDGCreationLabel.sObjectName.toString(),M4Sdg1ObjectName},{SDGCreationLabel.Parent_Field_Name.toString(),M4Sdg1ParentName}};

				if (sdg.createCustomSDG(projectName, M4Sdg1Name, sdgLabels, action.BOOLEAN, 20)) {
					log(LogStatus.PASS,"create/verify created SDG : "+M4Sdg1Name,YesNo.No);
					for(int i = 0;i<5;i++) {
						String api=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "CField" + (i+1), excelLabel.APIName);
						/*String over=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "CField" + (i+1), excelLabel.Override_Label);
						String fieldOrder=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "CField" + (i+1), excelLabel.FieldOrder);
						String url=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "CField" + (i+1), excelLabel.URL);
						String par=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "CField" + (i+1), excelLabel.Parent_Field_Name);
*/
						values=api;
						if (sdg.addFieldOnSDG(projectName,fields,values)) {
							log(LogStatus.INFO,"Successfully added fields on "+M4Sdg1Name,YesNo.Yes);

						}else {
							sa.assertTrue(false,"Not Able to add fields on SDG : "+M4Sdg1Name);
							log(LogStatus.SKIP,"Not Able to add fields on SDG : "+M4Sdg1Name,YesNo.Yes);
						}
					}
				} else {
					sa.assertTrue(false,"Not Able to create/verify created SDG : "+M4Sdg1Name);
					log(LogStatus.SKIP,"Not Able to create/verify created SDG : "+M4Sdg1Name,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.SDGTab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.SDGTab,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click/Search : "+SDG+" so can not create custom SDG");
			log(LogStatus.SKIP,"Not Able to Click/Search : "+SDG+" so can not create custom SDG",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M4tc003_CreateDealTeamSDG(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, SDG);
		String fields=SDGLabels.APIName.toString();String values="";
		if (lp.searchAndClickOnApp(SDG, 30)) {
			log(LogStatus.INFO,"Able to Click/Search : "+SDG+" going to create custom SDG",YesNo.No);	 
			ThreadSleep(3000);

			if (lp.clickOnTab(projectName, TabName.SDGTab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.SDGTab,YesNo.No);

				String[][] sdgLabels = {{SDGCreationLabel.SDG_Name.toString(),M4Sdg2Name},
						{SDGCreationLabel.SDG_Tag.toString(),M4Sdg2TagName},
						{SDGCreationLabel.sObjectName.toString(),M4Sdg2ObjectName},{SDGCreationLabel.Parent_Field_Name.toString(),M4Sdg2ParentName}};

				if (sdg.createCustomSDG(projectName, M4Sdg2Name, sdgLabels, action.BOOLEAN, 20)) {
					log(LogStatus.PASS,"create/verify created SDG : "+M4Sdg2Name,YesNo.No);
					for(int i = 0;i<4;i++) {
						String api=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "DTField" + (i+1), excelLabel.APIName);
						/*String over=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "DTField" + (i+1), excelLabel.Override_Label);
						String fieldOrder=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "DTField" + (i+1), excelLabel.FieldOrder);
						String url=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "DTField" + (i+1), excelLabel.URL);
						String par=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "DTField" + (i+1), excelLabel.Parent_Field_Name);
*/
						values=api;
						if (sdg.addFieldOnSDG(projectName,fields,values)) {
							log(LogStatus.INFO,"Successfully added fields on "+M4Sdg1Name,YesNo.Yes);

						}else {
							sa.assertTrue(false,"Not Able to add fields on SDG : "+M4Sdg1Name);
							log(LogStatus.SKIP,"Not Able to add fields on SDG : "+M4Sdg1Name,YesNo.Yes);
						}
					}
				} else {
					sa.assertTrue(false,"Not Able to create/verify created SDG : "+M4Sdg2Name);
					log(LogStatus.SKIP,"Not Able to create/verify created SDG : "+M4Sdg2Name,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.SDGTab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.SDGTab,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click/Search : "+SDG+" so can not create SDG");
			log(LogStatus.SKIP,"Not Able to Click/Search : "+SDG+" so can not create SDG",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M4tc004_CreateAttendeeSDG(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, SDG);
		String fields=SDGLabels.APIName.toString();String values="";
		if (lp.searchAndClickOnApp(SDG, 30)) {
			log(LogStatus.INFO,"Able to Click/Search : "+SDG+" going to create custom SDG",YesNo.No);	 
			ThreadSleep(3000);

			if (lp.clickOnTab(projectName, TabName.SDGTab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.SDGTab,YesNo.No);

				String[][] sdgLabels = {{SDGCreationLabel.SDG_Name.toString(),M4Sdg3Name},
						{SDGCreationLabel.SDG_Tag.toString(),M4Sdg3TagName},
						{SDGCreationLabel.sObjectName.toString(),M4Sdg3ObjectName},{SDGCreationLabel.Parent_Field_Name.toString(),M4Sdg3ParentName}};

				if (sdg.createCustomSDG(projectName, M4Sdg3Name, sdgLabels, action.BOOLEAN, 20)) {
					log(LogStatus.PASS,"create/verify created SDG : "+M4Sdg3Name,YesNo.No);
					for(int i = 0;i<4;i++) {
						String api=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "AField" + (i+1), excelLabel.APIName);
						/*String over=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "AField" + (i+1), excelLabel.Override_Label);
						String fieldOrder=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "AField" + (i+1), excelLabel.FieldOrder);
						String url=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "AField" + (i+1), excelLabel.URL);
						String par=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "AField" + (i+1), excelLabel.Parent_Field_Name);
*/
						values=api;
						if (sdg.addFieldOnSDG(projectName,fields,values)) {
							log(LogStatus.INFO,"Successfully added fields on "+M4Sdg3Name,YesNo.Yes);

						}else {
							sa.assertTrue(false,"Not Able to add fields on SDG : "+M4Sdg3Name);
							log(LogStatus.SKIP,"Not Able to add fields on SDG : "+M4Sdg3Name,YesNo.Yes);
						}
					}
				} else {
					sa.assertTrue(false,"Not Able to create/verify created SDG : "+M4Sdg3Name);
					log(LogStatus.SKIP,"Not Able to create/verify created SDG : "+M4Sdg3Name,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.SDGTab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.SDGTab,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click/Search : "+SDG+" so can not create SDG");
			log(LogStatus.SKIP,"Not Able to Click/Search : "+SDG+" so can not create SDG",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M4tc005_AddRelatedListAccordianOnEntity(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String fieldValues[]={EditPageLabel.Title.toString()+"<break>"+"Contacts",EditPageLabel.Query.toString()+"<break>"+ep.ContactSDGQuery(""),
				EditPageLabel.Image_Field_API_Name.toString()+"<break>"+"Profile_Image__c",EditPageLabel.Number_of_Records_to_Display.toString()+"<break>6",
				EditPageLabel.SDG_Name.toString()+"<break>"+M4Sdg1Name,EditPageLabel.Popup_Title.toString()+"<break>"+M4Sdg1Name+"s"
		};
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1, 10)) {
				if (ep.clickOnEditPageLink()) {
					log(LogStatus.INFO, "successfully reached edit page", YesNo.No);
					
					if (ep.dragAndDropAccordian(projectName, PageName.Object1Page, "RelatedListAccordion", fieldValues)) {
						log(LogStatus.INFO, "successfully added accordion on entity page", YesNo.No);
							
					}else {
						log(LogStatus.ERROR, "could not drop accordion on entity page", YesNo.Yes);
						sa.assertTrue(false,"could not drop accordion on entity page" );
					}
				}else {
					log(LogStatus.ERROR, "Not able to open edit page, so cannot add accordion", YesNo.Yes);
					sa.assertTrue(false,"Not able to open edit page, so cannot add accordion" );
				}
			}else {
				log(LogStatus.ERROR, "Not able to go to entity record "+M4Ins1, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to entity record "+M4Ins1 );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on entity tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on entity tab" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M4tc006_VerifyAccordionOnEntityPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		String id=null;
		WebElement ele=null;
		String contact=M4Contact1FName+" "+M4Contact1LName;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String ind=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON1", excelLabel.Industry);
		
		String fieldValue[]={excelLabel.Title.toString()+breakSP+M4Contact1Title};
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1,10)) {
				if (ip.verifyAccordion(projectName, contact, fieldValue,10)) {
					log(LogStatus.INFO, "successfully verified fields and values in accordion", YesNo.No);

				}else {
					log(LogStatus.ERROR, "could not verify fields and values in accordion", YesNo.Yes);
					sa.assertTrue(false,"could not verify fields and values in accordion" );
				}
				if (ip.verifyAccordianRecordImage(projectName, contact, BasePageErrorMessage.defaultPhotoText)) {
					log(LogStatus.INFO, "successfully verified update photo in accordion", YesNo.No);

				}else {
					log(LogStatus.ERROR, "could not verify update photo in accordion", YesNo.Yes);
					sa.assertTrue(false,"could not verify update photo in accordion" );
				}
			}else {
				log(LogStatus.ERROR, "Not able to go to entity record "+M4Ins1, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to entity record "+M4Ins1 );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on entity tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on entity tab" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M4tc007_UpdateImageOnContactProfile(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		String id=null;
		String attachmentPath1= System.getProperty("user.dir")+"\\UploadFiles\\Module 4\\tc7\\1.jpg";
		String attachmentPath2= System.getProperty("user.dir")+"\\UploadFiles\\Module 4\\tc7\\2.jpg";
		WebElement ele=null;
		String contact=M4Contact1FName+" "+M4Contact1LName;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object2Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName,contact , 10)) {
				ele=cp.getRelatedTab(projectName, RelatedTab.Overview.toString(), 10);
				click(driver, ele, "overview tab", action.BOOLEAN);
				id=cp.updatePhotoInDetailPage(projectName, attachmentPath1);
				if (id!=null) {
					log(LogStatus.INFO, "successfully updated photo", YesNo.No);

				}else {
					log(LogStatus.ERROR, "could not update photo", YesNo.Yes);
					sa.assertTrue(false,"could not update photo" );
				}
			}else {
				log(LogStatus.ERROR, "Not able to go to contact record "+contact, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to contact record "+contact );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on contact tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on contact tab" );
		}
		
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1,10)) {
				if (ip.verifyAccordianRecordImage(projectName, contact, id)) {
					log(LogStatus.INFO, "successfully verified update photo in accordion", YesNo.No);

				}else {
					log(LogStatus.ERROR, "could not verify update photo in accordion", YesNo.Yes);
					sa.assertTrue(false,"could not verify update photo in accordion" );
				}
			}else {
				log(LogStatus.ERROR, "Not able to go to entity record "+M4Ins1, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to entity record "+M4Ins1 );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on entity tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on entity tab" );
		}
		
		if (ip.clickOnTab(projectName, TabName.Object2Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Contact1FName+" "+M4Contact1LName, 10)) {
				ele=cp.getRelatedTab(projectName, RelatedTab.Overview.toString(), 10);
				click(driver, ele, "overview tab", action.BOOLEAN);
				id=cp.updatePhotoInDetailPage(projectName,attachmentPath2);
				if (id!=null) {
					log(LogStatus.INFO, "successfully updated photo", YesNo.No);

				}else {
					log(LogStatus.ERROR, "could not update photo", YesNo.Yes);
					sa.assertTrue(false,"could not update photo" );
				}
			}else {
				log(LogStatus.ERROR, "Not able to go to contact record "+M4Contact1FName+" "+M4Contact1LName, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to contact record "+M4Contact1FName+" "+M4Contact1LName );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on contact tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on contact tab" );
		}
		
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1,10)) {
				if (ip.verifyAccordianRecordImage(projectName, contact, id)) {
					log(LogStatus.INFO, "successfully verified update photo in accordion", YesNo.No);

				}else {
					log(LogStatus.ERROR, "could not verify update photo in accordion", YesNo.Yes);
					sa.assertTrue(false,"could not verify update photo in accordion" );
				}
			}else {
				log(LogStatus.ERROR, "Not able to go to entity record "+M4Ins1, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to entity record "+M4Ins1 );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on entity tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on entity tab" );
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M4tc008_DeleteImageOnContactProfile(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		String id=null;
		WebElement ele=null;
		String contact=M4Contact1FName+" "+M4Contact1LName;
		
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object2Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName,contact , 10)) {
				ele=cp.getRelatedTab(projectName, RelatedTab.Overview.toString(), 10);
				click(driver, ele, "overview tab", action.BOOLEAN);
				if (cp.deleteImage(projectName, contact)) {
					log(LogStatus.INFO, "successfully deleted photo", YesNo.No);

				}else {
					log(LogStatus.ERROR, "could not deleted photo", YesNo.Yes);
					sa.assertTrue(false,"could not deleted photo" );
				}
			}else {
				log(LogStatus.ERROR, "Not able to go to contact record "+contact, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to contact record "+contact );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on contact tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on contact tab" );
		}
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1,10)) {
				if (ip.verifyAccordianRecordImage(projectName, contact, BasePageErrorMessage.defaultPhotoText)) {
					log(LogStatus.INFO, "successfully verified deleted photo in accordion", YesNo.No);

				}else {
					log(LogStatus.ERROR, "could not verify deleted photo in accordion", YesNo.Yes);
					sa.assertTrue(false,"could not verify deleted photo in accordion" );
				}
			}else {
				log(LogStatus.ERROR, "Not able to go to entity record "+M4Ins1, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to entity record "+M4Ins1 );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on entity tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on entity tab" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M4tc009_VerifyMinimumRecordsOnAccordionEntityPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		String id=null;
		WebElement ele=null;
		String contact=M4Contact1FName+" "+M4Contact1LName;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String first,last,title;
		
		String fieldValue[]={excelLabel.Title.toString()+breakSP+M4Contact1Title};
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1,10)) {
				for (int i = 0;i<7;i++) {
					first=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON"+(i+1), excelLabel.Contact_FirstName);
					last=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON"+(i+1), excelLabel.Contact_LastName);
					contact=first+" "+last;
					title=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON"+(i+1), excelLabel.Title);
					fieldValue[0]=excelLabel.Title.toString()+breakSP+title;
					if (i==6) {
						if (!ip.verifyAccordion(projectName, contact, fieldValue,5)) {
							log(LogStatus.INFO, "successfully verified absence of 7th contact in accordion", YesNo.No);

						}else {
							log(LogStatus.ERROR, "7th contact is present but it should not be", YesNo.Yes);
							sa.assertTrue(false,"7th contact is present but it should not be" );
						}
					}
					else {

						if (ip.verifyAccordion(projectName, contact, fieldValue,10)) {
							log(LogStatus.INFO, "successfully verified "+(i+1)+"th contact in accordion", YesNo.No);

						}else {
							log(LogStatus.ERROR, "could not verify "+(i+1)+"th contact in accordion", YesNo.Yes);
							sa.assertTrue(false,"could not verify "+(i+1)+"th contact in accordion" );
						}
					}
				}
			}else {
				log(LogStatus.ERROR, "Not able to go to entity record "+M4Ins1, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to entity record "+M4Ins1 );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on entity tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on entity tab" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M4tc010_VerifyExpandCollapseOnAccordion(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		String id=null;
		WebElement ele=null;
		String contact=M4Contact1FName+" "+M4Contact1LName;
		String contactHeader=ip.getTabName(projectName,TabName.Object2Tab);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1,10)) {
				ele=ip.returnAccordionViewDetailsLink(projectName, contactHeader);
				if (click(driver, ele, "accordion view details", action.SCROLLANDBOOLEAN)) {
					ele=ip.getHeaderTextForPage(projectName, PageName.NewTaskPopUP, contactHeader, action.BOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO, "successfully verified presence of header in accordion", YesNo.No);

					}else {
						log(LogStatus.ERROR, "could not verify presence of header in accordion", YesNo.Yes);
						sa.assertTrue(false,"could not verify presence of header in accordion" );
					}
					ele=ip.accordionExpandCollapse(projectName, ExpandCollapse.Collapse, 10);
					
					if (ele!=null) {
						log(LogStatus.INFO, "verified default is expanded sdg", YesNo.No);

						if (click(driver, ele, "collapse icon", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on collapse link", YesNo.No);

							ele=ip.accordionExpandCollapse(projectName, ExpandCollapse.Collapse,2);
							if (ele==null)
								log(LogStatus.INFO, "successfully verified collapsed sdg", YesNo.No);
							else {
								log(LogStatus.ERROR, "could not verify sdg collapse", YesNo.Yes);
								sa.assertTrue(false,"could not verify sdg collapse" );
							}
						}else {
							log(LogStatus.ERROR, "Collapse icon is not clickable", YesNo.Yes);
							sa.assertTrue(false,"Collapse icon is not clickable" );
						}
					}else {
						log(LogStatus.ERROR, "Collapse icon is not visible, so cannot verify collapse functionality", YesNo.Yes);
						sa.assertTrue(false,"Collapse icon is not visible, so cannot verify collapse functionality" );
					}
					click(driver, ip.accordionModalWindowClose(projectName, contactHeader),"cross icon", action.BOOLEAN);
				}else {
					log(LogStatus.ERROR, "accordion link is not clickable", YesNo.Yes);
					sa.assertTrue(false,"accordion link is not clickable" );
				}
			}else {
				log(LogStatus.ERROR, "Not able to go to entity record "+M4Ins1, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to entity record "+M4Ins1 );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on entity tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on entity tab" );
		}
		lp.CRMlogout();
		ThreadSleep(3000);
		driver.close();
		
		config(browserToLaunch);
		lp = new LoginPageBusinessLayer(driver);
		fp = new FundsPageBusinessLayer(driver);
		cp = new ContactsPageBusinessLayer(driver);
		ip = new InstitutionsPageBusinessLayer(driver);
		
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1,10)) {
				ele=ip.returnAccordionViewDetailsLink(projectName, contactHeader);
				if (click(driver, ele, "accordion view details", action.SCROLLANDBOOLEAN)) {
					ele=ip.getHeaderTextForPage(projectName, PageName.NewTaskPopUP, contactHeader, action.BOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO, "successfully verified presence of header in accordion", YesNo.No);

					}else {
						log(LogStatus.ERROR, "could not verify presence of header in accordion", YesNo.Yes);
						sa.assertTrue(false,"could not verify presence of header in accordion" );
					}
					ele=ip.accordionExpandCollapse(projectName, ExpandCollapse.Collapse, 2);
					
					if (ele==null) {
						log(LogStatus.INFO, "verified sdg is collapsed", YesNo.No);

							ele=ip.accordionExpandCollapse(projectName, ExpandCollapse.Expand,2);
							if (ele!=null)
								log(LogStatus.INFO, "successfully verified collapsed sdg", YesNo.No);
							else {
								log(LogStatus.ERROR, "could not verify sdg collapse", YesNo.Yes);
								sa.assertTrue(false,"could not verify sdg collapse" );
							}
					}else {
						log(LogStatus.ERROR, "Collapse icon is visible, but it should not be", YesNo.Yes);
						sa.assertTrue(false,"Collapse icon is visible, but it should not be" );
					}
					click(driver, ip.accordionModalWindowClose(projectName, contactHeader),"cross icon", action.BOOLEAN);
				}else {
					log(LogStatus.ERROR, "accordion link is not clickable", YesNo.Yes);
					sa.assertTrue(false,"accordion link is not clickable" );
				}
			}else {
				log(LogStatus.ERROR, "Not able to go to entity record "+M4Ins1, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to entity record "+M4Ins1 );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on entity tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on entity tab" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M4tc011_1_VerifySpecialCharacterFields(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String parentID=null;
		boolean flag=false;
		WebElement ele=null;
		String special="Test@%^$%^%^&%^^%^&^%^&%^dhgf";
		String length="255";
		String a="";
		String[][] labelAndValues= {{"Length",length}};
		ObjectFeatureName objectFeatureName=ObjectFeatureName.pageLayouts;
		
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
				if (sp.addCustomFieldforFormula(environment,mode, object.Contact, ObjectFeatureName.FieldAndRelationShip, "Text", special, labelAndValues, null, null)) {
					flag=true;
					log(LogStatus.INFO, "successfully created new custom field", YesNo.No);
					if (sendKeys(driver, sp.getQuickSearchInObjectManager_Lighting(10),special+Keys.ENTER, "search", action.SCROLLANDBOOLEAN)) {
						a=sp.returnAPINameOfField(projectName, special);
						if (!a.equals(""))
						ExcelUtils.writeData(phase1DataSheetFilePath, a, "FieldComponent", excelLabel.Variable_Name, "M4Field1",excelLabel.APIName);
						else {
							log(LogStatus.FAIL, "could not find api name of "+special, YesNo.Yes);
							sa.assertTrue(false, "could not find api name of "+special);
						
						}
					}else {
						log(LogStatus.FAIL, "could not find api name of "+special, YesNo.Yes);
						sa.assertTrue(false, "could not find api name of "+special);
					
					}
				}
				else {
					log(LogStatus.FAIL, "could not create new field", YesNo.Yes);
					sa.assertTrue(false, "could not create new field");
				
				}
				if (flag) {
				List<String> layoutName = new ArrayList<String>();
				layoutName.add("Contact Layout");
				HashMap<String, String> sourceANDDestination = new HashMap<String, String>();
				sourceANDDestination.put(special,excelLabel.Title.toString());
				List<String> abc = sp.DragNDrop("", mode, object.Contact, objectFeatureName.pageLayouts, layoutName, sourceANDDestination);
				ThreadSleep(10000);
				if (!abc.isEmpty()) {
					log(LogStatus.FAIL, "field not added/already present 1", YesNo.Yes);
					sa.assertTrue(false, "field not added/already present 1");
				}else{
					log(LogStatus.INFO, "field added/already present 1", YesNo.Yes);
				}
				}else {
					log(LogStatus.FAIL, "new field could not be created, so no need to add in page layout", YesNo.Yes);
					sa.assertTrue(false, "new field could not be created, so no need to add in page layout");

				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");

			}
		}
		else {
			log(LogStatus.FAIL, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		
		}
		Actions actions = new Actions(driver);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1,10)) {
				if (ep.clickOnEditPageLink()) {
					log(LogStatus.INFO, "successfully reached edit page", YesNo.No);
					switchToFrame(driver, 30, ep.getEditPageFrame(projectName,30));
					ThreadSleep(10000);	
					ele=ep.clickOnAccordion(projectName, TabName.Object2Tab);
						actions.moveToElement(ele).build().perform();
						ThreadSleep(2000);
						actions.click(ele).perform();
							String query= ep.ContactSDGQuery(a+",");
							switchToDefaultContent(driver);
							if (sendKeys(driver, ep.getFieldTextbox(projectName, EditPageLabel.Query.toString(), 10),query, "query textbox", action.SCROLLANDBOOLEAN)) {
								
							if(click(driver, ep.getCustomTabSaveBtn(projectName, 10), "save button", action.BOOLEAN)) {
								 log(LogStatus.INFO, "clicked on save button", YesNo.No);
								 ThreadSleep(2000);
								 actions.moveToElement(ep.getBackButton(10)).build().perform();
								 ThreadSleep(2000);
								 if(clickUsingJavaScript(driver, ep.getBackButton(10), "back button", action.BOOLEAN)) {
									 log(LogStatus.PASS, "clicked on back button", YesNo.No);
									 flag=true;
								 }else {
									 log(LogStatus.ERROR, "Not able to click on back button", YesNo.Yes);
								 }
							 }else {
							log(LogStatus.ERROR, "Not able to click on save button so cannot edit accordion : ", YesNo.No);
								sa.assertTrue(false, "Not able to click on save button so cannot edit accordion : ");
									
							 }
						}else {
							log(LogStatus.ERROR, "field text box is not visible", YesNo.No);
							sa.assertTrue(false, "field text box is not visible");
								
						 }
				}
				else {
					log(LogStatus.ERROR, "edit page link is not clickable", YesNo.No);
					sa.assertTrue(false, "edit page link is not clickable");
						
				 }
			}else {
				log(LogStatus.ERROR, "could not click on "+M4Ins1, YesNo.No);
				sa.assertTrue(false,  "could not click on "+M4Ins1);
					
			 }
		}else {
			log(LogStatus.ERROR, "entity tab is not clickable", YesNo.No);
			sa.assertTrue(false, "entity tab is not clickable");
				
		 }
						
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M4tc011_2_VerifySpecialCharacterFields(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		String fname="",lname="",ins="",mailID,special="Test@%^$%^%^&%^^%^&^%^&%^dhgf",value="Demo^%^%#^%&^&&^&*^E^#";
		WebElement ele=null;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);

		if (lp.clickOnTab(projectName, TabName.Object2Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Contact1FName+" "+M4Contact1LName,10)) {
				ip.clickOnShowMoreDropdownOnly(projectName, PageName.Object2Page);
				ele = ip.actionDropdownElement(projectName, ShowMoreActionDropDownList.Edit, 10);

				if (click(driver, ele, ShowMoreActionDropDownList.Edit.toString(), action.BOOLEAN)) {
					ele = cp.getContactPageTextBoxOrRichTextBoxWebElement(projectName, special, 30);
					if (ele!=null) {
						if (sendKeys(driver, ele, value, "newly created field", action.SCROLLANDBOOLEAN)) {
							if (click(driver, cp.getSaveButton(projectName, 60), "Save Button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO,"successfully edited Contact : "+M4Contact1FName+" "+M4Contact1LName,YesNo.No);	
							} else {
								sa.assertTrue(false,"Not Able to edit Contact : "+M4Contact1FName+" "+M4Contact1LName);
								log(LogStatus.SKIP,"Not Able to edit Contact: "+M4Contact1FName+" "+M4Contact1LName,YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"textbox is not visible "+special);
							log(LogStatus.SKIP,"textbox is not visible "+special,YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"textbox is not visible "+special);
						log(LogStatus.SKIP,"textbox is not visible "+special,YesNo.Yes);
					}
			} else {
				sa.assertTrue(false,"Not Able to click on edit button : "+M4Contact1FName+" "+M4Contact1LName);
				log(LogStatus.SKIP,"Not Able to click on edit button : "+M4Contact1FName+" "+M4Contact1LName,YesNo.Yes);
			}
			} else {
				sa.assertTrue(false,"Not Able to Click on contact : "+M4Contact1FName+" "+M4Contact1LName);
				log(LogStatus.SKIP,"Not Able to Click on contact : "+M4Contact1FName+" "+M4Contact1LName,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object2Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object2Tab,YesNo.Yes);
		}
		String fieldValue[]={special+breakSP+value};
		
		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object2Tab,YesNo.No);
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1,10)) {
				if (ip.verifyAccordion(projectName, M4Contact1FName+" "+M4Contact1LName, fieldValue,10)) {
					log(LogStatus.INFO, "successfully verified contact in accordion "+M4Contact1FName+" "+M4Contact1LName, YesNo.No);

				}else {
					log(LogStatus.ERROR, "could not verify contact in accordion "+M4Contact1FName+" "+M4Contact1LName, YesNo.Yes);
					sa.assertTrue(false,"could not verify contact in accordion "+M4Contact1FName+" "+M4Contact1LName );
				}
			} else {
				sa.assertTrue(false,"Not Able to Click on entity : "+M4Ins1);
				log(LogStatus.SKIP,"Not Able to Click on entity : "+M4Ins1,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M4tc012_VerifySDGTagAndSDGName(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		WebElement ele=null;
		String parentWindow=null;
		int i = 0;
		String contactHeader=ip.getTabName(projectName,TabName.Object2Tab);
		String fieldLabel[]={SDGCreationLabel.SDG_Name.toString(),SDGCreationLabel.SDG_Tag.toString()};
		String fieldValue[]={M4Sdg1Name,M4Sdg1TagName};
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1,10)) {
				ele=ip.returnAccordionViewDetailsLink(projectName, contactHeader);
				scrollDownThroughWebelement(driver, ele, "view details");
				if (click(driver, ele, "accordion view details", action.SCROLLANDBOOLEAN)) {
					ele=ip.getHeaderTextForPage(projectName, PageName.NewTaskPopUP, contactHeader, action.BOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO, "successfully verified presence of header in accordion", YesNo.No);

					}else {
						log(LogStatus.ERROR, "could not verify presence of header in accordion", YesNo.Yes);
						sa.assertTrue(false,"could not verify presence of header in accordion" );
					}
					ele=ip.accordionSDGButtons(projectName, ip.getTabName(projectName, TabName.Object2Tab),ToggleButtonGroup.SDGButton , action.BOOLEAN, 10);
					if (click(driver, ele, "sdp setup button", action.SCROLLANDBOOLEAN)) {
						parentWindow=switchOnWindow(driver);
						if (parentWindow!=null) {
						for (String label:fieldLabel) {
							label = label.replace("_", " ");
							if (ip.fieldValueVerificationOnInstitutionPage(environment,mode, TabName.Object2Tab, label, fieldValue[i])) {
								log(LogStatus.INFO, "successfully verified presence of "+label+" in "+fieldValue[i], YesNo.No);

							}else {
								log(LogStatus.ERROR, "could not verify field "+label+" with value "+fieldValue[i], YesNo.Yes);
								sa.assertTrue(false,"could not verify field "+label+" with value "+fieldValue[i] );
							}
							i++;
						}
						driver.close();
						driver.switchTo().window(parentWindow);
						}else {
							log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
							sa.assertTrue(false,"could not find new window to switch" );
						}
					}else {
						log(LogStatus.ERROR, "sdg setup button is not clickable", YesNo.Yes);
						sa.assertTrue(false,"sdg setup button is not clickable" );
					}
					
					ele=cp.accordionModalWindowClose(projectName, cp.getTabName(projectName, TabName.Object2Tab));
					click(driver, ele, "close button", action.SCROLLANDBOOLEAN);
				}else {
					log(LogStatus.ERROR, "view details link is not clickable", YesNo.Yes);
					sa.assertTrue(false,"view details link is not clickable" );
				}
			}else {
				sa.assertTrue(false,"Not Able to Click on entity : "+M4Ins1);
				log(LogStatus.SKIP,"Not Able to Click on entity : "+M4Ins1,YesNo.Yes);
			}
		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
		}
	@Parameters({ "projectName"})
	@Test
	public void M4tc013_VerifyRelatedListForWithMinAndMaxRecords(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		WebElement ele=null;
		int i = 0;
		String first="",last="",contact="";
		Actions actions = new Actions(driver);
		String number[]={"0","31","30","1"};
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1,10)) {
				if (ep.clickOnEditPageLink()) {
					log(LogStatus.INFO, "successfully reached edit page", YesNo.No);
					switchToFrame(driver, 30, ep.getEditPageFrame(projectName,30));
					ThreadSleep(10000);	
					/*ele=ep.clickOnAccordion(projectName, TabName.Object2Tab);
					mouseHoverJScript(driver, ele);
					actions.moveToElement(ele).build().perform();
					ThreadSleep(2000);
					//actions.click(ele).perform();
					clickUsingJavaScript(driver, ele, "contacts accordion", action.BOOLEAN);*/
					switchToDefaultContent(driver);
					for (String num:number){
						if (sendKeys(driver, ep.getFieldTextbox(projectName, EditPageLabel.Number_of_Records_to_Display.toString(), 10),num, "query textbox", action.SCROLLANDBOOLEAN)) {

							if(click(driver, ep.getCustomTabSaveBtn(projectName, 10), "save button", action.BOOLEAN)) {
								log(LogStatus.INFO, "clicked on save button", YesNo.No);
								ThreadSleep(2000);
								if ((i==0)||(i==1)) {
									ele=ep.getnoOfRecordsErrorMessage(projectName, 10);
									if (ele!=null) {
										if (ele.getText().trim().equalsIgnoreCase(EditPageErrorMessage.noOfRecordsLimit)) {
											log(LogStatus.PASS, "successfully verified error message for no of records", YesNo.No);

										}else {
											log(LogStatus.ERROR, "Not able to verify error message for no of records", YesNo.Yes);
											sa.assertTrue(false, "Not able to verify error message for no of records");
										}
									}else {
										log(LogStatus.ERROR, "error message for no of records is not visible", YesNo.Yes);
										sa.assertTrue(false, "error message for no of records is not visible");
									}
									
									ele=ep.getnoOfRecordsErrorPopup(projectName, 10);
									if (ele!=null) {
										if (ele.getText().trim().equalsIgnoreCase(EditPageErrorMessage.noOfRecordsError)) {
											log(LogStatus.PASS, "successfully verified error message for no of records", YesNo.No);

										}else {
											log(LogStatus.ERROR, "Not able to verify error message for no of records", YesNo.Yes);
											sa.assertTrue(false, "Not able to verify error message for no of records");
										}
									}else {
										log(LogStatus.ERROR, "error message for no of records is not visible", YesNo.Yes);
										sa.assertTrue(false, "error message for no of records is not visible");
									}
									click(driver, ep.getnoOfRecordsErrorPopupOK(projectName, 10), "ok button", action.BOOLEAN);
								}
								else {
									ele=ep.getnoOfRecordsErrorMessage(projectName, 5);
									if (ele==null) {
										log(LogStatus.PASS, "successfully verified saving of no of records when "+num, YesNo.No);

									}else {
										log(LogStatus.ERROR, "not able to save when no of records is "+num, YesNo.Yes);
										sa.assertTrue(false, "not able to save when no of records is "+num);
									}
									ele=ep.getnoOfRecordsErrorPopup(projectName, 5);
									if (ele==null) {
										log(LogStatus.PASS, "successfully verified saving of no of records when "+num, YesNo.No);

									}else {
										log(LogStatus.ERROR, "not able to save when no of records is "+num, YesNo.Yes);
										sa.assertTrue(false, "not able to save when no of records is "+num);
									}
								}
							}else {
								log(LogStatus.ERROR, "Not able to click on save button so cannot edit accordion : ", YesNo.No);
								sa.assertTrue(false, "Not able to click on save button so cannot edit accordion : ");

							}
						}
						else {
							log(LogStatus.ERROR, "query textbox is not visible", YesNo.No);
							sa.assertTrue(false, "query textbox is not visible");

						}
						i++;
					}
					actions.moveToElement(ep.getBackButton(10)).build().perform();
					ThreadSleep(2000);
					if(clickUsingJavaScript(driver, ep.getBackButton(10), "back button", action.BOOLEAN)) {
						log(LogStatus.PASS, "clicked on back button", YesNo.No);
					}else {
						log(LogStatus.ERROR, "Not able to click on back button", YesNo.Yes);
						sa.assertTrue(false,"Not able to click on back button" );
						
					}
					
					for (int j = 0;j<2;j++) {
						first=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON"+(j+1), excelLabel.Contact_FirstName);
						last=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON"+(j+1), excelLabel.Contact_LastName);
						contact=first+" "+last;
						if (j==1) {
							if (!ip.verifyAccordion(projectName, contact, null,5)) {
								log(LogStatus.INFO, "successfully verified absence of 2nd contact in accordion", YesNo.No);

							}else {
								log(LogStatus.ERROR, "2nd contact is present but it should not be", YesNo.Yes);
								sa.assertTrue(false,"2nd contact is present but it should not be" );
							}
						}
						else {

							if (ip.verifyAccordion(projectName, contact, null,10)) {
								log(LogStatus.INFO, "successfully verified "+(j+1)+"th contact in accordion", YesNo.No);

							}else {
								log(LogStatus.ERROR, "could not verify "+(j+1)+"th contact in accordion", YesNo.Yes);
								sa.assertTrue(false,"could not verify "+(j+1)+"th contact in accordion" );
							}
						}
					}
				}else {
					log(LogStatus.ERROR, "edit page link is not clickable", YesNo.No);
					sa.assertTrue(false, "edit page link is not clickable");

				}
			}else {
				log(LogStatus.ERROR, "could not click on ins: "+M4Ins1, YesNo.No);
				sa.assertTrue(false, "could not click on ins: "+M4Ins1);

			}
		}else {
			log(LogStatus.ERROR, "could not click on entity tab", YesNo.No);
			sa.assertTrue(false, "could not click on entity tab");

		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M4tc014_AddRelatedListAccordianOnDeal(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String tab=M4Sdg2Name;
		String fieldValues[]={EditPageLabel.Title.toString()+"<break>"+tab,EditPageLabel.Query.toString()+"<break>"+ep.DealTeamSDGQuery(""),
				EditPageLabel.Image_Field_API_Name.toString()+"<break>"+"Member__r.MediumPhotoURL",EditPageLabel.Number_of_Records_to_Display.toString()+"<break>8",
				EditPageLabel.SDG_Name.toString()+"<break>"+tab,EditPageLabel.Popup_Title.toString()+"<break>"+tab
		};
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Deal1, 10)) {
				if (ep.clickOnEditPageLink()) {
					log(LogStatus.INFO, "successfully reached edit page", YesNo.No);
					
					if (ep.dragAndDropAccordian(projectName, PageName.Object1Page, "RelatedListAccordion", fieldValues)) {
						log(LogStatus.INFO, "successfully added accordion on entity page", YesNo.No);
							
					}else {
						log(LogStatus.ERROR, "could not drop accordion on entity page", YesNo.Yes);
						sa.assertTrue(false,"could not drop accordion on entity page" );
					}
				}else {
					log(LogStatus.ERROR, "Not able to open edit page, so cannot add accordion", YesNo.Yes);
					sa.assertTrue(false,"Not able to open edit page, so cannot add accordion" );
				}
			}else {
				log(LogStatus.ERROR, "Not able to go to entity record "+M4Ins1, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to entity record "+M4Ins1 );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on entity tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on entity tab" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	@Parameters({ "projectName"})
	@Test
	public void M4tc015_VerifyAccordionOnDealPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		String id=null;
		WebElement ele=null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String user=ExcelUtils.readData(phase1DataSheetFilePath,"Deal Team",excelLabel.Variable_Name, "M4DT1", excelLabel.Member);
		String type=ExcelUtils.readData(phase1DataSheetFilePath,"Deal Team",excelLabel.Variable_Name, "M4DT1", excelLabel.Type);
		String role=ExcelUtils.readData(phase1DataSheetFilePath,"Deal Team",excelLabel.Variable_Name, "M4DT1", excelLabel.Role);

		String fieldValue[]={PageLabel.Team_Member_Role.toString()+breakSP+M4Contact1Title,PageLabel.Type.toString()+breakSP+M4Contact1Title};
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Deal1,10)) {
				for (int i = 0;i<3;i++) {
					user=ExcelUtils.readData(phase1DataSheetFilePath,"Deal Team",excelLabel.Variable_Name, "M4DT"+(i+1), excelLabel.Member);
					type=ExcelUtils.readData(phase1DataSheetFilePath,"Deal Team",excelLabel.Variable_Name, "M4DT"+(i+1), excelLabel.Type);
					role=ExcelUtils.readData(phase1DataSheetFilePath,"Deal Team",excelLabel.Variable_Name, "M4DT"+(i+1), excelLabel.Role);
					fieldValue[0]=PageLabel.Team_Member_Role.toString()+breakSP+role;
					fieldValue[1]=PageLabel.Type.toString()+breakSP+type;
					if (ip.verifyAccordion(projectName, user, fieldValue,10)) {
						log(LogStatus.INFO, "successfully verified fields and values in accordion", YesNo.No);

					}else {
						log(LogStatus.ERROR, "could not verify fields and values in accordion", YesNo.Yes);
						sa.assertTrue(false,"could not verify fields and values in accordion" );
					}
					/*if (ip.verifyAccordianRecordImage(projectName, user, BasePageErrorMessage.defaultPhotoText)) {
						log(LogStatus.INFO, "successfully verified update photo in accordion", YesNo.No);

					}else {
						log(LogStatus.ERROR, "could not verify update photo in accordion", YesNo.Yes);
						sa.assertTrue(false,"could not verify update photo in accordion" );
					}*/
				}
			}else {
				log(LogStatus.ERROR, "Not able to go to entity record "+M4Ins1, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to entity record "+M4Ins1 );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on entity tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on entity tab" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M4tc016_UpdateImageOnUserProfile(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		DealPageBusinessLayer dp =new DealPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		String id=null,parentID=null;
		String attachmentPath1= System.getProperty("user.dir")+"\\UploadFiles\\Module 4\\tc7\\1.jpg";
		String attachmentPath2= System.getProperty("user.dir")+"\\UploadFiles\\Module 4\\tc7\\2.jpg";
		WebElement ele=null;
		String user=AdminUserFirstName+" "+AdminUserLastName;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName,M4Deal1 , 10)) {
				ele=cp.getRelatedTab(projectName, RelatedTab.Overview.toString(), 10);
				click(driver, ele, "overview tab", action.BOOLEAN);
				ele=dp.returnAccordionLink(projectName, user);
				if (click(driver, ele, "accordion header", action.BOOLEAN)) {
					parentID=switchOnWindow(driver);
					if (parentID!=null) {
						ThreadSleep(3000);
						id=dp.updatePhotoInUserPage(projectName, attachmentPath1);
						if (id!=null) {
							log(LogStatus.INFO, "successfully updated photo", YesNo.No);

						}else {
							log(LogStatus.ERROR, "could not update photo", YesNo.Yes);
							sa.assertTrue(false,"could not update photo" );
						}
						driver.close();
						driver.switchTo().window(parentID);
					}else {
						log(LogStatus.ERROR, "could not find new window to switch, so cannot update admin photo", YesNo.Yes);
						sa.assertTrue(false,"could not find new window to switch, so cannot update admin photo" );
					}
					
					
				}else {
					log(LogStatus.ERROR, "could not click on accordion header, so cannot update admin photo", YesNo.Yes);
					sa.assertTrue(false,"could not click on accordion header, so cannot update admin photo" );
				}
				refresh(driver);
				ThreadSleep(3000);
				if (ip.verifyAccordianRecordImage(projectName, user, id)) {
					log(LogStatus.INFO, "successfully verified update photo in accordion", YesNo.No);
					
				}else {
					log(LogStatus.ERROR, "could not verify update photo in accordion", YesNo.Yes);
					sa.assertTrue(false,"could not verify update photo in accordion" );
				}
				refresh(driver);
				ThreadSleep(3000);
				
				ele=dp.returnAccordionLink(projectName, user);
				if (click(driver, ele, "accordion header", action.BOOLEAN)) {
					parentID=switchOnWindow(driver);
					if (parentID!=null) {
						ThreadSleep(3000);
						id=dp.updatePhotoInUserPage(projectName, attachmentPath2);
						if (id!=null) {
							log(LogStatus.INFO, "successfully updated photo", YesNo.No);

						}else {
							log(LogStatus.ERROR, "could not update photo", YesNo.Yes);
							sa.assertTrue(false,"could not update photo" );
						}
						driver.close();
						driver.switchTo().window(parentID);
					}else {
						log(LogStatus.ERROR, "could not find new window to switch, so cannot update admin photo", YesNo.Yes);
						sa.assertTrue(false,"could not find new window to switch, so cannot update admin photo" );
					}
					
					
				}else {
					log(LogStatus.ERROR, "could not click on accordion header, so cannot update admin photo", YesNo.Yes);
					sa.assertTrue(false,"could not click on accordion header, so cannot update admin photo" );
				}
				refresh(driver);
				ThreadSleep(3000);
				
				if (ip.verifyAccordianRecordImage(projectName, user, id)) {
					log(LogStatus.INFO, "successfully verified update photo in accordion", YesNo.No);

				}else {
					log(LogStatus.ERROR, "could not verify update photo in accordion", YesNo.Yes);
					sa.assertTrue(false,"could not verify update photo in accordion" );
				}
			}else {
				log(LogStatus.ERROR, "Not able to go to deal record "+M4Deal1, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to deal record "+M4Deal1 );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on deal tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on deal tab" );
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M4tc017_VerifyNoOfRecordsOnDealPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		DealPageBusinessLayer dp =new DealPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		String id=null,parentID=null;
		WebElement ele=null;
		String user=AdminUserFirstName+" "+AdminUserLastName;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName,M4Deal1 , 10)) {
				ele=cp.getRelatedTab(projectName, RelatedTab.Overview.toString(), 10);
				String xpath = "//article[@class='cRelatedListAccordion']//a[text()='"+user+"']";
				List<WebElement> li=FindElements(driver, xpath, "list of deal team records");
				if (li.size()==8) {
					log(LogStatus.INFO, "successfully verified 8 records found", YesNo.No);

				}else {
					log(LogStatus.ERROR, "could not verify presence of 8 records. found: "+li.size(), YesNo.Yes);
					sa.assertTrue(false,"could not verify presence of 8 records. found: "+li.size() );
				}
			}else {
				log(LogStatus.ERROR, "Not able to go to entity record "+M4Ins1, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to entity record "+M4Ins1 );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on entity tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on entity tab" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	@Parameters({ "projectName"})
	@Test
	public void M4tc018_VerifyExpandCollapseOnDealAccordion(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		String id=null;
		WebElement ele=null;
		String user=AdminUserFirstName+" "+AdminUserLastName;
		String dealteamHeader=ip.getTabName(projectName,TabName.Deal_Team);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Deal1,10)) {
				ele=ip.returnAccordionViewDetailsLink(projectName, dealteamHeader);
				if (click(driver, ele, "accordion view details", action.SCROLLANDBOOLEAN)) {
					ele=ip.getHeaderTextForPage(projectName, PageName.NewTaskPopUP, dealteamHeader, action.BOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO, "successfully verified presence of header in accordion", YesNo.No);

					}else {
						log(LogStatus.ERROR, "could not verify presence of header in accordion", YesNo.Yes);
						sa.assertTrue(false,"could not verify presence of header in accordion" );
					}
					ele=ip.accordionExpandCollapse(projectName, ExpandCollapse.Collapse, 10);
					
					if (ele!=null) {
						log(LogStatus.INFO, "verified default is expanded sdg", YesNo.No);

						if (click(driver, ele, "collapse icon", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on collapse link", YesNo.No);

							ele=ip.accordionExpandCollapse(projectName, ExpandCollapse.Collapse,2);
							if (ele==null)
								log(LogStatus.INFO, "successfully verified collapsed sdg", YesNo.No);
							else {
								log(LogStatus.ERROR, "could not verify sdg collapse", YesNo.Yes);
								sa.assertTrue(false,"could not verify sdg collapse" );
							}
						}else {
							log(LogStatus.ERROR, "Collapse icon is not clickable", YesNo.Yes);
							sa.assertTrue(false,"Collapse icon is not clickable" );
						}
					}else {
						log(LogStatus.ERROR, "Collapse icon is not visible, so cannot verify collapse functionality", YesNo.Yes);
						sa.assertTrue(false,"Collapse icon is not visible, so cannot verify collapse functionality" );
					}
					click(driver, ip.accordionModalWindowClose(projectName, dealteamHeader),"cross icon", action.BOOLEAN);
				}else {
					log(LogStatus.ERROR, "accordion link is not clickable", YesNo.Yes);
					sa.assertTrue(false,"accordion link is not clickable" );
				}
			}else {
				log(LogStatus.ERROR, "Not able to go to deal record "+M4Deal1, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to deal record "+M4Deal1 );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on deal tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on deal tab" );
		}
		lp.CRMlogout();
		ThreadSleep(3000);
		driver.close();
		
		config(browserToLaunch);
		lp = new LoginPageBusinessLayer(driver);
		fp = new FundsPageBusinessLayer(driver);
		cp = new ContactsPageBusinessLayer(driver);
		ip = new InstitutionsPageBusinessLayer(driver);
		
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Deal1,10)) {
				ele=ip.returnAccordionViewDetailsLink(projectName, dealteamHeader);
				if (click(driver, ele, "accordion view details", action.SCROLLANDBOOLEAN)) {
					ele=ip.getHeaderTextForPage(projectName, PageName.NewTaskPopUP, dealteamHeader, action.BOOLEAN, 10);
					if (ele!=null) {
						log(LogStatus.INFO, "successfully verified presence of header in accordion", YesNo.No);

					}else {
						log(LogStatus.ERROR, "could not verify presence of header in accordion", YesNo.Yes);
						sa.assertTrue(false,"could not verify presence of header in accordion" );
					}
					ele=ip.accordionExpandCollapse(projectName, ExpandCollapse.Collapse, 2);
					
					if (ele==null) {
						log(LogStatus.INFO, "verified sdg is collapsed", YesNo.No);

							ele=ip.accordionExpandCollapse(projectName, ExpandCollapse.Expand,2);
							if (ele!=null)
								log(LogStatus.INFO, "successfully verified collapsed sdg", YesNo.No);
							else {
								log(LogStatus.ERROR, "could not verify sdg collapse", YesNo.Yes);
								sa.assertTrue(false,"could not verify sdg collapse" );
							}
					}else {
						log(LogStatus.ERROR, "Collapse icon is visible, but it should not be", YesNo.Yes);
						sa.assertTrue(false,"Collapse icon is visible, but it should not be" );
					}
					click(driver, ip.accordionModalWindowClose(projectName, dealteamHeader),"cross icon", action.BOOLEAN);
				}else {
					log(LogStatus.ERROR, "accordion link is not clickable", YesNo.Yes);
					sa.assertTrue(false,"accordion link is not clickable" );
				}
			}else {
				log(LogStatus.ERROR, "Not able to go to deal record "+M4Deal1, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to deal record "+M4Deal1 );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on deal tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on deal tab" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M4tc019_AddRelatedListAccordianOnMarketingEvent(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String tab=M4Sdg3Name;
		String fieldValues[]={EditPageLabel.Title.toString()+"<break>"+tab,EditPageLabel.Query.toString()+"<break>"+ep.attendeeQuery,
				EditPageLabel.Image_Field_API_Name.toString()+"<break>"+"Member__r.MediumPhotoURL",EditPageLabel.Number_of_Records_to_Display.toString()+"<break>8",
				EditPageLabel.SDG_Name.toString()+"<break>"+tab,EditPageLabel.Popup_Title.toString()+"<break>"+tab
		};
		if (ip.clickOnTab(projectName, TabName.Object5Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4MarketingEvent1Name, 10)) {
				if (ep.clickOnEditPageLink()) {
					log(LogStatus.INFO, "successfully reached edit page", YesNo.No);
					
					if (ep.dragAndDropAccordian(projectName, PageName.Object1Page, "RelatedListAccordion", fieldValues)) {
						log(LogStatus.INFO, "successfully added accordion on entity page", YesNo.No);
							
					}else {
						log(LogStatus.ERROR, "could not drop accordion on entity page", YesNo.Yes);
						sa.assertTrue(false,"could not drop accordion on entity page" );
					}
				}else {
					log(LogStatus.ERROR, "Not able to open edit page, so cannot add accordion", YesNo.Yes);
					sa.assertTrue(false,"Not able to open edit page, so cannot add accordion" );
				}
			}else {
				log(LogStatus.ERROR, "Not able to go to entity record "+M4Ins1, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to entity record "+M4Ins1 );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on entity tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on entity tab" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}
}
