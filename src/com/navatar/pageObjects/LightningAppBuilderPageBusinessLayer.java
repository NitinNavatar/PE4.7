package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.log;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.navatar.generic.CommonLib;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.SoftAssert;
import com.relevantcodes.extentreports.LogStatus;

public class LightningAppBuilderPageBusinessLayer extends LightningAppBuilderPage {

	public LightningAppBuilderPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public boolean CreateAppPage(String environment, String mode, String LabelName,String tableName,String parentWindowID)
	{
		boolean flag=false;
		BasePageBusinessLayer BP=new BasePageBusinessLayer(driver);
		SoftAssert sa= new SoftAssert();

		CommonLib.switchToFrame(driver, 50, getLocator());

		if (CommonLib.click(driver, getnewButton(80), "New Button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on the new button", YesNo.No);
			CommonLib.switchToDefaultContent(driver);
			if (CommonLib.click(driver, getnextButton(80), "Next Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on the next button", YesNo.No);
				if (CommonLib.sendKeys(driver, getlabelName(80), LabelName, "Label Name", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, "Title has been Entered", YesNo.No);
					if (CommonLib.click(driver, getnextButton(80), "Next Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on the next button", YesNo.No);
						if (CommonLib.click(driver, getfinishButton(80), "Next Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on the Finish button", YesNo.No);
							CommonLib.switchToFrame(driver, 50, getAppBuilderIframe(80));
							CommonLib.ThreadSleep(20000);
							if(CommonLib.isElementPresent(getAddComponentButton(50)))
							{
								if(CommonLib.click(driver, getAddComponentButton(50), "Add to component", action.SCROLLANDBOOLEAN))
								{
									log(LogStatus.INFO, "Add to component button has been clicked", YesNo.No);
								}
								else
								{
									log(LogStatus.ERROR, "Could not be click on the Add to component button", YesNo.Yes);
									return false;	
								}
							}
							else
							{
								JavascriptExecutor js = (JavascriptExecutor) driver;
								CommonLib.clickUsingJavaScript(driver, getsldHeader(50), "Deal Element");
								WebElement addComp = new WebDriverWait(driver, 25).until(ExpectedConditions.presenceOfElementLocated(By.xpath(
										"//div[@class='sf-interactions-proxy sf-interactions-proxyAddComponent sf-interactions-proxyAddComponentBefore']")));
								js.executeScript("arguments[0].setAttribute('style.display', 'block')", addComp);
								CommonLib.clickUsingJavaScript(driver, driver.findElement(By.xpath(
										"//div[@class='sf-interactions-proxy sf-interactions-proxyAddComponent sf-interactions-proxyAddComponentBefore']/a")),
										"Add Link");
							}

							CommonLib.switchToDefaultContent(driver);
							if (CommonLib.sendKeys(driver, getSearchonAppBuilder(50), "Navatar SDG", "SearchBox", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Navatar SDG has been Search", YesNo.No);
								if (CommonLib.click(driver, getNavatarSDGBtn(50), "Navatar SDG Button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Navatar SDG Button has been clicked", YesNo.No);
									if (CommonLib.sendKeys(driver, getTitle(50),tableName , "Title", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Title has been Entered", YesNo.No);
										if (CommonLib.getSelectedOptionOfDropDown(driver, getDataProvider(50), getDataProviderDropDownList(30), "Data Provider", "CustomObject:SDG_GROUPBY_1"))  {
											log(LogStatus.INFO, "SDG Data Provider has been searched", YesNo.No);
											if (CommonLib.click(driver, getSaveButton(50), "App builder Save Button", action.SCROLLANDBOOLEAN)) {
												log(LogStatus.INFO, "App Builder save button has been clicked", YesNo.No);
												if (CommonLib.click(driver, getAvtivateButton(50), "App builder Activate Button", action.SCROLLANDBOOLEAN)) {
													log(LogStatus.INFO, "App Builder save button has been clicked", YesNo.No);
													if (CommonLib.click(driver, getAvtivatesaveButton(50), "Activate save Button", action.SCROLLANDBOOLEAN)) {
														log(LogStatus.INFO, "Activate save button has been clicked", YesNo.No);

														if (CommonLib.click(driver, getAvtivateFinishButton(50), "Activate Finish Button", action.SCROLLANDBOOLEAN)) {
															log(LogStatus.INFO, "Activate Finish button has been clicked", YesNo.No);
														}
														else
														{
															log(LogStatus.ERROR, "Could not be click on Activate Finish button", YesNo.Yes);
															return false;
														}
													}
													else
													{
														log(LogStatus.ERROR, "Could not be click on Activate save button", YesNo.Yes);
														return false;
													}
												}
												else
												{
													log(LogStatus.ERROR, "Could not be click on Activate button", YesNo.Yes);
													return false;
												}
											}


											else {
												log(LogStatus.ERROR, "Could not be click on save button", YesNo.Yes);
												return false;
											}

										} else {
											log(LogStatus.ERROR, "Could not be Search the SDG Data Provider", YesNo.Yes);
											return false;
										}
									} else {
										log(LogStatus.ERROR, "Could not be entered the Title", YesNo.Yes);
										return false;
									}
								} else {
									log(LogStatus.ERROR, "Could not click on the Navatar SDG", YesNo.Yes);
									return false;
								}
							} else {
								log(LogStatus.ERROR, "Could not be Search the item", YesNo.Yes);
								return false;

							}

						}
						else
						{
							log(LogStatus.ERROR, "Could not be click on the Finish Button", YesNo.Yes);
							return false;
						}
					}
					else
					{
						log(LogStatus.ERROR, "Could not be click on next Button", YesNo.Yes);
						return false;
					}
				}
				else
				{
					log(LogStatus.ERROR, "Could not entered the label name", YesNo.Yes);
					return false;
				}
			}
			else
			{
				log(LogStatus.ERROR, "Could not click on the next button", YesNo.Yes);
				return false;
			}
		}
		else
		{
			log(LogStatus.ERROR, "Could not click on the new button", YesNo.Yes);
			return false;
		}

		driver.close();
		driver.switchTo().window(parentWindowID);
		CommonLib.ThreadSleep(1500);
		CommonLib.refresh(driver);

		if(BP.openAppFromAppLauchner(LabelName,50))
		{
			log(LogStatus.PASS,"App Page has been created : "+LabelName,YesNo.No);
			return true;
		}
		else
		{
			log(LogStatus.ERROR,"App Page is not created : "+LabelName,YesNo.No);
			return false;
		}

	}




	public  ArrayList<String> verifySDGDataOnAppPage(String environment, String mode, String appPageName,String sdgtableName,String[][] sdgTableData)
	{
		String sdgData=null;
		boolean flag=false;
		int status=0;
		WebElement pageSizeElement=null;
		BasePageBusinessLayer BP=new BasePageBusinessLayer(driver);
		ArrayList<String> verifyData = new ArrayList<String>();
		//	String pageSizeXpath="//a[text()='"+sdgtableName+"']/ancestor::div[contains(@class,'slds-card__header')]/following-sibling::div//select[@name='PagerSize']";
		//	String pageSizeXpath="//select[@name='PagerSize']";
		//	WebElement pageSizeElement=CommonLib.FindElement(driver, pageSizeXpath, "Page size", action.SCROLLANDBOOLEAN, 50);	
		int row = sdgTableData.length;	
		int col= sdgTableData[0].length+1;
		int totalData=row*col;

		ArrayList<String> sdgDataFromExcel=new ArrayList<String>();
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<sdgTableData[0].length;j++)
			{
				sdgDataFromExcel.add(sdgTableData[i][j]);
			}
		}

		if(row<=10)
		{
			log(LogStatus.INFO, "Rows are Less then or equal to 10", YesNo.No);

		}
		if(row>10 && row<=20)
		{
			String pageSizeXpath="//a[text()='"+sdgtableName+"']/ancestor::div[contains(@class,'slds-card__header')]/following-sibling::div//select[@name='PagerSize']";
			pageSizeElement=CommonLib.FindElement(driver, pageSizeXpath, "Page size", action.SCROLLANDBOOLEAN, 50);
			if(CommonLib.selectVisibleTextFromDropDown(driver, pageSizeElement, "Page Size","20"))
			{
				log(LogStatus.INFO, "Page size 20 is Selected", YesNo.No);
			}
			else
			{					
				log(LogStatus.ERROR, "Could not Select the 20 from the Page size", YesNo.Yes);	
				verifyData.add("Could not Select the 20 from the Page size");

			}

		}
		else if(row>20 && row<=50)
		{
			String pageSizeXpath="//a[text()='"+sdgtableName+"']/ancestor::div[contains(@class,'slds-card__header')]/following-sibling::div//select[@name='PagerSize']";
			pageSizeElement=CommonLib.FindElement(driver, pageSizeXpath, "Page size", action.SCROLLANDBOOLEAN, 50);
			if(CommonLib.selectVisibleTextFromDropDown(driver, pageSizeElement, "Page Size","50"))
			{
				log(LogStatus.INFO, "Page size 50 is Selected", YesNo.No);
			}
			else
			{
				log(LogStatus.ERROR, "Could not Select the 50 from the Page size", YesNo.Yes);
				verifyData.add("Could not Select the 50 from the Page size");

			}
		}
		else if(row>50)
		{
			String pageSizeXpath="//a[text()='"+sdgtableName+"']/ancestor::div[contains(@class,'slds-card__header')]/following-sibling::div//select[@name='PagerSize']";
			pageSizeElement=CommonLib.FindElement(driver, pageSizeXpath, "Page size", action.SCROLLANDBOOLEAN, 50);
			if(CommonLib.selectVisibleTextFromDropDown(driver, pageSizeElement, "Page Size","100"))
			{
				log(LogStatus.INFO, "Page size 100 is Selected", YesNo.No);
			}
			else
			{
				log(LogStatus.ERROR, "Could not Select the 100 from the Page size", YesNo.Yes);
				verifyData.add("Could not Select the 100 from the Page size");
			}
		}

		CommonLib.ThreadSleep(20000);
		String xpath="//a[text()='"+sdgtableName+"']/ancestor::div[contains(@class,'slds-card__header')]/following-sibling::div//tbody//td";

		List<WebElement> ele=CommonLib.FindElements(driver, xpath, "SDG Data");
		ArrayList<String> sdgDataFromOrg=new ArrayList<String>();
		for(int i=0;i<ele.size();i++)
		{
			try
			{
				sdgData=CommonLib.getText(driver, ele.get(i), "SDG Table", action.SCROLLANDBOOLEAN); 

				if(sdgData!="")
				{
					sdgDataFromOrg.add(sdgData);    		
				}		

			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				log(LogStatus.ERROR, "Could not get the text from the SDG", YesNo.Yes);
				verifyData.add("Could not get the text from the SDG");

			}
		}	

		for(int i=0;i<sdgDataFromExcel.size();i++)
		{
			if(sdgDataFromOrg.get(i).equals(sdgDataFromExcel.get(i)))
			{
				log(LogStatus.INFO, "Data from Excel : "+sdgDataFromExcel.get(i)+" has been matched with the Org SDG Data : "+sdgDataFromOrg.get(i), YesNo.No);
			}
			else
			{
				log(LogStatus.ERROR, "Data from Excel : "+sdgDataFromExcel.get(i)+" is not matched with the Org SDG Data : "+sdgDataFromOrg.get(i), YesNo.Yes);
				verifyData.add(sdgDataFromExcel.get(i));

			}
		}

		return verifyData;
	}







