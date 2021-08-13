package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonVariables.*;

import java.util.Scanner;

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
		//for(int i = 16;i<17;i++){
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
				
				}
				else {
					if (ip.createEntityOrAccount(projectName, value, type,null, 20)) {
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
		//for(int i =1;i<19;i++) {
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
				for (int i = 0;i<3;i++) {
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
				ThreadSleep(2000);
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
		String labelValues[]={Stage.Closed.toString(),Stage.Closed.toString(),todaysDateSingleDigit};
		String labelName1[]={excelLabel.Status.toString()
		};
		String labelValues1[]={InstRecordType.Portfolio_Company.toString()};
		String labelValues2[]={String.valueOf(averageDealQualityScore),String.valueOf(totalDealsshown)};


		lp.CRMLogin(crmUser1EmailID, adminPassword, appName);
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal2, 10)) {
				for (int i =0;i<labelName.length;i++) {
					if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
						log(LogStatus.SKIP,"successfully verified "+labelName[i],YesNo.No);

					}else {
						sa.assertTrue(false,"Not Able to verify "+labelName[i]);
						log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
					}

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
							log(LogStatus.SKIP,"successfully verified "+labelName1[i],YesNo.No);
							
						}else {
							sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
							log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
						}

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
		String labelValues[]={Stage.Closed.toString(),Stage.Closed.toString(),todaysDateSingleDigit};
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
								
								if (fp.FieldValueVerificationOnFundPage(projectName, labelName[i],labelValues[i])) {
									log(LogStatus.INFO,"successfully verified "+labelName[i],YesNo.Yes);
								}else {
									flag=false;
									sa.assertTrue(false,"Not Able to verify "+labelName[i]);
									log(LogStatus.SKIP,"Not Able to verify "+labelName[i],YesNo.Yes);
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
										
										if (fp.FieldValueVerificationOnFundPage(projectName, labelName1[i],labelValues1[i])) {
											log(LogStatus.INFO,"successfully verified "+labelName1[i],YesNo.Yes);
										}else {
											flag=false;
											sa.assertTrue(false,"Not Able to verify "+labelName1[i]);
											log(LogStatus.SKIP,"Not Able to verify "+labelName1[i],YesNo.Yes);
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

				if(setup.searchStandardOrCustomObject(environment, mode, object.Institution)) {
					if(setup.clickOnObjectFeature(environment, mode, object.Institution, ObjectFeatureName.recordTypes)) {
						if (sendKeys(driver, setup.getsearchTextboxFieldsAndRelationships(10), rt+Keys.ENTER, "status", action.BOOLEAN)) {
							if (setup.clickOnAlreadyCreatedLayout(rt)) {
								switchToFrame(driver, 10, setup.getFrame(PageName.RecordTypePortfolioCompany, 10));
								if (click(driver, setup.getEditButton(environment,Mode.Classic.toString(),10), "edit", action.BOOLEAN)) {
									switchToDefaultContent(driver);
									switchToFrame(driver, 10, setup.getFrame(PageName.RecordTypePortfolioCompany, 10));
									ThreadSleep(2000);
									setup.getRecordTypeLabel(projectName, "Record Type Label", 10).sendKeys(InstRecordType.Portfolio.toString());
									ThreadSleep(2000);switchToAlertAndAcceptOrDecline(driver, 30, action.ACCEPT);
										if (click(driver, setup.getSaveButtonInCustomFields(10), "save", action.SCROLLANDBOOLEAN)) {
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

				if(setup.searchStandardOrCustomObject(environment, mode, object.Institution)) {
					if(setup.clickOnObjectFeature(environment, mode, object.Institution, ObjectFeatureName.recordTypes)) {
						if (sendKeys(driver, setup.getsearchTextboxFieldsAndRelationships(10), InstRecordType.Portfolio.toString()+Keys.ENTER, "status", action.BOOLEAN)) {
							if (setup.clickOnAlreadyCreatedLayout(InstRecordType.Portfolio.toString())) {
								switchToFrame(driver, 10, setup.getFrame(PageName.RecordTypePortfolioCompany, 10));
								if (click(driver, setup.getEditButton(environment,Mode.Classic.toString(),10), "edit", action.BOOLEAN)) {
									switchToDefaultContent(driver);
									switchToFrame(driver, 10, setup.getFrame(PageName.RecordTypePortfolioCompany, 10));
									ThreadSleep(2000);
									setup.getRecordTypeLabel(projectName, "Record Type Label", 10).sendKeys(rt);
									switchToAlertAndAcceptOrDecline(driver, 20, action.ACCEPT);
									if (sendKeys(driver, setup.getRecordTypeLabel(projectName, "Record Type Label", 10), rt, "label", action.BOOLEAN)){
										if (sendKeysWithoutClearingTextBox(driver, setup.getRecordTypeLabel(projectName, "Record Type Name", 10), updateLabel, "label", action.BOOLEAN)){
											switchToAlertAndAcceptOrDecline(driver, 10, action.ACCEPT);
										}
									}
									ThreadSleep(2000);
									//setup.getRecordTypeLabel(projectName,"Record Type Name", 10).sendKeys(updateLabel);
									//if(sendKeys(driver, setup.getRecordTypeLabel(projectName,"Record Type Name", 10), , "name", action.SCROLLANDBOOLEAN)) {
										
									
									if (click(driver, setup.getSaveButtonInCustomFields(10), "save", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "save button is clicked",YesNo.No);

										}else {
											log(LogStatus.FAIL, "save button not clickable",YesNo.Yes);
											sa.assertTrue(false, "save button not clickable");
										}
//									}else {
//										log(LogStatus.FAIL, "name textbox not visible",YesNo.Yes);
//										sa.assertTrue(false, "name textbox not visible");
//									}
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
		for(String d:deals) {/*
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
		*/}
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

				if(setup.searchStandardOrCustomObject(environment, mode, object.Institution)) {
					if(setup.clickOnObjectFeature(environment, mode, object.Institution, ObjectFeatureName.recordTypes)) {
						if (sendKeys(driver, setup.getsearchTextboxFieldsAndRelationships(10), rt+Keys.ENTER, "status", action.BOOLEAN)) {
							if (setup.clickOnAlreadyCreatedLayout(rt)) {
								switchToFrame(driver, 10, setup.getFrame(PageName.RecordTypePortfolioCompany, 10));
								if (click(driver, setup.getEditButton(environment,Mode.Classic.toString(),10), "edit", action.BOOLEAN)) {
									ThreadSleep(2000);
									switchToDefaultContent(driver);
									switchToFrame(driver, 10, setup.getFrame(PageName.RecordTypePortfolioCompany, 10));
									ThreadSleep(2000);
										if (sendKeys(driver, setup.getRecordTypeLabel(projectName, "Record Type Name", 10), updateLabel, "label", action.BOOLEAN)){
											switchToAlertAndAcceptOrDecline(driver, 10, action.ACCEPT);
										}
									ThreadSleep(2000);
									//setup.getRecordTypeLabel(projectName,"Record Type Name", 10).sendKeys(updateLabel);
									//if(sendKeys(driver, setup.getRecordTypeLabel(projectName,"Record Type Name", 10), , "name", action.SCROLLANDBOOLEAN)) {
									if (click(driver, setup.getactiveCheckbox(10), "active", action.BOOLEAN)) {
										log(LogStatus.INFO, "activeCheckbox is clicked",YesNo.No);

									}else {
										log(LogStatus.FAIL, "activeCheckbox not clickable",YesNo.Yes);
										sa.assertTrue(false, "activeCheckbox not clickable");
									}
									
									if (click(driver, setup.getSaveButtonInCustomFields(10), "save", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "save button is clicked",YesNo.No);

										}else {
											log(LogStatus.FAIL, "save button not clickable",YesNo.Yes);
											sa.assertTrue(false, "save button not clickable");
										}
//									}else {
//										log(LogStatus.FAIL, "name textbox not visible",YesNo.Yes);
//										sa.assertTrue(false, "name textbox not visible");
//									}
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
				sa.assertTrue(false,M6Deal8+" deal is not clickable");
				log(LogStatus.SKIP,M6Deal8+" deal is not clickable",YesNo.Yes);
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
		if (home.clickOnSetUpLink()) {
			String parentID = switchOnWindow(driver);
			if (parentID!=null) {

				if(setup.searchStandardOrCustomObject(environment, mode, object.Institution)) {
					if(setup.clickOnObjectFeature(environment, mode, object.Institution, ObjectFeatureName.recordTypes)) {
						if (sendKeys(driver, setup.getsearchTextboxFieldsAndRelationships(10), rt+Keys.ENTER, "status", action.BOOLEAN)) {
							if (setup.clickOnAlreadyCreatedLayout(rt)) {
								switchToFrame(driver, 10, setup.getFrame(PageName.RecordTypePortfolioCompany, 10));
								if (click(driver, setup.getEditButton(environment,Mode.Classic.toString(),10), "edit", action.BOOLEAN)) {
									ThreadSleep(2000);
									switchToDefaultContent(driver);
									switchToFrame(driver, 10, setup.getFrame(PageName.RecordTypePortfolioCompany, 10));
									ThreadSleep(2000);
									//setup.getRecordTypeLabel(projectName,"Record Type Name", 10).sendKeys(updateLabel);
									//if(sendKeys(driver, setup.getRecordTypeLabel(projectName,"Record Type Name", 10), , "name", action.SCROLLANDBOOLEAN)) {
									if(!isSelected(driver, setup.getactiveCheckbox(10), "active checkbox")){
									if (click(driver, setup.getactiveCheckbox(10), "active", action.BOOLEAN)) {
										log(LogStatus.INFO, "activeCheckbox is now checked",YesNo.No);

									}else {
										log(LogStatus.FAIL, "activeCheckbox not clickable",YesNo.Yes);
										sa.assertTrue(false, "activeCheckbox not clickable");
									}
									}
									if (click(driver, setup.getSaveButtonInCustomFields(10), "save", action.SCROLLANDBOOLEAN)) {
										ThreadSleep(3000);	
										log(LogStatus.INFO, "save button is clicked",YesNo.No);

										}else {
											log(LogStatus.FAIL, "save button not clickable",YesNo.Yes);
											sa.assertTrue(false, "save button not clickable");
										}
//									}else {
//										log(LogStatus.FAIL, "name textbox not visible",YesNo.Yes);
//										sa.assertTrue(false, "name textbox not visible");
//									}
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
				
				if(setup.searchStandardOrCustomObject(environment, mode, object.Deal)) {
					if(setup.clickOnObjectFeature(environment, mode, object.Deal, ObjectFeatureName.FieldAndRelationShip)) {
						if (sendKeys(driver, setup.getsearchTextboxFieldsAndRelationships(10), excelLabel.Stage.toString(), "stage", action.BOOLEAN)) {
							if (setup.clickOnAlreadyCreatedLayout(excelLabel.Stage.toString())) {
								switchToDefaultContent(driver);
								switchToFrame(driver, 10, setup.getFrame(PageName.PipelineCustomPage, 10));
								WebElement ele=dp.findDeactivateLink(projectName, Stage.Closed.toString());
								if (click(driver, ele, "deactivate closed", action.SCROLLANDBOOLEAN)) {
									ThreadSleep(3000);
									driver.switchTo().alert().accept();
									//switchToAlertAndAcceptOrDecline(driver, 10, action.ACCEPT);
									switchToDefaultContent(driver);
									switchToFrame(driver, 10, setup.getFrame(PageName.PipelineCustomPage, 10));
										
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
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal9, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
					if (click(driver, dp.getnextButton(10),"next button", action.BOOLEAN)) {
						ThreadSleep(3000);
						refresh(driver);
						ThreadSleep(2000);
						String text=dp.getconvertToPortfolioMessageAfterNext(10).getText();

						if (text.contains(dp.convertToPortfolioAfterNext(M6Ins9))) {
							log(LogStatus.INFO,"successfully verified max character inst name on convert to portfolio message congratulations",YesNo.Yes);

						}else {
							sa.assertTrue(false,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins9)+"\nactual: "+text);
							log(LogStatus.SKIP,"could not verify convert to portfolio message Expected: "+dp.convertToPortfolioAfterNext(M6Ins9)+"\nactual: "+text,YesNo.Yes);
						}
						if (click(driver, dp.getconvertToPortfolioCrossButton(10), "finish", action.BOOLEAN)) {
							log(LogStatus.INFO, "clicked on cross button", YesNo.No);
						}else {
							sa.assertTrue(false,"could not click on cross button");
							log(LogStatus.SKIP,"could not click on cross button",YesNo.Yes);
						}
						
					

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
								switchToFrame(driver, 10, setup.getFrame(PageName.PipelineCustomPage, 10));
								WebElement ele=dp.findActivateLink(projectName, Stage.Closed.toString());
								if (click(driver, ele, "activate closed", action.SCROLLANDBOOLEAN)) {
									ThreadSleep(3000);
									switchToDefaultContent(driver);
									switchToFrame(driver, 10, setup.getFrame(PageName.PipelineCustomPage, 10));
										
									ele=dp.findDeactivateLink(projectName, Stage.Closed.toString());
									if(ele!=null) {
										log(LogStatus.INFO, "successfully activated closed value", YesNo.No);
									}else {
										sa.assertTrue(false,"not able to activate closed value");
										log(LogStatus.SKIP,"not able to activate closed value",YesNo.Yes);
									}
									ele=setup.clickOnEditInFrontOfFieldValues(projectName, Stage.Closed.toString());
									if (click(driver, ele, "closed", action.BOOLEAN)) {
										switchToDefaultContent(driver);
										ThreadSleep(3000);
										switchToFrame(driver, 10, setup.getFrame(PageName.PipelineCustomPage, 10));
										sendKeys(driver, setup.getFieldLabelTextBox1(10), Stage.Closed_Updated.toString(), "label", action.BOOLEAN);
											

										if (click(driver, fp.getCustomTabSaveBtn(10), "save", action.BOOLEAN)) {
											ThreadSleep(3000);
											switchToDefaultContent(driver);
											ThreadSleep(3000);
											switchToFrame(driver, 10, setup.getFrame(PageName.PipelineCustomPage, 10));
											ele=dp.findDeactivateLink(projectName, Stage.Closed_Updated.toString());
											
											if(ele!=null) {
												log(LogStatus.INFO, "successfully renamed closed value", YesNo.No);
												if (click(driver, ele, "deactivate link", action.SCROLLANDBOOLEAN)) {
													ThreadSleep(3000);
													driver.switchTo().alert().accept();
													//switchToAlertAndAcceptOrDecline(driver, 10, action.ACCEPT);
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
						ThreadSleep(4000);if (dp.getconvertToPortfolioMessageRepeat(10)!=null) {
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
								switchToFrame(driver, 10, setup.getFrame(PageName.PipelineCustomPage, 10));
								WebElement ele=dp.findActivateLink(projectName, Stage.Closed_Updated.toString());
								if (click(driver, ele, "activate closed", action.SCROLLANDBOOLEAN)) {
									ThreadSleep(3000);
									switchToDefaultContent(driver);
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
	public void M6tc017_CheckConvertToPortfolioFuncWhenRecordTypeAdvisor(String projectName) {
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
		String labelValues[]={Stage.Closed.toString(),Stage.Closed_Updated.toString(),String.valueOf(closedScore)};
		
		if (ip.clickOnTab(projectName, TabName.Object4Tab)) {
			if (ip.clickOnAlreadyCreatedItem(projectName, M6Deal12, 10)) {
				if (click(driver, dp.getconvertToPortfolio(10),"convert to portfolio button", action.BOOLEAN)) {
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
	
}
