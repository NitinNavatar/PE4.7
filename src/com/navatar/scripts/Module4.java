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
	Scanner sc = new Scanner(System.in);
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
		for (i = 0;i<9;i++) {
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
				ExcelUtils.writeData(phase1DataSheetFilePath, id, "FilePath", excelLabel.TestCases_Name, "M4tc016_UpdateImageOnUserProfile",excelLabel.URL);
				
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
				EditPageLabel.Image_Field_API_Name.toString()+"<break>"+"Attendee_Staff__r.MediumPhotoURL",EditPageLabel.Number_of_Records_to_Display.toString()+"<break>8",
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
	
	@Parameters({ "projectName"})
	@Test
	public void M4tc020_VerifyAccordionOnEventPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		String id=null;
		WebElement ele=null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String user="";
		String status1="";
		id=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name, "M4tc016_UpdateImageOnUserProfile", excelLabel.URL);
		
		String fieldValue[]=new String[1];
		if (ip.clickOnTab(projectName, TabName.Object5Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4MarketingEvent1Name,10)) {
				for (int i = 0;i<6;i++) {
					user=ExcelUtils.readData(phase1DataSheetFilePath,"Attendees",excelLabel.Variable_Name, "M4Att"+(i+1), excelLabel.Attendee_Staff);
					status1=ExcelUtils.readData(phase1DataSheetFilePath,"Attendees",excelLabel.Variable_Name, "M4Att"+(i+1), excelLabel.Status);
					fieldValue[0]=PageLabel.Status.toString()+breakSP+status1;
					if (ip.verifyAccordion(projectName, user, fieldValue,10)) {
						log(LogStatus.INFO, "successfully verified fields and values in accordion", YesNo.No);

					}else {
						log(LogStatus.ERROR, "could not verify fields and values in accordion", YesNo.Yes);
						sa.assertTrue(false,"could not verify fields and values in accordion" );
					}
					if (ip.verifyAccordianRecordImage(projectName, user, id)) {
						log(LogStatus.INFO, "successfully verified update photo in accordion", YesNo.No);

					}else {
						log(LogStatus.ERROR, "could not verify update photo in accordion", YesNo.Yes);
						sa.assertTrue(false,"could not verify update photo in accordion" );
					}
				}
			}else {
				log(LogStatus.ERROR, "Not able to go to event record "+M4MarketingEvent1Name, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to event record "+M4MarketingEvent1Name );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on event tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on event tab" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M4tc021_UpdateImageOnUserProfileAndCheckEventAccordion(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		DealPageBusinessLayer dp =new DealPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		String id=null,parentID=null;
		String attachmentPath1= System.getProperty("user.dir")+"\\UploadFiles\\Module 4\\tc20\\3.png";
		String attachmentPath2= System.getProperty("user.dir")+"\\UploadFiles\\Module 4\\tc20\\4.png";
		WebElement ele=null;
		String user=AdminUserFirstName+" "+AdminUserLastName;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object5Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName,M4MarketingEvent1Name , 10)) {
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
				ExcelUtils.writeData(phase1DataSheetFilePath, id, "FilePath", excelLabel.TestCases_Name, "M4tc016_UpdateImageOnUserProfile",excelLabel.URL);
				
				if (ip.verifyAccordianRecordImage(projectName, user, id)) {
					log(LogStatus.INFO, "successfully verified update photo in accordion", YesNo.No);

				}else {
					log(LogStatus.ERROR, "could not verify update photo in accordion", YesNo.Yes);
					sa.assertTrue(false,"could not verify update photo in accordion" );
				}
			}else {
				log(LogStatus.ERROR, "Not able to go to event record "+M4MarketingEvent1Name, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to event record "+M4MarketingEvent1Name );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on event tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on event tab" );
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M4tc022_VerifyNoOfRecordsOnEventPage(String projectName) {
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
		if (ip.clickOnTab(projectName, TabName.Object5Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName,M4MarketingEvent1Name , 10)) {
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
				log(LogStatus.ERROR, "Not able to go to event record "+M4MarketingEvent1Name, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to event record "+M4MarketingEvent1Name );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on event tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on event tab" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	@Parameters({ "projectName"})
	@Test
	public void M4tc023_VerifyExpandCollapseOnEventAccordion(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		CustomObjPageBusinessLayer cop= new CustomObjPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		String id=null;
		WebElement ele=null;
		String user=AdminUserFirstName+" "+AdminUserLastName;
		String dealteamHeader=AttendeeLabels.Attendee.toString();
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object5Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4MarketingEvent1Name,10)) {
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
				log(LogStatus.ERROR, "Not able to go to event record "+M4MarketingEvent1Name, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to event record "+M4MarketingEvent1Name );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on event tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on event tab" );
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
		if (ip.clickOnTab(projectName, TabName.Object5Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4MarketingEvent1Name,10)) {
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
				log(LogStatus.ERROR, "Not able to go to event record "+M4MarketingEvent1Name, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to event record "+M4MarketingEvent1Name );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on event tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on event tab" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	@Parameters({ "projectName"})
	@Test
	public void M4tc024_Add3RelatedListAccordianOnEntityPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		WebElement ele=null;
		String user=AdminUserFirstName+" "+AdminUserLastName;
		String tab[] = {tabObj5,tabObj4,M4Sdg2Name};
		String record[]={M4MarketingEvent1Name,M4Deal1,user};
		String query[]={EditPageErrorMessage.query1,EditPageErrorMessage.query2,EditPageErrorMessage.query3};
		String image[]={EditPageErrorMessage.image1,EditPageErrorMessage.image2,EditPageErrorMessage.image3};
		String fieldValues[]= new String[3];
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1, 10)) {
				for (int i = 0;i<3;i++) {
					fieldValues[0]=EditPageLabel.Title.toString()+breakSP+tab[i];
					fieldValues[1]=EditPageLabel.Query.toString()+breakSP+query[i];
					fieldValues[2]=EditPageLabel.Image_Field_API_Name.toString()+breakSP+image[i];
					if (ep.clickOnEditPageLink()) {
						log(LogStatus.INFO, "successfully reached edit page", YesNo.No);

						if (ep.dragAndDropAccordian(projectName, PageName.Object1Page, "RelatedListAccordion", fieldValues)) {
							log(LogStatus.INFO, "successfully added "+tab[i]+" accordion on entity page", YesNo.No);

						}else {
							log(LogStatus.ERROR, "could not drop "+tab[i]+" accordion on entity page", YesNo.Yes);
							sa.assertTrue(false,"could not drop "+tab[i]+" accordion on entity page" );
						}
					}else {
						log(LogStatus.ERROR, "Not able to open edit page, so cannot add accordion", YesNo.Yes);
						sa.assertTrue(false,"Not able to open edit page, so cannot add accordion" );
					}
					ThreadSleep(4000);
				}
			}else {
				log(LogStatus.ERROR, "Not able to go to entity record "+M4Ins1, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to entity record "+M4Ins1 );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on entity tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on entity tab" );
		}
		String imgId=null;
		TabName tname[]={TabName.Object5Tab,TabName.Object4Tab};
		for (int i = 0;i<2;i++) {
			String src=null;
			if (ip.clickOnTab(projectName, tname[i])) {
				if (ip.clickOnAlreadyCreatedItem(projectName, record[i], 10)) {
					ele=ip.getImgForObject(tab[i], 10);
					if (ele!=null) {
						src=ele.getAttribute("src");
						if (src!=null) {
							log(LogStatus.INFO, "successfully retrieved "+src+" from photo", YesNo.No);

						}else {
							log(LogStatus.ERROR, "could not retrieve "+src+" from photo", YesNo.Yes);
							sa.assertTrue(false,"could not retrieve "+src+" from photo");
						}
					}else {
						log(LogStatus.ERROR, "could not retrieve src from photo", YesNo.Yes);
						sa.assertTrue(false,"could not retrieve src from photo");
					}
				}else {
					log(LogStatus.ERROR, "Not able to go to event record "+record[i], YesNo.Yes);
					sa.assertTrue(false,"Not able to go to event record "+record[i] );
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on event tab", YesNo.Yes);
				sa.assertTrue(false,"Not able to click on event tab" );
			}
			if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
				if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1, 10)) {
					ele=ip.getCustomNumberOfImg(tab[i], record[i], 10);
					String customNo=null;
					if (ele!=null) {
						customNo=ele.getAttribute("data-key");
						if (src.contains(customNo)) {
							log(LogStatus.INFO, "successfully verified "+customNo+" matches photo", YesNo.No);

						}else {
							log(LogStatus.ERROR, "not matched "+customNo+" in "+src, YesNo.Yes);
							sa.assertTrue(false,"not matched "+customNo+" in "+src);
						}
					}else {
						log(LogStatus.ERROR, "could not get number of image on accordion", YesNo.Yes);
						sa.assertTrue(false,"could not get number of image on accordion");
					}
					if (i==1) {
						ele=dp.returnAccordionLink(projectName, user);
						if (click(driver, ele, "accordion header", action.BOOLEAN)) {
							String parentID=switchOnWindow(driver);
							if (parentID!=null) {
								ThreadSleep(3000);
								imgId=dp.getimgIconForPath(projectName, 10).getAttribute("src");
								if (imgId!=null){
									log(LogStatus.INFO, "found id of img uploaded: "+imgId, YesNo.Yes);

								}
								else {
									log(LogStatus.ERROR, "could not find id of img uploaded", YesNo.Yes);
									sa.assertTrue(false, "could not find id of img uploaded");

								}
								driver.close();
								driver.switchTo().window(parentID);
							}else {
								log(LogStatus.ERROR, "could not find new window to switch, so cannot update admin photo", YesNo.Yes);
								sa.assertTrue(false,"could not find new window to switch, so cannot update admin photo" );
							}
							if (ip.verifyAccordianRecordImage(projectName, user, imgId)) {
								log(LogStatus.INFO, "successfully verified "+imgId+" for image on Deal team accordion for "+user, YesNo.Yes);

							}
							else {
								log(LogStatus.ERROR, "could not verify "+imgId+" for image on Deal team accordion for "+user, YesNo.Yes);
								sa.assertTrue(false,"could not verify "+imgId+" for image on Deal team accordion for "+user );
							}

						}else {
							log(LogStatus.ERROR, "could not click on accordion header, so cannot update admin photo", YesNo.Yes);
							sa.assertTrue(false,"could not click on accordion header, so cannot update admin photo" );
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
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	@Parameters({ "projectName"})
	@Test
	public void M4tc025_1_Precondition(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		WebElement ele=null;
		String parentWindow=null;
		String contactHeader=ip.getTabName(projectName,TabName.Object2Tab);
		String fieldValues[]={SDGActionsCreationLabel.Name.toString()+breakSP+M4SDGAction2Name,SDGActionsCreationLabel.Action_Order.toString()+breakSP+M4SDGAction2Order,
				SDGActionsCreationLabel.Event.toString()+breakSP+M4SDGAction2Event,SDGActionsCreationLabel.Event_Payload.toString()+breakSP+M4SDGAction2EventPayload,
				SDGActionsCreationLabel.Action_Type+breakSP+M4SDGAction2Type
				};
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1, 10)) {

				ele=ip.returnAccordionViewDetailsLink(projectName, contactHeader);
				if (click(driver, ele, "accordion view details", action.SCROLLANDBOOLEAN)) {
					ele=ip.accordionSDGButtons(projectName, ip.getTabName(projectName, TabName.Object2Tab),ToggleButtonGroup.SDGButton , action.BOOLEAN, 10);
					if (click(driver, ele, "sdp setup button", action.SCROLLANDBOOLEAN)) {
						parentWindow=switchOnWindow(driver);
						if (parentWindow!=null) {
							ele=ip.getRelatedTab(projectName, RelatedTab.Related.toString(), 10);
							if (click(driver, ele, "related", action.BOOLEAN)) {
								ele=ip.sdgButtons(projectName, SDGLabels.Actions.toString(), "New", 10);
								if(click(driver,ele, "new action",  action.SCROLLANDBOOLEAN)) {
									ip.createSDGAction(projectName, fieldValues);
									ThreadSleep(5000);
									click(driver, ip.getactionsSDGRefresh(projectName, 10), "refresh", action.SCROLLANDBOOLEAN);
									if (sdg.getSDGValue(projectName, "Actions",M4SDGAction2Name , 10)!=null) {
										log(LogStatus.INFO,"successfully verified new actions link on sdg",YesNo.No);

									}else {
										log(LogStatus.SKIP,"could not verify new action created",YesNo.Yes);
										sa.assertTrue(false, "could not verify new action created");
									}
								}else {
									log(LogStatus.SKIP,"new actions button is not cilckable",YesNo.Yes);
									sa.assertTrue(false, "new actions button is not cilckable");
								}
							}else {
								log(LogStatus.SKIP,"related tab is not clickable",YesNo.Yes);
								sa.assertTrue(false, "related tab is not clickable");
							}
							driver.close();
							driver.switchTo().window(parentWindow);
						}else {
							log(LogStatus.SKIP,"not found new window to switch",YesNo.Yes);
							sa.assertTrue(false, "not found new window to switch");
						}
					}else {
						log(LogStatus.SKIP,"sdg setup btton is not clickable",YesNo.Yes);
						sa.assertTrue(false, "sdg setup btton is not clickable");
					}
				}else {
					log(LogStatus.SKIP,"view details link is not clickable",YesNo.Yes);
					sa.assertTrue(false, "view details link is not clickable");
				}
			}else {
				log(LogStatus.SKIP,M4Ins1+" entity is not found on entity tab",YesNo.Yes);
				sa.assertTrue(false, M4Ins1+" entity is not found on entity tab");
			}
		}else {
			log(LogStatus.SKIP,"entity tab is not clickable",YesNo.Yes);
			sa.assertTrue(false, "entity tab is not clickable");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	@Parameters({ "projectName"})
	@Test
	public void M4tc025_2_VerifyAddingContactImageInSDG(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele=null;
		String contactHeader=ip.getTabName(projectName,TabName.Object2Tab);
		String parentID=null;
		int i = 0;
		String fields[]={excelLabel.Name.toString()};
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1, 10)) {
				ele=ip.returnAccordionViewDetailsLink(projectName, contactHeader);
				if (click(driver, ele, "view details link", action.SCROLLANDBOOLEAN)) {
					ele=ip.accordionSDGButtons(projectName, ip.getTabName(projectName, TabName.Object2Tab),ToggleButtonGroup.SDGButton , action.BOOLEAN, 10);
					if (click(driver, ele, "sdp setup button", action.SCROLLANDBOOLEAN)) {
						parentID=switchOnWindow(driver);
						if (parentID!=null) {
							ele=ip.getRelatedTab(projectName, RelatedTab.Related.toString(), 10);
							if (click(driver, ele, "related tab", action.BOOLEAN)) {
							for (String field:fields) {
									ele=ip.sdgButtons(projectName,fields[i], ShowMoreActionDropDownList.Edit.toString(), 10);
									if (click(driver, ele, "edit", action.SCROLLANDBOOLEAN)) {
										ele=sdg.getLabelTextBox(projectName, PageName.SDGPage.toString(),SDGCreationLabel.Image_Field_API.toString(), 10);
										if (sendKeys(driver, ele, EditPageErrorMessage.image1, "image field api", action.SCROLLANDBOOLEAN)) {


											if (click(driver, sdg.getRecordPageSettingSave(10), "save", action.BOOLEAN)) {
												log(LogStatus.INFO,"successfully clicked on save button",YesNo.Yes);
											}
											else {
												log(LogStatus.SKIP,"could not click on save button, so could not add "+field,YesNo.Yes);
												sa.assertTrue(false, "could not click on save button, so could not add "+field);
											}
										}else {
											log(LogStatus.SKIP,"image field api field is not visible",YesNo.Yes);
											sa.assertTrue(false, "image field api field is not visible");
										}
									}else {
										log(LogStatus.SKIP,"edit button is not clickable for "+field,YesNo.Yes);
										sa.assertTrue(false, "edit button is not clickable for "+field);
									}
									i++;
							}
								}else {
									log(LogStatus.SKIP,"related tab is not clickable",YesNo.Yes);
									sa.assertTrue(false, "related tab is not clickable");
								}
							driver.close();
							driver.switchTo().window(parentID);
						}else {
							log(LogStatus.SKIP,"could not find new window to switch, so cannot edit field",YesNo.Yes);
							sa.assertTrue(false, "could not find new window to switch, so cannot edit field");
						}
					}else {
						log(LogStatus.SKIP,"setup button on sdg is not clickable",YesNo.Yes);
						sa.assertTrue(false, "setup button on sdg is not clickable");
					}
					refresh(driver);
					ele=ip.returnAccordionViewDetailsLink(projectName,contactHeader );
					click(driver, ele, "view details", action.SCROLLANDBOOLEAN);
					ThreadSleep(3000);
					String first="",last="",contact="";
					for (i = 0;i<6;i++) {
						first=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON"+(i+1), excelLabel.Contact_FirstName);
						last=ExcelUtils.readData(phase1DataSheetFilePath,"Contacts",excelLabel.Variable_Name, "M4CON"+(i+1), excelLabel.Contact_LastName);
						contact=first+" "+last;
						ele=ip.sdgContactImage(projectName, contact, 10);
						if (ele!=null) {

							if (ele.getAttribute("src").contains(BasePageErrorMessage.defaultPhotoText)) {
								log(LogStatus.INFO,"successfully verified photo of contact "+contact,YesNo.Yes);

							}
							else {
								log(LogStatus.SKIP,"could not verify picture in front of "+contact,YesNo.Yes);
								sa.assertTrue(false, "could not verify picture in front of "+contact);
							}
						}else {
							log(LogStatus.SKIP,"picture not present in front of "+contact,YesNo.Yes);
							sa.assertTrue(false, "picture not present in front of "+contact);
						}
					}
				}else {
					log(LogStatus.SKIP,"view details link is not clickable",YesNo.Yes);
					sa.assertTrue(false, "view details link is not clickable");
				}
			}else {
				log(LogStatus.SKIP,"could not click on entity record "+M4Ins1,YesNo.Yes);
				sa.assertTrue(false, "could not click on entity record "+M4Ins1);
			}
		}else {
			log(LogStatus.SKIP,"entity tab is not clickable",YesNo.Yes);
			sa.assertTrue(false, "entity tab is not clickable");
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M4tc026_VerifyChangingContactImageInSDG(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		String contactHeader=ip.getTabName(projectName,TabName.Object2Tab);
		
		String contact=M4Contact2FName+" "+M4Contact2LName;
		String attachmentPath1= System.getProperty("user.dir")+"\\UploadFiles\\Module 4\\tc7\\1.jpg";
		String attachmentPath2= System.getProperty("user.dir")+"\\UploadFiles\\Module 4\\tc7\\2.jpg";
		String id=null;
		WebElement ele=null;
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
				ele=ip.returnAccordionViewDetailsLink(projectName, contactHeader);
				if (click(driver, ele, "view details link", action.SCROLLANDBOOLEAN)) {
				
					ele=ip.sdgContactImage(projectName, contact, 10);
					if (ele!=null) {

						if (ele.getAttribute("src").contains(id)) {
							log(LogStatus.INFO,"successfully verified photo of contact "+contact,YesNo.Yes);

						}
						else {
							log(LogStatus.SKIP,"could not verify picture in front of "+contact,YesNo.Yes);
							sa.assertTrue(false, "could not verify picture in front of "+contact);
						}
					}else {
						log(LogStatus.SKIP,"picture not present in front of "+contact,YesNo.Yes);
						sa.assertTrue(false, "picture not present in front of "+contact);
					}
				click(driver, ip.accordionModalWindowClose(projectName, cp.getTabName(projectName, TabName.Object2Tab)), "cross", action.BOOLEAN);
				}else {
					log(LogStatus.SKIP,"view details link is not clickable",YesNo.Yes);
					sa.assertTrue(false, "view details link is not clickable");
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
			if (ip.clickOnAlreadyCreatedItem(projectName, contact, 10)) {
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
				log(LogStatus.ERROR, "Not able to go to contact record "+contact, YesNo.Yes);
				sa.assertTrue(false,"Not able to go to contact record "+contact );
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on contact tab", YesNo.Yes);
			sa.assertTrue(false,"Not able to click on contact tab" );
		}
		
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1,10)) {
				ele=ip.returnAccordionViewDetailsLink(projectName, contactHeader);
				if (click(driver, ele, "view details link", action.SCROLLANDBOOLEAN)) {
				
					ele=ip.sdgContactImage(projectName, contact, 10);
					if (ele!=null) {

						if (ele.getAttribute("src").contains(id)) {
							log(LogStatus.INFO,"successfully verified photo of contact "+contact,YesNo.Yes);

						}
						else {
							log(LogStatus.SKIP,"could not verify picture in front of "+contact,YesNo.Yes);
							sa.assertTrue(false, "could not verify picture in front of "+contact);
						}
					}else {
						log(LogStatus.SKIP,"picture not present in front of "+contact,YesNo.Yes);
						sa.assertTrue(false, "picture not present in front of "+contact);
					}
					click(driver, ip.accordionModalWindowClose(projectName, cp.getTabName(projectName, TabName.Object2Tab)), "cross", action.BOOLEAN);
				}else {
					log(LogStatus.SKIP,"view details link is not clickable",YesNo.Yes);
					sa.assertTrue(false, "view details link is not clickable");
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
	public void M4tc027_VerifyDeletingContactImageInSDG(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		String contactHeader=ip.getTabName(projectName,TabName.Object2Tab);
		String contact=M4Contact2FName+" "+M4Contact2LName;
		WebElement ele=null;
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
				ele=ip.returnAccordionViewDetailsLink(projectName, contactHeader);
				if (click(driver, ele, "view details link", action.SCROLLANDBOOLEAN)) {

					ele=ip.sdgContactImage(projectName, contact, 10);
					if (ele!=null) {

						if (ele.getAttribute("src").contains(BasePageErrorMessage.defaultPhotoText)) {
							log(LogStatus.INFO,"successfully verified photo removed of contact "+contact,YesNo.Yes);

						}
						else {
							log(LogStatus.SKIP,"could not verify photo removed in front of "+contact,YesNo.Yes);
							sa.assertTrue(false, "could not verify photo removed in front of "+contact);
						}
					}else {
						log(LogStatus.SKIP,"removed picture not present in front of "+contact,YesNo.Yes);
						sa.assertTrue(false, "removed picture not present in front of "+contact);
					}
					click(driver, ip.accordionModalWindowClose(projectName, cp.getTabName(projectName, TabName.Object2Tab)), "cross", action.BOOLEAN);
				}else {
					log(LogStatus.SKIP,"view details link is not clickable",YesNo.Yes);
					sa.assertTrue(false, "view details link is not clickable");
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
	public void M4tc028_VerifyBrokenImageInContactProfile(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String contactHeader=ip.getTabName(projectName,TabName.Object2Tab);
		String contact=M4Contact2FName+" "+M4Contact2LName;
		String parentID=null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
			List<String> layoutName = new ArrayList<String>();
			layoutName.add("Contact Layout");
			HashMap<String, String> sourceANDDestination = new HashMap<String, String>();
			sourceANDDestination.put(PageLabel.Profile_Image.toString(), PageLabel.Industry.toString());
			List<String> abc = sp.DragNDrop("", mode, object.Contact, ObjectFeatureName.pageLayouts, layoutName, sourceANDDestination);
			ThreadSleep(10000);
			if (!abc.isEmpty()) {
				log(LogStatus.FAIL, "field not added/already present 1", YesNo.Yes);
				//sa.assertTrue(false, "field not added/already present 1");
			}else{
				log(LogStatus.INFO, "field added/already present 1", YesNo.Yes);
			}
			driver.close();
			driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch, so cannot add field", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch, so cannot add field");
			}
		}
		else {
			log(LogStatus.FAIL, "could not click on setup link", YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}
		lp.CRMlogout();
		driver.close();
		config(browserToLaunch);
		lp = new LoginPageBusinessLayer(driver);
		cp = new ContactsPageBusinessLayer(driver);
		ip = new InstitutionsPageBusinessLayer(driver);
		ep = new EditPageBusinessLayer(driver);
		dp = new DealPageBusinessLayer(driver);
		sdg = new SDGPageBusinessLayer(driver);
		sp=new SetupPageBusinessLayer(driver);
		WebElement ele=null;
		lp.CRMLogin(crmUser1EmailID, adminPassword,appName);
		lp.searchAndClickOnApp(AppName.Files.toString(), 10);
		ele=ip.getuploadFilesButton(projectName, 10);
		String attachmentPath= System.getProperty("user.dir")+"\\UploadImages\\JPEGImage.jpg";
		String fileName=ExcelUtils.readData(phase1DataSheetFilePath,"FilePath",excelLabel.TestCases_Name,currentlyExecutingTC, excelLabel.File);
		String imgUrl="";
		if (click(driver, ele, "upload button", action.BOOLEAN)) {
			if (uploadFileAutoIT(attachmentPath)) {
				log(LogStatus.INFO, "successfully uploaded file "+attachmentPath, YesNo.No);
				ThreadSleep(5000);
				if (click(driver, ip.getdoneButtonListView(projectName, 10), "done", action.SCROLLANDBOOLEAN)) {
					
					
					ele=ip.documentNameOnFilesApp(projectName, fileName, 10);
					if (click(driver, ele, fileName, action.SCROLLANDBOOLEAN)) {
						imgUrl=ip.rightClickOnFileAndCopy(projectName,fileName,10);
						if (!imgUrl.equals("")) {
							log(LogStatus.INFO, "successfully copied img url", YesNo.Yes);
								
						}else {
							log(LogStatus.FAIL, "could not copy image path", YesNo.Yes);
							sa.assertTrue(false, "could not copy image path");
						}
					}else {
						log(LogStatus.FAIL, "image name is not clickable", YesNo.Yes);
						sa.assertTrue(false, "image name is not clickable");
					}
				}else {
					log(LogStatus.FAIL, "done button is not clickable", YesNo.Yes);
					sa.assertTrue(false, "done button is not clickable");
				}
			}else {
				log(LogStatus.FAIL, "could not upload file", YesNo.Yes);
				sa.assertTrue(false, "could not upload file");
			}
		}else {
			log(LogStatus.FAIL, "upload button is not clickable", YesNo.Yes);
			sa.assertTrue(false, "upload button is not clickable");
		}
		String a[]=imgUrl.split(".com");
				imgUrl=a[0]+"akul.com"+a[1];
		if (ip.clickOnTab(projectName, TabName.Object2Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName,contact , 10)) {
				ele=cp.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				click(driver, ele, "Details tab", action.BOOLEAN);
				if (ip.clickOnShowMoreActionDownArrow(projectName, PageName.Object2Page, ShowMoreActionDropDownList.Edit, 10)) {

					ele=cp.getContactPageTextBoxOrRichTextBoxWebElement(projectName, PageLabel.Profile_Image.toString(), 10);
					if (ele!=null) {
						sendKeys(driver, ele, imgUrl, "profile image", action.SCROLLANDBOOLEAN);
						if (click(driver, cp.getNavigationTabSaveBtn(projectName, 60), "Save Button",action.SCROLLANDBOOLEAN)){
							log(LogStatus.INFO, "successfully saved profile image", YesNo.Yes);

						}
						else {
							log(LogStatus.FAIL, "could not click on save button", YesNo.Yes);
							sa.assertTrue(false, "could not click on save button");
						}
					}
					else {
						log(LogStatus.FAIL, "profile image textbos is not visible", YesNo.Yes);
						sa.assertTrue(false, "profile image textbos is not visible");
					}
				}else {
					log(LogStatus.FAIL, "edit button is not clickable", YesNo.Yes);
					sa.assertTrue(false, "edit button is not clickable");
				}
				
				ele=ip.getRelatedTab(projectName, RelatedTab.Overview.toString(), 10);
				if (click(driver, ele, "overview tab", action.SCROLLANDBOOLEAN)) {
					ele=cp.getimgLink(projectName, 10);
					if (ele.getAttribute("src").contains(BasePageErrorMessage.defaultPhotoText)) {
						log(LogStatus.INFO, "successfully verified broken photo on contact page", YesNo.Yes);
						
					}else {
						log(LogStatus.FAIL, "could not verify standard photo on contact page", YesNo.Yes);
						sa.assertTrue(false, "could not verify standard photo on contact page");
					}
				}else {
					log(LogStatus.FAIL, "overview tab is not clickable", YesNo.Yes);
					sa.assertTrue(false, "overview tab is not clickable");
				}
			}else {
				log(LogStatus.FAIL, contact+" not found on contact page", YesNo.Yes);
				sa.assertTrue(false, contact+" not found on contact page");
			}
		}else {
			log(LogStatus.FAIL, "could not click on contact object", YesNo.Yes);
			sa.assertTrue(false, "could not click on contact object");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	@Parameters({ "projectName"})
	@Test
	public void M4tc029_VerifySDGProperties(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);

		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		WebElement ele=null;
		String contactHeader=ip.getTabName(projectName,TabName.Object2Tab);
		String contact=M4Contact2FName+" "+M4Contact2LName;
		String parentID=null;
		boolean flag=true;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1,10)) {
				ele=ip.returnAccordionViewDetailsLink(projectName, contactHeader);
				if (click(driver, ele, "view details link", action.SCROLLANDBOOLEAN)) {
					if (ip.clickOnEditButtonOnSDG(projectName, contact, EditPageLabel.Title.toString(), 10)) {
						appLog.error(">>>>");
						sc.next();
						
						ele=ip.SDGInputTextbox(projectName, EditPageLabel.Title.toString(), 10);
						
						sendKeys(driver, ele, M4Contact2Title+"a", "title textbox", action.BOOLEAN);
						ele=ip.accordionSDGContactCheckbox(projectName, contact, EditPageLabel.Title.toString(), action.BOOLEAN, 10);
						click(driver, ele, "checkbox", action.SCROLLANDBOOLEAN);
						click(driver, ele, "checkbox", action.SCROLLANDBOOLEAN);
						if (click(driver, ip.getsdgSaveButton(projectName,10), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.ERROR, "successfully clicked on save button", YesNo.Yes);

						}else {
							log(LogStatus.ERROR, "could not click on save button", YesNo.Yes);
							sa.assertTrue(false,"could not click on save button" );
						}
					}else {
						log(LogStatus.ERROR, "could not click on edit button", YesNo.Yes);
						sa.assertTrue(false,"could not click on edit button" );
					}

					if (ip.clickOnEditButtonOnSDG(projectName, contact, excelLabel.Email.toString(), 10)) {
						ele=ip.SDGInputTextbox(projectName, excelLabel.Email.toString(), 10);
						sendKeys(driver, ele, M4Contact2Email+"a", "email textbox", action.BOOLEAN);
						appLog.error(">>>>");
						sc.next();
						
						ele=ip.accordionSDGContactCheckbox(projectName, contact, EditPageLabel.Title.toString(), action.BOOLEAN, 10);
						click(driver, ele, "checkbox", action.SCROLLANDBOOLEAN);
						click(driver, ele, "checkbox", action.SCROLLANDBOOLEAN);
						if (click(driver, ip.getsdgSaveButton(projectName,10), "save", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "successfully clicked on save button", YesNo.No);

						}else {
							log(LogStatus.ERROR, "could not click on save button", YesNo.Yes);
							sa.assertTrue(false,"could not click on save button" );
						}
					}else {
						log(LogStatus.ERROR, "could not click on edit button", YesNo.Yes);
						sa.assertTrue(false,"could not click on edit button" );
					}
					if (ip.clickOnShowMoreActionDownArrow(projectName, PageName.SDGPage, ShowMoreActionDropDownList.New, 10)) {
						log(LogStatus.INFO, "successfully clicked on new button", YesNo.No);
					}
					else {
						ele=ip.accordionSDGActionButtons(projectName, contactHeader, "New", action.SCROLLANDBOOLEAN, 10);
						if (click(driver, ele, "new", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "successfully clicked on new button", YesNo.Yes);

						}
						else {
							log(LogStatus.ERROR, "could not click on new button on sdg", YesNo.Yes);
							sa.assertTrue(false,"could not click on new button on sdg" );
							flag=false;
						}
					}
					if (flag) {
					if (sendKeys(driver, cp.getContactFirstName(projectName, 60), M4Contact8FName, "Contact first Name",
								action.BOOLEAN)) {
							if (sendKeys(driver, cp.getContactLastName(projectName, 60), M4Contact8LName, "Contact Last Name",
									action.BOOLEAN)) {
								if (sendKeys(driver, cp.getcontactTitle(projectName, 10), M4Contact8Title, "title", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "passed value "+M4Contact8Title+" to title", YesNo.No);
									if (click(driver, ip.getNavigationTabSaveBtn(projectName, 10), "save", action.SCROLLANDBOOLEAN)) {
										ThreadSleep(2000);
										refresh(driver);
										ThreadSleep(2000);
										if(cp.verifyCreatedItemOnPage(Header.Contact, M4Contact8FName+" "+M4Contact8LName)!=null) {
											log(LogStatus.INFO, "successfully verified contact is created", YesNo.No);
											
										}else {
											log(LogStatus.ERROR, "could not verify contact created", YesNo.No);
											BaseLib.sa.assertTrue(false,"could not verify contact created" );
										}
									}else {
										log(LogStatus.ERROR, "could not click on save button, so cannot create contact from sdg", YesNo.No);
										BaseLib.sa.assertTrue(false,"could not click on save button, so cannot create contact from sdg" );
									}
								}
								else {
									log(LogStatus.ERROR, "could not pass value "+M4Contact8Title+" to title", YesNo.Yes);
									BaseLib.sa.assertTrue(false,"could not pass value "+M4Contact8Title+" to title" );
								}
							}else {
								log(LogStatus.ERROR, "could not pass value "+M4Contact8LName+" to last name", YesNo.Yes);
								BaseLib.sa.assertTrue(false,"could not pass value "+M4Contact8LName+" to last name" );
							}
						}else {
							log(LogStatus.ERROR, "could not pass value "+M4Contact8FName+" to first name", YesNo.Yes);
							BaseLib.sa.assertTrue(false,"could not pass value "+M4Contact8FName+" to first name" );
						}
					}else {
						log(LogStatus.ERROR, "could not click on new button", YesNo.Yes);
						sa.assertTrue(false,"could not click on new button" );
					}
				}else {
					log(LogStatus.ERROR, "could not click on view details link", YesNo.Yes);
					sa.assertTrue(false,"could not click on view details link" );
				}
			}else {
				log(LogStatus.ERROR, "could not click on "+M4Ins1, YesNo.Yes);
				sa.assertTrue(false,"could not click on "+M4Ins1 );
			}
		}else {
			log(LogStatus.ERROR, "entity tab is not clickable", YesNo.Yes);
			sa.assertTrue(false,"entity tab is not clickable" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M4tc030_VerifyCancelAndCrossOnEntityPageContactAccordionSDG(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);

		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		WebElement ele=null;
		String contactHeader=ip.getTabName(projectName,TabName.Object2Tab);
		String contact=M4Contact2FName+" "+M4Contact2LName;
		String parentID=null;

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1,10)) {
				for (int i = 0;i<2;i++) {

					ele=ip.returnAccordionViewDetailsLink(projectName, contactHeader);
					if (click(driver, ele, "accordion view details", action.SCROLLANDBOOLEAN)) {
						ele=ip.getHeaderTextForPage(projectName, PageName.NewTaskPopUP, contactHeader, action.BOOLEAN, 10);
						if (ele!=null) {
							log(LogStatus.INFO, "successfully verified presence of header in accordion", YesNo.No);

						}else {
							log(LogStatus.ERROR, "could not verify presence of header in accordion", YesNo.Yes);
							sa.assertTrue(false,"could not verify presence of header in accordion" );
						}
						if (i==0)
							ele=ip.accordionModalWindowClose(projectName, cp.getTabName(projectName, TabName.Object2Tab));
						else
							ele=ip.getfooterCloseButton(projectName, 10);
						if (click(driver,ele , "cross", action.BOOLEAN)) {
							if (isDisplayed(driver, ele, "visibility", 5, "close")==null) {
								log(LogStatus.INFO, "successfully verified window is closed", YesNo.No);

							}else {
								log(LogStatus.ERROR, "window is still present after clicking on close button", YesNo.Yes);
								sa.assertTrue(false,"window is still present after clicking on close button" );
							}
						}else {
							log(LogStatus.ERROR, "close icon is not clickable", YesNo.Yes);
							sa.assertTrue(false,"close icon is not clickable" );
						}
					}else {
						log(LogStatus.ERROR, "view details is not clickable", YesNo.Yes);
						sa.assertTrue(false,"view details is not clickable" );
					}
				}
			}else {
				log(LogStatus.ERROR, M4Ins1+" is not found on entity page", YesNo.Yes);
				sa.assertTrue(false,M4Ins1+" is not found on entity page" );
			}
		}else {
			log(LogStatus.ERROR, "entity page is not clickable", YesNo.Yes);
			sa.assertTrue(false,"entity page is not clickable" );
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M4tc031_VerifyUserImageOnSDG(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);

		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		WebElement ele=null;
		String dealteamHeader=ip.getTabName(projectName,TabName.Deal_Team);
		String contact=M4Contact2FName+" "+M4Contact2LName;
		String parentID=null;

		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		int i = 0;
		String field="Member__r.Name";
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Deal1, 10)) {
				ele=ip.returnAccordionViewDetailsLink(projectName, dealteamHeader);
				if (click(driver, ele, "view details link", action.SCROLLANDBOOLEAN)) {
					ele=ip.accordionSDGButtons(projectName, dealteamHeader,ToggleButtonGroup.SDGButton , action.BOOLEAN, 10);
					if (click(driver, ele, "sdp setup button", action.SCROLLANDBOOLEAN)) {
						parentID=switchOnWindow(driver);
						if (parentID!=null) {
							ele=ip.getRelatedTab(projectName, RelatedTab.Related.toString(), 10);
							if (click(driver, ele, "related tab", action.BOOLEAN)) {
									ele=ip.sdgButtons(projectName,field, ShowMoreActionDropDownList.Edit.toString(), 10);
									if (click(driver, ele, "edit", action.SCROLLANDBOOLEAN)) {
										ele=sdg.getLabelTextBox(projectName, PageName.SDGPage.toString(),SDGCreationLabel.Image_Field_API.toString(), 10);
										if (sendKeys(driver, ele, EditPageErrorMessage.image3, "image field api", action.SCROLLANDBOOLEAN)) {


											if (click(driver, sdg.getRecordPageSettingSave(10), "save", action.BOOLEAN)) {
												log(LogStatus.INFO,"successfully clicked on save button",YesNo.Yes);
											}
											else {
												log(LogStatus.SKIP,"could not click on save button, so could not add "+field,YesNo.Yes);
												sa.assertTrue(false, "could not click on save button, so could not add "+field);
											}
										}else {
											log(LogStatus.SKIP,"image field api field is not visible",YesNo.Yes);
											sa.assertTrue(false, "image field api field is not visible");
										}
									}else {
										log(LogStatus.SKIP,"edit button is not clickable for "+field,YesNo.Yes);
										sa.assertTrue(false, "edit button is not clickable for "+field);
									}
								}else {
									log(LogStatus.SKIP,"related tab is not clickable",YesNo.Yes);
									sa.assertTrue(false, "related tab is not clickable");
								}
							driver.close();
							driver.switchTo().window(parentID);
						}else {
							log(LogStatus.SKIP,"could not find new window to switch, so cannot edit field",YesNo.Yes);
							sa.assertTrue(false, "could not find new window to switch, so cannot edit field");
						}
					}else {
						log(LogStatus.SKIP,"setup button on sdg is not clickable",YesNo.Yes);
						sa.assertTrue(false, "setup button on sdg is not clickable");
					}
				}else {
					log(LogStatus.SKIP,"view details link is not clickable",YesNo.Yes);
					sa.assertTrue(false, "view details link is not clickable");
				}
			}else {
				log(LogStatus.SKIP,"could not click on deal "+M4Deal1,YesNo.Yes);
				sa.assertTrue(false, "could not click on deal "+M4Deal1);
			}
		}else {
			log(LogStatus.SKIP,"deals tab is not clickable",YesNo.Yes);
			sa.assertTrue(false, "deals tab is not clickable");
		}
		
		lp.CRMlogout();
		driver.close();
		config(browserToLaunch);
		
		lp = new LoginPageBusinessLayer(driver);
		fp = new FundsPageBusinessLayer(driver);
		cp = new ContactsPageBusinessLayer(driver);
		ip = new InstitutionsPageBusinessLayer(driver);
		ep = new EditPageBusinessLayer(driver);
		dp = new DealPageBusinessLayer(driver);
		sdg = new SDGPageBusinessLayer(driver);
		sp=new SetupPageBusinessLayer(driver);
		String user=AdminUserFirstName+" "+AdminUserLastName,imgId="";
		lp.CRMLogin(superAdminUserName, adminPassword);
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Deal1, 10)) {
				ele=ip.returnAccordionViewDetailsLink(projectName, dealteamHeader);
				if (click(driver, ele, "view details link", action.SCROLLANDBOOLEAN)) {
					ele=dp.returnAccordionLink(projectName, user);
					if (click(driver, ele, "accordion username", action.BOOLEAN)) {
						parentID=switchOnWindow(driver);
						if (parentID!=null) {
							ThreadSleep(3000);
							imgId=dp.getimgIconForPath(projectName, 10).getAttribute("src");
							if (imgId!=null){
								log(LogStatus.INFO, "found id of img uploaded: "+imgId, YesNo.Yes);

							}
							else {
								log(LogStatus.ERROR, "could not find id of img uploaded", YesNo.Yes);
								sa.assertTrue(false, "could not find id of img uploaded");

							}
							driver.close();
							driver.switchTo().window(parentID);
						}else {
							log(LogStatus.ERROR, "could not find new window to switch, so cannot update admin photo", YesNo.Yes);
							sa.assertTrue(false,"could not find new window to switch, so cannot update admin photo" );
						}
						List<WebElement> li = FindElements(driver, ip.sdgContactImageXpath(projectName, user), "admin profile photos");
						i=1;
						for (WebElement ele1:li) {
							String src=ele1.getAttribute("src");
							if (src.equalsIgnoreCase(imgId)) {
								log(LogStatus.INFO, "successfully verified "+imgId+" for image on Deal team accordion for "+user, YesNo.Yes);
		
							}else {
								log(LogStatus.ERROR, "expected: "+imgId+"\nfound: "+src+" for image on Deal team accordion for "+i+" th "+user, YesNo.Yes);
								sa.assertTrue(false,"expected: "+imgId+"\nfound: "+src+" for image on Deal team accordion for "+i+" th "+user );
							}
							i++;
						}
						

					}else {
						log(LogStatus.ERROR, "could not click on accordion header, so cannot update admin photo", YesNo.Yes);
						sa.assertTrue(false,"could not click on accordion header, so cannot update admin photo" );
					}
				}else {
					log(LogStatus.ERROR, "view details link is not clickable", YesNo.Yes);
					sa.assertTrue(false,"view details link is not clickable" );
				}
			}else {
				log(LogStatus.ERROR, M4Deal1+" is not found", YesNo.Yes);
				sa.assertTrue(false,M4Deal1+" is not found" );
			}
		}else {
			log(LogStatus.ERROR, "deals object is not clickable", YesNo.Yes);
			sa.assertTrue(false,"deals object is not clickable" );
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M4tc033_VerifyImageFieldByUploadingNewImage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);

		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		WebElement ele=null;
		String attachmentPath= System.getProperty("user.dir")+"\\UploadImages\\JPGImage.jpg";

		String dealteamHeader=ip.getTabName(projectName,TabName.Deal_Team);
		String user=AdminUserFirstName+" "+AdminUserLastName;
		String parentID=null;
		String id = null;
		boolean flag = false;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		ele=lp.getUserMenuTab_Lightning().get(0);
		if (click(driver, ele, "profile link", action.BOOLEAN)) {
			ele=lp.getProfilePageLink(projectName, AdminUserFirstName+" "+AdminUserLastName, 10);
			if (click(driver, ele, "profile link", action.BOOLEAN)) {
				id=dp.updatePhotoInUserPage(projectName, attachmentPath);
				if (id!=null) {
					log(LogStatus.INFO, "successfully updated photo", YesNo.No);
					flag = true;
				}else {
					log(LogStatus.ERROR, "could not update photo", YesNo.Yes);
					sa.assertTrue(false,"could not update photo" );
				}
			}else {
				log(LogStatus.ERROR, "profile link on user dropdown is not clickable, so cannot verify updated photo", YesNo.Yes);
				sa.assertTrue(false,"profile link on user dropdown is not clickable, so cannot verify updated photo" );
			}
		}else {
			log(LogStatus.ERROR, "profile link is not clickable, so cannot verify updated photo", YesNo.Yes);
			sa.assertTrue(false,"profile link is not clickable, so cannot verify updated photo" );
		}
		if (flag) {
			if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
				if (ip.clickOnAlreadyCreatedItem(projectName,M4Deal1 , 10)) {
					ele=cp.getRelatedTab(projectName, RelatedTab.Overview.toString(), 10);
					click(driver, ele, "overview tab", action.BOOLEAN);
					if (ip.verifyAccordianRecordImage(projectName, user, id)) {
						log(LogStatus.INFO, "successfully verified update photo in accordion", YesNo.No);

					}else {
						log(LogStatus.ERROR, "could not verify update photo in accordion", YesNo.Yes);
						sa.assertTrue(false,"could not verify update photo in accordion" );
					}
				}else {
					log(LogStatus.ERROR, "could not click on deal "+M4Deal1, YesNo.Yes);
					sa.assertTrue(false,"could not click on deal "+M4Deal1 );
				}
			}else {
				log(LogStatus.ERROR, "could not click on deal tab", YesNo.Yes);
				sa.assertTrue(false,"could not click on deal tab" );
			}
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M4tc034_VerifyImageFieldByPerformingDeleteAction(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);

		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		WebElement ele=null;
		String dealteamHeader=ip.getTabName(projectName,TabName.Deal_Team);
		
		String user=AdminUserFirstName+" "+AdminUserLastName;
		String parentID=null;
		String id = BasePageErrorMessage.defaultPhotoTextForAdminPhoto;
		boolean flag = false;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		ele=lp.getUserMenuTab_Lightning().get(0);
		if (click(driver, ele, "profile link", action.BOOLEAN)) {
			ele=lp.getProfilePageLink(projectName, AdminUserFirstName+" "+AdminUserLastName, 10);
			if (click(driver, ele, "profile link", action.BOOLEAN)) {
			if (ip.deletePhotoInUserPage(projectName, user)) {
				log(LogStatus.ERROR, "successfully delete photo for user "+user, YesNo.Yes);
				flag=true;
			}else {
				log(LogStatus.ERROR, "could not delete photo for user "+user, YesNo.Yes);
				sa.assertTrue(false,"could not delete photo for user "+user );
			}
			}else {
				log(LogStatus.ERROR, "profile link on profile menu is not clickable", YesNo.Yes);
				sa.assertTrue(false,"profile link on profile menu is not clickable"+user );
			}
		}else {
			log(LogStatus.ERROR, "profile link is not clickable, so cannot delete photo", YesNo.Yes);
			sa.assertTrue(false,"profile link is not clickable, so cannot delete photo" );
		}
		int i = 0;
		if (flag) {
			if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
				if (ip.clickOnAlreadyCreatedItem(projectName,M4Deal1 , 10)) {
					ele=cp.getRelatedTab(projectName, RelatedTab.Overview.toString(), 10);
					click(driver, ele, "overview tab", action.BOOLEAN);
					ele=ip.returnAccordionViewDetailsLink(projectName, dealteamHeader);
					if (click(driver, ele, "view details link", action.BOOLEAN)) {
						List<WebElement> li = FindElements(driver, ip.sdgContactImageXpath(projectName, user), "admin profile photos");
						i=1;
						for (WebElement ele1:li) {
							String src=ele1.getAttribute("src");
							if (src.contains(BasePageErrorMessage.defaultPhotoTextForAdminPhoto)) {
								log(LogStatus.INFO, "successfully verified no image on Deal team accordion for "+user, YesNo.Yes);

							}else {
								log(LogStatus.ERROR, "expected: no image\nfound: "+src+" for image on Deal team accordion for "+i+" th "+user, YesNo.Yes);
								sa.assertTrue(false,"expected: no image\nfound: "+src+" for image on Deal team accordion for "+i+" th "+user );
							}
							i++;
						}
					}
					if (ip.verifyAccordianRecordImage(projectName, user, id)) {
						log(LogStatus.INFO, "successfully verified deleted photo in accordion", YesNo.No);

					}else {
						log(LogStatus.ERROR, "photo on accordion for admin should be deleted but it is still present", YesNo.Yes);
						sa.assertTrue(false,"photo on accordion for admin should be deleted but it is still present" );
					}
				}else {
					log(LogStatus.ERROR, "could not click on deal "+M4Deal1, YesNo.Yes);
					sa.assertTrue(false,"could not click on deal "+M4Deal1 );
				}
			}else {
				log(LogStatus.ERROR, "could not click on deal tab", YesNo.Yes);
				sa.assertTrue(false,"could not click on deal tab" );
			}
		}
		lp.CRMlogout();
		sa.assertAll();
		
	}
	@Parameters({ "projectName"})
	@Test
	public void M4tc032_VerifyStaffImageOnMarketingEventSDG(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);

		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		WebElement ele=null;
		String attendeeHeader=AttendeeLabels.Attendee.toString();
		String contact=M4Contact2FName+" "+M4Contact2LName;
		String parentID=null;
		String field=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name,"AFIeld1", excelLabel.APIName);
		
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		int i = 0;
		if (ip.clickOnTab(projectName, TabName.Object5Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4MarketingEvent1Name, 10)) {
				ele=ip.returnAccordionViewDetailsLink(projectName, attendeeHeader);
				if (click(driver, ele, "view details link", action.SCROLLANDBOOLEAN)) {
					
					ele=ip.accordionExpandCollapse(projectName, ExpandCollapse.Expand, 10);
					click(driver, ele, "collapse icon", action.SCROLLANDBOOLEAN);
					ele=ip.accordionSDGButtons(projectName, attendeeHeader,ToggleButtonGroup.SDGButton , action.BOOLEAN, 10);
					if (click(driver, ele, "sdp setup button", action.SCROLLANDBOOLEAN)) {
						parentID=switchOnWindow(driver);
						if (parentID!=null) {
							ele=ip.getRelatedTab(projectName, RelatedTab.Related.toString(), 10);
							if (click(driver, ele, "related tab", action.BOOLEAN)) {
									ele=ip.sdgButtons(projectName,field, ShowMoreActionDropDownList.Edit.toString(), 10);
									if (click(driver, ele, "edit", action.SCROLLANDBOOLEAN)) {
										ele=sdg.getLabelTextBox(projectName, PageName.SDGPage.toString(),SDGCreationLabel.Image_Field_API.toString(), 10);
										if (sendKeys(driver, ele, EditPageErrorMessage.AttendeeImage, "image field api", action.SCROLLANDBOOLEAN)) {


											if (click(driver, sdg.getRecordPageSettingSave(10), "save", action.BOOLEAN)) {
												log(LogStatus.INFO,"successfully clicked on save button",YesNo.Yes);
											}
											else {
												log(LogStatus.SKIP,"could not click on save button, so could not add "+field,YesNo.Yes);
												sa.assertTrue(false, "could not click on save button, so could not add "+field);
											}
										}else {
											log(LogStatus.SKIP,"image field api field is not visible",YesNo.Yes);
											sa.assertTrue(false, "image field api field is not visible");
										}
									}else {
										log(LogStatus.SKIP,"edit button is not clickable for "+field,YesNo.Yes);
										sa.assertTrue(false, "edit button is not clickable for "+field);
									}
								}else {
									log(LogStatus.SKIP,"related tab is not clickable",YesNo.Yes);
									sa.assertTrue(false, "related tab is not clickable");
								}
							ThreadSleep(3000);
							driver.close();
							driver.switchTo().window(parentID);
						}else {
							log(LogStatus.SKIP,"could not find new window to switch, so cannot edit field",YesNo.Yes);
							sa.assertTrue(false, "could not find new window to switch, so cannot edit field");
						}
					}else {
						log(LogStatus.SKIP,"setup button on sdg is not clickable",YesNo.Yes);
						sa.assertTrue(false, "setup button on sdg is not clickable");
					}
				}else {
					log(LogStatus.SKIP,"view details link is not clickable",YesNo.Yes);
					sa.assertTrue(false, "view details link is not clickable");
				}
			}else {
				log(LogStatus.SKIP,"could not click on deal "+M4MarketingEvent1Name,YesNo.Yes);
				sa.assertTrue(false, "could not click on deal "+M4MarketingEvent1Name);
			}
		}else {
			log(LogStatus.SKIP,"events tab is not clickable",YesNo.Yes);
			sa.assertTrue(false, "events tab is not clickable");
		}
		
		lp.CRMlogout();
		driver.close();
		config(browserToLaunch);
		
		lp = new LoginPageBusinessLayer(driver);
		fp = new FundsPageBusinessLayer(driver);
		cp = new ContactsPageBusinessLayer(driver);
		ip = new InstitutionsPageBusinessLayer(driver);
		ep = new EditPageBusinessLayer(driver);
		dp = new DealPageBusinessLayer(driver);
		sdg = new SDGPageBusinessLayer(driver);
		sp=new SetupPageBusinessLayer(driver);
		String user=AdminUserFirstName+" "+AdminUserLastName,imgId="";
		lp.CRMLogin(superAdminUserName, adminPassword);
		if (ip.clickOnTab(projectName, TabName.Object5Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4MarketingEvent1Name, 10)) {
				ele=ip.returnAccordionViewDetailsLink(projectName, attendeeHeader);
				if (click(driver, ele, "view details link", action.SCROLLANDBOOLEAN)) {
					ele=dp.returnAccordionLink(projectName, user);
					if (click(driver, ele, "accordion username", action.BOOLEAN)) {
						parentID=switchOnWindow(driver);
						if (parentID!=null) {
							ThreadSleep(3000);
							imgId=dp.getimgIconForPath(projectName, 10).getAttribute("src");
							if (imgId!=null){
								log(LogStatus.INFO, "found id of img uploaded: "+imgId, YesNo.Yes);

							}
							else {
								log(LogStatus.ERROR, "could not find id of img uploaded", YesNo.Yes);
								sa.assertTrue(false, "could not find id of img uploaded");

							}
							driver.close();
							driver.switchTo().window(parentID);
						}else {
							log(LogStatus.ERROR, "could not find new window to switch, so cannot update admin photo", YesNo.Yes);
							sa.assertTrue(false,"could not find new window to switch, so cannot update admin photo" );
						}
						List<WebElement> li = FindElements(driver, ip.sdgContactImageXpath(projectName, user), "admin profile photos");
						i=1;
						for (i = 0;i<6;i++){
							String src=li.get(i).getAttribute("src");
							if (src.equalsIgnoreCase(imgId)) {
								log(LogStatus.INFO, "successfully verified "+imgId+" for image on Deal team accordion for "+user, YesNo.Yes);
		
							}else {
								log(LogStatus.ERROR, "expected: "+imgId+"\nfound: "+src+" for image on Deal team accordion for "+i+" th "+user, YesNo.Yes);
								sa.assertTrue(false,"expected: "+imgId+"\nfound: "+src+" for image on Deal team accordion for "+i+" th "+user );
							}
						}
						

					}else {
						log(LogStatus.ERROR, "could not click on accordion header, so cannot update admin photo", YesNo.Yes);
						sa.assertTrue(false,"could not click on accordion header, so cannot update admin photo" );
					}
				}else {
					log(LogStatus.ERROR, "view details link is not clickable", YesNo.Yes);
					sa.assertTrue(false,"view details link is not clickable" );
				}
			}else {
				log(LogStatus.ERROR, M4MarketingEvent1Name+" is not found", YesNo.Yes);
				sa.assertTrue(false,M4MarketingEvent1Name+" is not found" );
			}
		}else {
			log(LogStatus.ERROR, "events object is not clickable", YesNo.Yes);
			sa.assertTrue(false,"events object is not clickable" );
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M4tc035_1_CalenderPrecondition(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);

		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		WebElement ele=null;
		String sdgEvent="InternalEventInvitees";
		String contact=M4Contact2FName+" "+M4Contact2LName;
		String parentID=null;
		String field=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name,"AFIeld1", excelLabel.APIName);
		String fieldValues[]={SDGActionsCreationLabel.Name.toString()+breakSP+M4SDGAction1Name,SDGActionsCreationLabel.Action_Order.toString()+breakSP+M4SDGAction1Order,
				SDGActionsCreationLabel.Event.toString()+breakSP+M4SDGAction1Event,SDGActionsCreationLabel.Event_Payload.toString()+breakSP+M4SDGAction1EventPayload,
				SDGActionsCreationLabel.Action_Type+breakSP+M4SDGAction1Type
				};
		lp.CRMLogin(superAdminUserName, adminPassword, SDG);
		String DropComponentName="Fullcalender";
		if (ip.clickOnTab(projectName, TabName.SDGTab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, TabName.SDGTab, sdgEvent, 10)) {
			ele=ip.getRelatedTab(projectName, RelatedTab.Related.toString(), 10);
			if (click(driver, ele, "related", action.BOOLEAN)) {
				ele=ip.sdgButtons(projectName, SDGLabels.Actions.toString(), "New", 10);
				if(click(driver,ele, "new action",  action.SCROLLANDBOOLEAN)) {
					ip.createSDGAction(projectName, fieldValues);
					ThreadSleep(5000);
					click(driver, ip.getactionsSDGRefresh(projectName, 10), "refresh", action.SCROLLANDBOOLEAN);
				}else {
					log(LogStatus.SKIP,"new actions button is not cilckable",YesNo.Yes);
					sa.assertTrue(false, "new actions button is not cilckable");
				}
			}else {
				log(LogStatus.SKIP,"related tab is not clickable",YesNo.Yes);
				sa.assertTrue(false, "related tab is not clickable");
			}
			}else {
				log(LogStatus.SKIP,sdgEvent+" sdg is not found",YesNo.Yes);
				sa.assertTrue(false,sdgEvent+ " sdg is not found");
			}
		}else {
			log(LogStatus.SKIP,"SDG tab is not clickable",YesNo.Yes);
			sa.assertTrue(false, "SDG tab is not clickable");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	@Parameters({ "projectName"})
	@Test
	public void M4tc035_2_AddFullCalenderOnInstPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);

		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String source= System.getProperty("user.dir")+"\\AutoIT\\EditPage\\fc.png";
		String target= System.getProperty("user.dir")+"\\AutoIT\\EditPage\\caldest.png";
		WebElement ele=null;
		String attendeeHeader=AttendeeLabels.Attendee.toString();
		String contact=M4Contact2FName+" "+M4Contact2LName;
		String parentID=null;
		String field=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name,"AFIeld1", excelLabel.APIName);
		String fieldValues[]={excelLabel.sObjectName.toString()+"<break>"+"Marketing_Event__c",EditPageLabel.Title.toString()+"<break>"+excelLabel.Name.toString(),
				EditPageLabel.Start_DateTime.toString()+"<break>"+"Date__c",EditPageLabel.Title_Highlight_Color.toString()+"<break>"+BasePageErrorMessage.titleHighlightColor,
				EditPageLabel.Filter.toString()+"<break>"+BasePageErrorMessage.filter,EditPageLabel.Onclick_Title.toString()+"<break>"+BasePageErrorMessage.onclickTitle
				};
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String DropComponentName="Fullcalendar";
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1, 10)) {
				if (ep.clickOnEditPageLink()) {
					log(LogStatus.INFO, "successfully reached edit page", YesNo.No);
					ThreadSleep(3000);
					if (sendKeys(driver, ep.getEditPageSeachTextBox(projectName, 10), DropComponentName, "calendar", action.SCROLLANDBOOLEAN)) {
						ThreadSleep(3000);
						switchToFrame(driver, 10, ep.getEditPageFrame(projectName, 10));
						ele=ep.getRelatedTab(ProjectName.PEEdge.toString(), PageName.Object1Page,RelatedTab.Events, 10);
						if (click(driver, ele, "events related ", action.SCROLLANDBOOLEAN)) {
							ThreadSleep(5000);
						switchToDefaultContent(driver);
						if (ep.dragAndDropCalender(projectName, source,target,PageName.Object1Page, RelatedTab.Events, DropComponentName, fieldValues)) {
							log(LogStatus.ERROR, "successfully dragged calender", YesNo.Yes);

						}else {
							log(LogStatus.ERROR, "could not drag and drop calender", YesNo.Yes);
							sa.assertTrue(false,"could not drag and drop calender" );
						}
						}else {
							log(LogStatus.ERROR, "events tab is not clickable", YesNo.Yes);
							sa.assertTrue(false,"events tab is not clickable" );
						}
						switchToDefaultContent(driver);
					}else {
						log(LogStatus.ERROR, "search text box is not visible", YesNo.Yes);
						sa.assertTrue(false,"search text box is not visible" );
					}
				}else {
					log(LogStatus.ERROR, "could not drag and drop calender", YesNo.Yes);
					sa.assertTrue(false,"could not drag and drop calender" );
				}
			}else {
				log(LogStatus.ERROR, "could not click on entity "+M4Ins1, YesNo.Yes);
				sa.assertTrue(false,"could not click on entity "+M4Ins1 );
			}
		}else {
			log(LogStatus.ERROR, "entity tab is not clickable", YesNo.Yes);
			sa.assertTrue(false,"entity tab is not clickable" );
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	@Parameters({ "projectName"})
	@Test
	public void M4tc036_CreateThirdPartEventCalenderOnInstPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		MarketingEventPageBusinessLayer me = new MarketingEventPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		String toggleTab="Upcoming Events",date=previousOrForwardDate(2, "M/d/YYYY");;
		String button="New "+excelLabel.Marketing_Event.toString();
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		WebElement ele=null;
		String attendeeHeader=AttendeeLabels.Attendee.toString();
		String contact=M4Contact2FName+" "+M4Contact2LName;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1, 10)) {
				ele=ip.getRelatedTab(projectName, RelatedTab.Events.toString(), 10);
				if (click(driver, ele, "events tab", action.SCROLLANDBOOLEAN)) {
					ThreadSleep(3000);
					ele=ip.accordionSDGActionButtons(projectName,toggleTab , button, action.SCROLLANDBOOLEAN, 10);
					scrollDownThroughWebelement(driver, ele, "events");
					if (click(driver, ele, "new marketing event button", action.BOOLEAN)) {
						if (me.createMarketingEventFromSDG(projectName, M4MarketingEvent2Name, M4MarketingEvent2RecordType, date, null, 10)) {
							log(LogStatus.INFO, "successfully created event from sdg", YesNo.Yes);


							ThreadSleep(3000);
							if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
								if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1, 10)) {
									ele=ip.getRelatedTab(projectName, RelatedTab.Events.toString(), 10);
									if (click(driver, ele, "events tab", action.SCROLLANDBOOLEAN)) {
										int loc=ip.findLocationOfDate(projectName, date.split("/")[1]);
										ele=ip.getEventPresentOnCalender(projectName, date.split("/")[1], loc,10);
										if (ele!=null) {
											if (ele.getAttribute("title").equalsIgnoreCase(M4MarketingEvent2Name)) {
												log(LogStatus.INFO, "successfully verified event name on calendar", YesNo.Yes);

											}else {
												log(LogStatus.ERROR, "event name not matched. expected: "+M4MarketingEvent2Name+"\nactual: "+ele.getAttribute("title"), YesNo.Yes);
												sa.assertTrue(false,"event name not matched. expected: "+M4MarketingEvent2Name+"\nactual: "+ele.getAttribute("title"));
											}
										}else {
											log(LogStatus.ERROR, "could not create event from sdg", YesNo.Yes);
											sa.assertTrue(false,"could not create event from sdg" );
										}
									}else {
										log(LogStatus.ERROR, "events tab is not clickable", YesNo.Yes);
										sa.assertTrue(false,"events tab is not clickable" );
									}
								}else {
									log(LogStatus.ERROR, "could not click on event "+M4Ins1, YesNo.Yes);
									sa.assertTrue(false,"could not click on event "+M4Ins1 );
								}
							}else {
								log(LogStatus.ERROR, "entity tab is not clickable", YesNo.Yes);
								sa.assertTrue(false,"entity tab is not clickable" );
							}
						}else {
							log(LogStatus.ERROR, "could not create event from sdg", YesNo.Yes);
							sa.assertTrue(false,"could not create event from sdg" );
						}
					}else {
						log(LogStatus.ERROR, "could not click on new event button", YesNo.Yes);
						sa.assertTrue(false,"could not click on new event button" );
					}
				}else {
					log(LogStatus.ERROR, "events tab is not clickable", YesNo.Yes);
					sa.assertTrue(false,"events tab is not clickable" );
				}
			}else {
				log(LogStatus.ERROR, "could not click on ins "+M4Ins1, YesNo.Yes);
				sa.assertTrue(false,"could not click on ins "+M4Ins1 );
			}
		}else {
			log(LogStatus.ERROR, "entity tab is not clickable", YesNo.Yes);
			sa.assertTrue(false,"events tab is not clickable" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M4tc037_CreateThirdPartEventByClickingOnDateOnCalenderOnInstPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		MarketingEventPageBusinessLayer me = new MarketingEventPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		String toggleTab="Upcoming Events",date=previousOrForwardDate(2, "YYYY-MM-dd");
		String button="New "+excelLabel.Marketing_Event.toString();
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		WebElement ele=null;
		String attendeeHeader=AttendeeLabels.Attendee.toString();
		String contact=M4Contact2FName+" "+M4Contact2LName;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		sc.next();
		/*if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1, 10)) {
			*/	ele=ip.getRelatedTab(projectName, RelatedTab.Events.toString(), 10);
				if (click(driver, ele, "events tab", action.SCROLLANDBOOLEAN)) {
					ThreadSleep(10000);
					ip.calendarBox(projectName, date);
					//scrollDownThroughWebelement(driver, ele, "calender date box");
					//if (clickUsingActionClass(driver, ele)) {
					//if (clickUsingJavaScript(driver, ele,"calendar date box")) {
						if (me.createMarketingEventFromSDG(projectName, M4MarketingEvent3Name, M4MarketingEvent3RecordType, "", M4MarketingEvent1Organizer, 10)) {
							log(LogStatus.INFO, "successfully created event from sdg", YesNo.Yes);
						}
					}
				//}
		/*	}
		}*/
	}@Parameters({ "projectName"})
	@Test
	public void M4tc038_VerifyByAddingEventForLaterMonthOnCalenderOnInstPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		MarketingEventPageBusinessLayer me = new MarketingEventPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		String toggleTab="Upcoming Events",date1=todaysDateSingleDigit;
		String date2=previousOrForwardMonthAccordingToTimeZone(1, "M/d/YYYY", BasePageErrorMessage.AmericaLosAngelesTimeZone);
		String button="New "+excelLabel.Marketing_Event.toString();
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		WebElement ele=null;
		String marketingE[][]={{M4MarketingEvent4Name,M4MarketingEvent4RecordType,date1,null},
				{M4MarketingEvent5Name,M4MarketingEvent5RecordType,date2,null}
		};
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1, 10)) {
				for (int i = 0;i<2;i++) {
					ele=ip.getRelatedTab(projectName, RelatedTab.Events.toString(), 10);
					if (click(driver, ele, "events tab", action.SCROLLANDBOOLEAN)) {
						ThreadSleep(3000);
						ele=ip.accordionSDGActionButtons(projectName,toggleTab , button, action.SCROLLANDBOOLEAN, 10);
						scrollDownThroughWebelement(driver, ele, "events");
						if (click(driver, ele, "new marketing event button", action.BOOLEAN)) {
							if (me.createMarketingEventFromSDG(projectName, marketingE[i][0], marketingE[i][1], marketingE[i][2], marketingE[i][3], 10)) {
								log(LogStatus.INFO, "successfully created event from sdg", YesNo.Yes);


								ThreadSleep(3000);
								if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
									if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1, 10)) {
										ele=ip.getRelatedTab(projectName, RelatedTab.Events.toString(), 10);
										if (click(driver, ele, "events tab", action.SCROLLANDBOOLEAN)) {
											String date=marketingE[i][2];
											String monthAndYear=findMonthNameAndYear(date);
											if (ip.reachToDesiredMonthOnCalnder(projectName, monthAndYear)) {
												log(LogStatus.INFO, "successfully reached to desired month on calender", YesNo.No);
												
												int loc=ip.findLocationOfDate(projectName, marketingE[i][2].split("/")[1]);
												ele=ip.getEventPresentOnCalender(projectName, marketingE[i][2].split("/")[1], loc,10);
												if (ele!=null) {
													if (ele.getAttribute("title").equalsIgnoreCase(marketingE[i][0])) {
														log(LogStatus.INFO, "successfully verified event name on calendar", YesNo.Yes);

													}else {
														log(LogStatus.ERROR, "event name not matched. expected: "+marketingE[i][0]+"\nactual: "+ele.getAttribute("title"), YesNo.Yes);
														sa.assertTrue(false,"event name not matched. expected: "+marketingE[i][0]+"\nactual: "+ele.getAttribute("title"));
													}
												}else {
													log(LogStatus.ERROR, "could not create event from sdg", YesNo.Yes);
													sa.assertTrue(false,"could not create event from sdg" );
												}
											}else {
												log(LogStatus.ERROR, "could not reach to desired month of event, so cannot verify event", YesNo.Yes);
												sa.assertTrue(false,"could not reach to desired month of event, so cannot verify event" );
											}
										}else {
											log(LogStatus.ERROR, "events tab is not clickable", YesNo.Yes);
											sa.assertTrue(false,"events tab is not clickable" );
										}
									}else {
										log(LogStatus.ERROR, "could not click on entity "+M4Ins1, YesNo.Yes);
										sa.assertTrue(false,"could not click on entity "+M4Ins1 );
									}
								}else {
									log(LogStatus.ERROR, "entity tab is not clickable", YesNo.Yes);
									sa.assertTrue(false,"entity tab is not clickable" );
								}
							}else {
								log(LogStatus.ERROR, "could not create event from sdg", YesNo.Yes);
								sa.assertTrue(false,"could not create event from sdg" );
							}
						}else {
							log(LogStatus.ERROR, "could not click on new event button", YesNo.Yes);
							sa.assertTrue(false,"could not click on new event button" );
						}
					}else {
						log(LogStatus.ERROR, "events tab is not clickable", YesNo.Yes);
						sa.assertTrue(false,"events tab is not clickable" );
					}
				}
			}else {
				log(LogStatus.ERROR, "could not click on ins "+M4Ins1, YesNo.Yes);
				sa.assertTrue(false,"could not click on ins "+M4Ins1 );
			}
		}else {
			log(LogStatus.ERROR, "entity tab is not clickable", YesNo.Yes);
			sa.assertTrue(false,"events tab is not clickable" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	@Parameters({ "projectName"})
	@Test
	public void M4tc039_VerifyClickingOnEventRedirectToAppPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		MarketingEventPageBusinessLayer me = new MarketingEventPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		AttendeePageBusinessLayer ap = new AttendeePageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String date=previousOrForwardDate(2, "M/d/YYYY");
		WebElement ele=null;
		String parentID=null;
		String xpath = "";
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1, 10)) {
					ele=ip.getRelatedTab(projectName, RelatedTab.Events.toString(), 10);
					if (click(driver, ele, "events tab", action.SCROLLANDBOOLEAN)) {
						ThreadSleep(3000);
						String monthAndYear=findMonthNameAndYear(date);
						if (ip.reachToDesiredMonthOnCalnder(projectName, monthAndYear)) {
							log(LogStatus.INFO, "successfully reached to desired month on calender", YesNo.No);
							
							int loc=ip.findLocationOfDate(projectName, date.split("/")[1]);
							ele=ip.getEventPresentOnCalender(projectName, date.split("/")[1], loc,7);
								if (ele!=null) {
									if (click(driver, ele,M4MarketingEvent2Name, action.SCROLLANDBOOLEAN)) {
										parentID=switchOnWindow(driver);
										if (parentID!=null) {
											ThreadSleep(5000);
											if (ip.clickOnShowMoreActionDownArrow(projectName, PageName.SDGPage, ShowMoreActionDropDownList.New_Attendee, 10)) {
												if (sendKeys(driver, me.getAttendeeStaffTextBoxe(projectName, 10), crmUser1FirstName+" "+crmUser1LastName, crmUser1FirstName+" "+crmUser1LastName,action.SCROLLANDBOOLEAN)) {
													ThreadSleep(1000);
													log(LogStatus.INFO,"Able to send "+crmUser1FirstName+" "+crmUser1LastName,YesNo.Yes);
													if (click(driver,FindElement(driver,"//span[contains(@class,'listbox')]//*[@title='"+crmUser1FirstName+" "+crmUser1LastName+"']","ATTENDEE Name List", action.SCROLLANDBOOLEAN, 30),
															crmUser1FirstName+" "+crmUser1LastName, action.SCROLLANDBOOLEAN)) {
														log(LogStatus.INFO,"Able to select "+crmUser1FirstName+" "+crmUser1LastName,YesNo.No);
													} else {
														sa.assertTrue(false,"Not Able to select "+crmUser1FirstName+" "+crmUser1LastName);
														log(LogStatus.SKIP,"Not Able to select "+crmUser1FirstName+" "+crmUser1LastName,YesNo.Yes);
													}

												} else {
													sa.assertTrue(false,"Not Able to send "+crmUser1FirstName+" "+crmUser1LastName);
													log(LogStatus.SKIP,"Not Able to send "+crmUser1FirstName+" "+crmUser1LastName,YesNo.Yes);
												}
												String label="status",value=M4Attendee10Status;
												if (click(driver, me.getStatus(projectName, 10), label, action.SCROLLANDBOOLEAN)) {
													ThreadSleep(2000);
													log(LogStatus.INFO,"Able to Click on "+label,YesNo.No);

													xpath="//span[@title='"+value+"']";
													ele = FindElement(driver,xpath, value,action.SCROLLANDBOOLEAN, 10);
													ThreadSleep(2000);
													if (click(driver, ele, value, action.SCROLLANDBOOLEAN)) {
														log(LogStatus.INFO,"Able to select "+value+" to label : "+label,YesNo.No);	
													} else {
														sa.assertTrue(false,"Not Able to select "+value+" to label : "+label);
														log(LogStatus.SKIP,"Not Able to select "+value+" to label : "+label,YesNo.Yes);
													}

												} else {
													sa.assertTrue(false,"Not Able to Click on "+label);
													log(LogStatus.SKIP,"Not Able to Click on "+label,YesNo.Yes);
												}
												ele=ap.labelTextBox(projectName,AttendeeLabels.Marketing_Event.toString(), 60);
												if (sendKeys(driver, ele, M4MarketingEvent2Name, "event textbox", action.BOOLEAN)){
													xpath="//li//span[@title='"+M4MarketingEvent2Name+"']";
													ThreadSleep(3000);
													ele = FindElement(driver, xpath, "marketingEvent", action.SCROLLANDBOOLEAN, 10);
													ele=isDisplayed(driver, ele, "visibility", 10, "marketing event");
													if (clickUsingJavaScript(driver, ele, "marketing event", action.SCROLLANDBOOLEAN)) {
														log(LogStatus.INFO,"marketingEvent Label prefilled with value : ",YesNo.No);	
													} else {
														sa.assertTrue(false,"could not select marketing event");
														log(LogStatus.SKIP,"could not select marketing event",YesNo.Yes);
													}

												}else {
													sa.assertTrue(false,"could not select marketing event");
													log(LogStatus.SKIP,"could not select marketing event",YesNo.Yes);
												}
													
												ele=ip.getCustomTabSaveBtn(projectName,10);
												if (click(driver, ele, value, action.SCROLLANDBOOLEAN)) {
													log(LogStatus.INFO,"Able to click on save button",YesNo.No);	
												} else {
													sa.assertTrue(false,"Not Able to click on save button ");
													log(LogStatus.SKIP,"Not Able to click on save button ",YesNo.Yes);
												}
											}else {
												sa.assertTrue(false,"not able to select dropdown");
												log(LogStatus.SKIP,"not able to select dropdown",YesNo.Yes);
											}
											ThreadSleep(4000);
												driver.close();
												driver.switchTo().window(parentID);
										}else {
											sa.assertTrue(false,"could not find new window to switch");
											log(LogStatus.SKIP,"could not find new window to switch",YesNo.Yes);
										}
									}else {
										log(LogStatus.ERROR, "could not click on event name on calender", YesNo.Yes);
										sa.assertTrue(false,"could not click on event name on calender" );
									}
									
									ele=ip.getEventPresentOnCalender(projectName, date.split("/")[1], loc,7);
									if (click(driver, ele, "event 2", action.BOOLEAN)) {
										parentID=switchOnWindow(driver);
										ThreadSleep(4000);
										if (parentID!=null) {
										if (sdg.getSDGValue(projectName, "Internal", crmUser1FirstName+" "+crmUser1LastName, 10)!=null) {
											log(LogStatus.INFO, "successfully verified presence of attendee staff on event sdg"+M4MarketingEvent2Name,YesNo.Yes);
											
										}else {
											log(LogStatus.ERROR, "could not verify presence of attendee staff on event sdg"+M4MarketingEvent2Name,YesNo.Yes);
											sa.assertTrue(false,"could not verify presence of attendee staff on event sdg"+M4MarketingEvent2Name);
										}
										driver.close();
										driver.switchTo().window(parentID);
										}else {
											log(LogStatus.ERROR, "could not find new window to switch, so cannot verify event sdg", YesNo.Yes);
											sa.assertTrue(false,"could not find new window to switch, so cannot verify event sdg" );
										}
									}
								}else {
									log(LogStatus.ERROR, "event name should not be present but it is: "+M4MarketingEvent2Name,YesNo.Yes);
									sa.assertTrue(false,"event name should not be present but it is: "+M4MarketingEvent2Name);
								}
						}else {
							log(LogStatus.ERROR, "could not reach to desired month of event, so cannot verify event", YesNo.Yes);
							sa.assertTrue(false,"could not reach to desired month of event, so cannot verify event" );
						}
					}else {
						log(LogStatus.ERROR, "events tab is not clickable", YesNo.Yes);
						sa.assertTrue(false,"events tab is not clickable" );
					}
			}else {
				log(LogStatus.ERROR, M4Ins1+" not found on entity tab", YesNo.Yes);
				sa.assertTrue(false,M4Ins1+" not found on entity tab" );
			}
		}else {
			log(LogStatus.ERROR, "entity tab is not clickable", YesNo.Yes);
			sa.assertTrue(false,"entity tab is not clickable" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M4tc040_CreateEventWithoutOrganizerCalenderOnInstPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		MarketingEventPageBusinessLayer me = new MarketingEventPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		String toggleTab="Upcoming Events",date=previousOrForwardDate(3, "M/d/YYYY");;
		String date2=previousOrForwardMonthAccordingToTimeZone(1, "M/d/YYYY", BasePageErrorMessage.AmericaLosAngelesTimeZone);
		String button="New "+excelLabel.Marketing_Event.toString();
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		WebElement ele=null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1, 10)) {
				ele=ip.getRelatedTab(projectName, RelatedTab.Events.toString(), 10);
				if (click(driver, ele, "events tab", action.SCROLLANDBOOLEAN)) {
					ThreadSleep(3000);
					ele=ip.accordionSDGActionButtons(projectName,toggleTab , button, action.SCROLLANDBOOLEAN, 10);
					scrollDownThroughWebelement(driver, ele, "events");
					if (click(driver, ele, "new marketing event button", action.BOOLEAN)) {
						if(click(driver, me.getRadioButtonforRecordTypeME(M4MarketingEvent6RecordType, 10), "Radio Button for : "+M4MarketingEvent6RecordType, action.SCROLLANDBOOLEAN)){
							appLog.info("Clicked on radio Button for Marketing Event for record type : "+M4MarketingEvent6RecordType);
							if (click(driver, me.getContinueOrNextButton(projectName,10), "Continue Button", action.BOOLEAN)) {
								appLog.info("Clicked on Continue or Nxt Button");	
								ThreadSleep(1000);
							}else{
								appLog.error("Not Able to Clicked on Next Button");
								sa.assertTrue(false,"Not Able to Clicked on Next Button");
									
							}
						}else{
							appLog.error("Not Able to Clicked on radio Button for record type : "+M4MarketingEvent6RecordType);
							sa.assertTrue(false,"Not Able to Clicked on radio Button for record type : ");
							
						}
						ele=ip.crossIconForEventField(projectName, PageLabel.Organizer.toString(), M4Ins1, 10);
						if (clickUsingJavaScript(driver, ele, "cross icon", action.BOOLEAN)) {
							log(LogStatus.INFO, "successfully clicked on cross icon "+M4Ins1, YesNo.No);
							
						}
						else {
							log(LogStatus.INFO, "could not click on cross icon "+M4Ins1, YesNo.Yes);
							sa.assertTrue(false,"could not click on cross icon "+M4Ins1);
							
						}
						if (me.createMarketingEventFromSDG(projectName, M4MarketingEvent6Name, "", date,null, 10)) {
							log(LogStatus.INFO, "successfully created event from sdg", YesNo.Yes);

							ThreadSleep(3000);
							if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
								if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1, 10)) {
									ele=ip.getRelatedTab(projectName, RelatedTab.Events.toString(), 10);
									if (click(driver, ele, "events tab", action.SCROLLANDBOOLEAN)) {
										String monthAndYear=findMonthNameAndYear(date);
										if (ip.reachToDesiredMonthOnCalnder(projectName, monthAndYear)) {
											log(LogStatus.INFO, "successfully reached to desired month on calender", YesNo.No);
											
											int loc=ip.findLocationOfDate(projectName, date.split("/")[1]);
											ele=ip.getEventPresentOnCalender(projectName, date.split("/")[1], loc,7);
												if (ele==null) {
													log(LogStatus.INFO, "successfully verified absence of event name on calendar: "+M4MarketingEvent6Name, YesNo.Yes);
	
												}else {
													log(LogStatus.ERROR, "event name should not be present but it is: "+M4MarketingEvent6Name,YesNo.Yes);
													sa.assertTrue(false,"event name should not be present but it is: "+M4MarketingEvent6Name);
												}
										}else {
											log(LogStatus.ERROR, "could not reach to desired month of event, so cannot verify event", YesNo.Yes);
											sa.assertTrue(false,"could not reach to desired month of event, so cannot verify event" );
										}
								}else {
									log(LogStatus.ERROR, "could not click on events tab", YesNo.Yes);
									sa.assertTrue(false,"could not click on events tab" );
								}
							}else {
								log(LogStatus.ERROR, M4Ins1+" could not be found on entity tab", YesNo.Yes);
								sa.assertTrue(false,M4Ins1+" could not be found on entity tab" );
							}
						}else {
							log(LogStatus.ERROR, "could not click on entity tab", YesNo.Yes);
							sa.assertTrue(false,"could not click on entity tab" );
						}
					}else {
						log(LogStatus.ERROR, "could not create "+M4MarketingEvent6Name, YesNo.Yes);
						sa.assertTrue(false,"could not create "+M4MarketingEvent6Name );
					}
				}else {
					log(LogStatus.ERROR, "new marketing button is not clickable", YesNo.Yes);
					sa.assertTrue(false,"new marketing button is not clickable" );
				}
				}else {
					log(LogStatus.ERROR, "could not click on events tab", YesNo.Yes);
					sa.assertTrue(false,"could not click on events tab" );
				}
			}else {
				log(LogStatus.ERROR, M4Ins1+" could not be found on entity tab", YesNo.Yes);
				sa.assertTrue(false,M4Ins1+" could not be found on entity tab" );
			}
		}else {
			log(LogStatus.ERROR, "could not click on entity tab", YesNo.Yes);
			sa.assertTrue(false,"could not click on entity tab" );
		}
		if (ip.clickOnTab(projectName, TabName.Object5Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4MarketingEvent6Name, 10)) {
				if (ip.clickOnShowMoreActionDownArrow(projectName, PageName.Object5Page, ShowMoreActionDropDownList.Edit, 10)) {
					if (sendKeys(driver, me.getOrganizerName(projectName, 60), M4MarketingEvent1Organizer, "organizer Name",
							action.SCROLLANDBOOLEAN)) {
						ThreadSleep(1000);
						if (click(driver,FindElement(driver,"//*[@title='"+M4MarketingEvent1Organizer+"']","organizer Name List", action.BOOLEAN, 30),
								M4MarketingEvent1Organizer + "   :   Company Name", action.BOOLEAN)) {
							log(LogStatus.INFO, M4MarketingEvent1Organizer + "  is present in list.", YesNo.Yes);
							
						} else {
							log(LogStatus.ERROR, M4MarketingEvent1Organizer + "  is not present in the list.", YesNo.Yes);
							sa.assertTrue(false,M4MarketingEvent1Organizer + "  is not present in the list." );
								
						}

					} else {
						log(LogStatus.ERROR,"Not able to enter organizer name", YesNo.Yes);
						sa.assertTrue(false,"Not able to enter organizer name" );
					}
					if (click(driver, me.getCustomTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "clicked on save button", YesNo.Yes);
						
					}else {
						log(LogStatus.ERROR, "could not click on save button", YesNo.Yes);
						sa.assertTrue(false,"could not click on save button" );
						
					}
				}else {
					log(LogStatus.ERROR, "could not click on edit button, so cannot edit event", YesNo.Yes);
					sa.assertTrue(false,"could not click on edit button, so cannot edit event" );
					
				}
			}else {
					log(LogStatus.ERROR, M4MarketingEvent6Name+" could not be found on event tab", YesNo.Yes);
					sa.assertTrue(false,M4MarketingEvent6Name+" could not be found on event tab" );
				}
		}else {
			log(LogStatus.ERROR, "could not click on event tab", YesNo.Yes);
			sa.assertTrue(false,"could not click on event tab" );
		}
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1, 10)) {
				ele=ip.getRelatedTab(projectName, RelatedTab.Events.toString(), 10);
				if (click(driver, ele, "events tab", action.SCROLLANDBOOLEAN)) {
					String monthAndYear=findMonthNameAndYear(date);
					if (ip.reachToDesiredMonthOnCalnder(projectName, monthAndYear)) {
						log(LogStatus.INFO, "successfully reached to desired month on calender", YesNo.No);
						
						int loc=ip.findLocationOfDate(projectName, date.split("/")[1]);
						ele=ip.getEventPresentOnCalender(projectName, date.split("/")[1], loc,7);
						if (ele!=null) {
							if (ele.getAttribute("title").equalsIgnoreCase(M4MarketingEvent6Name)) {
								log(LogStatus.INFO, "successfully verified event name on calendar after adding entity name", YesNo.Yes);

							}else {
								log(LogStatus.ERROR, "event name not matched. expected: "+M4MarketingEvent6Name+"\nactual: "+ele.getAttribute("title"), YesNo.Yes);
								sa.assertTrue(false,"event name not matched. expected: "+M4MarketingEvent6Name+"\nactual: "+ele.getAttribute("title"));
							}
						}else {
							log(LogStatus.ERROR, "could not create event from sdg", YesNo.Yes);
							sa.assertTrue(false,"could not create event from sdg" );
						}
					}else {
						log(LogStatus.ERROR, "could not reach to desired month of event, so cannot verify event", YesNo.Yes);
						sa.assertTrue(false,"could not reach to desired month of event, so cannot verify event" );
					}
			}else {
				log(LogStatus.ERROR, "could not click on events tab", YesNo.Yes);
				sa.assertTrue(false,"could not click on events tab" );
			}
		}else {
			log(LogStatus.ERROR, M4Ins1+" could not be found on entity tab", YesNo.Yes);
			sa.assertTrue(false,M4Ins1+" could not be found on entity tab" );
		}
	}else {
		log(LogStatus.ERROR, "could not click on entity tab", YesNo.Yes);
		sa.assertTrue(false,"could not click on entity tab" );
	}
	lp.CRMlogout();
	sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M4tc041_VerifyButtonsOnCalender(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		MarketingEventPageBusinessLayer me = new MarketingEventPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		String date=previousOrForwardDate(3, "M/d/YYYY");;
		String date2=previousOrForwardMonthAccordingToTimeZone(1, "M/d/YYYY", BasePageErrorMessage.AmericaLosAngelesTimeZone);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentID=null;
		WebElement ele=null;
		String head[]={todaysDateSingleDigit.split("/")[2],findMonthNameAndYear(todaysDateSingleDigit),findCurrentWeek("M/d/YYYY",BasePageErrorMessage.AmericaLosAngelesTimeZone),findMonthDateCommaYear(todaysDateSingleDigit)};
		CalenderButton cb[] = {CalenderButton.Year,CalenderButton.Month,CalenderButton.Week,CalenderButton.Day};
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1, 10)) {
				ele=ip.getRelatedTab(projectName, RelatedTab.Events.toString(), 10);
				if (click(driver, ele, "events tab", action.SCROLLANDBOOLEAN)) {
					ThreadSleep(3000);
					for (CalenderButton button1:cb) {
						ele=ip.calenderButtons(projectName, button1);
						if (ele!=null) {
							log(LogStatus.ERROR, "successfully verified "+button1, YesNo.Yes);
								
						}
						else {
							log(LogStatus.ERROR, "could not verify "+button1, YesNo.Yes);
							sa.assertTrue(false,"could not verify "+button1 );
						}
					}
					int i =0;
					for (CalenderButton button1:cb) {

						ele=ip.calenderButtons(projectName, button1);
						if (click(driver, ele, button1+" button", action.BOOLEAN)) {

							ele=ip.getcalenderHeader(projectName, 10);
							if (ele!=null) {
								if (ele.getText().trim().equalsIgnoreCase(head[i])) {
									log(LogStatus.INFO, "successfully verified "+head[i], YesNo.Yes);

								}else {
									log(LogStatus.ERROR, "could not verify calender header: expected:"+head[i]+"\nactual: "+ele.getText(), YesNo.Yes);
									sa.assertTrue(false,"could not verify calender header: expected:"+head[i]+"\nactual: "+ele.getText() );
								}

								String xpath = "";
								if ((i==0)||(i==2)) {
									xpath = "//div[contains(@class,'Fullcalendar')]//a[text()='"+M4MarketingEvent2Name+"']";
								}
								else if(i==1)
									xpath = "//div[contains(@class,'Fullcalendar')]//a[@title='"+M4MarketingEvent2Name+"']";
								else if(i==3)
									xpath = "//div[contains(@class,'Fullcalendar')]//a[@title='"+M4MarketingEvent4Name+"']";

								ele=FindElement(driver, xpath, "event name", action.SCROLLANDBOOLEAN, 10);
								ele=isDisplayed(driver, ele, "visibility", 10, "event name");

								if (ele!=null) {
									if (click(driver, ele, "event name", action.SCROLLANDBOOLEAN)) {
										parentID=switchOnWindow(driver);
										if (parentID!=null) {

											ele=ip.geteventInviteesHeader(projectName, 10);
											if (ele!=null) {
												log(LogStatus.INFO, "event invitees header is successfully verified", YesNo.Yes);

											}else {
												log(LogStatus.ERROR, "event invitees header could not be verified", YesNo.Yes);
												sa.assertTrue(false,"event invitees header could not be verified" );
											}

											driver.close();
											driver.switchTo().window(parentID);
										}else {
											log(LogStatus.ERROR, "new window could not be find", YesNo.Yes);
											sa.assertTrue(false,"new window could not be find" );
										}
									}else {
										log(LogStatus.ERROR, M4MarketingEvent2Name+" event name could not be clicked", YesNo.Yes);
										sa.assertTrue(false,M4MarketingEvent2Name+" event name could not be clicked" );
									}
								}else {
									log(LogStatus.ERROR, M4MarketingEvent2Name+" event name could not be found", YesNo.Yes);
									sa.assertTrue(false,M4MarketingEvent2Name+" event name could not be found" );
								}
							}else {
								log(LogStatus.ERROR, "date header is not present", YesNo.Yes);
								sa.assertTrue(false,"date header is not present" );
							}
						}else {
							log(LogStatus.ERROR, button1+" button is not present", YesNo.Yes);
							sa.assertTrue(false,button1+" button is not present" );
						}
						i++;
					}
				}else {
					log(LogStatus.ERROR, "events tab is not clickable", YesNo.Yes);
					sa.assertTrue(false,"events tab is not clickable" );
				}
			}else {
				log(LogStatus.ERROR, M4Ins1+" entity is not present on entity page", YesNo.Yes);
				sa.assertTrue(false,M4Ins1+" entity is not present on entity page" );
			}
		}else {
			log(LogStatus.ERROR, "entity tab is not clickable", YesNo.Yes);
			sa.assertTrue(false,"entity tab is not clickable" );
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M4tc042_VerifyEventsAfterEditDate(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		MarketingEventPageBusinessLayer me = new MarketingEventPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		String twodaysdate=previousOrForwardDate(2, "M/d/YYYY");
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentID=null;
		WebElement ele=null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object5Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4MarketingEvent2Name, 10)) {
					ThreadSleep(3000);
					if (ip.clickOnShowMoreActionDownArrow(projectName, PageName.Object5Page, ShowMoreActionDropDownList.Edit, 10)) {
						ele = me.getLabelTextBox(projectName, PageName.Object5Page.toString(), PageLabel.Date.toString(), 10);
						if (sendKeys(driver,ele, todaysDateSingleDigit, todaysDateSingleDigit, action.BOOLEAN)) {
							appLog.info("Successfully Entered value on date TextBox : "+todaysDateSingleDigit);		
							ThreadSleep(1000);
						
							if (click(driver, me.getCustomTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
								appLog.info("clicked on save button");

							}else {
								log(LogStatus.ERROR, "could not click on save button", YesNo.Yes);
								sa.assertTrue(false,"could not click on save button" );
							}
						}else {
							log(LogStatus.ERROR, "date textbox is not visible", YesNo.Yes);
							sa.assertTrue(false,"date textbox is not visible" );
						}
					}else {
						log(LogStatus.ERROR, "edit button is not clickable", YesNo.Yes);
						sa.assertTrue(false,"edit button is not clickable" );
					}
			}else {
				log(LogStatus.ERROR, M4MarketingEvent2Name+" event is not found on events tab", YesNo.Yes);
				sa.assertTrue(false,M4MarketingEvent2Name+" event is not found on events tab" );
			}
		}else {
			log(LogStatus.ERROR, "marketing events tab is not clickable", YesNo.Yes);
			sa.assertTrue(false,"marketing events tab is not clickable" );
		}
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1, 10)) {
				ele=ip.getRelatedTab(projectName, RelatedTab.Events.toString(), 10);
				if (click(driver, ele, "events tab", action.SCROLLANDBOOLEAN)) {
					String monthAndYear=findMonthNameAndYear(twodaysdate);
					if (ip.reachToDesiredMonthOnCalnder(projectName, monthAndYear)) {
						log(LogStatus.INFO, "successfully reached to desired month on calender", YesNo.No);

						int loc=ip.findLocationOfDate(projectName, twodaysdate.split("/")[1]);
						ele=ip.getEventPresentOnCalender(projectName, twodaysdate.split("/")[1], loc,7);
						if (ele==null) {
							log(LogStatus.INFO, "successfully verified absence of event name on calendar: "+M4MarketingEvent2Name+" for date "+twodaysdate, YesNo.Yes);

						}else {
							log(LogStatus.ERROR, "event name should not be present but it is: "+M4MarketingEvent2Name+" for date "+twodaysdate,YesNo.Yes);
							sa.assertTrue(false,"event name should not be present but it is: "+M4MarketingEvent2Name+" for date "+twodaysdate);
						}
					}else {
						log(LogStatus.ERROR, "could not reach to desired month of event, so cannot verify event", YesNo.Yes);
						sa.assertTrue(false,"could not reach to desired month of event, so cannot verify event" );
					}
					if (ip.getcalenderHeader(projectName, 10).getText().trim().equalsIgnoreCase(findMonthNameAndYear(todaysDateSingleDigit))) {
						log(LogStatus.INFO, "successfully reached to desired month on calender for todays date", YesNo.No);
						
					}else {
						ele=ip.getpreviousButtonOnCalender(projectName, 10);
						click(driver, ele, "previous button", action.SCROLLANDBOOLEAN);
					}
					int loc=ip.findLocationOfDate(projectName, todaysDateSingleDigit.split("/")[1]);
					ele=ip.getEventPresentOnCalender(projectName, todaysDateSingleDigit.split("/")[1], loc,7);
					if (ele!=null) {
						if (ele.getText().trim().equalsIgnoreCase(M4MarketingEvent2Name))
						log(LogStatus.INFO, "successfully verified presence of event name on calendar: "+M4MarketingEvent2Name+" for date "+todaysDateSingleDigit, YesNo.Yes);
						else {
							log(LogStatus.ERROR, "event name is absent: "+M4MarketingEvent2Name+" for date "+todaysDateSingleDigit,YesNo.Yes);
							sa.assertTrue(false,"event name is absent: "+M4MarketingEvent2Name+" for date "+todaysDateSingleDigit);
						}
					}else {
						log(LogStatus.ERROR, "event name is absent: "+M4MarketingEvent2Name+" for date "+todaysDateSingleDigit,YesNo.Yes);
						sa.assertTrue(false,"event name is absent: "+M4MarketingEvent2Name+" for date "+todaysDateSingleDigit);
					}
				}else {
					log(LogStatus.ERROR, "events tab is not clickable", YesNo.Yes);
					sa.assertTrue(false,"events tab is not clickable" );
				}
			}else {
				log(LogStatus.ERROR, M4Ins1+" entity is not found on entity tab", YesNo.Yes);
				sa.assertTrue(false,M4Ins1+" entity is not found on entity tab" );
			}
		}else {
			log(LogStatus.ERROR, "entity tab is not clickable", YesNo.Yes);
			sa.assertTrue(false,"entity tab is not clickable" );
		}
		lp.CRMlogout();
		sa.assertAll();
		}
	@Parameters({ "projectName"})
	@Test
	public void M4tc043_VerifyCalenderDateCellbycreatingMultipleEvents(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		MarketingEventPageBusinessLayer me = new MarketingEventPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		String fourdaysdate=previousOrForwardDate(4, "M/d/YYYY");
		String fivedaysdate=previousOrForwardDate(5, "M/d/YYYY");
		
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentID=null,name,recordtype, monthAndYear;
		WebElement ele=null;
		List<WebElement> li=null;
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1, 10)) {
				ele=ip.getRelatedTab(projectName, RelatedTab.Events.toString(), 10);
				if (click(driver, ele, "events tab", action.SCROLLANDBOOLEAN)) {
					ThreadSleep(3000);
					for(int i = 11;i<16;i++) {
						ThreadSleep(3000);
						name=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME"+i, excelLabel.Marketing_Event_Name);
						recordtype=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME"+i, excelLabel.Record_Type);
						
						monthAndYear=findMonthNameAndYear(fourdaysdate);
						if (ip.reachToDesiredMonthOnCalnder(projectName, monthAndYear)) {
							log(LogStatus.INFO, "successfully reached to desired month on calender", YesNo.No);
							appLog.error(">>>");
							sc.next();
							if (me.createMarketingEventFromCalendar(projectName, name,recordtype, "", M4Ins1, 10)) {
							log(LogStatus.INFO, "successfully created event from sdg", YesNo.No);

							}else {
								log(LogStatus.ERROR, "could not create ME "+name, YesNo.Yes);
								sa.assertTrue(false,"could not create ME "+name );
							}

							
						}else {
							log(LogStatus.ERROR, "could not reach to desired month of event, so cannot verify event", YesNo.Yes);
							sa.assertTrue(false,"could not reach to desired month of event, so cannot verify event" );
						}
					}
					boolean flag=false;
					String filesName=M4MarketingEvent11Name+","+M4MarketingEvent12Name+","+M4MarketingEvent13Name+","+M4MarketingEvent14Name+","+M4MarketingEvent15Name;
					ele=ip.plusMoreButtonOnCalendar(projectName, 3, 10);
					click(driver, ele, "plus button", action.SCROLLANDBOOLEAN);
					li=ip.getListOfEvents(projectName, findMonthDateCommaYear(fourdaysdate), 7);
					List<String> check=compareMultipleListOnBasisOfTitle(driver, filesName, li);
					if (check.size()==0) {
						log(LogStatus.INFO, "successfully verified all events present on date"+fourdaysdate, YesNo.No);
						
					}
					else {
						for (int i = 0;i<check.size();i++) {
							log(LogStatus.ERROR, "not found "+check.get(i), YesNo.No);
							sa.assertTrue(false,"not found "+check.get(i));
								
						}
					}
					
					for(int i = 16;i<20;i++) {
						ThreadSleep(3000);
						name=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME"+i, excelLabel.Marketing_Event_Name);
						recordtype=ExcelUtils.readData(phase1DataSheetFilePath,"MarketingEvent",excelLabel.Variable_Name, "M4ME"+i, excelLabel.Record_Type);
						
						monthAndYear=findMonthNameAndYear(fivedaysdate);
						if (ip.reachToDesiredMonthOnCalnder(projectName, monthAndYear)) {
							log(LogStatus.INFO, "successfully reached to desired month on calender", YesNo.No);
							appLog.error(">>>");
							sc.next();
							if (me.createMarketingEventFromCalendar(projectName, name,recordtype, "", M4Ins1, 10)) {
							log(LogStatus.INFO, "successfully created event from sdg", YesNo.No);

							}else {
								log(LogStatus.ERROR, "could not create ME "+name, YesNo.Yes);
								sa.assertTrue(false,"could not create ME "+name );
							}

							
						}else {
							log(LogStatus.ERROR, "could not reach to desired month of event, so cannot verify event", YesNo.Yes);
							sa.assertTrue(false,"could not reach to desired month of event, so cannot verify event" );
						}
					}
					flag=false;
					filesName=M4MarketingEvent16Name+","+M4MarketingEvent17Name+","+M4MarketingEvent18Name+","+M4MarketingEvent19Name;
					ele=ip.plusMoreButtonOnCalendar(projectName, 2, 10);
					click(driver, ele, "plus button", action.SCROLLANDBOOLEAN);
					li=ip.getListOfEvents(projectName, findMonthDateCommaYear(fivedaysdate), 7);
					check.clear();
					check=compareMultipleListOnBasisOfTitle(driver, filesName, li);
					if (check.size()==0) {
						log(LogStatus.INFO, "successfully verified all events present on date"+fivedaysdate, YesNo.No);
						
					}
					else {
						for (int i = 0;i<check.size();i++) {
							log(LogStatus.ERROR, "not found "+check.get(i), YesNo.No);
							sa.assertTrue(false,"not found "+check.get(i));
								
						}
					}
					
					
				}else {
					log(LogStatus.ERROR, "events related tab is not clickable", YesNo.Yes);
					sa.assertTrue(false,"events related tab is not clickable" );
				}
			}else {
				log(LogStatus.ERROR, M4Ins1+" entity is not clickable", YesNo.Yes);
				sa.assertTrue(false,M4Ins1+" entity is not clickable" );
			}
		}else {
			log(LogStatus.ERROR, "entity tab is not clickable", YesNo.Yes);
			sa.assertTrue(false,"entity tab is not clickable" );
		}
		
		
		if (ip.clickOnTab(projectName, TabName.Object5Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4MarketingEvent11Name, 10)) {
				ThreadSleep(3000);
				if (ip.clickOnShowMoreActionDownArrow(projectName, PageName.Object5Page, ShowMoreActionDropDownList.Edit, 10)) {
					ele = me.getLabelTextBox(projectName, PageName.Object5Page.toString(), PageLabel.Date.toString(), 10);
					if (sendKeys(driver,ele, fivedaysdate, fivedaysdate, action.BOOLEAN)) {
						appLog.info("Successfully Entered value on date TextBox : "+fivedaysdate);		
						ThreadSleep(1000);
					
						if (click(driver, me.getCustomTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
							appLog.info("clicked on save button");

						}else {
							log(LogStatus.ERROR, "could not click on save button", YesNo.Yes);
							sa.assertTrue(false,"could not click on save button" );
						}
					}else {
						log(LogStatus.ERROR, "date textbox is not visible", YesNo.Yes);
						sa.assertTrue(false,"date textbox is not visible" );
					}
				}else {
					log(LogStatus.ERROR, "edit button is not clickable", YesNo.Yes);
					sa.assertTrue(false,"edit button is not clickable" );
				}
		}else {
			log(LogStatus.ERROR, M4MarketingEvent11Name+" event is not found on events tab", YesNo.Yes);
			sa.assertTrue(false,M4MarketingEvent11Name+" event is not found on events tab" );
		}
	}else {
		log(LogStatus.ERROR, "marketing events tab is not clickable", YesNo.Yes);
		sa.assertTrue(false,"marketing events tab is not clickable" );
	}
		boolean flag=false;
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1, 10)) {
				ele=ip.getRelatedTab(projectName, RelatedTab.Events.toString(), 10);
				if (click(driver, ele, "events tab", action.SCROLLANDBOOLEAN)) {
					ThreadSleep(3000);
					ele=ip.plusMoreButtonOnCalendar(projectName, 3, 10);
					click(driver, ele, "plus button", action.SCROLLANDBOOLEAN);
					li=ip.getListOfEvents(projectName, findMonthDateCommaYear(fivedaysdate), 7);
					for(WebElement ele1:li) {
						if(ele1.getAttribute("title").trim().equals(M4MarketingEvent11Name)){
							log(LogStatus.ERROR, "successfully found "+M4MarketingEvent11Name+" for date "+fivedaysdate, YesNo.No);
							
							flag=true;
							break;
						}
							
					}
					if(!flag){
							log(LogStatus.ERROR, "could not find event "+M4MarketingEvent11Name+" for date "+fivedaysdate, YesNo.Yes);
							sa.assertTrue(false,"could not find event "+M4MarketingEvent11Name+" for date "+fivedaysdate);
					}
				}else {
					log(LogStatus.ERROR, "related events tab is not clickable", YesNo.Yes);
					sa.assertTrue(false,"related events tab is not clickable" );
				}
			}else {
				log(LogStatus.ERROR, M4Ins1+" entity is not found on entity tab", YesNo.Yes);
				sa.assertTrue(false,M4Ins1+" entity is not found on entity tab" );
			}
		}else {
			log(LogStatus.ERROR, "entity tab is not clickable", YesNo.Yes);
			sa.assertTrue(false,"entity tab is not clickable" );
		}
		if (ip.clickOnTab(projectName, TabName.Object5Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4MarketingEvent11Name, 10)) {
				ThreadSleep(3000);
				if (ip.clickOnShowMoreActionDownArrow(projectName, PageName.Object5Page, ShowMoreActionDropDownList.Delete, 10)) {
					ele = me.getDeleteButtonPopUp(projectName, 10);
					if (click(driver,ele, "delete",  action.BOOLEAN)) {
						appLog.info("Successfully deleted event : "+M4MarketingEvent11Name);		
						ThreadSleep(1000);
					
					}else {
						log(LogStatus.ERROR, "delete button on popup is not clickable", YesNo.Yes);
						sa.assertTrue(false,"delete button on popup is not clickable" );
					}
				}else {
					log(LogStatus.ERROR, "delete button is not clickable", YesNo.Yes);
					sa.assertTrue(false,"delete button is not clickable" );
				}
		}else {
			log(LogStatus.ERROR, M4MarketingEvent11Name+" event is not found on events tab", YesNo.Yes);
			sa.assertTrue(false,M4MarketingEvent11Name+" event is not found on events tab" );
		}
	}else {
		log(LogStatus.ERROR, "marketing events tab is not clickable", YesNo.Yes);
		sa.assertTrue(false,"marketing events tab is not clickable" );
	}
		flag=true;
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
		if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1, 10)) {
			ele=ip.getRelatedTab(projectName, RelatedTab.Events.toString(), 10);
			if (click(driver, ele, "events tab", action.SCROLLANDBOOLEAN)) {
				ThreadSleep(3000);
				ele=ip.plusMoreButtonOnCalendar(projectName, 2, 10);
				click(driver, ele, "plus button", action.SCROLLANDBOOLEAN);
				li=ip.getListOfEvents(projectName, findMonthDateCommaYear(fourdaysdate), 7);
				for(WebElement ele1:li) {
					if(ele1.getAttribute("title").trim().equals(M4MarketingEvent11Name)){
						log(LogStatus.ERROR, "event found "+M4MarketingEvent11Name+" for date "+fivedaysdate+" but it should not be there", YesNo.Yes);
						sa.assertTrue(false,"event found "+M4MarketingEvent11Name+" for date "+fivedaysdate+" but it should not be there");
						flag=false;
						
						break;
					}
						
				}
				if(flag){
					log(LogStatus.INFO, "successfully verified absence of "+M4MarketingEvent11Name, YesNo.Yes);
					
				}
			}else {
				log(LogStatus.ERROR, "related events tab is not clickable", YesNo.Yes);
				sa.assertTrue(false,"related events tab is not clickable" );
			}
		}else {
			log(LogStatus.ERROR, M4Ins1+" entity is not found on entity tab", YesNo.Yes);
			sa.assertTrue(false,M4Ins1+" entity is not found on entity tab" );
		}
	}else {
		log(LogStatus.ERROR, "entity tab is not clickable", YesNo.Yes);
		sa.assertTrue(false,"entity tab is not clickable" );
	}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M4tc044_VerifyAddingFilterByEditPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		MarketingEventPageBusinessLayer me = new MarketingEventPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentID=null,value,field, monthAndYear;
		WebElement ele=null;
		Actions actions = new Actions(driver);
		List<WebElement> li=null;
		String fieldValues[]={EditPageLabel.Calendar_Filter_1.toString()+"<break>"+"Industry_New__c",EditPageLabel.Calendar_Filter_2.toString()+"<break>"+"Status__c",
				EditPageLabel.Calendar_Filter_3.toString()+"<break>"+"Type__c"
		};
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1, 10)) {
				if (ep.clickOnEditPageLink()) {
					log(LogStatus.INFO, "successfully reached edit page", YesNo.No);

					ThreadSleep(3000);
					switchToFrame(driver, 10, ep.getEditPageFrame(projectName, 10));
					ele=ep.getRelatedTab(ProjectName.PEEdge.toString(), PageName.Object1Page,RelatedTab.Events, 10);
					if (click(driver, ele, "events related ", action.SCROLLANDBOOLEAN)) {
						ThreadSleep(5000);
						if (ep.clickOnCalenderEditPage(projectName)){
						ThreadSleep(3000);
						switchToDefaultContent(driver);
							for (String fieldValue:fieldValues) {
								value=fieldValue.split("<break>")[1];
								field=fieldValue.split("<break>")[0];
								if(sendKeys(driver, ep.getFieldTextbox(projectName, field, 10), value, field, action.BOOLEAN)) {
									log(LogStatus.INFO, "field : "+field+", value: "+value, YesNo.No);
								}else {
									log(LogStatus.ERROR, "Not able to enter : "+value+" on"+field, YesNo.Yes);
									sa.assertTrue(false, "Not able to enter : "+value+" on"+field );
								}
							}
							if(click(driver, ep.getCustomTabSaveBtn(projectName, 10), "save button", action.BOOLEAN)) {
								log(LogStatus.INFO, "clicked on save button", YesNo.No);
								ThreadSleep(2000);
								actions.moveToElement(ep.getBackButton(10)).build().perform();
								ThreadSleep(2000);
								if(clickUsingJavaScript(driver, ep.getBackButton(10), "back button", action.BOOLEAN)) {
									log(LogStatus.PASS, "clicked on back button", YesNo.No);

								}else {
									log(LogStatus.ERROR, "Not able to click on back button so cannot back on page ", YesNo.Yes);
									sa.assertTrue(false, "Not able to click on back button so cannot back on page ");
								}
							}else {
								log(LogStatus.ERROR, "Not able to click on save button so cannot add filter fields", YesNo.No);
								sa.assertTrue(false, "Not able to click on save button so cannot add filter fields");

							}
						}else {
							log(LogStatus.ERROR, "Not able to find calendar on edit page", YesNo.No);
							sa.assertTrue(false, "Not able to find calendar on edit page");

						}
					}else {
						log(LogStatus.ERROR, "Events related tab is not clickable", YesNo.No);
						sa.assertTrue(false, "Events related tab is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "edit page link is not clickabl", YesNo.No);
					sa.assertTrue(false, "edit page link is not clickabl");
				}
			}else {
				log(LogStatus.ERROR, M4Ins1+" entity record is not found", YesNo.No);
				sa.assertTrue(false, M4Ins1+" entity record is not found");
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
	public void M4tc045_VerifyCalenderByChangingTitleHighlightColor(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		MarketingEventPageBusinessLayer me = new MarketingEventPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentID=null,value="", monthAndYear;
		WebElement ele=null;
		Actions actions = new Actions(driver);
		List<WebElement> li=null;
		String color="RecordType.DeveloperName;Firm_Event:#6464FF";
		String field=EditPageLabel.Title_Highlight_Color.toString();
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1, 10)) {
				if (ep.clickOnEditPageLink()) {
					log(LogStatus.INFO, "successfully reached edit page", YesNo.No);

					ThreadSleep(3000);
					switchToFrame(driver, 10, ep.getEditPageFrame(projectName, 10));
					ele=ep.getRelatedTab(ProjectName.PEEdge.toString(), PageName.Object1Page,RelatedTab.Events, 10);
					if (click(driver, ele, "events related ", action.SCROLLANDBOOLEAN)) {
						ThreadSleep(5000);
						if (ep.clickOnCalenderEditPage(projectName)){
						ThreadSleep(3000);
						switchToDefaultContent(driver);
						if(sendKeys(driver, ep.getFieldTextbox(projectName, field, 10), color, field, action.BOOLEAN)) {
							log(LogStatus.INFO, "field : "+field+", value: "+color, YesNo.No);
						}else {
							log(LogStatus.ERROR, "Not able to enter : "+color+" on"+field, YesNo.Yes);
							sa.assertTrue(false, "Not able to enter : "+color+" on"+field );
						}
						if(click(driver, ep.getCustomTabSaveBtn(projectName, 10), "save button", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on save button", YesNo.No);
							ThreadSleep(2000);
							actions.moveToElement(ep.getBackButton(10)).build().perform();
							ThreadSleep(2000);
							if(clickUsingJavaScript(driver, ep.getBackButton(10), "back button", action.BOOLEAN)) {
								log(LogStatus.PASS, "clicked on back button", YesNo.No);

							}else {
								log(LogStatus.ERROR, "Not able to click on back button so cannot back on page ", YesNo.Yes);
								sa.assertTrue(false, "Not able to click on back button so cannot back on page ");
							}
						}else {
							log(LogStatus.ERROR, "Not able to click on save button so cannot add filter fields", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on save button so cannot add filter fields");

						}
						}else {
							log(LogStatus.ERROR, "Not able to click on calender button so cannot check change of colours", YesNo.Yes);
							sa.assertTrue(false, "Not able to click on calender button so cannot check change of colours");

						}
					}else {
						log(LogStatus.ERROR, "Events related tab is not clickable", YesNo.Yes);
						sa.assertTrue(false, "Events related tab is not clickable");

					}
					switchToDefaultContent(driver);
				}else {
					log(LogStatus.ERROR, "edit page link is not clickable", YesNo.Yes);
					sa.assertTrue(false, "edit page link is not clickable");

				}
				
				ThreadSleep(5000);
			}else {
				log(LogStatus.ERROR,M4Ins1+ " is not found on entity page", YesNo.Yes);
				sa.assertTrue(false,M4Ins1+ " is not found on entity page");

			}
		}else {
			log(LogStatus.ERROR, "entity tab is not found", YesNo.Yes);
			sa.assertTrue(false, "entity tab is not found");

		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M4tc046_VerifyCalenderByChangingTimezoneForUsers(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		MarketingEventPageBusinessLayer me = new MarketingEventPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentID=null,value="", monthAndYear;
		WebElement ele=null;
		boolean flag=false;
		String newZeland="(GMT+13:00) New Zealand Daylight Time (Pacific/Auckland)";
		Actions actions = new Actions(driver);
		List<WebElement> li=null;
		String field=EditPageLabel.Title_Highlight_Color.toString();
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink("", Mode.Lightning.toString())) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
				log(LogStatus.INFO, "Able to switch on new window", YesNo.No);
				ThreadSleep(100);
				if(setup.searchStandardOrCustomObject(environment,mode, object.Users)) {
					log(LogStatus.INFO, "click on Object : "+object.Users, YesNo.No);
					ThreadSleep(2000);

					if(setup.clickOnEditBtnForCRMUser(driver, crmUser1LastName, crmUser1FirstName, 20)) {
						log(LogStatus.INFO, "Click on edit Button "+crmUser1LastName+","+crmUser1FirstName, YesNo.No);
						ThreadSleep(2000);

						if (selectVisibleTextFromDropDown(driver, setup.gettimezoneDropdownList(10), "Timezone DropDown List",newZeland)) {
							log(LogStatus.INFO, "selected visbible text from the Timezone dropdown "+newZeland, YesNo.No);
							ThreadSleep(2000);

							if (click(driver, setup.getSaveButton(20), "Save Button",action.SCROLLANDBOOLEAN)) {
								switchToDefaultContent(driver);
								ThreadSleep(5000);
								switchToFrame(driver, 20, setup.getSetUpPageIframe(20));
								scrollDownThroughWebelement(driver, setup.getRegionDropdownList("Time Zone", newZeland, 10), newZeland);
								if (setup.getRegionDropdownList("Time Zone", newZeland, 10)!=null) {
									log(LogStatus.INFO, "Time Zone Value verified "+newZeland, YesNo.No);
									flag=true;
								} else {
									log(LogStatus.ERROR, "Time Zone Value not verified "+newZeland, YesNo.Yes);
									sa.assertTrue(false, "Time Zone Value not verified "+newZeland);
								}
							} else {
								log(LogStatus.ERROR, "Not Able to Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName, YesNo.Yes);
								sa.assertTrue(false, "Not Able to Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName);
							}
						} else {
							log(LogStatus.ERROR, "Not Able to Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName, YesNo.Yes);
							sa.assertTrue(false, "Not Able to Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName);
						}
					}else {
						log(LogStatus.ERROR, "edit button is not clickable",YesNo.Yes);
						sa.assertTrue(false, "edit button is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "Not able to search/click on "+object.Users, YesNo.Yes);
					sa.assertTrue(false, "Not able to search/click on "+object.Users);
				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch, so cannot Set the Time Zone as "+newZeland, YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch, so cannot Set the Time Zone as "+newZeland);
			}
		}else {
			log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
			sa.assertTrue(false, "Not able to click on setup link");	
		}

		lp.CRMlogout();
		driver.close();
		config(browserToLaunch);
		lp = new LoginPageBusinessLayer(driver);
		fp = new FundsPageBusinessLayer(driver);
		cp = new ContactsPageBusinessLayer(driver);
		ip = new InstitutionsPageBusinessLayer(driver);

		lp.CRMLogin(superAdminUserName, adminPassword);
		String date=findMonthDateCommaYear(todaysDateSingleDigit);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1, 10)) {
				ele=ip.getRelatedTab(projectName, RelatedTab.Events.toString(), 10);
				if (click(driver, ele, "events tab", action.SCROLLANDBOOLEAN)) {
					ele=ip.calenderButtons(projectName, CalenderButton.Day);
					if (click(driver, ele, "calendar day button", action.SCROLLANDBOOLEAN)) {
						ele=ip.getcalenderHeader(projectName, 10);
						if (ele!=null) {
							if (ele.getText().trim().equalsIgnoreCase(date)) {
								log(LogStatus.INFO, "successfully verified calender header as current date :"+date, YesNo.No);
								
							}else {
								log(LogStatus.ERROR, "could not verify calender header as current date: \nexpected: "+date+"\nactual: "+ele.getText(), YesNo.Yes);
								sa.assertTrue(false, "could not verify calender header as current date: \nexpected: "+date+"\nactual: "+ele.getText());	
							}
						}else {
							log(LogStatus.ERROR, "could not find date on calender header, so cannot verify date", YesNo.Yes);
							sa.assertTrue(false, "could not find date on calender header, so cannot verify date");	
						}
					}else {
						log(LogStatus.ERROR, "could not click on day button on calender", YesNo.Yes);
						sa.assertTrue(false, "could not click on day button on calender");	
					}
				}else {
					log(LogStatus.ERROR, "events related tab is not clickable", YesNo.Yes);
					sa.assertTrue(false, "events related tab is not clickable");	
				}
			}
			else {
				log(LogStatus.ERROR,M4Ins1+ " is not found on entity page", YesNo.Yes);
				sa.assertTrue(false,M4Ins1+ " is not found on entity page");

			}
		}else {
			log(LogStatus.ERROR, "entity tab is not found", YesNo.Yes);
			sa.assertTrue(false, "entity tab is not found");

		}

		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M4tc047_VerifyCalenderByChangingTimezoneForUsers_1_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		MarketingEventPageBusinessLayer me = new MarketingEventPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentID=null,value="", monthAndYear;
		WebElement ele=null;
		String dateMinusOne=previousOrForwardDate(-1, "M/d/YYYY");
		
		
		boolean flag=false;
		Actions actions = new Actions(driver);
		List<WebElement> li=null;
		String field=EditPageLabel.Title_Highlight_Color.toString();
		/*lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object5Tab)) {
			log(LogStatus.INFO, "successfully clicked on "+TabName.Object5Tab, YesNo.No);

			if (me.createMarketingEvent(projectName, M4MarketingEvent20Name, M4MarketingEvent20RecordType, dateMinusOne, M4MarketingEvent20Organizer, 10)) {
				log(LogStatus.INFO, "successfully created marketing event "+M4MarketingEvent20Name, YesNo.Yes);

			}else {
				log(LogStatus.ERROR, "could not create marketing event "+M4MarketingEvent20Name, YesNo.Yes);
				sa.assertTrue(false, "could not create marketing event "+M4MarketingEvent20Name);

			}
		}else {
			log(LogStatus.ERROR, "could not click on marketing event tab", YesNo.Yes);
			sa.assertTrue(false, "could not click on marketing event tab");

		}
		
		lp.CRMlogout();
		driver.close();
		config(browserToLaunch);
		lp = new LoginPageBusinessLayer(driver);
		fp = new FundsPageBusinessLayer(driver);
		cp = new ContactsPageBusinessLayer(driver);
		ip = new InstitutionsPageBusinessLayer(driver);
		ep = new EditPageBusinessLayer(driver);
		me = new MarketingEventPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object5Tab)) {
			log(LogStatus.INFO, "successfully clicked on "+TabName.Object5Tab, YesNo.No);

			if (me.createMarketingEvent(projectName, M4MarketingEvent21Name, M4MarketingEvent21RecordType, dateMinusOne, M4MarketingEvent21Organizer, 10)) {
				log(LogStatus.INFO, "successfully created marketing event "+M4MarketingEvent21Name, YesNo.Yes);

			}else {
				log(LogStatus.ERROR, "could not create marketing event "+M4MarketingEvent21Name, YesNo.Yes);
				sa.assertTrue(false, "could not create marketing event "+M4MarketingEvent21Name);

			}
		}else {
			log(LogStatus.ERROR, "could not click on marketing event tab", YesNo.Yes);
			sa.assertTrue(false, "could not click on marketing event tab");

		}lp.CRMlogout();
		driver.close();
		config(browserToLaunch);
		*/lp = new LoginPageBusinessLayer(driver);
		fp = new FundsPageBusinessLayer(driver);
		cp = new ContactsPageBusinessLayer(driver);
		ip = new InstitutionsPageBusinessLayer(driver);
		ep = new EditPageBusinessLayer(driver);
		me = new MarketingEventPageBusinessLayer(driver);
		setup=new SetupPageBusinessLayer(driver);
		
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			parentID = switchOnWindow(driver);
			if (parentID!=null) {
				log(LogStatus.INFO, "Able to switch on new window, so going to Set the User Region as "+TechonlogyCoverage, YesNo.No);
				ThreadSleep(100);
				if(setup.searchStandardOrCustomObject(environment,mode, object.Sharing_Settings)) {
					log(LogStatus.INFO, "click on Object : "+object.Users, YesNo.No);
					ThreadSleep(2000);
					switchToFrame(driver, 10, lp.getFrame(PageName.SharingSettingsPage, 10));
					ele=lp.getEditButton("",Mode.Classic.toString(), 10);
					if (click(driver, ele, "edit", action.SCROLLANDBOOLEAN)) {
						ThreadSleep(2000);
						switchToDefaultContent(driver);
						switchToFrame(driver, 10, lp.getFrame(PageName.PipelineCustomPage, 10));
						ele=setup.selectObjectDropdownOnSharingSettings(projectName, object.Marketing_Event.toString(), accessType.InternalUserAccess);
						if (selectVisibleTextFromDropDown(driver,ele , "marketing event Internal dropdown", "Private")) {
							log(LogStatus.INFO, "successfully select private in internal access dropdown", YesNo.Yes);
							
						}else {
							log(LogStatus.ERROR, "could not select private in internal access dropdown", YesNo.Yes);
							sa.assertTrue(false, "could not select private in internal access dropdown");

						}
						ele=setup.selectObjectDropdownOnSharingSettings(projectName, object.Marketing_Event.toString(), accessType.ExternalUserAccess);
						if (selectVisibleTextFromDropDown(driver,ele , "marketing event Internal dropdown", "Private")) {
							log(LogStatus.INFO, "successfully select private in internal access dropdown", YesNo.Yes);
							
						}else {
							log(LogStatus.ERROR, "could not select private in internal access dropdown", YesNo.Yes);
							sa.assertTrue(false, "could not select private in internal access dropdown");

						}
						if (click(driver, setup.getCreateUserSaveBtn_Lighting(10), "save", action.SCROLLANDBOOLEAN)) {
							ThreadSleep(5000);
						}else {
							log(LogStatus.ERROR, "save button is not clickable", YesNo.Yes);
							sa.assertTrue(false, "save button is not clickable");

						}
					}else {
						log(LogStatus.ERROR, "edit button is not clickable", YesNo.Yes);
						sa.assertTrue(false, "edit button is not clickable");

					}
				}else {
					log(LogStatus.ERROR, "not able to select sharing settings", YesNo.Yes);
					sa.assertTrue(false, "not able to select sharing settings");

				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.ERROR, "could not find new window to switch", YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");

			}
		}else {
			log(LogStatus.ERROR, "setup link is not clickable", YesNo.Yes);
			sa.assertTrue(false, "setup link is not clickable");

		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M4tc047_VerifyCalenderByChangingTimezoneForUsers_2_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		MarketingEventPageBusinessLayer me = new MarketingEventPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentID=null,value="", monthAndYear;
		WebElement ele=null;
		String dateMinusOne=previousOrForwardDate(-1, "M/d/YYYY");
		
		
		boolean flag=false;
		Actions actions = new Actions(driver);
		List<WebElement> li=null;
		String field=EditPageLabel.Title_Highlight_Color.toString();
		//lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1, 10)) {
				ele=ip.getRelatedTab(projectName, RelatedTab.Events.toString(), 10);
				if (click(driver, ele, "events tab", action.SCROLLANDBOOLEAN)) {
					monthAndYear=findMonthNameAndYear(dateMinusOne);
					if (ip.getcalenderHeader(projectName, 10).getText().trim().equalsIgnoreCase(monthAndYear)) {
						log(LogStatus.INFO, "successfully reached to desired month on calender", YesNo.No);
					}
					else {
						ele=ip.calenderButtons(projectName, CalenderButton.prev);
						click(driver,ele , "previous button", action.BOOLEAN);
						if (ip.getcalenderHeader(projectName, 10).getText().trim().equalsIgnoreCase(monthAndYear)) {
							log(LogStatus.INFO, "successfully reached to desired month on calender", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not find current month on calender", YesNo.No);
						}	
					}
					int loc=ip.findLocationOfDate(projectName, dateMinusOne.split("/")[1]);
					ele=ip.getEventPresentOnCalender(projectName, dateMinusOne.split("/")[1], loc,10);
					if (ele!=null) {
						if (ele.getAttribute("title").equalsIgnoreCase(M4MarketingEvent21Name)) {
							log(LogStatus.ERROR, M4MarketingEvent21Name+" event name should not present on calendar, but it is still present", YesNo.Yes);
							sa.assertTrue(false,M4MarketingEvent21Name+" event name should not present on calendar, but it is still present");

						}else {
							log(LogStatus.INFO, "successfully verified absence of "+M4MarketingEvent21Name, YesNo.No);
						}
					}else {
						log(LogStatus.ERROR, "could not create event from sdg", YesNo.Yes);
						sa.assertTrue(false,"could not create event from sdg" );
					}
				}else {
					log(LogStatus.ERROR, "events tab is not clickable", YesNo.Yes);
					sa.assertTrue(false,"events tab is not clickable" );
				}
			}else {
				log(LogStatus.ERROR, M4Ins1+" entity is not found on entity tab", YesNo.Yes);
				sa.assertTrue(false,M4Ins1+" entity is not found on entity tab" );
			}
		}else {
			log(LogStatus.ERROR, "entity tab is not clickable", YesNo.Yes);
			sa.assertTrue(false,"entity tab is not clickable" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M4tc047_VerifyCalenderByChangingTimezoneForUsers_3_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		MarketingEventPageBusinessLayer me = new MarketingEventPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentID=null,value="", monthAndYear;
		WebElement ele=null;
		String dateMinusOne=previousOrForwardDate(-1, "M/d/YYYY");
		
		
		boolean flag=false;
		Actions actions = new Actions(driver);
		List<WebElement> li=null;
		String field=EditPageLabel.Title_Highlight_Color.toString();
		//lp.CRMLogin(crmUser2EmailID, adminPassword, appName);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1, 10)) {
				ele=ip.getRelatedTab(projectName, RelatedTab.Events.toString(), 10);
				if (click(driver, ele, "events tab", action.SCROLLANDBOOLEAN)) {
					monthAndYear=findMonthNameAndYear(dateMinusOne);
					if (ip.getcalenderHeader(projectName, 10).getText().trim().equalsIgnoreCase(monthAndYear)) {
						log(LogStatus.INFO, "successfully reached to desired month on calender", YesNo.No);
					}
					else {
						ele=ip.calenderButtons(projectName, CalenderButton.prev);
						click(driver,ele , "previous button", action.BOOLEAN);
						if (ip.getcalenderHeader(projectName, 10).getText().trim().equalsIgnoreCase(monthAndYear)) {
							log(LogStatus.INFO, "successfully reached to desired month on calender", YesNo.No);
						}
						else {
							log(LogStatus.ERROR, "could not find current month on calender", YesNo.No);
						}	
					}
					int loc=ip.findLocationOfDate(projectName, dateMinusOne.split("/")[1]);
					ele=ip.getEventPresentOnCalender(projectName, dateMinusOne.split("/")[1], loc,10);
					if (ele!=null) {
						if (ele.getAttribute("title").equalsIgnoreCase(M4MarketingEvent20Name)) {
							log(LogStatus.ERROR, M4MarketingEvent20Name+" event name should not present on calendar, but it is still present", YesNo.Yes);
							sa.assertTrue(false,M4MarketingEvent20Name+" event name should not present on calendar, but it is still present");

						}else {
							log(LogStatus.INFO, "successfully verified absence of "+M4MarketingEvent20Name, YesNo.No);
						}
					}else {
						log(LogStatus.ERROR, "could not create event from sdg", YesNo.Yes);
						sa.assertTrue(false,"could not create event from sdg" );
					}
				}else {
					log(LogStatus.ERROR, "events tab is not clickable", YesNo.Yes);
					sa.assertTrue(false,"events tab is not clickable" );
				}
			}else {
				log(LogStatus.ERROR, M4Ins1+" entity is not found on entity tab", YesNo.Yes);
				sa.assertTrue(false,M4Ins1+" entity is not found on entity tab" );
			}
		}else {
			log(LogStatus.ERROR, "entity tab is not clickable", YesNo.Yes);
			sa.assertTrue(false,"entity tab is not clickable" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	@Parameters({ "projectName"})
	@Test
	public void M4tc048_VerifyTooltipOnEventOnCalendar(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		EditPageBusinessLayer ep = new EditPageBusinessLayer(driver);
		MarketingEventPageBusinessLayer me = new MarketingEventPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		String parentID=null,value="", monthAndYear;
		WebElement ele=null;
		
		boolean flag=false;
		Actions actions = new Actions(driver);
		List<WebElement> li=null;
		String field=EditPageLabel.Title_Highlight_Color.toString();
		//lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Ins1, 10)) {
				ele=ip.getRelatedTab(projectName, RelatedTab.Events.toString(), 10);
				if (click(driver, ele, "events tab", action.SCROLLANDBOOLEAN)) {
					int loc=ip.findLocationOfDate(projectName, todaysDateSingleDigit.split("/")[1]);
					ele=ip.getEventPresentOnCalender(projectName, todaysDateSingleDigit.split("/")[1], loc,10);
					if (ele!=null) {
						scrollDownThroughWebelement(driver, ele, "event name");
						if (mouseHoverJScript(driver, ele)) {
							log(LogStatus.INFO, "successfully performed mouseover operation", YesNo.Yes);
								
						}else {
							log(LogStatus.ERROR, "could not perform mouseover operation", YesNo.Yes);
							sa.assertTrue(false,"could not perform mouseover operation" );
						}
						ThreadSleep(3000);
						if (ele.getAttribute("title").equalsIgnoreCase(M4MarketingEvent4Name)){
							log(LogStatus.INFO, "successfully verified presence of tooltip "+M4MarketingEvent4Name, YesNo.No);
							
						}else {
							log(LogStatus.ERROR, "could not verify tooltip", YesNo.Yes);
							sa.assertTrue(false,"could not verify tooltip" );
						}
					}else {
							log(LogStatus.ERROR, "could not verify event present on todays date", YesNo.Yes);
							sa.assertTrue(false,"could not verify event present on todays date" );
						}
					}else {
						log(LogStatus.ERROR, "could not click on event tab", YesNo.Yes);
						sa.assertTrue(false,"could not click on event tab" );
					}
			}else {
				log(LogStatus.ERROR, M4Ins1+" entity is not found on entity tab", YesNo.Yes);
				sa.assertTrue(false,M4Ins1+" entity is not found on entity tab" );
			}
		}else {
			log(LogStatus.ERROR, "entity tab is not clickable", YesNo.Yes);
			sa.assertTrue(false,"entity tab is not clickable" );
		}
		lp.CRMlogout();
		sa.assertAll();
	}
}

