package com.navatar.scripts;


import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;
import com.navatar.generic.BaseLib;
import static com.navatar.generic.EnumConstants.*;

import java.util.Scanner;

import com.navatar.generic.ExcelUtils;
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
		for (int i = 0;i<8;i++) {
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
		String fields=SDGLabels.APIName.toString()+","+SDGLabels.Override_Label.toString()+","+
				SDGLabels.FieldOrder.toString();//+","+SDGLabels.URL.toString()+","+SDGLabels.Parent_Field_Name.toString();
		String values="";
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
						String over=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "CField" + (i+1), excelLabel.Override_Label);
						String fieldOrder=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "CField" + (i+1), excelLabel.FieldOrder);
						String url=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "CField" + (i+1), excelLabel.URL);
						String par=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "CField" + (i+1), excelLabel.Parent_Field_Name);

						values=api+","+over+","+fieldOrder+","+url+","+par;
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
		String fields=SDGLabels.APIName.toString()+","+SDGLabels.Override_Label.toString()+","+
				SDGLabels.FieldOrder.toString();//+","+SDGLabels.URL.toString()+","+SDGLabels.Parent_Field_Name.toString();
		String values="";
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
						String over=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "DTField" + (i+1), excelLabel.Override_Label);
						String fieldOrder=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "DTField" + (i+1), excelLabel.FieldOrder);
						String url=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "DTField" + (i+1), excelLabel.URL);
						String par=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "DTField" + (i+1), excelLabel.Parent_Field_Name);

						values=api+","+over+","+fieldOrder+","+url+","+par;
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
		String fields=SDGLabels.APIName.toString()+","+SDGLabels.Override_Label.toString()+","+
				SDGLabels.FieldOrder.toString();//+","+SDGLabels.URL.toString()+","+SDGLabels.Parent_Field_Name.toString();
		String values="";
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
						String over=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "AField" + (i+1), excelLabel.Override_Label);
						String fieldOrder=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "AField" + (i+1), excelLabel.FieldOrder);
						String url=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "AField" + (i+1), excelLabel.URL);
						String par=ExcelUtils.readData(phase1DataSheetFilePath,"Fields",excelLabel.Variable_Name, "AField" + (i+1), excelLabel.Parent_Field_Name);

						values=api+","+over+","+fieldOrder+","+url+","+par;
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
		String fieldValues[]={EditPageLabel.Title.toString()+"<break>"+"Contacts",EditPageLabel.Query.toString()+"<break>"+EditPageErrorMessage.ContactSDGQuery,
				EditPageLabel.Image_Field_API_Name.toString()+"<break>"+"Profile_Image__c",EditPageLabel.Number_of_Records_to_Display.toString()+"<break>6",
				EditPageLabel.SDG_Name.toString()+"<break>"+"Contact",EditPageLabel.Popup_Title.toString()+"<break>"+"Contacts"
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
	public void M4tc007_UpdateImageOnContactProfile(String projectName) {
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

		String attachmentPath1= System.getProperty("user.dir")+"\\UploadFiles\\Module 4\\tc7\\1.jpg";
		String attachmentPath2= System.getProperty("user.dir")+"\\UploadFiles\\Module 4\\tc7\\2.jpg";
		WebElement ele=null;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object2Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Contact1FName+" "+M4Contact1LName, 10)) {
				ele=cp.getRelatedTab(projectName, RelatedTab.Overview.toString(), 10);
				click(driver, ele, "overview tab", action.BOOLEAN);
				if (cp.updatePhotoInDetailPage(projectName, attachmentPath1)) {
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
		
		
		
		
		if (ip.clickOnTab(projectName, TabName.Object2Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M4Contact1FName+" "+M4Contact1LName, 10)) {
				ele=cp.getRelatedTab(projectName, RelatedTab.Overview.toString(), 10);
				click(driver, ele, "overview tab", action.BOOLEAN);
				if (cp.updatePhotoInDetailPage(projectName,attachmentPath2)) {
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
	}
}
