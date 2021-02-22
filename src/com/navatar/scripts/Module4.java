package com.navatar.scripts;


import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static com.navatar.generic.CommonLib.ThreadSleep;
import static com.navatar.generic.CommonLib.log;
import static com.navatar.generic.CommonVariables.*;
import com.navatar.generic.BaseLib;
import com.navatar.generic.EnumConstants.*;
import com.navatar.generic.ExcelUtils;
import com.navatar.pageObjects.ContactsPageBusinessLayer;
import com.navatar.pageObjects.CustomObjPageBusinessLayer;
import com.navatar.pageObjects.FundsPageBusinessLayer;
import com.navatar.pageObjects.HomePageBusineesLayer;
import com.navatar.pageObjects.InstitutionsPageBusinessLayer;
import com.navatar.pageObjects.LoginPageBusinessLayer;
import com.navatar.pageObjects.SDGPageBusinessLayer;
import com.navatar.pageObjects.SetupPageBusinessLayer;
import com.relevantcodes.extentreports.LogStatus;

public class Module4 extends BaseLib{
	@Parameters({ "projectName"})
	@Test
	public void M4tc001_CreateContactSDG(String projectName) {
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
}