	public boolean CreateAppPage(String environment, String mode, String LabelName,ArrayList<String> tableName,ArrayList<String> dataProviderName,String parentWindowID)
	{
		String xPath="";
		boolean flag=false;
		BasePageBusinessLayer BP=new BasePageBusinessLayer(driver);
		SoftAssert sa= new SoftAssert();

		CommonLib.switchToFrame(driver, 50, getLocator());

		if (CommonLib.click(driver, getnewButton(80), "New Button", action.SCROLLANDBOOLEAN)) {
			log(LogStatus.INFO, "Clicked on the new button", YesNo.No);
			CommonLib.switchToDefaultContent(driver);
			if (CommonLib.click(driver, getnextButton(80), "Next Button", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on the next button", YesNo.No);
				if (CommonLib.sendKeys(driver, getlabelName(80), LabelName, "Label Name", action.SCROLLANDBOOLEAN)) {
					log(LogStatus.INFO, LabelName+"has been entered in the title", YesNo.No);
					if (CommonLib.click(driver, getnextButton(80), "Next Button", action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "Clicked on the next button", YesNo.No);
						if (CommonLib.click(driver, getfinishButton(80), "Next Button", action.SCROLLANDBOOLEAN)) {
							log(LogStatus.INFO, "Clicked on the Finish button", YesNo.No);

							for(int i =0;i<dataProviderName.size();i++)
							{

								CommonLib.switchToFrame(driver, 50, getAppBuilderIframe(80));
								CommonLib.ThreadSleep(5000);
								if(getAddComponentButton(50)!=null)
								{
									if(CommonLib.click(driver, getAddComponentButton(50), "Add to component", action.SCROLLANDBOOLEAN))
									{
										log(LogStatus.INFO, "Add to component button has been clicked", YesNo.No);
									}
									else
									{
										log(LogStatus.ERROR, "Could not be click on the Add to component button", YesNo.Yes);
										return false;	
									}
								}
								else
								{
									JavascriptExecutor js = (JavascriptExecutor) driver;
									CommonLib.clickUsingJavaScript(driver, getsldHeader(50), "SDG Header Element",action.SCROLLANDBOOLEAN);
									WebElement addComp = new WebDriverWait(driver, 25).until(ExpectedConditions.presenceOfElementLocated(By.xpath(
											"//div[@class='sf-interactions-proxy sf-interactions-proxyAddComponent sf-interactions-proxyAddComponentBefore']")));
									js.executeScript("arguments[0].setAttribute('style.display', 'block')", addComp);
									if(CommonLib.clickUsingJavaScript(driver, driver.findElement(By.xpath("//div[@class='sf-interactions-proxy sf-interactions-proxyAddComponent sf-interactions-proxyAddComponentBefore']/a")),"Add Link"))
									{
										log(LogStatus.INFO, "Add component plus icon has been clicked", YesNo.No);
									}
								}

								CommonLib.switchToDefaultContent(driver);
								if (CommonLib.sendKeys(driver, getSearchonAppBuilder(50), "Navatar SDG", "SearchBox", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "Navatar SDG has been Search", YesNo.No);
									if (CommonLib.click(driver, getNavatarSDGBtn(50), "Navatar SDG Button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Navatar SDG Button has been clicked", YesNo.No);
										if (CommonLib.sendKeys(driver, getTitle(50),tableName.get(i) , "Title", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Title has been Entered", YesNo.No);
											if (CommonLib.getSelectedOptionOfDropDown(driver, getDataProvider(50), getDataProviderDropDownList(30), "Data Provider",dataProviderName.get(i) ))  {
												log(LogStatus.INFO, "SDG Data Provider has been searched", YesNo.No);
											}
											else {
												log(LogStatus.ERROR, "Could not be Search the SDG Data Provider", YesNo.Yes);
												return false;
											}
										} else {
											log(LogStatus.ERROR, "Could not be entered the Title", YesNo.Yes);
											return false;
										}
									} else {
										log(LogStatus.ERROR, "Could not click on the Navatar SDG", YesNo.Yes);
										return false;
									}
								} else {
									log(LogStatus.ERROR, "Could not be Search the item", YesNo.Yes);
									return false;

								}
							}
							if (CommonLib.click(driver, getSaveButton(50), "App builder Save Button", action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "App Builder save button has been clicked", YesNo.No);
								if (CommonLib.click(driver, getAvtivateButton(50), "App builder Activate Button", action.SCROLLANDBOOLEAN)) {
									log(LogStatus.INFO, "App Builder save button has been clicked", YesNo.No);
									if (CommonLib.click(driver, getAvtivatesaveButton(50), "Activate save Button", action.SCROLLANDBOOLEAN)) {
										log(LogStatus.INFO, "Activate save button has been clicked", YesNo.No);

										if (CommonLib.click(driver, getAvtivateFinishButton(50), "Activate Finish Button", action.SCROLLANDBOOLEAN)) {
											log(LogStatus.INFO, "Activate Finish button has been clicked", YesNo.No);
										}
										else
										{
											log(LogStatus.ERROR, "Could not be click on Activate Finish button", YesNo.Yes);
											return false;
										}
									}
									else
									{
										log(LogStatus.ERROR, "Could not be click on Activate save button", YesNo.Yes);
										return false;
									}
								}
								else
								{
									log(LogStatus.ERROR, "Could not be click on Activate button", YesNo.Yes);
									return false;
								}
							}


							else {
								log(LogStatus.ERROR, "Could not be click on save button", YesNo.Yes);
								return false;
							}

						}				

						else 
						{
							log(LogStatus.ERROR, "Could not be click on the Finish Button", YesNo.Yes);						
							return false;
						}
					}
					else
					{
						log(LogStatus.ERROR, "Could not be click on next Button", YesNo.Yes);
						return false;
					}
				}
				else
				{
					log(LogStatus.ERROR, "Could not enter the label name", YesNo.Yes);
					return false;
				}
			}
			else
			{
				log(LogStatus.ERROR, "Could not click on the next button", YesNo.Yes);
				return false;
			}
		}
		else
		{
			log(LogStatus.ERROR, "Could not click on the new button", YesNo.Yes);
			return false;
		}


		driver.close();
		driver.switchTo().window(parentWindowID);
		CommonLib.ThreadSleep(1500);
		CommonLib.refresh(driver);

		if(BP.openAppFromAppLauchner(LabelName,50))
		{
			log(LogStatus.PASS,"App Page has been created : "+LabelName,YesNo.No);
			return true;
		}
		else
		{
			log(LogStatus.ERROR,"App Page is not created : "+LabelName,YesNo.No);
			return false;
		}

	}
}








