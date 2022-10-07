package com.navatar.scripts;

import static com.navatar.generic.BaseLib.phase1DataSheetFilePath;
import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.navatar.generic.BaseLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.*;
import com.navatar.pageObjects.*;
import com.relevantcodes.extentreports.LogStatus;


public class Module6 extends BaseLib{
	public static double dealReceivedScore=1.0,loiScore=5.0,closedScore=5.0;
	@Parameters({ "projectName"})
	@Test
	public void M6tc001_CreatePrecondition(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		MarketingEventPageBusinessLayer me = new MarketingEventPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String value="";
		String type="";
		String status1=null;
		//for (int i = 13;i<14;i++) {
		for (int i = 0;i<20;i++) {
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
				
				value=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M6INS"+(i+1), excelLabel.Institutions_Name);
				type=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M6INS"+(i+1), excelLabel.Record_Type);
				if(i==16) {
					
					if (ip.createInstitution(projectName, environment, mode, value,type, InstitutionPageFieldLabelText.Parent_Institution.toString(),M6Ins1)) {
						log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
						log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
					}
				
				} else if(i==13) {
					
					if (ip.createInstitution(projectName, environment, mode, value,type, InstitutionPageFieldLabelText.Parent_Institution.toString(),M6Ins13)) {
						log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
						log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
					}
				
				}
				else {
					if (ip.createEntityOrAccount(projectName, mode, value,type, null, null, 20)) {
						log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
					} else {
						sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
						log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
					}
				}


			} else {
				sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
				log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
			}
		}

		
		String pipe,company,stage;
		//for(int i =13;i<14;i++) {
		for(int i = 0;i<19;i++) {
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object4Tab,YesNo.No);	
			pipe= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal"+(i+1), excelLabel.Deal_Name);
			company= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal"+(i+1), excelLabel.Company_Name);
			stage= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal"+(i+1), excelLabel.Stage);
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
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M6tc002_VerifyConvertToPortfolio_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal2, 10)) {
				if ( dp.getconvertToPortfolio(10)!=null) {
					log(LogStatus.INFO, "successfully verified presence of convert to portfolio button", YesNo.Yes);

				}else {
					log(LogStatus.ERROR, "could not verify presence of convert to portfolio button", YesNo.Yes);
					sa.assertTrue(false,"could not verify presence of convert to portfolio button" );
				}
				for (int i = 0;i<3;i++) {//Change 3 to 2
					if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {

						if (i==0) {
							if (click(driver, dp.getconvertToPortfolioCrossButton(10), "cross", action.BOOLEAN)) {
								if (dp.getconvertToPortfolioCrossButton(5)==null) {

								}else {
									sa.assertTrue(false,"could not verify cross icon is still visible");
									log(LogStatus.SKIP,"could not verify cross icon is still visible",YesNo.Yes);
								}
							}else {
								sa.assertTrue(false,"could not click cross icon");
								log(LogStatus.SKIP,"could not click cross icon",YesNo.Yes);
							}
						}
						else if(i==1) {
							if (dp.getconvertToPortfolioMessage(M6Ins2,10)!=null) {
								log(LogStatus.INFO, "successfully verified convert to portfolio text message", YesNo.No);
							}else {
								sa.assertTrue(false,"could not verify convert to portfolio text message before next");
								log(LogStatus.SKIP,"could not verify convert to portfolio text message before next",YesNo.Yes);
							}
							if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
								log(LogStatus.INFO, "successfully clicked next button", YesNo.No);
								if(dp.getconvertToPortfolioMessageAfterNext( 10)!=null) {
									if(dp.getconvertToPortfolioMessageAfterNext( 10).getText().contains(M6Ins2) && 
											dp.getconvertToPortfolioMessageAfterNext( 10).getText().contains(dp.convertToPortfolioAfterNext(M6Ins2))) {
									log(LogStatus.INFO, "successfully verified after next convert to portfolio text message", YesNo.No);
									}else {
										sa.assertTrue(false,"could not verify after next convert to portfolio text message");
										log(LogStatus.SKIP,"could not verify after next convert to portfolio text message",YesNo.Yes);
									}
									
									}else {
									sa.assertTrue(false,"could not verify after next convert to portfolio text message");
									log(LogStatus.SKIP,"could not verify after next convert to portfolio text message",YesNo.Yes);
								}
								if (click(driver, dp.getconvertToPortfolioCrossButton(10), "cross", action.BOOLEAN)) {
									log(LogStatus.INFO,"clicked on cross icon",YesNo.Yes);
									if (dp.getconvertToPortfolioCrossButton(5)==null) {
										log(LogStatus.INFO,"window is closed after clicking cross",YesNo.Yes);
										
									}else {
										sa.assertTrue(false,"could not verify cross icon is still visible");
										log(LogStatus.SKIP,"could not verify cross icon is still visible",YesNo.Yes);
									}
								}else {
									sa.assertTrue(false,"could not click cross icon");
									log(LogStatus.SKIP,"could not click cross icon",YesNo.Yes);
								}

							}else {
								sa.assertTrue(false,"not able to click on next button");
								log(LogStatus.SKIP,"not able to click on next button",YesNo.Yes);
							}
						}
						else if(i==2) {

							if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
								log(LogStatus.INFO, "successfully clicked next button", YesNo.No);
								if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
									log(LogStatus.INFO, "successfully verified finish button after convert to portfolio", YesNo.No);
									ExcelUtils.writeData(phase1DataSheetFilePath, Stage.Closed.toString(), "Deal", excelLabel.Variable_Name, "M6Deal2",excelLabel.Updated_Stage);
								}else {
									sa.assertTrue(false,"could not verify convert to portfolio as finish button not clicked");
									log(LogStatus.SKIP,"could not verify convert to portfolio as finish button not clicked",YesNo.Yes);
								}

							}else {
								sa.assertTrue(false,"not able to click on next button");
								log(LogStatus.SKIP,"not able to click on next button",YesNo.Yes);
							}
						
						}
					}else {
						sa.assertTrue(false,"not able to click on convert to portfolio button");
						log(LogStatus.SKIP,"not able to click on convert to portfolio button",YesNo.Yes);
					}
				}
				
				ThreadSleep(2000);
				refresh(driver);
				ThreadSleep(5000);
			}
			else {
				log(LogStatus.ERROR, "could not verify presence of convert to portfolio button", YesNo.Yes);
				sa.assertTrue(false,"could not verify presence of convert to portfolio button" );
			}
		}
		else {
			log(LogStatus.ERROR, "could not verify presence of convert to portfolio button", YesNo.Yes);
			sa.assertTrue(false,"could not verify presence of convert to portfolio button" );
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M6tc002_VerifyConvertToPortfolio_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Last_Stage_Change_Date.toString()};
		String labelValues[]={Stage.Closed.toString(),Stage.Closed.toString(),todaysDate};
		String labelName1[]={excelLabel.Status.toString()
		};
		String labelValues1[]={InstRecordType.Portfolio_Company.toString()};
		String labelValues2[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};


		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal2, 10)) {
				for (int i =0;i<labelName.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
						log(LogStatus.INFO,"successfully verified "+labelName[i],YesNo.No);

					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
					}

				}
				if(dp.checkValueOfPathComponentValueOfStage(Stage.Closed.toString(), 10)) {
					log(LogStatus.INFO,"stage on path component successfully verified",YesNo.Yes);
						
				}else {
					sa.assertTrue(false,"stage on path component could not be verified");
					log(LogStatus.SKIP,"stage on path component could not be verified",YesNo.Yes);
				}
			}else {
				log(LogStatus.ERROR, M6Deal2+" deal is not found", YesNo.Yes);
				sa.assertTrue(false,M6Deal2+" deal is not found" );
			}
		}else {
			log(LogStatus.ERROR, "deal tab is not clickable", YesNo.Yes);
			sa.assertTrue(false,"deal tab is not clickable" );
		}
		WebElement ele=null;
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Ins2, 10)) {
				ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
				if (click(driver, ele, "details tab", action.SCROLLANDBOOLEAN)) {
					for (int i =0;i<labelName1.length;i++) {
						if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
							log(LogStatus.INFO,"successfully verified "+labelName1[i],YesNo.No);
							
						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
						}

					}
					String text=ip.getLastModifiedTime(projectName,10).getText().trim();
					text=text.split(",")[1];
					if(ip.verifyBeforeTimeOrNot(projectName, text)) {
						log(LogStatus.INFO,"successfully verified last modified time",YesNo.Yes);
						
					}else {
						sa.assertTrue(false,"could not verify last modified time");
						log(LogStatus.SKIP,"could not verify last modified time",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"not able to click on details tab");
					log(LogStatus.SKIP,"not able to click on details tab",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,M6Ins2+ " is not present in inst tab");
				log(LogStatus.SKIP,M6Ins2+ " is not present in inst tab",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deals tab");
			log(LogStatus.SKIP,"not able to click on deals tab",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M6tc003_VerifyAlreadyPortfolioCompanyConvertingToPortfolio_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				,excelLabel.Last_Stage_Change_Date.toString()};
		String labelValues[]={Stage.Closed.toString(),Stage.Closed.toString(),todaysDate};
		String labelName1[]={excelLabel.Status.toString()
		};
		String labelValues1[]={InstRecordType.Portfolio_Company.toString()};
		String labelValues2[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};


		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal2, 10)) {
				
				for (int i = 0;i<2;i++) {
				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						if (dp.getconvertToPortfolioMessageRepeat(10)!=null) {
							if (dp.getconvertToPortfolioMessageRepeat(10).getText().contains(dp.convertToPortfolioRepeat(M6Ins2))) {
								log(LogStatus.INFO,"successfully verified already portfolio message",YesNo.Yes);
								
							}else {
								sa.assertTrue(false,"could not verify already portfolio message");
								log(LogStatus.SKIP,"could not verify already portfolio message",YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"not visible already portfolio message");
							log(LogStatus.SKIP,"not visible already portfolio message",YesNo.Yes);
						}
						
						if (i==0) {
							if (click(driver, dp.getconvertToPortfolioCrossButton(10), "cross", action.BOOLEAN)) {
								log(LogStatus.INFO,"clicked on cross icon",YesNo.Yes);
								if (dp.getconvertToPortfolioCrossButton(5)==null) {
									log(LogStatus.INFO,"window is closed after clicking cross",YesNo.Yes);
									
								}else {
									sa.assertTrue(false,"could not verify cross icon is still visible");
									log(LogStatus.SKIP,"could not verify cross icon is still visible",YesNo.Yes);
								}
							}else {
								sa.assertTrue(false,"could not click cross icon");
								log(LogStatus.SKIP,"could not click cross icon",YesNo.Yes);
							}
						}
						else if(i==1) {
							if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
								log(LogStatus.INFO, "successfully verified finish button after convert to portfolio", YesNo.No);
							}else {
								sa.assertTrue(false,"could not verify convert to portfolio as finish button not clicked");
								log(LogStatus.SKIP,"could not verify convert to portfolio as finish button not clicked",YesNo.Yes);
							}
						}
						for(int j =0;j<2;j++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[j],labelValues[j])) {
							log(LogStatus.SKIP,"successfully verified "+labelName[j],YesNo.No);

						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[j]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[j],YesNo.Yes);
						}
						}
					}else {
						sa.assertTrue(false,"next button is not clickable");
						log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"convert portfolio button is not clickable");
					log(LogStatus.SKIP,"convert portfolio button is not clickable",YesNo.Yes);
				}
				}
			}else {
				sa.assertTrue(false,M6Deal2+" is not clickable");
				log(LogStatus.SKIP,M6Deal2+" is not clickable",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M6tc004_VerifyConvertingToPortfolioAfterChangingCompanyRecordType(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		String labelName1[]={excelLabel.Record_Type.toString()
		};
		String labelValues1[]={InstRecordType.Company.toString()};
		int i = 0;

		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Ins2, 10)) {
				if (ip.clickOnShowMoreActionDownArrow(projectName, PageName.Object1Page, ShowMoreActionDropDownList.Change_Record_Type, 10)) {
					if (click(driver, ip.getRadioButtonforRecordType(RecordType.Company.toString(), 10), "company record type", action.BOOLEAN)) {
						if (click(driver, ip.getContinueOrNextButton(projectName,10), "Continue Button", action.BOOLEAN)) {
							appLog.info("Clicked on Continue or Nxt Button");	
							if (click(driver, ip.getNavigationTabSaveBtn(projectName,10), "save button", action.SCROLLANDBOOLEAN)) {
								appLog.info("clicked on save button");
							ThreadSleep(3000);
							if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
								log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);
								
							}else {
								sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
								log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
							}
							}else {
								sa.assertTrue(false,"save button is not clickable");
								log(LogStatus.SKIP,"save button is not clickable",YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"next button is not clickable");
							log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"radio button company button is not clickable");
						log(LogStatus.SKIP,"radio button company button is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"change record type button is not clickable");
					log(LogStatus.SKIP,"change record type button is not clickable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,M6Ins2+" company is not clickable");
				log(LogStatus.SKIP,M6Ins2+" company is not clickable",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"inst tab is not clickable");
			log(LogStatus.SKIP,"inst tab is not clickable",YesNo.Yes);
		}
		
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal2, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						String text=dp.getconvertToPortfolioMessageAfterNext(10).getText();
						if (text.contains(dp.convertToPortfolioAfterNext(M6Ins2))) {
							log(LogStatus.INFO,"successfully verified convert to portfolio message congratulations",YesNo.Yes);
							
						}else {
							sa.assertTrue(false,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins2)+" actual: "+text);
							log(LogStatus.SKIP,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins2)+" actual: "+text,YesNo.Yes);
						}
						if (dp.getconvertToPortfolioCrossButton(10)!=null) {
							log(LogStatus.INFO,"cross button is present",YesNo.Yes);
							
						}else {
							sa.assertTrue(false,"could not verify cross icon presence");
							log(LogStatus.SKIP,"could not verify cross icon presence",YesNo.Yes);
						}
						if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "successfully verified finish button after convert to portfolio", YesNo.No);
						}else {
							sa.assertTrue(false,"could not verify convert to portfolio as finish button not clicked");
							log(LogStatus.SKIP,"could not verify convert to portfolio as finish button not clicked",YesNo.Yes);
						}
						ThreadSleep(3000);
						refresh(driver);
						ThreadSleep(3000);
						if(dp.checkValueOfPathComponentValueOfStage(Stage.Closed.toString(), 10)) {
							log(LogStatus.INFO,"stage on path component successfully verified",YesNo.Yes);
								
						}else {
							sa.assertTrue(false,"stage on path component could not be verified");
							log(LogStatus.SKIP,"stage on path component could not be verified",YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"could not verify convert to portfolio as finish button not clicked");
						log(LogStatus.SKIP,"could not verify convert to portfolio as finish button not clicked",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"could not verify convert to portfolio as finish button not clicked");
					log(LogStatus.SKIP,"could not verify convert to portfolio as finish button not clicked",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"could not click on "+M6Deal2);
				log(LogStatus.SKIP,"could not click on "+M6Deal2,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"could not click on deal tab");
			log(LogStatus.SKIP,"could not click on deal tab",YesNo.Yes);
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M6tc005_VerifyConvertingAfterChangingDealStage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		WebElement ele=null;

		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				};
		String labelValues[]={Stage.LOI.toString(),Stage.LOI.toString()};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal2, 10)) {
				if (fp.changeStage(projectName, Stage.LOI.toString(), 10)) {
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele, "details", action.SCROLLANDBOOLEAN);
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
							log(LogStatus.INFO,"successfully verified "+labelName[i],YesNo.Yes);
						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}
					
					}
					ThreadSleep(3000);
					refresh(driver);
					ThreadSleep(3000);
					if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
						if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
							if (dp.getconvertToPortfolioMessageRepeat(10)!=null) {
								if (dp.getconvertToPortfolioMessageRepeat(10).getText().contains(dp.convertToPortfolioRepeat(M6Ins2))) {
									log(LogStatus.INFO,"successfully verified already portfolio message",YesNo.Yes);
									
								}else {
									sa.assertTrue(false,"could not verify already portfolio message");
									log(LogStatus.SKIP,"could not verify already portfolio message",YesNo.Yes);
								}
							}else {
								sa.assertTrue(false,"not visible already portfolio message");
								log(LogStatus.SKIP,"not visible already portfolio message",YesNo.Yes);
							}
							click(driver, dp.getconvertToPortfolioCrossButton(10), "cross", action.BOOLEAN);
							for(int j =0;j<2;j++) {
								if (fp.FieldValueVerificationOnFundPage(projectName, labelName[j],labelValues[j])) {
									log(LogStatus.SKIP,"successfully verified "+labelName[j],YesNo.No);

								}else {
									sa.assertTrue(false,"Not Able to verify "+labelName[j]);
									log(LogStatus.SKIP,"Not Able to verify "+labelName[j],YesNo.Yes);
								}
							}
						}else {
								sa.assertTrue(false,"next button is not clickable");
								log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
							}
					}else {
						sa.assertTrue(false,"convert to portfolio button is not clickable");
						log(LogStatus.SKIP,"convert to portfolio button is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"stage could not be changed");
					log(LogStatus.SKIP,"stage could not be changed",YesNo.Yes);
				}
				
			}else {
				sa.assertTrue(false,M6Deal2+" deal is not found");
				log(LogStatus.SKIP,M6Deal2+" deal is not found",YesNo.Yes);
			}
			
		}else {
			sa.assertTrue(false,"could not click on deal tab");
			log(LogStatus.SKIP,"could not click on deal tab",YesNo.Yes);
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M6tc006_VerifyConvertingAfterChangingCompany(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		WebElement ele=null;

		String labelName[]={excelLabel.Company_Name.toString()
		};
		String labelValues[]={M6Ins3};
		String labelName1[]={excelLabel.Stage.toString()
		};
		String labelValues1[]={Stage.Closed.toString()};
		String labelName2[]={excelLabel.Status.toString()
		};
		String labelValues2[]={InstRecordType.Portfolio_Company.toString()};
		
		boolean flag=true;
		int i = 0;
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal2, 10)) {
				if (dp.clickOnShowMoreActionDownArrow(projectName, PageName.Object4Page, ShowMoreActionDropDownList.Edit, 10)) {
					ele=dp.crossIconForCompanyName(M6Ins2, 10);
					if (click(driver, ele, "cross icon", action.BOOLEAN)) {
						if (sendKeys(driver, fp.getCompanyName(projectName, 60), M6Ins3, "Company Name",
								action.SCROLLANDBOOLEAN)) {
							ThreadSleep(1000);
							if (click(driver,FindElement(driver,"//input[contains(@placeholder,'Search')]/../following-sibling::*//*[@title='"+M6Ins3+"']","Company Name List", action.BOOLEAN, 30),
									M6Ins3 + "   :   Company Name", action.BOOLEAN)) {
								log(LogStatus.INFO,"succssfully selected company",YesNo.Yes);
								if (click(driver, fp.getCustomTabSaveBtn(projectName,30), "Save Button", action.SCROLLANDBOOLEAN)) {

									appLog.error("Click on save Button");	
								}
								else {
									sa.assertTrue(false,"could not click on save button");
									log(LogStatus.SKIP,"could not click on save button",YesNo.Yes);
								}
								
								if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
									if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
										String text=dp.getconvertToPortfolioMessageAfterNext(10).getText();
										if (text.contains(dp.convertToPortfolioAfterNext(M6Ins3))) {
											log(LogStatus.INFO,"successfully verified convert to portfolio message congratulations",YesNo.Yes);
											
										}else {
											sa.assertTrue(false,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins3)+" actual: "+text);
											log(LogStatus.SKIP,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins3)+" actual: "+text,YesNo.Yes);
										}
										
										if ( dp.getconvertToPortfolioCrossButton(10)!=null) {
											log(LogStatus.INFO,"successfully verified presence of cross icon",YesNo.Yes);
											
										}else {
											sa.assertTrue(false,"could not verify cross icon presence");
											log(LogStatus.SKIP,"could not verify cross icon presence",YesNo.Yes);
										}
										if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
											log(LogStatus.INFO, "successfully verified finish button after convert to portfolio", YesNo.No);
										}else {
											sa.assertTrue(false,"could not verify convert to portfolio as finish button not clicked");
											log(LogStatus.SKIP,"could not verify convert to portfolio as finish button not clicked",YesNo.Yes);
										}
										ThreadSleep(3000);
										refresh(driver);
										ThreadSleep(3000);
										if (fp.FieldValueVerificationOnFundPage(projectName, labelName1[i],labelValues1[i])) {
											log(LogStatus.INFO,"successfully verified "+labelName1[i],YesNo.Yes);
										}else {
											flag=false;
											sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
											log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
										}
										if(dp.checkValueOfPathComponentValueOfStage(Stage.Closed.toString(), 10)) {
											log(LogStatus.INFO,"stage on path component successfully verified",YesNo.Yes);
												
										}else {
											sa.assertTrue(false,"stage on path component could not be verified");
											log(LogStatus.SKIP,"stage on path component could not be verified",YesNo.Yes);
										}
									}else {
										sa.assertTrue(false,"next button is not clickable");
										log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
									}
								}else {
									sa.assertTrue(false,"convert to portfolio button is not clickable");
									log(LogStatus.SKIP,"convert to portfolio button is not clickable",YesNo.Yes);
								}
							} else {
								sa.assertTrue(false,"could not select company "+M3Ins3);
								log(LogStatus.SKIP,"could not select company "+M3Ins3,YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"company textbox is not visible "+M3Ins3);
							log(LogStatus.SKIP,"company textbox is not visible "+M3Ins3,YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"cross icon is not clckable");
						log(LogStatus.SKIP,"cross icon is not clckable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"edit button is not clckable");
					log(LogStatus.SKIP,"edit button is not clckable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,M6Deal2+" is not found on deal page");
				log(LogStatus.SKIP,M6Deal2+" is not found on deal page",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}
		
		if (flag) {
			if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
				if (ip.clickOnAlreadyCreatedItem(projectName, M6Ins3, 10)) {
					if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName2[i],labelValues2[i])) {
						log(LogStatus.SKIP,"successfully verified "+labelName2[i],YesNo.No);

					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName2[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName2[i],YesNo.Yes);
					}
				}
				else {
					sa.assertTrue(false,M6Ins3+" is not found on inst page");
					log(LogStatus.SKIP,M6Ins3+" is not found on inst page",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,"inst tab is not clickable");
				log(LogStatus.SKIP,"inst tab is not clickable",YesNo.Yes);
			}
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M6tc007_1_VerifyImpactOnIntermediarySourceFirm(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		WebElement ele=null;
		int total=1;
		double avgDealQualityScore=dealReceivedScore/total;
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString(),excelLabel.Deal_Quality_Score.toString()
		};
		String labelValues[]={M6Deal20Stage,M6Deal20Stage,String.valueOf(dealReceivedScore)};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		String labelValues1[]={String.valueOf(avgDealQualityScore),String.valueOf(total)};
		String labelName2[]={excelLabel.Status.toString()
		};
		String labelValues2[]={InstRecordType.Portfolio_Company.toString()};
		
		boolean flag=true;
		int i = 0;
		String pipe="",company="",stage="",sf="";
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object4Tab,YesNo.No);	
			pipe= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal20", excelLabel.Deal_Name);
			company= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal20", excelLabel.Company_Name);
			sf= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal20", excelLabel.Source_Firm);
			stage= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal20", excelLabel.Stage);
			String[][] otherLabels = {{excelLabel.Source_Firm.toString(),sf}};
			refresh(driver);
			ThreadSleep(3000);
			if (fp.createDeal(projectName,"",pipe, company, stage,otherLabels, 15)) {
				log(LogStatus.INFO,"Created Deal : "+pipe,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Deal  : "+pipe);
				log(LogStatus.SKIP,"Not Able to Create Deal  : "+pipe,YesNo.Yes);
			}
			
			for (i =0;i<labelName.length;i++) {
				if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
					log(LogStatus.INFO,"successfully verified "+labelName[i],YesNo.Yes);
				}else {
					sa.assertTrue(false,"Not Able to verify "+labelName[i]);
					log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
				}
			
			}
			
			if (click(driver, fp.sourceFirmLink(10), "source firm link", action.SCROLLANDBOOLEAN)) {
				for(i = 0;i<labelName1.length;i++) {
				if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
					log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);
					
				}else {
					sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
					log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
				}
				}
			}else {
				sa.assertTrue(false,"not able to click on source firm link");
				log(LogStatus.SKIP,"not able to click on source firm link",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
			
	@Parameters({ "projectName"})
	@Test
	public void M6tc007_2_VerifyImpactOnIntermediarySourceFirm(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		int total=1;
		double avgDealQualityScore=loiScore/total;
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString(),excelLabel.Deal_Quality_Score.toString()
		};
		String labelValues[]={Stage.LOI.toString(),Stage.LOI.toString(),String.valueOf(loiScore)};
		String labelName1[]={excelLabel.Average_Deal_Quality_Score.toString(),excelLabel.Total_Deals_Shown.toString()
		};
		String labelValues1[]={String.valueOf(avgDealQualityScore),String.valueOf(total)};
		int i;
		WebElement ele=null;
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal20, 10)) {
				if (fp.changeStage(projectName, Stage.LOI.toString(), 10)) {
					ele=ip.getRelatedTab(projectName, RelatedTab.Details.toString(), 10);
					click(driver, ele, "details", action.SCROLLANDBOOLEAN);
					for (i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
							log(LogStatus.INFO,"successfully verified "+labelName[i],YesNo.Yes);
						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}
					
					}
				}else {
					sa.assertTrue(false,"not able to change stage");
					log(LogStatus.SKIP,"not able to change stage",YesNo.Yes);
				}
				
				if (click(driver, fp.sourceFirmLink(10), "source firm link", action.SCROLLANDBOOLEAN)) {
					for(i = 0;i<labelName1.length;i++) {
					if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
						log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);
						
					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
					}
					}
					refresh(driver);
					ThreadSleep(3000);
					if (ip.getLastModifiedTime(projectName,10)!=null) {
						scrollDownThroughWebelement(driver, ip.getLastModifiedTime(projectName,10), "last modified time");
						String text=ip.getLastModifiedTime(projectName,10).getText().trim();
						text=text.split(",")[1];
						if(ip.verifyBeforeTimeOrNot(projectName, text)) {
							log(LogStatus.INFO,"successfully verified last modified time",YesNo.Yes);

						}else {
							sa.assertTrue(false,"could not verify last modified time");
							log(LogStatus.SKIP,"could not verify last modified time",YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"could not verify last modified time");
						log(LogStatus.SKIP,"could not verify last modified time",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"not able to click on source firm link");
					log(LogStatus.SKIP,"not able to click on source firm link",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,M6Deal20+" deal is not found");
				log(LogStatus.SKIP,M6Deal20+" deal is not found",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M6tc008_CheckCompanyNameLengthOnConvertToPortfolioPopup(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SDGPageBusinessLayer sdg = new SDGPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
		};
		String labelValues[]={Stage.Closed.toString(),Stage.Closed.toString()};
		int total=1;
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal11, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					//if(dp.getCompanyNameOnconvertToPortfolioMessage(10).getText().contains(M6Ins11)) {
					if (dp.getconvertToPortfolioMessage(M6Ins11, 10)!=null) {
						log(LogStatus.INFO,"successfully verified max character inst name on before next convert to portfolio message congratulations",YesNo.Yes);
						
					}else {
						sa.assertTrue(false,"could not verify max char inst name before next convert to portfolio message");
						log(LogStatus.SKIP,"could not verify max char inst name before next convert to portfolio message",YesNo.Yes);
					}
					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						String text=dp.getconvertToPortfolioMessageAfterNext(10).getText();
						
						if (text.contains(dp.convertToPortfolioAfterNext(M6Ins11))) {
							log(LogStatus.INFO,"successfully verified max character inst name on convert to portfolio message congratulations",YesNo.Yes);
							
						}else {
							sa.assertTrue(false,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins11)+"\nactual: "+text);
							log(LogStatus.SKIP,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins11)+"\nactual: "+text,YesNo.Yes);
						}
						if ( dp.getconvertToPortfolioCrossButton(10)!=null) {
							log(LogStatus.INFO,"successfully verified presence of cross icon",YesNo.Yes);
							
						}else {
							sa.assertTrue(false,"could not verify cross icon presence");
							log(LogStatus.SKIP,"could not verify cross icon presence",YesNo.Yes);
						}
						if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "successfully verified finish button after convert to portfolio", YesNo.No);
						}else {
							sa.assertTrue(false,"could not verify convert to portfolio as finish button not clicked");
							log(LogStatus.SKIP,"could not verify convert to portfolio as finish button not clicked",YesNo.Yes);
						}
						ThreadSleep(3000);
						refresh(driver);
						ThreadSleep(3000);
						for (int i =0;i<labelName.length;i++) {
							if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
								log(LogStatus.INFO,"successfully verified "+labelName[i],YesNo.Yes);
							}else {
								sa.assertTrue(false,"Not Able to verify "+labelName[i]);
								log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
							}
						
						}
						if(dp.checkValueOfPathComponentValueOfStage(Stage.Closed.toString(), 10)) {
							log(LogStatus.INFO,"stage on path component successfully verified",YesNo.Yes);
								
						}else {
							sa.assertTrue(false,"stage on path component could not be verified");
							log(LogStatus.SKIP,"stage on path component could not be verified",YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"next button is not clickable");
						log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"convert to portfolio button is not clickable");
					log(LogStatus.SKIP,"convert to portfolio button is not clickable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,M6Deal11+" deal is not clickable on deal tab");
				log(LogStatus.SKIP,M6Deal11+" deal is not clickable on deal tab",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
					
	@Parameters({ "projectName"})
	@Test
	public void M6tc009_CheckConvertToPortfolioFuncAfterRevokingEditPermissions_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		int total=1;
		String[] userNames= {"PE Standard User"};
		String onObject=tabObj4+"s";
		String permission="Edit";
		for (String userName : userNames) {
			switchToDefaultContent(driver);
			if (home.clickOnSetUpLink()) {
				String parentID = switchOnWindow(driver);
				if (parentID!=null) {
					log(LogStatus.INFO, "Able to switch on new window, so going to set"+permission+" for "+onObject, YesNo.No);
					ThreadSleep(500);
					if(setup.searchStandardOrCustomObject(environment,mode, object.Profiles)) {
						log(LogStatus.INFO, "click on Object : "+object.Profiles, YesNo.No);
						ThreadSleep(2000);
						if (setup.permissionChangeForUserONObject(driver, PermissionType.removePermission,userName, new String[][]{{onObject,permission}}, 20)) {
							log(LogStatus.PASS,permission+ " permission change for "+userName+" on object "+onObject,YesNo.No);
						} else {
							sa.assertTrue(false, permission+ " permission not change for "+userName+" on object "+onObject);
							log(LogStatus.FAIL,permission+ " permission not change for "+userName+" on object "+onObject,YesNo.Yes);
						}
					}else {
						log(LogStatus.ERROR, "Not able to search/click on "+object.Profiles, YesNo.Yes);
						sa.assertTrue(false, "Not able to search/click on "+object.Profiles);
					}
					ThreadSleep(2000);
					driver.close();
					ThreadSleep(2000);
					driver.switchTo().window(parentID);
				}else {
					log(LogStatus.FAIL, "could not find new window to switch, so cannot to set"+permission+" for "+onObject, YesNo.Yes);
					sa.assertTrue(false, "could not find new window to switch, to set"+permission+" for "+onObject);
				}

			}else {
				log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link");	
			}
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M6tc009_CheckConvertToPortfolioFuncAfterRevokingEditPermissions_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal2, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					
					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						if (dp.getconvertToPortfolioMessageRepeat(10)!=null) {
							String text=dp.getconvertToPortfolioMessageRepeat(10).getText();
							if (text.contains(dp.convertToPortfolioRepeat(M6Ins3))) {
								log(LogStatus.INFO,"successfully verified already portfolio message",YesNo.Yes);
								
							}else {
								sa.assertTrue(false,"could not verify already portfolio message\nExpected: "+text+"\nActual: "+dp.convertToPortfolioRepeat(M6Ins2));
								log(LogStatus.SKIP,"could not verify already portfolio message\\nExpected: "+text+"\nActual: "+dp.convertToPortfolioRepeat(M6Ins2),YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"not visible already portfolio message");
							log(LogStatus.SKIP,"not visible already portfolio message",YesNo.Yes);
						}
						if ( dp.getconvertToPortfolioCrossButton(10)!=null) {
							log(LogStatus.INFO,"successfully verified presence of cross icon",YesNo.Yes);
							
						}else {
							sa.assertTrue(false,"could not verify cross icon presence");
							log(LogStatus.SKIP,"could not verify cross icon presence",YesNo.Yes);
						}
						if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "successfully verified finish button after convert to portfolio", YesNo.No);
						}else {
							sa.assertTrue(false,"could not verify convert to portfolio as finish button not clicked");
							log(LogStatus.SKIP,"could not verify convert to portfolio as finish button not clicked",YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"next button is not clickable");
						log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"convertToPortfolio button is not clickable");
					log(LogStatus.SKIP,"convertToPortfolio button is not clickable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,M6Deal2+" deal is not found on deal tab");
				log(LogStatus.SKIP,M6Deal2+" deal is not found on deal tab",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}
		
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal4, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						
						if (dp.getconvertToPortfolioMessageUnhandledFlow(10)!=null) {
							if (dp.getconvertToPortfolioMessageUnhandledFlow(10).getText().contains(dp.unhandledError)) {
								log(LogStatus.INFO,"successfully verified unhandled flow message",YesNo.Yes);
								
							}else {
								sa.assertTrue(false,"could not verify unhandled flow message");
								log(LogStatus.SKIP,"could not verify unhandled flow message",YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"not visible already portfolio message for unhandled flow");
							log(LogStatus.SKIP,"not visible already portfolio message for unhandled flow",YesNo.Yes);
						}
						if (click(driver, dp.getconvertToPortfolioCrossButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on cross button", YesNo.No);
						}else {
							sa.assertTrue(false,"could not click on cross button");
							log(LogStatus.SKIP,"could not click on cross button",YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"next button is not clickable");
						log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"convert to portfolio button is not clickable");
					log(LogStatus.SKIP,"convert to portfolio button is not clickable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,M6Deal4+" deal is not clickable");
				log(LogStatus.SKIP,M6Deal4+" deal is not clickable",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	
	@Parameters({ "projectName"})
	@Test
	public void M6tc010_CheckConvertToPortfolioFuncAfterRevokingEditPermissionsAccount_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		int total=1;
		String[] userNames= {"PE Standard User"};
		String onObject=object.Account.toString();
		String permission="Edit";
		for (String userName : userNames) {
			switchToDefaultContent(driver);
			if (home.clickOnSetUpLink()) {
				String parentID = switchOnWindow(driver);
				if (parentID!=null) {
					log(LogStatus.INFO, "Able to switch on new window, so going to set"+permission+" for "+onObject, YesNo.No);
					ThreadSleep(500);
					if(setup.searchStandardOrCustomObject(environment,mode, object.Profiles)) {
						log(LogStatus.INFO, "click on Object : "+object.Profiles, YesNo.No);
						ThreadSleep(2000);
						if (setup.permissionChangeForUserONObject(driver,PermissionType.removePermission, userName, new String[][]{{onObject,permission}}, 20)) {
							log(LogStatus.PASS,permission+ " permission change for "+userName+" on object "+onObject,YesNo.No);
						} else {
							sa.assertTrue(false, permission+ " permission not change for "+userName+" on object "+onObject);
							log(LogStatus.FAIL,permission+ " permission not change for "+userName+" on object "+onObject,YesNo.Yes);
						}
					}else {
						log(LogStatus.ERROR, "Not able to search/click on "+object.Profiles, YesNo.Yes);
						sa.assertTrue(false, "Not able to search/click on "+object.Profiles);
					}
					ThreadSleep(5000);
					driver.close();
					driver.switchTo().window(parentID);
				}else {
					log(LogStatus.FAIL, "could not find new window to switch, so cannot to set"+permission+" for "+onObject, YesNo.Yes);
					sa.assertTrue(false, "could not find new window to switch, to set"+permission+" for "+onObject);
				}

			}else {
				log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link");	
			}
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	
	@Parameters({ "projectName"})
	@Test
	public void M6tc010_CheckConvertToPortfolioFuncAfterRevokingEditPermissionsAccount_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal2, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					
					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						ThreadSleep(2000);
						if (dp.getconvertToPortfolioMessageRepeat(10)!=null) {
							String text=dp.getconvertToPortfolioMessageRepeat(10).getText();
							if (text.contains(dp.convertToPortfolioRepeat(M6Ins3))) {
								log(LogStatus.INFO,"successfully verified already portfolio message",YesNo.Yes);
								
							}else {
								sa.assertTrue(false,"could not verify already portfolio message\nExpected: "+text+"\nActual: "+dp.convertToPortfolioRepeat(M6Ins2));
								log(LogStatus.SKIP,"could not verify already portfolio message\\nExpected: "+text+"\nActual: "+dp.convertToPortfolioRepeat(M6Ins2),YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"not visible already portfolio message");
							log(LogStatus.SKIP,"not visible already portfolio message",YesNo.Yes);
						}
						if ( dp.getconvertToPortfolioCrossButton(10)!=null) {
							log(LogStatus.INFO,"successfully verified presence of cross icon",YesNo.Yes);
							
						}else {
							sa.assertTrue(false,"could not verify cross icon presence");
							log(LogStatus.SKIP,"could not verify cross icon presence",YesNo.Yes);
						}
						if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "successfully verified finish button after convert to portfolio", YesNo.No);
						}else {
							sa.assertTrue(false,"could not verify convert to portfolio as finish button not clicked");
							log(LogStatus.SKIP,"could not verify convert to portfolio as finish button not clicked",YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"next button is not clickable");
						log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"convertToPortfolio button is not clickable");
					log(LogStatus.SKIP,"convertToPortfolio button is not clickable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,M6Deal2+" deal is not found on deal tab");
				log(LogStatus.SKIP,M6Deal2+" deal is not found on deal tab",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}
		
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal5, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						ThreadSleep(2000);
						if (dp.getconvertToPortfolioMessageUnhandledFlow(10)!=null) {
							if (dp.getconvertToPortfolioMessageUnhandledFlow(10).getText().contains(dp.unhandledError)) {
								log(LogStatus.INFO,"successfully verified unhandled flow message",YesNo.Yes);
								
							}else {
								sa.assertTrue(false,"could not verify unhandled flow message");
								log(LogStatus.SKIP,"could not verify unhandled flow message",YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"not visible already portfolio message for unhandled flow");
							log(LogStatus.SKIP,"not visible already portfolio message for unhandled flow",YesNo.Yes);
						}
						if (click(driver, dp.getconvertToPortfolioCrossButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on cross button", YesNo.No);
						}else {
							sa.assertTrue(false,"could not click on cross button");
							log(LogStatus.SKIP,"could not click on cross button",YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"next button is not clickable");
						log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"convert to portfolio button is not clickable");
					log(LogStatus.SKIP,"convert to portfolio button is not clickable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,M6Deal4+" deal is not clickable");
				log(LogStatus.SKIP,M6Deal4+" deal is not clickable",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M6tc011_1_CheckConvertToPortfolioFuncRenamePortfolioCompany(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		int total=1;
		String[] userNames= {"PE Standard User"};
		String onObject=object.Account.toString();
		String onObject1=tabObj4+"s";
		String permission="Edit";
		for (String userName : userNames) {
			switchToDefaultContent(driver);
			if (home.clickOnSetUpLink()) {
				String parentID = switchOnWindow(driver);
				if (parentID!=null) {
					log(LogStatus.INFO, "Able to switch on new window, so going to set"+permission+" for "+onObject, YesNo.No);
					ThreadSleep(500);
					if(setup.searchStandardOrCustomObject(environment,mode, object.Profiles)) {
						log(LogStatus.INFO, "click on Object : "+object.Profiles, YesNo.No);
						ThreadSleep(2000);
						if (setup.permissionChangeForUserONObject(driver,PermissionType.givePermission, userName, new String[][]{{onObject,permission},{onObject1,permission}}, 20)) {
							log(LogStatus.PASS,permission+ " permission change for "+userName+" on object "+onObject,YesNo.No);
						} else {
							sa.assertTrue(false, permission+ " permission not change for "+userName+" on object "+onObject);
							log(LogStatus.FAIL,permission+ " permission not change for "+userName+" on object "+onObject,YesNo.Yes);
						}
						
					}else {
						log(LogStatus.ERROR, "Not able to search/click on "+object.Profiles, YesNo.Yes);
						sa.assertTrue(false, "Not able to search/click on "+object.Profiles);
					}
					driver.close();
					driver.switchTo().window(parentID);
				}else {
					log(LogStatus.FAIL, "could not find new window to switch, so cannot to set"+permission+" for "+onObject, YesNo.Yes);
					sa.assertTrue(false, "could not find new window to switch, to set"+permission+" for "+onObject);
				}

			}else {
				log(LogStatus.ERROR, "Not able to click on setup link", YesNo.Yes);
				sa.assertTrue(false, "Not able to click on setup link");	
			}
		}
		String rt=InstRecordType.Portfolio_Company.toString().replace("_", " ");
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {

				if(setup.searchStandardOrCustomObject(environment, mode, object.Firm)) {
					if(setup.clickOnObjectFeature(environment, mode, object.Firm, ObjectFeatureName.recordTypes)) {
						if (sendKeys(driver, setup.getsearchTextboxFieldsAndRelationships(10), rt+Keys.ENTER, "status", action.BOOLEAN)) {
							if (setup.clickOnAlreadyCreatedLayout(rt)) {
								ThreadSleep(2000);
								switchToFrame(driver, 10, setup.getFrame(PageName.RecordTypePortfolioCompany, 10));
								if (click(driver, setup.getEditButton(environment,Mode.Classic.toString(),10), "edit", action.BOOLEAN)) {
									switchToDefaultContent(driver);
									ThreadSleep(5000);
									switchToFrame(driver, 10, setup.getFrame(PageName.RecordTypePortfolioCompany, 10));
									ThreadSleep(2000);
									setup.getRecordTypeLabel(projectName, "Record Type Label", 10).sendKeys(InstRecordType.Portfolio.toString());
									ThreadSleep(2000);switchToAlertAndAcceptOrDecline(driver, 30, action.ACCEPT);
										if (clickUsingJavaScript(driver, setup.getSaveButtonInCustomFields(10), "save", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "save button is clicked",YesNo.No);

										}else {
											log(LogStatus.FAIL, "save button not clickable",YesNo.Yes);
											sa.assertTrue(false, "save button not clickable");
										}
								}else {
									log(LogStatus.FAIL, "edit button not clickable",YesNo.Yes);
									sa.assertTrue(false, "edit button not clickable");
								}
							}else {
								log(LogStatus.FAIL, rt+" not found",YesNo.Yes);
								sa.assertTrue(false, rt+" not found");
							}
						}else {
							log(LogStatus.FAIL, "search textbox not found",YesNo.Yes);
							sa.assertTrue(false, "search textbox not found");
						}
					}else {
						log(LogStatus.FAIL, "record type feature not clickable",YesNo.Yes);
						sa.assertTrue(false, "record type feature not clickable");
					}
				}else {
					log(LogStatus.FAIL, "institution object is not clickable",YesNo.Yes);
					sa.assertTrue(false, "institution object is not clickable");
				}
				driver.close();
				ThreadSleep(2000);
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch",YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
			sa.assertTrue(false, "setup link is not clickable");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M6tc011_2_CheckConvertToPortfolioFuncRenamePortfolioCompany(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal2, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					
					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						if (dp.getconvertToPortfolioMessageRepeat(10)!=null) {
							
							String text=dp.getconvertToPortfolioMessageRepeat(10).getText();
							if (text.contains(dp.convertToPortfolioRepeat(M6Ins3))) {
								log(LogStatus.INFO,"successfully verified already portfolio message",YesNo.Yes);
								
							}else {
								sa.assertTrue(false,"could not verify already portfolio message\nExpected: "+text+"\nActual: "+dp.convertToPortfolioRepeat(M6Ins2));
								log(LogStatus.SKIP,"could not verify already portfolio message\\nExpected: "+text+"\nActual: "+dp.convertToPortfolioRepeat(M6Ins2),YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"not visible already portfolio message");
							log(LogStatus.SKIP,"not visible already portfolio message",YesNo.Yes);
						}
						if ( dp.getconvertToPortfolioCrossButton(10)!=null) {
							log(LogStatus.INFO,"successfully verified presence of cross icon",YesNo.Yes);
							
						}else {
							sa.assertTrue(false,"could not verify cross icon presence");
							log(LogStatus.SKIP,"could not verify cross icon presence",YesNo.Yes);
						}
						if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "successfully verified finish button after convert to portfolio", YesNo.No);
						}else {
							sa.assertTrue(false,"could not verify convert to portfolio as finish button not clicked");
							log(LogStatus.SKIP,"could not verify convert to portfolio as finish button not clicked",YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"next button is not clickable");
						log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"convertToPortfolio button is not clickable");
					log(LogStatus.SKIP,"convertToPortfolio button is not clickable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,M6Deal2+" deal is not found on deal tab");
				log(LogStatus.SKIP,M6Deal2+" deal is not found on deal tab",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal6, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {

					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						
						String text=dp.getconvertToPortfolioMessageAfterNext(10).getText();

						if (text.contains(dp.convertToPortfolioAfterNext(M6Ins6))) {
							log(LogStatus.INFO,"successfully verified max character inst name on convert to portfolio message congratulations",YesNo.Yes);

						}else {
							sa.assertTrue(false,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins6)+"\nactual: "+text);
							log(LogStatus.SKIP,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins6)+"\nactual: "+text,YesNo.Yes);
						}
						if ( dp.getconvertToPortfolioCrossButton(10)!=null) {
							log(LogStatus.INFO,"successfully verified presence of cross icon",YesNo.Yes);

						}else {
							sa.assertTrue(false,"could not verify cross icon presence");
							log(LogStatus.SKIP,"could not verify cross icon presence",YesNo.Yes);
						}
						if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "successfully verified finish button after convert to portfolio", YesNo.No);
						}else {
							sa.assertTrue(false,"could not verify convert to portfolio as finish button not clicked");
							log(LogStatus.SKIP,"could not verify convert to portfolio as finish button not clicked",YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"next button is not clickable");
						log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"convertToPortfolio button is not clickable");
					log(LogStatus.SKIP,"convertToPortfolio button is not clickable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,M6Deal6+" deal is not found on deal tab");
				log(LogStatus.SKIP,M6Deal6+" deal is not found on deal tab",YesNo.Yes);
			}
		}else {
				sa.assertTrue(false,"deal tab is not clickable");
				log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
			}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M6tc012_1_CheckConvertToPortfolioFuncRenamePortfolio(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String rt=InstRecordType.Portfolio_Company.toString().replace("_", " ");
		String updateLabel="Portfolio_Company2";
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {

				if(setup.searchStandardOrCustomObject(environment, mode, object.Firm)) {
					if(setup.clickOnObjectFeature(environment, mode, object.Firm, ObjectFeatureName.recordTypes)) {
						if (sendKeys(driver, setup.getsearchTextboxFieldsAndRelationships(10), InstRecordType.Portfolio.toString()+Keys.ENTER, "status", action.BOOLEAN)) {
							if (setup.clickOnAlreadyCreatedLayout(InstRecordType.Portfolio.toString())) {
								switchToFrame(driver, 10, setup.getFrame(PageName.RecordTypePortfolioCompany, 10));
								if (click(driver, setup.getEditButton(environment,Mode.Classic.toString(),10), "edit", action.BOOLEAN)) {
									switchToDefaultContent(driver);
									ThreadSleep(2000);
									switchToFrame(driver, 10, setup.getFrame(PageName.RecordTypePortfolioCompany, 10));
									ThreadSleep(5000);
								
									WebElement ele=setup.getRecordTypeLabel(projectName, "Record Type Label", 10);
									JavascriptExecutor js=(JavascriptExecutor) driver;
									js.executeScript("arguments[0].setAttribute('value','')", ele);
									ele.sendKeys(rt);
									ele.sendKeys(Keys.TAB);
									ThreadSleep(2000);
									if (isAlertPresent(driver)) {
										ThreadSleep(3000);
										switchToAlertAndAcceptOrDecline(driver, 30, action.ACCEPT);
									}

									ele=setup.getRecordTypeLabel(projectName, "Record Type Name", 10);
									js.executeScript("arguments[0].setAttribute('value','')", ele);
									ele.sendKeys(updateLabel);
									ele.sendKeys(Keys.TAB);
									ThreadSleep(2000);
									if (isAlertPresent(driver)) {
										ThreadSleep(3000);
										switchToAlertAndAcceptOrDecline(driver, 30, action.ACCEPT);
									}
									ThreadSleep(2000);

									if (clickUsingJavaScript(driver, setup.getSaveButtonInCustomFields(10), "save", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "save button is clicked",YesNo.No);

										}else {
											log(LogStatus.FAIL, "save button not clickable",YesNo.Yes);
											sa.assertTrue(false, "save button not clickable");
										}
								}else {
									log(LogStatus.FAIL, "edit button not clickable",YesNo.Yes);
									sa.assertTrue(false, "edit button not clickable");
								}
							}else {
								log(LogStatus.FAIL, InstRecordType.Portfolio+" not found",YesNo.Yes);
								sa.assertTrue(false, InstRecordType.Portfolio+" not found");
							}
						}else {
							log(LogStatus.FAIL, "search textbox not found",YesNo.Yes);
							sa.assertTrue(false, "search textbox not found");
						}
					}else {
						log(LogStatus.FAIL, "record type feature not clickable",YesNo.Yes);
						sa.assertTrue(false, "record type feature not clickable");
					}
				}else {
					log(LogStatus.FAIL, "institution object is not clickable",YesNo.Yes);
					sa.assertTrue(false, "institution object is not clickable");
				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch",YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
			sa.assertTrue(false, "setup link is not clickable");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M6tc012_2_CheckConvertToPortfolioFuncRenamePortfolio(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String deals[]={M6Deal2,M6Deal7};
		for(String d:deals) {
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, d, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					
						if (dp.getconvertToPortfolioMessageRecordTypeInvalid(10)!=null) {
							String text=dp.getconvertToPortfolioMessageRecordTypeInvalid(10).getText();
							if (text.contains(dp.invalidRecordType)) {
								log(LogStatus.INFO,"successfully verified invalid record type portfolio message",YesNo.Yes);
								
							}else {
								sa.assertTrue(false,"could not verify invalid record type portfolio message\nExpected: "+text+"\nActual: "+dp.invalidRecordType);
								log(LogStatus.SKIP,"could not verify invalid record type portfolio message\\nExpected: "+text+"\nActual: "+dp.invalidRecordType,YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"not visible already portfolio message");
							log(LogStatus.SKIP,"not visible already portfolio message",YesNo.Yes);
						}
						if ( dp.getconvertToPortfolioCrossButton(10)!=null) {
							log(LogStatus.INFO,"successfully verified presence of cross icon",YesNo.Yes);
							
						}else {
							sa.assertTrue(false,"could not verify cross icon presence");
							log(LogStatus.SKIP,"could not verify cross icon presence",YesNo.Yes);
						}
						if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "successfully verified finish button after convert to portfolio", YesNo.No);
						}else {
							sa.assertTrue(false,"could not verify convert to portfolio as finish button not clicked");
							log(LogStatus.SKIP,"could not verify convert to portfolio as finish button not clicked",YesNo.Yes);
						}
				}else {
					sa.assertTrue(false,"convertToPortfolio button is not clickable");
					log(LogStatus.SKIP,"convertToPortfolio button is not clickable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,d+" deal is not found on deal tab");
				log(LogStatus.SKIP,d+" deal is not found on deal tab",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}
		}
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
				};
		String labelValues[]={M6Deal7Stage,M6Deal7Stage};


		for (int i =0;i<labelName.length;i++) {
			if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
				log(LogStatus.SKIP,"successfully verified "+labelName[i],YesNo.No);

			}else {
				sa.assertTrue(false,"Not Able to verify "+labelName[i]);
				log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
			}

		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M6tc013_1_CheckConvertToPortfolioFuncAfterInvactiveRT(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String rt=InstRecordType.Portfolio_Company.toString().replace("_", " ");
		String updateLabel="Portfolio_Company";
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {

				if(setup.searchStandardOrCustomObject(environment, mode, object.Firm)) {
					if(setup.clickOnObjectFeature(environment, mode, object.Firm, ObjectFeatureName.recordTypes)) {
						if (sendKeys(driver, setup.getsearchTextboxFieldsAndRelationships(10), rt+Keys.ENTER, "status", action.BOOLEAN)) {
							if (setup.clickOnAlreadyCreatedLayout(rt)) {
								switchToFrame(driver, 10, setup.getFrame(PageName.RecordTypePortfolioCompany, 10));
								if (click(driver, setup.getEditButton(environment,Mode.Classic.toString(),10), "edit", action.BOOLEAN)) {
									ThreadSleep(2000);
									switchToDefaultContent(driver);
									switchToFrame(driver, 10, setup.getFrame(PageName.RecordTypePortfolioCompany, 10));
									ThreadSleep(2000);
										if (sendKeys(driver, setup.getRecordTypeLabel(projectName, "Record Type Name", 10), updateLabel, "label", action.BOOLEAN)){
											ThreadSleep(2000);
											switchToAlertAndAcceptOrDecline(driver, 10, action.ACCEPT);
										}
									ThreadSleep(2000);
									if (click(driver, setup.getactiveCheckbox(10), "active", action.BOOLEAN)) {
										log(LogStatus.INFO, "activeCheckbox is clicked",YesNo.No);

									}else {
										log(LogStatus.FAIL, "activeCheckbox not clickable",YesNo.Yes);
										sa.assertTrue(false, "activeCheckbox not clickable");
									}
									
									if (clickUsingJavaScript(driver, setup.getSaveButtonInCustomFields(10), "save", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "save button is clicked",YesNo.No);

										}else {
											log(LogStatus.FAIL, "save button not clickable",YesNo.Yes);
											sa.assertTrue(false, "save button not clickable");
										}
								}else {
									log(LogStatus.FAIL, "edit button not clickable",YesNo.Yes);
									sa.assertTrue(false, "edit button not clickable");
								}
							}else {
								log(LogStatus.FAIL, InstRecordType.Portfolio+" not found",YesNo.Yes);
								sa.assertTrue(false, InstRecordType.Portfolio+" not found");
							}
						}else {
							log(LogStatus.FAIL, "search textbox not found",YesNo.Yes);
							sa.assertTrue(false, "search textbox not found");
						}
					}else {
						log(LogStatus.FAIL, "record type feature not clickable",YesNo.Yes);
						sa.assertTrue(false, "record type feature not clickable");
					}
				}else {
					log(LogStatus.FAIL, "institution object is not clickable",YesNo.Yes);
					sa.assertTrue(false, "institution object is not clickable");
				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch",YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
			sa.assertTrue(false, "setup link is not clickable");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M6tc013_2_CheckConvertToPortfolioFuncAfterInvactiveRT(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
		};
		String labelValues[]={M6Deal8Stage,M6Deal8Stage};

		
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal2, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					
					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						ThreadSleep(2000);
						if (dp.getconvertToPortfolioMessageRepeat(10)!=null) {
							String text=dp.getconvertToPortfolioMessageRepeat(10).getText();
							if (text.contains(dp.convertToPortfolioRepeat(M6Ins3))) {
								log(LogStatus.INFO,"successfully verified already portfolio message",YesNo.Yes);
								
							}else {
								sa.assertTrue(false,"could not verify already portfolio message\nExpected: "+text+"\nActual: "+dp.convertToPortfolioRepeat(M6Ins2));
								log(LogStatus.SKIP,"could not verify already portfolio message\\nExpected: "+text+"\nActual: "+dp.convertToPortfolioRepeat(M6Ins2),YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"not visible already portfolio message");
							log(LogStatus.SKIP,"not visible already portfolio message",YesNo.Yes);
						}
						if ( dp.getconvertToPortfolioCrossButton(10)!=null) {
							log(LogStatus.INFO,"successfully verified presence of cross icon",YesNo.Yes);
							
						}else {
							sa.assertTrue(false,"could not verify cross icon presence");
							log(LogStatus.SKIP,"could not verify cross icon presence",YesNo.Yes);
						}
						if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "successfully verified finish button after convert to portfolio", YesNo.No);
						}else {
							sa.assertTrue(false,"could not verify convert to portfolio as finish button not clicked");
							log(LogStatus.SKIP,"could not verify convert to portfolio as finish button not clicked",YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"next button is not clickable");
						log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"convertToPortfolio button is not clickable");
					log(LogStatus.SKIP,"convertToPortfolio button is not clickable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,M6Deal2+" deal is not found on deal tab");
				log(LogStatus.SKIP,M6Deal2+" deal is not found on deal tab",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}
		
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal8, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10), "convert to portfolio button", action.BOOLEAN)) {
					if (click(driver, dp.getnextButton(10), "next button", action.BOOLEAN)) {
						ThreadSleep(2000);
						if (dp.getconvertToPortfolioMessageUnhandledFlow(10) != null) {
							if (dp.getconvertToPortfolioMessageUnhandledFlow(10).getText()
									.contains(dp.unhandledError)) {
								log(LogStatus.INFO, "successfully verified unhandled flow message", YesNo.Yes);

							} else {
								sa.assertTrue(false, "could not verify unhandled flow message");
								log(LogStatus.SKIP, "could not verify unhandled flow message", YesNo.Yes);
							}
						} else {
							sa.assertTrue(false, "not visible already portfolio message for unhandled flow");
							log(LogStatus.SKIP, "not visible already portfolio message for unhandled flow", YesNo.Yes);
						}
						if (click(driver, dp.getconvertToPortfolioCrossButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on cross button", YesNo.No);
						} else {
							sa.assertTrue(false, "could not click on cross button");
							log(LogStatus.SKIP, "could not click on cross button", YesNo.Yes);
						}

						for (int i = 0; i < labelName.length; i++) {
							if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i], labelValues[i])) {
								log(LogStatus.SKIP, "successfully verified " + labelName[i], YesNo.No);

							} else {
								sa.assertTrue(false, "Not Able to verify " + labelName[i]);
								log(LogStatus.SKIP, "Not Able to verify " + labelName[i], YesNo.Yes);
							}

						}
					} else {
						sa.assertTrue(false, "next button is not clickable");
						log(LogStatus.SKIP, "next button is not clickable", YesNo.Yes);
					}
				} else {
					sa.assertTrue(false, "convert to portfolio button is not clickable");
					log(LogStatus.SKIP, "convert to portfolio button is not clickable", YesNo.Yes);
				}
			} else {
				sa.assertTrue(false, M6Deal8 + " deal is not clickable");
				log(LogStatus.SKIP, M6Deal8 + " deal is not clickable", YesNo.Yes);
			}
		} else {
			sa.assertTrue(false, "deal tab is not clickable");
			log(LogStatus.SKIP, "deal tab is not clickable", YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M6tc014_1_CheckConvertToPortfolioFuncAfterInvactiveClosedDealStage(String projectName) {
		//alert problem
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String rt=InstRecordType.Portfolio_Company.toString().replace("_", " ");
		String updateLabel="Portfolio_Company";
		String userName="PE Standard User";
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {

				if (setup.searchStandardOrCustomObject(environment, mode, object.Firm)) {
					if (setup.clickOnObjectFeature(environment, mode, object.Firm, ObjectFeatureName.recordTypes)) {
						if (sendKeys(driver, setup.getsearchTextboxFieldsAndRelationships(10), rt + Keys.ENTER,
								"status", action.BOOLEAN)) {
							if (setup.clickOnAlreadyCreatedLayout(rt)) {

								switchToFrame(driver, 10, setup.getFrame(PageName.RecordTypePortfolioCompany, 10));
								if (click(driver, setup.getEditButton(environment, Mode.Classic.toString(), 10), "edit",
										action.BOOLEAN)) {
									ThreadSleep(2000);
									switchToDefaultContent(driver);
									switchToFrame(driver, 10, setup.getFrame(PageName.RecordTypePortfolioCompany, 10));
									ThreadSleep(2000);
									// setup.getRecordTypeLabel(projectName,"Record Type
									// Name",10).sendKeys(updateLabel);
									// if(sendKeys(driver,setup.getRecordTypeLabel(projectName,"Record Type Name",
									// 10), , "name",action.SCROLLANDBOOLEAN)) {
									if (!isSelected(driver, setup.getactiveCheckbox(10), "active checkbox")) {
										if (clickUsingJavaScript(driver, setup.getactiveCheckbox(10), "active",
												action.BOOLEAN)) {
											log(LogStatus.INFO, "activeCheckbox is now checked", YesNo.No);

										} else {
											log(LogStatus.FAIL, "activeCheckbox not clickable", YesNo.Yes);
											sa.assertTrue(false, "activeCheckbox not clickable");
										}
									}
									if (click(driver, setup.getSaveButtonInCustomFields(10), "save",
											action.SCROLLANDBOOLEAN)) {
										ThreadSleep(3000);
										log(LogStatus.INFO, "save button is clicked", YesNo.No);

									} else {
										log(LogStatus.FAIL, "save button not clickable", YesNo.Yes);
										sa.assertTrue(false, "save button not clickable");
									} // }else { //
//									log(LogStatus.FAIL, "name textbox not visible", YesNo.Yes); //
//									sa.assertTrue(false, "name textbox not visible"); // } }else {
//									log(LogStatus.FAIL, "edit button not clickable", YesNo.Yes);
//									sa.assertTrue(false, "edit button not clickable");
								}
							} else {
								log(LogStatus.FAIL, InstRecordType.Portfolio + " not found", YesNo.Yes);
								sa.assertTrue(false, InstRecordType.Portfolio + " not found");
							}
						} else {
							log(LogStatus.FAIL, "search textbox not found", YesNo.Yes);
							sa.assertTrue(false, "search textbox not found");
						}
					} else {
						log(LogStatus.FAIL, "record type feature not clickable", YesNo.Yes);
						sa.assertTrue(false, "record type feature not clickable");
					}
				} else {
					log(LogStatus.FAIL, "institution object is not clickable", YesNo.Yes);
					sa.assertTrue(false, "institution object is not clickable");
				}
				ThreadSleep(500);
				if (setup.searchStandardOrCustomObject(environment, mode, object.Profiles)) {
					log(LogStatus.INFO, "click on Object : " + object.Profiles, YesNo.No);
					ThreadSleep(2000);
					switchToDefaultContent(driver);
					switchToFrame(driver, 60, setup.getSetUpPageIframe(60));
					boolean flag = false;
					;
					String xpath = "";
					xpath = "//th//a[text()='" + userName + "']";
					WebElement ele = FindElement(driver, xpath, userName, action.SCROLLANDBOOLEAN, 10);
					ele = isDisplayed(driver, ele, "visibility", 10, userName);
					if (click(driver, ele, userName.toString(), action.BOOLEAN)) {
						log(LogStatus.INFO, "able to click on " + userName, YesNo.No);
						ThreadSleep(10000);
						switchToDefaultContent(driver);
						ThreadSleep(5000);
						switchToFrame(driver, 60, setup.getSetUpPageIframe(60));
						xpath = "//*[text()='Accounts']/following-sibling::td/a[text()='Edit']";
						ele = FindElement(driver, xpath, "Edit Button", action.SCROLLANDBOOLEAN, 10);
						ele = isDisplayed(driver, ele, "visibility", 10, "Edit Button");
						if (click(driver, ele, "Edit Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "able to click on edit button for record type settiing", YesNo.No);
							switchToDefaultContent(driver);
							ThreadSleep(5000);
							switchToFrame(driver, 60, setup.getSetUpPageIframe(60));
							ThreadSleep(2000);
							if (selectVisibleTextFromDropDown(driver, setup.getavailableRecordType(60),
									"Available Tab List", rt)) {
								appLog.info(rt + " is selected successfully in available tabs");
								if (click(driver, setup.getAddBtn(60), "Custom Tab Add Button",
										action.SCROLLANDBOOLEAN)) {
									appLog.error("clicked on add button");
								} else {
									sa.assertTrue(false, "Not able to click on add button so cannot add custom tabs");
									appLog.error("Not able to click on add button so cannot add custom tabs");
								}
							} else {
								appLog.error(rt + " record type is not Available list Tab.");
								sa.assertTrue(false, rt + " record type is not Available list Tab.");
							}
							if (clickUsingJavaScript(driver, setup.getCreateUserSaveBtn_Lighting(30), "Save Button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on save button for record type settiing", YesNo.No);
								ThreadSleep(2000);
							} else {
								log(LogStatus.ERROR, "not able to click on save button for record type settiing",
										YesNo.Yes);
								sa.assertTrue(false, "not able to click on save button for record type settiing");

							}
						} else {
							log(LogStatus.ERROR, "not able to click on edit button for record type settiing",
									YesNo.Yes);
							sa.assertTrue(false, "not able to click on edit button for record type settiing");

						}
					} else {
						log(LogStatus.ERROR, userName + " profile is not clickable", YesNo.Yes);
						sa.assertTrue(false, userName + " profile is not clickable");
					}
				} else {
					log(LogStatus.ERROR, "profiles tab is not clickable", YesNo.Yes);
					sa.assertTrue(false, "profiles tab is not clickable");
				}

				if(setup.searchStandardOrCustomObject(environment, mode, object.Deal)) {
					if(setup.clickOnObjectFeature(environment, mode, object.Deal, ObjectFeatureName.FieldAndRelationShip)) {
						if (sendKeys(driver, setup.getsearchTextboxFieldsAndRelationships(10), excelLabel.Stage.toString(), "stage", action.BOOLEAN)) {
							if (setup.clickOnAlreadyCreatedLayout(excelLabel.Stage.toString())) {
								switchToDefaultContent(driver);
								switchToFrame(driver, 10, setup.getFrame(PageName.PipelineCustomPage, 10));
								ThreadSleep(5000);
								WebElement ele=dp.findDeactivateLink(projectName, Stage.Closed.toString());
								if (click(driver, ele, "deactivate  closed", action.SCROLLANDBOOLEAN)) {
									ThreadSleep(5000);
									if(!isAlertPresent(driver)) {
										clickUsingJavaScript(driver, ele, "deactivate closed", action.SCROLLANDBOOLEAN);
									}
									ThreadSleep(2000);
									//driver.switchTo().alert().accept();
									switchToAlertAndAcceptOrDecline(driver, 10, action.ACCEPT);
									switchToDefaultContent(driver);
									switchToFrame(driver, 10, setup.getFrame(PageName.PipelineCustomPage, 10));
									ThreadSleep(2000);
									ele=dp.findActivateLink(projectName, Stage.Closed.toString());
									if(ele!=null) {
										log(LogStatus.INFO, "successfully deactivated closed value", YesNo.No);
									}else {
										sa.assertTrue(false,"not able to deactivate closed value");
										log(LogStatus.SKIP,"not able to deactivate closed value",YesNo.Yes);
									}

								}else {
									sa.assertTrue(false,"deactivate link is not clickable");
									log(LogStatus.SKIP,"deactivate link is not clickable",YesNo.Yes);
								}
							}else {
								sa.assertTrue(false,"stage field link is not clickable");
								log(LogStatus.SKIP,"stage field link is not clickable",YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"search textbox is not visible");
							log(LogStatus.SKIP,"search textbox is not visible",YesNo.Yes);
						}
					}else {
							log(LogStatus.FAIL, "field n relationships feature not clickable",YesNo.Yes);
							sa.assertTrue(false, "field n relationships feature not clickable");
						}
					}else {
						log(LogStatus.FAIL, "deal object is not clickable",YesNo.Yes);
						sa.assertTrue(false, "deal object is not clickable");
					}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch",YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
			sa.assertTrue(false, "setup link is not clickable");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M6tc014_2_CheckConvertToPortfolioFuncAfterInvactiveClosedDealStage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
		};
		String labelValues[]={Stage.Closed.toString(),Stage.Closed.toString()};

		
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal2, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					
					if (clickUsingJavaScript(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						ThreadSleep(3000);
						if (dp.getconvertToPortfolioMessageRepeat(10)!=null) {
							String text=dp.getconvertToPortfolioMessageRepeat(10).getText();
							if (text.contains(dp.convertToPortfolioRepeat(M6Ins3))) {
								log(LogStatus.INFO,"successfully verified already portfolio message",YesNo.Yes);
								
							}else {
								sa.assertTrue(false,"could not verify already portfolio message\nExpected: "+text+"\nActual: "+dp.convertToPortfolioRepeat(M6Ins2));
								log(LogStatus.SKIP,"could not verify already portfolio message\\nExpected: "+text+"\nActual: "+dp.convertToPortfolioRepeat(M6Ins2),YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"not visible already portfolio message");
							log(LogStatus.SKIP,"not visible already portfolio message",YesNo.Yes);
						}
						if ( dp.getconvertToPortfolioCrossButton(10)!=null) {
							log(LogStatus.INFO,"successfully verified presence of cross icon",YesNo.Yes);
							
						}else {
							sa.assertTrue(false,"could not verify cross icon presence");
							log(LogStatus.SKIP,"could not verify cross icon presence",YesNo.Yes);
						}
						if (clickUsingJavaScript(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "successfully verified finish button after convert to portfolio", YesNo.No);
						}else {
							sa.assertTrue(false,"could not verify convert to portfolio as finish button not clicked");
							log(LogStatus.SKIP,"could not verify convert to portfolio as finish button not clicked",YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"next button is not clickable");
						log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"convertToPortfolio button is not clickable");
					log(LogStatus.SKIP,"convertToPortfolio button is not clickable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,M6Deal2+" deal is not found on deal tab");
				log(LogStatus.SKIP,M6Deal2+" deal is not found on deal tab",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}
		
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal9, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					if (dp.getconvertToPortfolioMessage(M6Ins9,10)!=null) {
						log(LogStatus.INFO, "successfully verified convert to portfolio text message", YesNo.No);
					}else {
						sa.assertTrue(false,"could not verify convert to portfolio text message before next");
						log(LogStatus.SKIP,"could not verify convert to portfolio text message before next",YesNo.Yes);
					}
					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						String text=dp.getconvertToPortfolioMessageAfterNext(10).getText();

						if (text.contains(dp.convertToPortfolioAfterNext(M6Ins9))) {
							log(LogStatus.INFO,"successfully verified max character inst name on convert to portfolio message congratulations",YesNo.Yes);

						}else {
							sa.assertTrue(false,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins15)+"\nactual: "+text);
							log(LogStatus.SKIP,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins15)+"\nactual: "+text,YesNo.Yes);
						}
						if ( dp.getconvertToPortfolioCrossButton(10)!=null) {
							log(LogStatus.INFO, "successfully verified cross button after convert to portfolio", YesNo.No);
						}else {
							sa.assertTrue(false,"could not verify convert to portfolio as cross button not clicked");
							log(LogStatus.SKIP,"could not verify convert to portfolio as cross button not clicked",YesNo.Yes);
						}
						if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on finish button", YesNo.No);
						}else {
							sa.assertTrue(false,"could not click on finish button");
							log(LogStatus.SKIP,"could not click on finish button",YesNo.Yes);
						}
						ThreadSleep(3000);
						refresh(driver);
						ThreadSleep(3000);
					

				for (int i =0;i<labelName.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
						log(LogStatus.SKIP,"successfully verified "+labelName[i],YesNo.No);

					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
					}

				}
					}else {
						sa.assertTrue(false,"next button is not clickable");
						log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"convert to portfolio button is not clickable");
					log(LogStatus.SKIP,"convert to portfolio button is not clickable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,M6Deal9+" deal is not clickable");
				log(LogStatus.SKIP,M6Deal9+" deal is not clickable",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M6tc015_1_CheckConvertToPortfolioFuncAfterRenameClosedDealStage(String projectName) {
		//alert problem
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String rt=InstRecordType.Portfolio_Company.toString().replace("_", " ");
		String updateLabel="Portfolio_Company";
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {
				if(setup.searchStandardOrCustomObject(environment, mode, object.Deal)) {
					if(setup.clickOnObjectFeature(environment, mode, object.Deal, ObjectFeatureName.FieldAndRelationShip)) {
						if (sendKeys(driver, setup.getsearchTextboxFieldsAndRelationships(10), excelLabel.Stage.toString(), "stage", action.BOOLEAN)) {
							if (setup.clickOnAlreadyCreatedLayout(excelLabel.Stage.toString())) {
								switchToDefaultContent(driver);
								ThreadSleep(5000);
								switchToFrame(driver, 10, setup.getFrame(PageName.PipelineCustomPage, 10));
								WebElement ele=dp.findActivateLink(projectName, Stage.Closed.toString());
								
								if (clickUsingJavaScript(driver, ele, "activate closed", action.SCROLLANDBOOLEAN)) {
									ThreadSleep(3000);
									switchToDefaultContent(driver);
									ThreadSleep(5000);
									switchToFrame(driver, 10, setup.getFrame(PageName.PipelineCustomPage, 10));
										
									ele=dp.findDeactivateLink(projectName, Stage.Closed.toString());
									if(ele!=null) {
										log(LogStatus.INFO, "successfully activated closed value", YesNo.No);
									}else {
										sa.assertTrue(false,"not able to activate closed value");
										log(LogStatus.SKIP,"not able to activate closed value",YesNo.Yes);
									}
									ThreadSleep(5000);
									ele=setup.clickOnEditInFrontOfFieldValues(projectName, Stage.Closed.toString());
									if (clickUsingJavaScript(driver, ele, "closed", action.BOOLEAN)) {
										switchToDefaultContent(driver);
										ThreadSleep(3000);
										switchToFrame(driver, 10, setup.getFrame(PageName.PipelineCustomPage, 10));
										sendKeys(driver, setup.getFieldLabelTextBox1(10), Stage.Closed_Updated.toString(), "label", action.BOOLEAN);
											

										if (clickUsingJavaScript(driver, fp.getCustomTabSaveBtn(10), "save", action.BOOLEAN)) {
											ThreadSleep(3000);
											switchToDefaultContent(driver);
											ThreadSleep(3000);
											switchToFrame(driver, 10, setup.getFrame(PageName.PipelineCustomPage, 10));
											ThreadSleep(5000);
											ele=dp.findDeactivateLink(projectName, Stage.Closed_Updated.toString());
											if (click(driver, ele, "deactivate  closed", action.SCROLLANDBOOLEAN)) {
												ThreadSleep(5000);
												if(!isAlertPresent(driver)) {
													clickUsingJavaScript(driver, ele, "deactivate closed", action.SCROLLANDBOOLEAN);
												}
											if(ele!=null) {
												log(LogStatus.INFO, "successfully renamed closed value", YesNo.No);
												if (clickUsingActionClass(driver, ele)) {
													
													//driver.switchTo().alert().accept();
													switchToAlertAndAcceptOrDecline(driver, 10, action.ACCEPT);
													switchToDefaultContent(driver);
													switchToFrame(driver, 10, setup.getFrame(PageName.PipelineCustomPage, 10));
														
													ele=dp.findActivateLink(projectName, Stage.Closed_Updated.toString());
													if(ele!=null) {
														log(LogStatus.INFO, "successfully deactivated closed value", YesNo.No);
													}else {
														sa.assertTrue(false,"not able to deactivate closed value");
														log(LogStatus.SKIP,"not able to deactivate closed value",YesNo.Yes);
													}
												}else {
													sa.assertTrue(false,"not able to click on deactivate link");
													log(LogStatus.SKIP,"not able to click on deactivate link",YesNo.Yes);
												}
											}else {
												sa.assertTrue(false,"not able to renamed closed value");
												log(LogStatus.SKIP,"not able to renamed closed value",YesNo.Yes);
											}
										}else {
											sa.assertTrue(false,"not able to click on save button");
											log(LogStatus.SKIP,"not able to click on save button",YesNo.Yes);
										}

									}else {
										sa.assertTrue(false,"edit button is not clickable");
										log(LogStatus.SKIP,"edit button is not clickable",YesNo.Yes);
									}
								}else {
									sa.assertTrue(false,"activate link is not clickable");
									log(LogStatus.SKIP,"activate link is not clickable",YesNo.Yes);
								}
							}else {
								sa.assertTrue(false,"stage field link is not clickable");
								log(LogStatus.SKIP,"stage field link is not clickable",YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"search textbox is not visible");
							log(LogStatus.SKIP,"search textbox is not visible",YesNo.Yes);
						}
					}else {
							log(LogStatus.FAIL, "field n relationships feature not clickable",YesNo.Yes);
							sa.assertTrue(false, "field n relationships feature not clickable");
						}
					}else {
						log(LogStatus.FAIL, "deal object is not clickable",YesNo.Yes);
						sa.assertTrue(false, "deal object is not clickable");
					}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch",YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
			sa.assertTrue(false, "setup link is not clickable");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	}
	@Parameters({ "projectName"})
	@Test
	public void M6tc015_2_CheckConvertToPortfolioFuncAfterRenameClosedDealStage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
		};
		String labelValues[]={Stage.Closed_Updated.toString(),Stage.Closed_Updated.toString()};

		
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal2, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					
					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						ThreadSleep(4000);
						if (dp.getconvertToPortfolioMessageRepeat(10)!=null) {
							String text=dp.getconvertToPortfolioMessageRepeat(10).getText();
							if (text.contains(dp.convertToPortfolioRepeat(M6Ins3))) {
								log(LogStatus.INFO,"successfully verified already portfolio message",YesNo.Yes);
								
							}else {
								sa.assertTrue(false,"could not verify already portfolio message\nExpected: "+text+"\nActual: "+dp.convertToPortfolioRepeat(M6Ins2));
								log(LogStatus.SKIP,"could not verify already portfolio message\\nExpected: "+text+"\nActual: "+dp.convertToPortfolioRepeat(M6Ins2),YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"not visible already portfolio message");
							log(LogStatus.SKIP,"not visible already portfolio message",YesNo.Yes);
						}
						if ( dp.getconvertToPortfolioCrossButton(10)!=null) {
							log(LogStatus.INFO,"successfully verified presence of cross icon",YesNo.Yes);
							
						}else {
							sa.assertTrue(false,"could not verify cross icon presence");
							log(LogStatus.SKIP,"could not verify cross icon presence",YesNo.Yes);
						}
						if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "successfully verified finish button after convert to portfolio", YesNo.No);
						}else {
							sa.assertTrue(false,"could not verify convert to portfolio as finish button not clicked");
							log(LogStatus.SKIP,"could not verify convert to portfolio as finish button not clicked",YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"next button is not clickable");
						log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"convertToPortfolio button is not clickable");
					log(LogStatus.SKIP,"convertToPortfolio button is not clickable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,M6Deal2+" deal is not found on deal tab");
				log(LogStatus.SKIP,M6Deal2+" deal is not found on deal tab",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}
		
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal20, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						String text=dp.getconvertToPortfolioMessageAfterNext(10).getText();

						if (text.contains(dp.convertToPortfolioAfterNext(M6Ins20))) {
							log(LogStatus.INFO,"successfully verified max character inst name on convert to portfolio message congratulations",YesNo.Yes);

						}else {
							sa.assertTrue(false,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins20)+"\nactual: "+text);
							log(LogStatus.SKIP,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins20)+"\nactual: "+text,YesNo.Yes);
						}
						if (click(driver, dp.getconvertToPortfolioCrossButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on cross button", YesNo.No);
						}else {
							sa.assertTrue(false,"could not click on cross button");
							log(LogStatus.SKIP,"could not click on cross button",YesNo.Yes);
						}
						
					
						refresh(driver);
						ThreadSleep(5000);
				for (int i =0;i<labelName.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i],false)) {
						log(LogStatus.SKIP,"successfully verified "+labelName[i],YesNo.No);

					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
					}

				}
					}else {
						sa.assertTrue(false,"next button is not clickable");
						log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"convert to portfolio button is not clickable");
					log(LogStatus.SKIP,"convert to portfolio button is not clickable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,M6Deal20+" deal is not clickable");
				log(LogStatus.SKIP,M6Deal20+" deal is not clickable",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	@Parameters({ "projectName"})
	@Test
	public void M6tc016_1_CheckConvertToPortfolioFuncWhenRecordTypeInstitution(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {

								
				if(setup.searchStandardOrCustomObject(environment, mode, object.Deal)) {
					if(setup.clickOnObjectFeature(environment, mode, object.Deal, ObjectFeatureName.FieldAndRelationShip)) {
						if (sendKeys(driver, setup.getsearchTextboxFieldsAndRelationships(10), excelLabel.Stage.toString(), "stage", action.BOOLEAN)) {
							if (setup.clickOnAlreadyCreatedLayout(excelLabel.Stage.toString())) {
								switchToDefaultContent(driver);
								ThreadSleep(3000);
								switchToFrame(driver, 10, setup.getFrame(PageName.PipelineCustomPage, 10));
								WebElement ele=dp.findActivateLink(projectName, Stage.Closed_Updated.toString());
								if (click(driver, ele, "activate closed", action.SCROLLANDBOOLEAN)) {
									ThreadSleep(3000);
									switchToDefaultContent(driver);
									ThreadSleep(3000);
									switchToFrame(driver, 10, setup.getFrame(PageName.PipelineCustomPage, 10));
										
									ele=dp.findDeactivateLink(projectName, Stage.Closed_Updated.toString());
									if(ele!=null) {
										log(LogStatus.INFO, "successfully activated closedupdated value", YesNo.No);
									}else {
										sa.assertTrue(false,"not able to activate closedupdated value");
										log(LogStatus.SKIP,"not able to activate closedupdated value",YesNo.Yes);
									}
									
								}else {
									sa.assertTrue(false,"activate link is not clickable");
									log(LogStatus.SKIP,"activate link is not clickable",YesNo.Yes);
								}
							}else {
								sa.assertTrue(false,"stage field link is not clickable");
								log(LogStatus.SKIP,"stage field link is not clickable",YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"search textbox is not visible");
							log(LogStatus.SKIP,"search textbox is not visible",YesNo.Yes);
						}
					}else {
							log(LogStatus.FAIL, "field n relationships feature not clickable",YesNo.Yes);
							sa.assertTrue(false, "field n relationships feature not clickable");
						}
					}else {
						log(LogStatus.FAIL, "deal object is not clickable",YesNo.Yes);
						sa.assertTrue(false, "deal object is not clickable");
					}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch",YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
			sa.assertTrue(false, "setup link is not clickable");
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M6tc016_2_CheckConvertToPortfolioFuncWhenRecordTypeInstitution(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
		};
		String labelValues[]={Stage.Closed.toString(),Stage.Closed_Updated.toString()};

		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal1, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						String text=dp.getconvertToPortfolioMessageAfterNext(10).getText();

						if (text.contains(dp.convertToPortfolioAfterNext(M6Ins1))) {
							log(LogStatus.INFO,"successfully verified max character inst name on convert to portfolio message congratulations",YesNo.Yes);

						}else {
							sa.assertTrue(false,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins1)+"\nactual: "+text);
							log(LogStatus.SKIP,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins1)+"\nactual: "+text,YesNo.Yes);
						}
						if ( dp.getconvertToPortfolioCrossButton(10)!=null) {
							log(LogStatus.INFO, "successfully verified cross button after convert to portfolio", YesNo.No);
						}else {
							sa.assertTrue(false,"could not verify convert to portfolio as cross button not clicked");
							log(LogStatus.SKIP,"could not verify convert to portfolio as cross button not clicked",YesNo.Yes);
						}
						if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on finish button", YesNo.No);
						}else {
							sa.assertTrue(false,"could not click on finish button");
							log(LogStatus.SKIP,"could not click on finish button",YesNo.Yes);
						}
						
					ThreadSleep(3000);
					refresh(driver);
					ThreadSleep(3000);
				for (int i =0;i<labelName.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i],false)) {
						log(LogStatus.SKIP,"successfully verified "+labelName[i],YesNo.No);

					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
					}

				}
					}else {
						sa.assertTrue(false,"next button is not clickable");
						log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"convert to portfolio button is not clickable");
					log(LogStatus.SKIP,"convert to portfolio button is not clickable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,M6Deal20+" deal is not clickable");
				log(LogStatus.SKIP,M6Deal20+" deal is not clickable",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M6tc017_1_CheckConvertToPortfolioFuncWhenRecordTypeAdvisor(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);

		String updateLabel="Portfolio_Company";
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {


				if(setup.searchStandardOrCustomObject(environment, mode, object.Deal)) {
					if(setup.clickOnObjectFeature(environment, mode, object.Deal, ObjectFeatureName.FieldAndRelationShip)) {
						if (sendKeys(driver, setup.getsearchTextboxFieldsAndRelationships(10), excelLabel.Stage.toString(), "stage", action.BOOLEAN)) {
							if (setup.clickOnAlreadyCreatedLayout(excelLabel.Stage.toString())) {
								switchToDefaultContent(driver);
								switchToFrame(driver, 10, setup.getFrame(PageName.PipelineCustomPage, 10));
								WebElement ele;

								
								switchToDefaultContent(driver);
								ThreadSleep(3000);
								switchToFrame(driver, 10, setup.getFrame(PageName.PipelineCustomPage, 10));

								ele=dp.findDeactivateLink(projectName, Stage.Closed_Updated.toString());
								if(ele!=null) {
									log(LogStatus.INFO, "closed_updated stage is active", YesNo.No);
								}else {
									sa.assertTrue(false,"not able to verify activated closed value");
									log(LogStatus.SKIP,"not able to verify activated closed value",YesNo.Yes);
								}
								ele=setup.clickOnEditInFrontOfFieldValues(projectName, Stage.Closed_Updated.toString(),false);
								if (click(driver, ele, "edit", action.BOOLEAN)) {
									switchToDefaultContent(driver);
									ThreadSleep(3000);
									switchToFrame(driver, 10, setup.getFrame(PageName.PipelineCustomPage, 10));
									ThreadSleep(3000);
									sendKeys(driver, setup.getFieldLabelTextBox1(10), Stage.Closed.toString(), "label", action.BOOLEAN);


									if (clickUsingJavaScript(driver, fp.getCustomTabSaveBtn(10), "save", action.BOOLEAN)) {
										ThreadSleep(3000);
									}else {
										sa.assertTrue(false,"not able to click on deactivate link");
										log(LogStatus.SKIP,"not able to click on deactivate link",YesNo.Yes);
									}
								}else {
									sa.assertTrue(false,"edit button is not clickable");
									log(LogStatus.SKIP,"edit button is not clickable",YesNo.Yes);
								}
							}else {
								sa.assertTrue(false,"stage field link is not clickable");
								log(LogStatus.SKIP,"stage field link is not clickable",YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"search textbox is not visible");
							log(LogStatus.SKIP,"search textbox is not visible",YesNo.Yes);
						}
					}else {
						log(LogStatus.FAIL, "field n relationships feature not clickable",YesNo.Yes);
						sa.assertTrue(false, "field n relationships feature not clickable");
					}
				}else {
					log(LogStatus.FAIL, "deal object is not clickable",YesNo.Yes);
					sa.assertTrue(false, "deal object is not clickable");
				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch",YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
			sa.assertTrue(false, "setup link is not clickable");
		}

		lp.CRMlogout();
		sa.assertAll();
	}
	@Parameters({ "projectName"})
	@Test
	public void M6tc017_2_CheckConvertToPortfolioFuncWhenRecordTypeAdvisor(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		int total=1;
		double avgDealQualityScore=closedScore/total;
		String labelName1[]={excelLabel.Record_Type.toString(),excelLabel.Status.toString()
		};
		String labelValues1[]={InstRecordType.Portfolio_Company.toString(),InstRecordType.Portfolio_Company.toString()};
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
		,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.Closed.toString(),Stage.Closed.toString(),String.valueOf(closedScore)};
		
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal12, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					if (dp.getconvertToPortfolioMessage(M6Ins12,10)!=null) {
						log(LogStatus.INFO, "successfully verified convert to portfolio text message", YesNo.No);
					}else {
						sa.assertTrue(false,"could not verify convert to portfolio text message before next");
						log(LogStatus.SKIP,"could not verify convert to portfolio text message before next",YesNo.Yes);
					}
					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						String text=dp.getconvertToPortfolioMessageAfterNext(10).getText();

						if (text.contains(dp.convertToPortfolioAfterNext(M6Ins12))) {
							log(LogStatus.INFO,"successfully verified max character inst name on convert to portfolio message congratulations",YesNo.Yes);

						}else {
							sa.assertTrue(false,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins12)+"\nactual: "+text);
							log(LogStatus.SKIP,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins12)+"\nactual: "+text,YesNo.Yes);
						}
						if ( dp.getfinishButton(10)!=null) {
							log(LogStatus.INFO, "successfully verified finish button after convert to portfolio", YesNo.No);
						}else {
							sa.assertTrue(false,"could not verify convert to portfolio as finish button not clicked");
							log(LogStatus.SKIP,"could not verify convert to portfolio as finish button not clicked",YesNo.Yes);
						}
						if (click(driver, dp.getconvertToPortfolioCrossButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on cross button", YesNo.No);
						}else {
							sa.assertTrue(false,"could not click on cross button");
							log(LogStatus.SKIP,"could not click on cross button",YesNo.Yes);
						}
						ThreadSleep(3000);
						refresh(driver);
						ThreadSleep(3000);
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i],false)) {
							log(LogStatus.SKIP,"successfully verified "+labelName[i],YesNo.No);

						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}

					}
					
					if(dp.checkValueOfPathComponentValueOfStage(Stage.Closed.toString(), 10)) {
						log(LogStatus.INFO,"stage on path component successfully verified",YesNo.Yes);
							
					}else {
						sa.assertTrue(false,"stage on path component could not be verified");
						log(LogStatus.SKIP,"stage on path component could not be verified",YesNo.Yes);
					}
					}else {
						sa.assertTrue(false,"next button is not clickable");
						log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"convert to portfolio button is not clickable");
					log(LogStatus.SKIP,"convert to portfolio button is not clickable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,M6Deal12+" deal is not clickable");
				log(LogStatus.SKIP,M6Deal12+" deal is not clickable",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Ins12, 10)) {
				for(int i = 0;i<labelName1.length;i++) {
				if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
					log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);

				}else {
					sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
					log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
				}
				}
				ThreadSleep(3000);
				refresh(driver);
				ThreadSleep(3000);
				String text=ip.getLastModifiedTime(projectName,10).getText().trim();
				text=text.split(",")[1];
				if(ip.verifyBeforeTimeOrNot(projectName, text)) {
					log(LogStatus.INFO,"successfully verified last modified time",YesNo.Yes);
					
				}else {
					sa.assertTrue(false,"could not verify last modified time");
					log(LogStatus.SKIP,"could not verify last modified time",YesNo.Yes);
				}
			}
			else {
				sa.assertTrue(false,M6Ins12+" is not found on inst page");
				log(LogStatus.SKIP,M6Ins12+" is not found on inst page",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"inst tab is not clickable");
			log(LogStatus.SKIP,"inst tab is not clickable",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	


	@Parameters({ "projectName"})
	@Test
	public void M6tc018_CheckConvertToPortfolioFuncWhenRecordTypeFundManager(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		int total=1;
		double avgDealQualityScore=closedScore/total;
		String labelName1[]={excelLabel.Record_Type.toString(),excelLabel.Status.toString()
		};
		String labelValues1[]={InstRecordType.Portfolio_Company.toString(),InstRecordType.Portfolio_Company.toString()};
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
		,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.Closed.toString(),Stage.Closed.toString(),String.valueOf(closedScore)};
		
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal13, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					if (dp.getconvertToPortfolioMessage(M6Ins13,10)!=null) {
						log(LogStatus.INFO, "successfully verified convert to portfolio text message", YesNo.No);
					}else {
						sa.assertTrue(false,"could not verify convert to portfolio text message before next");
						log(LogStatus.SKIP,"could not verify convert to portfolio text message before next",YesNo.Yes);
					}
					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						String text=dp.getconvertToPortfolioMessageAfterNext(10).getText();

						if (text.contains(dp.convertToPortfolioAfterNext(M6Ins13))) {
							log(LogStatus.INFO,"successfully verified max character inst name on convert to portfolio message congratulations",YesNo.Yes);

						}else {
							sa.assertTrue(false,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins13)+"\nactual: "+text);
							log(LogStatus.SKIP,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins13)+"\nactual: "+text,YesNo.Yes);
						}
						if ( dp.getconvertToPortfolioCrossButton(10)!=null) {
							log(LogStatus.INFO, "successfully verified cross button after convert to portfolio", YesNo.No);
						}else {
							sa.assertTrue(false,"could not verify convert to portfolio as cross button not clicked");
							log(LogStatus.SKIP,"could not verify convert to portfolio as cross button not clicked",YesNo.Yes);
						}
						ThreadSleep(3000);
						if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on finish button", YesNo.No);
						}else {
							sa.assertTrue(false,"could not click on finish button");
							log(LogStatus.SKIP,"could not click on finish button",YesNo.Yes);
						}
						ThreadSleep(3000);
						refresh(driver);
						ThreadSleep(3000);
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i],false)) {
							log(LogStatus.SKIP,"successfully verified "+labelName[i],YesNo.No);

						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}

					} 
						  if(dp.checkValueOfPathComponentValueOfStage(Stage.Closed.toString(), 10)) {
						  log(LogStatus.INFO,"stage on path component successfully verified",YesNo.Yes)
						  ;
						  
						  }else { sa.assertTrue(false,"stage on path component could not be verified");
						  log(LogStatus.SKIP,"stage on path component could not be verified",YesNo.Yes)
						  ; }
						 
					}else {
						sa.assertTrue(false,"next button is not clickable");
						log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"convert to portfolio button is not clickable");
					log(LogStatus.SKIP,"convert to portfolio button is not clickable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,M6Deal13+" deal is not clickable");
				log(LogStatus.SKIP,M6Deal13+" deal is not clickable",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Ins13, 10)) {
				for(int i = 0;i<labelName1.length;i++) {
				if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
					log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);

				}else {
					sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
					log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
				}
				}
				ThreadSleep(3000);
				refresh(driver);
				ThreadSleep(3000);
				String text=ip.getLastModifiedTime(projectName,10).getText().trim();
				text=text.split(",")[1];
				if(ip.verifyBeforeTimeOrNot(projectName, text)) {
					log(LogStatus.INFO,"successfully verified last modified time",YesNo.Yes);
					
				}else {
					sa.assertTrue(false,"could not verify last modified time");
					log(LogStatus.SKIP,"could not verify last modified time",YesNo.Yes);
				}
			}
			else {
				sa.assertTrue(false,M6Ins13+" is not found on inst page");
				log(LogStatus.SKIP,M6Ins13+" is not found on inst page",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"inst tab is not clickable");
			log(LogStatus.SKIP,"inst tab is not clickable",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	@Parameters({ "projectName"})
	@Test
	public void M6tc019_CheckConvertToPortfolioFuncWhenRecordTypeFundManagersFund(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		int total=1;
		double avgDealQualityScore=closedScore/total;
		String labelName1[]={excelLabel.Record_Type.toString(),excelLabel.Status.toString()
		};
		String labelValues1[]={InstRecordType.Portfolio_Company.toString(),InstRecordType.Portfolio_Company.toString()};
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
		,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.Closed.toString(),Stage.Closed.toString(),String.valueOf(closedScore)};
		
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal14, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					if (dp.getconvertToPortfolioMessage(M6Ins14,10)!=null) {
						log(LogStatus.INFO, "successfully verified convert to portfolio text message", YesNo.No);
					}else {
						sa.assertTrue(false,"could not verify convert to portfolio text message before next");
						log(LogStatus.SKIP,"could not verify convert to portfolio text message before next",YesNo.Yes);
					}
					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						String text=dp.getconvertToPortfolioMessageAfterNext(10).getText();

						if (text.contains(dp.convertToPortfolioAfterNext(M6Ins14))) {
							log(LogStatus.INFO,"successfully verified max character inst name on convert to portfolio message congratulations",YesNo.Yes);

						}else {
							sa.assertTrue(false,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins14)+"\nactual: "+text);
							log(LogStatus.SKIP,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins14)+"\nactual: "+text,YesNo.Yes);
						}
						if ( dp.getconvertToPortfolioCrossButton(10)!=null) {
							log(LogStatus.INFO, "successfully verified cross button after convert to portfolio", YesNo.No);
						}else {
							sa.assertTrue(false,"could not verify convert to portfolio as cross button not clicked");
							log(LogStatus.SKIP,"could not verify convert to portfolio as cross button not clicked",YesNo.Yes);
						}
						if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on finish button", YesNo.No);
						}else {
							sa.assertTrue(false,"could not click on finish button");
							log(LogStatus.SKIP,"could not click on finish button",YesNo.Yes);
						}
						ThreadSleep(3000);
						refresh(driver);
						ThreadSleep(3000);
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i],false)) {
							log(LogStatus.SKIP,"successfully verified "+labelName[i],YesNo.No);

						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}

					}if(dp.checkValueOfPathComponentValueOfStage(Stage.Closed.toString(), 10)) {
						log(LogStatus.INFO,"stage on path component successfully verified",YesNo.Yes);
							
					}else {
						sa.assertTrue(false,"stage on path component could not be verified");
						log(LogStatus.SKIP,"stage on path component could not be verified",YesNo.Yes);
					}
					}else {
						sa.assertTrue(false,"next button is not clickable");
						log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"convert to portfolio button is not clickable");
					log(LogStatus.SKIP,"convert to portfolio button is not clickable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,M6Deal14+" deal is not clickable");
				log(LogStatus.SKIP,M6Deal14+" deal is not clickable",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Ins14, 10)) {
				for(int i = 0;i<labelName1.length;i++) {
				if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
					log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);

				}else {
					sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
					log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
				}
				}
				ThreadSleep(3000);
				refresh(driver);
				ThreadSleep(3000);
				String text=ip.getLastModifiedTime(projectName,10).getText().trim();
				text=text.split(",")[1];
				if(ip.verifyBeforeTimeOrNot(projectName, text)) {
					log(LogStatus.INFO,"successfully verified last modified time",YesNo.Yes);
					
				}else {
					sa.assertTrue(false,"could not verify last modified time");
					log(LogStatus.SKIP,"could not verify last modified time",YesNo.Yes);
				}
			}
			else {
				sa.assertTrue(false,M6Ins14+" is not found on inst page");
				log(LogStatus.SKIP,M6Ins14+" is not found on inst page",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"inst tab is not clickable");
			log(LogStatus.SKIP,"inst tab is not clickable",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M6tc020_CheckConvertToPortfolioFuncWhenRecordTypeIndividualInvestor(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		int total=1;
		double avgDealQualityScore=closedScore/total;
		String labelName1[]={excelLabel.Record_Type.toString(),excelLabel.Status.toString()
		};
		String labelValues1[]={InstRecordType.Portfolio_Company.toString(),InstRecordType.Portfolio_Company.toString()};
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
		,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.Closed.toString(),Stage.Closed.toString(),String.valueOf(closedScore)};
		
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal15, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					if (dp.getconvertToPortfolioMessage(M6Ins15,10)!=null) {
						log(LogStatus.INFO, "successfully verified convert to portfolio text message", YesNo.No);
					}else {
						sa.assertTrue(false,"could not verify convert to portfolio text message before next");
						log(LogStatus.SKIP,"could not verify convert to portfolio text message before next",YesNo.Yes);
					}
					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						String text=dp.getconvertToPortfolioMessageAfterNext(10).getText();

						if (text.contains(dp.convertToPortfolioAfterNext(M6Ins15))) {
							log(LogStatus.INFO,"successfully verified max character inst name on convert to portfolio message congratulations",YesNo.Yes);

						}else {
							sa.assertTrue(false,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins15)+"\nactual: "+text);
							log(LogStatus.SKIP,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins15)+"\nactual: "+text,YesNo.Yes);
						}
						if ( dp.getconvertToPortfolioCrossButton(10)!=null) {
							log(LogStatus.INFO, "successfully verified cross button after convert to portfolio", YesNo.No);
						}else {
							sa.assertTrue(false,"could not verify convert to portfolio as cross button not clicked");
							log(LogStatus.SKIP,"could not verify convert to portfolio as cross button not clicked",YesNo.Yes);
						}
						if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on finish button", YesNo.No);
						}else {
							sa.assertTrue(false,"could not click on finish button");
							log(LogStatus.SKIP,"could not click on finish button",YesNo.Yes);
						}
						ThreadSleep(3000);
						refresh(driver);
						ThreadSleep(3000);
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i],false)) {
							log(LogStatus.SKIP,"successfully verified "+labelName[i],YesNo.No);

						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}

					}
					
					  if(dp.checkValueOfPathComponentValueOfStage(Stage.Closed.toString(), 10)) {
					  log(LogStatus.INFO,"stage on path component successfully verified",YesNo.Yes)
					  ;
					  
					  }else { sa.assertTrue(false,"stage on path component could not be verified");
					  log(LogStatus.SKIP,"stage on path component could not be verified",YesNo.Yes)
					  ; }
					 
					}else {
						sa.assertTrue(false,"next button is not clickable");
						log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"convert to portfolio button is not clickable");
					log(LogStatus.SKIP,"convert to portfolio button is not clickable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,M6Deal15+" deal is not clickable");
				log(LogStatus.SKIP,M6Deal15+" deal is not clickable",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Ins15, 10)) {
				for(int i = 0;i<labelName1.length;i++) {
				if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
					log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);

				}else {
					sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
					log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
				}
				}
				ThreadSleep(3000);
				refresh(driver);
				ThreadSleep(3000);
				String text=ip.getLastModifiedTime(projectName,10).getText().trim();
				text=text.split(",")[1];
				if(ip.verifyBeforeTimeOrNot(projectName, text)) {
					log(LogStatus.INFO,"successfully verified last modified time",YesNo.Yes);
					
				}else {
					sa.assertTrue(false,"could not verify last modified time");
					log(LogStatus.SKIP,"could not verify last modified time",YesNo.Yes);
				}
			}
			else {
				sa.assertTrue(false,M6Ins15+" is not found on inst page");
				log(LogStatus.SKIP,M6Ins15+" is not found on inst page",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"inst tab is not clickable");
			log(LogStatus.SKIP,"inst tab is not clickable",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	
	@Parameters({ "projectName"})
	@Test
	public void M6tc021_CheckConvertToPortfolioFuncWhenRecordTypeIntermediary(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		int total=1;
		double avgDealQualityScore=closedScore/total;
		String labelName1[]={excelLabel.Record_Type.toString(),excelLabel.Status.toString()
		};
		String labelValues1[]={InstRecordType.Portfolio_Company.toString(),InstRecordType.Portfolio_Company.toString()};
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
		,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.Closed.toString(),Stage.Closed.toString(),String.valueOf(closedScore)};
		
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal16, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					if (dp.getconvertToPortfolioMessage(M6Ins16,10)!=null) {
						log(LogStatus.INFO, "successfully verified convert to portfolio text message", YesNo.No);
					}else {
						sa.assertTrue(false,"could not verify convert to portfolio text message before next");
						log(LogStatus.SKIP,"could not verify convert to portfolio text message before next",YesNo.Yes);
					}
					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						String text=dp.getconvertToPortfolioMessageAfterNext(10).getText();

						if (text.contains(dp.convertToPortfolioAfterNext(M6Ins16))) {
							log(LogStatus.INFO,"successfully verified max character inst name on convert to portfolio message congratulations",YesNo.Yes);

						}else {
							sa.assertTrue(false,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins16)+"\nactual: "+text);
							log(LogStatus.SKIP,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins16)+"\nactual: "+text,YesNo.Yes);
						}
						if ( dp.getconvertToPortfolioCrossButton(10)!=null) {
							log(LogStatus.INFO, "successfully verified cross button after convert to portfolio", YesNo.No);
						}else {
							sa.assertTrue(false,"could not verify convert to portfolio as cross button not clicked");
							log(LogStatus.SKIP,"could not verify convert to portfolio as cross button not clicked",YesNo.Yes);
						}
						if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on finish button", YesNo.No);
						}else {
							sa.assertTrue(false,"could not click on finish button");
							log(LogStatus.SKIP,"could not click on finish button",YesNo.Yes);
						}
						ThreadSleep(3000);
						refresh(driver);
						ThreadSleep(3000);
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i],false)) {
							log(LogStatus.SKIP,"successfully verified "+labelName[i],YesNo.No);

						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}

					}
					
					  if(dp.checkValueOfPathComponentValueOfStage(Stage.Closed.toString(), 10)) {
					  log(LogStatus.INFO,"stage on path component successfully verified",YesNo.Yes)
					  ;
					  
					  }else { sa.assertTrue(false,"stage on path component could not be verified");
					  log(LogStatus.SKIP,"stage on path component could not be verified",YesNo.Yes)
					  ; }
					 
					}else {
						sa.assertTrue(false,"next button is not clickable");
						log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"convert to portfolio button is not clickable");
					log(LogStatus.SKIP,"convert to portfolio button is not clickable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,M6Deal16+" deal is not clickable");
				log(LogStatus.SKIP,M6Deal16+" deal is not clickable",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Ins16, 10)) {
				for(int i = 0;i<labelName1.length;i++) {
				if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
					log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);

				}else {
					sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
					log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
				}
				}
				ThreadSleep(3000);
				refresh(driver);
				ThreadSleep(3000);
				String text=ip.getLastModifiedTime(projectName,10).getText().trim();
				text=text.split(",")[1];
				if(ip.verifyBeforeTimeOrNot(projectName, text)) {
					log(LogStatus.INFO,"successfully verified last modified time",YesNo.Yes);
					
				}else {
					sa.assertTrue(false,"could not verify last modified time");
					log(LogStatus.SKIP,"could not verify last modified time",YesNo.Yes);
				}
			}
			else {
				sa.assertTrue(false,M6Ins16+" is not found on inst page");
				log(LogStatus.SKIP,M6Ins16+" is not found on inst page",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"inst tab is not clickable");
			log(LogStatus.SKIP,"inst tab is not clickable",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	@Parameters({ "projectName"})
	@Test
	public void M6tc022_CheckConvertToPortfolioFuncWhenRecordTypeLP(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		int total=1;
		double avgDealQualityScore=closedScore/total;
		String labelName1[]={excelLabel.Record_Type.toString(),excelLabel.Status.toString()
		};
		String labelValues1[]={InstRecordType.Portfolio_Company.toString(),InstRecordType.Portfolio_Company.toString()};
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
		,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.Closed.toString(),Stage.Closed.toString(),String.valueOf(closedScore)};
		
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal17, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					if (dp.getconvertToPortfolioMessage(M6Ins17,10)!=null) {
						log(LogStatus.INFO, "successfully verified convert to portfolio text message", YesNo.No);
					}else {
						sa.assertTrue(false,"could not verify convert to portfolio text message before next");
						log(LogStatus.SKIP,"could not verify convert to portfolio text message before next",YesNo.Yes);
					}
					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						String text=dp.getconvertToPortfolioMessageAfterNext(10).getText();

						if (text.contains(dp.convertToPortfolioAfterNext(M6Ins17))) {
							log(LogStatus.INFO,"successfully verified max character inst name on convert to portfolio message congratulations",YesNo.Yes);

						}else {
							sa.assertTrue(false,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins17)+"\nactual: "+text);
							log(LogStatus.SKIP,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins17)+"\nactual: "+text,YesNo.Yes);
						}
						if ( dp.getconvertToPortfolioCrossButton(10)!=null) {
							log(LogStatus.INFO, "successfully verified cross button after convert to portfolio", YesNo.No);
						}else {
							sa.assertTrue(false,"could not verify convert to portfolio as cross button not clicked");
							log(LogStatus.SKIP,"could not verify convert to portfolio as cross button not clicked",YesNo.Yes);
						}
						if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on finish button", YesNo.No);
						}else {
							sa.assertTrue(false,"could not click on finish button");
							log(LogStatus.SKIP,"could not click on finish button",YesNo.Yes);
						}
						ThreadSleep(3000);
						refresh(driver);
						ThreadSleep(3000);
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i],false)) {
							log(LogStatus.SKIP,"successfully verified "+labelName[i],YesNo.No);

						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}

					}
					
					  if(dp.checkValueOfPathComponentValueOfStage(Stage.Closed.toString(), 10)) {
					  log(LogStatus.INFO,"stage on path component successfully verified",YesNo.Yes)
					  ;
					  
					  }else { sa.assertTrue(false,"stage on path component could not be verified");
					  log(LogStatus.SKIP,"stage on path component could not be verified",YesNo.Yes)
					  ; }
					 
					}else {
						sa.assertTrue(false,"next button is not clickable");
						log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"convert to portfolio button is not clickable");
					log(LogStatus.SKIP,"convert to portfolio button is not clickable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,M6Deal17+" deal is not clickable");
				log(LogStatus.SKIP,M6Deal17+" deal is not clickable",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Ins17, 10)) {
				for(int i = 0;i<labelName1.length;i++) {
				if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
					log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);

				}else {
					sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
					log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
				}
				}
				ThreadSleep(3000);
				refresh(driver);
				ThreadSleep(3000);
				String text=ip.getLastModifiedTime(projectName,10).getText().trim();
				text=text.split(",")[1];
				if(ip.verifyBeforeTimeOrNot(projectName, text)) {
					log(LogStatus.INFO,"successfully verified last modified time",YesNo.Yes);
					
				}else {
					sa.assertTrue(false,"could not verify last modified time");
					log(LogStatus.SKIP,"could not verify last modified time",YesNo.Yes);
				}
			}
			else {
				sa.assertTrue(false,M6Ins17+" is not found on inst page");
				log(LogStatus.SKIP,M6Ins17+" is not found on inst page",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"inst tab is not clickable");
			log(LogStatus.SKIP,"inst tab is not clickable",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	@Parameters({ "projectName"})
	@Test
	public void M6tc023_CheckConvertToPortfolioFuncWhenRecordTypeLender(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		int total=1;
		double avgDealQualityScore=closedScore/total;
		String labelName1[]={excelLabel.Record_Type.toString(),excelLabel.Status.toString()
		};
		String labelValues1[]={InstRecordType.Portfolio_Company.toString(),InstRecordType.Portfolio_Company.toString()};
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
		,excelLabel.Deal_Quality_Score.toString()};
		String labelValues[]={Stage.Closed.toString(),Stage.Closed.toString(),String.valueOf(closedScore)};
		
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal18, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					if (dp.getconvertToPortfolioMessage(M6Ins18,10)!=null) {
						log(LogStatus.INFO, "successfully verified convert to portfolio text message", YesNo.No);
					}else {
						sa.assertTrue(false,"could not verify convert to portfolio text message before next");
						log(LogStatus.SKIP,"could not verify convert to portfolio text message before next",YesNo.Yes);
					}
					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						String text=dp.getconvertToPortfolioMessageAfterNext(10).getText();

						if (text.contains(dp.convertToPortfolioAfterNext(M6Ins18))) {
							log(LogStatus.INFO,"successfully verified max character inst name on convert to portfolio message congratulations",YesNo.Yes);

						}else {
							sa.assertTrue(false,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins18)+"\nactual: "+text);
							log(LogStatus.SKIP,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins18)+"\nactual: "+text,YesNo.Yes);
						}
						if ( dp.getconvertToPortfolioCrossButton(10)!=null) {
							log(LogStatus.INFO, "successfully verified cross button after convert to portfolio", YesNo.No);
						}else {
							sa.assertTrue(false,"could not verify convert to portfolio as cross button not clicked");
							log(LogStatus.SKIP,"could not verify convert to portfolio as cross button not clicked",YesNo.Yes);
						}
						if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on finish button", YesNo.No);
						}else {
							sa.assertTrue(false,"could not click on finish button");
							log(LogStatus.SKIP,"could not click on finish button",YesNo.Yes);
						}
						ThreadSleep(3000);
						refresh(driver);
						ThreadSleep(3000);
					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i],false)) {
							log(LogStatus.SKIP,"successfully verified "+labelName[i],YesNo.No);

						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}

					}
					
					  if(dp.checkValueOfPathComponentValueOfStage(Stage.Closed.toString(), 10)) {
					  log(LogStatus.INFO,"stage on path component successfully verified",YesNo.Yes)
					  ;
					  
					  }else { sa.assertTrue(false,"stage on path component could not be verified");
					  log(LogStatus.SKIP,"stage on path component could not be verified",YesNo.Yes)
					  ; }
					 
					}else {
						sa.assertTrue(false,"next button is not clickable");
						log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"convert to portfolio button is not clickable");
					log(LogStatus.SKIP,"convert to portfolio button is not clickable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,M6Deal18+" deal is not clickable");
				log(LogStatus.SKIP,M6Deal18+" deal is not clickable",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}
		if (ip.clickOnTab(projectName, TabName.Object1Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Ins18, 10)) {
				for(int i = 0;i<labelName1.length;i++) {
				if (ip.fieldValueVerificationOnInstitutionPage(environment,mode,TabName.Object1Tab, labelName1[i],labelValues1[i])) {
					log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);

				}else {
					sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
					log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
				}
				}
				ThreadSleep(3000);
				refresh(driver);
				ThreadSleep(3000);
				String text=ip.getLastModifiedTime(projectName,10).getText().trim();
				text=text.split(",")[1];
				if(ip.verifyBeforeTimeOrNot(projectName, text)) {
					log(LogStatus.INFO,"successfully verified last modified time",YesNo.Yes);
					
				}else {
					sa.assertTrue(false,"could not verify last modified time");
					log(LogStatus.SKIP,"could not verify last modified time",YesNo.Yes);
				}
			}
			else {
				sa.assertTrue(false,M6Ins18+" is not found on inst page");
				log(LogStatus.SKIP,M6Ins18+" is not found on inst page",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"inst tab is not clickable");
			log(LogStatus.SKIP,"inst tab is not clickable",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M6tc024_RenameConvertToPortfolioToConvert_Action(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String updateLabel="Convert";
		WebElement ele=null;
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {


				if(setup.searchStandardOrCustomObject(environment, mode, object.Deal)) {
					if(setup.clickOnObjectFeature(environment, mode, object.Deal, ObjectFeatureName.ButtonLinksAndActions)) {
						if (sendKeys(driver, setup.getsearchTextboxFieldsAndRelationships(10), updateLabel, "stage", action.BOOLEAN)) {
							ThreadSleep(4000);
							if (setup.clickOnAlreadyCreatedLayout(PageLabel.Convert_to_Portfolio.toString().replace("_", " "))) {
								switchToDefaultContent(driver);
								switchToFrame(driver, 10, setup.getFrame(PageName.ConvertToPortfolioFrame, 10));
								ele=setup.getEditButton(environment,Mode.Classic.toString(), 10);
								if (click(driver, ele, "edit", action.BOOLEAN)) {
									ThreadSleep(4000);
									switchToDefaultContent(driver);
									switchToFrame(driver, 10, setup.getFrame(PageName.ConvertToPortfolioFrame, 10));
									ThreadSleep(4000);
									if (sendKeys(driver, setup.getRecordTypeLabel(projectName, "Label", 10), updateLabel, "Label", action.BOOLEAN)){
										if (clickUsingJavaScript(driver, setup.getSaveButtonInCustomFields(10), "save", action.SCROLLANDBOOLEAN)) {
											ThreadSleep(3000);	
											log(LogStatus.INFO, "save button is clicked",YesNo.No);

										}else {
											log(LogStatus.FAIL, "save button not clickable",YesNo.Yes);
											sa.assertTrue(false, "save button not clickable");
										}
									}else {
										log(LogStatus.FAIL, "label textbox is not visible",YesNo.Yes);
										sa.assertTrue(false, "label textbox is not visible");
									}
								}else {
									log(LogStatus.FAIL, "edit button not clickable",YesNo.Yes);
									sa.assertTrue(false, "edit button not clickable");
								}
							}else {
								log(LogStatus.FAIL, PageLabel.Convert_to_Portfolio+" not found",YesNo.Yes);
								sa.assertTrue(false, PageLabel.Convert_to_Portfolio+" not found");
							}
						}else {
							log(LogStatus.FAIL, "search textbox not found",YesNo.Yes);
							sa.assertTrue(false, "search textbox not found");
						}
					}else {
						log(LogStatus.FAIL, "button links actions feature not clickable",YesNo.Yes);
						sa.assertTrue(false, "button links actions feature not clickable");
					}
				}else {
					log(LogStatus.FAIL, "deal object is not clickable",YesNo.Yes);
					sa.assertTrue(false, "deal object is not clickable");
				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch",YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			log(LogStatus.FAIL, "could not click on setup link",YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}

		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M6tc024_RenameConvertToPortfolioToConvert_Impact(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
		};
		String labelValues[]={Stage.Closed.toString(),Stage.Closed.toString()};

		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal2, 10)) {
				if (click(driver, dp.getconvertButton(10),"convert to portfolio button", action.BOOLEAN)) {
					
					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						ThreadSleep(4000);if (dp.getconvertToPortfolioMessageRepeat("Convert",10)!=null) {
							String text=dp.getconvertToPortfolioMessageRepeat("Convert",10).getText();
							if (text.contains(dp.convertToPortfolioRepeat(M6Ins3))) {
								log(LogStatus.INFO,"successfully verified already portfolio message",YesNo.Yes);
								
							}else {
								sa.assertTrue(false,"could not verify already portfolio message\nExpected: "+text+"\nActual: "+dp.convertToPortfolioRepeat(M6Ins2));
								log(LogStatus.SKIP,"could not verify already portfolio message\\nExpected: "+text+"\nActual: "+dp.convertToPortfolioRepeat(M6Ins2),YesNo.Yes);
							}
						}else {
							sa.assertTrue(false,"not visible already portfolio message");
							log(LogStatus.SKIP,"not visible already portfolio message",YesNo.Yes);
						}
						if ( dp.getconvertToPortfolioCrossButton("Convert",10)!=null) {
							log(LogStatus.INFO,"successfully verified presence of cross icon",YesNo.Yes);
							
						}else {
							sa.assertTrue(false,"could not verify cross icon presence");
							log(LogStatus.SKIP,"could not verify cross icon presence",YesNo.Yes);
						}
						if (click(driver, dp.getfinishButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "successfully verified finish button after convert to portfolio", YesNo.No);
						}else {
							sa.assertTrue(false,"could not verify convert to portfolio as finish button not clicked");
							log(LogStatus.SKIP,"could not verify convert to portfolio as finish button not clicked",YesNo.Yes);
						}
					}else {
						sa.assertTrue(false,"next button is not clickable");
						log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"convertToPortfolio button is not clickable");
					log(LogStatus.SKIP,"convertToPortfolio button is not clickable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,M6Deal2+" deal is not found on deal tab");
				log(LogStatus.SKIP,M6Deal2+" deal is not found on deal tab",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}
		
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal10, 10)) {
				if (click(driver, dp.getconvertButton(10),"convert to portfolio button", action.BOOLEAN)) {
					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						String text=dp.getconvertToPortfolioMessageAfterNext("Convert",10).getText();

						if (text.contains(dp.convertToPortfolioAfterNext(M6Ins10))) {
							log(LogStatus.INFO,"successfully verified inst name on convert to portfolio message congratulations",YesNo.Yes);

						}else {
							sa.assertTrue(false,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins10)+"\nactual: "+text);
							log(LogStatus.SKIP,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins10)+"\nactual: "+text,YesNo.Yes);
						}
						if (click(driver, dp.getconvertToPortfolioCrossButton("Convert",10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on cross button", YesNo.No);
						}else {
							sa.assertTrue(false,"could not click on cross button");
							log(LogStatus.SKIP,"could not click on cross button",YesNo.Yes);
						}
						
					ThreadSleep(3000);
					refresh(driver);
					ThreadSleep(3000);
				for (int i =0;i<labelName.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i],false)) {
						log(LogStatus.SKIP,"successfully verified "+labelName[i],YesNo.No);

					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
					}

				}
				if(dp.checkValueOfPathComponentValueOfStage(Stage.Closed.toString(), 10)) {
					log(LogStatus.INFO,"stage on path component successfully verified",YesNo.Yes);
						
				}else {
					sa.assertTrue(false,"stage on path component could not be verified");
					log(LogStatus.SKIP,"stage on path component could not be verified",YesNo.Yes);
				}
					}else {
						sa.assertTrue(false,"next button is not clickable");
						log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"convert to portfolio button is not clickable");
					log(LogStatus.SKIP,"convert to portfolio button is not clickable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,M6Deal20+" deal is not clickable");
				log(LogStatus.SKIP,M6Deal20+" deal is not clickable",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M6tc025_1_RenameConvertAsConvertToPortfolio(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String oldLabel="Convert";
		String recTypeArray[]={"Pitch","Deal"};WebElement ele=null;
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {


				if(setup.searchStandardOrCustomObject(environment, mode, object.Deal)) {
					if(setup.clickOnObjectFeature(environment, mode, object.Deal, ObjectFeatureName.ButtonLinksAndActions)) {
						if (sendKeys(driver, setup.getsearchTextboxFieldsAndRelationships(10), oldLabel, "search", action.BOOLEAN)) {
							if (setup.clickOnAlreadyCreatedLayout(oldLabel)) {
							//if (setup.clickOnAlreadyCreatedLayout(PageLabel.Convert_to_Portfolio.toString().replace("_", " "))) {
								switchToDefaultContent(driver);
								ThreadSleep(3000);
								switchToFrame(driver, 20,setup. getSetUpPageIframe(60));
								ThreadSleep(3000);
								ele=setup.getEditButton(environment,Mode.Classic.toString(), 10);
								if (click(driver, ele, "edit", action.BOOLEAN)) {
									switchToDefaultContent(driver);
									ThreadSleep(3000);
									switchToFrame(driver, 20,setup. getSetUpPageIframe(60));
									if (sendKeys(driver, setup.getRecordTypeLabel(projectName, "Label", 10), PageLabel.Convert_to_Portfolio.toString().replace("_", " "), "label", action.BOOLEAN)){
										if (clickUsingJavaScript(driver, setup.getSaveButtonInCustomFields(10), "save", action.SCROLLANDBOOLEAN)) {
											ThreadSleep(3000);	
											log(LogStatus.INFO, "save button is clicked",YesNo.No);

										}else {
											log(LogStatus.FAIL, "save button not clickable",YesNo.Yes);
											sa.assertTrue(false, "save button not clickable");
										}
									}else {
										log(LogStatus.FAIL, "label textbox is not visible",YesNo.Yes);
										sa.assertTrue(false, "label textbox is not visible");
									}
								}else {
									log(LogStatus.FAIL, "edit button not clickable",YesNo.Yes);
									sa.assertTrue(false, "edit button not clickable");
								}
							}else {
								log(LogStatus.FAIL, oldLabel+" not found",YesNo.Yes);
								sa.assertTrue(false, oldLabel+" not found");
							}
						}else {
							log(LogStatus.FAIL, "search textbox not found",YesNo.Yes);
							sa.assertTrue(false, "search textbox not found");
						}
					}else {
						log(LogStatus.FAIL, "button links actions feature not clickable",YesNo.Yes);
						sa.assertTrue(false, "button links actions feature not clickable");
					}
				}else {
					log(LogStatus.FAIL, "deal object is not clickable",YesNo.Yes);
					sa.assertTrue(false, "deal object is not clickable");
				}
				String[][] pitch = {{recordTypeLabel.Record_Type_Label.toString(),recTypeArray[0]},
						{recordTypeLabel.Active.toString(),""}};
				String[][] deal = {{recordTypeLabel.Record_Type_Label.toString(),recTypeArray[1]},
						{recordTypeLabel.Active.toString(),""}};
				String [] profileforSelection= {crmUserProfile,AdminUserProfile};
				if(setup.clickOnObjectFeature("", Mode.Lightning.toString(),object.Deal, ObjectFeatureName.recordTypes)) {
					ThreadSleep(5000);
					if (setup.createRecordTypeForObject(projectName, pitch, false, profileforSelection,false, null, 10)) {
						log(LogStatus.INFO, "successfully created record type pitch",YesNo.Yes);
						
					}else {
						log(LogStatus.FAIL, "could not create record type",YesNo.Yes);
						sa.assertTrue(false, "could not create record type");
					}
				}else {
					log(LogStatus.FAIL, "could not find record type link",YesNo.Yes);
					sa.assertTrue(false, "could not find record type link");
				}
				if(setup.clickOnObjectFeature("", Mode.Lightning.toString(),object.Deal, ObjectFeatureName.recordTypes)) {
					ThreadSleep(5000);
					if (setup.createRecordTypeForObject(projectName, deal, false, profileforSelection,false, null, 10)) {
						log(LogStatus.INFO, "successfully created record type deal",YesNo.Yes);
						
					}else {
						log(LogStatus.FAIL, "could not create record type",YesNo.Yes);
						sa.assertTrue(false, "could not create record type");
					}
				}else {
					log(LogStatus.FAIL, "could not find record type link",YesNo.Yes);
					sa.assertTrue(false, "could not find record type link");
				}
				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "could not find new window to switch",YesNo.Yes);
				sa.assertTrue(false, "could not find new window to switch");
			}
		}else {
			log(LogStatus.FAIL, "could not click on setup link",YesNo.Yes);
			sa.assertTrue(false, "could not click on setup link");
		}

		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M6tc025_2_RenameConvertAsConvertToPortfolio(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String rt[]={"Pitch","Deal"};
		String layouts[]={"Pitch Layout","Deal Layout"};
		String existing="Pipeline Layout";
		String userProfile="PE Standard User";
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {


				if(setup.searchStandardOrCustomObject(environment, mode, object.Deal)) {
					if(setup.clickOnObjectFeature(environment, mode, object.Deal, ObjectFeatureName.pageLayouts)) {
					if (setup.createPageLayout(projectName, new String[][] {{PageLabel.Page_Layout_Name.toString(),layouts[0]}}, existing, null, null, 10)){
						
					}else {
						log(LogStatus.FAIL, "could not create new page layout",YesNo.Yes);
						sa.assertTrue(false, "could not create new page layout");
					}
					}else {
						log(LogStatus.FAIL, "object feature page layout is not clickable",YesNo.Yes);
						sa.assertTrue(false, "object feature page layout is not clickable");
					}
					if(setup.clickOnObjectFeature(environment, mode, object.Deal, ObjectFeatureName.pageLayouts)) {
						if (setup.createPageLayout(projectName, new String[][] {{PageLabel.Page_Layout_Name.toString(),layouts[1]}}, existing, null, null, 10)){
							
						}else {
							log(LogStatus.FAIL, "could not create new page layout",YesNo.Yes);
							sa.assertTrue(false, "could not create new page layout");
						}
						}else {
							log(LogStatus.FAIL, "object feature page layout is not clickable",YesNo.Yes);
							sa.assertTrue(false, "object feature page layout is not clickable");
						}
					if(setup.clickOnObjectFeature(environment, mode, object.Deal, ObjectFeatureName.pageLayouts)) {
						if (click(driver, setup.getpageLayoutAssignment(projectName, 10), "layout assignment", action.BOOLEAN)) {
							switchToDefaultContent(driver);
							ThreadSleep(3000);
							switchToFrame(driver, 20, setup.getSetUpPageIframe(60));
							
							if (click(driver, setup.geteditAssignment(projectName, 10), "edit assignment", action.BOOLEAN)) {
								switchToDefaultContent(driver);
								ThreadSleep(3000);
								switchToFrame(driver, 20, setup.getSetUpPageIframe(60));
								int i = 0;
								for (String re:rt) {
									int loc=setup.getCountOfColumnRecordType(projectName, rt[i]);
								
								WebElement ele=setup.clickOnRecordTypePageLayout(projectName, userProfile, loc);
								if (click(driver,ele, "layout name for record type", action.BOOLEAN)) {
									ele=setup.getpageLayoutSelector(10);
									ThreadSleep(3000);
									if (selectVisibleTextFromDropDown(driver,ele , "page layout selector", layouts[i])) {
										
									}else {
										log(LogStatus.FAIL, "could not select "+layouts[i]+" on dropdown",YesNo.Yes);
										sa.assertTrue(false, "could not select "+layouts[i]+" on dropdown");
									}
								}else {
									log(LogStatus.FAIL, "layout name is not clickable on webtable",YesNo.Yes);
									sa.assertTrue(false, "layout name is not clickable on webtable");
								}
								i++;}
								
								if (clickUsingJavaScript(driver, setup.getSaveButtonInCustomFields(10), "save", action.SCROLLANDBOOLEAN)) {
									
								}else {
									log(LogStatus.FAIL, "save button is not clickable",YesNo.Yes);
									sa.assertTrue(false, "save button is not clickable");
								}
						}else {
							log(LogStatus.FAIL, "edit button is not clickable",YesNo.Yes);
							sa.assertTrue(false, "edit button is not clickable");
						}
						}else {
							log(LogStatus.FAIL, "page layout assignment button is not clickable",YesNo.Yes);
							sa.assertTrue(false, "page layout assignment button is not clickable");
						}
					}else {
						log(LogStatus.FAIL, "page layout is not found",YesNo.Yes);
						sa.assertTrue(false, "page layout is not found");
					}
				}else {
					log(LogStatus.FAIL, "deal object is not found",YesNo.Yes);
					sa.assertTrue(false, "deal object is not found");
				}
			driver.close();
			driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "deal object is not found",YesNo.Yes);
				sa.assertTrue(false, "deal object is not found");
			}
		}else {
			log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
			sa.assertTrue(false, "setup link is not clickable");
		}
		lp.CRMlogout();
		sa.assertAll();
		
	}
	@Parameters({ "projectName"})
	@Test
	public void M6tc025_3_RenameConvertAsConvertToPortfolio(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String userName="PE Standard User";
		String rt[]={"Pitch","Deal"};
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {

				if (setup.searchStandardOrCustomObject(environment, mode, object.Profiles)) {
					log(LogStatus.INFO, "click on Object : " + object.Profiles, YesNo.No);
					ThreadSleep(2000);
					switchToDefaultContent(driver);
					switchToFrame(driver, 60, setup.getSetUpPageIframe(60));
					boolean flag = false;
					;
					String xpath = "";
					xpath = "//th//a[text()='" + userName + "']";
					WebElement ele = FindElement(driver, xpath, userName, action.SCROLLANDBOOLEAN, 10);
					ele = isDisplayed(driver, ele, "visibility", 10, userName);
					if (click(driver, ele, userName.toString(), action.BOOLEAN)) {
						log(LogStatus.INFO, "able to click on " + userName, YesNo.No);
						ThreadSleep(10000);
						switchToDefaultContent(driver);
						ThreadSleep(5000);
						switchToFrame(driver, 60, setup.getSetUpPageIframe(60));
						xpath = "//*[text()='Deals']/following-sibling::*//*[text()='Edit']";
						ele = FindElement(driver, xpath, "Edit Button", action.SCROLLANDBOOLEAN, 10);
						ele = isDisplayed(driver, ele, "visibility", 10, "Edit Button");
						if (click(driver, ele, "Edit Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "able to click on edit button for record type settiing", YesNo.No);
							switchToDefaultContent(driver);
							ThreadSleep(5000);
							switchToFrame(driver, 60, setup.getSetUpPageIframe(60));
							ThreadSleep(2000);
							if (selectVisibleTextFromDropDown(driver, setup.getavailableRecordType(60),
									"Available Tab List", rt[0])) {
								appLog.info(rt + " is selected successfully in available tabs");
								if (click(driver, setup.getAddBtn(60), "Custom Tab Add Button",
										action.SCROLLANDBOOLEAN)) {
									appLog.error("clicked on add button");
								} else {
									//sa.assertTrue(false,"Not able to click on add button so cannot add custom tabs");
									appLog.error("Not able to click on add button so cannot add custom tabs");
								}
							} else {
								appLog.error(rt + " record type is not Available list Tab.");
								sa.assertTrue(false,rt + " record type is not Available list Tab.");
							}
							if (selectVisibleTextFromDropDown(driver, setup.getavailableRecordType(60), "Available Tab List",
									rt[1])) {
								appLog.info(rt + " is selected successfully in available tabs");
								if (click(driver, setup.getAddBtn(60), "Custom Tab Add Button",
										action.SCROLLANDBOOLEAN)) {
									appLog.error("clicked on add button");
								} else {
									//sa.assertTrue(false,"Not able to click on add button so cannot add custom tabs");
									appLog.error("Not able to click on add button so cannot add custom tabs");
								}
							} else {
								appLog.error(rt + " record type is not Available list Tab.");
								sa.assertTrue(false,rt + " record type is not Available list Tab.");
							}
							if (selectVisibleTextFromDropDown(driver, setup.getSelectedRecordType(60), "selected Tab List",
									rt[0])) {
								log(LogStatus.INFO, "successfully verified "+rt[0], YesNo.No);

							}else {
								log(LogStatus.ERROR, "not able to verify "+rt[0]+" in selected record type", YesNo.Yes);
								sa.assertTrue(false,"not able to verify "+rt[0]+" in selected record type");

							} if (selectVisibleTextFromDropDown(driver, setup.getSelectedRecordType(60), "selected Tab List",
									rt[1])) {
								log(LogStatus.INFO, "successfully verified "+rt[1], YesNo.No);

							}else {
								log(LogStatus.ERROR, "not able to verify "+rt[1]+" in selected record type", YesNo.Yes);
								sa.assertTrue(false,"not able to verify "+rt[1]+" in selected record type");

							} 
							if (click(driver, setup.getCreateUserSaveBtn_Lighting(30), "Save Button",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "clicked on save button for record type settiing", YesNo.No);
								ThreadSleep(2000);
							} else {
								log(LogStatus.ERROR, "not able to click on save button for record type settiing", YesNo.Yes);
								sa.assertTrue(false,"not able to click on save button for record type settiing");

							} }else {
								log(LogStatus.ERROR, "not able to click on edit button for record type settiing", YesNo.Yes);
								sa.assertTrue(false,"not able to click on edit button for record type settiing");

							}
					}else {
						log(LogStatus.ERROR, userName+" profile is not clickable", YesNo.Yes);
						sa.assertTrue(false,userName+" profile is not clickable");
					}
				} else {
					log(LogStatus.ERROR, "profiles tab is not clickable", YesNo.Yes);
					sa.assertTrue(false,"profiles tab is not clickable");
				}

				driver.close();
				driver.switchTo().window(parentID);
			}else {
				log(LogStatus.FAIL, "deal object is not found",YesNo.Yes);
				sa.assertTrue(false, "deal object is not found");
			}
		}else {
			log(LogStatus.FAIL, "setup link is not clickable",YesNo.Yes);
			sa.assertTrue(false, "setup link is not clickable");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M6tc025_4_RenameConvertAsConvertToPortfolio(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String value="",type="";
		for (int i = 21;i<23;i++) {
			if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
				log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	
				
				value=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M6INS"+i, excelLabel.Institutions_Name);
				type=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M6INS"+i, excelLabel.Record_Type);
					if (ip.createEntityOrAccount(projectName, mode, value,type, null, null, 20)) {
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
		String pipe,company,stage,rt;
		for(int i = 21;i<23;i++) {
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object4Tab,YesNo.No);	
			pipe= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal"+i, excelLabel.Deal_Name);
			company= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal"+i, excelLabel.Company_Name);
			stage= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal"+i, excelLabel.Stage);
			rt= ExcelUtils.readData(phase1DataSheetFilePath,"Deal",excelLabel.Variable_Name, "M6Deal"+i, excelLabel.Record_Type);
			refresh(driver);
			ThreadSleep(3000);
			if (fp.createDeal(projectName,rt,pipe, company, stage,null, 15)) {
				log(LogStatus.INFO,"Created Deal : "+pipe,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Deal  : "+pipe);
				log(LogStatus.SKIP,"Not Able to Create Deal  : "+pipe,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"not able to click on deal tab");
			log(LogStatus.SKIP,"not able to click on deal tab",YesNo.Yes);
		}
		}
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString()
		};
		String labelValues[]={Stage.Closed.toString(),Stage.Closed.toString()};
		String deals[]={M6Deal21,M6Deal22};
		String ins[]={M6Ins21,M6Ins22};
		for(int j = 0;j<2;j++) {
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object4Tab,YesNo.No);	
			if (ip.clickOnAlreadyCreatedItem(projectName, deals[j], 10)) {
				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						String text=dp.getconvertToPortfolioMessageAfterNext(10).getText();

						if (text.contains(dp.convertToPortfolioAfterNext(ins[j]))) {
							log(LogStatus.INFO,"successfully verified inst name on convert to portfolio message congratulations",YesNo.Yes);

						}else {
							sa.assertTrue(false,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(ins[j])+"\nactual: "+text);
							log(LogStatus.SKIP,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(ins[j])+"\nactual: "+text,YesNo.Yes);
						}
						if (click(driver, dp.getconvertToPortfolioCrossButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on cross button", YesNo.No);
						}else {
							sa.assertTrue(false,"could not click on cross button");
							log(LogStatus.SKIP,"could not click on cross button",YesNo.Yes);
						}
						
					ThreadSleep(3000);
					refresh(driver);
					ThreadSleep(3000);
				for (int i =0;i<labelName.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i],false)) {
						log(LogStatus.SKIP,"successfully verified "+labelName[i],YesNo.No);

					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
					}

				}
				}else {
						sa.assertTrue(false,"next button is not clickable");
						log(LogStatus.SKIP,"next button is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"convert to portfolio button is not clickable");
					log(LogStatus.SKIP,"convert to portfolio button is not clickable",YesNo.Yes);
				}
			}else {
				sa.assertTrue(false,deals[j]+" deal is not clickable");
				log(LogStatus.SKIP,deals[j]+" deal is not clickable",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}
		}
		lp.CRMlogout();
		sa.assertAll();
		
	}

	@Parameters({ "projectName"})
	@Test
	public void M6tc026_VerifyPathComponent(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString(),excelLabel.Last_Stage_Change_Date.toString()
		};
		String labelValues[]={Stage.LOI.toString(),Stage.LOI.toString(),todaysDate};
		String labelName1[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString(),excelLabel.Last_Stage_Change_Date.toString()
		};
		String labelValues1[]={Stage.Closed.toString(),Stage.Closed.toString(),todaysDate};

		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object4Tab,YesNo.No);	
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal3, 10)) {
				WebElement ele;
				if(dp.checkValueOfPathComponentValueOfStage(Stage.IOI.toString(), 10)) {
					log(LogStatus.INFO,"stage IOI on path component successfully verified",YesNo.Yes);

				}else {
					sa.assertTrue(false,"stage IOI on path component could not be verified");
					log(LogStatus.SKIP,"stage IOI on path component could not be verified",YesNo.Yes);
				}
				ele=dp.getmarkStageAsCompleteButton(10);
				if (click(driver, ele, "mark stage as complete", action.BOOLEAN)) {
					ThreadSleep(3000);
					refresh(driver);
					ThreadSleep(3000);
					if(dp.checkValueOfPathComponentValueOfStage(Stage.LOI.toString(), 10)) {
						log(LogStatus.INFO,"stage DD on path component successfully verified",YesNo.Yes);

					}else {
						sa.assertTrue(false,"stage DD on path component could not be verified");
						log(LogStatus.SKIP,"stage DD on path component could not be verified",YesNo.Yes);
					}

					for (int i =0;i<labelName.length;i++) {
						if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i],false)) {
							log(LogStatus.SKIP,"successfully verified "+labelName[i],YesNo.No);

						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
						}

					}
				}else {
					sa.assertTrue(false,"mark stage as complete btuton is not clickable");
					log(LogStatus.SKIP,"mark stage as complete btuton is not clickable",YesNo.Yes);
				}

				ele=dp.selectPathComponent(projectName, Stage.Closed.toString());
				if (click(driver, ele,"stage closd" , action.BOOLEAN)) {
					ele=dp.getmarkAsCurrentStage(10);
					if (click(driver, ele,"mark as current stage" , action.BOOLEAN)) {
						ThreadSleep(3000);
						refresh(driver);
						ThreadSleep(3000);
						for (int i =0;i<labelName.length;i++) {
							if (fp.FieldValueVerificationOnFundPage(projectName, labelName1[i],labelValues1[i],false)) {
								log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);

							}else {
								sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
								log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
							}

						}
					}else {
						sa.assertTrue(false,"mark as curent stage btuton is not clickable");
						log(LogStatus.SKIP,"mark as curent stage btuton is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"stage closed button is not clickable");
					log(LogStatus.SKIP,"stage closed button is not clickable",YesNo.Yes);
				}
			}
			else {
				sa.assertTrue(false,M6Deal3+" deal is not clickable");
				log(LogStatus.SKIP,M6Deal3+" deal is not clickable",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	//@Test
	public void M6tc026_1_VerifyPathComponent(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString(),excelLabel.Last_Stage_Change_Date.toString()
		};
		String labelValues[]={Stage.Closed.toString(),Stage.Closed.toString(),todaysDate};
		String labelName1[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString(),excelLabel.Last_Stage_Change_Date.toString()
		};
		String labelValues1[]={Stage.Due_Diligence.toString(),Stage.Due_Diligence.toString(),todaysDate};
		if (lp.clickOnTab(projectName, TabName.Object4Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object4Tab,YesNo.No);	
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal3, 10)) {
				WebElement ele;
				if(dp.checkValueOfPathComponentValueOfStage(Stage.Closed.toString(), 10)) {
					log(LogStatus.INFO,"stage Closed on path component successfully verified",YesNo.Yes);

				}else {
					sa.assertTrue(false,"stage Closed on path component could not be verified");
					log(LogStatus.SKIP,"stage Closed on path component could not be verified",YesNo.Yes);
				}
				ele=dp.getmarkStageAsCompleteButton(10);
				if (click(driver, ele, "mark stage as complete", action.BOOLEAN)) {
					ThreadSleep(3000);
					refresh(driver);
					ThreadSleep(3000);
					if(dp.checkValueOfPathComponentValueOfStage(Stage.Closed.toString(), 10)) {
						log(LogStatus.INFO,"stage DD on path component successfully verified",YesNo.Yes);

					}else {
						sa.assertTrue(false,"stage DD on path component could not be verified");
						log(LogStatus.SKIP,"stage DD on path component could not be verified",YesNo.Yes);
					}
  ele=dp.selectPathComponent(projectName, Stage.Due_Diligence.toString());
				if (click(driver, ele,"stage Due_Diligence" , action.BOOLEAN)) {
					ele=dp.getmarkAsCurrentStage(10);
					if (click(driver, ele,"mark as current stage" , action.BOOLEAN)) {
						ThreadSleep(3000);
						refresh(driver);
						ThreadSleep(3000);
						for (int i =0;i<labelName.length;i++) {
							if (fp.FieldValueVerificationOnFundPage(projectName, labelName1[i],labelValues1[i],false)) {
								log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);

							}else {
								sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
								log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
							}

						}
					}else {
						sa.assertTrue(false,"mark as curent stage btuton is not clickable");
						log(LogStatus.SKIP,"mark as curent stage btuton is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"stage closed button is not clickable");
					log(LogStatus.SKIP,"stage closed button is not clickable",YesNo.Yes);
				}
			}
			else {
				sa.assertTrue(false,M6Deal3+" deal is not clickable");
				log(LogStatus.SKIP,M6Deal3+" deal is not clickable",YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"deal tab is not clickable");
			log(LogStatus.SKIP,"deal tab is not clickable",YesNo.Yes);
		}

		lp.CRMlogout();
		sa.assertAll();
	}}
	@Parameters({ "projectName"})
	@Test
	public void M6tc027_LastStageChangedFundriaisingPrecondition(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		FundRaisingPageBusinessLayer frp=new FundRaisingPageBusinessLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String labelName[]={excelLabel.Highest_Stage_Reached.toString(),excelLabel.Stage.toString(),excelLabel.Last_Stage_Change_Date.toString()};
		String value="",type="";
		if (lp.clickOnTab(projectName, TabName.Object1Tab)) {
			log(LogStatus.INFO,"Click on Tab : "+TabName.Object1Tab,YesNo.No);	

			value=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M6LSCFINS1", excelLabel.Institutions_Name);
			type=ExcelUtils.readData(phase1DataSheetFilePath,"Entities",excelLabel.Variable_Name, "M6LSCFINS1", excelLabel.Record_Type);
			if (ip.createEntityOrAccount(projectName, mode, value,type, null, null, 20)) {
				log(LogStatus.INFO,"successfully Created Account/Entity : "+value+" of record type : "+type,YesNo.No);	
			} else {
				sa.assertTrue(false,"Not Able to Create Account/Entity : "+value+" of record type : "+type);
				log(LogStatus.SKIP,"Not Able to Create Account/Entity : "+value+" of record type : "+type,YesNo.Yes);
			}


		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object1Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object1Tab,YesNo.Yes);
		}
		String name="",cat="";
		if (lp.clickOnTab(projectName, TabName.Object3Tab)) {
			name=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "M6LSCFFUND1", excelLabel.Fund_Name);
			type=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "M6LSCFFUND1", excelLabel.Fund_Type);
			cat=ExcelUtils.readData(phase1DataSheetFilePath,"Fund",excelLabel.Variable_Name, "M6LSCFFUND1", excelLabel.Investment_Category);

			if (fp.createFundPE(projectName, name, "", type, cat, null, 10)) {
				log(LogStatus.INFO,"successfully create fund : "+name,YesNo.Yes);

			}else {
				sa.assertTrue(false,"Not Able to create fund : "+name);
				log(LogStatus.SKIP,"Not Able to create fund : "+name,YesNo.Yes);
			}
		}
		else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.Object3Tab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.Object3Tab,YesNo.Yes);
		}
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {

			if(frp.createFundRaising(environment,mode,M6LSCFundraising1,M6LSCFund1,M6LSCIns1, null, null, null, null)){
				appLog.info("fundraising is created : "+M6LSCFundraising1);
			}else {
				appLog.error("Not able to create fundraising: "+M6LSCFundraising1);
				sa.assertTrue(false, "Not able to create fundraising: "+M6LSCFundraising1);
			}
		}else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.FundraisingsTab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.FundraisingsTab,YesNo.Yes);
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	@Parameters({ "projectName"})
	@Test
	public void M6tc028_1_AddFieldsOnFundraisingPage(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		FundRaisingPageBusinessLayer frp=new FundRaisingPageBusinessLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
	
		String parentID=null;
		String mode="Lightning";
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
			List<String> layoutName = new ArrayList<String>();
			layoutName.add("Fundraising Layout");
			HashMap<String, String> sourceANDDestination = new HashMap<String, String>();
			sourceANDDestination.put(excelLabel.Last_Stage_Change_Date.toString(), excelLabel.Stage.toString());
			List<String> abc = setup.DragNDrop("", mode, object.Fundraising, ObjectFeatureName.pageLayouts, layoutName, sourceANDDestination);
			ThreadSleep(5000);
			if (!abc.isEmpty()) {
				log(LogStatus.FAIL, "field not added/already present 1", YesNo.Yes);
			}else{
				log(LogStatus.INFO, "field added/already present 1", YesNo.Yes);
			}
			driver.close();
			ThreadSleep(5000);
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
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M6tc028_2_FundraisingPageChangeStages(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		FundRaisingPageBusinessLayer frp=new FundRaisingPageBusinessLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String labelName[]={excelLabel.Last_Stage_Change_Date.toString()
		};
		String labelValues[]={todaysDate};
		String stages[]={Stage.Prospect.toString(),Stage.Interested.toString(),Stage.Sent_PPM.toString()};
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {

			if (lp.clickOnAlreadyCreatedItem(projectName, M6LSCFundraising1, 10)) {
				for(String stage:stages) {
				if (fp.changeStage(projectName, stage, 10)) {
					log(LogStatus.INFO, "successfully changed stage to "+stage, YesNo.Yes);
						
				}else {
					log(LogStatus.FAIL, "could not change stage to "+stage, YesNo.Yes);
					sa.assertTrue(false, "could not change stage to "+stage);
				}
				
				for (int i =0;i<labelName.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i],false)) {
						log(LogStatus.INFO,"successfully verified "+labelName[i],YesNo.No);

					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
					}

				}
				ThreadSleep(2000);
				if(dp.checkValueOfPathComponentValueOfStage(stage.replace("_", " "), 10)) {
					log(LogStatus.INFO,"stage on path component successfully verified "+stage,YesNo.Yes);
						
				}else {
					sa.assertTrue(false,"stage on path component could not be verified "+stage);
					log(LogStatus.SKIP,"stage on path component could not be verified "+stage,YesNo.Yes);
				}

				}
			}else {
				log(LogStatus.FAIL, "could not click on "+M6LSCFundraising1, YesNo.Yes);
				sa.assertTrue(false, "could not click on "+M6LSCFundraising1);
			}
		}else {
			log(LogStatus.FAIL, "fundraising tab is not clickable", YesNo.Yes);
			sa.assertTrue(false, "fundraising tab is not clickable");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}
	
	@Parameters({ "projectName"})
	@Test
	public void M6tc029_FundraisingPageChangeStages(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		FundRaisingPageBusinessLayer frp=new FundRaisingPageBusinessLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String labelName[]={excelLabel.Last_Stage_Change_Date.toString()
		};
		String labelValues[]={todaysDate};
		String stages[]={Stage.Declined.toString(),Stage.Verbal_Commitment.toString(),Stage.Declined.toString()};
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {

			if (lp.clickOnAlreadyCreatedItem(projectName, M6LSCFundraising1, 10)) {
				for(String stage:stages) {
				if (fp.changeStage(projectName, stage, 10)) {
					log(LogStatus.INFO, "successfully changed stage to "+stage, YesNo.Yes);
						
				}else {
					log(LogStatus.FAIL, "could not change stage to "+stage, YesNo.Yes);
					sa.assertTrue(false, "could not change stage to "+stage);
				}
				
				for (int i =0;i<labelName.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i],false)) {
						log(LogStatus.INFO,"successfully verified "+labelName[i],YesNo.No);

					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
					}

				}
				ThreadSleep(2000);
				if(dp.checkValueOfPathComponentValueOfStage(stage.replace("_", " "), 10)) {
					log(LogStatus.INFO,"stage on path component successfully verified "+stage,YesNo.Yes);
						
				}else {
					sa.assertTrue(false,"stage on path component could not be verified "+stage);
					log(LogStatus.SKIP,"stage on path component could not be verified "+stage,YesNo.Yes);
				}

				}
			}else {
				log(LogStatus.FAIL, "could not click on "+M6LSCFundraising1, YesNo.Yes);
				sa.assertTrue(false, "could not click on "+M6LSCFundraising1);
			}
		}else {
			log(LogStatus.FAIL, "fundraising tab is not clickable", YesNo.Yes);
			sa.assertTrue(false, "fundraising tab is not clickable");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M6tc030_ChangeStagesOnPathComponent(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		FundRaisingPageBusinessLayer frp=new FundRaisingPageBusinessLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String labelName[]={excelLabel.Stage.toString(),excelLabel.Last_Stage_Change_Date.toString()
		};
		String stages[]={Stage.Follow_up_Diligence.toString(),Stage.Declined.toString()};
		WebElement ele;
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {

			if (lp.clickOnAlreadyCreatedItem(projectName, M6LSCFundraising1, 10)) {
				for(String stage:stages) {
					stage=stage.replace("_", " ");
					String labelValues[]={stage,todaysDate};
					ele=dp.selectPathComponent(projectName, stage);
				if (click(driver, ele,"stage "+stage , action.BOOLEAN)) {
					ele=dp.getmarkAsCurrentStage(10);
					if (clickUsingJavaScript(driver, ele,"")) {
						log(LogStatus.SKIP,"Able to click on mark as current stage",YesNo.Yes);

						ThreadSleep(3000);
						if(stage.equalsIgnoreCase(Stage.Declined.toString())) {
						if(clickUsingActionClass(driver, frp.getdoneButton(projectName,20))) {
							log(LogStatus.SKIP,"Able to click on  popup done button",YesNo.Yes);

						}else {
							sa.assertTrue(false,"not Able to click on popup done button");
							log(LogStatus.SKIP,"not Able to click on popup done button",YesNo.Yes);
						}
						}
						refresh(driver);
						ThreadSleep(3000);
						for (int i =0;i<labelName.length;i++) {
							if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i],false)) {
								log(LogStatus.SKIP,"successfully verified "+labelName[i],YesNo.No);

							}else {
								sa.assertTrue(false,"Not Able to verify "+labelName[i]);
								log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
							}

						}
					}else {
						sa.assertTrue(false,"mark as curent stage btuton is not clickable");
						log(LogStatus.SKIP,"mark as curent stage btuton is not clickable",YesNo.Yes);
					}
				}else {
					sa.assertTrue(false,"stage button on path component is not clickable");
					log(LogStatus.SKIP,"stage button on path component is not clickable",YesNo.Yes);
				}
				}
			}else {
				sa.assertTrue(false,"fundraising is not found on fundraising tab "+M6LSCFundraising1);
				log(LogStatus.SKIP,"fundraising is not found on fundraising tab "+M6LSCFundraising1,YesNo.Yes);
			}
		}else {
			sa.assertTrue(false,"fundraising tab is not clickable");
			log(LogStatus.SKIP,"fundraising tab is not clickable",YesNo.Yes);
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M6tc031_DeleteAndRestoreFundraising(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		FundRaisingPageBusinessLayer frp=new FundRaisingPageBusinessLayer(driver);
		SetupPageBusinessLayer setup = new SetupPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		HomePageBusineesLayer home = new HomePageBusineesLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		WebElement ele;
		String labelName[]={excelLabel.Stage.toString(),excelLabel.Last_Stage_Change_Date.toString()
		};
		String labelValues[]={Stage.Declined.toString(),todaysDate};
		if (ip.clickOnTab(projectName, TabName.FundraisingsTab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6LSCFundraising1, 10)) {
				ThreadSleep(3000);
				if (ip.clickOnShowMoreActionDownArrow(projectName, PageName.Object5Page, ShowMoreActionDropDownList.Delete, 10)) {
					ThreadSleep(3000);ele = frp.getDeleteButtonOnDeletePopUp(projectName, 10);
					if (clickUsingJavaScript(driver,ele, "delete",  action.BOOLEAN)) {
						appLog.info("Successfully deleted event : "+M6LSCFundraising1);		
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
				log(LogStatus.ERROR, M6LSCFundraising1+" fundraising is not found on fundraising tab", YesNo.Yes);
				sa.assertTrue(false,M6LSCFundraising1+" fundraising is not found on fundraising tab" );
			}
		}else {
			log(LogStatus.ERROR, "fundraising tab is not clickable", YesNo.Yes);
			sa.assertTrue(false,"fundraising tab is not clickable" );
		}

		String name=M6LSCFundraising1;
		TabName tabName =TabName.RecycleBinTab;
		if (cp.clickOnTab(projectName, tabName)) {
			log(LogStatus.INFO,"Clicked on Tab : "+tabName+" For : "+name,YesNo.No);
			ThreadSleep(1000);
			cp.clickOnAlreadyCreatedItem(projectName, tabName, name, 20);
			log(LogStatus.INFO,"Clicked on  : "+name+" For : "+tabName,YesNo.No);
			ThreadSleep(2000);

			ele=cp.getCheckboxOfRestoreItemOnRecycleBin(projectName, name, 10);
			if (clickUsingJavaScript(driver, ele, "Check box against : "+name, action.BOOLEAN)) {
				log(LogStatus.INFO,"Click on checkbox for "+name,YesNo.No);

				ThreadSleep(1000);
				//					scn.nextLine();
				ele=cp.getRestoreButtonOnRecycleBin(projectName, 10);
				if (clickUsingJavaScript(driver, ele, "Restore Button : "+name, action.BOOLEAN)) {
					log(LogStatus.INFO,"Click on Restore Button for "+name,YesNo.No);
					ThreadSleep(1000);

				} else {
					sa.assertTrue(false,"Not Able to Click on Restore Button for "+name);
					log(LogStatus.SKIP,"Not Able to Click on Restore Button for "+name,YesNo.Yes);
				}

			} else {
				sa.assertTrue(false,"Not Able to Click on checkbox for "+name);
				log(LogStatus.SKIP,"Not Able to Click on checkbox for "+name,YesNo.Yes);
			}

		} else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+tabName+" For : "+name);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+tabName+" For : "+name,YesNo.Yes);
		}
		if (ip.clickOnTab(projectName, TabName.FundraisingsTab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6LSCFundraising1, 10)) {

				for (int i =0;i<labelName.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i],false)) {
						log(LogStatus.INFO,"successfully verified "+labelName[i],YesNo.No);

					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
					}

				}
				if(dp.checkValueOfPathComponentValueOfStage(Stage.Declined.toString(), 10)) {
					log(LogStatus.INFO,"stage on path component successfully verified",YesNo.Yes);

				}else {
					sa.assertTrue(false,"stage on path component could not be verified");
					log(LogStatus.SKIP,"stage on path component could not be verified",YesNo.Yes);
				}
			}else {
				log(LogStatus.ERROR, M6LSCFundraising1+" fundraising is not found on fundraising tab", YesNo.Yes);
				sa.assertTrue(false,M6LSCFundraising1+" fundraising is not found on fundraising tab" );
			}
		}else {
			log(LogStatus.ERROR, "fundraising tab is not clickable", YesNo.Yes);
			sa.assertTrue(false,"fundraising tab is not clickable" );
		}

		switchToDefaultContent(driver);
		lp.CRMlogout();
		sa.assertAll();
	}
	@Parameters({ "projectName"})
	@Test
	public void M6tc032_1_ChangeLabelNamesOfStages(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		FundRaisingPageBusinessLayer frp = new FundRaisingPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		WebElement ele=null;
		String parentID=null;
		String stage=Stage.Interested.toString();
		String newstage=Stage.New_Interested.toString().replace("_", " ");
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject(environment, mode,object.Fundraising )) {
					if(sp.clickOnObjectFeature(environment, mode,object.Fundraising, ObjectFeatureName.FieldAndRelationShip)) {
						if (sendKeys(driver, sp.getsearchTextboxFieldsAndRelationships(10), excelLabel.Stage.toString()+Keys.ENTER, "status", action.BOOLEAN)) {
							if (sp.clickOnAlreadyCreatedLayout(excelLabel.Stage.toString())) {
									switchToDefaultContent(driver);
									ThreadSleep(5000);
									switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
									stage=stage.replace("_", " ");
									ThreadSleep(3000);ele=frp.getEditBtn(projectName, stage,action.SCROLLANDBOOLEAN,10);
									if (ele!=null) {
										log(LogStatus.INFO,"successfully found active stage : "+stage,YesNo.No);	
									}else {
										log(LogStatus.ERROR,"could not find active stage"+stage,YesNo.No);	
										ThreadSleep(5000);
									}
										if (click(driver, ele, "edit", action.SCROLLANDBOOLEAN)) {
											switchToDefaultContent(driver);
											ThreadSleep(3000);
											switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
											
											if (sendKeys(driver, sp.getRecordTypeLabel(projectName, "Label", 10), newstage, "label", action.BOOLEAN)){
												if (click(driver, sp.getSaveButtonInCustomFields(10), "save", action.SCROLLANDBOOLEAN)) {
													ThreadSleep(3000);	
													log(LogStatus.INFO, "save button is clicked",YesNo.No);

												}else {
													log(LogStatus.FAIL, "save button not clickable",YesNo.Yes);
													sa.assertTrue(false, "save button not clickable");
												}
											}else {
												log(LogStatus.FAIL, "label textbox is not visible",YesNo.Yes);
												sa.assertTrue(false, "label textbox is not visible");
											}
										}else {
											log(LogStatus.FAIL, "edit button not clickable",YesNo.Yes);
											sa.assertTrue(false, "edit button not clickable");
										}
										}else {
											log(LogStatus.ERROR,"Stage field is not clickable : ",YesNo.No);	
											sa.assertTrue(false,"Stage field is not clickable : ");
										}
									}else {
										log(LogStatus.ERROR,"search textbox field is not visible",YesNo.No);	
										sa.assertTrue(false,"search textbox field is not visible");
									}
								}else {
									log(LogStatus.ERROR,"field and relationships link is not clickable",YesNo.No);	
									sa.assertTrue(false,"field and relationships link is not clickable");
								}
							}else {
								log(LogStatus.ERROR,"fundraising object is not clickable",YesNo.No);	
								sa.assertTrue(false,"fundraising object is not clickable");
							}
				
				driver.close();
				driver.switchTo().window(parentID);
						}
			else {
				log(LogStatus.ERROR,"could not find new window",YesNo.No);	
				sa.assertTrue(false,"could not find new window");
			}
		}else {
			log(LogStatus.ERROR,"setup link is not clickable",YesNo.No);	
			sa.assertTrue(false,"setup link is not clickable");
		}
		
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M6tc032_2_ChangeLabelNamesOfStages(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		FundRaisingPageBusinessLayer frp = new FundRaisingPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String labelName[]={excelLabel.Stage.toString(),excelLabel.Last_Stage_Change_Date.toString()
		};
		String stage=Stage.New_Interested.toString().replace("_", " ");
		String labelValues[]={stage,todaysDate};
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {

			if (lp.clickOnAlreadyCreatedItem(projectName, M6LSCFundraising1, 10)) {
				
				if (fp.changeStage(projectName, stage, 10)) {
					log(LogStatus.INFO, "successfully changed stage to "+stage, YesNo.Yes);
						
				}else {
					log(LogStatus.FAIL, "could not change stage to "+stage, YesNo.Yes);
					sa.assertTrue(false, "could not change stage to "+stage);
				}
				ThreadSleep(3000);
				refresh(driver);
				ThreadSleep(3000);
				for (int i =0;i<labelName.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i],false)) {
						log(LogStatus.INFO,"successfully verified "+labelName[i],YesNo.No);

					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
					}

				}
				if(dp.checkValueOfPathComponentValueOfStage(stage, 10)) {
					log(LogStatus.INFO,"stage on path component successfully verified "+stage,YesNo.Yes);
						
				}else {
					sa.assertTrue(false,"stage on path component could not be verified "+stage);
					log(LogStatus.SKIP,"stage on path component could not be verified "+stage,YesNo.Yes);
				}

			}else {
				log(LogStatus.FAIL, "could not click on "+M6LSCFundraising1, YesNo.Yes);
				sa.assertTrue(false, "could not click on "+M6LSCFundraising1);
			}
		}else {
			log(LogStatus.FAIL, "fundraising tab is not clickable", YesNo.Yes);
			sa.assertTrue(false, "fundraising tab is not clickable");
		}
		lp.CRMlogout();
		sa.assertAll();
	}

	@Parameters({ "projectName"})
	@Test
	public void M6tc033_1_CreateNewLabelOfStages(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		FundRaisingPageBusinessLayer frp = new FundRaisingPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		WebElement ele=null;
		String parentID=null;
		String stage=Stage.New_Stage_Verification.toString();
		String newstage=Stage.New_Interested.toString().replace("_", " ");
		if (home.clickOnSetUpLink()) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
				if (sp.searchStandardOrCustomObject(environment, mode,object.Fundraising )) {
					if(sp.clickOnObjectFeature(environment, mode,object.Fundraising, ObjectFeatureName.FieldAndRelationShip)) {
						if (sendKeys(driver, sp.getsearchTextboxFieldsAndRelationships(10), excelLabel.Stage.toString()+Keys.ENTER, "status", action.BOOLEAN)) {
							if (sp.clickOnAlreadyCreatedLayout(excelLabel.Stage.toString())) {
									switchToDefaultContent(driver);
									ThreadSleep(10000);
									switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
									stage=stage.replace("_", " ");
									ThreadSleep(3000);
									ele=sp.getValuesNewButton(10);
									if (click(driver, ele, "new button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "click on Values New Button", YesNo.No);
									switchToDefaultContent(driver);
									ThreadSleep(20000);
									switchToFrame(driver, 10, sp.getFrame(PageName.PipelineCustomPage, 10));
									ThreadSleep(5000);
									if (sendKeys(driver, dp.getTextArea(20), stage, stage, action.BOOLEAN)) {
										log(LogStatus.INFO,"enter value on textarea "+stage,YesNo.No);
										if (click(driver, sp.getCustomTabSaveBtn(projectName, 10), "save button", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.ERROR, "Click on save Button : ", YesNo.No);
											ThreadSleep(2000);
										}else {
											log(LogStatus.ERROR, "Not Able to Click on save Button : ", YesNo.Yes);
											sa.assertTrue(false,"Not Able to Click on save Button : ");
										}
									}else {
										sa.assertTrue(false,"Not Able to enter value to textarea ");
										log(LogStatus.SKIP,"Not Able to enter value to textarea ",YesNo.Yes);
									}
									} else {
										log(LogStatus.ERROR, "new button is not clickable", YesNo.Yes);
										sa.assertTrue(false, "new button is not clickable");
									}
								} else {
									log(LogStatus.ERROR, "stage field is not clickable", YesNo.Yes);
									sa.assertTrue(false, "stage field is not clickable");
								}
						}else {
							log(LogStatus.ERROR, "field search textbox is not visible", YesNo.Yes);
							sa.assertTrue(false, "field search textbox is not visible");
						}
					}else {
						log(LogStatus.ERROR, "fundraising object is not clickable", YesNo.Yes);
						sa.assertTrue(false, "fundraising object is not clickable");
					}
				}else {
					log(LogStatus.ERROR, "fundraising object is not clickable", YesNo.Yes);
					sa.assertTrue(false, "fundraising object is not clickable");
				}
				ThreadSleep(5000);
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
	public void M6tc033_2_CreateNewLabelOfStages(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		FundRaisingPageBusinessLayer frp = new FundRaisingPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer sp=new SetupPageBusinessLayer(driver);
		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		String labelName[]={excelLabel.Stage.toString(),excelLabel.Last_Stage_Change_Date.toString()
		};
		String stage=Stage.New_Stage_Verification.toString().replace("_", " ");
		String labelValues[]={stage,todaysDate};
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {

			if (lp.clickOnAlreadyCreatedItem(projectName, M6LSCFundraising1, 10)) {
				
				if (fp.changeStage(projectName, stage, 10)) {
					log(LogStatus.INFO, "successfully changed stage to "+stage, YesNo.Yes);
						
				}else {
					log(LogStatus.FAIL, "could not change stage to "+stage, YesNo.Yes);
					sa.assertTrue(false, "could not change stage to "+stage);
				}
				ThreadSleep(3000);
				refresh(driver);
				ThreadSleep(3000);
				for (int i =0;i<labelName.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i],false)) {
						log(LogStatus.INFO,"successfully verified "+labelName[i],YesNo.No);

					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
					}

				}
				if(dp.checkValueOfPathComponentValueOfStage(stage, 10)) {
					log(LogStatus.INFO,"stage on path component successfully verified "+stage,YesNo.Yes);
						
				}else {
					sa.assertTrue(false,"stage on path component could not be verified "+stage);
					log(LogStatus.SKIP,"stage on path component could not be verified "+stage,YesNo.Yes);
				}

			}else {
				log(LogStatus.FAIL, "could not click on "+M6LSCFundraising1, YesNo.Yes);
				sa.assertTrue(false, "could not click on "+M6LSCFundraising1);
			}
		}else {
			log(LogStatus.FAIL, "fundraising tab is not clickable", YesNo.Yes);
			sa.assertTrue(false, "fundraising tab is not clickable");
		}
		lp.CRMlogout();
		sa.assertAll();
	}
	@Parameters({ "projectName"})
	@Test
	public void M6tc034_VerifyNewLocale(String projectName) {
		LoginPageBusinessLayer lp = new LoginPageBusinessLayer(driver);
		FundsPageBusinessLayer fp = new FundsPageBusinessLayer(driver);
		ContactsPageBusinessLayer cp = new ContactsPageBusinessLayer(driver);
		InstitutionsPageBusinessLayer ip = new InstitutionsPageBusinessLayer(driver);
		DealPageBusinessLayer dp = new DealPageBusinessLayer(driver);
		FundRaisingPageBusinessLayer frp = new FundRaisingPageBusinessLayer(driver);
		HomePageBusineesLayer home=new HomePageBusineesLayer(driver);
		SetupPageBusinessLayer setup=new SetupPageBusinessLayer(driver);
		int i = 0;
		String labelValues,labelName;
		String locale[]={"German (Germany)","English (United Kingdom)","English (Australia)","French (France)"};
		String timezone[]={"(GMT+02:00) Central European Summer Time (Europe/Berlin)","(GMT+01:00) British Summer Time (Europe/London)","(GMT+08:00) Australian Western Standard Time (Australia/Perth)","(GMT+02:00) Central European Summer Time (Europe/Paris)"};
		for(String t:timezone) {
		lp.CRMLogin(superAdminUserName, adminPassword, appName);
		String parentID=null;
		if (home.clickOnSetUpLink("", Mode.Lightning.toString())) {
			parentID=switchOnWindow(driver);
			if (parentID!=null) {
				log(LogStatus.INFO, "Able to switch on  new window", YesNo.No);
				ThreadSleep(3000);
				refresh(driver);
				ThreadSleep(5000);
				System.out.println("Url:"+driver.getCurrentUrl());
				if(setup.searchStandardOrCustomObject(environment,mode, object.Users)) {
					log(LogStatus.INFO, "click on Object : "+object.Users, YesNo.No);
					ThreadSleep(2000);
					
					if(setup.clickOnEditBtnForCRMUser(driver, crmUser1LastName, crmUser1FirstName, 20)) {
						log(LogStatus.INFO, "Click on edit Button "+crmUser1LastName+","+crmUser1FirstName, YesNo.No);
						ThreadSleep(2000);

						if (selectVisibleTextFromDropDown(driver, setup.getLocaleDropdownList(10), "locale dropdown",locale[i])) {
							log(LogStatus.INFO, "selected visbible text from the Timezone dropdown "+locale[i], YesNo.No);
							ThreadSleep(2000);
							switchToDefaultContent(driver);
							ThreadSleep(5000);
							switchToFrame(driver, 20, setup.getSetUpPageIframe(20));
							if (selectVisibleTextFromDropDown(driver, setup.gettimezoneDropdownList(10), "timezone dropdown",t)) {
								log(LogStatus.INFO, "selected visbible text from the Timezone dropdown "+t, YesNo.No);
								ThreadSleep(2000);
							if (clickUsingJavaScript(driver, setup.getSaveButton(20), "Save Button",action.SCROLLANDBOOLEAN)) {
								ThreadSleep(5000);
								switchToDefaultContent(driver);
							} else {
								log(LogStatus.ERROR, "Not Able to Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName, YesNo.Yes);
								sa.assertTrue(false, "Not Able to Click on Save Button for  "+crmUser1LastName+","+crmUser1FirstName);
							}
						} else {
							log(LogStatus.ERROR, "Not Able to select timezone dropdown for  "+crmUser1LastName+","+crmUser1FirstName, YesNo.Yes);
							sa.assertTrue(false, "Not Able to select timezone dropdown for  "+crmUser1LastName+","+crmUser1FirstName);
						}
						}else {
							log(LogStatus.ERROR, "Not Able to select locale dropdown for  "+crmUser1LastName+","+crmUser1FirstName, YesNo.Yes);
							sa.assertTrue(false, "Not Able to select locale dropdown for  "+crmUser1LastName+","+crmUser1FirstName);
						}
					}else {
						log(LogStatus.ERROR, "edit button is not clickable for "+crmUser1LastName+","+crmUser1FirstName, YesNo.Yes);
						sa.assertTrue(false, "edit button is not clickable for "+crmUser1LastName+","+crmUser1FirstName);
					}
					}else {
						log(LogStatus.ERROR, "users object is not clickable", YesNo.Yes);
						sa.assertTrue(false, "users object is not clickable");
					}
				lp.CRMlogout();
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
		i++;
		
		lp.CRMlogout();
		ThreadSleep(5000);
		refresh(driver);
		lp = new LoginPageBusinessLayer(driver);
		fp = new FundsPageBusinessLayer(driver);
		cp = new ContactsPageBusinessLayer(driver);
		ip = new InstitutionsPageBusinessLayer(driver);
		dp = new DealPageBusinessLayer(driver);
		frp = new FundRaisingPageBusinessLayer(driver);
		home=new HomePageBusineesLayer(driver);
		String fundr,stage,inst;
		int j=i+1;
		labelName=excelLabel.Last_Stage_Change_Date.toString();
		if(j==1)
			labelValues=todaysDateEurope;
		else
		labelValues=todaysDateddmm;
		lp.CRMLogin(crmUser1EmailID, adminPassword);
		if (lp.clickOnTab(projectName, TabName.FundraisingsTab)) {
			fundr=ExcelUtils.readData(phase1DataSheetFilePath,"Fundraisings",excelLabel.Variable_Name, "M6LSCFUNDR"+j, excelLabel.FundRaising_Name);
			stage=ExcelUtils.readData(phase1DataSheetFilePath,"Fundraisings",excelLabel.Variable_Name, "M6LSCFUNDR"+j, excelLabel.Stage);
			
			if(frp.createFundRaising(environment,mode,fundr,M6LSCFund1,M6LSCIns1, null, null, null, null)){
				appLog.info("fundraising is created : "+fundr);
				ThreadSleep(3000);
				if (fp.changeStage(projectName, stage, 10)) {
					log(LogStatus.INFO, "successfully changed stage to "+stage, YesNo.Yes);
						
				}else {
					log(LogStatus.FAIL, "could not change stage to "+stage, YesNo.Yes);
					sa.assertTrue(false, "could not change stage to "+stage);
				}
				ThreadSleep(3000);
				refresh(driver);
				ThreadSleep(3000);
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName,labelValues,"date")) {
						log(LogStatus.INFO,"successfully verified "+labelName,YesNo.No);

					}else {
						sa.assertTrue(false,"Not Able to verify "+todaysDateddmm+" "+labelName);
						log(LogStatus.SKIP,"Not Able to verify "+todaysDateddmm+" "+labelName,YesNo.Yes);
					}

			}else {
				appLog.error("Not able to create fundraising: "+fundr);
				sa.assertTrue(false, "Not able to create fundraising: "+fundr);
			}
		}else {
			sa.assertTrue(false,"Not Able to Click on Tab : "+TabName.FundraisingsTab);
			log(LogStatus.SKIP,"Not Able to Click on Tab : "+TabName.FundraisingsTab,YesNo.Yes);
		}
		lp.CRMlogout();
		ThreadSleep(5000);
		lp = new LoginPageBusinessLayer(driver);
		fp = new FundsPageBusinessLayer(driver);
		cp = new ContactsPageBusinessLayer(driver);
		ip = new InstitutionsPageBusinessLayer(driver);
		dp = new DealPageBusinessLayer(driver);
		frp = new FundRaisingPageBusinessLayer(driver);
		home=new HomePageBusineesLayer(driver);
		}
		
		
		sa.assertAll();
	}
}

